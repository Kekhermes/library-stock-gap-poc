# Boucle hybride et règles runtime

## Cible actuelle — 2026-10-31

Les [réponses N01–N18](04-registre-des-decisions.md) et la [vision révisée](00-vision-et-exigences.md) prévalent sur les détails historiques ci-dessous.

Boucle obligatoire : dossier vierge non couvert → investigateur OpenAI outillé → diagnostic sourcé → acceptation humaine → agent OpenAI proposant les conditions FEEL → sérialisation DMN standard par Java → vrais tests → approbation humaine → publication runtime → trois autres dossiers analogues reconnus sans LLM. Deux familles doivent pouvoir suivre cette boucle indépendamment ; R001 couvre déjà deux dossiers au départ.

Le LLM choisit réellement la logique. Un gabarit Java peut construire les éléments XML, mais pas fournir les conditions métier préfabriquées. Les règles recombinent des faits structurés disponibles sans IA ; aucun diagnostic forcé dans les entrées, aucune nouvelle source ou fonction hôte ajoutée par du code généré.

Le chat explique les preuves, recueille une correction et affine une règle. Réévaluation sur demande ; nouveau diagnostic à accepter si modifié. Toute révision de règle invalide ses tests précédents. Le rapport d'échec peut déclencher une correction automatique bornée de l'agent ; refus persistant visible, sans activation forcée.

La gestion dynamique doit montrer versions, contenu exact, essais et retrait. Les nouvelles analyses prennent la publication confirmée ; les analyses en cours gardent leur version. Une transaction de pointeur seule ne prouve ni que la bonne révision a été testée ni que le manifeste complet compile.

**OUVERTS pour l'atelier DMN/FEEL :** sous-ensemble, représentation des conditions, typage/provenance des faits, instrumentation des résultats vrais/faux/inconnus, restrictions et bornes d'évaluation. Pas de quota de consommation fixé maintenant ; ne pas confondre « open bar » et correction sans fin.

La composition multi-cause et les remplacements coordonnés avancés ne pilotent plus la première démo. Voir [l'audit des défauts actuels](12-etat-implementation-actuel.md), [M2–M6](13-roadmap-demo.md) et [l'explication pédagogique](14-guide-pedagogique.md).

## Spécification détaillée historique — à réconcilier

## 1. Décisions acquises et portée de cette page

Acquis : déterministe en premier ; fallback automatique visible, borné et annulable ; causes étayées ou hypothèses incertaines avec preuves et recommandations ; validation humaine ; ajout/modification/retrait runtime ; historique conservé ; stock source en lecture seule.

Les mécanismes détaillés ci-dessous sont **PROPOSÉS**, sauf mention contraire.

**Décisions du 2026-10-24 :** DMN/FEEL avec moteur Java existant ; notes pour l'investigation uniquement et conditions déterministes sur faits structurés sans IA ; validation par types, tests et replay, sans Z3 dans le premier périmètre ; deux actes humains distincts pour diagnostic et changement de règles ; publication atomique avec dossiers de preuves figés conservés par les analyses en cours. Apache KIE embarqué est retenu. Collecter les règles applicables, combiner les conclusions compatibles et déclencher le fallback sur conflit, couverture partielle ou hypothèse insuffisamment étayée. Les types d'entrée et le sous-ensemble FEEL restent à spécifier en détail.

## 2. Contrat de résultat proposé

Ne pas fusionner les dimensions suivantes dans un unique statut :

- **Exécution** : en cours, terminée, annulée, en échec.
- **Couverture** : totale, partielle, aucune, conflit.
- **Conclusion** : cause étayée, hypothèse, indéterminé.
- **Provenance** : déterministe, agentique, revue humaine.
- **Acceptation humaine** : non revue, acceptée, rejetée.

Une analyse peut être terminée techniquement tout en restant indéterminée métier. Plusieurs hypothèses ne doivent pas être affichées comme plusieurs causes simultanément prouvées.

Chaque conclusion référence les observations et événements sources, les prédicats évalués, les contradictions éventuelles et la version du référentiel. Les recommandations n'effectuent aucune modification de stock.

### Quand le déterministe suffit-il ?

Proposition : ne pas déclencher de LLM si une conclusion étayée couvre les dimensions de l'écart sans contradiction. Une règle produisant une hypothèse peut contribuer au dossier mais ne suffit pas automatiquement à court-circuiter l'investigation.

