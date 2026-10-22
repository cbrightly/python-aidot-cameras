"""What a phase or region binds reaches every later reader of it.

The two stream opens and the two serve loops run as phase and region
coroutines (see ``tests/source_parts.py``). A phase receives the method's
``locals()``, runs its statements, and hands back the names it bound through
``_phase_outputs(locals(), (...))``; the method reads each one back with
``if "x" in _o: x = _o["x"]``. In the single-function form every assignment
was visible to every later line, so after the split two things must hold:

1. A name a phase binds that a later phase takes in (its ``if "x" in _in``
   prologue) or that the method reads after the call is in the phase's export
   tuple, and the method reads back exactly what the phase exports. A name
   left out is silently dropped: the later reader sees the method's older
   value, or none at all.
2. A function or lambda defined in a phase does not capture a phase local
   that a later phase rebinds. In one function the closure saw the rebinding
   through the shared cell; after the split it keeps the phase's cell and
   never sees it.

For the serve loops "later" also wraps around: the next pass of the loop
reads every region's inputs again, the region's own included.
"""

import ast
import importlib
import inspect
import symtable

import pytest

from tests.source_parts import PARTS

_COMPREHENSIONS = (ast.ListComp, ast.SetComp, ast.DictComp, ast.GeneratorExp)
_SCOPES = (ast.FunctionDef, ast.AsyncFunctionDef, ast.Lambda, ast.ClassDef)
_COMPREHENSION_TABLES = {"listcomp", "setcomp", "dictcomp", "genexpr"}
_LOOPS = {"_sdes_keepalive_loop_inner", "_dtls_serve_loop_inner"}


def _readback_name(stmt, var):
    """``x`` for ``if "x" in <var>: x = <var>["x"]``, else None."""
    if not isinstance(stmt, ast.If) or stmt.orelse or len(stmt.body) != 1:
        return None
    t = stmt.test
    if not (
        isinstance(t, ast.Compare)
        and isinstance(t.left, ast.Constant)
        and isinstance(t.left.value, str)
        and len(t.comparators) == 1
        and isinstance(t.comparators[0], ast.Name)
        and t.comparators[0].id == var
        and isinstance(stmt.body[0], ast.Assign)
    ):
        return None
    return t.left.value


def _own_nodes(fn):
    """Nodes of ``fn``'s own scope: not inside a nested def, lambda or class."""
    stack = list(ast.iter_child_nodes(fn))
    while stack:
        node = stack.pop()
        yield node
        if not isinstance(node, _SCOPES):
            stack.extend(ast.iter_child_nodes(node))


def _binds(fn, skip=()):
    """Names ``fn`` itself binds, outside the statements in ``skip``.

    A comprehension's targets are its own (they never leak), but a walrus in
    one binds in the enclosing function. An ``except E as e`` name is left
    out too: Python unbinds it when the handler ends, so nothing after the
    handler can read it in either form.
    """
    out = set()
    stack = [s for s in fn.body if s not in skip]
    while stack:
        node = stack.pop()
        if isinstance(node, (ast.FunctionDef, ast.AsyncFunctionDef, ast.ClassDef)):
            out.add(node.name)
            continue
        if isinstance(node, ast.Lambda):
            continue
        if isinstance(node, _COMPREHENSIONS):
            out |= {
                n.target.id
                for n in ast.walk(node)
                if isinstance(n, ast.NamedExpr) and isinstance(n.target, ast.Name)
            }
            continue
        if isinstance(node, ast.Name) and isinstance(node.ctx, (ast.Store, ast.Del)):
            out.add(node.id)
        elif isinstance(node, (ast.Import, ast.ImportFrom)):
            out |= {(a.asname or a.name).split(".")[0] for a in node.names}
        elif isinstance(node, (ast.MatchAs, ast.MatchStar)) and node.name:
            out.add(node.name)
        stack.extend(ast.iter_child_nodes(node))
    return out


