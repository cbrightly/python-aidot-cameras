"""The DTLS serve loop backs off a camera whose sessions keep failing fast.

Measured 2026-11-04 on an A000088: for half an hour its sessions died within
seconds of opening, the next open came on the bare 15 s gate each time (an
open that connects resets the pacer), then opens stopped connecting at all
("ICE connection not established"), and every retry 15 s after one of those
was refused as busy (-50002) - the camera still held the session it had
accepted for the offer that never connected. Then it went offline.

Pinned here:

* a session that ends short (or never delivers video) is paced on its own
  pacer, apart from the open-failure pacer: the loop waits 30 s after the
  first, then jittered in [30, 45] s - never less than 30 s, never more than
  45 s, so a pull the serve relay holds (90 s) is still there for the reopen;
* a session that lasted and delivered video resets that pacer, however its
  last serve cycle ended;
* open failures are paced exactly as before: a successful open resets them,
  and short sessions never push a camera into the slow probe;
* while it waits the loop keeps its concurrent-stream slot (as a failed-open
  wait does), does not claim to be serving, keeps renewing a battery
  camera's keep-alive, and returns quietly when cancelled, as its other
  waits do; it never releases the camera after the wait - idle release is
  the serve cycle's alone;
* a video-less session is paced once, as a short session, and does not also
  escalate the open-failure pacer;
* an open whose ICE never connected is paced like any failed open (backoff,
  slow probe, offline pause) with a floor of AIDOT_BUSY_RETRY_S that no early
  return cuts; a shifted answer that did not connect keeps main's 3 s burst;
* a session's life is measured to when it stopped serving, not past its
  teardown.

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
import time as _real_time
import types

import pytest

from aidot_cameras import settings
from aidot_cameras.camera import client as cc
from aidot_cameras.camera import protocol
from aidot_cameras.camera.client import _session_end_pacing
from aidot_cameras.camera.client import _video_has_started as _real_video_has_started
from aidot_cameras.camera.protocol import ReconnectPacer
from aidot_cameras.exceptions import (
    AidotCameraBusy,
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


def _pacer(r=1.0):
    return ReconnectPacer(30.0, 45.0, rand=lambda: r)


def _pace(pacer, lived, *, video=True):
    return _session_end_pacing(pacer, lived, had_video=video, threshold_s=30.0)


@pytest.mark.parametrize("r, waits", [(0.0, [30.0] * 6), (1.0, [30.0] + [45.0] * 5)])
def test_the_short_session_wait_is_30_then_at_most_45(r, waits):
    """Exactly 30 s after the first at either end of the jitter; after that
    within [30, 45] s, never outside it."""
    p = _pacer(r)
    assert [_pace(p, 5.0) for _ in range(6)] == waits


def test_a_session_that_lasted_with_video_resets_the_pacer():
    p = _pacer()
    _pace(p, 5.0)
    _pace(p, 5.0)
    assert _pace(p, 120.0) == 0.0
    assert p.attempt == 0
    assert _pace(p, 5.0) == 30.0


def test_a_video_less_session_is_short_however_long_it_ran():
    assert _pace(_pacer(), 600.0, video=False) == 30.0


def test_the_ice_failure_is_still_a_negotiation_failure():
    """Callers that catch AidotNegotiationFailed keep catching it."""
    assert issubclass(AidotIceNotEstablished, AidotNegotiationFailed)


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

    with pytest.raises((AidotIceNotEstablished, AidotCameraNotReady)) as info:
        asyncio.run(_go())
    return info.value


def test_a_plain_answer_that_never_connects_is_an_ice_failure():
    exc = _ice_wait(shifted=False)
    assert isinstance(exc, AidotIceNotEstablished)


def test_a_shifted_answer_that_never_connects_is_still_a_not_ready():
    """As on main: a shifted answer still connects about 1 in 7 on a quick
    retry, so it keeps the 3 s burst rather than the busy floor."""
    exc = _ice_wait(shifted=True)
    assert isinstance(exc, AidotCameraNotReady)
    assert not isinstance(exc, AidotIceNotEstablished)


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
    ):
        self.life = life
        self.video = video
        self.idle = idle
        self._pc = types.SimpleNamespace(connectionState="connected")
        self.stopped = 0

    async def stop(self) -> None:
        self.stopped += 1


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
        self.renewals: list[tuple[float, bool]] = []  # (at, waiting)
        self.dormant: list[str] = []
        self.slots = slots
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
        cam._serve_relay = None

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
            # In steps, so the renew loop running alongside sees the time pass.
            for _ in range(int(sess.life)):
                run.now += 1.0
                await _real_sleep(0)
            run.now += sess.life - int(sess.life)
            ps._session_ended_at = run.now  # what the real cycle records
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
        cam._maybe_start_serve_relay = lambda url: None
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


def test_sessions_that_keep_dying_fast_wait_30_then_never_more_than_45(
    monkeypatch, cam
):
    """At the top of the jitter: 30 s, then 45 s for as long as it goes on.
    45 s + a 16-25 s cold open + ~8 s to serve-ready stays inside the 90 s a
    pull is held by the serve relay."""
    run = _Run(monkeypatch, cam, [_Sess(5.0) for _ in range(8)]).run()
    assert run.gaps() == [30.0] + [45.0] * 7


def test_the_first_wait_is_30s_even_at_the_bottom_of_the_jitter(monkeypatch, cam):
    run = _Run(monkeypatch, cam, [_Sess(5.0), _Sess(5.0), _Sess(5.0)])
    monkeypatch.setattr(protocol, "random", types.SimpleNamespace(random=lambda: 0.0))
    run.run()
    assert run.gaps() == [30.0, 30.0, 30.0]


def test_a_session_that_lasted_reopens_at_once_and_resets_the_pacer(monkeypatch, cam):
    script = [_Sess(5.0), _Sess(5.0), _Sess(120.0), _Sess(5.0)]
    run = _Run(monkeypatch, cam, script).run()
    # The pacer started again: the short session after it waits 30 s, not 45.
    assert run.gaps() == [30.0, 45.0, 0.0, 30.0]


def test_a_video_less_session_is_short_however_long_it_ran(monkeypatch, cam):
    run = _Run(monkeypatch, cam, [_Sess(600.0, video=False), _Sess(5.0)]).run()
    assert run.gaps() == [30.0, 45.0]


@pytest.mark.parametrize("threshold, gaps", [(None, [0.0, 30.0]), ("90", [30.0, 45.0])])
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
    assert _opened_after(run)[:4] == [15.0, 30.0, 60.0, 120.0]
    assert _opened_after(run)[-1] == 15.0


def test_short_sessions_never_push_a_camera_into_the_slow_probe(monkeypatch, cam):
    script = [_Sess(5.0) for _ in range(8)] + [AidotNegotiationFailed("x")]
    run = _Run(monkeypatch, cam, script).run()
    assert _opened_after(run)[-1] == 15.0


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
    """serve_ready is down for the whole wait; the next serve sets it."""
    run = _Run(monkeypatch, cam, [_Sess(5.0), _Sess(5.0), _Sess(5.0)]).run()
    assert run.gaps() == [30.0, 45.0, 45.0]
    assert run.ready_in_wait and not any(run.ready_in_wait)


@pytest.mark.parametrize("viewer", [True, False, None])
def test_the_wait_never_releases_the_camera(monkeypatch, cam, viewer):
    """Idle release is the serve cycle's alone. With the integration's 15 s
    battery window, shorter than any wait, a check after the wait would
    release a camera someone is watching; so there is none, whatever the
    viewer state. The cost, accepted: a camera nobody watches whose sessions
    keep dying fast is reopened every 30-45 s plus its session time until
    its keepalive is stopped."""
    cam._stream_idle_opt = 15.0
    asked: list = []
    run = _Run(
        monkeypatch,
        cam,
        [_Sess(5.0) for _ in range(6)],
        viewer=lambda now: asked.append(now) or viewer,
    ).run()
    assert not run.dormant
    assert len(run.opens) == 7
    assert asked == []


def test_battery_keep_alive_renewals_carry_on_through_the_wait(monkeypatch, cam):
    """Letting the camera's ~25 s low-power timer run out in a 30-45 s wait
    would turn every reopen into a slow cold wake: the renew loop keeps
    renewing while the loop waits."""
    script = [_Sess(25.0) for _ in range(4)]
    run = _Run(monkeypatch, cam, script, renew=True).run()
    assert run.gaps() == [30.0, 45.0, 45.0, 45.0]
    assert [at for at, waiting in run.renewals if waiting]


def test_the_wait_is_a_plain_wait_even_when_the_cloud_says_offline(monkeypatch, cam):
    """Offline handling belongs to the open path: the session-end wait is
    its 30 s, not the offline pause's 600 s."""
    cam.status.online = False
    cam._cloud_online_explicit = True
    run = _Run(monkeypatch, cam, [_Sess(5.0), _Sess(5.0)]).run()
    assert run.gaps() == [30.0, 45.0]


