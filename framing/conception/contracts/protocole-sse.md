# REST/SSE : snapshot sans fenêtre de perte

## Curseur et log durable

Le curseur est une chaîne `<datasetEpoch UUID>:<position décimale>` ; la position zéro est autorisée pour un log vide. C'est un identifiant de log UI durable, **pas** `eventId`, offset Kafka ou numéro de tentative. JSON `cursor`, ligne SSE `id` et epoch de l'enveloppe doivent correspondre. Cette égalité interchamps est vérifiée par Java, pas exprimable par le JSON Schema standard fourni.

Chaque changement métier adopté écrit sa notification SSE **dans la même transaction PostgreSQL**. Les notifications ne représentent jamais une simple réception Kafka non validée. Les writers verrouillent une ligne compteur propre à l'epoch jusqu'au commit, affectent les positions dans cette transaction et commitent état + log + compteur ensemble. Une simple séquence PostgreSQL `nextval` ne suffit pas : les commits pourraient arriver dans un ordre différent des numéros.

## Ouverture sans course

1. Vue appelle `GET /api/sync-snapshot` (`Cache-Control: no-store`). Java ouvre un snapshot MVCC cohérent, lit l'epoch et le compteur commité, puis toutes les collections synchronisées dans **la même vue transactionnelle**. Il renvoie `SyncSnapshot`, incluant `cursor=C`. Aucune pagination indépendante ne peut remplacer cette lecture atomique ; les plafonds sont ceux du POC. Si la taille maximale ne permet plus une réponse complète, refuser explicitement et faire évoluer ce protocole, ne jamais tronquer silencieusement.
2. Vue remplace atomiquement son store courant avec ce snapshot puis ouvre `/api/events?after=C`.
3. Le serveur valide l'epoch, la borne de rétention et l'absence de curseur futur. Il rejoue toutes les positions strictement `> C` depuis le log durable, dans l'ordre.
4. Il continue à lire ce **même log** après sa dernière position envoyée. Une notification mémoire peut réveiller la boucle mais n'est jamais la source de vérité. Pour éviter la course replay/abonnement, utiliser la boucle durable avec réveil périodique, ou installer le réveil avant la dernière relecture. Ne pas faire « lire l'historique puis s'abonner uniquement aux notifications mémoire ».
5. Tout commit après la vue MVCC initiale a une position supérieure à C et sera donc rejoué. Tout commit déjà visible au snapshot est couvert par C. C'est la raison du verrou de compteur commité.

Les collections de SyncSnapshot couvrent cas, analyses, missions, propositions, publications, conversations et messages, avec vue globale. Les dossiers détaillés, preuves et rapports lourds restent des lectures versionnées à la demande. Une notification indique exactement quelle ressource relire. Lorsqu'une lecture a une version plus récente qu'une notification en retard, ne pas régresser le store.

## Reconnexion

- Première connexion : `after` requis s'il n'y a pas `Last-Event-ID` ; absence des deux → `400`.
- Reconnexion native EventSource : `Last-Event-ID` prime sur le query `after` conservé. S'ils sont tous deux présents, même epoch obligatoire et Last-Event-ID doit être supérieur ou égal à after. Contradiction → `409`.
- Curseur non syntaxique → `400`. Curseur futur → `409 STATE_CONFLICT`. Mauvaise epoch → `409 EPOCH_MISMATCH`. Curseur trop ancien → `410 CURSOR_EXPIRED` avant ouverture.
- Si la réponse SSE est déjà ouverte, `ResyncRequired` est un événement de contrôle **sans ligne id et sans champ cursor**, puis fermeture. Vue ferme l'ancien EventSource, oublie le curseur et recommence au snapshot. Elle ne persiste pas le contrôle comme nouvelle position.
- Les replays peuvent dupliquer des notifications après une coupure ; Vue déduplique par epoch + position. Le curseur local n'avance qu'après application du changement au store. Chaque onglet garde son propre curseur.
- Pour ne pas laisser EventSource boucler indéfiniment sur un statut d'erreur, le wrapper UI ferme le flux en erreur et réexécute le protocole snapshot ; il ne tente pas d'inférer une continuité inexistante.

## Framing exact

`Content-Type: text/event-stream; charset=utf-8`, `Cache-Control: no-cache`, buffering proxy désactivé. Une notification durable :

```text
id: 00000000-0000-4000-8000-000000000002:1
event: AnalysisChanged
data: {"eventType":"AnalysisChanged","schemaVersion":"1.0.0","datasetEpoch":"00000000-0000-4000-8000-000000000002","occurredAt":"2026-01-15T12:00:00Z","data":{"analysisId":"00000000-0000-4000-8000-00000000000b","caseId":"00000000-0000-4000-8000-00000000000a","version":2,"state":"RUNNING"},"cursor":"00000000-0000-4000-8000-000000000002:1"}

```

Cet exemple est synthétique. Le fichier `examples/sse/stream.txt` donne l'ensemble des variantes exactes. JSON `eventType` doit égaler la ligne `event`. Les retours à la ligne d'un texte JSON sont échappés ; un bloc se termine par une ligne vide.

## Types et sémantique

| Type | Invalidation/relecture |
|---|---|
| `AnalysisChanged` | analyse et état du cas |
| `MissionChanged` | mission, compteurs et annulation |
| `DiagnosticReady` | diagnostic complet par REST, jamais déduit d'un chunk |
| `ProposalChanged` | proposition/révision courante |
| `ValidationCompleted` | rapport exact de validation |
| `PublicationActivated` | pointeur de référentiel, catalogue et propositions devenues périmées |
| `ConversationMessageChanged` | texte complet du message à cette version, pas delta ambigu ; état COMPLETE ou INTERRUPTED explicite |
| `BatchChanged` | progression du lot et résultats individuels |
| `DatasetReset` | contrôle sans cursor : fermer et refaire le snapshot ; aucun changement de modèle en place |
| `ResyncRequired` | contrôle sans cursor : refaire le snapshot |

Heartbeat transport : commentaire `: heartbeat` toutes les 15 secondes, sans id, sans donnée métier ni modification de curseur. Timeout proxy recommandé supérieur à 45 secondes. Rétention proposée : 24 heures, avec plafond 100 000 événements ; la borne minimale disponible est durablement connue. Buffer par connexion maximum 1 000 notifications ; au-delà, signal `SLOW_CONSUMER` puis fermeture, pas de perte silencieuse. Ces valeurs sont des choix de conception à mettre en configuration, pas une mesure de performances.

Un reset invalide tous les curseurs de l'ancienne epoch. `DatasetReset` est best effort sur les connexions ouvertes ; le contrôle d'epoch à la prochaine connexion reste obligatoire même si ce signal n'a jamais été reçu. La fermeture du navigateur n'annule aucune mission. Un chunk incomplet reste affiché comme interrompu après échec/annulation ; il ne devient jamais un diagnostic ou une proposition approuvable.

## Tests d'intégration à réaliser côté application

Commit entre snapshot et ouverture ; commits concurrents avec ordre inversé avant verrou compteur ; coupure après envoi mais avant application UI ; purge pendant replay ; reset pendant connexion ; onglets concurrents ; consommateur lent ; signal mémoire perdu ; réponse REST plus récente que notification en retard. Les tests locaux des schémas ne prétendent pas exécuter ces scénarios réseau/transactionnels.
