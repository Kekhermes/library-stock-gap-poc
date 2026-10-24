-- Schéma de référence PostgreSQL 18, pas une migration applicative.
-- Exécuter seulement dans une base vide dédiée. Aucun DROP, extension ou donnée oracle runtime.
-- Toutes les dates sont timestamptz ; configurer les connexions en UTC.
CREATE SCHEMA IF NOT EXISTS runtime;
CREATE SCHEMA IF NOT EXISTS evaluation;
REVOKE ALL ON SCHEMA runtime, evaluation FROM PUBLIC;
CREATE DOMAIN runtime.sha256 AS text CHECK (VALUE ~ '^[0-9a-f]{64}$');
CREATE DOMAIN runtime.rule_key AS text CHECK (VALUE = 'R001' OR VALUE ~ '^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$');

-- @table simulation | Importeur | dataset ; manifeste technique stable, sans scénario.
CREATE TABLE runtime.dataset (
 dataset_id uuid PRIMARY KEY, -- dataset.datasetId ; identité reproductible.
 schema_version text NOT NULL CHECK (schema_version = '1.0.0'), -- schemaVersion ; contrat du fichier.
 profile text NOT NULL, -- dataset.profile ; nom du profil, pas une cause.
 seed bigint NOT NULL, -- dataset.seed ; graine du générateur.
 reference_time timestamptz NOT NULL, -- dataset.referenceTime ; temps métier fixe.
 captured_at timestamptz NOT NULL, -- dataset.capturedAt ; borne de capture source.
 generator_version text NOT NULL, -- Manifeste externe ; version exacte du générateur.
 runtime_sha256 runtime.sha256 NOT NULL, -- Empreinte des octets runtime importés.
 CHECK (captured_at >= reference_time)
);
-- @table simulation | Importeur | Instance technique différente à chaque import/reset.
CREATE TABLE runtime.dataset_epoch (
 dataset_epoch uuid PRIMARY KEY, -- datasetEpoch ; UUID aléatoire créé à l'import, absent des fixtures.
 dataset_id uuid NOT NULL REFERENCES runtime.dataset(dataset_id), -- Jeu stable chargé.
 imported_at timestamptz NOT NULL, -- Temps réel d'import.
 sealed_at timestamptz, -- Fermeture irréversible de l'import ; null pendant chargement.
 CHECK (sealed_at IS NULL OR sealed_at >= imported_at)
);
-- @table simulation | Java/maintenance | Pointeur d'instance ; une seule ligne, absence = non prêt.
CREATE TABLE runtime.active_dataset (
 singleton boolean PRIMARY KEY DEFAULT true CHECK (singleton), -- Clé technique singleton.
 dataset_epoch uuid NOT NULL UNIQUE REFERENCES runtime.dataset_epoch(dataset_epoch), -- Époque autorisée pour les commandes.
 lock_version bigint NOT NULL DEFAULT 0 CHECK (lock_version >= 0) -- CAS du reset.
);
-- @table librarysource | Importeur | sites[].
CREATE TABLE runtime.site (
 dataset_epoch uuid NOT NULL REFERENCES runtime.dataset_epoch(dataset_epoch), -- Partition d'import.
 site_id text NOT NULL CHECK (site_id IN ('CENTRE','NORD','SUD')), -- siteId ; code métier, exception aux UUID.
 name text NOT NULL, -- name ; libellé public.
 PRIMARY KEY (dataset_epoch, site_id)
);
-- @table librarysource | Importeur | locations[].
CREATE TABLE runtime.location (
 dataset_epoch uuid NOT NULL, -- Partition d'import.
 location_id uuid NOT NULL, -- locationId.
 site_id text NOT NULL, -- siteId.
 kind text NOT NULL CHECK (kind IN ('SHELF','RESERVE','RETURNS','RECEIVING','REPAIR')), -- kind.
 label text NOT NULL, -- label.
 PRIMARY KEY (dataset_epoch, location_id),
 UNIQUE (dataset_epoch, location_id, site_id),
 FOREIGN KEY (dataset_epoch, site_id) REFERENCES runtime.site(dataset_epoch, site_id)
);
-- @table librarysource | Importeur | titles[].
CREATE TABLE runtime.title (
 dataset_epoch uuid NOT NULL REFERENCES runtime.dataset_epoch(dataset_epoch), -- Partition d'import.
 title_id uuid NOT NULL, -- titleId.
 isbn text NOT NULL, -- isbn ; chaîne pour conserver zéros et tirets, non unique volontairement.
 title text NOT NULL, -- title.
 PRIMARY KEY (dataset_epoch, title_id)
);
-- @table librarysource | Importeur | copies[].
CREATE TABLE runtime.copy (
 dataset_epoch uuid NOT NULL, -- Partition d'import.
 copy_id uuid NOT NULL, -- copyId.
 barcode text NOT NULL, -- barcode ; ne pas encoder la famille de simulation.
 title_id uuid NOT NULL, -- titleId.
 owning_site_id text NOT NULL, -- owningSiteId ; propriété distincte de localisation.
 PRIMARY KEY (dataset_epoch, copy_id),
 UNIQUE (dataset_epoch, barcode),
 FOREIGN KEY (dataset_epoch, title_id) REFERENCES runtime.title(dataset_epoch, title_id),
 FOREIGN KEY (dataset_epoch, owning_site_id) REFERENCES runtime.site(dataset_epoch, site_id)
);
-- @table librarysource | Importeur | sourceEvents[] ; aucune cause attendue.
CREATE TABLE runtime.source_event (
 dataset_epoch uuid NOT NULL, -- Partition d'import.
 source_event_id uuid NOT NULL, -- sourceEventId ; identité de preuve.
 copy_id uuid NOT NULL, -- copyId.
 event_type text NOT NULL CHECK (event_type IN ('LoanOpened','LoanClosed','ReturnReceived','ReceiptReceived','TransferDispatched','TransferReceived','RepairStarted','IntegrationProcessed')), -- eventType ; discriminant de payload.
 occurred_at timestamptz NOT NULL, -- occurredAt ; temps métier.
 recorded_at timestamptz NOT NULL, -- recordedAt ; visibilité source, distincte du transport.
 payload jsonb NOT NULL CHECK (jsonb_typeof(payload) = 'object'), -- payload ; validation discriminée par contrat côté importeur.
 PRIMARY KEY (dataset_epoch, source_event_id),
 UNIQUE (dataset_epoch, source_event_id, copy_id),
 FOREIGN KEY (dataset_epoch, copy_id) REFERENCES runtime.copy(dataset_epoch, copy_id),
 CHECK (recorded_at >= occurred_at),
 CHECK (payload ?& ARRAY['correlationId','siteId','locationId']),
 CHECK (CASE event_type
  WHEN 'LoanOpened' THEN payload ?& ARRAY['operationId','loanId']
  WHEN 'LoanClosed' THEN payload ?& ARRAY['operationId','loanId']
  WHEN 'ReturnReceived' THEN payload ?& ARRAY['operationId','loanId']
  WHEN 'ReceiptReceived' THEN payload ?& ARRAY['operationId','receiptId']
  WHEN 'TransferDispatched' THEN payload ?& ARRAY['operationId','transferId','fromSiteId','toSiteId']
  WHEN 'TransferReceived' THEN payload ?& ARRAY['operationId','transferId','fromSiteId','toSiteId']
  WHEN 'RepairStarted' THEN payload ?& ARRAY['operationId','repairId']
  WHEN 'IntegrationProcessed' THEN payload ?& ARRAY['originalEventId','outcome','projectedStatus']
  ELSE false END)
);
CREATE INDEX source_event_timeline_idx ON runtime.source_event(dataset_epoch, copy_id, occurred_at, source_event_id);
CREATE INDEX source_event_capture_idx ON runtime.source_event(dataset_epoch, recorded_at);
-- @table librarysource | Importeur | systemStates[] ; projection source, jamais corrigée par le POC.
CREATE TABLE runtime.system_state (
 dataset_epoch uuid NOT NULL, -- Partition d'import.
 copy_id uuid NOT NULL, -- copyId ; exactement une projection technique par exemplaire importé.
 status text NOT NULL CHECK (status IN ('ON_SHELF','ON_LOAN','IN_TRANSIT','RECEIVING','IN_REPAIR','WITHDRAWN','NOT_REGISTERED')), -- status ; NOT_REGISTERED n'affirme pas une inscription au stock.
 site_id text NOT NULL, -- siteId ; site de projection.
 location_id uuid, -- locationId ; null si inconnu/inapplicable.
 PRIMARY KEY (dataset_epoch, copy_id),
 FOREIGN KEY (dataset_epoch, copy_id) REFERENCES runtime.copy(dataset_epoch, copy_id),
 FOREIGN KEY (dataset_epoch, site_id) REFERENCES runtime.site(dataset_epoch, site_id),
 FOREIGN KEY (dataset_epoch, location_id, site_id) REFERENCES runtime.location(dataset_epoch, location_id, site_id)
);
-- @table librarysource | Importeur | systemStates[].sourceEventIds[] dénormalisé en association ordonnée.
CREATE TABLE runtime.system_state_event (
 dataset_epoch uuid NOT NULL, -- Partition d'import.
 copy_id uuid NOT NULL, -- Exemplaire propriétaire de la projection.
 position integer NOT NULL CHECK (position >= 0), -- Position dans sourceEventIds.
 source_event_id uuid NOT NULL, -- sourceEventIds[position].
 PRIMARY KEY (dataset_epoch, copy_id, source_event_id),
 UNIQUE (dataset_epoch, copy_id, position),
 FOREIGN KEY (dataset_epoch, copy_id) REFERENCES runtime.system_state(dataset_epoch, copy_id),
 FOREIGN KEY (dataset_epoch, source_event_id, copy_id) REFERENCES runtime.source_event(dataset_epoch, source_event_id, copy_id)
);
-- @table librarysource | Importeur | inventoryScopes[] ; une campagne implicite par époque.
CREATE TABLE runtime.inventory_scope (
 dataset_epoch uuid NOT NULL, -- Partition d'import.
 scope_id uuid NOT NULL, -- scopeId.
 location_id uuid NOT NULL, -- locationId.
 status text NOT NULL CHECK (status IN ('COMPLETE','INCOMPLETE','FAILED')), -- status ; couverture et non conclusion.
 started_at timestamptz NOT NULL, -- startedAt.
 closed_at timestamptz, -- closedAt ; nullable même pour une collecte incomplète.
 PRIMARY KEY (dataset_epoch, scope_id),
 UNIQUE (dataset_epoch, scope_id, location_id),
 FOREIGN KEY (dataset_epoch, location_id) REFERENCES runtime.location(dataset_epoch, location_id),
 CHECK (closed_at IS NULL OR closed_at >= started_at),
 CHECK (status <> 'COMPLETE' OR closed_at IS NOT NULL)
);
-- @table librarysource | Importeur | observations[].
CREATE TABLE runtime.observation (
 dataset_epoch uuid NOT NULL, -- Partition d'import.
 observation_id uuid NOT NULL, -- observationId.
 copy_id uuid NOT NULL, -- copyId.
 location_id uuid NOT NULL, -- locationId.
 scope_id uuid NOT NULL, -- scopeId.
 observed_at timestamptz NOT NULL, -- observedAt ; temps métier.
 quality text NOT NULL CHECK (quality IN ('VALID','AMBIGUOUS','REJECTED')), -- quality ; ne pas convertir en vérité absolue.
 PRIMARY KEY (dataset_epoch, observation_id),
 FOREIGN KEY (dataset_epoch, copy_id) REFERENCES runtime.copy(dataset_epoch, copy_id),
 FOREIGN KEY (dataset_epoch, scope_id, location_id) REFERENCES runtime.inventory_scope(dataset_epoch, scope_id, location_id)
);
CREATE INDEX observation_copy_idx ON runtime.observation(dataset_epoch, copy_id, observed_at);
-- @table librarysource | Importeur | sourceAvailability[].
CREATE TABLE runtime.source_availability (
 dataset_epoch uuid NOT NULL, -- Partition d'import.
 copy_id uuid NOT NULL, -- copyId.
 source text NOT NULL CHECK (source IN ('LOANS','RETURNS','RECEIPTS','TRANSFERS','REPAIRS','INTEGRATIONS','INVENTORY')), -- source.
 status text NOT NULL CHECK (status IN ('COMPLETE','PARTIAL','UNAVAILABLE')), -- status.
 complete_from timestamptz, -- completeFrom ; null ne signifie pas début infini.
 complete_to timestamptz, -- completeTo ; null ne signifie pas fin infinie.
 PRIMARY KEY (dataset_epoch, copy_id, source),
 FOREIGN KEY (dataset_epoch, copy_id) REFERENCES runtime.copy(dataset_epoch, copy_id),
 CHECK ((complete_from IS NULL) = (complete_to IS NULL)),
 CHECK (complete_to IS NULL OR complete_to >= complete_from),
 CHECK (status <> 'COMPLETE' OR complete_from IS NOT NULL),
 CHECK (status <> 'UNAVAILABLE' OR complete_from IS NULL)
);
-- @table librarysource | Importeur | operatorNotes[] ; texte non fiable, pas une instruction.
CREATE TABLE runtime.operator_note (
 dataset_epoch uuid NOT NULL, -- Partition d'import.
 note_id uuid NOT NULL, -- noteId.
 copy_id uuid NOT NULL, -- copyId.
 text text NOT NULL, -- text ; preuve libre, exclue des conditions FEEL.
 recorded_at timestamptz NOT NULL, -- recordedAt.
 PRIMARY KEY (dataset_epoch, note_id),
 FOREIGN KEY (dataset_epoch, copy_id) REFERENCES runtime.copy(dataset_epoch, copy_id)
);
CREATE INDEX operator_note_copy_idx ON runtime.operator_note(dataset_epoch, copy_id, recorded_at);
-- @table librarysource | Importeur | cases[] ; identité et faits immuables séparés du workflow.
CREATE TABLE runtime.source_case (
 dataset_epoch uuid NOT NULL, -- Campagne implicite ; une campagne dans chaque fichier runtime.
 case_id uuid NOT NULL, -- caseId.
 copy_id uuid NOT NULL, -- copyId ; un cas ne porte jamais sur plusieurs exemplaires.
 inventory_reference_time timestamptz NOT NULL, -- inventoryReferenceTime ; conservé par cas.
 initial_status text NOT NULL CHECK (initial_status = 'A_ANALYSER'), -- cases[].status à l'import uniquement.
 PRIMARY KEY (dataset_epoch, case_id),
 UNIQUE (dataset_epoch, copy_id),
 FOREIGN KEY (dataset_epoch, copy_id) REFERENCES runtime.copy(dataset_epoch, copy_id)
);
-- @table librarysource | Importeur | cases[].symptoms[] ; plusieurs symptômes par cas.
CREATE TABLE runtime.gap_symptom (
 dataset_epoch uuid NOT NULL, -- Partition d'import.
 symptom_id uuid NOT NULL, -- symptomId.
 case_id uuid NOT NULL, -- Parent cases[].caseId.
 position integer NOT NULL CHECK (position >= 0), -- Ordre dans symptoms.
 kind text NOT NULL CHECK (kind IN ('MISSING','UNEXPECTED_PRESENCE','LOCATION_MISMATCH','STATUS_MISMATCH','COUNT_UNCERTAIN')), -- kind.
 expected_value text, -- expectedValue ; chaîne ou null, pas JSON arbitraire.
 observed_value text, -- observedValue ; chaîne ou null.
 PRIMARY KEY (dataset_epoch, symptom_id),
 UNIQUE (dataset_epoch, case_id, position),
 FOREIGN KEY (dataset_epoch, case_id) REFERENCES runtime.source_case(dataset_epoch, case_id)
);
-- @table analysis | Java | Agrégat cas ; pas de modification des faits sources.
CREATE TABLE runtime.gap_case (
 dataset_epoch uuid NOT NULL, -- Époque de travail.
 case_id uuid NOT NULL, -- Référence au dossier importé.
 status text NOT NULL CHECK (status IN ('A_ANALYSER','EN_ANALYSE','A_REVOIR','DIAGNOSTIC_ACCEPTE')), -- État métier courant.
 current_acceptance_id uuid, -- Acceptation courante ; l'historique reste append-only.
 lock_version bigint NOT NULL DEFAULT 0 CHECK (lock_version >= 0), -- Version optimiste des commandes.
 PRIMARY KEY (dataset_epoch, case_id),
 FOREIGN KEY (dataset_epoch, case_id) REFERENCES runtime.source_case(dataset_epoch, case_id)
);
-- @table analysis | Java | Snapshot construit intégralement puis inséré, jamais enrichi en place.
CREATE TABLE runtime.evidence_snapshot (
 dataset_epoch uuid NOT NULL, -- Époque source.
 snapshot_id uuid NOT NULL, -- evidenceSnapshotId / snapshotId.
 case_id uuid NOT NULL, -- Cas d'origine, y compris révision de règle sans nouveau cas.
 schema_version text NOT NULL, -- Version du contrat CaseFacts.
 inventory_reference_time timestamptz NOT NULL, -- Temps métier de décision copié du cas.
 data_captured_at timestamptz NOT NULL, -- Borne de visibilité recordedAt, non heure du serveur.
 created_at timestamptz NOT NULL, -- Temps réel de construction.
 facts jsonb NOT NULL CHECK (jsonb_typeof(facts) = 'object'), -- CaseFacts normalisé, sans notes ni oracle.
 evidence_bundle jsonb NOT NULL CHECK (jsonb_typeof(evidence_bundle) = 'object'), -- Preuves détaillées, notes incluses comme données.
 content_sha256 runtime.sha256 NOT NULL, -- Hash de la sérialisation canonique facts + evidenceBundle + références.
 PRIMARY KEY (dataset_epoch, snapshot_id),
 UNIQUE (dataset_epoch, snapshot_id, case_id),
 FOREIGN KEY (dataset_epoch, case_id) REFERENCES runtime.gap_case(dataset_epoch, case_id),
 CHECK (data_captured_at >= inventory_reference_time)
);
-- @table analysis | Java | Références figées ; résolution polymorphe contrôlée par Java.
CREATE TABLE runtime.evidence_reference (
 dataset_epoch uuid NOT NULL, -- Époque du snapshot.
 snapshot_id uuid NOT NULL, -- Snapshot propriétaire.
 reference_id uuid NOT NULL, -- Identité stable d'une référence de preuve.
 source_type text NOT NULL CHECK (source_type IN ('SYSTEM_STATE','SOURCE_EVENT','OBSERVATION','INVENTORY_SCOPE','SOURCE_AVAILABILITY','OPERATOR_NOTE','GAP_SYMPTOM')), -- Table source admissible.
 source_key jsonb NOT NULL CHECK (jsonb_typeof(source_key) = 'object'), -- PK source explicite ; composites représentées sans concaténation ambiguë.
 fact_path text NOT NULL, -- JSON Pointer dans le bundle/facts.
 content_sha256 runtime.sha256 NOT NULL, -- Empreinte du fragment capturé.
 PRIMARY KEY (dataset_epoch, snapshot_id, reference_id),
 FOREIGN KEY (dataset_epoch, snapshot_id) REFERENCES runtime.evidence_snapshot(dataset_epoch, snapshot_id)
);
-- @table analysis | Java | Exécution d'analyse ; publication figée au lancement.
CREATE TABLE runtime.analysis_run (
 dataset_epoch uuid NOT NULL, -- Époque active au lancement.
 analysis_id uuid NOT NULL, -- analysisId.
 case_id uuid NOT NULL, -- caseId.
 snapshot_id uuid NOT NULL, -- evidenceSnapshotId figé.
 rule_set_version integer NOT NULL CHECK (rule_set_version > 0), -- ruleSetVersion figée.
 status text NOT NULL CHECK (status IN ('QUEUED','DETERMINISTIC_RUNNING','AGENT_PENDING','AGENT_RUNNING','CANCEL_REQUESTED','COMPLETED','FAILED','CANCELLED')), -- Cycle de vie.
 fallback_reason jsonb, -- Motif structuré après évaluation déterministe ; null si pas de fallback.
 deterministic_trace jsonb, -- Résultats DMN, couverture, contradictions ; pas une chaîne de pensée.
 created_at timestamptz NOT NULL, -- Temps réel d'acceptation de commande.
 finished_at timestamptz, -- Temps terminal réel.
 lock_version bigint NOT NULL DEFAULT 0 CHECK (lock_version >= 0), -- CAS.
 PRIMARY KEY (dataset_epoch, analysis_id),
 UNIQUE (dataset_epoch, analysis_id, case_id),
 FOREIGN KEY (dataset_epoch, snapshot_id, case_id) REFERENCES runtime.evidence_snapshot(dataset_epoch, snapshot_id, case_id),
 CHECK ((status IN ('COMPLETED','FAILED','CANCELLED')) = (finished_at IS NOT NULL)),
 CHECK (finished_at IS NULL OR finished_at >= created_at),
 CHECK (status NOT IN ('AGENT_PENDING','AGENT_RUNNING') OR fallback_reason IS NOT NULL)
);
CREATE UNIQUE INDEX analysis_one_active_idx ON runtime.analysis_run(dataset_epoch, case_id) WHERE status NOT IN ('COMPLETED','FAILED','CANCELLED');
CREATE INDEX analysis_case_history_idx ON runtime.analysis_run(dataset_epoch, case_id, created_at);
-- @table analysis | Java | Diagnostic structuré immuable ; aucune vérité de simulation.
CREATE TABLE runtime.diagnostic_revision (
 dataset_epoch uuid NOT NULL, -- Époque.
 diagnostic_id uuid NOT NULL, -- Identité d'une révision de diagnostic.
 analysis_id uuid NOT NULL, -- Analyse productrice.
 case_id uuid NOT NULL, -- Cas, FK composite évitant un rattachement erroné.
 revision integer NOT NULL CHECK (revision > 0), -- Numéro dans l'analyse.
 producer text NOT NULL CHECK (producer IN ('DMN','INVESTIGATOR')), -- Provenance observée, pas une persona utilisateur.
 conclusion text NOT NULL CHECK (conclusion IN ('SUPPORTED','PARTIAL','INDETERMINATE','CONFLICT')), -- Conclusion épistémique.
 result jsonb NOT NULL CHECK (jsonb_typeof(result) = 'object'), -- Diagnostic : findings/missingEvidence/summary ; causeCode inféré admis, labels oracle interdits.
 content_sha256 runtime.sha256 NOT NULL, -- Empreinte du résultat validé.
 created_at timestamptz NOT NULL, -- Adoption réelle du résultat.
 PRIMARY KEY (dataset_epoch, diagnostic_id),
 UNIQUE (dataset_epoch, analysis_id, revision),
 UNIQUE (dataset_epoch, diagnostic_id, case_id),
 FOREIGN KEY (dataset_epoch, analysis_id, case_id) REFERENCES runtime.analysis_run(dataset_epoch, analysis_id, case_id)
);
-- @table analysis | Admin via Java | Acte distinct de la publication ; immuable.
CREATE TABLE runtime.diagnostic_acceptance (
 dataset_epoch uuid NOT NULL, -- Époque.
 acceptance_id uuid NOT NULL, -- Identité de l'acte.
 diagnostic_id uuid NOT NULL, -- Révision exacte acceptée.
 case_id uuid NOT NULL, -- Cas de ce diagnostic.
 accepted_by text NOT NULL CHECK (accepted_by = 'Admin'), -- Unique rôle utilisateur.
 accepted_at timestamptz NOT NULL, -- Temps réel.
 command_id uuid NOT NULL, -- Idempotence de l'acte.
 PRIMARY KEY (dataset_epoch, acceptance_id),
 UNIQUE (dataset_epoch, diagnostic_id),
 UNIQUE (dataset_epoch, command_id),
 UNIQUE (dataset_epoch, acceptance_id, case_id),
 FOREIGN KEY (dataset_epoch, diagnostic_id, case_id) REFERENCES runtime.diagnostic_revision(dataset_epoch, diagnostic_id, case_id)
);
ALTER TABLE runtime.gap_case ADD FOREIGN KEY (dataset_epoch, current_acceptance_id, case_id) REFERENCES runtime.diagnostic_acceptance(dataset_epoch, acceptance_id, case_id);
-- @table knowledge | Java | Identité de règle, indépendante de son applicabilité.
CREATE TABLE runtime.rule (
 dataset_epoch uuid NOT NULL REFERENCES runtime.dataset_epoch(dataset_epoch), -- Époque de connaissance.
 rule_id runtime.rule_key NOT NULL, -- ruleId ; R001 bootstrap, sinon UUID textuel.
 origin_acceptance_id uuid, -- Diagnostic accepté d'origine ; null seulement pour R001.
 created_at timestamptz NOT NULL, -- Temps réel de création.
 PRIMARY KEY (dataset_epoch, rule_id),
 FOREIGN KEY (dataset_epoch, origin_acceptance_id) REFERENCES runtime.diagnostic_acceptance(dataset_epoch, acceptance_id),
 CHECK ((rule_id = 'R001') = (origin_acceptance_id IS NULL))
);
-- @table knowledge | Java | Racine mutable ; les contenus sont dans les révisions immuables.
CREATE TABLE runtime.rule_change_proposal (
 dataset_epoch uuid NOT NULL REFERENCES runtime.dataset_epoch(dataset_epoch), -- Époque.
 proposal_id uuid NOT NULL, -- proposalId.
 current_revision integer NOT NULL CHECK (current_revision > 0), -- Révision à afficher ; FK différée ci-dessous.
 status text NOT NULL CHECK (status IN ('DRAFT','VALIDATING','CHANGES_REQUIRED','READY_FOR_APPROVAL','REJECTED','PUBLISHED','STALE')), -- Cycle de vie proposé.
 lock_version bigint NOT NULL DEFAULT 0 CHECK (lock_version >= 0), -- CAS de modification/publication.
 created_at timestamptz NOT NULL, -- Temps réel.
 PRIMARY KEY (dataset_epoch, proposal_id)
);
-- @table knowledge | Agent de règles via Java | Révision append-only, une modification donne un nouveau numéro.
CREATE TABLE runtime.proposal_revision (
 dataset_epoch uuid NOT NULL, -- Époque.
 proposal_id uuid NOT NULL, -- Proposition propriétaire.
 revision integer NOT NULL CHECK (revision > 0), -- proposalRevision.
 base_rule_set_version integer NOT NULL CHECK (base_rule_set_version > 0), -- Publication attendue, jamais rebasing silencieux.
 origin_acceptance_id uuid, -- Diagnostic accepté à l'origine d'une création ; peut être null pour révision du bootstrap.
 origin_snapshot_id uuid NOT NULL, -- Preuves d'origine ; une révision n'invente pas de cas courant.
 author_mission_id uuid NOT NULL, -- Mission RULE_SYNTHESIS/RULE_REVISION attestée par Java.
 rationale jsonb NOT NULL CHECK (jsonb_typeof(rationale) = 'object'), -- Justification, périmètre, exemples/tests proposés, métadonnées métier.
 content_sha256 runtime.sha256 NOT NULL, -- Hash canonique de la révision et de toutes ses opérations.
 created_at timestamptz NOT NULL, -- Temps réel.
 PRIMARY KEY (dataset_epoch, proposal_id, revision),
 FOREIGN KEY (dataset_epoch, proposal_id) REFERENCES runtime.rule_change_proposal(dataset_epoch, proposal_id),
 FOREIGN KEY (dataset_epoch, origin_acceptance_id) REFERENCES runtime.diagnostic_acceptance(dataset_epoch, acceptance_id),
 FOREIGN KEY (dataset_epoch, origin_snapshot_id) REFERENCES runtime.evidence_snapshot(dataset_epoch, snapshot_id)
);
ALTER TABLE runtime.rule_change_proposal ADD FOREIGN KEY (dataset_epoch, proposal_id, current_revision) REFERENCES runtime.proposal_revision(dataset_epoch, proposal_id, revision) DEFERRABLE INITIALLY DEFERRED;
-- @table knowledge | Agent de règles via Java | Opérations d'une révision ; aucun CREATE/REVISE Admin.
CREATE TABLE runtime.proposal_operation (
 dataset_epoch uuid NOT NULL, -- Époque.
 proposal_id uuid NOT NULL, -- Proposition.
 revision integer NOT NULL, -- Révision exacte.
 operation_no integer NOT NULL CHECK (operation_no > 0), -- Ordre stable d'affichage ; application atomique de l'ensemble.
 kind text NOT NULL CHECK (kind IN ('CREATE','REVISE','RETIRE')), -- Type d'opération.
 rule_id runtime.rule_key NOT NULL, -- Identité cible ; FK polymorphe contrôlée à la validation pour CREATE.
 expected_rule_version integer CHECK (expected_rule_version > 0), -- Version cible attendue pour REVISE/RETIRE.
 candidate_rule_version integer CHECK (candidate_rule_version > 0), -- Nouvelle version pour CREATE/REVISE.
 dmn_xml text, -- Artefact candidat exact ; null pour RETIRE.
 artifact_sha256 runtime.sha256, -- Hash des octets UTF-8 du DMN, non hash d'une traduction UI.
 metadata jsonb NOT NULL CHECK (jsonb_typeof(metadata) = 'object'), -- Définition des causes métier, portée, preuves et justification du retrait.
 PRIMARY KEY (dataset_epoch, proposal_id, revision, operation_no),
 UNIQUE (dataset_epoch, proposal_id, revision, rule_id),
 FOREIGN KEY (dataset_epoch, proposal_id, revision) REFERENCES runtime.proposal_revision(dataset_epoch, proposal_id, revision),
 CHECK ((kind = 'RETIRE' AND candidate_rule_version IS NULL AND dmn_xml IS NULL AND artifact_sha256 IS NULL AND expected_rule_version IS NOT NULL) OR (kind = 'CREATE' AND expected_rule_version IS NULL AND candidate_rule_version IS NOT NULL AND candidate_rule_version = 1 AND dmn_xml IS NOT NULL AND artifact_sha256 IS NOT NULL) OR (kind = 'REVISE' AND expected_rule_version IS NOT NULL AND candidate_rule_version IS NOT NULL AND candidate_rule_version > expected_rule_version AND dmn_xml IS NOT NULL AND artifact_sha256 IS NOT NULL))
);
-- @table knowledge | Java | Rapports immuables attachés au contenu exact, jamais booléen de validation flottant.
CREATE TABLE runtime.validation_report (
 dataset_epoch uuid NOT NULL, -- Époque.
 report_id uuid NOT NULL, -- Identité du rapport.
 proposal_id uuid NOT NULL, -- Proposition testée.
 proposal_revision integer NOT NULL, -- Révision testée.
 base_rule_set_version integer NOT NULL CHECK (base_rule_set_version > 0), -- Publication contre laquelle le replay a été fait.
 proposal_sha256 runtime.sha256 NOT NULL, -- Empreinte exacte de la proposition.
 candidate_manifest_sha256 runtime.sha256 NOT NULL, -- Empreinte de toutes les versions candidates.
 corpus_sha256 runtime.sha256 NOT NULL, -- Empreinte du corpus indépendant ; pas son oracle.
 engine_version text NOT NULL, -- Version exacte KIE.
 fact_schema_version text NOT NULL, -- Contrat d'entrée testé.
 validator_version text NOT NULL, -- Version des contrôles de sous-ensemble et invariants.
 outcome text NOT NULL CHECK (outcome IN ('PASS','BLOCKED','INCOMPLETE')), -- PASS n'est pas une preuve formelle universelle.
 blocking_count integer NOT NULL CHECK (blocking_count >= 0), -- Nombre de blocages.
 report jsonb NOT NULL CHECK (jsonb_typeof(report) = 'object'), -- Rapport expurgé des labels holdout ; avertissements, replay, détails techniques.
 created_at timestamptz NOT NULL, -- Temps réel.
 PRIMARY KEY (dataset_epoch, report_id),
 UNIQUE (dataset_epoch, report_id, proposal_id, proposal_revision),
 FOREIGN KEY (dataset_epoch, proposal_id, proposal_revision) REFERENCES runtime.proposal_revision(dataset_epoch, proposal_id, revision),
 CHECK (outcome <> 'PASS' OR blocking_count = 0)
);
-- @table knowledge | Java | Versions exécutables immuables ; brouillons conservés dans proposal_operation.
CREATE TABLE runtime.rule_version (
 dataset_epoch uuid NOT NULL, -- Époque.
 rule_id runtime.rule_key NOT NULL, -- Identité stable.
 rule_version integer NOT NULL CHECK (rule_version > 0), -- Version positive.
 author_kind text NOT NULL CHECK (author_kind IN ('BOOTSTRAP','AGENT')), -- Seule exception manuelle : bootstrap.
 proposal_id uuid, -- Proposition productrice, hors bootstrap.
 proposal_revision integer, -- Révision exacte productrice.
 dmn_xml text NOT NULL, -- Module DMN original, UTF-8.
 artifact_sha256 runtime.sha256 NOT NULL, -- Hash exact de l'artefact.
 decision_id text NOT NULL, -- Identifiant de sortie DMN.
 decision_row_count integer NOT NULL CHECK (decision_row_count > 0), -- Métadonnée vérifiée par parsing XML côté Java.
 fact_schema_version text NOT NULL, -- Version du contrat CaseFacts.
 engine_version text NOT NULL, -- Version de compilation validée.
 metadata jsonb NOT NULL CHECK (jsonb_typeof(metadata) = 'object'), -- Vocabulaire métier versionné et portée, jamais attentes de simulation.
 created_at timestamptz NOT NULL, -- Temps réel de matérialisation après validation.
 PRIMARY KEY (dataset_epoch, rule_id, rule_version),
 FOREIGN KEY (dataset_epoch, rule_id) REFERENCES runtime.rule(dataset_epoch, rule_id),
 FOREIGN KEY (dataset_epoch, proposal_id, proposal_revision) REFERENCES runtime.proposal_revision(dataset_epoch, proposal_id, revision),
 CHECK ((author_kind = 'BOOTSTRAP' AND rule_id = 'R001' AND rule_version = 1 AND decision_row_count = 1 AND proposal_id IS NULL AND proposal_revision IS NULL) OR (author_kind = 'AGENT' AND proposal_id IS NOT NULL AND proposal_revision IS NOT NULL))
);
CREATE UNIQUE INDEX rule_one_bootstrap_idx ON runtime.rule_version(dataset_epoch) WHERE author_kind = 'BOOTSTRAP';
-- @table knowledge | Admin via Java / import bootstrap | Publication immuable, sans état mutable approved.
CREATE TABLE runtime.rule_set_publication (
 dataset_epoch uuid NOT NULL REFERENCES runtime.dataset_epoch(dataset_epoch), -- Époque.
 rule_set_version integer NOT NULL CHECK (rule_set_version > 0), -- Version totale du référentiel.
 previous_version integer, -- Parent ; null seulement pour bootstrap.
 kind text NOT NULL CHECK (kind IN ('BOOTSTRAP','AGENT_CHANGE','ADMIN_RETIRE')), -- Origine de la publication.
 proposal_id uuid, -- Proposition exacte pour AGENT_CHANGE.
 proposal_revision integer, -- Révision approuvée.
 validation_report_id uuid, -- Rapport exact pour AGENT_CHANGE.
 retired_rule_id runtime.rule_key, -- Règle retirée directement par Admin, sinon null.
 manifest_sha256 runtime.sha256 NOT NULL, -- Hash du manifeste canonique ordonné ruleId/version/hash.
 approved_by text NOT NULL CHECK (approved_by = 'Admin'), -- Unique rôle humain ; bootstrap = acte d'initialisation explicite.
 command_id uuid NOT NULL, -- Idempotence de publication.
 created_at timestamptz NOT NULL, -- Instant de préparation de publication.
 sealed_at timestamptz, -- Clôture unique du manifeste ; activation interdite tant que null.
 PRIMARY KEY (dataset_epoch, rule_set_version),
 UNIQUE (dataset_epoch, command_id),
 UNIQUE (dataset_epoch, proposal_id, proposal_revision),
 FOREIGN KEY (dataset_epoch, previous_version) REFERENCES runtime.rule_set_publication(dataset_epoch, rule_set_version),
 FOREIGN KEY (dataset_epoch, validation_report_id, proposal_id, proposal_revision) REFERENCES runtime.validation_report(dataset_epoch, report_id, proposal_id, proposal_revision),
 FOREIGN KEY (dataset_epoch, retired_rule_id) REFERENCES runtime.rule(dataset_epoch, rule_id),
 CHECK ((kind = 'BOOTSTRAP' AND rule_set_version = 1 AND previous_version IS NULL AND proposal_id IS NULL AND proposal_revision IS NULL AND validation_report_id IS NULL AND retired_rule_id IS NULL) OR (kind = 'AGENT_CHANGE' AND previous_version IS NOT NULL AND rule_set_version = previous_version + 1 AND proposal_id IS NOT NULL AND proposal_revision IS NOT NULL AND validation_report_id IS NOT NULL AND retired_rule_id IS NULL) OR (kind = 'ADMIN_RETIRE' AND previous_version IS NOT NULL AND rule_set_version = previous_version + 1 AND proposal_id IS NULL AND proposal_revision IS NULL AND validation_report_id IS NULL AND retired_rule_id IS NOT NULL))
);
-- @table knowledge | Java/import bootstrap | Manifeste complet ; absence d'une règle = non applicable dans cette publication.
CREATE TABLE runtime.rule_set_member (
 dataset_epoch uuid NOT NULL, -- Époque.
 rule_set_version integer NOT NULL, -- Publication.
 rule_id runtime.rule_key NOT NULL, -- Règle membre.
 rule_version integer NOT NULL, -- Version exacte.
 PRIMARY KEY (dataset_epoch, rule_set_version, rule_id),
 FOREIGN KEY (dataset_epoch, rule_set_version) REFERENCES runtime.rule_set_publication(dataset_epoch, rule_set_version),
 FOREIGN KEY (dataset_epoch, rule_id, rule_version) REFERENCES runtime.rule_version(dataset_epoch, rule_id, rule_version)
);
-- @table knowledge | Java | Pointeur actif par époque ; bloquer les commandes si absent.
CREATE TABLE runtime.active_rule_set (
 dataset_epoch uuid PRIMARY KEY REFERENCES runtime.dataset_epoch(dataset_epoch), -- Époque.
 rule_set_version integer NOT NULL, -- Publication active.
 lock_version bigint NOT NULL DEFAULT 0 CHECK (lock_version >= 0), -- CAS publication/retrait.
 FOREIGN KEY (dataset_epoch, rule_set_version) REFERENCES runtime.rule_set_publication(dataset_epoch, rule_set_version)
);
ALTER TABLE runtime.analysis_run ADD FOREIGN KEY (dataset_epoch, rule_set_version) REFERENCES runtime.rule_set_publication(dataset_epoch, rule_set_version);
ALTER TABLE runtime.proposal_revision ADD FOREIGN KEY (dataset_epoch, base_rule_set_version) REFERENCES runtime.rule_set_publication(dataset_epoch, rule_set_version);
ALTER TABLE runtime.validation_report ADD FOREIGN KEY (dataset_epoch, base_rule_set_version) REFERENCES runtime.rule_set_publication(dataset_epoch, rule_set_version);
-- @table dialogue | Java | Contexte fixé ; exactement un cas, une règle ou une proposition.
CREATE TABLE runtime.expert_conversation (
 dataset_epoch uuid NOT NULL, -- Époque.
 conversation_id uuid NOT NULL, -- conversationId.
 case_id uuid, -- Contexte cas, si applicable.
 rule_id runtime.rule_key, -- Contexte règle, si applicable.
 proposal_id uuid, -- Contexte proposition, si applicable.
 next_turn_no integer NOT NULL DEFAULT 1 CHECK (next_turn_no > 0), -- Compteur sous verrou de conversation.
 lock_version bigint NOT NULL DEFAULT 0 CHECK (lock_version >= 0), -- CAS.
 created_at timestamptz NOT NULL, -- Temps réel.
 PRIMARY KEY (dataset_epoch, conversation_id),
 FOREIGN KEY (dataset_epoch, case_id) REFERENCES runtime.gap_case(dataset_epoch, case_id),
 FOREIGN KEY (dataset_epoch, rule_id) REFERENCES runtime.rule(dataset_epoch, rule_id),
 FOREIGN KEY (dataset_epoch, proposal_id) REFERENCES runtime.rule_change_proposal(dataset_epoch, proposal_id),
 CHECK (num_nonnulls(case_id, rule_id, proposal_id) = 1)
);
-- @table dialogue | Java | Tour séquencé ; ne vaut jamais approbation.
CREATE TABLE runtime.conversation_turn (
 dataset_epoch uuid NOT NULL, -- Époque.
 turn_id uuid NOT NULL, -- turnId.
 conversation_id uuid NOT NULL, -- Conversation propriétaire.
 turn_no integer NOT NULL CHECK (turn_no > 0), -- Ordre total par conversation.
 proposal_id uuid, -- Proposition visée par ce tour, si présente.
 proposal_revision integer, -- Révision visée, jamais réattachée silencieusement après modification.
 status text NOT NULL CHECK (status IN ('QUEUED','RUNNING','COMPLETED','FAILED','CANCELLED')), -- Cycle technique du tour.
 created_at timestamptz NOT NULL, -- Temps réel.
 PRIMARY KEY (dataset_epoch, turn_id),
 UNIQUE (dataset_epoch, conversation_id, turn_no),
 UNIQUE (dataset_epoch, turn_id, conversation_id),
 FOREIGN KEY (dataset_epoch, conversation_id) REFERENCES runtime.expert_conversation(dataset_epoch, conversation_id),
 FOREIGN KEY (dataset_epoch, proposal_id, proposal_revision) REFERENCES runtime.proposal_revision(dataset_epoch, proposal_id, revision),
 CHECK ((proposal_id IS NULL) = (proposal_revision IS NULL))
);
CREATE UNIQUE INDEX conversation_one_active_turn_idx ON runtime.conversation_turn(dataset_epoch, conversation_id) WHERE status IN ('QUEUED','RUNNING');
-- @table dialogue | Java | Messages append-only ; contenu affichable borné et expurgé.
CREATE TABLE runtime.chat_message (
 dataset_epoch uuid NOT NULL, -- Époque.
 message_id uuid NOT NULL, -- Identité du message.
 turn_id uuid NOT NULL, -- Tour producteur.
 conversation_id uuid NOT NULL, -- Conversation vérifiée par FK composite.
 sequence integer NOT NULL CHECK (sequence > 0), -- Séquence dans le tour.
 speaker text NOT NULL CHECK (speaker IN ('Admin','INVESTIGATOR','RULE_AGENT','SYSTEM')), -- Un seul rôle humain, deux rôles agents.
 content text NOT NULL, -- Message/synthèse, jamais chaîne de pensée privée.
 references_json jsonb NOT NULL CHECK (jsonb_typeof(references_json) = 'array'), -- Références de preuves/règles contrôlées par Java.
 created_at timestamptz NOT NULL, -- Temps réel.
 PRIMARY KEY (dataset_epoch, message_id),
 UNIQUE (dataset_epoch, turn_id, sequence),
 FOREIGN KEY (dataset_epoch, turn_id, conversation_id) REFERENCES runtime.conversation_turn(dataset_epoch, turn_id, conversation_id)
);
-- @table execution | Java | Mission bornée ; le worker ne possède aucune connexion SQL.
CREATE TABLE runtime.agent_mission (
 dataset_epoch uuid NOT NULL, -- Époque imposée à chaque outil et événement.
 mission_id uuid NOT NULL, -- missionId, clé Kafka.
 kind text NOT NULL CHECK (kind IN ('INVESTIGATION','RULE_SYNTHESIS','RULE_REVISION','EXPERT_TURN')), -- Rôle/commande autorisés.
 analysis_id uuid, -- Analyse en fallback pour INVESTIGATION.
 origin_acceptance_id uuid, -- Diagnostic d'origine pour RULE_SYNTHESIS et contexte de révision.
 target_rule_id runtime.rule_key, -- Règle visée par RULE_REVISION sans nouvelle proposition/cas courant.
 proposal_id uuid, -- Proposition visée, si révision de proposition.
 proposal_revision integer, -- Révision visée.
 turn_id uuid, -- Tour pour EXPERT_TURN.
 snapshot_id uuid NOT NULL, -- Snapshot obligatoire, y compris cas d'origine d'une règle.
 rule_set_version integer NOT NULL, -- Référentiel fixé.
 status text NOT NULL CHECK (status IN ('QUEUED','RUNNING','CANCEL_REQUESTED','COMPLETED','FAILED','CANCELLED')), -- État durable.
 current_attempt integer NOT NULL DEFAULT 0 CHECK (current_attempt BETWEEN 0 AND 2), -- Une tentative initiale et un retry maximum.
 prompt_version text NOT NULL, -- Prompt exact référencé.
 model_configuration jsonb NOT NULL CHECK (jsonb_typeof(model_configuration) = 'object'), -- Modèle et paramètres non secrets ; aucun credential.
 max_llm_calls integer NOT NULL CHECK (max_llm_calls > 0), -- Budget cumulé retries inclus.
 max_tool_calls integer NOT NULL CHECK (max_tool_calls BETWEEN 1 AND 24), -- Budget outils cumulé.
 max_input_tokens integer NOT NULL CHECK (max_input_tokens BETWEEN 1 AND 64000), -- Plafond de réservation en entrée.
 max_output_tokens integer NOT NULL CHECK (max_output_tokens BETWEEN 1 AND 12000), -- Plafond de réservation en sortie.
 max_duration_seconds integer NOT NULL CHECK (max_duration_seconds > 0), -- Hors attente humaine et file.
 started_at timestamptz, -- Début de première tentative ; jamais remis à zéro au retry.
 deadline_at timestamptz, -- Échéance globale de la mission, pas du lease.
 created_at timestamptz NOT NULL, -- Création réelle.
 finished_at timestamptz, -- Terminal réel.
 lock_version bigint NOT NULL DEFAULT 0 CHECK (lock_version >= 0), -- CAS.
 PRIMARY KEY (dataset_epoch, mission_id),
 FOREIGN KEY (dataset_epoch, analysis_id) REFERENCES runtime.analysis_run(dataset_epoch, analysis_id),
 FOREIGN KEY (dataset_epoch, origin_acceptance_id) REFERENCES runtime.diagnostic_acceptance(dataset_epoch, acceptance_id),
 FOREIGN KEY (dataset_epoch, proposal_id, proposal_revision) REFERENCES runtime.proposal_revision(dataset_epoch, proposal_id, revision),
 FOREIGN KEY (dataset_epoch, turn_id) REFERENCES runtime.conversation_turn(dataset_epoch, turn_id),
 FOREIGN KEY (dataset_epoch, snapshot_id) REFERENCES runtime.evidence_snapshot(dataset_epoch, snapshot_id),
 FOREIGN KEY (dataset_epoch, rule_set_version) REFERENCES runtime.rule_set_publication(dataset_epoch, rule_set_version),
 CHECK ((proposal_id IS NULL) = (proposal_revision IS NULL)),
 FOREIGN KEY (dataset_epoch, target_rule_id) REFERENCES runtime.rule(dataset_epoch, rule_id),
 CHECK (kind <> 'RULE_REVISION' OR target_rule_id IS NOT NULL OR proposal_id IS NOT NULL),
 CHECK (kind <> 'INVESTIGATION' OR analysis_id IS NOT NULL),
 CHECK (kind <> 'RULE_SYNTHESIS' OR origin_acceptance_id IS NOT NULL),
 CHECK ((kind = 'EXPERT_TURN') = (turn_id IS NOT NULL)),
 CHECK ((kind = 'EXPERT_TURN' AND max_llm_calls <= 4 AND max_duration_seconds <= 120) OR (kind <> 'EXPERT_TURN' AND max_llm_calls <= 8 AND max_duration_seconds <= 300)),
 CHECK ((started_at IS NULL) = (deadline_at IS NULL)),
 CHECK (deadline_at IS NULL OR deadline_at > started_at),
 CHECK ((status IN ('COMPLETED','FAILED','CANCELLED')) = (finished_at IS NOT NULL))
);
CREATE UNIQUE INDEX mission_one_investigation_idx ON runtime.agent_mission(dataset_epoch, analysis_id) WHERE kind = 'INVESTIGATION';
CREATE UNIQUE INDEX mission_one_synthesis_idx ON runtime.agent_mission(dataset_epoch, origin_acceptance_id) WHERE kind = 'RULE_SYNTHESIS';
CREATE UNIQUE INDEX mission_one_turn_idx ON runtime.agent_mission(dataset_epoch, turn_id) WHERE turn_id IS NOT NULL;
CREATE INDEX mission_queue_idx ON runtime.agent_mission(dataset_epoch, status, created_at);
ALTER TABLE runtime.proposal_revision ADD FOREIGN KEY (dataset_epoch, author_mission_id) REFERENCES runtime.agent_mission(dataset_epoch, mission_id);
-- @table execution | Java | Tentative lease et jeton de fencing, jamais réutilisés.
CREATE TABLE runtime.mission_attempt (
 dataset_epoch uuid NOT NULL, -- Époque.
 mission_id uuid NOT NULL, -- Mission propriétaire.
 attempt integer NOT NULL CHECK (attempt BETWEEN 1 AND 2), -- Numéro de tentative monotone.
 lease_token uuid NOT NULL, -- Jeton de fencing opaque fourni aux outils/heartbeats.
 worker_id text NOT NULL, -- Identité technique, pas utilisateur.
 status text NOT NULL CHECK (status IN ('RUNNING','COMPLETED','FAILED','EXPIRED','CANCELLED')), -- Une tentative expirée ne redevient jamais active.
 claimed_at timestamptz NOT NULL, -- Instant serveur de claim.
 heartbeat_at timestamptz NOT NULL, -- Dernier heartbeat accepté.
 lease_expires_at timestamptz NOT NULL, -- Échéance bornée par deadline_at côté Java.
 last_sequence bigint NOT NULL DEFAULT 0 CHECK (last_sequence >= 0), -- Dernière séquence contiguë adoptée pour cette tentative.
 finished_at timestamptz, -- Terminal technique.
 failure_code text, -- Code d'échec expurgé, sans secrets.
 PRIMARY KEY (dataset_epoch, mission_id, attempt),
 UNIQUE (dataset_epoch, lease_token),
 FOREIGN KEY (dataset_epoch, mission_id) REFERENCES runtime.agent_mission(dataset_epoch, mission_id),
 CHECK (heartbeat_at >= claimed_at AND lease_expires_at > heartbeat_at),
 CHECK ((status = 'RUNNING') = (finished_at IS NULL))
);
CREATE UNIQUE INDEX mission_one_running_attempt_idx ON runtime.mission_attempt(dataset_epoch, mission_id) WHERE status = 'RUNNING';
-- @table execution | Java | Deux créneaux globaux par époque ; aucun troisième numéro autorisé.
CREATE TABLE runtime.execution_slot (
 dataset_epoch uuid NOT NULL REFERENCES runtime.dataset_epoch(dataset_epoch), -- Époque.
 slot_no smallint NOT NULL CHECK (slot_no IN (1,2)), -- Limite globale de concurrence logique.
 mission_id uuid, -- null si libre.
 attempt integer, -- Tentative titulaire.
 PRIMARY KEY (dataset_epoch, slot_no),
 UNIQUE (dataset_epoch, mission_id, attempt),
 FOREIGN KEY (dataset_epoch, mission_id, attempt) REFERENCES runtime.mission_attempt(dataset_epoch, mission_id, attempt),
 CHECK ((mission_id IS NULL) = (attempt IS NULL))
);
-- @table execution | Java | Journal de réservation/règlement de quota ; retries et appels incertains inclus.
CREATE TABLE runtime.call_reservation (
 dataset_epoch uuid NOT NULL, -- Époque.
 reservation_id uuid NOT NULL, -- Idempotence de la demande d'appel.
 mission_id uuid NOT NULL, -- Mission portant le budget cumulé.
 attempt integer NOT NULL, -- Tentative ayant réservé.
 kind text NOT NULL CHECK (kind IN ('LLM','TOOL')), -- Catégorie de budget.
 status text NOT NULL CHECK (status IN ('RESERVED','SETTLED','UNKNOWN','VOID')), -- UNKNOWN = appel possiblement effectué, budget non libéré.
 reserved_input_tokens integer NOT NULL CHECK (reserved_input_tokens >= 0), -- Estimation conservatrice avant appel.
 reserved_output_tokens integer NOT NULL CHECK (reserved_output_tokens >= 0), -- Max output transmis au fournisseur.
 actual_input_tokens integer CHECK (actual_input_tokens >= 0), -- Mesure fournisseur, peut dépasser estimation ; ne pas rejeter cette vérité comptable.
 actual_output_tokens integer CHECK (actual_output_tokens >= 0), -- Inclure raisonnement quand rapporté.
 provider_request_id text, -- Corrélation technique non secrète, pas garantie d'exactly-once.
 reserved_at timestamptz NOT NULL, -- Temps réel.
 settled_at timestamptz, -- Retour comptabilisé ou libération prouvée avant envoi.
 PRIMARY KEY (dataset_epoch, reservation_id),
 FOREIGN KEY (dataset_epoch, mission_id, attempt) REFERENCES runtime.mission_attempt(dataset_epoch, mission_id, attempt),
 CHECK (kind <> 'TOOL' OR (reserved_input_tokens = 0 AND reserved_output_tokens = 0)),
 CHECK (status <> 'SETTLED' OR (settled_at IS NOT NULL AND (kind = 'TOOL' OR (actual_input_tokens IS NOT NULL AND actual_output_tokens IS NOT NULL))))
);
CREATE INDEX reservation_mission_idx ON runtime.call_reservation(dataset_epoch, mission_id, kind, status);
-- @table analysis | Admin via Java | Lot borné ; liste figée, états enfants conservés.
CREATE TABLE runtime.analysis_batch (
 dataset_epoch uuid NOT NULL REFERENCES runtime.dataset_epoch(dataset_epoch), -- Époque.
 batch_id uuid NOT NULL, -- Identité du lot.
 status text NOT NULL CHECK (status IN ('QUEUED','RUNNING','COMPLETED','CANCEL_REQUESTED','CANCELLED')), -- État de coordination.
 created_at timestamptz NOT NULL, -- Temps réel.
 command_id uuid NOT NULL, -- Idempotence du lancement.
 PRIMARY KEY (dataset_epoch, batch_id),
 UNIQUE (dataset_epoch, command_id)
);
-- @table analysis | Java | Sélection figée de 1 à 5 cas ; minimum vérifié à la création.
CREATE TABLE runtime.batch_item (
 dataset_epoch uuid NOT NULL, -- Époque.
 batch_id uuid NOT NULL, -- Lot parent.
 position smallint NOT NULL CHECK (position BETWEEN 1 AND 5), -- Borne SQL de cinq enfants maximum.
 case_id uuid NOT NULL, -- Cas sélectionné.
 analysis_id uuid, -- Analyse associée dès lancement, null si attente ou conflit.
 status text NOT NULL CHECK (status IN ('PENDING','RUNNING','COMPLETED','FAILED','CANCELLED','CONFLICT')), -- Résultat enfant, sans écraser diagnostic.
 error_code text, -- Cause technique de refus/échec, pas label de simulation.
 PRIMARY KEY (dataset_epoch, batch_id, position),
 UNIQUE (dataset_epoch, batch_id, case_id),
 FOREIGN KEY (dataset_epoch, batch_id) REFERENCES runtime.analysis_batch(dataset_epoch, batch_id),
 FOREIGN KEY (dataset_epoch, case_id) REFERENCES runtime.gap_case(dataset_epoch, case_id),
 FOREIGN KEY (dataset_epoch, analysis_id, case_id) REFERENCES runtime.analysis_run(dataset_epoch, analysis_id, case_id)
);
-- @table execution | Java | Déduplication REST : clé, empreinte de requête et résultat commités ensemble.
CREATE TABLE runtime.command_receipt (
 dataset_epoch uuid NOT NULL REFERENCES runtime.dataset_epoch(dataset_epoch), -- Époque, jamais réutiliser une clé de l'ancien reset.
 command_id uuid NOT NULL, -- Clé d'idempotence opaque.
 command_type text NOT NULL, -- Type explicite de commande.
 request_sha256 runtime.sha256 NOT NULL, -- Empêche même clé avec contenu différent.
 response_status integer NOT NULL CHECK (response_status BETWEEN 200 AND 599), -- Statut REST durable.
 response_body jsonb NOT NULL, -- Identifiants et résultat court, sans secret.
 created_at timestamptz NOT NULL, -- Temps réel.
 PRIMARY KEY (dataset_epoch, command_id)
);
-- @table execution | Java/dispatcher | État métier + insertion dans une seule transaction.
CREATE TABLE runtime.outbox_event (
 dataset_epoch uuid NOT NULL REFERENCES runtime.dataset_epoch(dataset_epoch), -- Époque.
 event_id uuid NOT NULL, -- eventId ; stable à chaque republication.
 mission_id uuid NOT NULL, -- missionId ; clé Kafka.
 event_type text NOT NULL, -- Type de commande du contrat Kafka.
 envelope jsonb NOT NULL CHECK (jsonb_typeof(envelope) = 'object'), -- Enveloppe complète conforme au JSON Schema partagé.
 created_at timestamptz NOT NULL, -- Temps d'insertion, distinct de occurredAt métier.
 available_at timestamptz NOT NULL, -- Backoff de dispatch.
 dispatch_attempts integer NOT NULL DEFAULT 0 CHECK (dispatch_attempts >= 0), -- Retries transport, pas budget LLM.
 dispatcher_token uuid, -- Fencing du dispatcher.
 dispatch_lease_until timestamptz, -- Lease de publication.
 published_at timestamptz, -- Marqué après ack Kafka ; crash intermédiaire => doublon attendu.
 PRIMARY KEY (dataset_epoch, event_id),
 FOREIGN KEY (dataset_epoch, mission_id) REFERENCES runtime.agent_mission(dataset_epoch, mission_id),
 CHECK ((dispatcher_token IS NULL) = (dispatch_lease_until IS NULL))
);
CREATE INDEX outbox_pending_idx ON runtime.outbox_event(available_at, created_at) WHERE published_at IS NULL;
-- @table execution | Java consommateur | Réception et effet métier commités ensemble avant ack Kafka.
CREATE TABLE runtime.inbox_event (
 event_id uuid PRIMARY KEY, -- Déduplication globale par eventId, même époque rejetée.
 dataset_epoch uuid NOT NULL, -- Époque déclarée ; volontairement sans FK pour journaliser une époque périmée/inconnue.
 mission_id uuid NOT NULL, -- Mission déclarée, idem : ne pas adopter sans vérification.
 attempt integer NOT NULL CHECK (attempt > 0), -- Tentative déclarée.
 sequence bigint NOT NULL CHECK (sequence > 0), -- Séquence producteur par tentative.
 event_type text NOT NULL, -- Type validé de l'événement reçu.
 payload_sha256 runtime.sha256 NOT NULL, -- Détecte collisions eventId/contenu.
 envelope jsonb NOT NULL CHECK (jsonb_typeof(envelope) = 'object'), -- Enveloppe reçue expurgée, taille bornée à l'entrée.
 disposition text NOT NULL CHECK (disposition IN ('BUFFERED','APPLIED','DUPLICATE','LATE','REJECTED')), -- Résultat d'adoption, pas simple réception technique.
 reason_code text, -- Motif explicite de rejet/retard.
 received_at timestamptz NOT NULL, -- Temps réel de réception.
 applied_at timestamptz, -- Temps réel de l'effet métier, null si non adopté.
 CHECK ((disposition = 'APPLIED') = (applied_at IS NOT NULL))
);
CREATE UNIQUE INDEX inbox_applied_sequence_idx ON runtime.inbox_event(dataset_epoch, mission_id, attempt, sequence) WHERE disposition = 'APPLIED';
CREATE INDEX inbox_buffer_idx ON runtime.inbox_event(dataset_epoch, mission_id, attempt, sequence) WHERE disposition = 'BUFFERED';
-- @table execution | Java | Allocateur SSE transactionnel ; pas une sequence SQL susceptible d'ordre de commit inversé.
CREATE TABLE runtime.sse_stream (
 dataset_epoch uuid PRIMARY KEY REFERENCES runtime.dataset_epoch(dataset_epoch), -- Époque portée par Last-Event-ID.
 last_cursor bigint NOT NULL DEFAULT 0 CHECK (last_cursor >= 0), -- Dernier curseur alloué sous verrou jusqu'au commit.
 retained_from bigint NOT NULL DEFAULT 1 CHECK (retained_from > 0), -- Plus petit curseur disponible ; trou de rétention => resynchronisation.
 CHECK (retained_from <= last_cursor + 1)
);
-- @table execution | Java | Notifications après changement durable ; pas un bus de commandes.
CREATE TABLE runtime.progress_event (
 dataset_epoch uuid NOT NULL, -- Époque du flux.
 cursor bigint NOT NULL CHECK (cursor > 0), -- Curseur global dans l'époque, ordre de commit sérialisé.
 event_type text NOT NULL, -- Type SSE versionné côté API.
 aggregate_type text NOT NULL, -- Type d'entité à relire par REST.
 aggregate_id text NOT NULL, -- Identité opaque ou R001 ; FK polymorphe vérifiée côté Java.
 payload jsonb NOT NULL CHECK (jsonb_typeof(payload) = 'object'), -- Projection notification expurgée ; taille bornée.
 created_at timestamptz NOT NULL, -- Temps réel.
 PRIMARY KEY (dataset_epoch, cursor),
 FOREIGN KEY (dataset_epoch) REFERENCES runtime.sse_stream(dataset_epoch)
);
-- @table evaluation | Importeur oracle / évaluateur seulement | Manifeste sans FK runtime : corpus isolés et imports indépendants.
CREATE TABLE evaluation.corpus (
 corpus_id uuid PRIMARY KEY, -- Identité technique du corpus.
 dataset_id uuid NOT NULL, -- Jeu stable correspondant, sans dépendance runtime.
 profile text NOT NULL, -- Profil générateur.
 purpose text NOT NULL CHECK (purpose IN ('DEMO_ORACLE','VALIDATION','HOLDOUT')), -- Politique de divulgation hors runtime.
 seed bigint NOT NULL, -- Graine reproduisible.
 generator_version text NOT NULL, -- Version exacte.
 runtime_sha256 text NOT NULL CHECK (runtime_sha256 ~ '^[0-9a-f]{64}$'), -- Hash du fichier source associé.
 oracle_sha256 text NOT NULL CHECK (oracle_sha256 ~ '^[0-9a-f]{64}$'), -- Hash du fichier oracle indépendant.
 case_count integer NOT NULL CHECK (case_count >= 0), -- Total déclaré à comparer au nombre effectivement importé.
 manifest jsonb NOT NULL CHECK (jsonb_typeof(manifest) = 'object') -- Métadonnées pédagogiques et algorithme PRNG, inaccessibles aux agents.
);
-- @table evaluation | Importeur oracle / évaluateur seulement | Vérité injectée et attentes admissibles, jamais preuve runtime.
CREATE TABLE evaluation.case_expectation (
 corpus_id uuid NOT NULL REFERENCES evaluation.corpus(corpus_id), -- Corpus propriétaire.
 case_id uuid NOT NULL, -- Identité du cas du fichier associé ; pas FK runtime.
 copy_id uuid NOT NULL, -- Exemplaire évalué.
 scenario_family text NOT NULL, -- Famille cachée autorisée exclusivement ici.
 variant text NOT NULL CHECK (variant IN ('POSITIVE','NEGATIVE','INSUFFICIENT','COMBINATION')), -- Type de test pédagogique.
 injected_truth jsonb NOT NULL CHECK (jsonb_typeof(injected_truth) = 'object'), -- Perturbation connue du générateur.
 admissible_diagnostic jsonb NOT NULL CHECK (jsonb_typeof(admissible_diagnostic) = 'object'), -- Conclusions permises au vu des seules preuves exposées.
 PRIMARY KEY (corpus_id, case_id)
);
-- @table evaluation | Évaluateur isolé | Exécution de mesure ; aucune fonction SQL lisible par runtime.
CREATE TABLE evaluation.evaluation_run (
 evaluation_id uuid PRIMARY KEY, -- Identité de mesure.
 corpus_id uuid NOT NULL REFERENCES evaluation.corpus(corpus_id), -- Corpus validé/holdout.
 publication_manifest_sha256 text NOT NULL CHECK (publication_manifest_sha256 ~ '^[0-9a-f]{64}$'), -- Artefact évalué, exporté sans accès inverse runtime.
 evaluator_version text NOT NULL, -- Version du protocole de mesure.
 started_at timestamptz NOT NULL, -- Temps réel.
 completed_at timestamptz, -- Temps terminal.
 metrics jsonb, -- Résultats agrégés ; absence ne vaut pas réussite.
 UNIQUE (evaluation_id, corpus_id),
 CHECK (completed_at IS NULL OR completed_at >= started_at)
);
-- @table evaluation | Évaluateur isolé | Mesures par cas, jamais lues comme entrée diagnostique.
CREATE TABLE evaluation.evaluation_result (
 evaluation_id uuid NOT NULL, -- Mesure parent.
 corpus_id uuid NOT NULL, -- Corpus du résultat, vérifié par FK composite.
 case_id uuid NOT NULL, -- Cas évalué.
 observed_result jsonb NOT NULL CHECK (jsonb_typeof(observed_result) = 'object'), -- Résultat effectivement obtenu, jamais simulé en résultat réel.
 verdict text NOT NULL CHECK (verdict IN ('PASS','FAIL','ERROR','NOT_RUN')), -- Succès, échec, panne ou non exécuté distincts.
 details jsonb NOT NULL CHECK (jsonb_typeof(details) = 'object'), -- Écarts détaillés accessibles seulement à l'évaluateur/expert autorisé.
 PRIMARY KEY (evaluation_id, case_id),
 FOREIGN KEY (evaluation_id, corpus_id) REFERENCES evaluation.evaluation_run(evaluation_id, corpus_id),
 FOREIGN KEY (corpus_id, case_id) REFERENCES evaluation.case_expectation(corpus_id, case_id)
);

