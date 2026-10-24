package com.library.stockgap.infrastructure.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        try { configJson = objectMapper.writeValueAsString(Map.of("model", "gpt-5.6-luna")); }
        catch (Exception e) { configJson = "{}"; }
        jdbcTemplate.update(
            "INSERT INTO runtime.agent_mission (dataset_epoch, mission_id, kind, analysis_id, origin_acceptance_id, snapshot_id, rule_set_version, turn_id, status, current_attempt, prompt_version, model_configuration, max_llm_calls, max_tool_calls, max_input_tokens, max_output_tokens, max_duration_seconds, started_at, deadline_at, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'QUEUED', 0, '1.0.0', ?::jsonb, 8, 16, 32000, 8000, 300, ?, ?, ?)",
            epoch, missionId, kind, analysisId, originAcceptanceId, targetSnapshotId, ruleSetVersion, turnId, configJson, Timestamp.from(now), Timestamp.from(now.plusSeconds(300)), Timestamp.from(now));
    }

    @Transactional
    public Map<String, Object> claimMission(UUID epoch, UUID missionId, String workerId) {
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
        return Map.of("status", "CLAIMED", "attempt", nextAttempt, "leaseToken", leaseToken, "fencingToken", nextAttempt, "expiresAt", now.plusSeconds(90).toString());
    }

    public boolean canReadSnapshot(UUID epoch, UUID missionId, UUID snapshotId, UUID leaseToken) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT count(*) FROM runtime.agent_mission m JOIN runtime.mission_attempt a ON a.dataset_epoch = m.dataset_epoch AND a.mission_id = m.mission_id AND a.attempt = m.current_attempt WHERE m.dataset_epoch = ? AND m.mission_id = ? AND m.snapshot_id = ? AND m.status = 'RUNNING' AND a.lease_token = ? AND a.status = 'RUNNING' AND a.lease_expires_at > now()",
            Integer.class, epoch, missionId, snapshotId, leaseToken);
        return count != null && count == 1;
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
    }
}
