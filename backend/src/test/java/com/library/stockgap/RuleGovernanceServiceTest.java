package com.library.stockgap;

import com.library.stockgap.domain.service.DmnEngineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RuleGovernanceServiceTest {

    private DmnEngineService dmnEngineService;

    @BeforeEach
    void setUp() {
        dmnEngineService = new DmnEngineService();
    }

    @Test
    void testValidDmnSyntaxPassesPrevalidation() {
        String validDmn = """
            <?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="https://www.omg.org/spec/DMN/20230324/MODEL/" id="d_test" name="TestModel" namespace="urn:test:1">
              <decision id="dec_1" name="decision1">
                <variable id="v_1" name="decision1" typeRef="string"/>
                <literalExpression><text>"VALID"</text></literalExpression>
              </decision>
            </definitions>
            """;

        boolean valid = dmnEngineService.validateDmnXml(validDmn);
        assertTrue(valid, "Le DMN syntaxiquement valide doit passer la prévalidation");
    }

    @Test
    void testInvalidDmnSyntaxIsRejected() {
        String invalidDmn = "<definitions>broken XML without model";

        boolean valid = dmnEngineService.validateDmnXml(invalidDmn);
        assertFalse(valid, "Le DMN corrompu doit être rejeté");
    }
}
