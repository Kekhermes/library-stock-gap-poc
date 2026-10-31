# Stack cible, versions et limites de vérification

## Lecture actuelle — 2026-10-31

**La stack existante est conservée ; la matrice candidate historique ci-dessous n'est pas une demande de migration.** Ses versions, licences et références de modèle n'ont pas été revérifiées auprès des registres/fournisseurs pendant cette passe.

L'existant inspecté utilise Java 21 / Spring Boot 3.4.2 / KIE 10.2.0, Python 3.11 en image, le client OpenAI Responses API, Spring JDBC et Flyway. Le build Vue vérifié utilise Vite 6.4.5 ; les plages Vue/Router/Pinia sont des déclarations de dépendances, pas une preuve de version runtime exacte. Node 22 construit l'interface et Nginx la sert. PostgreSQL est tagué `18` dans Compose, Redpanda `v24.3.1` et Console `v3.13.1`.

Sources : [POM](../backend/pom.xml), [image worker](../agents/Dockerfile), [dépendances Python](../agents/requirements.txt), [frontend](../frontend/package.json), [image frontend](../frontend/Dockerfile), [Compose](../docker-compose.yml), [audit des tests exécutés](12-etat-implementation-actuel.md).

OpenAI reste le fournisseur, avec modèle configurable et accès à vérifier réellement. Agents SDK et les plafonds historiques ne sont pas reconfirmés. Pas de plafond de consommation fixé actuellement ; appels/usage mesurés, corrections bornées, aucun secret en traces. « Open bar » ne prouve pas la disponibilité d'un modèle ni sa compatibilité avec les sorties structurées.

La recette finale enregistre les versions réellement résolues et les images utilisées ; elle ne substitue pas une recherche de « latest » à la preuve de la boucle. Voir [la roadmap](13-roadmap-demo.md) et [les rôles des technologies](14-guide-pedagogique.md).

## Recherche documentaire historique — non reconfirmée

## Statut

Recherche documentaire et registres consultés le **2026-10-24**. Technologies principales validées par Kevin ; versions ci-dessous proposées à partir de sources vivantes. **Aucune compatibilité intégrée, image Docker ou application n'a été testée pendant le cadrage.** Les lockfiles et digests seront figés au début de l'implémentation après le gate technique.

## 1. Matrice proposée

| Élément | Version / ligne candidate | Base vérifiée et décision |
|---|---|---|
| JDK | Temurin / Java 25 LTS | Ligne 25 LTS indiquée par Adoptium. Patch exact à figer ; API de découverte refusée en HTTP 403 dans cette session.[23] |
| Spring Boot | `4.1.2` | Version stable présente sur Maven Central ; exigences documentées Java 17 à 27.[17][20] |
| DMN / FEEL | Apache KIE `10.2.0` | Artefact `org.kie:kie-dmn-core` publié ; documentation 10.x : minimum JDK 17 et support DMN 1.5 introduit en 10.0.[6][18] |
| PostgreSQL | `18.6` standard, sans pgvector | Version indiquée par la documentation officielle consultée.[22] |
| Redpanda | `v26.2.6` Community | Version annoncée dans les métadonnées de la documentation courante ; rester sur les fonctions gratuites.[7] |
| Console Redpanda | `v3.13.1`, facultative | Version annoncée par la même documentation ; profil technique optionnel.[7] |
| Python | `3.14.9` | Release officielle ; ligne 3.14 déclarée dans les métadonnées des candidats SDK/HTTP.[21][14][16] |
| OpenAI Agents SDK | `openai-agents==0.24.0` | Métadonnées PyPI et quickstart officiel ; deux rôles explicites dans un worker.[14][8] |
| Client Kafka Python | `confluent-kafka==2.16.0`, candidat | Version PyPI, classification de licence Apache et wheels CPython 3.14 constatées ; vérifier la plateforme de l'image retenue.[15] |
| HTTP/santé du worker | FastAPI `0.162.0`, si nécessaire | Version et support Python déclarés sur PyPI ; ne pas y déplacer l'orchestration métier.[16] |
| Node de build | `24.22.1` LTS | Version listée dans l'index officiel, branche LTS Krypton.[19] |
| Vue | `3.5.44` | Dist-tag stable `latest` du registre npm.[10] |
| Vite | `8.3.5` | Registre npm ; exigence Node `^20.19.0 || >=22.12.0`, satisfaite par la ligne proposée.[11] |
| Pinia | `4.1.0` | Registre npm ; peer Vue `^3.5.11`, compatible sur le papier avec Vue proposé.[12] |
| Vue Router | `5.4.0` | Registre npm ; peers Vue `^3.5.34 || ^4.0.0`, Vite `^7.3.0 || ^8.0.0`, Pinia `^3.0.4 || ^4.0.2`.[13] |
| Modèle | `gpt-5.6-luna` | Identifiant officiel demandé, support annoncé des outils, sorties structurées et streaming.[1] |

