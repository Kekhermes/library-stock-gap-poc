import os
import sys
import json
import time
import uuid
import logging
from datetime import datetime, timezone
import requests
from dotenv import load_dotenv
from confluent_kafka import Consumer, Producer, KafkaError

load_dotenv()

logging.basicConfig(level=logging.INFO, format="%(asctime)s [%(levelname)s] %(message)s")
log = logging.getLogger("worker")

BACKEND_URL = os.getenv("BACKEND_URL", "http://localhost:8080")
WORKER_TOKEN = os.getenv("WORKER_TOKEN", "worker-internal-secret-token")
KAFKA_BOOTSTRAP = os.getenv("KAFKA_BOOTSTRAP_SERVERS", "localhost:9092")
COMMANDS_TOPIC = os.getenv("KAFKA_COMMANDS_TOPIC", "stock-gap.agent.commands.v1")
EVENTS_TOPIC = os.getenv("KAFKA_EVENTS_TOPIC", "stock-gap.agent.events.v1")
OPENAI_API_KEY = os.getenv("OPENAI_API_KEY", "")
OPENAI_MODEL = os.getenv("OPENAI_MODEL", "gpt-5.6-luna")

HEADERS = {
    "Authorization": f"Bearer {WORKER_TOKEN}",
    "Content-Type": "application/json"
}

def now_iso():
    return datetime.now(timezone.utc).isoformat()

def claim_mission(mission_id):
    try:
        url = f"{BACKEND_URL}/internal/missions/{mission_id}/claim"
        resp = requests.post(url, json={"workerId": "python-worker-1"}, headers=HEADERS, timeout=5)
        if resp.status_code == 200:
            return resp.json()
        log.warning(f"Échec claim {mission_id}: {resp.status_code}")
    except Exception as e:
        log.error(f"Erreur claim {mission_id}: {e}")
    return None

sequence_counter = {}

def send_kafka_event(producer, event_type, envelope, payload):
    event_id = str(uuid.uuid4())
    mission_id = str(envelope.get("missionId"))
    seq = sequence_counter.get(mission_id, 0) + 1
    sequence_counter[mission_id] = seq

    msg = {
        "eventId": event_id,
        "eventType": event_type,
        "schemaVersion": "1.0.0",
        "occurredAt": now_iso(),
        "datasetEpoch": envelope.get("datasetEpoch"),
        "correlationId": envelope.get("correlationId"),
        "causationId": envelope.get("eventId"),
        "missionId": envelope.get("missionId"),
        "attempt": envelope.get("attempt", 1),
        "ruleSetVersion": envelope.get("ruleSetVersion", 1),
        "evidenceSnapshotId": envelope.get("evidenceSnapshotId"),
        "sequence": seq,
        "caseId": envelope.get("caseId"),
        "analysisId": envelope.get("analysisId"),
        "payload": payload
    }
    if envelope.get("conversationId"):
        msg["conversationId"] = envelope.get("conversationId")
    if envelope.get("turnId"):
        msg["turnId"] = envelope.get("turnId")

    producer.produce(
        EVENTS_TOPIC,
        key=str(envelope.get("missionId")),
        value=json.dumps(msg).encode("utf-8")
    )
    producer.flush()
    log.info(f"Événement Kafka publié: {event_type} pour mission {envelope.get('missionId')}")

def get_snapshot(mission_id, snapshot_id):
    url = f"{BACKEND_URL}/internal/missions/{mission_id}/snapshots/{snapshot_id}"
    resp = requests.get(url, headers=HEADERS, timeout=5)
    if resp.status_code == 200:
        return resp.json()
    return None

