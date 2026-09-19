"""The DTLS direct TS server is an HTTP listener: it may only stand in for an
http:// pull serve. For "-" it used to bind a random port and write nothing to
stdout (the `aidot-go2rtc <dtls-id> -` exec source produced no media), and for
rtsp:// it bound the push target's port locally instead of publishing."""

import inspect

from aidot_cameras.camera import client
from aidot_cameras.camera.client import _is_http_serve_url


def test_only_http_urls_are_direct_ts_serves():
    assert _is_http_serve_url("http://127.0.0.1:18600/aidot_x.ts")
    assert _is_http_serve_url("HTTP://127.0.0.1:18600/aidot_x.ts")
    assert not _is_http_serve_url("-")
    assert not _is_http_serve_url("rtsp://127.0.0.1:8554/aidot_x")
    assert not _is_http_serve_url(None)


def test_the_serve_loop_gates_the_direct_ts_serve_on_http():
    src = inspect.getsource(client)
    assert "elif _direct_serve_enabled() and _is_http_serve_url(serve_url):" in src