### Pourquoi ne pas retenir mécaniquement tous les « latest » ?

La page d'accueil Spring affichait encore `4.1.1`, alors que Maven Central et les exigences système documentaient `4.1.2`. Le marqueur Maven `release` pointait vers `4.2.0-RC1`, une préversion : il n'est donc pas utilisé aveuglément comme version stable.[5][17][20]

Python `3.15.1` est publié, mais les métadonnées de l'Agents SDK consultées déclarent explicitement les versions jusqu'à 3.14. Le choix proposé de 3.14.9 réduit le risque d'adopter une version d'interpréteur plus récente que la matrice annoncée, sans prétendre que 3.15 serait incompatible.[9][14][21]

## 2. Dépendances laissées au gate technique

- Maven Wrapper, TypeScript, compilateur Vue, plugins Vite, dépendances UI, Vitest et tests e2e.
- Spring Kafka, driver PostgreSQL, persistence JPA et migration : versions cohérentes avec la BOM Spring Boot ; pas de versions transitives inventées dans le cadrage.
- Ensemble exact des modules KIE (`core`, API, validation, FEEL) alignés sur la même version.
- Dépendances Python transitives, notamment SDK OpenAI et Pydantic, via lockfile résolu.
- Images de build/runtime et digests ; pas de tag `latest` dans la livraison finale.

Les peers additionnels déclarés par les packages frontend doivent être examinés lors de la résolution complète ; le fait que Vue/Vite/Pinia concordent ne prouve pas à lui seul un build réussi.

## 3. Licence et gratuité d'usage

**Décision utilisateur :** gratuité d'usage personnel, source available admis ; OpenAI payant explicitement accepté comme dépendance du POC, sans comptabilité monétaire intégrée.

- Vue, Vite, Pinia et Vue Router déclarent MIT dans leurs métadonnées npm.[10][11][12]
- Vue Router déclare également MIT ; OpenAI Agents SDK et FastAPI déclarent MIT sur PyPI.[13][14][16]
- Le client Kafka Python déclare la catégorie de licence Apache dans les métadonnées PyPI ; contrôler aussi les licences des composants natifs embarqués dans l'image finale.[15]
- PostgreSQL publie sa licence permissive autorisant l'usage sans frais.[25]
- Redpanda Community est gratuit et **source available sous BSL**, pas à présenter comme intégralement open source sans nuance. Les fonctionnalités Enterprise nécessitent une licence et restent exclues. La documentation mentionne aussi des essais temporaires : le POC ne doit dépendre d'aucune fonction d'essai.[7]
- Docker Engine peut être utilisé directement sur Linux ; la documentation le distingue de Docker Desktop. Préférer Engine + plugin Compose pour ne pas rendre Desktop nécessaire à l'application.[26]

Une vérification finale des licences de tous les artefacts résolus est un gate de livraison. Le présent document n'est ni une analyse juridique exhaustive ni une autorisation d'exploiter les mêmes distributions en entreprise.

## 4. OpenAI : intégration bornée

