# Cadrage révisé — library-stock-gap-poc

## Statut

**Interview et audit consolidés le 2026-10-31 ; cible simplifiée, roadmap proposée.**

Dix dossiers vierges **2 + 4 + 4**, une règle initiale, deux familles à apprendre, deux agents et Discussion Expert utilisant réellement OpenAI. Les règles proposées sont activables au runtime sans redémarrage. Le LLM produit les conditions FEEL ; Java sérialise le DMN standard. Public développeurs/architectes, parcours 10–15 minutes, documentation pédagogique Markdown.

Les anciens choix incompatibles de volumétrie, plafonds et SDK ne sont plus prescriptifs. Aucun plafond de consommation à définir maintenant : « open bar », avec mesure réelle, reprises bornées et erreurs visibles. Le détail FEEL et certains contrats d'agents restent à arbitrer.

## Autorité des documents

1. **Attendu :** [vision actuelle](00-vision-et-exigences.md), [réponses et décisions](04-registre-des-decisions.md), [jeu et scénario](09-donnees-evaluation-et-reset.md).
2. **Existant vérifié :** [audit](12-etat-implementation-actuel.md), qui ne redéfinit pas l'attendu.
3. **Passage à la cible :** [roadmap proposée](13-roadmap-demo.md) et ses critères de sortie.
4. **Compréhension :** [guide pédagogique](14-guide-pedagogique.md), avec labels EXISTANT/CIBLE.
5. **Historique et détails à réconcilier :** autres sections historiques et ressources de conception ; elles ne réintroduisent pas une exigence contredite par l'interview.

Une réponse partielle n'approuve pas l'ensemble des détails. Une cible n'est pas une livraison. Un test de contrat ou un double OpenAI n'est pas une exécution réelle de la boucle.

Le projet est désormais un dépôt Git contenant une pile Docker Compose. Le démarrage réel est `docker compose up -d --build`.

Les documents distinguent :
- **ACQUIS** : exigence explicite de Kevin ;
- **PROPOSÉ** : recommandation à discuter, non approuvée ;
- **OUVERT** : arbitrage nécessaire ;
- **VÉRIFIÉ** : constat étayé par une source consultée, pas une décision métier.

## Documents

| Document | Rôle |
|---|---|
| [Revue fournie](archi-review.md) | Source initiale, conservée sans modification |
| [Vision et exigences](00-vision-et-exigences.md) | Cible actuelle validée, simplification et points ouverts |
| [Lecture critique](01-lecture-critique.md) | Apports, limites et implications de la revue |
| [Projet de référence](02-stack-de-reference.md) | Stack réellement inspectée et adaptations nécessaires |
| [Domaine et données — propositions](03-domaine-et-donnees.md) | Modèle et catalogue historiques, à réduire au scénario actuel |
| [Décisions et questions](04-registre-des-decisions.md) | Réponses N01–N18 du 2026-10-31 ; historique conservé |
| [Boucle hybride et règles](05-boucle-hybride-et-regles.md) | Contrat de diagnostic, options de langage, validation et publication runtime |
| [DDD et cycles de vie](06-domaine-ddd-et-cycles-de-vie.md) | Contextes, agrégats, invariants et modèles de données |
| [Cas d'usage et UX](07-cas-usage-et-ux.md) | Parcours métier, dossier à trois panneaux et mode démonstration |
| [Architecture et contrats](08-architecture-et-contrats.md) | Conteneurs, REST/Kafka/SSE, transactions, annulation et reprise |
| [Données, évaluation et reset](09-donnees-evaluation-et-reset.md) | Jeu 2 + 4 + 4 et reset cible ; ancien profil archivé |
| [Stack et vérifications](10-stack-cible-et-verifications.md) | Stack conservée ; matrice candidate historique non reconfirmée |
| [Contrat des faits et DMN](11-contrat-faits-et-dmn.md) | Données d'entrée, FEEL autorisé, conclusions et validation |
| **[Audit actuel](12-etat-implementation-actuel.md)** | **Constats étayés, tests exécutés, écarts priorisés et critique de l'ancien framing** |
| **[Roadmap](13-roadmap-demo.md)** | **Incréments M0–M8 et recette T01–T10** |
| **[Guide pédagogique](14-guide-pedagogique.md)** | **Traitements, technologies, rôles, preuves, runtime, erreurs et exploitation** |

## Méthode de travail

1. Définir le domaine, le sens d'un écart et les données observables.
2. Définir la règle exécutable, les limites de généralisation et la validation humaine.
3. Définir les cas d'usage, écrans et interactions du chat expert.
4. Définir les frontières DDD, objets, cycles de vie et contrats sync/async.
5. Fixer les versions, licences, critères de preuve, tests et réinitialisation.
6. Effectuer une revue finale bornée et recueillir une validation explicite du cadrage.

Les choix sont recueillis par questionnaires interactifs, par vagues cohérentes. Les recommandations ne deviennent jamais implicitement des décisions.

## Livrables rédigés, à relire avant gel du cadrage

- Spécification des cas d'usage et de l'UX/UI, avec parcours nominaux et erreurs.
- Modèle de domaine, invariants, agrégats et diagrammes de cycles de vie.
- Spécification des règles, changements de référentiel et protocole de validation.
- Architecture, contrats API/événements, persistance et reprise sur panne.
- Catalogue des scénarios, protocole d'évaluation et démonstration pédagogique.
- Stack cible vérifiée, périmètre des licences et exploitation locale/reset.

Le projet applicatif existe. La passe du 2026-10-31 a réalisé interview, inspection du code et du framing, tests hors ligne, build Vue et consultations REST, puis révision documentaire. **Pas de modification de code applicatif, reset ni inférence payante pendant cette passe.** Les détails historiques ne sont pas implicitement réapprouvés.

## Ordre de lecture conseillé

1. `00` puis `04` : vérifier la fidélité de la cible aux réponses.
2. `12` : comprendre ce qui fonctionne, ce qui manque et ce qui est trompeur.
3. `09` : visualiser les dix dossiers et les deux acquisitions de règle.
4. `13` : examiner l'ordre des corrections et les critères de sortie.
5. `14` : comprendre les traitements et les technologies.
6. `05`, `07`, `11`, puis la conception : préparer l'atelier FEEL et les détails de réalisation sans réintroduire l'ancien périmètre.

`01` et `02` documentent la lecture critique et la comparaison avec le projet de référence. Les propositions initiales de `03` sont précisées par `06`, `09` et `11` ; en cas d'évolution, le registre des décisions fait foi sur les choix explicites.

## Réserves avant réalisation

- Pas de garantie générale de causalité ou de non-régression ; tests et replay sur un domaine délimité.
- Compatibilité réelle Java/KIE/Python et restrictions FEEL à éprouver avant développement massif.
- Les résultats exacts d'un LLM ne sont pas garantis, même avec un scénario de données reproductible.
- Bootstrap d'une seule règle couvrant deux dossiers ; création/révision agent uniquement ; retrait Admin historisé.
- Les commandes d'exploitation décrites sont des contrats à implémenter, pas des exécutables déjà disponibles.

