package com.library.stockgap.domain.model;

public enum AnalysisState {
    PENDING,
    RUNNING,
    DETERMINISTIC_COMPLETED,
    FALLBACK_REQUESTED,
    DIAGNOSTIC_PROPOSED,
    ACCEPTED,
    REJECTED,
    CANCELLED,
    FAILED
}
