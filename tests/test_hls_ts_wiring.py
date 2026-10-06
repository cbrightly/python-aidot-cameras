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


def _path_of(url):
    return "/" + url.split("/", 3)[3]


def _get(url, path):
    port = int(url.split(":")[2].split("/")[0])
    s = socket.create_connection(("127.0.0.1", port), timeout=3)
    s.sendall(b"GET %s HTTP/1.1\r\n\r\n" % path.encode())
    s.settimeout(3)
    head = s.recv(64)
    s.close()
    return head


def _on(monkeypatch):
    monkeypatch.setenv("AIDOT_HLS_DIRECT_TS", "1")
    monkeypatch.setenv("AIDOT_DIRECT_PUBLISH", "1")
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "1")


class _Cam(CameraMixin):
    """A camera whose transport the test chooses (no cloud profile needed)."""

    is_sdes_camera = property(lambda self: self._sdes)


def _cam(sdes=False, push_url="rtsp://127.0.0.1:8554/aidot_0123456789ab", model=""):
    c = _Cam.__new__(_Cam)
    c.device_id = "0123456789abcdef0123456789abcdef"
    c._sdes = sdes
    c.info = type("Info", (), {"model_id": model})()
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
    s.sendall(b"GET %s HTTP/1.1\r\n\r\n" % _path_of(url).encode())
    s.settimeout(3)
    assert s.recv(64).startswith(b"HTTP/1.0 200")
    s.close()


@pytest.mark.parametrize(
    ("model", "pin", "eligible"),
    [
        ("LK.IPC.A001064", "96", True),
        ("LK.IPC.A001513", "96", True),
        ("LK.IPC.A001064", None, False),  # might answer H.265: ffmpeg serve
        ("LK.IPC.A001064", "97", False),
        ("LK.IPC.A009999", "96", False),  # media reaches the serve encrypted
    ],
)
def test_an_sdes_camera_gets_the_ts_only_when_every_session_feeds_it(
    monkeypatch, model, pin, eligible
):
    # Home Assistant keeps the URL it is given. A TS that a session never
    # writes (the ffmpeg serve: an H.265 answer, or a model whose media the
    # bridge cannot decrypt) would be no video at all, not just late sound.
    _on(monkeypatch)
    if pin is None:
        monkeypatch.delenv("AIDOT_SDES_VIDEO_PT", raising=False)
    else:
        monkeypatch.setenv("AIDOT_SDES_VIDEO_PT", pin)
    cam = _cam(sdes=True, model=model)
    assert (cam.hls_ts_url() is not None) is eligible
    assert (cam._hls_ts_session() is not None) is eligible


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
    s.sendall(b"GET %s HTTP/1.1\r\n\r\n" % _path_of(url).encode())
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


def test_the_url_carries_a_secret_only_the_process_knows(monkeypatch):
    # The listener is loopback-only, but anything else on the host (an add-on
    # sharing the host network, say) could otherwise read a camera's video by
    # guessing the port and the camera's well-known stream name.
    _on(monkeypatch)
    url = _cam().hls_ts_url()
    token = _path_of(url).split("/")[1]
    assert len(token) >= 20 and token not in ("aidot_0123456789ab.ts",)
    assert _get(url, _path_of(url)).startswith(b"HTTP/1.0 200")
    assert _get(url, "/aidot_0123456789ab.ts").startswith(b"HTTP/1.0 404")
    assert _get(url, "/x" + token[1:] + "/aidot_0123456789ab.ts").startswith(
        b"HTTP/1.0 404"
    )
    hls_ts.shutdown()  # a new listener gets a new secret
    assert _path_of(_cam().hls_ts_url()).split("/")[1] != token


def test_the_listener_base_url_is_left_for_the_owners_tools(monkeypatch, tmp_path):
    # Test tooling on the host (a raw capture of a camera's TS, as reference
    # clock) needs the secret path. It is written beside the library's other
    # state, readable by the owner only, and removed when the listener stops.
    monkeypatch.setenv("AIDOT_SPROP_DIR", str(tmp_path))
    _on(monkeypatch)
    url = _cam().hls_ts_url()
    f = tmp_path / "hls-ts-base"
    assert f.read_text() == url.rsplit("/", 1)[0] + "/"
    assert (f.stat().st_mode & 0o777) == 0o600
    hls_ts.shutdown()
    assert not f.exists()


def test_an_unwritable_state_dir_does_not_stop_the_stream(monkeypatch, tmp_path):
    blocker = tmp_path / "not-a-dir"
    blocker.write_text("x")
    monkeypatch.setenv("AIDOT_SPROP_DIR", str(blocker))
    _on(monkeypatch)
    assert _cam().hls_ts_url() is not None
