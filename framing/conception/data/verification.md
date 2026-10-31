# Vérification effective des ressources de données

## Corpus M3 — réalisé et vérifié

Le générateur produit désormais `data/private/validation/rule-corpus.json`, version `demo-validation-1.1.0`, **33 cas techniques**. Une copie identique est embarquée dans le validateur backend. Les trois portées localisation/retour/réparation possèdent des positifs, négatifs et inconnus ; six variantes isolent source de mises à jour inconnue, attente de mise à jour et mauvais lien d'opération.

La régénération en temporaire a confirmé que les données publiques validation restent identiques octet pour octet. Les attentes du corpus sont hors des preuves et ne sont pas ajoutées au runtime demo. La suite complète actuelle compte **17 tests réussis** (35,784 secondes lors de cette finalisation).

Tests backend : **41 tests réussis**, recette PostgreSQL activée. Le replay causal passe ; les règles trop larges, spécifiques au site ou ignorant l'échec sont refusées. La recette de publication annule toutes ses écritures à la fin et laisse dix dossiers vierges/R001 seule. Voir [l'état M3](../../12-etat-implementation-actuel.md). Les métriques et corpus historiques ci-dessous sont conservés comme archive de M1, pas comme mesure actuelle de publication.

## Générateur 2.0.0 et jeu minimal — réalisé

Le profil demo contient dix titres, dix exemplaires et dix dossiers 2/4/4, tous positifs et non analysés au chargement, avec 48 événements. Les profils validation et holdout restent séparés ; ils sont régénérés avec les nouvelles identités/version et conservent leurs tailles de 180/90 et 240/120.

Suite complète actuelle : **16 tests réussis** en 32,711 secondes ; références, dates, sources masquées, négatifs, données incomplètes, schéma public et reproductibilité sont contrôlés. Les cinq fichiers de chaque profil comprennent désormais un manifeste public distinct du manifeste privé. Douze empreintes des six manifestes ont été vérifiées ; les ressources backend correspondent au seul export public demo.

Le contrôle backend et les resets ont également été exécutés sur l'instance locale : voir [l'état de réalisation](../../12-etat-implementation-actuel.md). Les chiffres et empreintes des sections historiques suivantes ne décrivent pas la version 2.0.0 ; les manifestes actuels font foi.

## Actualisation du vocabulaire — 2026-10-31

Le rendu des catalogues emploie désormais « opération métier », « mise à jour du stock informatique » et « état du stock informatique ». Les extraits JSON, identifiants, codes et valeurs métier restent inchangés ; une légende explique les noms techniques conservés. Le [glossaire validé](../../06-domaine-ddd-et-cycles-de-vie.md) fait référence.

Contrôles exécutés pour cette actualisation : compilation `javac --release 17 -Xlint:all`, génération des trois profils en répertoire temporaire, comparaison octet pour octet des JSON runtime et oracles avec les fichiers livrés, comptage des sections de catalogue et recalcul des neuf empreintes des manifestes. Tous passent. Seuls les catalogues et leur SHA-256 dans chaque manifeste ont été remplacés ; aucune donnée métier ni oracle n'a été modifié.

Les mesures et empreintes ci-dessous décrivent la livraison historique, avant ce changement de rendu. La suite historique de 16 tests n'a pas été relancée pour cette actualisation ; la comparaison ciblée confirme l'absence de changement des données métier. Les empreintes actuelles des fichiers sont celles des manifestes, pas celles de cette archive.

## Résultats historiques

