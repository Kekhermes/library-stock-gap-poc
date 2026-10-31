import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;

/** Simulation hors ligne ; aucune décision diagnostique n'est exécutée. */
public final class GenerateDataset {
    static final String VERSION = "2.0.0";
    static final Instant REFERENCE = Instant.parse("2026-09-30T12:00:00Z");
    final long seed;
    final String profile;
    final Map<String, Object> runtime = obj();
    GenerateDataset(long seed, String profile) {
        if (!List.of("demo", "validation", "holdout").contains(profile)) throw new IllegalArgumentException("Profil inconnu : " + profile);
        this.seed = seed;
        this.profile = profile;
        runtime.put("schemaVersion", "1.0.0");
        runtime.put("dataset", obj("datasetId", id("dataset"), "profile", profile,
            "seed", seed, "referenceTime", REFERENCE.toString(), "capturedAt", REFERENCE.toString()));
        runtime.put("initialRuleSet", obj("version", 1, "rules", List.of(obj(
            "ruleId", "R001", "ruleVersion", 1, "dmnPath", "rules/R001-v1.dmn"))));
    }
    String id(String key) {
        return UUID.nameUUIDFromBytes((VERSION + "/" + profile + "/" + seed + "/" + key)
            .getBytes(StandardCharsets.UTF_8)).toString();
    }
    static final String[] SITES = {"CENTRE", "NORD", "SUD"};
    static final String[] KINDS = {"SHELF", "RESERVE", "RETURNS", "RECEIVING", "REPAIR"};
    final Map<String, List<Map<String, Object>>> tables = new LinkedHashMap<>();
    final Random random = new Random();
    int population() { return profile.equals("demo") ? 10 : profile.equals("validation") ? 180 : 240; }
    int perFamily() { return profile.equals("validation") ? 9 : 12; }
    int caseCount() { return profile.equals("demo") ? 10 : perFamily() * 10; }
    int serial = 0;
    String nextId() { return id("entity/" + serial++); }
    String time(long secondsBefore) { return REFERENCE.minusSeconds(secondsBefore).toString(); }
    List<Map<String, Object>> table(String name) { return tables.get(name); }
    String location(String site, String kind) { return id("location/" + site + "/" + kind); }
    Map<String, Object> event(String copy, String type, long before, Map<String, Object> payload) {
        Map<String, Object> event = obj("sourceEventId", nextId(), "copyId", copy, "eventType", type,
            "occurredAt", time(before), "recordedAt", time(before - 10), "payload", payload);
        table("sourceEvents").add(event);
        return event;
    }
    Map<String, Object> project(String copy, Map<String, Object> event, String outcome,
            String status, String site, String location, long before) {
        Map<String, Object> payload = obj("originalEventId", event.get("sourceEventId"),
            "correlationId", payload(event).get("correlationId"), "outcome", outcome,
            "projectedStatus", status, "siteId", site, "locationId", location);
        Map<String, Object> applied = event(copy, "IntegrationProcessed", before, payload);
        if (outcome.equals("APPLIED")) {
            Map<String, Object> state = state(copy);
            state.put("status", status); state.put("siteId", site); state.put("locationId", location);
            state.put("sourceEventIds", List.of(event.get("sourceEventId"), applied.get("sourceEventId")));
        }
        return applied;
    }
    @SuppressWarnings("unchecked")
    static Map<String, Object> payload(Map<String, Object> event) { return (Map<String, Object>) event.get("payload"); }
    Map<String, Object> state(String copy) {
        return table("systemStates").stream().filter(s -> s.get("copyId").equals(copy)).findFirst().orElseThrow();
    }
    Map<String, Object> business(String copy, String type, long before, String site, String location, Object... extra) {
        Map<String, Object> data = obj("operationId", nextId(), "correlationId", nextId(),
            "siteId", site, "locationId", location);
        data.putAll(obj(extra));
        return event(copy, type, before, data);
    }
    Map<String, Object> scope(String location, String status) {
        Map<String, Object> value = obj("scopeId", nextId(), "locationId", location, "status", status,
            "startedAt", time(3600), "closedAt", status.equals("COMPLETE") ? time(60) : null);
        table("inventoryScopes").add(value);
        return value;
    }
    Map<String, Object> observe(String copy, String location, String quality, Map<String, Object> scope) {
        Map<String, Object> value = obj("observationId", nextId(), "copyId", copy, "locationId", location,
            "scopeId", scope.get("scopeId"), "observedAt", time(300), "quality", quality);
        table("observations").add(value);
        return value;
    }
    void populate() {
        random.setSeed(seed ^ profile.hashCode() ^ VERSION.hashCode());
        for (String name : List.of("sites", "locations", "titles", "copies", "systemStates", "inventoryScopes",
                "observations", "sourceEvents", "sourceAvailability", "operatorNotes", "cases")) {
            List<Map<String, Object>> rows = new ArrayList<>(); tables.put(name, rows); runtime.put(name, rows);
        }
        for (String site : SITES) {
            table("sites").add(obj("siteId", site, "name", "Les Trois Rives — " + site));
            for (String kind : KINDS) {
                table("locations").add(obj("locationId", location(site, kind), "siteId", site, "kind", kind,
                    "label", site + " / " + kind));
                scope(location(site, kind), "COMPLETE");
            }
        }
        int titleCount = profile.equals("demo") ? 10 : 300;
        for (int i = 0; i < titleCount; i++) {
            String base = String.format(Locale.ROOT, "978290%06d", i);
            int sum = 0; for (int d = 0; d < 12; d++) sum += (base.charAt(d) - '0') * (d % 2 == 0 ? 1 : 3);
            table("titles").add(obj("titleId", id("title/" + i), "isbn", base + ((10 - sum % 10) % 10),
                "title", new String[]{"Chroniques", "Horizons", "Atlas", "Carnets", "Voyages"}[i % 5] + " des Trois Rives — " + (i + 1)));
        }
        List<Integer> order = new ArrayList<>(); for (int i = 0; i < population(); i++) order.add(i);
        Collections.shuffle(order, random);
        Set<Integer> cases = new HashSet<>(order.subList(0, caseCount()));
        for (int i = 0; i < population(); i++) {
            String copy = id("copy/" + i), site = SITES[random.nextInt(3)], shelf = location(site, "SHELF");
            table("copies").add(obj("copyId", copy, "barcode", "TR" + id("barcode/" + i).replace("-", "").substring(0, 16),
                "titleId", id("title/" + random.nextInt(titleCount)), "owningSiteId", site));
            table("systemStates").add(obj("copyId", copy, "status", "NOT_REGISTERED", "siteId", site,
                "locationId", null, "sourceEventIds", List.of()));
            long jitter = random.nextInt(86400);
            Map<String, Object> received = business(copy, "ReceiptReceived", 120L * 86400 + jitter, site, location(site, "RECEIVING"), "receiptId", nextId());
            project(copy, received, "APPLIED", "ON_SHELF", site, shelf, 119L * 86400 + jitter);
            if (!profile.equals("demo")) {
                String loan = nextId();
                Map<String, Object> opened = business(copy, "LoanOpened", 60L * 86400 + jitter, site, null, "loanId", loan);
                project(copy, opened, "APPLIED", "ON_LOAN", site, null, 60L * 86400 + jitter - 60);
                business(copy, "LoanClosed", 40L * 86400 + jitter, site, location(site, "RETURNS"), "loanId", loan);
                Map<String, Object> returned = business(copy, "ReturnReceived", 40L * 86400 + jitter - 30, site, location(site, "RETURNS"), "loanId", loan);
                project(copy, returned, "APPLIED", "ON_SHELF", site, shelf, 39L * 86400 + jitter);
            }
            if (!profile.equals("demo") && i % 4 == 0) {
                String destination = SITES[(Arrays.asList(SITES).indexOf(site) + 1) % 3], transfer = nextId();
                Map<String, Object> sent = business(copy, "TransferDispatched", 30L * 86400 + jitter, site, null,
                    "transferId", transfer, "fromSiteId", site, "toSiteId", destination);
                project(copy, sent, "APPLIED", "IN_TRANSIT", site, null, 30L * 86400 + jitter - 60);
                Map<String, Object> arrived = business(copy, "TransferReceived", 29L * 86400 + jitter, destination, location(destination, "RECEIVING"),
                    "transferId", transfer, "fromSiteId", site, "toSiteId", destination);
                site = destination; shelf = location(site, "SHELF");
                project(copy, arrived, "APPLIED", "ON_SHELF", site, shelf, 28L * 86400 + jitter);
            }
            if (!cases.contains(i)) {
                int mode = random.nextInt(100);
                if (mode >= 60 && mode < 80) {
                    Map<String, Object> e = business(copy, "LoanOpened", 5L * 86400 + jitter, site, null, "loanId", nextId());
                    project(copy, e, "APPLIED", "ON_LOAN", site, null, 5L * 86400 + jitter - 60);
                } else if (mode >= 80 && mode < 88) {
                    String target = SITES[(Arrays.asList(SITES).indexOf(site) + 1) % 3];
                    Map<String, Object> e = business(copy, "TransferDispatched", 7200 + jitter, site, null,
                        "transferId", nextId(), "fromSiteId", site, "toSiteId", target);
                    project(copy, e, "APPLIED", "IN_TRANSIT", site, null, 7140 + jitter);
                } else if (mode >= 88 && mode < 94) {
                    String recentLoan = nextId();
                    Map<String, Object> active = business(copy, "LoanOpened", 8L * 86400 + jitter, site, null, "loanId", recentLoan);
                    project(copy, active, "APPLIED", "ON_LOAN", site, null, 8L * 86400 + jitter - 60);
                    business(copy, "LoanClosed", 7230 + jitter, site, location(site, "RETURNS"), "loanId", recentLoan);
                    Map<String, Object> e = business(copy, "ReturnReceived", 7200 + jitter, site, location(site, "RETURNS"), "loanId", recentLoan);
                    project(copy, e, "APPLIED", "RECEIVING", site, location(site, "RETURNS"), 7140 + jitter);
                } else if (mode >= 94) {
                    Map<String, Object> e = business(copy, "RepairStarted", 5L * 86400 + jitter, site, location(site, "REPAIR"), "repairId", nextId());
                    project(copy, e, "APPLIED", "IN_REPAIR", site, location(site, "REPAIR"), 5L * 86400 + jitter - 60);
                }
            } else {
                table("cases").add(obj("caseId", id("case/" + i), "copyId", copy,
                    "inventoryReferenceTime", REFERENCE.toString(), "status", "A_ANALYSER",
                    "symptoms", List.of(obj("symptomId", nextId(), "kind", "COUNT_UNCERTAIN", "expectedValue", "1", "observedValue", null))));
            }
            Map<String, Object> current = state(copy);
            if (current.get("locationId") != null) {
                String currentLocation = (String) current.get("locationId");
                Map<String, Object> scan = table("inventoryScopes").stream().filter(s -> currentLocation.equals(s.get("locationId"))).findFirst().orElseThrow();
                observe(copy, currentLocation, "VALID", scan);
            }
            for (String source : List.of("LOANS", "RETURNS", "RECEIPTS", "TRANSFERS", "REPAIRS", "INTEGRATIONS", "INVENTORY"))
                table("sourceAvailability").add(obj("copyId", copy, "source", source, "status", "COMPLETE",
                    "completeFrom", time(180L * 86400), "completeTo", REFERENCE.toString()));
        }
    }
    final List<Map<String, Object>> oracleCases = new ArrayList<>();
    static final String[] FAMILIES = {"LOCALISATION", "PRET_NON_PROJETE", "RETOUR_NON_PROJETE", "TRANSFERT_INCOHERENT",
        "RECEPTION_NON_INTEGREE", "REPARATION_NON_PROJETEE", "INVENTAIRE_INCOMPLET", "RETARD_INTEGRATION", "ABSENCE_INEXPLIQUEE", "DEUX_CAUSES"};
    void symptoms(Map<String, Object> c, String kind, String expected, String observed) {
        c.put("symptoms", List.of(obj("symptomId", nextId(), "kind", kind, "expectedValue", expected, "observedValue", observed)));
    }
    void availability(String copy, String source, String status) {
        for (Map<String, Object> row : table("sourceAvailability")) if (copy.equals(row.get("copyId")) && source.equals(row.get("source"))) {
            row.put("status", status);
            row.put("completeFrom", status.equals("UNAVAILABLE") ? null : time(180L * 86400));
            row.put("completeTo", status.equals("UNAVAILABLE") ? null : time(7L * 86400));
        }
    }
    Map<String, Object> scan(String copy, String location, String quality, String scopeStatus) {
        return observe(copy, location, quality, scope(location, scopeStatus));
    }
    void simulate() {
        Collections.shuffle(table("cases"), random);
        for (int index = 0; index < caseCount(); index++) {
            Map<String, Object> c = table("cases").get(index);
            int family = profile.equals("demo") ? (index < 2 ? 0 : index < 6 ? 2 : 5) : index / perFamily();
            if (family == 6 || family == 8) { uncertaintyCase(c, family, index % perFamily()); continue; }
            if (family == 9) { compositeCase(c, index % perFamily()); continue; }
            if (family != 0) {
                projectionCase(c, family, index % perFamily());
                continue;
            }
            String copy = (String) c.get("copyId");
            Map<String, Object> state = state(copy);
            String site = (String) state.get("siteId"), expected = (String) state.get("locationId");
            String alternative = location(site, "RESERVE");
            String variant = variant(index);
            table("observations").removeIf(o -> copy.equals(o.get("copyId")));
            Map<String, Object> observation = scan(copy, variant.equals("NEAR_NEGATIVE") ? expected : alternative,
                variant.equals("INCOMPLETE") ? "AMBIGUOUS" : "VALID", "COMPLETE");
            symptoms(c, variant.equals("POSITIVE") ? "LOCATION_MISMATCH" : "COUNT_UNCERTAIN", expected,
                variant.equals("POSITIVE") ? alternative : null);
            Map<String, Object> truth = obj("mechanism", variant.equals("NEAR_NEGATIVE") ? "Aucun déplacement ; signal de collecte non confirmé." : "Déplacement physique sans modification de l'affectation ; aucune attribution à un opérateur.",
                "activePerturbations", variant.equals("NEAR_NEGATIVE") ? List.of() : List.of("LOCATION_CHANGE_UNPROJECTED"),
                "physicalStatus", "ON_SHELF", "physicalSiteId", site,
                "physicalLocationId", variant.equals("NEAR_NEGATIVE") ? expected : alternative);
            Map<String, Object> admissible = obj("conclusion", variant.equals("INCOMPLETE") ? "INDETERMINATE" : "SUPPORTED",
                "causeCodes", variant.equals("POSITIVE") ? List.of("LOCATION_MISMATCH") : List.of(),
                "explanation", variant.equals("POSITIVE") ? "Observation unique valide à un autre emplacement du même site ; écart établi, origine humaine non prouvée."
                    : variant.equals("NEAR_NEGATIVE") ? "Le scan valide confirme l'emplacement attendu ; aucun écart de localisation étayé."
                    : "Identité du scan ambiguë ; ne pas attribuer un emplacement physique certain.",
                "forbiddenClaims", List.of("Vol", "Perte définitive", "Responsabilité d'un opérateur"),
                "recommendedChecks", variant.equals("INCOMPLETE") ? List.of("Refaire le scan et vérifier le code-barres") : List.of());
            oracleCases.add(obj("caseId", c.get("caseId"), "copyId", copy, "family", "LOCALISATION", "variant", variant,
                "symptoms", c.get("symptoms"), "injectedTruth", truth, "admissible", admissible,
                "evidenceRefs", List.of(obj("kind", "observation", "id", observation.get("observationId")), obj("kind", "systemState", "id", copy))));
        }
    }
    String variant(int index) {
        return profile.equals("demo") ? "POSITIVE"
            : new String[]{"POSITIVE", "NEAR_NEGATIVE", "INCOMPLETE"}[index % 3];
    }
    void projectionCase(Map<String, Object> c, int family, int ordinal) {
        String copy = (String) c.get("copyId"), variant = variant(ordinal);
        boolean negative = variant.equals("NEAR_NEGATIVE"), incomplete = variant.equals("INCOMPLETE");
        Map<String, Object> state = state(copy);
        String site = (String) state.get("siteId"), loc = null, targetStatus = "ON_LOAN";
        table("observations").removeIf(o -> copy.equals(o.get("copyId")));
        long age = family == 7 ? (profile.equals("holdout") ? 600 : 360) + random.nextInt(240)
            : (profile.equals("holdout") ? 5L : profile.equals("validation") ? 3L : 2L) * 86400 + random.nextInt(21600);
        Map<String, Object> target;
        String cause;
        if (family == 1 || family == 7) {
            target = business(copy, "LoanOpened", age, site, null, "loanId", nextId());
            cause = family == 7 ? "INTEGRATION_PENDING_WITHIN_SLA" : "LOAN_NOT_PROJECTED";
        } else if (family == 2) {
            String loan = nextId();
            Map<String, Object> opened = business(copy, "LoanOpened", 15L * 86400, site, null, "loanId", loan);
            project(copy, opened, "APPLIED", "ON_LOAN", site, null, 15L * 86400 - 60);
            loc = location(site, "RETURNS"); targetStatus = "RECEIVING";
            business(copy, "LoanClosed", age + (profile.equals("holdout") ? -20 : 30), site, loc, "loanId", loan);
            target = business(copy, "ReturnReceived", age, site, loc, "loanId", loan);
            cause = "RETURN_NOT_PROJECTED";
        } else if (family == 3) {
            String destination = SITES[(Arrays.asList(SITES).indexOf(site) + 1) % 3], transfer = nextId();
            Map<String, Object> sent = business(copy, "TransferDispatched", age + 86400, site, null,
                "transferId", transfer, "fromSiteId", site, "toSiteId", destination);
            project(copy, sent, "APPLIED", "IN_TRANSIT", site, null, age + 86340);
            loc = location(destination, "RECEIVING"); targetStatus = "RECEIVING";
            target = business(copy, "TransferReceived", age, destination, loc,
                "transferId", transfer, "fromSiteId", site, "toSiteId", destination);
            site = destination; cause = "TRANSFER_RECEIPT_NOT_PROJECTED";
        } else if (family == 4) {
            table("sourceEvents").removeIf(e -> copy.equals(e.get("copyId")));
            state.put("status", "NOT_REGISTERED"); state.put("locationId", null); state.put("sourceEventIds", List.of());
            loc = location(site, "RECEIVING"); targetStatus = "RECEIVING";
            target = business(copy, "ReceiptReceived", age, site, loc, "receiptId", nextId());
            cause = "RECEIPT_NOT_PROJECTED";
        } else {
            loc = location(site, "REPAIR"); targetStatus = "IN_REPAIR";
            target = business(copy, "RepairStarted", age, site, loc, "repairId", nextId());
            cause = "REPAIR_NOT_PROJECTED";
        }
        String outcome = family == 7 ? "QUEUED" : "FAILED";
        Map<String, Object> processing = project(copy, target, outcome, targetStatus, site, loc, age - 30);
        payload(processing).put("maxProcessingDelaySeconds", 900);
        payload(processing).put("attempt", 1);
        if (!outcome.equals("QUEUED")) payload(processing).put("errorCode", "PROJECTION_WRITE_UNAVAILABLE");
        if (negative) project(copy, target, "APPLIED", targetStatus, site, loc, age - 90);
        if (incomplete) {
            table("sourceEvents").remove(processing);
            availability(copy, "INTEGRATIONS", "PARTIAL");
        }
        if (loc != null) scan(copy, loc, incomplete ? "AMBIGUOUS" : "VALID", "COMPLETE");
        if (negative) {
            symptoms(c, "COUNT_UNCERTAIN", "1", null);
            // Une lecture rejetée explique le signal brut sans inventer un écart encore actif.
            scan(copy, location(site, "RESERVE"), "REJECTED", "INCOMPLETE");
        } else symptoms(c, family == 1 || family == 7 ? "MISSING" : family == 4 ? "UNEXPECTED_PRESENCE" : "STATUS_MISMATCH",
            (String) state.get("status"), targetStatus);
        Map<String, Object> truth = obj("activePerturbations", negative ? List.of() : List.of(family == 7 ? "QUEUE_PENDING" : "PROJECTION_WRITE_FAILED"),
            "hiddenSourceEvents", incomplete ? List.of(processing) : List.of(),
            "mechanism", negative ? "Traitement repris et appliqué avant référence ; défaut devenu inactif." : family == 7 ? "Événement métier en attente normale dans la fenêtre contractuelle de 900 secondes."
            : "L'opération métier aboutit ; l'écriture de projection échoue, indépendamment du déplacement physique.",
            "businessEventId", target.get("sourceEventId"), "physicalStatus", targetStatus, "physicalSiteId", site,
            "physicalLocationId", loc, "projectionPerturbation", negative ? "Échec transitoire puis reprise réussie avant référence" : outcome,
            "evidenceMask", incomplete ? "Journal d'intégration récent absent ; scan ambigu" : "Aucun");
        Map<String, Object> admissible = obj("conclusion", incomplete ? "PARTIAL" : "SUPPORTED",
            "causeCodes", negative || incomplete ? List.of() : List.of(cause),
            "explanation", incomplete ? "L'événement métier et l'état projeté divergent ; le journal incomplet interdit d'affirmer un échec d'intégration ou sa durée."
                : negative ? "La dernière application corrélée a réussi avant référence ; le défaut de projection n'est plus actif."
                : family == 7 ? "Prêt actif et traitement en file depuis moins de 900 secondes ; transition attendue, pas de panne persistante démontrée."
                : "Événement métier confirmé et écriture corrélée échouée ; l'état conserve la projection antérieure.",
            "forbiddenClaims", List.of("Vol", "Perte définitive", "Défaillance persistante sans preuve"),
            "recommendedChecks", incomplete ? List.of("Récupérer le journal d'intégration récent", "Confirmer le scan") : family == 7 ? List.of("Recontrôler après la fenêtre de traitement") : List.of());
        oracleCases.add(obj("caseId", c.get("caseId"), "copyId", copy, "family", FAMILIES[family], "variant", variant,
            "symptoms", c.get("symptoms"), "injectedTruth", truth, "admissible", admissible, "evidenceRefs", evidence(copy)));
    }
    List<Map<String, Object>> evidence(String copy) {
        List<Map<String, Object>> refs = new ArrayList<>();
        refs.add(obj("kind", "systemState", "id", copy));
        for (Map<String, Object> event : table("sourceEvents")) if (copy.equals(event.get("copyId")))
            refs.add(obj("kind", "sourceEvent", "id", event.get("sourceEventId")));
        for (Map<String, Object> observation : table("observations")) if (copy.equals(observation.get("copyId"))) {
            refs.add(obj("kind", "observation", "id", observation.get("observationId")));
            refs.add(obj("kind", "inventoryScope", "id", observation.get("scopeId")));
        }
        for (Map<String, Object> availability : table("sourceAvailability")) if (copy.equals(availability.get("copyId")))
            refs.add(obj("kind", "sourceAvailability", "id", copy + "/" + availability.get("source")));
        for (Map<String, Object> note : table("operatorNotes")) if (copy.equals(note.get("copyId")))
            refs.add(obj("kind", "operatorNote", "id", note.get("noteId")));
        Object expectedLocation = state(copy).get("locationId");
        if (expectedLocation != null) for (Map<String, Object> scope : table("inventoryScopes"))
            if (expectedLocation.equals(scope.get("locationId"))) refs.add(obj("kind", "inventoryScope", "id", scope.get("scopeId")));
        return new ArrayList<>(new LinkedHashSet<>(refs));
    }
    void uncertaintyCase(Map<String, Object> c, int family, int ordinal) {
        String copy = (String) c.get("copyId"), variant = variant(ordinal);
        boolean negative = variant.equals("NEAR_NEGATIVE"), incomplete = variant.equals("INCOMPLETE");
        Map<String, Object> state = state(copy);
        String loc = (String) state.get("locationId");
        table("observations").removeIf(o -> copy.equals(o.get("copyId")));
        Map<String, Object> session = scope(loc, negative || (family == 8 && !incomplete) ? "COMPLETE" : incomplete ? "FAILED" : "INCOMPLETE");
        if (negative) observe(copy, loc, "VALID", session);
        else if (family == 6 || incomplete) {
            availability(copy, "INVENTORY", incomplete ? "UNAVAILABLE" : "PARTIAL");
            session.put("startedAt", time(120));
        }
        if (family == 8 && !negative) {
            availability(copy, "LOANS", "PARTIAL"); availability(copy, "TRANSFERS", "PARTIAL");
        }
        symptoms(c, family == 8 && !incomplete && !negative ? "MISSING" : "COUNT_UNCERTAIN", "1", negative ? "1" : null);
        table("operatorNotes").add(obj("noteId", nextId(), "copyId", copy, "text",
            negative ? "Nouvelle lecture du code-barres effectuée dans le rayon affecté."
                : family == 6 ? "Le terminal de collecte a cessé de répondre pendant le passage ; aucune conclusion sur les exemplaires non lus."
                : "Le passage au rayon n'a pas permis de retrouver cet exemplaire ; contrôle complémentaire demandé.", "recordedAt", time(30)));
        Map<String, Object> truth = obj("activePerturbations", negative ? List.of() : List.of(family == 6 ? "COLLECTION_INTERRUPTED" : "UNOBSERVED_MOVE"),
            "mechanism", negative ? "Exemplaire resté au rayon, lecture de contrôle réussie." : family == 6 ? "Interruption de collecte ; exemplaire resté au rayon."
                : "Exemplaire déplacé hors du périmètre instrumenté ; la destination n'est pas exposée et aucune perte n'est simulée.",
            "physicalStatus", "ON_SHELF", "physicalSiteId", state.get("siteId"), "physicalLocationId", family == 6 || negative ? loc : null,
            "evidenceMask", incomplete ? "Collecte indisponible" : "Aucun");
        Map<String, Object> admissible = obj("conclusion", negative || (family == 6 && !incomplete) ? "SUPPORTED" : "INDETERMINATE",
            "causeCodes", family == 6 && !negative && !incomplete ? List.of("INVENTORY_INCOMPLETE") : List.of(),
            "explanation", negative ? "Le scan valide au rayon attendu réfute l'absence courante."
                : family == 6 && !incomplete ? "La session interrompue explique l'incertitude de comptage ; la présence physique reste à vérifier."
                : "L'absence de lecture ne démontre ni la destination, ni une perte, ni un vol ; données insuffisantes pour attribuer une cause.",
            "forbiddenClaims", List.of("Vol", "Perte définitive", "Absence certaine déduite d'une collecte incomplète"),
            "recommendedChecks", negative ? List.of() : List.of("Réaliser un inventaire contradictoire", "Compléter les journaux métier"));
        List<Map<String, Object>> refs = evidence(copy);
        refs.add(obj("kind", "inventoryScope", "id", session.get("scopeId")));
        oracleCases.add(obj("caseId", c.get("caseId"), "copyId", copy, "family", FAMILIES[family], "variant", variant,
            "symptoms", c.get("symptoms"), "injectedTruth", truth, "admissible", admissible, "evidenceRefs", refs));
    }
    void compositeCase(Map<String, Object> c, int ordinal) {
        String variant = variant(ordinal), copy = (String) c.get("copyId");
        // La réparation est documentée même lorsque seule la seconde cause manque de preuve.
        projectionCase(c, 5, variant.equals("INCOMPLETE") ? 0 : ordinal);
        Map<String, Object> oracle = oracleCases.get(oracleCases.size() - 1);
        String site = (String) state(copy).get("siteId"), reserve = location(site, "RESERVE"), workshop = location(site, "REPAIR");
        table("observations").removeIf(o -> copy.equals(o.get("copyId")));
        scan(copy, reserve, variant.equals("INCOMPLETE") ? "AMBIGUOUS" : "VALID", "COMPLETE");
        c.put("symptoms", List.of(
            obj("symptomId", nextId(), "kind", "STATUS_MISMATCH", "expectedValue", "IN_REPAIR", "observedValue", state(copy).get("status")),
            obj("symptomId", nextId(), "kind", "LOCATION_MISMATCH", "expectedValue", workshop, "observedValue", reserve)));
        oracle.put("family", "DEUX_CAUSES"); oracle.put("variant", variant); oracle.put("symptoms", c.get("symptoms"));
        Map<String, Object> truth = cast(oracle.get("injectedTruth"));
        truth.put("physicalLocationId", reserve);
        truth.put("activePerturbations", variant.equals("NEAR_NEGATIVE") ? List.of("REPAIR_LOCATION_CHANGED") : List.of("PROJECTION_WRITE_FAILED", "REPAIR_LOCATION_CHANGED"));
        truth.put("mechanism", "Réparation engagée puis déplacement distinct de l'atelier vers la réserve sans mise à jour de l'affectation de réparation.");
        truth.put("evidenceMask", variant.equals("INCOMPLETE") ? "Scan de réserve ambigu" : "Aucun");
        oracle.put("admissible", obj("conclusion", variant.equals("INCOMPLETE") ? "PARTIAL" : "SUPPORTED",
            "causeCodes", variant.equals("POSITIVE") ? List.of("REPAIR_NOT_PROJECTED", "REPAIR_LOCATION_MISMATCH")
                : variant.equals("INCOMPLETE") ? List.of("REPAIR_NOT_PROJECTED") : List.of("REPAIR_LOCATION_MISMATCH"),
            "explanation", variant.equals("POSITIVE") ? "Deux écarts compatibles : réparation non projetée et scan valide hors de l'atelier indiqué par l'ordre de réparation."
                : variant.equals("INCOMPLETE") ? "Échec de projection de réparation documenté ; seconde localisation non confirmée par un scan valide."
                : "Réparation correctement projetée après reprise ; seul le déplacement hors atelier reste étayé.",
            "forbiddenClaims", List.of("Deux causes confirmées par un scan ambigu", "Vol"),
            "recommendedChecks", variant.equals("INCOMPLETE") ? List.of("Refaire le scan en réserve") : List.of()));
        oracle.put("evidenceRefs", evidence(copy));
    }
    @SuppressWarnings("unchecked")
    static Map<String, Object> cast(Object value) { return (Map<String, Object>) value; }
    static String eventLabel(Object eventType) {
        return switch (String.valueOf(eventType)) {
            case "LoanOpened" -> "Prêt enregistré (`LoanOpened`)";
            case "LoanClosed" -> "Prêt clôturé (`LoanClosed`)";
            case "ReturnReceived" -> "Retour reçu (`ReturnReceived`)";
            case "ReceiptReceived" -> "Réception enregistrée (`ReceiptReceived`)";
            case "TransferDispatched" -> "Transfert expédié (`TransferDispatched`)";
            case "TransferReceived" -> "Transfert reçu (`TransferReceived`)";
            case "RepairStarted" -> "Réparation engagée (`RepairStarted`)";
            case "IntegrationProcessed" -> "Mise à jour du stock informatique (`IntegrationProcessed`)";
            default -> "`" + eventType + "`";
        };
    }
    String catalogue() {
        StringBuilder md = new StringBuilder("# Catalogue privé — " + profile + "\n\nGénéré depuis l'oracle de simulation v" + VERSION
            + ". Réservé aux tests et à l'expert ; ne pas exposer au worker IA.\n\n"
            + "Vocabulaire explicite : une opération métier concerne un exemplaire ; sa mise à jour du stock informatique est représentée par `IntegrationProcessed`. Un cas est un dossier d'écart.\n\n"
            + "Les extraits JSON restent fidèles aux données générées : leurs noms techniques et anciens libellés sont conservés. `projection` désigne l'état du stock informatique ; `intégration` désigne sa mise à jour, pas un échange Kafka. Voir le [glossaire validé](../../../../06-domaine-ddd-et-cycles-de-vie.md).\n\n"
            + "Référence : " + REFERENCE + "; seed : " + seed
            + ". Chaque attente est déclarée par le scénario, sans exécuter un moteur diagnostique.\n");
        for (Map<String, Object> c : oracleCases) {
            String copy = (String) c.get("copyId");
            md.append("\n## Cas ").append(c.get("caseId")).append(" — dossier d'écart\n\n")
                .append("- Exemplaire : `").append(copy).append("`\n")
                .append("- Famille privée : ").append(c.get("family")).append(" ; variante : ").append(c.get("variant")).append("\n")
                .append("- Symptômes : `").append(json(c.get("symptoms"))).append("`\n")
                .append("- Vérité injectée (non accessible au diagnostic) : `").append(json(c.get("injectedTruth"))).append("`\n")
                .append("- Diagnostic admissible attendu : `").append(json(c.get("admissible"))).append("`\n")
                .append("- État du stock informatique : `").append(json(state(copy))).append("`\n\n### Chronologie et preuves\n\n")
                .append("| Événement | ID | Occurrence UTC | Enregistrement UTC | Données corrélées |\n|---|---|---|---|---|\n");
            table("sourceEvents").stream().filter(e -> copy.equals(e.get("copyId")))
                .sorted(Comparator.comparing(e -> (String) e.get("occurredAt"))).forEach(e -> md.append("| ")
                    .append(eventLabel(e.get("eventType"))).append(" | ").append(e.get("sourceEventId")).append(" | ")
                    .append(e.get("occurredAt")).append(" | ").append(e.get("recordedAt")).append(" | `")
                    .append(json(e.get("payload"))).append("` |\n"));
            for (String name : List.of("observations", "sourceAvailability", "operatorNotes")) {
                String label = switch (name) {
                    case "observations" -> "Observations d'inventaire";
                    case "sourceAvailability" -> "Disponibilité des sources";
                    default -> "Notes opérateur";
                };
                md.append("\n**").append(label).append(" (`").append(name).append("`)**\n");
                for (Map<String, Object> row : table(name)) if (copy.equals(row.get("copyId"))) md.append("- `").append(json(row)).append("`\n");
            }
            md.append("\n**Références de preuve résolubles**\n\n`").append(json(c.get("evidenceRefs"))).append("`\n");
            for (Map<String, Object> ref : refs(c)) if (ref.get("kind").equals("inventoryScope"))
                table("inventoryScopes").stream().filter(s -> s.get("scopeId").equals(ref.get("id")))
                    .forEach(s -> md.append("\n- Session : `").append(json(s)).append("`\n"));
        }
        return md.toString();
    }
    @SuppressWarnings("unchecked")
    static List<Map<String, Object>> refs(Map<String, Object> c) { return (List<Map<String, Object>>) c.get("evidenceRefs"); }
    static String sha256(Path file) throws Exception {
        return HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(file)));
    }
    void export(Path root) throws Exception {
        for (List<Map<String, Object>> rows : tables.values()) Collections.shuffle(rows, random);
        oracleCases.sort(Comparator.comparing(c -> (String) c.get("caseId")));
        String runtimePath = "seed/generated/" + profile + "/runtime.json";
        String privatePath = "data/private/" + profile + "/";
        write(root.resolve(runtimePath), runtime);
        write(root.resolve(privatePath + "oracle.json"), obj("generatorVersion", VERSION,
            "datasetId", id("dataset"), "cases", oracleCases));
        Files.writeString(root.resolve(privatePath + "catalogue.md"), catalogue(), StandardCharsets.UTF_8);
        Map<String, Object> counts = obj(); tables.forEach((name, rows) -> counts.put(name, rows.size()));
        write(root.resolve("seed/generated/" + profile + "/manifest.json"), obj(
            "generatorVersion", VERSION, "datasetId", id("dataset"), "profile", profile,
            "seed", seed, "referenceTime", REFERENCE.toString(), "counts", counts,
            "sha256", obj(runtimePath, sha256(root.resolve(runtimePath)))));
        Map<String, Object> families = obj(), variants = obj(), healthy = obj();
        for (Map<String, Object> c : oracleCases) {
            increment(families, (String) c.get("family")); increment(variants, (String) c.get("variant"));
        }
        Set<Object> subjects = new HashSet<>(); for (Map<String, Object> c : oracleCases) subjects.add(c.get("copyId"));
        for (Map<String, Object> state : table("systemStates")) if (!subjects.contains(state.get("copyId"))) increment(healthy, (String) state.get("status"));
        Map<String, Object> hashes = obj();
        for (String file : List.of(runtimePath, privatePath + "oracle.json", privatePath + "catalogue.md")) hashes.put(file, sha256(root.resolve(file)));
        write(root.resolve(privatePath + "manifest.json"), obj("generatorVersion", VERSION, "datasetId", id("dataset"),
            "profile", profile, "seed", seed, "referenceTime", REFERENCE.toString(), "counts", counts,
            "familyCounts", families, "variantCounts", variants, "normalStateDistribution", healthy, "sha256", hashes));
    }
    static void increment(Map<String, Object> counts, String key) { counts.put(key, ((Number) counts.getOrDefault(key, 0)).intValue() + 1); }
    static Map<String, Object> obj(Object... pairs) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (int i = 0; i < pairs.length; i += 2) result.put((String) pairs[i], pairs[i + 1]);
        return result;
    }
    static String json(Object value) {
        if (value == null) return "null";
        if (value instanceof String s) return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"")
            .replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t") + "\"";
        if (value instanceof Map<?, ?> m) {
            List<String> items = new ArrayList<>();
            m.forEach((k, v) -> items.add(json(k.toString()) + ":" + json(v)));
            return "{" + String.join(",", items) + "}";
        }
        if (value instanceof Iterable<?> list) {
            List<String> items = new ArrayList<>();
            for (Object item : list) items.add(json(item));
            return "[" + String.join(",", items) + "]";
        }
        return value.toString();
    }
    static void write(Path file, Object value) throws Exception {
        Files.createDirectories(file.getParent());
        Files.writeString(file, json(value) + "\n", StandardCharsets.UTF_8);
    }
    public static void main(String[] args) throws Exception {
        if (args.length != 3) throw new IllegalArgumentException("Usage : GenerateDataset <racine-conception> <seed> <demo|validation|holdout>");
        GenerateDataset generator = new GenerateDataset(Long.parseLong(args[1]), args[2]);
        generator.populate();
        generator.simulate();
        generator.export(Path.of(args[0]));
    }
}
