# Kafka : enveloppes, adoption et cycle de vie

## Topics et consommateurs

Choix proposés pour le POC local Redpanda, à matérialiser ensuite dans l'infrastructure (aucun topic n'a été créé par ce dossier) :

| Topic | Producteur | Consumer group | Rétention / cleanup |
|---|---|---|---|
| `stock-gap.agent.commands.v1` | outbox Java | `stock-gap-agents-v1` | 24 h / delete |
| `stock-gap.agent.events.v1` | workers Python | `stock-gap-backend-v1` | 24 h / delete |
| `stock-gap.agent.dead-letter.v1` | composant qui met en quarantaine | `stock-gap-dead-letter-inspector-v1` (inspection locale) | 7 jours / delete |

Trois partitions par topic, réplication 1 pour le nœud local ; ceci n'est pas une configuration haute disponibilité. Pas de compaction : chaque transition compte. Producteurs configurés acks=all et idempotence Kafka ; consommateurs sans auto-commit. Le même group répartit les partitions entre workers, il ne diffuse pas toutes les commandes à chaque worker. Un lecteur de debug utilise un autre group et ne produit aucun effet métier.

La clé est le `missionId` UUID exact. Comparer clé et enveloppe. L'ordre Kafka existe uniquement dans une partition/un topic ; ne pas en déduire l'ordre entre commandes et résultats ni entre missions d'une conversation. Java sérialise cas et conversations.

Le point de vue AsyncAPI est Java : send commandes, receive événements, send quarantaines d'événements. Côté worker les directions commandes/événements sont inversées ; il peut également produire une quarantaine de commande. Le dead-letter inspector ne relance jamais automatiquement un message.

## Enveloppe commune

Tous les messages d'agent contiennent `eventId`, `eventType`, `schemaVersion="1.0.0"`, `occurredAt`, `datasetEpoch`, `correlationId`, `causationId`, `missionId`, `attempt`, `ruleSetVersion`, `evidenceSnapshotId`, `sequence`, `payload`.

- `eventId` : identité de livraison logique, stable pendant les retries de transport.
- `correlationId` : chaîne de l'intention métier ; `causationId` : UUID de commande/événement parent (pour une première commande, UUID de la commande HTTP durable).
- `occurredAt` enveloppe : instant d'émission/événement transport, pas la date des faits lus. Les faits sources conservent `occurredAt` et `recordedAt` séparés.
- `attempt` et `fencingToken` : autorités Java ; le worker ne les incrémente pas. Les événements worker portent leaseToken/fencingToken dans payload. Une commande initiale ne peut pas prétendre posséder un bail déjà accordé.
- `sequence` : monotone à partir de 1 par `(datasetEpoch, missionId, attempt, sens de topic)` ; commandes et résultats ont leurs propres séquences. Ne pas comparer la séquence d'une commande avec celle d'un résultat. La séquence SSE est indépendante.
- `ruleSetVersion` et `evidenceSnapshotId` doivent égaler les valeurs épinglées de la mission, pas les valeurs courantes au moment de réception.

Tous les objets sont fermés : un champ `approve`, un type source utilisé comme eventType d'agent, une version inconnue ou une identité non UUID sont rejetés avant traitement. Le type discriminant sélectionne le payload exact ; il n'existe pas d'enveloppe à payload générique arbitraire.

## Contextes conditionnels

Le champ `payload.contextKind` rend le contexte explicite sans ambiguïté :

| contextKind | Obligatoire dans l'enveloppe | Interdit |
|---|---|---|
| INVESTIGATION | caseId, analysisId | proposalId, proposalRevision |
| RULE_CASE | caseId, analysisId, proposalId, proposalRevision | — |
| RULE_HISTORY | proposalId, proposalRevision | caseId, analysisId |

`conversationId` et `turnId` sont toujours présents ensemble ou absents ensemble ; obligatoires pour `ExpertTurnRequested` et `TextChunkProduced`. Les révisions sans nouveau cas utilisent RULE_HISTORY avec originDiagnosticId/originEvidenceSnapshotId dans la commande, non un faux cas courant.

Un proposalId dans une commande de synthèse est une **identité de candidat réservée** avec révision cible, pas la preuve qu'un brouillon existe. Si `RuleChangeNotProposed` termine la mission, aucun faux objet Proposal ou DMN n'est créé pour remplir cette identité. L'interface montre le résultat de mission, pas une proposition vide. Les références proposition ne deviennent consultables en tant que candidat qu'après adoption d'un changement complet. Pour une révision, le contexte désigne la révision cible autorisée ; Java compare également la révision de base enregistrée dans la mission.

