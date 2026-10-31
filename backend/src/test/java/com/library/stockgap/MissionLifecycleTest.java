package com.library.stockgap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.stockgap.domain.service.RuleGovernanceService;
import com.library.stockgap.domain.service.SseHubService;
import com.library.stockgap.infrastructure.kafka.AgentEventConsumer;
import com.library.stockgap.infrastructure.persistence.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.library.stockgap.domain.model.*;
import java.time.Instant;
import java.util.List;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MissionLifecycleTest {
    @Test
    @EnabledIfEnvironmentVariable(named="STOCKGAP_DB_TESTS",matches="true")
    void sqlAttemptsDuplicatesExpiryAndExpertTurnsAreRealAndRolledBack() {
        var source=new DriverManagerDataSource("jdbc:postgresql://127.0.0.1:5433/stockgap","postgres","postgres");
        var transaction=new TransactionTemplate(new DataSourceTransactionManager(source));
        JdbcTemplate jdbc=new JdbcTemplate(source);ObjectMapper mapper=new ObjectMapper().findAndRegisterModules();
        UUID epoch=new ActiveDatasetRepository(jdbc).requireActiveEpoch();
        int initial=jdbc.queryForObject("SELECT count(*) FROM runtime.agent_mission WHERE dataset_epoch = ?",Integer.class,epoch);
        transaction.executeWithoutResult(state->{try {
            var cases=new CaseRepository(jdbc,mapper);var analyses=new AnalysisRepository(jdbc,mapper);var missions=new MissionRepository(jdbc,mapper);var inbox=new InboxRepository(jdbc,mapper);
            var dossier=cases.findCaseDossier(epoch,(UUID)cases.findCases(epoch,null,null,null,0,1).getFirst().get("caseId")).orElseThrow();
            UUID snapshot=UUID.randomUUID(),analysis=UUID.randomUUID(),mission=UUID.randomUUID();
            analyses.insertSnapshot(epoch,new EvidenceSnapshot(snapshot,dossier.caseId(),dossier.copyId(),"1.0.0",dossier.inventoryReferenceTime(),Instant.now(),Map.of("copyId",dossier.copyId().toString()),dossier.timeline(),dossier.operatorNotes(),dossier.systemState(),dossier.observations(),dossier.sourceAvailability(),dossier.symptoms()));
            analyses.insertAnalysisRun(epoch,analysis,dossier.caseId(),snapshot,1);
            analyses.markFallbackRequested(epoch,analysis,FallbackReason.NO_RULE_MATCH,Map.of());
            missions.createMission(epoch,mission,"INVESTIGATION",analysis,null,snapshot,1,null);
            var claimed=missions.claimMission(epoch,mission,"fixture-worker");UUID lease=(UUID)claimed.get("leaseToken");
            assertTrue(missions.canReadSnapshot(epoch,mission,snapshot,lease));
            assertFalse(missions.canReadSnapshot(UUID.randomUUID(),mission,snapshot,lease));
            assertFalse(missions.canReadSnapshot(epoch,mission,UUID.randomUUID(),lease));
            assertFalse(missions.lockActiveEvent(epoch,mission,snapshot,lease,1,2,1,analysis,dossier.caseId(),"ToolObservationRecorded"));
            Map<String,Object> envelope=new java.util.LinkedHashMap<>(event("ToolObservationRecorded"));
            envelope.put("datasetEpoch",epoch.toString());envelope.put("missionId",mission.toString());envelope.put("evidenceSnapshotId",snapshot.toString());envelope.put("analysisId",analysis.toString());envelope.put("caseId",dossier.caseId().toString());
            envelope.put("payload",Map.of("leaseToken",lease.toString(),"fencingToken",1,"toolName","fixture-read","observations",List.of("Fixture SQL, pas d'appel LLM")));
            var consumer=new AgentEventConsumer(inbox,analyses,new RuleRepository(jdbc,mapper),missions,new ConversationRepository(jdbc,mapper),mock(RuleGovernanceService.class),mock(SseHubService.class),mapper);
            consumer.consumeEvent(mapper.writeValueAsString(envelope));consumer.consumeEvent(mapper.writeValueAsString(envelope));
            assertEquals(1,jdbc.queryForObject("SELECT count(*) FROM runtime.inbox_event WHERE event_id = ?",Integer.class,UUID.fromString(envelope.get("eventId").toString())));
            assertEquals(1,jdbc.queryForObject("SELECT count(*) FROM runtime.agent_trace WHERE mission_id = ?",Integer.class,mission));
            jdbc.update("UPDATE runtime.mission_attempt SET claimed_at = now()-interval '180 seconds', heartbeat_at = now()-interval '120 seconds', lease_expires_at = now()-interval '30 seconds' WHERE dataset_epoch = ? AND mission_id = ?",epoch,mission);
            missions.recoverExpired();var retry=missions.claimMission(epoch,mission,"fixture-retry");
            assertEquals(2,retry.get("attempt"));assertFalse(missions.canReadSnapshot(epoch,mission,snapshot,lease));
            assertTrue(missions.canReadSnapshot(epoch,mission,snapshot,(UUID)retry.get("leaseToken")));
            missions.cancelOpenMissionsForAnalysis(epoch,analysis);
            assertFalse(missions.canReadSnapshot(epoch,mission,snapshot,(UUID)retry.get("leaseToken")));
            var conversations=new ConversationRepository(jdbc,mapper);UUID conversation=conversations.getOrCreateConversationForCase(epoch,dossier.caseId());
            UUID turn=conversations.startTurn(epoch,conversation,"INVESTIGATOR","Fixture : expliquer les preuves");
            assertThrows(IllegalStateException.class,()->conversations.startTurn(epoch,conversation,"RULE_AGENT","Second tour concurrent interdit"));
            UUID expert=UUID.randomUUID();missions.createMission(epoch,expert,"EXPERT_TURN",null,null,snapshot,1,turn);
            var expertClaim=missions.claimMission(epoch,expert,"fixture-expert");UUID expertLease=(UUID)expertClaim.get("leaseToken");
            var bound=missions.activeContext(epoch,expert,expertLease);assertEquals(conversation,bound.get("conversation_id"));assertEquals("INVESTIGATOR",bound.get("agent_role"));
            Map<String,Object> reply=new java.util.LinkedHashMap<>(event("TextChunkProduced"));
            reply.put("datasetEpoch",epoch.toString());reply.put("missionId",expert.toString());reply.put("evidenceSnapshotId",snapshot.toString());reply.put("caseId",dossier.caseId().toString());reply.put("conversationId",conversation.toString());reply.put("turnId",turn.toString());
            reply.put("payload",Map.of("leaseToken",expertLease.toString(),"fencingToken",1,"senderRole","INVESTIGATOR","text","Réponse de fixture SQL, pas une génération LLM","isFinal",true,"references",List.of()));
            consumer.consumeEvent(mapper.writeValueAsString(reply));
            assertEquals(2,conversations.getMessages(epoch,conversation).size());
            assertEquals("COMPLETED",jdbc.queryForObject("SELECT status FROM runtime.conversation_turn WHERE dataset_epoch = ? AND turn_id = ?",String.class,epoch,turn));
                    UUID cancelled=UUID.randomUUID();UUID nextTurn=conversations.startTurn(epoch,conversation,"INVESTIGATOR","Annulation de fixture");
                    missions.createMission(epoch,cancelled,"EXPERT_TURN",null,null,snapshot,1,nextTurn);
                    missions.cancel(epoch,cancelled);assertEquals("CANCELLED",missions.status(epoch,cancelled).get("status"));
                    assertEquals("TERMINAL",missions.claimMission(epoch,cancelled,"fixture").get("status"));
        } catch(Exception error){throw new IllegalStateException("Recette M4 interrompue",error);}finally{state.setRollbackOnly();}});
        assertEquals(initial,jdbc.queryForObject("SELECT count(*) FROM runtime.agent_mission WHERE dataset_epoch = ?",Integer.class,epoch));
    }
    @Test
    void lateProposalIsIgnoredBeforeAnyBusinessWrite() throws Exception {
        InboxRepository inbox = mock(InboxRepository.class);
        RuleRepository rules = mock(RuleRepository.class);
        MissionRepository missions = mock(MissionRepository.class);
        AgentEventConsumer consumer = consumer(inbox, rules, missions);
        consumer.consumeEvent(new ObjectMapper().writeValueAsString(event("RuleChangeProposed")));
        verify(missions).lockActiveEvent(any(), any(), any(), any(), eq(1), eq(1), eq(1), isNull(), any(), eq("RuleChangeProposed"));
        verify(inbox).recordLate(any(),any(),any(),eq(1),eq(1L),eq("RuleChangeProposed"));
        verifyNoInteractions(rules);
    }

    @Test
    void databaseFailureIsPropagatedSoTheListenerTransactionCanRollBack() throws Exception {
        InboxRepository inbox = mock(InboxRepository.class);
        RuleRepository rules = mock(RuleRepository.class);
        MissionRepository missions = mock(MissionRepository.class);
        when(missions.lockActiveEvent(any(), any(), any(), any(), anyInt(), anyInt(), anyInt(), any(), any(), anyString())).thenReturn(true);
        when(inbox.recordAndCheckIfNew(any(), any(), any(), anyInt(), anyLong(), anyString(), anyMap()))
            .thenThrow(new IllegalStateException("database unavailable"));
        assertThrows(IllegalStateException.class, () -> consumer(inbox, rules, missions)
            .consumeEvent(new ObjectMapper().writeValueAsString(event("RuleChangeProposed"))));
        verifyNoInteractions(rules);
    }

    private AgentEventConsumer consumer(InboxRepository inbox, RuleRepository rules, MissionRepository missions) {
        return new AgentEventConsumer(inbox, mock(AnalysisRepository.class), rules, missions, mock(ConversationRepository.class),
            mock(RuleGovernanceService.class), mock(SseHubService.class), new ObjectMapper());
    }

    private Map<String, Object> event(String type) {
        return Map.of("eventId", UUID.randomUUID().toString(), "eventType", type, "datasetEpoch", UUID.randomUUID().toString(),
            "missionId", UUID.randomUUID().toString(), "evidenceSnapshotId", UUID.randomUUID().toString(), "caseId", UUID.randomUUID().toString(),
            "attempt", 1, "sequence", 1, "ruleSetVersion", 1,
            "payload", Map.of("leaseToken", UUID.randomUUID().toString(), "fencingToken", 1));
    }
}