"""Drive the real SDES keepalive loop on a fake clock.

The loop (``CameraMixin._sdes_keepalive_loop_inner``) is the code that decides
what a failed open, an ended session or an absent viewer costs a camera: the
backoff, the peer id it re-offers on, whether it goes dormant. Those are
behaviours, so the tests that pin them drive the loop rather than read it.

Only the I/O is faked, on the instance of a real client:

- ``async_open_webrtc_stream`` replays a script of outcomes - an exception to
  raise or a :class:`FakeSession` to return - and records every call's kwargs;
- ``_viewer_present`` answers from a callable of the fake clock;
- ``_deregister_go2rtc`` records instead of calling go2rtc.

Everything else - the pacer, the not-ready burst, the abandon rule, the idle
rule, ``_go_dormant`` - is the production code. Time is a fake monotonic
clock that ``asyncio.sleep`` and ``asyncio.wait`` advance instead of waiting,
so an hour of keepalive runs in milliseconds. The client module's ``time`` is
rebound, never ``time.monotonic`` itself (that is the event loop's clock).
When the script runs out the next open is cancelled, and so is a watch that
outlives ``max_time``; every exit of the loop turns that into a clean return.
"""

from __future__ import annotations

import asyncio
import time as _real_time
import types

import aidot_cameras.camera.client as cc
from aidot_cameras.exceptions import AidotCameraNoMedia

_real_sleep = asyncio.sleep


class Clock:
    def __init__(self, start: float = 1000.0) -> None:
        self.now = start

    def monotonic(self) -> float:
        return self.now


class FakeSession:
    """A keepalive session that lives ``life`` fake seconds, then ends.

    ``media`` says whether it delivered media; a session that did keeps its
    media and STUN-answer stamps current, so neither watchdog fires and only
    its own end, the idle rule or a cap can stop it. ``life=None`` never ends
    on its own.
    """

    talk_supported = True

    def __init__(self, *, media: bool = True, life: float | None = 30.0) -> None:
        self.media = media
        self.life = life
        self.opened_at: float | None = None
        self.stopped = 0
        self._ended: asyncio.Event | None = None
        self._clock: Clock | None = None

    def bind(self, clock: Clock) -> None:
        self._clock = clock
        self.opened_at = clock.now
        self._ended = asyncio.Event()

    @property
    def last_media_monotonic(self) -> float:
        return self._clock.now if self.media else 0.0

    @property
    def last_ice_answer_monotonic(self) -> float:
        return self._clock.now

    @staticmethod
    def is_stalled(last_media, started_at, now, grace=60.0) -> bool:
        return False

    def media_stats(self) -> dict:
        n = 10 if self.media else 0
        return {"packets": n, "bytes": n * 100}

    def ends_by(self, t: float) -> bool:
        return self.life is not None and self.opened_at + self.life <= t

    async def wait_done(self) -> None:
        await self._ended.wait()

    async def stop(self) -> None:
        self.stopped += 1
        self._ended.set()