def _loads(nodes):
    return {
        n.id for n in nodes if isinstance(n, ast.Name) and isinstance(n.ctx, ast.Load)
    }


def _export_tuples(fn):
    """Each ``_phase_outputs(..., (names))`` call in ``fn``, as (line, names)."""
    found = []
    for node in _own_nodes(fn):
        if (
            isinstance(node, ast.Call)
            and isinstance(node.func, ast.Name)
            and node.func.id == "_phase_outputs"
        ):
            arg = node.args[1]
            assert isinstance(arg, ast.Tuple) and all(
                isinstance(e, ast.Constant) and isinstance(e.value, str)
                for e in arg.elts
            ), f"{fn.name} line {node.lineno}: export list is not a literal tuple"
            found.append((node.lineno, {e.value for e in arg.elts}))
    return found


def _captured(table):
    """Locals of ``table``'s function that a def or lambda nested in it closes over."""
    own = {s.get_name() for s in table.get_symbols() if s.is_local()}
    out = set()
    stack = list(table.get_children())
    while stack:
        t = stack.pop()
        stack.extend(t.get_children())
        if t.get_name().strip("<>") in _COMPREHENSION_TABLES:
            continue
        out |= {s.get_name() for s in t.get_symbols() if s.is_free()} & own
    return out


class _Phase:
    def __init__(self, fn, table):
        self.name = fn.name
        prologue = set()
        self.inputs = set()
        for stmt in fn.body:
            k = _readback_name(stmt, "_in")
            if k:
                self.inputs.add(k)
                prologue.add(stmt)
        self.binds = _binds(fn, prologue)
        self.exports = _export_tuples(fn)
        self.captured = _captured(table)


class _Call:
    def __init__(self, phase, line, readback, loop):
        self.phase, self.line, self.readback, self.loop = phase, line, readback, loop


class _Method:
    def __init__(self, calls, own, from_closures, phases, counts):
        self.calls, self.own, self.phases = calls, own, phases
        self.from_closures, self.counts = from_closures, counts


def _analyse(method):
    """The phases ``method`` calls, in order, and what it reads back from each."""
    modname, prefix = PARTS[method]
    mod = importlib.import_module(modname)
    path = inspect.getsourcefile(mod)
    with open(path, encoding="utf-8") as f:
        src = f.read()
    tree = ast.parse(src)
    tables = {
        t.get_name(): t for t in symtable.symtable(src, path, "exec").get_children()
    }
    phases = {
        n.name: _Phase(n, tables[n.name])
        for n in tree.body
        if isinstance(n, ast.AsyncFunctionDef) and n.name.startswith(prefix)
    }
    (orch,) = [
        n
        for n in ast.walk(tree)
        if isinstance(n, ast.AsyncFunctionDef) and n.name == method
    ]

    parent = {}
    loop_of = {}
    for node in ast.walk(orch):
        for field in ("body", "orelse", "finalbody"):
            block = getattr(node, field, None)
            if isinstance(block, list):
                for i, s in enumerate(block):
                    parent[s] = (block, i)
        if isinstance(node, ast.While):
            for inner in ast.walk(node):
                loop_of.setdefault(inner, node)

    own = list(_own_nodes(orch))
    calls = []
    for node in own:
        if (
            isinstance(node, ast.Assign)
            and isinstance(node.value, ast.Await)
            and isinstance(node.value.value, ast.Call)
            and isinstance(node.value.value.func, ast.Name)
            and node.value.value.func.id in phases
        ):
            block, i = parent[node]
            readback = set()
            for s in block[i + 1 :]:
                if isinstance(s, ast.Assign) and isinstance(s.value, ast.Await):
                    break  # the next phase's call
                k = _readback_name(s, "_o")
                if k is not None:
                    readback.add(k)
            calls.append(
                _Call(
                    phases[node.value.value.func.id],
                    node.lineno,
                    readback,
                    loop_of.get(node) if method in _LOOPS else None,
                )
            )
    calls.sort(key=lambda c: c.line)

    # A name the method binds itself (other than by reading it back from a
    # phase) is its own when it reads it: a phase binding the same name
    # locally does not reach it, and never did. The method's ``except E as
    # exc`` and a phase's are two different ``exc``.
    readback_stmts = {s for s in own if _readback_name(s, "_o")}
    all_readback = set().union(*(c.readback for c in calls))
    own_bound = _binds(orch, readback_stmts)
    own_bound -= all_readback

    def _counts(name):
        return name not in own_bound

    (orch_table,) = [
        t
        for t in _tables(symtable.symtable(src, path, "exec"))
        if t.get_name() == method and t.get_lineno() == orch.lineno
    ]
    from_closures = {n for n in _captured(orch_table) if _counts(n)}
    return _Method(calls, own, from_closures, set(phases), _counts)


