package com.library.stockgap.infrastructure.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.stockgap.domain.model.Publication;
import com.library.stockgap.domain.model.RuleMetadata;
import com.library.stockgap.domain.model.RuleProposal;
import com.library.stockgap.domain.model.ValidationReport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Repository
public class RuleRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public RuleRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    public int getActiveRuleSetVersion(UUID epoch) {
        Integer v = jdbcTemplate.queryForObject(
            "SELECT rule_set_version FROM runtime.active_rule_set WHERE dataset_epoch = ?",
            Integer.class, epoch
        );
        return v != null ? v : 1;
    }

    public List<RuleMetadata> getActiveRules(UUID epoch) {
        int activeVer = getActiveRuleSetVersion(epoch);
        String sql =
            "SELECT r.rule_id, rv.rule_version, rv.dmn_xml, rv.artifact_sha256, rv.decision_id, " +
            "rv.metadata, rv.created_at, r.origin_acceptance_id " +
            "FROM runtime.rule_set_member rsm " +
            "JOIN runtime.rule r ON rsm.dataset_epoch = r.dataset_epoch AND rsm.rule_id = r.rule_id " +
            "JOIN runtime.rule_version rv ON rsm.dataset_epoch = rv.dataset_epoch AND rsm.rule_id = rv.rule_id AND rsm.rule_version = rv.rule_version " +
            "WHERE rsm.dataset_epoch = ? AND rsm.rule_set_version = ? ORDER BY r.rule_id ASC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            String ruleId = rs.getString("rule_id");
            int ver = rs.getInt("rule_version");
            String dmnXml = rs.getString("dmn_xml");
            String sha = rs.getString("artifact_sha256");
            String decisionId = rs.getString("decision_id");
            Instant createdAt = rs.getTimestamp("created_at").toInstant();
            String accId = rs.getString("origin_acceptance_id");
            UUID originAccId = accId != null ? UUID.fromString(accId) : null;

            Map<String, Object> meta;
            try {
                meta = objectMapper.readValue(rs.getString("metadata"), Map.class);
            } catch (Exception e) {
                meta = Collections.emptyMap();
            }

            String name = (String) meta.getOrDefault("name", ruleId);
            String origin = (String) meta.getOrDefault("origin", "AGENT");
            String dmnPath = (String) meta.getOrDefault("dmnPath", "rules/" + ruleId + "-v" + ver + ".dmn");
            String namespace = (String) meta.getOrDefault("namespace", "urn:library-stock-gap:rules:" + ruleId + ":" + ver);
            String modelName = (String) meta.getOrDefault("modelName", ruleId + "-v" + ver);
            String causeCode = (String) meta.getOrDefault("causeCode", "UNKNOWN");
            List<String> covered = (List<String>) meta.getOrDefault("coveredSymptomKinds", Collections.emptyList());
            List<String> recs = (List<String>) meta.getOrDefault("recommendations", Collections.emptyList());
            List<String> limits = (List<String>) meta.getOrDefault("limitations", Collections.emptyList());

            return new RuleMetadata(
                ruleId, ver, name, origin, dmnPath, namespace, modelName, decisionId,
                causeCode, covered, recs, limits, sha, dmnXml, true, null, originAccId
            );
        }, epoch, activeVer);
    }

    public List<Map<String, Object>> listRules(UUID epoch) {
        int activeVer = getActiveRuleSetVersion(epoch);
        String sql =
            "SELECT r.rule_id, r.created_at, r.origin_acceptance_id, " +
            "EXISTS(SELECT 1 FROM runtime.rule_set_member rsm WHERE rsm.dataset_epoch = r.dataset_epoch AND rsm.rule_set_version = ? AND rsm.rule_id = r.rule_id) as is_active, " +
            "(SELECT max(rv.rule_version) FROM runtime.rule_version rv WHERE rv.dataset_epoch = r.dataset_epoch AND rv.rule_id = r.rule_id) as latest_version " +
            "FROM runtime.rule r WHERE r.dataset_epoch = ? ORDER BY r.rule_id ASC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            String ruleId = rs.getString("rule_id");
            int latestVer = rs.getInt("latest_version");
            map.put("ruleId", ruleId);
            map.put("latestVersion", latestVer);
            map.put("isActive", rs.getBoolean("is_active"));
            map.put("createdAt", rs.getTimestamp("created_at").toInstant().toString());
            var ruleMeta = getRuleVersion(epoch, ruleId, latestVer);
            map.put("details", ruleMeta.orElse(null));
            return map;
        }, activeVer, epoch);
    }

    public Optional<RuleMetadata> getRuleVersion(UUID epoch, String ruleId, int version) {
        int activeVer = getActiveRuleSetVersion(epoch);
        String sql =
            "SELECT rv.rule_id, rv.rule_version, rv.dmn_xml, rv.artifact_sha256, rv.decision_id, " +
            "rv.metadata, rv.created_at, r.origin_acceptance_id, " +
            "EXISTS(SELECT 1 FROM runtime.rule_set_member rsm WHERE rsm.dataset_epoch = rv.dataset_epoch AND rsm.rule_set_version = ? AND rsm.rule_id = rv.rule_id AND rsm.rule_version = rv.rule_version) as is_active " +
            "FROM runtime.rule_version rv " +
            "JOIN runtime.rule r ON rv.dataset_epoch = r.dataset_epoch AND rv.rule_id = r.rule_id " +
            "WHERE rv.dataset_epoch = ? AND rv.rule_id = ? AND rv.rule_version = ?";

        var list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            String id = rs.getString("rule_id");
            int ver = rs.getInt("rule_version");
            String dmnXml = rs.getString("dmn_xml");
            String sha = rs.getString("artifact_sha256");
            String decisionId = rs.getString("decision_id");
            Instant createdAt = rs.getTimestamp("created_at").toInstant();
            String accId = rs.getString("origin_acceptance_id");
            UUID originAccId = accId != null ? UUID.fromString(accId) : null;
            boolean isActive = rs.getBoolean("is_active");

            Map<String, Object> meta;
            try {
                meta = objectMapper.readValue(rs.getString("metadata"), Map.class);
            } catch (Exception e) {
                meta = Collections.emptyMap();
            }

            String name = (String) meta.getOrDefault("name", id);
            String origin = (String) meta.getOrDefault("origin", "AGENT");
            String dmnPath = (String) meta.getOrDefault("dmnPath", "rules/" + id + "-v" + ver + ".dmn");
            String namespace = (String) meta.getOrDefault("namespace", "urn:library-stock-gap:rules:" + id + ":" + ver);
            String modelName = (String) meta.getOrDefault("modelName", id + "-v" + ver);
            String causeCode = (String) meta.getOrDefault("causeCode", "UNKNOWN");
            List<String> covered = (List<String>) meta.getOrDefault("coveredSymptomKinds", Collections.emptyList());
            List<String> recs = (List<String>) meta.getOrDefault("recommendations", Collections.emptyList());
            List<String> limits = (List<String>) meta.getOrDefault("limitations", Collections.emptyList());

            return new RuleMetadata(
                id, ver, name, origin, dmnPath, namespace, modelName, decisionId,
                causeCode, covered, recs, limits, sha, dmnXml, isActive, null, originAccId
            );
        }, activeVer, epoch, ruleId, version);

        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public List<Map<String, Object>> listPublications(UUID epoch) {
        int activeVer = getActiveRuleSetVersion(epoch);
        String sql = "SELECT * FROM runtime.rule_set_publication WHERE dataset_epoch = ? ORDER BY rule_set_version DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            int ver = rs.getInt("rule_set_version");
            map.put("ruleSetVersion", ver);
            map.put("previousVersion", rs.getObject("previous_version"));
            map.put("kind", rs.getString("kind"));
            map.put("proposalId", rs.getObject("proposal_id"));
            map.put("validationReportId", rs.getObject("validation_report_id"));
            map.put("retiredRuleId", rs.getString("retired_rule_id"));
            map.put("manifestSha256", rs.getString("manifest_sha256"));
            map.put("approvedBy", rs.getString("approved_by"));
            map.put("createdAt", rs.getTimestamp("created_at").toInstant().toString());
            var sealed = rs.getTimestamp("sealed_at");
            map.put("sealedAt", sealed != null ? sealed.toInstant().toString() : null);
            map.put("isActive", ver == activeVer);
            return map;
        }, epoch);
    }

    @Transactional
    public void insertProposal(UUID epoch, UUID proposalId, UUID diagnosticId, UUID snapshotId, UUID missionId,
                               String operation, String targetRuleId, String ruleName, String causeCode,
                               List<String> coveredSymptomKinds, String rationale, String dmnXml,
                               int baseRuleSetVersion) {
        Instant now = Instant.now();
        String sha = AnalysisRepository.sha256Hex(dmnXml);

        // 1. rule_change_proposal
        jdbcTemplate.update(
            "INSERT INTO runtime.rule_change_proposal (dataset_epoch, proposal_id, current_revision, status, created_at) " +
            "VALUES (?, ?, 1, 'READY_FOR_APPROVAL', ?)",
            epoch, proposalId, Timestamp.from(now)
        );

        // 2. proposal_revision
        try {
            // Find acceptance_id
            UUID originAcceptanceId = null;
            var accList = jdbcTemplate.query(
                "SELECT acceptance_id FROM runtime.diagnostic_acceptance WHERE dataset_epoch = ? AND (acceptance_id = ? OR diagnostic_id = ?)",
                (rs, rowNum) -> UUID.fromString(rs.getString("acceptance_id")),
                epoch, diagnosticId, diagnosticId
            );
            if (!accList.isEmpty()) {
                originAcceptanceId = accList.get(0);
            }

            Map<String, Object> ratMap = Map.of(
                "rationale", rationale,
                "ruleName", ruleName,
                "causeCode", causeCode,
                "coveredSymptomKinds", coveredSymptomKinds
            );
            String ratJson = objectMapper.writeValueAsString(ratMap);
            String revSha = AnalysisRepository.sha256Hex(ratJson + sha);

            jdbcTemplate.update(
                "INSERT INTO runtime.proposal_revision (dataset_epoch, proposal_id, revision, base_rule_set_version, " +
                "origin_acceptance_id, origin_snapshot_id, author_mission_id, rationale, content_sha256, created_at) " +
                "VALUES (?, ?, 1, ?, ?, ?, ?, ?::jsonb, ?, ?)",
                epoch, proposalId, baseRuleSetVersion, originAcceptanceId, snapshotId, missionId, ratJson, revSha, Timestamp.from(now)
            );

            // 3. proposal_operation
            Map<String, Object> opMeta = Map.of(
                "name", ruleName,
                "causeCode", causeCode,
                "coveredSymptomKinds", coveredSymptomKinds,
                "recommendations", List.of("Contrôler la conformité du stock."),
                "limitations", List.of("Proposition synthétisée par agent.")
            );
            String opMetaJson = objectMapper.writeValueAsString(opMeta);

            int candidateVer = 1;
            Integer expectedVer = null;
            if ("REVISE".equals(operation)) {
                candidateVer = 2;
                expectedVer = 1;
            }

            jdbcTemplate.update(
                "INSERT INTO runtime.proposal_operation (dataset_epoch, proposal_id, revision, operation_no, kind, " +
                "rule_id, expected_rule_version, candidate_rule_version, dmn_xml, artifact_sha256, metadata) " +
                "VALUES (?, ?, 1, 1, ?, ?, ?, ?, ?, ?, ?::jsonb)",
                epoch, proposalId, operation, targetRuleId, expectedVer, candidateVer, dmnXml, sha, opMetaJson
            );
        } catch (Exception e) {
            throw new RuntimeException("Erreur insertion proposition", e);
        }
    }

    @Transactional
    public void insertValidationReport(UUID epoch, ValidationReport rep) {
        try {
            Map<String, Object> rMap = Map.of(
                "passed", rep.passed(),
                "positiveCasesTested", rep.positiveCasesTested(),
                "positiveCasesPassed", rep.positiveCasesPassed(),
                "negativeCasesTested", rep.negativeCasesTested(),
                "negativeCasesPassed", rep.negativeCasesPassed(),
                "totalReplayEvaluated", rep.totalReplayEvaluated(),
                "regressionsDetected", rep.regressionsDetected(),
                "conflicts", rep.conflicts(),
                "coverageLosses", rep.coverageLosses()
            );
            String repJson = objectMapper.writeValueAsString(rMap);

            jdbcTemplate.update(
                "INSERT INTO runtime.validation_report (dataset_epoch, report_id, proposal_id, proposal_revision, " +
                "base_rule_set_version, proposal_sha256, candidate_manifest_sha256, corpus_sha256, engine_version, " +
                "fact_schema_version, validator_version, outcome, blocking_count, report, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'KIE 10.2.0', '1.0.0', '1.0.0', ?, 0, ?::jsonb, ?)",
                epoch, rep.reportId(), rep.proposalId(), rep.proposalRevision(), rep.baseRuleSetVersion(),
                rep.candidateSha256(), rep.candidateSha256(), rep.candidateSha256(),
                rep.passed() ? "PASS" : "BLOCKED", repJson, Timestamp.from(Instant.now())
            );
        } catch (Exception e) {
            throw new RuntimeException("Erreur insertion validation report", e);
        }
    }

    @Transactional
    public int publishProposal(UUID epoch, UUID proposalId, int proposalRevision, UUID reportId) {
        // Read proposal operation
        String opSql = "SELECT * FROM runtime.proposal_operation WHERE dataset_epoch = ? AND proposal_id = ? AND revision = ? AND operation_no = 1";
        var opList = jdbcTemplate.queryForMap(opSql, epoch, proposalId, proposalRevision);
        String kind = (String) opList.get("kind");
        String ruleId = (String) opList.get("rule_id");
        int candidateVer = ((Number) opList.get("candidate_rule_version")).intValue();
        String dmnXml = (String) opList.get("dmn_xml");
        String sha = String.valueOf(opList.get("artifact_sha256"));
        Object metaObj = opList.get("metadata");
        String metaJson = metaObj != null ? metaObj.toString() : "{}";

        // Read revision for origin_acceptance_id
        String revSql = "SELECT origin_acceptance_id, base_rule_set_version FROM runtime.proposal_revision WHERE dataset_epoch = ? AND proposal_id = ? AND revision = ?";
        var revMap = jdbcTemplate.queryForMap(revSql, epoch, proposalId, proposalRevision);
        Object rawAccId = revMap.get("origin_acceptance_id");
        UUID originAccId = rawAccId instanceof UUID u ? u : (rawAccId != null ? UUID.fromString(rawAccId.toString()) : null);
        int baseVer = ((Number) revMap.get("base_rule_set_version")).intValue();

        Instant now = Instant.now();

        // 1. If CREATE, register rule in runtime.rule
        if ("CREATE".equals(kind)) {
            jdbcTemplate.update(
                "INSERT INTO runtime.rule (dataset_epoch, rule_id, origin_acceptance_id, created_at) " +
                "VALUES (?, ?, ?, ?) ON CONFLICT DO NOTHING",
                epoch, ruleId, originAccId, Timestamp.from(now)
            );
        }

        // 2. Materialize rule_version
        jdbcTemplate.update(
            "INSERT INTO runtime.rule_version (dataset_epoch, rule_id, rule_version, author_kind, proposal_id, " +
            "proposal_revision, dmn_xml, artifact_sha256, decision_id, decision_row_count, fact_schema_version, " +
            "engine_version, metadata, created_at) VALUES (?, ?, ?, 'AGENT', ?, ?, ?, ?, 'decision', 1, '1.0.0', 'KIE 10.2.0', ?::jsonb, ?)",
            epoch, ruleId, candidateVer, proposalId, proposalRevision, dmnXml, sha, metaJson, Timestamp.from(now)
        );

        // 3. Prepare new rule_set_publication version
        int newVer = baseVer + 1;
        UUID commandId = UUID.randomUUID();
        String manifestSha = AnalysisRepository.sha256Hex("publication-v" + newVer + "-" + ruleId + "-" + candidateVer);

        jdbcTemplate.update(
            "INSERT INTO runtime.rule_set_publication (dataset_epoch, rule_set_version, previous_version, kind, " +
            "proposal_id, proposal_revision, validation_report_id, manifest_sha256, approved_by, command_id, created_at) " +
            "VALUES (?, ?, ?, 'AGENT_CHANGE', ?, ?, ?, ?, 'Admin', ?, ?)",
            epoch, newVer, baseVer, proposalId, proposalRevision, reportId, manifestSha, commandId, Timestamp.from(now)
        );

        // 4. Copy existing members from baseVer, replacing or adding this rule
        List<Map<String, Object>> prevMembers = jdbcTemplate.queryForList(
            "SELECT rule_id, rule_version FROM runtime.rule_set_member WHERE dataset_epoch = ? AND rule_set_version = ?",
            epoch, baseVer
        );
        boolean added = false;
        for (Map<String, Object> pm : prevMembers) {
            String existingRuleId = (String) pm.get("rule_id");
            int existingRuleVer = ((Number) pm.get("rule_version")).intValue();
            if (existingRuleId.equals(ruleId)) {
                // Revised
                jdbcTemplate.update(
                    "INSERT INTO runtime.rule_set_member (dataset_epoch, rule_set_version, rule_id, rule_version) " +
                    "VALUES (?, ?, ?, ?)",
                    epoch, newVer, ruleId, candidateVer
                );
                added = true;
            } else {
                // Kept
                jdbcTemplate.update(
                    "INSERT INTO runtime.rule_set_member (dataset_epoch, rule_set_version, rule_id, rule_version) " +
                    "VALUES (?, ?, ?, ?)",
                    epoch, newVer, existingRuleId, existingRuleVer
                );
            }
        }
        if (!added) {
            // New rule
            jdbcTemplate.update(
                "INSERT INTO runtime.rule_set_member (dataset_epoch, rule_set_version, rule_id, rule_version) " +
                "VALUES (?, ?, ?, ?)",
                epoch, newVer, ruleId, candidateVer
            );
        }

        // 5. Seal publication
        jdbcTemplate.update(
            "UPDATE runtime.rule_set_publication SET sealed_at = ? WHERE dataset_epoch = ? AND rule_set_version = ?",
            Timestamp.from(now), epoch, newVer
        );

        // 6. Atomically update active_rule_set pointer!
        jdbcTemplate.update(
            "UPDATE runtime.active_rule_set SET rule_set_version = ?, lock_version = lock_version + 1 " +
            "WHERE dataset_epoch = ?",
            newVer, epoch
        );

        // 7. Update proposal state
        jdbcTemplate.update(
            "UPDATE runtime.rule_change_proposal SET status = 'PUBLISHED', lock_version = lock_version + 1 " +
            "WHERE dataset_epoch = ? AND proposal_id = ?",
            epoch, proposalId
        );

        return newVer;
    }

    @Transactional
    public int retireRule(UUID epoch, String ruleId) {
        int currentVer = getActiveRuleSetVersion(epoch);
        int newVer = currentVer + 1;
        UUID commandId = UUID.randomUUID();
        Instant now = Instant.now();
        String manifestSha = AnalysisRepository.sha256Hex("retire-" + ruleId + "-v" + newVer);

        // 1. Create publication
        jdbcTemplate.update(
            "INSERT INTO runtime.rule_set_publication (dataset_epoch, rule_set_version, previous_version, kind, " +
            "retired_rule_id, manifest_sha256, approved_by, command_id, created_at) " +
            "VALUES (?, ?, ?, 'ADMIN_RETIRE', ?, ?, 'Admin', ?, ?)",
            epoch, newVer, currentVer, ruleId, manifestSha, commandId, Timestamp.from(now)
        );

        // 2. Copy members except retiredRuleId
        List<Map<String, Object>> prevMembers = jdbcTemplate.queryForList(
            "SELECT rule_id, rule_version FROM runtime.rule_set_member WHERE dataset_epoch = ? AND rule_set_version = ?",
            epoch, currentVer
        );
        for (Map<String, Object> pm : prevMembers) {
            String rid = (String) pm.get("rule_id");
            int rver = ((Number) pm.get("rule_version")).intValue();
            if (!rid.equals(ruleId)) {
                jdbcTemplate.update(
                    "INSERT INTO runtime.rule_set_member (dataset_epoch, rule_set_version, rule_id, rule_version) " +
                    "VALUES (?, ?, ?, ?)",
                    epoch, newVer, rid, rver
                );
            }
        }

        // 3. Seal publication
        jdbcTemplate.update(
            "UPDATE runtime.rule_set_publication SET sealed_at = ? WHERE dataset_epoch = ? AND rule_set_version = ?",
            Timestamp.from(now), epoch, newVer
        );

        // 4. Update active_rule_set
        jdbcTemplate.update(
            "UPDATE runtime.active_rule_set SET rule_set_version = ?, lock_version = lock_version + 1 " +
            "WHERE dataset_epoch = ?",
            newVer, epoch
        );

        return newVer;
    }

    public List<Map<String, Object>> listProposals(UUID epoch) {
        String sql =
            "SELECT p.proposal_id, p.current_revision, p.status, p.created_at, " +
            "pr.origin_acceptance_id, pr.origin_snapshot_id, pr.base_rule_set_version, pr.rationale, " +
            "po.kind as operation, po.rule_id, po.candidate_rule_version, po.dmn_xml, po.metadata " +
            "FROM runtime.rule_change_proposal p " +
            "JOIN runtime.proposal_revision pr ON p.dataset_epoch = pr.dataset_epoch AND p.proposal_id = pr.proposal_id AND p.current_revision = pr.revision " +
            "LEFT JOIN runtime.proposal_operation po ON pr.dataset_epoch = po.dataset_epoch AND pr.proposal_id = po.proposal_id AND pr.revision = po.revision " +
            "WHERE p.dataset_epoch = ? ORDER BY p.created_at DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("proposalId", UUID.fromString(rs.getString("proposal_id")));
            map.put("currentRevision", rs.getInt("current_revision"));
            map.put("status", rs.getString("status"));
            map.put("createdAt", rs.getTimestamp("created_at").toInstant().toString());
            map.put("operation", rs.getString("operation"));
            map.put("targetRuleId", rs.getString("rule_id"));
            map.put("candidateRuleVersion", rs.getObject("candidate_rule_version"));
            map.put("dmnXml", rs.getString("dmn_xml"));
            map.put("baseRuleSetVersion", rs.getInt("base_rule_set_version"));

            try {
                map.put("rationale", objectMapper.readValue(rs.getString("rationale"), Map.class));
            } catch (Exception e) {
                map.put("rationale", Collections.emptyMap());
            }
            try {
                String meta = rs.getString("metadata");
                map.put("metadata", meta != null ? objectMapper.readValue(meta, Map.class) : Collections.emptyMap());
            } catch (Exception e) {
                map.put("metadata", Collections.emptyMap());
            }

            return map;
        }, epoch);
    }
}
