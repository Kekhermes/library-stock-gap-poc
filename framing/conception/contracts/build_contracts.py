"""Recrée uniquement les ressources de contrats et fixtures synthétiques locales.
Aucune implémentation serveur, aucun accès réseau. Python standard.
"""
import json
from pathlib import Path
from uuid import UUID

ROOT = Path(__file__).resolve().parent
DRAFT = 'https://json-schema.org/draft/2020-12/schema'
DEFS = {}
EXAMPLES = []

def write(path, data):
    target = ROOT / path
    target.parent.mkdir(parents=True, exist_ok=True)
    target.write_text(json.dumps(data, ensure_ascii=False, indent=2) + '\n', encoding='utf-8')

def obj(properties, required=None):
    return {'type': 'object', 'properties': properties, 'required': list(properties) if required is None else required, 'additionalProperties': False}

def arr(items, minimum=0, maximum=1000):
    return {'type': 'array', 'items': items, 'minItems': minimum, 'maxItems': maximum}

def enum(*values):
    return {'type': 'string', 'enum': list(values)}

def ref(name):
    return {'$ref': '#/$defs/' + name}

def ext(name):
    return {'$ref': 'common.schema.json#/$defs/' + name}

def nullable(schema):
    return {'anyOf': [schema, {'type': 'null'}]}

def definition(name, schema):
    DEFS[name] = schema
    return ref(name)

def uid(n):
    return str(UUID(int=n, version=4))

TEXT = {'type': 'string', 'minLength': 1, 'maxLength': 16000}
SHORT = {'type': 'string', 'minLength': 1, 'maxLength': 256}
BOOL = {'type': 'boolean'}
POS = {'type': 'integer', 'minimum': 1}
NAT = {'type': 'integer', 'minimum': 0}
UUID_SCHEMA = {'type': 'string', 'format': 'uuid'}
DATE = {'type': 'string', 'format': 'date-time', 'pattern': 'Z$'}
RULE_ID = {'anyOf': [{'const': 'R001'}, UUID_SCHEMA]}
SITE = enum('CENTRE', 'NORD', 'SUD')
HASH = {'type': 'string', 'pattern': '^[a-f0-9]{64}$'}
CURSOR = {'type': 'string', 'pattern': '^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}:[0-9]+$'}
TOOLS = ['READ_SNAPSHOT', 'READ_TIMELINE', 'READ_LOANS', 'READ_RETURNS', 'READ_RECEIPTS', 'READ_TRANSFERS', 'READ_REPAIRS', 'READ_INTEGRATIONS', 'READ_NOTES', 'READ_RULES', 'READ_PUBLICATIONS', 'READ_DIAGNOSTIC', 'READ_CONVERSATION', 'READ_PROPOSAL', 'PREVALIDATE']
ROLES = enum('INVESTIGATOR', 'RULE_AGENT')
CONTEXTS = enum('INVESTIGATION', 'RULE_CASE', 'RULE_HISTORY')
STATES = enum('QUEUED', 'RUNNING', 'CANCEL_REQUESTED', 'SUCCEEDED', 'FAILED', 'CANCELLED')
for name, schema in [('Uuid', UUID_SCHEMA), ('UtcTimestamp', DATE), ('RuleId', RULE_ID), ('SiteId', SITE), ('Version', POS), ('Cursor', CURSOR)]:
    definition(name, schema)
definition('EvidenceRef', obj({'source': enum('LOANS', 'RETURNS', 'RECEIPTS', 'TRANSFERS', 'REPAIRS', 'INTEGRATIONS', 'INVENTORY', 'SYSTEM_STATE', 'OPERATOR_NOTE'), 'recordId': UUID_SCHEMA, 'evidenceSnapshotId': UUID_SCHEMA, 'fieldPath': SHORT}))
definition('Finding', obj({'ruleId': RULE_ID, 'ruleVersion': POS, 'decisionId': SHORT, 'rowIds': arr(SHORT, 1, 100), 'subjectCopyId': UUID_SCHEMA, 'coveredSymptomIds': arr(UUID_SCHEMA, 1, 100), 'causeCode': SHORT, 'epistemicStatus': enum('SUPPORTED', 'HYPOTHESIS'), 'assertions': arr(TEXT, 1, 100), 'evidenceRefs': arr(ref('EvidenceRef'), 1, 100), 'contradictionRefs': arr(ref('EvidenceRef'), 0, 100), 'recommendedChecks': arr(TEXT, 0, 100), 'explanation': TEXT}, ['subjectCopyId', 'coveredSymptomIds', 'causeCode', 'epistemicStatus', 'assertions', 'evidenceRefs', 'contradictionRefs', 'recommendedChecks', 'explanation']))
DEFS['Finding']['dependentRequired'] = {key: [x for x in ['ruleId', 'ruleVersion', 'decisionId', 'rowIds'] if x != key] for key in ['ruleId', 'ruleVersion', 'decisionId', 'rowIds']}
definition('Diagnostic', obj({'conclusion': enum('SUPPORTED', 'PARTIAL', 'INDETERMINATE', 'CONFLICT'), 'findings': arr(ref('Finding'), 0, 100), 'missingEvidence': arr(obj({'source': SHORT, 'reason': TEXT, 'symptomIds': arr(UUID_SCHEMA, 0, 100)}), 0, 100), 'summary': TEXT}))
definition('Limits', obj({'maxLlmCalls': {'type': 'integer', 'minimum': 1, 'maximum': 20}, 'maxToolCalls': {'type': 'integer', 'minimum': 1, 'maximum': 100}, 'maxInputTokens': POS, 'maxOutputTokens': POS, 'maxDurationSeconds': {'type': 'integer', 'minimum': 1, 'maximum': 1800}, 'maxAttempts': {'type': 'integer', 'minimum': 1, 'maximum': 3}}))
definition('Usage', obj({'llmCalls': NAT, 'toolCalls': NAT, 'inputTokens': NAT, 'outputTokens': NAT, 'elapsedMilliseconds': NAT, 'accountingComplete': BOOL}))
definition('Fence', obj({'datasetEpoch': UUID_SCHEMA, 'attempt': POS, 'leaseToken': UUID_SCHEMA, 'fencingToken': POS}))
definition('DmnArtifact', obj({'mediaType': {'const': 'application/xml'}, 'content': {'type': 'string', 'minLength': 1, 'maxLength': 262144}, 'sha256': HASH, 'decisionId': SHORT, 'rowIds': arr(SHORT, 1, 100)}))
definition('RuleDraft', obj({'name': SHORT, 'causeCode': SHORT, 'epistemicStatus': enum('SUPPORTED', 'HYPOTHESIS'), 'scopeDescription': TEXT, 'requiredSources': arr(enum('LOANS', 'RETURNS', 'RECEIPTS', 'TRANSFERS', 'REPAIRS', 'INTEGRATIONS', 'INVENTORY'), 1, 7), 'conditionsExplanation': arr(TEXT, 1, 100), 'exceptions': arr(TEXT, 0, 100), 'recommendedChecks': arr(TEXT, 0, 100), 'dmn': ref('DmnArtifact')}))
origin = {'originDiagnosticId': UUID_SCHEMA, 'originEvidenceSnapshotId': UUID_SCHEMA, 'rationale': TEXT}
changes = []
for operation in ['CREATE', 'REVISE', 'RETIRE']:
    props = {'operation': {'const': operation}, **origin}
    if operation != 'CREATE':
        props.update({'targetRuleId': RULE_ID, 'expectedRuleVersion': POS})
    if operation != 'RETIRE':
        props['draft'] = ref('RuleDraft')
    changes.append(obj(props))
definition('RuleChange', {'oneOf': changes})
definition('PrevalidationIssue', obj({'code': enum('SCHEMA', 'XML', 'DMN_COMPILE', 'FEEL_UNSAFE', 'IMPORT_FORBIDDEN', 'MISSING_FACT', 'CONFLICT', 'COVERAGE_LOSS'), 'severity': enum('ERROR', 'WARNING'), 'path': SHORT, 'message': TEXT}))
definition('ImpactRow', obj({'caseId': UUID_SCHEMA, 'before': enum('SUPPORTED', 'PARTIAL', 'INDETERMINATE', 'CONFLICT'), 'after': enum('SUPPORTED', 'PARTIAL', 'INDETERMINATE', 'CONFLICT'), 'changedRuleIds': arr(RULE_ID, 0, 100), 'explanation': TEXT}))
definition('ValidationReport', obj({'reportId': UUID_SCHEMA, 'proposalId': UUID_SCHEMA, 'proposalRevision': POS, 'candidateSha256': HASH, 'ruleSetVersion': POS, 'corpusId': UUID_SCHEMA, 'corpusSha256': HASH, 'engineVersion': SHORT, 'createdAt': DATE, 'state': enum('QUEUED', 'RUNNING', 'PASSED', 'FAILED', 'CANCELLED'), 'publishable': BOOL, 'positivePassed': NAT, 'positiveFailed': NAT, 'negativePassed': NAT, 'negativeFailed': NAT, 'unknownHandled': NAT, 'issues': arr(ref('PrevalidationIssue')), 'impacts': arr(ref('ImpactRow'))}))
definition('Problem', obj({'type': {'type': 'string', 'format': 'uri'}, 'title': SHORT, 'status': {'type': 'integer', 'minimum': 400, 'maximum': 599}, 'code': enum('BAD_REQUEST', 'UNAUTHORIZED', 'FORBIDDEN', 'NOT_FOUND', 'VERSION_CONFLICT', 'STATE_CONFLICT', 'IDEMPOTENCY_CONFLICT', 'FENCED', 'EPOCH_MISMATCH', 'CURSOR_EXPIRED', 'LEASE_EXPIRED', 'INVALID_COMMAND', 'VALIDATION_FAILED', 'QUOTA_EXCEEDED', 'RATE_LIMITED', 'DEPENDENCY_UNAVAILABLE', 'INTERNAL_ERROR'), 'detail': TEXT, 'instance': {'type': 'string', 'format': 'uri-reference'}, 'correlationId': UUID_SCHEMA, 'retryable': BOOL, 'violations': arr(obj({'path': SHORT, 'code': SHORT, 'message': TEXT}), 0, 100), 'currentVersion': POS, 'retryAfterSeconds': NAT}, ['type', 'title', 'status', 'code', 'detail', 'instance', 'correlationId', 'retryable', 'violations']))

