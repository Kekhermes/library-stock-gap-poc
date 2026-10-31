# Ressources de conception — library-stock-gap-poc

Dossier de ressources issues du premier cadrage, distinct du code applicatif désormais présent à la racine. Il contient des spécifications et fixtures testables, pas une preuve de fonctionnement du produit.

## Réconciliation nécessaire — 2026-10-31

L'[interview et la vision actuelles](../00-vision-et-exigences.md) et la [roadmap](../13-roadmap-demo.md) prévalent sur les détails incompatibles de ce dossier. Le jeu cible est dix dossiers **2 + 4 + 4**, tous vierges après reset ; les anciennes fixtures 120 dossiers/dix familles ne doivent pas être régénérées comme cible par défaut.

Les rôles LLM restent réels, la génération attendue porte sur les conditions FEEL avec sérialisation DMN Java, et Discussion Expert doit être branchée. Les anciens budgets/SDK et les contrats proposés ne sont pas reconfirmés implicitement. Adapter schémas, exemples, prompts, données et tests ensemble au jalon correspondant, sans les assouplir pour masquer un écart.

Le [guide pédagogique](../14-guide-pedagogique.md) distingue EXISTANT/CIBLE. Les résultats de conception historiques ci-dessous n'attestent ni les endpoints HTTP actuels ni la boucle réelle OpenAI.

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
