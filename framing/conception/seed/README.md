# Générateur reproductible hors ligne — Les Trois Rives

## Livrable et frontière

`GenerateDataset.java` est une **simulation de sources**, pas l'application, un importeur SQL, un moteur DMN ou un moteur de diagnostic Python. Il ne lit aucun secret, ne contacte aucun réseau et ne lance aucun conteneur. Java 17 ou ultérieur suffit ; aucune dépendance Maven. Les tests utilisent `unittest` et, pour le seul contrôle JSON Schema, le module Python `jsonschema` déjà disponible dans l'environnement de réalisation.

Autorités : `../00-conventions.md` et `../../09-donnees-evaluation-et-reset.md`. Le premier définit l'interface JSON exportée. Le nom court de profil est `demo`, `validation` ou `holdout` ; `demo-v1` dans le cadrage correspond ici au profil `demo` du générateur version `1.0.0`. Aucun fichier partagé n'est modifié.

### Arborescence

- `seed/GenerateDataset.java` : simulateur Java autonome.
- `seed/runtime.schema.json` : contrat JSON Schema 2020-12 strict, avec payload propre à chacun des huit types d'événement.
- `seed/test_dataset.py` : tests exécutant réellement `javac` et `java`, en répertoire scratch.
- `seed/generated/<profil>/runtime.json` : seules données normalisées susceptibles d'être importées dans le runtime concerné.
- `data/private/<profil>/oracle.json` : vérité injectée, événements masqués et conclusions admissibles distincts.
- `data/private/<profil>/catalogue.md` : catalogue intégral, généré depuis cet oracle, une section pour **chaque** dossier avec ID, symptôme, variante, chronologie, état, preuves et diagnostic admissible.
- `data/private/<profil>/manifest.json` : comptes exacts et SHA-256 des trois fichiers générés ci-dessus. Le manifeste ne se hache pas lui-même.
- `data/verification.md` : résultats et empreintes obtenus pendant la vérification de livraison.

**Isolation à réaliser lors du raccordement à l'application** : ne monter dans le service métier que le `runtime.json` du profil sélectionné. Ne monter ni `seed/`, ni `data/`, ni le dépôt entier dans les workers IA. Les oracles, catalogues et manifestes restent dans un espace de tests/expert. Le runtime holdout lui-même est réservé au banc d'évaluation finale. La séparation de fichiers ici n'est pas une preuve d'isolation Docker, SQL ou réseau ; aucun déploiement n'est livré.

## Reproduire les octets livrés

Depuis n'importe quel répertoire, sous Linux :

```sh
ROOT=/home/kek/projects/library-stock-gap-ai-poc/framing/conception
SCRATCH=${TMPDIR:-"$HOME/.hermes/cache/scratch"}
mkdir -p "$SCRATCH"
BUILD=$(mktemp -d "$SCRATCH/library-seed.XXXXXX")
trap 'rm -rf "$BUILD"' EXIT
javac --release 17 -Xlint:all -d "$BUILD" "$ROOT/seed/GenerateDataset.java"
java -cp "$BUILD" GenerateDataset "$ROOT" 42 demo
java -cp "$BUILD" GenerateDataset "$ROOT" 4242 validation
java -cp "$BUILD" GenerateDataset "$ROOT" 9001 holdout
python "$ROOT/seed/test_dataset.py"
```

Le premier argument est une racine de conception, non un chemin de fichier. Le programme ne crée que `seed/generated/<profil>/` et `data/private/<profil>/` sous cette racine ; une nouvelle exécution écrase les quatre sorties de ce profil, pas les autres profils. N'utiliser qu'une racine locale maîtrisée, sans liens symboliques malveillants. Les classes compilées ne doivent jamais être placées dans le dépôt. Les profils inconnus sont rejetés avant écriture ; la seed est un entier Java `long` signé.

Pour régénérer sans toucher les fixtures livrées, passer une racine dans `$SCRATCH`. Pour éprouver la variation, remplacer `42` par `43`. Les tests comparent les octets de deux générations identiques et constatent également une variation des dates métier, pas seulement des identifiants.

## Profils et tailles exactes

