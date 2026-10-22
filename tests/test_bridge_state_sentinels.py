"""Lazy-init guards on the bridge must name a variable something actually sets.

The bridge keeps its per-session state as attributes on its own function object
and initialises them with `if not hasattr(_bridge_fn, 'X')`. That idiom fails
silently when `X` is not the attribute the block goes on to assign: the guard is
true on every packet, so the state resets every packet, and nothing raises.

It happened. The TUTK-to-RTP synthesis guarded on `_tutk_seq` while assigning
`_tutk_seq_a` and `_tutk_seq_v`, so both sequence counters reset on every packet
and every synthesized RTP packet carried sequence number 1. ffmpeg reads a
constant sequence as a stream of discontinuities and logs `RTP: missed N
packets` - loss that never happened, on a hop where the kernel counters showed
zero drops. Measured on the box: 0 drops on every socket in the path while
ffmpeg reported 21 new missed-packet lines in one window.

The bridge is a thread body that cannot be driven in a unit test, so these
are structural checks on its AST.
"""

import ast

from tests.ast_guards import attr_writes, module_tree, name_of, names_in, sdes_bridge


def _hasattr_guards():
    """(If node, attribute) for every `if not hasattr(_bridge_fn, "x"):`."""
    out = []
    for node in ast.walk(sdes_bridge()):
        if not isinstance(node, ast.If):
            continue
        test = node.test
        if isinstance(test, ast.UnaryOp) and isinstance(test.op, ast.Not):
            test = test.operand
        if (
            isinstance(test, ast.Call)
            and name_of(test) == "hasattr"
            and len(test.args) == 2
            and isinstance(test.args[0], ast.Name)
            and test.args[0].id == "_bridge_fn"
            and isinstance(test.args[1], ast.Constant)
        ):
            out.append((node, test.args[1].value))
    return out


def test_every_hasattr_sentinel_is_a_name_something_assigns():
    guards = _hasattr_guards()
    assert guards, "no hasattr guards found - has the bridge changed shape?"
    assigned = attr_writes(module_tree("aidot_cameras.camera.sdes_open"), "_bridge_fn")
    orphans = sorted({attr for _n, attr in guards} - assigned)
    assert not orphans, (
        "these hasattr guards name attributes nothing ever assigns, so they are "
        f"true on every packet and reset their state each time: {orphans}"
    )


def test_the_tutk_sequence_counters_survive_between_packets():
    """The specific regression: a constant RTP sequence number. The guard
    that initialises the two counters must test one of them."""
    blocks = [
        (node, attr)
        for node, attr in _hasattr_guards()
        if {"_tutk_seq_a", "_tutk_seq_v"} <= attr_writes(node, "_bridge_fn")
    ]
    assert blocks, "the TUTK sequence init block moved - re-point this test"
    for _node, attr in blocks:
        assert attr in ("_tutk_seq_a", "_tutk_seq_v"), (
            f"the guard tests {attr!r}, which is not one of the counters it "
            "initialises - the counters will reset on every packet"
        )


def test_the_counters_increment_rather_than_being_reassigned():
    """Both counters advance from their previous value, not restart."""
    for name in ("_tutk_seq_a", "_tutk_seq_v"):
        advances = [
            n
            for n in ast.walk(sdes_bridge())
            if isinstance(n, ast.Assign)
            and any(isinstance(t, ast.Attribute) and t.attr == name for t in n.targets)
            and isinstance(n.value, ast.BinOp)
            and name in names_in(n.value)
        ]
        assert advances, f"{name} no longer advances from its own previous value"
