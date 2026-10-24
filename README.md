# library-stock-gap-poc

POC pédagogique d'analyse des désynchronisations de stock d'une bibliothèque après inventaire.

**Ce dépôt contient le cadrage, les ressources de conception et les données synthétiques de référence. L'application n'est pas encore implémentée.**

## Principe

```text
Cas d'écart → diagnostic déterministe DMN
                 ├─ suffisant → résultat expliqué, sans LLM
                 └─ insuffisant → investigation IA → diagnostic accepté
                                      → proposition de règle → tests/replay
                                      → validation humaine → publication runtime
                                      → prochain cas traité par le déterministe
```

- Java/Spring Boot : autorité métier, cycles de vie, DMN/KIE et publication.
- Python/OpenAI Agents SDK : investigateur et agent de règles.
- Vue : interface REST/SSE ; Kafka sur Redpanda pour les missions asynchrones.
- PostgreSQL ; déploiement applicatif futur exclusivement local via Docker Compose.
- Pas de RAG, Keycloak, Valkey ni correction du stock dans le POC.

## Ressources

- [Cadrage et décisions](framing/README.md).
- [Ressources de conception](framing/conception/README.md).
- [Revue initiale et lecture critique](framing/01-lecture-critique.md).

Le détail de la réalisation reste sous le contrôle du porteur du projet. Les ressources autonomes de génération et de validation ne constituent pas un backend/frontend déjà livré.

## Données et confidentialité

Les bibliothèques, exemplaires, opérations et cas sont fictifs. Les oracles et catalogues d'évaluation sont publics pour les développeurs, mais doivent être **inaccessibles aux agents du POC en fonctionnement**. Ils ne doivent pas être montés dans le worker IA ou injectés dans ses prompts.

Aucune clé OpenAI n'est fournie. La future application utilisera un `.env` local ignoré par Git ; seules les inférences demandées transmettront les données nécessaires au fournisseur. Le générateur de fixtures fonctionne sans LLM.

## Statut des garanties

Les vérifications effectuées et leurs limites sont documentées dans le dossier de conception. Un contrat validé, un DMN exécutable ou un jeu de données reproductible ne prouvent pas à eux seuls l'intégration future de l'application ni une vérité causale générale.
