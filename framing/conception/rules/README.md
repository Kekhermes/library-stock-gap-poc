# Règle initiale et adaptation des faits

## Ressource livrée

`R001-v1.dmn` est un vrai modèle DMN 1.5, avec une table `COLLECT`, une ligne et deux sorties : causeCode, epistemicStatus. C'est **l'unique règle initiale**. `R001.metadata.json` décrit sa portée ; aucune autre règle n'est embarquée par défaut.

Cette règle constate une localisation différente. Elle ne prétend ni démontrer une faute humaine ni découvrir la procédure qui a dysfonctionné.

## Entrées : transformation sans raisonnement IA

Le schéma `R001-input.schema.json` est fermé. Sa construction ne consulte que le snapshot figé :

| Entrée | Provenance |
|---|---|
| expectedSiteId | `systemStates` de l'exemplaire ; null si absence de situation connue |
| expectedLocationId | `systemStates.locationId`, éventuellement null |
| observedSiteId | Site de l'emplacement de l'observation sélectionnée ; null sans observation unique admissible |
| observedLocationId | Emplacement de cette même observation |
| observationReliable | Qualité connue : une observation `VALID`, pas d'observation concurrente contradictoire/ambiguë, identité résolue et fenêtre temporelle admissible ; null si la qualité ne peut être établie |

Il est interdit de choisir arbitrairement le premier scan dans une liste contenant deux localisations contradictoires. Les scans identiques peuvent être dédupliqués par une règle de qualité documentée, jamais en effaçant leur provenance.

La qualité d'une observation positive n'est pas la complétude d'un inventaire. Une observation fiable peut prouver une présence ; l'absence de scan exige un périmètre complet pour soutenir une absence. Le booléen de qualité ne doit pas contenir un diagnostic caché.

## Adaptation de sortie

Le moteur renvoie une liste vide ou une liste contenant :

```json
{"causeCode":"LOCATION_DIFFERENCE","epistemicStatus":"SUPPORTED"}
```

L'adaptateur Java enrichit le résultat avec la version exacte de la règle, les lignes correspondantes, le copyId, les références vers le `systemState` et l'observation, et **uniquement les symptomIds de type LOCATION_MISMATCH**. Il ne prétend pas couvrir les autres symptômes.

Décider que le cas est entièrement expliqué relève du service de combinaison de diagnostics : union des symptômes couverts, contrôle des contradictions et qualité des preuves. Une ligne DMN qui correspond n'équivaut pas à une analyse complète.

## Vérification réelle effectuée

Le modèle a été chargé et évalué par Apache KIE `10.2.0` sous Java `21.0.12.1`, sur la machine de conception. Huit tests vérifient le cas nominal, même emplacement, autre site, qualité fausse/inconnue et lieux/sites manquants. Cette exécution ne valide pas encore l'intégration Spring Boot ni le runtime cible Java 25.

Le harnais `../verification/VerifyBootstrapDmn.java` utilise `org.kie.dmn.core.internal.utils.DMNRuntimeBuilder`, identifié dans l'artefact réel. **Il s'agit d'une API interne utilisée seulement pour vérifier cette ressource** ; encapsuler/adopter une API appropriée avant d'en faire une dépendance de production. Aucun backend n'est livré par ce test.

```bash
# Depuis framing/conception ; répertoire temporaire à adapter.
WORK="$HOME/.hermes/cache/scratch/stock-gap-design-dmn"
mvn -q -f verification/dmn-pom.xml dependency:copy-dependencies -DoutputDirectory="$WORK/lib"
javac -cp "$WORK/lib/*" -d "$WORK" verification/VerifyBootstrapDmn.java
java -cp "$WORK:$WORK/lib/*" VerifyBootstrapDmn rules/R001-v1.dmn
python -m unittest discover -s verification -p test_bootstrap.py -v
```

Le téléchargement Maven nécessite le réseau au premier passage ; le générateur de données lui-même n'en a pas besoin. Les avertissements SLF4J du harnais indiquent l'absence de fournisseur de logs, pas un échec du modèle.

## Contrat de génération ultérieure des règles

L'agent de règles fournit DMN + métadonnées + justification + exemples positifs/négatifs + opérations coordonnées. Java contrôle schéma, syntaxe/types, fonctions autorisées, compilation, références, tests et replay. Une revue humaine explicite porte sur une révision figée.

Un nouveau code de cause peut être proposé comme métadonnée versionnée, mais un nouvel attribut d'entrée ou une fonction hôte nécessite une évolution de contrat. Aucun mécanisme n'autorise l'agent à injecter Java, Python, SQL ou des E/S dans FEEL.

## Limitations assumées

Le harnais ne prouve pas qu'un DMN arbitraire généré par IA est sûr. L'implémentation devra limiter l'AST FEEL, la taille des listes, les imports, les fonctions externes et le temps d'évaluation. L'absence de contrôle SMT est un choix du cadrage ; ne pas qualifier les tests de preuve générale de non-régression.
