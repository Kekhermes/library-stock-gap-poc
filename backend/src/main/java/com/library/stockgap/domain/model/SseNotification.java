package com.library.stockgap.domain.model;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record SseNotification(
    String cursor,
    String eventType,
    String schemaVersion,
    UUID datasetEpoch,
    Instant occurredAt,
    Map<String, Object> data
) {}
