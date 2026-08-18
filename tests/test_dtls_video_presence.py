"""A connected DTLS session that never receives video is not healthy.

Measured 2026-08-17: an A000088 held a session with 62823 audio packets and
zero video for hours. ICE was connected, the peer connection state was
perfect, ffmpeg respawned for every consumer, and Home Assistant reported
"Stream has no video" on a 10/20/30/40 s retry ladder that never ended,
because nothing in the serve loop asks whether video arrives.

The camera answers the same way on every fresh open, so noticing is only half
of it: a loop that re-opens on every verdict would wake the camera every 15 s
forever. The abandon counter is the other half, and it mirrors the futile
keepalive limit the SDES path already carries.
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.client import (
    _futile_video_limit,
    _video_presence_verdict,
)

GRACE = 30.0


def test_inside_the_grace_window_we_wait():
    assert _video_presence_verdict(None, 100.0, 120.0, GRACE) == "waiting"


def test_video_arriving_is_ok():
    assert _video_presence_verdict(105.0, 100.0, 200.0, GRACE) == "ok"


def test_no_video_past_the_grace_window_gives_up():
    assert _video_presence_verdict(None, 100.0, 131.0, GRACE) == "give-up"


def test_a_session_that_had_video_never_gives_up_on_this_check():
    """Mid-session video loss is a different problem with a different fix."""
    assert _video_presence_verdict(105.0, 100.0, 1000.0, GRACE) == "ok"


def test_zero_grace_disables_the_check():
    assert _video_presence_verdict(None, 100.0, 1000.0, 0.0) == "waiting"


def test_not_yet_connected_is_not_a_verdict():
    assert _video_presence_verdict(None, None, 500.0, GRACE) == "waiting"


def test_futile_limit_defaults_to_five():
    assert _futile_video_limit({}) == 5


def test_futile_limit_zero_disables():
    assert _futile_video_limit({"AIDOT_DTLS_FUTILE_VIDEO_LIMIT": "0"}) == 0


def test_futile_limit_ignores_junk():
    assert _futile_video_limit({"AIDOT_DTLS_FUTILE_VIDEO_LIMIT": "many"}) == 5


class _FakeTrack:
    def __init__(self, kind):
        self.kind = kind


class _FakeReceiver:
    def __init__(self, kind):
        self.track = _FakeTrack(kind)


class _FakePC:
    def __init__(self, kinds):
        self._rx = [_FakeReceiver(k) for k in kinds]

    def getReceivers(self):
        return self._rx


def test_a_session_with_no_video_receiver_drops_the_previous_canary():
    """The canary is the serve loop's "has video arrived" signal.

    It lives on the receiver's decoder queue, so a session that never gets a
    video receiver leaves the attribute untouched - and the previous session's
    dict still reads frames > 0, which would answer "yes, video" for a session
    that has delivered nothing. This is exactly the failing camera's shape.
    """
    from aidot_cameras.camera.client import CameraMixin

    obj = CameraMixin.__new__(CameraMixin)
    obj._serve_video_canary = {"frames": 12345, "keyframes": 40,
                               "max_gap": 9, "gap": 1}
    obj.device_id = "dev"

    assert CameraMixin._install_av_taps(obj, _FakePC(["audio"]), None, None) is False
    assert obj._serve_video_canary is None


# --- frames are not the same thing as video ---------------------------------

from aidot_cameras.camera.client import _canary_has_decodable_video  # noqa: E402


def test_a_session_with_keyframes_has_delivered_video():
    assert _canary_has_decodable_video(
        {"frames": 300, "keyframes": 8}) is True


def test_frames_without_a_keyframe_are_not_video():
    """Measured 2026-08-17, and it is what the viewer actually saw.

    One session carried 600 frames and ZERO keyframes through roughly fifteen
    PLIs. The serve mux begins on a keyframe and discards everything before it,
    so it produced an empty stream: the viewer's playlist held three segments
    and no frames at all. The presence watchdog counted frames, saw 600, and
    stayed quiet for the whole session - the exact hole it was written to
    close, one field over.
    """
    assert _canary_has_decodable_video(
        {"frames": 600, "keyframes": 0}) is False


def test_an_absent_or_empty_canary_has_delivered_nothing():
    assert _canary_has_decodable_video(None) is False
    assert _canary_has_decodable_video({}) is False