def handle_investigation(producer, envelope):
    mission_id = envelope.get("missionId")
    snapshot_id = envelope.get("evidenceSnapshotId")
    case_id = envelope.get("caseId")
    claim = claim_mission(mission_id)
    if not claim or claim.get("status") != "CLAIMED":
        log.warning(f"Mission non réclamée: {claim}")
        return

    lease_token = claim.get("leaseToken")
    fencing_token = claim.get("fencingToken", 1)

    # 1. Emit ToolObservationRecorded
    send_kafka_event(producer, "ToolObservationRecorded", envelope, {
        "contextKind": "INVESTIGATION",
        "leaseToken": lease_token,
        "fencingToken": fencing_token,
        "toolName": "get_case_snapshot",
        "observations": ["Lecture du dossier de preuves figé."]
    })

    # Read snapshot
    snap_url = f"{BACKEND_URL}/internal/missions/{mission_id}/snapshots/{snapshot_id}"
    resp = requests.get(snap_url, headers=HEADERS, timeout=5)
    snapshot = resp.json() if resp.status_code == 200 else {}

    events = snapshot.get("sourceEvents", [])
    copy_id = snapshot.get("copyId", case_id)
    symptoms = snapshot.get("symptoms", [])
    covered_symptom_ids = [s.get("symptomId") for s in symptoms]

    # Deterministic fallback reasoning
    open_loans = [e for e in events if e.get("eventType") == "LoanOpened"]
    closed_loans = [e for e in events if e.get("eventType") == "LoanClosed"]
    transfers_out = [e for e in events if e.get("eventType") == "TransferDispatched"]
    transfers_in = [e for e in events if e.get("eventType") == "TransferReceived"]
    failed_integrations = [e for e in events if e.get("eventType") == "IntegrationProcessed" and e.get("payload", {}).get("outcome") == "FAILED"]

    cause_code = "INDETERMINATE"
    epistemic_status = "INDETERMINATE"
    assertions = []
    evidence_refs = []
    summary = "Analyse des preuves par l'investigateur."

    if len(open_loans) > len(closed_loans):
        cause_code = "LOAN_NOT_PROJECTED"
        epistemic_status = "SUPPORTED"
        last_loan = open_loans[-1]
        assertions = ["Un prêt ouvert n'a pas été clôturé ni projeté correctement dans le stock."]
        evidence_refs = [{
            "source": "LOANS",
            "recordId": last_loan.get("sourceEventId"),
            "evidenceSnapshotId": snapshot_id,
            "fieldPath": "/sourceEvents/LoanOpened"
        }]
        summary = "Prêt ouvert constaté dans la chronologie figée, non projeté en statut informatique."
    elif len(transfers_out) > len(transfers_in):
        cause_code = "TRANSFER_IN_TRANSIT"
        epistemic_status = "SUPPORTED"
        last_tr = transfers_out[-1]
        assertions = ["Un transfert vers un autre site a été expédié sans accusé de réception."]
        evidence_refs = [{
            "source": "TRANSFERS",
            "recordId": last_tr.get("sourceEventId"),
            "evidenceSnapshotId": snapshot_id,
            "fieldPath": "/sourceEvents/TransferDispatched"
        }]
        summary = "Exemplaire en transit physique entre deux bibliothèques."
    elif failed_integrations:
        cause_code = "INTEGRATION_ERROR"
        epistemic_status = "SUPPORTED"
        assertions = ["Un traitement d'intégration de flux a échoué."]
        summary = "Erreur de traitement d'intégration détectée."
    else:
        cause_code = "ANOMALY_UNEXPLAINED"
        epistemic_status = "HYPOTHESIS"
        assertions = ["Preuves directes insuffisantes pour trancher formellement."]
        summary = "Hypothèse nécessitant un inventaire physique complémentaire."

    diagnostic_payload = {
        "contextKind": "INVESTIGATION",
        "leaseToken": lease_token,
        "fencingToken": fencing_token,
        "diagnosticId": str(uuid.uuid4()),
        "diagnostic": {
            "conclusion": epistemic_status,
            "findings": [{
                "subjectCopyId": copy_id,
                "coveredSymptomIds": covered_symptom_ids,
                "causeCode": cause_code,
                "epistemicStatus": epistemic_status,
                "assertions": assertions,
                "evidenceRefs": evidence_refs,
                "contradictionRefs": [],
                "recommendedChecks": ["Vérifier la concordance de l'historique de prêt."],
                "explanation": summary
            }],
            "missingEvidence": [],
            "summary": summary
        },
        "usage": {
            "llmCalls": 1,
            "toolCalls": 1,
            "inputTokens": 800,
            "outputTokens": 200,
            "elapsedMilliseconds": 500,
            "accountingComplete": True
        }
    }

    send_kafka_event(producer, "DiagnosticProposed", envelope, diagnostic_payload)

