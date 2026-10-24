# Catalogue privé — validation

Généré depuis l'oracle de simulation v1.0.0. Réservé aux tests et à l'expert ; ne pas exposer au worker IA.

Référence : 2026-09-30T12:00:00Z; seed : 4242. Chaque attente est déclarée par le scénario, sans exécuter un moteur diagnostique.

## Cas 04a40fde-b0f7-34bb-8f07-fb5fdab643e2

- Exemplaire : `07700c82-cbeb-3010-b2d1-52304657be43`
- Famille privée : RETARD_INTEGRATION ; variante : POSITIVE
- Symptômes : `[{"symptomId":"657e7d18-a4bc-302a-8303-830e2d45a943","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["QUEUE_PENDING"],"hiddenSourceEvents":[],"mechanism":"Événement métier en attente normale dans la fenêtre contractuelle de 900 secondes.","businessEventId":"40cadddd-ab44-3855-b75d-01635ef9f582","physicalStatus":"ON_LOAN","physicalSiteId":"CENTRE","physicalLocationId":null,"projectionPerturbation":"QUEUED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["INTEGRATION_PENDING_WITHIN_SLA"],"explanation":"Prêt actif et traitement en file depuis moins de 900 secondes ; transition attendue, pas de panne persistante démontrée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Recontrôler après la fenêtre de traitement"]}`
- État projeté : `{"copyId":"07700c82-cbeb-3010-b2d1-52304657be43","status":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","sourceEventIds":["0532cd24-db9c-34bc-86ac-459f125fecb3","a80814b5-6ecf-3014-befb-00fb4368490d"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | e29bfbb3-bcd8-37cd-af65-983e035dcae0 | 2026-06-01T18:18:32Z | 2026-06-01T18:18:42Z | `{"operationId":"33bd9fc6-f891-39f1-9f12-2d97193b0c8d","correlationId":"1aee0b81-2d58-3bb0-8e9d-701e8b67d93c","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"0371e11a-3ab0-319a-9905-f5275287a057"}` |
| IntegrationProcessed | aef941be-bc40-3884-a7a1-34a34548e9e5 | 2026-06-02T18:18:32Z | 2026-06-02T18:18:42Z | `{"originalEventId":"e29bfbb3-bcd8-37cd-af65-983e035dcae0","correlationId":"1aee0b81-2d58-3bb0-8e9d-701e8b67d93c","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | d2300598-618c-3666-9e85-18a8c38eb3b1 | 2026-07-31T18:18:32Z | 2026-07-31T18:18:42Z | `{"operationId":"1032d6f4-032b-38ed-a4dc-6bd786d26a1f","correlationId":"79ccb0bd-8ee5-31bb-917f-5e3f7c74db0a","siteId":"CENTRE","locationId":null,"loanId":"8dcb3fd3-f18b-3684-b4ff-3cbdecd0bda2"}` |
| IntegrationProcessed | 59b11abd-e210-32e8-8f1c-e75e79fc27f4 | 2026-07-31T18:19:32Z | 2026-07-31T18:19:42Z | `{"originalEventId":"d2300598-618c-3666-9e85-18a8c38eb3b1","correlationId":"79ccb0bd-8ee5-31bb-917f-5e3f7c74db0a","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 4951812a-46ce-3f64-a068-3f2433d8f4f9 | 2026-08-20T18:18:32Z | 2026-08-20T18:18:42Z | `{"operationId":"148fd065-12e7-3a86-89d1-61ca178f081a","correlationId":"c376cd91-e4be-3b55-8a49-ccdd7efc0106","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"8dcb3fd3-f18b-3684-b4ff-3cbdecd0bda2"}` |
| ReturnReceived | 0532cd24-db9c-34bc-86ac-459f125fecb3 | 2026-08-20T18:19:02Z | 2026-08-20T18:19:12Z | `{"operationId":"ea76d70b-c2d5-3483-9e43-1c0cce3221ff","correlationId":"8d135bab-81a1-3510-8ad4-620a4eec4c3e","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"8dcb3fd3-f18b-3684-b4ff-3cbdecd0bda2"}` |
| IntegrationProcessed | a80814b5-6ecf-3014-befb-00fb4368490d | 2026-08-21T18:18:32Z | 2026-08-21T18:18:42Z | `{"originalEventId":"0532cd24-db9c-34bc-86ac-459f125fecb3","correlationId":"8d135bab-81a1-3510-8ad4-620a4eec4c3e","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 40cadddd-ab44-3855-b75d-01635ef9f582 | 2026-09-30T11:53:57Z | 2026-09-30T11:54:07Z | `{"operationId":"3a8145b6-6749-3bdc-88a2-573eda06466a","correlationId":"968b662c-21d9-300a-8861-83fa62ff4134","siteId":"CENTRE","locationId":null,"loanId":"35a07b7a-91b6-3f2d-bc24-1f699fba5209"}` |
| IntegrationProcessed | f9c74e4a-d76b-3305-92ad-a9a78d558637 | 2026-09-30T11:54:27Z | 2026-09-30T11:54:37Z | `{"originalEventId":"40cadddd-ab44-3855-b75d-01635ef9f582","correlationId":"968b662c-21d9-300a-8861-83fa62ff4134","outcome":"QUEUED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1}` |

**observations**

**sourceAvailability**
- `{"copyId":"07700c82-cbeb-3010-b2d1-52304657be43","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"07700c82-cbeb-3010-b2d1-52304657be43","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"07700c82-cbeb-3010-b2d1-52304657be43","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"07700c82-cbeb-3010-b2d1-52304657be43","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"07700c82-cbeb-3010-b2d1-52304657be43","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"07700c82-cbeb-3010-b2d1-52304657be43","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"07700c82-cbeb-3010-b2d1-52304657be43","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"07700c82-cbeb-3010-b2d1-52304657be43"},{"kind":"sourceEvent","id":"e29bfbb3-bcd8-37cd-af65-983e035dcae0"},{"kind":"sourceEvent","id":"aef941be-bc40-3884-a7a1-34a34548e9e5"},{"kind":"sourceEvent","id":"d2300598-618c-3666-9e85-18a8c38eb3b1"},{"kind":"sourceEvent","id":"59b11abd-e210-32e8-8f1c-e75e79fc27f4"},{"kind":"sourceEvent","id":"4951812a-46ce-3f64-a068-3f2433d8f4f9"},{"kind":"sourceEvent","id":"0532cd24-db9c-34bc-86ac-459f125fecb3"},{"kind":"sourceEvent","id":"a80814b5-6ecf-3014-befb-00fb4368490d"},{"kind":"sourceEvent","id":"40cadddd-ab44-3855-b75d-01635ef9f582"},{"kind":"sourceEvent","id":"f9c74e4a-d76b-3305-92ad-a9a78d558637"},{"kind":"sourceAvailability","id":"07700c82-cbeb-3010-b2d1-52304657be43/LOANS"},{"kind":"sourceAvailability","id":"07700c82-cbeb-3010-b2d1-52304657be43/RETURNS"},{"kind":"sourceAvailability","id":"07700c82-cbeb-3010-b2d1-52304657be43/RECEIPTS"},{"kind":"sourceAvailability","id":"07700c82-cbeb-3010-b2d1-52304657be43/TRANSFERS"},{"kind":"sourceAvailability","id":"07700c82-cbeb-3010-b2d1-52304657be43/REPAIRS"},{"kind":"sourceAvailability","id":"07700c82-cbeb-3010-b2d1-52304657be43/INTEGRATIONS"},{"kind":"sourceAvailability","id":"07700c82-cbeb-3010-b2d1-52304657be43/INVENTORY"},{"kind":"inventoryScope","id":"8021dca8-65fe-3cab-985e-d4fb9df5c308"},{"kind":"inventoryScope","id":"b0027657-6abb-37d2-8bc4-9341357dfe79"},{"kind":"inventoryScope","id":"4757f5eb-f104-3413-b118-51a6951a650c"},{"kind":"inventoryScope","id":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592"},{"kind":"inventoryScope","id":"40e94818-3a62-34a4-b461-398d7b9ffca2"},{"kind":"inventoryScope","id":"ed60c0be-b218-3f9e-8aa8-69c16f76c22e"},{"kind":"inventoryScope","id":"35514ff7-97f8-345d-8cf7-6cee969a726c"}]`

- Session : `{"scopeId":"8021dca8-65fe-3cab-985e-d4fb9df5c308","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b0027657-6abb-37d2-8bc4-9341357dfe79","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4757f5eb-f104-3413-b118-51a6951a650c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"40e94818-3a62-34a4-b461-398d7b9ffca2","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"ed60c0be-b218-3f9e-8aa8-69c16f76c22e","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"35514ff7-97f8-345d-8cf7-6cee969a726c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 05e1db86-b774-3df7-a73a-60687e4ce6c9

- Exemplaire : `cc7f0f50-99e8-36ce-9805-08b8dff1a638`
- Famille privée : PRET_NON_PROJETE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"7e5036f2-7f01-3491-8b5a-0e7230310543","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"f7118fed-b6cb-361f-8c51-8167082153e2","copyId":"cc7f0f50-99e8-36ce-9805-08b8dff1a638","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T10:32:56Z","recordedAt":"2026-09-27T10:33:06Z","payload":{"originalEventId":"879d8d6b-3673-391e-a939-25dd0cf9de1f","correlationId":"963656b4-b190-3712-a776-348049480bbb","outcome":"FAILED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"879d8d6b-3673-391e-a939-25dd0cf9de1f","physicalStatus":"ON_LOAN","physicalSiteId":"NORD","physicalLocationId":null,"projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État projeté : `{"copyId":"cc7f0f50-99e8-36ce-9805-08b8dff1a638","status":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","sourceEventIds":["8d6414f8-49cc-3909-88c3-eb55c59f83c2","18e1a182-9e9f-3584-95e2-d18c60fb1d98"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | a5b065a5-2ee0-3d90-accb-797b7aa0b456 | 2026-06-02T06:46:20Z | 2026-06-02T06:46:30Z | `{"operationId":"d3050931-764e-3d84-bcd7-45290488c5b3","correlationId":"cf92850f-ce24-3b73-9e0e-a9af3aef1ad6","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"51dd20a9-fe55-3299-bcc8-9da9e73d33a5"}` |
| IntegrationProcessed | f45ad7d1-7708-37c4-8a6c-3506c95882f2 | 2026-06-03T06:46:20Z | 2026-06-03T06:46:30Z | `{"originalEventId":"a5b065a5-2ee0-3d90-accb-797b7aa0b456","correlationId":"cf92850f-ce24-3b73-9e0e-a9af3aef1ad6","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | d6c30d10-217f-377a-a60b-e0744acbf131 | 2026-08-01T06:46:20Z | 2026-08-01T06:46:30Z | `{"operationId":"1a10bbd9-08eb-3e69-8167-c706192dcacc","correlationId":"d3bbe8ad-4efe-39a7-89ed-4392f7865adc","siteId":"CENTRE","locationId":null,"loanId":"b68339ed-11d1-363e-b20a-86cec7a743ad"}` |
| IntegrationProcessed | 60c336ed-eaa1-3f36-bef7-9977711b9877 | 2026-08-01T06:47:20Z | 2026-08-01T06:47:30Z | `{"originalEventId":"d6c30d10-217f-377a-a60b-e0744acbf131","correlationId":"d3bbe8ad-4efe-39a7-89ed-4392f7865adc","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 4cdb25ab-67c1-34a7-97d0-88e35d942b55 | 2026-08-21T06:46:20Z | 2026-08-21T06:46:30Z | `{"operationId":"c61dd07f-b8b8-36a4-ba0f-4e5f7260e409","correlationId":"a5c96ea9-1fe8-3351-aea0-38c3674675f9","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"b68339ed-11d1-363e-b20a-86cec7a743ad"}` |
| ReturnReceived | f8700d7b-a338-3659-8495-4e37c0f7f5d5 | 2026-08-21T06:46:50Z | 2026-08-21T06:47:00Z | `{"operationId":"34343f72-6283-30c1-93e1-7a6961a9733d","correlationId":"298a2767-f24f-3fe0-ad53-a7dde8494106","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"b68339ed-11d1-363e-b20a-86cec7a743ad"}` |
| IntegrationProcessed | 91202999-7a46-3b7c-ad6a-2644a3eacddb | 2026-08-22T06:46:20Z | 2026-08-22T06:46:30Z | `{"originalEventId":"f8700d7b-a338-3659-8495-4e37c0f7f5d5","correlationId":"298a2767-f24f-3fe0-ad53-a7dde8494106","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| TransferDispatched | 5ab0fddf-3cd8-3c7f-b653-e6501beb2111 | 2026-08-31T06:46:20Z | 2026-08-31T06:46:30Z | `{"operationId":"dec29306-6b49-388c-b299-06569ac7789d","correlationId":"dfda96aa-a59a-3262-814d-cf51b037c0f6","siteId":"CENTRE","locationId":null,"transferId":"589d719b-f2f5-3ea1-aec5-91a019fa28c5","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | efb5acf5-1a03-3d7d-8b46-a190053bc638 | 2026-08-31T06:47:20Z | 2026-08-31T06:47:30Z | `{"originalEventId":"5ab0fddf-3cd8-3c7f-b653-e6501beb2111","correlationId":"dfda96aa-a59a-3262-814d-cf51b037c0f6","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| TransferReceived | 8d6414f8-49cc-3909-88c3-eb55c59f83c2 | 2026-09-01T06:46:20Z | 2026-09-01T06:46:30Z | `{"operationId":"5634f9c7-f856-3738-930a-1500e08523ed","correlationId":"223d16aa-396c-3736-a4ed-9a0dfab014ed","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","transferId":"589d719b-f2f5-3ea1-aec5-91a019fa28c5","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | 18e1a182-9e9f-3584-95e2-d18c60fb1d98 | 2026-09-02T06:46:20Z | 2026-09-02T06:46:30Z | `{"originalEventId":"8d6414f8-49cc-3909-88c3-eb55c59f83c2","correlationId":"223d16aa-396c-3736-a4ed-9a0dfab014ed","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 879d8d6b-3673-391e-a939-25dd0cf9de1f | 2026-09-27T10:32:26Z | 2026-09-27T10:32:36Z | `{"operationId":"34fa672a-547c-3a33-b840-ca18376a8bcf","correlationId":"963656b4-b190-3712-a776-348049480bbb","siteId":"NORD","locationId":null,"loanId":"28f5dae2-df3c-3a9b-ab8b-324aaa6b1270"}` |

**observations**

**sourceAvailability**
- `{"copyId":"cc7f0f50-99e8-36ce-9805-08b8dff1a638","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cc7f0f50-99e8-36ce-9805-08b8dff1a638","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cc7f0f50-99e8-36ce-9805-08b8dff1a638","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cc7f0f50-99e8-36ce-9805-08b8dff1a638","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cc7f0f50-99e8-36ce-9805-08b8dff1a638","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cc7f0f50-99e8-36ce-9805-08b8dff1a638","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cc7f0f50-99e8-36ce-9805-08b8dff1a638","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"cc7f0f50-99e8-36ce-9805-08b8dff1a638"},{"kind":"sourceEvent","id":"a5b065a5-2ee0-3d90-accb-797b7aa0b456"},{"kind":"sourceEvent","id":"f45ad7d1-7708-37c4-8a6c-3506c95882f2"},{"kind":"sourceEvent","id":"d6c30d10-217f-377a-a60b-e0744acbf131"},{"kind":"sourceEvent","id":"60c336ed-eaa1-3f36-bef7-9977711b9877"},{"kind":"sourceEvent","id":"4cdb25ab-67c1-34a7-97d0-88e35d942b55"},{"kind":"sourceEvent","id":"f8700d7b-a338-3659-8495-4e37c0f7f5d5"},{"kind":"sourceEvent","id":"91202999-7a46-3b7c-ad6a-2644a3eacddb"},{"kind":"sourceEvent","id":"5ab0fddf-3cd8-3c7f-b653-e6501beb2111"},{"kind":"sourceEvent","id":"efb5acf5-1a03-3d7d-8b46-a190053bc638"},{"kind":"sourceEvent","id":"8d6414f8-49cc-3909-88c3-eb55c59f83c2"},{"kind":"sourceEvent","id":"18e1a182-9e9f-3584-95e2-d18c60fb1d98"},{"kind":"sourceEvent","id":"879d8d6b-3673-391e-a939-25dd0cf9de1f"},{"kind":"sourceAvailability","id":"cc7f0f50-99e8-36ce-9805-08b8dff1a638/LOANS"},{"kind":"sourceAvailability","id":"cc7f0f50-99e8-36ce-9805-08b8dff1a638/RETURNS"},{"kind":"sourceAvailability","id":"cc7f0f50-99e8-36ce-9805-08b8dff1a638/RECEIPTS"},{"kind":"sourceAvailability","id":"cc7f0f50-99e8-36ce-9805-08b8dff1a638/TRANSFERS"},{"kind":"sourceAvailability","id":"cc7f0f50-99e8-36ce-9805-08b8dff1a638/REPAIRS"},{"kind":"sourceAvailability","id":"cc7f0f50-99e8-36ce-9805-08b8dff1a638/INTEGRATIONS"},{"kind":"sourceAvailability","id":"cc7f0f50-99e8-36ce-9805-08b8dff1a638/INVENTORY"},{"kind":"inventoryScope","id":"07c957d2-b086-3b6b-9915-d21f1d5906cf"}]`

- Session : `{"scopeId":"07c957d2-b086-3b6b-9915-d21f1d5906cf","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 06bac0e5-01d3-3866-afe7-5855935a9519

- Exemplaire : `30f31828-2a90-3c59-bcbf-c6f0f419ecd1`
- Famille privée : PRET_NON_PROJETE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"aa775648-2a19-3d29-bcf8-f5f44e40a812","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"8b6dc718-1554-382f-8e76-119557370e1b","copyId":"30f31828-2a90-3c59-bcbf-c6f0f419ecd1","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T06:39:04Z","recordedAt":"2026-09-27T06:39:14Z","payload":{"originalEventId":"3434b6ea-9060-3ac9-8e85-324db0a439cb","correlationId":"3add644b-89a5-304c-bfe9-88c08c13ba57","outcome":"FAILED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"3434b6ea-9060-3ac9-8e85-324db0a439cb","physicalStatus":"ON_LOAN","physicalSiteId":"NORD","physicalLocationId":null,"projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État projeté : `{"copyId":"30f31828-2a90-3c59-bcbf-c6f0f419ecd1","status":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","sourceEventIds":["d45582cf-9521-3361-8ee9-5b3f77d36ecd","2fb8325a-2252-354b-b6b8-9bccc8fb10c5"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | d679edc9-49d0-3555-a5ec-38d587f3be7d | 2026-06-02T05:38:00Z | 2026-06-02T05:38:10Z | `{"operationId":"031660a5-c672-3220-8355-9c232f0b90f5","correlationId":"a721824a-768c-3745-bf56-2dad741e8d06","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"f39581ad-1813-3cfc-bf73-8b6972f067eb"}` |
| IntegrationProcessed | b4c6d45f-8fd2-3fc8-a12d-837b7bfbb3c1 | 2026-06-03T05:38:00Z | 2026-06-03T05:38:10Z | `{"originalEventId":"d679edc9-49d0-3555-a5ec-38d587f3be7d","correlationId":"a721824a-768c-3745-bf56-2dad741e8d06","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 769a82f6-3aa9-3ee1-91db-5f5002b14f53 | 2026-08-01T05:38:00Z | 2026-08-01T05:38:10Z | `{"operationId":"fd839055-5ca1-3f11-b46a-c9c7edb1f6c9","correlationId":"1a1087d7-21e7-3b62-a32d-995fbf429a01","siteId":"NORD","locationId":null,"loanId":"ffee3dc0-4412-3d39-bece-6e6a4679b2c6"}` |
| IntegrationProcessed | bbbd7aed-1c21-3d33-aee7-eda59001f1a2 | 2026-08-01T05:39:00Z | 2026-08-01T05:39:10Z | `{"originalEventId":"769a82f6-3aa9-3ee1-91db-5f5002b14f53","correlationId":"1a1087d7-21e7-3b62-a32d-995fbf429a01","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | 8dd38db6-87e8-38ea-b576-7f8945d4609f | 2026-08-21T05:38:00Z | 2026-08-21T05:38:10Z | `{"operationId":"cbf1d251-b179-3fdb-a267-ba6fff65c41e","correlationId":"cc047feb-9339-3931-b23f-255b6a3d8603","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"ffee3dc0-4412-3d39-bece-6e6a4679b2c6"}` |
| ReturnReceived | d45582cf-9521-3361-8ee9-5b3f77d36ecd | 2026-08-21T05:38:30Z | 2026-08-21T05:38:40Z | `{"operationId":"a7506d3f-65c9-3166-9ea6-f0b183101be9","correlationId":"99fd636b-3ff1-3f78-a7f2-1231c7da41c0","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"ffee3dc0-4412-3d39-bece-6e6a4679b2c6"}` |
| IntegrationProcessed | 2fb8325a-2252-354b-b6b8-9bccc8fb10c5 | 2026-08-22T05:38:00Z | 2026-08-22T05:38:10Z | `{"originalEventId":"d45582cf-9521-3361-8ee9-5b3f77d36ecd","correlationId":"99fd636b-3ff1-3f78-a7f2-1231c7da41c0","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 3434b6ea-9060-3ac9-8e85-324db0a439cb | 2026-09-27T06:38:34Z | 2026-09-27T06:38:44Z | `{"operationId":"b493cc01-5de5-3ea8-be01-6e30b7fff305","correlationId":"3add644b-89a5-304c-bfe9-88c08c13ba57","siteId":"NORD","locationId":null,"loanId":"db0a4291-f639-32de-b85d-65f13769ef40"}` |

**observations**

**sourceAvailability**
- `{"copyId":"30f31828-2a90-3c59-bcbf-c6f0f419ecd1","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"30f31828-2a90-3c59-bcbf-c6f0f419ecd1","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"30f31828-2a90-3c59-bcbf-c6f0f419ecd1","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"30f31828-2a90-3c59-bcbf-c6f0f419ecd1","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"30f31828-2a90-3c59-bcbf-c6f0f419ecd1","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"30f31828-2a90-3c59-bcbf-c6f0f419ecd1","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"30f31828-2a90-3c59-bcbf-c6f0f419ecd1","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"30f31828-2a90-3c59-bcbf-c6f0f419ecd1"},{"kind":"sourceEvent","id":"d679edc9-49d0-3555-a5ec-38d587f3be7d"},{"kind":"sourceEvent","id":"b4c6d45f-8fd2-3fc8-a12d-837b7bfbb3c1"},{"kind":"sourceEvent","id":"769a82f6-3aa9-3ee1-91db-5f5002b14f53"},{"kind":"sourceEvent","id":"bbbd7aed-1c21-3d33-aee7-eda59001f1a2"},{"kind":"sourceEvent","id":"8dd38db6-87e8-38ea-b576-7f8945d4609f"},{"kind":"sourceEvent","id":"d45582cf-9521-3361-8ee9-5b3f77d36ecd"},{"kind":"sourceEvent","id":"2fb8325a-2252-354b-b6b8-9bccc8fb10c5"},{"kind":"sourceEvent","id":"3434b6ea-9060-3ac9-8e85-324db0a439cb"},{"kind":"sourceAvailability","id":"30f31828-2a90-3c59-bcbf-c6f0f419ecd1/LOANS"},{"kind":"sourceAvailability","id":"30f31828-2a90-3c59-bcbf-c6f0f419ecd1/RETURNS"},{"kind":"sourceAvailability","id":"30f31828-2a90-3c59-bcbf-c6f0f419ecd1/RECEIPTS"},{"kind":"sourceAvailability","id":"30f31828-2a90-3c59-bcbf-c6f0f419ecd1/TRANSFERS"},{"kind":"sourceAvailability","id":"30f31828-2a90-3c59-bcbf-c6f0f419ecd1/REPAIRS"},{"kind":"sourceAvailability","id":"30f31828-2a90-3c59-bcbf-c6f0f419ecd1/INTEGRATIONS"},{"kind":"sourceAvailability","id":"30f31828-2a90-3c59-bcbf-c6f0f419ecd1/INVENTORY"},{"kind":"inventoryScope","id":"07c957d2-b086-3b6b-9915-d21f1d5906cf"}]`

- Session : `{"scopeId":"07c957d2-b086-3b6b-9915-d21f1d5906cf","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 0e302059-bf26-3247-a90c-d966e7fdcf62

- Exemplaire : `bffa5f63-efe7-3a63-ba01-845fc1b993f6`
- Famille privée : ABSENCE_INEXPLIQUEE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"f2311e7c-fd52-31f0-8882-131b8515974f","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["UNOBSERVED_MOVE"],"mechanism":"Exemplaire déplacé hors du périmètre instrumenté ; la destination n'est pas exposée et aucune perte n'est simulée.","physicalStatus":"ON_SHELF","physicalSiteId":"SUD","physicalLocationId":null,"evidenceMask":"Collecte indisponible"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"L'absence de lecture ne démontre ni la destination, ni une perte, ni un vol ; données insuffisantes pour attribuer une cause.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État projeté : `{"copyId":"bffa5f63-efe7-3a63-ba01-845fc1b993f6","status":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","sourceEventIds":["85e83c12-8627-3055-a031-d65996522af0","0e9f389c-378b-33c4-9a11-0b141d93633d"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 81ee5957-4d47-314a-bdc5-25dbab56e888 | 2026-06-01T16:30:19Z | 2026-06-01T16:30:29Z | `{"operationId":"4ff1d363-266f-3780-bdc2-d9537485b7b7","correlationId":"7fe31f55-c9bf-3ff7-824e-a60f2f8e8c9d","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"42cd065b-2547-32a6-844a-b86523ead1b7"}` |
| IntegrationProcessed | f81322e1-6c06-38e0-b93d-b1c9e823b1fc | 2026-06-02T16:30:19Z | 2026-06-02T16:30:29Z | `{"originalEventId":"81ee5957-4d47-314a-bdc5-25dbab56e888","correlationId":"7fe31f55-c9bf-3ff7-824e-a60f2f8e8c9d","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | 93e5d25e-e914-3b42-ab63-2535aa248222 | 2026-07-31T16:30:19Z | 2026-07-31T16:30:29Z | `{"operationId":"1af461dc-534b-3c65-bb79-c6db9ab02ac9","correlationId":"ec3ecca1-58a8-31e5-9351-8d7fdb049e4b","siteId":"SUD","locationId":null,"loanId":"b06a8670-f807-33b9-b908-ee6776bb3cc0"}` |
| IntegrationProcessed | 70370ec5-16a9-3f9a-b6e2-ae95714e764c | 2026-07-31T16:31:19Z | 2026-07-31T16:31:29Z | `{"originalEventId":"93e5d25e-e914-3b42-ab63-2535aa248222","correlationId":"ec3ecca1-58a8-31e5-9351-8d7fdb049e4b","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | 617f0744-abd1-3a7b-996d-568853c2b34f | 2026-08-20T16:30:19Z | 2026-08-20T16:30:29Z | `{"operationId":"f6484fc8-593f-3d6d-8352-c53f099b60df","correlationId":"bec85527-184d-32b2-9830-a29c356dbe65","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"b06a8670-f807-33b9-b908-ee6776bb3cc0"}` |
| ReturnReceived | 85e83c12-8627-3055-a031-d65996522af0 | 2026-08-20T16:30:49Z | 2026-08-20T16:30:59Z | `{"operationId":"72a94d24-1c0a-34e5-8334-1dbe6fafd54e","correlationId":"656f008e-61e3-3759-ab63-901fe96e4544","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"b06a8670-f807-33b9-b908-ee6776bb3cc0"}` |
| IntegrationProcessed | 0e9f389c-378b-33c4-9a11-0b141d93633d | 2026-08-21T16:30:19Z | 2026-08-21T16:30:29Z | `{"originalEventId":"85e83c12-8627-3055-a031-d65996522af0","correlationId":"656f008e-61e3-3759-ab63-901fe96e4544","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |

**observations**

**sourceAvailability**
- `{"copyId":"bffa5f63-efe7-3a63-ba01-845fc1b993f6","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bffa5f63-efe7-3a63-ba01-845fc1b993f6","source":"TRANSFERS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"bffa5f63-efe7-3a63-ba01-845fc1b993f6","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bffa5f63-efe7-3a63-ba01-845fc1b993f6","source":"INVENTORY","status":"UNAVAILABLE","completeFrom":null,"completeTo":null}`
- `{"copyId":"bffa5f63-efe7-3a63-ba01-845fc1b993f6","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bffa5f63-efe7-3a63-ba01-845fc1b993f6","source":"LOANS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"bffa5f63-efe7-3a63-ba01-845fc1b993f6","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**
- `{"noteId":"8b097fd4-555f-3980-8c9d-dfb928691c91","copyId":"bffa5f63-efe7-3a63-ba01-845fc1b993f6","text":"Le passage au rayon n'a pas permis de retrouver cet exemplaire ; contrôle complémentaire demandé.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"bffa5f63-efe7-3a63-ba01-845fc1b993f6"},{"kind":"sourceEvent","id":"81ee5957-4d47-314a-bdc5-25dbab56e888"},{"kind":"sourceEvent","id":"f81322e1-6c06-38e0-b93d-b1c9e823b1fc"},{"kind":"sourceEvent","id":"93e5d25e-e914-3b42-ab63-2535aa248222"},{"kind":"sourceEvent","id":"70370ec5-16a9-3f9a-b6e2-ae95714e764c"},{"kind":"sourceEvent","id":"617f0744-abd1-3a7b-996d-568853c2b34f"},{"kind":"sourceEvent","id":"85e83c12-8627-3055-a031-d65996522af0"},{"kind":"sourceEvent","id":"0e9f389c-378b-33c4-9a11-0b141d93633d"},{"kind":"sourceAvailability","id":"bffa5f63-efe7-3a63-ba01-845fc1b993f6/LOANS"},{"kind":"sourceAvailability","id":"bffa5f63-efe7-3a63-ba01-845fc1b993f6/RETURNS"},{"kind":"sourceAvailability","id":"bffa5f63-efe7-3a63-ba01-845fc1b993f6/RECEIPTS"},{"kind":"sourceAvailability","id":"bffa5f63-efe7-3a63-ba01-845fc1b993f6/TRANSFERS"},{"kind":"sourceAvailability","id":"bffa5f63-efe7-3a63-ba01-845fc1b993f6/REPAIRS"},{"kind":"sourceAvailability","id":"bffa5f63-efe7-3a63-ba01-845fc1b993f6/INTEGRATIONS"},{"kind":"sourceAvailability","id":"bffa5f63-efe7-3a63-ba01-845fc1b993f6/INVENTORY"},{"kind":"operatorNote","id":"8b097fd4-555f-3980-8c9d-dfb928691c91"},{"kind":"inventoryScope","id":"294f8877-9130-3961-8435-48be641fa20c"},{"kind":"inventoryScope","id":"9d6c09df-1bb9-3fa2-927d-b44bcfe9e1be"},{"kind":"inventoryScope","id":"8ba7cc3d-833a-342a-bcaa-e1d23f56a4e3"},{"kind":"inventoryScope","id":"59269371-fb13-3ca1-a62a-01272d903229"},{"kind":"inventoryScope","id":"59269371-fb13-3ca1-a62a-01272d903229"}]`

- Session : `{"scopeId":"294f8877-9130-3961-8435-48be641fa20c","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9d6c09df-1bb9-3fa2-927d-b44bcfe9e1be","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"8ba7cc3d-833a-342a-bcaa-e1d23f56a4e3","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"59269371-fb13-3ca1-a62a-01272d903229","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"59269371-fb13-3ca1-a62a-01272d903229","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 0f8b43bd-a2d7-3090-83ca-149026532480

- Exemplaire : `b9948965-5331-38cf-895d-b89c2b328e92`
- Famille privée : REPARATION_NON_PROJETEE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"aa14cfc3-e888-317d-8025-296f9756ba36","kind":"STATUS_MISMATCH","expectedValue":"ON_SHELF","observedValue":"IN_REPAIR"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"6960c6ba-659f-32b7-944c-b887073b0bc3","physicalStatus":"IN_REPAIR","physicalSiteId":"NORD","physicalLocationId":"11044fb8-eb45-33d3-90df-81754bc299a0","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"b9948965-5331-38cf-895d-b89c2b328e92","status":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","sourceEventIds":["35545c9b-e86a-3dbb-8ff3-533d4e9aa273","9e0602f6-58e9-36ad-92c7-17e38014e4cb"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 5abd3f19-9ada-370b-b8a7-8c0edfd9748f | 2026-06-02T08:01:22Z | 2026-06-02T08:01:32Z | `{"operationId":"76fd9654-37af-3b94-9cfe-14b0d6d42c02","correlationId":"07b3f048-7792-3e54-ae3e-da133b4db0df","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"77d93798-2bf5-3eed-aaf2-2d13b6060035"}` |
| IntegrationProcessed | bb410d75-2776-3f56-9ba7-2a951d1dc5d4 | 2026-06-03T08:01:22Z | 2026-06-03T08:01:32Z | `{"originalEventId":"5abd3f19-9ada-370b-b8a7-8c0edfd9748f","correlationId":"07b3f048-7792-3e54-ae3e-da133b4db0df","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | e7aafd73-69dd-3f1b-97dc-43ef3b65e5a4 | 2026-08-01T08:01:22Z | 2026-08-01T08:01:32Z | `{"operationId":"a3e219c6-89e0-308b-aa79-37b619344f3f","correlationId":"44c27049-d586-3801-807f-fb7465c7f771","siteId":"CENTRE","locationId":null,"loanId":"12f84e19-acb2-34f9-8fb5-416292037386"}` |
| IntegrationProcessed | 43346c66-9ef3-32f2-9265-0e9bc3817ff5 | 2026-08-01T08:02:22Z | 2026-08-01T08:02:32Z | `{"originalEventId":"e7aafd73-69dd-3f1b-97dc-43ef3b65e5a4","correlationId":"44c27049-d586-3801-807f-fb7465c7f771","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | e60ef0c6-3a42-365d-b2db-f65bce83bbad | 2026-08-21T08:01:22Z | 2026-08-21T08:01:32Z | `{"operationId":"960ccc01-8da8-3194-8bb1-6a28efeb463c","correlationId":"f3d77ccd-bc4d-3555-8873-6394b86788cd","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"12f84e19-acb2-34f9-8fb5-416292037386"}` |
| ReturnReceived | 0a71667b-1633-31c7-ae08-5c58db48d983 | 2026-08-21T08:01:52Z | 2026-08-21T08:02:02Z | `{"operationId":"4f6a5053-9874-3c8f-ac8b-f992142a9c48","correlationId":"ccc3630e-ab03-32a2-9f62-21a13255a4c3","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"12f84e19-acb2-34f9-8fb5-416292037386"}` |
| IntegrationProcessed | 05a83619-69d4-38fc-88b6-8680db6f993c | 2026-08-22T08:01:22Z | 2026-08-22T08:01:32Z | `{"originalEventId":"0a71667b-1633-31c7-ae08-5c58db48d983","correlationId":"ccc3630e-ab03-32a2-9f62-21a13255a4c3","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| TransferDispatched | bb844452-d5f2-3e6c-87f6-1725af6899cf | 2026-08-31T08:01:22Z | 2026-08-31T08:01:32Z | `{"operationId":"315f8d43-acf8-33d0-ab3d-0097dd313484","correlationId":"226fd69a-886b-3aa7-8c42-94e1da2169f4","siteId":"CENTRE","locationId":null,"transferId":"f96f00d5-b449-3ce2-a0b8-f5c50f2cbdf6","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | f957d8bc-804e-3611-8064-e731fc9b592f | 2026-08-31T08:02:22Z | 2026-08-31T08:02:32Z | `{"originalEventId":"bb844452-d5f2-3e6c-87f6-1725af6899cf","correlationId":"226fd69a-886b-3aa7-8c42-94e1da2169f4","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| TransferReceived | 35545c9b-e86a-3dbb-8ff3-533d4e9aa273 | 2026-09-01T08:01:22Z | 2026-09-01T08:01:32Z | `{"operationId":"bef81c5e-90d8-38c8-bb53-d58c31dcb408","correlationId":"9271f9a3-81c1-310f-b11a-243b57f16b23","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","transferId":"f96f00d5-b449-3ce2-a0b8-f5c50f2cbdf6","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | 9e0602f6-58e9-36ad-92c7-17e38014e4cb | 2026-09-02T08:01:22Z | 2026-09-02T08:01:32Z | `{"originalEventId":"35545c9b-e86a-3dbb-8ff3-533d4e9aa273","correlationId":"9271f9a3-81c1-310f-b11a-243b57f16b23","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| RepairStarted | 6960c6ba-659f-32b7-944c-b887073b0bc3 | 2026-09-27T10:48:24Z | 2026-09-27T10:48:34Z | `{"operationId":"4ce3cb75-2ffd-3bf0-988f-55641287f98a","correlationId":"4dbd078c-0b4e-307a-9456-52144e08c0a4","siteId":"NORD","locationId":"11044fb8-eb45-33d3-90df-81754bc299a0","repairId":"1f9cbb5b-3cd1-31b5-a913-9c270aca6915"}` |
| IntegrationProcessed | 33eb06e7-1348-30e9-bd8a-5d5f9df8726a | 2026-09-27T10:48:54Z | 2026-09-27T10:49:04Z | `{"originalEventId":"6960c6ba-659f-32b7-944c-b887073b0bc3","correlationId":"4dbd078c-0b4e-307a-9456-52144e08c0a4","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"NORD","locationId":"11044fb8-eb45-33d3-90df-81754bc299a0","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**observations**
- `{"observationId":"b97f44cc-52dd-394d-83c7-1db8b38a20a5","copyId":"b9948965-5331-38cf-895d-b89c2b328e92","locationId":"11044fb8-eb45-33d3-90df-81754bc299a0","scopeId":"9c8fd8d3-68e5-33e3-b739-628e602f488d","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"b9948965-5331-38cf-895d-b89c2b328e92","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b9948965-5331-38cf-895d-b89c2b328e92","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b9948965-5331-38cf-895d-b89c2b328e92","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b9948965-5331-38cf-895d-b89c2b328e92","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b9948965-5331-38cf-895d-b89c2b328e92","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b9948965-5331-38cf-895d-b89c2b328e92","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b9948965-5331-38cf-895d-b89c2b328e92","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"b9948965-5331-38cf-895d-b89c2b328e92"},{"kind":"sourceEvent","id":"5abd3f19-9ada-370b-b8a7-8c0edfd9748f"},{"kind":"sourceEvent","id":"bb410d75-2776-3f56-9ba7-2a951d1dc5d4"},{"kind":"sourceEvent","id":"e7aafd73-69dd-3f1b-97dc-43ef3b65e5a4"},{"kind":"sourceEvent","id":"43346c66-9ef3-32f2-9265-0e9bc3817ff5"},{"kind":"sourceEvent","id":"e60ef0c6-3a42-365d-b2db-f65bce83bbad"},{"kind":"sourceEvent","id":"0a71667b-1633-31c7-ae08-5c58db48d983"},{"kind":"sourceEvent","id":"05a83619-69d4-38fc-88b6-8680db6f993c"},{"kind":"sourceEvent","id":"bb844452-d5f2-3e6c-87f6-1725af6899cf"},{"kind":"sourceEvent","id":"f957d8bc-804e-3611-8064-e731fc9b592f"},{"kind":"sourceEvent","id":"35545c9b-e86a-3dbb-8ff3-533d4e9aa273"},{"kind":"sourceEvent","id":"9e0602f6-58e9-36ad-92c7-17e38014e4cb"},{"kind":"sourceEvent","id":"6960c6ba-659f-32b7-944c-b887073b0bc3"},{"kind":"sourceEvent","id":"33eb06e7-1348-30e9-bd8a-5d5f9df8726a"},{"kind":"observation","id":"b97f44cc-52dd-394d-83c7-1db8b38a20a5"},{"kind":"inventoryScope","id":"9c8fd8d3-68e5-33e3-b739-628e602f488d"},{"kind":"sourceAvailability","id":"b9948965-5331-38cf-895d-b89c2b328e92/LOANS"},{"kind":"sourceAvailability","id":"b9948965-5331-38cf-895d-b89c2b328e92/RETURNS"},{"kind":"sourceAvailability","id":"b9948965-5331-38cf-895d-b89c2b328e92/RECEIPTS"},{"kind":"sourceAvailability","id":"b9948965-5331-38cf-895d-b89c2b328e92/TRANSFERS"},{"kind":"sourceAvailability","id":"b9948965-5331-38cf-895d-b89c2b328e92/REPAIRS"},{"kind":"sourceAvailability","id":"b9948965-5331-38cf-895d-b89c2b328e92/INTEGRATIONS"},{"kind":"sourceAvailability","id":"b9948965-5331-38cf-895d-b89c2b328e92/INVENTORY"},{"kind":"inventoryScope","id":"07c957d2-b086-3b6b-9915-d21f1d5906cf"}]`

- Session : `{"scopeId":"9c8fd8d3-68e5-33e3-b739-628e602f488d","locationId":"11044fb8-eb45-33d3-90df-81754bc299a0","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"07c957d2-b086-3b6b-9915-d21f1d5906cf","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 1090fe5e-50a6-33df-81e8-4c797a60606e

- Exemplaire : `4f48eb42-d078-3598-a347-954c817176b8`
- Famille privée : RETARD_INTEGRATION ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"7a35b00d-cf59-3f3b-9327-cba9285cb705","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["QUEUE_PENDING"],"hiddenSourceEvents":[{"sourceEventId":"8f6d482d-5710-39da-80c7-f5af79fb034a","copyId":"4f48eb42-d078-3598-a347-954c817176b8","eventType":"IntegrationProcessed","occurredAt":"2026-09-30T11:51:19Z","recordedAt":"2026-09-30T11:51:29Z","payload":{"originalEventId":"179d34e6-f763-3c9e-b95d-f767f661f042","correlationId":"eb228a4b-e209-3de1-bb0a-fa9a43d6fe01","outcome":"QUEUED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1}}],"mechanism":"Événement métier en attente normale dans la fenêtre contractuelle de 900 secondes.","businessEventId":"179d34e6-f763-3c9e-b95d-f767f661f042","physicalStatus":"ON_LOAN","physicalSiteId":"NORD","physicalLocationId":null,"projectionPerturbation":"QUEUED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État projeté : `{"copyId":"4f48eb42-d078-3598-a347-954c817176b8","status":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","sourceEventIds":["521ff760-628a-3098-ab0e-feb80c7cfd4a","3ba93390-befe-3bb6-adbb-ed1499b7edfc"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 3479735d-2f23-38c2-9f49-ade170fd2a11 | 2026-06-02T11:01:21Z | 2026-06-02T11:01:31Z | `{"operationId":"205b21fa-5498-36aa-b0e2-f75807ec9fa9","correlationId":"1de2bf37-e260-33ef-bb93-951021cc7bc6","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"5cf9196b-2239-301e-b68a-93421ed662ce"}` |
| IntegrationProcessed | e026bf0a-db9e-362d-a704-6cd77a11da8b | 2026-06-03T11:01:21Z | 2026-06-03T11:01:31Z | `{"originalEventId":"3479735d-2f23-38c2-9f49-ade170fd2a11","correlationId":"1de2bf37-e260-33ef-bb93-951021cc7bc6","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 02dd41bf-b4cb-3d72-a072-168b2946fcfd | 2026-08-01T11:01:21Z | 2026-08-01T11:01:31Z | `{"operationId":"472247cf-855e-3799-b290-a6a1a7846139","correlationId":"7efe4d30-78dc-314a-9af8-43fdb8f68356","siteId":"NORD","locationId":null,"loanId":"5c4ddd13-b8df-3078-831b-e5faddda7571"}` |
| IntegrationProcessed | 845fadea-579e-39d0-b7df-218e612b223e | 2026-08-01T11:02:21Z | 2026-08-01T11:02:31Z | `{"originalEventId":"02dd41bf-b4cb-3d72-a072-168b2946fcfd","correlationId":"7efe4d30-78dc-314a-9af8-43fdb8f68356","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | 16fae92e-7f62-3b6f-a86f-7d28b67ba281 | 2026-08-21T11:01:21Z | 2026-08-21T11:01:31Z | `{"operationId":"ca30d374-7507-35e5-9a86-1e0ee607ce07","correlationId":"13a3f3ef-5bcc-3310-8f03-36cd69035e95","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"5c4ddd13-b8df-3078-831b-e5faddda7571"}` |
| ReturnReceived | 521ff760-628a-3098-ab0e-feb80c7cfd4a | 2026-08-21T11:01:51Z | 2026-08-21T11:02:01Z | `{"operationId":"437cb081-c3ae-3eaa-b219-5b19e4c2d906","correlationId":"50c7919a-2a10-3b6f-a349-5e481058a1f7","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"5c4ddd13-b8df-3078-831b-e5faddda7571"}` |
| IntegrationProcessed | 3ba93390-befe-3bb6-adbb-ed1499b7edfc | 2026-08-22T11:01:21Z | 2026-08-22T11:01:31Z | `{"originalEventId":"521ff760-628a-3098-ab0e-feb80c7cfd4a","correlationId":"50c7919a-2a10-3b6f-a349-5e481058a1f7","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 179d34e6-f763-3c9e-b95d-f767f661f042 | 2026-09-30T11:50:49Z | 2026-09-30T11:50:59Z | `{"operationId":"384cb957-072d-3a88-8827-fff599b67215","correlationId":"eb228a4b-e209-3de1-bb0a-fa9a43d6fe01","siteId":"NORD","locationId":null,"loanId":"0d640691-3340-3478-a9bc-a5a3d7656ea2"}` |

**observations**

**sourceAvailability**
- `{"copyId":"4f48eb42-d078-3598-a347-954c817176b8","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4f48eb42-d078-3598-a347-954c817176b8","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4f48eb42-d078-3598-a347-954c817176b8","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4f48eb42-d078-3598-a347-954c817176b8","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4f48eb42-d078-3598-a347-954c817176b8","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4f48eb42-d078-3598-a347-954c817176b8","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4f48eb42-d078-3598-a347-954c817176b8","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"4f48eb42-d078-3598-a347-954c817176b8"},{"kind":"sourceEvent","id":"3479735d-2f23-38c2-9f49-ade170fd2a11"},{"kind":"sourceEvent","id":"e026bf0a-db9e-362d-a704-6cd77a11da8b"},{"kind":"sourceEvent","id":"02dd41bf-b4cb-3d72-a072-168b2946fcfd"},{"kind":"sourceEvent","id":"845fadea-579e-39d0-b7df-218e612b223e"},{"kind":"sourceEvent","id":"16fae92e-7f62-3b6f-a86f-7d28b67ba281"},{"kind":"sourceEvent","id":"521ff760-628a-3098-ab0e-feb80c7cfd4a"},{"kind":"sourceEvent","id":"3ba93390-befe-3bb6-adbb-ed1499b7edfc"},{"kind":"sourceEvent","id":"179d34e6-f763-3c9e-b95d-f767f661f042"},{"kind":"sourceAvailability","id":"4f48eb42-d078-3598-a347-954c817176b8/LOANS"},{"kind":"sourceAvailability","id":"4f48eb42-d078-3598-a347-954c817176b8/RETURNS"},{"kind":"sourceAvailability","id":"4f48eb42-d078-3598-a347-954c817176b8/RECEIPTS"},{"kind":"sourceAvailability","id":"4f48eb42-d078-3598-a347-954c817176b8/TRANSFERS"},{"kind":"sourceAvailability","id":"4f48eb42-d078-3598-a347-954c817176b8/REPAIRS"},{"kind":"sourceAvailability","id":"4f48eb42-d078-3598-a347-954c817176b8/INTEGRATIONS"},{"kind":"sourceAvailability","id":"4f48eb42-d078-3598-a347-954c817176b8/INVENTORY"},{"kind":"inventoryScope","id":"07c957d2-b086-3b6b-9915-d21f1d5906cf"},{"kind":"inventoryScope","id":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c"},{"kind":"inventoryScope","id":"a481af0e-6045-37c4-bb84-d5269f5addc5"},{"kind":"inventoryScope","id":"9880a39c-a8f4-3f61-a473-804a0fd611a5"},{"kind":"inventoryScope","id":"79157bb9-b809-3388-ad1a-42d980616ea0"}]`

- Session : `{"scopeId":"07c957d2-b086-3b6b-9915-d21f1d5906cf","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"a481af0e-6045-37c4-bb84-d5269f5addc5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9880a39c-a8f4-3f61-a473-804a0fd611a5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"79157bb9-b809-3388-ad1a-42d980616ea0","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 12891625-3d41-3a2b-a083-1493770918e8

- Exemplaire : `1c5b265d-b374-3068-8609-57b8ffa0943f`
- Famille privée : TRANSFERT_INCOHERENT ; variante : POSITIVE
- Symptômes : `[{"symptomId":"87571e16-ed3e-31fd-be7e-d9669daf48ea","kind":"STATUS_MISMATCH","expectedValue":"IN_TRANSIT","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"21e42ff4-4fc2-3dbc-a7c2-f140953d1b79","physicalStatus":"RECEIVING","physicalSiteId":"SUD","physicalLocationId":"8228c3af-c19d-30d4-95b5-30240d9818af","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["TRANSFER_RECEIPT_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"1c5b265d-b374-3068-8609-57b8ffa0943f","status":"IN_TRANSIT","siteId":"NORD","locationId":null,"sourceEventIds":["8aa5c5eb-8916-370d-a872-0bd09933024c","18c558a2-fcd9-3ac2-b079-31a5ef09bc2a"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 7fc4498a-3054-3bd5-8bf4-557b7d5e720f | 2026-06-01T15:58:23Z | 2026-06-01T15:58:33Z | `{"operationId":"fec70890-9538-3561-9643-96a32be8a84f","correlationId":"5f25aff6-eb6e-3f7f-9ca8-ccdfbf240fef","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"66b94719-3d82-31c7-acee-09a7388d4f65"}` |
| IntegrationProcessed | 7805fc70-1568-36f3-9784-1800016729de | 2026-06-02T15:58:23Z | 2026-06-02T15:58:33Z | `{"originalEventId":"7fc4498a-3054-3bd5-8bf4-557b7d5e720f","correlationId":"5f25aff6-eb6e-3f7f-9ca8-ccdfbf240fef","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | ad203145-64a3-3a9d-a377-a0b39504d156 | 2026-07-31T15:58:23Z | 2026-07-31T15:58:33Z | `{"operationId":"5390cc73-ed7d-3816-ae65-858da68112d5","correlationId":"d7ab4983-0b2b-3e0d-bcdb-143f0adcf199","siteId":"NORD","locationId":null,"loanId":"eb5c4af7-8584-33a7-bfa7-a297f6f00ef1"}` |
| IntegrationProcessed | 71a7169a-edb2-3bcd-928d-212fdd7da5ab | 2026-07-31T15:59:23Z | 2026-07-31T15:59:33Z | `{"originalEventId":"ad203145-64a3-3a9d-a377-a0b39504d156","correlationId":"d7ab4983-0b2b-3e0d-bcdb-143f0adcf199","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | 1c46df0f-3276-3435-bd5d-3e7d4e40cb55 | 2026-08-20T15:58:23Z | 2026-08-20T15:58:33Z | `{"operationId":"45d73c0c-283d-3db7-9114-70f5b64ccc71","correlationId":"187f3b72-c303-3a0c-94e2-4a9eeedb9b9d","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"eb5c4af7-8584-33a7-bfa7-a297f6f00ef1"}` |
| ReturnReceived | 1914f94b-28b4-3053-8f6b-425774492c08 | 2026-08-20T15:58:53Z | 2026-08-20T15:59:03Z | `{"operationId":"f1b58f62-756a-36c1-85b6-d596a9429e6a","correlationId":"8c489255-bc74-3588-800e-c47cfd305008","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"eb5c4af7-8584-33a7-bfa7-a297f6f00ef1"}` |
| IntegrationProcessed | 2e946724-73a7-36c9-a96a-1ddf94411c5b | 2026-08-21T15:58:23Z | 2026-08-21T15:58:33Z | `{"originalEventId":"1914f94b-28b4-3053-8f6b-425774492c08","correlationId":"8c489255-bc74-3588-800e-c47cfd305008","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| TransferDispatched | 8aa5c5eb-8916-370d-a872-0bd09933024c | 2026-09-26T06:08:43Z | 2026-09-26T06:08:53Z | `{"operationId":"b9401f78-2e1e-336e-a4bd-7d76a9d7bdad","correlationId":"d4e8907c-751b-377f-92bb-424e3f8fcafc","siteId":"NORD","locationId":null,"transferId":"2128cb84-a65f-31e4-8b52-4c6c96ed8d11","fromSiteId":"NORD","toSiteId":"SUD"}` |
| IntegrationProcessed | 18c558a2-fcd9-3ac2-b079-31a5ef09bc2a | 2026-09-26T06:09:43Z | 2026-09-26T06:09:53Z | `{"originalEventId":"8aa5c5eb-8916-370d-a872-0bd09933024c","correlationId":"d4e8907c-751b-377f-92bb-424e3f8fcafc","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"NORD","locationId":null}` |
| TransferReceived | 21e42ff4-4fc2-3dbc-a7c2-f140953d1b79 | 2026-09-27T06:08:43Z | 2026-09-27T06:08:53Z | `{"operationId":"19a25e5c-1bc0-374b-8534-a46f42ed5d09","correlationId":"b89138ea-3be5-32b0-91d0-583c5d4d0e8a","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","transferId":"2128cb84-a65f-31e4-8b52-4c6c96ed8d11","fromSiteId":"NORD","toSiteId":"SUD"}` |
| IntegrationProcessed | 953ab530-535d-38f3-87d6-a90ae5bf834e | 2026-09-27T06:09:13Z | 2026-09-27T06:09:23Z | `{"originalEventId":"21e42ff4-4fc2-3dbc-a7c2-f140953d1b79","correlationId":"b89138ea-3be5-32b0-91d0-583c5d4d0e8a","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**observations**
- `{"observationId":"7257256f-68e0-3c5c-bd12-bbc5971f9b0f","copyId":"1c5b265d-b374-3068-8609-57b8ffa0943f","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","scopeId":"535ec227-65d3-31f0-a1b4-5c96e8deba81","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"1c5b265d-b374-3068-8609-57b8ffa0943f","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1c5b265d-b374-3068-8609-57b8ffa0943f","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1c5b265d-b374-3068-8609-57b8ffa0943f","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1c5b265d-b374-3068-8609-57b8ffa0943f","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1c5b265d-b374-3068-8609-57b8ffa0943f","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1c5b265d-b374-3068-8609-57b8ffa0943f","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1c5b265d-b374-3068-8609-57b8ffa0943f","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"1c5b265d-b374-3068-8609-57b8ffa0943f"},{"kind":"sourceEvent","id":"7fc4498a-3054-3bd5-8bf4-557b7d5e720f"},{"kind":"sourceEvent","id":"7805fc70-1568-36f3-9784-1800016729de"},{"kind":"sourceEvent","id":"ad203145-64a3-3a9d-a377-a0b39504d156"},{"kind":"sourceEvent","id":"71a7169a-edb2-3bcd-928d-212fdd7da5ab"},{"kind":"sourceEvent","id":"1c46df0f-3276-3435-bd5d-3e7d4e40cb55"},{"kind":"sourceEvent","id":"1914f94b-28b4-3053-8f6b-425774492c08"},{"kind":"sourceEvent","id":"2e946724-73a7-36c9-a96a-1ddf94411c5b"},{"kind":"sourceEvent","id":"8aa5c5eb-8916-370d-a872-0bd09933024c"},{"kind":"sourceEvent","id":"18c558a2-fcd9-3ac2-b079-31a5ef09bc2a"},{"kind":"sourceEvent","id":"21e42ff4-4fc2-3dbc-a7c2-f140953d1b79"},{"kind":"sourceEvent","id":"953ab530-535d-38f3-87d6-a90ae5bf834e"},{"kind":"observation","id":"7257256f-68e0-3c5c-bd12-bbc5971f9b0f"},{"kind":"inventoryScope","id":"535ec227-65d3-31f0-a1b4-5c96e8deba81"},{"kind":"sourceAvailability","id":"1c5b265d-b374-3068-8609-57b8ffa0943f/LOANS"},{"kind":"sourceAvailability","id":"1c5b265d-b374-3068-8609-57b8ffa0943f/RETURNS"},{"kind":"sourceAvailability","id":"1c5b265d-b374-3068-8609-57b8ffa0943f/RECEIPTS"},{"kind":"sourceAvailability","id":"1c5b265d-b374-3068-8609-57b8ffa0943f/TRANSFERS"},{"kind":"sourceAvailability","id":"1c5b265d-b374-3068-8609-57b8ffa0943f/REPAIRS"},{"kind":"sourceAvailability","id":"1c5b265d-b374-3068-8609-57b8ffa0943f/INTEGRATIONS"},{"kind":"sourceAvailability","id":"1c5b265d-b374-3068-8609-57b8ffa0943f/INVENTORY"}]`

- Session : `{"scopeId":"535ec227-65d3-31f0-a1b4-5c96e8deba81","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 1388d32b-d1a3-32d9-9402-12720441a35e

- Exemplaire : `b56c1c39-0e08-3b69-9215-3c9a3d154917`
- Famille privée : RETARD_INTEGRATION ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"d768e73b-ef44-38b9-b44c-480f1f5920e8","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"52bd250c-82b9-3597-a139-fbdf25fe9387","physicalStatus":"ON_LOAN","physicalSiteId":"CENTRE","physicalLocationId":null,"projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Recontrôler après la fenêtre de traitement"]}`
- État projeté : `{"copyId":"b56c1c39-0e08-3b69-9215-3c9a3d154917","status":"ON_LOAN","siteId":"CENTRE","locationId":null,"sourceEventIds":["52bd250c-82b9-3597-a139-fbdf25fe9387","6c230d74-d945-37e9-8009-e9e4c572ac0b"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | b3cdd556-2479-3b4b-bddd-61b15d80207f | 2026-06-02T10:50:45Z | 2026-06-02T10:50:55Z | `{"operationId":"e816878f-ccb0-3b24-b2bb-e6d773042e00","correlationId":"df9d0ecb-e576-3c80-b8c0-1af0213dc88f","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"565160f9-9d20-3946-b7de-0b10a7ec9a3d"}` |
| IntegrationProcessed | 30a3f6c4-fd77-3671-b5a1-7002528955be | 2026-06-03T10:50:45Z | 2026-06-03T10:50:55Z | `{"originalEventId":"b3cdd556-2479-3b4b-bddd-61b15d80207f","correlationId":"df9d0ecb-e576-3c80-b8c0-1af0213dc88f","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 29a4a244-ba5b-3a49-a444-83f457d2592c | 2026-08-01T10:50:45Z | 2026-08-01T10:50:55Z | `{"operationId":"76746192-c500-3e58-961a-32743a9e1e5a","correlationId":"0508d6f7-34ee-3d78-8757-d93c28c4f9d5","siteId":"CENTRE","locationId":null,"loanId":"be4edace-ed5e-313a-8a57-9188aaad42fc"}` |
| IntegrationProcessed | 774cd990-a880-33c8-acf8-14e17052db64 | 2026-08-01T10:51:45Z | 2026-08-01T10:51:55Z | `{"originalEventId":"29a4a244-ba5b-3a49-a444-83f457d2592c","correlationId":"0508d6f7-34ee-3d78-8757-d93c28c4f9d5","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | be5fd85a-8d02-3dbf-b126-3223d870e27e | 2026-08-21T10:50:45Z | 2026-08-21T10:50:55Z | `{"operationId":"a6b56634-21bd-34d1-8f4f-593b2d00c3a9","correlationId":"9d6e802a-7fae-3225-ba67-26b43f9c032b","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"be4edace-ed5e-313a-8a57-9188aaad42fc"}` |
| ReturnReceived | 2c3647d5-6c5e-34c3-9db5-e2b963e25af0 | 2026-08-21T10:51:15Z | 2026-08-21T10:51:25Z | `{"operationId":"446719f8-74b7-335a-9112-d10ad9e129a5","correlationId":"955af9db-41da-3632-9b00-9e855a3efd9f","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"be4edace-ed5e-313a-8a57-9188aaad42fc"}` |
| IntegrationProcessed | 642a85d3-c83f-3291-b2dd-0d326a999228 | 2026-08-22T10:50:45Z | 2026-08-22T10:50:55Z | `{"originalEventId":"2c3647d5-6c5e-34c3-9db5-e2b963e25af0","correlationId":"955af9db-41da-3632-9b00-9e855a3efd9f","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 52bd250c-82b9-3597-a139-fbdf25fe9387 | 2026-09-30T11:53:35Z | 2026-09-30T11:53:45Z | `{"operationId":"c52ef33e-40f4-3763-bdae-6bc150087fdd","correlationId":"ab41f0b2-e95f-3e11-a8c9-38c46f5c76b9","siteId":"CENTRE","locationId":null,"loanId":"2a847f11-1538-3c22-bba0-06e616040c27"}` |
| IntegrationProcessed | aa2132ef-8558-3feb-95dd-1ea8296f8eab | 2026-09-30T11:54:05Z | 2026-09-30T11:54:15Z | `{"originalEventId":"52bd250c-82b9-3597-a139-fbdf25fe9387","correlationId":"ab41f0b2-e95f-3e11-a8c9-38c46f5c76b9","outcome":"QUEUED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1}` |
| IntegrationProcessed | 6c230d74-d945-37e9-8009-e9e4c572ac0b | 2026-09-30T11:55:05Z | 2026-09-30T11:55:15Z | `{"originalEventId":"52bd250c-82b9-3597-a139-fbdf25fe9387","correlationId":"ab41f0b2-e95f-3e11-a8c9-38c46f5c76b9","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |

**observations**
- `{"observationId":"20128bf4-21e9-3d53-b9b6-ea92955154ca","copyId":"b56c1c39-0e08-3b69-9215-3c9a3d154917","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","scopeId":"83082bac-e596-397a-892e-cfc107653a39","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**sourceAvailability**
- `{"copyId":"b56c1c39-0e08-3b69-9215-3c9a3d154917","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b56c1c39-0e08-3b69-9215-3c9a3d154917","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b56c1c39-0e08-3b69-9215-3c9a3d154917","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b56c1c39-0e08-3b69-9215-3c9a3d154917","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b56c1c39-0e08-3b69-9215-3c9a3d154917","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b56c1c39-0e08-3b69-9215-3c9a3d154917","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"b56c1c39-0e08-3b69-9215-3c9a3d154917","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"b56c1c39-0e08-3b69-9215-3c9a3d154917"},{"kind":"sourceEvent","id":"b3cdd556-2479-3b4b-bddd-61b15d80207f"},{"kind":"sourceEvent","id":"30a3f6c4-fd77-3671-b5a1-7002528955be"},{"kind":"sourceEvent","id":"29a4a244-ba5b-3a49-a444-83f457d2592c"},{"kind":"sourceEvent","id":"774cd990-a880-33c8-acf8-14e17052db64"},{"kind":"sourceEvent","id":"be5fd85a-8d02-3dbf-b126-3223d870e27e"},{"kind":"sourceEvent","id":"2c3647d5-6c5e-34c3-9db5-e2b963e25af0"},{"kind":"sourceEvent","id":"642a85d3-c83f-3291-b2dd-0d326a999228"},{"kind":"sourceEvent","id":"52bd250c-82b9-3597-a139-fbdf25fe9387"},{"kind":"sourceEvent","id":"aa2132ef-8558-3feb-95dd-1ea8296f8eab"},{"kind":"sourceEvent","id":"6c230d74-d945-37e9-8009-e9e4c572ac0b"},{"kind":"observation","id":"20128bf4-21e9-3d53-b9b6-ea92955154ca"},{"kind":"inventoryScope","id":"83082bac-e596-397a-892e-cfc107653a39"},{"kind":"sourceAvailability","id":"b56c1c39-0e08-3b69-9215-3c9a3d154917/LOANS"},{"kind":"sourceAvailability","id":"b56c1c39-0e08-3b69-9215-3c9a3d154917/RETURNS"},{"kind":"sourceAvailability","id":"b56c1c39-0e08-3b69-9215-3c9a3d154917/RECEIPTS"},{"kind":"sourceAvailability","id":"b56c1c39-0e08-3b69-9215-3c9a3d154917/TRANSFERS"},{"kind":"sourceAvailability","id":"b56c1c39-0e08-3b69-9215-3c9a3d154917/REPAIRS"},{"kind":"sourceAvailability","id":"b56c1c39-0e08-3b69-9215-3c9a3d154917/INTEGRATIONS"},{"kind":"sourceAvailability","id":"b56c1c39-0e08-3b69-9215-3c9a3d154917/INVENTORY"}]`

- Session : `{"scopeId":"83082bac-e596-397a-892e-cfc107653a39","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

## Cas 15c7b4be-d66b-3225-b091-9cce52c850ea

- Exemplaire : `75daa545-e4fb-3ea8-9757-add5748bbe9d`
- Famille privée : RETOUR_NON_PROJETE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"d221b7d8-5212-39d5-bb83-9f3c3dbfe45d","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"6a28cf2a-3b32-3b70-b101-9f09dc75571e","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"75daa545-e4fb-3ea8-9757-add5748bbe9d","status":"RECEIVING","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","sourceEventIds":["6a28cf2a-3b32-3b70-b101-9f09dc75571e","83a4d7cd-95f9-3296-862e-b0614d70dcda"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 7d9b3834-0710-3e53-9c52-ecc66d698e27 | 2026-06-02T10:04:36Z | 2026-06-02T10:04:46Z | `{"operationId":"e14fdbb0-40b7-3b45-a7b7-251c189af57b","correlationId":"11620d09-d752-3629-9599-794cf1fd0289","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"ef0fc092-0034-37c9-b883-2408bc706af5"}` |
| IntegrationProcessed | 2af87d87-d72e-3f88-95cf-4eb52c2a9c91 | 2026-06-03T10:04:36Z | 2026-06-03T10:04:46Z | `{"originalEventId":"7d9b3834-0710-3e53-9c52-ecc66d698e27","correlationId":"11620d09-d752-3629-9599-794cf1fd0289","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | c9862ab1-f2c5-368f-a035-bbf234b1ceb8 | 2026-08-01T10:04:36Z | 2026-08-01T10:04:46Z | `{"operationId":"30a46176-eb75-30f6-98a5-a57bab43e306","correlationId":"83512120-33ae-3792-99eb-a94ef5c0746a","siteId":"CENTRE","locationId":null,"loanId":"a047c669-a3c2-3745-9fee-7bcee2ce4a54"}` |
| IntegrationProcessed | 1d807b97-f84c-39e1-ab45-aa4bc2246b17 | 2026-08-01T10:05:36Z | 2026-08-01T10:05:46Z | `{"originalEventId":"c9862ab1-f2c5-368f-a035-bbf234b1ceb8","correlationId":"83512120-33ae-3792-99eb-a94ef5c0746a","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | fa71208c-c28f-3771-adf0-9a7e155ce0f4 | 2026-08-21T10:04:36Z | 2026-08-21T10:04:46Z | `{"operationId":"52880956-b104-36e0-8801-fcaadddddb0b","correlationId":"0392b672-0b96-3d09-abbe-9bc99cde67c6","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"a047c669-a3c2-3745-9fee-7bcee2ce4a54"}` |
| ReturnReceived | 5a4e8b1e-a934-3c1b-888f-0b604ad93386 | 2026-08-21T10:05:06Z | 2026-08-21T10:05:16Z | `{"operationId":"6853184d-25e9-3a78-8d17-2e16d8965e26","correlationId":"064e4ef4-5c29-3349-b0a0-2b37eee3e32a","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"a047c669-a3c2-3745-9fee-7bcee2ce4a54"}` |
| IntegrationProcessed | 3e55a712-713e-3e2b-9783-fc1f1ce41b45 | 2026-08-22T10:04:36Z | 2026-08-22T10:04:46Z | `{"originalEventId":"5a4e8b1e-a934-3c1b-888f-0b604ad93386","correlationId":"064e4ef4-5c29-3349-b0a0-2b37eee3e32a","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 0bc693ac-0f3d-31a0-befc-0d4b848e61e8 | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"0474e1be-a1a0-331a-95dd-9e9972e135dc","correlationId":"f9706198-d229-310b-9214-d67709a5ef35","siteId":"CENTRE","locationId":null,"loanId":"6ff75cfa-5031-3662-971c-8856342f1923"}` |
| IntegrationProcessed | daa7ed66-633f-3bc8-8ccb-29078fb9884f | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"0bc693ac-0f3d-31a0-befc-0d4b848e61e8","correlationId":"f9706198-d229-310b-9214-d67709a5ef35","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | eb526551-0ea4-3600-a22d-872fabd5f6f5 | 2026-09-27T09:11:15Z | 2026-09-27T09:11:25Z | `{"operationId":"1f2b8590-f01a-3ec5-a97d-b1bae87b3b41","correlationId":"95d8e7ed-6665-35b3-b687-2c9b8260e305","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"6ff75cfa-5031-3662-971c-8856342f1923"}` |
| ReturnReceived | 6a28cf2a-3b32-3b70-b101-9f09dc75571e | 2026-09-27T09:11:45Z | 2026-09-27T09:11:55Z | `{"operationId":"cf885a67-6505-3e6c-ac5d-1258e21fd7e1","correlationId":"7ff32857-e438-3fb9-b7e5-226424dcf636","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"6ff75cfa-5031-3662-971c-8856342f1923"}` |
| IntegrationProcessed | ded07487-b9ba-3d69-8822-69421aa8d661 | 2026-09-27T09:12:15Z | 2026-09-27T09:12:25Z | `{"originalEventId":"6a28cf2a-3b32-3b70-b101-9f09dc75571e","correlationId":"7ff32857-e438-3fb9-b7e5-226424dcf636","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| IntegrationProcessed | 83a4d7cd-95f9-3296-862e-b0614d70dcda | 2026-09-27T09:13:15Z | 2026-09-27T09:13:25Z | `{"originalEventId":"6a28cf2a-3b32-3b70-b101-9f09dc75571e","correlationId":"7ff32857-e438-3fb9-b7e5-226424dcf636","outcome":"APPLIED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19"}` |

**observations**
- `{"observationId":"8f845e7f-485c-3dd1-a62b-664f0e270c9a","copyId":"75daa545-e4fb-3ea8-9757-add5748bbe9d","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","scopeId":"4100018a-c306-3d3a-ac26-da8271d29138","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`
- `{"observationId":"cd5ec605-b449-3004-9135-bf2a63f7863d","copyId":"75daa545-e4fb-3ea8-9757-add5748bbe9d","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","scopeId":"2ff98455-b198-3fa6-b5d2-0c6d431cc0c8","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**sourceAvailability**
- `{"copyId":"75daa545-e4fb-3ea8-9757-add5748bbe9d","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"75daa545-e4fb-3ea8-9757-add5748bbe9d","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"75daa545-e4fb-3ea8-9757-add5748bbe9d","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"75daa545-e4fb-3ea8-9757-add5748bbe9d","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"75daa545-e4fb-3ea8-9757-add5748bbe9d","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"75daa545-e4fb-3ea8-9757-add5748bbe9d","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"75daa545-e4fb-3ea8-9757-add5748bbe9d","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"75daa545-e4fb-3ea8-9757-add5748bbe9d"},{"kind":"sourceEvent","id":"7d9b3834-0710-3e53-9c52-ecc66d698e27"},{"kind":"sourceEvent","id":"2af87d87-d72e-3f88-95cf-4eb52c2a9c91"},{"kind":"sourceEvent","id":"c9862ab1-f2c5-368f-a035-bbf234b1ceb8"},{"kind":"sourceEvent","id":"1d807b97-f84c-39e1-ab45-aa4bc2246b17"},{"kind":"sourceEvent","id":"fa71208c-c28f-3771-adf0-9a7e155ce0f4"},{"kind":"sourceEvent","id":"5a4e8b1e-a934-3c1b-888f-0b604ad93386"},{"kind":"sourceEvent","id":"3e55a712-713e-3e2b-9783-fc1f1ce41b45"},{"kind":"sourceEvent","id":"0bc693ac-0f3d-31a0-befc-0d4b848e61e8"},{"kind":"sourceEvent","id":"daa7ed66-633f-3bc8-8ccb-29078fb9884f"},{"kind":"sourceEvent","id":"eb526551-0ea4-3600-a22d-872fabd5f6f5"},{"kind":"sourceEvent","id":"6a28cf2a-3b32-3b70-b101-9f09dc75571e"},{"kind":"sourceEvent","id":"ded07487-b9ba-3d69-8822-69421aa8d661"},{"kind":"sourceEvent","id":"83a4d7cd-95f9-3296-862e-b0614d70dcda"},{"kind":"observation","id":"8f845e7f-485c-3dd1-a62b-664f0e270c9a"},{"kind":"inventoryScope","id":"4100018a-c306-3d3a-ac26-da8271d29138"},{"kind":"observation","id":"cd5ec605-b449-3004-9135-bf2a63f7863d"},{"kind":"inventoryScope","id":"2ff98455-b198-3fa6-b5d2-0c6d431cc0c8"},{"kind":"sourceAvailability","id":"75daa545-e4fb-3ea8-9757-add5748bbe9d/LOANS"},{"kind":"sourceAvailability","id":"75daa545-e4fb-3ea8-9757-add5748bbe9d/RETURNS"},{"kind":"sourceAvailability","id":"75daa545-e4fb-3ea8-9757-add5748bbe9d/RECEIPTS"},{"kind":"sourceAvailability","id":"75daa545-e4fb-3ea8-9757-add5748bbe9d/TRANSFERS"},{"kind":"sourceAvailability","id":"75daa545-e4fb-3ea8-9757-add5748bbe9d/REPAIRS"},{"kind":"sourceAvailability","id":"75daa545-e4fb-3ea8-9757-add5748bbe9d/INTEGRATIONS"},{"kind":"sourceAvailability","id":"75daa545-e4fb-3ea8-9757-add5748bbe9d/INVENTORY"},{"kind":"inventoryScope","id":"edbecaba-d35c-3b09-998f-113009bca25d"},{"kind":"inventoryScope","id":"a8e04495-fff8-39c4-ae7d-dee65b43d714"},{"kind":"inventoryScope","id":"0a12c174-c6ce-3c90-852a-76f72a3edebf"},{"kind":"inventoryScope","id":"057c773f-512b-39ba-af02-aa5f3aa70a82"}]`

- Session : `{"scopeId":"4100018a-c306-3d3a-ac26-da8271d29138","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"2ff98455-b198-3fa6-b5d2-0c6d431cc0c8","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"edbecaba-d35c-3b09-998f-113009bca25d","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"a8e04495-fff8-39c4-ae7d-dee65b43d714","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"0a12c174-c6ce-3c90-852a-76f72a3edebf","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"057c773f-512b-39ba-af02-aa5f3aa70a82","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 16ce9ff6-3181-3802-82ff-35a8e8b748ed

- Exemplaire : `19698251-7fe7-3f01-adc3-eeac81f0b854`
- Famille privée : DEUX_CAUSES ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"144bad2c-1f75-325f-8dc5-d1cc4719fd61","kind":"STATUS_MISMATCH","expectedValue":"IN_REPAIR","observedValue":"ON_SHELF"},{"symptomId":"125a2691-e658-300f-a3f7-b4a882b944d8","kind":"LOCATION_MISMATCH","expectedValue":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","observedValue":"b8600ac8-b5d3-3580-b3c5-71a711df1a50"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED","REPAIR_LOCATION_CHANGED"],"hiddenSourceEvents":[],"mechanism":"Réparation engagée puis déplacement distinct de l'atelier vers la réserve sans mise à jour de l'affectation de réparation.","businessEventId":"0df48b48-4b30-3b58-a91a-47309395fbf5","physicalStatus":"IN_REPAIR","physicalSiteId":"CENTRE","physicalLocationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","projectionPerturbation":"FAILED","evidenceMask":"Scan de réserve ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":["REPAIR_NOT_PROJECTED"],"explanation":"Échec de projection de réparation documenté ; seconde localisation non confirmée par un scan valide.","forbiddenClaims":["Deux causes confirmées par un scan ambigu","Vol"],"recommendedChecks":["Refaire le scan en réserve"]}`
- État projeté : `{"copyId":"19698251-7fe7-3f01-adc3-eeac81f0b854","status":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","sourceEventIds":["17a3d892-0d3a-3ff9-87fd-69aa2e014f8a","0a4693c6-e7b6-3768-b02a-ead4a6462c13"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 9e0b7896-7814-3aa1-851d-8a72dda9ddfb | 2026-06-01T17:55:52Z | 2026-06-01T17:56:02Z | `{"operationId":"6da7e383-0a21-3d27-a5de-06a6823b77b9","correlationId":"1d316814-0ed6-38ac-9e70-227f22342519","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"d45a1f6a-26b8-33e7-8df7-3b0d542d2fda"}` |
| IntegrationProcessed | 85228891-e3e8-3de7-8a0d-488bbc3ceeeb | 2026-06-02T17:55:52Z | 2026-06-02T17:56:02Z | `{"originalEventId":"9e0b7896-7814-3aa1-851d-8a72dda9ddfb","correlationId":"1d316814-0ed6-38ac-9e70-227f22342519","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 212bd7fa-6a21-3d0a-8cb0-aad29ee87c64 | 2026-07-31T17:55:52Z | 2026-07-31T17:56:02Z | `{"operationId":"c10a8d1b-f9ce-3c4e-9872-7bff7e1bf646","correlationId":"550ac410-08d3-33b0-89cb-703d877b5f77","siteId":"CENTRE","locationId":null,"loanId":"0c591d8f-0026-3890-8aa0-9d3373dec80b"}` |
| IntegrationProcessed | 0f7e86cb-70c6-33b1-95b5-59f85526b95e | 2026-07-31T17:56:52Z | 2026-07-31T17:57:02Z | `{"originalEventId":"212bd7fa-6a21-3d0a-8cb0-aad29ee87c64","correlationId":"550ac410-08d3-33b0-89cb-703d877b5f77","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | ec2366bc-ca7a-3fdb-9159-e00df04ac378 | 2026-08-20T17:55:52Z | 2026-08-20T17:56:02Z | `{"operationId":"ece7a5a0-8325-3790-9b4b-426696cb1d9b","correlationId":"6119ebf8-43b6-35cd-b310-20cfe58391e5","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"0c591d8f-0026-3890-8aa0-9d3373dec80b"}` |
| ReturnReceived | 17a3d892-0d3a-3ff9-87fd-69aa2e014f8a | 2026-08-20T17:56:22Z | 2026-08-20T17:56:32Z | `{"operationId":"a618b3cb-0b3d-3377-8f2e-acb0a7a2835b","correlationId":"342064ec-99ba-318d-b495-88145fd13236","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"0c591d8f-0026-3890-8aa0-9d3373dec80b"}` |
| IntegrationProcessed | 0a4693c6-e7b6-3768-b02a-ead4a6462c13 | 2026-08-21T17:55:52Z | 2026-08-21T17:56:02Z | `{"originalEventId":"17a3d892-0d3a-3ff9-87fd-69aa2e014f8a","correlationId":"342064ec-99ba-318d-b495-88145fd13236","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| RepairStarted | 0df48b48-4b30-3b58-a91a-47309395fbf5 | 2026-09-27T11:10:11Z | 2026-09-27T11:10:21Z | `{"operationId":"c0a9584d-e61f-3381-8d33-d325361a6757","correlationId":"fcbe3775-3479-3886-961e-d672c8b023d3","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","repairId":"f65726f1-fabe-35cf-8976-ec0d73e85050"}` |
| IntegrationProcessed | 7eba2598-c685-338f-842b-196f68170682 | 2026-09-27T11:10:41Z | 2026-09-27T11:10:51Z | `{"originalEventId":"0df48b48-4b30-3b58-a91a-47309395fbf5","correlationId":"fcbe3775-3479-3886-961e-d672c8b023d3","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**observations**
- `{"observationId":"dee5d1cd-eff5-3b55-8a35-85ed0405d092","copyId":"19698251-7fe7-3f01-adc3-eeac81f0b854","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","scopeId":"046f396d-2bb2-30ac-8a2f-25798c0002b8","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**sourceAvailability**
- `{"copyId":"19698251-7fe7-3f01-adc3-eeac81f0b854","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"19698251-7fe7-3f01-adc3-eeac81f0b854","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"19698251-7fe7-3f01-adc3-eeac81f0b854","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"19698251-7fe7-3f01-adc3-eeac81f0b854","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"19698251-7fe7-3f01-adc3-eeac81f0b854","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"19698251-7fe7-3f01-adc3-eeac81f0b854","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"19698251-7fe7-3f01-adc3-eeac81f0b854","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"19698251-7fe7-3f01-adc3-eeac81f0b854"},{"kind":"sourceEvent","id":"9e0b7896-7814-3aa1-851d-8a72dda9ddfb"},{"kind":"sourceEvent","id":"85228891-e3e8-3de7-8a0d-488bbc3ceeeb"},{"kind":"sourceEvent","id":"212bd7fa-6a21-3d0a-8cb0-aad29ee87c64"},{"kind":"sourceEvent","id":"0f7e86cb-70c6-33b1-95b5-59f85526b95e"},{"kind":"sourceEvent","id":"ec2366bc-ca7a-3fdb-9159-e00df04ac378"},{"kind":"sourceEvent","id":"17a3d892-0d3a-3ff9-87fd-69aa2e014f8a"},{"kind":"sourceEvent","id":"0a4693c6-e7b6-3768-b02a-ead4a6462c13"},{"kind":"sourceEvent","id":"0df48b48-4b30-3b58-a91a-47309395fbf5"},{"kind":"sourceEvent","id":"7eba2598-c685-338f-842b-196f68170682"},{"kind":"observation","id":"dee5d1cd-eff5-3b55-8a35-85ed0405d092"},{"kind":"inventoryScope","id":"046f396d-2bb2-30ac-8a2f-25798c0002b8"},{"kind":"sourceAvailability","id":"19698251-7fe7-3f01-adc3-eeac81f0b854/LOANS"},{"kind":"sourceAvailability","id":"19698251-7fe7-3f01-adc3-eeac81f0b854/RETURNS"},{"kind":"sourceAvailability","id":"19698251-7fe7-3f01-adc3-eeac81f0b854/RECEIPTS"},{"kind":"sourceAvailability","id":"19698251-7fe7-3f01-adc3-eeac81f0b854/TRANSFERS"},{"kind":"sourceAvailability","id":"19698251-7fe7-3f01-adc3-eeac81f0b854/REPAIRS"},{"kind":"sourceAvailability","id":"19698251-7fe7-3f01-adc3-eeac81f0b854/INTEGRATIONS"},{"kind":"sourceAvailability","id":"19698251-7fe7-3f01-adc3-eeac81f0b854/INVENTORY"},{"kind":"inventoryScope","id":"8021dca8-65fe-3cab-985e-d4fb9df5c308"},{"kind":"inventoryScope","id":"b0027657-6abb-37d2-8bc4-9341357dfe79"},{"kind":"inventoryScope","id":"4757f5eb-f104-3413-b118-51a6951a650c"},{"kind":"inventoryScope","id":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592"},{"kind":"inventoryScope","id":"40e94818-3a62-34a4-b461-398d7b9ffca2"},{"kind":"inventoryScope","id":"ed60c0be-b218-3f9e-8aa8-69c16f76c22e"},{"kind":"inventoryScope","id":"35514ff7-97f8-345d-8cf7-6cee969a726c"},{"kind":"inventoryScope","id":"351b3e2d-1bff-3290-938e-527c15f97274"},{"kind":"inventoryScope","id":"7bda04de-7eea-3647-9e69-f3610f2ce443"}]`

- Session : `{"scopeId":"046f396d-2bb2-30ac-8a2f-25798c0002b8","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"8021dca8-65fe-3cab-985e-d4fb9df5c308","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b0027657-6abb-37d2-8bc4-9341357dfe79","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4757f5eb-f104-3413-b118-51a6951a650c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"40e94818-3a62-34a4-b461-398d7b9ffca2","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"ed60c0be-b218-3f9e-8aa8-69c16f76c22e","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"35514ff7-97f8-345d-8cf7-6cee969a726c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"351b3e2d-1bff-3290-938e-527c15f97274","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"7bda04de-7eea-3647-9e69-f3610f2ce443","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 1a21af62-23c5-3d2a-8e57-bd75fb730a6e

- Exemplaire : `ea614b83-5a2a-3147-9bb2-8d0f13ade7ea`
- Famille privée : LOCALISATION ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"08a1d898-42fe-3156-b9d3-544a0eff42a8","kind":"COUNT_UNCERTAIN","expectedValue":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"mechanism":"Déplacement physique sans modification de l'affectation ; aucune attribution à un opérateur.","activePerturbations":["LOCATION_CHANGE_UNPROJECTED"],"physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":"5b61a611-a926-3ed2-82dc-fc122118757c"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"Identité du scan ambiguë ; ne pas attribuer un emplacement physique certain.","forbiddenClaims":["Vol","Perte définitive","Responsabilité d'un opérateur"],"recommendedChecks":["Refaire le scan et vérifier le code-barres"]}`
- État projeté : `{"copyId":"ea614b83-5a2a-3147-9bb2-8d0f13ade7ea","status":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","sourceEventIds":["4fae99b2-7996-361b-b4ef-be621ba1d16d","13221ced-4684-3f15-8759-a2c3007a59c8"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 1d3c57d3-d2d9-30a5-ab3a-83014e250bd5 | 2026-06-02T08:07:57Z | 2026-06-02T08:08:07Z | `{"operationId":"a57ad0f5-c652-367a-bcf8-3e348d7de4e2","correlationId":"8df0c559-f045-310a-a842-f4c9face04b1","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"4f20fb8a-29ce-3d65-9792-1825203feecc"}` |
| IntegrationProcessed | a010e1b9-d0d7-310e-a767-abda7a43e08e | 2026-06-03T08:07:57Z | 2026-06-03T08:08:07Z | `{"originalEventId":"1d3c57d3-d2d9-30a5-ab3a-83014e250bd5","correlationId":"8df0c559-f045-310a-a842-f4c9face04b1","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | bbac1f37-b690-37b7-80fa-d48699bc2d2e | 2026-08-01T08:07:57Z | 2026-08-01T08:08:07Z | `{"operationId":"9ccc7081-f130-3474-9591-802d94be76f2","correlationId":"997a9322-eec2-362b-9e27-37d1702ff498","siteId":"NORD","locationId":null,"loanId":"9e66da46-a83c-38f6-8e60-e39390494ef8"}` |
| IntegrationProcessed | c2c04db5-5126-3e9c-8868-ee0138f54e02 | 2026-08-01T08:08:57Z | 2026-08-01T08:09:07Z | `{"originalEventId":"bbac1f37-b690-37b7-80fa-d48699bc2d2e","correlationId":"997a9322-eec2-362b-9e27-37d1702ff498","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | 21f3e0a8-8484-3109-a620-846e237ac024 | 2026-08-21T08:07:57Z | 2026-08-21T08:08:07Z | `{"operationId":"8257fc6b-c9f1-3e99-8b1f-96e6af13d4bc","correlationId":"8efb865b-b3c7-37a7-a071-afbdcc4990c3","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"9e66da46-a83c-38f6-8e60-e39390494ef8"}` |
| ReturnReceived | 4fae99b2-7996-361b-b4ef-be621ba1d16d | 2026-08-21T08:08:27Z | 2026-08-21T08:08:37Z | `{"operationId":"5a5c83e8-698a-3c00-b18e-156a1a9964d8","correlationId":"91580eef-1185-33f0-bd2a-e945bf221ef7","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"9e66da46-a83c-38f6-8e60-e39390494ef8"}` |
| IntegrationProcessed | 13221ced-4684-3f15-8759-a2c3007a59c8 | 2026-08-22T08:07:57Z | 2026-08-22T08:08:07Z | `{"originalEventId":"4fae99b2-7996-361b-b4ef-be621ba1d16d","correlationId":"91580eef-1185-33f0-bd2a-e945bf221ef7","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |

**observations**
- `{"observationId":"47809a8d-75e5-32ac-9159-f94dfd453305","copyId":"ea614b83-5a2a-3147-9bb2-8d0f13ade7ea","locationId":"5b61a611-a926-3ed2-82dc-fc122118757c","scopeId":"1278bf9c-a7d7-3df7-9a81-22229cd71041","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**sourceAvailability**
- `{"copyId":"ea614b83-5a2a-3147-9bb2-8d0f13ade7ea","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ea614b83-5a2a-3147-9bb2-8d0f13ade7ea","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ea614b83-5a2a-3147-9bb2-8d0f13ade7ea","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ea614b83-5a2a-3147-9bb2-8d0f13ade7ea","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ea614b83-5a2a-3147-9bb2-8d0f13ade7ea","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ea614b83-5a2a-3147-9bb2-8d0f13ade7ea","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ea614b83-5a2a-3147-9bb2-8d0f13ade7ea","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"observation","id":"47809a8d-75e5-32ac-9159-f94dfd453305"},{"kind":"systemState","id":"ea614b83-5a2a-3147-9bb2-8d0f13ade7ea"}]`

## Cas 1e62024e-1075-3a4b-a297-a6fc494c4949

- Exemplaire : `1191015d-a995-3248-befc-8a80437b29b5`
- Famille privée : REPARATION_NON_PROJETEE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"1bf524b9-c6ee-3d4f-a9e0-c4c23800c861","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"40b84193-7d4e-353c-88ee-9969ecbeec6e","physicalStatus":"IN_REPAIR","physicalSiteId":"NORD","physicalLocationId":"11044fb8-eb45-33d3-90df-81754bc299a0","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"1191015d-a995-3248-befc-8a80437b29b5","status":"IN_REPAIR","siteId":"NORD","locationId":"11044fb8-eb45-33d3-90df-81754bc299a0","sourceEventIds":["40b84193-7d4e-353c-88ee-9969ecbeec6e","df64bdc7-ae64-38a2-b4cd-508b5db7a9ea"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | e1cabe61-9e47-3c84-981b-d4a46ceb7bce | 2026-06-02T09:02:51Z | 2026-06-02T09:03:01Z | `{"operationId":"6c4dcc1b-8f00-34da-bccd-0954982cab14","correlationId":"81703788-fbfa-3945-920f-b322a29fc873","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"fac6f230-972b-3699-b96e-ce399eaf298f"}` |
| IntegrationProcessed | 440125c3-2620-3ab7-b10b-5e99e9bad0ce | 2026-06-03T09:02:51Z | 2026-06-03T09:03:01Z | `{"originalEventId":"e1cabe61-9e47-3c84-981b-d4a46ceb7bce","correlationId":"81703788-fbfa-3945-920f-b322a29fc873","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 85ad69aa-5468-3b24-8df0-89aa6e6f7abf | 2026-08-01T09:02:51Z | 2026-08-01T09:03:01Z | `{"operationId":"482af138-0a6d-3b5c-82d5-3ffa4db66c3f","correlationId":"bb94ea91-ff97-3de1-8f82-43ecccc3c417","siteId":"CENTRE","locationId":null,"loanId":"e17abb0f-b0c9-3fea-9243-22a54268abef"}` |
| IntegrationProcessed | 3a16a8dd-1b0e-3227-ac0c-6835e8cf2e82 | 2026-08-01T09:03:51Z | 2026-08-01T09:04:01Z | `{"originalEventId":"85ad69aa-5468-3b24-8df0-89aa6e6f7abf","correlationId":"bb94ea91-ff97-3de1-8f82-43ecccc3c417","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | a3cca4ee-76a7-306e-9794-c35bfeab46ec | 2026-08-21T09:02:51Z | 2026-08-21T09:03:01Z | `{"operationId":"53beaf8d-6ae0-3275-8feb-95f44eb79321","correlationId":"7e03581d-e907-34bb-8fc5-904950c057c7","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"e17abb0f-b0c9-3fea-9243-22a54268abef"}` |
| ReturnReceived | 59866944-b920-320e-81ae-5f5b584f1518 | 2026-08-21T09:03:21Z | 2026-08-21T09:03:31Z | `{"operationId":"1501dc4a-3cd7-34b8-b715-96a719956b02","correlationId":"7b89d4a4-40b7-30ce-b288-33744f1f1fff","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"e17abb0f-b0c9-3fea-9243-22a54268abef"}` |
| IntegrationProcessed | aee3befb-5141-3992-ac95-86a32c7ae3dd | 2026-08-22T09:02:51Z | 2026-08-22T09:03:01Z | `{"originalEventId":"59866944-b920-320e-81ae-5f5b584f1518","correlationId":"7b89d4a4-40b7-30ce-b288-33744f1f1fff","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| TransferDispatched | 2258468d-9e84-3e00-aa42-a231f094f15d | 2026-08-31T09:02:51Z | 2026-08-31T09:03:01Z | `{"operationId":"e84607e1-83aa-340e-ba17-b4ec138b1424","correlationId":"875c9109-9481-361a-8a9a-c92cc0d7e4fa","siteId":"CENTRE","locationId":null,"transferId":"87ad4d4f-dad9-3c35-a88d-651b338d31e3","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | 1cacf771-db91-38b1-a839-170695354dbc | 2026-08-31T09:03:51Z | 2026-08-31T09:04:01Z | `{"originalEventId":"2258468d-9e84-3e00-aa42-a231f094f15d","correlationId":"875c9109-9481-361a-8a9a-c92cc0d7e4fa","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| TransferReceived | c7dcdd86-659a-3dc7-ba63-e7a60834cdc0 | 2026-09-01T09:02:51Z | 2026-09-01T09:03:01Z | `{"operationId":"76ab8f6d-e643-3e4a-a9e9-b29cfe918ac1","correlationId":"40295424-af6e-3084-89d5-75060321062b","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","transferId":"87ad4d4f-dad9-3c35-a88d-651b338d31e3","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | 592a0556-7e95-3ab7-974d-147291bff662 | 2026-09-02T09:02:51Z | 2026-09-02T09:03:01Z | `{"originalEventId":"c7dcdd86-659a-3dc7-ba63-e7a60834cdc0","correlationId":"40295424-af6e-3084-89d5-75060321062b","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| RepairStarted | 40b84193-7d4e-353c-88ee-9969ecbeec6e | 2026-09-27T11:01:16Z | 2026-09-27T11:01:26Z | `{"operationId":"baf33500-e064-3c8d-894b-dc53b5cdd2c8","correlationId":"cc81d614-a747-36d9-acd5-55822f6d2752","siteId":"NORD","locationId":"11044fb8-eb45-33d3-90df-81754bc299a0","repairId":"835dcc02-980b-350f-bbca-d8afbcca447b"}` |
| IntegrationProcessed | 562204cf-a4cb-33e9-bdd1-de01e9fd652d | 2026-09-27T11:01:46Z | 2026-09-27T11:01:56Z | `{"originalEventId":"40b84193-7d4e-353c-88ee-9969ecbeec6e","correlationId":"cc81d614-a747-36d9-acd5-55822f6d2752","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"NORD","locationId":"11044fb8-eb45-33d3-90df-81754bc299a0","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| IntegrationProcessed | df64bdc7-ae64-38a2-b4cd-508b5db7a9ea | 2026-09-27T11:02:46Z | 2026-09-27T11:02:56Z | `{"originalEventId":"40b84193-7d4e-353c-88ee-9969ecbeec6e","correlationId":"cc81d614-a747-36d9-acd5-55822f6d2752","outcome":"APPLIED","projectedStatus":"IN_REPAIR","siteId":"NORD","locationId":"11044fb8-eb45-33d3-90df-81754bc299a0"}` |

**observations**
- `{"observationId":"0a7e80a1-81e3-3b68-b39f-0b75bb0150e4","copyId":"1191015d-a995-3248-befc-8a80437b29b5","locationId":"11044fb8-eb45-33d3-90df-81754bc299a0","scopeId":"f8c85b34-9c52-34a7-8f9e-d7d96bb4b2a2","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`
- `{"observationId":"ee9d104f-7abe-34c1-86f0-c605dc722047","copyId":"1191015d-a995-3248-befc-8a80437b29b5","locationId":"5b61a611-a926-3ed2-82dc-fc122118757c","scopeId":"9e57b677-25a5-364a-a48f-02258d32b2dd","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**sourceAvailability**
- `{"copyId":"1191015d-a995-3248-befc-8a80437b29b5","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1191015d-a995-3248-befc-8a80437b29b5","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1191015d-a995-3248-befc-8a80437b29b5","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1191015d-a995-3248-befc-8a80437b29b5","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1191015d-a995-3248-befc-8a80437b29b5","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1191015d-a995-3248-befc-8a80437b29b5","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1191015d-a995-3248-befc-8a80437b29b5","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"1191015d-a995-3248-befc-8a80437b29b5"},{"kind":"sourceEvent","id":"e1cabe61-9e47-3c84-981b-d4a46ceb7bce"},{"kind":"sourceEvent","id":"440125c3-2620-3ab7-b10b-5e99e9bad0ce"},{"kind":"sourceEvent","id":"85ad69aa-5468-3b24-8df0-89aa6e6f7abf"},{"kind":"sourceEvent","id":"3a16a8dd-1b0e-3227-ac0c-6835e8cf2e82"},{"kind":"sourceEvent","id":"a3cca4ee-76a7-306e-9794-c35bfeab46ec"},{"kind":"sourceEvent","id":"59866944-b920-320e-81ae-5f5b584f1518"},{"kind":"sourceEvent","id":"aee3befb-5141-3992-ac95-86a32c7ae3dd"},{"kind":"sourceEvent","id":"2258468d-9e84-3e00-aa42-a231f094f15d"},{"kind":"sourceEvent","id":"1cacf771-db91-38b1-a839-170695354dbc"},{"kind":"sourceEvent","id":"c7dcdd86-659a-3dc7-ba63-e7a60834cdc0"},{"kind":"sourceEvent","id":"592a0556-7e95-3ab7-974d-147291bff662"},{"kind":"sourceEvent","id":"40b84193-7d4e-353c-88ee-9969ecbeec6e"},{"kind":"sourceEvent","id":"562204cf-a4cb-33e9-bdd1-de01e9fd652d"},{"kind":"sourceEvent","id":"df64bdc7-ae64-38a2-b4cd-508b5db7a9ea"},{"kind":"observation","id":"0a7e80a1-81e3-3b68-b39f-0b75bb0150e4"},{"kind":"inventoryScope","id":"f8c85b34-9c52-34a7-8f9e-d7d96bb4b2a2"},{"kind":"observation","id":"ee9d104f-7abe-34c1-86f0-c605dc722047"},{"kind":"inventoryScope","id":"9e57b677-25a5-364a-a48f-02258d32b2dd"},{"kind":"sourceAvailability","id":"1191015d-a995-3248-befc-8a80437b29b5/LOANS"},{"kind":"sourceAvailability","id":"1191015d-a995-3248-befc-8a80437b29b5/RETURNS"},{"kind":"sourceAvailability","id":"1191015d-a995-3248-befc-8a80437b29b5/RECEIPTS"},{"kind":"sourceAvailability","id":"1191015d-a995-3248-befc-8a80437b29b5/TRANSFERS"},{"kind":"sourceAvailability","id":"1191015d-a995-3248-befc-8a80437b29b5/REPAIRS"},{"kind":"sourceAvailability","id":"1191015d-a995-3248-befc-8a80437b29b5/INTEGRATIONS"},{"kind":"sourceAvailability","id":"1191015d-a995-3248-befc-8a80437b29b5/INVENTORY"},{"kind":"inventoryScope","id":"ec51e7eb-14f0-38c9-b069-fc062ac5d09f"},{"kind":"inventoryScope","id":"9c8fd8d3-68e5-33e3-b739-628e602f488d"}]`

- Session : `{"scopeId":"f8c85b34-9c52-34a7-8f9e-d7d96bb4b2a2","locationId":"11044fb8-eb45-33d3-90df-81754bc299a0","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9e57b677-25a5-364a-a48f-02258d32b2dd","locationId":"5b61a611-a926-3ed2-82dc-fc122118757c","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"ec51e7eb-14f0-38c9-b069-fc062ac5d09f","locationId":"11044fb8-eb45-33d3-90df-81754bc299a0","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9c8fd8d3-68e5-33e3-b739-628e602f488d","locationId":"11044fb8-eb45-33d3-90df-81754bc299a0","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 208999dd-6ffe-38bf-9b13-8c7e53e2d86c

- Exemplaire : `639af38b-0415-3ad1-a77f-17d0f89225ab`
- Famille privée : RECEPTION_NON_INTEGREE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"f3cde77b-e4f1-345a-9a32-d8a05f308f45","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"fb49629f-5888-3484-ab48-967168001060","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"639af38b-0415-3ad1-a77f-17d0f89225ab","status":"RECEIVING","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","sourceEventIds":["fb49629f-5888-3484-ab48-967168001060","0feba4da-9fd5-3d36-9f28-2b96a8c28c74"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | fb49629f-5888-3484-ab48-967168001060 | 2026-09-27T07:57:49Z | 2026-09-27T07:57:59Z | `{"operationId":"2959ff1d-8271-38ce-9c1f-c70584ab856b","correlationId":"40441032-5d7f-3db5-bf4a-add2bd3f7868","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"ccb8a736-caa6-36a5-8b4c-58de34cd6db3"}` |
| IntegrationProcessed | 9619c37e-d9c7-315b-9965-37907c827d46 | 2026-09-27T07:58:19Z | 2026-09-27T07:58:29Z | `{"originalEventId":"fb49629f-5888-3484-ab48-967168001060","correlationId":"40441032-5d7f-3db5-bf4a-add2bd3f7868","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| IntegrationProcessed | 0feba4da-9fd5-3d36-9f28-2b96a8c28c74 | 2026-09-27T07:59:19Z | 2026-09-27T07:59:29Z | `{"originalEventId":"fb49629f-5888-3484-ab48-967168001060","correlationId":"40441032-5d7f-3db5-bf4a-add2bd3f7868","outcome":"APPLIED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca"}` |

**observations**
- `{"observationId":"1cd38479-c7ca-3d3d-85a0-3fb54f2badbf","copyId":"639af38b-0415-3ad1-a77f-17d0f89225ab","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","scopeId":"3dcdb434-8f34-3a6d-9686-5a131ba8628c","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`
- `{"observationId":"f8b39acb-107a-3149-9e3f-5359f10a3739","copyId":"639af38b-0415-3ad1-a77f-17d0f89225ab","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","scopeId":"05d8db8a-5e3e-3d34-bc9c-a98682e8bf48","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**sourceAvailability**
- `{"copyId":"639af38b-0415-3ad1-a77f-17d0f89225ab","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"639af38b-0415-3ad1-a77f-17d0f89225ab","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"639af38b-0415-3ad1-a77f-17d0f89225ab","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"639af38b-0415-3ad1-a77f-17d0f89225ab","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"639af38b-0415-3ad1-a77f-17d0f89225ab","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"639af38b-0415-3ad1-a77f-17d0f89225ab","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"639af38b-0415-3ad1-a77f-17d0f89225ab","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"639af38b-0415-3ad1-a77f-17d0f89225ab"},{"kind":"sourceEvent","id":"fb49629f-5888-3484-ab48-967168001060"},{"kind":"sourceEvent","id":"9619c37e-d9c7-315b-9965-37907c827d46"},{"kind":"sourceEvent","id":"0feba4da-9fd5-3d36-9f28-2b96a8c28c74"},{"kind":"observation","id":"1cd38479-c7ca-3d3d-85a0-3fb54f2badbf"},{"kind":"inventoryScope","id":"3dcdb434-8f34-3a6d-9686-5a131ba8628c"},{"kind":"observation","id":"f8b39acb-107a-3149-9e3f-5359f10a3739"},{"kind":"inventoryScope","id":"05d8db8a-5e3e-3d34-bc9c-a98682e8bf48"},{"kind":"sourceAvailability","id":"639af38b-0415-3ad1-a77f-17d0f89225ab/LOANS"},{"kind":"sourceAvailability","id":"639af38b-0415-3ad1-a77f-17d0f89225ab/RETURNS"},{"kind":"sourceAvailability","id":"639af38b-0415-3ad1-a77f-17d0f89225ab/RECEIPTS"},{"kind":"sourceAvailability","id":"639af38b-0415-3ad1-a77f-17d0f89225ab/TRANSFERS"},{"kind":"sourceAvailability","id":"639af38b-0415-3ad1-a77f-17d0f89225ab/REPAIRS"},{"kind":"sourceAvailability","id":"639af38b-0415-3ad1-a77f-17d0f89225ab/INTEGRATIONS"},{"kind":"sourceAvailability","id":"639af38b-0415-3ad1-a77f-17d0f89225ab/INVENTORY"},{"kind":"inventoryScope","id":"27d49d3d-f104-306e-b8d1-ada0e3973acb"},{"kind":"inventoryScope","id":"59116f6f-fc86-3206-80a1-873afe081073"},{"kind":"inventoryScope","id":"bea0c19c-0c1e-3d31-ab4d-dda10370bd37"},{"kind":"inventoryScope","id":"57ee798c-ebfa-3b42-9818-10132c6f964d"},{"kind":"inventoryScope","id":"36b049f7-1dcb-338a-a1c8-06aeb50d77ff"},{"kind":"inventoryScope","id":"03c2ead3-c284-3854-b5df-7e7d3216c4f7"},{"kind":"inventoryScope","id":"561e4db8-b71d-30ae-ba0e-f61099efa960"}]`

- Session : `{"scopeId":"3dcdb434-8f34-3a6d-9686-5a131ba8628c","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"05d8db8a-5e3e-3d34-bc9c-a98682e8bf48","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"27d49d3d-f104-306e-b8d1-ada0e3973acb","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"59116f6f-fc86-3206-80a1-873afe081073","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"bea0c19c-0c1e-3d31-ab4d-dda10370bd37","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"57ee798c-ebfa-3b42-9818-10132c6f964d","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"36b049f7-1dcb-338a-a1c8-06aeb50d77ff","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"03c2ead3-c284-3854-b5df-7e7d3216c4f7","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"561e4db8-b71d-30ae-ba0e-f61099efa960","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 23ea9d0c-5f9d-33c1-9710-88d52e6043ae

- Exemplaire : `82ab76e4-2f33-394a-a0cd-a46749de61c8`
- Famille privée : PRET_NON_PROJETE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"9428f03f-30e8-3715-ace5-4fbf90b528ce","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"7253e490-5099-392e-b354-6ae5990d08fc","copyId":"82ab76e4-2f33-394a-a0cd-a46749de61c8","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T11:11:44Z","recordedAt":"2026-09-27T11:11:54Z","payload":{"originalEventId":"9f585d6f-2a66-3b16-83b0-6c6ea72e9a88","correlationId":"b7c2b4bb-bb21-3c5a-9aaf-794fda7c473a","outcome":"FAILED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"9f585d6f-2a66-3b16-83b0-6c6ea72e9a88","physicalStatus":"ON_LOAN","physicalSiteId":"CENTRE","physicalLocationId":null,"projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État projeté : `{"copyId":"82ab76e4-2f33-394a-a0cd-a46749de61c8","status":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","sourceEventIds":["655516d1-0713-3c2e-8555-93ce86567f06","e7495332-2048-3491-9a46-e8716c7f454e"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | f0b1f881-fb84-31f9-85c9-4e786b95fd3b | 2026-06-01T16:00:01Z | 2026-06-01T16:00:11Z | `{"operationId":"ecb3bf2c-1e60-35df-a09c-f84820568bea","correlationId":"d04f6d64-ca12-3485-90d8-ff71f1069888","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"209fdc8d-73d9-321f-92f3-0f7bcf4a9c9b"}` |
| IntegrationProcessed | a55fe118-b7ca-3f1f-b4f0-123ce513cb03 | 2026-06-02T16:00:01Z | 2026-06-02T16:00:11Z | `{"originalEventId":"f0b1f881-fb84-31f9-85c9-4e786b95fd3b","correlationId":"d04f6d64-ca12-3485-90d8-ff71f1069888","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 57fb9e1e-7d00-3c1c-923e-8e3ee94636a5 | 2026-07-31T16:00:01Z | 2026-07-31T16:00:11Z | `{"operationId":"75774e4a-fc83-306f-a92b-408b9ad0e20b","correlationId":"cc4c8864-8b7a-32a6-a49e-eb4bc9c9316b","siteId":"CENTRE","locationId":null,"loanId":"731eb423-4509-3d36-b5d1-4b26e7478926"}` |
| IntegrationProcessed | b0e263b7-2e7e-3de1-a330-b18866297eaf | 2026-07-31T16:01:01Z | 2026-07-31T16:01:11Z | `{"originalEventId":"57fb9e1e-7d00-3c1c-923e-8e3ee94636a5","correlationId":"cc4c8864-8b7a-32a6-a49e-eb4bc9c9316b","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 93927903-4c3c-3b66-a4d1-f5bc6b4d959a | 2026-08-20T16:00:01Z | 2026-08-20T16:00:11Z | `{"operationId":"eb64a9b6-ddd0-3aca-8623-918e8ecf3d11","correlationId":"f29d760f-0c91-379a-8b04-32081484a3cc","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"731eb423-4509-3d36-b5d1-4b26e7478926"}` |
| ReturnReceived | 655516d1-0713-3c2e-8555-93ce86567f06 | 2026-08-20T16:00:31Z | 2026-08-20T16:00:41Z | `{"operationId":"9366da01-4ad4-3731-9f05-86c0fc81e5ab","correlationId":"dea2c78d-de10-38c8-8da3-b941ce3145c4","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"731eb423-4509-3d36-b5d1-4b26e7478926"}` |
| IntegrationProcessed | e7495332-2048-3491-9a46-e8716c7f454e | 2026-08-21T16:00:01Z | 2026-08-21T16:00:11Z | `{"originalEventId":"655516d1-0713-3c2e-8555-93ce86567f06","correlationId":"dea2c78d-de10-38c8-8da3-b941ce3145c4","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 9f585d6f-2a66-3b16-83b0-6c6ea72e9a88 | 2026-09-27T11:11:14Z | 2026-09-27T11:11:24Z | `{"operationId":"d81fd31c-4765-3651-a48e-24c24f1bd7e4","correlationId":"b7c2b4bb-bb21-3c5a-9aaf-794fda7c473a","siteId":"CENTRE","locationId":null,"loanId":"b10b493e-54b6-345e-86cb-a86a0f162403"}` |

**observations**

**sourceAvailability**
- `{"copyId":"82ab76e4-2f33-394a-a0cd-a46749de61c8","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"82ab76e4-2f33-394a-a0cd-a46749de61c8","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"82ab76e4-2f33-394a-a0cd-a46749de61c8","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"82ab76e4-2f33-394a-a0cd-a46749de61c8","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"82ab76e4-2f33-394a-a0cd-a46749de61c8","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"82ab76e4-2f33-394a-a0cd-a46749de61c8","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"82ab76e4-2f33-394a-a0cd-a46749de61c8","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"82ab76e4-2f33-394a-a0cd-a46749de61c8"},{"kind":"sourceEvent","id":"f0b1f881-fb84-31f9-85c9-4e786b95fd3b"},{"kind":"sourceEvent","id":"a55fe118-b7ca-3f1f-b4f0-123ce513cb03"},{"kind":"sourceEvent","id":"57fb9e1e-7d00-3c1c-923e-8e3ee94636a5"},{"kind":"sourceEvent","id":"b0e263b7-2e7e-3de1-a330-b18866297eaf"},{"kind":"sourceEvent","id":"93927903-4c3c-3b66-a4d1-f5bc6b4d959a"},{"kind":"sourceEvent","id":"655516d1-0713-3c2e-8555-93ce86567f06"},{"kind":"sourceEvent","id":"e7495332-2048-3491-9a46-e8716c7f454e"},{"kind":"sourceEvent","id":"9f585d6f-2a66-3b16-83b0-6c6ea72e9a88"},{"kind":"sourceAvailability","id":"82ab76e4-2f33-394a-a0cd-a46749de61c8/LOANS"},{"kind":"sourceAvailability","id":"82ab76e4-2f33-394a-a0cd-a46749de61c8/RETURNS"},{"kind":"sourceAvailability","id":"82ab76e4-2f33-394a-a0cd-a46749de61c8/RECEIPTS"},{"kind":"sourceAvailability","id":"82ab76e4-2f33-394a-a0cd-a46749de61c8/TRANSFERS"},{"kind":"sourceAvailability","id":"82ab76e4-2f33-394a-a0cd-a46749de61c8/REPAIRS"},{"kind":"sourceAvailability","id":"82ab76e4-2f33-394a-a0cd-a46749de61c8/INTEGRATIONS"},{"kind":"sourceAvailability","id":"82ab76e4-2f33-394a-a0cd-a46749de61c8/INVENTORY"},{"kind":"inventoryScope","id":"8021dca8-65fe-3cab-985e-d4fb9df5c308"},{"kind":"inventoryScope","id":"b0027657-6abb-37d2-8bc4-9341357dfe79"},{"kind":"inventoryScope","id":"4757f5eb-f104-3413-b118-51a6951a650c"}]`

- Session : `{"scopeId":"8021dca8-65fe-3cab-985e-d4fb9df5c308","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b0027657-6abb-37d2-8bc4-9341357dfe79","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4757f5eb-f104-3413-b118-51a6951a650c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 27dbbee6-9aea-3f87-9dc7-5270f4d49641

- Exemplaire : `0720827b-3886-3a3e-9056-280df643c86f`
- Famille privée : DEUX_CAUSES ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"1cab1dd0-fb8c-3c79-8647-c195c88fd20b","kind":"STATUS_MISMATCH","expectedValue":"IN_REPAIR","observedValue":"ON_SHELF"},{"symptomId":"be0bf1c8-06d8-37ce-bd82-162a02bb0abb","kind":"LOCATION_MISMATCH","expectedValue":"64a3276c-7791-367a-984b-158e1b652a52","observedValue":"205b3714-25eb-3b7f-aead-24616287f71a"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED","REPAIR_LOCATION_CHANGED"],"hiddenSourceEvents":[],"mechanism":"Réparation engagée puis déplacement distinct de l'atelier vers la réserve sans mise à jour de l'affectation de réparation.","businessEventId":"b1fc8dbf-9f8d-3ce8-bd61-30f35e70ab81","physicalStatus":"IN_REPAIR","physicalSiteId":"SUD","physicalLocationId":"205b3714-25eb-3b7f-aead-24616287f71a","projectionPerturbation":"FAILED","evidenceMask":"Scan de réserve ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":["REPAIR_NOT_PROJECTED"],"explanation":"Échec de projection de réparation documenté ; seconde localisation non confirmée par un scan valide.","forbiddenClaims":["Deux causes confirmées par un scan ambigu","Vol"],"recommendedChecks":["Refaire le scan en réserve"]}`
- État projeté : `{"copyId":"0720827b-3886-3a3e-9056-280df643c86f","status":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","sourceEventIds":["e4d4b8f7-baad-3f48-9b05-23075762b70b","cb5616ec-4e83-3699-9f5a-7733c01b228b"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 4577ceca-180c-3418-a97d-cb94d8a883ee | 2026-06-02T00:35:17Z | 2026-06-02T00:35:27Z | `{"operationId":"5a1e9364-46ec-3651-86d5-d7fbef19bb61","correlationId":"70195bc7-3f04-3298-b6bc-d8e4c41788c8","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"701387a7-b1e4-359e-9c11-7a061dd72fa8"}` |
| IntegrationProcessed | 4a8479a5-8886-373e-b7f6-81c8ae6ab6f0 | 2026-06-03T00:35:17Z | 2026-06-03T00:35:27Z | `{"originalEventId":"4577ceca-180c-3418-a97d-cb94d8a883ee","correlationId":"70195bc7-3f04-3298-b6bc-d8e4c41788c8","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | fe9d906c-3c4c-35ef-85d6-326b5373ec5b | 2026-08-01T00:35:17Z | 2026-08-01T00:35:27Z | `{"operationId":"c71f0ad8-4fa5-32e6-bed6-4170c0e9c54f","correlationId":"c5f7fe85-80e9-3738-bca6-e3e34b20a358","siteId":"NORD","locationId":null,"loanId":"24428b4c-10c3-309b-aec0-68c9c9c1a70b"}` |
| IntegrationProcessed | bcfa957a-de3d-390f-bb5d-29d719f023e0 | 2026-08-01T00:36:17Z | 2026-08-01T00:36:27Z | `{"originalEventId":"fe9d906c-3c4c-35ef-85d6-326b5373ec5b","correlationId":"c5f7fe85-80e9-3738-bca6-e3e34b20a358","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | ec5c08c1-f4c0-31a1-9014-c9594583ad1f | 2026-08-21T00:35:17Z | 2026-08-21T00:35:27Z | `{"operationId":"056cbe95-c56f-38b6-8dc9-43ea1818af6e","correlationId":"8c3a1586-66c4-3ed3-8e36-b8f62c00d331","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"24428b4c-10c3-309b-aec0-68c9c9c1a70b"}` |
| ReturnReceived | 96a867e9-0af7-321c-b67a-f246809aa9b2 | 2026-08-21T00:35:47Z | 2026-08-21T00:35:57Z | `{"operationId":"de5a65f1-8ec2-396e-8c8d-92a312be108e","correlationId":"e55ee3bb-bc13-3f6a-9d27-9b7053c9fd5b","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"24428b4c-10c3-309b-aec0-68c9c9c1a70b"}` |
| IntegrationProcessed | 3d04b915-3007-394d-84cb-921cc76f339d | 2026-08-22T00:35:17Z | 2026-08-22T00:35:27Z | `{"originalEventId":"96a867e9-0af7-321c-b67a-f246809aa9b2","correlationId":"e55ee3bb-bc13-3f6a-9d27-9b7053c9fd5b","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| TransferDispatched | 89ba3808-1c58-3a5b-83e2-6a7c4efdd5a3 | 2026-08-31T00:35:17Z | 2026-08-31T00:35:27Z | `{"operationId":"f10a9a9e-dba0-3953-a8a9-d728f9a4ddf1","correlationId":"5f6f9ffb-0712-353d-aeff-63620ffab52f","siteId":"NORD","locationId":null,"transferId":"26a04bc1-2b3b-3626-81c5-930242e0496a","fromSiteId":"NORD","toSiteId":"SUD"}` |
| IntegrationProcessed | d88395aa-891b-31c4-97b1-d0cc71cfe509 | 2026-08-31T00:36:17Z | 2026-08-31T00:36:27Z | `{"originalEventId":"89ba3808-1c58-3a5b-83e2-6a7c4efdd5a3","correlationId":"5f6f9ffb-0712-353d-aeff-63620ffab52f","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"NORD","locationId":null}` |
| TransferReceived | e4d4b8f7-baad-3f48-9b05-23075762b70b | 2026-09-01T00:35:17Z | 2026-09-01T00:35:27Z | `{"operationId":"39668dd8-8dba-38ca-b7ba-bcdfb3b888e8","correlationId":"aed4a679-ef87-3670-b7c5-feddb068088a","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","transferId":"26a04bc1-2b3b-3626-81c5-930242e0496a","fromSiteId":"NORD","toSiteId":"SUD"}` |
| IntegrationProcessed | cb5616ec-4e83-3699-9f5a-7733c01b228b | 2026-09-02T00:35:17Z | 2026-09-02T00:35:27Z | `{"originalEventId":"e4d4b8f7-baad-3f48-9b05-23075762b70b","correlationId":"aed4a679-ef87-3670-b7c5-feddb068088a","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| RepairStarted | b1fc8dbf-9f8d-3ce8-bd61-30f35e70ab81 | 2026-09-27T09:17:18Z | 2026-09-27T09:17:28Z | `{"operationId":"f8ac4a3f-7397-3825-8f05-5db0d6d5adde","correlationId":"52e220f8-3adc-3374-99dd-7b9535180f51","siteId":"SUD","locationId":"64a3276c-7791-367a-984b-158e1b652a52","repairId":"c18d5b5c-e132-3c93-95f7-4d52a23ae271"}` |
| IntegrationProcessed | 577b47e3-e912-3d77-978f-3336b354a9f2 | 2026-09-27T09:17:48Z | 2026-09-27T09:17:58Z | `{"originalEventId":"b1fc8dbf-9f8d-3ce8-bd61-30f35e70ab81","correlationId":"52e220f8-3adc-3374-99dd-7b9535180f51","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"SUD","locationId":"64a3276c-7791-367a-984b-158e1b652a52","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**observations**
- `{"observationId":"df4cd5b7-8e5e-36b7-bfe1-cac87555f787","copyId":"0720827b-3886-3a3e-9056-280df643c86f","locationId":"205b3714-25eb-3b7f-aead-24616287f71a","scopeId":"0bdd53b4-abea-35e8-9e58-51c13fd457ed","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**sourceAvailability**
- `{"copyId":"0720827b-3886-3a3e-9056-280df643c86f","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0720827b-3886-3a3e-9056-280df643c86f","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0720827b-3886-3a3e-9056-280df643c86f","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0720827b-3886-3a3e-9056-280df643c86f","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0720827b-3886-3a3e-9056-280df643c86f","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0720827b-3886-3a3e-9056-280df643c86f","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0720827b-3886-3a3e-9056-280df643c86f","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"0720827b-3886-3a3e-9056-280df643c86f"},{"kind":"sourceEvent","id":"4577ceca-180c-3418-a97d-cb94d8a883ee"},{"kind":"sourceEvent","id":"4a8479a5-8886-373e-b7f6-81c8ae6ab6f0"},{"kind":"sourceEvent","id":"fe9d906c-3c4c-35ef-85d6-326b5373ec5b"},{"kind":"sourceEvent","id":"bcfa957a-de3d-390f-bb5d-29d719f023e0"},{"kind":"sourceEvent","id":"ec5c08c1-f4c0-31a1-9014-c9594583ad1f"},{"kind":"sourceEvent","id":"96a867e9-0af7-321c-b67a-f246809aa9b2"},{"kind":"sourceEvent","id":"3d04b915-3007-394d-84cb-921cc76f339d"},{"kind":"sourceEvent","id":"89ba3808-1c58-3a5b-83e2-6a7c4efdd5a3"},{"kind":"sourceEvent","id":"d88395aa-891b-31c4-97b1-d0cc71cfe509"},{"kind":"sourceEvent","id":"e4d4b8f7-baad-3f48-9b05-23075762b70b"},{"kind":"sourceEvent","id":"cb5616ec-4e83-3699-9f5a-7733c01b228b"},{"kind":"sourceEvent","id":"b1fc8dbf-9f8d-3ce8-bd61-30f35e70ab81"},{"kind":"sourceEvent","id":"577b47e3-e912-3d77-978f-3336b354a9f2"},{"kind":"observation","id":"df4cd5b7-8e5e-36b7-bfe1-cac87555f787"},{"kind":"inventoryScope","id":"0bdd53b4-abea-35e8-9e58-51c13fd457ed"},{"kind":"sourceAvailability","id":"0720827b-3886-3a3e-9056-280df643c86f/LOANS"},{"kind":"sourceAvailability","id":"0720827b-3886-3a3e-9056-280df643c86f/RETURNS"},{"kind":"sourceAvailability","id":"0720827b-3886-3a3e-9056-280df643c86f/RECEIPTS"},{"kind":"sourceAvailability","id":"0720827b-3886-3a3e-9056-280df643c86f/TRANSFERS"},{"kind":"sourceAvailability","id":"0720827b-3886-3a3e-9056-280df643c86f/REPAIRS"},{"kind":"sourceAvailability","id":"0720827b-3886-3a3e-9056-280df643c86f/INTEGRATIONS"},{"kind":"sourceAvailability","id":"0720827b-3886-3a3e-9056-280df643c86f/INVENTORY"},{"kind":"inventoryScope","id":"294f8877-9130-3961-8435-48be641fa20c"},{"kind":"inventoryScope","id":"9d6c09df-1bb9-3fa2-927d-b44bcfe9e1be"},{"kind":"inventoryScope","id":"8ba7cc3d-833a-342a-bcaa-e1d23f56a4e3"},{"kind":"inventoryScope","id":"59269371-fb13-3ca1-a62a-01272d903229"},{"kind":"inventoryScope","id":"139f34be-b437-3f23-84c9-757fbea07b69"},{"kind":"inventoryScope","id":"002b3157-56c1-3594-86e4-95e1ed6a3889"}]`

- Session : `{"scopeId":"0bdd53b4-abea-35e8-9e58-51c13fd457ed","locationId":"205b3714-25eb-3b7f-aead-24616287f71a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"294f8877-9130-3961-8435-48be641fa20c","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9d6c09df-1bb9-3fa2-927d-b44bcfe9e1be","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"8ba7cc3d-833a-342a-bcaa-e1d23f56a4e3","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"59269371-fb13-3ca1-a62a-01272d903229","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"139f34be-b437-3f23-84c9-757fbea07b69","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"002b3157-56c1-3594-86e4-95e1ed6a3889","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 29458c23-99a3-3a47-ae5b-992214b2d9a9

- Exemplaire : `97a7dc6d-9cbe-39d0-8048-06e94d678f90`
- Famille privée : PRET_NON_PROJETE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"2fd05a4d-ca79-3750-9482-aa1c9ed9be31","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"a3e44f6f-b9d1-3166-86da-5a032221ee63","physicalStatus":"ON_LOAN","physicalSiteId":"SUD","physicalLocationId":null,"projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"97a7dc6d-9cbe-39d0-8048-06e94d678f90","status":"ON_LOAN","siteId":"SUD","locationId":null,"sourceEventIds":["a3e44f6f-b9d1-3166-86da-5a032221ee63","bed11e69-bf57-3266-923a-38c83e6a19c9"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 283b7cb5-334c-3400-9eab-8e6094a24f9b | 2026-06-02T09:35:21Z | 2026-06-02T09:35:31Z | `{"operationId":"f9ad7128-a04c-3bf6-8b8c-c499e9889a6c","correlationId":"3a234e5b-a734-3fd3-97fe-70b3e6bcaa69","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"d74b6b0d-e14d-3442-9eae-f8367836aacd"}` |
| IntegrationProcessed | cca0cc75-ccdf-37b1-b9e7-d552b901a543 | 2026-06-03T09:35:21Z | 2026-06-03T09:35:31Z | `{"originalEventId":"283b7cb5-334c-3400-9eab-8e6094a24f9b","correlationId":"3a234e5b-a734-3fd3-97fe-70b3e6bcaa69","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | e4507a59-983f-3056-b146-3d116bf48a01 | 2026-08-01T09:35:21Z | 2026-08-01T09:35:31Z | `{"operationId":"ffb50b15-d6b0-3997-b550-3e681eb7422d","correlationId":"d007ad89-21a5-3b29-889f-33cafdc06414","siteId":"SUD","locationId":null,"loanId":"9fb757a1-7516-3494-b8f1-b5f780b52e0c"}` |
| IntegrationProcessed | 6a01ef80-9285-3f22-9efa-e96cb705df57 | 2026-08-01T09:36:21Z | 2026-08-01T09:36:31Z | `{"originalEventId":"e4507a59-983f-3056-b146-3d116bf48a01","correlationId":"d007ad89-21a5-3b29-889f-33cafdc06414","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | e67d62ae-6f9a-3cca-b4fd-3c2ef09373ba | 2026-08-21T09:35:21Z | 2026-08-21T09:35:31Z | `{"operationId":"f0be72ca-91f5-367d-aca9-4d4d1cf48254","correlationId":"f6e4cbcd-b2a7-3639-8df0-aaf73ba87b46","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"9fb757a1-7516-3494-b8f1-b5f780b52e0c"}` |
| ReturnReceived | 59847637-3b70-3896-8e02-5a38cdc75118 | 2026-08-21T09:35:51Z | 2026-08-21T09:36:01Z | `{"operationId":"51681035-a873-3d4d-9263-edda8584d911","correlationId":"819636b2-39ba-3b57-b4e2-1b761bd0fea1","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"9fb757a1-7516-3494-b8f1-b5f780b52e0c"}` |
| IntegrationProcessed | f0fa22d6-eabd-3262-b86f-f8bf07adf5c0 | 2026-08-22T09:35:21Z | 2026-08-22T09:35:31Z | `{"originalEventId":"59847637-3b70-3896-8e02-5a38cdc75118","correlationId":"819636b2-39ba-3b57-b4e2-1b761bd0fea1","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | a3e44f6f-b9d1-3166-86da-5a032221ee63 | 2026-09-27T10:40:45Z | 2026-09-27T10:40:55Z | `{"operationId":"e263a951-fe3e-3b04-9c52-ec2509278dc1","correlationId":"d04bae84-3f9f-39c7-b89e-a9173b5a5eea","siteId":"SUD","locationId":null,"loanId":"ab5ca896-e900-35ef-aae6-1273b06df5fe"}` |
| IntegrationProcessed | 685dc3b3-9d2c-3dcc-940d-37843542bd88 | 2026-09-27T10:41:15Z | 2026-09-27T10:41:25Z | `{"originalEventId":"a3e44f6f-b9d1-3166-86da-5a032221ee63","correlationId":"d04bae84-3f9f-39c7-b89e-a9173b5a5eea","outcome":"FAILED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| IntegrationProcessed | bed11e69-bf57-3266-923a-38c83e6a19c9 | 2026-09-27T10:42:15Z | 2026-09-27T10:42:25Z | `{"originalEventId":"a3e44f6f-b9d1-3166-86da-5a032221ee63","correlationId":"d04bae84-3f9f-39c7-b89e-a9173b5a5eea","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |

**observations**
- `{"observationId":"56dd4959-f745-3171-bf55-df7ff8be859a","copyId":"97a7dc6d-9cbe-39d0-8048-06e94d678f90","locationId":"205b3714-25eb-3b7f-aead-24616287f71a","scopeId":"a41d4e45-576a-3574-8341-bb2ef3a86f22","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**sourceAvailability**
- `{"copyId":"97a7dc6d-9cbe-39d0-8048-06e94d678f90","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"97a7dc6d-9cbe-39d0-8048-06e94d678f90","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"97a7dc6d-9cbe-39d0-8048-06e94d678f90","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"97a7dc6d-9cbe-39d0-8048-06e94d678f90","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"97a7dc6d-9cbe-39d0-8048-06e94d678f90","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"97a7dc6d-9cbe-39d0-8048-06e94d678f90","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"97a7dc6d-9cbe-39d0-8048-06e94d678f90","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"97a7dc6d-9cbe-39d0-8048-06e94d678f90"},{"kind":"sourceEvent","id":"283b7cb5-334c-3400-9eab-8e6094a24f9b"},{"kind":"sourceEvent","id":"cca0cc75-ccdf-37b1-b9e7-d552b901a543"},{"kind":"sourceEvent","id":"e4507a59-983f-3056-b146-3d116bf48a01"},{"kind":"sourceEvent","id":"6a01ef80-9285-3f22-9efa-e96cb705df57"},{"kind":"sourceEvent","id":"e67d62ae-6f9a-3cca-b4fd-3c2ef09373ba"},{"kind":"sourceEvent","id":"59847637-3b70-3896-8e02-5a38cdc75118"},{"kind":"sourceEvent","id":"f0fa22d6-eabd-3262-b86f-f8bf07adf5c0"},{"kind":"sourceEvent","id":"a3e44f6f-b9d1-3166-86da-5a032221ee63"},{"kind":"sourceEvent","id":"685dc3b3-9d2c-3dcc-940d-37843542bd88"},{"kind":"sourceEvent","id":"bed11e69-bf57-3266-923a-38c83e6a19c9"},{"kind":"observation","id":"56dd4959-f745-3171-bf55-df7ff8be859a"},{"kind":"inventoryScope","id":"a41d4e45-576a-3574-8341-bb2ef3a86f22"},{"kind":"sourceAvailability","id":"97a7dc6d-9cbe-39d0-8048-06e94d678f90/LOANS"},{"kind":"sourceAvailability","id":"97a7dc6d-9cbe-39d0-8048-06e94d678f90/RETURNS"},{"kind":"sourceAvailability","id":"97a7dc6d-9cbe-39d0-8048-06e94d678f90/RECEIPTS"},{"kind":"sourceAvailability","id":"97a7dc6d-9cbe-39d0-8048-06e94d678f90/TRANSFERS"},{"kind":"sourceAvailability","id":"97a7dc6d-9cbe-39d0-8048-06e94d678f90/REPAIRS"},{"kind":"sourceAvailability","id":"97a7dc6d-9cbe-39d0-8048-06e94d678f90/INTEGRATIONS"},{"kind":"sourceAvailability","id":"97a7dc6d-9cbe-39d0-8048-06e94d678f90/INVENTORY"}]`

- Session : `{"scopeId":"a41d4e45-576a-3574-8341-bb2ef3a86f22","locationId":"205b3714-25eb-3b7f-aead-24616287f71a","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

## Cas 29e8a572-4d3b-3e7c-8e4d-98e2866b98b0

- Exemplaire : `87120eb8-cbde-37f5-8306-4adc56d148b2`
- Famille privée : TRANSFERT_INCOHERENT ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"78afaed2-c8e6-3a2a-8197-522ed1764c12","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"29d7144c-9c6f-36ac-b65d-20cc183830e1","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"87120eb8-cbde-37f5-8306-4adc56d148b2","status":"RECEIVING","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","sourceEventIds":["29d7144c-9c6f-36ac-b65d-20cc183830e1","69eb8e21-0082-3300-abb9-b24b81ee3b14"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 519f0686-3ebf-3015-a1a8-e50022bcbc3c | 2026-06-02T08:00:26Z | 2026-06-02T08:00:36Z | `{"operationId":"4f3d34a7-7882-3990-95b4-b096dac9d610","correlationId":"38431ffd-6fc3-37d2-8976-d3647359f9a0","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"8689cbc2-a6db-3b4a-ad47-306b85abfc68"}` |
| IntegrationProcessed | 41d24b1a-ddd0-3409-95e9-a3ed94624b21 | 2026-06-03T08:00:26Z | 2026-06-03T08:00:36Z | `{"originalEventId":"519f0686-3ebf-3015-a1a8-e50022bcbc3c","correlationId":"38431ffd-6fc3-37d2-8976-d3647359f9a0","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 769c0c63-64d6-3f5f-be99-fa9699a49f85 | 2026-08-01T08:00:26Z | 2026-08-01T08:00:36Z | `{"operationId":"d3680f01-0d63-3220-bd24-be7546dbae9a","correlationId":"2b0b3f8b-c05a-3d22-801f-cc2f9bad80a1","siteId":"CENTRE","locationId":null,"loanId":"87f44e9b-8a02-3f0b-9854-6bb438a51ec7"}` |
| IntegrationProcessed | bac6cf05-93cd-3f53-bf5b-e3079a9f71e8 | 2026-08-01T08:01:26Z | 2026-08-01T08:01:36Z | `{"originalEventId":"769c0c63-64d6-3f5f-be99-fa9699a49f85","correlationId":"2b0b3f8b-c05a-3d22-801f-cc2f9bad80a1","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | ace005c1-8e02-38b0-86e7-6dff147cc590 | 2026-08-21T08:00:26Z | 2026-08-21T08:00:36Z | `{"operationId":"cdcb5c03-e4c6-3920-883d-549a7d1675e9","correlationId":"93dd1f2f-edd8-3fc5-a091-06d558c84c00","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"87f44e9b-8a02-3f0b-9854-6bb438a51ec7"}` |
| ReturnReceived | 623c6e85-c280-3896-a4d7-ab7cb501d3e8 | 2026-08-21T08:00:56Z | 2026-08-21T08:01:06Z | `{"operationId":"a72233d9-2cbc-3496-b6bc-457bc72b0236","correlationId":"4187124d-42b6-35d6-8574-fe0329893b30","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"87f44e9b-8a02-3f0b-9854-6bb438a51ec7"}` |
| IntegrationProcessed | 09340baa-e945-3a02-99ba-fa99faef0d42 | 2026-08-22T08:00:26Z | 2026-08-22T08:00:36Z | `{"originalEventId":"623c6e85-c280-3896-a4d7-ab7cb501d3e8","correlationId":"4187124d-42b6-35d6-8574-fe0329893b30","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| TransferDispatched | a9325f93-e6e3-3b2b-bc88-735b4fdb392d | 2026-09-26T08:31:48Z | 2026-09-26T08:31:58Z | `{"operationId":"5f92d74a-f0b7-344c-9042-4f4d2ac8694b","correlationId":"86eb5c8f-635b-3c56-9840-38c124a1b570","siteId":"CENTRE","locationId":null,"transferId":"19a3d2d8-c786-3c8e-8794-48c8509b127b","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | ab4fa834-01ad-3aab-8138-2704dce62e26 | 2026-09-26T08:32:48Z | 2026-09-26T08:32:58Z | `{"originalEventId":"a9325f93-e6e3-3b2b-bc88-735b4fdb392d","correlationId":"86eb5c8f-635b-3c56-9840-38c124a1b570","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| TransferReceived | 29d7144c-9c6f-36ac-b65d-20cc183830e1 | 2026-09-27T08:31:48Z | 2026-09-27T08:31:58Z | `{"operationId":"b2a8f109-12ed-335e-8d77-9730be5901c2","correlationId":"946aa99b-582d-3c00-bbca-6f5297e48c30","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","transferId":"19a3d2d8-c786-3c8e-8794-48c8509b127b","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | 3c86bf48-fb44-362b-bbf7-4807daabd5f4 | 2026-09-27T08:32:18Z | 2026-09-27T08:32:28Z | `{"originalEventId":"29d7144c-9c6f-36ac-b65d-20cc183830e1","correlationId":"946aa99b-582d-3c00-bbca-6f5297e48c30","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| IntegrationProcessed | 69eb8e21-0082-3300-abb9-b24b81ee3b14 | 2026-09-27T08:33:18Z | 2026-09-27T08:33:28Z | `{"originalEventId":"29d7144c-9c6f-36ac-b65d-20cc183830e1","correlationId":"946aa99b-582d-3c00-bbca-6f5297e48c30","outcome":"APPLIED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87"}` |

**observations**
- `{"observationId":"b243e56f-e0d2-3ad9-ab6f-90c64060543b","copyId":"87120eb8-cbde-37f5-8306-4adc56d148b2","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","scopeId":"ca717688-0aa5-31d5-b84f-5753b077f0cc","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`
- `{"observationId":"af9ad577-85cf-35d9-900e-8d7e61e247fc","copyId":"87120eb8-cbde-37f5-8306-4adc56d148b2","locationId":"5b61a611-a926-3ed2-82dc-fc122118757c","scopeId":"a2b11c82-4606-3a98-a06e-382d06e9688d","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**sourceAvailability**
- `{"copyId":"87120eb8-cbde-37f5-8306-4adc56d148b2","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"87120eb8-cbde-37f5-8306-4adc56d148b2","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"87120eb8-cbde-37f5-8306-4adc56d148b2","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"87120eb8-cbde-37f5-8306-4adc56d148b2","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"87120eb8-cbde-37f5-8306-4adc56d148b2","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"87120eb8-cbde-37f5-8306-4adc56d148b2","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"87120eb8-cbde-37f5-8306-4adc56d148b2","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"87120eb8-cbde-37f5-8306-4adc56d148b2"},{"kind":"sourceEvent","id":"519f0686-3ebf-3015-a1a8-e50022bcbc3c"},{"kind":"sourceEvent","id":"41d24b1a-ddd0-3409-95e9-a3ed94624b21"},{"kind":"sourceEvent","id":"769c0c63-64d6-3f5f-be99-fa9699a49f85"},{"kind":"sourceEvent","id":"bac6cf05-93cd-3f53-bf5b-e3079a9f71e8"},{"kind":"sourceEvent","id":"ace005c1-8e02-38b0-86e7-6dff147cc590"},{"kind":"sourceEvent","id":"623c6e85-c280-3896-a4d7-ab7cb501d3e8"},{"kind":"sourceEvent","id":"09340baa-e945-3a02-99ba-fa99faef0d42"},{"kind":"sourceEvent","id":"a9325f93-e6e3-3b2b-bc88-735b4fdb392d"},{"kind":"sourceEvent","id":"ab4fa834-01ad-3aab-8138-2704dce62e26"},{"kind":"sourceEvent","id":"29d7144c-9c6f-36ac-b65d-20cc183830e1"},{"kind":"sourceEvent","id":"3c86bf48-fb44-362b-bbf7-4807daabd5f4"},{"kind":"sourceEvent","id":"69eb8e21-0082-3300-abb9-b24b81ee3b14"},{"kind":"observation","id":"b243e56f-e0d2-3ad9-ab6f-90c64060543b"},{"kind":"inventoryScope","id":"ca717688-0aa5-31d5-b84f-5753b077f0cc"},{"kind":"observation","id":"af9ad577-85cf-35d9-900e-8d7e61e247fc"},{"kind":"inventoryScope","id":"a2b11c82-4606-3a98-a06e-382d06e9688d"},{"kind":"sourceAvailability","id":"87120eb8-cbde-37f5-8306-4adc56d148b2/LOANS"},{"kind":"sourceAvailability","id":"87120eb8-cbde-37f5-8306-4adc56d148b2/RETURNS"},{"kind":"sourceAvailability","id":"87120eb8-cbde-37f5-8306-4adc56d148b2/RECEIPTS"},{"kind":"sourceAvailability","id":"87120eb8-cbde-37f5-8306-4adc56d148b2/TRANSFERS"},{"kind":"sourceAvailability","id":"87120eb8-cbde-37f5-8306-4adc56d148b2/REPAIRS"},{"kind":"sourceAvailability","id":"87120eb8-cbde-37f5-8306-4adc56d148b2/INTEGRATIONS"},{"kind":"sourceAvailability","id":"87120eb8-cbde-37f5-8306-4adc56d148b2/INVENTORY"},{"kind":"inventoryScope","id":"74a6fe29-578f-31cb-b1e3-32177945701e"}]`

- Session : `{"scopeId":"ca717688-0aa5-31d5-b84f-5753b077f0cc","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"a2b11c82-4606-3a98-a06e-382d06e9688d","locationId":"5b61a611-a926-3ed2-82dc-fc122118757c","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"74a6fe29-578f-31cb-b1e3-32177945701e","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 2a61a63d-5122-32c2-b068-1732eb9ec9b0

- Exemplaire : `cfc6cd64-eef5-3eb3-a730-3e5671e1fe11`
- Famille privée : REPARATION_NON_PROJETEE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"988b7170-6e10-33a5-b3a9-13345ec584d2","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"0d91eecd-5630-37cc-8b96-3684af6eea18","physicalStatus":"IN_REPAIR","physicalSiteId":"SUD","physicalLocationId":"64a3276c-7791-367a-984b-158e1b652a52","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"cfc6cd64-eef5-3eb3-a730-3e5671e1fe11","status":"IN_REPAIR","siteId":"SUD","locationId":"64a3276c-7791-367a-984b-158e1b652a52","sourceEventIds":["0d91eecd-5630-37cc-8b96-3684af6eea18","5794e4fc-6a66-399d-8d23-5bdda25520c4"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | d037cdbc-185c-35c3-b512-34971e160d83 | 2026-06-02T01:45:48Z | 2026-06-02T01:45:58Z | `{"operationId":"42e1c01c-1f63-32be-8e94-384894d49819","correlationId":"89b01cf2-1786-38c1-999b-9c53fcc84230","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"e6b803e2-26be-3847-b0c5-e2af93feb310"}` |
| IntegrationProcessed | de585814-acfe-3daf-9340-7b3bbad8d71e | 2026-06-03T01:45:48Z | 2026-06-03T01:45:58Z | `{"originalEventId":"d037cdbc-185c-35c3-b512-34971e160d83","correlationId":"89b01cf2-1786-38c1-999b-9c53fcc84230","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 85b6659e-a320-32e7-9366-dfc7961dfdba | 2026-08-01T01:45:48Z | 2026-08-01T01:45:58Z | `{"operationId":"eda8ca91-2b10-39ef-9c4e-a8f213572ed5","correlationId":"da6efe01-eff3-3634-bb4d-1900975a2dc2","siteId":"NORD","locationId":null,"loanId":"0ec9d9e3-1d45-32ff-a591-027c35334788"}` |
| IntegrationProcessed | 7b29dd5b-bcc8-3548-a93f-2c0a9ba55f48 | 2026-08-01T01:46:48Z | 2026-08-01T01:46:58Z | `{"originalEventId":"85b6659e-a320-32e7-9366-dfc7961dfdba","correlationId":"da6efe01-eff3-3634-bb4d-1900975a2dc2","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | 4d3784e0-a160-3519-ab85-e37d135f30eb | 2026-08-21T01:45:48Z | 2026-08-21T01:45:58Z | `{"operationId":"01e805ee-999e-372f-8bfb-afad61c30526","correlationId":"fecc8070-f6ec-3d34-bf29-d4b041f00657","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"0ec9d9e3-1d45-32ff-a591-027c35334788"}` |
| ReturnReceived | 60d05657-25db-3d5d-8d1e-d11924ee35fa | 2026-08-21T01:46:18Z | 2026-08-21T01:46:28Z | `{"operationId":"f76bc00a-8276-35d0-ad37-7b02f60d977f","correlationId":"e94b07cb-2fef-3232-86b1-8ecd3f0f63b5","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"0ec9d9e3-1d45-32ff-a591-027c35334788"}` |
| IntegrationProcessed | ac8cd6a5-f027-3e75-a4f0-1b1e08e2989a | 2026-08-22T01:45:48Z | 2026-08-22T01:45:58Z | `{"originalEventId":"60d05657-25db-3d5d-8d1e-d11924ee35fa","correlationId":"e94b07cb-2fef-3232-86b1-8ecd3f0f63b5","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| TransferDispatched | 29f7779b-61df-3cc8-ad3c-1fc8da09319e | 2026-08-31T01:45:48Z | 2026-08-31T01:45:58Z | `{"operationId":"7ae7895b-de84-359b-bb44-d9be5e2286a7","correlationId":"21dbb20b-4eba-3abc-ba9e-1225be8c3c14","siteId":"NORD","locationId":null,"transferId":"839ef9ec-7194-355c-b975-72a486684835","fromSiteId":"NORD","toSiteId":"SUD"}` |
| IntegrationProcessed | 55cdf988-ec2e-3679-a288-a31af5c75067 | 2026-08-31T01:46:48Z | 2026-08-31T01:46:58Z | `{"originalEventId":"29f7779b-61df-3cc8-ad3c-1fc8da09319e","correlationId":"21dbb20b-4eba-3abc-ba9e-1225be8c3c14","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"NORD","locationId":null}` |
| TransferReceived | ef20c3df-b11a-3393-9363-c40dcd2fe243 | 2026-09-01T01:45:48Z | 2026-09-01T01:45:58Z | `{"operationId":"c402c679-1cbe-3403-89ac-36584f4762c0","correlationId":"56dd26e1-ef68-3d2c-b100-cd31d4133698","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","transferId":"839ef9ec-7194-355c-b975-72a486684835","fromSiteId":"NORD","toSiteId":"SUD"}` |
| IntegrationProcessed | e0dce9a6-4bdc-3feb-a23b-f7f8ed22e21b | 2026-09-02T01:45:48Z | 2026-09-02T01:45:58Z | `{"originalEventId":"ef20c3df-b11a-3393-9363-c40dcd2fe243","correlationId":"56dd26e1-ef68-3d2c-b100-cd31d4133698","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| RepairStarted | 0d91eecd-5630-37cc-8b96-3684af6eea18 | 2026-09-27T11:56:08Z | 2026-09-27T11:56:18Z | `{"operationId":"9e5e300e-6166-3b41-b085-68594dca153c","correlationId":"df52aad5-08ff-3f65-90cb-bbed616e35c1","siteId":"SUD","locationId":"64a3276c-7791-367a-984b-158e1b652a52","repairId":"fa9b1e6b-abb9-3ddc-a42b-f4729aeb398a"}` |
| IntegrationProcessed | 63717166-599d-3f50-a0a1-bf80e47f944f | 2026-09-27T11:56:38Z | 2026-09-27T11:56:48Z | `{"originalEventId":"0d91eecd-5630-37cc-8b96-3684af6eea18","correlationId":"df52aad5-08ff-3f65-90cb-bbed616e35c1","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"SUD","locationId":"64a3276c-7791-367a-984b-158e1b652a52","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| IntegrationProcessed | 5794e4fc-6a66-399d-8d23-5bdda25520c4 | 2026-09-27T11:57:38Z | 2026-09-27T11:57:48Z | `{"originalEventId":"0d91eecd-5630-37cc-8b96-3684af6eea18","correlationId":"df52aad5-08ff-3f65-90cb-bbed616e35c1","outcome":"APPLIED","projectedStatus":"IN_REPAIR","siteId":"SUD","locationId":"64a3276c-7791-367a-984b-158e1b652a52"}` |

**observations**
- `{"observationId":"f4648c71-f571-395a-837b-09302fa91322","copyId":"cfc6cd64-eef5-3eb3-a730-3e5671e1fe11","locationId":"205b3714-25eb-3b7f-aead-24616287f71a","scopeId":"66424e7f-3088-3243-b309-5d7b921368d1","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`
- `{"observationId":"1610489a-43d8-360c-b274-3ec29f325b9f","copyId":"cfc6cd64-eef5-3eb3-a730-3e5671e1fe11","locationId":"64a3276c-7791-367a-984b-158e1b652a52","scopeId":"44f187ae-1c67-3cb8-8200-609dc747e27b","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"cfc6cd64-eef5-3eb3-a730-3e5671e1fe11","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cfc6cd64-eef5-3eb3-a730-3e5671e1fe11","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cfc6cd64-eef5-3eb3-a730-3e5671e1fe11","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cfc6cd64-eef5-3eb3-a730-3e5671e1fe11","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cfc6cd64-eef5-3eb3-a730-3e5671e1fe11","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cfc6cd64-eef5-3eb3-a730-3e5671e1fe11","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"cfc6cd64-eef5-3eb3-a730-3e5671e1fe11","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"cfc6cd64-eef5-3eb3-a730-3e5671e1fe11"},{"kind":"sourceEvent","id":"d037cdbc-185c-35c3-b512-34971e160d83"},{"kind":"sourceEvent","id":"de585814-acfe-3daf-9340-7b3bbad8d71e"},{"kind":"sourceEvent","id":"85b6659e-a320-32e7-9366-dfc7961dfdba"},{"kind":"sourceEvent","id":"7b29dd5b-bcc8-3548-a93f-2c0a9ba55f48"},{"kind":"sourceEvent","id":"4d3784e0-a160-3519-ab85-e37d135f30eb"},{"kind":"sourceEvent","id":"60d05657-25db-3d5d-8d1e-d11924ee35fa"},{"kind":"sourceEvent","id":"ac8cd6a5-f027-3e75-a4f0-1b1e08e2989a"},{"kind":"sourceEvent","id":"29f7779b-61df-3cc8-ad3c-1fc8da09319e"},{"kind":"sourceEvent","id":"55cdf988-ec2e-3679-a288-a31af5c75067"},{"kind":"sourceEvent","id":"ef20c3df-b11a-3393-9363-c40dcd2fe243"},{"kind":"sourceEvent","id":"e0dce9a6-4bdc-3feb-a23b-f7f8ed22e21b"},{"kind":"sourceEvent","id":"0d91eecd-5630-37cc-8b96-3684af6eea18"},{"kind":"sourceEvent","id":"63717166-599d-3f50-a0a1-bf80e47f944f"},{"kind":"sourceEvent","id":"5794e4fc-6a66-399d-8d23-5bdda25520c4"},{"kind":"observation","id":"1610489a-43d8-360c-b274-3ec29f325b9f"},{"kind":"inventoryScope","id":"44f187ae-1c67-3cb8-8200-609dc747e27b"},{"kind":"observation","id":"f4648c71-f571-395a-837b-09302fa91322"},{"kind":"inventoryScope","id":"66424e7f-3088-3243-b309-5d7b921368d1"},{"kind":"sourceAvailability","id":"cfc6cd64-eef5-3eb3-a730-3e5671e1fe11/LOANS"},{"kind":"sourceAvailability","id":"cfc6cd64-eef5-3eb3-a730-3e5671e1fe11/RETURNS"},{"kind":"sourceAvailability","id":"cfc6cd64-eef5-3eb3-a730-3e5671e1fe11/RECEIPTS"},{"kind":"sourceAvailability","id":"cfc6cd64-eef5-3eb3-a730-3e5671e1fe11/TRANSFERS"},{"kind":"sourceAvailability","id":"cfc6cd64-eef5-3eb3-a730-3e5671e1fe11/REPAIRS"},{"kind":"sourceAvailability","id":"cfc6cd64-eef5-3eb3-a730-3e5671e1fe11/INTEGRATIONS"},{"kind":"sourceAvailability","id":"cfc6cd64-eef5-3eb3-a730-3e5671e1fe11/INVENTORY"},{"kind":"inventoryScope","id":"ac80cfd2-4d1b-37ad-b3fe-eab57127cf6d"}]`

- Session : `{"scopeId":"44f187ae-1c67-3cb8-8200-609dc747e27b","locationId":"64a3276c-7791-367a-984b-158e1b652a52","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"66424e7f-3088-3243-b309-5d7b921368d1","locationId":"205b3714-25eb-3b7f-aead-24616287f71a","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"ac80cfd2-4d1b-37ad-b3fe-eab57127cf6d","locationId":"64a3276c-7791-367a-984b-158e1b652a52","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 2b68aa38-209e-3811-a2bb-53777cd4f25c

- Exemplaire : `73109250-d188-382c-beb8-375944d07c26`
- Famille privée : INVENTAIRE_INCOMPLET ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"f7447ec0-a953-3fd8-b582-7d3211574bd3","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":"1"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"mechanism":"Exemplaire resté au rayon, lecture de contrôle réussie.","physicalStatus":"ON_SHELF","physicalSiteId":"CENTRE","physicalLocationId":"4a5364f2-388b-351d-91f2-d1364d119042","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"Le scan valide au rayon attendu réfute l'absence courante.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"73109250-d188-382c-beb8-375944d07c26","status":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","sourceEventIds":["f076c02b-26f0-316f-87b5-4540cacb9eac","d768b0e1-60b0-3b63-bef6-2cbe4027e48c"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 85cba2af-28ac-3073-b84a-d408d2bd453c | 2026-06-02T07:48:35Z | 2026-06-02T07:48:45Z | `{"operationId":"f5973004-6bed-3b72-b83c-4d7e230433fc","correlationId":"5d44b254-9680-323f-b1d8-f90527bf41d6","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"881e994a-e398-3ff5-bc8c-1bc3d2a55870"}` |
| IntegrationProcessed | b6e522ba-fc12-3dc2-a88c-6eb3d93dba4a | 2026-06-03T07:48:35Z | 2026-06-03T07:48:45Z | `{"originalEventId":"85cba2af-28ac-3073-b84a-d408d2bd453c","correlationId":"5d44b254-9680-323f-b1d8-f90527bf41d6","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 6695c650-8827-322a-a575-536aee8e201a | 2026-08-01T07:48:35Z | 2026-08-01T07:48:45Z | `{"operationId":"933aed41-da65-3dad-8926-8cd6b743bc21","correlationId":"a35666a7-506c-3796-a64b-5381ba4056e7","siteId":"CENTRE","locationId":null,"loanId":"73d5e077-7f78-36d9-b603-178c17a4c8d6"}` |
| IntegrationProcessed | e7f1295b-d0e8-3891-9ae3-40fd2504edc7 | 2026-08-01T07:49:35Z | 2026-08-01T07:49:45Z | `{"originalEventId":"6695c650-8827-322a-a575-536aee8e201a","correlationId":"a35666a7-506c-3796-a64b-5381ba4056e7","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 9404395d-9b19-365b-b626-9332d5a7b2db | 2026-08-21T07:48:35Z | 2026-08-21T07:48:45Z | `{"operationId":"2a5d02fa-99a4-33f8-a172-8916a4f001eb","correlationId":"3653cb1b-4851-3d25-b6b1-3dbdf1d07ad4","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"73d5e077-7f78-36d9-b603-178c17a4c8d6"}` |
| ReturnReceived | f076c02b-26f0-316f-87b5-4540cacb9eac | 2026-08-21T07:49:05Z | 2026-08-21T07:49:15Z | `{"operationId":"0d0dc668-51fd-304c-9a4b-85aca4e471c8","correlationId":"1d81b7d6-7f26-3a65-a8a2-8ef61176aef7","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"73d5e077-7f78-36d9-b603-178c17a4c8d6"}` |
| IntegrationProcessed | d768b0e1-60b0-3b63-bef6-2cbe4027e48c | 2026-08-22T07:48:35Z | 2026-08-22T07:48:45Z | `{"originalEventId":"f076c02b-26f0-316f-87b5-4540cacb9eac","correlationId":"1d81b7d6-7f26-3a65-a8a2-8ef61176aef7","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |

**observations**
- `{"observationId":"e1c34824-8a35-3649-a58e-d65cf1ac8e49","copyId":"73109250-d188-382c-beb8-375944d07c26","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","scopeId":"40e94818-3a62-34a4-b461-398d7b9ffca2","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"73109250-d188-382c-beb8-375944d07c26","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"73109250-d188-382c-beb8-375944d07c26","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"73109250-d188-382c-beb8-375944d07c26","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"73109250-d188-382c-beb8-375944d07c26","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"73109250-d188-382c-beb8-375944d07c26","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"73109250-d188-382c-beb8-375944d07c26","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"73109250-d188-382c-beb8-375944d07c26","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**
- `{"noteId":"0df826ed-6a1c-3554-afd0-f5fb5cf563f1","copyId":"73109250-d188-382c-beb8-375944d07c26","text":"Nouvelle lecture du code-barres effectuée dans le rayon affecté.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"73109250-d188-382c-beb8-375944d07c26"},{"kind":"sourceEvent","id":"85cba2af-28ac-3073-b84a-d408d2bd453c"},{"kind":"sourceEvent","id":"b6e522ba-fc12-3dc2-a88c-6eb3d93dba4a"},{"kind":"sourceEvent","id":"6695c650-8827-322a-a575-536aee8e201a"},{"kind":"sourceEvent","id":"e7f1295b-d0e8-3891-9ae3-40fd2504edc7"},{"kind":"sourceEvent","id":"9404395d-9b19-365b-b626-9332d5a7b2db"},{"kind":"sourceEvent","id":"f076c02b-26f0-316f-87b5-4540cacb9eac"},{"kind":"sourceEvent","id":"d768b0e1-60b0-3b63-bef6-2cbe4027e48c"},{"kind":"observation","id":"e1c34824-8a35-3649-a58e-d65cf1ac8e49"},{"kind":"inventoryScope","id":"40e94818-3a62-34a4-b461-398d7b9ffca2"},{"kind":"sourceAvailability","id":"73109250-d188-382c-beb8-375944d07c26/LOANS"},{"kind":"sourceAvailability","id":"73109250-d188-382c-beb8-375944d07c26/RETURNS"},{"kind":"sourceAvailability","id":"73109250-d188-382c-beb8-375944d07c26/RECEIPTS"},{"kind":"sourceAvailability","id":"73109250-d188-382c-beb8-375944d07c26/TRANSFERS"},{"kind":"sourceAvailability","id":"73109250-d188-382c-beb8-375944d07c26/REPAIRS"},{"kind":"sourceAvailability","id":"73109250-d188-382c-beb8-375944d07c26/INTEGRATIONS"},{"kind":"sourceAvailability","id":"73109250-d188-382c-beb8-375944d07c26/INVENTORY"},{"kind":"operatorNote","id":"0df826ed-6a1c-3554-afd0-f5fb5cf563f1"},{"kind":"inventoryScope","id":"8021dca8-65fe-3cab-985e-d4fb9df5c308"},{"kind":"inventoryScope","id":"b0027657-6abb-37d2-8bc4-9341357dfe79"},{"kind":"inventoryScope","id":"4757f5eb-f104-3413-b118-51a6951a650c"},{"kind":"inventoryScope","id":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592"},{"kind":"inventoryScope","id":"40e94818-3a62-34a4-b461-398d7b9ffca2"}]`

- Session : `{"scopeId":"40e94818-3a62-34a4-b461-398d7b9ffca2","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"8021dca8-65fe-3cab-985e-d4fb9df5c308","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b0027657-6abb-37d2-8bc4-9341357dfe79","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4757f5eb-f104-3413-b118-51a6951a650c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"40e94818-3a62-34a4-b461-398d7b9ffca2","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 2ecedda5-796e-39b5-a083-b86075759579

- Exemplaire : `5f9b447b-4f1f-3780-9585-6927b66a6a08`
- Famille privée : RECEPTION_NON_INTEGREE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"fcc025ad-2fbb-3f44-bcc7-c9da85f4340c","kind":"UNEXPECTED_PRESENCE","expectedValue":"NOT_REGISTERED","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"f16a35f4-3c6b-3cce-9bce-2e60ad4bf143","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["RECEIPT_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"5f9b447b-4f1f-3780-9585-6927b66a6a08","status":"NOT_REGISTERED","siteId":"CENTRE","locationId":null,"sourceEventIds":[]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | f16a35f4-3c6b-3cce-9bce-2e60ad4bf143 | 2026-09-27T09:04:48Z | 2026-09-27T09:04:58Z | `{"operationId":"f5d82aaa-2b07-30e5-ba27-b4cad24d3848","correlationId":"a3a67157-18f0-3c23-af1b-a9ba8e037268","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"91abd68d-7782-3214-9da9-e5f5c0faf8fc"}` |
| IntegrationProcessed | cd597b9f-7378-3f35-bf39-66735dd960a2 | 2026-09-27T09:05:18Z | 2026-09-27T09:05:28Z | `{"originalEventId":"f16a35f4-3c6b-3cce-9bce-2e60ad4bf143","correlationId":"a3a67157-18f0-3c23-af1b-a9ba8e037268","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**observations**
- `{"observationId":"065338fa-5f94-3572-9b2a-bf54775bce2b","copyId":"5f9b447b-4f1f-3780-9585-6927b66a6a08","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","scopeId":"57ee798c-ebfa-3b42-9818-10132c6f964d","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"5f9b447b-4f1f-3780-9585-6927b66a6a08","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5f9b447b-4f1f-3780-9585-6927b66a6a08","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5f9b447b-4f1f-3780-9585-6927b66a6a08","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5f9b447b-4f1f-3780-9585-6927b66a6a08","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5f9b447b-4f1f-3780-9585-6927b66a6a08","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5f9b447b-4f1f-3780-9585-6927b66a6a08","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5f9b447b-4f1f-3780-9585-6927b66a6a08","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"5f9b447b-4f1f-3780-9585-6927b66a6a08"},{"kind":"sourceEvent","id":"f16a35f4-3c6b-3cce-9bce-2e60ad4bf143"},{"kind":"sourceEvent","id":"cd597b9f-7378-3f35-bf39-66735dd960a2"},{"kind":"observation","id":"065338fa-5f94-3572-9b2a-bf54775bce2b"},{"kind":"inventoryScope","id":"57ee798c-ebfa-3b42-9818-10132c6f964d"},{"kind":"sourceAvailability","id":"5f9b447b-4f1f-3780-9585-6927b66a6a08/LOANS"},{"kind":"sourceAvailability","id":"5f9b447b-4f1f-3780-9585-6927b66a6a08/RETURNS"},{"kind":"sourceAvailability","id":"5f9b447b-4f1f-3780-9585-6927b66a6a08/RECEIPTS"},{"kind":"sourceAvailability","id":"5f9b447b-4f1f-3780-9585-6927b66a6a08/TRANSFERS"},{"kind":"sourceAvailability","id":"5f9b447b-4f1f-3780-9585-6927b66a6a08/REPAIRS"},{"kind":"sourceAvailability","id":"5f9b447b-4f1f-3780-9585-6927b66a6a08/INTEGRATIONS"},{"kind":"sourceAvailability","id":"5f9b447b-4f1f-3780-9585-6927b66a6a08/INVENTORY"}]`

- Session : `{"scopeId":"57ee798c-ebfa-3b42-9818-10132c6f964d","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 2f42e348-69ae-3867-acb9-1f7a1134180c

- Exemplaire : `f6e4b1ea-16ea-3e41-8c69-223ff1e62918`
- Famille privée : LOCALISATION ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"38a5cc1a-b57f-37b4-8ad8-c5985009d3e9","kind":"COUNT_UNCERTAIN","expectedValue":"4a5364f2-388b-351d-91f2-d1364d119042","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"mechanism":"Aucun déplacement ; signal de collecte non confirmé.","activePerturbations":[],"physicalStatus":"ON_SHELF","physicalSiteId":"CENTRE","physicalLocationId":"4a5364f2-388b-351d-91f2-d1364d119042"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"Le scan valide confirme l'emplacement attendu ; aucun écart de localisation étayé.","forbiddenClaims":["Vol","Perte définitive","Responsabilité d'un opérateur"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"f6e4b1ea-16ea-3e41-8c69-223ff1e62918","status":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","sourceEventIds":["370db788-234d-3c9a-b7ca-a033fe4124b4","1f15b6e3-c439-3a0d-bb16-2326b0522d68"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | caddc8bf-63f9-3537-8647-65f94084715e | 2026-06-02T00:10:31Z | 2026-06-02T00:10:41Z | `{"operationId":"29521273-9369-388e-a0ed-093f04582a36","correlationId":"fd1df12a-b57b-327b-8218-5a8f4a9edcd4","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"f4f48045-93c5-3a16-8dbb-9141eb07fbba"}` |
| IntegrationProcessed | e5a9ef41-3b20-3137-ba9d-3fe676eb5043 | 2026-06-03T00:10:31Z | 2026-06-03T00:10:41Z | `{"originalEventId":"caddc8bf-63f9-3537-8647-65f94084715e","correlationId":"fd1df12a-b57b-327b-8218-5a8f4a9edcd4","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | e030dfaa-9e74-3c41-a640-dd3362865215 | 2026-08-01T00:10:31Z | 2026-08-01T00:10:41Z | `{"operationId":"4cf60096-639b-349c-b278-53f938d63075","correlationId":"9893a85b-528c-3415-9606-f83d3cd2910c","siteId":"CENTRE","locationId":null,"loanId":"16e9d77e-31f7-33cf-88ec-948fbe4cd3e9"}` |
| IntegrationProcessed | 0184080b-5b2f-3740-8a06-6530b29b1166 | 2026-08-01T00:11:31Z | 2026-08-01T00:11:41Z | `{"originalEventId":"e030dfaa-9e74-3c41-a640-dd3362865215","correlationId":"9893a85b-528c-3415-9606-f83d3cd2910c","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 35584ef0-85f1-3225-8369-d3830084a309 | 2026-08-21T00:10:31Z | 2026-08-21T00:10:41Z | `{"operationId":"a2db80f5-7664-3795-ad84-ed09e45ec147","correlationId":"ed4fafc2-d099-3abf-aa73-e1fe8f320802","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"16e9d77e-31f7-33cf-88ec-948fbe4cd3e9"}` |
| ReturnReceived | 370db788-234d-3c9a-b7ca-a033fe4124b4 | 2026-08-21T00:11:01Z | 2026-08-21T00:11:11Z | `{"operationId":"5c164367-33b5-3cbc-ad09-0d190c11185b","correlationId":"97e44e3d-242d-3d68-a3c1-ff82f42da396","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"16e9d77e-31f7-33cf-88ec-948fbe4cd3e9"}` |
| IntegrationProcessed | 1f15b6e3-c439-3a0d-bb16-2326b0522d68 | 2026-08-22T00:10:31Z | 2026-08-22T00:10:41Z | `{"originalEventId":"370db788-234d-3c9a-b7ca-a033fe4124b4","correlationId":"97e44e3d-242d-3d68-a3c1-ff82f42da396","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |

**observations**
- `{"observationId":"0b7bd9f1-166c-364a-882c-c5ed23bc4d1b","copyId":"f6e4b1ea-16ea-3e41-8c69-223ff1e62918","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","scopeId":"4757f5eb-f104-3413-b118-51a6951a650c","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"f6e4b1ea-16ea-3e41-8c69-223ff1e62918","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f6e4b1ea-16ea-3e41-8c69-223ff1e62918","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f6e4b1ea-16ea-3e41-8c69-223ff1e62918","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f6e4b1ea-16ea-3e41-8c69-223ff1e62918","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f6e4b1ea-16ea-3e41-8c69-223ff1e62918","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f6e4b1ea-16ea-3e41-8c69-223ff1e62918","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f6e4b1ea-16ea-3e41-8c69-223ff1e62918","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"observation","id":"0b7bd9f1-166c-364a-882c-c5ed23bc4d1b"},{"kind":"systemState","id":"f6e4b1ea-16ea-3e41-8c69-223ff1e62918"}]`

## Cas 31cecdd1-b0e2-3956-a037-68b51a0f43c1

- Exemplaire : `09ea98aa-433b-32c2-9c77-ea51e233d529`
- Famille privée : LOCALISATION ; variante : POSITIVE
- Symptômes : `[{"symptomId":"f5c1a598-a9c9-3219-b16f-017ee9988b00","kind":"LOCATION_MISMATCH","expectedValue":"4a5364f2-388b-351d-91f2-d1364d119042","observedValue":"b8600ac8-b5d3-3580-b3c5-71a711df1a50"}]`
- Vérité injectée (non accessible au diagnostic) : `{"mechanism":"Déplacement physique sans modification de l'affectation ; aucune attribution à un opérateur.","activePerturbations":["LOCATION_CHANGE_UNPROJECTED"],"physicalStatus":"ON_SHELF","physicalSiteId":"CENTRE","physicalLocationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["LOCATION_MISMATCH"],"explanation":"Observation unique valide à un autre emplacement du même site ; écart établi, origine humaine non prouvée.","forbiddenClaims":["Vol","Perte définitive","Responsabilité d'un opérateur"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"09ea98aa-433b-32c2-9c77-ea51e233d529","status":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","sourceEventIds":["f0da20a4-07b5-3ed6-aa0e-116595841ce0","8ae207da-c6c2-3180-b972-22fa59df90df"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | bd28b92f-1d3c-30c1-812e-bc9d22e748ab | 2026-06-01T17:52:58Z | 2026-06-01T17:53:08Z | `{"operationId":"1c7d6756-52ef-39c0-a57e-d05673e607ab","correlationId":"8c377abd-f0ea-33f1-b75a-ca68f36de079","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"d054878e-d1ee-3f37-9cb0-c241d6bd5536"}` |
| IntegrationProcessed | 7a57a675-8d80-3c01-a079-217166d7dcae | 2026-06-02T17:52:58Z | 2026-06-02T17:53:08Z | `{"originalEventId":"bd28b92f-1d3c-30c1-812e-bc9d22e748ab","correlationId":"8c377abd-f0ea-33f1-b75a-ca68f36de079","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | d303ac89-6478-344c-b366-79cd0b43fa2b | 2026-07-31T17:52:58Z | 2026-07-31T17:53:08Z | `{"operationId":"c5eebeb8-d537-3460-a791-d8f1cf85b250","correlationId":"0ae3bb3e-479d-3081-b380-2e567ab43219","siteId":"CENTRE","locationId":null,"loanId":"1e2e63d8-0c37-3402-988d-04b4d51c8eea"}` |
| IntegrationProcessed | a1c08b4f-7a33-38dd-af07-33d90a2e4b53 | 2026-07-31T17:53:58Z | 2026-07-31T17:54:08Z | `{"originalEventId":"d303ac89-6478-344c-b366-79cd0b43fa2b","correlationId":"0ae3bb3e-479d-3081-b380-2e567ab43219","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 328a8508-2dda-391b-ba24-75953de25d5c | 2026-08-20T17:52:58Z | 2026-08-20T17:53:08Z | `{"operationId":"fdd056ff-bfc0-39f3-93fd-13cd4706a470","correlationId":"440ac3fb-6bbe-3c65-ada5-52576e1975b5","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"1e2e63d8-0c37-3402-988d-04b4d51c8eea"}` |
| ReturnReceived | f0da20a4-07b5-3ed6-aa0e-116595841ce0 | 2026-08-20T17:53:28Z | 2026-08-20T17:53:38Z | `{"operationId":"0972cb09-90bc-3dbd-aa5b-388610e5654a","correlationId":"a859533d-6311-312b-bb29-daba7224d295","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"1e2e63d8-0c37-3402-988d-04b4d51c8eea"}` |
| IntegrationProcessed | 8ae207da-c6c2-3180-b972-22fa59df90df | 2026-08-21T17:52:58Z | 2026-08-21T17:53:08Z | `{"originalEventId":"f0da20a4-07b5-3ed6-aa0e-116595841ce0","correlationId":"a859533d-6311-312b-bb29-daba7224d295","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |

**observations**
- `{"observationId":"5cae440b-4400-3b4b-a01b-3ba6458f6e65","copyId":"09ea98aa-433b-32c2-9c77-ea51e233d529","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","scopeId":"49b4c505-5655-3633-b7e5-47e942e94be9","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"09ea98aa-433b-32c2-9c77-ea51e233d529","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"09ea98aa-433b-32c2-9c77-ea51e233d529","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"09ea98aa-433b-32c2-9c77-ea51e233d529","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"09ea98aa-433b-32c2-9c77-ea51e233d529","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"09ea98aa-433b-32c2-9c77-ea51e233d529","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"09ea98aa-433b-32c2-9c77-ea51e233d529","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"09ea98aa-433b-32c2-9c77-ea51e233d529","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"observation","id":"5cae440b-4400-3b4b-a01b-3ba6458f6e65"},{"kind":"systemState","id":"09ea98aa-433b-32c2-9c77-ea51e233d529"}]`

## Cas 34ba2b6f-92f3-37f6-82c5-8c217159a9ae

- Exemplaire : `bc2feadc-d7b3-3f7e-b7af-42707aa311a4`
- Famille privée : PRET_NON_PROJETE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"69f88a45-327d-3a3a-829b-c01612bf4667","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"ae61975e-1329-3961-b4f0-d2bd9d8dde7b","physicalStatus":"ON_LOAN","physicalSiteId":"CENTRE","physicalLocationId":null,"projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"bc2feadc-d7b3-3f7e-b7af-42707aa311a4","status":"ON_LOAN","siteId":"CENTRE","locationId":null,"sourceEventIds":["ae61975e-1329-3961-b4f0-d2bd9d8dde7b","f4a55b08-87d4-3920-8d0a-bdd03ae59c2b"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | c415c82d-55e1-3919-ada3-231da2270007 | 2026-06-02T07:54:43Z | 2026-06-02T07:54:53Z | `{"operationId":"2428f956-5be8-36ef-909d-0bc1cc5fc5c3","correlationId":"d2f23e90-7861-33a7-abfd-a1edbaa882ae","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"5f9cdbd5-b1c8-3a8d-ac2f-6e5121e694cf"}` |
| IntegrationProcessed | 340d4e14-558c-32a3-9230-593b8305a9e0 | 2026-06-03T07:54:43Z | 2026-06-03T07:54:53Z | `{"originalEventId":"c415c82d-55e1-3919-ada3-231da2270007","correlationId":"d2f23e90-7861-33a7-abfd-a1edbaa882ae","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | 76887e92-e089-3310-a4cf-929ffa1ff092 | 2026-08-01T07:54:43Z | 2026-08-01T07:54:53Z | `{"operationId":"ffcbc9d5-7094-3ee5-954c-96cd1cec0baa","correlationId":"bb2ab801-a034-349f-955c-222e9a812f36","siteId":"SUD","locationId":null,"loanId":"a7f2b408-8ab6-37ee-9c52-56613eaec5e4"}` |
| IntegrationProcessed | a6d405bc-22b3-3d1f-84d4-843139b2eaad | 2026-08-01T07:55:43Z | 2026-08-01T07:55:53Z | `{"originalEventId":"76887e92-e089-3310-a4cf-929ffa1ff092","correlationId":"bb2ab801-a034-349f-955c-222e9a812f36","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | fe69996c-8a22-3ef5-a7ec-79c8fa80a560 | 2026-08-21T07:54:43Z | 2026-08-21T07:54:53Z | `{"operationId":"612adc05-3689-3883-9957-20d91518d7a9","correlationId":"013f8896-7e39-32ee-88b7-7a8f49aea65c","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"a7f2b408-8ab6-37ee-9c52-56613eaec5e4"}` |
| ReturnReceived | d7d7f6c8-0595-3cdd-99c9-65b93aa4e998 | 2026-08-21T07:55:13Z | 2026-08-21T07:55:23Z | `{"operationId":"41649ce2-c9f1-32df-8441-111a64408003","correlationId":"cb02e38b-2a89-3128-9e6d-258e2ba92dca","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"a7f2b408-8ab6-37ee-9c52-56613eaec5e4"}` |
| IntegrationProcessed | 99a3b75f-61aa-37b0-a9cb-8f270282f272 | 2026-08-22T07:54:43Z | 2026-08-22T07:54:53Z | `{"originalEventId":"d7d7f6c8-0595-3cdd-99c9-65b93aa4e998","correlationId":"cb02e38b-2a89-3128-9e6d-258e2ba92dca","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| TransferDispatched | a8fd90cc-7a86-383e-9b37-d073a69bb6d3 | 2026-08-31T07:54:43Z | 2026-08-31T07:54:53Z | `{"operationId":"c552771a-a36b-30f4-abef-64d2e3178581","correlationId":"282b3a38-f859-3d17-83ad-66c494984859","siteId":"SUD","locationId":null,"transferId":"4ae8b8f4-475b-3846-9933-b738fc9c5e51","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| IntegrationProcessed | 74fa4a4e-74f3-3f97-af03-7fe0e71eda35 | 2026-08-31T07:55:43Z | 2026-08-31T07:55:53Z | `{"originalEventId":"a8fd90cc-7a86-383e-9b37-d073a69bb6d3","correlationId":"282b3a38-f859-3d17-83ad-66c494984859","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"SUD","locationId":null}` |
| TransferReceived | a9ba29a4-7b69-309f-8ffc-2dc4a938736e | 2026-09-01T07:54:43Z | 2026-09-01T07:54:53Z | `{"operationId":"1ba734ab-a03d-390e-b893-c721957b42f5","correlationId":"40b06f6a-2089-3759-bdc7-bd9b2e942327","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","transferId":"4ae8b8f4-475b-3846-9933-b738fc9c5e51","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| IntegrationProcessed | 4fcdd671-3274-32e4-8e89-e14f6b0d6ba5 | 2026-09-02T07:54:43Z | 2026-09-02T07:54:53Z | `{"originalEventId":"a9ba29a4-7b69-309f-8ffc-2dc4a938736e","correlationId":"40b06f6a-2089-3759-bdc7-bd9b2e942327","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | ae61975e-1329-3961-b4f0-d2bd9d8dde7b | 2026-09-27T09:53:52Z | 2026-09-27T09:54:02Z | `{"operationId":"363d97a0-ce41-3a0a-84be-1a7dd75e2f7b","correlationId":"e8138adc-b24d-3ba8-bc8e-349acf0a57dd","siteId":"CENTRE","locationId":null,"loanId":"ceb16ff5-06c3-359b-94e4-ffb1964b0d76"}` |
| IntegrationProcessed | 3581ab3e-9469-3ea5-b3ec-79fb66e3db4d | 2026-09-27T09:54:22Z | 2026-09-27T09:54:32Z | `{"originalEventId":"ae61975e-1329-3961-b4f0-d2bd9d8dde7b","correlationId":"e8138adc-b24d-3ba8-bc8e-349acf0a57dd","outcome":"FAILED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| IntegrationProcessed | f4a55b08-87d4-3920-8d0a-bdd03ae59c2b | 2026-09-27T09:55:22Z | 2026-09-27T09:55:32Z | `{"originalEventId":"ae61975e-1329-3961-b4f0-d2bd9d8dde7b","correlationId":"e8138adc-b24d-3ba8-bc8e-349acf0a57dd","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |

**observations**
- `{"observationId":"6f14b4ed-f0f2-3170-8e50-516435f5b89c","copyId":"bc2feadc-d7b3-3f7e-b7af-42707aa311a4","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","scopeId":"49be3af5-e9d2-339c-a4a9-53e874e9ede7","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**sourceAvailability**
- `{"copyId":"bc2feadc-d7b3-3f7e-b7af-42707aa311a4","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bc2feadc-d7b3-3f7e-b7af-42707aa311a4","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bc2feadc-d7b3-3f7e-b7af-42707aa311a4","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bc2feadc-d7b3-3f7e-b7af-42707aa311a4","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bc2feadc-d7b3-3f7e-b7af-42707aa311a4","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bc2feadc-d7b3-3f7e-b7af-42707aa311a4","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bc2feadc-d7b3-3f7e-b7af-42707aa311a4","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"bc2feadc-d7b3-3f7e-b7af-42707aa311a4"},{"kind":"sourceEvent","id":"c415c82d-55e1-3919-ada3-231da2270007"},{"kind":"sourceEvent","id":"340d4e14-558c-32a3-9230-593b8305a9e0"},{"kind":"sourceEvent","id":"76887e92-e089-3310-a4cf-929ffa1ff092"},{"kind":"sourceEvent","id":"a6d405bc-22b3-3d1f-84d4-843139b2eaad"},{"kind":"sourceEvent","id":"fe69996c-8a22-3ef5-a7ec-79c8fa80a560"},{"kind":"sourceEvent","id":"d7d7f6c8-0595-3cdd-99c9-65b93aa4e998"},{"kind":"sourceEvent","id":"99a3b75f-61aa-37b0-a9cb-8f270282f272"},{"kind":"sourceEvent","id":"a8fd90cc-7a86-383e-9b37-d073a69bb6d3"},{"kind":"sourceEvent","id":"74fa4a4e-74f3-3f97-af03-7fe0e71eda35"},{"kind":"sourceEvent","id":"a9ba29a4-7b69-309f-8ffc-2dc4a938736e"},{"kind":"sourceEvent","id":"4fcdd671-3274-32e4-8e89-e14f6b0d6ba5"},{"kind":"sourceEvent","id":"ae61975e-1329-3961-b4f0-d2bd9d8dde7b"},{"kind":"sourceEvent","id":"3581ab3e-9469-3ea5-b3ec-79fb66e3db4d"},{"kind":"sourceEvent","id":"f4a55b08-87d4-3920-8d0a-bdd03ae59c2b"},{"kind":"observation","id":"6f14b4ed-f0f2-3170-8e50-516435f5b89c"},{"kind":"inventoryScope","id":"49be3af5-e9d2-339c-a4a9-53e874e9ede7"},{"kind":"sourceAvailability","id":"bc2feadc-d7b3-3f7e-b7af-42707aa311a4/LOANS"},{"kind":"sourceAvailability","id":"bc2feadc-d7b3-3f7e-b7af-42707aa311a4/RETURNS"},{"kind":"sourceAvailability","id":"bc2feadc-d7b3-3f7e-b7af-42707aa311a4/RECEIPTS"},{"kind":"sourceAvailability","id":"bc2feadc-d7b3-3f7e-b7af-42707aa311a4/TRANSFERS"},{"kind":"sourceAvailability","id":"bc2feadc-d7b3-3f7e-b7af-42707aa311a4/REPAIRS"},{"kind":"sourceAvailability","id":"bc2feadc-d7b3-3f7e-b7af-42707aa311a4/INTEGRATIONS"},{"kind":"sourceAvailability","id":"bc2feadc-d7b3-3f7e-b7af-42707aa311a4/INVENTORY"}]`

- Session : `{"scopeId":"49be3af5-e9d2-339c-a4a9-53e874e9ede7","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

## Cas 378ff216-4163-30d2-a241-4357ad4af1d4

- Exemplaire : `901dc95c-d089-3e62-8d47-ef9dc30357c4`
- Famille privée : RETARD_INTEGRATION ; variante : POSITIVE
- Symptômes : `[{"symptomId":"00fdd10b-40fe-3f11-baa6-674f98105222","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["QUEUE_PENDING"],"hiddenSourceEvents":[],"mechanism":"Événement métier en attente normale dans la fenêtre contractuelle de 900 secondes.","businessEventId":"a169000c-059b-3d9d-860c-b316f8ac16a0","physicalStatus":"ON_LOAN","physicalSiteId":"NORD","physicalLocationId":null,"projectionPerturbation":"QUEUED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["INTEGRATION_PENDING_WITHIN_SLA"],"explanation":"Prêt actif et traitement en file depuis moins de 900 secondes ; transition attendue, pas de panne persistante démontrée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Recontrôler après la fenêtre de traitement"]}`
- État projeté : `{"copyId":"901dc95c-d089-3e62-8d47-ef9dc30357c4","status":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","sourceEventIds":["df2b35b5-1e3e-345a-bfd6-1c996832c9c7","3197cb1b-9919-3d9a-8727-f6925e282527"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | b900b195-260d-3d7b-8099-5f9c16bcf0c5 | 2026-06-01T23:27:13Z | 2026-06-01T23:27:23Z | `{"operationId":"a3b0a180-97b0-38a4-980a-6bf5f25ee458","correlationId":"3b98e2d5-366c-33ff-afad-27038162dfe7","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"d46c03f6-f0e5-3165-9321-48ada3da7be1"}` |
| IntegrationProcessed | 7431f36d-00f4-30d8-9228-de0880e120c1 | 2026-06-02T23:27:13Z | 2026-06-02T23:27:23Z | `{"originalEventId":"b900b195-260d-3d7b-8099-5f9c16bcf0c5","correlationId":"3b98e2d5-366c-33ff-afad-27038162dfe7","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | e7587904-167d-30f3-8346-216467542f96 | 2026-07-31T23:27:13Z | 2026-07-31T23:27:23Z | `{"operationId":"b4169922-b131-3101-9046-87ae29109623","correlationId":"179929d7-d5bb-3ffb-b6c4-d58769c78dbb","siteId":"NORD","locationId":null,"loanId":"a44ce41f-d909-369f-bf9f-5b9fc26ba4b0"}` |
| IntegrationProcessed | be5df3fc-4e0c-3a69-bef8-3079281c5002 | 2026-07-31T23:28:13Z | 2026-07-31T23:28:23Z | `{"originalEventId":"e7587904-167d-30f3-8346-216467542f96","correlationId":"179929d7-d5bb-3ffb-b6c4-d58769c78dbb","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | 1f2716d6-b35c-3b8f-ae27-2d4d17456d68 | 2026-08-20T23:27:13Z | 2026-08-20T23:27:23Z | `{"operationId":"60e98055-c546-3b3e-9630-9aefd9db5f30","correlationId":"f59cc92c-cc35-390a-8efa-b0f905795a1b","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"a44ce41f-d909-369f-bf9f-5b9fc26ba4b0"}` |
| ReturnReceived | df2b35b5-1e3e-345a-bfd6-1c996832c9c7 | 2026-08-20T23:27:43Z | 2026-08-20T23:27:53Z | `{"operationId":"22147687-6e2b-3f32-ab80-770608116c1c","correlationId":"0d200805-00d3-3280-bf24-da0c2ff5ddc9","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"a44ce41f-d909-369f-bf9f-5b9fc26ba4b0"}` |
| IntegrationProcessed | 3197cb1b-9919-3d9a-8727-f6925e282527 | 2026-08-21T23:27:13Z | 2026-08-21T23:27:23Z | `{"originalEventId":"df2b35b5-1e3e-345a-bfd6-1c996832c9c7","correlationId":"0d200805-00d3-3280-bf24-da0c2ff5ddc9","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | a169000c-059b-3d9d-860c-b316f8ac16a0 | 2026-09-30T11:52:05Z | 2026-09-30T11:52:15Z | `{"operationId":"82b636fd-e649-30ab-a9b2-f9dd9fc3f5dd","correlationId":"35bfd934-a1e1-3a33-9136-1e38c81f214a","siteId":"NORD","locationId":null,"loanId":"cf30d508-870f-38b1-839f-fc78834925bb"}` |
| IntegrationProcessed | 7e42f94c-c464-3e5e-be0f-afa2d25e2e26 | 2026-09-30T11:52:35Z | 2026-09-30T11:52:45Z | `{"originalEventId":"a169000c-059b-3d9d-860c-b316f8ac16a0","correlationId":"35bfd934-a1e1-3a33-9136-1e38c81f214a","outcome":"QUEUED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1}` |

**observations**

**sourceAvailability**
- `{"copyId":"901dc95c-d089-3e62-8d47-ef9dc30357c4","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"901dc95c-d089-3e62-8d47-ef9dc30357c4","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"901dc95c-d089-3e62-8d47-ef9dc30357c4","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"901dc95c-d089-3e62-8d47-ef9dc30357c4","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"901dc95c-d089-3e62-8d47-ef9dc30357c4","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"901dc95c-d089-3e62-8d47-ef9dc30357c4","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"901dc95c-d089-3e62-8d47-ef9dc30357c4","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"901dc95c-d089-3e62-8d47-ef9dc30357c4"},{"kind":"sourceEvent","id":"b900b195-260d-3d7b-8099-5f9c16bcf0c5"},{"kind":"sourceEvent","id":"7431f36d-00f4-30d8-9228-de0880e120c1"},{"kind":"sourceEvent","id":"e7587904-167d-30f3-8346-216467542f96"},{"kind":"sourceEvent","id":"be5df3fc-4e0c-3a69-bef8-3079281c5002"},{"kind":"sourceEvent","id":"1f2716d6-b35c-3b8f-ae27-2d4d17456d68"},{"kind":"sourceEvent","id":"df2b35b5-1e3e-345a-bfd6-1c996832c9c7"},{"kind":"sourceEvent","id":"3197cb1b-9919-3d9a-8727-f6925e282527"},{"kind":"sourceEvent","id":"a169000c-059b-3d9d-860c-b316f8ac16a0"},{"kind":"sourceEvent","id":"7e42f94c-c464-3e5e-be0f-afa2d25e2e26"},{"kind":"sourceAvailability","id":"901dc95c-d089-3e62-8d47-ef9dc30357c4/LOANS"},{"kind":"sourceAvailability","id":"901dc95c-d089-3e62-8d47-ef9dc30357c4/RETURNS"},{"kind":"sourceAvailability","id":"901dc95c-d089-3e62-8d47-ef9dc30357c4/RECEIPTS"},{"kind":"sourceAvailability","id":"901dc95c-d089-3e62-8d47-ef9dc30357c4/TRANSFERS"},{"kind":"sourceAvailability","id":"901dc95c-d089-3e62-8d47-ef9dc30357c4/REPAIRS"},{"kind":"sourceAvailability","id":"901dc95c-d089-3e62-8d47-ef9dc30357c4/INTEGRATIONS"},{"kind":"sourceAvailability","id":"901dc95c-d089-3e62-8d47-ef9dc30357c4/INVENTORY"},{"kind":"inventoryScope","id":"07c957d2-b086-3b6b-9915-d21f1d5906cf"},{"kind":"inventoryScope","id":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c"},{"kind":"inventoryScope","id":"a481af0e-6045-37c4-bb84-d5269f5addc5"},{"kind":"inventoryScope","id":"9880a39c-a8f4-3f61-a473-804a0fd611a5"},{"kind":"inventoryScope","id":"79157bb9-b809-3388-ad1a-42d980616ea0"}]`

- Session : `{"scopeId":"07c957d2-b086-3b6b-9915-d21f1d5906cf","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"a481af0e-6045-37c4-bb84-d5269f5addc5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9880a39c-a8f4-3f61-a473-804a0fd611a5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"79157bb9-b809-3388-ad1a-42d980616ea0","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 39a3a191-0bb9-3ef2-8939-ac01086f4c01

- Exemplaire : `a94788b9-0a40-3c3f-b3e1-6e7e2535e6d4`
- Famille privée : RETOUR_NON_PROJETE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"d6417ba3-fad4-3928-af88-d4316dadbed8","kind":"STATUS_MISMATCH","expectedValue":"ON_LOAN","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"53a4f96c-d4b5-3c92-9d5d-6dac1d829250","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["RETURN_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"a94788b9-0a40-3c3f-b3e1-6e7e2535e6d4","status":"ON_LOAN","siteId":"NORD","locationId":null,"sourceEventIds":["432c06ff-4811-3482-9855-373e72270fb8","f7dddc21-c0ff-32b0-8ef5-44ceefd0685d"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 4f018957-e4c7-38ac-ab05-f346c94d36f5 | 2026-06-01T17:25:55Z | 2026-06-01T17:26:05Z | `{"operationId":"744513e2-0e00-3c9b-8aca-d888d5ac9fa8","correlationId":"875b1dc8-6998-307c-9b16-a65329225bab","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"a9529f97-a71a-3380-bd0d-86100f78e523"}` |
| IntegrationProcessed | 6c01434a-87b0-3605-bc14-d5d4589fff3d | 2026-06-02T17:25:55Z | 2026-06-02T17:26:05Z | `{"originalEventId":"4f018957-e4c7-38ac-ab05-f346c94d36f5","correlationId":"875b1dc8-6998-307c-9b16-a65329225bab","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 7b9156ed-40a4-374f-82c9-b5399ecab2f7 | 2026-07-31T17:25:55Z | 2026-07-31T17:26:05Z | `{"operationId":"b550dc5f-f2e9-31e0-ab6d-6ad733ab2cf6","correlationId":"74dfcf5d-7bec-32b0-838b-4ebb2c487a32","siteId":"CENTRE","locationId":null,"loanId":"6aad4191-2217-3f74-bb39-f3fc48858557"}` |
| IntegrationProcessed | 50db78c6-c97a-3d42-a7c1-72a1c628fc50 | 2026-07-31T17:26:55Z | 2026-07-31T17:27:05Z | `{"originalEventId":"7b9156ed-40a4-374f-82c9-b5399ecab2f7","correlationId":"74dfcf5d-7bec-32b0-838b-4ebb2c487a32","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 5ad4a654-11f1-36d5-8359-90db9cdd3a75 | 2026-08-20T17:25:55Z | 2026-08-20T17:26:05Z | `{"operationId":"c5782186-f48c-3917-add9-b8995b9e16e0","correlationId":"1bc14627-656f-3d34-a9b8-827aae87152a","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"6aad4191-2217-3f74-bb39-f3fc48858557"}` |
| ReturnReceived | 884088ca-4f37-3ff5-9285-e998fa18a50c | 2026-08-20T17:26:25Z | 2026-08-20T17:26:35Z | `{"operationId":"30110d3e-54b1-3f4b-a21c-f2f357a4ffba","correlationId":"a822bcf5-3eed-3314-9954-d46d652b1e6d","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"6aad4191-2217-3f74-bb39-f3fc48858557"}` |
| IntegrationProcessed | 7145a216-c909-3ec5-9de8-f3e4adec8320 | 2026-08-21T17:25:55Z | 2026-08-21T17:26:05Z | `{"originalEventId":"884088ca-4f37-3ff5-9285-e998fa18a50c","correlationId":"a822bcf5-3eed-3314-9954-d46d652b1e6d","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| TransferDispatched | 5b09685c-3ef2-3052-9903-c42bc433d7c7 | 2026-08-30T17:25:55Z | 2026-08-30T17:26:05Z | `{"operationId":"6efd75ae-c21a-368f-9feb-6b719e14fa3c","correlationId":"271d726b-fda3-35f8-ae41-a03b637072ac","siteId":"CENTRE","locationId":null,"transferId":"af60fef2-eb25-3b41-b796-35b7a3b10221","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | 886436e9-12e2-3f8c-8d22-6fc0e63c8e37 | 2026-08-30T17:26:55Z | 2026-08-30T17:27:05Z | `{"originalEventId":"5b09685c-3ef2-3052-9903-c42bc433d7c7","correlationId":"271d726b-fda3-35f8-ae41-a03b637072ac","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| TransferReceived | e2b6286e-35de-3ecf-8d7c-40836bb1ffcb | 2026-08-31T17:25:55Z | 2026-08-31T17:26:05Z | `{"operationId":"6ae37634-5b28-33b3-9291-8d708abffe16","correlationId":"edeaa205-beea-363a-8967-07402ef4da11","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","transferId":"af60fef2-eb25-3b41-b796-35b7a3b10221","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | ed7213e7-a042-3ff2-b2d1-0d7bb98d4902 | 2026-09-01T17:25:55Z | 2026-09-01T17:26:05Z | `{"originalEventId":"e2b6286e-35de-3ecf-8d7c-40836bb1ffcb","correlationId":"edeaa205-beea-363a-8967-07402ef4da11","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 432c06ff-4811-3482-9855-373e72270fb8 | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"24846b95-4277-3deb-a8c0-08bd81b4d546","correlationId":"c1541ecf-e9b5-372d-9680-972672ea030a","siteId":"NORD","locationId":null,"loanId":"f48dc794-a9aa-3d86-9421-d44bb304cab2"}` |
| IntegrationProcessed | f7dddc21-c0ff-32b0-8ef5-44ceefd0685d | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"432c06ff-4811-3482-9855-373e72270fb8","correlationId":"c1541ecf-e9b5-372d-9680-972672ea030a","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | 29485660-bab4-3c96-8f0f-31b295dd19be | 2026-09-27T11:13:19Z | 2026-09-27T11:13:29Z | `{"operationId":"4c94f2aa-628d-377e-816a-91e34fe5d313","correlationId":"adeeeb9e-9a54-361e-9fed-5ec0ac2d5c38","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"f48dc794-a9aa-3d86-9421-d44bb304cab2"}` |
| ReturnReceived | 53a4f96c-d4b5-3c92-9d5d-6dac1d829250 | 2026-09-27T11:13:49Z | 2026-09-27T11:13:59Z | `{"operationId":"eb8e7084-465c-3331-ab89-19eb9a81e52e","correlationId":"d9a0f1a3-b51a-3629-9dbb-d283e5891b88","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"f48dc794-a9aa-3d86-9421-d44bb304cab2"}` |
| IntegrationProcessed | f528e98e-7709-3ea0-8d5d-706ca40235de | 2026-09-27T11:14:19Z | 2026-09-27T11:14:29Z | `{"originalEventId":"53a4f96c-d4b5-3c92-9d5d-6dac1d829250","correlationId":"d9a0f1a3-b51a-3629-9dbb-d283e5891b88","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**observations**
- `{"observationId":"dda5263f-e096-31e6-8812-ba9ce4176031","copyId":"a94788b9-0a40-3c3f-b3e1-6e7e2535e6d4","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","scopeId":"456afa19-cbae-3187-bcba-3bf1ba51b9b8","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"a94788b9-0a40-3c3f-b3e1-6e7e2535e6d4","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a94788b9-0a40-3c3f-b3e1-6e7e2535e6d4","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a94788b9-0a40-3c3f-b3e1-6e7e2535e6d4","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a94788b9-0a40-3c3f-b3e1-6e7e2535e6d4","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a94788b9-0a40-3c3f-b3e1-6e7e2535e6d4","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a94788b9-0a40-3c3f-b3e1-6e7e2535e6d4","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a94788b9-0a40-3c3f-b3e1-6e7e2535e6d4","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"a94788b9-0a40-3c3f-b3e1-6e7e2535e6d4"},{"kind":"sourceEvent","id":"4f018957-e4c7-38ac-ab05-f346c94d36f5"},{"kind":"sourceEvent","id":"6c01434a-87b0-3605-bc14-d5d4589fff3d"},{"kind":"sourceEvent","id":"7b9156ed-40a4-374f-82c9-b5399ecab2f7"},{"kind":"sourceEvent","id":"50db78c6-c97a-3d42-a7c1-72a1c628fc50"},{"kind":"sourceEvent","id":"5ad4a654-11f1-36d5-8359-90db9cdd3a75"},{"kind":"sourceEvent","id":"884088ca-4f37-3ff5-9285-e998fa18a50c"},{"kind":"sourceEvent","id":"7145a216-c909-3ec5-9de8-f3e4adec8320"},{"kind":"sourceEvent","id":"5b09685c-3ef2-3052-9903-c42bc433d7c7"},{"kind":"sourceEvent","id":"886436e9-12e2-3f8c-8d22-6fc0e63c8e37"},{"kind":"sourceEvent","id":"e2b6286e-35de-3ecf-8d7c-40836bb1ffcb"},{"kind":"sourceEvent","id":"ed7213e7-a042-3ff2-b2d1-0d7bb98d4902"},{"kind":"sourceEvent","id":"432c06ff-4811-3482-9855-373e72270fb8"},{"kind":"sourceEvent","id":"f7dddc21-c0ff-32b0-8ef5-44ceefd0685d"},{"kind":"sourceEvent","id":"29485660-bab4-3c96-8f0f-31b295dd19be"},{"kind":"sourceEvent","id":"53a4f96c-d4b5-3c92-9d5d-6dac1d829250"},{"kind":"sourceEvent","id":"f528e98e-7709-3ea0-8d5d-706ca40235de"},{"kind":"observation","id":"dda5263f-e096-31e6-8812-ba9ce4176031"},{"kind":"inventoryScope","id":"456afa19-cbae-3187-bcba-3bf1ba51b9b8"},{"kind":"sourceAvailability","id":"a94788b9-0a40-3c3f-b3e1-6e7e2535e6d4/LOANS"},{"kind":"sourceAvailability","id":"a94788b9-0a40-3c3f-b3e1-6e7e2535e6d4/RETURNS"},{"kind":"sourceAvailability","id":"a94788b9-0a40-3c3f-b3e1-6e7e2535e6d4/RECEIPTS"},{"kind":"sourceAvailability","id":"a94788b9-0a40-3c3f-b3e1-6e7e2535e6d4/TRANSFERS"},{"kind":"sourceAvailability","id":"a94788b9-0a40-3c3f-b3e1-6e7e2535e6d4/REPAIRS"},{"kind":"sourceAvailability","id":"a94788b9-0a40-3c3f-b3e1-6e7e2535e6d4/INTEGRATIONS"},{"kind":"sourceAvailability","id":"a94788b9-0a40-3c3f-b3e1-6e7e2535e6d4/INVENTORY"}]`

- Session : `{"scopeId":"456afa19-cbae-3187-bcba-3bf1ba51b9b8","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 3bfa3f12-6360-3c6a-bfbc-5ef5190eb254

- Exemplaire : `2b41f39e-3847-3b36-aa33-cb806513523f`
- Famille privée : PRET_NON_PROJETE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"e8ea56f9-9a57-327f-8deb-78d1911a7647","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"05216718-8657-3a62-9c54-f5a23b572e61","physicalStatus":"ON_LOAN","physicalSiteId":"NORD","physicalLocationId":null,"projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["LOAN_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"2b41f39e-3847-3b36-aa33-cb806513523f","status":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","sourceEventIds":["f87d023b-8aa7-34d9-a9ac-fca9ed48f17a","5efb98a1-3af7-3f40-acc4-74c59372efd5"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | e10173be-5151-3f1a-b7f6-4b823c4ab954 | 2026-06-02T10:31:49Z | 2026-06-02T10:31:59Z | `{"operationId":"8b6d5ba3-2611-353d-8705-492b59c8366a","correlationId":"511139f3-e7a8-3a6c-96c2-cbcd7fad177a","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"6e2f19bc-bf02-3cbf-a120-377df0271b3d"}` |
| IntegrationProcessed | 2984df12-eaa6-36e6-975d-84a0c758c844 | 2026-06-03T10:31:49Z | 2026-06-03T10:31:59Z | `{"originalEventId":"e10173be-5151-3f1a-b7f6-4b823c4ab954","correlationId":"511139f3-e7a8-3a6c-96c2-cbcd7fad177a","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 347aff83-e8b0-3977-a8d3-0161a28d894b | 2026-08-01T10:31:49Z | 2026-08-01T10:31:59Z | `{"operationId":"89c1ad50-6dec-34c1-a83b-533bbda377dd","correlationId":"87827653-8129-3548-bd5c-f91b51d5a994","siteId":"CENTRE","locationId":null,"loanId":"fb94744c-13f3-33b7-9c62-3ca30c8d5d6f"}` |
| IntegrationProcessed | ea599033-e4e5-3328-9a54-37536866d610 | 2026-08-01T10:32:49Z | 2026-08-01T10:32:59Z | `{"originalEventId":"347aff83-e8b0-3977-a8d3-0161a28d894b","correlationId":"87827653-8129-3548-bd5c-f91b51d5a994","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | b5b01a24-59ff-378e-8f08-c6aa9e66b30c | 2026-08-21T10:31:49Z | 2026-08-21T10:31:59Z | `{"operationId":"2e0a9482-7716-30f7-aa3b-44d90f9d1e23","correlationId":"17b71a76-30e2-3d63-b0ed-0a26d89659e0","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"fb94744c-13f3-33b7-9c62-3ca30c8d5d6f"}` |
| ReturnReceived | 0f7afecb-4c94-36f1-8545-4b4a9cfa5d0c | 2026-08-21T10:32:19Z | 2026-08-21T10:32:29Z | `{"operationId":"2ed20f22-d9d0-3042-981c-1c2300d55ee7","correlationId":"78c1a7e2-09c3-32a9-9f7e-01c5f0ecb155","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"fb94744c-13f3-33b7-9c62-3ca30c8d5d6f"}` |
| IntegrationProcessed | 449f4e1a-7ea6-32a4-8b4d-1050f2660c61 | 2026-08-22T10:31:49Z | 2026-08-22T10:31:59Z | `{"originalEventId":"0f7afecb-4c94-36f1-8545-4b4a9cfa5d0c","correlationId":"78c1a7e2-09c3-32a9-9f7e-01c5f0ecb155","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| TransferDispatched | 7b690c8e-d6fc-3574-a6cd-55035fc8135e | 2026-08-31T10:31:49Z | 2026-08-31T10:31:59Z | `{"operationId":"1c1910ef-d784-38a4-86b5-e39cb1bb68ae","correlationId":"ab4ee6f7-1750-3e26-8d8a-12b4345991fc","siteId":"CENTRE","locationId":null,"transferId":"6f892176-298a-3b4a-a173-aecd6b19b8bc","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | 517d7284-a337-3651-a8ac-f4aeaa804843 | 2026-08-31T10:32:49Z | 2026-08-31T10:32:59Z | `{"originalEventId":"7b690c8e-d6fc-3574-a6cd-55035fc8135e","correlationId":"ab4ee6f7-1750-3e26-8d8a-12b4345991fc","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| TransferReceived | f87d023b-8aa7-34d9-a9ac-fca9ed48f17a | 2026-09-01T10:31:49Z | 2026-09-01T10:31:59Z | `{"operationId":"69deb86d-ceb0-35e0-87bd-ed34ef7d33b2","correlationId":"3bc36e35-f1d7-3e72-a0d4-6d97610f1803","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","transferId":"6f892176-298a-3b4a-a173-aecd6b19b8bc","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | 5efb98a1-3af7-3f40-acc4-74c59372efd5 | 2026-09-02T10:31:49Z | 2026-09-02T10:31:59Z | `{"originalEventId":"f87d023b-8aa7-34d9-a9ac-fca9ed48f17a","correlationId":"3bc36e35-f1d7-3e72-a0d4-6d97610f1803","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 05216718-8657-3a62-9c54-f5a23b572e61 | 2026-09-27T06:08:18Z | 2026-09-27T06:08:28Z | `{"operationId":"267abe43-dcce-3dd9-89b1-5ed495667588","correlationId":"d7e05122-e075-3ab2-831a-e43de59a1681","siteId":"NORD","locationId":null,"loanId":"09ed3a14-f1ce-3fbe-909b-c922b53ca8c6"}` |
| IntegrationProcessed | 68b10957-6ba9-3fcc-b791-ec86752de776 | 2026-09-27T06:08:48Z | 2026-09-27T06:08:58Z | `{"originalEventId":"05216718-8657-3a62-9c54-f5a23b572e61","correlationId":"d7e05122-e075-3ab2-831a-e43de59a1681","outcome":"FAILED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**observations**

**sourceAvailability**
- `{"copyId":"2b41f39e-3847-3b36-aa33-cb806513523f","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2b41f39e-3847-3b36-aa33-cb806513523f","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2b41f39e-3847-3b36-aa33-cb806513523f","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2b41f39e-3847-3b36-aa33-cb806513523f","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2b41f39e-3847-3b36-aa33-cb806513523f","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2b41f39e-3847-3b36-aa33-cb806513523f","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2b41f39e-3847-3b36-aa33-cb806513523f","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"2b41f39e-3847-3b36-aa33-cb806513523f"},{"kind":"sourceEvent","id":"e10173be-5151-3f1a-b7f6-4b823c4ab954"},{"kind":"sourceEvent","id":"2984df12-eaa6-36e6-975d-84a0c758c844"},{"kind":"sourceEvent","id":"347aff83-e8b0-3977-a8d3-0161a28d894b"},{"kind":"sourceEvent","id":"ea599033-e4e5-3328-9a54-37536866d610"},{"kind":"sourceEvent","id":"b5b01a24-59ff-378e-8f08-c6aa9e66b30c"},{"kind":"sourceEvent","id":"0f7afecb-4c94-36f1-8545-4b4a9cfa5d0c"},{"kind":"sourceEvent","id":"449f4e1a-7ea6-32a4-8b4d-1050f2660c61"},{"kind":"sourceEvent","id":"7b690c8e-d6fc-3574-a6cd-55035fc8135e"},{"kind":"sourceEvent","id":"517d7284-a337-3651-a8ac-f4aeaa804843"},{"kind":"sourceEvent","id":"f87d023b-8aa7-34d9-a9ac-fca9ed48f17a"},{"kind":"sourceEvent","id":"5efb98a1-3af7-3f40-acc4-74c59372efd5"},{"kind":"sourceEvent","id":"05216718-8657-3a62-9c54-f5a23b572e61"},{"kind":"sourceEvent","id":"68b10957-6ba9-3fcc-b791-ec86752de776"},{"kind":"sourceAvailability","id":"2b41f39e-3847-3b36-aa33-cb806513523f/LOANS"},{"kind":"sourceAvailability","id":"2b41f39e-3847-3b36-aa33-cb806513523f/RETURNS"},{"kind":"sourceAvailability","id":"2b41f39e-3847-3b36-aa33-cb806513523f/RECEIPTS"},{"kind":"sourceAvailability","id":"2b41f39e-3847-3b36-aa33-cb806513523f/TRANSFERS"},{"kind":"sourceAvailability","id":"2b41f39e-3847-3b36-aa33-cb806513523f/REPAIRS"},{"kind":"sourceAvailability","id":"2b41f39e-3847-3b36-aa33-cb806513523f/INTEGRATIONS"},{"kind":"sourceAvailability","id":"2b41f39e-3847-3b36-aa33-cb806513523f/INVENTORY"},{"kind":"inventoryScope","id":"07c957d2-b086-3b6b-9915-d21f1d5906cf"}]`

- Session : `{"scopeId":"07c957d2-b086-3b6b-9915-d21f1d5906cf","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 3fa514a7-05d4-3c39-9786-ab9342bc5186

- Exemplaire : `22149e6c-72de-3471-9fcc-2356346382b8`
- Famille privée : PRET_NON_PROJETE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"5d161538-d384-3424-82f5-d7bc73467e8c","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"46fde550-49aa-3b3a-ac94-c2cfc3ae13c8","physicalStatus":"ON_LOAN","physicalSiteId":"CENTRE","physicalLocationId":null,"projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["LOAN_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"22149e6c-72de-3471-9fcc-2356346382b8","status":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","sourceEventIds":["a23fd385-504b-375f-a3fc-e7e0314ac3e8","f24c004f-423f-334b-b26f-483c6d3bf61b"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | bcb56bbd-1083-31be-b1e0-0bd46062d42a | 2026-06-02T00:19:34Z | 2026-06-02T00:19:44Z | `{"operationId":"61507743-8f25-3246-87a4-3c576102dd2b","correlationId":"4686a801-7647-3ad6-8bdd-a7af734e2f2a","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"ff08d200-2ff4-3542-91df-e40154f86a65"}` |
| IntegrationProcessed | 8f89619a-a30d-3fd6-bb36-e3bf90e1a19e | 2026-06-03T00:19:34Z | 2026-06-03T00:19:44Z | `{"originalEventId":"bcb56bbd-1083-31be-b1e0-0bd46062d42a","correlationId":"4686a801-7647-3ad6-8bdd-a7af734e2f2a","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | 8d01ccee-d05e-36da-ab46-3a51f1cfb82c | 2026-08-01T00:19:34Z | 2026-08-01T00:19:44Z | `{"operationId":"2e5a3967-319a-3e91-af36-3f7afcfffbeb","correlationId":"c1d97397-5901-312a-80f1-8f6c149ae91a","siteId":"SUD","locationId":null,"loanId":"1958f508-3263-350e-878a-46e93771e945"}` |
| IntegrationProcessed | 2b431271-b983-3cab-8c3f-131c6c2c97bc | 2026-08-01T00:20:34Z | 2026-08-01T00:20:44Z | `{"originalEventId":"8d01ccee-d05e-36da-ab46-3a51f1cfb82c","correlationId":"c1d97397-5901-312a-80f1-8f6c149ae91a","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | ea6a1e7c-248e-3ed2-b8cb-df3c11c6908c | 2026-08-21T00:19:34Z | 2026-08-21T00:19:44Z | `{"operationId":"ed76d1bf-f401-33d0-b431-800fae5f0c8e","correlationId":"9f1955af-423d-363f-8bd2-8b2431577a6c","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"1958f508-3263-350e-878a-46e93771e945"}` |
| ReturnReceived | c17799b6-39d0-3239-8163-51f0a78dd9c1 | 2026-08-21T00:20:04Z | 2026-08-21T00:20:14Z | `{"operationId":"ccc08860-e497-30fd-b8a1-a71dd86f6103","correlationId":"0bcf17d5-9221-3813-a2a7-96ffe65c63a1","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"1958f508-3263-350e-878a-46e93771e945"}` |
| IntegrationProcessed | ee1b6780-0739-3fac-ac81-018927d9216f | 2026-08-22T00:19:34Z | 2026-08-22T00:19:44Z | `{"originalEventId":"c17799b6-39d0-3239-8163-51f0a78dd9c1","correlationId":"0bcf17d5-9221-3813-a2a7-96ffe65c63a1","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| TransferDispatched | 8974fdc4-8c70-3427-9cce-e1e42db69fe4 | 2026-08-31T00:19:34Z | 2026-08-31T00:19:44Z | `{"operationId":"d1bae1c1-3121-3906-b1df-064d0955ee0e","correlationId":"7421992d-9bb6-3e76-91ff-5d5f30ac1eb4","siteId":"SUD","locationId":null,"transferId":"dfff2fca-40fa-32eb-bfd4-0e4c0b742580","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| IntegrationProcessed | c0082233-08e3-3d9a-b005-012e8052efab | 2026-08-31T00:20:34Z | 2026-08-31T00:20:44Z | `{"originalEventId":"8974fdc4-8c70-3427-9cce-e1e42db69fe4","correlationId":"7421992d-9bb6-3e76-91ff-5d5f30ac1eb4","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"SUD","locationId":null}` |
| TransferReceived | a23fd385-504b-375f-a3fc-e7e0314ac3e8 | 2026-09-01T00:19:34Z | 2026-09-01T00:19:44Z | `{"operationId":"4b02aa31-b5a5-3d7c-abea-907ef140bc36","correlationId":"5ce9b67b-18f8-3c3c-a409-f212b112c235","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","transferId":"dfff2fca-40fa-32eb-bfd4-0e4c0b742580","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| IntegrationProcessed | f24c004f-423f-334b-b26f-483c6d3bf61b | 2026-09-02T00:19:34Z | 2026-09-02T00:19:44Z | `{"originalEventId":"a23fd385-504b-375f-a3fc-e7e0314ac3e8","correlationId":"5ce9b67b-18f8-3c3c-a409-f212b112c235","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 46fde550-49aa-3b3a-ac94-c2cfc3ae13c8 | 2026-09-27T10:20:51Z | 2026-09-27T10:21:01Z | `{"operationId":"0a912c68-7515-3f01-97de-8e8e9146ca87","correlationId":"60d95af2-3549-3520-9d95-0ad847db27c5","siteId":"CENTRE","locationId":null,"loanId":"f9808be1-55fd-3f36-9485-d2eb3e94f6d1"}` |
| IntegrationProcessed | a431687e-a6c3-3951-8717-774574aab0b5 | 2026-09-27T10:21:21Z | 2026-09-27T10:21:31Z | `{"originalEventId":"46fde550-49aa-3b3a-ac94-c2cfc3ae13c8","correlationId":"60d95af2-3549-3520-9d95-0ad847db27c5","outcome":"FAILED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**observations**

**sourceAvailability**
- `{"copyId":"22149e6c-72de-3471-9fcc-2356346382b8","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"22149e6c-72de-3471-9fcc-2356346382b8","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"22149e6c-72de-3471-9fcc-2356346382b8","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"22149e6c-72de-3471-9fcc-2356346382b8","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"22149e6c-72de-3471-9fcc-2356346382b8","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"22149e6c-72de-3471-9fcc-2356346382b8","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"22149e6c-72de-3471-9fcc-2356346382b8","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"22149e6c-72de-3471-9fcc-2356346382b8"},{"kind":"sourceEvent","id":"bcb56bbd-1083-31be-b1e0-0bd46062d42a"},{"kind":"sourceEvent","id":"8f89619a-a30d-3fd6-bb36-e3bf90e1a19e"},{"kind":"sourceEvent","id":"8d01ccee-d05e-36da-ab46-3a51f1cfb82c"},{"kind":"sourceEvent","id":"2b431271-b983-3cab-8c3f-131c6c2c97bc"},{"kind":"sourceEvent","id":"ea6a1e7c-248e-3ed2-b8cb-df3c11c6908c"},{"kind":"sourceEvent","id":"c17799b6-39d0-3239-8163-51f0a78dd9c1"},{"kind":"sourceEvent","id":"ee1b6780-0739-3fac-ac81-018927d9216f"},{"kind":"sourceEvent","id":"8974fdc4-8c70-3427-9cce-e1e42db69fe4"},{"kind":"sourceEvent","id":"c0082233-08e3-3d9a-b005-012e8052efab"},{"kind":"sourceEvent","id":"a23fd385-504b-375f-a3fc-e7e0314ac3e8"},{"kind":"sourceEvent","id":"f24c004f-423f-334b-b26f-483c6d3bf61b"},{"kind":"sourceEvent","id":"46fde550-49aa-3b3a-ac94-c2cfc3ae13c8"},{"kind":"sourceEvent","id":"a431687e-a6c3-3951-8717-774574aab0b5"},{"kind":"sourceAvailability","id":"22149e6c-72de-3471-9fcc-2356346382b8/LOANS"},{"kind":"sourceAvailability","id":"22149e6c-72de-3471-9fcc-2356346382b8/RETURNS"},{"kind":"sourceAvailability","id":"22149e6c-72de-3471-9fcc-2356346382b8/RECEIPTS"},{"kind":"sourceAvailability","id":"22149e6c-72de-3471-9fcc-2356346382b8/TRANSFERS"},{"kind":"sourceAvailability","id":"22149e6c-72de-3471-9fcc-2356346382b8/REPAIRS"},{"kind":"sourceAvailability","id":"22149e6c-72de-3471-9fcc-2356346382b8/INTEGRATIONS"},{"kind":"sourceAvailability","id":"22149e6c-72de-3471-9fcc-2356346382b8/INVENTORY"},{"kind":"inventoryScope","id":"8021dca8-65fe-3cab-985e-d4fb9df5c308"},{"kind":"inventoryScope","id":"b0027657-6abb-37d2-8bc4-9341357dfe79"},{"kind":"inventoryScope","id":"4757f5eb-f104-3413-b118-51a6951a650c"}]`

- Session : `{"scopeId":"8021dca8-65fe-3cab-985e-d4fb9df5c308","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b0027657-6abb-37d2-8bc4-9341357dfe79","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4757f5eb-f104-3413-b118-51a6951a650c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 443d05a1-abb4-3639-ab12-fdcc9ac2ce94

- Exemplaire : `3a25e3b1-e761-32f3-8cad-6b437db4f48f`
- Famille privée : REPARATION_NON_PROJETEE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"6c0ee4d2-657f-3364-bbfa-aefc62897fbc","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"2d5e2539-5b66-3f91-8751-53bd75d123c8","physicalStatus":"IN_REPAIR","physicalSiteId":"CENTRE","physicalLocationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"3a25e3b1-e761-32f3-8cad-6b437db4f48f","status":"IN_REPAIR","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","sourceEventIds":["2d5e2539-5b66-3f91-8751-53bd75d123c8","248780c6-33b9-32d3-90c7-239d378122d8"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | bcbd40b4-51be-3e24-986e-0de231237187 | 2026-06-01T20:37:10Z | 2026-06-01T20:37:20Z | `{"operationId":"90bc69ae-1279-3120-8c04-7a5f7fe86624","correlationId":"1a38b898-c9bc-3fa6-b4ed-fa793cb6731b","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"e5100e9e-9b31-3232-9874-8b4f67cdc0dc"}` |
| IntegrationProcessed | 1da67deb-e54c-3fa2-877f-56109db15019 | 2026-06-02T20:37:10Z | 2026-06-02T20:37:20Z | `{"originalEventId":"bcbd40b4-51be-3e24-986e-0de231237187","correlationId":"1a38b898-c9bc-3fa6-b4ed-fa793cb6731b","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | e0414342-7697-3994-bd8d-eed8827806a2 | 2026-07-31T20:37:10Z | 2026-07-31T20:37:20Z | `{"operationId":"12385842-bfc2-31f9-86d2-15b7f92cd070","correlationId":"2389cffa-ae59-3ce8-ae61-7c3ddecc3e85","siteId":"SUD","locationId":null,"loanId":"b7909713-b0ca-3c75-b51d-1fef7a01db7c"}` |
| IntegrationProcessed | 757836d3-90f5-3995-9aa0-d89ea0c05ee9 | 2026-07-31T20:38:10Z | 2026-07-31T20:38:20Z | `{"originalEventId":"e0414342-7697-3994-bd8d-eed8827806a2","correlationId":"2389cffa-ae59-3ce8-ae61-7c3ddecc3e85","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | 6dcc5d69-b6f1-37a3-86c7-078d00ee2138 | 2026-08-20T20:37:10Z | 2026-08-20T20:37:20Z | `{"operationId":"754c8b92-9a61-30d3-9afe-d60555c7729c","correlationId":"82f70e1e-c267-353d-8cf0-f98049c60106","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"b7909713-b0ca-3c75-b51d-1fef7a01db7c"}` |
| ReturnReceived | 282307dc-8744-32ab-a91a-ec18343f84b1 | 2026-08-20T20:37:40Z | 2026-08-20T20:37:50Z | `{"operationId":"aa76a13f-c111-388e-a862-346478859aa0","correlationId":"958151fe-c720-3a66-95da-4e49ef3844e0","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"b7909713-b0ca-3c75-b51d-1fef7a01db7c"}` |
| IntegrationProcessed | 1cf1af5b-f35c-3c11-b752-e569ebc785b0 | 2026-08-21T20:37:10Z | 2026-08-21T20:37:20Z | `{"originalEventId":"282307dc-8744-32ab-a91a-ec18343f84b1","correlationId":"958151fe-c720-3a66-95da-4e49ef3844e0","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| TransferDispatched | 59a7bb77-d0e2-3b38-8ddd-a54ba091857b | 2026-08-30T20:37:10Z | 2026-08-30T20:37:20Z | `{"operationId":"5d783e32-3134-3c17-bf0a-231a21c3f584","correlationId":"9a2f4da9-ffbd-3380-82cf-914040908686","siteId":"SUD","locationId":null,"transferId":"9a152d89-e6a4-348c-8b3e-f4cdbcb7a6a2","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| IntegrationProcessed | 995d3c47-8a63-387a-bf4b-d608252c671e | 2026-08-30T20:38:10Z | 2026-08-30T20:38:20Z | `{"originalEventId":"59a7bb77-d0e2-3b38-8ddd-a54ba091857b","correlationId":"9a2f4da9-ffbd-3380-82cf-914040908686","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"SUD","locationId":null}` |
| TransferReceived | d21b5929-abcf-3291-a7eb-874b85a6406e | 2026-08-31T20:37:10Z | 2026-08-31T20:37:20Z | `{"operationId":"40e918e2-fd00-3a8d-a048-0d163b8b1c71","correlationId":"4b32a420-8614-3726-8272-fb0c7aa4d008","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","transferId":"9a152d89-e6a4-348c-8b3e-f4cdbcb7a6a2","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| IntegrationProcessed | 8cadef33-ebcf-3283-91d8-86ae265cc2e9 | 2026-09-01T20:37:10Z | 2026-09-01T20:37:20Z | `{"originalEventId":"d21b5929-abcf-3291-a7eb-874b85a6406e","correlationId":"4b32a420-8614-3726-8272-fb0c7aa4d008","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| RepairStarted | 2d5e2539-5b66-3f91-8751-53bd75d123c8 | 2026-09-27T11:55:19Z | 2026-09-27T11:55:29Z | `{"operationId":"0ef701ad-2b2f-3d09-897d-db807252fb17","correlationId":"30fd3db5-962d-38cc-aea2-e713732f2cde","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","repairId":"ec88b811-9e8a-3cb7-9dd7-3baa2537b0f5"}` |
| IntegrationProcessed | 915039a1-d244-3685-9379-32219d9cfaab | 2026-09-27T11:55:49Z | 2026-09-27T11:55:59Z | `{"originalEventId":"2d5e2539-5b66-3f91-8751-53bd75d123c8","correlationId":"30fd3db5-962d-38cc-aea2-e713732f2cde","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| IntegrationProcessed | 248780c6-33b9-32d3-90c7-239d378122d8 | 2026-09-27T11:56:49Z | 2026-09-27T11:56:59Z | `{"originalEventId":"2d5e2539-5b66-3f91-8751-53bd75d123c8","correlationId":"30fd3db5-962d-38cc-aea2-e713732f2cde","outcome":"APPLIED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be"}` |

**observations**
- `{"observationId":"a01f8a45-eeaa-3d25-bce7-0e427aef9987","copyId":"3a25e3b1-e761-32f3-8cad-6b437db4f48f","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","scopeId":"e40f4833-d76c-3015-95f9-c5c4c5316123","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`
- `{"observationId":"6ca20684-28f7-3e18-904b-d701eace1ac2","copyId":"3a25e3b1-e761-32f3-8cad-6b437db4f48f","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","scopeId":"cba545a3-72f1-399f-b2cf-1bfca18ba57f","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**sourceAvailability**
- `{"copyId":"3a25e3b1-e761-32f3-8cad-6b437db4f48f","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3a25e3b1-e761-32f3-8cad-6b437db4f48f","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3a25e3b1-e761-32f3-8cad-6b437db4f48f","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3a25e3b1-e761-32f3-8cad-6b437db4f48f","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3a25e3b1-e761-32f3-8cad-6b437db4f48f","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3a25e3b1-e761-32f3-8cad-6b437db4f48f","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3a25e3b1-e761-32f3-8cad-6b437db4f48f","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"3a25e3b1-e761-32f3-8cad-6b437db4f48f"},{"kind":"sourceEvent","id":"bcbd40b4-51be-3e24-986e-0de231237187"},{"kind":"sourceEvent","id":"1da67deb-e54c-3fa2-877f-56109db15019"},{"kind":"sourceEvent","id":"e0414342-7697-3994-bd8d-eed8827806a2"},{"kind":"sourceEvent","id":"757836d3-90f5-3995-9aa0-d89ea0c05ee9"},{"kind":"sourceEvent","id":"6dcc5d69-b6f1-37a3-86c7-078d00ee2138"},{"kind":"sourceEvent","id":"282307dc-8744-32ab-a91a-ec18343f84b1"},{"kind":"sourceEvent","id":"1cf1af5b-f35c-3c11-b752-e569ebc785b0"},{"kind":"sourceEvent","id":"59a7bb77-d0e2-3b38-8ddd-a54ba091857b"},{"kind":"sourceEvent","id":"995d3c47-8a63-387a-bf4b-d608252c671e"},{"kind":"sourceEvent","id":"d21b5929-abcf-3291-a7eb-874b85a6406e"},{"kind":"sourceEvent","id":"8cadef33-ebcf-3283-91d8-86ae265cc2e9"},{"kind":"sourceEvent","id":"2d5e2539-5b66-3f91-8751-53bd75d123c8"},{"kind":"sourceEvent","id":"915039a1-d244-3685-9379-32219d9cfaab"},{"kind":"sourceEvent","id":"248780c6-33b9-32d3-90c7-239d378122d8"},{"kind":"observation","id":"a01f8a45-eeaa-3d25-bce7-0e427aef9987"},{"kind":"inventoryScope","id":"e40f4833-d76c-3015-95f9-c5c4c5316123"},{"kind":"observation","id":"6ca20684-28f7-3e18-904b-d701eace1ac2"},{"kind":"inventoryScope","id":"cba545a3-72f1-399f-b2cf-1bfca18ba57f"},{"kind":"sourceAvailability","id":"3a25e3b1-e761-32f3-8cad-6b437db4f48f/LOANS"},{"kind":"sourceAvailability","id":"3a25e3b1-e761-32f3-8cad-6b437db4f48f/RETURNS"},{"kind":"sourceAvailability","id":"3a25e3b1-e761-32f3-8cad-6b437db4f48f/RECEIPTS"},{"kind":"sourceAvailability","id":"3a25e3b1-e761-32f3-8cad-6b437db4f48f/TRANSFERS"},{"kind":"sourceAvailability","id":"3a25e3b1-e761-32f3-8cad-6b437db4f48f/REPAIRS"},{"kind":"sourceAvailability","id":"3a25e3b1-e761-32f3-8cad-6b437db4f48f/INTEGRATIONS"},{"kind":"sourceAvailability","id":"3a25e3b1-e761-32f3-8cad-6b437db4f48f/INVENTORY"},{"kind":"inventoryScope","id":"fc22847f-af8c-366f-be86-355e86e7e988"}]`

- Session : `{"scopeId":"e40f4833-d76c-3015-95f9-c5c4c5316123","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"cba545a3-72f1-399f-b2cf-1bfca18ba57f","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"fc22847f-af8c-366f-be86-355e86e7e988","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 477ff4c5-18ff-33e6-b76d-5698427dbacd

- Exemplaire : `2ec8c5bc-7900-3737-90c8-6201a4e1bee4`
- Famille privée : ABSENCE_INEXPLIQUEE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"4eb6fb82-0ca8-33db-9e4b-c2da02e751b3","kind":"MISSING","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["UNOBSERVED_MOVE"],"mechanism":"Exemplaire déplacé hors du périmètre instrumenté ; la destination n'est pas exposée et aucune perte n'est simulée.","physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":null,"evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"L'absence de lecture ne démontre ni la destination, ni une perte, ni un vol ; données insuffisantes pour attribuer une cause.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État projeté : `{"copyId":"2ec8c5bc-7900-3737-90c8-6201a4e1bee4","status":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","sourceEventIds":["6ec42d53-cdca-3f7d-910a-a0d55dd8fbf7","f183164a-9323-34e6-bae0-cf04d76bf3b6"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 39ae9ba1-29af-3890-a831-4d90bf430025 | 2026-06-02T05:37:31Z | 2026-06-02T05:37:41Z | `{"operationId":"027dfff2-a65d-31d9-937f-5488650236d3","correlationId":"00de702e-7636-3cd3-8aad-48ec2ffb09c1","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"f20d9e2a-227a-37cd-a429-62dd440f73bb"}` |
| IntegrationProcessed | 790bee21-d9c7-3317-9dba-44d3803c1d42 | 2026-06-03T05:37:31Z | 2026-06-03T05:37:41Z | `{"originalEventId":"39ae9ba1-29af-3890-a831-4d90bf430025","correlationId":"00de702e-7636-3cd3-8aad-48ec2ffb09c1","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 49503f66-fdb0-3376-b3f2-c0e6df27300c | 2026-08-01T05:37:31Z | 2026-08-01T05:37:41Z | `{"operationId":"f981873d-2830-36d7-9e5f-88303eb0c359","correlationId":"960f99e8-6c20-388c-bb6b-5993c8e7355b","siteId":"NORD","locationId":null,"loanId":"85d0a6ba-e2a2-3c2c-b354-0696e96690a7"}` |
| IntegrationProcessed | 45ce6833-2d15-3eb6-b0ef-959a43bc8875 | 2026-08-01T05:38:31Z | 2026-08-01T05:38:41Z | `{"originalEventId":"49503f66-fdb0-3376-b3f2-c0e6df27300c","correlationId":"960f99e8-6c20-388c-bb6b-5993c8e7355b","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | c4e7c3af-7935-3769-9bbe-82c62acb3406 | 2026-08-21T05:37:31Z | 2026-08-21T05:37:41Z | `{"operationId":"bc8a4e66-3491-3687-a660-f3b587360540","correlationId":"69cddb49-192d-36b5-a6ce-42c75046b1db","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"85d0a6ba-e2a2-3c2c-b354-0696e96690a7"}` |
| ReturnReceived | 6ec42d53-cdca-3f7d-910a-a0d55dd8fbf7 | 2026-08-21T05:38:01Z | 2026-08-21T05:38:11Z | `{"operationId":"517820f2-75b4-3ffd-b60d-4cbf41625ae9","correlationId":"38d72761-c943-3fe1-9e6c-aa91be824691","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"85d0a6ba-e2a2-3c2c-b354-0696e96690a7"}` |
| IntegrationProcessed | f183164a-9323-34e6-bae0-cf04d76bf3b6 | 2026-08-22T05:37:31Z | 2026-08-22T05:37:41Z | `{"originalEventId":"6ec42d53-cdca-3f7d-910a-a0d55dd8fbf7","correlationId":"38d72761-c943-3fe1-9e6c-aa91be824691","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |

**observations**

**sourceAvailability**
- `{"copyId":"2ec8c5bc-7900-3737-90c8-6201a4e1bee4","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2ec8c5bc-7900-3737-90c8-6201a4e1bee4","source":"TRANSFERS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"2ec8c5bc-7900-3737-90c8-6201a4e1bee4","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2ec8c5bc-7900-3737-90c8-6201a4e1bee4","source":"LOANS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"2ec8c5bc-7900-3737-90c8-6201a4e1bee4","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2ec8c5bc-7900-3737-90c8-6201a4e1bee4","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2ec8c5bc-7900-3737-90c8-6201a4e1bee4","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**
- `{"noteId":"718422f0-99ce-33a5-a637-4366be9038c8","copyId":"2ec8c5bc-7900-3737-90c8-6201a4e1bee4","text":"Le passage au rayon n'a pas permis de retrouver cet exemplaire ; contrôle complémentaire demandé.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"2ec8c5bc-7900-3737-90c8-6201a4e1bee4"},{"kind":"sourceEvent","id":"39ae9ba1-29af-3890-a831-4d90bf430025"},{"kind":"sourceEvent","id":"790bee21-d9c7-3317-9dba-44d3803c1d42"},{"kind":"sourceEvent","id":"49503f66-fdb0-3376-b3f2-c0e6df27300c"},{"kind":"sourceEvent","id":"45ce6833-2d15-3eb6-b0ef-959a43bc8875"},{"kind":"sourceEvent","id":"c4e7c3af-7935-3769-9bbe-82c62acb3406"},{"kind":"sourceEvent","id":"6ec42d53-cdca-3f7d-910a-a0d55dd8fbf7"},{"kind":"sourceEvent","id":"f183164a-9323-34e6-bae0-cf04d76bf3b6"},{"kind":"sourceAvailability","id":"2ec8c5bc-7900-3737-90c8-6201a4e1bee4/LOANS"},{"kind":"sourceAvailability","id":"2ec8c5bc-7900-3737-90c8-6201a4e1bee4/RETURNS"},{"kind":"sourceAvailability","id":"2ec8c5bc-7900-3737-90c8-6201a4e1bee4/RECEIPTS"},{"kind":"sourceAvailability","id":"2ec8c5bc-7900-3737-90c8-6201a4e1bee4/TRANSFERS"},{"kind":"sourceAvailability","id":"2ec8c5bc-7900-3737-90c8-6201a4e1bee4/REPAIRS"},{"kind":"sourceAvailability","id":"2ec8c5bc-7900-3737-90c8-6201a4e1bee4/INTEGRATIONS"},{"kind":"sourceAvailability","id":"2ec8c5bc-7900-3737-90c8-6201a4e1bee4/INVENTORY"},{"kind":"operatorNote","id":"718422f0-99ce-33a5-a637-4366be9038c8"},{"kind":"inventoryScope","id":"07c957d2-b086-3b6b-9915-d21f1d5906cf"},{"kind":"inventoryScope","id":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c"},{"kind":"inventoryScope","id":"a481af0e-6045-37c4-bb84-d5269f5addc5"},{"kind":"inventoryScope","id":"9880a39c-a8f4-3f61-a473-804a0fd611a5"},{"kind":"inventoryScope","id":"79157bb9-b809-3388-ad1a-42d980616ea0"},{"kind":"inventoryScope","id":"eba23a81-5103-3ded-ba6b-6619213a7840"},{"kind":"inventoryScope","id":"6c49954b-e555-36a5-ab71-af60ea210c9b"},{"kind":"inventoryScope","id":"52bd3f79-07d0-3284-a51a-c100a5c5022e"},{"kind":"inventoryScope","id":"dcb0af01-2143-3d85-9776-f4578912c7d1"},{"kind":"inventoryScope","id":"dcb0af01-2143-3d85-9776-f4578912c7d1"}]`

- Session : `{"scopeId":"07c957d2-b086-3b6b-9915-d21f1d5906cf","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"a481af0e-6045-37c4-bb84-d5269f5addc5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9880a39c-a8f4-3f61-a473-804a0fd611a5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"79157bb9-b809-3388-ad1a-42d980616ea0","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"eba23a81-5103-3ded-ba6b-6619213a7840","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"6c49954b-e555-36a5-ab71-af60ea210c9b","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"52bd3f79-07d0-3284-a51a-c100a5c5022e","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"dcb0af01-2143-3d85-9776-f4578912c7d1","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"dcb0af01-2143-3d85-9776-f4578912c7d1","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 4a38c68c-4677-3578-9ae9-057076dc7543

- Exemplaire : `37fc5075-ff7e-386f-8d55-14f142bdc815`
- Famille privée : ABSENCE_INEXPLIQUEE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"fd89c698-c464-3ca5-b19e-f626d6ba0a0b","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["UNOBSERVED_MOVE"],"mechanism":"Exemplaire déplacé hors du périmètre instrumenté ; la destination n'est pas exposée et aucune perte n'est simulée.","physicalStatus":"ON_SHELF","physicalSiteId":"SUD","physicalLocationId":null,"evidenceMask":"Collecte indisponible"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"L'absence de lecture ne démontre ni la destination, ni une perte, ni un vol ; données insuffisantes pour attribuer une cause.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État projeté : `{"copyId":"37fc5075-ff7e-386f-8d55-14f142bdc815","status":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","sourceEventIds":["6c9bf1fe-5a3d-3bae-a08b-81258f803765","6a40391b-f343-3da1-9f6c-75e5f16c9fe1"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | c284c91a-f5c7-39a3-a2e7-99450a6f7fce | 2026-06-01T22:49:49Z | 2026-06-01T22:49:59Z | `{"operationId":"1a380091-4f8c-3c43-9f20-96367febdd33","correlationId":"40b72846-1c87-3164-9bac-0987ec309687","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"b85f2da0-59b0-3733-82f8-3b0c45b8a263"}` |
| IntegrationProcessed | 8c70ecc8-8fce-355e-a2ad-568b3044fc73 | 2026-06-02T22:49:49Z | 2026-06-02T22:49:59Z | `{"originalEventId":"c284c91a-f5c7-39a3-a2e7-99450a6f7fce","correlationId":"40b72846-1c87-3164-9bac-0987ec309687","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | 0c87fdf5-5664-3737-a495-5288141aa423 | 2026-07-31T22:49:49Z | 2026-07-31T22:49:59Z | `{"operationId":"6e4874bd-e65a-3379-ae53-72bb80edc789","correlationId":"edac607a-a481-3a05-ae2f-7688d86d7c60","siteId":"SUD","locationId":null,"loanId":"1bb275d4-690a-32e8-a53b-4461af0a4504"}` |
| IntegrationProcessed | f28cc9f4-aa20-3e22-8bbb-ea5c6a0e9a89 | 2026-07-31T22:50:49Z | 2026-07-31T22:50:59Z | `{"originalEventId":"0c87fdf5-5664-3737-a495-5288141aa423","correlationId":"edac607a-a481-3a05-ae2f-7688d86d7c60","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | 1272b4b6-4d1b-39b5-be2f-39112f16d607 | 2026-08-20T22:49:49Z | 2026-08-20T22:49:59Z | `{"operationId":"ed1f924f-7aea-3378-91bb-5323b4bb2836","correlationId":"fffd53b0-ada1-3b34-9d5f-bfaef2f6e7b6","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"1bb275d4-690a-32e8-a53b-4461af0a4504"}` |
| ReturnReceived | 6c9bf1fe-5a3d-3bae-a08b-81258f803765 | 2026-08-20T22:50:19Z | 2026-08-20T22:50:29Z | `{"operationId":"92f4056e-9190-3393-8a48-ba14a7fa6399","correlationId":"83df6cb4-f259-35e7-b0f6-5074a36421be","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"1bb275d4-690a-32e8-a53b-4461af0a4504"}` |
| IntegrationProcessed | 6a40391b-f343-3da1-9f6c-75e5f16c9fe1 | 2026-08-21T22:49:49Z | 2026-08-21T22:49:59Z | `{"originalEventId":"6c9bf1fe-5a3d-3bae-a08b-81258f803765","correlationId":"83df6cb4-f259-35e7-b0f6-5074a36421be","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |

**observations**

**sourceAvailability**
- `{"copyId":"37fc5075-ff7e-386f-8d55-14f142bdc815","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"37fc5075-ff7e-386f-8d55-14f142bdc815","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"37fc5075-ff7e-386f-8d55-14f142bdc815","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"37fc5075-ff7e-386f-8d55-14f142bdc815","source":"TRANSFERS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"37fc5075-ff7e-386f-8d55-14f142bdc815","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"37fc5075-ff7e-386f-8d55-14f142bdc815","source":"LOANS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"37fc5075-ff7e-386f-8d55-14f142bdc815","source":"INVENTORY","status":"UNAVAILABLE","completeFrom":null,"completeTo":null}`

**operatorNotes**
- `{"noteId":"abf9fdc4-7d77-3630-94e4-fc3e64a99a73","copyId":"37fc5075-ff7e-386f-8d55-14f142bdc815","text":"Le passage au rayon n'a pas permis de retrouver cet exemplaire ; contrôle complémentaire demandé.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"37fc5075-ff7e-386f-8d55-14f142bdc815"},{"kind":"sourceEvent","id":"c284c91a-f5c7-39a3-a2e7-99450a6f7fce"},{"kind":"sourceEvent","id":"8c70ecc8-8fce-355e-a2ad-568b3044fc73"},{"kind":"sourceEvent","id":"0c87fdf5-5664-3737-a495-5288141aa423"},{"kind":"sourceEvent","id":"f28cc9f4-aa20-3e22-8bbb-ea5c6a0e9a89"},{"kind":"sourceEvent","id":"1272b4b6-4d1b-39b5-be2f-39112f16d607"},{"kind":"sourceEvent","id":"6c9bf1fe-5a3d-3bae-a08b-81258f803765"},{"kind":"sourceEvent","id":"6a40391b-f343-3da1-9f6c-75e5f16c9fe1"},{"kind":"sourceAvailability","id":"37fc5075-ff7e-386f-8d55-14f142bdc815/LOANS"},{"kind":"sourceAvailability","id":"37fc5075-ff7e-386f-8d55-14f142bdc815/RETURNS"},{"kind":"sourceAvailability","id":"37fc5075-ff7e-386f-8d55-14f142bdc815/RECEIPTS"},{"kind":"sourceAvailability","id":"37fc5075-ff7e-386f-8d55-14f142bdc815/TRANSFERS"},{"kind":"sourceAvailability","id":"37fc5075-ff7e-386f-8d55-14f142bdc815/REPAIRS"},{"kind":"sourceAvailability","id":"37fc5075-ff7e-386f-8d55-14f142bdc815/INTEGRATIONS"},{"kind":"sourceAvailability","id":"37fc5075-ff7e-386f-8d55-14f142bdc815/INVENTORY"},{"kind":"operatorNote","id":"abf9fdc4-7d77-3630-94e4-fc3e64a99a73"},{"kind":"inventoryScope","id":"294f8877-9130-3961-8435-48be641fa20c"},{"kind":"inventoryScope","id":"9d6c09df-1bb9-3fa2-927d-b44bcfe9e1be"},{"kind":"inventoryScope","id":"8ba7cc3d-833a-342a-bcaa-e1d23f56a4e3"},{"kind":"inventoryScope","id":"59269371-fb13-3ca1-a62a-01272d903229"},{"kind":"inventoryScope","id":"139f34be-b437-3f23-84c9-757fbea07b69"},{"kind":"inventoryScope","id":"139f34be-b437-3f23-84c9-757fbea07b69"}]`

- Session : `{"scopeId":"294f8877-9130-3961-8435-48be641fa20c","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9d6c09df-1bb9-3fa2-927d-b44bcfe9e1be","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"8ba7cc3d-833a-342a-bcaa-e1d23f56a4e3","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"59269371-fb13-3ca1-a62a-01272d903229","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"139f34be-b437-3f23-84c9-757fbea07b69","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"139f34be-b437-3f23-84c9-757fbea07b69","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 4ec5ec08-d932-3d78-a935-0ee2b0b22205

- Exemplaire : `47539f6c-0c9f-3730-9f5e-28bd257a9db3`
- Famille privée : RETOUR_NON_PROJETE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"bdc3f520-f953-3834-9a93-45d9fc8c631c","kind":"STATUS_MISMATCH","expectedValue":"ON_LOAN","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"57655303-f1c4-35c5-b259-a50ca6d7a1b1","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["RETURN_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"47539f6c-0c9f-3730-9f5e-28bd257a9db3","status":"ON_LOAN","siteId":"NORD","locationId":null,"sourceEventIds":["40ff4c00-9f8b-3019-b25f-bb46d20ebc4a","aecd43d5-1a34-3055-95f8-b7538456a4d0"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | e7034ed4-2592-3d93-b270-d7eece65b7fc | 2026-06-02T05:28:56Z | 2026-06-02T05:29:06Z | `{"operationId":"fdb28544-e7c6-3661-8409-ea3f21c48434","correlationId":"bdf946bd-0667-3135-b06a-86c94dd59929","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"9543cc3e-8692-38d9-b847-6051819153fd"}` |
| IntegrationProcessed | ce02182b-8fb9-31d3-b04f-244f0c9bcc01 | 2026-06-03T05:28:56Z | 2026-06-03T05:29:06Z | `{"originalEventId":"e7034ed4-2592-3d93-b270-d7eece65b7fc","correlationId":"bdf946bd-0667-3135-b06a-86c94dd59929","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 1a726872-e353-3b4a-bbd9-63b0a9143bd7 | 2026-08-01T05:28:56Z | 2026-08-01T05:29:06Z | `{"operationId":"9ef763e2-a5ad-3bfd-8b83-7a9b814f1a0a","correlationId":"4832f262-ef12-3a1f-a91c-3a3418c1c746","siteId":"CENTRE","locationId":null,"loanId":"9e69b100-6f30-30ae-9777-6d3dc7b2491b"}` |
| IntegrationProcessed | ede2636d-f3ee-3b98-98c1-5c74e8a4d14a | 2026-08-01T05:29:56Z | 2026-08-01T05:30:06Z | `{"originalEventId":"1a726872-e353-3b4a-bbd9-63b0a9143bd7","correlationId":"4832f262-ef12-3a1f-a91c-3a3418c1c746","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | faf7afc7-805a-36b4-8852-ed1f08d707c7 | 2026-08-21T05:28:56Z | 2026-08-21T05:29:06Z | `{"operationId":"5e351c2e-8595-3e98-8fd0-8c336cf8eebd","correlationId":"9b4c0e3c-f3b6-36e3-95a5-db39524315fa","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"9e69b100-6f30-30ae-9777-6d3dc7b2491b"}` |
| ReturnReceived | f2782944-016c-3e51-85cc-d03f024ff591 | 2026-08-21T05:29:26Z | 2026-08-21T05:29:36Z | `{"operationId":"8f0b69f4-cb60-35d3-b8ef-e2ed43eb27b1","correlationId":"5800382d-616a-325b-9421-318517af4e9b","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"9e69b100-6f30-30ae-9777-6d3dc7b2491b"}` |
| IntegrationProcessed | 5fe7a55b-c6c4-376b-944b-0648fa45f2d8 | 2026-08-22T05:28:56Z | 2026-08-22T05:29:06Z | `{"originalEventId":"f2782944-016c-3e51-85cc-d03f024ff591","correlationId":"5800382d-616a-325b-9421-318517af4e9b","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| TransferDispatched | 6bdf411d-d966-32b1-a87d-f87f5f361092 | 2026-08-31T05:28:56Z | 2026-08-31T05:29:06Z | `{"operationId":"babdd6da-7954-335e-bdbc-dae70414b971","correlationId":"71bec5dc-70ec-394f-9430-74598de6765a","siteId":"CENTRE","locationId":null,"transferId":"087c2ef2-92ed-3ecd-842d-76f9fee3fd53","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | 357117b6-50e7-3929-9cb2-4d198f47bedf | 2026-08-31T05:29:56Z | 2026-08-31T05:30:06Z | `{"originalEventId":"6bdf411d-d966-32b1-a87d-f87f5f361092","correlationId":"71bec5dc-70ec-394f-9430-74598de6765a","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| TransferReceived | 89063ff0-276f-33ac-911c-4e82591266ab | 2026-09-01T05:28:56Z | 2026-09-01T05:29:06Z | `{"operationId":"ab4c20e1-f993-3680-8dff-d996a044ba28","correlationId":"b9d8a478-f943-3a96-b149-8b9873170884","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","transferId":"087c2ef2-92ed-3ecd-842d-76f9fee3fd53","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | 002c2698-0f53-395f-960a-5582ff6395a4 | 2026-09-02T05:28:56Z | 2026-09-02T05:29:06Z | `{"originalEventId":"89063ff0-276f-33ac-911c-4e82591266ab","correlationId":"b9d8a478-f943-3a96-b149-8b9873170884","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 40ff4c00-9f8b-3019-b25f-bb46d20ebc4a | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"f9b3083c-f88d-3145-8bd8-3c9dabf1a54e","correlationId":"d7979b2e-e4fe-3c2d-bbb8-4aa0bbc1586d","siteId":"NORD","locationId":null,"loanId":"7d97482c-691c-350a-8c98-b2da31991849"}` |
| IntegrationProcessed | aecd43d5-1a34-3055-95f8-b7538456a4d0 | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"40ff4c00-9f8b-3019-b25f-bb46d20ebc4a","correlationId":"d7979b2e-e4fe-3c2d-bbb8-4aa0bbc1586d","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | 8813bb83-69b2-3e29-80f1-8f29fa009c92 | 2026-09-27T06:57:21Z | 2026-09-27T06:57:31Z | `{"operationId":"75373fe4-be23-3265-96be-5f022934a9f0","correlationId":"a047a4c9-a922-3c2f-9ae8-1b0b67bae8bf","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"7d97482c-691c-350a-8c98-b2da31991849"}` |
| ReturnReceived | 57655303-f1c4-35c5-b259-a50ca6d7a1b1 | 2026-09-27T06:57:51Z | 2026-09-27T06:58:01Z | `{"operationId":"2002b1c1-93c2-3c56-80a2-89e9cb0d47bd","correlationId":"3e9bdbf8-953c-3016-bf03-fd3c3c17ee45","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"7d97482c-691c-350a-8c98-b2da31991849"}` |
| IntegrationProcessed | 215763bf-0fa8-31d7-b342-5a1777d1433f | 2026-09-27T06:58:21Z | 2026-09-27T06:58:31Z | `{"originalEventId":"57655303-f1c4-35c5-b259-a50ca6d7a1b1","correlationId":"3e9bdbf8-953c-3016-bf03-fd3c3c17ee45","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**observations**
- `{"observationId":"87729ce2-3696-3e45-b814-bacfedb39f70","copyId":"47539f6c-0c9f-3730-9f5e-28bd257a9db3","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","scopeId":"1f02f17b-5558-3e7a-82cb-80f27f2cf2d1","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"47539f6c-0c9f-3730-9f5e-28bd257a9db3","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"47539f6c-0c9f-3730-9f5e-28bd257a9db3","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"47539f6c-0c9f-3730-9f5e-28bd257a9db3","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"47539f6c-0c9f-3730-9f5e-28bd257a9db3","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"47539f6c-0c9f-3730-9f5e-28bd257a9db3","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"47539f6c-0c9f-3730-9f5e-28bd257a9db3","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"47539f6c-0c9f-3730-9f5e-28bd257a9db3","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"47539f6c-0c9f-3730-9f5e-28bd257a9db3"},{"kind":"sourceEvent","id":"e7034ed4-2592-3d93-b270-d7eece65b7fc"},{"kind":"sourceEvent","id":"ce02182b-8fb9-31d3-b04f-244f0c9bcc01"},{"kind":"sourceEvent","id":"1a726872-e353-3b4a-bbd9-63b0a9143bd7"},{"kind":"sourceEvent","id":"ede2636d-f3ee-3b98-98c1-5c74e8a4d14a"},{"kind":"sourceEvent","id":"faf7afc7-805a-36b4-8852-ed1f08d707c7"},{"kind":"sourceEvent","id":"f2782944-016c-3e51-85cc-d03f024ff591"},{"kind":"sourceEvent","id":"5fe7a55b-c6c4-376b-944b-0648fa45f2d8"},{"kind":"sourceEvent","id":"6bdf411d-d966-32b1-a87d-f87f5f361092"},{"kind":"sourceEvent","id":"357117b6-50e7-3929-9cb2-4d198f47bedf"},{"kind":"sourceEvent","id":"89063ff0-276f-33ac-911c-4e82591266ab"},{"kind":"sourceEvent","id":"002c2698-0f53-395f-960a-5582ff6395a4"},{"kind":"sourceEvent","id":"40ff4c00-9f8b-3019-b25f-bb46d20ebc4a"},{"kind":"sourceEvent","id":"aecd43d5-1a34-3055-95f8-b7538456a4d0"},{"kind":"sourceEvent","id":"8813bb83-69b2-3e29-80f1-8f29fa009c92"},{"kind":"sourceEvent","id":"57655303-f1c4-35c5-b259-a50ca6d7a1b1"},{"kind":"sourceEvent","id":"215763bf-0fa8-31d7-b342-5a1777d1433f"},{"kind":"observation","id":"87729ce2-3696-3e45-b814-bacfedb39f70"},{"kind":"inventoryScope","id":"1f02f17b-5558-3e7a-82cb-80f27f2cf2d1"},{"kind":"sourceAvailability","id":"47539f6c-0c9f-3730-9f5e-28bd257a9db3/LOANS"},{"kind":"sourceAvailability","id":"47539f6c-0c9f-3730-9f5e-28bd257a9db3/RETURNS"},{"kind":"sourceAvailability","id":"47539f6c-0c9f-3730-9f5e-28bd257a9db3/RECEIPTS"},{"kind":"sourceAvailability","id":"47539f6c-0c9f-3730-9f5e-28bd257a9db3/TRANSFERS"},{"kind":"sourceAvailability","id":"47539f6c-0c9f-3730-9f5e-28bd257a9db3/REPAIRS"},{"kind":"sourceAvailability","id":"47539f6c-0c9f-3730-9f5e-28bd257a9db3/INTEGRATIONS"},{"kind":"sourceAvailability","id":"47539f6c-0c9f-3730-9f5e-28bd257a9db3/INVENTORY"}]`

- Session : `{"scopeId":"1f02f17b-5558-3e7a-82cb-80f27f2cf2d1","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 4f092b8c-de42-395d-b571-ec96aeb6d04a

- Exemplaire : `8a9e2ca5-abbf-352b-a869-ea4b8b7150ec`
- Famille privée : LOCALISATION ; variante : POSITIVE
- Symptômes : `[{"symptomId":"f6ab2c57-768a-37c1-a718-2bc314c313f4","kind":"LOCATION_MISMATCH","expectedValue":"4a5364f2-388b-351d-91f2-d1364d119042","observedValue":"b8600ac8-b5d3-3580-b3c5-71a711df1a50"}]`
- Vérité injectée (non accessible au diagnostic) : `{"mechanism":"Déplacement physique sans modification de l'affectation ; aucune attribution à un opérateur.","activePerturbations":["LOCATION_CHANGE_UNPROJECTED"],"physicalStatus":"ON_SHELF","physicalSiteId":"CENTRE","physicalLocationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["LOCATION_MISMATCH"],"explanation":"Observation unique valide à un autre emplacement du même site ; écart établi, origine humaine non prouvée.","forbiddenClaims":["Vol","Perte définitive","Responsabilité d'un opérateur"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"8a9e2ca5-abbf-352b-a869-ea4b8b7150ec","status":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","sourceEventIds":["a8180078-9641-3cd7-94e7-bd2c19358e79","762efa6e-4043-32a3-9c87-0a944c9d3e91"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 0390a3f0-d6a8-32d7-a636-500f391efa7c | 2026-06-02T08:56:41Z | 2026-06-02T08:56:51Z | `{"operationId":"e9177a36-2a74-361f-a9fe-484322957dae","correlationId":"0793c8a3-f127-3b06-a13f-4ce03262223c","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"6b02bed8-ddfc-3994-a6fc-e67341342fad"}` |
| IntegrationProcessed | 0ccc0d77-5f6b-3aa1-ab2b-41a9c180d981 | 2026-06-03T08:56:41Z | 2026-06-03T08:56:51Z | `{"originalEventId":"0390a3f0-d6a8-32d7-a636-500f391efa7c","correlationId":"0793c8a3-f127-3b06-a13f-4ce03262223c","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 6d0af6ba-a349-30a2-836c-e4f9fa0d9893 | 2026-08-01T08:56:41Z | 2026-08-01T08:56:51Z | `{"operationId":"2c675c03-a9aa-3546-81b7-70030166fc16","correlationId":"10d9d3fa-7034-3578-adfc-1251ca38707f","siteId":"CENTRE","locationId":null,"loanId":"425fa9d5-02b0-3d8d-bcb2-26c8c3e21128"}` |
| IntegrationProcessed | e381e3ea-f167-36c6-bc7d-8ef7e4e1d3c6 | 2026-08-01T08:57:41Z | 2026-08-01T08:57:51Z | `{"originalEventId":"6d0af6ba-a349-30a2-836c-e4f9fa0d9893","correlationId":"10d9d3fa-7034-3578-adfc-1251ca38707f","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 17c2ab50-9a81-3a12-a93e-5590c266fba7 | 2026-08-21T08:56:41Z | 2026-08-21T08:56:51Z | `{"operationId":"4776c54c-52a6-3790-bdfb-22ca7331e79e","correlationId":"9f9e9d24-6bb5-35fd-912f-a4c47af7624a","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"425fa9d5-02b0-3d8d-bcb2-26c8c3e21128"}` |
| ReturnReceived | a8180078-9641-3cd7-94e7-bd2c19358e79 | 2026-08-21T08:57:11Z | 2026-08-21T08:57:21Z | `{"operationId":"016276ce-6e2d-34de-8b2b-1d97f12f2779","correlationId":"7fd55e78-4b92-3e3a-8ea3-40b696dae165","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"425fa9d5-02b0-3d8d-bcb2-26c8c3e21128"}` |
| IntegrationProcessed | 762efa6e-4043-32a3-9c87-0a944c9d3e91 | 2026-08-22T08:56:41Z | 2026-08-22T08:56:51Z | `{"originalEventId":"a8180078-9641-3cd7-94e7-bd2c19358e79","correlationId":"7fd55e78-4b92-3e3a-8ea3-40b696dae165","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |

**observations**
- `{"observationId":"d4658bf6-255e-3203-8832-a796115a3eea","copyId":"8a9e2ca5-abbf-352b-a869-ea4b8b7150ec","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","scopeId":"64fca6b5-8bd0-3ab9-a6d6-8b413e874bb1","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"8a9e2ca5-abbf-352b-a869-ea4b8b7150ec","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8a9e2ca5-abbf-352b-a869-ea4b8b7150ec","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8a9e2ca5-abbf-352b-a869-ea4b8b7150ec","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8a9e2ca5-abbf-352b-a869-ea4b8b7150ec","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8a9e2ca5-abbf-352b-a869-ea4b8b7150ec","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8a9e2ca5-abbf-352b-a869-ea4b8b7150ec","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8a9e2ca5-abbf-352b-a869-ea4b8b7150ec","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"observation","id":"d4658bf6-255e-3203-8832-a796115a3eea"},{"kind":"systemState","id":"8a9e2ca5-abbf-352b-a869-ea4b8b7150ec"}]`

## Cas 5315201f-c537-3647-8b15-30913e15be4e

- Exemplaire : `95291f04-cb55-3a6d-a3fe-c0e1ced69ca9`
- Famille privée : ABSENCE_INEXPLIQUEE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"c7afd5cf-9b51-31d4-84cf-56ddf2fc68c0","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":"1"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"mechanism":"Exemplaire resté au rayon, lecture de contrôle réussie.","physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"Le scan valide au rayon attendu réfute l'absence courante.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"95291f04-cb55-3a6d-a3fe-c0e1ced69ca9","status":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","sourceEventIds":["ea076104-be74-3588-9532-573091ed6c07","f5ec1b98-ad7c-37a9-8883-f17017f638b2"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 3ee2f124-88a9-3738-88b5-35f175695594 | 2026-06-02T09:05:38Z | 2026-06-02T09:05:48Z | `{"operationId":"b03924e1-a56f-3524-aeb3-04ab52a1f7cd","correlationId":"85166780-4eb9-39b7-b965-569c62bc4541","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"377dd9a2-f094-3f71-afb6-c15db7e53ebf"}` |
| IntegrationProcessed | 76dce4fb-c2cd-3962-b2e9-71e9cfb0896a | 2026-06-03T09:05:38Z | 2026-06-03T09:05:48Z | `{"originalEventId":"3ee2f124-88a9-3738-88b5-35f175695594","correlationId":"85166780-4eb9-39b7-b965-569c62bc4541","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 73f1bdac-e12b-394e-a283-65930ab0cd49 | 2026-08-01T09:05:38Z | 2026-08-01T09:05:48Z | `{"operationId":"b6f15ee4-5db8-341b-a326-e9affc8e0169","correlationId":"45db37ea-6cd5-31f2-8f81-775fb0fbf8ba","siteId":"NORD","locationId":null,"loanId":"a6118852-678c-3f56-874f-4398b47b2df8"}` |
| IntegrationProcessed | c388d326-698e-354b-9399-8260145af278 | 2026-08-01T09:06:38Z | 2026-08-01T09:06:48Z | `{"originalEventId":"73f1bdac-e12b-394e-a283-65930ab0cd49","correlationId":"45db37ea-6cd5-31f2-8f81-775fb0fbf8ba","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | 0b71d853-7cf0-331c-a46c-148f550ffba2 | 2026-08-21T09:05:38Z | 2026-08-21T09:05:48Z | `{"operationId":"8a38c8a2-3895-3ce7-a6e0-6d806c9d35f4","correlationId":"67519d39-f51b-39e8-9f5d-62f17a95c12d","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"a6118852-678c-3f56-874f-4398b47b2df8"}` |
| ReturnReceived | ea076104-be74-3588-9532-573091ed6c07 | 2026-08-21T09:06:08Z | 2026-08-21T09:06:18Z | `{"operationId":"7763fa02-ada6-3790-b184-bf0ad8a153d9","correlationId":"3544923a-c2ff-301f-9096-c12d2d462769","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"a6118852-678c-3f56-874f-4398b47b2df8"}` |
| IntegrationProcessed | f5ec1b98-ad7c-37a9-8883-f17017f638b2 | 2026-08-22T09:05:38Z | 2026-08-22T09:05:48Z | `{"originalEventId":"ea076104-be74-3588-9532-573091ed6c07","correlationId":"3544923a-c2ff-301f-9096-c12d2d462769","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |

**observations**
- `{"observationId":"d2f29bd8-35de-3f6f-93b2-d98558207689","copyId":"95291f04-cb55-3a6d-a3fe-c0e1ced69ca9","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","scopeId":"52bd3f79-07d0-3284-a51a-c100a5c5022e","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"95291f04-cb55-3a6d-a3fe-c0e1ced69ca9","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"95291f04-cb55-3a6d-a3fe-c0e1ced69ca9","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"95291f04-cb55-3a6d-a3fe-c0e1ced69ca9","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"95291f04-cb55-3a6d-a3fe-c0e1ced69ca9","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"95291f04-cb55-3a6d-a3fe-c0e1ced69ca9","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"95291f04-cb55-3a6d-a3fe-c0e1ced69ca9","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"95291f04-cb55-3a6d-a3fe-c0e1ced69ca9","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**
- `{"noteId":"21052ba9-a94b-37fd-956a-94c0eb1dee15","copyId":"95291f04-cb55-3a6d-a3fe-c0e1ced69ca9","text":"Nouvelle lecture du code-barres effectuée dans le rayon affecté.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"95291f04-cb55-3a6d-a3fe-c0e1ced69ca9"},{"kind":"sourceEvent","id":"3ee2f124-88a9-3738-88b5-35f175695594"},{"kind":"sourceEvent","id":"76dce4fb-c2cd-3962-b2e9-71e9cfb0896a"},{"kind":"sourceEvent","id":"73f1bdac-e12b-394e-a283-65930ab0cd49"},{"kind":"sourceEvent","id":"c388d326-698e-354b-9399-8260145af278"},{"kind":"sourceEvent","id":"0b71d853-7cf0-331c-a46c-148f550ffba2"},{"kind":"sourceEvent","id":"ea076104-be74-3588-9532-573091ed6c07"},{"kind":"sourceEvent","id":"f5ec1b98-ad7c-37a9-8883-f17017f638b2"},{"kind":"observation","id":"d2f29bd8-35de-3f6f-93b2-d98558207689"},{"kind":"inventoryScope","id":"52bd3f79-07d0-3284-a51a-c100a5c5022e"},{"kind":"sourceAvailability","id":"95291f04-cb55-3a6d-a3fe-c0e1ced69ca9/LOANS"},{"kind":"sourceAvailability","id":"95291f04-cb55-3a6d-a3fe-c0e1ced69ca9/RETURNS"},{"kind":"sourceAvailability","id":"95291f04-cb55-3a6d-a3fe-c0e1ced69ca9/RECEIPTS"},{"kind":"sourceAvailability","id":"95291f04-cb55-3a6d-a3fe-c0e1ced69ca9/TRANSFERS"},{"kind":"sourceAvailability","id":"95291f04-cb55-3a6d-a3fe-c0e1ced69ca9/REPAIRS"},{"kind":"sourceAvailability","id":"95291f04-cb55-3a6d-a3fe-c0e1ced69ca9/INTEGRATIONS"},{"kind":"sourceAvailability","id":"95291f04-cb55-3a6d-a3fe-c0e1ced69ca9/INVENTORY"},{"kind":"operatorNote","id":"21052ba9-a94b-37fd-956a-94c0eb1dee15"},{"kind":"inventoryScope","id":"07c957d2-b086-3b6b-9915-d21f1d5906cf"},{"kind":"inventoryScope","id":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c"},{"kind":"inventoryScope","id":"a481af0e-6045-37c4-bb84-d5269f5addc5"},{"kind":"inventoryScope","id":"9880a39c-a8f4-3f61-a473-804a0fd611a5"},{"kind":"inventoryScope","id":"79157bb9-b809-3388-ad1a-42d980616ea0"},{"kind":"inventoryScope","id":"eba23a81-5103-3ded-ba6b-6619213a7840"},{"kind":"inventoryScope","id":"6c49954b-e555-36a5-ab71-af60ea210c9b"},{"kind":"inventoryScope","id":"52bd3f79-07d0-3284-a51a-c100a5c5022e"}]`

- Session : `{"scopeId":"52bd3f79-07d0-3284-a51a-c100a5c5022e","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"07c957d2-b086-3b6b-9915-d21f1d5906cf","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"a481af0e-6045-37c4-bb84-d5269f5addc5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9880a39c-a8f4-3f61-a473-804a0fd611a5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"79157bb9-b809-3388-ad1a-42d980616ea0","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"eba23a81-5103-3ded-ba6b-6619213a7840","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"6c49954b-e555-36a5-ab71-af60ea210c9b","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"52bd3f79-07d0-3284-a51a-c100a5c5022e","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 55c50fa6-85e7-3801-a28b-cd87066c9a0b

- Exemplaire : `3193bec4-a4bc-3552-baf5-5b87afd43529`
- Famille privée : RETARD_INTEGRATION ; variante : POSITIVE
- Symptômes : `[{"symptomId":"66fc993d-28d8-3868-bfec-31b641ae32b1","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["QUEUE_PENDING"],"hiddenSourceEvents":[],"mechanism":"Événement métier en attente normale dans la fenêtre contractuelle de 900 secondes.","businessEventId":"532cb2f4-554b-3c31-aeee-dccaa9bfed4e","physicalStatus":"ON_LOAN","physicalSiteId":"NORD","physicalLocationId":null,"projectionPerturbation":"QUEUED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["INTEGRATION_PENDING_WITHIN_SLA"],"explanation":"Prêt actif et traitement en file depuis moins de 900 secondes ; transition attendue, pas de panne persistante démontrée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Recontrôler après la fenêtre de traitement"]}`
- État projeté : `{"copyId":"3193bec4-a4bc-3552-baf5-5b87afd43529","status":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","sourceEventIds":["c9ceb13a-09cd-3a81-ad96-6ad197b7c251","f0463195-9840-30e5-b62b-7ddae7e0ac9b"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 072f560c-52bd-36ac-b9a8-d48e04478c22 | 2026-06-01T13:13:55Z | 2026-06-01T13:14:05Z | `{"operationId":"d361dd70-abd1-3407-aa36-2356331c063d","correlationId":"f704f617-7d8c-34ab-ad9c-dcf064f3a558","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"c89f67f7-7af1-3eca-a5d6-04ae03fd525b"}` |
| IntegrationProcessed | f03b13a1-d117-3b87-b8d0-abeedc02ce16 | 2026-06-02T13:13:55Z | 2026-06-02T13:14:05Z | `{"originalEventId":"072f560c-52bd-36ac-b9a8-d48e04478c22","correlationId":"f704f617-7d8c-34ab-ad9c-dcf064f3a558","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | a2b948e7-0373-34d0-8c96-385074ac7f70 | 2026-07-31T13:13:55Z | 2026-07-31T13:14:05Z | `{"operationId":"fa2fe016-1b45-3e8d-a2e3-bb1f51b1f4fa","correlationId":"b21d3469-b46b-3eda-8fb6-2fcbbcda2065","siteId":"NORD","locationId":null,"loanId":"9b3832be-3979-3238-8fee-873fb9026920"}` |
| IntegrationProcessed | 3dc10dc6-feb0-3e41-9b42-08a34b13cb5e | 2026-07-31T13:14:55Z | 2026-07-31T13:15:05Z | `{"originalEventId":"a2b948e7-0373-34d0-8c96-385074ac7f70","correlationId":"b21d3469-b46b-3eda-8fb6-2fcbbcda2065","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | c9345605-7cb2-3243-8a4d-b8d85fbec567 | 2026-08-20T13:13:55Z | 2026-08-20T13:14:05Z | `{"operationId":"3deeeba3-8b83-34fc-b1f1-02b32945b9c6","correlationId":"1ef89fa5-08ef-3e98-95a4-2051e75c56b0","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"9b3832be-3979-3238-8fee-873fb9026920"}` |
| ReturnReceived | c9ceb13a-09cd-3a81-ad96-6ad197b7c251 | 2026-08-20T13:14:25Z | 2026-08-20T13:14:35Z | `{"operationId":"daffd25d-25e8-3d33-be83-8c02d42c930a","correlationId":"d87c36db-cdf5-39d5-a711-55a9a1cd9fa2","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"9b3832be-3979-3238-8fee-873fb9026920"}` |
| IntegrationProcessed | f0463195-9840-30e5-b62b-7ddae7e0ac9b | 2026-08-21T13:13:55Z | 2026-08-21T13:14:05Z | `{"originalEventId":"c9ceb13a-09cd-3a81-ad96-6ad197b7c251","correlationId":"d87c36db-cdf5-39d5-a711-55a9a1cd9fa2","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 532cb2f4-554b-3c31-aeee-dccaa9bfed4e | 2026-09-30T11:51:42Z | 2026-09-30T11:51:52Z | `{"operationId":"6da680f0-f33e-3215-abe9-54e3a29ec3e3","correlationId":"31c67186-bbd0-3f30-8784-a3f04d85c574","siteId":"NORD","locationId":null,"loanId":"0fd854ff-73de-38c6-bd66-d198eb8ef7dc"}` |
| IntegrationProcessed | ab257189-00fb-3359-b174-56b0b9c72c00 | 2026-09-30T11:52:12Z | 2026-09-30T11:52:22Z | `{"originalEventId":"532cb2f4-554b-3c31-aeee-dccaa9bfed4e","correlationId":"31c67186-bbd0-3f30-8784-a3f04d85c574","outcome":"QUEUED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1}` |

**observations**

**sourceAvailability**
- `{"copyId":"3193bec4-a4bc-3552-baf5-5b87afd43529","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3193bec4-a4bc-3552-baf5-5b87afd43529","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3193bec4-a4bc-3552-baf5-5b87afd43529","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3193bec4-a4bc-3552-baf5-5b87afd43529","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3193bec4-a4bc-3552-baf5-5b87afd43529","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3193bec4-a4bc-3552-baf5-5b87afd43529","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"3193bec4-a4bc-3552-baf5-5b87afd43529","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"3193bec4-a4bc-3552-baf5-5b87afd43529"},{"kind":"sourceEvent","id":"072f560c-52bd-36ac-b9a8-d48e04478c22"},{"kind":"sourceEvent","id":"f03b13a1-d117-3b87-b8d0-abeedc02ce16"},{"kind":"sourceEvent","id":"a2b948e7-0373-34d0-8c96-385074ac7f70"},{"kind":"sourceEvent","id":"3dc10dc6-feb0-3e41-9b42-08a34b13cb5e"},{"kind":"sourceEvent","id":"c9345605-7cb2-3243-8a4d-b8d85fbec567"},{"kind":"sourceEvent","id":"c9ceb13a-09cd-3a81-ad96-6ad197b7c251"},{"kind":"sourceEvent","id":"f0463195-9840-30e5-b62b-7ddae7e0ac9b"},{"kind":"sourceEvent","id":"532cb2f4-554b-3c31-aeee-dccaa9bfed4e"},{"kind":"sourceEvent","id":"ab257189-00fb-3359-b174-56b0b9c72c00"},{"kind":"sourceAvailability","id":"3193bec4-a4bc-3552-baf5-5b87afd43529/LOANS"},{"kind":"sourceAvailability","id":"3193bec4-a4bc-3552-baf5-5b87afd43529/RETURNS"},{"kind":"sourceAvailability","id":"3193bec4-a4bc-3552-baf5-5b87afd43529/RECEIPTS"},{"kind":"sourceAvailability","id":"3193bec4-a4bc-3552-baf5-5b87afd43529/TRANSFERS"},{"kind":"sourceAvailability","id":"3193bec4-a4bc-3552-baf5-5b87afd43529/REPAIRS"},{"kind":"sourceAvailability","id":"3193bec4-a4bc-3552-baf5-5b87afd43529/INTEGRATIONS"},{"kind":"sourceAvailability","id":"3193bec4-a4bc-3552-baf5-5b87afd43529/INVENTORY"},{"kind":"inventoryScope","id":"07c957d2-b086-3b6b-9915-d21f1d5906cf"},{"kind":"inventoryScope","id":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c"},{"kind":"inventoryScope","id":"a481af0e-6045-37c4-bb84-d5269f5addc5"},{"kind":"inventoryScope","id":"9880a39c-a8f4-3f61-a473-804a0fd611a5"},{"kind":"inventoryScope","id":"79157bb9-b809-3388-ad1a-42d980616ea0"}]`

- Session : `{"scopeId":"07c957d2-b086-3b6b-9915-d21f1d5906cf","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"a481af0e-6045-37c4-bb84-d5269f5addc5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9880a39c-a8f4-3f61-a473-804a0fd611a5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"79157bb9-b809-3388-ad1a-42d980616ea0","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 561e5664-4326-3329-ada0-2e0d8f07452a

- Exemplaire : `e4f4f59b-6ed2-3c86-b0ee-cef831999e6c`
- Famille privée : PRET_NON_PROJETE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"2bd76a37-4c8d-3926-b4e1-7f5af27cda50","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"de24a23d-a1e6-3cdb-8b41-07f5372be42a","physicalStatus":"ON_LOAN","physicalSiteId":"SUD","physicalLocationId":null,"projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"e4f4f59b-6ed2-3c86-b0ee-cef831999e6c","status":"ON_LOAN","siteId":"SUD","locationId":null,"sourceEventIds":["de24a23d-a1e6-3cdb-8b41-07f5372be42a","42d0e682-0a44-3e10-bfa4-06f7f231b55f"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 0a70198e-31a6-3dea-98b7-d6a61f72c163 | 2026-06-01T20:23:23Z | 2026-06-01T20:23:33Z | `{"operationId":"7c6d62c8-c71e-321e-88b8-89251094cc2d","correlationId":"34378bd9-bca0-3077-b02e-7c8cc494c169","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"fe7c2035-65db-3db1-a0c9-7bfb042d8fc1"}` |
| IntegrationProcessed | d2ddf992-dbf5-3e22-b649-046b2f676b95 | 2026-06-02T20:23:23Z | 2026-06-02T20:23:33Z | `{"originalEventId":"0a70198e-31a6-3dea-98b7-d6a61f72c163","correlationId":"34378bd9-bca0-3077-b02e-7c8cc494c169","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | a619233a-7cfa-3c5a-9eae-2c2a1ba85a07 | 2026-07-31T20:23:23Z | 2026-07-31T20:23:33Z | `{"operationId":"918c49b1-2d80-35f1-9628-94c07e8184df","correlationId":"e308e962-755d-3479-95bf-c046ab82fa0f","siteId":"SUD","locationId":null,"loanId":"4ae12f7e-d802-3999-985e-2d9ba23fce39"}` |
| IntegrationProcessed | 2c0f5e1e-50ed-36b0-a2e2-34afc23a51d2 | 2026-07-31T20:24:23Z | 2026-07-31T20:24:33Z | `{"originalEventId":"a619233a-7cfa-3c5a-9eae-2c2a1ba85a07","correlationId":"e308e962-755d-3479-95bf-c046ab82fa0f","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | d356879b-905a-3db9-a165-a2654777a4ec | 2026-08-20T20:23:23Z | 2026-08-20T20:23:33Z | `{"operationId":"e3ff8968-4936-3318-b172-a27612265709","correlationId":"4eb09f9f-8f4a-3398-9ec2-0d12c6030ac1","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"4ae12f7e-d802-3999-985e-2d9ba23fce39"}` |
| ReturnReceived | 8f370363-fd01-349a-976b-00bc970ffb7f | 2026-08-20T20:23:53Z | 2026-08-20T20:24:03Z | `{"operationId":"37d9d9af-931a-3570-bd96-43367dc61350","correlationId":"3b62d003-d007-32b6-94cb-598a6ddcdac6","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"4ae12f7e-d802-3999-985e-2d9ba23fce39"}` |
| IntegrationProcessed | 5d59f730-6c09-3877-8ab7-8bb905fad385 | 2026-08-21T20:23:23Z | 2026-08-21T20:23:33Z | `{"originalEventId":"8f370363-fd01-349a-976b-00bc970ffb7f","correlationId":"3b62d003-d007-32b6-94cb-598a6ddcdac6","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | de24a23d-a1e6-3cdb-8b41-07f5372be42a | 2026-09-27T11:39:03Z | 2026-09-27T11:39:13Z | `{"operationId":"bdb88755-992c-3333-b328-23dd69029454","correlationId":"e9c91143-9b6f-3612-b3fb-bdb4df0a38c6","siteId":"SUD","locationId":null,"loanId":"34fe10b3-9c8e-3cd1-8e80-663f981528e1"}` |
| IntegrationProcessed | 841973b1-cfa4-3103-8e86-64f9050c23e9 | 2026-09-27T11:39:33Z | 2026-09-27T11:39:43Z | `{"originalEventId":"de24a23d-a1e6-3cdb-8b41-07f5372be42a","correlationId":"e9c91143-9b6f-3612-b3fb-bdb4df0a38c6","outcome":"FAILED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| IntegrationProcessed | 42d0e682-0a44-3e10-bfa4-06f7f231b55f | 2026-09-27T11:40:33Z | 2026-09-27T11:40:43Z | `{"originalEventId":"de24a23d-a1e6-3cdb-8b41-07f5372be42a","correlationId":"e9c91143-9b6f-3612-b3fb-bdb4df0a38c6","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |

**observations**
- `{"observationId":"a6b39548-2124-3d72-bed5-9c5bbf549f8b","copyId":"e4f4f59b-6ed2-3c86-b0ee-cef831999e6c","locationId":"205b3714-25eb-3b7f-aead-24616287f71a","scopeId":"11d3679a-d478-3c69-b75e-823d3779394c","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**sourceAvailability**
- `{"copyId":"e4f4f59b-6ed2-3c86-b0ee-cef831999e6c","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e4f4f59b-6ed2-3c86-b0ee-cef831999e6c","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e4f4f59b-6ed2-3c86-b0ee-cef831999e6c","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e4f4f59b-6ed2-3c86-b0ee-cef831999e6c","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e4f4f59b-6ed2-3c86-b0ee-cef831999e6c","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e4f4f59b-6ed2-3c86-b0ee-cef831999e6c","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e4f4f59b-6ed2-3c86-b0ee-cef831999e6c","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"e4f4f59b-6ed2-3c86-b0ee-cef831999e6c"},{"kind":"sourceEvent","id":"0a70198e-31a6-3dea-98b7-d6a61f72c163"},{"kind":"sourceEvent","id":"d2ddf992-dbf5-3e22-b649-046b2f676b95"},{"kind":"sourceEvent","id":"a619233a-7cfa-3c5a-9eae-2c2a1ba85a07"},{"kind":"sourceEvent","id":"2c0f5e1e-50ed-36b0-a2e2-34afc23a51d2"},{"kind":"sourceEvent","id":"d356879b-905a-3db9-a165-a2654777a4ec"},{"kind":"sourceEvent","id":"8f370363-fd01-349a-976b-00bc970ffb7f"},{"kind":"sourceEvent","id":"5d59f730-6c09-3877-8ab7-8bb905fad385"},{"kind":"sourceEvent","id":"de24a23d-a1e6-3cdb-8b41-07f5372be42a"},{"kind":"sourceEvent","id":"841973b1-cfa4-3103-8e86-64f9050c23e9"},{"kind":"sourceEvent","id":"42d0e682-0a44-3e10-bfa4-06f7f231b55f"},{"kind":"observation","id":"a6b39548-2124-3d72-bed5-9c5bbf549f8b"},{"kind":"inventoryScope","id":"11d3679a-d478-3c69-b75e-823d3779394c"},{"kind":"sourceAvailability","id":"e4f4f59b-6ed2-3c86-b0ee-cef831999e6c/LOANS"},{"kind":"sourceAvailability","id":"e4f4f59b-6ed2-3c86-b0ee-cef831999e6c/RETURNS"},{"kind":"sourceAvailability","id":"e4f4f59b-6ed2-3c86-b0ee-cef831999e6c/RECEIPTS"},{"kind":"sourceAvailability","id":"e4f4f59b-6ed2-3c86-b0ee-cef831999e6c/TRANSFERS"},{"kind":"sourceAvailability","id":"e4f4f59b-6ed2-3c86-b0ee-cef831999e6c/REPAIRS"},{"kind":"sourceAvailability","id":"e4f4f59b-6ed2-3c86-b0ee-cef831999e6c/INTEGRATIONS"},{"kind":"sourceAvailability","id":"e4f4f59b-6ed2-3c86-b0ee-cef831999e6c/INVENTORY"}]`

- Session : `{"scopeId":"11d3679a-d478-3c69-b75e-823d3779394c","locationId":"205b3714-25eb-3b7f-aead-24616287f71a","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

## Cas 579810fe-ac22-3803-b079-3b601ebe7fc6

- Exemplaire : `041c4a55-abef-38a9-9200-26ab40ec74e9`
- Famille privée : LOCALISATION ; variante : POSITIVE
- Symptômes : `[{"symptomId":"00cdff55-fb1b-3ad2-8820-86e978f26b83","kind":"LOCATION_MISMATCH","expectedValue":"63e3aa91-8217-35a3-b458-6bae9b374c87","observedValue":"205b3714-25eb-3b7f-aead-24616287f71a"}]`
- Vérité injectée (non accessible au diagnostic) : `{"mechanism":"Déplacement physique sans modification de l'affectation ; aucune attribution à un opérateur.","activePerturbations":["LOCATION_CHANGE_UNPROJECTED"],"physicalStatus":"ON_SHELF","physicalSiteId":"SUD","physicalLocationId":"205b3714-25eb-3b7f-aead-24616287f71a"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["LOCATION_MISMATCH"],"explanation":"Observation unique valide à un autre emplacement du même site ; écart établi, origine humaine non prouvée.","forbiddenClaims":["Vol","Perte définitive","Responsabilité d'un opérateur"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"041c4a55-abef-38a9-9200-26ab40ec74e9","status":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","sourceEventIds":["39f6d87a-020d-33e3-b812-ac3843eba262","517bd11c-0664-365f-9a90-f551a4d1a2a3"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 9b13341f-1c59-328d-90a6-def74f9d79e7 | 2026-06-02T06:39:19Z | 2026-06-02T06:39:29Z | `{"operationId":"295120ce-7d40-3921-bf28-790d9daf5abe","correlationId":"c0ca5d36-bc0b-36ba-9b03-a8f6b6955d0e","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"632b9e84-ee5a-3a99-88ed-8257a4f3352c"}` |
| IntegrationProcessed | 57f9eacf-1769-3d4b-ad69-b042f3ca7639 | 2026-06-03T06:39:19Z | 2026-06-03T06:39:29Z | `{"originalEventId":"9b13341f-1c59-328d-90a6-def74f9d79e7","correlationId":"c0ca5d36-bc0b-36ba-9b03-a8f6b6955d0e","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | 03c581b5-38fc-33e1-9ead-8bff29ed958e | 2026-08-01T06:39:19Z | 2026-08-01T06:39:29Z | `{"operationId":"e46a148e-7354-3481-aa11-b36744364111","correlationId":"0f1850e3-05af-3f93-a594-1d0fa6c5aa0e","siteId":"SUD","locationId":null,"loanId":"190dd1ec-fbfe-3f0e-85cb-32a53aaeeee9"}` |
| IntegrationProcessed | ddf66e55-1c8a-3339-b7fe-721b3dea15bb | 2026-08-01T06:40:19Z | 2026-08-01T06:40:29Z | `{"originalEventId":"03c581b5-38fc-33e1-9ead-8bff29ed958e","correlationId":"0f1850e3-05af-3f93-a594-1d0fa6c5aa0e","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | b0709b45-b019-3839-8164-b2ebb288083d | 2026-08-21T06:39:19Z | 2026-08-21T06:39:29Z | `{"operationId":"4b7e6fe5-a712-3668-b51f-1f7231cec998","correlationId":"fb9da0c1-7a20-32e8-9b3e-e63c26f61535","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"190dd1ec-fbfe-3f0e-85cb-32a53aaeeee9"}` |
| ReturnReceived | 39f6d87a-020d-33e3-b812-ac3843eba262 | 2026-08-21T06:39:49Z | 2026-08-21T06:39:59Z | `{"operationId":"3772afba-1d47-3d78-ac60-34a6cd44b718","correlationId":"f472fd83-273e-309d-ab0f-06f15354f55d","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"190dd1ec-fbfe-3f0e-85cb-32a53aaeeee9"}` |
| IntegrationProcessed | 517bd11c-0664-365f-9a90-f551a4d1a2a3 | 2026-08-22T06:39:19Z | 2026-08-22T06:39:29Z | `{"originalEventId":"39f6d87a-020d-33e3-b812-ac3843eba262","correlationId":"f472fd83-273e-309d-ab0f-06f15354f55d","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |

**observations**
- `{"observationId":"e6da1eae-e9b9-3f99-ae27-2821adb0cd13","copyId":"041c4a55-abef-38a9-9200-26ab40ec74e9","locationId":"205b3714-25eb-3b7f-aead-24616287f71a","scopeId":"59f8a6d3-dd84-3e3c-9b2e-0e573d0b9b08","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"041c4a55-abef-38a9-9200-26ab40ec74e9","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"041c4a55-abef-38a9-9200-26ab40ec74e9","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"041c4a55-abef-38a9-9200-26ab40ec74e9","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"041c4a55-abef-38a9-9200-26ab40ec74e9","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"041c4a55-abef-38a9-9200-26ab40ec74e9","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"041c4a55-abef-38a9-9200-26ab40ec74e9","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"041c4a55-abef-38a9-9200-26ab40ec74e9","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"observation","id":"e6da1eae-e9b9-3f99-ae27-2821adb0cd13"},{"kind":"systemState","id":"041c4a55-abef-38a9-9200-26ab40ec74e9"}]`

## Cas 5985c95c-1f43-323e-bff9-450413ad0131

- Exemplaire : `ea2d1a1d-5560-3558-8258-27a37cafd608`
- Famille privée : REPARATION_NON_PROJETEE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"b1863b9f-a3bb-3d66-8b34-cc26c9267d5e","kind":"STATUS_MISMATCH","expectedValue":"ON_SHELF","observedValue":"IN_REPAIR"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"bd30ce6c-054c-3cd3-9681-9f0f17ffe936","copyId":"ea2d1a1d-5560-3558-8258-27a37cafd608","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T11:38:33Z","recordedAt":"2026-09-27T11:38:43Z","payload":{"originalEventId":"65cc7dec-5090-3d42-a976-2f0be30d3ed6","correlationId":"38df4c9c-8fe9-32fa-9cd1-d35eab1287fd","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"65cc7dec-5090-3d42-a976-2f0be30d3ed6","physicalStatus":"IN_REPAIR","physicalSiteId":"CENTRE","physicalLocationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État projeté : `{"copyId":"ea2d1a1d-5560-3558-8258-27a37cafd608","status":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","sourceEventIds":["b87ddda2-d8f2-3353-9205-ba46f3700945","eda040d1-e3bc-3e7b-a530-e693fd65dcee"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 592ae9f4-c94a-3ecc-8d7f-8951326e9526 | 2026-06-02T07:22:31Z | 2026-06-02T07:22:41Z | `{"operationId":"cfc27b4b-8b35-368b-b056-b8df0628fc7d","correlationId":"296b0bb8-866c-3d01-a679-c98a0052bbee","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"8605c853-db88-39ac-9607-22529a41b38f"}` |
| IntegrationProcessed | c2fec4c6-21ad-3a26-ae87-1d06b7835718 | 2026-06-03T07:22:31Z | 2026-06-03T07:22:41Z | `{"originalEventId":"592ae9f4-c94a-3ecc-8d7f-8951326e9526","correlationId":"296b0bb8-866c-3d01-a679-c98a0052bbee","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 28ac1c92-be2f-3aa3-b938-f5161743bd2d | 2026-08-01T07:22:31Z | 2026-08-01T07:22:41Z | `{"operationId":"cd8da523-f3e5-3671-b915-eaeabc30b399","correlationId":"4558f6df-514d-3081-b22f-56a0b134e1c5","siteId":"CENTRE","locationId":null,"loanId":"0e38a1b2-5ff5-3ac2-8cba-1b443365ea3d"}` |
| IntegrationProcessed | 695a1b6b-987a-38bf-9163-9b772ea8f7ce | 2026-08-01T07:23:31Z | 2026-08-01T07:23:41Z | `{"originalEventId":"28ac1c92-be2f-3aa3-b938-f5161743bd2d","correlationId":"4558f6df-514d-3081-b22f-56a0b134e1c5","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | c7ea111d-4c4d-3fd7-9e32-257b6b3db662 | 2026-08-21T07:22:31Z | 2026-08-21T07:22:41Z | `{"operationId":"8aa59fe8-aa11-30b8-8269-dbac582ae029","correlationId":"1e53ab47-6911-3c2f-8da7-6288c2b1eaae","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"0e38a1b2-5ff5-3ac2-8cba-1b443365ea3d"}` |
| ReturnReceived | b87ddda2-d8f2-3353-9205-ba46f3700945 | 2026-08-21T07:23:01Z | 2026-08-21T07:23:11Z | `{"operationId":"3dee6723-c680-308e-b2f0-2b803de8da2d","correlationId":"15c3887e-02d6-321f-bddf-bc0a5860965e","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"0e38a1b2-5ff5-3ac2-8cba-1b443365ea3d"}` |
| IntegrationProcessed | eda040d1-e3bc-3e7b-a530-e693fd65dcee | 2026-08-22T07:22:31Z | 2026-08-22T07:22:41Z | `{"originalEventId":"b87ddda2-d8f2-3353-9205-ba46f3700945","correlationId":"15c3887e-02d6-321f-bddf-bc0a5860965e","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| RepairStarted | 65cc7dec-5090-3d42-a976-2f0be30d3ed6 | 2026-09-27T11:38:03Z | 2026-09-27T11:38:13Z | `{"operationId":"20f51c65-1f03-32e8-942b-e0ff58ed096e","correlationId":"38df4c9c-8fe9-32fa-9cd1-d35eab1287fd","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","repairId":"3fa343e5-101b-3d70-a061-5b167fc8ccab"}` |

**observations**
- `{"observationId":"492f3b78-1b61-35b5-b706-b64f4cacf70d","copyId":"ea2d1a1d-5560-3558-8258-27a37cafd608","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","scopeId":"dc38a3c7-811a-3c7e-9d52-13be1b9914cd","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**sourceAvailability**
- `{"copyId":"ea2d1a1d-5560-3558-8258-27a37cafd608","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ea2d1a1d-5560-3558-8258-27a37cafd608","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ea2d1a1d-5560-3558-8258-27a37cafd608","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ea2d1a1d-5560-3558-8258-27a37cafd608","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"ea2d1a1d-5560-3558-8258-27a37cafd608","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ea2d1a1d-5560-3558-8258-27a37cafd608","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ea2d1a1d-5560-3558-8258-27a37cafd608","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"ea2d1a1d-5560-3558-8258-27a37cafd608"},{"kind":"sourceEvent","id":"592ae9f4-c94a-3ecc-8d7f-8951326e9526"},{"kind":"sourceEvent","id":"c2fec4c6-21ad-3a26-ae87-1d06b7835718"},{"kind":"sourceEvent","id":"28ac1c92-be2f-3aa3-b938-f5161743bd2d"},{"kind":"sourceEvent","id":"695a1b6b-987a-38bf-9163-9b772ea8f7ce"},{"kind":"sourceEvent","id":"c7ea111d-4c4d-3fd7-9e32-257b6b3db662"},{"kind":"sourceEvent","id":"b87ddda2-d8f2-3353-9205-ba46f3700945"},{"kind":"sourceEvent","id":"eda040d1-e3bc-3e7b-a530-e693fd65dcee"},{"kind":"sourceEvent","id":"65cc7dec-5090-3d42-a976-2f0be30d3ed6"},{"kind":"observation","id":"492f3b78-1b61-35b5-b706-b64f4cacf70d"},{"kind":"inventoryScope","id":"dc38a3c7-811a-3c7e-9d52-13be1b9914cd"},{"kind":"sourceAvailability","id":"ea2d1a1d-5560-3558-8258-27a37cafd608/LOANS"},{"kind":"sourceAvailability","id":"ea2d1a1d-5560-3558-8258-27a37cafd608/RETURNS"},{"kind":"sourceAvailability","id":"ea2d1a1d-5560-3558-8258-27a37cafd608/RECEIPTS"},{"kind":"sourceAvailability","id":"ea2d1a1d-5560-3558-8258-27a37cafd608/TRANSFERS"},{"kind":"sourceAvailability","id":"ea2d1a1d-5560-3558-8258-27a37cafd608/REPAIRS"},{"kind":"sourceAvailability","id":"ea2d1a1d-5560-3558-8258-27a37cafd608/INTEGRATIONS"},{"kind":"sourceAvailability","id":"ea2d1a1d-5560-3558-8258-27a37cafd608/INVENTORY"},{"kind":"inventoryScope","id":"8021dca8-65fe-3cab-985e-d4fb9df5c308"},{"kind":"inventoryScope","id":"b0027657-6abb-37d2-8bc4-9341357dfe79"},{"kind":"inventoryScope","id":"4757f5eb-f104-3413-b118-51a6951a650c"}]`

- Session : `{"scopeId":"dc38a3c7-811a-3c7e-9d52-13be1b9914cd","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"8021dca8-65fe-3cab-985e-d4fb9df5c308","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b0027657-6abb-37d2-8bc4-9341357dfe79","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4757f5eb-f104-3413-b118-51a6951a650c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 5c6215eb-7fda-31f4-b5ee-5d8eebf3e9eb

- Exemplaire : `35ffc774-2d56-3615-a32d-72e2b708e761`
- Famille privée : RETARD_INTEGRATION ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"6ea0e37d-daae-3ec8-92cb-556144046afa","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"d02673c6-8fe0-35bb-a2f7-2bf4d56844ca","physicalStatus":"ON_LOAN","physicalSiteId":"SUD","physicalLocationId":null,"projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Recontrôler après la fenêtre de traitement"]}`
- État projeté : `{"copyId":"35ffc774-2d56-3615-a32d-72e2b708e761","status":"ON_LOAN","siteId":"SUD","locationId":null,"sourceEventIds":["d02673c6-8fe0-35bb-a2f7-2bf4d56844ca","f8ba50d4-3a7d-31ee-b616-bdd75a0f33af"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | ab10bde5-62c7-3cc9-9e08-5cf2758fd9e3 | 2026-06-02T08:50:34Z | 2026-06-02T08:50:44Z | `{"operationId":"9870b16c-c012-3734-97e9-60a2c4e95bd8","correlationId":"ede659c8-652e-311a-83a6-c10d782a7adc","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"60767e06-f86c-3ef4-80cd-f324b0218648"}` |
| IntegrationProcessed | f8a84663-4642-3d8c-b147-62add6220944 | 2026-06-03T08:50:34Z | 2026-06-03T08:50:44Z | `{"originalEventId":"ab10bde5-62c7-3cc9-9e08-5cf2758fd9e3","correlationId":"ede659c8-652e-311a-83a6-c10d782a7adc","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | 70447ece-99c5-33b7-9ae0-667574debc6d | 2026-08-01T08:50:34Z | 2026-08-01T08:50:44Z | `{"operationId":"219c484c-6c5e-3ce1-9c95-860f4f432aad","correlationId":"14f43d36-9dba-38ee-a2f5-8b34ca2b444c","siteId":"SUD","locationId":null,"loanId":"5ba7f1c4-d628-3386-91de-dea777e29c53"}` |
| IntegrationProcessed | 6647a4c8-11fd-3471-a378-5103745f0d3c | 2026-08-01T08:51:34Z | 2026-08-01T08:51:44Z | `{"originalEventId":"70447ece-99c5-33b7-9ae0-667574debc6d","correlationId":"14f43d36-9dba-38ee-a2f5-8b34ca2b444c","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | 0876f5d1-0d1b-324a-a5fa-7161c2af5269 | 2026-08-21T08:50:34Z | 2026-08-21T08:50:44Z | `{"operationId":"c66babdd-2b36-3201-85ab-cfd40b75d673","correlationId":"3a3ab05d-9a47-3128-a342-407f20cf8e16","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"5ba7f1c4-d628-3386-91de-dea777e29c53"}` |
| ReturnReceived | ea9808e5-671c-38bd-bc2b-da7c8b51ebb7 | 2026-08-21T08:51:04Z | 2026-08-21T08:51:14Z | `{"operationId":"6a3e767f-23c8-34ff-b594-8a28e59bd547","correlationId":"31633bbf-aea4-369a-83fa-6a83f53cde56","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"5ba7f1c4-d628-3386-91de-dea777e29c53"}` |
| IntegrationProcessed | ac77a125-564b-36fb-803a-6903b59d7b21 | 2026-08-22T08:50:34Z | 2026-08-22T08:50:44Z | `{"originalEventId":"ea9808e5-671c-38bd-bc2b-da7c8b51ebb7","correlationId":"31633bbf-aea4-369a-83fa-6a83f53cde56","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | d02673c6-8fe0-35bb-a2f7-2bf4d56844ca | 2026-09-30T11:53:51Z | 2026-09-30T11:54:01Z | `{"operationId":"d71fba3b-c526-34e5-a327-02811f3729cb","correlationId":"84f7206a-c025-36f8-95cf-2c5403b4dd21","siteId":"SUD","locationId":null,"loanId":"18064e90-e08e-35bb-9e9a-a43e2b446cef"}` |
| IntegrationProcessed | d39f631b-8a49-3136-b028-06ea0d60e615 | 2026-09-30T11:54:21Z | 2026-09-30T11:54:31Z | `{"originalEventId":"d02673c6-8fe0-35bb-a2f7-2bf4d56844ca","correlationId":"84f7206a-c025-36f8-95cf-2c5403b4dd21","outcome":"QUEUED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1}` |
| IntegrationProcessed | f8ba50d4-3a7d-31ee-b616-bdd75a0f33af | 2026-09-30T11:55:21Z | 2026-09-30T11:55:31Z | `{"originalEventId":"d02673c6-8fe0-35bb-a2f7-2bf4d56844ca","correlationId":"84f7206a-c025-36f8-95cf-2c5403b4dd21","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |

**observations**
- `{"observationId":"10c4755b-f84b-3f73-8d3c-9c5ee16814b8","copyId":"35ffc774-2d56-3615-a32d-72e2b708e761","locationId":"205b3714-25eb-3b7f-aead-24616287f71a","scopeId":"0444b399-e488-3a6c-a5da-478154ae42da","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**sourceAvailability**
- `{"copyId":"35ffc774-2d56-3615-a32d-72e2b708e761","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"35ffc774-2d56-3615-a32d-72e2b708e761","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"35ffc774-2d56-3615-a32d-72e2b708e761","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"35ffc774-2d56-3615-a32d-72e2b708e761","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"35ffc774-2d56-3615-a32d-72e2b708e761","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"35ffc774-2d56-3615-a32d-72e2b708e761","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"35ffc774-2d56-3615-a32d-72e2b708e761","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"35ffc774-2d56-3615-a32d-72e2b708e761"},{"kind":"sourceEvent","id":"ab10bde5-62c7-3cc9-9e08-5cf2758fd9e3"},{"kind":"sourceEvent","id":"f8a84663-4642-3d8c-b147-62add6220944"},{"kind":"sourceEvent","id":"70447ece-99c5-33b7-9ae0-667574debc6d"},{"kind":"sourceEvent","id":"6647a4c8-11fd-3471-a378-5103745f0d3c"},{"kind":"sourceEvent","id":"0876f5d1-0d1b-324a-a5fa-7161c2af5269"},{"kind":"sourceEvent","id":"ea9808e5-671c-38bd-bc2b-da7c8b51ebb7"},{"kind":"sourceEvent","id":"ac77a125-564b-36fb-803a-6903b59d7b21"},{"kind":"sourceEvent","id":"d02673c6-8fe0-35bb-a2f7-2bf4d56844ca"},{"kind":"sourceEvent","id":"d39f631b-8a49-3136-b028-06ea0d60e615"},{"kind":"sourceEvent","id":"f8ba50d4-3a7d-31ee-b616-bdd75a0f33af"},{"kind":"observation","id":"10c4755b-f84b-3f73-8d3c-9c5ee16814b8"},{"kind":"inventoryScope","id":"0444b399-e488-3a6c-a5da-478154ae42da"},{"kind":"sourceAvailability","id":"35ffc774-2d56-3615-a32d-72e2b708e761/LOANS"},{"kind":"sourceAvailability","id":"35ffc774-2d56-3615-a32d-72e2b708e761/RETURNS"},{"kind":"sourceAvailability","id":"35ffc774-2d56-3615-a32d-72e2b708e761/RECEIPTS"},{"kind":"sourceAvailability","id":"35ffc774-2d56-3615-a32d-72e2b708e761/TRANSFERS"},{"kind":"sourceAvailability","id":"35ffc774-2d56-3615-a32d-72e2b708e761/REPAIRS"},{"kind":"sourceAvailability","id":"35ffc774-2d56-3615-a32d-72e2b708e761/INTEGRATIONS"},{"kind":"sourceAvailability","id":"35ffc774-2d56-3615-a32d-72e2b708e761/INVENTORY"}]`

- Session : `{"scopeId":"0444b399-e488-3a6c-a5da-478154ae42da","locationId":"205b3714-25eb-3b7f-aead-24616287f71a","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

## Cas 5ca32dd4-3418-333e-8f60-8e4e2ca6ac73

- Exemplaire : `181a20a7-4c80-3a88-ac78-e926d227402b`
- Famille privée : REPARATION_NON_PROJETEE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"5b70a703-eef8-3eb8-841f-e1fe183f8299","kind":"STATUS_MISMATCH","expectedValue":"ON_SHELF","observedValue":"IN_REPAIR"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"07e7ec70-7990-38d6-82e1-40ebfeab6970","copyId":"181a20a7-4c80-3a88-ac78-e926d227402b","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T09:05:20Z","recordedAt":"2026-09-27T09:05:30Z","payload":{"originalEventId":"4ea69d09-d815-335f-ae4c-fba0e3830db1","correlationId":"1a317d0b-5faf-3979-9270-a2756f1cb988","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"NORD","locationId":"11044fb8-eb45-33d3-90df-81754bc299a0","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"4ea69d09-d815-335f-ae4c-fba0e3830db1","physicalStatus":"IN_REPAIR","physicalSiteId":"NORD","physicalLocationId":"11044fb8-eb45-33d3-90df-81754bc299a0","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État projeté : `{"copyId":"181a20a7-4c80-3a88-ac78-e926d227402b","status":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","sourceEventIds":["b5472b05-f6b8-349c-b17c-2636c18dba62","6bbd4707-d3e0-3668-adb8-84ca0d490ad4"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 2a4bcead-08d1-3f0a-b1b9-4411ba6c313b | 2026-06-02T11:19:33Z | 2026-06-02T11:19:43Z | `{"operationId":"8c475e3d-1933-3f97-bb00-a1333ad8638b","correlationId":"01e7f4c8-7c4c-336e-81d2-474a215826a6","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"e552c571-7bd6-35d4-b3a7-6a9bb02f7751"}` |
| IntegrationProcessed | 7f67d6ab-d9de-3be2-a324-ef6623b27b34 | 2026-06-03T11:19:33Z | 2026-06-03T11:19:43Z | `{"originalEventId":"2a4bcead-08d1-3f0a-b1b9-4411ba6c313b","correlationId":"01e7f4c8-7c4c-336e-81d2-474a215826a6","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 7a64aab3-f850-352c-8e46-cb30b636ba7e | 2026-08-01T11:19:33Z | 2026-08-01T11:19:43Z | `{"operationId":"c466a46d-cf0d-3304-9386-5f091fdb7003","correlationId":"31990577-0bb3-33ce-a979-50550436e7ae","siteId":"NORD","locationId":null,"loanId":"17e2aeca-3983-31f3-98f4-dcbaf73f4aad"}` |
| IntegrationProcessed | 22138536-36ff-335a-acbd-80c2b7036971 | 2026-08-01T11:20:33Z | 2026-08-01T11:20:43Z | `{"originalEventId":"7a64aab3-f850-352c-8e46-cb30b636ba7e","correlationId":"31990577-0bb3-33ce-a979-50550436e7ae","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | a494a4ce-7eae-3cc3-805f-a45aa0aadb84 | 2026-08-21T11:19:33Z | 2026-08-21T11:19:43Z | `{"operationId":"2a4c99c1-5612-3819-a6da-d74cbc11fa53","correlationId":"bef1faa9-59db-32fc-b1c5-461505b2599b","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"17e2aeca-3983-31f3-98f4-dcbaf73f4aad"}` |
| ReturnReceived | b5472b05-f6b8-349c-b17c-2636c18dba62 | 2026-08-21T11:20:03Z | 2026-08-21T11:20:13Z | `{"operationId":"3fa894e5-cbd5-3344-9829-f4357265007b","correlationId":"82bdefe0-5b47-3283-8cbb-e15fdafc3b92","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"17e2aeca-3983-31f3-98f4-dcbaf73f4aad"}` |
| IntegrationProcessed | 6bbd4707-d3e0-3668-adb8-84ca0d490ad4 | 2026-08-22T11:19:33Z | 2026-08-22T11:19:43Z | `{"originalEventId":"b5472b05-f6b8-349c-b17c-2636c18dba62","correlationId":"82bdefe0-5b47-3283-8cbb-e15fdafc3b92","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| RepairStarted | 4ea69d09-d815-335f-ae4c-fba0e3830db1 | 2026-09-27T09:04:50Z | 2026-09-27T09:05:00Z | `{"operationId":"7dce67da-3115-3d31-a17f-0f7778ff52c9","correlationId":"1a317d0b-5faf-3979-9270-a2756f1cb988","siteId":"NORD","locationId":"11044fb8-eb45-33d3-90df-81754bc299a0","repairId":"16cd7ae8-22b4-3ba8-8f8f-0315441868d6"}` |

**observations**
- `{"observationId":"3eaf84a1-4b2b-3974-b349-debdb24f59e2","copyId":"181a20a7-4c80-3a88-ac78-e926d227402b","locationId":"11044fb8-eb45-33d3-90df-81754bc299a0","scopeId":"99b9fee1-8d98-3a29-a648-0bf3eb095524","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**sourceAvailability**
- `{"copyId":"181a20a7-4c80-3a88-ac78-e926d227402b","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"181a20a7-4c80-3a88-ac78-e926d227402b","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"181a20a7-4c80-3a88-ac78-e926d227402b","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"181a20a7-4c80-3a88-ac78-e926d227402b","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"181a20a7-4c80-3a88-ac78-e926d227402b","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"181a20a7-4c80-3a88-ac78-e926d227402b","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"181a20a7-4c80-3a88-ac78-e926d227402b","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"181a20a7-4c80-3a88-ac78-e926d227402b"},{"kind":"sourceEvent","id":"2a4bcead-08d1-3f0a-b1b9-4411ba6c313b"},{"kind":"sourceEvent","id":"7f67d6ab-d9de-3be2-a324-ef6623b27b34"},{"kind":"sourceEvent","id":"7a64aab3-f850-352c-8e46-cb30b636ba7e"},{"kind":"sourceEvent","id":"22138536-36ff-335a-acbd-80c2b7036971"},{"kind":"sourceEvent","id":"a494a4ce-7eae-3cc3-805f-a45aa0aadb84"},{"kind":"sourceEvent","id":"b5472b05-f6b8-349c-b17c-2636c18dba62"},{"kind":"sourceEvent","id":"6bbd4707-d3e0-3668-adb8-84ca0d490ad4"},{"kind":"sourceEvent","id":"4ea69d09-d815-335f-ae4c-fba0e3830db1"},{"kind":"observation","id":"3eaf84a1-4b2b-3974-b349-debdb24f59e2"},{"kind":"inventoryScope","id":"99b9fee1-8d98-3a29-a648-0bf3eb095524"},{"kind":"sourceAvailability","id":"181a20a7-4c80-3a88-ac78-e926d227402b/LOANS"},{"kind":"sourceAvailability","id":"181a20a7-4c80-3a88-ac78-e926d227402b/RETURNS"},{"kind":"sourceAvailability","id":"181a20a7-4c80-3a88-ac78-e926d227402b/RECEIPTS"},{"kind":"sourceAvailability","id":"181a20a7-4c80-3a88-ac78-e926d227402b/TRANSFERS"},{"kind":"sourceAvailability","id":"181a20a7-4c80-3a88-ac78-e926d227402b/REPAIRS"},{"kind":"sourceAvailability","id":"181a20a7-4c80-3a88-ac78-e926d227402b/INTEGRATIONS"},{"kind":"sourceAvailability","id":"181a20a7-4c80-3a88-ac78-e926d227402b/INVENTORY"},{"kind":"inventoryScope","id":"07c957d2-b086-3b6b-9915-d21f1d5906cf"}]`

- Session : `{"scopeId":"99b9fee1-8d98-3a29-a648-0bf3eb095524","locationId":"11044fb8-eb45-33d3-90df-81754bc299a0","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"07c957d2-b086-3b6b-9915-d21f1d5906cf","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 5d764a2b-2e67-3da3-93dd-bb0af6880274

- Exemplaire : `e97ef0a2-ac69-3ba4-9a9f-91b008c02e9b`
- Famille privée : TRANSFERT_INCOHERENT ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"490aa285-b3cc-3323-a5f0-884195076f19","kind":"STATUS_MISMATCH","expectedValue":"IN_TRANSIT","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"c559a4e4-0cc0-328b-acb0-e253ce14246b","copyId":"e97ef0a2-ac69-3ba4-9a9f-91b008c02e9b","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T07:41:19Z","recordedAt":"2026-09-27T07:41:29Z","payload":{"originalEventId":"2f6be2bc-778e-3cef-bfa6-c7e4e9be4789","correlationId":"c8f8f8f5-060c-3e1c-bd1f-6b626db0982b","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"2f6be2bc-778e-3cef-bfa6-c7e4e9be4789","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État projeté : `{"copyId":"e97ef0a2-ac69-3ba4-9a9f-91b008c02e9b","status":"IN_TRANSIT","siteId":"CENTRE","locationId":null,"sourceEventIds":["5f9f5955-16e2-399b-8efc-902c3defac0c","faec6852-f85f-3c70-afda-fc341dfe5990"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | f40dfc2a-db5b-35da-aedd-7bd4a52916c3 | 2026-06-01T12:49:22Z | 2026-06-01T12:49:32Z | `{"operationId":"168332bd-bc90-3431-abe8-0f038c4f8e40","correlationId":"ece79f52-d2d9-3ca6-acb7-c2ced27e633e","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"640c05e8-d892-3b29-a244-806c096d3103"}` |
| IntegrationProcessed | 64cb404e-4cfd-307d-b565-c6dcbabbf1a4 | 2026-06-02T12:49:22Z | 2026-06-02T12:49:32Z | `{"originalEventId":"f40dfc2a-db5b-35da-aedd-7bd4a52916c3","correlationId":"ece79f52-d2d9-3ca6-acb7-c2ced27e633e","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 422de39a-fa7b-3b7c-b15e-ed6b69b7ea90 | 2026-07-31T12:49:22Z | 2026-07-31T12:49:32Z | `{"operationId":"b14b5581-e5b0-3650-a077-97a8dd50943f","correlationId":"af2fb53f-7b3f-360d-bf2d-e45900077eb3","siteId":"CENTRE","locationId":null,"loanId":"eedbc1ed-7dfa-3934-999b-28ea05ab7b75"}` |
| IntegrationProcessed | 74bbc981-b30b-3ae2-8b22-8e5d76d1fb93 | 2026-07-31T12:50:22Z | 2026-07-31T12:50:32Z | `{"originalEventId":"422de39a-fa7b-3b7c-b15e-ed6b69b7ea90","correlationId":"af2fb53f-7b3f-360d-bf2d-e45900077eb3","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | e5b8fd06-0c12-3d44-a958-506f8d9fd376 | 2026-08-20T12:49:22Z | 2026-08-20T12:49:32Z | `{"operationId":"8156e806-4117-3fe6-9c2e-6ddc2151df89","correlationId":"8f48ab98-0e1b-3f45-9bcc-e917b987bc5f","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"eedbc1ed-7dfa-3934-999b-28ea05ab7b75"}` |
| ReturnReceived | f82d1464-3d10-3248-bd9d-08f6d9a7947f | 2026-08-20T12:49:52Z | 2026-08-20T12:50:02Z | `{"operationId":"03c68fae-f3bb-3fc6-bb4a-16220787b445","correlationId":"7e378193-d6f1-3dd1-9d66-5866e0de4a0e","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"eedbc1ed-7dfa-3934-999b-28ea05ab7b75"}` |
| IntegrationProcessed | d46d2fa8-b5b5-3284-9698-c54b0a43ebb1 | 2026-08-21T12:49:22Z | 2026-08-21T12:49:32Z | `{"originalEventId":"f82d1464-3d10-3248-bd9d-08f6d9a7947f","correlationId":"7e378193-d6f1-3dd1-9d66-5866e0de4a0e","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| TransferDispatched | 5f9f5955-16e2-399b-8efc-902c3defac0c | 2026-09-26T07:40:49Z | 2026-09-26T07:40:59Z | `{"operationId":"1a6a7b91-e719-3702-a45f-43c1f6454e73","correlationId":"5f735753-0322-3176-ab3e-32e3d21ace83","siteId":"CENTRE","locationId":null,"transferId":"fb5f2415-d7f1-382e-8775-e2c12c453a69","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | faec6852-f85f-3c70-afda-fc341dfe5990 | 2026-09-26T07:41:49Z | 2026-09-26T07:41:59Z | `{"originalEventId":"5f9f5955-16e2-399b-8efc-902c3defac0c","correlationId":"5f735753-0322-3176-ab3e-32e3d21ace83","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| TransferReceived | 2f6be2bc-778e-3cef-bfa6-c7e4e9be4789 | 2026-09-27T07:40:49Z | 2026-09-27T07:40:59Z | `{"operationId":"84411f22-62b0-3603-9fd2-2730a512b840","correlationId":"c8f8f8f5-060c-3e1c-bd1f-6b626db0982b","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","transferId":"fb5f2415-d7f1-382e-8775-e2c12c453a69","fromSiteId":"CENTRE","toSiteId":"NORD"}` |

**observations**
- `{"observationId":"a5662819-00ac-34fa-89fb-f37ee4f43775","copyId":"e97ef0a2-ac69-3ba4-9a9f-91b008c02e9b","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","scopeId":"edfc42ec-eb05-38f1-b0ea-2cf6b2ad9221","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**sourceAvailability**
- `{"copyId":"e97ef0a2-ac69-3ba4-9a9f-91b008c02e9b","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e97ef0a2-ac69-3ba4-9a9f-91b008c02e9b","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e97ef0a2-ac69-3ba4-9a9f-91b008c02e9b","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e97ef0a2-ac69-3ba4-9a9f-91b008c02e9b","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e97ef0a2-ac69-3ba4-9a9f-91b008c02e9b","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"e97ef0a2-ac69-3ba4-9a9f-91b008c02e9b","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e97ef0a2-ac69-3ba4-9a9f-91b008c02e9b","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"e97ef0a2-ac69-3ba4-9a9f-91b008c02e9b"},{"kind":"sourceEvent","id":"f40dfc2a-db5b-35da-aedd-7bd4a52916c3"},{"kind":"sourceEvent","id":"64cb404e-4cfd-307d-b565-c6dcbabbf1a4"},{"kind":"sourceEvent","id":"422de39a-fa7b-3b7c-b15e-ed6b69b7ea90"},{"kind":"sourceEvent","id":"74bbc981-b30b-3ae2-8b22-8e5d76d1fb93"},{"kind":"sourceEvent","id":"e5b8fd06-0c12-3d44-a958-506f8d9fd376"},{"kind":"sourceEvent","id":"f82d1464-3d10-3248-bd9d-08f6d9a7947f"},{"kind":"sourceEvent","id":"d46d2fa8-b5b5-3284-9698-c54b0a43ebb1"},{"kind":"sourceEvent","id":"5f9f5955-16e2-399b-8efc-902c3defac0c"},{"kind":"sourceEvent","id":"faec6852-f85f-3c70-afda-fc341dfe5990"},{"kind":"sourceEvent","id":"2f6be2bc-778e-3cef-bfa6-c7e4e9be4789"},{"kind":"observation","id":"a5662819-00ac-34fa-89fb-f37ee4f43775"},{"kind":"inventoryScope","id":"edfc42ec-eb05-38f1-b0ea-2cf6b2ad9221"},{"kind":"sourceAvailability","id":"e97ef0a2-ac69-3ba4-9a9f-91b008c02e9b/LOANS"},{"kind":"sourceAvailability","id":"e97ef0a2-ac69-3ba4-9a9f-91b008c02e9b/RETURNS"},{"kind":"sourceAvailability","id":"e97ef0a2-ac69-3ba4-9a9f-91b008c02e9b/RECEIPTS"},{"kind":"sourceAvailability","id":"e97ef0a2-ac69-3ba4-9a9f-91b008c02e9b/TRANSFERS"},{"kind":"sourceAvailability","id":"e97ef0a2-ac69-3ba4-9a9f-91b008c02e9b/REPAIRS"},{"kind":"sourceAvailability","id":"e97ef0a2-ac69-3ba4-9a9f-91b008c02e9b/INTEGRATIONS"},{"kind":"sourceAvailability","id":"e97ef0a2-ac69-3ba4-9a9f-91b008c02e9b/INVENTORY"}]`

- Session : `{"scopeId":"edfc42ec-eb05-38f1-b0ea-2cf6b2ad9221","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 605b4039-fa14-33d5-bf9e-562954b2dea9

- Exemplaire : `f44af00a-1d04-33c9-8c95-906e93308745`
- Famille privée : TRANSFERT_INCOHERENT ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"c63c21bf-d5b0-3953-9c04-6e60350751b0","kind":"STATUS_MISMATCH","expectedValue":"IN_TRANSIT","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"bd3cf4ae-1dd6-3b51-81d1-b3bb065d7ec7","copyId":"f44af00a-1d04-33c9-8c95-906e93308745","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T08:46:35Z","recordedAt":"2026-09-27T08:46:45Z","payload":{"originalEventId":"5dd1c0ac-076a-32b8-85e4-6cd03e99aded","correlationId":"7fa050ee-4146-357f-ac7a-c75caee062e1","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"5dd1c0ac-076a-32b8-85e4-6cd03e99aded","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État projeté : `{"copyId":"f44af00a-1d04-33c9-8c95-906e93308745","status":"IN_TRANSIT","siteId":"CENTRE","locationId":null,"sourceEventIds":["5ca9a5d5-5bd3-35a7-afa2-3a0d44f3df79","e5a4d42d-b6de-374a-9969-c816e8af27d1"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 56fec1d2-aec9-3591-8cc4-282c20ba18d1 | 2026-06-02T07:43:41Z | 2026-06-02T07:43:51Z | `{"operationId":"2bfe959e-8acd-3ad0-8aa8-9961083c7b3d","correlationId":"96f9f09c-b9fa-30ec-8e46-a256f7a991e4","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"cdbea5ac-3d79-3d80-8b79-903ab2df6110"}` |
| IntegrationProcessed | 599b89a7-eab1-37d8-9c4c-5cffc30f7da4 | 2026-06-03T07:43:41Z | 2026-06-03T07:43:51Z | `{"originalEventId":"56fec1d2-aec9-3591-8cc4-282c20ba18d1","correlationId":"96f9f09c-b9fa-30ec-8e46-a256f7a991e4","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 7a73f041-e9af-3480-a2e5-22794d2fc7e5 | 2026-08-01T07:43:41Z | 2026-08-01T07:43:51Z | `{"operationId":"3486dacd-6f40-3b60-b50c-9679ffcbe5eb","correlationId":"2f7bd599-e1bd-3a7a-b309-25250fe3834f","siteId":"CENTRE","locationId":null,"loanId":"a5f518e0-bb53-36f8-837a-a83ae326d58b"}` |
| IntegrationProcessed | 5346ce13-4724-39de-9543-503bafbc128e | 2026-08-01T07:44:41Z | 2026-08-01T07:44:51Z | `{"originalEventId":"7a73f041-e9af-3480-a2e5-22794d2fc7e5","correlationId":"2f7bd599-e1bd-3a7a-b309-25250fe3834f","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 1df1b8a6-910d-3891-aee6-5ad2b8426938 | 2026-08-21T07:43:41Z | 2026-08-21T07:43:51Z | `{"operationId":"69564647-e124-373b-a42a-5e27f8ffd31e","correlationId":"9a5050a6-4b80-371d-96c5-e306c9a11a64","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"a5f518e0-bb53-36f8-837a-a83ae326d58b"}` |
| ReturnReceived | ad2ada5f-14ed-329f-80f8-3a225cbf2ddd | 2026-08-21T07:44:11Z | 2026-08-21T07:44:21Z | `{"operationId":"6270e98a-1e6b-3f4c-9916-c354d095f99d","correlationId":"227f255f-d14a-3ca6-a8fd-4ef4328646a3","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"a5f518e0-bb53-36f8-837a-a83ae326d58b"}` |
| IntegrationProcessed | 789520ac-f468-343d-ba6f-737bf0c455ef | 2026-08-22T07:43:41Z | 2026-08-22T07:43:51Z | `{"originalEventId":"ad2ada5f-14ed-329f-80f8-3a225cbf2ddd","correlationId":"227f255f-d14a-3ca6-a8fd-4ef4328646a3","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| TransferDispatched | 5ca9a5d5-5bd3-35a7-afa2-3a0d44f3df79 | 2026-09-26T08:46:05Z | 2026-09-26T08:46:15Z | `{"operationId":"be12712e-8936-3431-a092-ae886f7a3803","correlationId":"02186d63-e7a0-33f0-90b7-cc12cb190302","siteId":"CENTRE","locationId":null,"transferId":"a3b4293c-f766-3520-9f53-3435dce2a494","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | e5a4d42d-b6de-374a-9969-c816e8af27d1 | 2026-09-26T08:47:05Z | 2026-09-26T08:47:15Z | `{"originalEventId":"5ca9a5d5-5bd3-35a7-afa2-3a0d44f3df79","correlationId":"02186d63-e7a0-33f0-90b7-cc12cb190302","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| TransferReceived | 5dd1c0ac-076a-32b8-85e4-6cd03e99aded | 2026-09-27T08:46:05Z | 2026-09-27T08:46:15Z | `{"operationId":"dc07c216-f88e-34a5-8c78-467f990bddb5","correlationId":"7fa050ee-4146-357f-ac7a-c75caee062e1","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","transferId":"a3b4293c-f766-3520-9f53-3435dce2a494","fromSiteId":"CENTRE","toSiteId":"NORD"}` |

**observations**
- `{"observationId":"2a4b8c84-4dd8-3fe6-9947-4d2def08c60b","copyId":"f44af00a-1d04-33c9-8c95-906e93308745","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","scopeId":"4be539c0-d149-373a-8ae9-09262c35e03c","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**sourceAvailability**
- `{"copyId":"f44af00a-1d04-33c9-8c95-906e93308745","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"f44af00a-1d04-33c9-8c95-906e93308745","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f44af00a-1d04-33c9-8c95-906e93308745","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f44af00a-1d04-33c9-8c95-906e93308745","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f44af00a-1d04-33c9-8c95-906e93308745","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f44af00a-1d04-33c9-8c95-906e93308745","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f44af00a-1d04-33c9-8c95-906e93308745","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"f44af00a-1d04-33c9-8c95-906e93308745"},{"kind":"sourceEvent","id":"56fec1d2-aec9-3591-8cc4-282c20ba18d1"},{"kind":"sourceEvent","id":"599b89a7-eab1-37d8-9c4c-5cffc30f7da4"},{"kind":"sourceEvent","id":"7a73f041-e9af-3480-a2e5-22794d2fc7e5"},{"kind":"sourceEvent","id":"5346ce13-4724-39de-9543-503bafbc128e"},{"kind":"sourceEvent","id":"1df1b8a6-910d-3891-aee6-5ad2b8426938"},{"kind":"sourceEvent","id":"ad2ada5f-14ed-329f-80f8-3a225cbf2ddd"},{"kind":"sourceEvent","id":"789520ac-f468-343d-ba6f-737bf0c455ef"},{"kind":"sourceEvent","id":"5ca9a5d5-5bd3-35a7-afa2-3a0d44f3df79"},{"kind":"sourceEvent","id":"e5a4d42d-b6de-374a-9969-c816e8af27d1"},{"kind":"sourceEvent","id":"5dd1c0ac-076a-32b8-85e4-6cd03e99aded"},{"kind":"observation","id":"2a4b8c84-4dd8-3fe6-9947-4d2def08c60b"},{"kind":"inventoryScope","id":"4be539c0-d149-373a-8ae9-09262c35e03c"},{"kind":"sourceAvailability","id":"f44af00a-1d04-33c9-8c95-906e93308745/LOANS"},{"kind":"sourceAvailability","id":"f44af00a-1d04-33c9-8c95-906e93308745/RETURNS"},{"kind":"sourceAvailability","id":"f44af00a-1d04-33c9-8c95-906e93308745/RECEIPTS"},{"kind":"sourceAvailability","id":"f44af00a-1d04-33c9-8c95-906e93308745/TRANSFERS"},{"kind":"sourceAvailability","id":"f44af00a-1d04-33c9-8c95-906e93308745/REPAIRS"},{"kind":"sourceAvailability","id":"f44af00a-1d04-33c9-8c95-906e93308745/INTEGRATIONS"},{"kind":"sourceAvailability","id":"f44af00a-1d04-33c9-8c95-906e93308745/INVENTORY"}]`

- Session : `{"scopeId":"4be539c0-d149-373a-8ae9-09262c35e03c","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 60a011b3-ed93-39d8-b8d1-4fb89d64fc5a

- Exemplaire : `6e319351-5225-3207-ae55-1f023fdc9a7f`
- Famille privée : ABSENCE_INEXPLIQUEE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"f8e6f1ed-1d9c-3066-8d9c-8a04dc3c6b1f","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":"1"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"mechanism":"Exemplaire resté au rayon, lecture de contrôle réussie.","physicalStatus":"ON_SHELF","physicalSiteId":"CENTRE","physicalLocationId":"4a5364f2-388b-351d-91f2-d1364d119042","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"Le scan valide au rayon attendu réfute l'absence courante.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"6e319351-5225-3207-ae55-1f023fdc9a7f","status":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","sourceEventIds":["c75ffd6f-839b-32df-9b59-db122a98f092","aa1c0f31-0e97-3667-9094-1e5e23b29a09"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 3d13562e-141c-3be8-a333-7302d8700500 | 2026-06-02T11:30:51Z | 2026-06-02T11:31:01Z | `{"operationId":"178f6817-88c7-3f98-b1a6-f56b88314e49","correlationId":"21824a00-de6b-3120-a8fa-8a58f9d272b4","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"d5ef3e55-c26c-3c96-9c0b-26fb5995cbee"}` |
| IntegrationProcessed | cf73ba56-edc0-3168-b891-49bed5934d7a | 2026-06-03T11:30:51Z | 2026-06-03T11:31:01Z | `{"originalEventId":"3d13562e-141c-3be8-a333-7302d8700500","correlationId":"21824a00-de6b-3120-a8fa-8a58f9d272b4","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 6daaa726-1efe-3163-8ce1-0eea02aa6a23 | 2026-08-01T11:30:51Z | 2026-08-01T11:31:01Z | `{"operationId":"29f27546-6dd1-3345-8c8e-5880ea9cf40a","correlationId":"9ebd325b-a2e3-3d08-9ed0-fd388383c61c","siteId":"CENTRE","locationId":null,"loanId":"7872fc6e-19d2-3b18-994f-9c74e811be88"}` |
| IntegrationProcessed | 9cf97004-f5fb-385f-b195-b652fc055bf0 | 2026-08-01T11:31:51Z | 2026-08-01T11:32:01Z | `{"originalEventId":"6daaa726-1efe-3163-8ce1-0eea02aa6a23","correlationId":"9ebd325b-a2e3-3d08-9ed0-fd388383c61c","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 59af01be-e80b-3676-a6ea-e1f776ed6c06 | 2026-08-21T11:30:51Z | 2026-08-21T11:31:01Z | `{"operationId":"5bf711c0-01c5-3df8-95b1-63b451916f99","correlationId":"174bc3c9-a4db-35a8-833d-419395a111d1","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"7872fc6e-19d2-3b18-994f-9c74e811be88"}` |
| ReturnReceived | c75ffd6f-839b-32df-9b59-db122a98f092 | 2026-08-21T11:31:21Z | 2026-08-21T11:31:31Z | `{"operationId":"f1021fd3-3ca4-3092-bdf8-427eb88f33aa","correlationId":"b4bd8309-0e01-3007-9175-b7bab031bbf1","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"7872fc6e-19d2-3b18-994f-9c74e811be88"}` |
| IntegrationProcessed | aa1c0f31-0e97-3667-9094-1e5e23b29a09 | 2026-08-22T11:30:51Z | 2026-08-22T11:31:01Z | `{"originalEventId":"c75ffd6f-839b-32df-9b59-db122a98f092","correlationId":"b4bd8309-0e01-3007-9175-b7bab031bbf1","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |

**observations**
- `{"observationId":"645b1d0c-4bdb-38f8-9500-d133c6a1fdd1","copyId":"6e319351-5225-3207-ae55-1f023fdc9a7f","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","scopeId":"7bda04de-7eea-3647-9e69-f3610f2ce443","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"6e319351-5225-3207-ae55-1f023fdc9a7f","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6e319351-5225-3207-ae55-1f023fdc9a7f","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6e319351-5225-3207-ae55-1f023fdc9a7f","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6e319351-5225-3207-ae55-1f023fdc9a7f","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6e319351-5225-3207-ae55-1f023fdc9a7f","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6e319351-5225-3207-ae55-1f023fdc9a7f","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6e319351-5225-3207-ae55-1f023fdc9a7f","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**
- `{"noteId":"032d22df-b204-34a9-8331-176d28f2ec16","copyId":"6e319351-5225-3207-ae55-1f023fdc9a7f","text":"Nouvelle lecture du code-barres effectuée dans le rayon affecté.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"6e319351-5225-3207-ae55-1f023fdc9a7f"},{"kind":"sourceEvent","id":"3d13562e-141c-3be8-a333-7302d8700500"},{"kind":"sourceEvent","id":"cf73ba56-edc0-3168-b891-49bed5934d7a"},{"kind":"sourceEvent","id":"6daaa726-1efe-3163-8ce1-0eea02aa6a23"},{"kind":"sourceEvent","id":"9cf97004-f5fb-385f-b195-b652fc055bf0"},{"kind":"sourceEvent","id":"59af01be-e80b-3676-a6ea-e1f776ed6c06"},{"kind":"sourceEvent","id":"c75ffd6f-839b-32df-9b59-db122a98f092"},{"kind":"sourceEvent","id":"aa1c0f31-0e97-3667-9094-1e5e23b29a09"},{"kind":"observation","id":"645b1d0c-4bdb-38f8-9500-d133c6a1fdd1"},{"kind":"inventoryScope","id":"7bda04de-7eea-3647-9e69-f3610f2ce443"},{"kind":"sourceAvailability","id":"6e319351-5225-3207-ae55-1f023fdc9a7f/LOANS"},{"kind":"sourceAvailability","id":"6e319351-5225-3207-ae55-1f023fdc9a7f/RETURNS"},{"kind":"sourceAvailability","id":"6e319351-5225-3207-ae55-1f023fdc9a7f/RECEIPTS"},{"kind":"sourceAvailability","id":"6e319351-5225-3207-ae55-1f023fdc9a7f/TRANSFERS"},{"kind":"sourceAvailability","id":"6e319351-5225-3207-ae55-1f023fdc9a7f/REPAIRS"},{"kind":"sourceAvailability","id":"6e319351-5225-3207-ae55-1f023fdc9a7f/INTEGRATIONS"},{"kind":"sourceAvailability","id":"6e319351-5225-3207-ae55-1f023fdc9a7f/INVENTORY"},{"kind":"operatorNote","id":"032d22df-b204-34a9-8331-176d28f2ec16"},{"kind":"inventoryScope","id":"8021dca8-65fe-3cab-985e-d4fb9df5c308"},{"kind":"inventoryScope","id":"b0027657-6abb-37d2-8bc4-9341357dfe79"},{"kind":"inventoryScope","id":"4757f5eb-f104-3413-b118-51a6951a650c"},{"kind":"inventoryScope","id":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592"},{"kind":"inventoryScope","id":"40e94818-3a62-34a4-b461-398d7b9ffca2"},{"kind":"inventoryScope","id":"ed60c0be-b218-3f9e-8aa8-69c16f76c22e"},{"kind":"inventoryScope","id":"35514ff7-97f8-345d-8cf7-6cee969a726c"},{"kind":"inventoryScope","id":"351b3e2d-1bff-3290-938e-527c15f97274"},{"kind":"inventoryScope","id":"7bda04de-7eea-3647-9e69-f3610f2ce443"}]`

- Session : `{"scopeId":"7bda04de-7eea-3647-9e69-f3610f2ce443","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"8021dca8-65fe-3cab-985e-d4fb9df5c308","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b0027657-6abb-37d2-8bc4-9341357dfe79","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4757f5eb-f104-3413-b118-51a6951a650c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"40e94818-3a62-34a4-b461-398d7b9ffca2","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"ed60c0be-b218-3f9e-8aa8-69c16f76c22e","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"35514ff7-97f8-345d-8cf7-6cee969a726c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"351b3e2d-1bff-3290-938e-527c15f97274","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"7bda04de-7eea-3647-9e69-f3610f2ce443","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 640a7022-d4df-37b9-be20-9f93f7706ad0

- Exemplaire : `c4a381c1-b17b-3628-a7df-6976dea36449`
- Famille privée : INVENTAIRE_INCOMPLET ; variante : POSITIVE
- Symptômes : `[{"symptomId":"c30df2dc-9440-39f2-bd52-30ac13ecc5cb","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["COLLECTION_INTERRUPTED"],"mechanism":"Interruption de collecte ; exemplaire resté au rayon.","physicalStatus":"ON_SHELF","physicalSiteId":"SUD","physicalLocationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["INVENTORY_INCOMPLETE"],"explanation":"La session interrompue explique l'incertitude de comptage ; la présence physique reste à vérifier.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État projeté : `{"copyId":"c4a381c1-b17b-3628-a7df-6976dea36449","status":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","sourceEventIds":["9f79b2ac-d9ce-31a4-ac26-516732dfb541","bf1a5dfd-9853-3c1d-abc5-04cffac1886d"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | bfadeef6-c77d-388c-b906-cfa0cba046d1 | 2026-06-01T15:00:58Z | 2026-06-01T15:01:08Z | `{"operationId":"9d4da567-99a6-397b-8713-415076981fc7","correlationId":"baf84532-fe58-3e40-92a9-eb1793a8f766","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"41e4d21b-461f-3ceb-afac-85ee37f24620"}` |
| IntegrationProcessed | 3b6e572c-0956-3d3e-91df-40c014c2f7c8 | 2026-06-02T15:00:58Z | 2026-06-02T15:01:08Z | `{"originalEventId":"bfadeef6-c77d-388c-b906-cfa0cba046d1","correlationId":"baf84532-fe58-3e40-92a9-eb1793a8f766","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | c0a59d14-e312-3478-b35a-3ed0bf8f3ea4 | 2026-07-31T15:00:58Z | 2026-07-31T15:01:08Z | `{"operationId":"178ad103-3ce5-30ec-89a0-e20f1e590be6","correlationId":"3ff62c4f-b6f8-3efe-b4ad-d862c5b7b3fb","siteId":"SUD","locationId":null,"loanId":"fec24d5d-0a5e-3304-9733-53ed4d67c166"}` |
| IntegrationProcessed | 76057d42-a07d-3005-a01b-fef608451753 | 2026-07-31T15:01:58Z | 2026-07-31T15:02:08Z | `{"originalEventId":"c0a59d14-e312-3478-b35a-3ed0bf8f3ea4","correlationId":"3ff62c4f-b6f8-3efe-b4ad-d862c5b7b3fb","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | 31bbba2b-95d1-3860-86af-21c5ceb62629 | 2026-08-20T15:00:58Z | 2026-08-20T15:01:08Z | `{"operationId":"5680544a-4f0c-3bf5-a010-c6104e3c9fc0","correlationId":"21a6f187-e6b6-3d83-9c92-24654077b88c","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"fec24d5d-0a5e-3304-9733-53ed4d67c166"}` |
| ReturnReceived | 9f79b2ac-d9ce-31a4-ac26-516732dfb541 | 2026-08-20T15:01:28Z | 2026-08-20T15:01:38Z | `{"operationId":"f5617472-2f38-3bd2-9ebb-757d3d0446ff","correlationId":"c0c3093d-0a3b-3eec-9ab7-43c368b168fc","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"fec24d5d-0a5e-3304-9733-53ed4d67c166"}` |
| IntegrationProcessed | bf1a5dfd-9853-3c1d-abc5-04cffac1886d | 2026-08-21T15:00:58Z | 2026-08-21T15:01:08Z | `{"originalEventId":"9f79b2ac-d9ce-31a4-ac26-516732dfb541","correlationId":"c0c3093d-0a3b-3eec-9ab7-43c368b168fc","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |

**observations**

**sourceAvailability**
- `{"copyId":"c4a381c1-b17b-3628-a7df-6976dea36449","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c4a381c1-b17b-3628-a7df-6976dea36449","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c4a381c1-b17b-3628-a7df-6976dea36449","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c4a381c1-b17b-3628-a7df-6976dea36449","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c4a381c1-b17b-3628-a7df-6976dea36449","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c4a381c1-b17b-3628-a7df-6976dea36449","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c4a381c1-b17b-3628-a7df-6976dea36449","source":"INVENTORY","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`

**operatorNotes**
- `{"noteId":"81dae626-c5e2-3d4c-9d3c-6403ebed7b42","copyId":"c4a381c1-b17b-3628-a7df-6976dea36449","text":"Le terminal de collecte a cessé de répondre pendant le passage ; aucune conclusion sur les exemplaires non lus.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"c4a381c1-b17b-3628-a7df-6976dea36449"},{"kind":"sourceEvent","id":"bfadeef6-c77d-388c-b906-cfa0cba046d1"},{"kind":"sourceEvent","id":"3b6e572c-0956-3d3e-91df-40c014c2f7c8"},{"kind":"sourceEvent","id":"c0a59d14-e312-3478-b35a-3ed0bf8f3ea4"},{"kind":"sourceEvent","id":"76057d42-a07d-3005-a01b-fef608451753"},{"kind":"sourceEvent","id":"31bbba2b-95d1-3860-86af-21c5ceb62629"},{"kind":"sourceEvent","id":"9f79b2ac-d9ce-31a4-ac26-516732dfb541"},{"kind":"sourceEvent","id":"bf1a5dfd-9853-3c1d-abc5-04cffac1886d"},{"kind":"sourceAvailability","id":"c4a381c1-b17b-3628-a7df-6976dea36449/LOANS"},{"kind":"sourceAvailability","id":"c4a381c1-b17b-3628-a7df-6976dea36449/RETURNS"},{"kind":"sourceAvailability","id":"c4a381c1-b17b-3628-a7df-6976dea36449/RECEIPTS"},{"kind":"sourceAvailability","id":"c4a381c1-b17b-3628-a7df-6976dea36449/TRANSFERS"},{"kind":"sourceAvailability","id":"c4a381c1-b17b-3628-a7df-6976dea36449/REPAIRS"},{"kind":"sourceAvailability","id":"c4a381c1-b17b-3628-a7df-6976dea36449/INTEGRATIONS"},{"kind":"sourceAvailability","id":"c4a381c1-b17b-3628-a7df-6976dea36449/INVENTORY"},{"kind":"operatorNote","id":"81dae626-c5e2-3d4c-9d3c-6403ebed7b42"},{"kind":"inventoryScope","id":"294f8877-9130-3961-8435-48be641fa20c"},{"kind":"inventoryScope","id":"9d6c09df-1bb9-3fa2-927d-b44bcfe9e1be"},{"kind":"inventoryScope","id":"8ba7cc3d-833a-342a-bcaa-e1d23f56a4e3"},{"kind":"inventoryScope","id":"8ba7cc3d-833a-342a-bcaa-e1d23f56a4e3"}]`

- Session : `{"scopeId":"294f8877-9130-3961-8435-48be641fa20c","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9d6c09df-1bb9-3fa2-927d-b44bcfe9e1be","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"8ba7cc3d-833a-342a-bcaa-e1d23f56a4e3","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"8ba7cc3d-833a-342a-bcaa-e1d23f56a4e3","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 68d2f065-6e51-39d8-bda8-15d12b305021

- Exemplaire : `52109819-7207-3265-983b-76eed568a51b`
- Famille privée : INVENTAIRE_INCOMPLET ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"d80dd030-fd2a-36e1-a515-80796e81d979","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["COLLECTION_INTERRUPTED"],"mechanism":"Interruption de collecte ; exemplaire resté au rayon.","physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","evidenceMask":"Collecte indisponible"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"L'absence de lecture ne démontre ni la destination, ni une perte, ni un vol ; données insuffisantes pour attribuer une cause.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État projeté : `{"copyId":"52109819-7207-3265-983b-76eed568a51b","status":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","sourceEventIds":["54c10abf-10c6-32fb-9c70-ddf93f80f8b5","23976c4b-5f15-309f-8fbf-49480f853f13"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 477ff82a-ad29-357e-81d5-577e69ea8084 | 2026-06-01T20:26:26Z | 2026-06-01T20:26:36Z | `{"operationId":"2de4257e-b8b5-3d2c-b832-daa7b9d78e56","correlationId":"e25d41a7-7fca-3c34-8773-93a9be0b70c5","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"77d82aba-1157-3ae1-96c2-292c52143cc2"}` |
| IntegrationProcessed | beab81e2-e3c2-3964-8af7-2c69264c8902 | 2026-06-02T20:26:26Z | 2026-06-02T20:26:36Z | `{"originalEventId":"477ff82a-ad29-357e-81d5-577e69ea8084","correlationId":"e25d41a7-7fca-3c34-8773-93a9be0b70c5","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 2d08a8d3-9df1-3069-bd1c-77da7c43945c | 2026-07-31T20:26:26Z | 2026-07-31T20:26:36Z | `{"operationId":"5e6e4fb5-1266-3665-b5b4-58fec79c14ea","correlationId":"2c61136c-3d74-3e5d-a51b-60b9cc840a8e","siteId":"NORD","locationId":null,"loanId":"4736a710-34bc-3555-9df3-a90adaaeba22"}` |
| IntegrationProcessed | c7eb3707-cb2b-3bd5-8180-03e5a570c5cb | 2026-07-31T20:27:26Z | 2026-07-31T20:27:36Z | `{"originalEventId":"2d08a8d3-9df1-3069-bd1c-77da7c43945c","correlationId":"2c61136c-3d74-3e5d-a51b-60b9cc840a8e","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | d0a8c699-15f4-3b05-ac27-aa303b6eb4a8 | 2026-08-20T20:26:26Z | 2026-08-20T20:26:36Z | `{"operationId":"317dac2a-8cc1-3eb5-bb83-ca9a8f59f666","correlationId":"34277001-8c2d-3c38-989d-bb2a56de490e","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"4736a710-34bc-3555-9df3-a90adaaeba22"}` |
| ReturnReceived | 54c10abf-10c6-32fb-9c70-ddf93f80f8b5 | 2026-08-20T20:26:56Z | 2026-08-20T20:27:06Z | `{"operationId":"4f5b245a-fc86-32ec-b5f5-c9cd84c994a5","correlationId":"ea8ecd94-7b6b-3a51-8ec8-d0dfe1806f8c","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"4736a710-34bc-3555-9df3-a90adaaeba22"}` |
| IntegrationProcessed | 23976c4b-5f15-309f-8fbf-49480f853f13 | 2026-08-21T20:26:26Z | 2026-08-21T20:26:36Z | `{"originalEventId":"54c10abf-10c6-32fb-9c70-ddf93f80f8b5","correlationId":"ea8ecd94-7b6b-3a51-8ec8-d0dfe1806f8c","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |

**observations**

**sourceAvailability**
- `{"copyId":"52109819-7207-3265-983b-76eed568a51b","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"52109819-7207-3265-983b-76eed568a51b","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"52109819-7207-3265-983b-76eed568a51b","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"52109819-7207-3265-983b-76eed568a51b","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"52109819-7207-3265-983b-76eed568a51b","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"52109819-7207-3265-983b-76eed568a51b","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"52109819-7207-3265-983b-76eed568a51b","source":"INVENTORY","status":"UNAVAILABLE","completeFrom":null,"completeTo":null}`

**operatorNotes**
- `{"noteId":"a14749ec-922b-3d6b-a6ec-307fec1aedf4","copyId":"52109819-7207-3265-983b-76eed568a51b","text":"Le terminal de collecte a cessé de répondre pendant le passage ; aucune conclusion sur les exemplaires non lus.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"52109819-7207-3265-983b-76eed568a51b"},{"kind":"sourceEvent","id":"477ff82a-ad29-357e-81d5-577e69ea8084"},{"kind":"sourceEvent","id":"beab81e2-e3c2-3964-8af7-2c69264c8902"},{"kind":"sourceEvent","id":"2d08a8d3-9df1-3069-bd1c-77da7c43945c"},{"kind":"sourceEvent","id":"c7eb3707-cb2b-3bd5-8180-03e5a570c5cb"},{"kind":"sourceEvent","id":"d0a8c699-15f4-3b05-ac27-aa303b6eb4a8"},{"kind":"sourceEvent","id":"54c10abf-10c6-32fb-9c70-ddf93f80f8b5"},{"kind":"sourceEvent","id":"23976c4b-5f15-309f-8fbf-49480f853f13"},{"kind":"sourceAvailability","id":"52109819-7207-3265-983b-76eed568a51b/LOANS"},{"kind":"sourceAvailability","id":"52109819-7207-3265-983b-76eed568a51b/RETURNS"},{"kind":"sourceAvailability","id":"52109819-7207-3265-983b-76eed568a51b/RECEIPTS"},{"kind":"sourceAvailability","id":"52109819-7207-3265-983b-76eed568a51b/TRANSFERS"},{"kind":"sourceAvailability","id":"52109819-7207-3265-983b-76eed568a51b/REPAIRS"},{"kind":"sourceAvailability","id":"52109819-7207-3265-983b-76eed568a51b/INTEGRATIONS"},{"kind":"sourceAvailability","id":"52109819-7207-3265-983b-76eed568a51b/INVENTORY"},{"kind":"operatorNote","id":"a14749ec-922b-3d6b-a6ec-307fec1aedf4"},{"kind":"inventoryScope","id":"07c957d2-b086-3b6b-9915-d21f1d5906cf"},{"kind":"inventoryScope","id":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c"},{"kind":"inventoryScope","id":"a481af0e-6045-37c4-bb84-d5269f5addc5"},{"kind":"inventoryScope","id":"9880a39c-a8f4-3f61-a473-804a0fd611a5"},{"kind":"inventoryScope","id":"79157bb9-b809-3388-ad1a-42d980616ea0"},{"kind":"inventoryScope","id":"79157bb9-b809-3388-ad1a-42d980616ea0"}]`

- Session : `{"scopeId":"07c957d2-b086-3b6b-9915-d21f1d5906cf","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"a481af0e-6045-37c4-bb84-d5269f5addc5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9880a39c-a8f4-3f61-a473-804a0fd611a5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"79157bb9-b809-3388-ad1a-42d980616ea0","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"79157bb9-b809-3388-ad1a-42d980616ea0","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 71c23d9d-e49f-3f0d-9d18-b7e454bef63d

- Exemplaire : `5dfd1adc-495a-3c5e-ae30-0a1eef788152`
- Famille privée : INVENTAIRE_INCOMPLET ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"d411780a-6b5a-3212-86a4-31df6453f949","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":"1"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"mechanism":"Exemplaire resté au rayon, lecture de contrôle réussie.","physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"Le scan valide au rayon attendu réfute l'absence courante.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"5dfd1adc-495a-3c5e-ae30-0a1eef788152","status":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","sourceEventIds":["19e28121-d615-3d94-9116-e4d87f15895b","fdc97132-72d2-3923-898d-8d1e7e20ff9c"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 5a933111-412f-318b-8d0b-5ffb52361776 | 2026-06-02T00:39:29Z | 2026-06-02T00:39:39Z | `{"operationId":"7224674c-12b1-3928-902c-8003203de959","correlationId":"d01f6bd0-d8d7-3722-9039-1930abceff41","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"8889a2a9-0830-350d-b634-7a18efc74ecb"}` |
| IntegrationProcessed | dded9b2c-d215-3410-a00c-5c00735fa18f | 2026-06-03T00:39:29Z | 2026-06-03T00:39:39Z | `{"originalEventId":"5a933111-412f-318b-8d0b-5ffb52361776","correlationId":"d01f6bd0-d8d7-3722-9039-1930abceff41","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 925b68e7-63a0-38dc-a958-9cbf85b3ac0c | 2026-08-01T00:39:29Z | 2026-08-01T00:39:39Z | `{"operationId":"ff49264d-ad23-3f8a-a730-21dbe8aee8cc","correlationId":"f39103cf-8853-3949-82e9-8bf6b3c6c324","siteId":"NORD","locationId":null,"loanId":"79f970a9-dc0d-31a7-94b4-6286e54ef444"}` |
| IntegrationProcessed | 62124464-a451-3587-a604-fae587ffefc9 | 2026-08-01T00:40:29Z | 2026-08-01T00:40:39Z | `{"originalEventId":"925b68e7-63a0-38dc-a958-9cbf85b3ac0c","correlationId":"f39103cf-8853-3949-82e9-8bf6b3c6c324","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | 76060c9b-217c-3fcc-acdf-452462936872 | 2026-08-21T00:39:29Z | 2026-08-21T00:39:39Z | `{"operationId":"6dc9be03-8c12-33f4-adb5-82953ff4e113","correlationId":"492f1764-203b-3bc0-a00f-414255b859d3","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"79f970a9-dc0d-31a7-94b4-6286e54ef444"}` |
| ReturnReceived | 19e28121-d615-3d94-9116-e4d87f15895b | 2026-08-21T00:39:59Z | 2026-08-21T00:40:09Z | `{"operationId":"24bf567b-89f2-36a7-bc11-062723b35d2d","correlationId":"41fb3257-919f-36f3-a95d-425ee864447b","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"79f970a9-dc0d-31a7-94b4-6286e54ef444"}` |
| IntegrationProcessed | fdc97132-72d2-3923-898d-8d1e7e20ff9c | 2026-08-22T00:39:29Z | 2026-08-22T00:39:39Z | `{"originalEventId":"19e28121-d615-3d94-9116-e4d87f15895b","correlationId":"41fb3257-919f-36f3-a95d-425ee864447b","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |

**observations**
- `{"observationId":"45c9a67c-765d-37c2-aad8-31d3d9b4bae3","copyId":"5dfd1adc-495a-3c5e-ae30-0a1eef788152","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","scopeId":"a481af0e-6045-37c4-bb84-d5269f5addc5","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"5dfd1adc-495a-3c5e-ae30-0a1eef788152","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5dfd1adc-495a-3c5e-ae30-0a1eef788152","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5dfd1adc-495a-3c5e-ae30-0a1eef788152","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5dfd1adc-495a-3c5e-ae30-0a1eef788152","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5dfd1adc-495a-3c5e-ae30-0a1eef788152","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5dfd1adc-495a-3c5e-ae30-0a1eef788152","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5dfd1adc-495a-3c5e-ae30-0a1eef788152","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**
- `{"noteId":"f18752ba-f2e9-325e-aca6-0eebad1e9455","copyId":"5dfd1adc-495a-3c5e-ae30-0a1eef788152","text":"Nouvelle lecture du code-barres effectuée dans le rayon affecté.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"5dfd1adc-495a-3c5e-ae30-0a1eef788152"},{"kind":"sourceEvent","id":"5a933111-412f-318b-8d0b-5ffb52361776"},{"kind":"sourceEvent","id":"dded9b2c-d215-3410-a00c-5c00735fa18f"},{"kind":"sourceEvent","id":"925b68e7-63a0-38dc-a958-9cbf85b3ac0c"},{"kind":"sourceEvent","id":"62124464-a451-3587-a604-fae587ffefc9"},{"kind":"sourceEvent","id":"76060c9b-217c-3fcc-acdf-452462936872"},{"kind":"sourceEvent","id":"19e28121-d615-3d94-9116-e4d87f15895b"},{"kind":"sourceEvent","id":"fdc97132-72d2-3923-898d-8d1e7e20ff9c"},{"kind":"observation","id":"45c9a67c-765d-37c2-aad8-31d3d9b4bae3"},{"kind":"inventoryScope","id":"a481af0e-6045-37c4-bb84-d5269f5addc5"},{"kind":"sourceAvailability","id":"5dfd1adc-495a-3c5e-ae30-0a1eef788152/LOANS"},{"kind":"sourceAvailability","id":"5dfd1adc-495a-3c5e-ae30-0a1eef788152/RETURNS"},{"kind":"sourceAvailability","id":"5dfd1adc-495a-3c5e-ae30-0a1eef788152/RECEIPTS"},{"kind":"sourceAvailability","id":"5dfd1adc-495a-3c5e-ae30-0a1eef788152/TRANSFERS"},{"kind":"sourceAvailability","id":"5dfd1adc-495a-3c5e-ae30-0a1eef788152/REPAIRS"},{"kind":"sourceAvailability","id":"5dfd1adc-495a-3c5e-ae30-0a1eef788152/INTEGRATIONS"},{"kind":"sourceAvailability","id":"5dfd1adc-495a-3c5e-ae30-0a1eef788152/INVENTORY"},{"kind":"operatorNote","id":"f18752ba-f2e9-325e-aca6-0eebad1e9455"},{"kind":"inventoryScope","id":"07c957d2-b086-3b6b-9915-d21f1d5906cf"},{"kind":"inventoryScope","id":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c"},{"kind":"inventoryScope","id":"a481af0e-6045-37c4-bb84-d5269f5addc5"}]`

- Session : `{"scopeId":"a481af0e-6045-37c4-bb84-d5269f5addc5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"07c957d2-b086-3b6b-9915-d21f1d5906cf","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"a481af0e-6045-37c4-bb84-d5269f5addc5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 73307c62-58d2-344c-bb46-380211169ce3

- Exemplaire : `5471fffe-bc7f-392e-bbb0-8928e8483e8a`
- Famille privée : INVENTAIRE_INCOMPLET ; variante : POSITIVE
- Symptômes : `[{"symptomId":"0a2fbee1-b5fc-3803-af8e-5b64c38f5f51","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["COLLECTION_INTERRUPTED"],"mechanism":"Interruption de collecte ; exemplaire resté au rayon.","physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["INVENTORY_INCOMPLETE"],"explanation":"La session interrompue explique l'incertitude de comptage ; la présence physique reste à vérifier.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État projeté : `{"copyId":"5471fffe-bc7f-392e-bbb0-8928e8483e8a","status":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","sourceEventIds":["dbbd7e85-c204-31c9-9124-4f75c2aa1408","d2587481-7daa-32f0-bcf2-62cd04b8e769"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 5570ce6c-691c-35d1-bbe3-12a802b0af7a | 2026-06-01T19:56:15Z | 2026-06-01T19:56:25Z | `{"operationId":"f38cd5b6-0c26-31d3-a29e-a1af56c0aa57","correlationId":"03daa387-b679-308b-8420-861f63135873","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"7fb53f42-ca5b-3b85-8802-ecedd40f8d90"}` |
| IntegrationProcessed | 1a9df6c5-d9de-3e2e-8e6e-700a029b5b03 | 2026-06-02T19:56:15Z | 2026-06-02T19:56:25Z | `{"originalEventId":"5570ce6c-691c-35d1-bbe3-12a802b0af7a","correlationId":"03daa387-b679-308b-8420-861f63135873","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | f2ffe770-ba45-3a08-ae2a-8cce95f1a57e | 2026-07-31T19:56:15Z | 2026-07-31T19:56:25Z | `{"operationId":"6ea8bb55-9281-3f07-9232-a40fac759144","correlationId":"3e7122f8-50af-3f42-b35f-c8bd53407892","siteId":"CENTRE","locationId":null,"loanId":"38b2b8e2-7ba9-3043-a22f-2a68341a2d9e"}` |
| IntegrationProcessed | 930eac62-f324-3232-82c3-a20d54e7e3a3 | 2026-07-31T19:57:15Z | 2026-07-31T19:57:25Z | `{"originalEventId":"f2ffe770-ba45-3a08-ae2a-8cce95f1a57e","correlationId":"3e7122f8-50af-3f42-b35f-c8bd53407892","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | f9da5f4d-730d-3513-8b7f-3c971efb2d21 | 2026-08-20T19:56:15Z | 2026-08-20T19:56:25Z | `{"operationId":"6d230c9b-67de-3580-bf1f-35302be517c1","correlationId":"b7bf9eaf-d76a-3c72-b59a-bce237d9b0cd","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"38b2b8e2-7ba9-3043-a22f-2a68341a2d9e"}` |
| ReturnReceived | 5da0352e-8ee6-3d35-939c-e2f26fb6ba65 | 2026-08-20T19:56:45Z | 2026-08-20T19:56:55Z | `{"operationId":"26b72219-6795-3252-98ae-a4728493bdce","correlationId":"143cbca3-6788-3bc1-b37c-067959200f36","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"38b2b8e2-7ba9-3043-a22f-2a68341a2d9e"}` |
| IntegrationProcessed | c50eb46c-a68b-37cc-be0b-fc8ac867215a | 2026-08-21T19:56:15Z | 2026-08-21T19:56:25Z | `{"originalEventId":"5da0352e-8ee6-3d35-939c-e2f26fb6ba65","correlationId":"143cbca3-6788-3bc1-b37c-067959200f36","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| TransferDispatched | 5be10dde-3f2f-39c2-b5d7-113af8e263d1 | 2026-08-30T19:56:15Z | 2026-08-30T19:56:25Z | `{"operationId":"56abfa5d-8cc5-34a7-acd2-1a43e3999683","correlationId":"69c03193-9578-3a43-86e7-849d67f7b48b","siteId":"CENTRE","locationId":null,"transferId":"67558938-a2ff-39e7-bdb1-3aba713666b6","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | b5af0754-f19a-3169-96e2-629ca5838d91 | 2026-08-30T19:57:15Z | 2026-08-30T19:57:25Z | `{"originalEventId":"5be10dde-3f2f-39c2-b5d7-113af8e263d1","correlationId":"69c03193-9578-3a43-86e7-849d67f7b48b","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| TransferReceived | dbbd7e85-c204-31c9-9124-4f75c2aa1408 | 2026-08-31T19:56:15Z | 2026-08-31T19:56:25Z | `{"operationId":"dbf69cc4-7e07-30e2-9bf7-b58016d01264","correlationId":"a3fbc221-c2bb-399c-ba39-d90a045e53fa","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","transferId":"67558938-a2ff-39e7-bdb1-3aba713666b6","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | d2587481-7daa-32f0-bcf2-62cd04b8e769 | 2026-09-01T19:56:15Z | 2026-09-01T19:56:25Z | `{"originalEventId":"dbbd7e85-c204-31c9-9124-4f75c2aa1408","correlationId":"a3fbc221-c2bb-399c-ba39-d90a045e53fa","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |

**observations**

**sourceAvailability**
- `{"copyId":"5471fffe-bc7f-392e-bbb0-8928e8483e8a","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5471fffe-bc7f-392e-bbb0-8928e8483e8a","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5471fffe-bc7f-392e-bbb0-8928e8483e8a","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5471fffe-bc7f-392e-bbb0-8928e8483e8a","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5471fffe-bc7f-392e-bbb0-8928e8483e8a","source":"INVENTORY","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"5471fffe-bc7f-392e-bbb0-8928e8483e8a","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5471fffe-bc7f-392e-bbb0-8928e8483e8a","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**
- `{"noteId":"6b606860-a7f6-350f-8962-86cba78d96d9","copyId":"5471fffe-bc7f-392e-bbb0-8928e8483e8a","text":"Le terminal de collecte a cessé de répondre pendant le passage ; aucune conclusion sur les exemplaires non lus.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"5471fffe-bc7f-392e-bbb0-8928e8483e8a"},{"kind":"sourceEvent","id":"5570ce6c-691c-35d1-bbe3-12a802b0af7a"},{"kind":"sourceEvent","id":"1a9df6c5-d9de-3e2e-8e6e-700a029b5b03"},{"kind":"sourceEvent","id":"f2ffe770-ba45-3a08-ae2a-8cce95f1a57e"},{"kind":"sourceEvent","id":"930eac62-f324-3232-82c3-a20d54e7e3a3"},{"kind":"sourceEvent","id":"f9da5f4d-730d-3513-8b7f-3c971efb2d21"},{"kind":"sourceEvent","id":"5da0352e-8ee6-3d35-939c-e2f26fb6ba65"},{"kind":"sourceEvent","id":"c50eb46c-a68b-37cc-be0b-fc8ac867215a"},{"kind":"sourceEvent","id":"5be10dde-3f2f-39c2-b5d7-113af8e263d1"},{"kind":"sourceEvent","id":"b5af0754-f19a-3169-96e2-629ca5838d91"},{"kind":"sourceEvent","id":"dbbd7e85-c204-31c9-9124-4f75c2aa1408"},{"kind":"sourceEvent","id":"d2587481-7daa-32f0-bcf2-62cd04b8e769"},{"kind":"sourceAvailability","id":"5471fffe-bc7f-392e-bbb0-8928e8483e8a/LOANS"},{"kind":"sourceAvailability","id":"5471fffe-bc7f-392e-bbb0-8928e8483e8a/RETURNS"},{"kind":"sourceAvailability","id":"5471fffe-bc7f-392e-bbb0-8928e8483e8a/RECEIPTS"},{"kind":"sourceAvailability","id":"5471fffe-bc7f-392e-bbb0-8928e8483e8a/TRANSFERS"},{"kind":"sourceAvailability","id":"5471fffe-bc7f-392e-bbb0-8928e8483e8a/REPAIRS"},{"kind":"sourceAvailability","id":"5471fffe-bc7f-392e-bbb0-8928e8483e8a/INTEGRATIONS"},{"kind":"sourceAvailability","id":"5471fffe-bc7f-392e-bbb0-8928e8483e8a/INVENTORY"},{"kind":"operatorNote","id":"6b606860-a7f6-350f-8962-86cba78d96d9"},{"kind":"inventoryScope","id":"07c957d2-b086-3b6b-9915-d21f1d5906cf"},{"kind":"inventoryScope","id":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c"},{"kind":"inventoryScope","id":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c"}]`

- Session : `{"scopeId":"07c957d2-b086-3b6b-9915-d21f1d5906cf","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 74c8488b-934d-331d-8f27-98a81938eccf

- Exemplaire : `0b92d754-4dbf-3887-84e6-46d631b9e27e`
- Famille privée : ABSENCE_INEXPLIQUEE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"8ffa17fe-2212-3cef-a48b-b7cc325cfe7b","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":"1"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"mechanism":"Exemplaire resté au rayon, lecture de contrôle réussie.","physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"Le scan valide au rayon attendu réfute l'absence courante.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"0b92d754-4dbf-3887-84e6-46d631b9e27e","status":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","sourceEventIds":["d5f8d190-9d51-3979-8817-d92945627028","aa208b7f-4433-3a53-93d9-51fce7912986"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 5cd3f628-62e0-35d1-b1dc-1f8be8b88f71 | 2026-06-02T06:30:49Z | 2026-06-02T06:30:59Z | `{"operationId":"5a348d31-1ed2-3e5c-8f9d-880b89fec053","correlationId":"c4fc7a85-a566-37f7-a05e-defafce5445e","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"20708815-3456-3f19-aae8-ded159db2f23"}` |
| IntegrationProcessed | 74f04ee2-e665-37b3-b003-205cebf9774d | 2026-06-03T06:30:49Z | 2026-06-03T06:30:59Z | `{"originalEventId":"5cd3f628-62e0-35d1-b1dc-1f8be8b88f71","correlationId":"c4fc7a85-a566-37f7-a05e-defafce5445e","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | e704b423-560f-345a-ad13-eddb38222a84 | 2026-08-01T06:30:49Z | 2026-08-01T06:30:59Z | `{"operationId":"bea14334-e72b-3645-8ef2-c979fd1087b2","correlationId":"e5f6034f-f055-36a8-9ce9-3f4dc1bffc29","siteId":"CENTRE","locationId":null,"loanId":"f845b328-cde6-3315-94df-4c5fa02584b6"}` |
| IntegrationProcessed | c7eb5b8b-751b-3056-aa92-1a122c282d83 | 2026-08-01T06:31:49Z | 2026-08-01T06:31:59Z | `{"originalEventId":"e704b423-560f-345a-ad13-eddb38222a84","correlationId":"e5f6034f-f055-36a8-9ce9-3f4dc1bffc29","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 659e8c1d-38a9-3dc9-8ef4-11684f659dda | 2026-08-21T06:30:49Z | 2026-08-21T06:30:59Z | `{"operationId":"948542ec-c025-30e6-b779-d85e9f8d76d5","correlationId":"d500f4bd-e983-3f02-92ae-e61e2176d2c4","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"f845b328-cde6-3315-94df-4c5fa02584b6"}` |
| ReturnReceived | 926b2bbc-050b-36cb-8378-1b2374c8349e | 2026-08-21T06:31:19Z | 2026-08-21T06:31:29Z | `{"operationId":"658a4ce1-dd83-33b0-a8ac-e8a1e33b0988","correlationId":"6da090f9-f0eb-3e79-b440-a3e314799dca","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"f845b328-cde6-3315-94df-4c5fa02584b6"}` |
| IntegrationProcessed | 24c4627e-06b3-3808-bbd9-b05c6b9709cf | 2026-08-22T06:30:49Z | 2026-08-22T06:30:59Z | `{"originalEventId":"926b2bbc-050b-36cb-8378-1b2374c8349e","correlationId":"6da090f9-f0eb-3e79-b440-a3e314799dca","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| TransferDispatched | 8020a5b6-c910-33a2-9284-869050fda8a2 | 2026-08-31T06:30:49Z | 2026-08-31T06:30:59Z | `{"operationId":"772b2382-e866-37c2-a207-93ba4449ad83","correlationId":"cb4b5404-0eba-3ca5-9eb2-243cd78dec44","siteId":"CENTRE","locationId":null,"transferId":"07c66ea4-8210-38c4-bcf7-e2c69c3f4b09","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | 0935423c-e6c7-3e58-a942-97cd755bf60a | 2026-08-31T06:31:49Z | 2026-08-31T06:31:59Z | `{"originalEventId":"8020a5b6-c910-33a2-9284-869050fda8a2","correlationId":"cb4b5404-0eba-3ca5-9eb2-243cd78dec44","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| TransferReceived | d5f8d190-9d51-3979-8817-d92945627028 | 2026-09-01T06:30:49Z | 2026-09-01T06:30:59Z | `{"operationId":"5971995d-0435-3ec3-bb6b-69890a08a808","correlationId":"c0c503e6-1c1b-38e5-be17-970a92b1fb05","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","transferId":"07c66ea4-8210-38c4-bcf7-e2c69c3f4b09","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | aa208b7f-4433-3a53-93d9-51fce7912986 | 2026-09-02T06:30:49Z | 2026-09-02T06:30:59Z | `{"originalEventId":"d5f8d190-9d51-3979-8817-d92945627028","correlationId":"c0c503e6-1c1b-38e5-be17-970a92b1fb05","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |

**observations**
- `{"observationId":"81c64d2a-ff25-392e-a9ef-7fbda2537ec8","copyId":"0b92d754-4dbf-3887-84e6-46d631b9e27e","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","scopeId":"eba23a81-5103-3ded-ba6b-6619213a7840","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"0b92d754-4dbf-3887-84e6-46d631b9e27e","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0b92d754-4dbf-3887-84e6-46d631b9e27e","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0b92d754-4dbf-3887-84e6-46d631b9e27e","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0b92d754-4dbf-3887-84e6-46d631b9e27e","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0b92d754-4dbf-3887-84e6-46d631b9e27e","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0b92d754-4dbf-3887-84e6-46d631b9e27e","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0b92d754-4dbf-3887-84e6-46d631b9e27e","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**
- `{"noteId":"512eab75-6e44-3676-a9ff-7800c05b50a3","copyId":"0b92d754-4dbf-3887-84e6-46d631b9e27e","text":"Nouvelle lecture du code-barres effectuée dans le rayon affecté.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"0b92d754-4dbf-3887-84e6-46d631b9e27e"},{"kind":"sourceEvent","id":"5cd3f628-62e0-35d1-b1dc-1f8be8b88f71"},{"kind":"sourceEvent","id":"74f04ee2-e665-37b3-b003-205cebf9774d"},{"kind":"sourceEvent","id":"e704b423-560f-345a-ad13-eddb38222a84"},{"kind":"sourceEvent","id":"c7eb5b8b-751b-3056-aa92-1a122c282d83"},{"kind":"sourceEvent","id":"659e8c1d-38a9-3dc9-8ef4-11684f659dda"},{"kind":"sourceEvent","id":"926b2bbc-050b-36cb-8378-1b2374c8349e"},{"kind":"sourceEvent","id":"24c4627e-06b3-3808-bbd9-b05c6b9709cf"},{"kind":"sourceEvent","id":"8020a5b6-c910-33a2-9284-869050fda8a2"},{"kind":"sourceEvent","id":"0935423c-e6c7-3e58-a942-97cd755bf60a"},{"kind":"sourceEvent","id":"d5f8d190-9d51-3979-8817-d92945627028"},{"kind":"sourceEvent","id":"aa208b7f-4433-3a53-93d9-51fce7912986"},{"kind":"observation","id":"81c64d2a-ff25-392e-a9ef-7fbda2537ec8"},{"kind":"inventoryScope","id":"eba23a81-5103-3ded-ba6b-6619213a7840"},{"kind":"sourceAvailability","id":"0b92d754-4dbf-3887-84e6-46d631b9e27e/LOANS"},{"kind":"sourceAvailability","id":"0b92d754-4dbf-3887-84e6-46d631b9e27e/RETURNS"},{"kind":"sourceAvailability","id":"0b92d754-4dbf-3887-84e6-46d631b9e27e/RECEIPTS"},{"kind":"sourceAvailability","id":"0b92d754-4dbf-3887-84e6-46d631b9e27e/TRANSFERS"},{"kind":"sourceAvailability","id":"0b92d754-4dbf-3887-84e6-46d631b9e27e/REPAIRS"},{"kind":"sourceAvailability","id":"0b92d754-4dbf-3887-84e6-46d631b9e27e/INTEGRATIONS"},{"kind":"sourceAvailability","id":"0b92d754-4dbf-3887-84e6-46d631b9e27e/INVENTORY"},{"kind":"operatorNote","id":"512eab75-6e44-3676-a9ff-7800c05b50a3"},{"kind":"inventoryScope","id":"07c957d2-b086-3b6b-9915-d21f1d5906cf"},{"kind":"inventoryScope","id":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c"},{"kind":"inventoryScope","id":"a481af0e-6045-37c4-bb84-d5269f5addc5"},{"kind":"inventoryScope","id":"9880a39c-a8f4-3f61-a473-804a0fd611a5"},{"kind":"inventoryScope","id":"79157bb9-b809-3388-ad1a-42d980616ea0"},{"kind":"inventoryScope","id":"eba23a81-5103-3ded-ba6b-6619213a7840"}]`

- Session : `{"scopeId":"eba23a81-5103-3ded-ba6b-6619213a7840","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"07c957d2-b086-3b6b-9915-d21f1d5906cf","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"a481af0e-6045-37c4-bb84-d5269f5addc5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9880a39c-a8f4-3f61-a473-804a0fd611a5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"79157bb9-b809-3388-ad1a-42d980616ea0","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"eba23a81-5103-3ded-ba6b-6619213a7840","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 7a939d3f-1be1-3b36-a4ac-49bc4f8fa4af

- Exemplaire : `20ac2c9f-9b4b-35aa-a625-31bcfa02be8c`
- Famille privée : ABSENCE_INEXPLIQUEE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"8c271812-dbb0-36b0-a5fa-3d43442ab002","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["UNOBSERVED_MOVE"],"mechanism":"Exemplaire déplacé hors du périmètre instrumenté ; la destination n'est pas exposée et aucune perte n'est simulée.","physicalStatus":"ON_SHELF","physicalSiteId":"SUD","physicalLocationId":null,"evidenceMask":"Collecte indisponible"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"L'absence de lecture ne démontre ni la destination, ni une perte, ni un vol ; données insuffisantes pour attribuer une cause.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État projeté : `{"copyId":"20ac2c9f-9b4b-35aa-a625-31bcfa02be8c","status":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","sourceEventIds":["73728297-ebd5-3fa0-9414-063afadc3bb0","ae515174-479a-3411-8e88-bb408d7e7920"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 47da5ed9-93f2-3613-a166-a5a946a00c43 | 2026-06-01T16:17:53Z | 2026-06-01T16:18:03Z | `{"operationId":"94d877b6-3944-38a5-8a5c-aa117f9521c7","correlationId":"e4407cc1-05f9-3503-987a-74609c9b5a66","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"070217b2-fccc-36af-b440-b79abe2280d5"}` |
| IntegrationProcessed | be668d70-6db1-34a3-b1a0-87c6c113e3e3 | 2026-06-02T16:17:53Z | 2026-06-02T16:18:03Z | `{"originalEventId":"47da5ed9-93f2-3613-a166-a5a946a00c43","correlationId":"e4407cc1-05f9-3503-987a-74609c9b5a66","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | b601d3e7-1334-3a7e-9d56-b1db68ffd6b8 | 2026-07-31T16:17:53Z | 2026-07-31T16:18:03Z | `{"operationId":"09fdcbf1-882f-3907-97e1-6331b2b3710c","correlationId":"d9ca491a-8cf2-395f-96da-97d4fa0c2101","siteId":"SUD","locationId":null,"loanId":"aa15db21-2682-386d-b5ae-37111569ae3e"}` |
| IntegrationProcessed | 35b733f1-19f8-3f03-abe7-4841c7fc41e2 | 2026-07-31T16:18:53Z | 2026-07-31T16:19:03Z | `{"originalEventId":"b601d3e7-1334-3a7e-9d56-b1db68ffd6b8","correlationId":"d9ca491a-8cf2-395f-96da-97d4fa0c2101","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | f08a6b91-c982-34bc-82d3-c26a3181786f | 2026-08-20T16:17:53Z | 2026-08-20T16:18:03Z | `{"operationId":"b0eb45d0-4bd5-3014-b2a2-890ae4415879","correlationId":"120d55a7-d1ff-37b9-b261-aff7b7911742","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"aa15db21-2682-386d-b5ae-37111569ae3e"}` |
| ReturnReceived | 73728297-ebd5-3fa0-9414-063afadc3bb0 | 2026-08-20T16:18:23Z | 2026-08-20T16:18:33Z | `{"operationId":"da2658fe-476f-3416-815e-ec5e0d286084","correlationId":"3aa678d7-65f0-3386-b3a0-4f7ee04d4ca6","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"aa15db21-2682-386d-b5ae-37111569ae3e"}` |
| IntegrationProcessed | ae515174-479a-3411-8e88-bb408d7e7920 | 2026-08-21T16:17:53Z | 2026-08-21T16:18:03Z | `{"originalEventId":"73728297-ebd5-3fa0-9414-063afadc3bb0","correlationId":"3aa678d7-65f0-3386-b3a0-4f7ee04d4ca6","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |

**observations**

**sourceAvailability**
- `{"copyId":"20ac2c9f-9b4b-35aa-a625-31bcfa02be8c","source":"INVENTORY","status":"UNAVAILABLE","completeFrom":null,"completeTo":null}`
- `{"copyId":"20ac2c9f-9b4b-35aa-a625-31bcfa02be8c","source":"LOANS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"20ac2c9f-9b4b-35aa-a625-31bcfa02be8c","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"20ac2c9f-9b4b-35aa-a625-31bcfa02be8c","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"20ac2c9f-9b4b-35aa-a625-31bcfa02be8c","source":"TRANSFERS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"20ac2c9f-9b4b-35aa-a625-31bcfa02be8c","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"20ac2c9f-9b4b-35aa-a625-31bcfa02be8c","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**
- `{"noteId":"d4a9e186-7cc3-3881-addf-3b281399069b","copyId":"20ac2c9f-9b4b-35aa-a625-31bcfa02be8c","text":"Le passage au rayon n'a pas permis de retrouver cet exemplaire ; contrôle complémentaire demandé.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"20ac2c9f-9b4b-35aa-a625-31bcfa02be8c"},{"kind":"sourceEvent","id":"47da5ed9-93f2-3613-a166-a5a946a00c43"},{"kind":"sourceEvent","id":"be668d70-6db1-34a3-b1a0-87c6c113e3e3"},{"kind":"sourceEvent","id":"b601d3e7-1334-3a7e-9d56-b1db68ffd6b8"},{"kind":"sourceEvent","id":"35b733f1-19f8-3f03-abe7-4841c7fc41e2"},{"kind":"sourceEvent","id":"f08a6b91-c982-34bc-82d3-c26a3181786f"},{"kind":"sourceEvent","id":"73728297-ebd5-3fa0-9414-063afadc3bb0"},{"kind":"sourceEvent","id":"ae515174-479a-3411-8e88-bb408d7e7920"},{"kind":"sourceAvailability","id":"20ac2c9f-9b4b-35aa-a625-31bcfa02be8c/LOANS"},{"kind":"sourceAvailability","id":"20ac2c9f-9b4b-35aa-a625-31bcfa02be8c/RETURNS"},{"kind":"sourceAvailability","id":"20ac2c9f-9b4b-35aa-a625-31bcfa02be8c/RECEIPTS"},{"kind":"sourceAvailability","id":"20ac2c9f-9b4b-35aa-a625-31bcfa02be8c/TRANSFERS"},{"kind":"sourceAvailability","id":"20ac2c9f-9b4b-35aa-a625-31bcfa02be8c/REPAIRS"},{"kind":"sourceAvailability","id":"20ac2c9f-9b4b-35aa-a625-31bcfa02be8c/INTEGRATIONS"},{"kind":"sourceAvailability","id":"20ac2c9f-9b4b-35aa-a625-31bcfa02be8c/INVENTORY"},{"kind":"operatorNote","id":"d4a9e186-7cc3-3881-addf-3b281399069b"},{"kind":"inventoryScope","id":"294f8877-9130-3961-8435-48be641fa20c"},{"kind":"inventoryScope","id":"9d6c09df-1bb9-3fa2-927d-b44bcfe9e1be"},{"kind":"inventoryScope","id":"8ba7cc3d-833a-342a-bcaa-e1d23f56a4e3"},{"kind":"inventoryScope","id":"59269371-fb13-3ca1-a62a-01272d903229"},{"kind":"inventoryScope","id":"139f34be-b437-3f23-84c9-757fbea07b69"},{"kind":"inventoryScope","id":"002b3157-56c1-3594-86e4-95e1ed6a3889"},{"kind":"inventoryScope","id":"002b3157-56c1-3594-86e4-95e1ed6a3889"}]`

- Session : `{"scopeId":"294f8877-9130-3961-8435-48be641fa20c","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9d6c09df-1bb9-3fa2-927d-b44bcfe9e1be","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"8ba7cc3d-833a-342a-bcaa-e1d23f56a4e3","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"59269371-fb13-3ca1-a62a-01272d903229","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"139f34be-b437-3f23-84c9-757fbea07b69","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"002b3157-56c1-3594-86e4-95e1ed6a3889","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"002b3157-56c1-3594-86e4-95e1ed6a3889","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 8059788e-57bc-3ba8-8ffe-599827c47237

- Exemplaire : `15c1e15c-e8b6-3bab-8ae5-656f3055da44`
- Famille privée : REPARATION_NON_PROJETEE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"101ea8fc-d5f7-329f-a384-42e9cf5bc3eb","kind":"STATUS_MISMATCH","expectedValue":"ON_SHELF","observedValue":"IN_REPAIR"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"30884223-6ed8-3f7b-afca-8da83d9125ec","physicalStatus":"IN_REPAIR","physicalSiteId":"CENTRE","physicalLocationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"15c1e15c-e8b6-3bab-8ae5-656f3055da44","status":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","sourceEventIds":["d7b86034-dc02-3255-8d36-0932140c6cfb","4c6e73c7-ed54-38b4-9bf7-21fb8a30e272"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 7b96db85-8077-3bb6-a368-2dfd48d3e843 | 2026-06-02T02:42:08Z | 2026-06-02T02:42:18Z | `{"operationId":"70f8ed6e-ec13-3115-a577-2b712b94d03a","correlationId":"72e099e5-0fc1-3fcf-83ac-bd14fdca93b1","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"4342c4bc-9dd0-3900-be93-71db1086771b"}` |
| IntegrationProcessed | 9b47dbd9-4e4d-3357-925b-e00eded8d35d | 2026-06-03T02:42:08Z | 2026-06-03T02:42:18Z | `{"originalEventId":"7b96db85-8077-3bb6-a368-2dfd48d3e843","correlationId":"72e099e5-0fc1-3fcf-83ac-bd14fdca93b1","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 49622946-ca3b-33de-9bc1-df2556e00763 | 2026-08-01T02:42:08Z | 2026-08-01T02:42:18Z | `{"operationId":"7fa1cb67-a1e0-3314-8110-b149b1729734","correlationId":"b740c411-155f-30e4-8abd-6857d463c654","siteId":"CENTRE","locationId":null,"loanId":"a63cb6ee-e6ff-3079-af98-6d820a83ddf5"}` |
| IntegrationProcessed | a4563dcb-bed0-3b5c-8c28-ddc698113a62 | 2026-08-01T02:43:08Z | 2026-08-01T02:43:18Z | `{"originalEventId":"49622946-ca3b-33de-9bc1-df2556e00763","correlationId":"b740c411-155f-30e4-8abd-6857d463c654","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | ad069df8-282f-349b-b8e4-8ed0bcfd1b05 | 2026-08-21T02:42:08Z | 2026-08-21T02:42:18Z | `{"operationId":"2e62eb68-129a-344b-a25c-8cbf6f8cb133","correlationId":"3166f615-06be-36e6-b259-3918f5e4b50e","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"a63cb6ee-e6ff-3079-af98-6d820a83ddf5"}` |
| ReturnReceived | d7b86034-dc02-3255-8d36-0932140c6cfb | 2026-08-21T02:42:38Z | 2026-08-21T02:42:48Z | `{"operationId":"cb7cc12a-0952-39b3-a633-d38290c250d5","correlationId":"2a724620-2bb2-32fb-b675-624205575e11","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"a63cb6ee-e6ff-3079-af98-6d820a83ddf5"}` |
| IntegrationProcessed | 4c6e73c7-ed54-38b4-9bf7-21fb8a30e272 | 2026-08-22T02:42:08Z | 2026-08-22T02:42:18Z | `{"originalEventId":"d7b86034-dc02-3255-8d36-0932140c6cfb","correlationId":"2a724620-2bb2-32fb-b675-624205575e11","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| RepairStarted | 30884223-6ed8-3f7b-afca-8da83d9125ec | 2026-09-27T06:59:22Z | 2026-09-27T06:59:32Z | `{"operationId":"d2ab837e-1092-3a3e-a77c-fca1d8dfe0e0","correlationId":"554bdcd9-4f0a-3ece-960c-5c4f46aa8521","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","repairId":"8059828c-58ad-30c8-a13d-506219bc7072"}` |
| IntegrationProcessed | 1b692b1e-8718-3574-92b1-564e38f5862d | 2026-09-27T06:59:52Z | 2026-09-27T07:00:02Z | `{"originalEventId":"30884223-6ed8-3f7b-afca-8da83d9125ec","correlationId":"554bdcd9-4f0a-3ece-960c-5c4f46aa8521","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**observations**
- `{"observationId":"3b86a0ba-7f34-3c3e-88fb-5fb6252cdb20","copyId":"15c1e15c-e8b6-3bab-8ae5-656f3055da44","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","scopeId":"9be0d6d0-cc8d-3d92-92f6-b5291f868628","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"15c1e15c-e8b6-3bab-8ae5-656f3055da44","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"15c1e15c-e8b6-3bab-8ae5-656f3055da44","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"15c1e15c-e8b6-3bab-8ae5-656f3055da44","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"15c1e15c-e8b6-3bab-8ae5-656f3055da44","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"15c1e15c-e8b6-3bab-8ae5-656f3055da44","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"15c1e15c-e8b6-3bab-8ae5-656f3055da44","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"15c1e15c-e8b6-3bab-8ae5-656f3055da44","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"15c1e15c-e8b6-3bab-8ae5-656f3055da44"},{"kind":"sourceEvent","id":"7b96db85-8077-3bb6-a368-2dfd48d3e843"},{"kind":"sourceEvent","id":"9b47dbd9-4e4d-3357-925b-e00eded8d35d"},{"kind":"sourceEvent","id":"49622946-ca3b-33de-9bc1-df2556e00763"},{"kind":"sourceEvent","id":"a4563dcb-bed0-3b5c-8c28-ddc698113a62"},{"kind":"sourceEvent","id":"ad069df8-282f-349b-b8e4-8ed0bcfd1b05"},{"kind":"sourceEvent","id":"d7b86034-dc02-3255-8d36-0932140c6cfb"},{"kind":"sourceEvent","id":"4c6e73c7-ed54-38b4-9bf7-21fb8a30e272"},{"kind":"sourceEvent","id":"30884223-6ed8-3f7b-afca-8da83d9125ec"},{"kind":"sourceEvent","id":"1b692b1e-8718-3574-92b1-564e38f5862d"},{"kind":"observation","id":"3b86a0ba-7f34-3c3e-88fb-5fb6252cdb20"},{"kind":"inventoryScope","id":"9be0d6d0-cc8d-3d92-92f6-b5291f868628"},{"kind":"sourceAvailability","id":"15c1e15c-e8b6-3bab-8ae5-656f3055da44/LOANS"},{"kind":"sourceAvailability","id":"15c1e15c-e8b6-3bab-8ae5-656f3055da44/RETURNS"},{"kind":"sourceAvailability","id":"15c1e15c-e8b6-3bab-8ae5-656f3055da44/RECEIPTS"},{"kind":"sourceAvailability","id":"15c1e15c-e8b6-3bab-8ae5-656f3055da44/TRANSFERS"},{"kind":"sourceAvailability","id":"15c1e15c-e8b6-3bab-8ae5-656f3055da44/REPAIRS"},{"kind":"sourceAvailability","id":"15c1e15c-e8b6-3bab-8ae5-656f3055da44/INTEGRATIONS"},{"kind":"sourceAvailability","id":"15c1e15c-e8b6-3bab-8ae5-656f3055da44/INVENTORY"},{"kind":"inventoryScope","id":"8021dca8-65fe-3cab-985e-d4fb9df5c308"},{"kind":"inventoryScope","id":"b0027657-6abb-37d2-8bc4-9341357dfe79"},{"kind":"inventoryScope","id":"4757f5eb-f104-3413-b118-51a6951a650c"}]`

- Session : `{"scopeId":"9be0d6d0-cc8d-3d92-92f6-b5291f868628","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"8021dca8-65fe-3cab-985e-d4fb9df5c308","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b0027657-6abb-37d2-8bc4-9341357dfe79","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4757f5eb-f104-3413-b118-51a6951a650c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 845f2015-d1b5-386d-af45-7c94a253c686

- Exemplaire : `dd53f851-3be7-3b78-b6d8-ef0d89778083`
- Famille privée : PRET_NON_PROJETE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"55f24c5b-ba6a-346b-9c93-fdcda1bc962c","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"bd7b13d5-16fa-3a38-ba07-7dfc9314c884","physicalStatus":"ON_LOAN","physicalSiteId":"NORD","physicalLocationId":null,"projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["LOAN_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"dd53f851-3be7-3b78-b6d8-ef0d89778083","status":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","sourceEventIds":["b48023ab-8226-383a-8619-c45bfea529a3","307454c0-0308-3c66-a37a-99f81324fab9"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | b7755561-5289-38af-847d-42e09e0b7711 | 2026-06-02T03:18:30Z | 2026-06-02T03:18:40Z | `{"operationId":"6f9dc141-80b0-3fdd-9578-c6a063f0ec0c","correlationId":"060bc2eb-613d-3247-996f-07f77be06fe8","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"371f5abb-d077-37c0-ae27-550ba24c5633"}` |
| IntegrationProcessed | f19b9ddd-8690-308a-b63a-80e9e51d9b4b | 2026-06-03T03:18:30Z | 2026-06-03T03:18:40Z | `{"originalEventId":"b7755561-5289-38af-847d-42e09e0b7711","correlationId":"060bc2eb-613d-3247-996f-07f77be06fe8","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | e73ccc4f-83c4-3035-bf7a-71eb5ac86631 | 2026-08-01T03:18:30Z | 2026-08-01T03:18:40Z | `{"operationId":"53701fb5-8ed0-3c60-b11c-335ca437daf7","correlationId":"16dbadc6-3e7a-36b4-adb8-6d410da2d361","siteId":"CENTRE","locationId":null,"loanId":"542df90e-eee0-32ba-886f-cbeb89828bcd"}` |
| IntegrationProcessed | aa07d3a1-7635-3e56-960e-b4174365dc99 | 2026-08-01T03:19:30Z | 2026-08-01T03:19:40Z | `{"originalEventId":"e73ccc4f-83c4-3035-bf7a-71eb5ac86631","correlationId":"16dbadc6-3e7a-36b4-adb8-6d410da2d361","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 93c5d670-c2de-367e-8b28-0538327a71c1 | 2026-08-21T03:18:30Z | 2026-08-21T03:18:40Z | `{"operationId":"060e8179-8960-364f-8182-38e63d3fc085","correlationId":"d6240c02-8cd5-3d30-9e7e-648ed02692c9","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"542df90e-eee0-32ba-886f-cbeb89828bcd"}` |
| ReturnReceived | dbe31470-2510-3084-b719-8aa8508fbe17 | 2026-08-21T03:19:00Z | 2026-08-21T03:19:10Z | `{"operationId":"fc09f8e3-59a7-38fc-8761-b8a68a56d0a9","correlationId":"22c0aa23-a57e-3904-aa03-02e232b41390","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"542df90e-eee0-32ba-886f-cbeb89828bcd"}` |
| IntegrationProcessed | 87628f12-6e1b-3aeb-b215-24dfe53ba56d | 2026-08-22T03:18:30Z | 2026-08-22T03:18:40Z | `{"originalEventId":"dbe31470-2510-3084-b719-8aa8508fbe17","correlationId":"22c0aa23-a57e-3904-aa03-02e232b41390","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| TransferDispatched | e1c55f3e-6faa-31bb-ae0a-160ba84447d0 | 2026-08-31T03:18:30Z | 2026-08-31T03:18:40Z | `{"operationId":"b00f463c-024b-396d-8cf5-6804f3aedf58","correlationId":"79922dff-fd98-32ef-b3c1-ec17a6f1ce6d","siteId":"CENTRE","locationId":null,"transferId":"24359639-05e5-385c-bd74-6c79b4c07e40","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | d1f285f3-8add-36a7-9135-e8a601c15e2c | 2026-08-31T03:19:30Z | 2026-08-31T03:19:40Z | `{"originalEventId":"e1c55f3e-6faa-31bb-ae0a-160ba84447d0","correlationId":"79922dff-fd98-32ef-b3c1-ec17a6f1ce6d","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| TransferReceived | b48023ab-8226-383a-8619-c45bfea529a3 | 2026-09-01T03:18:30Z | 2026-09-01T03:18:40Z | `{"operationId":"d178a63a-9be5-39fc-9ee8-6a5cddadd674","correlationId":"a3441ee7-811d-3525-ba60-d6ecf5ff0bb4","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","transferId":"24359639-05e5-385c-bd74-6c79b4c07e40","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | 307454c0-0308-3c66-a37a-99f81324fab9 | 2026-09-02T03:18:30Z | 2026-09-02T03:18:40Z | `{"originalEventId":"b48023ab-8226-383a-8619-c45bfea529a3","correlationId":"a3441ee7-811d-3525-ba60-d6ecf5ff0bb4","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | bd7b13d5-16fa-3a38-ba07-7dfc9314c884 | 2026-09-27T09:25:43Z | 2026-09-27T09:25:53Z | `{"operationId":"4ae66b4d-9c15-3ce5-88a2-ccc18effb8ba","correlationId":"8570135e-95e3-34c8-988d-cfdcb9b093ab","siteId":"NORD","locationId":null,"loanId":"32893800-8ad0-3c31-8e96-94c9bad52b91"}` |
| IntegrationProcessed | 1d3ad60d-7d73-3dec-8bfc-8282a16a203f | 2026-09-27T09:26:13Z | 2026-09-27T09:26:23Z | `{"originalEventId":"bd7b13d5-16fa-3a38-ba07-7dfc9314c884","correlationId":"8570135e-95e3-34c8-988d-cfdcb9b093ab","outcome":"FAILED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**observations**

**sourceAvailability**
- `{"copyId":"dd53f851-3be7-3b78-b6d8-ef0d89778083","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"dd53f851-3be7-3b78-b6d8-ef0d89778083","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"dd53f851-3be7-3b78-b6d8-ef0d89778083","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"dd53f851-3be7-3b78-b6d8-ef0d89778083","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"dd53f851-3be7-3b78-b6d8-ef0d89778083","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"dd53f851-3be7-3b78-b6d8-ef0d89778083","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"dd53f851-3be7-3b78-b6d8-ef0d89778083","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"dd53f851-3be7-3b78-b6d8-ef0d89778083"},{"kind":"sourceEvent","id":"b7755561-5289-38af-847d-42e09e0b7711"},{"kind":"sourceEvent","id":"f19b9ddd-8690-308a-b63a-80e9e51d9b4b"},{"kind":"sourceEvent","id":"e73ccc4f-83c4-3035-bf7a-71eb5ac86631"},{"kind":"sourceEvent","id":"aa07d3a1-7635-3e56-960e-b4174365dc99"},{"kind":"sourceEvent","id":"93c5d670-c2de-367e-8b28-0538327a71c1"},{"kind":"sourceEvent","id":"dbe31470-2510-3084-b719-8aa8508fbe17"},{"kind":"sourceEvent","id":"87628f12-6e1b-3aeb-b215-24dfe53ba56d"},{"kind":"sourceEvent","id":"e1c55f3e-6faa-31bb-ae0a-160ba84447d0"},{"kind":"sourceEvent","id":"d1f285f3-8add-36a7-9135-e8a601c15e2c"},{"kind":"sourceEvent","id":"b48023ab-8226-383a-8619-c45bfea529a3"},{"kind":"sourceEvent","id":"307454c0-0308-3c66-a37a-99f81324fab9"},{"kind":"sourceEvent","id":"bd7b13d5-16fa-3a38-ba07-7dfc9314c884"},{"kind":"sourceEvent","id":"1d3ad60d-7d73-3dec-8bfc-8282a16a203f"},{"kind":"sourceAvailability","id":"dd53f851-3be7-3b78-b6d8-ef0d89778083/LOANS"},{"kind":"sourceAvailability","id":"dd53f851-3be7-3b78-b6d8-ef0d89778083/RETURNS"},{"kind":"sourceAvailability","id":"dd53f851-3be7-3b78-b6d8-ef0d89778083/RECEIPTS"},{"kind":"sourceAvailability","id":"dd53f851-3be7-3b78-b6d8-ef0d89778083/TRANSFERS"},{"kind":"sourceAvailability","id":"dd53f851-3be7-3b78-b6d8-ef0d89778083/REPAIRS"},{"kind":"sourceAvailability","id":"dd53f851-3be7-3b78-b6d8-ef0d89778083/INTEGRATIONS"},{"kind":"sourceAvailability","id":"dd53f851-3be7-3b78-b6d8-ef0d89778083/INVENTORY"},{"kind":"inventoryScope","id":"07c957d2-b086-3b6b-9915-d21f1d5906cf"}]`

- Session : `{"scopeId":"07c957d2-b086-3b6b-9915-d21f1d5906cf","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 85d6bab6-88e5-3808-9fc7-36f054e8c5ed

- Exemplaire : `e34c86ad-f0fd-34dc-a4f9-4e1cd3ce01cc`
- Famille privée : TRANSFERT_INCOHERENT ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"bf542d95-5027-36ca-acee-3adb392ed2da","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"5906a61b-ae0f-38cb-bd91-8655a5ab59b8","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"e34c86ad-f0fd-34dc-a4f9-4e1cd3ce01cc","status":"RECEIVING","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","sourceEventIds":["5906a61b-ae0f-38cb-bd91-8655a5ab59b8","b72db27e-002d-3405-acd8-f2ac3a46d9a0"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | d25cb494-194c-35b1-aeaf-5b4c2c1efe8e | 2026-06-02T10:31:58Z | 2026-06-02T10:32:08Z | `{"operationId":"bf28942c-dab6-3de4-857c-a699e4b488bd","correlationId":"8475a911-2740-3548-9eed-e13e5188c751","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"e69d8366-6eee-322f-a3d4-07da3eab456a"}` |
| IntegrationProcessed | 4a8bcd60-3cfa-3af0-b8ba-abc56f56ebb0 | 2026-06-03T10:31:58Z | 2026-06-03T10:32:08Z | `{"originalEventId":"d25cb494-194c-35b1-aeaf-5b4c2c1efe8e","correlationId":"8475a911-2740-3548-9eed-e13e5188c751","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | e11ae379-7d66-3822-938d-c7f0a571e5b3 | 2026-08-01T10:31:58Z | 2026-08-01T10:32:08Z | `{"operationId":"7a317497-7f07-363e-a84e-586d84e5fe31","correlationId":"799d0fe2-94ec-320e-9e0c-691ffe3bbb62","siteId":"SUD","locationId":null,"loanId":"0d6050bb-2b7c-3ddb-9f05-1342acdebda2"}` |
| IntegrationProcessed | 1c8a8226-0a85-3f0b-8e30-efc37998f5c9 | 2026-08-01T10:32:58Z | 2026-08-01T10:33:08Z | `{"originalEventId":"e11ae379-7d66-3822-938d-c7f0a571e5b3","correlationId":"799d0fe2-94ec-320e-9e0c-691ffe3bbb62","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | 09c76de7-d24e-3463-9948-708c160fbb71 | 2026-08-21T10:31:58Z | 2026-08-21T10:32:08Z | `{"operationId":"9d7d42d0-0a72-3c62-b398-612b11cca337","correlationId":"1733d0a4-bc92-3ca5-b99a-f78fe9b804af","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"0d6050bb-2b7c-3ddb-9f05-1342acdebda2"}` |
| ReturnReceived | d8477e8f-f0dc-332e-a9df-70e34874fa38 | 2026-08-21T10:32:28Z | 2026-08-21T10:32:38Z | `{"operationId":"4881c66b-342e-34eb-8570-36d7c0089f6f","correlationId":"d7e412ac-5621-329e-8057-47807a73995a","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"0d6050bb-2b7c-3ddb-9f05-1342acdebda2"}` |
| IntegrationProcessed | 3861e704-e204-368b-be1a-c70cb35154b5 | 2026-08-22T10:31:58Z | 2026-08-22T10:32:08Z | `{"originalEventId":"d8477e8f-f0dc-332e-a9df-70e34874fa38","correlationId":"d7e412ac-5621-329e-8057-47807a73995a","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| TransferDispatched | 7abc2627-58ff-3437-9c6a-eb59de8bc737 | 2026-09-26T10:29:59Z | 2026-09-26T10:30:09Z | `{"operationId":"ddee339a-7261-31a0-8920-fdd13d30ea9e","correlationId":"091ed1dc-fad6-3cf6-b4b1-1bc713a1a7d6","siteId":"SUD","locationId":null,"transferId":"cbcbf4f4-84fe-3345-ac78-61b9550c3408","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| IntegrationProcessed | 987b05af-0d04-3453-bc63-b9d38a8b7b1d | 2026-09-26T10:30:59Z | 2026-09-26T10:31:09Z | `{"originalEventId":"7abc2627-58ff-3437-9c6a-eb59de8bc737","correlationId":"091ed1dc-fad6-3cf6-b4b1-1bc713a1a7d6","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"SUD","locationId":null}` |
| TransferReceived | 5906a61b-ae0f-38cb-bd91-8655a5ab59b8 | 2026-09-27T10:29:59Z | 2026-09-27T10:30:09Z | `{"operationId":"a9041256-112d-36ac-b4a7-f3c1e453637d","correlationId":"57cac4f3-ec22-36d5-b2fc-199fe29b4dd9","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","transferId":"cbcbf4f4-84fe-3345-ac78-61b9550c3408","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| IntegrationProcessed | 4b75f161-7d95-3d24-b5c3-4a5c01aa904c | 2026-09-27T10:30:29Z | 2026-09-27T10:30:39Z | `{"originalEventId":"5906a61b-ae0f-38cb-bd91-8655a5ab59b8","correlationId":"57cac4f3-ec22-36d5-b2fc-199fe29b4dd9","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| IntegrationProcessed | b72db27e-002d-3405-acd8-f2ac3a46d9a0 | 2026-09-27T10:31:29Z | 2026-09-27T10:31:39Z | `{"originalEventId":"5906a61b-ae0f-38cb-bd91-8655a5ab59b8","correlationId":"57cac4f3-ec22-36d5-b2fc-199fe29b4dd9","outcome":"APPLIED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca"}` |

**observations**
- `{"observationId":"c712d509-aeed-38d7-8038-fd84bce16422","copyId":"e34c86ad-f0fd-34dc-a4f9-4e1cd3ce01cc","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","scopeId":"bea0c19c-0c1e-3d31-ab4d-dda10370bd37","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`
- `{"observationId":"3401ad78-d866-3ef8-81eb-5a1d2969360a","copyId":"e34c86ad-f0fd-34dc-a4f9-4e1cd3ce01cc","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","scopeId":"537bf057-61c6-3986-ba87-70403b519713","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**sourceAvailability**
- `{"copyId":"e34c86ad-f0fd-34dc-a4f9-4e1cd3ce01cc","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e34c86ad-f0fd-34dc-a4f9-4e1cd3ce01cc","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e34c86ad-f0fd-34dc-a4f9-4e1cd3ce01cc","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e34c86ad-f0fd-34dc-a4f9-4e1cd3ce01cc","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e34c86ad-f0fd-34dc-a4f9-4e1cd3ce01cc","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e34c86ad-f0fd-34dc-a4f9-4e1cd3ce01cc","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"e34c86ad-f0fd-34dc-a4f9-4e1cd3ce01cc","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"e34c86ad-f0fd-34dc-a4f9-4e1cd3ce01cc"},{"kind":"sourceEvent","id":"d25cb494-194c-35b1-aeaf-5b4c2c1efe8e"},{"kind":"sourceEvent","id":"4a8bcd60-3cfa-3af0-b8ba-abc56f56ebb0"},{"kind":"sourceEvent","id":"e11ae379-7d66-3822-938d-c7f0a571e5b3"},{"kind":"sourceEvent","id":"1c8a8226-0a85-3f0b-8e30-efc37998f5c9"},{"kind":"sourceEvent","id":"09c76de7-d24e-3463-9948-708c160fbb71"},{"kind":"sourceEvent","id":"d8477e8f-f0dc-332e-a9df-70e34874fa38"},{"kind":"sourceEvent","id":"3861e704-e204-368b-be1a-c70cb35154b5"},{"kind":"sourceEvent","id":"7abc2627-58ff-3437-9c6a-eb59de8bc737"},{"kind":"sourceEvent","id":"987b05af-0d04-3453-bc63-b9d38a8b7b1d"},{"kind":"sourceEvent","id":"5906a61b-ae0f-38cb-bd91-8655a5ab59b8"},{"kind":"sourceEvent","id":"4b75f161-7d95-3d24-b5c3-4a5c01aa904c"},{"kind":"sourceEvent","id":"b72db27e-002d-3405-acd8-f2ac3a46d9a0"},{"kind":"observation","id":"c712d509-aeed-38d7-8038-fd84bce16422"},{"kind":"inventoryScope","id":"bea0c19c-0c1e-3d31-ab4d-dda10370bd37"},{"kind":"observation","id":"3401ad78-d866-3ef8-81eb-5a1d2969360a"},{"kind":"inventoryScope","id":"537bf057-61c6-3986-ba87-70403b519713"},{"kind":"sourceAvailability","id":"e34c86ad-f0fd-34dc-a4f9-4e1cd3ce01cc/LOANS"},{"kind":"sourceAvailability","id":"e34c86ad-f0fd-34dc-a4f9-4e1cd3ce01cc/RETURNS"},{"kind":"sourceAvailability","id":"e34c86ad-f0fd-34dc-a4f9-4e1cd3ce01cc/RECEIPTS"},{"kind":"sourceAvailability","id":"e34c86ad-f0fd-34dc-a4f9-4e1cd3ce01cc/TRANSFERS"},{"kind":"sourceAvailability","id":"e34c86ad-f0fd-34dc-a4f9-4e1cd3ce01cc/REPAIRS"},{"kind":"sourceAvailability","id":"e34c86ad-f0fd-34dc-a4f9-4e1cd3ce01cc/INTEGRATIONS"},{"kind":"sourceAvailability","id":"e34c86ad-f0fd-34dc-a4f9-4e1cd3ce01cc/INVENTORY"},{"kind":"inventoryScope","id":"27d49d3d-f104-306e-b8d1-ada0e3973acb"},{"kind":"inventoryScope","id":"59116f6f-fc86-3206-80a1-873afe081073"}]`

- Session : `{"scopeId":"bea0c19c-0c1e-3d31-ab4d-dda10370bd37","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"537bf057-61c6-3986-ba87-70403b519713","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"27d49d3d-f104-306e-b8d1-ada0e3973acb","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"59116f6f-fc86-3206-80a1-873afe081073","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 8909889f-7cfc-3251-ab6d-7f14b4186fc8

- Exemplaire : `97f75b07-395c-3f0e-bc78-add8b90cbe50`
- Famille privée : RETOUR_NON_PROJETE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"ed0f0eeb-0a94-3529-9330-1f6fe5661494","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"8c1fd975-0875-3f57-8509-8bd206696edb","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"97f75b07-395c-3f0e-bc78-add8b90cbe50","status":"RECEIVING","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","sourceEventIds":["8c1fd975-0875-3f57-8509-8bd206696edb","2775940d-219d-3b06-8c39-60368336fce7"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 41c52bae-55ca-3b76-854d-f0e2ef358ce1 | 2026-06-01T16:40:28Z | 2026-06-01T16:40:38Z | `{"operationId":"c04c6ed7-aca6-3043-bb29-f44bbc644278","correlationId":"df51aea8-2f8a-3f3f-b745-a9dfef0b703c","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"4bd55f88-63fe-3ae5-a318-5ec59d17518b"}` |
| IntegrationProcessed | cb4e3e8c-affc-3214-b8d7-46a6f3daa178 | 2026-06-02T16:40:28Z | 2026-06-02T16:40:38Z | `{"originalEventId":"41c52bae-55ca-3b76-854d-f0e2ef358ce1","correlationId":"df51aea8-2f8a-3f3f-b745-a9dfef0b703c","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 017b2a66-b696-3339-8bf7-e0b86afc5623 | 2026-07-31T16:40:28Z | 2026-07-31T16:40:38Z | `{"operationId":"a2260d06-6ae2-3a56-9545-71978ece4ed3","correlationId":"75715ba4-a6a8-3871-ba7c-e39843f78a0c","siteId":"CENTRE","locationId":null,"loanId":"ef68417d-8bba-37f4-9f8a-2cc05c310049"}` |
| IntegrationProcessed | 1ee41443-660c-358c-890b-c59bd804478f | 2026-07-31T16:41:28Z | 2026-07-31T16:41:38Z | `{"originalEventId":"017b2a66-b696-3339-8bf7-e0b86afc5623","correlationId":"75715ba4-a6a8-3871-ba7c-e39843f78a0c","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 33864cf8-317f-3ef0-8167-a8bc4f028ed1 | 2026-08-20T16:40:28Z | 2026-08-20T16:40:38Z | `{"operationId":"238f2bfa-486f-35d1-a37b-0e9926ca7ff8","correlationId":"08534cd1-53ca-3701-af0e-1a2a6908102d","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"ef68417d-8bba-37f4-9f8a-2cc05c310049"}` |
| ReturnReceived | 7cded306-e334-3da5-b91f-30782ce2f46b | 2026-08-20T16:40:58Z | 2026-08-20T16:41:08Z | `{"operationId":"4b7ef494-9425-3f27-944e-f85042e3a77f","correlationId":"38ebfb06-622f-31a6-8efb-9c1192415555","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"ef68417d-8bba-37f4-9f8a-2cc05c310049"}` |
| IntegrationProcessed | e8c2af6e-799a-3c16-b01a-551a5960f7f9 | 2026-08-21T16:40:28Z | 2026-08-21T16:40:38Z | `{"originalEventId":"7cded306-e334-3da5-b91f-30782ce2f46b","correlationId":"38ebfb06-622f-31a6-8efb-9c1192415555","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 34fc6c9a-d300-365b-9429-026d767640dd | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"2cbd4694-ddd7-38cc-9369-d77932609a53","correlationId":"f5867f7a-e5f4-3bd5-85ce-8dba27fe7f60","siteId":"CENTRE","locationId":null,"loanId":"ca4eba86-d51e-3a9a-85b8-d2507bdce73a"}` |
| IntegrationProcessed | 511e91b2-761c-3278-922d-e2344f22143c | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"34fc6c9a-d300-365b-9429-026d767640dd","correlationId":"f5867f7a-e5f4-3bd5-85ce-8dba27fe7f60","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 3ab9d3ca-a571-3dec-acbc-995032abd8c0 | 2026-09-27T07:13:54Z | 2026-09-27T07:14:04Z | `{"operationId":"c6a6666a-c4b1-3acc-8414-d7412bf9d33a","correlationId":"e1daf5d3-5dca-3da5-b396-f383df2a2418","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"ca4eba86-d51e-3a9a-85b8-d2507bdce73a"}` |
| ReturnReceived | 8c1fd975-0875-3f57-8509-8bd206696edb | 2026-09-27T07:14:24Z | 2026-09-27T07:14:34Z | `{"operationId":"75baf9af-5b98-320f-9bb7-460d9e2db6e7","correlationId":"8d4fb822-4cf5-36e1-b177-57896d0028a8","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"ca4eba86-d51e-3a9a-85b8-d2507bdce73a"}` |
| IntegrationProcessed | a5efe2f1-c922-3b2a-bd25-808cdbcd6fca | 2026-09-27T07:14:54Z | 2026-09-27T07:15:04Z | `{"originalEventId":"8c1fd975-0875-3f57-8509-8bd206696edb","correlationId":"8d4fb822-4cf5-36e1-b177-57896d0028a8","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| IntegrationProcessed | 2775940d-219d-3b06-8c39-60368336fce7 | 2026-09-27T07:15:54Z | 2026-09-27T07:16:04Z | `{"originalEventId":"8c1fd975-0875-3f57-8509-8bd206696edb","correlationId":"8d4fb822-4cf5-36e1-b177-57896d0028a8","outcome":"APPLIED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19"}` |

**observations**
- `{"observationId":"aa468cc6-2153-3460-b4dc-647b1c64a30e","copyId":"97f75b07-395c-3f0e-bc78-add8b90cbe50","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","scopeId":"461e91a9-f002-3ffc-b8a9-1f0914558f2c","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`
- `{"observationId":"586f1e92-b6b1-3147-8167-30a93be4b0f5","copyId":"97f75b07-395c-3f0e-bc78-add8b90cbe50","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","scopeId":"a8e04495-fff8-39c4-ae7d-dee65b43d714","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"97f75b07-395c-3f0e-bc78-add8b90cbe50","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"97f75b07-395c-3f0e-bc78-add8b90cbe50","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"97f75b07-395c-3f0e-bc78-add8b90cbe50","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"97f75b07-395c-3f0e-bc78-add8b90cbe50","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"97f75b07-395c-3f0e-bc78-add8b90cbe50","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"97f75b07-395c-3f0e-bc78-add8b90cbe50","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"97f75b07-395c-3f0e-bc78-add8b90cbe50","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"97f75b07-395c-3f0e-bc78-add8b90cbe50"},{"kind":"sourceEvent","id":"41c52bae-55ca-3b76-854d-f0e2ef358ce1"},{"kind":"sourceEvent","id":"cb4e3e8c-affc-3214-b8d7-46a6f3daa178"},{"kind":"sourceEvent","id":"017b2a66-b696-3339-8bf7-e0b86afc5623"},{"kind":"sourceEvent","id":"1ee41443-660c-358c-890b-c59bd804478f"},{"kind":"sourceEvent","id":"33864cf8-317f-3ef0-8167-a8bc4f028ed1"},{"kind":"sourceEvent","id":"7cded306-e334-3da5-b91f-30782ce2f46b"},{"kind":"sourceEvent","id":"e8c2af6e-799a-3c16-b01a-551a5960f7f9"},{"kind":"sourceEvent","id":"34fc6c9a-d300-365b-9429-026d767640dd"},{"kind":"sourceEvent","id":"511e91b2-761c-3278-922d-e2344f22143c"},{"kind":"sourceEvent","id":"3ab9d3ca-a571-3dec-acbc-995032abd8c0"},{"kind":"sourceEvent","id":"8c1fd975-0875-3f57-8509-8bd206696edb"},{"kind":"sourceEvent","id":"a5efe2f1-c922-3b2a-bd25-808cdbcd6fca"},{"kind":"sourceEvent","id":"2775940d-219d-3b06-8c39-60368336fce7"},{"kind":"observation","id":"586f1e92-b6b1-3147-8167-30a93be4b0f5"},{"kind":"inventoryScope","id":"a8e04495-fff8-39c4-ae7d-dee65b43d714"},{"kind":"observation","id":"aa468cc6-2153-3460-b4dc-647b1c64a30e"},{"kind":"inventoryScope","id":"461e91a9-f002-3ffc-b8a9-1f0914558f2c"},{"kind":"sourceAvailability","id":"97f75b07-395c-3f0e-bc78-add8b90cbe50/LOANS"},{"kind":"sourceAvailability","id":"97f75b07-395c-3f0e-bc78-add8b90cbe50/RETURNS"},{"kind":"sourceAvailability","id":"97f75b07-395c-3f0e-bc78-add8b90cbe50/RECEIPTS"},{"kind":"sourceAvailability","id":"97f75b07-395c-3f0e-bc78-add8b90cbe50/TRANSFERS"},{"kind":"sourceAvailability","id":"97f75b07-395c-3f0e-bc78-add8b90cbe50/REPAIRS"},{"kind":"sourceAvailability","id":"97f75b07-395c-3f0e-bc78-add8b90cbe50/INTEGRATIONS"},{"kind":"sourceAvailability","id":"97f75b07-395c-3f0e-bc78-add8b90cbe50/INVENTORY"},{"kind":"inventoryScope","id":"edbecaba-d35c-3b09-998f-113009bca25d"}]`

- Session : `{"scopeId":"a8e04495-fff8-39c4-ae7d-dee65b43d714","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"461e91a9-f002-3ffc-b8a9-1f0914558f2c","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"edbecaba-d35c-3b09-998f-113009bca25d","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 8b52bfd1-0f47-385c-99d9-138754d0b652

- Exemplaire : `6a6d0d9c-144c-314b-a398-c114200987c4`
- Famille privée : RETARD_INTEGRATION ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"82ee2a3e-6c26-3910-8bde-99dda1d7f7e2","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["QUEUE_PENDING"],"hiddenSourceEvents":[{"sourceEventId":"c86e04d4-bbf4-3d59-be3c-598ff411414a","copyId":"6a6d0d9c-144c-314b-a398-c114200987c4","eventType":"IntegrationProcessed","occurredAt":"2026-09-30T11:53:30Z","recordedAt":"2026-09-30T11:53:40Z","payload":{"originalEventId":"83ff06b5-de0f-3e35-973d-910a452f11a6","correlationId":"b31558eb-55d4-3595-b2d1-9244defa68a0","outcome":"QUEUED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1}}],"mechanism":"Événement métier en attente normale dans la fenêtre contractuelle de 900 secondes.","businessEventId":"83ff06b5-de0f-3e35-973d-910a452f11a6","physicalStatus":"ON_LOAN","physicalSiteId":"SUD","physicalLocationId":null,"projectionPerturbation":"QUEUED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État projeté : `{"copyId":"6a6d0d9c-144c-314b-a398-c114200987c4","status":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","sourceEventIds":["bf5eab3a-e30e-39ca-93dd-ba5eda6d2c58","02cddf38-cba2-388a-aee0-e88f023ffc72"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 00cd293e-779d-3ca3-9c90-74393d631743 | 2026-06-02T04:39:47Z | 2026-06-02T04:39:57Z | `{"operationId":"8b8493ba-3017-38b5-8992-5ace3e737c85","correlationId":"447fcd3c-0b26-3bd7-ada6-de114073458e","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"f87c3033-8a9c-3090-b963-b7d317570047"}` |
| IntegrationProcessed | 2de296a3-05f3-39dc-b41a-bd1b02a5e212 | 2026-06-03T04:39:47Z | 2026-06-03T04:39:57Z | `{"originalEventId":"00cd293e-779d-3ca3-9c90-74393d631743","correlationId":"447fcd3c-0b26-3bd7-ada6-de114073458e","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | f0b479b2-6e5f-3b5a-a099-f3d85bbf5328 | 2026-08-01T04:39:47Z | 2026-08-01T04:39:57Z | `{"operationId":"35b9db91-aa4b-3e1b-94d8-0ad184f20437","correlationId":"fc51190b-872e-3eb5-bf23-fa7c769ad0b6","siteId":"SUD","locationId":null,"loanId":"e9c4e82a-dbc8-322b-8eec-558d61782ebd"}` |
| IntegrationProcessed | 62a6e538-5e0f-3108-bc00-bcedeb12d5ea | 2026-08-01T04:40:47Z | 2026-08-01T04:40:57Z | `{"originalEventId":"f0b479b2-6e5f-3b5a-a099-f3d85bbf5328","correlationId":"fc51190b-872e-3eb5-bf23-fa7c769ad0b6","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | a6f70938-3cdd-3305-b369-c5f5bfae5c13 | 2026-08-21T04:39:47Z | 2026-08-21T04:39:57Z | `{"operationId":"24dd27b6-b575-3dfe-9816-0b452063c2d6","correlationId":"9b93b713-c80a-3021-88d1-6e5ed37abcce","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"e9c4e82a-dbc8-322b-8eec-558d61782ebd"}` |
| ReturnReceived | bf5eab3a-e30e-39ca-93dd-ba5eda6d2c58 | 2026-08-21T04:40:17Z | 2026-08-21T04:40:27Z | `{"operationId":"26c6cc21-fa59-3a1b-a359-406eb27b6d75","correlationId":"52b5250a-57a0-3a80-b5ca-fda33c24e254","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"e9c4e82a-dbc8-322b-8eec-558d61782ebd"}` |
| IntegrationProcessed | 02cddf38-cba2-388a-aee0-e88f023ffc72 | 2026-08-22T04:39:47Z | 2026-08-22T04:39:57Z | `{"originalEventId":"bf5eab3a-e30e-39ca-93dd-ba5eda6d2c58","correlationId":"52b5250a-57a0-3a80-b5ca-fda33c24e254","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | 83ff06b5-de0f-3e35-973d-910a452f11a6 | 2026-09-30T11:53:00Z | 2026-09-30T11:53:10Z | `{"operationId":"0dd7a9f0-ed50-3fe0-8f2f-027635f8703a","correlationId":"b31558eb-55d4-3595-b2d1-9244defa68a0","siteId":"SUD","locationId":null,"loanId":"5ccd9546-cc32-35c4-98f1-59fcd76e1ec3"}` |

**observations**

**sourceAvailability**
- `{"copyId":"6a6d0d9c-144c-314b-a398-c114200987c4","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"6a6d0d9c-144c-314b-a398-c114200987c4","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6a6d0d9c-144c-314b-a398-c114200987c4","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6a6d0d9c-144c-314b-a398-c114200987c4","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6a6d0d9c-144c-314b-a398-c114200987c4","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6a6d0d9c-144c-314b-a398-c114200987c4","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6a6d0d9c-144c-314b-a398-c114200987c4","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"6a6d0d9c-144c-314b-a398-c114200987c4"},{"kind":"sourceEvent","id":"00cd293e-779d-3ca3-9c90-74393d631743"},{"kind":"sourceEvent","id":"2de296a3-05f3-39dc-b41a-bd1b02a5e212"},{"kind":"sourceEvent","id":"f0b479b2-6e5f-3b5a-a099-f3d85bbf5328"},{"kind":"sourceEvent","id":"62a6e538-5e0f-3108-bc00-bcedeb12d5ea"},{"kind":"sourceEvent","id":"a6f70938-3cdd-3305-b369-c5f5bfae5c13"},{"kind":"sourceEvent","id":"bf5eab3a-e30e-39ca-93dd-ba5eda6d2c58"},{"kind":"sourceEvent","id":"02cddf38-cba2-388a-aee0-e88f023ffc72"},{"kind":"sourceEvent","id":"83ff06b5-de0f-3e35-973d-910a452f11a6"},{"kind":"sourceAvailability","id":"6a6d0d9c-144c-314b-a398-c114200987c4/LOANS"},{"kind":"sourceAvailability","id":"6a6d0d9c-144c-314b-a398-c114200987c4/RETURNS"},{"kind":"sourceAvailability","id":"6a6d0d9c-144c-314b-a398-c114200987c4/RECEIPTS"},{"kind":"sourceAvailability","id":"6a6d0d9c-144c-314b-a398-c114200987c4/TRANSFERS"},{"kind":"sourceAvailability","id":"6a6d0d9c-144c-314b-a398-c114200987c4/REPAIRS"},{"kind":"sourceAvailability","id":"6a6d0d9c-144c-314b-a398-c114200987c4/INTEGRATIONS"},{"kind":"sourceAvailability","id":"6a6d0d9c-144c-314b-a398-c114200987c4/INVENTORY"},{"kind":"inventoryScope","id":"294f8877-9130-3961-8435-48be641fa20c"},{"kind":"inventoryScope","id":"9d6c09df-1bb9-3fa2-927d-b44bcfe9e1be"},{"kind":"inventoryScope","id":"8ba7cc3d-833a-342a-bcaa-e1d23f56a4e3"}]`

- Session : `{"scopeId":"294f8877-9130-3961-8435-48be641fa20c","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9d6c09df-1bb9-3fa2-927d-b44bcfe9e1be","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"8ba7cc3d-833a-342a-bcaa-e1d23f56a4e3","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas 8fb5c7d8-ed76-3ed0-b52d-65f563b93691

- Exemplaire : `dc8fc0b7-aacd-344a-b9da-42fcc472fb55`
- Famille privée : RECEPTION_NON_INTEGREE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"8cc9a85a-e5cf-3d4a-9475-7a9004c22334","kind":"UNEXPECTED_PRESENCE","expectedValue":"NOT_REGISTERED","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"6a7b4775-ada7-3e0a-8752-8fd4b6f89671","copyId":"dc8fc0b7-aacd-344a-b9da-42fcc472fb55","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T10:43:22Z","recordedAt":"2026-09-27T10:43:32Z","payload":{"originalEventId":"bb8a6fa4-3dd9-3ead-a942-75351a1f2c6d","correlationId":"38cc6c6b-690b-32d0-9dfb-6edd1c6b0b7f","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"bb8a6fa4-3dd9-3ead-a942-75351a1f2c6d","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État projeté : `{"copyId":"dc8fc0b7-aacd-344a-b9da-42fcc472fb55","status":"NOT_REGISTERED","siteId":"NORD","locationId":null,"sourceEventIds":[]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | bb8a6fa4-3dd9-3ead-a942-75351a1f2c6d | 2026-09-27T10:42:52Z | 2026-09-27T10:43:02Z | `{"operationId":"9d2a6885-8bf7-34a4-9d37-7df380c26e48","correlationId":"38cc6c6b-690b-32d0-9dfb-6edd1c6b0b7f","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"9e72049a-8975-3b57-933f-c05014c0dd65"}` |

**observations**
- `{"observationId":"94fa7b9a-9dce-3d5f-baf5-576d5439de1d","copyId":"dc8fc0b7-aacd-344a-b9da-42fcc472fb55","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","scopeId":"04a59e9f-dc29-30f2-b2a5-3e841a8e43ff","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**sourceAvailability**
- `{"copyId":"dc8fc0b7-aacd-344a-b9da-42fcc472fb55","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"dc8fc0b7-aacd-344a-b9da-42fcc472fb55","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"dc8fc0b7-aacd-344a-b9da-42fcc472fb55","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"dc8fc0b7-aacd-344a-b9da-42fcc472fb55","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"dc8fc0b7-aacd-344a-b9da-42fcc472fb55","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"dc8fc0b7-aacd-344a-b9da-42fcc472fb55","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"dc8fc0b7-aacd-344a-b9da-42fcc472fb55","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"dc8fc0b7-aacd-344a-b9da-42fcc472fb55"},{"kind":"sourceEvent","id":"bb8a6fa4-3dd9-3ead-a942-75351a1f2c6d"},{"kind":"observation","id":"94fa7b9a-9dce-3d5f-baf5-576d5439de1d"},{"kind":"inventoryScope","id":"04a59e9f-dc29-30f2-b2a5-3e841a8e43ff"},{"kind":"sourceAvailability","id":"dc8fc0b7-aacd-344a-b9da-42fcc472fb55/LOANS"},{"kind":"sourceAvailability","id":"dc8fc0b7-aacd-344a-b9da-42fcc472fb55/RETURNS"},{"kind":"sourceAvailability","id":"dc8fc0b7-aacd-344a-b9da-42fcc472fb55/RECEIPTS"},{"kind":"sourceAvailability","id":"dc8fc0b7-aacd-344a-b9da-42fcc472fb55/TRANSFERS"},{"kind":"sourceAvailability","id":"dc8fc0b7-aacd-344a-b9da-42fcc472fb55/REPAIRS"},{"kind":"sourceAvailability","id":"dc8fc0b7-aacd-344a-b9da-42fcc472fb55/INTEGRATIONS"},{"kind":"sourceAvailability","id":"dc8fc0b7-aacd-344a-b9da-42fcc472fb55/INVENTORY"}]`

- Session : `{"scopeId":"04a59e9f-dc29-30f2-b2a5-3e841a8e43ff","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 902dc2d6-64b8-3102-aa8d-578bcf4a51d8

- Exemplaire : `14e1d9cb-35db-36bd-b08d-d92956169fd0`
- Famille privée : TRANSFERT_INCOHERENT ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"6f06d5df-a125-367b-a184-99bd1b072afc","kind":"STATUS_MISMATCH","expectedValue":"IN_TRANSIT","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"00e835af-df12-3bc1-98fb-3310ed9a8418","copyId":"14e1d9cb-35db-36bd-b08d-d92956169fd0","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T09:22:33Z","recordedAt":"2026-09-27T09:22:43Z","payload":{"originalEventId":"d3d39108-3ccc-31f2-bc0d-5a7bbc4114da","correlationId":"e45180c0-5d3e-3126-8d0e-1ecf3302c89e","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"d3d39108-3ccc-31f2-bc0d-5a7bbc4114da","physicalStatus":"RECEIVING","physicalSiteId":"SUD","physicalLocationId":"8228c3af-c19d-30d4-95b5-30240d9818af","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État projeté : `{"copyId":"14e1d9cb-35db-36bd-b08d-d92956169fd0","status":"IN_TRANSIT","siteId":"NORD","locationId":null,"sourceEventIds":["e4001bdd-b2aa-33ef-a70e-c13523117a2b","45aced4e-2326-3cf1-88b1-817dbcee48ee"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 0bc7482e-36ad-3f3c-a0a4-74c9123ef24a | 2026-06-02T07:44:08Z | 2026-06-02T07:44:18Z | `{"operationId":"060ee99f-0e0c-3514-8385-391a1e03ee63","correlationId":"33c2ea88-58ff-3927-bfa0-711260cea289","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"cf254fc5-d8ae-3480-9051-27c694c07727"}` |
| IntegrationProcessed | a5da443d-33e2-381d-9da3-ec5444c7257c | 2026-06-03T07:44:08Z | 2026-06-03T07:44:18Z | `{"originalEventId":"0bc7482e-36ad-3f3c-a0a4-74c9123ef24a","correlationId":"33c2ea88-58ff-3927-bfa0-711260cea289","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | a22dcc77-cb46-3c0f-9428-e05ccbdcfd10 | 2026-08-01T07:44:08Z | 2026-08-01T07:44:18Z | `{"operationId":"16f5ec32-844f-35a9-9a09-f9ab031c3c3e","correlationId":"6c653aef-f1c1-34e7-acd2-c52dc0ac12b0","siteId":"CENTRE","locationId":null,"loanId":"e7906cda-ff02-385b-bdf4-b701f6f58ade"}` |
| IntegrationProcessed | ff452324-e352-31a3-9020-b0607a42aaa1 | 2026-08-01T07:45:08Z | 2026-08-01T07:45:18Z | `{"originalEventId":"a22dcc77-cb46-3c0f-9428-e05ccbdcfd10","correlationId":"6c653aef-f1c1-34e7-acd2-c52dc0ac12b0","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 671bf031-b2a8-3f57-a9fd-0560d73539bc | 2026-08-21T07:44:08Z | 2026-08-21T07:44:18Z | `{"operationId":"01de435f-98eb-3a69-b67d-4edd8c648c8d","correlationId":"66e9c557-a86a-3d0c-b53b-dd15e554a49f","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"e7906cda-ff02-385b-bdf4-b701f6f58ade"}` |
| ReturnReceived | 76fef483-e68b-3fce-81d0-da442249e56c | 2026-08-21T07:44:38Z | 2026-08-21T07:44:48Z | `{"operationId":"31d06ea2-ccba-3e13-a717-c31de9ba1a28","correlationId":"ada7e185-8535-32a5-a16b-80c82df04dc0","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"e7906cda-ff02-385b-bdf4-b701f6f58ade"}` |
| IntegrationProcessed | c746aa82-f39c-3ae6-a8d1-7abc5b69ea35 | 2026-08-22T07:44:08Z | 2026-08-22T07:44:18Z | `{"originalEventId":"76fef483-e68b-3fce-81d0-da442249e56c","correlationId":"ada7e185-8535-32a5-a16b-80c82df04dc0","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| TransferDispatched | 1a07c6a7-e939-3524-abd3-97b75d93af1a | 2026-08-31T07:44:08Z | 2026-08-31T07:44:18Z | `{"operationId":"233bb104-e071-3fd7-abd8-a9b428e241e2","correlationId":"18f32007-33a4-3afd-811f-af8edb6f1a9d","siteId":"CENTRE","locationId":null,"transferId":"b054ce98-e418-3cf1-9e76-1b1f5b48ca4a","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | 53e43d66-af16-3b2a-b2aa-afc4ff2ad970 | 2026-08-31T07:45:08Z | 2026-08-31T07:45:18Z | `{"originalEventId":"1a07c6a7-e939-3524-abd3-97b75d93af1a","correlationId":"18f32007-33a4-3afd-811f-af8edb6f1a9d","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| TransferReceived | 4679824c-7691-38f5-b595-ab9d8c9e4447 | 2026-09-01T07:44:08Z | 2026-09-01T07:44:18Z | `{"operationId":"2c814314-ff1b-33f6-b995-00ec389b60b2","correlationId":"59cf1a83-a801-361c-b43c-8a29f4e0d882","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","transferId":"b054ce98-e418-3cf1-9e76-1b1f5b48ca4a","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | 156cb14d-bb3d-3e93-a049-b8c4695e165b | 2026-09-02T07:44:08Z | 2026-09-02T07:44:18Z | `{"originalEventId":"4679824c-7691-38f5-b595-ab9d8c9e4447","correlationId":"59cf1a83-a801-361c-b43c-8a29f4e0d882","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| TransferDispatched | e4001bdd-b2aa-33ef-a70e-c13523117a2b | 2026-09-26T09:22:03Z | 2026-09-26T09:22:13Z | `{"operationId":"16006e9f-a975-37c0-b3b3-e3622b115de0","correlationId":"c73e82ab-702f-36f1-bce4-e5cc8c2d804d","siteId":"NORD","locationId":null,"transferId":"b3a1a1da-c967-391b-ae85-e6ab6d25cffe","fromSiteId":"NORD","toSiteId":"SUD"}` |
| IntegrationProcessed | 45aced4e-2326-3cf1-88b1-817dbcee48ee | 2026-09-26T09:23:03Z | 2026-09-26T09:23:13Z | `{"originalEventId":"e4001bdd-b2aa-33ef-a70e-c13523117a2b","correlationId":"c73e82ab-702f-36f1-bce4-e5cc8c2d804d","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"NORD","locationId":null}` |
| TransferReceived | d3d39108-3ccc-31f2-bc0d-5a7bbc4114da | 2026-09-27T09:22:03Z | 2026-09-27T09:22:13Z | `{"operationId":"bf642e8e-a25c-34c0-94ae-0f459498215f","correlationId":"e45180c0-5d3e-3126-8d0e-1ecf3302c89e","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","transferId":"b3a1a1da-c967-391b-ae85-e6ab6d25cffe","fromSiteId":"NORD","toSiteId":"SUD"}` |

**observations**
- `{"observationId":"0901907d-8038-36d5-9d5b-89c8802de437","copyId":"14e1d9cb-35db-36bd-b08d-d92956169fd0","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","scopeId":"19855afc-11fb-34f4-af75-20d4c7d70d3d","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**sourceAvailability**
- `{"copyId":"14e1d9cb-35db-36bd-b08d-d92956169fd0","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"14e1d9cb-35db-36bd-b08d-d92956169fd0","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"14e1d9cb-35db-36bd-b08d-d92956169fd0","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"14e1d9cb-35db-36bd-b08d-d92956169fd0","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"14e1d9cb-35db-36bd-b08d-d92956169fd0","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"14e1d9cb-35db-36bd-b08d-d92956169fd0","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"14e1d9cb-35db-36bd-b08d-d92956169fd0","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"14e1d9cb-35db-36bd-b08d-d92956169fd0"},{"kind":"sourceEvent","id":"0bc7482e-36ad-3f3c-a0a4-74c9123ef24a"},{"kind":"sourceEvent","id":"a5da443d-33e2-381d-9da3-ec5444c7257c"},{"kind":"sourceEvent","id":"a22dcc77-cb46-3c0f-9428-e05ccbdcfd10"},{"kind":"sourceEvent","id":"ff452324-e352-31a3-9020-b0607a42aaa1"},{"kind":"sourceEvent","id":"671bf031-b2a8-3f57-a9fd-0560d73539bc"},{"kind":"sourceEvent","id":"76fef483-e68b-3fce-81d0-da442249e56c"},{"kind":"sourceEvent","id":"c746aa82-f39c-3ae6-a8d1-7abc5b69ea35"},{"kind":"sourceEvent","id":"1a07c6a7-e939-3524-abd3-97b75d93af1a"},{"kind":"sourceEvent","id":"53e43d66-af16-3b2a-b2aa-afc4ff2ad970"},{"kind":"sourceEvent","id":"4679824c-7691-38f5-b595-ab9d8c9e4447"},{"kind":"sourceEvent","id":"156cb14d-bb3d-3e93-a049-b8c4695e165b"},{"kind":"sourceEvent","id":"e4001bdd-b2aa-33ef-a70e-c13523117a2b"},{"kind":"sourceEvent","id":"45aced4e-2326-3cf1-88b1-817dbcee48ee"},{"kind":"sourceEvent","id":"d3d39108-3ccc-31f2-bc0d-5a7bbc4114da"},{"kind":"observation","id":"0901907d-8038-36d5-9d5b-89c8802de437"},{"kind":"inventoryScope","id":"19855afc-11fb-34f4-af75-20d4c7d70d3d"},{"kind":"sourceAvailability","id":"14e1d9cb-35db-36bd-b08d-d92956169fd0/LOANS"},{"kind":"sourceAvailability","id":"14e1d9cb-35db-36bd-b08d-d92956169fd0/RETURNS"},{"kind":"sourceAvailability","id":"14e1d9cb-35db-36bd-b08d-d92956169fd0/RECEIPTS"},{"kind":"sourceAvailability","id":"14e1d9cb-35db-36bd-b08d-d92956169fd0/TRANSFERS"},{"kind":"sourceAvailability","id":"14e1d9cb-35db-36bd-b08d-d92956169fd0/REPAIRS"},{"kind":"sourceAvailability","id":"14e1d9cb-35db-36bd-b08d-d92956169fd0/INTEGRATIONS"},{"kind":"sourceAvailability","id":"14e1d9cb-35db-36bd-b08d-d92956169fd0/INVENTORY"}]`

- Session : `{"scopeId":"19855afc-11fb-34f4-af75-20d4c7d70d3d","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 91876696-ad7d-3e39-93f9-ba2877688c3d

- Exemplaire : `906b5acb-3f24-3cf6-9c2f-0a2237d24b36`
- Famille privée : DEUX_CAUSES ; variante : POSITIVE
- Symptômes : `[{"symptomId":"906f8dc2-31ef-33b8-ac70-b3edf12ceb0e","kind":"STATUS_MISMATCH","expectedValue":"IN_REPAIR","observedValue":"ON_SHELF"},{"symptomId":"1db98325-54a3-3c42-8ab7-13a5a1e79d70","kind":"LOCATION_MISMATCH","expectedValue":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","observedValue":"b8600ac8-b5d3-3580-b3c5-71a711df1a50"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED","REPAIR_LOCATION_CHANGED"],"hiddenSourceEvents":[],"mechanism":"Réparation engagée puis déplacement distinct de l'atelier vers la réserve sans mise à jour de l'affectation de réparation.","businessEventId":"8a35d6ad-e9d2-345b-8d1e-f1fc393d6e29","physicalStatus":"IN_REPAIR","physicalSiteId":"CENTRE","physicalLocationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_NOT_PROJECTED","REPAIR_LOCATION_MISMATCH"],"explanation":"Deux écarts compatibles : réparation non projetée et scan valide hors de l'atelier indiqué par l'ordre de réparation.","forbiddenClaims":["Deux causes confirmées par un scan ambigu","Vol"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"906b5acb-3f24-3cf6-9c2f-0a2237d24b36","status":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","sourceEventIds":["d04cf640-72e4-3a7b-b060-a78118b90040","e06ff01f-1294-38d4-8049-edf0a9e2aae8"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | fb0563dc-eb72-3a60-af9e-5b260eb5ddd3 | 2026-06-01T20:53:34Z | 2026-06-01T20:53:44Z | `{"operationId":"472b26bc-d738-380f-9a40-b92ff5787a26","correlationId":"79c32efb-43ff-3297-a979-709de25efa24","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"c4fb4d05-a0db-30d8-bdad-4c73806cd236"}` |
| IntegrationProcessed | 53bed18e-f00f-31b6-9d98-7f4bb0feec43 | 2026-06-02T20:53:34Z | 2026-06-02T20:53:44Z | `{"originalEventId":"fb0563dc-eb72-3a60-af9e-5b260eb5ddd3","correlationId":"79c32efb-43ff-3297-a979-709de25efa24","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 3d7fd401-4d36-3d83-812a-8911413041b7 | 2026-07-31T20:53:34Z | 2026-07-31T20:53:44Z | `{"operationId":"11368710-497a-39be-8af2-1291c18ab49d","correlationId":"b30f9dca-4991-3fd0-9394-72ad009cdb8d","siteId":"CENTRE","locationId":null,"loanId":"356d9df9-2c9c-3a9a-89a1-411e91f967db"}` |
| IntegrationProcessed | 9d788ade-85c8-3b36-9343-5ee361d63efa | 2026-07-31T20:54:34Z | 2026-07-31T20:54:44Z | `{"originalEventId":"3d7fd401-4d36-3d83-812a-8911413041b7","correlationId":"b30f9dca-4991-3fd0-9394-72ad009cdb8d","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 0d10ba96-87bb-3ae8-8a5a-2db7657dd760 | 2026-08-20T20:53:34Z | 2026-08-20T20:53:44Z | `{"operationId":"70332af9-2914-3e0b-b8a7-6b368f10336a","correlationId":"924f23d2-e09e-3451-81a2-7f4e261f142e","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"356d9df9-2c9c-3a9a-89a1-411e91f967db"}` |
| ReturnReceived | d04cf640-72e4-3a7b-b060-a78118b90040 | 2026-08-20T20:54:04Z | 2026-08-20T20:54:14Z | `{"operationId":"0b041493-df72-301e-8f16-2c5ad040b921","correlationId":"0678e3a1-ad9b-3477-9689-dcc8412a882e","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"356d9df9-2c9c-3a9a-89a1-411e91f967db"}` |
| IntegrationProcessed | e06ff01f-1294-38d4-8049-edf0a9e2aae8 | 2026-08-21T20:53:34Z | 2026-08-21T20:53:44Z | `{"originalEventId":"d04cf640-72e4-3a7b-b060-a78118b90040","correlationId":"0678e3a1-ad9b-3477-9689-dcc8412a882e","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| RepairStarted | 8a35d6ad-e9d2-345b-8d1e-f1fc393d6e29 | 2026-09-27T06:51:46Z | 2026-09-27T06:51:56Z | `{"operationId":"29d2df14-5117-3705-b616-e1dd8261d28f","correlationId":"b575c993-4a9e-3649-a22e-5c64e41fcf45","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","repairId":"2d7750dd-8136-393c-bad5-9d7f2ff5df55"}` |
| IntegrationProcessed | b0980b4e-a8eb-3be9-91d3-b7f0577dd619 | 2026-09-27T06:52:16Z | 2026-09-27T06:52:26Z | `{"originalEventId":"8a35d6ad-e9d2-345b-8d1e-f1fc393d6e29","correlationId":"b575c993-4a9e-3649-a22e-5c64e41fcf45","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**observations**
- `{"observationId":"13df2fe3-e820-3fe5-818e-cc8278af2fae","copyId":"906b5acb-3f24-3cf6-9c2f-0a2237d24b36","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","scopeId":"4594bfd6-3ff2-33ed-b12d-6a9a24547c63","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"906b5acb-3f24-3cf6-9c2f-0a2237d24b36","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"906b5acb-3f24-3cf6-9c2f-0a2237d24b36","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"906b5acb-3f24-3cf6-9c2f-0a2237d24b36","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"906b5acb-3f24-3cf6-9c2f-0a2237d24b36","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"906b5acb-3f24-3cf6-9c2f-0a2237d24b36","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"906b5acb-3f24-3cf6-9c2f-0a2237d24b36","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"906b5acb-3f24-3cf6-9c2f-0a2237d24b36","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"906b5acb-3f24-3cf6-9c2f-0a2237d24b36"},{"kind":"sourceEvent","id":"fb0563dc-eb72-3a60-af9e-5b260eb5ddd3"},{"kind":"sourceEvent","id":"53bed18e-f00f-31b6-9d98-7f4bb0feec43"},{"kind":"sourceEvent","id":"3d7fd401-4d36-3d83-812a-8911413041b7"},{"kind":"sourceEvent","id":"9d788ade-85c8-3b36-9343-5ee361d63efa"},{"kind":"sourceEvent","id":"0d10ba96-87bb-3ae8-8a5a-2db7657dd760"},{"kind":"sourceEvent","id":"d04cf640-72e4-3a7b-b060-a78118b90040"},{"kind":"sourceEvent","id":"e06ff01f-1294-38d4-8049-edf0a9e2aae8"},{"kind":"sourceEvent","id":"8a35d6ad-e9d2-345b-8d1e-f1fc393d6e29"},{"kind":"sourceEvent","id":"b0980b4e-a8eb-3be9-91d3-b7f0577dd619"},{"kind":"observation","id":"13df2fe3-e820-3fe5-818e-cc8278af2fae"},{"kind":"inventoryScope","id":"4594bfd6-3ff2-33ed-b12d-6a9a24547c63"},{"kind":"sourceAvailability","id":"906b5acb-3f24-3cf6-9c2f-0a2237d24b36/LOANS"},{"kind":"sourceAvailability","id":"906b5acb-3f24-3cf6-9c2f-0a2237d24b36/RETURNS"},{"kind":"sourceAvailability","id":"906b5acb-3f24-3cf6-9c2f-0a2237d24b36/RECEIPTS"},{"kind":"sourceAvailability","id":"906b5acb-3f24-3cf6-9c2f-0a2237d24b36/TRANSFERS"},{"kind":"sourceAvailability","id":"906b5acb-3f24-3cf6-9c2f-0a2237d24b36/REPAIRS"},{"kind":"sourceAvailability","id":"906b5acb-3f24-3cf6-9c2f-0a2237d24b36/INTEGRATIONS"},{"kind":"sourceAvailability","id":"906b5acb-3f24-3cf6-9c2f-0a2237d24b36/INVENTORY"},{"kind":"inventoryScope","id":"8021dca8-65fe-3cab-985e-d4fb9df5c308"},{"kind":"inventoryScope","id":"b0027657-6abb-37d2-8bc4-9341357dfe79"},{"kind":"inventoryScope","id":"4757f5eb-f104-3413-b118-51a6951a650c"},{"kind":"inventoryScope","id":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592"},{"kind":"inventoryScope","id":"40e94818-3a62-34a4-b461-398d7b9ffca2"},{"kind":"inventoryScope","id":"ed60c0be-b218-3f9e-8aa8-69c16f76c22e"},{"kind":"inventoryScope","id":"35514ff7-97f8-345d-8cf7-6cee969a726c"},{"kind":"inventoryScope","id":"351b3e2d-1bff-3290-938e-527c15f97274"},{"kind":"inventoryScope","id":"7bda04de-7eea-3647-9e69-f3610f2ce443"}]`

- Session : `{"scopeId":"4594bfd6-3ff2-33ed-b12d-6a9a24547c63","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"8021dca8-65fe-3cab-985e-d4fb9df5c308","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b0027657-6abb-37d2-8bc4-9341357dfe79","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4757f5eb-f104-3413-b118-51a6951a650c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"40e94818-3a62-34a4-b461-398d7b9ffca2","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"ed60c0be-b218-3f9e-8aa8-69c16f76c22e","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"35514ff7-97f8-345d-8cf7-6cee969a726c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"351b3e2d-1bff-3290-938e-527c15f97274","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"7bda04de-7eea-3647-9e69-f3610f2ce443","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 94815913-bb56-3241-b9ff-af7551fdc592

- Exemplaire : `83b85e1e-f156-3d84-a7d1-22fb6485aace`
- Famille privée : REPARATION_NON_PROJETEE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"24ef0eb6-0149-38f3-a277-e53d69d4abb6","kind":"STATUS_MISMATCH","expectedValue":"ON_SHELF","observedValue":"IN_REPAIR"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"529bfd9a-e635-3246-ac7a-495d1ce25d38","physicalStatus":"IN_REPAIR","physicalSiteId":"CENTRE","physicalLocationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"83b85e1e-f156-3d84-a7d1-22fb6485aace","status":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","sourceEventIds":["16752001-fc3f-3855-acd3-735c05761c61","9b56b16f-f51d-32b6-a999-4c8080561893"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 5a1652f4-d6f2-34bf-a77f-a3414daa3377 | 2026-06-01T12:59:56Z | 2026-06-01T13:00:06Z | `{"operationId":"7f454a6e-32d3-3e2e-8d15-897312757d48","correlationId":"acc3867b-e016-3c17-bfa9-6c9c56c2a9c8","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"a34e5e70-1027-3a5c-99d8-bb562d6e9986"}` |
| IntegrationProcessed | 081a61f5-f9ac-3601-9936-02836b9d7292 | 2026-06-02T12:59:56Z | 2026-06-02T13:00:06Z | `{"originalEventId":"5a1652f4-d6f2-34bf-a77f-a3414daa3377","correlationId":"acc3867b-e016-3c17-bfa9-6c9c56c2a9c8","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | 3d775861-b99d-32dd-8ed5-0ba9eb571677 | 2026-07-31T12:59:56Z | 2026-07-31T13:00:06Z | `{"operationId":"26c41b3d-9b4e-378c-9d7c-f765f778d83e","correlationId":"33fb94a8-87be-3201-b5f0-9f7adc813fcf","siteId":"SUD","locationId":null,"loanId":"4232ca0c-b5d3-31cd-8e33-ecc82f03cfa4"}` |
| IntegrationProcessed | 21ce9256-94d1-3874-a26b-8e575d0899cb | 2026-07-31T13:00:56Z | 2026-07-31T13:01:06Z | `{"originalEventId":"3d775861-b99d-32dd-8ed5-0ba9eb571677","correlationId":"33fb94a8-87be-3201-b5f0-9f7adc813fcf","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | 409f9b84-1240-39ba-91d2-56cf7d122f15 | 2026-08-20T12:59:56Z | 2026-08-20T13:00:06Z | `{"operationId":"98772e52-6228-3034-8231-b1a77781e7da","correlationId":"5c80b92b-709c-3579-b586-7100a488a243","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"4232ca0c-b5d3-31cd-8e33-ecc82f03cfa4"}` |
| ReturnReceived | fb57672b-e35e-315c-8a28-23287cd5d6fd | 2026-08-20T13:00:26Z | 2026-08-20T13:00:36Z | `{"operationId":"d5ac5b2a-2a4d-3dd9-b03f-ad2ab13d5b51","correlationId":"3f830de6-9fda-3c32-9aa5-3367af26b8ee","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"4232ca0c-b5d3-31cd-8e33-ecc82f03cfa4"}` |
| IntegrationProcessed | 4e0d341d-5023-3f11-994d-a51a2c5b7677 | 2026-08-21T12:59:56Z | 2026-08-21T13:00:06Z | `{"originalEventId":"fb57672b-e35e-315c-8a28-23287cd5d6fd","correlationId":"3f830de6-9fda-3c32-9aa5-3367af26b8ee","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| TransferDispatched | 6d6bbc89-6d60-3e2b-be91-385d5f28e913 | 2026-08-30T12:59:56Z | 2026-08-30T13:00:06Z | `{"operationId":"04faa2cf-b39b-3c13-a4e1-294d1690dd0b","correlationId":"63771e17-d5f7-3bb7-a6fb-3a1a0759e1bd","siteId":"SUD","locationId":null,"transferId":"a9e3813e-bcab-3362-93d3-91f0c1ab58e7","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| IntegrationProcessed | 6acf822d-a837-340f-b131-43e1da563320 | 2026-08-30T13:00:56Z | 2026-08-30T13:01:06Z | `{"originalEventId":"6d6bbc89-6d60-3e2b-be91-385d5f28e913","correlationId":"63771e17-d5f7-3bb7-a6fb-3a1a0759e1bd","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"SUD","locationId":null}` |
| TransferReceived | 16752001-fc3f-3855-acd3-735c05761c61 | 2026-08-31T12:59:56Z | 2026-08-31T13:00:06Z | `{"operationId":"df33d29d-9de5-3d6d-b1c2-e19f709ea3bf","correlationId":"66cc4217-af8d-3e6f-b978-220c17e8c993","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","transferId":"a9e3813e-bcab-3362-93d3-91f0c1ab58e7","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| IntegrationProcessed | 9b56b16f-f51d-32b6-a999-4c8080561893 | 2026-09-01T12:59:56Z | 2026-09-01T13:00:06Z | `{"originalEventId":"16752001-fc3f-3855-acd3-735c05761c61","correlationId":"66cc4217-af8d-3e6f-b978-220c17e8c993","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| RepairStarted | 529bfd9a-e635-3246-ac7a-495d1ce25d38 | 2026-09-27T11:44:01Z | 2026-09-27T11:44:11Z | `{"operationId":"5a554024-da04-3d74-9fcb-efc131bc15ad","correlationId":"e1020000-f090-3062-9c3a-fd51880b85f3","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","repairId":"fdbb994f-919f-369d-8c63-0223b80f805b"}` |
| IntegrationProcessed | edc52e24-3c99-3057-a96f-7d2be42587ef | 2026-09-27T11:44:31Z | 2026-09-27T11:44:41Z | `{"originalEventId":"529bfd9a-e635-3246-ac7a-495d1ce25d38","correlationId":"e1020000-f090-3062-9c3a-fd51880b85f3","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**observations**
- `{"observationId":"4a64b20a-d15e-31b3-b3e5-4bfcd307ecf3","copyId":"83b85e1e-f156-3d84-a7d1-22fb6485aace","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","scopeId":"12e94e75-b47d-323a-913a-d261320a99e1","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"83b85e1e-f156-3d84-a7d1-22fb6485aace","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"83b85e1e-f156-3d84-a7d1-22fb6485aace","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"83b85e1e-f156-3d84-a7d1-22fb6485aace","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"83b85e1e-f156-3d84-a7d1-22fb6485aace","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"83b85e1e-f156-3d84-a7d1-22fb6485aace","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"83b85e1e-f156-3d84-a7d1-22fb6485aace","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"83b85e1e-f156-3d84-a7d1-22fb6485aace","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"83b85e1e-f156-3d84-a7d1-22fb6485aace"},{"kind":"sourceEvent","id":"5a1652f4-d6f2-34bf-a77f-a3414daa3377"},{"kind":"sourceEvent","id":"081a61f5-f9ac-3601-9936-02836b9d7292"},{"kind":"sourceEvent","id":"3d775861-b99d-32dd-8ed5-0ba9eb571677"},{"kind":"sourceEvent","id":"21ce9256-94d1-3874-a26b-8e575d0899cb"},{"kind":"sourceEvent","id":"409f9b84-1240-39ba-91d2-56cf7d122f15"},{"kind":"sourceEvent","id":"fb57672b-e35e-315c-8a28-23287cd5d6fd"},{"kind":"sourceEvent","id":"4e0d341d-5023-3f11-994d-a51a2c5b7677"},{"kind":"sourceEvent","id":"6d6bbc89-6d60-3e2b-be91-385d5f28e913"},{"kind":"sourceEvent","id":"6acf822d-a837-340f-b131-43e1da563320"},{"kind":"sourceEvent","id":"16752001-fc3f-3855-acd3-735c05761c61"},{"kind":"sourceEvent","id":"9b56b16f-f51d-32b6-a999-4c8080561893"},{"kind":"sourceEvent","id":"529bfd9a-e635-3246-ac7a-495d1ce25d38"},{"kind":"sourceEvent","id":"edc52e24-3c99-3057-a96f-7d2be42587ef"},{"kind":"observation","id":"4a64b20a-d15e-31b3-b3e5-4bfcd307ecf3"},{"kind":"inventoryScope","id":"12e94e75-b47d-323a-913a-d261320a99e1"},{"kind":"sourceAvailability","id":"83b85e1e-f156-3d84-a7d1-22fb6485aace/LOANS"},{"kind":"sourceAvailability","id":"83b85e1e-f156-3d84-a7d1-22fb6485aace/RETURNS"},{"kind":"sourceAvailability","id":"83b85e1e-f156-3d84-a7d1-22fb6485aace/RECEIPTS"},{"kind":"sourceAvailability","id":"83b85e1e-f156-3d84-a7d1-22fb6485aace/TRANSFERS"},{"kind":"sourceAvailability","id":"83b85e1e-f156-3d84-a7d1-22fb6485aace/REPAIRS"},{"kind":"sourceAvailability","id":"83b85e1e-f156-3d84-a7d1-22fb6485aace/INTEGRATIONS"},{"kind":"sourceAvailability","id":"83b85e1e-f156-3d84-a7d1-22fb6485aace/INVENTORY"},{"kind":"inventoryScope","id":"8021dca8-65fe-3cab-985e-d4fb9df5c308"},{"kind":"inventoryScope","id":"b0027657-6abb-37d2-8bc4-9341357dfe79"},{"kind":"inventoryScope","id":"4757f5eb-f104-3413-b118-51a6951a650c"}]`

- Session : `{"scopeId":"12e94e75-b47d-323a-913a-d261320a99e1","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"8021dca8-65fe-3cab-985e-d4fb9df5c308","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b0027657-6abb-37d2-8bc4-9341357dfe79","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4757f5eb-f104-3413-b118-51a6951a650c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 9ff88220-efe3-3dc1-8d8d-706dce286523

- Exemplaire : `f31f938a-8d5f-336b-b625-57b1895012e3`
- Famille privée : RETOUR_NON_PROJETE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"e216952e-ff9a-3d20-9b92-466979bf925a","kind":"STATUS_MISMATCH","expectedValue":"ON_LOAN","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"a5150b05-bc63-3b6d-91fb-32b18e1d9f7c","copyId":"f31f938a-8d5f-336b-b625-57b1895012e3","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T11:17:16Z","recordedAt":"2026-09-27T11:17:26Z","payload":{"originalEventId":"03436430-c9a4-3b84-8763-da7684d545c7","correlationId":"e374b13d-c95e-357d-b866-8ef79fc8021e","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"03436430-c9a4-3b84-8763-da7684d545c7","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État projeté : `{"copyId":"f31f938a-8d5f-336b-b625-57b1895012e3","status":"ON_LOAN","siteId":"CENTRE","locationId":null,"sourceEventIds":["0351cca1-daaa-3926-8fb8-50acf201b620","a785583d-f8fe-3f4c-a91c-d8c996866992"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 8a6521b2-0e27-30cb-857a-ff334aaa93b4 | 2026-06-02T03:34:04Z | 2026-06-02T03:34:14Z | `{"operationId":"d7b27bb8-8b59-3491-b59e-8818bd810958","correlationId":"ee8a12d5-bf9e-3bef-8cb9-f3fc9e668458","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"3db893e5-6708-3a15-b34e-411846b1dba0"}` |
| IntegrationProcessed | 3a26ded3-b762-30cb-bc00-bdc108a523fd | 2026-06-03T03:34:04Z | 2026-06-03T03:34:14Z | `{"originalEventId":"8a6521b2-0e27-30cb-857a-ff334aaa93b4","correlationId":"ee8a12d5-bf9e-3bef-8cb9-f3fc9e668458","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | 4394100d-56b2-323e-ba51-194ed5fdcf76 | 2026-08-01T03:34:04Z | 2026-08-01T03:34:14Z | `{"operationId":"dfc13c32-1b31-3ec0-b24d-1386cd549e19","correlationId":"a02ebfba-f024-39bb-8d4d-2372e0cfe815","siteId":"SUD","locationId":null,"loanId":"38cccbc7-fedb-3d7f-a610-2c763b8aac17"}` |
| IntegrationProcessed | aa4ef2f3-59e4-3585-8e2f-421db0fc0300 | 2026-08-01T03:35:04Z | 2026-08-01T03:35:14Z | `{"originalEventId":"4394100d-56b2-323e-ba51-194ed5fdcf76","correlationId":"a02ebfba-f024-39bb-8d4d-2372e0cfe815","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | 1660d7ce-1b83-385b-be0a-90da379e0873 | 2026-08-21T03:34:04Z | 2026-08-21T03:34:14Z | `{"operationId":"9f497a3d-90fa-32a8-a761-e7fa7f1acd9c","correlationId":"2ee3b09c-3516-354e-9870-72ba228688b8","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"38cccbc7-fedb-3d7f-a610-2c763b8aac17"}` |
| ReturnReceived | 2a1a54e4-19f0-34be-a448-d552bd32392d | 2026-08-21T03:34:34Z | 2026-08-21T03:34:44Z | `{"operationId":"dc15f16e-505b-39e1-a168-0b4357884328","correlationId":"abe7ee8f-64d7-3627-8d4d-b9dad206d786","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"38cccbc7-fedb-3d7f-a610-2c763b8aac17"}` |
| IntegrationProcessed | abe646cd-1421-3ca8-a4b2-70524b074d74 | 2026-08-22T03:34:04Z | 2026-08-22T03:34:14Z | `{"originalEventId":"2a1a54e4-19f0-34be-a448-d552bd32392d","correlationId":"abe7ee8f-64d7-3627-8d4d-b9dad206d786","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| TransferDispatched | 6676db3a-7218-38c9-946b-4ba0efa9ea63 | 2026-08-31T03:34:04Z | 2026-08-31T03:34:14Z | `{"operationId":"961e7684-a513-3882-8d60-e6e808d6fa7d","correlationId":"0257572d-2749-331a-bd0b-cbb209e5e69a","siteId":"SUD","locationId":null,"transferId":"28882f22-9cb6-371b-8eab-4254c60982ad","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| IntegrationProcessed | 46374585-e020-3352-81f2-b703abb409ea | 2026-08-31T03:35:04Z | 2026-08-31T03:35:14Z | `{"originalEventId":"6676db3a-7218-38c9-946b-4ba0efa9ea63","correlationId":"0257572d-2749-331a-bd0b-cbb209e5e69a","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"SUD","locationId":null}` |
| TransferReceived | e4a5444a-7693-3dea-931b-24481fd82aee | 2026-09-01T03:34:04Z | 2026-09-01T03:34:14Z | `{"operationId":"d3172951-cc0d-3a64-adba-1b87b5998267","correlationId":"559416cf-88d5-3ee3-a072-a19d87d466b4","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","transferId":"28882f22-9cb6-371b-8eab-4254c60982ad","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| IntegrationProcessed | eb0c6740-7828-330f-8d71-1211ec2d6049 | 2026-09-02T03:34:04Z | 2026-09-02T03:34:14Z | `{"originalEventId":"e4a5444a-7693-3dea-931b-24481fd82aee","correlationId":"559416cf-88d5-3ee3-a072-a19d87d466b4","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 0351cca1-daaa-3926-8fb8-50acf201b620 | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"9ded7540-5d17-3631-9f46-a4e6211dc399","correlationId":"905f7044-04d6-3b4d-8885-386e849ebe29","siteId":"CENTRE","locationId":null,"loanId":"26f64205-801d-301f-8631-942d56b264f8"}` |
| IntegrationProcessed | a785583d-f8fe-3f4c-a91c-d8c996866992 | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"0351cca1-daaa-3926-8fb8-50acf201b620","correlationId":"905f7044-04d6-3b4d-8885-386e849ebe29","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | e25a2833-4ef4-37d3-af09-dc8ba6954f1a | 2026-09-27T11:16:16Z | 2026-09-27T11:16:26Z | `{"operationId":"711f8969-a343-35f7-9e96-2b0aee729954","correlationId":"32f83e57-7f28-3c52-bc2c-9ea478613fb3","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"26f64205-801d-301f-8631-942d56b264f8"}` |
| ReturnReceived | 03436430-c9a4-3b84-8763-da7684d545c7 | 2026-09-27T11:16:46Z | 2026-09-27T11:16:56Z | `{"operationId":"8cc7d7b4-e901-38aa-9a62-573494af0d55","correlationId":"e374b13d-c95e-357d-b866-8ef79fc8021e","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"26f64205-801d-301f-8631-942d56b264f8"}` |

**observations**
- `{"observationId":"a0787b42-8ea0-357a-bc55-6b778843bf92","copyId":"f31f938a-8d5f-336b-b625-57b1895012e3","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","scopeId":"057c773f-512b-39ba-af02-aa5f3aa70a82","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**sourceAvailability**
- `{"copyId":"f31f938a-8d5f-336b-b625-57b1895012e3","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f31f938a-8d5f-336b-b625-57b1895012e3","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f31f938a-8d5f-336b-b625-57b1895012e3","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f31f938a-8d5f-336b-b625-57b1895012e3","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f31f938a-8d5f-336b-b625-57b1895012e3","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f31f938a-8d5f-336b-b625-57b1895012e3","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f31f938a-8d5f-336b-b625-57b1895012e3","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"f31f938a-8d5f-336b-b625-57b1895012e3"},{"kind":"sourceEvent","id":"8a6521b2-0e27-30cb-857a-ff334aaa93b4"},{"kind":"sourceEvent","id":"3a26ded3-b762-30cb-bc00-bdc108a523fd"},{"kind":"sourceEvent","id":"4394100d-56b2-323e-ba51-194ed5fdcf76"},{"kind":"sourceEvent","id":"aa4ef2f3-59e4-3585-8e2f-421db0fc0300"},{"kind":"sourceEvent","id":"1660d7ce-1b83-385b-be0a-90da379e0873"},{"kind":"sourceEvent","id":"2a1a54e4-19f0-34be-a448-d552bd32392d"},{"kind":"sourceEvent","id":"abe646cd-1421-3ca8-a4b2-70524b074d74"},{"kind":"sourceEvent","id":"6676db3a-7218-38c9-946b-4ba0efa9ea63"},{"kind":"sourceEvent","id":"46374585-e020-3352-81f2-b703abb409ea"},{"kind":"sourceEvent","id":"e4a5444a-7693-3dea-931b-24481fd82aee"},{"kind":"sourceEvent","id":"eb0c6740-7828-330f-8d71-1211ec2d6049"},{"kind":"sourceEvent","id":"0351cca1-daaa-3926-8fb8-50acf201b620"},{"kind":"sourceEvent","id":"a785583d-f8fe-3f4c-a91c-d8c996866992"},{"kind":"sourceEvent","id":"e25a2833-4ef4-37d3-af09-dc8ba6954f1a"},{"kind":"sourceEvent","id":"03436430-c9a4-3b84-8763-da7684d545c7"},{"kind":"observation","id":"a0787b42-8ea0-357a-bc55-6b778843bf92"},{"kind":"inventoryScope","id":"057c773f-512b-39ba-af02-aa5f3aa70a82"},{"kind":"sourceAvailability","id":"f31f938a-8d5f-336b-b625-57b1895012e3/LOANS"},{"kind":"sourceAvailability","id":"f31f938a-8d5f-336b-b625-57b1895012e3/RETURNS"},{"kind":"sourceAvailability","id":"f31f938a-8d5f-336b-b625-57b1895012e3/RECEIPTS"},{"kind":"sourceAvailability","id":"f31f938a-8d5f-336b-b625-57b1895012e3/TRANSFERS"},{"kind":"sourceAvailability","id":"f31f938a-8d5f-336b-b625-57b1895012e3/REPAIRS"},{"kind":"sourceAvailability","id":"f31f938a-8d5f-336b-b625-57b1895012e3/INTEGRATIONS"},{"kind":"sourceAvailability","id":"f31f938a-8d5f-336b-b625-57b1895012e3/INVENTORY"}]`

- Session : `{"scopeId":"057c773f-512b-39ba-af02-aa5f3aa70a82","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas ac9702fb-87a9-3bfc-985d-287fad7087d5

- Exemplaire : `a0d2cf4c-de0a-3512-9bd1-120e2e902cd8`
- Famille privée : LOCALISATION ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"b5d9b939-7d5a-394a-a64a-de47147f3855","kind":"COUNT_UNCERTAIN","expectedValue":"63e3aa91-8217-35a3-b458-6bae9b374c87","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"mechanism":"Aucun déplacement ; signal de collecte non confirmé.","activePerturbations":[],"physicalStatus":"ON_SHELF","physicalSiteId":"SUD","physicalLocationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"Le scan valide confirme l'emplacement attendu ; aucun écart de localisation étayé.","forbiddenClaims":["Vol","Perte définitive","Responsabilité d'un opérateur"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"a0d2cf4c-de0a-3512-9bd1-120e2e902cd8","status":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","sourceEventIds":["9c0c9624-4fb6-36d4-9d83-2d830d964b7c","716c58f3-dc5b-3ba2-87b8-72673632ac70"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | ebdb4a86-d938-3698-bd42-481e23dbdc05 | 2026-06-02T01:11:28Z | 2026-06-02T01:11:38Z | `{"operationId":"2850dc42-a081-3836-b0e0-67ea8250ed36","correlationId":"f05ca056-9189-3c26-b2c0-a3fc176ba17c","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"cd70ce82-e795-32db-bb50-c65c63482424"}` |
| IntegrationProcessed | 10bc14ee-26d4-31a8-8419-0d6dac491d95 | 2026-06-03T01:11:28Z | 2026-06-03T01:11:38Z | `{"originalEventId":"ebdb4a86-d938-3698-bd42-481e23dbdc05","correlationId":"f05ca056-9189-3c26-b2c0-a3fc176ba17c","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | 38ff44ba-dcda-3a09-935c-57f42ca05b6a | 2026-08-01T01:11:28Z | 2026-08-01T01:11:38Z | `{"operationId":"3133dd6d-c214-3d43-ac39-bd294e081eef","correlationId":"c9ef3ded-6ff5-3e56-8ddf-51e2d24cb456","siteId":"SUD","locationId":null,"loanId":"189ad2fc-931e-3793-9a8e-20b067be2070"}` |
| IntegrationProcessed | 4dd82bed-786b-3e92-af0f-2694a0de436e | 2026-08-01T01:12:28Z | 2026-08-01T01:12:38Z | `{"originalEventId":"38ff44ba-dcda-3a09-935c-57f42ca05b6a","correlationId":"c9ef3ded-6ff5-3e56-8ddf-51e2d24cb456","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | 5ca8b7b0-96c1-33d4-83b7-b218aecc5941 | 2026-08-21T01:11:28Z | 2026-08-21T01:11:38Z | `{"operationId":"3ff56c69-bac2-3a14-a0dd-315da607abc1","correlationId":"abf819d8-262a-3ef6-8b4e-cc4cf4dd4470","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"189ad2fc-931e-3793-9a8e-20b067be2070"}` |
| ReturnReceived | 9c0c9624-4fb6-36d4-9d83-2d830d964b7c | 2026-08-21T01:11:58Z | 2026-08-21T01:12:08Z | `{"operationId":"7e740840-83dd-3113-baf2-75f30fc40277","correlationId":"3c3fb8b7-ed1e-3301-99f0-54d6f60cf287","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"189ad2fc-931e-3793-9a8e-20b067be2070"}` |
| IntegrationProcessed | 716c58f3-dc5b-3ba2-87b8-72673632ac70 | 2026-08-22T01:11:28Z | 2026-08-22T01:11:38Z | `{"originalEventId":"9c0c9624-4fb6-36d4-9d83-2d830d964b7c","correlationId":"3c3fb8b7-ed1e-3301-99f0-54d6f60cf287","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |

**observations**
- `{"observationId":"169f4f1b-170b-3185-8b68-5d6352cfa8d5","copyId":"a0d2cf4c-de0a-3512-9bd1-120e2e902cd8","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","scopeId":"9d6c09df-1bb9-3fa2-927d-b44bcfe9e1be","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"a0d2cf4c-de0a-3512-9bd1-120e2e902cd8","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a0d2cf4c-de0a-3512-9bd1-120e2e902cd8","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a0d2cf4c-de0a-3512-9bd1-120e2e902cd8","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a0d2cf4c-de0a-3512-9bd1-120e2e902cd8","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a0d2cf4c-de0a-3512-9bd1-120e2e902cd8","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a0d2cf4c-de0a-3512-9bd1-120e2e902cd8","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a0d2cf4c-de0a-3512-9bd1-120e2e902cd8","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"observation","id":"169f4f1b-170b-3185-8b68-5d6352cfa8d5"},{"kind":"systemState","id":"a0d2cf4c-de0a-3512-9bd1-120e2e902cd8"}]`

## Cas ae912e2d-e042-3df3-9b9a-e58bcb836f07

- Exemplaire : `2c6460c6-a14e-3a68-b989-dd824ec5b1f0`
- Famille privée : RETOUR_NON_PROJETE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"52d38eea-d4ec-3f5d-b47d-c2438dc74895","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"c09f4443-b6d0-3450-add1-d235b8f136c1","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"2c6460c6-a14e-3a68-b989-dd824ec5b1f0","status":"RECEIVING","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","sourceEventIds":["c09f4443-b6d0-3450-add1-d235b8f136c1","e95a36e3-9da8-3c76-9eb6-2ef13272f636"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 72771bdf-5566-3d78-a5f4-cef118de481b | 2026-06-01T17:24:52Z | 2026-06-01T17:25:02Z | `{"operationId":"bead1b18-11d8-3a34-a05f-41c3efff7a74","correlationId":"ff45a74a-4e5f-37f8-8cf1-9ed911111785","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"ed316216-9e2b-39e0-a538-409658b080da"}` |
| IntegrationProcessed | 5de977f5-047e-3cad-8944-b706394649c5 | 2026-06-02T17:24:52Z | 2026-06-02T17:25:02Z | `{"originalEventId":"72771bdf-5566-3d78-a5f4-cef118de481b","correlationId":"ff45a74a-4e5f-37f8-8cf1-9ed911111785","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 04a66ff0-ba65-3198-8811-de34fe97b349 | 2026-07-31T17:24:52Z | 2026-07-31T17:25:02Z | `{"operationId":"ef50d916-6db7-330b-abd9-59f73740ea01","correlationId":"fad8ef8f-ac51-3f8d-b4a9-eb113a06b771","siteId":"NORD","locationId":null,"loanId":"4b9d0022-740c-3713-9b22-3641570f6e12"}` |
| IntegrationProcessed | 93ca3e33-f2b9-30b8-af36-cb6ffb0e3b7d | 2026-07-31T17:25:52Z | 2026-07-31T17:26:02Z | `{"originalEventId":"04a66ff0-ba65-3198-8811-de34fe97b349","correlationId":"fad8ef8f-ac51-3f8d-b4a9-eb113a06b771","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | b28b7b84-f76a-3358-98f5-fa24212ad506 | 2026-08-20T17:24:52Z | 2026-08-20T17:25:02Z | `{"operationId":"e9b44606-8c4d-3e23-80f1-a17aeed308f2","correlationId":"ac5d33be-3d09-3156-9c1c-a01b30eded78","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"4b9d0022-740c-3713-9b22-3641570f6e12"}` |
| ReturnReceived | ace3e623-c42a-319d-8480-c5eaea37f889 | 2026-08-20T17:25:22Z | 2026-08-20T17:25:32Z | `{"operationId":"6dde7dca-0d2b-3a60-8897-1cd98d817f8d","correlationId":"7d0e098a-2504-3539-ab60-43b4646731cd","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"4b9d0022-740c-3713-9b22-3641570f6e12"}` |
| IntegrationProcessed | f2a28e05-6709-32e4-8968-2b4336720d00 | 2026-08-21T17:24:52Z | 2026-08-21T17:25:02Z | `{"originalEventId":"ace3e623-c42a-319d-8480-c5eaea37f889","correlationId":"7d0e098a-2504-3539-ab60-43b4646731cd","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | a2dbece2-9672-3f1f-aca7-58eb96b8ff74 | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"42bced82-8729-3473-842d-96abb51265ea","correlationId":"4c212704-ee88-3f45-9b3d-1f1fb9ba2bba","siteId":"NORD","locationId":null,"loanId":"99762513-4c69-3c3f-91e1-8c26611c51b9"}` |
| IntegrationProcessed | 1ed2cca4-fa4c-3b3a-8e7f-77055669f708 | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"a2dbece2-9672-3f1f-aca7-58eb96b8ff74","correlationId":"4c212704-ee88-3f45-9b3d-1f1fb9ba2bba","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | 05fb344f-d37f-338c-a94a-2d47c3794dee | 2026-09-27T11:26:06Z | 2026-09-27T11:26:16Z | `{"operationId":"560340cb-e515-30ef-b239-2040fe66ae2e","correlationId":"bce84941-b4ab-32f3-ba49-69555c8ffee7","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"99762513-4c69-3c3f-91e1-8c26611c51b9"}` |
| ReturnReceived | c09f4443-b6d0-3450-add1-d235b8f136c1 | 2026-09-27T11:26:36Z | 2026-09-27T11:26:46Z | `{"operationId":"91e9e24c-25ed-398a-9ab8-c8932c90c0e9","correlationId":"4992cc45-7365-3d95-8b53-b42cfa0b4d78","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"99762513-4c69-3c3f-91e1-8c26611c51b9"}` |
| IntegrationProcessed | 977f375e-c649-3f0d-aaeb-46d80b077de3 | 2026-09-27T11:27:06Z | 2026-09-27T11:27:16Z | `{"originalEventId":"c09f4443-b6d0-3450-add1-d235b8f136c1","correlationId":"4992cc45-7365-3d95-8b53-b42cfa0b4d78","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| IntegrationProcessed | e95a36e3-9da8-3c76-9eb6-2ef13272f636 | 2026-09-27T11:28:06Z | 2026-09-27T11:28:16Z | `{"originalEventId":"c09f4443-b6d0-3450-add1-d235b8f136c1","correlationId":"4992cc45-7365-3d95-8b53-b42cfa0b4d78","outcome":"APPLIED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361"}` |

**observations**
- `{"observationId":"43eb1be8-919d-347a-ba56-f1863655ab2e","copyId":"2c6460c6-a14e-3a68-b989-dd824ec5b1f0","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","scopeId":"5af1d118-5264-36ac-91c6-a898b634bc93","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`
- `{"observationId":"218db39c-143a-34bb-bed4-61214bc94934","copyId":"2c6460c6-a14e-3a68-b989-dd824ec5b1f0","locationId":"5b61a611-a926-3ed2-82dc-fc122118757c","scopeId":"d60acc1b-d5b1-3fed-98e8-986d6a32b493","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**sourceAvailability**
- `{"copyId":"2c6460c6-a14e-3a68-b989-dd824ec5b1f0","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2c6460c6-a14e-3a68-b989-dd824ec5b1f0","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2c6460c6-a14e-3a68-b989-dd824ec5b1f0","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2c6460c6-a14e-3a68-b989-dd824ec5b1f0","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2c6460c6-a14e-3a68-b989-dd824ec5b1f0","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2c6460c6-a14e-3a68-b989-dd824ec5b1f0","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"2c6460c6-a14e-3a68-b989-dd824ec5b1f0","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"2c6460c6-a14e-3a68-b989-dd824ec5b1f0"},{"kind":"sourceEvent","id":"72771bdf-5566-3d78-a5f4-cef118de481b"},{"kind":"sourceEvent","id":"5de977f5-047e-3cad-8944-b706394649c5"},{"kind":"sourceEvent","id":"04a66ff0-ba65-3198-8811-de34fe97b349"},{"kind":"sourceEvent","id":"93ca3e33-f2b9-30b8-af36-cb6ffb0e3b7d"},{"kind":"sourceEvent","id":"b28b7b84-f76a-3358-98f5-fa24212ad506"},{"kind":"sourceEvent","id":"ace3e623-c42a-319d-8480-c5eaea37f889"},{"kind":"sourceEvent","id":"f2a28e05-6709-32e4-8968-2b4336720d00"},{"kind":"sourceEvent","id":"a2dbece2-9672-3f1f-aca7-58eb96b8ff74"},{"kind":"sourceEvent","id":"1ed2cca4-fa4c-3b3a-8e7f-77055669f708"},{"kind":"sourceEvent","id":"05fb344f-d37f-338c-a94a-2d47c3794dee"},{"kind":"sourceEvent","id":"c09f4443-b6d0-3450-add1-d235b8f136c1"},{"kind":"sourceEvent","id":"977f375e-c649-3f0d-aaeb-46d80b077de3"},{"kind":"sourceEvent","id":"e95a36e3-9da8-3c76-9eb6-2ef13272f636"},{"kind":"observation","id":"43eb1be8-919d-347a-ba56-f1863655ab2e"},{"kind":"inventoryScope","id":"5af1d118-5264-36ac-91c6-a898b634bc93"},{"kind":"observation","id":"218db39c-143a-34bb-bed4-61214bc94934"},{"kind":"inventoryScope","id":"d60acc1b-d5b1-3fed-98e8-986d6a32b493"},{"kind":"sourceAvailability","id":"2c6460c6-a14e-3a68-b989-dd824ec5b1f0/LOANS"},{"kind":"sourceAvailability","id":"2c6460c6-a14e-3a68-b989-dd824ec5b1f0/RETURNS"},{"kind":"sourceAvailability","id":"2c6460c6-a14e-3a68-b989-dd824ec5b1f0/RECEIPTS"},{"kind":"sourceAvailability","id":"2c6460c6-a14e-3a68-b989-dd824ec5b1f0/TRANSFERS"},{"kind":"sourceAvailability","id":"2c6460c6-a14e-3a68-b989-dd824ec5b1f0/REPAIRS"},{"kind":"sourceAvailability","id":"2c6460c6-a14e-3a68-b989-dd824ec5b1f0/INTEGRATIONS"},{"kind":"sourceAvailability","id":"2c6460c6-a14e-3a68-b989-dd824ec5b1f0/INVENTORY"},{"kind":"inventoryScope","id":"be5b3298-e883-3ee7-ad58-ee9f92b3e51e"},{"kind":"inventoryScope","id":"456afa19-cbae-3187-bcba-3bf1ba51b9b8"}]`

- Session : `{"scopeId":"5af1d118-5264-36ac-91c6-a898b634bc93","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"d60acc1b-d5b1-3fed-98e8-986d6a32b493","locationId":"5b61a611-a926-3ed2-82dc-fc122118757c","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"be5b3298-e883-3ee7-ad58-ee9f92b3e51e","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"456afa19-cbae-3187-bcba-3bf1ba51b9b8","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas b105e208-df0b-35f3-9c45-f72461561893

- Exemplaire : `9e97e67f-79d0-3c23-95db-ab60e12dd9dd`
- Famille privée : INVENTAIRE_INCOMPLET ; variante : POSITIVE
- Symptômes : `[{"symptomId":"e9ade8dc-b171-3d81-bd82-b48967612ff5","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["COLLECTION_INTERRUPTED"],"mechanism":"Interruption de collecte ; exemplaire resté au rayon.","physicalStatus":"ON_SHELF","physicalSiteId":"CENTRE","physicalLocationId":"4a5364f2-388b-351d-91f2-d1364d119042","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["INVENTORY_INCOMPLETE"],"explanation":"La session interrompue explique l'incertitude de comptage ; la présence physique reste à vérifier.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État projeté : `{"copyId":"9e97e67f-79d0-3c23-95db-ab60e12dd9dd","status":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","sourceEventIds":["9601e5c9-3f9b-3d6f-bc91-ba0baae2788e","4eaa86e2-34e5-3a97-b02a-4258fb659142"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 97e2c747-e051-3842-80d8-ce8bfb927967 | 2026-06-01T12:37:01Z | 2026-06-01T12:37:11Z | `{"operationId":"95f7f823-bb18-3c80-8791-601634a0f06b","correlationId":"63bb8cea-a2a7-39ee-892b-c1400e839e32","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"59020f90-3285-3c86-90fb-b3841e0f8bc3"}` |
| IntegrationProcessed | 2cdfd8f7-9c30-3dd3-8500-63e932ac44b0 | 2026-06-02T12:37:01Z | 2026-06-02T12:37:11Z | `{"originalEventId":"97e2c747-e051-3842-80d8-ce8bfb927967","correlationId":"63bb8cea-a2a7-39ee-892b-c1400e839e32","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 71a9f083-d5cb-3146-adab-fbb78b7b207b | 2026-07-31T12:37:01Z | 2026-07-31T12:37:11Z | `{"operationId":"30d66046-a0b4-3c9e-81b6-df7c38e145ea","correlationId":"2433eb5f-cdd7-335c-9c7e-0cd69cfdc7f2","siteId":"CENTRE","locationId":null,"loanId":"dafe0390-2f81-3b96-ac02-b4b0f5ca8b23"}` |
| IntegrationProcessed | 911441dd-a51d-3c60-ad97-881f8a06ce75 | 2026-07-31T12:38:01Z | 2026-07-31T12:38:11Z | `{"originalEventId":"71a9f083-d5cb-3146-adab-fbb78b7b207b","correlationId":"2433eb5f-cdd7-335c-9c7e-0cd69cfdc7f2","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 907bf151-0a87-3218-aaa6-11099aa08f6a | 2026-08-20T12:37:01Z | 2026-08-20T12:37:11Z | `{"operationId":"d1c1152d-a7c6-3dd9-90a6-9851cf4db36a","correlationId":"b4b0b43d-3627-364a-a75d-701ece9c2b61","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"dafe0390-2f81-3b96-ac02-b4b0f5ca8b23"}` |
| ReturnReceived | 9601e5c9-3f9b-3d6f-bc91-ba0baae2788e | 2026-08-20T12:37:31Z | 2026-08-20T12:37:41Z | `{"operationId":"42b9e8fb-5f65-3d8e-84d1-c6afa1f7adfb","correlationId":"9854f4dc-4fa0-3293-b811-56fb58bff5bd","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"dafe0390-2f81-3b96-ac02-b4b0f5ca8b23"}` |
| IntegrationProcessed | 4eaa86e2-34e5-3a97-b02a-4258fb659142 | 2026-08-21T12:37:01Z | 2026-08-21T12:37:11Z | `{"originalEventId":"9601e5c9-3f9b-3d6f-bc91-ba0baae2788e","correlationId":"9854f4dc-4fa0-3293-b811-56fb58bff5bd","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |

**observations**

**sourceAvailability**
- `{"copyId":"9e97e67f-79d0-3c23-95db-ab60e12dd9dd","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9e97e67f-79d0-3c23-95db-ab60e12dd9dd","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9e97e67f-79d0-3c23-95db-ab60e12dd9dd","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9e97e67f-79d0-3c23-95db-ab60e12dd9dd","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9e97e67f-79d0-3c23-95db-ab60e12dd9dd","source":"INVENTORY","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"9e97e67f-79d0-3c23-95db-ab60e12dd9dd","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9e97e67f-79d0-3c23-95db-ab60e12dd9dd","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**
- `{"noteId":"911c2cd1-d8b5-30ad-ad73-096758d91f61","copyId":"9e97e67f-79d0-3c23-95db-ab60e12dd9dd","text":"Le terminal de collecte a cessé de répondre pendant le passage ; aucune conclusion sur les exemplaires non lus.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"9e97e67f-79d0-3c23-95db-ab60e12dd9dd"},{"kind":"sourceEvent","id":"97e2c747-e051-3842-80d8-ce8bfb927967"},{"kind":"sourceEvent","id":"2cdfd8f7-9c30-3dd3-8500-63e932ac44b0"},{"kind":"sourceEvent","id":"71a9f083-d5cb-3146-adab-fbb78b7b207b"},{"kind":"sourceEvent","id":"911441dd-a51d-3c60-ad97-881f8a06ce75"},{"kind":"sourceEvent","id":"907bf151-0a87-3218-aaa6-11099aa08f6a"},{"kind":"sourceEvent","id":"9601e5c9-3f9b-3d6f-bc91-ba0baae2788e"},{"kind":"sourceEvent","id":"4eaa86e2-34e5-3a97-b02a-4258fb659142"},{"kind":"sourceAvailability","id":"9e97e67f-79d0-3c23-95db-ab60e12dd9dd/LOANS"},{"kind":"sourceAvailability","id":"9e97e67f-79d0-3c23-95db-ab60e12dd9dd/RETURNS"},{"kind":"sourceAvailability","id":"9e97e67f-79d0-3c23-95db-ab60e12dd9dd/RECEIPTS"},{"kind":"sourceAvailability","id":"9e97e67f-79d0-3c23-95db-ab60e12dd9dd/TRANSFERS"},{"kind":"sourceAvailability","id":"9e97e67f-79d0-3c23-95db-ab60e12dd9dd/REPAIRS"},{"kind":"sourceAvailability","id":"9e97e67f-79d0-3c23-95db-ab60e12dd9dd/INTEGRATIONS"},{"kind":"sourceAvailability","id":"9e97e67f-79d0-3c23-95db-ab60e12dd9dd/INVENTORY"},{"kind":"operatorNote","id":"911c2cd1-d8b5-30ad-ad73-096758d91f61"},{"kind":"inventoryScope","id":"8021dca8-65fe-3cab-985e-d4fb9df5c308"},{"kind":"inventoryScope","id":"b0027657-6abb-37d2-8bc4-9341357dfe79"},{"kind":"inventoryScope","id":"4757f5eb-f104-3413-b118-51a6951a650c"},{"kind":"inventoryScope","id":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592"},{"kind":"inventoryScope","id":"40e94818-3a62-34a4-b461-398d7b9ffca2"},{"kind":"inventoryScope","id":"ed60c0be-b218-3f9e-8aa8-69c16f76c22e"},{"kind":"inventoryScope","id":"35514ff7-97f8-345d-8cf7-6cee969a726c"},{"kind":"inventoryScope","id":"35514ff7-97f8-345d-8cf7-6cee969a726c"}]`

- Session : `{"scopeId":"8021dca8-65fe-3cab-985e-d4fb9df5c308","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b0027657-6abb-37d2-8bc4-9341357dfe79","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4757f5eb-f104-3413-b118-51a6951a650c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"40e94818-3a62-34a4-b461-398d7b9ffca2","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"ed60c0be-b218-3f9e-8aa8-69c16f76c22e","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"35514ff7-97f8-345d-8cf7-6cee969a726c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"35514ff7-97f8-345d-8cf7-6cee969a726c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas b1f240b6-b9f3-3d80-8868-611a0d0352f8

- Exemplaire : `f0daac5e-571b-3380-bac9-8e4b749ed59f`
- Famille privée : RECEPTION_NON_INTEGREE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"d818485f-35fd-3bee-8d1b-5d84952790a0","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"df95b645-5e90-31ba-9f85-dcef7332809d","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"f0daac5e-571b-3380-bac9-8e4b749ed59f","status":"RECEIVING","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","sourceEventIds":["df95b645-5e90-31ba-9f85-dcef7332809d","b66ee5a3-5aca-3edd-9c3c-f3ae28542344"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | df95b645-5e90-31ba-9f85-dcef7332809d | 2026-09-27T07:42:54Z | 2026-09-27T07:43:04Z | `{"operationId":"7a71776a-5ede-374e-88d1-f303a4b1f446","correlationId":"ff087d6e-f1fe-36ab-bfea-1dd4357135c2","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"65c5da99-1f32-3fe1-8a3e-002822faa496"}` |
| IntegrationProcessed | a9b5276f-9006-3f57-b026-53433d807de5 | 2026-09-27T07:43:24Z | 2026-09-27T07:43:34Z | `{"originalEventId":"df95b645-5e90-31ba-9f85-dcef7332809d","correlationId":"ff087d6e-f1fe-36ab-bfea-1dd4357135c2","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| IntegrationProcessed | b66ee5a3-5aca-3edd-9c3c-f3ae28542344 | 2026-09-27T07:44:24Z | 2026-09-27T07:44:34Z | `{"originalEventId":"df95b645-5e90-31ba-9f85-dcef7332809d","correlationId":"ff087d6e-f1fe-36ab-bfea-1dd4357135c2","outcome":"APPLIED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca"}` |

**observations**
- `{"observationId":"ae073ee5-cad4-3a1e-96b9-d20a224d2184","copyId":"f0daac5e-571b-3380-bac9-8e4b749ed59f","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","scopeId":"36b049f7-1dcb-338a-a1c8-06aeb50d77ff","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`
- `{"observationId":"07372dae-d6c7-3b7a-8644-9d49bf4e7025","copyId":"f0daac5e-571b-3380-bac9-8e4b749ed59f","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","scopeId":"e356c26d-748f-3fea-aaa9-35ba4054ee1e","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**sourceAvailability**
- `{"copyId":"f0daac5e-571b-3380-bac9-8e4b749ed59f","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f0daac5e-571b-3380-bac9-8e4b749ed59f","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f0daac5e-571b-3380-bac9-8e4b749ed59f","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f0daac5e-571b-3380-bac9-8e4b749ed59f","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f0daac5e-571b-3380-bac9-8e4b749ed59f","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f0daac5e-571b-3380-bac9-8e4b749ed59f","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f0daac5e-571b-3380-bac9-8e4b749ed59f","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"f0daac5e-571b-3380-bac9-8e4b749ed59f"},{"kind":"sourceEvent","id":"df95b645-5e90-31ba-9f85-dcef7332809d"},{"kind":"sourceEvent","id":"a9b5276f-9006-3f57-b026-53433d807de5"},{"kind":"sourceEvent","id":"b66ee5a3-5aca-3edd-9c3c-f3ae28542344"},{"kind":"observation","id":"ae073ee5-cad4-3a1e-96b9-d20a224d2184"},{"kind":"inventoryScope","id":"36b049f7-1dcb-338a-a1c8-06aeb50d77ff"},{"kind":"observation","id":"07372dae-d6c7-3b7a-8644-9d49bf4e7025"},{"kind":"inventoryScope","id":"e356c26d-748f-3fea-aaa9-35ba4054ee1e"},{"kind":"sourceAvailability","id":"f0daac5e-571b-3380-bac9-8e4b749ed59f/LOANS"},{"kind":"sourceAvailability","id":"f0daac5e-571b-3380-bac9-8e4b749ed59f/RETURNS"},{"kind":"sourceAvailability","id":"f0daac5e-571b-3380-bac9-8e4b749ed59f/RECEIPTS"},{"kind":"sourceAvailability","id":"f0daac5e-571b-3380-bac9-8e4b749ed59f/TRANSFERS"},{"kind":"sourceAvailability","id":"f0daac5e-571b-3380-bac9-8e4b749ed59f/REPAIRS"},{"kind":"sourceAvailability","id":"f0daac5e-571b-3380-bac9-8e4b749ed59f/INTEGRATIONS"},{"kind":"sourceAvailability","id":"f0daac5e-571b-3380-bac9-8e4b749ed59f/INVENTORY"},{"kind":"inventoryScope","id":"27d49d3d-f104-306e-b8d1-ada0e3973acb"},{"kind":"inventoryScope","id":"59116f6f-fc86-3206-80a1-873afe081073"},{"kind":"inventoryScope","id":"bea0c19c-0c1e-3d31-ab4d-dda10370bd37"},{"kind":"inventoryScope","id":"57ee798c-ebfa-3b42-9818-10132c6f964d"}]`

- Session : `{"scopeId":"36b049f7-1dcb-338a-a1c8-06aeb50d77ff","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"e356c26d-748f-3fea-aaa9-35ba4054ee1e","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"27d49d3d-f104-306e-b8d1-ada0e3973acb","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"59116f6f-fc86-3206-80a1-873afe081073","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"bea0c19c-0c1e-3d31-ab4d-dda10370bd37","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"57ee798c-ebfa-3b42-9818-10132c6f964d","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas b27585aa-f518-35a7-9187-a94080f81620

- Exemplaire : `79e82586-d846-371e-b56f-ba8a8906f42e`
- Famille privée : RETARD_INTEGRATION ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"7f25c1d1-d6be-3735-8fe7-9ea9acb04dce","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"e1a211a6-31eb-3ebe-bec6-49b9df773049","physicalStatus":"ON_LOAN","physicalSiteId":"NORD","physicalLocationId":null,"projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Recontrôler après la fenêtre de traitement"]}`
- État projeté : `{"copyId":"79e82586-d846-371e-b56f-ba8a8906f42e","status":"ON_LOAN","siteId":"NORD","locationId":null,"sourceEventIds":["e1a211a6-31eb-3ebe-bec6-49b9df773049","bf3fcbde-e72a-30bf-8e3b-c83288301698"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 60c74db3-b2bc-34c3-9572-8b1d46d59724 | 2026-06-01T20:17:29Z | 2026-06-01T20:17:39Z | `{"operationId":"5f5d2f2a-24ed-3bc8-bdc4-fb84329c08fe","correlationId":"02e8dc7e-ce25-319f-9a99-fb1cd26ba996","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"c59cb77c-d625-356f-94cd-7c2f8c9a5a1f"}` |
| IntegrationProcessed | 6e0cdd41-e71d-3857-bd1d-83232c38cea6 | 2026-06-02T20:17:29Z | 2026-06-02T20:17:39Z | `{"originalEventId":"60c74db3-b2bc-34c3-9572-8b1d46d59724","correlationId":"02e8dc7e-ce25-319f-9a99-fb1cd26ba996","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 9e287fba-f770-3a63-b11c-14109c034a8b | 2026-07-31T20:17:29Z | 2026-07-31T20:17:39Z | `{"operationId":"81e1f90e-4c7d-36ba-afe1-98e8eefb1092","correlationId":"9e27a087-e625-3a3d-97c0-b50b8d26ce2a","siteId":"NORD","locationId":null,"loanId":"48bf6150-57d3-3473-8e95-8194eadff4a4"}` |
| IntegrationProcessed | 051bc06b-7e9c-3893-9c2b-84a1bb842d39 | 2026-07-31T20:18:29Z | 2026-07-31T20:18:39Z | `{"originalEventId":"9e287fba-f770-3a63-b11c-14109c034a8b","correlationId":"9e27a087-e625-3a3d-97c0-b50b8d26ce2a","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | e1092be3-7f0b-3aa9-af6d-9927bf1eec79 | 2026-08-20T20:17:29Z | 2026-08-20T20:17:39Z | `{"operationId":"b9319f62-05c2-3a22-b641-85760ebde2e2","correlationId":"f265e974-c592-3b24-9607-62dbffa115c7","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"48bf6150-57d3-3473-8e95-8194eadff4a4"}` |
| ReturnReceived | 57c5a86e-99e5-3509-b9ce-721f83a65c72 | 2026-08-20T20:17:59Z | 2026-08-20T20:18:09Z | `{"operationId":"ac85dd9c-f51f-37b3-b5bc-f19a573150ec","correlationId":"8e09f87c-feda-3b08-9a5c-ee5316e65c4d","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"48bf6150-57d3-3473-8e95-8194eadff4a4"}` |
| IntegrationProcessed | 28893ac3-191f-340b-baa5-520bdcf97349 | 2026-08-21T20:17:29Z | 2026-08-21T20:17:39Z | `{"originalEventId":"57c5a86e-99e5-3509-b9ce-721f83a65c72","correlationId":"8e09f87c-feda-3b08-9a5c-ee5316e65c4d","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | e1a211a6-31eb-3ebe-bec6-49b9df773049 | 2026-09-30T11:51:24Z | 2026-09-30T11:51:34Z | `{"operationId":"a17f893b-6d09-3eca-8053-fb63db7c4146","correlationId":"375e59c0-5122-3964-baf6-70a0404ad022","siteId":"NORD","locationId":null,"loanId":"8d653026-6584-3858-a215-3dcecbba594c"}` |
| IntegrationProcessed | cda4ef13-ee71-3179-b3da-d88c63cba479 | 2026-09-30T11:51:54Z | 2026-09-30T11:52:04Z | `{"originalEventId":"e1a211a6-31eb-3ebe-bec6-49b9df773049","correlationId":"375e59c0-5122-3964-baf6-70a0404ad022","outcome":"QUEUED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1}` |
| IntegrationProcessed | bf3fcbde-e72a-30bf-8e3b-c83288301698 | 2026-09-30T11:52:54Z | 2026-09-30T11:53:04Z | `{"originalEventId":"e1a211a6-31eb-3ebe-bec6-49b9df773049","correlationId":"375e59c0-5122-3964-baf6-70a0404ad022","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |

**observations**
- `{"observationId":"334d3862-d9d3-330e-8442-99e46242740e","copyId":"79e82586-d846-371e-b56f-ba8a8906f42e","locationId":"5b61a611-a926-3ed2-82dc-fc122118757c","scopeId":"cc4a60dc-5c2f-3495-a1cf-2138ce8c133e","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**sourceAvailability**
- `{"copyId":"79e82586-d846-371e-b56f-ba8a8906f42e","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"79e82586-d846-371e-b56f-ba8a8906f42e","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"79e82586-d846-371e-b56f-ba8a8906f42e","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"79e82586-d846-371e-b56f-ba8a8906f42e","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"79e82586-d846-371e-b56f-ba8a8906f42e","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"79e82586-d846-371e-b56f-ba8a8906f42e","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"79e82586-d846-371e-b56f-ba8a8906f42e","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"79e82586-d846-371e-b56f-ba8a8906f42e"},{"kind":"sourceEvent","id":"60c74db3-b2bc-34c3-9572-8b1d46d59724"},{"kind":"sourceEvent","id":"6e0cdd41-e71d-3857-bd1d-83232c38cea6"},{"kind":"sourceEvent","id":"9e287fba-f770-3a63-b11c-14109c034a8b"},{"kind":"sourceEvent","id":"051bc06b-7e9c-3893-9c2b-84a1bb842d39"},{"kind":"sourceEvent","id":"e1092be3-7f0b-3aa9-af6d-9927bf1eec79"},{"kind":"sourceEvent","id":"57c5a86e-99e5-3509-b9ce-721f83a65c72"},{"kind":"sourceEvent","id":"28893ac3-191f-340b-baa5-520bdcf97349"},{"kind":"sourceEvent","id":"e1a211a6-31eb-3ebe-bec6-49b9df773049"},{"kind":"sourceEvent","id":"cda4ef13-ee71-3179-b3da-d88c63cba479"},{"kind":"sourceEvent","id":"bf3fcbde-e72a-30bf-8e3b-c83288301698"},{"kind":"observation","id":"334d3862-d9d3-330e-8442-99e46242740e"},{"kind":"inventoryScope","id":"cc4a60dc-5c2f-3495-a1cf-2138ce8c133e"},{"kind":"sourceAvailability","id":"79e82586-d846-371e-b56f-ba8a8906f42e/LOANS"},{"kind":"sourceAvailability","id":"79e82586-d846-371e-b56f-ba8a8906f42e/RETURNS"},{"kind":"sourceAvailability","id":"79e82586-d846-371e-b56f-ba8a8906f42e/RECEIPTS"},{"kind":"sourceAvailability","id":"79e82586-d846-371e-b56f-ba8a8906f42e/TRANSFERS"},{"kind":"sourceAvailability","id":"79e82586-d846-371e-b56f-ba8a8906f42e/REPAIRS"},{"kind":"sourceAvailability","id":"79e82586-d846-371e-b56f-ba8a8906f42e/INTEGRATIONS"},{"kind":"sourceAvailability","id":"79e82586-d846-371e-b56f-ba8a8906f42e/INVENTORY"}]`

- Session : `{"scopeId":"cc4a60dc-5c2f-3495-a1cf-2138ce8c133e","locationId":"5b61a611-a926-3ed2-82dc-fc122118757c","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

## Cas b66b60f1-7696-320b-85ec-bd730240ea3a

- Exemplaire : `985ba86f-a78a-3064-bf1c-c1c3363f63a3`
- Famille privée : INVENTAIRE_INCOMPLET ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"66ac3d45-26a3-33e4-a30c-f123513a32bb","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["COLLECTION_INTERRUPTED"],"mechanism":"Interruption de collecte ; exemplaire resté au rayon.","physicalStatus":"ON_SHELF","physicalSiteId":"CENTRE","physicalLocationId":"4a5364f2-388b-351d-91f2-d1364d119042","evidenceMask":"Collecte indisponible"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"L'absence de lecture ne démontre ni la destination, ni une perte, ni un vol ; données insuffisantes pour attribuer une cause.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État projeté : `{"copyId":"985ba86f-a78a-3064-bf1c-c1c3363f63a3","status":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","sourceEventIds":["c09ce654-0985-360b-92a3-222b414faf99","071cc821-7058-3dc5-b66c-146e130ae0b3"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | db8e9429-5972-3d48-91f4-3b995990e7c8 | 2026-06-02T06:15:47Z | 2026-06-02T06:15:57Z | `{"operationId":"a317a6f2-ec67-3857-ae71-fea71f2712a4","correlationId":"f6ff38a6-42a1-36aa-81b7-5b80c7f7d744","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"75a6573f-ac38-39f4-9a4e-9b7c5f124921"}` |
| IntegrationProcessed | 70162fc9-46dd-37fe-bb47-36c5737ce4e9 | 2026-06-03T06:15:47Z | 2026-06-03T06:15:57Z | `{"originalEventId":"db8e9429-5972-3d48-91f4-3b995990e7c8","correlationId":"f6ff38a6-42a1-36aa-81b7-5b80c7f7d744","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | a39dd380-f0a4-3919-8cba-84494b1858b1 | 2026-08-01T06:15:47Z | 2026-08-01T06:15:57Z | `{"operationId":"8b2fb579-72a9-3a3c-be4f-4acf8362deff","correlationId":"5dac44d2-249a-38d5-a7bb-d4d0fb9272c2","siteId":"CENTRE","locationId":null,"loanId":"a405fa5a-8551-38b7-8591-34149d032099"}` |
| IntegrationProcessed | 83ebdbf0-7ae5-3991-b758-cdab3f2d1e54 | 2026-08-01T06:16:47Z | 2026-08-01T06:16:57Z | `{"originalEventId":"a39dd380-f0a4-3919-8cba-84494b1858b1","correlationId":"5dac44d2-249a-38d5-a7bb-d4d0fb9272c2","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 42ae0342-acd6-38a2-9537-f00442779446 | 2026-08-21T06:15:47Z | 2026-08-21T06:15:57Z | `{"operationId":"73077fa3-5cf3-333c-b6dc-24f86f1c5772","correlationId":"cb593bd1-bfe8-3e97-9a60-e09b47e597ca","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"a405fa5a-8551-38b7-8591-34149d032099"}` |
| ReturnReceived | c09ce654-0985-360b-92a3-222b414faf99 | 2026-08-21T06:16:17Z | 2026-08-21T06:16:27Z | `{"operationId":"e685e530-fd81-3529-a2a9-1a7e0413d990","correlationId":"c36259a2-98b0-346c-baf7-ed8a9cc73cfe","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"a405fa5a-8551-38b7-8591-34149d032099"}` |
| IntegrationProcessed | 071cc821-7058-3dc5-b66c-146e130ae0b3 | 2026-08-22T06:15:47Z | 2026-08-22T06:15:57Z | `{"originalEventId":"c09ce654-0985-360b-92a3-222b414faf99","correlationId":"c36259a2-98b0-346c-baf7-ed8a9cc73cfe","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |

**observations**

**sourceAvailability**
- `{"copyId":"985ba86f-a78a-3064-bf1c-c1c3363f63a3","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"985ba86f-a78a-3064-bf1c-c1c3363f63a3","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"985ba86f-a78a-3064-bf1c-c1c3363f63a3","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"985ba86f-a78a-3064-bf1c-c1c3363f63a3","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"985ba86f-a78a-3064-bf1c-c1c3363f63a3","source":"INVENTORY","status":"UNAVAILABLE","completeFrom":null,"completeTo":null}`
- `{"copyId":"985ba86f-a78a-3064-bf1c-c1c3363f63a3","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"985ba86f-a78a-3064-bf1c-c1c3363f63a3","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**
- `{"noteId":"bbeb2bb3-3025-3d7f-8648-2aa53d5c69e6","copyId":"985ba86f-a78a-3064-bf1c-c1c3363f63a3","text":"Le terminal de collecte a cessé de répondre pendant le passage ; aucune conclusion sur les exemplaires non lus.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"985ba86f-a78a-3064-bf1c-c1c3363f63a3"},{"kind":"sourceEvent","id":"db8e9429-5972-3d48-91f4-3b995990e7c8"},{"kind":"sourceEvent","id":"70162fc9-46dd-37fe-bb47-36c5737ce4e9"},{"kind":"sourceEvent","id":"a39dd380-f0a4-3919-8cba-84494b1858b1"},{"kind":"sourceEvent","id":"83ebdbf0-7ae5-3991-b758-cdab3f2d1e54"},{"kind":"sourceEvent","id":"42ae0342-acd6-38a2-9537-f00442779446"},{"kind":"sourceEvent","id":"c09ce654-0985-360b-92a3-222b414faf99"},{"kind":"sourceEvent","id":"071cc821-7058-3dc5-b66c-146e130ae0b3"},{"kind":"sourceAvailability","id":"985ba86f-a78a-3064-bf1c-c1c3363f63a3/LOANS"},{"kind":"sourceAvailability","id":"985ba86f-a78a-3064-bf1c-c1c3363f63a3/RETURNS"},{"kind":"sourceAvailability","id":"985ba86f-a78a-3064-bf1c-c1c3363f63a3/RECEIPTS"},{"kind":"sourceAvailability","id":"985ba86f-a78a-3064-bf1c-c1c3363f63a3/TRANSFERS"},{"kind":"sourceAvailability","id":"985ba86f-a78a-3064-bf1c-c1c3363f63a3/REPAIRS"},{"kind":"sourceAvailability","id":"985ba86f-a78a-3064-bf1c-c1c3363f63a3/INTEGRATIONS"},{"kind":"sourceAvailability","id":"985ba86f-a78a-3064-bf1c-c1c3363f63a3/INVENTORY"},{"kind":"operatorNote","id":"bbeb2bb3-3025-3d7f-8648-2aa53d5c69e6"},{"kind":"inventoryScope","id":"8021dca8-65fe-3cab-985e-d4fb9df5c308"},{"kind":"inventoryScope","id":"b0027657-6abb-37d2-8bc4-9341357dfe79"},{"kind":"inventoryScope","id":"4757f5eb-f104-3413-b118-51a6951a650c"},{"kind":"inventoryScope","id":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592"},{"kind":"inventoryScope","id":"40e94818-3a62-34a4-b461-398d7b9ffca2"},{"kind":"inventoryScope","id":"ed60c0be-b218-3f9e-8aa8-69c16f76c22e"},{"kind":"inventoryScope","id":"ed60c0be-b218-3f9e-8aa8-69c16f76c22e"}]`

- Session : `{"scopeId":"8021dca8-65fe-3cab-985e-d4fb9df5c308","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b0027657-6abb-37d2-8bc4-9341357dfe79","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4757f5eb-f104-3413-b118-51a6951a650c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"40e94818-3a62-34a4-b461-398d7b9ffca2","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"ed60c0be-b218-3f9e-8aa8-69c16f76c22e","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"ed60c0be-b218-3f9e-8aa8-69c16f76c22e","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas b76c9b93-bd45-3a7b-9c20-82078c7f33cd

- Exemplaire : `362fba0a-74b1-3cd6-b2bd-17d98e837a72`
- Famille privée : LOCALISATION ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"3c61a757-d1e2-3cd4-bff8-f30c266fd20d","kind":"COUNT_UNCERTAIN","expectedValue":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"mechanism":"Déplacement physique sans modification de l'affectation ; aucune attribution à un opérateur.","activePerturbations":["LOCATION_CHANGE_UNPROJECTED"],"physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":"5b61a611-a926-3ed2-82dc-fc122118757c"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"Identité du scan ambiguë ; ne pas attribuer un emplacement physique certain.","forbiddenClaims":["Vol","Perte définitive","Responsabilité d'un opérateur"],"recommendedChecks":["Refaire le scan et vérifier le code-barres"]}`
- État projeté : `{"copyId":"362fba0a-74b1-3cd6-b2bd-17d98e837a72","status":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","sourceEventIds":["a5e88b6b-942d-35a1-86a8-3834ad7b6216","40df4c96-f592-3717-a57c-48f99385b399"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | ecd6fecc-cc82-33fe-8acb-7fd8bccb1529 | 2026-06-01T22:18:26Z | 2026-06-01T22:18:36Z | `{"operationId":"b538edea-bb6d-3f8c-970f-e9a08290cdf5","correlationId":"f104d303-5401-3e38-aaa7-09d1cd0c9028","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"1f6fa003-2fa0-3a10-abde-ffb7c1227098"}` |
| IntegrationProcessed | 18f6e142-9698-32df-813a-d8ba53b1cd51 | 2026-06-02T22:18:26Z | 2026-06-02T22:18:36Z | `{"originalEventId":"ecd6fecc-cc82-33fe-8acb-7fd8bccb1529","correlationId":"f104d303-5401-3e38-aaa7-09d1cd0c9028","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 3d5812f4-f54a-3b57-a27f-7ade52926e00 | 2026-07-31T22:18:26Z | 2026-07-31T22:18:36Z | `{"operationId":"0577fdea-b5b4-337e-8d14-db74dead5905","correlationId":"fc83a2f3-666d-3493-a38e-3be2305b4659","siteId":"NORD","locationId":null,"loanId":"901154ac-2c88-329d-a257-e511bd429fe5"}` |
| IntegrationProcessed | bcb26930-520c-3e71-b78c-be45ef828d72 | 2026-07-31T22:19:26Z | 2026-07-31T22:19:36Z | `{"originalEventId":"3d5812f4-f54a-3b57-a27f-7ade52926e00","correlationId":"fc83a2f3-666d-3493-a38e-3be2305b4659","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | ef43d9fd-e7f9-3c56-9db7-c06be714a425 | 2026-08-20T22:18:26Z | 2026-08-20T22:18:36Z | `{"operationId":"b2cdbee6-c828-3328-9a86-72fbfc311461","correlationId":"144d1e06-8b65-39b3-a4b3-a29c3f814705","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"901154ac-2c88-329d-a257-e511bd429fe5"}` |
| ReturnReceived | a5e88b6b-942d-35a1-86a8-3834ad7b6216 | 2026-08-20T22:18:56Z | 2026-08-20T22:19:06Z | `{"operationId":"9686955b-c9ca-330b-96e7-76dec72f7e09","correlationId":"22fb1df0-b9cb-3886-984f-b3b45ecd89fe","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"901154ac-2c88-329d-a257-e511bd429fe5"}` |
| IntegrationProcessed | 40df4c96-f592-3717-a57c-48f99385b399 | 2026-08-21T22:18:26Z | 2026-08-21T22:18:36Z | `{"originalEventId":"a5e88b6b-942d-35a1-86a8-3834ad7b6216","correlationId":"22fb1df0-b9cb-3886-984f-b3b45ecd89fe","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |

**observations**
- `{"observationId":"1860f660-5874-3173-87fb-f75b6589571c","copyId":"362fba0a-74b1-3cd6-b2bd-17d98e837a72","locationId":"5b61a611-a926-3ed2-82dc-fc122118757c","scopeId":"b3b3129a-10c5-3848-8a54-1b25731fe7d6","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**sourceAvailability**
- `{"copyId":"362fba0a-74b1-3cd6-b2bd-17d98e837a72","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"362fba0a-74b1-3cd6-b2bd-17d98e837a72","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"362fba0a-74b1-3cd6-b2bd-17d98e837a72","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"362fba0a-74b1-3cd6-b2bd-17d98e837a72","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"362fba0a-74b1-3cd6-b2bd-17d98e837a72","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"362fba0a-74b1-3cd6-b2bd-17d98e837a72","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"362fba0a-74b1-3cd6-b2bd-17d98e837a72","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"observation","id":"1860f660-5874-3173-87fb-f75b6589571c"},{"kind":"systemState","id":"362fba0a-74b1-3cd6-b2bd-17d98e837a72"}]`

## Cas b7df0cd1-baeb-36cc-988a-409edfece6cc

- Exemplaire : `5f2dcd97-3737-301c-ad7e-f420a4f140ef`
- Famille privée : DEUX_CAUSES ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"51169fc0-91ff-3a61-abc1-46cbaf28681f","kind":"STATUS_MISMATCH","expectedValue":"IN_REPAIR","observedValue":"IN_REPAIR"},{"symptomId":"fac95bf7-f004-37ff-8269-9e55882e57d7","kind":"LOCATION_MISMATCH","expectedValue":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","observedValue":"b8600ac8-b5d3-3580-b3c5-71a711df1a50"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["REPAIR_LOCATION_CHANGED"],"hiddenSourceEvents":[],"mechanism":"Réparation engagée puis déplacement distinct de l'atelier vers la réserve sans mise à jour de l'affectation de réparation.","businessEventId":"aff1dd4e-6dff-3df1-aa98-81e91281fc87","physicalStatus":"IN_REPAIR","physicalSiteId":"CENTRE","physicalLocationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_LOCATION_MISMATCH"],"explanation":"Réparation correctement projetée après reprise ; seul le déplacement hors atelier reste étayé.","forbiddenClaims":["Deux causes confirmées par un scan ambigu","Vol"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"5f2dcd97-3737-301c-ad7e-f420a4f140ef","status":"IN_REPAIR","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","sourceEventIds":["aff1dd4e-6dff-3df1-aa98-81e91281fc87","2e334476-4e85-3fce-aa9f-d275f6896de4"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | fc3abfd5-cf6d-3628-b445-4be3b9d0d78f | 2026-06-01T14:30:34Z | 2026-06-01T14:30:44Z | `{"operationId":"a8b804ec-3180-3781-9c5c-ebd74d72fc21","correlationId":"b1c6450f-fa2d-3e1f-95d0-5845e357ddad","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"a26f8171-ad9c-38ab-a890-f406839105a7"}` |
| IntegrationProcessed | 91c3ca80-4f4a-3831-8405-41f7cfb1c9fd | 2026-06-02T14:30:34Z | 2026-06-02T14:30:44Z | `{"originalEventId":"fc3abfd5-cf6d-3628-b445-4be3b9d0d78f","correlationId":"b1c6450f-fa2d-3e1f-95d0-5845e357ddad","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 1ede9782-11df-30ce-8f4b-57c4e4b193f4 | 2026-07-31T14:30:34Z | 2026-07-31T14:30:44Z | `{"operationId":"6316181a-3801-3ec5-8ceb-568d11583a80","correlationId":"075d8703-6cd2-3a1e-9590-0ecf150ea798","siteId":"CENTRE","locationId":null,"loanId":"d889cf2e-265e-36f0-b569-662e56022715"}` |
| IntegrationProcessed | f6b21fa4-868f-3585-9fd3-3d66321e5e0e | 2026-07-31T14:31:34Z | 2026-07-31T14:31:44Z | `{"originalEventId":"1ede9782-11df-30ce-8f4b-57c4e4b193f4","correlationId":"075d8703-6cd2-3a1e-9590-0ecf150ea798","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | b0045004-9ee9-38be-b7d1-a62f00fff1b7 | 2026-08-20T14:30:34Z | 2026-08-20T14:30:44Z | `{"operationId":"456c162d-af13-32e1-bf24-f718b2fafa36","correlationId":"28d8c73a-d725-3473-b2ec-7c091ceca9cb","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"d889cf2e-265e-36f0-b569-662e56022715"}` |
| ReturnReceived | 3af16f08-d9a0-3a6c-b3e1-e14625ea2e37 | 2026-08-20T14:31:04Z | 2026-08-20T14:31:14Z | `{"operationId":"ee6c3f49-c643-328e-99f8-c37667ba65a8","correlationId":"dbdadad9-d88e-3f07-a164-577b273f4529","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"d889cf2e-265e-36f0-b569-662e56022715"}` |
| IntegrationProcessed | 61417a7a-25aa-3fb4-a0fa-87454ef38c72 | 2026-08-21T14:30:34Z | 2026-08-21T14:30:44Z | `{"originalEventId":"3af16f08-d9a0-3a6c-b3e1-e14625ea2e37","correlationId":"dbdadad9-d88e-3f07-a164-577b273f4529","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| RepairStarted | aff1dd4e-6dff-3df1-aa98-81e91281fc87 | 2026-09-27T06:17:56Z | 2026-09-27T06:18:06Z | `{"operationId":"e6046cd2-f597-3ca5-92fc-3392b8c5f475","correlationId":"be887d45-215b-3432-92c0-47abc040e7f1","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","repairId":"1a9a27e5-478d-30fe-93f6-7b1da7755f90"}` |
| IntegrationProcessed | 23f31027-b475-356d-bb4f-5b6f4a101e9d | 2026-09-27T06:18:26Z | 2026-09-27T06:18:36Z | `{"originalEventId":"aff1dd4e-6dff-3df1-aa98-81e91281fc87","correlationId":"be887d45-215b-3432-92c0-47abc040e7f1","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| IntegrationProcessed | 2e334476-4e85-3fce-aa9f-d275f6896de4 | 2026-09-27T06:19:26Z | 2026-09-27T06:19:36Z | `{"originalEventId":"aff1dd4e-6dff-3df1-aa98-81e91281fc87","correlationId":"be887d45-215b-3432-92c0-47abc040e7f1","outcome":"APPLIED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be"}` |

**observations**
- `{"observationId":"61ca5564-9f38-3c23-a43e-801affc4405b","copyId":"5f2dcd97-3737-301c-ad7e-f420a4f140ef","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","scopeId":"09aa3342-4271-32c5-b418-38aa137984c1","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"5f2dcd97-3737-301c-ad7e-f420a4f140ef","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5f2dcd97-3737-301c-ad7e-f420a4f140ef","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5f2dcd97-3737-301c-ad7e-f420a4f140ef","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5f2dcd97-3737-301c-ad7e-f420a4f140ef","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5f2dcd97-3737-301c-ad7e-f420a4f140ef","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5f2dcd97-3737-301c-ad7e-f420a4f140ef","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5f2dcd97-3737-301c-ad7e-f420a4f140ef","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"5f2dcd97-3737-301c-ad7e-f420a4f140ef"},{"kind":"sourceEvent","id":"fc3abfd5-cf6d-3628-b445-4be3b9d0d78f"},{"kind":"sourceEvent","id":"91c3ca80-4f4a-3831-8405-41f7cfb1c9fd"},{"kind":"sourceEvent","id":"1ede9782-11df-30ce-8f4b-57c4e4b193f4"},{"kind":"sourceEvent","id":"f6b21fa4-868f-3585-9fd3-3d66321e5e0e"},{"kind":"sourceEvent","id":"b0045004-9ee9-38be-b7d1-a62f00fff1b7"},{"kind":"sourceEvent","id":"3af16f08-d9a0-3a6c-b3e1-e14625ea2e37"},{"kind":"sourceEvent","id":"61417a7a-25aa-3fb4-a0fa-87454ef38c72"},{"kind":"sourceEvent","id":"aff1dd4e-6dff-3df1-aa98-81e91281fc87"},{"kind":"sourceEvent","id":"23f31027-b475-356d-bb4f-5b6f4a101e9d"},{"kind":"sourceEvent","id":"2e334476-4e85-3fce-aa9f-d275f6896de4"},{"kind":"observation","id":"61ca5564-9f38-3c23-a43e-801affc4405b"},{"kind":"inventoryScope","id":"09aa3342-4271-32c5-b418-38aa137984c1"},{"kind":"sourceAvailability","id":"5f2dcd97-3737-301c-ad7e-f420a4f140ef/LOANS"},{"kind":"sourceAvailability","id":"5f2dcd97-3737-301c-ad7e-f420a4f140ef/RETURNS"},{"kind":"sourceAvailability","id":"5f2dcd97-3737-301c-ad7e-f420a4f140ef/RECEIPTS"},{"kind":"sourceAvailability","id":"5f2dcd97-3737-301c-ad7e-f420a4f140ef/TRANSFERS"},{"kind":"sourceAvailability","id":"5f2dcd97-3737-301c-ad7e-f420a4f140ef/REPAIRS"},{"kind":"sourceAvailability","id":"5f2dcd97-3737-301c-ad7e-f420a4f140ef/INTEGRATIONS"},{"kind":"sourceAvailability","id":"5f2dcd97-3737-301c-ad7e-f420a4f140ef/INVENTORY"},{"kind":"inventoryScope","id":"fc22847f-af8c-366f-be86-355e86e7e988"},{"kind":"inventoryScope","id":"e40f4833-d76c-3015-95f9-c5c4c5316123"},{"kind":"inventoryScope","id":"dc38a3c7-811a-3c7e-9d52-13be1b9914cd"},{"kind":"inventoryScope","id":"9be0d6d0-cc8d-3d92-92f6-b5291f868628"},{"kind":"inventoryScope","id":"12e94e75-b47d-323a-913a-d261320a99e1"},{"kind":"inventoryScope","id":"64d8a581-eb79-3896-a532-b4a4254d76fe"},{"kind":"inventoryScope","id":"f67d2862-9684-34ec-92de-3f36a2e82aa0"},{"kind":"inventoryScope","id":"171f3784-92b9-3ed7-9ea2-6a546847ae52"},{"kind":"inventoryScope","id":"1f9fddc4-161a-3ebe-9bdf-116dfb2e29eb"},{"kind":"inventoryScope","id":"28dbc24e-0180-3464-be43-570a9a4fbb69"},{"kind":"inventoryScope","id":"dded33db-7a0a-3ef8-8121-8d342e50d362"}]`

- Session : `{"scopeId":"09aa3342-4271-32c5-b418-38aa137984c1","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"fc22847f-af8c-366f-be86-355e86e7e988","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"e40f4833-d76c-3015-95f9-c5c4c5316123","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"dc38a3c7-811a-3c7e-9d52-13be1b9914cd","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9be0d6d0-cc8d-3d92-92f6-b5291f868628","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"12e94e75-b47d-323a-913a-d261320a99e1","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"64d8a581-eb79-3896-a532-b4a4254d76fe","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"f67d2862-9684-34ec-92de-3f36a2e82aa0","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"171f3784-92b9-3ed7-9ea2-6a546847ae52","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"1f9fddc4-161a-3ebe-9bdf-116dfb2e29eb","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"28dbc24e-0180-3464-be43-570a9a4fbb69","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"dded33db-7a0a-3ef8-8121-8d342e50d362","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas b8627a9c-9f52-35d2-a2d0-97fcbdf4789c

- Exemplaire : `55830823-0394-3c9c-9ff5-23b0bf782b62`
- Famille privée : RETOUR_NON_PROJETE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"b0d6d872-0b2e-3c40-8739-95eefc1bff04","kind":"STATUS_MISMATCH","expectedValue":"ON_LOAN","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"3262e9b6-a96c-3cd5-bd88-508517890e82","physicalStatus":"RECEIVING","physicalSiteId":"SUD","physicalLocationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["RETURN_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"55830823-0394-3c9c-9ff5-23b0bf782b62","status":"ON_LOAN","siteId":"SUD","locationId":null,"sourceEventIds":["1bf2f1e0-a19d-3952-9e27-8dc360d8ebf5","4922d1d2-d46d-355d-b535-ce0f2eecd278"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 1cf8b38b-fa2e-3c9b-a3d7-4ccc8ddd6973 | 2026-06-01T15:31:07Z | 2026-06-01T15:31:17Z | `{"operationId":"3621662e-4e06-39ec-b3ac-d71601320eab","correlationId":"20a3e046-e380-3f83-9c38-3c1385188c69","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"76f57393-4da6-36e3-9a99-fa52ad351a53"}` |
| IntegrationProcessed | d84425df-e97f-3632-9612-6c3d676e1153 | 2026-06-02T15:31:07Z | 2026-06-02T15:31:17Z | `{"originalEventId":"1cf8b38b-fa2e-3c9b-a3d7-4ccc8ddd6973","correlationId":"20a3e046-e380-3f83-9c38-3c1385188c69","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | 8868032b-5a94-30f5-b6fb-40aec4ff196c | 2026-07-31T15:31:07Z | 2026-07-31T15:31:17Z | `{"operationId":"55841b2d-056e-31ac-bf23-ef8fa536da05","correlationId":"1ffc2005-8a64-3633-a55e-5f724ce9b718","siteId":"SUD","locationId":null,"loanId":"e2ad8d39-c23d-32a4-b615-1a789cedcd21"}` |
| IntegrationProcessed | 7d8dcf63-1f8a-3aa4-b7cb-5114f0797a32 | 2026-07-31T15:32:07Z | 2026-07-31T15:32:17Z | `{"originalEventId":"8868032b-5a94-30f5-b6fb-40aec4ff196c","correlationId":"1ffc2005-8a64-3633-a55e-5f724ce9b718","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | e71f94f0-b3f1-3474-9c1f-c225c4663061 | 2026-08-20T15:31:07Z | 2026-08-20T15:31:17Z | `{"operationId":"ada0df46-463d-3c26-83bd-6112c2840404","correlationId":"1ae7678d-ecea-34ea-9878-0afc2db1ba4c","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"e2ad8d39-c23d-32a4-b615-1a789cedcd21"}` |
| ReturnReceived | 3fb7e9cb-b0c1-3416-939f-9549c814dfc6 | 2026-08-20T15:31:37Z | 2026-08-20T15:31:47Z | `{"operationId":"79a8265a-50a1-3fa6-82b3-24c3a4b0398f","correlationId":"9d94e5ec-3fbf-3ea8-93de-027269bdb357","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"e2ad8d39-c23d-32a4-b615-1a789cedcd21"}` |
| IntegrationProcessed | 6be387a1-3b73-3eb2-a18f-6d57092d2695 | 2026-08-21T15:31:07Z | 2026-08-21T15:31:17Z | `{"originalEventId":"3fb7e9cb-b0c1-3416-939f-9549c814dfc6","correlationId":"9d94e5ec-3fbf-3ea8-93de-027269bdb357","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | 1bf2f1e0-a19d-3952-9e27-8dc360d8ebf5 | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"49777a0e-0b81-3345-95b2-2a780491adb6","correlationId":"b60a8de1-0a6a-35cd-b8bb-4dca62197ad6","siteId":"SUD","locationId":null,"loanId":"84b4d48a-a3f8-3afc-b1a6-53889c7779a2"}` |
| IntegrationProcessed | 4922d1d2-d46d-355d-b535-ce0f2eecd278 | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"1bf2f1e0-a19d-3952-9e27-8dc360d8ebf5","correlationId":"b60a8de1-0a6a-35cd-b8bb-4dca62197ad6","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | 225c2207-f130-3f2b-86aa-1e61c50c861c | 2026-09-27T07:29:12Z | 2026-09-27T07:29:22Z | `{"operationId":"25b2b921-14f9-38ff-bd6e-ea5b7fe77f3f","correlationId":"e5ce6957-1beb-3def-92d9-ada5fa26fc9a","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"84b4d48a-a3f8-3afc-b1a6-53889c7779a2"}` |
| ReturnReceived | 3262e9b6-a96c-3cd5-bd88-508517890e82 | 2026-09-27T07:29:42Z | 2026-09-27T07:29:52Z | `{"operationId":"110b3fd5-fe12-3895-a17c-d0c5c16ed326","correlationId":"34521bc1-2758-3a21-915c-12598ae760fd","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"84b4d48a-a3f8-3afc-b1a6-53889c7779a2"}` |
| IntegrationProcessed | 1c484a77-0890-3420-b5be-fb4406375874 | 2026-09-27T07:30:12Z | 2026-09-27T07:30:22Z | `{"originalEventId":"3262e9b6-a96c-3cd5-bd88-508517890e82","correlationId":"34521bc1-2758-3a21-915c-12598ae760fd","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**observations**
- `{"observationId":"2ea4dde8-e2ce-38ad-a140-9ab38cc98f2b","copyId":"55830823-0394-3c9c-9ff5-23b0bf782b62","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","scopeId":"1383d223-33b0-3821-a009-f6e5838b32e8","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"55830823-0394-3c9c-9ff5-23b0bf782b62","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"55830823-0394-3c9c-9ff5-23b0bf782b62","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"55830823-0394-3c9c-9ff5-23b0bf782b62","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"55830823-0394-3c9c-9ff5-23b0bf782b62","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"55830823-0394-3c9c-9ff5-23b0bf782b62","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"55830823-0394-3c9c-9ff5-23b0bf782b62","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"55830823-0394-3c9c-9ff5-23b0bf782b62","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"55830823-0394-3c9c-9ff5-23b0bf782b62"},{"kind":"sourceEvent","id":"1cf8b38b-fa2e-3c9b-a3d7-4ccc8ddd6973"},{"kind":"sourceEvent","id":"d84425df-e97f-3632-9612-6c3d676e1153"},{"kind":"sourceEvent","id":"8868032b-5a94-30f5-b6fb-40aec4ff196c"},{"kind":"sourceEvent","id":"7d8dcf63-1f8a-3aa4-b7cb-5114f0797a32"},{"kind":"sourceEvent","id":"e71f94f0-b3f1-3474-9c1f-c225c4663061"},{"kind":"sourceEvent","id":"3fb7e9cb-b0c1-3416-939f-9549c814dfc6"},{"kind":"sourceEvent","id":"6be387a1-3b73-3eb2-a18f-6d57092d2695"},{"kind":"sourceEvent","id":"1bf2f1e0-a19d-3952-9e27-8dc360d8ebf5"},{"kind":"sourceEvent","id":"4922d1d2-d46d-355d-b535-ce0f2eecd278"},{"kind":"sourceEvent","id":"225c2207-f130-3f2b-86aa-1e61c50c861c"},{"kind":"sourceEvent","id":"3262e9b6-a96c-3cd5-bd88-508517890e82"},{"kind":"sourceEvent","id":"1c484a77-0890-3420-b5be-fb4406375874"},{"kind":"observation","id":"2ea4dde8-e2ce-38ad-a140-9ab38cc98f2b"},{"kind":"inventoryScope","id":"1383d223-33b0-3821-a009-f6e5838b32e8"},{"kind":"sourceAvailability","id":"55830823-0394-3c9c-9ff5-23b0bf782b62/LOANS"},{"kind":"sourceAvailability","id":"55830823-0394-3c9c-9ff5-23b0bf782b62/RETURNS"},{"kind":"sourceAvailability","id":"55830823-0394-3c9c-9ff5-23b0bf782b62/RECEIPTS"},{"kind":"sourceAvailability","id":"55830823-0394-3c9c-9ff5-23b0bf782b62/TRANSFERS"},{"kind":"sourceAvailability","id":"55830823-0394-3c9c-9ff5-23b0bf782b62/REPAIRS"},{"kind":"sourceAvailability","id":"55830823-0394-3c9c-9ff5-23b0bf782b62/INTEGRATIONS"},{"kind":"sourceAvailability","id":"55830823-0394-3c9c-9ff5-23b0bf782b62/INVENTORY"}]`

- Session : `{"scopeId":"1383d223-33b0-3821-a009-f6e5838b32e8","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas bb05270f-7b9a-3a5e-bff0-fa56345a8cfe

- Exemplaire : `40907dce-2acf-3659-8c3f-f049da3396d7`
- Famille privée : RECEPTION_NON_INTEGREE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"5311f429-c0cc-39a1-ba77-7c0a58142ccd","kind":"UNEXPECTED_PRESENCE","expectedValue":"NOT_REGISTERED","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"634467f1-4d63-3133-9f3b-f100ad550600","copyId":"40907dce-2acf-3659-8c3f-f049da3396d7","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T06:46:38Z","recordedAt":"2026-09-27T06:46:48Z","payload":{"originalEventId":"46d8db29-8193-3957-afb1-3923e97396b3","correlationId":"17e3b012-a818-3d14-9566-6b148ae8fc94","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"46d8db29-8193-3957-afb1-3923e97396b3","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État projeté : `{"copyId":"40907dce-2acf-3659-8c3f-f049da3396d7","status":"NOT_REGISTERED","siteId":"CENTRE","locationId":null,"sourceEventIds":[]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 46d8db29-8193-3957-afb1-3923e97396b3 | 2026-09-27T06:46:08Z | 2026-09-27T06:46:18Z | `{"operationId":"293491f0-ae46-35ef-8ae1-a6cf2476b38a","correlationId":"17e3b012-a818-3d14-9566-6b148ae8fc94","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"1da98409-386c-31fe-a855-b4a75d702489"}` |

**observations**
- `{"observationId":"f85df255-15f1-3838-9e86-d270f55cef3b","copyId":"40907dce-2acf-3659-8c3f-f049da3396d7","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","scopeId":"03c2ead3-c284-3854-b5df-7e7d3216c4f7","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**sourceAvailability**
- `{"copyId":"40907dce-2acf-3659-8c3f-f049da3396d7","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"40907dce-2acf-3659-8c3f-f049da3396d7","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"40907dce-2acf-3659-8c3f-f049da3396d7","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"40907dce-2acf-3659-8c3f-f049da3396d7","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"40907dce-2acf-3659-8c3f-f049da3396d7","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"40907dce-2acf-3659-8c3f-f049da3396d7","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"40907dce-2acf-3659-8c3f-f049da3396d7","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"40907dce-2acf-3659-8c3f-f049da3396d7"},{"kind":"sourceEvent","id":"46d8db29-8193-3957-afb1-3923e97396b3"},{"kind":"observation","id":"f85df255-15f1-3838-9e86-d270f55cef3b"},{"kind":"inventoryScope","id":"03c2ead3-c284-3854-b5df-7e7d3216c4f7"},{"kind":"sourceAvailability","id":"40907dce-2acf-3659-8c3f-f049da3396d7/LOANS"},{"kind":"sourceAvailability","id":"40907dce-2acf-3659-8c3f-f049da3396d7/RETURNS"},{"kind":"sourceAvailability","id":"40907dce-2acf-3659-8c3f-f049da3396d7/RECEIPTS"},{"kind":"sourceAvailability","id":"40907dce-2acf-3659-8c3f-f049da3396d7/TRANSFERS"},{"kind":"sourceAvailability","id":"40907dce-2acf-3659-8c3f-f049da3396d7/REPAIRS"},{"kind":"sourceAvailability","id":"40907dce-2acf-3659-8c3f-f049da3396d7/INTEGRATIONS"},{"kind":"sourceAvailability","id":"40907dce-2acf-3659-8c3f-f049da3396d7/INVENTORY"}]`

- Session : `{"scopeId":"03c2ead3-c284-3854-b5df-7e7d3216c4f7","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas be9f053d-0059-33f9-ae22-ea925b05e1c5

- Exemplaire : `f3af51d6-903e-3820-b056-5f72174e8077`
- Famille privée : ABSENCE_INEXPLIQUEE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"e77af3a9-b35b-38bb-a911-87a88da7ca01","kind":"MISSING","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["UNOBSERVED_MOVE"],"mechanism":"Exemplaire déplacé hors du périmètre instrumenté ; la destination n'est pas exposée et aucune perte n'est simulée.","physicalStatus":"ON_SHELF","physicalSiteId":"CENTRE","physicalLocationId":null,"evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"L'absence de lecture ne démontre ni la destination, ni une perte, ni un vol ; données insuffisantes pour attribuer une cause.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État projeté : `{"copyId":"f3af51d6-903e-3820-b056-5f72174e8077","status":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","sourceEventIds":["ef45ce43-146c-3f8f-9de5-a298908ced4d","07895ddf-a2ed-3ffd-8e07-7c9139bf2f0a"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | ffa1f517-0372-3ef9-b136-6620602b21b1 | 2026-06-02T04:25:19Z | 2026-06-02T04:25:29Z | `{"operationId":"2762d197-7b59-3942-8c2e-0dc105b08597","correlationId":"db2bb4d0-50e0-355a-b679-bf707b9b8918","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"f590b13c-3bf3-3003-bac5-3a875b990b71"}` |
| IntegrationProcessed | 7e5e96c9-2f8f-3cee-b263-62253fbf36c8 | 2026-06-03T04:25:19Z | 2026-06-03T04:25:29Z | `{"originalEventId":"ffa1f517-0372-3ef9-b136-6620602b21b1","correlationId":"db2bb4d0-50e0-355a-b679-bf707b9b8918","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | ab0c283f-1c33-3cb6-aa07-2405157f5b1d | 2026-08-01T04:25:19Z | 2026-08-01T04:25:29Z | `{"operationId":"c8a1e1da-5068-3f14-bc2c-39e18172bdfd","correlationId":"24e7bca6-7586-3fdc-b815-926614bd324b","siteId":"CENTRE","locationId":null,"loanId":"dee02b7d-67d2-3439-96e5-30be9de5c124"}` |
| IntegrationProcessed | 558c1e7a-ff65-3d16-a2d8-c46e37cfcfbd | 2026-08-01T04:26:19Z | 2026-08-01T04:26:29Z | `{"originalEventId":"ab0c283f-1c33-3cb6-aa07-2405157f5b1d","correlationId":"24e7bca6-7586-3fdc-b815-926614bd324b","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | e370d620-da76-3812-91df-aecd83236b81 | 2026-08-21T04:25:19Z | 2026-08-21T04:25:29Z | `{"operationId":"91ad7368-86b6-3b05-9386-259243b212e5","correlationId":"fb882bb9-8585-3504-980a-23f1033875c1","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"dee02b7d-67d2-3439-96e5-30be9de5c124"}` |
| ReturnReceived | ef45ce43-146c-3f8f-9de5-a298908ced4d | 2026-08-21T04:25:49Z | 2026-08-21T04:25:59Z | `{"operationId":"d49a508a-b1e6-38e6-bb11-4da5a3c9be2c","correlationId":"d653aa47-d165-3a0d-aa1f-4e6c9ea3c092","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"dee02b7d-67d2-3439-96e5-30be9de5c124"}` |
| IntegrationProcessed | 07895ddf-a2ed-3ffd-8e07-7c9139bf2f0a | 2026-08-22T04:25:19Z | 2026-08-22T04:25:29Z | `{"originalEventId":"ef45ce43-146c-3f8f-9de5-a298908ced4d","correlationId":"d653aa47-d165-3a0d-aa1f-4e6c9ea3c092","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |

**observations**

**sourceAvailability**
- `{"copyId":"f3af51d6-903e-3820-b056-5f72174e8077","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f3af51d6-903e-3820-b056-5f72174e8077","source":"TRANSFERS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"f3af51d6-903e-3820-b056-5f72174e8077","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f3af51d6-903e-3820-b056-5f72174e8077","source":"LOANS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"f3af51d6-903e-3820-b056-5f72174e8077","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f3af51d6-903e-3820-b056-5f72174e8077","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f3af51d6-903e-3820-b056-5f72174e8077","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**
- `{"noteId":"31ccd021-7fbe-31e1-b702-3b0f5ea79026","copyId":"f3af51d6-903e-3820-b056-5f72174e8077","text":"Le passage au rayon n'a pas permis de retrouver cet exemplaire ; contrôle complémentaire demandé.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"f3af51d6-903e-3820-b056-5f72174e8077"},{"kind":"sourceEvent","id":"ffa1f517-0372-3ef9-b136-6620602b21b1"},{"kind":"sourceEvent","id":"7e5e96c9-2f8f-3cee-b263-62253fbf36c8"},{"kind":"sourceEvent","id":"ab0c283f-1c33-3cb6-aa07-2405157f5b1d"},{"kind":"sourceEvent","id":"558c1e7a-ff65-3d16-a2d8-c46e37cfcfbd"},{"kind":"sourceEvent","id":"e370d620-da76-3812-91df-aecd83236b81"},{"kind":"sourceEvent","id":"ef45ce43-146c-3f8f-9de5-a298908ced4d"},{"kind":"sourceEvent","id":"07895ddf-a2ed-3ffd-8e07-7c9139bf2f0a"},{"kind":"sourceAvailability","id":"f3af51d6-903e-3820-b056-5f72174e8077/LOANS"},{"kind":"sourceAvailability","id":"f3af51d6-903e-3820-b056-5f72174e8077/RETURNS"},{"kind":"sourceAvailability","id":"f3af51d6-903e-3820-b056-5f72174e8077/RECEIPTS"},{"kind":"sourceAvailability","id":"f3af51d6-903e-3820-b056-5f72174e8077/TRANSFERS"},{"kind":"sourceAvailability","id":"f3af51d6-903e-3820-b056-5f72174e8077/REPAIRS"},{"kind":"sourceAvailability","id":"f3af51d6-903e-3820-b056-5f72174e8077/INTEGRATIONS"},{"kind":"sourceAvailability","id":"f3af51d6-903e-3820-b056-5f72174e8077/INVENTORY"},{"kind":"operatorNote","id":"31ccd021-7fbe-31e1-b702-3b0f5ea79026"},{"kind":"inventoryScope","id":"8021dca8-65fe-3cab-985e-d4fb9df5c308"},{"kind":"inventoryScope","id":"b0027657-6abb-37d2-8bc4-9341357dfe79"},{"kind":"inventoryScope","id":"4757f5eb-f104-3413-b118-51a6951a650c"},{"kind":"inventoryScope","id":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592"},{"kind":"inventoryScope","id":"40e94818-3a62-34a4-b461-398d7b9ffca2"},{"kind":"inventoryScope","id":"ed60c0be-b218-3f9e-8aa8-69c16f76c22e"},{"kind":"inventoryScope","id":"35514ff7-97f8-345d-8cf7-6cee969a726c"},{"kind":"inventoryScope","id":"351b3e2d-1bff-3290-938e-527c15f97274"},{"kind":"inventoryScope","id":"351b3e2d-1bff-3290-938e-527c15f97274"}]`

- Session : `{"scopeId":"8021dca8-65fe-3cab-985e-d4fb9df5c308","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b0027657-6abb-37d2-8bc4-9341357dfe79","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4757f5eb-f104-3413-b118-51a6951a650c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"40e94818-3a62-34a4-b461-398d7b9ffca2","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"ed60c0be-b218-3f9e-8aa8-69c16f76c22e","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"35514ff7-97f8-345d-8cf7-6cee969a726c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"351b3e2d-1bff-3290-938e-527c15f97274","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"351b3e2d-1bff-3290-938e-527c15f97274","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas c92509c5-51b5-35df-8449-4428f41ff400

- Exemplaire : `44059ab7-1d7e-39df-93df-0dfb17288932`
- Famille privée : RETOUR_NON_PROJETE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"ba8d7332-7625-3d0b-b669-ee5c79e0d9cf","kind":"STATUS_MISMATCH","expectedValue":"ON_LOAN","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"1a456810-0625-3818-b75f-dfd148b5f652","copyId":"44059ab7-1d7e-39df-93df-0dfb17288932","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T10:57:54Z","recordedAt":"2026-09-27T10:58:04Z","payload":{"originalEventId":"dc3b290f-0c3e-3de6-a8fb-c0f206b0c783","correlationId":"cdb1c666-fa07-3257-970f-b023931ce987","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"dc3b290f-0c3e-3de6-a8fb-c0f206b0c783","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État projeté : `{"copyId":"44059ab7-1d7e-39df-93df-0dfb17288932","status":"ON_LOAN","siteId":"CENTRE","locationId":null,"sourceEventIds":["daa7a786-5907-39ea-a4fe-74c8b3a0d6d8","95dcd557-4516-3bc6-996a-96340cfb26f8"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | a7c31c53-0ce2-35dc-832f-678037816be4 | 2026-06-01T15:47:26Z | 2026-06-01T15:47:36Z | `{"operationId":"e8214848-a72d-3ca0-87ff-1f09b4896daf","correlationId":"adb573e8-e4d7-3bd5-ad67-c939c88771c8","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"52957332-b474-34ed-811b-deed494e4197"}` |
| IntegrationProcessed | 3ff7ebb7-60e4-3bf7-a33a-573c020519de | 2026-06-02T15:47:26Z | 2026-06-02T15:47:36Z | `{"originalEventId":"a7c31c53-0ce2-35dc-832f-678037816be4","correlationId":"adb573e8-e4d7-3bd5-ad67-c939c88771c8","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | 91b295ee-af14-3e1e-a71f-0af1c95ad417 | 2026-07-31T15:47:26Z | 2026-07-31T15:47:36Z | `{"operationId":"ef48c517-bb4b-3cb8-be74-c3dc84318967","correlationId":"cccb7254-c215-3842-adee-8f2784021b77","siteId":"SUD","locationId":null,"loanId":"62d388c8-394f-309d-9da2-2404e572821e"}` |
| IntegrationProcessed | 8a23b42f-bfc5-387f-b7a3-6a0928a88e69 | 2026-07-31T15:48:26Z | 2026-07-31T15:48:36Z | `{"originalEventId":"91b295ee-af14-3e1e-a71f-0af1c95ad417","correlationId":"cccb7254-c215-3842-adee-8f2784021b77","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | 5a3b376c-ef31-3b3f-aa52-b90a6b9c1e8a | 2026-08-20T15:47:26Z | 2026-08-20T15:47:36Z | `{"operationId":"baeda858-9a3c-39fe-b5b6-8915dcc0067e","correlationId":"951ee15a-6f1f-3e00-b9bf-33799bc474fa","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"62d388c8-394f-309d-9da2-2404e572821e"}` |
| ReturnReceived | d5128ead-80a4-3e0b-8264-e324c711de27 | 2026-08-20T15:47:56Z | 2026-08-20T15:48:06Z | `{"operationId":"97dc8d76-05ab-326b-b210-c7f0e141cd8d","correlationId":"eb1c707a-339b-39e4-8447-260cbe27ce27","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"62d388c8-394f-309d-9da2-2404e572821e"}` |
| IntegrationProcessed | 9f4f0f03-807f-32cf-a9af-e45564057242 | 2026-08-21T15:47:26Z | 2026-08-21T15:47:36Z | `{"originalEventId":"d5128ead-80a4-3e0b-8264-e324c711de27","correlationId":"eb1c707a-339b-39e4-8447-260cbe27ce27","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| TransferDispatched | 8cad7e54-e38d-3402-a686-f3a1a1e2d109 | 2026-08-30T15:47:26Z | 2026-08-30T15:47:36Z | `{"operationId":"d6bae1fa-3a65-3b5f-a417-37aae3468e42","correlationId":"c5ab575a-3055-3a1f-b138-903f385375e7","siteId":"SUD","locationId":null,"transferId":"2616973a-02c9-3d43-bf68-d3926a3fdfcd","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| IntegrationProcessed | 9a6a3f8a-14d5-3d50-8377-eb7c711879e8 | 2026-08-30T15:48:26Z | 2026-08-30T15:48:36Z | `{"originalEventId":"8cad7e54-e38d-3402-a686-f3a1a1e2d109","correlationId":"c5ab575a-3055-3a1f-b138-903f385375e7","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"SUD","locationId":null}` |
| TransferReceived | 11f6a196-860a-3d11-982f-c8735ccbbc6b | 2026-08-31T15:47:26Z | 2026-08-31T15:47:36Z | `{"operationId":"c62e1304-30cb-3aa6-a5b1-1a6634c39dbd","correlationId":"f1e1d227-60b0-3d89-b4b6-b390bea962ff","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","transferId":"2616973a-02c9-3d43-bf68-d3926a3fdfcd","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| IntegrationProcessed | df8e0581-cd34-31ca-8fca-c1594e89ec92 | 2026-09-01T15:47:26Z | 2026-09-01T15:47:36Z | `{"originalEventId":"11f6a196-860a-3d11-982f-c8735ccbbc6b","correlationId":"f1e1d227-60b0-3d89-b4b6-b390bea962ff","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | daa7a786-5907-39ea-a4fe-74c8b3a0d6d8 | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"8675d802-43bf-3d35-844f-05362c1e7982","correlationId":"0ef671b7-cf9e-372b-8d12-b7bb0dd0190b","siteId":"CENTRE","locationId":null,"loanId":"daaafb75-9db6-35fc-ae0f-6fdbdeebb466"}` |
| IntegrationProcessed | 95dcd557-4516-3bc6-996a-96340cfb26f8 | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"daa7a786-5907-39ea-a4fe-74c8b3a0d6d8","correlationId":"0ef671b7-cf9e-372b-8d12-b7bb0dd0190b","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 40305dce-0705-3f40-bed9-03f17115ab3a | 2026-09-27T10:56:54Z | 2026-09-27T10:57:04Z | `{"operationId":"25109e74-d64b-3fdf-b6c3-518097a4ab05","correlationId":"c9f1ca5d-4288-38eb-8bec-5610eb30b372","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"daaafb75-9db6-35fc-ae0f-6fdbdeebb466"}` |
| ReturnReceived | dc3b290f-0c3e-3de6-a8fb-c0f206b0c783 | 2026-09-27T10:57:24Z | 2026-09-27T10:57:34Z | `{"operationId":"f903184f-f504-3d77-a833-7a7b5517fb52","correlationId":"cdb1c666-fa07-3257-970f-b023931ce987","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"daaafb75-9db6-35fc-ae0f-6fdbdeebb466"}` |

**observations**
- `{"observationId":"c5c55419-f830-31ae-a207-c9a04dd42f71","copyId":"44059ab7-1d7e-39df-93df-0dfb17288932","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","scopeId":"0a12c174-c6ce-3c90-852a-76f72a3edebf","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**sourceAvailability**
- `{"copyId":"44059ab7-1d7e-39df-93df-0dfb17288932","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"44059ab7-1d7e-39df-93df-0dfb17288932","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"44059ab7-1d7e-39df-93df-0dfb17288932","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"44059ab7-1d7e-39df-93df-0dfb17288932","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"44059ab7-1d7e-39df-93df-0dfb17288932","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"44059ab7-1d7e-39df-93df-0dfb17288932","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"44059ab7-1d7e-39df-93df-0dfb17288932","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"44059ab7-1d7e-39df-93df-0dfb17288932"},{"kind":"sourceEvent","id":"a7c31c53-0ce2-35dc-832f-678037816be4"},{"kind":"sourceEvent","id":"3ff7ebb7-60e4-3bf7-a33a-573c020519de"},{"kind":"sourceEvent","id":"91b295ee-af14-3e1e-a71f-0af1c95ad417"},{"kind":"sourceEvent","id":"8a23b42f-bfc5-387f-b7a3-6a0928a88e69"},{"kind":"sourceEvent","id":"5a3b376c-ef31-3b3f-aa52-b90a6b9c1e8a"},{"kind":"sourceEvent","id":"d5128ead-80a4-3e0b-8264-e324c711de27"},{"kind":"sourceEvent","id":"9f4f0f03-807f-32cf-a9af-e45564057242"},{"kind":"sourceEvent","id":"8cad7e54-e38d-3402-a686-f3a1a1e2d109"},{"kind":"sourceEvent","id":"9a6a3f8a-14d5-3d50-8377-eb7c711879e8"},{"kind":"sourceEvent","id":"11f6a196-860a-3d11-982f-c8735ccbbc6b"},{"kind":"sourceEvent","id":"df8e0581-cd34-31ca-8fca-c1594e89ec92"},{"kind":"sourceEvent","id":"daa7a786-5907-39ea-a4fe-74c8b3a0d6d8"},{"kind":"sourceEvent","id":"95dcd557-4516-3bc6-996a-96340cfb26f8"},{"kind":"sourceEvent","id":"40305dce-0705-3f40-bed9-03f17115ab3a"},{"kind":"sourceEvent","id":"dc3b290f-0c3e-3de6-a8fb-c0f206b0c783"},{"kind":"observation","id":"c5c55419-f830-31ae-a207-c9a04dd42f71"},{"kind":"inventoryScope","id":"0a12c174-c6ce-3c90-852a-76f72a3edebf"},{"kind":"sourceAvailability","id":"44059ab7-1d7e-39df-93df-0dfb17288932/LOANS"},{"kind":"sourceAvailability","id":"44059ab7-1d7e-39df-93df-0dfb17288932/RETURNS"},{"kind":"sourceAvailability","id":"44059ab7-1d7e-39df-93df-0dfb17288932/RECEIPTS"},{"kind":"sourceAvailability","id":"44059ab7-1d7e-39df-93df-0dfb17288932/TRANSFERS"},{"kind":"sourceAvailability","id":"44059ab7-1d7e-39df-93df-0dfb17288932/REPAIRS"},{"kind":"sourceAvailability","id":"44059ab7-1d7e-39df-93df-0dfb17288932/INTEGRATIONS"},{"kind":"sourceAvailability","id":"44059ab7-1d7e-39df-93df-0dfb17288932/INVENTORY"}]`

- Session : `{"scopeId":"0a12c174-c6ce-3c90-852a-76f72a3edebf","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas cadc8dd4-0133-3009-9140-99aca197abeb

- Exemplaire : `87cafe6f-a16b-34ba-a26e-e7e391e19017`
- Famille privée : REPARATION_NON_PROJETEE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"4d097ff7-6457-39df-869c-ba1ca50e224e","kind":"STATUS_MISMATCH","expectedValue":"ON_SHELF","observedValue":"IN_REPAIR"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"ec5a2266-85cb-3b48-bc23-69ea81a3fc15","copyId":"87cafe6f-a16b-34ba-a26e-e7e391e19017","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T10:22:51Z","recordedAt":"2026-09-27T10:23:01Z","payload":{"originalEventId":"293bb9c3-6d60-3bf3-9da6-d78c26dcffb8","correlationId":"a68cbfe8-a738-3f66-a710-d49e9c6b2678","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"293bb9c3-6d60-3bf3-9da6-d78c26dcffb8","physicalStatus":"IN_REPAIR","physicalSiteId":"CENTRE","physicalLocationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État projeté : `{"copyId":"87cafe6f-a16b-34ba-a26e-e7e391e19017","status":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","sourceEventIds":["6d0f1869-57a4-3352-af7f-28e33ab96118","df32118c-284c-3a3f-9102-1bc423f86e9a"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | a15e5e77-8ba5-3660-b9bf-189a97bd46f6 | 2026-06-01T19:30:16Z | 2026-06-01T19:30:26Z | `{"operationId":"43b8383d-679d-3e1f-9c3f-e38795483f78","correlationId":"43b942b0-443e-3125-a491-55a532ade36f","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"a3880a81-bb57-332d-9d13-c378e1ca655d"}` |
| IntegrationProcessed | b6ca5e90-ab37-3f72-9f75-e2756367701c | 2026-06-02T19:30:16Z | 2026-06-02T19:30:26Z | `{"originalEventId":"a15e5e77-8ba5-3660-b9bf-189a97bd46f6","correlationId":"43b942b0-443e-3125-a491-55a532ade36f","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | c44829db-0902-3e78-ad7e-3879780f36e3 | 2026-07-31T19:30:16Z | 2026-07-31T19:30:26Z | `{"operationId":"013c77a1-a47d-3a02-a968-94bb18d36ec4","correlationId":"abc5fc3b-dba4-351e-aaff-e08acf171093","siteId":"CENTRE","locationId":null,"loanId":"fb7c8001-a6f0-350e-96a1-89270e355b64"}` |
| IntegrationProcessed | da12a1a3-d9c1-3ea9-b5ef-eeaabc1fd4c1 | 2026-07-31T19:31:16Z | 2026-07-31T19:31:26Z | `{"originalEventId":"c44829db-0902-3e78-ad7e-3879780f36e3","correlationId":"abc5fc3b-dba4-351e-aaff-e08acf171093","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 9b4a7782-a732-30b0-8b7b-bc221f48872b | 2026-08-20T19:30:16Z | 2026-08-20T19:30:26Z | `{"operationId":"f754f7b6-580b-3233-8cfd-2c64fc857667","correlationId":"3bb55613-64aa-3a96-a9a7-f01ffec4f327","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"fb7c8001-a6f0-350e-96a1-89270e355b64"}` |
| ReturnReceived | 6d0f1869-57a4-3352-af7f-28e33ab96118 | 2026-08-20T19:30:46Z | 2026-08-20T19:30:56Z | `{"operationId":"0013884d-cdef-3d5b-8893-4866c2f1fb92","correlationId":"81061a3b-ac96-32e1-b826-5bd80dd52d37","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"fb7c8001-a6f0-350e-96a1-89270e355b64"}` |
| IntegrationProcessed | df32118c-284c-3a3f-9102-1bc423f86e9a | 2026-08-21T19:30:16Z | 2026-08-21T19:30:26Z | `{"originalEventId":"6d0f1869-57a4-3352-af7f-28e33ab96118","correlationId":"81061a3b-ac96-32e1-b826-5bd80dd52d37","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| RepairStarted | 293bb9c3-6d60-3bf3-9da6-d78c26dcffb8 | 2026-09-27T10:22:21Z | 2026-09-27T10:22:31Z | `{"operationId":"2304931f-ab8e-3e4c-95dd-3b4d2832d9c1","correlationId":"a68cbfe8-a738-3f66-a710-d49e9c6b2678","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","repairId":"46605f3e-6590-3d7c-8984-f2ffe23c6608"}` |

**observations**
- `{"observationId":"e696f877-2b59-37c9-b8fa-d8f41b21069c","copyId":"87cafe6f-a16b-34ba-a26e-e7e391e19017","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","scopeId":"64d8a581-eb79-3896-a532-b4a4254d76fe","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**sourceAvailability**
- `{"copyId":"87cafe6f-a16b-34ba-a26e-e7e391e19017","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"87cafe6f-a16b-34ba-a26e-e7e391e19017","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"87cafe6f-a16b-34ba-a26e-e7e391e19017","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"87cafe6f-a16b-34ba-a26e-e7e391e19017","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"87cafe6f-a16b-34ba-a26e-e7e391e19017","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"87cafe6f-a16b-34ba-a26e-e7e391e19017","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"87cafe6f-a16b-34ba-a26e-e7e391e19017","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"87cafe6f-a16b-34ba-a26e-e7e391e19017"},{"kind":"sourceEvent","id":"a15e5e77-8ba5-3660-b9bf-189a97bd46f6"},{"kind":"sourceEvent","id":"b6ca5e90-ab37-3f72-9f75-e2756367701c"},{"kind":"sourceEvent","id":"c44829db-0902-3e78-ad7e-3879780f36e3"},{"kind":"sourceEvent","id":"da12a1a3-d9c1-3ea9-b5ef-eeaabc1fd4c1"},{"kind":"sourceEvent","id":"9b4a7782-a732-30b0-8b7b-bc221f48872b"},{"kind":"sourceEvent","id":"6d0f1869-57a4-3352-af7f-28e33ab96118"},{"kind":"sourceEvent","id":"df32118c-284c-3a3f-9102-1bc423f86e9a"},{"kind":"sourceEvent","id":"293bb9c3-6d60-3bf3-9da6-d78c26dcffb8"},{"kind":"observation","id":"e696f877-2b59-37c9-b8fa-d8f41b21069c"},{"kind":"inventoryScope","id":"64d8a581-eb79-3896-a532-b4a4254d76fe"},{"kind":"sourceAvailability","id":"87cafe6f-a16b-34ba-a26e-e7e391e19017/LOANS"},{"kind":"sourceAvailability","id":"87cafe6f-a16b-34ba-a26e-e7e391e19017/RETURNS"},{"kind":"sourceAvailability","id":"87cafe6f-a16b-34ba-a26e-e7e391e19017/RECEIPTS"},{"kind":"sourceAvailability","id":"87cafe6f-a16b-34ba-a26e-e7e391e19017/TRANSFERS"},{"kind":"sourceAvailability","id":"87cafe6f-a16b-34ba-a26e-e7e391e19017/REPAIRS"},{"kind":"sourceAvailability","id":"87cafe6f-a16b-34ba-a26e-e7e391e19017/INTEGRATIONS"},{"kind":"sourceAvailability","id":"87cafe6f-a16b-34ba-a26e-e7e391e19017/INVENTORY"},{"kind":"inventoryScope","id":"8021dca8-65fe-3cab-985e-d4fb9df5c308"},{"kind":"inventoryScope","id":"b0027657-6abb-37d2-8bc4-9341357dfe79"},{"kind":"inventoryScope","id":"4757f5eb-f104-3413-b118-51a6951a650c"}]`

- Session : `{"scopeId":"64d8a581-eb79-3896-a532-b4a4254d76fe","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"8021dca8-65fe-3cab-985e-d4fb9df5c308","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b0027657-6abb-37d2-8bc4-9341357dfe79","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4757f5eb-f104-3413-b118-51a6951a650c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas cc86860c-02a4-3354-bfd8-de1566c0e74a

- Exemplaire : `0591c97f-dff6-30ac-a967-3ea4d4155175`
- Famille privée : INVENTAIRE_INCOMPLET ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"5654df16-b57b-35f3-aaaa-ad44e74187ec","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["COLLECTION_INTERRUPTED"],"mechanism":"Interruption de collecte ; exemplaire resté au rayon.","physicalStatus":"ON_SHELF","physicalSiteId":"CENTRE","physicalLocationId":"4a5364f2-388b-351d-91f2-d1364d119042","evidenceMask":"Collecte indisponible"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"L'absence de lecture ne démontre ni la destination, ni une perte, ni un vol ; données insuffisantes pour attribuer une cause.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État projeté : `{"copyId":"0591c97f-dff6-30ac-a967-3ea4d4155175","status":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","sourceEventIds":["473282de-fc3d-3360-8299-39625fff1784","cbf27fb7-dff7-3d15-b3b6-2635deaf06e6"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 4839a349-629c-3b80-8b3b-07afe6623196 | 2026-06-02T08:50:02Z | 2026-06-02T08:50:12Z | `{"operationId":"e9a117b8-50ea-3887-b7c8-0cf17e74717f","correlationId":"f0328f21-5007-3f5f-ba82-46e2b0841c94","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"51ec0543-e43d-3853-a9f5-9bcd872aa60e"}` |
| IntegrationProcessed | a9998706-cffe-30e2-8c48-631c26be778c | 2026-06-03T08:50:02Z | 2026-06-03T08:50:12Z | `{"originalEventId":"4839a349-629c-3b80-8b3b-07afe6623196","correlationId":"f0328f21-5007-3f5f-ba82-46e2b0841c94","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | c51cccda-9760-337e-8937-7351ce09597e | 2026-08-01T08:50:02Z | 2026-08-01T08:50:12Z | `{"operationId":"97d0ec8c-41a5-32da-94e8-2027834293c8","correlationId":"059c94ed-96b7-3d87-904a-ad277c77ca87","siteId":"SUD","locationId":null,"loanId":"1bee3b47-28f9-31a9-9381-42b410c1e28d"}` |
| IntegrationProcessed | 9d62de0c-0c73-3f63-ba88-3075f2120f0a | 2026-08-01T08:51:02Z | 2026-08-01T08:51:12Z | `{"originalEventId":"c51cccda-9760-337e-8937-7351ce09597e","correlationId":"059c94ed-96b7-3d87-904a-ad277c77ca87","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | 81d122df-90fc-329d-8c74-ebdd0c1929b1 | 2026-08-21T08:50:02Z | 2026-08-21T08:50:12Z | `{"operationId":"a1fba9ec-bf1f-307c-9339-a87a6861d19a","correlationId":"f9b85d5d-02d1-3077-a800-cd181f60258c","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"1bee3b47-28f9-31a9-9381-42b410c1e28d"}` |
| ReturnReceived | b8165a17-fac7-3ecc-82fe-5e677afc7890 | 2026-08-21T08:50:32Z | 2026-08-21T08:50:42Z | `{"operationId":"d7f3475b-9025-3a28-a6ca-7038bcb340f3","correlationId":"fa82f200-f399-3512-9d4a-7f0679b3bac2","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"1bee3b47-28f9-31a9-9381-42b410c1e28d"}` |
| IntegrationProcessed | 156e045d-30e7-3250-a076-7784f77cb703 | 2026-08-22T08:50:02Z | 2026-08-22T08:50:12Z | `{"originalEventId":"b8165a17-fac7-3ecc-82fe-5e677afc7890","correlationId":"fa82f200-f399-3512-9d4a-7f0679b3bac2","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| TransferDispatched | 97444863-22b1-377d-93ab-3063e20d8e1a | 2026-08-31T08:50:02Z | 2026-08-31T08:50:12Z | `{"operationId":"c62dfb48-8dab-359f-bf59-f7d12acf8272","correlationId":"bc9f1c9f-9ee1-3ed6-9cac-29c6f3f0abf1","siteId":"SUD","locationId":null,"transferId":"780ed755-ae97-36bc-b56c-911d1a3799a6","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| IntegrationProcessed | 5c70ad81-d0b6-3d27-a2f4-e275a21f2300 | 2026-08-31T08:51:02Z | 2026-08-31T08:51:12Z | `{"originalEventId":"97444863-22b1-377d-93ab-3063e20d8e1a","correlationId":"bc9f1c9f-9ee1-3ed6-9cac-29c6f3f0abf1","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"SUD","locationId":null}` |
| TransferReceived | 473282de-fc3d-3360-8299-39625fff1784 | 2026-09-01T08:50:02Z | 2026-09-01T08:50:12Z | `{"operationId":"67c78937-dc83-3ce2-bea6-12a577930011","correlationId":"cd0802a6-2f03-34d0-922f-3cb25455404c","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","transferId":"780ed755-ae97-36bc-b56c-911d1a3799a6","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| IntegrationProcessed | cbf27fb7-dff7-3d15-b3b6-2635deaf06e6 | 2026-09-02T08:50:02Z | 2026-09-02T08:50:12Z | `{"originalEventId":"473282de-fc3d-3360-8299-39625fff1784","correlationId":"cd0802a6-2f03-34d0-922f-3cb25455404c","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |

**observations**

**sourceAvailability**
- `{"copyId":"0591c97f-dff6-30ac-a967-3ea4d4155175","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0591c97f-dff6-30ac-a967-3ea4d4155175","source":"INVENTORY","status":"UNAVAILABLE","completeFrom":null,"completeTo":null}`
- `{"copyId":"0591c97f-dff6-30ac-a967-3ea4d4155175","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0591c97f-dff6-30ac-a967-3ea4d4155175","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0591c97f-dff6-30ac-a967-3ea4d4155175","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0591c97f-dff6-30ac-a967-3ea4d4155175","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"0591c97f-dff6-30ac-a967-3ea4d4155175","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**
- `{"noteId":"8d85e9a6-8461-387d-82d0-d9b956c8e138","copyId":"0591c97f-dff6-30ac-a967-3ea4d4155175","text":"Le terminal de collecte a cessé de répondre pendant le passage ; aucune conclusion sur les exemplaires non lus.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"0591c97f-dff6-30ac-a967-3ea4d4155175"},{"kind":"sourceEvent","id":"4839a349-629c-3b80-8b3b-07afe6623196"},{"kind":"sourceEvent","id":"a9998706-cffe-30e2-8c48-631c26be778c"},{"kind":"sourceEvent","id":"c51cccda-9760-337e-8937-7351ce09597e"},{"kind":"sourceEvent","id":"9d62de0c-0c73-3f63-ba88-3075f2120f0a"},{"kind":"sourceEvent","id":"81d122df-90fc-329d-8c74-ebdd0c1929b1"},{"kind":"sourceEvent","id":"b8165a17-fac7-3ecc-82fe-5e677afc7890"},{"kind":"sourceEvent","id":"156e045d-30e7-3250-a076-7784f77cb703"},{"kind":"sourceEvent","id":"97444863-22b1-377d-93ab-3063e20d8e1a"},{"kind":"sourceEvent","id":"5c70ad81-d0b6-3d27-a2f4-e275a21f2300"},{"kind":"sourceEvent","id":"473282de-fc3d-3360-8299-39625fff1784"},{"kind":"sourceEvent","id":"cbf27fb7-dff7-3d15-b3b6-2635deaf06e6"},{"kind":"sourceAvailability","id":"0591c97f-dff6-30ac-a967-3ea4d4155175/LOANS"},{"kind":"sourceAvailability","id":"0591c97f-dff6-30ac-a967-3ea4d4155175/RETURNS"},{"kind":"sourceAvailability","id":"0591c97f-dff6-30ac-a967-3ea4d4155175/RECEIPTS"},{"kind":"sourceAvailability","id":"0591c97f-dff6-30ac-a967-3ea4d4155175/TRANSFERS"},{"kind":"sourceAvailability","id":"0591c97f-dff6-30ac-a967-3ea4d4155175/REPAIRS"},{"kind":"sourceAvailability","id":"0591c97f-dff6-30ac-a967-3ea4d4155175/INTEGRATIONS"},{"kind":"sourceAvailability","id":"0591c97f-dff6-30ac-a967-3ea4d4155175/INVENTORY"},{"kind":"operatorNote","id":"8d85e9a6-8461-387d-82d0-d9b956c8e138"},{"kind":"inventoryScope","id":"8021dca8-65fe-3cab-985e-d4fb9df5c308"},{"kind":"inventoryScope","id":"b0027657-6abb-37d2-8bc4-9341357dfe79"},{"kind":"inventoryScope","id":"4757f5eb-f104-3413-b118-51a6951a650c"},{"kind":"inventoryScope","id":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592"},{"kind":"inventoryScope","id":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592"}]`

- Session : `{"scopeId":"8021dca8-65fe-3cab-985e-d4fb9df5c308","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b0027657-6abb-37d2-8bc4-9341357dfe79","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4757f5eb-f104-3413-b118-51a6951a650c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas cca5fd8d-b4b2-3c04-912c-9f96dc52b159

- Exemplaire : `4ea93ecb-5fdd-3e43-9a39-9c2591c87cde`
- Famille privée : DEUX_CAUSES ; variante : POSITIVE
- Symptômes : `[{"symptomId":"2bb87fe1-712f-3b39-93b4-307555e96c3f","kind":"STATUS_MISMATCH","expectedValue":"IN_REPAIR","observedValue":"ON_SHELF"},{"symptomId":"69e4a02f-836f-3c57-8ee1-7a2919d972da","kind":"LOCATION_MISMATCH","expectedValue":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","observedValue":"b8600ac8-b5d3-3580-b3c5-71a711df1a50"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED","REPAIR_LOCATION_CHANGED"],"hiddenSourceEvents":[],"mechanism":"Réparation engagée puis déplacement distinct de l'atelier vers la réserve sans mise à jour de l'affectation de réparation.","businessEventId":"1b498c81-2ce4-380f-b0fc-73a3a9ca6e8f","physicalStatus":"IN_REPAIR","physicalSiteId":"CENTRE","physicalLocationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_NOT_PROJECTED","REPAIR_LOCATION_MISMATCH"],"explanation":"Deux écarts compatibles : réparation non projetée et scan valide hors de l'atelier indiqué par l'ordre de réparation.","forbiddenClaims":["Deux causes confirmées par un scan ambigu","Vol"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"4ea93ecb-5fdd-3e43-9a39-9c2591c87cde","status":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","sourceEventIds":["e2de23a5-b3cd-3c19-9ba4-c8f811dc7a8a","3eb56fba-ae9c-3978-9a00-be763fc213e0"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 45ee3c02-4059-310d-832c-ec4c34665ea1 | 2026-06-01T14:05:58Z | 2026-06-01T14:06:08Z | `{"operationId":"2ab5124d-31ba-393e-92af-7fecdf421bcf","correlationId":"8eacb082-73d0-32f8-a7e9-2704f85a7ea8","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"62e99dd3-b2a5-35e9-b993-456dce707f88"}` |
| IntegrationProcessed | 39154aa2-e3e6-370a-bec9-32620ab2db19 | 2026-06-02T14:05:58Z | 2026-06-02T14:06:08Z | `{"originalEventId":"45ee3c02-4059-310d-832c-ec4c34665ea1","correlationId":"8eacb082-73d0-32f8-a7e9-2704f85a7ea8","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 90bc1067-33c3-3680-a5e2-b2004d03d902 | 2026-07-31T14:05:58Z | 2026-07-31T14:06:08Z | `{"operationId":"a4b17e52-ca6f-3742-b809-7d63e1a9c7e9","correlationId":"42a2b4e0-04d7-3c03-a697-5a0c70d94097","siteId":"CENTRE","locationId":null,"loanId":"8f5b5512-758a-3116-9b7b-75cfb4555b8f"}` |
| IntegrationProcessed | 9d6fa9ed-336c-34fb-b27f-436ff91c22bc | 2026-07-31T14:06:58Z | 2026-07-31T14:07:08Z | `{"originalEventId":"90bc1067-33c3-3680-a5e2-b2004d03d902","correlationId":"42a2b4e0-04d7-3c03-a697-5a0c70d94097","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 9bb0122b-174a-30c3-811b-4b673afa1f01 | 2026-08-20T14:05:58Z | 2026-08-20T14:06:08Z | `{"operationId":"2f0f56f1-76c4-3a84-8691-931f78bd0140","correlationId":"389b4792-3c8e-37aa-9161-5290f41c4ac7","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"8f5b5512-758a-3116-9b7b-75cfb4555b8f"}` |
| ReturnReceived | e2de23a5-b3cd-3c19-9ba4-c8f811dc7a8a | 2026-08-20T14:06:28Z | 2026-08-20T14:06:38Z | `{"operationId":"94b4e0ac-38f9-3e3a-ba0d-f567e965d6fb","correlationId":"510e8c1b-f789-3dbf-a182-34cdc31a9247","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"8f5b5512-758a-3116-9b7b-75cfb4555b8f"}` |
| IntegrationProcessed | 3eb56fba-ae9c-3978-9a00-be763fc213e0 | 2026-08-21T14:05:58Z | 2026-08-21T14:06:08Z | `{"originalEventId":"e2de23a5-b3cd-3c19-9ba4-c8f811dc7a8a","correlationId":"510e8c1b-f789-3dbf-a182-34cdc31a9247","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| RepairStarted | 1b498c81-2ce4-380f-b0fc-73a3a9ca6e8f | 2026-09-27T06:45:28Z | 2026-09-27T06:45:38Z | `{"operationId":"f9e477d0-9b6c-3c59-9f19-4b7a7e77a14e","correlationId":"31a7217d-ff72-31f0-846b-0d620d1eb20f","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","repairId":"dd1a8d19-7e82-3fdf-8abe-a644b7e369b1"}` |
| IntegrationProcessed | 7136a0e0-8d84-3333-906c-46c35e03f7b6 | 2026-09-27T06:45:58Z | 2026-09-27T06:46:08Z | `{"originalEventId":"1b498c81-2ce4-380f-b0fc-73a3a9ca6e8f","correlationId":"31a7217d-ff72-31f0-846b-0d620d1eb20f","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**observations**
- `{"observationId":"fec34e84-23f0-3131-9f7d-3e2413b761c1","copyId":"4ea93ecb-5fdd-3e43-9a39-9c2591c87cde","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","scopeId":"88edb641-fcfa-3a26-9a6b-aa7badb70e65","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"4ea93ecb-5fdd-3e43-9a39-9c2591c87cde","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4ea93ecb-5fdd-3e43-9a39-9c2591c87cde","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4ea93ecb-5fdd-3e43-9a39-9c2591c87cde","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4ea93ecb-5fdd-3e43-9a39-9c2591c87cde","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4ea93ecb-5fdd-3e43-9a39-9c2591c87cde","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4ea93ecb-5fdd-3e43-9a39-9c2591c87cde","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"4ea93ecb-5fdd-3e43-9a39-9c2591c87cde","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"4ea93ecb-5fdd-3e43-9a39-9c2591c87cde"},{"kind":"sourceEvent","id":"45ee3c02-4059-310d-832c-ec4c34665ea1"},{"kind":"sourceEvent","id":"39154aa2-e3e6-370a-bec9-32620ab2db19"},{"kind":"sourceEvent","id":"90bc1067-33c3-3680-a5e2-b2004d03d902"},{"kind":"sourceEvent","id":"9d6fa9ed-336c-34fb-b27f-436ff91c22bc"},{"kind":"sourceEvent","id":"9bb0122b-174a-30c3-811b-4b673afa1f01"},{"kind":"sourceEvent","id":"e2de23a5-b3cd-3c19-9ba4-c8f811dc7a8a"},{"kind":"sourceEvent","id":"3eb56fba-ae9c-3978-9a00-be763fc213e0"},{"kind":"sourceEvent","id":"1b498c81-2ce4-380f-b0fc-73a3a9ca6e8f"},{"kind":"sourceEvent","id":"7136a0e0-8d84-3333-906c-46c35e03f7b6"},{"kind":"observation","id":"fec34e84-23f0-3131-9f7d-3e2413b761c1"},{"kind":"inventoryScope","id":"88edb641-fcfa-3a26-9a6b-aa7badb70e65"},{"kind":"sourceAvailability","id":"4ea93ecb-5fdd-3e43-9a39-9c2591c87cde/LOANS"},{"kind":"sourceAvailability","id":"4ea93ecb-5fdd-3e43-9a39-9c2591c87cde/RETURNS"},{"kind":"sourceAvailability","id":"4ea93ecb-5fdd-3e43-9a39-9c2591c87cde/RECEIPTS"},{"kind":"sourceAvailability","id":"4ea93ecb-5fdd-3e43-9a39-9c2591c87cde/TRANSFERS"},{"kind":"sourceAvailability","id":"4ea93ecb-5fdd-3e43-9a39-9c2591c87cde/REPAIRS"},{"kind":"sourceAvailability","id":"4ea93ecb-5fdd-3e43-9a39-9c2591c87cde/INTEGRATIONS"},{"kind":"sourceAvailability","id":"4ea93ecb-5fdd-3e43-9a39-9c2591c87cde/INVENTORY"},{"kind":"inventoryScope","id":"8021dca8-65fe-3cab-985e-d4fb9df5c308"},{"kind":"inventoryScope","id":"b0027657-6abb-37d2-8bc4-9341357dfe79"},{"kind":"inventoryScope","id":"4757f5eb-f104-3413-b118-51a6951a650c"},{"kind":"inventoryScope","id":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592"},{"kind":"inventoryScope","id":"40e94818-3a62-34a4-b461-398d7b9ffca2"},{"kind":"inventoryScope","id":"ed60c0be-b218-3f9e-8aa8-69c16f76c22e"},{"kind":"inventoryScope","id":"35514ff7-97f8-345d-8cf7-6cee969a726c"},{"kind":"inventoryScope","id":"351b3e2d-1bff-3290-938e-527c15f97274"},{"kind":"inventoryScope","id":"7bda04de-7eea-3647-9e69-f3610f2ce443"}]`

- Session : `{"scopeId":"88edb641-fcfa-3a26-9a6b-aa7badb70e65","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"8021dca8-65fe-3cab-985e-d4fb9df5c308","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"b0027657-6abb-37d2-8bc4-9341357dfe79","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4757f5eb-f104-3413-b118-51a6951a650c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"4ad799a8-d94a-3a00-b0fb-f6b1120b6592","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"40e94818-3a62-34a4-b461-398d7b9ffca2","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"ed60c0be-b218-3f9e-8aa8-69c16f76c22e","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"35514ff7-97f8-345d-8cf7-6cee969a726c","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"351b3e2d-1bff-3290-938e-527c15f97274","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"7bda04de-7eea-3647-9e69-f3610f2ce443","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas cd0d747f-a28d-323a-b8f8-de6bcef24d8d

- Exemplaire : `f1aeb278-367f-3124-b7ed-735963c40bfb`
- Famille privée : DEUX_CAUSES ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"c70d4857-f6b2-3587-83f6-029ae824f193","kind":"STATUS_MISMATCH","expectedValue":"IN_REPAIR","observedValue":"IN_REPAIR"},{"symptomId":"bb287167-4793-38c3-b7ba-78f3d5c47bd4","kind":"LOCATION_MISMATCH","expectedValue":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","observedValue":"b8600ac8-b5d3-3580-b3c5-71a711df1a50"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["REPAIR_LOCATION_CHANGED"],"hiddenSourceEvents":[],"mechanism":"Réparation engagée puis déplacement distinct de l'atelier vers la réserve sans mise à jour de l'affectation de réparation.","businessEventId":"8f3f5499-a462-3262-9d20-cb27c1a2c2e8","physicalStatus":"IN_REPAIR","physicalSiteId":"CENTRE","physicalLocationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_LOCATION_MISMATCH"],"explanation":"Réparation correctement projetée après reprise ; seul le déplacement hors atelier reste étayé.","forbiddenClaims":["Deux causes confirmées par un scan ambigu","Vol"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"f1aeb278-367f-3124-b7ed-735963c40bfb","status":"IN_REPAIR","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","sourceEventIds":["8f3f5499-a462-3262-9d20-cb27c1a2c2e8","1f334dd0-8b95-3582-bc09-e7c6b9cd9050"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 11601460-c0bd-3218-810a-edfa70d1c31f | 2026-06-01T20:16:27Z | 2026-06-01T20:16:37Z | `{"operationId":"25708982-790e-3023-9bc4-a2367c39b570","correlationId":"e6c71ad1-445d-3a9b-8141-5ccb2fa861a0","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"3486ecf0-1d29-3d8b-bc61-af59f5cfbb7c"}` |
| IntegrationProcessed | 7cce2478-3281-3c8b-9683-b899280c0f63 | 2026-06-02T20:16:27Z | 2026-06-02T20:16:37Z | `{"originalEventId":"11601460-c0bd-3218-810a-edfa70d1c31f","correlationId":"e6c71ad1-445d-3a9b-8141-5ccb2fa861a0","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 491756a1-29b5-32f3-88e2-1f108df589f4 | 2026-07-31T20:16:27Z | 2026-07-31T20:16:37Z | `{"operationId":"cfad6530-6242-3b01-bd2d-fb7f0ed8bf9b","correlationId":"371803da-3c74-3f50-bc2d-45f75570c34a","siteId":"CENTRE","locationId":null,"loanId":"7485dfdc-3f40-345a-8ce2-280661263519"}` |
| IntegrationProcessed | b33ddd9e-2cae-38b2-973f-cd05ec5d1688 | 2026-07-31T20:17:27Z | 2026-07-31T20:17:37Z | `{"originalEventId":"491756a1-29b5-32f3-88e2-1f108df589f4","correlationId":"371803da-3c74-3f50-bc2d-45f75570c34a","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 71bfe208-6522-3a92-bc1d-9526d59aebed | 2026-08-20T20:16:27Z | 2026-08-20T20:16:37Z | `{"operationId":"e11970d8-cd22-3e50-a93a-7c119dc36a8c","correlationId":"42905904-ac1c-32c7-8ad1-99c872736621","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"7485dfdc-3f40-345a-8ce2-280661263519"}` |
| ReturnReceived | 8cc8bf69-9f7d-3659-b492-fb8e647e1e7a | 2026-08-20T20:16:57Z | 2026-08-20T20:17:07Z | `{"operationId":"58932c57-22fa-3f60-955b-4d164fbe3602","correlationId":"08ed8884-aa55-39ee-9455-e0c01d842fe4","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"7485dfdc-3f40-345a-8ce2-280661263519"}` |
| IntegrationProcessed | b64dc7fb-07c6-31f1-9ad1-a037db29cfea | 2026-08-21T20:16:27Z | 2026-08-21T20:16:37Z | `{"originalEventId":"8cc8bf69-9f7d-3659-b492-fb8e647e1e7a","correlationId":"08ed8884-aa55-39ee-9455-e0c01d842fe4","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| RepairStarted | 8f3f5499-a462-3262-9d20-cb27c1a2c2e8 | 2026-09-27T11:07:53Z | 2026-09-27T11:08:03Z | `{"operationId":"ca6a8ecd-213c-3219-9513-e2c99470af3a","correlationId":"9ffe88c0-c0fc-3240-8115-5053b6d915fb","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","repairId":"590fb61f-2fb9-35c0-a431-49cac322fb86"}` |
| IntegrationProcessed | 2af54385-1c05-32e6-b266-b598adfcd3ac | 2026-09-27T11:08:23Z | 2026-09-27T11:08:33Z | `{"originalEventId":"8f3f5499-a462-3262-9d20-cb27c1a2c2e8","correlationId":"9ffe88c0-c0fc-3240-8115-5053b6d915fb","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| IntegrationProcessed | 1f334dd0-8b95-3582-bc09-e7c6b9cd9050 | 2026-09-27T11:09:23Z | 2026-09-27T11:09:33Z | `{"originalEventId":"8f3f5499-a462-3262-9d20-cb27c1a2c2e8","correlationId":"9ffe88c0-c0fc-3240-8115-5053b6d915fb","outcome":"APPLIED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be"}` |

**observations**
- `{"observationId":"dd27cbd4-7e5d-35a0-967e-15a204542157","copyId":"f1aeb278-367f-3124-b7ed-735963c40bfb","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","scopeId":"82f06c91-c91e-30a7-9588-c30f5bb7d588","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"f1aeb278-367f-3124-b7ed-735963c40bfb","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f1aeb278-367f-3124-b7ed-735963c40bfb","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f1aeb278-367f-3124-b7ed-735963c40bfb","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f1aeb278-367f-3124-b7ed-735963c40bfb","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f1aeb278-367f-3124-b7ed-735963c40bfb","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f1aeb278-367f-3124-b7ed-735963c40bfb","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f1aeb278-367f-3124-b7ed-735963c40bfb","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"f1aeb278-367f-3124-b7ed-735963c40bfb"},{"kind":"sourceEvent","id":"11601460-c0bd-3218-810a-edfa70d1c31f"},{"kind":"sourceEvent","id":"7cce2478-3281-3c8b-9683-b899280c0f63"},{"kind":"sourceEvent","id":"491756a1-29b5-32f3-88e2-1f108df589f4"},{"kind":"sourceEvent","id":"b33ddd9e-2cae-38b2-973f-cd05ec5d1688"},{"kind":"sourceEvent","id":"71bfe208-6522-3a92-bc1d-9526d59aebed"},{"kind":"sourceEvent","id":"8cc8bf69-9f7d-3659-b492-fb8e647e1e7a"},{"kind":"sourceEvent","id":"b64dc7fb-07c6-31f1-9ad1-a037db29cfea"},{"kind":"sourceEvent","id":"8f3f5499-a462-3262-9d20-cb27c1a2c2e8"},{"kind":"sourceEvent","id":"2af54385-1c05-32e6-b266-b598adfcd3ac"},{"kind":"sourceEvent","id":"1f334dd0-8b95-3582-bc09-e7c6b9cd9050"},{"kind":"observation","id":"dd27cbd4-7e5d-35a0-967e-15a204542157"},{"kind":"inventoryScope","id":"82f06c91-c91e-30a7-9588-c30f5bb7d588"},{"kind":"sourceAvailability","id":"f1aeb278-367f-3124-b7ed-735963c40bfb/LOANS"},{"kind":"sourceAvailability","id":"f1aeb278-367f-3124-b7ed-735963c40bfb/RETURNS"},{"kind":"sourceAvailability","id":"f1aeb278-367f-3124-b7ed-735963c40bfb/RECEIPTS"},{"kind":"sourceAvailability","id":"f1aeb278-367f-3124-b7ed-735963c40bfb/TRANSFERS"},{"kind":"sourceAvailability","id":"f1aeb278-367f-3124-b7ed-735963c40bfb/REPAIRS"},{"kind":"sourceAvailability","id":"f1aeb278-367f-3124-b7ed-735963c40bfb/INTEGRATIONS"},{"kind":"sourceAvailability","id":"f1aeb278-367f-3124-b7ed-735963c40bfb/INVENTORY"},{"kind":"inventoryScope","id":"fc22847f-af8c-366f-be86-355e86e7e988"},{"kind":"inventoryScope","id":"e40f4833-d76c-3015-95f9-c5c4c5316123"},{"kind":"inventoryScope","id":"dc38a3c7-811a-3c7e-9d52-13be1b9914cd"},{"kind":"inventoryScope","id":"9be0d6d0-cc8d-3d92-92f6-b5291f868628"},{"kind":"inventoryScope","id":"12e94e75-b47d-323a-913a-d261320a99e1"},{"kind":"inventoryScope","id":"64d8a581-eb79-3896-a532-b4a4254d76fe"},{"kind":"inventoryScope","id":"f67d2862-9684-34ec-92de-3f36a2e82aa0"},{"kind":"inventoryScope","id":"171f3784-92b9-3ed7-9ea2-6a546847ae52"}]`

- Session : `{"scopeId":"82f06c91-c91e-30a7-9588-c30f5bb7d588","locationId":"b8600ac8-b5d3-3580-b3c5-71a711df1a50","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"fc22847f-af8c-366f-be86-355e86e7e988","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"e40f4833-d76c-3015-95f9-c5c4c5316123","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"dc38a3c7-811a-3c7e-9d52-13be1b9914cd","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9be0d6d0-cc8d-3d92-92f6-b5291f868628","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"12e94e75-b47d-323a-913a-d261320a99e1","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"64d8a581-eb79-3896-a532-b4a4254d76fe","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"f67d2862-9684-34ec-92de-3f36a2e82aa0","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"171f3784-92b9-3ed7-9ea2-6a546847ae52","locationId":"7ff71b2b-bd42-34c4-a7fc-8a8584bd56be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas d35c6b8f-7cf5-3ccd-b7f5-1a23456c5e70

- Exemplaire : `9cfece2f-fd8d-3ef0-9baa-a8a860033016`
- Famille privée : TRANSFERT_INCOHERENT ; variante : POSITIVE
- Symptômes : `[{"symptomId":"c774840e-aea1-333d-94a8-26a70940336d","kind":"STATUS_MISMATCH","expectedValue":"IN_TRANSIT","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"e3f4e83c-6ce8-3c2c-b665-3b2f56c3854e","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["TRANSFER_RECEIPT_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"9cfece2f-fd8d-3ef0-9baa-a8a860033016","status":"IN_TRANSIT","siteId":"CENTRE","locationId":null,"sourceEventIds":["ef98cb0c-d9aa-302f-a570-1da196038cc5","313c1a21-1314-3fd5-bc74-d58718278ada"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | a216e374-d10b-3f9d-ab9f-0fe512ca41b9 | 2026-06-02T00:49:15Z | 2026-06-02T00:49:25Z | `{"operationId":"c7147a03-0227-3923-8061-fab0e6b98f0f","correlationId":"29d422c5-68bc-3540-a54f-cd4a48b94a48","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"8f868a50-4f15-37d7-ab4b-6a857f4d80d5"}` |
| IntegrationProcessed | 394c2c57-b887-35ca-b6e2-e3015987ce44 | 2026-06-03T00:49:15Z | 2026-06-03T00:49:25Z | `{"originalEventId":"a216e374-d10b-3f9d-ab9f-0fe512ca41b9","correlationId":"29d422c5-68bc-3540-a54f-cd4a48b94a48","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 0c90bd3b-c571-3fb9-ac55-0c91d6f9abc6 | 2026-08-01T00:49:15Z | 2026-08-01T00:49:25Z | `{"operationId":"8c93a10f-46d2-338e-bd49-81377f6d27e5","correlationId":"f3ba05a3-64b4-310e-87e2-2f4bcb534efb","siteId":"CENTRE","locationId":null,"loanId":"e193bfec-3da6-388b-bacc-1c74bdf14391"}` |
| IntegrationProcessed | 5b115991-8af5-327b-b24d-761f8783e000 | 2026-08-01T00:50:15Z | 2026-08-01T00:50:25Z | `{"originalEventId":"0c90bd3b-c571-3fb9-ac55-0c91d6f9abc6","correlationId":"f3ba05a3-64b4-310e-87e2-2f4bcb534efb","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | 407d97b0-97a7-3bbd-986c-abf02a93f0b1 | 2026-08-21T00:49:15Z | 2026-08-21T00:49:25Z | `{"operationId":"5a141025-6594-392b-801e-08a6fa14f607","correlationId":"267068b7-e8e5-3aff-951a-8e29d18fec60","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"e193bfec-3da6-388b-bacc-1c74bdf14391"}` |
| ReturnReceived | a3b29c51-2042-3918-86d3-bd8fcd037bba | 2026-08-21T00:49:45Z | 2026-08-21T00:49:55Z | `{"operationId":"5e19cdb9-a102-3bcc-8b50-d40e4b210d98","correlationId":"6e3e0e09-3705-3b60-aae6-66fc24fdf4ae","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"e193bfec-3da6-388b-bacc-1c74bdf14391"}` |
| IntegrationProcessed | 31ec4438-1272-3eca-8987-f986286f31d2 | 2026-08-22T00:49:15Z | 2026-08-22T00:49:25Z | `{"originalEventId":"a3b29c51-2042-3918-86d3-bd8fcd037bba","correlationId":"6e3e0e09-3705-3b60-aae6-66fc24fdf4ae","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| TransferDispatched | ef98cb0c-d9aa-302f-a570-1da196038cc5 | 2026-09-26T08:20:42Z | 2026-09-26T08:20:52Z | `{"operationId":"f9d02aa1-6649-3984-b49d-b4a9ec274857","correlationId":"ac8ce842-45f3-3391-8777-4b22be0c87dd","siteId":"CENTRE","locationId":null,"transferId":"f1dbb559-27c2-39a4-a5f8-4923870346a6","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | 313c1a21-1314-3fd5-bc74-d58718278ada | 2026-09-26T08:21:42Z | 2026-09-26T08:21:52Z | `{"originalEventId":"ef98cb0c-d9aa-302f-a570-1da196038cc5","correlationId":"ac8ce842-45f3-3391-8777-4b22be0c87dd","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| TransferReceived | e3f4e83c-6ce8-3c2c-b665-3b2f56c3854e | 2026-09-27T08:20:42Z | 2026-09-27T08:20:52Z | `{"operationId":"df876e85-fa8d-3412-9614-ecf25151a3aa","correlationId":"1d7c184b-032e-388a-94dc-cdac56e6846b","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","transferId":"f1dbb559-27c2-39a4-a5f8-4923870346a6","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | f413b411-8d70-3eaa-a316-4daec49ff4f5 | 2026-09-27T08:21:12Z | 2026-09-27T08:21:22Z | `{"originalEventId":"e3f4e83c-6ce8-3c2c-b665-3b2f56c3854e","correlationId":"1d7c184b-032e-388a-94dc-cdac56e6846b","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**observations**
- `{"observationId":"dcdae375-1a15-32e4-8d64-cb9bc07b6a5f","copyId":"9cfece2f-fd8d-3ef0-9baa-a8a860033016","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","scopeId":"be246416-e1d7-3d56-9b43-52041a130c0f","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"9cfece2f-fd8d-3ef0-9baa-a8a860033016","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9cfece2f-fd8d-3ef0-9baa-a8a860033016","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9cfece2f-fd8d-3ef0-9baa-a8a860033016","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9cfece2f-fd8d-3ef0-9baa-a8a860033016","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9cfece2f-fd8d-3ef0-9baa-a8a860033016","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9cfece2f-fd8d-3ef0-9baa-a8a860033016","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9cfece2f-fd8d-3ef0-9baa-a8a860033016","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"9cfece2f-fd8d-3ef0-9baa-a8a860033016"},{"kind":"sourceEvent","id":"a216e374-d10b-3f9d-ab9f-0fe512ca41b9"},{"kind":"sourceEvent","id":"394c2c57-b887-35ca-b6e2-e3015987ce44"},{"kind":"sourceEvent","id":"0c90bd3b-c571-3fb9-ac55-0c91d6f9abc6"},{"kind":"sourceEvent","id":"5b115991-8af5-327b-b24d-761f8783e000"},{"kind":"sourceEvent","id":"407d97b0-97a7-3bbd-986c-abf02a93f0b1"},{"kind":"sourceEvent","id":"a3b29c51-2042-3918-86d3-bd8fcd037bba"},{"kind":"sourceEvent","id":"31ec4438-1272-3eca-8987-f986286f31d2"},{"kind":"sourceEvent","id":"ef98cb0c-d9aa-302f-a570-1da196038cc5"},{"kind":"sourceEvent","id":"313c1a21-1314-3fd5-bc74-d58718278ada"},{"kind":"sourceEvent","id":"e3f4e83c-6ce8-3c2c-b665-3b2f56c3854e"},{"kind":"sourceEvent","id":"f413b411-8d70-3eaa-a316-4daec49ff4f5"},{"kind":"observation","id":"dcdae375-1a15-32e4-8d64-cb9bc07b6a5f"},{"kind":"inventoryScope","id":"be246416-e1d7-3d56-9b43-52041a130c0f"},{"kind":"sourceAvailability","id":"9cfece2f-fd8d-3ef0-9baa-a8a860033016/LOANS"},{"kind":"sourceAvailability","id":"9cfece2f-fd8d-3ef0-9baa-a8a860033016/RETURNS"},{"kind":"sourceAvailability","id":"9cfece2f-fd8d-3ef0-9baa-a8a860033016/RECEIPTS"},{"kind":"sourceAvailability","id":"9cfece2f-fd8d-3ef0-9baa-a8a860033016/TRANSFERS"},{"kind":"sourceAvailability","id":"9cfece2f-fd8d-3ef0-9baa-a8a860033016/REPAIRS"},{"kind":"sourceAvailability","id":"9cfece2f-fd8d-3ef0-9baa-a8a860033016/INTEGRATIONS"},{"kind":"sourceAvailability","id":"9cfece2f-fd8d-3ef0-9baa-a8a860033016/INVENTORY"}]`

- Session : `{"scopeId":"be246416-e1d7-3d56-9b43-52041a130c0f","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas d62ee162-4968-32f6-9be3-4f8a5507ea09

- Exemplaire : `c17b6b1e-5bb8-3aa4-8e0b-23f51bf0f607`
- Famille privée : ABSENCE_INEXPLIQUEE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"652407ba-f163-3590-a8d2-a9389e0474a7","kind":"MISSING","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["UNOBSERVED_MOVE"],"mechanism":"Exemplaire déplacé hors du périmètre instrumenté ; la destination n'est pas exposée et aucune perte n'est simulée.","physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":null,"evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"L'absence de lecture ne démontre ni la destination, ni une perte, ni un vol ; données insuffisantes pour attribuer une cause.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":["Réaliser un inventaire contradictoire","Compléter les journaux métier"]}`
- État projeté : `{"copyId":"c17b6b1e-5bb8-3aa4-8e0b-23f51bf0f607","status":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","sourceEventIds":["d6e9ec80-8a9b-3661-9d50-53d9adad8412","b4a60d3d-36cd-3164-b211-79646836fb89"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 522c870c-81bc-3af0-bb12-5a88a6e917b8 | 2026-06-01T16:45:29Z | 2026-06-01T16:45:39Z | `{"operationId":"04addc48-734c-3e6e-9687-ac970dca7e39","correlationId":"6974d137-add6-3ce9-80aa-c54d55335f69","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"ace77404-8f2f-3a13-8566-62b0a1b584b7"}` |
| IntegrationProcessed | e6d9ebc2-5746-3add-ad11-24fbf0773c3d | 2026-06-02T16:45:29Z | 2026-06-02T16:45:39Z | `{"originalEventId":"522c870c-81bc-3af0-bb12-5a88a6e917b8","correlationId":"6974d137-add6-3ce9-80aa-c54d55335f69","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | aeda9846-cd12-3a95-b960-2606d7b540be | 2026-07-31T16:45:29Z | 2026-07-31T16:45:39Z | `{"operationId":"d8e721b8-7ff4-342d-834c-50961869fbcb","correlationId":"4d5c4d3c-6fc5-39e2-beef-01ada4279fd6","siteId":"NORD","locationId":null,"loanId":"f4e1d1f1-06c6-3b8f-bdbf-75953b424c15"}` |
| IntegrationProcessed | c7065259-ad7d-30e6-bc3e-7b77d44a13f4 | 2026-07-31T16:46:29Z | 2026-07-31T16:46:39Z | `{"originalEventId":"aeda9846-cd12-3a95-b960-2606d7b540be","correlationId":"4d5c4d3c-6fc5-39e2-beef-01ada4279fd6","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | f517f6fe-b417-356c-9392-6eabc9f2334a | 2026-08-20T16:45:29Z | 2026-08-20T16:45:39Z | `{"operationId":"c82c8a29-3aef-3859-9a12-08f42a446185","correlationId":"42773912-8821-3a63-89c5-3f6f46939048","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"f4e1d1f1-06c6-3b8f-bdbf-75953b424c15"}` |
| ReturnReceived | d6e9ec80-8a9b-3661-9d50-53d9adad8412 | 2026-08-20T16:45:59Z | 2026-08-20T16:46:09Z | `{"operationId":"695795dc-d7df-357d-8918-a3cb16436a85","correlationId":"96e4725f-1db1-37bc-ab52-f2e8d08eba5c","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"f4e1d1f1-06c6-3b8f-bdbf-75953b424c15"}` |
| IntegrationProcessed | b4a60d3d-36cd-3164-b211-79646836fb89 | 2026-08-21T16:45:29Z | 2026-08-21T16:45:39Z | `{"originalEventId":"d6e9ec80-8a9b-3661-9d50-53d9adad8412","correlationId":"96e4725f-1db1-37bc-ab52-f2e8d08eba5c","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |

**observations**

**sourceAvailability**
- `{"copyId":"c17b6b1e-5bb8-3aa4-8e0b-23f51bf0f607","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c17b6b1e-5bb8-3aa4-8e0b-23f51bf0f607","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c17b6b1e-5bb8-3aa4-8e0b-23f51bf0f607","source":"TRANSFERS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"c17b6b1e-5bb8-3aa4-8e0b-23f51bf0f607","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c17b6b1e-5bb8-3aa4-8e0b-23f51bf0f607","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c17b6b1e-5bb8-3aa4-8e0b-23f51bf0f607","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c17b6b1e-5bb8-3aa4-8e0b-23f51bf0f607","source":"LOANS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`

**operatorNotes**
- `{"noteId":"a5c059be-af19-335b-a347-988ec63809ea","copyId":"c17b6b1e-5bb8-3aa4-8e0b-23f51bf0f607","text":"Le passage au rayon n'a pas permis de retrouver cet exemplaire ; contrôle complémentaire demandé.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"c17b6b1e-5bb8-3aa4-8e0b-23f51bf0f607"},{"kind":"sourceEvent","id":"522c870c-81bc-3af0-bb12-5a88a6e917b8"},{"kind":"sourceEvent","id":"e6d9ebc2-5746-3add-ad11-24fbf0773c3d"},{"kind":"sourceEvent","id":"aeda9846-cd12-3a95-b960-2606d7b540be"},{"kind":"sourceEvent","id":"c7065259-ad7d-30e6-bc3e-7b77d44a13f4"},{"kind":"sourceEvent","id":"f517f6fe-b417-356c-9392-6eabc9f2334a"},{"kind":"sourceEvent","id":"d6e9ec80-8a9b-3661-9d50-53d9adad8412"},{"kind":"sourceEvent","id":"b4a60d3d-36cd-3164-b211-79646836fb89"},{"kind":"sourceAvailability","id":"c17b6b1e-5bb8-3aa4-8e0b-23f51bf0f607/LOANS"},{"kind":"sourceAvailability","id":"c17b6b1e-5bb8-3aa4-8e0b-23f51bf0f607/RETURNS"},{"kind":"sourceAvailability","id":"c17b6b1e-5bb8-3aa4-8e0b-23f51bf0f607/RECEIPTS"},{"kind":"sourceAvailability","id":"c17b6b1e-5bb8-3aa4-8e0b-23f51bf0f607/TRANSFERS"},{"kind":"sourceAvailability","id":"c17b6b1e-5bb8-3aa4-8e0b-23f51bf0f607/REPAIRS"},{"kind":"sourceAvailability","id":"c17b6b1e-5bb8-3aa4-8e0b-23f51bf0f607/INTEGRATIONS"},{"kind":"sourceAvailability","id":"c17b6b1e-5bb8-3aa4-8e0b-23f51bf0f607/INVENTORY"},{"kind":"operatorNote","id":"a5c059be-af19-335b-a347-988ec63809ea"},{"kind":"inventoryScope","id":"07c957d2-b086-3b6b-9915-d21f1d5906cf"},{"kind":"inventoryScope","id":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c"},{"kind":"inventoryScope","id":"a481af0e-6045-37c4-bb84-d5269f5addc5"},{"kind":"inventoryScope","id":"9880a39c-a8f4-3f61-a473-804a0fd611a5"},{"kind":"inventoryScope","id":"79157bb9-b809-3388-ad1a-42d980616ea0"},{"kind":"inventoryScope","id":"eba23a81-5103-3ded-ba6b-6619213a7840"},{"kind":"inventoryScope","id":"6c49954b-e555-36a5-ab71-af60ea210c9b"},{"kind":"inventoryScope","id":"6c49954b-e555-36a5-ab71-af60ea210c9b"}]`

- Session : `{"scopeId":"07c957d2-b086-3b6b-9915-d21f1d5906cf","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"a481af0e-6045-37c4-bb84-d5269f5addc5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9880a39c-a8f4-3f61-a473-804a0fd611a5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"79157bb9-b809-3388-ad1a-42d980616ea0","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"eba23a81-5103-3ded-ba6b-6619213a7840","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"6c49954b-e555-36a5-ab71-af60ea210c9b","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"6c49954b-e555-36a5-ab71-af60ea210c9b","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas d6d51de9-c573-3098-a0d1-754ff206002b

- Exemplaire : `ff7df138-1655-37c7-a911-dd8222af3dd5`
- Famille privée : RECEPTION_NON_INTEGREE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"d410bc95-db6e-35a5-9359-40e324fc0bf0","kind":"UNEXPECTED_PRESENCE","expectedValue":"NOT_REGISTERED","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"8408dc8c-c884-3279-bf2b-bc5a89f2f4c9","physicalStatus":"RECEIVING","physicalSiteId":"SUD","physicalLocationId":"8228c3af-c19d-30d4-95b5-30240d9818af","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["RECEIPT_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"ff7df138-1655-37c7-a911-dd8222af3dd5","status":"NOT_REGISTERED","siteId":"SUD","locationId":null,"sourceEventIds":[]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 8408dc8c-c884-3279-bf2b-bc5a89f2f4c9 | 2026-09-27T09:32:34Z | 2026-09-27T09:32:44Z | `{"operationId":"168d1e42-f4d1-3bba-95b8-fe30042b91be","correlationId":"8848445c-2a7c-3665-aa7e-e6ed6d6a2be6","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"399394e4-bd1f-3747-9039-aaafe186aa5a"}` |
| IntegrationProcessed | 8f4779ba-74f7-35c0-a3a5-f071a4b5b86b | 2026-09-27T09:33:04Z | 2026-09-27T09:33:14Z | `{"originalEventId":"8408dc8c-c884-3279-bf2b-bc5a89f2f4c9","correlationId":"8848445c-2a7c-3665-aa7e-e6ed6d6a2be6","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**observations**
- `{"observationId":"2c005ac7-8843-3b7e-85b9-33cb1e5534ca","copyId":"ff7df138-1655-37c7-a911-dd8222af3dd5","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","scopeId":"149f5e9e-4d86-3bca-80a1-a982302e6ec4","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"ff7df138-1655-37c7-a911-dd8222af3dd5","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ff7df138-1655-37c7-a911-dd8222af3dd5","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ff7df138-1655-37c7-a911-dd8222af3dd5","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ff7df138-1655-37c7-a911-dd8222af3dd5","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ff7df138-1655-37c7-a911-dd8222af3dd5","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ff7df138-1655-37c7-a911-dd8222af3dd5","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"ff7df138-1655-37c7-a911-dd8222af3dd5","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"ff7df138-1655-37c7-a911-dd8222af3dd5"},{"kind":"sourceEvent","id":"8408dc8c-c884-3279-bf2b-bc5a89f2f4c9"},{"kind":"sourceEvent","id":"8f4779ba-74f7-35c0-a3a5-f071a4b5b86b"},{"kind":"observation","id":"2c005ac7-8843-3b7e-85b9-33cb1e5534ca"},{"kind":"inventoryScope","id":"149f5e9e-4d86-3bca-80a1-a982302e6ec4"},{"kind":"sourceAvailability","id":"ff7df138-1655-37c7-a911-dd8222af3dd5/LOANS"},{"kind":"sourceAvailability","id":"ff7df138-1655-37c7-a911-dd8222af3dd5/RETURNS"},{"kind":"sourceAvailability","id":"ff7df138-1655-37c7-a911-dd8222af3dd5/RECEIPTS"},{"kind":"sourceAvailability","id":"ff7df138-1655-37c7-a911-dd8222af3dd5/TRANSFERS"},{"kind":"sourceAvailability","id":"ff7df138-1655-37c7-a911-dd8222af3dd5/REPAIRS"},{"kind":"sourceAvailability","id":"ff7df138-1655-37c7-a911-dd8222af3dd5/INTEGRATIONS"},{"kind":"sourceAvailability","id":"ff7df138-1655-37c7-a911-dd8222af3dd5/INVENTORY"}]`

- Session : `{"scopeId":"149f5e9e-4d86-3bca-80a1-a982302e6ec4","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas d98d1d3e-87b6-3112-a85a-f3842492c5e1

- Exemplaire : `16873cb7-8826-381c-bf06-e92e044d2651`
- Famille privée : RECEPTION_NON_INTEGREE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"46639d3c-cf04-30aa-b595-f4e5972e144b","kind":"UNEXPECTED_PRESENCE","expectedValue":"NOT_REGISTERED","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"3cd16326-17f9-3e14-ad8f-eb1736b63619","copyId":"16873cb7-8826-381c-bf06-e92e044d2651","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T08:04:51Z","recordedAt":"2026-09-27T08:05:01Z","payload":{"originalEventId":"48a3abfc-65c1-346a-b7f0-f69452931c6a","correlationId":"e065369f-cd44-3dce-9257-0c3df33ceefc","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"48a3abfc-65c1-346a-b7f0-f69452931c6a","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État projeté : `{"copyId":"16873cb7-8826-381c-bf06-e92e044d2651","status":"NOT_REGISTERED","siteId":"CENTRE","locationId":null,"sourceEventIds":[]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 48a3abfc-65c1-346a-b7f0-f69452931c6a | 2026-09-27T08:04:21Z | 2026-09-27T08:04:31Z | `{"operationId":"07d2c51a-1c00-3d80-a5c4-7dd920462aba","correlationId":"e065369f-cd44-3dce-9257-0c3df33ceefc","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"c0d5fe5b-6fac-36bb-997f-06edcee71a84"}` |

**observations**
- `{"observationId":"ac982850-0497-3c66-a265-ce6f5d35c8d7","copyId":"16873cb7-8826-381c-bf06-e92e044d2651","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","scopeId":"6e3d3b3a-fb11-3e6a-88f3-122e90d9059e","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**sourceAvailability**
- `{"copyId":"16873cb7-8826-381c-bf06-e92e044d2651","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"16873cb7-8826-381c-bf06-e92e044d2651","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"16873cb7-8826-381c-bf06-e92e044d2651","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"16873cb7-8826-381c-bf06-e92e044d2651","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"16873cb7-8826-381c-bf06-e92e044d2651","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"16873cb7-8826-381c-bf06-e92e044d2651","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"16873cb7-8826-381c-bf06-e92e044d2651","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"16873cb7-8826-381c-bf06-e92e044d2651"},{"kind":"sourceEvent","id":"48a3abfc-65c1-346a-b7f0-f69452931c6a"},{"kind":"observation","id":"ac982850-0497-3c66-a265-ce6f5d35c8d7"},{"kind":"inventoryScope","id":"6e3d3b3a-fb11-3e6a-88f3-122e90d9059e"},{"kind":"sourceAvailability","id":"16873cb7-8826-381c-bf06-e92e044d2651/LOANS"},{"kind":"sourceAvailability","id":"16873cb7-8826-381c-bf06-e92e044d2651/RETURNS"},{"kind":"sourceAvailability","id":"16873cb7-8826-381c-bf06-e92e044d2651/RECEIPTS"},{"kind":"sourceAvailability","id":"16873cb7-8826-381c-bf06-e92e044d2651/TRANSFERS"},{"kind":"sourceAvailability","id":"16873cb7-8826-381c-bf06-e92e044d2651/REPAIRS"},{"kind":"sourceAvailability","id":"16873cb7-8826-381c-bf06-e92e044d2651/INTEGRATIONS"},{"kind":"sourceAvailability","id":"16873cb7-8826-381c-bf06-e92e044d2651/INVENTORY"}]`

- Session : `{"scopeId":"6e3d3b3a-fb11-3e6a-88f3-122e90d9059e","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas e142ff2c-5ab4-3f24-8493-eca5701d0217

- Exemplaire : `98eb3a2f-0f10-3c78-a838-1503a047c9ed`
- Famille privée : TRANSFERT_INCOHERENT ; variante : POSITIVE
- Symptômes : `[{"symptomId":"929ff376-e37e-3498-b701-1d101466dc92","kind":"STATUS_MISMATCH","expectedValue":"IN_TRANSIT","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"4c6157a5-079b-3e42-9dd8-a9e72bda66aa","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["TRANSFER_RECEIPT_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"98eb3a2f-0f10-3c78-a838-1503a047c9ed","status":"IN_TRANSIT","siteId":"SUD","locationId":null,"sourceEventIds":["1b4e20fd-c4dc-3100-a2a1-c074e62f732e","a0e0d1ca-9b59-322a-8aa0-db4067b8c1e7"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | fc288072-e790-30ae-90a1-e1272320fc4d | 2026-06-02T00:35:22Z | 2026-06-02T00:35:32Z | `{"operationId":"a7a5702b-8568-3a8d-9ddf-c50160e26ff0","correlationId":"c437d44c-5eee-3aa5-ae92-c4248a0a02ee","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"ba4d1506-ce3f-30a8-b7aa-8e8f2d0f3dbc"}` |
| IntegrationProcessed | dfa819c9-efd4-3b06-b389-1884be0abf16 | 2026-06-03T00:35:22Z | 2026-06-03T00:35:32Z | `{"originalEventId":"fc288072-e790-30ae-90a1-e1272320fc4d","correlationId":"c437d44c-5eee-3aa5-ae92-c4248a0a02ee","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | e0e6b4c9-cfbd-37f5-a380-fb47ed890ee0 | 2026-08-01T00:35:22Z | 2026-08-01T00:35:32Z | `{"operationId":"b6644c9f-67e7-3bc5-a24f-4cfe849eec61","correlationId":"b2b859cc-8212-3bcc-984e-d74ec8a6bb87","siteId":"SUD","locationId":null,"loanId":"2df43cd6-c5f8-3ddd-a611-4575b19923c8"}` |
| IntegrationProcessed | def84f98-7670-37b5-be11-acffbf202a8d | 2026-08-01T00:36:22Z | 2026-08-01T00:36:32Z | `{"originalEventId":"e0e6b4c9-cfbd-37f5-a380-fb47ed890ee0","correlationId":"b2b859cc-8212-3bcc-984e-d74ec8a6bb87","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | af815527-2b8a-3df8-abac-5d898c080b20 | 2026-08-21T00:35:22Z | 2026-08-21T00:35:32Z | `{"operationId":"41c703b8-c825-3105-97f3-60afd66f74af","correlationId":"bbf41d02-a1e5-3b09-aa93-06b114edb0e5","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"2df43cd6-c5f8-3ddd-a611-4575b19923c8"}` |
| ReturnReceived | 640b6f59-9660-3b96-a05e-97dc517a213f | 2026-08-21T00:35:52Z | 2026-08-21T00:36:02Z | `{"operationId":"174d2051-abc1-3e76-8b4c-cac109f0da73","correlationId":"2d037a89-0e5e-3ff5-b36d-f0c9d3e599b7","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"2df43cd6-c5f8-3ddd-a611-4575b19923c8"}` |
| IntegrationProcessed | 805adb67-e8c0-3637-9ea4-00a11e381997 | 2026-08-22T00:35:22Z | 2026-08-22T00:35:32Z | `{"originalEventId":"640b6f59-9660-3b96-a05e-97dc517a213f","correlationId":"2d037a89-0e5e-3ff5-b36d-f0c9d3e599b7","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| TransferDispatched | 1b4e20fd-c4dc-3100-a2a1-c074e62f732e | 2026-09-26T06:09:33Z | 2026-09-26T06:09:43Z | `{"operationId":"eae77ecc-7c6c-3c4f-aafe-b6716e102e54","correlationId":"a13cf04a-ed67-3ff4-abdd-50027a0f7218","siteId":"SUD","locationId":null,"transferId":"de4298d8-7179-382e-a311-e6a1b10c8943","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| IntegrationProcessed | a0e0d1ca-9b59-322a-8aa0-db4067b8c1e7 | 2026-09-26T06:10:33Z | 2026-09-26T06:10:43Z | `{"originalEventId":"1b4e20fd-c4dc-3100-a2a1-c074e62f732e","correlationId":"a13cf04a-ed67-3ff4-abdd-50027a0f7218","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"SUD","locationId":null}` |
| TransferReceived | 4c6157a5-079b-3e42-9dd8-a9e72bda66aa | 2026-09-27T06:09:33Z | 2026-09-27T06:09:43Z | `{"operationId":"d6ff22ff-4ef7-3a02-aab2-5df22735045e","correlationId":"0d63dab2-cf98-31c5-b8c0-897d3af029cc","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","transferId":"de4298d8-7179-382e-a311-e6a1b10c8943","fromSiteId":"SUD","toSiteId":"CENTRE"}` |
| IntegrationProcessed | 0aefa8cd-58e5-31d9-8610-68f22df2f009 | 2026-09-27T06:10:03Z | 2026-09-27T06:10:13Z | `{"originalEventId":"4c6157a5-079b-3e42-9dd8-a9e72bda66aa","correlationId":"0d63dab2-cf98-31c5-b8c0-897d3af029cc","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**observations**
- `{"observationId":"fe8031ee-4347-3079-93ba-6b4cf3a26471","copyId":"98eb3a2f-0f10-3c78-a838-1503a047c9ed","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","scopeId":"59116f6f-fc86-3206-80a1-873afe081073","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"98eb3a2f-0f10-3c78-a838-1503a047c9ed","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"98eb3a2f-0f10-3c78-a838-1503a047c9ed","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"98eb3a2f-0f10-3c78-a838-1503a047c9ed","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"98eb3a2f-0f10-3c78-a838-1503a047c9ed","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"98eb3a2f-0f10-3c78-a838-1503a047c9ed","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"98eb3a2f-0f10-3c78-a838-1503a047c9ed","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"98eb3a2f-0f10-3c78-a838-1503a047c9ed","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"98eb3a2f-0f10-3c78-a838-1503a047c9ed"},{"kind":"sourceEvent","id":"fc288072-e790-30ae-90a1-e1272320fc4d"},{"kind":"sourceEvent","id":"dfa819c9-efd4-3b06-b389-1884be0abf16"},{"kind":"sourceEvent","id":"e0e6b4c9-cfbd-37f5-a380-fb47ed890ee0"},{"kind":"sourceEvent","id":"def84f98-7670-37b5-be11-acffbf202a8d"},{"kind":"sourceEvent","id":"af815527-2b8a-3df8-abac-5d898c080b20"},{"kind":"sourceEvent","id":"640b6f59-9660-3b96-a05e-97dc517a213f"},{"kind":"sourceEvent","id":"805adb67-e8c0-3637-9ea4-00a11e381997"},{"kind":"sourceEvent","id":"1b4e20fd-c4dc-3100-a2a1-c074e62f732e"},{"kind":"sourceEvent","id":"a0e0d1ca-9b59-322a-8aa0-db4067b8c1e7"},{"kind":"sourceEvent","id":"4c6157a5-079b-3e42-9dd8-a9e72bda66aa"},{"kind":"sourceEvent","id":"0aefa8cd-58e5-31d9-8610-68f22df2f009"},{"kind":"observation","id":"fe8031ee-4347-3079-93ba-6b4cf3a26471"},{"kind":"inventoryScope","id":"59116f6f-fc86-3206-80a1-873afe081073"},{"kind":"sourceAvailability","id":"98eb3a2f-0f10-3c78-a838-1503a047c9ed/LOANS"},{"kind":"sourceAvailability","id":"98eb3a2f-0f10-3c78-a838-1503a047c9ed/RETURNS"},{"kind":"sourceAvailability","id":"98eb3a2f-0f10-3c78-a838-1503a047c9ed/RECEIPTS"},{"kind":"sourceAvailability","id":"98eb3a2f-0f10-3c78-a838-1503a047c9ed/TRANSFERS"},{"kind":"sourceAvailability","id":"98eb3a2f-0f10-3c78-a838-1503a047c9ed/REPAIRS"},{"kind":"sourceAvailability","id":"98eb3a2f-0f10-3c78-a838-1503a047c9ed/INTEGRATIONS"},{"kind":"sourceAvailability","id":"98eb3a2f-0f10-3c78-a838-1503a047c9ed/INVENTORY"}]`

- Session : `{"scopeId":"59116f6f-fc86-3206-80a1-873afe081073","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas e214e6b3-cdb4-3445-9684-59b13b0062b4

- Exemplaire : `7539afe5-c637-3ec5-b63a-93a06a57ace7`
- Famille privée : RETOUR_NON_PROJETE ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"a8c8fd65-6770-3a94-bc68-eff0198fae9d","kind":"STATUS_MISMATCH","expectedValue":"ON_LOAN","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[{"sourceEventId":"c3147d59-865e-3566-bfb7-ce741aa78898","copyId":"7539afe5-c637-3ec5-b63a-93a06a57ace7","eventType":"IntegrationProcessed","occurredAt":"2026-09-27T09:23:59Z","recordedAt":"2026-09-27T09:24:09Z","payload":{"originalEventId":"5776e534-40bf-3639-8bef-82a17fdbffd9","correlationId":"6b7a1f72-2d55-383b-bf44-570e060f31b0","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}}],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"5776e534-40bf-3639-8bef-82a17fdbffd9","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","projectionPerturbation":"FAILED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État projeté : `{"copyId":"7539afe5-c637-3ec5-b63a-93a06a57ace7","status":"ON_LOAN","siteId":"NORD","locationId":null,"sourceEventIds":["06eaeb2f-58ac-3b5c-8aa6-7e545ddd974a","a31cd76c-b66c-32da-9d64-00415bccbe06"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | e1b07e11-6a65-3d33-9d4f-4a0aa9966559 | 2026-06-02T01:19:16Z | 2026-06-02T01:19:26Z | `{"operationId":"d4ca0c55-bdf3-3a0b-9b7f-e62ac01cd8f9","correlationId":"5aac5317-415a-3d1a-83f1-a857e373a919","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"6962978f-acc5-33a8-bcc6-026a0121b082"}` |
| IntegrationProcessed | a241b4a8-8728-32ce-ba90-55fae7db94c7 | 2026-06-03T01:19:16Z | 2026-06-03T01:19:26Z | `{"originalEventId":"e1b07e11-6a65-3d33-9d4f-4a0aa9966559","correlationId":"5aac5317-415a-3d1a-83f1-a857e373a919","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 925d17fc-9e87-3da0-a2f2-4da5cb4f70fc | 2026-08-01T01:19:16Z | 2026-08-01T01:19:26Z | `{"operationId":"d0a567bf-fc7c-3256-a578-155a2948074d","correlationId":"d95e7d6a-f6f4-349d-9f98-fd514cdfad7e","siteId":"NORD","locationId":null,"loanId":"f8a546fa-8301-3759-9200-c2e1341d0288"}` |
| IntegrationProcessed | b3a002ff-80f4-3383-815a-85d58189e36a | 2026-08-01T01:20:16Z | 2026-08-01T01:20:26Z | `{"originalEventId":"925d17fc-9e87-3da0-a2f2-4da5cb4f70fc","correlationId":"d95e7d6a-f6f4-349d-9f98-fd514cdfad7e","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | aebf4b57-c009-379c-8997-e785f1d320fb | 2026-08-21T01:19:16Z | 2026-08-21T01:19:26Z | `{"operationId":"0ba2e861-642e-3d74-ae68-f20b5c379ba1","correlationId":"0ac20f7a-acc1-3a7b-83d4-6fd2b900dea0","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"f8a546fa-8301-3759-9200-c2e1341d0288"}` |
| ReturnReceived | 07ffdb24-5522-3b80-ab65-a162fc53c380 | 2026-08-21T01:19:46Z | 2026-08-21T01:19:56Z | `{"operationId":"a6575bff-9aa6-3008-9d14-4c97e17e15f0","correlationId":"d7b46e5a-b925-308f-b5de-7e492ac236bc","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"f8a546fa-8301-3759-9200-c2e1341d0288"}` |
| IntegrationProcessed | 00424c0e-8ad7-3dcb-8671-4da5bedd6b1a | 2026-08-22T01:19:16Z | 2026-08-22T01:19:26Z | `{"originalEventId":"07ffdb24-5522-3b80-ab65-a162fc53c380","correlationId":"d7b46e5a-b925-308f-b5de-7e492ac236bc","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 06eaeb2f-58ac-3b5c-8aa6-7e545ddd974a | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"645e924b-e25b-3149-a58c-2cff1651d3e0","correlationId":"3ae384d6-f288-3eef-9f67-91a4ebc856d0","siteId":"NORD","locationId":null,"loanId":"047e3f69-b372-34c1-8518-7408d8e20540"}` |
| IntegrationProcessed | a31cd76c-b66c-32da-9d64-00415bccbe06 | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"06eaeb2f-58ac-3b5c-8aa6-7e545ddd974a","correlationId":"3ae384d6-f288-3eef-9f67-91a4ebc856d0","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | 61e7eca5-d3a5-329f-b59d-9d400691a90c | 2026-09-27T09:22:59Z | 2026-09-27T09:23:09Z | `{"operationId":"f2954bc6-4f84-3102-94c2-e2bb1425f224","correlationId":"597775d0-99ba-31ad-a34f-095f74bcd9d7","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"047e3f69-b372-34c1-8518-7408d8e20540"}` |
| ReturnReceived | 5776e534-40bf-3639-8bef-82a17fdbffd9 | 2026-09-27T09:23:29Z | 2026-09-27T09:23:39Z | `{"operationId":"0cf26124-ec25-3ee1-bc2e-79c45885537a","correlationId":"6b7a1f72-2d55-383b-bf44-570e060f31b0","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"047e3f69-b372-34c1-8518-7408d8e20540"}` |

**observations**
- `{"observationId":"b3cb729e-3fed-3ed4-be53-da08940a2f99","copyId":"7539afe5-c637-3ec5-b63a-93a06a57ace7","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","scopeId":"383e34d4-4027-3722-853d-fa49f26f9ea1","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**sourceAvailability**
- `{"copyId":"7539afe5-c637-3ec5-b63a-93a06a57ace7","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"7539afe5-c637-3ec5-b63a-93a06a57ace7","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"7539afe5-c637-3ec5-b63a-93a06a57ace7","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"7539afe5-c637-3ec5-b63a-93a06a57ace7","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"7539afe5-c637-3ec5-b63a-93a06a57ace7","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"7539afe5-c637-3ec5-b63a-93a06a57ace7","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"7539afe5-c637-3ec5-b63a-93a06a57ace7","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"7539afe5-c637-3ec5-b63a-93a06a57ace7"},{"kind":"sourceEvent","id":"e1b07e11-6a65-3d33-9d4f-4a0aa9966559"},{"kind":"sourceEvent","id":"a241b4a8-8728-32ce-ba90-55fae7db94c7"},{"kind":"sourceEvent","id":"925d17fc-9e87-3da0-a2f2-4da5cb4f70fc"},{"kind":"sourceEvent","id":"b3a002ff-80f4-3383-815a-85d58189e36a"},{"kind":"sourceEvent","id":"aebf4b57-c009-379c-8997-e785f1d320fb"},{"kind":"sourceEvent","id":"07ffdb24-5522-3b80-ab65-a162fc53c380"},{"kind":"sourceEvent","id":"00424c0e-8ad7-3dcb-8671-4da5bedd6b1a"},{"kind":"sourceEvent","id":"06eaeb2f-58ac-3b5c-8aa6-7e545ddd974a"},{"kind":"sourceEvent","id":"a31cd76c-b66c-32da-9d64-00415bccbe06"},{"kind":"sourceEvent","id":"61e7eca5-d3a5-329f-b59d-9d400691a90c"},{"kind":"sourceEvent","id":"5776e534-40bf-3639-8bef-82a17fdbffd9"},{"kind":"observation","id":"b3cb729e-3fed-3ed4-be53-da08940a2f99"},{"kind":"inventoryScope","id":"383e34d4-4027-3722-853d-fa49f26f9ea1"},{"kind":"sourceAvailability","id":"7539afe5-c637-3ec5-b63a-93a06a57ace7/LOANS"},{"kind":"sourceAvailability","id":"7539afe5-c637-3ec5-b63a-93a06a57ace7/RETURNS"},{"kind":"sourceAvailability","id":"7539afe5-c637-3ec5-b63a-93a06a57ace7/RECEIPTS"},{"kind":"sourceAvailability","id":"7539afe5-c637-3ec5-b63a-93a06a57ace7/TRANSFERS"},{"kind":"sourceAvailability","id":"7539afe5-c637-3ec5-b63a-93a06a57ace7/REPAIRS"},{"kind":"sourceAvailability","id":"7539afe5-c637-3ec5-b63a-93a06a57ace7/INTEGRATIONS"},{"kind":"sourceAvailability","id":"7539afe5-c637-3ec5-b63a-93a06a57ace7/INVENTORY"}]`

- Session : `{"scopeId":"383e34d4-4027-3722-853d-fa49f26f9ea1","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas e2e647d2-6aa5-3ce0-9265-3652366533ea

- Exemplaire : `f0bd37f6-8f56-3542-bdb5-5b08d7036c77`
- Famille privée : RECEPTION_NON_INTEGREE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"92c84718-2376-38aa-b011-1f80ad84bc66","kind":"UNEXPECTED_PRESENCE","expectedValue":"NOT_REGISTERED","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"1b6e7148-d080-3b6f-9fcc-272d52f85f04","physicalStatus":"RECEIVING","physicalSiteId":"CENTRE","physicalLocationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["RECEIPT_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"f0bd37f6-8f56-3542-bdb5-5b08d7036c77","status":"NOT_REGISTERED","siteId":"CENTRE","locationId":null,"sourceEventIds":[]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 1b6e7148-d080-3b6f-9fcc-272d52f85f04 | 2026-09-27T06:03:17Z | 2026-09-27T06:03:27Z | `{"operationId":"82cfe59f-a286-32c6-b12d-6fe1043ab147","correlationId":"b9390778-579e-39a4-9698-053affe08cee","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"01bdf6c0-5fb2-35f8-8b9f-bbfea23d6cba"}` |
| IntegrationProcessed | ed87fe66-52b2-32e1-947c-0c4b5df066a7 | 2026-09-27T06:03:47Z | 2026-09-27T06:03:57Z | `{"originalEventId":"1b6e7148-d080-3b6f-9fcc-272d52f85f04","correlationId":"b9390778-579e-39a4-9698-053affe08cee","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**observations**
- `{"observationId":"359fa296-b9f8-3031-a85a-ae9005d3a8a5","copyId":"f0bd37f6-8f56-3542-bdb5-5b08d7036c77","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","scopeId":"561e4db8-b71d-30ae-ba0e-f61099efa960","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"f0bd37f6-8f56-3542-bdb5-5b08d7036c77","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f0bd37f6-8f56-3542-bdb5-5b08d7036c77","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f0bd37f6-8f56-3542-bdb5-5b08d7036c77","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f0bd37f6-8f56-3542-bdb5-5b08d7036c77","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f0bd37f6-8f56-3542-bdb5-5b08d7036c77","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f0bd37f6-8f56-3542-bdb5-5b08d7036c77","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f0bd37f6-8f56-3542-bdb5-5b08d7036c77","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"f0bd37f6-8f56-3542-bdb5-5b08d7036c77"},{"kind":"sourceEvent","id":"1b6e7148-d080-3b6f-9fcc-272d52f85f04"},{"kind":"sourceEvent","id":"ed87fe66-52b2-32e1-947c-0c4b5df066a7"},{"kind":"observation","id":"359fa296-b9f8-3031-a85a-ae9005d3a8a5"},{"kind":"inventoryScope","id":"561e4db8-b71d-30ae-ba0e-f61099efa960"},{"kind":"sourceAvailability","id":"f0bd37f6-8f56-3542-bdb5-5b08d7036c77/LOANS"},{"kind":"sourceAvailability","id":"f0bd37f6-8f56-3542-bdb5-5b08d7036c77/RETURNS"},{"kind":"sourceAvailability","id":"f0bd37f6-8f56-3542-bdb5-5b08d7036c77/RECEIPTS"},{"kind":"sourceAvailability","id":"f0bd37f6-8f56-3542-bdb5-5b08d7036c77/TRANSFERS"},{"kind":"sourceAvailability","id":"f0bd37f6-8f56-3542-bdb5-5b08d7036c77/REPAIRS"},{"kind":"sourceAvailability","id":"f0bd37f6-8f56-3542-bdb5-5b08d7036c77/INTEGRATIONS"},{"kind":"sourceAvailability","id":"f0bd37f6-8f56-3542-bdb5-5b08d7036c77/INVENTORY"}]`

- Session : `{"scopeId":"561e4db8-b71d-30ae-ba0e-f61099efa960","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas e45bedcd-3fe0-36a5-86b3-563783232314

- Exemplaire : `1df2ff70-d621-3e19-bf84-a8293fa00b69`
- Famille privée : RECEPTION_NON_INTEGREE ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"9677c663-2ed2-3a28-a5f0-f8dc887b2471","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"f744b09e-f05d-3321-8504-84ae1cbe8ea4","physicalStatus":"RECEIVING","physicalSiteId":"SUD","physicalLocationId":"8228c3af-c19d-30d4-95b5-30240d9818af","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"1df2ff70-d621-3e19-bf84-a8293fa00b69","status":"RECEIVING","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","sourceEventIds":["f744b09e-f05d-3321-8504-84ae1cbe8ea4","0b267c07-21c5-37a9-8cd2-2700003e9908"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | f744b09e-f05d-3321-8504-84ae1cbe8ea4 | 2026-09-27T10:14:02Z | 2026-09-27T10:14:12Z | `{"operationId":"47e5fd06-be28-38ea-a08e-3ce9513e5ce1","correlationId":"361cfe68-a31b-398b-b2a1-7e788ac8ecf5","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"ce27f62e-3b95-31d8-b173-5e96406428eb"}` |
| IntegrationProcessed | 8e4fb29b-dd66-35bd-b36c-284fc9132994 | 2026-09-27T10:14:32Z | 2026-09-27T10:14:42Z | `{"originalEventId":"f744b09e-f05d-3321-8504-84ae1cbe8ea4","correlationId":"361cfe68-a31b-398b-b2a1-7e788ac8ecf5","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| IntegrationProcessed | 0b267c07-21c5-37a9-8cd2-2700003e9908 | 2026-09-27T10:15:32Z | 2026-09-27T10:15:42Z | `{"originalEventId":"f744b09e-f05d-3321-8504-84ae1cbe8ea4","correlationId":"361cfe68-a31b-398b-b2a1-7e788ac8ecf5","outcome":"APPLIED","projectedStatus":"RECEIVING","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af"}` |

**observations**
- `{"observationId":"8b0705bf-61a0-3d3c-b5cb-db70a7c1d93f","copyId":"1df2ff70-d621-3e19-bf84-a8293fa00b69","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","scopeId":"379a9916-c03d-3031-abc0-bdb770289f15","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`
- `{"observationId":"aa5fab9d-d4f3-3974-a8a8-931b61693389","copyId":"1df2ff70-d621-3e19-bf84-a8293fa00b69","locationId":"205b3714-25eb-3b7f-aead-24616287f71a","scopeId":"d3284395-a86c-3232-8688-9754bbecb8a5","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**sourceAvailability**
- `{"copyId":"1df2ff70-d621-3e19-bf84-a8293fa00b69","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1df2ff70-d621-3e19-bf84-a8293fa00b69","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1df2ff70-d621-3e19-bf84-a8293fa00b69","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1df2ff70-d621-3e19-bf84-a8293fa00b69","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1df2ff70-d621-3e19-bf84-a8293fa00b69","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1df2ff70-d621-3e19-bf84-a8293fa00b69","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"1df2ff70-d621-3e19-bf84-a8293fa00b69","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"1df2ff70-d621-3e19-bf84-a8293fa00b69"},{"kind":"sourceEvent","id":"f744b09e-f05d-3321-8504-84ae1cbe8ea4"},{"kind":"sourceEvent","id":"8e4fb29b-dd66-35bd-b36c-284fc9132994"},{"kind":"sourceEvent","id":"0b267c07-21c5-37a9-8cd2-2700003e9908"},{"kind":"observation","id":"8b0705bf-61a0-3d3c-b5cb-db70a7c1d93f"},{"kind":"inventoryScope","id":"379a9916-c03d-3031-abc0-bdb770289f15"},{"kind":"observation","id":"aa5fab9d-d4f3-3974-a8a8-931b61693389"},{"kind":"inventoryScope","id":"d3284395-a86c-3232-8688-9754bbecb8a5"},{"kind":"sourceAvailability","id":"1df2ff70-d621-3e19-bf84-a8293fa00b69/LOANS"},{"kind":"sourceAvailability","id":"1df2ff70-d621-3e19-bf84-a8293fa00b69/RETURNS"},{"kind":"sourceAvailability","id":"1df2ff70-d621-3e19-bf84-a8293fa00b69/RECEIPTS"},{"kind":"sourceAvailability","id":"1df2ff70-d621-3e19-bf84-a8293fa00b69/TRANSFERS"},{"kind":"sourceAvailability","id":"1df2ff70-d621-3e19-bf84-a8293fa00b69/REPAIRS"},{"kind":"sourceAvailability","id":"1df2ff70-d621-3e19-bf84-a8293fa00b69/INTEGRATIONS"},{"kind":"sourceAvailability","id":"1df2ff70-d621-3e19-bf84-a8293fa00b69/INVENTORY"},{"kind":"inventoryScope","id":"7aaa006b-0aad-359d-a813-df34bbf923f1"},{"kind":"inventoryScope","id":"535ec227-65d3-31f0-a1b4-5c96e8deba81"},{"kind":"inventoryScope","id":"39981143-b396-39b8-b392-eaf8e88be80b"},{"kind":"inventoryScope","id":"19855afc-11fb-34f4-af75-20d4c7d70d3d"},{"kind":"inventoryScope","id":"149f5e9e-4d86-3bca-80a1-a982302e6ec4"}]`

- Session : `{"scopeId":"379a9916-c03d-3031-abc0-bdb770289f15","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"d3284395-a86c-3232-8688-9754bbecb8a5","locationId":"205b3714-25eb-3b7f-aead-24616287f71a","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"7aaa006b-0aad-359d-a813-df34bbf923f1","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"535ec227-65d3-31f0-a1b4-5c96e8deba81","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"39981143-b396-39b8-b392-eaf8e88be80b","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"19855afc-11fb-34f4-af75-20d4c7d70d3d","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"149f5e9e-4d86-3bca-80a1-a982302e6ec4","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas e5102e8b-beef-3322-a307-18ce51f2d87b

- Exemplaire : `5e40810d-c707-367e-b7e9-ab2f1204d06e`
- Famille privée : LOCALISATION ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"104d10e3-8bc7-373f-87cc-75aa435e0a1b","kind":"COUNT_UNCERTAIN","expectedValue":"4a5364f2-388b-351d-91f2-d1364d119042","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"mechanism":"Aucun déplacement ; signal de collecte non confirmé.","activePerturbations":[],"physicalStatus":"ON_SHELF","physicalSiteId":"CENTRE","physicalLocationId":"4a5364f2-388b-351d-91f2-d1364d119042"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"Le scan valide confirme l'emplacement attendu ; aucun écart de localisation étayé.","forbiddenClaims":["Vol","Perte définitive","Responsabilité d'un opérateur"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"5e40810d-c707-367e-b7e9-ab2f1204d06e","status":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","sourceEventIds":["81ad950d-fa11-3a7f-ba69-8f8b8337b377","596f72b8-e51d-3413-8de5-82f8fde2fe37"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 39838df8-5992-3cfb-993a-a5d5c2e62f62 | 2026-06-02T10:17:57Z | 2026-06-02T10:18:07Z | `{"operationId":"a733c9cc-52a4-30f9-9502-f9b22a4fe939","correlationId":"61c36ea7-6789-3ffb-a69d-c1123fc0d57d","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"f0685419-1139-3129-8f82-b38a155af1d2"}` |
| IntegrationProcessed | 7b21cc71-1d64-3598-bceb-a39ea4f47c7a | 2026-06-03T10:17:57Z | 2026-06-03T10:18:07Z | `{"originalEventId":"39838df8-5992-3cfb-993a-a5d5c2e62f62","correlationId":"61c36ea7-6789-3ffb-a69d-c1123fc0d57d","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 9a1fbcec-e14e-3120-afad-ab4c19271d83 | 2026-08-01T10:17:57Z | 2026-08-01T10:18:07Z | `{"operationId":"a8057aee-498d-3038-bac8-f763d9a6f9aa","correlationId":"9e9e3f3c-8b3f-34c1-bcc9-10d8e7b8ef62","siteId":"CENTRE","locationId":null,"loanId":"09d1477e-651f-3629-960c-03700d84e137"}` |
| IntegrationProcessed | 3ca80e56-37f3-356b-a99c-67ee25645526 | 2026-08-01T10:18:57Z | 2026-08-01T10:19:07Z | `{"originalEventId":"9a1fbcec-e14e-3120-afad-ab4c19271d83","correlationId":"9e9e3f3c-8b3f-34c1-bcc9-10d8e7b8ef62","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | d7a5e30b-67f1-3533-9bf6-761a06758e6b | 2026-08-21T10:17:57Z | 2026-08-21T10:18:07Z | `{"operationId":"923daa64-59f3-3562-9df0-b13747cdc828","correlationId":"08c88f6c-e559-3041-9a82-417e02787685","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"09d1477e-651f-3629-960c-03700d84e137"}` |
| ReturnReceived | 81ad950d-fa11-3a7f-ba69-8f8b8337b377 | 2026-08-21T10:18:27Z | 2026-08-21T10:18:37Z | `{"operationId":"47d29bbf-82b6-3bc6-abe9-7cb9d2e1bfa9","correlationId":"2f62d9dd-8a22-3e2a-a76e-f49787a77656","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"09d1477e-651f-3629-960c-03700d84e137"}` |
| IntegrationProcessed | 596f72b8-e51d-3413-8de5-82f8fde2fe37 | 2026-08-22T10:17:57Z | 2026-08-22T10:18:07Z | `{"originalEventId":"81ad950d-fa11-3a7f-ba69-8f8b8337b377","correlationId":"2f62d9dd-8a22-3e2a-a76e-f49787a77656","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |

**observations**
- `{"observationId":"0fd71184-f0d4-3de3-90a8-19c2819e5869","copyId":"5e40810d-c707-367e-b7e9-ab2f1204d06e","locationId":"4a5364f2-388b-351d-91f2-d1364d119042","scopeId":"b0027657-6abb-37d2-8bc4-9341357dfe79","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"5e40810d-c707-367e-b7e9-ab2f1204d06e","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5e40810d-c707-367e-b7e9-ab2f1204d06e","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5e40810d-c707-367e-b7e9-ab2f1204d06e","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5e40810d-c707-367e-b7e9-ab2f1204d06e","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5e40810d-c707-367e-b7e9-ab2f1204d06e","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5e40810d-c707-367e-b7e9-ab2f1204d06e","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5e40810d-c707-367e-b7e9-ab2f1204d06e","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"observation","id":"0fd71184-f0d4-3de3-90a8-19c2819e5869"},{"kind":"systemState","id":"5e40810d-c707-367e-b7e9-ab2f1204d06e"}]`

## Cas e65e7a71-6d08-3ad3-aace-499311d04060

- Exemplaire : `6d719d27-a332-33d9-92f7-217a974cb9c0`
- Famille privée : INVENTAIRE_INCOMPLET ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"b26f4ae2-bb51-36f2-af8a-7146875a1301","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":"1"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"mechanism":"Exemplaire resté au rayon, lecture de contrôle réussie.","physicalStatus":"ON_SHELF","physicalSiteId":"NORD","physicalLocationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"Le scan valide au rayon attendu réfute l'absence courante.","forbiddenClaims":["Vol","Perte définitive","Absence certaine déduite d'une collecte incomplète"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"6d719d27-a332-33d9-92f7-217a974cb9c0","status":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","sourceEventIds":["bd720219-d76d-3c05-9e6d-ab90d0d63f34","b002b4bc-e321-3da4-9c01-da53681ccb40"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | c3711466-b9ba-32c9-b8c8-72599a9eeafe | 2026-06-01T16:55:08Z | 2026-06-01T16:55:18Z | `{"operationId":"c7aaff5a-7346-3c2c-a3f2-c811b7e80def","correlationId":"3b56c8f0-e978-3e6c-83f7-60573cb41c14","siteId":"CENTRE","locationId":"4fdc3825-e139-3fa2-8b0e-d06aab6e02ca","receiptId":"f0747159-5c12-3f39-8292-65bf711e75c2"}` |
| IntegrationProcessed | 950f3dd3-39ba-3618-a782-481d2a2a8099 | 2026-06-02T16:55:08Z | 2026-06-02T16:55:18Z | `{"originalEventId":"c3711466-b9ba-32c9-b8c8-72599a9eeafe","correlationId":"3b56c8f0-e978-3e6c-83f7-60573cb41c14","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| LoanOpened | 42c659c8-a0a9-38e6-be4b-eace92d50a6a | 2026-07-31T16:55:08Z | 2026-07-31T16:55:18Z | `{"operationId":"a5aaa721-2254-3b59-8229-bd60ad6e5a9c","correlationId":"972615f5-d1bc-34f7-a426-bc19b6202da8","siteId":"CENTRE","locationId":null,"loanId":"0fdeb800-d6eb-35f5-80c1-82f259ba6b6f"}` |
| IntegrationProcessed | 9cca48df-31c4-31e9-9406-962d85c66617 | 2026-07-31T16:56:08Z | 2026-07-31T16:56:18Z | `{"originalEventId":"42c659c8-a0a9-38e6-be4b-eace92d50a6a","correlationId":"972615f5-d1bc-34f7-a426-bc19b6202da8","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"CENTRE","locationId":null}` |
| LoanClosed | ac7d036e-4b3c-3675-997f-b0beca6b8b04 | 2026-08-20T16:55:08Z | 2026-08-20T16:55:18Z | `{"operationId":"2540bf50-e00f-3f6b-8949-8f196852da3a","correlationId":"1ebac2b5-b400-3a3a-83d8-671436bf51de","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"0fdeb800-d6eb-35f5-80c1-82f259ba6b6f"}` |
| ReturnReceived | 1299f3bf-1c62-3c6a-8a61-f39596cea393 | 2026-08-20T16:55:38Z | 2026-08-20T16:55:48Z | `{"operationId":"7372cff2-814e-3d48-b729-f325f6ac1f3b","correlationId":"01e89355-1f94-3165-9bf2-19ee37640869","siteId":"CENTRE","locationId":"56a0087f-bf10-36e6-815d-9ddb25c30e19","loanId":"0fdeb800-d6eb-35f5-80c1-82f259ba6b6f"}` |
| IntegrationProcessed | 10d0f342-9cec-3a7e-be37-c2e2fe7365c9 | 2026-08-21T16:55:08Z | 2026-08-21T16:55:18Z | `{"originalEventId":"1299f3bf-1c62-3c6a-8a61-f39596cea393","correlationId":"01e89355-1f94-3165-9bf2-19ee37640869","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"4a5364f2-388b-351d-91f2-d1364d119042"}` |
| TransferDispatched | 7f5cac5f-7714-36a6-8f8e-dd72f698e153 | 2026-08-30T16:55:08Z | 2026-08-30T16:55:18Z | `{"operationId":"34350f06-d937-3fcf-9e12-1a41d9718798","correlationId":"e6c81880-a8ea-3f0d-8558-9da65f295634","siteId":"CENTRE","locationId":null,"transferId":"ae4eea1f-b712-3e78-8ef7-7099cbae7305","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | d99ebcdb-3454-3ea1-b728-7b9a86c15724 | 2026-08-30T16:56:08Z | 2026-08-30T16:56:18Z | `{"originalEventId":"7f5cac5f-7714-36a6-8f8e-dd72f698e153","correlationId":"e6c81880-a8ea-3f0d-8558-9da65f295634","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"CENTRE","locationId":null}` |
| TransferReceived | bd720219-d76d-3c05-9e6d-ab90d0d63f34 | 2026-08-31T16:55:08Z | 2026-08-31T16:55:18Z | `{"operationId":"8699fe4e-b5ed-35ab-a53a-924ccc8e6be3","correlationId":"db35f1f6-935b-3ea3-96df-feac5faa30ec","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","transferId":"ae4eea1f-b712-3e78-8ef7-7099cbae7305","fromSiteId":"CENTRE","toSiteId":"NORD"}` |
| IntegrationProcessed | b002b4bc-e321-3da4-9c01-da53681ccb40 | 2026-09-01T16:55:08Z | 2026-09-01T16:55:18Z | `{"originalEventId":"bd720219-d76d-3c05-9e6d-ab90d0d63f34","correlationId":"db35f1f6-935b-3ea3-96df-feac5faa30ec","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |

**observations**
- `{"observationId":"32b92d45-cdfb-3360-9c69-779ee7fb78d9","copyId":"6d719d27-a332-33d9-92f7-217a974cb9c0","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","scopeId":"9880a39c-a8f4-3f61-a473-804a0fd611a5","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"6d719d27-a332-33d9-92f7-217a974cb9c0","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6d719d27-a332-33d9-92f7-217a974cb9c0","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6d719d27-a332-33d9-92f7-217a974cb9c0","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6d719d27-a332-33d9-92f7-217a974cb9c0","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6d719d27-a332-33d9-92f7-217a974cb9c0","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6d719d27-a332-33d9-92f7-217a974cb9c0","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"6d719d27-a332-33d9-92f7-217a974cb9c0","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**
- `{"noteId":"2a271044-d83c-36b0-be62-876cd6db6445","copyId":"6d719d27-a332-33d9-92f7-217a974cb9c0","text":"Nouvelle lecture du code-barres effectuée dans le rayon affecté.","recordedAt":"2026-09-30T11:59:30Z"}`

**Références de preuve résolubles**

`[{"kind":"systemState","id":"6d719d27-a332-33d9-92f7-217a974cb9c0"},{"kind":"sourceEvent","id":"c3711466-b9ba-32c9-b8c8-72599a9eeafe"},{"kind":"sourceEvent","id":"950f3dd3-39ba-3618-a782-481d2a2a8099"},{"kind":"sourceEvent","id":"42c659c8-a0a9-38e6-be4b-eace92d50a6a"},{"kind":"sourceEvent","id":"9cca48df-31c4-31e9-9406-962d85c66617"},{"kind":"sourceEvent","id":"ac7d036e-4b3c-3675-997f-b0beca6b8b04"},{"kind":"sourceEvent","id":"1299f3bf-1c62-3c6a-8a61-f39596cea393"},{"kind":"sourceEvent","id":"10d0f342-9cec-3a7e-be37-c2e2fe7365c9"},{"kind":"sourceEvent","id":"7f5cac5f-7714-36a6-8f8e-dd72f698e153"},{"kind":"sourceEvent","id":"d99ebcdb-3454-3ea1-b728-7b9a86c15724"},{"kind":"sourceEvent","id":"bd720219-d76d-3c05-9e6d-ab90d0d63f34"},{"kind":"sourceEvent","id":"b002b4bc-e321-3da4-9c01-da53681ccb40"},{"kind":"observation","id":"32b92d45-cdfb-3360-9c69-779ee7fb78d9"},{"kind":"inventoryScope","id":"9880a39c-a8f4-3f61-a473-804a0fd611a5"},{"kind":"sourceAvailability","id":"6d719d27-a332-33d9-92f7-217a974cb9c0/LOANS"},{"kind":"sourceAvailability","id":"6d719d27-a332-33d9-92f7-217a974cb9c0/RETURNS"},{"kind":"sourceAvailability","id":"6d719d27-a332-33d9-92f7-217a974cb9c0/RECEIPTS"},{"kind":"sourceAvailability","id":"6d719d27-a332-33d9-92f7-217a974cb9c0/TRANSFERS"},{"kind":"sourceAvailability","id":"6d719d27-a332-33d9-92f7-217a974cb9c0/REPAIRS"},{"kind":"sourceAvailability","id":"6d719d27-a332-33d9-92f7-217a974cb9c0/INTEGRATIONS"},{"kind":"sourceAvailability","id":"6d719d27-a332-33d9-92f7-217a974cb9c0/INVENTORY"},{"kind":"operatorNote","id":"2a271044-d83c-36b0-be62-876cd6db6445"},{"kind":"inventoryScope","id":"07c957d2-b086-3b6b-9915-d21f1d5906cf"},{"kind":"inventoryScope","id":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c"},{"kind":"inventoryScope","id":"a481af0e-6045-37c4-bb84-d5269f5addc5"},{"kind":"inventoryScope","id":"9880a39c-a8f4-3f61-a473-804a0fd611a5"}]`

- Session : `{"scopeId":"9880a39c-a8f4-3f61-a473-804a0fd611a5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"07c957d2-b086-3b6b-9915-d21f1d5906cf","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"a481af0e-6045-37c4-bb84-d5269f5addc5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9880a39c-a8f4-3f61-a473-804a0fd611a5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas ec123df4-262b-30ea-b9b2-0cdb1da89b66

- Exemplaire : `c6d9f367-6866-3d3f-bb73-0f90407959d9`
- Famille privée : DEUX_CAUSES ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"9ae3a917-256d-37ab-a7dd-4489192a2318","kind":"STATUS_MISMATCH","expectedValue":"IN_REPAIR","observedValue":"IN_REPAIR"},{"symptomId":"b129ad7a-5013-3a06-b0be-2e83cb75de7c","kind":"LOCATION_MISMATCH","expectedValue":"64a3276c-7791-367a-984b-158e1b652a52","observedValue":"205b3714-25eb-3b7f-aead-24616287f71a"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["REPAIR_LOCATION_CHANGED"],"hiddenSourceEvents":[],"mechanism":"Réparation engagée puis déplacement distinct de l'atelier vers la réserve sans mise à jour de l'affectation de réparation.","businessEventId":"984bd92f-d363-3fb2-954a-3ba495233935","physicalStatus":"IN_REPAIR","physicalSiteId":"SUD","physicalLocationId":"205b3714-25eb-3b7f-aead-24616287f71a","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_LOCATION_MISMATCH"],"explanation":"Réparation correctement projetée après reprise ; seul le déplacement hors atelier reste étayé.","forbiddenClaims":["Deux causes confirmées par un scan ambigu","Vol"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"c6d9f367-6866-3d3f-bb73-0f90407959d9","status":"IN_REPAIR","siteId":"SUD","locationId":"64a3276c-7791-367a-984b-158e1b652a52","sourceEventIds":["984bd92f-d363-3fb2-954a-3ba495233935","30c6eee7-c751-3a6a-9bc0-4c24dac4e1c5"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 8ca8980c-42af-3f6e-a251-d2ac6a6da1b3 | 2026-06-01T14:55:50Z | 2026-06-01T14:56:00Z | `{"operationId":"306c9675-8615-3bec-aad2-9dfa1b2d08ef","correlationId":"9fc8b575-035e-376c-afce-20dde774268f","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"d8f7cdb4-0327-30ff-b928-2c1cab4942ce"}` |
| IntegrationProcessed | 67e24559-459c-37eb-aaa8-95ad50e9a3dd | 2026-06-02T14:55:50Z | 2026-06-02T14:56:00Z | `{"originalEventId":"8ca8980c-42af-3f6e-a251-d2ac6a6da1b3","correlationId":"9fc8b575-035e-376c-afce-20dde774268f","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | b6832acc-b5b0-3f59-af21-61abbe9a9fe7 | 2026-07-31T14:55:50Z | 2026-07-31T14:56:00Z | `{"operationId":"ab3399c3-17e9-3425-aa33-0c748c950e92","correlationId":"49c8dd95-65a2-3909-bfed-af622b940305","siteId":"SUD","locationId":null,"loanId":"55b48c15-0af6-3ccb-b028-668f6c895215"}` |
| IntegrationProcessed | a0b39047-a5c1-3149-8f65-536a106a45e7 | 2026-07-31T14:56:50Z | 2026-07-31T14:57:00Z | `{"originalEventId":"b6832acc-b5b0-3f59-af21-61abbe9a9fe7","correlationId":"49c8dd95-65a2-3909-bfed-af622b940305","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | a4370bbc-cab1-3e00-99ff-50fcb4dbbb19 | 2026-08-20T14:55:50Z | 2026-08-20T14:56:00Z | `{"operationId":"259a6140-65fe-33cb-b483-3e3666b80254","correlationId":"c79f7ed2-9071-3029-b52a-eba0ef985ae1","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"55b48c15-0af6-3ccb-b028-668f6c895215"}` |
| ReturnReceived | 6d0e24d6-e058-396c-9d34-523406d7e9c1 | 2026-08-20T14:56:20Z | 2026-08-20T14:56:30Z | `{"operationId":"29db0789-9740-3bc2-b0ae-3323649b9f0e","correlationId":"90705cf6-9d3b-3683-af42-a3ad7ac45cec","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"55b48c15-0af6-3ccb-b028-668f6c895215"}` |
| IntegrationProcessed | e7339546-dc73-3f65-9095-86f97dc80ffc | 2026-08-21T14:55:50Z | 2026-08-21T14:56:00Z | `{"originalEventId":"6d0e24d6-e058-396c-9d34-523406d7e9c1","correlationId":"90705cf6-9d3b-3683-af42-a3ad7ac45cec","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| RepairStarted | 984bd92f-d363-3fb2-954a-3ba495233935 | 2026-09-27T08:00:17Z | 2026-09-27T08:00:27Z | `{"operationId":"8512f758-e925-3317-a68b-560c78b9b483","correlationId":"5e3ebf9c-4b1b-3952-85af-252ebe01a32f","siteId":"SUD","locationId":"64a3276c-7791-367a-984b-158e1b652a52","repairId":"729419e5-1b6a-3a9c-9c72-09918820bf76"}` |
| IntegrationProcessed | e31d0638-e2f0-30f8-b535-e5092eff4a9a | 2026-09-27T08:00:47Z | 2026-09-27T08:00:57Z | `{"originalEventId":"984bd92f-d363-3fb2-954a-3ba495233935","correlationId":"5e3ebf9c-4b1b-3952-85af-252ebe01a32f","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"SUD","locationId":"64a3276c-7791-367a-984b-158e1b652a52","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| IntegrationProcessed | 30c6eee7-c751-3a6a-9bc0-4c24dac4e1c5 | 2026-09-27T08:01:47Z | 2026-09-27T08:01:57Z | `{"originalEventId":"984bd92f-d363-3fb2-954a-3ba495233935","correlationId":"5e3ebf9c-4b1b-3952-85af-252ebe01a32f","outcome":"APPLIED","projectedStatus":"IN_REPAIR","siteId":"SUD","locationId":"64a3276c-7791-367a-984b-158e1b652a52"}` |

**observations**
- `{"observationId":"b8e04612-8e6b-3c22-a715-f24cf74845b8","copyId":"c6d9f367-6866-3d3f-bb73-0f90407959d9","locationId":"205b3714-25eb-3b7f-aead-24616287f71a","scopeId":"7938956c-9bf4-32ec-af97-99a9b1ef146d","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"c6d9f367-6866-3d3f-bb73-0f90407959d9","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c6d9f367-6866-3d3f-bb73-0f90407959d9","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c6d9f367-6866-3d3f-bb73-0f90407959d9","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c6d9f367-6866-3d3f-bb73-0f90407959d9","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c6d9f367-6866-3d3f-bb73-0f90407959d9","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c6d9f367-6866-3d3f-bb73-0f90407959d9","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c6d9f367-6866-3d3f-bb73-0f90407959d9","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"c6d9f367-6866-3d3f-bb73-0f90407959d9"},{"kind":"sourceEvent","id":"8ca8980c-42af-3f6e-a251-d2ac6a6da1b3"},{"kind":"sourceEvent","id":"67e24559-459c-37eb-aaa8-95ad50e9a3dd"},{"kind":"sourceEvent","id":"b6832acc-b5b0-3f59-af21-61abbe9a9fe7"},{"kind":"sourceEvent","id":"a0b39047-a5c1-3149-8f65-536a106a45e7"},{"kind":"sourceEvent","id":"a4370bbc-cab1-3e00-99ff-50fcb4dbbb19"},{"kind":"sourceEvent","id":"6d0e24d6-e058-396c-9d34-523406d7e9c1"},{"kind":"sourceEvent","id":"e7339546-dc73-3f65-9095-86f97dc80ffc"},{"kind":"sourceEvent","id":"984bd92f-d363-3fb2-954a-3ba495233935"},{"kind":"sourceEvent","id":"e31d0638-e2f0-30f8-b535-e5092eff4a9a"},{"kind":"sourceEvent","id":"30c6eee7-c751-3a6a-9bc0-4c24dac4e1c5"},{"kind":"observation","id":"b8e04612-8e6b-3c22-a715-f24cf74845b8"},{"kind":"inventoryScope","id":"7938956c-9bf4-32ec-af97-99a9b1ef146d"},{"kind":"sourceAvailability","id":"c6d9f367-6866-3d3f-bb73-0f90407959d9/LOANS"},{"kind":"sourceAvailability","id":"c6d9f367-6866-3d3f-bb73-0f90407959d9/RETURNS"},{"kind":"sourceAvailability","id":"c6d9f367-6866-3d3f-bb73-0f90407959d9/RECEIPTS"},{"kind":"sourceAvailability","id":"c6d9f367-6866-3d3f-bb73-0f90407959d9/TRANSFERS"},{"kind":"sourceAvailability","id":"c6d9f367-6866-3d3f-bb73-0f90407959d9/REPAIRS"},{"kind":"sourceAvailability","id":"c6d9f367-6866-3d3f-bb73-0f90407959d9/INTEGRATIONS"},{"kind":"sourceAvailability","id":"c6d9f367-6866-3d3f-bb73-0f90407959d9/INVENTORY"},{"kind":"inventoryScope","id":"ac80cfd2-4d1b-37ad-b3fe-eab57127cf6d"},{"kind":"inventoryScope","id":"44f187ae-1c67-3cb8-8200-609dc747e27b"},{"kind":"inventoryScope","id":"1c440542-3079-3a02-9ddc-d3c3a528ee0a"}]`

- Session : `{"scopeId":"7938956c-9bf4-32ec-af97-99a9b1ef146d","locationId":"205b3714-25eb-3b7f-aead-24616287f71a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"ac80cfd2-4d1b-37ad-b3fe-eab57127cf6d","locationId":"64a3276c-7791-367a-984b-158e1b652a52","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"44f187ae-1c67-3cb8-8200-609dc747e27b","locationId":"64a3276c-7791-367a-984b-158e1b652a52","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"1c440542-3079-3a02-9ddc-d3c3a528ee0a","locationId":"64a3276c-7791-367a-984b-158e1b652a52","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas f426dad0-a117-31bc-889d-6f5eee96caab

- Exemplaire : `9a750d28-d872-3817-a160-eec8dda0193c`
- Famille privée : RETARD_INTEGRATION ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"35550855-c7a6-3e85-817a-d40f754b934f","kind":"MISSING","expectedValue":"ON_SHELF","observedValue":"ON_LOAN"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["QUEUE_PENDING"],"hiddenSourceEvents":[{"sourceEventId":"90dfe184-681f-39ec-af57-8457b5f059ce","copyId":"9a750d28-d872-3817-a160-eec8dda0193c","eventType":"IntegrationProcessed","occurredAt":"2026-09-30T11:53:28Z","recordedAt":"2026-09-30T11:53:38Z","payload":{"originalEventId":"8e378f90-8119-37d4-9914-c76bb97ccfbd","correlationId":"bb2993e6-b466-35a2-bd5c-a13961278b64","outcome":"QUEUED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null,"maxProcessingDelaySeconds":900,"attempt":1}}],"mechanism":"Événement métier en attente normale dans la fenêtre contractuelle de 900 secondes.","businessEventId":"8e378f90-8119-37d4-9914-c76bb97ccfbd","physicalStatus":"ON_LOAN","physicalSiteId":"SUD","physicalLocationId":null,"projectionPerturbation":"QUEUED","evidenceMask":"Journal d'intégration récent absent ; scan ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":[],"explanation":"L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":["Récupérer le journal d'intégration récent","Confirmer le scan"]}`
- État projeté : `{"copyId":"9a750d28-d872-3817-a160-eec8dda0193c","status":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","sourceEventIds":["2b1c7a78-42bf-3c22-8065-afa445e0f689","45f40453-d8bb-3ba7-9f32-66d1a3100823"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 8c2e920b-43a3-3963-a27a-7f346d8a23b3 | 2026-06-01T15:03:53Z | 2026-06-01T15:04:03Z | `{"operationId":"475063d2-2cb0-3b5b-93df-99219fa09322","correlationId":"a049bbc5-802a-3289-a711-7320a0747f7c","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"c786d8af-bb7f-3c05-b820-a4c6170a685b"}` |
| IntegrationProcessed | 6993689b-da9d-339c-8007-90d0700a912e | 2026-06-02T15:03:53Z | 2026-06-02T15:04:03Z | `{"originalEventId":"8c2e920b-43a3-3963-a27a-7f346d8a23b3","correlationId":"a049bbc5-802a-3289-a711-7320a0747f7c","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | 4a038570-c330-3483-a867-44707e1eaab2 | 2026-07-31T15:03:53Z | 2026-07-31T15:04:03Z | `{"operationId":"5943b399-4471-38e4-8a2e-3491e0124662","correlationId":"a7e9a4a5-baa4-3ec7-9894-d8146861f06a","siteId":"SUD","locationId":null,"loanId":"2e9b6a4d-1e43-3893-9d19-db6baba1a71d"}` |
| IntegrationProcessed | 1e224ebe-f87c-3b97-a3c8-8cb618ada23e | 2026-07-31T15:04:53Z | 2026-07-31T15:05:03Z | `{"originalEventId":"4a038570-c330-3483-a867-44707e1eaab2","correlationId":"a7e9a4a5-baa4-3ec7-9894-d8146861f06a","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | dfe6a325-46c5-3555-870b-579a56897f9b | 2026-08-20T15:03:53Z | 2026-08-20T15:04:03Z | `{"operationId":"c03f12c1-38a7-3701-bfd2-852678dc42fe","correlationId":"945e7002-2808-3513-9d4f-4ab1be64cc6b","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"2e9b6a4d-1e43-3893-9d19-db6baba1a71d"}` |
| ReturnReceived | 2b1c7a78-42bf-3c22-8065-afa445e0f689 | 2026-08-20T15:04:23Z | 2026-08-20T15:04:33Z | `{"operationId":"508c0dde-07b1-33fb-9338-4c9a0524f83d","correlationId":"18f5e6a4-e328-3ec1-9e88-6f2d7e519d1a","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"2e9b6a4d-1e43-3893-9d19-db6baba1a71d"}` |
| IntegrationProcessed | 45f40453-d8bb-3ba7-9f32-66d1a3100823 | 2026-08-21T15:03:53Z | 2026-08-21T15:04:03Z | `{"originalEventId":"2b1c7a78-42bf-3c22-8065-afa445e0f689","correlationId":"18f5e6a4-e328-3ec1-9e88-6f2d7e519d1a","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | 8e378f90-8119-37d4-9914-c76bb97ccfbd | 2026-09-30T11:52:58Z | 2026-09-30T11:53:08Z | `{"operationId":"cf244eee-5236-3e12-9c51-b0888b28e5ab","correlationId":"bb2993e6-b466-35a2-bd5c-a13961278b64","siteId":"SUD","locationId":null,"loanId":"2bcc1471-d687-39e1-97cb-d6213af98a40"}` |

**observations**

**sourceAvailability**
- `{"copyId":"9a750d28-d872-3817-a160-eec8dda0193c","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9a750d28-d872-3817-a160-eec8dda0193c","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9a750d28-d872-3817-a160-eec8dda0193c","source":"INTEGRATIONS","status":"PARTIAL","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-23T12:00:00Z"}`
- `{"copyId":"9a750d28-d872-3817-a160-eec8dda0193c","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9a750d28-d872-3817-a160-eec8dda0193c","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9a750d28-d872-3817-a160-eec8dda0193c","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"9a750d28-d872-3817-a160-eec8dda0193c","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"9a750d28-d872-3817-a160-eec8dda0193c"},{"kind":"sourceEvent","id":"8c2e920b-43a3-3963-a27a-7f346d8a23b3"},{"kind":"sourceEvent","id":"6993689b-da9d-339c-8007-90d0700a912e"},{"kind":"sourceEvent","id":"4a038570-c330-3483-a867-44707e1eaab2"},{"kind":"sourceEvent","id":"1e224ebe-f87c-3b97-a3c8-8cb618ada23e"},{"kind":"sourceEvent","id":"dfe6a325-46c5-3555-870b-579a56897f9b"},{"kind":"sourceEvent","id":"2b1c7a78-42bf-3c22-8065-afa445e0f689"},{"kind":"sourceEvent","id":"45f40453-d8bb-3ba7-9f32-66d1a3100823"},{"kind":"sourceEvent","id":"8e378f90-8119-37d4-9914-c76bb97ccfbd"},{"kind":"sourceAvailability","id":"9a750d28-d872-3817-a160-eec8dda0193c/LOANS"},{"kind":"sourceAvailability","id":"9a750d28-d872-3817-a160-eec8dda0193c/RETURNS"},{"kind":"sourceAvailability","id":"9a750d28-d872-3817-a160-eec8dda0193c/RECEIPTS"},{"kind":"sourceAvailability","id":"9a750d28-d872-3817-a160-eec8dda0193c/TRANSFERS"},{"kind":"sourceAvailability","id":"9a750d28-d872-3817-a160-eec8dda0193c/REPAIRS"},{"kind":"sourceAvailability","id":"9a750d28-d872-3817-a160-eec8dda0193c/INTEGRATIONS"},{"kind":"sourceAvailability","id":"9a750d28-d872-3817-a160-eec8dda0193c/INVENTORY"},{"kind":"inventoryScope","id":"294f8877-9130-3961-8435-48be641fa20c"},{"kind":"inventoryScope","id":"9d6c09df-1bb9-3fa2-927d-b44bcfe9e1be"},{"kind":"inventoryScope","id":"8ba7cc3d-833a-342a-bcaa-e1d23f56a4e3"}]`

- Session : `{"scopeId":"294f8877-9130-3961-8435-48be641fa20c","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9d6c09df-1bb9-3fa2-927d-b44bcfe9e1be","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"8ba7cc3d-833a-342a-bcaa-e1d23f56a4e3","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas f439de58-5ba7-36d2-854b-30adad0ab40c

- Exemplaire : `f379bb14-5bb3-3093-a3d3-902bca433171`
- Famille privée : LOCALISATION ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"048f15c3-4b68-382c-92ee-026dcaef92b4","kind":"COUNT_UNCERTAIN","expectedValue":"63e3aa91-8217-35a3-b458-6bae9b374c87","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"mechanism":"Déplacement physique sans modification de l'affectation ; aucune attribution à un opérateur.","activePerturbations":["LOCATION_CHANGE_UNPROJECTED"],"physicalStatus":"ON_SHELF","physicalSiteId":"SUD","physicalLocationId":"205b3714-25eb-3b7f-aead-24616287f71a"}`
- Diagnostic admissible attendu : `{"conclusion":"INDETERMINATE","causeCodes":[],"explanation":"Identité du scan ambiguë ; ne pas attribuer un emplacement physique certain.","forbiddenClaims":["Vol","Perte définitive","Responsabilité d'un opérateur"],"recommendedChecks":["Refaire le scan et vérifier le code-barres"]}`
- État projeté : `{"copyId":"f379bb14-5bb3-3093-a3d3-902bca433171","status":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","sourceEventIds":["a9ddf1ff-9745-3706-be4c-ad4f314f1330","64a98e84-f598-3db0-aaae-a4cb7c7c5067"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 053c637e-dcf2-34e3-9ab6-ef5f3733a772 | 2026-06-02T03:57:26Z | 2026-06-02T03:57:36Z | `{"operationId":"bac0ac45-3f5e-358f-bf15-dbdf5e15ba18","correlationId":"36022d48-bd11-3d16-a268-4538605b99b8","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"68a347f1-b901-3e89-9075-953b49a67ad9"}` |
| IntegrationProcessed | 3f05237a-73a7-3f91-b8d6-8cd59a155715 | 2026-06-03T03:57:26Z | 2026-06-03T03:57:36Z | `{"originalEventId":"053c637e-dcf2-34e3-9ab6-ef5f3733a772","correlationId":"36022d48-bd11-3d16-a268-4538605b99b8","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 9ba58230-1f22-32a4-843e-85f0aedca38f | 2026-08-01T03:57:26Z | 2026-08-01T03:57:36Z | `{"operationId":"296d1e56-b1e4-34b6-9881-7de4321ff2d4","correlationId":"14f07d71-944b-3bf6-ada2-c10a590df2de","siteId":"NORD","locationId":null,"loanId":"cc59bae9-e188-37df-9d1d-d2db0abf90cc"}` |
| IntegrationProcessed | bdc258c2-7d8e-32a4-b845-cbfaeb038d8b | 2026-08-01T03:58:26Z | 2026-08-01T03:58:36Z | `{"originalEventId":"9ba58230-1f22-32a4-843e-85f0aedca38f","correlationId":"14f07d71-944b-3bf6-ada2-c10a590df2de","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | f279f0d8-c4fb-3aef-9fa2-4473ac42bcb4 | 2026-08-21T03:57:26Z | 2026-08-21T03:57:36Z | `{"operationId":"56c6bf58-64a7-38b2-8378-76a3d971dafe","correlationId":"82ebaa4c-aa73-3749-b6e7-932f6e5ddd7d","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"cc59bae9-e188-37df-9d1d-d2db0abf90cc"}` |
| ReturnReceived | 52309f94-5137-37d0-9899-08ee745d3700 | 2026-08-21T03:57:56Z | 2026-08-21T03:58:06Z | `{"operationId":"200112b9-6cbb-394e-a4ed-67e49dc8b8c0","correlationId":"00815af4-2a03-34b6-8563-1f0dbe790e5d","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"cc59bae9-e188-37df-9d1d-d2db0abf90cc"}` |
| IntegrationProcessed | fb29e2a3-e981-3c52-8586-b5514c48c2f8 | 2026-08-22T03:57:26Z | 2026-08-22T03:57:36Z | `{"originalEventId":"52309f94-5137-37d0-9899-08ee745d3700","correlationId":"00815af4-2a03-34b6-8563-1f0dbe790e5d","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| TransferDispatched | 4c34a3fe-47ff-3f78-89ff-fd0c1e72c36b | 2026-08-31T03:57:26Z | 2026-08-31T03:57:36Z | `{"operationId":"7202390f-2343-307d-b655-eebe095bb916","correlationId":"a20391ae-8bbd-3f9a-972e-57afcbf69c79","siteId":"NORD","locationId":null,"transferId":"8f8dfa0c-d083-3d93-97f4-f930a4e30f61","fromSiteId":"NORD","toSiteId":"SUD"}` |
| IntegrationProcessed | 81f6c330-20bc-3e64-9317-dbc15a4fad9d | 2026-08-31T03:58:26Z | 2026-08-31T03:58:36Z | `{"originalEventId":"4c34a3fe-47ff-3f78-89ff-fd0c1e72c36b","correlationId":"a20391ae-8bbd-3f9a-972e-57afcbf69c79","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"NORD","locationId":null}` |
| TransferReceived | a9ddf1ff-9745-3706-be4c-ad4f314f1330 | 2026-09-01T03:57:26Z | 2026-09-01T03:57:36Z | `{"operationId":"d13b7928-c758-32ff-a974-497b49763c1e","correlationId":"4bdb1621-7d23-3960-893b-324538faf3fd","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","transferId":"8f8dfa0c-d083-3d93-97f4-f930a4e30f61","fromSiteId":"NORD","toSiteId":"SUD"}` |
| IntegrationProcessed | 64a98e84-f598-3db0-aaae-a4cb7c7c5067 | 2026-09-02T03:57:26Z | 2026-09-02T03:57:36Z | `{"originalEventId":"a9ddf1ff-9745-3706-be4c-ad4f314f1330","correlationId":"4bdb1621-7d23-3960-893b-324538faf3fd","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |

**observations**
- `{"observationId":"84560ea3-908c-363f-a78f-7c4b7a4c38c1","copyId":"f379bb14-5bb3-3093-a3d3-902bca433171","locationId":"205b3714-25eb-3b7f-aead-24616287f71a","scopeId":"e917306f-8bcc-38a8-ab0c-704519c4b17f","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**sourceAvailability**
- `{"copyId":"f379bb14-5bb3-3093-a3d3-902bca433171","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f379bb14-5bb3-3093-a3d3-902bca433171","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f379bb14-5bb3-3093-a3d3-902bca433171","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f379bb14-5bb3-3093-a3d3-902bca433171","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f379bb14-5bb3-3093-a3d3-902bca433171","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f379bb14-5bb3-3093-a3d3-902bca433171","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f379bb14-5bb3-3093-a3d3-902bca433171","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"observation","id":"84560ea3-908c-363f-a78f-7c4b7a4c38c1"},{"kind":"systemState","id":"f379bb14-5bb3-3093-a3d3-902bca433171"}]`

## Cas f5a81719-f793-3d90-9ee3-c9c4ded09e8c

- Exemplaire : `bdafb275-129d-3a0f-b92f-25fc3eca0de3`
- Famille privée : TRANSFERT_INCOHERENT ; variante : NEAR_NEGATIVE
- Symptômes : `[{"symptomId":"3b351714-072c-3b27-9f84-f8e3995b970c","kind":"COUNT_UNCERTAIN","expectedValue":"1","observedValue":null}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":[],"hiddenSourceEvents":[],"mechanism":"Traitement repris et appliqué avant référence ; défaut devenu inactif.","businessEventId":"2464f419-625b-3eb4-aaeb-88e996828c6d","physicalStatus":"RECEIVING","physicalSiteId":"SUD","physicalLocationId":"8228c3af-c19d-30d4-95b5-30240d9818af","projectionPerturbation":"Échec transitoire puis reprise réussie avant référence","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":[],"explanation":"La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"bdafb275-129d-3a0f-b92f-25fc3eca0de3","status":"RECEIVING","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","sourceEventIds":["2464f419-625b-3eb4-aaeb-88e996828c6d","e0f74c13-500f-3fe5-8e6f-864ff7e8136f"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 45066c02-3084-3c10-98bb-529cae0ec4e8 | 2026-06-02T04:15:33Z | 2026-06-02T04:15:43Z | `{"operationId":"6622da97-1b69-31ea-af2e-cf75c7acc073","correlationId":"1cbc4610-7e08-31f2-a2e8-55a286a68ac4","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"b34b5d38-b2c5-30dd-9479-5d72b90dfc48"}` |
| IntegrationProcessed | a4dc0d83-5a86-3009-94d3-48261000d372 | 2026-06-03T04:15:33Z | 2026-06-03T04:15:43Z | `{"originalEventId":"45066c02-3084-3c10-98bb-529cae0ec4e8","correlationId":"1cbc4610-7e08-31f2-a2e8-55a286a68ac4","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 7d09e63f-fa38-3dc5-8dd6-aa12c45256c3 | 2026-08-01T04:15:33Z | 2026-08-01T04:15:43Z | `{"operationId":"e39f5728-8c46-31dc-83e2-9d66ba4735cf","correlationId":"7ebc7666-aba1-335c-acad-7f0f8981c9c1","siteId":"NORD","locationId":null,"loanId":"27693a2c-5054-33a0-84dc-c9d5dd950b05"}` |
| IntegrationProcessed | 5ecf6766-5e26-3bef-958a-e6927ab7c38e | 2026-08-01T04:16:33Z | 2026-08-01T04:16:43Z | `{"originalEventId":"7d09e63f-fa38-3dc5-8dd6-aa12c45256c3","correlationId":"7ebc7666-aba1-335c-acad-7f0f8981c9c1","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | 3c598381-5e3c-3a72-96b5-1e2894461660 | 2026-08-21T04:15:33Z | 2026-08-21T04:15:43Z | `{"operationId":"86d9dda4-92d8-39c3-9631-386104ff8ef7","correlationId":"2a31aa71-ae07-3d6f-9fb7-40305ec17b03","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"27693a2c-5054-33a0-84dc-c9d5dd950b05"}` |
| ReturnReceived | 2cf9e808-8cf1-3e29-8fa9-a614209c7933 | 2026-08-21T04:16:03Z | 2026-08-21T04:16:13Z | `{"operationId":"8bf715a0-75e5-35af-987b-c96e40b0b3a4","correlationId":"a3d669c7-f012-354d-8507-b6c0cb6d5b64","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"27693a2c-5054-33a0-84dc-c9d5dd950b05"}` |
| IntegrationProcessed | 4ef436f9-d2bb-305f-93da-71674d84a9e5 | 2026-08-22T04:15:33Z | 2026-08-22T04:15:43Z | `{"originalEventId":"2cf9e808-8cf1-3e29-8fa9-a614209c7933","correlationId":"a3d669c7-f012-354d-8507-b6c0cb6d5b64","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| TransferDispatched | adfd40ab-ac01-3564-a0de-ce6a9390c07c | 2026-09-26T08:55:19Z | 2026-09-26T08:55:29Z | `{"operationId":"5b92a08f-36a1-3a91-9b53-b9786c93383a","correlationId":"720630bc-f2eb-3f42-a17e-2312d2d2b9e5","siteId":"NORD","locationId":null,"transferId":"aab29e8a-dc84-36cf-bf1a-eca525f68e20","fromSiteId":"NORD","toSiteId":"SUD"}` |
| IntegrationProcessed | 5100eeb2-f59b-300c-bbd1-2904fd63db12 | 2026-09-26T08:56:19Z | 2026-09-26T08:56:29Z | `{"originalEventId":"adfd40ab-ac01-3564-a0de-ce6a9390c07c","correlationId":"720630bc-f2eb-3f42-a17e-2312d2d2b9e5","outcome":"APPLIED","projectedStatus":"IN_TRANSIT","siteId":"NORD","locationId":null}` |
| TransferReceived | 2464f419-625b-3eb4-aaeb-88e996828c6d | 2026-09-27T08:55:19Z | 2026-09-27T08:55:29Z | `{"operationId":"3eb0f544-06e0-3d8a-8ce6-a10c43532765","correlationId":"f4f7c59e-e07d-3f28-a9fe-feff136ef2ee","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","transferId":"aab29e8a-dc84-36cf-bf1a-eca525f68e20","fromSiteId":"NORD","toSiteId":"SUD"}` |
| IntegrationProcessed | 251060f9-ec68-3b0b-9505-8f1443432436 | 2026-09-27T08:55:49Z | 2026-09-27T08:55:59Z | `{"originalEventId":"2464f419-625b-3eb4-aaeb-88e996828c6d","correlationId":"f4f7c59e-e07d-3f28-a9fe-feff136ef2ee","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |
| IntegrationProcessed | e0f74c13-500f-3fe5-8e6f-864ff7e8136f | 2026-09-27T08:56:49Z | 2026-09-27T08:56:59Z | `{"originalEventId":"2464f419-625b-3eb4-aaeb-88e996828c6d","correlationId":"f4f7c59e-e07d-3f28-a9fe-feff136ef2ee","outcome":"APPLIED","projectedStatus":"RECEIVING","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af"}` |

**observations**
- `{"observationId":"8129dbd4-dc2b-33c0-8ec8-84ede17c76db","copyId":"bdafb275-129d-3a0f-b92f-25fc3eca0de3","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","scopeId":"39981143-b396-39b8-b392-eaf8e88be80b","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`
- `{"observationId":"7b397bc0-98dd-3755-aefe-151a199d1654","copyId":"bdafb275-129d-3a0f-b92f-25fc3eca0de3","locationId":"205b3714-25eb-3b7f-aead-24616287f71a","scopeId":"fac0ecf0-ec11-319b-b39b-95d2ac5e5fa0","observedAt":"2026-09-30T11:55:00Z","quality":"REJECTED"}`

**sourceAvailability**
- `{"copyId":"bdafb275-129d-3a0f-b92f-25fc3eca0de3","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bdafb275-129d-3a0f-b92f-25fc3eca0de3","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bdafb275-129d-3a0f-b92f-25fc3eca0de3","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bdafb275-129d-3a0f-b92f-25fc3eca0de3","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bdafb275-129d-3a0f-b92f-25fc3eca0de3","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bdafb275-129d-3a0f-b92f-25fc3eca0de3","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"bdafb275-129d-3a0f-b92f-25fc3eca0de3","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"bdafb275-129d-3a0f-b92f-25fc3eca0de3"},{"kind":"sourceEvent","id":"45066c02-3084-3c10-98bb-529cae0ec4e8"},{"kind":"sourceEvent","id":"a4dc0d83-5a86-3009-94d3-48261000d372"},{"kind":"sourceEvent","id":"7d09e63f-fa38-3dc5-8dd6-aa12c45256c3"},{"kind":"sourceEvent","id":"5ecf6766-5e26-3bef-958a-e6927ab7c38e"},{"kind":"sourceEvent","id":"3c598381-5e3c-3a72-96b5-1e2894461660"},{"kind":"sourceEvent","id":"2cf9e808-8cf1-3e29-8fa9-a614209c7933"},{"kind":"sourceEvent","id":"4ef436f9-d2bb-305f-93da-71674d84a9e5"},{"kind":"sourceEvent","id":"adfd40ab-ac01-3564-a0de-ce6a9390c07c"},{"kind":"sourceEvent","id":"5100eeb2-f59b-300c-bbd1-2904fd63db12"},{"kind":"sourceEvent","id":"2464f419-625b-3eb4-aaeb-88e996828c6d"},{"kind":"sourceEvent","id":"251060f9-ec68-3b0b-9505-8f1443432436"},{"kind":"sourceEvent","id":"e0f74c13-500f-3fe5-8e6f-864ff7e8136f"},{"kind":"observation","id":"8129dbd4-dc2b-33c0-8ec8-84ede17c76db"},{"kind":"inventoryScope","id":"39981143-b396-39b8-b392-eaf8e88be80b"},{"kind":"observation","id":"7b397bc0-98dd-3755-aefe-151a199d1654"},{"kind":"inventoryScope","id":"fac0ecf0-ec11-319b-b39b-95d2ac5e5fa0"},{"kind":"sourceAvailability","id":"bdafb275-129d-3a0f-b92f-25fc3eca0de3/LOANS"},{"kind":"sourceAvailability","id":"bdafb275-129d-3a0f-b92f-25fc3eca0de3/RETURNS"},{"kind":"sourceAvailability","id":"bdafb275-129d-3a0f-b92f-25fc3eca0de3/RECEIPTS"},{"kind":"sourceAvailability","id":"bdafb275-129d-3a0f-b92f-25fc3eca0de3/TRANSFERS"},{"kind":"sourceAvailability","id":"bdafb275-129d-3a0f-b92f-25fc3eca0de3/REPAIRS"},{"kind":"sourceAvailability","id":"bdafb275-129d-3a0f-b92f-25fc3eca0de3/INTEGRATIONS"},{"kind":"sourceAvailability","id":"bdafb275-129d-3a0f-b92f-25fc3eca0de3/INVENTORY"},{"kind":"inventoryScope","id":"7aaa006b-0aad-359d-a813-df34bbf923f1"},{"kind":"inventoryScope","id":"535ec227-65d3-31f0-a1b4-5c96e8deba81"}]`

- Session : `{"scopeId":"39981143-b396-39b8-b392-eaf8e88be80b","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"fac0ecf0-ec11-319b-b39b-95d2ac5e5fa0","locationId":"205b3714-25eb-3b7f-aead-24616287f71a","status":"INCOMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":null}`

- Session : `{"scopeId":"7aaa006b-0aad-359d-a813-df34bbf923f1","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"535ec227-65d3-31f0-a1b4-5c96e8deba81","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas fa419399-547d-3b60-9265-f73f946434c3

- Exemplaire : `5c6a9218-561d-3a1c-9e4a-020ad2a9e5e7`
- Famille privée : DEUX_CAUSES ; variante : INCOMPLETE
- Symptômes : `[{"symptomId":"572d72d2-e91d-3c82-b75a-645054597fa0","kind":"STATUS_MISMATCH","expectedValue":"IN_REPAIR","observedValue":"ON_SHELF"},{"symptomId":"c53b809c-6ef1-373d-a9e4-049d98ad07af","kind":"LOCATION_MISMATCH","expectedValue":"64a3276c-7791-367a-984b-158e1b652a52","observedValue":"205b3714-25eb-3b7f-aead-24616287f71a"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED","REPAIR_LOCATION_CHANGED"],"hiddenSourceEvents":[],"mechanism":"Réparation engagée puis déplacement distinct de l'atelier vers la réserve sans mise à jour de l'affectation de réparation.","businessEventId":"fc91365c-fa84-3073-b38e-b93bd249c302","physicalStatus":"IN_REPAIR","physicalSiteId":"SUD","physicalLocationId":"205b3714-25eb-3b7f-aead-24616287f71a","projectionPerturbation":"FAILED","evidenceMask":"Scan de réserve ambigu"}`
- Diagnostic admissible attendu : `{"conclusion":"PARTIAL","causeCodes":["REPAIR_NOT_PROJECTED"],"explanation":"Échec de projection de réparation documenté ; seconde localisation non confirmée par un scan valide.","forbiddenClaims":["Deux causes confirmées par un scan ambigu","Vol"],"recommendedChecks":["Refaire le scan en réserve"]}`
- État projeté : `{"copyId":"5c6a9218-561d-3a1c-9e4a-020ad2a9e5e7","status":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","sourceEventIds":["d6ece00a-a8b3-3f95-bfdf-81b9e9366cff","3e990f57-3110-34e5-8f5a-8c561182ca66"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | d825deec-0cd4-381d-a1e2-30a62804b034 | 2026-06-01T12:43:42Z | 2026-06-01T12:43:52Z | `{"operationId":"04ac4fe2-ab19-3c98-b761-0dfd24b0c6bb","correlationId":"1f8b4f3e-dfa6-3c4e-9b1a-19ad47945e65","siteId":"SUD","locationId":"8228c3af-c19d-30d4-95b5-30240d9818af","receiptId":"4d0b1a33-e0e9-3d10-a7bc-84734d2fb6d1"}` |
| IntegrationProcessed | 8715ab60-2050-34ab-ad87-bc421fc03689 | 2026-06-02T12:43:42Z | 2026-06-02T12:43:52Z | `{"originalEventId":"d825deec-0cd4-381d-a1e2-30a62804b034","correlationId":"1f8b4f3e-dfa6-3c4e-9b1a-19ad47945e65","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| LoanOpened | 1fbe8430-c870-3c4a-a204-8cf030d2d841 | 2026-07-31T12:43:42Z | 2026-07-31T12:43:52Z | `{"operationId":"1df65d97-2d07-3738-87ca-046173aa454c","correlationId":"79ece7df-2cce-3a7a-8588-a7f5a81aac7e","siteId":"SUD","locationId":null,"loanId":"f596cb73-c56e-3ad8-974f-2b1adf5d7870"}` |
| IntegrationProcessed | bb5050b1-9c09-3750-864d-13d593a65830 | 2026-07-31T12:44:42Z | 2026-07-31T12:44:52Z | `{"originalEventId":"1fbe8430-c870-3c4a-a204-8cf030d2d841","correlationId":"79ece7df-2cce-3a7a-8588-a7f5a81aac7e","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| LoanClosed | e6108b02-668d-3473-991e-d2f9c5cbfefa | 2026-08-20T12:43:42Z | 2026-08-20T12:43:52Z | `{"operationId":"c1eb864c-79a7-3bbd-ad33-12e72b849b02","correlationId":"1ecfd7f4-d692-3118-ba8a-a3fa37ea5fee","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"f596cb73-c56e-3ad8-974f-2b1adf5d7870"}` |
| ReturnReceived | d6ece00a-a8b3-3f95-bfdf-81b9e9366cff | 2026-08-20T12:44:12Z | 2026-08-20T12:44:22Z | `{"operationId":"bd16cce9-c46d-30ca-975f-8d97b82fc51c","correlationId":"6b396431-2fac-3265-bc2a-446bcd56bcc0","siteId":"SUD","locationId":"f963a1ee-c2bd-3fb7-bf4f-f41260f1d1fb","loanId":"f596cb73-c56e-3ad8-974f-2b1adf5d7870"}` |
| IntegrationProcessed | 3e990f57-3110-34e5-8f5a-8c561182ca66 | 2026-08-21T12:43:42Z | 2026-08-21T12:43:52Z | `{"originalEventId":"d6ece00a-a8b3-3f95-bfdf-81b9e9366cff","correlationId":"6b396431-2fac-3265-bc2a-446bcd56bcc0","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87"}` |
| RepairStarted | fc91365c-fa84-3073-b38e-b93bd249c302 | 2026-09-27T10:48:49Z | 2026-09-27T10:48:59Z | `{"operationId":"396a5629-99cd-3300-a3bb-c39d058fe6ac","correlationId":"c7d931a1-313e-3e7a-ae2c-eb0e0119dcc2","siteId":"SUD","locationId":"64a3276c-7791-367a-984b-158e1b652a52","repairId":"5a7ef42d-ecbc-371b-a4a8-2fc182f51055"}` |
| IntegrationProcessed | 50ed463f-4b2d-3cc9-bf91-dcf4118716e8 | 2026-09-27T10:49:19Z | 2026-09-27T10:49:29Z | `{"originalEventId":"fc91365c-fa84-3073-b38e-b93bd249c302","correlationId":"c7d931a1-313e-3e7a-ae2c-eb0e0119dcc2","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"SUD","locationId":"64a3276c-7791-367a-984b-158e1b652a52","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**observations**
- `{"observationId":"1e67833c-cd61-3c4a-a797-02c66bc0178d","copyId":"5c6a9218-561d-3a1c-9e4a-020ad2a9e5e7","locationId":"205b3714-25eb-3b7f-aead-24616287f71a","scopeId":"b7ccbb3e-77b4-3ff4-abeb-1423d40b3800","observedAt":"2026-09-30T11:55:00Z","quality":"AMBIGUOUS"}`

**sourceAvailability**
- `{"copyId":"5c6a9218-561d-3a1c-9e4a-020ad2a9e5e7","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5c6a9218-561d-3a1c-9e4a-020ad2a9e5e7","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5c6a9218-561d-3a1c-9e4a-020ad2a9e5e7","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5c6a9218-561d-3a1c-9e4a-020ad2a9e5e7","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5c6a9218-561d-3a1c-9e4a-020ad2a9e5e7","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5c6a9218-561d-3a1c-9e4a-020ad2a9e5e7","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"5c6a9218-561d-3a1c-9e4a-020ad2a9e5e7","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"5c6a9218-561d-3a1c-9e4a-020ad2a9e5e7"},{"kind":"sourceEvent","id":"d825deec-0cd4-381d-a1e2-30a62804b034"},{"kind":"sourceEvent","id":"8715ab60-2050-34ab-ad87-bc421fc03689"},{"kind":"sourceEvent","id":"1fbe8430-c870-3c4a-a204-8cf030d2d841"},{"kind":"sourceEvent","id":"bb5050b1-9c09-3750-864d-13d593a65830"},{"kind":"sourceEvent","id":"e6108b02-668d-3473-991e-d2f9c5cbfefa"},{"kind":"sourceEvent","id":"d6ece00a-a8b3-3f95-bfdf-81b9e9366cff"},{"kind":"sourceEvent","id":"3e990f57-3110-34e5-8f5a-8c561182ca66"},{"kind":"sourceEvent","id":"fc91365c-fa84-3073-b38e-b93bd249c302"},{"kind":"sourceEvent","id":"50ed463f-4b2d-3cc9-bf91-dcf4118716e8"},{"kind":"observation","id":"1e67833c-cd61-3c4a-a797-02c66bc0178d"},{"kind":"inventoryScope","id":"b7ccbb3e-77b4-3ff4-abeb-1423d40b3800"},{"kind":"sourceAvailability","id":"5c6a9218-561d-3a1c-9e4a-020ad2a9e5e7/LOANS"},{"kind":"sourceAvailability","id":"5c6a9218-561d-3a1c-9e4a-020ad2a9e5e7/RETURNS"},{"kind":"sourceAvailability","id":"5c6a9218-561d-3a1c-9e4a-020ad2a9e5e7/RECEIPTS"},{"kind":"sourceAvailability","id":"5c6a9218-561d-3a1c-9e4a-020ad2a9e5e7/TRANSFERS"},{"kind":"sourceAvailability","id":"5c6a9218-561d-3a1c-9e4a-020ad2a9e5e7/REPAIRS"},{"kind":"sourceAvailability","id":"5c6a9218-561d-3a1c-9e4a-020ad2a9e5e7/INTEGRATIONS"},{"kind":"sourceAvailability","id":"5c6a9218-561d-3a1c-9e4a-020ad2a9e5e7/INVENTORY"},{"kind":"inventoryScope","id":"294f8877-9130-3961-8435-48be641fa20c"},{"kind":"inventoryScope","id":"9d6c09df-1bb9-3fa2-927d-b44bcfe9e1be"},{"kind":"inventoryScope","id":"8ba7cc3d-833a-342a-bcaa-e1d23f56a4e3"},{"kind":"inventoryScope","id":"59269371-fb13-3ca1-a62a-01272d903229"},{"kind":"inventoryScope","id":"139f34be-b437-3f23-84c9-757fbea07b69"},{"kind":"inventoryScope","id":"002b3157-56c1-3594-86e4-95e1ed6a3889"}]`

- Session : `{"scopeId":"b7ccbb3e-77b4-3ff4-abeb-1423d40b3800","locationId":"205b3714-25eb-3b7f-aead-24616287f71a","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"294f8877-9130-3961-8435-48be641fa20c","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9d6c09df-1bb9-3fa2-927d-b44bcfe9e1be","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"8ba7cc3d-833a-342a-bcaa-e1d23f56a4e3","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"59269371-fb13-3ca1-a62a-01272d903229","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"139f34be-b437-3f23-84c9-757fbea07b69","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"002b3157-56c1-3594-86e4-95e1ed6a3889","locationId":"63e3aa91-8217-35a3-b458-6bae9b374c87","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

## Cas fd59db60-07d3-37c3-9c3f-717124f61a7e

- Exemplaire : `8152cf3e-1aaf-3651-9938-a793cee18169`
- Famille privée : DEUX_CAUSES ; variante : POSITIVE
- Symptômes : `[{"symptomId":"46c2adad-1134-338e-a9b9-854cc747dec3","kind":"STATUS_MISMATCH","expectedValue":"IN_REPAIR","observedValue":"ON_SHELF"},{"symptomId":"1ca73617-92d1-3c0b-92a6-3d3ec3e1daaf","kind":"LOCATION_MISMATCH","expectedValue":"11044fb8-eb45-33d3-90df-81754bc299a0","observedValue":"5b61a611-a926-3ed2-82dc-fc122118757c"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED","REPAIR_LOCATION_CHANGED"],"hiddenSourceEvents":[],"mechanism":"Réparation engagée puis déplacement distinct de l'atelier vers la réserve sans mise à jour de l'affectation de réparation.","businessEventId":"9fef6048-15eb-3fee-99bd-6e38c539dd04","physicalStatus":"IN_REPAIR","physicalSiteId":"NORD","physicalLocationId":"5b61a611-a926-3ed2-82dc-fc122118757c","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_NOT_PROJECTED","REPAIR_LOCATION_MISMATCH"],"explanation":"Deux écarts compatibles : réparation non projetée et scan valide hors de l'atelier indiqué par l'ordre de réparation.","forbiddenClaims":["Deux causes confirmées par un scan ambigu","Vol"],"recommendedChecks":[]}`
- État projeté : `{"copyId":"8152cf3e-1aaf-3651-9938-a793cee18169","status":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","sourceEventIds":["2c4321a8-2a3b-3c65-b2c5-3cccf32c63fd","9d00ef16-6567-35bd-acfc-eca718ce223c"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| ReceiptReceived | 99f084b1-0384-3b8b-8bf8-09799abb2448 | 2026-06-02T03:47:14Z | 2026-06-02T03:47:24Z | `{"operationId":"a7b219d4-73d9-3ef7-b6af-33114fd49ad4","correlationId":"372ee332-800f-349b-b4b8-50eb6fa0b517","siteId":"NORD","locationId":"977b6cd6-c21b-3713-9bb7-6e130b5dcd87","receiptId":"7c593033-8d9a-3af4-b8e7-6d1630ed4709"}` |
| IntegrationProcessed | 5a547ca4-c2b6-3717-926e-e7aff77550d3 | 2026-06-03T03:47:14Z | 2026-06-03T03:47:24Z | `{"originalEventId":"99f084b1-0384-3b8b-8bf8-09799abb2448","correlationId":"372ee332-800f-349b-b4b8-50eb6fa0b517","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| LoanOpened | 225f424a-77da-33a2-a298-6eb6281ed7c0 | 2026-08-01T03:47:14Z | 2026-08-01T03:47:24Z | `{"operationId":"5e831841-eb10-329a-8c77-374a5b17d610","correlationId":"fbb351a1-350e-367b-864f-8fec0a2684ca","siteId":"NORD","locationId":null,"loanId":"b06b6e94-55df-3763-bc34-3c87bf9cc9de"}` |
| IntegrationProcessed | 867256f2-1efc-342f-9ab7-4e6d3e369862 | 2026-08-01T03:48:14Z | 2026-08-01T03:48:24Z | `{"originalEventId":"225f424a-77da-33a2-a298-6eb6281ed7c0","correlationId":"fbb351a1-350e-367b-864f-8fec0a2684ca","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| LoanClosed | 0c670a2b-2e97-3c44-b9a2-e2c1ddd5df0e | 2026-08-21T03:47:14Z | 2026-08-21T03:47:24Z | `{"operationId":"c20d8c26-0475-39d7-bda3-fe262b9ef0cd","correlationId":"47a8aaf6-4efa-36a9-92cb-f2f3e37552d4","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"b06b6e94-55df-3763-bc34-3c87bf9cc9de"}` |
| ReturnReceived | 2c4321a8-2a3b-3c65-b2c5-3cccf32c63fd | 2026-08-21T03:47:44Z | 2026-08-21T03:47:54Z | `{"operationId":"f4518854-46ef-3000-8ce5-2e0936bd0c0a","correlationId":"6fc0b764-41f5-3112-b25f-c0fcfe5f4e07","siteId":"NORD","locationId":"317ca7ca-cd8c-31bb-b723-0a6ea637a361","loanId":"b06b6e94-55df-3763-bc34-3c87bf9cc9de"}` |
| IntegrationProcessed | 9d00ef16-6567-35bd-acfc-eca718ce223c | 2026-08-22T03:47:14Z | 2026-08-22T03:47:24Z | `{"originalEventId":"2c4321a8-2a3b-3c65-b2c5-3cccf32c63fd","correlationId":"6fc0b764-41f5-3112-b25f-c0fcfe5f4e07","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34"}` |
| RepairStarted | 9fef6048-15eb-3fee-99bd-6e38c539dd04 | 2026-09-27T11:52:37Z | 2026-09-27T11:52:47Z | `{"operationId":"e5830a6d-315e-3f5a-b52e-c319cc5706d7","correlationId":"b8c22ad3-a24f-30f6-8ea0-f0f20d8b4723","siteId":"NORD","locationId":"11044fb8-eb45-33d3-90df-81754bc299a0","repairId":"9848fef0-24e4-360e-8c9d-11c0d6e07f0d"}` |
| IntegrationProcessed | ef94d235-1b71-3edc-9c0d-c1fd5edb886a | 2026-09-27T11:53:07Z | 2026-09-27T11:53:17Z | `{"originalEventId":"9fef6048-15eb-3fee-99bd-6e38c539dd04","correlationId":"b8c22ad3-a24f-30f6-8ea0-f0f20d8b4723","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"NORD","locationId":"11044fb8-eb45-33d3-90df-81754bc299a0","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**observations**
- `{"observationId":"601e7dcd-3cbc-3a15-b24a-0ede7f86f706","copyId":"8152cf3e-1aaf-3651-9938-a793cee18169","locationId":"5b61a611-a926-3ed2-82dc-fc122118757c","scopeId":"7a2908b3-ab59-3036-8ab1-ca3a4538b7b3","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**sourceAvailability**
- `{"copyId":"8152cf3e-1aaf-3651-9938-a793cee18169","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8152cf3e-1aaf-3651-9938-a793cee18169","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8152cf3e-1aaf-3651-9938-a793cee18169","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8152cf3e-1aaf-3651-9938-a793cee18169","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8152cf3e-1aaf-3651-9938-a793cee18169","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8152cf3e-1aaf-3651-9938-a793cee18169","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"8152cf3e-1aaf-3651-9938-a793cee18169","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**operatorNotes**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"8152cf3e-1aaf-3651-9938-a793cee18169"},{"kind":"sourceEvent","id":"99f084b1-0384-3b8b-8bf8-09799abb2448"},{"kind":"sourceEvent","id":"5a547ca4-c2b6-3717-926e-e7aff77550d3"},{"kind":"sourceEvent","id":"225f424a-77da-33a2-a298-6eb6281ed7c0"},{"kind":"sourceEvent","id":"867256f2-1efc-342f-9ab7-4e6d3e369862"},{"kind":"sourceEvent","id":"0c670a2b-2e97-3c44-b9a2-e2c1ddd5df0e"},{"kind":"sourceEvent","id":"2c4321a8-2a3b-3c65-b2c5-3cccf32c63fd"},{"kind":"sourceEvent","id":"9d00ef16-6567-35bd-acfc-eca718ce223c"},{"kind":"sourceEvent","id":"9fef6048-15eb-3fee-99bd-6e38c539dd04"},{"kind":"sourceEvent","id":"ef94d235-1b71-3edc-9c0d-c1fd5edb886a"},{"kind":"observation","id":"601e7dcd-3cbc-3a15-b24a-0ede7f86f706"},{"kind":"inventoryScope","id":"7a2908b3-ab59-3036-8ab1-ca3a4538b7b3"},{"kind":"sourceAvailability","id":"8152cf3e-1aaf-3651-9938-a793cee18169/LOANS"},{"kind":"sourceAvailability","id":"8152cf3e-1aaf-3651-9938-a793cee18169/RETURNS"},{"kind":"sourceAvailability","id":"8152cf3e-1aaf-3651-9938-a793cee18169/RECEIPTS"},{"kind":"sourceAvailability","id":"8152cf3e-1aaf-3651-9938-a793cee18169/TRANSFERS"},{"kind":"sourceAvailability","id":"8152cf3e-1aaf-3651-9938-a793cee18169/REPAIRS"},{"kind":"sourceAvailability","id":"8152cf3e-1aaf-3651-9938-a793cee18169/INTEGRATIONS"},{"kind":"sourceAvailability","id":"8152cf3e-1aaf-3651-9938-a793cee18169/INVENTORY"},{"kind":"inventoryScope","id":"07c957d2-b086-3b6b-9915-d21f1d5906cf"},{"kind":"inventoryScope","id":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c"},{"kind":"inventoryScope","id":"a481af0e-6045-37c4-bb84-d5269f5addc5"},{"kind":"inventoryScope","id":"9880a39c-a8f4-3f61-a473-804a0fd611a5"},{"kind":"inventoryScope","id":"79157bb9-b809-3388-ad1a-42d980616ea0"},{"kind":"inventoryScope","id":"eba23a81-5103-3ded-ba6b-6619213a7840"},{"kind":"inventoryScope","id":"6c49954b-e555-36a5-ab71-af60ea210c9b"},{"kind":"inventoryScope","id":"52bd3f79-07d0-3284-a51a-c100a5c5022e"},{"kind":"inventoryScope","id":"dcb0af01-2143-3d85-9776-f4578912c7d1"}]`

- Session : `{"scopeId":"7a2908b3-ab59-3036-8ab1-ca3a4538b7b3","locationId":"5b61a611-a926-3ed2-82dc-fc122118757c","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"07c957d2-b086-3b6b-9915-d21f1d5906cf","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"f0ee04ce-42f9-356d-98e7-06c6fc21fd8c","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"INCOMPLETE","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"a481af0e-6045-37c4-bb84-d5269f5addc5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"9880a39c-a8f4-3f61-a473-804a0fd611a5","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"79157bb9-b809-3388-ad1a-42d980616ea0","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"FAILED","startedAt":"2026-09-30T11:58:00Z","closedAt":null}`

- Session : `{"scopeId":"eba23a81-5103-3ded-ba6b-6619213a7840","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"6c49954b-e555-36a5-ab71-af60ea210c9b","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"52bd3f79-07d0-3284-a51a-c100a5c5022e","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"dcb0af01-2143-3d85-9776-f4578912c7d1","locationId":"92ddef9a-6359-3ab6-98b0-53f2161c5d34","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`
