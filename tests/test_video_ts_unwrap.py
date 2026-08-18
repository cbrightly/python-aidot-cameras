"""aiortc unwraps a reordered frame as a 2**32 counter wrap.

Probed on the live tap 2026-08-18 by logging every timestamp it received.
1200 frames from each of two A000088 units:

    normal steps  6030, 5940, 6120, 5850   (sum -> 78.5 s of media, 15.3 fps)
    ABNORMAL      22 steps of ~42949xxxxx

2**32 is 4,294,967,296, and every abnormal step is 2**32 minus an ordinary
frame interval: 4294961266 is 2**32 - 6030, 4294829236 is 2**32 - 138060. They
are small BACKWARD steps - reordered or re-sent frames - read as the 32-bit
timestamp wrapping and "unwrapped" by adding 2**32.

The serve mux writes `pkt.pts = pkt.dts = ts - v0[0]`, so each artifact puts a
jump of about 47,721 seconds into a container whose PTS field is 33 bits.

The camera itself is fine: those same 1200 frames carry an honest 15.3 fps at
6030 ticks per frame on a 90 kHz clock, matching the wire exactly. Only 1.8% of
steps are corrupt, which is why every median, mode and "most common steps"
view of this data looked perfectly healthy for as long as I kept using them.
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.client import (
    _TS_MODULO,
    _correct_ts,
    _unwrap_state,
)


def _feed(raws):
    st = _unwrap_state()
    return [_correct_ts(st, r) for r in raws]


def test_an_ordinary_sequence_is_left_alone():
    assert _feed([0, 6030, 12060, 18090]) == [0, 6030, 12060, 18090]


def test_the_measured_artifact_is_undone():
    """4294961266 is 2**32 - 6030: the frame really went BACK 6030."""
    out = _feed([1_000_000, 1_000_000 + 4294961266])
    assert out[1] == 1_000_000 - 6030


def test_the_shift_is_undone_for_EVERY_later_frame():
    """The artifact leaves the whole rest of the session 2**32 too high.

    Correcting only the offending step would look right at that frame and
    leave everything after it wrong, which is why this is a running offset.
    """
    raw = [0, 6030]
    raw.append(raw[-1] - 6030 + _TS_MODULO)      # the artifact
    for _ in range(5):
        raw.append(raw[-1] + 6030)
    out = _feed(raw)
    assert out == [0, 6030, 0, 6030, 12060, 18090, 24120, 30150]


def test_a_backwards_step_that_was_never_unwrapped_is_left_alone():
    assert _feed([10_000, 4_000]) == [10_000, 4_000]


def test_a_long_forward_gap_is_not_mistaken_for_an_artifact():
    """A real hole in delivery advances by far less than 2**31."""
    out = _feed([1_000_000, 1_000_000 + 90_000 * 60])
    assert out[1] == 1_000_000 + 90_000 * 60


def test_the_measured_sequence_recovers_the_real_frame_rate():
    """1200 frames carrying 22 artifacts must come out at about 15.3 fps.

    Rebuilds the probed shape exactly: ordinary 6030-tick frames, with an
    artifact every ~55 frames that adds 2**32 to everything after it.
    """
    raw, cur = [], 0
    for i in range(1200):
        cur = (cur - 6030 + _TS_MODULO) if (i and i % 55 == 0) else (cur + 6030)
        raw.append(cur)
    assert raw[-1] - raw[0] > 9e10, "the raw span really is ~94 billion"
    out = _feed(raw)
    steps = [b - a for a, b in zip(out, out[1:])]
    assert not any(abs(s) > 2 ** 31 for s in steps), "no artifact survives"
    fps = len(out) / ((max(out) - min(out)) / 90000)
    assert 14.5 < fps < 16.0, f"expected about 15.3 fps, got {fps:.1f}"
