package com.library.stockgap.domain.model;

import java.util.List;
import java.util.Locale;

/**
 * Conclusion épistémique d'un diagnostic (niveau dossier).
 * Vocabulaire distinct de {@link EpistemicStatus}, qui qualifie un finding.
 * Contrainte SQL : runtime.diagnostic_revision.conclusion.
 */
public enum DiagnosticConclusion {
    SUPPORTED,
    PARTIAL,
    INDETERMINATE,
    CONFLICT;

    /**
     * Convertit une valeur reçue d'un producteur (DMN ou investigateur) en conclusion de diagnostic.
     * Les statuts de finding (HYPOTHESIS, CONTRADICTED) sont traduits explicitement : un agent qui
     * qualifie son finding d'hypothèse produit un diagnostic INDETERMINATE, une contradiction CONFLICT.
     */
    public static DiagnosticConclusion fromAgentValue(String raw, List<Finding> findings) {
        if (raw == null || raw.isBlank()) {
            return fromFindings(findings);
        }
        return switch (raw.trim().toUpperCase(Locale.ROOT)) {
            case "SUPPORTED" -> SUPPORTED;
            case "PARTIAL" -> PARTIAL;
            case "INDETERMINATE" -> INDETERMINATE;
            case "CONFLICT" -> CONFLICT;
            case "HYPOTHESIS" -> INDETERMINATE;
            case "CONTRADICTED" -> CONFLICT;
            default -> throw new IllegalArgumentException("Conclusion de diagnostic inconnue: " + raw);
        };
    }

    /** Repli conservateur : seule une couverture entièrement étayée donne SUPPORTED. */
    public static DiagnosticConclusion fromFindings(List<Finding> findings) {
        if (findings == null || findings.isEmpty()) {
            return INDETERMINATE;
        }
        boolean allSupported = true;
        boolean anyContradicted = false;
        for (Finding finding : findings) {
            if (finding.epistemicStatus() == EpistemicStatus.CONTRADICTED) {
                anyContradicted = true;
                allSupported = false;
            } else if (finding.epistemicStatus() != EpistemicStatus.SUPPORTED) {
                allSupported = false;
            }
        }
        if (anyContradicted) {
            return CONFLICT;
        }
        return allSupported ? SUPPORTED : INDETERMINATE;
    }
}
