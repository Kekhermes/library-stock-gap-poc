package com.library.stockgap.infrastructure.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.stockgap.domain.model.CaseDossier;
import com.library.stockgap.domain.model.CaseStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Repository
public class CaseRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public CaseRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    public List<Map<String, Object>> findCases(UUID epoch, String siteId, String status, String symptomKind, int page, int size) {
        StringBuilder sql = new StringBuilder(
            "SELECT c.case_id, c.copy_id, cp.barcode, t.title, cp.owning_site_id, " +
            "c.inventory_reference_time, gc.status, gc.current_acceptance_id " +
            "FROM runtime.source_case c " +
            "JOIN runtime.copy cp ON c.dataset_epoch = cp.dataset_epoch AND c.copy_id = cp.copy_id " +
            "JOIN runtime.title t ON cp.dataset_epoch = t.dataset_epoch AND cp.title_id = t.title_id " +
            "JOIN runtime.gap_case gc ON c.dataset_epoch = gc.dataset_epoch AND c.case_id = gc.case_id " +
            "WHERE c.dataset_epoch = ? "
        );
        List<Object> params = new ArrayList<>();
        params.add(epoch);

        if (siteId != null && !siteId.isBlank()) {
            sql.append("AND cp.owning_site_id = ? ");
            params.add(siteId);
        }
        if (status != null && !status.isBlank()) {
            sql.append("AND gc.status = ? ");
            params.add(status);
        }
        if (symptomKind != null && !symptomKind.isBlank()) {
            sql.append("AND EXISTS (SELECT 1 FROM runtime.gap_symptom gs WHERE gs.dataset_epoch = c.dataset_epoch AND gs.case_id = c.case_id AND gs.kind = ?) ");
            params.add(symptomKind);
        }

