"""Every name a phase or region reads from the shared state is written before it.

The two stream opens and the two serve loops run as phase and region
coroutines (see ``tests/source_parts.py``) that share one state object: the
orchestrator builds a ``@dataclass(slots=True)`` (``_DtlsOpenState`` and so
on), passes it to each phase as its second argument, and every name that
crosses a phase boundary is a field read and written as ``ps.<name>``. In the
single-function form every assignment was visible to every later line; with
the state object that holds only if:

1. every field a phase or the orchestrator reads is a constructor argument,
   or is written by the orchestrator or an earlier phase (or earlier in the
   same phase) - never only by a later phase, and never by nothing at all;
2. a phase that binds a plain local with a field's name does not leave a
   later reader of the field looking at an older value: that is the
   dropped-export bug rc58 shipped, in this form;
3. every attribute used on the state is a declared field, every field is
   both written and read, and every constructor field is passed;
4. no ``locals()`` call is left in the modules the phases live in.

For the serve loops "earlier" also wraps around: the next pass runs every
region again, so a region may read what a later region of the previous pass
wrote, as long as the loop or an earlier region wrote it before the first.
"""

import ast
import importlib
import inspect

import pytest

from tests.source_parts import PARTS

_LOOPS = {"_sdes_keepalive_loop_inner", "_dtls_serve_loop_inner"}

# Fields a later region of the previous pass writes before an earlier region
# reads them. The first pass cannot reach the read: it is behind a flow that
# only a previous pass sets up. Keep this empty unless a region really needs it.
_WRAPAROUND_READS: dict[str, set[str]] = {}

_SCOPES = (ast.FunctionDef, ast.AsyncFunctionDef, ast.Lambda, ast.ClassDef)


def _own_nodes(fn):
    stack = list(ast.iter_child_nodes(fn))
    while stack:
        node = stack.pop()
        yield node
        if not isinstance(node, _SCOPES):
            stack.extend(ast.iter_child_nodes(node))


def _target_names(t):
    if isinstance(t, ast.Name):
        return {t.id}
    if isinstance(t, (ast.Tuple, ast.List)):
        return set().union(*(_target_names(e) for e in t.elts)) if t.elts else set()
    if isinstance(t, ast.Starred):
        return _target_names(t.value)
    return set()


def _local_bindings(fn):
    """Plain names ``fn`` itself binds (not attributes, not comprehension targets)."""
    out = set()
    for node in _own_nodes(fn):
        if isinstance(node, ast.Name) and isinstance(node.ctx, (ast.Store, ast.Del)):
            out.add(node.id)
        elif isinstance(node, (ast.FunctionDef, ast.AsyncFunctionDef, ast.ClassDef)):
            out.add(node.name)
        elif isinstance(node, (ast.Import, ast.ImportFrom)):
            out |= {(a.asname or a.name).split(".")[0] for a in node.names}
        elif isinstance(node, ast.ExceptHandler) and node.name:
            out.add(node.name)
    return out


class _Access:
    def __init__(self, fn, var):
        """Reads and writes of ``var.<field>`` anywhere in ``fn``, with lines."""
        self.reads, self.writes = {}, {}
        self.attrs = set()
        # Reads inside a nested def or lambda happen when it is called, not
        # where it is defined: a closure may read what its phase writes later.
        nested = {
            id(node)
            for inner in ast.walk(fn)
            if inner is not fn and isinstance(inner, _SCOPES)
            for node in ast.walk(inner)
        }
        direct, late = {}, {}
        for node in ast.walk(fn):
            if isinstance(node, ast.AugAssign) and self._field(node.target, var):
                book = late if id(node) in nested else direct
                x = node.target.attr
                book[x] = min(book.get(x, node.lineno), node.lineno)
            if not self._field(node, var):
                continue
            self.attrs.add(node.attr)
            if isinstance(node.ctx, (ast.Store, ast.Del)):
                book = self.writes
            else:
                book = late if id(node) in nested else direct
            book[node.attr] = min(book.get(node.attr, node.lineno), node.lineno)
        # The line that needs a writer before it: the first direct read, or,
        # for a name only closures read, the first closure read.
        self.reads = {**late, **direct}
        self.only_late = set(late) - set(direct)

    @staticmethod
    def _field(node, var):
        return (
            isinstance(node, ast.Attribute)
            and isinstance(node.value, ast.Name)
            and node.value.id == var
        )


