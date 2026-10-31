package com.library.stockgap.domain.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.*;

@Service
public class RuleDraftAssembler {
    private static final String NS = "https://www.omg.org/spec/DMN/20230324/MODEL/";
    private final RuleValidationPolicy policy;
    public RuleDraftAssembler(RuleValidationPolicy policy) { this.policy = policy; }

    public String assemble(Map<String, Object> draft) {
        try {
            String name = Objects.toString(draft.get("name"), "");
            String cause = Objects.toString(draft.get("causeCode"), "");
            if (name.isBlank() || !cause.matches("[A-Z][A-Z0-9_]{1,79}")) throw new IllegalArgumentException("Nom ou code de cause invalide");
            if (!(draft.get("conditions") instanceof List<?> conditions) || conditions.isEmpty() || conditions.size() > 8) throw new IllegalArgumentException("Une à huit conditions FEEL requises");
            Map<String, String> types = policy.factTypes();
            Set<String> facts = new TreeSet<>();
            for (Object raw : conditions) {
                Map<?, ?> condition = (Map<?, ?>) raw;
                if (!(condition.get("factNames") instanceof List<?> names)) throw new IllegalArgumentException("Faits de condition requis");
                for (Object fact : names) {
                    if (!types.containsKey(fact.toString())) throw new IllegalArgumentException("Fait indisponible : " + fact);
                    facts.add(fact.toString());
                }
            }
            if (facts.isEmpty() || facts.size() > 8) throw new IllegalArgumentException("Entre un et huit faits déclarés sont requis");
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = document.createElementNS(NS, "definitions");
            root.setAttribute("xmlns", NS); root.setAttribute("id", "draft"); root.setAttribute("name", "GeneratedRule");
            root.setAttribute("namespace", "urn:stockgap:generated:" + UUID.randomUUID()); document.appendChild(root);
            Element description = child(document, root, "description"); description.setTextContent(name);
            for (String fact : facts) {
                Element input = child(document, root, "inputData"); input.setAttribute("id", "fact_" + fact); input.setAttribute("name", fact);
                Element variable = child(document, input, "variable"); variable.setAttribute("name", fact); variable.setAttribute("typeRef", types.get(fact));
            }
            Element decision = child(document, root, "decision"); decision.setAttribute("id", "decision"); decision.setAttribute("name", "decision");
            child(document, decision, "variable").setAttribute("name", "decision");
            for (String fact : facts) child(document, child(document, decision, "informationRequirement"), "requiredInput").setAttribute("href", "#fact_" + fact);
            Element table = child(document, decision, "decisionTable"); table.setAttribute("hitPolicy", "COLLECT");
            for (Object raw : conditions) {
                Map<?, ?> condition = (Map<?, ?>) raw;
                Element input = child(document, table, "input"); input.setAttribute("label", Objects.toString(condition.get("question"), ""));
                Element expression = child(document, input, "inputExpression"); expression.setAttribute("typeRef", "boolean");
                child(document, expression, "text").setTextContent(Objects.toString(condition.get("expression"), ""));
            }
            for (String outputName : List.of("causeCode", "epistemicStatus")) {
                Element output = child(document, table, "output"); output.setAttribute("name", outputName); output.setAttribute("typeRef", "string");
            }
            Element row = child(document, table, "rule"); row.setAttribute("id", "generated_row");
            for (Object ignored : conditions) child(document, child(document, row, "inputEntry"), "text").setTextContent("true");
            child(document, child(document, row, "outputEntry"), "text").setTextContent("\"" + cause + "\"");
            child(document, child(document, row, "outputEntry"), "text").setTextContent("\"SUPPORTED\"");
            StringWriter result = new StringWriter();
            TransformerFactory.newInstance().newTransformer().transform(new DOMSource(document), new StreamResult(result));
            String xml = result.toString(); policy.validate(xml); return xml;
        } catch (IllegalArgumentException failure) { throw failure; }
        catch (Exception failure) { throw new IllegalArgumentException("Conditions FEEL ou structure de brouillon non conformes", failure); }
    }

    private Element child(Document document, Element parent, String name) {
        Element child = document.createElementNS(NS, name); parent.appendChild(child); return child;
    }
}