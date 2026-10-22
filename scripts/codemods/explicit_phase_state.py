#!/usr/bin/env python3
"""Codemod: replace the ``locals()`` phase plumbing with explicit state objects.

1.0.0rc58 split the two stream opens and the two serve loops into phase and
region coroutines that receive the caller's ``locals()`` as ``_in``, re-bind
what they need with ``if "x" in _in: x = _in["x"]``, and hand back what they
bound through ``_phase_outputs(locals(), (...names))``. This codemod rewrites
each of those families to a per-call state object instead:

* a ``@dataclasses.dataclass(slots=True)`` class per family holding every name
  that crosses a phase boundary (the union of the ``_in`` prologues and the
  export tuples). The orchestrator's own parameters are constructor
  arguments; every other field starts unset, so reading it before a phase
  writes it raises ``AttributeError`` the way the unbound local raised
  ``NameError``;
* phases take ``(self, ps)``, read and write ``ps.<name>`` directly, and return
  only a control signal (``_PhaseReturn`` / ``_LoopContinue`` / ``_LoopBreak``)
  or None;
* the orchestrator builds the state first thing, keeps every reference to a
  field on it, and drops the read-back boilerplate and ``_phase_outputs``.

A name stays a plain local inside one phase (it is not rewritten to
``ps.<name>``) when rewriting it would change what other phases see:

* it is bound only by ``except ... as``: Python unbinds it when the handler
  ends, so no export ever saw it;
* the phase takes it in and rebinds it without exporting it: the rebinding
  was the phase's own (``x = ps.x`` replaces the prologue where the incoming
  value can be read);
* it is exported but bound by ``import``, ``def`` or ``class``, which cannot
  bind an attribute: the binding stays and ``ps.x = x`` follows it.

The codemod refuses (exits 2 and says why) instead of guessing when a family
has a shape it does not handle: a phase call inside ``try``/``with``, a bare
``return value`` in a phase, a closure that would see a later rebinding it did
not see before, ``nonlocal``/walrus/``del`` on a field, export tuples that
differ between the returns of one phase, and so on.

It is deterministic and idempotent: an already converted family is left
alone, so running it twice is a no-op. Usage::

    python scripts/codemods/explicit_phase_state.py aidot_cameras/camera
    python scripts/codemods/explicit_phase_state.py aidot_cameras/camera --check
    python scripts/codemods/explicit_phase_state.py aidot_cameras/camera --awaits

``--check`` and ``--awaits`` need only the standard library; rewriting needs
libcst (``pip install -e ".[codemod]"``, or
``uvx --with libcst python scripts/codemods/explicit_phase_state.py ...``).
See docs/CODEMODS.md.
"""

from __future__ import annotations

import argparse
import ast
import builtins
import re
import shutil
import subprocess
import symtable
import sys
from dataclasses import dataclass, field
from pathlib import Path
from typing import Any


@dataclass(frozen=True)
class Family:
    module: str  # file name inside the package directory
    orchestrator: str  # method name on the mixin
    prefix: str  # phase/region coroutine name prefix
    state_class: str
    loop: bool  # a serve loop: its regions run again on every pass


FAMILIES = (
    Family(
        "webrtc_open.py",
        "_async_open_webrtc_stream_impl",
        "_dtls_open_",
        "_DtlsOpenState",
        False,
    ),
    Family(
        "sdes_open.py",
        "_open_sdes_stream_impl",
        "_sdes_open_",
        "_SdesOpenState",
        False,
    ),
    Family(
        "client.py",
        "_sdes_keepalive_loop_inner",
        "_sdes_keepalive_",
        "_SdesKeepaliveState",
        True,
    ),
    Family(
        "client.py",
        "_dtls_serve_loop_inner",
        "_dtls_serve_loop_",
        "_DtlsServeLoopState",
        True,
    ),
)

# The state parameter's name. Fixed, not picked, so a regeneration on a later
# base always spells it the same way (tests/source_parts.without_state strips
# exactly this prefix).
STATE_NAME = "ps"
SIGNALS = ("_PhaseReturn", "_LoopContinue", "_LoopBreak")
GENERATED_BY = "scripts/codemods/explicit_phase_state.py"


class Refuse(Exception):
    """The source has a shape this codemod does not convert safely."""


# --------------------------------------------------------------------------- #
# Reading the old form (stdlib ast only)
# --------------------------------------------------------------------------- #

_SCOPES = (ast.FunctionDef, ast.AsyncFunctionDef, ast.Lambda, ast.ClassDef)
_COMPS = (ast.ListComp, ast.SetComp, ast.DictComp, ast.GeneratorExp)
_TRY = (ast.Try, ast.TryStar) if hasattr(ast, "TryStar") else (ast.Try,)


def _readback_key(stmt: ast.AST, var: str) -> str | None:
    """``x`` for ``if "x" in <var>: x = <var>["x"]``, else None."""
    if not isinstance(stmt, ast.If):
        return None
    t = stmt.test
    if not (
        isinstance(t, ast.Compare)
        and isinstance(t.left, ast.Constant)
        and isinstance(t.left.value, str)
        and len(t.ops) == 1
        and isinstance(t.ops[0], ast.In)
        and isinstance(t.comparators[0], ast.Name)
        and t.comparators[0].id == var
    ):
        return None
    body = stmt.body[0] if len(stmt.body) == 1 else None
    if (
        stmt.orelse
        or not isinstance(body, ast.Assign)
        or len(body.targets) != 1
        or not isinstance(body.targets[0], ast.Name)
        or body.targets[0].id != t.left.value
    ):
        raise Refuse(f"line {stmt.lineno}: unexpected read-back shape")
    return t.left.value


def _own_nodes(fn: ast.AST):
    """Nodes in ``fn``'s own scope (not inside a nested def, lambda or class)."""
    stack = list(ast.iter_child_nodes(fn))
    while stack:
        node = stack.pop()
        yield node
        if not isinstance(node, _SCOPES):
            stack.extend(ast.iter_child_nodes(node))


def _target_names(t: ast.AST | None) -> set[str]:
    if isinstance(t, ast.Name):
        return {t.id}
    if isinstance(t, (ast.Tuple, ast.List)):
        out: set[str] = set()
        for e in t.elts:
            out |= _target_names(e)
        return out
    if isinstance(t, ast.Starred):
        return _target_names(t.value)
    return set()


def _bindings(fn: ast.AST, skip: set[ast.AST]) -> dict[str, set[str]]:
    """Names bound in ``fn``'s own scope outside ``skip``, by binding form.

    Forms: ``plain`` (assignment, for/with target, del), ``except``,
    ``import``, ``def`` (def and class), ``walrus``, ``match``.
    """
    out: dict[str, set[str]] = {
        k: set() for k in ("plain", "except", "import", "def", "walrus", "match")
    }
    stack: list[ast.AST] = [s for s in fn.body if s not in skip]  # type: ignore[attr-defined]
    while stack:
        node = stack.pop()
        if isinstance(node, (ast.FunctionDef, ast.AsyncFunctionDef, ast.ClassDef)):
            out["def"].add(node.name)
            stack.extend(node.decorator_list)
            continue
        if isinstance(node, ast.Lambda):
            continue
        if isinstance(node, _COMPS):
            for n in ast.walk(node):
                if isinstance(n, ast.NamedExpr):
                    out["walrus"] |= _target_names(n.target)
            continue
        if isinstance(node, ast.Name) and isinstance(node.ctx, (ast.Store, ast.Del)):
            out["plain"].add(node.id)
        elif isinstance(node, ast.NamedExpr):
            out["walrus"] |= _target_names(node.target)
            stack.append(node.value)
            continue
        elif isinstance(node, ast.ExceptHandler) and node.name:
            out["except"].add(node.name)
        elif isinstance(node, (ast.Import, ast.ImportFrom)):
            out["import"] |= {(a.asname or a.name).split(".")[0] for a in node.names}
        elif isinstance(node, (ast.MatchAs, ast.MatchStar)) and node.name:
            out["match"].add(node.name)
        stack.extend(ast.iter_child_nodes(node))
    return out


def _export_tuples(fn: ast.AST) -> list[tuple[int, tuple[str, ...]]]:
    found = []
    for node in _own_nodes(fn):
        if (
            isinstance(node, ast.Call)
            and isinstance(node.func, ast.Name)
            and node.func.id == "_phase_outputs"
        ):
            ok = (
                len(node.args) == 2
                and isinstance(node.args[0], ast.Call)
                and isinstance(node.args[0].func, ast.Name)
                and node.args[0].func.id == "locals"
                and isinstance(node.args[1], ast.Tuple)
                and all(
                    isinstance(e, ast.Constant) and isinstance(e.value, str)
                    for e in node.args[1].elts
                )
            )
            if not ok:
                raise Refuse(f"line {node.lineno}: export is not a literal tuple")
            names = tuple(e.value for e in node.args[1].elts)  # type: ignore[attr-defined]
            found.append((node.lineno, names))
    return found


