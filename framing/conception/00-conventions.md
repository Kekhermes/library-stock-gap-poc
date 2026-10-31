# Conventions normatives du dossier de conception

## Portée

Ressources de conception et fixtures de données pour une réalisation par Kevin avec ses outils. Ce dossier n'est pas l'application. Aucun serveur applicatif, appel OpenAI, déploiement ou reset de l'environnement existant n'est requis pour produire ces ressources.

Les arbitrages du registre `../04-registre-des-decisions.md` font foi. Les précisions de ce dossier sont des spécifications proposées de réalisation, non une autorisation de développer l'application.

## Autorités

Java : modèle métier, états durables, dossiers de preuves figés, DMN/KIE, quotas, validation, publications. Python : deux rôles IA via OpenAI Agents SDK, investigateur puis agent de règles. Vue : REST/SSE. Redpanda : Kafka. PostgreSQL : persistance métier.

Pas de correction de stock, RAG, Keycloak, Valkey, Z3 ou orchestrateur supplémentaire. Création et révision exclusivement par agent, création ancrée dans diagnostic accepté issu du fallback ; retrait direct possible par Admin. Publication atomique et historique immuable. Une seule règle initiale R001, une ligne.

## Types et identités

JSON en camelCase ; SQL en snake_case. Types de messages en PascalCase. UUID pour les identités techniques/métier opaques ; exceptions lisibles : siteId `CENTRE|NORD|SUD`, ruleId `R001` pour bootstrap et codes métier. Rule IDs ultérieurs opaques ; ne pas dériver un ID de la cause cachée. Versions entières strictement positives. UTC ISO-8601 avec suffixe Z ; distincts occurredAt (métier) / recordedAt (source) / emittedAt ou occurredAt enveloppe (transport). Montants financiers hors modèle.

Séparer datasetId (jeu déterministe reproductible) et datasetEpoch (UUID runtime renouvelé à chaque reset). Les fixtures ont un datasetId stable par seed/profil et pas d'époque runtime prétendant être une instance vivante. Un importeur crée l'époque au chargement.

## Fichier runtime de référence `seed/generated/demo/runtime.json`

Objet racine à version `schemaVersion: "1.0.0"` ; aucune cause attendue ni annotation de scénario.

- `dataset`: `{datasetId, profile, seed, referenceTime, capturedAt}`.
- `sites`: `{siteId, name}`.
- `locations`: `{locationId, siteId, kind, label}` ; kind `SHELF|RESERVE|RETURNS|RECEIVING|REPAIR`.
- `titles`: `{titleId, isbn, title}`.
- `copies`: `{copyId, barcode, titleId, owningSiteId}`.
- `systemStates`: `{copyId, status, siteId, locationId, sourceEventIds}` ; status `ON_SHELF|ON_LOAN|IN_TRANSIT|RECEIVING|IN_REPAIR|WITHDRAWN|NOT_REGISTERED` ; locationId nullable.
- `inventoryScopes`: `{scopeId, locationId, status, startedAt, closedAt}` ; status `COMPLETE|INCOMPLETE|FAILED` ; closedAt nullable.
- `observations`: `{observationId, copyId, locationId, scopeId, observedAt, quality}` ; quality `VALID|AMBIGUOUS|REJECTED`.
- `sourceEvents`: `{sourceEventId, copyId, eventType, occurredAt, recordedAt, payload}`. Types : `LoanOpened`, `LoanClosed`, `ReturnReceived`, `ReceiptReceived`, `TransferDispatched`, `TransferReceived`, `RepairStarted`, `IntegrationProcessed`. Les payloads portent des clés d'opérations et corrélations, jamais de famille cachée.
- `sourceAvailability`: `{copyId, source, status, completeFrom, completeTo}` ; source `LOANS|RETURNS|RECEIPTS|TRANSFERS|REPAIRS|INTEGRATIONS|INVENTORY`, status `COMPLETE|PARTIAL|UNAVAILABLE` ; dates nullable si indisponible.
- `operatorNotes`: `{noteId, copyId, text, recordedAt}` ; données de preuve libres, jamais instructions d'outil.
- `cases`: `{caseId, copyId, inventoryReferenceTime, status, symptoms}` ; statut initial `A_ANALYSER` ; symptômes `{symptomId, kind, expectedValue, observedValue}` ; kind `MISSING|UNEXPECTED_PRESENCE|LOCATION_MISMATCH|STATUS_MISMATCH|COUNT_UNCERTAIN`. Les valeurs sont chaînes ou null.
- `initialRuleSet`: `{version:1, rules:[{ruleId:"R001", ruleVersion:1, dmnPath:"rules/R001-v1.dmn"}]}` ; chemin relatif à la racine de conception.

