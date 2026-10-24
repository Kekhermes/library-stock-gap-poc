# Spécification UI pour la réalisation Vue

## Direction retenue

Interface en français, claire et sobre, densité de poste d'analyse sans masquer les preuves. L'UI ne contient aucun moteur de diagnostic ou de validation de publication. Tous les statuts métier viennent de Java.

## Routes proposées

| Route Vue | Vue | Données nécessaires |
|---|---|---|
| `/` | Vue d'ensemble | Compteurs de cas, missions, propositions et référentiel actif |
| `/cases` | Liste filtrable | Cas paginés ; filtres site, titre, symptôme, statut ; jamais famille cachée |
| `/cases/:caseId` | Dossier trois panneaux | Cas, analyse sélectionnée, snapshot, diagnostic, conversation et proposition |
| `/batches/:batchId` | Suivi d'un petit lot | Enfants, résultats individuels, progression et annulation |
| `/rules` | Catalogue | Règles actives ou retirées, versions, derniers rapports |
| `/rules/:ruleId` | Fiche lecture seule | Métadonnées, DMN/FEEL, exemples, historique et dialogue de révision |
| `/proposals/:proposalId` | Revue | Révision, base, opérations, diff, tests et approbation possible |
| `/publications` | Historique | Manifestes, changements et motifs de retrait |
| `/demo` | Parcours guidé | Étapes réellement exécutées ; pas de conclusions préchargées affichées comme réelles |
| `/system` | État local | Healthchecks, configuration présente, limites ; aucune valeur de secret |

Les routes sont frontend ; les operationIds OpenAPI constituent le contrat des appels serveur. Un écran peut agréger plusieurs lectures ; pas de dépendance à un payload Kafka brut.

## Composants

- `CaseFilters`, `CaseTable`, `BatchProgress`.
- `CaseHeader`, `EvidenceTimeline`, `SourceCoverageBadge`, `EvidenceDetailDrawer`.
- `DiagnosticCard`, `FindingList`, `ContradictionPanel`, `DiagnosticAcceptanceBar`.
- `RuleProposalPanel`, `RuleDiff`, `ValidationReport`, `PublicationConfirmation`.
- `ExpertChat`, `ChatRoleBadge`, `EvidenceLink`, `IncompleteMessageNotice`.
- `RuleReadOnlyTable`, `DmnSourceViewer`, `RuleHistory`, `RetirementDialog`.
- `SseConnectionStatus`, `MissionProgress`, `QuotaNotice`, `StaleVersionBanner`.

Les boutons de création/édition manuelle de règles n'existent pas. Une demande de révision ouvre une conversation ; retirer est une commande distincte avec confirmation.

## Dossier : comportement des panneaux

1. **Gauche — faits** : attendu/observé, identité, couverture d'inventaire, chronologie triée par temps métier avec temps d'enregistrement visible, sources manquantes, notes.
2. **Centre — résultat et connaissance** : diagnostic, preuves et symptômes couverts ; ensuite proposition DMN et rapport. Les deux approbations sont séparées visuellement.
3. **Droite — échange** : rôle actif, messages, demandes de précision et révisions ; barre d'état de la mission, annulation et quota.

Cliquer sur une preuve ouvre son objet exact dans le snapshot de l'analyse, jamais la version « actuelle » d'une source différente. Recharger un dossier garde la sélection par `analysisId` dans l'URL/query state lorsque possible.

## Matrice des actions

| État | Action autorisée | Retour attendu / erreur |
|---|---|---|
| Cas sans analyse active | Analyser | 202 et nouvel analysisId ; 409 si une analyse vient de démarrer ailleurs |
| Analyse en cours | Annuler | Annulation demandée puis confirmée ; un retard ne signifie pas échec de commande |
| Diagnostic à revoir | Accepter / rejeter | Version attendue obligatoire ; ne pas accepter un résultat périmé |
| Diagnostic accepté non généralisable | Consulter les limites | Aucune règle artificielle ; RuleChangeNotProposed est affiché comme résultat métier |
| Proposition brouillon | Discuter / demander tests | Nouvelle révision ; aucune action sur la publication active |
| Validation en cours | Lire progression / annuler si disponible | Rapport incomplet explicitement affiché |
| Proposition prête | Approuver et publier | Version/empreinte/base/rapport exacts ; 409 si changement concurrent |
| Proposition périmée | Demander réévaluation | Bouton publier désactivé, aucune approbation reconduite |
| Règle active | Demander révision / retirer | Retrait confirmé avec impact ; historique conservé |
| Règle retirée | Consulter / demander une nouvelle proposition | Pas de bouton qui réactive directement hors validation |

## Client REST et SSE

Conserver les données serveur dans des stores séparés par entité et version. Ne pas appliquer aveuglément un événement à une ancienne route : vérifier identifiant, epoch et séquence/version.

Le protocole de snapshot/cursor du contrat SSE protège contre le trou entre lecture REST et connexion. Un événement sert à mettre à jour une projection sûre ou à invalider/refetch une ressource. Après reset/curseur expiré, abandonner l'ancien cache et relire un snapshot complet.

Les boutons n'affichent « terminé » qu'après l'état durable correspondant, pas après un 202. Après timeout HTTP ambigu d'une commande, réutiliser son idempotency key ou relire son résultat avant de générer une nouvelle commande.

## Chat

Messages persistés distingués des chunks provisoires. Une réponse interrompue reste marquée incomplète. Le texte Markdown est nettoyé avant rendu ; pas d'HTML arbitraire. Une note ou citation contenant des instructions est présentée comme une pièce de preuve, pas comme un message système.

Les liens de preuve ne sont créés qu'à partir de références validées par Java. Une URL/identité inventée dans le texte du LLM n'est pas un outil ni une autorisation.

## Accessibilité et lisibilité

- Navigation au clavier, focus visible, ordre de tabulation cohérent entre panneaux.
- Statuts texte + icône + couleur ; éviter rouge/vert comme seule distinction.
- Lecteurs d'écran : annoncer les étapes, pas chaque token du streaming.
- Dates affichées avec timezone ; possibilité de voir UTC exact pour les recoupements.
- Emplacements et IDs copiables ; libellés métier avant identifiants techniques.
- Sur écran étroit, panneaux en onglets ; choix du panneau conservé après rafraîchissement.
- Confirmation destructive limitée au retrait/reset, pas une boîte modale à chaque clic.

## Critères de revue du prototype

Sur un cas nominal et un cas ambigu, l'Admin doit pouvoir retrouver une preuve, comprendre le statut, distinguer hypothèse et conclusion, accepter le diagnostic sans publier de règle, demander une révision puis lire son impact. Aucune maquette de ce dossier ne constitue un prototype visuel déjà testé : ces critères sont à vérifier pendant ta réalisation.