def _signals(fn: ast.AST) -> set[str]:
    """Control signals a phase can return; refuses a bare value return."""
    out = set()
    for node in _own_nodes(fn):
        if not isinstance(node, ast.Return):
            continue
        v = node.value
        if isinstance(v, ast.Call) and isinstance(v.func, ast.Name):
            if v.func.id == "_phase_outputs":
                continue
            if v.func.id in SIGNALS:
                out.add(v.func.id)
                continue
        if v is None or (isinstance(v, ast.Constant) and v.value is None):
            continue
        raise Refuse(
            f"line {node.lineno}: a phase returns {ast.unparse(v)!r} without a"
            " signal, so the orchestrator would drop it"
        )
    return out


class _DefiniteAssignment:
    """Which of ``names`` a function body may read before binding them itself.

    Conservative: a read counts as unsafe unless every path to it binds the
    name first. A read inside a nested def or lambda counts at the point the
    function is defined.
    """

    def __init__(self, names: set[str]):
        self.names = names
        self.unsafe: set[str] = set()
        self.exits: list[frozenset] = []  # bound names at each plain return

    TOP = None  # the end of the block is unreachable

    def _meet(self, a, b):
        if a is self.TOP:
            return b
        if b is self.TOP:
            return a
        return a & b

    def expr(self, node, assigned, shadow=frozenset()):
        if node is None:
            return
        if isinstance(node, ast.Name):
            if (
                isinstance(node.ctx, ast.Load)
                and node.id in self.names
                and node.id not in assigned
                and node.id not in shadow
            ):
                self.unsafe.add(node.id)
            return
        if isinstance(node, ast.Lambda):
            params = {a.arg for a in ast.walk(node.args) if isinstance(a, ast.arg)}
            for d in node.args.defaults:
                self.expr(d, assigned, shadow)
            for d in node.args.kw_defaults:
                self.expr(d, assigned, shadow)
            self.expr(node.body, assigned, shadow | params)
            return
        if isinstance(node, _COMPS):
            targets: set[str] = set()
            for g in node.generators:
                targets |= _target_names(g.target)
            for i, g in enumerate(node.generators):
                self.expr(g.iter, assigned, shadow if i == 0 else shadow | targets)
                for c in g.ifs:
                    self.expr(c, assigned, shadow | targets)
            for part in ("elt", "key", "value"):
                self.expr(getattr(node, part, None), assigned, shadow | targets)
            return
        for child in ast.iter_child_nodes(node):
            self.expr(child, assigned, shadow)

    def nested_def(self, node, assigned):
        local: set[str] = set()
        outer: set[str] = set()
        for n in ast.walk(node):
            if n is node:
                continue
            if isinstance(n, ast.Name) and isinstance(n.ctx, (ast.Store, ast.Del)):
                local.add(n.id)
            elif isinstance(n, ast.arg):
                local.add(n.arg)
            elif isinstance(n, (ast.Nonlocal, ast.Global)):
                outer |= set(n.names)
        local -= outer
        for n in ast.walk(node):
            if (
                isinstance(n, ast.Name)
                and isinstance(n.ctx, ast.Load)
                and n.id in self.names
                and n.id not in local
                and n.id not in assigned
            ):
                self.unsafe.add(n.id)

    def block(self, stmts, assigned):
        cur = assigned
        for s in stmts:
            if cur is self.TOP:
                return self.TOP
            cur = self.stmt(s, cur)
        return cur

    def stmt(self, s, a):
        T = self.TOP
        if isinstance(s, ast.Assign):
            self.expr(s.value, a)
            out = set(a)
            for t in s.targets:
                names = _target_names(t)
                if not names:
                    self.expr(t, a)
                out |= names
            return frozenset(out)
        if isinstance(s, ast.AugAssign):
            self.expr(s.value, a)
            if isinstance(s.target, ast.Name):
                if s.target.id in self.names and s.target.id not in a:
                    self.unsafe.add(s.target.id)
                return a | {s.target.id}
            self.expr(s.target, a)
            return a
        if isinstance(s, ast.AnnAssign):
            if s.value is None:
                return a
            self.expr(s.value, a)
            if not isinstance(s.target, ast.Name):
                self.expr(s.target, a)
            return a | _target_names(s.target)
        if isinstance(s, (ast.For, ast.AsyncFor)):
            self.expr(s.iter, a)
            self.block(s.body, a | _target_names(s.target))
            self.block(s.orelse, a)
            return a
        if isinstance(s, ast.While):
            self.expr(s.test, a)
            self.block(s.body, a)
            self.block(s.orelse, a)
            return a
        if isinstance(s, ast.If):
            self.expr(s.test, a)
            return self._meet(self.block(s.body, a), self.block(s.orelse, a))
        if isinstance(s, (ast.With, ast.AsyncWith)):
            inner = set(a)
            for item in s.items:
                self.expr(item.context_expr, a)
                inner |= _target_names(item.optional_vars)
            out = self.block(s.body, frozenset(inner))
            return a if out is T else out
        if isinstance(s, _TRY):
            body = self.block(s.body, a)
            outs = [self.block(s.orelse, body) if body is not T else T]
            for h in s.handlers:
                self.expr(h.type, a)
                ho = self.block(h.body, a | {h.name} if h.name else a)
                outs.append(T if ho is T else (ho - {h.name} if h.name else ho))
            merged = outs[0]
            for o in outs[1:]:
                merged = self._meet(merged, o)
            fin = self.block(s.finalbody, a)
            if fin is T or merged is T:
                return T
            return merged | (fin - a)
        if isinstance(s, (ast.FunctionDef, ast.AsyncFunctionDef, ast.ClassDef)):
            for d in s.decorator_list:
                self.expr(d, a)
            if isinstance(s, ast.ClassDef):
                for b in s.bases:
                    self.expr(b, a)
            else:
                for d in s.args.defaults:
                    self.expr(d, a)
                for d in s.args.kw_defaults:
                    self.expr(d, a)
            self.nested_def(s, a)
            return a | {s.name}
        if isinstance(s, (ast.Import, ast.ImportFrom)):
            return a | {(n.asname or n.name).split(".")[0] for n in s.names}
        if isinstance(s, (ast.Return, ast.Raise)):
            for c in ast.iter_child_nodes(s):
                self.expr(c, a)
            v = s.value if isinstance(s, ast.Return) else None
            if isinstance(s, ast.Return) and not (
                isinstance(v, ast.Call)
                and isinstance(v.func, ast.Name)
                and v.func.id in SIGNALS
            ):
                self.exits.append(a)
            return T
        if isinstance(s, (ast.Continue, ast.Break)):
            return T
        if isinstance(s, ast.Delete):
            gone: set[str] = set()
            for t in s.targets:
                names = _target_names(t)
                if not names:
                    self.expr(t, a)
                gone |= names
            return a - gone
        if isinstance(s, ast.Match):
            self.expr(s.subject, a)
            for c in s.cases:
                self.block(c.body, a)
            return a
        for c in ast.iter_child_nodes(s):
            self.expr(c, a)
        return a


@dataclass
class PhasePlan:
    name: str
    node: ast.AsyncFunctionDef
    inputs: list[str]  # prologue order
    exports: set[str]
    signals: set[str]
    attr: set[str] = field(default_factory=set)  # rewritten to ps.x
    local: set[str] = field(default_factory=set)  # kept as phase locals
    sync: set[str] = field(default_factory=set)  # local, mirrored after binding
    prologue: set[str] = field(default_factory=set)  # local, starts as ps.x
    writes: set[str] = field(default_factory=set)  # fields this phase writes
    captured: set[str] = field(default_factory=set)
    surely_writes: frozenset = frozenset()  # bound on every plain return
    guarded: set[str] = field(default_factory=set)  # prologue copies kept conditional


@dataclass
class FamilyPlan:
    family: Family
    phases: list[PhasePlan]  # in call order
    fields: list[str]  # ordered
    init_fields: list[str]  # orchestrator parameters that are fields
    param_annotations: dict[str, str]
    orch_writes: set[str]
    orch_fields: set[str]  # fields the orchestrator itself references
    state_name: str
    kinds: dict[str, list[str]] = field(default_factory=dict)
    first_def: str = ""


def _captured(table: symtable.SymbolTable) -> set[str]:
    """Locals of ``table``'s function that a function nested in it closes over."""
    own = {s.get_name() for s in table.get_symbols() if s.is_local()}
    out: set[str] = set()
    stack = list(table.get_children())
    while stack:
        t = stack.pop()
        stack.extend(t.get_children())
        out |= {s.get_name() for s in t.get_symbols() if s.is_free()} & own
    return out


def _table_of(src: str, path: str, fn: ast.AST) -> symtable.SymbolTable:
    def walk(t):
        yield t
        for c in t.get_children():
            yield from walk(c)

    for t in walk(symtable.symtable(src, path, "exec")):
        if t.get_name() == fn.name and t.get_lineno() == fn.lineno:  # type: ignore[attr-defined]
            return t
    raise Refuse(f"no symbol table for {fn.name}")  # type: ignore[attr-defined]


