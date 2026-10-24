package com.library.stockgap.infrastructure.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Repository
public class MissionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public MissionRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void createMission(UUID epoch, UUID missionId, String kind, UUID analysisId, UUID originAcceptanceId,
                              UUID targetSnapshotId, int ruleSetVersion, UUID turnId) {
        Instant now = Instant.now();
        Instant deadline = now.plusSeconds(300);
        int maxLlm = "EXPERT_TURN".equals(kind) ? 4 : 8;
        int maxSec = "EXPERT_TURN".equals(kind) ? 120 : 300;

        Map<String, Object> config = Map.of("model", "gpt-5.6-luna", "temperature", 0.0);
        String configJson;
        try {
            configJson = objectMapper.writeValueAsString(config);
        } catch (Exception e) {
            configJson = "{}";
        }

        jdbcTemplate.update(
            "INSERT INTO runtime.agent_mission (dataset_epoch, mission_id, kind, analysis_id, origin_acceptance_id, " +
            "snapshot_id, rule_set_version, turn_id, status, current_attempt, prompt_version, model_configuration, " +
            "max_llm_calls, max_tool_calls, max_input_tokens, max_output_tokens, max_duration_seconds, " +
            "started_at, deadline_at, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'QUEUED', 0, '1.0.0', ?::jsonb, " +
            "?, 16, 32000, 8000, ?, ?, ?, ?)",
            epoch, missionId, kind, analysisId, originAcceptanceId, targetSnapshotId, ruleSetVersion, turnId,
            configJson, maxLlm, maxSec, Timestamp.from(now), Timestamp.from(deadline), Timestamp.from(now)
        );
    }

    @Transactional
    public Map<String, Object> claimMission(UUID epoch, UUID missionId, String workerId) {
        // Find mission
        String sql = "SELECT * FROM runtime.agent_mission WHERE dataset_epoch = ? AND mission_id = ? FOR UPDATE";
        var list = jdbcTemplate.queryForMap(sql, epoch, missionId);
        String status = (String) list.get("status");
        int currentAttempt = ((Number) list.get("current_attempt")).intValue();

        if ("COMPLETED".equals(status) || "CANCELLED".equals(status) || "FAILED".equals(status)) {
            return Map.of("status", "TERMINAL");
        }
        if (currentAttempt >= 2) {
            return Map.of("status", "EXHAUSTED");
        }

        int nextAttempt = currentAttempt + 1;
        UUID leaseToken = UUID.randomUUID();
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(30);

        jdbcTemplate.update(
            "INSERT INTO runtime.mission_attempt (dataset_epoch, mission_id, attempt, lease_token, worker_id, " +
            "status, claimed_at, heartbeat_at, lease_expires_at) VALUES (?, ?, ?, ?, ?, 'RUNNING', ?, ?, ?)",
            epoch, missionId, nextAttempt, leaseToken, workerId, Timestamp.from(now), Timestamp.from(now), Timestamp.from(expiresAt)
        );

        jdbcTemplate.update(
            "UPDATE runtime.agent_mission SET status = 'RUNNING', current_attempt = ?, lock_version = lock_version + 1 " +
            "WHERE dataset_epoch = ? AND mission_id = ?",
            nextAttempt, epoch, missionId
        );

        return Map.of(
            "status", "CLAIMED",
            "attempt", nextAttempt,
            "leaseToken", leaseToken,
            "fencingToken", nextAttempt,
            "expiresAt", expiresAt.toString()
        );
    }

    @Transactional
    public boolean heartbeat(UUID epoch, UUID missionId, UUID leaseToken) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(30);

        int updated = jdbcTemplate.update(
            "UPDATE runtime.mission_attempt SET heartbeat_at = ?, lease_expires_at = ? " +
            "WHERE dataset_epoch = ? AND mission_id = ? AND lease_token = ? AND status = 'RUNNING'",
            Timestamp.from(now), Timestamp.from(expiresAt), epoch, missionId, leaseToken
        );
        return updated > 0;
    }

    @Transactional
    public Map<String, Object> authorizeCall(UUID epoch, UUID missionId, UUID leaseToken, String callType, String toolName) {
        // Verify lease
        Integer count = jdbcTemplate.queryForObject(
            "SELECT count(*) FROM runtime.mission_attempt WHERE dataset_epoch = ? AND mission_id = ? AND lease_token = ? AND status = 'RUNNING' AND lease_expires_at > now()",
            Integer.class, epoch, missionId, leaseToken
        );
        if (count == null || count == 0) {
            return Map.of("authorized", false, "reason", "LEASE_EXPIRED_OR_INVALID");
        }

        UUID callId = UUID.randomUUID();
        return Map.of(
            "authorized", true,
            "callId", callId,
            "callAuthorization", callId.toString()
        );
    }

    @Transactional
    public void markMissionCompleted(UUID epoch, UUID missionId) {
        Instant now = Instant.now();
        jdbcTemplate.update(
            "UPDATE runtime.agent_mission SET status = 'COMPLETED', finished_at = ?, lock_version = lock_version + 1 " +
            "WHERE dataset_epoch = ? AND mission_id = ?",
            Timestamp.from(now), epoch, missionId
        );
        jdbcTemplate.update(
            "UPDATE runtime.mission_attempt SET status = 'COMPLETED', finished_at = ? " +
            "WHERE dataset_epoch = ? AND mission_id = ? AND status = 'RUNNING'",
            Timestamp.from(now), epoch, missionId
        );
    }

    @Transactional
    public void markMissionCancelled(UUID epoch, UUID missionId) {
        Instant now = Instant.now();
        jdbcTemplate.update(
            "UPDATE runtime.agent_mission SET status = 'CANCELLED', finished_at = ?, lock_version = lock_version + 1 " +
            "WHERE dataset_epoch = ? AND mission_id = ?",
            Timestamp.from(now), epoch, missionId
        );
        jdbcTemplate.update(
            "UPDATE runtime.mission_attempt SET status = 'CANCELLED', finished_at = ? " +
            "WHERE dataset_epoch = ? AND mission_id = ? AND status = 'RUNNING'",
            Timestamp.from(now), epoch, missionId
        );
    }
}
