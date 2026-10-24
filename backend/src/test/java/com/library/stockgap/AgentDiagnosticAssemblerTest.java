package com.library.stockgap;

import com.library.stockgap.domain.model.*;
import com.library.stockgap.domain.service.AgentDiagnosticAssembler;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class AgentDiagnosticAssemblerTest {
    private final UUID diagnosticId = UUID.randomUUID(), analysisId = UUID.randomUUID(), caseId = UUID.randomUUID(), copyId = UUID.randomUUID(), symptomId = UUID.randomUUID(), snapshotId = UUID.randomUUID(), eventId = UUID.randomUUID();
    private EvidenceSnapshot snapshot() { return new EvidenceSnapshot(snapshotId, caseId, copyId, "1.0.0", Instant.now(), Instant.now(), Map.of(), List.of(Map.of("sourceEventId", eventId.toString())), List.of(), Map.of(), List.of(), List.of(), List.of(Map.of("symptomId", symptomId.toString()))); }
    private Map<String,Object> finding() { return new HashMap<>(Map.of("subjectCopyId",copyId.toString(),"coveredSymptomIds",List.of(symptomId.toString()),"causeCode","LOAN_NOT_PROJECTED","epistemicStatus","SUPPORTED","assertions",List.of("preuve"),"evidenceRefs",List.of(Map.of("source","LOANS","recordId",eventId.toString(),"evidenceSnapshotId",snapshotId.toString(),"fieldPath","/sourceEvents/0")),"contradictionRefs",List.of(),"recommendedChecks",List.of("contrôler"),"explanation","preuve corroborée")); }
    private Map<String,Object> diagnostic(Map<String,Object> finding) { return Map.of("conclusion","SUPPORTED","findings",List.of(finding),"missingEvidence",List.of(),"summary","Synthèse validée"); }
    @Test void groundedDiagnosticIsAccepted() { var record=AgentDiagnosticAssembler.assemble(diagnosticId,analysisId,caseId,diagnostic(finding()),snapshot()); assertEquals(DiagnosticConclusion.SUPPORTED,record.conclusion()); assertEquals(eventId,record.findings().getFirst().evidenceRefs().getFirst().recordId()); }
    @Test void inventedEvidenceIsRejected() { var finding=finding(); finding.put("evidenceRefs",List.of(Map.of("source","LOANS","recordId",UUID.randomUUID().toString(),"evidenceSnapshotId",snapshotId.toString(),"fieldPath","/x"))); assertThrows(IllegalArgumentException.class,()->AgentDiagnosticAssembler.assemble(diagnosticId,analysisId,caseId,diagnostic(finding),snapshot())); }
    @Test void foreignCopyOrSymptomIsRejected() { var badCopy=finding(); badCopy.put("subjectCopyId",UUID.randomUUID().toString()); assertThrows(IllegalArgumentException.class,()->AgentDiagnosticAssembler.assemble(diagnosticId,analysisId,caseId,diagnostic(badCopy),snapshot())); var badSymptom=finding(); badSymptom.put("coveredSymptomIds",List.of(UUID.randomUUID().toString())); assertThrows(IllegalArgumentException.class,()->AgentDiagnosticAssembler.assemble(diagnosticId,analysisId,caseId,diagnostic(badSymptom),snapshot())); }
    @Test void missingDiagnosticIsRejected() { assertThrows(IllegalArgumentException.class,()->AgentDiagnosticAssembler.assemble(diagnosticId,analysisId,caseId,null,snapshot())); }
}