-- Immutabilité SQL : protège UPDATE/DELETE ; ni le propriétaire ni le superuser ne sont une frontière de sécurité.
CREATE FUNCTION runtime.reject_mutation() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 RAISE EXCEPTION 'Relation immuable : %.%', TG_TABLE_SCHEMA, TG_TABLE_NAME USING ERRCODE = '55000';
END;
$$;
-- Fermer l'import et insérer une source se sérialisent sur la même ligne d'époque.
CREATE FUNCTION runtime.guard_source_insert() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE closed timestamptz;
BEGIN
 SELECT sealed_at INTO closed FROM runtime.dataset_epoch WHERE dataset_epoch = NEW.dataset_epoch FOR SHARE;
 IF NOT FOUND OR closed IS NOT NULL THEN
  RAISE EXCEPTION 'Import absent ou clos' USING ERRCODE = '55000';
 END IF;
 RETURN NEW;
END;
$$;
CREATE FUNCTION runtime.guard_epoch_update() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 IF OLD.sealed_at IS NOT NULL OR NEW.dataset_epoch <> OLD.dataset_epoch OR NEW.dataset_id <> OLD.dataset_id OR NEW.imported_at <> OLD.imported_at OR NEW.sealed_at IS NULL THEN
  RAISE EXCEPTION 'Seule la clôture initiale de l''import est permise' USING ERRCODE = '55000';
 END IF;
 RETURN NEW;
