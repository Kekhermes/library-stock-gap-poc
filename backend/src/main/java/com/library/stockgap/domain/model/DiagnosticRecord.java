package com.library.stockgap.domain.model;

import java.util.List;
import java.util.UUID;

public record DiagnosticRecord(
    UUID diagnosticId,
    UUID analysisId,
    UUID caseId,
    DiagnosticConclusion conclusion,
    List<Finding> findings,
    List<String> missingEvidence,
    String summary,
    boolean isDeterministic,
    String createdOrigin
) {}
