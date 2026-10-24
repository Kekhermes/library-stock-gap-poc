package com.library.stockgap.infrastructure.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Repository
public class OutboxRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public OutboxRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void enqueueEvent(UUID epoch, UUID eventId, UUID missionId, String eventType, Map<String, Object> envelope) {
        Instant now = Instant.now();
        try {
            String json = objectMapper.writeValueAsString(envelope);
            jdbcTemplate.update(
                "INSERT INTO runtime.outbox_event (dataset_epoch, event_id, mission_id, event_type, envelope, created_at, available_at) " +
                "VALUES (?, ?, ?, ?, ?::jsonb, ?, ?)",
                epoch, eventId, missionId, eventType, json, Timestamp.from(now), Timestamp.from(now)
            );
        } catch (Exception e) {
            throw new RuntimeException("Erreur insertion outbox", e);
        }
    }

    @Transactional
    public List<Map<String, Object>> fetchPendingEvents(int limit) {
        Instant now = Instant.now();
        UUID token = UUID.randomUUID();
        Instant leaseUntil = now.plusSeconds(10);

        String sql =
            "SELECT event_id, dataset_epoch, mission_id, event_type, envelope " +
            "FROM runtime.outbox_event " +
            "WHERE published_at IS NULL AND available_at <= ? AND (dispatch_lease_until IS NULL OR dispatch_lease_until < ?) " +
            "ORDER BY created_at ASC LIMIT ? FOR UPDATE SKIP LOCKED";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("eventId", UUID.fromString(rs.getString("event_id")));
            map.put("datasetEpoch", UUID.fromString(rs.getString("dataset_epoch")));
            map.put("missionId", UUID.fromString(rs.getString("mission_id")));
            map.put("eventType", rs.getString("event_type"));
            try {
                map.put("envelope", objectMapper.readValue(rs.getString("envelope"), Map.class));
            } catch (Exception e) {
                map.put("envelope", Collections.emptyMap());
            }
            return map;
        }, Timestamp.from(now), Timestamp.from(now), limit);
    }

    @Transactional
    public void markPublished(UUID epoch, UUID eventId) {
        jdbcTemplate.update(
            "UPDATE runtime.outbox_event SET published_at = ? WHERE dataset_epoch = ? AND event_id = ?",
            Timestamp.from(Instant.now()), epoch, eventId
        );
    }
}
