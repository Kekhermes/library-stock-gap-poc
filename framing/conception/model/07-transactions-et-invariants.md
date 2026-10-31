# Transactions, invariants et concurrence

## 1. Frontière transactionnelle

Java est le seul arbitre métier. `READ COMMITTED` + verrous de lignes explicites + CAS suffisent aux procédures ci-dessous si **tous** leurs chemins suivent le protocole. Les contrôles de cardinalité/agrégats ne sont pas remplacés par un `SELECT` non verrouillé. Une future implémentation peut choisir `SERIALIZABLE` et rejouer les erreurs `40001` ; cela ne rend pas les effets réseau transactionnels.

Aucun appel LLM, Kafka, compilation lente ou évaluation de corpus n'a lieu sous verrou DB. Les travaux longs produisent des artefacts préparés, puis une transaction courte vérifie de nouveau époque/versions/empreintes avant adoption. Les identités sont générées hors base sans encodage de scénario.

### Ordre de verrouillage commun

1. `active_dataset FOR SHARE` pour une commande courte, `FOR UPDATE` pour fermer l'instance (opérateur habilité seulement).
2. `active_rule_set` : `FOR SHARE` pour figer une version au lancement, `FOR UPDATE` pour publier/retirer.
3. Agrégats concernés dans un ordre global documenté : cas → proposition → conversation → mission ; plusieurs identités de même type triées. Une adoption de résultat qui touche une analyse/cas prend le cas avant la mission, comme l'annulation.
4. Tentative puis créneau global ; ou sélection d'un créneau libre `FOR UPDATE SKIP LOCKED` sous mission, sans jamais verrouiller une autre mission en conservant ce créneau. Le récupérateur d'expiration prend la mission avant sa tentative/créneau.
5. `sse_stream FOR UPDATE` **en dernier**, conservé jusqu'au commit. Toute procédure susceptible de créer un cycle de locks est revue avant ajout.

Les transactions qui n'ont pas besoin d'une publication active (heartbeat, dispatch) n'en prennent pas le verrou. Les verrous de fermeture d'import/manifeste suivent les triggers du DDL. La discipline d'ordre, les timeouts SQL et les retries de deadlock sont applicatifs ; une liste dans un document n'est pas une preuve d'absence de deadlock.

### Idempotence de commande

La clé est `(datasetEpoch, commandId)` et `request_sha256` couvre type + cible + versions attendues + corps. Si la clé existe avec même empreinte, renvoyer exactement la réponse durable ; sinon `409`. Si deux transactions ne voient encore rien, la PK de `command_receipt` tranche au commit : la perdante rollback **tout** son effet, recharge le reçu puis répond. Ne jamais faire d'appel réseau irréversible avant cette résolution. Les contraintes métier (analyse active, acceptation unique, publication/commande unique) constituent des défenses supplémentaires.

`command_receipt` est écrit dans la même transaction que son effet ; pas de `202` durable renvoyé avant commit. Un résultat terminal de mission est distinct de ce reçu de lancement.

## 2. Lancer une analyse et un lot

```text
préparer les identités ; vérifier syntaxe et droits Admin ; calculer hash de commande
BEGIN
  verrou SHARE active_dataset ; vérifier époque attendue et import clôturé
  relire command_receipt ; si présent, vérifier hash puis retour idempotent
  verrou SHARE active_rule_set ; conserver publication P clôturée
  verrou UPDATE gap_case ; vérifier lock_version attendu, aucun run actif
  lire les sources immuables de cette époque et de cet exemplaire
  construire facts + bundle + références ; valider temps, couverture, bornes et hash
  INSERT evidence_snapshot et toutes ses evidence_reference dans la même transaction
  INSERT analysis_run(QUEUED, case, snapshot, P)
  UPDATE gap_case SET status=EN_ANALYSE, lock_version=lock_version+1
  conserver current_acceptance_id précédent ; il n'est pas effacé par une réanalyse
  INSERT command_receipt(202, analysisId)
  verrou sse_stream ; incrémenter last_cursor ; INSERT progress_event
COMMIT ; renvoyer 202
```

