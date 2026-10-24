"""Tests hors ligne : OpenAI est simulé, aucun secret ni Kafka requis."""
import json
import sys
import unittest
from pathlib import Path
from types import SimpleNamespace
from unittest.mock import patch

sys.path.insert(0, str(Path(__file__).resolve().parent))
import worker  # noqa: E402

ENVELOPE = {
    "eventId": "11111111-1111-4111-8111-111111111111", "missionId": "44444444-4444-4444-8444-444444444444",
    "evidenceSnapshotId": "55555555-5555-4555-8555-555555555555", "caseId": "66666666-6666-4666-8666-666666666666",
    "datasetEpoch": "22222222-2222-4222-8222-222222222222", "correlationId": "33333333-3333-4333-8333-333333333333",
}
SNAPSHOT = {
    "copyId": "99999999-9999-4999-8999-999999999999",
    "symptoms": [{"symptomId": "aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa", "kind": "MISSING"}],
    "sourceEvents": [{"sourceEventId": "bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb", "eventType": "LoanOpened"}],
    "observations": [], "operatorNotes": [],
}
DIAGNOSTIC = {
    "conclusion": "SUPPORTED",
    "findings": [{
        "subjectCopyId": SNAPSHOT["copyId"], "coveredSymptomIds": [SNAPSHOT["symptoms"][0]["symptomId"]],
        "causeCode": "LOAN_NOT_PROJECTED", "epistemicStatus": "SUPPORTED",
        "assertions": ["Le prêt ouvert est présent dans les preuves."],
        "evidenceRefs": [{"source": "LOANS", "recordId": SNAPSHOT["sourceEvents"][0]["sourceEventId"], "evidenceSnapshotId": ENVELOPE["evidenceSnapshotId"], "fieldPath": "/sourceEvents/0"}],
        "contradictionRefs": [], "recommendedChecks": ["Vérifier la projection SIGB."], "explanation": "Le modèle relie le prêt ouvert au statut de stock.",
    }], "missingEvidence": [], "summary": "Diagnostic généré après lecture du snapshot."
}


class FakeResponses:
    def __init__(self, final_payload):
        self.final_payload = final_payload
        self.calls = []

    def create(self, **kwargs):
        self.calls.append(kwargs)
        if len(self.calls) == 1:
            return SimpleNamespace(id="resp_tool", output=[SimpleNamespace(type="function_call", name="get_case_snapshot", call_id="call_1")], usage=SimpleNamespace(input_tokens=11, output_tokens=3))
        return SimpleNamespace(id="resp_final", output=[], output_text=json.dumps(self.final_payload), usage=SimpleNamespace(input_tokens=21, output_tokens=13))


class GptInvestigationTest(unittest.TestCase):
    def test_agent_calls_the_bounded_tool_then_returns_validated_diagnostic(self):
        responses = FakeResponses(DIAGNOSTIC)
        client = SimpleNamespace(responses=responses)
        with patch.object(worker, "OpenAI", return_value=client), patch.object(worker, "get_case_snapshot", return_value=SNAPSHOT), patch.object(worker, "send_kafka_event") as sent:
            diagnostic, usage = worker.run_gpt_investigation(None, ENVELOPE, "lease", 1)
        self.assertEqual("SUPPORTED", diagnostic.conclusion)
        self.assertEqual(2, usage["llmCalls"])
        self.assertEqual(1, usage["toolCalls"])
        self.assertEqual(32, usage["inputTokens"])
        self.assertEqual("get_case_snapshot", responses.calls[0]["tool_choice"]["name"])
        self.assertEqual("function_call_output", responses.calls[1]["input"][0]["type"])
        self.assertEqual("ToolObservationRecorded", sent.call_args.args[1])

    def test_agent_output_with_invented_evidence_is_rejected(self):
        invalid = json.loads(json.dumps(DIAGNOSTIC))
        invalid["findings"][0]["evidenceRefs"][0]["recordId"] = "cccccccc-cccc-4ccc-8ccc-cccccccccccc"
        client = SimpleNamespace(responses=FakeResponses(invalid))
        with patch.object(worker, "OpenAI", return_value=client), patch.object(worker, "get_case_snapshot", return_value=SNAPSHOT), patch.object(worker, "send_kafka_event"):
            with self.assertRaisesRegex(RuntimeError, "Sortie GPT invalide après correction"):
                worker.run_gpt_investigation(None, ENVELOPE, "lease", 1)

    def test_strict_schema_requires_every_declared_property(self):
        schema = worker.Diagnostic.model_json_schema()
        definitions = [schema, *schema.get("$defs", {}).values()]
        for definition in definitions:
            if "properties" in definition:
                self.assertEqual(set(definition["properties"]), set(definition.get("required", [])))

    def test_missing_api_key_produces_a_clear_failure(self):
        with patch.object(worker, "OPENAI_API_KEY", ""):
            with self.assertRaisesRegex(RuntimeError, "OPENAI_API_KEY absent"):
                worker.run_gpt_investigation(None, ENVELOPE, "lease", 1)


if __name__ == "__main__":
    unittest.main()
