# library-stock-gap-poc

POC pédagogique d'analyse hybride des écarts de stock d'une bibliothèque : règles DMN en premier, investigation OpenAI en fallback, puis capitalisation en nouvelles règles au runtime.

Java/Spring Boot possède les états, dossiers de preuves figés, validations et publications. Python consulte les preuves et propose diagnostics/règles sans accès SQL libre ni droit de publication. Vue présente les dossiers, règles et échanges.

## État et cible

Le [glossaire métier](framing/06-domaine-ddd-et-cycles-de-vie.md) définit les termes communs : opération métier de bibliothèque, mise à jour du stock informatique et dossier de preuves figé. La bibliothèque simulée, l'application d'analyse et ses moyens de diagnostic sont trois niveaux distincts.

L'[état d'implémentation](framing/12-etat-implementation-actuel.md) distingue le code livré de la cible. Le premier incrément est réalisé : **10 dossiers vierges, 10 exemplaires et 48 événements**, avec R001 couvrant les deux localisations ; les quatre retours et quatre réparations restent hors règle initiale.

La validation des propositions exécute désormais un **vrai corpus de 33 cas**, puis compare le référentiel avant/après. Le rapport est lié à la révision persistée, au corpus et au manifeste candidat ; l'approbation exige le rapport affiché et refuse les validations périmées.

**La boucle complète est réalisée et vérifiée avec OpenAI réel** : investigation à consultations successives, génération de conditions FEEL par l'agent de règles, XML standard sérialisé par Java, chat des deux rôles, affinage, réévaluation humaine et publication runtime. Les deux familles ont été acquises dans les deux ordres ; les trois autres dossiers de chacune ont ensuite été reconnus sans LLM.

Commencer sur http://localhost:3000/demo. Le [rapport de recette réelle](framing/conception/verification/live-demo-result.json) conserve les étapes exécutées ; ce n'est pas un résultat préfabriqué. La recette a restauré les dix dossiers vierges/R001 seule.

La [cible révisée](framing/00-vision-et-exigences.md) prévoit dix dossiers vierges **2 + 4 + 4**, deux agents et un chat réels, règles générées en FEEL et activées sans redémarrage. Une règle apprise sur un dossier doit reconnaître les trois autres analogues sans LLM. Voir la [roadmap](framing/13-roadmap-demo.md) et le [guide pédagogique](framing/14-guide-pedagogique.md).

## Démarrage

Prérequis : Docker Engine et le plugin Compose. La clé OpenAI est requise pour la voie LLM, pas pour le contrôle de préparation ni les diagnostics déjà couverts par R001.

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

L'analyse fige les preuves et la version des règles. Si DMN couvre le dossier, aucun appel OpenAI n'est requis. Sinon, Java crée une mission Kafka ; Python réclame et renouvelle son autorisation, consulte la synthèse puis les sources utiles et produit un diagnostic JSON vérifié contre les preuves réellement consultées.

Accepter un diagnostic `SUPPORTED` déclenche l'agent de règles : il propose des conditions FEEL séparées, pas un XML ou une règle codée d'avance. Java construit le DMN et renvoie les vrais tests au modèle pour correction bornée. L'expert approuve ensuite le rapport/révision exacts, sans redémarrage. Le chat explique et affine ; « Réévaluer avec la discussion » crée un nouveau diagnostic à accepter sans transformer les messages humains en faits généraux.

## Sécurité et fiabilité

- La clé OpenAI reste dans `.env`, exclu de Git.
- Le worker ne peut ni exécuter du shell, ni interroger PostgreSQL, ni corriger le stock.
- Les sorties GPT doivent citer uniquement des symptômes et identifiants de preuve présents dans le dossier de preuves figé ; toute référence inventée est rejetée.
- Les missions, tentatives, outils et résultats sont contrôlés ; heartbeat, reprise bornée et annulation sont livrés. Doublons sans double effet, réponses tardives journalisées sans adoption.
- Une erreur est visible et les missions/tours peuvent être annulés. Un appel OpenAI déjà envoyé peut consommer des tokens malgré l'annulation.
- Le diagnostic est une aide probabiliste : l'acceptation humaine ne déclenche aucune correction automatique.
- Les doubles OpenAI des tests hors ligne ne sont pas des résultats de démonstration. Les appels réels restent variables ; aucun échec ne doit être masqué par un résultat préfabriqué.

## Vérifications

La page http://localhost:3000/system affiche la préparation initiale. `GET /api/system/demo-check` vérifie en lecture seule le jeu importé, les dix dossiers vierges, R001 seule, l'absence de traitements enregistrés et la couverture 2 / 8. Il ne crée aucune analyse ou mission et ne teste pas la disponibilité d'OpenAI.

Une base existante n'est pas remplacée automatiquement au démarrage. Après confirmation explicite depuis l'écran système, la remise à zéro importe le nouveau jeu dans une époque neuve, conserve l'historique ancien et affiche le résultat des contrôles. Aucun volume Docker ni fichier de configuration n'est supprimé.

```bash
mvn -q -f backend/pom.xml clean test
/usr/bin/python3 framing/conception/seed/test_dataset.py
cd agents && OPENAI_API_KEY=offline-audit-placeholder .venv/bin/python -m unittest -v test_worker
cd .. && npm --prefix frontend run build
```

Ces vérifications hors ligne ne prouvent pas l'accès fournisseur ni les trois usages LLM. Un essai réel doit être convenu avant les appels payants ; aucun plafond de consommation n'est fixé pour l'instant, conformément à l'interview.

Recette PostgreSQL opt-in, sur la base locale du POC préparée avec R001 seule, sans appel LLM :

```bash
STOCKGAP_DB_TESTS=true mvn -q -f backend/pom.xml -Dtest=RuleGovernanceServiceTest test
```

Cette recette utilise des propositions explicitement de test, vérifie validation/publication/révisions/retrait et annule sa transaction à la fin. Ne pas l'exécuter sur une base de production ou pendant une démo en cours. Voir [les preuves et limites de M3](framing/12-etat-implementation-actuel.md).

Voir le [cadrage consolidé](framing/README.md).

## Recette OpenAI réelle

Cette commande effectue des appels payants, exige la préparation initiale et ne doit pas être exécutée pendant une présentation :

```bash
node framing/conception/verification/verify-live-demo.mjs --all-orders --restore --report=/tmp/stockgap-live-result.json
```

`--all-orders` remet le jeu à zéro entre les deux ordres d'acquisition ; `--restore` restaure le jeu initial à la fin ; `--reset` est une option explicite pour également remettre à zéro au début. Les échecs sont consignés, jamais remplacés par une simulation. La recette est bornée à 15 minutes. Préserver l'accord de consommation et ne pas l'utiliser sur une base de production.

La fiche règle propose un essai natif FEEL sans LLM ni analyse persistée. Les traces des dossiers montrent outils, modèle, appels/tokens cumulés et durée ; ne pas additionner les instantanés cumulés. Les clés et le raisonnement interne du modèle ne sont pas exposés.
