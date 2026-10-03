"""Stopping a session twice must be harmless.

Callers stop defensively - the release gate stops before judging a recording
and again in its ``finally``, the serve loops stop on every exit path - so a
second ``stop()`` is normal, not misuse. It used to re-run the whole teardown:

- after a slow MQTT teardown the first stop's ``wait_for`` cancels the future,
  and the second stop then awaits that cancelled future and raises
  ``CancelledError``, which escapes every ``except Exception`` above it;
- when the first ``pc.close()`` raises part-way, aiortc never resolves its
  "closed" marker, so a second ``close()`` waits on it forever;
- an SDES session re-logs ffmpeg's whole stderr tail at WARNING.

These use the real session classes; only their collaborators are faked.
"""

import asyncio
from types import SimpleNamespace
from unittest.mock import AsyncMock, MagicMock

import pytest

import aidot_cameras.camera.sdes as sdes_mod
import aidot_cameras.camera.webrtc as webrtc_mod
from aidot_cameras.camera.sdes import SdesSession
from aidot_cameras.camera.webrtc import WebRTCSession


def _webrtc(pc=None, mqtt_fut=None, recorder=None):
    return WebRTCSession(
        pc=pc if pc is not None else MagicMock(close=AsyncMock()),
        outgoing_q=MagicMock(),
        mqtt_fut=mqtt_fut if mqtt_fut is not None else MagicMock(),
        recorder=recorder,
        track_tasks=[],
        dc=SimpleNamespace(send=lambda d: None),
        audio_sender=MagicMock(),
        talk_track=MagicMock(),
        talk_holder={},
    )


async def test_webrtc_second_stop_after_a_slow_mqtt_teardown_does_not_raise(
    monkeypatch,
):
    monkeypatch.setattr(webrtc_mod, "_STOP_MQTT_WAIT_S", 0.05)
    never = asyncio.get_running_loop().create_future()  # MQTT never finishes
    s = _webrtc(mqtt_fut=never)
    await s.stop()
    await asyncio.wait_for(s.stop(), timeout=2.0)  # must neither raise nor hang


async def test_webrtc_second_stop_after_close_raised_returns_at_once():
    pc = MagicMock(close=AsyncMock(side_effect=RuntimeError("SSL write refused")))
    s = _webrtc(pc=pc)
    await s.stop()  # a failing close must not escape stop()
    await asyncio.wait_for(s.stop(), timeout=1.0)
    pc.close.assert_awaited_once()


async def test_webrtc_recorder_is_stopped_once():
    recorder = MagicMock(stop=AsyncMock())
    s = _webrtc(recorder=recorder)
    await s.stop()
    await s.stop()
    recorder.stop.assert_awaited_once()


def _sdes(mqtt_fut=None):
    s = SdesSession(
        proc=MagicMock(),
        sdp_path="/tmp/nonexistent.sdp",
        outgoing_q=MagicMock(),
        mqtt_fut=mqtt_fut if mqtt_fut is not None else MagicMock(),
    )
    s._proc.poll.return_value = 0
    s._proc.stderr.read.return_value = b"[mpegts] some ffmpeg warning\n"
    return s


async def test_sdes_second_stop_does_not_rerun_teardown(monkeypatch):
    logged = []
    s = _sdes()
    monkeypatch.setattr(s, "_log_ffmpeg_stderr", lambda b: logged.append(b))
    await s.stop()
    await s.stop()
    s._proc.terminate.assert_called_once()
    assert len(logged) == 1, "ffmpeg's stderr tail was logged again by the second stop"
    assert s._outgoing_q.put_nowait.call_count == 1


async def test_sdes_second_stop_after_a_slow_mqtt_teardown_does_not_raise(monkeypatch):
    monkeypatch.setattr(sdes_mod, "_STOP_MQTT_WAIT_S", 0.05)
    never = asyncio.get_running_loop().create_future()
    s = _sdes(mqtt_fut=never)
    await s.stop()
    await asyncio.wait_for(s.stop(), timeout=2.0)


@pytest.mark.parametrize("kind", ["webrtc", "sdes"])
async def test_concurrent_stops_tear_down_once(kind):
    # Two callers racing (a viewer leaving while the serve loop exits) must not
    # interleave two teardowns either.
    if kind == "webrtc":
        pc = MagicMock(close=AsyncMock())
        s = _webrtc(pc=pc)
        await asyncio.gather(s.stop(), s.stop())
        pc.close.assert_awaited_once()
    else:
        s = _sdes()
        await asyncio.gather(s.stop(), s.stop())
        s._proc.terminate.assert_called_once()