# Types source : huit faits normalisés, distincts des douze messages Kafka.
source_payloads = {
    'LoanOpened': {'loanId': UUID_SCHEMA, 'siteId': SITE, 'dueAt': DATE},
    'LoanClosed': {'loanId': UUID_SCHEMA, 'returnId': UUID_SCHEMA},
    'ReturnReceived': {'returnId': UUID_SCHEMA, 'loanId': UUID_SCHEMA, 'siteId': SITE, 'locationId': UUID_SCHEMA},
    'ReceiptReceived': {'receiptId': UUID_SCHEMA, 'siteId': SITE, 'locationId': UUID_SCHEMA},
    'TransferDispatched': {'transferId': UUID_SCHEMA, 'fromSiteId': SITE, 'toSiteId': SITE},
    'TransferReceived': {'transferId': UUID_SCHEMA, 'siteId': SITE, 'locationId': UUID_SCHEMA},
    'RepairStarted': {'repairId': UUID_SCHEMA, 'siteId': SITE, 'locationId': UUID_SCHEMA},
    'IntegrationProcessed': {'integrationId': UUID_SCHEMA, 'relatedSourceEventId': UUID_SCHEMA, 'status': enum('APPLIED', 'REJECTED'), 'projectionVersion': POS},
}
definition('SourceEvent', {'oneOf': [obj({'sourceEventId': UUID_SCHEMA, 'copyId': UUID_SCHEMA, 'eventType': {'const': t}, 'occurredAt': DATE, 'recordedAt': DATE, 'payload': obj(p)}) for t, p in source_payloads.items()]})
definition('SystemState', obj({'copyId': UUID_SCHEMA, 'status': enum('ON_SHELF', 'ON_LOAN', 'IN_TRANSIT', 'RECEIVING', 'IN_REPAIR', 'WITHDRAWN', 'NOT_REGISTERED'), 'siteId': SITE, 'locationId': nullable(UUID_SCHEMA), 'sourceEventIds': arr(UUID_SCHEMA)}))
definition('SourceAvailability', obj({'copyId': UUID_SCHEMA, 'source': enum('LOANS', 'RETURNS', 'RECEIPTS', 'TRANSFERS', 'REPAIRS', 'INTEGRATIONS', 'INVENTORY'), 'status': enum('COMPLETE', 'PARTIAL', 'UNAVAILABLE'), 'completeFrom': nullable(DATE), 'completeTo': nullable(DATE)}))
definition('Observation', obj({'observationId': UUID_SCHEMA, 'copyId': UUID_SCHEMA, 'locationId': UUID_SCHEMA, 'scopeId': UUID_SCHEMA, 'observedAt': DATE, 'quality': enum('VALID', 'AMBIGUOUS', 'REJECTED')}))
definition('InventoryScope', obj({'scopeId': UUID_SCHEMA, 'locationId': UUID_SCHEMA, 'status': enum('COMPLETE', 'INCOMPLETE', 'FAILED'), 'startedAt': DATE, 'closedAt': nullable(DATE)}))
definition('OperatorNote', obj({'noteId': UUID_SCHEMA, 'copyId': UUID_SCHEMA, 'text': TEXT, 'recordedAt': DATE}))
definition('Symptom', obj({'symptomId': UUID_SCHEMA, 'kind': enum('MISSING', 'UNEXPECTED_PRESENCE', 'LOCATION_MISMATCH', 'STATUS_MISMATCH', 'COUNT_UNCERTAIN'), 'expectedValue': nullable(TEXT), 'observedValue': nullable(TEXT)}))
definition('Case', obj({'caseId': UUID_SCHEMA, 'copyId': UUID_SCHEMA, 'titleId': UUID_SCHEMA, 'title': SHORT, 'siteId': SITE, 'inventoryReferenceTime': DATE, 'status': enum('A_ANALYSER', 'EN_ANALYSE', 'A_REVOIR', 'ACCEPTE', 'REJETE', 'INDETERMINE'), 'version': POS, 'diagnosticPath': enum('NONE', 'DETERMINISTIC', 'FALLBACK'), 'symptoms': arr(ref('Symptom'), 1, 100)}))
definition('EvidenceSnapshot', obj({'evidenceSnapshotId': UUID_SCHEMA, 'datasetEpoch': UUID_SCHEMA, 'capturedAt': DATE, 'caseId': UUID_SCHEMA, 'copyId': UUID_SCHEMA, 'systemStates': arr(ref('SystemState')), 'observations': arr(ref('Observation')), 'inventoryScopes': arr(ref('InventoryScope')), 'sourceAvailability': arr(ref('SourceAvailability')), 'sourceEvents': arr(ref('SourceEvent')), 'operatorNotes': arr(ref('OperatorNote')), 'sha256': HASH}))
definition('DiagnosticRecord', obj({'diagnosticId': UUID_SCHEMA, 'analysisId': UUID_SCHEMA, 'caseId': UUID_SCHEMA, 'evidenceSnapshotId': UUID_SCHEMA, 'ruleSetVersion': POS, 'version': POS, 'path': enum('DETERMINISTIC', 'FALLBACK'), 'reviewStatus': enum('PENDING', 'ACCEPTED', 'REJECTED'), 'diagnostic': ref('Diagnostic')}))
definition('Analysis', obj({'analysisId': UUID_SCHEMA, 'caseId': UUID_SCHEMA, 'version': POS, 'state': STATES, 'datasetEpoch': UUID_SCHEMA, 'evidenceSnapshotId': UUID_SCHEMA, 'ruleSetVersion': POS, 'diagnosticId': nullable(UUID_SCHEMA), 'missionId': nullable(UUID_SCHEMA), 'createdAt': DATE, 'updatedAt': DATE, 'usage': ref('Usage')}))
definition('Mission', obj({'missionId': UUID_SCHEMA, 'version': POS, 'state': STATES, 'datasetEpoch': UUID_SCHEMA, 'attempt': POS, 'fencingToken': POS, 'role': ROLES, 'evidenceSnapshotId': UUID_SCHEMA, 'ruleSetVersion': POS, 'cancelRequested': BOOL, 'leaseExpiresAt': nullable(DATE), 'limits': ref('Limits'), 'usage': ref('Usage')}))
definition('RuleVersion', obj({'ruleId': RULE_ID, 'ruleVersion': POS, 'active': BOOL, 'originDiagnosticId': nullable(UUID_SCHEMA), 'createdAt': DATE, 'draft': ref('RuleDraft'), 'author': enum('BOOTSTRAP', 'RULE_AGENT')}))
definition('Proposal', obj({'proposalId': UUID_SCHEMA, 'proposalRevision': POS, 'version': POS, 'state': enum('DRAFT', 'VALIDATING', 'READY', 'NON_PUBLISHABLE', 'SUPERSEDED', 'PUBLISHED', 'REJECTED'), 'expectedRuleSetVersion': POS, 'conversationId': UUID_SCHEMA, 'change': ref('RuleChange'), 'reportId': nullable(UUID_SCHEMA), 'nonPublishableReasons': arr(TEXT, 0, 100), 'createdAt': DATE}))
definition('Publication', obj({'publicationId': UUID_SCHEMA, 'ruleSetVersion': POS, 'previousRuleSetVersion': POS, 'publishedAt': DATE, 'operation': enum('CREATE', 'REVISE', 'RETIRE'), 'proposalId': nullable(UUID_SCHEMA), 'proposalRevision': nullable(POS), 'reportId': UUID_SCHEMA, 'activeRules': arr(obj({'ruleId': RULE_ID, 'ruleVersion': POS}), 0, 1000), 'actor': {'const': 'ADMIN'}, 'sha256': HASH}))
definition('Conversation', obj({'conversationId': UUID_SCHEMA, 'version': POS, 'role': ROLES, 'originDiagnosticId': UUID_SCHEMA, 'proposalId': nullable(UUID_SCHEMA), 'activeTurnId': nullable(UUID_SCHEMA)}))
definition('ChatMessage', obj({'messageId': UUID_SCHEMA, 'conversationId': UUID_SCHEMA, 'turnId': UUID_SCHEMA, 'sequence': POS, 'author': enum('ADMIN', 'INVESTIGATOR', 'RULE_AGENT'), 'text': TEXT, 'state': enum('ACCEPTED', 'STREAMING', 'COMPLETE', 'INTERRUPTED'), 'evidenceRefs': arr(ref('EvidenceRef')), 'createdAt': DATE}))
definition('Batch', obj({'batchId': UUID_SCHEMA, 'version': POS, 'state': STATES, 'items': arr(obj({'caseId': UUID_SCHEMA, 'analysisId': nullable(UUID_SCHEMA), 'state': STATES, 'error': nullable(ref('Problem'))}), 1, 20), 'createdAt': DATE}))

