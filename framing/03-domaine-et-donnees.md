# Domaine et données synthétiques — propositions de travail

**Statut : SOCLE VALIDÉ, DÉTAILS PROPOSÉS.** Le 2026-10-24, Kevin a validé : exemplaires identifiés par code-barres avec synthèses par titre/site ; réseau de trois sites avec rayons, réserves et transferts ; diagnostic et recommandations sans correction du stock ; socle mono-cause avec ambiguïtés, données incomplètes et quelques multi-causes contrôlées ; scénarios pédagogiques garantis et générateur déterministe paramétré par seed. La convention temporelle est désormais validée ci-dessous. Le catalogue de réalisation, la volumétrie et la règle initiale sont consolidés dans `09-donnees-evaluation-et-reset.md` ; les structures détaillées restent soumises à la revue finale.

## 1. Définir le stock avant les causes

Une bibliothèque ne se réduit pas à une quantité par titre. Plusieurs exemplaires d'une même édition peuvent avoir des localisations, prêts et états différents.

Proposition : distinguer `NoticeBibliographique`, `Exemplaire` identifié par code-barres, `Site`, `Emplacement`, `SituationInformatique`, `CampagneInventaire` et `ObservationInventaire`.

À une date de référence et dans un périmètre explicitement couverts, comparer la présence/localisation attendue de chaque exemplaire aux observations. Produire des vues agrégées par titre, site et famille d'écart, sans perdre l'identité des exemplaires.

Un exemplaire prêté n'est pas attendu en rayon. Une zone non inventoriée n'est pas équivalente à une zone vide. Un exemplaire au mauvais rayon peut créer un manque local et un surplus ailleurs sans modifier le total du site.

### Temps et périmètre

**VALIDÉ le 2026-10-24 :** inventaire à date de référence fixe, activité suspendue pendant les scans ; historiques et retards de mise à jour du stock informatique conservés. Les mouvements concurrents pendant les scans ne font pas partie du premier périmètre.

Proposition technique : conserver temps de l'événement métier et temps de son enregistrement, couverture de l'inventaire, provenance des observations et version du dossier de preuves figé analysé. Définir séparément la date de référence métier et la date de gel des données collectées, pour représenter une mise à jour du stock informatique tardive sans modifier silencieusement une analyse passée.

**VALIDÉ :** sources structurées et quelques notes opérateur libres, sans RAG. Leur consultation doit être ciblée par exemplaire, opération et fenêtre temporelle. Le rôle des notes dans les conditions exécutables des règles reste ouvert.

## 2. Objets métier candidats

| Ensemble | Objets candidats | Responsabilité |
|---|---|---|
| Référentiel bibliothèque | Notice, exemplaire, site, emplacement | Identité et rattachements |
| Activité métier | Prêt, retour, réception, transfert, réparation, retrait | Événements permettant les recoupements |
| Inventaire | Campagne, périmètre, session de scan, observation | Ce qui a réellement été observé et quand |
| Analyse | Cas d'écart, exécution d'analyse, dossier de preuves, hypothèse, diagnostic | Investigation et résultat historisé |
| Connaissance | Règle, version, proposition de changement, rapport de validation, publication | Apprentissage gouverné |
| Interaction | Conversation, message, demande de précision, approbation | Dialogue ancré sur un cas et une proposition versionnée |

Ce tableau n'impose pas encore des agrégats DDD ni un découpage de tables. Les invariants et frontières transactionnelles doivent précéder ce choix.

## 3. Sources de recoupement proposées

- Situations informatiques et journal des mises à jour de stock/localisation.
- Prêts, renouvellements et retours, avec traces de bornes ou postes lorsque le scénario le nécessite.
- Réceptions et étapes de catalogage/mise à disposition.
- Transferts expédiés/reçus et observations des différents emplacements.
- Réparations, retraits et affectations aux réserves.
- Couverture d'inventaire, scans, doublons et incidents de lecture.
- Journaux de mise à jour du stock informatique : succès, rejet, retard, duplication, corrélation d'événements.
- Éventuellement notes opérateur courtes : à arbitrer, car elles changent le besoin d'interprétation sémantique.

Un journal technique peut attester un événement rejeté ; il ne doit pas contenir une étiquette « cause attendue » artificiellement écrite par le générateur.

## 4. Catalogue initial de familles candidates

Les signaux ci-dessous étayent des hypothèses ; ils ne suffisent pas tous, isolément, à établir une cause.

