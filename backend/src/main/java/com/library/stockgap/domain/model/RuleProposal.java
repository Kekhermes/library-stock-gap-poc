package com.library.stockgap.domain.model;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record RuleProposal(
    UUID proposalId,
    int currentRevision,
    String state, // DRAFT, VALIDATED, PUBLISHED, REJECTED, OBSOLETE
    UUID originDiagnosticId,
    UUID caseId,
    String operation, // CREATE, REVISE, RETIRE
    String targetRuleId,
    String ruleName,
    String causeCode,
    List<String> coveredSymptomKinds,
    String rationale,
    String dmnXml,
    String dmnSha256,
    ValidationReport validationReport,
    Instant createdAt,
    Instant updatedAt
) {}