def test_a_cancellation_in_the_wait_ends_the_loop_quietly(monkeypatch, cam):
    """As the loop's other waits do: cancelled while it waits, the loop
    returns None rather than raising, and opens nothing more."""

    def _cancel_in_the_wait(run):
        if run._waiting:
            raise asyncio.CancelledError

    run = _Run(monkeypatch, cam, [_Sess(5.0), _Sess(5.0)], on_sleep=_cancel_in_the_wait)
    run.run()
    assert len(run.opens) == 1


_ICE = AidotIceNotEstablished("ICE connection not established")


def test_an_ice_failure_has_the_busy_floor_and_escalates_to_the_slow_probe(
    monkeypatch, cam
):
    """At least AIDOT_BUSY_RETRY_S each time, then the pacer's own backoff,
    then the slow probe after five failures."""
    run = _Run(monkeypatch, cam, [_ICE] * 6).run()
    assert _opened_after(run) == [_BUSY, _BUSY, 60.0, 120.0, 600.0, 600.0]


def test_the_busy_floor_holds_in_a_short_slow_probe_too(monkeypatch, cam):
    """At the bottom of the jitter every failed open waits 15 s; with the
    slow-probe interval set to 20 s, an ICE failure still waits 45 s."""
    monkeypatch.setattr(cc, "_SLOW_PROBE_INTERVAL_S", 20.0)
    run = _Run(monkeypatch, cam, [_ICE] * 8)
    monkeypatch.setattr(protocol, "random", types.SimpleNamespace(random=lambda: 0.0))
    run.run()
    assert _opened_after(run) == [_BUSY] * 8


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

    run = _Run(monkeypatch, cam, [_ICE, _ICE], on_sleep=_back_online).run()
    assert _opened_after(run)[0] == _BUSY


