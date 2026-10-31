package com.library.stockgap;
import com.library.stockgap.domain.service.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
class RuleInspectionServiceTest {
    @Test
    void showsTrueFalseAndUnknownForTheActualFeelExpression() {
        var policy=new RuleValidationPolicy();
        String xml=new RuleDraftAssembler(policy).assemble(Map.of("name","Test natif","causeCode","TEST_CAUSE","conditions",List.of(Map.of("question","Observation fiable ?","expression","observationReliable = true","factNames",List.of("observationReliable")))));
        var inspection=new RuleInspectionService(policy);
        assertEquals("TRUE",inspection.inspect(xml,Map.of("observationReliable",true)).getFirst().get("result"));
        assertEquals("FALSE",inspection.inspect(xml,Map.of("observationReliable",false)).getFirst().get("result"));
        assertEquals("UNKNOWN",inspection.inspect(xml,new HashMap<>()).getFirst().get("result"));
    }
}