def _find_orchestrator(tree: ast.Module, name: str) -> ast.AsyncFunctionDef:
    found = [
        n
        for n in ast.walk(tree)
        if isinstance(n, ast.AsyncFunctionDef) and n.name == name
    ]
    if len(found) != 1:
        raise Refuse(f"{name}: expected one definition, found {len(found)}")
    return found[0]


def _phase_defs(tree: ast.Module, fam: Family) -> list[ast.AsyncFunctionDef]:
    return [
        n
        for n in tree.body
        if isinstance(n, ast.AsyncFunctionDef) and n.name.startswith(fam.prefix)
    ]


def family_is_old_form(tree: ast.Module, fam: Family) -> bool:
    return any(
        [a.arg for a in n.args.args] == ["self", "_in"] for n in _phase_defs(tree, fam)
    )


def _parents(root: ast.AST) -> dict[ast.AST, ast.AST]:
    out = {}
    for node in ast.walk(root):
        for child in ast.iter_child_nodes(node):
            out[child] = node
    return out


def _orchestrator_calls(orch: ast.AsyncFunctionDef, phase_names: set[str]):
    """``(phase name, statement)`` for each ``_o = await P(self, locals())``."""
    calls = []
    parents = _parents(orch)
    for node in _own_nodes(orch):
        if not (
            isinstance(node, ast.Call)
            and isinstance(node.func, ast.Name)
            and node.func.id in phase_names
        ):
            continue
        aw = parents.get(node)
        stmt = parents.get(aw) if aw is not None else None
        ok = (
            isinstance(aw, ast.Await)
            and isinstance(stmt, ast.Assign)
            and len(stmt.targets) == 1
            and isinstance(stmt.targets[0], ast.Name)
            and stmt.targets[0].id == "_o"
            and len(node.args) == 2
            and not node.keywords
            and isinstance(node.args[0], ast.Name)
            and node.args[0].id == "self"
            and isinstance(node.args[1], ast.Call)
            and isinstance(node.args[1].func, ast.Name)
            and node.args[1].func.id == "locals"
        )
        if not ok:
            raise Refuse(
                f"{orch.name} line {node.lineno}: unexpected call to {node.func.id}"
            )
        p = parents.get(stmt)
        while p is not None and p is not orch:
            if isinstance(p, (*_TRY, ast.With, ast.AsyncWith)):
                raise Refuse(
                    f"{orch.name} line {node.lineno}: {node.func.id} is called inside"
                    " try/with, where a phase's partial writes would become visible"
                )
            p = parents.get(p)
        calls.append((node.func.id, stmt))
    calls.sort(key=lambda c: c[1].lineno)
    return calls


def _enclosing_while(orch: ast.AST, stmt: ast.AST) -> ast.While | None:
    parents = _parents(orch)
    p = parents.get(stmt)
    while p is not None:
        if isinstance(p, ast.While):
            return p
        p = parents.get(p)
    return None


def plan_family(src: str, path: str, fam: Family, state_name: str) -> FamilyPlan:
    tree = ast.parse(src)
    defs = {n.name: n for n in _phase_defs(tree, fam)}
    orch = _find_orchestrator(tree, fam.orchestrator)
    calls = _orchestrator_calls(orch, set(defs))
    order = [name for name, _ in calls]
    if sorted(order) != sorted(defs) or len(set(order)) != len(order):
        raise Refuse(
            f"{fam.orchestrator}: calls {order}, module defines {sorted(defs)}"
        )
    call_line = {name: stmt.lineno for name, stmt in calls}
    loop_node = None
    if fam.loop:
        loops = {id(_enclosing_while(orch, stmt)) for _, stmt in calls}
        loop_node = _enclosing_while(orch, calls[0][1])
        if len(loops) != 1 or loop_node is None:
            raise Refuse(f"{fam.orchestrator}: regions are not all in one loop")

    # Pass 1: what each phase takes in, binds, exports and really reads.
    phases: list[PhasePlan] = []
    info = {}
    for name in order:
        fn = defs[name]
        prologue_stmts: set[ast.AST] = set()
        inputs = []
        for s in fn.body:
            k = _readback_key(s, "_in")
            if k is not None:
                prologue_stmts.add(s)
                inputs.append(k)
        in_prologue = {id(n) for s in prologue_stmts for n in ast.walk(s)}
        for n in ast.walk(fn):
            if isinstance(n, ast.Name) and n.id == "_in" and id(n) not in in_prologue:
                raise Refuse(f"{name} line {n.lineno}: reads _in outside its prologue")
        tuples = _export_tuples(fn)
        if not tuples:
            raise Refuse(f"{name}: no _phase_outputs export")
        exports = set(tuples[0][1])
        for line, t in tuples[1:]:
            if set(t) != exports:
                raise Refuse(
                    f"{name} line {line}: export tuple differs from line {tuples[0][0]}"
                )
        p = PhasePlan(name, fn, inputs, exports - {"self"}, _signals(fn))
        p.captured = _captured(_table_of(src, path, fn))
        b = _bindings(fn, prologue_stmts)
        # Reads of an incoming value: an input read where the phase may not
        # have bound it itself.
        da = _DefiniteAssignment(set(inputs) - {"self"})
        end = da.block([s for s in fn.body if s not in prologue_stmts], frozenset())
        exits = da.exits + ([end] if end is not None else [])
        p.surely_writes = frozenset.intersection(*exits) if exits else frozenset()
        info[name] = (b, da.unsafe)
        phases.append(p)

    # The orchestrator: its parameters, own bindings and incoming reads.
    params = orch.args.args[1:] + orch.args.kwonlyargs
    param_names = [a.arg for a in params]
    readbacks = [
        n for n in _own_nodes(orch) if isinstance(n, ast.If) and _readback_key(n, "_o")
    ]
    in_readback = {id(n) for s in readbacks for n in ast.walk(s)}
    own_stores = [
        n
        for n in _own_nodes(orch)
        if isinstance(n, ast.Name)
        and isinstance(n.ctx, (ast.Store, ast.Del))
        and id(n) not in in_readback
    ]
    all_bound = set()
    for b, _ in info.values():
        all_bound |= b["plain"] | b["except"] | b["import"] | b["def"]
    exported_anywhere = set().union(*(p.exports for p in phases))
    orch_da = _DefiniteAssignment(exported_anywhere | set(param_names))
    orch_da.block([s for s in orch.body if s not in readbacks], frozenset(param_names))
    # A loop orchestrator reads its own pre-loop names too; those reach the
    # regions through locals() and are found below as the regions' inputs.
    orch_reads = set(orch_da.unsafe)
    orch_table = _table_of(src, path, orch)
    orch_free = _captured(orch_table)

    # A field is a name some phase or the orchestrator reads from outside
    # itself. Anything else stays local to whatever binds it.
    external = set(orch_reads) | (orch_free & (exported_anywhere | set(param_names)))
    for p in phases:
        external |= info[p.name][1]
    writable = set(param_names) | {n.id for n in own_stores}
    for p in phases:
        b = info[p.name][0]
        writable |= p.exports & (b["plain"] | b["except"] | b["import"] | b["def"])
    fields_set = external & writable
    missing = external - writable
    # A read nothing writes was unbound before too (or a global); only a name
    # some phase takes in as an input is suspicious.
    missing &= {x for p in phases for x in p.inputs}
    if missing:
        raise Refuse(
            f"{fam.orchestrator}: {sorted(missing)} are read from the state but no"
            " phase exports them and the orchestrator never binds them"
        )

    for p in phases:
        fn = p.node
        b, unsafe = info[p.name]
        nameonly = b["import"] | b["def"]
        bound_any = b["plain"] | b["except"] | nameonly
        mine = fields_set & (bound_any | unsafe)
        for bad in ("walrus", "match"):
            if b[bad] & mine:
                raise Refuse(f"{p.name}: {bad} binds field(s) {sorted(b[bad] & mine)}")
        for n in ast.walk(fn):
            if isinstance(n, ast.Nonlocal) and set(n.names) & mine:
                raise Refuse(f"{p.name} line {n.lineno}: nonlocal on a field")
            if isinstance(n, ast.Delete):
                hit = set().union(*(_target_names(t) for t in n.targets)) & mine
                if hit:
                    raise Refuse(
                        f"{p.name} line {n.lineno}: del of field(s) {sorted(hit)}"
                    )
        for x in sorted(mine):
            incoming = x in unsafe
            if x not in bound_any:
                p.attr.add(x)  # read only
            elif x not in p.exports:
                # Rebound here, but the rebinding was never handed back.
                p.local.add(x)
                if incoming:
                    p.prologue.add(x)
            elif x in b["except"] and x not in (b["plain"] | nameonly):
                # Only ever bound by ``except ... as``: unbound again before any
                # export could see it, unless the handler itself returns.
                for h in _own_nodes(fn):
                    if isinstance(h, ast.ExceptHandler) and h.name == x:
                        if any(isinstance(r, ast.Return) for r in _own_nodes(h)):
                            raise Refuse(
                                f"{p.name} line {h.lineno}: returns inside the"
                                f" handler that binds {x}"
                            )
                p.local.add(x)
                if incoming:
                    p.prologue.add(x)
            elif x in (nameonly | b["except"]):
                p.local.add(x)
                p.sync.add(x)
                if incoming:
                    p.prologue.add(x)
            else:
                p.attr.add(x)
        p.writes = (p.attr & b["plain"]) | p.sync
        # Bindings of names that are not fields (or not exported) stay local.
        p.local |= (bound_any & (p.exports | set(p.inputs))) - p.attr - fields_set

    # A prologue copy runs unconditionally now; it is only the same as the old
    # ``if "x" in _in`` when the field is certainly set by then.
    pre = _DefiniteAssignment(set())
    top_pre = []
    for s in orch.body:
        if any(s is c or any(s is a for a in ast.walk(s)) for _, c in calls):
            break
        if any(c in ast.walk(s) for _, c in calls):
            break
        top_pre.append(s)
    pre_set = pre.block(top_pre, frozenset(param_names)) or frozenset()
    for i, p in enumerate(phases):
        for x in sorted(p.prologue):
            sure = x in pre_set
            if not fam.loop:
                sure = sure or any(
                    x in q.surely_writes and x in q.exports for q in phases[:i]
                )
            if not sure:
                p.guarded.add(x)

    ob = _bindings(orch, set())
    for bad in ("except", "import", "def", "walrus", "match"):
        hit = ob[bad] & fields_set
        if hit:
            raise Refuse(
                f"{fam.orchestrator}: {bad} binds field(s) {sorted(hit)} in the"
                " orchestrator itself"
            )
    if any(isinstance(n.ctx, ast.Del) and n.id in fields_set for n in own_stores):
        raise Refuse(f"{fam.orchestrator}: del of a field")
    # An orchestrator closure that exists while a phase runs would now see the
    # phase's writes as they happen instead of after it returns.
    last_call = calls[-1][1].lineno
    for n in _own_nodes(orch):
        if isinstance(n, (ast.FunctionDef, ast.AsyncFunctionDef, ast.Lambda)):
            if n.lineno < last_call or (
                fam.loop
                and loop_node is not None
                and n.lineno <= (loop_node.end_lineno or 0)
            ):
                free = {
                    x.id for x in ast.walk(n) if isinstance(x, ast.Name)
                } & fields_set
                if free:
                    raise Refuse(
                        f"{fam.orchestrator} line {n.lineno}: a closure over"
                        f" {sorted(free)} exists while phases run"
                    )
    orch_loads = {
        n.id
        for n in _own_nodes(orch)
        if isinstance(n, ast.Name) and isinstance(n.ctx, ast.Load)
    }
    orch_fields = (
        orch_loads | {n.id for n in own_stores} | orch_free | set(param_names)
    ) & fields_set
    orch_writes = ({n.id for n in own_stores} | set(param_names)) & fields_set

    # A closure in a phase now reads the attribute when it runs instead of the
    # phase's own cell; the same thing only if nothing rebinds it afterwards.
    for i, p in enumerate(phases):
        for x in sorted(p.captured & p.attr):
            if fam.loop:
                assert loop_node is not None
                others = [q.name for q in phases if x in q.writes]
                orch_hit = any(
                    n.id == x
                    and loop_node.lineno <= n.lineno <= (loop_node.end_lineno or 0)
                    for n in own_stores
                )
            else:
                others = [q.name for q in phases[i + 1 :] if x in q.writes]
                orch_hit = any(
                    n.id == x and n.lineno > call_line[p.name] for n in own_stores
                )
            if others or orch_hit:
                raise Refuse(
                    f"{p.name}: a closure reads {x!r}, which"
                    f" {others or fam.orchestrator} rebinds later; as an attribute"
                    " it would see the new value where it used to keep the old one"
                )
    # Field order: constructor arguments, then by first write in run order.
    first_write: dict[str, tuple] = {}
    for x in param_names:
        if x in fields_set:
            first_write.setdefault(x, (0, param_names.index(x)))
    first_call = calls[0][1].lineno
    for n in sorted(own_stores, key=lambda n: (n.lineno, n.col_offset)):
        if n.id in fields_set:
            stage = 1 if n.lineno < first_call else 3
            first_write.setdefault(n.id, (stage, n.lineno, n.col_offset))
    for idx, p in enumerate(phases):
        spots = []
        for n in ast.walk(p.node):
            if isinstance(n, ast.Name) and isinstance(n.ctx, ast.Store):
                spots.append((n.lineno, n.col_offset, n.id))
            elif isinstance(n, (ast.FunctionDef, ast.AsyncFunctionDef, ast.ClassDef)):
                spots.append((n.lineno, n.col_offset, n.name))
            elif isinstance(n, (ast.Import, ast.ImportFrom)):
                for a in n.names:
                    spots.append(
                        (n.lineno, n.col_offset, (a.asname or a.name).split(".")[0])
                    )
        for line, col, nm in sorted(spots):
            if nm in p.writes:
                first_write.setdefault(nm, (2, idx, line, col))
    ordered = sorted(fields_set, key=lambda x: (first_write.get(x, (9,)), x))
    init_fields = [x for x in param_names if x in fields_set]
    ann = {
        a.arg: a.annotation.value
        if isinstance(a.annotation, ast.Constant)
        and isinstance(a.annotation.value, str)
        else ast.unparse(a.annotation)
        for a in params
        if a.arg in fields_set and a.annotation is not None
    }
    plan = FamilyPlan(
        fam, phases, ordered, init_fields, ann, orch_writes, orch_fields, state_name
    )
    plan.kinds = _literal_kinds(tree, plan)
    plan.first_def = min(phases, key=lambda p: p.node.lineno).name
    return plan


