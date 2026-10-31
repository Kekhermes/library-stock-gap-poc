package com.library.stockgap.domain.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.stockgap.domain.model.EvidenceSnapshot;
import com.library.stockgap.infrastructure.persistence.ActiveDatasetRepository;
import com.library.stockgap.infrastructure.persistence.CaseRepository;
import com.library.stockgap.infrastructure.persistence.RuleRepository;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DemoVerificationService {
    private final ActiveDatasetRepository datasets;
    private final CaseRepository cases;
    private final RuleRepository rules;
    private final JdbcTemplate jdbc;
    private final ObjectMapper mapper;
    private final ResourceLoader resources;
    private final DeterministicAnalysisService deterministic;

    public DemoVerificationService(ActiveDatasetRepository datasets, CaseRepository cases, RuleRepository rules,
                                   JdbcTemplate jdbc, ObjectMapper mapper, ResourceLoader resources,
                                   DeterministicAnalysisService deterministic) {
        this.datasets = datasets;
        this.cases = cases;
        this.rules = rules;
        this.jdbc = jdbc;
        this.mapper = mapper;
        this.resources = resources;
        this.deterministic = deterministic;
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public Map<String, Object> verify() throws IOException {
        UUID epoch = datasets.requireActiveEpoch();
        JsonNode manifest;
        try (InputStream input = resources.getResource("classpath:seed/demo-manifest.json").getInputStream()) {
            manifest = mapper.readTree(input);
        }
        var imported = jdbc.queryForMap(
            "SELECT d.runtime_sha256, d.generator_version FROM runtime.dataset d " +
            "JOIN runtime.dataset_epoch e ON e.dataset_id = d.dataset_id WHERE e.dataset_epoch = ?", epoch);
        int total = cases.countCases(epoch, null, null, null);
        int virgin = cases.countCases(epoch, null, "A_ANALYSER", null);
        int copies = count(epoch, "copy");
        int analyses = count(epoch, "analysis_run");
        int missions = count(epoch, "agent_mission");
        int conversations = count(epoch, "expert_conversation");
        int proposals = count(epoch, "rule_change_proposal");
        var activeRules = rules.getActiveRules(epoch);
        int version = rules.getActiveRuleSetVersion(epoch);
        boolean bootstrapOnly = version == 1 && activeRules.size() == 1
            && "R001".equals(activeRules.getFirst().ruleId()) && activeRules.getFirst().ruleVersion() == 1;
        int covered = 0;
        int uncovered = 0;
        List<Map<String, Object>> coverage = new ArrayList<>();
        boolean evaluated = total == 10 && bootstrapOnly;
        if (evaluated) {
            for (var item : cases.findCases(epoch, null, null, null, 0, 10)) {
                UUID caseId = UUID.fromString(item.get("caseId").toString());
                var dossier = cases.findCaseDossier(epoch, caseId).orElseThrow();
                var evidence = new EvidenceSnapshot(UUID.randomUUID(), caseId, dossier.copyId(), "1.0.0",
                    dossier.inventoryReferenceTime(), Instant.now(), Map.of(), dossier.timeline(), dossier.operatorNotes(),
                    dossier.systemState(), dossier.observations(), dossier.sourceAvailability(), dossier.symptoms());
                var result = deterministic.evaluate(evidence, activeRules);
                if (result.fullyCovered()) covered++;
                else uncovered++;
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("caseId", caseId);
                row.put("barcode", dossier.barcode());
                row.put("covered", result.fullyCovered());
                row.put("ruleIds", result.findings().stream().map(finding -> finding.ruleId()).distinct().toList());
                row.put("fallbackReason", result.fallbackReason() == null ? null : result.fallbackReason().name());
                coverage.add(row);
            }
        }
        Map<String, Boolean> checks = new LinkedHashMap<>();
        checks.put("expectedDataset", manifest.path("sha256").path("seed/generated/demo/runtime.json").asText().equals(imported.get("runtime_sha256"))
            && manifest.path("generatorVersion").asText().equals(imported.get("generator_version")));
        checks.put("tenCasesAndCopies", total == 10 && copies == 10);
        checks.put("allCasesVirgin", total == 10 && virgin == 10);
        checks.put("onlyBootstrapRule", bootstrapOnly);
        checks.put("noAnalyses", analyses == 0);
        checks.put("noMissions", missions == 0);
        checks.put("noConversations", conversations == 0);
        checks.put("noProposals", proposals == 0);
        checks.put("expectedCoverage", evaluated && covered == 2 && uncovered == 8);
        Map<String, Object> report = new LinkedHashMap<>();
        report.put("status", checks.values().stream().allMatch(Boolean::booleanValue) ? "READY" : "RESET_REQUIRED");
        report.put("datasetEpoch", epoch);
        report.put("generatorVersion", imported.get("generator_version"));
        report.put("runtimeSha256", imported.get("runtime_sha256"));
        report.put("checks", checks);
        report.put("counts", Map.of("cases", total, "copies", copies, "virginCases", virgin, "activeRules", activeRules.size(),
            "analyses", analyses, "missions", missions, "conversations", conversations, "proposals", proposals));
        report.put("coverageEvaluated", evaluated);
        report.put("deterministicCases", covered);
        report.put("uncoveredCases", uncovered);
        report.put("coverage", coverage);
        report.put("llmCalls", 0);
        return report;
    }

    private int count(UUID epoch, String table) {
        Integer result = jdbc.queryForObject("SELECT count(*) FROM runtime." + table + " WHERE dataset_epoch = ?", Integer.class, epoch);
        return result == null ? 0 : result;
    }
}