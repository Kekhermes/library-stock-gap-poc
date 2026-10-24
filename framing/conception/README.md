# Ressources de conception — library-stock-gap-poc

Dossier destiné à la réalisation du projet par Kevin avec ses outils. Il contient des spécifications concrètes et des ressources testables, pas une application commencée implicitement.

## Organisation

- [Conventions partagées](00-conventions.md) : types, identités, temps, données sources et messages.
- [Décisions de détail](01-decisions-de-detail.md) : précisions apportées au cadrage.
- `model/` : modèle de données, SQL de référence, dictionnaire et transactions.
- `contracts/` : REST/OpenAPI, Kafka/AsyncAPI, JSON Schema, SSE et exemples.
- `seed/` et `data/` : générateur Java, données publiques et oracles/catalogues de test isolés.
- [Règle DMN initiale](rules/README.md) : R001 réelle et adaptation des faits.
- [Agents et prompts](agents/README.md) : responsabilités, outils, limites et templates.
- [Spécification Vue](ui/README.md) : écrans, composants et états.
- [Guide de réalisation](implementation/README.md) : incréments et gates.
- `verification/` : contrôles exécutables et scénarios d'acceptation.

## Périmètre

Une seule règle initiale ; dix familles de désynchronisation ; 1 200 exemplaires et 120 cas dans le profil demo, avec jeux de validation/holdout séparés. Les labels cachés servent uniquement à l'évaluation, jamais aux outils d'investigation.

Les éléments détaillés de conception sont proposés pour la réalisation et doivent être réconciliés lors d'une évolution de contrat. L'autorisation de produire/publier ce dossier n'est pas une autorisation de démarrer des services applicatifs, payer des inférences ou réinitialiser des données existantes.

## Vérifications

Le DMN R001 a été réellement exécuté dans Apache KIE 10.2.0 : huit cas positifs/négatifs/nulls ont passé. Les autres résultats et commandes de reproduction seront consolidés après la vérification croisée finale.
