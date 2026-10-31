# Import, dictionnaire des sources et normalisation des faits

## 1. Contrat importé, sans renommage implicite

Autorité : `../00-conventions.md`, fichier `../seed/generated/demo/runtime.json` ; le générateur Java émet les mêmes tableaux pour ses autres profils. Ce n'est pas un export JPA. L'époque runtime ne figure pas dans les fixtures : l'importeur génère un UUID aléatoire neuf et l'ajoute à **toutes** les lignes runtime de cette importation. `datasetId` reste exactement celui du fichier.

### Mapping exhaustif JSON → SQL

Dans chaque ligne, les noms non cités ne sont pas importés silencieusement. Les identifiants opaques sont contrôlés avant conversion UUID ; ne pas « réparer » un token invalide.

| Objet/tableau JSON | Destination et mapping exact | Traitement |
|---|---|---|
| `schemaVersion` | `dataset.schema_version` | Version `1.0.0` requise |
| `dataset` | `datasetId→dataset_id`, `profile→profile`, `seed→seed`, `referenceTime→reference_time`, `capturedAt→captured_at` dans `dataset` | `generator_version` vient du manifeste/générateur ; `runtime_sha256` des octets du fichier, pas de sa réécriture JSON |
| `sites[]` | `siteId→site.site_id`, `name→site.name` | Code de site, pas UUID ; aucune conversion vers `siteCode` dans le JSON partagé |
| `locations[]` | `locationId→location.location_id`, `siteId→site_id`, `kind→kind`, `label→label` | FK site ; kinds conservés |
| `titles[]` | `titleId→title.title_id`, `isbn→isbn`, `title→title` | ISBN chaîne, pas entier ; pas d'unicité ISBN supposée par le contrat |
| `copies[]` | `copyId→copy.copy_id`, `barcode→barcode`, `titleId→title_id`, `owningSiteId→owning_site_id` | Barcode unique dans l'époque |
| `systemStates[]` | `copyId→system_state.copy_id`, `status→status`, `siteId→site_id`, `locationId→location_id` | Nullable location conservée ; une ligne par exemplaire requise par l'importeur |
| `systemStates[].sourceEventIds[]` | `system_state_event(copy_id, source_event_id, position)` | Ordre zéro-based et déduplication interdite : doublon = rejet, pas correction ; FK garantit le même exemplaire |
| `inventoryScopes[]` | `scopeId→inventory_scope.scope_id`, `locationId→location_id`, `status→status`, `startedAt→started_at`, `closedAt→closed_at` | FK location ; null de clôture conservé |
| `observations[]` | `observationId→observation.observation_id`, `copyId→copy_id`, `locationId→location_id`, `scopeId→scope_id`, `observedAt→observed_at`, `quality→quality` | FK composite scope/location ; qualité non réduite à vrai/faux |
| `sourceEvents[]` | `sourceEventId→source_event.source_event_id`, `copyId→copy_id`, `eventType→event_type`, `occurredAt→occurred_at`, `recordedAt→recorded_at`, `payload→payload` | JSONB typé par discriminant ; clés internes camelCase inchangées |
| `sourceAvailability[]` | `copyId→source_availability.copy_id`, `source→source`, `status→status`, `completeFrom→complete_from`, `completeTo→complete_to` | PK copie/source ; null = inconnu, pas période infinie |
| `operatorNotes[]` | `noteId→operator_note.note_id`, `copyId→copy_id`, `text→text`, `recordedAt→recorded_at` | Texte auxiliaire ; jamais interprété comme instruction |
| `cases[]` | `caseId→source_case.case_id`, `copyId→copy_id`, `inventoryReferenceTime→inventory_reference_time`, `status→initial_status` | `initial_status=A_ANALYSER` immuable ; création séparée de `gap_case` avec même état initial |
| `cases[].symptoms[]` | `symptomId→gap_symptom.symptom_id`, parent `caseId→case_id`, `kind→kind`, `expectedValue→expected_value`, `observedValue→observed_value` | Position zéro-based calculée ; chaînes/null conservées ; au moins un symptôme validé hors SQL |
| `initialRuleSet.version` | `rule_set_publication.rule_set_version`, puis `active_rule_set.rule_set_version` | Exiger exactement `1` ; publication BOOTSTRAP |
| `initialRuleSet.rules[]` | `ruleId→rule.rule_id + rule_version.rule_id + rule_set_member.rule_id`, `ruleVersion→rule_version.rule_version + rule_set_member.rule_version` | Exiger une seule entrée, `R001`, version `1` ; membre unique de publication 1 |
| `initialRuleSet.rules[].dmnPath` | Lecture de l'artefact vers `rule_version.dmn_xml`, hash vers `artifact_sha256`, chemin conservé dans `metadata.bootstrapDmnPath` | Chemin relatif à conception ; résoudre et vérifier confinement dans `rules/`, refuser traversal/URL/liens sortants ; chemin exact attendu `rules/R001-v1.dmn` |