# REST requests : bornées, commandes et outils séparés.
definition('AnalyzeRequest', obj({'expectedCaseVersion': POS, 'expectedRuleSetVersion': POS}))
definition('BatchRequest', obj({'cases': arr(obj({'caseId': UUID_SCHEMA, 'expectedCaseVersion': POS}), 1, 20), 'expectedRuleSetVersion': POS}))
definition('CancelRequest', obj({'expectedVersion': POS, 'reason': TEXT}))
definition('AcceptDiagnosticRequest', obj({'expectedDiagnosticVersion': POS, 'expectedAnalysisVersion': POS, 'expectedRuleSetVersion': POS, 'comment': TEXT}))
definition('RejectDiagnosticRequest', obj({'expectedDiagnosticVersion': POS, 'expectedAnalysisVersion': POS, 'reason': TEXT}))
definition('MessageRequest', obj({'expectedConversationVersion': POS, 'text': {'type': 'string', 'minLength': 1, 'maxLength': 8000}}))
definition('RevisionRequest', obj({'expectedRuleVersion': POS, 'expectedRuleSetVersion': POS, 'originDiagnosticId': UUID_SCHEMA, 'instruction': TEXT}))
definition('TestProposalRequest', obj({'expectedProposalRevision': POS, 'expectedProposalVersion': POS, 'expectedRuleSetVersion': POS, 'corpusId': UUID_SCHEMA}))
definition('PublishRequest', obj({'expectedProposalRevision': POS, 'expectedProposalVersion': POS, 'expectedRuleSetVersion': POS, 'reportId': UUID_SCHEMA, 'candidateSha256': HASH, 'confirmation': {'const': 'APPROVE_AND_PUBLISH'}}))
definition('RetirementPreviewRequest', obj({'expectedRuleVersion': POS, 'expectedRuleSetVersion': POS, 'reason': TEXT}))
definition('RetireRequest', obj({'expectedRuleVersion': POS, 'expectedRuleSetVersion': POS, 'previewId': UUID_SCHEMA, 'reportId': UUID_SCHEMA, 'reason': TEXT, 'confirmation': {'const': 'RETIRE_RULE'}}))
definition('RetirementPreview', obj({'previewId': UUID_SCHEMA, 'ruleId': RULE_ID, 'ruleVersion': POS, 'ruleSetVersion': POS, 'reportId': UUID_SCHEMA, 'expiresAt': DATE, 'lostCoverageCaseIds': arr(UUID_SCHEMA), 'issues': arr(ref('PrevalidationIssue'))}))
definition('AcceptedAnalysis', obj({'analysisId': UUID_SCHEMA, 'state': {'const': 'QUEUED'}, 'version': POS}))
definition('AcceptedBatch', obj({'batchId': UUID_SCHEMA, 'state': {'const': 'QUEUED'}, 'version': POS}))
definition('AcceptedMission', obj({'missionId': UUID_SCHEMA, 'conversationId': UUID_SCHEMA, 'turnId': nullable(UUID_SCHEMA), 'state': {'const': 'QUEUED'}, 'version': POS}))
definition('AcceptedValidation', obj({'reportId': UUID_SCHEMA, 'proposalId': UUID_SCHEMA, 'proposalRevision': POS, 'state': {'const': 'QUEUED'}}))
definition('DiagnosticAcceptance', obj({'diagnostic': ref('DiagnosticRecord'), 'synthesisMissionId': nullable(UUID_SCHEMA), 'synthesisStatus': enum('QUEUED', 'NOT_APPLICABLE', 'NON_FORMALIZABLE')}))
definition('ClaimRequest', obj({'datasetEpoch': UUID_SCHEMA, 'commandEventId': UUID_SCHEMA, 'attempt': POS, 'workerId': UUID_SCHEMA, 'requestedLeaseSeconds': {'type': 'integer', 'minimum': 15, 'maximum': 60}}))
definition('ClaimResponse', {'oneOf': [obj({'disposition': {'const': 'CLAIMED'}, 'fence': ref('Fence'), 'leaseExpiresAt': DATE, 'heartbeatIntervalSeconds': {'const': 10}, 'mission': ref('Mission')}), obj({'disposition': enum('TERMINAL', 'BUSY', 'STALE_EPOCH', 'STALE_ATTEMPT'), 'missionState': STATES, 'retryAfterSeconds': NAT})]})
definition('HeartbeatRequest', obj({'fence': ref('Fence'), 'lastProducedSequence': NAT}))
definition('HeartbeatResponse', obj({'leaseExpiresAt': DATE, 'cancelRequested': BOOL, 'remaining': ref('Limits')}))
definition('AuthorizeCallRequest', obj({'fence': ref('Fence'), 'callId': UUID_SCHEMA, 'kind': enum('LLM', 'TOOL'), 'tool': enum(*TOOLS), 'maxInputTokens': NAT, 'maxOutputTokens': NAT}, ['fence', 'callId', 'kind', 'maxInputTokens', 'maxOutputTokens']))
DEFS['AuthorizeCallRequest']['allOf'] = [{'if': {'properties': {'kind': {'const': 'TOOL'}}}, 'then': {'required': ['tool'], 'properties': {'maxInputTokens': {'const': 0}, 'maxOutputTokens': {'const': 0}}}, 'else': {'not': {'required': ['tool']}}}]
definition('CallAuthorization', obj({'authorizationId': UUID_SCHEMA, 'callId': UUID_SCHEMA, 'expiresAt': DATE, 'remaining': ref('Limits')}))
definition('CallCompletionRequest', obj({'fence': ref('Fence'), 'authorizationId': UUID_SCHEMA, 'callId': UUID_SCHEMA, 'outcome': enum('SUCCEEDED', 'FAILED', 'UNKNOWN'), 'inputTokens': NAT, 'outputTokens': NAT, 'elapsedMilliseconds': NAT}))
definition('CallCompletion', obj({'callId': UUID_SCHEMA, 'recorded': {'const': True}, 'usage': ref('Usage')}))
definition('PrevalidationRequest', obj({'change': ref('RuleChange'), 'expectedRuleSetVersion': POS}))
definition('PrevalidationResult', obj({'prevalidationId': UUID_SCHEMA, 'candidateSha256': HASH, 'compiles': BOOL, 'issues': arr(ref('PrevalidationIssue')), 'publishable': {'const': False}, 'explanation': TEXT}))

# Un diagnostic déterministe doit exposer la provenance DMN de chaque constat.
DEFS['DiagnosticRecord']['allOf'] = [{'if': {'properties': {'path': {'const': 'DETERMINISTIC'}}}, 'then': {'properties': {'diagnostic': {'properties': {'findings': {'items': {'required': ['ruleId', 'ruleVersion', 'decisionId', 'rowIds']}}}}}}}]

# Paginations : curseur opaque UUID, aucun query/SQL libre.
for name in ['Case', 'Analysis', 'Mission', 'RuleVersion', 'Proposal', 'Publication', 'ChatMessage', 'SourceEvent', 'OperatorNote', 'DiagnosticRecord']:
    definition(name + 'Page', obj({'items': arr(ref(name), 0, 100), 'nextCursor': nullable(UUID_SCHEMA)}))