Motifs explicites de fallback : `NO_MATCH`, `PARTIAL_COVERAGE`, `CONFLICT`, `INSUFFICIENT_EVIDENCE`. Une erreur technique d'évaluation est distincte : elle doit être signalée comme panne, pas maquillée en cas métier inconnu.

Un cas durablement indécidable ne doit pas déclencher une boucle infinie : une exécution agentique s'arrête et conserve son résultat. Une nouvelle tentative est une action identifiée, pas une relance implicite sur chaque lecture de page.

## 3. Faits, observations et données absentes

Proposition : les deux voies consultent les mêmes sources métier versionnées via des outils contrôlés. Le déterministe ne bénéficie pas d'étiquettes du générateur ; l'agent ne peut pas lire l'oracle.

Un fait dérivé comporte valeur, type, provenance et état de disponibilité. Distinguer `FALSE` de `UNKNOWN` : « aucun prêt trouvé dans une source complète » n'est pas « source des prêts indisponible ».

Le catalogue des faits doit exprimer des propriétés générales : prêt actif à la date de référence, réception antérieure, événement de mise à jour du stock informatique rejeté corrélé à une opération métier, présence observée ailleurs. Éviter les faits déjà diagnostiques comme `causeEstRetourNonIntegre`, qui cacheraient la règle apprise dans le code Java.

## 4. Notes libres : une frontière déterminante

L'Admin a choisi quelques notes opérateur sans RAG. Leur lecture directe par l'agent n'est pas un RAG et ne nécessite pas de base vectorielle.

Mais une règle disant « si la note signifie que le lecteur a rendu le livre » n'est pas déterministe si elle exige un LLM à chaque nouveau cas.

### Option retenue pour le premier périmètre

- Les notes aident à l'investigation et au dialogue.
- Les règles actives portent sur des faits structurés, calculables sans LLM.
- Si une conclusion dépend exclusivement d'une interprétation textuelle non disponible comme fait structuré, conserver un diagnostic ou une hypothèse sans promettre une règle publiable.
- Une annotation humaine structurée peut être une extension ultérieure ; si elle est introduite, sa provenance et sa portée au seul cas annoté restent explicites.

Alternative : extraction préalable de faits par IA, stockée et validée. Elle introduit un coût et une incertitude hors moteur ; il serait trompeur d'affirmer alors que tout le parcours des nouveaux cas est sans IA.

## 5. Options pour représenter les règles

DMN standardise la représentation des décisions ; la documentation Apache KIE décrit un moteur Java, les tables de décision et des analyses de lacunes/chevauchements.[2] La politique d'application des règles est un choix sémantique : par exemple, `UNIQUE` exige au plus une correspondance alors que `FIRST` sélectionne la première correspondance.[3]

Le tableau suivant est une **évaluation de conception pour ce POC**, pas un benchmark réalisé.

| Option | Intérêt | Coût et risque pour ce projet |
|---|---|---|
| DSL déclaratif typé, sérialisé en JSON et interprété en Java | Contrôle précis des prédicats autorisés, erreurs, inconnus, preuves et UX ; aucun code arbitraire | Concevoir et tester la sémantique d'un langage spécifique ; pas de portabilité DMN native |
| DMN/FEEL avec moteur Java existant et sous-ensemble contraint | Standard partageable, tables et outillage existants ; intérêt pour la présentation entreprise | Adapter les faits relationnels/temporels, restreindre les fonctions, maîtriser validation et versionnement runtime |
| Règles exécutables Java/Python/SQL générées librement | Expressivité élevée | Surface de sécurité et preuve beaucoup plus difficiles ; déconseillé pour la boucle runtime du POC |

**Décision : DMN/FEEL retenu le 2026-10-24.** Le DSL propriétaire est écarté. Le tableau conserve les alternatives examinées. Aucun framework BPMN n'est requis par le seul choix de DMN.

### Expressivité minimale proposée

- Prédicats typés, égalité, ordre, appartenance, conjonction, disjonction et négation encadrée.
- Existence d'événements corrélés au même exemplaire ou à la même opération.
- Comparaisons temporelles par rapport à une date fixe ; pas d'appel à l'horloge courante pendant le replay.
- Agrégations limitées avec bornes et politique sur sources incomplètes.
- Conclusions et recommandations structurées, sans effet de bord.
- Pas d'E/S réseau, système de fichiers, shell, chargement de classes, fonctions arbitraires ou écriture SQL.

