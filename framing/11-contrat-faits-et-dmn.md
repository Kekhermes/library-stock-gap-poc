# Contrat des faits et des règles DMN/FEEL

## Contrat cible partiellement arbitré — 2026-10-31

### État d'exécution actuel — M2

Le service Java applique maintenant la sélection admissible des observations et opérations métier, la dernière tentative connue pour la même opération, la disponibilité des sources et une provenance des faits. Les valeurs inconnues sont des `null`, pas des booléens ou compteurs fabriqués.

| Faits utilisés | Type actuel | Sélection et portée |
|---|---|---|
| `symptomKind`, `expectedValue`, `observedValue` | Chaîne ou null | Symptôme du dossier mono-symptôme de démo ; valeur observée inconnue si le constat n'est pas fiable |
| `systemStatus`, `expectedStatus`, sites/lieux attendus | Chaîne ou null | État informatique du dossier de preuves figé ; provenance de cet état |
| `observedSiteId`, `observedLocationId`, `observationQuality`, `observationReliable` | Chaînes ou booléen nullable | Une observation unique valide avant la référence ; les constats ambigus/absents ne donnent pas de lieu certain |
| `latestBusinessEventType` | Chaîne ou null | Dernière opération datée parmi prêt, retour, réception, transfert et réparation ; clôture administrative conservée en preuve, pas confondue avec réception du retour |
| `latestIntegrationOutcome`, `latestIntegrationErrorCode`, `latestProjectedStatus` | Chaîne ou null | Dernière tentative admissible de mise à jour de cette même opération ; corrélation vérifiée lorsqu'elle est présente |
| Disponibilités des sources et compteurs | Chaîne ou entier nullable | Une source complète doit couvrir la référence ; absence/incomplétude interdit de conclure à partir d'un zéro fictif |

Les noms techniques d'entrée existants sont conservés ; leur sens explicite reste « mise à jour du stock informatique ». La provenance est regroupée dans `factProvenance` et les traces d'évaluation ; ce n'est pas une nouvelle condition métier ni un accès à l'oracle.

Une règle avec entrée indispensable inconnue n'est pas évaluée comme si cette entrée était fausse. Le résultat est un besoin d'investigation identifié. Une hypothèse seule ne suffit pas à couvrir le dossier. Pour la démo mono-cause, des codes de cause distincts concurrentiels sur un même symptôme entraînent un relais conservateur, sans prétendre démontrer mathématiquement une contradiction. Des règles de même cause conservent leurs contributions et preuves dans le diagnostic et les traces.

Les sorties DMN sont désormais contrôlées : `causeCode` chaîne non vide et `epistemicStatus` `SUPPORTED` ou `HYPOTHESIS`. Les pannes de compilation/évaluation et les sorties non conformes restent des erreurs techniques, pas `NO_RULE_MATCH`. Elles sont persistées et ne déclenchent pas de mission LLM.

**Encore ouverts :** structure JSON des conditions générées, restrictions et bornes d'exécution, analyse générale des conflits et traces détaillées par prédicat/ligne. Les tests/replay de publication restent à implémenter. Voir [les preuves de réalisation](12-etat-implementation-actuel.md).

