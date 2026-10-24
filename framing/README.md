# Cadrage — library-stock-gap-poc

## Statut

**Cadrage v0.1 restauré, avec implémentation hybride en cours.**

Les documents historiques conservent les décisions, hypothèses et cibles initiales. L’état factuel du POC livré (cascade DMN → fallback GPT, sécurité, UX, limites et versions) est documenté dans **[l’état d’implémentation actuel](12-etat-implementation-actuel.md)** et prévaut en cas de divergence.

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
| [Vision et exigences](00-vision-et-exigences.md) | Périmètre acquis, exclusions, critères proposés |
| [Lecture critique](01-lecture-critique.md) | Apports, limites et implications de la revue |
| [Projet de référence](02-stack-de-reference.md) | Stack réellement inspectée et adaptations nécessaires |
| [Domaine et données — propositions](03-domaine-et-donnees.md) | Modèle métier, anomalies et génération non triviale |
| [Décisions et questions](04-registre-des-decisions.md) | Arbitrages ouverts et séquence des ateliers |
| [Boucle hybride et règles](05-boucle-hybride-et-regles.md) | Contrat de diagnostic, options de langage, validation et publication runtime |
| [DDD et cycles de vie](06-domaine-ddd-et-cycles-de-vie.md) | Contextes, agrégats, invariants et modèles de données |
| [Cas d'usage et UX](07-cas-usage-et-ux.md) | Parcours métier, dossier à trois panneaux et mode démonstration |
| [Architecture et contrats](08-architecture-et-contrats.md) | Conteneurs, REST/Kafka/SSE, transactions, annulation et reprise |
| [Données, évaluation et reset](09-donnees-evaluation-et-reset.md) | Jeu initial, unique règle, critères de preuve, quotas et remise à zéro |
| [Stack cible vérifiée documentairement](10-stack-cible-et-verifications.md) | Versions candidates, licences et gates de compatibilité |
| [Contrat des faits et DMN](11-contrat-faits-et-dmn.md) | Données d'entrée, FEEL autorisé, conclusions et validation |
| **[État d’implémentation actuel](12-etat-implementation-actuel.md)** | **Comportement réellement livré, sécurité, UX, versions et écarts restants** |

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

Ces livrables sont rédigés et intègrent les arbitrages déjà exprimés. Les choix détaillés signalés PROPOSÉS restent soumis à la revue finale. Aucun code applicatif, dépôt Git ou service Docker n'a été créé ; aucune inférence payante n'a été effectuée.

## Ordre de lecture conseillé

1. `00` : vérifier l'intention, le périmètre et les exclusions.
2. `07` : se projeter dans les parcours et les écrans.
3. `05` puis `11` : examiner la boucle d'apprentissage et ce qu'une règle peut réellement exprimer.
4. `06` puis `08` : discuter objets métier, cycles de vie et échanges.
5. `09` puis `10` : examiner les preuves, le reset, les limites et la stack.
6. `04` : contrôler la fidélité des décisions enregistrées et les points encore proposés.

`01` et `02` documentent la lecture critique et la comparaison avec le projet de référence. Les propositions initiales de `03` sont précisées par `06`, `09` et `11` ; en cas d'évolution, le registre des décisions fait foi sur les choix explicites.

## Réserves avant réalisation

- Pas de garantie générale de causalité ou de non-régression ; tests et replay sur un domaine délimité.
- Compatibilité réelle Java/KIE/Python et restrictions FEEL à éprouver avant développement massif.
- Les résultats exacts d'un LLM ne sont pas garantis, même avec un scénario de données reproductible.
- Bootstrap d'une seule règle ; création/révision agent uniquement ; retrait Admin historisé.
- Les commandes d'exploitation décrites sont des contrats à implémenter, pas des exécutables déjà disponibles.

