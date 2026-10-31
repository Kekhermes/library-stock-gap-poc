package com.library.stockgap.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.stockgap.domain.model.EvidenceSnapshot;
import com.library.stockgap.domain.model.Finding;
import com.library.stockgap.domain.model.RuleMetadata;
import com.library.stockgap.infrastructure.persistence.AnalysisRepository;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RuleReplayService {
    private final ObjectMapper mapper;
    private final ResourceLoader resources;
    private final DeterministicAnalysisService deterministic;
    private final DmnEngineService dmn;
    private final RuleValidationPolicy policy;

    public RuleReplayService(ObjectMapper mapper, ResourceLoader resources, DeterministicAnalysisService deterministic,
                             DmnEngineService dmn, RuleValidationPolicy policy) {
        this.mapper = mapper;
        this.resources = resources;
        this.deterministic = deterministic;
        this.dmn = dmn;
        this.policy = policy;
    }

    private record TestCase(UUID testId, String scope, String variant, boolean expectedMatch, EvidenceSnapshot evidence) {}
    private record Corpus(String schemaVersion, String corpusVersion, String generatorVersion, String referenceTime, List<TestCase> cases) {}
    public record Replay(boolean passed, int positiveTested, int positivePassed, int negativeTested, int negativePassed,
                         int replayed, int regressions, String corpusSha256, List<String> conflicts,
                         List<String> coverageLosses, List<Map<String, Object>> results) {}

    public String corpusFingerprint() {
        try (InputStream input = resources.getResource("classpath:rules/validation-corpus.json").getInputStream()) {
            return AnalysisRepository.sha256Hex(new String(input.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8));
        } catch (IOException failure) {
            throw new IllegalStateException("Corpus de validation indisponible", failure);
        }
    }

    public Replay verify(RuleMetadata candidate, List<RuleMetadata> before, List<RuleMetadata> after, EvidenceSnapshot origin) {
        List<String> failures = new ArrayList<>();
        List<String> losses = new ArrayList<>();
        List<Map<String, Object>> results = new ArrayList<>();
        String fingerprint = corpusFingerprint();
        int positives = 0, positivePassed = 0, negatives = 0, negativePassed = 0, replayed = 0, regressions = 0;
        try {
            prepare(after);
            Corpus corpus;
            try (InputStream input = resources.getResource("classpath:rules/validation-corpus.json").getInputStream()) {
                corpus = mapper.readValue(input, Corpus.class);
            }
            if (!"1.0.0".equals(corpus.schemaVersion()) || corpus.cases().isEmpty()) throw new IllegalArgumentException("Corpus incompatible ou vide");
            var facts = deterministic.facts(origin);
            String scope = "LOCATION_MISMATCH".equals(facts.get("symptomKind")) ? "LOCATION_MISMATCH" : String.valueOf(facts.get("latestBusinessEventType"));
            if (corpus.cases().stream().noneMatch(test -> test.scope().equals(scope) && test.expectedMatch())) {
                throw new IllegalArgumentException("Aucun positif indépendant pour la portée d'origine : " + scope);
            }
            positives++;
            var originResult = deterministic.evaluate(origin, List.of(candidate));
            boolean originPassed = originResult.fullyCovered() && originResult.findings().stream().allMatch(finding -> finding.causeCode().equals(candidate.causeCode()));
            if (originPassed) positivePassed++;
            results.add(Map.of("testId", origin.caseId(), "category", "ORIGIN", "passed", originPassed,
                "expectedMatch", true, "actualMatch", originResult.fullyCovered(), "trace", originResult.trace()));
            for (TestCase test : corpus.cases()) {
                boolean expected = scope.equals(test.scope()) && test.expectedMatch();
                if (expected) positives++;
                else negatives++;
                var candidateResult = deterministic.evaluate(test.evidence(), List.of(candidate));
                boolean actual = candidateResult.fullyCovered();
                boolean testPassed = expected ? actual && candidateResult.findings().stream().allMatch(finding -> finding.causeCode().equals(candidate.causeCode()))
                    : !actual && candidateResult.findings().isEmpty();
                if (testPassed) {
                    if (expected) positivePassed++;
                    else negativePassed++;
                }
                var baseline = deterministic.evaluate(test.evidence(), before);
                var changed = deterministic.evaluate(test.evidence(), after);
                boolean regression = baseline.fullyCovered() && (!changed.fullyCovered() || !causes(baseline.findings()).equals(causes(changed.findings())));
                if (regression) {
                    regressions++;
                    losses.add(test.testId().toString());
                }
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("testId", test.testId());
                row.put("category", expected ? "POSITIVE" : "NEGATIVE");
                row.put("variant", test.variant());
                row.put("passed", testPassed && !regression);
                row.put("expectedMatch", expected);
                row.put("actualMatch", actual);
                row.put("beforeCovered", baseline.fullyCovered());
                row.put("afterCovered", changed.fullyCovered());
                row.put("beforeCauses", causes(baseline.findings()));
                row.put("afterCauses", causes(changed.findings()));
                row.put("regression", regression);
                row.put("trace", candidateResult.trace());
                results.add(row);
                replayed++;
            }
        } catch (Exception failure) {
            failures.add(String.valueOf(failure.getMessage()));
        }
        boolean passed = failures.isEmpty() && positives > 1 && positives == positivePassed
            && negatives > 0 && negatives == negativePassed && replayed > 0 && regressions == 0;
        return new Replay(passed, positives, positivePassed, negatives, negativePassed, replayed, regressions,
            fingerprint, List.copyOf(failures), List.copyOf(losses), List.copyOf(results));
    }

    private Set<String> causes(List<Finding> findings) {
        return findings.stream().map(Finding::causeCode).collect(Collectors.toCollection(java.util.TreeSet::new));
    }

    public void prepare(List<RuleMetadata> rules) {
        for (RuleMetadata rule : rules) {
            policy.validate(rule.dmnXml());
            if (rule.coveredSymptomKinds().isEmpty() || rule.causeCode().isBlank()) throw new IllegalArgumentException("Métadonnées de règle incomplètes");
        }
        dmn.createRuntime(rules);
    }
}