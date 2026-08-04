"""A resolution choice must survive being made while nothing is streaming.

The setter rides the live session (SETSTREAMCTRL): with no session there is
nothing to send it over. It used to return False and drop the choice entirely,
and nothing re-sent it - so the usual outcome of changing resolution was
nothing at all, because a camera is idle far more often than it is streaming.
The caller was told the setting had been applied, and the comment in the
integration's select claimed it would "take effect on the next session", which
no code path did.
"""

import os
import sys
from unittest.mock import MagicMock

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import pytest

from aidot_cameras.camera.constants import _STREAM_QUALITY, SETSTREAMCTRL_CMD
from aidot_cameras.camera.controls import _CameraControlsMixin


class _Cam(_CameraControlsMixin):
    def __init__(self, session=None):
        self._stream_session = session
        self._desired_quality = None


def _session():
    s = MagicMock()
    s._avio_cmd = MagicMock(return_value=True)
    return s


@pytest.mark.asyncio
async def test_choice_made_while_idle_is_remembered_not_dropped():
    cam = _Cam(session=None)

    assert await cam.async_set_resolution("sd") is True
    assert cam._desired_quality == "sd"


@pytest.mark.asyncio
async def test_remembered_choice_is_sent_when_a_session_starts():
    cam = _Cam(session=None)
    await cam.async_set_resolution("sd")

    cam._stream_session = _session()
    cam._apply_pending_resolution()

    cam._stream_session._avio_cmd.assert_called_once()
    cmd, payload = cam._stream_session._avio_cmd.call_args[0]
    assert cmd == SETSTREAMCTRL_CMD
    assert payload[4] == _STREAM_QUALITY["sd"]   # AVIOCTRL_QUALITY_MIDDLE


@pytest.mark.asyncio
async def test_hd_round_trips_too():
    cam = _Cam(session=None)
    await cam.async_set_resolution("hd")
    cam._stream_session = _session()
    cam._apply_pending_resolution()
    assert cam._stream_session._avio_cmd.call_args[0][1][4] == _STREAM_QUALITY["hd"]


@pytest.mark.asyncio
async def test_a_live_session_still_gets_it_immediately():
    """Unchanged behaviour when the camera is already streaming."""
    cam = _Cam(session=_session())

    assert await cam.async_set_resolution("sd") is True
    cam._stream_session._avio_cmd.assert_called_once()


def test_nothing_is_sent_when_no_choice_was_ever_made():
    cam = _Cam(session=_session())
    cam._apply_pending_resolution()
    cam._stream_session._avio_cmd.assert_not_called()


@pytest.mark.asyncio
async def test_an_unknown_quality_is_still_rejected():
    cam = _Cam(session=_session())
    assert await cam.async_set_resolution("ultra") is False
    assert cam._desired_quality is None


def test_reapply_never_disturbs_a_working_stream():
    """A quality preference must never be able to break a stream that is up."""
    cam = _Cam(session=_session())
    cam._desired_quality = "sd"
    cam._stream_session._avio_cmd.side_effect = RuntimeError("channel gone")

    cam._apply_pending_resolution()          # must not raise
