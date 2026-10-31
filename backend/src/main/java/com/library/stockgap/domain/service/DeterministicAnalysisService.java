package com.library.stockgap.domain.service;

import com.library.stockgap.domain.model.*;
import org.kie.dmn.api.core.DMNRuntime;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.*;

@Service
public class DeterministicAnalysisService {
    private final DmnEngineService dmnEngineService;
    public DeterministicAnalysisService(DmnEngineService dmnEngineService) { this.dmnEngineService = dmnEngineService; }
    public record DeterministicEvaluationResult(boolean fullyCovered, FallbackReason fallbackReason, List<Finding> findings, Map<String,Object> trace) {}
    public DeterministicEvaluationResult evaluate(EvidenceSnapshot snapshot, List<RuleMetadata> rules) {
        if (rules.isEmpty()) return new DeterministicEvaluationResult(false, FallbackReason.NO_RULE_MATCH, List.of(), Map.of("rulesEvaluated",0));
        DMNRuntime runtime = dmnEngineService.createRuntime(rules);
        List<UUID> ids=new ArrayList<>(); Set<String> kinds=new HashSet<>();
        for(var symptom:snapshot.symptoms()){ ids.add(UUID.fromString(symptom.get("symptomId").toString())); kinds.add(symptom.get("kind").toString()); }
        Map<String,Object> facts=facts(snapshot); List<Finding> findings=new ArrayList<>(); Set<String> covered=new HashSet<>(); List<Map<String,Object>> traces=new ArrayList<>();
        int evaluatedCount = 0;
        boolean missingEvidence = false;
        Set<UUID> coveredIds = new HashSet<>();
        Map<String, Set<String>> causesBySymptom = new LinkedHashMap<>();
        for (var rule : rules) {
            if (Collections.disjoint(rule.coveredSymptomKinds(), kinds)) {
                traces.add(Map.of("ruleId", rule.ruleId(), "match", false, "evaluated", false, "reason", "SYMPTOM_OUT_OF_SCOPE"));
                continue;
            }
            Map<String, Object> inputs = new LinkedHashMap<>();
            for (String name : dmnEngineService.inputNames(runtime, rule)) inputs.put(name, facts.get(name));
            List<String> missing = inputs.entrySet().stream().filter(input -> input.getValue() == null).map(Map.Entry::getKey).toList();
            if (!missing.isEmpty()) {
                missingEvidence = true;
                traces.add(Map.of("ruleId", rule.ruleId(), "match", false, "evaluated", false, "reason", "MISSING_FACTS", "missingFacts", missing, "inputs", inputs));
                continue;
            }
            evaluatedCount++;
            List<UUID> ruleSymptoms = snapshot.symptoms().stream().filter(symptom -> rule.coveredSymptomKinds().contains(symptom.get("kind")))
                .map(symptom -> UUID.fromString(symptom.get("symptomId").toString())).toList();
            var result = dmnEngineService.evaluateRule(runtime, rule, facts, snapshot.copyId(), ruleSymptoms);
            if (result.isEmpty()) traces.add(Map.of("ruleId", rule.ruleId(), "match", false, "evaluated", true, "inputs", inputs));
            else {
                List<EvidenceRef> references = references(facts, inputs.keySet());
                for (Finding finding : result) {
                    findings.add(new Finding(finding.subjectCopyId(), finding.coveredSymptomIds(), finding.causeCode(), finding.epistemicStatus(),
                        finding.assertions(), references, finding.contradictionRefs(), finding.recommendedChecks(), finding.explanation(), finding.ruleId(), finding.ruleVersion()));
                    if (finding.epistemicStatus() == EpistemicStatus.SUPPORTED) {
                        coveredIds.addAll(finding.coveredSymptomIds());
                        covered.addAll(rule.coveredSymptomKinds());
                        for (UUID symptom : finding.coveredSymptomIds()) causesBySymptom.computeIfAbsent(symptom.toString(), ignored -> new LinkedHashSet<>()).add(finding.causeCode());
                    } else missingEvidence = true;
                }
                traces.add(Map.of("ruleId", rule.ruleId(), "match", true, "evaluated", true, "findingsCount", result.size(), "inputs", inputs, "evidenceRefs", references));
            }
        }
        boolean competingCauses = causesBySymptom.values().stream().anyMatch(causes -> causes.size() > 1);
        var trace=Map.<String,Object>of("rulesEvaluated",evaluatedCount,"rulesConsidered",rules.size(),"ruleTraces",traces,"caseSymptomKinds",kinds,
            "coveredSymptomKinds",covered,"facts",facts,"causesBySymptom",causesBySymptom,"competingCauses",competingCauses);
        if (missingEvidence || competingCauses) return new DeterministicEvaluationResult(false, FallbackReason.INSUFFICIENT_EVIDENCE, findings, trace);
        if(findings.isEmpty()) return new DeterministicEvaluationResult(false,FallbackReason.NO_RULE_MATCH,List.of(),trace);
        boolean complete = coveredIds.containsAll(ids);
        return new DeterministicEvaluationResult(complete, complete ? null : FallbackReason.PARTIAL_COVERAGE, findings, trace);
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
        var obs = s.observations().stream().filter(observation -> admissible(s, observation, "observedAt")).toList();
        if (obs.size() != 1 || !"VALID".equals(obs.getFirst().get("quality"))) {
            f.put("observedSiteId", null);
            f.put("observedLocationId", null);
            f.put("observedStatus", null);
            f.put("observedValue", null);
            f.put("observationReliable", null);
            f.put("observationQuality", obs.isEmpty() ? null : obs.size() > 1 ? "AMBIGUOUS" : obs.getFirst().get("quality"));
        } else {
            var o = obs.getFirst();
            f.put("observedSiteId", o.get("siteId"));
            f.put("observedLocationId", o.get("locationId") == null ? null : o.get("locationId").toString());
            String quality = (String) o.get("quality");
            f.put("observationQuality", quality);
            f.put("observationReliable", o.get("siteId") != null && o.get("locationId") != null ? true : null);
        }
        f.put("observationsCount", obs.size());

        var events = s.sourceEvents().stream().filter(event -> admissible(s, event, "occurredAt")).toList();
        Set<String> operations = Set.of("LoanOpened", "ReturnReceived", "ReceiptReceived", "TransferDispatched", "TransferReceived", "RepairStarted");
        Comparator<Map<String, Object>> eventOrder = Comparator
            .comparing((Map<String, Object> event) -> timestamp(event.get("occurredAt")))
            .thenComparing(event -> timestamp(event.get("recordedAt")))
            .thenComparing(event -> Objects.toString(event.get("sourceEventId"), ""));
        Map<String, Object> latestBusinessEvent = events.stream()
            .filter(event -> operations.contains(event.get("eventType"))).max(eventOrder).orElse(null);
        Map<String, Object> latestIntegration = latestBusinessEvent == null ? null : events.stream()
            .filter(event -> "IntegrationProcessed".equals(event.get("eventType")))
            .filter(event -> latestBusinessEvent.get("sourceEventId") != null)
            .filter(event -> event.get("payload") instanceof Map<?, ?> payload
                && Objects.equals(Objects.toString(payload.get("originalEventId"), null), Objects.toString(latestBusinessEvent.get("sourceEventId"), null))
                && (!(latestBusinessEvent.get("payload") instanceof Map<?, ?> original) || original.get("correlationId") == null
                    || Objects.equals(original.get("correlationId"), payload.get("correlationId"))))
            .max(eventOrder).orElse(null);
        for (String name : List.of("latestIntegrationOutcome", "integrationOutcome", "latestIntegrationErrorCode", "integrationErrorCode",
                "errorCode", "latestErrorCode", "latestProjectedStatus", "projectedStatus", "integrationEventType",
                "latestBusinessEventType", "businessEventType", "repairOutcome", "repairProjectedStatus")) f.put(name, null);

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

        for (String source : List.of("LOANS", "RETURNS", "RECEIPTS", "TRANSFERS", "REPAIRS", "INTEGRATIONS", "INVENTORY")) {
            f.put(source.toLowerCase(Locale.ROOT) + "AvailabilityStatus", null);
        }
        for (var sa : s.sourceAvailability()) {
            String src = (String) sa.get("source");
            String status = (String) sa.get("status");
            if (src != null && status != null) {
                if ("COMPLETE".equals(status) && (sa.get("completeFrom") == null || sa.get("completeTo") == null
                        || timestamp(sa.get("completeFrom")).isAfter(s.inventoryReferenceTime())
                        || timestamp(sa.get("completeTo")).isBefore(s.inventoryReferenceTime()))) status = "PARTIAL";
                String name = src.toLowerCase(Locale.ROOT) + "AvailabilityStatus";
                Object previous = f.get(name);
                if ("UNAVAILABLE".equals(previous) || "PARTIAL".equals(previous) && "COMPLETE".equals(status)) continue;
                f.put(name, status);
            }
        }

        f.put("loansCount", count(events, f, "loans", "LoanOpened"));
        f.put("loansClosedCount", count(events, f, "loans", "LoanClosed"));
        f.put("returnsCount", count(events, f, "returns", "ReturnReceived"));
        f.put("transfersDispatchedCount", count(events, f, "transfers", "TransferDispatched"));
        f.put("transfersReceivedCount", count(events, f, "transfers", "TransferReceived"));
        f.put("repairsCount", count(events, f, "repairs", "RepairStarted"));

        Map<String, List<Map<String, Object>>> provenance = new LinkedHashMap<>();
        if (!s.symptoms().isEmpty() && s.symptoms().getFirst().get("symptomId") != null) {
            var symptom = s.symptoms().getFirst();
            for (String name : List.of("symptomKind", "expectedValue", "observedValue", "observedStatus")) {
                if (f.get(name) != null) provenance.put(name, List.of(Map.of("source", "SYMPTOMS", "recordId", symptom.get("symptomId").toString(),
                    "evidenceSnapshotId", s.snapshotId().toString(), "fieldPath", "/symptoms/0")));
            }
        }
        for (String name : List.of("systemStatus", "expectedStatus", "expectedSiteId", "expectedLocationId")) {
            if (f.get(name) != null) provenance.put(name, List.of(Map.of("source", "SYSTEM_STATE", "recordId", s.copyId().toString(),
                "evidenceSnapshotId", s.snapshotId().toString(), "fieldPath", "/systemState")));
        }
        if (obs.size() == 1 && Boolean.TRUE.equals(f.get("observationReliable")) && obs.getFirst().get("observationId") != null) {
            var observation = obs.getFirst();
            for (String name : List.of("observedSiteId", "observedLocationId", "observationReliable", "observationQuality")) {
                if (f.get(name) != null) provenance.put(name, List.of(Map.of("source", "INVENTORY", "recordId", observation.get("observationId").toString(),
                    "evidenceSnapshotId", s.snapshotId().toString(), "fieldPath", "/observations")));
            }
        }
        for (int sourceIndex = 0; sourceIndex < s.sourceAvailability().size(); sourceIndex++) {
            Map<String, Object> source = s.sourceAvailability().get(sourceIndex);
            if (source.get("source") == null) continue;
            String name = source.get("source").toString().toLowerCase(Locale.ROOT) + "AvailabilityStatus";
            if (f.get(name) == null) continue;
            provenance.computeIfAbsent(name, ignored -> new ArrayList<>()).add(Map.of("source", "SOURCE_AVAILABILITY",
                "recordId", s.copyId().toString(), "evidenceSnapshotId", s.snapshotId().toString(), "fieldPath", "/sourceAvailability/" + sourceIndex));
        }
        Map<String, String> countedSources = Map.of("loansCount", "loans", "loansClosedCount", "loans", "returnsCount", "returns",
            "transfersDispatchedCount", "transfers", "transfersReceivedCount", "transfers", "repairsCount", "repairs");
        for (var counted : countedSources.entrySet()) {
            if (f.get(counted.getKey()) != null) provenance.put(counted.getKey(), provenance.getOrDefault(counted.getValue() + "AvailabilityStatus", List.of()));
        }
        if (latestBusinessEvent != null && latestBusinessEvent.get("sourceEventId") != null) {
            provenance.put("latestBusinessEventType", List.of(Map.of("source", "SOURCE_EVENTS", "recordId", latestBusinessEvent.get("sourceEventId").toString(),
                "evidenceSnapshotId", s.snapshotId().toString(), "fieldPath", "/sourceEvents")));
            provenance.put("businessEventType", provenance.get("latestBusinessEventType"));
        }
        if (latestIntegration != null) {
            List<Map<String, Object>> references = List.of(
                Map.of("source", "SOURCE_EVENTS", "recordId", latestBusinessEvent.get("sourceEventId").toString(),
                    "evidenceSnapshotId", s.snapshotId().toString(), "fieldPath", "/sourceEvents"),
                Map.of("source", "INTEGRATIONS", "recordId", latestIntegration.get("sourceEventId").toString(),
                    "evidenceSnapshotId", s.snapshotId().toString(), "fieldPath", "/sourceEvents"));
                for (String name : List.of("latestIntegrationOutcome", "integrationOutcome", "latestIntegrationErrorCode", "integrationErrorCode", "errorCode",
                    "latestErrorCode", "latestProjectedStatus", "projectedStatus", "repairOutcome", "repairProjectedStatus")) {
                if (f.get(name) != null) provenance.put(name, references);
            }
        }
        f.put("factProvenance", provenance);

        return f;
    }

