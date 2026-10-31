package com.library.stockgap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.stockgap.domain.model.EvidenceSnapshot;
import com.library.stockgap.domain.model.RuleMetadata;
import com.library.stockgap.domain.service.DeterministicAnalysisService;
import com.library.stockgap.domain.service.DmnEngineService;
import com.library.stockgap.domain.service.RuleReplayService;
import com.library.stockgap.domain.service.RuleValidationPolicy;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RuleReplayServiceTest {
    private static final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
    private final DmnEngineService dmn = new DmnEngineService();
    private final RuleReplayService replay = new RuleReplayService(mapper, new DefaultResourceLoader(),
        new DeterministicAnalysisService(dmn), dmn, new RuleValidationPolicy());

    @Test
    void causalReturnRulePassesIndependentPositiveNegativeAndReplayCases() throws Exception {
        RuleMetadata candidate = returnRule(false, false);
        var result = replay.verify(candidate, List.of(bootstrap()), List.of(bootstrap(), candidate), origin());
        assertTrue(result.passed(), result.conflicts().toString());
        assertEquals(4, result.positiveTested());
        assertEquals(30, result.negativeTested());
        assertEquals(33, result.replayed());
        assertEquals(0, result.regressions());
        assertEquals(34, result.results().size());
    }

    @Test
    void compilableRuleThatAlsoCapturesRepairsIsBlocked() throws Exception {
        RuleMetadata candidate = returnRule(true, false);
        assertTrue(dmn.validateDmnXml(candidate.dmnXml()));
        var result = replay.verify(candidate, List.of(bootstrap()), List.of(bootstrap(), candidate), origin());
        assertFalse(result.passed());
        assertTrue(result.negativePassed() < result.negativeTested());
        assertTrue(result.results().stream().anyMatch(row -> Boolean.TRUE.equals(row.get("actualMatch")) && Boolean.FALSE.equals(row.get("expectedMatch"))));
    }

    @Test
    void ruleMemorizingTheOriginSiteIsBlockedByGeneralizationCases() throws Exception {
        RuleMetadata candidate = returnRule(false, true);
        var result = replay.verify(candidate, List.of(bootstrap()), List.of(bootstrap(), candidate), origin());
        assertFalse(result.passed());
        assertTrue(result.positivePassed() < result.positiveTested());
        assertEquals(true, result.results().getFirst().get("passed"));
    }

    @Test
    void ruleThatIgnoresTheFailedUpdateIsBlockedByPendingUpdates() throws Exception {
        RuleMetadata candidate = returnRule(false, false, false);
        var result = replay.verify(candidate, List.of(bootstrap()), List.of(bootstrap(), candidate), origin());
        assertFalse(result.passed());
        assertTrue(result.negativePassed() < result.negativeTested());
        assertTrue(result.results().stream().anyMatch(row -> "QUEUED_UPDATE".equals(row.get("variant")) && Boolean.FALSE.equals(row.get("passed"))));
    }

    static EvidenceSnapshot origin() throws Exception {
        try (var input = RuleReplayServiceTest.class.getResourceAsStream("/rules/validation-corpus.json")) {
            var cases = mapper.readTree(input).path("cases");
            for (var item : cases) {
                if (item.path("scope").asText().equals("ReturnReceived") && item.path("expectedMatch").asBoolean()) {
                    return mapper.treeToValue(item.path("evidence"), EvidenceSnapshot.class);
                }
            }
        }
        throw new IllegalStateException("Positif d'origine absent");
    }

    static RuleMetadata bootstrap() throws Exception {
        String xml;
        try (var input = RuleReplayServiceTest.class.getResourceAsStream("/rules/R001-v1.dmn")) {
            xml = new String(input.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
        }
        return new RuleMetadata("R001", 1, "R001", "BOOTSTRAP", "rules/R001-v1.dmn", "urn:library-stock-gap:rules:R001:1", "R001-v1",
            "locationDifference", "LOCATION_DIFFERENCE", List.of("LOCATION_MISMATCH"), List.of(), List.of(), "test", xml, true, null, null);
    }

    static RuleMetadata returnRule(boolean broad, boolean oneSite) throws Exception {
        return returnRule(broad, oneSite, true);
    }

    private static RuleMetadata returnRule(boolean broad, boolean oneSite, boolean requireFailure) throws Exception {
        String site = origin().systemState().get("siteId").toString();
        String xml = """
            <definitions xmlns="https://www.omg.org/spec/DMN/20230324/MODEL/" id="return_definition" name="ReturnRule" namespace="urn:test:return">
              <inputData id="operation" name="latestBusinessEventType"><variable name="latestBusinessEventType" typeRef="string"/></inputData>
              <inputData id="outcome" name="latestIntegrationOutcome"><variable name="latestIntegrationOutcome" typeRef="string"/></inputData>
              <inputData id="status" name="systemStatus"><variable name="systemStatus" typeRef="string"/></inputData>
              <inputData id="quality" name="observationReliable"><variable name="observationReliable" typeRef="boolean"/></inputData>
              <inputData id="source" name="integrationsAvailabilityStatus"><variable name="integrationsAvailabilityStatus" typeRef="string"/></inputData>
              <inputData id="site" name="expectedSiteId"><variable name="expectedSiteId" typeRef="string"/></inputData>
              <decision id="decision" name="decision"><variable name="decision"/>
                <informationRequirement><requiredInput href="#operation"/></informationRequirement>
                <informationRequirement><requiredInput href="#outcome"/></informationRequirement>
                <informationRequirement><requiredInput href="#status"/></informationRequirement>
                <informationRequirement><requiredInput href="#quality"/></informationRequirement>
                <informationRequirement><requiredInput href="#source"/></informationRequirement>
                <informationRequirement><requiredInput href="#site"/></informationRequirement>
                <decisionTable hitPolicy="COLLECT">
                  <input><inputExpression typeRef="string"><text>latestBusinessEventType</text></inputExpression></input>
                  <input><inputExpression typeRef="string"><text>latestIntegrationOutcome</text></inputExpression></input>
                  <input><inputExpression typeRef="string"><text>systemStatus</text></inputExpression></input>
                  <input><inputExpression typeRef="boolean"><text>observationReliable</text></inputExpression></input>
                  <input><inputExpression typeRef="string"><text>integrationsAvailabilityStatus</text></inputExpression></input>
                  <input><inputExpression typeRef="string"><text>expectedSiteId</text></inputExpression></input>
                  <output name="causeCode" typeRef="string"/><output name="epistemicStatus" typeRef="string"/>
                  <rule id="return_row">
                    <inputEntry><text>%s</text></inputEntry><inputEntry><text>%s</text></inputEntry>
                    <inputEntry><text>%s</text></inputEntry><inputEntry><text>true</text></inputEntry>
                    <inputEntry><text>"COMPLETE"</text></inputEntry><inputEntry><text>%s</text></inputEntry>
                    <outputEntry><text>"RETURN_NOT_PROJECTED"</text></outputEntry><outputEntry><text>"SUPPORTED"</text></outputEntry>
                  </rule>
                </decisionTable>
              </decision>
            </definitions>
            """.formatted(broad ? "-" : "\"ReturnReceived\"", requireFailure ? "\"FAILED\"" : "-", broad ? "-" : "\"ON_LOAN\"", oneSite ? "\"" + site + "\"" : "-");
        return new RuleMetadata(UUID.randomUUID().toString(), 1, "ReturnRule", "AGENT", "rules/return.dmn", "urn:test:return", "ReturnRule", "decision",
            "RETURN_NOT_PROJECTED", List.of("STATUS_MISMATCH"), List.of(), List.of(), com.library.stockgap.infrastructure.persistence.AnalysisRepository.sha256Hex(xml), xml, false, null, null);
    }
}