class _Family:
    def __init__(self, method):
        modname, prefix = PARTS[method]
        self.method = method
        self.module = importlib.import_module(modname)
        self.src = inspect.getsource(self.module)
        tree = ast.parse(self.src)
        self.tree = tree
        (self.orch,) = [
            n
            for n in ast.walk(tree)
            if isinstance(n, ast.AsyncFunctionDef) and n.name == method
        ]
        classes = {n.name: n for n in tree.body if isinstance(n, ast.ClassDef)}
        built = [
            n
            for n in self.orch.body
            if isinstance(n, ast.Assign)
            and isinstance(n.value, ast.Call)
            and isinstance(n.value.func, ast.Name)
            and n.value.func.id in classes
        ]
        assert len(built) == 1, f"{method} should build its state object once, first"
        self.var = built[0].targets[0].id
        self.ctor_kwargs = {k.arg for k in built[0].value.keywords}
        cls = classes[built[0].value.func.id]
        self.cls = cls
        self.fields, self.init_fields = [], set()
        for stmt in cls.body:
            if isinstance(stmt, ast.AnnAssign) and isinstance(stmt.target, ast.Name):
                self.fields.append(stmt.target.id)
                v = stmt.value
                init_false = isinstance(v, ast.Call) and any(
                    k.arg == "init"
                    and isinstance(k.value, ast.Constant)
                    and k.value.value is False
                    for k in v.keywords
                )
                if v is None or not init_false:
                    self.init_fields.add(stmt.target.id)
        self.phase_defs = {
            n.name: n
            for n in tree.body
            if isinstance(n, ast.AsyncFunctionDef) and n.name.startswith(prefix)
        }
        calls = []
        for node in _own_nodes(self.orch):
            if (
                isinstance(node, ast.Call)
                and isinstance(node.func, ast.Name)
                and node.func.id in self.phase_defs
            ):
                calls.append(node)
        calls.sort(key=lambda c: c.lineno)
        self.calls = calls
        self.order = [c.func.id for c in calls]
        self.call_line = {c.func.id: c.lineno for c in calls}
        self.loop = None
        if method in _LOOPS:
            (self.loop,) = [n for n in ast.walk(self.orch) if isinstance(n, ast.While)]
        self.access = {}
        for name, fn in self.phase_defs.items():
            args = [a.arg for a in fn.args.args]
            self.access[name] = _Access(fn, args[1] if len(args) == 2 else None)
        self.orch_access = _Access(self.orch, self.var)


_FAMILIES = {m: _Family(m) for m in sorted(PARTS)}
_IDS = sorted(PARTS)


@pytest.mark.parametrize("method", _IDS)
def test_the_orchestrator_calls_every_phase_once_with_the_state(method):
    fam = _FAMILIES[method]
    assert sorted(fam.order) == sorted(fam.phase_defs), (
        f"{method} calls {fam.order}, the module defines {sorted(fam.phase_defs)}"
    )
    assert len(set(fam.order)) == len(fam.order), f"{method} calls a phase twice"
    for call in fam.calls:
        assert [ast.unparse(a) for a in call.args] == ["self", fam.var], (
            f"{method} line {call.lineno}: {call.func.id} must get (self, {fam.var})"
        )
    for name, fn in fam.phase_defs.items():
        assert len(fn.args.args) == 2, f"{name} should take (self, state)"
        ann = fn.args.args[1].annotation
        assert ann is not None and ast.unparse(ann) == fam.cls.name, (
            f"{name}'s state argument should be annotated {fam.cls.name}"
        )
    if fam.loop is not None:
        inside = {id(n) for n in ast.walk(fam.loop)}
        assert all(id(c) in inside for c in fam.calls), (
            f"{method}: a region outside the loop"
        )


@pytest.mark.parametrize("method", _IDS)
def test_the_state_class_is_a_slotted_dataclass(method):
    cls = _FAMILIES[method].cls
    decos = [ast.unparse(d) for d in cls.decorator_list]
    assert any("dataclass(" in d and "slots=True" in d for d in decos), (
        f"{cls.name} must be @dataclasses.dataclass(slots=True): without slots a"
        " misspelt write creates a new attribute instead of failing"
    )


@pytest.mark.parametrize("method", _IDS)
def test_every_constructor_field_is_passed(method):
    fam = _FAMILIES[method]
    assert fam.ctor_kwargs == fam.init_fields, (
        f"{method} builds {fam.cls.name} with {sorted(fam.ctor_kwargs)}; its"
        f" constructor fields are {sorted(fam.init_fields)}"
    )


@pytest.mark.parametrize("method", _IDS)
def test_every_state_attribute_is_a_declared_field(method):
    fam = _FAMILIES[method]
    declared = set(fam.fields)
    for name, acc in [*fam.access.items(), (method, fam.orch_access)]:
        unknown = sorted(acc.attrs - declared)
        assert not unknown, f"{name} uses undeclared {fam.cls.name} fields {unknown}"


