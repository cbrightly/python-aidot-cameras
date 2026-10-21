"""A ``return`` in a phase or region comes back out of the method it was split from.

The opens and the serve loops hand their work to phase and region coroutines
(see ``tests/source_parts.py``). A phase's ``return`` is the method's
``return``: the phase hands back ``_PhaseReturn(value)`` and the method returns
``value``. A ``return`` left bare in a phase is read by the method as its
outputs dict and dropped.

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
_WRAPPERS = _SIGNALS | {"_phase_outputs"}


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
        if _wrapper_of(r) not in _WRAPPERS
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
    src = textwrap.dedent(inspect.getsource(getattr(CameraMixin, method)))
    missing = []
    for name, emitted in signals.items():
        call = f"_o = await {name}(self, locals())"
        assert src.count(call) == 1, f"{method} should call {name} once"
        after = src.split(call, 1)[1].split("_o = await ", 1)[0]
        for signal in sorted(emitted):
            if f"isinstance(_o, {signal})" not in after and not (
                signal in ("_LoopContinue", "_LoopBreak")
                and "isinstance(_o, (_LoopContinue, _LoopBreak))" in after
            ):
                missing.append(f"{name} -> {signal}")
    assert not missing, f"{method} ignores what these parts hand back: {missing}"
