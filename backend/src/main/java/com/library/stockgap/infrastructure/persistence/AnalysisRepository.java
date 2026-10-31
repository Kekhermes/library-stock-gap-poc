package com.library.stockgap.infrastructure.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.stockgap.domain.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Repository
public class AnalysisRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public AnalysisRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    public static String sha256Hex(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("SHA-256 error", e);
        }
    }

    @Transactional
    public void insertSnapshot(UUID epoch, EvidenceSnapshot snapshot) {
        try {
            String factsJson = objectMapper.writeValueAsString(snapshot.caseFacts());
            Map<String, Object> bundle = Map.of(
                "sourceEvents", snapshot.sourceEvents(),
                "operatorNotes", snapshot.operatorNotes(),
                "observations", snapshot.observations(),
                "systemState", snapshot.systemState(),
                "sourceAvailability", snapshot.sourceAvailability(),
                "symptoms", snapshot.symptoms()
            );
            String bundleJson = objectMapper.writeValueAsString(bundle);
            String sha = sha256Hex(factsJson + bundleJson);

            jdbcTemplate.update(
                "INSERT INTO runtime.evidence_snapshot (dataset_epoch, snapshot_id, case_id, schema_version, " +
                "inventory_reference_time, data_captured_at, created_at, facts, evidence_bundle, content_sha256) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?::jsonb, ?::jsonb, ?)",
                epoch, snapshot.snapshotId(), snapshot.caseId(), snapshot.schemaVersion(),
                Timestamp.from(snapshot.inventoryReferenceTime()), Timestamp.from(snapshot.dataCapturedAt()),
                Timestamp.from(Instant.now()), factsJson, bundleJson, sha
            );
        } catch (Exception e) {
            throw new RuntimeException("Erreur d'enregistrement du dossier de preuves figé", e);
        }
    }

    @Transactional
    public void insertAnalysisRun(UUID epoch, UUID analysisId, UUID caseId, UUID snapshotId, int ruleSetVersion) {
        jdbcTemplate.update("INSERT INTO runtime.analysis_run (dataset_epoch, analysis_id, case_id, snapshot_id, rule_set_version, status, created_at) VALUES (?, ?, ?, ?, ?, 'DETERMINISTIC_RUNNING', ?)", epoch, analysisId, caseId, snapshotId, ruleSetVersion, Timestamp.from(Instant.now()));
        jdbcTemplate.update("UPDATE runtime.gap_case SET status = 'EN_ANALYSE', lock_version = lock_version + 1 WHERE dataset_epoch = ? AND case_id = ?", epoch, caseId);
    }

    @Transactional
    public void markDeterministicCompleted(UUID epoch, UUID analysisId, UUID caseId, Map<String,Object> trace, DiagnosticRecord diagnostic) {
        try { jdbcTemplate.update("UPDATE runtime.analysis_run SET status = 'COMPLETED', deterministic_trace = ?::jsonb, finished_at = ?, lock_version = lock_version + 1 WHERE dataset_epoch = ? AND analysis_id = ?", objectMapper.writeValueAsString(trace), Timestamp.from(Instant.now()), epoch, analysisId); insertDiagnosticRevision(epoch, diagnostic, "DMN", 1); }
        catch(Exception e) { throw new RuntimeException("Erreur diagnostic DMN",e); }
    }
    @Transactional
    public void markFallbackRequested(UUID epoch, UUID analysisId, FallbackReason reason, Map<String,Object> trace) {
        try { jdbcTemplate.update("UPDATE runtime.analysis_run SET status = 'AGENT_PENDING', fallback_reason = ?::jsonb, deterministic_trace = ?::jsonb, lock_version = lock_version + 1 WHERE dataset_epoch = ? AND analysis_id = ?", objectMapper.writeValueAsString(Map.of("reason",reason.name(),"trace",trace)), objectMapper.writeValueAsString(trace), epoch, analysisId); }
        catch(Exception e) { throw new RuntimeException("Erreur fallback agent",e); }
    }

    @Transactional
    public void updateAnalysisStatus(UUID epoch, UUID analysisId, String status) {
        Instant now = Instant.now();
        if ("COMPLETED".equals(status) || "CANCELLED".equals(status) || "FAILED".equals(status)) {
            jdbcTemplate.update(
                "UPDATE runtime.analysis_run SET status = ?, finished_at = ?, lock_version = lock_version + 1 " +
                "WHERE dataset_epoch = ? AND analysis_id = ?",
                status, Timestamp.from(now), epoch, analysisId
            );
        } else {
            jdbcTemplate.update(
                "UPDATE runtime.analysis_run SET status = ?, lock_version = lock_version + 1 " +
                "WHERE dataset_epoch = ? AND analysis_id = ?",
                status, epoch, analysisId
            );
        }
    }

    /**
     * Termine une analyse par un échec visible et rend le cas de nouveau analysable.
     * Un diagnostic agent rejeté ne doit pas laisser le dossier bloqué en EN_ANALYSE.
     */
    @Transactional
    public void failAnalysis(UUID epoch, UUID analysisId) {
        Instant now = Instant.now();
        jdbcTemplate.update(
            "UPDATE runtime.analysis_run SET status = 'FAILED', finished_at = ?, lock_version = lock_version + 1 " +
            "WHERE dataset_epoch = ? AND analysis_id = ? AND status NOT IN ('COMPLETED','FAILED','CANCELLED')",
            Timestamp.from(now), epoch, analysisId
        );
        jdbcTemplate.update(
            "UPDATE runtime.gap_case SET status = 'A_ANALYSER', lock_version = lock_version + 1 " +
            "WHERE dataset_epoch = ? AND status = 'EN_ANALYSE' " +
            "AND case_id = (SELECT case_id FROM runtime.analysis_run WHERE dataset_epoch = ? AND analysis_id = ?)",
            epoch, epoch, analysisId
        );
    }

    /** Analyse encore active (non terminale) d'un cas, si elle existe. */
    public Optional<UUID> findActiveAnalysisId(UUID epoch, UUID caseId) {
        var list = jdbcTemplate.query(
            "SELECT analysis_id FROM runtime.analysis_run WHERE dataset_epoch = ? AND case_id = ? " +
            "AND status NOT IN ('COMPLETED','FAILED','CANCELLED') ORDER BY created_at DESC LIMIT 1",
            (rs, rowNum) -> UUID.fromString(rs.getString("analysis_id")), epoch, caseId);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Transactional
    public void insertDiagnosticRevision(UUID epoch, DiagnosticRecord diagnostic, String producer, int revision) {
        try {
            Map<String, Object> resultPayload = new HashMap<>();
            resultPayload.put("conclusion", diagnostic.conclusion().name());
            resultPayload.put("findings", diagnostic.findings());
            resultPayload.put("missingEvidence", diagnostic.missingEvidence());
            resultPayload.put("summary", diagnostic.summary());
            String resultJson = objectMapper.writeValueAsString(resultPayload);
            String sha = sha256Hex(resultJson);

            jdbcTemplate.update(
                "INSERT INTO runtime.diagnostic_revision (dataset_epoch, diagnostic_id, analysis_id, case_id, revision, " +
                "producer, conclusion, result, content_sha256, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?::jsonb, ?, ?)",
                epoch, diagnostic.diagnosticId(), diagnostic.analysisId(), diagnostic.caseId(), revision,
                producer, diagnostic.conclusion().name(), resultJson, sha, Timestamp.from(Instant.now())
            );

            // Also update gap_case status to A_REVOIR
            jdbcTemplate.update(
                "UPDATE runtime.gap_case SET status = 'A_REVOIR', lock_version = lock_version + 1 " +
                "WHERE dataset_epoch = ? AND case_id = ?",
                epoch, diagnostic.caseId()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erreur insertion diagnostic", e);
        }
    }

    @Transactional
    public UUID acceptDiagnostic(UUID epoch, UUID diagnosticId, UUID caseId) {
        UUID acceptanceId = UUID.randomUUID();
        UUID commandId = UUID.randomUUID();
        Instant now = Instant.now();

        jdbcTemplate.update(
            "INSERT INTO runtime.diagnostic_acceptance (dataset_epoch, acceptance_id, diagnostic_id, case_id, accepted_by, accepted_at, command_id) " +
            "VALUES (?, ?, ?, ?, 'Admin', ?, ?)",
            epoch, acceptanceId, diagnosticId, caseId, Timestamp.from(now), commandId
        );

        jdbcTemplate.update(
            "UPDATE runtime.gap_case SET status = 'DIAGNOSTIC_ACCEPTE', current_acceptance_id = ?, lock_version = lock_version + 1 " +
            "WHERE dataset_epoch = ? AND case_id = ?",
            acceptanceId, epoch, caseId
        );

        return acceptanceId;
    }

    public Optional<Map<String, Object>> findAnalysis(UUID epoch, UUID analysisId) {
        String sql = "SELECT * FROM runtime.analysis_run WHERE dataset_epoch = ? AND analysis_id = ?";
        var list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("analysisId", UUID.fromString(rs.getString("analysis_id")));
            map.put("caseId", UUID.fromString(rs.getString("case_id")));
            map.put("snapshotId", UUID.fromString(rs.getString("snapshot_id")));
            map.put("ruleSetVersion", rs.getInt("rule_set_version"));
            map.put("status", rs.getString("status"));
            map.put("createdAt", rs.getTimestamp("created_at").toInstant().toString());
            var fin = rs.getTimestamp("finished_at");
            map.put("finishedAt", fin != null ? fin.toInstant().toString() : null);
            return map;
        }, epoch, analysisId);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public Optional<EvidenceSnapshot> findSnapshot(UUID epoch, UUID snapshotId) {
        String sql = "SELECT * FROM runtime.evidence_snapshot WHERE dataset_epoch = ? AND snapshot_id = ?";
        var list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            UUID id = UUID.fromString(rs.getString("snapshot_id"));
            UUID caseId = UUID.fromString(rs.getString("case_id"));
            String schemaVersion = rs.getString("schema_version");
            Instant refTime = rs.getTimestamp("inventory_reference_time").toInstant();
            Instant capTime = rs.getTimestamp("data_captured_at").toInstant();
            Map<String, Object> facts;
            Map<String, Object> bundle;
            try {
                facts = objectMapper.readValue(rs.getString("facts"), Map.class);
                bundle = objectMapper.readValue(rs.getString("evidence_bundle"), Map.class);
            } catch (Exception e) {
                facts = Collections.emptyMap();
                bundle = Collections.emptyMap();
            }
            List<Map<String, Object>> events = (List<Map<String, Object>>) bundle.getOrDefault("sourceEvents", Collections.emptyList());
            List<Map<String, Object>> notes = (List<Map<String, Object>>) bundle.getOrDefault("operatorNotes", Collections.emptyList());
            Map<String, Object> sysState = (Map<String, Object>) bundle.getOrDefault("systemState", Collections.emptyMap());
            List<Map<String, Object>> obs = (List<Map<String, Object>>) bundle.getOrDefault("observations", Collections.emptyList());
            List<Map<String, Object>> avail = (List<Map<String, Object>>) bundle.getOrDefault("sourceAvailability", Collections.emptyList());
            List<Map<String, Object>> symptoms = (List<Map<String, Object>>) bundle.getOrDefault("symptoms", Collections.emptyList());

            UUID copyId = UUID.fromString((String) facts.getOrDefault("copyId", caseId.toString()));
            return new EvidenceSnapshot(id, caseId, copyId, schemaVersion, refTime, capTime, facts, events, notes, sysState, obs, avail, symptoms);
        }, epoch, snapshotId);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public Optional<DiagnosticRecord> findDiagnostic(UUID epoch, UUID diagnosticId) {
        String sql = "SELECT * FROM runtime.diagnostic_revision WHERE dataset_epoch = ? AND diagnostic_id = ?";
        var list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            UUID id = UUID.fromString(rs.getString("diagnostic_id"));
            UUID analysisId = UUID.fromString(rs.getString("analysis_id"));
            UUID caseId = UUID.fromString(rs.getString("case_id"));
            DiagnosticConclusion conclusion = DiagnosticConclusion.valueOf(rs.getString("conclusion"));
            String producer = rs.getString("producer");
            Map<String, Object> res;
            try {
                res = objectMapper.readValue(rs.getString("result"), Map.class);
            } catch (Exception e) {
                res = Collections.emptyMap();
            }
            String summary = (String) res.getOrDefault("summary", "");
            List<Map<String, Object>> rawFindings = (List<Map<String, Object>>) res.getOrDefault("findings", Collections.emptyList());
            List<Finding> findings = new ArrayList<>();
            for (Map<String, Object> rf : rawFindings) {
                findings.add(new Finding(
                    UUID.fromString((String) rf.get("subjectCopyId")),
                    ((List<String>) rf.getOrDefault("coveredSymptomIds", Collections.emptyList())).stream().map(UUID::fromString).toList(),
                    (String) rf.get("causeCode"),
                    EpistemicStatus.valueOf((String) rf.get("epistemicStatus")),
                    (List<String>) rf.getOrDefault("assertions", Collections.emptyList()),
                    Collections.emptyList(),
                    Collections.emptyList(),
                    (List<String>) rf.getOrDefault("recommendedChecks", Collections.emptyList()),
                    (String) rf.getOrDefault("explanation", ""),
                    (String) rf.get("ruleId"),
                    rf.get("ruleVersion") != null ? ((Number) rf.get("ruleVersion")).intValue() : null
                ));
            }
            List<String> missing = (List<String>) res.getOrDefault("missingEvidence", Collections.emptyList());
            return new DiagnosticRecord(id, analysisId, caseId, conclusion, findings, missing, summary, "DMN".equals(producer), producer);
        }, epoch, diagnosticId);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }
}