[L'interview et les décisions N19–N28](04-registre-des-decisions.md) fixent le résultat : le LLM produit les conditions FEEL ; Java sérialise le DMN standard, compile, teste et publie la révision approuvée au runtime. Les vagues d'arbitrage ci-dessous sont validées ; les détails historiques plus bas ne font pas autorité lorsqu'ils les contredisent. Le [glossaire explicite](06-domaine-ddd-et-cycles-de-vie.md) fait référence pour les discussions et les livrables.

### Première vague validée

| Sujet | Décision | Portée initiale |
|---|---|---|
| Unité de règle | Une table indépendante par règle métier, une ligne au départ | Identité, version, tests et retrait indépendants ; pas de table commune regroupant tout |
| Entrées | Faits simples, typés et corrélés par Java | FEEL ne parcourt pas les historiques bruts ; la normalisation Java ne choisit pas la cause |
| Expressions | Comparaisons simples, booléens, valeurs manquantes | Égalité, différence, ordre, appartenance, and/or ; pas de dates/durées, filtres ou quantifications d'emblée |
| Insuffisance/conflit | Fallback LLM avec motif visible | L'investigateur peut encore s'abstenir ; une panne moteur reste une erreur technique distincte |
| Conclusions équivalentes | Diagnostic unique, contributions conservées | Dédupliquer la conclusion tout en gardant toutes les règles et preuves pertinentes |

Une comparaison de valeurs déjà normalisées est autorisée ; l'appartenance à un ensemble de littéraux ne signifie pas autoriser des filtres sur l'historique. Une extension utile devra être explicitement discutée, pas introduite silencieusement par le LLM.

Java peut retrouver le résultat de mise à jour du stock informatique de la bonne opération métier et l'exposer avec sa provenance. Il ne doit pas fournir un booléen `retourNonRepercute` ou une conclusion déjà décidée : la combinaison de conditions qui produit la cause appartient à la règle FEEL générée.

La ligne unique d'une règle ne fixe pas à elle seule la politique DMN `UNIQUE` ou `COLLECT`, ni la définition des conclusions équivalentes. La question des lignes multiples et ce choix technique sont reportés ; R001 existante n'est pas modifiée par cette décision documentaire.

### Deuxième vague validée — données et lecture des conditions

Les règles de la démo disposent d'un petit ensemble de **données observables**, sans diagnostic déjà décidé :

| Données retenues | Sens métier | Provenance à conserver |
|---|---|---|
| État du stock informatique | Statut et emplacement indiqués pour l'exemplaire | Situation informatique enregistrée |
| Lieux et observations d'inventaire | Ce qui a été attendu et constaté physiquement | Scan ou constat, exemplaire, lieu et date |
| Qualité du constat | Observation fiable, ambiguë ou absente | Données de qualité et éventuelles observations contradictoires |
| Traces d'opérations métier | Prêt, retour ou réparation enregistrés pour l'exemplaire | Événements métier et leurs identifiants |
| Résultats de mises à jour correspondantes | Réussite, échec ou attente de la mise à jour liée à la bonne opération métier | Tentatives et lien avec cette opération, pas un échec quelconque dans l'historique |
| Disponibilité des sources | Ce qui est complet, partiel ou indisponible | Source et période connue |

Les noms techniques, types précis et règles de sélection seront rédigés dans le contrat ; cette liste ne valide pas chaque champ du catalogue historique ci-dessous. Les dates servent à sélectionner et justifier les preuves ; elles ne rajoutent pas de calcul temporel FEEL à la démo.

**Mise à jour reprise :** exposer le résultat de la dernière tentative connue pour la même opération métier. L'historique reste consultable dans le dossier de preuves figé. Si un retour a un échec puis une mise à jour réussie, l'échec ancien ne prouve pas une panne encore active. Si la source ne permet pas de connaître les tentatives suivantes, signaler cette limite au lieu de déclarer la situation certaine.

**Conditions séparées :** chaque condition produite par le LLM a une question métier lisible, son expression FEEL réelle et un résultat d'essai vrai/faux/inconnu. Java sérialise la structure DMN ; il ne fournit pas de réponses préfabriquées par cause. La forme JSON exacte reste ouverte.

**Une colonne et une ligne ne désignent pas la même chose :** une colonne contient une condition ; une ligne contient les valeurs attendues pour les conditions et le résultat associé. Exemple pédagogique de présentation, pas une règle préchargée :

| Retour enregistré ? | Stock encore « prêté » ? | Mise à jour de ce retour échouée ? | Résultat |
|---|---|---|---|
| Vrai | Vrai | Vrai | Retour non répercuté |

Pour cette ligne, toutes les conditions doivent être satisfaites. L'expression FEEL et ses preuves sont consultables pour chaque condition. Une condition indispensable inconnue ne doit pas être présentée comme fausse pour fabriquer un résultat certain ; l'insuffisance déclenche le relais vers l'investigateur selon N22.

La démo garde **une seule ligne par table de règle**. Le choix relatif aux lignes multiples, `UNIQUE` ou `COLLECT`, est reporté. La déduplication des contributions de plusieurs règles différentes reste acquise et constitue un autre sujet.

Principes conservés : faits structurés typés et sourcés, aucune IA dans leur préparation déterministe, pas d'oracle ni de conclusion préexistante exigée, aucune fonction hôte ou nouvelle source ajoutée librement par l'agent. Le JSON de proposition décrit des expressions FEEL natives et métadonnées ; il ne doit pas devenir un langage de diagnostic propriétaire avec une méthode par cause.

Le contrat devra permettre une règle simple par famille inconnue reconnaissant trois autres dossiers vierges. Les faits doivent distinguer retour et réparation, corréler la mise à jour du stock informatique à la bonne opération métier et tenir compte d'une reprise réussie. Une absence de source ne devient pas `false`.

Le rapport et la trace seront liés au contenu exécuté : types, conditions vraies/fausses/inconnues, décision/ligne, symptômes effectivement couverts et preuves. Le contrat du candidat, les schémas/API, la sérialisation DMN et le rendu UI seront modifiés ensemble ; ce document seul ne livre pas ces contrôles.

**À préciser ensuite :** noms/types/provenance et sélection exacte des données retenues, forme JSON des conditions séparées, définition d'équivalence/conflit, restrictions et limites d'évaluation. Les lignes multiples et le choix `UNIQUE`/`COLLECT` sont reportés. Le périmètre FEEL simple et le choix des faits corrélés sont acquis, sans validation implicite de tous les détails. La composition multi-cause avancée n'est pas requise pour le parcours initial. Voir [M0–M3](13-roadmap-demo.md), [l'audit](12-etat-implementation-actuel.md) et [le guide](14-guide-pedagogique.md).

