# Contrats de réalisation — REST, Kafka et SSE

Ces fichiers sont des **ressources de conception**, pas une application. Toutes les données sous `examples/` sont des **fixtures synthétiques indépendantes**, jamais des réponses observées sur un serveur. Les UUID d'époque de ces exemples désignent une simulation contractuelle, pas l'époque du dataset généré. Aucun appel OpenAI, installation, serveur ou déploiement n'est nécessaire.

## Entrées et validation

- `openapi.json` : OpenAPI **3.1.0**, schémas JSON Schema draft **2020-12** ; frontières Admin/worker explicites.
- `asyncapi.json` : AsyncAPI **3.0.0**, perspective backend Java. `payload` est un **Multi Format Schema Object** avec `schemaFormat: application/schema+json;version=draft-2020-12`. Les outils AsyncAPI doivent prendre ce dialecte en charge ; ne pas interpréter ces payloads comme du draft-07.
- `schemas/common.schema.json` : objets métier, requêtes/réponses REST, références de preuves, diagnostics, opérations de règles, erreurs et budgets. Objets fermés, tableaux et chaînes bornés.
- `schemas/kafka-message.schema.json` : union `oneOf` discriminée par `eventType`, **13 types** après ajout normatif de `RuleChangeNotProposed`.
- `schemas/dead-letter.schema.json` : notification de quarantaine distincte des résultats métier.
- `schemas/sse-event.schema.json` : notifications et contrôles SSE.
- `operation-index.json` : index machine des opérations et des schémas associés.
- `examples/manifest.json` : inventaire exhaustif des fixtures JSON et de leur schéma.
- `examples/sse/stream.txt` : exemple exact du framing SSE, séparé des fixtures JSON.
- `http-et-outils.md`, `kafka-lifecycle.md`, `protocole-sse.md` : sémantique transactionnelle, permissions, erreurs, reprises et garanties.

Depuis ce dossier :

```sh
python -m unittest discover -s tests -v
python validate.py
```

Les scripts utilisent Python standard, `jsonschema` et `referencing` déjà disponibles. Aucun téléchargement. `python build_contracts.py` régénère les JSON et exemples uniquement dans ce dossier ; il ne touche pas aux documents de cadrage. Les fichiers générés complets sont directement utilisables sans exécuter ce script.

Les résultats réels de validation sont conservés sous `test-results/`. Les captures RED/GREEN montrent les échecs intentionnels avant implémentation du validateur puis les tests réussis. `validation.json` est le rapport du dernier contrôle exécuté ; ne pas confondre ses résultats avec les fixtures métier.

**Portée honnête des vérifications :** validation réelle des métaschémas et des instances avec `Draft202012Validator` et `FormatChecker`, résolution locale des `$ref`, contrôles structurels OpenAPI/AsyncAPI et mutations négatives. Aucun validateur officiel OpenAPI/AsyncAPI n'est installé/utilisé ici. Le contrôle personnalisé n'est pas une certification officielle de ces formats. Aucun test HTTP réel, Kafka, PostgreSQL, KIE/DMN, quota distribué ou sécurité réseau n'a été exécuté. Les invariants transactionnels décrits doivent devenir des tests entre composants de l'application.

## Conventions essentielles

- Une seule convention de version de contrat : `schemaVersion: "1.0.0"`. Les documents OpenAPI/AsyncAPI et versions de prompts illustratifs utilisent également `1.0.0`. Le suffixe `.v1` des topics désigne la génération majeure du transport.
- Identités métier/techniques opaques UUID ; `siteId` vaut `CENTRE|NORD|SUD`, règle bootstrap `R001` puis UUID. `decisionId`, `rowIds`, cause codes et noms de source sont des codes métier, pas des identités UUID.
- Versions positives ; compteurs et index de chunks peuvent être nuls. Dates UTC avec `Z`.
- `datasetId` identifie un jeu reproductible, `datasetEpoch` une charge runtime. Ne jamais remplacer l'un par l'autre.
- `RuleChange` est une union fermée `CREATE|REVISE|RETIRE`. La création et la révision passent exclusivement par l'agent ; le retrait peut aussi être confirmé directement par Admin. Aucun endpoint de saisie de DMN ou de conditions par Admin.
- Les huit types `SourceEvent` sont des faits sources, **pas** des messages Kafka d'agent. Le contrat des payloads sources proposé ici est une représentation typée ; l'importeur doit adapter les données brutes à ces clés sans ajouter d'oracle.
- Les `$ref` ne sortent jamais de `contracts/`. Les schémas sont indépendants de l'ordre de création des ressources par d'autres intervenants.

## Couverture UX

| Cas d'usage | Opérations principales |
|---|---|
| UC01–02 explorer/dossier | `list_cases`, `get_case_dossier`, `get_analysis_snapshot` |
| UC03–04 analyse/suivi/arrêt | `start_analysis`, `get_analysis`, `get_mission`, `cancel_analysis`, `cancel_mission`, SSE |
| UC05 diagnostic | `get_analysis_diagnostic`, `accept_diagnostic`, `reject_diagnostic` |
| UC06 proposition agent | synthèse automatique après acceptation fallback, `get_proposal` ; issue normale `RuleChangeNotProposed` si rien à proposer |
| UC07 discussion/diff | `send_expert_message`, `list_messages`, `get_proposal_revision`, `compare_proposal_revisions` |
| UC08 tests/impact | `test_proposal`, `get_validation_report` |
| UC09 publication | `approve_and_publish` uniquement Admin |
| UC10 analogue | même `start_analysis`, publication courante épinglée au démarrage |
| UC11–12 règles/révision | `list_rules`, `get_rule_version`, `request_rule_revision` |
| UC13 retrait | `preview_rule_retirement`, `retire_rule` uniquement Admin |
| UC14 lots | `start_batch`, `get_batch`, `cancel_batch` ; 20 cas maximum |
| UC15 comparaisons | `compare_analyses`, `compare_publications` |
| UC16 démonstration | `get_guided_demo` vérifie les étapes réellement atteintes ; les actions utilisent les mêmes API métier, pas un endpoint simulant un résultat |
| État local/vue globale | `get_local_status`, `get_overview`, `get_sync_snapshot` |

Le reset destructif reste hors REST, commande locale séparée du parcours métier. La démo n'a pas d'accès aux labels cachés ; les étapes sont déduites d'actions réellement enregistrées, pas de conclusions fabriquées.

## Finding et R001

Le schéma `Finding` est la référence partagée. Pour un résultat déterministe, les quatre champs `ruleId`, `ruleVersion`, `decisionId`, `rowIds` sont obligatoires ; même hors `DiagnosticRecord` ils doivent être présents ensemble ou tous absents. Ils ne remplacent ni `subjectCopyId`, ni la couverture précise des symptômes, ni les références de preuves.

R001 reçoit les cinq entrées plates `expectedSiteId`, `expectedLocationId`, `observedSiteId`, `observedLocationId`, `observationReliable`. Le résultat brut COLLECT `causeCode=LOCATION_DIFFERENCE`, `epistemicStatus=SUPPORTED` n'est **pas** à lui seul un Finding. Java l'enrichit avec `ruleId=R001`, `ruleVersion=1`, `decisionId=decision_location_difference`, `rowIds=["R001_row_1"]`, le sujet, les symptômes effectivement couverts, preuves et limites. Aucun schéma de contrat n'importe le fichier DMN externe. Les exemples de brouillon DMN ne prouvent pas une compilation KIE ; l'empreinte seule ne prouve ni correction ni sécurité.
