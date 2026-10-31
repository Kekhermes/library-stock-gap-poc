package com.library.stockgap.domain.service;

import com.library.stockgap.domain.model.EpistemicStatus;
import com.library.stockgap.domain.model.Finding;
import com.library.stockgap.domain.model.RuleMetadata;
import org.kie.api.io.Resource;
import org.kie.dmn.api.core.*;
import org.kie.dmn.core.internal.utils.DMNRuntimeBuilder;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.io.StringReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import java.util.*;

@Service
public class DmnEngineService {
    private static final Logger log = LoggerFactory.getLogger(DmnEngineService.class);

    public DMNRuntime createRuntime(List<RuleMetadata> rules) {
        List<Resource> resources = new ArrayList<>();
        for (RuleMetadata r : rules) {
            resources.add(ResourceFactory.newByteArrayResource(r.dmnXml().getBytes(StandardCharsets.UTF_8)));
        }
        DMNRuntime runtime = DMNRuntimeBuilder.fromDefaults().buildConfiguration().fromResources(resources)
            .getOrElseThrow(error -> new IllegalStateException("Compilation DMN impossible", error));
        if (runtime.getModels().size() != rules.size() || runtime.getModels().stream().anyMatch(DMNModel::hasErrors)) {
            throw new IllegalStateException("Référentiel DMN incomplet ou invalide : aucune règle ne sera écartée silencieusement");
        }
        return runtime;
    }

    private static String extractXmlAttribute(String xml, String attr) {
        try {
            XMLInputFactory factory = XMLInputFactory.newFactory();
            factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
            factory.setProperty("javax.xml.stream.isSupportingExternalEntities", false);
            var reader = factory.createXMLStreamReader(new StringReader(xml));
            try {
                while (reader.hasNext()) {
                    if (reader.next() == XMLStreamConstants.START_ELEMENT) return reader.getAttributeValue(null, attr);
                }
            } finally {
                reader.close();
            }
            return null;
        } catch (Exception error) {
            throw new IllegalStateException("Attributs du modèle DMN illisibles", error);
        }
    }

    private DMNModel modelFor(DMNRuntime runtime, RuleMetadata rule) {
        String ns = rule.namespace();
        String name = rule.modelName();
        String xmlNs = extractXmlAttribute(rule.dmnXml(), "namespace");
        String xmlName = extractXmlAttribute(rule.dmnXml(), "name");

        DMNModel model = null;
        if (xmlNs != null && xmlName != null) {
            model = runtime.getModel(xmlNs, xmlName);
        }
        if (model == null && ns != null && name != null) {
            model = runtime.getModel(ns, name);
        }
        if (model == null || model.hasErrors()) {
            throw new IllegalStateException("Modèle DMN invalide ou introuvable pour " + rule.ruleId());
        }
        return model;
    }

    public Set<String> inputNames(DMNRuntime runtime, RuleMetadata rule) {
        Set<String> names = new TreeSet<>();
        for (var input : modelFor(runtime, rule).getInputs()) names.add(input.getName());
        return names;
    }

    public List<Finding> evaluateRule(DMNRuntime runtime, RuleMetadata rule, Map<String, Object> facts, UUID copyId, List<UUID> symptomIds) {
        DMNModel model = modelFor(runtime, rule);

        DMNContext ctx = runtime.newContext();
        for (Map.Entry<String, Object> entry : facts.entrySet()) {
            ctx.set(entry.getKey(), entry.getValue());
        }

        DMNResult result = runtime.evaluateAll(model, ctx);
        if (result.hasErrors()) {
            throw new IllegalStateException("Évaluation DMN en échec pour " + rule.ruleId() + ": " + result.getMessages());
        }

        List<Finding> findings = new ArrayList<>();
        for (DMNDecisionResult dr : result.getDecisionResults()) {
            if (!rule.decisionName().equals(dr.getDecisionName()) && !rule.decisionName().equals(dr.getDecisionId())) continue;
            Object val = dr.getResult();
            if (val == null) continue;
            if (val instanceof List<?> list) {
                for (Object item : list) {
                    processDecisionItem(item, rule, copyId, symptomIds, findings);
                }
            } else {
                processDecisionItem(val, rule, copyId, symptomIds, findings);
            }
        }

        return findings;
    }

    private void processDecisionItem(Object item, RuleMetadata rule, UUID copyId, List<UUID> symptomIds, List<Finding> findings) {
        if (item instanceof Map<?, ?> map) {
            String causeCode = extractCauseCode(map, rule);
            String epistemicStr = map.get("epistemicStatus") instanceof String value ? value : null;
            EpistemicStatus epistemic = parseEpistemicStatus(epistemicStr);
            findings.add(new Finding(
                copyId,
                symptomIds,
                causeCode,
                epistemic,
                List.of("Règle " + rule.ruleId() + " (" + rule.name() + ") applicable."),
                Collections.emptyList(),
                Collections.emptyList(),
                rule.recommendations(),
                "Diagnostic déterministe par DMN: " + rule.name(),
                rule.ruleId(),
                rule.ruleVersion()
            ));
        } else throw new IllegalStateException("Sortie DMN non conforme pour " + rule.ruleId() + " : causeCode et epistemicStatus requis");
    }

    private String extractCauseCode(Map<?, ?> map, RuleMetadata rule) {
        if (map.get("causeCode") instanceof String value && !value.isBlank()) return value;
        throw new IllegalStateException("Sortie DMN non conforme pour " + rule.ruleId() + " : causeCode absent ou invalide");
    }

    private EpistemicStatus parseEpistemicStatus(String str) {
        if (!"SUPPORTED".equals(str) && !"HYPOTHESIS".equals(str)) throw new IllegalStateException("Sortie DMN non conforme : epistemicStatus absent ou invalide");
        return EpistemicStatus.valueOf(str);
    }

    public boolean validateDmnXml(String dmnXml) {
        try {
            Resource res = ResourceFactory.newByteArrayResource(dmnXml.getBytes(StandardCharsets.UTF_8));
            DMNRuntime rt = DMNRuntimeBuilder.fromDefaults().buildConfiguration()
                    .fromResources(List.of(res)).getOrElseThrow(RuntimeException::new);
            return !rt.getModels().isEmpty() && !rt.getModels().get(0).hasErrors();
        } catch (Exception e) {
            log.debug("Validation DMN échouée: {}", e.getMessage());
            return false;
        }
    }
}