def test_the_ice_floor_survives_an_offline_probe_interval_shorter_than_it(
    monkeypatch, cam
):
    """Still offline when a 10 s AIDOT_OFFLINE_PROBE_S elapses: the pause
    falls through to a probe open, but not before the 45 s floor."""
    monkeypatch.setattr(cc, "_OFFLINE_PROBE_S", 10.0)
    cam.status.online = False
    cam._cloud_online_explicit = True
    run = _Run(monkeypatch, cam, [_ICE, _ICE]).run()
    assert _opened_after(run)[0] == _BUSY


def test_a_not_ready_still_bursts(monkeypatch, cam):
    """Including a shifted answer that did not connect, as on main."""
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
    # A canary is there to read (the driver's default is none); whether it
    # shows video is up to the test.
    monkeypatch.setattr(cc, "_live_video_canary", lambda pc, stored: {"keyframes": 0})
    monkeypatch.setattr(
        cc,
        "_video_has_started",
        lambda canary: first_cycle_video and len(run.mux_targets) <= 1,
    )
    return run


def _canary_run(monkeypatch, make_camera_device_client, canary, **kw):
    """A real-serve-cycle run whose session has ``canary`` (None: none)."""
    from tests.keepalive_driver import DtlsServeRun, FakeDtlsSession

    proxy = types.SimpleNamespace(
        **{k: getattr(asyncio, k) for k in dir(asyncio) if not k.startswith("_")}
    )
    monkeypatch.setattr(cc, "asyncio", proxy)
    cam = make_camera_device_client("A000088")
    cam._stream_idle_opt = 0.0
    run = DtlsServeRun(monkeypatch, cam, [FakeDtlsSession()], timeout=1.5, **kw)
    monkeypatch.setattr(cc, "_live_video_canary", lambda pc, stored: canary)
    monkeypatch.setattr(cc, "_video_has_started", _real_video_has_started)
    return run, cam


def _paced(caplog) -> bool:
    return any("before reopening" in r.getMessage() for r in caplog.records)


@pytest.mark.parametrize(
    "canary",
    [None, {"frames": 40, "keyframes": 0}],
    ids=["no canary", "canary without keyframes"],
)
def test_a_no_video_verdict_makes_a_session_short_canary_or_not(
    monkeypatch, make_camera_device_client, caplog, canary
):
    """The presence watchdog gives up after 0.15 s (threshold 0.1 s, so the
    session lasted). Its no-video verdict is final: with a canary showing no
    keyframe, and with no canary at all, the session is short."""
    monkeypatch.setenv("AIDOT_DTLS_VIDEO_GRACE_S", "0.15")
    monkeypatch.setenv("AIDOT_DTLS_SHORT_SESSION_S", "0.1")
    run, _cam = _canary_run(monkeypatch, make_camera_device_client, canary)
    with caplog.at_level("INFO", logger="aidot_cameras.camera.client"):
        run.run()
    msgs = [r.getMessage() for r in caplog.records]
    assert any("delivered no video" in m for m in msgs), msgs
    assert _paced(caplog), msgs


