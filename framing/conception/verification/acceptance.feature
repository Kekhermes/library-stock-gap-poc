# language: fr
Fonctionnalité: Boucle hybride et gouvernance des règles
  # Spécification exécutable à raccorder à de futures steps ; non exécutée contre une application dans ce dossier.

  Scénario: Le bootstrap ne cache pas un catalogue de diagnostics
    Étant donné le profil demo-v1 initialisé
    Alors exactement une règle de diagnostic R001 version 1 est active
    Et aucune règle acquise ni conversation antérieure n'est active

  Scénario: Une localisation simple est expliquée sans IA
    Étant donné une observation fiable à un emplacement différent du même site
    Et un seul symptôme LOCATION_MISMATCH
    Quand je lance l'analyse
    Alors R001 contribue au diagnostic
    Et aucun appel LLM n'est effectué

  Scénario: Une correspondance partielle ne bloque pas le fallback
    Étant donné un cas avec LOCATION_MISMATCH et STATUS_MISMATCH
    Quand R001 ne couvre que LOCATION_MISMATCH
    Alors le diagnostic porte la couverture partielle
    Et une investigation est lancée avec le motif PARTIAL_COVERAGE

  Scénario: L'absence de source ne devient pas une preuve
    Étant donné un exemplaire non observé et une source prêts indisponible
    Quand l'investigation ne trouve pas d'autre preuve suffisante
    Alors elle conclut à l'indétermination ou explicite plusieurs hypothèses
    Et elle n'affirme pas un vol

  Scénario: Deux validations humaines distinctes
    Étant donné un diagnostic agentique accepté
    Et une proposition de règle avec rapport valide
    Quand j'accepte seulement le diagnostic
    Alors la proposition n'est pas publiée
    Quand j'approuve explicitement la révision testée de la proposition
    Alors un nouveau référentiel devient actif atomiquement

  Scénario: Un message de chat ne publie rien
    Étant donné une proposition prête à approuver
    Quand j'écris "oui publie cette règle" dans le chat
    Alors aucune publication n'est effectuée par l'agent
    Et l'acte applicatif d'approbation reste nécessaire

  Scénario: Une proposition peut honnêtement ne pas exister
    Étant donné un diagnostic accepté dépendant uniquement d'une note libre
    Et aucun fait structuré permettant sa généralisation
    Quand l'agent de règles termine sa mission
    Alors RuleChangeNotProposed indique la limitation
    Et aucune règle déterministe prétendument équivalente n'est créée

  Scénario: La publication est immédiate mais ne réécrit pas le passé
    Étant donné une analyse A démarrée sur la publication 1
    Quand je publie atomiquement la publication 2
    Et je démarre une analyse B analogue
    Alors B utilise la publication 2
    Et A conserve la publication 1
    Et aucun redémarrage du backend n'est nécessaire

  Scénario: Les propositions périmées sont bloquées
    Étant donné une proposition testée sur la publication 1
    Et la publication 2 devenue active
    Quand j'essaie de publier sans réévaluation
    Alors une erreur de conflit 409 est retournée
    Et la publication active reste 2

  Scénario: Retirer conserve l'explicabilité historique
    Étant donné une règle ayant participé à un diagnostic antérieur
    Quand l'Admin confirme son retrait
    Alors les nouvelles analyses ne la chargent plus
    Et les anciennes affichent toujours sa version et ses preuves

  Scénario: Un événement retardé après reset ne change pas le nouveau jeu
    Étant donné une mission lancée sous l'époque E1
    Et un reset ayant créé l'époque E2
    Quand un résultat de E1 est consommé
    Alors il n'est pas adopté dans E2

  Scénario: Un doublon Kafka ne publie pas deux fois
    Étant donné un résultat de mission déjà adopté
    Quand le même eventId est reçu de nouveau
    Alors aucun second diagnostic ou changement métier n'est créé

  Scénario: L'annulation gagne sur un résultat tardif
    Étant donné une mission annulée durablement
    Quand son ancien worker envoie un résultat complet
    Alors le résultat n'est pas adopté
    Et aucune proposition n'est publiée

  Scénario: Les limites sont globales et par mission
    Étant donné deux missions LLM déjà actives
    Quand un nouveau lot est lancé
    Alors aucun troisième appel LLM concurrent n'est autorisé
    Et les cas attendent en file sans consommer leur durée active

  Scénario: Le corpus réservé reste hors de portée des agents
    Étant donné les datasets demo, validation et holdout
    Quand l'investigateur interroge ses outils
    Alors ni oracle ni famille injectée ni assertions de holdout ne sont accessibles

  Scénario: Les réponses après reconnexion sont cohérentes
    Étant donné une modification entre le snapshot REST et la connexion SSE
    Quand le client reprend au curseur du snapshot
    Alors cette modification n'est pas perdue
    Et un curseur expiré provoque une resynchronisation explicite