Le dataset ne contient pas de `analysis_run`, mission, réponse IA ou conversation préfabriquée. Les fichiers oracle, parcours et catalogue de scénarios ne sont **jamais** passés à cette fonction d'import runtime.

## 2. Événements source : payloads réels et typage

`03-source-event.schema.json` décrit la forme complète `{sourceEventId, copyId, eventType, occurredAt, recordedAt, payload}`. Il utilise `oneOf`, discriminateur `eventType`, propriétés requises et `additionalProperties:false`. Activer la validation de `format:uuid`/`date-time` : certains validateurs ne la font pas par défaut. Les dates doivent finir par Z. Les identifiants d'opération sont opaques et ne codent pas une famille de simulation.

Le DDL vérifie les huit types, `payload` objet, les clés communes et les clés discriminantes. Il ne sait pas valider la totalité du JSON Schema, l'existence des identités imbriquées ou les chronologies inter-événements. Ces vérifications sont **obligatoires avant INSERT**, pas reportées au premier diagnostic.

### Clés communes aux événements métier (hors IntegrationProcessed)

| Clé | Type | Sens |
|---|---|---|
| `operationId` | UUID requis | Identité de l'opération source représentée par cet événement |
| `correlationId` | UUID requis | Corrélation avec son traitement de mise à jour du stock informatique ; pas nécessairement commune aux étapes d'un prêt/transfert |
| `siteId` | `CENTRE|NORD|SUD` requis | Site où l'événement est constaté |
| `locationId` | UUID ou null, propriété requise | Emplacement de l'opération, si connu ; doit appartenir à siteId si renseigné |

### Variantes discriminées

| eventType | Clés supplémentaires requises | Corrélation/normalisation |
|---|---|---|
| `LoanOpened` | `loanId: UUID` | Ouvre un état de prêt pour `(copyId, loanId)` ; l'absence de LoanClosed n'est probante que sur une source complète |
| `LoanClosed` | `loanId: UUID` | Ferme le même prêt ; ne prouve pas à lui seul une état du stock informatique correcte |
| `ReturnReceived` | `loanId: UUID` | Réception physique du retour ; `returnId` normalisé = `operationId` de cet événement, car le payload partagé ne contient pas de returnId |
| `ReceiptReceived` | `receiptId: UUID` | Réception physique ; un exemplaire peut être connu par cette source mais avoir un état du stock informatique `NOT_REGISTERED` |
| `TransferDispatched` | `transferId: UUID`, `fromSiteId: site`, `toSiteId: site` | Départ ; source et destination distinctes, siteId correspond au départ |
| `TransferReceived` | `transferId: UUID`, `fromSiteId: site`, `toSiteId: site` | Arrivée ; même transfert et exemplaire, siteId correspond à la destination |
| `RepairStarted` | `repairId: UUID` | Dossier de réparation ; conserver aussi operationId, sans inventer une fin de réparation absente |
| `IntegrationProcessed` | Variante ci-dessous, **sans operationId** | Corrélation explicite à l'événement métier original, pas à un libellé de cause |

### Variante IntegrationProcessed

