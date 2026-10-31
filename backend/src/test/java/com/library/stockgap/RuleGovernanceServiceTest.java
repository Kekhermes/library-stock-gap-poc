package com.library.stockgap;

import com.library.stockgap.domain.service.DmnEngineService;
import com.library.stockgap.domain.service.RuleGovernanceService;
import com.library.stockgap.domain.service.RuleValidationPolicy;
import com.library.stockgap.domain.service.SseHubService;
import com.library.stockgap.infrastructure.persistence.RuleRepository;
import com.library.stockgap.infrastructure.persistence.AnalysisRepository;
import com.library.stockgap.domain.service.RuleReplayService;
import com.library.stockgap.domain.service.DeterministicAnalysisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import com.library.stockgap.infrastructure.persistence.ActiveDatasetRepository;
import com.library.stockgap.infrastructure.persistence.CaseRepository;
import com.library.stockgap.infrastructure.persistence.MissionRepository;
import com.library.stockgap.domain.model.EvidenceSnapshot;
import com.library.stockgap.domain.model.DiagnosticRecord;
import com.library.stockgap.domain.model.DiagnosticConclusion;
import com.library.stockgap.domain.model.Finding;
import com.library.stockgap.domain.model.EpistemicStatus;
import java.time.Instant;
import org.springframework.core.io.DefaultResourceLoader;
import java.util.Map;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

public class RuleGovernanceServiceTest {

    private DmnEngineService dmnEngineService;

    @BeforeEach
    void setUp() {
        dmnEngineService = new DmnEngineService();
    }

    @Test
    void testValidDmnSyntaxPassesPrevalidation() {
        String validDmn = """
            <?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20230324/MODEL/" id="d_test" name="TestModel" namespace="urn:test:1">
              <decision id="dec_1" name="decision1">
                <variable id="v_1" name="decision1" typeRef="string"/>
                <literalExpression><text>"VALID"</text></literalExpression>
              </decision>
            </definitions>
            """;

        boolean valid = dmnEngineService.validateDmnXml(validDmn);
        assertTrue(valid, "Le DMN syntaxiquement valide doit passer la prévalidation");
    }

    @Test
    void testInvalidDmnSyntaxIsRejected() {
        String invalidDmn = "<definitions>broken XML without model";

        boolean valid = dmnEngineService.validateDmnXml(invalidDmn);
        assertFalse(valid, "Le DMN corrompu doit être rejeté");
    }

    @Test
    void aDifferentClientXmlCannotReplaceThePersistedCandidate() {
        UUID epoch = UUID.randomUUID(), proposal = UUID.randomUUID();
        RuleRepository repository = mock(RuleRepository.class);
        DmnEngineService engine = mock(DmnEngineService.class);
        SseHubService notifications = mock(SseHubService.class);
        when(repository.requireProposalDmnXml(epoch, proposal, 1)).thenReturn("persisted XML");
        var governance = new RuleGovernanceService(repository, engine, notifications);
        assertThrows(IllegalArgumentException.class, () -> governance.validateProposal(epoch, proposal, 1, "different XML"));
        verifyNoInteractions(engine, notifications);
    }

    @Test
    void compilationAloneCannotProduceInventedSuccessfulTests() {
        UUID epoch = UUID.randomUUID(), proposal = UUID.randomUUID();
        RuleRepository repository = mock(RuleRepository.class);
        DmnEngineService engine = mock(DmnEngineService.class);
        when(repository.requireProposalDmnXml(epoch, proposal, 1)).thenReturn("persisted XML");
        when(engine.validateDmnXml("persisted XML")).thenReturn(true);
        when(repository.getActiveRuleSetVersion(epoch)).thenReturn(1);
        var governance = new RuleGovernanceService(repository, engine, mock(SseHubService.class));
        var report = governance.validateProposal(epoch, proposal, 1, null);
        assertFalse(report.passed());
        assertFalse(report.publishable());
        assertEquals(0, report.positiveCasesTested());
        assertEquals(0, report.negativeCasesTested());
        assertEquals(0, report.totalReplayEvaluated());
    }