| Profil | Seed livrée | Exemplaires | Dossiers | Sans dossier | Familles | Répartition privée des dossiers |
|---|---:|---:|---:|---:|---|---|
| demo | 42 | 1 200 | 120 | 1 080 | 10 × 12 | 80 positifs, 20 négatifs proches, 20 preuves incomplètes |
| validation | 4242 | 180 | 90 | 90 | 10 × 9 | 30 positifs, 30 négatifs proches, 30 preuves incomplètes |
| holdout | 9001 | 240 | 120 | 120 | 10 × 12 | 40 positifs, 40 négatifs proches, 40 preuves incomplètes |

`POSITIVE` signifie « variante positive du mécanisme pédagogique », pas « cause forcément démontrable ». Ainsi, la famille absence inexpliquée attend une abstention même dans sa variante positive. `NEAR_NEGATIVE` signifie « ne pas conclure au mécanisme ciblé encore actif » ; pour la combinaison, une des deux causes subsiste. Un dossier négatif est préchargé en `A_ANALYSER` à partir d'un signal brut de collecte à contrôler : `COUNT_UNCERTAIN` ne signifie pas qu'un écart final a déjà été établi.

Chaque profil possède son propre espace d'UUID, même avec la même seed. Les sources portent des sites, durées et contre-signaux différents. Les perturbations métier principales datent de deux jours dans demo, trois dans validation, cinq dans holdout, plus une variation inférieure à six heures. Les prêts en attente de mise à jour du stock informatique ont de 360 à 599 secondes dans demo/validation, de 600 à 839 secondes dans holdout, avec une fenêtre contractuelle de 900 secondes. Dans holdout, la réception physique précède de vingt secondes la clôture administrative du prêt ; dans les autres profils la clôture précède la réception de trente secondes. Les listes exportées sont mélangées : leur ordre n'est ni une chronologie ni une étiquette de famille.

Le holdout partage volontairement les mêmes dix mécanismes, mais pas les exemplaires, les seeds, les délais ni toutes les séquences. Ce n'est pas un corpus industriel indépendant : il mesure une généralisation synthétique limitée. Ne pas réviser les règles en lisant ses résultats avant l'évaluation finale.

## Algorithme déterministe, version 1.0.0

1. Initialiser `java.util.Random` avec `seed XOR profile.hashCode() XOR VERSION.hashCode()` ; utiliser le générateur linéaire congruentiel 48 bits standard de Java (`Random`, multiplicateur `0x5DEECE66D`, incrément `0xB`, modulo `2^48`). Aucun `SecureRandom`, horloge système ou appel réseau.
2. Générer les UUID avec `UUID.nameUUIDFromBytes`, sur les octets UTF-8 de `version/profil/seed/clé`. Cela produit des UUID v3 opaques. Les clés publiques sont des compteurs ou index d'entité, jamais des codes de famille. MD5 ici ne protège aucun secret : c'est seulement le mécanisme d'identité reproductible de l'API Java. Les empreintes d'intégrité des fichiers sont bien SHA-256.
3. Créer trois sites, cinq types d'emplacement par site, 300 titres fictifs avec ISBN-13 synthétique à clé valide, puis sélectionner sans remise les exemplaires associés à des dossiers, après mélange Fisher–Yates (`Collections.shuffle` sur `ArrayList`, alimenté par ce `Random`). Le fonds est fictif ; les ISBN ne sont pas une assertion d'inscription dans un catalogue bibliographique réel.
4. Construire un historique cohérent ordinaire : réception physique vers J−120, mise à jour du statut « en rayon » ; prêt à J−60, mise à jour du statut « prêté » ; clôture et retour vers J−40, mise à jour du statut « en rayon ». Un quart des exemplaires reçoit aussi un transfert intersites vers J−30/J−29, avec expédition, réception et deux mises à jour du stock informatique distinctes.
5. Pour les exemplaires sans dossier, tirer l'état courant : 60 % de probabilité en rayon, 20 % prêtés, 8 % en transfert, 6 % au retour en traitement, 6 % en réparation. Ces proportions sont des probabilités de simulation, pas des quotas ni une fréquence empirique. Créer les événements nécessaires à chaque état : un nouveau prêt et sa clôture précèdent le retour récent ; ne jamais réutiliser un prêt ancien déjà rendu. Les observations valides concernent les exemplaires physiquement présents, pas les prêts ou transferts en cours.
6. Appliquer à chaque dossier un scénario de simulation déclaré. Un événement métier décrit l'opération métier ; un `IntegrationProcessed` distinct, corrélé par `originalEventId` et `correlationId`, décrit la réussite, l'échec ou l'attente de sa mise à jour du stock informatique. **Seul `APPLIED` modifie `systemStates`**. Les références de l'état désignent la dernière mise à jour appliquée, et non la dernière tentative échouée.
7. Dans les variantes sans journal récent, simuler d'abord la tentative, puis la retirer du flux public ; conserver l'événement entier dans `injectedTruth.hiddenSourceEvents`. La plage de complétude publiée se termine à J−7, avant la mise à jour masquée. Un scan ambigu n'est jamais promu en preuve valide.
8. Ajouter les symptômes d'entrée et les attentes privées déclarées par le scénario. L'oracle n'infère pas une cause en parcourant les sources et n'exécute ni DMN, ni règle diagnostique. Les tests contrôlent ensuite les implications et contre-exemples de ces déclarations. Il s'agit d'un modèle de simulation, pas d'une vérité terrain indépendante de son concepteur.
9. Mélanger séparément toutes les tables publiques. Trier seulement le catalogue privé par UUID de dossier. Sérialiser avec `LinkedHashMap`, ordre de champs stable, UTF-8, LF et aucune date d'exécution. Hacher les octets effectivement écrits.