def _literal_kinds(tree: ast.Module, plan: FamilyPlan) -> dict[str, list[str]]:
    """Per field, the kind of each value written to it (``?``: not a literal)."""
    out: dict[str, list[str]] = {}
    fields_set = set(plan.fields)

    def kind(v: ast.AST | None) -> str:
        if isinstance(v, ast.Constant):
            if v.value is None:
                return "None"
            for t in (bool, int, float, str):
                if type(v.value) is t:
                    return t.__name__
        if isinstance(v, ast.JoinedStr):
            return "str"
        if isinstance(v, ast.UnaryOp) and isinstance(v.op, ast.USub):
            return kind(v.operand)
        return "?"

    def add(names, k):
        for nm in names & fields_set:
            out.setdefault(nm, []).append(k)

    module_names = set(dir(builtins))
    top = list(tree.body)
    for n in tree.body:  # imports under ``if TYPE_CHECKING:`` count too
        if isinstance(n, ast.If) and "TYPE_CHECKING" in ast.unparse(n.test):
            top += n.body
    for n in top:
        if isinstance(n, (ast.Import, ast.ImportFrom)):
            module_names |= {(a.asname or a.name).split(".")[0] for a in n.names}
        elif isinstance(n, (ast.FunctionDef, ast.AsyncFunctionDef, ast.ClassDef)):
            module_names.add(n.name)
        elif isinstance(n, ast.Assign):
            for t in n.targets:
                module_names |= _target_names(t)
    fns: list[ast.AST] = [_find_orchestrator(tree, plan.family.orchestrator)]
    fns += [p.node for p in plan.phases]
    for fn in fns:
        for n in _own_nodes(fn):
            if isinstance(n, ast.Assign):
                for t in n.targets:
                    add(
                        _target_names(t),
                        kind(n.value) if isinstance(t, ast.Name) else "?",
                    )
            elif isinstance(n, (ast.AugAssign, ast.AnnAssign)):
                if n.value is not None:
                    add(_target_names(n.target), kind(n.value))
                if isinstance(n, ast.AnnAssign):
                    names = {
                        a.id for a in ast.walk(n.annotation) if isinstance(a, ast.Name)
                    }
                    ok = names <= module_names and not any(
                        isinstance(a, ast.Constant) for a in ast.walk(n.annotation)
                    )
                    add(
                        _target_names(n.target),
                        "ann:" + ast.unparse(n.annotation) if ok else "?",
                    )
            elif isinstance(n, (ast.For, ast.AsyncFor)):
                add(_target_names(n.target), "?")
            elif isinstance(n, (ast.With, ast.AsyncWith)):
                for i in n.items:
                    add(_target_names(i.optional_vars), "?")
            elif isinstance(n, (ast.FunctionDef, ast.AsyncFunctionDef)):
                add({n.name}, "callable")
            elif isinstance(n, ast.ClassDef):
                add({n.name}, "?")
            elif isinstance(n, (ast.Import, ast.ImportFrom)):
                add({(a.asname or a.name).split(".")[0] for a in n.names}, "?")
    return out


_BARE_GENERICS = {
    "dict": "dict[Any, Any]",
    "list": "list[Any]",
    "set": "set[Any]",
    "tuple": "tuple[Any, ...]",
    "asyncio.Future": "asyncio.Future[Any]",
    "asyncio.Queue": "asyncio.Queue[Any]",
}