    @Test
    void simpleBootstrapPassesTheNativeFeelPolicy() throws Exception {
        String xml;
        try (var input = getClass().getResourceAsStream("/rules/R001-v1.dmn")) {
            xml = new String(input.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
        }
        assertDoesNotThrow(() -> new RuleValidationPolicy().validate(xml));
        String external = xml.replace("observationReliable = true", "now() = now()");
        assertThrows(IllegalArgumentException.class, () -> new RuleValidationPolicy().validate(external));
        String multipleRows = xml.replace("</decisionTable>", "<rule><inputEntry><text>true</text></inputEntry></rule></decisionTable>");
        assertThrows(IllegalArgumentException.class, () -> new RuleValidationPolicy().validate(multipleRows));
        String undeclared = xml.replace("observationReliable = true", "systemStatus = \"ON_LOAN\"");
        assertThrows(IllegalArgumentException.class, () -> new RuleValidationPolicy().validate(undeclared));
    }

    @Test
    void productionValidationRunsTheCorpusAndBindsTheRevision() throws Exception {
        UUID epoch = UUID.randomUUID(), proposal = UUID.randomUUID();
        var origin = RuleReplayServiceTest.origin();
        var rule = RuleReplayServiceTest.returnRule(false, false);
        var before = List.of(RuleReplayServiceTest.bootstrap());
        var after = List.of(before.getFirst(), rule);
        String fingerprint = AnalysisRepository.sha256Hex("fixture-proposal");
        var candidate = new RuleRepository.ProposalCandidate(proposal, 1, 1, origin.snapshotId(), UUID.randomUUID(), "CREATE", null, rule, fingerprint);
        RuleRepository repository = mock(RuleRepository.class);
        AnalysisRepository analyses = mock(AnalysisRepository.class);
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        RuleRepository hashing = new RuleRepository(mock(JdbcTemplate.class), mapper);
        when(repository.requireProposalDmnXml(epoch, proposal, 1)).thenReturn(rule.dmnXml());
        when(repository.loadProposalCandidate(epoch, proposal, 1)).thenReturn(candidate);
        when(repository.getActiveRuleSetVersion(epoch)).thenReturn(1);
        when(repository.getActiveRules(epoch)).thenReturn(before);
        when(repository.candidateRules(before, candidate)).thenReturn(after);
        when(repository.manifestFingerprint(after)).thenReturn(hashing.manifestFingerprint(after));
        when(analyses.findSnapshot(epoch, origin.snapshotId())).thenReturn(java.util.Optional.of(origin));
        DmnEngineService engine = new DmnEngineService();
        var replay = new RuleReplayService(mapper, new DefaultResourceLoader(), new DeterministicAnalysisService(engine), engine, new RuleValidationPolicy());
        var governance = new RuleGovernanceService(repository, engine, mock(SseHubService.class), replay, analyses);
        var report = governance.validateProposal(epoch, proposal, 1, null);
        assertTrue(report.passed(), report.conflicts().toString());
        assertEquals(4, report.positiveCasesTested());
        assertEquals(30, report.negativeCasesTested());
        assertEquals(33, report.totalReplayEvaluated());
        assertEquals(fingerprint, report.proposalSha256());
        assertEquals(replay.corpusFingerprint(), report.corpusSha256());
        assertEquals(hashing.manifestFingerprint(after), report.candidateManifestSha256());
        verify(repository).insertValidationReport(epoch, report);
        verify(repository).setProposalStatus(epoch, proposal, 1, "READY_FOR_APPROVAL");
    }

    @Test
    void publicationRejectsOldValidatorReportsBeforeAnyWrite() throws Exception {
        assertPublicationRefused("OLD_VALIDATOR");
    }

    @Test
    void publicationRejectsAChangedBaseBeforeAnyWrite() throws Exception {
        assertPublicationRefused("STALE_BASE");
    }

    @Test
    void publicationRejectsAChangedProposalBeforeAnyWrite() throws Exception {
        assertPublicationRefused("CHANGED_CONTENT");
    }

    @Test
    void publicationRejectsAChangedCorpusBeforeAnyWrite() throws Exception {
        assertPublicationRefused("CHANGED_CORPUS");
    }

    private void assertPublicationRefused(String scenario) throws Exception {
        UUID epoch = UUID.randomUUID(), proposal = UUID.randomUUID(), reportId = UUID.randomUUID();
        int activeVersion = scenario.equals("STALE_BASE") ? 2 : 1;
        var rule = RuleReplayServiceTest.returnRule(false, false);
        var before = List.of(RuleReplayServiceTest.bootstrap());
        String fingerprint = AnalysisRepository.sha256Hex("proposal");
        var candidate = new RuleRepository.ProposalCandidate(proposal, 1, 1, UUID.randomUUID(), UUID.randomUUID(), "CREATE", null, rule, fingerprint);
        JdbcTemplate jdbc = mock(JdbcTemplate.class);
        RuleRepository repository = spy(new RuleRepository(jdbc, new ObjectMapper().findAndRegisterModules()));
        doReturn(candidate).when(repository).loadProposalCandidate(epoch, proposal, 1);
        doReturn(before).when(repository).getRulesForVersion(epoch, activeVersion);
        when(jdbc.queryForMap(contains("active_dataset"))).thenReturn(Map.of("dataset_epoch", epoch, "rule_set_version", activeVersion));
        when(jdbc.queryForMap(contains("rule_change_proposal"), eq(epoch), eq(proposal)))
            .thenReturn(Map.of("current_revision", 1, "status", "READY_FOR_APPROVAL"));
        String corpusHash;
        try (var input = getClass().getResourceAsStream("/rules/validation-corpus.json")) {
            corpusHash = AnalysisRepository.sha256Hex(new String(input.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8));
        }
        Map<String, Object> validation = new java.util.LinkedHashMap<>();
        validation.put("outcome", "PASS");
        validation.put("blocking_count", 0);
        validation.put("validator_version", scenario.equals("OLD_VALIDATOR") ? "1.0.0" : RuleValidationPolicy.VERSION);
        validation.put("engine_version", "KIE 10.2.0");
        validation.put("fact_schema_version", "1.0.0");
        validation.put("base_rule_set_version", 1);
        validation.put("proposal_sha256", scenario.equals("CHANGED_CONTENT") ? "different" : fingerprint);
        validation.put("candidate_manifest_sha256", repository.manifestFingerprint(repository.candidateRules(before, candidate)));
        validation.put("corpus_sha256", scenario.equals("CHANGED_CORPUS") ? "different" : corpusHash);
        when(jdbc.queryForList(contains("validation_report"), eq(epoch), eq(reportId), eq(proposal), eq(1))).thenReturn(List.of(validation));
        assertThrows(IllegalStateException.class, () -> repository.publishProposal(epoch, proposal, 1, reportId));
        assertTrue(mockingDetails(jdbc).getInvocations().stream().allMatch(invocation -> invocation.getMethod().getName().startsWith("query")));
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "STOCKGAP_DB_TESTS", matches = "true")
    void postgresValidationPublicationAndPinnedVersionAreRealAndRolledBack() throws Exception {
        var datasource = new DriverManagerDataSource(System.getenv().getOrDefault("STOCKGAP_DB_URL", "jdbc:postgresql://127.0.0.1:5433/stockgap"),
            System.getenv().getOrDefault("STOCKGAP_DB_USER", "postgres"), System.getenv().getOrDefault("STOCKGAP_DB_PASSWORD", "postgres"));
        var transactions = new TransactionTemplate(new DataSourceTransactionManager(datasource));
        JdbcTemplate jdbc = new JdbcTemplate(datasource);
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        RuleRepository rules = new RuleRepository(jdbc, mapper);
        AnalysisRepository analyses = new AnalysisRepository(jdbc, mapper);
        CaseRepository cases = new CaseRepository(jdbc, mapper);
        MissionRepository missions = new MissionRepository(jdbc, mapper);
        UUID epoch = new ActiveDatasetRepository(jdbc).requireActiveEpoch();
        int originalVersion = rules.getActiveRuleSetVersion(epoch);
        int originalAnalyses = jdbc.queryForObject("SELECT count(*) FROM runtime.analysis_run WHERE dataset_epoch = ?", Integer.class, epoch);
        int originalProposals = jdbc.queryForObject("SELECT count(*) FROM runtime.rule_change_proposal WHERE dataset_epoch = ?", Integer.class, epoch);
        DmnEngineService engine = new DmnEngineService();
        DeterministicAnalysisService deterministic = new DeterministicAnalysisService(engine);
        RuleReplayService replay = new RuleReplayService(mapper, new DefaultResourceLoader(), deterministic, engine, new RuleValidationPolicy());
        RuleGovernanceService governance = new RuleGovernanceService(rules, engine, mock(SseHubService.class), replay, analyses);
        var valid = RuleReplayServiceTest.returnRule(false, false);
        var broad = RuleReplayServiceTest.returnRule(true, false);
        transactions.executeWithoutResult(transaction -> {
            try {
                assertEquals(1, originalVersion, "La recette attend la préparation initiale de la démo");
                var fixture = sqlFixture(epoch, cases, analyses, missions, deterministic, "ReturnReceived", "RETURN_NOT_PROJECTED", originalVersion);
                UUID rejected = UUID.randomUUID();
                rules.insertProposal(epoch, rejected, fixture.diagnosticId(), fixture.evidence().snapshotId(), fixture.missionId(), "CREATE",
                    UUID.randomUUID().toString(), "Fixture M3 trop large", "RETURN_NOT_PROJECTED", List.of("STATUS_MISMATCH"),
                    "Fixture de test SQL, aucune génération LLM", broad.dmnXml(), originalVersion);
                var failed = governance.validateProposal(epoch, rejected, 1, null);
                assertFalse(failed.passed());
                assertEquals("CHANGES_REQUIRED", jdbc.queryForObject("SELECT status FROM runtime.rule_change_proposal WHERE dataset_epoch = ? AND proposal_id = ?",
                    String.class, epoch, rejected));
                UUID proposal = UUID.randomUUID(), stale = UUID.randomUUID();
                String ruleId = UUID.randomUUID().toString();
                rules.insertProposal(epoch, proposal, fixture.diagnosticId(), fixture.evidence().snapshotId(), fixture.missionId(), "CREATE", ruleId,
                    "Fixture M3 retour", "RETURN_NOT_PROJECTED", List.of("STATUS_MISMATCH"), "Fixture SQL, aucune génération LLM", valid.dmnXml(), originalVersion);
                rules.insertProposal(epoch, stale, fixture.diagnosticId(), fixture.evidence().snapshotId(), fixture.missionId(), "CREATE", UUID.randomUUID().toString(),
                    "Fixture M3 concurrence", "RETURN_NOT_PROJECTED", List.of("STATUS_MISMATCH"), "Fixture SQL concurrente", valid.dmnXml(), originalVersion);
                var report = governance.validateProposal(epoch, proposal, 1, null);
                var staleReport = governance.validateProposal(epoch, stale, 1, null);
                assertTrue(report.passed(), report.conflicts().toString());
                assertTrue(staleReport.passed());
                assertEquals(33, report.totalReplayEvaluated());
                assertEquals(34, ((List<?>) rules.latestValidationReport(epoch, proposal, 1).orElseThrow().get("caseResults")).size());
                assertThrows(IllegalStateException.class, () -> rules.publishProposal(epoch, proposal, 1, staleReport.reportId()));
                int published = governance.approveAndPublish(epoch, proposal, 1, report.reportId());
                assertEquals(2, published);
                assertEquals(2, rules.getActiveRuleSetVersion(epoch));
                assertFalse(deterministic.evaluate(fixture.evidence(), rules.getRulesForVersion(epoch, originalVersion)).fullyCovered());
                int recognized = 0;
                for (var item : cases.findCases(epoch, null, null, "STATUS_MISMATCH", 0, 10)) {
                    var dossier = cases.findCaseDossier(epoch, UUID.fromString(item.get("caseId").toString())).orElseThrow();
                    if (dossier.caseId().equals(fixture.evidence().caseId())) continue;
                    var evidence = sqlEvidence(dossier);
                    if (!"ReturnReceived".equals(deterministic.facts(evidence).get("latestBusinessEventType"))) continue;
                    var result = deterministic.evaluate(evidence, rules.getActiveRules(epoch));
                    assertTrue(result.fullyCovered());
                    assertEquals(ruleId, result.findings().getFirst().ruleId());
                    recognized++;
                }
                assertEquals(3, recognized);
                assertEquals("STALE", jdbc.queryForObject("SELECT status FROM runtime.rule_change_proposal WHERE dataset_epoch = ? AND proposal_id = ?",
                    String.class, epoch, stale));
                assertThrows(IllegalStateException.class, () -> governance.approveAndPublish(epoch, stale, 1, staleReport.reportId()));
                assertThrows(IllegalStateException.class, () -> governance.approveAndPublish(epoch, proposal, 1, report.reportId()));
                assertEquals(2, rules.getActiveRuleSetVersion(epoch));
                UUID revisionProposal = UUID.randomUUID();
                rules.insertProposal(epoch, revisionProposal, fixture.diagnosticId(), fixture.evidence().snapshotId(), fixture.missionId(), "REVISE", ruleId,
                    "Fixture M3 retour révisé", "RETURN_NOT_PROJECTED", List.of("STATUS_MISMATCH"), "Révision de fixture SQL", valid.dmnXml(), 2);
                var revisedReport = governance.validateProposal(epoch, revisionProposal, 1, null);
                assertTrue(revisedReport.passed(), revisedReport.conflicts().toString());
                assertEquals(3, governance.approveAndPublish(epoch, revisionProposal, 1, revisedReport.reportId()));
                assertEquals(2, rules.latestRuleVersion(epoch, ruleId).orElseThrow().ruleVersion());
                assertEquals(1, rules.getRuleVersion(epoch, ruleId, 1).orElseThrow().ruleVersion());
                UUID nextRevision = UUID.randomUUID();
                rules.insertProposal(epoch, nextRevision, fixture.diagnosticId(), fixture.evidence().snapshotId(), fixture.missionId(), "REVISE", ruleId,
                    "Fixture M3 troisième version", "RETURN_NOT_PROJECTED", List.of("STATUS_MISMATCH"), "Deuxième révision SQL", valid.dmnXml(), 3);
                var nextReport = governance.validateProposal(epoch, nextRevision, 1, null);
                assertTrue(nextReport.passed(), nextReport.conflicts().toString());
                assertEquals(4, governance.approveAndPublish(epoch, nextRevision, 1, nextReport.reportId()));
                assertEquals(3, rules.latestRuleVersion(epoch, ruleId).orElseThrow().ruleVersion());
                rules.retireRule(epoch, ruleId);
                assertEquals(5, rules.getActiveRuleSetVersion(epoch));
                assertEquals(1, rules.getActiveRules(epoch).size());
                var repair = sqlFixture(epoch, cases, analyses, missions, deterministic, "RepairStarted", "REPAIR_NOT_PROJECTED", 5);
                String repairXml = valid.dmnXml().replace("ReturnRule", "RepairRule").replace("urn:test:return", "urn:test:repair")
                    .replace("ReturnReceived", "RepairStarted").replace("ON_LOAN", "ON_SHELF").replace("RETURN_NOT_PROJECTED", "REPAIR_NOT_PROJECTED");
                UUID repairProposal = UUID.randomUUID();
                String repairRuleId = UUID.randomUUID().toString();
                rules.insertProposal(epoch, repairProposal, repair.diagnosticId(), repair.evidence().snapshotId(), repair.missionId(), "CREATE", repairRuleId,
                    "Fixture M3 réparation", "REPAIR_NOT_PROJECTED", List.of("STATUS_MISMATCH"), "Fixture SQL réparation", repairXml, 5);
                var repairReport = governance.validateProposal(epoch, repairProposal, 1, null);
                assertTrue(repairReport.passed(), repairReport.conflicts().toString());
                assertEquals(6, governance.approveAndPublish(epoch, repairProposal, 1, repairReport.reportId()));
                UUID secondReturn = UUID.randomUUID();
                rules.insertProposal(epoch, secondReturn, fixture.diagnosticId(), fixture.evidence().snapshotId(), fixture.missionId(), "CREATE", UUID.randomUUID().toString(),
                    "Fixture M3 retour après réparation", "RETURN_NOT_PROJECTED", List.of("STATUS_MISMATCH"), "Fixture SQL ordre inverse", valid.dmnXml(), 6);
                var secondReturnReport = governance.validateProposal(epoch, secondReturn, 1, null);
                assertTrue(secondReturnReport.passed(), secondReturnReport.conflicts().toString());
                assertEquals(7, governance.approveAndPublish(epoch, secondReturn, 1, secondReturnReport.reportId()));
                assertEquals(3, rules.getActiveRules(epoch).size());
            } catch (Exception failure) {
                throw new IllegalStateException("Recette SQL M3 interrompue", failure);
            } finally {
                transaction.setRollbackOnly();
            }
        });
        assertEquals(originalVersion, rules.getActiveRuleSetVersion(epoch));
        assertEquals(originalAnalyses, jdbc.queryForObject("SELECT count(*) FROM runtime.analysis_run WHERE dataset_epoch = ?", Integer.class, epoch));
        assertEquals(originalProposals, jdbc.queryForObject("SELECT count(*) FROM runtime.rule_change_proposal WHERE dataset_epoch = ?", Integer.class, epoch));
    }

    private record SqlFixture(EvidenceSnapshot evidence, UUID diagnosticId, UUID missionId) {}

    private SqlFixture sqlFixture(UUID epoch, CaseRepository cases, AnalysisRepository analyses, MissionRepository missions,
                                  DeterministicAnalysisService deterministic, String operation, String cause, int version) {
        for (var item : cases.findCases(epoch, null, null, "STATUS_MISMATCH", 0, 10)) {
            var dossier = cases.findCaseDossier(epoch, UUID.fromString(item.get("caseId").toString())).orElseThrow();
            var evidence = sqlEvidence(dossier);
            if (!operation.equals(deterministic.facts(evidence).get("latestBusinessEventType"))) continue;
            UUID analysisId = UUID.randomUUID(), diagnosticId = UUID.randomUUID();
            analyses.insertSnapshot(epoch, evidence);
            analyses.insertAnalysisRun(epoch, analysisId, dossier.caseId(), evidence.snapshotId(), version);
            var finding = new Finding(dossier.copyId(), List.of(UUID.fromString(dossier.symptoms().getFirst().get("symptomId").toString())),
                cause, EpistemicStatus.SUPPORTED, List.of("Fixture SQL M3"), List.of(), List.of(), List.of(), "Fixture SQL M3", null, null);
            var diagnostic = new DiagnosticRecord(diagnosticId, analysisId, dossier.caseId(), DiagnosticConclusion.SUPPORTED,
                List.of(finding), List.of(), "Fixture de test M3 : aucun appel LLM", false, "INVESTIGATOR");
            analyses.insertDiagnosticRevision(epoch, diagnostic, "INVESTIGATOR", 1);
            analyses.updateAnalysisStatus(epoch, analysisId, "COMPLETED");
            UUID acceptance = analyses.acceptDiagnostic(epoch, diagnosticId, dossier.caseId());
            UUID mission = UUID.randomUUID();
            missions.createMission(epoch, mission, "RULE_SYNTHESIS", null, acceptance, evidence.snapshotId(), version, null);
            return new SqlFixture(evidence, diagnosticId, mission);
        }
        throw new IllegalStateException("Dossier de test absent : " + operation);
    }

    private EvidenceSnapshot sqlEvidence(com.library.stockgap.domain.model.CaseDossier dossier) {
        return new EvidenceSnapshot(UUID.randomUUID(), dossier.caseId(), dossier.copyId(), "1.0.0", dossier.inventoryReferenceTime(), Instant.now(),
            Map.of("caseId", dossier.caseId().toString(), "copyId", dossier.copyId().toString()), dossier.timeline(), dossier.operatorNotes(),
            dossier.systemState(), dossier.observations(), dossier.sourceAvailability(), dossier.symptoms());
    }
}
