# HTTP, outils de mission et invariants métier

## Frontières de confiance malgré l'absence de login

`/api/**` est la surface **Admin locale**, servie exclusivement par le proxy lié à loopback. Pas de persona supplémentaire ni de fournisseur d'identité. `security: []` dans OpenAPI exprime l'absence de login applicatif, **pas** un droit accordé au worker.

`/internal/**` est une surface distincte, accessible uniquement sur le réseau privé du worker, avec identité technique `WorkerToken` (bearer opaque, jamais une clé OpenAI). Le routage/listener interne doit refuser `/api/**` indépendamment des en-têtes présentés. Le listener Admin ne doit pas être atteignable depuis le conteneur worker. Ne pas implémenter cette frontière comme un simple booléen `isAdmin` reçu du client ou comme une vérification du header Host. Un jeton worker sur une route Admin donne `403`, jamais une promotion de rôle. CORS n'est pas une barrière réseau. Sur la surface locale, contrôler Origin/Host et SameSite/CSRF pour les commandes et ne pas exposer le service à un LAN non fiable.

| Action | Admin local | Worker investigateur | Worker agent de règles |
|---|---|---|---|
| Lire l'interface métier | oui | non via `/api` | non via `/api` |
| Réclamer/renouveler sa mission | non | oui si mission assignée | oui si mission assignée |
| Lire les preuves de sa mission | oui via dossier | oui, outils autorisés | oui, origine et dossier de preuves figé autorisés |
| Prévalider un brouillon DMN | non directement | non | oui si capacité PREVALIDATE |
| Proposer CREATE/REVISE/RETIRE | non | non | résultat Kafka structuré |
| Accepter un diagnostic | oui | non | non |
| Approuver/publier | oui | **jamais** | **jamais** |
| Retirer directement une règle | oui après aperçu exact | non | non |
| Créer/modifier manuellement une règle | **non** | non | non hors proposition agent |
| Lire oracle, secrets, SQL libre, shell, corriger stock | non via ces API | **non** | **non** |

Les notes opérateur et messages humains sont des données. Leur contenu ne modifie jamais les droits, le dossier de preuves figé ou la liste d'outils. Les prompts peuvent nommer un outil, mais seul Java autorise son exécution effective.

## Requêtes et idempotence

Toutes les commandes POST portent `Idempotency-Key` UUID. Les commandes Admin portent aussi `X-Dataset-Epoch`. Les corps spécifient les versions attendues des ressources modifiées ; les dossiers de preuves figés/référentiels épinglés ne changent pas pendant une analyse. Un `202` signifie création durable du travail, **jamais** diagnostic terminé, test réussi ou règle active.