Le dossier de preuves figé ne varie plus. Une collecte supplémentaire par l'agent lit uniquement le bundle de ce dossier de preuves figé via les outils autorisés. Une extension réelle du périmètre de preuves nécessite un **nouveau dossier de preuves figé/analyse**, pas une mutation du précédent. Le hash lie aussi l'ensemble des références : pas d'ajout tardif d'une référence à une collection déjà utilisée.

Un job Java durable retrouve les `analysis_run.QUEUED` après redémarrage. Il adopte `DETERMINISTIC_RUNNING` par CAS, compile/reconstruit la publication P en cache jetable, puis évalue **toutes** ses règles. Les traces enregistrent les règles/lignes, preuves, symptômes couverts, contradictions et motif de fallback.

- Couverture suffisante : transaction vérifiant run encore attendu, diagnostic DMN immuable, analyse COMPLETED, cas A_REVOIR, SSE.
- Insuffisance/contradiction : même garde, trace + `fallback_reason`, mission INVESTIGATION bornée, état AGENT_PENDING, outbox `InvestigationRequested` et SSE dans la même transaction.
- Échec technique DMN : FAILED explicite ; ne pas déguiser une erreur de compilation en connaissance insuffisante. Le cas revient A_ANALYSER ; acceptation ancienne conservée.

**Lot :** figer 1 à 5 caseIds sans doublon dans `analysis_batch` + `batch_item` en une transaction. La CHECK de position et la PK bornent SQL à cinq éléments ; l'existence d'au moins un item et l'absence d'ajout après lancement sont des gardes Java. Chaque enfant suit ensuite le lancement ci-dessus. Un cas déjà actif devient CONFLICT sans faire échouer les autres. Annuler le lot marque/annule les enfants non terminés seulement ; COMPLETED/FAILED restent consultables et le résultat de chaque cas est conservé.

## 3. Claim, leases et budget

### Claim d'une tentative

```text
BEGIN
  verrou SHARE active_dataset ; refuser ancienne époque
  verrou agrégat parent si transition nécessaire, puis mission FOR UPDATE
  vérifier mission non terminale, non annulée, contexte/snapshot/publication autorisés
  si tentative RUNNING + lease encore valide : retourner ALREADY_CLAIMED, aucun appel
  si lease expiré : terminaliser tentative EXPIRED ; clôturer réservations incertaines UNKNOWN
                      libérer uniquement son créneau (missionId,attempt exacts)
  vérifier current_attempt < 2 et échéance globale non atteinte
  vérifier budget résiduel cumulé ; un retry ne réinitialise aucune consommation
  prendre un slot libre FOR UPDATE SKIP LOCKED ; si aucun : rollback/attente sans appeler LLM
  n = current_attempt + 1 ; générer lease_token neuf
  si premier claim : started_at=maintenant ; deadline_at=maintenant+durée autorisée
  INSERT mission_attempt(n, RUNNING, token, heartbeat, lease_expires_at<=deadline)
  UPDATE mission current_attempt=n, status=RUNNING
  UPDATE slot SET mission_id,attempt=n
  notifier état adopté, avec SSE si nécessaire
COMMIT ; retourner n, token, deadline, quotas restants, contexte autorisé
```

La mission automatique est bornée à 300 s, 8 appels LLM, 24 outils, 64 000 tokens d'entrée et 12 000 de sortie. Chat : 120 s et 4 appels LLM, autres plafonds inchangés. Ce sont les valeurs maximales configurées dans le DDL ; les budgets choisis peuvent être plus faibles. Un seul retry technique est autorisé, dans la même mission. Pas de retry automatique d'une proposition métier invalide pour masquer l'échec.

`heartbeat`/outil/résultat doivent présenter `(datasetEpoch, missionId, attempt, leaseToken)` ; Java vérifie le token actuel, la tentative RUNNING, le lease et la deadline avec l'heure serveur. Un heartbeat arrivé après expiration ne ressuscite pas la tentative, même si aucun repreneur n'a encore claim. Le nouveau lease est borné par la deadline initiale. `attempt` constitue un fence durable ; un vieux token ne peut pas libérer le créneau d'une nouvelle tentative.

### Réservation avant chaque appel

