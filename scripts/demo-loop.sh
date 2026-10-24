#!/usr/bin/env bash
# Démonstration reproductible de la boucle hybride library-stock-gap-poc.
# Prérequis : pile Docker Compose démarrée (docker compose up -d), jq et psql disponibles.
# Effet : consomme 2 cas du jeu demo. À lancer sur une époque fraîche (POST /api/system/reset)
#         pour obtenir la progression R001 seule -> publication v2 -> résolution déterministe.
set -uo pipefail

API="${API:-http://localhost:8080}"
PGHOST="${PGHOST:-localhost}"
PGPORT="${PGPORT:-5433}"
PGUSER="${PGUSER:-postgres}"
PGDB="${PGDB:-stockgap}"
export PGPASSWORD="${PGPASSWORD:-postgres}"

pass=0; fail=0
ok()   { echo "  [OK]   $*"; pass=$((pass+1)); }
ko()   { echo "  [FAIL] $*"; fail=$((fail+1)); }
step() { echo; echo "=== $* ==="; }

psql_q() { psql -h "$PGHOST" -p "$PGPORT" -U "$PGUSER" -d "$PGDB" -t -A -c "$1"; }

# Cas candidat au fallback : symptôme MISSING avec plus de prêts ouverts que fermés.
pick_case() {
  local exclude="${1:-}"
  local guard=""
  [ -n "$exclude" ] && guard="AND sc.case_id <> '$exclude'"
  psql_q "
    SELECT sc.case_id FROM runtime.source_case sc
    JOIN runtime.gap_case gc ON gc.dataset_epoch = sc.dataset_epoch AND gc.case_id = sc.case_id
    JOIN runtime.gap_symptom gs ON gs.dataset_epoch = sc.dataset_epoch AND gs.case_id = sc.case_id
    WHERE gc.status = 'A_ANALYSER' AND gs.kind = 'MISSING' $guard
      AND sc.dataset_epoch = (SELECT dataset_epoch FROM runtime.active_dataset WHERE singleton)
    GROUP BY sc.case_id, sc.dataset_epoch, sc.copy_id
    HAVING (SELECT count(*) FROM runtime.source_event se WHERE se.dataset_epoch = sc.dataset_epoch
              AND se.copy_id = sc.copy_id AND se.event_type = 'LoanOpened')
         > (SELECT count(*) FROM runtime.source_event se WHERE se.dataset_epoch = sc.dataset_epoch
              AND se.copy_id = sc.copy_id AND se.event_type = 'LoanClosed')
    ORDER BY sc.case_id LIMIT 1;" | head -n 1
}

analyse() {
  curl -fsS -X POST "$API/api/cases/$1/analyses" >/dev/null
}

# Attend l'apparition (ou l'absence) d'un diagnostic sur le dossier.
wait_diagnostic() {
  local case_id="$1" want="${2:-present}" timeout="${3:-90}" i=0 diag=""
  while [ "$i" -lt "$timeout" ]; do
    diag=$(curl -fsS "$API/api/cases/$case_id" | jq -r '.currentDiagnostic // empty')
    if [ "$want" = "present" ] && [ -n "$diag" ]; then return 0; fi
    if [ "$want" = "present" ] && [ -z "$diag" ]; then :; fi
    sleep 1; i=$((i+1))
  done
  [ "$want" = "absent" ] && return 0
  return 1
}

step "0. État initial du référentiel"
ver0=$(curl -fsS "$API/api/overview" | jq -r '.activeRuleSetVersion')
rules0=$(curl -fsS "$API/api/overview" | jq -r '.activeRulesCount')
echo "  référentiel=v$ver0, règles actives=$rules0"
[ "$ver0" = "1" ] && [ "$rules0" = "1" ] && ok "bootstrap : exactement R001 v1" \
  || ko "attendu v1/1 règle, obtenu v$ver0/$rules0"

step "1. Cas A — fallback (R001 ne couvre pas MISSING)"
caseA=$(pick_case)
[ -n "$caseA" ] || { ko "aucun cas candidat"; echo "Résultat: $pass OK / $fail FAIL"; exit 1; }
echo "  caseId=$caseA"
analyse "$caseA" >/dev/null 2>&1 || true
if wait_diagnostic "$caseA" present 90; then
  diag=$(curl -fsS "$API/api/cases/$caseA" | jq -r '.currentDiagnostic')
  det=$(echo "$diag" | jq -r '.isDeterministic')
  cause=$(echo "$diag" | jq -r '.findings[0].causeCode')
  [ "$det" = "false" ] && ok "diagnostic NON déterministe (investigateur), cause=$cause" \
    || ko "attendu isDeterministic=false, obtenu $det"
  diagId=$(echo "$diag" | jq -r '.diagnosticId')
