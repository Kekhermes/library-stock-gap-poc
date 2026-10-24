import java.io.File;
import java.util.*;
import org.kie.api.io.Resource;
import org.kie.internal.io.ResourceFactory;
import org.kie.dmn.api.core.*;
import org.kie.dmn.core.internal.utils.DMNRuntimeBuilder;

/** Tests d'acceptation du fichier DMN, pas une implémentation du backend. */
public class VerifyBootstrapDmn {
    static int checks = 0;
    static void check(DMNRuntime runtime, DMNModel model, String label,
                      String expectedSite, String observedSite, String expectedLocation,
                      String observedLocation, Boolean reliable, boolean shouldMatch) {
        DMNContext ctx = runtime.newContext();
        ctx.set("expectedSiteId", expectedSite);
        ctx.set("observedSiteId", observedSite);
        ctx.set("expectedLocationId", expectedLocation);
        ctx.set("observedLocationId", observedLocation);
        ctx.set("observationReliable", reliable);
        DMNResult result = runtime.evaluateAll(model, ctx);
        if (result.hasErrors()) throw new AssertionError(label + ": " + result.getMessages());
        Object value = result.getDecisionResultByName("locationDifference").getResult();
        if (!(value instanceof List<?> list)) throw new AssertionError("Expected COLLECT list: " + value);
        if (list.size() != (shouldMatch ? 1 : 0)) throw new AssertionError(label + ": " + value);
        if (shouldMatch) {
            Map<?, ?> out = (Map<?, ?>) list.get(0);
            if (!"LOCATION_DIFFERENCE".equals(out.get("causeCode")) || !"SUPPORTED".equals(out.get("epistemicStatus")))
                throw new AssertionError("Unexpected output: " + out);
        }
        checks++;
        System.out.println("PASS " + label);
    }
    public static void main(String[] args) {
        if (args.length != 1) throw new IllegalArgumentException("Argument: chemin R001-v1.dmn");
        Resource resource = ResourceFactory.newFileResource(new File(args[0]));
        DMNRuntime runtime = DMNRuntimeBuilder.fromDefaults().buildConfiguration()
                .fromResources(List.of(resource)).getOrElseThrow(RuntimeException::new);
        DMNModel model = runtime.getModel("urn:library-stock-gap:rules:R001:1", "R001-v1");
        if (model == null || model.hasErrors()) throw new AssertionError("Invalid model: " + (model == null ? "missing" : model.getMessages()));
        check(runtime, model, "different location, same site", "CENTRE", "CENTRE", "a", "b", true, true);
        check(runtime, model, "same location", "CENTRE", "CENTRE", "a", "a", true, false);
        check(runtime, model, "different site", "CENTRE", "NORD", "a", "b", true, false);
        check(runtime, model, "unreliable observation", "CENTRE", "CENTRE", "a", "b", false, false);
        check(runtime, model, "unknown observation quality", "CENTRE", "CENTRE", "a", "b", null, false);
        check(runtime, model, "missing observed site", "CENTRE", null, "a", "b", true, false);
        check(runtime, model, "missing observed location", "CENTRE", "CENTRE", "a", null, true, false);
        check(runtime, model, "missing expected location", "CENTRE", "CENTRE", null, "b", true, false);
        System.out.println("DMN_KIE_CHECKS=" + checks);
    }
}
