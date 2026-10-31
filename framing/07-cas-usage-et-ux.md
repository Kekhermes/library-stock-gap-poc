# Cas d'usage et UX/UI

## Parcours cible actuel — 2026-10-31

Les [décisions actuelles](04-registre-des-decisions.md) et le [jeu 2 + 4 + 4](09-donnees-evaluation-et-reset.md) prévalent sur la navigation et les parcours historiques ci-dessous. Objectif : une démo 10–15 minutes pour développeurs/architectes, pas une plateforme opérationnelle exhaustive.

- Liste de dix dossiers vierges au reset ; un dossier à la fois dans le parcours initial.
- Dossier : attendu/observé et preuves ; diagnostic et proposition ; Discussion Expert réelle.
- Investigation : étapes, consultations successives, observations, synthèses factuelles, modèle/appels/tokens/durée mesurés.
- Chat : rôle choisi selon la phase, affiché et modifiable ; expliquer, contester et affiner, avec demande explicite de réévaluation.
- Deux décisions distinctes : accepter le diagnostic, puis approuver/publier la révision de règle testée.
- Espace DMN/FEEL : table lisible, expression exacte en lecture seule, faits/provenance, essai pur sur un dossier et conditions vraies/fausses/inconnues.
- Gestion runtime : brouillon/révisions, rapports réels, approbation, publication/version active, retrait et historique.
- Démo : progression déduite des actions réellement exécutées, puis analyse des trois analogues encore vierges.

Le chat ne se limite pas à enregistrer la question. Un statut « prêt » ou une compilation réussie n'autorise pas un badge « replay validé ». Le refus de synthèse, la correction, l'expiration d'une validation et les erreurs fournisseur doivent être compréhensibles et relançables.

Les preuves sont consultables depuis le diagnostic et les essais de règle. Le code FEEL inspecté est celui réellement exécuté, pas une explication reconstruite par l'UI. Un essai de règle ne doit pas déclencher un LLM caché.

Sur écran étroit, utiliser onglets ou étapes lisibles plutôt que trois colonnes fixes. Réutiliser les vues existantes, organiser règles/propositions/publications ensemble si cela simplifie la navigation. Lots, multi-causes et comparaison avancée ne sont pas des exigences du parcours initial.

**À prototyper :** structure précise de l'espace règles et intégration du reset avec confirmation. La confirmation avant reset et la conservation de la configuration sont obligatoires ; l'ancien choix « commande locale exclusivement » n'est pas reconfirmé.

Voir [la roadmap M6–M8](13-roadmap-demo.md). La documentation pédagogique approfondit le fonctionnement hors du parcours court ; il ne faut pas un chat technique généraliste supplémentaire.

## Spécification détaillée historique — à réconcilier

## Statut et principes

**Validé** : persona Admin unique, dossier à trois panneaux, analyse individuelle et petits lots, mode démonstration guidé, absence d'édition manuelle des règles, création/révision exclusivement par l'agent, retrait possible par l'Admin.

Le détail ci-dessous est une proposition de spécification fonctionnelle à valider. Langue proposée : français ; identifiants techniques et code FEEL conservés dans leur syntaxe native.

## 1. Navigation

- **Vue d'ensemble** : cas à analyser/à revoir, missions en cours, propositions à valider, couverture déterministe.
- **Cas d'inventaire** : liste, filtres et dossier détaillé.
- **Règles** : catalogue actif, historique, demandes de révision et retraits.
- **Publications** : changements, tests, approbations et comparaison des versions.
- **Démonstration** : parcours guidé avec étapes vérifiables, pas de résultats préfabriqués.
- **État local** : santé des services, présence de la configuration OpenAI sans valeur du secret, limites techniques et version du jeu de données.

Le reset destructif reste une commande locale documentée, pas un bouton discret au milieu des écrans métier.

## 2. Cas d'usage

