# Registre des décisions et questions

## Décisions actuelles — interview du 2026-10-31

Ces décisions prévalent sur les lignes historiques incompatibles conservées plus bas. Les détails de FEEL, des contrats et de l'UX ne sont pas globalement approuvés par cette interview.

| ID | Réponse explicite | Conséquence |
|---|---|---|
| N01 | LLM réels pour les deux agents ET Discussion Expert | Pas de réponses préfabriquées dans le parcours applicatif ; tests hors ligne clairement séparés |
| N02 | Règles gérées dynamiquement au runtime, feature essentielle | Publication utilisée dès l'analyse suivante sans rebuild/redémarrage |
| N03 | Consultations successives via outils bornés | Le snapshot complet à outil unique actuel ne suffit pas à la cible |
| N04 | Chat : expliquer les preuves, corriger/réévaluer, construire/affiner une règle | Pas de simple stockage de messages ; effet métier explicite et traçable |
| N05 | Accepter le diagnostic puis approuver la règle testée | Deux actes humains, jamais une publication par texte libre |
| N06 | Dix dossiers : deux couverts par R001, quatre retours et quatre réparations | Tous vierges au reset ; un apprentissage par famille doit reconnaître les trois autres dossiers |
| N07 | Localisation différente, retour non répercuté, réparation non répercutée | Trois causes métier, pas dix familles ni 120 dossiers dans la cible |
| N08 | Appels directs, reprises bornées et erreurs visibles | Pas de secours mock/replay présenté comme réel ; aléa fournisseur expliqué |
| N09 | OpenAI existant, modèle configurable | Pas de changement de fournisseur demandé ni d'identifiant de modèle tenu pour vérifié sans contrôle |
| N10 | Table lisible, FEEL réel, faits et essais, cycle de gestion runtime | Espace DMN/FEEL dédié ; édition manuelle non retenue |
| N11 | Conserver la stack ; simplifier données, parcours et concepts | Pas de suppression de Kafka ni de migration générale |
| N12 | Développeurs/architectes découvrant agents et DMN ; démo 10–15 min | Markdown pédagogique lié au code, approfondissement séparé |
| N13 | Étapes/outils/preuves, explications intermédiaires et mesures réelles | Synthèses factuelles, pas chaîne de pensée interne ni compteurs fictifs |
| N14 | LLM produit les conditions FEEL, Java sérialise le DMN standard | Gabarit structurel permis, logique métier préfabriquée interdite |
| N15 | Rôle selon la phase, affiché, avec possibilité de changer | Deux interlocuteurs réels sans troisième agent routeur imposé |
| N16 | Réévaluation sur demande ; nouvelle acceptation si diagnostic modifié | Invalider les propositions dépendantes ; apport humain avec provenance explicite |
| N17 | Rapport d'échec renvoyé à l'agent ; correction automatique bornée | Nouvelles révisions/test, arrêt visible si refus persistant |
| N18 | Aucun plafond à déterminer pour l'instant : « open bar » | Les anciens budgets ne font plus autorité ; mesure et bornage des corrections demeurent |

Le découpage exact **2 + 4 + 4** vient de la précision de Kevin, pas de la proposition initiale d'une boucle unique. Chacune des deux familles inconnues doit pouvoir être apprise indépendamment, dans l'ordre choisi par le démonstrateur.

**OUVERTS :** détail FEEL et sérialisation, catalogue des outils, SDK d'orchestration, nombre de reprises, prototype UI. **Non exécutés pendant l'audit :** appels OpenAI réels et reset. L'interview ne vaut pas autorisation générale de modifier l'application.

## Historique — 2026-10-24

Les décisions de volumétrie, quotas et SDK ci-dessous sont historiques lorsqu'elles contredisent N01–N18. Elles expliquent le cadrage initial ; elles ne sont pas des exigences supplémentaires à réaliser.

## Règle de gestion

Une recommandation reste **OUVERTE** jusqu'à une réponse explicite de Kevin. Les exigences déjà explicites sont dans [la vision](00-vision-et-exigences.md) et ne sont pas remises en question sans raison.