def _parametrise(ann: str) -> str:
    """``list`` -> ``list[Any]`` and so on, so the field passes mypy --strict."""
    for bare, full in _BARE_GENERICS.items():
        ann = re.sub(rf"(?<![\w.]){re.escape(bare)}(?![\w.\[])", full, ann)
    return ann


def _field_type(plan: FamilyPlan, x: str) -> str:
    return _parametrise(_field_type_raw(plan, x)).replace("'", "")


def _field_type_raw(plan: FamilyPlan, x: str) -> str:
    if x in plan.param_annotations:
        return plan.param_annotations[x]
    anns = {k[4:] for k in plan.kinds.get(x, []) if k.startswith("ann:")}
    if len(anns) == 1:
        return anns.pop()
    kinds = {k for k in plan.kinds.get(x, ["?"]) if not k.startswith("ann:")} or {"?"}
    if "?" in kinds:
        return "Any"
    has_none = "None" in kinds
    kinds.discard("None")
    if kinds == {"callable"}:
        base = "Callable[..., Any]"
    elif kinds and kinds <= {"int", "float"}:
        base = "float" if "float" in kinds else "int"
    elif kinds in ({"bool"}, {"str"}):
        base = next(iter(kinds))
    else:
        return "Any"
    return f"Optional[{base}]" if has_none else base


def choose_state_name(sources: dict[str, str]) -> str:
    used: set[str] = set()
    for src in sources.values():
        tree = ast.parse(src)
        for fam in FAMILIES:
            fns: list[ast.AST] = list(_phase_defs(tree, fam))
            try:
                fns.append(_find_orchestrator(tree, fam.orchestrator))
            except Refuse:
                continue
            for fn in fns:
                for n in ast.walk(fn):
                    if isinstance(n, ast.Name):
                        used.add(n.id)
                    elif isinstance(n, ast.arg):
                        used.add(n.arg)
    if STATE_NAME in used:
        raise Refuse(
            f"{STATE_NAME!r} is already a name inside a phase or orchestrator;"
            " rename it there first"
        )
    return STATE_NAME


# --------------------------------------------------------------------------- #
# Converted-form check and await listing (stdlib ast only)
# --------------------------------------------------------------------------- #


def check_tree(pkg: Path) -> list[str]:
    problems = []
    for fname in sorted({f.module for f in FAMILIES}):
        tree = ast.parse((pkg / fname).read_text(encoding="utf-8"))
        for n in ast.walk(tree):
            if (
                isinstance(n, ast.Call)
                and isinstance(n.func, ast.Name)
                and n.func.id == "locals"
            ):
                problems.append(f"{fname}:{n.lineno}: locals() call")
            if (
                isinstance(n, (ast.FunctionDef, ast.AsyncFunctionDef))
                and n.name == "_phase_outputs"
            ):
                problems.append(f"{fname}:{n.lineno}: _phase_outputs is still defined")
        for fam in FAMILIES:
            if fam.module != fname:
                continue
            if not any(
                isinstance(n, ast.ClassDef) and n.name == fam.state_class
                for n in tree.body
            ):
                problems.append(f"{fname}: no {fam.state_class}")
            for p in _phase_defs(tree, fam):
                args = [a.arg for a in p.args.args]
                if len(args) != 2 or args[1] == "_in":
                    problems.append(f"{fname}:{p.lineno}: {p.name} takes {args}")
            try:
                orch = _find_orchestrator(tree, fam.orchestrator)
            except Refuse as exc:
                problems.append(f"{fname}: {exc}")
                continue
            if not any(
                isinstance(n, ast.Call)
                and isinstance(n.func, ast.Name)
                and n.func.id == fam.state_class
                for n in ast.walk(orch)
            ):
                problems.append(
                    f"{fname}: {fam.orchestrator} never builds {fam.state_class}"
                )
    return problems


def await_sequence(pkg: Path) -> list[str]:
    """Every await of each orchestrator then its phases, in source order.

    The state variable and ``locals()`` are both shown as ``<state>`` and
    ``ps.x`` as ``x``, so the old and the new form list the same lines when
    nothing was reordered.
    """
    out = []
    for fam in FAMILIES:
        tree = ast.parse((pkg / fam.module).read_text(encoding="utf-8"))
        orch = _find_orchestrator(tree, fam.orchestrator)
        var = None
        for n in ast.walk(orch):
            if (
                isinstance(n, ast.Assign)
                and isinstance(n.value, ast.Call)
                and isinstance(n.value.func, ast.Name)
                and n.value.func.id == fam.state_class
                and isinstance(n.targets[0], ast.Name)
            ):
                var = n.targets[0].id
        for fn in [orch, *sorted(_phase_defs(tree, fam), key=lambda n: n.lineno)]:
            for a in sorted(
                (n for n in ast.walk(fn) if isinstance(n, ast.Await)),
                key=lambda n: (n.lineno, n.col_offset),
            ):
                text = ast.unparse(a.value).replace(
                    "(self, locals())", "(self, <state>)"
                )
                if var:
                    text = text.replace(f"(self, {var})", "(self, <state>)")
                    text = re.sub(rf"\b{var}\.(?=[A-Za-z_])", "", text)
                out.append(f"{fam.orchestrator} | {fn.name} | {text}")
    return out


# --------------------------------------------------------------------------- #
# Rewriting (libcst)
# --------------------------------------------------------------------------- #


