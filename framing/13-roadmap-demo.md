# Roadmap — de l'existant à la démo attendue

## Statut

Roadmap proposée le **2026-10-31**, fondée sur [l'interview](04-registre-des-decisions.md), [la cible validée](00-vision-et-exigences.md) et [l'audit](12-etat-implementation-actuel.md). Les résultats attendus sont validés ; ce découpage de réalisation reste une proposition. Aucun jalon applicatif ci-dessous n'est déclaré livré par la rédaction de ce document.

Ne pas reconstruire le projet ni migrer sa stack. Réaliser des incréments courts, démontrables et documentés ; ne pas élargir le périmètre avant la preuve de la boucle.

## Résultat final obligatoire

Après reset : dix dossiers `A_ANALYSER`, une seule règle R001 active, répartition **2 + 4 + 4**. Les deux localisations sont déterministes. Pour chacun des deux autres groupes, un dossier sert à l'investigation réelle puis à la création d'une règle réelle ; les trois autres deviennent déterministes après publication, sans redémarrage ni nouvel appel LLM de diagnostic.

Les acquisitions doivent fonctionner dans les deux ordres, retour puis réparation ou réparation puis retour. Les conditions viennent du LLM, pas du seed, d'une table de réponses attendues ou de méthodes Java par cause.

## Priorités et dépendances

| Jalon | Priorité | Dépend de | Livrable vérifiable | Écarts d'audit |
|---|---|---|---|---|
| M0 — Revue de la cible | Immédiate | Interview réalisée | Valider cette roadmap et traiter l'atelier DMN/agents restant ouvert | F09 |
| M1 — Données et reset | Bloquante | M0 | Dix dossiers vierges, une seule règle, prévol non destructif | F08, F10 |
| M2 — Faits et exécution DMN | Bloquante | M1, atelier FEEL | Faits non biaisés, sorties strictes, traces fiables et aucune panne masquée | F02, F06 |
| M3 — Validation et publication | Bloquante | M2 | Tests/replay réels et activation de la révision exacte au runtime | F01, F05 |
| M4 — Missions, preuves et mesures | Bloquante | M0 ; intégré à M2–M6 | Adoption cohérente, reprise/annulation, traces persistantes et mesures vraies | F07, F09 |
| M5 — Deux agents réels outillés | Bloquante | M2, M3, M4 | Investigation progressive et synthèse FEEL, corrections contrôlées | F03, F04 |
| M6 — Discussion Expert | Bloquante | M4, M5 | Dialogue réel, rôle visible, correction et révision avec effets métier | F03 |
| M7 — Espace DMN et démo | Essentielle | M1–M6 | Compréhension des conditions et parcours guidé 10–15 min | F08, F09 |
| M8 — Recette et documentation | Livraison | M1–M7 | Preuve directe des deux boucles et documentation à jour | F01–F10 |

Les documents pédagogiques sont mis à jour **à chaque jalon**, pas uniquement en M8. M4 est un travail transversal ; ses protections sont terminées avant les premiers essais de boucle réelle.

## M0 — Atelier restant, sans rouvrir les acquis

Fixer le contrat des conditions FEEL produites par le LLM, la table DMN sérialisée par Java, les types/faits et le sous-ensemble autorisé. Choisir les outils réellement exposés aux deux rôles et le nombre de corrections automatiques. Comparer Responses API actuelle et Agents SDK seulement si cela aide cette réalisation ; aucun framework supplémentaire par principe.

**Sortie :** exemples d'entrées/sorties, responsabilités, erreurs et traces compris et approuvés. Aucun plafond financier/tokens/appels demandé pour l'instant ; les limites de taille/durée d'évaluation et l'arrêt des boucles sont des garde-fous techniques distincts.

## M1 — Un vrai jeu minimal

Adapter le seed/générateur existant et les ressources applicatives : deux localisations, quatre retours, quatre réparations. Conserver uniquement les événements et le fonds nécessaires. Ne pas simplement filtrer l'écran tout en conservant 120 dossiers comme modèle de démo.

Les sources restent plausibles, à temps de référence fixe, avec opérations et intégrations corrélées. Un seul symptôme principal par dossier et preuves suffisantes pour les deux règles futures. Oracle et catalogue pédagogique hors outils/prompts ; aucun nom de famille cachée n'est un fait de diagnostic.

