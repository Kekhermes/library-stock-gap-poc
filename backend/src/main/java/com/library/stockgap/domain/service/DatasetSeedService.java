package com.library.stockgap.domain.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.stockgap.infrastructure.persistence.AnalysisRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
public class DatasetSeedService {
    private static final Logger log = LoggerFactory.getLogger(DatasetSeedService.class);

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;
    private final boolean autoSeed;

    public DatasetSeedService(JdbcTemplate jdbcTemplate,
                              ObjectMapper objectMapper,
                              ResourceLoader resourceLoader,
                              @Value("${stockgap.auto-seed:true}") boolean autoSeed) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
        this.resourceLoader = resourceLoader;
        this.autoSeed = autoSeed;
    }

    @PostConstruct
    public void init() {
        if (!autoSeed) return;
        try {
            Integer count = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM runtime.active_dataset WHERE singleton = true", Integer.class
            );
            if (count == null || count == 0) {
                log.info("Base de données sans époque active. Lancement du seed initial...");
                seedDemoDataset();
                log.info("Seed initial terminé avec succès.");
            } else {
                log.info("Époque active déjà présente. Aucun re-seed nécessaire.");
            }
        } catch (Exception e) {
            log.warn("Vérification d'époque au démarrage: {}", e.getMessage());
        }
    }

    @Transactional
    public UUID seedDemoDataset() {
        try {
            Resource dataResource = resourceLoader.getResource("classpath:seed/demo-runtime.json");
            Resource dmnResource = resourceLoader.getResource("classpath:rules/R001-v1.dmn");

            byte[] dmnBytes;
            try (InputStream is = dmnResource.getInputStream()) {
                dmnBytes = is.readAllBytes();
            }
            String dmnXml = new String(dmnBytes, StandardCharsets.UTF_8);
            String dmnSha = AnalysisRepository.sha256Hex(dmnXml);

            JsonNode root;
            byte[] dataBytes;
            try (InputStream is = dataResource.getInputStream()) {
                dataBytes = is.readAllBytes();
            }
            root = objectMapper.readTree(dataBytes);
            String runtimeSha = AnalysisRepository.sha256Hex(new String(dataBytes, StandardCharsets.UTF_8));

            JsonNode dsNode = root.get("dataset");
            UUID datasetId = UUID.fromString(dsNode.get("datasetId").asText());
            String profile = dsNode.get("profile").asText();
            long seed = dsNode.get("seed").asLong();
            Instant refTime = Instant.parse(dsNode.get("referenceTime").asText());
            Instant capturedAt = Instant.parse(dsNode.get("capturedAt").asText());
            String generatorVersion = "1.0.0";

            Instant now = Instant.now();
            UUID epoch = UUID.randomUUID();

            // 1. runtime.dataset
            jdbcTemplate.update(
                "INSERT INTO runtime.dataset (dataset_id, schema_version, profile, seed, reference_time, captured_at, generator_version, runtime_sha256) " +
                "VALUES (?, '1.0.0', ?, ?, ?, ?, ?, ?) ON CONFLICT (dataset_id) DO NOTHING",
                datasetId, profile, seed, Timestamp.from(refTime), Timestamp.from(capturedAt), generatorVersion, runtimeSha
            );

            // 2. runtime.dataset_epoch
            jdbcTemplate.update(
                "INSERT INTO runtime.dataset_epoch (dataset_epoch, dataset_id, imported_at) VALUES (?, ?, ?)",
                epoch, datasetId, Timestamp.from(now)
            );

            // 3. Sites
            JsonNode sites = root.get("sites");
            if (sites != null) {
                for (JsonNode s : sites) {
                    jdbcTemplate.update(
                        "INSERT INTO runtime.site (dataset_epoch, site_id, name) VALUES (?, ?, ?)",
                        epoch, s.get("siteId").asText(), s.get("name").asText()
                    );
                }
            }

            // 4. Locations
            JsonNode locations = root.get("locations");
            if (locations != null) {
                for (JsonNode l : locations) {
                    jdbcTemplate.update(
                        "INSERT INTO runtime.location (dataset_epoch, location_id, site_id, kind, label) VALUES (?, ?, ?, ?, ?)",
                        epoch, UUID.fromString(l.get("locationId").asText()), l.get("siteId").asText(), l.get("kind").asText(), l.get("label").asText()
                    );
                }
            }

            // 5. Titles
            JsonNode titles = root.get("titles");
            if (titles != null) {
                for (JsonNode t : titles) {
                    jdbcTemplate.update(
                        "INSERT INTO runtime.title (dataset_epoch, title_id, isbn, title) VALUES (?, ?, ?, ?)",
                        epoch, UUID.fromString(t.get("titleId").asText()), t.get("isbn").asText(), t.get("title").asText()
                    );
                }
            }

            // 6. Copies
            JsonNode copies = root.get("copies");
            if (copies != null) {
                for (JsonNode c : copies) {
                    jdbcTemplate.update(
                        "INSERT INTO runtime.copy (dataset_epoch, copy_id, barcode, title_id, owning_site_id) VALUES (?, ?, ?, ?, ?)",
                        epoch, UUID.fromString(c.get("copyId").asText()), c.get("barcode").asText(),
                        UUID.fromString(c.get("titleId").asText()), c.get("owningSiteId").asText()
                    );
                }
            }

            // 7. Source Events
            JsonNode sourceEvents = root.get("sourceEvents");
            if (sourceEvents != null) {
                for (JsonNode se : sourceEvents) {
                    UUID seId = UUID.fromString(se.get("sourceEventId").asText());
                    UUID copyId = UUID.fromString(se.get("copyId").asText());
                    String eventType = se.get("eventType").asText();
                    Instant occAt = Instant.parse(se.get("occurredAt").asText());
                    Instant recAt = Instant.parse(se.get("recordedAt").asText());
                    String payloadJson = objectMapper.writeValueAsString(se.get("payload"));

                    jdbcTemplate.update(
                        "INSERT INTO runtime.source_event (dataset_epoch, source_event_id, copy_id, event_type, occurred_at, recorded_at, payload) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?::jsonb)",
                        epoch, seId, copyId, eventType, Timestamp.from(occAt), Timestamp.from(recAt), payloadJson
                    );
                }
            }

            // 8. System States
            JsonNode systemStates = root.get("systemStates");
            if (systemStates != null) {
                for (JsonNode ss : systemStates) {
                    UUID copyId = UUID.fromString(ss.get("copyId").asText());
                    String status = ss.get("status").asText();
                    String siteId = ss.get("siteId").asText();
                    JsonNode locNode = ss.get("locationId");
                    UUID locId = (locNode != null && !locNode.isNull()) ? UUID.fromString(locNode.asText()) : null;

                    jdbcTemplate.update(
                        "INSERT INTO runtime.system_state (dataset_epoch, copy_id, status, site_id, location_id) VALUES (?, ?, ?, ?, ?)",
                        epoch, copyId, status, siteId, locId
                    );

                    JsonNode eventIds = ss.get("sourceEventIds");
                    if (eventIds != null) {
                        int pos = 0;
                        for (JsonNode eid : eventIds) {
                            jdbcTemplate.update(
                                "INSERT INTO runtime.system_state_event (dataset_epoch, copy_id, position, source_event_id) VALUES (?, ?, ?, ?)",
                                epoch, copyId, pos++, UUID.fromString(eid.asText())
                            );
                        }
                    }
                }
            }

            // 9. Inventory Scopes
            JsonNode scopes = root.get("inventoryScopes");
            if (scopes != null) {
                for (JsonNode sc : scopes) {
                    UUID scopeId = UUID.fromString(sc.get("scopeId").asText());
                    UUID locId = UUID.fromString(sc.get("locationId").asText());
                    String status = sc.get("status").asText();
                    Instant startAt = Instant.parse(sc.get("startedAt").asText());
                    JsonNode clNode = sc.get("closedAt");
                    Instant closedAt = (clNode != null && !clNode.isNull()) ? Instant.parse(clNode.asText()) : null;

                    jdbcTemplate.update(
                        "INSERT INTO runtime.inventory_scope (dataset_epoch, scope_id, location_id, status, started_at, closed_at) " +
                        "VALUES (?, ?, ?, ?, ?, ?)",
                        epoch, scopeId, locId, status, Timestamp.from(startAt), closedAt != null ? Timestamp.from(closedAt) : null
                    );
                }
            }

            // 10. Observations
            JsonNode observations = root.get("observations");
            if (observations != null) {
                for (JsonNode obs : observations) {
                    UUID obsId = UUID.fromString(obs.get("observationId").asText());
                    UUID copyId = UUID.fromString(obs.get("copyId").asText());
                    UUID locId = UUID.fromString(obs.get("locationId").asText());
                    UUID scopeId = UUID.fromString(obs.get("scopeId").asText());
                    Instant obsAt = Instant.parse(obs.get("observedAt").asText());
                    String quality = obs.get("quality").asText();

                    jdbcTemplate.update(
                        "INSERT INTO runtime.observation (dataset_epoch, observation_id, copy_id, location_id, scope_id, observed_at, quality) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)",
                        epoch, obsId, copyId, locId, scopeId, Timestamp.from(obsAt), quality
                    );
                }
            }

            // 11. Source Availability
            JsonNode avail = root.get("sourceAvailability");
            if (avail != null) {
                for (JsonNode av : avail) {
                    UUID copyId = UUID.fromString(av.get("copyId").asText());
                    String src = av.get("source").asText();
                    String status = av.get("status").asText();
                    JsonNode cf = av.get("completeFrom");
                    JsonNode ct = av.get("completeTo");
                    Instant compFrom = (cf != null && !cf.isNull()) ? Instant.parse(cf.asText()) : null;
                    Instant compTo = (ct != null && !ct.isNull()) ? Instant.parse(ct.asText()) : null;

                    jdbcTemplate.update(
                        "INSERT INTO runtime.source_availability (dataset_epoch, copy_id, source, status, complete_from, complete_to) " +
                        "VALUES (?, ?, ?, ?, ?, ?)",
                        epoch, copyId, src, status, compFrom != null ? Timestamp.from(compFrom) : null, compTo != null ? Timestamp.from(compTo) : null
                    );
                }
            }

            // 12. Operator Notes
            JsonNode notes = root.get("operatorNotes");
            if (notes != null) {
                for (JsonNode n : notes) {
                    UUID noteId = UUID.fromString(n.get("noteId").asText());
                    UUID copyId = UUID.fromString(n.get("copyId").asText());
                    String text = n.get("text").asText();
                    Instant recAt = Instant.parse(n.get("recordedAt").asText());

                    jdbcTemplate.update(
                        "INSERT INTO runtime.operator_note (dataset_epoch, note_id, copy_id, text, recorded_at) VALUES (?, ?, ?, ?, ?)",
                        epoch, noteId, copyId, text, Timestamp.from(recAt)
                    );
                }
            }

            // 13. Source Cases & Symptoms & Gap Cases
            JsonNode cases = root.get("cases");
            if (cases != null) {
                for (JsonNode sc : cases) {
                    UUID caseId = UUID.fromString(sc.get("caseId").asText());
                    UUID copyId = UUID.fromString(sc.get("copyId").asText());
                    Instant invRef = Instant.parse(sc.get("inventoryReferenceTime").asText());

                    jdbcTemplate.update(
                        "INSERT INTO runtime.source_case (dataset_epoch, case_id, copy_id, inventory_reference_time, initial_status) " +
                        "VALUES (?, ?, ?, ?, 'A_ANALYSER')",
                        epoch, caseId, copyId, Timestamp.from(invRef)
                    );

                    jdbcTemplate.update(
                        "INSERT INTO runtime.gap_case (dataset_epoch, case_id, status) VALUES (?, ?, 'A_ANALYSER')",
                        epoch, caseId
                    );

                    JsonNode symptoms = sc.get("symptoms");
                    if (symptoms != null) {
                        int pos = 0;
                        for (JsonNode sym : symptoms) {
                            UUID symId = UUID.fromString(sym.get("symptomId").asText());
                            String kind = sym.get("kind").asText();
                            JsonNode expVal = sym.get("expectedValue");
                            JsonNode obsVal = sym.get("observedValue");

                            jdbcTemplate.update(
                                "INSERT INTO runtime.gap_symptom (dataset_epoch, symptom_id, case_id, position, kind, expected_value, observed_value) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?)",
                                epoch, symId, caseId, pos++, kind,
                                expVal != null && !expVal.isNull() ? expVal.asText() : null,
                                obsVal != null && !obsVal.isNull() ? obsVal.asText() : null
                            );
                        }
                    }
                }
            }

            // 14. Bootstrap rule R001
            Map<String, Object> r1Meta = new HashMap<>();
            r1Meta.put("schemaVersion", "1.0.0");
            r1Meta.put("name", "Localisation différente constatée");
            r1Meta.put("origin", "BOOTSTRAP");
            r1Meta.put("dmnPath", "rules/R001-v1.dmn");
            r1Meta.put("namespace", "urn:library-stock-gap:rules:R001:1");
            r1Meta.put("modelName", "R001-v1");
            r1Meta.put("decisionName", "locationDifference");
            r1Meta.put("causeCode", "LOCATION_DIFFERENCE");
            r1Meta.put("coveredSymptomKinds", List.of("LOCATION_MISMATCH"));
            r1Meta.put("recommendations", List.of("Vérifier le rangement et l'affectation de l'exemplaire."));
            r1Meta.put("limitations", List.of("N'identifie pas l'auteur ni le mécanisme causal du déplacement."));
            String r1MetaJson = objectMapper.writeValueAsString(r1Meta);

            jdbcTemplate.update(
                "INSERT INTO runtime.rule (dataset_epoch, rule_id, created_at) VALUES (?, 'R001', ?)",
                epoch, Timestamp.from(now)
            );

            jdbcTemplate.update(
                "INSERT INTO runtime.rule_version (dataset_epoch, rule_id, rule_version, author_kind, dmn_xml, artifact_sha256, decision_id, decision_row_count, fact_schema_version, engine_version, metadata, created_at) " +
                "VALUES (?, 'R001', 1, 'BOOTSTRAP', ?, ?, 'locationDifference', 1, '1.0.0', 'KIE 10.2.0', ?::jsonb, ?)",
                epoch, dmnXml, dmnSha, r1MetaJson, Timestamp.from(now)
            );

            // 15. Publication v1
            UUID pubCommandId = UUID.randomUUID();
            String manifestSha = AnalysisRepository.sha256Hex("bootstrap-publication-v1-R001-1");

            jdbcTemplate.update(
                "INSERT INTO runtime.rule_set_publication (dataset_epoch, rule_set_version, kind, manifest_sha256, approved_by, command_id, created_at) " +
                "VALUES (?, 1, 'BOOTSTRAP', ?, 'Admin', ?, ?)",
                epoch, manifestSha, pubCommandId, Timestamp.from(now)
            );

            jdbcTemplate.update(
                "INSERT INTO runtime.rule_set_member (dataset_epoch, rule_set_version, rule_id, rule_version) VALUES (?, 1, 'R001', 1)",
                epoch
            );

            jdbcTemplate.update(
                "UPDATE runtime.rule_set_publication SET sealed_at = ? WHERE dataset_epoch = ? AND rule_set_version = 1",
                Timestamp.from(now), epoch
            );

            jdbcTemplate.update(
                "INSERT INTO runtime.active_rule_set (dataset_epoch, rule_set_version) VALUES (?, 1)",
                epoch
            );

            // 16. Execution slots & SSE Stream
            jdbcTemplate.update(
                "INSERT INTO runtime.execution_slot (dataset_epoch, slot_no, mission_id, attempt) VALUES (?, 1, NULL, NULL), (?, 2, NULL, NULL)",
                epoch, epoch
            );

            jdbcTemplate.update(
                "INSERT INTO runtime.sse_stream (dataset_epoch, last_cursor, retained_from) VALUES (?, 0, 1)",
                epoch
            );

            // 17. Seal dataset_epoch
            jdbcTemplate.update(
                "UPDATE runtime.dataset_epoch SET sealed_at = ? WHERE dataset_epoch = ?",
                Timestamp.from(now), epoch
            );

            // 18. Set active_dataset singleton
            jdbcTemplate.update(
                "INSERT INTO runtime.active_dataset (singleton, dataset_epoch, lock_version) VALUES (true, ?, 0) " +
                "ON CONFLICT (singleton) DO UPDATE SET dataset_epoch = EXCLUDED.dataset_epoch, lock_version = runtime.active_dataset.lock_version + 1",
                epoch
            );

            log.info("Époque active initialisée: {}", epoch);
            return epoch;

        } catch (Exception e) {
            log.error("Échec du seed: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de l'initialisation du jeu de données", e);
        }
    }
}