def test_a_session_with_no_canary_and_no_verdict_is_not_short(
    monkeypatch, make_camera_device_client, caplog
):
    """No canary to read and the watchdog off (grace 0), so nothing says the
    session had no video: it lasted 0.3 s against a 0.1 s threshold, and is
    not short for want of a canary."""
    monkeypatch.setenv("AIDOT_DTLS_VIDEO_GRACE_S", "0")
    monkeypatch.setenv("AIDOT_DTLS_SHORT_SESSION_S", "0.1")
    run, cam = _canary_run(monkeypatch, make_camera_device_client, None)
    _open = cam.async_open_webrtc_stream

    async def _open_and_close_later(**kwargs):
        out = await _open(**kwargs)
        asyncio.get_running_loop().call_later(
            0.3, setattr, out._pc, "connectionState", "closed"
        )
        return out

    cam.async_open_webrtc_stream = _open_and_close_later
    with caplog.at_level("INFO", logger="aidot_cameras.camera.client"):
        run.run()
    assert not _paced(caplog), [r.getMessage() for r in caplog.records]
    assert run.opens == 2


def test_a_cycle_that_ends_before_polling_does_not_flip_the_verdict(
    monkeypatch, make_camera_device_client, caplog
):
    """Cycle 1 sees a keyframe; the PC closes as cycle 2 starts, so cycle 2
    ends before it ever reads the canary. The session delivered video and
    lasted (0.3 s against 0.2 s): it is not short."""
    monkeypatch.setenv("AIDOT_DIRECT_PUBLISH", "1")
    monkeypatch.setenv("AIDOT_DTLS_SHORT_SESSION_S", "0.2")
    run, cam = _canary_run(
        monkeypatch,
        make_camera_device_client,
        {"frames": 30, "keyframes": 1},
        url="rtsp://127.0.0.1:8554/cam",
        publish_for=0.3,
    )
    plis = {"n": 0}

    async def _pli(pc):
        plis["n"] += 1
        if plis["n"] == 2:  # the start of cycle 2
            pc.connectionState = "closed"

    cam._send_video_pli = _pli
    with caplog.at_level("INFO", logger="aidot_cameras.camera.client"):
        run.run()
    assert len(run.mux_targets) == 2
    assert not _paced(caplog), [r.getMessage() for r in caplog.records]
    assert run.opens == 2


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


class _SlowStopSession:
    """A session whose PC dies after ``die_after`` s and whose stop takes
    ``stop_takes`` s - the teardown that must not count as its life."""

    def __init__(self, die_after: float, stop_takes: float):
        from tests.keepalive_driver import FakePc

        self._pc = FakePc()
        self.die_after = die_after
        self.stop_takes = stop_takes

    async def get_stats(self) -> dict:
        return {}

    async def stop(self) -> None:
        await _real_sleep(self.stop_takes)


def test_a_session_is_measured_to_its_end_not_past_its_teardown(
    monkeypatch, make_camera_device_client, caplog
):
    """Threshold 0.35 s (real time here): the PC dies at 0.2 s, and the
    session's stop then takes 0.4 s. Measured past the teardown it would be
    0.6 s and count as lasting; measured to its end it is short and paced."""
    from tests.keepalive_driver import DtlsServeRun

    proxy = types.SimpleNamespace(
        **{k: getattr(asyncio, k) for k in dir(asyncio) if not k.startswith("_")}
    )
    monkeypatch.setattr(cc, "asyncio", proxy)
    monkeypatch.setenv("AIDOT_DTLS_SHORT_SESSION_S", "0.35")
    cam = make_camera_device_client("A000088")
    cam._stream_idle_opt = 0.0
    sess = _SlowStopSession(die_after=0.2, stop_takes=0.4)
    run = DtlsServeRun(monkeypatch, cam, [sess], timeout=1.5)
    _open = cam.async_open_webrtc_stream

    async def _open_and_die_later(**kwargs):
        out = await _open(**kwargs)
        asyncio.get_running_loop().call_later(
            out.die_after, setattr, out._pc, "connectionState", "closed"
        )
        return out

    cam.async_open_webrtc_stream = _open_and_die_later
    with caplog.at_level("INFO", logger="aidot_cameras.camera.client"):
        run.run()
    paced = [
        r.getMessage() for r in caplog.records if "before reopening" in r.getMessage()
    ]
    assert paced, [r.getMessage() for r in caplog.records]
    assert "ended after 0s" in paced[0]


