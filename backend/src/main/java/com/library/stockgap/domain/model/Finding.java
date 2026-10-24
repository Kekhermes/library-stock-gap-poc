package com.library.stockgap.domain.model;

import java.util.List;
import java.util.UUID;

public record Finding(
    UUID subjectCopyId,
    List<UUID> coveredSymptomIds,
    String causeCode,
    EpistemicStatus epistemicStatus,
    List<String> assertions,
    List<EvidenceRef> evidenceRefs,
    List<EvidenceRef> contradictionRefs,
    List<String> recommendedChecks,
    String explanation,
    String ruleId,
    Integer ruleVersion
) {}