END;
$$;
CREATE TRIGGER epoch_close_once BEFORE UPDATE ON runtime.dataset_epoch FOR EACH ROW EXECUTE FUNCTION runtime.guard_epoch_update();
CREATE TRIGGER epoch_no_delete BEFORE DELETE ON runtime.dataset_epoch FOR EACH ROW EXECUTE FUNCTION runtime.reject_mutation();
DO $$
DECLARE t text;
BEGIN
 FOREACH t IN ARRAY ARRAY['dataset','site','location','title','copy','source_event','system_state','system_state_event','inventory_scope','observation','source_availability','operator_note','source_case','gap_symptom','evidence_snapshot','evidence_reference','diagnostic_revision','diagnostic_acceptance','rule','proposal_revision','proposal_operation','validation_report','rule_version','rule_set_member','chat_message','command_receipt'] LOOP
  EXECUTE format('CREATE TRIGGER immutable_row BEFORE UPDATE OR DELETE ON runtime.%I FOR EACH ROW EXECUTE FUNCTION runtime.reject_mutation()', t);
 END LOOP;
 FOREACH t IN ARRAY ARRAY['site','location','title','copy','source_event','system_state','system_state_event','inventory_scope','observation','source_availability','operator_note','source_case','gap_symptom'] LOOP
  EXECUTE format('CREATE TRIGGER import_only BEFORE INSERT ON runtime.%I FOR EACH ROW EXECUTE FUNCTION runtime.guard_source_insert()', t);
 END LOOP;
