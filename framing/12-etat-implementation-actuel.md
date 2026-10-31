# Audit du projet — 2026-10-31

## Conclusion

Le projet n'est plus intégralement mocké : **deux chemins OpenAI réels existent dans le code**, investigation et synthèse XML DMN. En revanche, **Discussion Expert n'appelle aucun LLM**, l'investigation n'explore pas successivement les sources, et les tests de règles présentés comme réussis sont synthétiques. La boucle démonstrative fiable demandée n'est donc pas établie.

La gestion runtime existe déjà : règles et publications en PostgreSQL, pointeur actif, chargement DMN depuis les versions actives. Il faut conserver ce socle et sécuriser ses contrôles, pas confondre ses lacunes avec une absence totale de DMN dynamique.

Cet audit décrit l'existant. [La vision](00-vision-et-exigences.md) décrit l'attendu ; [la roadmap](13-roadmap-demo.md) organise le passage de l'un à l'autre. Aucune fonctionnalité applicative n'a été corrigée pendant cette passe documentaire.

## Méthode et preuves exécutées

Inspection des chemins Python, services métier Java, persistance, consommateurs Kafka, API, vues Vue et documents de cadrage/conception. Consultations REST en lecture seule sur l'instance déjà lancée ; pas de reset, nouvelle analyse, publication ni appel OpenAI payant.

| Vérification exécutée | Résultat observé | Ce qu'elle ne prouve pas |
|---|---|---|
| `mvn -q -f backend/pom.xml test` | 11 tests, zéro échec/erreur, quatre classes de tests | Pas de recette PostgreSQL/Kafka/publication/chat de bout en bout |
| Tests `agents/test_worker.py` dans le venv existant | 4 tests réussis ; OpenAI remplacé par des doubles | Pas d'inférence fournisseur, de synthèse de règles ou de dialogue réel |
| `npm --prefix frontend run build` | Build réussi, Vite 6.4.5 | Pas de preuve de parcours navigateur ni d'ergonomie |
| `GET /actuator/health` | HTTP 200, `UP`, base connectée | Pas de santé fonctionnelle OpenAI ni de roundtrip Kafka |
| `GET /api/overview`, règles, publications et dossiers | 120 dossiers, 110 à analyser ; R001 seule active ; publication bootstrap v1 | Pas de preuve d'une acquisition de règle dans l'époque actuelle |
| Lecture JSON du seed applicatif | 1 200 exemplaires, 120 dossiers, 3 sites, 15 emplacements, 10 796 événements | Pas de conformité au nouveau jeu dix dossiers |

Une valeur factice `OPENAI_API_KEY=offline-audit-placeholder` a uniquement satisfait la précondition des tests Python qui patchent le client. Aucun secret réel n'a été lu ni exposé. Les erreurs KIE dans les logs Java correspondent au test de rejet d'un XML volontairement invalide ; les tests passent.

L'état en cours peut évoluer après cette observation. Les conteneurs et les fichiers source n'ont pas été comparés par empreinte ; les constats de code ne remplacent pas une recette sur une image reconstruite.

## Cartographie fonctionnelle réelle

| Fonction | État | Sources principales |
|---|---|---|
| Cascade DMN puis mission d'investigation | Implémentée ; couverture complète ou fallback | [Orchestration](../backend/src/main/java/com/library/stockgap/domain/service/AnalysisOrchestrationService.java), [déterministe](../backend/src/main/java/com/library/stockgap/domain/service/DeterministicAnalysisService.java) |
| Investigation GPT | Deux appels Responses API, outil dossier de preuves figé forcé, correction de sortie possible | [Worker](../agents/worker.py) |
| Synthèse GPT | Diagnostic accepté `SUPPORTED` → mission → génération XML DMN → prévalidation | [Orchestration](../backend/src/main/java/com/library/stockgap/domain/service/AnalysisOrchestrationService.java), [worker](../agents/worker.py), [consommateur](../backend/src/main/java/com/library/stockgap/infrastructure/kafka/AgentEventConsumer.java) |
| Discussion Expert | Lecture/stockage des messages, sans mission LLM | [API Admin](../backend/src/main/java/com/library/stockgap/web/AdminApiController.java), [dossier Vue](../frontend/src/views/CaseDossierView.vue) |
| Publication runtime | Matérialisation de version, copie du manifeste, mise à jour transactionnelle du pointeur actif | [Référentiel](../backend/src/main/java/com/library/stockgap/infrastructure/persistence/RuleRepository.java), [moteur](../backend/src/main/java/com/library/stockgap/domain/service/DmnEngineService.java) |
| Validation de règle | Compilation DMN réelle, métriques de replay fictives | [Gouvernance](../backend/src/main/java/com/library/stockgap/domain/service/RuleGovernanceService.java) |
| Consultation DMN | Catalogue et XML brut ; pas de table expliquée ni d'essai par condition | [Fiche règle](../frontend/src/views/RuleDetailView.vue) |
| Événements UI | SSE persistant avec replay limité ; dossier rafraîchi par polling | [SSE](../backend/src/main/java/com/library/stockgap/domain/service/SseHubService.java), [store](../frontend/src/stores/stockGapStore.js) |
| Reset | Import du même grand seed dans une nouvelle époque ; recette complète non testée | [Seed](../backend/src/main/java/com/library/stockgap/domain/service/DatasetSeedService.java), [API](../backend/src/main/java/com/library/stockgap/web/AdminApiController.java) |