Chaque décision retenue documentera : choix, raison, alternatives écartées, conséquences, date de décision et documents concernés. Une réponse partielle ne vaut pas approbation de tout le cadrage.

## Vague A — domaine et preuve

| ID | Question | Recommandation initiale | État |
|---|---|---|---|
| A01 | Quelle unité d'inventaire et d'analyse ? | Exemplaires identifiés par code-barres, avec synthèses par titre/site | VALIDÉ — 2026-10-24 |
| A02 | Quelle organisation de bibliothèque ? | Petit réseau fictif de trois sites, avec rayons, réserves et transferts inter-sites | VALIDÉ — 2026-10-24 |
| A03 | Le produit analyse-t-il ou corrige-t-il aussi le stock ? | Diagnostic expliqué et recommandations uniquement ; aucune correction du stock | VALIDÉ — 2026-10-24 |
| A04 | Quelle profondeur causale ? | Socle mono-cause, cas ambigus ou incomplets, quelques cas multi-causes contrôlés | VALIDÉ — 2026-10-24 |
| A05 | Quel mode de génération ? | Scénarios pédagogiques garantis + générateur déterministe paramétré par seed | VALIDÉ — 2026-10-24 |

Décisions recueillies par questionnaire interactif. Conséquences : l'exemplaire est l'unité de référence ; les transferts font partie du domaine ; le stock source reste en lecture seule ; l'abstention et les diagnostics partiels doivent être représentables ; les données de démonstration doivent être reproductibles. L'analyse agrégée comme domaine autonome, la correction du stock et une simulation ouverte exhaustive ne sont pas retenues pour ce périmètre.

### Deuxième vague validée — 2026-10-24

| ID | Décision explicite | Conséquence |
|---|---|---|
| A06 | Inventaire à date de référence fixe, activité suspendue pendant les scans ; historiques et retards d'intégration conservés | Pas de reconstruction de mouvements concurrents pendant les scans dans le périmètre initial. |
| A07 | Données structurées et quelques notes opérateur en texte libre, sans RAG | Prévoir des outils de lecture ciblée des notes ; décider ce qui est admissible comme prédicat déterministe. |
| B01 | Cause étayée ou hypothèses explicitement incertaines, avec preuves et recommandations | La conclusion n'est pas obligatoirement une cause unique ; ne pas inventer de confiance numérique calibrée. |
| B04 | Fallback automatique, visible, borné par un budget et annulable | Définir la suffisance du diagnostic, les motifs de fallback et les limites d'exécution. |
| B08 | Suppression = retrait immédiat du référentiel actif avec conservation des versions historiques | Les nouvelles analyses ignorent la règle retirée ; les anciennes restent explicables. |

Ces décisions sont validées ; leurs modalités d'exécution détaillées restent à concevoir.

### Troisième vague validée — 2026-10-24

| ID | Décision explicite | Conséquence |
|---|---|---|
| B02a | Notes réservées à l'investigation ; règles fondées uniquement sur des faits structurés calculables sans IA | Aucun appel LLM caché dans le calcul des entrées déterministes. |
| B03 | DMN/FEEL avec moteur Java existant | Ne pas construire un DSL propriétaire ; définir les entrées, le sous-ensemble autorisé et la politique d'application. Le moteur exact reste à sélectionner. |
| B07 | Types, tests positifs/négatifs et replay avant/après ; vérification formelle reportée | Z3 et toute promesse de preuve SMT sont hors premier périmètre. |
| B06 | Deux actes distincts dans le même espace : accepter le diagnostic, puis approuver/publier le changement de règles | L'acceptation d'un cas ne vaut pas approbation de sa généralisation. |
| B09 | Publication atomique des créations/révisions/retraits ; nouvelles analyses sur la nouvelle version, analyses en cours sur leur version initiale | Snapshot de référentiel par exécution et concurrence optimiste sur publication. |

Motif exprimé pour B03 : Kevin préfère un langage décisionnel existant à un DSL dont les méthodes/opérateurs restent à définir. DMN/FEEL ne dispense pas de spécifier les faits d'entrée et les capacités de calcul autorisées.

## Vague B — sémantique des règles et boucle d'apprentissage