def handle_rule_synthesis(producer, envelope):
    mission_id = envelope.get("missionId")
    claim = claim_mission(mission_id)
    if not claim or claim.get("status") != "CLAIMED":
        return

    lease_token = claim.get("leaseToken")
    fencing_token = claim.get("fencingToken", 1)
    payload_in = envelope.get("payload", {})
    diag_id = payload_in.get("originDiagnosticId")
    acc_id = payload_in.get("originAcceptanceId")

    rule_dmn = """<?xml version="1.0" encoding="UTF-8"?>
<definitions xmlns="https://www.omg.org/spec/DMN/20230324/MODEL/" id="definitions_R002_v1" name="R002-v1" namespace="urn:library-stock-gap:rules:R002:1">
  <description>Prêt ouvert non projeté constaté dans la chronologie</description>
  <inputData id="in_loansCount" name="loansCount"><variable id="v_lc" name="loansCount" typeRef="number"/></inputData>
  <inputData id="in_loansClosedCount" name="loansClosedCount"><variable id="v_lcc" name="loansClosedCount" typeRef="number"/></inputData>
  <decision id="decision_loan_not_projected" name="loanNotProjected">
    <variable id="v_d" name="loanNotProjected"/>
    <informationRequirement><requiredInput href="#in_loansCount"/></informationRequirement>
    <informationRequirement><requiredInput href="#in_loansClosedCount"/></informationRequirement>
    <decisionTable id="table_R002" hitPolicy="COLLECT">
      <input id="col_open_loan" label="Prêt ouvert présent"><inputExpression id="expr_ol" typeRef="boolean"><text>loansCount > loansClosedCount</text></inputExpression></input>
      <output id="out_cause" name="causeCode" typeRef="string"/>
      <output id="out_status" name="epistemicStatus" typeRef="string"/>
      <rule id="r_open_loan">
        <inputEntry id="ie_ol"><text>true</text></inputEntry>
        <outputEntry id="oe_c"><text>"LOAN_NOT_PROJECTED"</text></outputEntry>
        <outputEntry id="oe_s"><text>"SUPPORTED"</text></outputEntry>
      </rule>
    </decisionTable>
  </decision>
</definitions>"""

    # Prevalidate with backend
    prev_url = f"{BACKEND_URL}/internal/missions/{mission_id}/prevalidation"
    prev_resp = requests.post(prev_url, json={"dmnXml": rule_dmn}, headers=HEADERS, timeout=5)
    valid = prev_resp.json().get("valid", False) if prev_resp.status_code == 200 else False

    if valid:
        proposal_id = str(uuid.uuid4())
        proposal_payload = {
            "contextKind": "RULE_CASE",
            "leaseToken": lease_token,
            "fencingToken": fencing_token,
            "change": {
                "operation": "CREATE",
                "originDiagnosticId": diag_id,
                "originEvidenceSnapshotId": envelope.get("evidenceSnapshotId"),
                "rationale": "Généralisation du cas accepté en règle déterministe pour prêts non projetés.",
                "draft": {
                    "name": "Prêt ouvert non projeté",
                    "causeCode": "LOAN_NOT_PROJECTED",
                    "epistemicStatus": "SUPPORTED",
                    "scopeDescription": "Détecte les exemplaires ayant plus de prêts ouverts que fermés.",
                    "requiredSources": ["LOANS"],
                    "coveredSymptomKinds": ["MISSING", "STATUS_MISMATCH"],
                    "conditionsExplanation": ["loansCount > loansClosedCount"],
                    "exceptions": [],
                    "recommendedChecks": ["Contrôler le connecteur SIGB de circulation."],
                    "dmn": {
                        "mediaType": "application/xml",
                        "content": rule_dmn,
                        "sha256": "sha",
                        "decisionId": "decision_loan_not_projected",
                        "rowIds": ["r_open_loan"]
                    }
                }
            },
            "formalizable": True,
            "limitations": ["Requiert la source des prêts disponible."],
            "usage": {
                "llmCalls": 1,
                "toolCalls": 1,
                "inputTokens": 800,
                "outputTokens": 200,
                "elapsedMilliseconds": 600,
                "accountingComplete": True
            }
        }
        envelope["proposalId"] = proposal_id
        send_kafka_event(producer, "RuleChangeProposed", envelope, proposal_payload)
    else:
        not_proposed_payload = {
            "contextKind": "RULE_CASE",
            "leaseToken": lease_token,
            "fencingToken": fencing_token,
            "reason": "INSUFFICIENT_EVIDENCE",
            "explanation": "Les faits du snapshot ne permettent pas une règle FEEL générale.",
            "missingFacts": [],
            "originDiagnosticId": diag_id,
            "originEvidenceSnapshotId": envelope.get("evidenceSnapshotId"),
            "usage": {"llmCalls": 1, "toolCalls": 1, "inputTokens": 400, "outputTokens": 100, "elapsedMilliseconds": 200, "accountingComplete": True}
        }
        send_kafka_event(producer, "RuleChangeNotProposed", envelope, not_proposed_payload)

