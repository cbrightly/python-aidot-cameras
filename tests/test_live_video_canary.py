"""The video-presence watchdog tore down sessions that were streaming.

`_install_av_taps` stores a reference to the canary of the first video
receiver it taps. Measured 2026-08-18: a camera serving at 0.993x wall clock,
its canary logging frames=3600 and climbing, and the watchdog firing
"delivered no video in 30s" every 35 seconds - because the dict it was reading
was not the dict being filled. The released build fired that warning zero
times in two and a half hours, so this was a regression, and a false teardown
of a healthy session is worse than the thing the watchdog exists to catch.

`_live_video_canary` prefers the stored reference when it has frames and
otherwise asks the peer connection, so the check reads whichever canary is
actually being fed.
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.client import _live_video_canary


class _Track:
    def __init__(self, kind):
        self.kind = kind


class _Queue:
    def __init__(self, canary):
        if canary is not None:
            self._aidot_serve_canary = canary


class _Receiver:
    def __init__(self, kind, canary):
        self.track = _Track(kind)
        self._RTCRtpReceiver__decoder_queue = _Queue(canary)


class _PC:
    def __init__(self, receivers):
        self._r = receivers

    def getReceivers(self):
        return self._r


def test_a_stored_canary_with_frames_is_used_as_is():
    stored = {"frames": 12}
    pc = _PC([_Receiver("video", {"frames": 999})])
    assert _live_video_canary(pc, stored) is stored


def test_an_empty_stored_canary_falls_back_to_the_live_one():
    """The regression: the stored dict stays at zero while another fills."""
    stored = {"frames": 0}
    live = {"frames": 3600}
    pc = _PC([_Receiver("video", live)])
    assert _live_video_canary(pc, stored) is live


def test_a_missing_stored_canary_still_finds_the_live_one():
    live = {"frames": 42}
    assert _live_video_canary(_PC([_Receiver("video", live)]), None) is live


def test_audio_receivers_are_ignored():
    pc = _PC([_Receiver("audio", {"frames": 5000}),
              _Receiver("video", {"frames": 7})])
    assert _live_video_canary(pc, None)["frames"] == 7


def test_the_fullest_video_canary_wins():
    pc = _PC([_Receiver("video", {"frames": 3}),
              _Receiver("video", {"frames": 900})])
    assert _live_video_canary(pc, None)["frames"] == 900


def test_nothing_anywhere_is_not_an_error():
    assert _live_video_canary(_PC([]), None) is None
    assert _live_video_canary(_PC([_Receiver("video", None)]), None) is None


def test_a_broken_peer_connection_never_fails_the_check():
    """This must never be the reason a healthy session is judged dead."""
    class _Boom:
        def getReceivers(self):
            raise RuntimeError("boom")

    stored = {"frames": 0}
    assert _live_video_canary(_Boom(), stored) is stored
