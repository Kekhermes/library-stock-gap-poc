package com.library.stockgap.domain.service;

import com.library.stockgap.domain.model.CaseDossier;
import com.library.stockgap.domain.model.CaseStatus;
import com.library.stockgap.domain.model.EvidenceSnapshot;
import com.library.stockgap.infrastructure.persistence.AnalysisRepository;
import com.library.stockgap.infrastructure.persistence.CaseRepository;
import com.library.stockgap.infrastructure.persistence.MissionRepository;
import com.library.stockgap.infrastructure.persistence.OutboxRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Orchestration agent-first : chaque analyse est confiée au worker GPT. */
@Service
public class AnalysisOrchestrationService {
    private final CaseRepository caseRepository;
    private final AnalysisRepository analysisRepository;
    private final MissionRepository missionRepository;
    private final OutboxRepository outboxRepository;
    private final SseHubService sseHubService;

    public AnalysisOrchestrationService(CaseRepository caseRepository,
                                        AnalysisRepository analysisRepository,
                                        MissionRepository missionRepository,
                                        OutboxRepository outboxRepository,
                                        SseHubService sseHubService) {
        this.caseRepository = caseRepository;
        this.analysisRepository = analysisRepository;
        this.missionRepository = missionRepository;
        this.outboxRepository = outboxRepository;
        this.sseHubService = sseHubService;
    }

    @Transactional
    public UUID startAnalysis(UUID epoch, UUID caseId) {
        CaseDossier dossier = caseRepository.findCaseDossier(epoch, caseId)
            .orElseThrow(() -> new IllegalArgumentException("Dossier introuvable pour caseId: " + caseId));

        analysisRepository.findActiveAnalysisId(epoch, caseId).ifPresent(previous -> {
            analysisRepository.updateAnalysisStatus(epoch, previous, "CANCELLED");
            missionRepository.cancelOpenMissionsForAnalysis(epoch, previous);
            sseHubService.publishEvent(epoch, "AnalysisChanged", "Analysis", previous.toString(),
                Map.of("analysisId", previous, "caseId", caseId, "state", "CANCELLED", "reason", "SUPERSEDED"));
        });

        UUID snapshotId = UUID.randomUUID();
        UUID analysisId = UUID.randomUUID();
        EvidenceSnapshot snapshot = new EvidenceSnapshot(
            snapshotId, caseId, dossier.copyId(), "1.0.0", dossier.inventoryReferenceTime(), Instant.now(),
            Map.of("caseId", caseId.toString(), "copyId", dossier.copyId().toString(),
                "owningSiteId", dossier.owningSiteId(), "inventoryReferenceTime", dossier.inventoryReferenceTime().toString()),
            dossier.timeline(), dossier.operatorNotes(), dossier.systemState(), dossier.observations(),
            dossier.sourceAvailability(), dossier.symptoms());
        analysisRepository.insertSnapshot(epoch, snapshot);
        analysisRepository.insertAnalysisRun(epoch, analysisId, caseId, snapshotId, 1);

        UUID missionId = UUID.randomUUID();
        missionRepository.createMission(epoch, missionId, "INVESTIGATION", analysisId, null, snapshotId, 1, null);
        Map<String, Object> envelope = new HashMap<>();
        envelope.put("eventId", UUID.randomUUID().toString());
        envelope.put("eventType", "InvestigationRequested");
        envelope.put("schemaVersion", "1.0.0");
        envelope.put("occurredAt", Instant.now().toString());
        envelope.put("datasetEpoch", epoch.toString());
        envelope.put("correlationId", analysisId.toString());
        envelope.put("causationId", caseId.toString());
        envelope.put("missionId", missionId.toString());
        envelope.put("attempt", 1);
        envelope.put("ruleSetVersion", 1);
        envelope.put("evidenceSnapshotId", snapshotId.toString());
        envelope.put("caseId", caseId.toString());
        envelope.put("analysisId", analysisId.toString());
        envelope.put("payload", Map.of("reason", "LLM_INVESTIGATION_REQUESTED"));
        outboxRepository.enqueueEvent(epoch, UUID.fromString((String) envelope.get("eventId")), missionId,
            "InvestigationRequested", envelope);

        sseHubService.publishEvent(epoch, "AnalysisChanged", "Analysis", analysisId.toString(),
            Map.of("analysisId", analysisId, "caseId", caseId, "state", "AGENT_PENDING"));
        sseHubService.publishEvent(epoch, "MissionChanged", "Mission", missionId.toString(),
            Map.of("missionId", missionId, "kind", "INVESTIGATION", "status", "QUEUED"));
        return analysisId;
    }

    @Transactional
    public UUID acceptDiagnostic(UUID epoch, UUID diagnosticId, UUID caseId) {
        UUID acceptanceId = analysisRepository.acceptDiagnostic(epoch, diagnosticId, caseId);
        sseHubService.publishEvent(epoch, "AnalysisChanged", "Case", caseId.toString(),
            Map.of("caseId", caseId, "status", "DIAGNOSTIC_ACCEPTE", "acceptanceId", acceptanceId));
        return acceptanceId;
    }

    @Transactional
    public void cancelAnalysis(UUID epoch, UUID analysisId) {
        var analysisOpt = analysisRepository.findAnalysis(epoch, analysisId);
        if (analysisOpt.isEmpty()) return;
        UUID caseId = (UUID) analysisOpt.get().get("caseId");
        analysisRepository.updateAnalysisStatus(epoch, analysisId, "CANCELLED");
        caseRepository.updateCaseStatus(epoch, caseId, CaseStatus.A_ANALYSER);
        sseHubService.publishEvent(epoch, "AnalysisChanged", "Analysis", analysisId.toString(),
            Map.of("analysisId", analysisId, "caseId", caseId, "state", "CANCELLED"));
    }
}
