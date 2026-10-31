package com.library.stockgap.web;

import com.library.stockgap.domain.model.EvidenceSnapshot;
import com.library.stockgap.domain.service.DmnEngineService;
import com.library.stockgap.infrastructure.persistence.ActiveDatasetRepository;
import com.library.stockgap.infrastructure.persistence.AnalysisRepository;
import com.library.stockgap.infrastructure.persistence.MissionRepository;
import com.library.stockgap.infrastructure.persistence.RuleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/internal")
@Transactional
public class InternalWorkerController {

    private final ActiveDatasetRepository activeDatasetRepository;
    private final MissionRepository missionRepository;
    private final AnalysisRepository analysisRepository;
    private final RuleRepository ruleRepository;
    private final DmnEngineService dmnEngineService;
    private final com.library.stockgap.domain.service.RuleValidationPolicy rulePolicy;
    private final com.library.stockgap.domain.service.RuleDraftAssembler drafts;
    private final com.library.stockgap.domain.service.RuleReplayService replay;
    private final com.library.stockgap.domain.service.DeterministicAnalysisService deterministic;
    private final org.springframework.jdbc.core.JdbcTemplate jdbc;
    private final com.library.stockgap.infrastructure.persistence.ConversationRepository conversations;
    private final com.library.stockgap.infrastructure.persistence.CaseRepository cases;

    public InternalWorkerController(ActiveDatasetRepository activeDatasetRepository,
                                    MissionRepository missionRepository,
                                    AnalysisRepository analysisRepository,
                                    RuleRepository ruleRepository,
                                    DmnEngineService dmnEngineService,
                                    com.library.stockgap.domain.service.RuleValidationPolicy rulePolicy,
                                    com.library.stockgap.domain.service.RuleDraftAssembler drafts,
                                    com.library.stockgap.domain.service.RuleReplayService replay,
                                    com.library.stockgap.domain.service.DeterministicAnalysisService deterministic,
                                    org.springframework.jdbc.core.JdbcTemplate jdbc,
                                    com.library.stockgap.infrastructure.persistence.ConversationRepository conversations,
                                    com.library.stockgap.infrastructure.persistence.CaseRepository cases) {
        this.activeDatasetRepository = activeDatasetRepository;
        this.missionRepository = missionRepository;
        this.analysisRepository = analysisRepository;
        this.ruleRepository = ruleRepository;
        this.dmnEngineService = dmnEngineService;
        this.rulePolicy = rulePolicy;
        this.drafts = drafts; this.replay = replay; this.deterministic = deterministic; this.jdbc = jdbc; this.conversations = conversations; this.cases = cases;
    }

    private UUID getEpoch() {
        return activeDatasetRepository.requireActiveEpoch();
    }

