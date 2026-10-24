package com.library.stockgap.web;

import com.library.stockgap.domain.model.CaseDossier;
import com.library.stockgap.domain.model.ChatMessageRecord;
import com.library.stockgap.domain.model.DiagnosticRecord;
import com.library.stockgap.domain.model.EvidenceSnapshot;
import com.library.stockgap.domain.service.AnalysisOrchestrationService;
import com.library.stockgap.domain.service.DatasetSeedService;
import com.library.stockgap.domain.service.RuleGovernanceService;
import com.library.stockgap.infrastructure.persistence.ActiveDatasetRepository;
import com.library.stockgap.infrastructure.persistence.AnalysisRepository;
import com.library.stockgap.infrastructure.persistence.CaseRepository;
import com.library.stockgap.infrastructure.persistence.ConversationRepository;
import com.library.stockgap.infrastructure.persistence.RuleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class AdminApiController {

    private final ActiveDatasetRepository activeDatasetRepository;
    private final CaseRepository caseRepository;
    private final AnalysisRepository analysisRepository;
    private final RuleRepository ruleRepository;
    private final ConversationRepository conversationRepository;
    private final AnalysisOrchestrationService analysisOrchestrationService;
    private final RuleGovernanceService ruleGovernanceService;
    private final DatasetSeedService datasetSeedService;

    public AdminApiController(ActiveDatasetRepository activeDatasetRepository,
                              CaseRepository caseRepository,
                              AnalysisRepository analysisRepository,
                              RuleRepository ruleRepository,
                              ConversationRepository conversationRepository,
                              AnalysisOrchestrationService analysisOrchestrationService,
                              RuleGovernanceService ruleGovernanceService,
                              DatasetSeedService datasetSeedService) {
        this.activeDatasetRepository = activeDatasetRepository;
        this.caseRepository = caseRepository;
        this.analysisRepository = analysisRepository;
        this.ruleRepository = ruleRepository;
        this.conversationRepository = conversationRepository;
        this.analysisOrchestrationService = analysisOrchestrationService;
        this.ruleGovernanceService = ruleGovernanceService;
        this.datasetSeedService = datasetSeedService;
    }

    private UUID getEpoch() {
        return activeDatasetRepository.requireActiveEpoch();
    }

    @GetMapping("/overview")
    public ResponseEntity<Map<String, Object>> getOverview() {
        UUID epoch = getEpoch();
        int totalCases = caseRepository.countCases(epoch, null, null, null);
        int aAnalyser = caseRepository.countCases(epoch, null, "A_ANALYSER", null);
        int enAnalyse = caseRepository.countCases(epoch, null, "EN_ANALYSE", null);
        int aRevoir = caseRepository.countCases(epoch, null, "A_REVOIR", null);
        int acceptes = caseRepository.countCases(epoch, null, "DIAGNOSTIC_ACCEPTE", null);
        int activeRuleVer = ruleRepository.getActiveRuleSetVersion(epoch);
        var activeRules = ruleRepository.getActiveRules(epoch);

        return ResponseEntity.ok(Map.of(
            "datasetEpoch", epoch,
            "totalCases", totalCases,
            "pendingCases", aAnalyser,
            "runningAnalyses", enAnalyse,
            "waitingReview", aRevoir,
            "acceptedDiagnostics", acceptes,
            "activeRuleSetVersion", activeRuleVer,
            "activeRulesCount", activeRules.size(),
            "activeRules", activeRules
        ));
    }

    @GetMapping("/local-status")
    public ResponseEntity<Map<String, Object>> getLocalStatus() {
        UUID epoch = getEpoch();
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "activeEpoch", epoch,
            "database", "PostgreSQL 18 connected",
            "engine", "Apache KIE 10.2.0 (DMN 1.5)",
            "kafkaEnabled", true
        ));
    }

    @GetMapping("/cases")
    public ResponseEntity<Map<String, Object>> listCases(
            @RequestParam(required = false) String siteId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String symptomKind,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        UUID epoch = getEpoch();
        var cases = caseRepository.findCases(epoch, siteId, status, symptomKind, page, size);
        int total = caseRepository.countCases(epoch, siteId, status, symptomKind);

        return ResponseEntity.ok(Map.of(
            "items", cases,
            "page", page,
            "size", size,
            "total", total,
            "totalPages", (int) Math.ceil((double) total / size)
        ));
    }

    @GetMapping("/cases/{caseId}")
    public ResponseEntity<CaseDossier> getCaseDossier(@PathVariable UUID caseId) {
        UUID epoch = getEpoch();
        var dossierOpt = caseRepository.findCaseDossier(epoch, caseId);
        return dossierOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/cases/{caseId}/analyses")
    public ResponseEntity<Map<String, Object>> startAnalysis(@PathVariable UUID caseId) {
        UUID epoch = getEpoch();
        try {
            UUID analysisId = analysisOrchestrationService.startAnalysis(epoch, caseId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                "analysisId", analysisId,
                "caseId", caseId,
                "status", "RUNNING"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/analyses/{analysisId}")
    public ResponseEntity<Map<String, Object>> getAnalysis(@PathVariable UUID analysisId) {
        UUID epoch = getEpoch();
        var analysisOpt = analysisRepository.findAnalysis(epoch, analysisId);
        return analysisOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/analyses/{analysisId}/cancellation")
    public ResponseEntity<Void> cancelAnalysis(@PathVariable UUID analysisId) {
        UUID epoch = getEpoch();
        analysisOrchestrationService.cancelAnalysis(epoch, analysisId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/analyses/{analysisId}/snapshot")
    public ResponseEntity<EvidenceSnapshot> getAnalysisSnapshot(@PathVariable UUID analysisId) {
        UUID epoch = getEpoch();
        var analysisOpt = analysisRepository.findAnalysis(epoch, analysisId);
        if (analysisOpt.isEmpty()) return ResponseEntity.notFound().build();
        UUID snapshotId = (UUID) analysisOpt.get().get("snapshotId");
        var snapshotOpt = analysisRepository.findSnapshot(epoch, snapshotId);
        return snapshotOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/analyses/{analysisId}/diagnostic")
    public ResponseEntity<DiagnosticRecord> getAnalysisDiagnostic(@PathVariable UUID analysisId) {
        UUID epoch = getEpoch();
        var analysisOpt = analysisRepository.findAnalysis(epoch, analysisId);
        if (analysisOpt.isEmpty()) return ResponseEntity.notFound().build();
        UUID caseId = (UUID) analysisOpt.get().get("caseId");
        var dossier = caseRepository.findCaseDossier(epoch, caseId);
        if (dossier.isPresent() && dossier.get().currentDiagnostic() != null) {
            UUID diagId = (UUID) dossier.get().currentDiagnostic().get("diagnosticId");
            return analysisRepository.findDiagnostic(epoch, diagId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/diagnostics/{diagnosticId}/acceptance")
    public ResponseEntity<Map<String, Object>> acceptDiagnostic(
            @PathVariable UUID diagnosticId,
            @RequestBody Map<String, Object> body) {
        UUID epoch = getEpoch();
        UUID caseId = UUID.fromString((String) body.get("caseId"));
        UUID acceptanceId = analysisOrchestrationService.acceptDiagnostic(epoch, diagnosticId, caseId);
        return ResponseEntity.ok(Map.of("acceptanceId", acceptanceId, "status", "DIAGNOSTIC_ACCEPTE"));
    }

    @GetMapping("/rules")
    public ResponseEntity<Map<String, Object>> listRules() {
        UUID epoch = getEpoch();
        var rules = ruleRepository.listRules(epoch);
        int activeVer = ruleRepository.getActiveRuleSetVersion(epoch);
        return ResponseEntity.ok(Map.of(
            "activeRuleSetVersion", activeVer,
            "rules", rules
        ));
    }

    @GetMapping("/rules/{ruleId}")
    public ResponseEntity<Map<String, Object>> getRule(@PathVariable String ruleId) {
        UUID epoch = getEpoch();
        var ruleOpt = ruleRepository.getRuleVersion(epoch, ruleId, 1);
        return ruleOpt.<ResponseEntity<Map<String, Object>>>map(r -> ResponseEntity.ok(Map.<String, Object>of("rule", r)))
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/rules/{ruleId}/retirement")
    public ResponseEntity<Map<String, Object>> retireRule(@PathVariable String ruleId) {
        UUID epoch = getEpoch();
        int newVersion = ruleGovernanceService.retireRule(epoch, ruleId);
        return ResponseEntity.ok(Map.of("ruleId", ruleId, "status", "RETIRED", "newRuleSetVersion", newVersion));
    }

    @GetMapping("/proposals")
    public ResponseEntity<List<Map<String, Object>>> listProposals() {
        UUID epoch = getEpoch();
        return ResponseEntity.ok(ruleRepository.listProposals(epoch));
    }

    @PostMapping("/proposals/{proposalId}/validation")
    public ResponseEntity<Map<String, Object>> validateProposal(
            @PathVariable UUID proposalId,
            @RequestBody Map<String, Object> body) {
        UUID epoch = getEpoch();
        int revision = ((Number) body.getOrDefault("revision", 1)).intValue();
        String dmnXml = (String) body.get("dmnXml");
        var report = ruleGovernanceService.validateProposal(epoch, proposalId, revision, dmnXml);
        return ResponseEntity.ok(Map.of("report", report));
    }

    @PostMapping("/proposals/{proposalId}/approval")
    public ResponseEntity<Map<String, Object>> approveProposal(
            @PathVariable UUID proposalId,
            @RequestBody Map<String, Object> body) {
        UUID epoch = getEpoch();
        int revision = ((Number) body.getOrDefault("revision", 1)).intValue();
        UUID reportId = UUID.fromString((String) body.get("reportId"));
        int newVer = ruleGovernanceService.approveAndPublish(epoch, proposalId, revision, reportId);
        return ResponseEntity.ok(Map.of("proposalId", proposalId, "status", "PUBLISHED", "newRuleSetVersion", newVer));
    }

    @GetMapping("/publications")
    public ResponseEntity<List<Map<String, Object>>> listPublications() {
        UUID epoch = getEpoch();
        return ResponseEntity.ok(ruleRepository.listPublications(epoch));
    }

    @GetMapping("/conversations/{conversationId}/messages")
    public ResponseEntity<List<ChatMessageRecord>> getMessages(@PathVariable UUID conversationId) {
        UUID epoch = getEpoch();
        return ResponseEntity.ok(conversationRepository.getMessages(epoch, conversationId));
    }

    @PostMapping("/conversations/{conversationId}/messages")
    public ResponseEntity<ChatMessageRecord> sendMessage(
            @PathVariable UUID conversationId,
            @RequestBody Map<String, Object> body) {
        UUID epoch = getEpoch();
        String text = (String) body.get("text");
        String role = (String) body.getOrDefault("role", "Admin");
        var msg = conversationRepository.addMessage(epoch, conversationId, role, text, Collections.emptyList());
        return ResponseEntity.ok(msg);
    }

    @PostMapping("/system/reset")
    public ResponseEntity<Map<String, Object>> resetSystem() {
        UUID newEpoch = datasetSeedService.seedDemoDataset();
        return ResponseEntity.ok(Map.of("status", "RESET_SUCCESS", "newEpoch", newEpoch));
    }
}