## Écarts et risques priorisés

### F01 — Critique : validation affichée mais non exécutée

[RuleGovernanceService](../backend/src/main/java/com/library/stockgap/domain/service/RuleGovernanceService.java) fixe cinq positifs, cinq négatifs et dix replays, dont le succès dépend seulement de `validateDmnXml`. Il ne consulte ni n'exécute ces cas. [Le dossier Vue](../frontend/src/views/CaseDossierView.vue) affiche « Tests de replay validés » sur le statut `READY_FOR_APPROVAL`.

Une règle syntaxiquement valide mais métier incorrecte peut donc être approuvée avec un rapport trompeur. Les colonnes d'empreintes du rapport réutilisent également une même valeur au lieu d'identifier distinctement proposition, manifeste et corpus.

### F02 — Critique : conclusions fabriquées dans les faits pré-LLM

[DeterministicAnalysisService.facts](../backend/src/main/java/com/library/stockgap/domain/service/DeterministicAnalysisService.java) injecte `diagnosticConclusion = SUPPORTED` et `acceptedDiagnosticConclusion = SUPPORTED` pour compatibilité avec d'anciennes règles. Ce ne sont pas des faits observés sur un dossier vierge. Le prompt de synthèse et le contrat de faits interdisent pourtant de s'appuyer sur ces champs.

Supprimer ces alias et rejeter/revalider les règles qui les utilisent, au lieu de les faire matcher artificiellement. Une règle doit reconnaître la cause à partir des preuves disponibles avant diagnostic.

### F03 — Bloquant fonctionnel : chat sans agent et exploration absente

[sendMessage](../backend/src/main/java/com/library/stockgap/web/AdminApiController.java) enregistre un message ; le worker ne traite que `InvestigationRequested` et `RuleSynthesisRequested`. Aucun tour expert/révision conversationnelle n'est déclenché. Les handlers Java de texte ne suffisent pas sans producteur réel.

L'investigateur a un unique outil obligatoire `get_case_snapshot`, appelé une fois. Les endpoints de sources existent mais ne sont pas enregistrés comme outils LLM. Cela ne satisfait pas les consultations successives choisies pendant l'interview.

### F04 — Élevé : synthèse insuffisamment ancrée dans le diagnostic accepté

[acceptDiagnostic](../backend/src/main/java/com/library/stockgap/domain/service/AnalysisOrchestrationService.java) transmet des identifiants et la conclusion globale, pas les findings, assertions et références du diagnostic accepté. Le worker relit un dossier de preuves figé, puis demande au LLM le XML entier.

La cible exige le diagnostic complet accepté, son contexte de preuves, la proposition/règle discutée et les conditions FEEL produites par le LLM. Java doit sérialiser la structure, pas choisir la causalité.

### F05 — Élevé : publication insuffisamment liée au contenu et à la base testés

L'[endpoint de validation](../backend/src/main/java/com/library/stockgap/web/AdminApiController.java) reçoit le XML depuis le navigateur au lieu de charger exclusivement la révision persistée. L'approbation sélectionne un rapport `PASS` pour proposition/révision, mais [publishProposal](../backend/src/main/java/com/library/stockgap/infrastructure/persistence/RuleRepository.java) ne vérifie pas l'ensemble des empreintes, le statut attendu et la version de base.

