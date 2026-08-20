"""The camera re-sends frames, and re-sent frames must not be muxed again.

Driven by the same 1200 real frames as the end-to-end test.

WHAT THE CAMERA ACTUALLY DOES (measured 2026-08-20 at the raw tap, three
cameras, 21,490 frames): its timestamps are clean - strictly increasing, spaced
for 15 fps, no duplicates and no backward steps - and one mux queue has exactly
one source, so nothing is interleaving. But frames arrive faster than 15 fps,
and after `_correct_ts` undoes aiortc's bogus 2**32 unwraps, **489 of the
fixture's 1200 timestamps are exact repeats of ones already served**: 40.75%,
which matches the 41.1% and 45.7% measured in what we actually serve.

So `_correct_ts` is correct. It recovers the camera's true timeline, and that
timeline really does return to an earlier point - the camera re-sends a run of
frames it has already sent.

`video_pts_dts` answered those repeats with its monotonic clamp, `prev + 1`.
The muxer accepts that, but it puts up to 41 already-served frames into the
container one tick apart: a burst, twice a second, for a viewer to play back.

Dropping them instead costs nothing and fixes it. Both routes carry the same
media rate over the fixture - 1.104x - because the repeats add no new
presentation time either way. Dropping simply declines to serve a frame whose
presentation time has already gone out.
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.client import _correct_ts, _unwrap_state
from aidot_cameras.camera.protocol import (
    _REORDER_SLACK_TICKS as SLACK,
    is_resent_video_frame,
    video_pts_dts,
)

FIXTURE = os.path.join(os.path.dirname(os.path.abspath(__file__)),
                       "fixtures", "tap_timestamps_a000088.txt")


def _raw():
    with open(FIXTURE) as fh:
        return [int(x) for x in fh if x.strip()]


def _corrected():
    st = _unwrap_state()
    c = [_correct_ts(st, r) for r in _raw()]
    return [v - c[0] for v in c]


def _served(drop):
    """The (pts, dts) actually muxed, with and without dropping re-sends."""
    state = {}
    out = []
    for v in _corrected():
        if drop and is_resent_video_frame(state, v):
            continue
        out.append(video_pts_dts(state, v, SLACK))
    return out


def test_a_fresh_stream_reports_nothing_as_resent():
    state = {}
    assert not any(is_resent_video_frame(state, i * 6000) for i in range(50))


def test_a_timestamp_already_served_is_resent():
    state = {}
    assert is_resent_video_frame(state, 6000) is False
    assert is_resent_video_frame(state, 6000) is True   # exact repeat
    assert is_resent_video_frame(state, 3000) is True   # behind the mark
    assert is_resent_video_frame(state, 12000) is False  # genuinely new


def test_the_fixture_is_40_percent_resent_frames():
    """Guards the premise: without the repeats this test proves nothing."""
    state = {}
    resent = sum(1 for v in _corrected() if is_resent_video_frame(state, v))
    assert resent == 489, resent


def test_dropping_removes_every_one_tick_burst():
    dts = [d for _p, d in _served(drop=True)]
    steps = [dts[i + 1] - dts[i] for i in range(len(dts) - 1)]
    assert not [s for s in steps if s <= 1], "frames still collapsed to a tick"
    assert min(steps) >= 5000, min(steps)


def test_todays_behaviour_really_does_burst():
    """The defect this replaces, so the fix is not measuring itself."""
    dts = [d for _p, d in _served(drop=False)]
    steps = [dts[i + 1] - dts[i] for i in range(len(dts) - 1)]
    assert len([s for s in steps if s == 1]) == 489


def test_dropping_does_not_change_the_media_rate():
    """The repeats carry no new presentation time, so removing them must not
    move the rate - this is the invariant the 1.82x work established."""
    a = _served(drop=False)
    b = _served(drop=True)
    span_a = (a[-1][1] - a[0][1]) / 90000
    span_b = (b[-1][1] - b[0][1]) / 90000
    assert abs(span_a - span_b) < 0.05, (span_a, span_b)


def test_every_muxer_invariant_still_holds():
    out = _served(drop=True)
    dts = [d for _p, d in out]
    assert dts == sorted(dts) and len(set(dts)) == len(dts)
    assert dts[0] >= 0
    assert all(p >= d for p, d in out)