definition('CaseDossier', obj({'case': ref('Case'), 'snapshot': ref('EvidenceSnapshot'), 'analysisIds': arr(UUID_SCHEMA), 'conversationIds': arr(UUID_SCHEMA)}))
definition('Overview', obj({'datasetEpoch': UUID_SCHEMA, 'ruleSetVersion': POS, 'casesToAnalyze': NAT, 'casesToReview': NAT, 'activeMissions': NAT, 'proposalsToReview': NAT, 'deterministicAnalyses': NAT, 'totalAnalyses': NAT}))
definition('LocalStatus', obj({'datasetId': UUID_SCHEMA, 'datasetEpoch': UUID_SCHEMA, 'schemaVersion': {'const': '1.0.0'}, 'openAiConfigured': BOOL, 'services': arr(obj({'name': enum('BACKEND', 'POSTGRES', 'REDPANDA', 'AGENTS', 'DMN'), 'status': enum('UP', 'DOWN', 'DEGRADED'), 'detail': SHORT}), 5, 5), 'limits': ref('Limits')}))
definition('Comparison', obj({'leftId': UUID_SCHEMA, 'rightId': UUID_SCHEMA, 'impacts': arr(ref('ImpactRow')), 'addedRuleIds': arr(RULE_ID), 'revisedRuleIds': arr(RULE_ID), 'retiredRuleIds': arr(RULE_ID)}))
definition('ProposalComparison', obj({'proposalId': UUID_SCHEMA, 'leftRevision': POS, 'rightRevision': POS, 'before': ref('RuleChange'), 'after': ref('RuleChange')}))
definition('DemoState', obj({'datasetId': UUID_SCHEMA, 'compatible': BOOL, 'reason': TEXT, 'steps': arr(obj({'step': {'type': 'integer', 'minimum': 1, 'maximum': 7}, 'state': enum('LOCKED', 'READY', 'DONE', 'INVALIDATED'), 'label': SHORT, 'caseId': nullable(UUID_SCHEMA), 'analysisId': nullable(UUID_SCHEMA), 'publicationId': nullable(UUID_SCHEMA)}), 7, 7)}))
definition('SyncSnapshot', obj({'datasetEpoch': UUID_SCHEMA, 'cursor': CURSOR, 'capturedAt': DATE, 'overview': ref('Overview'), 'cases': arr(ref('Case'), 0, 10000), 'analyses': arr(ref('Analysis'), 0, 10000), 'missions': arr(ref('Mission'), 0, 10000), 'proposals': arr(ref('Proposal'), 0, 10000), 'publications': arr(ref('Publication'), 0, 10000), 'conversations': arr(ref('Conversation'), 0, 10000), 'messages': arr(ref('ChatMessage'), 0, 100000)}))

# Kafka : union discriminée stricte avec contexte conditionnel.
command_types = ['InvestigationRequested', 'RuleSynthesisRequested', 'RuleRevisionRequested', 'ExpertTurnRequested', 'CancellationRequested']
event_types = ['MissionStarted', 'ToolObservationRecorded', 'TextChunkProduced', 'DiagnosticProposed', 'RuleChangeProposed', 'RuleChangeNotProposed', 'MissionFailed', 'MissionCancelled']
all_types = command_types + event_types
command_common = {'contextKind': CONTEXTS, 'role': ROLES, 'promptVersion': {'const': '1.0.0'}, 'limits': ext('Limits'), 'allowedTools': arr(enum(*TOOLS), 1, len(TOOLS)), 'deadlineAt': DATE}
worker_common = {'contextKind': CONTEXTS, 'leaseToken': UUID_SCHEMA, 'fencingToken': POS}
payloads = {
    'InvestigationRequested': obj({**command_common, 'objective': TEXT}),
    'RuleSynthesisRequested': obj({**command_common, 'originDiagnosticId': UUID_SCHEMA, 'originEvidenceSnapshotId': UUID_SCHEMA}),
    'RuleRevisionRequested': obj({**command_common, 'originDiagnosticId': UUID_SCHEMA, 'originEvidenceSnapshotId': UUID_SCHEMA, 'targetRuleId': RULE_ID, 'expectedRuleVersion': POS, 'instruction': TEXT}),
    'ExpertTurnRequested': obj({**command_common, 'messageId': UUID_SCHEMA, 'text': TEXT, 'originDiagnosticId': UUID_SCHEMA}),
    'CancellationRequested': obj({'contextKind': CONTEXTS, 'reason': TEXT, 'requestedBy': enum('ADMIN', 'BACKEND'), 'expectedMissionVersion': POS, 'fencingToken': POS}),
    'MissionStarted': obj({**worker_common, 'workerId': UUID_SCHEMA, 'leaseExpiresAt': DATE}),
    'ToolObservationRecorded': obj({**worker_common, 'callId': UUID_SCHEMA, 'tool': enum(*TOOLS), 'outcome': enum('SUCCEEDED', 'FAILED'), 'evidenceRefs': arr(ext('EvidenceRef')), 'summary': TEXT, 'elapsedMilliseconds': NAT}),
    'TextChunkProduced': obj({**worker_common, 'messageId': UUID_SCHEMA, 'chunkIndex': NAT, 'text': TEXT, 'isFinal': BOOL, 'usage': ext('Usage')}),
    'DiagnosticProposed': obj({**worker_common, 'diagnosticId': UUID_SCHEMA, 'diagnostic': ext('Diagnostic'), 'usage': ext('Usage')}),
    'RuleChangeProposed': obj({**worker_common, 'change': ext('RuleChange'), 'formalizable': BOOL, 'limitations': arr(TEXT, 0, 100), 'usage': ext('Usage')}),
    'RuleChangeNotProposed': obj({**worker_common, 'reason': enum('NO_CHANGE', 'INSUFFICIENT_EVIDENCE', 'UNSUPPORTED_CAPABILITY'), 'explanation': TEXT, 'missingFacts': arr(obj({'fact': SHORT, 'reason': TEXT}), 0, 100), 'originDiagnosticId': UUID_SCHEMA, 'originEvidenceSnapshotId': UUID_SCHEMA, 'usage': ext('Usage')}),
    'MissionFailed': obj({**worker_common, 'code': enum('QUOTA_EXCEEDED', 'TIMEOUT', 'PROVIDER_UNAVAILABLE', 'INVALID_OUTPUT', 'TOOL_DENIED', 'INTERNAL_ERROR'), 'retryable': BOOL, 'message': TEXT, 'retryAfterSeconds': NAT, 'usage': ext('Usage')}),
    'MissionCancelled': obj({**worker_common, 'reason': TEXT, 'lastCompletedCallId': nullable(UUID_SCHEMA), 'usage': ext('Usage')}),
}
context_fields = {'caseId': UUID_SCHEMA, 'analysisId': UUID_SCHEMA, 'conversationId': UUID_SCHEMA, 'turnId': UUID_SCHEMA, 'proposalId': UUID_SCHEMA, 'proposalRevision': POS}
base = {'eventId': UUID_SCHEMA, 'eventType': enum(*all_types), 'schemaVersion': {'const': '1.0.0'}, 'occurredAt': DATE, 'datasetEpoch': UUID_SCHEMA, 'correlationId': UUID_SCHEMA, 'causationId': UUID_SCHEMA, 'missionId': UUID_SCHEMA, 'attempt': POS, 'ruleSetVersion': POS, 'evidenceSnapshotId': UUID_SCHEMA, 'sequence': POS}
message_defs = {}
for typ in all_types:
    payload = payloads[typ]
    allowed_context = {'InvestigationRequested': ['INVESTIGATION'], 'RuleSynthesisRequested': ['RULE_CASE'], 'RuleRevisionRequested': ['RULE_HISTORY'], 'DiagnosticProposed': ['INVESTIGATION'], 'RuleChangeProposed': ['RULE_CASE', 'RULE_HISTORY'], 'RuleChangeNotProposed': ['RULE_CASE', 'RULE_HISTORY']}.get(typ, ['INVESTIGATION', 'RULE_CASE', 'RULE_HISTORY'])
    payload['properties']['contextKind'] = enum(*allowed_context)
    if typ == 'InvestigationRequested':
        payload['properties']['role'] = {'const': 'INVESTIGATOR'}
    elif typ in ['RuleSynthesisRequested', 'RuleRevisionRequested']:
        payload['properties']['role'] = {'const': 'RULE_AGENT'}
    schema = obj({**base, **context_fields, 'eventType': {'const': typ}, 'payload': payload}, list(base) + ['payload'])
    schema['dependentRequired'] = {'conversationId': ['turnId'], 'turnId': ['conversationId']}
    schema['allOf'] = []
    for kind, required, forbidden in [('INVESTIGATION', ['caseId', 'analysisId'], ['proposalId', 'proposalRevision']), ('RULE_CASE', ['caseId', 'analysisId', 'proposalId', 'proposalRevision'], []), ('RULE_HISTORY', ['proposalId', 'proposalRevision'], ['caseId', 'analysisId'])]:
        schema['allOf'].append({'if': {'properties': {'payload': {'properties': {'contextKind': {'const': kind}}}}}, 'then': {'required': required, 'properties': {key: False for key in forbidden}}})
    if typ in ['ExpertTurnRequested', 'TextChunkProduced']:
        schema['required'] += ['conversationId', 'turnId']
    if typ == 'ExpertTurnRequested':
        schema['allOf'].append({'if': {'properties': {'payload': {'properties': {'contextKind': {'const': 'INVESTIGATION'}}}}}, 'then': {'properties': {'payload': {'properties': {'role': {'const': 'INVESTIGATOR'}}}}}, 'else': {'properties': {'payload': {'properties': {'role': {'const': 'RULE_AGENT'}}}}}})
    message_defs[typ] = schema
write('schemas/kafka-message.schema.json', {'$schema': DRAFT, 'title': 'KafkaAgentMessage', 'description': 'Union discriminée eventType. Fixtures synthétiques, schemaVersion unique 1.0.0.', 'oneOf': [{'$ref': '#/$defs/' + t} for t in all_types], '$defs': message_defs})
write('schemas/dead-letter.schema.json', {'$schema': DRAFT, **obj({'deadLetterId': UUID_SCHEMA, 'schemaVersion': {'const': '1.0.0'}, 'missionId': UUID_SCHEMA, 'datasetEpoch': UUID_SCHEMA, 'sourceTopic': enum('stock-gap.agent.commands.v1', 'stock-gap.agent.events.v1'), 'sourcePartition': NAT, 'sourceOffset': NAT, 'eventId': nullable(UUID_SCHEMA), 'failedAt': DATE, 'reason': enum('MALFORMED_JSON', 'SCHEMA_INVALID', 'UNSUPPORTED_VERSION', 'POISON_MESSAGE', 'RETRIES_EXHAUSTED'), 'deliveryCount': POS, 'payloadSha256': HASH, 'errorSummary': TEXT, 'quarantineId': UUID_SCHEMA})})

