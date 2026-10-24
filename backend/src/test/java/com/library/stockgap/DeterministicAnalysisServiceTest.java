package com.library.stockgap;

import com.library.stockgap.domain.model.*;
import com.library.stockgap.domain.service.DeterministicAnalysisService;
import com.library.stockgap.domain.service.DmnEngineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class DeterministicAnalysisServiceTest {

    private DmnEngineService dmnEngineService;
    private DeterministicAnalysisService deterministicAnalysisService;

    @BeforeEach
    void setUp() {
        dmnEngineService = new DmnEngineService();
        deterministicAnalysisService = new DeterministicAnalysisService(dmnEngineService);
    }

    private EvidenceSnapshot createStatusMismatchSnapshot(String expectedStatus, String observedStatus,
                                                           String integrationOutcome, String errorCode, String projectedStatus) {
        UUID caseId = UUID.randomUUID();
        UUID copyId = UUID.randomUUID();
        UUID symptomId = UUID.randomUUID();
        UUID eventId = UUID.randomUUID();

        List<Map<String, Object>> symptoms = List.of(Map.of(
            "symptomId", symptomId.toString(),
            "kind", "STATUS_MISMATCH",
            "expectedValue", expectedStatus,
            "observedValue", observedStatus
        ));

        Map<String, Object> systemState = Map.of(
            "siteId", "NORD",
            "status", expectedStatus
        );

        List<Map<String, Object>> observations = List.of(Map.of(
            "siteId", "NORD",
            "locationId", "loc-123",
            "quality", "VALID"
        ));

        Map<String, Object> integPayload = new HashMap<>();
        integPayload.put("outcome", integrationOutcome);
        integPayload.put("errorCode", errorCode);
        integPayload.put("projectedStatus", projectedStatus);
        integPayload.put("siteId", "NORD");

        List<Map<String, Object>> sourceEvents = List.of(
            Map.of(
                "sourceEventId", UUID.randomUUID().toString(),
                "eventType", "ReturnReceived",
                "payload", Map.of("siteId", "NORD")
            ),
            Map.of(
                "sourceEventId", eventId.toString(),
                "eventType", "IntegrationProcessed",
                "payload", integPayload
            )
        );

        List<Map<String, Object>> availability = List.of(
            Map.of("source", "INTEGRATIONS", "status", "COMPLETE"),
            Map.of("source", "LOANS", "status", "COMPLETE"),
            Map.of("source", "RETURNS", "status", "COMPLETE")
        );

        return new EvidenceSnapshot(
            UUID.randomUUID(), caseId, copyId, "1.0.0",
            Instant.now(), Instant.now(),
            Map.of(), sourceEvents, List.of(), systemState, observations, availability, symptoms
        );
    }

    @Test
    void testFactsExtractionForStatusMismatch() {
        EvidenceSnapshot snapshot = createStatusMismatchSnapshot(
            "ON_LOAN", "RECEIVING", "FAILED", "PROJECTION_WRITE_UNAVAILABLE", "RECEIVING"
        );

        Map<String, Object> facts = deterministicAnalysisService.facts(snapshot);

        assertEquals("STATUS_MISMATCH", facts.get("symptomKind"));
        assertEquals("ON_LOAN", facts.get("expectedValue"));
        assertEquals("RECEIVING", facts.get("observedValue"));
        assertEquals("ON_LOAN", facts.get("expectedStatus"));
        assertEquals("RECEIVING", facts.get("observedStatus"));
        assertEquals("ON_LOAN", facts.get("systemStatus"));
        assertEquals("NORD", facts.get("expectedSiteId"));
        assertEquals("NORD", facts.get("observedSiteId"));
        assertEquals(true, facts.get("observationReliable"));
        assertEquals("VALID", facts.get("observationQuality"));

        assertEquals("FAILED", facts.get("latestIntegrationOutcome"));
        assertEquals("PROJECTION_WRITE_UNAVAILABLE", facts.get("latestIntegrationErrorCode"));
        assertEquals("RECEIVING", facts.get("latestProjectedStatus"));
        assertEquals("COMPLETE", facts.get("integrationsAvailabilityStatus"));
        assertEquals("COMPLETE", facts.get("loansAvailabilityStatus"));
        assertEquals(1, facts.get("returnsCount"));
    }

    @Test
    void testEvaluateStatusMismatchRuleMatchesDeterministically() {
        EvidenceSnapshot snapshot = createStatusMismatchSnapshot(
            "ON_LOAN", "RECEIVING", "FAILED", "PROJECTION_WRITE_UNAVAILABLE", "RECEIVING"
        );

        String dmnXml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20230324/MODEL/"
                         id="def_status_mismatch" name="StatusMismatchRule"
                         namespace="https://example.org/dmn/diagnostics">
              <inputData id="input_symptomKind" name="symptomKind"><variable name="symptomKind" typeRef="string"/></inputData>
              <inputData id="input_outcome" name="latestIntegrationOutcome"><variable name="latestIntegrationOutcome" typeRef="string"/></inputData>
              <inputData id="input_errorCode" name="latestIntegrationErrorCode"><variable name="latestIntegrationErrorCode" typeRef="string"/></inputData>
              <decision id="decision" name="decision">
                <variable name="decision"/>
                <informationRequirement><requiredInput href="#input_symptomKind"/></informationRequirement>
                <informationRequirement><requiredInput href="#input_outcome"/></informationRequirement>
                <informationRequirement><requiredInput href="#input_errorCode"/></informationRequirement>
                <decisionTable id="table_sm" hitPolicy="UNIQUE">
                  <input id="in_1"><inputExpression typeRef="string"><text>symptomKind</text></inputExpression></input>
                  <input id="in_2"><inputExpression typeRef="string"><text>latestIntegrationOutcome</text></inputExpression></input>
                  <input id="in_3"><inputExpression typeRef="string"><text>latestIntegrationErrorCode</text></inputExpression></input>
                  <output id="out_1" name="causeCode" typeRef="string"/>
                  <output id="out_2" name="epistemicStatus" typeRef="string"/>
                  <rule id="row_1">
                    <inputEntry id="ie_1"><text>"STATUS_MISMATCH"</text></inputEntry>
                    <inputEntry id="ie_2"><text>"FAILED"</text></inputEntry>
                    <inputEntry id="ie_3"><text>"PROJECTION_WRITE_UNAVAILABLE"</text></inputEntry>
                    <outputEntry id="oe_1"><text>"PROJECTION_WRITE_UNAVAILABLE"</text></outputEntry>
                    <outputEntry id="oe_2"><text>"SUPPORTED"</text></outputEntry>
                  </rule>
                </decisionTable>
              </decision>
            </definitions>
            """;

        RuleMetadata rule = new RuleMetadata(
            "rule-sm-1", 1, "Règle Échec Projection", "AGENT", "rules/sm.dmn",
            "https://example.org/dmn/diagnostics", "StatusMismatchRule", "decision",
            "PROJECTION_WRITE_UNAVAILABLE", List.of("STATUS_MISMATCH"),
            List.of("Rejouer la projection."), List.of(),
            "sha-123", dmnXml, true, null, null
        );

        var result = deterministicAnalysisService.evaluate(snapshot, List.of(rule));

        assertTrue(result.fullyCovered(), "Le dossier doit être entièrement couvert par la règle déterministe");
        assertNull(result.fallbackReason());
        assertEquals(1, result.findings().size());

        Finding finding = result.findings().getFirst();
        assertEquals("PROJECTION_WRITE_UNAVAILABLE", finding.causeCode());
        assertEquals(EpistemicStatus.SUPPORTED, finding.epistemicStatus());
        assertEquals("rule-sm-1", finding.ruleId());
    }

    @Test
    void testEvaluateRuleWithSingleOutputAndNoMatchWhenConditionsDiffer() {
        EvidenceSnapshot snapshot = createStatusMismatchSnapshot(
            "ON_LOAN", "RECEIVING", "APPLIED", null, "ON_LOAN"
        );

        String dmnXml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20230324/MODEL/"
                         id="def_status_mismatch_single" name="StatusMismatchSingleOutput"
                         namespace="https://example.org/dmn/single">
              <inputData id="input_outcome" name="latestIntegrationOutcome"><variable name="latestIntegrationOutcome" typeRef="string"/></inputData>
              <decision id="decision" name="decision">
                <variable name="decision"/>
                <informationRequirement><requiredInput href="#input_outcome"/></informationRequirement>
                <decisionTable id="table_single" hitPolicy="UNIQUE">
                  <input id="in_1"><inputExpression typeRef="string"><text>latestIntegrationOutcome</text></inputExpression></input>
                  <output id="out_1" name="recommendation" typeRef="string"/>
                  <rule id="row_1">
                    <inputEntry id="ie_1"><text>"FAILED"</text></inputEntry>
                    <outputEntry id="oe_1"><text>"RECONCILIATION_REQUIRED"</text></outputEntry>
                  </rule>
                </decisionTable>
              </decision>
            </definitions>
            """;

        RuleMetadata rule = new RuleMetadata(
            "rule-single", 1, "Règle Simple Output", "AGENT", "rules/single.dmn",
            "https://example.org/dmn/single", "StatusMismatchSingleOutput", "decision",
            "PROJECTION_WRITE_UNAVAILABLE", List.of("STATUS_MISMATCH"),
            List.of(), List.of(),
            "sha-single", dmnXml, true, null, null
        );

        var result = deterministicAnalysisService.evaluate(snapshot, List.of(rule));

        assertFalse(result.fullyCovered());
        assertEquals(FallbackReason.NO_RULE_MATCH, result.fallbackReason());
        assertTrue(result.findings().isEmpty());
    }

    @Test
    void testExistingDatabaseRule8bfdMatchesOnStatusMismatchCase() {
        // Case corresponding to repair projection failure: expected ON_SHELF, observed IN_REPAIR
        EvidenceSnapshot snapshot = createStatusMismatchSnapshot(
            "ON_SHELF", "IN_REPAIR", "FAILED", "PROJECTION_WRITE_UNAVAILABLE", "IN_REPAIR"
        );

        String ruleXml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20230324/MODEL/" id="definitions_status_mismatch" name="Definitions_status_mismatch" namespace="https://example.org/dmn/status-mismatch">
              <inputData id="InputData_symptomKind" name="symptomKind"><variable name="symptomKind" typeRef="string" /></inputData>
              <inputData id="InputData_expectedStatus" name="expectedStatus"><variable name="expectedStatus" typeRef="string" /></inputData>
              <inputData id="InputData_observedStatus" name="observedStatus"><variable name="observedStatus" typeRef="string" /></inputData>
              <inputData id="InputData_systemStatus" name="systemStatus"><variable name="systemStatus" typeRef="string" /></inputData>
              <inputData id="InputData_observationQuality" name="observationQuality"><variable name="observationQuality" typeRef="string" /></inputData>
              <inputData id="InputData_latestIntegrationOutcome" name="latestIntegrationOutcome"><variable name="latestIntegrationOutcome" typeRef="string" /></inputData>
              <inputData id="InputData_latestProjectedStatus" name="latestProjectedStatus"><variable name="latestProjectedStatus" typeRef="string" /></inputData>
              <inputData id="InputData_latestErrorCode" name="latestErrorCode"><variable name="latestErrorCode" typeRef="string" /></inputData>
              <decision id="Decision_decision" name="decision">
                <variable name="decision" typeRef="string" />
                <informationRequirement><requiredInput href="#InputData_symptomKind" /></informationRequirement>
                <informationRequirement><requiredInput href="#InputData_expectedStatus" /></informationRequirement>
                <informationRequirement><requiredInput href="#InputData_observedStatus" /></informationRequirement>
                <informationRequirement><requiredInput href="#InputData_systemStatus" /></informationRequirement>
                <informationRequirement><requiredInput href="#InputData_observationQuality" /></informationRequirement>
                <informationRequirement><requiredInput href="#InputData_latestIntegrationOutcome" /></informationRequirement>
                <informationRequirement><requiredInput href="#InputData_latestProjectedStatus" /></informationRequirement>
                <informationRequirement><requiredInput href="#InputData_latestErrorCode" /></informationRequirement>
                <decisionTable id="DecisionTable_1" hitPolicy="UNIQUE">
                  <input id="Input_symptomKind"><inputExpression typeRef="string"><text>symptomKind</text></inputExpression></input>
                  <input id="Input_expectedStatus"><inputExpression typeRef="string"><text>expectedStatus</text></inputExpression></input>
                  <input id="Input_observedStatus"><inputExpression typeRef="string"><text>observedStatus</text></inputExpression></input>
                  <input id="Input_systemStatus"><inputExpression typeRef="string"><text>systemStatus</text></inputExpression></input>
                  <input id="Input_observationQuality"><inputExpression typeRef="string"><text>observationQuality</text></inputExpression></input>
                  <input id="Input_latestIntegrationOutcome"><inputExpression typeRef="string"><text>latestIntegrationOutcome</text></inputExpression></input>
                  <input id="Input_latestProjectedStatus"><inputExpression typeRef="string"><text>latestProjectedStatus</text></inputExpression></input>
                  <input id="Input_latestErrorCode"><inputExpression typeRef="string"><text>latestErrorCode</text></inputExpression></input>
                  <output id="Output_recommendation" name="recommendation" typeRef="string" />
                  <rule id="Rule_1">
                    <inputEntry id="Entry_symptomKind"><text>"STATUS_MISMATCH"</text></inputEntry>
                    <inputEntry id="Entry_expectedStatus"><text>"ON_SHELF"</text></inputEntry>
                    <inputEntry id="Entry_observedStatus"><text>"IN_REPAIR"</text></inputEntry>
                    <inputEntry id="Entry_systemStatus"><text>"ON_SHELF"</text></inputEntry>
                    <inputEntry id="Entry_observationQuality"><text>"VALID"</text></inputEntry>
                    <inputEntry id="Entry_latestIntegrationOutcome"><text>"FAILED"</text></inputEntry>
                    <inputEntry id="Entry_latestProjectedStatus"><text>"IN_REPAIR"</text></inputEntry>
                    <inputEntry id="Entry_latestErrorCode"><text>"PROJECTION_WRITE_UNAVAILABLE"</text></inputEntry>
                    <outputEntry id="OutputEntry_recommendation"><text>"REVIEW_PROJECTION_AND_RECONCILE_STATUS"</text></outputEntry>
                  </rule>
                </decisionTable>
              </decision>
            </definitions>
            """;

        RuleMetadata rule8bfd = new RuleMetadata(
            "8bfd622a-a794-459c-a102-7af4857305d3", 1,
            "Reconnaissance d’un statut projeté après échec d’intégration", "AGENT",
            "rules/8bfd.dmn", "https://example.org/dmn/status-mismatch", "Definitions_status_mismatch",
            "decision", "PROJECTION_WRITE_UNAVAILABLE", List.of("STATUS_MISMATCH"),
            List.of("Contrôler la conformité du stock."), List.of("Proposition synthétisée par agent."),
            "sha-8bfd", ruleXml, true, null, null
        );

        var result = deterministicAnalysisService.evaluate(snapshot, List.of(rule8bfd));

        assertTrue(result.fullyCovered(), "La règle 8bfd de la base doit maintenant matcher le cas STATUS_MISMATCH !");
        assertEquals(1, result.findings().size());
        assertEquals("PROJECTION_WRITE_UNAVAILABLE", result.findings().getFirst().causeCode());
        assertEquals(EpistemicStatus.SUPPORTED, result.findings().getFirst().epistemicStatus());
    }
}
