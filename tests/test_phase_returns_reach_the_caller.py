"""A ``return`` in a phase or region comes back out of the method it was split from.

The opens and the serve loops hand their work to phase and region coroutines
(see ``tests/source_parts.py``) that share a state object and hand back only a
control signal. A phase's ``return`` is the method's ``return``: the phase
hands back ``_PhaseReturn(value)`` and the method returns ``value``. A phase
that returns anything else (``return session``) has its value dropped by the
method, which only looks for the signals.

1.0.0rc58's first cut did exactly that with the DTLS open's last statement,
``return WebRTCSession(...)``, which sat at the phase's own top level where the
split did not look for returns. Every successful DTLS open handed ``None`` to
its caller, so the DTLS serve loop failed on ``session._pc`` and the snapshot,
talk and live-view paths lost the session they had just opened. The camera
still streamed into the session's recorder, so a live run judged on the
recording passed.
"""

import ast
import importlib
import inspect
import textwrap

import pytest

from tests.source_parts import PARTS

_SIGNALS = {"_PhaseReturn", "_LoopContinue", "_LoopBreak"}


def _parts():
    for method, (modname, prefix) in PARTS.items():
        mod = importlib.import_module(modname)
        for name in sorted(dir(mod)):
            fn = getattr(mod, name)
            if name.startswith(prefix) and inspect.isfunction(fn):
                yield method, name, ast.parse(textwrap.dedent(inspect.getsource(fn)))


def _own_returns(fn: ast.AST):
    """The ``return`` statements of ``fn`` itself, not of functions nested in it."""
    stack = list(fn.body)
    while stack:
        node = stack.pop()
        if isinstance(node, (ast.FunctionDef, ast.AsyncFunctionDef, ast.Lambda)):
            continue
        if isinstance(node, ast.Return):
            yield node
        stack.extend(ast.iter_child_nodes(node))


def _wrapper_of(ret: ast.Return):
    v = ret.value
    if v is None or (isinstance(v, ast.Constant) and v.value is None):
        return "None"
    if isinstance(v, ast.Call) and isinstance(v.func, ast.Name):
        return v.func.id
    return None


_ALL = list(_parts())


def test_the_split_methods_still_have_parts():
    assert {m for m, _, _ in _ALL} == set(PARTS)


@pytest.mark.parametrize("method,name,tree", _ALL, ids=[f"{m}:{n}" for m, n, _ in _ALL])
def test_every_return_in_a_part_is_wrapped(method, name, tree):
    fn = tree.body[0]
    bare = [
        f"line {r.lineno}: return {ast.unparse(r.value) if r.value else ''}"
        for r in _own_returns(fn)
        if _wrapper_of(r) not in _SIGNALS | {"None"}
    ]
    assert not bare, (
        f"{name} returns without a wrapper, so {method} drops the value: {bare}"
    )


@pytest.mark.parametrize("method", sorted(PARTS))
def test_every_part_that_can_return_is_checked_where_it_is_called(method):
    from aidot_cameras.camera.client import CameraMixin

    signals = {
        name: {_wrapper_of(r) for r in _own_returns(tree.body[0])} & _SIGNALS
        for m, name, tree in _ALL
        if m == method
    }
    orch = ast.parse(textwrap.dedent(inspect.getsource(getattr(CameraMixin, method))))
    # Every statement list in the orchestrator, so "what follows the call"
    # means the statements after it in its own block.
    blocks = [
        stmts
        for node in ast.walk(orch)
        for field in ("body", "orelse")
        if isinstance(stmts := getattr(node, field, None), list)
    ]
    missing = []
    for name, emitted in signals.items():
        sites = [
            (stmts, i)
            for stmts in blocks
            for i, s in enumerate(stmts)
            if isinstance(s, (ast.Assign, ast.Expr))
            and isinstance(s.value, ast.Await)
            and isinstance(s.value.value, ast.Call)
            and getattr(s.value.value.func, "id", None) == name
        ]
        assert len(sites) == 1, f"{method} should call {name} once"
        stmts, i = sites[0]
        call = stmts[i]
        args = call.value.value.args
        assert len(args) == 2 and ast.unparse(args[0]) == "self", ast.unparse(call)
        if not emitted:
            continue
        assert isinstance(call, ast.Assign) and ast.unparse(call.targets[0]) == "_o", (
            f"{method} drops what {name} hands back"
        )
        after = []
        for s in stmts[i + 1 :]:
            if any(isinstance(a, ast.Await) for a in ast.walk(s)):
                break
            after.append(s)
        checked = {
            ast.unparse(c.args[1])
            for s in after
            for c in ast.walk(s)
            if isinstance(c, ast.Call)
            and getattr(c.func, "id", None) == "isinstance"
            and ast.unparse(c.args[0]) == "_o"
        }
        missing += [f"{name} -> {sig}" for sig in sorted(emitted - checked)]
    assert not missing, f"{method} ignores what these parts hand back: {missing}"
