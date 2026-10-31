# library-stock-gap-poc

POC pédagogique d'analyse hybride des écarts de stock d'une bibliothèque : règles DMN en premier, investigation OpenAI en fallback, puis capitalisation en nouvelles règles au runtime.

Java/Spring Boot possède les états, snapshots, validations et publications. Python consulte les preuves et propose diagnostics/règles sans accès SQL libre ni droit de publication. Vue présente les dossiers, règles et échanges.

## État et cible

L'[audit du 2026-10-31](framing/12-etat-implementation-actuel.md) distingue le code livré de la cible. L'investigation et la synthèse XML DMN possèdent des appels OpenAI réels ; **Discussion Expert n'appelle pas encore de LLM**, et les métriques de tests/replay des propositions sont encore synthétiques. Le jeu actuel contient 120 dossiers, pas les dix de la nouvelle démo.

La [cible révisée](framing/00-vision-et-exigences.md) prévoit dix dossiers vierges **2 + 4 + 4**, deux agents et un chat réels, règles générées en FEEL et activées sans redémarrage. Une règle apprise sur un dossier doit reconnaître les trois autres analogues sans LLM. Voir la [roadmap](framing/13-roadmap-demo.md) et le [guide pédagogique](framing/14-guide-pedagogique.md).

## Démarrage

Prérequis : Docker Engine et le plugin Compose. La clé OpenAI est requise pour une analyse.

```bash
cp .env.example .env
# renseigner OPENAI_API_KEY dans .env
# OPENAI_MODEL est configurable ; vérifier l'accès au modèle choisi
docker compose up -d --build
```

| Service | URL locale |
|---|---|
| Interface Vue | http://localhost:3000 |
| API Admin | http://localhost:8080/api |
| Santé | http://localhost:8080/actuator/health |
| Console Redpanda | http://localhost:8085 |
| PostgreSQL | `127.0.0.1:5433` |
| Kafka | `127.0.0.1:19092` |

L'analyse depuis un dossier fige les preuves et évalue les règles DMN actives. Si elles couvrent suffisamment le dossier, aucun appel OpenAI n'est requis. Sinon, Java crée une mission Kafka ; le worker la réclame, appelle l'outil `get_case_snapshot` et demande un diagnostic JSON structuré, contrôlé côté Python puis Java.

L'acceptation d'un diagnostic agentique `SUPPORTED` déclenche actuellement une synthèse GPT de XML DMN. L'approbation publie une nouvelle version de règles en base, lue au runtime. Les limites de validation et de dialogue décrites dans l'audit empêchent de présenter ce parcours comme une démo complète déjà fiabilisée.

## Sécurité et fiabilité

- La clé OpenAI reste dans `.env`, exclu de Git.
- Le worker ne peut ni exécuter du shell, ni interroger PostgreSQL, ni corriger le stock.
- Les sorties GPT doivent citer uniquement des symptômes et identifiants de preuve présents dans le snapshot ; toute référence inventée est rejetée.
- Une erreur d'investigation est visible et relançable ; la reprise de synthèse/chat reste à compléter.
- Le diagnostic est une aide probabiliste : l'acceptation humaine ne déclenche aucune correction automatique.
- Les doubles OpenAI des tests hors ligne ne sont pas des résultats de démonstration. Les appels réels restent variables ; aucun échec ne doit être masqué par un résultat préfabriqué.

## Vérifications

```bash
mvn -q -f backend/pom.xml test
cd agents && OPENAI_API_KEY=offline-audit-placeholder .venv/bin/python -m unittest -v test_worker
cd .. && npm --prefix frontend run build
```

Ces vérifications hors ligne ne prouvent pas l'accès fournisseur ni les trois usages LLM. Un essai réel doit être convenu avant les appels payants ; aucun plafond de consommation n'est fixé pour l'instant, conformément à l'interview.

Voir le [cadrage consolidé](framing/README.md).
