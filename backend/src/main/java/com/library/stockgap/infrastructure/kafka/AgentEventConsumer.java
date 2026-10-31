package com.library.stockgap.infrastructure.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.stockgap.domain.model.DiagnosticRecord;
import com.library.stockgap.domain.model.EpistemicStatus;
import com.library.stockgap.domain.model.Finding;
import com.library.stockgap.domain.service.AgentDiagnosticAssembler;
import com.library.stockgap.domain.service.RuleGovernanceService;
import com.library.stockgap.domain.service.SseHubService;
import com.library.stockgap.infrastructure.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AgentEventConsumer {
    private static final Logger log = LoggerFactory.getLogger(AgentEventConsumer.class);

    private final InboxRepository inboxRepository;
    private final AnalysisRepository analysisRepository;
    private final RuleRepository ruleRepository;
    private final MissionRepository missionRepository;
    private final ConversationRepository conversationRepository;
    private final RuleGovernanceService ruleGovernanceService;
    private final SseHubService sseHubService;
    private final ObjectMapper objectMapper;

    public AgentEventConsumer(InboxRepository inboxRepository,
                              AnalysisRepository analysisRepository,
                              RuleRepository ruleRepository,
                              MissionRepository missionRepository,
                              ConversationRepository conversationRepository,
                              RuleGovernanceService ruleGovernanceService,
                              SseHubService sseHubService,
                              ObjectMapper objectMapper) {
        this.inboxRepository = inboxRepository;
        this.analysisRepository = analysisRepository;
        this.ruleRepository = ruleRepository;
        this.missionRepository = missionRepository;
        this.conversationRepository = conversationRepository;
        this.ruleGovernanceService = ruleGovernanceService;
        this.sseHubService = sseHubService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${stockgap.topics.events:stock-gap.agent.events.v1}", groupId = "${spring.kafka.consumer.group-id:stock-gap-backend}")
    public void consumeEvent(String message) {
        try {
            Map<String, Object> envelope = objectMapper.readValue(message, Map.class);
            UUID eventId = UUID.fromString((String) envelope.get("eventId"));
            String eventType = (String) envelope.get("eventType");
            UUID epoch = UUID.fromString((String) envelope.get("datasetEpoch"));
            UUID missionId = UUID.fromString((String) envelope.get("missionId"));
            int attempt = ((Number) envelope.getOrDefault("attempt", 1)).intValue();
            long sequence = ((Number) envelope.getOrDefault("sequence", 1)).longValue();

            // Check deduplication
            boolean isNew = inboxRepository.recordAndCheckIfNew(eventId, epoch, missionId, attempt, sequence, eventType, envelope);
            if (!isNew) {
                log.info("Événement Kafka déjà traité ou doublon: {}", eventId);
                return;
            }

            Map<String, Object> payload = (Map<String, Object>) envelope.get("payload");

            switch (eventType) {
                case "DiagnosticProposed" -> handleDiagnosticProposed(epoch, envelope, payload);
                case "RuleChangeProposed" -> handleRuleChangeProposed(epoch, envelope, payload);
                case "RuleChangeNotProposed" -> handleRuleChangeNotProposed(epoch, envelope, payload);
                case "TextChunkProduced" -> handleTextChunkProduced(epoch, envelope, payload);
                case "ToolObservationRecorded" -> handleToolObservation(epoch, envelope, payload);
                case "MissionCancelled" -> handleMissionCancelled(epoch, missionId);
                case "MissionFailed" -> handleMissionFailed(epoch, missionId, uuidOrNull(envelope, "analysisId"), uuidOrNull(envelope, "caseId"));
                default -> log.debug("Événement Kafka ignoré: {}", eventType);
            }

        } catch (Exception e) {
            log.error("Erreur traitement message Kafka: {}", e.getMessage(), e);
        }
    }

    private void handleDiagnosticProposed(UUID epoch, Map<String, Object> envelope, Map<String, Object> payload) {
        UUID caseId = uuidOrNull(envelope, "caseId");
        UUID analysisId = uuidOrNull(envelope, "analysisId");
        UUID missionId = uuidOrNull(envelope, "missionId");
        UUID diagId = payload != null && payload.get("diagnosticId") != null
            ? UUID.fromString((String) payload.get("diagnosticId")) : UUID.randomUUID();

        try {
            Map<String, Object> diagData = payload == null ? null : (Map<String, Object>) payload.get("diagnostic");
            UUID snapshotId = uuidOrNull(envelope, "evidenceSnapshotId");
            UUID leaseToken = payload == null ? null : uuidOrNull(payload, "leaseToken");
            int fencingToken = payload != null && payload.get("fencingToken") instanceof Number n ? n.intValue() : -1;
            if (snapshotId == null || leaseToken == null || !missionRepository.isActiveInvestigation(epoch, missionId, analysisId, snapshotId, leaseToken, fencingToken)) {
                throw new IllegalArgumentException("Mission, lease ou fencing token non actif");
            }
            var snapshot = analysisRepository.findSnapshot(epoch, snapshotId)
                .orElseThrow(() -> new IllegalArgumentException("Dossier de preuves figé introuvable"));
            DiagnosticRecord record = AgentDiagnosticAssembler.assemble(diagId, analysisId, caseId, diagData, snapshot);

            analysisRepository.insertDiagnosticRevision(epoch, record, "INVESTIGATOR", 1);
            analysisRepository.updateAnalysisStatus(epoch, analysisId, "COMPLETED");
            missionRepository.markMissionCompleted(epoch, missionId);

            sseHubService.publishEvent(epoch, "AnalysisChanged", "Analysis", analysisId.toString(),
                Map.of("analysisId", analysisId, "caseId", caseId, "state", "COMPLETED", "isDeterministic", false));
            sseHubService.publishEvent(epoch, "DiagnosticReady", "Diagnostic", diagId.toString(),
                Map.of("diagnosticId", diagId, "analysisId", analysisId, "caseId", caseId,
                    "conclusion", record.conclusion().name()));
        } catch (Exception e) {
            // Un diagnostic agent non conforme ne doit jamais laisser l'analyse pendante:
            // l'échec est visible et le cas redevient analysable.
            log.error("Diagnostic agent rejeté (analyse {}, cas {}): {}", analysisId, caseId, e.getMessage(), e);
            rejectDiagnostic(epoch, analysisId, caseId, missionId, e);
        }
    }

    private void rejectDiagnostic(UUID epoch, UUID analysisId, UUID caseId, UUID missionId, Exception failure) {
        try {
            if (analysisId != null) {
                analysisRepository.failAnalysis(epoch, analysisId);
                sseHubService.publishEvent(epoch, "AnalysisChanged", "Analysis", analysisId.toString(),
                    Map.of("analysisId", analysisId, "caseId", caseId, "state", "FAILED",
                        "reason", String.valueOf(failure.getMessage())));
            }
            if (missionId != null) {
                missionRepository.markMissionFailed(epoch, missionId);
                sseHubService.publishEvent(epoch, "MissionChanged", "Mission", missionId.toString(),
                    Map.of("missionId", missionId, "status", "FAILED"));
            }
        } catch (Exception nested) {
            log.error("Échec du marquage d'échec pour analyse {}: {}", analysisId, nested.getMessage(), nested);
        }
    }

    private void handleRuleChangeProposed(UUID epoch, Map<String, Object> envelope, Map<String, Object> payload) {
        UUID missionId = UUID.fromString((String) envelope.get("missionId"));
        UUID proposalId = envelope.get("proposalId") != null ?
            UUID.fromString((String) envelope.get("proposalId")) : UUID.randomUUID();
        UUID snapshotId = UUID.fromString((String) envelope.get("evidenceSnapshotId"));
        int baseVer = ((Number) envelope.getOrDefault("ruleSetVersion", 1)).intValue();

        Map<String, Object> change = (Map<String, Object>) payload.get("change");
        String op = (String) change.get("operation");
        UUID originDiagId = UUID.fromString((String) change.get("originDiagnosticId"));
        String rationale = (String) change.get("rationale");

        Map<String, Object> draft = (Map<String, Object>) change.get("draft");
        String ruleName = (String) draft.get("name");
        String causeCode = (String) draft.get("causeCode");
        // Les symptômes couverts sont des symptômes métier, jamais des noms de sources.
        List<String> covered = new ArrayList<>();
        Object coveredRaw = draft.get("coveredSymptomKinds");
        if (coveredRaw instanceof List<?> list) {
            for (Object o : list) {
                if (o != null) covered.add(o.toString());
            }
        }
        if (covered.isEmpty()) {
            // Repli conservateur : aucune couverture affirmée sans métadonnée explicite.
            covered = List.of("LOAN_NOT_PROJECTED".equals(causeCode) ? "MISSING" : "STATUS_MISMATCH");
        }

        Map<String, Object> dmnObj = (Map<String, Object>) draft.get("dmn");
        String dmnXml = (String) dmnObj.get("content");
        String targetRuleId = op.equals("CREATE") ? UUID.randomUUID().toString() : "R001";

        ruleRepository.insertProposal(epoch, proposalId, originDiagId, snapshotId, missionId,
            op, targetRuleId, ruleName, causeCode, covered, rationale, dmnXml, baseVer);

        // Prevalidate proposal automatically
        var validationReport = ruleGovernanceService.validateProposal(epoch, proposalId, 1, dmnXml);
        missionRepository.markMissionCompleted(epoch, missionId);
        String proposalStatus = validationReport.passed() ? "READY_FOR_APPROVAL" : validationReport.status().equals("STALE") ? "STALE" : "CHANGES_REQUIRED";

        sseHubService.publishEvent(epoch, "ProposalChanged", "Proposal", proposalId.toString(),
            Map.of("proposalId", proposalId, "status", proposalStatus, "ruleName", ruleName, "validationPassed", validationReport.passed()));
    }

    private void handleRuleChangeNotProposed(UUID epoch, Map<String, Object> envelope, Map<String, Object> payload) {
        UUID missionId = UUID.fromString((String) envelope.get("missionId"));
        String reason = (String) payload.get("reason");
        missionRepository.markMissionCompleted(epoch, missionId);

        sseHubService.publishEvent(epoch, "ProposalChanged", "Proposal", missionId.toString(),
            Map.of("missionId", missionId, "status", "NOT_PROPOSED", "reason", reason));
    }

    private void handleTextChunkProduced(UUID epoch, Map<String, Object> envelope, Map<String, Object> payload) {
        if (envelope.get("conversationId") != null) {
            UUID convId = UUID.fromString((String) envelope.get("conversationId"));
            String text = (String) payload.get("text");
            boolean isFinal = Boolean.TRUE.equals(payload.get("isFinal"));
            if (isFinal) {
                conversationRepository.addMessage(epoch, convId, "RULE_AGENT", text, Collections.emptyList());
                sseHubService.publishEvent(epoch, "ConversationMessageChanged", "Conversation", convId.toString(),
                    Map.of("conversationId", convId, "text", text, "sender", "RULE_AGENT"));
            }
        }
    }

    private void handleToolObservation(UUID epoch, Map<String, Object> envelope, Map<String, Object> payload) {
        UUID missionId = UUID.fromString((String) envelope.get("missionId"));
        String toolName = (String) payload.get("toolName");
        sseHubService.publishEvent(epoch, "MissionChanged", "Mission", missionId.toString(),
            Map.of("missionId", missionId, "toolName", toolName, "status", "RUNNING"));
    }

    private void handleMissionCancelled(UUID epoch, UUID missionId) {
        missionRepository.markMissionCancelled(epoch, missionId);
        sseHubService.publishEvent(epoch, "MissionChanged", "Mission", missionId.toString(),
            Map.of("missionId", missionId, "status", "CANCELLED"));
    }

    private void handleMissionFailed(UUID epoch, UUID missionId, UUID analysisId, UUID caseId) {
        missionRepository.markMissionFailed(epoch, missionId);
        if (analysisId != null) {
            analysisRepository.failAnalysis(epoch, analysisId);
            sseHubService.publishEvent(epoch, "AnalysisChanged", "Analysis", analysisId.toString(),
                Map.of("analysisId", analysisId, "caseId", caseId, "state", "FAILED", "reason", "LLM_INVESTIGATION_FAILED"));
        }
        sseHubService.publishEvent(epoch, "MissionChanged", "Mission", missionId.toString(),
            Map.of("missionId", missionId, "status", "FAILED"));
    }

    private static UUID uuidOrNull(Map<String, Object> source, String key) {
        Object raw = source.get(key);
        return raw == null ? null : UUID.fromString(raw.toString());
    }
}