def _rewrite(src: str, plan: FamilyPlan) -> str:
    import libcst as cst
    import libcst.matchers as m

    S = plan.state_name
    fam = plan.family
    phase_by_name = {p.name: p for p in plan.phases}

    def st_attr(x: str) -> cst.Attribute:
        return cst.Attribute(value=cst.Name(S), attr=cst.Name(x))

    def target_names(t) -> set[str]:
        if isinstance(t, cst.Name):
            return {t.value}
        if isinstance(t, (cst.Tuple, cst.List)):
            out: set[str] = set()
            for e in t.elements:
                out |= target_names(e.value)
            return out
        if isinstance(t, cst.StarredElement):
            return target_names(t.value)
        return set()

    def alias_names(node) -> set[str]:
        if isinstance(node.names, cst.ImportStar):
            return set()
        out = set()
        for a in node.names:
            if a.asname is not None:
                out |= target_names(a.asname.name)
            else:
                n = a.name
                while isinstance(n, cst.Attribute):
                    n = n.value
                out.add(n.value)
        return out

    def bound_in(stmts) -> set[str]:
        """Names a scope body binds (without entering nested scopes)."""
        out: set[str] = set()
        removed: set[str] = set()

        class V(cst.CSTVisitor):
            def visit_FunctionDef(self, node):
                out.add(node.name.value)
                return False

            def visit_ClassDef(self, node):
                out.add(node.name.value)
                return False

            def visit_Lambda(self, node):
                return False

            def visit_ListComp(self, node):
                return False

            def visit_SetComp(self, node):
                return False

            def visit_DictComp(self, node):
                return False

            def visit_GeneratorExp(self, node):
                return False

            def visit_AssignTarget(self, node):
                out.update(target_names(node.target))

            def visit_AnnAssign(self, node):
                out.update(target_names(node.target))

            def visit_AugAssign(self, node):
                out.update(target_names(node.target))

            def visit_For(self, node):
                out.update(target_names(node.target))

            def visit_WithItem(self, node):
                if node.asname is not None:
                    out.update(target_names(node.asname.name))

            def visit_ExceptHandler(self, node):
                if node.name is not None:
                    out.update(target_names(node.name.name))

            def visit_Import(self, node):
                out.update(alias_names(node))

            def visit_ImportFrom(self, node):
                out.update(alias_names(node))

            def visit_Del(self, node):
                out.update(target_names(node.target))

            def visit_NamedExpr(self, node):
                out.update(target_names(node.target))

            def visit_Global(self, node):
                removed.update(n.name.value for n in node.names)

            def visit_Nonlocal(self, node):
                removed.update(n.name.value for n in node.names)

        for s in stmts:
            s.visit(V())
        return out - removed

    def params_of(params) -> set[str]:
        out = {
            p.name.value
            for p in (*params.params, *params.posonly_params, *params.kwonly_params)
        }
        if isinstance(params.star_arg, cst.Param):
            out.add(params.star_arg.name.value)
        if params.star_kwarg is not None:
            out.add(params.star_kwarg.name.value)
        return out

    def comp_targets(node) -> set[str]:
        out: set[str] = set()
        f = node.for_in
        while f is not None:
            out |= target_names(f.target)
            f = f.inner_for_in
        return out

    def is_docstring(stmt) -> bool:
        return (
            isinstance(stmt, cst.SimpleStatementLine)
            and len(stmt.body) == 1
            and isinstance(stmt.body[0], cst.Expr)
            and isinstance(
                stmt.body[0].value, (cst.SimpleString, cst.ConcatenatedString)
            )
        )

    doc_re = re.compile(
        r"Split out of ``(?P<m>\w+)`` with its body unchanged; ``_in``\s+is the\s+"
        r"(?:open's|loop's) ``locals\(\)`` and the result the names the\s+"
        r"(?:rest of the open\s+reads back \(see ``_phase_outputs``\)\."
        r"|loop reads back, or a\s+sentinel for a ``continue``, ``break`` or"
        r"\s+``return`` of the loop\.)"
    )

    def fix_docstring(body, pname):
        if not body or not is_docstring(body[0]):
            return body
        expr = body[0].body[0]
        s = expr.value
        mo = doc_re.search(s.value) if isinstance(s, cst.SimpleString) else None
        if mo is None:
            print(
                f"warning: {pname}: docstring not recognised, left as is",
                file=sys.stderr,
            )
            return body
        if fam.loop:
            new = (
                f"Split out of ``{mo['m']}``; reads and writes the loop's state\n"
                f"    through ``{S}`` (a ``{fam.state_class}``) and returns a sentinel for a\n"
                "    ``continue``, ``break`` or ``return`` of the loop, else None."
            )
        else:
            new = (
                f"Split out of ``{mo['m']}``; reads and writes the open's\n"
                f"    state through ``{S}`` (a ``{fam.state_class}``)."
            )
        val = s.value[: mo.start()] + new + s.value[mo.end() :]
        new_expr = expr.with_changes(value=s.with_changes(value=val))
        return [body[0].with_changes(body=[new_expr]), *body[1:]]

    noqa_re = re.compile(r"#\s*noqa:\s*([A-Z]+[0-9]+(?:\s*,\s*[A-Z]+[0-9]+)*)")

    def strip_noqa(node, codes: set[str]):
        """Drop ``# noqa`` comments for ``codes`` that the rewrite made unused."""

        class Strip(cst.CSTTransformer):
            def leave_TrailingWhitespace(self, original, updated):
                c = updated.comment
                mo = noqa_re.match(c.value) if c is not None else None
                if mo and {x.strip() for x in mo.group(1).split(",")} <= codes:
                    return updated.with_changes(
                        comment=None, whitespace=cst.SimpleWhitespace("")
                    )
                return updated

        return node.visit(Strip())

    class Frame:
        __slots__ = ("kind", "names", "suspended", "target")

        def __init__(self, kind, names, target=False):
            self.kind, self.names, self.target = kind, names, target
            self.suspended = False

    class FunctionRewriter(cst.CSTTransformer):
        """Rewrites one phase, or the orchestrator."""

        def __init__(self, fn, attr: set[str], phase: PhasePlan | None):
            super().__init__()
            self.fn = fn
            self.attr = attr
            self.phase = phase  # None for the orchestrator
            self.frames: list[Frame] = []
            self.nonref: set[int] = set()
            self.outer_compfor: set[int] = set()
            self.created_returns: set[int] = set()
            self.pending_lines: list = []
            self._collect_nonref(fn)

        def _collect_nonref(self, fn):
            """Name nodes that are not variable references."""
            nonref = self.nonref

            def dotted(x):
                while isinstance(x, cst.Attribute):
                    nonref.add(id(x.attr))
                    x = x.value
                if isinstance(x, cst.Name):
                    nonref.add(id(x))

            class C(cst.CSTVisitor):
                def visit_Attribute(self, node):
                    nonref.add(id(node.attr))

                def visit_Arg(self, node):
                    if node.keyword is not None:
                        nonref.add(id(node.keyword))

                def visit_FunctionDef(self, node):
                    nonref.add(id(node.name))

                def visit_ClassDef(self, node):
                    nonref.add(id(node.name))

                def visit_Param(self, node):
                    nonref.add(id(node.name))

                def visit_ImportAlias(self, node):
                    dotted(node.name)
                    if node.asname is not None:
                        dotted(node.asname.name)

                def visit_ImportFrom(self, node):
                    if node.module is not None:
                        dotted(node.module)

                def visit_NameItem(self, node):
                    nonref.add(id(node.name))

                def visit_ExceptHandler(self, node):
                    if node.name is not None:
                        dotted(node.name.name)

                def visit_FormattedStringExpression(self, node):
                    if node.equal is not None:
                        raise Refuse(f"{fn.name.value}: f-string '=' specifier")

            fn.visit(C())

        # -- scopes -------------------------------------------------------------
        def _resolve(self, name: str):
            innermost = True
            for fr in reversed(self.frames):
                if fr.suspended:
                    continue
                if fr.kind == "class" and not innermost:
                    continue
                innermost = False
                if name in fr.names:
                    return fr
            return None

        def _in_target_scope(self) -> bool:
            for fr in reversed(self.frames):
                if not fr.suspended:
                    return fr.target
            return False

        def visit_FunctionDef_body(self, node):
            names = bound_in(node.body.body) | params_of(node.params)
            if node is self.fn:
                self.frames.append(Frame("function", names | self.attr, target=True))
            else:
                self.frames.append(Frame("function", names))

        def leave_FunctionDef_body(self, node):
            self.frames.pop()

        def visit_ClassDef_body(self, node):
            self.frames.append(Frame("class", bound_in(node.body.body)))

        def leave_ClassDef_body(self, node):
            self.frames.pop()

        def visit_Lambda_body(self, node):
            self.frames.append(Frame("function", params_of(node.params)))

        def leave_Lambda_body(self, node):
            self.frames.pop()

        def _enter_comp(self, node):
            self.outer_compfor.add(id(node.for_in))
            self.frames.append(Frame("comp", comp_targets(node)))

        def visit_ListComp(self, node):
            self._enter_comp(node)

        def leave_ListComp(self, original, updated):
            self.frames.pop()
            return updated

        def visit_SetComp(self, node):
            self._enter_comp(node)

        def leave_SetComp(self, original, updated):
            self.frames.pop()
            return updated

        def visit_DictComp(self, node):
            self._enter_comp(node)

        def leave_DictComp(self, original, updated):
            self.frames.pop()
            return updated

        def visit_GeneratorExp(self, node):
            self._enter_comp(node)

        def leave_GeneratorExp(self, original, updated):
            self.frames.pop()
            return updated

        def visit_CompFor_iter(self, node):
            if id(node) in self.outer_compfor:
                self.frames[-1].suspended = True

        def leave_CompFor_iter(self, node):
            if id(node) in self.outer_compfor:
                self.frames[-1].suspended = False

        # -- references ---------------------------------------------------------
        def leave_Name(self, original, updated):
            x = original.value
            if id(original) in self.nonref or x not in self.attr or not self.frames:
                return updated
            fr = self._resolve(x)
            if fr is not None and fr.target:
                return st_attr(x)
            return updated

        def leave_Nonlocal(self, original, updated):
            keep = []
            for n in original.names:
                x = n.name.value
                # Resolve from the enclosing function, not the declaring one.
                fr = None
                for f in reversed(self.frames[:-1]):
                    if not f.suspended and f.kind != "class" and x in f.names:
                        fr = f
                        break
                if x in self.attr and fr is not None and fr.target:
                    if self.phase is not None:
                        raise Refuse(f"{self.fn.name.value}: nonlocal {x} on a field")
                    # The orchestrator keeps every use of the field on the
                    # state, so the closure writes the same place it reads.
                    continue
                keep.append(n)
            if len(keep) == len(original.names):
                return updated
            if not keep:
                return cst.RemoveFromParent()
            keep[-1] = keep[-1].with_changes(comma=cst.MaybeSentinel.DEFAULT)
            return updated.with_changes(names=keep)

        def leave_AnnAssign(self, original, updated):
            if m.matches(updated.target, m.Attribute(value=m.Name(S))):
                if updated.value is None:
                    return updated  # dropped with its line in leave_IndentedBlock
                return cst.Assign(
                    targets=[cst.AssignTarget(target=updated.target)],
                    value=updated.value,
                )
            return updated

        def leave_Call(self, original, updated):
            if (
                self.phase is None
                and m.matches(updated, m.Call(func=m.Name("locals"), args=[]))
                and self._in_target_scope()
            ):
                return cst.Name(S)
            return updated

        def leave_Return(self, original, updated):
            if self.phase is None or not self._in_target_scope():
                return updated
            v = updated.value
            if m.matches(v, m.Call(func=m.Name("_phase_outputs"))):
                if self.phase.signals:
                    new = updated.with_changes(value=cst.Name("None"))
                else:
                    new = updated.with_changes(
                        value=None, whitespace_after_return=cst.MaybeSentinel.DEFAULT
                    )
                self.created_returns.add(id(new))
                return new
            if m.matches(
                v,
                m.Call(
                    func=m.Name("_LoopContinue") | m.Name("_LoopBreak"),
                    args=[m.Arg(value=m.Call(func=m.Name("_phase_outputs")))],
                ),
            ):
                return updated.with_changes(
                    value=v.with_changes(
                        args=[], whitespace_before_args=cst.SimpleWhitespace("")
                    )
                )
            return updated

        # -- statement lists ----------------------------------------------------
        def _sync_names(self, stmt) -> list[str]:
            if self.phase is None or not self.phase.sync:
                return []
            names: set[str] = set()
            if isinstance(stmt, cst.SimpleStatementLine):
                for small in stmt.body:
                    if isinstance(small, cst.Assign):
                        for t in small.targets:
                            names |= target_names(t.target)
                    elif isinstance(small, cst.AugAssign) or (
                        isinstance(small, cst.AnnAssign) and small.value is not None
                    ):
                        names |= target_names(small.target)
                    elif isinstance(small, (cst.Import, cst.ImportFrom)):
                        names |= alias_names(small)
            elif isinstance(stmt, (cst.FunctionDef, cst.ClassDef)):
                names.add(stmt.name.value)
            return sorted(names & self.phase.sync)

        def _sync_line(self, x: str):
            return cst.SimpleStatementLine(
                body=[
                    cst.Assign(
                        targets=[cst.AssignTarget(target=st_attr(x))], value=cst.Name(x)
                    )
                ]
            )

        def _prepend_sync(self, updated, names):
            if not names:
                return updated
            body = updated.body
            return updated.with_changes(
                body=body.with_changes(
                    body=[*(self._sync_line(x) for x in names), *body.body]
                )
            )

        def leave_For(self, original, updated):
            if self.phase is not None and self._in_target_scope():
                return self._prepend_sync(
                    updated, sorted(target_names(updated.target) & self.phase.sync)
                )
            return updated

        def leave_With(self, original, updated):
            if self.phase is not None and self._in_target_scope():
                names: set[str] = set()
                for item in updated.items:
                    if item.asname is not None:
                        names |= target_names(item.asname.name)
                return self._prepend_sync(updated, sorted(names & self.phase.sync))
            return updated

        def _readback(self, stmt, var: str) -> str | None:
            t = stmt.test
            if (
                isinstance(t, cst.Comparison)
                and isinstance(t.left, cst.SimpleString)
                and len(t.comparisons) == 1
                and isinstance(t.comparisons[0].operator, cst.In)
                and m.matches(t.comparisons[0].comparator, m.Name(var))
            ):
                return t.left.evaluated_value  # type: ignore[return-value]
            return None

        def leave_IndentedBlock(self, original, updated):
            if not self._in_target_scope():
                return updated
            out = []
            pending: list = []
            for stmt in updated.body:
                if isinstance(stmt, cst.If):
                    key = self._readback(
                        stmt, "_in" if self.phase is not None else "_o"
                    )
                    if key is not None:
                        if self.phase is not None and key in self.phase.prologue:
                            if key in self.phase.guarded:
                                code = f"if hasattr({S}, {key!r}):\n    {key} = {S}.{key}\n"
                            else:
                                code = f"{key} = {S}.{key}\n"
                            out.append(
                                cst.parse_statement(code).with_changes(
                                    leading_lines=[*pending, *stmt.leading_lines]
                                )
                            )
                            pending = []
                        else:
                            pending += list(stmt.leading_lines)
                        continue
                if self.phase is None:
                    stmt = self._orch_stmt(stmt)
                    if stmt is None:
                        continue
                if m.matches(
                    stmt,
                    m.SimpleStatementLine(
                        body=[
                            m.AnnAssign(target=m.Attribute(value=m.Name(S)), value=None)
                        ]
                    ),
                ):
                    # ``x: T`` only declared a local; the type is on the field now.
                    pending += list(stmt.leading_lines)
                    tc = stmt.trailing_whitespace.comment
                    if tc is not None:
                        pending.append(cst.EmptyLine(comment=tc))
                    continue
                if pending:
                    stmt = stmt.with_changes(
                        leading_lines=[*pending, *stmt.leading_lines]
                    )
                    pending = []
                synced = self._sync_names(stmt)
                if synced:
                    stmt = strip_noqa(stmt, {"F401"})  # the mirror line uses it now
                if m.matches(
                    stmt,
                    m.SimpleStatementLine(
                        body=[
                            m.Assign(
                                targets=[
                                    m.AssignTarget(target=m.Attribute(value=m.Name(S)))
                                ]
                            )
                        ]
                    ),
                ):
                    stmt = strip_noqa(stmt, {"F841", "RUF006"})  # an attribute is kept
                out.append(stmt)
                out.extend(self._sync_line(x) for x in synced)
            if not out:
                out = [cst.SimpleStatementLine(body=[cst.Pass()])]
            return updated.with_changes(body=out)

        def _orch_stmt(self, stmt):
            # _flow = _o if isinstance(_o, (_LoopContinue, _LoopBreak)) else None
            if m.matches(
                stmt,
                m.SimpleStatementLine(
                    body=[m.Assign(targets=[m.AssignTarget(target=m.Name("_flow"))])]
                ),
            ):
                return None
            if isinstance(stmt, cst.If):
                # if _flow is not None: _o = _flow.outputs
                if m.matches(stmt.test, m.Comparison(left=m.Name("_flow"))):
                    return None
                # if isinstance(_flow, _LoopBreak): break
                if m.matches(
                    stmt.test,
                    m.Call(
                        func=m.Name("isinstance"),
                        args=[m.Arg(value=m.Name("_flow")), m.DoNotCare()],
                    ),
                ):
                    args = list(stmt.test.args)
                    args[0] = args[0].with_changes(value=cst.Name("_o"))
                    return stmt.with_changes(test=stmt.test.with_changes(args=args))
            # _o = await P(self, ps) -> await P(self, ps) when P never signals
            if m.matches(
                stmt,
                m.SimpleStatementLine(
                    body=[
                        m.Assign(
                            targets=[m.AssignTarget(target=m.Name("_o"))],
                            value=m.Await(expression=m.Call(func=m.Name())),
                        )
                    ]
                ),
            ):
                assign = stmt.body[0]
                p = phase_by_name.get(assign.value.expression.func.value)
                if p is not None and not p.signals:
                    return stmt.with_changes(body=[cst.Expr(value=assign.value)])
            return stmt

        def leave_FunctionDef(self, original, updated):
            if original is not self.fn:
                return updated
            body = list(updated.body.body)
            if self.phase is not None:
                last = body[-1] if body else None
                if (
                    isinstance(last, cst.SimpleStatementLine)
                    and len(last.body) == 1
                    and id(last.body[0]) in self.created_returns
                    and not self.phase.signals
                ):
                    body = body[:-1]
                ann = " | ".join(
                    [s for s in SIGNALS if s in self.phase.signals] + ["None"]
                )
                params = list(updated.params.params)
                params[1] = params[1].with_changes(
                    name=cst.Name(S),
                    annotation=cst.Annotation(annotation=cst.Name(fam.state_class)),
                )
                return updated.with_changes(
                    params=updated.params.with_changes(params=params),
                    returns=cst.Annotation(annotation=cst.parse_expression(ann)),
                    body=updated.body.with_changes(
                        body=fix_docstring(body, self.phase.name)
                    ),
                )
            # The orchestrator builds the state first thing (after its docstring).
            line = f"{S} = {fam.state_class}({', '.join(f'{x}={x}' for x in plan.init_fields)})"
            if len(line) + 8 > 88:
                inner = "".join(f"    {x}={x},\n" for x in plan.init_fields)
                line = f"{S} = {fam.state_class}(\n{inner})"
            ctor = cst.parse_statement(line + "\n")
            idx = 1 if body and is_docstring(body[0]) else 0
            if idx < len(body):
                ctor = ctor.with_changes(leading_lines=body[idx].leading_lines)
                body[idx] = body[idx].with_changes(leading_lines=[])
            body.insert(idx, ctor)
            return updated.with_changes(body=updated.body.with_changes(body=body))

    module = cst.parse_module(src)

    replacements: dict[int, Any] = {}
    for stmt in module.body:
        if isinstance(stmt, cst.FunctionDef) and stmt.name.value in phase_by_name:
            p = phase_by_name[stmt.name.value]
            replacements[id(stmt)] = stmt.visit(FunctionRewriter(stmt, p.attr, p))

    found: list = []

    class FindOrch(cst.CSTVisitor):
        def visit_FunctionDef(self, node):
            if node.name.value == fam.orchestrator:
                found.append(node)

    module.visit(FindOrch())
    if len(found) != 1:
        raise Refuse(f"{fam.orchestrator}: not found by libcst")
    orch = found[0]
    replacements[id(orch)] = orch.visit(
        FunctionRewriter(orch, set(plan.orch_fields), None)
    )

    class Replace(cst.CSTTransformer):
        def leave_FunctionDef(self, original, updated):
            return replacements.get(id(original), updated)

    module = module.visit(Replace())
    return _module_level(module, plan, cst, m).code


