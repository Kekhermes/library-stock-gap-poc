package com.library.stockgap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.stockgap.domain.model.EvidenceSnapshot;
import com.library.stockgap.domain.model.CaseDossier;
import com.library.stockgap.domain.model.CaseStatus;
import com.library.stockgap.domain.model.FallbackReason;
import com.library.stockgap.domain.model.RuleMetadata;
import com.library.stockgap.domain.service.DemoVerificationService;
import com.library.stockgap.domain.service.DeterministicAnalysisService;
import com.library.stockgap.domain.service.DmnEngineService;
import com.library.stockgap.infrastructure.persistence.AnalysisRepository;
import com.library.stockgap.infrastructure.persistence.ActiveDatasetRepository;
import com.library.stockgap.infrastructure.persistence.CaseRepository;
import com.library.stockgap.infrastructure.persistence.RuleRepository;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DemoDatasetTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private final DeterministicAnalysisService analysis = new DeterministicAnalysisService(new DmnEngineService());
    private JsonNode dataset;
    private JsonNode manifest;
    private String runtimeJson;
    private String dmnXml;

    @BeforeEach
    void loadResources() throws Exception {
        runtimeJson = resource("/seed/demo-runtime.json");
        dataset = mapper.readTree(runtimeJson);
        manifest = mapper.readTree(resource("/seed/demo-manifest.json"));
        dmnXml = resource("/rules/R001-v1.dmn");
    }

    @Test
    void packagedDemoHasTenVirginCasesAndNoPrivateLabels() {
        assertEquals(10, dataset.path("cases").size());
        assertEquals(10, dataset.path("copies").size());
        assertEquals(10, dataset.path("titles").size());
        assertEquals(48, dataset.path("sourceEvents").size());
        assertEquals("2.0.0", manifest.path("generatorVersion").asText());
        assertEquals(AnalysisRepository.sha256Hex(runtimeJson), manifest.path("sha256").path("seed/generated/demo/runtime.json").asText());
        assertEquals(1, dataset.path("initialRuleSet").path("rules").size());
        assertEquals("R001", dataset.path("initialRuleSet").path("rules").get(0).path("ruleId").asText());
        for (JsonNode dossier : dataset.path("cases")) {
            assertEquals("A_ANALYSER", dossier.path("status").asText());
            assertEquals(1, dossier.path("symptoms").size());
        }
        assertFalse(runtimeJson.contains("injectedTruth"));
        assertFalse(runtimeJson.contains("admissible"));
        assertFalse(runtimeJson.contains("\"family\""));
        assertFalse(manifest.has("familyCounts"));
        assertFalse(manifest.has("variantCounts"));
        assertEquals(1, manifest.path("sha256").size());
    }

    @Test
    void bootstrapMatchesOnlyTheTwoLocationDossiers() {
        RuleMetadata bootstrap = bootstrap();
        int covered = 0;
        int uncovered = 0;
        for (JsonNode dossier : dataset.path("cases")) {
            var result = analysis.evaluate(snapshot(dossier), List.of(bootstrap));
            if ("LOCATION_MISMATCH".equals(dossier.path("symptoms").get(0).path("kind").asText())) {
                assertTrue(result.fullyCovered());
                assertEquals(1, result.findings().size());
                assertEquals("R001", result.findings().getFirst().ruleId());
                covered++;
            } else {
                assertFalse(result.fullyCovered());
                assertTrue(result.findings().isEmpty());
                assertEquals(FallbackReason.NO_RULE_MATCH, result.fallbackReason());
                uncovered++;
            }
        }
        assertEquals(2, covered);
        assertEquals(8, uncovered);
    }

    @Test
    void factsDoNotContainAPredeterminedDiagnostic() {
        for (JsonNode dossier : dataset.path("cases")) {
            var facts = analysis.facts(snapshot(dossier));
            assertFalse(facts.containsKey("diagnosticConclusion"));
            assertFalse(facts.containsKey("acceptedDiagnosticConclusion"));
        }
    }

    @Test
    void preparationCheckIsReadOnlyAndFindsTheExpectedCoverage() throws Exception {
        JdbcTemplate jdbc = mock(JdbcTemplate.class);
        Map<String, Object> report = verification(jdbc, 0).verify();
        assertEquals("READY", report.get("status"));
        assertEquals(2, report.get("deterministicCases"));
        assertEquals(8, report.get("uncoveredCases"));
        assertEquals(0, report.get("llmCalls"));
        assertTrue(mockingDetails(jdbc).getInvocations().stream()
            .allMatch(invocation -> invocation.getMethod().getName().startsWith("query")));
    }

    @Test
    void preparationCheckRejectsAnEpochWithExistingAnalyses() throws Exception {
        Map<String, Object> report = verification(mock(JdbcTemplate.class), 1).verify();
        assertEquals("RESET_REQUIRED", report.get("status"));
        assertEquals(false, ((Map<?, ?>) report.get("checks")).get("noAnalyses"));
    }

    private RuleMetadata bootstrap() {
        return new RuleMetadata("R001", 1, "Localisation différente constatée", "BOOTSTRAP", "rules/R001-v1.dmn",
            "urn:library-stock-gap:rules:R001:1", "R001-v1", "locationDifference", "LOCATION_DIFFERENCE",
            List.of("LOCATION_MISMATCH"), List.of(), List.of(), AnalysisRepository.sha256Hex(dmnXml), dmnXml, true, null, null);
    }

    private DemoVerificationService verification(JdbcTemplate jdbc, int existingAnalyses) {
        UUID epoch = UUID.randomUUID();
        ActiveDatasetRepository datasets = mock(ActiveDatasetRepository.class);
        CaseRepository cases = mock(CaseRepository.class);
        RuleRepository rules = mock(RuleRepository.class);
        when(datasets.requireActiveEpoch()).thenReturn(epoch);
        when(jdbc.queryForMap(anyString(), eq(epoch))).thenReturn(Map.of(
            "runtime_sha256", AnalysisRepository.sha256Hex(runtimeJson), "generator_version", "2.0.0"));
        when(jdbc.queryForObject(anyString(), eq(Integer.class), eq(epoch))).thenReturn(0);
        when(jdbc.queryForObject("SELECT count(*) FROM runtime.copy WHERE dataset_epoch = ?", Integer.class, epoch)).thenReturn(10);
        when(jdbc.queryForObject("SELECT count(*) FROM runtime.analysis_run WHERE dataset_epoch = ?", Integer.class, epoch)).thenReturn(existingAnalyses);
        when(cases.countCases(epoch, null, null, null)).thenReturn(10);
        when(cases.countCases(epoch, null, "A_ANALYSER", null)).thenReturn(10);
        when(rules.getActiveRules(epoch)).thenReturn(List.of(bootstrap()));
        when(rules.getActiveRuleSetVersion(epoch)).thenReturn(1);
        List<Map<String, Object>> items = new ArrayList<>();
        for (JsonNode raw : dataset.path("cases")) {
            EvidenceSnapshot evidence = snapshot(raw);
            items.add(Map.of("caseId", evidence.caseId()));
            CaseDossier dossier = new CaseDossier(evidence.caseId(), evidence.copyId(), "demo", "demo", "demo",
                evidence.systemState().get("siteId").toString(), evidence.inventoryReferenceTime(), CaseStatus.A_ANALYSER,
                evidence.symptoms(), evidence.systemState(), evidence.observations(), evidence.sourceAvailability(), evidence.operatorNotes(),
                evidence.sourceEvents(), List.of(), null, null, null);
            when(cases.findCaseDossier(epoch, evidence.caseId())).thenReturn(java.util.Optional.of(dossier));
        }
        when(cases.findCases(epoch, null, null, null, 0, 10)).thenReturn(items);
        return new DemoVerificationService(datasets, cases, rules, jdbc, mapper, new DefaultResourceLoader(), analysis);
    }

    private EvidenceSnapshot snapshot(JsonNode dossier) {
        String copyId = dossier.path("copyId").asText();
        return new EvidenceSnapshot(
            UUID.randomUUID(), UUID.fromString(dossier.path("caseId").asText()), UUID.fromString(copyId), "1.0.0",
            Instant.parse(dossier.path("inventoryReferenceTime").asText()), Instant.parse(dataset.path("dataset").path("capturedAt").asText()),
            Map.of(), rows("sourceEvents", copyId), rows("operatorNotes", copyId), rows("systemStates", copyId).getFirst(),
            rows("observations", copyId), rows("sourceAvailability", copyId),
            mapper.convertValue(dossier.path("symptoms"), new TypeReference<>() {})
        );
    }

    private List<Map<String, Object>> rows(String collection, String copyId) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (JsonNode item : dataset.path(collection)) {
            if (!copyId.equals(item.path("copyId").asText())) continue;
            Map<String, Object> row = mapper.convertValue(item, new TypeReference<>() {});
            if ("observations".equals(collection)) {
                for (JsonNode location : dataset.path("locations")) {
                    if (location.path("locationId").asText().equals(item.path("locationId").asText())) {
                        row.put("siteId", location.path("siteId").asText());
                    }
                }
            }
            result.add(row);
        }
        if ("sourceEvents".equals(collection)) result.sort(Comparator.comparing(row -> row.get("occurredAt").toString()));
        return result;
    }

    private String resource(String name) throws Exception {
        try (InputStream input = getClass().getResourceAsStream(name)) {
            assertNotNull(input, name);
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}