## Détails historiques — non globalement approuvés

## Statut

DMN/FEEL, moteur Apache KIE, collecte des règles compatibles et fallback en cas d'insuffisance sont validés. Le contrat détaillé de cette page est **PROPOSÉ** ; il devra être éprouvé sur le jeu de scénarios avant gel d'implémentation.

Il ne s'agit pas d'un DSL propriétaire masqué : les conditions exécutables sont en FEEL et les décisions en DMN. Le schéma applicatif définit seulement les données, métadonnées, sorties et autorisations nécessaires à notre domaine.

## 1. Unité de connaissance

Proposition : une règle métier est un module DMN autonome, avec identité stable, version immuable et décision de sortie nommée. Une table peut avoir plusieurs lignes pour exprimer des variantes de la même règle ; chaque ligne a un identifiant de trace.

Au seed, exception de simplicité explicitement demandée : **un module, une table, une seule ligne de diagnostic**. Aucun ensemble de règles caché n'est chargé en parallèle.

Les règles n'importent pas arbitrairement d'autres règles actives. Une publication contient le manifeste de toutes les versions évaluables. Cette indépendance facilite le retrait sans casser une chaîne de dépendances implicites.

## 2. Faits d'entrée

Structure logique candidate :

```text
CaseFacts (faits simples et typés pour FEEL/DMN) :
  - symptomKind : string ("STATUS_MISMATCH", "LOCATION_MISMATCH", "UNEXPECTED_PRESENCE", "MISSING", "COUNT_UNCERTAIN")
  - expectedValue : string (statut ou valeur attendue, ex: "ON_LOAN", "IN_TRANSIT", "ON_SHELF")
  - observedValue : string (statut ou valeur observée, ex: "RECEIVING", "IN_REPAIR")
  - expectedStatus : string (statut système attendu)
  - observedStatus : string (statut physique constaté)
  - systemStatus : string (statut actuel au SIGB)
  - expectedSiteId : string (site attendu)
  - observedSiteId : string (site constaté)
  - expectedLocationId : string (emplacement attendu)
  - observedLocationId : string (emplacement constaté)
  - observationReliable : boolean (true si quality = "VALID")
  - observationQuality : string ("VALID", "AMBIGUOUS", "REJECTED")
  - latestIntegrationOutcome : string ("FAILED", "APPLIED", "QUEUED")
  - latestIntegrationErrorCode : string (ex: "PROJECTION_WRITE_UNAVAILABLE")
  - latestProjectedStatus : string (statut que la mise à jour du stock informatique devait enregistrer)
  - integrationsAvailabilityStatus : string ("COMPLETE", "PARTIAL", "UNAVAILABLE")
  - loansAvailabilityStatus, returnsAvailabilityStatus, transfersAvailabilityStatus, repairsAvailabilityStatus, inventoryAvailabilityStatus : string
  - loansCount, loansClosedCount, returnsCount, transfersDispatchedCount, transfersReceivedCount, repairsCount : integer
```

