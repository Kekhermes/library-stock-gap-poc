package com.library.stockgap.domain.model;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record CaseDossier(
    UUID caseId,
    UUID copyId,
    String barcode,
    String title,
    String isbn,
    String owningSiteId,
    Instant inventoryReferenceTime,
    CaseStatus status,
    List<Map<String, Object>> symptoms,
    Map<String, Object> systemState,
    List<Map<String, Object>> observations,
    List<Map<String, Object>> sourceAvailability,
    List<Map<String, Object>> operatorNotes,
    List<Map<String, Object>> timeline,
    List<Map<String, Object>> analyses,
    Map<String, Object> currentDiagnostic,
    Map<String, Object> currentProposal,
    UUID activeConversationId
) {}