        sql.append("ORDER BY cp.barcode ASC LIMIT ? OFFSET ?");
        params.add(size);
        params.add(page * size);

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            UUID caseId = UUID.fromString(rs.getString("case_id"));
            map.put("caseId", caseId);
            map.put("copyId", UUID.fromString(rs.getString("copy_id")));
            map.put("barcode", rs.getString("barcode"));
            map.put("title", rs.getString("title"));
            map.put("owningSiteId", rs.getString("owning_site_id"));
            map.put("inventoryReferenceTime", rs.getTimestamp("inventory_reference_time").toInstant().toString());
            map.put("status", rs.getString("status"));
            String accId = rs.getString("current_acceptance_id");
            map.put("currentAcceptanceId", accId != null ? UUID.fromString(accId) : null);
            map.put("symptoms", getSymptoms(epoch, caseId));
            return map;
        }, params.toArray());
    }

    public int countCases(UUID epoch, String siteId, String status, String symptomKind) {
        StringBuilder sql = new StringBuilder(
            "SELECT count(*) FROM runtime.source_case c " +
            "JOIN runtime.copy cp ON c.dataset_epoch = cp.dataset_epoch AND c.copy_id = cp.copy_id " +
            "JOIN runtime.gap_case gc ON c.dataset_epoch = gc.dataset_epoch AND c.case_id = gc.case_id " +
            "WHERE c.dataset_epoch = ? "
        );
        List<Object> params = new ArrayList<>();
        params.add(epoch);

        if (siteId != null && !siteId.isBlank()) {
            sql.append("AND cp.owning_site_id = ? ");
            params.add(siteId);
        }
        if (status != null && !status.isBlank()) {
            sql.append("AND gc.status = ? ");
            params.add(status);
        }
        if (symptomKind != null && !symptomKind.isBlank()) {
            sql.append("AND EXISTS (SELECT 1 FROM runtime.gap_symptom gs WHERE gs.dataset_epoch = c.dataset_epoch AND gs.case_id = c.case_id AND gs.kind = ?) ");
            params.add(symptomKind);
        }

        Integer count = jdbcTemplate.queryForObject(sql.toString(), Integer.class, params.toArray());
        return count != null ? count : 0;
    }

    public Optional<CaseDossier> findCaseDossier(UUID epoch, UUID caseId) {
        String sql =
            "SELECT c.case_id, c.copy_id, cp.barcode, t.title, t.isbn, cp.owning_site_id, " +
            "c.inventory_reference_time, gc.status, gc.current_acceptance_id " +
            "FROM runtime.source_case c " +
            "JOIN runtime.copy cp ON c.dataset_epoch = cp.dataset_epoch AND c.copy_id = cp.copy_id " +
            "JOIN runtime.title t ON cp.dataset_epoch = t.dataset_epoch AND cp.title_id = t.title_id " +
            "JOIN runtime.gap_case gc ON c.dataset_epoch = gc.dataset_epoch AND c.case_id = gc.case_id " +
            "WHERE c.dataset_epoch = ? AND c.case_id = ?";

        var list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            UUID id = UUID.fromString(rs.getString("case_id"));
            UUID copyId = UUID.fromString(rs.getString("copy_id"));
            String barcode = rs.getString("barcode");
            String title = rs.getString("title");
            String isbn = rs.getString("isbn");
            String owningSiteId = rs.getString("owning_site_id");
            Instant refTime = rs.getTimestamp("inventory_reference_time").toInstant();
            CaseStatus status = CaseStatus.valueOf(rs.getString("status"));

            List<Map<String, Object>> symptoms = getSymptoms(epoch, id);
            Map<String, Object> systemState = getSystemState(epoch, copyId);
            List<Map<String, Object>> observations = getObservations(epoch, copyId);
            List<Map<String, Object>> sourceAvailability = getSourceAvailability(epoch, copyId);
            List<Map<String, Object>> operatorNotes = getOperatorNotes(epoch, copyId);
            List<Map<String, Object>> timeline = getTimelineEvents(epoch, copyId);
            List<Map<String, Object>> analyses = getAnalysesForCase(epoch, id);

            Map<String, Object> currentDiagnostic = getLatestDiagnosticForCase(epoch, id);
            Map<String, Object> currentProposal = getLatestProposalForCase(epoch, id);
            UUID conversationId = getConversationIdForCase(epoch, id);

            return new CaseDossier(
                id, copyId, barcode, title, isbn, owningSiteId, refTime, status,
                symptoms, systemState, observations, sourceAvailability, operatorNotes,
                timeline, analyses, currentDiagnostic, currentProposal, conversationId
            );
        }, epoch, caseId);

        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public List<Map<String, Object>> getSymptoms(UUID epoch, UUID caseId) {
        String sql = "SELECT symptom_id, position, kind, expected_value, observed_value " +
                     "FROM runtime.gap_symptom WHERE dataset_epoch = ? AND case_id = ? ORDER BY position ASC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("symptomId", UUID.fromString(rs.getString("symptom_id")));
            map.put("position", rs.getInt("position"));
            map.put("kind", rs.getString("kind"));
            map.put("expectedValue", rs.getString("expected_value"));
            map.put("observedValue", rs.getString("observed_value"));
            return map;
        }, epoch, caseId);
    }

    public Map<String, Object> getSystemState(UUID epoch, UUID copyId) {
        String sql = "SELECT status, site_id, location_id FROM runtime.system_state " +
                     "WHERE dataset_epoch = ? AND copy_id = ?";
        var list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("status", rs.getString("status"));
            map.put("siteId", rs.getString("site_id"));
            String loc = rs.getString("location_id");
            map.put("locationId", loc != null ? UUID.fromString(loc) : null);
            return map;
        }, epoch, copyId);
        return list.isEmpty() ? Collections.emptyMap() : list.get(0);
    }

    public List<Map<String, Object>> getObservations(UUID epoch, UUID copyId) {
        String sql = "SELECT o.observation_id, o.location_id, l.site_id, o.scope_id, o.observed_at, o.quality " +
                     "FROM runtime.observation o " +
                     "JOIN runtime.location l ON o.dataset_epoch = l.dataset_epoch AND o.location_id = l.location_id " +
                     "WHERE o.dataset_epoch = ? AND o.copy_id = ? ORDER BY o.observed_at ASC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("observationId", UUID.fromString(rs.getString("observation_id")));
            map.put("locationId", UUID.fromString(rs.getString("location_id")));
            map.put("siteId", rs.getString("site_id"));
            map.put("scopeId", UUID.fromString(rs.getString("scope_id")));
            map.put("observedAt", rs.getTimestamp("observed_at").toInstant().toString());
            map.put("quality", rs.getString("quality"));
            return map;
        }, epoch, copyId);
    }

    public List<Map<String, Object>> getSourceAvailability(UUID epoch, UUID copyId) {
        String sql = "SELECT source, status, complete_from, complete_to FROM runtime.source_availability " +
                     "WHERE dataset_epoch = ? AND copy_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("source", rs.getString("source"));
            map.put("status", rs.getString("status"));
            var cf = rs.getTimestamp("complete_from");
            map.put("completeFrom", cf != null ? cf.toInstant().toString() : null);
            var ct = rs.getTimestamp("complete_to");
            map.put("completeTo", ct != null ? ct.toInstant().toString() : null);
            return map;
        }, epoch, copyId);
    }

    public List<Map<String, Object>> getOperatorNotes(UUID epoch, UUID copyId) {
        String sql = "SELECT note_id, text, recorded_at FROM runtime.operator_note " +
                     "WHERE dataset_epoch = ? AND copy_id = ? ORDER BY recorded_at ASC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("noteId", UUID.fromString(rs.getString("note_id")));
            map.put("text", rs.getString("text"));
            map.put("recordedAt", rs.getTimestamp("recorded_at").toInstant().toString());
            return map;
        }, epoch, copyId);
    }

    public List<Map<String, Object>> getTimelineEvents(UUID epoch, UUID copyId) {
        String sql = "SELECT source_event_id, event_type, occurred_at, recorded_at, payload " +
                     "FROM runtime.source_event WHERE dataset_epoch = ? AND copy_id = ? " +
                     "ORDER BY occurred_at ASC, recorded_at ASC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("sourceEventId", UUID.fromString(rs.getString("source_event_id")));
            map.put("eventType", rs.getString("event_type"));
            map.put("occurredAt", rs.getTimestamp("occurred_at").toInstant().toString());
            map.put("recordedAt", rs.getTimestamp("recorded_at").toInstant().toString());
            try {
                map.put("payload", objectMapper.readValue(rs.getString("payload"), Map.class));
            } catch (Exception e) {
                map.put("payload", Collections.emptyMap());
            }
            return map;
        }, epoch, copyId);
    }

    public void updateCaseStatus(UUID epoch, UUID caseId, CaseStatus status) {
        jdbcTemplate.update(
            "UPDATE runtime.gap_case SET status = ?, lock_version = lock_version + 1 " +
            "WHERE dataset_epoch = ? AND case_id = ?",
            status.name(), epoch, caseId
        );
    }

    private List<Map<String, Object>> getAnalysesForCase(UUID epoch, UUID caseId) {
        String sql = "SELECT analysis_id, rule_set_version, status, created_at, finished_at, fallback_reason " +
                     "FROM runtime.analysis_run WHERE dataset_epoch = ? AND case_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("analysisId", UUID.fromString(rs.getString("analysis_id")));
            map.put("ruleSetVersion", rs.getInt("rule_set_version"));
            map.put("status", rs.getString("status"));
            map.put("createdAt", rs.getTimestamp("created_at").toInstant().toString());
            var comp = rs.getTimestamp("finished_at");
            map.put("finishedAt", comp != null ? comp.toInstant().toString() : null);
            map.put("fallbackReason", rs.getString("fallback_reason"));
            return map;
        }, epoch, caseId);
    }

    private Map<String, Object> getLatestDiagnosticForCase(UUID epoch, UUID caseId) {
        String sql = "SELECT dr.diagnostic_id, dr.analysis_id, dr.conclusion, dr.result, dr.producer " +
                     "FROM runtime.diagnostic_revision dr " +
                     "WHERE dr.dataset_epoch = ? AND dr.case_id = ? ORDER BY dr.created_at DESC LIMIT 1";
        var list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("diagnosticId", UUID.fromString(rs.getString("diagnostic_id")));
            map.put("analysisId", UUID.fromString(rs.getString("analysis_id")));
            map.put("conclusion", rs.getString("conclusion"));
            String producer = rs.getString("producer");
            map.put("isDeterministic", "DMN".equals(producer));
            try {
                Map<String, Object> res = objectMapper.readValue(rs.getString("result"), Map.class);
                map.put("summary", res.getOrDefault("summary", ""));
                map.put("findings", res.getOrDefault("findings", Collections.emptyList()));
                map.put("missingEvidence", res.getOrDefault("missingEvidence", Collections.emptyList()));
            } catch (Exception e) {
                map.put("summary", "");
                map.put("findings", Collections.emptyList());
                map.put("missingEvidence", Collections.emptyList());
            }
            return map;
        }, epoch, caseId);
        return list.isEmpty() ? null : list.get(0);
    }

    private Map<String, Object> getLatestProposalForCase(UUID epoch, UUID caseId) {
        String sql = "SELECT p.proposal_id, p.current_revision, p.status, " +
                     "pr.rationale, po.kind as operation, po.rule_id, po.dmn_xml, po.artifact_sha256, po.metadata " +
                     "FROM runtime.rule_change_proposal p " +
                     "JOIN runtime.proposal_revision pr ON p.dataset_epoch = pr.dataset_epoch AND p.proposal_id = pr.proposal_id AND p.current_revision = pr.revision " +
                     "JOIN runtime.evidence_snapshot es ON pr.dataset_epoch = es.dataset_epoch AND pr.origin_snapshot_id = es.snapshot_id " +
                     "LEFT JOIN runtime.proposal_operation po ON pr.dataset_epoch = po.dataset_epoch AND pr.proposal_id = po.proposal_id AND pr.revision = po.revision " +
                     "WHERE p.dataset_epoch = ? AND es.case_id = ? ORDER BY p.created_at DESC LIMIT 1";
        var list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("proposalId", UUID.fromString(rs.getString("proposal_id")));
            map.put("revision", rs.getInt("current_revision"));
            map.put("state", rs.getString("status"));
            map.put("operation", rs.getString("operation"));
            map.put("targetRuleId", rs.getString("rule_id"));
            map.put("dmnXml", rs.getString("dmn_xml"));
            map.put("artifactSha256", rs.getString("artifact_sha256"));
            try {
                Map<String, Object> rat = objectMapper.readValue(rs.getString("rationale"), Map.class);
                map.put("rationale", rat.getOrDefault("rationale", ""));
                map.put("ruleName", rat.getOrDefault("ruleName", rs.getString("rule_id")));
                map.put("causeCode", rat.getOrDefault("causeCode", ""));
                map.put("coveredSymptomKinds", rat.getOrDefault("coveredSymptomKinds", Collections.emptyList()));
            } catch (Exception e) {
                map.put("ruleName", rs.getString("rule_id"));
            }
            return map;
        }, epoch, caseId);
        return list.isEmpty() ? null : list.get(0);
    }

    private UUID getConversationIdForCase(UUID epoch, UUID caseId) {
        String sql = "SELECT conversation_id FROM runtime.expert_conversation " +
                     "WHERE dataset_epoch = ? AND case_id = ? LIMIT 1";
        var list = jdbcTemplate.query(sql, (rs, rowNum) -> UUID.fromString(rs.getString("conversation_id")), epoch, caseId);
        return list.isEmpty() ? null : list.get(0);
    }
}
