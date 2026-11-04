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
  last serve cycle ended, and so does a camera that took over 300 s to open
  again;
* open failures are paced exactly as before: a successful open resets them,
  and short sessions never push a camera into the slow probe;
* while it waits the loop keeps its concurrent-stream slot (as a failed-open
  wait does), does not claim to be serving, renews no battery keep-alive, and
  afterwards releases the camera if nobody has watched for the idle window,
  the waits themselves not counted;
* a video-less session is paced once, as a short session, and does not also
  escalate the open-failure pacer;
* an open whose ICE never connected, on a plain or a shifted answer, is one
  failure with one policy: the pacer, the slow probe, the offline pause and a
  floor of AIDOT_BUSY_RETRY_S that an offline pause ending early cannot cut.

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

import asyncio
import socket
import time as _real_time
import types

import pytest

from aidot_cameras import settings
from aidot_cameras.camera import client as cc
from aidot_cameras.camera import protocol
from aidot_cameras.camera.client import _session_end_pacing, _short_session_delay
from aidot_cameras.exceptions import (
    AidotCameraNotReady,
    AidotIceNotEstablished,
    AidotNegotiationFailed,
    AidotShiftedAnswerNotConnected,
)

_real_sleep = asyncio.sleep
_URL = "http://127.0.0.1:18555/cam.ts"
_BUSY = settings.read("AIDOT_BUSY_RETRY_S")


# --------------------------------------------------------------------------- #
# The pure pacing                                                              #
# --------------------------------------------------------------------------- #


def _pace(n, lived, *, video=True, down=0.0, r=1.0):
    return _session_end_pacing(
        n, lived, had_video=video, threshold_s=30.0, down_s=down, rand=lambda: r
    )


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
    assert _pace(4, 120.0) == (0, 0.0)


def test_a_short_or_video_less_session_adds_one_and_waits():
    assert _pace(0, 5.0) == (1, 30.0)
    assert _pace(1, 5.0) == (2, 60.0)
    assert _pace(0, 600.0, video=False) == (1, 30.0)


def test_downtime_past_the_cap_starts_the_count_again():
    assert _pace(4, 5.0, down=301.0) == (1, 30.0)
    assert _pace(4, 5.0, down=300.0) == (5, 300.0)


def test_the_shifted_answer_failure_is_both_kinds():
    """Callers that caught AidotCameraNotReady (what this case raised) or
    AidotNegotiationFailed keep catching it."""
    exc = AidotShiftedAnswerNotConnected("x")
    assert isinstance(exc, AidotIceNotEstablished)
    assert isinstance(exc, AidotNegotiationFailed)
    assert isinstance(exc, AidotCameraNotReady)


# --------------------------------------------------------------------------- #
# The ICE wait raises one type per outcome                                     #
# --------------------------------------------------------------------------- #


class _NeverConnects:
    connectionState = "connecting"
    iceConnectionState = "checking"
    iceGatheringState = "complete"

    def on(self, _event):
        return lambda fn: fn

    def getTransceivers(self):
        return []


def _ice_wait(shifted: bool) -> BaseException:
    from aidot_cameras.camera import webrtc_open

    async def _abort():
        return None

    ps = types.SimpleNamespace(
        pc=_NeverConnects(),
        _status=lambda *a, **k: None,
        _trace=lambda *a, **k: None,
        timeout=0.3,
        _ice_wait_timeout_s=0.3,
        _answer_inserted=shifted,
        ice_q=asyncio.Queue(),
        candidate_from_sdp=None,
        outgoing_q=asyncio.Queue(),
        user_id="u",
        camera_reconnect_ev=asyncio.Event(),
        _rr_ice_payloads=[],
        _rr_webrtc_resp_topic=None,
        _rr_webrtc_resp_payload=None,
        _abort_open=_abort,
        recorder=None,
    )
    cam = types.SimpleNamespace(device_id="cam")

    async def _go():
        ps.second_answer_fut = asyncio.get_running_loop().create_future()
        await webrtc_open._dtls_open_ice_candidates_and_connect(cam, ps)

    with pytest.raises(AidotIceNotEstablished) as info:
        asyncio.run(_go())
    return info.value


def test_a_shifted_answer_that_never_connects_raises_the_shifted_failure():
    exc = _ice_wait(shifted=True)
    assert isinstance(exc, AidotShiftedAnswerNotConnected)
    assert isinstance(exc, AidotCameraNotReady)


