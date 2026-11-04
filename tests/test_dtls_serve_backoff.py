"""The DTLS serve loop backs off a camera whose sessions keep failing fast.

Measured 2026-11-04 on an A000088: for half an hour its sessions died within
seconds of opening, the next open came on the bare 15 s gate each time (an
open that connects resets the pacer), then opens stopped connecting at all
("ICE connection not established"), and every retry 15 s after one of those
was refused as busy (-50002) - the camera still held the session it had
accepted for the offer that never connected. Then it went offline.

Pinned here:

* a session that ends short (or never delivers video) is counted on its own,
  apart from the open-failure pacer, and the loop waits 30 s after the first,
  then up to a doubling ceiling, before reopening - never less than 30 s;
* a session that lasted and delivered video clears that count, however its
  last serve cycle ended;
* open failures are paced exactly as before: a successful open resets them,
  and short sessions never push a camera into the slow probe;
* the wait does not hold the concurrent-stream slot;
* after an ICE failure, on a plain or a shifted answer, the next open waits at
  least AIDOT_BUSY_RETRY_S, even when the offline pause ends early.

The loop tests drive the REAL ``_dtls_serve_loop_inner`` (and
``_dtls_serve_loop``) on a fake clock. Only the client module's ``asyncio`` and
``time`` are rebound, to proxies of the real modules with ``sleep``,
``get_running_loop().time()`` and ``monotonic`` on the fake clock; the global
``asyncio.sleep`` is never touched. The serve cycle is replaced by a fake that
lives a session's scripted length; the open attempt, session preparation and
session-end pacing are the production code.

Repo convention: no pytest-asyncio; drive coroutines with asyncio.run().
"""

from __future__ import annotations

import ast
import asyncio
import inspect
import textwrap
import time as _real_time
import types

import pytest

from aidot_cameras import settings
from aidot_cameras.camera import client as cc
from aidot_cameras.camera import protocol
from aidot_cameras.camera.client import (
    CameraMixin,
    _session_end_pacing,
    _short_session_delay,
)
from aidot_cameras.exceptions import (
    AidotCameraNotReady,
    AidotIceNotEstablished,
    AidotNegotiationFailed,
)

_real_sleep = asyncio.sleep
_URL = "http://127.0.0.1:18555/cam.ts"
_BUSY = settings.read("AIDOT_BUSY_RETRY_S")


# --------------------------------------------------------------------------- #
# The pure pacing                                                              #
# --------------------------------------------------------------------------- #


def test_the_first_short_session_waits_exactly_30s_at_either_end_of_the_jitter():
    for r in (0.0, 1.0):
        assert _short_session_delay(1, rand=lambda r=r: r) == 30.0


def test_jitter_never_lands_below_the_30s_floor():
    low = [_short_session_delay(n, rand=lambda: 0.0) for n in range(1, 9)]
    assert low == [30.0] * 8


def test_the_ceiling_doubles_per_short_session_up_to_300s():
    high = [_short_session_delay(n, rand=lambda: 1.0) for n in range(1, 8)]
    assert high == [30.0, 60.0, 120.0, 240.0, 300.0, 300.0, 300.0]


def test_a_session_that_lasted_with_video_clears_the_count():
    assert _session_end_pacing(4, 120.0, had_video=True) == (0, 0.0)


def test_a_short_or_video_less_session_adds_one_and_waits():
    one = lambda: 1.0  # noqa: E731
    assert _session_end_pacing(0, 5.0, had_video=True, rand=one) == (1, 30.0)
    assert _session_end_pacing(1, 5.0, had_video=True, rand=one) == (2, 60.0)
    assert _session_end_pacing(0, 600.0, had_video=False, rand=one) == (1, 30.0)


def test_the_threshold_is_a_knob(monkeypatch):
    assert settings.read("AIDOT_DTLS_SHORT_SESSION_S") == 30.0
    monkeypatch.setenv("AIDOT_DTLS_SHORT_SESSION_S", "90")
    assert settings.read("AIDOT_DTLS_SHORT_SESSION_S") == 90.0


