"""AVIO control commands carry the session dSeq, as the app's do.

Read out of the app (`KVSWebRTCChannel`, decompiled) 2026-08-23. Its control
header is 28 bytes little-endian and ours agrees on every field but one:

    offset  app                              ours (before this change)
    0       live-play dSeq                   random.randint(0, 0x7FFFFFFF)
    4       cmd                              cmd
    8       timestamp (long, ms)             same
    16      payload length                   len(payload)
    20      0                                0
    24      pad                              pad

`sendCtrl(cmd, bytes)` calls `generateLivePlayDSeq()` first: a per-client
counter that starts at 100 and increments on EVERY control command. The app
routes 16 of its 17 commands that way and only the heartbeat (0x1424 / 5156)
without one. We already keep that counter -- `_next_dseq`, commented "app
parity (Q0(): starts at 100, increments)" -- and used it for `livePlayReq`
only, filling the control frames with a random number instead.

**This is parity, not a fix.** It is explicitly NOT the reason SETSTREAMCTRL is
ignored: `button.*_ptz_*` is AVIO 0x1001 sent through the identical frame
builder with the same random dSeq, and it is class A on physical evidence --
"the camera pans. Mean frame difference across a 5 s pan: 25.2 at speed 4,
38.6 at speed 200, against a no-command control of 2.6." A camera that pans on
a random dSeq is not validating it. Closing the gap removes the last known
framing difference from the app; it is not expected to change any behaviour,
and the PTZ pan must still work afterwards.
"""
import pathlib
import re

_SRC = pathlib.Path(__file__).resolve().parents[1] / "aidot_cameras" / "camera"


def _client_cls():
    import aidot_cameras.camera.client as cc
    return next(v for v in vars(cc).values()
                if isinstance(v, type) and "_next_dseq" in v.__dict__)


def test_the_counter_starts_at_100_and_increments():
    cam = _client_cls().__new__(_client_cls())
    cam._live_dseq = 100
    assert [cam._next_dseq() for _ in range(4)] == [100, 101, 102, 103]


def test_control_commands_and_live_play_share_one_counter():
    # The app keys its counter on the client id, not on the command, so a
    # control command consumes a number from the same run as livePlayReq.
    cam = _client_cls().__new__(_client_cls())
    cam._live_dseq = 100
    first = cam._next_dseq()
    second = cam._next_dseq()
    assert second == first + 1


def test_the_avio_control_frame_no_longer_uses_a_random_dseq():
    """Binds the test to the real send site, which lives in a closure.

    Without this the unit tests above would still pass with the production
    frame builder untouched -- the same reason the media-counter gate has a
    source guard.
    """
    src = (_SRC / "sdes_open.py").read_text()
    m = re.search(r"def _persistent_sdes_cmd\(.*?\n(?P<body>(?:.*\n){0,12})", src)
    assert m, "could not find the persistent AVIO command sender"
    body = m.group("body")
    assert "randint" not in body, (
        "the AVIO control frame still fills the dSeq slot with a random number; "
        "the app fills it with the live-play counter")
    assert "_next_dseq" in body, (
        "the AVIO control frame must take its dSeq from the session counter "
        "(_next_dseq), the way livePlayReq already does")


def test_the_header_layout_is_unchanged():
    # Only the VALUE in the dSeq slot changes. The layout is verified
    # byte-for-byte against the app: 28 bytes, little-endian, dSeq/cmd/ts/len/0.
    src = (_SRC / "sdes_open.py").read_text()
    assert "'<IIqII4x'" in src, "the 28-byte little-endian AVIO header must not change"