Réutiliser le reset existant et fournir une vérification simple. Les fixtures négatives/incomplètes restent dans le corpus technique, séparées des dix dossiers.

**Gate :** après deux resets, dix dossiers tous vierges, R001 seule, aucune ancienne mission/conversation/proposition active, même contenu métier/version/seed et nouvelle époque. Deux dossiers sont couverts par R001 en évaluation pure, huit ne le sont pas ; le prévol ne crée ni analyse ni mission LLM.

## M2 — Des faits qui ne connaissent pas déjà le diagnostic

Retirer les conclusions forcées et revalider les règles héritées qui en dépendent. Stabiliser un catalogue de faits génériques : identité, qualité/unicité d'observation, état attendu, événement métier, résultat d'intégration corrélé, disponibilité des sources et temporalité.

Choisir les preuves admissibles plutôt que le premier scan. Distinguer valeur fausse, inconnue et source incomplète. Ne pas ajouter `isRetourNonProjete` ou une méthode équivalente qui ferait le diagnostic dans Java avant FEEL.

Durcir les sorties DMN et leur adaptation : cause, statut, symptômes effectivement couverts, version/décision/ligne et références. Séparer absence de match, insuffisance et panne moteur ; ne pas ignorer une règle invalide pour afficher un référentiel supposé entièrement évalué.

**Gate :** R001 ne couvre que ses deux dossiers ; cas faux/inconnu/scan contradictoire refusés ; erreur KIE visible ; aucune conclusion préalable/oracle/IA dans les faits. Traces de conditions conformes à l'évaluation réelle.

## M3 — Remplacer les chiffres synthétiques par des exécutions

Charger le candidat depuis la révision persistée, pas depuis un XML choisi par le navigateur. Compiler et vérifier structure, types, fonctions autorisées et capacités disponibles. Exécuter positifs, négatifs proches, inconnus et replay du référentiel avant/après sur le corpus de validation.

Rapport lié à la proposition/révision, à son contenu, au manifeste, au corpus, à la version des faits et du moteur. Vérifier les mêmes références lors de l'approbation. Toute modification ou changement de base rend la validation périmée ; préparation du runtime complet avant basculement atomique, rejet explicite de la concurrence périmée.

**Gate :** un DMN compilable mais trop large échoue sur un négatif ; une règle à identifiant figé échoue sur des positifs nouveaux ; changer le candidat ou la base interdit la publication sans nouveaux tests. Une bonne publication est utilisée dès l'analyse suivante ; une analyse déjà commencée conserve sa version. Pas de badge « tests validés » sans rapport réel réussi.

## M4 — Missions sûres et observables

Uniformiser mission/snapshot/époque/lease/fencing pour tous les outils, diagnostics, propositions et réponses de chat. Mettre adoption et déduplication dans une transaction cohérente ; ne pas marquer un résultat appliqué avant son effet métier. Gérer ack/rejeu, expiration, heartbeat ou autre stratégie justifiée, et annulation avec résultats tardifs sans effet.

Conserver les traces utiles : rôle, modèle réellement appelé, versions de prompt et contexte, outils/arguments autorisés, observations, synthèses courtes, erreurs, essais, tokens et durées. Additionner toutes les sorties rejetées/corrigées et les retries lorsque leur usage est observable ; indiquer explicitement une comptabilité incomplète plutôt que zéro fictif.

Rendre les plafonds historiques non prescriptifs et la configuration conforme à « open bar », sans boucle infinie. Ne pas compter une limite SDK par appel comme un budget global effectivement appliqué.

**Gate :** doublon sans double effet, résultat annulé/ancienne époque refusé, erreur après réception rejouable, dépassement de lease traité, traces retrouvées après reconnexion. Aucun secret ni export de raisonnement interne dans les traces.

## M5 — Deux agents distincts, vraiment génératifs

L'investigateur consulte successivement les preuves via outils Java bornés au snapshot. Il choisit ses consultations ; le code ne suit pas un script qui reconnaît secrètement les familles. Diagnostic structuré sourcé, abstention possible et contrôles Python/Java.

L'agent de règles reçoit le diagnostic complet accepté, les preuves autorisées, les faits disponibles, la version de base et le contexte d'échange. Le LLM propose réellement les conditions FEEL et métadonnées ; Java sérialise le DMN standard sans choisir les conditions ni substituer une règle prédéfinie. Le gabarit contient seulement la structure.

