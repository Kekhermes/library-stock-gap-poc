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
    Instant evaluatedAt
) {}
