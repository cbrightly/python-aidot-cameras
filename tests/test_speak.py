"""Unit tests for DeviceClient.async_speak - deterministic, no network.

Validates that async_speak reuses a warm talk-capable session, drives the PCM
provider through to exhaustion (None), and stops talk afterwards; and that it
returns False when no talk-capable session is available.

Runs under pytest, or standalone:  python tests/test_speak.py
"""
import asyncio
import os
import sys
import types

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.device_client import DeviceClient


class _FakeSession:
    """Stand-in for WebRTCSession that drains the provider like the real track."""

    def __init__(self, talk_supported=True):
        self.talk_supported = talk_supported
        self.frames = []
        self.started = False
        self.stopped = False
        self.session_stopped = False

    async def async_start_talk(self, provider):
        if not self.talk_supported:
            return False
        self.started = True
        frame = provider()
        while frame is not None:        # simulate aiortc polling the track
            self.frames.append(frame)
            frame = provider()
        return True

    async def async_stop_talk(self):
        self.stopped = True
        return True

    async def stop(self):
        self.session_stopped = True


def _run(dc, provider, **kw):
    return asyncio.run(DeviceClient.async_speak(dc, provider, **kw))


def test_reuses_warm_session_and_drives_provider():
    fake = _FakeSession()
    dc = types.SimpleNamespace(_stream_session=fake)
    frames = [b"\x01" * 320, b"\x02" * 320, b"\x03" * 320]
    it = iter(frames)
    ok = _run(dc, lambda: next(it, None), max_seconds=5)
    assert ok is True
    assert fake.frames == frames          # every frame reached the session
    assert fake.started and fake.stopped  # talk started + stopped
    assert fake.session_stopped is False  # warm session is NOT closed (reused)


def test_returns_false_when_no_talkable_session_and_open_fails():
    # No warm session; opening one always fails -> async_speak returns False.
    async def _open_fails(**kw):
        raise RuntimeError("camera busy")

    dc = types.SimpleNamespace(
        _stream_session=None,
        async_open_webrtc_stream=_open_fails,
        device_id="dev-test",
    )
    it = iter([b"\x00" * 320])
    ok = _run(dc, lambda: next(it, None), retries=2)
    assert ok is False


def test_skips_warm_session_without_talk_support_then_opens():
    # Warm session exists but cannot talk -> async_speak opens a fresh one and
    # closes it afterwards.
    warm = _FakeSession(talk_supported=False)
    fresh = _FakeSession(talk_supported=True)

    async def _open(**kw):
        return fresh

    dc = types.SimpleNamespace(
        _stream_session=warm,
        async_open_webrtc_stream=_open,
        device_id="dev-test",
    )
    it = iter([b"\x05" * 320, b"\x06" * 320])
    ok = _run(dc, lambda: next(it, None), max_seconds=5)
    assert ok is True
    assert fresh.frames == [b"\x05" * 320, b"\x06" * 320]
    assert fresh.session_stopped is True   # owned session IS closed afterwards
    assert warm.started is False


if __name__ == "__main__":
    fns = [v for k, v in sorted(globals().items())
           if k.startswith("test_") and callable(v)]
    for fn in fns:
        fn()
        print(f"PASS {fn.__name__}")
    print(f"\nALL {len(fns)} TESTS PASSED")