    private Optional<EvidenceSnapshot> authorizedSnapshot(UUID epoch, UUID missionId, UUID snapshotId, UUID lease) {
        var context = missionRepository.activeContext(epoch, missionId, lease);
        if (!snapshotId.equals(context.get("snapshot_id"))) throw new SecurityException("Dossier de preuves non autorisé pour cette mission");
        return analysisRepository.findSnapshot(epoch, snapshotId);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<Map<String, Object>> forbidden(SecurityException failure) {
        return ResponseEntity.status(403).body(Map.of("error", "Mission, tentative ou dossier de preuves non autorisé"));
    }

    @PostMapping("/missions/{missionId}/heartbeat")
    public ResponseEntity<Map<String, Object>> heartbeat(@PathVariable UUID missionId,
            @RequestHeader("X-Mission-Lease") UUID lease, @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(missionRepository.heartbeat(getEpoch(), missionId, lease,
            ((Number) body.get("attempt")).intValue(), (String) body.get("model")));
    }

    @GetMapping("/missions/{missionId}/context")
    public ResponseEntity<Map<String, Object>> context(@PathVariable UUID missionId, @RequestHeader("X-Mission-Lease") UUID lease) {
        UUID epoch = getEpoch(); var mission = missionRepository.activeContext(epoch, missionId, lease);
        EvidenceSnapshot snapshot = analysisRepository.findSnapshot(epoch, (UUID) mission.get("snapshot_id")).orElseThrow();
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("snapshot", snapshot); result.put("facts", deterministic.facts(snapshot)); result.put("factTypes", rulePolicy.factTypes());
        result.put("ruleSetVersion", mission.get("rule_set_version"));
        result.put("rules", ruleRepository.getRulesForVersion(epoch, ((Number) mission.get("rule_set_version")).intValue()));
        if (mission.get("origin_acceptance_id") != null) {
            var ids = jdbc.queryForList("SELECT diagnostic_id FROM runtime.diagnostic_acceptance WHERE dataset_epoch = ? AND acceptance_id = ?", epoch, mission.get("origin_acceptance_id"));
            if (!ids.isEmpty()) result.put("acceptedDiagnostic", analysisRepository.findDiagnostic(epoch, (UUID) ids.getFirst().get("diagnostic_id")).orElseThrow());
        }
        var dossier = cases.findCaseDossier(epoch, snapshot.caseId()).orElseThrow();
        result.put("proposal", dossier.currentProposal());
        result.put("diagnostic", dossier.currentDiagnostic());
        result.put("messages", dossier.activeConversationId() == null ? List.of() : conversations.getMessages(epoch, dossier.activeConversationId()));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/missions/{missionId}/claim")
    public ResponseEntity<Map<String, Object>> claimMission(
            @PathVariable UUID missionId,
            @RequestBody(required = false) Map<String, Object> body) {
        UUID epoch = getEpoch();
        String workerId = body != null && body.containsKey("workerId") ? (String) body.get("workerId") : "python-worker-1";
        var res = missionRepository.claimMission(epoch, missionId, workerId);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/missions/{missionId}/snapshots/{snapshotId}")
    public ResponseEntity<EvidenceSnapshot> getSnapshot(
            @PathVariable UUID missionId,
            @PathVariable UUID snapshotId,
            @RequestHeader("X-Mission-Lease") UUID leaseToken) {
        UUID epoch = getEpoch();
        if (!missionRepository.canReadSnapshot(epoch, missionId, snapshotId, leaseToken)) {
            return ResponseEntity.status(403).build();
        }
        var snapOpt = authorizedSnapshot(epoch, missionId, snapshotId, leaseToken);
        return snapOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/missions/{missionId}/snapshots/{snapshotId}/source-events")
    public ResponseEntity<List<Map<String, Object>>> getSourceEvents(
            @PathVariable UUID missionId,
            @PathVariable UUID snapshotId, @RequestHeader("X-Mission-Lease") UUID leaseToken) {
        UUID epoch = getEpoch();
        var snapOpt = authorizedSnapshot(epoch, missionId, snapshotId, leaseToken);
        return snapOpt.map(s -> ResponseEntity.ok(s.sourceEvents())).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/missions/{missionId}/snapshots/{snapshotId}/operator-notes")
    public ResponseEntity<List<Map<String, Object>>> getOperatorNotes(
            @PathVariable UUID missionId,
            @PathVariable UUID snapshotId, @RequestHeader("X-Mission-Lease") UUID leaseToken) {
        UUID epoch = getEpoch();
        var snapOpt = authorizedSnapshot(epoch, missionId, snapshotId, leaseToken);
        return snapOpt.map(s -> ResponseEntity.ok(s.operatorNotes())).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/missions/{missionId}/snapshots/{snapshotId}/loans")
    public ResponseEntity<List<Map<String, Object>>> getLoans(
            @PathVariable UUID missionId,
            @PathVariable UUID snapshotId, @RequestHeader("X-Mission-Lease") UUID leaseToken) {
        UUID epoch = getEpoch();
        var snapOpt = authorizedSnapshot(epoch, missionId, snapshotId, leaseToken);
        if (snapOpt.isEmpty()) return ResponseEntity.notFound().build();
        List<Map<String, Object>> loans = snapOpt.get().sourceEvents().stream()
                .filter(e -> "LoanOpened".equals(e.get("eventType")) || "LoanClosed".equals(e.get("eventType")))
                .toList();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/missions/{missionId}/snapshots/{snapshotId}/returns")
    public ResponseEntity<List<Map<String, Object>>> getReturns(
            @PathVariable UUID missionId,
            @PathVariable UUID snapshotId, @RequestHeader("X-Mission-Lease") UUID leaseToken) {
        UUID epoch = getEpoch();
        var snapOpt = authorizedSnapshot(epoch, missionId, snapshotId, leaseToken);
        if (snapOpt.isEmpty()) return ResponseEntity.notFound().build();
        List<Map<String, Object>> returns = snapOpt.get().sourceEvents().stream()
                .filter(e -> "ReturnReceived".equals(e.get("eventType")))
                .toList();
        return ResponseEntity.ok(returns);
    }

    @GetMapping("/missions/{missionId}/snapshots/{snapshotId}/transfers")
    public ResponseEntity<List<Map<String, Object>>> getTransfers(
            @PathVariable UUID missionId,
            @PathVariable UUID snapshotId, @RequestHeader("X-Mission-Lease") UUID leaseToken) {
        UUID epoch = getEpoch();
        var snapOpt = authorizedSnapshot(epoch, missionId, snapshotId, leaseToken);
        if (snapOpt.isEmpty()) return ResponseEntity.notFound().build();
        List<Map<String, Object>> transfers = snapOpt.get().sourceEvents().stream()
                .filter(e -> "TransferDispatched".equals(e.get("eventType")) || "TransferReceived".equals(e.get("eventType")))
                .toList();
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/missions/{missionId}/snapshots/{snapshotId}/repairs")
    public ResponseEntity<List<Map<String, Object>>> getRepairs(
            @PathVariable UUID missionId,
            @PathVariable UUID snapshotId, @RequestHeader("X-Mission-Lease") UUID leaseToken) {
        UUID epoch = getEpoch();
        var snapOpt = authorizedSnapshot(epoch, missionId, snapshotId, leaseToken);
        if (snapOpt.isEmpty()) return ResponseEntity.notFound().build();
        List<Map<String, Object>> repairs = snapOpt.get().sourceEvents().stream()
                .filter(e -> "RepairStarted".equals(e.get("eventType")))
                .toList();
        return ResponseEntity.ok(repairs);
    }

    @GetMapping("/missions/{missionId}/snapshots/{snapshotId}/integrations")
    public ResponseEntity<List<Map<String, Object>>> getIntegrations(
            @PathVariable UUID missionId,
            @PathVariable UUID snapshotId, @RequestHeader("X-Mission-Lease") UUID leaseToken) {
        UUID epoch = getEpoch();
        var snapOpt = authorizedSnapshot(epoch, missionId, snapshotId, leaseToken);
        if (snapOpt.isEmpty()) return ResponseEntity.notFound().build();
        List<Map<String, Object>> integrations = snapOpt.get().sourceEvents().stream()
                .filter(e -> "IntegrationProcessed".equals(e.get("eventType")))
                .toList();
        return ResponseEntity.ok(integrations);
    }

    @GetMapping("/missions/{missionId}/rule-set")
    public ResponseEntity<Map<String, Object>> getRuleSet(@PathVariable UUID missionId, @RequestHeader("X-Mission-Lease") UUID leaseToken) {
        UUID epoch = getEpoch();
        int activeVer = ((Number) missionRepository.activeContext(epoch, missionId, leaseToken).get("rule_set_version")).intValue();
        var activeRules = ruleRepository.getRulesForVersion(epoch, activeVer);
        return ResponseEntity.ok(Map.of("ruleSetVersion", activeVer, "rules", activeRules));
    }

    @PostMapping("/missions/{missionId}/prevalidation")
    public ResponseEntity<Map<String, Object>> prevalidate(
            @PathVariable UUID missionId,
            @RequestHeader("X-Mission-Lease") UUID leaseToken,
            @RequestBody Map<String, Object> body) {
        UUID epoch = getEpoch();
        var mission = missionRepository.activeContext(epoch, missionId, leaseToken);
        if (body.get("draft") instanceof Map<?, ?> raw) {
            try {
                @SuppressWarnings("unchecked") Map<String, Object> draft = (Map<String, Object>) raw;
                String xml = drafts.assemble(draft);
                int version = ((Number) mission.get("rule_set_version")).intValue();
                if (version != ruleRepository.getActiveRuleSetVersion(epoch)) throw new IllegalArgumentException("Version de base périmée");
                EvidenceSnapshot origin = analysisRepository.findSnapshot(epoch, (UUID) mission.get("snapshot_id")).orElseThrow();
                var candidate = new com.library.stockgap.domain.model.RuleMetadata(UUID.randomUUID().toString(), 1, draft.get("name").toString(), "AGENT", "generated", "", "", "decision",
                    draft.get("causeCode").toString(), strings(draft.get("coveredSymptomKinds")), strings(draft.get("recommendations")), strings(draft.get("limitations")),
                    AnalysisRepository.sha256Hex(xml), xml, false, null, null);
                var before = ruleRepository.getRulesForVersion(epoch, version);
                List<com.library.stockgap.domain.model.RuleMetadata> after = new ArrayList<>(before);
                if ("REVISE".equals(draft.get("operation"))) after.removeIf(rule -> rule.ruleId().equals(draft.get("targetRuleId")));
                after.add(candidate);
                var result = replay.verify(candidate, before, after, origin);
                Map<String, Object> response = new LinkedHashMap<>();
                response.put("valid", result.passed()); response.put("publishable", false); response.put("dmnXml", xml);
                response.put("feedback", Map.of("positivePassed", result.positivePassed(), "positiveTested", result.positiveTested(), "negativePassed", result.negativePassed(),
                    "negativeTested", result.negativeTested(), "failures", result.conflicts(), "failedCases", result.results().stream().filter(row -> Boolean.FALSE.equals(row.get("passed"))).limit(5).toList()));
                return ResponseEntity.ok(response);
            } catch (Exception failure) { return ResponseEntity.ok(Map.of("valid", false, "publishable", false, "feedback", String.valueOf(failure.getMessage()))); }
        }
        String dmnXml = (String) body.get("dmnXml");
        try {
            rulePolicy.validate(dmnXml);
        } catch (IllegalArgumentException failure) {
            return ResponseEntity.ok(Map.of("valid", false, "publishable", false, "message", String.valueOf(failure.getMessage())));
        }
        boolean valid = dmnEngineService.validateDmnXml(dmnXml);
        return ResponseEntity.ok(Map.of(
            "valid", valid,
            "publishable", false, // Prevalidation is never publishable by itself
            "message", valid ? "DMN FEEL syntax valid" : "DMN compilation failed"
        ));
    }

    private List<String> strings(Object value) {
        if (!(value instanceof List<?> list)) throw new IllegalArgumentException("Liste de métadonnées requise");
        return list.stream().map(Object::toString).toList();
    }
}
