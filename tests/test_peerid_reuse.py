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


def test_impl_accepts_reuse_peer_id_defaulting_to_none():
    p = _impl_signature().parameters.get("reuse_peer_id")
    assert p is not None, "impl must accept reuse_peer_id"
    assert p.default is None, "default None preserves mint-per-call for other callers"


def test_impl_uses_reuse_peer_id_verbatim_when_given():
    """A supplied peerid is used as-is, not regenerated: the open's peer id is
    ``reuse_peer_id or <fresh one>``."""
    import ast

    from tests.ast_guards import dtls_open_family, name_of

    picks = [
        n.value
        for fn in dtls_open_family()
        for n in ast.walk(fn)
        if isinstance(n, ast.Assign)
        and isinstance(n.value, ast.BoolOp)
        and isinstance(n.value.op, ast.Or)
        and name_of(n.value.values[0]) == "reuse_peer_id"
    ]
    assert picks, "when a peerid is supplied it must be used as-is"
    assert any(name_of(p.values[-1]) == "generate_webrtc_peer_id" for p in picks)


def test_sdes_keepalive_loop_holds_and_rotates_one_peerid(
    monkeypatch, make_camera_device_client
):
    """One peerid across dead attempts, capped at _PEERID_MAX_REUSE, and a
    fresh one after a session that delivered media."""
    from tests.keepalive_driver import FakeSession, SdesKeepaliveRun, no_media

    monkeypatch.setenv("AIDOT_FUTILE_KEEPALIVE_LIMIT", "0")
    cam = make_camera_device_client("A001064")
    cap = cc._PEERID_MAX_REUSE
    run = SdesKeepaliveRun(
        monkeypatch,
        cam,
        no_media(cap + 1) + [FakeSession(media=True, life=20)] + no_media(1),
    ).run()

    ids = run.peer_ids
    assert all(i for i in ids)
    assert len(set(ids[:cap])) == 1, "dead attempts re-offer on one peerid"
    assert ids[cap] != ids[0], "rotated after the reuse cap"
    # opens: cap+1 dead, the media session, one more dead, the stop
    assert ids[cap + 1] == ids[cap], "the session itself is still on that peerid"
    assert ids[cap + 2] != ids[cap + 1], "a delivered session rotates the peerid"


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
