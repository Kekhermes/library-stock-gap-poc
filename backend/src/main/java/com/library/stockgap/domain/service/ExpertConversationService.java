package com.library.stockgap.domain.service;
import com.library.stockgap.domain.model.EvidenceSnapshot;
import com.library.stockgap.infrastructure.persistence.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.*;

@Service
public class ExpertConversationService {
    private final CaseRepository cases;
    private final AnalysisRepository analyses;
    private final ConversationRepository conversations;
    private final MissionRepository missions;
    private final RuleRepository rules;
    private final OutboxRepository outbox;
    private final JdbcTemplate jdbc;
    public ExpertConversationService(CaseRepository cases, AnalysisRepository analyses, ConversationRepository conversations,
            MissionRepository missions, RuleRepository rules, OutboxRepository outbox, JdbcTemplate jdbc) {
        this.cases=cases; this.analyses=analyses; this.conversations=conversations; this.missions=missions; this.rules=rules; this.outbox=outbox; this.jdbc=jdbc;
    }
    @Transactional
    public Map<String,Object> send(UUID epoch, UUID caseId, String role, String text) {
        var dossier=cases.findCaseDossier(epoch,caseId).orElseThrow();
        UUID conversation=conversations.getOrCreateConversationForCase(epoch,caseId);
        UUID turn=conversations.startTurn(epoch,conversation,role,text);
        UUID snapshotId;
        if (!dossier.analyses().isEmpty()) snapshotId=(UUID)analyses.findAnalysis(epoch,(UUID)dossier.analyses().getFirst().get("analysisId")).orElseThrow().get("snapshotId");
        else {
            snapshotId=UUID.randomUUID();
            analyses.insertSnapshot(epoch,new EvidenceSnapshot(snapshotId,caseId,dossier.copyId(),"1.0.0",dossier.inventoryReferenceTime(),Instant.now(),
                Map.of("copyId",dossier.copyId().toString()),dossier.timeline(),dossier.operatorNotes(),dossier.systemState(),dossier.observations(),dossier.sourceAvailability(),dossier.symptoms()));
        }
        UUID acceptance=(UUID)jdbc.queryForMap("SELECT current_acceptance_id FROM runtime.gap_case WHERE dataset_epoch = ? AND case_id = ?",epoch,caseId).get("current_acceptance_id");
        int version=rules.getActiveRuleSetVersion(epoch);
        UUID mission=UUID.randomUUID();
        missions.createMission(epoch,mission,"EXPERT_TURN",null,acceptance,snapshotId,version,turn);
        Map<String,Object> event=new LinkedHashMap<>();
        event.put("eventId",UUID.randomUUID().toString());event.put("eventType","ExpertTurnRequested");event.put("schemaVersion","1.0.0");
        event.put("occurredAt",Instant.now().toString());event.put("datasetEpoch",epoch.toString());event.put("missionId",mission.toString());event.put("attempt",1);
        event.put("caseId",caseId.toString());event.put("conversationId",conversation.toString());event.put("turnId",turn.toString());
        event.put("correlationId",conversation.toString());event.put("causationId",turn.toString());event.put("evidenceSnapshotId",snapshotId.toString());
        event.put("ruleSetVersion",version);event.put("payload",Map.of("role",role));
        outbox.enqueueEvent(epoch,UUID.fromString(event.get("eventId").toString()),mission,"ExpertTurnRequested",event);
        return Map.of("conversationId",conversation,"turnId",turn,"missionId",mission,"status","QUEUED","role",role);
    }
}