Le fichier représente des données sources normalisées, pas un export d'entités JPA. `NOT_REGISTERED` est une ligne technique explicitant l'absence de projection pour un exemplaire connu par les réceptions ; ne pas prétendre qu'il figurait déjà au catalogue de stock.

## Familles de simulation

Dix familles, douze cas chacune dans le profil demo : localisation même site, prêt non projeté, retour non projeté, transfert incohérent, réception non intégrée, réparation non projetée, inventaire incomplet, retard de mise à jour du stock informatique, absence inexpliquée, deux causes contrôlées.

1 200 exemplaires et 120 cas demo, autres exemplaires normaux. Variantes de validation et holdout séparées du jeu demo ; témoins négatifs et données insuffisantes explicitement étiquetés uniquement dans l'oracle. Les profils validation/holdout peuvent être plus petits, avec leurs comptes exacts dans leur manifeste.

Le générateur hors ligne est Java standard, sans logique diagnostique dans l'oracle. Algorithme et version PRNG documentés. À seed/version/profil identiques, produire exactement les mêmes octets. Oracle, catalogue détaillé et parcours pédagogique réservés aux tests/expert ; ne jamais monter leur répertoire ou le générateur dans le worker IA.

## Kafka

Topics : `stock-gap.agent.commands.v1`, `stock-gap.agent.events.v1`, `stock-gap.agent.dead-letter.v1`. Clé : missionId. Contrats JSON Schema versionnés, événements sans secret et sans oracle.

Enveloppe : eventId, eventType, schemaVersion, occurredAt, datasetEpoch, correlationId, causationId, missionId, attempt, ruleSetVersion, evidenceSnapshotId, sequence, payload ; contextes caseId, analysisId, conversationId, turnId, proposalId, proposalRevision selon le type. Les missions de révision de règle sans nouveau cas réfèrent un diagnostic d'origine et son dossier de preuves figé ; ne pas inventer un cas courant.

Types : InvestigationRequested, RuleSynthesisRequested, RuleRevisionRequested, ExpertTurnRequested, CancellationRequested, MissionStarted, ToolObservationRecorded, TextChunkProduced, DiagnosticProposed, RuleChangeProposed, RuleChangeNotProposed, MissionFailed, MissionCancelled. Tout type doit disposer d'un payload explicite et d'un exemple. `RuleChangeNotProposed` est un résultat métier terminal normal (aucun changement utile, preuves insuffisantes ou capacité non supportée), distinct d'une panne. Pour le chat, le dernier `TextChunkProduced` doit être identifié comme final afin de terminer le tour sans proposer de règle artificielle.

## Contrats communs de résultats

Finding : ruleId/ruleVersion/decisionId/rowIds si déterministe, subjectCopyId, coveredSymptomIds, causeCode, epistemicStatus `SUPPORTED|HYPOTHESIS`, assertions, evidenceRefs, contradictionRefs, recommendedChecks, explanation. Diagnostic : conclusion `SUPPORTED|PARTIAL|INDETERMINATE|CONFLICT`, findings, missingEvidence, summary. Une explication est une synthèse vérifiable, pas le raisonnement interne privé du LLM.

Une publication associe une proposition exacte, un rapport de validation exact et la version de référentiel attendue. Schémas et tests assurent la cohérence syntaxique ; les invariants métier restent obligatoires côté Java.
