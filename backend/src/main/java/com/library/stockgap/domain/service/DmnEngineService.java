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
import java.util.*;

@Service
public class DmnEngineService {
    private static final Logger log = LoggerFactory.getLogger(DmnEngineService.class);

    public DMNRuntime createRuntime(List<RuleMetadata> rules) {
        List<Resource> resources = new ArrayList<>();
        for (RuleMetadata r : rules) {
            resources.add(ResourceFactory.newByteArrayResource(r.dmnXml().getBytes(StandardCharsets.UTF_8)));
        }
        try {
            return DMNRuntimeBuilder.fromDefaults()
                    .buildConfiguration()
                    .fromResources(resources)
                    .getOrElseThrow(e -> new RuntimeException("Erreur de compilation DMN KIE", e));
        } catch (Exception e) {
            log.warn("Erreur de compilation globale DMN: {}, repli sur les règles valides", e.getMessage());
            List<Resource> validResources = new ArrayList<>();
            for (RuleMetadata r : rules) {
                if (validateDmnXml(r.dmnXml())) {
                    validResources.add(ResourceFactory.newByteArrayResource(r.dmnXml().getBytes(StandardCharsets.UTF_8)));
                }
            }
            if (validResources.isEmpty()) {
                throw e;
            }
            return DMNRuntimeBuilder.fromDefaults()
                    .buildConfiguration()
                    .fromResources(validResources)
                    .getOrElseThrow(ex -> new RuntimeException("Erreur de compilation DMN KIE", ex));
        }
    }

    private static String extractXmlAttribute(String xml, String attr) {
        if (xml == null) return null;
        var p = java.util.regex.Pattern.compile(attr + "\\s*=\\s*\"([^\"]+)\"");
        var m = p.matcher(xml);
        return m.find() ? m.group(1) : null;
    }

    public List<Finding> evaluateRule(DMNRuntime runtime, RuleMetadata rule, Map<String, Object> facts, UUID copyId, List<UUID> symptomIds) {
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
        if (model == null) {
            String targetNs = xmlNs != null ? xmlNs : ns;
            for (DMNModel m : runtime.getModels()) {
                if (m.getNamespace().equals(targetNs)) {
                    model = m;
                    break;
                }
            }
        }
        if (model == null && (xmlName != null || name != null)) {
            String targetName = xmlName != null ? xmlName : name;
            for (DMNModel m : runtime.getModels()) {
                if (m.getName().equals(targetName)) {
                    model = m;
                    break;
                }
            }
        }
        if (model == null) {
            try {
                DMNRuntime isolated = createRuntime(List.of(rule));
                if (!isolated.getModels().isEmpty()) {
                    model = isolated.getModels().get(0);
                    runtime = isolated;
                }
            } catch (Exception e) {
                log.warn("Impossible de compiler la règle isolée {}: {}", rule.ruleId(), e.getMessage());
            }
        }
        if (model == null || model.hasErrors()) {
            log.warn("Modèle DMN invalide ou introuvable pour la règle {}: {}", rule.ruleId(), rule.name());
            return Collections.emptyList();
        }

        DMNContext ctx = runtime.newContext();
        for (Map.Entry<String, Object> entry : facts.entrySet()) {
            ctx.set(entry.getKey(), entry.getValue());
        }

        DMNResult result = runtime.evaluateAll(model, ctx);
        if (result.hasErrors()) {
            log.warn("Erreur d'évaluation DMN pour règle {}: {}", rule.ruleId(), result.getMessages());
            return Collections.emptyList();
        }

        List<Finding> findings = new ArrayList<>();
        for (DMNDecisionResult dr : result.getDecisionResults()) {
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
            String epistemicStr = (String) map.get("epistemicStatus");
            EpistemicStatus epistemic = parseEpistemicStatus(epistemicStr);
            findings.add(new Finding(
                copyId,
                symptomIds,
                causeCode,
                epistemic,
                List.of("Règle " + rule.ruleId() + " (" + rule.name() + ") validée."),
                Collections.emptyList(),
                Collections.emptyList(),
                rule.recommendations(),
                "Diagnostic déterministe par DMN: " + rule.name(),
                rule.ruleId(),
                rule.ruleVersion()
            ));
        } else if (item instanceof String s && !s.isBlank()) {
            findings.add(new Finding(
                copyId,
                symptomIds,
                rule.causeCode(),
                EpistemicStatus.SUPPORTED,
                List.of("Règle " + rule.ruleId() + " (" + rule.name() + ") validée."),
                Collections.emptyList(),
                Collections.emptyList(),
                rule.recommendations(),
                "Diagnostic déterministe par DMN: " + rule.name(),
                rule.ruleId(),
                rule.ruleVersion()
            ));
        }
    }

    private String extractCauseCode(Map<?, ?> map, RuleMetadata rule) {
        if (map.containsKey("causeCode") && map.get("causeCode") != null) {
            return map.get("causeCode").toString();
        }
        if (map.containsKey("cause") && map.get("cause") != null) {
            return map.get("cause").toString();
        }
        return rule.causeCode();
    }

    private EpistemicStatus parseEpistemicStatus(String str) {
        if (str == null || str.isBlank()) return EpistemicStatus.SUPPORTED;
        try {
            return EpistemicStatus.valueOf(str);
        } catch (Exception e) {
            return EpistemicStatus.SUPPORTED;
        }
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