Le déterminisme est garanti à code/version/profil/seed identiques avec cette implémentation Java standard. Changer la logique de génération nécessite de changer `VERSION` et de republier les manifestes. Le JSON est volontairement compact ; le catalogue Markdown est le support de lecture humaine.

### Fonds ordinaire effectivement livré dans demo

Les 1 080 exemplaires sans dossier comprennent 637 en rayon, 233 prêtés, 96 en transfert, 60 en traitement de retour et 54 en réparation. Chaque exemplaire normal conserve son historique de réception/prêt/retour ; certains ont également un transfert terminé avant leur état courant. Le manifeste contient les distributions exactes des trois profils.

## Mécanismes et limites des conclusions

| Famille privée | Construction | Positif admissible | Contre-exemple / manque de preuve |
|---|---|---|---|
| LOCALISATION | Affectation au rayon inchangée, déplacement vers la réserve du même site, scan distinct | Écart de localisation seulement ; aucune responsabilité humaine | Négatif : scan au bon endroit ; incomplet : scan ambigu |
| PRET_NON_PROJETE | Prêt métier actif ; mise à jour du stock informatique échouée ; pas de scan physique | Prêt non projeté avec preuve corrélée d'échec | Reprise `APPLIED` plus récente ; ou journal masqué |
| RETOUR_NON_PROJETE | Nouveau prêt répercuté dans le stock, clôture, réception physique ; mise à jour du retour échouée | Retour reçu mais statut informatique encore « prêté » | Reprise appliquée ; ou journal masqué et scan ambigu |
| TRANSFERT_INCOHERENT | Expédition projetée puis réception dans l'autre site, même `transferId` ; réception non projetée | Réception de transfert non répercutée, non disparition | Reprise appliquée ; ou absence de journal récent |
| RECEPTION_NON_INTEGREE | Nouvel exemplaire connu du bordereau ; suppression de toute histoire de catalogue antérieure ; réception physique | Réception non projetée, ligne technique `NOT_REGISTERED` | Négatif désormais `RECEIVING` ; incomplet sans preuve d'échec |
| REPARATION_NON_PROJETEE | Ordre de réparation et présence en atelier ; statut informatique resté « en rayon » | Mise en réparation non répercutée | Reprise appliquée ; ou preuve de traitement masquée |
| INVENTAIRE_INCOMPLET | Session interrompue/échouée, disponibilité par exemplaire partielle ou indisponible | Incertitude de collecte, pas disparition | Négatif : lecture de contrôle valide ; indisponibilité : abstention |
| RETARD_INTEGRATION | Prêt récent, écriture `QUEUED`, âge inférieur à 900 s | Transition attendue dans la fenêtre ; aucune panne persistante prouvée | Traitement déjà appliqué ; ou journal absent |
| ABSENCE_INEXPLIQUEE | Déplacement non instrumenté privé, pas de scan valide et journaux métier partiels | Aucune cause attribuable : `INDETERMINATE` | Scan valide au rayon réfute l'absence ; collecte indisponible renforce l'abstention |
| DEUX_CAUSES | Réparation non répercutée **puis** déplacement distinct de l'atelier vers la réserve | Réparation non répercutée et divergence par rapport à l'emplacement de l'ordre de réparation | Mise à jour finalement réussie : une seule cause ; scan ambigu : diagnostic partiel sur la réparation seulement |

