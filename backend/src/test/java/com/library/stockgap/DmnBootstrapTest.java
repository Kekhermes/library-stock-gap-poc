package com.library.stockgap;

import com.library.stockgap.domain.model.RuleMetadata;
import com.library.stockgap.domain.service.DmnEngineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DmnBootstrapTest {

    private DmnEngineService dmnEngineService;
    private DMNRuntime runtime;
    private DMNModel model;

    @BeforeEach
    void setUp() throws Exception {
        dmnEngineService = new DmnEngineService();
        ClassPathResource res = new ClassPathResource("rules/R001-v1.dmn");
        String xml;
        try (InputStream is = res.getInputStream()) {
            xml = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }

        RuleMetadata r1 = new RuleMetadata(
            "R001", 1, "Localisation différente constatée", "BOOTSTRAP",
            "rules/R001-v1.dmn", "urn:library-stock-gap:rules:R001:1", "R001-v1",
            "locationDifference", "LOCATION_DIFFERENCE", List.of("LOCATION_MISMATCH"),
            List.of(), List.of(), "sha", xml, true, null, null
        );

        runtime = dmnEngineService.createRuntime(List.of(r1));
        model = runtime.getModel("urn:library-stock-gap:rules:R001:1", "R001-v1");
        assertNotNull(model, "Le modèle R001-v1 doit être présent");
        assertFalse(model.hasErrors(), "Le modèle ne doit comporter aucune erreur");
    }

    private boolean evaluateR001(String expectedSite, String observedSite,
                                 String expectedLoc, String observedLoc,
                                 Boolean reliable) {
        DMNContext ctx = runtime.newContext();
        ctx.set("expectedSiteId", expectedSite);
        ctx.set("observedSiteId", observedSite);
        ctx.set("expectedLocationId", expectedLoc);
        ctx.set("observedLocationId", observedLoc);
        ctx.set("observationReliable", reliable);

        DMNResult result = runtime.evaluateAll(model, ctx);
        assertFalse(result.hasErrors(), "L'évaluation ne doit pas lever d'erreur");

        Object val = result.getDecisionResultByName("locationDifference").getResult();
        assertTrue(val instanceof List<?>, "Le résultat doit être une liste COLLECT");
        List<?> list = (List<?>) val;

        if (list.isEmpty()) {
            return false;
        }

        assertEquals(1, list.size());
        Map<?, ?> map = (Map<?, ?>) list.get(0);
        assertEquals("LOCATION_DIFFERENCE", map.get("causeCode"));
        assertEquals("SUPPORTED", map.get("epistemicStatus"));
        return true;
    }

    @Test
    void testAcceptanceCriteria() {
        // 1. Nominal case: different location, same site, reliable observation -> MATCH
        assertTrue(evaluateR001("CENTRE", "CENTRE", "locA", "locB", true));

        // 2. Same location -> NO MATCH
        assertFalse(evaluateR001("CENTRE", "CENTRE", "locA", "locA", true));

        // 3. Different site -> NO MATCH
        assertFalse(evaluateR001("CENTRE", "NORD", "locA", "locB", true));

        // 4. Unreliable observation -> NO MATCH
        assertFalse(evaluateR001("CENTRE", "CENTRE", "locA", "locB", false));

        // 5. Unknown observation quality (null) -> NO MATCH
        assertFalse(evaluateR001("CENTRE", "CENTRE", "locA", "locB", null));

        // 6. Missing observed site -> NO MATCH
        assertFalse(evaluateR001("CENTRE", null, "locA", "locB", true));

        // 7. Missing observed location -> NO MATCH
        assertFalse(evaluateR001("CENTRE", "CENTRE", "locA", null, true));

        // 8. Missing expected location -> NO MATCH
        assertFalse(evaluateR001("CENTRE", "CENTRE", null, "locB", true));
    }
}