def test_the_short_session_line_is_throttled(monkeypatch, cam, caplog):
    """INFO for the first short session and every
    AIDOT_DTLS_SLOW_PROBE_LOG_EVERY-th (6) after it; DEBUG for the rest."""
    with caplog.at_level("DEBUG", logger="aidot_cameras.camera.client"):
        _Run(monkeypatch, cam, [_Sess(5.0) for _ in range(8)]).run()
    lines = [r for r in caplog.records if "before reopening" in r.getMessage()]
    assert len(lines) == 8
    info = [i + 1 for i, r in enumerate(lines) if r.levelname == "INFO"]
    assert info == [1, 7]


def test_a_disconnect_is_measured_from_when_it_began(
    monkeypatch, make_camera_device_client, caplog
):
    """The PC goes disconnected at 0.2 s and stays so; the debounce
    (AIDOT_ICE_DISCONNECT_S, 0.4 s here) gives up on it at 0.6 s. With a
    0.45 s threshold the session is short: it died at 0.2 s, not at 0.6 s."""
    from tests.keepalive_driver import DtlsServeRun

    proxy = types.SimpleNamespace(
        **{k: getattr(asyncio, k) for k in dir(asyncio) if not k.startswith("_")}
    )
    monkeypatch.setattr(cc, "asyncio", proxy)
    monkeypatch.setenv("AIDOT_DTLS_SHORT_SESSION_S", "0.45")
    monkeypatch.setenv("AIDOT_ICE_DISCONNECT_S", "0.4")
    cam = make_camera_device_client("A000088")
    cam._stream_idle_opt = 0.0
    sess = _SlowStopSession(die_after=0.2, stop_takes=0.0)
    run = DtlsServeRun(monkeypatch, cam, [sess], timeout=1.5)
    _open = cam.async_open_webrtc_stream

    async def _open_and_drop_later(**kwargs):
        out = await _open(**kwargs)
        asyncio.get_running_loop().call_later(
            out.die_after, setattr, out._pc, "connectionState", "disconnected"
        )
        return out

    cam.async_open_webrtc_stream = _open_and_drop_later
    with caplog.at_level("INFO", logger="aidot_cameras.camera.client"):
        run.run()
    paced = [
        r.getMessage() for r in caplog.records if "before reopening" in r.getMessage()
    ]
    assert paced, [r.getMessage() for r in caplog.records]


def test_the_busy_floor_stops_when_streaming_does(monkeypatch, cam):
    """The 45 s floor is chunked: a stop 5 s into it ends the wait there."""

    def _stop_in_the_floor(run):
        if run.sleeps and run.sleeps[-1][1] <= 5.0 and len(run.opens) == 1:
            cam._streaming_active = False

    run = _Run(monkeypatch, cam, [_ICE, _ICE], on_sleep=_stop_in_the_floor).run()
    assert len(run.opens) == 1
    assert sum(d for _at, d, _w in run.sleeps) <= 5.0


@pytest.mark.parametrize(
    "offline, says", [(False, "(retry 45s)"), (True, "(retry after the offline pause)")]
)
def test_the_failed_open_line_names_the_wait_that_runs(
    monkeypatch, cam, caplog, offline, says
):
    if offline:
        monkeypatch.setattr(cc, "_OFFLINE_RECHECK_S", 5.0)
        cam.status.online = False
        cam._cloud_online_explicit = True

        def _back_online(run):
            cam.status.online = True

    else:
        _back_online = None
    with caplog.at_level("DEBUG", logger="aidot_cameras.camera.client"):
        _Run(monkeypatch, cam, [_ICE], on_sleep=_back_online).run()
    lines = [
        r.getMessage() for r in caplog.records if "open failed for" in r.getMessage()
    ]
    assert lines and says in lines[0], lines


