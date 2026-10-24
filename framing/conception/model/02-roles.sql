-- Référence d'habilitations, à examiner puis appliquer une seule fois dans un cluster dédié.
-- Nécessite un opérateur habilité CREATE ROLE/ALTER OWNER ; aucun LOGIN/mot de passe créé ici.
-- Les rôles techniques ne sont pas des personas : le seul utilisateur métier reste Admin.
-- Ne jamais donner l'appartenance à sg_runtime_owner/sg_evaluation_owner aux identités applicatives.
BEGIN;
CREATE ROLE sg_runtime_owner NOLOGIN NOSUPERUSER NOCREATEDB NOCREATEROLE NOREPLICATION NOBYPASSRLS;
CREATE ROLE sg_evaluation_owner NOLOGIN NOSUPERUSER NOCREATEDB NOCREATEROLE NOREPLICATION NOBYPASSRLS;
CREATE ROLE sg_java NOLOGIN NOSUPERUSER NOCREATEDB NOCREATEROLE NOREPLICATION NOBYPASSRLS;
CREATE ROLE sg_seed_runtime NOLOGIN NOSUPERUSER NOCREATEDB NOCREATEROLE NOREPLICATION NOBYPASSRLS;
CREATE ROLE sg_seed_oracle NOLOGIN NOSUPERUSER NOCREATEDB NOCREATEROLE NOREPLICATION NOBYPASSRLS;
CREATE ROLE sg_evaluator NOLOGIN NOSUPERUSER NOCREATEDB NOCREATEROLE NOREPLICATION NOBYPASSRLS;
CREATE ROLE sg_dispatcher NOLOGIN NOSUPERUSER NOCREATEDB NOCREATEROLE NOREPLICATION NOBYPASSRLS;
ALTER SCHEMA runtime OWNER TO sg_runtime_owner;
ALTER SCHEMA evaluation OWNER TO sg_evaluation_owner;
DO $$
DECLARE r record;
BEGIN
 FOR r IN SELECT schemaname, tablename FROM pg_tables WHERE schemaname IN ('runtime','evaluation') LOOP
  EXECUTE format('ALTER TABLE %I.%I OWNER TO %I', r.schemaname, r.tablename,
   CASE WHEN r.schemaname = 'runtime' THEN 'sg_runtime_owner' ELSE 'sg_evaluation_owner' END);
 END LOOP;
END;
$$;
ALTER DOMAIN runtime.sha256 OWNER TO sg_runtime_owner;
ALTER DOMAIN runtime.rule_key OWNER TO sg_runtime_owner;
ALTER FUNCTION runtime.reject_mutation() OWNER TO sg_runtime_owner;
ALTER FUNCTION runtime.guard_source_insert() OWNER TO sg_runtime_owner;
ALTER FUNCTION runtime.guard_epoch_update() OWNER TO sg_runtime_owner;
ALTER FUNCTION runtime.guard_publication_seal() OWNER TO sg_runtime_owner;
ALTER FUNCTION runtime.guard_member_insert() OWNER TO sg_runtime_owner;
ALTER FUNCTION runtime.guard_active_publication() OWNER TO sg_runtime_owner;
REVOKE ALL ON SCHEMA runtime, evaluation FROM PUBLIC;
REVOKE ALL ON ALL TABLES IN SCHEMA runtime, evaluation FROM PUBLIC;
REVOKE ALL ON ALL FUNCTIONS IN SCHEMA runtime FROM PUBLIC;
REVOKE ALL ON SCHEMA evaluation FROM sg_java, sg_seed_runtime, sg_dispatcher;
REVOKE ALL ON ALL TABLES IN SCHEMA evaluation FROM sg_java, sg_seed_runtime, sg_dispatcher;
GRANT USAGE ON SCHEMA runtime TO sg_java, sg_seed_runtime, sg_dispatcher;
GRANT USAGE ON SCHEMA evaluation TO sg_seed_oracle, sg_evaluator;
GRANT SELECT ON ALL TABLES IN SCHEMA runtime TO sg_java;
GRANT SELECT ON ALL TABLES IN SCHEMA runtime TO sg_seed_runtime;
GRANT INSERT ON runtime.dataset, runtime.dataset_epoch, runtime.site, runtime.location, runtime.title,
 runtime.copy, runtime.source_event, runtime.system_state, runtime.system_state_event,
 runtime.inventory_scope, runtime.observation, runtime.source_availability, runtime.operator_note,
 runtime.source_case, runtime.gap_symptom, runtime.gap_case, runtime.rule, runtime.rule_version,
 runtime.rule_set_publication, runtime.rule_set_member, runtime.active_dataset,
 runtime.active_rule_set, runtime.execution_slot, runtime.sse_stream TO sg_seed_runtime;