    @SuppressWarnings("unchecked")
    private List<EvidenceRef> references(Map<String, Object> facts, Set<String> names) {
        Map<String, List<Map<String, Object>>> provenance = (Map<String, List<Map<String, Object>>>) facts.get("factProvenance");
        Set<EvidenceRef> references = new LinkedHashSet<>();
        for (String name : names) {
            for (Map<String, Object> reference : provenance.getOrDefault(name, List.of())) {
                references.add(new EvidenceRef(reference.get("source").toString(), UUID.fromString(reference.get("recordId").toString()),
                    UUID.fromString(reference.get("evidenceSnapshotId").toString()), reference.get("fieldPath").toString()));
            }
        }
        return List.copyOf(references);
    }
    private Integer count(List<Map<String, Object>> events, Map<String, Object> facts, String source, String type) {
        if (!"COMPLETE".equals(facts.get(source + "AvailabilityStatus"))) return null;
        return (int) events.stream().filter(event -> type.equals(event.get("eventType"))).count();
    }

    private Instant timestamp(Object value) {
        if (value == null) return Instant.MIN;
        return value instanceof Instant instant ? instant : Instant.parse(value.toString());
    }

    private boolean admissible(EvidenceSnapshot snapshot, Map<String, Object> record, String timeField) {
        return (record.get("copyId") == null || snapshot.copyId().toString().equals(record.get("copyId").toString()))
            && record.get(timeField) != null
            && (!"occurredAt".equals(timeField) || record.get("recordedAt") != null)
            && !timestamp(record.get(timeField)).isAfter(snapshot.inventoryReferenceTime())
            && !timestamp(record.get("recordedAt")).isAfter(snapshot.dataCapturedAt());
    }
}
