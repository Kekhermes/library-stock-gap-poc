package com.library.stockgap;

import com.library.stockgap.domain.model.*;
import com.library.stockgap.domain.service.DeterministicAnalysisService;
import com.library.stockgap.domain.service.DmnEngineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class DeterministicAnalysisServiceTest {

    private DeterministicAnalysisService service;
    private RuleMetadata r001;

    @BeforeEach
    void setUp() throws Exception {
        DmnEngineService dmnEngine = new DmnEngineService();
        service = new DeterministicAnalysisService(dmnEngine);

        ClassPathResource res = new ClassPathResource("rules/R001-v1.dmn");
        String xml;
        try (InputStream is = res.getInputStream()) {
            xml = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }

        r001 = new RuleMetadata(
            "R001", 1, "Localisation différente constatée", "BOOTSTRAP",
            "rules/R001-v1.dmn", "urn:library-stock-gap:rules:R001:1", "R001-v1",
            "locationDifference", "LOCATION_DIFFERENCE", List.of("LOCATION_MISMATCH"),
            List.of("Vérifier le rangement"), List.of(), "sha", xml, true, null, null
        );
    }

    @Test
    void testNominalLocationMismatchFullyCovered() {
        UUID copyId = UUID.randomUUID();
        UUID caseId = UUID.randomUUID();
        UUID symptomId = UUID.randomUUID();

        Map<String, Object> sysState = Map.of(
            "status", "ON_SHELF",
            "siteId", "CENTRE",
            "locationId", "00000000-0000-4000-8000-000000000001"
        );
        List<Map<String, Object>> observations = List.of(
            Map.of(
                "siteId", "CENTRE",
                "locationId", "00000000-0000-4000-8000-000000000002",
                "quality", "VALID"
            )
        );
        List<Map<String, Object>> symptoms = List.of(
            Map.of("symptomId", symptomId.toString(), "kind", "LOCATION_MISMATCH")
        );

        EvidenceSnapshot snapshot = new EvidenceSnapshot(
            UUID.randomUUID(), caseId, copyId, "1.0.0",
            Instant.now(), Instant.now(), Map.of(), List.of(), List.of(),
            sysState, observations, List.of(), symptoms
        );

        var result = service.evaluate(snapshot, List.of(r001));

        assertTrue(result.fullyCovered(), "Le cas nominal doit être entièrement couvert par R001");
        assertNull(result.fallbackReason(), "Aucun fallback attendu pour ce cas");
        assertEquals(1, result.findings().size());
        assertEquals("LOCATION_DIFFERENCE", result.findings().get(0).causeCode());
    }

    @Test
    void testPartialCoverageWhenStatusMismatchAlsoPresent() {
        UUID copyId = UUID.randomUUID();
        UUID caseId = UUID.randomUUID();
        UUID sym1 = UUID.randomUUID();
        UUID sym2 = UUID.randomUUID();

        Map<String, Object> sysState = Map.of(
            "status", "ON_SHELF",
            "siteId", "CENTRE",
            "locationId", "00000000-0000-4000-8000-000000000001"
        );
        List<Map<String, Object>> observations = List.of(
            Map.of(
                "siteId", "CENTRE",
                "locationId", "00000000-0000-4000-8000-000000000002",
                "quality", "VALID"
            )
        );
        // Two symptoms: location mismatch AND status mismatch
        List<Map<String, Object>> symptoms = List.of(
            Map.of("symptomId", sym1.toString(), "kind", "LOCATION_MISMATCH"),
            Map.of("symptomId", sym2.toString(), "kind", "STATUS_MISMATCH")
        );

        EvidenceSnapshot snapshot = new EvidenceSnapshot(
            UUID.randomUUID(), caseId, copyId, "1.0.0",
            Instant.now(), Instant.now(), Map.of(), List.of(), List.of(),
            sysState, observations, List.of(), symptoms
        );

        var result = service.evaluate(snapshot, List.of(r001));

        assertFalse(result.fullyCovered(), "La couverture doit être partielle");
        assertEquals(FallbackReason.PARTIAL_COVERAGE, result.fallbackReason());
        assertEquals(1, result.findings().size(), "R001 a quand même produit un finding partiel");
    }
}