La base est lue sans bloquer une proposition devenue périmée ; la publication copie les membres courants sans nouveau replay sur ceux-ci. L'incrément de `lock_version` n'est pas, à lui seul, un contrôle de concurrence. La compilation isolée ne prouve pas que le manifeste complet est évaluable.

### F06 — Élevé : suffisance, qualité et erreurs DMN trop permissives

[facts](../backend/src/main/java/com/library/stockgap/domain/service/DeterministicAnalysisService.java) prend le premier symptôme et le premier scan ; qualité `VALID` ne garantit pas unicité/cohérence. Les faits de dernière mise à jour du stock informatique ne réalisent pas une corrélation explicite avec l'opération métier à expliquer.

[DmnEngineService](../backend/src/main/java/com/library/stockgap/domain/service/DmnEngineService.java) peut adapter une sortie chaîne en finding `SUPPORTED`, attribue tous les symptômes à chaque finding et fournit des références de preuves vides. Le service de couverture raisonne par types de symptômes sans contrôler suffisamment le degré d'étayage ni les conflits.

Une compilation/évaluation défaillante peut devenir absence de match ; le chargement global peut écarter silencieusement des règles invalides. Il faut distinguer panne technique et cause non couverte, refuser les sorties non conformes et tracer les conditions réellement évaluées.

### F07 — Élevé : adoption des résultats et reprise non uniformes

La voie diagnostic contrôle mission/dossier de preuves figé/lease/fencing. La voie proposition et les endpoints internes spécialisés n'appliquent pas la même protection : voir [consommateur](../backend/src/main/java/com/library/stockgap/infrastructure/kafka/AgentEventConsumer.java) et [API worker](../backend/src/main/java/com/library/stockgap/web/InternalWorkerController.java).

L'[inbox](../backend/src/main/java/com/library/stockgap/infrastructure/persistence/InboxRepository.java) marque `APPLIED` avant l'effet métier, dans une transaction distincte du consommateur qui attrape les exceptions. Un échec après cette inscription peut empêcher un rejeu utile. Le worker utilise l'autocommit Kafka. Les leases durent 90 secondes sans heartbeat worker implémenté ; les appels/retries fournisseur peuvent dépasser cette durée.

La recette doit inclure erreur, doublon, annulation, ancienne époque et expiration de tentative. Ne pas annoncer « exactly once » pour les inférences payantes.

### F08 — Moyen : données et navigation disproportionnées ou incomplètes

Le seed et le générateur historique couvrent dix familles/120 dossiers ; cible actuelle : dix dossiers **2 + 4 + 4**, tous vierges, deux seules familles à apprendre.

[Le routeur](../frontend/src/router/index.js) n'expose pas les vues de propositions/publications présentes dans les fichiers. Le dossier fournit néanmoins une proposition et un bouton de publication : il serait faux de dire qu'aucun chemin UI n'existe. Ni démo guidée ni espace DMN/FEEL explicatif complet n'est branché.

### F09 — Moyen : traces, configuration et documentation non alignées

Les prompts réellement exécutés sont dans [worker.py](../agents/worker.py), pas chargés depuis les templates de conception. L'usage est transmis dans certaines enveloppes Kafka, mais sa restitution/agrégation complète par rôle et par correction n'est pas réalisée ; le calcul d'investigation omet l'usage de la première sortie finale lorsqu'une réparation est nécessaire.

Les plafonds stockés en mission ne sont pas appliqués par le worker ; ils ne correspondent ni aux anciens documents ni à la décision actuelle « open bar ». La configuration modèle de la mission est figée alors que le worker lit une variable configurable. Les états d'échec de synthèse ne disposent pas du parcours de reprise aussi explicite que celui du diagnostic.

### F10 — Non vérifié : reset et exécution OpenAI de bout en bout

Un reset existe, mais son résultat complet, l'isolation des réponses tardives et la remise à zéro des traces actives ne sont pas testés ici. La présence d'appels SDK dans le code ne prouve ni l'accès au modèle configuré ni une génération réussie en direct. Aucun appel payant n'a été lancé pendant cet audit.

## Lecture critique du premier framing

Les documents historiques demandaient déjà les LLM réels, le chat utile, les règles runtime, la séparation diagnostic/publication et interdisaient de présenter des fixtures comme OpenAI. Les mocks initiaux et les métriques synthétiques ne sont donc pas des conséquences exigées par le framing ; ce sont des écarts de réalisation et de vérification.

