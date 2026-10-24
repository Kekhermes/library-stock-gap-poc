package com.library.stockgap.infrastructure.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.stockgap.domain.model.ChatMessageRecord;
import com.library.stockgap.domain.model.EvidenceRef;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Repository
public class ConversationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public ConversationRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public UUID getOrCreateConversationForCase(UUID epoch, UUID caseId) {
        String findSql = "SELECT conversation_id FROM runtime.expert_conversation WHERE dataset_epoch = ? AND case_id = ?";
        var list = jdbcTemplate.query(findSql, (rs, rowNum) -> UUID.fromString(rs.getString("conversation_id")), epoch, caseId);
        if (!list.isEmpty()) {
            return list.get(0);
        }

        UUID convId = UUID.randomUUID();
        jdbcTemplate.update(
            "INSERT INTO runtime.expert_conversation (dataset_epoch, conversation_id, case_id, created_at) " +
            "VALUES (?, ?, ?, ?)",
            epoch, convId, caseId, Timestamp.from(Instant.now())
        );

        // Create turn 1
        UUID turnId = UUID.randomUUID();
        jdbcTemplate.update(
            "INSERT INTO runtime.conversation_turn (dataset_epoch, turn_id, conversation_id, turn_no, status, created_at) " +
            "VALUES (?, ?, ?, 1, 'WAITING_EXPERT', ?)",
            epoch, turnId, convId, Timestamp.from(Instant.now())
        );

        return convId;
    }

    public List<ChatMessageRecord> getMessages(UUID epoch, UUID conversationId) {
        String sql =
            "SELECT m.message_id, m.turn_id, m.sequence, m.speaker, m.content, m.references_json, m.created_at " +
            "FROM runtime.chat_message m " +
            "JOIN runtime.conversation_turn t ON m.dataset_epoch = t.dataset_epoch AND m.turn_id = t.turn_id " +
            "WHERE m.dataset_epoch = ? AND t.conversation_id = ? ORDER BY m.created_at ASC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            UUID msgId = UUID.fromString(rs.getString("message_id"));
            UUID turnId = UUID.fromString(rs.getString("turn_id"));
            int seq = rs.getInt("sequence");
            String speaker = rs.getString("speaker");
            String content = rs.getString("content");
            Instant createdAt = rs.getTimestamp("created_at").toInstant();

            List<EvidenceRef> refs = new ArrayList<>();
            try {
                List<Map<String, Object>> rawRefs = objectMapper.readValue(rs.getString("references_json"), List.class);
                for (Map<String, Object> rr : rawRefs) {
                    refs.add(new EvidenceRef(
                        (String) rr.get("source"),
                        rr.get("recordId") != null ? UUID.fromString((String) rr.get("recordId")) : null,
                        rr.get("evidenceSnapshotId") != null ? UUID.fromString((String) rr.get("evidenceSnapshotId")) : null,
                        (String) rr.get("fieldPath")
                    ));
                }
            } catch (Exception e) {
                // ignore
            }

            return new ChatMessageRecord(msgId, conversationId, seq, speaker, content, true, refs, createdAt);
        }, epoch, conversationId);
    }

    @Transactional
    public ChatMessageRecord addMessage(UUID epoch, UUID conversationId, String speaker, String content, List<EvidenceRef> refs) {
        // Get active turn
        String turnSql = "SELECT turn_id, next_turn_no FROM runtime.expert_conversation ec " +
                         "JOIN runtime.conversation_turn ct ON ec.dataset_epoch = ct.dataset_epoch AND ec.conversation_id = ct.conversation_id " +
                         "WHERE ec.dataset_epoch = ? AND ec.conversation_id = ? ORDER BY ct.turn_no DESC LIMIT 1";

        var turns = jdbcTemplate.query(turnSql, (rs, rowNum) -> Map.of(
            "turnId", UUID.fromString(rs.getString("turn_id")),
            "turnNo", rs.getInt("next_turn_no")
        ), epoch, conversationId);

        UUID turnId;
        int turnNo = 1;
        if (turns.isEmpty()) {
            turnId = UUID.randomUUID();
            jdbcTemplate.update(
                "INSERT INTO runtime.conversation_turn (dataset_epoch, turn_id, conversation_id, turn_no, status, created_at) " +
                "VALUES (?, ?, ?, 1, 'WAITING_EXPERT', ?)",
                epoch, turnId, conversationId, Timestamp.from(Instant.now())
            );
        } else {
            turnId = (UUID) turns.get(0).get("turnId");
            turnNo = (int) turns.get(0).get("turnNo");
        }

        // Get max sequence in turn
        Integer maxSeq = jdbcTemplate.queryForObject(
            "SELECT coalesce(max(sequence), 0) FROM runtime.chat_message WHERE dataset_epoch = ? AND turn_id = ?",
            Integer.class, epoch, turnId
        );
        int nextSeq = (maxSeq != null ? maxSeq : 0) + 1;

        UUID msgId = UUID.randomUUID();
        Instant now = Instant.now();

        try {
            String refsJson = objectMapper.writeValueAsString(refs != null ? refs : Collections.emptyList());
            jdbcTemplate.update(
                "INSERT INTO runtime.chat_message (dataset_epoch, message_id, turn_id, sequence, speaker, content, references_json, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?::jsonb, ?)",
                epoch, msgId, turnId, nextSeq, speaker, content, refsJson, Timestamp.from(now)
            );
        } catch (Exception e) {
            throw new RuntimeException("Erreur insertion message", e);
        }

        return new ChatMessageRecord(msgId, conversationId, nextSeq, speaker, content, true, refs, now);
    }
}