```text
BEGIN ; mission FOR UPDATE ; tentative courante vérifiée avec token/époque/échéance
  si reservationId déjà présent : résultat idempotent ; aucun nouvel appel autorisé
  sommer toutes les tentatives de la mission :
    nombre appels = RESERVED + SETTLED + UNKNOWN, hors VOID prouvé avant envoi
    tokens imputés = actual si connus, sinon reserved ; UNKNOWN conserve sa réserve
  refuser si appels+1 ou réserve entrée/sortie dépasserait le budget de mission
  INSERT call_reservation(RESERVED, estimation entrée, max sortie réellement transmis)
COMMIT
appel externe seulement après autorisation ; restituer mesures fournisseur ou UNKNOWN
BEGIN ; mission verrouillée ; règlement idempotent de la réservation
  SETTLED : conserver les compteurs réels même s'ils dépassent l'estimation
  si budget épuisé/dépassé : empêcher l'appel suivant et terminer FAILED/limite explicite
COMMIT
```

Ne pas libérer une réserve parce que le réseau a expiré : l'appel a peut-être eu lieu. `VOID` n'est permis que si l'absence d'envoi est prouvée. Les tokens de raisonnement sont inclus quand rapportés ; à défaut, garder l'incertitude visible. Le SQL accepte des compteurs réels supérieurs aux estimations afin de ne pas perdre cette vérité. Le plafond d'inférence/facturation exact n'est pas garanti ; aucun montant financier ici.

Deux créneaux garantissent **au plus deux missions logiquement autorisées** par époque via ce protocole. Un ancien appel réseau ne peut pas toujours être tué lors d'une expiration : il peut physiquement se terminer pendant une reprise. Ne pas annoncer une limite physique absolue de deux inférences externes en cas de panne/partition. Pour une limite physique stricte, attendre une confirmation d'arrêt ou refuser la reprise jusqu'à la borne réseau, au prix de disponibilité ; l'effet métier reste protégé par fencing.

## 4. Réception, ordre et annulation

Pour chaque message Kafka : valider taille/JSON Schema/type/enveloppe avant transaction ; ne pas imprimer de secret sur rejet. L'inbox stocke aussi une époque inconnue : elle n'a donc volontairement pas de FK vers mission/époque. Un message totalement malformé n'entre pas dans cette table typée ; il est rejeté vers la dead-letter expurgée du contrat, sans effet métier.

```text
BEGIN
  verrou SHARE époque active ; puis agrégat parent et mission/tentative dans l'ordre commun
  si eventId connu : comparer hash ; même hash = noop, différent = rejet/audit, jamais effet
  si époque/tentative/lease obsolète, mission annulée/terminale : INSERT inbox LATE/REJECTED
  sinon vérifier sequence par tentative :
    sequence == last_sequence+1 : valider le résultat et toutes ses références/empreintes
       INSERT inbox APPLIED ; transition métier et outbox éventuelle ; avancer last_sequence
    sequence > last_sequence+1 : INSERT inbox BUFFERED ; pas d'effet métier anticipé
    sequence <= last_sequence : INSERT inbox DUPLICATE/REJECTED ; pas de second effet
  regrouper les chunks ; persister seulement les synthèses/propositions validées
  émettre SSE uniquement pour changements effectivement adoptés
COMMIT ; ACK Kafka après commit
```

Un drain reprend BUFFERED en ordre après l'adoption du trou manquant. Borne de buffer, timeout et erreur explicite en cas de trou durable ; ne pas attendre sans limite. La contrainte partielle d'inbox empêche deux événements APPLIED pour une même séquence/tentative mais ne garantit pas à elle seule l'ordre contigu. L'immutabilité de `eventId/payload_sha256/envelope` après réception et les transitions BUFFERED→terminal sont applicatives.

**Annulation :** CAS mission/analyse/tour vers CANCEL_REQUESTED, marqueur lisible aux outils, outbox CancellationRequested, SSE. Les queued peuvent devenir CANCELLED immédiatement. La réponse de l'appel courant peut être journalisée tardive mais n'est plus adoptée comme réussite après le point d'annulation. Une seule transition terminale gagne. Libérer uniquement les réservations prouvées non envoyées et le créneau de la tentative concernée ; les preuves déjà collectées restent accessibles. Aucun diagnostic négatif n'est inventé.