def test_the_ice_failure_is_still_a_negotiation_failure():
    """Callers that catch AidotNegotiationFailed keep catching it."""
    assert issubclass(AidotIceNotEstablished, AidotNegotiationFailed)


def test_a_plain_not_ready_does_not_hold_a_session():
    assert AidotCameraNotReady("cam").holds_session is False


# --------------------------------------------------------------------------- #
# The loop, on a fake clock                                                    #
# --------------------------------------------------------------------------- #


class _Sess:
    """A session the fake serve cycle keeps for ``life`` fake seconds."""

    def __init__(self, life: float, *, video: bool = True, idle: bool = False):
        self.life = life
        self.video = video
        self.idle = idle
        self._pc = types.SimpleNamespace(connectionState="connected")
        self.stopped = 0

    async def stop(self) -> None:
        self.stopped += 1


def _held_not_ready() -> AidotCameraNotReady:
    exc = AidotCameraNotReady("cam")
    exc.holds_session = True
    return exc


class _Run:
    """One run of the serve loop against a script of open outcomes."""

    def __init__(self, monkeypatch, cam, script, *, slots=None, on_sleep=None):
        self.cam = cam
        self.script = list(script)
        self.now = 1000.0
        self.opens: list[float] = []
        self.ends: list[float] = []
        self.sleeps: list[tuple[float, float, bool]] = []  # (at, delay, waiting)
        self.slot_free_in_wait: list[bool] = []
        self.slot_held_in_serve: list[bool] = []
        self.dormant: list[str] = []
        self.slots = slots
        self._waiting = False
        self._on_sleep = on_sleep

        cam._streaming_active = True
        cam._keepalive_rtsp_url = _URL
        cam._serve_relay = None

        run = self

        async def _sleep(delay, *a, **k):
            run.sleeps.append((run.now, delay, run._waiting))
            if len(run.sleeps) > 500:
                raise asyncio.CancelledError
            if run._waiting and run.slots is not None:
                run.slot_free_in_wait.append(not run.slots.locked())
            if run._on_sleep is not None:
                run._on_sleep(run)
            run.now += max(0.0, float(delay))
            await _real_sleep(0)

        class _Loop:
            @staticmethod
            def time() -> float:
                return run.now

        fake_asyncio = types.SimpleNamespace(
            **{k: getattr(asyncio, k) for k in dir(asyncio) if not k.startswith("_")}
        )
        fake_asyncio.sleep = _sleep
        fake_asyncio.get_running_loop = lambda: _Loop
        fake_time = types.SimpleNamespace(
            **{
                k: getattr(_real_time, k)
                for k in dir(_real_time)
                if not k.startswith("_")
            }
        )
        fake_time.monotonic = lambda: run.now
        # Scoped to the client module: the real asyncio and time are untouched.
        monkeypatch.setattr(cc, "asyncio", fake_asyncio)
        monkeypatch.setattr(cc, "time", fake_time)
        # rand 1.0: the top of every jitter band, so delays are exact.
        monkeypatch.setattr(
            protocol, "random", types.SimpleNamespace(random=lambda: 1.0)
        )

        async def _open(**kwargs):
            run._waiting = False
            run.opens.append(run.now)
            if not run.script:
                raise asyncio.CancelledError
            out = run.script.pop(0)
            if isinstance(out, BaseException):
                raise out
            return out

        async def _serve(self_, ps):
            sess = ps.session
            if run.slots is not None:
                run.slot_held_in_serve.append(run.slots.locked())
            run.now += sess.life
            ps._session_had_video = sess.video
            ps.idle_release = sess.idle
            await sess.stop()
            run.ends.append(run.now)
            run._waiting = True
            return

        async def _go_dormant(*, release_url, why):
            run.dormant.append(why)
            cam._streaming_active = False

        cam.async_open_webrtc_stream = _open
        cam._install_av_taps = lambda pc, vq, aq: True
        cam._maybe_start_serve_relay = lambda url: None
        cam._go_dormant = _go_dormant
        monkeypatch.setattr(cc, "_dtls_serve_loop_serve_cycle", _serve)

    def run(self, entry: str = "_dtls_serve_loop_inner") -> "_Run":
        asyncio.run(getattr(self.cam, entry)())
        return self

    def gaps(self) -> list[float]:
        """Seconds from each session's end to the next open."""
        return [
            round(self.opens[i + 1] - end, 6)
            for i, end in enumerate(self.ends)
            if i + 1 < len(self.opens)
        ]

    def open_sleeps(self) -> list[float]:
        """The delays slept outside a session-end wait (failed-open retries)."""
        return [d for _at, d, waiting in self.sleeps if not waiting]


