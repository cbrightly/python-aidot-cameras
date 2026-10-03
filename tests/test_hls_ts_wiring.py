"""Wiring of the in-sync HLS TS path: when it is on, which cameras, which URL.

Home Assistant fixes a stream's source URL when it creates the stream, so the
URL must be the same for the life of the process and must exist before the
camera's session does. The TS path needs the direct publisher (it is fed from
it) and the AAC track (Home Assistant's HLS keeps only AAC), and in this first
phase only DTLS cameras: the SDES publishers' AAC alignment is not yet good
enough to hand to every joiner (design review, 2026-10-03).
"""

import socket
import time

import pytest

import aidot_cameras.camera.hls_ts as hls_ts
from aidot_cameras.camera.client import CameraMixin


@pytest.fixture(autouse=True)
def _fresh(monkeypatch):
    hls_ts.shutdown()
    for k in ("AIDOT_HLS_DIRECT_TS", "AIDOT_DIRECT_PUBLISH", "AIDOT_PUBLISH_AAC"):
        monkeypatch.delenv(k, raising=False)
    yield
    hls_ts.shutdown()


def _on(monkeypatch):
    monkeypatch.setenv("AIDOT_HLS_DIRECT_TS", "1")
    monkeypatch.setenv("AIDOT_DIRECT_PUBLISH", "1")
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "1")


class _Cam(CameraMixin):
    """A camera whose transport the test chooses (no cloud profile needed)."""

    is_sdes_camera = property(lambda self: self._sdes)


def _cam(sdes=False, push_url="rtsp://127.0.0.1:8554/aidot_0123456789ab"):
    c = _Cam.__new__(_Cam)
    c.device_id = "0123456789abcdef0123456789abcdef"
    c._sdes = sdes
    c._keepalive_rtsp_url = push_url  # set by start_keepalive
    return c


@pytest.mark.parametrize(
    "env",
    [
        {},
        {"AIDOT_HLS_DIRECT_TS": "1"},
        {"AIDOT_HLS_DIRECT_TS": "1", "AIDOT_DIRECT_PUBLISH": "1"},
        {"AIDOT_HLS_DIRECT_TS": "1", "AIDOT_PUBLISH_AAC": "1"},
        {"AIDOT_DIRECT_PUBLISH": "1", "AIDOT_PUBLISH_AAC": "1"},
    ],
)
def test_off_unless_the_option_direct_publish_and_aac_are_all_on(monkeypatch, env):
    for k, v in env.items():
        monkeypatch.setenv(k, v)
    assert not hls_ts.enabled()
    assert _cam().hls_ts_url() is None


def test_a_dtls_camera_gets_a_stable_url_before_its_session_exists(monkeypatch):
    _on(monkeypatch)
    cam = _cam()
    url = cam.hls_ts_url()
    assert (
        url
        and url.startswith("http://127.0.0.1:")
        and url.endswith("/aidot_0123456789ab.ts")
    )
    assert cam.hls_ts_url() == url  # same URL every call
    # The channel already exists: a consumer can connect and wait for media.
    port = int(url.split(":")[2].split("/")[0])
    s = socket.create_connection(("127.0.0.1", port), timeout=3)
    s.sendall(b"GET /aidot_0123456789ab.ts HTTP/1.1\r\n\r\n")
    s.settimeout(3)
    assert s.recv(64).startswith(b"HTTP/1.0 200")
    s.close()


def test_sdes_cameras_stay_on_go2rtc_in_this_phase(monkeypatch):
    _on(monkeypatch)
    assert _cam(sdes=True).hls_ts_url() is None


def test_the_dtls_publisher_gets_a_fresh_session_only_when_on(monkeypatch):
    cam = _cam()
    assert "ts_session" not in cam._dtls_publish_kwargs({})
    _on(monkeypatch)
    a = cam._dtls_publish_kwargs({})["ts_session"]
    b = cam._dtls_publish_kwargs({})["ts_session"]
    assert a is not b and a._sid != b._sid  # each serve cycle is a new session


async def test_a_ts_consumer_counts_as_a_viewer_even_when_go2rtc_sees_none(monkeypatch):
    _on(monkeypatch)
    cam = _cam()
    url = cam.hls_ts_url()
    port = int(url.split(":")[2].split("/")[0])
    s = socket.create_connection(("127.0.0.1", port), timeout=3)
    s.sendall(b"GET /aidot_0123456789ab.ts HTTP/1.1\r\n\r\n")
    s.settimeout(3)
    s.recv(64)
    end = time.time() + 3
    while time.time() < end and hls_ts.consumers(cam._go2rtc_stream_name()) == 0:
        time.sleep(0.02)
    cam._viewer_cache = (0.0, None)
    cam._go2rtc_url = "http://127.0.0.1:1"  # would answer "no viewers" if asked
    cam._keepalive_rtsp_url = "rtsp://127.0.0.1:8554/x"
    assert await cam._viewer_present(0) is True
    s.close()


@pytest.mark.parametrize("push_url", [None, "http://127.0.0.1:18765/serve.ts"])
def test_no_ts_url_unless_the_camera_publishes_to_go2rtc(monkeypatch, push_url):
    # With go2rtc unreachable a DTLS camera is pulled from its local serve and
    # the direct publisher - the only thing that feeds the TS - never runs. A TS
    # URL then would give Home Assistant a stream nothing writes: no video at all.
    _on(monkeypatch)
    assert _cam(push_url=push_url).hls_ts_url() is None


async def test_ts_consumers_are_not_viewers_while_the_option_is_off(monkeypatch):
    cam = _cam()
    monkeypatch.setattr(hls_ts, "consumers", lambda name: 3)  # left over from before
    cam._viewer_cache = (0.0, None)
    cam._go2rtc_url = None
    assert await cam._viewer_present(0) is not True


def test_a_stopped_tee_is_replaced_and_the_url_is_kept(monkeypatch):
    # A mux thread that died used to stay dead for the life of the process,
    # with Home Assistant still pointed at its (now silent) URL.
    _on(monkeypatch)
    cam = _cam()
    url = cam.hls_ts_url()
    name = cam._go2rtc_stream_name()
    first = hls_ts.tee_for(name)
    first.close()  # the mux thread is gone
    assert not first.is_running()
    second = hls_ts.tee_for(name)
    assert second is not first and second.is_running()
    assert cam.hls_ts_url() == url