| Famille | Écart observable | Recoupement utile | Négatif proche à prévoir |
|---|---|---|---|
| Prêt effectué, état du stock informatique non mis à jour | Attendu en rayon, non observé | Prêt actif antérieur au cutoff et rejet du traitement associé | Prêt annulé ou commencé après le cutoff |
| Retour reçu, situation encore « prêté » | Présence physique inattendue | Scan de retour, référence du prêt et mise à jour en échec | Scan de retour du mauvais exemplaire |
| Transfert non finalisé informatiquement | Manque au départ, présence à l'arrivée | Expédition, réception et chronologie concordantes | Transfert réellement encore en cours |
| Mauvais rangement | Absence à l'emplacement prévu, présence ailleurs | Même identifiant scanné dans une autre zone | Changement d'emplacement autorisé mais récent |
| Réception non intégrée | Exemplaire observé mais absent du stock attendu | Bon de réception et identification exploitable | Exemplaire n'appartenant pas à la bibliothèque |
| Réception enregistrée deux fois | Attendu informatique surestimé | Même référence externe et deux écritures | Deux réceptions distinctes légitimes |
| Réparation ou retrait non répercuté | Exemplaire attendu dans un emplacement inadapté | Ordre de réparation/retrait et trace de mouvement | Ordre créé mais non exécuté |
| Défaut de collecte d'inventaire | Absence de scan interprétée à tort comme manque | Zone non terminée, lecteur en erreur ou session incomplète | Zone bien couverte et lecture correcte |
| Identifiant erroné ou dupliqué | Présences incompatibles | Scans, étiquettes et identité des exemplaires | Deux scans du même objet sans déplacement réel |
| Décalage temporel normal | Écart apparent entre sources | Temps métier et ingestion, statut transitoire | Retard ayant dépassé la borne de cohérence acceptée |
| Disparition non expliquée | Manque malgré couverture complète | Pas de mouvement explicatif dans les sources disponibles | Prêt ou transfert non encore visible |
| Causes combinées | Plusieurs divergences pour un même dossier | Chronologie et contributions indépendantes | Une cause unique produisant plusieurs symptômes |

Cette liste n'est pas un engagement à tout implémenter. Retenir un noyau représentatif puis des variantes difficiles selon l'ambition choisie.

## 5. Génération : simuler les événements, pas seulement des lignes

### Proposition de pipeline

1. Générer un monde cohérent : exemplaires, emplacements, activité et calendrier.
2. Produire une chronologie métier vraisemblable : prêt, retour, transfert, réception, etc.
3. Introduire des perturbations contrôlées sur certaines mises à jour du stock informatique ou observations : retard, perte, duplication, mauvais rattachement, couverture partielle.
4. Matérialiser séparément la situation informatique et l'inventaire observé.
5. Charger les cas d'écart avant l'utilisation de l'application ; inclure aussi des témoins sains pour les tests de faux positifs.
6. Conserver la cause injectée et les attentes dans un oracle d'évaluation isolé des chemins de diagnostic.

L'oracle connaît l'histoire simulée ; le moteur et l'agent ne connaissent que les preuves exposées. Une cause connue de l'oracle peut rester impossible à identifier à partir des seules observations. Le résultat attendu doit alors autoriser l'abstention ou plusieurs hypothèses, pas exiger de deviner la vérité cachée.

### Reproductibilité et variété

- Seed, version du générateur, horloge métier de référence et paramètres enregistrés.
- Variations d'identifiants, dates, sites et volumes sans modifier les propriétés causales.
- Cas positifs, négatifs proches, sources manquantes, contradictions et situations normales.
- Familles gardées à l'écart du référentiel initial pour déclencher réellement le fallback.
- Variantes réservées à l'évaluation et non montrées à l'agent pendant la proposition.
- Une règle doit généraliser les preuves utiles, pas mémoriser un identifiant, une date exacte ou un marqueur de scénario.

Recommandation initiale : générateur déterministe paramétré + scénarios écrits à la main pour la démonstration. Ne pas confier au LLM la vérité de référence et le jugement de sa propre réussite. Une génération LLM facultative de textes annexes ne remplace pas les invariants métier.

## 6. Frontière importante de l'apprentissage runtime

Une nouvelle règle peut recombiner les faits et opérateurs déjà disponibles. Si elle exige une nouvelle source ou une nouvelle primitive de calcul, elle ne doit pas être exécutée comme du code arbitraire généré par l'IA.

Il faudra choisir entre :
- une proposition bloquée et signalée « capacité manquante » ;
- un catalogue suffisamment expressif de jointures, fenêtres temporelles et agrégations autorisées ;
- une extension explicite du logiciel, hors boucle normale de publication runtime.

La démonstration doit distinguer une **règle nouvelle** d'une **capacité d'observation nouvelle**.

## 7. Arbitrages de domaine désormais enregistrés

1. Exemplaires individuels ou quantités agrégées ?
2. Un site ou un petit réseau avec transferts ?
3. Diagnostic seulement ou correction simulée du stock ?
4. Jusqu'où aller dans les causes combinées et l'incertitude ?
5. Générateur paramétré et scénarios garantis ou priorité à une simulation plus ouverte ?
