"""Contrôles exécutables du générateur hors ligne (bibliothèque standard)."""
import json
import os
from pathlib import Path
import subprocess
import tempfile
import unittest

SEED_DIR = Path(__file__).resolve().parent
SCRATCH = Path(os.environ.get('TMPDIR', str(Path.home() / '.hermes/cache/scratch')))

class DatasetTests(unittest.TestCase):
    def generate(self, seed=42, profile='demo'):
        temporary = tempfile.TemporaryDirectory(dir=SCRATCH)
        self.addCleanup(temporary.cleanup)
        root = Path(temporary.name)
        subprocess.run(['javac', '--release', '17', '-d', str(root), str(SEED_DIR / 'GenerateDataset.java')], check=True)
        subprocess.run(['java', '-cp', str(root), 'GenerateDataset', str(root / 'out'), str(seed), profile], check=True)
        runtime = json.loads((root / f'out/seed/generated/{profile}/runtime.json').read_text())
        return root / 'out', runtime

    def test_population_et_histoire_normale(self):
        from collections import Counter
        root, runtime = self.generate()
        self.assertEqual(len(runtime['copies']), 10)
        self.assertEqual(len(runtime['titles']), 10)
        self.assertEqual(len(runtime['cases']), 10)
        self.assertEqual(len({case['copyId'] for case in runtime['cases']}), 10)
        self.assertEqual(len(runtime['systemStates']), 10)
        self.assertEqual(len(runtime['sourceEvents']), 48)
        self.assertTrue(all(case['status'] == 'A_ANALYSER' and len(case['symptoms']) == 1 for case in runtime['cases']))
        oracle = json.loads((root / 'data/private/demo/oracle.json').read_text())
        self.assertEqual(Counter(case['family'] for case in oracle['cases']), {
            'LOCALISATION': 2, 'RETOUR_NON_PROJETE': 4, 'REPARATION_NON_PROJETEE': 4,
        })
        self.assertTrue(all(case['variant'] == 'POSITIVE' for case in oracle['cases']))
        self.assertEqual([rule['ruleId'] for rule in runtime['initialRuleSet']['rules']], ['R001'])

    def test_retours_normaux_sans_double_retour(self):
        _, runtime = self.generate()
        returned = [(e['copyId'], e['payload']['loanId']) for e in runtime['sourceEvents'] if e['eventType'] == 'ReturnReceived']
        self.assertEqual(len(returned), len(set(returned)), 'Un prêt ne peut être rendu physiquement deux fois')

    def test_localisation_simulee_oracle_separe(self):
        root, runtime = self.generate()
        path = root / 'data/private/demo/oracle.json'
        self.assertTrue(path.is_file(), 'L’oracle privé de simulation doit être exporté')
        oracle = json.loads(path.read_text())
        rows = [c for c in oracle['cases'] if c['family'] == 'LOCALISATION']
        self.assertEqual(len(rows), 2)
        self.assertEqual({c['variant'] for c in rows}, {'POSITIVE'})
        for case in rows:
            obs = [o for o in runtime['observations'] if o['copyId'] == case['copyId'] and o['quality'] == 'VALID']
            state = next(s for s in runtime['systemStates'] if s['copyId'] == case['copyId'])
            if case['variant'] == 'POSITIVE':
                self.assertEqual(len(obs), 1)
                self.assertNotEqual(obs[0]['locationId'], state['locationId'])
                self.assertEqual(case['admissible']['causeCodes'], ['LOCATION_MISMATCH'])
            else:
                self.assertNotIn('LOCATION_MISMATCH', case['admissible']['causeCodes'])
            self.assertTrue(case['evidenceRefs'])
        text = json.dumps(runtime)
        for forbidden in ('injectedTruth', 'admissible', 'variant', 'family', 'POSITIVE', 'NEAR_NEGATIVE'):
            self.assertNotIn(forbidden, text)

    def test_perturbations_projection_corrigees_ou_masquees(self):
        root, runtime = self.generate(profile='validation')
        oracle = json.loads((root / 'data/private/validation/oracle.json').read_text())
        mechanisms = {'PRET_NON_PROJETE': 'LoanOpened', 'RETOUR_NON_PROJETE': 'ReturnReceived',
            'TRANSFERT_INCOHERENT': 'TransferReceived', 'RECEPTION_NON_INTEGREE': 'ReceiptReceived',
            'REPARATION_NON_PROJETEE': 'RepairStarted', 'RETARD_INTEGRATION': 'LoanOpened'}
        for family, event_type in mechanisms.items():
            rows = [c for c in oracle['cases'] if c['family'] == family]
            self.assertEqual(len(rows), 9, family)
            for case in rows:
                events = [e for e in runtime['sourceEvents'] if e['copyId'] == case['copyId']]
                target = next(e for e in events if e['sourceEventId'] == case['injectedTruth']['businessEventId'])
                self.assertEqual(target['eventType'], event_type)
                processing = [e for e in events if e['eventType'] == 'IntegrationProcessed' and e['payload']['originalEventId'] == target['sourceEventId']]
                if case['variant'] == 'INCOMPLETE':
                    self.assertFalse(processing)
                    self.assertNotEqual(case['admissible']['conclusion'], 'SUPPORTED')
                    self.assertFalse(case['admissible']['causeCodes'])
                elif case['variant'] == 'NEAR_NEGATIVE':
                    self.assertEqual(max(processing, key=lambda e:e['occurredAt'])['payload']['outcome'], 'APPLIED')
                    self.assertFalse(case['admissible']['causeCodes'])
                else:
                    self.assertEqual(max(processing, key=lambda e:e['occurredAt'])['payload']['outcome'], 'QUEUED' if family == 'RETARD_INTEGRATION' else 'FAILED')
                    self.assertTrue(case['admissible']['causeCodes'])
                if family == 'RECEPTION_NON_INTEGREE':
                    self.assertFalse(any(e['eventType'] == 'LoanOpened' for e in events))
                    state = next(s for s in runtime['systemStates'] if s['copyId'] == case['copyId'])
                    self.assertEqual(state['status'], 'RECEIVING' if case['variant'] == 'NEAR_NEGATIVE' else 'NOT_REGISTERED')

    def test_incertitude_et_composition_sans_invention_de_perte(self):
        root, runtime = self.generate(profile='validation')
        oracle = json.loads((root / 'data/private/validation/oracle.json').read_text())
        from collections import Counter
        self.assertEqual(Counter(c['family'] for c in oracle['cases']), {f:9 for f in (
            'LOCALISATION', 'PRET_NON_PROJETE', 'RETOUR_NON_PROJETE', 'TRANSFERT_INCOHERENT', 'RECEPTION_NON_INTEGREE',
            'REPARATION_NON_PROJETEE', 'INVENTAIRE_INCOMPLET', 'RETARD_INTEGRATION', 'ABSENCE_INEXPLIQUEE', 'DEUX_CAUSES')})
        for case in oracle['cases']:
            family, variant, expected = case['family'], case['variant'], case['admissible']
            self.assertNotIn('LOST', expected['causeCodes'])
            self.assertNotIn('THEFT', expected['causeCodes'])
            if family == 'ABSENCE_INEXPLIQUEE' and variant != 'NEAR_NEGATIVE':
                self.assertEqual(expected['conclusion'], 'INDETERMINATE')
                self.assertFalse(expected['causeCodes'])
            if family == 'INVENTAIRE_INCOMPLET' and variant == 'POSITIVE':
                self.assertEqual(expected['causeCodes'], ['INVENTORY_INCOMPLETE'])
                self.assertTrue(any(r['kind'] == 'inventoryScope' for r in case['evidenceRefs']))
            if family == 'DEUX_CAUSES':
                self.assertEqual(len(case['symptoms']), 2)
                self.assertEqual(len(expected['causeCodes']), 2 if variant == 'POSITIVE' else 1)
                events = [e for e in runtime['sourceEvents'] if e['copyId'] == case['copyId'] and e['eventType'] == 'RepairStarted']
                observations = [o for o in runtime['observations'] if o['copyId'] == case['copyId']]
                self.assertNotEqual(events[-1]['payload']['locationId'], observations[0]['locationId'])

    def test_profils_independants_variations_structurelles(self):
        from collections import Counter
        datasets = {}
        for profile, total, cases in [('demo', 10, 10), ('validation', 180, 90), ('holdout', 240, 120)]:
            root, runtime = self.generate(profile=profile)
            self.assertEqual(len(runtime['copies']), total)
            self.assertEqual(len(runtime['cases']), cases)
            oracle = json.loads((root / f'data/private/{profile}/oracle.json').read_text())
            self.assertEqual(len(oracle['cases']), cases)
            if profile != 'demo':
                self.assertEqual(Counter(c['variant'] for c in oracle['cases']), {'POSITIVE':cases//3, 'NEAR_NEGATIVE':cases//3, 'INCOMPLETE':cases//3})
            families = {c['caseId']: c['family'] for c in oracle['cases']}
            public_order = [families[c['caseId']] for c in runtime['cases']]
            self.assertNotEqual(public_order, sorted(public_order))
            datasets[profile] = runtime
        for a,b in [('demo','validation'), ('validation','holdout'), ('holdout','demo')]:
            self.assertFalse({c['copyId'] for c in datasets[a]['copies']} & {c['copyId'] for c in datasets[b]['copies']})
        def ages(data):
            from datetime import datetime
            ref = datetime.fromisoformat(data['dataset']['referenceTime'])
            return {(ref-datetime.fromisoformat(e['occurredAt'])).total_seconds() for e in data['sourceEvents'] if e['eventType']=='LoanOpened' and e['occurredAt'] > '2026-09-29'}
        self.assertNotEqual(ages(datasets['validation']), ages(datasets['holdout']))

    def test_fenetre_incomplete_ne_promet_pas_le_journal_masque(self):
        root, runtime = self.generate(profile='validation')
        oracle = json.loads((root / 'data/private/validation/oracle.json').read_text())
        by_event = {e['sourceEventId']: e for e in runtime['sourceEvents']}
        for case in oracle['cases']:
            if case['variant'] == 'INCOMPLETE' and 'businessEventId' in case['injectedTruth'] and case['family'] != 'DEUX_CAUSES':
                target = by_event[case['injectedTruth']['businessEventId']]
                coverage = next(a for a in runtime['sourceAvailability'] if a['copyId']==case['copyId'] and a['source']=='INTEGRATIONS')
                self.assertLess(coverage['completeTo'], target['occurredAt'], 'La couverture promise doit exclure le traitement masqué')

    def test_catalogue_integral_manifestes_et_reproductibilite(self):
        import hashlib
        first, runtime = self.generate()
        catalogue = first / 'data/private/demo/catalogue.md'
        self.assertTrue(catalogue.is_file(), 'Le catalogue doit être généré automatiquement')
        text = catalogue.read_text()
        self.assertEqual(text.count('\n## Cas '), 10)
        for case in runtime['cases']:
            self.assertIn('## Cas ' + case['caseId'], text)
        manifest = json.loads((first / 'data/private/demo/manifest.json').read_text())
        self.assertEqual(manifest['counts']['copies'], 10)
        self.assertEqual(manifest['counts']['cases'], 10)
        for path, expected in manifest['sha256'].items():
            self.assertEqual(hashlib.sha256((first/path).read_bytes()).hexdigest(), expected)
        second, _ = self.generate()
        third, changed = self.generate(seed=43)
        files = [p.relative_to(first) for p in first.rglob('*') if p.is_file()]
        for path in files:
            self.assertEqual((first/path).read_bytes(), (second/path).read_bytes(), str(path))
        self.assertNotEqual((first/'seed/generated/demo/runtime.json').read_bytes(), (third/'seed/generated/demo/runtime.json').read_bytes())
        self.assertNotEqual([e['occurredAt'] for e in runtime['sourceEvents']], [e['occurredAt'] for e in changed['sourceEvents']])

    def test_schema_runtime_strict(self):
        import jsonschema
        schema_file = SEED_DIR / 'runtime.schema.json'
        self.assertTrue(schema_file.is_file(), 'Le contrat JSON Schema du runtime doit être livré')
        schema = json.loads(schema_file.read_text())
        jsonschema.Draft202012Validator.check_schema(schema)
        validator = jsonschema.Draft202012Validator(schema, format_checker=jsonschema.FormatChecker())
        for profile in ('demo','validation','holdout'):
            _, runtime = self.generate(profile=profile)
            validator.validate(runtime)
            runtime['cases'][0]['family'] = 'INTERDIT'
            self.assertTrue(list(validator.iter_errors(runtime)), 'Une annotation privée doit être rejetée')

    def test_notes_et_perimetres_absents_sont_cites(self):
        root, runtime = self.generate(profile='validation')
        oracle = json.loads((root / 'data/private/validation/oracle.json').read_text())
        for note in runtime['operatorNotes']:
            case = next(c for c in oracle['cases'] if c['copyId']==note['copyId'])
            self.assertIn({'kind':'operatorNote','id':note['noteId']}, case['evidenceRefs'])
        for case in oracle['cases']:
            if any(s['kind']=='MISSING' for s in case['symptoms']):
                self.assertTrue(any(r['kind']=='inventoryScope' for r in case['evidenceRefs']))

    def test_references_et_coupure_temporelle(self):
        from datetime import datetime
        from uuid import UUID
        for profile in ('demo','validation','holdout'):
            root, r = self.generate(profile=profile)
            ref = r['dataset']['referenceTime']
            copies = {c['copyId']:c for c in r['copies']}
            locations = {c['locationId']:c for c in r['locations']}
            events = {e['sourceEventId']:e for e in r['sourceEvents']}
            scopes = {s['scopeId']:s for s in r['inventoryScopes']}
            titles = {t['titleId'] for t in r['titles']}
            self.assertEqual(len(events), len(r['sourceEvents']))
            self.assertEqual(len(copies), len(r['copies']))
            for key in list(copies) + list(locations) + list(events) + list(scopes):
                self.assertEqual(str(UUID(key)), key)
            for c in copies.values(): self.assertIn(c['titleId'], titles)
            for e in events.values():
                self.assertIn(e['copyId'], copies)
                self.assertLessEqual(e['occurredAt'], e['recordedAt'])
                self.assertLess(e['recordedAt'], ref)
                if e['payload']['locationId'] is not None:
                    self.assertEqual(locations[e['payload']['locationId']]['siteId'], e['payload']['siteId'])
                if e['eventType']=='IntegrationProcessed':
                    original = events[e['payload']['originalEventId']]
                    self.assertNotEqual(original['eventType'], 'IntegrationProcessed')
                    self.assertEqual(original['copyId'], e['copyId'])
                    self.assertEqual(original['payload']['correlationId'], e['payload']['correlationId'])
                    self.assertLessEqual(original['recordedAt'], e['occurredAt'])
            for state in r['systemStates']:
                own = [e for e in events.values() if e['copyId']==state['copyId'] and e['eventType']=='IntegrationProcessed' and e['payload']['outcome']=='APPLIED']
                if own:
                    last = max(own,key=lambda e:e['occurredAt'])
                    self.assertIn(last['sourceEventId'], state['sourceEventIds'])
                    self.assertEqual(state['status'], last['payload']['projectedStatus'])
                    self.assertEqual(state['locationId'], last['payload']['locationId'])
                else: self.assertEqual(state['status'], 'NOT_REGISTERED')
                for event_id in state['sourceEventIds']: self.assertEqual(events[event_id]['copyId'], state['copyId'])
            for observation in r['observations']:
                scope = scopes[observation['scopeId']]
                self.assertEqual(scope['locationId'], observation['locationId'])
                self.assertLessEqual(scope['startedAt'], observation['observedAt'])
                self.assertLess(observation['observedAt'], ref)
                if scope['closedAt']: self.assertLessEqual(observation['observedAt'], scope['closedAt'])
            for scope in scopes.values():
                self.assertLess(scope['startedAt'], ref)
                if scope['closedAt']:
                    self.assertLessEqual(scope['startedAt'], scope['closedAt'])
                    self.assertLess(scope['closedAt'], ref)
            for availability in r['sourceAvailability']:
                if availability['status']=='UNAVAILABLE':
                    self.assertIsNone(availability['completeFrom']); self.assertIsNone(availability['completeTo'])
                else:
                    self.assertLessEqual(availability['completeFrom'], availability['completeTo'])
                    self.assertLessEqual(availability['completeTo'], ref)
            self.assertEqual(len(r['initialRuleSet']['rules']), 1)
            lookup = {'sourceEvent':set(events), 'systemState':set(copies), 'inventoryScope':set(scopes),
                'observation':{o['observationId'] for o in r['observations']}, 'operatorNote':{n['noteId'] for n in r['operatorNotes']},
                'sourceAvailability':{a['copyId']+'/'+a['source'] for a in r['sourceAvailability']}}
            oracle = json.loads((root/f'data/private/{profile}/oracle.json').read_text())
            for case in oracle['cases']:
                for evidence in case['evidenceRefs']: self.assertIn(evidence['id'], lookup[evidence['kind']])
            for c in r['cases']:
                self.assertEqual(c['inventoryReferenceTime'], ref)
                self.assertEqual(c['status'], 'A_ANALYSER')

    def test_chronologie_prets_et_transferts(self):
        _, r = self.generate()
        events = r['sourceEvents']
        opened = {e['payload']['loanId']:e for e in events if e['eventType']=='LoanOpened'}
        closed = {e['payload']['loanId']:e for e in events if e['eventType']=='LoanClosed'}
        dispatched = {e['payload']['transferId']:e for e in events if e['eventType']=='TransferDispatched'}
        for e in events:
            if e['eventType'] in ('LoanClosed','ReturnReceived'):
                before = opened[e['payload']['loanId']]
                self.assertEqual(e['copyId'], before['copyId'])
                self.assertLess(before['occurredAt'], e['occurredAt'])
                if e['eventType']=='ReturnReceived': self.assertIn(e['payload']['loanId'], closed)
            if e['eventType']=='TransferReceived':
                before = dispatched[e['payload']['transferId']]
                self.assertLess(before['occurredAt'], e['occurredAt'])
                self.assertEqual(before['payload']['fromSiteId'], e['payload']['fromSiteId'])
                self.assertEqual(before['payload']['toSiteId'], e['payload']['toSiteId'])

    def test_profil_invalide_sans_ecriture(self):
        temporary = tempfile.TemporaryDirectory(dir=SCRATCH)
        self.addCleanup(temporary.cleanup)
        root = Path(temporary.name)
        subprocess.run(['javac','--release','17','-d',str(root),str(SEED_DIR/'GenerateDataset.java')], check=True)
        result = subprocess.run(['java','-cp',str(root),'GenerateDataset',str(root/'out'),'42','../incorrect'], capture_output=True, text=True)
        self.assertNotEqual(result.returncode, 0)
        self.assertIn('Profil inconnu', result.stderr)
        self.assertFalse((root/'out').exists())

    def test_verite_injectee_et_masquage_reel(self):
        root, r = self.generate(profile='validation')
        oracle = json.loads((root/'data/private/validation/oracle.json').read_text())
        public_ids = {e['sourceEventId'] for e in r['sourceEvents']}
        for case in oracle['cases']:
            truth = case['injectedTruth']
            self.assertIn('activePerturbations', truth)
            if case['variant']=='NEAR_NEGATIVE' and case['family']!='DEUX_CAUSES':
                self.assertFalse(truth['activePerturbations'])
            if case['variant']=='INCOMPLETE' and 'businessEventId' in truth and case['family']!='DEUX_CAUSES':
                self.assertEqual(len(truth['hiddenSourceEvents']), 1)
                event = truth['hiddenSourceEvents'][0]
                self.assertNotIn(event['sourceEventId'], public_ids)
                self.assertEqual(event['payload']['originalEventId'], truth['businessEventId'])
                self.assertEqual(event['payload']['outcome'], 'QUEUED' if case['family']=='RETARD_INTEGRATION' else 'FAILED')

    def test_holdout_varie_ordre_metier_retour_cloture(self):
        root, r = self.generate(profile='holdout')
        oracle = json.loads((root/'data/private/holdout/oracle.json').read_text())
        for case in [c for c in oracle['cases'] if c['family']=='RETOUR_NON_PROJETE']:
            received = next(e for e in r['sourceEvents'] if e['sourceEventId']==case['injectedTruth']['businessEventId'])
            closed = next(e for e in r['sourceEvents'] if e['eventType']=='LoanClosed' and e['payload']['loanId']==received['payload']['loanId'])
            self.assertLess(received['occurredAt'], closed['occurredAt'])

    def test_export_minimal(self):
        source = SEED_DIR / 'GenerateDataset.java'
        self.assertTrue(source.is_file(), 'Le générateur Java doit exister')
        with tempfile.TemporaryDirectory(dir=SCRATCH) as tmp:
            root = Path(tmp)
            subprocess.run(['javac', '--release', '17', '-d', str(root), str(source)], check=True)
            subprocess.run(['java', '-cp', str(root), 'GenerateDataset', str(root / 'out'), '42', 'demo'], check=True)
            runtime = json.loads((root / 'out/seed/generated/demo/runtime.json').read_text())
            self.assertEqual(runtime['schemaVersion'], '1.0.0')
            self.assertEqual(runtime['dataset']['seed'], 42)
            self.assertNotIn('datasetEpoch', runtime['dataset'])
            self.assertEqual(runtime['initialRuleSet'], {'version': 1, 'rules': [{'ruleId': 'R001', 'ruleVersion': 1, 'dmnPath': 'rules/R001-v1.dmn'}]})
            manifest = json.loads((root / 'out/seed/generated/demo/manifest.json').read_text())
            self.assertEqual(manifest['generatorVersion'], '2.0.0')
            self.assertEqual(manifest['counts']['cases'], 10)
            self.assertNotIn('familyCounts', manifest)
            self.assertNotIn('variantCounts', manifest)
            self.assertEqual(set(manifest['sha256']), {'seed/generated/demo/runtime.json'})

    def test_rule_validation_corpus_is_separate_and_has_counterexamples(self):
        from collections import Counter
        root, runtime = self.generate(profile='validation')
        corpus = json.loads((root / 'data/private/validation/rule-corpus.json').read_text())
        self.assertEqual(len(corpus['cases']), 33)
        self.assertEqual(Counter(case['scope'] for case in corpus['cases']), {
            'LOCATION_MISMATCH': 9, 'ReturnReceived': 12, 'RepairStarted': 12,
        })
        self.assertEqual(sum(case['expectedMatch'] for case in corpus['cases']), 9)
        for case in corpus['cases']:
            self.assertNotIn('scope', case['evidence'])
            self.assertNotIn('expectedMatch', case['evidence'])
            self.assertNotIn('injectedTruth', case['evidence'])
            self.assertEqual(len(case['evidence']['symptoms']), 1)
        self.assertNotIn('expectedMatch', json.dumps(runtime))
        independent = [case for case in corpus['cases'] if case['variant'] == 'UNKNOWN_SOURCE']
        self.assertEqual(len(independent), 2)
        self.assertTrue(all(len(case['evidence']['observations']) == 1 and case['evidence']['observations'][0]['quality'] == 'VALID' for case in independent))

if __name__ == '__main__':
    unittest.main(verbosity=2)
