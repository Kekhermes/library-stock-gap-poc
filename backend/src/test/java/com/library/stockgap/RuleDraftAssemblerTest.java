package com.library.stockgap;

import com.library.stockgap.domain.service.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class RuleDraftAssemblerTest {
    @Test
    void serializesTheProvidedFeelWithoutChoosingBusinessConditions() {
        var assembler = new RuleDraftAssembler(new RuleValidationPolicy());
        String xml = assembler.assemble(Map.of("name", "Condition de test", "causeCode", "TEST_CAUSE", "conditions",
            List.of(Map.of("question", "Observation fiable ?", "expression", "observationReliable = true", "factNames", List.of("observationReliable")))));
        assertTrue(xml.contains("observationReliable = true"));
        assertTrue(new DmnEngineService().validateDmnXml(xml));
        assertFalse(xml.contains("ReturnReceived"));
        assertThrows(IllegalArgumentException.class, () -> assembler.assemble(Map.of("name", "Test", "causeCode", "TEST_CAUSE", "conditions",
            List.of(Map.of("question", "Interdit", "expression", "now()", "factNames", List.of("observationReliable"))))));
    }
}