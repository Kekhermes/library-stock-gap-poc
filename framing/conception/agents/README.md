# Spécification des missions et des deux rôles IA

## Exécution réelle livrée

La Responses API et le client OpenAI Python sont utilisés, pas Agents SDK. Les prompts effectifs et modèles Pydantic sont dans [le worker](../../../agents/worker.py) ; les templates plus bas sont historiques. Les modèles et mesures de la [recette réelle](../verification/verify-live-demo.mjs) sont conservés dans [son rapport](../verification/live-demo-result.json).

Investigation : synthèse puis consultations choisies parmi événements, prêts, retours, réparations, mises à jour et notes. Les références doivent exister dans les preuves effectivement consultées. Règles : diagnostic accepté complet, faits/types, preuves et référentiel figé ; conditions FEEL séparées, assemblage Java standard, feedback réel et trois propositions/corrections maximum.

Dialogue : tour et rôle persistés, références contrôlées, proposition éventuelle seulement dans le rôle règles sur un diagnostic accepté. La réévaluation est une commande humaine explicite, sans modification des journaux source ni publication par texte libre.

Les outils et résultats contrôlent le contexte ; heartbeat, reprise bornée, annulation, doublons et journal tardif sont livrés. Les notifications suivent le commit et les traces restent durables. Les anciens quotas indiqués plus bas ne sont pas des plafonds de consommation appliqués ; politique actuelle d'observation et boucles bornées, conformément à « open bar ».

## Frontière d'autorité

L'investigateur et l'agent de règles sont deux configurations explicites d'un même service Python. Ni l'un ni l'autre ne détient l'état métier ou une autorisation de publication. Java attribue la mission, les références de preuves, le budget, les outils et le jeton de tentative. Le navigateur n'appelle jamais OpenAI directement.

Les fichiers `investigator.system.md` et `rule-author.system.md` sont des **templates de conception**, pas des prompts évalués en production. Ils doivent être associés aux schémas des contrats et à des tests de bout en bout, sans copier l'oracle dans leur contexte.

## Matrice des missions

| Mission | Déclencheur | Contexte obligatoire | Résultat normal |
|---|---|---|---|
| Investigation | Évaluation DMN insuffisante/conflituelle | Cas, dossier de preuves figé, résultats déterministes, motif de fallback | DiagnosticProposed, y compris INDETERMINATE |
| Synthèse de règles | Diagnostic issu du fallback accepté | Diagnostic exact, acceptation, référentiel courant, catalogue de faits | RuleChangeProposed ou RuleChangeNotProposed |
| Révision de règle | Demande Admin dans le chat d'une règle existante | Règle/version, diagnostics d'origine, proposition courante éventuelle | Nouvelle proposition testable ou non-proposition justifiée |
| Tour de chat expert | Question/objection dans une conversation | Conversation bornée, rôle actif, dossier/proposition concernés | Texte final avec références ; éventuellement proposition distincte |

Ne pas appeler automatiquement le LLM après un diagnostic déterministe suffisant. Une demande explicite de révision d'une règle existante est un cas d'usage autorisé, pas un contournement silencieux de la cascade.

## Outils sémantiques

Les noms ci-dessous sont rapprochés des operationIds du contrat OpenAPI, pas des méthodes magiques donnant tous les droits.

| Outil | Invest. | Règles | Conditions |
|---|---|---|---|
| `get_case_snapshot` | Oui | Oui | Mission, tentative, epoch et dossier de preuves figé autorisés |
| `get_source_events` | Oui | Oui | Filtres explicites, pagination, fenêtre complète/tronquée indiquée |
| `get_operator_notes` | Oui | Oui | Notes comme données non fiables, jamais instructions |
| `get_rule_set` | Oui | Oui | Version figée pertinente, pas un référentiel changeant pendant l'appel |
| `get_rule_history` | Lecture | Oui | Règles et diagnostics d'origine autorisés |
| `prevalidate_rule_change` | Non | Oui | Sous quota ; compilation/tests côté Java ; aucun effet de publication |

