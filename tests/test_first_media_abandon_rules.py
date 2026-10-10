"""Two first-media stalls that the existing early-abandons cannot see.

Measured 2026-10-08 on a battery camera: (1) the camera answered our
connectivity checks (two Binding Successes), LIVING was sent and never acked,
and no media came for the whole 75 s; (2) the camera answered the SDP and then
never probed at all, so nothing was ever nominated and the unreachable-nominee
grace never started. Both ran the full budget; the next attempt served in 5 s.
"""

import pytest

from aidot_cameras.camera.sdes_open import (
    _no_probe_abandon_due,
    _trigger_unacked_abandon_due,
)


@pytest.mark.parametrize(
    ("since", "nominated", "probes", "bs", "expect"),
    [
        # No answer yet: a waking battery camera, never clipped.
        (None, False, 0, 0, False),
        (19.9, False, 0, 0, False),  # inside the grace
        (20.0, False, 0, 0, True),  # the case this exists for
        (20.0, True, 0, 0, False),  # nominated: the nominee rule owns it
        (20.0, False, 1, 0, False),  # a probe arrived: ICE is progressing
        # A check was answered: something was nominated on a path no record
        # sees (the trickle-fed tick), and the trigger rule owns it.
        (20.0, False, 0, 2, False),
    ],
)
def test_no_probe_rule(since, nominated, probes, bs, expect):
    assert (
        _no_probe_abandon_due(
            answered_since_s=since,
            grace_s=20.0,
            nominated=nominated,
            probes=probes,
            binding_success=bs,
        )
        is expect
    )


def test_no_probe_rule_disabled_by_a_zero_grace():
    assert (
        _no_probe_abandon_due(
            answered_since_s=100.0,
            grace_s=0,
            nominated=False,
            probes=0,
            binding_success=0,
        )
        is False
    )


@pytest.mark.parametrize(
    ("since", "acked", "pkts", "expect"),
    [
        (None, False, 0, False),  # LIVING not sent yet
        (19.9, False, 0, False),  # inside the grace
        (20.0, False, 0, True),  # the case this exists for
        (20.0, True, 0, False),  # acked: the camera is acting on it
        (20.0, False, 3, False),  # media arrived (the wait ends on its own)
    ],
)
def test_trigger_unacked_rule(since, acked, pkts, expect):
    assert (
        _trigger_unacked_abandon_due(
            trigger_sent_since_s=since,
            grace_s=20.0,
            trigger_acked=acked,
            media_pkts=pkts,
        )
        is expect
    )


def test_trigger_rule_disabled_by_a_zero_grace():
    assert (
        _trigger_unacked_abandon_due(
            trigger_sent_since_s=100.0, grace_s=0, trigger_acked=False, media_pkts=0
        )
        is False
    )


def test_the_bridge_stamps_when_living_is_first_sent():
    """The loop times the trigger rule from this stamp; it must exist and be monotonic."""
    import inspect

    from aidot_cameras.camera import sdes_open

    # Whitespace-free, so the formatter's line wrapping cannot break it.
    src = "".join(inspect.getsource(sdes_open).split())
    assert "_bridge_fn._br_trigger_sent_ts=None" in src
    assert "_bridge_fn._br_trigger_sent_ts=(_time_br.monotonic())" in src


def test_tutk_framed_media_is_counted_for_the_trigger_rule():
    """TUTK SFrame media leaves the branch before _br_media_pkts is counted.

    No camera in the fleet sends it today, but if one did, the trigger rule
    would read zero media while media flowed, so the branch keeps its own count
    and the loop adds it in.
    """
    import inspect

    from aidot_cameras.camera import sdes_open

    src = inspect.getsource(sdes_open)
    assert "_bridge_fn._br_tutk_media = 0" in src
    assert 'getattr(_bridge_fn, "_br_tutk_media", 0) + 1' in src
    assert 'int(getattr(_bridge_fn, "_br_tutk_media", 0))' in src
