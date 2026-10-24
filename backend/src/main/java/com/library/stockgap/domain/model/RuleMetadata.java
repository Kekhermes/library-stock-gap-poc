package com.library.stockgap.domain.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record RuleMetadata(
    String ruleId,
    int ruleVersion,
    String name,
    String origin,
    String dmnPath,
    String namespace,
    String modelName,
    String decisionName,
    String causeCode,
    List<String> coveredSymptomKinds,
    List<String> recommendations,
    List<String> limitations,
    String artifactSha256,
    String dmnXml,
    boolean active,
    Instant sealedAt,
    UUID originAcceptanceId
) {}