Stocker l'idempotence par `(datasetEpoch, principal technique/local, méthode, chemin canonique, Idempotency-Key)` avec empreinte du corps JSON normalisé et résultat HTTP. Normalisation explicitement versionnée : clés d'objet triées, UTF-8, aucun espace structurel, chaînes inchangées et nombres entiers (les schémas n'exposent pas de flottants métier). Même clé + même corps → même identifiant/résultat, header `Idempotency-Replayed: true`. Même clé + autre corps → `409 IDEMPOTENCY_CONFLICT` sans effet. Un conflit de version n'est pas contourné en réutilisant la clé avec un nouveau corps : relire puis créer une nouvelle commande.

L'enregistrement de la commande, ses mutations, son outbox et le résultat idempotent forment une transaction. Pour une commande longue, la transaction enregistre l'acceptation, pas sa réussite future. Retenir les clés pendant toute l'epoch du POC ; un reset archive l'epoch, ne permet pas la réutilisation implicite d'un ancien résultat. Pour les lectures-outils GET, `X-Call-Authorization` et le couple callId/empreinte de requête jouent le rôle de déduplication de consommation du quota.

`claim` et `authorize-call` ne rendent jamais une permission éternelle : même si une réponse idempotente est rejouée, le serveur revérifie l'expiration et le fence à chaque outil. Le worker doit vérifier heartbeat/expiration avant un nouvel appel fournisseur. Une erreur réseau après acceptation est résolue par le même identifiant, pas en lançant un second travail à l'aveugle.

## Workflow et publication atomique

- Un seul run actif par cas, vérifié sous verrou/contrainte transactionnelle. Un lot est borné à 20 cas, sans doublon ; une erreur d'un élément est conservée dans son item et n'efface pas les autres résultats. Les appels partagent les quotas définis par Java.
- Le diagnostic est accepté/rejeté indépendamment d'une règle. Une acceptation fallback peut créer une mission de synthèse et son outbox dans la même transaction. Le déterministe ne crée pas automatiquement une règle. `NOT_APPLICABLE` ou `NON_FORMALIZABLE` explique l'absence de synthèse ; après synthèse, `RuleChangeNotProposed` est une réussite métier sans candidat.
- Java crée et retourne les conversations du dossier. Une conversation possède au plus un tour actif. `expectedConversationVersion` garantit la sérialisation ; changer d'onglet ne crée pas deux tours concurrents.
- Réviser une règle sans nouveau cas exige un diagnostic d'origine et son dossier de preuves figé autorisés, **sans inventer un caseId courant**. Le retrait agent est une proposition soumise au même sas que CREATE/REVISE.
- Une prévalidation d'outil vérifie un brouillon de façon bornée mais renvoie toujours `publishable: false`. Elle n'est ni un rapport de replay complet ni une autorisation de publication.
- Les validations finales portent sur un triplet immuable `(proposalId, proposalRevision, candidateSha256)`, une version de référentiel, un corpus/empreinte et une version de moteur. Les positifs, négatifs, inconnus, conflits et pertes de couverture sont exposés sans injecter le label oracle dans le worker.
- L'approbation transmet exactement `expectedProposalRevision`, `expectedProposalVersion`, `expectedRuleSetVersion`, `reportId`, `candidateSha256` et la confirmation explicite. Le rapport doit être PASSED/publishable, appartenir au même triplet et au référentiel attendu ; sinon `409` si périmé ou `422` si invalide. L'Admin ne peut pas joindre de DMN/draft : `additionalProperties: false` le refuse.
- Préparer le runtime KIE avant transaction ; parser XML sans entités externes, analyser l'AST FEEL, refuser imports/Java/extensions/fonctions externes ou non déterministes. La compilation seule n'est pas un contrôle de sécurité.
- Sous verrou du pointeur de référentiel : revérifier toutes les versions et empreintes, enregistrer nouvelles versions immuables, publication, approbation, pointeur actif, état de proposition, invalidation des anciennes validations et log SSE, puis commit unique. En cas d'échec, **aucune activation partielle**. Le runtime préparé doit être prêt à être référencé au commit ; toute analyse consulte le pointeur PostgreSQL, pas un cache pouvant rester à l'ancienne version.
- Cache mémoire indexé par publication, reconstruction au redémarrage ; si impossible, `503`, jamais fallback silencieux vers un autre référentiel. Une analyse déjà commencée conserve sa version et ses preuves.
- Le retrait Admin exige un `previewId` et `reportId` calculés pour la règle/version/référentiel exacts, non expirés. Il crée immédiatement une nouvelle publication atomique avec perte de couverture visible. `proposalId/proposalRevision` sont null pour ce retrait direct ; ils sont non nuls pour un changement issu d'agent. Java impose cette cohérence interchamps. Aucun historique n'est écrasé.

## Outils : noms de prompts et operationId

Tous les noms ci-dessous sont des `operationId` exacts. Les identités de mission/dossier de preuves figé et headers de contrôle sont injectés par l'adaptateur **hors du texte du modèle**.

| Nom sémantique | Ressource | Capacité authorize-call |
|---|---|---|
| `get_case_snapshot` | `/internal/missions/{missionId}/snapshots/{evidenceSnapshotId}` | READ_SNAPSHOT |
| `get_source_events` | même préfixe + `/source-events` ; eventType/date/page bornés | READ_TIMELINE |
| `get_operator_notes` | même préfixe + `/operator-notes` | READ_NOTES |
| `get_rule_set` | `/internal/missions/{missionId}/rule-set` ; version épinglée côté Java | READ_RULES |
| `get_rule_history` | `/internal/missions/{missionId}/rules/{ruleId}/history` | READ_RULES |
| `prevalidate_rule_change` | `/internal/missions/{missionId}/prevalidation` | PREVALIDATE |
| `get_loans`, `get_returns`, `get_receipts`, `get_transfers`, `get_repairs`, `get_integrations` | sous le préfixe dossier de preuves figé | READ_LOANS, READ_RETURNS, READ_RECEIPTS, READ_TRANSFERS, READ_REPAIRS, READ_INTEGRATIONS |
| `get_publication_history` | publications permises à la mission | READ_PUBLICATIONS |
| `get_origin_diagnostic` | diagnostic d'origine explicitement autorisé | READ_DIAGNOSTIC |
| `get_mission_conversation` | messages de la conversation liée seulement | READ_CONVERSATION |
| `get_mission_proposal` | proposition/révision liée seulement | READ_PROPOSAL |

Le périmètre ne vient jamais d'un copyId libre envoyé par le modèle. Java compare le dossier de preuves figé de l'URL au dossier de preuves figé enregistré pour la mission. Chaque page et chaque source est une nouvelle invocation autorisée ; aucune continuation ne permet d'échapper au dossier de preuves figé. Pas de query DSL ni de filtre par famille cachée. Les endpoints spécialisés source-events ne retournent que les eventTypes pertinents (LoanOpened/LoanClosed pour loans, etc.). Les plages temporelles restent dans les bornes du dossier de preuves figé.

## Claim, heartbeat, quotas et fence

1. À réception d'une commande, `claim_mission` vérifie epoch, commandEventId, rôle, mission et tentative exacts. Réponse `CLAIMED` : bail de 15 à 60 s, leaseToken UUID et fencingToken monotone serveur. `BUSY`, `TERMINAL`, `STALE_EPOCH`, `STALE_ATTEMPT` ne donnent aucun jeton ni droit d'appel ; le worker n'exécute pas la mission.
2. Heartbeat toutes les 10 s ; il ne fait aucun appel LLM et ne consomme pas de quota outil. Renouvellement conditionnel du même bail, jamais résurrection d'un bail expiré. `cancelRequested` est vérifié avant les prochains outils/appels.
3. `authorize_call` est obligatoire avant **chaque** appel LLM ou outil, retries compris. Réserver atomiquement un callId, une capacité, le budget maximal de tokens et l'appel dans les limites par mission + limites partagées (lot/dataset). TTL d'autorisation proposé 10 s, toujours inférieur au bail restant. Une même autorisation ne couvre qu'un appel. Pour TOOL, nom exact et budgets tokens à zéro. Pour LLM, aucun nom d'outil.
4. Pour les outils, envoyer `X-Dataset-Epoch`, `X-Attempt`, `X-Lease-Token`, `X-Fencing-Token`, `X-Call-Authorization`, plus le bearer worker. L'autorisation doit être liée à l'empreinte canonique de la première invocation ; un autre corps/URL avec le même jeton est refusé. Java consomme la réservation atomiquement avec la lecture/prévalidation.
5. `complete_call` enregistre les compteurs réels ou UNKNOWN. Les réservations inconnues restent pessimistes : ne pas libérer artificiellement du budget après un crash. Les limites configurées sont positives mais les budgets restants peuvent être zéro. Aucun compteur monétaire.
6. Bail perdu : worker cesse les appels ; ordonnanceur Java décide de la tentative suivante sous quota, incrémente attempt et fence, crée une nouvelle commande. Tout résultat de l'ancien fence est audité comme tardif et non appliqué.
7. Annulation : Java interdit atomiquement tout nouveau résultat/appel, incrémente fence et invalide les autorisations. La mission peut être signalée CANCEL_REQUESTED puis finalisée CANCELLED sans attendre Python ; l'arrêt réseau courant est best effort. `MissionCancelled` de l'ancien fence est un accusé audité, pas une exception permettant de réactiver la mission. Aucune publication n'en découle.

Pas d'exactly-once fournisseur : un crash après appel externe peut laisser sa facturation inconnue. La déduplication métier ne rend pas cet appel réversible ou exactement unique.

## Matrice HTTP et action du client

Toutes les erreurs utilisent `application/problem+json`, `Problem`, correlationId UUID et `violations` typées. Pas de stacktrace, token, clé OpenAI, SQL ou note privée non autorisée.

| HTTP | code(s) | Cas typique | Effet/reprise |
|---|---|---|---|
| 400 | BAD_REQUEST | JSON invalide, UUID/date/cursor mal formé, paramètre requis manquant | aucun effet ; corriger l'entrée |
| 401 | UNAUTHORIZED | bearer worker absent/invalide | aucun appel ; corriger la configuration technique sans exposer de secret |
| 403 | FORBIDDEN | worker vers Admin, outil non autorisé, dossier de preuves figé hors mission, tentative d'oracle | aucun effet ; pas de retry automatique |
| 404 | NOT_FOUND | ressource absente dans le périmètre visible | ne pas révéler les identités hors périmètre |
| 409 | VERSION_CONFLICT | proposition/référentiel/cas/conversation changé | relire, informer l'Admin, nouvelle commande si souhaitée |
| 409 | STATE_CONFLICT | analyse déjà active, tour actif, publication non prête, curseur futur | relire l'état ; ne pas contourner avec une autre clé |
| 409 | IDEMPOTENCY_CONFLICT | clé identique, corps différent | erreur client ; aucune deuxième exécution |
| 409 | FENCED | tentative/leaseToken/fence dépassés | arrêter le worker courant, aucun retry fournisseur |
| 409 | EPOCH_MISMATCH | reset intervenu | abandonner le travail et resynchroniser |
| 410 | LEASE_EXPIRED | heartbeat/outil après expiration | arrêter ; Java seul peut réattribuer |
| 410 | CURSOR_EXPIRED | historique SSE purgé | refaire dossier de preuves figé ; ne pas sauter la plage perdue |
| 422 | INVALID_COMMAND | origine de création non acceptée/fallback, lot dupliqué, valeurs métier incohérentes | aucun effet ; expliquer les violations |
| 422 | VALIDATION_FAILED | DMN dangereux/non compilable, tests en échec, rapport non publiable | ne pas activer ; discuter/réviser par agent |
| 429 | QUOTA_EXCEEDED | limite durable mission/lot/dataset | retryable=false sans nouveau budget ; pas de boucle |
| 429 | RATE_LIMITED | débit temporairement limité | retryable=true + Retry-After si reprise possible dans le budget |
| 500 | INTERNAL_ERROR | panne inattendue | état indéterminé côté réseau : relire/rejouer la même clé ; jamais supposer l'absence de commit |
| 503 | DEPENDENCY_UNAVAILABLE | DB/Kafka requis pour acceptation, runtime KIE indisponible | même clé, backoff borné ; `Retry-After` si connu |

La disponibilité Kafka n'empêche pas nécessairement d'accepter une commande si l'outbox durable est disponible : `202` peut rester correct, avec état d'attente visible. `503` signifie que les garanties d'acceptation ne peuvent pas être tenues. Les réponses `200` de cancellation confirment l'état durable retourné, pas l'arrêt physique instantané du fournisseur. Les schémas déclarent un catalogue commun d'erreurs ; seules les lignes pertinentes pour l'opération sont émises.

## Obligations à tester au runtime

JSON Schema vérifie forme et valeurs ; Java vérifie appartenance des UUID, égalité de dossier de preuves figé, origine acceptée, ordre temporel, lien report/candidat/référentiel, hash exact, versions courantes, causalité, permissions, unicité des lots, somme des quotas, limites de corpus et activation atomique. Aucun validateur statique ne prouve ces propriétés à la place de transactions et de tests entre composants.
