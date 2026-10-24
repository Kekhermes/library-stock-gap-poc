"""Tests hors ligne des contrats ; aucune requête réseau."""
import importlib.util
from pathlib import Path
import unittest

ROOT = Path(__file__).resolve().parents[1]

class ValidatorBootstrap(unittest.TestCase):
    def test_validator_exists_and_checks_its_own_schema(self):
        path = ROOT / 'validate.py'
        self.assertTrue(path.is_file(), 'Le validateur de contrats reste à implémenter')
        spec = importlib.util.spec_from_file_location('contract_validator', path)
        module = importlib.util.module_from_spec(spec)
        spec.loader.exec_module(module)
        validator = module.ContractValidator(ROOT)
        validator.validate_schema({'$schema': 'https://json-schema.org/draft/2020-12/schema', 'type': 'object'})
        with self.assertRaises(Exception):
            validator.validate_schema({'type': 'not-a-json-schema-type'})

class MessageContracts(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        spec = importlib.util.spec_from_file_location('contract_validator', ROOT / 'validate.py')
        cls.module = importlib.util.module_from_spec(spec)
        spec.loader.exec_module(cls.module)

    def test_all_thirteen_kafka_examples_and_reject_mutations(self):
        import json
        import copy
        validator = self.module.ContractValidator(ROOT)
        self.assertTrue(callable(getattr(validator, 'validate', None)), 'Validation des instances manquante')
        paths = sorted((ROOT / 'examples/kafka').glob('*.json'))
        self.assertEqual(13, len(paths))
        types = set()
        for path in paths:
            example = json.loads(path.read_text())
            types.add(example['eventType'])
            validator.validate('kafka-message.schema.json', example)
            for field, value in [('eventType', 'UnknownType'), ('schemaVersion', '2.0.0'),
                                 ('attempt', 0), ('eventId', 'not-a-uuid'), ('datasetEpoch', 'old'),
                                 ('occurredAt', '2026-01-01T00:00:00+02:00')]:
                with self.subTest(path=path.name, field=field):
                    bad = copy.deepcopy(example)
                    bad[field] = value
                    with self.assertRaises(self.module.ValidationError):
                        validator.validate('kafka-message.schema.json', bad)
        self.assertEqual(13, len(types))
        no_change = json.loads((ROOT / 'examples/kafka/RuleChangeNotProposed.json').read_text())
        no_change['payload']['reason'] = 'TECHNICAL_FAILURE'
        with self.assertRaises(self.module.ValidationError):
            validator.validate('kafka-message.schema.json', no_change)
        investigation = json.loads((ROOT / 'examples/kafka/InvestigationRequested.json').read_text())
        del investigation['caseId']
        with self.assertRaises(self.module.ValidationError):
            validator.validate('kafka-message.schema.json', investigation)
        revision = json.loads((ROOT / 'examples/kafka/RuleRevisionRequested.json').read_text())
        revision['caseId'] = revision['missionId']
        with self.assertRaises(self.module.ValidationError):
            validator.validate('kafka-message.schema.json', revision)
        diagnostic = json.loads((ROOT / 'examples/kafka/DiagnosticProposed.json').read_text())
        del diagnostic['payload']['diagnostic']['findings'][0]['evidenceRefs']
        with self.assertRaises(self.module.ValidationError):
            validator.validate('kafka-message.schema.json', diagnostic)

class TransportDocuments(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        spec = importlib.util.spec_from_file_location('contract_validator', ROOT / 'validate.py')
        cls.module = importlib.util.module_from_spec(spec)
        spec.loader.exec_module(cls.module)

    def test_documents_and_manifest_validate(self):
        validator = self.module.ContractValidator(ROOT)
        self.assertTrue(callable(getattr(validator, 'check_documents', None)), 'Contrôles de documents manquants')
        result = validator.check_documents()
        self.assertGreaterEqual(result['openapiOperations'], 50)
        self.assertEqual(result['kafkaTypes'], 13)
        self.assertGreaterEqual(validator.validate_examples(), 30)

    def test_deterministic_record_requires_dmn_provenance(self):
        import json
        validator = self.module.ContractValidator(ROOT)
        example = json.loads((ROOT / 'examples/rest/diagnostic-record.json').read_text())
        example['path'] = 'DETERMINISTIC'
        with self.assertRaises(self.module.ValidationError):
            validator.validate('common.schema.json#/$defs/DiagnosticRecord', example)
        finding = example['diagnostic']['findings'][0]
        finding.update(ruleId='R001', ruleVersion=1, decisionId='decision_location_difference', rowIds=['R001_row_1'])
        validator.validate('common.schema.json#/$defs/DiagnosticRecord', example)

    def test_closed_payload_and_tool_authorization(self):
        import json
        validator = self.module.ContractValidator(ROOT)
        message = json.loads((ROOT / 'examples/kafka/RuleChangeProposed.json').read_text())
        message['payload']['approveAndPublish'] = True
        with self.assertRaises(self.module.ValidationError):
            validator.validate('kafka-message.schema.json', message)
        authorize = json.loads((ROOT / 'examples/rest/authorize-tool-request.json').read_text())
        del authorize['tool']
        with self.assertRaises(self.module.ValidationError):
            validator.validate('common.schema.json#/$defs/AuthorizeCallRequest', authorize)
        prevalidation = json.loads((ROOT / 'examples/rest/prevalidate-result.json').read_text())
        prevalidation['publishable'] = True
        with self.assertRaises(self.module.ValidationError):
            validator.validate('common.schema.json#/$defs/PrevalidationResult', prevalidation)

    def test_sse_and_publication_reject_invalid_versions_and_fields(self):
        import json
        validator = self.module.ContractValidator(ROOT)
        self.assertTrue((ROOT / 'examples/sse/AnalysisChanged.json').is_file())
        event = json.loads((ROOT / 'examples/sse/AnalysisChanged.json').read_text())
        validator.validate('sse-event.schema.json', event)
        event['data']['version'] = 0
        with self.assertRaises(self.module.ValidationError):
            validator.validate('sse-event.schema.json', event)
        publication = {'expectedProposalRevision': 1, 'expectedProposalVersion': 1,
                       'expectedRuleSetVersion': 1, 'reportId': '00000000-0000-4000-8000-000000000001',
                       'candidateSha256': 'a' * 64, 'confirmation': 'APPROVE_AND_PUBLISH'}
        validator.validate('common.schema.json#/$defs/PublishRequest', publication)
        publication['draft'] = {'arbitrary': 'manual edit'}
        with self.assertRaises(self.module.ValidationError):
            validator.validate('common.schema.json#/$defs/PublishRequest', publication)

if __name__ == '__main__':
    unittest.main()
