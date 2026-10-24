#!/usr/bin/env python3
"""Tests de contrat et revue statique ; aucun test de base exécutée."""
import copy
import json
import re
import unittest
from datetime import datetime
from pathlib import Path
from uuid import UUID, uuid5, NAMESPACE_URL

from jsonschema import Draft202012Validator, FormatChecker

import verify_model

ROOT = Path(__file__).resolve().parent
SOURCE_ARRAYS = {
    "sites": "site", "locations": "location", "titles": "title", "copies": "copy",
    "systemStates": "system_state", "inventoryScopes": "inventory_scope",
    "observations": "observation", "sourceEvents": "source_event",
    "sourceAvailability": "source_availability", "operatorNotes": "operator_note", "cases": "source_case",
}


def uid(name):
    return str(uuid5(NAMESPACE_URL, "urn:library-stock-gap:model-test:" + name))


def event(kind):
    common = {"operationId": uid("operation"), "correlationId": uid("correlation"), "siteId": "CENTRE", "locationId": None}
    extra = {
        "LoanOpened": {"loanId": uid("loan")}, "LoanClosed": {"loanId": uid("loan")},
        "ReturnReceived": {"loanId": uid("loan")}, "ReceiptReceived": {"receiptId": uid("receipt")},
        "TransferDispatched": {"transferId": uid("transfer"), "fromSiteId": "CENTRE", "toSiteId": "NORD"},
        "TransferReceived": {"transferId": uid("transfer"), "fromSiteId": "CENTRE", "toSiteId": "NORD"},
        "RepairStarted": {"repairId": uid("repair")},
    }
    payload = {**common, **extra.get(kind, {})}
    if kind == "IntegrationProcessed":
        payload = {"originalEventId": uid("original"), "correlationId": uid("correlation"), "outcome": "APPLIED", "projectedStatus": "ON_SHELF", "siteId": "CENTRE", "locationId": uid("location")}
    return {"sourceEventId": uid(kind), "copyId": uid("copy"), "eventType": kind, "occurredAt": "2026-09-30T10:00:00Z", "recordedAt": "2026-09-30T10:00:10Z", "payload": payload}


