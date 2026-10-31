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
import java.io.StringReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;

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

    private static String extractXmlAttribute(String xml, String attr) {
        if (xml == null) return null;
        try {
            XMLInputFactory factory = XMLInputFactory.newFactory();
            factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
            factory.setProperty("javax.xml.stream.isSupportingExternalEntities", false);
            var reader = factory.createXMLStreamReader(new StringReader(xml));
            try {
                while (reader.hasNext()) {
                    if (reader.next() == XMLStreamConstants.START_ELEMENT) return reader.getAttributeValue(null, attr);
                }
            } finally {
                reader.close();
            }
        } catch (Exception ignored) {
            return null;
        }
        return null;
    }

    public List<RuleMetadata> getActiveRules(UUID epoch) {
        return getRulesForVersion(epoch, getActiveRuleSetVersion(epoch));
    }

    public List<RuleMetadata> getRulesForVersion(UUID epoch, int activeVer) {
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

            String xmlNs = extractXmlAttribute(dmnXml, "namespace");
            String xmlName = extractXmlAttribute(dmnXml, "name");

            String name = (String) meta.getOrDefault("name", xmlName != null ? xmlName : ruleId);
            String origin = (String) meta.getOrDefault("origin", "AGENT");
            String dmnPath = (String) meta.getOrDefault("dmnPath", "rules/" + ruleId + "-v" + ver + ".dmn");
            String namespace = (String) meta.getOrDefault("namespace", xmlNs != null ? xmlNs : "urn:library-stock-gap:rules:" + ruleId + ":" + ver);
            String modelName = (String) meta.getOrDefault("modelName", xmlName != null ? xmlName : ruleId + "-v" + ver);
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

            String xmlNs = extractXmlAttribute(dmnXml, "namespace");
            String xmlName = extractXmlAttribute(dmnXml, "name");

            String name = (String) meta.getOrDefault("name", xmlName != null ? xmlName : id);
            String origin = (String) meta.getOrDefault("origin", "AGENT");
            String dmnPath = (String) meta.getOrDefault("dmnPath", "rules/" + id + "-v" + ver + ".dmn");
            String namespace = (String) meta.getOrDefault("namespace", xmlNs != null ? xmlNs : "urn:library-stock-gap:rules:" + id + ":" + ver);
            String modelName = (String) meta.getOrDefault("modelName", xmlName != null ? xmlName : id + "-v" + ver);
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
            "VALUES (?, ?, 1, 'DRAFT', ?)",
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
            String extractedNs = extractXmlAttribute(dmnXml, "namespace");
            String extractedName = extractXmlAttribute(dmnXml, "name");
            Map<String, Object> opMeta = new HashMap<>();
            opMeta.put("name", ruleName);
            opMeta.put("causeCode", causeCode);
            opMeta.put("coveredSymptomKinds", coveredSymptomKinds);
            opMeta.put("recommendations", List.of("Contrôler la conformité du stock."));
            opMeta.put("limitations", List.of("Proposition synthétisée par agent."));
            if (extractedNs != null) opMeta.put("namespace", extractedNs);
            if (extractedName != null) opMeta.put("modelName", extractedName);
            String opMetaJson = objectMapper.writeValueAsString(opMeta);

            int candidateVer = 1;
            Integer expectedVer = null;
            if ("REVISE".equals(operation)) {
                var existing = getRulesForVersion(epoch, baseRuleSetVersion).stream().filter(rule -> rule.ruleId().equals(targetRuleId))
                    .findFirst().orElseThrow(() -> new IllegalArgumentException("Règle à réviser absente de la version de base"));
                expectedVer = existing.ruleVersion();
                candidateVer = expectedVer + 1;
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

    public String requireProposalDmnXml(UUID epoch, UUID proposalId, int revision) {
        var rows = jdbcTemplate.queryForList(
            "SELECT po.dmn_xml FROM runtime.proposal_operation po " +
            "JOIN runtime.rule_change_proposal p ON p.dataset_epoch = po.dataset_epoch AND p.proposal_id = po.proposal_id " +
            "WHERE po.dataset_epoch = ? AND po.proposal_id = ? AND po.revision = ? AND po.operation_no = 1 " +
            "AND p.current_revision = po.revision AND p.status NOT IN ('PUBLISHED','REJECTED')",
            String.class, epoch, proposalId, revision);
        if (rows.size() != 1 || rows.getFirst() == null) {
            throw new IllegalArgumentException("Révision de proposition absente, périmée ou déjà publiée");
        }
        return rows.getFirst();
    }

    public record ProposalCandidate(UUID proposalId, int revision, int baseRuleSetVersion, UUID snapshotId,
                                    UUID acceptanceId, String operation, Integer expectedVersion,
                                    RuleMetadata rule, String fingerprint) {}

    public ProposalCandidate loadProposalCandidate(UUID epoch, UUID proposalId, int revision) {
        Map<String, Object> row = jdbcTemplate.queryForMap(
            "SELECT p.current_revision, p.status, pr.base_rule_set_version, pr.origin_acceptance_id, pr.origin_snapshot_id, " +
            "pr.content_sha256, pr.rationale, po.kind, po.rule_id, po.expected_rule_version, po.candidate_rule_version, " +
            "po.dmn_xml, po.artifact_sha256, po.metadata, " +
            "(SELECT count(*) FROM runtime.proposal_operation other WHERE other.dataset_epoch = po.dataset_epoch " +
            "AND other.proposal_id = po.proposal_id AND other.revision = po.revision) AS operation_count " +
            "FROM runtime.rule_change_proposal p JOIN runtime.proposal_revision pr ON pr.dataset_epoch = p.dataset_epoch " +
            "AND pr.proposal_id = p.proposal_id JOIN runtime.proposal_operation po ON po.dataset_epoch = pr.dataset_epoch " +
            "AND po.proposal_id = pr.proposal_id AND po.revision = pr.revision " +
            "WHERE p.dataset_epoch = ? AND p.proposal_id = ? AND pr.revision = ? AND po.operation_no = 1",
            epoch, proposalId, revision);
        String status = Objects.toString(row.get("status"), "");
        if (status.equals("PUBLISHED") || status.equals("REJECTED")) {
            throw new IllegalStateException("Proposition déjà publiée ou rejetée : aucune nouvelle activation possible");
        }
        if (((Number) row.get("current_revision")).intValue() != revision
                || ((Number) row.get("operation_count")).intValue() != 1) {
            throw new IllegalArgumentException("Révision absente, périmée, terminale ou changement multiple hors démo");
        }
        String operation = row.get("kind").toString();
        if (!Set.of("CREATE", "REVISE").contains(operation)) throw new IllegalArgumentException("Opération de proposition non prise en charge");
        UUID snapshotId = UUID.fromString(row.get("origin_snapshot_id").toString());
        UUID acceptanceId = row.get("origin_acceptance_id") == null ? null : UUID.fromString(row.get("origin_acceptance_id").toString());
        if (operation.equals("CREATE")) {
            if (acceptanceId == null) throw new IllegalArgumentException("Création sans diagnostic accepté");
            Integer accepted = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM runtime.diagnostic_acceptance a JOIN runtime.diagnostic_revision d " +
                "ON d.dataset_epoch = a.dataset_epoch AND d.diagnostic_id = a.diagnostic_id " +
                "JOIN runtime.analysis_run r ON r.dataset_epoch = d.dataset_epoch AND r.analysis_id = d.analysis_id " +
                "WHERE a.dataset_epoch = ? AND a.acceptance_id = ? AND r.snapshot_id = ? " +
                "AND d.conclusion = 'SUPPORTED' AND d.producer = 'INVESTIGATOR' AND r.status = 'COMPLETED'", Integer.class, epoch, acceptanceId, snapshotId);
            if (accepted == null || accepted != 1) throw new IllegalArgumentException("Diagnostic d'origine non accepté ou preuves différentes");
        }
        String xml = row.get("dmn_xml").toString();
        String artifactSha = AnalysisRepository.sha256Hex(xml);
        if (!artifactSha.equals(row.get("artifact_sha256").toString())) throw new IllegalArgumentException("Empreinte XML de proposition incorrecte");
        Map<String, Object> metadata = jsonMap(row.get("metadata"));
        RuleMetadata rule = new RuleMetadata(row.get("rule_id").toString(), ((Number) row.get("candidate_rule_version")).intValue(),
            Objects.toString(metadata.get("name"), row.get("rule_id").toString()), "AGENT", "proposal",
            Objects.toString(metadata.get("namespace"), extractXmlAttribute(xml, "namespace")),
            Objects.toString(metadata.get("modelName"), extractXmlAttribute(xml, "name")), "decision",
            Objects.toString(metadata.get("causeCode"), ""), strings(metadata.get("coveredSymptomKinds")),
            strings(metadata.get("recommendations")), strings(metadata.get("limitations")), artifactSha, xml, false, null, acceptanceId);
        Map<String, Object> content = new LinkedHashMap<>();
        content.put("proposalId", proposalId);
        content.put("revision", revision);
        content.put("baseRuleSetVersion", row.get("base_rule_set_version"));
        content.put("snapshotId", snapshotId);
        content.put("acceptanceId", acceptanceId);
        content.put("storedRevisionHash", row.get("content_sha256").toString());
        content.put("rationale", jsonMap(row.get("rationale")));
        content.put("operation", operation);
        content.put("expectedVersion", row.get("expected_rule_version"));
        content.put("rule", ruleContent(rule));
        String fingerprint = hashCanonical(content);
        return new ProposalCandidate(proposalId, revision, ((Number) row.get("base_rule_set_version")).intValue(), snapshotId,
            acceptanceId, operation, row.get("expected_rule_version") == null ? null : ((Number) row.get("expected_rule_version")).intValue(), rule, fingerprint);
    }

    public List<RuleMetadata> candidateRules(List<RuleMetadata> active, ProposalCandidate candidate) {
        List<RuleMetadata> result = new ArrayList<>(active);
        var existing = active.stream().filter(rule -> rule.ruleId().equals(candidate.rule().ruleId())).findFirst();
        if (candidate.operation().equals("CREATE") && existing.isPresent()) throw new IllegalArgumentException("Identité de règle déjà présente");
        if (candidate.operation().equals("REVISE")) {
            if (existing.isEmpty() || !Objects.equals(candidate.expectedVersion(), existing.get().ruleVersion())
                    || candidate.rule().ruleVersion() <= existing.get().ruleVersion()) {
                throw new IllegalArgumentException("Version de règle à réviser périmée");
            }
            result.removeIf(rule -> rule.ruleId().equals(candidate.rule().ruleId()));
        }
        result.add(candidate.rule());
        return List.copyOf(result);
    }

    public String manifestFingerprint(List<RuleMetadata> rules) {
        return hashCanonical(rules.stream().sorted(Comparator.comparing(RuleMetadata::ruleId)).map(this::ruleContent).toList());
    }

    private Map<String, Object> ruleContent(RuleMetadata rule) {
        Map<String, Object> content = new LinkedHashMap<>();
        content.put("ruleId", rule.ruleId());
        content.put("version", rule.ruleVersion());
        content.put("artifactSha256", AnalysisRepository.sha256Hex(rule.dmnXml()));
        content.put("name", rule.name());
        content.put("namespace", rule.namespace());
        content.put("modelName", rule.modelName());
        content.put("decision", rule.decisionName());
        content.put("causeCode", rule.causeCode());
        content.put("coveredSymptomKinds", rule.coveredSymptomKinds());
        content.put("recommendations", rule.recommendations());
        content.put("limitations", rule.limitations());
        return content;
    }

    private Map<String, Object> jsonMap(Object raw) {
        try {
            if (raw instanceof Map<?, ?>) return objectMapper.convertValue(raw, Map.class);
            return objectMapper.readValue(raw.toString(), Map.class);
        } catch (Exception failure) {
            throw new IllegalArgumentException("Métadonnées de proposition illisibles", failure);
        }
    }

    private List<String> strings(Object value) {
        if (value == null) return List.of();
        if (!(value instanceof List<?> values) || values.stream().anyMatch(item -> !(item instanceof String))) {
            throw new IllegalArgumentException("Liste de métadonnées non conforme");
        }
        return values.stream().map(Object::toString).toList();
    }

    private String hashCanonical(Object value) {
        try {
            return AnalysisRepository.sha256Hex(objectMapper.writeValueAsString(canonical(value)));
        } catch (Exception failure) {
            throw new IllegalArgumentException("Calcul d'empreinte impossible", failure);
        }
    }

    private Object canonical(Object value) {
        if (value instanceof Map<?, ?> map) {
            Map<String, Object> result = new TreeMap<>();
            map.forEach((key, item) -> result.put(key.toString(), canonical(item)));
            return result;
        }
        if (value instanceof List<?> list) return list.stream().map(this::canonical).toList();
        return value;
    }

    public void setProposalStatus(UUID epoch, UUID proposalId, int revision, String status) {
        int changed = jdbcTemplate.update(
            "UPDATE runtime.rule_change_proposal SET status = ?, lock_version = lock_version + 1 " +
            "WHERE dataset_epoch = ? AND proposal_id = ? AND current_revision = ? AND status NOT IN ('PUBLISHED','REJECTED')",
            status, epoch, proposalId, revision);
        if (changed != 1) throw new IllegalStateException("La proposition a changé : rechargez sa révision");
    }

    @Transactional
    public void insertValidationReport(UUID epoch, ValidationReport rep) {
        try {
            Map<String, Object> rMap = new LinkedHashMap<>();
            rMap.put("reportId", rep.reportId());
            rMap.put("proposalId", rep.proposalId());
            rMap.put("proposalRevision", rep.proposalRevision());
            rMap.put("baseRuleSetVersion", rep.baseRuleSetVersion());
            rMap.put("passed", rep.passed());
            rMap.put("publishable", rep.publishable());
            rMap.put("status", rep.status());
            rMap.put("positiveCasesTested", rep.positiveCasesTested());
            rMap.put("positiveCasesPassed", rep.positiveCasesPassed());
            rMap.put("negativeCasesTested", rep.negativeCasesTested());
            rMap.put("negativeCasesPassed", rep.negativeCasesPassed());
            rMap.put("totalReplayEvaluated", rep.totalReplayEvaluated());
            rMap.put("regressionsDetected", rep.regressionsDetected());
            rMap.put("conflicts", rep.conflicts());
            rMap.put("coverageLosses", rep.coverageLosses());
            rMap.put("candidateSha256", rep.candidateSha256());
            rMap.put("proposalSha256", rep.proposalSha256());
            rMap.put("candidateManifestSha256", rep.candidateManifestSha256());
            rMap.put("corpusSha256", rep.corpusSha256());
            rMap.put("caseResults", rep.caseResults());
            rMap.put("evaluatedAt", rep.evaluatedAt().toString());
            String repJson = objectMapper.writeValueAsString(rMap);

            jdbcTemplate.update(
                "INSERT INTO runtime.validation_report (dataset_epoch, report_id, proposal_id, proposal_revision, " +
                "base_rule_set_version, proposal_sha256, candidate_manifest_sha256, corpus_sha256, engine_version, " +
                "fact_schema_version, validator_version, outcome, blocking_count, report, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'KIE 10.2.0', '1.0.0', ?, ?, ?, ?::jsonb, ?)",
                epoch, rep.reportId(), rep.proposalId(), rep.proposalRevision(), rep.baseRuleSetVersion(),
                rep.proposalSha256(), rep.candidateManifestSha256(), rep.corpusSha256(),
                com.library.stockgap.domain.service.RuleValidationPolicy.VERSION,
                rep.passed() ? "PASS" : "BLOCKED", rep.passed() ? 0 : 1, repJson, Timestamp.from(Instant.now())
            );
        } catch (Exception e) {
            throw new RuntimeException("Erreur insertion validation report", e);
        }
    }

    public UUID requirePassingValidationReport(UUID epoch, UUID proposalId, int revision) {
        var reports = jdbcTemplate.query(
            "SELECT report_id FROM runtime.validation_report WHERE dataset_epoch = ? AND proposal_id = ? AND proposal_revision = ? AND outcome = 'PASS' ORDER BY created_at DESC LIMIT 1",
            (rs, rowNum) -> UUID.fromString(rs.getString("report_id")), epoch, proposalId, revision);
        if (reports.isEmpty()) {
            throw new IllegalStateException("Aucun rapport de validation PASS pour cette proposition");
        }
        return reports.getFirst();
    }

    public Optional<Map<String, Object>> latestValidationReport(UUID epoch, UUID proposalId, int revision) {
        var reports = jdbcTemplate.query(
            "SELECT report FROM runtime.validation_report WHERE dataset_epoch = ? AND proposal_id = ? AND proposal_revision = ? ORDER BY created_at DESC LIMIT 1",
            (rs, rowNum) -> jsonMap(rs.getString("report")), epoch, proposalId, revision);
        return reports.isEmpty() ? Optional.empty() : Optional.of(reports.getFirst());
    }

    public Optional<RuleMetadata> latestRuleVersion(UUID epoch, String ruleId) {
        Integer version = jdbcTemplate.queryForObject("SELECT max(rule_version) FROM runtime.rule_version WHERE dataset_epoch = ? AND rule_id = ?",
            Integer.class, epoch, ruleId);
        return version == null ? Optional.empty() : getRuleVersion(epoch, ruleId, version);
    }

    @Transactional
    public int publishProposal(UUID epoch, UUID proposalId, int proposalRevision, UUID reportId) {
        int currentVer = lockActiveVersion(epoch);
        var proposal = jdbcTemplate.queryForMap(
            "SELECT current_revision, status FROM runtime.rule_change_proposal WHERE dataset_epoch = ? AND proposal_id = ? FOR UPDATE",
            epoch, proposalId);
        if (!"READY_FOR_APPROVAL".equals(proposal.get("status")) || ((Number) proposal.get("current_revision")).intValue() != proposalRevision) {
            throw new IllegalStateException("Proposition non prête ou révision périmée");
        }
        var reports = jdbcTemplate.queryForList(
            "SELECT * FROM runtime.validation_report WHERE dataset_epoch = ? AND report_id = ? AND proposal_id = ? AND proposal_revision = ?",
            epoch, reportId, proposalId, proposalRevision);
        if (reports.size() != 1) throw new IllegalStateException("Rapport absent ou appartenant à une autre révision");
        var validation = reports.getFirst();
        if (!"PASS".equals(validation.get("outcome")) || ((Number) validation.get("blocking_count")).intValue() != 0
                || !com.library.stockgap.domain.service.RuleValidationPolicy.VERSION.equals(validation.get("validator_version"))
                || !"KIE 10.2.0".equals(validation.get("engine_version")) || !"1.0.0".equals(validation.get("fact_schema_version"))) {
            throw new IllegalStateException("Rapport non réussi ou ancien validateur : nouveaux tests requis");
        }
        var candidate = loadProposalCandidate(epoch, proposalId, proposalRevision);
        if (candidate.baseRuleSetVersion() != currentVer || ((Number) validation.get("base_rule_set_version")).intValue() != currentVer) {
            throw new IllegalStateException("Le référentiel a changé depuis les tests : nouvelle révision requise");
        }
        String candidateManifest = manifestFingerprint(candidateRules(getRulesForVersion(epoch, currentVer), candidate));
        if (!candidate.fingerprint().equals(validation.get("proposal_sha256"))
                || !candidateManifest.equals(validation.get("candidate_manifest_sha256"))
                || !currentCorpusFingerprint().equals(validation.get("corpus_sha256"))) {
            throw new IllegalStateException("Contenu, manifeste ou corpus différent du rapport testé");
        }
        Map<String, Object> details = jsonMap(validation.get("report"));
        if (!Boolean.TRUE.equals(details.get("passed")) || !Boolean.TRUE.equals(details.get("publishable"))
                || ((Number) details.get("positiveCasesTested")).intValue() < 2
                || ((Number) details.get("negativeCasesTested")).intValue() < 1
                || ((Number) details.get("totalReplayEvaluated")).intValue() < 1) {
            throw new IllegalStateException("Rapport incomplet : aucune activation sans tests et replay réels");
        }
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
                "VALUES (?, ?, ?, ?)",
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

        // Publication version is allocated from the current active pointer, not the historical
        // proposal base. This prevents a second approved proposal from colliding on v2.
        int newVer = currentVer + 1;
        UUID commandId = UUID.randomUUID();
        String manifestSha = candidateManifest;

        jdbcTemplate.update(
            "INSERT INTO runtime.rule_set_publication (dataset_epoch, rule_set_version, previous_version, kind, " +
            "proposal_id, proposal_revision, validation_report_id, manifest_sha256, approved_by, command_id, created_at) " +
            "VALUES (?, ?, ?, 'AGENT_CHANGE', ?, ?, ?, ?, 'Admin', ?, ?)",
            epoch, newVer, currentVer, proposalId, proposalRevision, reportId, manifestSha, commandId, Timestamp.from(now)
        );

        // 4. Copy existing members from baseVer, replacing or adding this rule
        List<Map<String, Object>> prevMembers = jdbcTemplate.queryForList(
            "SELECT rule_id, rule_version FROM runtime.rule_set_member WHERE dataset_epoch = ? AND rule_set_version = ?",
            epoch, currentVer
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
        int switched = jdbcTemplate.update(
            "UPDATE runtime.active_rule_set SET rule_set_version = ?, lock_version = lock_version + 1 " +
            "WHERE dataset_epoch = ? AND rule_set_version = ?",
            newVer, epoch, currentVer
        );
        if (switched != 1) throw new IllegalStateException("Conflit de publication : pointeur actif modifié");

        // 7. Update proposal state
        jdbcTemplate.update(
            "UPDATE runtime.rule_change_proposal SET status = 'PUBLISHED', lock_version = lock_version + 1 " +
            "WHERE dataset_epoch = ? AND proposal_id = ?",
            epoch, proposalId
        );
        markOtherProposalsStale(epoch, newVer);

        return newVer;
    }

    @Transactional
    public int retireRule(UUID epoch, String ruleId) {
        int currentVer = lockActiveVersion(epoch);
        var currentRules = getRulesForVersion(epoch, currentVer);
        if (currentRules.stream().noneMatch(rule -> rule.ruleId().equals(ruleId))) throw new IllegalArgumentException("Règle non active");
        int newVer = currentVer + 1;
        UUID commandId = UUID.randomUUID();
        Instant now = Instant.now();
        String manifestSha = manifestFingerprint(currentRules.stream().filter(rule -> !rule.ruleId().equals(ruleId)).toList());

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
        markOtherProposalsStale(epoch, newVer);

        return newVer;
    }

    private int lockActiveVersion(UUID epoch) {
        var pointer = jdbcTemplate.queryForMap(
            "SELECT a.dataset_epoch, r.rule_set_version FROM runtime.active_dataset a " +
            "JOIN runtime.active_rule_set r ON r.dataset_epoch = a.dataset_epoch WHERE a.singleton = true FOR UPDATE OF a, r");
        if (!epoch.equals(UUID.fromString(pointer.get("dataset_epoch").toString()))) throw new IllegalStateException("Époque active remplacée depuis la commande");
        return ((Number) pointer.get("rule_set_version")).intValue();
    }

    private String currentCorpusFingerprint() {
        try (var input = getClass().getResourceAsStream("/rules/validation-corpus.json")) {
            if (input == null) throw new IllegalStateException("Corpus de validation absent");
            return AnalysisRepository.sha256Hex(new String(input.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8));
        } catch (java.io.IOException failure) {
            throw new IllegalStateException("Corpus de validation illisible", failure);
        }
    }

    private void markOtherProposalsStale(UUID epoch, int activeVersion) {
        jdbcTemplate.update(
            "UPDATE runtime.rule_change_proposal p SET status = 'STALE', lock_version = lock_version + 1 " +
            "WHERE p.dataset_epoch = ? AND p.status = 'READY_FOR_APPROVAL' AND EXISTS " +
            "(SELECT 1 FROM runtime.proposal_revision r WHERE r.dataset_epoch = p.dataset_epoch AND r.proposal_id = p.proposal_id " +
            "AND r.revision = p.current_revision AND r.base_rule_set_version <> ?)", epoch, activeVersion);
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
            map.put("validationReport", latestValidationReport(epoch, (UUID) map.get("proposalId"), rs.getInt("current_revision")).orElse(null));
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
