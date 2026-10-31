# Catalogue privé — validation

Généré depuis l'oracle de simulation v2.0.0. Réservé aux tests et à l'expert ; ne pas exposer au worker IA.

Vocabulaire explicite : une opération métier concerne un exemplaire ; sa mise à jour du stock informatique est représentée par `IntegrationProcessed`. Un cas est un dossier d'écart.

Les extraits JSON restent fidèles aux données générées : leurs noms techniques et anciens libellés sont conservés. `projection` désigne l'état du stock informatique ; `intégration` désigne sa mise à jour, pas un échange Kafka. Voir le [glossaire validé](../../../../06-domaine-ddd-et-cycles-de-vie.md).

Référence : 2026-09-30T12:00:00Z; seed : 4242. Chaque attente est déclarée par le scénario, sans exécuter un moteur diagnostique.

## Cas 09c0cb68-f98f-3bbe-b6e5-95d60e39b586 — dossier d'écart

- Exemplaire : `66e61e6c-6206-3bc0-94c0-27fd99dbfb43`
- Famille privée : ABSENCE_INEXPLIQUEE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"ce9599a9-88e8-3bd9-8175-d427f47097a5","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":"1"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"mechanism":"Exemplaire resté au rayon, lecture de contrôle réussie.","physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"Le scan valide au rayon attendu réfute l'absence courante.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"66e61e6c-6206-3bc0-94c0-27fd99dbfb43","status":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","sourceEventIds":["9ee19cd0-0933-3147-a5fa-e4d0cb083c9c","77ce96e9-8a88-374f-a71e-7386c81bbbf0"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | ae2e513b-38a5-3b6a-b21f-d6193b79ddba | 2026-06-01T21:26:56Z | 2026-06-01T21:27:06Z | `{"operationId":"af99ab31-9ed6-3974-9b01-44fe0e0c80cc","correlationId":"5363a279-9e74-34db-a97e-c252b514e7be","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"23a8c32d-a435-389c-9163-56efec22dea2"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | f13352c4-aed3-3a4c-af87-f3c315ae9a18 | 2026-06-02T21:26:56Z | 2026-06-02T21:27:06Z | `{"originalEventId":"ae2e513b-38a5-3b6a-b21f-d6193b79ddba","correlationId":"5363a279-9e74-34db-a97e-c252b514e7be","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | effda30b-8512-353d-b708-7c17aef3c275 | 2026-07-31T21:26:56Z | 2026-07-31T21:27:06Z | `{"operationId":"1e3411cb-c01d-3076-8a92-f8c46e38ee2e","correlationId":"f4963db1-b475-3cb6-9f6c-49b11450e2d6","siteId":"NORD","locationId":null,"loanId":"930e0887-4e59-3b04-8c45-2f09a940bd7f"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | d4aa1890-2d97-3695-8ae8-4ce49907b549 | 2026-07-31T21:27:56Z | 2026-07-31T21:28:06Z | `{"originalEventId":"effda30b-8512-353d-b708-7c17aef3c275","correlationId":"f4963db1-b475-3cb6-9f6c-49b11450e2d6","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | a73605fa-f347-3526-b811-aede26409476 | 2026-08-20T21:26:56Z | 2026-08-20T21:27:06Z | `{"operationId":"282a771f-c738-3c37-81fb-f47b3de34292","correlationId":"b74ddcc3-7a01-37df-9385-f9a1e3766c5d","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"930e0887-4e59-3b04-8c45-2f09a940bd7f"}` |
| Retour reçu (`ReturnReceived`) | 9ee19cd0-0933-3147-a5fa-e4d0cb083c9c | 2026-08-20T21:27:26Z | 2026-08-20T21:27:36Z | `{"operationId":"2d35a2e2-9859-38c3-938d-0ad79c8e48d8","correlationId":"741b028f-24ab-3909-9865-bd4e101c943b","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"930e0887-4e59-3b04-8c45-2f09a940bd7f"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 77ce96e9-8a88-374f-a71e-7386c81bbbf0 | 2026-08-21T21:26:56Z | 2026-08-21T21:27:06Z | `{"originalEventId":"9ee19cd0-0933-3147-a5fa-e4d0cb083c9c","correlationId":"741b028f-24ab-3909-9865-bd4e101c943b","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"843c6b81-b1ff-36c8-9f6a-523f2dc79562","copyId":"66e61e6c-6206-3bc0-94c0-27fd99dbfb43","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","scopeId":"147f13fd-620d-3c02-bc47-bf4f7080184b","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"66e61e6c-6206-3bc0-94c0-27fd99dbfb43","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"66e61e6c-6206-3bc0-94c0-27fd99dbfb43","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"66e61e6c-6206-3bc0-94c0-27fd99dbfb43","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"66e61e6c-6206-3bc0-94c0-27fd99dbfb43","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"66e61e6c-6206-3bc0-94c0-27fd99dbfb43","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"66e61e6c-6206-3bc0-94c0-27fd99dbfb43","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"66e61e6c-6206-3bc0-94c0-27fd99dbfb43","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**
- `{"noteId":"103c1e9d-e5c9-36db-8171-c3ed3cef6220","copyId":"66e61e6c-6206-3bc0-94c0-27fd99dbfb43","text":"Nouvelle lecture du code-barres effectuée dans le rayon affecté.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"66e61e6c-6206-3bc0-94c0-27fd99dbfb43"},{"kind":"sourceEvent","id":"ae2e513b-38a5-3b6a-b21f-d6193b79ddba"},{"kind":"sourceEvent","id":"f13352c4-aed3-3a4c-af87-f3c315ae9a18"},{"kind":"sourceEvent","id":"effda30b-8512-353d-b708-7c17aef3c275"},{"kind":"sourceEvent","id":"d4aa1890-2d97-3695-8ae8-4ce49907b549"},{"kind":"sourceEvent","id":"a73605fa-f347-3526-b811-aede26409476"},{"kind":"sourceEvent","id":"9ee19cd0-0933-3147-a5fa-e4d0cb083c9c"},{"kind":"sourceEvent","id":"77ce96e9-8a88-374f-a71e-7386c81bbbf0"},{"kind":"observation","id":"843c6b81-b1ff-36c8-9f6a-523f2dc79562"},{"kind":"inventoryScope","id":"147f13fd-620d-3c02-bc47-bf4f7080184b"},{"kind":"sourceAvailability","id":"66e61e6c-6206-3bc0-94c0-27fd99dbfb43/LOANS"},{"kind":"sourceAvailability","id":"66e61e6c-6206-3bc0-94c0-27fd99dbfb43/RETURNS"},{"kind":"sourceAvailability","id":"66e61e6c-6206-3bc0-94c0-27fd99dbfb43/RECEIPTS"},{"kind":"sourceAvailability","id":"66e61e6c-6206-3bc0-94c0-27fd99dbfb43/TRANSFERS"},{"kind":"sourceAvailability","id":"66e61e6c-6206-3bc0-94c0-27fd99dbfb43/REPAIRS"},{"kind":"sourceAvailability","id":"66e61e6c-6206-3bc0-94c0-27fd99dbfb43/INTEGRATIONS"},{"kind":"sourceAvailability","id":"66e61e6c-6206-3bc0-94c0-27fd99dbfb43/INVENTORY"},{"kind":"operatorNote","id":"103c1e9d-e5c9-36db-8171-c3ed3cef6220"},{"kind":"inventoryScope","id":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5"},{"kind":"inventoryScope","id":"eb1ce5f1-7397-3b45-9385-ef328e09fec8"},{"kind":"inventoryScope","id":"d0c55bda-319d-3537-857a-5d135f796cab"},{"kind":"inventoryScope","id":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb"},{"kind":"inventoryScope","id":"d9842c03-ba37-324b-b71b-e58ac7147d4c"},{"kind":"inventoryScope","id":"07bae854-b205-39bf-a23e-9eaa22063b92"},{"kind":"inventoryScope","id":"9de706b4-d360-3282-b440-c440861c039c"},{"kind":"inventoryScope","id":"b98af8e5-7297-3dc5-a947-eb8fe8af159f"},{"kind":"inventoryScope","id":"7eb96d9b-e927-305a-b369-01d6b239f3db"},{"kind":"inventoryScope","id":"b4a97852-2336-3ae2-ab5b-30b6e2e4d536"},{"kind":"inventoryScope","id":"147f13fd-620d-3c02-bc47-bf4f7080184b"}]`

- Session : `{"scopeId":"147f13fd-620d-3c02-bc47-bf4f7080184b","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"eb1ce5f1-7397-3b45-9385-ef328e09fec8","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"d0c55bda-319d-3537-857a-5d135f796cab","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"d9842c03-ba37-324b-b71b-e58ac7147d4c","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"07bae854-b205-39bf-a23e-9eaa22063b92","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"9de706b4-d360-3282-b440-c440861c039c","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b98af8e5-7297-3dc5-a947-eb8fe8af159f","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"7eb96d9b-e927-305a-b369-01d6b239f3db","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b4a97852-2336-3ae2-ab5b-30b6e2e4d536","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"147f13fd-620d-3c02-bc47-bf4f7080184b","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 11e83f80-e278-3f62-94b7-7d62c8aad077 — dossier d'écart

- Exemplaire : `88045099-9058-313f-a828-d56a9fb2671c`
- Famille privée : ABSENCE_INEXPLIQUEE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"054c19f9-a80b-3e38-be7f-eda8aa35ba54","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["UNOBSERVED_MOVE"],"mechanism":"Exemplaire déplacé hors du périmètre instrumenté ; la destination n'est pas exposée et aucune perte n'est simulée.","physicalStatus":"ON_SHELF","physicalSiteId":"CENTRE","physicalLocationId":null,"evidenceMask":"Collecte indisponible"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"L'absence de lecture ne démontre ni la destination, ni une perte, ni un vol ; données insuffisantes pour attribuer une cause.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État du stock informatique : `{"copyId":"88045099-9058-313f-a828-d56a9fb2671c","status":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","sourceEventIds":["6f60bbcb-c603-3bc1-b01c-e19de89cf0e1","03069d20-d6f2-35d9-a71c-b289d5cb065b"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | efea5b3b-b5af-3545-8c45-ccf2d1fd56e4 | 2026-06-02T08:49:31Z | 2026-06-02T08:49:41Z | `{"operationId":"8ae693d9-f65d-3fd6-a596-21b602096ac0","correlationId":"9ff2624e-addf-3272-9acf-faba10ecdbce","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"e4e208e5-6a2c-344c-96e5-18966272d613"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 463e27d5-9f76-3fa5-ab4f-7c69ed43bcb3 | 2026-06-03T08:49:31Z | 2026-06-03T08:49:41Z | `{"originalEventId":"efea5b3b-b5af-3545-8c45-ccf2d1fd56e4","correlationId":"9ff2624e-addf-3272-9acf-faba10ecdbce","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | db5de0fe-a0b3-3e36-bd02-1bb48c7d80df | 2026-08-01T08:49:31Z | 2026-08-01T08:49:41Z | `{"operationId":"7e575084-8cef-34f4-b04b-67cce331caf7","correlationId":"5d832f9a-7c50-3bf8-9d9a-544ef49c6ba6","siteId":"CENTRE","locationId":null,"loanId":"8b428e5a-ac8e-38f6-a93c-14dd4a05add0"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 04896c10-e455-317a-a60b-210e8c97ff00 | 2026-08-01T08:50:31Z | 2026-08-01T08:50:41Z | `{"originalEventId":"db5de0fe-a0b3-3e36-bd02-1bb48c7d80df","correlationId":"5d832f9a-7c50-3bf8-9d9a-544ef49c6ba6","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 42c49056-7811-3f4e-a005-d5309caa8fb6 | 2026-08-21T08:49:31Z | 2026-08-21T08:49:41Z | `{"operationId":"e67a6da2-24a1-3e76-accd-99ad00858158","correlationId":"651ac57a-642a-3da6-b3bf-65db2886f891","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"8b428e5a-ac8e-38f6-a93c-14dd4a05add0"}` |
| Retour reçu (`ReturnReceived`) | 6f60bbcb-c603-3bc1-b01c-e19de89cf0e1 | 2026-08-21T08:50:01Z | 2026-08-21T08:50:11Z | `{"operationId":"574f64f4-1a57-3d0a-90e7-800347754516","correlationId":"82cf6d8f-c7e6-3f72-889d-26513513bb93","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"8b428e5a-ac8e-38f6-a93c-14dd4a05add0"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 03069d20-d6f2-35d9-a71c-b289d5cb065b | 2026-08-22T08:49:31Z | 2026-08-22T08:49:41Z | `{"originalEventId":"6f60bbcb-c603-3bc1-b01c-e19de89cf0e1","correlationId":"82cf6d8f-c7e6-3f72-889d-26513513bb93","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"88045099-9058-313f-a828-d56a9fb2671c","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"88045099-9058-313f-a828-d56a9fb2671c","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"88045099-9058-313f-a828-d56a9fb2671c","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"88045099-9058-313f-a828-d56a9fb2671c","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"88045099-9058-313f-a828-d56a9fb2671c","source":"TRANSFERS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"88045099-9058-313f-a828-d56a9fb2671c","source":"LOANS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"88045099-9058-313f-a828-d56a9fb2671c","source":"INVENTORY","status":"UNAVAILABLE","completeFrom":null,"completeTo":null}`

**Notes opérateur (`operatorNotes`)**
- `{"noteId":"5d59541d-84a9-3edd-ba27-e8463e66b63b","copyId":"88045099-9058-313f-a828-d56a9fb2671c","text":"Le passage au rayon n'a pas permis de retrouver cet exemplaire ; contrôle complémentaire demandé.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"88045099-9058-313f-a828-d56a9fb2671c"},{"kind":"sourceEvent","id":"efea5b3b-b5af-3545-8c45-ccf2d1fd56e4"},{"kind":"sourceEvent","id":"463e27d5-9f76-3fa5-ab4f-7c69ed43bcb3"},{"kind":"sourceEvent","id":"db5de0fe-a0b3-3e36-bd02-1bb48c7d80df"},{"kind":"sourceEvent","id":"04896c10-e455-317a-a60b-210e8c97ff00"},{"kind":"sourceEvent","id":"42c49056-7811-3f4e-a005-d5309caa8fb6"},{"kind":"sourceEvent","id":"6f60bbcb-c603-3bc1-b01c-e19de89cf0e1"},{"kind":"sourceEvent","id":"03069d20-d6f2-35d9-a71c-b289d5cb065b"},{"kind":"sourceAvailability","id":"88045099-9058-313f-a828-d56a9fb2671c/LOANS"},{"kind":"sourceAvailability","id":"88045099-9058-313f-a828-d56a9fb2671c/RETURNS"},{"kind":"sourceAvailability","id":"88045099-9058-313f-a828-d56a9fb2671c/RECEIPTS"},{"kind":"sourceAvailability","id":"88045099-9058-313f-a828-d56a9fb2671c/TRANSFERS"},{"kind":"sourceAvailability","id":"88045099-9058-313f-a828-d56a9fb2671c/REPAIRS"},{"kind":"sourceAvailability","id":"88045099-9058-313f-a828-d56a9fb2671c/INTEGRATIONS"},{"kind":"sourceAvailability","id":"88045099-9058-313f-a828-d56a9fb2671c/INVENTORY"},{"kind":"operatorNote","id":"5d59541d-84a9-3edd-ba27-e8463e66b63b"},{"kind":"inventoryScope","id":"7d46c7a4-f137-377d-8437-5d11eeb4049d"},{"kind":"inventoryScope","id":"66954879-6228-3c31-bf72-7b217d3c9695"},{"kind":"inventoryScope","id":"2a6f7727-71d3-3a12-96f6-4b5e2b38ce02"},{"kind":"inventoryScope","id":"c0755c3b-6689-3d3d-8527-6c90e6c6e5d5"},{"kind":"inventoryScope","id":"3879a968-0f8b-33bb-b62b-63ec36357fbd"},{"kind":"inventoryScope","id":"4c347ad9-c499-38ba-af54-54e88e2f7e28"},{"kind":"inventoryScope","id":"4c347ad9-c499-38ba-af54-54e88e2f7e28"}]`

- Session : `{"scopeId":"7d46c7a4-f137-377d-8437-5d11eeb4049d","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"66954879-6228-3c31-bf72-7b217d3c9695","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2a6f7727-71d3-3a12-96f6-4b5e2b38ce02","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"c0755c3b-6689-3d3d-8527-6c90e6c6e5d5","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"3879a968-0f8b-33bb-b62b-63ec36357fbd","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4c347ad9-c499-38ba-af54-54e88e2f7e28","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"4c347ad9-c499-38ba-af54-54e88e2f7e28","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 1587dc9f-42ee-3f24-9e1d-3854f17c3a7c — dossier d'écart

- Exemplaire : `f9a79871-5a0c-36f5-8276-f4b4d02ea5c2`
- Famille privée : DEUX_CAUSES ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"ff6e5dca-30b0-3e2c-a714-426eb4e66248","kind":"STATUS_MISMATCH","expectedValue":"IN_REPAIR","observedValue":"IN_REPAIR"},{"symptomId":"88fbd4f9-04fb-34f3-b7ed-98ea4161f7f4","kind":"LOCATION_MISMATCH","expectedValue":"14f2d626-9459-3fee-9d3a-daf738f7e59a","observedValue":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["REPAIR_LOCATION_CHANGED"],"hiddenSourceEvents":[],"mechanism":"Réparation engagée puis déplacement distinct de l'atelier vers la réserve sans mise à jour de l'affectation de réparation.","businessEventId":"20f41d03-7eeb-38c5-93fb-5043e198e946","physicalStatus":"IN_REPAIR","physicalSiteId":"CENTRE","physicalLocationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_LOCATION_MISMATCH"],"explanation":"Réparation correctement projetée après reprise ; seul le déplacement hors atelier reste étayé.","forbiddenClaims":["Deux causes confirmées par un scan ambigu","Vol"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"f9a79871-5a0c-36f5-8276-f4b4d02ea5c2","status":"IN_REPAIR","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","sourceEventIds":["20f41d03-7eeb-38c5-93fb-5043e198e946","8202af06-6832-3a1e-bb00-095944c374ff"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 4687ba54-ffa2-3075-985b-a6232a7bf045 | 2026-06-02T11:07:57Z | 2026-06-02T11:08:07Z | `{"operationId":"96540d94-2b87-37c8-abc1-741ca07c9be9","correlationId":"8c7a2ecc-f75c-3f1d-8114-95a1de8c7778","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"a07051f6-cb2a-3e07-90b7-435b993c7d7d"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 24a43205-696a-3385-8423-6a5d4022420c | 2026-06-03T11:07:57Z | 2026-06-03T11:08:07Z | `{"originalEventId":"4687ba54-ffa2-3075-985b-a6232a7bf045","correlationId":"8c7a2ecc-f75c-3f1d-8114-95a1de8c7778","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 8d5bc36c-5007-3c0c-9df3-9052c8685ace | 2026-08-01T11:07:57Z | 2026-08-01T11:08:07Z | `{"operationId":"83e2f51d-9a63-3759-8360-54843f3895aa","correlationId":"245ed091-dbf1-3296-9b3b-bf9a024ba247","siteId":"CENTRE","locationId":null,"loanId":"d802ffe6-3a86-3233-b269-5402e5c3a23d"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 812aae43-213b-3bd1-9ae0-956d4d802de4 | 2026-08-01T11:08:57Z | 2026-08-01T11:09:07Z | `{"originalEventId":"8d5bc36c-5007-3c0c-9df3-9052c8685ace","correlationId":"245ed091-dbf1-3296-9b3b-bf9a024ba247","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 94fc0a2b-bc15-38ff-b874-02b7388e5082 | 2026-08-21T11:07:57Z | 2026-08-21T11:08:07Z | `{"operationId":"29b31f16-a9dc-38fb-910f-ed98691a9da8","correlationId":"b0173043-b8da-3f37-adc1-ecc2cd7e6134","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"d802ffe6-3a86-3233-b269-5402e5c3a23d"}` |
| Retour reçu (`ReturnReceived`) | 33f12231-dcc7-3406-aecd-74ca72d24b56 | 2026-08-21T11:08:27Z | 2026-08-21T11:08:37Z | `{"operationId":"d1988fb0-37bc-369f-b42e-15ab0b2d348a","correlationId":"f40d8b3e-531c-33d7-8d35-3d9502cfb7ad","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"d802ffe6-3a86-3233-b269-5402e5c3a23d"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | e3a1a7f1-c8fc-336e-b434-8d507f0f4953 | 2026-08-22T11:07:57Z | 2026-08-22T11:08:07Z | `{"originalEventId":"33f12231-dcc7-3406-aecd-74ca72d24b56","correlationId":"f40d8b3e-531c-33d7-8d35-3d9502cfb7ad","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Réparation engagée (`RepairStarted`) | 20f41d03-7eeb-38c5-93fb-5043e198e946 | 2026-09-27T07:06:43Z | 2026-09-27T07:06:53Z | `{"operationId":"757a2140-d935-3819-9dc4-aaadf38a54b8","correlationId":"e875be05-9ee5-398b-9195-80bdc3be13db","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","repairId":"781b83da-ff72-3514-9f49-a574981710d3"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | ab67754a-0396-3395-bd45-4143a1cd9dca | 2026-09-27T07:07:13Z | 2026-09-27T07:07:23Z | `{"originalEventId":"20f41d03-7eeb-38c5-93fb-5043e198e946","correlationId":"e875be05-9ee5-398b-9195-80bdc3be13db","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 8202af06-6832-3a1e-bb00-095944c374ff | 2026-09-27T07:08:13Z | 2026-09-27T07:08:23Z | `{"originalEventId":"20f41d03-7eeb-38c5-93fb-5043e198e946","correlationId":"e875be05-9ee5-398b-9195-80bdc3be13db","outcome":"APPLIED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"5d3e6311-fbfa-3295-9b28-63f4de9f65b4","copyId":"f9a79871-5a0c-36f5-8276-f4b4d02ea5c2","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","scopeId":"eabf2185-4c56-34ae-818c-636ef86c2810","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"f9a79871-5a0c-36f5-8276-f4b4d02ea5c2","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f9a79871-5a0c-36f5-8276-f4b4d02ea5c2","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f9a79871-5a0c-36f5-8276-f4b4d02ea5c2","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f9a79871-5a0c-36f5-8276-f4b4d02ea5c2","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f9a79871-5a0c-36f5-8276-f4b4d02ea5c2","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f9a79871-5a0c-36f5-8276-f4b4d02ea5c2","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f9a79871-5a0c-36f5-8276-f4b4d02ea5c2","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"f9a79871-5a0c-36f5-8276-f4b4d02ea5c2"},{"kind":"sourceEvent","id":"4687ba54-ffa2-3075-985b-a6232a7bf045"},{"kind":"sourceEvent","id":"24a43205-696a-3385-8423-6a5d4022420c"},{"kind":"sourceEvent","id":"8d5bc36c-5007-3c0c-9df3-9052c8685ace"},{"kind":"sourceEvent","id":"812aae43-213b-3bd1-9ae0-956d4d802de4"},{"kind":"sourceEvent","id":"94fc0a2b-bc15-38ff-b874-02b7388e5082"},{"kind":"sourceEvent","id":"33f12231-dcc7-3406-aecd-74ca72d24b56"},{"kind":"sourceEvent","id":"e3a1a7f1-c8fc-336e-b434-8d507f0f4953"},{"kind":"sourceEvent","id":"20f41d03-7eeb-38c5-93fb-5043e198e946"},{"kind":"sourceEvent","id":"ab67754a-0396-3395-bd45-4143a1cd9dca"},{"kind":"sourceEvent","id":"8202af06-6832-3a1e-bb00-095944c374ff"},{"kind":"observation","id":"5d3e6311-fbfa-3295-9b28-63f4de9f65b4"},{"kind":"inventoryScope","id":"eabf2185-4c56-34ae-818c-636ef86c2810"},{"kind":"sourceAvailability","id":"f9a79871-5a0c-36f5-8276-f4b4d02ea5c2/LOANS"},{"kind":"sourceAvailability","id":"f9a79871-5a0c-36f5-8276-f4b4d02ea5c2/RETURNS"},{"kind":"sourceAvailability","id":"f9a79871-5a0c-36f5-8276-f4b4d02ea5c2/RECEIPTS"},{"kind":"sourceAvailability","id":"f9a79871-5a0c-36f5-8276-f4b4d02ea5c2/TRANSFERS"},{"kind":"sourceAvailability","id":"f9a79871-5a0c-36f5-8276-f4b4d02ea5c2/REPAIRS"},{"kind":"sourceAvailability","id":"f9a79871-5a0c-36f5-8276-f4b4d02ea5c2/INTEGRATIONS"},{"kind":"sourceAvailability","id":"f9a79871-5a0c-36f5-8276-f4b4d02ea5c2/INVENTORY"},{"kind":"inventoryScope","id":"ec50a541-89c5-30b3-950e-99a9f4dfc9f5"},{"kind":"inventoryScope","id":"08896ffd-fe25-3d58-800a-49293c383852"},{"kind":"inventoryScope","id":"d60e9f22-40a9-3088-8d67-f59de1f0f246"},{"kind":"inventoryScope","id":"820c3d0f-aace-30c6-b8e1-be88b5aeb758"},{"kind":"inventoryScope","id":"c2a743d7-550e-32a1-ba48-e4ea1c20120b"},{"kind":"inventoryScope","id":"bdfc1cac-117d-307c-9360-9fffd70d003f"},{"kind":"inventoryScope","id":"f49b4714-c5cb-3a9d-8630-8a0124b59558"},{"kind":"inventoryScope","id":"34e1ac84-1292-3186-a2ad-f2132554fb00"}]`

- Session : `{"scopeId":"eabf2185-4c56-34ae-818c-636ef86c2810","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"ec50a541-89c5-30b3-950e-99a9f4dfc9f5","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"08896ffd-fe25-3d58-800a-49293c383852","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"d60e9f22-40a9-3088-8d67-f59de1f0f246","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"820c3d0f-aace-30c6-b8e1-be88b5aeb758","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"c2a743d7-550e-32a1-ba48-e4ea1c20120b","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"bdfc1cac-117d-307c-9360-9fffd70d003f","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"f49b4714-c5cb-3a9d-8630-8a0124b59558","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"34e1ac84-1292-3186-a2ad-f2132554fb00","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 1b59c33c-17c7-3a90-8e51-7b5021c8f7fb — dossier d'écart

- Exemplaire : `9744e631-3592-346d-97d8-4aa6474d9664`
- Famille privée : ABSENCE_INEXPLIQUEE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"85fd8bbd-0911-34a2-ae79-9d152e07d736","kind":"MISSING","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["UNOBSERVED_MOVE"],"mechanism":"Exemplaire déplacé hors du périmètre instrumenté ; la destination n'est pas exposée et aucune perte n'est simulée.","physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":null,"evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"L'absence de lecture ne démontre ni la destination, ni une perte, ni un vol ; données insuffisantes pour attribuer une cause.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État du stock informatique : `{"copyId":"9744e631-3592-346d-97d8-4aa6474d9664","status":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","sourceEventIds":["af41ce87-44f4-35d1-aefe-78acce5a2cbc","b662fdbe-ac83-33dd-9cce-e4cf7009b694"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 95df1ac1-3b2d-38d1-b055-47888eac53f6 | 2026-06-01T14:23:11Z | 2026-06-01T14:23:21Z | `{"operationId":"1aceec2e-47a5-334d-85ae-132354acbab3","correlationId":"e1ca51ae-eaec-37b9-97cd-0fd623b2d582","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"b5b1fb73-69f3-3758-8bc8-1ed858815d5c"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 85abca69-b05f-317c-ab43-f5a59b7be2e5 | 2026-06-02T14:23:11Z | 2026-06-02T14:23:21Z | `{"originalEventId":"95df1ac1-3b2d-38d1-b055-47888eac53f6","correlationId":"e1ca51ae-eaec-37b9-97cd-0fd623b2d582","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 05c766a5-6bd7-3bab-a808-722402bbe574 | 2026-07-31T14:23:11Z | 2026-07-31T14:23:21Z | `{"operationId":"3b90cbb0-9902-357f-aa91-31681a0a76d5","correlationId":"245e912f-74a2-3d43-aca8-d5698339361d","siteId":"CENTRE","locationId":null,"loanId":"37cbacc8-91cb-3fd5-82a7-3a3c7c1743d5"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 5cda559e-b886-39ce-8ab8-80ca88db0f41 | 2026-07-31T14:24:11Z | 2026-07-31T14:24:21Z | `{"originalEventId":"05c766a5-6bd7-3bab-a808-722402bbe574","correlationId":"245e912f-74a2-3d43-aca8-d5698339361d","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 92078592-8d4f-363f-891d-2f007881cc86 | 2026-08-20T14:23:11Z | 2026-08-20T14:23:21Z | `{"operationId":"19eaf7ff-8a23-3ebe-b577-55930990c541","correlationId":"f45eeff3-1090-34aa-974e-99cc3c4894f4","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"37cbacc8-91cb-3fd5-82a7-3a3c7c1743d5"}` |
| Retour reçu (`ReturnReceived`) | b068ef68-28fd-3a9f-b8d5-7820366ab7b1 | 2026-08-20T14:23:41Z | 2026-08-20T14:23:51Z | `{"operationId":"67b290b2-fc0a-35d4-b51c-31bd73ee303d","correlationId":"87651443-87d5-346f-b6eb-71505a681e84","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"37cbacc8-91cb-3fd5-82a7-3a3c7c1743d5"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | a72bd3f2-1fde-3b22-afa5-35a8c84b59a0 | 2026-08-21T14:23:11Z | 2026-08-21T14:23:21Z | `{"originalEventId":"b068ef68-28fd-3a9f-b8d5-7820366ab7b1","correlationId":"87651443-87d5-346f-b6eb-71505a681e84","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Transfert expédié (`TransferDispatched`) | bb785dab-7f44-3063-9a8b-09162cf20807 | 2026-08-30T14:23:11Z | 2026-08-30T14:23:21Z | `{"operationId":"06eb8afe-f998-35fe-b9ea-c4016b5fe5da","correlationId":"8b34bfaf-08bf-3549-a9db-1cf8f4aea44e","siteId":"CENTRE","locationId":null,"transferId":"d0b8a7a9-2f33-32de-aecc-1e7a8bc45f93","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | efe5ad25-47fb-3bbc-a9a2-1723bf304924 | 2026-08-30T14:24:11Z | 2026-08-30T14:24:21Z | `{"originalEventId":"bb785dab-7f44-3063-9a8b-09162cf20807","correlationId":"8b34bfaf-08bf-3549-a9db-1cf8f4aea44e","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| Transfert reçu (`TransferReceived`) | af41ce87-44f4-35d1-aefe-78acce5a2cbc | 2026-08-31T14:23:11Z | 2026-08-31T14:23:21Z | `{"operationId":"b3088ad0-9fbb-3157-b9a1-213ca6d66f02","correlationId":"fdb75aaf-1a45-3fe9-bc09-5110fa261300","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","transferId":"d0b8a7a9-2f33-32de-aecc-1e7a8bc45f93","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | b662fdbe-ac83-33dd-9cce-e4cf7009b694 | 2026-09-01T14:23:11Z | 2026-09-01T14:23:21Z | `{"originalEventId":"af41ce87-44f4-35d1-aefe-78acce5a2cbc","correlationId":"fdb75aaf-1a45-3fe9-bc09-5110fa261300","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"9744e631-3592-346d-97d8-4aa6474d9664","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9744e631-3592-346d-97d8-4aa6474d9664","source":"LOANS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"9744e631-3592-346d-97d8-4aa6474d9664","source":"TRANSFERS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"9744e631-3592-346d-97d8-4aa6474d9664","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9744e631-3592-346d-97d8-4aa6474d9664","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9744e631-3592-346d-97d8-4aa6474d9664","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9744e631-3592-346d-97d8-4aa6474d9664","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**
- `{"noteId":"34ae9f64-574e-3ea3-80c0-2cf6cf986100","copyId":"9744e631-3592-346d-97d8-4aa6474d9664","text":"Le passage au rayon n'a pas permis de retrouver cet exemplaire ; contrôle complémentaire demandé.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"9744e631-3592-346d-97d8-4aa6474d9664"},{"kind":"sourceEvent","id":"95df1ac1-3b2d-38d1-b055-47888eac53f6"},{"kind":"sourceEvent","id":"85abca69-b05f-317c-ab43-f5a59b7be2e5"},{"kind":"sourceEvent","id":"05c766a5-6bd7-3bab-a808-722402bbe574"},{"kind":"sourceEvent","id":"5cda559e-b886-39ce-8ab8-80ca88db0f41"},{"kind":"sourceEvent","id":"92078592-8d4f-363f-891d-2f007881cc86"},{"kind":"sourceEvent","id":"b068ef68-28fd-3a9f-b8d5-7820366ab7b1"},{"kind":"sourceEvent","id":"a72bd3f2-1fde-3b22-afa5-35a8c84b59a0"},{"kind":"sourceEvent","id":"bb785dab-7f44-3063-9a8b-09162cf20807"},{"kind":"sourceEvent","id":"efe5ad25-47fb-3bbc-a9a2-1723bf304924"},{"kind":"sourceEvent","id":"af41ce87-44f4-35d1-aefe-78acce5a2cbc"},{"kind":"sourceEvent","id":"b662fdbe-ac83-33dd-9cce-e4cf7009b694"},{"kind":"sourceAvailability","id":"9744e631-3592-346d-97d8-4aa6474d9664/LOANS"},{"kind":"sourceAvailability","id":"9744e631-3592-346d-97d8-4aa6474d9664/RETURNS"},{"kind":"sourceAvailability","id":"9744e631-3592-346d-97d8-4aa6474d9664/RECEIPTS"},{"kind":"sourceAvailability","id":"9744e631-3592-346d-97d8-4aa6474d9664/TRANSFERS"},{"kind":"sourceAvailability","id":"9744e631-3592-346d-97d8-4aa6474d9664/REPAIRS"},{"kind":"sourceAvailability","id":"9744e631-3592-346d-97d8-4aa6474d9664/INTEGRATIONS"},{"kind":"sourceAvailability","id":"9744e631-3592-346d-97d8-4aa6474d9664/INVENTORY"},{"kind":"operatorNote","id":"34ae9f64-574e-3ea3-80c0-2cf6cf986100"},{"kind":"inventoryScope","id":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5"},{"kind":"inventoryScope","id":"eb1ce5f1-7397-3b45-9385-ef328e09fec8"},{"kind":"inventoryScope","id":"d0c55bda-319d-3537-857a-5d135f796cab"},{"kind":"inventoryScope","id":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb"},{"kind":"inventoryScope","id":"d9842c03-ba37-324b-b71b-e58ac7147d4c"},{"kind":"inventoryScope","id":"07bae854-b205-39bf-a23e-9eaa22063b92"},{"kind":"inventoryScope","id":"9de706b4-d360-3282-b440-c440861c039c"},{"kind":"inventoryScope","id":"b98af8e5-7297-3dc5-a947-eb8fe8af159f"},{"kind":"inventoryScope","id":"7eb96d9b-e927-305a-b369-01d6b239f3db"},{"kind":"inventoryScope","id":"b4a97852-2336-3ae2-ab5b-30b6e2e4d536"},{"kind":"inventoryScope","id":"b4a97852-2336-3ae2-ab5b-30b6e2e4d536"}]`

- Session : `{"scopeId":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"eb1ce5f1-7397-3b45-9385-ef328e09fec8","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"d0c55bda-319d-3537-857a-5d135f796cab","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"d9842c03-ba37-324b-b71b-e58ac7147d4c","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"07bae854-b205-39bf-a23e-9eaa22063b92","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"9de706b4-d360-3282-b440-c440861c039c","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b98af8e5-7297-3dc5-a947-eb8fe8af159f","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"7eb96d9b-e927-305a-b369-01d6b239f3db","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b4a97852-2336-3ae2-ab5b-30b6e2e4d536","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b4a97852-2336-3ae2-ab5b-30b6e2e4d536","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 23f47f0d-2c13-3a13-b78f-b7710c6e10b7 — dossier d'écart

- Exemplaire : `4ee4a526-2d07-32a7-b8f8-f3a4681b8e94`
- Famille privée : TRANSFERT_INCOHERENT ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"64266c5f-efcb-37fd-a2dd-e7b7d50939b5","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"42ac7916-1875-3f32-9201-744cfbd8f24c","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"4ee4a526-2d07-32a7-b8f8-f3a4681b8e94","status":"RECEIVING","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","sourceEventIds":["42ac7916-1875-3f32-9201-744cfbd8f24c","29f463fc-212b-3945-9049-0a469072105f"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 3496a68b-0e3b-3884-9050-bdeca812e721 | 2026-06-02T08:38:51Z | 2026-06-02T08:39:01Z | `{"operationId":"c73a18b6-24a9-31e4-a07b-566e0ca30e84","correlationId":"82f82502-94ee-3fc5-82c3-e1fdec33267c","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"1a08c916-00bf-3147-b61a-aad9c3b05d81"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 0ec01473-cdc6-3b17-8f8e-e55d588067bc | 2026-06-03T08:38:51Z | 2026-06-03T08:39:01Z | `{"originalEventId":"3496a68b-0e3b-3884-9050-bdeca812e721","correlationId":"82f82502-94ee-3fc5-82c3-e1fdec33267c","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 4dadc075-d0c4-3d5b-8fcd-e3307fa08486 | 2026-08-01T08:38:51Z | 2026-08-01T08:39:01Z | `{"operationId":"82aecbb9-d37c-343a-a39a-e37cbcd45193","correlationId":"141cdbcc-fcc8-316f-a5e4-fc4c1b6f189d","siteId":"SUD","locationId":null,"loanId":"08377088-b8a0-3dd2-9dfd-0942aeca4c9f"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 7c0c973e-2abf-3d05-ae07-112700b8ebe2 | 2026-08-01T08:39:51Z | 2026-08-01T08:40:01Z | `{"originalEventId":"4dadc075-d0c4-3d5b-8fcd-e3307fa08486","correlationId":"141cdbcc-fcc8-316f-a5e4-fc4c1b6f189d","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 3d7971ad-112e-3285-956e-9eb46245fdf2 | 2026-08-21T08:38:51Z | 2026-08-21T08:39:01Z | `{"operationId":"d5f07956-1b44-34fa-b019-2cf9fad63e60","correlationId":"9f7f6941-8174-3bc0-be79-ab17bea202df","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"08377088-b8a0-3dd2-9dfd-0942aeca4c9f"}` |
| Retour reçu (`ReturnReceived`) | 8144d253-19ac-37dc-b4b2-aba088d83353 | 2026-08-21T08:39:21Z | 2026-08-21T08:39:31Z | `{"operationId":"3878e019-7f1e-38ce-b9df-f2e889b114bd","correlationId":"453b89ab-f237-3bb0-9ba9-d499a73ea367","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"08377088-b8a0-3dd2-9dfd-0942aeca4c9f"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 7b9891af-3319-3639-b6f5-2a2d475f8914 | 2026-08-22T08:38:51Z | 2026-08-22T08:39:01Z | `{"originalEventId":"8144d253-19ac-37dc-b4b2-aba088d83353","correlationId":"453b89ab-f237-3bb0-9ba9-d499a73ea367","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Transfert expédié (`TransferDispatched`) | 41db7d0b-77c3-32ca-99e5-f12f1d09b471 | 2026-08-31T08:38:51Z | 2026-08-31T08:39:01Z | `{"operationId":"ace76a17-b7dd-3203-9403-fb6e3b218faf","correlationId":"176c2501-0820-35a3-b6c0-5e5a06d07858","siteId":"SUD","locationId":null,"transferId":"7699cea7-09fd-3869-8bb4-e2af30845d29","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | b87c0c47-2b81-3be2-acfc-85329c762ccd | 2026-08-31T08:39:51Z | 2026-08-31T08:40:01Z | `{"originalEventId":"41db7d0b-77c3-32ca-99e5-f12f1d09b471","correlationId":"176c2501-0820-35a3-b6c0-5e5a06d07858","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"SUD","locationId":null}` |
| Transfert reçu (`TransferReceived`) | e2512443-1c10-36d2-bc57-7ae1919dcdb3 | 2026-09-01T08:38:51Z | 2026-09-01T08:39:01Z | `{"operationId":"62e92c59-9a84-3bcb-87ca-29b55aa2294b","correlationId":"e576e3a0-d487-3ca3-8554-afe6132172ba","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","transferId":"7699cea7-09fd-3869-8bb4-e2af30845d29","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | c7894666-e11e-36b5-9084-9b400701ab42 | 2026-09-02T08:38:51Z | 2026-09-02T08:39:01Z | `{"originalEventId":"e2512443-1c10-36d2-bc57-7ae1919dcdb3","correlationId":"e576e3a0-d487-3ca3-8554-afe6132172ba","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Transfert expédié (`TransferDispatched`) | 5c339246-2912-3224-8a21-69f34771295d | 2026-09-26T11:51:57Z | 2026-09-26T11:52:07Z | `{"operationId":"35918631-fa6f-331b-8598-d9ca61591ff8","correlationId":"b0e2344c-e7b5-3f2a-8fbb-96110a1b5835","siteId":"CENTRE","locationId":null,"transferId":"a13be920-47f2-3d07-b461-b77fdb164331","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 482903ae-e128-3205-b23c-8bff3444e74f | 2026-09-26T11:52:57Z | 2026-09-26T11:53:07Z | `{"originalEventId":"5c339246-2912-3224-8a21-69f34771295d","correlationId":"b0e2344c-e7b5-3f2a-8fbb-96110a1b5835","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| Transfert reçu (`TransferReceived`) | 42ac7916-1875-3f32-9201-744cfbd8f24c | 2026-09-27T11:51:57Z | 2026-09-27T11:52:07Z | `{"operationId":"6a78bdca-3258-3667-8075-82aeaa060b06","correlationId":"749329e7-0b5f-380c-9e4f-881488efd464","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","transferId":"a13be920-47f2-3d07-b461-b77fdb164331","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | f6d21f40-b2a4-3adf-8f83-122b05c3e93d | 2026-09-27T11:52:27Z | 2026-09-27T11:52:37Z | `{"originalEventId":"42ac7916-1875-3f32-9201-744cfbd8f24c","correlationId":"749329e7-0b5f-380c-9e4f-881488efd464","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 29f463fc-212b-3945-9049-0a469072105f | 2026-09-27T11:53:27Z | 2026-09-27T11:53:37Z | `{"originalEventId":"42ac7916-1875-3f32-9201-744cfbd8f24c","correlationId":"749329e7-0b5f-380c-9e4f-881488efd464","outcome":"APPLIED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"e0f91520-af65-304f-9574-55137c31afdf","copyId":"4ee4a526-2d07-32a7-b8f8-f3a4681b8e94","locationId":"2cf9a5ef-0690-3b0f-9031-67da0dd9d884","scopeId":"a2fa9bf6-5cac-320e-ab7e-f091a5f93b04","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`
- `{"observationId":"78b30b6c-25e8-34d3-9370-dd9d230615f4","copyId":"4ee4a526-2d07-32a7-b8f8-f3a4681b8e94","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","scopeId":"4f5d7609-3713-3c2c-a8a6-b5bad10966de","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"4ee4a526-2d07-32a7-b8f8-f3a4681b8e94","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4ee4a526-2d07-32a7-b8f8-f3a4681b8e94","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4ee4a526-2d07-32a7-b8f8-f3a4681b8e94","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4ee4a526-2d07-32a7-b8f8-f3a4681b8e94","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4ee4a526-2d07-32a7-b8f8-f3a4681b8e94","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4ee4a526-2d07-32a7-b8f8-f3a4681b8e94","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4ee4a526-2d07-32a7-b8f8-f3a4681b8e94","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"4ee4a526-2d07-32a7-b8f8-f3a4681b8e94"},{"kind":"sourceEvent","id":"3496a68b-0e3b-3884-9050-bdeca812e721"},{"kind":"sourceEvent","id":"0ec01473-cdc6-3b17-8f8e-e55d588067bc"},{"kind":"sourceEvent","id":"4dadc075-d0c4-3d5b-8fcd-e3307fa08486"},{"kind":"sourceEvent","id":"7c0c973e-2abf-3d05-ae07-112700b8ebe2"},{"kind":"sourceEvent","id":"3d7971ad-112e-3285-956e-9eb46245fdf2"},{"kind":"sourceEvent","id":"8144d253-19ac-37dc-b4b2-aba088d83353"},{"kind":"sourceEvent","id":"7b9891af-3319-3639-b6f5-2a2d475f8914"},{"kind":"sourceEvent","id":"41db7d0b-77c3-32ca-99e5-f12f1d09b471"},{"kind":"sourceEvent","id":"b87c0c47-2b81-3be2-acfc-85329c762ccd"},{"kind":"sourceEvent","id":"e2512443-1c10-36d2-bc57-7ae1919dcdb3"},{"kind":"sourceEvent","id":"c7894666-e11e-36b5-9084-9b400701ab42"},{"kind":"sourceEvent","id":"5c339246-2912-3224-8a21-69f34771295d"},{"kind":"sourceEvent","id":"482903ae-e128-3205-b23c-8bff3444e74f"},{"kind":"sourceEvent","id":"42ac7916-1875-3f32-9201-744cfbd8f24c"},{"kind":"sourceEvent","id":"f6d21f40-b2a4-3adf-8f83-122b05c3e93d"},{"kind":"sourceEvent","id":"29f463fc-212b-3945-9049-0a469072105f"},{"kind":"observation","id":"78b30b6c-25e8-34d3-9370-dd9d230615f4"},{"kind":"inventoryScope","id":"4f5d7609-3713-3c2c-a8a6-b5bad10966de"},{"kind":"observation","id":"e0f91520-af65-304f-9574-55137c31afdf"},{"kind":"inventoryScope","id":"a2fa9bf6-5cac-320e-ab7e-f091a5f93b04"},{"kind":"sourceAvailability","id":"4ee4a526-2d07-32a7-b8f8-f3a4681b8e94/LOANS"},{"kind":"sourceAvailability","id":"4ee4a526-2d07-32a7-b8f8-f3a4681b8e94/RETURNS"},{"kind":"sourceAvailability","id":"4ee4a526-2d07-32a7-b8f8-f3a4681b8e94/RECEIPTS"},{"kind":"sourceAvailability","id":"4ee4a526-2d07-32a7-b8f8-f3a4681b8e94/TRANSFERS"},{"kind":"sourceAvailability","id":"4ee4a526-2d07-32a7-b8f8-f3a4681b8e94/REPAIRS"},{"kind":"sourceAvailability","id":"4ee4a526-2d07-32a7-b8f8-f3a4681b8e94/INTEGRATIONS"},{"kind":"sourceAvailability","id":"4ee4a526-2d07-32a7-b8f8-f3a4681b8e94/INVENTORY"},{"kind":"inventoryScope","id":"d1e77ae7-a841-38e1-b16e-d9b468a2d87a"},{"kind":"inventoryScope","id":"2d080b55-2867-3e1b-a766-49afaacecf82"},{"kind":"inventoryScope","id":"ff7cadbd-2e34-31a2-a383-62602b9d45d5"},{"kind":"inventoryScope","id":"dc947621-590d-3bee-a9d2-851a4ba45547"}]`

- Session : `{"scopeId":"4f5d7609-3713-3c2c-a8a6-b5bad10966de","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"a2fa9bf6-5cac-320e-ab7e-f091a5f93b04","locationId":"2cf9a5ef-0690-3b0f-9031-67da0dd9d884","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"d1e77ae7-a841-38e1-b16e-d9b468a2d87a","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2d080b55-2867-3e1b-a766-49afaacecf82","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"ff7cadbd-2e34-31a2-a383-62602b9d45d5","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"dc947621-590d-3bee-a9d2-851a4ba45547","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 24139b3a-c6f0-3215-b28f-a983e97eb96c — dossier d'écart

- Exemplaire : `a3876f7e-c42a-355f-9d06-0a12261b08b2`
- Famille privée : LOCALISATION ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"35e734c5-8a07-3256-bd45-69cea35b714e","kind":"COUNT_UNCERTAIN","expectedValue":"00f09742-c706-31b7-a4a0-c694a087403f","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"mechanism":"Aucun déplacement ; signal de collecte non confirmé.","activePerturbations":[],"physicalStatus":"ON_SHELF","physicalSiteId":"SUD","physicalLocationId":"00f09742-c706-31b7-a4a0-c694a087403f"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"Le scan valide confirme l'emplacement attendu ; aucun écart de localisation étayé.","forbiddenClaims":["Vol","Perte définitive","Responsabilité d'un opérateur"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"a3876f7e-c42a-355f-9d06-0a12261b08b2","status":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","sourceEventIds":["c8a7e9ba-8f3c-3ed1-bd45-a49ee23ad983","9b1bd203-a133-3784-a1b3-1fa59056ef2d"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 3eec554e-b1b1-3037-bdf7-b142a3456756 | 2026-06-02T04:28:18Z | 2026-06-02T04:28:28Z | `{"operationId":"a4b80ae1-be8e-3faa-88f9-fc3624044d14","correlationId":"849e7998-53aa-32a4-b0a2-f1dfcc96390c","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"a319d11d-8b36-3f51-9cb3-3fecf608fb06"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 198539d8-36a0-3d7a-9a19-51de581017a9 | 2026-06-03T04:28:18Z | 2026-06-03T04:28:28Z | `{"originalEventId":"3eec554e-b1b1-3037-bdf7-b142a3456756","correlationId":"849e7998-53aa-32a4-b0a2-f1dfcc96390c","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | c0447913-8f77-38b6-8897-ee188c0e0056 | 2026-08-01T04:28:18Z | 2026-08-01T04:28:28Z | `{"operationId":"a576b56b-c54f-30ad-8e2e-86a04fc067d7","correlationId":"4ed99476-fa65-333d-8522-ab0adc1860d2","siteId":"SUD","locationId":null,"loanId":"22fcad8e-6412-3e7e-8624-fbe0f217564e"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 42ecc9c2-fffc-35c6-a516-0a3e4d5d114d | 2026-08-01T04:29:18Z | 2026-08-01T04:29:28Z | `{"originalEventId":"c0447913-8f77-38b6-8897-ee188c0e0056","correlationId":"4ed99476-fa65-333d-8522-ab0adc1860d2","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 599c1937-f136-310b-838e-0bbd3b1795c3 | 2026-08-21T04:28:18Z | 2026-08-21T04:28:28Z | `{"operationId":"c154d036-3c21-32bc-a059-30f2ae69b1cd","correlationId":"354e8cd4-e457-33ea-8845-34884e4ae3e6","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"22fcad8e-6412-3e7e-8624-fbe0f217564e"}` |
| Retour reçu (`ReturnReceived`) | c8a7e9ba-8f3c-3ed1-bd45-a49ee23ad983 | 2026-08-21T04:28:48Z | 2026-08-21T04:28:58Z | `{"operationId":"3408f402-49ee-3ff8-b909-825fc0c0e598","correlationId":"4f397863-2623-3669-a350-ade7b9384872","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"22fcad8e-6412-3e7e-8624-fbe0f217564e"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 9b1bd203-a133-3784-a1b3-1fa59056ef2d | 2026-08-22T04:28:18Z | 2026-08-22T04:28:28Z | `{"originalEventId":"c8a7e9ba-8f3c-3ed1-bd45-a49ee23ad983","correlationId":"4f397863-2623-3669-a350-ade7b9384872","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"0f82c120-bef2-3aa6-9a60-1decce1659c8","copyId":"a3876f7e-c42a-355f-9d06-0a12261b08b2","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","scopeId":"202855ec-9065-3be6-a0c2-07ce228ecf21","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"a3876f7e-c42a-355f-9d06-0a12261b08b2","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a3876f7e-c42a-355f-9d06-0a12261b08b2","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a3876f7e-c42a-355f-9d06-0a12261b08b2","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a3876f7e-c42a-355f-9d06-0a12261b08b2","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a3876f7e-c42a-355f-9d06-0a12261b08b2","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a3876f7e-c42a-355f-9d06-0a12261b08b2","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a3876f7e-c42a-355f-9d06-0a12261b08b2","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"observation","id":"0f82c120-bef2-3aa6-9a60-1decce1659c8"},{"kind":"systemState","id":"a3876f7e-c42a-355f-9d06-0a12261b08b2"}]`

## Cas 246e81bb-56c4-3dc2-aa55-37f27b67e2ac — dossier d'écart

- Exemplaire : `ad1bf60d-7d2e-30da-b57e-3fad36387d45`
- Famille privée : DEUX_CAUSES ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"62c69ba9-e203-34aa-840e-dfca04118500","kind":"STATUS_MISMATCH","expectedValue":"IN_REPAIR","observedValue":"ON_SHELF"},{"symptomId":"1967fde8-98a1-37da-aac1-02edaa694fbb","kind":"LOCATION_MISMATCH","expectedValue":"14f2d626-9459-3fee-9d3a-daf738f7e59a","observedValue":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED","REPAIR_LOCATION_CHANGED"],"hiddenSourceEvents":[],"mechanism":"Réparation engagée puis déplacement distinct de l'atelier vers la réserve sans mise à jour de l'affectation de réparation.","businessEventId":"30b641bb-8977-3e06-963f-9f0c561baf43","physicalStatus":"IN_REPAIR","physicalSiteId":"CENTRE","physicalLocationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","projectionPerturbation":"FAILED","evidenceMask":"Scan de réserve ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":["REPAIR_NOT_PROJECTED"],"explanation":"Échec de projection de réparation documenté ; seconde localisation non confirmée par un scan valide.","forbiddenClaims":["Deux causes confirmées par un scan ambigu","Vol"],"recommendedChecks":["Refaire le scan en réserve"]}`
- État du stock informatique : `{"copyId":"ad1bf60d-7d2e-30da-b57e-3fad36387d45","status":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","sourceEventIds":["c33e6955-e9d9-3af0-abe0-969f55a42600","562a5cb0-8717-3df9-bba4-70f301f0433d"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | b0b6f410-d67e-3cbc-9466-f84ccc13148f | 2026-06-01T16:41:14Z | 2026-06-01T16:41:24Z | `{"operationId":"6211d97c-9fdd-3413-97b9-bd359567a060","correlationId":"06d8a5d5-f904-3697-8b68-fae66b51827f","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"de9d4d2c-7d85-358e-9e7f-7f9f6ca382e2"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 35f7e0ce-30f0-3af2-a830-b17ba773862f | 2026-06-02T16:41:14Z | 2026-06-02T16:41:24Z | `{"originalEventId":"b0b6f410-d67e-3cbc-9466-f84ccc13148f","correlationId":"06d8a5d5-f904-3697-8b68-fae66b51827f","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 95ec7cb2-76b1-3ba5-88ae-132d831ccf2f | 2026-07-31T16:41:14Z | 2026-07-31T16:41:24Z | `{"operationId":"4523e2b6-9195-35ab-81e8-dcbd28963301","correlationId":"cd3a51ba-ea8f-3b4f-bc06-2b1d37bb2f1a","siteId":"CENTRE","locationId":null,"loanId":"4667e182-a5b3-30a5-83f4-f53157b7f369"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | d46d5d11-f0ae-3f42-a526-c0cace542854 | 2026-07-31T16:42:14Z | 2026-07-31T16:42:24Z | `{"originalEventId":"95ec7cb2-76b1-3ba5-88ae-132d831ccf2f","correlationId":"cd3a51ba-ea8f-3b4f-bc06-2b1d37bb2f1a","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | dea0963a-a145-32ca-805b-da6dc91a25fe | 2026-08-20T16:41:14Z | 2026-08-20T16:41:24Z | `{"operationId":"ec18d45e-04a7-3bd8-9b05-a55dad04a4ca","correlationId":"07807f84-6c6b-345f-855f-83b88cc3ca77","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"4667e182-a5b3-30a5-83f4-f53157b7f369"}` |
| Retour reçu (`ReturnReceived`) | c33e6955-e9d9-3af0-abe0-969f55a42600 | 2026-08-20T16:41:44Z | 2026-08-20T16:41:54Z | `{"operationId":"e374119f-23c2-3d06-bf02-e4b7bec9710c","correlationId":"0da5e104-91f6-33f9-9019-284f5df03d3a","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"4667e182-a5b3-30a5-83f4-f53157b7f369"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 562a5cb0-8717-3df9-bba4-70f301f0433d | 2026-08-21T16:41:14Z | 2026-08-21T16:41:24Z | `{"originalEventId":"c33e6955-e9d9-3af0-abe0-969f55a42600","correlationId":"0da5e104-91f6-33f9-9019-284f5df03d3a","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Réparation engagée (`RepairStarted`) | 30b641bb-8977-3e06-963f-9f0c561baf43 | 2026-09-27T07:04:24Z | 2026-09-27T07:04:34Z | `{"operationId":"ff03b8f3-fc02-3d95-a0ec-c258f4d1a09a","correlationId":"70af964a-7709-39df-a9c3-eeae9dedc51c","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","repairId":"e56e885c-ce81-3928-9dde-c3f2ab6046ef"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 67a72bc0-1321-35b4-9a0e-f7a3a2ac8273 | 2026-09-27T07:04:54Z | 2026-09-27T07:05:04Z | `{"originalEventId":"30b641bb-8977-3e06-963f-9f0c561baf43","correlationId":"70af964a-7709-39df-a9c3-eeae9dedc51c","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"f8b052df-5133-35d6-9224-70f321d192a4","copyId":"ad1bf60d-7d2e-30da-b57e-3fad36387d45","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","scopeId":"72e273b5-5cd5-3223-a80a-c46752c1cc20","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"ad1bf60d-7d2e-30da-b57e-3fad36387d45","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ad1bf60d-7d2e-30da-b57e-3fad36387d45","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ad1bf60d-7d2e-30da-b57e-3fad36387d45","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ad1bf60d-7d2e-30da-b57e-3fad36387d45","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ad1bf60d-7d2e-30da-b57e-3fad36387d45","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ad1bf60d-7d2e-30da-b57e-3fad36387d45","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ad1bf60d-7d2e-30da-b57e-3fad36387d45","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"ad1bf60d-7d2e-30da-b57e-3fad36387d45"},{"kind":"sourceEvent","id":"b0b6f410-d67e-3cbc-9466-f84ccc13148f"},{"kind":"sourceEvent","id":"35f7e0ce-30f0-3af2-a830-b17ba773862f"},{"kind":"sourceEvent","id":"95ec7cb2-76b1-3ba5-88ae-132d831ccf2f"},{"kind":"sourceEvent","id":"d46d5d11-f0ae-3f42-a526-c0cace542854"},{"kind":"sourceEvent","id":"dea0963a-a145-32ca-805b-da6dc91a25fe"},{"kind":"sourceEvent","id":"c33e6955-e9d9-3af0-abe0-969f55a42600"},{"kind":"sourceEvent","id":"562a5cb0-8717-3df9-bba4-70f301f0433d"},{"kind":"sourceEvent","id":"30b641bb-8977-3e06-963f-9f0c561baf43"},{"kind":"sourceEvent","id":"67a72bc0-1321-35b4-9a0e-f7a3a2ac8273"},{"kind":"observation","id":"f8b052df-5133-35d6-9224-70f321d192a4"},{"kind":"inventoryScope","id":"72e273b5-5cd5-3223-a80a-c46752c1cc20"},{"kind":"sourceAvailability","id":"ad1bf60d-7d2e-30da-b57e-3fad36387d45/LOANS"},{"kind":"sourceAvailability","id":"ad1bf60d-7d2e-30da-b57e-3fad36387d45/RETURNS"},{"kind":"sourceAvailability","id":"ad1bf60d-7d2e-30da-b57e-3fad36387d45/RECEIPTS"},{"kind":"sourceAvailability","id":"ad1bf60d-7d2e-30da-b57e-3fad36387d45/TRANSFERS"},{"kind":"sourceAvailability","id":"ad1bf60d-7d2e-30da-b57e-3fad36387d45/REPAIRS"},{"kind":"sourceAvailability","id":"ad1bf60d-7d2e-30da-b57e-3fad36387d45/INTEGRATIONS"},{"kind":"sourceAvailability","id":"ad1bf60d-7d2e-30da-b57e-3fad36387d45/INVENTORY"},{"kind":"inventoryScope","id":"7d46c7a4-f137-377d-8437-5d11eeb4049d"},{"kind":"inventoryScope","id":"66954879-6228-3c31-bf72-7b217d3c9695"},{"kind":"inventoryScope","id":"2a6f7727-71d3-3a12-96f6-4b5e2b38ce02"},{"kind":"inventoryScope","id":"c0755c3b-6689-3d3d-8527-6c90e6c6e5d5"},{"kind":"inventoryScope","id":"3879a968-0f8b-33bb-b62b-63ec36357fbd"},{"kind":"inventoryScope","id":"4c347ad9-c499-38ba-af54-54e88e2f7e28"}]`

- Session : `{"scopeId":"72e273b5-5cd5-3223-a80a-c46752c1cc20","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"7d46c7a4-f137-377d-8437-5d11eeb4049d","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"66954879-6228-3c31-bf72-7b217d3c9695","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2a6f7727-71d3-3a12-96f6-4b5e2b38ce02","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"c0755c3b-6689-3d3d-8527-6c90e6c6e5d5","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"3879a968-0f8b-33bb-b62b-63ec36357fbd","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4c347ad9-c499-38ba-af54-54e88e2f7e28","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 273d4cf2-65f6-3e7f-97de-7ebfd193298d — dossier d'écart

- Exemplaire : `deaa0af6-cddd-3d4b-847f-48e5f392154d`
- Famille privée : INVENTAIRE_INCOMPLET ; variante : POSITIVE
- Symptômes : `[{"symptomId":"8309127b-4221-3234-b362-60b3ca6847bd","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["COLLECTION_INTERRUPTED"],"mechanism":"Interruption de collecte ; exemplaire resté au rayon.","physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["INVENTORY_INCOMPLETE"],"explanation":"La session interrompue explique l'incertitude de comptage ; la présence physique reste à vérifier.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État du stock informatique : `{"copyId":"deaa0af6-cddd-3d4b-847f-48e5f392154d","status":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","sourceEventIds":["0e3b9fba-2a6f-3093-9392-5d3d472ae248","fd34cdf0-f73f-39c1-9b11-db4fe2a7a57f"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 90d00bbe-74a3-3612-a08a-c2d3846ba046 | 2026-06-02T08:49:21Z | 2026-06-02T08:49:31Z | `{"operationId":"f3641c62-8721-3e54-9421-371e65730340","correlationId":"5960a42f-a3df-3665-a62d-cb30db39c3f6","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"2a038366-69dd-340a-9610-e2da633e7b19"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | e8cccea8-e392-3f52-ad26-41960b77ced6 | 2026-06-03T08:49:21Z | 2026-06-03T08:49:31Z | `{"originalEventId":"90d00bbe-74a3-3612-a08a-c2d3846ba046","correlationId":"5960a42f-a3df-3665-a62d-cb30db39c3f6","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | b0ebbe87-de44-3f02-a7da-da75953c957e | 2026-08-01T08:49:21Z | 2026-08-01T08:49:31Z | `{"operationId":"1844f6cd-e927-3bd7-97e2-6db9ae201761","correlationId":"2e113cc8-34aa-31ed-800d-e557dbcc316b","siteId":"CENTRE","locationId":null,"loanId":"fa4eaca0-86c4-37ca-a330-7137f47d2acd"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | f0a28d0f-9b3b-304c-9379-077d06fb7279 | 2026-08-01T08:50:21Z | 2026-08-01T08:50:31Z | `{"originalEventId":"b0ebbe87-de44-3f02-a7da-da75953c957e","correlationId":"2e113cc8-34aa-31ed-800d-e557dbcc316b","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 29308478-efca-32d0-b169-4cb7cbef54ad | 2026-08-21T08:49:21Z | 2026-08-21T08:49:31Z | `{"operationId":"ee411363-bc32-3569-bf7a-5546ee9e233a","correlationId":"1a668dc9-9cef-39d0-9514-0daf871e3c7e","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"fa4eaca0-86c4-37ca-a330-7137f47d2acd"}` |
| Retour reçu (`ReturnReceived`) | 1485c1d0-026c-3b7a-8021-eebb0d464e22 | 2026-08-21T08:49:51Z | 2026-08-21T08:50:01Z | `{"operationId":"b2a1b245-d7b5-364d-94cb-115400f36450","correlationId":"1453bf04-dd5e-3f57-8e74-0c4ce570513d","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"fa4eaca0-86c4-37ca-a330-7137f47d2acd"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | d1df3963-4c4a-3297-98d4-663446396d64 | 2026-08-22T08:49:21Z | 2026-08-22T08:49:31Z | `{"originalEventId":"1485c1d0-026c-3b7a-8021-eebb0d464e22","correlationId":"1453bf04-dd5e-3f57-8e74-0c4ce570513d","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Transfert expédié (`TransferDispatched`) | d9472a7d-d0d8-3429-8496-5f0e236e1456 | 2026-08-31T08:49:21Z | 2026-08-31T08:49:31Z | `{"operationId":"afcab2a9-1e58-3723-bdc1-0eda85899f4f","correlationId":"7c300181-94ca-3768-83fe-41851a0d1c86","siteId":"CENTRE","locationId":null,"transferId":"9dd8d34c-6416-3784-abf9-eacdee343111","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 309908a9-166b-38da-8eab-1d1b490bb477 | 2026-08-31T08:50:21Z | 2026-08-31T08:50:31Z | `{"originalEventId":"d9472a7d-d0d8-3429-8496-5f0e236e1456","correlationId":"7c300181-94ca-3768-83fe-41851a0d1c86","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| Transfert reçu (`TransferReceived`) | 0e3b9fba-2a6f-3093-9392-5d3d472ae248 | 2026-09-01T08:49:21Z | 2026-09-01T08:49:31Z | `{"operationId":"f4615a5d-5ac5-3545-9569-5bb787d4a687","correlationId":"e3e911f0-3a52-3bb4-b0b4-1898a5aa905d","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","transferId":"9dd8d34c-6416-3784-abf9-eacdee343111","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | fd34cdf0-f73f-39c1-9b11-db4fe2a7a57f | 2026-09-02T08:49:21Z | 2026-09-02T08:49:31Z | `{"originalEventId":"0e3b9fba-2a6f-3093-9392-5d3d472ae248","correlationId":"e3e911f0-3a52-3bb4-b0b4-1898a5aa905d","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"deaa0af6-cddd-3d4b-847f-48e5f392154d","source":"INVENTORY","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"deaa0af6-cddd-3d4b-847f-48e5f392154d","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"deaa0af6-cddd-3d4b-847f-48e5f392154d","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"deaa0af6-cddd-3d4b-847f-48e5f392154d","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"deaa0af6-cddd-3d4b-847f-48e5f392154d","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"deaa0af6-cddd-3d4b-847f-48e5f392154d","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"deaa0af6-cddd-3d4b-847f-48e5f392154d","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**
- `{"noteId":"9c375b89-3776-37b9-bd4f-f28b87b581c6","copyId":"deaa0af6-cddd-3d4b-847f-48e5f392154d","text":"Le terminal de collecte a cessé de répondre pendant le passage ; aucune conclusion sur les exemplaires non lus.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"deaa0af6-cddd-3d4b-847f-48e5f392154d"},{"kind":"sourceEvent","id":"90d00bbe-74a3-3612-a08a-c2d3846ba046"},{"kind":"sourceEvent","id":"e8cccea8-e392-3f52-ad26-41960b77ced6"},{"kind":"sourceEvent","id":"b0ebbe87-de44-3f02-a7da-da75953c957e"},{"kind":"sourceEvent","id":"f0a28d0f-9b3b-304c-9379-077d06fb7279"},{"kind":"sourceEvent","id":"29308478-efca-32d0-b169-4cb7cbef54ad"},{"kind":"sourceEvent","id":"1485c1d0-026c-3b7a-8021-eebb0d464e22"},{"kind":"sourceEvent","id":"d1df3963-4c4a-3297-98d4-663446396d64"},{"kind":"sourceEvent","id":"d9472a7d-d0d8-3429-8496-5f0e236e1456"},{"kind":"sourceEvent","id":"309908a9-166b-38da-8eab-1d1b490bb477"},{"kind":"sourceEvent","id":"0e3b9fba-2a6f-3093-9392-5d3d472ae248"},{"kind":"sourceEvent","id":"fd34cdf0-f73f-39c1-9b11-db4fe2a7a57f"},{"kind":"sourceAvailability","id":"deaa0af6-cddd-3d4b-847f-48e5f392154d/LOANS"},{"kind":"sourceAvailability","id":"deaa0af6-cddd-3d4b-847f-48e5f392154d/RETURNS"},{"kind":"sourceAvailability","id":"deaa0af6-cddd-3d4b-847f-48e5f392154d/RECEIPTS"},{"kind":"sourceAvailability","id":"deaa0af6-cddd-3d4b-847f-48e5f392154d/TRANSFERS"},{"kind":"sourceAvailability","id":"deaa0af6-cddd-3d4b-847f-48e5f392154d/REPAIRS"},{"kind":"sourceAvailability","id":"deaa0af6-cddd-3d4b-847f-48e5f392154d/INTEGRATIONS"},{"kind":"sourceAvailability","id":"deaa0af6-cddd-3d4b-847f-48e5f392154d/INVENTORY"},{"kind":"operatorNote","id":"9c375b89-3776-37b9-bd4f-f28b87b581c6"},{"kind":"inventoryScope","id":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5"},{"kind":"inventoryScope","id":"eb1ce5f1-7397-3b45-9385-ef328e09fec8"},{"kind":"inventoryScope","id":"d0c55bda-319d-3537-857a-5d135f796cab"},{"kind":"inventoryScope","id":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb"},{"kind":"inventoryScope","id":"d9842c03-ba37-324b-b71b-e58ac7147d4c"},{"kind":"inventoryScope","id":"07bae854-b205-39bf-a23e-9eaa22063b92"},{"kind":"inventoryScope","id":"07bae854-b205-39bf-a23e-9eaa22063b92"}]`

- Session : `{"scopeId":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"eb1ce5f1-7397-3b45-9385-ef328e09fec8","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"d0c55bda-319d-3537-857a-5d135f796cab","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"d9842c03-ba37-324b-b71b-e58ac7147d4c","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"07bae854-b205-39bf-a23e-9eaa22063b92","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"07bae854-b205-39bf-a23e-9eaa22063b92","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 2918b4da-73ea-3f71-9206-45d97038f6cd — dossier d'écart

- Exemplaire : `3ca84f45-f92c-3d62-a027-07d783512e04`
- Famille privée : TRANSFERT_INCOHERENT ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"22e39911-4c31-370a-99a5-387987271bfa","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"5db2d6bc-4720-3796-8cc6-f33a33c0189a","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"3ca84f45-f92c-3d62-a027-07d783512e04","status":"RECEIVING","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","sourceEventIds":["5db2d6bc-4720-3796-8cc6-f33a33c0189a","192e0166-1be0-3fd8-812b-70b1c9e7f5b0"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | cbc57940-5906-386a-bad4-a59fd9b0f437 | 2026-06-01T21:24:59Z | 2026-06-01T21:25:09Z | `{"operationId":"c405fea9-0440-30e5-97e3-892ab4930239","correlationId":"b3dbbd18-0b7a-3f26-b151-88db7b6413ba","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"0285defe-0f43-3024-8898-7c0e3f89b5f8"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 50ae75c9-bf9a-3ce1-b0df-83717408d021 | 2026-06-02T21:24:59Z | 2026-06-02T21:25:09Z | `{"originalEventId":"cbc57940-5906-386a-bad4-a59fd9b0f437","correlationId":"b3dbbd18-0b7a-3f26-b151-88db7b6413ba","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 8251354f-f2cb-3131-ba28-d3fabb9dd1ca | 2026-07-31T21:24:59Z | 2026-07-31T21:25:09Z | `{"operationId":"867b617a-4edd-3ba8-96aa-71824bbf5c7a","correlationId":"5cca6e66-f037-3c8b-8fda-8b06ca66af66","siteId":"SUD","locationId":null,"loanId":"6f67afbe-4094-3cab-8325-b81a9b9da029"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 9b093c94-ccc4-3f02-a8e9-ba6b501a8685 | 2026-07-31T21:25:59Z | 2026-07-31T21:26:09Z | `{"originalEventId":"8251354f-f2cb-3131-ba28-d3fabb9dd1ca","correlationId":"5cca6e66-f037-3c8b-8fda-8b06ca66af66","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | a2bc36f1-8398-3819-9f4a-bd7ad78e7050 | 2026-08-20T21:24:59Z | 2026-08-20T21:25:09Z | `{"operationId":"5d551095-e60c-312a-b853-512b9c7cd686","correlationId":"59cb7fe2-a1a4-33e1-a08a-016516c966e3","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"6f67afbe-4094-3cab-8325-b81a9b9da029"}` |
| Retour reçu (`ReturnReceived`) | acf47843-e9e8-3c5a-9604-e6e801bebb83 | 2026-08-20T21:25:29Z | 2026-08-20T21:25:39Z | `{"operationId":"a296c866-d6be-3c36-9c4b-04f08874d962","correlationId":"36f49fb3-5e17-39b0-9d5a-9e56e23e4256","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"6f67afbe-4094-3cab-8325-b81a9b9da029"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | a7892a24-29bb-3f9c-a50d-7194bc709dbf | 2026-08-21T21:24:59Z | 2026-08-21T21:25:09Z | `{"originalEventId":"acf47843-e9e8-3c5a-9604-e6e801bebb83","correlationId":"36f49fb3-5e17-39b0-9d5a-9e56e23e4256","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Transfert expédié (`TransferDispatched`) | 006c4905-78b5-345e-91ca-b8caf96a58ad | 2026-08-30T21:24:59Z | 2026-08-30T21:25:09Z | `{"operationId":"1cac26cb-3559-3f0e-ba30-8699cab87362","correlationId":"66d0b9de-ce15-33e0-bdf7-a0f35f1c75ca","siteId":"SUD","locationId":null,"transferId":"2fcc28ca-1eba-382d-8971-9ae64d80a956","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | a46934ee-b987-3748-83ae-979ff69a0d3d | 2026-08-30T21:25:59Z | 2026-08-30T21:26:09Z | `{"originalEventId":"006c4905-78b5-345e-91ca-b8caf96a58ad","correlationId":"66d0b9de-ce15-33e0-bdf7-a0f35f1c75ca","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"SUD","locationId":null}` |
| Transfert reçu (`TransferReceived`) | 3613ba2b-e559-3d18-9a87-43040e7fca36 | 2026-08-31T21:24:59Z | 2026-08-31T21:25:09Z | `{"operationId":"6d208e5b-8dbe-3e7a-9d4a-1adc687c0e65","correlationId":"5de39c2d-53c1-311f-a361-81a291a0f562","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","transferId":"2fcc28ca-1eba-382d-8971-9ae64d80a956","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 032ab822-dd68-3158-b059-eae124658534 | 2026-09-01T21:24:59Z | 2026-09-01T21:25:09Z | `{"originalEventId":"3613ba2b-e559-3d18-9a87-43040e7fca36","correlationId":"5de39c2d-53c1-311f-a361-81a291a0f562","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Transfert expédié (`TransferDispatched`) | ab84d078-9038-3d87-9461-defc2358d223 | 2026-09-26T07:50:49Z | 2026-09-26T07:50:59Z | `{"operationId":"c01be8a6-6182-33c0-a449-3b25a984edfc","correlationId":"9f505e77-f1ff-39b3-b3f4-b80e84918fb3","siteId":"CENTRE","locationId":null,"transferId":"9c9ab860-e42d-3552-9e54-babac3f9c0d4","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | af4eb65f-1256-3fff-93a1-c36e26c7a967 | 2026-09-26T07:51:49Z | 2026-09-26T07:51:59Z | `{"originalEventId":"ab84d078-9038-3d87-9461-defc2358d223","correlationId":"9f505e77-f1ff-39b3-b3f4-b80e84918fb3","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| Transfert reçu (`TransferReceived`) | 5db2d6bc-4720-3796-8cc6-f33a33c0189a | 2026-09-27T07:50:49Z | 2026-09-27T07:50:59Z | `{"operationId":"ccfe667a-c237-3436-871c-0afca7650b6c","correlationId":"8d33423c-100c-3878-ae0d-4c04905c984f","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","transferId":"9c9ab860-e42d-3552-9e54-babac3f9c0d4","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 01330a8a-2ad2-32f3-92be-fc6540397d13 | 2026-09-27T07:51:19Z | 2026-09-27T07:51:29Z | `{"originalEventId":"5db2d6bc-4720-3796-8cc6-f33a33c0189a","correlationId":"8d33423c-100c-3878-ae0d-4c04905c984f","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 192e0166-1be0-3fd8-812b-70b1c9e7f5b0 | 2026-09-27T07:52:19Z | 2026-09-27T07:52:29Z | `{"originalEventId":"5db2d6bc-4720-3796-8cc6-f33a33c0189a","correlationId":"8d33423c-100c-3878-ae0d-4c04905c984f","outcome":"APPLIED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"e5afe2ce-44f7-3ec1-b12b-90646afe194a","copyId":"3ca84f45-f92c-3d62-a027-07d783512e04","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","scopeId":"2d080b55-2867-3e1b-a766-49afaacecf82","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`
- `{"observationId":"cc368231-dc55-3d0f-9fcd-7bfa487bb5a0","copyId":"3ca84f45-f92c-3d62-a027-07d783512e04","locationId":"2cf9a5ef-0690-3b0f-9031-67da0dd9d884","scopeId":"ac823531-87e3-3946-ac4b-26f9a608c929","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"3ca84f45-f92c-3d62-a027-07d783512e04","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3ca84f45-f92c-3d62-a027-07d783512e04","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3ca84f45-f92c-3d62-a027-07d783512e04","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3ca84f45-f92c-3d62-a027-07d783512e04","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3ca84f45-f92c-3d62-a027-07d783512e04","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3ca84f45-f92c-3d62-a027-07d783512e04","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3ca84f45-f92c-3d62-a027-07d783512e04","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"3ca84f45-f92c-3d62-a027-07d783512e04"},{"kind":"sourceEvent","id":"cbc57940-5906-386a-bad4-a59fd9b0f437"},{"kind":"sourceEvent","id":"50ae75c9-bf9a-3ce1-b0df-83717408d021"},{"kind":"sourceEvent","id":"8251354f-f2cb-3131-ba28-d3fabb9dd1ca"},{"kind":"sourceEvent","id":"9b093c94-ccc4-3f02-a8e9-ba6b501a8685"},{"kind":"sourceEvent","id":"a2bc36f1-8398-3819-9f4a-bd7ad78e7050"},{"kind":"sourceEvent","id":"acf47843-e9e8-3c5a-9604-e6e801bebb83"},{"kind":"sourceEvent","id":"a7892a24-29bb-3f9c-a50d-7194bc709dbf"},{"kind":"sourceEvent","id":"006c4905-78b5-345e-91ca-b8caf96a58ad"},{"kind":"sourceEvent","id":"a46934ee-b987-3748-83ae-979ff69a0d3d"},{"kind":"sourceEvent","id":"3613ba2b-e559-3d18-9a87-43040e7fca36"},{"kind":"sourceEvent","id":"032ab822-dd68-3158-b059-eae124658534"},{"kind":"sourceEvent","id":"ab84d078-9038-3d87-9461-defc2358d223"},{"kind":"sourceEvent","id":"af4eb65f-1256-3fff-93a1-c36e26c7a967"},{"kind":"sourceEvent","id":"5db2d6bc-4720-3796-8cc6-f33a33c0189a"},{"kind":"sourceEvent","id":"01330a8a-2ad2-32f3-92be-fc6540397d13"},{"kind":"sourceEvent","id":"192e0166-1be0-3fd8-812b-70b1c9e7f5b0"},{"kind":"observation","id":"e5afe2ce-44f7-3ec1-b12b-90646afe194a"},{"kind":"inventoryScope","id":"2d080b55-2867-3e1b-a766-49afaacecf82"},{"kind":"observation","id":"cc368231-dc55-3d0f-9fcd-7bfa487bb5a0"},{"kind":"inventoryScope","id":"ac823531-87e3-3946-ac4b-26f9a608c929"},{"kind":"sourceAvailability","id":"3ca84f45-f92c-3d62-a027-07d783512e04/LOANS"},{"kind":"sourceAvailability","id":"3ca84f45-f92c-3d62-a027-07d783512e04/RETURNS"},{"kind":"sourceAvailability","id":"3ca84f45-f92c-3d62-a027-07d783512e04/RECEIPTS"},{"kind":"sourceAvailability","id":"3ca84f45-f92c-3d62-a027-07d783512e04/TRANSFERS"},{"kind":"sourceAvailability","id":"3ca84f45-f92c-3d62-a027-07d783512e04/REPAIRS"},{"kind":"sourceAvailability","id":"3ca84f45-f92c-3d62-a027-07d783512e04/INTEGRATIONS"},{"kind":"sourceAvailability","id":"3ca84f45-f92c-3d62-a027-07d783512e04/INVENTORY"},{"kind":"inventoryScope","id":"d1e77ae7-a841-38e1-b16e-d9b468a2d87a"}]`

- Session : `{"scopeId":"2d080b55-2867-3e1b-a766-49afaacecf82","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"ac823531-87e3-3946-ac4b-26f9a608c929","locationId":"2cf9a5ef-0690-3b0f-9031-67da0dd9d884","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"d1e77ae7-a841-38e1-b16e-d9b468a2d87a","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 2b6df402-f46b-3015-ac87-bb41da529361 — dossier d'écart

- Exemplaire : `43905945-78ad-3efc-a5ba-2b29491963f3`
- Famille privée : RETARD_INTEGRATION ; variante : POSITIVE
- Symptômes : `[{"symptomId":"764427b0-2da6-3da3-9e1f-2b3d905d644b","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["QUEUE_PENDING"],"hiddenSourceEvents":[],"mechanism":"Événement métier en attente normale dans la fenêtre contractuelle de 900 secondes.","businessEventId":"92e89088-0259-3a25-a53c-33be4f968a57","physicalStatus":"ON_LOAN","physicalSiteId":"SUD","physicalLocationId":null,"projectionPerturbation":"QUEUED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["INTEGRATION_PENDING_WITHIN_SLA"],"explanation":"Prêt actif et traitement en file depuis moins de 900 secondes ; transition attendue, pas de panne persistante démontrée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Recontrôler après la fenêtre de traitement"]}`
- État du stock informatique : `{"copyId":"43905945-78ad-3efc-a5ba-2b29491963f3","status":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","sourceEventIds":["19a465e4-b700-30cd-a878-b40ac8e7c4d3","fdf72fc4-9fad-323e-a94f-f240612d4fe4"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 69136fd4-2a76-32a4-a2fe-ebcd331827fe | 2026-06-02T07:26:39Z | 2026-06-02T07:26:49Z | `{"operationId":"d623600c-4464-3f3f-9d65-939aef49c1ff","correlationId":"23fd3cdb-4b1b-3cde-8146-46fce185a9e6","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"c5123176-a7a1-34c7-9edc-ce8d928179ad"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | a10f171c-babb-3c4b-9175-7e6d89334b15 | 2026-06-03T07:26:39Z | 2026-06-03T07:26:49Z | `{"originalEventId":"69136fd4-2a76-32a4-a2fe-ebcd331827fe","correlationId":"23fd3cdb-4b1b-3cde-8146-46fce185a9e6","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | a68185f5-006a-369c-8c97-4a60245aad76 | 2026-08-01T07:26:39Z | 2026-08-01T07:26:49Z | `{"operationId":"ccb43513-d192-331b-a1e3-2a63612e0de1","correlationId":"3958cfd5-0ad1-31e5-b76f-1829da490d1a","siteId":"NORD","locationId":null,"loanId":"72be8bd0-7633-3ab1-befe-753509131ed1"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 3ea29b20-a887-323e-96f1-bfb3b71c1281 | 2026-08-01T07:27:39Z | 2026-08-01T07:27:49Z | `{"originalEventId":"a68185f5-006a-369c-8c97-4a60245aad76","correlationId":"3958cfd5-0ad1-31e5-b76f-1829da490d1a","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | b1df88f9-5b53-38f2-bb9c-caeba06ec7fa | 2026-08-21T07:26:39Z | 2026-08-21T07:26:49Z | `{"operationId":"6d044998-211d-37fa-86bb-5e2bba84bf6b","correlationId":"c607d151-02ef-304c-8feb-de3ff0a02955","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"72be8bd0-7633-3ab1-befe-753509131ed1"}` |
| Retour reçu (`ReturnReceived`) | ba8ae865-b268-3213-b230-e25b14144cd9 | 2026-08-21T07:27:09Z | 2026-08-21T07:27:19Z | `{"operationId":"9bf6efee-0879-3474-91b6-c24949163457","correlationId":"a5ae1da8-35bd-3b21-af53-44ee1da30493","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"72be8bd0-7633-3ab1-befe-753509131ed1"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | b3c07a62-7d91-3f20-886c-ca8ed4d15962 | 2026-08-22T07:26:39Z | 2026-08-22T07:26:49Z | `{"originalEventId":"ba8ae865-b268-3213-b230-e25b14144cd9","correlationId":"a5ae1da8-35bd-3b21-af53-44ee1da30493","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Transfert expédié (`TransferDispatched`) | e59a8dd6-165e-31dc-975c-320c66925bd3 | 2026-08-31T07:26:39Z | 2026-08-31T07:26:49Z | `{"operationId":"7cd89d7a-78f5-314e-88c2-1c1a688bd6c3","correlationId":"9ce6c85e-3127-3590-ac36-b8aced4cdf69","siteId":"NORD","locationId":null,"transferId":"0d657139-c94e-36e0-8055-28a0cebbaa7f","fromSiteId":"NORD","toSiteId":"SUD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 256a704a-cd94-348b-a2e5-c3ffcff2a618 | 2026-08-31T07:27:39Z | 2026-08-31T07:27:49Z | `{"originalEventId":"e59a8dd6-165e-31dc-975c-320c66925bd3","correlationId":"9ce6c85e-3127-3590-ac36-b8aced4cdf69","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"NORD","locationId":null}` |
| Transfert reçu (`TransferReceived`) | 19a465e4-b700-30cd-a878-b40ac8e7c4d3 | 2026-09-01T07:26:39Z | 2026-09-01T07:26:49Z | `{"operationId":"71bf31bd-da65-37b5-94d5-d317f2321b57","correlationId":"f0bf4fe5-53e1-3d05-8103-d6e2ff3360f3","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","transferId":"0d657139-c94e-36e0-8055-28a0cebbaa7f","fromSiteId":"NORD","toSiteId":"SUD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | fdf72fc4-9fad-323e-a94f-f240612d4fe4 | 2026-09-02T07:26:39Z | 2026-09-02T07:26:49Z | `{"originalEventId":"19a465e4-b700-30cd-a878-b40ac8e7c4d3","correlationId":"f0bf4fe5-53e1-3d05-8103-d6e2ff3360f3","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 92e89088-0259-3a25-a53c-33be4f968a57 | 2026-09-30T11:50:04Z | 2026-09-30T11:50:14Z | `{"operationId":"c458e6e8-6716-3fb5-88f6-a00030eab7ef","correlationId":"d1ffaf6e-1e40-348e-8ff0-926285d22b0b","siteId":"SUD","locationId":null,"loanId":"f7237e4e-66b8-326b-aa7c-99263a29b705"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 07fbc309-08f9-38f1-84e3-bf580accf690 | 2026-09-30T11:50:34Z | 2026-09-30T11:50:44Z | `{"originalEventId":"92e89088-0259-3a25-a53c-33be4f968a57","correlationId":"d1ffaf6e-1e40-348e-8ff0-926285d22b0b","outcome":"QUEUED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"43905945-78ad-3efc-a5ba-2b29491963f3","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"43905945-78ad-3efc-a5ba-2b29491963f3","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"43905945-78ad-3efc-a5ba-2b29491963f3","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"43905945-78ad-3efc-a5ba-2b29491963f3","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"43905945-78ad-3efc-a5ba-2b29491963f3","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"43905945-78ad-3efc-a5ba-2b29491963f3","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"43905945-78ad-3efc-a5ba-2b29491963f3","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"43905945-78ad-3efc-a5ba-2b29491963f3"},{"kind":"sourceEvent","id":"69136fd4-2a76-32a4-a2fe-ebcd331827fe"},{"kind":"sourceEvent","id":"a10f171c-babb-3c4b-9175-7e6d89334b15"},{"kind":"sourceEvent","id":"a68185f5-006a-369c-8c97-4a60245aad76"},{"kind":"sourceEvent","id":"3ea29b20-a887-323e-96f1-bfb3b71c1281"},{"kind":"sourceEvent","id":"b1df88f9-5b53-38f2-bb9c-caeba06ec7fa"},{"kind":"sourceEvent","id":"ba8ae865-b268-3213-b230-e25b14144cd9"},{"kind":"sourceEvent","id":"b3c07a62-7d91-3f20-886c-ca8ed4d15962"},{"kind":"sourceEvent","id":"e59a8dd6-165e-31dc-975c-320c66925bd3"},{"kind":"sourceEvent","id":"256a704a-cd94-348b-a2e5-c3ffcff2a618"},{"kind":"sourceEvent","id":"19a465e4-b700-30cd-a878-b40ac8e7c4d3"},{"kind":"sourceEvent","id":"fdf72fc4-9fad-323e-a94f-f240612d4fe4"},{"kind":"sourceEvent","id":"92e89088-0259-3a25-a53c-33be4f968a57"},{"kind":"sourceEvent","id":"07fbc309-08f9-38f1-84e3-bf580accf690"},{"kind":"sourceAvailability","id":"43905945-78ad-3efc-a5ba-2b29491963f3/LOANS"},{"kind":"sourceAvailability","id":"43905945-78ad-3efc-a5ba-2b29491963f3/RETURNS"},{"kind":"sourceAvailability","id":"43905945-78ad-3efc-a5ba-2b29491963f3/RECEIPTS"},{"kind":"sourceAvailability","id":"43905945-78ad-3efc-a5ba-2b29491963f3/TRANSFERS"},{"kind":"sourceAvailability","id":"43905945-78ad-3efc-a5ba-2b29491963f3/REPAIRS"},{"kind":"sourceAvailability","id":"43905945-78ad-3efc-a5ba-2b29491963f3/INTEGRATIONS"},{"kind":"sourceAvailability","id":"43905945-78ad-3efc-a5ba-2b29491963f3/INVENTORY"},{"kind":"inventoryScope","id":"7f91fb45-70cc-3811-b222-43a5370aa433"},{"kind":"inventoryScope","id":"202855ec-9065-3be6-a0c2-07ce228ecf21"},{"kind":"inventoryScope","id":"1b4f280a-3145-3607-a009-dca44e9d4181"},{"kind":"inventoryScope","id":"2948b706-5519-3fcb-9c6a-82f0197080d2"},{"kind":"inventoryScope","id":"2977b6c8-c0f0-3d4f-aba5-6b5d16436fb8"}]`

- Session : `{"scopeId":"7f91fb45-70cc-3811-b222-43a5370aa433","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"202855ec-9065-3be6-a0c2-07ce228ecf21","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"1b4f280a-3145-3607-a009-dca44e9d4181","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2948b706-5519-3fcb-9c6a-82f0197080d2","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2977b6c8-c0f0-3d4f-aba5-6b5d16436fb8","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 2d2ef36b-eb02-31fe-8471-7ed6099519ed — dossier d'écart

- Exemplaire : `bf1c13d1-374c-3760-9762-57540473974e`
- Famille privée : ABSENCE_INEXPLIQUEE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"b54c709f-00fd-392b-9f2f-c2c08bb23b16","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["UNOBSERVED_MOVE"],"mechanism":"Exemplaire déplacé hors du périmètre instrumenté ; la destination n'est pas exposée et aucune perte n'est simulée.","physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":null,"evidenceMask":"Collecte indisponible"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"L'absence de lecture ne démontre ni la destination, ni une perte, ni un vol ; données insuffisantes pour attribuer une cause.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État du stock informatique : `{"copyId":"bf1c13d1-374c-3760-9762-57540473974e","status":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","sourceEventIds":["2c38d847-1d2e-38e4-9188-b809e513539d","201b6523-79b7-3e32-89c4-315a95f637ea"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | ec44af55-4a9f-32f0-809e-26b63c84d12a | 2026-06-02T09:41:53Z | 2026-06-02T09:42:03Z | `{"operationId":"d5e0bee1-17b0-362f-a295-5f8f60db8a9e","correlationId":"93e748f4-809a-3c04-85b9-e6059bdc6a0c","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"0325ddc3-bee8-3a48-88fa-2d37a019e66a"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 2f0710c7-6b20-36db-89ec-bc56b4193268 | 2026-06-03T09:41:53Z | 2026-06-03T09:42:03Z | `{"originalEventId":"ec44af55-4a9f-32f0-809e-26b63c84d12a","correlationId":"93e748f4-809a-3c04-85b9-e6059bdc6a0c","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 240d4f03-2bbd-3f3f-96a6-48407126795e | 2026-08-01T09:41:53Z | 2026-08-01T09:42:03Z | `{"operationId":"6b50be2a-ee90-3ad0-aa49-23693cfb647e","correlationId":"71a07a64-2e8c-371c-8a5f-f3b3fbf78f76","siteId":"CENTRE","locationId":null,"loanId":"84291835-6dcc-3dae-9959-a3cb4d1b0e19"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 94a01367-2cda-3e61-9f02-9be5dbbecdbe | 2026-08-01T09:42:53Z | 2026-08-01T09:43:03Z | `{"originalEventId":"240d4f03-2bbd-3f3f-96a6-48407126795e","correlationId":"71a07a64-2e8c-371c-8a5f-f3b3fbf78f76","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | a3be69e0-cc11-3f4f-bf33-5bc8fff77acc | 2026-08-21T09:41:53Z | 2026-08-21T09:42:03Z | `{"operationId":"5f6b3e63-2b93-314c-94e7-a4dcccc529cc","correlationId":"d7a1d801-df02-37ad-a4a6-c70ffbcac41b","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"84291835-6dcc-3dae-9959-a3cb4d1b0e19"}` |
| Retour reçu (`ReturnReceived`) | 3d4d276b-124d-352f-aa40-775f9cd1126a | 2026-08-21T09:42:23Z | 2026-08-21T09:42:33Z | `{"operationId":"9ce7a0dd-5d16-3c76-b0d4-858b4ea3690d","correlationId":"30e6a969-5482-3191-be37-fb42e8d35f10","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"84291835-6dcc-3dae-9959-a3cb4d1b0e19"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | a0da9f0d-b3c6-361e-ba67-fa8692ff804a | 2026-08-22T09:41:53Z | 2026-08-22T09:42:03Z | `{"originalEventId":"3d4d276b-124d-352f-aa40-775f9cd1126a","correlationId":"30e6a969-5482-3191-be37-fb42e8d35f10","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Transfert expédié (`TransferDispatched`) | b57c962d-0045-35f7-9b72-ce5a930e6e31 | 2026-08-31T09:41:53Z | 2026-08-31T09:42:03Z | `{"operationId":"c4f3ebb8-d2fa-3a8d-a1b5-b6dcf703e730","correlationId":"019afc36-1dd4-3957-a2bd-27dda9da20fb","siteId":"CENTRE","locationId":null,"transferId":"01180abc-289f-3707-b166-8f4a782688db","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 09374311-06e0-31d4-8b04-d97138177ac0 | 2026-08-31T09:42:53Z | 2026-08-31T09:43:03Z | `{"originalEventId":"b57c962d-0045-35f7-9b72-ce5a930e6e31","correlationId":"019afc36-1dd4-3957-a2bd-27dda9da20fb","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| Transfert reçu (`TransferReceived`) | 2c38d847-1d2e-38e4-9188-b809e513539d | 2026-09-01T09:41:53Z | 2026-09-01T09:42:03Z | `{"operationId":"2dcb31a9-c506-36c3-8c73-ade5979b3a30","correlationId":"33909cf0-b91a-3931-a404-88000e31dd16","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","transferId":"01180abc-289f-3707-b166-8f4a782688db","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 201b6523-79b7-3e32-89c4-315a95f637ea | 2026-09-02T09:41:53Z | 2026-09-02T09:42:03Z | `{"originalEventId":"2c38d847-1d2e-38e4-9188-b809e513539d","correlationId":"33909cf0-b91a-3931-a404-88000e31dd16","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"bf1c13d1-374c-3760-9762-57540473974e","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bf1c13d1-374c-3760-9762-57540473974e","source":"TRANSFERS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"bf1c13d1-374c-3760-9762-57540473974e","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bf1c13d1-374c-3760-9762-57540473974e","source":"INVENTORY","status":"UNAVAILABLE","completeFrom":null,"completeTo":null}`
- `{"copyId":"bf1c13d1-374c-3760-9762-57540473974e","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bf1c13d1-374c-3760-9762-57540473974e","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bf1c13d1-374c-3760-9762-57540473974e","source":"LOANS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**
- `{"noteId":"c02b113b-142f-3a45-b3dd-32e0eec19a19","copyId":"bf1c13d1-374c-3760-9762-57540473974e","text":"Le passage au rayon n'a pas permis de retrouver cet exemplaire ; contrôle complémentaire demandé.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"bf1c13d1-374c-3760-9762-57540473974e"},{"kind":"sourceEvent","id":"ec44af55-4a9f-32f0-809e-26b63c84d12a"},{"kind":"sourceEvent","id":"2f0710c7-6b20-36db-89ec-bc56b4193268"},{"kind":"sourceEvent","id":"240d4f03-2bbd-3f3f-96a6-48407126795e"},{"kind":"sourceEvent","id":"94a01367-2cda-3e61-9f02-9be5dbbecdbe"},{"kind":"sourceEvent","id":"a3be69e0-cc11-3f4f-bf33-5bc8fff77acc"},{"kind":"sourceEvent","id":"3d4d276b-124d-352f-aa40-775f9cd1126a"},{"kind":"sourceEvent","id":"a0da9f0d-b3c6-361e-ba67-fa8692ff804a"},{"kind":"sourceEvent","id":"b57c962d-0045-35f7-9b72-ce5a930e6e31"},{"kind":"sourceEvent","id":"09374311-06e0-31d4-8b04-d97138177ac0"},{"kind":"sourceEvent","id":"2c38d847-1d2e-38e4-9188-b809e513539d"},{"kind":"sourceEvent","id":"201b6523-79b7-3e32-89c4-315a95f637ea"},{"kind":"sourceAvailability","id":"bf1c13d1-374c-3760-9762-57540473974e/LOANS"},{"kind":"sourceAvailability","id":"bf1c13d1-374c-3760-9762-57540473974e/RETURNS"},{"kind":"sourceAvailability","id":"bf1c13d1-374c-3760-9762-57540473974e/RECEIPTS"},{"kind":"sourceAvailability","id":"bf1c13d1-374c-3760-9762-57540473974e/TRANSFERS"},{"kind":"sourceAvailability","id":"bf1c13d1-374c-3760-9762-57540473974e/REPAIRS"},{"kind":"sourceAvailability","id":"bf1c13d1-374c-3760-9762-57540473974e/INTEGRATIONS"},{"kind":"sourceAvailability","id":"bf1c13d1-374c-3760-9762-57540473974e/INVENTORY"},{"kind":"operatorNote","id":"c02b113b-142f-3a45-b3dd-32e0eec19a19"},{"kind":"inventoryScope","id":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5"},{"kind":"inventoryScope","id":"eb1ce5f1-7397-3b45-9385-ef328e09fec8"},{"kind":"inventoryScope","id":"d0c55bda-319d-3537-857a-5d135f796cab"},{"kind":"inventoryScope","id":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb"},{"kind":"inventoryScope","id":"d9842c03-ba37-324b-b71b-e58ac7147d4c"},{"kind":"inventoryScope","id":"07bae854-b205-39bf-a23e-9eaa22063b92"},{"kind":"inventoryScope","id":"9de706b4-d360-3282-b440-c440861c039c"},{"kind":"inventoryScope","id":"b98af8e5-7297-3dc5-a947-eb8fe8af159f"},{"kind":"inventoryScope","id":"b98af8e5-7297-3dc5-a947-eb8fe8af159f"}]`

- Session : `{"scopeId":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"eb1ce5f1-7397-3b45-9385-ef328e09fec8","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"d0c55bda-319d-3537-857a-5d135f796cab","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"d9842c03-ba37-324b-b71b-e58ac7147d4c","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"07bae854-b205-39bf-a23e-9eaa22063b92","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"9de706b4-d360-3282-b440-c440861c039c","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b98af8e5-7297-3dc5-a947-eb8fe8af159f","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"b98af8e5-7297-3dc5-a947-eb8fe8af159f","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 3137eda8-d34f-30ae-a8ec-6732cc837706 — dossier d'écart

- Exemplaire : `c726dd62-a03e-3ccc-9625-c0423d25b4ab`
- Famille privée : RETOUR_NON_PROJETE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"c5e14197-3e77-3dc0-8ba2-b2b3deb35a3a","kind":"STATUS_MISMATCH","expectedValue":"ON_LOAN","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"1cd8d067-a984-38a0-8b6a-b73315b2efcd","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["RETURN_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"c726dd62-a03e-3ccc-9625-c0423d25b4ab","status":"ON_LOAN","siteId":"NORD","locationId":null,"sourceEventIds":["71ebd69e-47ed-3456-b98d-e89c0aaf9c07","50b14486-dbb5-3667-bb62-506c3637922e"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | b8c9b232-58e1-33fa-851b-1660add20378 | 2026-06-02T09:52:41Z | 2026-06-02T09:52:51Z | `{"operationId":"66b23ac9-dfcb-3f38-92c9-98c7948c5e5b","correlationId":"4441f154-23c0-3df2-a695-60d1a29a605a","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"ced88b5c-15e9-3940-81e9-7778286a94f1"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 42480260-162a-37c6-a580-9df47f89c410 | 2026-06-03T09:52:41Z | 2026-06-03T09:52:51Z | `{"originalEventId":"b8c9b232-58e1-33fa-851b-1660add20378","correlationId":"4441f154-23c0-3df2-a695-60d1a29a605a","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | dc7cb18f-5f6c-32fd-9727-102e68a462aa | 2026-08-01T09:52:41Z | 2026-08-01T09:52:51Z | `{"operationId":"5fd12ee6-4f92-3082-a182-bcddaf705448","correlationId":"8e3faee6-0ab2-3b9c-87aa-835529f9110c","siteId":"NORD","locationId":null,"loanId":"5b7b3cf8-af95-3fc7-bc3f-0848a4da8633"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 245b32dc-84b1-30a9-9e19-28eba6a11412 | 2026-08-01T09:53:41Z | 2026-08-01T09:53:51Z | `{"originalEventId":"dc7cb18f-5f6c-32fd-9727-102e68a462aa","correlationId":"8e3faee6-0ab2-3b9c-87aa-835529f9110c","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | d6c48105-0604-3f6c-82e1-cf784ee28a12 | 2026-08-21T09:52:41Z | 2026-08-21T09:52:51Z | `{"operationId":"1e3c4f69-7b88-3e2a-ac3c-f4e574a168f9","correlationId":"4d9d5250-2eb2-37eb-baf1-dc9e7104f8e1","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"5b7b3cf8-af95-3fc7-bc3f-0848a4da8633"}` |
| Retour reçu (`ReturnReceived`) | 2f71536c-ef41-3b74-91a9-48b3e7e78536 | 2026-08-21T09:53:11Z | 2026-08-21T09:53:21Z | `{"operationId":"6b757579-e878-35b2-a0e4-fd910481e12f","correlationId":"e7464f26-c97b-320f-81ed-3cf6b019c498","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"5b7b3cf8-af95-3fc7-bc3f-0848a4da8633"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 846d0cee-4632-3fe9-8227-f85d8090de9c | 2026-08-22T09:52:41Z | 2026-08-22T09:52:51Z | `{"originalEventId":"2f71536c-ef41-3b74-91a9-48b3e7e78536","correlationId":"e7464f26-c97b-320f-81ed-3cf6b019c498","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | 71ebd69e-47ed-3456-b98d-e89c0aaf9c07 | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"df97fe13-0103-32b7-878a-cc30c8b7bef1","correlationId":"a9d2e442-4be5-383c-8136-72baf6dedeb9","siteId":"NORD","locationId":null,"loanId":"b888fa07-c464-3b7d-b1e4-a8da5dc596e2"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 50b14486-dbb5-3667-bb62-506c3637922e | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"71ebd69e-47ed-3456-b98d-e89c0aaf9c07","correlationId":"a9d2e442-4be5-383c-8136-72baf6dedeb9","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 5db2f310-ac7b-370e-8e57-378f8cde4d66 | 2026-09-27T10:02:54Z | 2026-09-27T10:03:04Z | `{"operationId":"b21951aa-1de9-3620-bdfd-e19822a72afc","correlationId":"a6d5d48b-644f-3c6e-973d-da57d81d22ff","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"b888fa07-c464-3b7d-b1e4-a8da5dc596e2"}` |
| Retour reçu (`ReturnReceived`) | 1cd8d067-a984-38a0-8b6a-b73315b2efcd | 2026-09-27T10:03:24Z | 2026-09-27T10:03:34Z | `{"operationId":"6169da7b-1ae2-3542-ae08-4f6b8eb4de40","correlationId":"d55c9c01-424a-391d-835e-91bf6f6db061","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"b888fa07-c464-3b7d-b1e4-a8da5dc596e2"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | d372d956-6ba1-351f-ae09-466919acfdfd | 2026-09-27T10:03:54Z | 2026-09-27T10:04:04Z | `{"originalEventId":"1cd8d067-a984-38a0-8b6a-b73315b2efcd","correlationId":"d55c9c01-424a-391d-835e-91bf6f6db061","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"09016836-222b-3154-9722-7fced32b5e0d","copyId":"c726dd62-a03e-3ccc-9625-c0423d25b4ab","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","scopeId":"041ea6ea-fb06-36bc-9dab-c0440e48bcc6","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"c726dd62-a03e-3ccc-9625-c0423d25b4ab","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c726dd62-a03e-3ccc-9625-c0423d25b4ab","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c726dd62-a03e-3ccc-9625-c0423d25b4ab","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c726dd62-a03e-3ccc-9625-c0423d25b4ab","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c726dd62-a03e-3ccc-9625-c0423d25b4ab","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c726dd62-a03e-3ccc-9625-c0423d25b4ab","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c726dd62-a03e-3ccc-9625-c0423d25b4ab","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"c726dd62-a03e-3ccc-9625-c0423d25b4ab"},{"kind":"sourceEvent","id":"b8c9b232-58e1-33fa-851b-1660add20378"},{"kind":"sourceEvent","id":"42480260-162a-37c6-a580-9df47f89c410"},{"kind":"sourceEvent","id":"dc7cb18f-5f6c-32fd-9727-102e68a462aa"},{"kind":"sourceEvent","id":"245b32dc-84b1-30a9-9e19-28eba6a11412"},{"kind":"sourceEvent","id":"d6c48105-0604-3f6c-82e1-cf784ee28a12"},{"kind":"sourceEvent","id":"2f71536c-ef41-3b74-91a9-48b3e7e78536"},{"kind":"sourceEvent","id":"846d0cee-4632-3fe9-8227-f85d8090de9c"},{"kind":"sourceEvent","id":"71ebd69e-47ed-3456-b98d-e89c0aaf9c07"},{"kind":"sourceEvent","id":"50b14486-dbb5-3667-bb62-506c3637922e"},{"kind":"sourceEvent","id":"5db2f310-ac7b-370e-8e57-378f8cde4d66"},{"kind":"sourceEvent","id":"1cd8d067-a984-38a0-8b6a-b73315b2efcd"},{"kind":"sourceEvent","id":"d372d956-6ba1-351f-ae09-466919acfdfd"},{"kind":"observation","id":"09016836-222b-3154-9722-7fced32b5e0d"},{"kind":"inventoryScope","id":"041ea6ea-fb06-36bc-9dab-c0440e48bcc6"},{"kind":"sourceAvailability","id":"c726dd62-a03e-3ccc-9625-c0423d25b4ab/LOANS"},{"kind":"sourceAvailability","id":"c726dd62-a03e-3ccc-9625-c0423d25b4ab/RETURNS"},{"kind":"sourceAvailability","id":"c726dd62-a03e-3ccc-9625-c0423d25b4ab/RECEIPTS"},{"kind":"sourceAvailability","id":"c726dd62-a03e-3ccc-9625-c0423d25b4ab/TRANSFERS"},{"kind":"sourceAvailability","id":"c726dd62-a03e-3ccc-9625-c0423d25b4ab/REPAIRS"},{"kind":"sourceAvailability","id":"c726dd62-a03e-3ccc-9625-c0423d25b4ab/INTEGRATIONS"},{"kind":"sourceAvailability","id":"c726dd62-a03e-3ccc-9625-c0423d25b4ab/INVENTORY"}]`

- Session : `{"scopeId":"041ea6ea-fb06-36bc-9dab-c0440e48bcc6","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 31533407-c5b9-30ec-ab97-b824acd17b2f — dossier d'écart

- Exemplaire : `d1ac4ccd-b0d3-3826-962e-32cd4d9bc731`
- Famille privée : PRET_NON_PROJETE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"976788af-e60f-3860-9ff4-5880d9e72bdb","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"abaed481-256d-3927-8670-f875f613a071","copyId":"d1ac4ccd-b0d3-3826-962e-32cd4d9bc731","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T08:58:24Z","recordedAt":"2026-09-27T08:58:34Z","payload":{"originalEventId":"35830835-ace5-3480-a6f9-166aefcebdcc","correlationId":"0f13a412-02b3-30a9-9b74-05515732855c","outcome":"FAILED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"35830835-ace5-3480-a6f9-166aefcebdcc","physicalStatus":"ON_LOAN","physicalSiteId":"NORD","physicalLocationId":null,"projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État du stock informatique : `{"copyId":"d1ac4ccd-b0d3-3826-962e-32cd4d9bc731","status":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","sourceEventIds":["de5bb18f-a9a3-3b4b-acd4-d62f1ef0670c","d780095b-5306-3f53-81b5-26732bf901ef"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | dec6e166-21bf-3436-922c-44d205555fda | 2026-06-01T13:25:44Z | 2026-06-01T13:25:54Z | `{"operationId":"bf8c7b48-2deb-3407-b897-c06d6d4bcdcd","correlationId":"71d7eef0-c6bc-33dc-8b9e-89e58c99b25f","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"ba7ebe64-206b-3f93-85a9-29be4684ce26"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | bbc2c0ed-6201-3009-854e-11c8a7de35d0 | 2026-06-02T13:25:44Z | 2026-06-02T13:25:54Z | `{"originalEventId":"dec6e166-21bf-3436-922c-44d205555fda","correlationId":"71d7eef0-c6bc-33dc-8b9e-89e58c99b25f","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | 09fb409e-4a5a-3207-9222-465fa0c50860 | 2026-07-31T13:25:44Z | 2026-07-31T13:25:54Z | `{"operationId":"8ec8be34-f3ba-3c77-8832-f93b41eaa180","correlationId":"53af2923-2890-3e26-bf5f-1cf5a854891a","siteId":"NORD","locationId":null,"loanId":"05564195-ed11-35f3-9ecf-fbd8a9a89ed1"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 5ee9021b-5cf5-3feb-a7e2-4949f9ef1561 | 2026-07-31T13:26:44Z | 2026-07-31T13:26:54Z | `{"originalEventId":"09fb409e-4a5a-3207-9222-465fa0c50860","correlationId":"53af2923-2890-3e26-bf5f-1cf5a854891a","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | a1e46574-56b9-318f-882a-6879c1f25049 | 2026-08-20T13:25:44Z | 2026-08-20T13:25:54Z | `{"operationId":"c192a463-d0a5-3e76-a728-a3ff05fd3d57","correlationId":"8f3afdd7-d045-32c8-bca1-e98f2e90989c","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"05564195-ed11-35f3-9ecf-fbd8a9a89ed1"}` |
| Retour reçu (`ReturnReceived`) | de5bb18f-a9a3-3b4b-acd4-d62f1ef0670c | 2026-08-20T13:26:14Z | 2026-08-20T13:26:24Z | `{"operationId":"a71d1b0b-4862-3782-aa5e-73c36a9cf6a3","correlationId":"f9448dca-8a11-3da7-a820-97ca69a62abd","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"05564195-ed11-35f3-9ecf-fbd8a9a89ed1"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | d780095b-5306-3f53-81b5-26732bf901ef | 2026-08-21T13:25:44Z | 2026-08-21T13:25:54Z | `{"originalEventId":"de5bb18f-a9a3-3b4b-acd4-d62f1ef0670c","correlationId":"f9448dca-8a11-3da7-a820-97ca69a62abd","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | 35830835-ace5-3480-a6f9-166aefcebdcc | 2026-09-27T08:57:54Z | 2026-09-27T08:58:04Z | `{"operationId":"72deae93-6502-3647-b1fd-57744ec7fc75","correlationId":"0f13a412-02b3-30a9-9b74-05515732855c","siteId":"NORD","locationId":null,"loanId":"2e4de7b2-2d0f-37a2-a8ab-1bf9388cf204"}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"d1ac4ccd-b0d3-3826-962e-32cd4d9bc731","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"d1ac4ccd-b0d3-3826-962e-32cd4d9bc731","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"d1ac4ccd-b0d3-3826-962e-32cd4d9bc731","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"d1ac4ccd-b0d3-3826-962e-32cd4d9bc731","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"d1ac4ccd-b0d3-3826-962e-32cd4d9bc731","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"d1ac4ccd-b0d3-3826-962e-32cd4d9bc731","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"d1ac4ccd-b0d3-3826-962e-32cd4d9bc731","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"d1ac4ccd-b0d3-3826-962e-32cd4d9bc731"},{"kind":"sourceEvent","id":"dec6e166-21bf-3436-922c-44d205555fda"},{"kind":"sourceEvent","id":"bbc2c0ed-6201-3009-854e-11c8a7de35d0"},{"kind":"sourceEvent","id":"09fb409e-4a5a-3207-9222-465fa0c50860"},{"kind":"sourceEvent","id":"5ee9021b-5cf5-3feb-a7e2-4949f9ef1561"},{"kind":"sourceEvent","id":"a1e46574-56b9-318f-882a-6879c1f25049"},{"kind":"sourceEvent","id":"de5bb18f-a9a3-3b4b-acd4-d62f1ef0670c"},{"kind":"sourceEvent","id":"d780095b-5306-3f53-81b5-26732bf901ef"},{"kind":"sourceEvent","id":"35830835-ace5-3480-a6f9-166aefcebdcc"},{"kind":"sourceAvailability","id":"d1ac4ccd-b0d3-3826-962e-32cd4d9bc731/LOANS"},{"kind":"sourceAvailability","id":"d1ac4ccd-b0d3-3826-962e-32cd4d9bc731/RETURNS"},{"kind":"sourceAvailability","id":"d1ac4ccd-b0d3-3826-962e-32cd4d9bc731/RECEIPTS"},{"kind":"sourceAvailability","id":"d1ac4ccd-b0d3-3826-962e-32cd4d9bc731/TRANSFERS"},{"kind":"sourceAvailability","id":"d1ac4ccd-b0d3-3826-962e-32cd4d9bc731/REPAIRS"},{"kind":"sourceAvailability","id":"d1ac4ccd-b0d3-3826-962e-32cd4d9bc731/INTEGRATIONS"},{"kind":"sourceAvailability","id":"d1ac4ccd-b0d3-3826-962e-32cd4d9bc731/INVENTORY"},{"kind":"inventoryScope","id":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5"},{"kind":"inventoryScope","id":"eb1ce5f1-7397-3b45-9385-ef328e09fec8"}]`

- Session : `{"scopeId":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"eb1ce5f1-7397-3b45-9385-ef328e09fec8","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 338ad5de-4dcb-3d68-8249-76b1b7c01d8c — dossier d'écart

- Exemplaire : `cd628ce3-3257-39d5-882a-7a0ae4df0355`
- Famille privée : RETARD_INTEGRATION ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"117acd1a-5d3c-31bb-a669-c7b9466c2e5c","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["QUEUE_PENDING"],"hiddenSourceEvents":[{"sourceEventId":"e31bbee0-92ab-359b-9e74-ef01752abf4e","copyId":"cd628ce3-3257-39d5-882a-7a0ae4df0355","eventType":"IntegrationProcessed","occurredAt":"2026-09-30T11:50:36Z","recordedAt":"2026-09-30T11:50:46Z","payload":{"originalEventId":"28742091-16a6-3421-a0ba-e03a1dbae035","correlationId":"88cc6773-cbaf-3cb8-a6a9-4048a5201d14","outcome":"QUEUED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1}}],"mechanism":"Événement métier en attente normale dans la fenêtre contractuelle de 900 secondes.","businessEventId":"28742091-16a6-3421-a0ba-e03a1dbae035","physicalStatus":"ON_LOAN","physicalSiteId":"SUD","physicalLocationId":null,"projectionPerturbation":"QUEUED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État du stock informatique : `{"copyId":"cd628ce3-3257-39d5-882a-7a0ae4df0355","status":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","sourceEventIds":["59b63d69-8a4e-3f23-9086-de53821d97d1","5e142642-f6a6-3293-8e10-97c5ab582988"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 97ef24fe-9327-348e-b73b-5a96aa83a3c3 | 2026-06-02T08:34:19Z | 2026-06-02T08:34:29Z | `{"operationId":"88b118ea-d496-3620-9032-05f451605370","correlationId":"aa8b05f0-ccad-3cd0-950f-d800ef499502","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"98c58bd6-9c02-3f4f-9730-93471b839517"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 7393fc45-a3a5-350c-a8c9-8ec3ac5a96ce | 2026-06-03T08:34:19Z | 2026-06-03T08:34:29Z | `{"originalEventId":"97ef24fe-9327-348e-b73b-5a96aa83a3c3","correlationId":"aa8b05f0-ccad-3cd0-950f-d800ef499502","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 97283fad-c4dc-323c-9ab3-024b64caf7ae | 2026-08-01T08:34:19Z | 2026-08-01T08:34:29Z | `{"operationId":"5e640194-1290-3268-9236-63452576f166","correlationId":"a98bc79e-93d8-37df-bc3a-a345e7cc6342","siteId":"SUD","locationId":null,"loanId":"a94d2fb6-0446-31ae-8fec-8cef014e9a23"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | f41ba236-59f8-30aa-bde0-4e1784959966 | 2026-08-01T08:35:19Z | 2026-08-01T08:35:29Z | `{"originalEventId":"97283fad-c4dc-323c-9ab3-024b64caf7ae","correlationId":"a98bc79e-93d8-37df-bc3a-a345e7cc6342","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 3208ffde-2e88-3d91-8c48-56615c56b021 | 2026-08-21T08:34:19Z | 2026-08-21T08:34:29Z | `{"operationId":"a2664cb2-8f36-3ede-8e9e-11e76aba4481","correlationId":"89526984-bf4b-3306-a9be-a3fb7b579c93","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"a94d2fb6-0446-31ae-8fec-8cef014e9a23"}` |
| Retour reçu (`ReturnReceived`) | 59b63d69-8a4e-3f23-9086-de53821d97d1 | 2026-08-21T08:34:49Z | 2026-08-21T08:34:59Z | `{"operationId":"ee3efb1d-e74b-3569-a164-21f3c5f545ff","correlationId":"113e2010-3d5c-348f-ba58-a871b89e678b","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"a94d2fb6-0446-31ae-8fec-8cef014e9a23"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 5e142642-f6a6-3293-8e10-97c5ab582988 | 2026-08-22T08:34:19Z | 2026-08-22T08:34:29Z | `{"originalEventId":"59b63d69-8a4e-3f23-9086-de53821d97d1","correlationId":"113e2010-3d5c-348f-ba58-a871b89e678b","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 28742091-16a6-3421-a0ba-e03a1dbae035 | 2026-09-30T11:50:06Z | 2026-09-30T11:50:16Z | `{"operationId":"24999754-f628-3909-957f-569feadf3e4a","correlationId":"88cc6773-cbaf-3cb8-a6a9-4048a5201d14","siteId":"SUD","locationId":null,"loanId":"1c4bab2f-ef3f-33b2-b18e-cf85436b3351"}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"cd628ce3-3257-39d5-882a-7a0ae4df0355","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"cd628ce3-3257-39d5-882a-7a0ae4df0355","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cd628ce3-3257-39d5-882a-7a0ae4df0355","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cd628ce3-3257-39d5-882a-7a0ae4df0355","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cd628ce3-3257-39d5-882a-7a0ae4df0355","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cd628ce3-3257-39d5-882a-7a0ae4df0355","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cd628ce3-3257-39d5-882a-7a0ae4df0355","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"cd628ce3-3257-39d5-882a-7a0ae4df0355"},{"kind":"sourceEvent","id":"97ef24fe-9327-348e-b73b-5a96aa83a3c3"},{"kind":"sourceEvent","id":"7393fc45-a3a5-350c-a8c9-8ec3ac5a96ce"},{"kind":"sourceEvent","id":"97283fad-c4dc-323c-9ab3-024b64caf7ae"},{"kind":"sourceEvent","id":"f41ba236-59f8-30aa-bde0-4e1784959966"},{"kind":"sourceEvent","id":"3208ffde-2e88-3d91-8c48-56615c56b021"},{"kind":"sourceEvent","id":"59b63d69-8a4e-3f23-9086-de53821d97d1"},{"kind":"sourceEvent","id":"5e142642-f6a6-3293-8e10-97c5ab582988"},{"kind":"sourceEvent","id":"28742091-16a6-3421-a0ba-e03a1dbae035"},{"kind":"sourceAvailability","id":"cd628ce3-3257-39d5-882a-7a0ae4df0355/LOANS"},{"kind":"sourceAvailability","id":"cd628ce3-3257-39d5-882a-7a0ae4df0355/RETURNS"},{"kind":"sourceAvailability","id":"cd628ce3-3257-39d5-882a-7a0ae4df0355/RECEIPTS"},{"kind":"sourceAvailability","id":"cd628ce3-3257-39d5-882a-7a0ae4df0355/TRANSFERS"},{"kind":"sourceAvailability","id":"cd628ce3-3257-39d5-882a-7a0ae4df0355/REPAIRS"},{"kind":"sourceAvailability","id":"cd628ce3-3257-39d5-882a-7a0ae4df0355/INTEGRATIONS"},{"kind":"sourceAvailability","id":"cd628ce3-3257-39d5-882a-7a0ae4df0355/INVENTORY"},{"kind":"inventoryScope","id":"7f91fb45-70cc-3811-b222-43a5370aa433"},{"kind":"inventoryScope","id":"202855ec-9065-3be6-a0c2-07ce228ecf21"},{"kind":"inventoryScope","id":"1b4f280a-3145-3607-a009-dca44e9d4181"},{"kind":"inventoryScope","id":"2948b706-5519-3fcb-9c6a-82f0197080d2"},{"kind":"inventoryScope","id":"2977b6c8-c0f0-3d4f-aba5-6b5d16436fb8"}]`

- Session : `{"scopeId":"7f91fb45-70cc-3811-b222-43a5370aa433","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"202855ec-9065-3be6-a0c2-07ce228ecf21","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"1b4f280a-3145-3607-a009-dca44e9d4181","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2948b706-5519-3fcb-9c6a-82f0197080d2","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2977b6c8-c0f0-3d4f-aba5-6b5d16436fb8","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 34cf5765-ebc2-3a99-8fa9-da8f17e618f8 — dossier d'écart

- Exemplaire : `a4f78792-09c4-3eee-a039-1723e2e67e03`
- Famille privée : LOCALISATION ; variante : POSITIVE
- Symptômes : `[{"symptomId":"3a9f4b63-2078-3612-84cf-8c4860412f15","kind":"LOCATION_MISMATCH","expectedValue":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","observedValue":"2cf9a5ef-0690-3b0f-9031-67da0dd9d884"}]`
- Vérité injectée (non accessible au diagnostic) : `{"mechanism":"Déplacement physique sans modification de l'affectation ; aucune attribution à un opérateur.","activePerturbations":["LOCATION_CHANGE_UNPROJECTED"],"physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":"2cf9a5ef-0690-3b0f-9031-67da0dd9d884"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["LOCATION_MISMATCH"],"explanation":"Observation unique valide à un autre emplacement du même site ; écart établi, origine humaine non prouvée.","forbiddenClaims":["Vol","Perte définitive","Responsabilité d'un opérateur"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"a4f78792-09c4-3eee-a039-1723e2e67e03","status":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","sourceEventIds":["d592fbe0-e379-354e-8151-6ed9c3214950","94ce60e4-abb4-3568-ad7a-e0181511f694"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 800e06b8-5c90-3f1b-af8b-1a990655a2de | 2026-06-01T15:26:38Z | 2026-06-01T15:26:48Z | `{"operationId":"462f9d71-028f-3be0-ae24-b6bead912365","correlationId":"11157a65-bdb9-3685-9379-6792ea7dd477","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"35bd5b0a-db37-3b6b-8d45-20d2bb8c5e22"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 752676c6-bfba-31c1-9cc9-b76c097a7356 | 2026-06-02T15:26:38Z | 2026-06-02T15:26:48Z | `{"originalEventId":"800e06b8-5c90-3f1b-af8b-1a990655a2de","correlationId":"11157a65-bdb9-3685-9379-6792ea7dd477","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | a5883124-e7dc-3048-840f-1febb401f4c6 | 2026-07-31T15:26:38Z | 2026-07-31T15:26:48Z | `{"operationId":"89f28e9a-e3d3-3262-a00b-58b70df2d161","correlationId":"85cafbac-5f21-3754-a134-32e99288328b","siteId":"NORD","locationId":null,"loanId":"df39d7e7-58b9-3b1c-82cb-22f513543c7c"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 34baf58f-7627-3d31-97e7-dc7da3226736 | 2026-07-31T15:27:38Z | 2026-07-31T15:27:48Z | `{"originalEventId":"a5883124-e7dc-3048-840f-1febb401f4c6","correlationId":"85cafbac-5f21-3754-a134-32e99288328b","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 2391bc19-fdd3-36b5-a7df-d6eaeab63f5c | 2026-08-20T15:26:38Z | 2026-08-20T15:26:48Z | `{"operationId":"1e84fbe2-1238-38fa-bc34-367162b8a4cd","correlationId":"2168ce62-c11a-3e91-bf5f-55bb5e5644d8","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"df39d7e7-58b9-3b1c-82cb-22f513543c7c"}` |
| Retour reçu (`ReturnReceived`) | d592fbe0-e379-354e-8151-6ed9c3214950 | 2026-08-20T15:27:08Z | 2026-08-20T15:27:18Z | `{"operationId":"6d2d9d31-3c73-3adf-9b55-ee828be49f3f","correlationId":"9f441446-aa86-356f-b897-72e2e5e9be52","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"df39d7e7-58b9-3b1c-82cb-22f513543c7c"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 94ce60e4-abb4-3568-ad7a-e0181511f694 | 2026-08-21T15:26:38Z | 2026-08-21T15:26:48Z | `{"originalEventId":"d592fbe0-e379-354e-8151-6ed9c3214950","correlationId":"9f441446-aa86-356f-b897-72e2e5e9be52","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"01c71493-d5b9-3d11-9217-51ff3974cb23","copyId":"a4f78792-09c4-3eee-a039-1723e2e67e03","locationId":"2cf9a5ef-0690-3b0f-9031-67da0dd9d884","scopeId":"7655a45b-28c5-31ac-b928-8f2a685bd74a","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"a4f78792-09c4-3eee-a039-1723e2e67e03","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a4f78792-09c4-3eee-a039-1723e2e67e03","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a4f78792-09c4-3eee-a039-1723e2e67e03","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a4f78792-09c4-3eee-a039-1723e2e67e03","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a4f78792-09c4-3eee-a039-1723e2e67e03","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a4f78792-09c4-3eee-a039-1723e2e67e03","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a4f78792-09c4-3eee-a039-1723e2e67e03","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"observation","id":"01c71493-d5b9-3d11-9217-51ff3974cb23"},{"kind":"systemState","id":"a4f78792-09c4-3eee-a039-1723e2e67e03"}]`

## Cas 41a6a8d7-8cef-36b2-b4a8-a048781ee142 — dossier d'écart

- Exemplaire : `5fff5ee6-e470-3a08-a84e-c4efb8730dd9`
- Famille privée : RECEPTION_NON_INTEGREE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"758479d5-f151-31d3-a334-cea00b2d1884","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"7b82fbee-0320-3445-9a54-f8a49efbf577","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"5fff5ee6-e470-3a08-a84e-c4efb8730dd9","status":"RECEIVING","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","sourceEventIds":["7b82fbee-0320-3445-9a54-f8a49efbf577","45ad2b02-ec28-3845-8b20-9a770e5b8036"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 7b82fbee-0320-3445-9a54-f8a49efbf577 | 2026-09-27T08:46:31Z | 2026-09-27T08:46:41Z | `{"operationId":"4cb7ae34-cbb0-3889-84e7-c92c31c6dd3a","correlationId":"7350aa05-ca0e-3728-a9be-d73ed5423c9e","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"70b241e1-3c9d-3b49-a814-b24d5a002121"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | d521c16b-d3de-3e31-b4a1-ea08018fcce7 | 2026-09-27T08:47:01Z | 2026-09-27T08:47:11Z | `{"originalEventId":"7b82fbee-0320-3445-9a54-f8a49efbf577","correlationId":"7350aa05-ca0e-3728-a9be-d73ed5423c9e","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 45ad2b02-ec28-3845-8b20-9a770e5b8036 | 2026-09-27T08:48:01Z | 2026-09-27T08:48:11Z | `{"originalEventId":"7b82fbee-0320-3445-9a54-f8a49efbf577","correlationId":"7350aa05-ca0e-3728-a9be-d73ed5423c9e","outcome":"APPLIED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"1e4560a7-69c0-3d53-93ea-b72dd89f28b7","copyId":"5fff5ee6-e470-3a08-a84e-c4efb8730dd9","locationId":"2cf9a5ef-0690-3b0f-9031-67da0dd9d884","scopeId":"70a51414-a59b-31ce-a6f2-1079c623f504","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`
- `{"observationId":"1c1c8295-542b-310f-a148-2b6c46e94900","copyId":"5fff5ee6-e470-3a08-a84e-c4efb8730dd9","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","scopeId":"4a1b66bb-bf98-304a-94ad-c5e5581f221f","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"5fff5ee6-e470-3a08-a84e-c4efb8730dd9","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5fff5ee6-e470-3a08-a84e-c4efb8730dd9","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5fff5ee6-e470-3a08-a84e-c4efb8730dd9","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5fff5ee6-e470-3a08-a84e-c4efb8730dd9","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5fff5ee6-e470-3a08-a84e-c4efb8730dd9","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5fff5ee6-e470-3a08-a84e-c4efb8730dd9","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5fff5ee6-e470-3a08-a84e-c4efb8730dd9","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"5fff5ee6-e470-3a08-a84e-c4efb8730dd9"},{"kind":"sourceEvent","id":"7b82fbee-0320-3445-9a54-f8a49efbf577"},{"kind":"sourceEvent","id":"d521c16b-d3de-3e31-b4a1-ea08018fcce7"},{"kind":"sourceEvent","id":"45ad2b02-ec28-3845-8b20-9a770e5b8036"},{"kind":"observation","id":"1c1c8295-542b-310f-a148-2b6c46e94900"},{"kind":"inventoryScope","id":"4a1b66bb-bf98-304a-94ad-c5e5581f221f"},{"kind":"observation","id":"1e4560a7-69c0-3d53-93ea-b72dd89f28b7"},{"kind":"inventoryScope","id":"70a51414-a59b-31ce-a6f2-1079c623f504"},{"kind":"sourceAvailability","id":"5fff5ee6-e470-3a08-a84e-c4efb8730dd9/LOANS"},{"kind":"sourceAvailability","id":"5fff5ee6-e470-3a08-a84e-c4efb8730dd9/RETURNS"},{"kind":"sourceAvailability","id":"5fff5ee6-e470-3a08-a84e-c4efb8730dd9/RECEIPTS"},{"kind":"sourceAvailability","id":"5fff5ee6-e470-3a08-a84e-c4efb8730dd9/TRANSFERS"},{"kind":"sourceAvailability","id":"5fff5ee6-e470-3a08-a84e-c4efb8730dd9/REPAIRS"},{"kind":"sourceAvailability","id":"5fff5ee6-e470-3a08-a84e-c4efb8730dd9/INTEGRATIONS"},{"kind":"sourceAvailability","id":"5fff5ee6-e470-3a08-a84e-c4efb8730dd9/INVENTORY"},{"kind":"inventoryScope","id":"d1e77ae7-a841-38e1-b16e-d9b468a2d87a"},{"kind":"inventoryScope","id":"2d080b55-2867-3e1b-a766-49afaacecf82"},{"kind":"inventoryScope","id":"ff7cadbd-2e34-31a2-a383-62602b9d45d5"},{"kind":"inventoryScope","id":"dc947621-590d-3bee-a9d2-851a4ba45547"},{"kind":"inventoryScope","id":"4f5d7609-3713-3c2c-a8a6-b5bad10966de"},{"kind":"inventoryScope","id":"aedcfa99-c0dc-345a-8a76-b4763a7db1f5"},{"kind":"inventoryScope","id":"21de8fb6-32df-3328-b955-f4fd2d8252e5"}]`

- Session : `{"scopeId":"4a1b66bb-bf98-304a-94ad-c5e5581f221f","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"70a51414-a59b-31ce-a6f2-1079c623f504","locationId":"2cf9a5ef-0690-3b0f-9031-67da0dd9d884","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"d1e77ae7-a841-38e1-b16e-d9b468a2d87a","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2d080b55-2867-3e1b-a766-49afaacecf82","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"ff7cadbd-2e34-31a2-a383-62602b9d45d5","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"dc947621-590d-3bee-a9d2-851a4ba45547","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4f5d7609-3713-3c2c-a8a6-b5bad10966de","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"aedcfa99-c0dc-345a-8a76-b4763a7db1f5","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"21de8fb6-32df-3328-b955-f4fd2d8252e5","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 420577ca-5b6c-3d9b-be9f-606292dfda79 — dossier d'écart

- Exemplaire : `2257b41f-efdc-3e85-a990-f6c134cebea4`
- Famille privée : RETARD_INTEGRATION ; variante : POSITIVE
- Symptômes : `[{"symptomId":"b50de272-b157-3f22-bbbf-18bcea6cdffb","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["QUEUE_PENDING"],"hiddenSourceEvents":[],"mechanism":"Événement métier en attente normale dans la fenêtre contractuelle de 900 secondes.","businessEventId":"54fe8f68-c8b4-386a-9097-a1186190cfae","physicalStatus":"ON_LOAN","physicalSiteId":"SUD","physicalLocationId":null,"projectionPerturbation":"QUEUED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["INTEGRATION_PENDING_WITHIN_SLA"],"explanation":"Prêt actif et traitement en file depuis moins de 900 secondes ; transition attendue, pas de panne persistante démontrée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Recontrôler après la fenêtre de traitement"]}`
- État du stock informatique : `{"copyId":"2257b41f-efdc-3e85-a990-f6c134cebea4","status":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","sourceEventIds":["494f45d7-c3f4-3eea-b1f8-7737faccb703","ae96e606-760e-3f97-bdea-8de8980f6d43"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 330047f1-3b4d-332d-80c6-dc3ba8b99425 | 2026-06-02T05:48:37Z | 2026-06-02T05:48:47Z | `{"operationId":"2355cfe2-161f-3be8-a54b-bcbf570e1613","correlationId":"41c56fa5-4cb1-364e-9702-a820a3862ccf","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"a7d7163a-fec1-3d29-890c-41c1de6659f5"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | e0f92a1d-59e3-3310-93c8-297c50337bd4 | 2026-06-03T05:48:37Z | 2026-06-03T05:48:47Z | `{"originalEventId":"330047f1-3b4d-332d-80c6-dc3ba8b99425","correlationId":"41c56fa5-4cb1-364e-9702-a820a3862ccf","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 3cdf5f9c-f1e2-3792-abcc-fc465e2257fb | 2026-08-01T05:48:37Z | 2026-08-01T05:48:47Z | `{"operationId":"fd8ffd3f-4b43-3f46-b236-63cd4ce9320b","correlationId":"117e1c18-d31f-3a7f-a196-a0ee4312a0d6","siteId":"SUD","locationId":null,"loanId":"7fd0de45-b733-360e-9cbb-04480fedd69c"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 075abefa-9bdb-3db4-860c-eb9e0c62999e | 2026-08-01T05:49:37Z | 2026-08-01T05:49:47Z | `{"originalEventId":"3cdf5f9c-f1e2-3792-abcc-fc465e2257fb","correlationId":"117e1c18-d31f-3a7f-a196-a0ee4312a0d6","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | c1a99867-8bb1-3bc5-b6b4-53b08ecf3be6 | 2026-08-21T05:48:37Z | 2026-08-21T05:48:47Z | `{"operationId":"5288f6bd-278b-38e3-a843-671674e68bda","correlationId":"c88701a8-f9aa-3466-ac44-36173052e5d2","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"7fd0de45-b733-360e-9cbb-04480fedd69c"}` |
| Retour reçu (`ReturnReceived`) | 494f45d7-c3f4-3eea-b1f8-7737faccb703 | 2026-08-21T05:49:07Z | 2026-08-21T05:49:17Z | `{"operationId":"c0e43ef9-b72a-39a5-8da8-89061bdc11d7","correlationId":"c24401f3-b789-3ad3-87d0-b0d1d7513ef1","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"7fd0de45-b733-360e-9cbb-04480fedd69c"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | ae96e606-760e-3f97-bdea-8de8980f6d43 | 2026-08-22T05:48:37Z | 2026-08-22T05:48:47Z | `{"originalEventId":"494f45d7-c3f4-3eea-b1f8-7737faccb703","correlationId":"c24401f3-b789-3ad3-87d0-b0d1d7513ef1","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 54fe8f68-c8b4-386a-9097-a1186190cfae | 2026-09-30T11:52:29Z | 2026-09-30T11:52:39Z | `{"operationId":"b306cc77-c2ba-3c32-a032-fd68015fd3de","correlationId":"8ad8347c-958b-3100-b497-178388f64349","siteId":"SUD","locationId":null,"loanId":"7da05739-8018-3ecf-8ba8-4738c2a0c208"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 1c20c2b4-fa48-3ea8-a9fb-8f4f17fafadd | 2026-09-30T11:52:59Z | 2026-09-30T11:53:09Z | `{"originalEventId":"54fe8f68-c8b4-386a-9097-a1186190cfae","correlationId":"8ad8347c-958b-3100-b497-178388f64349","outcome":"QUEUED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"2257b41f-efdc-3e85-a990-f6c134cebea4","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2257b41f-efdc-3e85-a990-f6c134cebea4","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2257b41f-efdc-3e85-a990-f6c134cebea4","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2257b41f-efdc-3e85-a990-f6c134cebea4","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2257b41f-efdc-3e85-a990-f6c134cebea4","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2257b41f-efdc-3e85-a990-f6c134cebea4","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2257b41f-efdc-3e85-a990-f6c134cebea4","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"2257b41f-efdc-3e85-a990-f6c134cebea4"},{"kind":"sourceEvent","id":"330047f1-3b4d-332d-80c6-dc3ba8b99425"},{"kind":"sourceEvent","id":"e0f92a1d-59e3-3310-93c8-297c50337bd4"},{"kind":"sourceEvent","id":"3cdf5f9c-f1e2-3792-abcc-fc465e2257fb"},{"kind":"sourceEvent","id":"075abefa-9bdb-3db4-860c-eb9e0c62999e"},{"kind":"sourceEvent","id":"c1a99867-8bb1-3bc5-b6b4-53b08ecf3be6"},{"kind":"sourceEvent","id":"494f45d7-c3f4-3eea-b1f8-7737faccb703"},{"kind":"sourceEvent","id":"ae96e606-760e-3f97-bdea-8de8980f6d43"},{"kind":"sourceEvent","id":"54fe8f68-c8b4-386a-9097-a1186190cfae"},{"kind":"sourceEvent","id":"1c20c2b4-fa48-3ea8-a9fb-8f4f17fafadd"},{"kind":"sourceAvailability","id":"2257b41f-efdc-3e85-a990-f6c134cebea4/LOANS"},{"kind":"sourceAvailability","id":"2257b41f-efdc-3e85-a990-f6c134cebea4/RETURNS"},{"kind":"sourceAvailability","id":"2257b41f-efdc-3e85-a990-f6c134cebea4/RECEIPTS"},{"kind":"sourceAvailability","id":"2257b41f-efdc-3e85-a990-f6c134cebea4/TRANSFERS"},{"kind":"sourceAvailability","id":"2257b41f-efdc-3e85-a990-f6c134cebea4/REPAIRS"},{"kind":"sourceAvailability","id":"2257b41f-efdc-3e85-a990-f6c134cebea4/INTEGRATIONS"},{"kind":"sourceAvailability","id":"2257b41f-efdc-3e85-a990-f6c134cebea4/INVENTORY"},{"kind":"inventoryScope","id":"7f91fb45-70cc-3811-b222-43a5370aa433"},{"kind":"inventoryScope","id":"202855ec-9065-3be6-a0c2-07ce228ecf21"},{"kind":"inventoryScope","id":"1b4f280a-3145-3607-a009-dca44e9d4181"},{"kind":"inventoryScope","id":"2948b706-5519-3fcb-9c6a-82f0197080d2"},{"kind":"inventoryScope","id":"2977b6c8-c0f0-3d4f-aba5-6b5d16436fb8"}]`

- Session : `{"scopeId":"7f91fb45-70cc-3811-b222-43a5370aa433","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"202855ec-9065-3be6-a0c2-07ce228ecf21","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"1b4f280a-3145-3607-a009-dca44e9d4181","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2948b706-5519-3fcb-9c6a-82f0197080d2","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2977b6c8-c0f0-3d4f-aba5-6b5d16436fb8","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 44dbf757-0a51-388a-a11d-e56165602bd1 — dossier d'écart

- Exemplaire : `a83896d6-4029-32bb-9327-0c2b74f01eef`
- Famille privée : RETARD_INTEGRATION ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"6aea32e3-46d5-3fff-bb66-f922a4b48a8d","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["QUEUE_PENDING"],"hiddenSourceEvents":[{"sourceEventId":"52317aa2-5b13-3108-927e-c54cd8fb5711","copyId":"a83896d6-4029-32bb-9327-0c2b74f01eef","eventType":"IntegrationProcessed","occurredAt":"2026-09-30T11:52:50Z","recordedAt":"2026-09-30T11:53:00Z","payload":{"originalEventId":"c4f92b5f-28d1-33a1-822e-89b8f0e9cffc","correlationId":"930e1fb0-ec20-3d38-917f-efb554c84a6f","outcome":"QUEUED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1}}],"mechanism":"Événement métier en attente normale dans la fenêtre contractuelle de 900 secondes.","businessEventId":"c4f92b5f-28d1-33a1-822e-89b8f0e9cffc","physicalStatus":"ON_LOAN","physicalSiteId":"SUD","physicalLocationId":null,"projectionPerturbation":"QUEUED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État du stock informatique : `{"copyId":"a83896d6-4029-32bb-9327-0c2b74f01eef","status":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","sourceEventIds":["8c963f18-a03e-3195-9e23-6d9abe00509d","8626d02b-7e65-3b96-b58f-a1f13cbf0859"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 9b167255-5264-3c19-a4cb-4b61063906ab | 2026-06-01T21:28:38Z | 2026-06-01T21:28:48Z | `{"operationId":"bc491a12-6e24-30fb-9ac8-264a73e8c2c1","correlationId":"4f3ac3b5-dcd5-36f2-8622-d4ebad932754","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"c78b8b3a-f230-38e5-8819-5ea3d53bfefb"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 291e8a9f-f569-3a41-bc49-f0694b949134 | 2026-06-02T21:28:38Z | 2026-06-02T21:28:48Z | `{"originalEventId":"9b167255-5264-3c19-a4cb-4b61063906ab","correlationId":"4f3ac3b5-dcd5-36f2-8622-d4ebad932754","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 641c7662-1616-3d3a-958c-cfd7d3e3d174 | 2026-07-31T21:28:38Z | 2026-07-31T21:28:48Z | `{"operationId":"9c44e8d7-9478-3dcf-8684-aaa857cde861","correlationId":"38d36657-1266-334f-996f-f9e9a19621e5","siteId":"SUD","locationId":null,"loanId":"b4af3554-387b-37d8-85f3-be1dbe43527a"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | d9496936-07d7-383d-80c8-dbf876ea98f3 | 2026-07-31T21:29:38Z | 2026-07-31T21:29:48Z | `{"originalEventId":"641c7662-1616-3d3a-958c-cfd7d3e3d174","correlationId":"38d36657-1266-334f-996f-f9e9a19621e5","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 2cdc5309-3f0e-3bec-bbc1-ac8f491042ac | 2026-08-20T21:28:38Z | 2026-08-20T21:28:48Z | `{"operationId":"be85c6b1-025a-31aa-b3b5-c52ac5db52f1","correlationId":"86752856-c5e7-3edd-b4ee-4e71c62ba00d","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"b4af3554-387b-37d8-85f3-be1dbe43527a"}` |
| Retour reçu (`ReturnReceived`) | 8c963f18-a03e-3195-9e23-6d9abe00509d | 2026-08-20T21:29:08Z | 2026-08-20T21:29:18Z | `{"operationId":"1c2e481c-34a8-330f-99f6-ff4d86ed419d","correlationId":"ebe43535-1b34-3685-816e-cd6b5d5aa4a4","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"b4af3554-387b-37d8-85f3-be1dbe43527a"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 8626d02b-7e65-3b96-b58f-a1f13cbf0859 | 2026-08-21T21:28:38Z | 2026-08-21T21:28:48Z | `{"originalEventId":"8c963f18-a03e-3195-9e23-6d9abe00509d","correlationId":"ebe43535-1b34-3685-816e-cd6b5d5aa4a4","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | c4f92b5f-28d1-33a1-822e-89b8f0e9cffc | 2026-09-30T11:52:20Z | 2026-09-30T11:52:30Z | `{"operationId":"7d408885-9928-38fd-a5e3-e4173c5e5fbd","correlationId":"930e1fb0-ec20-3d38-917f-efb554c84a6f","siteId":"SUD","locationId":null,"loanId":"8d82fa3a-b726-3e3e-ba10-f1ecbbbfe7ba"}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"a83896d6-4029-32bb-9327-0c2b74f01eef","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"a83896d6-4029-32bb-9327-0c2b74f01eef","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a83896d6-4029-32bb-9327-0c2b74f01eef","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a83896d6-4029-32bb-9327-0c2b74f01eef","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a83896d6-4029-32bb-9327-0c2b74f01eef","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a83896d6-4029-32bb-9327-0c2b74f01eef","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a83896d6-4029-32bb-9327-0c2b74f01eef","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"a83896d6-4029-32bb-9327-0c2b74f01eef"},{"kind":"sourceEvent","id":"9b167255-5264-3c19-a4cb-4b61063906ab"},{"kind":"sourceEvent","id":"291e8a9f-f569-3a41-bc49-f0694b949134"},{"kind":"sourceEvent","id":"641c7662-1616-3d3a-958c-cfd7d3e3d174"},{"kind":"sourceEvent","id":"d9496936-07d7-383d-80c8-dbf876ea98f3"},{"kind":"sourceEvent","id":"2cdc5309-3f0e-3bec-bbc1-ac8f491042ac"},{"kind":"sourceEvent","id":"8c963f18-a03e-3195-9e23-6d9abe00509d"},{"kind":"sourceEvent","id":"8626d02b-7e65-3b96-b58f-a1f13cbf0859"},{"kind":"sourceEvent","id":"c4f92b5f-28d1-33a1-822e-89b8f0e9cffc"},{"kind":"sourceAvailability","id":"a83896d6-4029-32bb-9327-0c2b74f01eef/LOANS"},{"kind":"sourceAvailability","id":"a83896d6-4029-32bb-9327-0c2b74f01eef/RETURNS"},{"kind":"sourceAvailability","id":"a83896d6-4029-32bb-9327-0c2b74f01eef/RECEIPTS"},{"kind":"sourceAvailability","id":"a83896d6-4029-32bb-9327-0c2b74f01eef/TRANSFERS"},{"kind":"sourceAvailability","id":"a83896d6-4029-32bb-9327-0c2b74f01eef/REPAIRS"},{"kind":"sourceAvailability","id":"a83896d6-4029-32bb-9327-0c2b74f01eef/INTEGRATIONS"},{"kind":"sourceAvailability","id":"a83896d6-4029-32bb-9327-0c2b74f01eef/INVENTORY"},{"kind":"inventoryScope","id":"7f91fb45-70cc-3811-b222-43a5370aa433"},{"kind":"inventoryScope","id":"202855ec-9065-3be6-a0c2-07ce228ecf21"},{"kind":"inventoryScope","id":"1b4f280a-3145-3607-a009-dca44e9d4181"},{"kind":"inventoryScope","id":"2948b706-5519-3fcb-9c6a-82f0197080d2"},{"kind":"inventoryScope","id":"2977b6c8-c0f0-3d4f-aba5-6b5d16436fb8"}]`

- Session : `{"scopeId":"7f91fb45-70cc-3811-b222-43a5370aa433","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"202855ec-9065-3be6-a0c2-07ce228ecf21","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"1b4f280a-3145-3607-a009-dca44e9d4181","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2948b706-5519-3fcb-9c6a-82f0197080d2","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2977b6c8-c0f0-3d4f-aba5-6b5d16436fb8","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 46b7cd1e-51dd-3345-8865-9787c517a7cb — dossier d'écart

- Exemplaire : `c4ed3458-551b-38a9-86bc-1435eecd46cc`
- Famille privée : RETOUR_NON_PROJETE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"b76ebb4b-f8b9-3a9a-9c2f-e930499ae917","kind":"STATUS_MISMATCH","expectedValue":"ON_LOAN","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"42264dc8-2532-35f0-aad0-0e3cb011484f","copyId":"c4ed3458-551b-38a9-86bc-1435eecd46cc","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T08:33:35Z","recordedAt":"2026-09-27T08:33:45Z","payload":{"originalEventId":"512b357f-29de-3199-a8cc-ec56319dac70","correlationId":"1d2a6b2d-76c0-3b2e-bf9a-9925dab48f1c","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"512b357f-29de-3199-a8cc-ec56319dac70","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État du stock informatique : `{"copyId":"c4ed3458-551b-38a9-86bc-1435eecd46cc","status":"ON_LOAN","siteId":"CENTRE","locationId":null,"sourceEventIds":["f4fb418b-4256-3a19-be5a-ba401f094125","1193fd64-794e-310b-99b1-7204182b9cb4"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 07f972ed-f236-3dfe-9454-02368115df1f | 2026-06-02T06:10:00Z | 2026-06-02T06:10:10Z | `{"operationId":"fd8e5ac1-c76d-3884-b028-f31ecdfc64b3","correlationId":"e328f99a-e794-345e-8a23-9206f84837f1","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"cb9db6e5-fe79-31a8-b119-85fbc46ae047"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 871d2dcf-33ab-377d-af2c-d1418b72193b | 2026-06-03T06:10:00Z | 2026-06-03T06:10:10Z | `{"originalEventId":"07f972ed-f236-3dfe-9454-02368115df1f","correlationId":"e328f99a-e794-345e-8a23-9206f84837f1","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 65de74ba-276c-34be-a8b0-0fb90e4f088a | 2026-08-01T06:10:00Z | 2026-08-01T06:10:10Z | `{"operationId":"04a92a34-9b86-39aa-b188-8616d1b1e5be","correlationId":"27253ec0-d29c-3fb5-b093-87845661b800","siteId":"CENTRE","locationId":null,"loanId":"bf0e181e-0c20-3718-9d18-acbc20f65e51"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 66a1bf59-e91a-3c5a-bdfd-fae925f28b9f | 2026-08-01T06:11:00Z | 2026-08-01T06:11:10Z | `{"originalEventId":"65de74ba-276c-34be-a8b0-0fb90e4f088a","correlationId":"27253ec0-d29c-3fb5-b093-87845661b800","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | e88952c9-f616-37f4-9000-ca829420ddb7 | 2026-08-21T06:10:00Z | 2026-08-21T06:10:10Z | `{"operationId":"74b1182c-4eb8-39eb-9e62-5cce8db62909","correlationId":"ca8ea4ec-1d00-33aa-b32c-ca5c21a357af","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"bf0e181e-0c20-3718-9d18-acbc20f65e51"}` |
| Retour reçu (`ReturnReceived`) | 585fd0cd-9635-37dd-80ea-3fed0c0c851d | 2026-08-21T06:10:30Z | 2026-08-21T06:10:40Z | `{"operationId":"4f865b59-e297-34a9-b6c8-25343858ef51","correlationId":"497360f7-8a39-31f1-aa1f-253686e3b9d5","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"bf0e181e-0c20-3718-9d18-acbc20f65e51"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | cad118f0-6cfa-304b-bc59-234915313be9 | 2026-08-22T06:10:00Z | 2026-08-22T06:10:10Z | `{"originalEventId":"585fd0cd-9635-37dd-80ea-3fed0c0c851d","correlationId":"497360f7-8a39-31f1-aa1f-253686e3b9d5","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | f4fb418b-4256-3a19-be5a-ba401f094125 | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"f56a4577-7153-3fdd-b40e-0795e52adb52","correlationId":"202e536b-7ff4-3839-a632-9294c2067f31","siteId":"CENTRE","locationId":null,"loanId":"89e200cd-5546-3f91-b186-b559114d323c"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 1193fd64-794e-310b-99b1-7204182b9cb4 | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"f4fb418b-4256-3a19-be5a-ba401f094125","correlationId":"202e536b-7ff4-3839-a632-9294c2067f31","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | f68e9b62-3a0f-3cbf-8191-635276c35344 | 2026-09-27T08:32:35Z | 2026-09-27T08:32:45Z | `{"operationId":"b3eea9cc-3e70-32cc-a16c-8f039bb62979","correlationId":"1d6c6ca6-9173-3ae5-bb29-6b87da74302b","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"89e200cd-5546-3f91-b186-b559114d323c"}` |
| Retour reçu (`ReturnReceived`) | 512b357f-29de-3199-a8cc-ec56319dac70 | 2026-09-27T08:33:05Z | 2026-09-27T08:33:15Z | `{"operationId":"112036df-bd03-38ad-b330-506a6f59d035","correlationId":"1d2a6b2d-76c0-3b2e-bf9a-9925dab48f1c","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"89e200cd-5546-3f91-b186-b559114d323c"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"1e05fbfd-72b2-355d-ba81-ef8b5214e146","copyId":"c4ed3458-551b-38a9-86bc-1435eecd46cc","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","scopeId":"5b78923b-b92a-39e3-a61c-131cd65d968a","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"c4ed3458-551b-38a9-86bc-1435eecd46cc","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c4ed3458-551b-38a9-86bc-1435eecd46cc","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c4ed3458-551b-38a9-86bc-1435eecd46cc","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c4ed3458-551b-38a9-86bc-1435eecd46cc","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c4ed3458-551b-38a9-86bc-1435eecd46cc","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c4ed3458-551b-38a9-86bc-1435eecd46cc","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"c4ed3458-551b-38a9-86bc-1435eecd46cc","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"c4ed3458-551b-38a9-86bc-1435eecd46cc"},{"kind":"sourceEvent","id":"07f972ed-f236-3dfe-9454-02368115df1f"},{"kind":"sourceEvent","id":"871d2dcf-33ab-377d-af2c-d1418b72193b"},{"kind":"sourceEvent","id":"65de74ba-276c-34be-a8b0-0fb90e4f088a"},{"kind":"sourceEvent","id":"66a1bf59-e91a-3c5a-bdfd-fae925f28b9f"},{"kind":"sourceEvent","id":"e88952c9-f616-37f4-9000-ca829420ddb7"},{"kind":"sourceEvent","id":"585fd0cd-9635-37dd-80ea-3fed0c0c851d"},{"kind":"sourceEvent","id":"cad118f0-6cfa-304b-bc59-234915313be9"},{"kind":"sourceEvent","id":"f4fb418b-4256-3a19-be5a-ba401f094125"},{"kind":"sourceEvent","id":"1193fd64-794e-310b-99b1-7204182b9cb4"},{"kind":"sourceEvent","id":"f68e9b62-3a0f-3cbf-8191-635276c35344"},{"kind":"sourceEvent","id":"512b357f-29de-3199-a8cc-ec56319dac70"},{"kind":"observation","id":"1e05fbfd-72b2-355d-ba81-ef8b5214e146"},{"kind":"inventoryScope","id":"5b78923b-b92a-39e3-a61c-131cd65d968a"},{"kind":"sourceAvailability","id":"c4ed3458-551b-38a9-86bc-1435eecd46cc/LOANS"},{"kind":"sourceAvailability","id":"c4ed3458-551b-38a9-86bc-1435eecd46cc/RETURNS"},{"kind":"sourceAvailability","id":"c4ed3458-551b-38a9-86bc-1435eecd46cc/RECEIPTS"},{"kind":"sourceAvailability","id":"c4ed3458-551b-38a9-86bc-1435eecd46cc/TRANSFERS"},{"kind":"sourceAvailability","id":"c4ed3458-551b-38a9-86bc-1435eecd46cc/REPAIRS"},{"kind":"sourceAvailability","id":"c4ed3458-551b-38a9-86bc-1435eecd46cc/INTEGRATIONS"},{"kind":"sourceAvailability","id":"c4ed3458-551b-38a9-86bc-1435eecd46cc/INVENTORY"}]`

- Session : `{"scopeId":"5b78923b-b92a-39e3-a61c-131cd65d968a","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 4877a708-e6fd-37a6-8b12-34a2addaf438 — dossier d'écart

- Exemplaire : `af322f7c-5ceb-375e-9ee2-5889e761afb6`
- Famille privée : LOCALISATION ; variante : POSITIVE
- Symptômes : `[{"symptomId":"6bdcfc2e-0c9f-3ffe-86f8-f7f6a3c856e7","kind":"LOCATION_MISMATCH","expectedValue":"00f09742-c706-31b7-a4a0-c694a087403f","observedValue":"128fab03-f193-3647-a67e-b835a30e7583"}]`
- Vérité injectée (non accessible au diagnostic) : `{"mechanism":"Déplacement physique sans modification de l'affectation ; aucune attribution à un opérateur.","activePerturbations":["LOCATION_CHANGE_UNPROJECTED"],"physicalStatus":"ON_SHELF","physicalSiteId":"SUD","physicalLocationId":"128fab03-f193-3647-a67e-b835a30e7583"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["LOCATION_MISMATCH"],"explanation":"Observation unique valide à un autre emplacement du même site ; écart établi, origine humaine non prouvée.","forbiddenClaims":["Vol","Perte définitive","Responsabilité d'un opérateur"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"af322f7c-5ceb-375e-9ee2-5889e761afb6","status":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","sourceEventIds":["12d9ad06-5b50-3de9-99ed-8cf2dd7c4b03","25da57dc-aa59-3e89-87a1-3ed2e2e15618"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 7d9b4d41-3edf-3eda-955b-ab0f01611580 | 2026-06-01T22:59:13Z | 2026-06-01T22:59:23Z | `{"operationId":"327aaea5-99df-3c92-bfcd-6a791eb22f6e","correlationId":"708efb43-9a87-38fc-959d-110bfbdb2163","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"dc08c0c3-e384-326c-8aea-65b9a324b43a"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 3d5d9cbf-46d9-34ed-a438-83f2597186b9 | 2026-06-02T22:59:13Z | 2026-06-02T22:59:23Z | `{"originalEventId":"7d9b4d41-3edf-3eda-955b-ab0f01611580","correlationId":"708efb43-9a87-38fc-959d-110bfbdb2163","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | 3327d7d0-9d65-34fd-860c-b5124ec68781 | 2026-07-31T22:59:13Z | 2026-07-31T22:59:23Z | `{"operationId":"3391c69b-2862-33d2-8bd3-f8ce380b15fe","correlationId":"ad99c18b-551e-3b4a-81ba-768efe89d828","siteId":"NORD","locationId":null,"loanId":"7b12624e-4277-3e1d-943c-b6e8fb39f927"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 3eec090f-9f1e-3456-868f-933a028052f1 | 2026-07-31T23:00:13Z | 2026-07-31T23:00:23Z | `{"originalEventId":"3327d7d0-9d65-34fd-860c-b5124ec68781","correlationId":"ad99c18b-551e-3b4a-81ba-768efe89d828","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 4feeea23-7d4a-3aea-881a-e4d3c84a3e56 | 2026-08-20T22:59:13Z | 2026-08-20T22:59:23Z | `{"operationId":"deeac0c7-0d15-3568-b301-8c9cc15c47d1","correlationId":"98b4f1cf-d52b-3386-b2fb-8c83e526878c","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"7b12624e-4277-3e1d-943c-b6e8fb39f927"}` |
| Retour reçu (`ReturnReceived`) | 0c0077ba-7953-3c59-8f2d-7d6109b48659 | 2026-08-20T22:59:43Z | 2026-08-20T22:59:53Z | `{"operationId":"aa01c95e-f476-3755-be70-ac3b60c98ed1","correlationId":"65ff1e87-cfdd-32db-aeac-4378d935cd82","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"7b12624e-4277-3e1d-943c-b6e8fb39f927"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | d811ce57-be24-3ed7-b047-58844ae7c7fb | 2026-08-21T22:59:13Z | 2026-08-21T22:59:23Z | `{"originalEventId":"0c0077ba-7953-3c59-8f2d-7d6109b48659","correlationId":"65ff1e87-cfdd-32db-aeac-4378d935cd82","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Transfert expédié (`TransferDispatched`) | ffbed1fc-60af-38b0-81ec-e9c382b79e41 | 2026-08-30T22:59:13Z | 2026-08-30T22:59:23Z | `{"operationId":"fcde02fb-e6ac-3e3e-a448-80a384bf899c","correlationId":"fee8bd58-f218-3ac3-a209-c5e7b7031d91","siteId":"NORD","locationId":null,"transferId":"b0780848-783f-3d23-99c2-c8d419f1695a","fromSiteId":"NORD","toSiteId":"SUD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 5f31ba1b-c840-3cf7-b3eb-7c50494ce4f0 | 2026-08-30T23:00:13Z | 2026-08-30T23:00:23Z | `{"originalEventId":"ffbed1fc-60af-38b0-81ec-e9c382b79e41","correlationId":"fee8bd58-f218-3ac3-a209-c5e7b7031d91","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"NORD","locationId":null}` |
| Transfert reçu (`TransferReceived`) | 12d9ad06-5b50-3de9-99ed-8cf2dd7c4b03 | 2026-08-31T22:59:13Z | 2026-08-31T22:59:23Z | `{"operationId":"0c4f493e-17a0-3565-869b-841ecd1d109e","correlationId":"58e6bb5b-3c19-3391-8ed2-13c024acb4fa","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","transferId":"b0780848-783f-3d23-99c2-c8d419f1695a","fromSiteId":"NORD","toSiteId":"SUD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 25da57dc-aa59-3e89-87a1-3ed2e2e15618 | 2026-09-01T22:59:13Z | 2026-09-01T22:59:23Z | `{"originalEventId":"12d9ad06-5b50-3de9-99ed-8cf2dd7c4b03","correlationId":"58e6bb5b-3c19-3391-8ed2-13c024acb4fa","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"fe5d6876-56a0-3549-9d80-0956ea04e928","copyId":"af322f7c-5ceb-375e-9ee2-5889e761afb6","locationId":"128fab03-f193-3647-a67e-b835a30e7583","scopeId":"c22ce4b3-52b3-3cb9-b18f-46d9249b712b","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"af322f7c-5ceb-375e-9ee2-5889e761afb6","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"af322f7c-5ceb-375e-9ee2-5889e761afb6","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"af322f7c-5ceb-375e-9ee2-5889e761afb6","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"af322f7c-5ceb-375e-9ee2-5889e761afb6","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"af322f7c-5ceb-375e-9ee2-5889e761afb6","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"af322f7c-5ceb-375e-9ee2-5889e761afb6","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"af322f7c-5ceb-375e-9ee2-5889e761afb6","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"observation","id":"fe5d6876-56a0-3549-9d80-0956ea04e928"},{"kind":"systemState","id":"af322f7c-5ceb-375e-9ee2-5889e761afb6"}]`

## Cas 50e8925c-d906-3c20-b276-69c6e758f509 — dossier d'écart

- Exemplaire : `f3c37b16-0537-32db-a6d9-126a606d9bf2`
- Famille privée : RECEPTION_NON_INTEGREE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"a9140473-f562-3d53-9671-c2d9e0874be4","kind":"UNEXPECTED_PRESENCE","expectedValue":"NOT_REGISTERED","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"28ca817f-9930-3955-a64f-a4f57a0f2232","physicalStatus":"RECEIVING","physicalSiteId":"SUD","physicalLocationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["RECEIPT_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"f3c37b16-0537-32db-a6d9-126a606d9bf2","status":"NOT_REGISTERED","siteId":"SUD","locationId":null,"sourceEventIds":[]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 28ca817f-9930-3955-a64f-a4f57a0f2232 | 2026-09-27T07:10:32Z | 2026-09-27T07:10:42Z | `{"operationId":"5a0068bb-e19d-30f4-84d0-aab9fed6af22","correlationId":"5e09d8c7-4de3-30be-8356-1988ae809fd8","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"9318c32d-0266-31db-98b1-cfa2f52e94ae"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | de937bb5-13ae-3820-8b35-e0cba87abb55 | 2026-09-27T07:11:02Z | 2026-09-27T07:11:12Z | `{"originalEventId":"28ca817f-9930-3955-a64f-a4f57a0f2232","correlationId":"5e09d8c7-4de3-30be-8356-1988ae809fd8","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"9fa613f1-a567-3d10-a631-addb4adce821","copyId":"f3c37b16-0537-32db-a6d9-126a606d9bf2","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","scopeId":"43c39e81-8ce5-3b42-8e9c-57e4bb397811","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"f3c37b16-0537-32db-a6d9-126a606d9bf2","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f3c37b16-0537-32db-a6d9-126a606d9bf2","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f3c37b16-0537-32db-a6d9-126a606d9bf2","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f3c37b16-0537-32db-a6d9-126a606d9bf2","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f3c37b16-0537-32db-a6d9-126a606d9bf2","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f3c37b16-0537-32db-a6d9-126a606d9bf2","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f3c37b16-0537-32db-a6d9-126a606d9bf2","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"f3c37b16-0537-32db-a6d9-126a606d9bf2"},{"kind":"sourceEvent","id":"28ca817f-9930-3955-a64f-a4f57a0f2232"},{"kind":"sourceEvent","id":"de937bb5-13ae-3820-8b35-e0cba87abb55"},{"kind":"observation","id":"9fa613f1-a567-3d10-a631-addb4adce821"},{"kind":"inventoryScope","id":"43c39e81-8ce5-3b42-8e9c-57e4bb397811"},{"kind":"sourceAvailability","id":"f3c37b16-0537-32db-a6d9-126a606d9bf2/LOANS"},{"kind":"sourceAvailability","id":"f3c37b16-0537-32db-a6d9-126a606d9bf2/RETURNS"},{"kind":"sourceAvailability","id":"f3c37b16-0537-32db-a6d9-126a606d9bf2/RECEIPTS"},{"kind":"sourceAvailability","id":"f3c37b16-0537-32db-a6d9-126a606d9bf2/TRANSFERS"},{"kind":"sourceAvailability","id":"f3c37b16-0537-32db-a6d9-126a606d9bf2/REPAIRS"},{"kind":"sourceAvailability","id":"f3c37b16-0537-32db-a6d9-126a606d9bf2/INTEGRATIONS"},{"kind":"sourceAvailability","id":"f3c37b16-0537-32db-a6d9-126a606d9bf2/INVENTORY"}]`

- Session : `{"scopeId":"43c39e81-8ce5-3b42-8e9c-57e4bb397811","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 524187e5-1679-3687-b252-9a569df6df40 — dossier d'écart

- Exemplaire : `51226ebe-ced6-3630-8a9a-a338963764cd`
- Famille privée : RECEPTION_NON_INTEGREE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"e78a5b42-33f9-3e62-91f5-0846c91501e6","kind":"UNEXPECTED_PRESENCE","expectedValue":"NOT_REGISTERED","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"d8296317-75dd-3898-9016-a9ad86bfb71c","copyId":"51226ebe-ced6-3630-8a9a-a338963764cd","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T08:23:46Z","recordedAt":"2026-09-27T08:23:56Z","payload":{"originalEventId":"89e4d36d-b831-3c90-a81f-7b4558c9c119","correlationId":"96b7a63e-4042-30cd-ade5-4a214af32506","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"89e4d36d-b831-3c90-a81f-7b4558c9c119","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"125bed65-7385-3595-a653-902580e9539f","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État du stock informatique : `{"copyId":"51226ebe-ced6-3630-8a9a-a338963764cd","status":"NOT_REGISTERED","siteId":"CENTRE","locationId":null,"sourceEventIds":[]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 89e4d36d-b831-3c90-a81f-7b4558c9c119 | 2026-09-27T08:23:16Z | 2026-09-27T08:23:26Z | `{"operationId":"f616f26f-de5a-3496-a098-443e851b7e98","correlationId":"96b7a63e-4042-30cd-ade5-4a214af32506","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"7cd5639a-3b86-3cd7-9f62-22ac965b0515"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"1819a04e-c266-3bb5-b9e7-80b4caaa15ae","copyId":"51226ebe-ced6-3630-8a9a-a338963764cd","locationId":"125bed65-7385-3595-a653-902580e9539f","scopeId":"f4d4ba30-3269-3685-a5cc-6e27fa487204","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"51226ebe-ced6-3630-8a9a-a338963764cd","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"51226ebe-ced6-3630-8a9a-a338963764cd","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"51226ebe-ced6-3630-8a9a-a338963764cd","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"51226ebe-ced6-3630-8a9a-a338963764cd","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"51226ebe-ced6-3630-8a9a-a338963764cd","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"51226ebe-ced6-3630-8a9a-a338963764cd","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"51226ebe-ced6-3630-8a9a-a338963764cd","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"51226ebe-ced6-3630-8a9a-a338963764cd"},{"kind":"sourceEvent","id":"89e4d36d-b831-3c90-a81f-7b4558c9c119"},{"kind":"observation","id":"1819a04e-c266-3bb5-b9e7-80b4caaa15ae"},{"kind":"inventoryScope","id":"f4d4ba30-3269-3685-a5cc-6e27fa487204"},{"kind":"sourceAvailability","id":"51226ebe-ced6-3630-8a9a-a338963764cd/LOANS"},{"kind":"sourceAvailability","id":"51226ebe-ced6-3630-8a9a-a338963764cd/RETURNS"},{"kind":"sourceAvailability","id":"51226ebe-ced6-3630-8a9a-a338963764cd/RECEIPTS"},{"kind":"sourceAvailability","id":"51226ebe-ced6-3630-8a9a-a338963764cd/TRANSFERS"},{"kind":"sourceAvailability","id":"51226ebe-ced6-3630-8a9a-a338963764cd/REPAIRS"},{"kind":"sourceAvailability","id":"51226ebe-ced6-3630-8a9a-a338963764cd/INTEGRATIONS"},{"kind":"sourceAvailability","id":"51226ebe-ced6-3630-8a9a-a338963764cd/INVENTORY"}]`

- Session : `{"scopeId":"f4d4ba30-3269-3685-a5cc-6e27fa487204","locationId":"125bed65-7385-3595-a653-902580e9539f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 570ade6d-a449-386a-8d4b-edff38b0dde9 — dossier d'écart

- Exemplaire : `73e2a754-6a5c-35ea-a36e-0e9bd85ed181`
- Famille privée : REPARATION_NON_PROJETEE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"99331857-6b71-3149-bac3-d58b14ba2741","kind":"STATUS_MISMATCH","expectedValue":"ON_SHELF","observedValue":"IN_REPAIR"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"94734c9c-a3ce-39f6-941b-a4dc1a7e4613","physicalStatus":"IN_REPAIR","physicalSiteId":"CENTRE","physicalLocationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"73e2a754-6a5c-35ea-a36e-0e9bd85ed181","status":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","sourceEventIds":["13ec7ed5-6aec-3617-bbdf-d02abe909f17","613c2675-e0d3-3dfb-9d01-35617a6b6dc3"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | d963b96d-9846-3860-a06f-7fad0f5e5586 | 2026-06-02T10:48:03Z | 2026-06-02T10:48:13Z | `{"operationId":"75d67d29-361b-3120-ba60-7bf29b985bb1","correlationId":"8299f6a8-f9a6-3ec0-b701-432c054edb28","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"d07dcdf4-40a8-39dd-8bde-857e667d690e"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 0259c1d7-5c52-380d-b10a-6f853ddf1f2b | 2026-06-03T10:48:03Z | 2026-06-03T10:48:13Z | `{"originalEventId":"d963b96d-9846-3860-a06f-7fad0f5e5586","correlationId":"8299f6a8-f9a6-3ec0-b701-432c054edb28","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 5d1a861d-5ea4-3208-b340-d9ce2fde1134 | 2026-08-01T10:48:03Z | 2026-08-01T10:48:13Z | `{"operationId":"cfa1aced-b567-3182-84d9-f10af4e63585","correlationId":"47b8b830-8945-3eb9-9b05-987fe81f7645","siteId":"SUD","locationId":null,"loanId":"cc838b29-dd0b-3f98-8bc8-bfbb03035666"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | e59317cf-9455-3b08-9ce9-29b513d2eb2d | 2026-08-01T10:49:03Z | 2026-08-01T10:49:13Z | `{"originalEventId":"5d1a861d-5ea4-3208-b340-d9ce2fde1134","correlationId":"47b8b830-8945-3eb9-9b05-987fe81f7645","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 7a02f7e9-0065-35e8-8165-8efc499b4e29 | 2026-08-21T10:48:03Z | 2026-08-21T10:48:13Z | `{"operationId":"ff67002d-75f3-3a5d-b0df-4c2c124ff5a3","correlationId":"a3f2dd0b-540b-3497-a4fc-51bcfbae5d86","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"cc838b29-dd0b-3f98-8bc8-bfbb03035666"}` |
| Retour reçu (`ReturnReceived`) | 5511673e-b4ad-333d-abc0-feedc269539e | 2026-08-21T10:48:33Z | 2026-08-21T10:48:43Z | `{"operationId":"32543fdc-1b6e-3cf9-8092-3785bd9769a3","correlationId":"8e886389-b8db-3155-a808-2876cfcaaa85","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"cc838b29-dd0b-3f98-8bc8-bfbb03035666"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 4f969bde-b4d3-3215-a59d-b37aed0a6bcb | 2026-08-22T10:48:03Z | 2026-08-22T10:48:13Z | `{"originalEventId":"5511673e-b4ad-333d-abc0-feedc269539e","correlationId":"8e886389-b8db-3155-a808-2876cfcaaa85","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Transfert expédié (`TransferDispatched`) | d5a6494b-90ba-302b-8969-bb750e5fb94a | 2026-08-31T10:48:03Z | 2026-08-31T10:48:13Z | `{"operationId":"3fc54f5d-fdd9-3071-8a72-cb37d353c133","correlationId":"80dcfa83-1b5f-32d0-a761-cdfff4dd641b","siteId":"SUD","locationId":null,"transferId":"7064a7c2-4cb8-3324-851b-13e6c8a474a4","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | da31e347-3cf8-3bf7-9922-921e9c0e3c83 | 2026-08-31T10:49:03Z | 2026-08-31T10:49:13Z | `{"originalEventId":"d5a6494b-90ba-302b-8969-bb750e5fb94a","correlationId":"80dcfa83-1b5f-32d0-a761-cdfff4dd641b","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"SUD","locationId":null}` |
| Transfert reçu (`TransferReceived`) | 13ec7ed5-6aec-3617-bbdf-d02abe909f17 | 2026-09-01T10:48:03Z | 2026-09-01T10:48:13Z | `{"operationId":"a3cffe04-f8a4-3af6-8dcc-6ed21bfff544","correlationId":"fbbacc5f-fdf3-3cb5-b838-9c417696464e","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","transferId":"7064a7c2-4cb8-3324-851b-13e6c8a474a4","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 613c2675-e0d3-3dfb-9d01-35617a6b6dc3 | 2026-09-02T10:48:03Z | 2026-09-02T10:48:13Z | `{"originalEventId":"13ec7ed5-6aec-3617-bbdf-d02abe909f17","correlationId":"fbbacc5f-fdf3-3cb5-b838-9c417696464e","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Réparation engagée (`RepairStarted`) | 94734c9c-a3ce-39f6-941b-a4dc1a7e4613 | 2026-09-27T08:34:47Z | 2026-09-27T08:34:57Z | `{"operationId":"768b7084-f417-33a0-bcfd-5f97537dc30d","correlationId":"fb2ff50a-6e36-3f76-b133-c2d9459746d3","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","repairId":"4592cafd-1882-370f-b597-3f57f68cb6f3"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 472340c4-af8f-3699-bab7-5799a783537b | 2026-09-27T08:35:17Z | 2026-09-27T08:35:27Z | `{"originalEventId":"94734c9c-a3ce-39f6-941b-a4dc1a7e4613","correlationId":"fb2ff50a-6e36-3f76-b133-c2d9459746d3","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"e1e96311-46f3-33f2-9ffe-14f6d0404cd3","copyId":"73e2a754-6a5c-35ea-a36e-0e9bd85ed181","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","scopeId":"08896ffd-fe25-3d58-800a-49293c383852","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"73e2a754-6a5c-35ea-a36e-0e9bd85ed181","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"73e2a754-6a5c-35ea-a36e-0e9bd85ed181","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"73e2a754-6a5c-35ea-a36e-0e9bd85ed181","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"73e2a754-6a5c-35ea-a36e-0e9bd85ed181","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"73e2a754-6a5c-35ea-a36e-0e9bd85ed181","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"73e2a754-6a5c-35ea-a36e-0e9bd85ed181","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"73e2a754-6a5c-35ea-a36e-0e9bd85ed181","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"73e2a754-6a5c-35ea-a36e-0e9bd85ed181"},{"kind":"sourceEvent","id":"d963b96d-9846-3860-a06f-7fad0f5e5586"},{"kind":"sourceEvent","id":"0259c1d7-5c52-380d-b10a-6f853ddf1f2b"},{"kind":"sourceEvent","id":"5d1a861d-5ea4-3208-b340-d9ce2fde1134"},{"kind":"sourceEvent","id":"e59317cf-9455-3b08-9ce9-29b513d2eb2d"},{"kind":"sourceEvent","id":"7a02f7e9-0065-35e8-8165-8efc499b4e29"},{"kind":"sourceEvent","id":"5511673e-b4ad-333d-abc0-feedc269539e"},{"kind":"sourceEvent","id":"4f969bde-b4d3-3215-a59d-b37aed0a6bcb"},{"kind":"sourceEvent","id":"d5a6494b-90ba-302b-8969-bb750e5fb94a"},{"kind":"sourceEvent","id":"da31e347-3cf8-3bf7-9922-921e9c0e3c83"},{"kind":"sourceEvent","id":"13ec7ed5-6aec-3617-bbdf-d02abe909f17"},{"kind":"sourceEvent","id":"613c2675-e0d3-3dfb-9d01-35617a6b6dc3"},{"kind":"sourceEvent","id":"94734c9c-a3ce-39f6-941b-a4dc1a7e4613"},{"kind":"sourceEvent","id":"472340c4-af8f-3699-bab7-5799a783537b"},{"kind":"observation","id":"e1e96311-46f3-33f2-9ffe-14f6d0404cd3"},{"kind":"inventoryScope","id":"08896ffd-fe25-3d58-800a-49293c383852"},{"kind":"sourceAvailability","id":"73e2a754-6a5c-35ea-a36e-0e9bd85ed181/LOANS"},{"kind":"sourceAvailability","id":"73e2a754-6a5c-35ea-a36e-0e9bd85ed181/RETURNS"},{"kind":"sourceAvailability","id":"73e2a754-6a5c-35ea-a36e-0e9bd85ed181/RECEIPTS"},{"kind":"sourceAvailability","id":"73e2a754-6a5c-35ea-a36e-0e9bd85ed181/TRANSFERS"},{"kind":"sourceAvailability","id":"73e2a754-6a5c-35ea-a36e-0e9bd85ed181/REPAIRS"},{"kind":"sourceAvailability","id":"73e2a754-6a5c-35ea-a36e-0e9bd85ed181/INTEGRATIONS"},{"kind":"sourceAvailability","id":"73e2a754-6a5c-35ea-a36e-0e9bd85ed181/INVENTORY"},{"kind":"inventoryScope","id":"7d46c7a4-f137-377d-8437-5d11eeb4049d"}]`

- Session : `{"scopeId":"08896ffd-fe25-3d58-800a-49293c383852","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"7d46c7a4-f137-377d-8437-5d11eeb4049d","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 5762f7a9-dbf9-3644-ab70-ad54760e428f — dossier d'écart

- Exemplaire : `4960c994-d289-35ef-ac33-5231ff3042aa`
- Famille privée : INVENTAIRE_INCOMPLET ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"6f52af17-e24e-378d-aa8b-50808af9213a","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["COLLECTION_INTERRUPTED"],"mechanism":"Interruption de collecte ; exemplaire resté au rayon.","physicalStatus":"ON_SHELF","physicalSiteId":"CENTRE","physicalLocationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","evidenceMask":"Collecte indisponible"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"L'absence de lecture ne démontre ni la destination, ni une perte, ni un vol ; données insuffisantes pour attribuer une cause.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État du stock informatique : `{"copyId":"4960c994-d289-35ef-ac33-5231ff3042aa","status":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","sourceEventIds":["02303541-4cea-34d4-8f16-8cac7e8bae1f","6169ac9c-0708-3610-9e8f-1bce053ea678"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 9f464ae5-0cc8-3398-bb71-ac8b15606f72 | 2026-06-01T14:09:39Z | 2026-06-01T14:09:49Z | `{"operationId":"3a766427-054e-3c30-bd23-81d357c3b19b","correlationId":"976ae92e-652c-33b1-a1c4-60e870145323","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"299a84a0-424c-3fb6-96d6-e7de9be6032a"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | c3033e9a-7db6-33d7-be12-e708b3b9ada7 | 2026-06-02T14:09:39Z | 2026-06-02T14:09:49Z | `{"originalEventId":"9f464ae5-0cc8-3398-bb71-ac8b15606f72","correlationId":"976ae92e-652c-33b1-a1c4-60e870145323","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 0fe9a7e9-6baa-3dab-8fea-0cc587356f07 | 2026-07-31T14:09:39Z | 2026-07-31T14:09:49Z | `{"operationId":"65b687bb-4fb4-35a1-93f2-1a5103a7cc80","correlationId":"680797b6-923b-3bde-aeae-670dacd0cdb5","siteId":"CENTRE","locationId":null,"loanId":"b17070a5-c8d1-3e6f-aad0-6924eb1a28fe"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | a4c4b0d7-5e6c-3b7a-a7ef-a59716e0fb57 | 2026-07-31T14:10:39Z | 2026-07-31T14:10:49Z | `{"originalEventId":"0fe9a7e9-6baa-3dab-8fea-0cc587356f07","correlationId":"680797b6-923b-3bde-aeae-670dacd0cdb5","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 22624e04-f8b2-35aa-8f3f-edc7bf2dc995 | 2026-08-20T14:09:39Z | 2026-08-20T14:09:49Z | `{"operationId":"6250a12b-0a2d-359e-80cf-45fe92646a66","correlationId":"07fc59ab-ff41-3979-bc69-c0019066eb53","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"b17070a5-c8d1-3e6f-aad0-6924eb1a28fe"}` |
| Retour reçu (`ReturnReceived`) | 02303541-4cea-34d4-8f16-8cac7e8bae1f | 2026-08-20T14:10:09Z | 2026-08-20T14:10:19Z | `{"operationId":"74def4c5-39fe-3177-9096-71a43e6243b2","correlationId":"eb3200ca-c36d-3963-a7ec-3ee9fd3e836b","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"b17070a5-c8d1-3e6f-aad0-6924eb1a28fe"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 6169ac9c-0708-3610-9e8f-1bce053ea678 | 2026-08-21T14:09:39Z | 2026-08-21T14:09:49Z | `{"originalEventId":"02303541-4cea-34d4-8f16-8cac7e8bae1f","correlationId":"eb3200ca-c36d-3963-a7ec-3ee9fd3e836b","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"4960c994-d289-35ef-ac33-5231ff3042aa","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4960c994-d289-35ef-ac33-5231ff3042aa","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4960c994-d289-35ef-ac33-5231ff3042aa","source":"INVENTORY","status":"UNAVAILABLE","completeFrom":null,"completeTo":null}`
- `{"copyId":"4960c994-d289-35ef-ac33-5231ff3042aa","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4960c994-d289-35ef-ac33-5231ff3042aa","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4960c994-d289-35ef-ac33-5231ff3042aa","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4960c994-d289-35ef-ac33-5231ff3042aa","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**
- `{"noteId":"6ae06f2d-f30f-3531-a24f-600004edef78","copyId":"4960c994-d289-35ef-ac33-5231ff3042aa","text":"Le terminal de collecte a cessé de répondre pendant le passage ; aucune conclusion sur les exemplaires non lus.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"4960c994-d289-35ef-ac33-5231ff3042aa"},{"kind":"sourceEvent","id":"9f464ae5-0cc8-3398-bb71-ac8b15606f72"},{"kind":"sourceEvent","id":"c3033e9a-7db6-33d7-be12-e708b3b9ada7"},{"kind":"sourceEvent","id":"0fe9a7e9-6baa-3dab-8fea-0cc587356f07"},{"kind":"sourceEvent","id":"a4c4b0d7-5e6c-3b7a-a7ef-a59716e0fb57"},{"kind":"sourceEvent","id":"22624e04-f8b2-35aa-8f3f-edc7bf2dc995"},{"kind":"sourceEvent","id":"02303541-4cea-34d4-8f16-8cac7e8bae1f"},{"kind":"sourceEvent","id":"6169ac9c-0708-3610-9e8f-1bce053ea678"},{"kind":"sourceAvailability","id":"4960c994-d289-35ef-ac33-5231ff3042aa/LOANS"},{"kind":"sourceAvailability","id":"4960c994-d289-35ef-ac33-5231ff3042aa/RETURNS"},{"kind":"sourceAvailability","id":"4960c994-d289-35ef-ac33-5231ff3042aa/RECEIPTS"},{"kind":"sourceAvailability","id":"4960c994-d289-35ef-ac33-5231ff3042aa/TRANSFERS"},{"kind":"sourceAvailability","id":"4960c994-d289-35ef-ac33-5231ff3042aa/REPAIRS"},{"kind":"sourceAvailability","id":"4960c994-d289-35ef-ac33-5231ff3042aa/INTEGRATIONS"},{"kind":"sourceAvailability","id":"4960c994-d289-35ef-ac33-5231ff3042aa/INVENTORY"},{"kind":"operatorNote","id":"6ae06f2d-f30f-3531-a24f-600004edef78"},{"kind":"inventoryScope","id":"7d46c7a4-f137-377d-8437-5d11eeb4049d"},{"kind":"inventoryScope","id":"66954879-6228-3c31-bf72-7b217d3c9695"},{"kind":"inventoryScope","id":"2a6f7727-71d3-3a12-96f6-4b5e2b38ce02"},{"kind":"inventoryScope","id":"2a6f7727-71d3-3a12-96f6-4b5e2b38ce02"}]`

- Session : `{"scopeId":"7d46c7a4-f137-377d-8437-5d11eeb4049d","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"66954879-6228-3c31-bf72-7b217d3c9695","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2a6f7727-71d3-3a12-96f6-4b5e2b38ce02","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"2a6f7727-71d3-3a12-96f6-4b5e2b38ce02","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 59a49d33-8a0f-3543-b6cf-96930f0db0bd — dossier d'écart

- Exemplaire : `b0bbb5c8-0c80-382f-bc77-aff174b426a9`
- Famille privée : ABSENCE_INEXPLIQUEE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"e8bcb90f-d0e2-3914-878a-c6402b046101","kind":"MISSING","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["UNOBSERVED_MOVE"],"mechanism":"Exemplaire déplacé hors du périmètre instrumenté ; la destination n'est pas exposée et aucune perte n'est simulée.","physicalStatus":"ON_SHELF","physicalSiteId":"SUD","physicalLocationId":null,"evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"L'absence de lecture ne démontre ni la destination, ni une perte, ni un vol ; données insuffisantes pour attribuer une cause.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État du stock informatique : `{"copyId":"b0bbb5c8-0c80-382f-bc77-aff174b426a9","status":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","sourceEventIds":["58c9ecbb-8733-30a0-ab5e-6908cb30d3d9","01f5e12a-6006-33c7-8f04-9376875a47bc"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 97a60591-23b2-3263-8bf1-aa38bbc61e10 | 2026-06-01T19:35:48Z | 2026-06-01T19:35:58Z | `{"operationId":"831e2c81-b0a0-3b51-8b2e-0f2916476ebe","correlationId":"018f7378-1c11-3c37-a4bb-23b45e993ef6","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"ccc2c819-3e3e-380f-b945-bdbfdbecf88a"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | e196e5fb-02bf-33ad-adcd-3eb5b45158e9 | 2026-06-02T19:35:48Z | 2026-06-02T19:35:58Z | `{"originalEventId":"97a60591-23b2-3263-8bf1-aa38bbc61e10","correlationId":"018f7378-1c11-3c37-a4bb-23b45e993ef6","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 7c34c7c8-1d85-3fa9-a9d4-fc688d379ffe | 2026-07-31T19:35:48Z | 2026-07-31T19:35:58Z | `{"operationId":"212c7e34-b1a1-36e0-9ef5-8ba82ce55d46","correlationId":"7ce02663-3662-3cf3-8565-12de99317b2d","siteId":"SUD","locationId":null,"loanId":"4b34e5c3-5c49-3b0e-a216-e28aaa986741"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | f57b8caf-428b-31e3-bb35-9472885c53f6 | 2026-07-31T19:36:48Z | 2026-07-31T19:36:58Z | `{"originalEventId":"7c34c7c8-1d85-3fa9-a9d4-fc688d379ffe","correlationId":"7ce02663-3662-3cf3-8565-12de99317b2d","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 1b131aec-a4be-3914-a942-2cb7f1d623ae | 2026-08-20T19:35:48Z | 2026-08-20T19:35:58Z | `{"operationId":"bbdef84e-77d7-3722-8ded-41768e6e4e15","correlationId":"b1bd45dd-c5d9-31d5-81d8-6388b4b7e965","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"4b34e5c3-5c49-3b0e-a216-e28aaa986741"}` |
| Retour reçu (`ReturnReceived`) | 58c9ecbb-8733-30a0-ab5e-6908cb30d3d9 | 2026-08-20T19:36:18Z | 2026-08-20T19:36:28Z | `{"operationId":"155ebb37-cccf-3026-8406-7088ad9977da","correlationId":"306202cb-aa13-3be4-a2e8-1b55f86ae0e6","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"4b34e5c3-5c49-3b0e-a216-e28aaa986741"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 01f5e12a-6006-33c7-8f04-9376875a47bc | 2026-08-21T19:35:48Z | 2026-08-21T19:35:58Z | `{"originalEventId":"58c9ecbb-8733-30a0-ab5e-6908cb30d3d9","correlationId":"306202cb-aa13-3be4-a2e8-1b55f86ae0e6","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"b0bbb5c8-0c80-382f-bc77-aff174b426a9","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b0bbb5c8-0c80-382f-bc77-aff174b426a9","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b0bbb5c8-0c80-382f-bc77-aff174b426a9","source":"LOANS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"b0bbb5c8-0c80-382f-bc77-aff174b426a9","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b0bbb5c8-0c80-382f-bc77-aff174b426a9","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b0bbb5c8-0c80-382f-bc77-aff174b426a9","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b0bbb5c8-0c80-382f-bc77-aff174b426a9","source":"TRANSFERS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**
- `{"noteId":"3a211184-6f1e-3472-a228-1290b6fe3adc","copyId":"b0bbb5c8-0c80-382f-bc77-aff174b426a9","text":"Le passage au rayon n'a pas permis de retrouver cet exemplaire ; contrôle complémentaire demandé.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"b0bbb5c8-0c80-382f-bc77-aff174b426a9"},{"kind":"sourceEvent","id":"97a60591-23b2-3263-8bf1-aa38bbc61e10"},{"kind":"sourceEvent","id":"e196e5fb-02bf-33ad-adcd-3eb5b45158e9"},{"kind":"sourceEvent","id":"7c34c7c8-1d85-3fa9-a9d4-fc688d379ffe"},{"kind":"sourceEvent","id":"f57b8caf-428b-31e3-bb35-9472885c53f6"},{"kind":"sourceEvent","id":"1b131aec-a4be-3914-a942-2cb7f1d623ae"},{"kind":"sourceEvent","id":"58c9ecbb-8733-30a0-ab5e-6908cb30d3d9"},{"kind":"sourceEvent","id":"01f5e12a-6006-33c7-8f04-9376875a47bc"},{"kind":"sourceAvailability","id":"b0bbb5c8-0c80-382f-bc77-aff174b426a9/LOANS"},{"kind":"sourceAvailability","id":"b0bbb5c8-0c80-382f-bc77-aff174b426a9/RETURNS"},{"kind":"sourceAvailability","id":"b0bbb5c8-0c80-382f-bc77-aff174b426a9/RECEIPTS"},{"kind":"sourceAvailability","id":"b0bbb5c8-0c80-382f-bc77-aff174b426a9/TRANSFERS"},{"kind":"sourceAvailability","id":"b0bbb5c8-0c80-382f-bc77-aff174b426a9/REPAIRS"},{"kind":"sourceAvailability","id":"b0bbb5c8-0c80-382f-bc77-aff174b426a9/INTEGRATIONS"},{"kind":"sourceAvailability","id":"b0bbb5c8-0c80-382f-bc77-aff174b426a9/INVENTORY"},{"kind":"operatorNote","id":"3a211184-6f1e-3472-a228-1290b6fe3adc"},{"kind":"inventoryScope","id":"7f91fb45-70cc-3811-b222-43a5370aa433"},{"kind":"inventoryScope","id":"202855ec-9065-3be6-a0c2-07ce228ecf21"},{"kind":"inventoryScope","id":"1b4f280a-3145-3607-a009-dca44e9d4181"},{"kind":"inventoryScope","id":"2948b706-5519-3fcb-9c6a-82f0197080d2"},{"kind":"inventoryScope","id":"2977b6c8-c0f0-3d4f-aba5-6b5d16436fb8"},{"kind":"inventoryScope","id":"1399ad71-4f56-32d4-803c-c365bb8f27e7"},{"kind":"inventoryScope","id":"1399ad71-4f56-32d4-803c-c365bb8f27e7"}]`

- Session : `{"scopeId":"7f91fb45-70cc-3811-b222-43a5370aa433","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"202855ec-9065-3be6-a0c2-07ce228ecf21","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"1b4f280a-3145-3607-a009-dca44e9d4181","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2948b706-5519-3fcb-9c6a-82f0197080d2","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2977b6c8-c0f0-3d4f-aba5-6b5d16436fb8","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"1399ad71-4f56-32d4-803c-c365bb8f27e7","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"1399ad71-4f56-32d4-803c-c365bb8f27e7","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 5aae8a71-4afb-3eaa-bf92-6ad57dfdf1b4 — dossier d'écart

- Exemplaire : `8e45081a-243b-3474-bddd-800408a097d4`
- Famille privée : RETOUR_NON_PROJETE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"b872c17c-c416-36e6-8a99-19705d0f7083","kind":"STATUS_MISMATCH","expectedValue":"ON_LOAN","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"5dd1007c-5999-306d-afa1-8101bca51975","copyId":"8e45081a-243b-3474-bddd-800408a097d4","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T11:25:32Z","recordedAt":"2026-09-27T11:25:42Z","payload":{"originalEventId":"0f25aefc-6687-3919-90dd-0c8ed2ed703a","correlationId":"0e4f7262-ca93-3c70-a6c8-8d28331bdb1e","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"0f25aefc-6687-3919-90dd-0c8ed2ed703a","physicalStatus":"RECEIVING","physicalSiteId":"SUD","physicalLocationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État du stock informatique : `{"copyId":"8e45081a-243b-3474-bddd-800408a097d4","status":"ON_LOAN","siteId":"SUD","locationId":null,"sourceEventIds":["bc318633-74b4-38be-b169-aae8c0c38743","6e8e911c-fce6-343b-af20-a7d40e64af97"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | d9f30cde-55c2-376f-b561-4e56395fc8b9 | 2026-06-02T07:46:05Z | 2026-06-02T07:46:15Z | `{"operationId":"73d8c51d-b146-3837-a9c6-9b54976a3b39","correlationId":"45aa5a91-2983-33b8-a318-ea49f6548276","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"170a26ed-4fa1-3dc9-8fab-948516fa1f3b"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 21745c37-fdbc-3cc0-8dc6-d035b128a7a0 | 2026-06-03T07:46:05Z | 2026-06-03T07:46:15Z | `{"originalEventId":"d9f30cde-55c2-376f-b561-4e56395fc8b9","correlationId":"45aa5a91-2983-33b8-a318-ea49f6548276","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | c6def61e-dc8b-3a92-b5b3-859f9dab4f7e | 2026-08-01T07:46:05Z | 2026-08-01T07:46:15Z | `{"operationId":"fdd399f0-61dd-3efd-bf41-b313288e4118","correlationId":"4dfbe6d3-486c-3cb0-8591-7d161ffa46e8","siteId":"SUD","locationId":null,"loanId":"0a00b5ba-2485-3ce1-80a9-2ee0b4d24e30"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 5f7fbce3-6b95-33f0-bc46-7f465a67bc03 | 2026-08-01T07:47:05Z | 2026-08-01T07:47:15Z | `{"originalEventId":"c6def61e-dc8b-3a92-b5b3-859f9dab4f7e","correlationId":"4dfbe6d3-486c-3cb0-8591-7d161ffa46e8","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 30e95e35-0476-3186-8384-53b964caf0a2 | 2026-08-21T07:46:05Z | 2026-08-21T07:46:15Z | `{"operationId":"a2b83197-dd1f-3573-8a40-4d8064ba1c4c","correlationId":"3ae84064-8ec7-3f13-92ef-6fa147e1089e","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"0a00b5ba-2485-3ce1-80a9-2ee0b4d24e30"}` |
| Retour reçu (`ReturnReceived`) | ff6f1cd7-b6e5-3fca-ac26-a4ed6ddd6e51 | 2026-08-21T07:46:35Z | 2026-08-21T07:46:45Z | `{"operationId":"4a6f823c-7b48-3103-b570-ff1f5f261f09","correlationId":"0da093de-da58-3227-99e0-3464a4298548","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"0a00b5ba-2485-3ce1-80a9-2ee0b4d24e30"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 66708705-15fb-3659-a451-0f600bceaf54 | 2026-08-22T07:46:05Z | 2026-08-22T07:46:15Z | `{"originalEventId":"ff6f1cd7-b6e5-3fca-ac26-a4ed6ddd6e51","correlationId":"0da093de-da58-3227-99e0-3464a4298548","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | bc318633-74b4-38be-b169-aae8c0c38743 | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"4f77a768-c436-36a8-b30d-da79a5762eb3","correlationId":"4c7aadb9-d34c-33b5-bfe3-9909555ce080","siteId":"SUD","locationId":null,"loanId":"86446e20-9afd-3617-a114-77752e60fb90"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 6e8e911c-fce6-343b-af20-a7d40e64af97 | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"bc318633-74b4-38be-b169-aae8c0c38743","correlationId":"4c7aadb9-d34c-33b5-bfe3-9909555ce080","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 10247ddf-373e-3ad4-81a6-b116ced71c67 | 2026-09-27T11:24:32Z | 2026-09-27T11:24:42Z | `{"operationId":"b99463ff-3484-3926-9644-aefee98dd303","correlationId":"46ee5f9c-4a56-33bc-ab6b-7ade14a658c8","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"86446e20-9afd-3617-a114-77752e60fb90"}` |
| Retour reçu (`ReturnReceived`) | 0f25aefc-6687-3919-90dd-0c8ed2ed703a | 2026-09-27T11:25:02Z | 2026-09-27T11:25:12Z | `{"operationId":"691c48f2-2695-383b-8491-b99d45787651","correlationId":"0e4f7262-ca93-3c70-a6c8-8d28331bdb1e","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"86446e20-9afd-3617-a114-77752e60fb90"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"1c20a50f-a605-30d9-bb6e-429b5988e5e0","copyId":"8e45081a-243b-3474-bddd-800408a097d4","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","scopeId":"cf2f8285-0f7e-3489-8b6b-d74530fe25a4","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"8e45081a-243b-3474-bddd-800408a097d4","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8e45081a-243b-3474-bddd-800408a097d4","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8e45081a-243b-3474-bddd-800408a097d4","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8e45081a-243b-3474-bddd-800408a097d4","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"8e45081a-243b-3474-bddd-800408a097d4","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8e45081a-243b-3474-bddd-800408a097d4","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8e45081a-243b-3474-bddd-800408a097d4","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"8e45081a-243b-3474-bddd-800408a097d4"},{"kind":"sourceEvent","id":"d9f30cde-55c2-376f-b561-4e56395fc8b9"},{"kind":"sourceEvent","id":"21745c37-fdbc-3cc0-8dc6-d035b128a7a0"},{"kind":"sourceEvent","id":"c6def61e-dc8b-3a92-b5b3-859f9dab4f7e"},{"kind":"sourceEvent","id":"5f7fbce3-6b95-33f0-bc46-7f465a67bc03"},{"kind":"sourceEvent","id":"30e95e35-0476-3186-8384-53b964caf0a2"},{"kind":"sourceEvent","id":"ff6f1cd7-b6e5-3fca-ac26-a4ed6ddd6e51"},{"kind":"sourceEvent","id":"66708705-15fb-3659-a451-0f600bceaf54"},{"kind":"sourceEvent","id":"bc318633-74b4-38be-b169-aae8c0c38743"},{"kind":"sourceEvent","id":"6e8e911c-fce6-343b-af20-a7d40e64af97"},{"kind":"sourceEvent","id":"10247ddf-373e-3ad4-81a6-b116ced71c67"},{"kind":"sourceEvent","id":"0f25aefc-6687-3919-90dd-0c8ed2ed703a"},{"kind":"observation","id":"1c20a50f-a605-30d9-bb6e-429b5988e5e0"},{"kind":"inventoryScope","id":"cf2f8285-0f7e-3489-8b6b-d74530fe25a4"},{"kind":"sourceAvailability","id":"8e45081a-243b-3474-bddd-800408a097d4/LOANS"},{"kind":"sourceAvailability","id":"8e45081a-243b-3474-bddd-800408a097d4/RETURNS"},{"kind":"sourceAvailability","id":"8e45081a-243b-3474-bddd-800408a097d4/RECEIPTS"},{"kind":"sourceAvailability","id":"8e45081a-243b-3474-bddd-800408a097d4/TRANSFERS"},{"kind":"sourceAvailability","id":"8e45081a-243b-3474-bddd-800408a097d4/REPAIRS"},{"kind":"sourceAvailability","id":"8e45081a-243b-3474-bddd-800408a097d4/INTEGRATIONS"},{"kind":"sourceAvailability","id":"8e45081a-243b-3474-bddd-800408a097d4/INVENTORY"}]`

- Session : `{"scopeId":"cf2f8285-0f7e-3489-8b6b-d74530fe25a4","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 5ca5dd81-b4fb-36b1-9e0a-bcb5b4c00c2f — dossier d'écart

- Exemplaire : `1e7a57fd-6edb-3725-a6ce-4323f9aba631`
- Famille privée : LOCALISATION ; variante : POSITIVE
- Symptômes : `[{"symptomId":"ce7c1d4a-a0ef-3d4e-9c14-f02fc5ef5755","kind":"LOCATION_MISMATCH","expectedValue":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","observedValue":"2cf9a5ef-0690-3b0f-9031-67da0dd9d884"}]`
- Vérité injectée (non accessible au diagnostic) : `{"mechanism":"Déplacement physique sans modification de l'affectation ; aucune attribution à un opérateur.","activePerturbations":["LOCATION_CHANGE_UNPROJECTED"],"physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":"2cf9a5ef-0690-3b0f-9031-67da0dd9d884"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["LOCATION_MISMATCH"],"explanation":"Observation unique valide à un autre emplacement du même site ; écart établi, origine humaine non prouvée.","forbiddenClaims":["Vol","Perte définitive","Responsabilité d'un opérateur"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"1e7a57fd-6edb-3725-a6ce-4323f9aba631","status":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","sourceEventIds":["c4124ab6-4f8e-3c59-85df-3af72306a734","f8105fb9-bf6f-341d-9c6e-1c47c2555fc9"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | c1831fdb-f936-3ef2-880c-7047924b5dac | 2026-06-02T01:33:31Z | 2026-06-02T01:33:41Z | `{"operationId":"21add743-e835-3fce-82fe-cb72b782a58b","correlationId":"66ae7ab7-8445-3e7c-bae9-fe7f1a115a52","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"c857164e-36e4-3e8b-be80-0189c3557e03"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | c97cd954-fa2c-39b9-912a-3cb0794245aa | 2026-06-03T01:33:31Z | 2026-06-03T01:33:41Z | `{"originalEventId":"c1831fdb-f936-3ef2-880c-7047924b5dac","correlationId":"66ae7ab7-8445-3e7c-bae9-fe7f1a115a52","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | 7175a81d-6250-36bb-8186-d456b2a9d01c | 2026-08-01T01:33:31Z | 2026-08-01T01:33:41Z | `{"operationId":"27f7bcff-712a-3f05-a3cd-2d941452a0bb","correlationId":"9c88f2d5-ce58-32f6-9c50-d54e8d645d5a","siteId":"NORD","locationId":null,"loanId":"06e54cd0-d5ac-3f59-a437-882ade5effe1"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | cc28d684-40d7-3bb8-8489-65e22dda708c | 2026-08-01T01:34:31Z | 2026-08-01T01:34:41Z | `{"originalEventId":"7175a81d-6250-36bb-8186-d456b2a9d01c","correlationId":"9c88f2d5-ce58-32f6-9c50-d54e8d645d5a","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 59964838-2b63-376f-92a9-a6e43aa7db68 | 2026-08-21T01:33:31Z | 2026-08-21T01:33:41Z | `{"operationId":"9089d759-d8c6-3702-8495-432c562cc709","correlationId":"64eac5d1-43c9-3495-8d88-51ff0fd50463","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"06e54cd0-d5ac-3f59-a437-882ade5effe1"}` |
| Retour reçu (`ReturnReceived`) | c4124ab6-4f8e-3c59-85df-3af72306a734 | 2026-08-21T01:34:01Z | 2026-08-21T01:34:11Z | `{"operationId":"d45b0d87-06cb-336f-94bc-90a6c75e71d8","correlationId":"384a4a3b-f454-3f95-8bb3-0297014b66d6","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"06e54cd0-d5ac-3f59-a437-882ade5effe1"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | f8105fb9-bf6f-341d-9c6e-1c47c2555fc9 | 2026-08-22T01:33:31Z | 2026-08-22T01:33:41Z | `{"originalEventId":"c4124ab6-4f8e-3c59-85df-3af72306a734","correlationId":"384a4a3b-f454-3f95-8bb3-0297014b66d6","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"3528163c-5306-3947-afe3-339c9d1472eb","copyId":"1e7a57fd-6edb-3725-a6ce-4323f9aba631","locationId":"2cf9a5ef-0690-3b0f-9031-67da0dd9d884","scopeId":"850933bc-9032-37ee-9fe3-fc0c0c0761d4","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"1e7a57fd-6edb-3725-a6ce-4323f9aba631","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1e7a57fd-6edb-3725-a6ce-4323f9aba631","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1e7a57fd-6edb-3725-a6ce-4323f9aba631","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1e7a57fd-6edb-3725-a6ce-4323f9aba631","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1e7a57fd-6edb-3725-a6ce-4323f9aba631","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1e7a57fd-6edb-3725-a6ce-4323f9aba631","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1e7a57fd-6edb-3725-a6ce-4323f9aba631","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"observation","id":"3528163c-5306-3947-afe3-339c9d1472eb"},{"kind":"systemState","id":"1e7a57fd-6edb-3725-a6ce-4323f9aba631"}]`

## Cas 5dabbc4f-7909-3772-b445-2001e920d5c6 — dossier d'écart

- Exemplaire : `661039e0-6a36-37a8-b7fd-c9faa7c83cc3`
- Famille privée : RETOUR_NON_PROJETE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"a847f999-15f6-3eee-a62c-e0db45ae3d4c","kind":"STATUS_MISMATCH","expectedValue":"ON_LOAN","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"c2957f9a-8c5f-3bfe-9acd-577014682842","physicalStatus":"RECEIVING","physicalSiteId":"SUD","physicalLocationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["RETURN_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"661039e0-6a36-37a8-b7fd-c9faa7c83cc3","status":"ON_LOAN","siteId":"SUD","locationId":null,"sourceEventIds":["803f15f8-9910-3f85-946a-bd31aa608af5","31e44f5a-4716-3b6a-9711-836f5d371918"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | d356d7d8-804e-357b-8815-d23920872eb3 | 2026-06-01T19:02:06Z | 2026-06-01T19:02:16Z | `{"operationId":"9873b273-00ae-3211-addf-dc40b07f0503","correlationId":"c3ffa335-8b85-3843-9500-dfa132cddb4d","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"78bb66bc-ba5f-384c-b00d-a5c04885fa5a"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | cbf6c780-84d1-3c4a-a6b6-01f8e66892cf | 2026-06-02T19:02:06Z | 2026-06-02T19:02:16Z | `{"originalEventId":"d356d7d8-804e-357b-8815-d23920872eb3","correlationId":"c3ffa335-8b85-3843-9500-dfa132cddb4d","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 065a76cd-58fc-3551-ba75-462e77eeafcd | 2026-07-31T19:02:06Z | 2026-07-31T19:02:16Z | `{"operationId":"8d1b65d1-88cf-3066-983c-cabfc5c50d8b","correlationId":"30504810-7aba-318b-b5f3-aca4c9c84ea2","siteId":"SUD","locationId":null,"loanId":"2d0d3afb-2f08-3997-9caf-3515f4195213"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 4685cbdb-89fc-320e-b694-b6593235bb80 | 2026-07-31T19:03:06Z | 2026-07-31T19:03:16Z | `{"originalEventId":"065a76cd-58fc-3551-ba75-462e77eeafcd","correlationId":"30504810-7aba-318b-b5f3-aca4c9c84ea2","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 46c9ba87-3b9a-3e3c-81f6-970e0492a7c0 | 2026-08-20T19:02:06Z | 2026-08-20T19:02:16Z | `{"operationId":"dee9daf3-ae43-3591-a072-6807e05cef6d","correlationId":"1cf83072-69d0-300a-b921-6a92d58bc311","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"2d0d3afb-2f08-3997-9caf-3515f4195213"}` |
| Retour reçu (`ReturnReceived`) | 8136293a-cf51-3989-8eb9-e5f9e6463c69 | 2026-08-20T19:02:36Z | 2026-08-20T19:02:46Z | `{"operationId":"b0c24427-a4df-332c-bf1e-08fb52b3181c","correlationId":"a44dc218-37e5-3f60-9110-fe6cf9fa9285","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"2d0d3afb-2f08-3997-9caf-3515f4195213"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 74609947-523a-370c-972f-a34e965e3c49 | 2026-08-21T19:02:06Z | 2026-08-21T19:02:16Z | `{"originalEventId":"8136293a-cf51-3989-8eb9-e5f9e6463c69","correlationId":"a44dc218-37e5-3f60-9110-fe6cf9fa9285","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 803f15f8-9910-3f85-946a-bd31aa608af5 | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"8aec8371-04ba-3853-9033-7eed49963ec3","correlationId":"22c4e74f-ee1f-323b-99c2-ce5af36c811d","siteId":"SUD","locationId":null,"loanId":"de2b8d9f-5af2-366b-a0ac-0802787a5976"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 31e44f5a-4716-3b6a-9711-836f5d371918 | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"803f15f8-9910-3f85-946a-bd31aa608af5","correlationId":"22c4e74f-ee1f-323b-99c2-ce5af36c811d","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 11bc64f4-5587-3c0b-b5d1-09cc50608212 | 2026-09-27T11:08:36Z | 2026-09-27T11:08:46Z | `{"operationId":"ee914589-3d5b-301c-b14c-937d532b9d6d","correlationId":"c5a960e3-9210-325c-bda2-bf2b75f5ee4a","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"de2b8d9f-5af2-366b-a0ac-0802787a5976"}` |
| Retour reçu (`ReturnReceived`) | c2957f9a-8c5f-3bfe-9acd-577014682842 | 2026-09-27T11:09:06Z | 2026-09-27T11:09:16Z | `{"operationId":"3b26d47e-d5f2-357d-897d-59d992f2fee0","correlationId":"334d4571-c78e-3ad6-b1aa-c32b8c6d4cff","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"de2b8d9f-5af2-366b-a0ac-0802787a5976"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 8d1050ae-c46c-356b-be41-437a6b9da612 | 2026-09-27T11:09:36Z | 2026-09-27T11:09:46Z | `{"originalEventId":"c2957f9a-8c5f-3bfe-9acd-577014682842","correlationId":"334d4571-c78e-3ad6-b1aa-c32b8c6d4cff","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"90bb2012-a74b-3a97-a448-eb3c65361412","copyId":"661039e0-6a36-37a8-b7fd-c9faa7c83cc3","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","scopeId":"93fcbc2e-9068-39f8-bd18-e051823b3400","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"661039e0-6a36-37a8-b7fd-c9faa7c83cc3","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"661039e0-6a36-37a8-b7fd-c9faa7c83cc3","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"661039e0-6a36-37a8-b7fd-c9faa7c83cc3","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"661039e0-6a36-37a8-b7fd-c9faa7c83cc3","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"661039e0-6a36-37a8-b7fd-c9faa7c83cc3","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"661039e0-6a36-37a8-b7fd-c9faa7c83cc3","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"661039e0-6a36-37a8-b7fd-c9faa7c83cc3","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"661039e0-6a36-37a8-b7fd-c9faa7c83cc3"},{"kind":"sourceEvent","id":"d356d7d8-804e-357b-8815-d23920872eb3"},{"kind":"sourceEvent","id":"cbf6c780-84d1-3c4a-a6b6-01f8e66892cf"},{"kind":"sourceEvent","id":"065a76cd-58fc-3551-ba75-462e77eeafcd"},{"kind":"sourceEvent","id":"4685cbdb-89fc-320e-b694-b6593235bb80"},{"kind":"sourceEvent","id":"46c9ba87-3b9a-3e3c-81f6-970e0492a7c0"},{"kind":"sourceEvent","id":"8136293a-cf51-3989-8eb9-e5f9e6463c69"},{"kind":"sourceEvent","id":"74609947-523a-370c-972f-a34e965e3c49"},{"kind":"sourceEvent","id":"803f15f8-9910-3f85-946a-bd31aa608af5"},{"kind":"sourceEvent","id":"31e44f5a-4716-3b6a-9711-836f5d371918"},{"kind":"sourceEvent","id":"11bc64f4-5587-3c0b-b5d1-09cc50608212"},{"kind":"sourceEvent","id":"c2957f9a-8c5f-3bfe-9acd-577014682842"},{"kind":"sourceEvent","id":"8d1050ae-c46c-356b-be41-437a6b9da612"},{"kind":"observation","id":"90bb2012-a74b-3a97-a448-eb3c65361412"},{"kind":"inventoryScope","id":"93fcbc2e-9068-39f8-bd18-e051823b3400"},{"kind":"sourceAvailability","id":"661039e0-6a36-37a8-b7fd-c9faa7c83cc3/LOANS"},{"kind":"sourceAvailability","id":"661039e0-6a36-37a8-b7fd-c9faa7c83cc3/RETURNS"},{"kind":"sourceAvailability","id":"661039e0-6a36-37a8-b7fd-c9faa7c83cc3/RECEIPTS"},{"kind":"sourceAvailability","id":"661039e0-6a36-37a8-b7fd-c9faa7c83cc3/TRANSFERS"},{"kind":"sourceAvailability","id":"661039e0-6a36-37a8-b7fd-c9faa7c83cc3/REPAIRS"},{"kind":"sourceAvailability","id":"661039e0-6a36-37a8-b7fd-c9faa7c83cc3/INTEGRATIONS"},{"kind":"sourceAvailability","id":"661039e0-6a36-37a8-b7fd-c9faa7c83cc3/INVENTORY"}]`

- Session : `{"scopeId":"93fcbc2e-9068-39f8-bd18-e051823b3400","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 63cf9f35-6b68-3c4c-ba7d-6abc2f3edfe3 — dossier d'écart

- Exemplaire : `604ffb3b-db2f-3a30-a02d-d5f25bab8fe1`
- Famille privée : LOCALISATION ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"1c49521e-4389-38a2-911d-c63ef786bd9d","kind":"COUNT_UNCERTAIN","expectedValue":"00f09742-c706-31b7-a4a0-c694a087403f","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"mechanism":"Déplacement physique sans modification de l'affectation ; aucune attribution à un opérateur.","activePerturbations":["LOCATION_CHANGE_UNPROJECTED"],"physicalStatus":"ON_SHELF","physicalSiteId":"SUD","physicalLocationId":"128fab03-f193-3647-a67e-b835a30e7583"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"Identité du scan ambiguë ; ne pas attribuer un emplacement physique certain.","forbiddenClaims":["Vol","Perte définitive","Responsabilité d'un opérateur"],"recommendedChecks":["Refaire le scan et vérifier le code-barres"]}`
- État du stock informatique : `{"copyId":"604ffb3b-db2f-3a30-a02d-d5f25bab8fe1","status":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","sourceEventIds":["ac846c7f-5117-35e8-9a3e-1f5d8d416c1f","4bdf39d3-850c-3250-aac3-b18b985fc31e"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 0b0affd0-70c7-35d8-b5d0-dad79e4c4b02 | 2026-06-01T21:14:52Z | 2026-06-01T21:15:02Z | `{"operationId":"04ffb362-8252-3292-bbbd-1acd7ccc1c9f","correlationId":"86c3f13b-b276-3835-b532-be17a6895daa","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"8c519bad-106d-3475-9094-efa2c4c8f5a9"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | f72005ed-66aa-3877-8a64-a83b4b6b07c2 | 2026-06-02T21:14:52Z | 2026-06-02T21:15:02Z | `{"originalEventId":"0b0affd0-70c7-35d8-b5d0-dad79e4c4b02","correlationId":"86c3f13b-b276-3835-b532-be17a6895daa","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 6f094ecb-8b44-321e-abe5-04fcf2f1216e | 2026-07-31T21:14:52Z | 2026-07-31T21:15:02Z | `{"operationId":"fa86c1f6-9b44-32e5-8443-0ccc736d55c3","correlationId":"020f1ced-4801-35dd-8616-9873c635f8bd","siteId":"SUD","locationId":null,"loanId":"b9f1fc13-5764-3e83-adb3-1408563abe6d"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 6f951929-83d2-32be-a1c7-08f7dfea583c | 2026-07-31T21:15:52Z | 2026-07-31T21:16:02Z | `{"originalEventId":"6f094ecb-8b44-321e-abe5-04fcf2f1216e","correlationId":"020f1ced-4801-35dd-8616-9873c635f8bd","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 6ea55d1a-bf14-32f2-939c-819bbbbbbe8f | 2026-08-20T21:14:52Z | 2026-08-20T21:15:02Z | `{"operationId":"a818c18b-f318-3570-9ad2-772a9b121eb8","correlationId":"2646e3f3-1bc7-3d01-8e66-bfcd5f2fd137","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"b9f1fc13-5764-3e83-adb3-1408563abe6d"}` |
| Retour reçu (`ReturnReceived`) | ac846c7f-5117-35e8-9a3e-1f5d8d416c1f | 2026-08-20T21:15:22Z | 2026-08-20T21:15:32Z | `{"operationId":"57267e1b-cadb-3de0-8915-2c579c28e338","correlationId":"612886fc-8224-38fb-8d19-8826b789e899","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"b9f1fc13-5764-3e83-adb3-1408563abe6d"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 4bdf39d3-850c-3250-aac3-b18b985fc31e | 2026-08-21T21:14:52Z | 2026-08-21T21:15:02Z | `{"originalEventId":"ac846c7f-5117-35e8-9a3e-1f5d8d416c1f","correlationId":"612886fc-8224-38fb-8d19-8826b789e899","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"58090c10-b1f6-3e26-b501-ee2fbaf62692","copyId":"604ffb3b-db2f-3a30-a02d-d5f25bab8fe1","locationId":"128fab03-f193-3647-a67e-b835a30e7583","scopeId":"ed48b7c1-dc47-3cf6-9304-d26f2d8c6810","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"604ffb3b-db2f-3a30-a02d-d5f25bab8fe1","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"604ffb3b-db2f-3a30-a02d-d5f25bab8fe1","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"604ffb3b-db2f-3a30-a02d-d5f25bab8fe1","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"604ffb3b-db2f-3a30-a02d-d5f25bab8fe1","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"604ffb3b-db2f-3a30-a02d-d5f25bab8fe1","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"604ffb3b-db2f-3a30-a02d-d5f25bab8fe1","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"604ffb3b-db2f-3a30-a02d-d5f25bab8fe1","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"observation","id":"58090c10-b1f6-3e26-b501-ee2fbaf62692"},{"kind":"systemState","id":"604ffb3b-db2f-3a30-a02d-d5f25bab8fe1"}]`

## Cas 649816ef-7abd-37d9-a760-343eaaa2a31b — dossier d'écart

- Exemplaire : `5f753f3a-e555-3301-acaf-9e1e851cd2b8`
- Famille privée : RECEPTION_NON_INTEGREE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"ce091072-253e-321c-a8c9-1c9c6965ae0d","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"83623a5f-c9c0-39f9-87df-3b7a98bfcf72","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"125bed65-7385-3595-a653-902580e9539f","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"5f753f3a-e555-3301-acaf-9e1e851cd2b8","status":"RECEIVING","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","sourceEventIds":["83623a5f-c9c0-39f9-87df-3b7a98bfcf72","b27ec8df-d17c-3871-91eb-3d09f618fab5"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 83623a5f-c9c0-39f9-87df-3b7a98bfcf72 | 2026-09-27T09:37:29Z | 2026-09-27T09:37:39Z | `{"operationId":"e0649801-d6c5-34b4-81e6-4b7b4e7423d4","correlationId":"c5f5c93a-d4d4-3b41-9e2c-7eceb2045899","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"c532ddb2-c22e-316a-8599-d8209c4266e6"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | cb50c78c-f29c-350b-9fce-a76e5af7e2ed | 2026-09-27T09:37:59Z | 2026-09-27T09:38:09Z | `{"originalEventId":"83623a5f-c9c0-39f9-87df-3b7a98bfcf72","correlationId":"c5f5c93a-d4d4-3b41-9e2c-7eceb2045899","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | b27ec8df-d17c-3871-91eb-3d09f618fab5 | 2026-09-27T09:38:59Z | 2026-09-27T09:39:09Z | `{"originalEventId":"83623a5f-c9c0-39f9-87df-3b7a98bfcf72","correlationId":"c5f5c93a-d4d4-3b41-9e2c-7eceb2045899","outcome":"APPLIED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"27bb3df3-7bae-3a13-b11f-060abf2efd01","copyId":"5f753f3a-e555-3301-acaf-9e1e851cd2b8","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","scopeId":"7e6650ee-4a06-3da3-b854-740d7670f5d0","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`
- `{"observationId":"441937d1-c949-39d5-90d0-108ed6513257","copyId":"5f753f3a-e555-3301-acaf-9e1e851cd2b8","locationId":"125bed65-7385-3595-a653-902580e9539f","scopeId":"c3f9df2c-4908-3948-ade0-75abb14c5765","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"5f753f3a-e555-3301-acaf-9e1e851cd2b8","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5f753f3a-e555-3301-acaf-9e1e851cd2b8","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5f753f3a-e555-3301-acaf-9e1e851cd2b8","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5f753f3a-e555-3301-acaf-9e1e851cd2b8","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5f753f3a-e555-3301-acaf-9e1e851cd2b8","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5f753f3a-e555-3301-acaf-9e1e851cd2b8","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5f753f3a-e555-3301-acaf-9e1e851cd2b8","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"5f753f3a-e555-3301-acaf-9e1e851cd2b8"},{"kind":"sourceEvent","id":"83623a5f-c9c0-39f9-87df-3b7a98bfcf72"},{"kind":"sourceEvent","id":"cb50c78c-f29c-350b-9fce-a76e5af7e2ed"},{"kind":"sourceEvent","id":"b27ec8df-d17c-3871-91eb-3d09f618fab5"},{"kind":"observation","id":"441937d1-c949-39d5-90d0-108ed6513257"},{"kind":"inventoryScope","id":"c3f9df2c-4908-3948-ade0-75abb14c5765"},{"kind":"observation","id":"27bb3df3-7bae-3a13-b11f-060abf2efd01"},{"kind":"inventoryScope","id":"7e6650ee-4a06-3da3-b854-740d7670f5d0"},{"kind":"sourceAvailability","id":"5f753f3a-e555-3301-acaf-9e1e851cd2b8/LOANS"},{"kind":"sourceAvailability","id":"5f753f3a-e555-3301-acaf-9e1e851cd2b8/RETURNS"},{"kind":"sourceAvailability","id":"5f753f3a-e555-3301-acaf-9e1e851cd2b8/RECEIPTS"},{"kind":"sourceAvailability","id":"5f753f3a-e555-3301-acaf-9e1e851cd2b8/TRANSFERS"},{"kind":"sourceAvailability","id":"5f753f3a-e555-3301-acaf-9e1e851cd2b8/REPAIRS"},{"kind":"sourceAvailability","id":"5f753f3a-e555-3301-acaf-9e1e851cd2b8/INTEGRATIONS"},{"kind":"sourceAvailability","id":"5f753f3a-e555-3301-acaf-9e1e851cd2b8/INVENTORY"},{"kind":"inventoryScope","id":"78b159f5-01d9-39e9-96e5-dddc8e0578c1"},{"kind":"inventoryScope","id":"aac4255b-e902-3e7e-b8f4-61a0508f0565"},{"kind":"inventoryScope","id":"a0f4b630-abea-33f6-9af9-eb48c395042e"},{"kind":"inventoryScope","id":"f2059a60-e250-3744-a6bc-f032a43a89a7"},{"kind":"inventoryScope","id":"398b2163-3ca7-39c8-b2ee-d941c5b71032"},{"kind":"inventoryScope","id":"dcc99ab8-60e8-3664-bd8b-2c8adf66d8d8"}]`

- Session : `{"scopeId":"c3f9df2c-4908-3948-ade0-75abb14c5765","locationId":"125bed65-7385-3595-a653-902580e9539f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"7e6650ee-4a06-3da3-b854-740d7670f5d0","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"78b159f5-01d9-39e9-96e5-dddc8e0578c1","locationId":"125bed65-7385-3595-a653-902580e9539f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"aac4255b-e902-3e7e-b8f4-61a0508f0565","locationId":"125bed65-7385-3595-a653-902580e9539f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"a0f4b630-abea-33f6-9af9-eb48c395042e","locationId":"125bed65-7385-3595-a653-902580e9539f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"f2059a60-e250-3744-a6bc-f032a43a89a7","locationId":"125bed65-7385-3595-a653-902580e9539f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"398b2163-3ca7-39c8-b2ee-d941c5b71032","locationId":"125bed65-7385-3595-a653-902580e9539f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"dcc99ab8-60e8-3664-bd8b-2c8adf66d8d8","locationId":"125bed65-7385-3595-a653-902580e9539f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 688ce4de-609f-3981-95f6-c08196e90d33 — dossier d'écart

- Exemplaire : `9a5ccd1d-a265-351c-9a94-c7e93b7f8f0a`
- Famille privée : ABSENCE_INEXPLIQUEE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"defd36e9-f057-398a-88c1-546e5443e9a3","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":"1"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"mechanism":"Exemplaire resté au rayon, lecture de contrôle réussie.","physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"Le scan valide au rayon attendu réfute l'absence courante.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"9a5ccd1d-a265-351c-9a94-c7e93b7f8f0a","status":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","sourceEventIds":["74fcd7b9-c41b-3796-8268-01fd31062985","aa269250-0951-30f1-9298-72a05cabd84d"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 37d8b0a8-2919-3d44-b1ac-761b638b7f82 | 2026-06-01T21:14:08Z | 2026-06-01T21:14:18Z | `{"operationId":"452af00d-ae6a-330c-a4ff-bee7affaa3f4","correlationId":"abd4b6ba-b68a-3b1c-bca5-2aa98a651675","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"bf3411b2-e375-36b2-aa7b-4970d6444c37"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 7beaa400-c747-309b-85cc-5f3ff7bf621b | 2026-06-02T21:14:08Z | 2026-06-02T21:14:18Z | `{"originalEventId":"37d8b0a8-2919-3d44-b1ac-761b638b7f82","correlationId":"abd4b6ba-b68a-3b1c-bca5-2aa98a651675","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 8a47fb38-aca2-3c9a-bca7-9d5fe535c58c | 2026-07-31T21:14:08Z | 2026-07-31T21:14:18Z | `{"operationId":"16db0bc6-131d-333d-aeca-db6efbf898c3","correlationId":"7c72acc0-33db-3a20-97c8-83bafe2cbd62","siteId":"CENTRE","locationId":null,"loanId":"5ec93b34-887f-3c24-a354-27dd373b95cd"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 9b5c79ab-af94-3f4a-8b66-ec793e9fbf4e | 2026-07-31T21:15:08Z | 2026-07-31T21:15:18Z | `{"originalEventId":"8a47fb38-aca2-3c9a-bca7-9d5fe535c58c","correlationId":"7c72acc0-33db-3a20-97c8-83bafe2cbd62","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | c42387ef-bfac-3dd5-8812-5712a363f50f | 2026-08-20T21:14:08Z | 2026-08-20T21:14:18Z | `{"operationId":"c32725fa-50b9-3277-8317-281ff8e3fc10","correlationId":"ed378080-8e95-3d7f-9e4e-bd4ff24eaf42","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"5ec93b34-887f-3c24-a354-27dd373b95cd"}` |
| Retour reçu (`ReturnReceived`) | f37ca69e-0e14-3d4f-b26c-7427be78e6fd | 2026-08-20T21:14:38Z | 2026-08-20T21:14:48Z | `{"operationId":"5ff229d8-99d8-338c-8772-9310faa6574c","correlationId":"2fbf4508-c6af-37b6-b5fd-2f31250854e8","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"5ec93b34-887f-3c24-a354-27dd373b95cd"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 38682c33-7ae3-3ca4-935c-21cdfe1ab1dc | 2026-08-21T21:14:08Z | 2026-08-21T21:14:18Z | `{"originalEventId":"f37ca69e-0e14-3d4f-b26c-7427be78e6fd","correlationId":"2fbf4508-c6af-37b6-b5fd-2f31250854e8","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Transfert expédié (`TransferDispatched`) | e6fdac15-8c74-3d49-82a5-86a446e33359 | 2026-08-30T21:14:08Z | 2026-08-30T21:14:18Z | `{"operationId":"c3870233-207d-3b60-88fc-83d77bb9e29f","correlationId":"74d46f14-48ad-3844-9e55-cec2845a7aaa","siteId":"CENTRE","locationId":null,"transferId":"a8324a2d-12bd-388e-85e5-498ffc8302d0","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 3da373f3-a565-3399-b51f-1946fe444bea | 2026-08-30T21:15:08Z | 2026-08-30T21:15:18Z | `{"originalEventId":"e6fdac15-8c74-3d49-82a5-86a446e33359","correlationId":"74d46f14-48ad-3844-9e55-cec2845a7aaa","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| Transfert reçu (`TransferReceived`) | 74fcd7b9-c41b-3796-8268-01fd31062985 | 2026-08-31T21:14:08Z | 2026-08-31T21:14:18Z | `{"operationId":"39993859-e670-3404-ad5d-c3e1078de328","correlationId":"4189dea9-3250-3a2c-9f91-7ca8b1d2c32f","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","transferId":"a8324a2d-12bd-388e-85e5-498ffc8302d0","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | aa269250-0951-30f1-9298-72a05cabd84d | 2026-09-01T21:14:08Z | 2026-09-01T21:14:18Z | `{"originalEventId":"74fcd7b9-c41b-3796-8268-01fd31062985","correlationId":"4189dea9-3250-3a2c-9f91-7ca8b1d2c32f","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"def55472-355f-39a5-a094-85f59f6028b8","copyId":"9a5ccd1d-a265-351c-9a94-c7e93b7f8f0a","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","scopeId":"9de706b4-d360-3282-b440-c440861c039c","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"9a5ccd1d-a265-351c-9a94-c7e93b7f8f0a","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9a5ccd1d-a265-351c-9a94-c7e93b7f8f0a","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9a5ccd1d-a265-351c-9a94-c7e93b7f8f0a","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9a5ccd1d-a265-351c-9a94-c7e93b7f8f0a","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9a5ccd1d-a265-351c-9a94-c7e93b7f8f0a","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9a5ccd1d-a265-351c-9a94-c7e93b7f8f0a","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9a5ccd1d-a265-351c-9a94-c7e93b7f8f0a","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**
- `{"noteId":"5e5beed9-777f-36fa-97fd-8a83fe5d38f4","copyId":"9a5ccd1d-a265-351c-9a94-c7e93b7f8f0a","text":"Nouvelle lecture du code-barres effectuée dans le rayon affecté.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"9a5ccd1d-a265-351c-9a94-c7e93b7f8f0a"},{"kind":"sourceEvent","id":"37d8b0a8-2919-3d44-b1ac-761b638b7f82"},{"kind":"sourceEvent","id":"7beaa400-c747-309b-85cc-5f3ff7bf621b"},{"kind":"sourceEvent","id":"8a47fb38-aca2-3c9a-bca7-9d5fe535c58c"},{"kind":"sourceEvent","id":"9b5c79ab-af94-3f4a-8b66-ec793e9fbf4e"},{"kind":"sourceEvent","id":"c42387ef-bfac-3dd5-8812-5712a363f50f"},{"kind":"sourceEvent","id":"f37ca69e-0e14-3d4f-b26c-7427be78e6fd"},{"kind":"sourceEvent","id":"38682c33-7ae3-3ca4-935c-21cdfe1ab1dc"},{"kind":"sourceEvent","id":"e6fdac15-8c74-3d49-82a5-86a446e33359"},{"kind":"sourceEvent","id":"3da373f3-a565-3399-b51f-1946fe444bea"},{"kind":"sourceEvent","id":"74fcd7b9-c41b-3796-8268-01fd31062985"},{"kind":"sourceEvent","id":"aa269250-0951-30f1-9298-72a05cabd84d"},{"kind":"observation","id":"def55472-355f-39a5-a094-85f59f6028b8"},{"kind":"inventoryScope","id":"9de706b4-d360-3282-b440-c440861c039c"},{"kind":"sourceAvailability","id":"9a5ccd1d-a265-351c-9a94-c7e93b7f8f0a/LOANS"},{"kind":"sourceAvailability","id":"9a5ccd1d-a265-351c-9a94-c7e93b7f8f0a/RETURNS"},{"kind":"sourceAvailability","id":"9a5ccd1d-a265-351c-9a94-c7e93b7f8f0a/RECEIPTS"},{"kind":"sourceAvailability","id":"9a5ccd1d-a265-351c-9a94-c7e93b7f8f0a/TRANSFERS"},{"kind":"sourceAvailability","id":"9a5ccd1d-a265-351c-9a94-c7e93b7f8f0a/REPAIRS"},{"kind":"sourceAvailability","id":"9a5ccd1d-a265-351c-9a94-c7e93b7f8f0a/INTEGRATIONS"},{"kind":"sourceAvailability","id":"9a5ccd1d-a265-351c-9a94-c7e93b7f8f0a/INVENTORY"},{"kind":"operatorNote","id":"5e5beed9-777f-36fa-97fd-8a83fe5d38f4"},{"kind":"inventoryScope","id":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5"},{"kind":"inventoryScope","id":"eb1ce5f1-7397-3b45-9385-ef328e09fec8"},{"kind":"inventoryScope","id":"d0c55bda-319d-3537-857a-5d135f796cab"},{"kind":"inventoryScope","id":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb"},{"kind":"inventoryScope","id":"d9842c03-ba37-324b-b71b-e58ac7147d4c"},{"kind":"inventoryScope","id":"07bae854-b205-39bf-a23e-9eaa22063b92"},{"kind":"inventoryScope","id":"9de706b4-d360-3282-b440-c440861c039c"}]`

- Session : `{"scopeId":"9de706b4-d360-3282-b440-c440861c039c","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"eb1ce5f1-7397-3b45-9385-ef328e09fec8","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"d0c55bda-319d-3537-857a-5d135f796cab","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"d9842c03-ba37-324b-b71b-e58ac7147d4c","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"07bae854-b205-39bf-a23e-9eaa22063b92","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"9de706b4-d360-3282-b440-c440861c039c","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 6dfde7fd-2839-32ec-9037-fe938c55a877 — dossier d'écart

- Exemplaire : `d3af7bfa-b1c4-3f94-845e-50120dbdb78f`
- Famille privée : RETOUR_NON_PROJETE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"e98a5945-a833-332c-a16c-8872197f5fab","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"ae6d4ec4-c70d-345c-82f6-8c136f96ad92","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"d3af7bfa-b1c4-3f94-845e-50120dbdb78f","status":"RECEIVING","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","sourceEventIds":["ae6d4ec4-c70d-345c-82f6-8c136f96ad92","3d9e742a-12a6-3ec1-9add-9d7c551fc5d6"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | debc1128-909e-3b5e-bbf0-d819125b7c61 | 2026-06-02T09:35:13Z | 2026-06-02T09:35:23Z | `{"operationId":"00d80c7e-e17c-3b27-8786-465ce41a46d2","correlationId":"9cd07c28-2765-36f3-9a9a-ee39619a07d0","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"7bdc3b47-ba06-336e-b46d-c7fb55c0e530"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 4fadb4c4-06e9-3e7d-b67d-69f50782c43c | 2026-06-03T09:35:13Z | 2026-06-03T09:35:23Z | `{"originalEventId":"debc1128-909e-3b5e-bbf0-d819125b7c61","correlationId":"9cd07c28-2765-36f3-9a9a-ee39619a07d0","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | 7551c672-a831-3873-bb5d-988ae448e5bb | 2026-08-01T09:35:13Z | 2026-08-01T09:35:23Z | `{"operationId":"dbc5490c-3e30-3451-9305-63d07856ec4a","correlationId":"465f5334-535a-33f5-8509-7b8b8bc6b7b2","siteId":"NORD","locationId":null,"loanId":"183bc76a-099e-3c1c-a904-38db14b4f394"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 22acc8f8-540e-3617-a43c-e434dd1e4f90 | 2026-08-01T09:36:13Z | 2026-08-01T09:36:23Z | `{"originalEventId":"7551c672-a831-3873-bb5d-988ae448e5bb","correlationId":"465f5334-535a-33f5-8509-7b8b8bc6b7b2","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | e3a42990-8f66-3529-8034-b7da240a7f23 | 2026-08-21T09:35:13Z | 2026-08-21T09:35:23Z | `{"operationId":"be4f018d-6e43-3ada-84fe-924d4ae925e9","correlationId":"e3d36d73-e580-3727-b986-d75be7b377ac","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"183bc76a-099e-3c1c-a904-38db14b4f394"}` |
| Retour reçu (`ReturnReceived`) | f76d0aad-92a5-359a-b444-4f7c4829544c | 2026-08-21T09:35:43Z | 2026-08-21T09:35:53Z | `{"operationId":"78508582-9123-3490-a7b1-a4a7ff9c3b4a","correlationId":"b5608fed-6395-3534-aa81-82ff11cd22f5","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"183bc76a-099e-3c1c-a904-38db14b4f394"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 66e48bff-b488-341b-8a57-92099f355d61 | 2026-08-22T09:35:13Z | 2026-08-22T09:35:23Z | `{"originalEventId":"f76d0aad-92a5-359a-b444-4f7c4829544c","correlationId":"b5608fed-6395-3534-aa81-82ff11cd22f5","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | 0809f12c-cc7d-3917-9898-de68d50f557d | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"6997f0a9-263e-3b1a-a224-6fe7a17e05f0","correlationId":"114fccfe-c610-39be-b847-937f4315226c","siteId":"NORD","locationId":null,"loanId":"2aeaea8c-0f9d-35b7-a15a-05d563533104"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 3d8f23fd-228f-3fe9-bbf9-31050c28a967 | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"0809f12c-cc7d-3917-9898-de68d50f557d","correlationId":"114fccfe-c610-39be-b847-937f4315226c","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | db9b871c-f488-3164-8e6b-2789da245c66 | 2026-09-27T07:49:53Z | 2026-09-27T07:50:03Z | `{"operationId":"b66b0e43-5879-3878-a3a1-7518fbbc98bc","correlationId":"a32c8af3-d853-3148-ba44-d0b517809bc6","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"2aeaea8c-0f9d-35b7-a15a-05d563533104"}` |
| Retour reçu (`ReturnReceived`) | ae6d4ec4-c70d-345c-82f6-8c136f96ad92 | 2026-09-27T07:50:23Z | 2026-09-27T07:50:33Z | `{"operationId":"204449ef-fdca-3fc0-91ca-f6e291c57a07","correlationId":"c095b839-ef9d-3bde-8216-2aa60f18b432","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"2aeaea8c-0f9d-35b7-a15a-05d563533104"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | d5e8c60f-05a4-329e-a92e-1e8f66f00b41 | 2026-09-27T07:50:53Z | 2026-09-27T07:51:03Z | `{"originalEventId":"ae6d4ec4-c70d-345c-82f6-8c136f96ad92","correlationId":"c095b839-ef9d-3bde-8216-2aa60f18b432","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 3d9e742a-12a6-3ec1-9add-9d7c551fc5d6 | 2026-09-27T07:51:53Z | 2026-09-27T07:52:03Z | `{"originalEventId":"ae6d4ec4-c70d-345c-82f6-8c136f96ad92","correlationId":"c095b839-ef9d-3bde-8216-2aa60f18b432","outcome":"APPLIED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"db421f40-5ca7-3585-8fa0-ca787d478304","copyId":"d3af7bfa-b1c4-3f94-845e-50120dbdb78f","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","scopeId":"476e34e8-4402-3aad-bd08-6baed3607760","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`
- `{"observationId":"eff894f7-2a4c-3318-a5ab-0f746a69fac6","copyId":"d3af7bfa-b1c4-3f94-845e-50120dbdb78f","locationId":"2cf9a5ef-0690-3b0f-9031-67da0dd9d884","scopeId":"c3634d0f-2531-327a-b15e-a1298924ebb4","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"d3af7bfa-b1c4-3f94-845e-50120dbdb78f","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"d3af7bfa-b1c4-3f94-845e-50120dbdb78f","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"d3af7bfa-b1c4-3f94-845e-50120dbdb78f","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"d3af7bfa-b1c4-3f94-845e-50120dbdb78f","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"d3af7bfa-b1c4-3f94-845e-50120dbdb78f","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"d3af7bfa-b1c4-3f94-845e-50120dbdb78f","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"d3af7bfa-b1c4-3f94-845e-50120dbdb78f","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"d3af7bfa-b1c4-3f94-845e-50120dbdb78f"},{"kind":"sourceEvent","id":"debc1128-909e-3b5e-bbf0-d819125b7c61"},{"kind":"sourceEvent","id":"4fadb4c4-06e9-3e7d-b67d-69f50782c43c"},{"kind":"sourceEvent","id":"7551c672-a831-3873-bb5d-988ae448e5bb"},{"kind":"sourceEvent","id":"22acc8f8-540e-3617-a43c-e434dd1e4f90"},{"kind":"sourceEvent","id":"e3a42990-8f66-3529-8034-b7da240a7f23"},{"kind":"sourceEvent","id":"f76d0aad-92a5-359a-b444-4f7c4829544c"},{"kind":"sourceEvent","id":"66e48bff-b488-341b-8a57-92099f355d61"},{"kind":"sourceEvent","id":"0809f12c-cc7d-3917-9898-de68d50f557d"},{"kind":"sourceEvent","id":"3d8f23fd-228f-3fe9-bbf9-31050c28a967"},{"kind":"sourceEvent","id":"db9b871c-f488-3164-8e6b-2789da245c66"},{"kind":"sourceEvent","id":"ae6d4ec4-c70d-345c-82f6-8c136f96ad92"},{"kind":"sourceEvent","id":"d5e8c60f-05a4-329e-a92e-1e8f66f00b41"},{"kind":"sourceEvent","id":"3d9e742a-12a6-3ec1-9add-9d7c551fc5d6"},{"kind":"observation","id":"db421f40-5ca7-3585-8fa0-ca787d478304"},{"kind":"inventoryScope","id":"476e34e8-4402-3aad-bd08-6baed3607760"},{"kind":"observation","id":"eff894f7-2a4c-3318-a5ab-0f746a69fac6"},{"kind":"inventoryScope","id":"c3634d0f-2531-327a-b15e-a1298924ebb4"},{"kind":"sourceAvailability","id":"d3af7bfa-b1c4-3f94-845e-50120dbdb78f/LOANS"},{"kind":"sourceAvailability","id":"d3af7bfa-b1c4-3f94-845e-50120dbdb78f/RETURNS"},{"kind":"sourceAvailability","id":"d3af7bfa-b1c4-3f94-845e-50120dbdb78f/RECEIPTS"},{"kind":"sourceAvailability","id":"d3af7bfa-b1c4-3f94-845e-50120dbdb78f/TRANSFERS"},{"kind":"sourceAvailability","id":"d3af7bfa-b1c4-3f94-845e-50120dbdb78f/REPAIRS"},{"kind":"sourceAvailability","id":"d3af7bfa-b1c4-3f94-845e-50120dbdb78f/INTEGRATIONS"},{"kind":"sourceAvailability","id":"d3af7bfa-b1c4-3f94-845e-50120dbdb78f/INVENTORY"},{"kind":"inventoryScope","id":"104e98b3-403c-3df7-a25e-35c32e84ad59"},{"kind":"inventoryScope","id":"e7075335-e771-3f5d-be5a-bea0d97b821f"},{"kind":"inventoryScope","id":"041ea6ea-fb06-36bc-9dab-c0440e48bcc6"}]`

- Session : `{"scopeId":"476e34e8-4402-3aad-bd08-6baed3607760","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"c3634d0f-2531-327a-b15e-a1298924ebb4","locationId":"2cf9a5ef-0690-3b0f-9031-67da0dd9d884","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"104e98b3-403c-3df7-a25e-35c32e84ad59","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"e7075335-e771-3f5d-be5a-bea0d97b821f","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"041ea6ea-fb06-36bc-9dab-c0440e48bcc6","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 6fbbca66-010a-3ca7-9197-7b64d8790e00 — dossier d'écart

- Exemplaire : `fc085da4-8a47-3452-8973-bccf5fd99844`
- Famille privée : INVENTAIRE_INCOMPLET ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"cda730cd-eb3a-3120-bf2b-d796b6b4f524","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":"1"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"mechanism":"Exemplaire resté au rayon, lecture de contrôle réussie.","physicalStatus":"ON_SHELF","physicalSiteId":"CENTRE","physicalLocationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"Le scan valide au rayon attendu réfute l'absence courante.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"fc085da4-8a47-3452-8973-bccf5fd99844","status":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","sourceEventIds":["b0adfdc1-a981-3c56-9b30-f857aad751c7","b4dca678-6cb1-32f9-8423-c5cd23e1a44e"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 3ddb9bab-337d-3266-babb-5656c7282b26 | 2026-06-02T07:05:50Z | 2026-06-02T07:06:00Z | `{"operationId":"e6a6135c-27d1-313b-b149-458ac5d72214","correlationId":"92b6329d-d9df-3fc6-ac02-1e5c3648cc12","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"8a8a5c04-3e58-351d-a2fd-6d4f4495cb69"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | a5ba1c6e-7903-331d-bd97-f75a42d84adb | 2026-06-03T07:05:50Z | 2026-06-03T07:06:00Z | `{"originalEventId":"3ddb9bab-337d-3266-babb-5656c7282b26","correlationId":"92b6329d-d9df-3fc6-ac02-1e5c3648cc12","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | b5aa8660-eb45-36c8-b5ff-ef8766a91b4a | 2026-08-01T07:05:50Z | 2026-08-01T07:06:00Z | `{"operationId":"d79e38de-8cbe-3af9-9f54-9debfd5a2351","correlationId":"82ac0f5f-78f8-3af1-b316-71f4ebe54a12","siteId":"CENTRE","locationId":null,"loanId":"d8bb3540-1e15-3a63-b361-b1040ab24742"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 32b86bab-60fb-3398-9431-4617f8646df0 | 2026-08-01T07:06:50Z | 2026-08-01T07:07:00Z | `{"originalEventId":"b5aa8660-eb45-36c8-b5ff-ef8766a91b4a","correlationId":"82ac0f5f-78f8-3af1-b316-71f4ebe54a12","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 08a215b0-8fe5-36a9-8fca-9bafcf0cac12 | 2026-08-21T07:05:50Z | 2026-08-21T07:06:00Z | `{"operationId":"d7fd9a5b-c8db-3e95-b1da-d2b916f2cfe4","correlationId":"514501e4-ecc2-3035-8a73-473b3b989a57","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"d8bb3540-1e15-3a63-b361-b1040ab24742"}` |
| Retour reçu (`ReturnReceived`) | b0adfdc1-a981-3c56-9b30-f857aad751c7 | 2026-08-21T07:06:20Z | 2026-08-21T07:06:30Z | `{"operationId":"52194f2c-d31d-3920-8588-b5a42a96cfe7","correlationId":"39c5a1b6-b3c1-3472-a1d2-c25018c844c1","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"d8bb3540-1e15-3a63-b361-b1040ab24742"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | b4dca678-6cb1-32f9-8423-c5cd23e1a44e | 2026-08-22T07:05:50Z | 2026-08-22T07:06:00Z | `{"originalEventId":"b0adfdc1-a981-3c56-9b30-f857aad751c7","correlationId":"39c5a1b6-b3c1-3472-a1d2-c25018c844c1","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"fad4042a-66cc-3e19-a192-8cd5b409e02c","copyId":"fc085da4-8a47-3452-8973-bccf5fd99844","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","scopeId":"c0755c3b-6689-3d3d-8527-6c90e6c6e5d5","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"fc085da4-8a47-3452-8973-bccf5fd99844","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"fc085da4-8a47-3452-8973-bccf5fd99844","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"fc085da4-8a47-3452-8973-bccf5fd99844","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"fc085da4-8a47-3452-8973-bccf5fd99844","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"fc085da4-8a47-3452-8973-bccf5fd99844","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"fc085da4-8a47-3452-8973-bccf5fd99844","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"fc085da4-8a47-3452-8973-bccf5fd99844","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**
- `{"noteId":"75764ea9-b870-3cdc-8d51-f0c09c2a84bd","copyId":"fc085da4-8a47-3452-8973-bccf5fd99844","text":"Nouvelle lecture du code-barres effectuée dans le rayon affecté.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"fc085da4-8a47-3452-8973-bccf5fd99844"},{"kind":"sourceEvent","id":"3ddb9bab-337d-3266-babb-5656c7282b26"},{"kind":"sourceEvent","id":"a5ba1c6e-7903-331d-bd97-f75a42d84adb"},{"kind":"sourceEvent","id":"b5aa8660-eb45-36c8-b5ff-ef8766a91b4a"},{"kind":"sourceEvent","id":"32b86bab-60fb-3398-9431-4617f8646df0"},{"kind":"sourceEvent","id":"08a215b0-8fe5-36a9-8fca-9bafcf0cac12"},{"kind":"sourceEvent","id":"b0adfdc1-a981-3c56-9b30-f857aad751c7"},{"kind":"sourceEvent","id":"b4dca678-6cb1-32f9-8423-c5cd23e1a44e"},{"kind":"observation","id":"fad4042a-66cc-3e19-a192-8cd5b409e02c"},{"kind":"inventoryScope","id":"c0755c3b-6689-3d3d-8527-6c90e6c6e5d5"},{"kind":"sourceAvailability","id":"fc085da4-8a47-3452-8973-bccf5fd99844/LOANS"},{"kind":"sourceAvailability","id":"fc085da4-8a47-3452-8973-bccf5fd99844/RETURNS"},{"kind":"sourceAvailability","id":"fc085da4-8a47-3452-8973-bccf5fd99844/RECEIPTS"},{"kind":"sourceAvailability","id":"fc085da4-8a47-3452-8973-bccf5fd99844/TRANSFERS"},{"kind":"sourceAvailability","id":"fc085da4-8a47-3452-8973-bccf5fd99844/REPAIRS"},{"kind":"sourceAvailability","id":"fc085da4-8a47-3452-8973-bccf5fd99844/INTEGRATIONS"},{"kind":"sourceAvailability","id":"fc085da4-8a47-3452-8973-bccf5fd99844/INVENTORY"},{"kind":"operatorNote","id":"75764ea9-b870-3cdc-8d51-f0c09c2a84bd"},{"kind":"inventoryScope","id":"7d46c7a4-f137-377d-8437-5d11eeb4049d"},{"kind":"inventoryScope","id":"66954879-6228-3c31-bf72-7b217d3c9695"},{"kind":"inventoryScope","id":"2a6f7727-71d3-3a12-96f6-4b5e2b38ce02"},{"kind":"inventoryScope","id":"c0755c3b-6689-3d3d-8527-6c90e6c6e5d5"}]`

- Session : `{"scopeId":"c0755c3b-6689-3d3d-8527-6c90e6c6e5d5","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"7d46c7a4-f137-377d-8437-5d11eeb4049d","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"66954879-6228-3c31-bf72-7b217d3c9695","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2a6f7727-71d3-3a12-96f6-4b5e2b38ce02","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"c0755c3b-6689-3d3d-8527-6c90e6c6e5d5","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 75f19148-9c9e-315e-b96b-cdc13858af32 — dossier d'écart

- Exemplaire : `db67f82f-b1f8-3652-bcf9-7c709490edef`
- Famille privée : INVENTAIRE_INCOMPLET ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"20b1e4e3-7f6e-3761-9098-a58ea51cbae1","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["COLLECTION_INTERRUPTED"],"mechanism":"Interruption de collecte ; exemplaire resté au rayon.","physicalStatus":"ON_SHELF","physicalSiteId":"SUD","physicalLocationId":"00f09742-c706-31b7-a4a0-c694a087403f","evidenceMask":"Collecte indisponible"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"L'absence de lecture ne démontre ni la destination, ni une perte, ni un vol ; données insuffisantes pour attribuer une cause.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État du stock informatique : `{"copyId":"db67f82f-b1f8-3652-bcf9-7c709490edef","status":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","sourceEventIds":["3722e321-3668-3d0c-b8d1-f9fbeadcd7ae","679546fe-1da7-3266-a83c-86a4d05ea651"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | ef4bb3df-6313-31e7-bcaf-a558f7bd2360 | 2026-06-02T00:48:53Z | 2026-06-02T00:49:03Z | `{"operationId":"1e4cea10-d5e3-3797-904c-923505227b3f","correlationId":"1166d547-2f87-3bbb-a3c6-23141a68d119","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"2f82ca29-8209-3614-a717-62b00e3408d9"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | e721717f-5353-3218-84d2-23bce80d0358 | 2026-06-03T00:48:53Z | 2026-06-03T00:49:03Z | `{"originalEventId":"ef4bb3df-6313-31e7-bcaf-a558f7bd2360","correlationId":"1166d547-2f87-3bbb-a3c6-23141a68d119","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 5ce6b29d-7bce-3b13-b9c0-a189db9de0ab | 2026-08-01T00:48:53Z | 2026-08-01T00:49:03Z | `{"operationId":"213ef3ea-35c2-3619-9544-f31fd10657bb","correlationId":"7a133303-925d-351e-bda8-05d342b90c5a","siteId":"SUD","locationId":null,"loanId":"a910cf73-eebd-360c-b61d-995d185f1535"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 45b60b2b-a3bd-3633-89a1-3bda8ebb7458 | 2026-08-01T00:49:53Z | 2026-08-01T00:50:03Z | `{"originalEventId":"5ce6b29d-7bce-3b13-b9c0-a189db9de0ab","correlationId":"7a133303-925d-351e-bda8-05d342b90c5a","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 62cee038-ca18-34f3-b781-f3a152b48595 | 2026-08-21T00:48:53Z | 2026-08-21T00:49:03Z | `{"operationId":"f7711284-091c-31a2-92b2-3250da54df60","correlationId":"943b5e4c-47fa-3d6e-b1e9-35783531aa3e","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"a910cf73-eebd-360c-b61d-995d185f1535"}` |
| Retour reçu (`ReturnReceived`) | 3722e321-3668-3d0c-b8d1-f9fbeadcd7ae | 2026-08-21T00:49:23Z | 2026-08-21T00:49:33Z | `{"operationId":"5a4cbb5f-3972-3d9f-b0df-438ac06777fd","correlationId":"3d2c50c1-018b-39b6-8a4f-a64c68ee94dd","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"a910cf73-eebd-360c-b61d-995d185f1535"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 679546fe-1da7-3266-a83c-86a4d05ea651 | 2026-08-22T00:48:53Z | 2026-08-22T00:49:03Z | `{"originalEventId":"3722e321-3668-3d0c-b8d1-f9fbeadcd7ae","correlationId":"3d2c50c1-018b-39b6-8a4f-a64c68ee94dd","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"db67f82f-b1f8-3652-bcf9-7c709490edef","source":"INVENTORY","status":"UNAVAILABLE","completeFrom":null,"completeTo":null}`
- `{"copyId":"db67f82f-b1f8-3652-bcf9-7c709490edef","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"db67f82f-b1f8-3652-bcf9-7c709490edef","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"db67f82f-b1f8-3652-bcf9-7c709490edef","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"db67f82f-b1f8-3652-bcf9-7c709490edef","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"db67f82f-b1f8-3652-bcf9-7c709490edef","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"db67f82f-b1f8-3652-bcf9-7c709490edef","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**
- `{"noteId":"e9b6cc6c-a750-3f7e-8dbb-3f0836fc768a","copyId":"db67f82f-b1f8-3652-bcf9-7c709490edef","text":"Le terminal de collecte a cessé de répondre pendant le passage ; aucune conclusion sur les exemplaires non lus.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"db67f82f-b1f8-3652-bcf9-7c709490edef"},{"kind":"sourceEvent","id":"ef4bb3df-6313-31e7-bcaf-a558f7bd2360"},{"kind":"sourceEvent","id":"e721717f-5353-3218-84d2-23bce80d0358"},{"kind":"sourceEvent","id":"5ce6b29d-7bce-3b13-b9c0-a189db9de0ab"},{"kind":"sourceEvent","id":"45b60b2b-a3bd-3633-89a1-3bda8ebb7458"},{"kind":"sourceEvent","id":"62cee038-ca18-34f3-b781-f3a152b48595"},{"kind":"sourceEvent","id":"3722e321-3668-3d0c-b8d1-f9fbeadcd7ae"},{"kind":"sourceEvent","id":"679546fe-1da7-3266-a83c-86a4d05ea651"},{"kind":"sourceAvailability","id":"db67f82f-b1f8-3652-bcf9-7c709490edef/LOANS"},{"kind":"sourceAvailability","id":"db67f82f-b1f8-3652-bcf9-7c709490edef/RETURNS"},{"kind":"sourceAvailability","id":"db67f82f-b1f8-3652-bcf9-7c709490edef/RECEIPTS"},{"kind":"sourceAvailability","id":"db67f82f-b1f8-3652-bcf9-7c709490edef/TRANSFERS"},{"kind":"sourceAvailability","id":"db67f82f-b1f8-3652-bcf9-7c709490edef/REPAIRS"},{"kind":"sourceAvailability","id":"db67f82f-b1f8-3652-bcf9-7c709490edef/INTEGRATIONS"},{"kind":"sourceAvailability","id":"db67f82f-b1f8-3652-bcf9-7c709490edef/INVENTORY"},{"kind":"operatorNote","id":"e9b6cc6c-a750-3f7e-8dbb-3f0836fc768a"},{"kind":"inventoryScope","id":"7f91fb45-70cc-3811-b222-43a5370aa433"},{"kind":"inventoryScope","id":"202855ec-9065-3be6-a0c2-07ce228ecf21"},{"kind":"inventoryScope","id":"1b4f280a-3145-3607-a009-dca44e9d4181"},{"kind":"inventoryScope","id":"2948b706-5519-3fcb-9c6a-82f0197080d2"},{"kind":"inventoryScope","id":"2977b6c8-c0f0-3d4f-aba5-6b5d16436fb8"},{"kind":"inventoryScope","id":"2977b6c8-c0f0-3d4f-aba5-6b5d16436fb8"}]`

- Session : `{"scopeId":"7f91fb45-70cc-3811-b222-43a5370aa433","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"202855ec-9065-3be6-a0c2-07ce228ecf21","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"1b4f280a-3145-3607-a009-dca44e9d4181","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2948b706-5519-3fcb-9c6a-82f0197080d2","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2977b6c8-c0f0-3d4f-aba5-6b5d16436fb8","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"2977b6c8-c0f0-3d4f-aba5-6b5d16436fb8","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 7959c2ab-b27e-3462-b703-2093a0260536 — dossier d'écart

- Exemplaire : `a7aed92d-b416-3941-ba61-c95dddeb2f17`
- Famille privée : INVENTAIRE_INCOMPLET ; variante : POSITIVE
- Symptômes : `[{"symptomId":"ae745f83-8784-3494-90b1-af7c865aaa30","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["COLLECTION_INTERRUPTED"],"mechanism":"Interruption de collecte ; exemplaire resté au rayon.","physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["INVENTORY_INCOMPLETE"],"explanation":"La session interrompue explique l'incertitude de comptage ; la présence physique reste à vérifier.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État du stock informatique : `{"copyId":"a7aed92d-b416-3941-ba61-c95dddeb2f17","status":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","sourceEventIds":["3330ef6e-9bfe-3e54-a732-8b9926cd7598","5e3920c7-598f-3249-be5b-4e6d2a8c9470"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 3a529f97-4f02-3c4f-8ffa-2002612463ec | 2026-06-02T06:11:38Z | 2026-06-02T06:11:48Z | `{"operationId":"4c5efb75-10ff-399d-b759-4a43edcd44e0","correlationId":"b36c3860-e223-3ecd-80ef-efd9d8524b2e","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"2ba9bad5-d1c2-3986-af2c-c10215451851"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 4cfecd36-1593-3443-8781-6f5b46fdcb9b | 2026-06-03T06:11:38Z | 2026-06-03T06:11:48Z | `{"originalEventId":"3a529f97-4f02-3c4f-8ffa-2002612463ec","correlationId":"b36c3860-e223-3ecd-80ef-efd9d8524b2e","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | 42512a49-5e6b-3f2a-8e64-30f81150e228 | 2026-08-01T06:11:38Z | 2026-08-01T06:11:48Z | `{"operationId":"7234c7c3-6a3e-3a0d-ad08-48fff07248b7","correlationId":"740fc762-13a4-36d6-be37-9b2293e3fb73","siteId":"NORD","locationId":null,"loanId":"319b836a-c819-302d-90b1-e6ac4cb730cc"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | f639cdbb-fdea-3ca0-bb4e-93b380b62acc | 2026-08-01T06:12:38Z | 2026-08-01T06:12:48Z | `{"originalEventId":"42512a49-5e6b-3f2a-8e64-30f81150e228","correlationId":"740fc762-13a4-36d6-be37-9b2293e3fb73","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 3772b9dc-317c-3dc6-8f18-14d55c333a6e | 2026-08-21T06:11:38Z | 2026-08-21T06:11:48Z | `{"operationId":"8d812782-5991-36b4-95b9-326c95f73e80","correlationId":"c76537ad-2e57-3ae8-8ed9-be0b0857bcd4","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"319b836a-c819-302d-90b1-e6ac4cb730cc"}` |
| Retour reçu (`ReturnReceived`) | 3330ef6e-9bfe-3e54-a732-8b9926cd7598 | 2026-08-21T06:12:08Z | 2026-08-21T06:12:18Z | `{"operationId":"26ab6caf-2f17-3902-a182-c9b0295fe5ef","correlationId":"e69930c8-b08c-36b3-84e7-9cc5f218ae21","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"319b836a-c819-302d-90b1-e6ac4cb730cc"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 5e3920c7-598f-3249-be5b-4e6d2a8c9470 | 2026-08-22T06:11:38Z | 2026-08-22T06:11:48Z | `{"originalEventId":"3330ef6e-9bfe-3e54-a732-8b9926cd7598","correlationId":"e69930c8-b08c-36b3-84e7-9cc5f218ae21","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"a7aed92d-b416-3941-ba61-c95dddeb2f17","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a7aed92d-b416-3941-ba61-c95dddeb2f17","source":"INVENTORY","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"a7aed92d-b416-3941-ba61-c95dddeb2f17","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a7aed92d-b416-3941-ba61-c95dddeb2f17","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a7aed92d-b416-3941-ba61-c95dddeb2f17","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a7aed92d-b416-3941-ba61-c95dddeb2f17","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a7aed92d-b416-3941-ba61-c95dddeb2f17","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**
- `{"noteId":"48875b65-4364-3442-b10d-070bb41fbbdf","copyId":"a7aed92d-b416-3941-ba61-c95dddeb2f17","text":"Le terminal de collecte a cessé de répondre pendant le passage ; aucune conclusion sur les exemplaires non lus.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"a7aed92d-b416-3941-ba61-c95dddeb2f17"},{"kind":"sourceEvent","id":"3a529f97-4f02-3c4f-8ffa-2002612463ec"},{"kind":"sourceEvent","id":"4cfecd36-1593-3443-8781-6f5b46fdcb9b"},{"kind":"sourceEvent","id":"42512a49-5e6b-3f2a-8e64-30f81150e228"},{"kind":"sourceEvent","id":"f639cdbb-fdea-3ca0-bb4e-93b380b62acc"},{"kind":"sourceEvent","id":"3772b9dc-317c-3dc6-8f18-14d55c333a6e"},{"kind":"sourceEvent","id":"3330ef6e-9bfe-3e54-a732-8b9926cd7598"},{"kind":"sourceEvent","id":"5e3920c7-598f-3249-be5b-4e6d2a8c9470"},{"kind":"sourceAvailability","id":"a7aed92d-b416-3941-ba61-c95dddeb2f17/LOANS"},{"kind":"sourceAvailability","id":"a7aed92d-b416-3941-ba61-c95dddeb2f17/RETURNS"},{"kind":"sourceAvailability","id":"a7aed92d-b416-3941-ba61-c95dddeb2f17/RECEIPTS"},{"kind":"sourceAvailability","id":"a7aed92d-b416-3941-ba61-c95dddeb2f17/TRANSFERS"},{"kind":"sourceAvailability","id":"a7aed92d-b416-3941-ba61-c95dddeb2f17/REPAIRS"},{"kind":"sourceAvailability","id":"a7aed92d-b416-3941-ba61-c95dddeb2f17/INTEGRATIONS"},{"kind":"sourceAvailability","id":"a7aed92d-b416-3941-ba61-c95dddeb2f17/INVENTORY"},{"kind":"operatorNote","id":"48875b65-4364-3442-b10d-070bb41fbbdf"},{"kind":"inventoryScope","id":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5"},{"kind":"inventoryScope","id":"eb1ce5f1-7397-3b45-9385-ef328e09fec8"},{"kind":"inventoryScope","id":"d0c55bda-319d-3537-857a-5d135f796cab"},{"kind":"inventoryScope","id":"d0c55bda-319d-3537-857a-5d135f796cab"}]`

- Session : `{"scopeId":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"eb1ce5f1-7397-3b45-9385-ef328e09fec8","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"d0c55bda-319d-3537-857a-5d135f796cab","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"d0c55bda-319d-3537-857a-5d135f796cab","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 7ab0b25b-9249-3446-bd3a-fae144a0c46e — dossier d'écart

- Exemplaire : `6833a3c9-f82a-3ad9-a272-68030dd570f2`
- Famille privée : REPARATION_NON_PROJETEE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"6a16a110-c888-3f95-85be-8b19bf8b97cc","kind":"STATUS_MISMATCH","expectedValue":"ON_SHELF","observedValue":"IN_REPAIR"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"7eda652b-3722-3acc-9b32-fb1622c9f066","physicalStatus":"IN_REPAIR","physicalSiteId":"NORD","physicalLocationId":"19124ccb-fb37-348d-81ff-686cdabbabb0","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"6833a3c9-f82a-3ad9-a272-68030dd570f2","status":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","sourceEventIds":["c23b44c3-279c-321f-9114-b6dcdfc6807b","86aac078-480d-3464-baa2-abe182c9d4bf"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 82ab302e-62e9-33ce-8f0d-d10a79a9eaf6 | 2026-06-02T07:45:11Z | 2026-06-02T07:45:21Z | `{"operationId":"2444bb25-3d73-3c38-a768-a80f7efe5536","correlationId":"41872b4c-de79-3498-8910-c6ec18beaa62","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"157d4c75-4aa4-3f2c-94ff-f5be93138f5a"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 2a01834c-f152-33db-b16a-fb3f666d2835 | 2026-06-03T07:45:11Z | 2026-06-03T07:45:21Z | `{"originalEventId":"82ab302e-62e9-33ce-8f0d-d10a79a9eaf6","correlationId":"41872b4c-de79-3498-8910-c6ec18beaa62","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 8c5f14fd-6115-3c51-b094-cab44f3de5d9 | 2026-08-01T07:45:11Z | 2026-08-01T07:45:21Z | `{"operationId":"3af33349-658f-3b9b-b948-21796a2b740e","correlationId":"1417cfc8-85d5-3d7d-a5ed-40d45f0d505a","siteId":"CENTRE","locationId":null,"loanId":"f6e87e95-9ed9-39c2-9622-a83958107034"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 3cb2504a-a26a-348d-90f7-d990afcb8c83 | 2026-08-01T07:46:11Z | 2026-08-01T07:46:21Z | `{"originalEventId":"8c5f14fd-6115-3c51-b094-cab44f3de5d9","correlationId":"1417cfc8-85d5-3d7d-a5ed-40d45f0d505a","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 0e631e72-bbd5-3f70-ba28-c42cffe75e50 | 2026-08-21T07:45:11Z | 2026-08-21T07:45:21Z | `{"operationId":"6694df9d-f3fd-3a57-b0e8-05e1e235c132","correlationId":"95117c0d-712f-3388-b3ff-c2870b2d9d8a","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"f6e87e95-9ed9-39c2-9622-a83958107034"}` |
| Retour reçu (`ReturnReceived`) | 3c17ef89-490b-311e-9964-99ebee1b26df | 2026-08-21T07:45:41Z | 2026-08-21T07:45:51Z | `{"operationId":"f32b6526-77c8-315c-873b-66a4de3d209d","correlationId":"26b7db44-de0e-3f1a-ac9d-3040f2dcb576","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"f6e87e95-9ed9-39c2-9622-a83958107034"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 8714094f-02d1-395e-9a41-e65550849601 | 2026-08-22T07:45:11Z | 2026-08-22T07:45:21Z | `{"originalEventId":"3c17ef89-490b-311e-9964-99ebee1b26df","correlationId":"26b7db44-de0e-3f1a-ac9d-3040f2dcb576","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Transfert expédié (`TransferDispatched`) | 6ab966d2-2b8e-364b-8f64-0080e19b447f | 2026-08-31T07:45:11Z | 2026-08-31T07:45:21Z | `{"operationId":"4c579169-d7ce-375a-bbf1-76c69b2beb57","correlationId":"34d379ec-9398-3c67-b4ac-e70732d93ff8","siteId":"CENTRE","locationId":null,"transferId":"5ead496c-66f0-3502-a374-135241f39de1","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 3137424b-5118-376f-97ef-6e30d3e43d8e | 2026-08-31T07:46:11Z | 2026-08-31T07:46:21Z | `{"originalEventId":"6ab966d2-2b8e-364b-8f64-0080e19b447f","correlationId":"34d379ec-9398-3c67-b4ac-e70732d93ff8","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| Transfert reçu (`TransferReceived`) | c23b44c3-279c-321f-9114-b6dcdfc6807b | 2026-09-01T07:45:11Z | 2026-09-01T07:45:21Z | `{"operationId":"d34fcda6-9c2f-3722-be2a-5209abf377fd","correlationId":"62c5644e-0d3a-3e95-87ac-13e5039683a0","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","transferId":"5ead496c-66f0-3502-a374-135241f39de1","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 86aac078-480d-3464-baa2-abe182c9d4bf | 2026-09-02T07:45:11Z | 2026-09-02T07:45:21Z | `{"originalEventId":"c23b44c3-279c-321f-9114-b6dcdfc6807b","correlationId":"62c5644e-0d3a-3e95-87ac-13e5039683a0","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Réparation engagée (`RepairStarted`) | 7eda652b-3722-3acc-9b32-fb1622c9f066 | 2026-09-27T11:39:34Z | 2026-09-27T11:39:44Z | `{"operationId":"b183a847-2666-3f43-99ea-453fb9dcb33a","correlationId":"0f8c9694-ef80-3c7e-a323-c2f1cdd66a49","siteId":"NORD","locationId":"19124ccb-fb37-348d-81ff-686cdabbabb0","repairId":"0044043d-dfd0-3e42-b97d-7407358811ae"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | d603d71e-4941-3da4-8b13-1646dfb6bf9e | 2026-09-27T11:40:04Z | 2026-09-27T11:40:14Z | `{"originalEventId":"7eda652b-3722-3acc-9b32-fb1622c9f066","correlationId":"0f8c9694-ef80-3c7e-a323-c2f1cdd66a49","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"NORD","locationId":"19124ccb-fb37-348d-81ff-686cdabbabb0","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"90db0f4b-03db-30c5-ad44-a9fd471d0b4d","copyId":"6833a3c9-f82a-3ad9-a272-68030dd570f2","locationId":"19124ccb-fb37-348d-81ff-686cdabbabb0","scopeId":"186f193b-e2f5-31f1-ba9e-fed4bb3bfa9f","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"6833a3c9-f82a-3ad9-a272-68030dd570f2","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6833a3c9-f82a-3ad9-a272-68030dd570f2","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6833a3c9-f82a-3ad9-a272-68030dd570f2","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6833a3c9-f82a-3ad9-a272-68030dd570f2","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6833a3c9-f82a-3ad9-a272-68030dd570f2","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6833a3c9-f82a-3ad9-a272-68030dd570f2","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6833a3c9-f82a-3ad9-a272-68030dd570f2","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"6833a3c9-f82a-3ad9-a272-68030dd570f2"},{"kind":"sourceEvent","id":"82ab302e-62e9-33ce-8f0d-d10a79a9eaf6"},{"kind":"sourceEvent","id":"2a01834c-f152-33db-b16a-fb3f666d2835"},{"kind":"sourceEvent","id":"8c5f14fd-6115-3c51-b094-cab44f3de5d9"},{"kind":"sourceEvent","id":"3cb2504a-a26a-348d-90f7-d990afcb8c83"},{"kind":"sourceEvent","id":"0e631e72-bbd5-3f70-ba28-c42cffe75e50"},{"kind":"sourceEvent","id":"3c17ef89-490b-311e-9964-99ebee1b26df"},{"kind":"sourceEvent","id":"8714094f-02d1-395e-9a41-e65550849601"},{"kind":"sourceEvent","id":"6ab966d2-2b8e-364b-8f64-0080e19b447f"},{"kind":"sourceEvent","id":"3137424b-5118-376f-97ef-6e30d3e43d8e"},{"kind":"sourceEvent","id":"c23b44c3-279c-321f-9114-b6dcdfc6807b"},{"kind":"sourceEvent","id":"86aac078-480d-3464-baa2-abe182c9d4bf"},{"kind":"sourceEvent","id":"7eda652b-3722-3acc-9b32-fb1622c9f066"},{"kind":"sourceEvent","id":"d603d71e-4941-3da4-8b13-1646dfb6bf9e"},{"kind":"observation","id":"90db0f4b-03db-30c5-ad44-a9fd471d0b4d"},{"kind":"inventoryScope","id":"186f193b-e2f5-31f1-ba9e-fed4bb3bfa9f"},{"kind":"sourceAvailability","id":"6833a3c9-f82a-3ad9-a272-68030dd570f2/LOANS"},{"kind":"sourceAvailability","id":"6833a3c9-f82a-3ad9-a272-68030dd570f2/RETURNS"},{"kind":"sourceAvailability","id":"6833a3c9-f82a-3ad9-a272-68030dd570f2/RECEIPTS"},{"kind":"sourceAvailability","id":"6833a3c9-f82a-3ad9-a272-68030dd570f2/TRANSFERS"},{"kind":"sourceAvailability","id":"6833a3c9-f82a-3ad9-a272-68030dd570f2/REPAIRS"},{"kind":"sourceAvailability","id":"6833a3c9-f82a-3ad9-a272-68030dd570f2/INTEGRATIONS"},{"kind":"sourceAvailability","id":"6833a3c9-f82a-3ad9-a272-68030dd570f2/INVENTORY"},{"kind":"inventoryScope","id":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5"},{"kind":"inventoryScope","id":"eb1ce5f1-7397-3b45-9385-ef328e09fec8"}]`

- Session : `{"scopeId":"186f193b-e2f5-31f1-ba9e-fed4bb3bfa9f","locationId":"19124ccb-fb37-348d-81ff-686cdabbabb0","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"eb1ce5f1-7397-3b45-9385-ef328e09fec8","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 7b820205-9a08-3ee0-9834-f2aee50efe41 — dossier d'écart

- Exemplaire : `5c47331f-049d-3e4f-bfc0-cdd69220f0d5`
- Famille privée : ABSENCE_INEXPLIQUEE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"7693c8e5-6310-3cec-8a5d-adf3ae2e41b9","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":"1"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"mechanism":"Exemplaire resté au rayon, lecture de contrôle réussie.","physicalStatus":"ON_SHELF","physicalSiteId":"CENTRE","physicalLocationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"Le scan valide au rayon attendu réfute l'absence courante.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"5c47331f-049d-3e4f-bfc0-cdd69220f0d5","status":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","sourceEventIds":["8343485f-83c0-34eb-a051-35eaea0c7df2","382448a2-e3bf-3704-867f-e12589b098a4"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 4aea1789-98d0-3f73-81e8-08643f238889 | 2026-06-01T13:55:57Z | 2026-06-01T13:56:07Z | `{"operationId":"8a4dc21e-56a1-3d71-8dbe-750e34e4ec31","correlationId":"508a607c-506c-3277-ab3d-edc88add986b","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"43fb6375-7625-3ff6-af1a-2e0197bbad8f"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | d30039e8-f00c-332c-b3a6-a08dbc15d94f | 2026-06-02T13:55:57Z | 2026-06-02T13:56:07Z | `{"originalEventId":"4aea1789-98d0-3f73-81e8-08643f238889","correlationId":"508a607c-506c-3277-ab3d-edc88add986b","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | cb28e9bc-ef80-3853-ba56-d58a22e155af | 2026-07-31T13:55:57Z | 2026-07-31T13:56:07Z | `{"operationId":"f5555fdd-1e8f-3f65-b856-3caca81dd54f","correlationId":"dde3190a-c471-3d95-a551-a42b4cf97753","siteId":"CENTRE","locationId":null,"loanId":"fdfe308c-e193-38b1-bb24-2e49c69e4d88"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | d98f2e55-0193-3955-aba5-03db492e9e35 | 2026-07-31T13:56:57Z | 2026-07-31T13:57:07Z | `{"originalEventId":"cb28e9bc-ef80-3853-ba56-d58a22e155af","correlationId":"dde3190a-c471-3d95-a551-a42b4cf97753","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 33862bc7-8ecf-3242-b2dc-806d7343b4f7 | 2026-08-20T13:55:57Z | 2026-08-20T13:56:07Z | `{"operationId":"146048e4-1c1d-3432-8023-4f02f9dd2b00","correlationId":"f6530c5e-72f4-3fce-9bb8-afe62eef32f6","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"fdfe308c-e193-38b1-bb24-2e49c69e4d88"}` |
| Retour reçu (`ReturnReceived`) | 8343485f-83c0-34eb-a051-35eaea0c7df2 | 2026-08-20T13:56:27Z | 2026-08-20T13:56:37Z | `{"operationId":"74f15698-5f93-3b65-9d29-6fc5df5587ae","correlationId":"a429459b-85ab-327b-8e5b-3a3fe5097916","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"fdfe308c-e193-38b1-bb24-2e49c69e4d88"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 382448a2-e3bf-3704-867f-e12589b098a4 | 2026-08-21T13:55:57Z | 2026-08-21T13:56:07Z | `{"originalEventId":"8343485f-83c0-34eb-a051-35eaea0c7df2","correlationId":"a429459b-85ab-327b-8e5b-3a3fe5097916","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"e57c6c89-93d3-395a-b48f-cd33750c3ae7","copyId":"5c47331f-049d-3e4f-bfc0-cdd69220f0d5","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","scopeId":"3879a968-0f8b-33bb-b62b-63ec36357fbd","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"5c47331f-049d-3e4f-bfc0-cdd69220f0d5","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5c47331f-049d-3e4f-bfc0-cdd69220f0d5","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5c47331f-049d-3e4f-bfc0-cdd69220f0d5","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5c47331f-049d-3e4f-bfc0-cdd69220f0d5","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5c47331f-049d-3e4f-bfc0-cdd69220f0d5","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5c47331f-049d-3e4f-bfc0-cdd69220f0d5","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5c47331f-049d-3e4f-bfc0-cdd69220f0d5","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**
- `{"noteId":"9c30068f-3e33-3079-ad62-d64d40149a0f","copyId":"5c47331f-049d-3e4f-bfc0-cdd69220f0d5","text":"Nouvelle lecture du code-barres effectuée dans le rayon affecté.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"5c47331f-049d-3e4f-bfc0-cdd69220f0d5"},{"kind":"sourceEvent","id":"4aea1789-98d0-3f73-81e8-08643f238889"},{"kind":"sourceEvent","id":"d30039e8-f00c-332c-b3a6-a08dbc15d94f"},{"kind":"sourceEvent","id":"cb28e9bc-ef80-3853-ba56-d58a22e155af"},{"kind":"sourceEvent","id":"d98f2e55-0193-3955-aba5-03db492e9e35"},{"kind":"sourceEvent","id":"33862bc7-8ecf-3242-b2dc-806d7343b4f7"},{"kind":"sourceEvent","id":"8343485f-83c0-34eb-a051-35eaea0c7df2"},{"kind":"sourceEvent","id":"382448a2-e3bf-3704-867f-e12589b098a4"},{"kind":"observation","id":"e57c6c89-93d3-395a-b48f-cd33750c3ae7"},{"kind":"inventoryScope","id":"3879a968-0f8b-33bb-b62b-63ec36357fbd"},{"kind":"sourceAvailability","id":"5c47331f-049d-3e4f-bfc0-cdd69220f0d5/LOANS"},{"kind":"sourceAvailability","id":"5c47331f-049d-3e4f-bfc0-cdd69220f0d5/RETURNS"},{"kind":"sourceAvailability","id":"5c47331f-049d-3e4f-bfc0-cdd69220f0d5/RECEIPTS"},{"kind":"sourceAvailability","id":"5c47331f-049d-3e4f-bfc0-cdd69220f0d5/TRANSFERS"},{"kind":"sourceAvailability","id":"5c47331f-049d-3e4f-bfc0-cdd69220f0d5/REPAIRS"},{"kind":"sourceAvailability","id":"5c47331f-049d-3e4f-bfc0-cdd69220f0d5/INTEGRATIONS"},{"kind":"sourceAvailability","id":"5c47331f-049d-3e4f-bfc0-cdd69220f0d5/INVENTORY"},{"kind":"operatorNote","id":"9c30068f-3e33-3079-ad62-d64d40149a0f"},{"kind":"inventoryScope","id":"7d46c7a4-f137-377d-8437-5d11eeb4049d"},{"kind":"inventoryScope","id":"66954879-6228-3c31-bf72-7b217d3c9695"},{"kind":"inventoryScope","id":"2a6f7727-71d3-3a12-96f6-4b5e2b38ce02"},{"kind":"inventoryScope","id":"c0755c3b-6689-3d3d-8527-6c90e6c6e5d5"},{"kind":"inventoryScope","id":"3879a968-0f8b-33bb-b62b-63ec36357fbd"}]`

- Session : `{"scopeId":"3879a968-0f8b-33bb-b62b-63ec36357fbd","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"7d46c7a4-f137-377d-8437-5d11eeb4049d","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"66954879-6228-3c31-bf72-7b217d3c9695","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2a6f7727-71d3-3a12-96f6-4b5e2b38ce02","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"c0755c3b-6689-3d3d-8527-6c90e6c6e5d5","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"3879a968-0f8b-33bb-b62b-63ec36357fbd","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 7c0f21e5-49d9-3a0b-b652-064f2fd400c8 — dossier d'écart

- Exemplaire : `ca3ce9e8-5483-37cc-9b8a-e856dab4c48e`
- Famille privée : RETARD_INTEGRATION ; variante : POSITIVE
- Symptômes : `[{"symptomId":"fe367b5a-f808-3c89-ac12-54f0331dfdad","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["QUEUE_PENDING"],"hiddenSourceEvents":[],"mechanism":"Événement métier en attente normale dans la fenêtre contractuelle de 900 secondes.","businessEventId":"b96edf81-3645-3f7e-a4bc-2219f6a87072","physicalStatus":"ON_LOAN","physicalSiteId":"SUD","physicalLocationId":null,"projectionPerturbation":"QUEUED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["INTEGRATION_PENDING_WITHIN_SLA"],"explanation":"Prêt actif et traitement en file depuis moins de 900 secondes ; transition attendue, pas de panne persistante démontrée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Recontrôler après la fenêtre de traitement"]}`
- État du stock informatique : `{"copyId":"ca3ce9e8-5483-37cc-9b8a-e856dab4c48e","status":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","sourceEventIds":["c6bdf09c-a0f3-30c3-8d87-4a0e2bc697e8","4f42c7a5-a45b-3581-860b-4cffeb5eb015"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | ef1e31cd-db84-3dba-b989-c77ebe5e875c | 2026-06-01T15:07:05Z | 2026-06-01T15:07:15Z | `{"operationId":"a52b99a6-0951-3763-8251-b2cce526f635","correlationId":"72627883-5b51-3d31-b496-6ee4e023eed7","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"019f4644-74a3-33a8-9b18-dd057545c0ea"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 2bc2f2cc-e566-3b7f-b7dc-d7ba36a8041d | 2026-06-02T15:07:05Z | 2026-06-02T15:07:15Z | `{"originalEventId":"ef1e31cd-db84-3dba-b989-c77ebe5e875c","correlationId":"72627883-5b51-3d31-b496-6ee4e023eed7","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | a3f21044-6f2c-3bff-982a-cd643ad9812e | 2026-07-31T15:07:05Z | 2026-07-31T15:07:15Z | `{"operationId":"98bb2955-352b-3760-b1ee-47ac6868a613","correlationId":"d733d6a9-a20c-3ab9-a6ff-226005990173","siteId":"SUD","locationId":null,"loanId":"d55e6fe0-9df4-31db-b722-52907e516aaf"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 82ba9c0f-7ec7-3842-9e88-c7097675d566 | 2026-07-31T15:08:05Z | 2026-07-31T15:08:15Z | `{"originalEventId":"a3f21044-6f2c-3bff-982a-cd643ad9812e","correlationId":"d733d6a9-a20c-3ab9-a6ff-226005990173","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | c5f90b15-3449-3e1a-8d0a-f50869e48e6d | 2026-08-20T15:07:05Z | 2026-08-20T15:07:15Z | `{"operationId":"93f57abf-d3e1-3ab4-bc92-87589c57cdd4","correlationId":"f66c9051-f871-31ff-b593-108b5605348a","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"d55e6fe0-9df4-31db-b722-52907e516aaf"}` |
| Retour reçu (`ReturnReceived`) | c6bdf09c-a0f3-30c3-8d87-4a0e2bc697e8 | 2026-08-20T15:07:35Z | 2026-08-20T15:07:45Z | `{"operationId":"5cc48ba6-d540-3d92-bf75-77446db5c63b","correlationId":"cba4dea2-0032-3db8-be79-b7041b1df18f","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"d55e6fe0-9df4-31db-b722-52907e516aaf"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 4f42c7a5-a45b-3581-860b-4cffeb5eb015 | 2026-08-21T15:07:05Z | 2026-08-21T15:07:15Z | `{"originalEventId":"c6bdf09c-a0f3-30c3-8d87-4a0e2bc697e8","correlationId":"cba4dea2-0032-3db8-be79-b7041b1df18f","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | b96edf81-3645-3f7e-a4bc-2219f6a87072 | 2026-09-30T11:52:43Z | 2026-09-30T11:52:53Z | `{"operationId":"9b4f630a-4bea-3995-a88f-1850db136572","correlationId":"bc71837c-61b8-3a37-adc3-d8ff78c6cf71","siteId":"SUD","locationId":null,"loanId":"41b4245d-6a4d-3b8d-8690-7937a08314da"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 38683b67-cfd5-3a7d-94f7-ea5a0f90c378 | 2026-09-30T11:53:13Z | 2026-09-30T11:53:23Z | `{"originalEventId":"b96edf81-3645-3f7e-a4bc-2219f6a87072","correlationId":"bc71837c-61b8-3a37-adc3-d8ff78c6cf71","outcome":"QUEUED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"ca3ce9e8-5483-37cc-9b8a-e856dab4c48e","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ca3ce9e8-5483-37cc-9b8a-e856dab4c48e","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ca3ce9e8-5483-37cc-9b8a-e856dab4c48e","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ca3ce9e8-5483-37cc-9b8a-e856dab4c48e","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ca3ce9e8-5483-37cc-9b8a-e856dab4c48e","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ca3ce9e8-5483-37cc-9b8a-e856dab4c48e","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ca3ce9e8-5483-37cc-9b8a-e856dab4c48e","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"ca3ce9e8-5483-37cc-9b8a-e856dab4c48e"},{"kind":"sourceEvent","id":"ef1e31cd-db84-3dba-b989-c77ebe5e875c"},{"kind":"sourceEvent","id":"2bc2f2cc-e566-3b7f-b7dc-d7ba36a8041d"},{"kind":"sourceEvent","id":"a3f21044-6f2c-3bff-982a-cd643ad9812e"},{"kind":"sourceEvent","id":"82ba9c0f-7ec7-3842-9e88-c7097675d566"},{"kind":"sourceEvent","id":"c5f90b15-3449-3e1a-8d0a-f50869e48e6d"},{"kind":"sourceEvent","id":"c6bdf09c-a0f3-30c3-8d87-4a0e2bc697e8"},{"kind":"sourceEvent","id":"4f42c7a5-a45b-3581-860b-4cffeb5eb015"},{"kind":"sourceEvent","id":"b96edf81-3645-3f7e-a4bc-2219f6a87072"},{"kind":"sourceEvent","id":"38683b67-cfd5-3a7d-94f7-ea5a0f90c378"},{"kind":"sourceAvailability","id":"ca3ce9e8-5483-37cc-9b8a-e856dab4c48e/LOANS"},{"kind":"sourceAvailability","id":"ca3ce9e8-5483-37cc-9b8a-e856dab4c48e/RETURNS"},{"kind":"sourceAvailability","id":"ca3ce9e8-5483-37cc-9b8a-e856dab4c48e/RECEIPTS"},{"kind":"sourceAvailability","id":"ca3ce9e8-5483-37cc-9b8a-e856dab4c48e/TRANSFERS"},{"kind":"sourceAvailability","id":"ca3ce9e8-5483-37cc-9b8a-e856dab4c48e/REPAIRS"},{"kind":"sourceAvailability","id":"ca3ce9e8-5483-37cc-9b8a-e856dab4c48e/INTEGRATIONS"},{"kind":"sourceAvailability","id":"ca3ce9e8-5483-37cc-9b8a-e856dab4c48e/INVENTORY"},{"kind":"inventoryScope","id":"7f91fb45-70cc-3811-b222-43a5370aa433"},{"kind":"inventoryScope","id":"202855ec-9065-3be6-a0c2-07ce228ecf21"},{"kind":"inventoryScope","id":"1b4f280a-3145-3607-a009-dca44e9d4181"},{"kind":"inventoryScope","id":"2948b706-5519-3fcb-9c6a-82f0197080d2"},{"kind":"inventoryScope","id":"2977b6c8-c0f0-3d4f-aba5-6b5d16436fb8"}]`

- Session : `{"scopeId":"7f91fb45-70cc-3811-b222-43a5370aa433","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"202855ec-9065-3be6-a0c2-07ce228ecf21","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"1b4f280a-3145-3607-a009-dca44e9d4181","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2948b706-5519-3fcb-9c6a-82f0197080d2","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2977b6c8-c0f0-3d4f-aba5-6b5d16436fb8","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 7e159718-3dc3-3b68-8c85-0c06789ebac2 — dossier d'écart

- Exemplaire : `042e68d7-437f-31af-be1c-e1e2973d8d32`
- Famille privée : INVENTAIRE_INCOMPLET ; variante : POSITIVE
- Symptômes : `[{"symptomId":"649bcdde-a627-3811-b502-4ff80a73bf5e","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["COLLECTION_INTERRUPTED"],"mechanism":"Interruption de collecte ; exemplaire resté au rayon.","physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["INVENTORY_INCOMPLETE"],"explanation":"La session interrompue explique l'incertitude de comptage ; la présence physique reste à vérifier.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État du stock informatique : `{"copyId":"042e68d7-437f-31af-be1c-e1e2973d8d32","status":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","sourceEventIds":["20365270-3851-340e-bc4b-9ee9c23abb84","c96d2ecb-2b6c-359e-8568-63abf91a17b3"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 5b80fd21-272d-3238-88d9-13e93fbec747 | 2026-06-02T06:32:20Z | 2026-06-02T06:32:30Z | `{"operationId":"672e3898-d782-3573-b564-a9ec56573323","correlationId":"bd593d1b-82a8-3e1c-97ef-37d2c71221fa","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"d3fffcfe-df52-3d3f-910f-9fad918ac47e"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 0bae1eb9-8c79-3b5f-b986-7af3963b7605 | 2026-06-03T06:32:20Z | 2026-06-03T06:32:30Z | `{"originalEventId":"5b80fd21-272d-3238-88d9-13e93fbec747","correlationId":"bd593d1b-82a8-3e1c-97ef-37d2c71221fa","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | 7166af20-2bd8-36b9-843c-f1335d5d93e4 | 2026-08-01T06:32:20Z | 2026-08-01T06:32:30Z | `{"operationId":"932c0c64-6515-3ff4-a30e-9de5a62cddce","correlationId":"38b3a1e1-4c7a-324a-8bef-a6944f653f08","siteId":"NORD","locationId":null,"loanId":"63e95af6-d4de-3ac2-b440-5d1f31b2cb17"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 505f4cf2-a2b0-3ce5-a33f-3fe12c3a75cf | 2026-08-01T06:33:20Z | 2026-08-01T06:33:30Z | `{"originalEventId":"7166af20-2bd8-36b9-843c-f1335d5d93e4","correlationId":"38b3a1e1-4c7a-324a-8bef-a6944f653f08","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 3c640e59-6c09-33ae-9829-9b1ba8385aab | 2026-08-21T06:32:20Z | 2026-08-21T06:32:30Z | `{"operationId":"0918b58d-4e82-38b2-93a1-ef715822b533","correlationId":"db0fe48d-fbf9-3700-b941-c70f15520a7d","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"63e95af6-d4de-3ac2-b440-5d1f31b2cb17"}` |
| Retour reçu (`ReturnReceived`) | 20365270-3851-340e-bc4b-9ee9c23abb84 | 2026-08-21T06:32:50Z | 2026-08-21T06:33:00Z | `{"operationId":"7eced44d-410d-3c40-90f7-e65faff575a0","correlationId":"8b95a42d-4cdb-3ce9-80fb-9043fc39146e","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"63e95af6-d4de-3ac2-b440-5d1f31b2cb17"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | c96d2ecb-2b6c-359e-8568-63abf91a17b3 | 2026-08-22T06:32:20Z | 2026-08-22T06:32:30Z | `{"originalEventId":"20365270-3851-340e-bc4b-9ee9c23abb84","correlationId":"8b95a42d-4cdb-3ce9-80fb-9043fc39146e","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"042e68d7-437f-31af-be1c-e1e2973d8d32","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"042e68d7-437f-31af-be1c-e1e2973d8d32","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"042e68d7-437f-31af-be1c-e1e2973d8d32","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"042e68d7-437f-31af-be1c-e1e2973d8d32","source":"INVENTORY","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"042e68d7-437f-31af-be1c-e1e2973d8d32","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"042e68d7-437f-31af-be1c-e1e2973d8d32","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"042e68d7-437f-31af-be1c-e1e2973d8d32","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**
- `{"noteId":"2a02dfb4-a116-3783-a1c1-4e788f05386b","copyId":"042e68d7-437f-31af-be1c-e1e2973d8d32","text":"Le terminal de collecte a cessé de répondre pendant le passage ; aucune conclusion sur les exemplaires non lus.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"042e68d7-437f-31af-be1c-e1e2973d8d32"},{"kind":"sourceEvent","id":"5b80fd21-272d-3238-88d9-13e93fbec747"},{"kind":"sourceEvent","id":"0bae1eb9-8c79-3b5f-b986-7af3963b7605"},{"kind":"sourceEvent","id":"7166af20-2bd8-36b9-843c-f1335d5d93e4"},{"kind":"sourceEvent","id":"505f4cf2-a2b0-3ce5-a33f-3fe12c3a75cf"},{"kind":"sourceEvent","id":"3c640e59-6c09-33ae-9829-9b1ba8385aab"},{"kind":"sourceEvent","id":"20365270-3851-340e-bc4b-9ee9c23abb84"},{"kind":"sourceEvent","id":"c96d2ecb-2b6c-359e-8568-63abf91a17b3"},{"kind":"sourceAvailability","id":"042e68d7-437f-31af-be1c-e1e2973d8d32/LOANS"},{"kind":"sourceAvailability","id":"042e68d7-437f-31af-be1c-e1e2973d8d32/RETURNS"},{"kind":"sourceAvailability","id":"042e68d7-437f-31af-be1c-e1e2973d8d32/RECEIPTS"},{"kind":"sourceAvailability","id":"042e68d7-437f-31af-be1c-e1e2973d8d32/TRANSFERS"},{"kind":"sourceAvailability","id":"042e68d7-437f-31af-be1c-e1e2973d8d32/REPAIRS"},{"kind":"sourceAvailability","id":"042e68d7-437f-31af-be1c-e1e2973d8d32/INTEGRATIONS"},{"kind":"sourceAvailability","id":"042e68d7-437f-31af-be1c-e1e2973d8d32/INVENTORY"},{"kind":"operatorNote","id":"2a02dfb4-a116-3783-a1c1-4e788f05386b"},{"kind":"inventoryScope","id":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5"},{"kind":"inventoryScope","id":"eb1ce5f1-7397-3b45-9385-ef328e09fec8"},{"kind":"inventoryScope","id":"d0c55bda-319d-3537-857a-5d135f796cab"},{"kind":"inventoryScope","id":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb"},{"kind":"inventoryScope","id":"d9842c03-ba37-324b-b71b-e58ac7147d4c"},{"kind":"inventoryScope","id":"d9842c03-ba37-324b-b71b-e58ac7147d4c"}]`

- Session : `{"scopeId":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"eb1ce5f1-7397-3b45-9385-ef328e09fec8","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"d0c55bda-319d-3537-857a-5d135f796cab","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"d9842c03-ba37-324b-b71b-e58ac7147d4c","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"d9842c03-ba37-324b-b71b-e58ac7147d4c","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 7e29f90d-03d2-3589-9b01-213bc5245091 — dossier d'écart

- Exemplaire : `475c7c32-070c-3ca9-91d2-b9ce3746295b`
- Famille privée : LOCALISATION ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"1f798af7-def2-3b88-959f-cf991d5548f5","kind":"COUNT_UNCERTAIN","expectedValue":"00f09742-c706-31b7-a4a0-c694a087403f","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"mechanism":"Déplacement physique sans modification de l'affectation ; aucune attribution à un opérateur.","activePerturbations":["LOCATION_CHANGE_UNPROJECTED"],"physicalStatus":"ON_SHELF","physicalSiteId":"SUD","physicalLocationId":"128fab03-f193-3647-a67e-b835a30e7583"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"Identité du scan ambiguë ; ne pas attribuer un emplacement physique certain.","forbiddenClaims":["Vol","Perte définitive","Responsabilité d'un opérateur"],"recommendedChecks":["Refaire le scan et vérifier le code-barres"]}`
- État du stock informatique : `{"copyId":"475c7c32-070c-3ca9-91d2-b9ce3746295b","status":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","sourceEventIds":["52a5e2cd-be66-3201-879e-92b8ceba60b0","52b48b13-1059-30e1-ac9d-4544a728be12"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | c9b5c430-d98f-3a70-86af-cc522c486611 | 2026-06-02T08:18:01Z | 2026-06-02T08:18:11Z | `{"operationId":"7a4f4f40-16bd-3abc-b187-0147f1a4d56c","correlationId":"29b35a42-499a-37fe-9504-0ac7bbf49ef0","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"29fdbd61-7b4b-3188-8f2a-26ff9a767dfe"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | ac1d3c39-ad69-33f8-a2fb-51f922713cd7 | 2026-06-03T08:18:01Z | 2026-06-03T08:18:11Z | `{"originalEventId":"c9b5c430-d98f-3a70-86af-cc522c486611","correlationId":"29b35a42-499a-37fe-9504-0ac7bbf49ef0","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | bd2b19d9-eab5-3cc2-9a98-2c0ef9c1ff88 | 2026-08-01T08:18:01Z | 2026-08-01T08:18:11Z | `{"operationId":"afc567e0-f8c9-3c58-b941-57d66a276a01","correlationId":"dde38784-f845-329c-9822-5f6c478969b5","siteId":"SUD","locationId":null,"loanId":"acb28dc0-ac6c-3055-9af6-4534082bbfa4"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | a74c4281-587c-33ad-9d00-8117778b8c3a | 2026-08-01T08:19:01Z | 2026-08-01T08:19:11Z | `{"originalEventId":"bd2b19d9-eab5-3cc2-9a98-2c0ef9c1ff88","correlationId":"dde38784-f845-329c-9822-5f6c478969b5","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 8b3cb58c-ddb6-382d-9032-13f6d8644698 | 2026-08-21T08:18:01Z | 2026-08-21T08:18:11Z | `{"operationId":"ac183ff5-8b4e-3410-9fb0-ba02e84a706d","correlationId":"496e6261-6a9b-3dbe-93a4-be0e140f2acf","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"acb28dc0-ac6c-3055-9af6-4534082bbfa4"}` |
| Retour reçu (`ReturnReceived`) | 52a5e2cd-be66-3201-879e-92b8ceba60b0 | 2026-08-21T08:18:31Z | 2026-08-21T08:18:41Z | `{"operationId":"9c6b0231-4d75-3138-9128-d85f6dea6674","correlationId":"5120d9e1-d479-3f59-b531-a7fcb066dda2","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"acb28dc0-ac6c-3055-9af6-4534082bbfa4"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 52b48b13-1059-30e1-ac9d-4544a728be12 | 2026-08-22T08:18:01Z | 2026-08-22T08:18:11Z | `{"originalEventId":"52a5e2cd-be66-3201-879e-92b8ceba60b0","correlationId":"5120d9e1-d479-3f59-b531-a7fcb066dda2","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"05c40a16-8a01-3ef9-9707-97032cfbf8f5","copyId":"475c7c32-070c-3ca9-91d2-b9ce3746295b","locationId":"128fab03-f193-3647-a67e-b835a30e7583","scopeId":"d2a9d7ba-9a94-3aef-855c-ddae0939cf4a","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"475c7c32-070c-3ca9-91d2-b9ce3746295b","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"475c7c32-070c-3ca9-91d2-b9ce3746295b","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"475c7c32-070c-3ca9-91d2-b9ce3746295b","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"475c7c32-070c-3ca9-91d2-b9ce3746295b","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"475c7c32-070c-3ca9-91d2-b9ce3746295b","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"475c7c32-070c-3ca9-91d2-b9ce3746295b","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"475c7c32-070c-3ca9-91d2-b9ce3746295b","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"observation","id":"05c40a16-8a01-3ef9-9707-97032cfbf8f5"},{"kind":"systemState","id":"475c7c32-070c-3ca9-91d2-b9ce3746295b"}]`

## Cas 810551b4-1897-3201-8d42-cb9e7874e8fe — dossier d'écart

- Exemplaire : `e8d5d9fc-4320-33ab-beda-9468b328b8fe`
- Famille privée : TRANSFERT_INCOHERENT ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"dbf90c7c-0643-3852-bc91-a8ce2aac0832","kind":"STATUS_MISMATCH","expectedValue":"IN_TRANSIT","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"86ed1adc-4818-3901-8ffc-fc2c95672496","copyId":"e8d5d9fc-4320-33ab-beda-9468b328b8fe","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T10:53:23Z","recordedAt":"2026-09-27T10:53:33Z","payload":{"originalEventId":"b6ed0c1d-5a54-3bf6-b2a1-2144ef5773f0","correlationId":"8e4a13da-0170-37b5-984e-df1abeee4b96","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"b6ed0c1d-5a54-3bf6-b2a1-2144ef5773f0","physicalStatus":"RECEIVING","physicalSiteId":"SUD","physicalLocationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État du stock informatique : `{"copyId":"e8d5d9fc-4320-33ab-beda-9468b328b8fe","status":"IN_TRANSIT","siteId":"NORD","locationId":null,"sourceEventIds":["7c955315-d144-3b70-ae8c-eda2284c71f1","9f01fdc3-35a2-3862-a657-03d1e53ab80c"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 6f88431a-6f97-37ea-ac44-caa88222b0f8 | 2026-06-01T16:59:51Z | 2026-06-01T17:00:01Z | `{"operationId":"daf41089-04b5-3673-aff2-72d31d614f41","correlationId":"ffb21644-be01-302c-8ad9-65c6ba7b140b","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"2fe16ad5-8d98-3f5c-a707-c2581122a20b"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 379eaef2-d506-38ab-95dc-eb4a21c2430a | 2026-06-02T16:59:51Z | 2026-06-02T17:00:01Z | `{"originalEventId":"6f88431a-6f97-37ea-ac44-caa88222b0f8","correlationId":"ffb21644-be01-302c-8ad9-65c6ba7b140b","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | dd9b90e0-1e27-38a9-9a71-8026aa4421f7 | 2026-07-31T16:59:51Z | 2026-07-31T17:00:01Z | `{"operationId":"219a8559-2b3b-35f3-a314-0336600b8ad8","correlationId":"edd41a00-08a7-3703-a37d-52c9873c1ff3","siteId":"NORD","locationId":null,"loanId":"e065d4e5-4b10-3f5a-87e2-14b7e881a1f5"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 5fc5b026-4cde-3c50-a110-8e882b8fbdde | 2026-07-31T17:00:51Z | 2026-07-31T17:01:01Z | `{"originalEventId":"dd9b90e0-1e27-38a9-9a71-8026aa4421f7","correlationId":"edd41a00-08a7-3703-a37d-52c9873c1ff3","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 11c9b55e-6f9c-3d27-99d1-2ec15c3d4153 | 2026-08-20T16:59:51Z | 2026-08-20T17:00:01Z | `{"operationId":"dc03b362-9677-3063-b783-f7d50a9b29b7","correlationId":"c6a1f982-5b78-36b2-91cd-906a2320a7a3","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"e065d4e5-4b10-3f5a-87e2-14b7e881a1f5"}` |
| Retour reçu (`ReturnReceived`) | 53f8cd60-8f3e-34c2-9840-8e205528e24b | 2026-08-20T17:00:21Z | 2026-08-20T17:00:31Z | `{"operationId":"719d5d65-8894-3161-ac51-9ebb171207a4","correlationId":"dbd33781-8da3-3fd5-b00f-f9e8dd80f78e","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"e065d4e5-4b10-3f5a-87e2-14b7e881a1f5"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | eea64b67-2454-3ac5-bcfa-a4ba75ab7bd9 | 2026-08-21T16:59:51Z | 2026-08-21T17:00:01Z | `{"originalEventId":"53f8cd60-8f3e-34c2-9840-8e205528e24b","correlationId":"dbd33781-8da3-3fd5-b00f-f9e8dd80f78e","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Transfert expédié (`TransferDispatched`) | 7c955315-d144-3b70-ae8c-eda2284c71f1 | 2026-09-26T10:52:53Z | 2026-09-26T10:53:03Z | `{"operationId":"405db9b7-7598-3fb2-850e-cdc9dcadb76b","correlationId":"5cf8db79-f80b-3a0d-8966-8ffc97e850e7","siteId":"NORD","locationId":null,"transferId":"126eee8b-4c11-30d7-979e-390f56bee93c","fromSiteId":"NORD","toSiteId":"SUD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 9f01fdc3-35a2-3862-a657-03d1e53ab80c | 2026-09-26T10:53:53Z | 2026-09-26T10:54:03Z | `{"originalEventId":"7c955315-d144-3b70-ae8c-eda2284c71f1","correlationId":"5cf8db79-f80b-3a0d-8966-8ffc97e850e7","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"NORD","locationId":null}` |
| Transfert reçu (`TransferReceived`) | b6ed0c1d-5a54-3bf6-b2a1-2144ef5773f0 | 2026-09-27T10:52:53Z | 2026-09-27T10:53:03Z | `{"operationId":"34115d17-0986-393f-b2b3-f824b511115b","correlationId":"8e4a13da-0170-37b5-984e-df1abeee4b96","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","transferId":"126eee8b-4c11-30d7-979e-390f56bee93c","fromSiteId":"NORD","toSiteId":"SUD"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"77c6f649-dbdd-32d3-a768-bc972a7cf689","copyId":"e8d5d9fc-4320-33ab-beda-9468b328b8fe","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","scopeId":"feed50c2-3619-3a09-ad10-33d0609a6132","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"e8d5d9fc-4320-33ab-beda-9468b328b8fe","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e8d5d9fc-4320-33ab-beda-9468b328b8fe","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"e8d5d9fc-4320-33ab-beda-9468b328b8fe","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e8d5d9fc-4320-33ab-beda-9468b328b8fe","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e8d5d9fc-4320-33ab-beda-9468b328b8fe","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e8d5d9fc-4320-33ab-beda-9468b328b8fe","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e8d5d9fc-4320-33ab-beda-9468b328b8fe","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"e8d5d9fc-4320-33ab-beda-9468b328b8fe"},{"kind":"sourceEvent","id":"6f88431a-6f97-37ea-ac44-caa88222b0f8"},{"kind":"sourceEvent","id":"379eaef2-d506-38ab-95dc-eb4a21c2430a"},{"kind":"sourceEvent","id":"dd9b90e0-1e27-38a9-9a71-8026aa4421f7"},{"kind":"sourceEvent","id":"5fc5b026-4cde-3c50-a110-8e882b8fbdde"},{"kind":"sourceEvent","id":"11c9b55e-6f9c-3d27-99d1-2ec15c3d4153"},{"kind":"sourceEvent","id":"53f8cd60-8f3e-34c2-9840-8e205528e24b"},{"kind":"sourceEvent","id":"eea64b67-2454-3ac5-bcfa-a4ba75ab7bd9"},{"kind":"sourceEvent","id":"7c955315-d144-3b70-ae8c-eda2284c71f1"},{"kind":"sourceEvent","id":"9f01fdc3-35a2-3862-a657-03d1e53ab80c"},{"kind":"sourceEvent","id":"b6ed0c1d-5a54-3bf6-b2a1-2144ef5773f0"},{"kind":"observation","id":"77c6f649-dbdd-32d3-a768-bc972a7cf689"},{"kind":"inventoryScope","id":"feed50c2-3619-3a09-ad10-33d0609a6132"},{"kind":"sourceAvailability","id":"e8d5d9fc-4320-33ab-beda-9468b328b8fe/LOANS"},{"kind":"sourceAvailability","id":"e8d5d9fc-4320-33ab-beda-9468b328b8fe/RETURNS"},{"kind":"sourceAvailability","id":"e8d5d9fc-4320-33ab-beda-9468b328b8fe/RECEIPTS"},{"kind":"sourceAvailability","id":"e8d5d9fc-4320-33ab-beda-9468b328b8fe/TRANSFERS"},{"kind":"sourceAvailability","id":"e8d5d9fc-4320-33ab-beda-9468b328b8fe/REPAIRS"},{"kind":"sourceAvailability","id":"e8d5d9fc-4320-33ab-beda-9468b328b8fe/INTEGRATIONS"},{"kind":"sourceAvailability","id":"e8d5d9fc-4320-33ab-beda-9468b328b8fe/INVENTORY"}]`

- Session : `{"scopeId":"feed50c2-3619-3a09-ad10-33d0609a6132","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 85d8c31c-e97e-377c-96b7-ccf5937b8bd3 — dossier d'écart

- Exemplaire : `1452a26c-75fd-33d0-95b6-91111cae5b72`
- Famille privée : LOCALISATION ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"a43c7b4b-702e-331b-9681-c53dbf108b4a","kind":"COUNT_UNCERTAIN","expectedValue":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"mechanism":"Déplacement physique sans modification de l'affectation ; aucune attribution à un opérateur.","activePerturbations":["LOCATION_CHANGE_UNPROJECTED"],"physicalStatus":"ON_SHELF","physicalSiteId":"CENTRE","physicalLocationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"Identité du scan ambiguë ; ne pas attribuer un emplacement physique certain.","forbiddenClaims":["Vol","Perte définitive","Responsabilité d'un opérateur"],"recommendedChecks":["Refaire le scan et vérifier le code-barres"]}`
- État du stock informatique : `{"copyId":"1452a26c-75fd-33d0-95b6-91111cae5b72","status":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","sourceEventIds":["5ad00138-8f61-385a-b9bb-0985d8cc4a74","98de241f-c7bd-3b2f-b37d-a55fb6e671bb"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 76bc1ccf-eb90-317a-b688-ff28d1125913 | 2026-06-01T23:12:15Z | 2026-06-01T23:12:25Z | `{"operationId":"6fda2ad0-22cf-3145-ac70-ebbb9d8b65ea","correlationId":"f4b67268-c144-3201-b837-0c6acf218406","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"d2d2e498-05ba-3b8b-a8a1-cef5c0f633db"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | b534d9e0-0f30-3e67-83bc-7db39cae0be8 | 2026-06-02T23:12:15Z | 2026-06-02T23:12:25Z | `{"originalEventId":"76bc1ccf-eb90-317a-b688-ff28d1125913","correlationId":"f4b67268-c144-3201-b837-0c6acf218406","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | efe39878-ae22-37c3-a144-dd3360930ce8 | 2026-07-31T23:12:15Z | 2026-07-31T23:12:25Z | `{"operationId":"6390c481-b0e6-3de3-99b8-2038f6703c46","correlationId":"e5881060-3ab7-32d8-9bc5-bd19223d6291","siteId":"CENTRE","locationId":null,"loanId":"49ab903b-873c-30fb-9206-49ea85446c6b"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 7daadae1-0370-371e-9813-8c9a9f5a1138 | 2026-07-31T23:13:15Z | 2026-07-31T23:13:25Z | `{"originalEventId":"efe39878-ae22-37c3-a144-dd3360930ce8","correlationId":"e5881060-3ab7-32d8-9bc5-bd19223d6291","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | a157eb99-9fa7-3a2c-b6ea-f6d88ecb1a90 | 2026-08-20T23:12:15Z | 2026-08-20T23:12:25Z | `{"operationId":"5c4a4f5b-dc37-34cb-9606-817c9d956f92","correlationId":"9e2c3350-d5b7-3989-b8b4-b464f97ba33e","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"49ab903b-873c-30fb-9206-49ea85446c6b"}` |
| Retour reçu (`ReturnReceived`) | 5ad00138-8f61-385a-b9bb-0985d8cc4a74 | 2026-08-20T23:12:45Z | 2026-08-20T23:12:55Z | `{"operationId":"6e77586b-380f-30a1-ae15-c3ca14db775a","correlationId":"333070c1-e57f-3f64-8762-b2026f37b09c","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"49ab903b-873c-30fb-9206-49ea85446c6b"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 98de241f-c7bd-3b2f-b37d-a55fb6e671bb | 2026-08-21T23:12:15Z | 2026-08-21T23:12:25Z | `{"originalEventId":"5ad00138-8f61-385a-b9bb-0985d8cc4a74","correlationId":"333070c1-e57f-3f64-8762-b2026f37b09c","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"071fbcff-28d8-3a8f-8cab-d1e701e8bf40","copyId":"1452a26c-75fd-33d0-95b6-91111cae5b72","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","scopeId":"460d62cc-9a6d-3ba8-8ba9-9385ac55da26","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"1452a26c-75fd-33d0-95b6-91111cae5b72","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1452a26c-75fd-33d0-95b6-91111cae5b72","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1452a26c-75fd-33d0-95b6-91111cae5b72","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1452a26c-75fd-33d0-95b6-91111cae5b72","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1452a26c-75fd-33d0-95b6-91111cae5b72","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1452a26c-75fd-33d0-95b6-91111cae5b72","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1452a26c-75fd-33d0-95b6-91111cae5b72","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"observation","id":"071fbcff-28d8-3a8f-8cab-d1e701e8bf40"},{"kind":"systemState","id":"1452a26c-75fd-33d0-95b6-91111cae5b72"}]`

## Cas 8631bf9a-dfd7-3b30-9ecd-72ee88c2e532 — dossier d'écart

- Exemplaire : `246e6131-39c3-3839-8119-9acc6fc71af4`
- Famille privée : RECEPTION_NON_INTEGREE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"702a4af6-b7aa-385a-b839-4bb0fe7163a1","kind":"UNEXPECTED_PRESENCE","expectedValue":"NOT_REGISTERED","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"dc5c3883-68b7-3d62-9324-f70e95c6df27","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["RECEIPT_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"246e6131-39c3-3839-8119-9acc6fc71af4","status":"NOT_REGISTERED","siteId":"NORD","locationId":null,"sourceEventIds":[]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | dc5c3883-68b7-3d62-9324-f70e95c6df27 | 2026-09-27T09:37:49Z | 2026-09-27T09:37:59Z | `{"operationId":"31ab6950-bf03-3c97-a26e-f1867b33d4d4","correlationId":"a7b2612f-2536-37bd-8f51-67ee8acfc9e2","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"1c7e5ee7-7a46-3c26-a5dc-ef483fd12f47"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 1ec99995-15d3-385d-a21e-da47ed62e17d | 2026-09-27T09:38:19Z | 2026-09-27T09:38:29Z | `{"originalEventId":"dc5c3883-68b7-3d62-9324-f70e95c6df27","correlationId":"a7b2612f-2536-37bd-8f51-67ee8acfc9e2","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"c9ea207e-fdff-3dac-a870-b5d914526b73","copyId":"246e6131-39c3-3839-8119-9acc6fc71af4","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","scopeId":"38cc059e-8845-3745-a788-0f99b8909a8c","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"246e6131-39c3-3839-8119-9acc6fc71af4","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"246e6131-39c3-3839-8119-9acc6fc71af4","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"246e6131-39c3-3839-8119-9acc6fc71af4","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"246e6131-39c3-3839-8119-9acc6fc71af4","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"246e6131-39c3-3839-8119-9acc6fc71af4","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"246e6131-39c3-3839-8119-9acc6fc71af4","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"246e6131-39c3-3839-8119-9acc6fc71af4","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"246e6131-39c3-3839-8119-9acc6fc71af4"},{"kind":"sourceEvent","id":"dc5c3883-68b7-3d62-9324-f70e95c6df27"},{"kind":"sourceEvent","id":"1ec99995-15d3-385d-a21e-da47ed62e17d"},{"kind":"observation","id":"c9ea207e-fdff-3dac-a870-b5d914526b73"},{"kind":"inventoryScope","id":"38cc059e-8845-3745-a788-0f99b8909a8c"},{"kind":"sourceAvailability","id":"246e6131-39c3-3839-8119-9acc6fc71af4/LOANS"},{"kind":"sourceAvailability","id":"246e6131-39c3-3839-8119-9acc6fc71af4/RETURNS"},{"kind":"sourceAvailability","id":"246e6131-39c3-3839-8119-9acc6fc71af4/RECEIPTS"},{"kind":"sourceAvailability","id":"246e6131-39c3-3839-8119-9acc6fc71af4/TRANSFERS"},{"kind":"sourceAvailability","id":"246e6131-39c3-3839-8119-9acc6fc71af4/REPAIRS"},{"kind":"sourceAvailability","id":"246e6131-39c3-3839-8119-9acc6fc71af4/INTEGRATIONS"},{"kind":"sourceAvailability","id":"246e6131-39c3-3839-8119-9acc6fc71af4/INVENTORY"}]`

- Session : `{"scopeId":"38cc059e-8845-3745-a788-0f99b8909a8c","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 87030190-e607-3350-82b3-54d058cd5bd1 — dossier d'écart

- Exemplaire : `096205a5-ec6a-31da-971c-60a97f6a57d4`
- Famille privée : REPARATION_NON_PROJETEE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"6afc8987-0c34-3f6c-8a88-5915ea10e05f","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"140aa747-f73d-35e5-9c4b-007859dd85db","physicalStatus":"IN_REPAIR","physicalSiteId":"SUD","physicalLocationId":"a570b572-6b24-3318-9575-eb310588592b","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"096205a5-ec6a-31da-971c-60a97f6a57d4","status":"IN_REPAIR","siteId":"SUD","locationId":"a570b572-6b24-3318-9575-eb310588592b","sourceEventIds":["140aa747-f73d-35e5-9c4b-007859dd85db","2474705e-f3f4-3eec-ad3c-0eaddaead492"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | ac44843e-f8ce-3dee-a598-f880babb21d0 | 2026-06-01T23:46:13Z | 2026-06-01T23:46:23Z | `{"operationId":"dc119834-cfdd-3a5b-a12f-b16ef17cee2d","correlationId":"fac365e1-283d-3aea-9e93-c36413a492c6","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"ff92a88b-5e9f-3e30-bfd3-3b5964133ca9"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 8dc613ec-53eb-3a26-84e0-92fb9556f2a5 | 2026-06-02T23:46:13Z | 2026-06-02T23:46:23Z | `{"originalEventId":"ac44843e-f8ce-3dee-a598-f880babb21d0","correlationId":"fac365e1-283d-3aea-9e93-c36413a492c6","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 50fca1fc-f06a-3be6-8460-aa28f22ef052 | 2026-07-31T23:46:13Z | 2026-07-31T23:46:23Z | `{"operationId":"227f3b3c-b61e-3388-9881-c0b22ecf88af","correlationId":"de2b67c6-d783-30aa-8e89-cd47b5b425e4","siteId":"SUD","locationId":null,"loanId":"06cf9a54-21b1-39f1-b135-de5dd414e238"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 16d07785-2eef-34d5-999b-ec79eef2b4d5 | 2026-07-31T23:47:13Z | 2026-07-31T23:47:23Z | `{"originalEventId":"50fca1fc-f06a-3be6-8460-aa28f22ef052","correlationId":"de2b67c6-d783-30aa-8e89-cd47b5b425e4","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | bcc9a587-1991-3927-a018-0ff1a93af2b8 | 2026-08-20T23:46:13Z | 2026-08-20T23:46:23Z | `{"operationId":"80eaa9f7-4043-372c-b0d0-73f5cd6b078a","correlationId":"7217c64e-396b-3778-8a70-7bcbd24963eb","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"06cf9a54-21b1-39f1-b135-de5dd414e238"}` |
| Retour reçu (`ReturnReceived`) | fa693bd6-3060-3310-80e3-ae402d80a91e | 2026-08-20T23:46:43Z | 2026-08-20T23:46:53Z | `{"operationId":"59e6c6a8-1219-34f4-8ee4-77507afce0f0","correlationId":"83663168-fb1e-3012-99f5-b49b8bfb2857","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"06cf9a54-21b1-39f1-b135-de5dd414e238"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 31f27001-31d8-3649-96e2-b3477a15a70d | 2026-08-21T23:46:13Z | 2026-08-21T23:46:23Z | `{"originalEventId":"fa693bd6-3060-3310-80e3-ae402d80a91e","correlationId":"83663168-fb1e-3012-99f5-b49b8bfb2857","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Réparation engagée (`RepairStarted`) | 140aa747-f73d-35e5-9c4b-007859dd85db | 2026-09-27T11:00:59Z | 2026-09-27T11:01:09Z | `{"operationId":"532c557d-1a9f-3a8d-b09f-2092c5324c23","correlationId":"833ded8e-f108-3482-9af2-0d6dc9d81f02","siteId":"SUD","locationId":"a570b572-6b24-3318-9575-eb310588592b","repairId":"a4bfe26c-829b-39f5-b018-edaccee82444"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 36a94db8-e114-323e-9020-28fa8e044b54 | 2026-09-27T11:01:29Z | 2026-09-27T11:01:39Z | `{"originalEventId":"140aa747-f73d-35e5-9c4b-007859dd85db","correlationId":"833ded8e-f108-3482-9af2-0d6dc9d81f02","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"SUD","locationId":"a570b572-6b24-3318-9575-eb310588592b","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 2474705e-f3f4-3eec-ad3c-0eaddaead492 | 2026-09-27T11:02:29Z | 2026-09-27T11:02:39Z | `{"originalEventId":"140aa747-f73d-35e5-9c4b-007859dd85db","correlationId":"833ded8e-f108-3482-9af2-0d6dc9d81f02","outcome":"APPLIED","projectedStatus":"IN_REPAIR","siteId":"SUD","locationId":"a570b572-6b24-3318-9575-eb310588592b"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"4477d7b4-edda-3439-802d-5d11b9a00624","copyId":"096205a5-ec6a-31da-971c-60a97f6a57d4","locationId":"128fab03-f193-3647-a67e-b835a30e7583","scopeId":"1d5adf88-eaa9-3257-861a-44cb401fe1dc","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`
- `{"observationId":"d49bef35-3c1a-3994-a6a0-84e43e1e1dd8","copyId":"096205a5-ec6a-31da-971c-60a97f6a57d4","locationId":"a570b572-6b24-3318-9575-eb310588592b","scopeId":"3b586894-b84e-3113-b650-26521b1804d4","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"096205a5-ec6a-31da-971c-60a97f6a57d4","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"096205a5-ec6a-31da-971c-60a97f6a57d4","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"096205a5-ec6a-31da-971c-60a97f6a57d4","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"096205a5-ec6a-31da-971c-60a97f6a57d4","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"096205a5-ec6a-31da-971c-60a97f6a57d4","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"096205a5-ec6a-31da-971c-60a97f6a57d4","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"096205a5-ec6a-31da-971c-60a97f6a57d4","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"096205a5-ec6a-31da-971c-60a97f6a57d4"},{"kind":"sourceEvent","id":"ac44843e-f8ce-3dee-a598-f880babb21d0"},{"kind":"sourceEvent","id":"8dc613ec-53eb-3a26-84e0-92fb9556f2a5"},{"kind":"sourceEvent","id":"50fca1fc-f06a-3be6-8460-aa28f22ef052"},{"kind":"sourceEvent","id":"16d07785-2eef-34d5-999b-ec79eef2b4d5"},{"kind":"sourceEvent","id":"bcc9a587-1991-3927-a018-0ff1a93af2b8"},{"kind":"sourceEvent","id":"fa693bd6-3060-3310-80e3-ae402d80a91e"},{"kind":"sourceEvent","id":"31f27001-31d8-3649-96e2-b3477a15a70d"},{"kind":"sourceEvent","id":"140aa747-f73d-35e5-9c4b-007859dd85db"},{"kind":"sourceEvent","id":"36a94db8-e114-323e-9020-28fa8e044b54"},{"kind":"sourceEvent","id":"2474705e-f3f4-3eec-ad3c-0eaddaead492"},{"kind":"observation","id":"d49bef35-3c1a-3994-a6a0-84e43e1e1dd8"},{"kind":"inventoryScope","id":"3b586894-b84e-3113-b650-26521b1804d4"},{"kind":"observation","id":"4477d7b4-edda-3439-802d-5d11b9a00624"},{"kind":"inventoryScope","id":"1d5adf88-eaa9-3257-861a-44cb401fe1dc"},{"kind":"sourceAvailability","id":"096205a5-ec6a-31da-971c-60a97f6a57d4/LOANS"},{"kind":"sourceAvailability","id":"096205a5-ec6a-31da-971c-60a97f6a57d4/RETURNS"},{"kind":"sourceAvailability","id":"096205a5-ec6a-31da-971c-60a97f6a57d4/RECEIPTS"},{"kind":"sourceAvailability","id":"096205a5-ec6a-31da-971c-60a97f6a57d4/TRANSFERS"},{"kind":"sourceAvailability","id":"096205a5-ec6a-31da-971c-60a97f6a57d4/REPAIRS"},{"kind":"sourceAvailability","id":"096205a5-ec6a-31da-971c-60a97f6a57d4/INTEGRATIONS"},{"kind":"sourceAvailability","id":"096205a5-ec6a-31da-971c-60a97f6a57d4/INVENTORY"},{"kind":"inventoryScope","id":"6cd75017-62f5-3a85-9ca4-5b89b79bc056"},{"kind":"inventoryScope","id":"0a5018ac-fd62-39c6-b080-03551f006e3d"},{"kind":"inventoryScope","id":"fc3c069e-87f6-32ef-b435-2710775e6dcd"}]`

- Session : `{"scopeId":"3b586894-b84e-3113-b650-26521b1804d4","locationId":"a570b572-6b24-3318-9575-eb310588592b","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"1d5adf88-eaa9-3257-861a-44cb401fe1dc","locationId":"128fab03-f193-3647-a67e-b835a30e7583","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"6cd75017-62f5-3a85-9ca4-5b89b79bc056","locationId":"a570b572-6b24-3318-9575-eb310588592b","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"0a5018ac-fd62-39c6-b080-03551f006e3d","locationId":"a570b572-6b24-3318-9575-eb310588592b","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"fc3c069e-87f6-32ef-b435-2710775e6dcd","locationId":"a570b572-6b24-3318-9575-eb310588592b","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 87b3357f-b8fc-3bb7-903e-ba02737021d0 — dossier d'écart

- Exemplaire : `4627d6c7-0a64-35de-966e-601a80240f83`
- Famille privée : TRANSFERT_INCOHERENT ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"84b7fe82-c801-35f9-9348-f7920a835f09","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"1e7996e3-3e30-33ad-b108-7c02dc7dfeb0","physicalStatus":"RECEIVING","physicalSiteId":"SUD","physicalLocationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"4627d6c7-0a64-35de-966e-601a80240f83","status":"RECEIVING","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","sourceEventIds":["1e7996e3-3e30-33ad-b108-7c02dc7dfeb0","f97e064f-c1ec-33e2-951f-835f52c0734f"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 1c2249d0-a34a-3866-b645-de1c975865a0 | 2026-06-02T08:45:08Z | 2026-06-02T08:45:18Z | `{"operationId":"23b4e3b5-a83f-3317-9bee-437ed2eb2baf","correlationId":"485e2f57-bd18-32fe-bd7a-8bc9e567941c","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"9610d427-ccc4-3c31-a867-dce5d8a43749"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 0551f76f-4db1-329e-847a-9a9834293e91 | 2026-06-03T08:45:08Z | 2026-06-03T08:45:18Z | `{"originalEventId":"1c2249d0-a34a-3866-b645-de1c975865a0","correlationId":"485e2f57-bd18-32fe-bd7a-8bc9e567941c","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | 8143b66c-ea94-395e-b946-729284f781de | 2026-08-01T08:45:08Z | 2026-08-01T08:45:18Z | `{"operationId":"acb1fec4-2787-3e70-9fc5-6a66b9415829","correlationId":"886b4beb-c9e6-39f4-a41f-81cb3a042af5","siteId":"NORD","locationId":null,"loanId":"06e60f64-15fc-3217-829d-4d6f980842e7"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 654ae109-44d2-332e-bd9d-fc5d3e6692ad | 2026-08-01T08:46:08Z | 2026-08-01T08:46:18Z | `{"originalEventId":"8143b66c-ea94-395e-b946-729284f781de","correlationId":"886b4beb-c9e6-39f4-a41f-81cb3a042af5","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 7b475846-fdf2-32b1-8154-f3f3715d8728 | 2026-08-21T08:45:08Z | 2026-08-21T08:45:18Z | `{"operationId":"4f6c7544-1ce9-3692-b7e2-0e4aedcf9030","correlationId":"b6f82c53-e610-3fde-b66c-728c1257e2b7","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"06e60f64-15fc-3217-829d-4d6f980842e7"}` |
| Retour reçu (`ReturnReceived`) | 2111e4f3-71f7-3082-9e47-d7a8eabd1149 | 2026-08-21T08:45:38Z | 2026-08-21T08:45:48Z | `{"operationId":"8604912c-15e2-3415-905e-5e4aeb109c32","correlationId":"7115a59d-a366-351c-a8d6-0fe4ea1612b8","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"06e60f64-15fc-3217-829d-4d6f980842e7"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 4eb05b0b-f63b-3820-ac1c-192f83cd942b | 2026-08-22T08:45:08Z | 2026-08-22T08:45:18Z | `{"originalEventId":"2111e4f3-71f7-3082-9e47-d7a8eabd1149","correlationId":"7115a59d-a366-351c-a8d6-0fe4ea1612b8","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Transfert expédié (`TransferDispatched`) | 67e0de80-f8e8-3da0-bd4a-504ee935e421 | 2026-09-26T09:49:00Z | 2026-09-26T09:49:10Z | `{"operationId":"82437982-5545-39c8-b843-064688bec054","correlationId":"b927b002-4439-3038-8794-7babdd557ab3","siteId":"NORD","locationId":null,"transferId":"0a0a20b7-f212-3db0-9241-a5afad1ef9bc","fromSiteId":"NORD","toSiteId":"SUD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 7ae58154-1cb3-37ea-9abb-5e9907c4d7f0 | 2026-09-26T09:50:00Z | 2026-09-26T09:50:10Z | `{"originalEventId":"67e0de80-f8e8-3da0-bd4a-504ee935e421","correlationId":"b927b002-4439-3038-8794-7babdd557ab3","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"NORD","locationId":null}` |
| Transfert reçu (`TransferReceived`) | 1e7996e3-3e30-33ad-b108-7c02dc7dfeb0 | 2026-09-27T09:49:00Z | 2026-09-27T09:49:10Z | `{"operationId":"2ddba366-2b16-3ed2-9ede-31a1654a2d6b","correlationId":"15a43c90-066d-3464-b5e0-8632499ba03d","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","transferId":"0a0a20b7-f212-3db0-9241-a5afad1ef9bc","fromSiteId":"NORD","toSiteId":"SUD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | fae404ca-fced-3f9c-91b8-1cd77b208919 | 2026-09-27T09:49:30Z | 2026-09-27T09:49:40Z | `{"originalEventId":"1e7996e3-3e30-33ad-b108-7c02dc7dfeb0","correlationId":"15a43c90-066d-3464-b5e0-8632499ba03d","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | f97e064f-c1ec-33e2-951f-835f52c0734f | 2026-09-27T09:50:30Z | 2026-09-27T09:50:40Z | `{"originalEventId":"1e7996e3-3e30-33ad-b108-7c02dc7dfeb0","correlationId":"15a43c90-066d-3464-b5e0-8632499ba03d","outcome":"APPLIED","projectedStatus":"RECEIVING","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"f52cd908-53da-32e7-9642-c38d431e88d7","copyId":"4627d6c7-0a64-35de-966e-601a80240f83","locationId":"128fab03-f193-3647-a67e-b835a30e7583","scopeId":"e8fb2837-a3a8-3d1f-8fe7-ba3cb9ebcc23","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`
- `{"observationId":"dd2063fa-0c16-3e16-9d81-fe21bdd0aeb5","copyId":"4627d6c7-0a64-35de-966e-601a80240f83","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","scopeId":"5c8c723e-bac8-3c2c-82ca-2e893103fefe","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"4627d6c7-0a64-35de-966e-601a80240f83","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4627d6c7-0a64-35de-966e-601a80240f83","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4627d6c7-0a64-35de-966e-601a80240f83","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4627d6c7-0a64-35de-966e-601a80240f83","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4627d6c7-0a64-35de-966e-601a80240f83","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4627d6c7-0a64-35de-966e-601a80240f83","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4627d6c7-0a64-35de-966e-601a80240f83","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"4627d6c7-0a64-35de-966e-601a80240f83"},{"kind":"sourceEvent","id":"1c2249d0-a34a-3866-b645-de1c975865a0"},{"kind":"sourceEvent","id":"0551f76f-4db1-329e-847a-9a9834293e91"},{"kind":"sourceEvent","id":"8143b66c-ea94-395e-b946-729284f781de"},{"kind":"sourceEvent","id":"654ae109-44d2-332e-bd9d-fc5d3e6692ad"},{"kind":"sourceEvent","id":"7b475846-fdf2-32b1-8154-f3f3715d8728"},{"kind":"sourceEvent","id":"2111e4f3-71f7-3082-9e47-d7a8eabd1149"},{"kind":"sourceEvent","id":"4eb05b0b-f63b-3820-ac1c-192f83cd942b"},{"kind":"sourceEvent","id":"67e0de80-f8e8-3da0-bd4a-504ee935e421"},{"kind":"sourceEvent","id":"7ae58154-1cb3-37ea-9abb-5e9907c4d7f0"},{"kind":"sourceEvent","id":"1e7996e3-3e30-33ad-b108-7c02dc7dfeb0"},{"kind":"sourceEvent","id":"fae404ca-fced-3f9c-91b8-1cd77b208919"},{"kind":"sourceEvent","id":"f97e064f-c1ec-33e2-951f-835f52c0734f"},{"kind":"observation","id":"dd2063fa-0c16-3e16-9d81-fe21bdd0aeb5"},{"kind":"inventoryScope","id":"5c8c723e-bac8-3c2c-82ca-2e893103fefe"},{"kind":"observation","id":"f52cd908-53da-32e7-9642-c38d431e88d7"},{"kind":"inventoryScope","id":"e8fb2837-a3a8-3d1f-8fe7-ba3cb9ebcc23"},{"kind":"sourceAvailability","id":"4627d6c7-0a64-35de-966e-601a80240f83/LOANS"},{"kind":"sourceAvailability","id":"4627d6c7-0a64-35de-966e-601a80240f83/RETURNS"},{"kind":"sourceAvailability","id":"4627d6c7-0a64-35de-966e-601a80240f83/RECEIPTS"},{"kind":"sourceAvailability","id":"4627d6c7-0a64-35de-966e-601a80240f83/TRANSFERS"},{"kind":"sourceAvailability","id":"4627d6c7-0a64-35de-966e-601a80240f83/REPAIRS"},{"kind":"sourceAvailability","id":"4627d6c7-0a64-35de-966e-601a80240f83/INTEGRATIONS"},{"kind":"sourceAvailability","id":"4627d6c7-0a64-35de-966e-601a80240f83/INVENTORY"},{"kind":"inventoryScope","id":"17056b8d-135e-37ef-9d61-d4c8b0c46e1c"},{"kind":"inventoryScope","id":"feed50c2-3619-3a09-ad10-33d0609a6132"}]`

- Session : `{"scopeId":"5c8c723e-bac8-3c2c-82ca-2e893103fefe","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"e8fb2837-a3a8-3d1f-8fe7-ba3cb9ebcc23","locationId":"128fab03-f193-3647-a67e-b835a30e7583","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"17056b8d-135e-37ef-9d61-d4c8b0c46e1c","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"feed50c2-3619-3a09-ad10-33d0609a6132","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 88d79242-4d1d-30de-9dd7-b5d08b49cd25 — dossier d'écart

- Exemplaire : `2428e425-84c7-38bf-a988-5a064494aadd`
- Famille privée : PRET_NON_PROJETE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"cfbcc8b9-2244-3046-8dcf-84f829265686","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"ed035e88-84ea-396a-87e3-8d0ab424a7c2","physicalStatus":"ON_LOAN","physicalSiteId":"SUD","physicalLocationId":null,"projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"2428e425-84c7-38bf-a988-5a064494aadd","status":"ON_LOAN","siteId":"SUD","locationId":null,"sourceEventIds":["ed035e88-84ea-396a-87e3-8d0ab424a7c2","f03b686e-54db-3cea-b4f1-7c451731a049"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 862225e7-d6bb-3c03-994a-4a5ff775fa56 | 2026-06-01T18:56:54Z | 2026-06-01T18:57:04Z | `{"operationId":"96ec6f6a-a50a-3074-b984-b5a193398a00","correlationId":"88697f17-88b5-3103-9ed1-8f627f194d1a","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"f21e07ea-2919-3e78-af5a-f9d994d313da"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 4150b5f1-454d-328b-ba20-a1ffac040e1d | 2026-06-02T18:56:54Z | 2026-06-02T18:57:04Z | `{"originalEventId":"862225e7-d6bb-3c03-994a-4a5ff775fa56","correlationId":"88697f17-88b5-3103-9ed1-8f627f194d1a","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 0235fe23-50b7-3e5b-85a3-11105aaddd00 | 2026-07-31T18:56:54Z | 2026-07-31T18:57:04Z | `{"operationId":"cbb2216d-5110-3f9d-9225-5fa128d160b4","correlationId":"4781676e-f0f5-39f5-b3eb-c3b1cf598f24","siteId":"SUD","locationId":null,"loanId":"c75ce79d-350c-34d2-89cd-183ebfbf8470"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 9b10d255-4cb5-3880-b25f-eb6f7a219d3d | 2026-07-31T18:57:54Z | 2026-07-31T18:58:04Z | `{"originalEventId":"0235fe23-50b7-3e5b-85a3-11105aaddd00","correlationId":"4781676e-f0f5-39f5-b3eb-c3b1cf598f24","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 3b09356b-7e90-3718-8b43-3841a4fdea6d | 2026-08-20T18:56:54Z | 2026-08-20T18:57:04Z | `{"operationId":"c4cd285c-876c-333a-a527-c582de072baa","correlationId":"852da419-d900-3308-8a91-fff130afa75e","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"c75ce79d-350c-34d2-89cd-183ebfbf8470"}` |
| Retour reçu (`ReturnReceived`) | 60c1ede1-39a0-3bd0-99d0-b8f7934cb880 | 2026-08-20T18:57:24Z | 2026-08-20T18:57:34Z | `{"operationId":"9fd2965e-383c-3b2c-960a-40c73d56b04f","correlationId":"1c9ce4df-f9b4-3ca3-81d0-6a914abbd3f4","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"c75ce79d-350c-34d2-89cd-183ebfbf8470"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 1cd3a92d-4467-3325-ad2f-1019b136e054 | 2026-08-21T18:56:54Z | 2026-08-21T18:57:04Z | `{"originalEventId":"60c1ede1-39a0-3bd0-99d0-b8f7934cb880","correlationId":"1c9ce4df-f9b4-3ca3-81d0-6a914abbd3f4","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | ed035e88-84ea-396a-87e3-8d0ab424a7c2 | 2026-09-27T10:13:08Z | 2026-09-27T10:13:18Z | `{"operationId":"df2be039-950d-3866-ac85-c4876f1bcde4","correlationId":"78f6eca0-dd8c-3130-a430-c5effd4f646e","siteId":"SUD","locationId":null,"loanId":"c4440667-f93c-3f51-b5fc-7c0befe08836"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | ddf72d8d-69ae-36bf-a4de-59c6867f353c | 2026-09-27T10:13:38Z | 2026-09-27T10:13:48Z | `{"originalEventId":"ed035e88-84ea-396a-87e3-8d0ab424a7c2","correlationId":"78f6eca0-dd8c-3130-a430-c5effd4f646e","outcome":"FAILED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | f03b686e-54db-3cea-b4f1-7c451731a049 | 2026-09-27T10:14:38Z | 2026-09-27T10:14:48Z | `{"originalEventId":"ed035e88-84ea-396a-87e3-8d0ab424a7c2","correlationId":"78f6eca0-dd8c-3130-a430-c5effd4f646e","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"03c39377-d081-3a9e-aee0-29326810ddc9","copyId":"2428e425-84c7-38bf-a988-5a064494aadd","locationId":"128fab03-f193-3647-a67e-b835a30e7583","scopeId":"bdcc866c-37e9-3b4d-9e55-96f12daafe5a","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"2428e425-84c7-38bf-a988-5a064494aadd","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2428e425-84c7-38bf-a988-5a064494aadd","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2428e425-84c7-38bf-a988-5a064494aadd","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2428e425-84c7-38bf-a988-5a064494aadd","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2428e425-84c7-38bf-a988-5a064494aadd","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2428e425-84c7-38bf-a988-5a064494aadd","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2428e425-84c7-38bf-a988-5a064494aadd","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"2428e425-84c7-38bf-a988-5a064494aadd"},{"kind":"sourceEvent","id":"862225e7-d6bb-3c03-994a-4a5ff775fa56"},{"kind":"sourceEvent","id":"4150b5f1-454d-328b-ba20-a1ffac040e1d"},{"kind":"sourceEvent","id":"0235fe23-50b7-3e5b-85a3-11105aaddd00"},{"kind":"sourceEvent","id":"9b10d255-4cb5-3880-b25f-eb6f7a219d3d"},{"kind":"sourceEvent","id":"3b09356b-7e90-3718-8b43-3841a4fdea6d"},{"kind":"sourceEvent","id":"60c1ede1-39a0-3bd0-99d0-b8f7934cb880"},{"kind":"sourceEvent","id":"1cd3a92d-4467-3325-ad2f-1019b136e054"},{"kind":"sourceEvent","id":"ed035e88-84ea-396a-87e3-8d0ab424a7c2"},{"kind":"sourceEvent","id":"ddf72d8d-69ae-36bf-a4de-59c6867f353c"},{"kind":"sourceEvent","id":"f03b686e-54db-3cea-b4f1-7c451731a049"},{"kind":"observation","id":"03c39377-d081-3a9e-aee0-29326810ddc9"},{"kind":"inventoryScope","id":"bdcc866c-37e9-3b4d-9e55-96f12daafe5a"},{"kind":"sourceAvailability","id":"2428e425-84c7-38bf-a988-5a064494aadd/LOANS"},{"kind":"sourceAvailability","id":"2428e425-84c7-38bf-a988-5a064494aadd/RETURNS"},{"kind":"sourceAvailability","id":"2428e425-84c7-38bf-a988-5a064494aadd/RECEIPTS"},{"kind":"sourceAvailability","id":"2428e425-84c7-38bf-a988-5a064494aadd/TRANSFERS"},{"kind":"sourceAvailability","id":"2428e425-84c7-38bf-a988-5a064494aadd/REPAIRS"},{"kind":"sourceAvailability","id":"2428e425-84c7-38bf-a988-5a064494aadd/INTEGRATIONS"},{"kind":"sourceAvailability","id":"2428e425-84c7-38bf-a988-5a064494aadd/INVENTORY"}]`

- Session : `{"scopeId":"bdcc866c-37e9-3b4d-9e55-96f12daafe5a","locationId":"128fab03-f193-3647-a67e-b835a30e7583","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

## Cas 8b905aa9-2ec8-306e-ae6e-55b7396e1ec1 — dossier d'écart

- Exemplaire : `36441826-8158-3759-9839-85111fa8c3bf`
- Famille privée : PRET_NON_PROJETE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"345b5de1-604b-3d7c-b116-402029e43cab","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"b0822ed8-e070-3b21-901f-96452a717252","physicalStatus":"ON_LOAN","physicalSiteId":"SUD","physicalLocationId":null,"projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["LOAN_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"36441826-8158-3759-9839-85111fa8c3bf","status":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","sourceEventIds":["2565ba43-c128-3778-a635-6cc694e5ff60","35421a66-c260-367d-82d1-54ca2012e9dd"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | ef341d8b-41b3-38a7-95dd-e9b3e31ba20e | 2026-06-01T22:48:56Z | 2026-06-01T22:49:06Z | `{"operationId":"e0aa96c6-fa0f-3b24-8260-8ded5bc70e32","correlationId":"39a96295-586b-39a6-9ba4-7f50f1df18aa","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"37f7825a-8410-3ea7-8bb5-c843df7b66da"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 1e79a025-d083-3ae3-844e-ca2346fa87ed | 2026-06-02T22:48:56Z | 2026-06-02T22:49:06Z | `{"originalEventId":"ef341d8b-41b3-38a7-95dd-e9b3e31ba20e","correlationId":"39a96295-586b-39a6-9ba4-7f50f1df18aa","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | 70cc351c-6406-313d-b671-99d38a74f874 | 2026-07-31T22:48:56Z | 2026-07-31T22:49:06Z | `{"operationId":"58d48af1-3ee0-379b-9351-ceecf26aed14","correlationId":"47a96424-dfda-3486-b777-7d189cc4201e","siteId":"NORD","locationId":null,"loanId":"64328278-03ae-3184-8990-bef8d4b8e7af"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 21ba44b5-7bbd-38c6-89e4-40d092045b5b | 2026-07-31T22:49:56Z | 2026-07-31T22:50:06Z | `{"originalEventId":"70cc351c-6406-313d-b671-99d38a74f874","correlationId":"47a96424-dfda-3486-b777-7d189cc4201e","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | f9c8f124-2b62-36d2-af52-8d00ae9a085e | 2026-08-20T22:48:56Z | 2026-08-20T22:49:06Z | `{"operationId":"bc3f1832-178d-3bad-853f-40dc2abba4a2","correlationId":"2297e5b3-c6b7-3ea4-b1b5-1d6a0fe366fc","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"64328278-03ae-3184-8990-bef8d4b8e7af"}` |
| Retour reçu (`ReturnReceived`) | de1dd2c7-b7fc-3b48-975a-b08d11e73fe8 | 2026-08-20T22:49:26Z | 2026-08-20T22:49:36Z | `{"operationId":"2068c294-b906-388d-945b-e08bf38e62f7","correlationId":"b493942e-eb22-39a7-9a3c-9f18de1fd28e","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"64328278-03ae-3184-8990-bef8d4b8e7af"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 219e2cab-e989-3270-936b-20bbdb60be1a | 2026-08-21T22:48:56Z | 2026-08-21T22:49:06Z | `{"originalEventId":"de1dd2c7-b7fc-3b48-975a-b08d11e73fe8","correlationId":"b493942e-eb22-39a7-9a3c-9f18de1fd28e","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Transfert expédié (`TransferDispatched`) | ce83801a-3728-3369-b282-5ab90b7c8d2a | 2026-08-30T22:48:56Z | 2026-08-30T22:49:06Z | `{"operationId":"9e060ec2-d7cf-3c8b-939c-b73f2b60e935","correlationId":"5fe8faa7-b5b5-3058-a3ad-23682c53c4d1","siteId":"NORD","locationId":null,"transferId":"4d42c0c7-879d-30c5-a688-12f3f05849ae","fromSiteId":"NORD","toSiteId":"SUD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 84324c19-76ba-337f-84f1-3bb455e2cc8e | 2026-08-30T22:49:56Z | 2026-08-30T22:50:06Z | `{"originalEventId":"ce83801a-3728-3369-b282-5ab90b7c8d2a","correlationId":"5fe8faa7-b5b5-3058-a3ad-23682c53c4d1","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"NORD","locationId":null}` |
| Transfert reçu (`TransferReceived`) | 2565ba43-c128-3778-a635-6cc694e5ff60 | 2026-08-31T22:48:56Z | 2026-08-31T22:49:06Z | `{"operationId":"91030000-f607-3554-bd53-7d5adda45d07","correlationId":"7367cdca-6540-3086-8d66-bc428c182616","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","transferId":"4d42c0c7-879d-30c5-a688-12f3f05849ae","fromSiteId":"NORD","toSiteId":"SUD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 35421a66-c260-367d-82d1-54ca2012e9dd | 2026-09-01T22:48:56Z | 2026-09-01T22:49:06Z | `{"originalEventId":"2565ba43-c128-3778-a635-6cc694e5ff60","correlationId":"7367cdca-6540-3086-8d66-bc428c182616","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | b0822ed8-e070-3b21-901f-96452a717252 | 2026-09-27T11:29:24Z | 2026-09-27T11:29:34Z | `{"operationId":"2100c70e-1885-3c71-918c-c8937b109ce4","correlationId":"235e6bfa-dbb4-363a-8e1c-034f8d497365","siteId":"SUD","locationId":null,"loanId":"cfecd4b5-aec5-3bc3-aedd-50728178593b"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | b2aa01ba-1c52-3d6a-b16c-91e0e554d64a | 2026-09-27T11:29:54Z | 2026-09-27T11:30:04Z | `{"originalEventId":"b0822ed8-e070-3b21-901f-96452a717252","correlationId":"235e6bfa-dbb4-363a-8e1c-034f8d497365","outcome":"FAILED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"36441826-8158-3759-9839-85111fa8c3bf","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"36441826-8158-3759-9839-85111fa8c3bf","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"36441826-8158-3759-9839-85111fa8c3bf","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"36441826-8158-3759-9839-85111fa8c3bf","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"36441826-8158-3759-9839-85111fa8c3bf","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"36441826-8158-3759-9839-85111fa8c3bf","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"36441826-8158-3759-9839-85111fa8c3bf","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"36441826-8158-3759-9839-85111fa8c3bf"},{"kind":"sourceEvent","id":"ef341d8b-41b3-38a7-95dd-e9b3e31ba20e"},{"kind":"sourceEvent","id":"1e79a025-d083-3ae3-844e-ca2346fa87ed"},{"kind":"sourceEvent","id":"70cc351c-6406-313d-b671-99d38a74f874"},{"kind":"sourceEvent","id":"21ba44b5-7bbd-38c6-89e4-40d092045b5b"},{"kind":"sourceEvent","id":"f9c8f124-2b62-36d2-af52-8d00ae9a085e"},{"kind":"sourceEvent","id":"de1dd2c7-b7fc-3b48-975a-b08d11e73fe8"},{"kind":"sourceEvent","id":"219e2cab-e989-3270-936b-20bbdb60be1a"},{"kind":"sourceEvent","id":"ce83801a-3728-3369-b282-5ab90b7c8d2a"},{"kind":"sourceEvent","id":"84324c19-76ba-337f-84f1-3bb455e2cc8e"},{"kind":"sourceEvent","id":"2565ba43-c128-3778-a635-6cc694e5ff60"},{"kind":"sourceEvent","id":"35421a66-c260-367d-82d1-54ca2012e9dd"},{"kind":"sourceEvent","id":"b0822ed8-e070-3b21-901f-96452a717252"},{"kind":"sourceEvent","id":"b2aa01ba-1c52-3d6a-b16c-91e0e554d64a"},{"kind":"sourceAvailability","id":"36441826-8158-3759-9839-85111fa8c3bf/LOANS"},{"kind":"sourceAvailability","id":"36441826-8158-3759-9839-85111fa8c3bf/RETURNS"},{"kind":"sourceAvailability","id":"36441826-8158-3759-9839-85111fa8c3bf/RECEIPTS"},{"kind":"sourceAvailability","id":"36441826-8158-3759-9839-85111fa8c3bf/TRANSFERS"},{"kind":"sourceAvailability","id":"36441826-8158-3759-9839-85111fa8c3bf/REPAIRS"},{"kind":"sourceAvailability","id":"36441826-8158-3759-9839-85111fa8c3bf/INTEGRATIONS"},{"kind":"sourceAvailability","id":"36441826-8158-3759-9839-85111fa8c3bf/INVENTORY"},{"kind":"inventoryScope","id":"7f91fb45-70cc-3811-b222-43a5370aa433"},{"kind":"inventoryScope","id":"202855ec-9065-3be6-a0c2-07ce228ecf21"},{"kind":"inventoryScope","id":"1b4f280a-3145-3607-a009-dca44e9d4181"}]`

- Session : `{"scopeId":"7f91fb45-70cc-3811-b222-43a5370aa433","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"202855ec-9065-3be6-a0c2-07ce228ecf21","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"1b4f280a-3145-3607-a009-dca44e9d4181","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 927651b9-dee4-34e6-a455-d9943b5e9f97 — dossier d'écart

- Exemplaire : `04a0fc2b-0dc9-3721-a9c8-7954d59cf5d4`
- Famille privée : RETOUR_NON_PROJETE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"82d1bf4a-e103-317a-ba91-c0bafd0b92d6","kind":"STATUS_MISMATCH","expectedValue":"ON_LOAN","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"96be86cf-c4e3-3862-a9e5-8fd33c469e8c","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["RETURN_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"04a0fc2b-0dc9-3721-a9c8-7954d59cf5d4","status":"ON_LOAN","siteId":"CENTRE","locationId":null,"sourceEventIds":["3b9a224f-1155-33b1-950a-cc163d481794","7e42ef6d-b6d5-340c-84dc-97d08c2600b2"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | e8bec694-a302-3a5f-a152-cd3aa4af1edb | 2026-06-02T11:57:15Z | 2026-06-02T11:57:25Z | `{"operationId":"7ce72d2f-c3e0-3ea1-a026-a7c7da6343ab","correlationId":"29e34ba5-075f-3bef-9403-a6a17daaf4e1","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"d8644af2-7e1c-3bd1-bcaf-23c7a488cdd3"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | eea3a63b-6620-33d7-875a-e16c93ae5cfb | 2026-06-03T11:57:15Z | 2026-06-03T11:57:25Z | `{"originalEventId":"e8bec694-a302-3a5f-a152-cd3aa4af1edb","correlationId":"29e34ba5-075f-3bef-9403-a6a17daaf4e1","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 150c7b94-53f6-3130-adb6-1dc11c622db1 | 2026-08-01T11:57:15Z | 2026-08-01T11:57:25Z | `{"operationId":"4c06f4e0-7911-3ed1-b91c-bfcb4900f226","correlationId":"f451b196-2567-3572-af50-a7cc84340d49","siteId":"CENTRE","locationId":null,"loanId":"e5f94d5e-a31c-35e5-99f2-11f2db809811"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | e0719041-9b3b-3e8b-981b-9e739c0b3b5b | 2026-08-01T11:58:15Z | 2026-08-01T11:58:25Z | `{"originalEventId":"150c7b94-53f6-3130-adb6-1dc11c622db1","correlationId":"f451b196-2567-3572-af50-a7cc84340d49","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 6afef6a9-4763-3a0a-91f0-32b204546a67 | 2026-08-21T11:57:15Z | 2026-08-21T11:57:25Z | `{"operationId":"457f3982-8815-3193-b1d3-bb797fa2606e","correlationId":"280dda7a-f4e7-3078-a2d4-36fc9891609f","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"e5f94d5e-a31c-35e5-99f2-11f2db809811"}` |
| Retour reçu (`ReturnReceived`) | 6b9dfa3a-9ae8-3289-8861-73a5062a83b3 | 2026-08-21T11:57:45Z | 2026-08-21T11:57:55Z | `{"operationId":"fa9072d2-abed-39d2-911b-89f6cdabf0fd","correlationId":"c44fbad1-1c53-379f-a0ad-c6613a94cb27","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"e5f94d5e-a31c-35e5-99f2-11f2db809811"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | ee6efad0-ba92-3025-b28d-021bf498814c | 2026-08-22T11:57:15Z | 2026-08-22T11:57:25Z | `{"originalEventId":"6b9dfa3a-9ae8-3289-8861-73a5062a83b3","correlationId":"c44fbad1-1c53-379f-a0ad-c6613a94cb27","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 3b9a224f-1155-33b1-950a-cc163d481794 | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"86d9f2a5-cdda-3eba-b70e-0cbb6aa0a1fe","correlationId":"2da08940-428b-3c2c-bad9-b72f0c8116ec","siteId":"CENTRE","locationId":null,"loanId":"3f28310a-8b35-3095-8ff7-e8a7dbdebe1b"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 7e42ef6d-b6d5-340c-84dc-97d08c2600b2 | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"3b9a224f-1155-33b1-950a-cc163d481794","correlationId":"2da08940-428b-3c2c-bad9-b72f0c8116ec","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 836ee6a3-e0ff-3dac-bc33-87b63f9150c8 | 2026-09-27T09:32:52Z | 2026-09-27T09:33:02Z | `{"operationId":"d011c1ec-ca4f-39d8-bb81-a559509de5f2","correlationId":"2578f98f-0995-377f-9cab-13c9a5142778","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"3f28310a-8b35-3095-8ff7-e8a7dbdebe1b"}` |
| Retour reçu (`ReturnReceived`) | 96be86cf-c4e3-3862-a9e5-8fd33c469e8c | 2026-09-27T09:33:22Z | 2026-09-27T09:33:32Z | `{"operationId":"76e3ed7a-70f7-3842-ba82-995f3f54d9ec","correlationId":"fed159a7-f4a7-3a9f-ab01-a5fcef912340","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"3f28310a-8b35-3095-8ff7-e8a7dbdebe1b"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 1c13f341-32a7-32c5-86fd-8fcca7f6d6be | 2026-09-27T09:33:52Z | 2026-09-27T09:34:02Z | `{"originalEventId":"96be86cf-c4e3-3862-a9e5-8fd33c469e8c","correlationId":"fed159a7-f4a7-3a9f-ab01-a5fcef912340","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"41d364df-2e03-39a4-9c12-efae877267c2","copyId":"04a0fc2b-0dc9-3721-a9c8-7954d59cf5d4","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","scopeId":"e9f2a8e9-8d40-3161-95a0-e7e0360ebb4e","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"04a0fc2b-0dc9-3721-a9c8-7954d59cf5d4","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"04a0fc2b-0dc9-3721-a9c8-7954d59cf5d4","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"04a0fc2b-0dc9-3721-a9c8-7954d59cf5d4","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"04a0fc2b-0dc9-3721-a9c8-7954d59cf5d4","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"04a0fc2b-0dc9-3721-a9c8-7954d59cf5d4","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"04a0fc2b-0dc9-3721-a9c8-7954d59cf5d4","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"04a0fc2b-0dc9-3721-a9c8-7954d59cf5d4","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"04a0fc2b-0dc9-3721-a9c8-7954d59cf5d4"},{"kind":"sourceEvent","id":"e8bec694-a302-3a5f-a152-cd3aa4af1edb"},{"kind":"sourceEvent","id":"eea3a63b-6620-33d7-875a-e16c93ae5cfb"},{"kind":"sourceEvent","id":"150c7b94-53f6-3130-adb6-1dc11c622db1"},{"kind":"sourceEvent","id":"e0719041-9b3b-3e8b-981b-9e739c0b3b5b"},{"kind":"sourceEvent","id":"6afef6a9-4763-3a0a-91f0-32b204546a67"},{"kind":"sourceEvent","id":"6b9dfa3a-9ae8-3289-8861-73a5062a83b3"},{"kind":"sourceEvent","id":"ee6efad0-ba92-3025-b28d-021bf498814c"},{"kind":"sourceEvent","id":"3b9a224f-1155-33b1-950a-cc163d481794"},{"kind":"sourceEvent","id":"7e42ef6d-b6d5-340c-84dc-97d08c2600b2"},{"kind":"sourceEvent","id":"836ee6a3-e0ff-3dac-bc33-87b63f9150c8"},{"kind":"sourceEvent","id":"96be86cf-c4e3-3862-a9e5-8fd33c469e8c"},{"kind":"sourceEvent","id":"1c13f341-32a7-32c5-86fd-8fcca7f6d6be"},{"kind":"observation","id":"41d364df-2e03-39a4-9c12-efae877267c2"},{"kind":"inventoryScope","id":"e9f2a8e9-8d40-3161-95a0-e7e0360ebb4e"},{"kind":"sourceAvailability","id":"04a0fc2b-0dc9-3721-a9c8-7954d59cf5d4/LOANS"},{"kind":"sourceAvailability","id":"04a0fc2b-0dc9-3721-a9c8-7954d59cf5d4/RETURNS"},{"kind":"sourceAvailability","id":"04a0fc2b-0dc9-3721-a9c8-7954d59cf5d4/RECEIPTS"},{"kind":"sourceAvailability","id":"04a0fc2b-0dc9-3721-a9c8-7954d59cf5d4/TRANSFERS"},{"kind":"sourceAvailability","id":"04a0fc2b-0dc9-3721-a9c8-7954d59cf5d4/REPAIRS"},{"kind":"sourceAvailability","id":"04a0fc2b-0dc9-3721-a9c8-7954d59cf5d4/INTEGRATIONS"},{"kind":"sourceAvailability","id":"04a0fc2b-0dc9-3721-a9c8-7954d59cf5d4/INVENTORY"}]`

- Session : `{"scopeId":"e9f2a8e9-8d40-3161-95a0-e7e0360ebb4e","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 92a48d80-b680-366d-a0a6-4aae4f7c85fb — dossier d'écart

- Exemplaire : `6c97cf88-997a-344a-84c0-72a7ef09eef6`
- Famille privée : RECEPTION_NON_INTEGREE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"99d2a77d-2c3c-3c59-ab7f-ed72e61b7c0f","kind":"UNEXPECTED_PRESENCE","expectedValue":"NOT_REGISTERED","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"06a554c3-048b-3f88-94e3-6279742d3e36","copyId":"6c97cf88-997a-344a-84c0-72a7ef09eef6","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T08:46:35Z","recordedAt":"2026-09-27T08:46:45Z","payload":{"originalEventId":"311009b0-9f79-3aed-8e95-6a5a677448d8","correlationId":"ba82f816-d7fd-3b3d-85a7-f95f32e28d28","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"311009b0-9f79-3aed-8e95-6a5a677448d8","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"125bed65-7385-3595-a653-902580e9539f","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État du stock informatique : `{"copyId":"6c97cf88-997a-344a-84c0-72a7ef09eef6","status":"NOT_REGISTERED","siteId":"CENTRE","locationId":null,"sourceEventIds":[]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 311009b0-9f79-3aed-8e95-6a5a677448d8 | 2026-09-27T08:46:05Z | 2026-09-27T08:46:15Z | `{"operationId":"5db59b50-e31f-3c76-97ee-08b6819337ff","correlationId":"ba82f816-d7fd-3b3d-85a7-f95f32e28d28","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"4bb3c7c2-e345-327c-9f12-36d2a367ded1"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"9742f094-4395-39bf-80d1-240579772609","copyId":"6c97cf88-997a-344a-84c0-72a7ef09eef6","locationId":"125bed65-7385-3595-a653-902580e9539f","scopeId":"398b2163-3ca7-39c8-b2ee-d941c5b71032","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"6c97cf88-997a-344a-84c0-72a7ef09eef6","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6c97cf88-997a-344a-84c0-72a7ef09eef6","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6c97cf88-997a-344a-84c0-72a7ef09eef6","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6c97cf88-997a-344a-84c0-72a7ef09eef6","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6c97cf88-997a-344a-84c0-72a7ef09eef6","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6c97cf88-997a-344a-84c0-72a7ef09eef6","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"6c97cf88-997a-344a-84c0-72a7ef09eef6","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"6c97cf88-997a-344a-84c0-72a7ef09eef6"},{"kind":"sourceEvent","id":"311009b0-9f79-3aed-8e95-6a5a677448d8"},{"kind":"observation","id":"9742f094-4395-39bf-80d1-240579772609"},{"kind":"inventoryScope","id":"398b2163-3ca7-39c8-b2ee-d941c5b71032"},{"kind":"sourceAvailability","id":"6c97cf88-997a-344a-84c0-72a7ef09eef6/LOANS"},{"kind":"sourceAvailability","id":"6c97cf88-997a-344a-84c0-72a7ef09eef6/RETURNS"},{"kind":"sourceAvailability","id":"6c97cf88-997a-344a-84c0-72a7ef09eef6/RECEIPTS"},{"kind":"sourceAvailability","id":"6c97cf88-997a-344a-84c0-72a7ef09eef6/TRANSFERS"},{"kind":"sourceAvailability","id":"6c97cf88-997a-344a-84c0-72a7ef09eef6/REPAIRS"},{"kind":"sourceAvailability","id":"6c97cf88-997a-344a-84c0-72a7ef09eef6/INTEGRATIONS"},{"kind":"sourceAvailability","id":"6c97cf88-997a-344a-84c0-72a7ef09eef6/INVENTORY"}]`

- Session : `{"scopeId":"398b2163-3ca7-39c8-b2ee-d941c5b71032","locationId":"125bed65-7385-3595-a653-902580e9539f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 98c52f0b-a6d3-3f69-96a7-f4a6a61b0d8a — dossier d'écart

- Exemplaire : `0cb61ce6-7ec8-3f85-9b84-aa6bb023f07d`
- Famille privée : REPARATION_NON_PROJETEE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"0bd1fec3-c0db-38cd-95e5-f59142eb59d2","kind":"STATUS_MISMATCH","expectedValue":"ON_SHELF","observedValue":"IN_REPAIR"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"bc80a383-6597-3d62-a79d-75d8ca08dc1f","copyId":"0cb61ce6-7ec8-3f85-9b84-aa6bb023f07d","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T06:18:22Z","recordedAt":"2026-09-27T06:18:32Z","payload":{"originalEventId":"145f5adf-238a-346d-a72c-ced16ba7c2d9","correlationId":"a7d73297-1909-3dc6-9d3a-c7b23cff3339","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"SUD","locationId":"a570b572-6b24-3318-9575-eb310588592b","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"145f5adf-238a-346d-a72c-ced16ba7c2d9","physicalStatus":"IN_REPAIR","physicalSiteId":"SUD","physicalLocationId":"a570b572-6b24-3318-9575-eb310588592b","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État du stock informatique : `{"copyId":"0cb61ce6-7ec8-3f85-9b84-aa6bb023f07d","status":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","sourceEventIds":["69b99ab0-8451-3120-be81-76b9ae0d60c9","d81ba93d-cf5c-344b-8b7f-ef632b01b548"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 0b1dce6c-d552-3862-a4c2-c2c8a354ce0f | 2026-06-01T18:07:08Z | 2026-06-01T18:07:18Z | `{"operationId":"749a3c6c-c825-3546-b94b-3c334672c665","correlationId":"9b5e6edd-6dcd-3253-85e5-9e7be2cbf404","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"79abfb7c-dad5-30cc-b378-a9305deb6d3d"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 618d9196-ece7-3aaf-b112-61cfcd0c567e | 2026-06-02T18:07:08Z | 2026-06-02T18:07:18Z | `{"originalEventId":"0b1dce6c-d552-3862-a4c2-c2c8a354ce0f","correlationId":"9b5e6edd-6dcd-3253-85e5-9e7be2cbf404","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 2e130cbe-43d9-39b2-9e12-7197a0490b59 | 2026-07-31T18:07:08Z | 2026-07-31T18:07:18Z | `{"operationId":"33165b0f-ebc2-3fd7-a4d8-f90b1aaedaf2","correlationId":"cfa12a72-2f25-3aaf-80a6-09c94fe06b96","siteId":"SUD","locationId":null,"loanId":"7650d5e8-ea16-33f5-81b0-ac5bb619228a"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | abdc16ae-c553-3003-8ee4-790afb65429a | 2026-07-31T18:08:08Z | 2026-07-31T18:08:18Z | `{"originalEventId":"2e130cbe-43d9-39b2-9e12-7197a0490b59","correlationId":"cfa12a72-2f25-3aaf-80a6-09c94fe06b96","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 9036ea04-ae06-3451-b547-6850cdc42203 | 2026-08-20T18:07:08Z | 2026-08-20T18:07:18Z | `{"operationId":"5fa83710-1565-334d-86e6-2b7427062227","correlationId":"4873dc4e-8367-3bda-840f-fa40e8c0ad4a","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"7650d5e8-ea16-33f5-81b0-ac5bb619228a"}` |
| Retour reçu (`ReturnReceived`) | 69b99ab0-8451-3120-be81-76b9ae0d60c9 | 2026-08-20T18:07:38Z | 2026-08-20T18:07:48Z | `{"operationId":"e1830b54-fa30-3e2d-8c57-8afe46d58de8","correlationId":"0d2c6c09-d25a-39cd-9c50-df02c5ddd4ad","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"7650d5e8-ea16-33f5-81b0-ac5bb619228a"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | d81ba93d-cf5c-344b-8b7f-ef632b01b548 | 2026-08-21T18:07:08Z | 2026-08-21T18:07:18Z | `{"originalEventId":"69b99ab0-8451-3120-be81-76b9ae0d60c9","correlationId":"0d2c6c09-d25a-39cd-9c50-df02c5ddd4ad","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Réparation engagée (`RepairStarted`) | 145f5adf-238a-346d-a72c-ced16ba7c2d9 | 2026-09-27T06:17:52Z | 2026-09-27T06:18:02Z | `{"operationId":"54ea6979-a53f-3706-9ed3-790e2c57a76c","correlationId":"a7d73297-1909-3dc6-9d3a-c7b23cff3339","siteId":"SUD","locationId":"a570b572-6b24-3318-9575-eb310588592b","repairId":"004eac8b-2cb6-3cca-a985-438842f67c29"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"72d90db7-4ed7-3529-a168-e31faec8c83b","copyId":"0cb61ce6-7ec8-3f85-9b84-aa6bb023f07d","locationId":"a570b572-6b24-3318-9575-eb310588592b","scopeId":"e4d9b7cb-3446-3955-8248-6f0b522656f2","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"0cb61ce6-7ec8-3f85-9b84-aa6bb023f07d","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0cb61ce6-7ec8-3f85-9b84-aa6bb023f07d","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"0cb61ce6-7ec8-3f85-9b84-aa6bb023f07d","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0cb61ce6-7ec8-3f85-9b84-aa6bb023f07d","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0cb61ce6-7ec8-3f85-9b84-aa6bb023f07d","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0cb61ce6-7ec8-3f85-9b84-aa6bb023f07d","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0cb61ce6-7ec8-3f85-9b84-aa6bb023f07d","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"0cb61ce6-7ec8-3f85-9b84-aa6bb023f07d"},{"kind":"sourceEvent","id":"0b1dce6c-d552-3862-a4c2-c2c8a354ce0f"},{"kind":"sourceEvent","id":"618d9196-ece7-3aaf-b112-61cfcd0c567e"},{"kind":"sourceEvent","id":"2e130cbe-43d9-39b2-9e12-7197a0490b59"},{"kind":"sourceEvent","id":"abdc16ae-c553-3003-8ee4-790afb65429a"},{"kind":"sourceEvent","id":"9036ea04-ae06-3451-b547-6850cdc42203"},{"kind":"sourceEvent","id":"69b99ab0-8451-3120-be81-76b9ae0d60c9"},{"kind":"sourceEvent","id":"d81ba93d-cf5c-344b-8b7f-ef632b01b548"},{"kind":"sourceEvent","id":"145f5adf-238a-346d-a72c-ced16ba7c2d9"},{"kind":"observation","id":"72d90db7-4ed7-3529-a168-e31faec8c83b"},{"kind":"inventoryScope","id":"e4d9b7cb-3446-3955-8248-6f0b522656f2"},{"kind":"sourceAvailability","id":"0cb61ce6-7ec8-3f85-9b84-aa6bb023f07d/LOANS"},{"kind":"sourceAvailability","id":"0cb61ce6-7ec8-3f85-9b84-aa6bb023f07d/RETURNS"},{"kind":"sourceAvailability","id":"0cb61ce6-7ec8-3f85-9b84-aa6bb023f07d/RECEIPTS"},{"kind":"sourceAvailability","id":"0cb61ce6-7ec8-3f85-9b84-aa6bb023f07d/TRANSFERS"},{"kind":"sourceAvailability","id":"0cb61ce6-7ec8-3f85-9b84-aa6bb023f07d/REPAIRS"},{"kind":"sourceAvailability","id":"0cb61ce6-7ec8-3f85-9b84-aa6bb023f07d/INTEGRATIONS"},{"kind":"sourceAvailability","id":"0cb61ce6-7ec8-3f85-9b84-aa6bb023f07d/INVENTORY"},{"kind":"inventoryScope","id":"7f91fb45-70cc-3811-b222-43a5370aa433"},{"kind":"inventoryScope","id":"202855ec-9065-3be6-a0c2-07ce228ecf21"},{"kind":"inventoryScope","id":"1b4f280a-3145-3607-a009-dca44e9d4181"}]`

- Session : `{"scopeId":"e4d9b7cb-3446-3955-8248-6f0b522656f2","locationId":"a570b572-6b24-3318-9575-eb310588592b","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"7f91fb45-70cc-3811-b222-43a5370aa433","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"202855ec-9065-3be6-a0c2-07ce228ecf21","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"1b4f280a-3145-3607-a009-dca44e9d4181","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 9a559b6e-068a-3caf-807a-12e752d83279 — dossier d'écart

- Exemplaire : `4b3e3438-a21d-38b2-9157-26f0eaea1a69`
- Famille privée : TRANSFERT_INCOHERENT ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"d7f388fc-6a59-3f58-8357-e420a7cf4ed2","kind":"STATUS_MISMATCH","expectedValue":"IN_TRANSIT","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"9c39630c-5e71-3755-b66d-f2822ebc8303","copyId":"4b3e3438-a21d-38b2-9157-26f0eaea1a69","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T08:55:03Z","recordedAt":"2026-09-27T08:55:13Z","payload":{"originalEventId":"636b3eb1-615f-3f4c-9f91-07ec8ee09b4d","correlationId":"3b416105-6f9f-3618-bd2a-83064eb61f6c","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"636b3eb1-615f-3f4c-9f91-07ec8ee09b4d","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"125bed65-7385-3595-a653-902580e9539f","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État du stock informatique : `{"copyId":"4b3e3438-a21d-38b2-9157-26f0eaea1a69","status":"IN_TRANSIT","siteId":"SUD","locationId":null,"sourceEventIds":["c9278783-7e80-3c71-bf7b-cff87f988776","0b570907-bbd0-3fc4-bab1-5a7b5587dd62"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | f6a7d195-30a5-3ff5-b6bb-af05720bab98 | 2026-06-01T13:05:51Z | 2026-06-01T13:06:01Z | `{"operationId":"9b4d7e2f-ef37-3c48-aeba-a0c964931701","correlationId":"12d2a5ca-3c36-34e4-a63b-9cfcafe7fbf5","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"ce780805-9729-3b37-a484-51ca63b0a6aa"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | ea7946e2-b32b-3435-b8a1-97a002300490 | 2026-06-02T13:05:51Z | 2026-06-02T13:06:01Z | `{"originalEventId":"f6a7d195-30a5-3ff5-b6bb-af05720bab98","correlationId":"12d2a5ca-3c36-34e4-a63b-9cfcafe7fbf5","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 0eba59a1-24f1-3afd-a0a3-8536a373aec5 | 2026-07-31T13:05:51Z | 2026-07-31T13:06:01Z | `{"operationId":"24dd9c90-7e77-3bce-a435-75a1b471294e","correlationId":"fc316613-4c34-3880-bf5c-9870c4b75961","siteId":"SUD","locationId":null,"loanId":"fab92cfe-590b-3a6a-b19f-3198f27ae5a8"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | d05b8ea2-b864-34cc-97d6-6b4f282354cc | 2026-07-31T13:06:51Z | 2026-07-31T13:07:01Z | `{"originalEventId":"0eba59a1-24f1-3afd-a0a3-8536a373aec5","correlationId":"fc316613-4c34-3880-bf5c-9870c4b75961","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | c4df117f-c84c-30ff-ad37-6318e166319e | 2026-08-20T13:05:51Z | 2026-08-20T13:06:01Z | `{"operationId":"51cd810d-cfb9-3380-bdd2-40c379db650d","correlationId":"2bffb344-0434-338c-bffd-4ef4c9afc11f","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"fab92cfe-590b-3a6a-b19f-3198f27ae5a8"}` |
| Retour reçu (`ReturnReceived`) | be4bad50-b399-31df-8e3d-12a97a41922f | 2026-08-20T13:06:21Z | 2026-08-20T13:06:31Z | `{"operationId":"3e07bbc9-86fb-339d-9a5d-62bab377447f","correlationId":"e492309e-c6e9-3a10-b932-18693eb55fc0","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"fab92cfe-590b-3a6a-b19f-3198f27ae5a8"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | ae15f202-379b-377f-bfcb-548f8fba750f | 2026-08-21T13:05:51Z | 2026-08-21T13:06:01Z | `{"originalEventId":"be4bad50-b399-31df-8e3d-12a97a41922f","correlationId":"e492309e-c6e9-3a10-b932-18693eb55fc0","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Transfert expédié (`TransferDispatched`) | c9278783-7e80-3c71-bf7b-cff87f988776 | 2026-09-26T08:54:33Z | 2026-09-26T08:54:43Z | `{"operationId":"18b6676a-09ae-3e8f-ae9a-0bdb16f5d419","correlationId":"ab8d34c4-9d68-3d7c-8f5c-f50089065bd3","siteId":"SUD","locationId":null,"transferId":"9546e37e-e745-3a85-ac2e-3ac272f02484","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 0b570907-bbd0-3fc4-bab1-5a7b5587dd62 | 2026-09-26T08:55:33Z | 2026-09-26T08:55:43Z | `{"originalEventId":"c9278783-7e80-3c71-bf7b-cff87f988776","correlationId":"ab8d34c4-9d68-3d7c-8f5c-f50089065bd3","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"SUD","locationId":null}` |
| Transfert reçu (`TransferReceived`) | 636b3eb1-615f-3f4c-9f91-07ec8ee09b4d | 2026-09-27T08:54:33Z | 2026-09-27T08:54:43Z | `{"operationId":"b2a34e6d-a324-37ae-bf89-8f908118cc2e","correlationId":"3b416105-6f9f-3618-bd2a-83064eb61f6c","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","transferId":"9546e37e-e745-3a85-ac2e-3ac272f02484","fromSiteId":"SUD","toSiteId":"CENTRE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"b2415996-c68c-37ac-a9ca-122935c9f12c","copyId":"4b3e3438-a21d-38b2-9157-26f0eaea1a69","locationId":"125bed65-7385-3595-a653-902580e9539f","scopeId":"a0f4b630-abea-33f6-9af9-eb48c395042e","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"4b3e3438-a21d-38b2-9157-26f0eaea1a69","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4b3e3438-a21d-38b2-9157-26f0eaea1a69","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"4b3e3438-a21d-38b2-9157-26f0eaea1a69","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4b3e3438-a21d-38b2-9157-26f0eaea1a69","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4b3e3438-a21d-38b2-9157-26f0eaea1a69","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4b3e3438-a21d-38b2-9157-26f0eaea1a69","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4b3e3438-a21d-38b2-9157-26f0eaea1a69","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"4b3e3438-a21d-38b2-9157-26f0eaea1a69"},{"kind":"sourceEvent","id":"f6a7d195-30a5-3ff5-b6bb-af05720bab98"},{"kind":"sourceEvent","id":"ea7946e2-b32b-3435-b8a1-97a002300490"},{"kind":"sourceEvent","id":"0eba59a1-24f1-3afd-a0a3-8536a373aec5"},{"kind":"sourceEvent","id":"d05b8ea2-b864-34cc-97d6-6b4f282354cc"},{"kind":"sourceEvent","id":"c4df117f-c84c-30ff-ad37-6318e166319e"},{"kind":"sourceEvent","id":"be4bad50-b399-31df-8e3d-12a97a41922f"},{"kind":"sourceEvent","id":"ae15f202-379b-377f-bfcb-548f8fba750f"},{"kind":"sourceEvent","id":"c9278783-7e80-3c71-bf7b-cff87f988776"},{"kind":"sourceEvent","id":"0b570907-bbd0-3fc4-bab1-5a7b5587dd62"},{"kind":"sourceEvent","id":"636b3eb1-615f-3f4c-9f91-07ec8ee09b4d"},{"kind":"observation","id":"b2415996-c68c-37ac-a9ca-122935c9f12c"},{"kind":"inventoryScope","id":"a0f4b630-abea-33f6-9af9-eb48c395042e"},{"kind":"sourceAvailability","id":"4b3e3438-a21d-38b2-9157-26f0eaea1a69/LOANS"},{"kind":"sourceAvailability","id":"4b3e3438-a21d-38b2-9157-26f0eaea1a69/RETURNS"},{"kind":"sourceAvailability","id":"4b3e3438-a21d-38b2-9157-26f0eaea1a69/RECEIPTS"},{"kind":"sourceAvailability","id":"4b3e3438-a21d-38b2-9157-26f0eaea1a69/TRANSFERS"},{"kind":"sourceAvailability","id":"4b3e3438-a21d-38b2-9157-26f0eaea1a69/REPAIRS"},{"kind":"sourceAvailability","id":"4b3e3438-a21d-38b2-9157-26f0eaea1a69/INTEGRATIONS"},{"kind":"sourceAvailability","id":"4b3e3438-a21d-38b2-9157-26f0eaea1a69/INVENTORY"}]`

- Session : `{"scopeId":"a0f4b630-abea-33f6-9af9-eb48c395042e","locationId":"125bed65-7385-3595-a653-902580e9539f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 9d978c98-51ac-37aa-8831-5064b228e24f — dossier d'écart

- Exemplaire : `9c5e8f26-d587-379c-9fb7-4f73ea71d99c`
- Famille privée : RETARD_INTEGRATION ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"58744637-8e9a-31d7-b544-47b151db73dc","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"64dbb38f-84e1-3df3-ad03-d2aa18ff1996","physicalStatus":"ON_LOAN","physicalSiteId":"SUD","physicalLocationId":null,"projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Recontrôler après la fenêtre de traitement"]}`
- État du stock informatique : `{"copyId":"9c5e8f26-d587-379c-9fb7-4f73ea71d99c","status":"ON_LOAN","siteId":"SUD","locationId":null,"sourceEventIds":["64dbb38f-84e1-3df3-ad03-d2aa18ff1996","5546d64c-a310-3cff-b0ea-434163833c2b"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | ee41e6ab-7483-3d8f-9b2a-c61bd037c710 | 2026-06-02T05:29:56Z | 2026-06-02T05:30:06Z | `{"operationId":"6780b66d-af17-3017-9a03-dc6d22ea53e0","correlationId":"5816b10f-f0ee-3728-b507-10ab5de69b3e","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"9f48ce7f-8e6e-3f58-9c25-ba2adb33e5fc"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 67a9b507-30cc-3aaf-838c-e2c7c726837e | 2026-06-03T05:29:56Z | 2026-06-03T05:30:06Z | `{"originalEventId":"ee41e6ab-7483-3d8f-9b2a-c61bd037c710","correlationId":"5816b10f-f0ee-3728-b507-10ab5de69b3e","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 8dd46821-11fa-35f5-8618-271236df3b97 | 2026-08-01T05:29:56Z | 2026-08-01T05:30:06Z | `{"operationId":"9d0b84f1-3e8c-3bfe-ba25-da758890adfc","correlationId":"176c873b-969f-3393-88b0-f287faeda756","siteId":"SUD","locationId":null,"loanId":"e11623ac-4e32-3273-8a76-cf6058db631c"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 1f90c6ae-a008-3153-b364-8a03d8a5e4c0 | 2026-08-01T05:30:56Z | 2026-08-01T05:31:06Z | `{"originalEventId":"8dd46821-11fa-35f5-8618-271236df3b97","correlationId":"176c873b-969f-3393-88b0-f287faeda756","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 85e24061-00b8-3fbd-9e42-7e1c4e8827dd | 2026-08-21T05:29:56Z | 2026-08-21T05:30:06Z | `{"operationId":"0012415c-faed-3e8c-b3e6-131633cf2186","correlationId":"1135af0c-236f-3bcb-94ef-39137f682d7f","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"e11623ac-4e32-3273-8a76-cf6058db631c"}` |
| Retour reçu (`ReturnReceived`) | 5779b4a2-4bae-3012-a7fd-53e060a9c525 | 2026-08-21T05:30:26Z | 2026-08-21T05:30:36Z | `{"operationId":"9c3c2aa2-4b29-38c0-920d-8349aed129ad","correlationId":"4541180a-8a95-33d0-9b01-f30acfd3e86f","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"e11623ac-4e32-3273-8a76-cf6058db631c"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 2e706bdc-bc57-3779-a75e-00c6cf75eb80 | 2026-08-22T05:29:56Z | 2026-08-22T05:30:06Z | `{"originalEventId":"5779b4a2-4bae-3012-a7fd-53e060a9c525","correlationId":"4541180a-8a95-33d0-9b01-f30acfd3e86f","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 64dbb38f-84e1-3df3-ad03-d2aa18ff1996 | 2026-09-30T11:53:26Z | 2026-09-30T11:53:36Z | `{"operationId":"6dbdac3d-77d6-3933-8ba8-ae025959023f","correlationId":"57721001-e5b8-366b-bd25-9ad687292b83","siteId":"SUD","locationId":null,"loanId":"1d251165-9c82-3467-bae1-cfc096e1a9ca"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | fa07c7f2-1125-3a12-af8f-8652ac078f84 | 2026-09-30T11:53:56Z | 2026-09-30T11:54:06Z | `{"originalEventId":"64dbb38f-84e1-3df3-ad03-d2aa18ff1996","correlationId":"57721001-e5b8-366b-bd25-9ad687292b83","outcome":"QUEUED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 5546d64c-a310-3cff-b0ea-434163833c2b | 2026-09-30T11:54:56Z | 2026-09-30T11:55:06Z | `{"originalEventId":"64dbb38f-84e1-3df3-ad03-d2aa18ff1996","correlationId":"57721001-e5b8-366b-bd25-9ad687292b83","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"bb3d4df5-1c4c-30b7-b086-ecb2770eb761","copyId":"9c5e8f26-d587-379c-9fb7-4f73ea71d99c","locationId":"128fab03-f193-3647-a67e-b835a30e7583","scopeId":"d0243333-bf4f-3382-9dbd-28e4e4bb1782","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"9c5e8f26-d587-379c-9fb7-4f73ea71d99c","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9c5e8f26-d587-379c-9fb7-4f73ea71d99c","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9c5e8f26-d587-379c-9fb7-4f73ea71d99c","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9c5e8f26-d587-379c-9fb7-4f73ea71d99c","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9c5e8f26-d587-379c-9fb7-4f73ea71d99c","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9c5e8f26-d587-379c-9fb7-4f73ea71d99c","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9c5e8f26-d587-379c-9fb7-4f73ea71d99c","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"9c5e8f26-d587-379c-9fb7-4f73ea71d99c"},{"kind":"sourceEvent","id":"ee41e6ab-7483-3d8f-9b2a-c61bd037c710"},{"kind":"sourceEvent","id":"67a9b507-30cc-3aaf-838c-e2c7c726837e"},{"kind":"sourceEvent","id":"8dd46821-11fa-35f5-8618-271236df3b97"},{"kind":"sourceEvent","id":"1f90c6ae-a008-3153-b364-8a03d8a5e4c0"},{"kind":"sourceEvent","id":"85e24061-00b8-3fbd-9e42-7e1c4e8827dd"},{"kind":"sourceEvent","id":"5779b4a2-4bae-3012-a7fd-53e060a9c525"},{"kind":"sourceEvent","id":"2e706bdc-bc57-3779-a75e-00c6cf75eb80"},{"kind":"sourceEvent","id":"64dbb38f-84e1-3df3-ad03-d2aa18ff1996"},{"kind":"sourceEvent","id":"fa07c7f2-1125-3a12-af8f-8652ac078f84"},{"kind":"sourceEvent","id":"5546d64c-a310-3cff-b0ea-434163833c2b"},{"kind":"observation","id":"bb3d4df5-1c4c-30b7-b086-ecb2770eb761"},{"kind":"inventoryScope","id":"d0243333-bf4f-3382-9dbd-28e4e4bb1782"},{"kind":"sourceAvailability","id":"9c5e8f26-d587-379c-9fb7-4f73ea71d99c/LOANS"},{"kind":"sourceAvailability","id":"9c5e8f26-d587-379c-9fb7-4f73ea71d99c/RETURNS"},{"kind":"sourceAvailability","id":"9c5e8f26-d587-379c-9fb7-4f73ea71d99c/RECEIPTS"},{"kind":"sourceAvailability","id":"9c5e8f26-d587-379c-9fb7-4f73ea71d99c/TRANSFERS"},{"kind":"sourceAvailability","id":"9c5e8f26-d587-379c-9fb7-4f73ea71d99c/REPAIRS"},{"kind":"sourceAvailability","id":"9c5e8f26-d587-379c-9fb7-4f73ea71d99c/INTEGRATIONS"},{"kind":"sourceAvailability","id":"9c5e8f26-d587-379c-9fb7-4f73ea71d99c/INVENTORY"}]`

- Session : `{"scopeId":"d0243333-bf4f-3382-9dbd-28e4e4bb1782","locationId":"128fab03-f193-3647-a67e-b835a30e7583","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

## Cas a4c278a9-8c23-35f2-804f-41c9b0c7bfe1 — dossier d'écart

- Exemplaire : `fc119c5d-3d07-3472-bcdb-4124326d0e2f`
- Famille privée : RECEPTION_NON_INTEGREE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"a15dccdc-0cc4-36a2-9656-957b0fce5731","kind":"UNEXPECTED_PRESENCE","expectedValue":"NOT_REGISTERED","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"97c252e9-2fa3-3856-884b-942b91f15df0","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["RECEIPT_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"fc119c5d-3d07-3472-bcdb-4124326d0e2f","status":"NOT_REGISTERED","siteId":"NORD","locationId":null,"sourceEventIds":[]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 97c252e9-2fa3-3856-884b-942b91f15df0 | 2026-09-27T11:13:41Z | 2026-09-27T11:13:51Z | `{"operationId":"bbc15173-9cce-3998-bea8-614792a492f1","correlationId":"1c2e0e11-44be-328c-97b8-688254200778","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"0c0d397c-b5a0-3891-8034-882c69e7354f"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 2fd39b31-6374-3e18-9aa9-a42fb0ad1194 | 2026-09-27T11:14:11Z | 2026-09-27T11:14:21Z | `{"originalEventId":"97c252e9-2fa3-3856-884b-942b91f15df0","correlationId":"1c2e0e11-44be-328c-97b8-688254200778","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"bbef90f5-35fb-3b01-8fdf-afae68891517","copyId":"fc119c5d-3d07-3472-bcdb-4124326d0e2f","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","scopeId":"21de8fb6-32df-3328-b955-f4fd2d8252e5","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"fc119c5d-3d07-3472-bcdb-4124326d0e2f","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"fc119c5d-3d07-3472-bcdb-4124326d0e2f","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"fc119c5d-3d07-3472-bcdb-4124326d0e2f","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"fc119c5d-3d07-3472-bcdb-4124326d0e2f","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"fc119c5d-3d07-3472-bcdb-4124326d0e2f","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"fc119c5d-3d07-3472-bcdb-4124326d0e2f","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"fc119c5d-3d07-3472-bcdb-4124326d0e2f","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"fc119c5d-3d07-3472-bcdb-4124326d0e2f"},{"kind":"sourceEvent","id":"97c252e9-2fa3-3856-884b-942b91f15df0"},{"kind":"sourceEvent","id":"2fd39b31-6374-3e18-9aa9-a42fb0ad1194"},{"kind":"observation","id":"bbef90f5-35fb-3b01-8fdf-afae68891517"},{"kind":"inventoryScope","id":"21de8fb6-32df-3328-b955-f4fd2d8252e5"},{"kind":"sourceAvailability","id":"fc119c5d-3d07-3472-bcdb-4124326d0e2f/LOANS"},{"kind":"sourceAvailability","id":"fc119c5d-3d07-3472-bcdb-4124326d0e2f/RETURNS"},{"kind":"sourceAvailability","id":"fc119c5d-3d07-3472-bcdb-4124326d0e2f/RECEIPTS"},{"kind":"sourceAvailability","id":"fc119c5d-3d07-3472-bcdb-4124326d0e2f/TRANSFERS"},{"kind":"sourceAvailability","id":"fc119c5d-3d07-3472-bcdb-4124326d0e2f/REPAIRS"},{"kind":"sourceAvailability","id":"fc119c5d-3d07-3472-bcdb-4124326d0e2f/INTEGRATIONS"},{"kind":"sourceAvailability","id":"fc119c5d-3d07-3472-bcdb-4124326d0e2f/INVENTORY"}]`

- Session : `{"scopeId":"21de8fb6-32df-3328-b955-f4fd2d8252e5","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas a613d722-2fe0-3da8-99ca-eeb8c240ab98 — dossier d'écart

- Exemplaire : `f873f98b-102f-3c4b-adef-c19b3f1a5608`
- Famille privée : DEUX_CAUSES ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"89615374-063c-3a9b-a8e7-e7aa1920fb04","kind":"STATUS_MISMATCH","expectedValue":"IN_REPAIR","observedValue":"IN_REPAIR"},{"symptomId":"44011748-04a6-34c5-a729-6097c4e81f36","kind":"LOCATION_MISMATCH","expectedValue":"14f2d626-9459-3fee-9d3a-daf738f7e59a","observedValue":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["REPAIR_LOCATION_CHANGED"],"hiddenSourceEvents":[],"mechanism":"Réparation engagée puis déplacement distinct de l'atelier vers la réserve sans mise à jour de l'affectation de réparation.","businessEventId":"19d03b52-88d9-3a8c-b326-5ee91d211f74","physicalStatus":"IN_REPAIR","physicalSiteId":"CENTRE","physicalLocationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_LOCATION_MISMATCH"],"explanation":"Réparation correctement projetée après reprise ; seul le déplacement hors atelier reste étayé.","forbiddenClaims":["Deux causes confirmées par un scan ambigu","Vol"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"f873f98b-102f-3c4b-adef-c19b3f1a5608","status":"IN_REPAIR","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","sourceEventIds":["19d03b52-88d9-3a8c-b326-5ee91d211f74","949ada87-b607-34d3-bfb8-61b98b27fbd7"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 0062cceb-da6b-36d7-9145-ab61930e08d8 | 2026-06-02T04:44:41Z | 2026-06-02T04:44:51Z | `{"operationId":"d1ae492a-9a18-3c96-8223-73def8cf6753","correlationId":"07e2a71c-f1a0-3cf3-9db9-f953cf3b6b9b","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"af004edb-272e-3383-bba5-505a6f3372ee"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 764da59f-2d49-313d-bb77-958fc7237d47 | 2026-06-03T04:44:41Z | 2026-06-03T04:44:51Z | `{"originalEventId":"0062cceb-da6b-36d7-9145-ab61930e08d8","correlationId":"07e2a71c-f1a0-3cf3-9db9-f953cf3b6b9b","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 2f0f28f4-c035-3b45-926e-34e873f4fb1d | 2026-08-01T04:44:41Z | 2026-08-01T04:44:51Z | `{"operationId":"6e1b4448-d3a1-3d45-9b29-250765b206cb","correlationId":"7f14e799-d11d-394f-89c2-03cbecff8598","siteId":"CENTRE","locationId":null,"loanId":"caf989b3-cf68-3a50-92db-458c767fb65b"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 7eedc9b2-6c5b-3c72-a2cb-bb5339d89fda | 2026-08-01T04:45:41Z | 2026-08-01T04:45:51Z | `{"originalEventId":"2f0f28f4-c035-3b45-926e-34e873f4fb1d","correlationId":"7f14e799-d11d-394f-89c2-03cbecff8598","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 517bd367-44a8-34cf-8a52-bd30582012a7 | 2026-08-21T04:44:41Z | 2026-08-21T04:44:51Z | `{"operationId":"ea78c677-d984-3f0c-af09-9d9c626c3aeb","correlationId":"cf059a98-4064-37d5-8299-39d3d30ce876","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"caf989b3-cf68-3a50-92db-458c767fb65b"}` |
| Retour reçu (`ReturnReceived`) | 412e7c64-d7e1-358a-a7be-5b5e47958735 | 2026-08-21T04:45:11Z | 2026-08-21T04:45:21Z | `{"operationId":"5e163e7d-f422-3772-8853-f34e5cac1c8a","correlationId":"fdd89ba3-8f84-334c-84f0-68ab7835ebf3","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"caf989b3-cf68-3a50-92db-458c767fb65b"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 220e895f-b99c-3125-bb3a-2ae0bc99e9e6 | 2026-08-22T04:44:41Z | 2026-08-22T04:44:51Z | `{"originalEventId":"412e7c64-d7e1-358a-a7be-5b5e47958735","correlationId":"fdd89ba3-8f84-334c-84f0-68ab7835ebf3","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Réparation engagée (`RepairStarted`) | 19d03b52-88d9-3a8c-b326-5ee91d211f74 | 2026-09-27T06:54:30Z | 2026-09-27T06:54:40Z | `{"operationId":"4b84ecf2-06f5-316d-82bf-c449ec8a3bb0","correlationId":"e24e2b22-20e6-3bd9-80d2-8f28da2c9be2","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","repairId":"44577c4b-13c8-300c-9bcd-66d0ba962313"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 0358d503-aa38-3bd4-a898-ce82a0a32497 | 2026-09-27T06:55:00Z | 2026-09-27T06:55:10Z | `{"originalEventId":"19d03b52-88d9-3a8c-b326-5ee91d211f74","correlationId":"e24e2b22-20e6-3bd9-80d2-8f28da2c9be2","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 949ada87-b607-34d3-bfb8-61b98b27fbd7 | 2026-09-27T06:56:00Z | 2026-09-27T06:56:10Z | `{"originalEventId":"19d03b52-88d9-3a8c-b326-5ee91d211f74","correlationId":"e24e2b22-20e6-3bd9-80d2-8f28da2c9be2","outcome":"APPLIED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"72cca0ba-39e8-33fd-818e-e4bac63a7d56","copyId":"f873f98b-102f-3c4b-adef-c19b3f1a5608","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","scopeId":"0bbd62c6-a226-3919-8e7b-168218b2e39d","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"f873f98b-102f-3c4b-adef-c19b3f1a5608","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f873f98b-102f-3c4b-adef-c19b3f1a5608","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f873f98b-102f-3c4b-adef-c19b3f1a5608","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f873f98b-102f-3c4b-adef-c19b3f1a5608","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f873f98b-102f-3c4b-adef-c19b3f1a5608","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f873f98b-102f-3c4b-adef-c19b3f1a5608","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f873f98b-102f-3c4b-adef-c19b3f1a5608","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"f873f98b-102f-3c4b-adef-c19b3f1a5608"},{"kind":"sourceEvent","id":"0062cceb-da6b-36d7-9145-ab61930e08d8"},{"kind":"sourceEvent","id":"764da59f-2d49-313d-bb77-958fc7237d47"},{"kind":"sourceEvent","id":"2f0f28f4-c035-3b45-926e-34e873f4fb1d"},{"kind":"sourceEvent","id":"7eedc9b2-6c5b-3c72-a2cb-bb5339d89fda"},{"kind":"sourceEvent","id":"517bd367-44a8-34cf-8a52-bd30582012a7"},{"kind":"sourceEvent","id":"412e7c64-d7e1-358a-a7be-5b5e47958735"},{"kind":"sourceEvent","id":"220e895f-b99c-3125-bb3a-2ae0bc99e9e6"},{"kind":"sourceEvent","id":"19d03b52-88d9-3a8c-b326-5ee91d211f74"},{"kind":"sourceEvent","id":"0358d503-aa38-3bd4-a898-ce82a0a32497"},{"kind":"sourceEvent","id":"949ada87-b607-34d3-bfb8-61b98b27fbd7"},{"kind":"observation","id":"72cca0ba-39e8-33fd-818e-e4bac63a7d56"},{"kind":"inventoryScope","id":"0bbd62c6-a226-3919-8e7b-168218b2e39d"},{"kind":"sourceAvailability","id":"f873f98b-102f-3c4b-adef-c19b3f1a5608/LOANS"},{"kind":"sourceAvailability","id":"f873f98b-102f-3c4b-adef-c19b3f1a5608/RETURNS"},{"kind":"sourceAvailability","id":"f873f98b-102f-3c4b-adef-c19b3f1a5608/RECEIPTS"},{"kind":"sourceAvailability","id":"f873f98b-102f-3c4b-adef-c19b3f1a5608/TRANSFERS"},{"kind":"sourceAvailability","id":"f873f98b-102f-3c4b-adef-c19b3f1a5608/REPAIRS"},{"kind":"sourceAvailability","id":"f873f98b-102f-3c4b-adef-c19b3f1a5608/INTEGRATIONS"},{"kind":"sourceAvailability","id":"f873f98b-102f-3c4b-adef-c19b3f1a5608/INVENTORY"},{"kind":"inventoryScope","id":"ec50a541-89c5-30b3-950e-99a9f4dfc9f5"},{"kind":"inventoryScope","id":"08896ffd-fe25-3d58-800a-49293c383852"},{"kind":"inventoryScope","id":"d60e9f22-40a9-3088-8d67-f59de1f0f246"},{"kind":"inventoryScope","id":"820c3d0f-aace-30c6-b8e1-be88b5aeb758"},{"kind":"inventoryScope","id":"c2a743d7-550e-32a1-ba48-e4ea1c20120b"},{"kind":"inventoryScope","id":"bdfc1cac-117d-307c-9360-9fffd70d003f"}]`

- Session : `{"scopeId":"0bbd62c6-a226-3919-8e7b-168218b2e39d","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"ec50a541-89c5-30b3-950e-99a9f4dfc9f5","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"08896ffd-fe25-3d58-800a-49293c383852","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"d60e9f22-40a9-3088-8d67-f59de1f0f246","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"820c3d0f-aace-30c6-b8e1-be88b5aeb758","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"c2a743d7-550e-32a1-ba48-e4ea1c20120b","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"bdfc1cac-117d-307c-9360-9fffd70d003f","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas a923f176-18e8-3f0e-9d9e-a81947979c20 — dossier d'écart

- Exemplaire : `3b1c29fb-4682-3aae-9cfd-9abeef2bdf7a`
- Famille privée : REPARATION_NON_PROJETEE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"1a10caa7-6e82-393f-ad4b-11504f52b146","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"ad7ddfb7-4daa-3b15-9773-f3390f2bcb06","physicalStatus":"IN_REPAIR","physicalSiteId":"SUD","physicalLocationId":"a570b572-6b24-3318-9575-eb310588592b","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"3b1c29fb-4682-3aae-9cfd-9abeef2bdf7a","status":"IN_REPAIR","siteId":"SUD","locationId":"a570b572-6b24-3318-9575-eb310588592b","sourceEventIds":["ad7ddfb7-4daa-3b15-9773-f3390f2bcb06","c750f0ae-4ed4-37c0-abf0-d41b491aa6ff"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 0b6c0d57-a086-3729-898c-b92d77d5cf80 | 2026-06-01T19:02:35Z | 2026-06-01T19:02:45Z | `{"operationId":"b755b446-6baf-3d98-9f4b-ef57f7a7671c","correlationId":"026af84d-450c-363d-a8cd-99a886bc04d1","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"a5d6fd37-d80f-3417-9812-b3c0c6b8caf2"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 4aad33c9-f56b-3b25-a951-057c03124119 | 2026-06-02T19:02:35Z | 2026-06-02T19:02:45Z | `{"originalEventId":"0b6c0d57-a086-3729-898c-b92d77d5cf80","correlationId":"026af84d-450c-363d-a8cd-99a886bc04d1","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 465e23fc-aac8-34c4-a4d5-9a332b35fabd | 2026-07-31T19:02:35Z | 2026-07-31T19:02:45Z | `{"operationId":"322b2192-3415-36b8-825f-8ebfeae2c2e6","correlationId":"548e835b-9cc8-300a-aeca-be68247a1739","siteId":"SUD","locationId":null,"loanId":"43adc52e-a72f-31e6-a129-c2e946816b0f"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | d0d699ed-ee4c-3e87-863b-ee8fce91879a | 2026-07-31T19:03:35Z | 2026-07-31T19:03:45Z | `{"originalEventId":"465e23fc-aac8-34c4-a4d5-9a332b35fabd","correlationId":"548e835b-9cc8-300a-aeca-be68247a1739","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 83592b8f-f2ca-34d1-9a5c-fc057c79be15 | 2026-08-20T19:02:35Z | 2026-08-20T19:02:45Z | `{"operationId":"4b4d22c1-ab9e-39e9-a4cf-34a4763bf596","correlationId":"f3de296a-ea51-3816-ab0f-7b7e068290d7","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"43adc52e-a72f-31e6-a129-c2e946816b0f"}` |
| Retour reçu (`ReturnReceived`) | 6bfbe5a1-acea-3bb0-aa3d-a8baedb4dc32 | 2026-08-20T19:03:05Z | 2026-08-20T19:03:15Z | `{"operationId":"3b41d685-9abc-37c8-a1cf-a295e0229cd2","correlationId":"7f2e548c-363f-3702-8203-2ef9ccf02f63","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"43adc52e-a72f-31e6-a129-c2e946816b0f"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 5b541a9b-4699-3041-9246-efae16d6558f | 2026-08-21T19:02:35Z | 2026-08-21T19:02:45Z | `{"originalEventId":"6bfbe5a1-acea-3bb0-aa3d-a8baedb4dc32","correlationId":"7f2e548c-363f-3702-8203-2ef9ccf02f63","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Réparation engagée (`RepairStarted`) | ad7ddfb7-4daa-3b15-9773-f3390f2bcb06 | 2026-09-27T10:15:29Z | 2026-09-27T10:15:39Z | `{"operationId":"633b0a77-d45c-35ca-b643-5f73c1544815","correlationId":"b11d3602-115c-3a7e-9548-feb332168d5b","siteId":"SUD","locationId":"a570b572-6b24-3318-9575-eb310588592b","repairId":"7289bc31-c2ea-34ac-aeac-ff08b209aec2"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 503acd12-e878-3382-93c1-49c865495b7c | 2026-09-27T10:15:59Z | 2026-09-27T10:16:09Z | `{"originalEventId":"ad7ddfb7-4daa-3b15-9773-f3390f2bcb06","correlationId":"b11d3602-115c-3a7e-9548-feb332168d5b","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"SUD","locationId":"a570b572-6b24-3318-9575-eb310588592b","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | c750f0ae-4ed4-37c0-abf0-d41b491aa6ff | 2026-09-27T10:16:59Z | 2026-09-27T10:17:09Z | `{"originalEventId":"ad7ddfb7-4daa-3b15-9773-f3390f2bcb06","correlationId":"b11d3602-115c-3a7e-9548-feb332168d5b","outcome":"APPLIED","projectedStatus":"IN_REPAIR","siteId":"SUD","locationId":"a570b572-6b24-3318-9575-eb310588592b"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"71844683-3530-34c2-adb9-ffa402ef8d4b","copyId":"3b1c29fb-4682-3aae-9cfd-9abeef2bdf7a","locationId":"128fab03-f193-3647-a67e-b835a30e7583","scopeId":"0b9baafe-ad53-3b99-933f-a517e76a250b","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`
- `{"observationId":"84233098-a503-3fb7-af83-cec6a30ea7f3","copyId":"3b1c29fb-4682-3aae-9cfd-9abeef2bdf7a","locationId":"a570b572-6b24-3318-9575-eb310588592b","scopeId":"589dd0dd-c0e4-3660-979b-45e6127f7a83","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"3b1c29fb-4682-3aae-9cfd-9abeef2bdf7a","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3b1c29fb-4682-3aae-9cfd-9abeef2bdf7a","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3b1c29fb-4682-3aae-9cfd-9abeef2bdf7a","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3b1c29fb-4682-3aae-9cfd-9abeef2bdf7a","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3b1c29fb-4682-3aae-9cfd-9abeef2bdf7a","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3b1c29fb-4682-3aae-9cfd-9abeef2bdf7a","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3b1c29fb-4682-3aae-9cfd-9abeef2bdf7a","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"3b1c29fb-4682-3aae-9cfd-9abeef2bdf7a"},{"kind":"sourceEvent","id":"0b6c0d57-a086-3729-898c-b92d77d5cf80"},{"kind":"sourceEvent","id":"4aad33c9-f56b-3b25-a951-057c03124119"},{"kind":"sourceEvent","id":"465e23fc-aac8-34c4-a4d5-9a332b35fabd"},{"kind":"sourceEvent","id":"d0d699ed-ee4c-3e87-863b-ee8fce91879a"},{"kind":"sourceEvent","id":"83592b8f-f2ca-34d1-9a5c-fc057c79be15"},{"kind":"sourceEvent","id":"6bfbe5a1-acea-3bb0-aa3d-a8baedb4dc32"},{"kind":"sourceEvent","id":"5b541a9b-4699-3041-9246-efae16d6558f"},{"kind":"sourceEvent","id":"ad7ddfb7-4daa-3b15-9773-f3390f2bcb06"},{"kind":"sourceEvent","id":"503acd12-e878-3382-93c1-49c865495b7c"},{"kind":"sourceEvent","id":"c750f0ae-4ed4-37c0-abf0-d41b491aa6ff"},{"kind":"observation","id":"84233098-a503-3fb7-af83-cec6a30ea7f3"},{"kind":"inventoryScope","id":"589dd0dd-c0e4-3660-979b-45e6127f7a83"},{"kind":"observation","id":"71844683-3530-34c2-adb9-ffa402ef8d4b"},{"kind":"inventoryScope","id":"0b9baafe-ad53-3b99-933f-a517e76a250b"},{"kind":"sourceAvailability","id":"3b1c29fb-4682-3aae-9cfd-9abeef2bdf7a/LOANS"},{"kind":"sourceAvailability","id":"3b1c29fb-4682-3aae-9cfd-9abeef2bdf7a/RETURNS"},{"kind":"sourceAvailability","id":"3b1c29fb-4682-3aae-9cfd-9abeef2bdf7a/RECEIPTS"},{"kind":"sourceAvailability","id":"3b1c29fb-4682-3aae-9cfd-9abeef2bdf7a/TRANSFERS"},{"kind":"sourceAvailability","id":"3b1c29fb-4682-3aae-9cfd-9abeef2bdf7a/REPAIRS"},{"kind":"sourceAvailability","id":"3b1c29fb-4682-3aae-9cfd-9abeef2bdf7a/INTEGRATIONS"},{"kind":"sourceAvailability","id":"3b1c29fb-4682-3aae-9cfd-9abeef2bdf7a/INVENTORY"},{"kind":"inventoryScope","id":"6cd75017-62f5-3a85-9ca4-5b89b79bc056"},{"kind":"inventoryScope","id":"0a5018ac-fd62-39c6-b080-03551f006e3d"},{"kind":"inventoryScope","id":"fc3c069e-87f6-32ef-b435-2710775e6dcd"},{"kind":"inventoryScope","id":"3b586894-b84e-3113-b650-26521b1804d4"},{"kind":"inventoryScope","id":"e4d9b7cb-3446-3955-8248-6f0b522656f2"}]`

- Session : `{"scopeId":"589dd0dd-c0e4-3660-979b-45e6127f7a83","locationId":"a570b572-6b24-3318-9575-eb310588592b","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"0b9baafe-ad53-3b99-933f-a517e76a250b","locationId":"128fab03-f193-3647-a67e-b835a30e7583","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"6cd75017-62f5-3a85-9ca4-5b89b79bc056","locationId":"a570b572-6b24-3318-9575-eb310588592b","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"0a5018ac-fd62-39c6-b080-03551f006e3d","locationId":"a570b572-6b24-3318-9575-eb310588592b","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"fc3c069e-87f6-32ef-b435-2710775e6dcd","locationId":"a570b572-6b24-3318-9575-eb310588592b","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"3b586894-b84e-3113-b650-26521b1804d4","locationId":"a570b572-6b24-3318-9575-eb310588592b","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"e4d9b7cb-3446-3955-8248-6f0b522656f2","locationId":"a570b572-6b24-3318-9575-eb310588592b","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas a9492e1d-96ca-34d0-b0c3-3c0a4ed534c6 — dossier d'écart

- Exemplaire : `b297b6de-46b4-3010-bb5e-d7aacf9ceec0`
- Famille privée : DEUX_CAUSES ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"250fbbf0-f61b-35e4-a5d1-2d71f1ef7750","kind":"STATUS_MISMATCH","expectedValue":"IN_REPAIR","observedValue":"ON_SHELF"},{"symptomId":"659e54f7-4279-33ce-ae95-027fb45e1b0d","kind":"LOCATION_MISMATCH","expectedValue":"14f2d626-9459-3fee-9d3a-daf738f7e59a","observedValue":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED","REPAIR_LOCATION_CHANGED"],"hiddenSourceEvents":[],"mechanism":"Réparation engagée puis déplacement distinct de l'atelier vers la réserve sans mise à jour de l'affectation de réparation.","businessEventId":"6fceb640-27bd-3fba-acd8-524ca4eee795","physicalStatus":"IN_REPAIR","physicalSiteId":"CENTRE","physicalLocationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","projectionPerturbation":"FAILED","evidenceMask":"Scan de réserve ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":["REPAIR_NOT_PROJECTED"],"explanation":"Échec de projection de réparation documenté ; seconde localisation non confirmée par un scan valide.","forbiddenClaims":["Deux causes confirmées par un scan ambigu","Vol"],"recommendedChecks":["Refaire le scan en réserve"]}`
- État du stock informatique : `{"copyId":"b297b6de-46b4-3010-bb5e-d7aacf9ceec0","status":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","sourceEventIds":["82e36738-08e4-31f6-aa08-a7ef5c509f75","9c586c83-56b3-3464-ba1d-73de7162dfe4"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | cef31543-ccdd-3ac5-bcba-8b0a4d590bce | 2026-06-01T16:34:56Z | 2026-06-01T16:35:06Z | `{"operationId":"c0a532d2-f9ac-324c-837b-919d018a34d3","correlationId":"3b5aa11f-7fdb-324c-913f-c94895094196","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"84f44a17-f102-316c-bf18-1be382792b16"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 8b61b9a0-2d24-33c3-8381-eb4b3e8be385 | 2026-06-02T16:34:56Z | 2026-06-02T16:35:06Z | `{"originalEventId":"cef31543-ccdd-3ac5-bcba-8b0a4d590bce","correlationId":"3b5aa11f-7fdb-324c-913f-c94895094196","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 782202de-cf87-3ce7-81e9-fb02d972f58c | 2026-07-31T16:34:56Z | 2026-07-31T16:35:06Z | `{"operationId":"66d41775-9888-3ddf-839f-b8fa97c7f556","correlationId":"b3f80db7-7d9a-33c8-8821-5a5c9de6ba70","siteId":"CENTRE","locationId":null,"loanId":"338d1f6a-c59f-38e2-8929-36b0ec82a15d"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 13329140-3ccd-35ef-bed8-4f47164e3fe8 | 2026-07-31T16:35:56Z | 2026-07-31T16:36:06Z | `{"originalEventId":"782202de-cf87-3ce7-81e9-fb02d972f58c","correlationId":"b3f80db7-7d9a-33c8-8821-5a5c9de6ba70","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 41ac43bc-b780-37d5-8063-2fecb8559221 | 2026-08-20T16:34:56Z | 2026-08-20T16:35:06Z | `{"operationId":"c3931b02-c9b7-3235-a853-7d552a1a3292","correlationId":"5d6101b4-9ffe-3586-ab8f-ecb82864360d","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"338d1f6a-c59f-38e2-8929-36b0ec82a15d"}` |
| Retour reçu (`ReturnReceived`) | 82e36738-08e4-31f6-aa08-a7ef5c509f75 | 2026-08-20T16:35:26Z | 2026-08-20T16:35:36Z | `{"operationId":"fd736989-b7a0-3e2f-a7da-8bb0905839a6","correlationId":"94073e38-705b-3f2e-acf1-562dccfc3969","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"338d1f6a-c59f-38e2-8929-36b0ec82a15d"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 9c586c83-56b3-3464-ba1d-73de7162dfe4 | 2026-08-21T16:34:56Z | 2026-08-21T16:35:06Z | `{"originalEventId":"82e36738-08e4-31f6-aa08-a7ef5c509f75","correlationId":"94073e38-705b-3f2e-acf1-562dccfc3969","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Réparation engagée (`RepairStarted`) | 6fceb640-27bd-3fba-acd8-524ca4eee795 | 2026-09-27T10:38:19Z | 2026-09-27T10:38:29Z | `{"operationId":"eb3f4311-af1d-3f65-90e4-e5d2e44d1868","correlationId":"fbb3f643-9cb4-3d88-b155-1e356995b94d","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","repairId":"88afd9eb-a06d-3c14-9e45-e7e671545ab9"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | de4bd853-3b07-36e4-bb92-78361266afbd | 2026-09-27T10:38:49Z | 2026-09-27T10:38:59Z | `{"originalEventId":"6fceb640-27bd-3fba-acd8-524ca4eee795","correlationId":"fbb3f643-9cb4-3d88-b155-1e356995b94d","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"1e63c5d9-9494-351c-aa82-fd1030c3decb","copyId":"b297b6de-46b4-3010-bb5e-d7aacf9ceec0","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","scopeId":"da44e1e1-c099-34b4-a121-397ca4ed7e37","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"b297b6de-46b4-3010-bb5e-d7aacf9ceec0","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b297b6de-46b4-3010-bb5e-d7aacf9ceec0","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b297b6de-46b4-3010-bb5e-d7aacf9ceec0","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b297b6de-46b4-3010-bb5e-d7aacf9ceec0","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b297b6de-46b4-3010-bb5e-d7aacf9ceec0","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b297b6de-46b4-3010-bb5e-d7aacf9ceec0","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b297b6de-46b4-3010-bb5e-d7aacf9ceec0","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"b297b6de-46b4-3010-bb5e-d7aacf9ceec0"},{"kind":"sourceEvent","id":"cef31543-ccdd-3ac5-bcba-8b0a4d590bce"},{"kind":"sourceEvent","id":"8b61b9a0-2d24-33c3-8381-eb4b3e8be385"},{"kind":"sourceEvent","id":"782202de-cf87-3ce7-81e9-fb02d972f58c"},{"kind":"sourceEvent","id":"13329140-3ccd-35ef-bed8-4f47164e3fe8"},{"kind":"sourceEvent","id":"41ac43bc-b780-37d5-8063-2fecb8559221"},{"kind":"sourceEvent","id":"82e36738-08e4-31f6-aa08-a7ef5c509f75"},{"kind":"sourceEvent","id":"9c586c83-56b3-3464-ba1d-73de7162dfe4"},{"kind":"sourceEvent","id":"6fceb640-27bd-3fba-acd8-524ca4eee795"},{"kind":"sourceEvent","id":"de4bd853-3b07-36e4-bb92-78361266afbd"},{"kind":"observation","id":"1e63c5d9-9494-351c-aa82-fd1030c3decb"},{"kind":"inventoryScope","id":"da44e1e1-c099-34b4-a121-397ca4ed7e37"},{"kind":"sourceAvailability","id":"b297b6de-46b4-3010-bb5e-d7aacf9ceec0/LOANS"},{"kind":"sourceAvailability","id":"b297b6de-46b4-3010-bb5e-d7aacf9ceec0/RETURNS"},{"kind":"sourceAvailability","id":"b297b6de-46b4-3010-bb5e-d7aacf9ceec0/RECEIPTS"},{"kind":"sourceAvailability","id":"b297b6de-46b4-3010-bb5e-d7aacf9ceec0/TRANSFERS"},{"kind":"sourceAvailability","id":"b297b6de-46b4-3010-bb5e-d7aacf9ceec0/REPAIRS"},{"kind":"sourceAvailability","id":"b297b6de-46b4-3010-bb5e-d7aacf9ceec0/INTEGRATIONS"},{"kind":"sourceAvailability","id":"b297b6de-46b4-3010-bb5e-d7aacf9ceec0/INVENTORY"},{"kind":"inventoryScope","id":"7d46c7a4-f137-377d-8437-5d11eeb4049d"},{"kind":"inventoryScope","id":"66954879-6228-3c31-bf72-7b217d3c9695"},{"kind":"inventoryScope","id":"2a6f7727-71d3-3a12-96f6-4b5e2b38ce02"},{"kind":"inventoryScope","id":"c0755c3b-6689-3d3d-8527-6c90e6c6e5d5"},{"kind":"inventoryScope","id":"3879a968-0f8b-33bb-b62b-63ec36357fbd"},{"kind":"inventoryScope","id":"4c347ad9-c499-38ba-af54-54e88e2f7e28"}]`

- Session : `{"scopeId":"da44e1e1-c099-34b4-a121-397ca4ed7e37","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"7d46c7a4-f137-377d-8437-5d11eeb4049d","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"66954879-6228-3c31-bf72-7b217d3c9695","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2a6f7727-71d3-3a12-96f6-4b5e2b38ce02","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"c0755c3b-6689-3d3d-8527-6c90e6c6e5d5","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"3879a968-0f8b-33bb-b62b-63ec36357fbd","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4c347ad9-c499-38ba-af54-54e88e2f7e28","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas ab92b6e7-cc1e-37e5-a195-803f0f036a1c — dossier d'écart

- Exemplaire : `456bf380-2fb4-3e38-a6e9-fa325405d9de`
- Famille privée : RETARD_INTEGRATION ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"e8ae143f-b0aa-34d0-8e60-22721aaee9c7","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"de90f37e-8a52-30de-ba56-3660cd688fba","physicalStatus":"ON_LOAN","physicalSiteId":"NORD","physicalLocationId":null,"projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Recontrôler après la fenêtre de traitement"]}`
- État du stock informatique : `{"copyId":"456bf380-2fb4-3e38-a6e9-fa325405d9de","status":"ON_LOAN","siteId":"NORD","locationId":null,"sourceEventIds":["de90f37e-8a52-30de-ba56-3660cd688fba","0c2d6cb9-97f3-327f-9dd7-3c08b7401359"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 33ccaa48-dd92-3164-b0b3-15de4a72301e | 2026-06-02T01:51:22Z | 2026-06-02T01:51:32Z | `{"operationId":"cfc17b5b-72a8-3af4-9faa-f241f06c00dc","correlationId":"3ee4d1fe-39e9-3df7-a874-ff0b2ae9d304","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"ae165385-1610-3d22-9624-707c79365ef1"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 462fb631-13fd-33d9-94db-192eac4206aa | 2026-06-03T01:51:22Z | 2026-06-03T01:51:32Z | `{"originalEventId":"33ccaa48-dd92-3164-b0b3-15de4a72301e","correlationId":"3ee4d1fe-39e9-3df7-a874-ff0b2ae9d304","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | d8f999b0-6c24-395f-bb51-ac76b4027485 | 2026-08-01T01:51:22Z | 2026-08-01T01:51:32Z | `{"operationId":"3f2140be-2c7a-3b9e-8fad-9bb3297cd60d","correlationId":"b649ad02-829f-36f0-8209-4b55a82cae9a","siteId":"CENTRE","locationId":null,"loanId":"af47c292-f464-3eff-89ea-cf3e16646ec0"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 16a59f25-3dbd-3b80-bdca-066ade44f7c3 | 2026-08-01T01:52:22Z | 2026-08-01T01:52:32Z | `{"originalEventId":"d8f999b0-6c24-395f-bb51-ac76b4027485","correlationId":"b649ad02-829f-36f0-8209-4b55a82cae9a","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 0d072960-5c5d-3496-97cd-197a457f2d53 | 2026-08-21T01:51:22Z | 2026-08-21T01:51:32Z | `{"operationId":"70ff5b21-d585-30cc-913d-9762103c5718","correlationId":"e073b8ee-2e88-3283-a6e3-c3edd5218657","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"af47c292-f464-3eff-89ea-cf3e16646ec0"}` |
| Retour reçu (`ReturnReceived`) | 9f63c926-bdcc-341e-8c2e-27a93cbd736a | 2026-08-21T01:51:52Z | 2026-08-21T01:52:02Z | `{"operationId":"b5581d9d-9444-3e67-bf3f-24ee5c041802","correlationId":"77f16fb1-a2a8-3113-b7b5-b363d086f5bb","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"af47c292-f464-3eff-89ea-cf3e16646ec0"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 60ae8842-fb03-3cdd-9fc6-63ec4d7b32c8 | 2026-08-22T01:51:22Z | 2026-08-22T01:51:32Z | `{"originalEventId":"9f63c926-bdcc-341e-8c2e-27a93cbd736a","correlationId":"77f16fb1-a2a8-3113-b7b5-b363d086f5bb","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Transfert expédié (`TransferDispatched`) | 7529ac64-26de-35d1-9a94-70917248ab12 | 2026-08-31T01:51:22Z | 2026-08-31T01:51:32Z | `{"operationId":"fea606dd-2758-3e45-95c7-3de2334abbc6","correlationId":"6b23d098-71ac-38b1-a46d-011882f14f35","siteId":"CENTRE","locationId":null,"transferId":"90de5342-629c-3701-9a2d-f0bd3e516563","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 6c74b548-123f-3a70-943a-fde3d7866c8f | 2026-08-31T01:52:22Z | 2026-08-31T01:52:32Z | `{"originalEventId":"7529ac64-26de-35d1-9a94-70917248ab12","correlationId":"6b23d098-71ac-38b1-a46d-011882f14f35","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| Transfert reçu (`TransferReceived`) | 2f3bee44-2eea-3529-b206-2f1f2378c5c7 | 2026-09-01T01:51:22Z | 2026-09-01T01:51:32Z | `{"operationId":"e754e333-1d81-380c-88be-f6570adc13a9","correlationId":"4bff0d9a-5e0b-3c39-a839-1754f8f05ecc","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","transferId":"90de5342-629c-3701-9a2d-f0bd3e516563","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 133fc9a0-2f01-38a3-b715-24ccb4948de9 | 2026-09-02T01:51:22Z | 2026-09-02T01:51:32Z | `{"originalEventId":"2f3bee44-2eea-3529-b206-2f1f2378c5c7","correlationId":"4bff0d9a-5e0b-3c39-a839-1754f8f05ecc","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | de90f37e-8a52-30de-ba56-3660cd688fba | 2026-09-30T11:51:52Z | 2026-09-30T11:52:02Z | `{"operationId":"fe5f99e9-1cbb-3fb9-b310-33c5f828318a","correlationId":"cac32776-afdc-3cbd-a9e1-585bb33ed545","siteId":"NORD","locationId":null,"loanId":"c15533d4-dec4-3c11-9950-58c05536f6c2"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | b69f5b87-0234-35a1-b8a9-72b8c221ae35 | 2026-09-30T11:52:22Z | 2026-09-30T11:52:32Z | `{"originalEventId":"de90f37e-8a52-30de-ba56-3660cd688fba","correlationId":"cac32776-afdc-3cbd-a9e1-585bb33ed545","outcome":"QUEUED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 0c2d6cb9-97f3-327f-9dd7-3c08b7401359 | 2026-09-30T11:53:22Z | 2026-09-30T11:53:32Z | `{"originalEventId":"de90f37e-8a52-30de-ba56-3660cd688fba","correlationId":"cac32776-afdc-3cbd-a9e1-585bb33ed545","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"bb353963-e8f2-311d-8223-2655efb618d7","copyId":"456bf380-2fb4-3e38-a6e9-fa325405d9de","locationId":"2cf9a5ef-0690-3b0f-9031-67da0dd9d884","scopeId":"3e8c4401-f859-3b2c-8815-dfbec50a6c47","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"456bf380-2fb4-3e38-a6e9-fa325405d9de","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"456bf380-2fb4-3e38-a6e9-fa325405d9de","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"456bf380-2fb4-3e38-a6e9-fa325405d9de","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"456bf380-2fb4-3e38-a6e9-fa325405d9de","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"456bf380-2fb4-3e38-a6e9-fa325405d9de","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"456bf380-2fb4-3e38-a6e9-fa325405d9de","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"456bf380-2fb4-3e38-a6e9-fa325405d9de","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"456bf380-2fb4-3e38-a6e9-fa325405d9de"},{"kind":"sourceEvent","id":"33ccaa48-dd92-3164-b0b3-15de4a72301e"},{"kind":"sourceEvent","id":"462fb631-13fd-33d9-94db-192eac4206aa"},{"kind":"sourceEvent","id":"d8f999b0-6c24-395f-bb51-ac76b4027485"},{"kind":"sourceEvent","id":"16a59f25-3dbd-3b80-bdca-066ade44f7c3"},{"kind":"sourceEvent","id":"0d072960-5c5d-3496-97cd-197a457f2d53"},{"kind":"sourceEvent","id":"9f63c926-bdcc-341e-8c2e-27a93cbd736a"},{"kind":"sourceEvent","id":"60ae8842-fb03-3cdd-9fc6-63ec4d7b32c8"},{"kind":"sourceEvent","id":"7529ac64-26de-35d1-9a94-70917248ab12"},{"kind":"sourceEvent","id":"6c74b548-123f-3a70-943a-fde3d7866c8f"},{"kind":"sourceEvent","id":"2f3bee44-2eea-3529-b206-2f1f2378c5c7"},{"kind":"sourceEvent","id":"133fc9a0-2f01-38a3-b715-24ccb4948de9"},{"kind":"sourceEvent","id":"de90f37e-8a52-30de-ba56-3660cd688fba"},{"kind":"sourceEvent","id":"b69f5b87-0234-35a1-b8a9-72b8c221ae35"},{"kind":"sourceEvent","id":"0c2d6cb9-97f3-327f-9dd7-3c08b7401359"},{"kind":"observation","id":"bb353963-e8f2-311d-8223-2655efb618d7"},{"kind":"inventoryScope","id":"3e8c4401-f859-3b2c-8815-dfbec50a6c47"},{"kind":"sourceAvailability","id":"456bf380-2fb4-3e38-a6e9-fa325405d9de/LOANS"},{"kind":"sourceAvailability","id":"456bf380-2fb4-3e38-a6e9-fa325405d9de/RETURNS"},{"kind":"sourceAvailability","id":"456bf380-2fb4-3e38-a6e9-fa325405d9de/RECEIPTS"},{"kind":"sourceAvailability","id":"456bf380-2fb4-3e38-a6e9-fa325405d9de/TRANSFERS"},{"kind":"sourceAvailability","id":"456bf380-2fb4-3e38-a6e9-fa325405d9de/REPAIRS"},{"kind":"sourceAvailability","id":"456bf380-2fb4-3e38-a6e9-fa325405d9de/INTEGRATIONS"},{"kind":"sourceAvailability","id":"456bf380-2fb4-3e38-a6e9-fa325405d9de/INVENTORY"}]`

- Session : `{"scopeId":"3e8c4401-f859-3b2c-8815-dfbec50a6c47","locationId":"2cf9a5ef-0690-3b0f-9031-67da0dd9d884","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

## Cas acbc9401-4a48-3f45-83d7-e76e635279de — dossier d'écart

- Exemplaire : `91275432-0616-3206-9585-6350623a3095`
- Famille privée : LOCALISATION ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"bb0fd965-0528-3169-bab0-4b24e6010a67","kind":"COUNT_UNCERTAIN","expectedValue":"00f09742-c706-31b7-a4a0-c694a087403f","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"mechanism":"Aucun déplacement ; signal de collecte non confirmé.","activePerturbations":[],"physicalStatus":"ON_SHELF","physicalSiteId":"SUD","physicalLocationId":"00f09742-c706-31b7-a4a0-c694a087403f"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"Le scan valide confirme l'emplacement attendu ; aucun écart de localisation étayé.","forbiddenClaims":["Vol","Perte définitive","Responsabilité d'un opérateur"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"91275432-0616-3206-9585-6350623a3095","status":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","sourceEventIds":["426001cc-ecf0-3266-9a8a-3876e5ef1953","b794e238-e98a-31bb-957e-d460ef889abb"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 72381b04-89c5-35d0-b594-06052b9e03d3 | 2026-06-01T13:17:19Z | 2026-06-01T13:17:29Z | `{"operationId":"f581e60d-047c-3fe5-a6be-9f670e8945d3","correlationId":"f75e5f85-bad0-3dff-9f0f-72edb93cdc2f","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"f2e8c460-3086-3ee6-b026-24d1dfe6017c"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 43e201bd-5129-3469-9b79-796d8665dbc9 | 2026-06-02T13:17:19Z | 2026-06-02T13:17:29Z | `{"originalEventId":"72381b04-89c5-35d0-b594-06052b9e03d3","correlationId":"f75e5f85-bad0-3dff-9f0f-72edb93cdc2f","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 22b38d51-1620-35d3-90f0-b90069582532 | 2026-07-31T13:17:19Z | 2026-07-31T13:17:29Z | `{"operationId":"561b0331-de59-3e70-8938-a15016b9779d","correlationId":"373cb26f-495e-391d-bc51-43d5b8ba0e20","siteId":"SUD","locationId":null,"loanId":"0d572ed4-d937-3dd9-b9cc-d4166c51a5c3"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | a23c08eb-ee89-30e3-ae84-fd7a4d913b12 | 2026-07-31T13:18:19Z | 2026-07-31T13:18:29Z | `{"originalEventId":"22b38d51-1620-35d3-90f0-b90069582532","correlationId":"373cb26f-495e-391d-bc51-43d5b8ba0e20","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 35fad535-8795-3583-a225-73dad699dae7 | 2026-08-20T13:17:19Z | 2026-08-20T13:17:29Z | `{"operationId":"6024245d-9ab9-3772-aa6d-b2c051a2ebd9","correlationId":"b839fad5-32b6-3bf3-8b95-1610158889ed","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"0d572ed4-d937-3dd9-b9cc-d4166c51a5c3"}` |
| Retour reçu (`ReturnReceived`) | 426001cc-ecf0-3266-9a8a-3876e5ef1953 | 2026-08-20T13:17:49Z | 2026-08-20T13:17:59Z | `{"operationId":"219c3d25-bc81-3ed3-9f59-a400b323acaa","correlationId":"4e3d0fde-87dd-3412-8a06-f9c8a87b358f","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"0d572ed4-d937-3dd9-b9cc-d4166c51a5c3"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | b794e238-e98a-31bb-957e-d460ef889abb | 2026-08-21T13:17:19Z | 2026-08-21T13:17:29Z | `{"originalEventId":"426001cc-ecf0-3266-9a8a-3876e5ef1953","correlationId":"4e3d0fde-87dd-3412-8a06-f9c8a87b358f","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"a33c8dbb-d8cf-3e90-9c1e-be4a49672132","copyId":"91275432-0616-3206-9585-6350623a3095","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","scopeId":"1b4f280a-3145-3607-a009-dca44e9d4181","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"91275432-0616-3206-9585-6350623a3095","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"91275432-0616-3206-9585-6350623a3095","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"91275432-0616-3206-9585-6350623a3095","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"91275432-0616-3206-9585-6350623a3095","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"91275432-0616-3206-9585-6350623a3095","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"91275432-0616-3206-9585-6350623a3095","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"91275432-0616-3206-9585-6350623a3095","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"observation","id":"a33c8dbb-d8cf-3e90-9c1e-be4a49672132"},{"kind":"systemState","id":"91275432-0616-3206-9585-6350623a3095"}]`

## Cas ad1e0a22-4aba-3c86-ad15-435cff383d1c — dossier d'écart

- Exemplaire : `0834219f-9700-337b-b0d6-a0e3e1c19287`
- Famille privée : RETARD_INTEGRATION ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"3702830f-d353-3e4d-ae9f-e253a1f6d756","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"87bece32-a47a-3479-b64c-ab1b76d58751","physicalStatus":"ON_LOAN","physicalSiteId":"CENTRE","physicalLocationId":null,"projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Recontrôler après la fenêtre de traitement"]}`
- État du stock informatique : `{"copyId":"0834219f-9700-337b-b0d6-a0e3e1c19287","status":"ON_LOAN","siteId":"CENTRE","locationId":null,"sourceEventIds":["87bece32-a47a-3479-b64c-ab1b76d58751","95f8727e-f303-3bbd-bc3d-90fe386ec0bf"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | ae7a0218-1ba1-34b4-a696-8eef75601190 | 2026-06-02T00:46:31Z | 2026-06-02T00:46:41Z | `{"operationId":"b9cb53db-5bbb-337c-858a-1f65b53511a6","correlationId":"fd5eb26d-b63a-396a-b219-897f0b9086e3","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"0544825c-f970-3124-bd16-2710fdd69582"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 98bb83d4-99a6-3d57-af47-e8491b5f8469 | 2026-06-03T00:46:31Z | 2026-06-03T00:46:41Z | `{"originalEventId":"ae7a0218-1ba1-34b4-a696-8eef75601190","correlationId":"fd5eb26d-b63a-396a-b219-897f0b9086e3","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 2399faba-c785-367d-a881-57a6230a227d | 2026-08-01T00:46:31Z | 2026-08-01T00:46:41Z | `{"operationId":"0374f854-5cbf-35bc-99fd-86beb1e998e2","correlationId":"058cc899-e9c8-3c2a-a205-8138c63a84eb","siteId":"CENTRE","locationId":null,"loanId":"fcd0fdf7-b23c-3133-9998-c0a1aa6f182c"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | a2135216-f4c7-33a4-9b69-75490eaf8900 | 2026-08-01T00:47:31Z | 2026-08-01T00:47:41Z | `{"originalEventId":"2399faba-c785-367d-a881-57a6230a227d","correlationId":"058cc899-e9c8-3c2a-a205-8138c63a84eb","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 371de2ca-1fb5-3502-a6c4-cfc3fd30b687 | 2026-08-21T00:46:31Z | 2026-08-21T00:46:41Z | `{"operationId":"ba4fa794-b8be-3098-a389-91f8eb0e2642","correlationId":"af9cd58b-0b6e-3125-877d-adac8f2d1252","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"fcd0fdf7-b23c-3133-9998-c0a1aa6f182c"}` |
| Retour reçu (`ReturnReceived`) | d2c8824d-db2a-314f-bda6-464582f20617 | 2026-08-21T00:47:01Z | 2026-08-21T00:47:11Z | `{"operationId":"7a52cfc5-1a2a-37d3-a0b1-96445aef5847","correlationId":"d182d3c8-5524-3a0a-9894-c8747e805564","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"fcd0fdf7-b23c-3133-9998-c0a1aa6f182c"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 794258e6-4550-32a9-8b23-02579281c379 | 2026-08-22T00:46:31Z | 2026-08-22T00:46:41Z | `{"originalEventId":"d2c8824d-db2a-314f-bda6-464582f20617","correlationId":"d182d3c8-5524-3a0a-9894-c8747e805564","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 87bece32-a47a-3479-b64c-ab1b76d58751 | 2026-09-30T11:50:52Z | 2026-09-30T11:51:02Z | `{"operationId":"1a106089-640e-37a4-ae11-2f7029ff3b75","correlationId":"fb28952e-a980-37cf-9c97-d83530131478","siteId":"CENTRE","locationId":null,"loanId":"9a8f60f0-2654-335c-b423-6a3f369bc92b"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 21956487-4499-3c72-89cd-e73c8eb0d2e9 | 2026-09-30T11:51:22Z | 2026-09-30T11:51:32Z | `{"originalEventId":"87bece32-a47a-3479-b64c-ab1b76d58751","correlationId":"fb28952e-a980-37cf-9c97-d83530131478","outcome":"QUEUED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 95f8727e-f303-3bbd-bc3d-90fe386ec0bf | 2026-09-30T11:52:22Z | 2026-09-30T11:52:32Z | `{"originalEventId":"87bece32-a47a-3479-b64c-ab1b76d58751","correlationId":"fb28952e-a980-37cf-9c97-d83530131478","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"ee014464-2727-3ae0-8751-18fc4146c9e4","copyId":"0834219f-9700-337b-b0d6-a0e3e1c19287","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","scopeId":"7e6a67b6-cf10-395e-9806-fd4b96472939","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"0834219f-9700-337b-b0d6-a0e3e1c19287","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0834219f-9700-337b-b0d6-a0e3e1c19287","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0834219f-9700-337b-b0d6-a0e3e1c19287","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0834219f-9700-337b-b0d6-a0e3e1c19287","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0834219f-9700-337b-b0d6-a0e3e1c19287","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0834219f-9700-337b-b0d6-a0e3e1c19287","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0834219f-9700-337b-b0d6-a0e3e1c19287","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"0834219f-9700-337b-b0d6-a0e3e1c19287"},{"kind":"sourceEvent","id":"ae7a0218-1ba1-34b4-a696-8eef75601190"},{"kind":"sourceEvent","id":"98bb83d4-99a6-3d57-af47-e8491b5f8469"},{"kind":"sourceEvent","id":"2399faba-c785-367d-a881-57a6230a227d"},{"kind":"sourceEvent","id":"a2135216-f4c7-33a4-9b69-75490eaf8900"},{"kind":"sourceEvent","id":"371de2ca-1fb5-3502-a6c4-cfc3fd30b687"},{"kind":"sourceEvent","id":"d2c8824d-db2a-314f-bda6-464582f20617"},{"kind":"sourceEvent","id":"794258e6-4550-32a9-8b23-02579281c379"},{"kind":"sourceEvent","id":"87bece32-a47a-3479-b64c-ab1b76d58751"},{"kind":"sourceEvent","id":"21956487-4499-3c72-89cd-e73c8eb0d2e9"},{"kind":"sourceEvent","id":"95f8727e-f303-3bbd-bc3d-90fe386ec0bf"},{"kind":"observation","id":"ee014464-2727-3ae0-8751-18fc4146c9e4"},{"kind":"inventoryScope","id":"7e6a67b6-cf10-395e-9806-fd4b96472939"},{"kind":"sourceAvailability","id":"0834219f-9700-337b-b0d6-a0e3e1c19287/LOANS"},{"kind":"sourceAvailability","id":"0834219f-9700-337b-b0d6-a0e3e1c19287/RETURNS"},{"kind":"sourceAvailability","id":"0834219f-9700-337b-b0d6-a0e3e1c19287/RECEIPTS"},{"kind":"sourceAvailability","id":"0834219f-9700-337b-b0d6-a0e3e1c19287/TRANSFERS"},{"kind":"sourceAvailability","id":"0834219f-9700-337b-b0d6-a0e3e1c19287/REPAIRS"},{"kind":"sourceAvailability","id":"0834219f-9700-337b-b0d6-a0e3e1c19287/INTEGRATIONS"},{"kind":"sourceAvailability","id":"0834219f-9700-337b-b0d6-a0e3e1c19287/INVENTORY"}]`

- Session : `{"scopeId":"7e6a67b6-cf10-395e-9806-fd4b96472939","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

## Cas aee172ce-8112-319a-8e0b-a7f887a5902c — dossier d'écart

- Exemplaire : `9379a5db-22e0-3db2-aaa3-5b49cb8e627f`
- Famille privée : RECEPTION_NON_INTEGREE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"195f5bd9-40ea-36e0-9224-8ecea4857bbc","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"9a1fddd9-067a-34a1-ab0a-e751de4810a2","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"125bed65-7385-3595-a653-902580e9539f","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"9379a5db-22e0-3db2-aaa3-5b49cb8e627f","status":"RECEIVING","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","sourceEventIds":["9a1fddd9-067a-34a1-ab0a-e751de4810a2","bc31b3fc-75d3-3471-beb3-aa9c7f4a834c"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 9a1fddd9-067a-34a1-ab0a-e751de4810a2 | 2026-09-27T11:16:32Z | 2026-09-27T11:16:42Z | `{"operationId":"a23bf56f-8520-3db1-8468-11ed8192872e","correlationId":"f0026fa6-2ce4-3c70-ab36-2cbb923501d8","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"8733694d-ed42-3aa8-972d-c77929ef434e"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 0a7d4d8f-6c8e-30fc-99b5-acd89aeba8e8 | 2026-09-27T11:17:02Z | 2026-09-27T11:17:12Z | `{"originalEventId":"9a1fddd9-067a-34a1-ab0a-e751de4810a2","correlationId":"f0026fa6-2ce4-3c70-ab36-2cbb923501d8","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | bc31b3fc-75d3-3471-beb3-aa9c7f4a834c | 2026-09-27T11:18:02Z | 2026-09-27T11:18:12Z | `{"originalEventId":"9a1fddd9-067a-34a1-ab0a-e751de4810a2","correlationId":"f0026fa6-2ce4-3c70-ab36-2cbb923501d8","outcome":"APPLIED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"abf2e47a-2579-3b36-a343-bf9f537fe547","copyId":"9379a5db-22e0-3db2-aaa3-5b49cb8e627f","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","scopeId":"fda37ead-622e-3ebd-aacb-f43e94d5970e","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`
- `{"observationId":"fec54714-1850-3912-8617-e0dece124e8d","copyId":"9379a5db-22e0-3db2-aaa3-5b49cb8e627f","locationId":"125bed65-7385-3595-a653-902580e9539f","scopeId":"f2059a60-e250-3744-a6bc-f032a43a89a7","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"9379a5db-22e0-3db2-aaa3-5b49cb8e627f","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9379a5db-22e0-3db2-aaa3-5b49cb8e627f","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9379a5db-22e0-3db2-aaa3-5b49cb8e627f","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9379a5db-22e0-3db2-aaa3-5b49cb8e627f","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9379a5db-22e0-3db2-aaa3-5b49cb8e627f","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9379a5db-22e0-3db2-aaa3-5b49cb8e627f","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9379a5db-22e0-3db2-aaa3-5b49cb8e627f","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"9379a5db-22e0-3db2-aaa3-5b49cb8e627f"},{"kind":"sourceEvent","id":"9a1fddd9-067a-34a1-ab0a-e751de4810a2"},{"kind":"sourceEvent","id":"0a7d4d8f-6c8e-30fc-99b5-acd89aeba8e8"},{"kind":"sourceEvent","id":"bc31b3fc-75d3-3471-beb3-aa9c7f4a834c"},{"kind":"observation","id":"fec54714-1850-3912-8617-e0dece124e8d"},{"kind":"inventoryScope","id":"f2059a60-e250-3744-a6bc-f032a43a89a7"},{"kind":"observation","id":"abf2e47a-2579-3b36-a343-bf9f537fe547"},{"kind":"inventoryScope","id":"fda37ead-622e-3ebd-aacb-f43e94d5970e"},{"kind":"sourceAvailability","id":"9379a5db-22e0-3db2-aaa3-5b49cb8e627f/LOANS"},{"kind":"sourceAvailability","id":"9379a5db-22e0-3db2-aaa3-5b49cb8e627f/RETURNS"},{"kind":"sourceAvailability","id":"9379a5db-22e0-3db2-aaa3-5b49cb8e627f/RECEIPTS"},{"kind":"sourceAvailability","id":"9379a5db-22e0-3db2-aaa3-5b49cb8e627f/TRANSFERS"},{"kind":"sourceAvailability","id":"9379a5db-22e0-3db2-aaa3-5b49cb8e627f/REPAIRS"},{"kind":"sourceAvailability","id":"9379a5db-22e0-3db2-aaa3-5b49cb8e627f/INTEGRATIONS"},{"kind":"sourceAvailability","id":"9379a5db-22e0-3db2-aaa3-5b49cb8e627f/INVENTORY"},{"kind":"inventoryScope","id":"78b159f5-01d9-39e9-96e5-dddc8e0578c1"},{"kind":"inventoryScope","id":"aac4255b-e902-3e7e-b8f4-61a0508f0565"},{"kind":"inventoryScope","id":"a0f4b630-abea-33f6-9af9-eb48c395042e"}]`

- Session : `{"scopeId":"f2059a60-e250-3744-a6bc-f032a43a89a7","locationId":"125bed65-7385-3595-a653-902580e9539f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"fda37ead-622e-3ebd-aacb-f43e94d5970e","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"78b159f5-01d9-39e9-96e5-dddc8e0578c1","locationId":"125bed65-7385-3595-a653-902580e9539f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"aac4255b-e902-3e7e-b8f4-61a0508f0565","locationId":"125bed65-7385-3595-a653-902580e9539f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"a0f4b630-abea-33f6-9af9-eb48c395042e","locationId":"125bed65-7385-3595-a653-902580e9539f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas b467d832-016f-3170-a1dc-05677dfe56c5 — dossier d'écart

- Exemplaire : `8639bb28-002b-3313-a124-9d9f83e195bc`
- Famille privée : REPARATION_NON_PROJETEE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"28544328-3956-3073-a1bf-b2dcc3954d2b","kind":"STATUS_MISMATCH","expectedValue":"ON_SHELF","observedValue":"IN_REPAIR"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"fcc68997-9119-3eb3-8927-154a51def93b","physicalStatus":"IN_REPAIR","physicalSiteId":"NORD","physicalLocationId":"19124ccb-fb37-348d-81ff-686cdabbabb0","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"8639bb28-002b-3313-a124-9d9f83e195bc","status":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","sourceEventIds":["82f1c1a9-dc69-37c8-b32e-1809730419a5","aa3e4353-1483-3b62-8616-bf7e1f1ad4c8"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 444184cd-948d-3b54-ad95-196fc87db28d | 2026-06-01T23:02:55Z | 2026-06-01T23:03:05Z | `{"operationId":"626cb9fa-af42-3ec1-aac3-9650d260e2d2","correlationId":"b6186d3f-f841-3a15-9e1a-1fe965ba6075","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"1528177d-5dda-3989-bb12-d0bc6610f1a4"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 3c2c1f57-648e-3f7d-88ae-b60f2d91ca3c | 2026-06-02T23:02:55Z | 2026-06-02T23:03:05Z | `{"originalEventId":"444184cd-948d-3b54-ad95-196fc87db28d","correlationId":"b6186d3f-f841-3a15-9e1a-1fe965ba6075","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | c3790458-a89a-3e82-86d6-c06dae8a7e15 | 2026-07-31T23:02:55Z | 2026-07-31T23:03:05Z | `{"operationId":"1f48588c-f43a-3789-a915-572209b9f132","correlationId":"9029b3d3-b011-32dd-a666-e16180d416d7","siteId":"NORD","locationId":null,"loanId":"cfa54a3f-29f6-37e9-bb8b-98c3a6b4d522"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 09246a43-f18c-324c-9c50-ae3de7859ff7 | 2026-07-31T23:03:55Z | 2026-07-31T23:04:05Z | `{"originalEventId":"c3790458-a89a-3e82-86d6-c06dae8a7e15","correlationId":"9029b3d3-b011-32dd-a666-e16180d416d7","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 05744835-c21f-376d-bedf-ed1da7cdfc3d | 2026-08-20T23:02:55Z | 2026-08-20T23:03:05Z | `{"operationId":"6697f00f-415a-3f16-ac64-68b9d0f5dfef","correlationId":"dde15572-5efe-34d6-b4dd-2032b75bac40","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"cfa54a3f-29f6-37e9-bb8b-98c3a6b4d522"}` |
| Retour reçu (`ReturnReceived`) | 82f1c1a9-dc69-37c8-b32e-1809730419a5 | 2026-08-20T23:03:25Z | 2026-08-20T23:03:35Z | `{"operationId":"f75b643d-b801-38e5-9159-f1391f7181e5","correlationId":"7a3f6e58-ae2b-38a6-b180-e5b3bb00932a","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"cfa54a3f-29f6-37e9-bb8b-98c3a6b4d522"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | aa3e4353-1483-3b62-8616-bf7e1f1ad4c8 | 2026-08-21T23:02:55Z | 2026-08-21T23:03:05Z | `{"originalEventId":"82f1c1a9-dc69-37c8-b32e-1809730419a5","correlationId":"7a3f6e58-ae2b-38a6-b180-e5b3bb00932a","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Réparation engagée (`RepairStarted`) | fcc68997-9119-3eb3-8927-154a51def93b | 2026-09-27T07:30:38Z | 2026-09-27T07:30:48Z | `{"operationId":"63138fb9-9c24-3dc0-8902-8905cb5665ae","correlationId":"3d28660c-77d4-384d-b63f-672a7f66ed54","siteId":"NORD","locationId":"19124ccb-fb37-348d-81ff-686cdabbabb0","repairId":"dd01b062-84c1-342c-9efd-605b619662b6"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 0d7ff76f-1396-3fb3-a4c2-88182c452663 | 2026-09-27T07:31:08Z | 2026-09-27T07:31:18Z | `{"originalEventId":"fcc68997-9119-3eb3-8927-154a51def93b","correlationId":"3d28660c-77d4-384d-b63f-672a7f66ed54","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"NORD","locationId":"19124ccb-fb37-348d-81ff-686cdabbabb0","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"14e0e85e-b08b-37e3-b804-fc7fcc24e752","copyId":"8639bb28-002b-3313-a124-9d9f83e195bc","locationId":"19124ccb-fb37-348d-81ff-686cdabbabb0","scopeId":"b5f9879a-f198-3021-8fb9-fac595373cee","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"8639bb28-002b-3313-a124-9d9f83e195bc","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8639bb28-002b-3313-a124-9d9f83e195bc","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8639bb28-002b-3313-a124-9d9f83e195bc","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8639bb28-002b-3313-a124-9d9f83e195bc","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8639bb28-002b-3313-a124-9d9f83e195bc","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8639bb28-002b-3313-a124-9d9f83e195bc","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8639bb28-002b-3313-a124-9d9f83e195bc","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"8639bb28-002b-3313-a124-9d9f83e195bc"},{"kind":"sourceEvent","id":"444184cd-948d-3b54-ad95-196fc87db28d"},{"kind":"sourceEvent","id":"3c2c1f57-648e-3f7d-88ae-b60f2d91ca3c"},{"kind":"sourceEvent","id":"c3790458-a89a-3e82-86d6-c06dae8a7e15"},{"kind":"sourceEvent","id":"09246a43-f18c-324c-9c50-ae3de7859ff7"},{"kind":"sourceEvent","id":"05744835-c21f-376d-bedf-ed1da7cdfc3d"},{"kind":"sourceEvent","id":"82f1c1a9-dc69-37c8-b32e-1809730419a5"},{"kind":"sourceEvent","id":"aa3e4353-1483-3b62-8616-bf7e1f1ad4c8"},{"kind":"sourceEvent","id":"fcc68997-9119-3eb3-8927-154a51def93b"},{"kind":"sourceEvent","id":"0d7ff76f-1396-3fb3-a4c2-88182c452663"},{"kind":"observation","id":"14e0e85e-b08b-37e3-b804-fc7fcc24e752"},{"kind":"inventoryScope","id":"b5f9879a-f198-3021-8fb9-fac595373cee"},{"kind":"sourceAvailability","id":"8639bb28-002b-3313-a124-9d9f83e195bc/LOANS"},{"kind":"sourceAvailability","id":"8639bb28-002b-3313-a124-9d9f83e195bc/RETURNS"},{"kind":"sourceAvailability","id":"8639bb28-002b-3313-a124-9d9f83e195bc/RECEIPTS"},{"kind":"sourceAvailability","id":"8639bb28-002b-3313-a124-9d9f83e195bc/TRANSFERS"},{"kind":"sourceAvailability","id":"8639bb28-002b-3313-a124-9d9f83e195bc/REPAIRS"},{"kind":"sourceAvailability","id":"8639bb28-002b-3313-a124-9d9f83e195bc/INTEGRATIONS"},{"kind":"sourceAvailability","id":"8639bb28-002b-3313-a124-9d9f83e195bc/INVENTORY"},{"kind":"inventoryScope","id":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5"},{"kind":"inventoryScope","id":"eb1ce5f1-7397-3b45-9385-ef328e09fec8"}]`

- Session : `{"scopeId":"b5f9879a-f198-3021-8fb9-fac595373cee","locationId":"19124ccb-fb37-348d-81ff-686cdabbabb0","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"eb1ce5f1-7397-3b45-9385-ef328e09fec8","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas b6028cf8-ad5b-3c85-b768-a1e32551d0e1 — dossier d'écart

- Exemplaire : `df77b0ff-c320-3aa4-81b8-a4c3735a036a`
- Famille privée : ABSENCE_INEXPLIQUEE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"fe4537f5-2322-3bd6-a111-524c50d80ec0","kind":"MISSING","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["UNOBSERVED_MOVE"],"mechanism":"Exemplaire déplacé hors du périmètre instrumenté ; la destination n'est pas exposée et aucune perte n'est simulée.","physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":null,"evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"L'absence de lecture ne démontre ni la destination, ni une perte, ni un vol ; données insuffisantes pour attribuer une cause.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État du stock informatique : `{"copyId":"df77b0ff-c320-3aa4-81b8-a4c3735a036a","status":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","sourceEventIds":["549d3b96-c533-3e14-bce9-1df9d9935ddf","e5d16691-6eb9-336c-a631-d11aaf214630"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 6d0848be-90d3-310f-a1f0-f11f331556dd | 2026-06-01T18:38:39Z | 2026-06-01T18:38:49Z | `{"operationId":"7463a0b6-2af0-3597-9f27-f09ba3fc042f","correlationId":"c9b2e3b7-895b-3ce9-a641-2fd72e772355","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"ab8cd5f0-67f1-3b88-9a3a-00ee2ef36eec"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 09bc164c-080c-3667-a514-5b8f16b94798 | 2026-06-02T18:38:39Z | 2026-06-02T18:38:49Z | `{"originalEventId":"6d0848be-90d3-310f-a1f0-f11f331556dd","correlationId":"c9b2e3b7-895b-3ce9-a641-2fd72e772355","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | b72ce027-902a-3263-a3f4-a88f750656d2 | 2026-07-31T18:38:39Z | 2026-07-31T18:38:49Z | `{"operationId":"dff3c834-c402-3e4d-9809-6238173bbe4c","correlationId":"703e6919-e306-3298-b0ea-36b34a92367c","siteId":"NORD","locationId":null,"loanId":"772603db-80e8-3241-8e9f-05c47f0ae153"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | ae11a31c-f520-34cf-8fde-cd39e5b71c45 | 2026-07-31T18:39:39Z | 2026-07-31T18:39:49Z | `{"originalEventId":"b72ce027-902a-3263-a3f4-a88f750656d2","correlationId":"703e6919-e306-3298-b0ea-36b34a92367c","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | dda27a88-7224-3e57-8607-3a03bc205240 | 2026-08-20T18:38:39Z | 2026-08-20T18:38:49Z | `{"operationId":"ff9598b0-3b6d-3cc9-a633-a09eaaf390b4","correlationId":"db58f7cf-249a-3169-a709-ed3a210a67d0","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"772603db-80e8-3241-8e9f-05c47f0ae153"}` |
| Retour reçu (`ReturnReceived`) | 549d3b96-c533-3e14-bce9-1df9d9935ddf | 2026-08-20T18:39:09Z | 2026-08-20T18:39:19Z | `{"operationId":"2351bd1f-35e3-359f-b7c5-7bea515a26a1","correlationId":"a81acdf8-5a0e-3254-a456-23d34a0cadeb","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"772603db-80e8-3241-8e9f-05c47f0ae153"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | e5d16691-6eb9-336c-a631-d11aaf214630 | 2026-08-21T18:38:39Z | 2026-08-21T18:38:49Z | `{"originalEventId":"549d3b96-c533-3e14-bce9-1df9d9935ddf","correlationId":"a81acdf8-5a0e-3254-a456-23d34a0cadeb","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"df77b0ff-c320-3aa4-81b8-a4c3735a036a","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"df77b0ff-c320-3aa4-81b8-a4c3735a036a","source":"TRANSFERS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"df77b0ff-c320-3aa4-81b8-a4c3735a036a","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"df77b0ff-c320-3aa4-81b8-a4c3735a036a","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"df77b0ff-c320-3aa4-81b8-a4c3735a036a","source":"LOANS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"df77b0ff-c320-3aa4-81b8-a4c3735a036a","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"df77b0ff-c320-3aa4-81b8-a4c3735a036a","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**
- `{"noteId":"37cef181-face-35d9-ae66-4c7190f03a00","copyId":"df77b0ff-c320-3aa4-81b8-a4c3735a036a","text":"Le passage au rayon n'a pas permis de retrouver cet exemplaire ; contrôle complémentaire demandé.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"df77b0ff-c320-3aa4-81b8-a4c3735a036a"},{"kind":"sourceEvent","id":"6d0848be-90d3-310f-a1f0-f11f331556dd"},{"kind":"sourceEvent","id":"09bc164c-080c-3667-a514-5b8f16b94798"},{"kind":"sourceEvent","id":"b72ce027-902a-3263-a3f4-a88f750656d2"},{"kind":"sourceEvent","id":"ae11a31c-f520-34cf-8fde-cd39e5b71c45"},{"kind":"sourceEvent","id":"dda27a88-7224-3e57-8607-3a03bc205240"},{"kind":"sourceEvent","id":"549d3b96-c533-3e14-bce9-1df9d9935ddf"},{"kind":"sourceEvent","id":"e5d16691-6eb9-336c-a631-d11aaf214630"},{"kind":"sourceAvailability","id":"df77b0ff-c320-3aa4-81b8-a4c3735a036a/LOANS"},{"kind":"sourceAvailability","id":"df77b0ff-c320-3aa4-81b8-a4c3735a036a/RETURNS"},{"kind":"sourceAvailability","id":"df77b0ff-c320-3aa4-81b8-a4c3735a036a/RECEIPTS"},{"kind":"sourceAvailability","id":"df77b0ff-c320-3aa4-81b8-a4c3735a036a/TRANSFERS"},{"kind":"sourceAvailability","id":"df77b0ff-c320-3aa4-81b8-a4c3735a036a/REPAIRS"},{"kind":"sourceAvailability","id":"df77b0ff-c320-3aa4-81b8-a4c3735a036a/INTEGRATIONS"},{"kind":"sourceAvailability","id":"df77b0ff-c320-3aa4-81b8-a4c3735a036a/INVENTORY"},{"kind":"operatorNote","id":"37cef181-face-35d9-ae66-4c7190f03a00"},{"kind":"inventoryScope","id":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5"},{"kind":"inventoryScope","id":"eb1ce5f1-7397-3b45-9385-ef328e09fec8"},{"kind":"inventoryScope","id":"d0c55bda-319d-3537-857a-5d135f796cab"},{"kind":"inventoryScope","id":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb"},{"kind":"inventoryScope","id":"d9842c03-ba37-324b-b71b-e58ac7147d4c"},{"kind":"inventoryScope","id":"07bae854-b205-39bf-a23e-9eaa22063b92"},{"kind":"inventoryScope","id":"9de706b4-d360-3282-b440-c440861c039c"},{"kind":"inventoryScope","id":"b98af8e5-7297-3dc5-a947-eb8fe8af159f"},{"kind":"inventoryScope","id":"7eb96d9b-e927-305a-b369-01d6b239f3db"},{"kind":"inventoryScope","id":"7eb96d9b-e927-305a-b369-01d6b239f3db"}]`

- Session : `{"scopeId":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"eb1ce5f1-7397-3b45-9385-ef328e09fec8","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"d0c55bda-319d-3537-857a-5d135f796cab","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"d9842c03-ba37-324b-b71b-e58ac7147d4c","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"07bae854-b205-39bf-a23e-9eaa22063b92","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"9de706b4-d360-3282-b440-c440861c039c","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b98af8e5-7297-3dc5-a947-eb8fe8af159f","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"7eb96d9b-e927-305a-b369-01d6b239f3db","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"7eb96d9b-e927-305a-b369-01d6b239f3db","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas bbfc9e33-e9b1-3940-ba0a-5697a67007ad — dossier d'écart

- Exemplaire : `be7cd84b-0b39-338a-b23d-35c709ab1fa2`
- Famille privée : INVENTAIRE_INCOMPLET ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"75e2d88f-9aec-320d-b50b-762b6a83a85e","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":"1"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"mechanism":"Exemplaire resté au rayon, lecture de contrôle réussie.","physicalStatus":"ON_SHELF","physicalSiteId":"CENTRE","physicalLocationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"Le scan valide au rayon attendu réfute l'absence courante.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"be7cd84b-0b39-338a-b23d-35c709ab1fa2","status":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","sourceEventIds":["d5c5948f-5a33-3821-9bcd-f73743cc702e","eb09d426-2118-3f05-a81e-3ac7462b9ba7"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 4226d93f-4dd5-32c7-a9de-e2c668772160 | 2026-06-02T11:48:59Z | 2026-06-02T11:49:09Z | `{"operationId":"dc0f5d40-cd89-3177-b0e2-9a2bc3953375","correlationId":"47c21d94-ed33-392f-a707-8911d4db48b8","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"684d93d5-751f-3ef3-8403-a34a37f3aa95"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 48538b45-44ea-3e86-add9-3c938edb1e88 | 2026-06-03T11:48:59Z | 2026-06-03T11:49:09Z | `{"originalEventId":"4226d93f-4dd5-32c7-a9de-e2c668772160","correlationId":"47c21d94-ed33-392f-a707-8911d4db48b8","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 54eeadd6-cb5d-3cd1-a2dc-e9f4ebe58461 | 2026-08-01T11:48:59Z | 2026-08-01T11:49:09Z | `{"operationId":"750c7e73-1f4b-3858-a4e6-72e7aee4eade","correlationId":"ccf51241-f899-3031-bb3a-c4a91e6664ef","siteId":"CENTRE","locationId":null,"loanId":"9feefc57-b1a4-341a-8c6e-5fa266b5974f"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 8fd8c011-bbac-36c6-991a-aaaf4e169161 | 2026-08-01T11:49:59Z | 2026-08-01T11:50:09Z | `{"originalEventId":"54eeadd6-cb5d-3cd1-a2dc-e9f4ebe58461","correlationId":"ccf51241-f899-3031-bb3a-c4a91e6664ef","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 78c65b09-5668-3ee4-9426-ca88bade7ef1 | 2026-08-21T11:48:59Z | 2026-08-21T11:49:09Z | `{"operationId":"a96670b6-3748-3b00-b6fa-2a5f40760d1c","correlationId":"edc342d3-84ba-3dcb-bb73-e913dde07306","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"9feefc57-b1a4-341a-8c6e-5fa266b5974f"}` |
| Retour reçu (`ReturnReceived`) | d5c5948f-5a33-3821-9bcd-f73743cc702e | 2026-08-21T11:49:29Z | 2026-08-21T11:49:39Z | `{"operationId":"ca7bf04c-2829-3244-8372-d63f612962d0","correlationId":"caa7319e-425c-3097-b3c1-478f9f39dbef","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"9feefc57-b1a4-341a-8c6e-5fa266b5974f"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | eb09d426-2118-3f05-a81e-3ac7462b9ba7 | 2026-08-22T11:48:59Z | 2026-08-22T11:49:09Z | `{"originalEventId":"d5c5948f-5a33-3821-9bcd-f73743cc702e","correlationId":"caa7319e-425c-3097-b3c1-478f9f39dbef","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"f52deecb-db7d-3e91-af85-d362e76554a0","copyId":"be7cd84b-0b39-338a-b23d-35c709ab1fa2","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","scopeId":"66954879-6228-3c31-bf72-7b217d3c9695","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"be7cd84b-0b39-338a-b23d-35c709ab1fa2","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"be7cd84b-0b39-338a-b23d-35c709ab1fa2","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"be7cd84b-0b39-338a-b23d-35c709ab1fa2","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"be7cd84b-0b39-338a-b23d-35c709ab1fa2","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"be7cd84b-0b39-338a-b23d-35c709ab1fa2","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"be7cd84b-0b39-338a-b23d-35c709ab1fa2","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"be7cd84b-0b39-338a-b23d-35c709ab1fa2","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**
- `{"noteId":"5e290171-5e6a-35a8-800d-7dd2057df38a","copyId":"be7cd84b-0b39-338a-b23d-35c709ab1fa2","text":"Nouvelle lecture du code-barres effectuée dans le rayon affecté.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"be7cd84b-0b39-338a-b23d-35c709ab1fa2"},{"kind":"sourceEvent","id":"4226d93f-4dd5-32c7-a9de-e2c668772160"},{"kind":"sourceEvent","id":"48538b45-44ea-3e86-add9-3c938edb1e88"},{"kind":"sourceEvent","id":"54eeadd6-cb5d-3cd1-a2dc-e9f4ebe58461"},{"kind":"sourceEvent","id":"8fd8c011-bbac-36c6-991a-aaaf4e169161"},{"kind":"sourceEvent","id":"78c65b09-5668-3ee4-9426-ca88bade7ef1"},{"kind":"sourceEvent","id":"d5c5948f-5a33-3821-9bcd-f73743cc702e"},{"kind":"sourceEvent","id":"eb09d426-2118-3f05-a81e-3ac7462b9ba7"},{"kind":"observation","id":"f52deecb-db7d-3e91-af85-d362e76554a0"},{"kind":"inventoryScope","id":"66954879-6228-3c31-bf72-7b217d3c9695"},{"kind":"sourceAvailability","id":"be7cd84b-0b39-338a-b23d-35c709ab1fa2/LOANS"},{"kind":"sourceAvailability","id":"be7cd84b-0b39-338a-b23d-35c709ab1fa2/RETURNS"},{"kind":"sourceAvailability","id":"be7cd84b-0b39-338a-b23d-35c709ab1fa2/RECEIPTS"},{"kind":"sourceAvailability","id":"be7cd84b-0b39-338a-b23d-35c709ab1fa2/TRANSFERS"},{"kind":"sourceAvailability","id":"be7cd84b-0b39-338a-b23d-35c709ab1fa2/REPAIRS"},{"kind":"sourceAvailability","id":"be7cd84b-0b39-338a-b23d-35c709ab1fa2/INTEGRATIONS"},{"kind":"sourceAvailability","id":"be7cd84b-0b39-338a-b23d-35c709ab1fa2/INVENTORY"},{"kind":"operatorNote","id":"5e290171-5e6a-35a8-800d-7dd2057df38a"},{"kind":"inventoryScope","id":"7d46c7a4-f137-377d-8437-5d11eeb4049d"},{"kind":"inventoryScope","id":"66954879-6228-3c31-bf72-7b217d3c9695"}]`

- Session : `{"scopeId":"66954879-6228-3c31-bf72-7b217d3c9695","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"7d46c7a4-f137-377d-8437-5d11eeb4049d","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"66954879-6228-3c31-bf72-7b217d3c9695","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas bcd5a1ff-d29b-33d1-a39e-102147917483 — dossier d'écart

- Exemplaire : `e599a8b6-5423-345d-a228-7a1c548fbde1`
- Famille privée : INVENTAIRE_INCOMPLET ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"cd65e3b6-dd89-3a15-be73-b5ff721fb486","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["COLLECTION_INTERRUPTED"],"mechanism":"Interruption de collecte ; exemplaire resté au rayon.","physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","evidenceMask":"Collecte indisponible"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"L'absence de lecture ne démontre ni la destination, ni une perte, ni un vol ; données insuffisantes pour attribuer une cause.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État du stock informatique : `{"copyId":"e599a8b6-5423-345d-a228-7a1c548fbde1","status":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","sourceEventIds":["527620f7-bc1b-33bb-8a32-d3a13bdb14a1","730bf23b-c2ba-3ec9-87d6-01eb384514c2"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | cb231b86-60a3-398c-9d6b-dea2f021a702 | 2026-06-01T21:48:10Z | 2026-06-01T21:48:20Z | `{"operationId":"544bf1a6-9a89-3ca0-a62a-2268138bb094","correlationId":"c9c5d60e-0b19-3c23-9335-df24d6f6005b","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"f9a18b19-00ef-33ef-94df-b603a4780642"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 7f671d73-946e-33c2-8f9e-7cba51effb2d | 2026-06-02T21:48:10Z | 2026-06-02T21:48:20Z | `{"originalEventId":"cb231b86-60a3-398c-9d6b-dea2f021a702","correlationId":"c9c5d60e-0b19-3c23-9335-df24d6f6005b","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | 4d81d7ba-ddb6-3014-b576-e20e053b21af | 2026-07-31T21:48:10Z | 2026-07-31T21:48:20Z | `{"operationId":"e9d2b8d5-e210-37bb-9463-44d8f45d456f","correlationId":"b60a8504-0255-305b-8392-ed364f129ef7","siteId":"NORD","locationId":null,"loanId":"84fa0c89-d684-3ecd-a60a-402587e9c64d"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | e5fd9048-caf2-3076-b1e1-211fb8eeef44 | 2026-07-31T21:49:10Z | 2026-07-31T21:49:20Z | `{"originalEventId":"4d81d7ba-ddb6-3014-b576-e20e053b21af","correlationId":"b60a8504-0255-305b-8392-ed364f129ef7","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | bf2de113-bf56-37a7-bb6b-1d48eb0dbf47 | 2026-08-20T21:48:10Z | 2026-08-20T21:48:20Z | `{"operationId":"3c47baf7-f593-3d18-8de2-9971c02ba839","correlationId":"977932fd-cfb5-3812-be7f-d7102e09c6f3","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"84fa0c89-d684-3ecd-a60a-402587e9c64d"}` |
| Retour reçu (`ReturnReceived`) | 527620f7-bc1b-33bb-8a32-d3a13bdb14a1 | 2026-08-20T21:48:40Z | 2026-08-20T21:48:50Z | `{"operationId":"ad9d6d2f-eb77-3c9e-8d45-1556475dfe7c","correlationId":"424f7b6a-528a-302f-a075-c58166f2a2a2","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"84fa0c89-d684-3ecd-a60a-402587e9c64d"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 730bf23b-c2ba-3ec9-87d6-01eb384514c2 | 2026-08-21T21:48:10Z | 2026-08-21T21:48:20Z | `{"originalEventId":"527620f7-bc1b-33bb-8a32-d3a13bdb14a1","correlationId":"424f7b6a-528a-302f-a075-c58166f2a2a2","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"e599a8b6-5423-345d-a228-7a1c548fbde1","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e599a8b6-5423-345d-a228-7a1c548fbde1","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e599a8b6-5423-345d-a228-7a1c548fbde1","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e599a8b6-5423-345d-a228-7a1c548fbde1","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e599a8b6-5423-345d-a228-7a1c548fbde1","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e599a8b6-5423-345d-a228-7a1c548fbde1","source":"INVENTORY","status":"UNAVAILABLE","completeFrom":null,"completeTo":null}`
- `{"copyId":"e599a8b6-5423-345d-a228-7a1c548fbde1","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**
- `{"noteId":"6ea599fa-14e8-3c8e-9008-12ca4ab54a43","copyId":"e599a8b6-5423-345d-a228-7a1c548fbde1","text":"Le terminal de collecte a cessé de répondre pendant le passage ; aucune conclusion sur les exemplaires non lus.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"e599a8b6-5423-345d-a228-7a1c548fbde1"},{"kind":"sourceEvent","id":"cb231b86-60a3-398c-9d6b-dea2f021a702"},{"kind":"sourceEvent","id":"7f671d73-946e-33c2-8f9e-7cba51effb2d"},{"kind":"sourceEvent","id":"4d81d7ba-ddb6-3014-b576-e20e053b21af"},{"kind":"sourceEvent","id":"e5fd9048-caf2-3076-b1e1-211fb8eeef44"},{"kind":"sourceEvent","id":"bf2de113-bf56-37a7-bb6b-1d48eb0dbf47"},{"kind":"sourceEvent","id":"527620f7-bc1b-33bb-8a32-d3a13bdb14a1"},{"kind":"sourceEvent","id":"730bf23b-c2ba-3ec9-87d6-01eb384514c2"},{"kind":"sourceAvailability","id":"e599a8b6-5423-345d-a228-7a1c548fbde1/LOANS"},{"kind":"sourceAvailability","id":"e599a8b6-5423-345d-a228-7a1c548fbde1/RETURNS"},{"kind":"sourceAvailability","id":"e599a8b6-5423-345d-a228-7a1c548fbde1/RECEIPTS"},{"kind":"sourceAvailability","id":"e599a8b6-5423-345d-a228-7a1c548fbde1/TRANSFERS"},{"kind":"sourceAvailability","id":"e599a8b6-5423-345d-a228-7a1c548fbde1/REPAIRS"},{"kind":"sourceAvailability","id":"e599a8b6-5423-345d-a228-7a1c548fbde1/INTEGRATIONS"},{"kind":"sourceAvailability","id":"e599a8b6-5423-345d-a228-7a1c548fbde1/INVENTORY"},{"kind":"operatorNote","id":"6ea599fa-14e8-3c8e-9008-12ca4ab54a43"},{"kind":"inventoryScope","id":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5"},{"kind":"inventoryScope","id":"eb1ce5f1-7397-3b45-9385-ef328e09fec8"},{"kind":"inventoryScope","id":"d0c55bda-319d-3537-857a-5d135f796cab"},{"kind":"inventoryScope","id":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb"},{"kind":"inventoryScope","id":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb"}]`

- Session : `{"scopeId":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"eb1ce5f1-7397-3b45-9385-ef328e09fec8","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"d0c55bda-319d-3537-857a-5d135f796cab","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas be1b619b-a687-37be-a7f8-cd98541c0499 — dossier d'écart

- Exemplaire : `d29f1c94-3a40-3c59-98a5-a1c429feb744`
- Famille privée : RETARD_INTEGRATION ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"83368183-ceb8-3241-aa1b-0d9743ea4c98","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["QUEUE_PENDING"],"hiddenSourceEvents":[{"sourceEventId":"3ffb8b79-0a6f-3811-b133-b18b69e4be6f","copyId":"d29f1c94-3a40-3c59-98a5-a1c429feb744","eventType":"IntegrationProcessed","occurredAt":"2026-09-30T11:53:25Z","recordedAt":"2026-09-30T11:53:35Z","payload":{"originalEventId":"55d0d7cc-16d8-3cd6-af3f-24fe46f1c058","correlationId":"407437f1-b872-3218-ac28-7e594320fbc9","outcome":"QUEUED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1}}],"mechanism":"Événement métier en attente normale dans la fenêtre contractuelle de 900 secondes.","businessEventId":"55d0d7cc-16d8-3cd6-af3f-24fe46f1c058","physicalStatus":"ON_LOAN","physicalSiteId":"NORD","physicalLocationId":null,"projectionPerturbation":"QUEUED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État du stock informatique : `{"copyId":"d29f1c94-3a40-3c59-98a5-a1c429feb744","status":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","sourceEventIds":["0cf27a8a-1cf6-34c4-ad4f-8206e2898079","7d0c6e46-1e0e-3f86-aaee-adcd99ce6661"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | f89972c0-bcf6-3a8e-ad62-aec3be747aab | 2026-06-01T17:32:32Z | 2026-06-01T17:32:42Z | `{"operationId":"dd2d1536-c8c3-3a01-874b-9933cba8a4f6","correlationId":"9c32a698-0c6a-3389-957c-9d4bcc0ec647","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"413b3374-0d24-37a4-bd06-4ff5fc75f548"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | e16d7677-be6f-37a5-a3ec-2986c743455c | 2026-06-02T17:32:32Z | 2026-06-02T17:32:42Z | `{"originalEventId":"f89972c0-bcf6-3a8e-ad62-aec3be747aab","correlationId":"9c32a698-0c6a-3389-957c-9d4bcc0ec647","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | 6315fdef-5ec3-34da-9f81-3f23f4747f8a | 2026-07-31T17:32:32Z | 2026-07-31T17:32:42Z | `{"operationId":"ec108fc7-d2fd-36e5-9464-76382d9a0da1","correlationId":"6f68b9a6-fc50-33f6-a6d1-e75151a73190","siteId":"NORD","locationId":null,"loanId":"eac2b4ec-1a36-3cad-b53e-bdec4bcb4c7c"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 8fd70f87-91d7-341d-9c89-2eb11ebfaf93 | 2026-07-31T17:33:32Z | 2026-07-31T17:33:42Z | `{"originalEventId":"6315fdef-5ec3-34da-9f81-3f23f4747f8a","correlationId":"6f68b9a6-fc50-33f6-a6d1-e75151a73190","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 397bb6c3-6bf2-38a8-b766-ef34b78ae437 | 2026-08-20T17:32:32Z | 2026-08-20T17:32:42Z | `{"operationId":"426546ee-62c8-383c-a4a4-7bfcf69d574c","correlationId":"36c795ba-734e-367d-b76d-bc155faffe14","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"eac2b4ec-1a36-3cad-b53e-bdec4bcb4c7c"}` |
| Retour reçu (`ReturnReceived`) | 0cf27a8a-1cf6-34c4-ad4f-8206e2898079 | 2026-08-20T17:33:02Z | 2026-08-20T17:33:12Z | `{"operationId":"6d87c72f-7a98-3d21-9b7a-5a392692abaa","correlationId":"2055974b-b35c-3ffc-8508-444324297bae","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"eac2b4ec-1a36-3cad-b53e-bdec4bcb4c7c"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 7d0c6e46-1e0e-3f86-aaee-adcd99ce6661 | 2026-08-21T17:32:32Z | 2026-08-21T17:32:42Z | `{"originalEventId":"0cf27a8a-1cf6-34c4-ad4f-8206e2898079","correlationId":"2055974b-b35c-3ffc-8508-444324297bae","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | 55d0d7cc-16d8-3cd6-af3f-24fe46f1c058 | 2026-09-30T11:52:55Z | 2026-09-30T11:53:05Z | `{"operationId":"d56851bc-3dc4-3971-88b0-acae877dde9f","correlationId":"407437f1-b872-3218-ac28-7e594320fbc9","siteId":"NORD","locationId":null,"loanId":"100d793d-d0b6-3986-81cc-da5bfa33c577"}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"d29f1c94-3a40-3c59-98a5-a1c429feb744","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"d29f1c94-3a40-3c59-98a5-a1c429feb744","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"d29f1c94-3a40-3c59-98a5-a1c429feb744","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"d29f1c94-3a40-3c59-98a5-a1c429feb744","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"d29f1c94-3a40-3c59-98a5-a1c429feb744","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"d29f1c94-3a40-3c59-98a5-a1c429feb744","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"d29f1c94-3a40-3c59-98a5-a1c429feb744","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"d29f1c94-3a40-3c59-98a5-a1c429feb744"},{"kind":"sourceEvent","id":"f89972c0-bcf6-3a8e-ad62-aec3be747aab"},{"kind":"sourceEvent","id":"e16d7677-be6f-37a5-a3ec-2986c743455c"},{"kind":"sourceEvent","id":"6315fdef-5ec3-34da-9f81-3f23f4747f8a"},{"kind":"sourceEvent","id":"8fd70f87-91d7-341d-9c89-2eb11ebfaf93"},{"kind":"sourceEvent","id":"397bb6c3-6bf2-38a8-b766-ef34b78ae437"},{"kind":"sourceEvent","id":"0cf27a8a-1cf6-34c4-ad4f-8206e2898079"},{"kind":"sourceEvent","id":"7d0c6e46-1e0e-3f86-aaee-adcd99ce6661"},{"kind":"sourceEvent","id":"55d0d7cc-16d8-3cd6-af3f-24fe46f1c058"},{"kind":"sourceAvailability","id":"d29f1c94-3a40-3c59-98a5-a1c429feb744/LOANS"},{"kind":"sourceAvailability","id":"d29f1c94-3a40-3c59-98a5-a1c429feb744/RETURNS"},{"kind":"sourceAvailability","id":"d29f1c94-3a40-3c59-98a5-a1c429feb744/RECEIPTS"},{"kind":"sourceAvailability","id":"d29f1c94-3a40-3c59-98a5-a1c429feb744/TRANSFERS"},{"kind":"sourceAvailability","id":"d29f1c94-3a40-3c59-98a5-a1c429feb744/REPAIRS"},{"kind":"sourceAvailability","id":"d29f1c94-3a40-3c59-98a5-a1c429feb744/INTEGRATIONS"},{"kind":"sourceAvailability","id":"d29f1c94-3a40-3c59-98a5-a1c429feb744/INVENTORY"},{"kind":"inventoryScope","id":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5"},{"kind":"inventoryScope","id":"eb1ce5f1-7397-3b45-9385-ef328e09fec8"},{"kind":"inventoryScope","id":"d0c55bda-319d-3537-857a-5d135f796cab"},{"kind":"inventoryScope","id":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb"},{"kind":"inventoryScope","id":"d9842c03-ba37-324b-b71b-e58ac7147d4c"},{"kind":"inventoryScope","id":"07bae854-b205-39bf-a23e-9eaa22063b92"}]`

- Session : `{"scopeId":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"eb1ce5f1-7397-3b45-9385-ef328e09fec8","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"d0c55bda-319d-3537-857a-5d135f796cab","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"d9842c03-ba37-324b-b71b-e58ac7147d4c","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"07bae854-b205-39bf-a23e-9eaa22063b92","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas bedc47f2-6828-3e90-8497-95119cd0f595 — dossier d'écart

- Exemplaire : `c740e0fb-e535-3b17-b645-0d6dd7a51f8c`
- Famille privée : LOCALISATION ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"b45af913-620e-31ed-94a9-e5bccd9a8815","kind":"COUNT_UNCERTAIN","expectedValue":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"mechanism":"Aucun déplacement ; signal de collecte non confirmé.","activePerturbations":[],"physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"Le scan valide confirme l'emplacement attendu ; aucun écart de localisation étayé.","forbiddenClaims":["Vol","Perte définitive","Responsabilité d'un opérateur"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"c740e0fb-e535-3b17-b645-0d6dd7a51f8c","status":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","sourceEventIds":["7ac496f1-af31-37b1-991e-d52e25975022","05ae4eb9-0d32-3988-8cf8-05cdfa888ca9"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 9f5af602-fd35-3e88-aac5-f962a80bc392 | 2026-06-01T12:00:27Z | 2026-06-01T12:00:37Z | `{"operationId":"96f7757a-af64-351a-aecf-b194d51ba60e","correlationId":"bc479ce9-6e4f-31c4-ba26-fde3e6e0fe53","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"c8b20032-15fc-3157-bca7-d1eb9d9c6a50"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 2e204a68-7769-3b90-82cb-39031b9dab74 | 2026-06-02T12:00:27Z | 2026-06-02T12:00:37Z | `{"originalEventId":"9f5af602-fd35-3e88-aac5-f962a80bc392","correlationId":"bc479ce9-6e4f-31c4-ba26-fde3e6e0fe53","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | e868f368-5f2b-31cf-b900-16e651daa4b7 | 2026-07-31T12:00:27Z | 2026-07-31T12:00:37Z | `{"operationId":"d128f592-bbad-35cb-8da7-93086fb0558c","correlationId":"e22a2a2f-bd63-3a74-9f0b-f6c3f7444295","siteId":"NORD","locationId":null,"loanId":"f174dae1-9d6a-3444-ad63-da70fe70ca7f"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 913a150c-32f8-3a91-a123-57f6881725fd | 2026-07-31T12:01:27Z | 2026-07-31T12:01:37Z | `{"originalEventId":"e868f368-5f2b-31cf-b900-16e651daa4b7","correlationId":"e22a2a2f-bd63-3a74-9f0b-f6c3f7444295","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 3867366c-1bb9-3253-a1f8-19f11117937a | 2026-08-20T12:00:27Z | 2026-08-20T12:00:37Z | `{"operationId":"b41c244a-f04e-33a1-b5cb-a74d5f4acf61","correlationId":"9d0c37f6-6239-3f4b-a93d-d5982fe70d92","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"f174dae1-9d6a-3444-ad63-da70fe70ca7f"}` |
| Retour reçu (`ReturnReceived`) | 7ac496f1-af31-37b1-991e-d52e25975022 | 2026-08-20T12:00:57Z | 2026-08-20T12:01:07Z | `{"operationId":"e811d499-279f-3a7f-a239-a8f2ce1763c8","correlationId":"49fa4852-3949-3b2a-bbbc-3730f89997dd","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"f174dae1-9d6a-3444-ad63-da70fe70ca7f"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 05ae4eb9-0d32-3988-8cf8-05cdfa888ca9 | 2026-08-21T12:00:27Z | 2026-08-21T12:00:37Z | `{"originalEventId":"7ac496f1-af31-37b1-991e-d52e25975022","correlationId":"49fa4852-3949-3b2a-bbbc-3730f89997dd","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"9e7f3e50-100c-37db-b3bc-76e5e6848e81","copyId":"c740e0fb-e535-3b17-b645-0d6dd7a51f8c","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","scopeId":"eb1ce5f1-7397-3b45-9385-ef328e09fec8","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"c740e0fb-e535-3b17-b645-0d6dd7a51f8c","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c740e0fb-e535-3b17-b645-0d6dd7a51f8c","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c740e0fb-e535-3b17-b645-0d6dd7a51f8c","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c740e0fb-e535-3b17-b645-0d6dd7a51f8c","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c740e0fb-e535-3b17-b645-0d6dd7a51f8c","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c740e0fb-e535-3b17-b645-0d6dd7a51f8c","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c740e0fb-e535-3b17-b645-0d6dd7a51f8c","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"observation","id":"9e7f3e50-100c-37db-b3bc-76e5e6848e81"},{"kind":"systemState","id":"c740e0fb-e535-3b17-b645-0d6dd7a51f8c"}]`

## Cas bfad03d3-e536-3bb2-88a0-502ccabc936c — dossier d'écart

- Exemplaire : `c83b91f3-ae09-3147-a142-707af41e0355`
- Famille privée : DEUX_CAUSES ; variante : POSITIVE
- Symptômes : `[{"symptomId":"63878aac-4f94-3395-b8c9-b914b12063a3","kind":"STATUS_MISMATCH","expectedValue":"IN_REPAIR","observedValue":"ON_SHELF"},{"symptomId":"f1b1da11-ed58-3787-afe3-374ef295e066","kind":"LOCATION_MISMATCH","expectedValue":"a570b572-6b24-3318-9575-eb310588592b","observedValue":"128fab03-f193-3647-a67e-b835a30e7583"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED","REPAIR_LOCATION_CHANGED"],"hiddenSourceEvents":[],"mechanism":"Réparation engagée puis déplacement distinct de l'atelier vers la réserve sans mise à jour de l'affectation de réparation.","businessEventId":"37713df4-3b3e-3e3a-b54d-8b30a805cc0f","physicalStatus":"IN_REPAIR","physicalSiteId":"SUD","physicalLocationId":"128fab03-f193-3647-a67e-b835a30e7583","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_NOT_PROJECTED","REPAIR_LOCATION_MISMATCH"],"explanation":"Deux écarts compatibles : réparation non projetée et scan valide hors de l'atelier indiqué par l'ordre de réparation.","forbiddenClaims":["Deux causes confirmées par un scan ambigu","Vol"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"c83b91f3-ae09-3147-a142-707af41e0355","status":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","sourceEventIds":["06dbada3-e6ac-35a1-a55b-33c10fbcaeb9","ec8e1f89-0644-31b9-aef6-261b7fdfe4af"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 212fa052-1481-37cf-94d8-682c661b727f | 2026-06-01T12:52:28Z | 2026-06-01T12:52:38Z | `{"operationId":"f9ddb446-cb5c-3c3a-ac67-d2524746ae14","correlationId":"f6276f26-0778-3f8e-bd84-757fc56296ab","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"78ea405b-b2a2-30fb-b8e4-915304e90a37"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | ff69e8bb-3431-3c72-b2de-06493a1b002f | 2026-06-02T12:52:28Z | 2026-06-02T12:52:38Z | `{"originalEventId":"212fa052-1481-37cf-94d8-682c661b727f","correlationId":"f6276f26-0778-3f8e-bd84-757fc56296ab","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 025f9e88-8765-38bf-8fb5-336f4450a481 | 2026-07-31T12:52:28Z | 2026-07-31T12:52:38Z | `{"operationId":"57b7ee54-7b61-385f-a6a4-94100e33e517","correlationId":"ea6f315a-e799-3739-9028-e35b301b920e","siteId":"SUD","locationId":null,"loanId":"673e55a7-3936-3d1f-b6d2-50422c4c4519"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 796bbf18-93eb-339e-b8f4-17338138c8ea | 2026-07-31T12:53:28Z | 2026-07-31T12:53:38Z | `{"originalEventId":"025f9e88-8765-38bf-8fb5-336f4450a481","correlationId":"ea6f315a-e799-3739-9028-e35b301b920e","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | aab7c1bf-a986-33aa-8801-4ea2b7e039f6 | 2026-08-20T12:52:28Z | 2026-08-20T12:52:38Z | `{"operationId":"9be13391-d2b0-3143-b623-01892bda1cc4","correlationId":"f67d3b5c-5f85-328a-8cf0-cbf4536e6311","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"673e55a7-3936-3d1f-b6d2-50422c4c4519"}` |
| Retour reçu (`ReturnReceived`) | 06dbada3-e6ac-35a1-a55b-33c10fbcaeb9 | 2026-08-20T12:52:58Z | 2026-08-20T12:53:08Z | `{"operationId":"9e775b11-dec4-388c-bef0-cfbeff482ace","correlationId":"65c19dc0-4111-3bb8-ad48-47ce92b8c5f8","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"673e55a7-3936-3d1f-b6d2-50422c4c4519"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | ec8e1f89-0644-31b9-aef6-261b7fdfe4af | 2026-08-21T12:52:28Z | 2026-08-21T12:52:38Z | `{"originalEventId":"06dbada3-e6ac-35a1-a55b-33c10fbcaeb9","correlationId":"65c19dc0-4111-3bb8-ad48-47ce92b8c5f8","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Réparation engagée (`RepairStarted`) | 37713df4-3b3e-3e3a-b54d-8b30a805cc0f | 2026-09-27T07:33:39Z | 2026-09-27T07:33:49Z | `{"operationId":"ece48353-7e4f-32ea-ab69-ad1e3772ffca","correlationId":"c4c9c942-648f-3177-aad2-e9333b2b83f0","siteId":"SUD","locationId":"a570b572-6b24-3318-9575-eb310588592b","repairId":"c1366ece-8c57-359c-99c1-cdb11906fd03"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 0a44534d-a2d3-37a6-bcfe-c44f87083dab | 2026-09-27T07:34:09Z | 2026-09-27T07:34:19Z | `{"originalEventId":"37713df4-3b3e-3e3a-b54d-8b30a805cc0f","correlationId":"c4c9c942-648f-3177-aad2-e9333b2b83f0","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"SUD","locationId":"a570b572-6b24-3318-9575-eb310588592b","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"572922a0-f842-37b7-88cc-adf02f5825ce","copyId":"c83b91f3-ae09-3147-a142-707af41e0355","locationId":"128fab03-f193-3647-a67e-b835a30e7583","scopeId":"29658e80-6047-3397-a95d-cadac28fda42","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"c83b91f3-ae09-3147-a142-707af41e0355","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c83b91f3-ae09-3147-a142-707af41e0355","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c83b91f3-ae09-3147-a142-707af41e0355","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c83b91f3-ae09-3147-a142-707af41e0355","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c83b91f3-ae09-3147-a142-707af41e0355","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c83b91f3-ae09-3147-a142-707af41e0355","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c83b91f3-ae09-3147-a142-707af41e0355","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"c83b91f3-ae09-3147-a142-707af41e0355"},{"kind":"sourceEvent","id":"212fa052-1481-37cf-94d8-682c661b727f"},{"kind":"sourceEvent","id":"ff69e8bb-3431-3c72-b2de-06493a1b002f"},{"kind":"sourceEvent","id":"025f9e88-8765-38bf-8fb5-336f4450a481"},{"kind":"sourceEvent","id":"796bbf18-93eb-339e-b8f4-17338138c8ea"},{"kind":"sourceEvent","id":"aab7c1bf-a986-33aa-8801-4ea2b7e039f6"},{"kind":"sourceEvent","id":"06dbada3-e6ac-35a1-a55b-33c10fbcaeb9"},{"kind":"sourceEvent","id":"ec8e1f89-0644-31b9-aef6-261b7fdfe4af"},{"kind":"sourceEvent","id":"37713df4-3b3e-3e3a-b54d-8b30a805cc0f"},{"kind":"sourceEvent","id":"0a44534d-a2d3-37a6-bcfe-c44f87083dab"},{"kind":"observation","id":"572922a0-f842-37b7-88cc-adf02f5825ce"},{"kind":"inventoryScope","id":"29658e80-6047-3397-a95d-cadac28fda42"},{"kind":"sourceAvailability","id":"c83b91f3-ae09-3147-a142-707af41e0355/LOANS"},{"kind":"sourceAvailability","id":"c83b91f3-ae09-3147-a142-707af41e0355/RETURNS"},{"kind":"sourceAvailability","id":"c83b91f3-ae09-3147-a142-707af41e0355/RECEIPTS"},{"kind":"sourceAvailability","id":"c83b91f3-ae09-3147-a142-707af41e0355/TRANSFERS"},{"kind":"sourceAvailability","id":"c83b91f3-ae09-3147-a142-707af41e0355/REPAIRS"},{"kind":"sourceAvailability","id":"c83b91f3-ae09-3147-a142-707af41e0355/INTEGRATIONS"},{"kind":"sourceAvailability","id":"c83b91f3-ae09-3147-a142-707af41e0355/INVENTORY"},{"kind":"inventoryScope","id":"7f91fb45-70cc-3811-b222-43a5370aa433"},{"kind":"inventoryScope","id":"202855ec-9065-3be6-a0c2-07ce228ecf21"},{"kind":"inventoryScope","id":"1b4f280a-3145-3607-a009-dca44e9d4181"},{"kind":"inventoryScope","id":"2948b706-5519-3fcb-9c6a-82f0197080d2"},{"kind":"inventoryScope","id":"2977b6c8-c0f0-3d4f-aba5-6b5d16436fb8"},{"kind":"inventoryScope","id":"1399ad71-4f56-32d4-803c-c365bb8f27e7"}]`

- Session : `{"scopeId":"29658e80-6047-3397-a95d-cadac28fda42","locationId":"128fab03-f193-3647-a67e-b835a30e7583","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"7f91fb45-70cc-3811-b222-43a5370aa433","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"202855ec-9065-3be6-a0c2-07ce228ecf21","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"1b4f280a-3145-3607-a009-dca44e9d4181","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2948b706-5519-3fcb-9c6a-82f0197080d2","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2977b6c8-c0f0-3d4f-aba5-6b5d16436fb8","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"1399ad71-4f56-32d4-803c-c365bb8f27e7","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas c046d168-278a-3de4-8eb5-4ebd588479d2 — dossier d'écart

- Exemplaire : `45a55655-1e1b-30f1-a58f-0bdacf269ed3`
- Famille privée : PRET_NON_PROJETE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"f68a1d37-7945-3541-9d4c-ea8f285bcf8f","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"61499434-372d-3551-b78d-5769e26648a4","physicalStatus":"ON_LOAN","physicalSiteId":"SUD","physicalLocationId":null,"projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["LOAN_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"45a55655-1e1b-30f1-a58f-0bdacf269ed3","status":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","sourceEventIds":["d8c1b3c8-9d75-3d5a-85e5-0bf546a70f11","b209310b-0c27-385d-8309-9ab97f7841dc"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | dbeb47f5-9ee2-3879-bd7f-fb0561709f61 | 2026-06-02T05:57:15Z | 2026-06-02T05:57:25Z | `{"operationId":"a49f68ac-f06f-35cb-a2c7-9b02a7fa7b1e","correlationId":"8275b6a4-1cc8-32f9-8daa-d53a5f17ba71","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"a5c107f3-3a45-30e2-a993-4d645a9d8357"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 69c0a7a5-685c-3d74-9c44-d93a4b466063 | 2026-06-03T05:57:15Z | 2026-06-03T05:57:25Z | `{"originalEventId":"dbeb47f5-9ee2-3879-bd7f-fb0561709f61","correlationId":"8275b6a4-1cc8-32f9-8daa-d53a5f17ba71","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 2da1d460-cd77-3ebb-8829-734d713894ab | 2026-08-01T05:57:15Z | 2026-08-01T05:57:25Z | `{"operationId":"74439d0c-906d-3f36-b817-869873300c79","correlationId":"f4d22dbc-4dbb-3ed9-9a71-b3426d0872c5","siteId":"SUD","locationId":null,"loanId":"712d0748-bf9e-3070-a41f-87892f9d1058"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 2e16e591-a51f-31a9-9524-c646ab00b2e9 | 2026-08-01T05:58:15Z | 2026-08-01T05:58:25Z | `{"originalEventId":"2da1d460-cd77-3ebb-8829-734d713894ab","correlationId":"f4d22dbc-4dbb-3ed9-9a71-b3426d0872c5","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | c9374212-a857-37f8-ac1e-543162cb6b99 | 2026-08-21T05:57:15Z | 2026-08-21T05:57:25Z | `{"operationId":"8d569e4e-609c-3de2-b48a-35d4693b5f23","correlationId":"c95751b5-a7fa-3e3c-92b9-df6f3ce30777","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"712d0748-bf9e-3070-a41f-87892f9d1058"}` |
| Retour reçu (`ReturnReceived`) | d8c1b3c8-9d75-3d5a-85e5-0bf546a70f11 | 2026-08-21T05:57:45Z | 2026-08-21T05:57:55Z | `{"operationId":"87c08dbe-d97b-3121-a0cf-85005013568f","correlationId":"f724feed-687b-30e6-aa6d-b9e63db3c51a","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"712d0748-bf9e-3070-a41f-87892f9d1058"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | b209310b-0c27-385d-8309-9ab97f7841dc | 2026-08-22T05:57:15Z | 2026-08-22T05:57:25Z | `{"originalEventId":"d8c1b3c8-9d75-3d5a-85e5-0bf546a70f11","correlationId":"f724feed-687b-30e6-aa6d-b9e63db3c51a","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 61499434-372d-3551-b78d-5769e26648a4 | 2026-09-27T07:37:40Z | 2026-09-27T07:37:50Z | `{"operationId":"e427b2de-0887-398c-a3ec-1131bb129dd1","correlationId":"d8858bd1-2ca3-3d7c-889b-3d49b1946dc1","siteId":"SUD","locationId":null,"loanId":"85963d4f-0ecc-39fe-a5b0-fd7a91dc4e09"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 52dc98e9-92dc-303d-adb4-fc7b999da3b4 | 2026-09-27T07:38:10Z | 2026-09-27T07:38:20Z | `{"originalEventId":"61499434-372d-3551-b78d-5769e26648a4","correlationId":"d8858bd1-2ca3-3d7c-889b-3d49b1946dc1","outcome":"FAILED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"45a55655-1e1b-30f1-a58f-0bdacf269ed3","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"45a55655-1e1b-30f1-a58f-0bdacf269ed3","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"45a55655-1e1b-30f1-a58f-0bdacf269ed3","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"45a55655-1e1b-30f1-a58f-0bdacf269ed3","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"45a55655-1e1b-30f1-a58f-0bdacf269ed3","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"45a55655-1e1b-30f1-a58f-0bdacf269ed3","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"45a55655-1e1b-30f1-a58f-0bdacf269ed3","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"45a55655-1e1b-30f1-a58f-0bdacf269ed3"},{"kind":"sourceEvent","id":"dbeb47f5-9ee2-3879-bd7f-fb0561709f61"},{"kind":"sourceEvent","id":"69c0a7a5-685c-3d74-9c44-d93a4b466063"},{"kind":"sourceEvent","id":"2da1d460-cd77-3ebb-8829-734d713894ab"},{"kind":"sourceEvent","id":"2e16e591-a51f-31a9-9524-c646ab00b2e9"},{"kind":"sourceEvent","id":"c9374212-a857-37f8-ac1e-543162cb6b99"},{"kind":"sourceEvent","id":"d8c1b3c8-9d75-3d5a-85e5-0bf546a70f11"},{"kind":"sourceEvent","id":"b209310b-0c27-385d-8309-9ab97f7841dc"},{"kind":"sourceEvent","id":"61499434-372d-3551-b78d-5769e26648a4"},{"kind":"sourceEvent","id":"52dc98e9-92dc-303d-adb4-fc7b999da3b4"},{"kind":"sourceAvailability","id":"45a55655-1e1b-30f1-a58f-0bdacf269ed3/LOANS"},{"kind":"sourceAvailability","id":"45a55655-1e1b-30f1-a58f-0bdacf269ed3/RETURNS"},{"kind":"sourceAvailability","id":"45a55655-1e1b-30f1-a58f-0bdacf269ed3/RECEIPTS"},{"kind":"sourceAvailability","id":"45a55655-1e1b-30f1-a58f-0bdacf269ed3/TRANSFERS"},{"kind":"sourceAvailability","id":"45a55655-1e1b-30f1-a58f-0bdacf269ed3/REPAIRS"},{"kind":"sourceAvailability","id":"45a55655-1e1b-30f1-a58f-0bdacf269ed3/INTEGRATIONS"},{"kind":"sourceAvailability","id":"45a55655-1e1b-30f1-a58f-0bdacf269ed3/INVENTORY"},{"kind":"inventoryScope","id":"7f91fb45-70cc-3811-b222-43a5370aa433"},{"kind":"inventoryScope","id":"202855ec-9065-3be6-a0c2-07ce228ecf21"},{"kind":"inventoryScope","id":"1b4f280a-3145-3607-a009-dca44e9d4181"}]`

- Session : `{"scopeId":"7f91fb45-70cc-3811-b222-43a5370aa433","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"202855ec-9065-3be6-a0c2-07ce228ecf21","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"1b4f280a-3145-3607-a009-dca44e9d4181","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas c346f548-5b5d-31fc-b239-3f83448a265f — dossier d'écart

- Exemplaire : `0538c7f6-0b33-329a-9588-0efb270940fd`
- Famille privée : DEUX_CAUSES ; variante : POSITIVE
- Symptômes : `[{"symptomId":"dd3912fa-5489-398a-bcbc-4b957f6ccc84","kind":"STATUS_MISMATCH","expectedValue":"IN_REPAIR","observedValue":"ON_SHELF"},{"symptomId":"9678a30f-1057-3b93-a2e3-d5cd96ae535f","kind":"LOCATION_MISMATCH","expectedValue":"a570b572-6b24-3318-9575-eb310588592b","observedValue":"128fab03-f193-3647-a67e-b835a30e7583"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED","REPAIR_LOCATION_CHANGED"],"hiddenSourceEvents":[],"mechanism":"Réparation engagée puis déplacement distinct de l'atelier vers la réserve sans mise à jour de l'affectation de réparation.","businessEventId":"3eb9fb3d-6411-3d45-b736-af855641d814","physicalStatus":"IN_REPAIR","physicalSiteId":"SUD","physicalLocationId":"128fab03-f193-3647-a67e-b835a30e7583","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_NOT_PROJECTED","REPAIR_LOCATION_MISMATCH"],"explanation":"Deux écarts compatibles : réparation non projetée et scan valide hors de l'atelier indiqué par l'ordre de réparation.","forbiddenClaims":["Deux causes confirmées par un scan ambigu","Vol"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"0538c7f6-0b33-329a-9588-0efb270940fd","status":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","sourceEventIds":["b2a3cf87-0bdf-3727-bcf9-36551e989be2","6623a99c-de90-305d-9366-e0c9aa2747af"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | a6cbcf87-9ba6-3f82-8884-98ebc48589c4 | 2026-06-02T01:44:08Z | 2026-06-02T01:44:18Z | `{"operationId":"e99f16ca-4d39-3daa-b688-2f671764992a","correlationId":"50aeb98a-682b-3ccf-91de-37ad2476c229","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"1f4a1ee8-5c9d-3d33-9971-a57f7d60a9ac"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 7915a487-7063-327c-8d22-3538ff1bf4ce | 2026-06-03T01:44:08Z | 2026-06-03T01:44:18Z | `{"originalEventId":"a6cbcf87-9ba6-3f82-8884-98ebc48589c4","correlationId":"50aeb98a-682b-3ccf-91de-37ad2476c229","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 297a51a4-51e6-36e0-9cc9-fab319b9041b | 2026-08-01T01:44:08Z | 2026-08-01T01:44:18Z | `{"operationId":"e544cca7-5466-34fe-9ac2-ccc6dc383d5e","correlationId":"a3984a1f-c931-3a1e-97c4-43b1a0646622","siteId":"SUD","locationId":null,"loanId":"1c5035bb-1cbd-3103-9f85-2e2d7465696f"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 9b0dd453-e740-31e9-b13a-2971f30a47db | 2026-08-01T01:45:08Z | 2026-08-01T01:45:18Z | `{"originalEventId":"297a51a4-51e6-36e0-9cc9-fab319b9041b","correlationId":"a3984a1f-c931-3a1e-97c4-43b1a0646622","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 048f7095-4fb5-330d-9b37-d0dd6da8b7b6 | 2026-08-21T01:44:08Z | 2026-08-21T01:44:18Z | `{"operationId":"53de682e-5586-3d78-bd17-669862890296","correlationId":"2c6e5ea0-6932-3020-837b-220f4c43c9b5","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"1c5035bb-1cbd-3103-9f85-2e2d7465696f"}` |
| Retour reçu (`ReturnReceived`) | b2a3cf87-0bdf-3727-bcf9-36551e989be2 | 2026-08-21T01:44:38Z | 2026-08-21T01:44:48Z | `{"operationId":"c29bf298-65b8-3bd4-8690-623b6f6e06c9","correlationId":"2dadd7e2-9431-353f-ba2a-c5a55dec9176","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"1c5035bb-1cbd-3103-9f85-2e2d7465696f"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 6623a99c-de90-305d-9366-e0c9aa2747af | 2026-08-22T01:44:08Z | 2026-08-22T01:44:18Z | `{"originalEventId":"b2a3cf87-0bdf-3727-bcf9-36551e989be2","correlationId":"2dadd7e2-9431-353f-ba2a-c5a55dec9176","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Réparation engagée (`RepairStarted`) | 3eb9fb3d-6411-3d45-b736-af855641d814 | 2026-09-27T06:43:32Z | 2026-09-27T06:43:42Z | `{"operationId":"0cddb460-50db-3dcb-b785-2d48ae562d58","correlationId":"3d2f91b0-8433-3dd8-a380-93eacc89ce17","siteId":"SUD","locationId":"a570b572-6b24-3318-9575-eb310588592b","repairId":"91272559-8a84-3b14-be4d-bd542a8e32fb"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | e2046dc2-478e-316d-af0e-9cc338314463 | 2026-09-27T06:44:02Z | 2026-09-27T06:44:12Z | `{"originalEventId":"3eb9fb3d-6411-3d45-b736-af855641d814","correlationId":"3d2f91b0-8433-3dd8-a380-93eacc89ce17","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"SUD","locationId":"a570b572-6b24-3318-9575-eb310588592b","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"100c77e2-f6a4-3760-884c-79a9f90cb3e3","copyId":"0538c7f6-0b33-329a-9588-0efb270940fd","locationId":"128fab03-f193-3647-a67e-b835a30e7583","scopeId":"1fce6be0-831d-3267-9491-5c9a1a91b2cc","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"0538c7f6-0b33-329a-9588-0efb270940fd","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0538c7f6-0b33-329a-9588-0efb270940fd","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0538c7f6-0b33-329a-9588-0efb270940fd","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0538c7f6-0b33-329a-9588-0efb270940fd","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0538c7f6-0b33-329a-9588-0efb270940fd","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0538c7f6-0b33-329a-9588-0efb270940fd","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0538c7f6-0b33-329a-9588-0efb270940fd","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"0538c7f6-0b33-329a-9588-0efb270940fd"},{"kind":"sourceEvent","id":"a6cbcf87-9ba6-3f82-8884-98ebc48589c4"},{"kind":"sourceEvent","id":"7915a487-7063-327c-8d22-3538ff1bf4ce"},{"kind":"sourceEvent","id":"297a51a4-51e6-36e0-9cc9-fab319b9041b"},{"kind":"sourceEvent","id":"9b0dd453-e740-31e9-b13a-2971f30a47db"},{"kind":"sourceEvent","id":"048f7095-4fb5-330d-9b37-d0dd6da8b7b6"},{"kind":"sourceEvent","id":"b2a3cf87-0bdf-3727-bcf9-36551e989be2"},{"kind":"sourceEvent","id":"6623a99c-de90-305d-9366-e0c9aa2747af"},{"kind":"sourceEvent","id":"3eb9fb3d-6411-3d45-b736-af855641d814"},{"kind":"sourceEvent","id":"e2046dc2-478e-316d-af0e-9cc338314463"},{"kind":"observation","id":"100c77e2-f6a4-3760-884c-79a9f90cb3e3"},{"kind":"inventoryScope","id":"1fce6be0-831d-3267-9491-5c9a1a91b2cc"},{"kind":"sourceAvailability","id":"0538c7f6-0b33-329a-9588-0efb270940fd/LOANS"},{"kind":"sourceAvailability","id":"0538c7f6-0b33-329a-9588-0efb270940fd/RETURNS"},{"kind":"sourceAvailability","id":"0538c7f6-0b33-329a-9588-0efb270940fd/RECEIPTS"},{"kind":"sourceAvailability","id":"0538c7f6-0b33-329a-9588-0efb270940fd/TRANSFERS"},{"kind":"sourceAvailability","id":"0538c7f6-0b33-329a-9588-0efb270940fd/REPAIRS"},{"kind":"sourceAvailability","id":"0538c7f6-0b33-329a-9588-0efb270940fd/INTEGRATIONS"},{"kind":"sourceAvailability","id":"0538c7f6-0b33-329a-9588-0efb270940fd/INVENTORY"},{"kind":"inventoryScope","id":"7f91fb45-70cc-3811-b222-43a5370aa433"},{"kind":"inventoryScope","id":"202855ec-9065-3be6-a0c2-07ce228ecf21"},{"kind":"inventoryScope","id":"1b4f280a-3145-3607-a009-dca44e9d4181"},{"kind":"inventoryScope","id":"2948b706-5519-3fcb-9c6a-82f0197080d2"},{"kind":"inventoryScope","id":"2977b6c8-c0f0-3d4f-aba5-6b5d16436fb8"},{"kind":"inventoryScope","id":"1399ad71-4f56-32d4-803c-c365bb8f27e7"}]`

- Session : `{"scopeId":"1fce6be0-831d-3267-9491-5c9a1a91b2cc","locationId":"128fab03-f193-3647-a67e-b835a30e7583","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"7f91fb45-70cc-3811-b222-43a5370aa433","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"202855ec-9065-3be6-a0c2-07ce228ecf21","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"1b4f280a-3145-3607-a009-dca44e9d4181","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2948b706-5519-3fcb-9c6a-82f0197080d2","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2977b6c8-c0f0-3d4f-aba5-6b5d16436fb8","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"1399ad71-4f56-32d4-803c-c365bb8f27e7","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas c550669a-1aac-3b06-9440-9f701bc6862c — dossier d'écart

- Exemplaire : `964aa65a-ce62-352d-9d4c-5375caefd1df`
- Famille privée : PRET_NON_PROJETE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"cb597deb-4b58-3a4d-8087-0db946a8fa37","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"73db6daf-b8ba-3a5d-a8b3-179deea19fbb","physicalStatus":"ON_LOAN","physicalSiteId":"SUD","physicalLocationId":null,"projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["LOAN_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"964aa65a-ce62-352d-9d4c-5375caefd1df","status":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","sourceEventIds":["2eedf91f-bef6-3839-9c96-5e001c03b0ec","2c1f55b8-c1c6-3811-83fc-2c1a2367524d"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | d73fc21e-1f16-3484-b526-6624fffcf49c | 2026-06-02T06:00:27Z | 2026-06-02T06:00:37Z | `{"operationId":"c688df7b-c726-3726-8323-2ff09c063bff","correlationId":"3f537f54-a7d1-3eba-8d19-de9285eb53ac","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"2d39a28e-d49a-3c3e-a421-be823607606e"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 249f06d1-2180-3a3b-9f8d-db276bfb9579 | 2026-06-03T06:00:27Z | 2026-06-03T06:00:37Z | `{"originalEventId":"d73fc21e-1f16-3484-b526-6624fffcf49c","correlationId":"3f537f54-a7d1-3eba-8d19-de9285eb53ac","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | cb462040-e8dd-3150-8b0e-9b9402c339cd | 2026-08-01T06:00:27Z | 2026-08-01T06:00:37Z | `{"operationId":"a883af99-033b-3b1b-a247-9c75b2a5fe88","correlationId":"4a9e9b23-0c38-30b0-aa1a-f8d91694253b","siteId":"SUD","locationId":null,"loanId":"cf3a007e-d3bd-3d14-965d-1db6aa5910a8"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | dc6f1bcf-5356-3a13-955c-1a304d4e2d74 | 2026-08-01T06:01:27Z | 2026-08-01T06:01:37Z | `{"originalEventId":"cb462040-e8dd-3150-8b0e-9b9402c339cd","correlationId":"4a9e9b23-0c38-30b0-aa1a-f8d91694253b","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 9bd211d8-d849-3301-a5e5-969e7f1a48ce | 2026-08-21T06:00:27Z | 2026-08-21T06:00:37Z | `{"operationId":"0d7e02b9-fdf6-391e-8e6c-7811c1e257af","correlationId":"5d091cf0-59a0-3b54-98e6-ade99d58f6fb","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"cf3a007e-d3bd-3d14-965d-1db6aa5910a8"}` |
| Retour reçu (`ReturnReceived`) | 2eedf91f-bef6-3839-9c96-5e001c03b0ec | 2026-08-21T06:00:57Z | 2026-08-21T06:01:07Z | `{"operationId":"31ce6f89-17fe-3b76-b083-08d70cca7cfa","correlationId":"6acabf43-a36f-31c9-b9fb-9f49a7cff07a","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"cf3a007e-d3bd-3d14-965d-1db6aa5910a8"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 2c1f55b8-c1c6-3811-83fc-2c1a2367524d | 2026-08-22T06:00:27Z | 2026-08-22T06:00:37Z | `{"originalEventId":"2eedf91f-bef6-3839-9c96-5e001c03b0ec","correlationId":"6acabf43-a36f-31c9-b9fb-9f49a7cff07a","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 73db6daf-b8ba-3a5d-a8b3-179deea19fbb | 2026-09-27T06:50:40Z | 2026-09-27T06:50:50Z | `{"operationId":"27f430a6-c32f-3ab2-8f70-049ad40f02df","correlationId":"e0786d19-8b02-35cb-9d09-412e1ad271c8","siteId":"SUD","locationId":null,"loanId":"f094dc25-692e-3e77-8853-fd2e4cdf945d"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | a31ef758-0ebb-3b86-aab7-578e09917f88 | 2026-09-27T06:51:10Z | 2026-09-27T06:51:20Z | `{"originalEventId":"73db6daf-b8ba-3a5d-a8b3-179deea19fbb","correlationId":"e0786d19-8b02-35cb-9d09-412e1ad271c8","outcome":"FAILED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"964aa65a-ce62-352d-9d4c-5375caefd1df","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"964aa65a-ce62-352d-9d4c-5375caefd1df","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"964aa65a-ce62-352d-9d4c-5375caefd1df","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"964aa65a-ce62-352d-9d4c-5375caefd1df","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"964aa65a-ce62-352d-9d4c-5375caefd1df","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"964aa65a-ce62-352d-9d4c-5375caefd1df","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"964aa65a-ce62-352d-9d4c-5375caefd1df","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"964aa65a-ce62-352d-9d4c-5375caefd1df"},{"kind":"sourceEvent","id":"d73fc21e-1f16-3484-b526-6624fffcf49c"},{"kind":"sourceEvent","id":"249f06d1-2180-3a3b-9f8d-db276bfb9579"},{"kind":"sourceEvent","id":"cb462040-e8dd-3150-8b0e-9b9402c339cd"},{"kind":"sourceEvent","id":"dc6f1bcf-5356-3a13-955c-1a304d4e2d74"},{"kind":"sourceEvent","id":"9bd211d8-d849-3301-a5e5-969e7f1a48ce"},{"kind":"sourceEvent","id":"2eedf91f-bef6-3839-9c96-5e001c03b0ec"},{"kind":"sourceEvent","id":"2c1f55b8-c1c6-3811-83fc-2c1a2367524d"},{"kind":"sourceEvent","id":"73db6daf-b8ba-3a5d-a8b3-179deea19fbb"},{"kind":"sourceEvent","id":"a31ef758-0ebb-3b86-aab7-578e09917f88"},{"kind":"sourceAvailability","id":"964aa65a-ce62-352d-9d4c-5375caefd1df/LOANS"},{"kind":"sourceAvailability","id":"964aa65a-ce62-352d-9d4c-5375caefd1df/RETURNS"},{"kind":"sourceAvailability","id":"964aa65a-ce62-352d-9d4c-5375caefd1df/RECEIPTS"},{"kind":"sourceAvailability","id":"964aa65a-ce62-352d-9d4c-5375caefd1df/TRANSFERS"},{"kind":"sourceAvailability","id":"964aa65a-ce62-352d-9d4c-5375caefd1df/REPAIRS"},{"kind":"sourceAvailability","id":"964aa65a-ce62-352d-9d4c-5375caefd1df/INTEGRATIONS"},{"kind":"sourceAvailability","id":"964aa65a-ce62-352d-9d4c-5375caefd1df/INVENTORY"},{"kind":"inventoryScope","id":"7f91fb45-70cc-3811-b222-43a5370aa433"},{"kind":"inventoryScope","id":"202855ec-9065-3be6-a0c2-07ce228ecf21"},{"kind":"inventoryScope","id":"1b4f280a-3145-3607-a009-dca44e9d4181"}]`

- Session : `{"scopeId":"7f91fb45-70cc-3811-b222-43a5370aa433","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"202855ec-9065-3be6-a0c2-07ce228ecf21","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"1b4f280a-3145-3607-a009-dca44e9d4181","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas c5bd02dd-3f2c-3881-bacd-8dc4f28e3bcf — dossier d'écart

- Exemplaire : `cb473dbe-fed6-3de6-9e47-c37fd077926e`
- Famille privée : REPARATION_NON_PROJETEE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"53ae99c0-a6ec-3d81-a273-8c13254038d1","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"11c29697-24df-309c-9a60-50ce667708ac","physicalStatus":"IN_REPAIR","physicalSiteId":"SUD","physicalLocationId":"a570b572-6b24-3318-9575-eb310588592b","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"cb473dbe-fed6-3de6-9e47-c37fd077926e","status":"IN_REPAIR","siteId":"SUD","locationId":"a570b572-6b24-3318-9575-eb310588592b","sourceEventIds":["11c29697-24df-309c-9a60-50ce667708ac","a78e920c-6977-366d-8782-1eba52f11807"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | b2c9eab3-0009-3f78-b2bf-b49d13a3647d | 2026-06-01T17:24:37Z | 2026-06-01T17:24:47Z | `{"operationId":"16b90380-49df-3ff7-91cb-d0a918747e67","correlationId":"2e67d44c-42fc-3c00-bc76-e7e00c992167","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"498ca8cf-772f-3394-a800-49edfb1f7cc9"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 5b730243-558d-3d20-8e26-467a565056b5 | 2026-06-02T17:24:37Z | 2026-06-02T17:24:47Z | `{"originalEventId":"b2c9eab3-0009-3f78-b2bf-b49d13a3647d","correlationId":"2e67d44c-42fc-3c00-bc76-e7e00c992167","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | e2bb3655-3614-3109-a13a-9baf600c9c49 | 2026-07-31T17:24:37Z | 2026-07-31T17:24:47Z | `{"operationId":"4023958e-b64f-3d92-bd1e-3e23fc944ca9","correlationId":"ff449c23-55b5-3fdc-a3ee-0b0588cb39a8","siteId":"SUD","locationId":null,"loanId":"53827fc4-58c4-3aef-aeb2-33f943791497"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 06baffb6-4d92-3b2f-b312-6c9ea3f21c1f | 2026-07-31T17:25:37Z | 2026-07-31T17:25:47Z | `{"originalEventId":"e2bb3655-3614-3109-a13a-9baf600c9c49","correlationId":"ff449c23-55b5-3fdc-a3ee-0b0588cb39a8","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 1b14f2a4-16b0-37f9-bff6-3e1d1fa2f54c | 2026-08-20T17:24:37Z | 2026-08-20T17:24:47Z | `{"operationId":"6cc7a070-8138-30b9-aeb4-3a0c885e9fb0","correlationId":"28ea4a4b-afa8-397a-8804-58649125b449","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"53827fc4-58c4-3aef-aeb2-33f943791497"}` |
| Retour reçu (`ReturnReceived`) | b4f29825-8c71-3ec2-ab78-d02459a3b76e | 2026-08-20T17:25:07Z | 2026-08-20T17:25:17Z | `{"operationId":"9bd956a7-6a20-3941-ac4c-02fdba975a9c","correlationId":"8552bcd0-2e51-3cb7-a10a-78d8153e4056","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"53827fc4-58c4-3aef-aeb2-33f943791497"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 99c8ee6b-df22-3bd6-8274-204fc443adfa | 2026-08-21T17:24:37Z | 2026-08-21T17:24:47Z | `{"originalEventId":"b4f29825-8c71-3ec2-ab78-d02459a3b76e","correlationId":"8552bcd0-2e51-3cb7-a10a-78d8153e4056","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Réparation engagée (`RepairStarted`) | 11c29697-24df-309c-9a60-50ce667708ac | 2026-09-27T08:21:50Z | 2026-09-27T08:22:00Z | `{"operationId":"4c2aa04d-c3f9-3fc1-bc53-bcf1c2c70145","correlationId":"ec4f868a-1485-31a3-995e-0bb5bc8c3158","siteId":"SUD","locationId":"a570b572-6b24-3318-9575-eb310588592b","repairId":"eae168df-cb6c-39f0-aa17-10a0523bd46a"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 52b4b4ba-b4ca-3ddf-a9b8-da3cf31ac601 | 2026-09-27T08:22:20Z | 2026-09-27T08:22:30Z | `{"originalEventId":"11c29697-24df-309c-9a60-50ce667708ac","correlationId":"ec4f868a-1485-31a3-995e-0bb5bc8c3158","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"SUD","locationId":"a570b572-6b24-3318-9575-eb310588592b","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | a78e920c-6977-366d-8782-1eba52f11807 | 2026-09-27T08:23:20Z | 2026-09-27T08:23:30Z | `{"originalEventId":"11c29697-24df-309c-9a60-50ce667708ac","correlationId":"ec4f868a-1485-31a3-995e-0bb5bc8c3158","outcome":"APPLIED","projectedStatus":"IN_REPAIR","siteId":"SUD","locationId":"a570b572-6b24-3318-9575-eb310588592b"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"32abd9bb-f737-3fa8-ad38-6062b942a160","copyId":"cb473dbe-fed6-3de6-9e47-c37fd077926e","locationId":"a570b572-6b24-3318-9575-eb310588592b","scopeId":"0a5018ac-fd62-39c6-b080-03551f006e3d","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`
- `{"observationId":"a92150b3-3bae-3bb3-b696-f8cd6d5417d2","copyId":"cb473dbe-fed6-3de6-9e47-c37fd077926e","locationId":"128fab03-f193-3647-a67e-b835a30e7583","scopeId":"9b400cfe-9d6b-3db8-9d3a-c898d9ed57a3","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"cb473dbe-fed6-3de6-9e47-c37fd077926e","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cb473dbe-fed6-3de6-9e47-c37fd077926e","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cb473dbe-fed6-3de6-9e47-c37fd077926e","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cb473dbe-fed6-3de6-9e47-c37fd077926e","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cb473dbe-fed6-3de6-9e47-c37fd077926e","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cb473dbe-fed6-3de6-9e47-c37fd077926e","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cb473dbe-fed6-3de6-9e47-c37fd077926e","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"cb473dbe-fed6-3de6-9e47-c37fd077926e"},{"kind":"sourceEvent","id":"b2c9eab3-0009-3f78-b2bf-b49d13a3647d"},{"kind":"sourceEvent","id":"5b730243-558d-3d20-8e26-467a565056b5"},{"kind":"sourceEvent","id":"e2bb3655-3614-3109-a13a-9baf600c9c49"},{"kind":"sourceEvent","id":"06baffb6-4d92-3b2f-b312-6c9ea3f21c1f"},{"kind":"sourceEvent","id":"1b14f2a4-16b0-37f9-bff6-3e1d1fa2f54c"},{"kind":"sourceEvent","id":"b4f29825-8c71-3ec2-ab78-d02459a3b76e"},{"kind":"sourceEvent","id":"99c8ee6b-df22-3bd6-8274-204fc443adfa"},{"kind":"sourceEvent","id":"11c29697-24df-309c-9a60-50ce667708ac"},{"kind":"sourceEvent","id":"52b4b4ba-b4ca-3ddf-a9b8-da3cf31ac601"},{"kind":"sourceEvent","id":"a78e920c-6977-366d-8782-1eba52f11807"},{"kind":"observation","id":"32abd9bb-f737-3fa8-ad38-6062b942a160"},{"kind":"inventoryScope","id":"0a5018ac-fd62-39c6-b080-03551f006e3d"},{"kind":"observation","id":"a92150b3-3bae-3bb3-b696-f8cd6d5417d2"},{"kind":"inventoryScope","id":"9b400cfe-9d6b-3db8-9d3a-c898d9ed57a3"},{"kind":"sourceAvailability","id":"cb473dbe-fed6-3de6-9e47-c37fd077926e/LOANS"},{"kind":"sourceAvailability","id":"cb473dbe-fed6-3de6-9e47-c37fd077926e/RETURNS"},{"kind":"sourceAvailability","id":"cb473dbe-fed6-3de6-9e47-c37fd077926e/RECEIPTS"},{"kind":"sourceAvailability","id":"cb473dbe-fed6-3de6-9e47-c37fd077926e/TRANSFERS"},{"kind":"sourceAvailability","id":"cb473dbe-fed6-3de6-9e47-c37fd077926e/REPAIRS"},{"kind":"sourceAvailability","id":"cb473dbe-fed6-3de6-9e47-c37fd077926e/INTEGRATIONS"},{"kind":"sourceAvailability","id":"cb473dbe-fed6-3de6-9e47-c37fd077926e/INVENTORY"},{"kind":"inventoryScope","id":"6cd75017-62f5-3a85-9ca4-5b89b79bc056"}]`

- Session : `{"scopeId":"0a5018ac-fd62-39c6-b080-03551f006e3d","locationId":"a570b572-6b24-3318-9575-eb310588592b","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9b400cfe-9d6b-3db8-9d3a-c898d9ed57a3","locationId":"128fab03-f193-3647-a67e-b835a30e7583","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"6cd75017-62f5-3a85-9ca4-5b89b79bc056","locationId":"a570b572-6b24-3318-9575-eb310588592b","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas c743c33a-e58b-376f-a577-211fb8b50db0 — dossier d'écart

- Exemplaire : `76759017-f757-3042-ac58-7db71413324f`
- Famille privée : REPARATION_NON_PROJETEE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"94a97b74-b382-316f-9bf6-644eb19dcf8e","kind":"STATUS_MISMATCH","expectedValue":"ON_SHELF","observedValue":"IN_REPAIR"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"652b2bdb-cfc3-3de2-80ed-6e78a6dee38c","copyId":"76759017-f757-3042-ac58-7db71413324f","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T08:10:24Z","recordedAt":"2026-09-27T08:10:34Z","payload":{"originalEventId":"7b1c8552-926c-36f4-9756-95326e15ddb6","correlationId":"1963c18a-50c9-3532-a2bd-2b6dc6290f2a","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"SUD","locationId":"a570b572-6b24-3318-9575-eb310588592b","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"7b1c8552-926c-36f4-9756-95326e15ddb6","physicalStatus":"IN_REPAIR","physicalSiteId":"SUD","physicalLocationId":"a570b572-6b24-3318-9575-eb310588592b","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État du stock informatique : `{"copyId":"76759017-f757-3042-ac58-7db71413324f","status":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","sourceEventIds":["7678cd36-1a18-3c75-86eb-0c7eba34e833","add9c02c-5d79-36a2-ac26-6f08e7797f05"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 872c41c0-89e9-3ff6-aec2-4be7a2dacf34 | 2026-06-01T13:27:38Z | 2026-06-01T13:27:48Z | `{"operationId":"04698ec9-370c-35e5-9f6b-0954c6f747ad","correlationId":"02219382-10a5-3308-bbc9-5aa906310baf","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"4f38fd2f-caf5-3b92-9b60-7d458643085e"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 7438a968-e224-3b92-9d10-aec1eefdd623 | 2026-06-02T13:27:38Z | 2026-06-02T13:27:48Z | `{"originalEventId":"872c41c0-89e9-3ff6-aec2-4be7a2dacf34","correlationId":"02219382-10a5-3308-bbc9-5aa906310baf","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 3a849623-a3a0-3eeb-8994-a58431ab9839 | 2026-07-31T13:27:38Z | 2026-07-31T13:27:48Z | `{"operationId":"09bf37e0-ffaa-32f8-b643-d40bad7cd636","correlationId":"c80b225b-f570-35ad-aeb2-678a9541deae","siteId":"SUD","locationId":null,"loanId":"3f3f81a7-0bef-3a3e-b3d3-61789fbad341"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 952982bf-1b49-3664-98cb-01932d730b15 | 2026-07-31T13:28:38Z | 2026-07-31T13:28:48Z | `{"originalEventId":"3a849623-a3a0-3eeb-8994-a58431ab9839","correlationId":"c80b225b-f570-35ad-aeb2-678a9541deae","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 04ab4e76-c444-32dc-9cd2-6a905998108d | 2026-08-20T13:27:38Z | 2026-08-20T13:27:48Z | `{"operationId":"fdda7303-92c5-3fc6-924d-b2f90234e5b0","correlationId":"7d21fd61-0612-34b5-9dc7-dbce4b23b190","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"3f3f81a7-0bef-3a3e-b3d3-61789fbad341"}` |
| Retour reçu (`ReturnReceived`) | 7678cd36-1a18-3c75-86eb-0c7eba34e833 | 2026-08-20T13:28:08Z | 2026-08-20T13:28:18Z | `{"operationId":"0503ceff-18f9-3f81-a521-698198a37d8c","correlationId":"246a0bad-4afe-3029-82f4-de4aedf007a2","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"3f3f81a7-0bef-3a3e-b3d3-61789fbad341"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | add9c02c-5d79-36a2-ac26-6f08e7797f05 | 2026-08-21T13:27:38Z | 2026-08-21T13:27:48Z | `{"originalEventId":"7678cd36-1a18-3c75-86eb-0c7eba34e833","correlationId":"246a0bad-4afe-3029-82f4-de4aedf007a2","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Réparation engagée (`RepairStarted`) | 7b1c8552-926c-36f4-9756-95326e15ddb6 | 2026-09-27T08:09:54Z | 2026-09-27T08:10:04Z | `{"operationId":"ea5d8cee-5995-3202-aec4-17628477636b","correlationId":"1963c18a-50c9-3532-a2bd-2b6dc6290f2a","siteId":"SUD","locationId":"a570b572-6b24-3318-9575-eb310588592b","repairId":"b10e15e9-225c-3201-bab3-a9bf644a1cd8"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"40feb451-295d-30e0-bde2-efc6240e0a3b","copyId":"76759017-f757-3042-ac58-7db71413324f","locationId":"a570b572-6b24-3318-9575-eb310588592b","scopeId":"fc3c069e-87f6-32ef-b435-2710775e6dcd","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"76759017-f757-3042-ac58-7db71413324f","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"76759017-f757-3042-ac58-7db71413324f","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"76759017-f757-3042-ac58-7db71413324f","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"76759017-f757-3042-ac58-7db71413324f","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"76759017-f757-3042-ac58-7db71413324f","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"76759017-f757-3042-ac58-7db71413324f","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"76759017-f757-3042-ac58-7db71413324f","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"76759017-f757-3042-ac58-7db71413324f"},{"kind":"sourceEvent","id":"872c41c0-89e9-3ff6-aec2-4be7a2dacf34"},{"kind":"sourceEvent","id":"7438a968-e224-3b92-9d10-aec1eefdd623"},{"kind":"sourceEvent","id":"3a849623-a3a0-3eeb-8994-a58431ab9839"},{"kind":"sourceEvent","id":"952982bf-1b49-3664-98cb-01932d730b15"},{"kind":"sourceEvent","id":"04ab4e76-c444-32dc-9cd2-6a905998108d"},{"kind":"sourceEvent","id":"7678cd36-1a18-3c75-86eb-0c7eba34e833"},{"kind":"sourceEvent","id":"add9c02c-5d79-36a2-ac26-6f08e7797f05"},{"kind":"sourceEvent","id":"7b1c8552-926c-36f4-9756-95326e15ddb6"},{"kind":"observation","id":"40feb451-295d-30e0-bde2-efc6240e0a3b"},{"kind":"inventoryScope","id":"fc3c069e-87f6-32ef-b435-2710775e6dcd"},{"kind":"sourceAvailability","id":"76759017-f757-3042-ac58-7db71413324f/LOANS"},{"kind":"sourceAvailability","id":"76759017-f757-3042-ac58-7db71413324f/RETURNS"},{"kind":"sourceAvailability","id":"76759017-f757-3042-ac58-7db71413324f/RECEIPTS"},{"kind":"sourceAvailability","id":"76759017-f757-3042-ac58-7db71413324f/TRANSFERS"},{"kind":"sourceAvailability","id":"76759017-f757-3042-ac58-7db71413324f/REPAIRS"},{"kind":"sourceAvailability","id":"76759017-f757-3042-ac58-7db71413324f/INTEGRATIONS"},{"kind":"sourceAvailability","id":"76759017-f757-3042-ac58-7db71413324f/INVENTORY"},{"kind":"inventoryScope","id":"7f91fb45-70cc-3811-b222-43a5370aa433"},{"kind":"inventoryScope","id":"202855ec-9065-3be6-a0c2-07ce228ecf21"},{"kind":"inventoryScope","id":"1b4f280a-3145-3607-a009-dca44e9d4181"}]`

- Session : `{"scopeId":"fc3c069e-87f6-32ef-b435-2710775e6dcd","locationId":"a570b572-6b24-3318-9575-eb310588592b","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"7f91fb45-70cc-3811-b222-43a5370aa433","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"202855ec-9065-3be6-a0c2-07ce228ecf21","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"1b4f280a-3145-3607-a009-dca44e9d4181","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas c91c2360-15d8-3a31-abdc-c5d2325c9dd0 — dossier d'écart

- Exemplaire : `2b143c95-1aa6-357e-929d-e752cd4ae3e4`
- Famille privée : DEUX_CAUSES ; variante : POSITIVE
- Symptômes : `[{"symptomId":"6b8078ad-d292-3aa4-b4e6-f99c210ec481","kind":"STATUS_MISMATCH","expectedValue":"IN_REPAIR","observedValue":"ON_SHELF"},{"symptomId":"6be13a40-e63f-3021-99a7-9d8bf3b04a98","kind":"LOCATION_MISMATCH","expectedValue":"19124ccb-fb37-348d-81ff-686cdabbabb0","observedValue":"2cf9a5ef-0690-3b0f-9031-67da0dd9d884"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED","REPAIR_LOCATION_CHANGED"],"hiddenSourceEvents":[],"mechanism":"Réparation engagée puis déplacement distinct de l'atelier vers la réserve sans mise à jour de l'affectation de réparation.","businessEventId":"e7191c3b-36e3-352b-b689-b41137888928","physicalStatus":"IN_REPAIR","physicalSiteId":"NORD","physicalLocationId":"2cf9a5ef-0690-3b0f-9031-67da0dd9d884","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_NOT_PROJECTED","REPAIR_LOCATION_MISMATCH"],"explanation":"Deux écarts compatibles : réparation non projetée et scan valide hors de l'atelier indiqué par l'ordre de réparation.","forbiddenClaims":["Deux causes confirmées par un scan ambigu","Vol"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"2b143c95-1aa6-357e-929d-e752cd4ae3e4","status":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","sourceEventIds":["d2da13f9-ba31-3ab6-a9ff-aa864b90615d","51543372-1aa8-38b5-a293-0533cda5f596"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | ec2118c4-5429-3876-8c21-afd3c3b141aa | 2026-06-01T22:21:50Z | 2026-06-01T22:22:00Z | `{"operationId":"cc78cb13-4b39-3b36-8751-b7dd11c541e0","correlationId":"fde1b7f9-3def-3619-ac89-cee708eb7cdb","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"d4b259e1-b4c4-36a0-908f-e2b7afa8cc92"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 96f5b4b7-549c-30a9-a9b0-3785f925debf | 2026-06-02T22:21:50Z | 2026-06-02T22:22:00Z | `{"originalEventId":"ec2118c4-5429-3876-8c21-afd3c3b141aa","correlationId":"fde1b7f9-3def-3619-ac89-cee708eb7cdb","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 4b84d882-a2b9-3a66-94c8-6bccf3233733 | 2026-07-31T22:21:50Z | 2026-07-31T22:22:00Z | `{"operationId":"867f0cdb-f2b7-37f8-a433-3474a520c8c1","correlationId":"ea30f3e7-2434-3177-88fa-2e1dbd91c0cd","siteId":"CENTRE","locationId":null,"loanId":"175cce75-0832-34b3-a259-ffdb3d4c451f"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | c93869b5-d55e-39b3-8199-5d6ca336c2dc | 2026-07-31T22:22:50Z | 2026-07-31T22:23:00Z | `{"originalEventId":"4b84d882-a2b9-3a66-94c8-6bccf3233733","correlationId":"ea30f3e7-2434-3177-88fa-2e1dbd91c0cd","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | f0f9440d-5f6e-3852-b1a2-e14e48d59973 | 2026-08-20T22:21:50Z | 2026-08-20T22:22:00Z | `{"operationId":"63c99a27-35a3-3d45-a2f2-f99751268d50","correlationId":"1767af78-5779-3423-a5b3-64e62816b3e3","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"175cce75-0832-34b3-a259-ffdb3d4c451f"}` |
| Retour reçu (`ReturnReceived`) | 8f731e4f-6fe6-328c-b8cd-2a38c431fea1 | 2026-08-20T22:22:20Z | 2026-08-20T22:22:30Z | `{"operationId":"358d3f7a-a576-376a-b9f8-8d45d7c69839","correlationId":"f052e16f-ed00-3179-88f0-f39740c5138c","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"175cce75-0832-34b3-a259-ffdb3d4c451f"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | d53061bc-c8f9-3420-b48c-dae7acf362c6 | 2026-08-21T22:21:50Z | 2026-08-21T22:22:00Z | `{"originalEventId":"8f731e4f-6fe6-328c-b8cd-2a38c431fea1","correlationId":"f052e16f-ed00-3179-88f0-f39740c5138c","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Transfert expédié (`TransferDispatched`) | fcb4e545-504c-310d-a36a-a325a20bc1c9 | 2026-08-30T22:21:50Z | 2026-08-30T22:22:00Z | `{"operationId":"d7814112-5162-3661-a490-f67a6b01366d","correlationId":"c0dea2c5-1db8-33fb-976e-f9370e312126","siteId":"CENTRE","locationId":null,"transferId":"319a15e1-0ec2-3191-aca0-0d6b7fbbcc4c","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | d94fb984-f27a-3975-b8fa-c7dacce77c26 | 2026-08-30T22:22:50Z | 2026-08-30T22:23:00Z | `{"originalEventId":"fcb4e545-504c-310d-a36a-a325a20bc1c9","correlationId":"c0dea2c5-1db8-33fb-976e-f9370e312126","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| Transfert reçu (`TransferReceived`) | d2da13f9-ba31-3ab6-a9ff-aa864b90615d | 2026-08-31T22:21:50Z | 2026-08-31T22:22:00Z | `{"operationId":"bdee5b70-8395-3d00-ba6c-30c074cc6ea8","correlationId":"f172e31b-7cc2-3000-97bc-288f3b9104b1","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","transferId":"319a15e1-0ec2-3191-aca0-0d6b7fbbcc4c","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 51543372-1aa8-38b5-a293-0533cda5f596 | 2026-09-01T22:21:50Z | 2026-09-01T22:22:00Z | `{"originalEventId":"d2da13f9-ba31-3ab6-a9ff-aa864b90615d","correlationId":"f172e31b-7cc2-3000-97bc-288f3b9104b1","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Réparation engagée (`RepairStarted`) | e7191c3b-36e3-352b-b689-b41137888928 | 2026-09-27T08:20:38Z | 2026-09-27T08:20:48Z | `{"operationId":"e553634e-66a5-31a6-9ed9-cd79a7879b73","correlationId":"43de3e7d-9a1c-369d-a340-24e7d85e6799","siteId":"NORD","locationId":"19124ccb-fb37-348d-81ff-686cdabbabb0","repairId":"75dc6673-197f-3cba-a278-1090d6070bc9"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | aa455f78-dcc7-3053-a558-04a81ce3133d | 2026-09-27T08:21:08Z | 2026-09-27T08:21:18Z | `{"originalEventId":"e7191c3b-36e3-352b-b689-b41137888928","correlationId":"43de3e7d-9a1c-369d-a340-24e7d85e6799","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"NORD","locationId":"19124ccb-fb37-348d-81ff-686cdabbabb0","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"14928599-0456-3829-9718-1326cad9889f","copyId":"2b143c95-1aa6-357e-929d-e752cd4ae3e4","locationId":"2cf9a5ef-0690-3b0f-9031-67da0dd9d884","scopeId":"704899fe-2427-3da2-b6ef-270506df51c7","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"2b143c95-1aa6-357e-929d-e752cd4ae3e4","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2b143c95-1aa6-357e-929d-e752cd4ae3e4","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2b143c95-1aa6-357e-929d-e752cd4ae3e4","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2b143c95-1aa6-357e-929d-e752cd4ae3e4","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2b143c95-1aa6-357e-929d-e752cd4ae3e4","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2b143c95-1aa6-357e-929d-e752cd4ae3e4","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2b143c95-1aa6-357e-929d-e752cd4ae3e4","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"2b143c95-1aa6-357e-929d-e752cd4ae3e4"},{"kind":"sourceEvent","id":"ec2118c4-5429-3876-8c21-afd3c3b141aa"},{"kind":"sourceEvent","id":"96f5b4b7-549c-30a9-a9b0-3785f925debf"},{"kind":"sourceEvent","id":"4b84d882-a2b9-3a66-94c8-6bccf3233733"},{"kind":"sourceEvent","id":"c93869b5-d55e-39b3-8199-5d6ca336c2dc"},{"kind":"sourceEvent","id":"f0f9440d-5f6e-3852-b1a2-e14e48d59973"},{"kind":"sourceEvent","id":"8f731e4f-6fe6-328c-b8cd-2a38c431fea1"},{"kind":"sourceEvent","id":"d53061bc-c8f9-3420-b48c-dae7acf362c6"},{"kind":"sourceEvent","id":"fcb4e545-504c-310d-a36a-a325a20bc1c9"},{"kind":"sourceEvent","id":"d94fb984-f27a-3975-b8fa-c7dacce77c26"},{"kind":"sourceEvent","id":"d2da13f9-ba31-3ab6-a9ff-aa864b90615d"},{"kind":"sourceEvent","id":"51543372-1aa8-38b5-a293-0533cda5f596"},{"kind":"sourceEvent","id":"e7191c3b-36e3-352b-b689-b41137888928"},{"kind":"sourceEvent","id":"aa455f78-dcc7-3053-a558-04a81ce3133d"},{"kind":"observation","id":"14928599-0456-3829-9718-1326cad9889f"},{"kind":"inventoryScope","id":"704899fe-2427-3da2-b6ef-270506df51c7"},{"kind":"sourceAvailability","id":"2b143c95-1aa6-357e-929d-e752cd4ae3e4/LOANS"},{"kind":"sourceAvailability","id":"2b143c95-1aa6-357e-929d-e752cd4ae3e4/RETURNS"},{"kind":"sourceAvailability","id":"2b143c95-1aa6-357e-929d-e752cd4ae3e4/RECEIPTS"},{"kind":"sourceAvailability","id":"2b143c95-1aa6-357e-929d-e752cd4ae3e4/TRANSFERS"},{"kind":"sourceAvailability","id":"2b143c95-1aa6-357e-929d-e752cd4ae3e4/REPAIRS"},{"kind":"sourceAvailability","id":"2b143c95-1aa6-357e-929d-e752cd4ae3e4/INTEGRATIONS"},{"kind":"sourceAvailability","id":"2b143c95-1aa6-357e-929d-e752cd4ae3e4/INVENTORY"},{"kind":"inventoryScope","id":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5"},{"kind":"inventoryScope","id":"eb1ce5f1-7397-3b45-9385-ef328e09fec8"},{"kind":"inventoryScope","id":"d0c55bda-319d-3537-857a-5d135f796cab"},{"kind":"inventoryScope","id":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb"},{"kind":"inventoryScope","id":"d9842c03-ba37-324b-b71b-e58ac7147d4c"},{"kind":"inventoryScope","id":"07bae854-b205-39bf-a23e-9eaa22063b92"},{"kind":"inventoryScope","id":"9de706b4-d360-3282-b440-c440861c039c"},{"kind":"inventoryScope","id":"b98af8e5-7297-3dc5-a947-eb8fe8af159f"},{"kind":"inventoryScope","id":"7eb96d9b-e927-305a-b369-01d6b239f3db"},{"kind":"inventoryScope","id":"b4a97852-2336-3ae2-ab5b-30b6e2e4d536"},{"kind":"inventoryScope","id":"147f13fd-620d-3c02-bc47-bf4f7080184b"},{"kind":"inventoryScope","id":"ed295eda-a27c-3fef-aa0e-3ee02619626a"}]`

- Session : `{"scopeId":"704899fe-2427-3da2-b6ef-270506df51c7","locationId":"2cf9a5ef-0690-3b0f-9031-67da0dd9d884","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"eb1ce5f1-7397-3b45-9385-ef328e09fec8","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"d0c55bda-319d-3537-857a-5d135f796cab","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"d9842c03-ba37-324b-b71b-e58ac7147d4c","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"07bae854-b205-39bf-a23e-9eaa22063b92","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"9de706b4-d360-3282-b440-c440861c039c","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b98af8e5-7297-3dc5-a947-eb8fe8af159f","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"7eb96d9b-e927-305a-b369-01d6b239f3db","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b4a97852-2336-3ae2-ab5b-30b6e2e4d536","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"147f13fd-620d-3c02-bc47-bf4f7080184b","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"ed295eda-a27c-3fef-aa0e-3ee02619626a","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas cab66b7d-2b97-3f5b-9fce-cac50e8ea0bd — dossier d'écart

- Exemplaire : `3bfa6be4-dcd8-3a48-865a-b4fadb430b89`
- Famille privée : PRET_NON_PROJETE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"a3fafeb0-035a-3a26-b58f-2b65d2bf9a8c","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"5172a5f4-4e9a-33f2-b168-145bac156598","physicalStatus":"ON_LOAN","physicalSiteId":"CENTRE","physicalLocationId":null,"projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"3bfa6be4-dcd8-3a48-865a-b4fadb430b89","status":"ON_LOAN","siteId":"CENTRE","locationId":null,"sourceEventIds":["5172a5f4-4e9a-33f2-b168-145bac156598","2006d561-909d-3657-a7da-658a4a36b602"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 3b759a72-5d52-39a8-959f-ffa5e7fa06f2 | 2026-06-01T20:25:49Z | 2026-06-01T20:25:59Z | `{"operationId":"4d13ed5a-f7de-3d2e-9b83-815f09169c86","correlationId":"bbd3c51c-8561-3225-a5f3-81737fcc8581","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"ab251bc6-3459-3dda-97db-ad7d23683ad4"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 2d053a7c-a853-3d67-aefd-c4c31a3fa06d | 2026-06-02T20:25:49Z | 2026-06-02T20:25:59Z | `{"originalEventId":"3b759a72-5d52-39a8-959f-ffa5e7fa06f2","correlationId":"bbd3c51c-8561-3225-a5f3-81737fcc8581","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 1d7ac15b-333e-341e-9120-3ab7c77ab903 | 2026-07-31T20:25:49Z | 2026-07-31T20:25:59Z | `{"operationId":"7130f90f-0142-3bbd-96aa-6b1316e706b6","correlationId":"17552971-9655-3946-95e9-40856a4ad9c3","siteId":"CENTRE","locationId":null,"loanId":"60810024-4bdd-3c64-b1a5-b6b7ef7c68d0"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | eea2f151-90f7-3a30-bf40-90c848358d55 | 2026-07-31T20:26:49Z | 2026-07-31T20:26:59Z | `{"originalEventId":"1d7ac15b-333e-341e-9120-3ab7c77ab903","correlationId":"17552971-9655-3946-95e9-40856a4ad9c3","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 9b9b3434-6a76-3c5f-a5d4-08cd89afe049 | 2026-08-20T20:25:49Z | 2026-08-20T20:25:59Z | `{"operationId":"c36f21f8-0246-303a-89ee-38f0c08d6068","correlationId":"d0f258ee-7cf3-3b16-99da-ea18047511d9","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"60810024-4bdd-3c64-b1a5-b6b7ef7c68d0"}` |
| Retour reçu (`ReturnReceived`) | 253b6468-555b-3e95-8927-4c381e33f015 | 2026-08-20T20:26:19Z | 2026-08-20T20:26:29Z | `{"operationId":"1542330f-39b0-3f9a-8d19-244fcb680474","correlationId":"7a25b43c-a1c7-37b5-a414-7b16e64ebe20","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"60810024-4bdd-3c64-b1a5-b6b7ef7c68d0"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 50914834-d6fb-3ca0-ac4f-c2429d0f67f8 | 2026-08-21T20:25:49Z | 2026-08-21T20:25:59Z | `{"originalEventId":"253b6468-555b-3e95-8927-4c381e33f015","correlationId":"7a25b43c-a1c7-37b5-a414-7b16e64ebe20","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 5172a5f4-4e9a-33f2-b168-145bac156598 | 2026-09-27T11:05:27Z | 2026-09-27T11:05:37Z | `{"operationId":"df48028f-f531-3a28-8749-1de392890254","correlationId":"f6dc9c7d-3e77-3a7f-8778-16f8c193d6c2","siteId":"CENTRE","locationId":null,"loanId":"cd5c5869-5730-3631-b6d1-e5f820d45ba1"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | b09cf83f-32a5-374c-a1df-1f48b5de6016 | 2026-09-27T11:05:57Z | 2026-09-27T11:06:07Z | `{"originalEventId":"5172a5f4-4e9a-33f2-b168-145bac156598","correlationId":"f6dc9c7d-3e77-3a7f-8778-16f8c193d6c2","outcome":"FAILED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 2006d561-909d-3657-a7da-658a4a36b602 | 2026-09-27T11:06:57Z | 2026-09-27T11:07:07Z | `{"originalEventId":"5172a5f4-4e9a-33f2-b168-145bac156598","correlationId":"f6dc9c7d-3e77-3a7f-8778-16f8c193d6c2","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"f55aac6e-2c97-3b28-baa3-abe6c2f9199d","copyId":"3bfa6be4-dcd8-3a48-865a-b4fadb430b89","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","scopeId":"2090c4fa-70ac-3ffb-9c02-c433988311a5","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"3bfa6be4-dcd8-3a48-865a-b4fadb430b89","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3bfa6be4-dcd8-3a48-865a-b4fadb430b89","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3bfa6be4-dcd8-3a48-865a-b4fadb430b89","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3bfa6be4-dcd8-3a48-865a-b4fadb430b89","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3bfa6be4-dcd8-3a48-865a-b4fadb430b89","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3bfa6be4-dcd8-3a48-865a-b4fadb430b89","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3bfa6be4-dcd8-3a48-865a-b4fadb430b89","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"3bfa6be4-dcd8-3a48-865a-b4fadb430b89"},{"kind":"sourceEvent","id":"3b759a72-5d52-39a8-959f-ffa5e7fa06f2"},{"kind":"sourceEvent","id":"2d053a7c-a853-3d67-aefd-c4c31a3fa06d"},{"kind":"sourceEvent","id":"1d7ac15b-333e-341e-9120-3ab7c77ab903"},{"kind":"sourceEvent","id":"eea2f151-90f7-3a30-bf40-90c848358d55"},{"kind":"sourceEvent","id":"9b9b3434-6a76-3c5f-a5d4-08cd89afe049"},{"kind":"sourceEvent","id":"253b6468-555b-3e95-8927-4c381e33f015"},{"kind":"sourceEvent","id":"50914834-d6fb-3ca0-ac4f-c2429d0f67f8"},{"kind":"sourceEvent","id":"5172a5f4-4e9a-33f2-b168-145bac156598"},{"kind":"sourceEvent","id":"b09cf83f-32a5-374c-a1df-1f48b5de6016"},{"kind":"sourceEvent","id":"2006d561-909d-3657-a7da-658a4a36b602"},{"kind":"observation","id":"f55aac6e-2c97-3b28-baa3-abe6c2f9199d"},{"kind":"inventoryScope","id":"2090c4fa-70ac-3ffb-9c02-c433988311a5"},{"kind":"sourceAvailability","id":"3bfa6be4-dcd8-3a48-865a-b4fadb430b89/LOANS"},{"kind":"sourceAvailability","id":"3bfa6be4-dcd8-3a48-865a-b4fadb430b89/RETURNS"},{"kind":"sourceAvailability","id":"3bfa6be4-dcd8-3a48-865a-b4fadb430b89/RECEIPTS"},{"kind":"sourceAvailability","id":"3bfa6be4-dcd8-3a48-865a-b4fadb430b89/TRANSFERS"},{"kind":"sourceAvailability","id":"3bfa6be4-dcd8-3a48-865a-b4fadb430b89/REPAIRS"},{"kind":"sourceAvailability","id":"3bfa6be4-dcd8-3a48-865a-b4fadb430b89/INTEGRATIONS"},{"kind":"sourceAvailability","id":"3bfa6be4-dcd8-3a48-865a-b4fadb430b89/INVENTORY"}]`

- Session : `{"scopeId":"2090c4fa-70ac-3ffb-9c02-c433988311a5","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

## Cas cc3d139d-d705-3cab-8fe2-e34b6db1e6d4 — dossier d'écart

- Exemplaire : `9f56b29e-bf60-3db4-b4cd-ae3d8bbb7416`
- Famille privée : PRET_NON_PROJETE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"096d30ac-67ac-3d0b-bbb9-6830758346f9","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"18070c24-d9f2-37fa-a270-d26a9561ad0c","copyId":"9f56b29e-bf60-3db4-b4cd-ae3d8bbb7416","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T11:09:16Z","recordedAt":"2026-09-27T11:09:26Z","payload":{"originalEventId":"d57ad97f-51d5-38e4-8101-9c8afda667b6","correlationId":"f5ec3277-dea4-3b43-811b-d4255ce5d388","outcome":"FAILED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"d57ad97f-51d5-38e4-8101-9c8afda667b6","physicalStatus":"ON_LOAN","physicalSiteId":"CENTRE","physicalLocationId":null,"projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État du stock informatique : `{"copyId":"9f56b29e-bf60-3db4-b4cd-ae3d8bbb7416","status":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","sourceEventIds":["58781b4e-f853-3617-baa2-15de3ae56e2c","ea564d39-0d09-3e11-a1fa-d1f40e2718fd"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 633e4f94-540e-3c58-92e1-35c0aa613fe2 | 2026-06-02T10:14:24Z | 2026-06-02T10:14:34Z | `{"operationId":"49523988-4a0d-34fc-9866-b64ac4711746","correlationId":"390655b6-8b75-3a57-955d-d44672be3d31","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"2c73c666-ddfe-3a46-99be-6e7653698fa0"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 6f9a3c51-6b3c-35c2-8e69-52f35c553289 | 2026-06-03T10:14:24Z | 2026-06-03T10:14:34Z | `{"originalEventId":"633e4f94-540e-3c58-92e1-35c0aa613fe2","correlationId":"390655b6-8b75-3a57-955d-d44672be3d31","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 51bae953-10c8-3344-baed-bf38a4936658 | 2026-08-01T10:14:24Z | 2026-08-01T10:14:34Z | `{"operationId":"4bcebced-07d4-3434-b7bd-f15c470f679b","correlationId":"e724404e-faac-3f2f-ac2e-6dd40f717c51","siteId":"CENTRE","locationId":null,"loanId":"2354396e-3503-3995-be38-0062bd0d852c"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 329b2aa7-7ef5-3b8f-ac15-f90cbc109155 | 2026-08-01T10:15:24Z | 2026-08-01T10:15:34Z | `{"originalEventId":"51bae953-10c8-3344-baed-bf38a4936658","correlationId":"e724404e-faac-3f2f-ac2e-6dd40f717c51","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | ad8ef5f0-8809-3a3b-b0e2-0cc9fa8e39f2 | 2026-08-21T10:14:24Z | 2026-08-21T10:14:34Z | `{"operationId":"3e219155-a314-38bb-ba4d-d04df75a7c28","correlationId":"014897b5-7768-365a-8e42-f980f4490fa2","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"2354396e-3503-3995-be38-0062bd0d852c"}` |
| Retour reçu (`ReturnReceived`) | 58781b4e-f853-3617-baa2-15de3ae56e2c | 2026-08-21T10:14:54Z | 2026-08-21T10:15:04Z | `{"operationId":"5ac690fb-3b95-3af6-81ef-c41a36f10ff4","correlationId":"3544c34c-b1f8-3dd4-af0a-8d51b2538e95","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"2354396e-3503-3995-be38-0062bd0d852c"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | ea564d39-0d09-3e11-a1fa-d1f40e2718fd | 2026-08-22T10:14:24Z | 2026-08-22T10:14:34Z | `{"originalEventId":"58781b4e-f853-3617-baa2-15de3ae56e2c","correlationId":"3544c34c-b1f8-3dd4-af0a-8d51b2538e95","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | d57ad97f-51d5-38e4-8101-9c8afda667b6 | 2026-09-27T11:08:46Z | 2026-09-27T11:08:56Z | `{"operationId":"f50c377a-3ee8-3ed5-aa25-a0bdc1e8c76e","correlationId":"f5ec3277-dea4-3b43-811b-d4255ce5d388","siteId":"CENTRE","locationId":null,"loanId":"d2313fa2-11eb-3bb6-b02f-bd04b3cb4aa5"}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"9f56b29e-bf60-3db4-b4cd-ae3d8bbb7416","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9f56b29e-bf60-3db4-b4cd-ae3d8bbb7416","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9f56b29e-bf60-3db4-b4cd-ae3d8bbb7416","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9f56b29e-bf60-3db4-b4cd-ae3d8bbb7416","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9f56b29e-bf60-3db4-b4cd-ae3d8bbb7416","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9f56b29e-bf60-3db4-b4cd-ae3d8bbb7416","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"9f56b29e-bf60-3db4-b4cd-ae3d8bbb7416","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"9f56b29e-bf60-3db4-b4cd-ae3d8bbb7416"},{"kind":"sourceEvent","id":"633e4f94-540e-3c58-92e1-35c0aa613fe2"},{"kind":"sourceEvent","id":"6f9a3c51-6b3c-35c2-8e69-52f35c553289"},{"kind":"sourceEvent","id":"51bae953-10c8-3344-baed-bf38a4936658"},{"kind":"sourceEvent","id":"329b2aa7-7ef5-3b8f-ac15-f90cbc109155"},{"kind":"sourceEvent","id":"ad8ef5f0-8809-3a3b-b0e2-0cc9fa8e39f2"},{"kind":"sourceEvent","id":"58781b4e-f853-3617-baa2-15de3ae56e2c"},{"kind":"sourceEvent","id":"ea564d39-0d09-3e11-a1fa-d1f40e2718fd"},{"kind":"sourceEvent","id":"d57ad97f-51d5-38e4-8101-9c8afda667b6"},{"kind":"sourceAvailability","id":"9f56b29e-bf60-3db4-b4cd-ae3d8bbb7416/LOANS"},{"kind":"sourceAvailability","id":"9f56b29e-bf60-3db4-b4cd-ae3d8bbb7416/RETURNS"},{"kind":"sourceAvailability","id":"9f56b29e-bf60-3db4-b4cd-ae3d8bbb7416/RECEIPTS"},{"kind":"sourceAvailability","id":"9f56b29e-bf60-3db4-b4cd-ae3d8bbb7416/TRANSFERS"},{"kind":"sourceAvailability","id":"9f56b29e-bf60-3db4-b4cd-ae3d8bbb7416/REPAIRS"},{"kind":"sourceAvailability","id":"9f56b29e-bf60-3db4-b4cd-ae3d8bbb7416/INTEGRATIONS"},{"kind":"sourceAvailability","id":"9f56b29e-bf60-3db4-b4cd-ae3d8bbb7416/INVENTORY"},{"kind":"inventoryScope","id":"7d46c7a4-f137-377d-8437-5d11eeb4049d"}]`

- Session : `{"scopeId":"7d46c7a4-f137-377d-8437-5d11eeb4049d","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas ce3e6fd3-7b80-35f0-b12c-40023125a97e — dossier d'écart

- Exemplaire : `10198b48-40d0-38d9-9742-c2b60d2a6fff`
- Famille privée : DEUX_CAUSES ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"79289131-a964-362a-a5ea-f04a6c52d65c","kind":"STATUS_MISMATCH","expectedValue":"IN_REPAIR","observedValue":"IN_REPAIR"},{"symptomId":"9e26debc-821a-3971-8b47-9503cbd47e60","kind":"LOCATION_MISMATCH","expectedValue":"14f2d626-9459-3fee-9d3a-daf738f7e59a","observedValue":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["REPAIR_LOCATION_CHANGED"],"hiddenSourceEvents":[],"mechanism":"Réparation engagée puis déplacement distinct de l'atelier vers la réserve sans mise à jour de l'affectation de réparation.","businessEventId":"07cb41ba-fbd0-34d0-b96d-239f1839bcd5","physicalStatus":"IN_REPAIR","physicalSiteId":"CENTRE","physicalLocationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_LOCATION_MISMATCH"],"explanation":"Réparation correctement projetée après reprise ; seul le déplacement hors atelier reste étayé.","forbiddenClaims":["Deux causes confirmées par un scan ambigu","Vol"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"10198b48-40d0-38d9-9742-c2b60d2a6fff","status":"IN_REPAIR","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","sourceEventIds":["07cb41ba-fbd0-34d0-b96d-239f1839bcd5","016ef53e-33f4-3d2e-96db-42a2a9e91a44"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 0b53a880-e6c9-3e3c-9ee5-5fed43a79302 | 2026-06-02T04:35:24Z | 2026-06-02T04:35:34Z | `{"operationId":"fba89085-fe04-3621-b2d5-a8e6640b0aee","correlationId":"3a3524af-ff3a-3c63-b2d0-4ac0d85756fd","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"9742c790-e00b-3069-9663-e56983918205"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 5656dfbe-4052-3a0f-9e75-1b2e5027dc4c | 2026-06-03T04:35:24Z | 2026-06-03T04:35:34Z | `{"originalEventId":"0b53a880-e6c9-3e3c-9ee5-5fed43a79302","correlationId":"3a3524af-ff3a-3c63-b2d0-4ac0d85756fd","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | d1750bee-9960-3c32-aed0-6e30e3f46de2 | 2026-08-01T04:35:24Z | 2026-08-01T04:35:34Z | `{"operationId":"3b4fe4cc-c657-341e-a36b-b15a575511ee","correlationId":"e0bc01c0-0361-3dd8-a6ff-1d7aacbc657f","siteId":"CENTRE","locationId":null,"loanId":"183076bf-ebdf-3d75-8485-679c3d7b8360"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | ad1f5f4e-35f9-32b5-a5ad-962254d0b4d3 | 2026-08-01T04:36:24Z | 2026-08-01T04:36:34Z | `{"originalEventId":"d1750bee-9960-3c32-aed0-6e30e3f46de2","correlationId":"e0bc01c0-0361-3dd8-a6ff-1d7aacbc657f","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 0f02d98b-b872-33b2-816e-8b4d9e7f28e5 | 2026-08-21T04:35:24Z | 2026-08-21T04:35:34Z | `{"operationId":"a679de0c-3cac-343a-af80-f62d5bdb071a","correlationId":"11bb68ab-f380-3cae-b1db-6864efeefc7a","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"183076bf-ebdf-3d75-8485-679c3d7b8360"}` |
| Retour reçu (`ReturnReceived`) | 61189bbe-be5e-312d-8cdb-adbae0484a38 | 2026-08-21T04:35:54Z | 2026-08-21T04:36:04Z | `{"operationId":"993a9df7-62c7-3f5e-a5c1-0ebca1a93535","correlationId":"6655d364-7721-3771-9074-fbfa874395ac","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"183076bf-ebdf-3d75-8485-679c3d7b8360"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 40aa6037-b8e5-3fdd-85d2-9ba0672c7ad4 | 2026-08-22T04:35:24Z | 2026-08-22T04:35:34Z | `{"originalEventId":"61189bbe-be5e-312d-8cdb-adbae0484a38","correlationId":"6655d364-7721-3771-9074-fbfa874395ac","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Réparation engagée (`RepairStarted`) | 07cb41ba-fbd0-34d0-b96d-239f1839bcd5 | 2026-09-27T10:54:04Z | 2026-09-27T10:54:14Z | `{"operationId":"e9712b2c-b9ec-311c-8f1c-b54d0e876e53","correlationId":"5988d65d-b310-3dbc-9c87-54ebed841dc0","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","repairId":"e3317d69-3ed4-3bbc-a1dc-dfa525f185c4"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 2ee2b632-dd05-3737-83af-f3de8b39abb2 | 2026-09-27T10:54:34Z | 2026-09-27T10:54:44Z | `{"originalEventId":"07cb41ba-fbd0-34d0-b96d-239f1839bcd5","correlationId":"5988d65d-b310-3dbc-9c87-54ebed841dc0","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 016ef53e-33f4-3d2e-96db-42a2a9e91a44 | 2026-09-27T10:55:34Z | 2026-09-27T10:55:44Z | `{"originalEventId":"07cb41ba-fbd0-34d0-b96d-239f1839bcd5","correlationId":"5988d65d-b310-3dbc-9c87-54ebed841dc0","outcome":"APPLIED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"d5dee8f3-91b9-3eb7-9597-cfb9f3241455","copyId":"10198b48-40d0-38d9-9742-c2b60d2a6fff","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","scopeId":"a69d5129-eb66-367a-a5ac-36499b1ccf06","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"10198b48-40d0-38d9-9742-c2b60d2a6fff","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"10198b48-40d0-38d9-9742-c2b60d2a6fff","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"10198b48-40d0-38d9-9742-c2b60d2a6fff","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"10198b48-40d0-38d9-9742-c2b60d2a6fff","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"10198b48-40d0-38d9-9742-c2b60d2a6fff","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"10198b48-40d0-38d9-9742-c2b60d2a6fff","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"10198b48-40d0-38d9-9742-c2b60d2a6fff","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"10198b48-40d0-38d9-9742-c2b60d2a6fff"},{"kind":"sourceEvent","id":"0b53a880-e6c9-3e3c-9ee5-5fed43a79302"},{"kind":"sourceEvent","id":"5656dfbe-4052-3a0f-9e75-1b2e5027dc4c"},{"kind":"sourceEvent","id":"d1750bee-9960-3c32-aed0-6e30e3f46de2"},{"kind":"sourceEvent","id":"ad1f5f4e-35f9-32b5-a5ad-962254d0b4d3"},{"kind":"sourceEvent","id":"0f02d98b-b872-33b2-816e-8b4d9e7f28e5"},{"kind":"sourceEvent","id":"61189bbe-be5e-312d-8cdb-adbae0484a38"},{"kind":"sourceEvent","id":"40aa6037-b8e5-3fdd-85d2-9ba0672c7ad4"},{"kind":"sourceEvent","id":"07cb41ba-fbd0-34d0-b96d-239f1839bcd5"},{"kind":"sourceEvent","id":"2ee2b632-dd05-3737-83af-f3de8b39abb2"},{"kind":"sourceEvent","id":"016ef53e-33f4-3d2e-96db-42a2a9e91a44"},{"kind":"observation","id":"d5dee8f3-91b9-3eb7-9597-cfb9f3241455"},{"kind":"inventoryScope","id":"a69d5129-eb66-367a-a5ac-36499b1ccf06"},{"kind":"sourceAvailability","id":"10198b48-40d0-38d9-9742-c2b60d2a6fff/LOANS"},{"kind":"sourceAvailability","id":"10198b48-40d0-38d9-9742-c2b60d2a6fff/RETURNS"},{"kind":"sourceAvailability","id":"10198b48-40d0-38d9-9742-c2b60d2a6fff/RECEIPTS"},{"kind":"sourceAvailability","id":"10198b48-40d0-38d9-9742-c2b60d2a6fff/TRANSFERS"},{"kind":"sourceAvailability","id":"10198b48-40d0-38d9-9742-c2b60d2a6fff/REPAIRS"},{"kind":"sourceAvailability","id":"10198b48-40d0-38d9-9742-c2b60d2a6fff/INTEGRATIONS"},{"kind":"sourceAvailability","id":"10198b48-40d0-38d9-9742-c2b60d2a6fff/INVENTORY"},{"kind":"inventoryScope","id":"ec50a541-89c5-30b3-950e-99a9f4dfc9f5"},{"kind":"inventoryScope","id":"08896ffd-fe25-3d58-800a-49293c383852"},{"kind":"inventoryScope","id":"d60e9f22-40a9-3088-8d67-f59de1f0f246"},{"kind":"inventoryScope","id":"820c3d0f-aace-30c6-b8e1-be88b5aeb758"}]`

- Session : `{"scopeId":"a69d5129-eb66-367a-a5ac-36499b1ccf06","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"ec50a541-89c5-30b3-950e-99a9f4dfc9f5","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"08896ffd-fe25-3d58-800a-49293c383852","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"d60e9f22-40a9-3088-8d67-f59de1f0f246","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"820c3d0f-aace-30c6-b8e1-be88b5aeb758","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas d21021ec-5181-3fec-ad4f-f6057913f128 — dossier d'écart

- Exemplaire : `2b61dbc9-d948-3c63-9ac5-e9dc7d2b53a5`
- Famille privée : RETOUR_NON_PROJETE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"72576f46-4779-38cc-bf59-e07adcd7ddfe","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"5236221c-1bdd-36bf-9e94-94c922a7c563","physicalStatus":"RECEIVING","physicalSiteId":"SUD","physicalLocationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"2b61dbc9-d948-3c63-9ac5-e9dc7d2b53a5","status":"RECEIVING","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","sourceEventIds":["5236221c-1bdd-36bf-9e94-94c922a7c563","5c45b80c-cf81-3378-b16e-ac7a35df8559"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 20dd0107-79d9-3206-88dc-4d7b11df42fb | 2026-06-01T22:38:20Z | 2026-06-01T22:38:30Z | `{"operationId":"b678a981-5684-3ccb-9ba9-febb4102e3ee","correlationId":"6dd57d7e-bfb7-3834-b76e-68792ba9595b","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"7a22a655-4fce-3e79-9e76-6c0d4cc3ef3e"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | e1eb82c0-1cdb-3bbf-ac44-47ed53b55edc | 2026-06-02T22:38:20Z | 2026-06-02T22:38:30Z | `{"originalEventId":"20dd0107-79d9-3206-88dc-4d7b11df42fb","correlationId":"6dd57d7e-bfb7-3834-b76e-68792ba9595b","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | fdfa37e1-5933-3800-91fe-50b79473e590 | 2026-07-31T22:38:20Z | 2026-07-31T22:38:30Z | `{"operationId":"8f020e27-601d-3cab-8deb-32f5c5415669","correlationId":"06c547cf-a12a-3bd2-87b1-1c148c47e9c2","siteId":"SUD","locationId":null,"loanId":"75317d11-0e60-321f-b189-76bdc32dae7b"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 3ce0f9b3-8b98-3f65-9e3d-3512c5df625c | 2026-07-31T22:39:20Z | 2026-07-31T22:39:30Z | `{"originalEventId":"fdfa37e1-5933-3800-91fe-50b79473e590","correlationId":"06c547cf-a12a-3bd2-87b1-1c148c47e9c2","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | fe13a982-019a-3674-bded-fdb25f4e6553 | 2026-08-20T22:38:20Z | 2026-08-20T22:38:30Z | `{"operationId":"c8b65cbd-d0d7-3687-9a04-bf423185fa37","correlationId":"0e7a4ac8-1717-3c22-8197-068aafc94300","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"75317d11-0e60-321f-b189-76bdc32dae7b"}` |
| Retour reçu (`ReturnReceived`) | d97b8e20-daf5-371f-82a9-58b8f6648069 | 2026-08-20T22:38:50Z | 2026-08-20T22:39:00Z | `{"operationId":"ae16db1e-5eaf-3ce3-8b86-51b443d2d788","correlationId":"c711890d-3e8f-3e38-9d4f-5c519a53be4a","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"75317d11-0e60-321f-b189-76bdc32dae7b"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | cee2aaf2-8851-3026-947e-62e5b188987a | 2026-08-21T22:38:20Z | 2026-08-21T22:38:30Z | `{"originalEventId":"d97b8e20-daf5-371f-82a9-58b8f6648069","correlationId":"c711890d-3e8f-3e38-9d4f-5c519a53be4a","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 0432726b-eae3-3ef8-8585-86590f73a752 | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"9da1c868-84bd-3841-822b-cc9a792a2557","correlationId":"70bf8ea9-6b9f-3be1-b9b2-1b16500e06dc","siteId":"SUD","locationId":null,"loanId":"35522cac-a703-3da2-9e5b-f6b9281f185f"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 09a27149-e7b9-3ddf-aba3-e22130c120c8 | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"0432726b-eae3-3ef8-8585-86590f73a752","correlationId":"70bf8ea9-6b9f-3be1-b9b2-1b16500e06dc","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 5b2275d0-ce71-3846-9334-6c82f8579c48 | 2026-09-27T11:38:42Z | 2026-09-27T11:38:52Z | `{"operationId":"3cf29532-9b72-3f10-82de-044426f282be","correlationId":"d015d95e-b7e9-3203-a9be-354e4b4260de","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"35522cac-a703-3da2-9e5b-f6b9281f185f"}` |
| Retour reçu (`ReturnReceived`) | 5236221c-1bdd-36bf-9e94-94c922a7c563 | 2026-09-27T11:39:12Z | 2026-09-27T11:39:22Z | `{"operationId":"fa51cb16-2d00-39ad-80f7-3cf0a705b5ff","correlationId":"94439a72-51cb-3d2b-9fcb-2eaeb4766c43","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"35522cac-a703-3da2-9e5b-f6b9281f185f"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | c1efef61-ad05-31cc-b954-44087ae11c03 | 2026-09-27T11:39:42Z | 2026-09-27T11:39:52Z | `{"originalEventId":"5236221c-1bdd-36bf-9e94-94c922a7c563","correlationId":"94439a72-51cb-3d2b-9fcb-2eaeb4766c43","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 5c45b80c-cf81-3378-b16e-ac7a35df8559 | 2026-09-27T11:40:42Z | 2026-09-27T11:40:52Z | `{"originalEventId":"5236221c-1bdd-36bf-9e94-94c922a7c563","correlationId":"94439a72-51cb-3d2b-9fcb-2eaeb4766c43","outcome":"APPLIED","projectedStatus":"RECEIVING","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"8f75f360-d729-3b58-a3f0-d38fbf52e42d","copyId":"2b61dbc9-d948-3c63-9ac5-e9dc7d2b53a5","locationId":"128fab03-f193-3647-a67e-b835a30e7583","scopeId":"b6ef153d-519e-37f9-8165-9d5903dca440","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`
- `{"observationId":"9010c2de-f253-3a60-9334-d218205939de","copyId":"2b61dbc9-d948-3c63-9ac5-e9dc7d2b53a5","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","scopeId":"b843cc87-7292-3352-b7b3-9b822ca2c5c4","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"2b61dbc9-d948-3c63-9ac5-e9dc7d2b53a5","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2b61dbc9-d948-3c63-9ac5-e9dc7d2b53a5","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2b61dbc9-d948-3c63-9ac5-e9dc7d2b53a5","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2b61dbc9-d948-3c63-9ac5-e9dc7d2b53a5","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2b61dbc9-d948-3c63-9ac5-e9dc7d2b53a5","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2b61dbc9-d948-3c63-9ac5-e9dc7d2b53a5","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2b61dbc9-d948-3c63-9ac5-e9dc7d2b53a5","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"2b61dbc9-d948-3c63-9ac5-e9dc7d2b53a5"},{"kind":"sourceEvent","id":"20dd0107-79d9-3206-88dc-4d7b11df42fb"},{"kind":"sourceEvent","id":"e1eb82c0-1cdb-3bbf-ac44-47ed53b55edc"},{"kind":"sourceEvent","id":"fdfa37e1-5933-3800-91fe-50b79473e590"},{"kind":"sourceEvent","id":"3ce0f9b3-8b98-3f65-9e3d-3512c5df625c"},{"kind":"sourceEvent","id":"fe13a982-019a-3674-bded-fdb25f4e6553"},{"kind":"sourceEvent","id":"d97b8e20-daf5-371f-82a9-58b8f6648069"},{"kind":"sourceEvent","id":"cee2aaf2-8851-3026-947e-62e5b188987a"},{"kind":"sourceEvent","id":"0432726b-eae3-3ef8-8585-86590f73a752"},{"kind":"sourceEvent","id":"09a27149-e7b9-3ddf-aba3-e22130c120c8"},{"kind":"sourceEvent","id":"5b2275d0-ce71-3846-9334-6c82f8579c48"},{"kind":"sourceEvent","id":"5236221c-1bdd-36bf-9e94-94c922a7c563"},{"kind":"sourceEvent","id":"c1efef61-ad05-31cc-b954-44087ae11c03"},{"kind":"sourceEvent","id":"5c45b80c-cf81-3378-b16e-ac7a35df8559"},{"kind":"observation","id":"9010c2de-f253-3a60-9334-d218205939de"},{"kind":"inventoryScope","id":"b843cc87-7292-3352-b7b3-9b822ca2c5c4"},{"kind":"observation","id":"8f75f360-d729-3b58-a3f0-d38fbf52e42d"},{"kind":"inventoryScope","id":"b6ef153d-519e-37f9-8165-9d5903dca440"},{"kind":"sourceAvailability","id":"2b61dbc9-d948-3c63-9ac5-e9dc7d2b53a5/LOANS"},{"kind":"sourceAvailability","id":"2b61dbc9-d948-3c63-9ac5-e9dc7d2b53a5/RETURNS"},{"kind":"sourceAvailability","id":"2b61dbc9-d948-3c63-9ac5-e9dc7d2b53a5/RECEIPTS"},{"kind":"sourceAvailability","id":"2b61dbc9-d948-3c63-9ac5-e9dc7d2b53a5/TRANSFERS"},{"kind":"sourceAvailability","id":"2b61dbc9-d948-3c63-9ac5-e9dc7d2b53a5/REPAIRS"},{"kind":"sourceAvailability","id":"2b61dbc9-d948-3c63-9ac5-e9dc7d2b53a5/INTEGRATIONS"},{"kind":"sourceAvailability","id":"2b61dbc9-d948-3c63-9ac5-e9dc7d2b53a5/INVENTORY"},{"kind":"inventoryScope","id":"4e69471d-73b3-3fc3-8e2c-8f1ea95a1336"},{"kind":"inventoryScope","id":"93fcbc2e-9068-39f8-bd18-e051823b3400"}]`

- Session : `{"scopeId":"b843cc87-7292-3352-b7b3-9b822ca2c5c4","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b6ef153d-519e-37f9-8165-9d5903dca440","locationId":"128fab03-f193-3647-a67e-b835a30e7583","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"4e69471d-73b3-3fc3-8e2c-8f1ea95a1336","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"93fcbc2e-9068-39f8-bd18-e051823b3400","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas d741c491-a82e-3cf5-9e2a-1dddeb27cab2 — dossier d'écart

- Exemplaire : `f2a4e565-e2d8-3c82-8100-d67fda186862`
- Famille privée : TRANSFERT_INCOHERENT ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"c2584de6-ecdd-305e-aead-2baeae96ac03","kind":"STATUS_MISMATCH","expectedValue":"IN_TRANSIT","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"7e8cf78b-256f-33e3-8ace-a9dd18b2e9f4","copyId":"f2a4e565-e2d8-3c82-8100-d67fda186862","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T06:51:56Z","recordedAt":"2026-09-27T06:52:06Z","payload":{"originalEventId":"c36dbcbe-2e3c-3e0a-8edd-f2cdc015fdd3","correlationId":"f39dabef-649f-3945-8351-d7f168582a12","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"c36dbcbe-2e3c-3e0a-8edd-f2cdc015fdd3","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État du stock informatique : `{"copyId":"f2a4e565-e2d8-3c82-8100-d67fda186862","status":"IN_TRANSIT","siteId":"CENTRE","locationId":null,"sourceEventIds":["90240b0b-6f24-3338-b57a-d2fd1cb03986","dc6992d5-cb07-3d5a-b972-7c5282ee249b"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 18db9781-4a4d-3cef-9c4e-7ab9f5055800 | 2026-06-02T03:02:21Z | 2026-06-02T03:02:31Z | `{"operationId":"64004763-e466-3ea4-b8b6-dcfb9198ce3f","correlationId":"518acca4-81ca-3389-8e2d-260f39c7c5a5","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"f723b8cd-283c-32e9-9ef4-62569c8a7afa"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 87dbba3f-9322-3185-bcb4-61cc7c9cd3f8 | 2026-06-03T03:02:21Z | 2026-06-03T03:02:31Z | `{"originalEventId":"18db9781-4a4d-3cef-9c4e-7ab9f5055800","correlationId":"518acca4-81ca-3389-8e2d-260f39c7c5a5","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | e6c964dd-ff96-3f0b-a2b0-3ae0880fdcde | 2026-08-01T03:02:21Z | 2026-08-01T03:02:31Z | `{"operationId":"3360c38c-e36c-3810-9c97-4055dfea73b3","correlationId":"9f0ce190-b153-3a4f-ada3-e893fa7fb9a9","siteId":"CENTRE","locationId":null,"loanId":"ad29a759-b6ea-3ad1-81ca-da581066bbe7"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 2113d18d-c7f7-3fc1-b006-037b13ec62ba | 2026-08-01T03:03:21Z | 2026-08-01T03:03:31Z | `{"originalEventId":"e6c964dd-ff96-3f0b-a2b0-3ae0880fdcde","correlationId":"9f0ce190-b153-3a4f-ada3-e893fa7fb9a9","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 2b417e66-8fee-37d5-9112-dbd0774e8dc7 | 2026-08-21T03:02:21Z | 2026-08-21T03:02:31Z | `{"operationId":"1a0511b4-cce1-3006-a719-bb8363aac814","correlationId":"84fd7ec5-bbd9-3031-b667-7c4335316264","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"ad29a759-b6ea-3ad1-81ca-da581066bbe7"}` |
| Retour reçu (`ReturnReceived`) | 48743865-6f3a-389a-b4d5-bab23e3ad7b4 | 2026-08-21T03:02:51Z | 2026-08-21T03:03:01Z | `{"operationId":"65e1f4d1-d83c-3c11-8dc1-3d86032b10ad","correlationId":"0e175c27-ba8c-3242-b132-e776b9d017de","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"ad29a759-b6ea-3ad1-81ca-da581066bbe7"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 2eb369d9-4c37-368b-98f1-45967efe9f28 | 2026-08-22T03:02:21Z | 2026-08-22T03:02:31Z | `{"originalEventId":"48743865-6f3a-389a-b4d5-bab23e3ad7b4","correlationId":"0e175c27-ba8c-3242-b132-e776b9d017de","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Transfert expédié (`TransferDispatched`) | 90240b0b-6f24-3338-b57a-d2fd1cb03986 | 2026-09-26T06:51:26Z | 2026-09-26T06:51:36Z | `{"operationId":"b7f4fa1c-3c15-37df-97af-659f5678b556","correlationId":"de63f5fa-297a-3b99-a128-9c6365809c6c","siteId":"CENTRE","locationId":null,"transferId":"09a96bf2-869f-327a-9106-ba64993e99e9","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | dc6992d5-cb07-3d5a-b972-7c5282ee249b | 2026-09-26T06:52:26Z | 2026-09-26T06:52:36Z | `{"originalEventId":"90240b0b-6f24-3338-b57a-d2fd1cb03986","correlationId":"de63f5fa-297a-3b99-a128-9c6365809c6c","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| Transfert reçu (`TransferReceived`) | c36dbcbe-2e3c-3e0a-8edd-f2cdc015fdd3 | 2026-09-27T06:51:26Z | 2026-09-27T06:51:36Z | `{"operationId":"874a50c2-3c9f-3afd-80de-758502c6b462","correlationId":"f39dabef-649f-3945-8351-d7f168582a12","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","transferId":"09a96bf2-869f-327a-9106-ba64993e99e9","fromSiteId":"CENTRE","toSiteId":"NORD"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"7e455e6e-bd72-38d4-96c2-f9908e2878db","copyId":"f2a4e565-e2d8-3c82-8100-d67fda186862","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","scopeId":"ff7cadbd-2e34-31a2-a383-62602b9d45d5","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"f2a4e565-e2d8-3c82-8100-d67fda186862","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f2a4e565-e2d8-3c82-8100-d67fda186862","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"f2a4e565-e2d8-3c82-8100-d67fda186862","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f2a4e565-e2d8-3c82-8100-d67fda186862","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f2a4e565-e2d8-3c82-8100-d67fda186862","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f2a4e565-e2d8-3c82-8100-d67fda186862","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f2a4e565-e2d8-3c82-8100-d67fda186862","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"f2a4e565-e2d8-3c82-8100-d67fda186862"},{"kind":"sourceEvent","id":"18db9781-4a4d-3cef-9c4e-7ab9f5055800"},{"kind":"sourceEvent","id":"87dbba3f-9322-3185-bcb4-61cc7c9cd3f8"},{"kind":"sourceEvent","id":"e6c964dd-ff96-3f0b-a2b0-3ae0880fdcde"},{"kind":"sourceEvent","id":"2113d18d-c7f7-3fc1-b006-037b13ec62ba"},{"kind":"sourceEvent","id":"2b417e66-8fee-37d5-9112-dbd0774e8dc7"},{"kind":"sourceEvent","id":"48743865-6f3a-389a-b4d5-bab23e3ad7b4"},{"kind":"sourceEvent","id":"2eb369d9-4c37-368b-98f1-45967efe9f28"},{"kind":"sourceEvent","id":"90240b0b-6f24-3338-b57a-d2fd1cb03986"},{"kind":"sourceEvent","id":"dc6992d5-cb07-3d5a-b972-7c5282ee249b"},{"kind":"sourceEvent","id":"c36dbcbe-2e3c-3e0a-8edd-f2cdc015fdd3"},{"kind":"observation","id":"7e455e6e-bd72-38d4-96c2-f9908e2878db"},{"kind":"inventoryScope","id":"ff7cadbd-2e34-31a2-a383-62602b9d45d5"},{"kind":"sourceAvailability","id":"f2a4e565-e2d8-3c82-8100-d67fda186862/LOANS"},{"kind":"sourceAvailability","id":"f2a4e565-e2d8-3c82-8100-d67fda186862/RETURNS"},{"kind":"sourceAvailability","id":"f2a4e565-e2d8-3c82-8100-d67fda186862/RECEIPTS"},{"kind":"sourceAvailability","id":"f2a4e565-e2d8-3c82-8100-d67fda186862/TRANSFERS"},{"kind":"sourceAvailability","id":"f2a4e565-e2d8-3c82-8100-d67fda186862/REPAIRS"},{"kind":"sourceAvailability","id":"f2a4e565-e2d8-3c82-8100-d67fda186862/INTEGRATIONS"},{"kind":"sourceAvailability","id":"f2a4e565-e2d8-3c82-8100-d67fda186862/INVENTORY"}]`

- Session : `{"scopeId":"ff7cadbd-2e34-31a2-a383-62602b9d45d5","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas d7eb392e-fa0d-3a70-ba0f-a01912dd4e30 — dossier d'écart

- Exemplaire : `78fc6ddc-4052-356a-9dd2-b314e22210a5`
- Famille privée : TRANSFERT_INCOHERENT ; variante : POSITIVE
- Symptômes : `[{"symptomId":"38490841-87f6-37ba-9e2c-d886a3e0de1e","kind":"STATUS_MISMATCH","expectedValue":"IN_TRANSIT","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"28c38cbf-ec9d-3041-ad4f-56a70ce06bd7","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["TRANSFER_RECEIPT_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"78fc6ddc-4052-356a-9dd2-b314e22210a5","status":"IN_TRANSIT","siteId":"CENTRE","locationId":null,"sourceEventIds":["ce2432b7-806b-39df-ae03-0e4e16f2bfa1","aac7e0a2-e50f-3c1d-a0f3-358e83e97cfd"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 7a86af91-ab5b-3657-9bc5-e07327a74c81 | 2026-06-01T14:27:48Z | 2026-06-01T14:27:58Z | `{"operationId":"4f526518-b3b3-3ded-afca-0121f3152567","correlationId":"c61fa4b0-11c5-34cf-abf8-5459883d707b","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"c55f6fb1-a8f1-3ab3-8268-ad6c7bac8a62"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 73790874-b25c-3847-9183-5e4365988a68 | 2026-06-02T14:27:48Z | 2026-06-02T14:27:58Z | `{"originalEventId":"7a86af91-ab5b-3657-9bc5-e07327a74c81","correlationId":"c61fa4b0-11c5-34cf-abf8-5459883d707b","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 533a026d-9647-38aa-99d3-57c5b60d9774 | 2026-07-31T14:27:48Z | 2026-07-31T14:27:58Z | `{"operationId":"5b328193-9ab9-3998-9d81-ff4652f0ab8e","correlationId":"c994f0a6-f7e1-33b1-afbc-5086ef6c3f1d","siteId":"CENTRE","locationId":null,"loanId":"d041d6d0-4c0a-355d-ab30-cfc37ac942a1"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | c93fb3a5-ff98-3d8f-be33-97813eb73b11 | 2026-07-31T14:28:48Z | 2026-07-31T14:28:58Z | `{"originalEventId":"533a026d-9647-38aa-99d3-57c5b60d9774","correlationId":"c994f0a6-f7e1-33b1-afbc-5086ef6c3f1d","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 7e46dd79-d969-3ec0-8493-124652fdb9b2 | 2026-08-20T14:27:48Z | 2026-08-20T14:27:58Z | `{"operationId":"64d173b5-7995-3e3d-a66f-85755a5820c7","correlationId":"3946d5cd-0d3a-35f5-aa79-0b4392b06c64","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"d041d6d0-4c0a-355d-ab30-cfc37ac942a1"}` |
| Retour reçu (`ReturnReceived`) | 9a7007ef-401f-3a2e-951b-142643e94afd | 2026-08-20T14:28:18Z | 2026-08-20T14:28:28Z | `{"operationId":"214adbf8-4697-30c7-9f69-3a43ab6c6c20","correlationId":"7cea59f5-5e8d-336f-9d09-0c7669f62342","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"d041d6d0-4c0a-355d-ab30-cfc37ac942a1"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | c63f90c9-e651-3c7e-a341-6da3fb9b5834 | 2026-08-21T14:27:48Z | 2026-08-21T14:27:58Z | `{"originalEventId":"9a7007ef-401f-3a2e-951b-142643e94afd","correlationId":"7cea59f5-5e8d-336f-9d09-0c7669f62342","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Transfert expédié (`TransferDispatched`) | ce2432b7-806b-39df-ae03-0e4e16f2bfa1 | 2026-09-26T10:26:52Z | 2026-09-26T10:27:02Z | `{"operationId":"78fa5c90-0029-34e5-96c4-1dbfacf39ca1","correlationId":"3f1a233f-9d4c-354b-9f74-472994011ec9","siteId":"CENTRE","locationId":null,"transferId":"cb588fce-6542-3592-af1f-d3e419aefeb7","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | aac7e0a2-e50f-3c1d-a0f3-358e83e97cfd | 2026-09-26T10:27:52Z | 2026-09-26T10:28:02Z | `{"originalEventId":"ce2432b7-806b-39df-ae03-0e4e16f2bfa1","correlationId":"3f1a233f-9d4c-354b-9f74-472994011ec9","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| Transfert reçu (`TransferReceived`) | 28c38cbf-ec9d-3041-ad4f-56a70ce06bd7 | 2026-09-27T10:26:52Z | 2026-09-27T10:27:02Z | `{"operationId":"c6c97654-38c3-387e-a4e5-7fb97af168f9","correlationId":"682c94d9-403e-3d2d-888f-99fb8800745c","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","transferId":"cb588fce-6542-3592-af1f-d3e419aefeb7","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | aa7e984a-b906-3865-a06a-4d4bd28f605e | 2026-09-27T10:27:22Z | 2026-09-27T10:27:32Z | `{"originalEventId":"28c38cbf-ec9d-3041-ad4f-56a70ce06bd7","correlationId":"682c94d9-403e-3d2d-888f-99fb8800745c","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"f885b8f8-bdba-330d-94ca-c43b046edabc","copyId":"78fc6ddc-4052-356a-9dd2-b314e22210a5","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","scopeId":"dc947621-590d-3bee-a9d2-851a4ba45547","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"78fc6ddc-4052-356a-9dd2-b314e22210a5","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"78fc6ddc-4052-356a-9dd2-b314e22210a5","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"78fc6ddc-4052-356a-9dd2-b314e22210a5","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"78fc6ddc-4052-356a-9dd2-b314e22210a5","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"78fc6ddc-4052-356a-9dd2-b314e22210a5","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"78fc6ddc-4052-356a-9dd2-b314e22210a5","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"78fc6ddc-4052-356a-9dd2-b314e22210a5","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"78fc6ddc-4052-356a-9dd2-b314e22210a5"},{"kind":"sourceEvent","id":"7a86af91-ab5b-3657-9bc5-e07327a74c81"},{"kind":"sourceEvent","id":"73790874-b25c-3847-9183-5e4365988a68"},{"kind":"sourceEvent","id":"533a026d-9647-38aa-99d3-57c5b60d9774"},{"kind":"sourceEvent","id":"c93fb3a5-ff98-3d8f-be33-97813eb73b11"},{"kind":"sourceEvent","id":"7e46dd79-d969-3ec0-8493-124652fdb9b2"},{"kind":"sourceEvent","id":"9a7007ef-401f-3a2e-951b-142643e94afd"},{"kind":"sourceEvent","id":"c63f90c9-e651-3c7e-a341-6da3fb9b5834"},{"kind":"sourceEvent","id":"ce2432b7-806b-39df-ae03-0e4e16f2bfa1"},{"kind":"sourceEvent","id":"aac7e0a2-e50f-3c1d-a0f3-358e83e97cfd"},{"kind":"sourceEvent","id":"28c38cbf-ec9d-3041-ad4f-56a70ce06bd7"},{"kind":"sourceEvent","id":"aa7e984a-b906-3865-a06a-4d4bd28f605e"},{"kind":"observation","id":"f885b8f8-bdba-330d-94ca-c43b046edabc"},{"kind":"inventoryScope","id":"dc947621-590d-3bee-a9d2-851a4ba45547"},{"kind":"sourceAvailability","id":"78fc6ddc-4052-356a-9dd2-b314e22210a5/LOANS"},{"kind":"sourceAvailability","id":"78fc6ddc-4052-356a-9dd2-b314e22210a5/RETURNS"},{"kind":"sourceAvailability","id":"78fc6ddc-4052-356a-9dd2-b314e22210a5/RECEIPTS"},{"kind":"sourceAvailability","id":"78fc6ddc-4052-356a-9dd2-b314e22210a5/TRANSFERS"},{"kind":"sourceAvailability","id":"78fc6ddc-4052-356a-9dd2-b314e22210a5/REPAIRS"},{"kind":"sourceAvailability","id":"78fc6ddc-4052-356a-9dd2-b314e22210a5/INTEGRATIONS"},{"kind":"sourceAvailability","id":"78fc6ddc-4052-356a-9dd2-b314e22210a5/INVENTORY"}]`

- Session : `{"scopeId":"dc947621-590d-3bee-a9d2-851a4ba45547","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas d8d17fc2-dd7a-3ada-b5e6-2bd4880bb5fd — dossier d'écart

- Exemplaire : `511cc38f-0155-35d9-9e83-fde8ec9f74ae`
- Famille privée : PRET_NON_PROJETE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"be826fee-5942-3afc-b861-16eeed0863b8","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"c693e7d3-ce74-3ad1-aa26-4f349f2e3e18","physicalStatus":"ON_LOAN","physicalSiteId":"CENTRE","physicalLocationId":null,"projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"511cc38f-0155-35d9-9e83-fde8ec9f74ae","status":"ON_LOAN","siteId":"CENTRE","locationId":null,"sourceEventIds":["c693e7d3-ce74-3ad1-aa26-4f349f2e3e18","0e440a91-8d34-38e6-b34a-8eb1bbd5693d"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | b42ee9a8-1a0c-3311-8686-cdbbddd8281d | 2026-06-01T15:51:21Z | 2026-06-01T15:51:31Z | `{"operationId":"0b8b5628-ab40-36a4-83e9-9f7f0b243f40","correlationId":"18a4714d-2cd8-3993-97e0-b1341519c344","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"55b5b5ee-cf09-3c1f-a234-8d092cb64d28"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 02ac61b9-9869-3e11-863c-2cf4570225fd | 2026-06-02T15:51:21Z | 2026-06-02T15:51:31Z | `{"originalEventId":"b42ee9a8-1a0c-3311-8686-cdbbddd8281d","correlationId":"18a4714d-2cd8-3993-97e0-b1341519c344","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 14dac949-8072-38d6-82eb-885ab337dd32 | 2026-07-31T15:51:21Z | 2026-07-31T15:51:31Z | `{"operationId":"dd505a99-5f4c-3797-bc09-90548b76c552","correlationId":"38680000-cbc8-364a-8bd5-72966fd5ad35","siteId":"CENTRE","locationId":null,"loanId":"6e82759c-a4ca-3627-8c40-a21e017db154"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 9e1cc661-9fa8-3f88-be5d-5136f953bf06 | 2026-07-31T15:52:21Z | 2026-07-31T15:52:31Z | `{"originalEventId":"14dac949-8072-38d6-82eb-885ab337dd32","correlationId":"38680000-cbc8-364a-8bd5-72966fd5ad35","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 63a1e8d4-6d21-354e-bab5-2ed087e9de85 | 2026-08-20T15:51:21Z | 2026-08-20T15:51:31Z | `{"operationId":"98aab52e-9411-34dd-ab59-ff26cc2f951c","correlationId":"974c7cbc-32a5-3685-9064-ea66a3b8696e","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"6e82759c-a4ca-3627-8c40-a21e017db154"}` |
| Retour reçu (`ReturnReceived`) | 0bb5cff3-122e-37e7-adee-ff6694700b60 | 2026-08-20T15:51:51Z | 2026-08-20T15:52:01Z | `{"operationId":"d3cb8c41-b056-3f1d-a3ce-a3853a5d909e","correlationId":"35506a51-ff3e-3be0-b15d-82f0af73e666","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"6e82759c-a4ca-3627-8c40-a21e017db154"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | fa8b068c-98fc-3c50-b39a-6aa9c0297183 | 2026-08-21T15:51:21Z | 2026-08-21T15:51:31Z | `{"originalEventId":"0bb5cff3-122e-37e7-adee-ff6694700b60","correlationId":"35506a51-ff3e-3be0-b15d-82f0af73e666","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | c693e7d3-ce74-3ad1-aa26-4f349f2e3e18 | 2026-09-27T06:14:52Z | 2026-09-27T06:15:02Z | `{"operationId":"ce572a78-1bd2-3a7c-8442-19f530e6297b","correlationId":"23d8383b-0cb7-389b-afac-d3aa01db59f4","siteId":"CENTRE","locationId":null,"loanId":"7c9b71cf-518a-3685-bafa-437750a679ee"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | f51ab406-9fb5-3af1-bc9b-0079f410e9f6 | 2026-09-27T06:15:22Z | 2026-09-27T06:15:32Z | `{"originalEventId":"c693e7d3-ce74-3ad1-aa26-4f349f2e3e18","correlationId":"23d8383b-0cb7-389b-afac-d3aa01db59f4","outcome":"FAILED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 0e440a91-8d34-38e6-b34a-8eb1bbd5693d | 2026-09-27T06:16:22Z | 2026-09-27T06:16:32Z | `{"originalEventId":"c693e7d3-ce74-3ad1-aa26-4f349f2e3e18","correlationId":"23d8383b-0cb7-389b-afac-d3aa01db59f4","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"fb8117ad-b02b-3145-94dd-56ef17f7143b","copyId":"511cc38f-0155-35d9-9e83-fde8ec9f74ae","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","scopeId":"954a1548-55b1-3013-8c41-974671a413eb","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"511cc38f-0155-35d9-9e83-fde8ec9f74ae","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"511cc38f-0155-35d9-9e83-fde8ec9f74ae","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"511cc38f-0155-35d9-9e83-fde8ec9f74ae","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"511cc38f-0155-35d9-9e83-fde8ec9f74ae","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"511cc38f-0155-35d9-9e83-fde8ec9f74ae","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"511cc38f-0155-35d9-9e83-fde8ec9f74ae","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"511cc38f-0155-35d9-9e83-fde8ec9f74ae","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"511cc38f-0155-35d9-9e83-fde8ec9f74ae"},{"kind":"sourceEvent","id":"b42ee9a8-1a0c-3311-8686-cdbbddd8281d"},{"kind":"sourceEvent","id":"02ac61b9-9869-3e11-863c-2cf4570225fd"},{"kind":"sourceEvent","id":"14dac949-8072-38d6-82eb-885ab337dd32"},{"kind":"sourceEvent","id":"9e1cc661-9fa8-3f88-be5d-5136f953bf06"},{"kind":"sourceEvent","id":"63a1e8d4-6d21-354e-bab5-2ed087e9de85"},{"kind":"sourceEvent","id":"0bb5cff3-122e-37e7-adee-ff6694700b60"},{"kind":"sourceEvent","id":"fa8b068c-98fc-3c50-b39a-6aa9c0297183"},{"kind":"sourceEvent","id":"c693e7d3-ce74-3ad1-aa26-4f349f2e3e18"},{"kind":"sourceEvent","id":"f51ab406-9fb5-3af1-bc9b-0079f410e9f6"},{"kind":"sourceEvent","id":"0e440a91-8d34-38e6-b34a-8eb1bbd5693d"},{"kind":"observation","id":"fb8117ad-b02b-3145-94dd-56ef17f7143b"},{"kind":"inventoryScope","id":"954a1548-55b1-3013-8c41-974671a413eb"},{"kind":"sourceAvailability","id":"511cc38f-0155-35d9-9e83-fde8ec9f74ae/LOANS"},{"kind":"sourceAvailability","id":"511cc38f-0155-35d9-9e83-fde8ec9f74ae/RETURNS"},{"kind":"sourceAvailability","id":"511cc38f-0155-35d9-9e83-fde8ec9f74ae/RECEIPTS"},{"kind":"sourceAvailability","id":"511cc38f-0155-35d9-9e83-fde8ec9f74ae/TRANSFERS"},{"kind":"sourceAvailability","id":"511cc38f-0155-35d9-9e83-fde8ec9f74ae/REPAIRS"},{"kind":"sourceAvailability","id":"511cc38f-0155-35d9-9e83-fde8ec9f74ae/INTEGRATIONS"},{"kind":"sourceAvailability","id":"511cc38f-0155-35d9-9e83-fde8ec9f74ae/INVENTORY"}]`

- Session : `{"scopeId":"954a1548-55b1-3013-8c41-974671a413eb","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

## Cas daeeb8c6-bdfb-3dd4-b3ab-1b767ec865ef — dossier d'écart

- Exemplaire : `40bbde67-e2be-327b-a59d-fa097f24f2fe`
- Famille privée : RETOUR_NON_PROJETE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"194abb26-f686-383d-8baa-c4d80c49dc2f","kind":"STATUS_MISMATCH","expectedValue":"ON_LOAN","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"b29ed5fe-ed12-3c09-be22-06257688c7c4","copyId":"40bbde67-e2be-327b-a59d-fa097f24f2fe","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T09:36:46Z","recordedAt":"2026-09-27T09:36:56Z","payload":{"originalEventId":"906140c1-60b7-3f31-94a4-c8ddd4acebaf","correlationId":"6c2b9f93-4c22-315b-bce7-46ff3d23a045","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"906140c1-60b7-3f31-94a4-c8ddd4acebaf","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État du stock informatique : `{"copyId":"40bbde67-e2be-327b-a59d-fa097f24f2fe","status":"ON_LOAN","siteId":"NORD","locationId":null,"sourceEventIds":["502ec241-2abd-3160-8f73-8a3bdd81a7fc","e97a6a30-85ce-34b2-a3d6-9ff2819be164"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | cf193a38-ac75-36a8-9cd6-9fd9e296750b | 2026-06-01T20:37:22Z | 2026-06-01T20:37:32Z | `{"operationId":"75c376c8-4ac9-3e8b-9f34-f59f03e215ce","correlationId":"319283ef-01f1-3dc5-9ca5-e610ac554233","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"81a3036a-7fbe-36e5-b8d3-0e0085f0b0e2"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | f5e789c4-229b-38a1-8e42-438dd439cc7e | 2026-06-02T20:37:22Z | 2026-06-02T20:37:32Z | `{"originalEventId":"cf193a38-ac75-36a8-9cd6-9fd9e296750b","correlationId":"319283ef-01f1-3dc5-9ca5-e610ac554233","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 5441f3fa-95eb-3906-9557-5bfe69fa327d | 2026-07-31T20:37:22Z | 2026-07-31T20:37:32Z | `{"operationId":"283cd99a-27bd-3704-9650-d456a1d9749c","correlationId":"0104475f-4484-3f78-a509-50f1e546dac1","siteId":"CENTRE","locationId":null,"loanId":"276032cc-433f-384d-9421-f2cbd89678c8"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 543bf5a0-03ce-33b1-82cc-72a60e33ef91 | 2026-07-31T20:38:22Z | 2026-07-31T20:38:32Z | `{"originalEventId":"5441f3fa-95eb-3906-9557-5bfe69fa327d","correlationId":"0104475f-4484-3f78-a509-50f1e546dac1","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | e4d4c07c-4980-3124-98e2-b089824dcf99 | 2026-08-20T20:37:22Z | 2026-08-20T20:37:32Z | `{"operationId":"6d4bd180-e89b-393b-96f1-a256daa66f02","correlationId":"6f8e2ee4-e4ad-34d2-a970-94a90349bb7e","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"276032cc-433f-384d-9421-f2cbd89678c8"}` |
| Retour reçu (`ReturnReceived`) | 6a9a126b-75bd-3afc-965d-c9ce39195e20 | 2026-08-20T20:37:52Z | 2026-08-20T20:38:02Z | `{"operationId":"6db4063d-0764-3124-952f-ce04357ed819","correlationId":"29cdcd8b-a5db-3a06-ac7f-c85909c8f373","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"276032cc-433f-384d-9421-f2cbd89678c8"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 9a8abb64-2524-39b5-90d3-d6bd95c6a3bc | 2026-08-21T20:37:22Z | 2026-08-21T20:37:32Z | `{"originalEventId":"6a9a126b-75bd-3afc-965d-c9ce39195e20","correlationId":"29cdcd8b-a5db-3a06-ac7f-c85909c8f373","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Transfert expédié (`TransferDispatched`) | fc8efd32-20e0-360d-9fb8-a51f53a01458 | 2026-08-30T20:37:22Z | 2026-08-30T20:37:32Z | `{"operationId":"cdd189dd-48c7-3083-841d-8749acf2abec","correlationId":"b41eb972-0c40-3a7b-b4bf-00cf65785850","siteId":"CENTRE","locationId":null,"transferId":"ab075a3b-5b47-3611-8705-a359570c459f","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | fc0a7340-a638-38ca-819f-ec7eca91da0e | 2026-08-30T20:38:22Z | 2026-08-30T20:38:32Z | `{"originalEventId":"fc8efd32-20e0-360d-9fb8-a51f53a01458","correlationId":"b41eb972-0c40-3a7b-b4bf-00cf65785850","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| Transfert reçu (`TransferReceived`) | 54f1d08d-dadf-3bcf-9bd6-8660e31aa173 | 2026-08-31T20:37:22Z | 2026-08-31T20:37:32Z | `{"operationId":"8df8cc44-f380-3273-ab27-4f6d413627e5","correlationId":"b9fa05ff-4215-3cb5-81f3-3260d6620744","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","transferId":"ab075a3b-5b47-3611-8705-a359570c459f","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 8ffd6fb6-de74-3505-97d6-95d6db6e1f76 | 2026-09-01T20:37:22Z | 2026-09-01T20:37:32Z | `{"originalEventId":"54f1d08d-dadf-3bcf-9bd6-8660e31aa173","correlationId":"b9fa05ff-4215-3cb5-81f3-3260d6620744","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | 502ec241-2abd-3160-8f73-8a3bdd81a7fc | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"02367ff5-1f1c-331e-9fbf-58b51aa8b90a","correlationId":"396a4981-0b70-3799-9f1c-abd6a939f8eb","siteId":"NORD","locationId":null,"loanId":"449b4e99-75d4-3765-b228-d02709d03c64"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | e97a6a30-85ce-34b2-a3d6-9ff2819be164 | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"502ec241-2abd-3160-8f73-8a3bdd81a7fc","correlationId":"396a4981-0b70-3799-9f1c-abd6a939f8eb","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 4f7ba396-8e7d-335c-98bc-0f5b4c04e0dc | 2026-09-27T09:35:46Z | 2026-09-27T09:35:56Z | `{"operationId":"57de070f-04ad-3ea3-a484-647bcf1bb426","correlationId":"0acda0f0-1d84-3dcc-85af-bb2ff6bb41e7","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"449b4e99-75d4-3765-b228-d02709d03c64"}` |
| Retour reçu (`ReturnReceived`) | 906140c1-60b7-3f31-94a4-c8ddd4acebaf | 2026-09-27T09:36:16Z | 2026-09-27T09:36:26Z | `{"operationId":"004bdb0b-d021-3872-9ab0-4b1211e76ba0","correlationId":"6c2b9f93-4c22-315b-bce7-46ff3d23a045","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"449b4e99-75d4-3765-b228-d02709d03c64"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"8ec5c0a0-792d-3496-be1b-f0acf86abf95","copyId":"40bbde67-e2be-327b-a59d-fa097f24f2fe","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","scopeId":"e7075335-e771-3f5d-be5a-bea0d97b821f","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"40bbde67-e2be-327b-a59d-fa097f24f2fe","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"40bbde67-e2be-327b-a59d-fa097f24f2fe","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"40bbde67-e2be-327b-a59d-fa097f24f2fe","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"40bbde67-e2be-327b-a59d-fa097f24f2fe","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"40bbde67-e2be-327b-a59d-fa097f24f2fe","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"40bbde67-e2be-327b-a59d-fa097f24f2fe","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"40bbde67-e2be-327b-a59d-fa097f24f2fe","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"40bbde67-e2be-327b-a59d-fa097f24f2fe"},{"kind":"sourceEvent","id":"cf193a38-ac75-36a8-9cd6-9fd9e296750b"},{"kind":"sourceEvent","id":"f5e789c4-229b-38a1-8e42-438dd439cc7e"},{"kind":"sourceEvent","id":"5441f3fa-95eb-3906-9557-5bfe69fa327d"},{"kind":"sourceEvent","id":"543bf5a0-03ce-33b1-82cc-72a60e33ef91"},{"kind":"sourceEvent","id":"e4d4c07c-4980-3124-98e2-b089824dcf99"},{"kind":"sourceEvent","id":"6a9a126b-75bd-3afc-965d-c9ce39195e20"},{"kind":"sourceEvent","id":"9a8abb64-2524-39b5-90d3-d6bd95c6a3bc"},{"kind":"sourceEvent","id":"fc8efd32-20e0-360d-9fb8-a51f53a01458"},{"kind":"sourceEvent","id":"fc0a7340-a638-38ca-819f-ec7eca91da0e"},{"kind":"sourceEvent","id":"54f1d08d-dadf-3bcf-9bd6-8660e31aa173"},{"kind":"sourceEvent","id":"8ffd6fb6-de74-3505-97d6-95d6db6e1f76"},{"kind":"sourceEvent","id":"502ec241-2abd-3160-8f73-8a3bdd81a7fc"},{"kind":"sourceEvent","id":"e97a6a30-85ce-34b2-a3d6-9ff2819be164"},{"kind":"sourceEvent","id":"4f7ba396-8e7d-335c-98bc-0f5b4c04e0dc"},{"kind":"sourceEvent","id":"906140c1-60b7-3f31-94a4-c8ddd4acebaf"},{"kind":"observation","id":"8ec5c0a0-792d-3496-be1b-f0acf86abf95"},{"kind":"inventoryScope","id":"e7075335-e771-3f5d-be5a-bea0d97b821f"},{"kind":"sourceAvailability","id":"40bbde67-e2be-327b-a59d-fa097f24f2fe/LOANS"},{"kind":"sourceAvailability","id":"40bbde67-e2be-327b-a59d-fa097f24f2fe/RETURNS"},{"kind":"sourceAvailability","id":"40bbde67-e2be-327b-a59d-fa097f24f2fe/RECEIPTS"},{"kind":"sourceAvailability","id":"40bbde67-e2be-327b-a59d-fa097f24f2fe/TRANSFERS"},{"kind":"sourceAvailability","id":"40bbde67-e2be-327b-a59d-fa097f24f2fe/REPAIRS"},{"kind":"sourceAvailability","id":"40bbde67-e2be-327b-a59d-fa097f24f2fe/INTEGRATIONS"},{"kind":"sourceAvailability","id":"40bbde67-e2be-327b-a59d-fa097f24f2fe/INVENTORY"}]`

- Session : `{"scopeId":"e7075335-e771-3f5d-be5a-bea0d97b821f","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas db620885-4154-3c79-9066-f4ce0ba31818 — dossier d'écart

- Exemplaire : `dbba605d-e48a-35b6-a970-b9059409f451`
- Famille privée : TRANSFERT_INCOHERENT ; variante : POSITIVE
- Symptômes : `[{"symptomId":"9560b676-e352-3aab-8c6b-e345c5fc763e","kind":"STATUS_MISMATCH","expectedValue":"IN_TRANSIT","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"9226dbef-863c-3acf-bd28-567bf9230eb8","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["TRANSFER_RECEIPT_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"dbba605d-e48a-35b6-a970-b9059409f451","status":"IN_TRANSIT","siteId":"CENTRE","locationId":null,"sourceEventIds":["67874f1c-2e97-3f92-a6b1-fbbf51cc1801","a8194999-847c-3338-b9dd-3b0ed1deb186"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 460c0621-f55d-328c-bbe6-33e2281f3ee5 | 2026-06-02T10:59:23Z | 2026-06-02T10:59:33Z | `{"operationId":"b81dc7f1-b964-3524-b5cd-f0013512becc","correlationId":"6adf55a1-4c26-3265-8196-735195d1fb66","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"02379132-5aef-35ae-8538-259d0265be09"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 2837bdec-a8b5-372b-9f67-31272956ccf2 | 2026-06-03T10:59:23Z | 2026-06-03T10:59:33Z | `{"originalEventId":"460c0621-f55d-328c-bbe6-33e2281f3ee5","correlationId":"6adf55a1-4c26-3265-8196-735195d1fb66","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 5cee804b-029e-3af6-8e5e-2627238e2ec1 | 2026-08-01T10:59:23Z | 2026-08-01T10:59:33Z | `{"operationId":"862a3b6c-bb34-311c-a146-720ff3904937","correlationId":"37b955c5-eea2-357f-b331-24a70ed58b59","siteId":"CENTRE","locationId":null,"loanId":"2e16ef65-2c5b-3ccf-a52f-9fb1559b994f"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 35f74cbe-52e3-331c-a6b9-e75e10e989f5 | 2026-08-01T11:00:23Z | 2026-08-01T11:00:33Z | `{"originalEventId":"5cee804b-029e-3af6-8e5e-2627238e2ec1","correlationId":"37b955c5-eea2-357f-b331-24a70ed58b59","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | a5af346e-82e5-32c0-8fd5-84ca583b0f29 | 2026-08-21T10:59:23Z | 2026-08-21T10:59:33Z | `{"operationId":"c874048f-2170-3e57-bd7c-3a70dcd24d75","correlationId":"1d7ca61d-456c-327d-8eff-7aab4bff6374","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"2e16ef65-2c5b-3ccf-a52f-9fb1559b994f"}` |
| Retour reçu (`ReturnReceived`) | 57f6ab30-d8dd-3219-8db6-3da655bdc55d | 2026-08-21T10:59:53Z | 2026-08-21T11:00:03Z | `{"operationId":"f41c754b-a87f-344e-b52a-520bb9398e5b","correlationId":"45328076-b8c5-31b8-a43a-2216fa4d879e","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"2e16ef65-2c5b-3ccf-a52f-9fb1559b994f"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 64442a51-cee9-3c44-ad44-803e71666d81 | 2026-08-22T10:59:23Z | 2026-08-22T10:59:33Z | `{"originalEventId":"57f6ab30-d8dd-3219-8db6-3da655bdc55d","correlationId":"45328076-b8c5-31b8-a43a-2216fa4d879e","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Transfert expédié (`TransferDispatched`) | 67874f1c-2e97-3f92-a6b1-fbbf51cc1801 | 2026-09-26T06:32:02Z | 2026-09-26T06:32:12Z | `{"operationId":"7006ddf1-c561-38c9-9a05-8ccfd8583d55","correlationId":"30ebbd68-cedd-32f9-99fe-32240566761a","siteId":"CENTRE","locationId":null,"transferId":"0011eb46-0dda-3183-bf06-440f85b4fd94","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | a8194999-847c-3338-b9dd-3b0ed1deb186 | 2026-09-26T06:33:02Z | 2026-09-26T06:33:12Z | `{"originalEventId":"67874f1c-2e97-3f92-a6b1-fbbf51cc1801","correlationId":"30ebbd68-cedd-32f9-99fe-32240566761a","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| Transfert reçu (`TransferReceived`) | 9226dbef-863c-3acf-bd28-567bf9230eb8 | 2026-09-27T06:32:02Z | 2026-09-27T06:32:12Z | `{"operationId":"adda927e-9d24-3d10-bf0b-0e9bee891ae7","correlationId":"5a17bbc2-beed-3912-9b14-d9ad65c3392e","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","transferId":"0011eb46-0dda-3183-bf06-440f85b4fd94","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 72753974-6dbd-351f-9096-2a8521789808 | 2026-09-27T06:32:32Z | 2026-09-27T06:32:42Z | `{"originalEventId":"9226dbef-863c-3acf-bd28-567bf9230eb8","correlationId":"5a17bbc2-beed-3912-9b14-d9ad65c3392e","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"92455ad4-7154-3fd7-9eb5-b9c826aa7d93","copyId":"dbba605d-e48a-35b6-a970-b9059409f451","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","scopeId":"aedcfa99-c0dc-345a-8a76-b4763a7db1f5","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"dbba605d-e48a-35b6-a970-b9059409f451","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"dbba605d-e48a-35b6-a970-b9059409f451","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"dbba605d-e48a-35b6-a970-b9059409f451","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"dbba605d-e48a-35b6-a970-b9059409f451","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"dbba605d-e48a-35b6-a970-b9059409f451","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"dbba605d-e48a-35b6-a970-b9059409f451","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"dbba605d-e48a-35b6-a970-b9059409f451","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"dbba605d-e48a-35b6-a970-b9059409f451"},{"kind":"sourceEvent","id":"460c0621-f55d-328c-bbe6-33e2281f3ee5"},{"kind":"sourceEvent","id":"2837bdec-a8b5-372b-9f67-31272956ccf2"},{"kind":"sourceEvent","id":"5cee804b-029e-3af6-8e5e-2627238e2ec1"},{"kind":"sourceEvent","id":"35f74cbe-52e3-331c-a6b9-e75e10e989f5"},{"kind":"sourceEvent","id":"a5af346e-82e5-32c0-8fd5-84ca583b0f29"},{"kind":"sourceEvent","id":"57f6ab30-d8dd-3219-8db6-3da655bdc55d"},{"kind":"sourceEvent","id":"64442a51-cee9-3c44-ad44-803e71666d81"},{"kind":"sourceEvent","id":"67874f1c-2e97-3f92-a6b1-fbbf51cc1801"},{"kind":"sourceEvent","id":"a8194999-847c-3338-b9dd-3b0ed1deb186"},{"kind":"sourceEvent","id":"9226dbef-863c-3acf-bd28-567bf9230eb8"},{"kind":"sourceEvent","id":"72753974-6dbd-351f-9096-2a8521789808"},{"kind":"observation","id":"92455ad4-7154-3fd7-9eb5-b9c826aa7d93"},{"kind":"inventoryScope","id":"aedcfa99-c0dc-345a-8a76-b4763a7db1f5"},{"kind":"sourceAvailability","id":"dbba605d-e48a-35b6-a970-b9059409f451/LOANS"},{"kind":"sourceAvailability","id":"dbba605d-e48a-35b6-a970-b9059409f451/RETURNS"},{"kind":"sourceAvailability","id":"dbba605d-e48a-35b6-a970-b9059409f451/RECEIPTS"},{"kind":"sourceAvailability","id":"dbba605d-e48a-35b6-a970-b9059409f451/TRANSFERS"},{"kind":"sourceAvailability","id":"dbba605d-e48a-35b6-a970-b9059409f451/REPAIRS"},{"kind":"sourceAvailability","id":"dbba605d-e48a-35b6-a970-b9059409f451/INTEGRATIONS"},{"kind":"sourceAvailability","id":"dbba605d-e48a-35b6-a970-b9059409f451/INVENTORY"}]`

- Session : `{"scopeId":"aedcfa99-c0dc-345a-8a76-b4763a7db1f5","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas dc51a7f5-10f0-3d8d-9806-ed55812323bf — dossier d'écart

- Exemplaire : `566b6759-7cf3-3f0f-8a34-ebdaf4b9ac57`
- Famille privée : INVENTAIRE_INCOMPLET ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"2df20ead-3ffd-3c1e-b46a-954bb0f72433","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":"1"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"mechanism":"Exemplaire resté au rayon, lecture de contrôle réussie.","physicalStatus":"ON_SHELF","physicalSiteId":"SUD","physicalLocationId":"00f09742-c706-31b7-a4a0-c694a087403f","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"Le scan valide au rayon attendu réfute l'absence courante.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"566b6759-7cf3-3f0f-8a34-ebdaf4b9ac57","status":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","sourceEventIds":["9cf80a4b-5c95-3a9c-97ce-ffea4cf3d064","493a62b3-6b81-3498-9102-e6d03ec20e26"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 4f44531a-4a97-3a97-b350-949228555bb5 | 2026-06-02T00:37:54Z | 2026-06-02T00:38:04Z | `{"operationId":"1f7eea6a-adf8-32f7-9f28-b7977eccd377","correlationId":"f8a3ca48-1855-3cc9-b4c2-5994fa5c76ce","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"9043dae3-9bd4-3cfa-b208-0c028ebf6dcc"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | b7d04a41-1420-344d-849b-8548725b1243 | 2026-06-03T00:37:54Z | 2026-06-03T00:38:04Z | `{"originalEventId":"4f44531a-4a97-3a97-b350-949228555bb5","correlationId":"f8a3ca48-1855-3cc9-b4c2-5994fa5c76ce","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | 6dc28ba2-e023-3bda-826e-9d174a5c19c1 | 2026-08-01T00:37:54Z | 2026-08-01T00:38:04Z | `{"operationId":"b96d6870-eb4f-3280-8dcd-b131a02237ef","correlationId":"ce32897f-79cb-3484-b476-4bbe2e3644e0","siteId":"NORD","locationId":null,"loanId":"3455ae86-7915-3ad2-b6cb-7c0ab6c138d9"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 8031baa2-f94d-3d80-bb34-7c57950d9219 | 2026-08-01T00:38:54Z | 2026-08-01T00:39:04Z | `{"originalEventId":"6dc28ba2-e023-3bda-826e-9d174a5c19c1","correlationId":"ce32897f-79cb-3484-b476-4bbe2e3644e0","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | cdefe10e-50ad-32ae-9154-9cb3cdebed64 | 2026-08-21T00:37:54Z | 2026-08-21T00:38:04Z | `{"operationId":"74a156f6-3aef-3ae2-9d2a-1b39ba8ffddd","correlationId":"84b9a9ae-8348-388c-a41b-db50eb2c06bf","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"3455ae86-7915-3ad2-b6cb-7c0ab6c138d9"}` |
| Retour reçu (`ReturnReceived`) | b6e5ec5f-22fa-35e1-8799-4ed9e72596e2 | 2026-08-21T00:38:24Z | 2026-08-21T00:38:34Z | `{"operationId":"2899898f-66b8-33c2-a742-6c83f4a75c74","correlationId":"4fb233a3-75eb-3926-9cf9-9ee1412ff92d","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"3455ae86-7915-3ad2-b6cb-7c0ab6c138d9"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 9e4ad756-05ec-36cc-92d8-ee145167da88 | 2026-08-22T00:37:54Z | 2026-08-22T00:38:04Z | `{"originalEventId":"b6e5ec5f-22fa-35e1-8799-4ed9e72596e2","correlationId":"4fb233a3-75eb-3926-9cf9-9ee1412ff92d","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Transfert expédié (`TransferDispatched`) | 68ffc82b-daea-3b78-a0b8-dd003145314c | 2026-08-31T00:37:54Z | 2026-08-31T00:38:04Z | `{"operationId":"f8e6be3a-59ee-34bf-8684-e6e35a54db4f","correlationId":"cca2d42e-22a5-3c91-8d39-6c42b8bf6035","siteId":"NORD","locationId":null,"transferId":"7f934b15-06d5-379c-9c32-5fd7d0e75e8c","fromSiteId":"NORD","toSiteId":"SUD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 74745d8a-c307-369b-8d05-75859c64ab98 | 2026-08-31T00:38:54Z | 2026-08-31T00:39:04Z | `{"originalEventId":"68ffc82b-daea-3b78-a0b8-dd003145314c","correlationId":"cca2d42e-22a5-3c91-8d39-6c42b8bf6035","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"NORD","locationId":null}` |
| Transfert reçu (`TransferReceived`) | 9cf80a4b-5c95-3a9c-97ce-ffea4cf3d064 | 2026-09-01T00:37:54Z | 2026-09-01T00:38:04Z | `{"operationId":"bef61e2b-3d52-3b49-80a0-81dd3162db19","correlationId":"e56a6121-a34a-38dd-b783-f471248a2ed8","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","transferId":"7f934b15-06d5-379c-9c32-5fd7d0e75e8c","fromSiteId":"NORD","toSiteId":"SUD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 493a62b3-6b81-3498-9102-e6d03ec20e26 | 2026-09-02T00:37:54Z | 2026-09-02T00:38:04Z | `{"originalEventId":"9cf80a4b-5c95-3a9c-97ce-ffea4cf3d064","correlationId":"e56a6121-a34a-38dd-b783-f471248a2ed8","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"79659fa4-128e-3ab8-8a58-cb619b9d395b","copyId":"566b6759-7cf3-3f0f-8a34-ebdaf4b9ac57","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","scopeId":"2948b706-5519-3fcb-9c6a-82f0197080d2","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"566b6759-7cf3-3f0f-8a34-ebdaf4b9ac57","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"566b6759-7cf3-3f0f-8a34-ebdaf4b9ac57","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"566b6759-7cf3-3f0f-8a34-ebdaf4b9ac57","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"566b6759-7cf3-3f0f-8a34-ebdaf4b9ac57","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"566b6759-7cf3-3f0f-8a34-ebdaf4b9ac57","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"566b6759-7cf3-3f0f-8a34-ebdaf4b9ac57","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"566b6759-7cf3-3f0f-8a34-ebdaf4b9ac57","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**
- `{"noteId":"8f669b67-61de-33b3-8859-988c44d65040","copyId":"566b6759-7cf3-3f0f-8a34-ebdaf4b9ac57","text":"Nouvelle lecture du code-barres effectuée dans le rayon affecté.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"566b6759-7cf3-3f0f-8a34-ebdaf4b9ac57"},{"kind":"sourceEvent","id":"4f44531a-4a97-3a97-b350-949228555bb5"},{"kind":"sourceEvent","id":"b7d04a41-1420-344d-849b-8548725b1243"},{"kind":"sourceEvent","id":"6dc28ba2-e023-3bda-826e-9d174a5c19c1"},{"kind":"sourceEvent","id":"8031baa2-f94d-3d80-bb34-7c57950d9219"},{"kind":"sourceEvent","id":"cdefe10e-50ad-32ae-9154-9cb3cdebed64"},{"kind":"sourceEvent","id":"b6e5ec5f-22fa-35e1-8799-4ed9e72596e2"},{"kind":"sourceEvent","id":"9e4ad756-05ec-36cc-92d8-ee145167da88"},{"kind":"sourceEvent","id":"68ffc82b-daea-3b78-a0b8-dd003145314c"},{"kind":"sourceEvent","id":"74745d8a-c307-369b-8d05-75859c64ab98"},{"kind":"sourceEvent","id":"9cf80a4b-5c95-3a9c-97ce-ffea4cf3d064"},{"kind":"sourceEvent","id":"493a62b3-6b81-3498-9102-e6d03ec20e26"},{"kind":"observation","id":"79659fa4-128e-3ab8-8a58-cb619b9d395b"},{"kind":"inventoryScope","id":"2948b706-5519-3fcb-9c6a-82f0197080d2"},{"kind":"sourceAvailability","id":"566b6759-7cf3-3f0f-8a34-ebdaf4b9ac57/LOANS"},{"kind":"sourceAvailability","id":"566b6759-7cf3-3f0f-8a34-ebdaf4b9ac57/RETURNS"},{"kind":"sourceAvailability","id":"566b6759-7cf3-3f0f-8a34-ebdaf4b9ac57/RECEIPTS"},{"kind":"sourceAvailability","id":"566b6759-7cf3-3f0f-8a34-ebdaf4b9ac57/TRANSFERS"},{"kind":"sourceAvailability","id":"566b6759-7cf3-3f0f-8a34-ebdaf4b9ac57/REPAIRS"},{"kind":"sourceAvailability","id":"566b6759-7cf3-3f0f-8a34-ebdaf4b9ac57/INTEGRATIONS"},{"kind":"sourceAvailability","id":"566b6759-7cf3-3f0f-8a34-ebdaf4b9ac57/INVENTORY"},{"kind":"operatorNote","id":"8f669b67-61de-33b3-8859-988c44d65040"},{"kind":"inventoryScope","id":"7f91fb45-70cc-3811-b222-43a5370aa433"},{"kind":"inventoryScope","id":"202855ec-9065-3be6-a0c2-07ce228ecf21"},{"kind":"inventoryScope","id":"1b4f280a-3145-3607-a009-dca44e9d4181"},{"kind":"inventoryScope","id":"2948b706-5519-3fcb-9c6a-82f0197080d2"}]`

- Session : `{"scopeId":"2948b706-5519-3fcb-9c6a-82f0197080d2","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"7f91fb45-70cc-3811-b222-43a5370aa433","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"202855ec-9065-3be6-a0c2-07ce228ecf21","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"1b4f280a-3145-3607-a009-dca44e9d4181","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2948b706-5519-3fcb-9c6a-82f0197080d2","locationId":"00f09742-c706-31b7-a4a0-c694a087403f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas e1ce363c-e548-3943-82fd-a126eb0aa887 — dossier d'écart

- Exemplaire : `8a4e82cc-08b2-3942-ae85-ab9d56360219`
- Famille privée : RECEPTION_NON_INTEGREE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"7d70fb2b-9e8b-3e02-ab38-16a178c30b50","kind":"UNEXPECTED_PRESENCE","expectedValue":"NOT_REGISTERED","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"b1df0920-b8b2-37de-837e-f41243a67f64","copyId":"8a4e82cc-08b2-3942-ae85-ab9d56360219","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T08:47:03Z","recordedAt":"2026-09-27T08:47:13Z","payload":{"originalEventId":"f9731820-d0d5-3de9-8b08-5a1c92632ee4","correlationId":"b408811e-f780-3974-81aa-3d208f3cdbb3","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"f9731820-d0d5-3de9-8b08-5a1c92632ee4","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"125bed65-7385-3595-a653-902580e9539f","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État du stock informatique : `{"copyId":"8a4e82cc-08b2-3942-ae85-ab9d56360219","status":"NOT_REGISTERED","siteId":"CENTRE","locationId":null,"sourceEventIds":[]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | f9731820-d0d5-3de9-8b08-5a1c92632ee4 | 2026-09-27T08:46:33Z | 2026-09-27T08:46:43Z | `{"operationId":"50fe962f-323e-3d65-8f28-25e616c95fb6","correlationId":"b408811e-f780-3974-81aa-3d208f3cdbb3","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"a040569e-c3de-3368-9926-e55701e8df67"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"43efbafd-ceb8-3df3-8e80-a7d7899e24b6","copyId":"8a4e82cc-08b2-3942-ae85-ab9d56360219","locationId":"125bed65-7385-3595-a653-902580e9539f","scopeId":"dcc99ab8-60e8-3664-bd8b-2c8adf66d8d8","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"8a4e82cc-08b2-3942-ae85-ab9d56360219","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8a4e82cc-08b2-3942-ae85-ab9d56360219","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8a4e82cc-08b2-3942-ae85-ab9d56360219","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8a4e82cc-08b2-3942-ae85-ab9d56360219","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8a4e82cc-08b2-3942-ae85-ab9d56360219","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"8a4e82cc-08b2-3942-ae85-ab9d56360219","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8a4e82cc-08b2-3942-ae85-ab9d56360219","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"8a4e82cc-08b2-3942-ae85-ab9d56360219"},{"kind":"sourceEvent","id":"f9731820-d0d5-3de9-8b08-5a1c92632ee4"},{"kind":"observation","id":"43efbafd-ceb8-3df3-8e80-a7d7899e24b6"},{"kind":"inventoryScope","id":"dcc99ab8-60e8-3664-bd8b-2c8adf66d8d8"},{"kind":"sourceAvailability","id":"8a4e82cc-08b2-3942-ae85-ab9d56360219/LOANS"},{"kind":"sourceAvailability","id":"8a4e82cc-08b2-3942-ae85-ab9d56360219/RETURNS"},{"kind":"sourceAvailability","id":"8a4e82cc-08b2-3942-ae85-ab9d56360219/RECEIPTS"},{"kind":"sourceAvailability","id":"8a4e82cc-08b2-3942-ae85-ab9d56360219/TRANSFERS"},{"kind":"sourceAvailability","id":"8a4e82cc-08b2-3942-ae85-ab9d56360219/REPAIRS"},{"kind":"sourceAvailability","id":"8a4e82cc-08b2-3942-ae85-ab9d56360219/INTEGRATIONS"},{"kind":"sourceAvailability","id":"8a4e82cc-08b2-3942-ae85-ab9d56360219/INVENTORY"}]`

- Session : `{"scopeId":"dcc99ab8-60e8-3664-bd8b-2c8adf66d8d8","locationId":"125bed65-7385-3595-a653-902580e9539f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas e9df8df7-92ec-3b71-aef5-d3e1dd0611a4 — dossier d'écart

- Exemplaire : `db76cbc7-2d55-3524-a318-95c73a95fa18`
- Famille privée : ABSENCE_INEXPLIQUEE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"4448d3c9-1b47-31ee-887b-1de4bd78037d","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["UNOBSERVED_MOVE"],"mechanism":"Exemplaire déplacé hors du périmètre instrumenté ; la destination n'est pas exposée et aucune perte n'est simulée.","physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":null,"evidenceMask":"Collecte indisponible"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"L'absence de lecture ne démontre ni la destination, ni une perte, ni un vol ; données insuffisantes pour attribuer une cause.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État du stock informatique : `{"copyId":"db76cbc7-2d55-3524-a318-95c73a95fa18","status":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","sourceEventIds":["19aa911d-508d-3180-9f58-ef6367cda591","3e90a17c-458f-3a92-b947-eba9c0f88ba6"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 53f90d4b-1684-3810-808e-ea9c3ce57916 | 2026-06-01T18:44:28Z | 2026-06-01T18:44:38Z | `{"operationId":"25dd4f4b-dfaf-3dfb-b68b-0e5f32a854d0","correlationId":"df29bc0c-14b4-34b0-aa5b-bf7a2f257551","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","receiptId":"48f1fcea-422d-3922-9f07-1b5b41800aeb"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | df7b068a-c98b-3e64-917a-8a2bbc0cfbb2 | 2026-06-02T18:44:28Z | 2026-06-02T18:44:38Z | `{"originalEventId":"53f90d4b-1684-3810-808e-ea9c3ce57916","correlationId":"df29bc0c-14b4-34b0-aa5b-bf7a2f257551","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | 00541259-af28-3f65-afed-77448d7080a4 | 2026-07-31T18:44:28Z | 2026-07-31T18:44:38Z | `{"operationId":"8cc47d8c-5c12-3df0-b574-5690e156d460","correlationId":"6c44783f-6ca0-3994-85a2-8e0c6edac464","siteId":"NORD","locationId":null,"loanId":"9be1c58f-9e07-3f5f-8c70-04a2bc241973"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | ed71b383-42a2-3fde-975b-bc3161ed77a9 | 2026-07-31T18:45:28Z | 2026-07-31T18:45:38Z | `{"originalEventId":"00541259-af28-3f65-afed-77448d7080a4","correlationId":"6c44783f-6ca0-3994-85a2-8e0c6edac464","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 9051c6e7-5df6-3de8-86dd-fc6cf3bad1af | 2026-08-20T18:44:28Z | 2026-08-20T18:44:38Z | `{"operationId":"cbacdc06-a27f-36dd-9a9c-b3453bdaceb1","correlationId":"6cd18106-ce75-32bb-b2c9-a03302b03e7c","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"9be1c58f-9e07-3f5f-8c70-04a2bc241973"}` |
| Retour reçu (`ReturnReceived`) | 19aa911d-508d-3180-9f58-ef6367cda591 | 2026-08-20T18:44:58Z | 2026-08-20T18:45:08Z | `{"operationId":"f1f65dc4-540e-3c1f-8bb3-884875ffea2f","correlationId":"94df9f51-73f2-39d1-ac99-61d089ecedfa","siteId":"NORD","locationId":"19a415f2-2d88-36e0-873f-4a35fe2684bd","loanId":"9be1c58f-9e07-3f5f-8c70-04a2bc241973"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 3e90a17c-458f-3a92-b947-eba9c0f88ba6 | 2026-08-21T18:44:28Z | 2026-08-21T18:44:38Z | `{"originalEventId":"19aa911d-508d-3180-9f58-ef6367cda591","correlationId":"94df9f51-73f2-39d1-ac99-61d089ecedfa","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"db76cbc7-2d55-3524-a318-95c73a95fa18","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"db76cbc7-2d55-3524-a318-95c73a95fa18","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"db76cbc7-2d55-3524-a318-95c73a95fa18","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"db76cbc7-2d55-3524-a318-95c73a95fa18","source":"LOANS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"db76cbc7-2d55-3524-a318-95c73a95fa18","source":"INVENTORY","status":"UNAVAILABLE","completeFrom":null,"completeTo":null}`
- `{"copyId":"db76cbc7-2d55-3524-a318-95c73a95fa18","source":"TRANSFERS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"db76cbc7-2d55-3524-a318-95c73a95fa18","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**
- `{"noteId":"20e848cc-b6ec-3f4e-b994-aba5d715878d","copyId":"db76cbc7-2d55-3524-a318-95c73a95fa18","text":"Le passage au rayon n'a pas permis de retrouver cet exemplaire ; contrôle complémentaire demandé.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"db76cbc7-2d55-3524-a318-95c73a95fa18"},{"kind":"sourceEvent","id":"53f90d4b-1684-3810-808e-ea9c3ce57916"},{"kind":"sourceEvent","id":"df7b068a-c98b-3e64-917a-8a2bbc0cfbb2"},{"kind":"sourceEvent","id":"00541259-af28-3f65-afed-77448d7080a4"},{"kind":"sourceEvent","id":"ed71b383-42a2-3fde-975b-bc3161ed77a9"},{"kind":"sourceEvent","id":"9051c6e7-5df6-3de8-86dd-fc6cf3bad1af"},{"kind":"sourceEvent","id":"19aa911d-508d-3180-9f58-ef6367cda591"},{"kind":"sourceEvent","id":"3e90a17c-458f-3a92-b947-eba9c0f88ba6"},{"kind":"sourceAvailability","id":"db76cbc7-2d55-3524-a318-95c73a95fa18/LOANS"},{"kind":"sourceAvailability","id":"db76cbc7-2d55-3524-a318-95c73a95fa18/RETURNS"},{"kind":"sourceAvailability","id":"db76cbc7-2d55-3524-a318-95c73a95fa18/RECEIPTS"},{"kind":"sourceAvailability","id":"db76cbc7-2d55-3524-a318-95c73a95fa18/TRANSFERS"},{"kind":"sourceAvailability","id":"db76cbc7-2d55-3524-a318-95c73a95fa18/REPAIRS"},{"kind":"sourceAvailability","id":"db76cbc7-2d55-3524-a318-95c73a95fa18/INTEGRATIONS"},{"kind":"sourceAvailability","id":"db76cbc7-2d55-3524-a318-95c73a95fa18/INVENTORY"},{"kind":"operatorNote","id":"20e848cc-b6ec-3f4e-b994-aba5d715878d"},{"kind":"inventoryScope","id":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5"},{"kind":"inventoryScope","id":"eb1ce5f1-7397-3b45-9385-ef328e09fec8"},{"kind":"inventoryScope","id":"d0c55bda-319d-3537-857a-5d135f796cab"},{"kind":"inventoryScope","id":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb"},{"kind":"inventoryScope","id":"d9842c03-ba37-324b-b71b-e58ac7147d4c"},{"kind":"inventoryScope","id":"07bae854-b205-39bf-a23e-9eaa22063b92"},{"kind":"inventoryScope","id":"9de706b4-d360-3282-b440-c440861c039c"},{"kind":"inventoryScope","id":"b98af8e5-7297-3dc5-a947-eb8fe8af159f"},{"kind":"inventoryScope","id":"7eb96d9b-e927-305a-b369-01d6b239f3db"},{"kind":"inventoryScope","id":"b4a97852-2336-3ae2-ab5b-30b6e2e4d536"},{"kind":"inventoryScope","id":"147f13fd-620d-3c02-bc47-bf4f7080184b"},{"kind":"inventoryScope","id":"ed295eda-a27c-3fef-aa0e-3ee02619626a"},{"kind":"inventoryScope","id":"ed295eda-a27c-3fef-aa0e-3ee02619626a"}]`

- Session : `{"scopeId":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"eb1ce5f1-7397-3b45-9385-ef328e09fec8","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"d0c55bda-319d-3537-857a-5d135f796cab","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"9c6fb442-e8a4-3dd9-9dc1-e2ac2e8722bb","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"d9842c03-ba37-324b-b71b-e58ac7147d4c","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"07bae854-b205-39bf-a23e-9eaa22063b92","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"9de706b4-d360-3282-b440-c440861c039c","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b98af8e5-7297-3dc5-a947-eb8fe8af159f","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"7eb96d9b-e927-305a-b369-01d6b239f3db","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b4a97852-2336-3ae2-ab5b-30b6e2e4d536","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"147f13fd-620d-3c02-bc47-bf4f7080184b","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"ed295eda-a27c-3fef-aa0e-3ee02619626a","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"ed295eda-a27c-3fef-aa0e-3ee02619626a","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas ecb7ff09-628f-345f-9a02-59148241de6f — dossier d'écart

- Exemplaire : `331a66a7-101b-386b-8b96-fdccdddcccc7`
- Famille privée : DEUX_CAUSES ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"0f70f2ae-3a06-34c9-ac14-7ca024100a1c","kind":"STATUS_MISMATCH","expectedValue":"IN_REPAIR","observedValue":"ON_SHELF"},{"symptomId":"d2dd0d6d-4170-300e-8049-79fe9552f66b","kind":"LOCATION_MISMATCH","expectedValue":"14f2d626-9459-3fee-9d3a-daf738f7e59a","observedValue":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED","REPAIR_LOCATION_CHANGED"],"hiddenSourceEvents":[],"mechanism":"Réparation engagée puis déplacement distinct de l'atelier vers la réserve sans mise à jour de l'affectation de réparation.","businessEventId":"4b8e42be-055d-31bd-a4a9-c2f915f60a3e","physicalStatus":"IN_REPAIR","physicalSiteId":"CENTRE","physicalLocationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","projectionPerturbation":"FAILED","evidenceMask":"Scan de réserve ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":["REPAIR_NOT_PROJECTED"],"explanation":"Échec de projection de réparation documenté ; seconde localisation non confirmée par un scan valide.","forbiddenClaims":["Deux causes confirmées par un scan ambigu","Vol"],"recommendedChecks":["Refaire le scan en réserve"]}`
- État du stock informatique : `{"copyId":"331a66a7-101b-386b-8b96-fdccdddcccc7","status":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","sourceEventIds":["4118ce09-7bf1-34bf-8f3d-faec70bd7da7","75579101-b5e4-3f76-8466-18280f9b999b"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 02dc80c4-3777-3fa6-a24d-296d8239b53e | 2026-06-02T04:31:43Z | 2026-06-02T04:31:53Z | `{"operationId":"42bfe2d4-8e68-332c-ac3f-165be3e68777","correlationId":"b45b8ab0-1881-3a64-b9a1-7ad312b5650b","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"f4fa4102-d3bc-3dff-9b2f-8e4e882b2d1c"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 2630aecc-623c-36b8-98f6-614895b32fcd | 2026-06-03T04:31:43Z | 2026-06-03T04:31:53Z | `{"originalEventId":"02dc80c4-3777-3fa6-a24d-296d8239b53e","correlationId":"b45b8ab0-1881-3a64-b9a1-7ad312b5650b","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | da3bc4d2-d607-3c53-8e9b-ae3f259a890e | 2026-08-01T04:31:43Z | 2026-08-01T04:31:53Z | `{"operationId":"1638e55b-ba68-3cd3-af5f-8536cc011af4","correlationId":"2d724564-6f60-3ae7-ab6a-e21f02ec4745","siteId":"CENTRE","locationId":null,"loanId":"effb90de-71e6-3e1c-b4db-cdf1b61e3a5e"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 9f69b839-39fe-3408-badc-d55e3607e5cb | 2026-08-01T04:32:43Z | 2026-08-01T04:32:53Z | `{"originalEventId":"da3bc4d2-d607-3c53-8e9b-ae3f259a890e","correlationId":"2d724564-6f60-3ae7-ab6a-e21f02ec4745","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | a291d428-d6a9-3a5f-ae35-3ed7ecd93470 | 2026-08-21T04:31:43Z | 2026-08-21T04:31:53Z | `{"operationId":"26da66e8-836f-326e-b26c-e2582e5217df","correlationId":"94fb68d8-6b9f-39fd-84fc-72ab1a39cfae","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"effb90de-71e6-3e1c-b4db-cdf1b61e3a5e"}` |
| Retour reçu (`ReturnReceived`) | 4118ce09-7bf1-34bf-8f3d-faec70bd7da7 | 2026-08-21T04:32:13Z | 2026-08-21T04:32:23Z | `{"operationId":"4b986cd5-64c6-3226-b11a-348c92b99b1e","correlationId":"249385e5-0817-378a-80ce-c16a5c17dc9d","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"effb90de-71e6-3e1c-b4db-cdf1b61e3a5e"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 75579101-b5e4-3f76-8466-18280f9b999b | 2026-08-22T04:31:43Z | 2026-08-22T04:31:53Z | `{"originalEventId":"4118ce09-7bf1-34bf-8f3d-faec70bd7da7","correlationId":"249385e5-0817-378a-80ce-c16a5c17dc9d","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Réparation engagée (`RepairStarted`) | 4b8e42be-055d-31bd-a4a9-c2f915f60a3e | 2026-09-27T09:25:28Z | 2026-09-27T09:25:38Z | `{"operationId":"9d0961b3-c01b-3b19-8c9e-ea726c0856eb","correlationId":"a15d3d5b-4026-3680-8832-b45343aa54d9","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","repairId":"fe77c85e-d294-3e53-bb37-30aa0395841b"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 16347bd6-ec5a-3075-ba30-d9b0f45b6f31 | 2026-09-27T09:25:58Z | 2026-09-27T09:26:08Z | `{"originalEventId":"4b8e42be-055d-31bd-a4a9-c2f915f60a3e","correlationId":"a15d3d5b-4026-3680-8832-b45343aa54d9","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"bd0fb04a-5fce-3bb6-8360-462cf1298d16","copyId":"331a66a7-101b-386b-8b96-fdccdddcccc7","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","scopeId":"b88ab3ba-a18c-336c-93a4-2292645c977b","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"331a66a7-101b-386b-8b96-fdccdddcccc7","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"331a66a7-101b-386b-8b96-fdccdddcccc7","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"331a66a7-101b-386b-8b96-fdccdddcccc7","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"331a66a7-101b-386b-8b96-fdccdddcccc7","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"331a66a7-101b-386b-8b96-fdccdddcccc7","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"331a66a7-101b-386b-8b96-fdccdddcccc7","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"331a66a7-101b-386b-8b96-fdccdddcccc7","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"331a66a7-101b-386b-8b96-fdccdddcccc7"},{"kind":"sourceEvent","id":"02dc80c4-3777-3fa6-a24d-296d8239b53e"},{"kind":"sourceEvent","id":"2630aecc-623c-36b8-98f6-614895b32fcd"},{"kind":"sourceEvent","id":"da3bc4d2-d607-3c53-8e9b-ae3f259a890e"},{"kind":"sourceEvent","id":"9f69b839-39fe-3408-badc-d55e3607e5cb"},{"kind":"sourceEvent","id":"a291d428-d6a9-3a5f-ae35-3ed7ecd93470"},{"kind":"sourceEvent","id":"4118ce09-7bf1-34bf-8f3d-faec70bd7da7"},{"kind":"sourceEvent","id":"75579101-b5e4-3f76-8466-18280f9b999b"},{"kind":"sourceEvent","id":"4b8e42be-055d-31bd-a4a9-c2f915f60a3e"},{"kind":"sourceEvent","id":"16347bd6-ec5a-3075-ba30-d9b0f45b6f31"},{"kind":"observation","id":"bd0fb04a-5fce-3bb6-8360-462cf1298d16"},{"kind":"inventoryScope","id":"b88ab3ba-a18c-336c-93a4-2292645c977b"},{"kind":"sourceAvailability","id":"331a66a7-101b-386b-8b96-fdccdddcccc7/LOANS"},{"kind":"sourceAvailability","id":"331a66a7-101b-386b-8b96-fdccdddcccc7/RETURNS"},{"kind":"sourceAvailability","id":"331a66a7-101b-386b-8b96-fdccdddcccc7/RECEIPTS"},{"kind":"sourceAvailability","id":"331a66a7-101b-386b-8b96-fdccdddcccc7/TRANSFERS"},{"kind":"sourceAvailability","id":"331a66a7-101b-386b-8b96-fdccdddcccc7/REPAIRS"},{"kind":"sourceAvailability","id":"331a66a7-101b-386b-8b96-fdccdddcccc7/INTEGRATIONS"},{"kind":"sourceAvailability","id":"331a66a7-101b-386b-8b96-fdccdddcccc7/INVENTORY"},{"kind":"inventoryScope","id":"7d46c7a4-f137-377d-8437-5d11eeb4049d"},{"kind":"inventoryScope","id":"66954879-6228-3c31-bf72-7b217d3c9695"},{"kind":"inventoryScope","id":"2a6f7727-71d3-3a12-96f6-4b5e2b38ce02"},{"kind":"inventoryScope","id":"c0755c3b-6689-3d3d-8527-6c90e6c6e5d5"},{"kind":"inventoryScope","id":"3879a968-0f8b-33bb-b62b-63ec36357fbd"},{"kind":"inventoryScope","id":"4c347ad9-c499-38ba-af54-54e88e2f7e28"}]`

- Session : `{"scopeId":"b88ab3ba-a18c-336c-93a4-2292645c977b","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"7d46c7a4-f137-377d-8437-5d11eeb4049d","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"66954879-6228-3c31-bf72-7b217d3c9695","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2a6f7727-71d3-3a12-96f6-4b5e2b38ce02","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"c0755c3b-6689-3d3d-8527-6c90e6c6e5d5","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"3879a968-0f8b-33bb-b62b-63ec36357fbd","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4c347ad9-c499-38ba-af54-54e88e2f7e28","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas f45cf84e-a291-38bb-b73f-c1ee5e89befc — dossier d'écart

- Exemplaire : `32f019df-7a32-34d7-9f56-febb4c4f10ee`
- Famille privée : TRANSFERT_INCOHERENT ; variante : POSITIVE
- Symptômes : `[{"symptomId":"6f9c40fc-4388-36d2-a5a1-d3bdaf81315c","kind":"STATUS_MISMATCH","expectedValue":"IN_TRANSIT","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"2ddf83fb-e92e-3fc5-aa08-cdc6a0bd5ed5","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"125bed65-7385-3595-a653-902580e9539f","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["TRANSFER_RECEIPT_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"32f019df-7a32-34d7-9f56-febb4c4f10ee","status":"IN_TRANSIT","siteId":"SUD","locationId":null,"sourceEventIds":["6f06f5b4-3f9e-3f9b-a5bb-a67e612c79a7","f6771202-8bd6-3e94-a035-ed1b8ca48ec8"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | f310b12f-9ada-3989-aad2-30daa36faf3c | 2026-06-01T21:01:25Z | 2026-06-01T21:01:35Z | `{"operationId":"fb0388d4-eee3-3328-bf86-452729397a34","correlationId":"32dd589b-aa0b-3b66-a44d-efb87690ea68","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"1c2e2a33-f60f-3545-85c8-0c892cf88217"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | ee1c6a64-63d8-3607-841a-9c0bfa22b2ef | 2026-06-02T21:01:25Z | 2026-06-02T21:01:35Z | `{"originalEventId":"f310b12f-9ada-3989-aad2-30daa36faf3c","correlationId":"32dd589b-aa0b-3b66-a44d-efb87690ea68","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | 554c6845-746c-3c25-b037-35bfb6ed9691 | 2026-07-31T21:01:25Z | 2026-07-31T21:01:35Z | `{"operationId":"c1513894-8bad-3cd9-a303-ff8bc2ecc2d9","correlationId":"d4611a1a-4b1e-33b2-a435-808f77bb3a2e","siteId":"SUD","locationId":null,"loanId":"3ca46041-a2a5-3011-a284-7b9f0966b9b3"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 61c7d308-b1c5-3909-8d06-388bb48a63a5 | 2026-07-31T21:02:25Z | 2026-07-31T21:02:35Z | `{"originalEventId":"554c6845-746c-3c25-b037-35bfb6ed9691","correlationId":"d4611a1a-4b1e-33b2-a435-808f77bb3a2e","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 1590cc24-ea6b-3bf4-9a4d-e80ce1fc7c62 | 2026-08-20T21:01:25Z | 2026-08-20T21:01:35Z | `{"operationId":"e7e0d5c0-000e-332e-9ce5-ad2b5caf08cd","correlationId":"23f47d64-049e-33e9-b161-75ca12bc39af","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"3ca46041-a2a5-3011-a284-7b9f0966b9b3"}` |
| Retour reçu (`ReturnReceived`) | 7b81c77e-95b0-3cba-aa48-786250695b2f | 2026-08-20T21:01:55Z | 2026-08-20T21:02:05Z | `{"operationId":"f036ff3b-ed9f-34b3-99e1-31ccc5b6d41c","correlationId":"453c9506-d24d-3e57-864e-d949f378e4fc","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"3ca46041-a2a5-3011-a284-7b9f0966b9b3"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | da541177-7c8e-38b7-bee5-e71e98f2b9d1 | 2026-08-21T21:01:25Z | 2026-08-21T21:01:35Z | `{"originalEventId":"7b81c77e-95b0-3cba-aa48-786250695b2f","correlationId":"453c9506-d24d-3e57-864e-d949f378e4fc","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Transfert expédié (`TransferDispatched`) | 6f06f5b4-3f9e-3f9b-a5bb-a67e612c79a7 | 2026-09-26T08:31:17Z | 2026-09-26T08:31:27Z | `{"operationId":"116302bf-3736-3e07-8ba8-6fcd6b456208","correlationId":"d621f50f-4116-3dd7-91c3-8d4dbe6fce1f","siteId":"SUD","locationId":null,"transferId":"2c7cee4f-5cc5-3bb4-832a-29f974bba50e","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | f6771202-8bd6-3e94-a035-ed1b8ca48ec8 | 2026-09-26T08:32:17Z | 2026-09-26T08:32:27Z | `{"originalEventId":"6f06f5b4-3f9e-3f9b-a5bb-a67e612c79a7","correlationId":"d621f50f-4116-3dd7-91c3-8d4dbe6fce1f","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"SUD","locationId":null}` |
| Transfert reçu (`TransferReceived`) | 2ddf83fb-e92e-3fc5-aa08-cdc6a0bd5ed5 | 2026-09-27T08:31:17Z | 2026-09-27T08:31:27Z | `{"operationId":"db05aed6-12eb-32d4-988e-16ab5e313e3f","correlationId":"7ac703dc-f365-340e-b27d-2a4d66df24e8","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","transferId":"2c7cee4f-5cc5-3bb4-832a-29f974bba50e","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | e6fb5da5-1b27-30a8-ab8b-84971ac71879 | 2026-09-27T08:31:47Z | 2026-09-27T08:31:57Z | `{"originalEventId":"2ddf83fb-e92e-3fc5-aa08-cdc6a0bd5ed5","correlationId":"7ac703dc-f365-340e-b27d-2a4d66df24e8","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"429bcdbd-a0d0-3627-81a7-7d4ed2be64e6","copyId":"32f019df-7a32-34d7-9f56-febb4c4f10ee","locationId":"125bed65-7385-3595-a653-902580e9539f","scopeId":"aac4255b-e902-3e7e-b8f4-61a0508f0565","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"32f019df-7a32-34d7-9f56-febb4c4f10ee","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"32f019df-7a32-34d7-9f56-febb4c4f10ee","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"32f019df-7a32-34d7-9f56-febb4c4f10ee","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"32f019df-7a32-34d7-9f56-febb4c4f10ee","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"32f019df-7a32-34d7-9f56-febb4c4f10ee","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"32f019df-7a32-34d7-9f56-febb4c4f10ee","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"32f019df-7a32-34d7-9f56-febb4c4f10ee","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"32f019df-7a32-34d7-9f56-febb4c4f10ee"},{"kind":"sourceEvent","id":"f310b12f-9ada-3989-aad2-30daa36faf3c"},{"kind":"sourceEvent","id":"ee1c6a64-63d8-3607-841a-9c0bfa22b2ef"},{"kind":"sourceEvent","id":"554c6845-746c-3c25-b037-35bfb6ed9691"},{"kind":"sourceEvent","id":"61c7d308-b1c5-3909-8d06-388bb48a63a5"},{"kind":"sourceEvent","id":"1590cc24-ea6b-3bf4-9a4d-e80ce1fc7c62"},{"kind":"sourceEvent","id":"7b81c77e-95b0-3cba-aa48-786250695b2f"},{"kind":"sourceEvent","id":"da541177-7c8e-38b7-bee5-e71e98f2b9d1"},{"kind":"sourceEvent","id":"6f06f5b4-3f9e-3f9b-a5bb-a67e612c79a7"},{"kind":"sourceEvent","id":"f6771202-8bd6-3e94-a035-ed1b8ca48ec8"},{"kind":"sourceEvent","id":"2ddf83fb-e92e-3fc5-aa08-cdc6a0bd5ed5"},{"kind":"sourceEvent","id":"e6fb5da5-1b27-30a8-ab8b-84971ac71879"},{"kind":"observation","id":"429bcdbd-a0d0-3627-81a7-7d4ed2be64e6"},{"kind":"inventoryScope","id":"aac4255b-e902-3e7e-b8f4-61a0508f0565"},{"kind":"sourceAvailability","id":"32f019df-7a32-34d7-9f56-febb4c4f10ee/LOANS"},{"kind":"sourceAvailability","id":"32f019df-7a32-34d7-9f56-febb4c4f10ee/RETURNS"},{"kind":"sourceAvailability","id":"32f019df-7a32-34d7-9f56-febb4c4f10ee/RECEIPTS"},{"kind":"sourceAvailability","id":"32f019df-7a32-34d7-9f56-febb4c4f10ee/TRANSFERS"},{"kind":"sourceAvailability","id":"32f019df-7a32-34d7-9f56-febb4c4f10ee/REPAIRS"},{"kind":"sourceAvailability","id":"32f019df-7a32-34d7-9f56-febb4c4f10ee/INTEGRATIONS"},{"kind":"sourceAvailability","id":"32f019df-7a32-34d7-9f56-febb4c4f10ee/INVENTORY"}]`

- Session : `{"scopeId":"aac4255b-e902-3e7e-b8f4-61a0508f0565","locationId":"125bed65-7385-3595-a653-902580e9539f","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas f5bcf254-ec5e-3a83-89f9-34d52b7b8bfb — dossier d'écart

- Exemplaire : `ef1a3fda-5be7-3ac7-85d0-33f60a2395f9`
- Famille privée : PRET_NON_PROJETE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"2a85ec8b-6ded-3bbf-83eb-11babd49a6e1","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"37d52580-9643-3ff8-bcb5-f675d39a401b","copyId":"ef1a3fda-5be7-3ac7-85d0-33f60a2395f9","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T06:02:25Z","recordedAt":"2026-09-27T06:02:35Z","payload":{"originalEventId":"f112541e-4efa-3687-b700-f464fc62313a","correlationId":"4e0b7c56-688a-3ef8-b75a-b7fb7e1da34e","outcome":"FAILED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"f112541e-4efa-3687-b700-f464fc62313a","physicalStatus":"ON_LOAN","physicalSiteId":"NORD","physicalLocationId":null,"projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État du stock informatique : `{"copyId":"ef1a3fda-5be7-3ac7-85d0-33f60a2395f9","status":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","sourceEventIds":["9545d41b-ddf2-350d-8955-31b8a1c2be08","bbbf0f50-c033-34eb-9d91-543d6ff62bac"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | fea9f585-aea8-37e0-a09f-d1756708486a | 2026-06-02T09:31:48Z | 2026-06-02T09:31:58Z | `{"operationId":"e9a02be5-462b-32d7-b40c-aa38891cda9d","correlationId":"17b112aa-beac-3926-a16d-cf863f89c434","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"af9aed3a-86a5-393f-adeb-44cd2c9a335f"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 7b8946de-7202-305a-b0f0-880b0d6c59c7 | 2026-06-03T09:31:48Z | 2026-06-03T09:31:58Z | `{"originalEventId":"fea9f585-aea8-37e0-a09f-d1756708486a","correlationId":"17b112aa-beac-3926-a16d-cf863f89c434","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 73de1845-8599-3bf2-9c48-e914f463a1b2 | 2026-08-01T09:31:48Z | 2026-08-01T09:31:58Z | `{"operationId":"83c77a89-fd03-39c1-a9ac-67cc749f21c1","correlationId":"e4a54239-f04c-323a-abbc-6d1b3b5da401","siteId":"CENTRE","locationId":null,"loanId":"ea27c7d1-da5c-3fa6-a004-064809e282a3"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 1012f48a-5c7d-3219-ae5b-5aedae3a5189 | 2026-08-01T09:32:48Z | 2026-08-01T09:32:58Z | `{"originalEventId":"73de1845-8599-3bf2-9c48-e914f463a1b2","correlationId":"e4a54239-f04c-323a-abbc-6d1b3b5da401","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 14da3286-6f63-37f6-8838-03ce4c107ee0 | 2026-08-21T09:31:48Z | 2026-08-21T09:31:58Z | `{"operationId":"0cef7bc6-e5e9-3e1d-a05d-b6c6de248543","correlationId":"a3058714-3e90-358d-a81f-4a46b361d6f0","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"ea27c7d1-da5c-3fa6-a004-064809e282a3"}` |
| Retour reçu (`ReturnReceived`) | 0d0134d9-00ec-3a61-8e6b-88ba45ce0cfb | 2026-08-21T09:32:18Z | 2026-08-21T09:32:28Z | `{"operationId":"422e0132-4cc1-37f7-8174-0b9c25d9974a","correlationId":"6b67a600-6626-32de-9e58-f203ed39f96a","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"ea27c7d1-da5c-3fa6-a004-064809e282a3"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 27fd811b-88b4-32fe-a8ec-b00bfe1171a7 | 2026-08-22T09:31:48Z | 2026-08-22T09:31:58Z | `{"originalEventId":"0d0134d9-00ec-3a61-8e6b-88ba45ce0cfb","correlationId":"6b67a600-6626-32de-9e58-f203ed39f96a","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Transfert expédié (`TransferDispatched`) | 58b3b61a-238d-3114-9cdb-bf46bcc2d0e6 | 2026-08-31T09:31:48Z | 2026-08-31T09:31:58Z | `{"operationId":"ccd0ce8c-2213-314a-9371-9d99cf7bbc7b","correlationId":"c2c7f1af-b928-32f0-b163-74e421b262aa","siteId":"CENTRE","locationId":null,"transferId":"11bb0348-6d21-39b4-9a1f-032f74248dc6","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 7f1b8020-af94-3118-876f-ba2c6a8984f5 | 2026-08-31T09:32:48Z | 2026-08-31T09:32:58Z | `{"originalEventId":"58b3b61a-238d-3114-9cdb-bf46bcc2d0e6","correlationId":"c2c7f1af-b928-32f0-b163-74e421b262aa","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| Transfert reçu (`TransferReceived`) | 9545d41b-ddf2-350d-8955-31b8a1c2be08 | 2026-09-01T09:31:48Z | 2026-09-01T09:31:58Z | `{"operationId":"c73812ae-5594-3751-aed7-2e2c0b6ae2ae","correlationId":"37a7028c-d986-3caf-8cb0-11d3e85bf94a","siteId":"NORD","locationId":"da6c862b-1e6d-3d21-b027-a9e0338b86a7","transferId":"11bb0348-6d21-39b4-9a1f-032f74248dc6","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | bbbf0f50-c033-34eb-9d91-543d6ff62bac | 2026-09-02T09:31:48Z | 2026-09-02T09:31:58Z | `{"originalEventId":"9545d41b-ddf2-350d-8955-31b8a1c2be08","correlationId":"37a7028c-d986-3caf-8cb0-11d3e85bf94a","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489"}` |
| Prêt enregistré (`LoanOpened`) | f112541e-4efa-3687-b700-f464fc62313a | 2026-09-27T06:01:55Z | 2026-09-27T06:02:05Z | `{"operationId":"bf2f0a89-5045-3d0f-8fc4-7b9ceb4057f6","correlationId":"4e0b7c56-688a-3ef8-b75a-b7fb7e1da34e","siteId":"NORD","locationId":null,"loanId":"c4b88dff-03e5-3ea6-9212-174bedc869ee"}` |

**Observations d'inventaire (`observations`)**

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"ef1a3fda-5be7-3ac7-85d0-33f60a2395f9","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ef1a3fda-5be7-3ac7-85d0-33f60a2395f9","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ef1a3fda-5be7-3ac7-85d0-33f60a2395f9","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ef1a3fda-5be7-3ac7-85d0-33f60a2395f9","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ef1a3fda-5be7-3ac7-85d0-33f60a2395f9","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ef1a3fda-5be7-3ac7-85d0-33f60a2395f9","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ef1a3fda-5be7-3ac7-85d0-33f60a2395f9","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"ef1a3fda-5be7-3ac7-85d0-33f60a2395f9"},{"kind":"sourceEvent","id":"fea9f585-aea8-37e0-a09f-d1756708486a"},{"kind":"sourceEvent","id":"7b8946de-7202-305a-b0f0-880b0d6c59c7"},{"kind":"sourceEvent","id":"73de1845-8599-3bf2-9c48-e914f463a1b2"},{"kind":"sourceEvent","id":"1012f48a-5c7d-3219-ae5b-5aedae3a5189"},{"kind":"sourceEvent","id":"14da3286-6f63-37f6-8838-03ce4c107ee0"},{"kind":"sourceEvent","id":"0d0134d9-00ec-3a61-8e6b-88ba45ce0cfb"},{"kind":"sourceEvent","id":"27fd811b-88b4-32fe-a8ec-b00bfe1171a7"},{"kind":"sourceEvent","id":"58b3b61a-238d-3114-9cdb-bf46bcc2d0e6"},{"kind":"sourceEvent","id":"7f1b8020-af94-3118-876f-ba2c6a8984f5"},{"kind":"sourceEvent","id":"9545d41b-ddf2-350d-8955-31b8a1c2be08"},{"kind":"sourceEvent","id":"bbbf0f50-c033-34eb-9d91-543d6ff62bac"},{"kind":"sourceEvent","id":"f112541e-4efa-3687-b700-f464fc62313a"},{"kind":"sourceAvailability","id":"ef1a3fda-5be7-3ac7-85d0-33f60a2395f9/LOANS"},{"kind":"sourceAvailability","id":"ef1a3fda-5be7-3ac7-85d0-33f60a2395f9/RETURNS"},{"kind":"sourceAvailability","id":"ef1a3fda-5be7-3ac7-85d0-33f60a2395f9/RECEIPTS"},{"kind":"sourceAvailability","id":"ef1a3fda-5be7-3ac7-85d0-33f60a2395f9/TRANSFERS"},{"kind":"sourceAvailability","id":"ef1a3fda-5be7-3ac7-85d0-33f60a2395f9/REPAIRS"},{"kind":"sourceAvailability","id":"ef1a3fda-5be7-3ac7-85d0-33f60a2395f9/INTEGRATIONS"},{"kind":"sourceAvailability","id":"ef1a3fda-5be7-3ac7-85d0-33f60a2395f9/INVENTORY"},{"kind":"inventoryScope","id":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5"},{"kind":"inventoryScope","id":"eb1ce5f1-7397-3b45-9385-ef328e09fec8"}]`

- Session : `{"scopeId":"f82b6d36-0ab3-3d7f-9b53-2443e280c4b5","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"eb1ce5f1-7397-3b45-9385-ef328e09fec8","locationId":"05d80dc4-3dc1-35d3-acce-b9021d7b2489","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas fbb7754d-d5a6-36dc-9a67-69d9e510d982 — dossier d'écart

- Exemplaire : `98e335cf-3b87-38c3-841e-33ef9683d8c6`
- Famille privée : RETOUR_NON_PROJETE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"97ce9112-89f2-3cbc-9106-7f5c980a62cd","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"fbae45d5-ce13-31f3-9ec9-674339210d09","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"98e335cf-3b87-38c3-841e-33ef9683d8c6","status":"RECEIVING","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","sourceEventIds":["fbae45d5-ce13-31f3-9ec9-674339210d09","bba6de60-1f14-3ec6-a19d-63f307a05c86"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 97ed9496-93fb-3b7b-8d8f-6c5cdcb2cd92 | 2026-06-01T15:32:06Z | 2026-06-01T15:32:16Z | `{"operationId":"255c10d3-6d27-3b88-b284-d94cb40f991c","correlationId":"d5e72481-4eee-3e50-b156-b32c5b83c978","siteId":"SUD","locationId":"847ae6bf-0254-3a06-b88b-2632fddd561c","receiptId":"b4fcb49f-acdd-3233-9bc1-041616ed127d"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 9244eb04-d7d6-38b9-b6f5-47ea202acdb9 | 2026-06-02T15:32:06Z | 2026-06-02T15:32:16Z | `{"originalEventId":"97ed9496-93fb-3b7b-8d8f-6c5cdcb2cd92","correlationId":"d5e72481-4eee-3e50-b156-b32c5b83c978","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Prêt enregistré (`LoanOpened`) | cba4bba8-a88a-345c-9630-1db6e37cc78f | 2026-07-31T15:32:06Z | 2026-07-31T15:32:16Z | `{"operationId":"527c02d2-75bd-3271-bd3b-a44328209522","correlationId":"c6cd8a5b-0f7c-3177-a919-c1907e10a580","siteId":"SUD","locationId":null,"loanId":"87749ad5-82b6-358a-8461-4b2f31064aef"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 9bced7c9-a957-3afe-8dd5-e074850fe826 | 2026-07-31T15:33:06Z | 2026-07-31T15:33:16Z | `{"originalEventId":"cba4bba8-a88a-345c-9630-1db6e37cc78f","correlationId":"c6cd8a5b-0f7c-3177-a919-c1907e10a580","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 63697c5a-34c1-3195-bc2e-8b6abad439e0 | 2026-08-20T15:32:06Z | 2026-08-20T15:32:16Z | `{"operationId":"c7ba4f9a-b4af-3d01-bd1e-14316961eed8","correlationId":"7975f451-5040-33cf-bc0f-1e347a2d5a56","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"87749ad5-82b6-358a-8461-4b2f31064aef"}` |
| Retour reçu (`ReturnReceived`) | ef5c27fd-2f08-39a5-9689-e60d8d3cb0a1 | 2026-08-20T15:32:36Z | 2026-08-20T15:32:46Z | `{"operationId":"f6f4f07c-fbc1-3c52-ac51-7d2bfc25f44e","correlationId":"7ad547d4-612b-39fa-9f9e-908f3f9205f2","siteId":"SUD","locationId":"fd933d55-aed6-3053-abfb-81b3d48c0475","loanId":"87749ad5-82b6-358a-8461-4b2f31064aef"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | ce98903d-14b2-3dcd-bd97-d11a69542a42 | 2026-08-21T15:32:06Z | 2026-08-21T15:32:16Z | `{"originalEventId":"ef5c27fd-2f08-39a5-9689-e60d8d3cb0a1","correlationId":"7ad547d4-612b-39fa-9f9e-908f3f9205f2","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"00f09742-c706-31b7-a4a0-c694a087403f"}` |
| Transfert expédié (`TransferDispatched`) | 0f6221e3-d483-3abe-94ac-cdf5b1ecfdab | 2026-08-30T15:32:06Z | 2026-08-30T15:32:16Z | `{"operationId":"a2059526-742b-3b52-9053-634605eb9b76","correlationId":"d616c72f-ef46-3c95-9009-b2c46261e46f","siteId":"SUD","locationId":null,"transferId":"039c6871-18f6-3b3d-a9f4-2db46481f94f","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 8485191f-e918-398e-9600-25d582ba6938 | 2026-08-30T15:33:06Z | 2026-08-30T15:33:16Z | `{"originalEventId":"0f6221e3-d483-3abe-94ac-cdf5b1ecfdab","correlationId":"d616c72f-ef46-3c95-9009-b2c46261e46f","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"SUD","locationId":null}` |
| Transfert reçu (`TransferReceived`) | 1c77ac98-0cab-3111-813b-c12af17bda62 | 2026-08-31T15:32:06Z | 2026-08-31T15:32:16Z | `{"operationId":"f1f5aff2-6721-35fb-b648-e520f3fb0e5d","correlationId":"ce300c25-6a84-3081-aec1-31c52cbbd7ba","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","transferId":"039c6871-18f6-3b3d-a9f4-2db46481f94f","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | c188b1ce-554a-3e2a-8e56-fe8da6eac396 | 2026-09-01T15:32:06Z | 2026-09-01T15:32:16Z | `{"originalEventId":"1c77ac98-0cab-3111-813b-c12af17bda62","correlationId":"ce300c25-6a84-3081-aec1-31c52cbbd7ba","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | d310daed-53e5-3af5-b28d-6958bea34aa5 | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"751473aa-e61a-3172-b166-d49236b68e72","correlationId":"a05e9f2e-74fc-328c-90e6-be9dad483cc9","siteId":"CENTRE","locationId":null,"loanId":"9671c72c-fb82-374c-beac-1c598ba72d4a"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 5c11b4c3-7eff-3ccf-b069-047135f22577 | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"d310daed-53e5-3af5-b28d-6958bea34aa5","correlationId":"a05e9f2e-74fc-328c-90e6-be9dad483cc9","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 17f6a780-1e11-3f84-9658-98f5e50dc6fb | 2026-09-27T09:19:15Z | 2026-09-27T09:19:25Z | `{"operationId":"53c1e751-cdb3-3cd7-b60b-2fbd83f4d89a","correlationId":"c9c4f6b8-d695-3d6b-b56f-62424785d0c2","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"9671c72c-fb82-374c-beac-1c598ba72d4a"}` |
| Retour reçu (`ReturnReceived`) | fbae45d5-ce13-31f3-9ec9-674339210d09 | 2026-09-27T09:19:45Z | 2026-09-27T09:19:55Z | `{"operationId":"ea5f0c6c-5e3f-3113-a397-f8a33a516438","correlationId":"4ae11104-2005-3f09-8911-80a04f6d7be9","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"9671c72c-fb82-374c-beac-1c598ba72d4a"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 6310518a-d3e0-38dc-8dee-ae26e521a2c3 | 2026-09-27T09:20:15Z | 2026-09-27T09:20:25Z | `{"originalEventId":"fbae45d5-ce13-31f3-9ec9-674339210d09","correlationId":"4ae11104-2005-3f09-8911-80a04f6d7be9","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | bba6de60-1f14-3ec6-a19d-63f307a05c86 | 2026-09-27T09:21:15Z | 2026-09-27T09:21:25Z | `{"originalEventId":"fbae45d5-ce13-31f3-9ec9-674339210d09","correlationId":"4ae11104-2005-3f09-8911-80a04f6d7be9","outcome":"APPLIED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"6a8b6b9b-8da0-3b9e-8833-c7a24aa58e06","copyId":"98e335cf-3b87-38c3-841e-33ef9683d8c6","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","scopeId":"6f793bcd-91fa-3e78-b8f1-0b09600a3310","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`
- `{"observationId":"325ac7c8-ab10-3417-ac90-f0e483c35bbe","copyId":"98e335cf-3b87-38c3-841e-33ef9683d8c6","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","scopeId":"cc995444-4842-3eca-8015-6f3c4f6c405c","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"98e335cf-3b87-38c3-841e-33ef9683d8c6","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"98e335cf-3b87-38c3-841e-33ef9683d8c6","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"98e335cf-3b87-38c3-841e-33ef9683d8c6","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"98e335cf-3b87-38c3-841e-33ef9683d8c6","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"98e335cf-3b87-38c3-841e-33ef9683d8c6","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"98e335cf-3b87-38c3-841e-33ef9683d8c6","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"98e335cf-3b87-38c3-841e-33ef9683d8c6","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"98e335cf-3b87-38c3-841e-33ef9683d8c6"},{"kind":"sourceEvent","id":"97ed9496-93fb-3b7b-8d8f-6c5cdcb2cd92"},{"kind":"sourceEvent","id":"9244eb04-d7d6-38b9-b6f5-47ea202acdb9"},{"kind":"sourceEvent","id":"cba4bba8-a88a-345c-9630-1db6e37cc78f"},{"kind":"sourceEvent","id":"9bced7c9-a957-3afe-8dd5-e074850fe826"},{"kind":"sourceEvent","id":"63697c5a-34c1-3195-bc2e-8b6abad439e0"},{"kind":"sourceEvent","id":"ef5c27fd-2f08-39a5-9689-e60d8d3cb0a1"},{"kind":"sourceEvent","id":"ce98903d-14b2-3dcd-bd97-d11a69542a42"},{"kind":"sourceEvent","id":"0f6221e3-d483-3abe-94ac-cdf5b1ecfdab"},{"kind":"sourceEvent","id":"8485191f-e918-398e-9600-25d582ba6938"},{"kind":"sourceEvent","id":"1c77ac98-0cab-3111-813b-c12af17bda62"},{"kind":"sourceEvent","id":"c188b1ce-554a-3e2a-8e56-fe8da6eac396"},{"kind":"sourceEvent","id":"d310daed-53e5-3af5-b28d-6958bea34aa5"},{"kind":"sourceEvent","id":"5c11b4c3-7eff-3ccf-b069-047135f22577"},{"kind":"sourceEvent","id":"17f6a780-1e11-3f84-9658-98f5e50dc6fb"},{"kind":"sourceEvent","id":"fbae45d5-ce13-31f3-9ec9-674339210d09"},{"kind":"sourceEvent","id":"6310518a-d3e0-38dc-8dee-ae26e521a2c3"},{"kind":"sourceEvent","id":"bba6de60-1f14-3ec6-a19d-63f307a05c86"},{"kind":"observation","id":"325ac7c8-ab10-3417-ac90-f0e483c35bbe"},{"kind":"inventoryScope","id":"cc995444-4842-3eca-8015-6f3c4f6c405c"},{"kind":"observation","id":"6a8b6b9b-8da0-3b9e-8833-c7a24aa58e06"},{"kind":"inventoryScope","id":"6f793bcd-91fa-3e78-b8f1-0b09600a3310"},{"kind":"sourceAvailability","id":"98e335cf-3b87-38c3-841e-33ef9683d8c6/LOANS"},{"kind":"sourceAvailability","id":"98e335cf-3b87-38c3-841e-33ef9683d8c6/RETURNS"},{"kind":"sourceAvailability","id":"98e335cf-3b87-38c3-841e-33ef9683d8c6/RECEIPTS"},{"kind":"sourceAvailability","id":"98e335cf-3b87-38c3-841e-33ef9683d8c6/TRANSFERS"},{"kind":"sourceAvailability","id":"98e335cf-3b87-38c3-841e-33ef9683d8c6/REPAIRS"},{"kind":"sourceAvailability","id":"98e335cf-3b87-38c3-841e-33ef9683d8c6/INTEGRATIONS"},{"kind":"sourceAvailability","id":"98e335cf-3b87-38c3-841e-33ef9683d8c6/INVENTORY"},{"kind":"inventoryScope","id":"f53ef7ea-6c60-3cc4-b1a7-e2a427b935f2"}]`

- Session : `{"scopeId":"cc995444-4842-3eca-8015-6f3c4f6c405c","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"6f793bcd-91fa-3e78-b8f1-0b09600a3310","locationId":"9c593eb4-3f3a-3d34-914e-a8e2b7e8d327","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"f53ef7ea-6c60-3cc4-b1a7-e2a427b935f2","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas fc5b2d7f-c5db-3c6f-b700-39d7441bf804 — dossier d'écart

- Exemplaire : `bee421f9-a919-334a-bcad-4c9b0e2dce64`
- Famille privée : REPARATION_NON_PROJETEE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"bdc59fc7-dc3a-3e52-af52-a8401e8c6270","kind":"STATUS_MISMATCH","expectedValue":"ON_SHELF","observedValue":"IN_REPAIR"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"ae7a0f1b-96c0-363e-bea1-0453cf4c4569","copyId":"bee421f9-a919-334a-bcad-4c9b0e2dce64","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T06:48:25Z","recordedAt":"2026-09-27T06:48:35Z","payload":{"originalEventId":"f3b8f980-5db5-36a8-9ad6-966a55466d09","correlationId":"4bf39219-778f-3498-b44e-ae340e90d067","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"f3b8f980-5db5-36a8-9ad6-966a55466d09","physicalStatus":"IN_REPAIR","physicalSiteId":"CENTRE","physicalLocationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État du stock informatique : `{"copyId":"bee421f9-a919-334a-bcad-4c9b0e2dce64","status":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","sourceEventIds":["a65ccf2e-06ac-387e-ac60-0301cd4573fd","8bc9ad12-39c9-394d-b6b6-fc7d17befd77"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 9d384033-206d-33dc-9206-fd1d4a8f06da | 2026-06-01T21:57:30Z | 2026-06-01T21:57:40Z | `{"operationId":"31a6ed74-d514-3ad5-96a2-23933e070342","correlationId":"299ad649-547f-3c79-8cc6-3b96cd5d3d0b","siteId":"CENTRE","locationId":"125bed65-7385-3595-a653-902580e9539f","receiptId":"8510a69b-8cdb-377c-8bff-5e19a0ed57a9"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 8f68da95-2b7c-340b-b5b0-ed4f8edba3d5 | 2026-06-02T21:57:30Z | 2026-06-02T21:57:40Z | `{"originalEventId":"9d384033-206d-33dc-9206-fd1d4a8f06da","correlationId":"299ad649-547f-3c79-8cc6-3b96cd5d3d0b","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Prêt enregistré (`LoanOpened`) | 06d3b50a-4b6d-31d3-b5d6-05d36fae5d9f | 2026-07-31T21:57:30Z | 2026-07-31T21:57:40Z | `{"operationId":"0e046434-e421-3625-9f16-446803e20c7a","correlationId":"873dfdfe-294c-3c24-b6c5-1cc95678765c","siteId":"CENTRE","locationId":null,"loanId":"cefe5b70-c187-3330-9ca4-1873525b3448"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | c2c7260a-f026-3f29-82d3-61ba30812104 | 2026-07-31T21:58:30Z | 2026-07-31T21:58:40Z | `{"originalEventId":"06d3b50a-4b6d-31d3-b5d6-05d36fae5d9f","correlationId":"873dfdfe-294c-3c24-b6c5-1cc95678765c","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 164d426d-272e-322b-bad9-d3fcb1b52a13 | 2026-08-20T21:57:30Z | 2026-08-20T21:57:40Z | `{"operationId":"935c31cc-e9a4-3710-9b9c-7d6d47bd2647","correlationId":"74dd9f5b-3bc0-381b-8e26-6304347ac9ba","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"cefe5b70-c187-3330-9ca4-1873525b3448"}` |
| Retour reçu (`ReturnReceived`) | a65ccf2e-06ac-387e-ac60-0301cd4573fd | 2026-08-20T21:58:00Z | 2026-08-20T21:58:10Z | `{"operationId":"3e529853-6b06-3f91-bb7f-44a42cebb8b4","correlationId":"93083a35-1b4f-38af-9350-8b080d2a716d","siteId":"CENTRE","locationId":"5e0a43fa-fa23-37fe-9e6b-a7979cfe2ef5","loanId":"cefe5b70-c187-3330-9ca4-1873525b3448"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 8bc9ad12-39c9-394d-b6b6-fc7d17befd77 | 2026-08-21T21:57:30Z | 2026-08-21T21:57:40Z | `{"originalEventId":"a65ccf2e-06ac-387e-ac60-0301cd4573fd","correlationId":"93083a35-1b4f-38af-9350-8b080d2a716d","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310"}` |
| Réparation engagée (`RepairStarted`) | f3b8f980-5db5-36a8-9ad6-966a55466d09 | 2026-09-27T06:47:55Z | 2026-09-27T06:48:05Z | `{"operationId":"e09cd3c3-a78c-33a6-a26d-69b014c75aa4","correlationId":"4bf39219-778f-3498-b44e-ae340e90d067","siteId":"CENTRE","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","repairId":"39111ade-7ff1-329a-944e-288d6c23b674"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"df346839-37eb-3ee8-876e-5564901a421b","copyId":"bee421f9-a919-334a-bcad-4c9b0e2dce64","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","scopeId":"d60e9f22-40a9-3088-8d67-f59de1f0f246","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"bee421f9-a919-334a-bcad-4c9b0e2dce64","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bee421f9-a919-334a-bcad-4c9b0e2dce64","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bee421f9-a919-334a-bcad-4c9b0e2dce64","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bee421f9-a919-334a-bcad-4c9b0e2dce64","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"bee421f9-a919-334a-bcad-4c9b0e2dce64","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bee421f9-a919-334a-bcad-4c9b0e2dce64","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bee421f9-a919-334a-bcad-4c9b0e2dce64","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"bee421f9-a919-334a-bcad-4c9b0e2dce64"},{"kind":"sourceEvent","id":"9d384033-206d-33dc-9206-fd1d4a8f06da"},{"kind":"sourceEvent","id":"8f68da95-2b7c-340b-b5b0-ed4f8edba3d5"},{"kind":"sourceEvent","id":"06d3b50a-4b6d-31d3-b5d6-05d36fae5d9f"},{"kind":"sourceEvent","id":"c2c7260a-f026-3f29-82d3-61ba30812104"},{"kind":"sourceEvent","id":"164d426d-272e-322b-bad9-d3fcb1b52a13"},{"kind":"sourceEvent","id":"a65ccf2e-06ac-387e-ac60-0301cd4573fd"},{"kind":"sourceEvent","id":"8bc9ad12-39c9-394d-b6b6-fc7d17befd77"},{"kind":"sourceEvent","id":"f3b8f980-5db5-36a8-9ad6-966a55466d09"},{"kind":"observation","id":"df346839-37eb-3ee8-876e-5564901a421b"},{"kind":"inventoryScope","id":"d60e9f22-40a9-3088-8d67-f59de1f0f246"},{"kind":"sourceAvailability","id":"bee421f9-a919-334a-bcad-4c9b0e2dce64/LOANS"},{"kind":"sourceAvailability","id":"bee421f9-a919-334a-bcad-4c9b0e2dce64/RETURNS"},{"kind":"sourceAvailability","id":"bee421f9-a919-334a-bcad-4c9b0e2dce64/RECEIPTS"},{"kind":"sourceAvailability","id":"bee421f9-a919-334a-bcad-4c9b0e2dce64/TRANSFERS"},{"kind":"sourceAvailability","id":"bee421f9-a919-334a-bcad-4c9b0e2dce64/REPAIRS"},{"kind":"sourceAvailability","id":"bee421f9-a919-334a-bcad-4c9b0e2dce64/INTEGRATIONS"},{"kind":"sourceAvailability","id":"bee421f9-a919-334a-bcad-4c9b0e2dce64/INVENTORY"},{"kind":"inventoryScope","id":"7d46c7a4-f137-377d-8437-5d11eeb4049d"}]`

- Session : `{"scopeId":"d60e9f22-40a9-3088-8d67-f59de1f0f246","locationId":"14f2d626-9459-3fee-9d3a-daf738f7e59a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"7d46c7a4-f137-377d-8437-5d11eeb4049d","locationId":"f1dac8d5-876d-3ec9-9579-d2c4682a4310","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`
