"""Small AST helpers for the structural guards.

A structural guard asserts a property of the code's shape that no behavioural
test can reach cheaply - a blocking call inside a coroutine, a relay allocate
that runs in lan mode, a secret handed to a logger. Those guards parse the
module and match on names, never on the text: formatting, comments and the
``ps.`` phase-state prefix cannot move them, and a renamed variable fails them
loudly instead of leaving a substring check vacuous.
"""

from __future__ import annotations

import ast
import functools
import importlib.util
import pathlib


@functools.cache
def module_tree(modname: str) -> ast.Module:
    """The parsed source of ``modname``, located without importing it twice."""
    spec = importlib.util.find_spec(modname)
    assert spec is not None and spec.origin, modname
    return ast.parse(pathlib.Path(spec.origin).read_text(encoding="utf-8"))


def name_of(node: ast.AST) -> str:
    """``foo`` for ``foo(...)``, ``self.foo(...)`` and ``ps.foo(...)`` alike."""
    if isinstance(node, ast.Call):
        node = node.func
    if isinstance(node, ast.Attribute):
        return node.attr
    if isinstance(node, ast.Name):
        return node.id
    return ""


def calls_to(tree: ast.AST, name: str) -> list[ast.Call]:
    """Every call of ``name``, as a plain name or an attribute, in ``tree``."""
    return [n for n in ast.walk(tree) if isinstance(n, ast.Call) and name_of(n) == name]


def functions(tree: ast.AST, name: str) -> list[ast.AST]:
    """Every (async) def named ``name`` in ``tree``, nested ones included."""
    return [
        n
        for n in ast.walk(tree)
        if isinstance(n, (ast.FunctionDef, ast.AsyncFunctionDef)) and n.name == name
    ]


def function(tree: ast.AST, name: str) -> ast.AST:
    found = functions(tree, name)
    assert len(found) == 1, f"expected one def {name}, found {len(found)}"
    return found[0]


def parents(tree: ast.AST) -> dict:
    """child -> parent for every node in ``tree``."""
    out = {}
    for node in ast.walk(tree):
        for child in ast.iter_child_nodes(node):
            out[child] = node
    return out


def enclosing(node: ast.AST, parent_map: dict, kinds) -> list[ast.AST]:
    """The ancestors of ``node`` of the given node types, innermost first."""
    out = []
    while node in parent_map:
        node = parent_map[node]
        if isinstance(node, kinds):
            out.append(node)
    return out


def names_in(node: ast.AST) -> set[str]:
    """Every plain name and attribute name used under ``node``."""
    out = set()
    for n in ast.walk(node):
        if isinstance(n, ast.Name):
            out.add(n.id)
        elif isinstance(n, ast.Attribute):
            out.add(n.attr)
    return out


def package_modules(root: str = "aidot_cameras") -> list[pathlib.Path]:
    """Every first-party module of the package, the vendored tree excluded."""
    spec = importlib.util.find_spec(root)
    base = pathlib.Path(spec.origin).parent
    return sorted(p for p in base.rglob("*.py") if "_vendor" not in p.parts)
