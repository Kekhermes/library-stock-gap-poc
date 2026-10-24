# library-stock-gap-poc

POC d'investigation agentique GPT des écarts de stock d'une bibliothèque après inventaire.

Le diagnostic est produit par le worker Python GPT à partir d'un snapshot complet et figé des données PostgreSQL. Java/Spring Boot reste propriétaire des états, des snapshots et de la persistance ; le worker n'a aucun accès SQL ni droit de modification métier.

## Démarrage

Prérequis : Docker Engine et le plugin Compose. La clé OpenAI est requise pour une analyse.

```bash
cp .env.example .env
# renseigner OPENAI_API_KEY dans .env
# OPENAI_MODEL est conservé tel que configuré
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

Lancez une analyse depuis un dossier. Elle crée une mission Kafka, puis le worker GPT :

1. réclame la mission ;
2. appelle l'outil borné `get_case_snapshot` ;
3. analyse le snapshot de toutes les données agrégées ;
4. retourne un diagnostic JSON structuré ;
5. est validé côté Python et Java avant persistance.

## Sécurité et fiabilité

- La clé OpenAI reste dans `.env`, exclu de Git.
- Le worker ne peut ni exécuter du shell, ni interroger PostgreSQL, ni corriger le stock.
- Les sorties GPT doivent citer uniquement des symptômes et identifiants de preuve présents dans le snapshot ; toute référence inventée est rejetée.
- Une erreur d'API, de modèle ou de validation met l'analyse en échec de manière visible et permet la relance.
- Le diagnostic est une aide probabiliste : l'acceptation humaine ne déclenche aucune correction automatique.

## Vérifications

```bash
cd agents && .venv/bin/python -m unittest -v test_worker
cd ../backend && mvn test
cd ../frontend && npm run build
```

Voir le [cadrage agentique](framing/README.md).