| ID | Arbitrage à traiter |
|---|---|
| B01 | Qu'est-ce qu'une règle produit : cause, degré d'étayage, éléments de preuve, vérifications recommandées, action ? |
| B02 | Quelles primitives relationnelles/temporelles sont autorisées, et que faire d'une capacité manquante ? |
| B03 | DSL typé, DMN/FEEL ou moteur de règles existant : quelle lisibilité métier et quelle expressivité ? |
| B04 | Quand déclencher le fallback : absence de règle, conflit, couverture partielle, faits inconnus ? |
| B05 | Plusieurs règles applicables : conclusions compatibles, contributions multiples ou conflit bloquant ? |
| B06 | Séparer validation du diagnostic, validation de la règle et publication : combien d'actes UX ? |
| B07 | Quelle profondeur de vérification : schéma/types, tests, replay, SMT restreint ? |
| B08 | Mise à jour et suppression : retrait opérationnel avec historique ou effacement définitif ? |
| B09 | Remplacements multiples, subsomption et rollback du référentiel : quelles garanties ? |
| B10 | Quels faits et quelles règles l'agent peut-il consulter ? Quelles limites de coût, durée et appels d'outils ? |
| B11 | Orchestration agentique : un agent avec phases distinctes ou plusieurs rôles spécialisés ? |
| B12 | Quand et comment une règle devient-elle obsolète ou à réexaminer ? |

**Proposition à discuter :** une publication atomique porte sur un ensemble de créations/révisions/retraits et sur une version de référentiel de départ. L'approbation devient périmée si le contenu, le rapport de tests ou le référentiel de départ change. Les analyses déjà en cours gardent leur snapshot ; celles démarrant après publication utilisent la nouvelle version.

## Vague C — cas d'usage et UX/UI

### Décisions du 2026-10-24

| ID | Choix explicite |
|---|---|
| C01 | Espace dossier à trois panneaux : faits/chronologie, diagnostic/proposition, chat expert. |
| C02 | Aucune modification manuelle des règles par l'Admin ; l'agent de règles a le monopole de leur modification. L'Admin peut retirer une règle. Création réservée à l'agent à partir d'un diagnostic (voir C05). |
| C03 | Analyse individuelle et petits lots bornés avec progression/résultats par cas. |
| C04 | Vue opérationnelle normale et mode démonstration guidé montrant le passage IA vers déterministe. |


- Tableau de bord : métriques utiles à l'Admin et à la démonstration.
- Liste des cas : filtres, priorités, regroupements et lancement individuel/par lot.
- Détail d'un cas : inventaire, état attendu, chronologie, sources et ambiguïtés.
- Progression déterministe/agentique, annulation, reprise et échec.
- Chat ancré au dossier : demander une preuve, corriger une hypothèse, affiner une règle.
- Proposition visible à côté du chat : conditions, conclusion, diff, tests et contre-exemples.
- Bibliothèque de règles : consultation, demandes de création/modification à l'agent, retrait par l'Admin, historique. Aucun éditeur manuel de modification de règle.
- Prévisualisation d'impact avant publication et comparaison après publication.
- Vue pédagogique : expliquer le chemin emprunté sans confondre trace d'outils et raisonnement interne LLM.
- Style visuel, langue, densité d'information, accessibilité et taille d'écran.

## Vague D — architecture, DDD et exploitation

### Décisions du 2026-10-24

| ID | Choix explicite |
|---|---|
| C05 | Création et modification uniquement par l'agent, à partir d'un diagnostic ; l'Admin peut demander par chat la révision d'une règle existante. Pas de création manuelle ni de création libre hors diagnostic. |
| D01 | Deux rôles IA explicites dans le même service Python : investigateur des causes, puis agent de règles pour généralisation et chat. |
| D02 | Java détient objets métier, cycles de vie, moteur DMN et publications ; Python exécute les missions IA ; Vue reste adaptateur REST/SSE. |
| D03 | Composants gratuits pour usage personnel, source available admis ; API OpenAI payante comme exception explicite. |
| D04 | Plafonds techniques de durée, tokens et appels ; pas de suivi monétaire dans l'application. |