**Outbox :** sélection des lignes disponibles via `FOR UPDATE SKIP LOCKED`, token/lease de dispatch, commit ; publication Kafka hors transaction avec clé missionId, puis marquage `published_at` uniquement si token toujours courant. Crash après ack Kafka avant marquage : republier le même eventId. L'inbox rend l'effet idempotent, pas l'inférence OpenAI exactly-once. Le dispatcher vérifie l'époque active et n'envoie pas une vieille commande après reset.

## 5. Accepter un diagnostic

```text
BEGIN
  époque courante vérifiée ; reçu d'idempotence vérifié
  verrou cas ; vérifier lock_version et statut A_REVOIR
  charger diagnostic exact + analyse ; vérifier COMPLETED et appartenance au cas
  vérifier que cette révision est celle proposée à la revue, pas une ancienne réponse
  INSERT diagnostic_acceptance(Admin, diagnosticId exact, caseId, commandId)
  UPDATE gap_case SET current_acceptance_id=acte, status=DIAGNOSTIC_ACCEPTE, version++
  si diagnostic issu du fallback investigateur, étayé suffisamment pour une règle :
    vérifier motif de fallback et références d'origine, absence de contradiction bloquante
    INSERT mission RULE_SYNTHESIS(snapshot d'origine, publication de base figée, budgets)
    INSERT outbox RuleSynthesisRequested
  sinon conserver acceptation sans synthèse automatique
  INSERT command_receipt ; SSE
COMMIT
```

Un INDETERMINATE peut être accepté comme constat d'insuffisance **sans création de règle de cause**. Un diagnostic DMN déjà couvert n'est pas une origine automatique d'acquisition. Pour une conclusion PARTIAL, seule la partie explicitement étayée, avec périmètre et symptômes précis, peut être proposée à la synthèse après contrôle ; accepter l'ensemble ne transforme pas toutes les hypothèses en faits. Les codes de cause métier ne sont pas les labels d'un oracle.

La réanalyse conserve les acceptations précédentes ; la FK composite garantit que le pointeur courant appartient au même cas. L'appartenance à la bonne analyse/révision de revue, son état et l'éligibilité à la synthèse sont des invariants Java.

## 6. Proposer, réviser et valider

Un résultat `RuleChangeProposed` est adopté seulement d'une mission RULE_SYNTHESIS/RULE_REVISION autorisée (ou du rôle agent de règles d'un tour explicitement autorisé par le contrat). Java ne crée pas des conditions FEEL « à la place » de l'agent.

Sous verrou proposition : insérer `proposal_revision N+1` + toutes ses opérations en une transaction, avec mission d'auteur, dossier de preuves figé d'origine, diagnostic d'origine si création, publication de base et hash canonique complet. Avancer `current_revision`, état DRAFT, version++. L'ancienne révision et ses rapports restent intacts. Une révision issue de la règle R001 peut n'avoir aucun diagnostic de création bootstrap ; elle doit néanmoins désigner explicitement ses preuves de contexte, jamais inventer un diagnostic accepté. Les nouvelles règles exigent un diagnostic accepté issu du fallback.

CREATE : UUID de règle neuf, version 1, origine valide. REVISE : règle existante, version attendue membre du référentiel de base, version nouvelle monotone non déjà utilisée, provenance du diagnostic/règle d'origine. RETIRE : règle existante et version attendue ; retrait coordonné justifié. Une opération par ruleId dans une révision ; créer puis retirer la même règle dans la même proposition est rejeté.

Préparer hors transaction : schéma du message, contrôles de références, parsing/compilation DMN, AST FEEL autorisé, limites, tests agent identifiés, corpus indépendant, replay avant/après, contradictions et impact. Le rapport porte proposalId/revision + hash + base + manifeste candidat + corpus + moteur + schéma des faits + version validateur. Un rapport incomplet n'est pas PASS. Aucun holdout n'est exposé pour entraîner/corriger l'agent.

