"""Unit tests for _stall_nudge_due - the mid-session stall-nudge policy.

The A001064 episodically stops transmitting mid-session with no teardown
signal, yet answers a fresh handshake immediately afterwards.  The bridge
therefore re-sends the AVIO LIVING that starts media before letting the
serve's input timeout kill the session.  This policy decides when: bounded
sends, spaced out, and never before a real stall threshold - the input
timeout and the keepalive reopen remain the fallback.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.sdes_open import _stall_nudge_due as due  # noqa: E402


def test_no_nudge_while_media_flows():
    assert not due(silence_s=0.4, nudges_sent=0, since_last_nudge_s=99.0)


def test_no_nudge_at_or_below_threshold():
    assert not due(silence_s=2.5, nudges_sent=0, since_last_nudge_s=99.0)


def test_first_nudge_past_threshold():
    assert due(silence_s=2.6, nudges_sent=0, since_last_nudge_s=99.0)


def test_nudges_are_spaced():
    # 1 s after the previous nudge: too soon; 2 s: due again.
    assert not due(silence_s=5.0, nudges_sent=1, since_last_nudge_s=1.0)
    assert due(silence_s=5.0, nudges_sent=1, since_last_nudge_s=2.0)


def test_nudges_are_bounded():
    # After max_nudges the camera is presumed truly asleep/out of range and
    # the fallback path (input timeout -> reopen) takes over unmolested.
    assert not due(silence_s=60.0, nudges_sent=3, since_last_nudge_s=99.0)


def test_threshold_is_parameterised():
    assert due(silence_s=1.1, nudges_sent=0, since_last_nudge_s=99.0,
               stall_after_s=1.0)
    assert not due(silence_s=1.1, nudges_sent=0, since_last_nudge_s=99.0,
                   stall_after_s=2.5)


def test_max_is_parameterised():
    assert due(silence_s=60.0, nudges_sent=3, since_last_nudge_s=99.0,
               max_nudges=5)
