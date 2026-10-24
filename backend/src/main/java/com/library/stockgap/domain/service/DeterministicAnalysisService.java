package com.library.stockgap.domain.service;

import com.library.stockgap.domain.model.*;
import org.kie.dmn.api.core.DMNRuntime;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeterministicAnalysisService {
    private final DmnEngineService dmnEngineService;

    public DeterministicAnalysisService(DmnEngineService dmnEngineService) {
        this.dmnEngineService = dmnEngineService;
    }

    public record DeterministicEvaluationResult(
        boolean fullyCovered,
        FallbackReason fallbackReason,
        List<Finding> findings,
        Map<String, Object> trace
    ) {}

    public DeterministicEvaluationResult evaluate(EvidenceSnapshot snapshot, List<RuleMetadata> activeRules) {
        if (activeRules.isEmpty()) {
            return new DeterministicEvaluationResult(
                false, FallbackReason.NO_RULE_MATCH, Collections.emptyList(), Map.of("rulesEvaluated", 0)
            );
        }

        DMNRuntime runtime;
        try {
            runtime = dmnEngineService.createRuntime(activeRules);
        } catch (Exception e) {
            return new DeterministicEvaluationResult(
                false, FallbackReason.NO_RULE_MATCH, Collections.emptyList(), Map.of("error", e.getMessage())
            );
        }

        // Build facts
        Map<String, Object> facts = buildFacts(snapshot);
        List<UUID> caseSymptomIds = new ArrayList<>();
        Set<String> caseSymptomKinds = new HashSet<>();

        for (Map<String, Object> sym : snapshot.symptoms()) {
            Object sId = sym.get("symptomId");
            UUID id = sId instanceof UUID ? (UUID) sId : UUID.fromString(sId.toString());
            caseSymptomIds.add(id);
            caseSymptomKinds.add((String) sym.get("kind"));
        }

        List<Finding> allFindings = new ArrayList<>();
        Set<String> coveredSymptomKinds = new HashSet<>();
        List<Map<String, Object>> ruleTraces = new ArrayList<>();

        for (RuleMetadata rule : activeRules) {
            List<Finding> ruleFindings = dmnEngineService.evaluateRule(
                runtime, rule, facts, snapshot.copyId(), caseSymptomIds
            );
            if (!ruleFindings.isEmpty()) {
                allFindings.addAll(ruleFindings);
                coveredSymptomKinds.addAll(rule.coveredSymptomKinds());
                ruleTraces.add(Map.of("ruleId", rule.ruleId(), "match", true, "findingsCount", ruleFindings.size()));
            } else {
                ruleTraces.add(Map.of("ruleId", rule.ruleId(), "match", false));
            }
        }

        Map<String, Object> trace = Map.of(
            "rulesEvaluated", activeRules.size(),
            "ruleTraces", ruleTraces,
            "caseSymptomKinds", caseSymptomKinds,
            "coveredSymptomKinds", coveredSymptomKinds
        );

        if (allFindings.isEmpty()) {
            return new DeterministicEvaluationResult(false, FallbackReason.NO_RULE_MATCH, Collections.emptyList(), trace);
        }

        // Check if all symptoms of the case are covered
        boolean allCovered = coveredSymptomKinds.containsAll(caseSymptomKinds);
        if (allCovered) {
            return new DeterministicEvaluationResult(true, null, allFindings, trace);
        } else {
            return new DeterministicEvaluationResult(false, FallbackReason.PARTIAL_COVERAGE, allFindings, trace);
        }
    }

    private Map<String, Object> buildFacts(EvidenceSnapshot snapshot) {
        Map<String, Object> facts = new HashMap<>();

        // 1. System state
        Map<String, Object> sysState = snapshot.systemState();
        facts.put("expectedSiteId", sysState.get("siteId"));
        facts.put("expectedLocationId", sysState.get("locationId") != null ? sysState.get("locationId").toString() : null);
        facts.put("expectedStatus", sysState.get("status"));

        // 2. Observations
        List<Map<String, Object>> obsList = snapshot.observations();
        if (obsList != null && !obsList.isEmpty()) {
            Map<String, Object> obs = obsList.get(0);
            facts.put("observedSiteId", obs.get("siteId"));
            facts.put("observedLocationId", obs.get("locationId") != null ? obs.get("locationId").toString() : null);
            String quality = (String) obs.get("quality");
            facts.put("observationReliable", "VALID".equalsIgnoreCase(quality));
            facts.put("observationQuality", quality);
        } else {
            facts.put("observedSiteId", null);
            facts.put("observedLocationId", null);
            facts.put("observationReliable", false);
            facts.put("observationQuality", null);
        }

        // 3. Loans, returns, transfers, notes summary
        facts.put("loansCount", countEventsOfType(snapshot, "LoanOpened"));
        facts.put("loansClosedCount", countEventsOfType(snapshot, "LoanClosed"));
        facts.put("transfersDispatchedCount", countEventsOfType(snapshot, "TransferDispatched"));
        facts.put("transfersReceivedCount", countEventsOfType(snapshot, "TransferReceived"));
        facts.put("operatorNotesCount", snapshot.operatorNotes() != null ? snapshot.operatorNotes().size() : 0);

        return facts;
    }

    private int countEventsOfType(EvidenceSnapshot snapshot, String eventType) {
        if (snapshot.sourceEvents() == null) return 0;
        int count = 0;
        for (Map<String, Object> ev : snapshot.sourceEvents()) {
            if (eventType.equals(ev.get("eventType"))) {
                count++;
            }
        }
        return count;
    }
}