À l'adoption du rapport : verrou proposition, vérifier révision/hash/base toujours courants ; insérer rapport immuable. READY_FOR_APPROVAL seulement pour PASS avec zéro blocage ; sinon CHANGES_REQUIRED. Une nouvelle révision **n'efface pas** l'ancien rapport mais le rend inutilisable pour publication de cette nouvelle révision. Le statut seul ne suffit jamais à publier.

## 7. Approuver et publier

La requête Admin contient commandId, proposalId, proposalRevision, reportId, proposalHash, expectedRuleSetVersion et version optimiste attendue. L'expert peut accepter des avertissements expliqués, pas contourner un blocage technique.

```text
avant transaction : préparer/charger le runtime candidat vérifié par hash ; pas l'activer
BEGIN
  époque active vérifiée ; reçu d'idempotence
  verrou UPDATE active_rule_set ; vérifier version P attendue
  verrou UPDATE proposition ; vérifier READY_FOR_APPROVAL, révision/hash/version exacts
  charger rapport exact via FK ; vérifier PASS, zéro blocage, même révision/hash/base P
  vérifier corpus/validateur/moteur/factSchema autorisés et préparation complète
  revalider provenance agent des CREATE/REVISE ; aucun contenu manuel Admin accepté
  recalculer manifeste complet à partir de P + toutes les opérations
  vérifier versions de cible, origines, hashes DMN, unicité et absence de conflit
  INSERT nouvelles identités rule si CREATE ; INSERT rule_version immuables
  V = P+1 (sous verrou pointeur, jamais SELECT MAX(version)+1 non verrouillé)
  INSERT rule_set_publication(V, parent=P, AGENT_CHANGE, proposition/révision/rapport exacts,
                              Admin, commandId, hash manifeste, sealed_at=NULL)
  INSERT tous les rule_set_member de V ; comparer ensemble et hash au candidat préparé
  UPDATE publication V SET sealed_at=maintenant  -- unique modification permise
  UPDATE active_rule_set SET version=V, lock_version=lock_version+1
  UPDATE proposition SET status=PUBLISHED, lock_version=lock_version+1
  marquer STALE les autres propositions basées sur P, sans altérer leurs contenus
  INSERT command_receipt(200, publication V) ; SSE
COMMIT
lire exactement le pointeur/manifeste confirmé ; renvoyer V active
```

Les trois actions **approbation + publication complète + pointeur actif** sont un seul commit. Aucune approbation réutilisable n'est stockée en attente d'un contenu modifiable. Les triggers refusent UPDATE/DELETE d'une publication clôturée, UPDATE/DELETE de ses membres et insertion tardive de nouveaux membres ; la clôture et une insertion concurrente se sérialisent sur la publication. Le pointeur ne peut cibler un manifeste non clôturé. La complétude du manifeste et sa conformité au rapport restent vérifiées par Java.

Après commit, une analyse suivante lit V en base. Une analyse déjà lancée conserve P, même après retrait. Si l'installation du cache échoue après commit, bloquer les nouvelles analyses avec erreur visible jusqu'à reconstruction de **V** ; ne pas servir silencieusement P en prétendant V active. Les références aux anciennes publications restent valides.

## 8. Retirer directement une règle

Admin peut retirer sans agent, jamais créer/réviser manuellement. L'aperçu d'impact se calcule sur la publication P ; la confirmation porte ruleId, version cible, expectedRuleSetVersion, empreinte d'aperçu et commandId.

Transaction identique au verrouillage de publication : vérifier appartenance de la règle à P et version attendue, confirmer que l'aperçu concerne exactement P, construire `M = membres(P) moins règle cible`, puis publication `ADMIN_RETIRE`, V=P+1, `retired_rule_id`, sans proposition ni faux rapport. Insérer membres(M), vérifier hash, clôturer, déplacer pointeur, périmer propositions basées sur P, reçu + SSE, commit. Aucun DELETE de règle/version/membre historique. Un manifeste vide après retrait de la dernière règle est autorisé : les futures analyses n'ont aucune couverture DMN et passent en fallback motivé. L'unique règle bootstrap est une propriété de l'initialisation, pas une règle impossible à retirer.