- Compilation réelle : `javac --release 17 -Xlint:all` réussie, sans avertissement ; classes en scratch, aucune classe dans le dépôt.
- Commande de suite : `python /home/kek/projects/library-stock-gap-ai-poc/framing/conception/seed/test_dataset.py`.
- Résultat final observé : **16 tests, 54,917 secondes, OK**.
- Cycles TDD rouge/vert exécutés pendant la construction ; la suite finale ne remplace pas ces échecs préalablement constatés.
- Contrôle indépendant des fichiers livrés : JSON Schema strict accepté pour les trois profils, toutes les empreintes recalculées et conformes, UUID des exemplaires disjoints, nombre de sections de catalogue égal au nombre de dossiers.
- Deuxième génération indépendante en scratch : **12 fichiers identiques octet pour octet**, soit runtime, oracle, catalogue et manifeste pour chacun des trois profils avec sa seed livrée.
- La suite vérifie aussi le changement de seed 42 → 43, les contre-exemples, les preuves incomplètes, les références, la chronologie, le masquage réel des journaux et l'unique règle initiale R001.

Les commandes de régénération et le détail des contrôles sont dans `../seed/README.md`. Les chiffres et empreintes ci-dessous sont extraits des fichiers effectivement produits, et non des objectifs du cadrage.

## Comptes et empreintes recalculés

