# Lecture critique de la revue fournie

## Périmètre de lecture

Le fichier [archi-review.md](archi-review.md) a été lu intégralement, lignes 1 à 78. Il est conservé intact. Les références ci-dessous pointent vers ses sections et lignes ; elles attestent ce que le document affirme, pas la validité scientifique de chaque affirmation.

Le document ne contient ni bibliographie exploitable ni liens permettant d'identifier sans ambiguïté les publications citées. ReaComp, ADS, ILP-CoT, Reason Popper-ly et les chiffres annoncés restent à vérifier avant toute présentation externe les reprenant comme faits. Le présent cadrage ne les traite pas comme des résultats établis. Le fichier contient également des marqueurs d'export `[span_102](start_span)` / `[span_102](end_span)` dans le tableau d'anomalies ; ce ne sont pas des références utilisables. Ils sont conservés dans la source originale, sans les propager aux livrables.

## Apports à conserver

- **Cascade asymétrique** : le déterministe est le chemin nominal, l'agent traite les cas non couverts (§ Fondements, lignes 3–11).
- **Capitalisation exécutable** : la sortie durable n'est pas seulement une explication, mais une règle réutilisable (§ Synthèse/induction, lignes 12–17).
- **Séparation des responsabilités** : orchestration, décision sans effet de bord et investigation outillée (§ Architecture, lignes 53–67).
- **Gouvernance des propositions** : l'humain doit comprendre le domaine d'application, pas simplement cliquer sur une conclusion (§ Gouvernance, lignes 25–34).
- **Gestion du référentiel** : chevauchement, contradiction, redondance, subsomption et sur-spécialisation sont des enjeux centraux (§ Verrous, lignes 47–52).

## Affirmations à ne pas reprendre comme garanties

| Affirmation du document | Limite à expliciter dans le POC |
|---|---|
| Inférence déterministe `<5 ms`, coût quasi nul, temps constant (lignes 7, 11, 20–24, 60) | Ce sont des objectifs éventuels à mesurer. L'acquisition des faits, les jointures et le nombre de règles influencent les coûts. Mesurer moteur seul et parcours complet séparément. |
| Toute instance analogue sera désormais résolue et l'agent définitivement contourné (ligne 11) | La généralisation dépend du périmètre exact de la règle et des données disponibles. Une famille peut contenir des variantes non couvertes. |
| Garantie absolue de non-régression par tests unitaires ou contrôle logique (ligne 24) | Les tests couvrent des exemples ; une preuve formelle porte sur une propriété modélisée. Aucun des deux ne garantit à lui seul la vérité causale ni toutes les propriétés du système. |
| UNSAT autorise l'inclusion sans risque de régression (ligne 51) | UNSAT n'établit l'absence que du conflit précisément encodé, sous les hypothèses du modèle. Une régression de couverture ou un diagnostic faux restent possibles. UNKNOWN et timeout doivent bloquer ou dégrader explicitement la validation. |
| DMN/S-FEEL directement traduisibles en logique (ligne 50) | La faisabilité dépend du sous-ensemble retenu : valeurs absentes, dates, collections, jointures, fonctions et hit policies doivent avoir une sémantique précise. Ne pas promettre une traduction générale. |
| Auditabilité totale et conformité garanties (lignes 23, 33, 78) | Traçabilité technique, conformité réglementaire et pertinence métier sont des propriétés distinctes. Le POC mono-admin ne démontre pas une séparation des pouvoirs. |
| L'usage de LLM tend asymptotiquement vers zéro (lignes 3, 20, 34) | Hypothèse testable sur un domaine stable, non une loi. Nouvelles familles, données manquantes, dérive et règles retirées peuvent faire remonter le recours au LLM. |

## Problème causal dans les exemples de distribution

La revue passe de signaux tels que « rayon vide, ventes nulles, stock informatique positif » à des causes possibles comme vol, casse ou erreur amont (lignes 35–46). Ces observations ne distinguent pas à elles seules ces causes.

Pour notre bibliothèque, « exemplaire non scanné à l'inventaire » peut correspondre à un prêt légitime, un mauvais rangement, une zone non inventoriée, un transfert ou une disparition. Une absence de scan ne doit donc pas induire une règle concluant automatiquement à un vol.

Recommandation : chaque proposition contient hypothèse, éléments qui l'étayent, éléments contradictoires, alternatives non éliminées et informations manquantes. Les explications sont des synthèses vérifiables de faits et d'appels d'outils, pas une exigence d'exposer un raisonnement interne du modèle.

## Adaptations indispensables au besoin exprimé

### Runtime plutôt que Git/CI dans le chemin critique

La revue propose une publication Git puis une chaîne CI/CD (ligne 66). Cela ne correspond pas à la demande d'un CRUD de règles avec effet immédiat au runtime.

**Proposition** : référentiel versionné en base, préparation/validation d'une nouvelle version, puis activation atomique. Export Git ou fichier possible à titre documentaire, pas obligatoire pour analyser le cas suivant.

### Un Admin plutôt qu'un maker-checker

Le principe des quatre yeux décrit ligne 33 est exclu du POC par le persona unique. Conserver une approbation explicite et un historique pédagogique, sans prétendre démontrer une séparation réelle entre auteur et approbateur.

### Pas d'orchestrateur supplémentaire imposé

LangGraph, Temporal, Camunda, Drools, Kogito et Z3 sont des pistes, pas des décisions. Le socle DDD Java peut porter les cycles de vie durables ; le service Python peut porter une investigation outillée. Un moteur additionnel doit répondre à un besoin démontré.

### Analyse plutôt qu'action logistique implicite

Les exemples de la revue déclenchent des actions sur le stock et les commandes. Le besoin utilisateur porte d'abord sur l'analyse de désynchronisation. La correction du stock doit être arbitrée séparément.

### Pas de TTL arbitraire imposé

Le vieillissement des règles est un sujet pertinent, mais une expiration temporelle automatique n'est pas nécessairement adaptée à un jeu pédagogique statique. Examiner d'abord désactivation, versionnement, réévaluation et remplacement.

## Conditions d'une démonstration honnête

- Ne pas confondre apprentissage du modèle et enrichissement du référentiel : aucune modification des poids LLM n'est demandée.
- Tester une règle sur d'autres cas que celui ayant servi à sa formulation.
- Inclure des cas négatifs proches et des données insuffisantes.
- Ne pas réutiliser les étiquettes cachées du générateur pour construire les faits consommés par le moteur.
- Distinguer vérification structurelle, tests empiriques et preuve formelle restreinte.
- Présenter les gains comme des mesures sur le jeu synthétique, pas comme une validation industrielle générale.

## Recherches encore ouvertes

1. Retrouver et vérifier les publications originales réellement utiles à nos décisions.
2. Comparer DMN/FEEL, DSL déclaratif typé et moteur de règles Java sur notre cas relationnel/temporel.
3. Définir le sous-ensemble où une vérification SMT serait réellement soutenable et utile.
4. Vérifier versions, maintenance et licences des candidats avant de figer la stack.