Un retrait composé avec créations/révisions peut aussi venir d'une proposition agent ; il suit alors le chemin AGENT_CHANGE avec rapport complet, pas une succession de publications partielles.

## 9. Dialogue et SSE

Sous verrou conversation : vérifier contexte et révision visée, aucun tour actif ; allouer turn_no, insérer tour QUEUED, message Admin, mission EXPERT_TURN (dossier de preuves figé autorisé, publication figée), outbox et reçu. La contrainte partielle interdit un deuxième tour actif, y compris depuis un autre onglet. Les réponses visent le tour et la révision exacts ; un brouillon révisé entretemps reçoit une réponse historique, pas une réapplication implicite. Le contenu d'un message ne vaut jamais commande de publication.

**SSE :** allouer `cursor=last_cursor+1` sous verrou de `sse_stream`, insérer l'événement et commiter avec le changement métier. Ne pas utiliser simplement un `bigserial` : deux transactions pourraient commiter dans l'ordre inverse de leurs numéros, et un client sauter le plus petit. Le verrou compteur conservé jusqu'au commit donne l'ordre nécessaire.

`Last-Event-ID` est `datasetEpoch:cursor`. Une époque différente, un curseur supérieur au dernier connu ou inférieur à `retained_from-1` impose une resynchronisation REST explicite. Un curseur disponible reprend `cursor > dernierVu ORDER BY cursor`. Plusieurs onglets ont leurs propres curseurs client, pas un curseur SQL global consommé destructivement.

Pour initialiser une vue sans trou : lecture cohérente de l'état REST **et du watermark SSE dans le même dossier de preuves figé de transaction**, puis replay des événements après ce watermark ; ou abonnement avec buffer avant lecture REST puis déduplication explicite. Une simple « lecture REST puis connexion SSE depuis maintenant » perdrait les changements intermédiaires. La rétention met à jour retained_from et supprime le préfixe correspondant dans une transaction verrouillant sse_stream. Si une connexion devient trop lente, demander resync au lieu de prétendre avoir rejoué les événements supprimés. Heartbeats SSE non durables ; chunks regroupés ; déconnexion navigateur ≠ annulation.

## 10. Reset : protocole d'exploitation, pas un DELETE applicatif

Aucun reset n'a été exécuté ici. Le rôle Java n'a ni DROP/TRUNCATE ni UPDATE d'active_dataset. Le reset est une commande de maintenance locale du **seul POC**, après confirmation explicite.

```text
confirmer projet, volumes nommés, profil, seed ; conserver .env sans le lire/afficher
fermer l'admission des commandes et arrêter les workers/dispatchers du projet
transaction courte éventuelle : prendre active_dataset FOR UPDATE, adopter les annulations
attendre arrêt borné / constater appels externes incertains ; ne pas promettre leur remboursement
arrêter le backend et broker du seul projet
recréer les stockages dédiés runtime/oracle/broker conformément au cadrage (hors SQL transaction)
appliquer le DDL de réalisation dans la base neuve ; importer le même jeu stable avec nouvelle époque
vérifier sources/manifeste, exactement R001 v1 active, zéro ancienne mission/conversation/proposition
vérifier topics/offsets et santé ; rouvrir admission seulement après succès complet
```

La recréation des volumes, SQL et Kafka n'est pas une transaction distribuée : toute étape échouée laisse le système fermé/non prêt, avec reprise explicite. La nouvelle époque est la barrière ultime contre un ancien message resté en vol, même après nettoyage des topics. Ne jamais simuler un reset en changeant seulement le pointeur tout en gardant les anciennes conversations visibles. Aucun nettoyage Docker global, aucune suppression de `.env`, aucun service démarré pendant cette conception.

## 11. Matrice des courses et garanties

