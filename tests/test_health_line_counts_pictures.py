"""The health line's frame figures count what a viewer is served.

In 1.0.0rc49 the line carried the tap canary's raw frame count and read at
about twice the picture rate (rc51 took the figures off). The reason is the
camera: it re-sends runs of frames it has already sent, and every consumer
drops the repeats with ``is_resent_video_frame``. The counter now applies
the consumers' rules at the tap, and this file proves it on the same 1200
real A000088 tap timestamps the re-send tests use.
"""

import os

from aidot_cameras.camera.client import (
    _correct_ts,
    _new_serve_canary,
    _observe_video_frame,
    _unwrap_state,
)
from aidot_cameras.camera.protocol import is_resent_video_frame
from aidot_cameras.camera.rtsp_publish import RESENT_MAX_BACK_S

FIXTURE = os.path.join(
    os.path.dirname(os.path.abspath(__file__)), "fixtures", "tap_timestamps_a000088.txt"
)


def _corrected_fixture():
    st = _unwrap_state()
    with open(FIXTURE) as fh:
        return [_correct_ts(st, int(x)) for x in fh if x.strip()]


def _replay(ts_list, keyframe_every=45):
    c = _new_serve_canary()
    for i, ts in enumerate(ts_list):
        _observe_video_frame(c, ts, i % keyframe_every == 0)
    return c


def test_the_fixture_counts_1200_frames_but_711_pictures():
    c = _replay(_corrected_fixture())
    assert c["frames"] == 1200
    assert c["resent"] == 489, "the re-send count the consumers measure"
    assert c["pictures"] == 1200 - 489 == 711
    assert c["pre_keyframe"] == 0 and c["resent_filter_resets"] == 0


def test_pictures_equal_what_the_publisher_forwards():
    """Same rule, same timestamps, same answer - not a number copied over."""
    ts = _corrected_fixture()
    state = {}
    forwarded = sum(1 for v in ts if not is_resent_video_frame(state, v - ts[0]))
    assert _replay(ts)["pictures"] == forwarded


def test_raw_frames_run_at_about_1_7x_the_pictures():
    """The rc49 observation ("twice the picture rate"), reproduced."""
    c = _replay(_corrected_fixture())
    assert 1.6 < c["frames"] / c["pictures"] < 1.8


def test_nothing_counts_before_the_first_keyframe():
    c = _new_serve_canary()
    for i in range(10):
        _observe_video_frame(c, 6000 * i, False)
    assert c["pictures"] == 0 and c["pre_keyframe"] == 10 and c["frames"] == 10
    _observe_video_frame(c, 6000 * 10, True)
    assert c["pictures"] == 1 and c["pict_keyframes"] == 1


def test_a_resent_keyframe_is_not_a_second_keyframe():
    c = _new_serve_canary()
    _observe_video_frame(c, 0, True)
    _observe_video_frame(c, 6000, False)
    _observe_video_frame(c, 0, True)  # the camera re-sends the keyframe
    assert c["keyframes"] == 2, "the raw count the watchdog reads still sees it"
    assert c["pict_keyframes"] == 1 and c["resent"] == 1 and c["pictures"] == 2


def test_the_keyframe_gap_is_measured_in_pictures():
    c = _new_serve_canary()
    _observe_video_frame(c, 0, True)
    for i in range(1, 31):
        _observe_video_frame(c, 6000 * i, False)
        _observe_video_frame(c, 6000 * i, False)  # every picture re-sent once
    _observe_video_frame(c, 6000 * 31, True)
    assert c["pict_max_gap"] == 30
    assert c["max_gap"] == 60, "the raw gap still counts the repeats"


def test_a_new_timestamp_base_restarts_the_filter_like_the_publisher():
    c = _new_serve_canary()
    _observe_video_frame(c, 90000 * 100, True)
    _observe_video_frame(c, 90000 * 100 + 6000, False)
    # Further back than any re-send run: a restarted clock, not a repeat.
    jump_back = int(RESENT_MAX_BACK_S * 90000) + 6000
    _observe_video_frame(c, 90000 * 100 - jump_back, False)
    _observe_video_frame(c, 90000 * 100 - jump_back + 6000, False)
    assert c["resent_filter_resets"] == 1
    assert c["resent"] == 0 and c["pictures"] == 4


def test_a_small_step_back_is_a_resend_not_a_reset():
    c = _new_serve_canary()
    _observe_video_frame(c, 0, True)
    _observe_video_frame(c, 60000, False)
    _observe_video_frame(c, 30000, False)
    assert c["resent"] == 1 and c["resent_filter_resets"] == 0
