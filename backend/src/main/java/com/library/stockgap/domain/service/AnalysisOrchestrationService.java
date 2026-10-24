package com.library.stockgap.domain.service;

import com.library.stockgap.domain.model.*;
import com.library.stockgap.infrastructure.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
public class AnalysisOrchestrationService {
    private static final Logger log = LoggerFactory.getLogger(AnalysisOrchestrationService.class);

    private final CaseRepository caseRepository;
    private final AnalysisRepository analysisRepository;
    private final RuleRepository ruleRepository;
    private final MissionRepository missionRepository;
    private final OutboxRepository outboxRepository;
    private final DeterministicAnalysisService deterministicAnalysisService;
    private final SseHubService sseHubService;

    public AnalysisOrchestrationService(CaseRepository caseRepository,
                                        AnalysisRepository analysisRepository,
                                        RuleRepository ruleRepository,
                                        MissionRepository missionRepository,
                                        OutboxRepository outboxRepository,
                                        DeterministicAnalysisService deterministicAnalysisService,
                                        SseHubService sseHubService) {
        this.caseRepository = caseRepository;
        this.analysisRepository = analysisRepository;
        this.ruleRepository = ruleRepository;
        this.missionRepository = missionRepository;
        this.outboxRepository = outboxRepository;
        this.deterministicAnalysisService = deterministicAnalysisService;
        this.sseHubService = sseHubService;
    }

    @Transactional
    public UUID startAnalysis(UUID epoch, UUID caseId) {
        var dossierOpt = caseRepository.findCaseDossier(epoch, caseId);
        if (dossierOpt.isEmpty()) {
            throw new IllegalArgumentException("Dossier introuvable pour caseId: " + caseId);
        }
        CaseDossier dossier = dossierOpt.get();

        UUID snapshotId = UUID.randomUUID();
        UUID analysisId = UUID.randomUUID();
        int activeVer = ruleRepository.getActiveRuleSetVersion(epoch);
        var activeRules = ruleRepository.getActiveRules(epoch);

        // 1. Build and insert EvidenceSnapshot
        Map<String, Object> facts = Map.of(
            "caseId", caseId.toString(),
            "copyId", dossier.copyId().toString(),
            "owningSiteId", dossier.owningSiteId(),
            "inventoryReferenceTime", dossier.inventoryReferenceTime().toString()
        );
        EvidenceSnapshot snapshot = new EvidenceSnapshot(
            snapshotId, caseId, dossier.copyId(), "1.0.0",
            dossier.inventoryReferenceTime(), Instant.now(), facts,
            dossier.timeline(), dossier.operatorNotes(), dossier.systemState(),
            dossier.observations(), dossier.sourceAvailability(), dossier.symptoms()
        );
        analysisRepository.insertSnapshot(epoch, snapshot);

        // 2. Insert analysis_run
        analysisRepository.insertAnalysisRun(epoch, analysisId, caseId, snapshotId, activeVer);
        sseHubService.publishEvent(epoch, "AnalysisChanged", "Analysis", analysisId.toString(),
            Map.of("analysisId", analysisId, "caseId", caseId, "state", "RUNNING", "version", 1));

        // 3. Evaluate deterministically
        var result = deterministicAnalysisService.evaluate(snapshot, activeRules);

        if (result.fullyCovered()) {
            // Deterministic success
            UUID diagId = UUID.randomUUID();
            DiagnosticRecord diag = new DiagnosticRecord(
                diagId, analysisId, caseId, EpistemicStatus.SUPPORTED, result.findings(),
                Collections.emptyList(), "Diagnostic déterministe complet établi par les règles DMN actives.",
                true, "DMN"
            );
            analysisRepository.markDeterministicCompleted(epoch, analysisId, caseId, result.trace(), diag);

            sseHubService.publishEvent(epoch, "AnalysisChanged", "Analysis", analysisId.toString(),
                Map.of("analysisId", analysisId, "caseId", caseId, "state", "COMPLETED", "isDeterministic", true));
            sseHubService.publishEvent(epoch, "DiagnosticReady", "Diagnostic", diagId.toString(),
                Map.of("diagnosticId", diagId, "analysisId", analysisId, "caseId", caseId, "conclusion", "SUPPORTED"));

        } else {
            // Fallback required
            analysisRepository.markFallbackRequested(epoch, analysisId, caseId, result.fallbackReason(), result.trace());

            UUID missionId = UUID.randomUUID();
            missionRepository.createMission(epoch, missionId, "INVESTIGATION", analysisId, null, snapshotId, activeVer, null);

            // Kafka command outbox
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
            envelope.put("ruleSetVersion", activeVer);
            envelope.put("evidenceSnapshotId", snapshotId.toString());
            envelope.put("caseId", caseId.toString());
            envelope.put("analysisId", analysisId.toString());
            envelope.put("payload", Map.of(
                "reason", result.fallbackReason().name(),
                "trace", result.trace()
            ));
            outboxRepository.enqueueEvent(epoch, UUID.fromString((String) envelope.get("eventId")), missionId, "InvestigationRequested", envelope);

            sseHubService.publishEvent(epoch, "AnalysisChanged", "Analysis", analysisId.toString(),
                Map.of("analysisId", analysisId, "caseId", caseId, "state", "AGENT_PENDING", "fallbackReason", result.fallbackReason().name()));
            sseHubService.publishEvent(epoch, "MissionChanged", "Mission", missionId.toString(),
                Map.of("missionId", missionId, "kind", "INVESTIGATION", "status", "QUEUED"));
        }

        return analysisId;
    }

