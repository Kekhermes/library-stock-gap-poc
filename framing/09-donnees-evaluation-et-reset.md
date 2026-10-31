# Données, démonstration, évaluation et réinitialisation

## Réalisation du premier incrément

Le jeu public est désormais produit par le générateur 2.0.0 et embarqué dans le backend : **10 exemplaires, 10 titres, 10 dossiers, 48 événements**, sans règle supplémentaire ni label privé. La recette locale a vérifié R001 sur les deux localisations, puis laissé les dix dossiers vierges après remise à zéro.

L'écran système et `GET /api/system/demo-check` contrôlent la préparation sans créer d'analyse ni de mission LLM. Une base déjà utilisée est conservée au démarrage ; l'utilisateur confirme la remise à zéro. Les anciennes époques restent consultables techniquement et ne polluent pas la liste active. L'adoption sûre des résultats tardifs et la boucle LLM complète restent des travaux distincts.

Les profils techniques historiques ne sont pas le jeu présenté dans l'application ; leur simplification et l'exécution réelle du corpus de replay restent à réaliser. Voir [l'état et les preuves](12-etat-implementation-actuel.md).

## Cible actuelle — validée le 2026-10-31

Cette section remplace la volumétrie, le catalogue et les plafonds historiques conservés plus bas. Voir [les décisions N01–N18](04-registre-des-decisions.md) et [la vision actuelle](00-vision-et-exigences.md).

### État exact après initialisation ou reset

| Famille | Dossiers | Règle active initiale | Résultat attendu |
|---|---:|---|---|
| Localisation différente | 2 | R001 uniquement | Les deux passent en déterministe lorsqu'ils sont analysés |
| Retour non répercuté | 4 | Aucune | Le premier dossier analysé produit un diagnostic LLM puis une règle ; les trois autres passent ensuite sans LLM |
| Réparation non répercutée | 4 | Aucune | Même mécanisme, indépendamment des retours ; trois autres analogues reconnus après publication |

**Total : dix dossiers en `A_ANALYSER`, aucun diagnostic, aucune proposition ni conversation préremplie.** Les vérifications de prévol ne doivent pas consommer le dossier d'apprentissage ou préanalyser les dossiers analogues dans l'époque de présentation.

Les termes R2/R3 sont des repères pédagogiques, pas des identifiants imposés ni des artefacts préchargés. L'ordre d'apprentissage retour/réparation est libre. Une seule règle doit suffire pour chacun des groupes de quatre ; créer une règle différente par code-barres ferait échouer la démo.

### Construction proposée des données

Conserver seulement le fonds et les événements nécessaires à ces dix dossiers, au lieu d'imposer 1 200 exemplaires. Conserver le schéma et la stack existants. Chaque dossier a une histoire courte, une seule cause et des preuves structurées suffisantes pour la règle future.

Retour : prêt antérieurement projeté, clôture/retour réellement enregistrés, échec corrélé de mise à jour du stock informatique, présence physique admissible, état informatique encore prêté. Réparation : ordre et présence en atelier, échec corrélé de mise à jour du stock informatique, état informatique resté au rayon. Localisation : observation unique fiable ailleurs dans le même site, sans second symptôme à expliquer.

Les quatre exemplaires d'une famille varient par identifiants et détails non causaux, mais partagent les faits suffisants à une règle simple. Les trois analogues ne sont pas transmis comme exemples d'apprentissage à l'investigateur ni à l'agent de règles avant la publication ; ils servent à prouver la réutilisation.

Les familles des données et leurs attentes sont connues du concepteur, pas du chemin de diagnostic. Pas de champ de cause attendue, de conclusion forcée ou de nom de scénario dans les faits DMN ou les prompts. Les notes restent des preuves auxiliaires ; la règle future doit fonctionner sur des faits structurés disponibles sans LLM.

### Tests hors liste de dossiers

Corpus technique séparé et réduit : positifs nouveaux, négatifs proches et faits inconnus. Pour une règle de retour, inclure notamment une mise à jour du stock informatique finalement appliquée, un échec portant sur une autre opération, des sources incomplètes et un cas de réparation. Le corpus ne crée pas une quatrième famille dans la liste de démo.

Java exécute réellement le candidat et le référentiel avant/après. Le rapport détaille les résultats ; aucune métrique de tests n'est dérivée du seul succès de compilation. Les trois analogues réservés sont contrôlés après publication et ne servent pas à corriger secrètement la règle.

### Parcours 10–15 minutes proposé

1. Constater dix dossiers vierges, une règle active et la configuration OpenAI prête.
2. Analyser un des deux dossiers de localisation : montrer R001 et zéro appel LLM.
3. Analyser un retour : aucune règle applicable, consultations successives réelles, diagnostic sourcé.
4. Échanger avec l'investigateur ; accepter explicitement le diagnostic.
5. Observer l'agent de règles, les conditions FEEL et leur table DMN ; discuter une condition ou un négatif.
6. Voir le rapport de tests réel, puis approuver et publier la révision exacte au runtime.
7. Analyser les trois autres retours : même nouvelle règle, nouvelle publication, aucun appel LLM de diagnostic.
8. Constater que les réparations restent hors couverture ; leur boucle identique peut être déroulée en approfondissement.

Le parcours court n'enlève pas l'exigence de réussite sur les deux familles ; la recette complète les acquiert toutes deux, dans les deux ordres.

### Réinitialisation simple

Réutiliser et fiabiliser le reset existant plutôt qu'imposer d'emblée une suppression de volumes. Résultat vérifié : nouvelle époque, dix dossiers `A_ANALYSER`, seule R001 active, aucune ancienne mission/proposition/conversation active ou réponse tardive adoptable. Les anciens états éventuellement archivés ne polluent pas l'écran initial.

Préserver la configuration OpenAI et les autres projets. Confirmation explicite avant reset ; aucun nettoyage Docker global. Le support UI et le contrôle de préparation sont désormais livrés et éprouvés sur les images locales. Une remise à zéro ne démontre pas à elle seule la reprise sûre de toutes les missions : cette protection uniforme reste à réaliser.

### Fiabilité et consommation

Appels en direct, prévol non destructif, corrections bornées, refus et panne visibles. Pas de replay/mock de secours présenté comme une nouvelle génération. Aucune promesse absolue sur le fournisseur ou une sortie LLM ; la preuve déterministe après publication est reproductible sur le jeu versionné.

Pas de plafond financier/tokens/appels à fixer maintenant, conformément à « open bar ». Mesurer la consommation de tous les essais et corrections. Les valeurs de l'ancien paragraphe 7 ne s'appliquent plus comme cible.

## Historique du premier cadrage — non prescriptif pour la nouvelle démo

Le profil 120 dossiers/dix familles et les commandes/plafonds suivants sont conservés pour comprendre l'existant. Ne pas régénérer ces fixtures comme jeu cible ; elles doivent être adaptées selon [la roadmap](13-roadmap-demo.md).

## Statut

**Validé** : trois sites, environ 1 200 exemplaires et 120 cas, scénarios garantis + seed, inventaire à date fixe, sources en lecture seule, notes libres auxiliaires, variantes réservées aux tests, **UNE SEULE règle initiale très simple**.

Le catalogue détaillé et les valeurs de configuration ci-dessous sont proposés pour validation finale, pas des performances déjà mesurées.

## 1. Bibliothèque fictive proposée

Réseau « Les Trois Rives » : sites `CENTRE`, `NORD`, `SUD`. Chaque site possède des rayons, une réserve, un espace retours/réception et une zone réparation. Des transferts relient les sites. Les lecteurs, opérateurs, titres et fournisseurs sont fictifs ; aucune donnée personnelle réelle.

Profil `demo-v1` : 1 200 exemplaires, 120 dossiers d'écart et 1 080 exemplaires sans dossier d'écart. Un dossier porte sur un exemplaire, mais peut contenir plusieurs symptômes. Les données normales alimentent les recoupements et les tests de faux positifs ; elles ne sont pas toutes affichées comme cas à traiter.

Répartition proposée : dix familles de douze cas. L'équilibre est pédagogique, pas une estimation statistique de la fréquence des anomalies en bibliothèque.

| Famille | Cas | Attente pédagogique |
|---|---:|---|
| Localisation différente dans le même site | 12 | Une partie simple couverte par la règle initiale ; variantes ambiguës exclues de sa portée |
| Prêt effectué, état du stock informatique non mis à jour | 12 | Croiser prêt actif et incident de mise à jour du stock informatique |
| Retour reçu, statut encore prêté | 12 | Première acquisition de règle par la boucle |
| Transfert expédié/reçu, état du stock informatique incohérent | 12 | Recoupement de plusieurs sites et chronologie |
| Réception physique non intégrée | 12 | Distinguer propriété du fonds et présence observée |
| Mise en réparation non répercutée | 12 | État métier différent d'une simple absence |
| Inventaire incomplet ou collecte défaillante | 12 | Refuser de conclure à une disparition |
| Retard de mise à jour du stock informatique et cohérence temporelle | 12 | Distinguer transition attendue et défaut persistant |
| Absence inexpliquée avec preuves insuffisantes | 12 | Abstention correcte ; aucune invention de vol |
| Combinaisons contrôlées de deux anomalies | 12 | Diagnostic partiel puis composition compatible |

Les autres familles de la proposition initiale, notamment identifiants corrompus et réceptions dupliquées, ne sont pas nécessaires au profil initial ; elles restent des extensions possibles.

## 2. Unique règle initiale retenue

`R001 — Localisation différente constatée` : un exemplaire attendu à un emplacement est observé de façon unique et valide à un autre emplacement du même site.

Conclusion : **écart de localisation étayé**, recommandation de vérifier le rangement et l'affectation. Ne pas prétendre prouver quel opérateur a commis une erreur ou quelle procédure a échoué.

Le dossier de preuves figé assure les préconditions de qualité (identité connue, scan non ambigu, dates cohérentes). La table DMN reste minimale : même site et emplacement différent. Si les observations sont ambiguës ou le cas comporte un autre symptôme inexpliqué, cette règle ne suffit pas et le fallback s'applique.

Cette règle initiale est un cas pédagogique bootstrap explicitement identifié. Elle ne sert pas à justifier de futures créations manuelles dans l'application. **Le seed contient un seul module/règle, une seule ligne de décision active.** Les garde-fous de typage et de publication ne sont pas comptés comme des règles de diagnostic cachées.

## 3. Générateur et preuve de causalité

Le générateur part d'une histoire cohérente puis applique une perturbation définie, plutôt que de fabriquer séparément des tables incohérentes sans explication. Proposition d'implémentation : job Java séparé, réutilisant les types et invariants des sources métier mais jamais le moteur de diagnostic pour fabriquer son oracle. Python reste dédié aux agents, pas à une seconde implémentation du domaine.

Chaque scénario spécifie :
- état initial et événements métier ;
- perturbation de mise à jour du stock informatique ou collecte ;
- observations visibles et sources incomplètes ;
- vérité injectée réservée à l'oracle ;
- conclusions admissibles compte tenu des seules preuves exposées ;
- variante positive, négatif proche et variante insuffisamment documentée.

Les journaux de mise à jour du stock informatique utilisent de vraies corrélations d'événements simulés, pas des messages « le scénario est X ». Les identifiants, noms, ordre de génération et notes ne doivent pas dévoiler la famille cachée.

Les titres et textes peuvent être synthétiques et variés, mais la logique de génération et l'oracle ne sont pas produits par un LLM.

## 4. Séparation des jeux

- **Application/démonstration** : les 120 cas opérationnels préchargés.
- **Validation des propositions** : corpus séparé avec cas positifs, négatifs proches et inconnus. Ses rapports peuvent être vus par l'expert et l'agent de règles pour corriger une proposition.
- **Évaluation finale réservée** : variantes/seeds distincts, jamais envoyés à l'agent ni utilisés pour ajuster une règle avant mesure.

Les deux corpus techniques sont supplémentaires ; ils ne diminuent pas les 120 cas opérationnels. Leur taille et leur manifeste sont enregistrés et comptés automatiquement. Répéter l'ajustement sur le corpus de validation est possible mais n'en fait pas un test indépendant : seule l'évaluation réservée garde ce rôle.

Un simple changement de code-barres ne suffit pas comme test de généralisation. Varier aussi site, délais, ordre des événements, degré de complétude et présence de contre-signaux, tout en conservant le mécanisme causal.

## 5. Parcours guidé proposé

1. Réinitialiser `demo-v1` et constater une seule règle active.
2. Analyser un cas simple de localisation : résultat déterministe et zéro appel LLM.
3. Analyser un retour non intégré : fallback, preuves, diagnostic et acceptation humaine.
4. L'agent de règles propose une règle ; l'expert discute ses conditions et un négatif proche.
5. Publier, puis analyser un autre retour analogue mais différent : zéro appel LLM après publication.
6. Demander une amélioration de cette règle à partir d'un nouveau cas ; démontrer révision ou remplacement avec retrait coordonné.
7. Examiner un cas indécidable ; le système conserve son incertitude.
8. Retirer une règle ; constater la nouvelle version et le retour au fallback sur un cas non couvert.

Les données et les étapes attendues sont garanties ; la sortie exacte d'un LLM ne l'est pas. Si le modèle ne fournit pas une proposition valide, la démonstration montre l'échec ou permet une correction par le chat. Des fixtures simulées peuvent servir aux tests automatisés, jamais être présentées comme une vraie exécution OpenAI.

## 6. Mesures et critères de réussite

| Critère | Vérification attendue |
|---|---|
| Reproductibilité | Même seed/version/horloge → mêmes données métier et même oracle, hors métadonnées techniques de lancement |
| Bootstrap | Exactement une règle de diagnostic active après initialisation/reset |
| Indépendance de l'oracle | Aucune route, credential SQL, fichier monté ou outil agent ne permet sa lecture |
| Cascade | Toute investigation de cause est précédée d'une évaluation déterministe et d'un motif de fallback enregistré |
| Notes | Aucun appel IA caché dans la préparation des faits déterministes |
| Runtime | Après publication confirmée, le cas suivant utilise la nouvelle version sans redémarrage ; cas en cours inchangé |
| Généralisation | Cas analogue distinct accepté et négatif proche non capturé à tort |
| Validation humaine | Diagnostic et changement de règles ont deux actes distincts ; un simple message « oui » ne publie rien |
| CRUD corrigé | Création/révision uniquement par agent, retrait Admin effectif, historique préservé |
| Concurrence | Proposition périmée bloquée, doublons sans double publication, résultats tardifs sans effet indu |
| Reconnexion | Recharger le navigateur permet de retrouver missions, messages et propositions cohérents |
| Panne | Indisponibilité LLM/broker et dépassement de quota visibles ; aucune réussite inventée |
| Reset | Retour à la base, une seule règle, zéro conversation/mission ancienne, `.env` préservé et autres projets intacts |

Mesurer séparément couverture déterministe, précision des conclusions, faux positifs, abstentions appropriées, part des hypothèses non confirmées, temps moteur et temps de bout en bout, appels LLM et tokens cumulés par rôle. Aucun montant financier n'est affiché.

Ne pas fixer un taux de causalité industrielle à partir de données artificielles. L'objectif de la démonstration est une boucle correcte et observable ; les performances agentiques sont rapportées honnêtement sur un jeu versionné.

## 7. Limites techniques retenues

Valeurs de départ acceptées le 2026-10-24, configurables et à ajuster après mesure sur machine cible :

| Limite | Proposition |
|---|---|
| Taille d'un lot | 5 cas |
| Missions LLM simultanées | 2 globalement, une par conversation |
| Durée d'une mission automatique | 300 secondes, hors attente humaine et attente en file |
| Appels LLM par mission automatique | 8 maximum, retries inclus |
| Appels d'outils par mission | 24 maximum |
| Tokens cumulés par mission | 64 000 en entrée et 12 000 en sortie, incluant les tokens de raisonnement quand rapportés par le fournisseur |
| Tour de chat expert | 120 secondes, 4 appels LLM maximum ; mêmes mécanismes de quota et annulation |
| Réessai technique automatique | 1 maximum sous le même budget ; pas de retry sur sortie métier invalide sans contrôle |

Avant chaque appel, réserver la limite restante et borner la sortie demandée. Un tokeniseur local fournit une estimation, le compteur fournisseur fait foi après retour ; ne pas prétendre garantir un plafond financier exact. La fenêtre de contexte maximale du modèle n'est pas le budget de l'application.

Proposition d'objectifs de réactivité : accusé d'une commande et navigation usuels sous une seconde sur machine de démonstration ; évaluation déterministe de bout en bout sous une seconde au profil initial. Ce sont des objectifs à mesurer, pas une garantie `<5 ms` reprise de la revue.

## 8. Contrat de réinitialisation locale

**Interface de commande à implémenter ultérieurement — ces commandes ne sont pas encore livrées :**

```text
./dev up
./dev status
./dev reset --profile demo-v1 --seed 42
./dev verify-reset
```

`reset` demande une confirmation explicite ; un mode non interactif `--yes` est réservé aux tests ou à une demande explicite. Ne jamais lancer un nettoyage Docker global.

Séquence attendue :
1. Vérifier le répertoire projet et un nom Compose explicite propre au POC.
2. Arrêter les missions et les services du seul projet.
3. Supprimer les volumes nommés du projet qui portent état applicatif, oracle et broker ; pas les fichiers sources, images partagées ou données d'autres projets.
4. Préserver `.env`, `.env.example`, documents et code ; ne jamais afficher la clé.
5. Recréer les services, appliquer les migrations, régénérer les données et le référentiel bootstrap.
6. Générer une nouvelle époque technique, conserver la seed et l'horloge métier de référence.
7. Vérifier santé, compteurs, manifeste et unique règle active ; afficher les étapes échouées le cas échéant.

`verify-reset` doit tester que les anciennes missions/propositions/publications acquises ne sont plus actives, que les topics sont propres et que le jeu correspond au manifeste. Un simple `docker compose up` ne constitue pas cette vérification.

## 9. Gates de réalisation futures

1. Compatibilité minimale de la stack, chargement DMN dynamique et isolation du langage.
2. Sources/générateur, invariants et séparation de l'oracle.
3. Cascade déterministe/agentique réelle et reprise sur panne.
4. Chat, propositions, validation et publication runtime.
5. Parcours complets, retraits, lots et démonstration.
6. Reset, tests entre composants et e2e, contrôle de secrets/licences puis revue finale bornée.

Aucun de ces gates d'implémentation n'a été exécuté pendant le cadrage ; ils constituent les critères du travail à autoriser ensuite.
