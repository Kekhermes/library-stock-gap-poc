package com.library.stockgap.domain.model;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record Publication(
    int ruleSetVersion,
    String origin,
    String manifestSha256,
    Map<String, Object> manifest,
    List<Map<String, Object>> members,
    boolean active,
    Instant createdAt,
    Instant sealedAt
) {}