## Types, payload et fin de traitement

| Type | Sens | Contexte | Effet métier après validation Java |
|---|---|---|---|
| InvestigationRequested | Java → worker | INVESTIGATION | réclamer mission investigateur ; pas de résultat immédiat |
| RuleSynthesisRequested | Java → worker | RULE_CASE | synthèse depuis diagnostic fallback accepté et dossier de preuves figé d'origine |
| RuleRevisionRequested | Java → worker | RULE_HISTORY | révision à partir de la règle/version et origine historique |
| ExpertTurnRequested | Java → worker | l'un des trois, rôle cohérent | un seul tour lié à la conversation |
| CancellationRequested | Java → worker | contexte de la mission | signal d'arrêt best effort ; Java a déjà adopté la décision |
| MissionStarted | worker → Java | contexte de mission | progression, aucun diagnostic/proposition |
| ToolObservationRecorded | worker → Java | contexte de mission | observation d'outil bornée, références de preuves, pas dump de sources ou secret |
| TextChunkProduced | worker → Java | contexte + conversation/turn | texte regroupé ; chunkIndex, isFinal explicites |
| DiagnosticProposed | worker → Java | INVESTIGATION | diagnostic complet, mission d'investigation terminée si adopté |
| RuleChangeProposed | worker → Java | RULE_CASE ou RULE_HISTORY | changement CREATE/REVISE/RETIRE complet ; mission terminée, candidat non actif |
| RuleChangeNotProposed | worker → Java | RULE_CASE ou RULE_HISTORY | réussite métier terminale sans candidat : reason NO_CHANGE, INSUFFICIENT_EVIDENCE ou UNSUPPORTED_CAPABILITY, explanation, missingFacts et origine |
| MissionFailed | worker → Java | contexte de mission | fin de tentative technique ; politique Java décide retry ou FAILED terminal |
| MissionCancelled | worker → Java | contexte de mission | confirmation/audit d'arrêt ; aucun droit de réactiver une tentative fenced |

`RuleChangeNotProposed` n'est pas une erreur de fournisseur et ne déclenche pas un retry automatique pour « forcer » une règle. Une discussion humaine ultérieure peut créer une nouvelle mission avec contexte enrichi. Les limitations d'une proposition sont exposées ; `formalizable` n'autorise aucune publication.

### Finalisation du chat

Pour une mission issue de `ExpertTurnRequested`, `TextChunkProduced.isFinal=true` termine le **tour textuel** sans exiger de proposition ni de diagnostic artificiel. `isFinal=false` ne termine rien. Si ce tour produit aussi un résultat structuré, ce résultat doit être envoyé et adopté avant le chunk final ; il n'est plus recevable après clôture du tour. Un résultat structuré peut être enregistré comme résultat du tour mais le tour garde son statut actif jusqu'au marqueur final. Pour une mission de synthèse/investigation distincte, un chunk final ne termine que le message textuel : seule l'issue structurée attendue termine cette mission. Java sait quel type de commande a créé la mission, il ne déduit pas cette distinction d'un texte libre.

ChunkIndex commence à zéro par messageId/attempt. Déduplication par eventId et par `(messageId, attempt, chunkIndex)` ; mêmes coordonnées et contenu différent → conflit audité/quarantaine, jamais concaténation silencieuse. Le backend transmet à Vue un texte complet versionné plutôt qu'un delta pouvant être appliqué deux fois. Annulation/échec avant le final → message INTERRUPTED. Ne pas exposer de raisonnement interne privé du modèle.

## Ack n'est pas adoption

### Commandes côté worker

- Valider le schéma puis réclamer auprès de Java avant tout appel externe.
- `CLAIMED` : l'ack de commande est autorisé après la prise durable Java. Le travail n'est pas seulement en mémoire : bail et ordonnanceur Java assurent sa reprise. Ne pas maintenir un offset non commité pendant un appel long au risque de rebalance.
- `TERMINAL`, `STALE_EPOCH`, `STALE_ATTEMPT`, `BUSY` : ack sans appel LLM. BUSY est sûr uniquement parce que Java conserve durablement le bail et planifie sa récupération. Ne pas créer une nouvelle tentative côté worker.
- Échec réseau de claim sans certitude : retry même clé ; pas d'ack tant que la décision n'est pas connue.
- `CancellationRequested` : ack après consultation de l'état durable/arrêt local demandé, pas après une prétendue interruption certaine du fournisseur.

