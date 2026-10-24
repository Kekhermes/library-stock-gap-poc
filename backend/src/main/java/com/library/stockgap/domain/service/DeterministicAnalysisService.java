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
        for(var rule:rules){ var result=dmnEngineService.evaluateRule(runtime,rule,facts,snapshot.copyId(),ids); if(result.isEmpty()) traces.add(Map.of("ruleId",rule.ruleId(),"match",false)); else { findings.addAll(result); covered.addAll(rule.coveredSymptomKinds()); traces.add(Map.of("ruleId",rule.ruleId(),"match",true,"findingsCount",result.size())); } }
        var trace=Map.<String,Object>of("rulesEvaluated",rules.size(),"ruleTraces",traces,"caseSymptomKinds",kinds,"coveredSymptomKinds",covered);
        if(findings.isEmpty()) return new DeterministicEvaluationResult(false,FallbackReason.NO_RULE_MATCH,List.of(),trace);
        return new DeterministicEvaluationResult(covered.containsAll(kinds), covered.containsAll(kinds)?null:FallbackReason.PARTIAL_COVERAGE, findings, trace);
    }
    private Map<String,Object> facts(EvidenceSnapshot s){ Map<String,Object> f=new HashMap<>(); var sys=s.systemState(); f.put("expectedSiteId",sys.get("siteId")); f.put("expectedLocationId",sys.get("locationId") == null?null:sys.get("locationId").toString()); f.put("expectedStatus",sys.get("status")); var obs=s.observations(); if(obs.isEmpty()){f.put("observedSiteId",null);f.put("observedLocationId",null);f.put("observationReliable",false);}else{var o=obs.getFirst();f.put("observedSiteId",o.get("siteId"));f.put("observedLocationId",o.get("locationId")==null?null:o.get("locationId").toString());f.put("observationReliable","VALID".equals(o.get("quality")));} f.put("loansCount",count(s,"LoanOpened"));f.put("loansClosedCount",count(s,"LoanClosed")); return f; }
    private int count(EvidenceSnapshot s,String type){return (int)s.sourceEvents().stream().filter(e->type.equals(e.get("eventType"))).count();}
}