class SdesKeepaliveRun:
    """One run of the SDES keepalive loop against a script of open outcomes."""

    def __init__(
        self,
        monkeypatch,
        client,
        outcomes,
        *,
        viewer=None,
        max_sleeps: int = 400,
        max_time: float = 7200.0,
        url: str = "rtsp://127.0.0.1:8554/cam",
    ) -> None:
        self.client = client
        self.outcomes = list(outcomes)
        self.clock = clock = Clock()
        self.viewer = viewer if viewer is not None else (lambda now: None)
        self.max_sleeps = max_sleeps
        self.deadline = clock.now + max_time
        self.opens: list[dict] = []
        self.open_times: list[float] = []
        self.sleeps: list[float] = []
        self.sessions: list[FakeSession] = []
        self.deregistered = 0
        self.dormant: list[dict] = []
        self.dormant_at: list[float] = []
        self.viewer_asks: list[int] = []
        self._current: FakeSession | None = None

        client._streaming_active = True
        client._keepalive_rtsp_url = url
        client._serve_relay = None

        fake_time = types.SimpleNamespace(
            **{
                k: getattr(_real_time, k)
                for k in dir(_real_time)
                if not k.startswith("_")
            }
        )
        fake_time.monotonic = clock.monotonic
        monkeypatch.setattr(cc, "time", fake_time)

        async def _sleep(delay, *a, **k):
            self.sleeps.append(delay)
            if len(self.sleeps) > self.max_sleeps:
                raise asyncio.CancelledError
            clock.now += max(0.0, float(delay))
            await _real_sleep(0)

        async def _wait(aws, *, timeout=None, **k):
            if clock.now > self.deadline:
                raise asyncio.CancelledError
            sess = self._current
            target = clock.now + (timeout or 0.0)
            if sess is not None and sess.ends_by(target):
                clock.now = max(clock.now, sess.opened_at + sess.life)
                sess._ended.set()
            else:
                clock.now = target
            for _ in range(3):
                await _real_sleep(0)
            done = {a for a in aws if a.done()}
            return done, set(aws) - done

        monkeypatch.setattr(cc.asyncio, "sleep", _sleep)
        monkeypatch.setattr(cc.asyncio, "wait", _wait)

        async def _open(**kwargs):
            self.opens.append(kwargs)
            self.open_times.append(clock.now)
            if not self.outcomes:
                raise asyncio.CancelledError
            out = self.outcomes.pop(0)
            if isinstance(out, BaseException):
                raise out
            out.bind(clock)
            self.sessions.append(out)
            self._current = out
            return out

        async def _viewer_present(port):
            self.viewer_asks.append(port)
            return self.viewer(clock.now)

        async def _deregister():
            self.deregistered += 1

        real_go_dormant = client._go_dormant

        async def _go_dormant(*, release_url, why):
            self.dormant.append({"release_url": release_url, "why": why})
            self.dormant_at.append(clock.now)
            await real_go_dormant(release_url=release_url, why=why)

        client.async_open_webrtc_stream = _open
        client._viewer_present = _viewer_present
        client._deregister_go2rtc = _deregister
        client._go_dormant = _go_dormant

    def run(self) -> "SdesKeepaliveRun":
        asyncio.run(self.client._sdes_keepalive_loop_inner())
        return self

    @property
    def peer_ids(self) -> list:
        return [o["reuse_peer_id"] for o in self.opens]


def no_media(times: int) -> list:
    """``times`` opens the camera answered and then sent nothing on."""
    return [AidotCameraNoMedia(12.0) for _ in range(times)]


# --------------------------------------------------------------------------- #
# The DTLS serve loop
# --------------------------------------------------------------------------- #
#
# The DTLS serve cycle times its release rules on the event loop's own clock
# (``loop.time()``), which a test must not fake. So this driver runs on real
# time with every ``asyncio.sleep`` in the client scaled down by ``scale``:
# the loop's half-second poll becomes 5 ms, and the release windows a test
# sets (idle, start-up grace, the unknown cap) are given in real seconds.


class FakePc:
    def __init__(self) -> None:
        self.connectionState = "connected"


class FakeDtlsSession:
    def __init__(self) -> None:
        self._pc = FakePc()
        self.stopped = 0

    async def get_stats(self) -> dict:
        return {}

    async def stop(self) -> None:
        self.stopped += 1


class FakeDirectTsServer:
    """Records the port the serve cycle binds instead of binding it."""

    instances: list = []

    def __init__(self, port: int) -> None:
        self.port = port
        FakeDirectTsServer.instances.append(self)

    def start(self) -> None:
        pass

    def write(self, data) -> int:
        return len(data)

    def close(self) -> None:
        pass


