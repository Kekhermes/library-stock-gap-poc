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
        return DMNRuntimeBuilder.fromDefaults()
                .buildConfiguration()
                .fromResources(resources)
                .getOrElseThrow(e -> new RuntimeException("Erreur de compilation DMN KIE", e));
    }

    public List<Finding> evaluateRule(DMNRuntime runtime, RuleMetadata rule, Map<String, Object> facts, UUID copyId, List<UUID> symptomIds) {
        DMNModel model = runtime.getModel(rule.namespace(), rule.modelName());
        if (model == null) {
            // Try by namespace alone or first model
            for (DMNModel m : runtime.getModels()) {
                if (m.getNamespace().equals(rule.namespace())) {
                    model = m;
                    break;
                }
            }
        }
        if (model == null && !runtime.getModels().isEmpty()) {
            model = runtime.getModels().get(0);
        }
        if (model == null || model.hasErrors()) {
            log.warn("Modèle DMN invalide ou introuvable: {}", rule.name());
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
            if (val instanceof List<?> list) {
                for (Object item : list) {
                    if (item instanceof Map<?, ?> map) {
                        String causeCode = (String) map.get("causeCode");
                        String epistemicStr = (String) map.get("epistemicStatus");
                        if (causeCode != null && epistemicStr != null) {
                            EpistemicStatus epistemic = EpistemicStatus.valueOf(epistemicStr);
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
                        }
                    }
                }
            } else if (val instanceof Map<?, ?> map) {
                String causeCode = (String) map.get("causeCode");
                String epistemicStr = (String) map.get("epistemicStatus");
                if (causeCode != null && epistemicStr != null) {
                    findings.add(new Finding(
                        copyId,
                        symptomIds,
                        causeCode,
                        EpistemicStatus.valueOf(epistemicStr),
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
        }

        return findings;
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
