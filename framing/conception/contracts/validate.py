"""Validation locale de ressources de conception, jamais de réseau."""
from pathlib import Path
import json
from jsonschema import Draft202012Validator, FormatChecker, ValidationError
from referencing import Registry, Resource

class ContractValidator:
    def __init__(self, root):
        self.root = Path(root).resolve()
        self._registry = None

    def validate_schema(self, schema):
        Draft202012Validator.check_schema(schema)

    def validate(self, schema_name, instance):
        schema_root = self.root / 'schemas'
        path = (schema_root / schema_name.split('#')[0]).resolve()
        if not path.is_relative_to(schema_root.resolve()):
            raise ValueError('Schema hors du répertoire contracts/schemas')
        if self._registry is None:
            resources = []
            for file in schema_root.glob('*.json'):
                schema = json.loads(file.read_text())
                self.validate_schema(schema)
                resources.append((file.resolve().as_uri(), Resource.from_contents(schema)))
            self._registry = Registry().with_resources(resources)
        registry = self._registry
        uri = path.as_uri()
        if '#' in schema_name:
            uri += '#' + schema_name.split('#', 1)[1]
        Draft202012Validator({'$ref': uri}, registry=registry, format_checker=FormatChecker()).validate(instance)

    def validate_examples(self):
        manifest = json.loads((self.root / 'examples/manifest.json').read_text())
        assert manifest['synthetic'] is True
        seen = set()
        for entry in manifest['examples']:
            assert entry['path'] not in seen, 'Fixture déclarée deux fois'
            seen.add(entry['path'])
            path = (self.root / entry['path']).resolve()
            assert path.is_relative_to(self.root / 'examples')
            self.validate(entry['schema'], json.loads(path.read_text()))
        actual = {str(path.relative_to(self.root)) for path in (self.root / 'examples').rglob('*.json') if path.name != 'manifest.json'}
        assert seen == actual, 'Fixture absente du manifeste ou référence périmée'
        return len(seen)

    def check_documents(self):
        """Checks spécifiques, NON équivalents aux validateurs officiels OAS/AsyncAPI."""
        import re
        from urllib.parse import unquote
        docs = {path.resolve(): json.loads(path.read_text()) for path in self.root.rglob('*.json') if 'test-results' not in path.parts}
        references = 0
        def walk(node, source):
            nonlocal references
            if isinstance(node, dict):
                if '$ref' in node:
                    reference = node['$ref']
                    filename, _, fragment = reference.partition('#')
                    assert '://' not in filename, 'Référence distante interdite'
                    target = (source.parent / filename).resolve() if filename else source
                    assert target.is_relative_to(self.root), 'Référence hors contracts'
                    value = docs[target]
                    if fragment:
                        assert fragment.startswith('/'), 'Seuls les pointeurs JSON locaux sont utilisés'
                        for token in unquote(fragment[1:]).split('/'):
                            value = value[token.replace('~1', '/').replace('~0', '~')]
                    references += 1
                for child in node.values():
                    walk(child, source)
            elif isinstance(node, list):
                for child in node:
                    walk(child, source)
        for path, doc in docs.items():
            walk(doc, path)
            if path.parent.name == 'schemas':
                self.validate_schema(doc)
        api = docs[self.root / 'openapi.json']
        assert api['openapi'] == '3.1.0'
        assert api['jsonSchemaDialect'] == 'https://json-schema.org/draft/2020-12/schema'
        operation_ids = set()
        for path, item in api['paths'].items():
            for method, operation in item.items():
                assert method in ['get', 'post'], 'Aucune édition manuelle ni endpoint non décrit'
                oid = operation['operationId']
                assert oid not in operation_ids
                operation_ids.add(oid)
                assert operation['responses']
                names = {p['name'] for p in operation['parameters'] if p['in'] == 'path' and p['required']}
                assert names == set(re.findall(r'{([^}]+)}', path))
                if method == 'post':
                    assert operation['requestBody']['required']
                    assert any(p['name'] == 'Idempotency-Key' and p['required'] for p in operation['parameters'])
                if path.startswith('/internal/'):
                    assert operation['security'] == [{'WorkerToken': []}]
                    assert operation['x-access-boundary'] == 'WORKER_MISSION_ONLY'
                    assert not any(term in oid for term in ['approve', 'publish', 'retire_rule'])
                else:
                    assert operation['x-access-boundary'] == 'LOCAL_ADMIN_ONLY'
        assert not any(method != 'get' for method in api['paths']['/api/rules'])
        asyncapi = docs[self.root / 'asyncapi.json']
        assert asyncapi['asyncapi'] == '3.0.0'
        expected = {'InvestigationRequested', 'RuleSynthesisRequested', 'RuleRevisionRequested', 'ExpertTurnRequested', 'CancellationRequested', 'MissionStarted', 'ToolObservationRecorded', 'TextChunkProduced', 'DiagnosticProposed', 'RuleChangeProposed', 'RuleChangeNotProposed', 'MissionFailed', 'MissionCancelled'}
        kafka = docs[self.root / 'schemas/kafka-message.schema.json']
        assert set(kafka['$defs']) == expected
        assert len(kafka['oneOf']) == len(expected)
        assert set(asyncapi['components']['messages']) == expected | {'DeadLetter'}
        for message in asyncapi['components']['messages'].values():
            assert message['payload']['schemaFormat'] == 'application/schema+json;version=draft-2020-12'
            for example in message.get('examples', []):
                self.validate('kafka-message.schema.json', example['payload'])
        assert set(asyncapi['channels']) == {'commands', 'events', 'deadLetter'}
        assert set(asyncapi['channels']['commands']['messages']) | set(asyncapi['channels']['events']['messages']) == expected
        return {'openapiOperations': len(operation_ids), 'kafkaTypes': len(expected), 'schemaDocuments': len(list((self.root / 'schemas').glob('*.json'))), 'resolvedLocalReferences': references, 'openapiValidation': 'structural-custom-not-official', 'asyncapiValidation': 'structural-custom-not-official'}

if __name__ == '__main__':
    import importlib.util
    import sys
    validator = ContractValidator(Path(__file__).resolve().parent)
    try:
        result = validator.check_documents()
        result['validatedSyntheticExamples'] = validator.validate_examples()
        result['jsonSchemaValidation'] = 'Draft202012Validator + FormatChecker'
        result['officialOpenapiValidatorInstalled'] = importlib.util.find_spec('openapi_spec_validator') is not None
        result['officialAsyncapiValidatorUsed'] = False
        result['status'] = 'PASS'
        output = validator.root / 'test-results/validation.json'
        output.parent.mkdir(exist_ok=True)
        output.write_text(json.dumps(result, ensure_ascii=False, indent=2) + '\n')
        print(json.dumps(result, ensure_ascii=False, indent=2))
    except Exception as exc:
        print(f'FAIL: {type(exc).__name__}: {exc}', file=sys.stderr)
        raise SystemExit(1)