La famille combinée n'est pas la duplication d'un même effet : l'événement `RepairStarted` indique l'atelier, l'observation indique la réserve et l'état projeté n'a pas appliqué la réparation. Les deux écarts sont compatibles et ont deux symptômes. Une règle minimale de localisation ne suffit pas à expliquer le statut de réparation.

`sourceAvailability` décrit la complétude de l'export pour un exemplaire et une source ; `inventoryScopes` décrit une session locale de collecte. Plusieurs sessions peuvent recouvrir le même emplacement. Une session fermée n'annule pas une indisponibilité ou un trou de données signalé pour un exemplaire. Un scan valide peut établir une présence malgré une session incomplète ; une absence de scan ne peut jamais établir une perte. Les notes sont des données auxiliaires, jamais des instructions ni l'unique preuve d'une cause technique.

Les déplacements internes sans type d'événement normalisé dédié sont représentés par l'affectation et le scan, non par l'invention d'un neuvième type d'événement. L'oracle conserve la position physique simulée ; le runtime ne reçoit que les preuves autorisées. Les codes privés de cause sont des attentes de test proposées et non un catalogue de règles actives.

## Coupure temporelle et import futur

La référence et la capture sont fixes : **2026-09-30T12:00:00Z**. Tous les événements, enregistrements, scans et notes précèdent cette référence ; seule la borne de couverture `completeTo` peut lui être égale. `occurredAt` est le temps métier, `recordedAt` l'inscription dans la source ; ces deux dates ne sont pas interchangeables. `capturedAt` ne prétend pas être l'heure réelle d'exécution du générateur.

Ordre d'import proposé, dans une transaction du profil choisi :

1. Vérifier le manifeste, le JSON Schema et les invariants de références et de temps ; refuser un jeu incompatible plutôt que corriger silencieusement ses identifiants.
2. Créer **une nouvelle `datasetEpoch` runtime** et conserver le `datasetId` déterministe. Aucun `datasetEpoch` n'est stocké dans ces fixtures.
3. Importer sites, emplacements, titres puis exemplaires (identités de sources ; `NOT_REGISTERED` ne signifie pas préexistence dans la état du stock informatique).
4. Importer événements métier, puis événements de mise à jour du stock informatique avec leurs références ; trier par dates pour une relecture causale, jamais par position du tableau JSON. Les références entre événements doivent être résolues avant validation des états.
5. Importer disponibilités, sessions, observations, notes et états du stock informatique. Valider que les références de l'état désignent la dernière mise à jour `APPLIED` du même exemplaire.
6. Importer les dossiers et symptômes **déjà présents** ; ne pas les fabriquer en exécutant des règles.
7. Charger `initialRuleSet.version = 1` avec exactement `R001`, version 1, chemin `rules/R001-v1.dmn`. **Ce générateur ne fournit que les métadonnées.** Le DMN et sa ligne unique sont livrés séparément par les ressources du dossier de conception.
8. Publier atomiquement le jeu initial et l'époque nouvellement créée. Ne pas importer oracle, catalogue ou états de missions inexistants. Aucun importeur ni reset d'environnement n'est implémenté ici.

## Vérification et TDD

Les fonctionnalités ont été ajoutées en cycles rouge/vert exécutés : export absent, population absente, retour physique dupliqué, localisation/oracle manquants, scénarios de mise à jour du stock informatique manquants, incertitude/composition absentes, profils encore groupés, fenêtre de complétude trop large, catalogue/manifeste absents, schéma absent, citations des notes absentes, événements masqués non matérialisés, ordre métier holdout non varié. Chaque échec observé a précédé sa correction. Des contrôles relationnels et chronologiques complémentaires éprouvent ensuite l'ensemble.

La suite couvre le compte exact des exemplaires et dossiers, les dix familles, les trois variantes, la distribution normale, les contre-exemples après reprise, les abstentions, les deux causes, les dates et corrélations, les UUID, le compte de règles initiales, les empreintes, l'intégralité des catalogues, la répétition byte-for-byte, le changement de seed, les profils distincts et le rejet de données privées par le schéma public. Les résultats effectifs et les SHA-256 de livraison sont consignés dans `../data/verification.md`.

Aucun résultat ne mesure ici un LLM, une performance métier réelle, l'import SQL ou l'exécution DMN : ces éléments restent hors périmètre.