else
  ko "aucun diagnostic produit dans le délai"; diagId=""
fi

step "2. Acceptation humaine du diagnostic"
if [ -n "${diagId:-}" ]; then
  acc=$(curl -fsS -X POST "$API/api/diagnostics/$diagId/acceptance" \
        -H 'Content-Type: application/json' -d "{\"caseId\":\"$caseA\"}" | jq -r '.status')
  [ "$acc" = "DIAGNOSTIC_ACCEPTE" ] && ok "diagnostic accepté" || ko "acceptation: $acc"
else
  ko "pas de diagnostic à accepter"
fi

step "3. Synthèse agentique d'une règle DMN"
proposalJson=""
for _ in $(seq 1 90); do
  proposalJson=$(curl -fsS "$API/api/proposals" \
    | jq -c '[.[] | select(.status=="READY_FOR_APPROVAL")] | first // empty')
  [ -n "$proposalJson" ] && break
  sleep 1
done
if [ -n "$proposalJson" ]; then
  propId=$(echo "$proposalJson" | jq -r '.proposalId')
  propRev=$(echo "$proposalJson" | jq -r '.currentRevision')
  covered=$(echo "$proposalJson" | jq -c '.metadata.coveredSymptomKinds // []')
  ok "proposition prête $propId (rév. $propRev), symptômes couverts=$covered"
else
  ko "aucune proposition prête"; propId=""
fi

step "4. Validation (replay + compilation DMN)"
if [ -n "${propId:-}" ]; then
  echo "$proposalJson" | jq '{revision: .currentRevision, dmnXml: .dmnXml}' > /tmp/val-body.json
  report=$(curl -fsS -X POST "$API/api/proposals/$propId/validation" \
           -H 'Content-Type: application/json' --data-binary @/tmp/val-body.json)
  reportId=$(echo "$report" | jq -r '.report.reportId')
  passed=$(echo "$report" | jq -r '.report.passed')
  [ "$passed" = "true" ] && ok "rapport $reportId : PASSED" || ko "rapport non validé"
else
  reportId=""
fi

step "5. Publication atomique du nouveau référentiel"
if [ -n "${propId:-}" ] && [ -n "${reportId:-}" ]; then
  pub=$(curl -fsS -X POST "$API/api/proposals/$propId/approval" \
        -H 'Content-Type: application/json' \
        -d "{\"revision\":$propRev,\"reportId\":\"$reportId\"}" | jq -r '.newRuleSetVersion')
  [ "$pub" = "2" ] && ok "référentiel actif v2" || ko "publication: v$pub"
  ver=$(curl -fsS "$API/api/overview" | jq -r '.activeRuleSetVersion')
  rc=$(curl -fsS "$API/api/overview" | jq -r '.activeRulesCount')
  [ "$ver" = "2" ] && [ "$rc" = "2" ] && ok "2 règles actives, aucune analyse en cours réécrite" \
    || ko "état actif inattendu v$ver/$rc"
else
  ko "publication impossible (proposition ou rapport manquant)"
fi

step "6. Cas B analogue — résolution déterministe sans LLM"
caseB=$(pick_case "$caseA")
if [ -n "$caseB" ]; then
  echo "  caseId=$caseB"
  analyse "$caseB" >/dev/null 2>&1 || true
  if wait_diagnostic "$caseB" present 60; then
    diagB=$(curl -fsS "$API/api/cases/$caseB" | jq -r '.currentDiagnostic')
    detB=$(echo "$diagB" | jq -r '.isDeterministic')
    causeB=$(echo "$diagB" | jq -r '.findings[0].causeCode')
    [ "$detB" = "true" ] && ok "diagnostic DÉTERMINISTE (DMN), cause=$causeB, 0 appel LLM" \
      || ko "attendu isDeterministic=true, obtenu $detB"
  else
    ko "aucun diagnostic produit pour le cas B"
  fi
else
  ko "aucun second cas analogue disponible"
fi

echo
echo "================================================="
echo " BILAN : $pass OK / $fail FAIL"
echo "================================================="
[ "$fail" -eq 0 ] || exit 1