| Course/panne | Verrou/contrainte/fence | Résultat attendu |
|---|---|---|
| Deux lancements même cas | Verrou cas + index unique analysis_one_active + reçu | Un run actif ; autre réponse idempotente ou 409 |
| Deux commandes même clé, corps différent | PK reçu + hash comparé après rollback perdant | 409, jamais deux effets |
| Lancement vs publication | Verrou SHARE/UPDATE pointeur | Analyse entièrement sur P ou V, jamais mélange |
| Publication vs publication/retrait | Verrou UPDATE pointeur + version attendue | Une gagne ; autre STALE/409 et nouvelle validation/confirmation |
| Nouvelle révision pendant validation | Verrou proposition + révision/hash du rapport | Rapport conservé historique, pas READY de la nouvelle révision |
| Ajout tardif de membre publié | Guard insert + publication clôturée | Refus SQL ; manifeste inchangé |
| Deux claims d'une mission | Verrou mission + unique tentative RUNNING | Un seul claim autorisé |
| Trois missions simultanées | Deux lignes slot + sélection verrouillée | Deux autorisations logiques, troisième attend ; limite externe physique nuancée ci-dessus |
| Ancien heartbeat/résultat après reprise | Époque + attempt + token + lease + état | Historisé LATE/refus, aucun effet métier ni libération du nouveau slot |
| Crash après appel LLM | Réserve UNKNOWN conservée, tentative bornée | Retry possible sous même budget ; pas d'exactly-once facturable |
| Résultat vs annulation | Même ordre de verrous + garde état terminal | Un seul point d'adoption gagne ; annulation adoptée interdit succès tardif |
| Double acceptation diagnostic | Verrou cas + unique diagnostic_id/command_id | Une acceptation, au plus une synthèse automatique |
| Deux tours / deux onglets | Verrou conversation + unique tour actif | Un tour actif, pas d'entrelacement de réponses |
| Kafka doublon / crash avant ack | Inbox eventId + hash + commit effet avant ack | Effet exactement une fois au niveau métier du protocole |
| Kafka ordre troué | sequence/attempt + BUFFERED + timeout | Pas d'adoption anticipée du résultat terminal |
| Outbox ack avant crash DB | eventId stable et déduplication inbox | Doublon transport attendu, pas perte silencieuse |
| Reconnexion SSE pendant commit | Compteur transactionnel + watermark cohérent | Replay sans saut dû à l'ordre des commits |
| Clôture d'import vs INSERT source | Verrou SHARE époque / UPDATE fermeture | Source incluse avant clôture ou insertion refusée après |
| Reset vs message ancien | Admission fermée + nouvelle époque aléatoire | Ancien résultat rejeté, même UUID métier stable |
| Cache DMN perdu après publication | Pointeur SQL autoritaire, hash artefacts | Reconstruction de V ou erreur explicite, pas fallback caché sur P |

## 12. Ce que SQL garantit, et ce qu'il ne garantit pas

