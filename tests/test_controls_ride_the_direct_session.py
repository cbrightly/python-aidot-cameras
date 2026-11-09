"""Controls ride the session the public open returned, not only the serve loop's.

``async_set_resolution`` and ``async_ptz_move`` found their session on
``_stream_session``, which only the serve loop (Home Assistant's path) sets. A
caller of ``async_open_webrtc_stream`` had every control answer "not
streaming" with frames flowing - live_validate set the private attribute
itself to work around it. The open now records its session, and a control
uses it while it is up.
"""

from __future__ import annotations

import asyncio
import struct

import pytest

from aidot_cameras.camera.constants import (
    IOTYPE_USER_IPCAM_PTZ_COMMAND,
    IOTYPE_USER_IPCAM_SETSTREAMCTRL_REQ,
)


class _Session:
    def __init__(self) -> None:
        self.cmds: list = []
        self.requests: list = []

    def _avio_cmd(self, cmd, payload) -> bool:
        self.cmds.append((cmd, payload))
        return True

    async def async_avio_request(self, cmd, payload, *, response_cmd, timeout):
        self.requests.append((cmd, payload))
        return

    async def stop(self) -> None:
        self._stop_task = asyncio.get_running_loop().create_future()
        self._stop_task.set_result(None)


@pytest.fixture
def cam(make_camera_device_client):
    c = make_camera_device_client("A000088")
    c._stream_session = None
    return c


def test_without_any_session_the_resolution_is_only_remembered(cam, caplog):
    with caplog.at_level("INFO", logger="aidot_cameras.camera.controls"):
        assert asyncio.run(cam.async_set_resolution("sd")) is True
    assert any("remembered" in r.getMessage() for r in caplog.records)
    assert cam._desired_quality == "sd"


def test_the_resolution_rides_the_session_the_open_returned(cam):
    s = _Session()
    cam._note_direct_session(s)
    assert asyncio.run(cam.async_set_resolution("hd")) is True
    assert s.requests == [
        (IOTYPE_USER_IPCAM_SETSTREAMCTRL_REQ, struct.pack("<IB3x", 0, 1))
    ]


def test_a_remembered_resolution_is_applied_to_the_opened_session(cam):
    asyncio.run(cam.async_set_resolution("sd"))  # nothing open: remembered
    s = _Session()
    cam._note_direct_session(s)
    assert s.cmds == [(IOTYPE_USER_IPCAM_SETSTREAMCTRL_REQ, struct.pack("<IB3x", 0, 5))]


def test_ptz_rides_the_session_the_open_returned(cam):
    s = _Session()
    cam._note_direct_session(s)
    assert asyncio.run(cam.async_ptz_move("left")) is True
    assert s.cmds and s.cmds[0][0] == IOTYPE_USER_IPCAM_PTZ_COMMAND


def test_a_stopped_session_no_longer_carries_controls(cam, caplog):
    s = _Session()
    cam._note_direct_session(s)
    asyncio.run(s.stop())
    with caplog.at_level("INFO", logger="aidot_cameras.camera.controls"):
        asyncio.run(cam.async_set_resolution("hd"))
    assert s.requests == []
    assert any("remembered" in r.getMessage() for r in caplog.records)


def test_the_serve_loops_session_wins(cam):
    direct, serve = _Session(), _Session()
    cam._note_direct_session(direct)
    cam._stream_session = serve
    asyncio.run(cam.async_set_resolution("hd"))
    assert serve.requests and direct.requests == []