Les noms définitifs sont stabilisés avec les tests contractuels. Dates normalisées, identités opaques et valeurs énumérées versionnées. Les listes sont bornées selon un périmètre temporel explicite ; une liste tronquée ne doit pas être déclarée complète.

Attention primordiale : les règles DMN s'exécutent lors de l'**analyse déterministe pré-LLM** sur des dossiers vierges (`A_ANALYSER`). Aucun champ de conclusion préexistante (`diagnosticConclusion`, `acceptedDiagnosticConclusion`) ne doit être exigé par les conditions DMN.

Les notes libres sont dans le dossier de preuves accessible à l'investigateur, **pas dans les conditions FEEL**. Aucun champ `expectedCause`, `scenarioFamily`, `injectedFault` ou équivalent n'existe dans ce contrat.

### Ce qui relève de Java

Chargement des événements, identité, qualité/couverture des sources, normalisation et provenance. Les dérivations élémentaires partagées sont autorisées (par exemple comparer deux lieux), mais pas une méthode par cause qui ferait tout le diagnostic avant DMN.

### Ce qui relève de FEEL/DMN

Corréler les faits disponibles, exprimer les conditions temporelles et exceptions, produire la conclusion et les preuves requises. L'agent peut proposer de nouvelles combinaisons à partir du contrat publié.

### Ce qui n'est pas extensible sans évolution du logiciel

Nouvelle source, nouvel attribut non exposé, nouvel effet externe ou nouvelle fonction hôte. Une telle proposition est refusée avec `UNSUPPORTED_CAPABILITY` et explication. Ajouter une règle runtime n'autorise pas à installer du code généré.

## 3. Absence, inconnus et clôture des sources

- `null` ou indisponibilité ne deviennent pas implicitement `false`.
- « Aucun retour » nécessite une source complète sur la période interrogée.
- Un événement postérieur au temps de référence ne prouve pas la situation à ce temps.
- Un événement métier antérieur enregistré tardivement est conservé avec les deux dates ; son admissibilité dépend de la date de capture du dossier de preuves figé.
- Les incertitudes de scan et de périmètre sont des faits de premier ordre, pas des détails cachés dans un log.

Le backend teste les cas limites de la sémantique FEEL retenue. Il ne suppose pas qu'une table est correcte parce que le XML se parse.

## 4. Sous-ensemble autorisé proposé

Autoriser : tables de décision, expressions FEEL typées, booléens, chaînes, nombres, dates/durées normalisées, comparaisons, listes/contextes, filtres, quantification bornée, agrégations bornées, opérateurs de combinaison.

Refuser : appels Java externes, imports de modèles non présents au manifeste, accès à l'heure courante, fonctions aléatoires, E/S, fonctions hôtes injectées, extensions non explicitement validées et expressions dépassant les limites de complexité.

Les limites portent sur taille d'artefact, nombre de lignes/nœuds d'expression, profondeur, listes d'entrée et temps d'évaluation. Une évaluation trop coûteuse est une erreur technique visible. Le caractère déclaratif ne suffit pas à garantir un temps borné.

La validation d'un sous-ensemble FEEL n'est pas une preuve formelle de sûreté complète. Les mécanismes disponibles dans KIE et l'isolation nécessaire devront être validés lors du gate technique ; aucune promesse de sandbox générale ne doit être faite sans test.

## 5. Contrat de sortie proposé

```text
Finding
  ruleId, ruleVersion, decisionId, rowIds
  subjectCopyId
  coveredSymptomIds[]
  causeCode
  epistemicStatus: SUPPORTED | HYPOTHESIS
  assertions[]: aspect, value, scope
  evidenceRefs[]: sourceType, sourceId, factPath
  contradictionRefs[]
  recommendedChecks[]
  explanationTemplateId / bounded explanation parameters
```

Au niveau de la table de décision DMN, chaque règle doit produire au minimum :
- `<output id="out_cause" name="causeCode" typeRef="string"/>` : le code de cause normalisé (ex. `"PROJECTION_WRITE_UNAVAILABLE"`, `"LOCATION_DIFFERENCE"`).
- `<output id="out_epistemic" name="epistemicStatus" typeRef="string"/>` : statut épistémique (`"SUPPORTED"`).

