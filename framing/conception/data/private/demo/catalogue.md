# Catalogue privé — demo

Généré depuis l'oracle de simulation v2.0.0. Réservé aux tests et à l'expert ; ne pas exposer au worker IA.

Vocabulaire explicite : une opération métier concerne un exemplaire ; sa mise à jour du stock informatique est représentée par `IntegrationProcessed`. Un cas est un dossier d'écart.

Les extraits JSON restent fidèles aux données générées : leurs noms techniques et anciens libellés sont conservés. `projection` désigne l'état du stock informatique ; `intégration` désigne sa mise à jour, pas un échange Kafka. Voir le [glossaire validé](../../../../06-domaine-ddd-et-cycles-de-vie.md).

Référence : 2026-09-30T12:00:00Z; seed : 42. Chaque attente est déclarée par le scénario, sans exécuter un moteur diagnostique.

## Cas 26975d89-ec8a-3b27-8b3e-5fda65565af8 — dossier d'écart

- Exemplaire : `25f3b9e9-8af0-3494-8a3f-b0bb1d7e2590`
- Famille privée : RETOUR_NON_PROJETE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"dde31e1b-8818-3ba2-8172-4f449bd4f350","kind":"STATUS_MISMATCH","expectedValue":"ON_LOAN","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"85e36a43-6f04-3b17-bc43-57676bed03a8","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"0ac5c4ea-5244-3e5f-8852-d9108a816a26","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["RETURN_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"25f3b9e9-8af0-3494-8a3f-b0bb1d7e2590","status":"ON_LOAN","siteId":"NORD","locationId":null,"sourceEventIds":["12c47540-b01f-3a89-a146-98fcc32fbb6e","93d50631-467e-3b16-8376-521ceea9833b"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 9d5f485f-6534-3315-8f13-661c6573e9cf | 2026-06-02T03:26:40Z | 2026-06-02T03:26:50Z | `{"operationId":"75cd5255-db48-3cec-9351-0aa8904d3068","correlationId":"52bb1943-6400-366e-a0fd-649077534f04","siteId":"NORD","locationId":"0a084b29-28a8-37c7-b061-e68202e602f5","receiptId":"908844e6-976a-3565-9735-7fde88dfe014"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 0286d186-362c-3e78-947a-587008fb7dc2 | 2026-06-03T03:26:40Z | 2026-06-03T03:26:50Z | `{"originalEventId":"9d5f485f-6534-3315-8f13-661c6573e9cf","correlationId":"52bb1943-6400-366e-a0fd-649077534f04","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"0888c111-02ad-379f-8d1e-405702cf1f0b"}` |
| Prêt enregistré (`LoanOpened`) | 12c47540-b01f-3a89-a146-98fcc32fbb6e | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"7bc78d29-c88d-3339-b086-23d07cccbb68","correlationId":"02a0732d-4d90-33fa-b1cc-e64096e1aa8e","siteId":"NORD","locationId":null,"loanId":"46738449-23c3-3bfc-a5c1-86c79da181d3"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 93d50631-467e-3b16-8376-521ceea9833b | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"12c47540-b01f-3a89-a146-98fcc32fbb6e","correlationId":"02a0732d-4d90-33fa-b1cc-e64096e1aa8e","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | cf265875-0035-3f6a-968a-ce5d3cca06be | 2026-09-28T06:33:03Z | 2026-09-28T06:33:13Z | `{"operationId":"769c6754-5595-3162-9e30-16bc25cab632","correlationId":"00523c84-f6ed-3235-a693-9f16fc638a8c","siteId":"NORD","locationId":"0ac5c4ea-5244-3e5f-8852-d9108a816a26","loanId":"46738449-23c3-3bfc-a5c1-86c79da181d3"}` |
| Retour reçu (`ReturnReceived`) | 85e36a43-6f04-3b17-bc43-57676bed03a8 | 2026-09-28T06:33:33Z | 2026-09-28T06:33:43Z | `{"operationId":"195749ed-aeca-395f-9602-5e870e7788d6","correlationId":"b1817cdb-0da1-32a9-9d96-d6107fdc2284","siteId":"NORD","locationId":"0ac5c4ea-5244-3e5f-8852-d9108a816a26","loanId":"46738449-23c3-3bfc-a5c1-86c79da181d3"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 3dbd88eb-f5e9-31c6-b296-34f01a394b6f | 2026-09-28T06:34:03Z | 2026-09-28T06:34:13Z | `{"originalEventId":"85e36a43-6f04-3b17-bc43-57676bed03a8","correlationId":"b1817cdb-0da1-32a9-9d96-d6107fdc2284","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"0ac5c4ea-5244-3e5f-8852-d9108a816a26","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"b743cb51-9341-3c87-a120-8565265fe934","copyId":"25f3b9e9-8af0-3494-8a3f-b0bb1d7e2590","locationId":"0ac5c4ea-5244-3e5f-8852-d9108a816a26","scopeId":"a2bf945a-52d7-3784-9468-dfd24b7686d9","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"25f3b9e9-8af0-3494-8a3f-b0bb1d7e2590","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"25f3b9e9-8af0-3494-8a3f-b0bb1d7e2590","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"25f3b9e9-8af0-3494-8a3f-b0bb1d7e2590","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"25f3b9e9-8af0-3494-8a3f-b0bb1d7e2590","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"25f3b9e9-8af0-3494-8a3f-b0bb1d7e2590","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"25f3b9e9-8af0-3494-8a3f-b0bb1d7e2590","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"25f3b9e9-8af0-3494-8a3f-b0bb1d7e2590","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"25f3b9e9-8af0-3494-8a3f-b0bb1d7e2590"},{"kind":"sourceEvent","id":"9d5f485f-6534-3315-8f13-661c6573e9cf"},{"kind":"sourceEvent","id":"0286d186-362c-3e78-947a-587008fb7dc2"},{"kind":"sourceEvent","id":"12c47540-b01f-3a89-a146-98fcc32fbb6e"},{"kind":"sourceEvent","id":"93d50631-467e-3b16-8376-521ceea9833b"},{"kind":"sourceEvent","id":"cf265875-0035-3f6a-968a-ce5d3cca06be"},{"kind":"sourceEvent","id":"85e36a43-6f04-3b17-bc43-57676bed03a8"},{"kind":"sourceEvent","id":"3dbd88eb-f5e9-31c6-b296-34f01a394b6f"},{"kind":"observation","id":"b743cb51-9341-3c87-a120-8565265fe934"},{"kind":"inventoryScope","id":"a2bf945a-52d7-3784-9468-dfd24b7686d9"},{"kind":"sourceAvailability","id":"25f3b9e9-8af0-3494-8a3f-b0bb1d7e2590/LOANS"},{"kind":"sourceAvailability","id":"25f3b9e9-8af0-3494-8a3f-b0bb1d7e2590/RETURNS"},{"kind":"sourceAvailability","id":"25f3b9e9-8af0-3494-8a3f-b0bb1d7e2590/RECEIPTS"},{"kind":"sourceAvailability","id":"25f3b9e9-8af0-3494-8a3f-b0bb1d7e2590/TRANSFERS"},{"kind":"sourceAvailability","id":"25f3b9e9-8af0-3494-8a3f-b0bb1d7e2590/REPAIRS"},{"kind":"sourceAvailability","id":"25f3b9e9-8af0-3494-8a3f-b0bb1d7e2590/INTEGRATIONS"},{"kind":"sourceAvailability","id":"25f3b9e9-8af0-3494-8a3f-b0bb1d7e2590/INVENTORY"}]`

- Session : `{"scopeId":"a2bf945a-52d7-3784-9468-dfd24b7686d9","locationId":"0ac5c4ea-5244-3e5f-8852-d9108a816a26","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 2fe877a6-fe6b-3134-8a9c-02b3dbe13dc4 — dossier d'écart

- Exemplaire : `a61d6a69-84be-3871-afa2-464ac8ec1596`
- Famille privée : RETOUR_NON_PROJETE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"04966b81-48ca-36b7-857f-e529d6a33f27","kind":"STATUS_MISMATCH","expectedValue":"ON_LOAN","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"38653553-50f4-32fb-b245-3ff16ae66dd1","physicalStatus":"RECEIVING","physicalSiteId":"NORD","physicalLocationId":"0ac5c4ea-5244-3e5f-8852-d9108a816a26","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["RETURN_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"a61d6a69-84be-3871-afa2-464ac8ec1596","status":"ON_LOAN","siteId":"NORD","locationId":null,"sourceEventIds":["9d3cbd8e-8cf7-3b47-a0eb-8d4ddede991f","6532e7e5-1747-334f-990c-9b43180dcbf4"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 43e2b1fb-e8f5-35b6-8e91-d3174f8d8baa | 2026-06-02T03:04:13Z | 2026-06-02T03:04:23Z | `{"operationId":"40ccf64e-7dad-3049-ad61-ddf3f15561d0","correlationId":"acee628e-90a6-322a-86e9-0aea60a13d25","siteId":"NORD","locationId":"0a084b29-28a8-37c7-b061-e68202e602f5","receiptId":"005d73ca-4146-34a7-80c3-06b9a2ed2777"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 619f5bf3-da1c-3235-8691-48523e4532a6 | 2026-06-03T03:04:13Z | 2026-06-03T03:04:23Z | `{"originalEventId":"43e2b1fb-e8f5-35b6-8e91-d3174f8d8baa","correlationId":"acee628e-90a6-322a-86e9-0aea60a13d25","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"0888c111-02ad-379f-8d1e-405702cf1f0b"}` |
| Prêt enregistré (`LoanOpened`) | 9d3cbd8e-8cf7-3b47-a0eb-8d4ddede991f | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"cbaca8da-4ef2-33ca-9453-fb505c771514","correlationId":"8ef20a06-065e-3e91-ac34-4626a5933ba8","siteId":"NORD","locationId":null,"loanId":"37c6bc3b-24af-3eba-b852-cde1c34cae51"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 6532e7e5-1747-334f-990c-9b43180dcbf4 | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"9d3cbd8e-8cf7-3b47-a0eb-8d4ddede991f","correlationId":"8ef20a06-065e-3e91-ac34-4626a5933ba8","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"NORD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 7ce8cb2d-3452-34e6-8a6a-313a2ac830aa | 2026-09-28T11:56:18Z | 2026-09-28T11:56:28Z | `{"operationId":"dd1dd088-4bec-3f1f-80b5-b70b0e3c8dfd","correlationId":"15f876f0-3740-3193-a814-b3d4c87b9080","siteId":"NORD","locationId":"0ac5c4ea-5244-3e5f-8852-d9108a816a26","loanId":"37c6bc3b-24af-3eba-b852-cde1c34cae51"}` |
| Retour reçu (`ReturnReceived`) | 38653553-50f4-32fb-b245-3ff16ae66dd1 | 2026-09-28T11:56:48Z | 2026-09-28T11:56:58Z | `{"operationId":"e0efde8c-279a-3afc-86a5-81ccf2255dd3","correlationId":"2578b7a0-25e5-3e5b-ab36-0c28388fea79","siteId":"NORD","locationId":"0ac5c4ea-5244-3e5f-8852-d9108a816a26","loanId":"37c6bc3b-24af-3eba-b852-cde1c34cae51"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 665d5c34-d429-39b5-ae70-6e4662987b65 | 2026-09-28T11:57:18Z | 2026-09-28T11:57:28Z | `{"originalEventId":"38653553-50f4-32fb-b245-3ff16ae66dd1","correlationId":"2578b7a0-25e5-3e5b-ab36-0c28388fea79","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"NORD","locationId":"0ac5c4ea-5244-3e5f-8852-d9108a816a26","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"73950493-8eca-3a88-a8cc-951711d3d6a5","copyId":"a61d6a69-84be-3871-afa2-464ac8ec1596","locationId":"0ac5c4ea-5244-3e5f-8852-d9108a816a26","scopeId":"c0614269-94ed-319b-854a-343e935afa97","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"a61d6a69-84be-3871-afa2-464ac8ec1596","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a61d6a69-84be-3871-afa2-464ac8ec1596","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a61d6a69-84be-3871-afa2-464ac8ec1596","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a61d6a69-84be-3871-afa2-464ac8ec1596","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a61d6a69-84be-3871-afa2-464ac8ec1596","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a61d6a69-84be-3871-afa2-464ac8ec1596","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a61d6a69-84be-3871-afa2-464ac8ec1596","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"a61d6a69-84be-3871-afa2-464ac8ec1596"},{"kind":"sourceEvent","id":"43e2b1fb-e8f5-35b6-8e91-d3174f8d8baa"},{"kind":"sourceEvent","id":"619f5bf3-da1c-3235-8691-48523e4532a6"},{"kind":"sourceEvent","id":"9d3cbd8e-8cf7-3b47-a0eb-8d4ddede991f"},{"kind":"sourceEvent","id":"6532e7e5-1747-334f-990c-9b43180dcbf4"},{"kind":"sourceEvent","id":"7ce8cb2d-3452-34e6-8a6a-313a2ac830aa"},{"kind":"sourceEvent","id":"38653553-50f4-32fb-b245-3ff16ae66dd1"},{"kind":"sourceEvent","id":"665d5c34-d429-39b5-ae70-6e4662987b65"},{"kind":"observation","id":"73950493-8eca-3a88-a8cc-951711d3d6a5"},{"kind":"inventoryScope","id":"c0614269-94ed-319b-854a-343e935afa97"},{"kind":"sourceAvailability","id":"a61d6a69-84be-3871-afa2-464ac8ec1596/LOANS"},{"kind":"sourceAvailability","id":"a61d6a69-84be-3871-afa2-464ac8ec1596/RETURNS"},{"kind":"sourceAvailability","id":"a61d6a69-84be-3871-afa2-464ac8ec1596/RECEIPTS"},{"kind":"sourceAvailability","id":"a61d6a69-84be-3871-afa2-464ac8ec1596/TRANSFERS"},{"kind":"sourceAvailability","id":"a61d6a69-84be-3871-afa2-464ac8ec1596/REPAIRS"},{"kind":"sourceAvailability","id":"a61d6a69-84be-3871-afa2-464ac8ec1596/INTEGRATIONS"},{"kind":"sourceAvailability","id":"a61d6a69-84be-3871-afa2-464ac8ec1596/INVENTORY"}]`

- Session : `{"scopeId":"c0614269-94ed-319b-854a-343e935afa97","locationId":"0ac5c4ea-5244-3e5f-8852-d9108a816a26","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 5a200d90-2008-3e74-a773-f50a226020c8 — dossier d'écart

- Exemplaire : `f372e8d5-55b2-3814-8e4b-b9376775fe53`
- Famille privée : RETOUR_NON_PROJETE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"788921ea-9504-3491-9d10-730cfa689f8b","kind":"STATUS_MISMATCH","expectedValue":"ON_LOAN","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"9d095b1b-deb8-3062-95f6-52c9c51e715b","physicalStatus":"RECEIVING","physicalSiteId":"SUD","physicalLocationId":"8f72cb9f-56cd-3baa-8f1b-1f256965aef0","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["RETURN_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"f372e8d5-55b2-3814-8e4b-b9376775fe53","status":"ON_LOAN","siteId":"SUD","locationId":null,"sourceEventIds":["547b62f6-76ba-3749-82c2-d6a64bc2a2b8","338a3d6a-e463-39ce-8351-eae6b1ea7e31"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | ef6049de-d50e-3338-8f40-61c8741f6d8d | 2026-06-02T03:41:34Z | 2026-06-02T03:41:44Z | `{"operationId":"cb8916c4-d028-3a9c-90d6-d8b8d388d7a0","correlationId":"cb5831f5-2ea6-3175-8204-ced851843361","siteId":"SUD","locationId":"00337e33-b95d-3e84-a341-2287c0bad954","receiptId":"b3ca3f9e-45b2-3a24-af3e-3c834b80ec2d"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 6123d6b9-a2c9-3355-9f9b-a1bbcb42401d | 2026-06-03T03:41:34Z | 2026-06-03T03:41:44Z | `{"originalEventId":"ef6049de-d50e-3338-8f40-61c8741f6d8d","correlationId":"cb5831f5-2ea6-3175-8204-ced851843361","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"222dee58-1b72-38dc-9071-8d14b0141831"}` |
| Prêt enregistré (`LoanOpened`) | 547b62f6-76ba-3749-82c2-d6a64bc2a2b8 | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"a6069ce5-91c2-3c17-99f2-19214e169b5a","correlationId":"b0d29f14-e839-3503-9e52-cc993daf975b","siteId":"SUD","locationId":null,"loanId":"6270c5c3-bc0d-30b3-b52e-eda7b2544a79"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 338a3d6a-e463-39ce-8351-eae6b1ea7e31 | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"547b62f6-76ba-3749-82c2-d6a64bc2a2b8","correlationId":"b0d29f14-e839-3503-9e52-cc993daf975b","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 361cb896-27ea-3faf-9f60-ed654ba94944 | 2026-09-28T11:26:40Z | 2026-09-28T11:26:50Z | `{"operationId":"84df7a71-624f-3313-820c-d9291b7d346f","correlationId":"16a15b35-23fd-32e1-91a6-6f19e2fda10f","siteId":"SUD","locationId":"8f72cb9f-56cd-3baa-8f1b-1f256965aef0","loanId":"6270c5c3-bc0d-30b3-b52e-eda7b2544a79"}` |
| Retour reçu (`ReturnReceived`) | 9d095b1b-deb8-3062-95f6-52c9c51e715b | 2026-09-28T11:27:10Z | 2026-09-28T11:27:20Z | `{"operationId":"e6f7e023-cb18-3add-a358-b082f700dd89","correlationId":"7c72c977-8057-3ef0-bf1c-358d714cad79","siteId":"SUD","locationId":"8f72cb9f-56cd-3baa-8f1b-1f256965aef0","loanId":"6270c5c3-bc0d-30b3-b52e-eda7b2544a79"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 7b1cf558-5a55-3014-972d-05033ab21624 | 2026-09-28T11:27:40Z | 2026-09-28T11:27:50Z | `{"originalEventId":"9d095b1b-deb8-3062-95f6-52c9c51e715b","correlationId":"7c72c977-8057-3ef0-bf1c-358d714cad79","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"SUD","locationId":"8f72cb9f-56cd-3baa-8f1b-1f256965aef0","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"3bcde9e0-a606-311b-a840-24de3a8ea17f","copyId":"f372e8d5-55b2-3814-8e4b-b9376775fe53","locationId":"8f72cb9f-56cd-3baa-8f1b-1f256965aef0","scopeId":"a9e43ae1-fc6c-3996-b551-4117da7622aa","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"f372e8d5-55b2-3814-8e4b-b9376775fe53","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f372e8d5-55b2-3814-8e4b-b9376775fe53","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f372e8d5-55b2-3814-8e4b-b9376775fe53","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f372e8d5-55b2-3814-8e4b-b9376775fe53","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f372e8d5-55b2-3814-8e4b-b9376775fe53","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f372e8d5-55b2-3814-8e4b-b9376775fe53","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"f372e8d5-55b2-3814-8e4b-b9376775fe53","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"f372e8d5-55b2-3814-8e4b-b9376775fe53"},{"kind":"sourceEvent","id":"ef6049de-d50e-3338-8f40-61c8741f6d8d"},{"kind":"sourceEvent","id":"6123d6b9-a2c9-3355-9f9b-a1bbcb42401d"},{"kind":"sourceEvent","id":"547b62f6-76ba-3749-82c2-d6a64bc2a2b8"},{"kind":"sourceEvent","id":"338a3d6a-e463-39ce-8351-eae6b1ea7e31"},{"kind":"sourceEvent","id":"361cb896-27ea-3faf-9f60-ed654ba94944"},{"kind":"sourceEvent","id":"9d095b1b-deb8-3062-95f6-52c9c51e715b"},{"kind":"sourceEvent","id":"7b1cf558-5a55-3014-972d-05033ab21624"},{"kind":"observation","id":"3bcde9e0-a606-311b-a840-24de3a8ea17f"},{"kind":"inventoryScope","id":"a9e43ae1-fc6c-3996-b551-4117da7622aa"},{"kind":"sourceAvailability","id":"f372e8d5-55b2-3814-8e4b-b9376775fe53/LOANS"},{"kind":"sourceAvailability","id":"f372e8d5-55b2-3814-8e4b-b9376775fe53/RETURNS"},{"kind":"sourceAvailability","id":"f372e8d5-55b2-3814-8e4b-b9376775fe53/RECEIPTS"},{"kind":"sourceAvailability","id":"f372e8d5-55b2-3814-8e4b-b9376775fe53/TRANSFERS"},{"kind":"sourceAvailability","id":"f372e8d5-55b2-3814-8e4b-b9376775fe53/REPAIRS"},{"kind":"sourceAvailability","id":"f372e8d5-55b2-3814-8e4b-b9376775fe53/INTEGRATIONS"},{"kind":"sourceAvailability","id":"f372e8d5-55b2-3814-8e4b-b9376775fe53/INVENTORY"}]`

- Session : `{"scopeId":"a9e43ae1-fc6c-3996-b551-4117da7622aa","locationId":"8f72cb9f-56cd-3baa-8f1b-1f256965aef0","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 69e6308d-3025-381c-84cb-fe067e864bcd — dossier d'écart

- Exemplaire : `09de1de5-e3ad-3cb5-bfd0-2b62a6fab540`
- Famille privée : RETOUR_NON_PROJETE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"cb3ef9a4-30d2-3857-8458-d1f2efa693ac","kind":"STATUS_MISMATCH","expectedValue":"ON_LOAN","observedValue":"RECEIVING"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"5d641e1d-a766-3f7d-89e5-124eaa2189fa","physicalStatus":"RECEIVING","physicalSiteId":"SUD","physicalLocationId":"8f72cb9f-56cd-3baa-8f1b-1f256965aef0","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["RETURN_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"09de1de5-e3ad-3cb5-bfd0-2b62a6fab540","status":"ON_LOAN","siteId":"SUD","locationId":null,"sourceEventIds":["9882e42f-4bbe-35b1-b79c-14deec78c0d9","c5d9ad8a-8720-33e7-a63e-b79c6ae3ee1e"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | fc8ca3a1-c09c-36ef-a5b4-8074c5c2e17b | 2026-06-01T17:29:01Z | 2026-06-01T17:29:11Z | `{"operationId":"4bb57847-672b-3e90-9fae-bce3a7405ae4","correlationId":"3e04e241-b54c-3292-ad1a-8fdd2fbf6996","siteId":"SUD","locationId":"00337e33-b95d-3e84-a341-2287c0bad954","receiptId":"aa438082-cb24-3269-86e0-4a01508352f1"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 4fbc29d8-bf83-34dc-8e77-72937cba62ed | 2026-06-02T17:29:01Z | 2026-06-02T17:29:11Z | `{"originalEventId":"fc8ca3a1-c09c-36ef-a5b4-8074c5c2e17b","correlationId":"3e04e241-b54c-3292-ad1a-8fdd2fbf6996","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"222dee58-1b72-38dc-9071-8d14b0141831"}` |
| Prêt enregistré (`LoanOpened`) | 9882e42f-4bbe-35b1-b79c-14deec78c0d9 | 2026-09-15T12:00:00Z | 2026-09-15T12:00:10Z | `{"operationId":"465fa03b-0d60-3a05-86cf-e2ede5618780","correlationId":"8f8d9fe0-9035-3de5-8109-0fe512eb9511","siteId":"SUD","locationId":null,"loanId":"ca18eb92-ab28-36a5-82d4-b36d531b547b"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | c5d9ad8a-8720-33e7-a63e-b79c6ae3ee1e | 2026-09-15T12:01:00Z | 2026-09-15T12:01:10Z | `{"originalEventId":"9882e42f-4bbe-35b1-b79c-14deec78c0d9","correlationId":"8f8d9fe0-9035-3de5-8109-0fe512eb9511","outcome":"APPLIED","projectedStatus":"ON_LOAN","siteId":"SUD","locationId":null}` |
| Prêt clôturé (`LoanClosed`) | 4e356d8d-e0bf-3484-8bee-878353130b05 | 2026-09-28T08:50:35Z | 2026-09-28T08:50:45Z | `{"operationId":"58bb7f55-2b0c-38a3-8930-7c0a5bc156c3","correlationId":"0a874418-919c-372f-8675-4b6519660892","siteId":"SUD","locationId":"8f72cb9f-56cd-3baa-8f1b-1f256965aef0","loanId":"ca18eb92-ab28-36a5-82d4-b36d531b547b"}` |
| Retour reçu (`ReturnReceived`) | 5d641e1d-a766-3f7d-89e5-124eaa2189fa | 2026-09-28T08:51:05Z | 2026-09-28T08:51:15Z | `{"operationId":"70f005c8-dc7f-340d-9431-36362209d184","correlationId":"0b4b43f5-a97f-3b4c-bcc9-5f6c0435aa93","siteId":"SUD","locationId":"8f72cb9f-56cd-3baa-8f1b-1f256965aef0","loanId":"ca18eb92-ab28-36a5-82d4-b36d531b547b"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | f811c6df-0189-35b8-8ad1-ed2e4bbae85b | 2026-09-28T08:51:35Z | 2026-09-28T08:51:45Z | `{"originalEventId":"5d641e1d-a766-3f7d-89e5-124eaa2189fa","correlationId":"0b4b43f5-a97f-3b4c-bcc9-5f6c0435aa93","outcome":"FAILED","projectedStatus":"RECEIVING","siteId":"SUD","locationId":"8f72cb9f-56cd-3baa-8f1b-1f256965aef0","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"f8ecbfe1-adfa-38e0-90d9-6063ad6ba821","copyId":"09de1de5-e3ad-3cb5-bfd0-2b62a6fab540","locationId":"8f72cb9f-56cd-3baa-8f1b-1f256965aef0","scopeId":"08aa11cd-c588-3490-88fc-7944e302b1fd","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"09de1de5-e3ad-3cb5-bfd0-2b62a6fab540","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"09de1de5-e3ad-3cb5-bfd0-2b62a6fab540","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"09de1de5-e3ad-3cb5-bfd0-2b62a6fab540","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"09de1de5-e3ad-3cb5-bfd0-2b62a6fab540","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"09de1de5-e3ad-3cb5-bfd0-2b62a6fab540","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"09de1de5-e3ad-3cb5-bfd0-2b62a6fab540","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"09de1de5-e3ad-3cb5-bfd0-2b62a6fab540","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"09de1de5-e3ad-3cb5-bfd0-2b62a6fab540"},{"kind":"sourceEvent","id":"fc8ca3a1-c09c-36ef-a5b4-8074c5c2e17b"},{"kind":"sourceEvent","id":"4fbc29d8-bf83-34dc-8e77-72937cba62ed"},{"kind":"sourceEvent","id":"9882e42f-4bbe-35b1-b79c-14deec78c0d9"},{"kind":"sourceEvent","id":"c5d9ad8a-8720-33e7-a63e-b79c6ae3ee1e"},{"kind":"sourceEvent","id":"4e356d8d-e0bf-3484-8bee-878353130b05"},{"kind":"sourceEvent","id":"5d641e1d-a766-3f7d-89e5-124eaa2189fa"},{"kind":"sourceEvent","id":"f811c6df-0189-35b8-8ad1-ed2e4bbae85b"},{"kind":"observation","id":"f8ecbfe1-adfa-38e0-90d9-6063ad6ba821"},{"kind":"inventoryScope","id":"08aa11cd-c588-3490-88fc-7944e302b1fd"},{"kind":"sourceAvailability","id":"09de1de5-e3ad-3cb5-bfd0-2b62a6fab540/LOANS"},{"kind":"sourceAvailability","id":"09de1de5-e3ad-3cb5-bfd0-2b62a6fab540/RETURNS"},{"kind":"sourceAvailability","id":"09de1de5-e3ad-3cb5-bfd0-2b62a6fab540/RECEIPTS"},{"kind":"sourceAvailability","id":"09de1de5-e3ad-3cb5-bfd0-2b62a6fab540/TRANSFERS"},{"kind":"sourceAvailability","id":"09de1de5-e3ad-3cb5-bfd0-2b62a6fab540/REPAIRS"},{"kind":"sourceAvailability","id":"09de1de5-e3ad-3cb5-bfd0-2b62a6fab540/INTEGRATIONS"},{"kind":"sourceAvailability","id":"09de1de5-e3ad-3cb5-bfd0-2b62a6fab540/INVENTORY"}]`

- Session : `{"scopeId":"08aa11cd-c588-3490-88fc-7944e302b1fd","locationId":"8f72cb9f-56cd-3baa-8f1b-1f256965aef0","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas 96d7187f-171b-31b4-8f43-4e19970f5b00 — dossier d'écart

- Exemplaire : `c8036ce6-e8f0-3aad-b436-6d0729d46cee`
- Famille privée : LOCALISATION ; variante : POSITIVE
- Symptômes : `[{"symptomId":"2c417e5b-0a6f-37e2-99ea-d95d06b16b13","kind":"LOCATION_MISMATCH","expectedValue":"5817d4d3-6d67-337a-8d23-3038191df5be","observedValue":"3eafb5d4-ad2e-33e1-9bcc-ab99cd7d700a"}]`
- Vérité injectée (non accessible au diagnostic) : `{"mechanism":"Déplacement physique sans modification de l'affectation ; aucune attribution à un opérateur.","activePerturbations":["LOCATION_CHANGE_UNPROJECTED"],"physicalStatus":"ON_SHELF","physicalSiteId":"CENTRE","physicalLocationId":"3eafb5d4-ad2e-33e1-9bcc-ab99cd7d700a"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["LOCATION_MISMATCH"],"explanation":"Observation unique valide à un autre emplacement du même site ; écart établi, origine humaine non prouvée.","forbiddenClaims":["Vol","Perte définitive","Responsabilité d'un opérateur"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"c8036ce6-e8f0-3aad-b436-6d0729d46cee","status":"ON_SHELF","siteId":"CENTRE","locationId":"5817d4d3-6d67-337a-8d23-3038191df5be","sourceEventIds":["6c34b448-97dd-3606-a509-1b01b9177703","6eed40e8-2522-3e42-bab5-531a85d25d04"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 6c34b448-97dd-3606-a509-1b01b9177703 | 2026-06-01T17:46:27Z | 2026-06-01T17:46:37Z | `{"operationId":"5c28d630-2435-332a-8161-85e812d043fd","correlationId":"4c2c255d-213f-3b88-93b2-fce93c887130","siteId":"CENTRE","locationId":"e24b9067-d31d-3928-9dbf-208c4f74c58c","receiptId":"68a2a0a8-d79a-30cc-ab14-3419a13eb324"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 6eed40e8-2522-3e42-bab5-531a85d25d04 | 2026-06-02T17:46:27Z | 2026-06-02T17:46:37Z | `{"originalEventId":"6c34b448-97dd-3606-a509-1b01b9177703","correlationId":"4c2c255d-213f-3b88-93b2-fce93c887130","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"5817d4d3-6d67-337a-8d23-3038191df5be"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"7d9661cb-c950-300a-8e17-4efcd6b890ae","copyId":"c8036ce6-e8f0-3aad-b436-6d0729d46cee","locationId":"3eafb5d4-ad2e-33e1-9bcc-ab99cd7d700a","scopeId":"e52326b9-6e72-3a05-b447-732f60c19358","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"c8036ce6-e8f0-3aad-b436-6d0729d46cee","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c8036ce6-e8f0-3aad-b436-6d0729d46cee","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c8036ce6-e8f0-3aad-b436-6d0729d46cee","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c8036ce6-e8f0-3aad-b436-6d0729d46cee","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c8036ce6-e8f0-3aad-b436-6d0729d46cee","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c8036ce6-e8f0-3aad-b436-6d0729d46cee","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"c8036ce6-e8f0-3aad-b436-6d0729d46cee","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"observation","id":"7d9661cb-c950-300a-8e17-4efcd6b890ae"},{"kind":"systemState","id":"c8036ce6-e8f0-3aad-b436-6d0729d46cee"}]`

## Cas 9b96b71c-12f1-3353-9420-1698ad17f08c — dossier d'écart

- Exemplaire : `51cbab5e-8310-3468-86d0-8a94282903cf`
- Famille privée : REPARATION_NON_PROJETEE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"e4946bee-ba51-3974-9c62-adf04f467b1a","kind":"STATUS_MISMATCH","expectedValue":"ON_SHELF","observedValue":"IN_REPAIR"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"f12fb25d-9a3c-3be1-899f-5610acf90eed","physicalStatus":"IN_REPAIR","physicalSiteId":"NORD","physicalLocationId":"f2f0b9bd-bb60-3767-b64f-12b2812c2c3b","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"51cbab5e-8310-3468-86d0-8a94282903cf","status":"ON_SHELF","siteId":"NORD","locationId":"0888c111-02ad-379f-8d1e-405702cf1f0b","sourceEventIds":["95c3c344-575c-36ba-9d62-11e1d1f704e5","70ca2de4-c0aa-3273-bf33-ebd801c76a85"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 95c3c344-575c-36ba-9d62-11e1d1f704e5 | 2026-06-01T20:12:55Z | 2026-06-01T20:13:05Z | `{"operationId":"11b1c49e-c1eb-394f-a8af-e3090ce74551","correlationId":"c940335e-3b35-3355-ad6d-27c7f3e92ce2","siteId":"NORD","locationId":"0a084b29-28a8-37c7-b061-e68202e602f5","receiptId":"a0488146-fc8e-3a5c-ad18-92a4dfc6d70d"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 70ca2de4-c0aa-3273-bf33-ebd801c76a85 | 2026-06-02T20:12:55Z | 2026-06-02T20:13:05Z | `{"originalEventId":"95c3c344-575c-36ba-9d62-11e1d1f704e5","correlationId":"c940335e-3b35-3355-ad6d-27c7f3e92ce2","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"0888c111-02ad-379f-8d1e-405702cf1f0b"}` |
| Réparation engagée (`RepairStarted`) | f12fb25d-9a3c-3be1-899f-5610acf90eed | 2026-09-28T11:17:16Z | 2026-09-28T11:17:26Z | `{"operationId":"2496fc52-28a8-3b72-be20-61798ad3358b","correlationId":"7b13c309-4b66-38fb-82df-a7d06b63ad2d","siteId":"NORD","locationId":"f2f0b9bd-bb60-3767-b64f-12b2812c2c3b","repairId":"21f3b2b8-541d-34ab-b6f4-8310c8a75ecc"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 8d481e43-c8c2-3df4-be57-850092fbb0cc | 2026-09-28T11:17:46Z | 2026-09-28T11:17:56Z | `{"originalEventId":"f12fb25d-9a3c-3be1-899f-5610acf90eed","correlationId":"7b13c309-4b66-38fb-82df-a7d06b63ad2d","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"NORD","locationId":"f2f0b9bd-bb60-3767-b64f-12b2812c2c3b","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"a583a348-bbb8-3f86-9e7c-5f16acac05fd","copyId":"51cbab5e-8310-3468-86d0-8a94282903cf","locationId":"f2f0b9bd-bb60-3767-b64f-12b2812c2c3b","scopeId":"d109a6db-3413-31dc-b1cd-efc5ae7c1559","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"51cbab5e-8310-3468-86d0-8a94282903cf","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"51cbab5e-8310-3468-86d0-8a94282903cf","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"51cbab5e-8310-3468-86d0-8a94282903cf","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"51cbab5e-8310-3468-86d0-8a94282903cf","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"51cbab5e-8310-3468-86d0-8a94282903cf","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"51cbab5e-8310-3468-86d0-8a94282903cf","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"51cbab5e-8310-3468-86d0-8a94282903cf","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"51cbab5e-8310-3468-86d0-8a94282903cf"},{"kind":"sourceEvent","id":"95c3c344-575c-36ba-9d62-11e1d1f704e5"},{"kind":"sourceEvent","id":"70ca2de4-c0aa-3273-bf33-ebd801c76a85"},{"kind":"sourceEvent","id":"f12fb25d-9a3c-3be1-899f-5610acf90eed"},{"kind":"sourceEvent","id":"8d481e43-c8c2-3df4-be57-850092fbb0cc"},{"kind":"observation","id":"a583a348-bbb8-3f86-9e7c-5f16acac05fd"},{"kind":"inventoryScope","id":"d109a6db-3413-31dc-b1cd-efc5ae7c1559"},{"kind":"sourceAvailability","id":"51cbab5e-8310-3468-86d0-8a94282903cf/LOANS"},{"kind":"sourceAvailability","id":"51cbab5e-8310-3468-86d0-8a94282903cf/RETURNS"},{"kind":"sourceAvailability","id":"51cbab5e-8310-3468-86d0-8a94282903cf/RECEIPTS"},{"kind":"sourceAvailability","id":"51cbab5e-8310-3468-86d0-8a94282903cf/TRANSFERS"},{"kind":"sourceAvailability","id":"51cbab5e-8310-3468-86d0-8a94282903cf/REPAIRS"},{"kind":"sourceAvailability","id":"51cbab5e-8310-3468-86d0-8a94282903cf/INTEGRATIONS"},{"kind":"sourceAvailability","id":"51cbab5e-8310-3468-86d0-8a94282903cf/INVENTORY"},{"kind":"inventoryScope","id":"0d0e6f8c-8a81-343a-bb30-82a80b94b45c"}]`

- Session : `{"scopeId":"d109a6db-3413-31dc-b1cd-efc5ae7c1559","locationId":"f2f0b9bd-bb60-3767-b64f-12b2812c2c3b","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"0d0e6f8c-8a81-343a-bb30-82a80b94b45c","locationId":"0888c111-02ad-379f-8d1e-405702cf1f0b","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas bf16e182-3c1b-3fe4-ada6-0cb72519e0e5 — dossier d'écart

- Exemplaire : `51f0dfc0-064e-31ff-92b0-a29c2318d626`
- Famille privée : REPARATION_NON_PROJETEE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"44f413b9-4ce5-36ed-92f1-e1a118795ee0","kind":"STATUS_MISMATCH","expectedValue":"ON_SHELF","observedValue":"IN_REPAIR"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"736ae700-7188-3744-8bfe-0f50a94f08a1","physicalStatus":"IN_REPAIR","physicalSiteId":"NORD","physicalLocationId":"f2f0b9bd-bb60-3767-b64f-12b2812c2c3b","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"51f0dfc0-064e-31ff-92b0-a29c2318d626","status":"ON_SHELF","siteId":"NORD","locationId":"0888c111-02ad-379f-8d1e-405702cf1f0b","sourceEventIds":["9bae4588-017d-3e08-a536-817290ab1197","74ddde80-0210-35a2-898f-84301223d09b"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 9bae4588-017d-3e08-a536-817290ab1197 | 2026-06-01T18:40:03Z | 2026-06-01T18:40:13Z | `{"operationId":"b14b853d-1123-3203-b1f9-ca8766b9dcc2","correlationId":"8cc5c253-e996-3205-9579-eef6533bf270","siteId":"NORD","locationId":"0a084b29-28a8-37c7-b061-e68202e602f5","receiptId":"21efc0b6-b4ea-3f0f-b06c-33356e2e5b3d"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 74ddde80-0210-35a2-898f-84301223d09b | 2026-06-02T18:40:03Z | 2026-06-02T18:40:13Z | `{"originalEventId":"9bae4588-017d-3e08-a536-817290ab1197","correlationId":"8cc5c253-e996-3205-9579-eef6533bf270","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"NORD","locationId":"0888c111-02ad-379f-8d1e-405702cf1f0b"}` |
| Réparation engagée (`RepairStarted`) | 736ae700-7188-3744-8bfe-0f50a94f08a1 | 2026-09-28T06:13:10Z | 2026-09-28T06:13:20Z | `{"operationId":"eed08efa-49a9-34d6-96da-ccb763ebc46c","correlationId":"0083034f-a708-3e7e-9490-c4ee4f8d88d5","siteId":"NORD","locationId":"f2f0b9bd-bb60-3767-b64f-12b2812c2c3b","repairId":"dc21e54b-d1ca-3bfa-ab46-283e7e8e0327"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 1acc8271-2e69-307d-8857-85eba2e54309 | 2026-09-28T06:13:40Z | 2026-09-28T06:13:50Z | `{"originalEventId":"736ae700-7188-3744-8bfe-0f50a94f08a1","correlationId":"0083034f-a708-3e7e-9490-c4ee4f8d88d5","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"NORD","locationId":"f2f0b9bd-bb60-3767-b64f-12b2812c2c3b","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"f7b89f91-19fd-3528-ac6d-1ed21448045b","copyId":"51f0dfc0-064e-31ff-92b0-a29c2318d626","locationId":"f2f0b9bd-bb60-3767-b64f-12b2812c2c3b","scopeId":"3aa74708-28c4-303c-895a-a49a50a6df10","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"51f0dfc0-064e-31ff-92b0-a29c2318d626","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"51f0dfc0-064e-31ff-92b0-a29c2318d626","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"51f0dfc0-064e-31ff-92b0-a29c2318d626","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"51f0dfc0-064e-31ff-92b0-a29c2318d626","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"51f0dfc0-064e-31ff-92b0-a29c2318d626","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"51f0dfc0-064e-31ff-92b0-a29c2318d626","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"51f0dfc0-064e-31ff-92b0-a29c2318d626","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"51f0dfc0-064e-31ff-92b0-a29c2318d626"},{"kind":"sourceEvent","id":"9bae4588-017d-3e08-a536-817290ab1197"},{"kind":"sourceEvent","id":"74ddde80-0210-35a2-898f-84301223d09b"},{"kind":"sourceEvent","id":"736ae700-7188-3744-8bfe-0f50a94f08a1"},{"kind":"sourceEvent","id":"1acc8271-2e69-307d-8857-85eba2e54309"},{"kind":"observation","id":"f7b89f91-19fd-3528-ac6d-1ed21448045b"},{"kind":"inventoryScope","id":"3aa74708-28c4-303c-895a-a49a50a6df10"},{"kind":"sourceAvailability","id":"51f0dfc0-064e-31ff-92b0-a29c2318d626/LOANS"},{"kind":"sourceAvailability","id":"51f0dfc0-064e-31ff-92b0-a29c2318d626/RETURNS"},{"kind":"sourceAvailability","id":"51f0dfc0-064e-31ff-92b0-a29c2318d626/RECEIPTS"},{"kind":"sourceAvailability","id":"51f0dfc0-064e-31ff-92b0-a29c2318d626/TRANSFERS"},{"kind":"sourceAvailability","id":"51f0dfc0-064e-31ff-92b0-a29c2318d626/REPAIRS"},{"kind":"sourceAvailability","id":"51f0dfc0-064e-31ff-92b0-a29c2318d626/INTEGRATIONS"},{"kind":"sourceAvailability","id":"51f0dfc0-064e-31ff-92b0-a29c2318d626/INVENTORY"},{"kind":"inventoryScope","id":"0d0e6f8c-8a81-343a-bb30-82a80b94b45c"}]`

- Session : `{"scopeId":"3aa74708-28c4-303c-895a-a49a50a6df10","locationId":"f2f0b9bd-bb60-3767-b64f-12b2812c2c3b","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"0d0e6f8c-8a81-343a-bb30-82a80b94b45c","locationId":"0888c111-02ad-379f-8d1e-405702cf1f0b","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas c17e8338-98a4-3da5-9eaa-b82ff4f073f9 — dossier d'écart

- Exemplaire : `a5140b20-e9ae-32df-a742-b43d52fb5fd5`
- Famille privée : LOCALISATION ; variante : POSITIVE
- Symptômes : `[{"symptomId":"93bd9320-fc9b-379f-89fe-6ebe7e7a4fc1","kind":"LOCATION_MISMATCH","expectedValue":"222dee58-1b72-38dc-9071-8d14b0141831","observedValue":"7e01dcbb-4374-3d45-b549-15c90e11ee34"}]`
- Vérité injectée (non accessible au diagnostic) : `{"mechanism":"Déplacement physique sans modification de l'affectation ; aucune attribution à un opérateur.","activePerturbations":["LOCATION_CHANGE_UNPROJECTED"],"physicalStatus":"ON_SHELF","physicalSiteId":"SUD","physicalLocationId":"7e01dcbb-4374-3d45-b549-15c90e11ee34"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["LOCATION_MISMATCH"],"explanation":"Observation unique valide à un autre emplacement du même site ; écart établi, origine humaine non prouvée.","forbiddenClaims":["Vol","Perte définitive","Responsabilité d'un opérateur"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"a5140b20-e9ae-32df-a742-b43d52fb5fd5","status":"ON_SHELF","siteId":"SUD","locationId":"222dee58-1b72-38dc-9071-8d14b0141831","sourceEventIds":["282162a6-a74d-3798-a007-debc01836e20","51f6ab94-dd4b-391e-ab19-8e8b9c4aa7fa"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 282162a6-a74d-3798-a007-debc01836e20 | 2026-06-01T21:28:47Z | 2026-06-01T21:28:57Z | `{"operationId":"840a6eba-5fcd-3f3f-ad2b-b49d7c644272","correlationId":"60c2dcb7-15dc-353f-9313-3b85253127fc","siteId":"SUD","locationId":"00337e33-b95d-3e84-a341-2287c0bad954","receiptId":"e7445ef7-fea5-3ad8-beae-5319ae06f882"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 51f6ab94-dd4b-391e-ab19-8e8b9c4aa7fa | 2026-06-02T21:28:47Z | 2026-06-02T21:28:57Z | `{"originalEventId":"282162a6-a74d-3798-a007-debc01836e20","correlationId":"60c2dcb7-15dc-353f-9313-3b85253127fc","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"222dee58-1b72-38dc-9071-8d14b0141831"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"ddd17393-a52b-3a2d-a0c0-d82b402087d0","copyId":"a5140b20-e9ae-32df-a742-b43d52fb5fd5","locationId":"7e01dcbb-4374-3d45-b549-15c90e11ee34","scopeId":"40cc9a1b-db3e-31fb-ab9b-ca90c0b24850","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"a5140b20-e9ae-32df-a742-b43d52fb5fd5","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a5140b20-e9ae-32df-a742-b43d52fb5fd5","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a5140b20-e9ae-32df-a742-b43d52fb5fd5","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a5140b20-e9ae-32df-a742-b43d52fb5fd5","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a5140b20-e9ae-32df-a742-b43d52fb5fd5","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a5140b20-e9ae-32df-a742-b43d52fb5fd5","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a5140b20-e9ae-32df-a742-b43d52fb5fd5","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"observation","id":"ddd17393-a52b-3a2d-a0c0-d82b402087d0"},{"kind":"systemState","id":"a5140b20-e9ae-32df-a742-b43d52fb5fd5"}]`

## Cas eb92ea0b-0ef5-3b39-b901-7b266fbe4818 — dossier d'écart

- Exemplaire : `a05034ad-9a9a-37b7-b554-cf10a5c1b8a8`
- Famille privée : REPARATION_NON_PROJETEE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"ad4ff49c-1b28-3ee9-a846-ace49c4bb796","kind":"STATUS_MISMATCH","expectedValue":"ON_SHELF","observedValue":"IN_REPAIR"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"82af9816-e895-3b44-9d2b-29577752111b","physicalStatus":"IN_REPAIR","physicalSiteId":"SUD","physicalLocationId":"544db59a-742f-31f1-9cd8-c4ebd918fc63","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"a05034ad-9a9a-37b7-b554-cf10a5c1b8a8","status":"ON_SHELF","siteId":"SUD","locationId":"222dee58-1b72-38dc-9071-8d14b0141831","sourceEventIds":["a7a00e30-6285-30a2-ae93-fe793e4f5ded","cb76bd36-3e97-39ff-9d4b-66aa6b444045"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | a7a00e30-6285-30a2-ae93-fe793e4f5ded | 2026-06-02T07:11:09Z | 2026-06-02T07:11:19Z | `{"operationId":"12a89453-67d9-35cc-98f2-27a9debddade","correlationId":"2864432b-2ab8-3d6f-8060-cc61d6e48d41","siteId":"SUD","locationId":"00337e33-b95d-3e84-a341-2287c0bad954","receiptId":"477b21c9-bd8f-3f06-badc-fd0f58f47100"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | cb76bd36-3e97-39ff-9d4b-66aa6b444045 | 2026-06-03T07:11:09Z | 2026-06-03T07:11:19Z | `{"originalEventId":"a7a00e30-6285-30a2-ae93-fe793e4f5ded","correlationId":"2864432b-2ab8-3d6f-8060-cc61d6e48d41","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"SUD","locationId":"222dee58-1b72-38dc-9071-8d14b0141831"}` |
| Réparation engagée (`RepairStarted`) | 82af9816-e895-3b44-9d2b-29577752111b | 2026-09-28T08:05:23Z | 2026-09-28T08:05:33Z | `{"operationId":"8965c461-c037-38e0-adf4-8aae1fb3c081","correlationId":"72dfc4bf-9327-3a30-a5d0-251b6b39d341","siteId":"SUD","locationId":"544db59a-742f-31f1-9cd8-c4ebd918fc63","repairId":"45f6d947-7db5-35b8-ac11-cefd895282db"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | 2a229ba9-4b39-3086-a59d-e8a60504869b | 2026-09-28T08:05:53Z | 2026-09-28T08:06:03Z | `{"originalEventId":"82af9816-e895-3b44-9d2b-29577752111b","correlationId":"72dfc4bf-9327-3a30-a5d0-251b6b39d341","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"SUD","locationId":"544db59a-742f-31f1-9cd8-c4ebd918fc63","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"3669563c-3ff5-3f44-b314-a6be244d4fab","copyId":"a05034ad-9a9a-37b7-b554-cf10a5c1b8a8","locationId":"544db59a-742f-31f1-9cd8-c4ebd918fc63","scopeId":"96a758d0-1062-3ab0-8824-15284d0eabcb","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"a05034ad-9a9a-37b7-b554-cf10a5c1b8a8","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a05034ad-9a9a-37b7-b554-cf10a5c1b8a8","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a05034ad-9a9a-37b7-b554-cf10a5c1b8a8","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a05034ad-9a9a-37b7-b554-cf10a5c1b8a8","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a05034ad-9a9a-37b7-b554-cf10a5c1b8a8","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a05034ad-9a9a-37b7-b554-cf10a5c1b8a8","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"a05034ad-9a9a-37b7-b554-cf10a5c1b8a8","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"a05034ad-9a9a-37b7-b554-cf10a5c1b8a8"},{"kind":"sourceEvent","id":"a7a00e30-6285-30a2-ae93-fe793e4f5ded"},{"kind":"sourceEvent","id":"cb76bd36-3e97-39ff-9d4b-66aa6b444045"},{"kind":"sourceEvent","id":"82af9816-e895-3b44-9d2b-29577752111b"},{"kind":"sourceEvent","id":"2a229ba9-4b39-3086-a59d-e8a60504869b"},{"kind":"observation","id":"3669563c-3ff5-3f44-b314-a6be244d4fab"},{"kind":"inventoryScope","id":"96a758d0-1062-3ab0-8824-15284d0eabcb"},{"kind":"sourceAvailability","id":"a05034ad-9a9a-37b7-b554-cf10a5c1b8a8/LOANS"},{"kind":"sourceAvailability","id":"a05034ad-9a9a-37b7-b554-cf10a5c1b8a8/RETURNS"},{"kind":"sourceAvailability","id":"a05034ad-9a9a-37b7-b554-cf10a5c1b8a8/RECEIPTS"},{"kind":"sourceAvailability","id":"a05034ad-9a9a-37b7-b554-cf10a5c1b8a8/TRANSFERS"},{"kind":"sourceAvailability","id":"a05034ad-9a9a-37b7-b554-cf10a5c1b8a8/REPAIRS"},{"kind":"sourceAvailability","id":"a05034ad-9a9a-37b7-b554-cf10a5c1b8a8/INTEGRATIONS"},{"kind":"sourceAvailability","id":"a05034ad-9a9a-37b7-b554-cf10a5c1b8a8/INVENTORY"},{"kind":"inventoryScope","id":"a777e499-f923-354a-9e8a-a2dfb870e8ba"}]`

- Session : `{"scopeId":"96a758d0-1062-3ab0-8824-15284d0eabcb","locationId":"544db59a-742f-31f1-9cd8-c4ebd918fc63","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"a777e499-f923-354a-9e8a-a2dfb870e8ba","locationId":"222dee58-1b72-38dc-9071-8d14b0141831","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

## Cas ecd72a2c-7bca-3a79-91fa-aac21b3d8811 — dossier d'écart

- Exemplaire : `153a4218-41cf-311a-ab2e-4ec5a3b633a1`
- Famille privée : REPARATION_NON_PROJETEE ; variante : POSITIVE
- Symptômes : `[{"symptomId":"d4212e3b-3a16-3f69-8c9e-2912efa2c3d7","kind":"STATUS_MISMATCH","expectedValue":"ON_SHELF","observedValue":"IN_REPAIR"}]`
- Vérité injectée (non accessible au diagnostic) : `{"activePerturbations":["PROJECTION_WRITE_FAILED"],"hiddenSourceEvents":[],"mechanism":"L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.","businessEventId":"2f0648a5-2a97-3850-ad16-e7aca1ca4ba2","physicalStatus":"IN_REPAIR","physicalSiteId":"CENTRE","physicalLocationId":"32ae07d9-c842-304d-b985-8905e18c4fb5","projectionPerturbation":"FAILED","evidenceMask":"Aucun"}`
- Diagnostic admissible attendu : `{"conclusion":"SUPPORTED","causeCodes":["REPAIR_NOT_PROJECTED"],"explanation":"Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.","forbiddenClaims":["Vol","Perte définitive","Défaillance persistante sans preuve"],"recommendedChecks":[]}`
- État du stock informatique : `{"copyId":"153a4218-41cf-311a-ab2e-4ec5a3b633a1","status":"ON_SHELF","siteId":"CENTRE","locationId":"5817d4d3-6d67-337a-8d23-3038191df5be","sourceEventIds":["40956909-b076-3ac8-83df-0f6ed9e286a9","bd9a3d4a-4a66-3fc1-a133-56899cf1131e"]}`

### Chronologie et preuves

| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |
|---|---|---|---|---|
| Réception enregistrée (`ReceiptReceived`) | 40956909-b076-3ac8-83df-0f6ed9e286a9 | 2026-06-01T18:37:34Z | 2026-06-01T18:37:44Z | `{"operationId":"2aee298b-005e-3402-8b71-1d891b09cfa2","correlationId":"8708a88e-1f6e-33bc-8836-9fb213173315","siteId":"CENTRE","locationId":"e24b9067-d31d-3928-9dbf-208c4f74c58c","receiptId":"2b2ed297-fd10-36e4-8518-9f53a2f7903b"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | bd9a3d4a-4a66-3fc1-a133-56899cf1131e | 2026-06-02T18:37:34Z | 2026-06-02T18:37:44Z | `{"originalEventId":"40956909-b076-3ac8-83df-0f6ed9e286a9","correlationId":"8708a88e-1f6e-33bc-8836-9fb213173315","outcome":"APPLIED","projectedStatus":"ON_SHELF","siteId":"CENTRE","locationId":"5817d4d3-6d67-337a-8d23-3038191df5be"}` |
| Réparation engagée (`RepairStarted`) | 2f0648a5-2a97-3850-ad16-e7aca1ca4ba2 | 2026-09-28T06:02:39Z | 2026-09-28T06:02:49Z | `{"operationId":"615bda8e-8aeb-3dd1-9fef-f8e627d3f8be","correlationId":"4aa74d09-91d9-31ba-94ea-6d0fc0e777b1","siteId":"CENTRE","locationId":"32ae07d9-c842-304d-b985-8905e18c4fb5","repairId":"259a08fe-13b7-3781-b2e7-931586b4c816"}` |
| Mise à jour du stock informatique (`IntegrationProcessed`) | a42c8a78-f63b-32a4-95d7-d2d9e4104962 | 2026-09-28T06:03:09Z | 2026-09-28T06:03:19Z | `{"originalEventId":"2f0648a5-2a97-3850-ad16-e7aca1ca4ba2","correlationId":"4aa74d09-91d9-31ba-94ea-6d0fc0e777b1","outcome":"FAILED","projectedStatus":"IN_REPAIR","siteId":"CENTRE","locationId":"32ae07d9-c842-304d-b985-8905e18c4fb5","maxProcessingDelaySeconds":900,"attempt":1,"errorCode":"PROJECTION_WRITE_UNAVAILABLE"}` |

**Observations d'inventaire (`observations`)**
- `{"observationId":"ba848f62-9d25-3b5a-a361-3bbe74e6c50e","copyId":"153a4218-41cf-311a-ab2e-4ec5a3b633a1","locationId":"32ae07d9-c842-304d-b985-8905e18c4fb5","scopeId":"87c7dfb0-e294-37fe-bac9-8c933276eeb6","observedAt":"2026-09-30T11:55:00Z","quality":"VALID"}`

**Disponibilité des sources (`sourceAvailability`)**
- `{"copyId":"153a4218-41cf-311a-ab2e-4ec5a3b633a1","source":"INTEGRATIONS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"153a4218-41cf-311a-ab2e-4ec5a3b633a1","source":"LOANS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"153a4218-41cf-311a-ab2e-4ec5a3b633a1","source":"TRANSFERS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"153a4218-41cf-311a-ab2e-4ec5a3b633a1","source":"INVENTORY","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"153a4218-41cf-311a-ab2e-4ec5a3b633a1","source":"RECEIPTS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"153a4218-41cf-311a-ab2e-4ec5a3b633a1","source":"REPAIRS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`
- `{"copyId":"153a4218-41cf-311a-ab2e-4ec5a3b633a1","source":"RETURNS","status":"COMPLETE","completeFrom":"2026-04-03T12:00:00Z","completeTo":"2026-09-30T12:00:00Z"}`

**Notes opérateur (`operatorNotes`)**

**Références de preuve résolubles**

`[{"kind":"systemState","id":"153a4218-41cf-311a-ab2e-4ec5a3b633a1"},{"kind":"sourceEvent","id":"40956909-b076-3ac8-83df-0f6ed9e286a9"},{"kind":"sourceEvent","id":"bd9a3d4a-4a66-3fc1-a133-56899cf1131e"},{"kind":"sourceEvent","id":"2f0648a5-2a97-3850-ad16-e7aca1ca4ba2"},{"kind":"sourceEvent","id":"a42c8a78-f63b-32a4-95d7-d2d9e4104962"},{"kind":"observation","id":"ba848f62-9d25-3b5a-a361-3bbe74e6c50e"},{"kind":"inventoryScope","id":"87c7dfb0-e294-37fe-bac9-8c933276eeb6"},{"kind":"sourceAvailability","id":"153a4218-41cf-311a-ab2e-4ec5a3b633a1/LOANS"},{"kind":"sourceAvailability","id":"153a4218-41cf-311a-ab2e-4ec5a3b633a1/RETURNS"},{"kind":"sourceAvailability","id":"153a4218-41cf-311a-ab2e-4ec5a3b633a1/RECEIPTS"},{"kind":"sourceAvailability","id":"153a4218-41cf-311a-ab2e-4ec5a3b633a1/TRANSFERS"},{"kind":"sourceAvailability","id":"153a4218-41cf-311a-ab2e-4ec5a3b633a1/REPAIRS"},{"kind":"sourceAvailability","id":"153a4218-41cf-311a-ab2e-4ec5a3b633a1/INTEGRATIONS"},{"kind":"sourceAvailability","id":"153a4218-41cf-311a-ab2e-4ec5a3b633a1/INVENTORY"},{"kind":"inventoryScope","id":"76e76ad9-fd50-351b-bf38-5562882dfdc0"}]`

- Session : `{"scopeId":"87c7dfb0-e294-37fe-bac9-8c933276eeb6","locationId":"32ae07d9-c842-304d-b985-8905e18c4fb5","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`

- Session : `{"scopeId":"76e76ad9-fd50-351b-bf38-5562882dfdc0","locationId":"5817d4d3-6d67-337a-8d23-3038191df5be","status":"COMPLETE","startedAt":"2026-09-30T11:00:00Z","closedAt":"2026-09-30T11:59:00Z"}`
