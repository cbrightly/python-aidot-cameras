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

These tests read the source because the bridge is one long closure that cannot
be called in isolation.
"""
import inspect
import re

from aidot_cameras.camera import sdes_open

_SRC = inspect.getsource(sdes_open)


def _assigned_attrs() -> set:
    """Every `_bridge_fn.X = ...` attribute assigned anywhere in the module."""
    return set(re.findall(r"_bridge_fn\.([A-Za-z_][A-Za-z0-9_]*)\s*=[^=]", _SRC))


def test_every_hasattr_sentinel_is_a_name_something_assigns():
    guarded = set(re.findall(r"hasattr\(_bridge_fn,\s*'([^']+)'\)", _SRC))
    assigned = _assigned_attrs()
    orphans = sorted(guarded - assigned)
    assert not orphans, (
        "these hasattr guards name attributes nothing ever assigns, so they are "
        f"true on every packet and reset their state each time: {orphans}"
    )


def test_the_tutk_sequence_counters_survive_between_packets():
    """The specific regression: a constant RTP sequence number.

    Pin the guard to a counter the block assigns, so a reset-per-packet cannot
    come back by renaming one side of the pair.
    """
    block = re.search(
        r"if not hasattr\(_bridge_fn,\s*'([^']+)'\):\s*\n"
        r"\s*_bridge_fn\._tutk_seq_a = 0\s*\n"
        r"\s*_bridge_fn\._tutk_seq_v = 0",
        _SRC,
    )
    assert block, "the TUTK sequence init block moved - re-point this test"
    sentinel = block.group(1)
    assert sentinel in ("_tutk_seq_a", "_tutk_seq_v"), (
        f"the guard tests {sentinel!r}, which is not one of the counters it "
        "initialises - the counters will reset on every packet and every "
        "synthesized packet will carry the same sequence number"
    )


def test_the_counters_increment_rather_than_being_reassigned():
    """Both counters must advance from their previous value, not restart."""
    for name in ("_tutk_seq_a", "_tutk_seq_v"):
        assert re.search(
            rf"_bridge_fn\.{name} = \(_bridge_fn\.{name} \+ 1\) & 0xFFFF", _SRC
        ), f"{name} no longer advances from its own previous value"
