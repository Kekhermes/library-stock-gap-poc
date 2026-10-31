package com.library.stockgap.domain.service;

import com.library.stockgap.domain.model.*;
import org.kie.dmn.api.core.DMNRuntime;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class DeterministicAnalysisService {
    private final DmnEngineService dmnEngineService;
    public DeterministicAnalysisService(DmnEngineService dmnEngineService) { this.dmnEngineService = dmnEngineService; }
    public record DeterministicEvaluationResult(boolean fullyCovered, FallbackReason fallbackReason, List<Finding> findings, Map<String,Object> trace) {}
    public DeterministicEvaluationResult evaluate(EvidenceSnapshot snapshot, List<RuleMetadata> rules) {
        if (rules.isEmpty()) return new DeterministicEvaluationResult(false, FallbackReason.NO_RULE_MATCH, List.of(), Map.of("rulesEvaluated",0));
        DMNRuntime runtime;
        try { runtime=dmnEngineService.createRuntime(rules); } catch(Exception e) { return new DeterministicEvaluationResult(false,FallbackReason.NO_RULE_MATCH,List.of(),Map.of("error",e.getMessage())); }
        List<UUID> ids=new ArrayList<>(); Set<String> kinds=new HashSet<>();
        for(var symptom:snapshot.symptoms()){ ids.add(UUID.fromString(symptom.get("symptomId").toString())); kinds.add(symptom.get("kind").toString()); }
        Map<String,Object> facts=facts(snapshot); List<Finding> findings=new ArrayList<>(); Set<String> covered=new HashSet<>(); List<Map<String,Object>> traces=new ArrayList<>();
        int evaluatedCount = 0;
        for (var rule : rules) {
            if (Collections.disjoint(rule.coveredSymptomKinds(), kinds)) {
                traces.add(Map.of("ruleId", rule.ruleId(), "match", false, "evaluated", false, "reason", "SYMPTOM_OUT_OF_SCOPE"));
                continue;
            }
            evaluatedCount++;
            var result = dmnEngineService.evaluateRule(runtime, rule, facts, snapshot.copyId(), ids);
            if (result.isEmpty()) traces.add(Map.of("ruleId", rule.ruleId(), "match", false, "evaluated", true));
            else {
                findings.addAll(result);
                covered.addAll(rule.coveredSymptomKinds());
                traces.add(Map.of("ruleId", rule.ruleId(), "match", true, "evaluated", true, "findingsCount", result.size()));
            }
        }
        var trace=Map.<String,Object>of("rulesEvaluated",evaluatedCount,"rulesConsidered",rules.size(),"ruleTraces",traces,"caseSymptomKinds",kinds,"coveredSymptomKinds",covered);
        if(findings.isEmpty()) return new DeterministicEvaluationResult(false,FallbackReason.NO_RULE_MATCH,List.of(),trace);
        return new DeterministicEvaluationResult(covered.containsAll(kinds), covered.containsAll(kinds)?null:FallbackReason.PARTIAL_COVERAGE, findings, trace);
    }
    public Map<String,Object> facts(EvidenceSnapshot s){
        Map<String,Object> f = new HashMap<>();

        // 1. Symptom facts (symptomKind, expectedValue, observedValue)
        if (!s.symptoms().isEmpty()) {
            var primary = s.symptoms().getFirst();
            Object kind = primary.get("kind");
            Object expVal = primary.get("expectedValue");
            Object obsVal = primary.get("observedValue");
            f.put("symptomKind", kind);
            f.put("expectedValue", expVal);
            f.put("observedValue", obsVal);
            if (expVal != null) f.put("expectedStatus", expVal.toString());
            if (obsVal != null) f.put("observedStatus", obsVal.toString());
        }
        f.put("symptomsCount", s.symptoms().size());

        // 2. System state facts
        var sys = s.systemState();
        if (sys != null) {
            f.put("expectedSiteId", sys.get("siteId"));
            f.put("expectedLocationId", sys.get("locationId") == null ? null : sys.get("locationId").toString());
            if (sys.get("status") != null) {
                f.put("expectedStatus", sys.get("status"));
                f.put("systemStatus", sys.get("status"));
            }
        }

        // 3. Observation facts
        var obs = s.observations();
        if (obs.isEmpty()) {
            f.put("observedSiteId", null);
            f.put("observedLocationId", null);
            f.put("observationReliable", false);
            f.put("observationQuality", null);
        } else {
            var o = obs.getFirst();
            f.put("observedSiteId", o.get("siteId"));
            f.put("observedLocationId", o.get("locationId") == null ? null : o.get("locationId").toString());
            String quality = (String) o.get("quality");
            f.put("observationQuality", quality);
            f.put("observationReliable", "VALID".equals(quality));
        }
        f.put("observationsCount", obs.size());

        // 4. Source events analysis (latest IntegrationProcessed and latest business event)
        Map<String, Object> latestIntegration = null;
        Map<String, Object> latestBusinessEvent = null;
        for (var ev : s.sourceEvents()) {
            String type = (String) ev.get("eventType");
            if ("IntegrationProcessed".equals(type)) {
                latestIntegration = ev;
            } else if (type != null) {
                latestBusinessEvent = ev;
            }
        }

        if (latestIntegration != null) {
            @SuppressWarnings("unchecked")
            var payload = (Map<String, Object>) latestIntegration.get("payload");
            if (payload != null) {
                Object outcome = payload.get("outcome");
                Object errCode = payload.get("errorCode");
                Object projStatus = payload.get("projectedStatus");
                f.put("latestIntegrationOutcome", outcome);
                f.put("integrationOutcome", outcome);
                f.put("latestIntegrationErrorCode", errCode);
                f.put("integrationErrorCode", errCode);
                f.put("errorCode", errCode);
                f.put("latestErrorCode", errCode);
                f.put("latestProjectedStatus", projStatus);
                f.put("projectedStatus", projStatus);
                f.put("integrationEventType", "IntegrationProcessed");

                if ("IN_REPAIR".equals(projStatus)) {
                    f.put("repairOutcome", outcome);
                    f.put("repairProjectedStatus", projStatus);
                }
            }
        }

        if (latestBusinessEvent != null) {
            f.put("latestBusinessEventType", latestBusinessEvent.get("eventType"));
            f.put("businessEventType", latestBusinessEvent.get("eventType"));
            if ("RepairStarted".equals(latestBusinessEvent.get("eventType")) && latestIntegration != null) {
                if (latestIntegration.get("payload") instanceof Map<?, ?> pl) {
                    f.put("repairOutcome", pl.get("outcome"));
                    f.put("repairProjectedStatus", pl.get("projectedStatus"));
                }
            }
        }

        // 5. Source availability facts
        for (var sa : s.sourceAvailability()) {
            String src = (String) sa.get("source");
            Object status = sa.get("status");
            if (src != null && status != null) {
                f.put(src.toLowerCase(Locale.ROOT) + "AvailabilityStatus", status);
                if ("INTEGRATIONS".equalsIgnoreCase(src)) f.put("integrationsAvailabilityStatus", status);
                if ("LOANS".equalsIgnoreCase(src)) f.put("loansAvailabilityStatus", status);
                if ("RETURNS".equalsIgnoreCase(src)) f.put("returnsAvailabilityStatus", status);
                if ("TRANSFERS".equalsIgnoreCase(src)) f.put("transfersAvailabilityStatus", status);
                if ("REPAIRS".equalsIgnoreCase(src)) f.put("repairsAvailabilityStatus", status);
                if ("INVENTORY".equalsIgnoreCase(src)) f.put("inventoryAvailabilityStatus", status);
            }
        }

        // 6. Event counts
        f.put("loansCount", count(s, "LoanOpened"));
        f.put("loansClosedCount", count(s, "LoanClosed"));
        f.put("returnsCount", count(s, "ReturnReceived"));
        f.put("transfersDispatchedCount", count(s, "TransferDispatched"));
        f.put("transfersReceivedCount", count(s, "TransferReceived"));
        f.put("repairsCount", count(s, "RepairStarted"));

        return f;
    }
    private int count(EvidenceSnapshot s,String type){return (int)s.sourceEvents().stream().filter(e->type.equals(e.get("eventType"))).count();}
}