### Résultats côté Java

Dans une transaction, enregistrer l'inbox `(datasetEpoch,eventId)` et la décision d'adoption. Sous contrôle de mission : vérifier état, epoch, tentative, fence, séquence, références autorisées, révision, rôle et quotas. Écrire effets, état terminal éventuel, SSE et outbox nécessaires puis commit. **Commit offset Kafka seulement après ce commit.**

- Doublon exact déjà traité : ack, sans nouveau résultat ni validation/publication.
- Résultat valide terminal adopté : ack après commit de l'issue terminale.
- Mission déjà terminale, annulée, ancienne tentative/epoch : persister décision LATE/IGNORED puis ack. Ne pas rejouer éternellement un message qui ne deviendra jamais applicable.
- Ancien fence `MissionCancelled` : audit uniquement, ack ; la cancellation métier fait autorité.
- Retard ou incohérence de séquence : ne pas avancer l'état aveuglément. Conserver une décision d'attente bornée ou mettre en quarantaine ; ne pas acquitter comme adopté un résultat dont un prédécesseur nécessaire manque. Un trou de progression non nécessaire peut être audité sans bloquer une issue finale complète si Java prouve sa validité ; cette politique doit être testée.
- Une erreur transitoire DB avant commit : pas d'ack ; retry. Un échec métier permanent de validation de proposition est un résultat durable non publiable, pas une boucle de redelivery.

Une transaction Kafka n'englobe pas PostgreSQL. Outbox/inbox rendent l'effet métier idempotent, pas la livraison ou l'inférence exactement unique.

## Reprises, poison et dead-letter

Deux compteurs distincts : `attempt` (nouvelle exécution métier, maximum du budget) et `deliveryCount` (relecture technique d'un même eventId). Valeurs proposées : 3 tentatives mission maximum, backoff technique 1 s / 5 s / 30 s avec jitter ; pas d'attente au-delà de deadline/budget. Un fournisseur non disponible peut autoriser une nouvelle tentative ; un output structurel invalide ne devient pas valide grâce à une redelivery identique.

Après retries techniques bornés ou schéma/version invalide : écrire d'abord une quarantaine durable et une notification dead-letter via outbox, puis ack du message original. Si cette écriture échoue, ne pas ack. Notification dead-letter fermée : provenance topic/partition/offset, eventId si connu, missionId, epoch, raison, deliveryCount, empreinte SHA-256, résumé nettoyé et quarantineId. Pas de payload brut libre, clé, oracle ou stacktrace sur ce topic. Les octets originaux, si nécessaires, restent dans un stockage local quarantiné à accès technique restreint et rétention bornée, pas accessibles au worker.

Si JSON est illisible mais la clé Kafka missionId et la mission durable sont identifiables, utiliser cette identité vérifiée pour le dead-letter. Si même l'identité de mission est invalide/inconnue, enregistrer une quarantaine technique locale par topic/partition/offset sans inventer un missionId ; l'alerte locale décrit ce cas. Ne pas publier un message hors schéma dans le topic dead-letter pour contourner sa clé normative.

Une réexécution depuis quarantaine est une décision locale explicite : revérifier epoch, état, quota et compatibilité ; créer une **nouvelle** commande/attempt via Java. Ne pas pousser le JSON d'un dead-letter directement dans le topic commandes.

## Reset et conservation

Le reset est un outil local hors API : arrêter producteurs/workers, invalider les baux, renouveler datasetEpoch dans la transaction d'import, recréer/purger les topics et les volumes du POC selon la procédure autorisée, réinitialiser les offsets dédiés, puis redémarrer. Même si un ancien message survit à une purge partielle, le contrôle d'epoch l'empêche d'être adopté. Conserver un petit audit technique de rejet d'anciennes epochs sans réintroduire leurs données dans l'état métier courant. Ne jamais faire confiance au seul nom de topic ou à un timestamp pour identifier le jeu chargé.

Les événements source du runtime (`LoanOpened`, `LoanClosed`, `ReturnReceived`, `ReceiptReceived`, `TransferDispatched`, `TransferReceived`, `RepairStarted`, `IntegrationProcessed`) sont lus par les outils dossier de preuves figé ; ils ne deviennent pas des commandes ou résultats de ces topics.
