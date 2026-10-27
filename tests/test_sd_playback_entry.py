"""async_open_sd_playback: the open an A001064 played a recording into.

What it must ask for, from the run that played (2026-10-27, peer id
``..._040cb3_1_0_0``): client class 0, playCmd 1 (SD card), StreamID 0, and
an SD playback request whose PLAY_START names the record.
"""

import asyncio
import re

import pytest

from aidot_cameras.camera.client import CameraMixin
from aidot_cameras.camera.sd_events import SdEvent
from aidot_cameras.camera.sd_playback import (
    PLAY_START,
    SESSION_MODE_SD,
    SdPlaybackStart,
    _CameraSdPlaybackMixin,
    playcontrol_payload,
)
from aidot_cameras.exceptions import AidotSdPlaybackUnavailable

PEER = re.compile(r"^[0-9a-f]{32}_([0-9a-f])[0-9a-f]{5}_(\d+)_(\d+)_(\d+)$")
REC = SdEvent(2026, 10, 21, 17, 24, 44, 0, 0, 0)


class _Task:
    def done(self):
        return False


class _Camera(_CameraSdPlaybackMixin):
    device_id = "12b144cb12da"
    generate_webrtc_peer_id = staticmethod(CameraMixin.generate_webrtc_peer_id)

    def __init__(self, sdes=True, live=False, streaming=False):
        self.is_sdes_camera = sdes
        self.has_live_session = live
        self._stream_task = _Task() if streaming else None
        self.opens = []

    async def async_open_webrtc_stream(self, on_frame=None, **kw):
        self.opens.append((on_frame, kw))
        return "session"


def _open(cam, **kw):
    return asyncio.run(cam.async_open_sd_playback(REC, **kw))


def test_it_opens_with_the_peer_id_the_camera_played_for():
    cam = _Camera()
    assert _open(cam) == "session"
    ((_, kw),) = cam.opens
    assert PEER.match(kw["reuse_peer_id"]).groups() == ("0", "1", "0", "0")


def test_it_hands_the_open_a_play_start_for_the_record():
    cam = _Camera()
    _open(cam, sd_channel=3)
    start = cam.opens[0][1]["_sd_playback"]
    assert isinstance(start, SdPlaybackStart)
    assert start.session_mode == SESSION_MODE_SD
    assert start.request == playcontrol_payload(PLAY_START, REC, sd_channel=3)


def test_the_serving_options_reach_the_open():
    cam = _Camera()
    sink = object()
    _open(
        cam,
        on_frame=sink,
        output_path="/tmp/x.ts",
        rtsp_push_url="rtsp://h/p",
        max_seconds=60,
        timeout=20,
    )
    on_frame, kw = cam.opens[0]
    assert on_frame is sink
    assert kw["output_path"] == "/tmp/x.ts"
    assert kw["rtsp_push_url"] == "rtsp://h/p"
    assert (kw["max_seconds"], kw["timeout"]) == (60, 20)


def test_a_dtls_camera_plays_into_a_ts_file():
    cam = _Camera(sdes=False)
    assert _open(cam, output_path="/tmp/x.ts") == "session"
    ((_, kw),) = cam.opens
    # Field 5 is the StreamID either way; the transport digit is not sent.
    assert PEER.match(kw["reuse_peer_id"]).groups() == ("0", "1", "0", "0")


@pytest.mark.parametrize("busy", [{"live": True}, {"streaming": True}])
def test_a_live_stream_on_the_camera_is_refused_before_anything_is_sent(busy):
    # One media slot per camera: a second session fights the first for it.
    cam = _Camera(**busy)
    with pytest.raises(AidotSdPlaybackUnavailable, match="async_stop_streaming"):
        _open(cam)
    assert cam.opens == []


def test_a_finished_stream_loop_does_not_block_it():
    cam = _Camera()
    cam._stream_task = type("T", (), {"done": lambda self: True})()
    assert _open(cam) == "session"


def test_every_camera_client_has_it():
    assert CameraMixin.async_open_sd_playback is (
        _CameraSdPlaybackMixin.async_open_sd_playback
    )