@pytest.fixture
def cam(make_camera_device_client):
    return make_camera_device_client("A000088")


def _opened_after(run: _Run) -> list[float]:
    """Each open's time since the one before it."""
    return [b - a for a, b in zip(run.opens, run.opens[1:])]


def test_sessions_that_keep_dying_fast_wait_longer_each_time(monkeypatch, cam):
    run = _Run(monkeypatch, cam, [_Sess(5.0) for _ in range(7)]).run()
    assert run.gaps() == [30.0, 60.0, 120.0, 240.0, 300.0, 300.0, 300.0]


def test_the_first_wait_is_30s_even_at_the_bottom_of_the_jitter(monkeypatch, cam):
    run = _Run(monkeypatch, cam, [_Sess(5.0), _Sess(5.0), _Sess(5.0)])
    monkeypatch.setattr(protocol, "random", types.SimpleNamespace(random=lambda: 0.0))
    run.run()
    assert run.gaps() == [30.0, 30.0, 30.0]


def test_a_session_that_lasted_reopens_at_once_and_clears_the_count(monkeypatch, cam):
    script = [_Sess(5.0), _Sess(5.0), _Sess(120.0), _Sess(5.0)]
    run = _Run(monkeypatch, cam, script).run()
    # The count started again: the short session after it waits 30 s, not 120.
    assert run.gaps() == [30.0, 60.0, 0.0, 30.0]


def test_a_video_less_session_is_short_however_long_it_ran(monkeypatch, cam):
    run = _Run(monkeypatch, cam, [_Sess(600.0, video=False), _Sess(5.0)]).run()
    assert run.gaps() == [30.0, 60.0]


def test_an_idle_release_is_never_paced(monkeypatch, cam, caplog):
    """The idle release returns from the loop before the session-end pacing:
    a session nobody watched is not a short session however briefly it ran."""
    with caplog.at_level("INFO", logger="aidot_cameras.camera.client"):
        run = _Run(monkeypatch, cam, [_Sess(5.0, idle=True), _Sess(5.0)]).run()
    assert run.dormant == ["idle release"]
    assert len(run.opens) == 1
    assert not [s for s in run.sleeps if s[2]]
    assert not [r for r in caplog.records if "before reopening" in r.getMessage()]


def test_open_failures_before_a_session_do_not_survive_it(monkeypatch, cam):
    """A successful open resets the open-failure pacer, as on main: four
    failures, a short session, then a failure retries on the 15 s base and
    is not slow-probed."""
    fails = [AidotNegotiationFailed("bad answer") for _ in range(4)]
    run = _Run(monkeypatch, cam, [*fails, _Sess(5.0), AidotNegotiationFailed("x")])
    run.run()
    assert run.open_sleeps()[:4] == [15.0, 30.0, 60.0, 120.0]
    assert run.open_sleeps()[-1] == 15.0


def test_short_sessions_never_push_a_camera_into_the_slow_probe(monkeypatch, cam):
    script = [_Sess(5.0) for _ in range(8)] + [AidotNegotiationFailed("x")]
    run = _Run(monkeypatch, cam, script).run()
    assert run.open_sleeps()[-1] == 15.0