GRANT UPDATE (sealed_at) ON runtime.dataset_epoch TO sg_seed_runtime;
GRANT UPDATE (sealed_at) ON runtime.rule_set_publication TO sg_seed_runtime, sg_java;
GRANT INSERT ON runtime.evidence_snapshot, runtime.evidence_reference, runtime.analysis_run,
 runtime.diagnostic_revision, runtime.diagnostic_acceptance, runtime.rule, runtime.rule_version,
 runtime.rule_change_proposal, runtime.proposal_revision, runtime.proposal_operation,
 runtime.validation_report, runtime.rule_set_publication, runtime.rule_set_member,
 runtime.expert_conversation, runtime.conversation_turn, runtime.chat_message, runtime.agent_mission,
 runtime.mission_attempt, runtime.call_reservation, runtime.analysis_batch, runtime.batch_item,
 runtime.command_receipt, runtime.outbox_event, runtime.inbox_event, runtime.progress_event TO sg_java;
GRANT UPDATE ON runtime.gap_case, runtime.analysis_run, runtime.rule_change_proposal,
 runtime.active_rule_set, runtime.expert_conversation, runtime.conversation_turn, runtime.agent_mission,
 runtime.mission_attempt, runtime.execution_slot, runtime.call_reservation, runtime.analysis_batch,
 runtime.batch_item, runtime.inbox_event, runtime.sse_stream TO sg_java;
GRANT DELETE ON runtime.progress_event TO sg_java;
GRANT SELECT ON runtime.outbox_event, runtime.active_dataset TO sg_dispatcher;
GRANT UPDATE (available_at, dispatch_attempts, dispatcher_token, dispatch_lease_until, published_at)
 ON runtime.outbox_event TO sg_dispatcher;
GRANT SELECT, INSERT ON evaluation.corpus, evaluation.case_expectation TO sg_seed_oracle;
GRANT SELECT ON ALL TABLES IN SCHEMA evaluation TO sg_evaluator;
GRANT INSERT ON evaluation.evaluation_run, evaluation.evaluation_result TO sg_evaluator;
GRANT UPDATE (completed_at, metrics) ON evaluation.evaluation_run TO sg_evaluator;
-- Les nouveaux objets n'héritent d'aucun droit public ; toute migration devra ajouter ses droits explicitement.
ALTER DEFAULT PRIVILEGES FOR ROLE sg_runtime_owner IN SCHEMA runtime REVOKE ALL ON TABLES FROM PUBLIC;
ALTER DEFAULT PRIVILEGES FOR ROLE sg_runtime_owner IN SCHEMA runtime REVOKE EXECUTE ON FUNCTIONS FROM PUBLIC;
ALTER DEFAULT PRIVILEGES FOR ROLE sg_evaluation_owner IN SCHEMA evaluation REVOKE ALL ON TABLES FROM PUBLIC;
ALTER DEFAULT PRIVILEGES FOR ROLE sg_evaluation_owner IN SCHEMA evaluation REVOKE EXECUTE ON FUNCTIONS FROM PUBLIC;
COMMIT;
