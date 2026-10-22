"""A terminal webrtcResp ack must be honoured on the SDES path, not just DTLS.

The shared message handler records -50002 ("max concurrent streams") and -50015
into ``terminal_error_fut`` for BOTH transports, but only the DTLS connect ever
read it. So when a battery camera answered "no free session", the SDES keepalive
loop saw a generic failure and retried on the short backoff - hammering the very
camera that had just said it was full, and minting a NEW peerid (= another
camera-side session, released only slowly) on every attempt. That is how a
battery A001513 gets wedged into a wake-then-sleep loop that serves nothing.

These tests lock the two halves of the fix: the codes are classified as
terminal, and the SDES keepalive loop treats AidotCameraBusy as "back off for
the release window" rather than "retry immediately".
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.protocol import (
    _WEBRTC_TERMINAL_ACK_CODES,
    _terminal_webrtc_ack,
)


def test_session_exceed_codes_are_terminal():
    assert -50002 in _WEBRTC_TERMINAL_ACK_CODES
    assert -50015 in _WEBRTC_TERMINAL_ACK_CODES


def test_terminal_ack_parses_session_exceed():
    msg = {"method": "webrtcResp", "ack": {"code": -50002, "desc": "SESSION_EXCEED"}}
    got = _terminal_webrtc_ack(msg)
    assert got is not None
    assert got[0] == -50002


def test_transient_ack_is_not_terminal():
    # -50019 ("not ready") is benign: mains cameras emit it and recover via ICE.
    msg = {"method": "webrtcResp", "ack": {"code": -50019, "desc": "not ready"}}
    assert _terminal_webrtc_ack(msg) is None


def test_sdes_keepalive_loop_backs_off_on_camera_busy(
    monkeypatch, make_camera_device_client
):
    """A camera refusal waits out the release window (_BUSY_BACKOFF_S, measured:
    2 s is refused, 8 s reopens), not the short open-failure backoff and not
    the 300 s the refusal used to cost."""
    from aidot_cameras.camera.client import _BUSY_BACKOFF_S
    from aidot_cameras.exceptions import AidotCameraBusy
    from tests.keepalive_driver import SdesKeepaliveRun

    cam = make_camera_device_client("A001513")
    run = SdesKeepaliveRun(
        monkeypatch, cam, [AidotCameraBusy(-50002, "max streams")] * 3
    ).run()

    assert run.sleeps == [_BUSY_BACKOFF_S] * 3
    assert 0 < _BUSY_BACKOFF_S < 300.0
    assert len(run.opens) == 4 and run.dormant == []
