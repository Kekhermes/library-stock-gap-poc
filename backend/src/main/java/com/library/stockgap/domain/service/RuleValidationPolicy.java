package com.library.stockgap.domain.service;

import org.kie.dmn.feel.FEEL;
import org.kie.dmn.feel.lang.ast.ASTNode;
import org.kie.dmn.feel.lang.ast.InfixOpNode;
import org.kie.dmn.feel.lang.ast.InfixOperator;
import org.kie.dmn.feel.lang.impl.CompiledExpressionImpl;
import org.kie.dmn.feel.lang.impl.UnaryTestInterpretedExecutableExpression;
import org.kie.dmn.feel.codegen.feel11.ProcessedUnaryTest;
import org.kie.dmn.feel.lang.types.BuiltInType;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RuleValidationPolicy {
    public static final String VERSION = "2.0.0";
    private static final Set<String> FACTS = Set.of("symptomKind", "expectedValue", "observedValue", "expectedStatus", "observedStatus",
        "systemStatus", "expectedSiteId", "observedSiteId", "expectedLocationId", "observedLocationId", "observationReliable",
        "observationQuality", "latestIntegrationOutcome", "latestIntegrationErrorCode", "latestProjectedStatus", "latestBusinessEventType",
        "businessEventType", "integrationsAvailabilityStatus", "loansAvailabilityStatus", "returnsAvailabilityStatus", "receiptsAvailabilityStatus",
        "transfersAvailabilityStatus", "repairsAvailabilityStatus", "inventoryAvailabilityStatus", "loansCount", "loansClosedCount", "returnsCount",
        "transfersDispatchedCount", "transfersReceivedCount", "repairsCount", "symptomsCount", "observationsCount",
        "integrationOutcome", "integrationErrorCode", "errorCode", "latestErrorCode", "projectedStatus", "repairOutcome", "repairProjectedStatus");
    private static final Set<String> ELEMENTS = Set.of("definitions", "description", "inputData", "variable", "decision",
        "informationRequirement", "requiredInput", "decisionTable", "input", "inputExpression", "output", "rule", "inputEntry", "outputEntry", "text");
    private static final Set<String> NODES = Set.of("StringNode", "NumberNode", "BooleanNode", "NullNode", "NameRefNode", "InfixOpNode",
        "SignedUnaryNode", "UnaryTestNode", "UnaryTestListNode", "DashNode", "ListNode", "InNode", "BetweenNode", "RangeNode");
    private static final Set<InfixOperator> OPERATORS = Set.of(InfixOperator.EQ, InfixOperator.NE, InfixOperator.LT, InfixOperator.LTE,
        InfixOperator.GT, InfixOperator.GTE, InfixOperator.AND, InfixOperator.OR);

    public void validate(String xml) {
        if (xml == null || xml.getBytes(StandardCharsets.UTF_8).length > 20000) throw new IllegalArgumentException("DMN absent ou supérieur à 20 Ko");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            var document = factory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
            if (!"https://www.omg.org/spec/DMN/20230324/MODEL/".equals(document.getDocumentElement().getNamespaceURI())) {
                throw new IllegalArgumentException("Version DMN non prise en charge dans la démo");
            }
            var all = document.getElementsByTagName("*");
            if (all.getLength() > 256) throw new IllegalArgumentException("DMN trop complexe");
            for (int index = 0; index < all.getLength(); index++) {
                Element element = (Element) all.item(index);
                if (!ELEMENTS.contains(element.getLocalName()) || !document.getDocumentElement().getNamespaceURI().equals(element.getNamespaceURI())) {
                    throw new IllegalArgumentException("Élément DMN interdit : " + element.getNodeName());
                }
                if (element.hasAttribute("expressionLanguage") && !element.getAttribute("expressionLanguage").isBlank()
                        && !element.getAttribute("expressionLanguage").equals("https://www.omg.org/spec/DMN/20230324/FEEL/")) {
                    throw new IllegalArgumentException("Langage d'expression autre que FEEL interdit");
                }
                if (element.hasAttribute("typeRef") && !Set.of("string", "boolean", "number", "Any", "").contains(element.getAttribute("typeRef"))) {
                    throw new IllegalArgumentException("Type DMN non disponible dans les faits simples");
                }
                if (element.hasAttribute("href") && !element.getAttribute("href").startsWith("#")) {
                    throw new IllegalArgumentException("Référence externe de modèle interdite");
                }
            }
            if (document.getElementsByTagNameNS("*", "decision").getLength() != 1
                    || document.getElementsByTagNameNS("*", "decisionTable").getLength() != 1
                    || document.getElementsByTagNameNS("*", "rule").getLength() != 1) {
                throw new IllegalArgumentException("La démo exige une décision, une table et une seule ligne");
            }
            Element table = (Element) document.getElementsByTagNameNS("*", "decisionTable").item(0);
            if (!Set.of("COLLECT", "UNIQUE").contains(table.getAttribute("hitPolicy")) || table.hasAttribute("aggregation")) {
                throw new IllegalArgumentException("Politique de table hors du périmètre à une ligne");
            }
            var inputs = document.getElementsByTagNameNS("*", "inputData");
            if (inputs.getLength() < 1 || inputs.getLength() > 8) throw new IllegalArgumentException("Entre une et huit données d'entrée sont requises");
            Set<String> declared = new java.util.HashSet<>();
            for (int index = 0; index < inputs.getLength(); index++) {
                String name = ((Element) inputs.item(index)).getAttribute("name");
                if (!FACTS.contains(name) || !declared.add(name)) throw new IllegalArgumentException("Fait non disponible ou déclaré deux fois : " + name);
            }
            var outputs = document.getElementsByTagNameNS("*", "output");
            Set<String> outputNames = new java.util.HashSet<>();
            for (int index = 0; index < outputs.getLength(); index++) outputNames.add(((Element) outputs.item(index)).getAttribute("name"));
            if (outputs.getLength() != 2 || !outputNames.equals(Set.of("causeCode", "epistemicStatus"))) {
                throw new IllegalArgumentException("Les sorties causeCode et epistemicStatus sont requises");
            }
            FEEL feel = FEEL.newInstance(getClass().getClassLoader(), List.of());
            var context = feel.newCompilerContext();
            context.setDoCompile(false);
            for (String name : FACTS) context.addInputVariableType(name, name.equals("observationReliable") ? BuiltInType.BOOLEAN
                : name.endsWith("Count") ? BuiltInType.NUMBER : BuiltInType.STRING);
            List<String> errors = new ArrayList<>();
            feel.addListener(event -> { if (event.getSeverity().name().equals("ERROR")) errors.add(event.getMessage()); });
            var texts = document.getElementsByTagNameNS("*", "text");
            for (int index = 0; index < texts.getLength(); index++) {
                Element text = (Element) texts.item(index);
                String expression = text.getTextContent();
                if (expression.length() > 600) throw new IllegalArgumentException("Expression FEEL trop longue");
                String parent = text.getParentNode().getLocalName();
                ASTNode node;
                if (parent.equals("inputEntry")) {
                    var compiled = feel.processUnaryTests(expression, context);
                    if (compiled instanceof UnaryTestInterpretedExecutableExpression unary) node = unary.getASTNode();
                    else if (compiled instanceof ProcessedUnaryTest unary) node = unary.getInterpreted().getASTNode();
                    else if (compiled instanceof CompiledExpressionImpl implementation) node = implementation.getExpression();
                    else throw new IllegalArgumentException("Type de test FEEL non pris en charge : " + compiled.getClass().getName());
                } else node = feel.processExpression(expression, context).getInterpreted().getASTNode();
                if (!errors.isEmpty()) throw new IllegalArgumentException("Expression FEEL invalide : " + errors);
                if (parent.equals("outputEntry") && !node.getClass().getSimpleName().equals("StringNode")) {
                    throw new IllegalArgumentException("La sortie d'une ligne doit être une chaîne constante");
                }
                inspect(node, 0, new int[]{0}, declared);
            }
        } catch (IllegalArgumentException failure) {
            throw failure;
        } catch (Exception failure) {
            throw new IllegalArgumentException("Structure DMN ou expressions non conformes", failure);
        }
    }

    private void inspect(ASTNode node, int depth, int[] count, Set<String> declared) {
        if (node == null || depth > 12 || ++count[0] > 128 || !NODES.contains(node.getClass().getSimpleName())) {
            throw new IllegalArgumentException("Expression FEEL hors sous-ensemble ou trop complexe");
        }
        if (node instanceof InfixOpNode operation && !OPERATORS.contains(operation.getOperator())) {
            throw new IllegalArgumentException("Opérateur FEEL non autorisé : " + operation.getOperator());
        }
        if (node.getClass().getSimpleName().equals("NameRefNode") && !declared.contains(node.getText()) && !node.getText().equals("?")) {
            throw new IllegalArgumentException("Référence FEEL non disponible : " + node.getText());
        }
        for (ASTNode child : node.getChildrenNode()) inspect(child, depth + 1, count, declared);
    }
}