def test_the_wait_does_not_hold_the_stream_slot(monkeypatch, cam):
    slots = asyncio.Semaphore(1)
    monkeypatch.setattr(cc, "_get_stream_slots", lambda: slots)
    run = _Run(monkeypatch, cam, [_Sess(5.0), _Sess(5.0), _Sess(5.0)], slots=slots)
    run.run(entry="_dtls_serve_loop")
    assert run.slot_held_in_serve == [True, True, True]
    assert run.slot_free_in_wait and all(run.slot_free_in_wait)
    # Taken back before each open and released once at the end - never twice,
    # which would silently raise the cap.
    assert not slots.locked() and slots._value == 1
    assert cam._dtls_stream_slot is None


def test_the_wait_takes_the_slot_back_before_reopening(monkeypatch, cam):
    """Another camera that took the slot during the wait is waited for."""
    slots = asyncio.Semaphore(1)
    monkeypatch.setattr(cc, "_get_stream_slots", lambda: slots)
    other: dict = {}

    def _other_camera_takes_the_slot(run):
        if run._waiting and "task" not in other:

            async def _hold():
                await slots.acquire()
                other["got"] = len(run.opens)
                # Longer than the rest of the wait, which ticks once a chunk.
                for _ in range(50):
                    await _real_sleep(0)
                other["freed"] = len(run.opens)
                slots.release()

            other["task"] = asyncio.ensure_future(_hold())

    run = _Run(
        monkeypatch,
        cam,
        [_Sess(5.0), _Sess(5.0)],
        slots=slots,
        on_sleep=_other_camera_takes_the_slot,
    )
    run.run(entry="_dtls_serve_loop")
    # It got the slot during the wait and freed it before the second open.
    assert other["got"] == 1 and other["freed"] == 1
    assert len(run.opens) == 3
    assert run.slot_held_in_serve == [True, True]
    assert slots._value == 1


def test_the_wait_floor_survives_an_offline_pause_that_ends_early(monkeypatch, cam):
    """Cloud-offline at session end, back online 5 s into the pause: the
    pause returns, and the loop still waits out the rest of the 30 s."""
    monkeypatch.setattr(cc, "_OFFLINE_RECHECK_S", 5.0)
    cam.status.online = False
    cam._cloud_online_explicit = True

    def _back_online(run):
        if run._waiting:
            cam.status.online = True

    run = _Run(monkeypatch, cam, [_Sess(5.0), _Sess(5.0)], on_sleep=_back_online)
    run.run()
    assert run.gaps() == [30.0, 60.0]


def test_an_ice_failure_waits_out_the_busy_interval(monkeypatch, cam):
    ice = AidotIceNotEstablished("ICE connection not established")
    run = _Run(monkeypatch, cam, [ice, ice]).run()
    assert _opened_after(run)[0] >= _BUSY


def test_another_negotiation_failure_keeps_the_ordinary_backoff(monkeypatch, cam):
    bad = AidotNegotiationFailed("bad answer")
    run = _Run(monkeypatch, cam, [bad, bad]).run()
    assert _opened_after(run)[0] == 15.0 < _BUSY


def test_the_ice_floor_survives_an_offline_pause_that_ends_early(monkeypatch, cam):
    """The offline pause would hold for AIDOT_OFFLINE_PROBE_S, but returns
    the moment the camera is back online; the busy floor still holds."""
    monkeypatch.setattr(cc, "_OFFLINE_RECHECK_S", 5.0)
    cam.status.online = False
    cam._cloud_online_explicit = True

    def _back_online(run):
        cam.status.online = True

    ice = AidotIceNotEstablished("ICE connection not established")
    run = _Run(monkeypatch, cam, [ice, ice], on_sleep=_back_online).run()
    assert _opened_after(run)[0] == _BUSY


def test_a_shifted_answer_that_did_not_connect_waits_out_the_busy_interval(
    monkeypatch, cam, caplog
):
    with caplog.at_level("INFO", logger="aidot_cameras.camera.client"):
        run = _Run(monkeypatch, cam, [_held_not_ready(), _held_not_ready()]).run()
    assert _opened_after(run)[0] >= _BUSY
    assert not [r for r in caplog.records if "[burst]" in r.getMessage()]