def _tables(t):
    yield t
    for c in t.get_children():
        yield from _tables(c)


_ANALYSED = {m: _analyse(m) for m in sorted(PARTS)}


def _later(calls, i):
    c = calls[i]
    if c.loop is not None:
        return [d for d in calls if d.loop is c.loop]
    return calls[i + 1 :]


def _read_after(method, i):
    m = _ANALYSED[method]
    c = m.calls[i]
    start = c.loop.lineno if c.loop is not None else c.line + 1
    names = {
        n
        for n in _loads(x for x in m.own if getattr(x, "lineno", 0) >= start)
        if m.counts(n)
    }
    names |= m.from_closures
    for d in _later(m.calls, i):
        names |= d.phase.inputs
    return names


def test_every_split_method_and_its_phases_are_found():
    assert set(_ANALYSED) == set(PARTS)
    for method, m in _ANALYSED.items():
        assert m.calls, f"{method}: no phase calls found"
        assert {c.phase.name for c in m.calls} == m.phases, method
        for c in m.calls:
            assert c.phase.exports, f"{c.phase.name} exports nothing"
        if method in _LOOPS:
            assert all(c.loop is not None for c in m.calls), method


_CASES = [
    (method, i, c.phase.name)
    for method, m in _ANALYSED.items()
    for i, c in enumerate(m.calls)
]
_IDS = [f"{method}:{phase}" for method, _, phase in _CASES]


@pytest.mark.parametrize("method,index,phase", _CASES, ids=_IDS)
def test_a_phase_exports_every_name_read_after_it(method, index, phase):
    c = _ANALYSED[method].calls[index]
    needed = c.phase.binds & _read_after(method, index)
    for line, names in c.phase.exports:
        missing = sorted(needed - names)
        assert not missing, (
            f"{phase} binds {missing} and a later phase or {method} reads "
            f"them, but the export at line {line} leaves them out"
        )


@pytest.mark.parametrize("method,index,phase", _CASES, ids=_IDS)
def test_the_method_reads_back_what_the_phase_exports(method, index, phase):
    c = _ANALYSED[method].calls[index]
    exported = set().union(*(names for _, names in c.phase.exports))
    assert exported == c.readback, (
        f"{method} after {phase}: exported but not read back "
        f"{sorted(exported - c.readback)}, read back but not exported "
        f"{sorted(c.readback - exported)}"
    )


@pytest.mark.parametrize("method,index,phase", _CASES, ids=_IDS)
def test_no_closure_in_a_phase_misses_a_later_rebinding(method, index, phase):
    calls = _ANALYSED[method].calls
    c = calls[index]
    stale = sorted(
        (name, d.phase.name)
        for d in _later(calls, index)
        if d is not c
        for name in c.phase.captured & d.phase.binds
    )
    assert not stale, (
        f"a function defined in {phase} closes over names a later phase "
        f"rebinds, and will keep the old value: {stale}"
    )
