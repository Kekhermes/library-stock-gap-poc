# Précisions de conception par rapport au cadrage

Ces décisions de détail rendent le cadrage réalisable. Elles n'autorisent pas une modification de périmètre ; elles restent lisibles et révisables par Kevin avant implémentation.

## ADR-01 — Données publiques et oracle privé

**Choix :** export normalisé runtime sans cause injectée ; oracle et catalogue des cas dans un répertoire distinct. Le code source du générateur lui-même n'est pas une ressource accessible à l'agent en fonctionnement.

**Raison :** un scénario peut être impossible à diagnostiquer à partir des seules observations. L'évaluation compare les conclusions admissibles, pas seulement la cause injectée.

**Conséquence :** copier seulement `runtime.json` dans l'import applicatif. Les fichiers privés ne sont ni un RAG ni une source d'outils IA.

## ADR-02 — Dataset reproductible, époque runtime renouvelée

**Choix :** datasetId/seed/profil sont stables ; datasetEpoch identifie une exécution chargée et change après reset.

**Raison :** reproduire exactement les données sans accepter un événement retardé d'une instance précédente.

**Conséquence :** les enveloppes Kafka portent datasetEpoch ; le générateur n'écrit pas une époque prétendument vivante dans ses fixtures.

## ADR-03 — Ledger d'événements source normalisé

**Choix :** l'export fournit `sourceEvents` avec types métier et payloads documentés, plutôt que des tables d'export incompatibles pour chaque source. Le modèle SQL de référence détermine sa persistance ; des vues/projections typées fournissent les faits aux règles.

**Raison :** conserver provenance, deux temps, corrélations et variété sans cacher le diagnostic dans une propriété booléenne pré-calculée.

**Conséquence :** l'importeur valide les payloads par type ; il ne stocke pas un JSON opaque non contrôlé. Ce choix précise les tables candidates du cadrage, il ne supprime pas les domaines prêts/retours/réceptions/transferts.

## ADR-04 — Non-proposition de règle explicite

**Choix :** ajouter `RuleChangeNotProposed` aux événements initialement esquissés. C'est une issue normale de synthèse/révision : NO_CHANGE, INSUFFICIENT_EVIDENCE ou UNSUPPORTED_CAPABILITY.

**Raison :** éviter d'utiliser une panne technique pour exprimer un jugement métier honnête ou de forcer une règle impossible à généraliser.

**Conséquence :** aucune proposition active créée, contexte et explication conservés, budget consommé visible. Un chat peut aussi terminer sans proposer de règle ; sa fin est explicitement marquée dans le protocole.

## ADR-05 — Une seule règle ne signifie pas un moteur incapable de valider

**Choix :** une règle métier bootstrap R001, une ligne DMN. Qualité des données, types, permissions et états restent des invariants applicatifs, pas des règles de cause supplémentaires.

**Raison :** éviter à la fois un catalogue initial caché et un système qui pourrait publier n'importe quoi au nom de la simplicité.

**Conséquence :** R001 ne couvre que LOCATION_MISMATCH ; l'agrégateur doit détecter les autres symptômes non expliqués avant de décider de ne pas appeler l'agent.

## ADR-06 — Artefacts testables sans construire l'application

**Choix :** fournir générateur Java autonome, schemas/exemples, SQL de référence, DMN réel et vérifications locales séparées. Pas de skeleton Spring/Vue/Python applicatif, de Docker Compose lancé ou de client LLM opérationnel livré.

**Raison :** Kevin réalise lui-même le projet avec ses outils ; les ressources doivent lui permettre d'implémenter, non lui imposer une application commencée par un autre outil.

**Conséquence :** résultats de validation documentaire/fixtures distincts de tests entre composants du futur produit. Les outils Python de vérification ne portent aucune logique métier runtime.

## ADR-07 — Versions de contrat et compatibilité

**Choix :** schemaVersion `1.0.0` pour les documents/exports ; suffixe `.v1` pour les topics. Les nouvelles valeurs d'enum et champs requis peuvent casser un consommateur même si un numéro a été incrémenté.

**Conséquence :** fixtures compatibles par version ; un type/version inconnu va en erreur contrôlée/dead-letter, pas dans un `dict` interprété librement. Évolution breaking : nouveau contrat/topic majeur ou migration coordonnée explicitement testée.

## ADR-08 — Séparer cas, diagnostic et règle

Un cas ne devient pas « stock corrigé ». Une analyse peut être terminée avec un diagnostic indéterminé. Un diagnostic accepté peut ne produire aucune règle. Une publication n'actualise pas les analyses passées.

Cette séparation doit apparaître dans les objets Java, tables, réponses API et boutons UI. Un unique statut générique `DONE` ne peut pas remplacer ces dimensions.
