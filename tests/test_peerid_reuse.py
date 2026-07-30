"""The SDES keepalive loop reuses one peerid across retries instead of minting
a fresh one every attempt.

A fresh peerid registers a NEW camera-side session, and the camera releases old
ones only slowly (~3-4 min measured), so minting one per retry on a failing loop
stacks up sessions faster than they drain - which is how a battery A001513 gets
wedged into a wake-then-sleep loop that serves nothing. The official app reuses a
peerid and resends within one session. This locks:
  - the impl accepts reuse_peer_id and uses it verbatim when given;
  - it still mints a fresh one when not given (unchanged for every other caller);
  - the SDES keepalive loop holds one across retries and rotates it after a
    media-delivering session or a reuse cap.
"""
import inspect
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot_cameras.camera.client as cc


def _impl_signature():
    for cls in vars(cc).values():
        if isinstance(cls, type) and "_async_open_webrtc_stream_impl" in cls.__dict__:
            return inspect.signature(cls.__dict__["_async_open_webrtc_stream_impl"])
    raise AssertionError("impl not found")


def _fn_source(qualname):
    for cls in vars(cc).values():
        if isinstance(cls, type) and qualname in cls.__dict__:
            return inspect.getsource(cls.__dict__[qualname])
    raise AssertionError(f"{qualname} not found")


def test_impl_accepts_reuse_peer_id_defaulting_to_none():
    p = _impl_signature().parameters.get("reuse_peer_id")
    assert p is not None, "impl must accept reuse_peer_id"
    assert p.default is None, "default None preserves mint-per-call for other callers"


def test_impl_uses_reuse_peer_id_verbatim_when_given():
    src = _fn_source("_async_open_webrtc_stream_impl")
    # peer_id = reuse_peer_id or generate_webrtc_peer_id(...)
    assert "reuse_peer_id or" in src, (
        "when a peerid is supplied it must be used as-is, not regenerated")


def test_sdes_keepalive_loop_holds_and_rotates_one_peerid():
    src = _fn_source("_sdes_keepalive_loop_inner")
    assert "reuse_peer_id=" in src, "the SDES loop must pass a reused peerid"
    assert "_loop_peer_id" in src
    # rotate after a media-delivering session and after a reuse cap
    assert "_PEERID_MAX_REUSE" in src, "must cap consecutive reuses"
    assert src.count("generate_webrtc_peer_id") >= 2, (
        "must be able to rotate to a fresh peerid (initial + rotation)")


def test_generate_peer_id_is_unique_per_call():
    gen = None
    for cls in vars(cc).values():
        if isinstance(cls, type) and "generate_webrtc_peer_id" in cls.__dict__:
            gen = cls.generate_webrtc_peer_id
            break
    assert gen is not None
    a = gen(live_type=2, stream_id=0, sdes=True)
    b = gen(live_type=2, stream_id=0, sdes=True)
    assert a != b and a.endswith("_2_0_1") and b.endswith("_2_0_1")