- Glossaire, contextes métier, agrégats, invariants et règles de dépendances.
- Cycles de vie séparés : cas, exécution, diagnostic, proposition, version de règle, publication.
- Source de vérité métier Java et responsabilité exacte du service Python.
- Modèles de données, snapshots de preuves et traçabilité des analyses.
- Contrats REST/Kafka/SSE ; identités de corrélation, versions de payload et ordre des messages.
- Commit métier/publication Kafka : outbox ou mécanisme alternatif justifié ; déduplication des résultats.
- Résultats tardifs, annulations, rejeu, redémarrages et traitement des propositions devenues périmées.
- Reconnexion SSE : reprise sur état durable, pas dépendance à un flux volatile.
- Matrice versions/licences, limites de ressources et exposition localhost.
- Coût OpenAI, secrets, logs expurgés et comportement sans clé/API indisponible.
- Réinitialisation : volumes du POC seulement, événements Kafka, conversations, règles, seed et oracle ; préserver `.env`.

## Vague E — critères d'acceptation et conclusion

### Décisions du 2026-10-24

| ID | Choix explicite |
|---|---|
| B05 | Collecter les règles applicables, combiner les conclusions compatibles ; fallback sur contradiction ou couverture insuffisante. |
| B04b | Une hypothèse insuffisamment étayée déclenche le fallback sur un nouveau cas ; l'investigation peut ensuite conclure à l'indétermination. |
| E01 | **UNE SEULE règle initiale, très très simple.** Toute règle supplémentaire doit être acquise via la boucle ou ajoutée seulement dans des fixtures de tests isolées du seed. |
| E02 | Environ 1 200 exemplaires et 120 cas d'écart, parcours guidé court et variantes réservées aux tests. |
| D05 | Moteur DMN Apache KIE embarqué en Java et OpenAI Agents SDK en Python ; aucun orchestrateur supplémentaire. |


- Jeu de démonstration garanti et jeu d'évaluation distinct.
- Mesures de précision, couverture, abstention, faux positifs, temps et appels LLM.
- Test de publication runtime et d'analyse immédiate d'un autre cas.
- Test de généralisation avec remplacement/retrait d'une ancienne règle.
- Tests de conflits, sur-spécialisation, données absentes, doublons et pannes.
- Tests d'absence de fuite de l'oracle et de secrets.
- Budget technique et risques résiduels explicites.
- Revue finale bornée, décision de démarrer ou non l'implémentation.

## Dernière vague validée — 2026-10-24

| ID | Décision |
|---|---|
| E03 | Unique règle initiale : localisation différente constatée, sur observations fiables, sans attribuer une faute ou un mécanisme non prouvé. |
| D06 | Valeurs de départ acceptées : lots de 5, 2 missions simultanées, mission 300 s / 8 appels LLM / 24 outils / 64 000 tokens entrants cumulés / 12 000 sortants ; chat 120 s / 4 appels. Configuration ajustable après mesure. |
| C06 | Interface française claire et sobre, tableaux lisibles et distinction faits/hypothèses/validations ; prototype visuel à valider ultérieurement. |
| F01 | Livrer le cadrage consolidé pour relecture, **sans aucune implémentation**. Pas de validation globale implicite des détails proposés. |

## Points restant à la revue finale

- Sémantique détaillée des contrats de faits et du sous-ensemble FEEL ; vérifier qu'elle couvre les scénarios sans logique diagnostique cachée en Java.
- Détails proposés des agrégats, API, messages, reprise et retrait ; validation de cohérence d'ensemble.
- Catalogue précis de génération, critères de performance et direction graphique via prototype.
- Versions transitives, digests, licence des artefacts et compatibilité réelle : gates de début de réalisation, non exécutés pendant le cadrage.

## Points pratiques non bloquants

- Confirmer ultérieurement le nom final du dossier : il contient actuellement `-ai-`.
- Portée de « libre » tranchée : gratuité d'usage personnel, source available admis (D03). Vérifier les licences exactes au gel des dépendances.
- Fixer les versions cibles après choix des composants ; ne pas recopier les tags `latest` de la référence.