class DtlsServeRun:
    """One run of the DTLS serve loop against a list of fake sessions."""

    def __init__(
        self,
        monkeypatch,
        client,
        sessions,
        *,
        viewer=None,
        url: str = "http://127.0.0.1:18555/cam.ts",
        scale: float = 0.01,
        timeout: float = 5.0,
        frames: bool = True,
        publish_for: float | None = None,
        video: bool = True,
    ) -> None:
        import threading

        self.client = client
        self.sessions = list(sessions)
        self.viewer = viewer if viewer is not None else (lambda elapsed: None)
        self.timeout = timeout
        self.opens = 0
        self.viewer_asks: list[int] = []
        self.deregistered = 0
        self.dormant: list[dict] = []
        self.dormant_after: list[float] = []
        self.mux_targets: list[str] = []
        self.started_at = _real_time.monotonic()
        FakeDirectTsServer.instances = []
        self.direct = FakeDirectTsServer.instances

        client._streaming_active = True
        client._keepalive_rtsp_url = url
        client._serve_relay = None
        client._serve_relay_opt = False

        real_sleep = asyncio.sleep

        async def _sleep(delay, *a, **k):
            await real_sleep(max(0.0, float(delay)) * scale)

        monkeypatch.setattr(cc.asyncio, "sleep", _sleep)

        def _elapsed() -> float:
            return _real_time.monotonic() - self.started_at

        async def _open(**kwargs):
            self.opens += 1
            if not self.sessions:
                raise asyncio.CancelledError
            return self.sessions.pop(0)

        async def _viewer_present(port):
            self.viewer_asks.append(port)
            return self.viewer(_elapsed())

        async def _deregister():
            self.deregistered += 1

        async def _pli(pc):
            return None

        real_go_dormant = client._go_dormant

        async def _go_dormant(*, release_url, why):
            self.dormant.append({"release_url": release_url, "why": why})
            self.dormant_after.append(_elapsed())
            await real_go_dormant(release_url=release_url, why=why)

        client.async_open_webrtc_stream = _open
        client._viewer_present = _viewer_present
        client._deregister_go2rtc = _deregister
        client._go_dormant = _go_dormant
        client._send_video_pli = _pli
        client._install_av_taps = lambda pc, vq, aq: True

        def _mux(vq, aq, wfile, progress, stop):
            self.mux_targets.append("mux")
            while not stop.is_set():
                if frames:
                    progress[0] = _real_time.monotonic()
                stop.wait(0.002)

        def _publish(vq, aq, url, progress, stop, **kw):
            self.mux_targets.append("publish")
            end = _real_time.monotonic() + (publish_for or 1e9)
            while not stop.is_set() and _real_time.monotonic() < end:
                if frames:
                    progress[0] = _real_time.monotonic()
                stop.wait(0.002)

        monkeypatch.setattr(cc, "_dtls_av_mux_run", _mux)
        monkeypatch.setattr(cc, "dtls_rtp_publish_run", _publish)
        monkeypatch.setattr(cc, "_DirectTsServer", FakeDirectTsServer)
        monkeypatch.setattr(cc, "_live_video_canary", lambda pc, stored: None)
        monkeypatch.setattr(cc, "_video_has_started", lambda canary: video)
        self._threading = threading

    def run(self, entry: str = "_dtls_serve_loop_inner") -> "DtlsServeRun":
        async def _go():
            try:
                await asyncio.wait_for(getattr(self.client, entry)(), self.timeout)
            except TimeoutError:
                pass

        asyncio.run(_go())
        return self


def dtls_view(
    monkeypatch,
    make_camera_device_client,
    viewer,
    *,
    idle=0.3,
    grace=0.3,
    cap=None,
    battery=False,
    timeout=2.0,
    **kw,
):
    """One DTLS serve run on real time, the release windows in seconds."""
    monkeypatch.setenv("AIDOT_STREAM_STARTUP_GRACE_S", str(grace))
    if cap is not None:
        monkeypatch.setenv("AIDOT_BATTERY_UNKNOWN_VIEWER_RELEASE_S", str(cap))
    cam = make_camera_device_client("A000088")
    if battery:
        monkeypatch.setattr(type(cam), "is_battery_camera", property(lambda s: True))
    cam._stream_idle_opt = idle
    return DtlsServeRun(
        monkeypatch, cam, [FakeDtlsSession()], viewer=viewer, timeout=timeout, **kw
    ).run()