def test_a_plain_answer_that_never_connects_is_not_a_not_ready():
    exc = _ice_wait(shifted=False)
    assert not isinstance(exc, AidotCameraNotReady)


# --------------------------------------------------------------------------- #
# The loop, on a fake clock                                                    #
# --------------------------------------------------------------------------- #


class _Sess:
    """A session the fake serve cycle keeps for ``life`` fake seconds."""

    def __init__(
        self,
        life: float,
        *,
        video: bool = True,
        idle: bool = False,
        watched: bool = False,
    ):
        self.life = life
        self.video = video
        self.idle = idle
        self.watched = watched
        self._pc = types.SimpleNamespace(connectionState="connected")
        self.stopped = 0

    async def stop(self) -> None:
        self.stopped += 1


class _Relay:
    def __init__(self):
        self.paused = False

    def set_paused(self, paused: bool) -> None:
        self.paused = paused

    def close(self) -> None:
        pass


class _Run:
    """One run of the serve loop against a script of open outcomes."""

    def __init__(
        self,
        monkeypatch,
        cam,
        script,
        *,
        slots=None,
        on_sleep=None,
        viewer=True,
        relay=None,
        renew=False,
    ):
        self.cam = cam
        self.script = list(script)
        self.now = 1000.0
        self.opens: list[float] = []
        self.ends: list[float] = []
        self.sleeps: list[tuple[float, float, bool]] = []  # (at, delay, waiting)
        self.slot_held_in_wait: list[bool] = []
        self.slot_held_in_serve: list[bool] = []
        self.ready_in_wait: list[bool] = []
        self.relay_in_wait: list[tuple[float, bool]] = []  # (since end, paused)
        self.renewals: list[tuple[float, bool]] = []  # (at, waiting)
        self.dormant: list[str] = []
        self.slots = slots
        self.relay = relay
        self._waiting = False
        self._on_sleep = on_sleep
        self._renew_task = None
        if renew:
            monkeypatch.setattr(
                type(cam), "is_battery_camera", property(lambda _s: True)
            )

            async def _keep_alive():
                run.renewals.append((run.now, run._waiting))

            cam._async_set_keep_alive = _keep_alive

        cam._streaming_active = True
        cam._keepalive_rtsp_url = _URL
        cam._serve_relay = relay

        run = self

        async def _sleep(delay, *a, **k):
            if run._renew_task is not None and asyncio.current_task() is (
                run._renew_task
            ):
                # The renew loop runs alongside on the same clock: it sleeps
                # until the loop has moved the clock on by ``delay``.
                target = run.now + delay
                for _ in range(100000):
                    if run.now >= target:
                        return
                    await _real_sleep(0)
                raise asyncio.CancelledError
            run.sleeps.append((run.now, delay, run._waiting))
            if len(run.sleeps) > 5000:
                raise asyncio.CancelledError
            if run._waiting:
                run.ready_in_wait.append(cam._serve_ready.is_set())
                if run.slots is not None:
                    run.slot_held_in_wait.append(run.slots.locked())
                if run.relay is not None:
                    run.relay_in_wait.append((run.now - run.ends[-1], run.relay.paused))
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
            cam._serve_ready.set()  # what a bound serve does
            if sess.watched:
                ps._last_known_viewer = run.now - ps._waited_s
            # In steps, so the renew loop running alongside sees the time pass.
            for _ in range(int(sess.life)):
                run.now += 1.0
                await _real_sleep(0)
            run.now += sess.life - int(sess.life)
            ps._session_had_video = sess.video
            ps.idle_release = sess.idle
            await sess.stop()
            run.ends.append(run.now)
            run._waiting = True

        async def _go_dormant(*, release_url, why):
            run.dormant.append(why)
            cam._streaming_active = False

        async def _viewer_present(port):
            return viewer(run.now) if callable(viewer) else viewer

        cam.async_open_webrtc_stream = _open
        cam._install_av_taps = lambda pc, vq, aq: True
        cam._maybe_start_serve_relay = lambda url: relay
        cam._go_dormant = _go_dormant
        cam._viewer_present = _viewer_present
        monkeypatch.setattr(cc, "_dtls_serve_loop_serve_cycle", _serve)

    def run(self, entry: str = "_dtls_serve_loop_inner") -> "_Run":
        async def _go():
            if self.cam.is_battery_camera:
                self._renew_task = asyncio.ensure_future(
                    self.cam._keepalive_renew_loop()
                )
            try:
                await getattr(self.cam, entry)()
            finally:
                if self._renew_task is not None:
                    self._renew_task.cancel()
                    await asyncio.gather(self._renew_task, return_exceptions=True)

        asyncio.run(_go())
        return self

    def gaps(self) -> list[float]:
        """Seconds from each session's end to the next open."""
        out = []
        for end in self.ends:
            nxt = [t for t in self.opens if t >= end]
            if nxt:
                out.append(round(nxt[0] - end, 6))
        return out

    def open_sleeps(self) -> list[float]:
        """The delays slept outside a session-end wait (failed-open retries)."""
        return [d for _at, d, waiting in self.sleeps if not waiting]


