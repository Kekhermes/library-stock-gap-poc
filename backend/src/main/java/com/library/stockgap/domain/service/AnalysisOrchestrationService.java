package com.library.stockgap.domain.service;
import com.library.stockgap.domain.model.*;
import com.library.stockgap.infrastructure.persistence.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant; import java.util.*;
@Service public class AnalysisOrchestrationService {
 private final CaseRepository cases; private final AnalysisRepository analyses; private final RuleRepository rules; private final MissionRepository missions; private final OutboxRepository outbox; private final DeterministicAnalysisService dmn; private final SseHubService sse;
 private final ConversationRepository conversations;
 public AnalysisOrchestrationService(CaseRepository cases,AnalysisRepository analyses,RuleRepository rules,MissionRepository missions,OutboxRepository outbox,DeterministicAnalysisService dmn,SseHubService sse){this(cases,analyses,rules,missions,outbox,dmn,sse,null);}
 @org.springframework.beans.factory.annotation.Autowired
 public AnalysisOrchestrationService(CaseRepository cases,AnalysisRepository analyses,RuleRepository rules,MissionRepository missions,OutboxRepository outbox,DeterministicAnalysisService dmn,SseHubService sse,ConversationRepository conversations){this.cases=cases;this.analyses=analyses;this.rules=rules;this.missions=missions;this.outbox=outbox;this.dmn=dmn;this.sse=sse;this.conversations=conversations;}
 @Transactional public UUID startAnalysis(UUID epoch,UUID caseId){ return startAnalysis(epoch,caseId,false); }
 @Transactional public UUID reassess(UUID epoch,UUID caseId){ rules.invalidatePendingProposals(epoch,caseId); return startAnalysis(epoch,caseId,true); }
 private UUID startAnalysis(UUID epoch,UUID caseId,boolean expertReview){
  CaseDossier d=cases.findCaseDossier(epoch,caseId).orElseThrow(()->new IllegalArgumentException("Dossier introuvable"));
  analyses.findActiveAnalysisId(epoch,caseId).ifPresent(old->{analyses.updateAnalysisStatus(epoch,old,"CANCELLED");missions.cancelOpenMissionsForAnalysis(epoch,old);});
  UUID sid=UUID.randomUUID(), aid=UUID.randomUUID(); int version=rules.getActiveRuleSetVersion(epoch);
  List<Map<String,Object>> notes=new ArrayList<>(d.operatorNotes());
  if(expertReview && conversations!=null && d.activeConversationId()!=null) for(var message:conversations.getMessages(epoch,d.activeConversationId())) {
   if("Admin".equals(message.senderRole())) notes.add(Map.of("noteId",message.messageId().toString(),"text",message.text(),"recordedAt",message.sentAt().toString(),"source","EXPERT_MESSAGE"));
  }
  EvidenceSnapshot snap=new EvidenceSnapshot(sid,caseId,d.copyId(),"1.0.0",d.inventoryReferenceTime(),Instant.now(),Map.of("caseId",caseId.toString(),"copyId",d.copyId().toString()),d.timeline(),notes,d.systemState(),d.observations(),d.sourceAvailability(),d.symptoms());
  analyses.insertSnapshot(epoch,snap); analyses.insertAnalysisRun(epoch,aid,caseId,sid,version);
    DeterministicAnalysisService.DeterministicEvaluationResult result;
    try { result=dmn.evaluate(snap,rules.getRulesForVersion(epoch,version));
    if(expertReview) {
     Map<String,Object> reviewTrace=new HashMap<>(result.trace());reviewTrace.put("expertReview",true);
     result=new DeterministicAnalysisService.DeterministicEvaluationResult(false,FallbackReason.INSUFFICIENT_EVIDENCE,result.findings(),reviewTrace);
    }
    }
    catch (RuntimeException failure) {
    analyses.failDeterministicAnalysis(epoch,aid,String.valueOf(failure.getMessage()));
     sse.publishEvent(epoch,"AnalysisChanged","Analysis",aid.toString(),Map.of("analysisId",aid,"caseId",caseId,"state","FAILED","reason","DMN_EVALUATION_FAILED","message",String.valueOf(failure.getMessage())));
     return aid;
    }
  if(result.fullyCovered()) { UUID did=UUID.randomUUID(); DiagnosticRecord diag=new DiagnosticRecord(did,aid,caseId,DiagnosticConclusion.fromFindings(result.findings()),result.findings(),List.of(),"Diagnostic déterministe complet établi par les règles DMN actives.",true,"DMN"); analyses.markDeterministicCompleted(epoch,aid,caseId,result.trace(),diag); sse.publishEvent(epoch,"AnalysisChanged","Analysis",aid.toString(),Map.of("analysisId",aid,"caseId",caseId,"state","COMPLETED","isDeterministic",true)); }
  else { analyses.markFallbackRequested(epoch,aid,result.fallbackReason(),result.trace()); queueAgent(epoch,aid,caseId,sid,version,result.fallbackReason()); }
  return aid;
 }
 private void queueAgent(UUID epoch,UUID aid,UUID caseId,UUID sid,int version,FallbackReason reason){UUID mid=UUID.randomUUID();missions.createMission(epoch,mid,"INVESTIGATION",aid,null,sid,version,null);Map<String,Object> e=new HashMap<>();e.put("eventId",UUID.randomUUID().toString());e.put("eventType","InvestigationRequested");e.put("schemaVersion","1.0.0");e.put("occurredAt",Instant.now().toString());e.put("datasetEpoch",epoch.toString());e.put("correlationId",aid.toString());e.put("causationId",caseId.toString());e.put("missionId",mid.toString());e.put("attempt",1);e.put("ruleSetVersion",version);e.put("evidenceSnapshotId",sid.toString());e.put("caseId",caseId.toString());e.put("analysisId",aid.toString());e.put("payload",Map.of("reason",reason.name()));outbox.enqueueEvent(epoch,UUID.fromString((String)e.get("eventId")),mid,"InvestigationRequested",e);sse.publishEvent(epoch,"AnalysisChanged","Analysis",aid.toString(),Map.of("analysisId",aid,"caseId",caseId,"state","AGENT_PENDING","fallbackReason",reason.name())); }
 @Transactional public UUID acceptDiagnostic(UUID epoch,UUID did,UUID cid){
  UUID id=analyses.acceptDiagnostic(epoch,did,cid);
  var diagnostic=analyses.findDiagnostic(epoch,did);
  if(diagnostic.isPresent() && !diagnostic.get().isDeterministic() && diagnostic.get().conclusion()==DiagnosticConclusion.SUPPORTED){
   var analysis=analyses.findAnalysis(epoch,diagnostic.get().analysisId());
  if(analysis.isPresent() && !(analysis.get().get("deterministicTrace") instanceof Map<?,?> trace && Boolean.TRUE.equals(trace.get("expertReview")))){
    UUID snapshot=(UUID)analysis.get().get("snapshotId"); int version=(int)analysis.get().get("ruleSetVersion"); UUID mission=UUID.randomUUID(); missions.createMission(epoch,mission,"RULE_SYNTHESIS",null,id,snapshot,version,null);
    Map<String,Object> e=new HashMap<>(); e.put("eventId",UUID.randomUUID().toString()); e.put("eventType","RuleSynthesisRequested"); e.put("schemaVersion","1.0.0"); e.put("occurredAt",Instant.now().toString()); e.put("datasetEpoch",epoch.toString()); e.put("correlationId",id.toString()); e.put("causationId",did.toString()); e.put("missionId",mission.toString()); e.put("attempt",1); e.put("ruleSetVersion",version); e.put("evidenceSnapshotId",snapshot.toString()); e.put("caseId",cid.toString()); e.put("payload",Map.of("originDiagnosticId",did.toString(),"originAcceptanceId",id.toString(),"diagnosticConclusion",diagnostic.get().conclusion().name())); outbox.enqueueEvent(epoch,UUID.fromString((String)e.get("eventId")),mission,"RuleSynthesisRequested",e); sse.publishEvent(epoch,"MissionChanged","Mission",mission.toString(),Map.of("missionId",mission,"kind","RULE_SYNTHESIS","status","QUEUED"));
   }
  }
  sse.publishEvent(epoch,"AnalysisChanged","Case",cid.toString(),Map.of("caseId",cid,"status","DIAGNOSTIC_ACCEPTE","acceptanceId",id));return id;
 }
 @Transactional public void cancelAnalysis(UUID epoch,UUID aid){var a=analyses.findAnalysis(epoch,aid);if(a.isEmpty())return;UUID cid=(UUID)a.get().get("caseId");analyses.updateAnalysisStatus(epoch,aid,"CANCELLED");missions.cancelOpenMissionsForAnalysis(epoch,aid);cases.updateCaseStatus(epoch,cid,CaseStatus.A_ANALYSER);}
}
