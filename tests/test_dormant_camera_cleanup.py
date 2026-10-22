"""A dormant camera must not leave a dead stream registered in go2rtc.

Idle-release stops the serve but used to leave the go2rtc stream in place,
pointing at a source that no longer exists. A viewer attaching then gets a hard
"connection refused" on the serve port rather than a clean miss - and in PUSH mode
that source was never dialable at all, because the keepalive publishes INTO go2rtc
rather than serving over HTTP.

Observed live: four go2rtc streams registered against serve ports with nothing
listening, and go2rtc reporting
`dial tcp 127.0.0.1:18981: connect: connection refused`.
"""

import asyncio

import aidot_cameras.camera.client as cc
from aidot_cameras.camera.sdes_open import (
    _FFMPEG_EXIT_EPIPE,
    _classify_ffmpeg_exit,
)
import logging

import pytest


def test_dtls_idle_release_goes_dormant(monkeypatch, make_camera_device_client):
    from tests.keepalive_driver import dtls_view as _dtls_view

    run = _dtls_view(monkeypatch, make_camera_device_client, lambda e: False)
    assert run.dormant == [{"release_url": True, "why": "idle release"}]
    assert run.deregistered == 1
    assert run.client._streaming_active is False
    assert run.client._keepalive_rtsp_url is None


@pytest.mark.parametrize("exit_", ["idle-release", "video-abandon"])
def test_every_dtls_exit_that_stops_streaming_also_deregisters(
    monkeypatch, make_camera_device_client, exit_
):
    """The DTLS serve's exits, the same rule as the SDES ones above."""
    from tests.keepalive_driver import dtls_view as _dtls_view

    if exit_ == "video-abandon":
        monkeypatch.setenv("AIDOT_DTLS_VIDEO_GRACE_S", "0.2")
        monkeypatch.setenv("AIDOT_DTLS_FUTILE_VIDEO_LIMIT", "1")
        run = _dtls_view(
            monkeypatch, make_camera_device_client, lambda e: True, video=False
        )
    else:
        run = _dtls_view(monkeypatch, make_camera_device_client, lambda e: False)
    assert len(run.dormant) == 1, run.dormant
    assert run.client._streaming_active is False
    assert run.deregistered == 1


def test_sdes_idle_release_goes_dormant(monkeypatch, make_camera_device_client):
    from tests.keepalive_driver import FakeSession, SdesKeepaliveRun

    cam = make_camera_device_client("A001064")
    run = SdesKeepaliveRun(
        monkeypatch, cam, [FakeSession(life=None)], viewer=lambda now: False
    ).run()
    assert run.dormant == [{"release_url": True, "why": "idle release"}]
    assert run.deregistered == 1
    assert cam._streaming_active is False and cam._keepalive_rtsp_url is None
    assert run.sessions[0].stopped >= 1


@pytest.mark.parametrize(
    "outcomes",
    [
        pytest.param("idle", id="idle-release"),
        pytest.param("open-abandon", id="no-media-open-abandon"),
        pytest.param("session-abandon", id="no-media-session-abandon"),
    ],
)
def test_every_sdes_exit_that_stops_streaming_also_deregisters(
    monkeypatch, make_camera_device_client, outcomes
):
    """Every way the SDES keepalive stops streaming takes the stream out of
    go2rtc too - the futile-keepalive abandon once returned without it, leaving
    a dormant camera's stream pointed at a dead serve port."""
    from tests.keepalive_driver import FakeSession, SdesKeepaliveRun, no_media

    cam = make_camera_device_client("A001513")
    limit = cc._FUTILE_KEEPALIVE_LIMIT
    script, viewer = {
        "idle": ([FakeSession(life=None)], lambda now: False),
        "open-abandon": (no_media(limit), lambda now: None),
        "session-abandon": (
            [FakeSession(media=False, life=10) for _ in range(limit)],
            lambda now: None,
        ),
    }[outcomes]
    run = SdesKeepaliveRun(monkeypatch, cam, script, viewer=viewer).run()

    assert len(run.dormant) == 1, run.dormant
    assert cam._streaming_active is False
    assert run.deregistered == 1


class _Dormant:
    """The attributes _go_dormant touches, with a go2rtc that may refuse."""

    def __init__(self, fail=False):
        self.device_id = "cam"
        self._streaming_active = True
        self._keepalive_rtsp_url = "rtsp://x"
        self._serve_ready = asyncio.Event()
        self._serve_ready.set()
        self.cancelled = 0
        self.deregistered = 0
        self._fail = fail

    def _cancel_keepalive_renew(self):
        self.cancelled += 1

    async def _deregister_go2rtc(self):
        self.deregistered += 1
        if self._fail:
            raise OSError("go2rtc unreachable")


async def test_going_dormant_deregisters_and_releases_the_url():
    d = _Dormant()
    await cc.CameraMixin._go_dormant(d, release_url=True, why="idle release")
    assert d._streaming_active is False
    assert d.cancelled == 1 and d.deregistered == 1
    assert d._keepalive_rtsp_url is None and not d._serve_ready.is_set()


async def test_an_abandon_keeps_the_url_but_still_deregisters():
    d = _Dormant()
    await cc.CameraMixin._go_dormant(d, release_url=False, why="abandon")
    assert d._streaming_active is False
    assert d.deregistered == 1
    assert d._keepalive_rtsp_url == "rtsp://x" and d._serve_ready.is_set()


async def test_deregistration_failure_cannot_break_the_release():
    # Going dormant must still complete if go2rtc is unreachable.
    d = _Dormant(fail=True)
    await cc.CameraMixin._go_dormant(d, release_url=True, why="idle release")
    assert d._streaming_active is False and d.deregistered == 1


def test_consumer_disconnect_is_not_logged_as_a_failure():
    # ffmpeg returns AVERROR(EPIPE) = -32 when its consumer goes away, and an exit
    # status is an unsigned byte, so it surfaces as 224. That is the normal end of
    # a `-listen 1` serve, not a fault.
    assert _FFMPEG_EXIT_EPIPE == 224
    assert _classify_ffmpeg_exit(224, teardown_requested=False) == logging.DEBUG
    assert _classify_ffmpeg_exit(224, teardown_requested=True) == logging.DEBUG


def test_real_failures_still_warn():
    assert _classify_ffmpeg_exit(1, teardown_requested=False) == logging.WARNING
    assert _classify_ffmpeg_exit(255, teardown_requested=False) == logging.WARNING
    # A signal death with no teardown in flight is still unexpected.
    assert _classify_ffmpeg_exit(-9, teardown_requested=False) == logging.WARNING
    # ...but an expected teardown is quiet.
    assert _classify_ffmpeg_exit(-9, teardown_requested=True) == logging.DEBUG