- Le modèle reste `gpt-5.6-luna`, sans remplacement silencieux.
- Deux rôles : investigateur des causes ; agent de règles et de dialogue.
- Utiliser les outils applicatifs contrôlés ; ne pas activer web search, file search, shell ou computer use parce que le fournisseur les propose.
- L'historique métier et conversationnel demeure sous autorité Java. Le SDK exécute un tour/une mission ; il ne devient pas l'orchestrateur durable des approbations.
- Interdire les outils de publication, écriture de stock ou accès SQL arbitraire.
- Aucun appel OpenAI payant n'a été réalisé pendant cette recherche.

L'Agents SDK active le tracing par défaut et documente `OPENAI_AGENTS_DISABLE_TRACING=1` pour le désactiver.[24] Proposition : désactiver cet export dès le démarrage du worker et conserver les traces pédagogiques utiles localement dans l'application. Les données nécessaires aux inférences sont néanmoins envoyées à OpenAI : « hébergé localement » ne signifie pas « aucune donnée sortante ».

## 5. Secrets et réseau

À implémenter avant le premier lancement : `.env` dans `.gitignore` et `.dockerignore`, exemple expurgé, clé injectée uniquement au worker, aucun secret dans Vue ou les logs. Pas de lecture de `.env` dans les rapports d'audit ou les exports de démonstration.

Ports exposés seulement sur `127.0.0.1` ; les services de données restent sur le réseau Compose. Absence de login utilisateur ne signifie pas absence de protections contre les commandes indues : commandes non-idempotentes protégées contre requêtes cross-origin, validation d'origine/hôte, types de contenu et contrôles d'état côté serveur.

## 6. Gate technique à exécuter après autorisation d'implémenter

1. Compiler Spring Boot/Java/KIE et vérifier le chargement d'un DMN depuis la persistance.
2. Évaluer vrai/faux/inconnu, plusieurs correspondances et sorties structurées avec références de preuves.
3. Charger une nouvelle version à chaud, préserver une analyse en cours et retrouver la version après redémarrage.
4. Tester la validation DMN et le refus des extensions/appels non autorisés.
5. Résoudre les dépendances frontend/Python, construire les images et vérifier les licences/secrets.
6. Tester Kafka, outils REST, annulation et reprise avec doublons.
7. Seulement avec une clé locale et autorisation d'usage : appel réel au modèle demandé, respect des limites et rapport honnête de résultat.

Si un point bloque, conserver le constat et soumettre une adaptation ciblée ; ne pas changer discrètement la stack ni fabriquer un succès.

## Sources

[1] https://developers.openai.com/api/docs/models/gpt-5.6-luna — GPT-5.6 Luna — documentation API OpenAI
[5] https://spring.io/projects/spring-boot
[6] https://kie.apache.org/docs/10.2.x/drools/drools/release-notes/index.html
[7] https://docs.redpanda.com/current/get-started/licensing/overview
[8] https://openai.github.io/openai-agents-python/quickstart
[9] https://www.python.org/downloads/release/python-3151
[10] https://registry.npmjs.org/vue/latest
[11] https://registry.npmjs.org/vite/latest
[12] https://registry.npmjs.org/pinia/latest
[13] https://registry.npmjs.org/vue-router/latest
[14] https://pypi.org/pypi/openai-agents/json
[15] https://pypi.org/pypi/confluent-kafka/json
[16] https://pypi.org/pypi/fastapi/json
[17] https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-starter-parent/maven-metadata.xml
[18] https://repo.maven.apache.org/maven2/org/kie/kie-dmn-core/maven-metadata.xml
[19] https://nodejs.org/dist/index.json
[20] https://docs.spring.io/spring-boot/system-requirements.html
[21] https://www.python.org/downloads/release/python-3149
[22] https://www.postgresql.org/docs/current/index.html
[23] https://adoptium.net/temurin/releases
[24] https://openai.github.io/openai-agents-python/tracing
[25] https://www.postgresql.org/about/licence
[26] https://docs.docker.com/engine/install