| Clé | Type / valeurs | Sens |
|---|---|---|
| `originalEventId` | UUID requis | Référence à sourceEvents[].sourceEventId d'un événement métier du **même exemplaire** |
| `correlationId` | UUID requis | Doit égaler la correlationId du payload original |
| `outcome` | `APPLIED|QUEUED|FAILED` | Résultat/état observable du traitement, pas une cause diagnostique |
| `projectedStatus` | Statut de systemStates | État cible tenté ; un FAILED/QUEUED ne prouve pas son application |
| `siteId` | Code site requis | Site que la mise à jour du stock informatique doit enregistrer |
| `locationId` | UUID ou null, propriété requise | Lieu que la mise à jour du stock informatique doit enregistrer |

L'instant de traitement est `source_event.occurred_at` de l'événement de mise à jour du stock informatique ; sa visibilité est `recorded_at`. Le payload ne contient ni `processedAt`, ni `businessEventId`, ni `targetProjection`, ni `state` : ce sont des **champs dérivés de l'adaptateur de faits**, pas des noms à imposer au générateur. Dans les faits proposés au cadrage :

- `integrations[].eventId = sourceEventId` ; `businessEventId = payload.originalEventId` ; `targetProjection = "SYSTEM_STATE"`, constante explicite de cet adaptateur ; `state = payload.outcome` ; `processedAt = occurredAt`.
- `loans[].loanId = payload.loanId`, état OPEN/CLOSED issu du discriminateur ; conserver les deux instants et sourceEventId. Une vue « état courant » se calcule à la date du dossier de preuves figé, sans détruire les événements.
- `returns[].returnId = payload.operationId` ; `loanId` inchangé.
- `receipts[].receiptId = payload.receiptId`, état RECEIVED dérivé de ReceiptReceived.
- `transfers[]` groupe `(copyId, transferId)` et conserve `sourceEventIds` ordonnés ; sentAt/receivedAt proviennent des événements respectifs, nullable si étape absente. Ne pas trier les événements de tout le réseau par simple ordre d'insertion.
- `maintenance[].operationId = payload.operationId`, `repairId` conservé dans les preuves, type REPAIR et état STARTED dérivés de RepairStarted. Aucune clôture n'est inférée de l'absence d'autre événement.

Ces noms normalisés sont un **mapping de réalisation** de `../../11-contrat-faits-et-dmn.md` ; l'adaptateur DMN final doit être versionné avec le schéma des faits. Le contexte minimal de `R001` peut contenir des faits typés extraits de ce dossier de preuves figé, pas une relecture non figée. Les notes n'entrent pas dans les conditions FEEL.

## 3. Contrôles inter-lignes d'import

En plus du DDL :

1. Racine exacte et présence de tous les tableaux ; UUID valides et uniques ; pas de clés de scénario/oracle ni de champ inconnu. Validation récursive du contrat, pas recherche de sous-chaînes seulement.
2. Comptes réels comparés au manifeste. Pour demo : 1 200 exemplaires / 120 cas ; tailles des autres profils prises au manifeste, non extrapolées. Une ligne systemStates par exemplaire ; une disponibilité par couple copie/source déclaré ; si les sept sources sont attendues par le profil, vérifier exactement ces sept entrées.
3. Au moins un symptôme par cas ; même exemplaire partout ; aucune FK manquante. Plusieurs symptômes autorisés. Pas de multiplication d'un cas pour représenter ses causes.
4. Payloads validés et références d'opérations cohérentes : même exemplaire, transfert cohérent, fromSiteId ≠ toSiteId, locations rattachées au bon site. Original de mise à jour du stock informatique existant, non IntegrationProcessed, même correlationId, temps original ≤ temps de mise à jour du stock informatique. Les domaines de prêt/réception/réparation ne sont pas interchangés.
5. Observation dans le périmètre et la fenêtre de sa collecte ; couverture COMPLETE réellement clôturée. Source COMPLETE couvre la fenêtre interrogée ; une liste filtrée/tronquée n'est pas complète par magie.
6. `recordedAt >= occurredAt` (SQL), et visibilité source à `capturedAt`/date de référence traitée explicitement. Ne pas antidater une preuve tardive pour « expliquer » le cas. Les faits postérieurs à inventoryReferenceTime ne sont pas utilisés comme preuves d'un état antérieur.
7. État du stock informatique compatible avec les mises à jour **effectivement APPLIED** ; les écarts entre opérations métier et état informatique sont précisément les faits simulés, ne pas les « réparer » ni rejeter un écart intentionnel de stock. Contrôler l'intégrité référentielle, pas imposer à l'import qu'il n'existe aucun écart.
8. Bootstrap : parsing XML/DMN, un module, une table, une ligne de diagnostic ; hash et decisionId extraits réellement, moteur/fact schema explicités. `decision_row_count=1` est une métadonnée SQL, pas un substitut au comptage XML.

