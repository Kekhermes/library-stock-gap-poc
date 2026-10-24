# Cadrage — agent GPT unique

## Décision

Le POC exécute une analyse **agentique GPT pour chaque dossier**. La démo et la cascade de diagnostic déterministe ont été supprimées. Le worker Python est le seul composant qui produit un diagnostic ; Java reste l'autorité des états métier et PostgreSQL conserve les preuves figées.

## Flux exécuté

```text
Admin → POST analyse → Java fige le snapshot BDD → mission Kafka
      → worker Python GPT → appel d'outil get_case_snapshot
      → sortie JSON structurée → validation locale des références
      → DiagnosticProposed → Java persiste le diagnostic ou marque l'analyse FAILED
```

## Garanties

- Le worker n'a aucun accès SQL ni droit de modification du stock.
- GPT reçoit les données uniquement après l'appel d'outil borné `get_case_snapshot`.
- Les notes opérateur et les données de preuve sont traitées comme des données non fiables, jamais comme des instructions.
- La sortie est contrainte par un JSON Schema Pydantic et rejetée si elle cite un symptôme, un exemplaire, un snapshot ou une preuve absente.
- En cas d'erreur de modèle, d'API ou de validation, la mission et l'analyse passent à `FAILED`; le dossier peut être relancé.
- La clé reste uniquement dans `.env`, ignoré par Git. Ne jamais la mettre dans un prompt, un log ou un commit.

## Configuration

```dotenv
OPENAI_API_KEY=...
OPENAI_MODEL=gpt-5.6-luna
```

`OPENAI_MODEL` est transmis littéralement au SDK OpenAI. Le modèle est vérifié par une requête minimale avant toute démonstration réelle.

## Limites assumées

Le diagnostic est probabiliste : une conclusion ne devient pas une correction automatique du stock. L'admin conserve l'acceptation explicite. Le POC ne comporte ni RAG, ni accès shell, ni écriture libre, ni génération/publication de règles.