@pytest.fixture
def cam(make_camera_device_client):
    return make_camera_device_client("A000088")


def _opened_after(run: _Run) -> list[float]:
    """Each open's time since the one before it."""
    return [round(b - a, 6) for a, b in zip(run.opens, run.opens[1:])]


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


@pytest.mark.parametrize("threshold, gaps", [(None, [0.0, 30.0]), ("90", [30.0, 60.0])])
def test_the_threshold_knob_is_read_at_each_session_end(
    monkeypatch, cam, threshold, gaps
):
    """A 60 s session with video lasted at the default 30 s threshold, and is
    short at 90 s - set after import, so the loop must read it at use."""
    if threshold is not None:
        monkeypatch.setenv("AIDOT_DTLS_SHORT_SESSION_S", threshold)
    run = _Run(monkeypatch, cam, [_Sess(60.0), _Sess(5.0)]).run()
    assert run.gaps() == gaps


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


@pytest.mark.parametrize("fails, wait", [(1, 300.0), (5, 30.0)])
def test_a_camera_down_past_the_cap_starts_its_short_count_again(
    monkeypatch, cam, fails, wait
):
    """Four short sessions, then failed opens, then a short session. One
    failed open (15 s) leaves the run going: it waits 300 s. Five (15 + 30 +
    60 + 120 s, then the 600 s slow probe) is a camera that was down: the
    count starts again and it waits 30 s."""
    script = [_Sess(5.0) for _ in range(4)]
    script += [AidotNegotiationFailed("down") for _ in range(fails)]
    script += [_Sess(5.0)]
    run = _Run(monkeypatch, cam, script).run()
    assert run.gaps()[:4] == [30.0, 60.0, 120.0, 240.0]
    assert run.gaps()[-1] == wait


def test_the_wait_keeps_the_stream_slot(monkeypatch, cam):
    """As a failed-open wait always has: letting go of the slot to wait and
    queueing for it again could park a camera in the queue indefinitely."""
    slots = asyncio.Semaphore(1)
    monkeypatch.setattr(cc, "_get_stream_slots", lambda: slots)
    run = _Run(monkeypatch, cam, [_Sess(5.0), _Sess(5.0), _Sess(5.0)], slots=slots)
    run.run(entry="_dtls_serve_loop")
    assert run.slot_held_in_serve == [True, True, True]
    assert run.slot_held_in_wait and all(run.slot_held_in_wait)
    assert not slots.locked() and slots._value == 1


def test_the_wait_does_not_claim_to_be_serving(monkeypatch, cam):
    """serve_ready is down for the whole wait. The relay closes pulls for
    all but the last 30 s of it, then holds them for the reopen, and is
    serving normally again when the next open starts. Waits: 30, 60, 120 s."""
    relay = _Relay()
    script = [_Sess(5.0), _Sess(5.0), _Sess(5.0)]
    run = _Run(monkeypatch, cam, script, relay=relay).run()
    assert run.ready_in_wait and not any(run.ready_in_wait)
    assert run.gaps() == [30.0, 60.0, 120.0]
    paused = {round(t) for t, p in run.relay_in_wait if p}
    held = {round(t) for t, p in run.relay_in_wait if not p}
    assert paused and max(paused) < 90.0
    assert {90, 95, 100, 105, 110, 115} <= held
    assert relay.paused is False


def test_a_paused_relay_closes_a_pull_at_once():
    relay = protocol._ServeRelay(0, dial_timeout=5.0)
    relay.start()
    try:
        relay.set_paused(True)
        with socket.create_connection(("127.0.0.1", relay.port), timeout=2) as c:
            c.settimeout(2.0)
            t0 = _real_time.monotonic()
            assert c.recv(1) == b""
            assert _real_time.monotonic() - t0 < 1.5
        relay.set_paused(False)
        with socket.create_connection(("127.0.0.1", relay.port), timeout=2) as c:
            # Unpaused with no backend: held, waiting for one.
            c.settimeout(0.5)
            with pytest.raises(TimeoutError):
                c.recv(1)
    finally:
        relay.close()