Rapport de refus renvoyé à l'agent pour une nouvelle révision et correction automatique bornée. Refus persistant, capacité manquante et panne fournisseur sont des états visibles et relançables ; aucune publication forcée.

**Gate hors ligne :** contrats et erreurs avec doubles explicitement de test. **Gate réel, après accord d'exécution :** appels OpenAI observés, outils successifs, diagnostic accepté et règle FEEL réellement produite pour chacune des deux familles. Aucune R2/R3 applicative préchargée ; aucun reset implicite pour arranger un résultat.

## M6 — Chat qui change réellement le travail

Tour expert durable avec contexte dossier/diagnostic/règle/proposition/révision et rôle actif. Routage selon phase, affiché et modifiable ; pas besoin d'un troisième agent coordinateur. Explications sourcées, question/réponse réelle, état en cours et erreurs visibles.

Une correction est un apport humain identifiable, pas une mutation des événements source. Réévaluer sur demande explicite ; si le diagnostic change, demander une nouvelle acceptation et périmer les propositions dépendantes. Une évolution de règle crée une révision distincte et invalide ses tests précédents. Pas d'approbation par chat.

**Gate :** trois conversations réelles : expliquer une preuve, contester puis réévaluer un diagnostic, affiner une condition et voir la proposition changer avec nouveaux tests. Un message « publie » ou « oui » n'active aucune règle.

## M7 — Rendre DMN et la boucle compréhensibles

Faire évoluer les vues existantes : liste de dix dossiers, faits/diagnostic/chat lisibles, espace règles avec table métier, FEEL exact en lecture seule, faits/provenance, essai sur un dossier et résultats vrais/faux/inconnus par condition. Historique des révisions, rapports et publications ; retrait runtime avec confirmation.

La table affichée et le XML exécuté représentent la même version. L'essai est une évaluation DMN pure, pas une analyse qui déclenche discrètement OpenAI. La démo guidée lit les étapes effectivement accomplies ; elle ne préremplit pas les conclusions.

**Gate navigateur :** parcours desktop/mobile, preuves accessibles, aucun badge de réussite inventé, rôle et erreurs visibles, publication suivie immédiatement des trois analogues. Rechargement/reconnexion retrouve le même état. Pas de nouvelles pages décoratives au détriment du parcours.

## M8 — Recette finale et critère de livraison

| Test | Attendu |
|---|---|
| T01 Initialisation/reset | Dix vierges, une règle active, groupes 2/4/4 et aucune trace active héritée |
| T02 Deux localisations | R001, provenance DMN, zéro appel LLM de diagnostic |
| T03 Premier retour | Aucun match initial, investigation OpenAI réelle, diagnostic sourcé |
| T04 Retour accepté → règle | Synthèse FEEL OpenAI réelle, tests réels, deux actes humains |
| T05 Trois autres retours | Nouvelle règle/version, pas de redémarrage, zéro appel LLM de diagnostic |
| T06 Réparations | Même preuve sur un dossier puis ses trois analogues ; aucun faux match de la règle retour |
| T07 Ordre inverse | Reset convenu, réparation puis retour, résultats équivalents |
| T08 Discussion Expert | Explication, correction/réévaluation et affinage réellement exécutés |
| T09 Garde-fous | Négatif, inconnu, XML invalide, rapport périmé, annulation et résultat tardif refusés |
| T10 Mesures et pédagogie | Modèle/appels/tokens/durée réels, toutes les étapes et technologies expliquées en Markdown |

Conserver un compte rendu versionné : environnement/image, dataset, prompts, modèle, résultats et limites, sans secret. Fixer le nombre de répétitions de la recette réelle lors de M0 ; une seule exécution réussie ne prouve pas une disponibilité absolue.

**Livré signifie :** code + données + documentation à jour + gates exécutés, avec preuves de T01–T10. Tests de doubles et build seuls ne satisfont pas les trois usages LLM. En cas de panne fournisseur ou refus persistant, consigner le gate non satisfait ; ne pas transformer l'échec en succès fictif.

## Hors parcours initial

Lots, dix familles, multi-causes, subsomption avancée, remplacements multiples, Z3, déploiement entreprise et migrations de versions. La révision simple par chat et le retrait runtime restent dans la cible, pas dans cette liste de reports.

Le [guide pédagogique](14-guide-pedagogique.md) sert de socle documentaire ; chaque jalon enrichit les explications avec ses contrats, exemples observés et commandes réellement livrées.