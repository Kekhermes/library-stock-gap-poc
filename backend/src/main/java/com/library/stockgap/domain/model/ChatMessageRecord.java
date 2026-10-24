package com.library.stockgap.domain.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ChatMessageRecord(
    UUID messageId,
    UUID conversationId,
    int turnId,
    String senderRole, // ADMIN, INVESTIGATOR, RULE_AUTHOR
    String text,
    boolean isFinal,
    List<EvidenceRef> citedEvidence,
    Instant sentAt
) {}