La règle ne retourne ni objet Java arbitraire ni action exécutable. Les références sont vérifiées contre le dossier de preuves figé ; une référence inventée invalide la sortie. Le moteur ne reçoit pas d'autorisation d'écrire sur le stock.

Le vocabulaire de causes est un référentiel métier, pas un `enum` Java à enrichir par compilation à chaque règle. Si un nouveau code de cause est nécessaire, l'agent propose sa définition comme métadonnée versionnée dans le même changement ; la validation humaine couvre cette extension. Les aspects utilisés pour détecter les contradictions restent gouvernés et ne peuvent pas être réinventés librement par une proposition.

## 6. Collecte et résolution de résultats

1. Évaluer toutes les règles du version figée des règles.
2. Collecter leurs sorties sans dépendre d'un ordre d'arrivée ou d'un « premier gagnant ».
3. Dédupliquer les conclusions identiques tout en conservant les règles et preuves contributrices.
4. Comparer les assertions portant sur le même sujet/aspect/périmètre temporel.
5. Combiner les contributions compatibles et vérifier la couverture des symptômes.
6. Conclure déterministement seulement si la couverture et le degré d'étayage sont suffisants ; sinon fallback motivé.

Exemple compatible : retour non intégré et exemplaire observé au mauvais emplacement expliquent deux dimensions différentes. Exemple contradictoire : deux conclusions affirment des localisations physiques exclusives au même instant et avec la même portée.

La simple différence de code cause n'est pas une preuve de contradiction. L'absence de contradiction détectée par ce modèle n'est pas une preuve que toutes les causes sont vraies.

## 7. Proposition produite par l'agent de règles

Le message structuré contient : contexte du diagnostic accepté, version de référentiel de départ, opérations CREATE/REVISE/RETIRE, DMN candidat, explication métier, preuves, périmètre, raisons de généralisation, exemples positifs et négatifs proposés, justification des remplacements et éventuelles limites.

Java valide l'enveloppe, le DMN et les métadonnées. Il peut normaliser/sérialiser le DMN sans inventer les conditions à la place de l'agent. Les fichiers DMN persistés permettent d'inspecter la règle réelle, pas une traduction approximative affichée par l'UI.

Pour une révision, le diff montre conditions ajoutées/retirées, seuils changés, sorties modifiées et changement de portée. Pour un retrait, expliquer la règle remplaçante éventuelle et la perte de couverture observée.

## 8. Validation et preuve de généralisation

Rapport proposé :
- validité XML/DMN, types, fonctions autorisées et références ;
- résultat des tests proposés par l'agent, identifiés comme tels ;
- résultat des tests du corpus de validation indépendant de la proposition ;
- replay avant/après et détail de chaque diagnostic modifié ;
- conflits constatés et contre-exemples disponibles ;
- avertissements sur règles trop spécifiques, identifiants littéraux ou fenêtres temporelles arbitraires ;
- version/empreinte des artefacts et du corpus.

**Blocages** : modèle invalide, fonction interdite, preuve inventée, violation d'invariant, faux positif connu non accepté comme correction d'un oracle erroné, contradiction non résolue, résultat incomplet de validation ou référentiel de départ périmé.

**Avertissements** : couverture empirique faible, domaine de validité étroit ou généralisation limitée. L'Admin peut approuver un périmètre étroit compris, mais ne peut pas contourner un blocage technique par un message de chat.

Une mise à jour du corpus/oracle est un acte de maintenance du jeu pédagogique, pas un outil disponible à l'agent pour faire passer ses tests.

## 9. Exemple pédagogique de progression

- Une règle de retour non intégré est créée à partir d'un premier diagnostic accepté.
- Une variante sur un autre site révèle une condition inutilement spécifique ; l'Admin demande une généralisation par chat.
- L'agent révise la règle ou en propose une nouvelle avec retrait de l'ancienne.
- Le replay montre les nouveaux cas couverts et préserve les négatifs proches.
- La publication change le référentiel immédiatement ; les analyses anciennes gardent la version qui les a expliquées.

La subsomption est ici soutenue par la structure et le corpus de tests, pas déclarée mathématiquement démontrée. Z3 est explicitement hors premier périmètre.