def test_nobody_watching_after_the_wait_releases_the_camera(monkeypatch, cam):
    """Sessions that die in seconds never reach the serve cycle's idle
    release; after each wait the loop asks itself, counting only the time
    sessions ran. Idle window 60 s, 5 s sessions: the thirteenth session takes
    it past 60 s, and the loop releases instead of opening a fourteenth."""
    cam._stream_idle_opt = 60.0
    monkeypatch.setenv("AIDOT_STREAM_STARTUP_GRACE_S", "0")
    script = [_Sess(5.0) for _ in range(20)]
    run = _Run(monkeypatch, cam, script, viewer=False).run()
    assert run.dormant == ["idle release"]
    assert len(run.opens) == 13


def test_the_wait_never_counts_towards_the_idle_window(monkeypatch, cam):
    """A viewer was watching each session; during a wait go2rtc can report
    none (the relay closed its pulls). Waits of 30 to 300 s, idle window
    60 s: still not released."""
    cam._stream_idle_opt = 60.0
    monkeypatch.setenv("AIDOT_STREAM_STARTUP_GRACE_S", "0")
    script = [_Sess(5.0, watched=True) for _ in range(6)]
    run = _Run(monkeypatch, cam, script, viewer=False).run()
    assert not run.dormant
    assert len(run.opens) == 7


@pytest.mark.parametrize("idle, viewer", [(60.0, True), (0.0, False)])
def test_a_watched_or_never_released_camera_keeps_reopening(
    monkeypatch, cam, idle, viewer
):
    cam._stream_idle_opt = idle
    script = [_Sess(5.0) for _ in range(15)]
    run = _Run(monkeypatch, cam, script, viewer=viewer).run()
    assert not run.dormant
    assert len(run.opens) == 16


def test_no_battery_keep_alive_is_renewed_during_the_wait(monkeypatch, cam):
    """The renew loop keeps a battery camera awake while a session runs; in
    the wait there is no session, and waking it is what the wait stops. It
    renews again once a session is back."""
    script = [_Sess(25.0) for _ in range(4)]
    run = _Run(monkeypatch, cam, script, renew=True).run()
    assert run.gaps() == [30.0, 60.0, 120.0, 240.0]
    assert [at for at, waiting in run.renewals if waiting] == []
    during = [at for at, waiting in run.renewals if not waiting]
    assert any(at > run.ends[2] for at in during)


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


_ICE = AidotIceNotEstablished("ICE connection not established")
_SHIFTED = AidotShiftedAnswerNotConnected("ICE connection not established")


@pytest.mark.parametrize("exc", [_ICE, _SHIFTED], ids=["plain", "shifted"])
def test_an_ice_failure_has_the_busy_floor_and_escalates_to_the_slow_probe(
    monkeypatch, cam, exc
):
    """At least AIDOT_BUSY_RETRY_S each time, then the pacer's own backoff,
    then the slow probe after five failures - a shifted answer exactly as a
    plain one, not the flat retry or 3 s burst of a NotReady."""
    run = _Run(monkeypatch, cam, [exc] * 6).run()
    assert _opened_after(run) == [_BUSY, _BUSY, 60.0, 120.0, 600.0, 600.0]


def test_another_negotiation_failure_keeps_the_ordinary_backoff(monkeypatch, cam):
    bad = AidotNegotiationFailed("bad answer")
    run = _Run(monkeypatch, cam, [bad, bad]).run()
    assert _opened_after(run)[0] == 15.0 < _BUSY


@pytest.mark.parametrize("exc", [_ICE, _SHIFTED], ids=["plain", "shifted"])
def test_the_ice_floor_survives_an_offline_pause_that_ends_early(monkeypatch, cam, exc):
    """The offline pause would hold for AIDOT_OFFLINE_PROBE_S, but returns
    the moment the camera is back online; the busy floor still holds."""
    monkeypatch.setattr(cc, "_OFFLINE_RECHECK_S", 5.0)
    cam.status.online = False
    cam._cloud_online_explicit = True

    def _back_online(run):
        cam.status.online = True

    run = _Run(monkeypatch, cam, [exc, exc], on_sleep=_back_online).run()
    assert _opened_after(run)[0] == _BUSY


