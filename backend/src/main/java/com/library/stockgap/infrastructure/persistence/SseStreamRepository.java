package com.library.stockgap.infrastructure.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.stockgap.domain.model.SseNotification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Repository
public class SseStreamRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public SseStreamRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public SseNotification publishEvent(UUID epoch, String eventType, String aggregateType, String aggregateId, Map<String, Object> data) {
        // Lock and increment cursor
        Long nextCursor = jdbcTemplate.queryForObject(
            "UPDATE runtime.sse_stream SET last_cursor = last_cursor + 1 WHERE dataset_epoch = ? RETURNING last_cursor",
            Long.class,
            epoch
        );
        if (nextCursor == null) {
            throw new IllegalStateException("sse_stream inexistant pour l'époque: " + epoch);
        }

        Instant now = Instant.now();
        String jsonPayload;
        try {
            jsonPayload = objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            throw new RuntimeException("Erreur de sérialisation JSON pour événement SSE", e);
        }

        jdbcTemplate.update(
            "INSERT INTO runtime.progress_event (dataset_epoch, cursor, event_type, aggregate_type, aggregate_id, payload, created_at) " +
            "VALUES (?, ?, ?, ?, ?, ?::jsonb, ?)",
            epoch, nextCursor, eventType, aggregateType, aggregateId, jsonPayload, Timestamp.from(now)
        );

        String cursorStr = epoch + ":" + nextCursor;
        return new SseNotification(cursorStr, eventType, "1.0.0", epoch, now, data);
    }

    public List<SseNotification> getEventsAfter(UUID epoch, long afterCursor, int limit) {
        String sql = "SELECT cursor, event_type, payload, created_at FROM runtime.progress_event " +
                     "WHERE dataset_epoch = ? AND cursor > ? ORDER BY cursor ASC LIMIT ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            long cursor = rs.getLong("cursor");
            String eventType = rs.getString("event_type");
            String json = rs.getString("payload");
            Instant createdAt = rs.getTimestamp("created_at").toInstant();
            Map<String, Object> data;
            try {
                data = objectMapper.readValue(json, Map.class);
            } catch (Exception e) {
                data = Collections.emptyMap();
            }
            return new SseNotification(epoch + ":" + cursor, eventType, "1.0.0", epoch, createdAt, data);
        }, epoch, afterCursor, limit);
    }

    public long getCurrentCursorPosition(UUID epoch) {
        Long cursor = jdbcTemplate.queryForObject(
            "SELECT last_cursor FROM runtime.sse_stream WHERE dataset_epoch = ?",
            Long.class,
            epoch
        );
        return cursor != null ? cursor : 0L;
    }

    public String getCurrentCursor(UUID epoch) {
        return epoch + ":" + getCurrentCursorPosition(epoch);
    }
}