| Invariant | SQL livré | Java/importeur/exploitation restant obligatoire |
|---|---|---|
| Types, nullabilité, intégrité de liens connus | PK/NOT NULL/FK/UNIQUE/CHECK ; ère dans les clés | Autorisation d'accès à l'époque/dossier de preuves figé de la mission |
| Source immuable et import fermé | UPDATE/DELETE refusés ; INSERT source interdit après sealed_at | Complétude avant clôture, exclusion des champs cachés/texte fuyant ; owners/TRUNCATE hors frontière |
| Un cas par exemplaire/campagne unique | UNIQUE époque/copy ; copy NOT NULL ; symptômes enfants | Au moins un symptôme ; attribution correcte du temps de référence |
| Projection cohérente du lieu/site | FK composite system_state/location/site | Sens métier de NOT_REGISTERED, incohérences de stock non « réparées » |
| Payload source discriminé | Enum + objet JSON + clés par type | Schéma complet, UUID/date/corrélations imbriqués, chronologie ; CHECK présence n'est pas typage intégral |
| Dossier de preuves figé et diagnostic immuables | UPDATE/DELETE des lignes interdits | Fermeture de la collection evidence_reference après création, hash du bundle, résolution source_key, absence de références inventées |
| Une analyse/tour/tentative active | Index partiels uniques | Graphe des transitions, contexte immuable, terminal jamais rouvert ; le DDL seul autoriserait certains UPDATE incohérents |
| Quota et leases | Limites configurées, tentative max2, slots 1/2, compteurs non négatifs | Sommes/réservations atomiques, deadline réelle, token actuel, expiration, contrôle de tous les chemins outils |
| Règles uniquement agents | Provenance FK mission, types author_kind et exception bootstrap | Vérifier rôle effectif de la mission, diagnostic accepté en fallback, aucun endpoint manuel CREATE/REVISE |
| R001 bootstrap unique | Unique bootstrap/époque ; R001 v1 + row_count=1 ; publication v1 | Exactement un membre, vraie table DMN à une ligne, pas de règles parallèles cachées ; metadata non preuve XML |
| Versions/révisions immuables | UPDATE/DELETE refusés | Insérer opérations/références une seule fois avec parent ; comparer hash complet avant validation/publication ; pas d'ajout tardif accepté |
| Manifeste clos immuable | Triggers de clôture, membres immuables, ajout tardif refusé, pointeur clos | Calcul exact ensemble/hashes, monotonie du pointeur, base du rapport, contenu des artefacts |
| Validation exacte | FK rapport→proposition/révision et publication→rapport exact | Hash/base/corpus/engine/validator concordants, outcome PASS, zéro blocage réel ; SQL n'impose pas ce lien sémantique |
| Lots bornés | Positions1..5 + PK + cas unique/lot | Minimum1, liste non étendue, annulation ne détruit pas résultat |
| Idempotence et ordre | Clés uniques reçus/inbox/séquence APPLIED | Hash identique, transitions, ordre contigu, ack après commit |
| SSE | PK époque/curseur, compteur non négatif | Protocole d'allocation/commit/relecture/rétention, pas simple INSERT avec curseur libre |
| Oracle isolé | Schéma/droits séparés ; aucune FK runtime vers evaluation | Memberships/credentials/montages/routes/logs/export nettoyés ; un schéma ne protège pas contre superuser |

Les contraintes utilisent SQL à trois valeurs : une CHECK qui retourne NULL passe. Les branches conditionnelles sensibles exigent donc explicitement `IS NOT NULL` (notamment versions de CREATE/REVISE) ; le schéma JSON valide aussi ces obligations. Les contraintes définies sur des colonnes JSON ne remplacent pas des FK vers les données imbriquées.

## 13. Tests entre composants à réaliser ultérieurement dans une base dédiée

Ces tests sont des critères, **pas des résultats exécutés dans ce dossier**. Les tests locaux livrés vérifient seulement syntaxe, structure et contrat.

- Appliquer 01 puis 02 en base PostgreSQL18 jetable ; contrôler tous les droits avec `SET ROLE` non owner. Runtime doit échouer sur `SELECT evaluation.case_expectation`, INSERT/UPDATE de source après clôture et TRUNCATE.
- Importer les vrais profils ; comparer comptes/manifeste/hash ; rollback d'import volontaire à mi-parcours laisse aucun pointeur actif partiel.
- Deux connexions : lancer même cas, publier deux propositions sur même base, retirer pendant publication, claim simultané, expirer une tentative puis envoyer ancien résultat. Vérifier les lignes finales et non seulement les codes HTTP.
- Insérer FK d'autre époque/cas, membre de règle inconnu, dossier de preuves figé d'un autre cas, slot3, sixième item de lot : refus SQL attendu.
- Fermer publication puis tenter INSERT/UPDATE/DELETE membre et UPDATE de publication : refus ; pointer publication ouverte : refus. Tenter un manifeste vide bootstrap : refus **importeur**, pas SQL.
- Agent propose preuve absente, XML interdit, cause sans diagnostic d'origine, validation stale : refus Java et absence de nouvelle publication.
- Crash contrôlé avant/après commit/ack outbox/inbox ; vérifier doublons sans double acceptation/publication et budget UNKNOWN conservé.
- Deux commits SSE inversés volontairement avant allocation du curseur ; vérifier ordre obtenu, rollback du compteur et resync après rétention/époque neuve.
- Dépasser estimation tokens fournisseur : comptabiliser réel, bloquer appel suivant, conserver échec visible. Chat4/retry2 et deadline non réinitialisée.
- Reset du seul projet confirmé : datasetId stable, époque différente, sources identiques, une seule règle, zéro conversation/mission/proposition ancienne active, clé secrète préservée sans lecture.
