package com.library.stockgap.domain.service;

import com.library.stockgap.domain.model.*;
import java.util.*;

/** Validation stricte d'un DiagnosticProposed contre le snapshot figé de sa mission. */
public final class AgentDiagnosticAssembler {
    private AgentDiagnosticAssembler() { }

    public static DiagnosticRecord assemble(UUID diagnosticId, UUID analysisId, UUID caseId,
                                            Map<String, Object> diagnostic, EvidenceSnapshot snapshot) {
        if (diagnostic == null || snapshot == null || !caseId.equals(snapshot.caseId())) {
            throw new IllegalArgumentException("Diagnostic ou snapshot de mission invalide");
        }
        List<Finding> findings = toFindings(diagnostic.get("findings"));
        if (findings.isEmpty()) throw new IllegalArgumentException("Aucun finding recevable");
        validateGrounding(findings, snapshot);
        DiagnosticConclusion conclusion = DiagnosticConclusion.fromAgentValue(asString(diagnostic.get("conclusion")), findings);
        String summary = asString(diagnostic.get("summary"));
        if (summary == null || summary.isBlank()) throw new IllegalArgumentException("summary absent");
        return new DiagnosticRecord(diagnosticId, analysisId, caseId, conclusion, findings,
            strings(diagnostic.get("missingEvidence")), summary, false, "INVESTIGATOR");
    }

    private static List<Finding> toFindings(Object raw) {
        if (!(raw instanceof List<?> items)) throw new IllegalArgumentException("findings absent");
        List<Finding> findings = new ArrayList<>();
        for (Object item : items) {
            if (!(item instanceof Map<?, ?> map)) throw new IllegalArgumentException("finding invalide");
            UUID copyId = uuid(map.get("subjectCopyId"));
            String cause = asString(map.get("causeCode"));
            String explanation = asString(map.get("explanation"));
            if (copyId == null || cause == null || cause.isBlank() || explanation == null || explanation.isBlank()) throw new IllegalArgumentException("finding incomplet");
            findings.add(new Finding(copyId, uuids(map.get("coveredSymptomIds")), cause,
                status(map.get("epistemicStatus")), strings(map.get("assertions")), refs(map.get("evidenceRefs")),
                refs(map.get("contradictionRefs")), strings(map.get("recommendedChecks")), explanation,
                asString(map.get("ruleId")), integer(map.get("ruleVersion"))));
        }
        return findings;
    }

    private static void validateGrounding(List<Finding> findings, EvidenceSnapshot snapshot) {
        Set<UUID> symptoms = ids(snapshot.symptoms(), "symptomId");
        Set<UUID> records = new HashSet<>();
        records.addAll(ids(snapshot.sourceEvents(), "sourceEventId"));
        records.addAll(ids(snapshot.observations(), "observationId"));
        records.addAll(ids(snapshot.operatorNotes(), "noteId"));
        for (Finding finding : findings) {
            if (!snapshot.copyId().equals(finding.subjectCopyId())) throw new IllegalArgumentException("copyId hors snapshot");
            if (finding.coveredSymptomIds().isEmpty() || !symptoms.containsAll(finding.coveredSymptomIds())) throw new IllegalArgumentException("symptôme hors snapshot");
            for (EvidenceRef ref : concat(finding.evidenceRefs(), finding.contradictionRefs())) {
                if (!snapshot.snapshotId().equals(ref.evidenceSnapshotId()) || !records.contains(ref.recordId())) throw new IllegalArgumentException("preuve hors snapshot");
            }
        }
    }
    private static List<EvidenceRef> concat(List<EvidenceRef> a, List<EvidenceRef> b) { List<EvidenceRef> all = new ArrayList<>(a); all.addAll(b); return all; }
    private static Set<UUID> ids(List<Map<String,Object>> items, String key) { Set<UUID> result = new HashSet<>(); for (Map<String,Object> item : items) { UUID id = uuid(item.get(key)); if (id != null) result.add(id); } return result; }
    private static List<EvidenceRef> refs(Object raw) { if (!(raw instanceof List<?> list)) throw new IllegalArgumentException("references absentes"); List<EvidenceRef> result = new ArrayList<>(); for (Object item : list) { if (!(item instanceof Map<?,?> map)) throw new IllegalArgumentException("reference invalide"); String source=asString(map.get("source")); UUID record=uuid(map.get("recordId")); UUID snap=uuid(map.get("evidenceSnapshotId")); if (source==null||record==null||snap==null) throw new IllegalArgumentException("reference incomplete"); result.add(new EvidenceRef(source,record,snap,asString(map.get("fieldPath")))); } return result; }
    private static List<UUID> uuids(Object raw) { if (!(raw instanceof List<?> list)) throw new IllegalArgumentException("identifiants absents"); List<UUID> result=new ArrayList<>(); for(Object x:list){UUID id=uuid(x); if(id==null) throw new IllegalArgumentException("UUID invalide"); result.add(id);} return result; }
    private static List<String> strings(Object raw) { if (!(raw instanceof List<?> list)) throw new IllegalArgumentException("liste absente"); List<String> result=new ArrayList<>(); for(Object x:list){if(!(x instanceof String s)) throw new IllegalArgumentException("texte invalide"); result.add(s);} return result; }
    private static EpistemicStatus status(Object raw) { try { return EpistemicStatus.valueOf(asString(raw)); } catch(Exception e) { throw new IllegalArgumentException("epistemicStatus invalide"); } }
    private static UUID uuid(Object raw) { try { return raw == null ? null : UUID.fromString(raw.toString()); } catch (IllegalArgumentException e) { return null; } }
    private static String asString(Object raw) { return raw instanceof String s ? s : null; }
    private static Integer integer(Object raw) { return raw instanceof Number n ? n.intValue() : null; }
}
