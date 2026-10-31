# Vision et exigences — démo simplifiée

## Statut et autorité

Cible révisée à partir des réponses de Kevin le **2026-10-31**. Cette page et le [registre des décisions](04-registre-des-decisions.md) prévalent sur les choix historiques incompatibles. Une exigence validée n'est pas une fonctionnalité déjà livrée : voir [l'audit réel](12-etat-implementation-actuel.md).

Les détails non arbitrés sont marqués **OUVERTS** ou **PROPOSÉS**. La présente révision est documentaire ; elle n'autorise pas implicitement sa réalisation applicative.

## Intention validée

Une démo locale, démonstrative **et pédagogique**, destinée aux développeurs et architectes découvrant les agents LLM et DMN. Parcours principal de **10 à 15 minutes**, hors installation, avec approfondissement technique séparé.

Montrer une acquisition de connaissance exécutable : un cas inconnu est diagnostiqué avec un LLM réel, une règle simple est produite avec un LLM réel, puis d'autres cas deviennent déterministes **sans redémarrage et sans nouvel appel LLM de diagnostic**. Il ne s'agit ni d'entraîner les poids du modèle, ni de corriger automatiquement le stock.

## Jeu initial validé : 2 + 4 + 4

Après première utilisation ou reset : **dix dossiers, tous non analysés**, une seule règle déterministe active.

| Groupe | Dossiers | Couverture initiale | Effet attendu de l'apprentissage |
|---|---:|---|---|
| Localisation différente | 2 | R001, déjà active | Les deux dossiers sont diagnostiqués sans LLM lorsqu'on les analyse |
| Retour non répercuté | 4 | Aucune règle active correspondante | Un dossier d'origine permet de créer une règle ; les trois autres sont ensuite reconnus sans LLM |
| Réparation non répercutée | 4 | Aucune règle active correspondante | Même boucle indépendante ; les trois autres deviennent déterministes après acquisition |

« Règle 2 » et « règle 3 » désignent ici les futures règles apprises, pas des règles cachées préchargées. Leur identité et leurs conditions viennent des propositions réelles. Les causes connues du concepteur restent hors contexte des agents. Voir [le scénario de données](09-donnees-evaluation-et-reset.md).

## Exigences fonctionnelles validées

| ID | Exigence | Preuve de réalisation attendue |
|---|---|---|
| E01 | Déterministe en premier, fallback automatique si le dossier n'est pas suffisamment couvert | Trace DMN et motif du relais avant la mission LLM |
| E02 | Investigateur utilisant réellement OpenAI avec consultations successives via des outils bornés | Appels fournisseur, outils et preuves référencées, diagnostic persistant |
| E03 | Agent de règles utilisant réellement OpenAI pour proposer les conditions FEEL | Conditions issues de la réponse LLM ; Java sérialise une structure DMN standard |
| E04 | Création, révision, activation et retrait des règles dynamiques au runtime | Nouvelle version utilisée dès l'analyse suivante, sans rebuild, redémarrage ni déploiement |
| E05 | Deux décisions humaines distinctes | Accepter le diagnostic, puis approuver et publier la révision de règle réellement testée |
| E06 | Discussion Expert réelle avec les deux rôles | Réponses sourcées, explication du diagnostic, correction métier, construction et affinage de règle |
| E07 | Rôle du chat choisi selon la phase, affiché et modifiable | Investigateur avant acceptation ; agent de règles ensuite ; retour au diagnostic possible |
| E08 | Réévaluation sur demande explicite après une correction métier | Nouveau diagnostic à accepter si modifié ; propositions dépendantes périmées |
| E09 | Échec des tests communiqué à l'agent, avec correction automatique bornée | Rapport réel, nouvelle révision, nouveaux tests ; arrêt visible si la correction échoue |
| E10 | Espace de compréhension et gestion DMN/FEEL | Table lisible, FEEL exact, faits, essai sur un dossier et conditions vraies/fausses/inconnues, cycle runtime |
| E11 | Investigation observable | Étapes, outils, preuves, synthèses intermédiaires factuelles, modèle, appels, tokens et durée réels |
| E12 | Documentation pédagogique Markdown de tous les traitements et technologies | Explications liées au code et aux étapes de démo, avec limites et procédures vérifiables |
| E13 | Démo simple à remettre à zéro | Retour aux dix dossiers vierges et à R001 seule ; configuration locale conservée |

Pas d'éditeur manuel de règles retenu : l'expert pilote la proposition par le dialogue et les décisions applicatives. Un message « oui » n'est jamais une publication.

## Contraintes conservées

La stack est conservée : Vue/Pinia/Router, Java/Spring Boot, PostgreSQL, Apache KIE DMN/FEEL, agents Python, Kafka/Redpanda, SSE et Docker Compose. Pas de migration technologique générale ni d'orchestrateur supplémentaire imposé.

OpenAI existant, modèle configurable ; l'identifiant réellement accessible doit être vérifié avec le fournisseur, sans recopier une affirmation historique comme preuve actuelle. L'application locale envoie les données nécessaires aux inférences à un service distant. Aucun secret dans le navigateur ou les traces.

Java reste propriétaire des états, dossiers de preuves figés, validations et publications. Python consulte et propose ; pas d'accès SQL libre, d'écriture de stock, de shell ou de droit de publication. Les règles déterministes utilisent des faits structurés calculés **sans LLM**, sans oracle ni conclusion fabriquée.

Pas de RAG, Keycloak, Valkey, multi-instance ou correction de stock dans ce périmètre. DDD sert les invariants ; il ne justifie pas de multiplier les services ou les écrans.

## Simplifier sans simuler

Les dix familles, les 120 dossiers, les 1 200 exemplaires comme volumétrie imposée, les lots et la composition multi-cause ne pilotent plus le parcours initial. Des contre-exemples et données incomplètes restent dans les tests, pas comme nouvelles familles à découvrir dans la liste de démo.

Les appels sont **en direct**, avec erreurs visibles et reprises bornées. Aucun mock ni replay n'est présenté comme une nouvelle exécution OpenAI. Les doubles restent légitimes dans les tests hors ligne explicitement identifiés.

Après publication, les résultats DMN attendus sur les trois analogues de chaque famille doivent être reproductibles. La disponibilité et les réponses d'un LLM ne peuvent pas être garanties à chaque appel : prévol, tests réels et corrections renforcent la fiabilité sans fabriquer une réussite.

**Aucun plafond financier, de tokens ou d'appels à définir pour l'instant : « open bar ».** Mesurer reste obligatoire. Cela n'autorise ni boucle infinie ni publication forcée ; les reprises restent bornées et les appels payants de vérification sont convenus avant exécution. Les anciens plafonds ne sont pas reconfirmés.

## Points ouverts

- Atelier DMN/FEEL : sous-ensemble autorisé, représentation des conditions LLM, faits typés, traces par condition et sécurisation de l'évaluation.
- Catalogue précis des outils des deux rôles, nombre de corrections et modalités d'annulation/reprise.
- Choix du SDK d'orchestration : la Responses API existe ; adopter Agents SDK n'est pas une exigence reconfirmée.
- Prototype de l'espace règles et détails du parcours 10–15 minutes.

L'ordre de réalisation et les critères de sortie sont dans [la roadmap](13-roadmap-demo.md). Le [guide pédagogique](14-guide-pedagogique.md) explique les mécanismes et distingue l'existant de la cible.