# Fixtures de messages exactes ; UUID fixes sans prétendre à un runtime vivant.
T = '2026-01-15T12:00:00Z'
LIMITS = {'maxLlmCalls': 4, 'maxToolCalls': 20, 'maxInputTokens': 16000, 'maxOutputTokens': 4000, 'maxDurationSeconds': 300, 'maxAttempts': 3}
USAGE = {'llmCalls': 1, 'toolCalls': 1, 'inputTokens': 800, 'outputTokens': 200, 'elapsedMilliseconds': 1800, 'accountingComplete': True}
EVIDENCE = {'source': 'LOANS', 'recordId': uid(70), 'evidenceSnapshotId': uid(8), 'fieldPath': '/payload/loanId'}
FINDING = {'subjectCopyId': uid(60), 'coveredSymptomIds': [uid(61)], 'causeCode': 'LOAN_NOT_PROJECTED', 'epistemicStatus': 'SUPPORTED', 'assertions': ['Un prêt ouvert est présent dans la chronologie figée.'], 'evidenceRefs': [EVIDENCE], 'contradictionRefs': [], 'recommendedChecks': ['Contrôler la projection du prêt.'], 'explanation': 'La projection indique un exemplaire en rayon malgré le prêt constaté.'}
DIAGNOSTIC = {'conclusion': 'SUPPORTED', 'findings': [FINDING], 'missingEvidence': [], 'summary': 'Écart compatible avec un prêt non projeté.'}
DMN = '<definitions xmlns="https://www.omg.org/spec/DMN/20230324/MODEL/" id="fixture" name="SyntheticFixture" namespace="urn:synthetic:contract"><decision id="d1" name="example"><literalExpression><text>true</text></literalExpression></decision></definitions>'
import hashlib
DRAFT_RULE = {'name': 'Fixture de contrat — prêt', 'causeCode': 'LOAN_NOT_PROJECTED', 'epistemicStatus': 'SUPPORTED', 'scopeDescription': 'Fixture syntaxique non publiable sans compilation et tests Java.', 'requiredSources': ['LOANS'], 'conditionsExplanation': ['Exemple technique, pas une règle prête à activer.'], 'exceptions': [], 'recommendedChecks': ['Contrôler les corrélations.'], 'dmn': {'mediaType': 'application/xml', 'content': DMN, 'sha256': hashlib.sha256(DMN.encode()).hexdigest(), 'decisionId': 'd1', 'rowIds': ['fixture-row']}}
CHANGE = {'operation': 'CREATE', 'originDiagnosticId': uid(90), 'originEvidenceSnapshotId': uid(8), 'rationale': 'Proposition synthétique issue du diagnostic accepté.', 'draft': DRAFT_RULE}
fixtures = {}
for i, typ in enumerate(all_types):
    kind = 'RULE_HISTORY' if typ == 'RuleRevisionRequested' else 'RULE_CASE' if typ in ['RuleSynthesisRequested', 'RuleChangeProposed', 'RuleChangeNotProposed', 'ExpertTurnRequested'] else 'INVESTIGATION'
    envelope = {'eventId': uid(100 + i), 'eventType': typ, 'schemaVersion': '1.0.0', 'occurredAt': T, 'datasetEpoch': uid(2), 'correlationId': uid(3), 'causationId': uid(4), 'missionId': uid(5), 'attempt': 1, 'ruleSetVersion': 1, 'evidenceSnapshotId': uid(8), 'sequence': i + 1}
    if kind != 'RULE_HISTORY':
        envelope.update(caseId=uid(10), analysisId=uid(11))
    if kind != 'INVESTIGATION':
        envelope.update(proposalId=uid(12), proposalRevision=1)
    if typ in ['ExpertTurnRequested', 'TextChunkProduced']:
        envelope.update(conversationId=uid(13), turnId=uid(14))
    p = {'contextKind': kind}
    if typ in command_types[:-1]:
        p.update(role='INVESTIGATOR' if kind == 'INVESTIGATION' else 'RULE_AGENT', promptVersion='1.0.0', limits=LIMITS, allowedTools=['READ_SNAPSHOT', 'READ_TIMELINE'], deadlineAt='2026-01-15T12:05:00Z')
    if typ in event_types:
        p.update(leaseToken=uid(20), fencingToken=1)
    specifics = {
        'InvestigationRequested': {'objective': 'Examiner les symptômes sans corriger le stock.'},
        'RuleSynthesisRequested': {'originDiagnosticId': uid(90), 'originEvidenceSnapshotId': uid(8)},
        'RuleRevisionRequested': {'originDiagnosticId': uid(90), 'originEvidenceSnapshotId': uid(8), 'targetRuleId': 'R001', 'expectedRuleVersion': 1, 'instruction': 'Examiner une généralisation prudente.'},
        'ExpertTurnRequested': {'messageId': uid(30), 'text': 'Quels contre-exemples ont été pris en compte ?', 'originDiagnosticId': uid(90)},
        'CancellationRequested': {'reason': 'Arrêt demandé par Admin.', 'requestedBy': 'ADMIN', 'expectedMissionVersion': 2, 'fencingToken': 2},
        'MissionStarted': {'workerId': uid(40), 'leaseExpiresAt': '2026-01-15T12:01:00Z'},
        'ToolObservationRecorded': {'callId': uid(41), 'tool': 'READ_LOANS', 'outcome': 'SUCCEEDED', 'evidenceRefs': [EVIDENCE], 'summary': 'Un prêt ouvert a été lu.', 'elapsedMilliseconds': 12},
        'TextChunkProduced': {'messageId': uid(30), 'chunkIndex': 0, 'text': 'Le prêt est cité dans le dossier de preuves.', 'isFinal': True, 'usage': USAGE},
        'DiagnosticProposed': {'diagnosticId': uid(90), 'diagnostic': DIAGNOSTIC, 'usage': USAGE},
        'RuleChangeProposed': {'change': CHANGE, 'formalizable': True, 'limitations': ['Fixture de contrat uniquement.'], 'usage': USAGE},
        'RuleChangeNotProposed': {'reason': 'INSUFFICIENT_EVIDENCE', 'explanation': 'Une note libre ne fournit pas de fait déterministe exploitable.', 'missingFacts': [{'fact': 'validatedLoanCorrelation', 'reason': 'Corrélation non disponible dans le snapshot.'}], 'originDiagnosticId': uid(90), 'originEvidenceSnapshotId': uid(8), 'usage': USAGE},
        'MissionFailed': {'code': 'PROVIDER_UNAVAILABLE', 'retryable': True, 'message': 'Indisponibilité simulée.', 'retryAfterSeconds': 5, 'usage': USAGE},
        'MissionCancelled': {'reason': 'Annulation confirmée.', 'lastCompletedCallId': uid(41), 'usage': USAGE},
    }
    envelope['payload'] = {**p, **specifics[typ]}
    fixtures[typ] = envelope
    write('examples/kafka/' + typ + '.json', envelope)
    EXAMPLES.append({'path': 'examples/kafka/' + typ + '.json', 'schema': 'kafka-message.schema.json'})

# SSE : notifications durables et signaux de resynchronisation non durables.
sse_payloads = {
    'AnalysisChanged': obj({'analysisId': UUID_SCHEMA, 'caseId': UUID_SCHEMA, 'version': POS, 'state': STATES}),
    'MissionChanged': obj({'missionId': UUID_SCHEMA, 'version': POS, 'state': STATES, 'attempt': POS, 'cancelRequested': BOOL}),
    'DiagnosticReady': obj({'diagnosticId': UUID_SCHEMA, 'analysisId': UUID_SCHEMA, 'caseId': UUID_SCHEMA, 'version': POS, 'conclusion': enum('SUPPORTED', 'PARTIAL', 'INDETERMINATE', 'CONFLICT')}),
    'ProposalChanged': obj({'proposalId': UUID_SCHEMA, 'proposalRevision': POS, 'version': POS, 'state': enum('DRAFT', 'VALIDATING', 'READY', 'NON_PUBLISHABLE', 'SUPERSEDED', 'PUBLISHED', 'REJECTED')}),
    'ValidationCompleted': obj({'reportId': UUID_SCHEMA, 'proposalId': UUID_SCHEMA, 'proposalRevision': POS, 'state': enum('PASSED', 'FAILED', 'CANCELLED'), 'publishable': BOOL}),
    'PublicationActivated': obj({'publicationId': UUID_SCHEMA, 'ruleSetVersion': POS, 'operation': enum('CREATE', 'REVISE', 'RETIRE')}),
    'ConversationMessageChanged': obj({'conversationId': UUID_SCHEMA, 'messageId': UUID_SCHEMA, 'turnId': UUID_SCHEMA, 'version': POS, 'state': enum('STREAMING', 'COMPLETE', 'INTERRUPTED'), 'text': TEXT}),
    'BatchChanged': obj({'batchId': UUID_SCHEMA, 'version': POS, 'state': STATES, 'completed': NAT, 'total': {'type': 'integer', 'minimum': 1, 'maximum': 20}}),
    'DatasetReset': obj({'previousDatasetEpoch': UUID_SCHEMA, 'newDatasetEpoch': UUID_SCHEMA, 'action': {'const': 'FETCH_SYNC_SNAPSHOT'}}),
    'ResyncRequired': obj({'reason': enum('CURSOR_EXPIRED', 'EPOCH_MISMATCH', 'SLOW_CONSUMER'), 'action': {'const': 'FETCH_SYNC_SNAPSHOT'}}),
}
sse_defs = {}
for typ, data_schema in sse_payloads.items():
    props = {'eventType': {'const': typ}, 'schemaVersion': {'const': '1.0.0'}, 'datasetEpoch': UUID_SCHEMA, 'occurredAt': DATE, 'data': data_schema}
    if typ not in ['DatasetReset', 'ResyncRequired']:
        props['cursor'] = CURSOR
    sse_defs[typ] = obj(props)