En revanche, le cadrage a accumulé dix familles, volumétrie élevée, variantes, lots, multi-causes, remplacements coordonnés et nombreuses surfaces de gouvernance avant de verrouiller une boucle minimale prouvée. Ce périmètre a dilué la démonstration. Il mêlait aussi décisions validées, détails proposés et guides de réalisation sans toujours rendre leur autorité suffisamment claire.

L'ancien document d'état était devenu obsolète sur la synthèse GPT ; le README racine réduisait l'histoire au diagnostic agentique. Les contrats de conception et leurs exemples sont des ressources testées indépendamment, **pas des observations HTTP/Kafka du produit livré**. La matrice de versions candidates et les références de modèle n'ont pas été revérifiées auprès des registres/fournisseurs pendant cet audit.

La [revue source](archi-review.md) reste un document d'inspiration non modifié. Ses promesses de non-régression absolue, de latence constante et de généralisation garantie ne sont pas des critères acceptables ; la [lecture critique](01-lecture-critique.md) avait déjà relevé ces limites.

**À garder :** cascade, deux rôles réels, runtime versionné, expert, traçabilité, isolation des preuves, stack actuelle. **À réduire :** données et parcours initial. **À reporter :** lots, composition avancée, subsomption/remplacements multiples, migrations de stack. **À prouver avant d'élargir :** chacune des deux acquisitions reconnaît ses trois analogues vierges sans LLM.

## État historique du 2026-10-24, conservé pour comparaison

Les sections suivantes sont une archive du constat précédent, avec la correction locale sur la synthèse effectuée le 2026-10-31. Elles ne constituent ni une seconde cible ni une nouvelle vérification indépendante ; les constats actuels ci-dessus prévalent.

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
  → dossier de preuves figé
  → règles DMN actives (Apache KIE)
      → couverture complète : diagnostic DMN, état A_REVOIR
      → aucune règle/couverture partielle : AGENT_PENDING
           → mission Kafka InvestigationRequested
           → worker Python GPT + get_case_snapshot
           → diagnostic agentique validé ou état FAILED
```

Un second clic n’est pas nécessaire pour lancer GPT. Une nouvelle analyse annule l’analyse et la mission actives du même dossier avant de créer un nouveau dossier de preuves figé.

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

Il n’a pas de connexion SQL. Pour une investigation il doit appeler une fois `get_case_snapshot`, puis rendre un JSON Schema strict. Python et Java refusent une sortie qui cible un autre exemplaire, cite un symptôme absent, ou référence une preuve absente/issue d’un autre dossier de preuves figé.

La lecture du dossier de preuves figé global exige le jeton worker, une mission active, un lease non expiré et le dossier de preuves figé associé à la mission. L’adoption Kafka vérifie aussi mission, analyse, dossier de preuves figé, lease et fencing token.

Limites actuelles : les endpoints internes spécialisés (événements, prêts, retours, transferts, notes et règles) ne portent pas encore la même vérification fine de mission/lease. Le token par défaut de développement doit être remplacé avant tout usage hors poste local. Les quotas enregistrés en base (300 s, 8 appels LLM, 16 outils, 32k tokens entrée, 8k sortie) ne sont pas encore imposés durant l’appel.

## 5. Règles et gouvernance : niveau réellement livré

Les règles actives sont versionnées et le catalogue/détail est consultable. Les analyses enregistrent la version du référentiel utilisée. Le retrait des règles non `R001` est exposé dans l’UI.

**Correction par inspection du code le 2026-10-31 :** le worker traite maintenant `InvestigationRequested` et `RuleSynthesisRequested`. L'acceptation d'un diagnostic agentique `SUPPORTED` déclenche une mission de synthèse ; le worker appelle réellement la Responses API OpenAI pour produire un brouillon XML DMN, puis demande sa prévalidation Java. Cela décrit un chemin implémenté, pas une preuve de réussite de bout en bout avec OpenAI.

Le contexte transmis à la synthèse contient les identifiants d'origine et la conclusion globale, mais pas les findings ni les preuves du diagnostic accepté ; le dossier de preuves figé est relu séparément. La révision conversationnelle reste absente : l'endpoint de chat enregistre le message sans appeler de LLM ni déclencher une mission de dialogue. L'investigation reste limitée à un appel obligatoire de l'outil `get_case_snapshot`, pas à une exploration progressive des sources.

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