class ModelContractTests(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.schema = json.loads((ROOT / "03-source-event.schema.json").read_text())
        cls.validator = Draft202012Validator(cls.schema, format_checker=FormatChecker())
        cls.kinds = [b["properties"]["eventType"]["const"] for b in cls.schema["oneOf"]]
        cls.ddl, _, cls.tables, cls.fks, cls.indexes = verify_model.load()
        cls.fixtures = sorted((ROOT.parent / "seed" / "generated").glob("*/runtime.json"))
        cls.datasets = [(p.parent.name, json.loads(p.read_text())) for p in cls.fixtures]

    def test_sql_and_plpgsql_parse_and_foreign_keys(self):
        report = verify_model.check()
        self.assertEqual(report["runtime_oracle_foreign_keys"], 0)
        self.assertTrue(report["all_columns_documented"])

    def test_every_shared_source_array_has_a_table(self):
        conventions = (ROOT.parent / "00-conventions.md").read_text()
        shared = set(re.findall(r"^- `([A-Za-z]+)`: ", conventions, re.M))
        self.assertEqual(set(SOURCE_ARRAYS), shared - {"dataset", "initialRuleSet"})
        for name, table in SOURCE_ARRAYS.items():
            self.assertIn("runtime." + table, self.tables, name)
        self.assertIn("runtime.gap_symptom", self.tables)
        self.assertIn("runtime.system_state_event", self.tables)

    def test_all_core_aggregates_present(self):
        required = {"gap_case", "analysis_run", "evidence_snapshot", "evidence_reference", "diagnostic_revision", "diagnostic_acceptance", "agent_mission", "mission_attempt", "execution_slot", "call_reservation", "expert_conversation", "conversation_turn", "chat_message", "rule", "rule_version", "rule_change_proposal", "proposal_revision", "proposal_operation", "validation_report", "rule_set_publication", "rule_set_member", "active_rule_set", "outbox_event", "inbox_event", "sse_stream", "progress_event", "analysis_batch", "batch_item", "dataset", "dataset_epoch", "active_dataset", "command_receipt"}
        self.assertTrue({"runtime." + x for x in required} <= set(self.tables))

    def test_json_schema_is_well_formed(self):
        Draft202012Validator.check_schema(self.schema)
        self.assertEqual(len(self.kinds), 8)
        self.assertEqual(len(set(self.kinds)), 8)

    def test_eight_positive_payload_variants(self):
        for kind in self.kinds:
            with self.subTest(kind=kind):
                self.validator.validate(event(kind))

    def test_missing_required_payload_key_rejected(self):
        for branch in self.schema["oneOf"]:
            kind = branch["properties"]["eventType"]["const"]
            for key in branch["properties"]["payload"]["required"]:
                with self.subTest(kind=kind, key=key):
                    sample = event(kind)
                    del sample["payload"][key]
                    self.assertFalse(self.validator.is_valid(sample))

    def test_hidden_labels_rejected_in_source_events(self):
        for kind in self.kinds:
            for key in ("expectedCause", "scenarioFamily", "injectedFault", "injectedTruth"):
                with self.subTest(kind=kind, key=key):
                    sample = event(kind)
                    sample["payload"][key] = "INTERDIT"
                    self.assertFalse(self.validator.is_valid(sample))
                    sample = event(kind)
                    sample[key] = "INTERDIT"
                    self.assertFalse(self.validator.is_valid(sample))

    def test_discriminator_and_types_rejected(self):
        sample = event("LoanOpened")
        sample["eventType"] = "ReceiptReceived"
        self.assertFalse(self.validator.is_valid(sample))
        for key, value in (("loanId", "R001"), ("loanId", None), ("operationId", 42), ("siteId", "PARIS"), ("locationId", "bad")):
            sample = event("LoanOpened")
            sample["payload"][key] = value
            self.assertFalse(self.validator.is_valid(sample), key)

    def test_utc_format_and_identity_checked(self):
        for key, value in (("recordedAt", "2026-09-30T12:00:00+02:00"), ("occurredAt", "2026-99-30T00:00:00Z"), ("sourceEventId", "not-a-uuid")):
            sample = event("LoanOpened")
            sample[key] = value
            self.assertFalse(self.validator.is_valid(sample), key)

    def test_actual_generated_profiles_and_all_events(self):
        self.assertEqual({name for name, _ in self.datasets}, {"demo", "validation", "holdout"}, "Les trois fixtures partagées doivent exister ; aucun résultat synthétique substitué")
        counts = []
        for name, data in self.datasets:
            with self.subTest(profile=name):
                for row in data["sourceEvents"]:
                    self.validator.validate(row)
                counts.append({"profile": name, "sourceEvents": len(data["sourceEvents"]), "copies": len(data["copies"]), "cases": len(data["cases"])})
        print("Profils réellement lus : " + json.dumps(counts, ensure_ascii=False, sort_keys=True))

    def test_generated_source_relations_and_sql_preconditions(self):
        for name, data in self.datasets:
            with self.subTest(profile=name):
                sites = {x["siteId"] for x in data["sites"]}
                locs = {x["locationId"]: x for x in data["locations"]}
                copies = {x["copyId"]: x for x in data["copies"]}
                events = {x["sourceEventId"]: x for x in data["sourceEvents"]}
                scopes = {x["scopeId"]: x for x in data["inventoryScopes"]}
                titles = {x["titleId"] for x in data["titles"]}
                self.assertEqual(len(copies), len(data["copies"]))
                self.assertEqual(len(events), len(data["sourceEvents"]))
                self.assertEqual(len({x["barcode"] for x in copies.values()}), len(copies))
                for row in copies.values():
                    self.assertIn(row["titleId"], titles)
                    self.assertIn(row["owningSiteId"], sites)
                self.assertEqual({s["copyId"] for s in data["systemStates"]}, set(copies))
                self.assertEqual(len(data["systemStates"]), len(copies))
                for row in data["systemStates"]:
                    self.assertIn(row["siteId"], sites)
                    if row["locationId"] is not None:
                        self.assertEqual(locs[row["locationId"]]["siteId"], row["siteId"])
                    self.assertEqual(len(row["sourceEventIds"]), len(set(row["sourceEventIds"])))
                    for source_id in row["sourceEventIds"]:
                        self.assertEqual(events[source_id]["copyId"], row["copyId"])
                for row in data["observations"]:
                    self.assertIn(row["copyId"], copies)
                    self.assertEqual(scopes[row["scopeId"]]["locationId"], row["locationId"])
                for row in data["inventoryScopes"]:
                    self.assertIn(row["locationId"], locs)
                    if row["status"] == "COMPLETE":
                        self.assertIsNotNone(row["closedAt"])
                    if row["closedAt"] is not None:
                        self.assertGreaterEqual(datetime.fromisoformat(row["closedAt"]), datetime.fromisoformat(row["startedAt"]))
                for row in data["sourceAvailability"]:
                    self.assertIn(row["copyId"], copies)
                    self.assertEqual(row["completeFrom"] is None, row["completeTo"] is None)
                    if row["status"] == "UNAVAILABLE":
                        self.assertIsNone(row["completeFrom"])
                    if row["status"] == "COMPLETE":
                        self.assertIsNotNone(row["completeFrom"])
                    if row["completeFrom"] is not None:
                        self.assertGreaterEqual(datetime.fromisoformat(row["completeTo"]), datetime.fromisoformat(row["completeFrom"]))
                self.assertEqual(len({(r["copyId"], r["source"]) for r in data["sourceAvailability"]}), len(data["sourceAvailability"]))
                for row in events.values():
                    self.assertIn(row["copyId"], copies)
                    self.assertGreaterEqual(datetime.fromisoformat(row["recordedAt"]), datetime.fromisoformat(row["occurredAt"]))
                    p = row["payload"]
                    if p["locationId"] is not None:
                        self.assertEqual(locs[p["locationId"]]["siteId"], p["siteId"])
                    if row["eventType"] == "IntegrationProcessed":
                        original = events[p["originalEventId"]]
                        self.assertNotEqual(original["eventType"], "IntegrationProcessed")
                        self.assertEqual(original["copyId"], row["copyId"])
                        self.assertEqual(original["payload"]["correlationId"], p["correlationId"])
                        self.assertLessEqual(datetime.fromisoformat(original["occurredAt"]), datetime.fromisoformat(row["occurredAt"]))
                self.assertEqual(len({r["copyId"] for r in data["cases"]}), len(data["cases"]))
                for row in data["cases"]:
                    self.assertIn(row["copyId"], copies)
                    self.assertEqual(row["status"], "A_ANALYSER")
                    self.assertTrue(row["symptoms"])
                    for symptom in row["symptoms"]:
                        self.assertTrue(symptom["expectedValue"] is None or isinstance(symptom["expectedValue"], str))
                        self.assertTrue(symptom["observedValue"] is None or isinstance(symptom["observedValue"], str))

    def test_dataset_epoch_absent_and_bootstrap_exact(self):
        for name, data in self.datasets:
            self.assertNotIn("datasetEpoch", data)
            self.assertNotIn("datasetEpoch", data["dataset"])
            UUID(data["dataset"]["datasetId"])
            self.assertEqual(data["initialRuleSet"], {"version": 1, "rules": [{"ruleId": "R001", "ruleVersion": 1, "dmnPath": "rules/R001-v1.dmn"}]})

    def test_no_oracle_columns_in_runtime(self):
        forbidden = {"expected_cause", "scenario_family", "injected_fault", "injected_truth", "admissible_diagnostic"}
        for name, table in self.tables.items():
            if name.startswith("runtime."):
                self.assertFalse(forbidden & set(table["columns"]))
        self.assertIn("scenario_family", self.tables["evaluation.case_expectation"]["columns"])

    def test_physical_guards_and_active_indexes_declared(self):
        for name in ("import_only", "immutable_row", "publication_close_once", "member_before_seal", "active_publication_closed"):
            self.assertIn(name, self.ddl)
        for name in ("analysis_one_active_idx", "mission_one_running_attempt_idx", "conversation_one_active_turn_idx", "rule_one_bootstrap_idx", "inbox_applied_sequence_idx"):
            self.assertIn("CREATE UNIQUE INDEX " + name, self.ddl)

    def test_nullable_conditional_versions_require_presence(self):
        self.assertIn("candidate_rule_version IS NOT NULL AND candidate_rule_version = 1", self.ddl)
        self.assertIn("candidate_rule_version IS NOT NULL AND candidate_rule_version > expected_rule_version", self.ddl)
        self.assertIn("CHECK ((proposal_id IS NULL) = (proposal_revision IS NULL))", self.ddl)

    def test_roles_do_not_grant_oracle_to_java_or_worker(self):
        roles = (ROOT / "02-roles.sql").read_text()
        grants = re.findall(r"GRANT .*?;", roles, re.S)
        for grant in grants:
            if "evaluation" in grant:
                self.assertNotIn("sg_java", grant)
                self.assertNotIn("sg_dispatcher", grant)
                self.assertNotIn("sg_seed_runtime", grant)
        self.assertNotIn("CREATE ROLE Admin", roles)
        self.assertNotIn("GRANT ALL", roles)
        self.assertNotIn("GRANT TRUNCATE", roles)
        self.assertIn("NOLOGIN", roles)

    def test_generated_dictionary_matches_sql(self):
        self.assertEqual((ROOT / "05-dictionnaire.md").read_text(), verify_model.dictionary())

    def test_generated_mermaid_covers_every_table_and_fk(self):
        diagram = verify_model.diagram()
        self.assertEqual((ROOT / "06-relations.md").read_text(), diagram)
        for table in self.tables:
            self.assertIn("  " + table.replace(".", "_") + " {", diagram)
        self.assertEqual(sum("--" in line and ' : "' in line for line in diagram.splitlines()), len(self.fks))


if __name__ == "__main__":
    unittest.main(verbosity=2)