write('schemas/sse-event.schema.json', {'$schema': DRAFT, 'title': 'SseEvent', 'oneOf': [{'$ref': '#/$defs/' + t} for t in sse_defs], '$defs': sse_defs})
sse_data = {
    'AnalysisChanged': {'analysisId': uid(11), 'caseId': uid(10), 'version': 2, 'state': 'RUNNING'},
    'MissionChanged': {'missionId': uid(5), 'version': 2, 'state': 'RUNNING', 'attempt': 1, 'cancelRequested': False},
    'DiagnosticReady': {'diagnosticId': uid(90), 'analysisId': uid(11), 'caseId': uid(10), 'version': 1, 'conclusion': 'SUPPORTED'},
    'ProposalChanged': {'proposalId': uid(12), 'proposalRevision': 1, 'version': 2, 'state': 'VALIDATING'},
    'ValidationCompleted': {'reportId': uid(91), 'proposalId': uid(12), 'proposalRevision': 1, 'state': 'PASSED', 'publishable': True},
    'PublicationActivated': {'publicationId': uid(92), 'ruleSetVersion': 2, 'operation': 'CREATE'},
    'ConversationMessageChanged': {'conversationId': uid(13), 'messageId': uid(30), 'turnId': uid(14), 'version': 2, 'state': 'COMPLETE', 'text': 'Réponse de fixture, aucun appel réel.'},
    'BatchChanged': {'batchId': uid(93), 'version': 2, 'state': 'RUNNING', 'completed': 1, 'total': 2},
    'DatasetReset': {'previousDatasetEpoch': uid(1), 'newDatasetEpoch': uid(2), 'action': 'FETCH_SYNC_SNAPSHOT'},
    'ResyncRequired': {'reason': 'CURSOR_EXPIRED', 'action': 'FETCH_SYNC_SNAPSHOT'},
}
wire = [': fixture synthétique — aucun serveur interrogé\n\n']
for index, (typ, data) in enumerate(sse_data.items(), 1):
    example = {'eventType': typ, 'schemaVersion': '1.0.0', 'datasetEpoch': uid(2), 'occurredAt': T, 'data': data}
    if typ not in ['DatasetReset', 'ResyncRequired']:
        example['cursor'] = uid(2) + ':' + str(index)
    write('examples/sse/' + typ + '.json', example)
    EXAMPLES.append({'path': 'examples/sse/' + typ + '.json', 'schema': 'sse-event.schema.json'})
    wire.append(('id: ' + example['cursor'] + '\n' if 'cursor' in example else '') + 'event: ' + typ + '\ndata: ' + json.dumps(example, ensure_ascii=False, separators=(',', ':')) + '\n\n')
(ROOT / 'examples/sse/stream.txt').write_text(''.join(wire) + ': heartbeat\n\n', encoding='utf-8')

# Remaining counters may be zero even though configured maxima are positive.
definition('RemainingBudget', obj({key: NAT for key in LIMITS}))
DEFS['HeartbeatResponse']['properties']['remaining'] = ref('RemainingBudget')
DEFS['CallAuthorization']['properties']['remaining'] = ref('RemainingBudget')
definition('RuleSetView', obj({'ruleSetVersion': POS, 'publicationId': nullable(UUID_SCHEMA), 'rules': arr(ref('RuleVersion'), 0, 1000)}))

# OpenAPI 3.1 : JSON Schema 2020-12, refs exclusivement internes au dossier.
def api_ref(name):
    return {'$ref': './schemas/common.schema.json#/$defs/' + name}

def parameter(name, schema, location='query', required=False, description=''):
    return {'name': name, 'in': location, 'required': required, 'schema': schema, 'description': description or name}

PAGE = [parameter('cursor', UUID_SCHEMA), parameter('limit', {'type': 'integer', 'minimum': 1, 'maximum': 100, 'default': 50})]
FENCE_HEADERS = [parameter('X-Dataset-Epoch', UUID_SCHEMA, 'header', True), parameter('X-Attempt', POS, 'header', True), parameter('X-Lease-Token', UUID_SCHEMA, 'header', True), parameter('X-Fencing-Token', POS, 'header', True), parameter('X-Call-Authorization', UUID_SCHEMA, 'header', True)]
paths = {}
operation_index = []

def route(method, path, operation_id, response, request=None, status='200', params=None, tool=False, summary=None):
    import re
    internal = path.startswith('/internal/')
    parameters = list(params or [])
    for name in re.findall(r'{([^}]+)}', path):
        schema = RULE_ID if name == 'ruleId' else POS if name in ['ruleVersion', 'ruleSetVersion', 'proposalRevision'] else UUID_SCHEMA
        parameters.append(parameter(name, schema, 'path', True))
    if method != 'get':
        parameters.append(parameter('Idempotency-Key', UUID_SCHEMA, 'header', True, 'Clé stable pour rejouer exactement la même commande ; conflit si corps différent.'))
        if not internal:
            parameters.append(parameter('X-Dataset-Epoch', UUID_SCHEMA, 'header', True))
    if tool:
        parameters += FENCE_HEADERS
    description = summary or operation_id
    operation = {'operationId': operation_id, 'summary': description, 'tags': ['Internal tools' if internal else 'Admin local'], 'x-access-boundary': 'WORKER_MISSION_ONLY' if internal else 'LOCAL_ADMIN_ONLY', 'security': [{'WorkerToken': []}] if internal else [], 'parameters': parameters, 'responses': {status: {'description': 'Accepté, pas encore terminé' if status == '202' else 'Résultat durable ou lecture du snapshot', 'content': {'application/json': {'schema': api_ref(response)}}}}, 'description': 'Lecture bornée au snapshot et au périmètre de la mission, après contrôle du bail et consommation de l’autorisation de cet outil.' if tool else description}
    if request:
        operation['requestBody'] = {'required': True, 'content': {'application/json': {'schema': api_ref(request)}}}
    if method != 'get':
        operation['responses'][status]['headers'] = {'Idempotency-Replayed': {'description': 'Réponse déjà enregistrée pour cette clé.', 'schema': BOOL}}
    for code in ['400', '401', '403', '404', '409', '410', '422', '429', '500', '503']:
        operation['responses'][code] = {'$ref': '#/components/responses/Error' + code}
    paths.setdefault(path, {})[method] = operation
    operation_index.append({'method': method.upper(), 'path': path, 'operationId': operation_id, 'request': request, 'response': response, 'status': status, 'internal': internal})

