#!/usr/bin/env python3
"""Vérification statique reproductible ; ne se connecte jamais à PostgreSQL.

--dictionary et --er rendent sur stdout les documents dérivés du DDL.
La dépendance pglast utilise le parseur PostgreSQL, pas un moteur de base.
"""
import argparse
import json
import re
from pathlib import Path

from pglast import ast, enums, get_postgresql_version, parse_plpgsql, parse_sql
from pglast.stream import RawStream

ROOT = Path(__file__).resolve().parent


def names(items):
    return tuple(x.sval for x in (items or ()))


def relation(node):
    return f"{node.schemaname}.{node.relname}"


def load():
    text = (ROOT / "01-schema.sql").read_text()
    statements = parse_sql(text)
    tables = {}
    fks = []
    indexes = {}
    for raw in statements:
        stmt = raw.stmt
        if isinstance(stmt, ast.CreateStmt):
            table = relation(stmt.relation)
            columns = {c.colname: c for c in stmt.tableElts if isinstance(c, ast.ColumnDef)}
            constraints = [(c, None) for c in stmt.tableElts if isinstance(c, ast.Constraint)]
            constraints += [(c, name) for name, col in columns.items() for c in (col.constraints or ())]
            tables[table] = {"columns": columns, "constraints": constraints, "unique": [], "primary": (), "notnull": set()}
        elif isinstance(stmt, ast.AlterTableStmt):
            for cmd in stmt.cmds:
                if isinstance(cmd.def_, ast.Constraint):
                    tables[relation(stmt.relation)]["constraints"].append((cmd.def_, None))
        elif isinstance(stmt, ast.IndexStmt):
            indexes.setdefault(relation(stmt.relation), []).append(RawStream()(stmt))
    for table, meta in tables.items():
        for c, column in meta["constraints"]:
            if c.contype in (enums.ConstrType.CONSTR_PRIMARY, enums.ConstrType.CONSTR_UNIQUE):
                key = (column,) if column else names(c.keys)
                meta["unique"].append(key)
                if c.contype == enums.ConstrType.CONSTR_PRIMARY:
                    meta["primary"] = key
                    meta["notnull"].update(key)
            elif c.contype == enums.ConstrType.CONSTR_NOTNULL:
                meta["notnull"].add(column)
            elif c.contype == enums.ConstrType.CONSTR_FOREIGN:
                local = (column,) if column else names(c.fk_attrs)
                fks.append((table, local, relation(c.pktable), names(c.pk_attrs), c))
    return text, statements, tables, fks, indexes


def check():
    text, stmts, tables, fks, indexes = load()
    errors = []
    for table, meta in tables.items():
        if not meta["primary"]:
            errors.append(f"Clé primaire absente : {table}")
        for key in meta["unique"]:
            if not set(key) <= set(meta["columns"]):
                errors.append(f"Colonne de clé inconnue : {table} {key}")
    for source, local, target, remote, _ in fks:
        if target not in tables:
            errors.append(f"Table FK absente : {target}")
            continue
        if remote not in tables[target]["unique"]:
            errors.append(f"Cible FK non unique : {target} {remote}")
        if len(local) != len(remote):
            errors.append(f"Arité FK : {source} {local}")
        for a, b in zip(local, remote):
            if a not in tables[source]["columns"] or b not in tables[target]["columns"]:
                errors.append(f"Colonne FK absente : {source}.{a} -> {target}.{b}")
                continue
            ta = names(tables[source]["columns"][a].typeName.names)
            tb = names(tables[target]["columns"][b].typeName.names)
            if ta != tb:
                errors.append(f"Types FK incompatibles : {source}.{a} {ta} != {target}.{b} {tb}")
        if source.startswith("runtime.") and target.startswith("evaluation."):
            errors.append("Lien runtime vers oracle interdit")
        if source.startswith("runtime.") and target.startswith("runtime.") and "dataset_epoch" in tables[source]["columns"] and "dataset_epoch" in tables[target]["columns"] and "dataset_epoch" not in local:
            errors.append(f"FK sans barrière d'époque : {source} -> {target}")
    ddl_metadata = re.findall(r"-- @table ([^\n]+)\nCREATE TABLE ([\w.]+) \((.*?)\n\);", text, re.S)
    documented = {}
    for label, table, body in ddl_metadata:
        documented[table] = set()
        for line in body.splitlines():
            match = re.match(r" ([a-z][a-z_0-9]*)\s+.*? -- (.+)", line)
            if match:
                documented[table].add(match[1])
        if documented[table] != set(tables[table]["columns"]):
            errors.append(f"Dictionnaire incomplet : {table} {set(tables[table]['columns']) - documented[table]}")
    if set(documented) != set(tables):
        errors.append("Tables sans métadonnées de dictionnaire")
    role_text = (ROOT / "02-roles.sql").read_text()
    roles_stmts = parse_sql(role_text)
    plpgsql_count = 0
    for source in (text, role_text):
        for block in re.findall(r"CREATE FUNCTION .*?\$\$;|DO \$\$.*?\$\$;", source, re.S):
            parse_plpgsql(block)
            plpgsql_count += 1
    if "GRANT USAGE ON SCHEMA evaluation TO sg_seed_oracle, sg_evaluator" not in role_text:
        errors.append("Isolation des rôles manquante")
    for table in ("site", "location", "title", "copy", "source_event", "system_state", "system_state_event", "inventory_scope", "observation", "source_availability", "operator_note", "source_case", "gap_symptom"):
        if f"'{table}'" not in text:
            errors.append(f"Source non protégée : {table}")
    assert not errors, "\n".join(errors)
    return {
        "verification": "statique uniquement ; aucune exécution SQL ni connexion DB",
        "postgresql_parser": list(get_postgresql_version()),
        "schema_statements": len(stmts),
        "roles_statements": len(roles_stmts),
        "plpgsql_blocks_parsed": plpgsql_count,
        "tables": len(tables),
        "columns": sum(len(t["columns"]) for t in tables.values()),
        "foreign_keys_checked": len(fks),
        "explicit_indexes": sum(len(i) for i in indexes.values()),
        "all_columns_documented": True,
        "foreign_key_targets_and_types": "OK",
        "runtime_oracle_foreign_keys": 0,
    }