END;
$$;
-- La fermeture fige aussi la collection du manifeste, pas seulement les lignes existantes.
CREATE FUNCTION runtime.guard_publication_seal() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 IF OLD.sealed_at IS NOT NULL OR NEW.sealed_at IS NULL OR NEW.sealed_at < OLD.created_at OR
    (to_jsonb(NEW) - 'sealed_at') IS DISTINCT FROM (to_jsonb(OLD) - 'sealed_at') THEN
  RAISE EXCEPTION 'Publication immuable ; seule la cloture initiale est permise' USING ERRCODE = '55000';
 END IF;
 RETURN NEW;
END;
$$;
CREATE TRIGGER publication_close_once BEFORE UPDATE ON runtime.rule_set_publication FOR EACH ROW EXECUTE FUNCTION runtime.guard_publication_seal();
CREATE TRIGGER publication_no_delete BEFORE DELETE ON runtime.rule_set_publication FOR EACH ROW EXECUTE FUNCTION runtime.reject_mutation();
CREATE FUNCTION runtime.guard_member_insert() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE closed timestamptz;
BEGIN
 SELECT sealed_at INTO closed FROM runtime.rule_set_publication
 WHERE dataset_epoch = NEW.dataset_epoch AND rule_set_version = NEW.rule_set_version FOR SHARE;
 IF NOT FOUND OR closed IS NOT NULL THEN
  RAISE EXCEPTION 'Manifeste absent ou clos' USING ERRCODE = '55000';
 END IF;
 RETURN NEW;
END;
$$;
CREATE TRIGGER member_before_seal BEFORE INSERT ON runtime.rule_set_member FOR EACH ROW EXECUTE FUNCTION runtime.guard_member_insert();
CREATE FUNCTION runtime.guard_active_publication() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE closed timestamptz;
BEGIN
 SELECT sealed_at INTO closed FROM runtime.rule_set_publication
 WHERE dataset_epoch = NEW.dataset_epoch AND rule_set_version = NEW.rule_set_version;
 IF NOT FOUND OR closed IS NULL THEN
  RAISE EXCEPTION 'Activation d''un manifeste non clos interdite' USING ERRCODE = '55000';
 END IF;
 RETURN NEW;
END;
$$;
CREATE TRIGGER active_publication_closed BEFORE INSERT OR UPDATE ON runtime.active_rule_set FOR EACH ROW EXECUTE FUNCTION runtime.guard_active_publication();
-- SQL fige les membres après clôture ; Java vérifie leur complétude, les hashes, les opérations et le rapport.
REVOKE ALL ON ALL TABLES IN SCHEMA runtime, evaluation FROM PUBLIC;
REVOKE ALL ON ALL FUNCTIONS IN SCHEMA runtime FROM PUBLIC;
