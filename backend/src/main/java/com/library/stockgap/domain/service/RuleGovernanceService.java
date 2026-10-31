package com.library.stockgap.domain.service;

import com.library.stockgap.domain.model.ValidationReport;
import com.library.stockgap.infrastructure.persistence.RuleRepository;
import com.library.stockgap.infrastructure.persistence.AnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
public class RuleGovernanceService {
    private final RuleRepository ruleRepository;
    private final DmnEngineService dmnEngineService;
    private final SseHubService sseHubService;
    private final RuleReplayService replayService;
    private final AnalysisRepository analysisRepository;

    @Autowired
    public RuleGovernanceService(RuleRepository ruleRepository,
                                 DmnEngineService dmnEngineService,
                                 SseHubService sseHubService,
                                 RuleReplayService replayService,
                                 AnalysisRepository analysisRepository) {
        this.ruleRepository = ruleRepository;
        this.dmnEngineService = dmnEngineService;
        this.sseHubService = sseHubService;
        this.replayService = replayService;
        this.analysisRepository = analysisRepository;
    }

    public RuleGovernanceService(RuleRepository rules, DmnEngineService engine, SseHubService notifications) {
        this(rules, engine, notifications, null, null);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ValidationReport validateProposal(UUID epoch, UUID proposalId, int revision, String dmnXml) {
        String persistedXml = ruleRepository.requireProposalDmnXml(epoch, proposalId, revision);
        if (dmnXml != null && !persistedXml.equals(dmnXml)) {
            throw new IllegalArgumentException("Le XML reçu ne correspond pas à la révision persistée ; rechargez la proposition");
        }
        dmnXml = persistedXml;
        if (replayService != null) return validatePersistedCandidate(epoch, proposalId, revision);
        boolean validXml = dmnEngineService.validateDmnXml(dmnXml);
        int baseVer = ruleRepository.getActiveRuleSetVersion(epoch);
        UUID reportId = UUID.randomUUID();
        String sha = com.library.stockgap.infrastructure.persistence.AnalysisRepository.sha256Hex(dmnXml != null ? dmnXml : "empty");

        int posTested = 0;
        int posPassed = 0;
        int negTested = 0;
        int negPassed = 0;
        int replayCount = 0;
        int regressions = 0;
        boolean passed = false;

        ValidationReport report = new ValidationReport(
            reportId, proposalId, revision, baseVer,
            sha,
            passed, passed, validXml ? "REPLAY_REQUIRED" : "FAILED",
            posTested, posPassed, negTested, negPassed,
            replayCount, regressions, Collections.emptyList(), Collections.emptyList(),
            Instant.now()
        );

        ruleRepository.insertValidationReport(epoch, report);
        sseHubService.publishEvent(epoch, "ValidationCompleted", "ValidationReport", reportId.toString(),
            Map.of("reportId", reportId, "proposalId", proposalId, "passed", passed));

        return report;
    }

    private ValidationReport validatePersistedCandidate(UUID epoch, UUID proposalId, int revision) {
        var candidate = ruleRepository.loadProposalCandidate(epoch, proposalId, revision);
        int activeVersion = ruleRepository.getActiveRuleSetVersion(epoch);
        ruleRepository.setProposalStatus(epoch, proposalId, revision, "VALIDATING");
        var before = ruleRepository.getActiveRules(epoch);
        List<com.library.stockgap.domain.model.RuleMetadata> after;
        RuleReplayService.Replay replay;
        String manifest;
        try {
            if (candidate.baseRuleSetVersion() != activeVersion) throw new IllegalArgumentException("Référentiel de base périmé : nouvelle révision requise");
            after = ruleRepository.candidateRules(before, candidate);
            manifest = ruleRepository.manifestFingerprint(after);
            var origin = analysisRepository.findSnapshot(epoch, candidate.snapshotId()).orElseThrow(() -> new IllegalArgumentException("Preuves d'origine absentes"));
            replay = replayService.verify(candidate.rule(), before, after, origin);
        } catch (Exception failure) {
            manifest = ruleRepository.manifestFingerprint(List.of(candidate.rule()));
            replay = new RuleReplayService.Replay(false, 0, 0, 0, 0, 0, 0, replayService.corpusFingerprint(),
                List.of(String.valueOf(failure.getMessage())), List.of(), List.of());
        }
        boolean stale = candidate.baseRuleSetVersion() != activeVersion;
        String status = replay.passed() ? "READY_FOR_APPROVAL" : stale ? "STALE" : "CHANGES_REQUIRED";
        ValidationReport report = new ValidationReport(UUID.randomUUID(), proposalId, revision, candidate.baseRuleSetVersion(),
            candidate.rule().artifactSha256(), replay.passed(), replay.passed(), replay.passed() ? "PASSED" : stale ? "STALE" : "FAILED",
            replay.positiveTested(), replay.positivePassed(), replay.negativeTested(), replay.negativePassed(), replay.replayed(), replay.regressions(),
            replay.conflicts(), replay.coverageLosses(), Instant.now(), candidate.fingerprint(), manifest, replay.corpusSha256(), replay.results());
        ruleRepository.insertValidationReport(epoch, report);
        ruleRepository.setProposalStatus(epoch, proposalId, revision, status);
        sseHubService.publishEvent(epoch, "ValidationCompleted", "ValidationReport", report.reportId().toString(),
            Map.of("reportId", report.reportId(), "proposalId", proposalId, "passed", report.passed(), "status", status));
        return report;
    }

    @Transactional
    public int approveAndPublish(UUID epoch, UUID proposalId, int revision, UUID reportId) {
        if (replayService == null) throw new IllegalStateException("Publication interdite sans validateur de replay");
        var candidate = ruleRepository.loadProposalCandidate(epoch, proposalId, revision);
        if (candidate.baseRuleSetVersion() != ruleRepository.getActiveRuleSetVersion(epoch)) {
            throw new IllegalStateException("Le référentiel a changé depuis les tests : nouvelle révision requise");
        }
        replayService.prepare(ruleRepository.candidateRules(ruleRepository.getActiveRules(epoch), candidate));
        int newVersion = ruleRepository.publishProposal(epoch, proposalId, revision, reportId);

        sseHubService.publishEvent(epoch, "PublicationActivated", "Publication", String.valueOf(newVersion),
            Map.of("ruleSetVersion", newVersion, "proposalId", proposalId));
        sseHubService.publishEvent(epoch, "ProposalChanged", "Proposal", proposalId.toString(),
            Map.of("proposalId", proposalId, "status", "PUBLISHED", "activeRuleSetVersion", newVersion));

        return newVersion;
    }

    @Transactional
    public int retireRule(UUID epoch, String ruleId) {
        int newVersion = ruleRepository.retireRule(epoch, ruleId);

        sseHubService.publishEvent(epoch, "PublicationActivated", "Publication", String.valueOf(newVersion),
            Map.of("ruleSetVersion", newVersion, "retiredRuleId", ruleId));

        return newVersion;
    }
}
