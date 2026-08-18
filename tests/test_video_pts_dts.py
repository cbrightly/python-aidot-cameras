"""A presentation timestamp is not a decode timestamp, and the mux used both.

The camera's RTP timestamp carries PRESENTATION time and its pictures do not
arrive in presentation order: measured 2026-08-18, timestamps step backward by
up to 138060 ticks (1.53 s). The serve mux wrote `pkt.pts = pkt.dts = ts - v0`,
so a backward step became a backward DTS.

An mpegts muxer will not accept that. The write fails, `_flush_video` treats it
as terminal and ends the mux thread, and the camera "serves" while nothing
reaches a viewer. That is exactly what happened on 2026-08-18 when the 2**32
unwrap artifacts - which had been MASKING the backward steps - were corrected
without also fixing this: serve loop up, no exceptions, zero packets at the
viewer. Reverting restored 1440 packets.

So the two halves have to ship together, and these tests pin the invariants an
mpegts muxer requires:

    dts is non-decreasing
    dts <= pts for every packet
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.protocol import (
    _REORDER_SLACK_TICKS,
    _reorder_slack,
    video_pts_dts,
)

SLACK = _REORDER_SLACK_TICKS


def _run(pts_seq, slack=SLACK):
    st = {}
    return [video_pts_dts(st, p, slack) for p in pts_seq]


def test_dts_trails_pts_by_the_slack():
    assert video_pts_dts({}, 1_000_000, SLACK) == (1_000_000 + SLACK, 1_000_000)


def test_the_first_packet_never_has_a_negative_dts():
    """A negative DTS on the first packet is rejected outright.

    Measured 2026-08-18: with presentation starting at 0 and DTS trailing by
    the slack, the first DTS was -180000. Frames flowed, the mux logged
    nothing, and the viewer received nothing at all.
    """
    pts, dts = video_pts_dts({}, 0, SLACK)
    assert dts == 0 and pts == SLACK
    assert all(d >= 0 for _, d in _run([i * 6030 for i in range(50)]))


def test_an_ordinary_run_is_monotonic_and_ordered():
    out = _run([i * 6030 for i in range(200)])
    dts = [d for _, d in out]
    assert dts == sorted(dts)
    assert all(d <= p for p, d in out)


def test_the_backward_step_that_broke_serving_is_survived():
    """One picture 138060 ticks behind its predecessor - the measured worst."""
    out = _run([0, 6030, 12060, 12060 - 138060, 18090, 24120])
    dts = [d for _, d in out]
    assert dts == sorted(dts), "a backward DTS is what killed the mux thread"
    assert all(d <= p for p, d in out), "a DTS past its PTS is rejected outright"


def test_every_measured_excursion_holds_both_invariants():
    pts, cur = [], 0
    for i in range(400):
        if i and i % 55 == 0:
            cur -= 138060                 # the largest excursion seen
        else:
            cur += 6030
        pts.append(cur)
    out = _run(pts)
    dts = [d for _, d in out]
    assert dts == sorted(dts)
    assert all(d <= p for p, d in out)


def test_an_excursion_beyond_the_slack_still_never_inverts():
    """Slack is sized from measurement; correctness must not depend on it."""
    out = _run([0, 10, 20, 20 - 10 * SLACK, 30], slack=SLACK)
    assert all(d <= p for p, d in out)


def test_media_time_still_tracks_the_pts_span():
    """The point of the whole exercise: wall-clock rate, not an inflated one."""
    pts = [i * 6030 for i in range(1000)]
    out = _run(pts)
    span_pts = pts[-1] - pts[0]
    span_dts = out[-1][1] - out[0][1]
    assert abs(span_dts - span_pts) < 2, "dts advances at the same rate as pts"


def test_the_slack_has_a_seam():
    assert _reorder_slack({}) == _REORDER_SLACK_TICKS
    assert _reorder_slack({"AIDOT_REORDER_SLACK_TICKS": "9000"}) == 9000
    assert _reorder_slack({"AIDOT_REORDER_SLACK_TICKS": "junk"}) == _REORDER_SLACK_TICKS
