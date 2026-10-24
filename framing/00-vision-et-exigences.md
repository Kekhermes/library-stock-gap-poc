# Vision et exigences

## Intention — ACQUIS

Application pédagogique locale d'analyse des désynchronisations du stock d'une bibliothèque après inventaire. Objectif secondaire : rendre l'architecture hybride suffisamment compréhensible et crédible pour la présenter comme piste pour un projet d'entreprise.

Le produit doit montrer une acquisition de connaissances contrôlée, pas simplement un chatbot placé devant une base de données.

## Exigences explicites

| ID | Exigence acquise |
|---|---|
| E01 | Le diagnostic déterministe intervient en premier ; l'investigation agentique est un fallback. |
| E02 | L'agent propose des règles réutilisables à partir de son investigation ; un humain valide leur intégration. |
| E03 | Une règle validée devient utilisable au runtime, dès l'analyse suivante, sans redémarrage ni livraison applicative. |
| E04 | Un chat intégré permet à l'expert de discuter efficacement avec l'agent responsable de la proposition de règles. |
| E05 | Les règles sont visibles ; l'agent peut proposer création, mise à jour et suppression, notamment par généralisation. Correction explicite : la modification des règles est le monopole de l'agent de règles, sans édition manuelle par l'Admin ; celui-ci peut les retirer. La création est également réservée à l'agent et ancrée dans un diagnostic ; l'Admin peut demander par chat la révision d'une règle existante. |
| E06 | Un domaine de bibliothèque fictif mais plausible fournit stock informatique, inventaire et sources de recoupement : prêts, réceptions, etc. |
| E07 | Les cas de désynchronisation sont déjà présents en base ; le POC n'est pas d'abord un outil de collecte d'inventaire. |
| E08 | Un seul persona Admin omnipotent simplifie le POC. |
| E09 | Conception DDD, objets métier, invariants et cycles de vie explicites. |
| E10 | Frontend Vue, backend applicatif Java/Spring Boot, SSE, agents Python, communications asynchrones Kafka sur Redpanda. |
| E11 | Exécution locale Docker/Docker Compose ; réinitialisation complète simple et documentée. |
| E12 | Pas de RAG, Keycloak, Valkey ni architecture multi-instance à construire. |
| E13 | Utilisation du modèle demandé GPT-5.6 Luna ; clé OpenAI fournie ultérieurement dans un `.env` ignoré par Git. |
| E14 | Composants gratuits pour usage personnel, y compris source available ; API OpenAI payante comme exception explicite. |
| E15 | Livrables Markdown dans `framing`, cadrage conjoint avant développement. |

## Interprétations à confirmer

- « Local » désigne l'hébergement de l'application, pas une inférence hors ligne : l'appel OpenAI est une dépendance distante explicitement demandée.
- Le coût d'usage de l'API OpenAI est distinct des licences des composants locaux. Un budget et des limites d'exécution doivent être fixés.
- DDD est l'interprétation de « DD », compte tenu de la demande d'objets métier et de cycles de vie.
- Pas de microservices métier supplémentaires par défaut. La séparation Java/Python et le broker constituent néanmoins plusieurs processus : délais, doublons et pannes restent à traiter même en local.

## Garde-fous proposés — NON ENCORE ARBITRÉS

1. Distinguer un constat d'écart, une hypothèse causale, un diagnostic accepté et une règle généralisée.
2. Une absence de preuve ne devient pas une preuve d'absence. Un résultat « indéterminé » doit être possible.
3. Une règle déterministe peut se tromper : déterminisme signifie reproductibilité, pas vérité métier.
4. L'agent n'écrit pas directement dans le stock ou dans le référentiel actif ; il soumet des propositions au backend.
5. Valider un diagnostic n'approuve pas automatiquement toutes les généralisations proposées.
6. Le texte libre du chat ne vaut pas autorisation de publication ; un acte explicite porte sur une version exacte et son rapport de validation.
7. Les modifications coordonnées de règles sont atomiques : remplacement et retrait ne créent pas une période incohérente.
8. Les analyses passées conservent leur référentiel et leurs preuves, même après modification ou suppression opérationnelle d'une règle.
9. La vérité de simulation ne doit pas être accessible aux outils de diagnostic ou aux invites LLM.

## Preuve pédagogique proposée

Une démonstration réussie doit permettre de voir :

- un premier cas non couvert, avec traces de recoupement et hypothèse expliquée ;
- une discussion métier qui modifie réellement la proposition de règle ;
- un contre-exemple ou test négatif empêchant une règle trop large ;
- une approbation explicite et une publication sans redémarrage ;
- un autre cas analogue mais non identique, diagnostiqué sans appel LLM ;
- une généralisation remplaçant une règle ancienne, avec impacts visibles ;
- un cas ambigu pour lequel le système refuse de conclure artificiellement ;
- une réinitialisation restaurant les cas et règles initiaux sans effacer la clé locale.

L'évaluation doit comparer précision, couverture déterministe, faux positifs, abstentions, appels LLM, tokens, latence et effort humain. Une baisse des appels LLM seule n'est pas une preuve de progrès.

## Hors périmètre acquis et points non décidés

Hors périmètre acquis : RAG, authentification Keycloak, cache Valkey, déploiement cloud, gestion multi-personas.

Décisions du 2026-10-24 : exemplaires identifiés par code-barres ; synthèses par titre/site ; trois sites avec rayons, réserves et transferts ; diagnostic et recommandations sans correction de stock ; socle mono-cause avec cas ambigus/incomplets et quelques multi-causes contrôlées ; scénarios garantis et générateur déterministe par seed.

Deuxième vague validée le 2026-10-24 : inventaire à date fixe avec activité suspendue pendant les scans ; historiques et retards d'intégration conservés ; données structurées et quelques notes opérateur libres sans RAG ; conclusions pouvant être des causes étayées ou des hypothèses incertaines, accompagnées de preuves et recommandations ; fallback automatique visible, borné et annulable ; suppression d'une règle par retrait actif avec historique conservé.

Troisième vague validée le 2026-10-24 : DMN/FEEL avec moteur Java existant, sans DSL propriétaire ; notes réservées à l'investigation et règles sur faits structurés sans IA ; types/tests/replay sans vérification SMT dans le premier périmètre ; validation du diagnostic distincte de celle du changement de règles ; publication atomique, analyses en cours conservant leur version initiale.

Décisions complémentaires : création de règles uniquement par l'agent à partir d'un diagnostic ; révision par chat d'une règle existante ; deux rôles IA dans un service Python ; Java propriétaire des états métier ; Apache KIE DMN et OpenAI Agents SDK ; collecte des conclusions compatibles avec fallback sur insuffisance ou conflit ; UNE SEULE règle initiale très simple ; environ 1 200 exemplaires et 120 cas ; petits lots ; dossier à trois panneaux et mode démonstration ; plafonds techniques sans suivi monétaire.

Derniers choix validés : règle initiale de localisation différente constatée, plafonds techniques proposés acceptés comme valeurs de départ, interface française claire et sobre. Kevin demande la livraison du cadrage consolidé pour relecture, sans implémentation.

Restent à valider : détails du langage FEEL autorisé, contrats de faits, objectifs mesurables, prototype visuel et cohérence finale de l'ensemble.
