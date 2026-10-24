# library-stock-gap-poc

POC pédagogique d'analyse des désynchronisations de stock d'une bibliothèque après inventaire.

**Le cadrage est dans [`framing/`](framing/README.md) ; l'application est implémentée et exécutable localement** (Java/Spring Boot + Apache KIE DMN, worker Python, Vue, PostgreSQL, Redpanda).

## Principe

```text
Cas d'écart → diagnostic déterministe DMN
                 ├─ suffisant → résultat expliqué, sans LLM
                 └─ insuffisant → investigation IA → diagnostic accepté
                                      → proposition de règle → tests/replay
                                      → validation humaine → publication runtime
                                      → prochain cas traité par le déterministe
```

## Démarrage

Prérequis : Docker Engine + plugin Compose.

```bash
cp .env.example .env          # facultatif : aucune clé n'est requise pour la démo
docker compose up -d --build  # postgres, redpanda, backend, agents, frontend
```

| Service | URL locale |
|---|---|
| Interface Vue | http://localhost:3000 |
| API Admin | http://localhost:8080/api |
| Santé | http://localhost:8080/actuator/health |
| PostgreSQL | `127.0.0.1:5433` (base `stockgap`) |
| Kafka (Redpanda) | `127.0.0.1:19092` |

Le premier démarrage migre le schéma, importe le jeu `demo` (1 200 exemplaires, 120 cas) et active le bootstrap : **une seule règle, R001 v1**.

## Démonstration vérifiée

```bash
./scripts/demo-loop.sh
```

Le script exécute et **vérifie** la boucle complète (8 assertions) :

1. bootstrap : référentiel v1, exactement R001 active ;
2. cas A non couvert → fallback vers l'investigateur (diagnostic non déterministe) ;
3. acceptation humaine du diagnostic ;
4. synthèse agentique d'une règle DMN ;
5. validation (compilation + replay) → rapport PASSED ;
6. publication atomique → référentiel **v2**, 2 règles actives ;
7. cas B analogue → diagnostic **déterministe**, **0 appel LLM**.

Exécution réelle du 2026-10-24 : **8 OK / 0 FAIL**.

## Architecture

| Service | Responsabilité |
|---|---|
| `backend/` | REST/SSE, domaine, cycles de vie, DMN/KIE, validation et publication atomique. Seule autorité métier. |
| `agents/` | Worker Kafka : investigateur et agent de règles. Aucun accès SQL, aucun droit de publication. |
| `frontend/` | Vue 3 + Vite + Pinia : adaptateur REST/SSE, aucun moteur de diagnostic. |
| `postgres` | Schémas `runtime` (métier) et `evaluation` (isolé, hors accès runtime). |
| `redpanda` | Missions et résultats asynchrones (`stock-gap.agent.commands.v1` / `.events.v1`). |

Deux surfaces d'API étanches : `/api/**` (Admin local) et `/internal/**` (worker, `WorkerToken` requis ; un jeton worker présenté sur `/api/**` renvoie **403**).

### Écrans

Vue d'ensemble (compteurs, règles actives, flux SSE temps réel) · Dossiers d'écarts filtrables · **Dossier à trois panneaux** (faits / diagnostic et règles / discussion expert) · Catalogue de règles en lecture seule avec retrait · Propositions · Publications · Démonstration guidée · Système & reset.

## Réinitialisation

`POST /api/system/reset` (ou bouton dans *Système & Reset*) crée une **époque neuve** : jeu `demo` réimporté, R001 v1 seule active, ancienne époque archivée. Elle n'efface ni `.env` ni la configuration locale.

## Garanties vérifiées et limites honnêtes

Vérifié par exécution réelle :

- moteur DMN Apache KIE 10.2.0 chargeant et évaluant R001 (8 cas d'acceptation, y compris qualité nulle et non fiable) ;
- couverture partielle détectée → fallback motivé, jamais un diagnostic inventé ;
- aller-retour Kafka de bout en bout (commande → investigation → `DiagnosticProposed`) ;
- acceptation du diagnostic distincte de la publication d'une règle ;
- publication atomique : nouvelle version de référentiel, aucune analyse en cours réécrite ;
- journal SSE durable : rejeu complet depuis un curseur, `id: <epoch>:<position>` ;
- 5 tests backend verts, build frontend vert, pile Compose saine.

Non couvert (assumé, cf. cadrage) :

- aucun contrôle SMT : un rapport PASSED n'est pas une preuve de non-régression universelle ;
- pas d'« exactly once » fournisseur : un crash après appel OpenAI peut répéter une inférence ;
- la synthèse de règles du worker est déterministe et locale ; brancher un modèle réel (`OPENAI_API_KEY`) doit être revalidé avant toute présentation comme résultat LLM ;
- pas de RAG, Keycloak, Valkey ni correction du stock dans le POC.

## Ressources

- [Cadrage et décisions](framing/README.md).
- [Ressources de conception](framing/conception/README.md).
- [Revue initiale et lecture critique](framing/01-lecture-critique.md).

## Données et confidentialité

Les bibliothèques, exemplaires, opérations et cas sont fictifs. Les oracles et catalogues d'évaluation (`framing/conception/data/private/`) restent hors du worker et ne sont jamais injectés dans ses prompts.

Aucune clé OpenAI n'est fournie. `.env` est ignoré par Git ; seules les inférences explicitement demandées transmettraient les données nécessaires au fournisseur. Le générateur de fixtures et la démonstration fonctionnent sans LLM.