def test_a_genuine_not_ready_still_bursts(monkeypatch, cam):
    nr = AidotCameraNotReady("cam")
    run = _Run(monkeypatch, cam, [nr, nr]).run()
    assert _opened_after(run)[0] == 3.0


def test_the_shifted_answer_ice_timeout_is_marked_as_holding_the_session():
    """webrtc_open raises the NotReady for a shifted answer that did not
    connect with holds_session set; the loop keys its floor on that."""
    from aidot_cameras.camera import webrtc_open

    src = inspect.getsource(webrtc_open._dtls_open_ice_candidates_and_connect)
    tree = ast.parse(textwrap.dedent(src))
    marks = [
        n
        for n in ast.walk(tree)
        if isinstance(n, ast.Assign)
        and any(
            isinstance(t, ast.Attribute) and t.attr == "holds_session"
            for t in n.targets
        )
    ]
    assert marks and all(
        isinstance(m.value, ast.Constant) and m.value.value is True for m in marks
    )


# --------------------------------------------------------------------------- #
# A session whose last serve cycle had no video, through the real serve cycle  #
# --------------------------------------------------------------------------- #


@pytest.mark.parametrize("first_cycle_video", [True, False])
def test_a_lasting_session_is_judged_on_all_its_cycles(
    monkeypatch, make_camera_device_client, caplog, first_cycle_video
):
    """Its first serve cycle delivers video and its second gives up on none:
    the session delivered video and lasted, so it is not short. A session
    with no video in either cycle is. The futile-video count still counts
    the video-less cycle either way."""
    from tests.keepalive_driver import DtlsServeRun, FakeDtlsSession

    # The driver patches cc.asyncio.sleep; rebinding cc.asyncio first keeps
    # that on a proxy of the client module's asyncio, not the global one.
    proxy = types.SimpleNamespace(
        **{k: getattr(asyncio, k) for k in dir(asyncio) if not k.startswith("_")}
    )
    monkeypatch.setattr(cc, "asyncio", proxy)
    monkeypatch.setenv("AIDOT_DIRECT_PUBLISH", "1")
    monkeypatch.setenv("AIDOT_DTLS_VIDEO_GRACE_S", "0.15")
    monkeypatch.setattr(cc, "_DTLS_SHORT_SESSION_S", 0.2)
    cam = make_camera_device_client("A000088")
    cam._stream_idle_opt = 0.0
    run = DtlsServeRun(
        monkeypatch,
        cam,
        [FakeDtlsSession()],
        url="rtsp://127.0.0.1:8554/cam",
        timeout=1.5,
        publish_for=0.3,
    )
    monkeypatch.setattr(
        cc,
        "_video_has_started",
        lambda canary: first_cycle_video and len(run.mux_targets) <= 1,
    )
    with caplog.at_level("INFO", logger="aidot_cameras.camera.client"):
        run.run()
    msgs = [r.getMessage() for r in caplog.records]
    assert any("delivered no video" in m for m in msgs), msgs
    paced = [m for m in msgs if "before reopening" in m]
    if first_cycle_video:
        assert len(run.mux_targets) == 2
        assert not paced, paced
        assert run.opens == 2  # reopened at once, then the script ran out
    else:
        assert paced, msgs


def test_sessions_are_paced_once_at_session_end_not_per_serve_cycle():
    """Pacing inside the serve cycle would sleep per ffmpeg restart, holding a
    live session; it belongs in the loop, after the session is stopped."""

    def _calls_in(fn):
        tree = ast.parse(textwrap.dedent(inspect.getsource(fn)))
        return {
            n.func.id
            for n in ast.walk(tree)
            if isinstance(n, ast.Call) and isinstance(n.func, ast.Name)
        }

    assert "_session_end_pacing" in _calls_in(CameraMixin._dtls_serve_loop_inner)
    assert "_session_end_pacing" not in _calls_in(cc._dtls_serve_loop_serve_cycle)
