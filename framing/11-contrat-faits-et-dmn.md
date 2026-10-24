# Contrat des faits et des règles DMN/FEEL

## Statut

DMN/FEEL, moteur Apache KIE, collecte des règles compatibles et fallback en cas d'insuffisance sont validés. Le contrat détaillé de cette page est **PROPOSÉ** ; il devra être éprouvé sur le jeu de scénarios avant gel d'implémentation.

Il ne s'agit pas d'un DSL propriétaire masqué : les conditions exécutables sont en FEEL et les décisions en DMN. Le schéma applicatif définit seulement les données, métadonnées, sorties et autorisations nécessaires à notre domaine.

## 1. Unité de connaissance

Proposition : une règle métier est un module DMN autonome, avec identité stable, version immuable et décision de sortie nommée. Une table peut avoir plusieurs lignes pour exprimer des variantes de la même règle ; chaque ligne a un identifiant de trace.

Au seed, exception de simplicité explicitement demandée : **un module, une table, une seule ligne de diagnostic**. Aucun ensemble de règles caché n'est chargé en parallèle.

Les règles n'importent pas arbitrairement d'autres règles actives. Une publication contient le manifeste de toutes les versions évaluables. Cette indépendance facilite le retrait sans casser une chaîne de dépendances implicites.

## 2. Snapshot d'entrée

Structure logique candidate :

```text
CaseFacts (projections scalaires aplaties pour FEEL/DMN) :
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
  - latestProjectedStatus : string (statut visé par la projection)
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
- Un événement métier antérieur enregistré tardivement est conservé avec les deux dates ; son admissibilité dépend de la date de capture du snapshot.
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

La règle ne retourne ni objet Java arbitraire ni action exécutable. Les références sont vérifiées contre le snapshot ; une référence inventée invalide la sortie. Le moteur ne reçoit pas d'autorisation d'écrire sur le stock.

Le vocabulaire de causes est un référentiel métier, pas un `enum` Java à enrichir par compilation à chaque règle. Si un nouveau code de cause est nécessaire, l'agent propose sa définition comme métadonnée versionnée dans le même changement ; la validation humaine couvre cette extension. Les aspects utilisés pour détecter les contradictions restent gouvernés et ne peuvent pas être réinventés librement par une proposition.

## 6. Collecte et résolution de résultats

1. Évaluer toutes les règles du snapshot de publication.
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
