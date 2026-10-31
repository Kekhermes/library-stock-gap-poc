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

@RestController
@RequestMapping("/internal")
public class InternalWorkerController {

    private final ActiveDatasetRepository activeDatasetRepository;
    private final MissionRepository missionRepository;
    private final AnalysisRepository analysisRepository;
    private final RuleRepository ruleRepository;
    private final DmnEngineService dmnEngineService;
    private final com.library.stockgap.domain.service.RuleValidationPolicy rulePolicy;

    public InternalWorkerController(ActiveDatasetRepository activeDatasetRepository,
                                    MissionRepository missionRepository,
                                    AnalysisRepository analysisRepository,
                                    RuleRepository ruleRepository,
                                    DmnEngineService dmnEngineService,
                                    com.library.stockgap.domain.service.RuleValidationPolicy rulePolicy) {
        this.activeDatasetRepository = activeDatasetRepository;
        this.missionRepository = missionRepository;
        this.analysisRepository = analysisRepository;
        this.ruleRepository = ruleRepository;
        this.dmnEngineService = dmnEngineService;
        this.rulePolicy = rulePolicy;
    }

    private UUID getEpoch() {
        return activeDatasetRepository.requireActiveEpoch();
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
        var snapOpt = analysisRepository.findSnapshot(epoch, snapshotId);
        return snapOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/missions/{missionId}/snapshots/{snapshotId}/source-events")
    public ResponseEntity<List<Map<String, Object>>> getSourceEvents(
            @PathVariable UUID missionId,
            @PathVariable UUID snapshotId) {
        UUID epoch = getEpoch();
        var snapOpt = analysisRepository.findSnapshot(epoch, snapshotId);
        return snapOpt.map(s -> ResponseEntity.ok(s.sourceEvents())).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/missions/{missionId}/snapshots/{snapshotId}/operator-notes")
    public ResponseEntity<List<Map<String, Object>>> getOperatorNotes(
            @PathVariable UUID missionId,
            @PathVariable UUID snapshotId) {
        UUID epoch = getEpoch();
        var snapOpt = analysisRepository.findSnapshot(epoch, snapshotId);
        return snapOpt.map(s -> ResponseEntity.ok(s.operatorNotes())).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/missions/{missionId}/snapshots/{snapshotId}/loans")
    public ResponseEntity<List<Map<String, Object>>> getLoans(
            @PathVariable UUID missionId,
            @PathVariable UUID snapshotId) {
        UUID epoch = getEpoch();
        var snapOpt = analysisRepository.findSnapshot(epoch, snapshotId);
        if (snapOpt.isEmpty()) return ResponseEntity.notFound().build();
        List<Map<String, Object>> loans = snapOpt.get().sourceEvents().stream()
                .filter(e -> "LoanOpened".equals(e.get("eventType")) || "LoanClosed".equals(e.get("eventType")))
                .toList();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/missions/{missionId}/snapshots/{snapshotId}/returns")
    public ResponseEntity<List<Map<String, Object>>> getReturns(
            @PathVariable UUID missionId,
            @PathVariable UUID snapshotId) {
        UUID epoch = getEpoch();
        var snapOpt = analysisRepository.findSnapshot(epoch, snapshotId);
        if (snapOpt.isEmpty()) return ResponseEntity.notFound().build();
        List<Map<String, Object>> returns = snapOpt.get().sourceEvents().stream()
                .filter(e -> "ReturnReceived".equals(e.get("eventType")))
                .toList();
        return ResponseEntity.ok(returns);
    }

    @GetMapping("/missions/{missionId}/snapshots/{snapshotId}/transfers")
    public ResponseEntity<List<Map<String, Object>>> getTransfers(
            @PathVariable UUID missionId,
            @PathVariable UUID snapshotId) {
        UUID epoch = getEpoch();
        var snapOpt = analysisRepository.findSnapshot(epoch, snapshotId);
        if (snapOpt.isEmpty()) return ResponseEntity.notFound().build();
        List<Map<String, Object>> transfers = snapOpt.get().sourceEvents().stream()
                .filter(e -> "TransferDispatched".equals(e.get("eventType")) || "TransferReceived".equals(e.get("eventType")))
                .toList();
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/missions/{missionId}/snapshots/{snapshotId}/repairs")
    public ResponseEntity<List<Map<String, Object>>> getRepairs(
            @PathVariable UUID missionId,
            @PathVariable UUID snapshotId) {
        UUID epoch = getEpoch();
        var snapOpt = analysisRepository.findSnapshot(epoch, snapshotId);
        if (snapOpt.isEmpty()) return ResponseEntity.notFound().build();
        List<Map<String, Object>> repairs = snapOpt.get().sourceEvents().stream()
                .filter(e -> "RepairStarted".equals(e.get("eventType")))
                .toList();
        return ResponseEntity.ok(repairs);
    }

    @GetMapping("/missions/{missionId}/snapshots/{snapshotId}/integrations")
    public ResponseEntity<List<Map<String, Object>>> getIntegrations(
            @PathVariable UUID missionId,
            @PathVariable UUID snapshotId) {
        UUID epoch = getEpoch();
        var snapOpt = analysisRepository.findSnapshot(epoch, snapshotId);
        if (snapOpt.isEmpty()) return ResponseEntity.notFound().build();
        List<Map<String, Object>> integrations = snapOpt.get().sourceEvents().stream()
                .filter(e -> "IntegrationProcessed".equals(e.get("eventType")))
                .toList();
        return ResponseEntity.ok(integrations);
    }

    @GetMapping("/missions/{missionId}/rule-set")
    public ResponseEntity<Map<String, Object>> getRuleSet(@PathVariable UUID missionId) {
        UUID epoch = getEpoch();
        int activeVer = ruleRepository.getActiveRuleSetVersion(epoch);
        var activeRules = ruleRepository.getActiveRules(epoch);
        return ResponseEntity.ok(Map.of("ruleSetVersion", activeVer, "rules", activeRules));
    }

    @PostMapping("/missions/{missionId}/prevalidation")
    public ResponseEntity<Map<String, Object>> prevalidate(
            @PathVariable UUID missionId,
            @RequestBody Map<String, Object> body) {
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
}