    @Transactional
    public UUID acceptDiagnostic(UUID epoch, UUID diagnosticId, UUID caseId) {
        UUID acceptanceId = analysisRepository.acceptDiagnostic(epoch, diagnosticId, caseId);

        // Fetch diagnostic to check if it was from agent investigation
        var diagOpt = analysisRepository.findDiagnostic(epoch, diagnosticId);
        if (diagOpt.isPresent() && !diagOpt.get().isDeterministic()) {
            // Trigger rule synthesis mission
            var analysisOpt = analysisRepository.findAnalysis(epoch, diagOpt.get().analysisId());
            if (analysisOpt.isPresent()) {
                UUID snapshotId = (UUID) analysisOpt.get().get("snapshotId");
                int ruleSetVer = (int) analysisOpt.get().get("ruleSetVersion");

                UUID synthMissionId = UUID.randomUUID();
                missionRepository.createMission(epoch, synthMissionId, "RULE_SYNTHESIS", null, acceptanceId, snapshotId, ruleSetVer, null);

                Map<String, Object> envelope = new HashMap<>();
                envelope.put("eventId", UUID.randomUUID().toString());
                envelope.put("eventType", "RuleSynthesisRequested");
                envelope.put("schemaVersion", "1.0.0");
                envelope.put("occurredAt", Instant.now().toString());
                envelope.put("datasetEpoch", epoch.toString());
                envelope.put("correlationId", acceptanceId.toString());
                envelope.put("causationId", diagnosticId.toString());
                envelope.put("missionId", synthMissionId.toString());
                envelope.put("attempt", 1);
                envelope.put("ruleSetVersion", ruleSetVer);
                envelope.put("evidenceSnapshotId", snapshotId.toString());
                envelope.put("caseId", caseId.toString());
                envelope.put("payload", Map.of(
                    "originDiagnosticId", diagnosticId.toString(),
                    "originAcceptanceId", acceptanceId.toString()
                ));
                outboxRepository.enqueueEvent(epoch, UUID.fromString((String) envelope.get("eventId")), synthMissionId, "RuleSynthesisRequested", envelope);

                sseHubService.publishEvent(epoch, "MissionChanged", "Mission", synthMissionId.toString(),
                    Map.of("missionId", synthMissionId, "kind", "RULE_SYNTHESIS", "status", "QUEUED"));
            }
        }

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
