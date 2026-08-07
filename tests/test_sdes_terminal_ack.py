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
import inspect
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.protocol import (
    _WEBRTC_TERMINAL_ACK_CODES,
    _terminal_webrtc_ack,
)
import aidot_cameras.camera.client as cc
import aidot_cameras.camera.webrtc_open as wo


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


def _fn_source(mod, qualname):
    """Source of a method by name, without relying on file offsets."""
    for cls in vars(mod).values():
        if isinstance(cls, type):
            fn = cls.__dict__.get(qualname)
            if fn is not None:
                return inspect.getsource(fn)
    raise AssertionError(f"{qualname} not found in {mod.__name__}")


def test_sdes_open_path_checks_the_terminal_future():
    # The SDES branch must consult terminal_error_fut; before the fix it never did.
    src = _fn_source(wo, "_async_open_webrtc_stream_impl")
    sdes_branch = src[src.rindex("if use_sdes:"):]
    assert "terminal_error_fut" in sdes_branch, (
        "SDES branch must read terminal_error_fut (GAP D was DTLS-only)")
    assert "AidotCameraBusy" in sdes_branch


def test_sdes_keepalive_loop_backs_off_on_camera_busy():
    loop_src = _fn_source(cc, "_sdes_keepalive_loop_inner")
    assert "except AidotCameraBusy" in loop_src, (
        "the SDES keepalive loop must honour a camera refusal instead of "
        "retrying on the short backoff")
    busy_at = loop_src.index("except AidotCameraBusy")
    assert "_BUSY_BACKOFF_S" in loop_src[busy_at:busy_at + 1600], (
        "a camera refusal must wait out the release window rather than "
        "retrying on the short backoff")
    assert "_MAX_DELAY" not in loop_src[busy_at:busy_at + 1600], (
        "the refusal wait used to be _MAX_DELAY (300s) on the belief that the "
        "camera releases slowly. Measured 2026-08-07: 2s is refused, 8s "
        "reopens cleanly. A camera that clears in seconds must not cost "
        "minutes - see _BUSY_BACKOFF_S")
