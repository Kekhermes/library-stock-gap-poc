package com.library.stockgap.domain.model;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record EvidenceSnapshot(
    UUID snapshotId,
    UUID caseId,
    UUID copyId,
    String schemaVersion,
    Instant inventoryReferenceTime,
    Instant dataCapturedAt,
    Map<String, Object> caseFacts,
    List<Map<String, Object>> sourceEvents,
    List<Map<String, Object>> operatorNotes,
    Map<String, Object> systemState,
    List<Map<String, Object>> observations,
    List<Map<String, Object>> sourceAvailability,
    List<Map<String, Object>> symptoms
) {}