| ID | Intention / action Admin | Précondition | Résultat et erreurs importantes |
|---|---|---|---|
| UC01 | Explorer les cas préchargés | Dataset initialisé | Filtrer par site, titre, type d'écart, statut et chemin de diagnostic ; jamais afficher la cause cachée comme filtre opérationnel |
| UC02 | Ouvrir un dossier | Cas existant | Voir attendu/observé, couverture d'inventaire, chronologie et provenance des données |
| UC03 | Lancer une analyse | Pas d'analyse active du même cas | Analyse figée sur un snapshot ; diagnostic déterministe ou fallback visible |
| UC04 | Suivre/annuler une investigation | Mission en cours | Progression, outils et limites ; annulation confirmée sans faux résultat final |
| UC05 | Examiner/accepter/rejeter un diagnostic | Résultat disponible | Acceptation indépendante de toute règle ; rejet motivé et possibilité de nouvel échange/analyse |
| UC06 | Obtenir une proposition de règle | Diagnostic issu du fallback accepté et connaissances formalisables | Mission de l'agent de règles ; si la preuve est seulement textuelle ou la capacité absente, résultat non publiable expliqué |
| UC07 | Discuter une proposition | Proposition/conversation existante | Questions, objections et nouvelles révisions ; comparaison avant/après ; aucune édition manuelle des conditions |
| UC08 | Examiner les tests et impacts | Révision testée | Positifs/négatifs, inconnus, résultats avant/après et alertes de généralisation |
| UC09 | Approuver et publier | Proposition actuelle et contrôles satisfaits | Publication atomique ; version active lisible ; conflit si approbation périmée |
| UC10 | Analyser un cas analogue | Nouvelle publication active | Nouveau diagnostic déterministe sans appel LLM si les conditions sont suffisantes ; mêmes limites de preuve |
| UC11 | Consulter les règles | Référentiel disponible | Fiche métier, conditions, sorties, DMN/FEEL en lecture seule, sources et historique |
| UC12 | Demander la révision d'une règle | Règle existante et contexte historique | Chat de l'agent de règles ; rattachement aux diagnostics d'origine ; proposition soumise au même sas |
| UC13 | Retirer une règle | Règle active | Aperçu de perte de couverture, confirmation, nouvelle publication ; historique intact |
| UC14 | Analyser un petit lot | Sélection bornée | Résultats et progression par cas ; quotas partagés ; panne d'un cas sans effacement du lot |
| UC15 | Comparer les publications/analyses | Historique existant | Visualiser quels changements ont modifié une conclusion et avec quelles preuves |
| UC16 | Exécuter la démonstration guidée | Dataset/règles compatibles avec le scénario | Montrer les étapes réellement exécutées, détecter une démo déjà modifiée et proposer le reset documenté |

La création libre de règle sans diagnostic d'origine et la modification manuelle sont hors périmètre, y compris via une API cachée non exposée par le frontend.

## 3. Dossier à trois panneaux

```text
Cas : exemplaire / titre / site           Écart observé | statut | version du référentiel
[Analyser / Réanalyser] [Annuler]          Analyse courante et accès à l'historique

┌──────────────────────┬──────────────────────────┬─────────────────────────┐
│ FAITS / CHRONOLOGIE   │ DIAGNOSTIC / PROPOSITION │ CHAT EXPERT             │
│ Attendu / observé    │ Déterministe ou IA       │ Rôle actif affiché      │
│ Périmètre inventorié │ Conclusion + incertitude │ Questions / objections  │
│ Prêts, retours, etc. │ Preuves / contradictions │ Réponses sourcées       │
│ Sources manquantes  │ Accepter le diagnostic   │ Révision proposée       │
│ Notes opérateur     │ Règle : diff, tests      │ Tour en cours / limite  │
│ Liens de preuve     │ Approuver et publier     │ Saisie et actions utiles│
└──────────────────────┴──────────────────────────┴─────────────────────────┘
```