def _state_class_source(plan: FamilyPlan) -> str:
    fam = plan.family
    writers: dict[str, list[str]] = {x: [] for x in plan.fields}
    for x in sorted(plan.orch_writes):
        if x not in plan.init_fields:
            writers[x].append("the " + ("loop" if fam.loop else "orchestrator"))
    for p in plan.phases:
        for x in sorted(p.writes):
            writers[x].append(p.name[len(fam.prefix) :])
    run = "pass of" if fam.loop else "call of"
    lines = [
        "@dataclasses.dataclass(slots=True, eq=False, repr=False)",
        f"class {fam.state_class}:",
        f'    """What the {"regions" if fam.loop else "phases"} of one {run} ``{fam.orchestrator}`` share.',
        "",
        "    One field per name that crosses a phase boundary; each phase reads and",
        f"    writes them on ``{plan.state_name}`` instead of through the caller's ``locals()``.",
    ]
    if plan.init_fields:
        lines += [
            "    The orchestrator's arguments are the constructor arguments. Every",
            "    other field starts unset: reading one before something writes it",
        ]
    else:
        lines += [
            "    Every field starts unset: reading one before something writes it",
        ]
    lines += [
        "    raises ``AttributeError``, as reading the unbound local did. The",
        "    comment on a field names what writes it. Generated by",
        f"    ``{GENERATED_BY}``; see docs/CODEMODS.md.",
        '    """',
        "",
    ]
    for x in plan.fields:
        t = _field_type(plan, x)
        if x in plan.init_fields:
            lines.append(f'    {x}: "{t}"')
            continue
        who = ", ".join(writers[x])
        decl = f'    {x}: "{t}" = dataclasses.field(init=False)'
        comment = f"  # {who}"
        if len(decl) + len(comment) > 88:
            lines.append(f"    # {who}")
            lines.append(decl)
        else:
            lines.append(decl + comment)
    return "\n".join(lines) + "\n"


