package com.library.stockgap.domain.model;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record ValidationReport(
    UUID reportId,
    UUID proposalId,
    int proposalRevision,
    int baseRuleSetVersion,
    String candidateSha256,
    boolean passed,
    boolean publishable,
    String status, // PASSED, FAILED
    int positiveCasesTested,
    int positiveCasesPassed,
    int negativeCasesTested,
    int negativeCasesPassed,
    int totalReplayEvaluated,
    int regressionsDetected,
    List<String> conflicts,
    List<String> coverageLosses,
    Instant evaluatedAt,
    String proposalSha256,
    String candidateManifestSha256,
    String corpusSha256,
    List<Map<String, Object>> caseResults
) {
    public ValidationReport(UUID reportId, UUID proposalId, int proposalRevision, int baseRuleSetVersion, String candidateSha256,
                            boolean passed, boolean publishable, String status, int positiveCasesTested, int positiveCasesPassed,
                            int negativeCasesTested, int negativeCasesPassed, int totalReplayEvaluated, int regressionsDetected,
                            List<String> conflicts, List<String> coverageLosses, Instant evaluatedAt) {
        this(reportId, proposalId, proposalRevision, baseRuleSetVersion, candidateSha256, passed, publishable, status,
            positiveCasesTested, positiveCasesPassed, negativeCasesTested, negativeCasesPassed, totalReplayEvaluated, regressionsDetected,
            conflicts, coverageLosses, evaluatedAt, candidateSha256, candidateSha256, candidateSha256, List.of());
    }
}