```json
{
  "profiles": {
    "demo": {
      "seed": 42,
      "counts": {
        "sites": 3,
        "locations": 15,
        "titles": 300,
        "copies": 1200,
        "systemStates": 1200,
        "inventoryScopes": 137,
        "observations": 839,
        "sourceEvents": 10796,
        "sourceAvailability": 8400,
        "operatorNotes": 24,
        "cases": 120
      },
      "variants": {
        "POSITIVE": 80,
        "INCOMPLETE": 20,
        "NEAR_NEGATIVE": 20
      },
      "families": {
        "DEUX_CAUSES": 12,
        "RETOUR_NON_PROJETE": 12,
        "RECEPTION_NON_INTEGREE": 12,
        "RETARD_INTEGRATION": 12,
        "LOCALISATION": 12,
        "REPARATION_NON_PROJETEE": 12,
        "ABSENCE_INEXPLIQUEE": 12,
        "TRANSFERT_INCOHERENT": 12,
        "INVENTAIRE_INCOMPLET": 12,
        "PRET_NON_PROJETE": 12
      },
      "healthy": {
        "RECEIVING": 60,
        "ON_SHELF": 637,
        "ON_LOAN": 233,
        "IN_TRANSIT": 96,
        "IN_REPAIR": 54
      },
      "sha256": {
        "seed/generated/demo/runtime.json": "4d95200bdbafc3b85dbf338a7bae7fc4159768c30f2426ce7d8d5e8d841b2aec",
        "data/private/demo/oracle.json": "eeaf12cddd000f8ac4c3692f74291140cc56331c0e7b00420e2451f9c6195de9",
        "data/private/demo/catalogue.md": "9b2e03ee2eb73f6bfe2d4e130eba91a600b919b298dd716e1454a6ff071a0fd7",
        "data/private/demo/manifest.json": "11ea05f6bb2e604739f5c9276969dbaf78deb4c07189c528e30c443ed3e22add"
      }
    },
    "validation": {
      "seed": 4242,
      "counts": {
        "sites": 3,
        "locations": 15,
        "titles": 300,
        "copies": 180,
        "systemStates": 180,
        "inventoryScopes": 117,
        "observations": 145,
        "sourceEvents": 1611,
        "sourceAvailability": 1260,
        "operatorNotes": 18,
        "cases": 90
      },
      "variants": {
        "POSITIVE": 30,
        "INCOMPLETE": 30,
        "NEAR_NEGATIVE": 30
      },
      "families": {
        "RETARD_INTEGRATION": 9,
        "PRET_NON_PROJETE": 9,
        "ABSENCE_INEXPLIQUEE": 9,
        "REPARATION_NON_PROJETEE": 9,
        "TRANSFERT_INCOHERENT": 9,
        "RETOUR_NON_PROJETE": 9,
        "DEUX_CAUSES": 9,
        "LOCALISATION": 9,
        "RECEPTION_NON_INTEGREE": 9,
        "INVENTAIRE_INCOMPLET": 9
      },
      "healthy": {
        "ON_SHELF": 60,
        "RECEIVING": 4,
        "ON_LOAN": 17,
        "IN_TRANSIT": 6,
        "IN_REPAIR": 3
      },
      "sha256": {
        "seed/generated/validation/runtime.json": "2e450d004886487adc72b5a52b13ae61e95aa0d63fec3c0a0e912066e75052ca",
        "data/private/validation/oracle.json": "c21272607afcf5c699261754d5b27701314ec54107583f7bd05c27eb93877c80",
        "data/private/validation/catalogue.md": "a594f6a1fd32529c024100fa22f37034578bdaa77e79acb7c398505176fddc5f",
        "data/private/validation/manifest.json": "a821ddbc8abc60d7cb4f6b376819a4005604f8ac5c29441cee471ed9870b1f8e"
      }
    },
    "holdout": {
      "seed": 9001,
      "counts": {
        "sites": 3,
        "locations": 15,
        "titles": 300,
        "copies": 240,
        "systemStates": 240,
        "inventoryScopes": 151,
        "observations": 192,
        "sourceEvents": 2149,
        "sourceAvailability": 1680,
        "operatorNotes": 24,
        "cases": 120
      },
      "variants": {
        "POSITIVE": 40,
        "INCOMPLETE": 40,
        "NEAR_NEGATIVE": 40
      },
      "families": {
        "DEUX_CAUSES": 12,
        "INVENTAIRE_INCOMPLET": 12,
        "PRET_NON_PROJETE": 12,
        "RETARD_INTEGRATION": 12,
        "TRANSFERT_INCOHERENT": 12,
        "REPARATION_NON_PROJETEE": 12,
        "RECEPTION_NON_INTEGREE": 12,
        "ABSENCE_INEXPLIQUEE": 12,
        "LOCALISATION": 12,
        "RETOUR_NON_PROJETE": 12
      },
      "healthy": {
        "IN_TRANSIT": 14,
        "ON_SHELF": 77,
        "ON_LOAN": 18,
        "IN_REPAIR": 6,
        "RECEIVING": 5
      },
      "sha256": {
        "seed/generated/holdout/runtime.json": "dba75473ec3aad05c619e8f627ca2213583f25df50902ff3c374cdec84503728",
        "data/private/holdout/oracle.json": "c3f6e6efd9fb44966002bdfc4c6dda71ce1f9d0810c8e1135b04f0dd608f0707",
        "data/private/holdout/catalogue.md": "6a9ebbe856454756bc5ab604f58e0da7eea92f4aabefefcba46fbc2785c2e354",
        "data/private/holdout/manifest.json": "a08181c621f0aaef9e54324bd9d29e2d309b4e05080958805eac7e11b42dce17"
      }
    }
  },
  "generatedFilesCompared": 12,
  "sourceHashes": {
    "seed/GenerateDataset.java": "e90f8c259410b2e43aeba67c0421fa87500f778b63c3b9ffc5c882329b907b14",
    "seed/runtime.schema.json": "f91514ee60ab2aed6d28435de6e892ad0873c2a6a6bc02178e837fc6c58d38f8",
    "seed/test_dataset.py": "78a2f9f3bb8472a93604c2777124977a9c7afc62c2dc15d74b65e0f652dee36c"
  },
  "totals": {
    "copies": 1620,
    "cases": 330,
    "sourceEvents": 14556,
    "observations": 1176
  }
}
```

## Limites explicites

Aucun appel OpenAI, test de LLM, chargement DMN, import PostgreSQL, déploiement, opération Docker ou reset applicatif n'a été exécuté. L'isolation des fichiers privés doit être appliquée par le futur déploiement. La présence de ressources privées dans le dépôt n'en fait pas des données à fournir aux agents. Les catalogues exposent intégralement l'oracle à l'expert ; le holdout ne doit pas servir à ajuster les règles avant mesure. Les occurrences synthétiques ne mesurent aucune fréquence industrielle. Le générateur contient les dix scénarios de simulation et leurs attentes déclarées, pas un moteur diagnostique alternatif.