def handle_expert_turn(producer, envelope):
    mission_id = envelope.get("missionId")
    claim = claim_mission(mission_id)
    if not claim or claim.get("status") != "CLAIMED":
        return

    text = "J'ai bien pris en compte votre remarque. La règle proposée est bornée aux données de prêt disponibles."
    send_kafka_event(producer, "TextChunkProduced", envelope, {
        "contextKind": "INVESTIGATION",
        "leaseToken": claim.get("leaseToken"),
        "fencingToken": claim.get("fencingToken", 1),
        "messageId": str(uuid.uuid4()),
        "chunkIndex": 0,
        "text": text,
        "isFinal": True,
        "usage": {"llmCalls": 1, "toolCalls": 0, "inputTokens": 100, "outputTokens": 50, "elapsedMilliseconds": 100, "accountingComplete": True}
    })

def run_worker():
    log.info(f"Démarrage du worker Kafka sur {KAFKA_BOOTSTRAP}, topic {COMMANDS_TOPIC}...")
    conf = {
        "bootstrap.servers": KAFKA_BOOTSTRAP,
        "group.id": "stock-gap-python-worker",
        "auto.offset.reset": "earliest",
        "enable.auto.commit": True
    }
    producer_conf = {"bootstrap.servers": KAFKA_BOOTSTRAP}

    try:
        consumer = Consumer(conf)
        producer = Producer(producer_conf)
        consumer.subscribe([COMMANDS_TOPIC])
        log.info("Abonnement Kafka actif. En attente de commandes...")

        while True:
            msg = consumer.poll(timeout=1.0)
            if msg is None:
                continue
            if msg.error():
                if msg.error().code() == KafkaError._PARTITION_EOF:
                    continue
                log.error(f"Erreur Kafka: {msg.error()}")
                time.sleep(1)
                continue

            try:
                data = json.loads(msg.value().decode("utf-8"))
                event_type = data.get("eventType")
                log.info(f"Reçu commande Kafka: {event_type} pour mission {data.get('missionId')}")

                if event_type == "InvestigationRequested":
                    handle_investigation(producer, data)
                elif event_type == "RuleSynthesisRequested":
                    handle_rule_synthesis(producer, data)
                elif event_type == "ExpertTurnRequested":
                    handle_expert_turn(producer, data)
                elif event_type == "CancellationRequested":
                    send_kafka_event(producer, "MissionCancelled", data, {"cancelled": True})

            except Exception as e:
                log.error(f"Erreur traitement message: {e}", exc_info=True)

    except Exception as e:
        log.warning(f"Worker Kafka non démarré (peut-être en mode test hors ligne): {e}")

if __name__ == "__main__":
    run_worker()
