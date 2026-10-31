package com.library.stockgap.infrastructure.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.List;
import java.util.Objects;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Repository
public class MissionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public MissionRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void createMission(UUID epoch, UUID missionId, String kind, UUID analysisId, UUID originAcceptanceId, UUID targetSnapshotId, int ruleSetVersion, UUID turnId) {
        Instant now = Instant.now();
        String configJson;
        try { configJson = objectMapper.writeValueAsString(Map.of("configuredBy", "worker", "consumptionPolicy", "OBSERVABILITY_ONLY")); }
        catch (Exception e) { configJson = "{}"; }
        jdbcTemplate.update(
            "INSERT INTO runtime.agent_mission (dataset_epoch, mission_id, kind, analysis_id, origin_acceptance_id, snapshot_id, rule_set_version, turn_id, status, current_attempt, prompt_version, model_configuration, max_llm_calls, max_tool_calls, max_input_tokens, max_output_tokens, max_duration_seconds, started_at, deadline_at, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'QUEUED', 0, '1.0.0', ?::jsonb, ?, 16, 32000, 8000, ?, ?, ?, ?)",
            epoch, missionId, kind, analysisId, originAcceptanceId, targetSnapshotId, ruleSetVersion, turnId, configJson,
            "EXPERT_TURN".equals(kind) ? 4 : 8, "EXPERT_TURN".equals(kind) ? 120 : 300, null, null, Timestamp.from(now));
    }

    @Transactional
    public Map<String, Object> claimMission(UUID epoch, UUID missionId, String workerId) {
        var active = jdbcTemplate.queryForList("SELECT dataset_epoch FROM runtime.active_dataset WHERE singleton = true AND dataset_epoch = ? FOR UPDATE", epoch);
        if (active.isEmpty()) return Map.of("status", "TERMINAL");
        var rows = jdbcTemplate.queryForList("SELECT status, current_attempt FROM runtime.agent_mission WHERE dataset_epoch = ? AND mission_id = ? FOR UPDATE", epoch, missionId);
        if (rows.isEmpty()) return Map.of("status", "TERMINAL");
        Map<String, Object> mission = rows.getFirst();
        String status = (String) mission.get("status");
        int attempt = ((Number) mission.get("current_attempt")).intValue();
        if (!"QUEUED".equals(status) || attempt >= 2) return Map.of("status", attempt >= 2 ? "EXHAUSTED" : "TERMINAL");
        int nextAttempt = attempt + 1;
        UUID leaseToken = UUID.randomUUID();
        Instant now = Instant.now();
        jdbcTemplate.update("INSERT INTO runtime.mission_attempt (dataset_epoch, mission_id, attempt, lease_token, worker_id, status, claimed_at, heartbeat_at, lease_expires_at) VALUES (?, ?, ?, ?, ?, 'RUNNING', ?, ?, ?)", epoch, missionId, nextAttempt, leaseToken, workerId, Timestamp.from(now), Timestamp.from(now), Timestamp.from(now.plusSeconds(90)));
        jdbcTemplate.update("UPDATE runtime.agent_mission SET status = 'RUNNING', current_attempt = ?, lock_version = lock_version + 1 WHERE dataset_epoch = ? AND mission_id = ? AND status = 'QUEUED'", nextAttempt, epoch, missionId);
        jdbcTemplate.update("UPDATE runtime.agent_mission SET started_at = coalesce(started_at, ?::timestamptz), deadline_at = coalesce(deadline_at, ?::timestamptz + max_duration_seconds * interval '1 second') WHERE dataset_epoch = ? AND mission_id = ?", Timestamp.from(now), Timestamp.from(now), epoch, missionId);
        jdbcTemplate.update("UPDATE runtime.conversation_turn SET status = 'RUNNING' WHERE dataset_epoch = ? AND turn_id = (SELECT turn_id FROM runtime.agent_mission WHERE dataset_epoch = ? AND mission_id = ?)", epoch, epoch, missionId);
        return Map.of("status", "CLAIMED", "attempt", nextAttempt, "leaseToken", leaseToken, "fencingToken", nextAttempt, "expiresAt", now.plusSeconds(90).toString());
    }

    public Map<String, Object> activeContext(UUID epoch, UUID missionId, UUID leaseToken) {
        var rows = jdbcTemplate.queryForList("SELECT m.kind, m.analysis_id, m.snapshot_id, m.rule_set_version, m.origin_acceptance_id, m.turn_id, a.attempt, s.case_id, t.conversation_id, t.agent_role " +
            "FROM runtime.agent_mission m JOIN runtime.active_dataset d ON d.dataset_epoch = m.dataset_epoch AND d.singleton = true " +
            "JOIN runtime.mission_attempt a ON a.dataset_epoch = m.dataset_epoch AND a.mission_id = m.mission_id AND a.attempt = m.current_attempt " +
            "JOIN runtime.evidence_snapshot s ON s.dataset_epoch = m.dataset_epoch AND s.snapshot_id = m.snapshot_id " +
            "LEFT JOIN runtime.analysis_run ar ON ar.dataset_epoch = m.dataset_epoch AND ar.analysis_id = m.analysis_id " +
            "LEFT JOIN runtime.conversation_turn t ON t.dataset_epoch = m.dataset_epoch AND t.turn_id = m.turn_id " +
            "WHERE m.dataset_epoch = ? AND m.mission_id = ? AND m.status = 'RUNNING' AND a.status = 'RUNNING' AND a.lease_token = ? AND a.lease_expires_at > now() " +
            "AND (m.kind <> 'INVESTIGATION' OR ar.status IN ('AGENT_PENDING','AGENT_RUNNING'))", epoch, missionId, leaseToken);
        if (rows.size() != 1) throw new SecurityException("Mission ou autorisation temporaire non active");
        return rows.getFirst();
    }

    @Transactional
    public Map<String, Object> heartbeat(UUID epoch, UUID missionId, UUID leaseToken, int attempt, String model) {
        var context = activeContext(epoch, missionId, leaseToken);
        if (((Number) context.get("attempt")).intValue() != attempt) throw new SecurityException("Ancienne tentative");
        int updated = jdbcTemplate.update("UPDATE runtime.mission_attempt SET heartbeat_at = now(), lease_expires_at = now() + interval '90 seconds' WHERE dataset_epoch = ? AND mission_id = ? AND attempt = ? AND lease_token = ? AND status = 'RUNNING' AND lease_expires_at > now()", epoch, missionId, attempt, leaseToken);
        if (updated != 1) throw new SecurityException("Autorisation temporaire expirée");
        if (model != null && !model.isBlank() && model.length() < 128) {
            jdbcTemplate.update("UPDATE runtime.agent_mission SET model_configuration = jsonb_build_object('model', ?::text, 'consumptionPolicy', 'OBSERVABILITY_ONLY') WHERE dataset_epoch = ? AND mission_id = ?", model, epoch, missionId);
        }
        return Map.of("status", "ACTIVE", "attempt", attempt);
    }

    @Scheduled(fixedDelay = 15000)
    @Transactional
    public void recoverExpired() {
        var rows = jdbcTemplate.queryForList("SELECT m.dataset_epoch, m.mission_id, m.current_attempt, m.analysis_id, m.turn_id FROM runtime.agent_mission m " +
            "JOIN runtime.active_dataset d ON d.dataset_epoch = m.dataset_epoch AND d.singleton = true " +
            "JOIN runtime.mission_attempt a ON a.dataset_epoch = m.dataset_epoch AND a.mission_id = m.mission_id AND a.attempt = m.current_attempt " +
            "WHERE m.status = 'RUNNING' AND a.status = 'RUNNING' AND a.lease_expires_at <= now() FOR UPDATE OF m, a SKIP LOCKED");
        for (var row : rows) {
            Object epoch = row.get("dataset_epoch"), mission = row.get("mission_id");
            int attempt = ((Number) row.get("current_attempt")).intValue();
            jdbcTemplate.update("UPDATE runtime.mission_attempt SET status = 'EXPIRED', finished_at = now(), failure_code = 'LEASE_EXPIRED' WHERE dataset_epoch = ? AND mission_id = ? AND attempt = ?", epoch, mission, attempt);
            boolean retry = attempt < 2;
            jdbcTemplate.update("UPDATE runtime.agent_mission SET status = ?, finished_at = ?, lock_version = lock_version + 1 WHERE dataset_epoch = ? AND mission_id = ?", retry ? "QUEUED" : "FAILED", retry ? null : Timestamp.from(Instant.now()), epoch, mission);
            if (retry) jdbcTemplate.update("UPDATE runtime.outbox_event SET published_at = NULL, available_at = now() WHERE dataset_epoch = ? AND mission_id = ?", epoch, mission);
            else {
                if (row.get("analysis_id") != null) {
                    jdbcTemplate.update("UPDATE runtime.analysis_run SET status = 'FAILED', finished_at = now() WHERE dataset_epoch = ? AND analysis_id = ? AND status IN ('AGENT_PENDING','AGENT_RUNNING')", epoch, row.get("analysis_id"));
                    jdbcTemplate.update("UPDATE runtime.gap_case SET status = 'A_ANALYSER' WHERE dataset_epoch = ? AND case_id = (SELECT case_id FROM runtime.analysis_run WHERE dataset_epoch = ? AND analysis_id = ?)", epoch, epoch, row.get("analysis_id"));
                }
                if (row.get("turn_id") != null) jdbcTemplate.update("UPDATE runtime.conversation_turn SET status = 'FAILED' WHERE dataset_epoch = ? AND turn_id = ?", epoch, row.get("turn_id"));
            }
        }
    }

    public void recordTrace(UUID epoch, UUID missionId, int attempt, UUID eventId, String type, Map<String, Object> data) {
        try {
            String content = objectMapper.writeValueAsString(data);
            if (content.length() > 20000) throw new IllegalArgumentException("Trace trop volumineuse");
            jdbcTemplate.update("INSERT INTO runtime.agent_trace (dataset_epoch, mission_id, attempt, event_id, event_type, details, created_at) VALUES (?, ?, ?, ?, ?, ?::jsonb, now()) ON CONFLICT DO NOTHING", epoch, missionId, attempt, eventId, type, content);
        } catch (Exception error) { throw new IllegalStateException("Trace de mission non persistée", error); }
    }

    public List<Map<String, Object>> tracesForCase(UUID epoch, UUID caseId) {
        return jdbcTemplate.queryForList("SELECT t.event_id, t.event_type, t.details, t.created_at, t.mission_id, m.kind, m.status, m.model_configuration FROM runtime.agent_trace t " +
            "JOIN runtime.agent_mission m ON m.dataset_epoch = t.dataset_epoch AND m.mission_id = t.mission_id " +
            "JOIN runtime.evidence_snapshot s ON s.dataset_epoch = m.dataset_epoch AND s.snapshot_id = m.snapshot_id WHERE t.dataset_epoch = ? AND s.case_id = ? ORDER BY t.created_at", epoch, caseId);
    }

    public Map<String,Object> status(UUID epoch, UUID missionId) {
        return jdbcTemplate.queryForMap("SELECT mission_id, kind, status, current_attempt, model_configuration, created_at, finished_at FROM runtime.agent_mission WHERE dataset_epoch = ? AND mission_id = ?", epoch, missionId);
    }

    @Transactional
    public void cancel(UUID epoch, UUID missionId) {
        var active=jdbcTemplate.queryForList("SELECT m.analysis_id FROM runtime.agent_mission m JOIN runtime.active_dataset d ON d.dataset_epoch = m.dataset_epoch AND d.singleton = true WHERE m.dataset_epoch = ? AND m.mission_id = ? AND m.status IN ('QUEUED','RUNNING') FOR UPDATE OF m",epoch,missionId);
        if(active.isEmpty())return;
        jdbcTemplate.update("UPDATE runtime.agent_mission SET status = 'CANCELLED', finished_at = now(), lock_version = lock_version + 1 WHERE dataset_epoch = ? AND mission_id = ?",epoch,missionId);
        jdbcTemplate.update("UPDATE runtime.mission_attempt SET status = 'CANCELLED', finished_at = now() WHERE dataset_epoch = ? AND mission_id = ? AND status = 'RUNNING'",epoch,missionId);
        jdbcTemplate.update("UPDATE runtime.conversation_turn SET status = 'CANCELLED' WHERE dataset_epoch = ? AND turn_id = (SELECT turn_id FROM runtime.agent_mission WHERE dataset_epoch = ? AND mission_id = ?)",epoch,epoch,missionId);
        if(active.get(0).get("analysis_id")!=null) {
            jdbcTemplate.update("UPDATE runtime.analysis_run SET status = 'CANCELLED', finished_at = now() WHERE dataset_epoch = ? AND analysis_id = ? AND status IN ('AGENT_PENDING','AGENT_RUNNING')",epoch,active.get(0).get("analysis_id"));
            jdbcTemplate.update("UPDATE runtime.gap_case SET status = 'A_ANALYSER' WHERE dataset_epoch = ? AND case_id = (SELECT case_id FROM runtime.analysis_run WHERE dataset_epoch = ? AND analysis_id = ?)",epoch,epoch,active.get(0).get("analysis_id"));
        }
    }

    public boolean canReadSnapshot(UUID epoch, UUID missionId, UUID snapshotId, UUID leaseToken) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT count(*) FROM runtime.agent_mission m JOIN runtime.active_dataset d ON d.dataset_epoch = m.dataset_epoch AND d.singleton = true JOIN runtime.mission_attempt a ON a.dataset_epoch = m.dataset_epoch AND a.mission_id = m.mission_id AND a.attempt = m.current_attempt WHERE m.dataset_epoch = ? AND m.mission_id = ? AND m.snapshot_id = ? AND m.status = 'RUNNING' AND a.lease_token = ? AND a.status = 'RUNNING' AND a.lease_expires_at > now()",
            Integer.class, epoch, missionId, snapshotId, leaseToken);
        return count != null && count == 1;
    }

    @Transactional
    public boolean lockActiveEvent(UUID epoch, UUID missionId, UUID snapshotId, UUID leaseToken, int attempt,
                                   int fencingToken, int ruleSetVersion, UUID analysisId, UUID caseId, String eventType) {
        var rows = jdbcTemplate.queryForList(
            "SELECT m.kind, m.analysis_id, m.rule_set_version, s.case_id, ar.status AS analysis_status " +
            "FROM runtime.active_dataset d JOIN runtime.agent_mission m ON m.dataset_epoch = d.dataset_epoch " +
            "JOIN runtime.mission_attempt a ON a.dataset_epoch = m.dataset_epoch AND a.mission_id = m.mission_id AND a.attempt = m.current_attempt " +
            "JOIN runtime.evidence_snapshot s ON s.dataset_epoch = m.dataset_epoch AND s.snapshot_id = m.snapshot_id " +
            "LEFT JOIN runtime.analysis_run ar ON ar.dataset_epoch = m.dataset_epoch AND ar.analysis_id = m.analysis_id " +
            "WHERE d.singleton = true AND m.dataset_epoch = ? AND m.mission_id = ? AND m.snapshot_id = ? " +
            "AND m.status = 'RUNNING' AND a.status = 'RUNNING' AND a.lease_token = ? AND a.attempt = ? AND a.lease_expires_at > now() " +
            "FOR UPDATE OF d, m, a", epoch, missionId, snapshotId, leaseToken, attempt);
        if (rows.size() != 1 || fencingToken != attempt) return false;
        var mission = rows.getFirst();
        if (((Number) mission.get("rule_set_version")).intValue() != ruleSetVersion
                || !java.util.Objects.equals(analysisId, mission.get("analysis_id"))
                || !java.util.Objects.equals(caseId, mission.get("case_id"))) return false;
        String kind = mission.get("kind").toString();
        if (kind.equals("INVESTIGATION") && !java.util.Set.of("AGENT_PENDING", "AGENT_RUNNING").contains(mission.get("analysis_status"))) return false;
        return switch (eventType) {
            case "DiagnosticProposed" -> kind.equals("INVESTIGATION");
            case "RuleChangeProposed", "RuleChangeNotProposed" -> kind.equals("RULE_SYNTHESIS") || kind.equals("RULE_REVISION") || kind.equals("EXPERT_TURN");
            case "TextChunkProduced" -> kind.equals("EXPERT_TURN");
            case "ToolObservationRecorded", "MissionFailed", "MissionCancelled" -> true;
            default -> false;
        };
    }

    public boolean isActiveInvestigation(UUID epoch, UUID missionId, UUID analysisId, UUID snapshotId, UUID leaseToken, int fencingToken) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT count(*) FROM runtime.agent_mission m JOIN runtime.mission_attempt a ON a.dataset_epoch = m.dataset_epoch AND a.mission_id = m.mission_id AND a.attempt = m.current_attempt JOIN runtime.analysis_run ar ON ar.dataset_epoch = m.dataset_epoch AND ar.analysis_id = m.analysis_id WHERE m.dataset_epoch = ? AND m.mission_id = ? AND m.analysis_id = ? AND m.snapshot_id = ? AND m.status = 'RUNNING' AND a.lease_token = ? AND a.attempt = ? AND a.status = 'RUNNING' AND a.lease_expires_at > now() AND ar.status IN ('AGENT_PENDING','AGENT_RUNNING')",
            Integer.class, epoch, missionId, analysisId, snapshotId, leaseToken, fencingToken);
        return count != null && count == 1;
    }

    @Transactional public void cancelOpenMissionsForAnalysis(UUID epoch, UUID analysisId) {
        Instant now = Instant.now();
        jdbcTemplate.update("UPDATE runtime.agent_mission SET status = 'CANCELLED', finished_at = ?, lock_version = lock_version + 1 WHERE dataset_epoch = ? AND analysis_id = ? AND status IN ('QUEUED','RUNNING')", Timestamp.from(now), epoch, analysisId);
        jdbcTemplate.update("UPDATE runtime.mission_attempt SET status = 'CANCELLED', finished_at = ? WHERE dataset_epoch = ? AND mission_id IN (SELECT mission_id FROM runtime.agent_mission WHERE dataset_epoch = ? AND analysis_id = ?) AND status = 'RUNNING'", Timestamp.from(now), epoch, epoch, analysisId);
    }
    @Transactional public void markMissionCompleted(UUID epoch, UUID missionId) { finish(epoch, missionId, "COMPLETED"); }
    @Transactional public void markMissionCancelled(UUID epoch, UUID missionId) { finish(epoch, missionId, "CANCELLED"); }
    @Transactional public void markMissionFailed(UUID epoch, UUID missionId) { finish(epoch, missionId, "FAILED"); }
    private void finish(UUID epoch, UUID missionId, String status) {
        Instant now = Instant.now();
        jdbcTemplate.update("UPDATE runtime.agent_mission SET status = ?, finished_at = ?, lock_version = lock_version + 1 WHERE dataset_epoch = ? AND mission_id = ? AND status = 'RUNNING'", status, Timestamp.from(now), epoch, missionId);
        jdbcTemplate.update("UPDATE runtime.mission_attempt SET status = ?, finished_at = ? WHERE dataset_epoch = ? AND mission_id = ? AND status = 'RUNNING'", status, Timestamp.from(now), epoch, missionId);
        jdbcTemplate.update("UPDATE runtime.conversation_turn SET status = ? WHERE dataset_epoch = ? AND turn_id = (SELECT turn_id FROM runtime.agent_mission WHERE dataset_epoch = ? AND mission_id = ?)", status, epoch, epoch, missionId);
    }
}