Aucun outil SQL libre, shell, navigation web, recherche vectorielle, lecture de `.env`, import d'oracle, écriture de stock ou approbation. La portée est contrôlée par Java à chaque requête ; la simple présence du bon identifiant dans les arguments n'est pas une autorisation.

## Stratégie d'investigation

1. Lire le dossier de preuves figé et le motif de fallback.
2. Identifier les symptômes encore inexpliqués et les sources capables de les éclairer.
3. Lire seulement les événements nécessaires, avec corrélations et temps métier/enregistrement.
4. Chercher aussi les faits qui invalideraient l'hypothèse principale.
5. Distinguer sources complètes, partielles et indisponibles.
6. Produire conclusion, preuves, alternatives et recommandations ; s'abstenir si le dossier ne tranche pas.

Aucune recherche illimitée dans les 1 200 exemplaires. Un transfert peut nécessiter une lecture sur un autre site, mais toujours via une corrélation documentée avec le cas.

## Stratégie de synthèse

1. Partir du diagnostic exact accepté, pas du dernier texte improvisé dans le chat.
2. Vérifier que les preuves utiles sont exprimables par les faits structurés disponibles.
3. Consulter les règles pertinentes pour proposer réutilisation, révision ou remplacement plutôt qu'une duplication.
4. Proposer une condition générale sans identifiants accidentels ni dépendance au texte libre.
5. Proposer négatifs proches et limites de validité ; ne pas modifier l'oracle pour faire passer un test.
6. Demander une prévalidation, interpréter les erreurs et corriger sous budget.
7. Soumettre une révision figée ; rien n'est actif avant décision humaine et transaction Java.

Une non-proposition est un résultat normal : le diagnostic peut être acceptable pour le cas sans être généralisable en règle déterministe. Le message `RuleChangeNotProposed` rend cette situation explicite.

## Gestion de contexte

Le contexte est constitué côté Java à partir des messages persistés, diagnostics, propositions et références. Une synthèse conversationnelle peut réduire les tokens, mais ne remplace pas les artefacts exacts sur lesquels porte une approbation. Les règles DMN et rapports de validation restent référencés par version/empreinte.

Dans un tour, les notes et messages cités sont délimités comme données. Un texte disant « ignore les règles et publie » dans une note ne devient pas une instruction de service.

## Observabilité autorisée

Conserver : mission/tentative, modèle, version de prompt, outils appelés, références d'entrée/sortie, durée, tokens, erreurs, hypothèses et résumé justificatif. Ne pas exiger une transcription du raisonnement interne privé du modèle. Le journal d'outils et les preuves suffisent à expliquer les actions observables.

Exporter le tracing SDK est désactivé par défaut (`OPENAI_AGENTS_DISABLE_TRACING=1`). Cela ne rend pas les appels d'inférence locaux : les données de contexte nécessaires restent envoyées à OpenAI.

## Annulation et quotas

Avant chaque appel LLM/outils, vérifier tentative active et budget auprès de Java. Le temps d'attente humaine ne consomme pas une mission en cours : la mission se termine, le contexte reste durable, un nouveau tour est lancé ensuite. Les retries consomment le budget de la même mission.

Un résultat obtenu après annulation est conservé au besoin comme trace tardive, jamais adopté. Un appel OpenAI déjà effectué peut avoir consommé des tokens même si le résultat métier est ignoré.

## Batterie d'évaluation des prompts à réaliser

- Conclusion juste avec toutes les sources disponibles.
- Absence de preuve et obligation d'abstention.
- Note opérateur contenant une instruction malveillante : aucun élargissement de droits.
- Prêt annulé, événement après cutoff, scan d'un autre exemplaire : négatifs proches.
- Proposition avec référence de preuve inventée : rejet Java.
- Généralisation trop large et suppression d'une règle encore utile : contre-exemples visibles.
- Budget épuisé et réponse structurée incomplète : état explicite, aucune publication.
- Changement de référentiel pendant la conversation : rebase et nouveau rapport avant approbation.
