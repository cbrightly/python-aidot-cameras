"""A peer-reflexive address the bridge learns is nominated on the same pass.

The bridge nominates on a 2.5 s tick, and the tick's first run lands on
whatever wakes the bridge first.  On a cold A001064 the camera advertises only
a host address this side cannot reach, and reaches us from its public one.
When the camera's probes were already queued as the bridge started, the first
pass learned that address before the tick ran and nominated it at once (first
media +2.9 s).  Once the webrtcReq echo wait was removed the probes came 30 ms
after the TURN CreatePermission reply: the tick ran on the reply, nominated
only the unreachable host, and the address that works waited for the next
tick - camera Binding Success, LIVING and first media all 2.5 s later (+5.4 s).

Learning a new address therefore re-arms the tick, so it runs at the end of
the pass that learned it.  The bridge is one long thread body, so this is
checked on its source.
"""

import ast
import inspect
import textwrap

from aidot_cameras.camera import sdes_open


def _bridge_fn_node() -> ast.FunctionDef:
    src = textwrap.dedent(inspect.getsource(sdes_open.make_sdes_bridge))
    tree = ast.parse(src)
    for node in ast.walk(tree):
        if isinstance(node, ast.FunctionDef) and node.name == "_bridge_fn":
            return node
    raise AssertionError("_bridge_fn not found in make_sdes_bridge")


def _assigns(node, name):
    return [
        n
        for n in ast.walk(node)
        if isinstance(n, ast.Assign)
        and any(isinstance(t, ast.Name) and t.id == name for t in n.targets)
    ]


def _learn_branch(fn: ast.FunctionDef) -> ast.If:
    """The ``if _br_prflx_now is not _br_prflx_was:`` block."""
    for n in ast.walk(fn):
        if (
            isinstance(n, ast.If)
            and isinstance(n.test, ast.Compare)
            and isinstance(n.test.left, ast.Name)
            and n.test.left.id == "_br_prflx_now"
            and isinstance(n.test.ops[0], ast.IsNot)
        ):
            return n
    raise AssertionError("the peer-reflexive learn branch is gone")


def test_learning_a_new_address_rearms_the_nomination_tick():
    branch = _learn_branch(_bridge_fn_node())
    resets = [
        a
        for a in _assigns(branch, "_br_last_uc")
        if isinstance(a.value, ast.Constant) and a.value.value == 0.0
    ]
    assert resets, (
        "a newly learned peer-reflexive address must reset _br_last_uc so the"
        " nomination tick runs on this pass, not up to 2.5 s later"
    )


def test_the_rearm_is_a_bridge_local_the_tick_reads():
    """The reset only works if it binds the same name the tick compares,
    in the same scope: not a nested function's local, not an attribute."""
    fn = _bridge_fn_node()
    branch = _learn_branch(fn)
    nested = [
        n
        for n in ast.walk(fn)
        if isinstance(n, (ast.FunctionDef, ast.AsyncFunctionDef)) and n is not fn
    ]
    for inner in nested:
        assert branch not in list(ast.walk(inner)), (
            "the learn branch moved into a nested function; its _br_last_uc"
            " reset would bind a different local"
        )
    tick_reads = [
        n
        for n in ast.walk(fn)
        if isinstance(n, ast.Compare)
        and any(
            isinstance(s, ast.Name) and s.id == "_br_last_uc"
            for s in ast.walk(n)
        )
    ]
    assert tick_reads, "the nomination tick no longer gates on _br_last_uc"


def test_the_tick_runs_after_the_packets_of_the_same_pass():
    """The tick must follow the per-packet loop inside the same iteration,
    or a reset made while handling a probe would wait a select timeout."""
    fn = _bridge_fn_node()
    for loop in ast.walk(fn):
        if not isinstance(loop, ast.While):
            continue
        body = loop.body
        pkt_idx = next(
            (
                i
                for i, s in enumerate(body)
                if isinstance(s, ast.For)
                and isinstance(s.iter, ast.Name)
                and s.iter.id == "_rl"
            ),
            None,
        )
        if pkt_idx is None:
            continue
        later = body[pkt_idx + 1 :]
        assert any(_assigns(s, "_br_last_uc") for s in later), (
            "the nomination tick must come after the packet loop in the"
            " same pass"
        )
        return
    raise AssertionError("bridge main loop not found")
