"""The DTLS direct TS server is an HTTP listener: it may only stand in for an
http:// pull serve. For "-" it used to bind a random port and write nothing to
stdout (the `aidot-go2rtc <dtls-id> -` exec source produced no media), and for
rtsp:// it bound the push target's port locally instead of publishing."""

import pytest

from aidot_cameras.camera.client import _is_http_serve_url


def test_only_http_urls_are_direct_ts_serves():
    assert _is_http_serve_url("http://127.0.0.1:18600/aidot_x.ts")
    assert _is_http_serve_url("HTTP://127.0.0.1:18600/aidot_x.ts")
    assert not _is_http_serve_url("-")
    assert not _is_http_serve_url("rtsp://127.0.0.1:8554/aidot_x")
    assert not _is_http_serve_url(None)


@pytest.mark.parametrize(
    "url, direct",
    [
        ("http://127.0.0.1:18557/cam.ts", True),
        ("-", False),
        ("rtsp://127.0.0.1:8554/cam", False),
    ],
)
def test_the_serve_loop_gates_the_direct_ts_serve_on_http(
    monkeypatch, make_camera_device_client, url, direct
):
    """Only an http:// serve is one go2rtc dials. For "-" the direct serve bound
    a random port and wrote nothing to stdout; for rtsp:// it bound the push
    target's port locally instead of publishing to it."""
    from tests.keepalive_driver import DtlsServeRun, FakeDtlsSession

    monkeypatch.setenv("AIDOT_DIRECT_PUBLISH", "0")
    cam = make_camera_device_client("A000088")
    cam._stream_idle_opt = 0.0
    spawned = []

    class _Proc:
        returncode = None

        def terminate(self):
            self.returncode = -15

    async def _spawn(serve_url, fd):
        spawned.append(serve_url)
        return _Proc()

    cam._spawn_dtls_serve_ffmpeg = _spawn
    run = DtlsServeRun(
        monkeypatch, cam, [FakeDtlsSession()], url=url, timeout=0.5
    ).run()
    assert bool(run.direct) is direct
    assert bool(spawned) is not direct, "every other serve goes through ffmpeg"