@pytest.mark.parametrize("method", _IDS)
def test_every_field_is_written_and_read(method):
    fam = _FAMILIES[method]
    written, read = set(fam.init_fields), set()
    for acc in [*fam.access.values(), fam.orch_access]:
        written |= set(acc.writes)
        read |= set(acc.reads)
    assert not set(fam.fields) - written, (
        f"{fam.cls.name}: never written {sorted(set(fam.fields) - written)}"
    )
    assert not set(fam.fields) - read, (
        f"{fam.cls.name}: never read {sorted(set(fam.fields) - read)} - a value"
        " nothing reads does not belong on the state"
    )


def _cases():
    for method, fam in _FAMILIES.items():
        for name in fam.order:
            yield method, name
        yield method, method


_CASES = list(_cases())


def _sources(fam):
    """For each field: who writes it, as (who, line) pairs."""
    out = {x: [] for x in fam.fields}
    for x in fam.init_fields:
        out[x].append(("constructor", 0))
    for x, line in fam.orch_access.writes.items():
        out[x].append(("orchestrator", line))
    for name in fam.order:
        for x, line in fam.access[name].writes.items():
            out[x].append((name, line))
    return out


@pytest.mark.parametrize("method,reader", _CASES, ids=[f"{m}:{r}" for m, r in _CASES])
def test_every_read_has_an_earlier_writer(method, reader):
    fam = _FAMILIES[method]
    writers = _sources(fam)
    loop = fam.loop
    if reader == method:
        reads = fam.orch_access.reads
    else:
        reads = fam.access[reader].reads
        own_late = fam.access[reader].only_late
        pos = fam.order.index(reader)
    problems = []
    for x, line in sorted(reads.items()):
        ok, later = False, []
        for who, wline in writers[x]:
            if who == "constructor":
                ok = True
            elif who == "orchestrator":
                if reader == method:
                    ok = ok or wline < line
                else:
                    # Before this phase is called, or anywhere in the loop body.
                    ok = (
                        ok
                        or wline < fam.call_line[reader]
                        or (
                            loop is not None and loop.lineno <= wline <= loop.end_lineno
                        )
                    )
            elif reader == method:
                ok = (
                    ok
                    or fam.call_line[who] < line
                    or (loop is not None and loop.lineno <= line <= loop.end_lineno)
                )
            elif who == reader and (wline < line or x in own_late):
                ok = True
            elif who != reader and fam.order.index(who) < pos:
                ok = True
            else:
                later.append(who)
        if not ok and later and loop is not None:
            ok = x in _WRAPAROUND_READS.get(reader, set())
        if not ok:
            problems.append(
                f"{x} (line {line}): "
                + (
                    f"only {later} write it, after {reader} runs"
                    if later
                    else "nothing writes it"
                )
            )
    assert not problems, (
        f"{reader} reads {fam.cls.name} fields with no earlier writer: {problems}"
    )


@pytest.mark.parametrize("method,reader", _CASES, ids=[f"{m}:{r}" for m, r in _CASES])
def test_a_local_never_shadows_a_field_a_later_reader_needs(method, reader):
    """The rc58 shape: a phase binds ``x`` where it should have written ``ps.x``."""
    fam = _FAMILIES[method]
    if reader == method:
        return
    fn = fam.phase_defs[reader]
    acc = fam.access[reader]
    shadowing = (_local_bindings(fn) & set(fam.fields)) - set(acc.writes)
    if not shadowing:
        return
    pos = fam.order.index(reader)
    later = fam.order if fam.loop is not None else fam.order[pos + 1 :]
    hits = []
    for x in sorted(shadowing):
        if x in acc.reads:
            # It copies the field in and keeps its own changes local; that is
            # what it did before as well, and it is what makes it a reader.
            continue
        readers = [n for n in later if n != reader and x in fam.access[n].reads]
        if x in fam.orch_access.reads and any(
            line > fam.call_line[reader] for line in [fam.orch_access.reads[x]]
        ):
            readers.append(method)
        if readers:
            hits.append(f"{x} (read later by {readers})")
    assert not hits, (
        f"{reader} binds a local with the name of a {fam.cls.name} field that a"
        f" later reader takes from the state, so the later reader sees an older"
        f" value: {hits}. Write {fam.var}.<name> instead."
    )


@pytest.mark.parametrize("modname", sorted({mod for mod, _ in PARTS.values()}))
def test_no_locals_call_is_left(modname):
    tree = ast.parse(inspect.getsource(importlib.import_module(modname)))
    calls = [
        n.lineno
        for n in ast.walk(tree)
        if isinstance(n, ast.Call)
        and isinstance(n.func, ast.Name)
        and n.func.id == "locals"
    ]
    assert not calls, f"{modname} calls locals() at lines {calls}"
    helpers = [
        n.name
        for n in ast.walk(tree)
        if isinstance(n, (ast.FunctionDef, ast.AsyncFunctionDef))
        and n.name == "_phase_outputs"
    ]
    assert not helpers, f"{modname} still defines _phase_outputs"