Une règle ne peut pas inventer une source ou une primitive absente. Une proposition hors capacités est marquée explicitement non publiable ; elle ne doit pas être approximée silencieusement.

## 6. Proposition de changement plutôt que règle isolée

Une `PropositionChangementRegles` peut contenir création, révision et retrait coordonnés. Elle référence le cas d'origine, la conversation, les faits examinés, les changements exacts et la version de départ du référentiel.

Exemple pédagogique : l'agent propose une règle générale de retour confirmé mais non intégré, qui remplace une ancienne règle limitée à un site. L'interface montre le périmètre élargi, les cas nouveaux couverts, les contre-exemples et le retrait de l'ancienne règle.

Le retrait ne détruit pas la version ancienne utilisée dans une analyse passée. Le remplacement ne signifie pas que deux causes sont contradictoires : la compatibilité dépend des conclusions métier, pas simplement du fait que deux règles correspondent au même cas.

## 7. Sas de validation proposé

1. **Schéma et types** : opérateurs autorisés, références existantes, bornes de complexité.
2. **Validité métier** : conclusion et preuve requise, faits absents, cohérence temporelle.
3. **Tests de la proposition** : positifs, négatifs proches, inconnus et cas limites. Les tests suggérés par l'agent ne sont pas le seul jeu de validation.
4. **Replay comparatif** : état avant/après sur corpus de validation, cas nouvellement couverts, conclusions modifiées, régressions et perte de couverture.
5. **Analyse de conflits/redondances** : distinguer résultats empiriques et propriétés formellement établies.
6. **Approbation humaine explicite** : contenu exact + rapport exact + version de référentiel de départ.
7. **Activation atomique** : publication de l'ensemble du changement après vérification finale des préconditions.

Une correction intentionnelle de diagnostic peut faire changer des résultats : le rapport doit distinguer changement attendu et régression indésirable plutôt que rejeter toute différence.

### Z3 reporté hors premier périmètre

**Décision :** ne pas inclure de solveur SMT dans le premier POC. Pour une extension future seulement, un contrôle formel pourrait porter sur un sous-ensemble précisément défini des prédicats. L'API Z3 distingue satisfiable, insatisfiable et inconnu.[4] Un inconnu ou timeout n'est pas une preuve de compatibilité.

Ne pas annoncer une preuve de non-régression générale. Tester la correspondance entre sémantique d'exécution et traduction SMT ; des faits relationnels dérivés nécessitent des contraintes de validité, sans quoi un contre-exemple pourrait être impossible dans le domaine.

## 8. Dialogue et approbation

Proposition : une conversation est rattachée à une investigation et à ses propositions, non à un assistant omniscient global.

L'expert peut demander une preuve, contester une cause, ajouter une contrainte, demander un contre-exemple, comparer deux versions ou demander une généralisation. Le panneau structuré de proposition suit ces modifications.

Le chat peut produire un nouveau brouillon, jamais activer une règle à lui seul. Un bouton d'approbation porte sur une version figée. Toute modification ultérieure invalide l'approbation et les tests concernés.

Le diagnostic du cas et l'approbation d'une règle généralisée sont deux décisions distinctes. L'UX peut les rapprocher sans les confondre.

## 9. Sens précis de l'effet immédiat

**VALIDÉ :** après confirmation de publication, toute analyse nouvellement démarrée prend la nouvelle version active ; une analyse déjà commencée conserve son dossier de preuves figé et l'affiche.

L'activation prépare d'abord une version évaluable, puis effectue le basculement transactionnel. En cas d'échec de préparation, la version active reste inchangée. Les demandes concurrentes basées sur une ancienne version reçoivent un conflit explicite.

Une lecture de l'état de publication après écriture doit permettre à l'interface de vérifier la version active. La première analyse du cas analogue doit afficher cette version et l'absence réelle d'appel LLM.

## Sources

[2] https://kie.apache.org/docs/components/drools/drools_dmn
[3] https://docs.camunda.io/docs/components/modeler/dmn/decision-table-hit-policy
[4] https://z3prover.github.io/api/html/enumcom_1_1microsoft_1_1z3_1_1_status.html