def camel(name):
    first, *rest = name.split("_")
    return first + "".join(x.title() for x in rest)


def dictionary():
    text, _, tables, fks, indexes = load()
    output = ["# Dictionnaire physique exhaustif", "", "Document dérivé de `01-schema.sql` par `verify_model.py --dictionary`. Les commentaires du DDL précisent la provenance et les exceptions au mapping. Le nom camelCase est une convention de DTO interne, **pas une extension automatique du contrat JSON source**. Pour les sources, les chemins exacts sont dans `04-import-et-faits.md`.", "", "`PK` implique `NOT NULL`. Les FK utilisent `NO ACTION`, sans cascade destructive ; seule la FK de révision courante est différée. Chaque PK/UNIQUE crée un index B-tree implicite. Les index supplémentaires et toutes les contraintes sont reproduits par table. Les droits effectifs sont dans `02-roles.sql` ; l'auteur métier n'est pas un rôle SQL distinct. Les colonnes non source proviennent de Java, du worker validé ou de l'acte Admin selon le propriétaire indiqué.", "", "Domaines : `runtime.sha256` = text hexadécimal minuscule de 64 caractères (valeur calculée par l'application) ; `runtime.rule_key` = `R001` ou UUID textuel canonique minuscule. `timestamptz` représente un instant ; timezone de session UTC, sortie JSON en Z. Aucune sequence SQL/extension requise.", ""]
    for label, table, body in re.findall(r"-- @table ([^\n]+)\nCREATE TABLE ([\w.]+) \((.*?)\n\);", text, re.S):
        context, owner, provenance = label.split(" | ", 2)
        output.extend([f"## `{table}`", "", f"**Contexte :** {context}. **Auteur/propriétaire métier :** {owner}. **Provenance :** {provenance}", "", "**Propriétaire SQL :** " + ("`sg_evaluation_owner`" if table.startswith("evaluation.") else "`sg_runtime_owner`") + "; aucune identité applicative n'en est membre.", "", "| Colonne | Type PostgreSQL | Null ? | JSON/DTO camelCase | Sens / provenance |", "|---|---|---|---|---|"])
        descriptions = {}
        for line in body.splitlines():
            match = re.match(r" ([a-z][a-z_0-9]*)\s+.*? -- (.+)", line)
            if match:
                descriptions[match[1]] = match[2]
        for name, col in tables[table]["columns"].items():
            typ = RawStream()(col.typeName)
            nullable = "non" if name in tables[table]["notnull"] else "oui"
            desc = descriptions[name].replace("|", "\\|")
            output.append(f"| `{name}` | `{typ}` | {nullable} | `{camel(name)}` | {desc} |")
        output.extend(["", "**Contraintes et valeurs par défaut (DDL normatif)**", "", "```sql"])
        for c, column in tables[table]["constraints"]:
            output.append((f"{column}: " if column else "") + RawStream()(c))
        output.extend(["```", "", "**Index supplémentaires :**", ""])
        if indexes.get(table):
            output.extend(["```sql", *[x + ";" for x in indexes[table]], "```", ""])
        else:
            output.extend(["Aucun ; PK/UNIQUE ci-dessus indexés implicitement. Pas d'index automatique côté enfant d'une FK.", ""])
    return "\n".join(output) + "\n"


def diagram():
    _, _, tables, fks, _ = load()
    output = ["# Modèle entité–relation complet", "", "Toutes les tables et toutes les FK physiques sont représentées. Les références JSON/polymorphes ne sont pas des FK et sont décrites séparément dans le dictionnaire et les invariants. L'absence de lien entre evaluation et runtime est intentionnelle. Une cardinalité optionnelle correspond à au moins une colonne nullable de la FK ; les invariants conditionnels restent documentés ailleurs.", "", "```mermaid", "erDiagram"]
    for table, meta in tables.items():
        output.append(f"  {table.replace('.', '_')} {{")
        for column in meta["primary"]:
            typ = names(meta["columns"][column].typeName.names)[-1]
            output.append(f"    {typ} {column} PK")
        output.append("  }")
    for source, local, target, remote, _ in fks:
        mandatory = set(local) <= tables[source]["notnull"]
        unique_child = any(set(key) <= set(local) for key in tables[source]["unique"])
        left = "||" if mandatory else "|o"
        right = "o|" if unique_child else "o{"
        label = ",".join(x for x in local if x != "dataset_epoch") or "dataset_epoch"
        output.append(f'  {target.replace(".", "_")} {left}--{right} {source.replace(".", "_")} : "{label}"')
    output.extend(["```", "", "Les liaisons métier hors FK : inbox → mission déclarée (non fiable, y compris époque inconnue) ; evidence_reference → source_key/fact_path ; progress_event → agrégat ; JSON des propositions/diagnostics → références validées ; corpus d'évaluation → export runtime par empreinte, jamais par droit de lecture donné au worker.", ""])
    return "\n".join(output)


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--dictionary", action="store_true")
    parser.add_argument("--er", action="store_true")
    args = parser.parse_args()
    if args.dictionary:
        print(dictionary(), end="")
    elif args.er:
        print(diagram(), end="")
    else:
        print(json.dumps(check(), ensure_ascii=False, indent=2))