route('get', '/api/overview', 'get_overview', 'Overview')
route('get', '/api/local-status', 'get_local_status', 'LocalStatus')
route('get', '/api/cases', 'list_cases', 'CasePage', params=PAGE + [parameter('siteId', SITE), parameter('titleId', UUID_SCHEMA), parameter('symptomKind', DEFS['Symptom']['properties']['kind']), parameter('status', DEFS['Case']['properties']['status']), parameter('diagnosticPath', DEFS['Case']['properties']['diagnosticPath'])])
route('get', '/api/cases/{caseId}', 'get_case_dossier', 'CaseDossier')
route('get', '/api/cases/{caseId}/analyses', 'list_case_analyses', 'AnalysisPage', params=PAGE)
route('post', '/api/cases/{caseId}/analyses', 'start_analysis', 'AcceptedAnalysis', 'AnalyzeRequest', '202')
route('get', '/api/analyses/{analysisId}', 'get_analysis', 'Analysis')
route('post', '/api/analyses/{analysisId}/cancellation', 'cancel_analysis', 'Analysis', 'CancelRequest')
route('get', '/api/analyses/{analysisId}/snapshot', 'get_analysis_snapshot', 'EvidenceSnapshot')
route('get', '/api/analyses/{analysisId}/diagnostic', 'get_analysis_diagnostic', 'DiagnosticRecord')
route('get', '/api/analysis-comparisons', 'compare_analyses', 'Comparison', params=[parameter('leftId', UUID_SCHEMA, required=True), parameter('rightId', UUID_SCHEMA, required=True)])
route('post', '/api/batches', 'start_batch', 'AcceptedBatch', 'BatchRequest', '202')
route('get', '/api/batches/{batchId}', 'get_batch', 'Batch')
route('post', '/api/batches/{batchId}/cancellation', 'cancel_batch', 'Batch', 'CancelRequest')
route('get', '/api/missions/{missionId}', 'get_mission', 'Mission')
route('post', '/api/missions/{missionId}/cancellation', 'cancel_mission', 'Mission', 'CancelRequest')
route('post', '/api/diagnostics/{diagnosticId}/acceptance', 'accept_diagnostic', 'DiagnosticAcceptance', 'AcceptDiagnosticRequest')
route('post', '/api/diagnostics/{diagnosticId}/rejection', 'reject_diagnostic', 'DiagnosticRecord', 'RejectDiagnosticRequest')
route('get', '/api/rules', 'list_rules', 'RuleVersionPage', params=PAGE)
route('get', '/api/rules/{ruleId}', 'get_rule', 'RuleVersion')
route('get', '/api/rules/{ruleId}/versions', 'list_rule_versions', 'RuleVersionPage', params=PAGE)
route('get', '/api/rules/{ruleId}/versions/{ruleVersion}', 'get_rule_version', 'RuleVersion')
route('post', '/api/rules/{ruleId}/revision-requests', 'request_rule_revision', 'AcceptedMission', 'RevisionRequest', '202')
route('post', '/api/rules/{ruleId}/retirement-previews', 'preview_rule_retirement', 'RetirementPreview', 'RetirementPreviewRequest')
route('post', '/api/rules/{ruleId}/retirement', 'retire_rule', 'Publication', 'RetireRequest')
route('get', '/api/proposals', 'list_proposals', 'ProposalPage', params=PAGE)
route('get', '/api/proposals/{proposalId}', 'get_proposal', 'Proposal')
route('get', '/api/proposals/{proposalId}/revisions/{proposalRevision}', 'get_proposal_revision', 'Proposal')
route('get', '/api/proposals/{proposalId}/comparison', 'compare_proposal_revisions', 'ProposalComparison', params=[parameter('leftRevision', POS, required=True), parameter('rightRevision', POS, required=True)])
route('post', '/api/proposals/{proposalId}/validations', 'test_proposal', 'AcceptedValidation', 'TestProposalRequest', '202')
route('get', '/api/validation-reports/{reportId}', 'get_validation_report', 'ValidationReport')
route('post', '/api/proposals/{proposalId}/publication', 'approve_and_publish', 'Publication', 'PublishRequest')
route('get', '/api/publications', 'list_publications', 'PublicationPage', params=PAGE)
route('get', '/api/publications/{publicationId}', 'get_publication', 'Publication')
route('get', '/api/publication-comparisons', 'compare_publications', 'Comparison', params=[parameter('leftId', UUID_SCHEMA, required=True), parameter('rightId', UUID_SCHEMA, required=True)])
route('get', '/api/conversations/{conversationId}', 'get_conversation', 'Conversation')
route('get', '/api/conversations/{conversationId}/messages', 'list_messages', 'ChatMessagePage', params=PAGE)
route('post', '/api/conversations/{conversationId}/messages', 'send_expert_message', 'AcceptedMission', 'MessageRequest', '202')
route('get', '/api/demo', 'get_guided_demo', 'DemoState', summary='Lire les préconditions et étapes réellement observées ; les actions réutilisent analyse/acceptation/publication, aucun résultat préfabriqué.')
route('get', '/api/sync-snapshot', 'get_sync_snapshot', 'SyncSnapshot', summary='Snapshot transactionnel complet et curseur du même état ; point de départ REST/SSE sans course.')
route('get', '/api/events', 'subscribe_events', 'SyncSnapshot', params=[parameter('after', CURSOR), parameter('Last-Event-ID', CURSOR, 'header')])
paths['/api/events']['get']['responses']['200'] = {'description': 'SSE UTF-8. Le JSON de chaque data est un SseEvent ; id égale cursor et event égale eventType. Contrôles sans id. Voir protocole-sse.md.', 'headers': {'Cache-Control': {'schema': {'const': 'no-cache'}}, 'X-Accel-Buffering': {'schema': {'const': 'no'}}}, 'content': {'text/event-stream': {'schema': {'type': 'string'}, 'x-event-schema': {'$ref': './schemas/sse-event.schema.json'}}}}
paths['/api/events']['get']['description'] = 'after obligatoire au premier branchement ; Last-Event-ID prime à la reconnexion. Valeurs contradictoires acceptées seulement si même epoch et Last-Event-ID >= after. Curseur futur : 409. Curseur expiré : 410 avant ouverture, sinon ResyncRequired sans id et fermeture.'
route('post', '/internal/missions/{missionId}/claim', 'claim_mission', 'ClaimResponse', 'ClaimRequest')
route('post', '/internal/missions/{missionId}/heartbeat', 'heartbeat_mission', 'HeartbeatResponse', 'HeartbeatRequest')
route('post', '/internal/missions/{missionId}/authorize-call', 'authorize_call', 'CallAuthorization', 'AuthorizeCallRequest')
route('post', '/internal/missions/{missionId}/complete-call', 'complete_call', 'CallCompletion', 'CallCompletionRequest')
base_tool = '/internal/missions/{missionId}/snapshots/{evidenceSnapshotId}'
route('get', base_tool, 'get_case_snapshot', 'EvidenceSnapshot', tool=True)
route('get', base_tool + '/source-events', 'get_source_events', 'SourceEventPage', params=PAGE + [parameter('eventType', enum(*source_payloads)), parameter('from', DATE), parameter('to', DATE)], tool=True)
for source in ['loans', 'returns', 'receipts', 'transfers', 'repairs', 'integrations']:
    route('get', base_tool + '/' + source, 'get_' + source, 'SourceEventPage', params=PAGE, tool=True)
route('get', base_tool + '/operator-notes', 'get_operator_notes', 'OperatorNotePage', params=PAGE, tool=True)
route('get', '/internal/missions/{missionId}/rule-set', 'get_rule_set', 'RuleSetView', tool=True)
route('get', '/internal/missions/{missionId}/rules/{ruleId}/history', 'get_rule_history', 'RuleVersionPage', params=PAGE, tool=True)
route('get', '/internal/missions/{missionId}/publications', 'get_publication_history', 'PublicationPage', params=PAGE, tool=True)
route('get', '/internal/missions/{missionId}/origin-diagnostic', 'get_origin_diagnostic', 'DiagnosticRecord', tool=True)
route('get', '/internal/missions/{missionId}/conversation', 'get_mission_conversation', 'ChatMessagePage', params=PAGE, tool=True)
route('get', '/internal/missions/{missionId}/proposal', 'get_mission_proposal', 'Proposal', tool=True)
route('post', '/internal/missions/{missionId}/prevalidation', 'prevalidate_rule_change', 'PrevalidationResult', 'PrevalidationRequest', tool=True)

error_descriptions = {'400': 'JSON/paramètre/curseur mal formé', '401': 'Identité technique worker absente ou invalide', '403': 'Frontière Admin/worker ou capacité/snapshot interdit', '404': 'Ressource absente du périmètre accessible', '409': 'Version/état/idempotence/fence/époque en conflit', '410': 'Lease expirée ou curseur SSE hors rétention', '422': 'Commande non recevable ou validation métier en échec', '429': 'Quota ou limitation atteints', '500': 'Erreur interne sans exposition de secret', '503': 'Dépendance nécessaire indisponible'}
error_responses = {('Error' + code): {'description': text, 'headers': {'Retry-After': {'description': 'Secondes ; présent seulement si une reprise est autorisée.', 'schema': NAT}}, 'content': {'application/problem+json': {'schema': api_ref('Problem')}}} for code, text in error_descriptions.items()}
write('openapi.json', {'openapi': '3.1.0', 'jsonSchemaDialect': DRAFT, 'info': {'title': 'Stock gap POC — REST et outils bornés', 'version': '1.0.0', 'description': 'Contrat de conception français ; pas de serveur déployé. Aucune API de création/édition manuelle des règles. Autorités Admin et worker distinctes même sans login utilisateur.'}, 'servers': [{'url': 'http://localhost:8080', 'description': 'Adresse illustrative du proxy local ; routage interne séparé obligatoire.'}], 'tags': [{'name': 'Admin local'}, {'name': 'Internal tools'}], 'paths': paths, 'components': {'securitySchemes': {'WorkerToken': {'type': 'http', 'scheme': 'bearer', 'description': 'Identité technique opaque interne. Jamais exposée au navigateur, ne confère aucun droit Admin.'}}, 'responses': error_responses}})
write('operation-index.json', {'operations': operation_index})

# AsyncAPI 3.0.0, Multi Format Schema Objects pour le draft 2020-12.
message_components = {}
for typ in all_types:
    message_components[typ] = {'name': typ, 'title': typ, 'contentType': 'application/json', 'correlationId': {'location': '$message.payload#/correlationId'}, 'payload': {'schemaFormat': 'application/schema+json;version=draft-2020-12', 'schema': {'$ref': './schemas/kafka-message.schema.json#/$defs/' + typ}}, 'examples': [{'name': 'synthetic-fixture', 'summary': 'Fixture de conception synthétique, aucune exécution réelle.', 'payload': fixtures[typ]}], 'bindings': {'kafka': {'key': UUID_SCHEMA, 'bindingVersion': '0.5.0'}}}