def test_a_pc_dying_in_the_teardown_does_not_lengthen_the_session(
    monkeypatch, make_camera_device_client, caplog
):
    """Serving stops at about 0.2 s (cycle 1's publish ends); the teardown
    then takes 0.3 s, at the end of which the PC closes and is stamped. The
    session's end is the earlier of the two, the serving stop: against a
    0.35 s threshold it is short, not carried to 0.5 s by the late stamp."""
    from tests.keepalive_driver import FakeDtlsSession

    monkeypatch.setenv("AIDOT_DIRECT_PUBLISH", "1")
    monkeypatch.setenv("AIDOT_DTLS_SHORT_SESSION_S", "0.35")
    sess = FakeDtlsSession()
    run, cam = _canary_run(
        monkeypatch,
        make_camera_device_client,
        {"frames": 30, "keyframes": 1},
        url="rtsp://127.0.0.1:8554/cam",
        publish_for=0.2,
    )
    run.sessions = [sess]
    calls = {"n": 0}

    def _slow_terminate(proc):
        calls["n"] += 1
        if calls["n"] == 1:  # the first cycle's teardown
            _real_time.sleep(0.3)
            sess._pc.connectionState = "closed"

    monkeypatch.setattr(cc, "_terminate_proc", _slow_terminate)
    with caplog.at_level("INFO", logger="aidot_cameras.camera.client"):
        run.run()
    assert len(run.mux_targets) == 1
    assert _paced(caplog), [r.getMessage() for r in caplog.records]


def test_a_pc_that_drops_during_the_serve_ready_wait_is_stamped_then(
    monkeypatch, make_camera_device_client, caplog
):
    """No frames, so the serve-ready wait runs its full 40 polls (0.8 s at
    this scale). The PC drops at 0.1 s; the disconnect debounce is 0.3 s.
    Stamped in the wait, the session ended at 0.1 s and is short against a
    0.5 s threshold; stamped only after the wait it would have lasted."""
    monkeypatch.setenv("AIDOT_DTLS_SHORT_SESSION_S", "0.5")
    monkeypatch.setenv("AIDOT_ICE_DISCONNECT_S", "0.3")
    run, cam = _canary_run(
        monkeypatch,
        make_camera_device_client,
        {"frames": 30, "keyframes": 1},
        scale=0.1,
        frames=False,
    )
    run.timeout = 3.0
    _open = cam.async_open_webrtc_stream

    async def _open_and_drop_later(**kwargs):
        out = await _open(**kwargs)
        asyncio.get_running_loop().call_later(
            0.1, setattr, out._pc, "connectionState", "disconnected"
        )
        return out

    cam.async_open_webrtc_stream = _open_and_drop_later
    with caplog.at_level("INFO", logger="aidot_cameras.camera.client"):
        run.run()
    assert _paced(caplog), [r.getMessage() for r in caplog.records]


def test_the_busy_wait_stops_when_streaming_does(monkeypatch, cam):
    busy = AidotCameraBusy(-50002)

    def _stop_in_the_wait(run):
        if len(run.opens) == 1:
            cam._streaming_active = False

    run = _Run(monkeypatch, cam, [busy, busy], on_sleep=_stop_in_the_wait).run()
    assert len(run.opens) == 1
    assert sum(d for _at, d, _w in run.sleeps) <= 5.0


class _CountingEvent(asyncio.Event):
    def __init__(self):
        super().__init__()
        self.sets = 0

    def set(self):
        self.sets += 1
        super().set()


def test_a_session_dead_in_the_serve_ready_wait_is_never_called_serving(
    monkeypatch, make_camera_device_client
):
    """No frames, so the serve-ready wait polls; the PC closes at 0.1 s. The
    wait stops there and serve-ready is never set for the dead session."""
    run, cam = _canary_run(
        monkeypatch,
        make_camera_device_client,
        {"frames": 30, "keyframes": 1},
        scale=0.1,
        frames=False,
    )
    cam._serve_ready = _CountingEvent()
    _open = cam.async_open_webrtc_stream

    async def _open_and_close_later(**kwargs):
        out = await _open(**kwargs)
        asyncio.get_running_loop().call_later(
            0.1, setattr, out._pc, "connectionState", "closed"
        )
        return out

    cam.async_open_webrtc_stream = _open_and_close_later
    run.run()
    assert cam._serve_ready.sets == 0


def test_the_tap_install_gives_up_on_a_dead_pc(monkeypatch, make_camera_device_client):
    """The taps never land and the PC is closed: the install loop stops on its
    first check instead of trying 40 times (about 12 s)."""
    run, cam = _canary_run(monkeypatch, make_camera_device_client, None)
    tries = {"n": 0}

    def _no_taps(pc, vq, aq):
        tries["n"] += 1
        pc.connectionState = "closed"
        return False

    cam._install_av_taps = _no_taps
    run.run()
    assert tries["n"] == 1
