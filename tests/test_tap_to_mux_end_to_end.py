"""The whole timestamp path, driven by 1200 real frames off the live tap.

`tests/fixtures/tap_timestamps_a000088.txt` is the exact sequence an A000088
handed the serve tap on 2026-08-18, captured by logging every timestamp it
received. It contains the 22 bogus 2**32 unwraps aiortc applies to a reordered
frame, and the backward excursions those unwraps were hiding.

Unit tests of each half passed while the pair still produced a stream no
viewer could play, twice, on real hardware. This drives the halves together
over real input and asserts the invariants an mpegts muxer actually enforces -
which is what those deploys were failing, and what no synthetic test had
covered.
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.client import _correct_ts, _unwrap_state
from aidot_cameras.camera.protocol import (
    _REORDER_SLACK_TICKS as SLACK,
    video_pts_dts,
)

FIXTURE = os.path.join(os.path.dirname(os.path.abspath(__file__)),
                       "fixtures", "tap_timestamps_a000088.txt")


def _raw():
    with open(FIXTURE) as fh:
        return [int(x) for x in fh if x.strip()]


def _pipeline(raw, slack=SLACK):
    st = _unwrap_state()
    corrected = [_correct_ts(st, r) for r in raw]
    v0 = corrected[0]                      # the mux anchors on its first frame
    tstate = {}
    return [video_pts_dts(tstate, c - v0, slack) for c in corrected]


def test_the_fixture_really_contains_the_artifacts():
    """Guards the fixture: without the artifacts this proves nothing."""
    raw = _raw()
    steps = [b - a for a, b in zip(raw, raw[1:])]
    assert sum(1 for s in steps if s > 2 ** 31) == 22
    assert raw[-1] - raw[0] > 9e10, "raw span is ~94 billion, i.e. 22 x 2**32"


def test_dts_is_monotonic_over_the_real_sequence():
    """The failure two deploys did not catch.

    Both halves' own unit tests passed while this did not hold, because each
    was checked on synthetic input that never made the clamp fight the slack.
    """
    dts = [d for _, d in _pipeline(_raw())]
    assert dts == sorted(dts)
    assert len(set(dts)) == len(dts), "and strictly increasing"


def test_dts_never_exceeds_its_own_pts():
    assert all(d <= p for p, d in _pipeline(_raw()))


def test_nothing_is_negative():
    out = _pipeline(_raw())
    assert all(p >= 0 for p, _ in out)
    assert all(d >= 0 for _, d in out)


def test_no_2_32_artifact_survives_into_the_container():
    dts = [d for _, d in _pipeline(_raw())]
    steps = [b - a for a, b in zip(dts, dts[1:])]
    assert not any(s > 2 ** 31 for s in steps)


def test_the_corrected_media_rate_is_far_closer_to_wall_clock():
    """1200 frames arrived over about 43 s of wall clock at the measured
    28 fps tap rate. Uncorrected the container carried 1.82x that.
    """
    dts = [d for _, d in _pipeline(_raw())]
    media_s = (dts[-1] - dts[0]) / 90000
    wall_s = len(dts) / 28.0
    ratio = media_s / wall_s
    assert ratio < 1.25, f"still inflated at {ratio:.2f}x"


def test_a_tiny_slack_still_holds_every_invariant():
    """Correctness must not depend on the slack being generously sized."""
    out = _pipeline(_raw(), slack=1)
    dts = [d for _, d in out]
    assert dts == sorted(dts)
    assert all(d <= p for p, d in out)
    assert all(d >= 0 for _, d in out)