## 4. Ordre transactionnel d'import runtime

L'importeur lit/valide les fichiers avant d'ouvrir la transaction ; aucune requête LLM, broker ou oracle pendant la transaction.

```text
Lire runtime.json + manifeste ; vérifier hash, contrat, comptes, source-event.schema.
Résoudre R001-v1.dmn sous conception/rules ; compiler et compter une ligne.
BEGIN ; timezone UTC ; transaction avec rôle seed_runtime.
  INSERT dataset (ou vérifier égalité exacte si datasetId existe déjà ; pas d'UPDATE).
  INSERT dataset_epoch (nouvel UUID, imported_at temps réel, sealed_at NULL).
  INSERT site ; location ; title ; copy.
  INSERT source_event (tous ; corrélations JSON prévalidées).
  INSERT system_state ; system_state_event.
  INSERT inventory_scope ; observation ; source_availability ; operator_note.
  INSERT source_case ; gap_symptom ; gap_case(A_ANALYSER, lock_version=0).
  INSERT rule(R001, origin_acceptance_id=NULL).
  INSERT rule_version(R001, 1, BOOTSTRAP, artefact exact, une ligne).
  INSERT rule_set_publication(v1, BOOTSTRAP, command_id neuf, manifeste calculé).
  INSERT rule_set_member(v1, R001, 1).
  UPDATE rule_set_publication(v1) SET sealed_at=temps réel ; INSERT active_rule_set(v1).
  INSERT execution_slot(1 libre), execution_slot(2 libre), sse_stream(0,1).
  Vérifier à nouveau les comptes SQL, tous les liens et le manifeste canonique.
  UPDATE dataset_epoch SET sealed_at=temps réel.
  INSERT active_dataset(singleton=true, époque neuve).
COMMIT.
Vérifier par lectures exactes la clôture, le pointeur et le membre R001 avant état READY.
Retirer l'accès opérationnel de l'importeur ; ne pas l'utiliser pour Java.
```

Le script ne crée pas de lignes dataset/époque arbitraires : ces valeurs viennent du fichier et de l'importeur réel. Les exemples ne prétendent pas être une instance vivante.

`guard_source_insert` prend un verrou SHARE sur l'époque ; sa clôture attend les imports concurrents. Après clôture, même une nouvelle ligne source est refusée ; UPDATE/DELETE sont bloqués par les triggers immuables et les privilèges. `TRUNCATE` n'est pas intercepté par ces triggers de lignes, donc aucun rôle applicatif ne reçoit ce droit.

L'import de `evaluation.corpus` et `case_expectation` se fait **séparément** avec `sg_seed_oracle`. Son manifeste et ses comptes sont validés ; le schéma n'utilise pas de FK vers runtime et n'envoie aucune donnée dans l'import runtime. Le processus seed qui a accès aux deux fichiers reste hors du worker et sépare ses connexions/credentials.

## 5. Réimport, échec et provenance

Échec de validation : aucune écriture. Échec SQL : rollback intégral, pas d'époque active partielle. Même datasetId avec empreinte ou métadonnées différentes : conflit explicite ; ce n'est pas une autorisation d'écraser le manifeste. Une époque déjà clôturée ne se rouvre jamais. Un reset prévu par le cadrage recrée le stockage du **seul projet** puis exécute l'import ci-dessus avec une nouvelle époque ; pas d'upsert global sur les sources.

Toute lecture source de Java filtre explicitement l'époque active/autorisée, puis celle du dossier de preuves figé. Toutes les FK runtime de contexte incluent l'époque. Une FK ne remplace pas cette autorisation : l'historique d'une autre époque ne doit pas être rendu par une route courante.