def test_shifted_failures_do_not_use_up_the_not_ready_burst(monkeypatch, cam):
    nr = AidotCameraNotReady("cam")
    run = _Run(monkeypatch, cam, [_SHIFTED] * 5 + [nr, nr]).run()
    assert _opened_after(run)[-1] == 3.0


def test_a_genuine_not_ready_still_bursts(monkeypatch, cam):
    nr = AidotCameraNotReady("cam")
    run = _Run(monkeypatch, cam, [nr, nr]).run()
    assert _opened_after(run)[0] == 3.0


# --------------------------------------------------------------------------- #
# Through the real serve cycle                                                 #
# --------------------------------------------------------------------------- #


def _real_cycle_run(monkeypatch, make_camera_device_client, first_cycle_video):
    """One session of two serve cycles: the first delivers video if
    ``first_cycle_video``, the second gives up on none."""
    from tests.keepalive_driver import DtlsServeRun, FakeDtlsSession

    # The driver patches cc.asyncio.sleep; rebinding cc.asyncio first keeps
    # that on a proxy of the client module's asyncio, not the global one.
    proxy = types.SimpleNamespace(
        **{k: getattr(asyncio, k) for k in dir(asyncio) if not k.startswith("_")}
    )
    monkeypatch.setattr(cc, "asyncio", proxy)
    monkeypatch.setenv("AIDOT_DIRECT_PUBLISH", "1")
    monkeypatch.setenv("AIDOT_DTLS_VIDEO_GRACE_S", "0.15")
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
    return run


@pytest.mark.parametrize("first_cycle_video", [True, False])
def test_a_lasting_session_is_judged_on_all_its_cycles(
    monkeypatch, make_camera_device_client, caplog, first_cycle_video
):
    """Its first serve cycle delivers video and its second gives up on none:
    the session delivered video and lasted, so it is not short. A session
    with no video in either cycle is. The futile-video count still counts
    the video-less cycle either way."""
    monkeypatch.setenv("AIDOT_DTLS_SHORT_SESSION_S", "0.2")
    run = _real_cycle_run(monkeypatch, make_camera_device_client, first_cycle_video)
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


def test_a_session_of_several_cycles_is_paced_once_after_it_ends(
    monkeypatch, make_camera_device_client, caplog
):
    """Two serve cycles (an ffmpeg/publish restart between them) in one short
    session: one wait, after the session, not one per cycle."""
    monkeypatch.setenv("AIDOT_DTLS_SHORT_SESSION_S", "100")
    run = _real_cycle_run(monkeypatch, make_camera_device_client, True)
    with caplog.at_level("INFO", logger="aidot_cameras.camera.client"):
        run.run()
    msgs = [r.getMessage() for r in caplog.records]
    assert len(run.mux_targets) == 2
    assert len([m for m in msgs if "before reopening" in m]) == 1, msgs
    assert run.opens == 1  # still waiting when the run timed out


def test_a_video_less_session_does_not_also_escalate_the_open_pacer(
    monkeypatch, make_camera_device_client, caplog
):
    """Paced once, as a short session. The open that fails after it retries
    on the pacer's 15 s base, as after any successful open, not 30 s: the
    video-less session no longer counts as an open failure too."""
    run = _real_cycle_run(monkeypatch, make_camera_device_client, False)
    # A short-session wait of a few ms of real time instead of 30 s.
    monkeypatch.setattr(cc, "_SHORT_SESSION_BASE_S", 0.01)
    monkeypatch.setattr(cc, "_SHORT_SESSION_CAP_S", 0.02)
    from tests.keepalive_driver import FakeDtlsSession

    outcomes = [FakeDtlsSession(), AidotNegotiationFailed("bad answer")]

    async def _open(**kwargs):
        run.opens += 1
        if not outcomes:
            raise asyncio.CancelledError
        out = outcomes.pop(0)
        if isinstance(out, BaseException):
            raise out
        return out

    run.client.async_open_webrtc_stream = _open
    with caplog.at_level("INFO", logger="aidot_cameras.camera.client"):
        run.run()
    msgs = [r.getMessage() for r in caplog.records]
    assert any("delivered no video" in m for m in msgs), msgs
    retries = [m for m in msgs if "open failed for" in m]
    assert retries and "(retry 15s)" in retries[0], retries
