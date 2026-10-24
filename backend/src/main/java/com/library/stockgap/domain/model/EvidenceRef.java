package com.library.stockgap.domain.model;

import java.util.UUID;

public record EvidenceRef(
    String source,
    UUID recordId,
    UUID evidenceSnapshotId,
    String fieldPath
) {}
