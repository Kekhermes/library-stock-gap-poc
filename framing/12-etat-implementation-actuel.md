# État d’implémentation actuel — 2026-10-24

Ce document met à jour le cadrage v0.1 restauré. Les fichiers `00` à `11` conservent les décisions, hypothèses et objectifs d’origine ; lorsqu’ils divergent de ce document, **cet état d’implémentation prévaut pour décrire le POC réellement livré**.

## 1. Réalisé et vérifié

Le dépôt contient une pile locale Docker Compose : Spring Boot/Java 21, PostgreSQL, Redpanda `v24.3.1`, Redpanda Console `v3.13.1`, frontend Vue/Vite et worker Python GPT.

Les contrôles exécutés sur le code courant sont :

- `backend`: `mvn test -q` ;
- `agents`: `./.venv/bin/python -m unittest -v test_worker` ;
- `frontend`: `npm run build` ;
- E2E local : commande d’analyse → DMN → fallback Kafka/GPT lorsque requis → diagnostic persistant.

Le POC est un dépôt Git et son démarrage réel est :

```bash
docker compose up -d --build
```

La santé backend est disponible sur `http://127.0.0.1:8080/actuator/health` et l’interface sur `http://127.0.0.1:3000`.

## 2. Cascade hybride réellement exécutée

L’analyse suit désormais ce flux :

```text
Commande Admin
  → snapshot de preuves figé
  → règles DMN actives (Apache KIE)
      → couverture complète : diagnostic DMN, état A_REVOIR
      → aucune règle/couverture partielle : AGENT_PENDING
           → mission Kafka InvestigationRequested
           → worker Python GPT + get_case_snapshot
           → diagnostic agentique validé ou état FAILED
```

Un second clic n’est pas nécessaire pour lancer GPT. Une nouvelle analyse annule l’analyse et la mission actives du même dossier avant de créer un nouveau snapshot.

Les seuls motifs de fallback déterministe actuellement produits sont `NO_RULE_MATCH` et `PARTIAL_COVERAGE`. La combinaison de conclusions compatibles, la détection de conflits inter-règles et les motifs plus riches du cadrage restent à réaliser.

## 3. UX actuelle

Le dossier affiche explicitement :

- **Évaluation déterministe en cours** ;
- **Investigation agentique en cours** quand DMN n’a pas complètement couvert le dossier, avec le motif de fallback ;
- **Investigation agentique en échec** uniquement si la cascade a bien démarré mais que GPT ou la validation a échoué.

Les routes réellement exposées sont : vue d’ensemble, dossiers, catalogue/détail des règles et système/reset. L’interface actualise le dossier toutes les trois secondes ; la connexion SSE est affichée au niveau application.

Les écrans de propositions, publications et démonstration guidée décrits dans `07` ne sont pas branchés au routeur actuel.

## 4. Agent GPT et frontières de sécurité

Le worker utilise le client Python OpenAI et la **Responses API**, pas OpenAI Agents SDK.

Il n’a pas de connexion SQL. Pour une investigation il doit appeler une fois `get_case_snapshot`, puis rendre un JSON Schema strict. Python et Java refusent une sortie qui cible un autre exemplaire, cite un symptôme absent, ou référence une preuve absente/issue d’un autre snapshot.

La lecture du snapshot global exige le jeton worker, une mission active, un lease non expiré et le snapshot associé à la mission. L’adoption Kafka vérifie aussi mission, analyse, snapshot, lease et fencing token.

Limites actuelles : les endpoints internes spécialisés (événements, prêts, retours, transferts, notes et règles) ne portent pas encore la même vérification fine de mission/lease. Le token par défaut de développement doit être remplacé avant tout usage hors poste local. Les quotas enregistrés en base (300 s, 8 appels LLM, 16 outils, 32k tokens entrée, 8k sortie) ne sont pas encore imposés durant l’appel.

## 5. Règles et gouvernance : niveau réellement livré

Les règles actives sont versionnées et le catalogue/détail est consultable. Les analyses enregistrent la version du référentiel utilisée. Le retrait des règles non `R001` est exposé dans l’UI.

En revanche, le worker actuel ne traite que `InvestigationRequested` : il ne réalise ni synthèse de règle, ni révision, ni chat agentique. Les diagnostics acceptés ne déclenchent donc pas encore de vraie mission `RuleSynthesisRequested`.

La prévalidation actuelle compile le DMN mais n’exécute pas le corpus de replay/généralisation décrit dans le cadrage. Les métriques de validation sont synthétiques ; une publication ne vérifie pas encore suffisamment le lien rapport/proposition/révision. Les documents `05`, `06`, `07`, `09` et `11` restent la cible fonctionnelle, pas une affirmation de livraison complète.

## 6. Données, reset et opérations

Le dataset démo est importé au démarrage. Un reset existe via `POST /api/system/reset`, mais les commandes `./dev up|status|reset|verify-reset` du cadrage ne sont pas implémentées. Il n’existe pas encore de vérification automatisée complète du reset, du broker, des corpus de validation ou des jeux holdout.

## 7. Versions réellement présentes

| Composant | POC courant |
|---|---|
| Java | 21 |
| Spring Boot | 3.4.2 |
| KIE DMN | 10.2.0 |
| Python | 3.11 |
| OpenAI | client Python / Responses API |
| Vite | 6.4.5 |
| Redpanda | 24.3.1 |
| Redpanda Console | 3.13.1 |

Les versions candidates plus récentes citées dans `10` sont historiques et ne décrivent pas la pile exécutée.