message_components['DeadLetter'] = {'name': 'DeadLetter', 'contentType': 'application/json', 'payload': {'schemaFormat': 'application/schema+json;version=draft-2020-12', 'schema': {'$ref': './schemas/dead-letter.schema.json'}}}
channels = {}
for channel, address, types in [('commands', 'stock-gap.agent.commands.v1', command_types), ('events', 'stock-gap.agent.events.v1', event_types), ('deadLetter', 'stock-gap.agent.dead-letter.v1', ['DeadLetter'])]:
    channels[channel] = {'address': address, 'description': 'Clé Kafka missionId (UUID), JSON UTF-8. Voir kafka-lifecycle.md.', 'messages': {typ: {'$ref': '#/components/messages/' + typ} for typ in types}, 'bindings': {'kafka': {'partitions': 3, 'replicas': 1, 'topicConfiguration': {'cleanup.policy': ['delete'], 'retention.ms': 604800000 if channel == 'deadLetter' else 86400000}, 'bindingVersion': '0.5.0'}}}
operations = {}
for name, action, channel, types in [('dispatchAgentCommand', 'send', 'commands', command_types), ('adoptAgentEvent', 'receive', 'events', event_types), ('quarantineInvalidEvent', 'send', 'deadLetter', ['DeadLetter'])]:
    operations[name] = {'action': action, 'channel': {'$ref': '#/channels/' + channel}, 'messages': [{'$ref': '#/channels/' + channel + '/messages/' + typ} for typ in types]}
write('asyncapi.json', {'asyncapi': '3.0.0', 'info': {'title': 'Stock gap — missions agents, point de vue Java', 'version': '1.0.0', 'description': 'AsyncAPI 3.0.0 ; payloads JSON Schema 2020-12 via Multi Format Schema Objects. Vérification structurelle locale, pas de validation officielle AsyncAPI.'}, 'defaultContentType': 'application/json', 'servers': {'localRedpanda': {'host': 'redpanda:9092', 'protocol': 'kafka', 'description': 'Réseau Compose interne illustratif'}}, 'channels': channels, 'operations': operations, 'components': {'messages': message_components}, 'x-consumer-groups': {'commands': 'stock-gap-agents-v1', 'events': 'stock-gap-backend-v1', 'deadLetter': 'stock-gap-dead-letter-inspector-v1'}})

# Fixtures REST des commandes sensibles et réponses ; schémas fermés.
FENCE = {'datasetEpoch': uid(2), 'attempt': 1, 'leaseToken': uid(20), 'fencingToken': 1}
rest_examples = {
    'analyze-request': ('AnalyzeRequest', {'expectedCaseVersion': 1, 'expectedRuleSetVersion': 1}),
    'analyze-accepted': ('AcceptedAnalysis', {'analysisId': uid(11), 'state': 'QUEUED', 'version': 1}),
    'batch-request': ('BatchRequest', {'cases': [{'caseId': uid(10), 'expectedCaseVersion': 1}], 'expectedRuleSetVersion': 1}),
    'cancel-request': ('CancelRequest', {'expectedVersion': 2, 'reason': 'Arrêt de la fixture.'}),
    'accept-diagnostic-request': ('AcceptDiagnosticRequest', {'expectedDiagnosticVersion': 1, 'expectedAnalysisVersion': 2, 'expectedRuleSetVersion': 1, 'comment': 'Preuves revues dans la fixture.'}),
    'reject-diagnostic-request': ('RejectDiagnosticRequest', {'expectedDiagnosticVersion': 1, 'expectedAnalysisVersion': 2, 'reason': 'Preuve insuffisante dans la fixture.'}),
    'message-request': ('MessageRequest', {'expectedConversationVersion': 1, 'text': 'Explique les contre-exemples.'}),
    'revision-request': ('RevisionRequest', {'expectedRuleVersion': 1, 'expectedRuleSetVersion': 1, 'originDiagnosticId': uid(90), 'instruction': 'Examiner les exceptions.'}),
    'test-proposal-request': ('TestProposalRequest', {'expectedProposalRevision': 1, 'expectedProposalVersion': 2, 'expectedRuleSetVersion': 1, 'corpusId': uid(94)}),
    'publish-request': ('PublishRequest', {'expectedProposalRevision': 1, 'expectedProposalVersion': 2, 'expectedRuleSetVersion': 1, 'reportId': uid(91), 'candidateSha256': 'a' * 64, 'confirmation': 'APPROVE_AND_PUBLISH'}),
    'retirement-preview-request': ('RetirementPreviewRequest', {'expectedRuleVersion': 1, 'expectedRuleSetVersion': 1, 'reason': 'Retrait illustratif.'}),
    'retire-request': ('RetireRequest', {'expectedRuleVersion': 1, 'expectedRuleSetVersion': 1, 'previewId': uid(95), 'reportId': uid(91), 'reason': 'Retrait illustratif.', 'confirmation': 'RETIRE_RULE'}),
    'claim-request': ('ClaimRequest', {'datasetEpoch': uid(2), 'commandEventId': uid(100), 'attempt': 1, 'workerId': uid(40), 'requestedLeaseSeconds': 60}),
    'heartbeat-request': ('HeartbeatRequest', {'fence': FENCE, 'lastProducedSequence': 1}),
    'authorize-llm-request': ('AuthorizeCallRequest', {'fence': FENCE, 'callId': uid(41), 'kind': 'LLM', 'maxInputTokens': 2000, 'maxOutputTokens': 1000}),
    'authorize-tool-request': ('AuthorizeCallRequest', {'fence': FENCE, 'callId': uid(42), 'kind': 'TOOL', 'tool': 'READ_TIMELINE', 'maxInputTokens': 0, 'maxOutputTokens': 0}),
    'complete-call-request': ('CallCompletionRequest', {'fence': FENCE, 'authorizationId': uid(43), 'callId': uid(41), 'outcome': 'SUCCEEDED', 'inputTokens': 800, 'outputTokens': 200, 'elapsedMilliseconds': 1800}),
    'prevalidate-request': ('PrevalidationRequest', {'change': CHANGE, 'expectedRuleSetVersion': 1}),
    'prevalidate-result': ('PrevalidationResult', {'prevalidationId': uid(96), 'candidateSha256': 'a' * 64, 'compiles': False, 'issues': [{'code': 'DMN_COMPILE', 'severity': 'ERROR', 'path': '/draft/dmn', 'message': 'Fixture non compilée, rejet illustratif.'}], 'publishable': False, 'explanation': 'Ceci n’est pas un rapport Java réel.'}),
    'diagnostic-record': ('DiagnosticRecord', {'diagnosticId': uid(90), 'analysisId': uid(11), 'caseId': uid(10), 'evidenceSnapshotId': uid(8), 'ruleSetVersion': 1, 'version': 1, 'path': 'FALLBACK', 'reviewStatus': 'ACCEPTED', 'diagnostic': DIAGNOSTIC}),
    'rule-revise': ('RuleChange', {**CHANGE, 'operation': 'REVISE', 'targetRuleId': 'R001', 'expectedRuleVersion': 1}),
    'rule-retire': ('RuleChange', {'operation': 'RETIRE', 'targetRuleId': 'R001', 'expectedRuleVersion': 1, **{key: CHANGE[key] for key in origin}}),
    'problem-version-conflict': ('Problem', {'type': 'urn:stock-gap:error:version-conflict', 'title': 'Version périmée', 'status': 409, 'code': 'VERSION_CONFLICT', 'detail': 'Relire la proposition et relancer ses validations.', 'instance': '/api/proposals/' + uid(12) + '/publication', 'correlationId': uid(3), 'retryable': False, 'violations': [], 'currentVersion': 3}),
}
for name, (schema_name, data) in rest_examples.items():
    write('examples/rest/' + name + '.json', data)
    EXAMPLES.append({'path': 'examples/rest/' + name + '.json', 'schema': 'common.schema.json#/$defs/' + schema_name})
dead_letter = {'deadLetterId': uid(97), 'schemaVersion': '1.0.0', 'missionId': uid(5), 'datasetEpoch': uid(2), 'sourceTopic': 'stock-gap.agent.events.v1', 'sourcePartition': 0, 'sourceOffset': 42, 'eventId': uid(100), 'failedAt': T, 'reason': 'SCHEMA_INVALID', 'deliveryCount': 1, 'payloadSha256': 'b' * 64, 'errorSummary': 'Fixture : version de contrat refusée.', 'quarantineId': uid(98)}
write('examples/dead-letter.json', dead_letter)
EXAMPLES.append({'path': 'examples/dead-letter.json', 'schema': 'dead-letter.schema.json'})
write('schemas/common.schema.json', {'$schema': DRAFT, 'title': 'SharedFiniteContractDefinitions', '$defs': DEFS})
write('examples/manifest.json', {'synthetic': True, 'description': 'Fixtures hors ligne indépendantes, pas une séquence métier ; aucun résultat API réel, aucune époque de dataset vivant.', 'examples': EXAMPLES})