def _module_level(module, plan: FamilyPlan, cst, m):
    fam = plan.family
    cls = cst.parse_statement(_state_class_source(plan))
    out = []
    inserted = False
    for stmt in module.body:
        if isinstance(stmt, cst.FunctionDef) and stmt.name.value == "_phase_outputs":
            continue
        if isinstance(stmt, cst.ClassDef) and stmt.name.value in (
            "_LoopContinue",
            "_LoopBreak",
        ):
            stmt = _signal_class(stmt, cst)
        if (
            not inserted
            and isinstance(stmt, cst.FunctionDef)
            and stmt.name.value == plan.first_def
        ):
            out.append(cls.with_changes(leading_lines=stmt.leading_lines))
            stmt = stmt.with_changes(leading_lines=[cst.EmptyLine(), cst.EmptyLine()])
            inserted = True
        out.append(stmt)
    if not inserted:
        raise Refuse(f"{fam.state_class}: no place to insert it")
    return _imports(module.with_changes(body=out), cst, m)


def _signal_class(stmt, cst):
    name = stmt.name.value
    if name == "_LoopContinue":
        src = 'class _LoopContinue:\n    """A region continuing the loop."""\n\n    __slots__ = ()\n'
    else:
        src = (
            "class _LoopBreak:\n"
            '    """A region breaking out of the loop.\n\n'
            "    Not a subclass of ``_LoopContinue``: the loops test for a continue with\n"
            "    ``isinstance``, and a break that matched it would continue instead.\n"
            '    """\n\n'
            "    __slots__ = ()\n"
        )
    return cst.parse_statement(src).with_changes(leading_lines=stmt.leading_lines)


def _imports(module, cst, m):
    body = list(module.body)
    has_dc = any(
        m.matches(
            s,
            m.SimpleStatementLine(
                body=[
                    m.Import(
                        names=[m.ImportAlias(name=m.Name("dataclasses"), asname=None)]
                    )
                ]
            ),
        )
        for s in body
    )
    out = []
    anchored = False
    for s in body:
        if not anchored and m.matches(
            s, m.SimpleStatementLine(body=[m.ImportFrom(module=m.Name("typing"))])
        ):
            anchored = True
            if not has_dc:
                out.append(
                    cst.parse_statement("import dataclasses\n").with_changes(
                        leading_lines=s.leading_lines
                    )
                )
                s = s.with_changes(leading_lines=[])
            imp = s.body[0]
            names = [a.name.value for a in imp.names]
            new = list(names)
            for n in ("Any", "Optional"):
                if n in new:
                    continue
                pos = 1 if new and new[0] == "TYPE_CHECKING" else 0
                while pos < len(new) and new[pos] < n:
                    pos += 1
                new.insert(pos, n)
            if new != names:
                aliases = [
                    cst.ImportAlias(
                        name=cst.Name(n),
                        comma=cst.Comma(whitespace_after=cst.SimpleWhitespace(" "))
                        if i < len(new) - 1
                        else cst.MaybeSentinel.DEFAULT,
                    )
                    for i, n in enumerate(new)
                ]
                s = s.with_changes(body=[imp.with_changes(names=aliases)])
        out.append(s)
    if not anchored:
        raise Refuse("no `from typing import` line to put the new imports next to")
    return module.with_changes(body=out)


# --------------------------------------------------------------------------- #
# Driver
# --------------------------------------------------------------------------- #


def convert(pkg: Path, verbose: bool = True) -> dict[str, str]:
    """New source for each module file with a family still in the old form."""
    sources = {f.module: (pkg / f.module).read_text(encoding="utf-8") for f in FAMILIES}
    state_name = None
    changed: dict[str, str] = {}
    for fam in FAMILIES:
        src = changed.get(fam.module, sources[fam.module])
        if not family_is_old_form(ast.parse(src), fam):
            if verbose:
                print(f"{fam.orchestrator}: already converted", file=sys.stderr)
            continue
        if state_name is None:
            state_name = choose_state_name(sources)
        plan = plan_family(src, str(pkg / fam.module), fam, state_name)
        new = _rewrite(src, plan)
        ast.parse(new)
        changed[fam.module] = new
        if verbose:
            _report(plan)
    return changed


def _report(plan: FamilyPlan) -> None:
    fam = plan.family
    print(
        f"{fam.orchestrator}: {len(plan.phases)} phases, {len(plan.fields)} fields"
        f" ({len(plan.init_fields)} constructor arguments)",
        file=sys.stderr,
    )
    for p in plan.phases:
        notes = []
        if p.sync:
            notes.append(f"mirrored after binding {sorted(p.sync)}")
        if p.prologue:
            notes.append(f"copied in {sorted(p.prologue)}")
        if p.guarded:
            notes.append(f"copied in only if set {sorted(p.guarded)}")
        kept = sorted(p.local - p.sync - p.prologue)
        if kept:
            notes.append(f"phase-local {kept}")
        if notes:
            print(f"  {p.name}: " + "; ".join(notes), file=sys.stderr)


def main(argv: list[str] | None = None) -> int:
    ap = argparse.ArgumentParser(description=(__doc__ or "").split("\n\n")[0])
    ap.add_argument("package", type=Path, help="the aidot_cameras/camera directory")
    g = ap.add_mutually_exclusive_group()
    g.add_argument(
        "--check", action="store_true", help="exit 1 unless already converted"
    )
    g.add_argument(
        "--awaits",
        action="store_true",
        help="print each orchestrator's and its phases' awaits in source order",
    )
    g.add_argument("--dry-run", action="store_true", help="convert in memory only")
    ap.add_argument(
        "--no-format",
        action="store_true",
        help="do not run `ruff format` on the rewritten files",
    )
    args = ap.parse_args(argv)
    pkg: Path = args.package
    if args.awaits:
        print("\n".join(await_sequence(pkg)))
        return 0
    if args.check:
        problems = check_tree(pkg)
        for p in problems:
            print(p, file=sys.stderr)
        if problems:
            print(f"not in converted form ({len(problems)} problems)", file=sys.stderr)
            return 1
        print("converted form: ok", file=sys.stderr)
        return 0
    try:
        changed = convert(pkg)
    except Refuse as exc:
        print(f"refusing: {exc}", file=sys.stderr)
        return 2
    if args.dry_run:
        print(f"would rewrite: {sorted(changed)}", file=sys.stderr)
        return 0
    written = []
    for fname, src in sorted(changed.items()):
        (pkg / fname).write_text(src, encoding="utf-8")
        written.append(str(pkg / fname))
        print(f"rewrote {pkg / fname}", file=sys.stderr)
    if written and not args.no_format:
        return _ruff_format(written)
    return 0


def _ruff_format(paths: list[str]) -> int:
    """Format the rewritten files the way the rest of the tree is formatted.

    The rewrite lengthens lines (``x`` becomes ``ps.x``) and joins or splits
    statements; ``ruff format`` puts them back in the repository's style.
    """
    for cmd in (["ruff"], [sys.executable, "-m", "ruff"], ["uvx", "ruff"]):
        if cmd[0] != sys.executable and shutil.which(cmd[0]) is None:
            continue
        probe = subprocess.run([*cmd, "--version"], capture_output=True, text=True)
        if probe.returncode != 0:
            continue
        print(f"formatting with {probe.stdout.strip()}", file=sys.stderr)
        return subprocess.run([*cmd, "format", *paths]).returncode
    print(
        "ruff not found: run `ruff format` on the rewritten files before committing",
        file=sys.stderr,
    )
    return 1


if __name__ == "__main__":
    sys.exit(main())
