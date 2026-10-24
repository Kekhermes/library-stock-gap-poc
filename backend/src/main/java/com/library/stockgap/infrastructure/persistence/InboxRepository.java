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
public class InboxRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public InboxRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public boolean recordAndCheckIfNew(UUID eventId, UUID epoch, UUID missionId, int attempt, long sequence,
                                       String eventType, Map<String, Object> envelope) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT count(*) FROM runtime.inbox_event WHERE event_id = ?",
            Integer.class, eventId
        );
        if (count != null && count > 0) {
            return false; // Duplicate
        }

        Instant now = Instant.now();
        try {
            String envJson = objectMapper.writeValueAsString(envelope);
            String sha = AnalysisRepository.sha256Hex(envJson);

            jdbcTemplate.update(
                "INSERT INTO runtime.inbox_event (event_id, dataset_epoch, mission_id, attempt, sequence, " +
                "event_type, payload_sha256, envelope, disposition, received_at, applied_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?::jsonb, 'APPLIED', ?, ?)",
                eventId, epoch, missionId, attempt, sequence, eventType, sha, envJson, Timestamp.from(now), Timestamp.from(now)
            );
            return true;
        } catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(InboxRepository.class).error("Erreur insertion inbox_event: {}", e.getMessage());
            return false;
        }
    }
}
