# Guide de réalisation avec tes outils

## Priorité de lecture — révision du 2026-10-31

Ce guide est historique. L'application existe désormais ; suivre [la vision actuelle](../../00-vision-et-exigences.md), [l'audit](../../12-etat-implementation-actuel.md) et [la roadmap M0–M8](../../13-roadmap-demo.md), pas une nouvelle reconstruction du socle.

Préserver les décisions actuelles : dix dossiers vierges 2 + 4 + 4, deux agents et chat OpenAI réels, conditions FEEL générées et sérialisation DMN Java, vrais tests, publication runtime, mesures sans plafond de consommation imposé. Les lots/dix familles, les anciens quotas et Agents SDK ne sont pas des obligations reconfirmées. Les incréments ci-dessous sont des ressources à réconcilier, pas la roadmap actuelle.

## Ce dossier est une entrée de travail, pas un projet déjà développé

Tu peux transmettre la racine `framing/conception/` à un outil de code. Les contrats et fixtures y sont concrets ; les snippets de transactions restent à implémenter. La conception ne dispense pas des tests d'intégration du futur backend.

**Ne transmets jamais automatiquement à un agent runtime les dossiers privés d'oracle, de catalogue ou de holdout.** Ils servent aux développeurs et à l'évaluation, pas au diagnostic en production du POC.

## Découpage conseillé en incréments vérifiables

| Incrément | Entrées de conception | Résultat à obtenir | Gate |
|---|---|---|---|
| 1. Socle local | Stack du cadrage, conventions, modèle SQL | Java/Spring, Vue, Python worker, PostgreSQL, Redpanda, réseau localhost | Build, santé des services et aucun secret embarqué |
| 2. Import des sources | Export Java et dictionnaire JSON→SQL | Jeu demo chargé, epoch créée, exactement R001 active | Compteurs, FK, contrôle d'oracle inaccessible, reset reproduisible |
| 3. Analyse déterministe | DMN réel, input schema, sorties et snapshots | Cas simple évalué avec preuve/version | Cas nominal, nulls, observation contradictoire, couverture partielle |
| 4. Orchestration durable | Contrats Kafka, états, outbox/inbox, leases | Mission roundtrip avec worker factice explicitement de test | Doublons, crash, tentative périmée, annulation, reset |
| 5. Investigateur réel | Prompt et outils de lecture | Fallback OpenAI sur un cas réel du dataset | Autorisation explicite de coût, références vérifiées, abstention |
| 6. Agent de règles | Prompt, proposition DMN, prévalidation | Création/révision et non-proposition explicite | Tests, négatifs, replay, aucun droit de publication dans Python |
| 7. Publication runtime | Transactions modèle, API de revue | Acceptation diagnostic puis publication atomique | Cas suivant sur nouvelle version ; analyse en cours inchangée |
| 8. UX | Parcours, contrats REST/SSE, états | Dossier trois panneaux, règles readonly, chat, lots | Tests navigateur, reconnexion, conflits et accessibilité |
| 9. Démonstration et reset | Catalogue expert, Gherkin, manifestes | Démo réelle et retour initial en une commande | Une règle après reset, même dataset, nouvelle epoch, historique effacé proprement |

Le worker factice du gate 4 est un outil de test, pas une alternative au vrai modèle pour la démonstration. Il ne doit jamais être présenté comme une investigation OpenAI.

## Dépendances d'architecture à conserver

```text
Vue -> API Java -> application/domain -> ports -> adapters
                                      -> persistence PostgreSQL
                                      -> DMN/KIE
                                      -> Kafka outbox/inbox
Python -> outils internes Java + OpenAI
       -> résultats Kafka, sans pouvoir de publication
```

Éviter de déplacer en frontend l'évaluation des conditions, les calculs de couverture, les vérifications de contradictions ou les transitions d'approbation. Le générateur Java autonome livré est une référence de fixtures ; tu peux le porter dans un job de seed sans copier un moteur de diagnostic dans son oracle.

## Contrats à traiter comme un ensemble

Une modification de champ requiert, selon son impact :
1. JSON Schema et exemples ;
2. mapping SQL/import et dictionnaire ;
3. OpenAPI ou AsyncAPI/SSE ;
4. code producteur/consommateur et tests contractuels ;
5. adaptateur des faits DMN ;
6. version du dataset et manifeste si la fixture change.

Ne pas faire fonctionner un test en assouplissant silencieusement `additionalProperties` ou en retirant un champ requis. Si un contrat est trop contraignant, documenter la correction et mettre à jour les deux côtés.

## Consigne prête à transmettre à un outil de développement

> Lis `framing/00-vision-et-exigences.md`, le registre des décisions, puis `framing/conception/README.md` et les conventions. Réalise seulement l'incrément demandé. Préserve Java comme autorité métier, les deux rôles IA Python, DMN/KIE, l'unique règle initiale, l'absence d'édition manuelle et l'isolation de l'oracle. Utilise les schémas/exemples et les données fournis ; ne remplace pas les résultats attendus par des simulations non annoncées. Écris et exécute les tests, puis indique ce qui est réellement vérifié et ce qui ne l'est pas. Ne lance aucun appel payant ni reset destructif sans périmètre autorisé.

## Tests qui ne doivent pas être substitués les uns aux autres

- Parsing SQL ≠ exécution d'une migration et validation de ses contraintes.
- Validation JSON Schema ≠ compatibilité Kafka en conditions de panne.
- Modèle DMN compilable ≠ sécurité générale de toutes les expressions FEEL générées.
- Fixture déterministe ≠ diagnostic LLM déterministe.
- Source de données cohérente ≠ cause identifiable à partir des preuves visibles.
- Compteurs sans appel LLM ≠ qualité diagnostique.
- Build frontend ≠ parcours utilisateur accessible.

## Risques à traiter tôt

1. Cohérence entre sources, snapshots et temps de référence.
2. Contexte des références de preuve et portée des outils.
3. Expressivité FEEL utile sans extension Java libre.
4. Sémantique de conflits et couverture multi-symptômes.
5. Publications concurrentes, tentatives périmées et SSE après reset.
6. Fuites par l'oracle, noms de scénario, exemples privés ou outils de développement accessibles au runtime.

## Réinitialisation à réaliser dans l'application

Le générateur peut être rejoué sans toucher à une base existante. Le futur reset de l'application doit arrêter son propre worker, isoler son projet Compose, renouveler epoch et topics, recharger les seules sources runtime, puis vérifier R001 unique. Il ne doit pas effacer `.env` ni utiliser un prune Docker global.

Les scripts de conception fournis ne sont pas ce reset applicatif : ils produisent ou contrôlent des fichiers, sans prétendre réinitialiser un produit qui n'est pas encore implémenté.