- Cliquer sur une preuve dans le diagnostic ouvre la ligne correspondante à gauche.
- Le centre distingue diagnostic, proposition et publication ; pas de bouton ambigu « Valider tout ».
- Le chat affiche s'il s'adresse à l'investigateur ou à l'agent de règles. Après acceptation du diagnostic, le travail sur les règles relève du second rôle.
- Le document DMN/FEEL est inspectable et copiable, pas éditable par l'Admin.
- Après publication, action « Analyser le cas analogue » si le scénario de démonstration en fournit un ; dans la vue opérationnelle, lien vers des cas candidats sans révélation d'oracle.
- Sur écran étroit : panneaux en onglets, pas trois colonnes illisibles.

## 4. Chat utile plutôt que chat décoratif

Actions contextuelles proposées :
- « Sur quelles données t'appuies-tu ? »
- « Quelle autre cause reste possible ? »
- « Pourquoi cette condition est-elle nécessaire ? »
- « Montre un cas qui ne doit pas déclencher cette règle. »
- « Peut-on généraliser à un autre site ? »
- « Cette proposition remplace-t-elle une règle existante ? »

Ces actions produisent un vrai message dans la conversation. Les réponses citent des identifiants de preuves, de règles et de tests cliquables. Les notes libres sont rendues comme données, jamais comme instructions donnant des droits à l'agent.

Une réponse textuelle peut être streamée, mais les propositions structurées ne deviennent utilisables qu'après réception complète et validation. Une réponse interrompue est marquée incomplète.

## 5. Fiche règle en lecture seule

- Nom métier, cause/hypothèse visée et degré d'étayage.
- Périmètre, conditions, exceptions et données requises.
- Recommandations et explication de chaque condition.
- DMN/FEEL exact de la version, identité et empreinte.
- Cas d'origine, agent auteur, diagnostic accepté et historique de publications.
- Exemples qui déclenchent, négatifs proches et cas avec faits inconnus.
- Actions : demander une révision par chat ; retirer avec confirmation.

Un changement proposé par l'agent reste un brouillon jusqu'à validation humaine ; l'écran ne présente jamais une suggestion comme déjà active.

## 6. Retrait et changements périmés

Le retrait montre la règle concernée, la publication actuelle et l'impact observé sur le corpus. Confirmer produit immédiatement une nouvelle version active ; les analyses en cours restent sur leur snapshot.

Si une proposition était prête à approuver avant ce retrait, son bouton est désactivé avec explication : « Le référentiel a changé. Réévaluation nécessaire. » La conversation reste consultable.

## 7. Modes pédagogiques

Mode guidé proposé :
1. Comprendre attendu/observé et sources disponibles.
2. Voir pourquoi le déterministe ne conclut pas.
3. Observer les outils et le diagnostic de l'investigateur.
4. Accepter le diagnostic et discuter avec l'agent de règles.
5. Voir un négatif proche empêchant une généralisation excessive.
6. Publier puis traiter un autre cas sans IA.
7. Examiner une évolution ou un retrait de règle.

Le compteur d'appels LLM, les tokens et durées sont réels. Pas de compteur monétaire, selon décision. La présentation pédagogique n'expose pas l'oracle au contexte agentique ; la révélation éventuelle de la cause injectée est réservée au bilan d'évaluation séparé.

## 8. Direction visuelle retenue, détails à prototyper

Interface sobre de poste d'analyse, fond neutre, hiérarchie typographique nette, tableaux denses mais lisibles, distinction visuelle déterministe/agentique/humain. Statuts exprimés par texte et icône en plus de la couleur ; focus clavier visible ; confirmations accessibles ; pas d'animation bloquant la lecture.

**Validé le 2026-10-24 :** interface en français, claire et sobre, tableaux lisibles, distinction nette entre faits, hypothèses et validations. La bibliothèque UI et les tokens détaillés restent ouverts. Un prototype visuel devra être validé avant de développer l'ensemble des écrans.
