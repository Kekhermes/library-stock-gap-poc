#!/usr/bin/env bash
# Vérifie une ressource de conception, sans démarrer l'application.
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
CACHE_ROOT="${TMPDIR:-$HOME/.cache/library-stock-gap-design}"
mkdir -p "$CACHE_ROOT"
WORK="$(mktemp -d "$CACHE_ROOT/dmn-design-XXXXXX")"
printf 'Répertoire de vérification : %s\n' "$WORK"
mvn -q -f "$ROOT/verification/dmn-pom.xml" dependency:copy-dependencies -DoutputDirectory="$WORK/lib"
javac -cp "$WORK/lib/*" -d "$WORK" "$ROOT/verification/VerifyBootstrapDmn.java"
java -cp "$WORK:$WORK/lib/*" VerifyBootstrapDmn "$ROOT/rules/R001-v1.dmn"
python -m unittest discover -s "$ROOT/verification" -p test_bootstrap.py -v
printf 'Vérification terminée ; artefacts temporaires conservés dans %s\n' "$WORK"
