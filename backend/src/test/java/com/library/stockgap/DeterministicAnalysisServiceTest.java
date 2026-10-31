package com.library.stockgap;

import com.library.stockgap.domain.model.*;
import com.library.stockgap.domain.service.DeterministicAnalysisService;
import com.library.stockgap.domain.service.DmnEngineService;
import com.library.stockgap.domain.service.AnalysisOrchestrationService;
import com.library.stockgap.domain.service.SseHubService;
import com.library.stockgap.infrastructure.persistence.CaseRepository;
import com.library.stockgap.infrastructure.persistence.AnalysisRepository;
import com.library.stockgap.infrastructure.persistence.RuleRepository;
import com.library.stockgap.infrastructure.persistence.MissionRepository;
import com.library.stockgap.infrastructure.persistence.OutboxRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
        UUID operationId = UUID.randomUUID();
        String correlationId = UUID.randomUUID().toString();
        Instant reference = Instant.parse("2026-09-30T12:00:00Z");

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
            "observationId", UUID.randomUUID().toString(),
            "observedAt", reference.minusSeconds(30),
            "quality", "VALID"
        ));

        Map<String, Object> integPayload = new HashMap<>();
        integPayload.put("outcome", integrationOutcome);
        integPayload.put("errorCode", errorCode);
        integPayload.put("projectedStatus", projectedStatus);
        integPayload.put("siteId", "NORD");
        integPayload.put("originalEventId", operationId.toString());
        integPayload.put("correlationId", correlationId);

        List<Map<String, Object>> sourceEvents = List.of(
            Map.of(
                "sourceEventId", operationId.toString(),
                "eventType", "ReturnReceived",
                "occurredAt", reference.minusSeconds(120),
                "recordedAt", reference.minusSeconds(110),
                "payload", Map.of("siteId", "NORD", "correlationId", correlationId)
            ),
            Map.of(
                "sourceEventId", eventId.toString(),
                "eventType", "IntegrationProcessed",
                "occurredAt", reference.minusSeconds(90),
                "recordedAt", reference.minusSeconds(80),
                "payload", integPayload
            )
        );

        List<Map<String, Object>> availability = List.of(
            Map.of("source", "INTEGRATIONS", "status", "COMPLETE", "completeFrom", reference.minusSeconds(86400), "completeTo", reference),
            Map.of("source", "LOANS", "status", "COMPLETE", "completeFrom", reference.minusSeconds(86400), "completeTo", reference),
            Map.of("source", "RETURNS", "status", "COMPLETE", "completeFrom", reference.minusSeconds(86400), "completeTo", reference)
        );

        return new EvidenceSnapshot(
            UUID.randomUUID(), caseId, copyId, "1.0.0",
            reference, reference,
            Map.of(), sourceEvents, List.of(), systemState, observations, availability, symptoms
        );
    }

    @Test
    void contradictoryScansDoNotProduceAReliableLocation() {
        var evidence = createStatusMismatchSnapshot("ON_LOAN", "RECEIVING", "FAILED", "ERROR", "RECEIVING");
        List<Map<String, Object>> observations = List.of(
            Map.of("siteId", "NORD", "locationId", "returns", "quality", "VALID", "observedAt", evidence.inventoryReferenceTime().minusSeconds(10)),
            Map.of("siteId", "NORD", "locationId", "repair", "quality", "VALID", "observedAt", evidence.inventoryReferenceTime().minusSeconds(5)));
        var facts = deterministicAnalysisService.facts(withEvidence(evidence, observations, evidence.sourceEvents(), evidence.sourceAvailability()));
        assertNull(facts.get("observationReliable"));
        assertNull(facts.get("observedLocationId"));
        assertNull(facts.get("observedStatus"));
        assertEquals("AMBIGUOUS", facts.get("observationQuality"));
    }

    @Test
    void missingObservationRemainsUnknown() {
        var evidence = createStatusMismatchSnapshot("ON_LOAN", "RECEIVING", "FAILED", "ERROR", "RECEIVING");
        var facts = deterministicAnalysisService.facts(withEvidence(evidence, List.of(), evidence.sourceEvents(), evidence.sourceAvailability()));
        assertNull(facts.get("observationReliable"));
        assertNull(facts.get("observedSiteId"));
    }

    @Test
    void successfulRetryWinsEvenWhenTheEventListIsReversed() {
        var evidence = createStatusMismatchSnapshot("ON_LOAN", "RECEIVING", "FAILED", "ERROR", "RECEIVING");
        Map<String, Object> failed = evidence.sourceEvents().getLast();
        Map<String, Object> payload = new HashMap<>((Map<String, Object>) failed.get("payload"));
        payload.put("outcome", "APPLIED");
        payload.put("errorCode", null);
        Map<String, Object> applied = new HashMap<>(failed);
        applied.put("sourceEventId", UUID.randomUUID().toString());
        applied.put("occurredAt", evidence.inventoryReferenceTime().minusSeconds(60));
        applied.put("recordedAt", evidence.inventoryReferenceTime().minusSeconds(50));
        applied.put("payload", payload);
        var facts = deterministicAnalysisService.facts(withEvidence(evidence, evidence.observations(),
            List.of(applied, failed, evidence.sourceEvents().getFirst()), evidence.sourceAvailability()));
        assertEquals("APPLIED", facts.get("latestIntegrationOutcome"));
        assertNull(facts.get("latestIntegrationErrorCode"));
        assertEquals("ReturnReceived", facts.get("latestBusinessEventType"));
    }

    @Test
    void updateOfAnotherOperationCannotExplainTheCurrentReturn() {
        var evidence = createStatusMismatchSnapshot("ON_LOAN", "RECEIVING", "FAILED", "ERROR", "RECEIVING");
        Map<String, Object> failed = new HashMap<>(evidence.sourceEvents().getLast());
        Map<String, Object> payload = new HashMap<>((Map<String, Object>) failed.get("payload"));
        payload.put("originalEventId", UUID.randomUUID().toString());
        failed.put("payload", payload);
        var facts = deterministicAnalysisService.facts(withEvidence(evidence, evidence.observations(),
            List.of(evidence.sourceEvents().getFirst(), failed), evidence.sourceAvailability()));
        assertNull(facts.get("latestIntegrationOutcome"));
        assertNull(facts.get("latestProjectedStatus"));
    }

    private EvidenceSnapshot withEvidence(EvidenceSnapshot original, List<Map<String, Object>> observations,
                                          List<Map<String, Object>> events, List<Map<String, Object>> availability) {
        return new EvidenceSnapshot(original.snapshotId(), original.caseId(), original.copyId(), original.schemaVersion(),
            original.inventoryReferenceTime(), original.dataCapturedAt(), original.caseFacts(), events, original.operatorNotes(),
            original.systemState(), observations, availability, original.symptoms());
    }

    @Test
    void incompleteSourcesDoNotProduceZeroAsProofOfAbsence() {
        var evidence = createStatusMismatchSnapshot("ON_LOAN", "RECEIVING", "FAILED", "ERROR", "RECEIVING");
        var availability = List.<Map<String, Object>>of(Map.of("source", "LOANS", "status", "PARTIAL"));
        var facts = deterministicAnalysisService.facts(withEvidence(evidence, evidence.observations(), List.of(), availability));
        assertNull(facts.get("loansCount"));
        assertNull(facts.get("returnsCount"));
        assertEquals("PARTIAL", facts.get("loansAvailabilityStatus"));
        var complete = deterministicAnalysisService.facts(withEvidence(evidence, evidence.observations(), List.of(), evidence.sourceAvailability()));
        assertEquals(0, complete.get("loansCount"));
        assertFalse(((List<?>) ((Map<?, ?>) complete.get("factProvenance")).get("loansCount")).isEmpty());
    }

    @Test
    void futureOrNotYetCapturedUpdatesAreNotUsed() {
        var evidence = createStatusMismatchSnapshot("ON_LOAN", "RECEIVING", "FAILED", "ERROR", "RECEIVING");
        Map<String, Object> future = new HashMap<>(evidence.sourceEvents().getLast());
        future.put("sourceEventId", UUID.randomUUID().toString());
        future.put("occurredAt", evidence.inventoryReferenceTime().plusSeconds(10));
        future.put("recordedAt", evidence.dataCapturedAt().plusSeconds(20));
        Map<String, Object> payload = new HashMap<>((Map<String, Object>) future.get("payload"));
        payload.put("outcome", "APPLIED");
        future.put("payload", payload);
        var facts = deterministicAnalysisService.facts(withEvidence(evidence, evidence.observations(),
            List.of(evidence.sourceEvents().getFirst(), evidence.sourceEvents().getLast(), future), evidence.sourceAvailability()));
        assertEquals("FAILED", facts.get("latestIntegrationOutcome"));
    }

    @Test
    void factsKeepTheReferencesOfTheOperationAndItsUpdate() {
        var evidence = createStatusMismatchSnapshot("ON_LOAN", "RECEIVING", "FAILED", "ERROR", "RECEIVING");
        var facts = deterministicAnalysisService.facts(evidence);
        Map<?, ?> provenance = (Map<?, ?>) facts.get("factProvenance");
        List<?> references = (List<?>) provenance.get("latestIntegrationOutcome");
        assertEquals(2, references.size());
        assertEquals(evidence.sourceEvents().getFirst().get("sourceEventId"), ((Map<?, ?>) references.getFirst()).get("recordId"));
        assertEquals(evidence.sourceEvents().getLast().get("sourceEventId"), ((Map<?, ?>) references.getLast()).get("recordId"));
    }

    @Test
    void missingRequiredFactTriggersInvestigationNotAFalseMatch() {
        var evidence = createStatusMismatchSnapshot("ON_LOAN", "RECEIVING", "FAILED", "ERROR", "RECEIVING");
        var result = deterministicAnalysisService.evaluate(withEvidence(evidence, List.of(), evidence.sourceEvents(), evidence.sourceAvailability()),
            List.of(candidateRule("unknown-observation", "RETURN_NOT_PROJECTED", "SUPPORTED")));
        assertFalse(result.fullyCovered());
        assertEquals(FallbackReason.INSUFFICIENT_EVIDENCE, result.fallbackReason());
        assertTrue(result.findings().isEmpty());
        assertEquals(0, result.trace().get("rulesEvaluated"));
    }

    @Test
    void aHypothesisCannotShortCircuitInvestigation() {
        var evidence = createStatusMismatchSnapshot("ON_LOAN", "RECEIVING", "FAILED", "ERROR", "RECEIVING");
        var result = deterministicAnalysisService.evaluate(evidence, List.of(candidateRule("hypothesis", "RETURN_NOT_PROJECTED", "HYPOTHESIS")));
        assertFalse(result.fullyCovered());
        assertEquals(FallbackReason.INSUFFICIENT_EVIDENCE, result.fallbackReason());
        assertEquals(EpistemicStatus.HYPOTHESIS, result.findings().getFirst().epistemicStatus());
    }

    @Test
    void equivalentRulesKeepAllContributorsAndEvidence() {
        var evidence = createStatusMismatchSnapshot("ON_LOAN", "RECEIVING", "FAILED", "ERROR", "RECEIVING");
        var result = deterministicAnalysisService.evaluate(evidence, List.of(
            candidateRule("equivalent-a", "RETURN_NOT_PROJECTED", "SUPPORTED"),
            candidateRule("equivalent-b", "RETURN_NOT_PROJECTED", "SUPPORTED")));
        assertTrue(result.fullyCovered());
        assertEquals(2, result.findings().size());
        assertEquals(1, result.findings().stream().map(Finding::causeCode).distinct().count());
        assertTrue(result.findings().stream().allMatch(finding -> !finding.evidenceRefs().isEmpty()
            && finding.evidenceRefs().stream().allMatch(reference -> evidence.snapshotId().equals(reference.evidenceSnapshotId()))));
        assertEquals(false, result.trace().get("competingCauses"));
    }

    @Test
    void competingCausesAreNotArbitrarilyAccepted() {
        var evidence = createStatusMismatchSnapshot("ON_LOAN", "RECEIVING", "FAILED", "ERROR", "RECEIVING");
        var result = deterministicAnalysisService.evaluate(evidence, List.of(
            candidateRule("competing-a", "RETURN_NOT_PROJECTED", "SUPPORTED"),
            candidateRule("competing-b", "OTHER_CAUSE", "SUPPORTED")));
        assertFalse(result.fullyCovered());
        assertEquals(FallbackReason.INSUFFICIENT_EVIDENCE, result.fallbackReason());
        assertEquals(true, result.trace().get("competingCauses"));
    }

    @Test
    void anInvalidModelIsATechnicalFailureNotAnUnknownCause() {
        var evidence = createStatusMismatchSnapshot("ON_LOAN", "RECEIVING", "FAILED", "ERROR", "RECEIVING");
        var valid = candidateRule("valid", "RETURN_NOT_PROJECTED", "SUPPORTED");
        var invalid = new RuleMetadata("invalid", 1, "Invalid", "AGENT", "rules/invalid.dmn", "urn:invalid", "invalid", "decision",
            "UNKNOWN", List.of("STATUS_MISMATCH"), List.of(), List.of(), "invalid", "<definitions>broken", true, null, null);
        assertThrows(RuntimeException.class, () -> deterministicAnalysisService.evaluate(evidence, List.of(valid, invalid)));
    }

    @Test
    void aDmnFailureIsPersistedWithoutQueuingAnLlmMission() {
        var evidence = createStatusMismatchSnapshot("ON_LOAN", "RECEIVING", "FAILED", "ERROR", "RECEIVING");
        UUID epoch = UUID.randomUUID();
        CaseRepository cases = mock(CaseRepository.class);
        AnalysisRepository analyses = mock(AnalysisRepository.class);
        RuleRepository rules = mock(RuleRepository.class);
        MissionRepository missions = mock(MissionRepository.class);
        OutboxRepository outbox = mock(OutboxRepository.class);
        DeterministicAnalysisService engine = mock(DeterministicAnalysisService.class);
        CaseDossier dossier = new CaseDossier(evidence.caseId(), evidence.copyId(), "demo", "demo", "demo", "NORD",
            evidence.inventoryReferenceTime(), CaseStatus.A_ANALYSER, evidence.symptoms(), evidence.systemState(), evidence.observations(),
            evidence.sourceAvailability(), evidence.operatorNotes(), evidence.sourceEvents(), List.of(), null, null, null);
        when(cases.findCaseDossier(epoch, evidence.caseId())).thenReturn(Optional.of(dossier));
        when(analyses.findActiveAnalysisId(epoch, evidence.caseId())).thenReturn(Optional.empty());
        when(rules.getActiveRuleSetVersion(epoch)).thenReturn(1);
        when(rules.getActiveRules(epoch)).thenReturn(List.of(candidateRule("failure", "RETURN_NOT_PROJECTED", "SUPPORTED")));
        when(engine.evaluate(any(), anyList())).thenThrow(new IllegalStateException("DMN_EVALUATION_FAILED"));
        var orchestration = new AnalysisOrchestrationService(cases, analyses, rules, missions, outbox, engine, mock(SseHubService.class));
        UUID analysisId = orchestration.startAnalysis(epoch, evidence.caseId());
        verify(analyses).failDeterministicAnalysis(epoch, analysisId, "DMN_EVALUATION_FAILED");
        verifyNoInteractions(missions, outbox);
    }

    private RuleMetadata candidateRule(String ruleId, String causeCode, String status) {
        String xml = """
            <definitions xmlns="https://www.omg.org/spec/DMN/20230324/MODEL/" id="definition_%s" name="%s" namespace="urn:test:%s">
              <inputData id="kind" name="symptomKind"><variable name="symptomKind" typeRef="string"/></inputData>
              <inputData id="outcome" name="latestIntegrationOutcome"><variable name="latestIntegrationOutcome" typeRef="string"/></inputData>
              <inputData id="quality" name="observationReliable"><variable name="observationReliable" typeRef="boolean"/></inputData>
              <decision id="decision" name="decision">
                <variable name="decision"/>
                <informationRequirement><requiredInput href="#kind"/></informationRequirement>
                <informationRequirement><requiredInput href="#outcome"/></informationRequirement>
                <informationRequirement><requiredInput href="#quality"/></informationRequirement>
                <decisionTable hitPolicy="COLLECT">
                  <input><inputExpression typeRef="string"><text>symptomKind</text></inputExpression></input>
                  <input><inputExpression typeRef="string"><text>latestIntegrationOutcome</text></inputExpression></input>
                  <input><inputExpression typeRef="boolean"><text>observationReliable</text></inputExpression></input>
                  <output name="causeCode" typeRef="string"/>
                  <output name="epistemicStatus" typeRef="string"/>
                  <rule id="row">
                    <inputEntry><text>"STATUS_MISMATCH"</text></inputEntry>
                    <inputEntry><text>"FAILED"</text></inputEntry>
                    <inputEntry><text>true</text></inputEntry>
                    <outputEntry><text>"%s"</text></outputEntry>
                    <outputEntry><text>"%s"</text></outputEntry>
                  </rule>
                </decisionTable>
              </decision>
            </definitions>
            """.formatted(ruleId, ruleId, ruleId, causeCode, status);
        return new RuleMetadata(ruleId, 1, ruleId, "AGENT", "rules/" + ruleId + ".dmn", "urn:test:" + ruleId, ruleId, "decision",
            causeCode, List.of("STATUS_MISMATCH"), List.of(), List.of(), "test", xml, true, null, null);
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
    void testExistingDatabaseRule8bfdRejectsItsMissingDiagnosticOutputs() {
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

        assertThrows(IllegalStateException.class, () -> deterministicAnalysisService.evaluate(snapshot, List.of(rule8bfd)));
    }
}
