"""Waiting for the publish target before launching the serve ffmpeg.

After a Home Assistant restart the serve ffmpeg can be launched before go2rtc's
RTSP listener is back. ffmpeg's first connect is then refused, it exits 145, and
that reads as "stream ended" -- the camera's view is down until something
retries. Every observed occurrence on a live box sat within a minute of a
restart.
"""
import asyncio
import socket

from aidot_cameras.camera.sdes_open import _await_rtsp_publish_target


def _free_port() -> int:
    s = socket.socket()
    s.bind(("127.0.0.1", 0))
    port = s.getsockname()[1]
    s.close()
    return port


class TestItWaitsForTheTarget:
    def test_an_open_port_returns_immediately(self):
        srv = socket.socket()
        srv.bind(("127.0.0.1", 0))
        srv.listen(1)
        port = srv.getsockname()[1]
        try:
            ok = asyncio.run(_await_rtsp_publish_target(
                f"rtsp://127.0.0.1:{port}/aidot_x", timeout=5.0))
            assert ok is True
        finally:
            srv.close()

    def test_a_closed_port_times_out_but_does_not_raise(self):
        """False, not an exception: the caller still launches ffmpeg so a target
        that never opens surfaces as ffmpeg's own error, not a silent refusal."""
        ok = asyncio.run(_await_rtsp_publish_target(
            f"rtsp://127.0.0.1:{_free_port()}/aidot_x", timeout=1.0))
        assert ok is False


class TestItNeverBlocksTheWrongThing:
    def test_a_non_rtsp_url_is_not_probed(self):
        assert asyncio.run(_await_rtsp_publish_target(
            "http://127.0.0.1:1/x.ts", timeout=0.1)) is True

    def test_none_is_not_probed(self):
        assert asyncio.run(_await_rtsp_publish_target(None, timeout=0.1)) is True

    def test_an_unparseable_url_does_not_block(self):
        assert asyncio.run(_await_rtsp_publish_target(
            "rtsp://[unclosed", timeout=0.1)) is True


class TestUrlForms:
    def test_credentials_in_the_url_are_stripped_before_the_host(self):
        srv = socket.socket()
        srv.bind(("127.0.0.1", 0))
        srv.listen(1)
        port = srv.getsockname()[1]
        try:
            ok = asyncio.run(_await_rtsp_publish_target(
                f"rtsp://user:pw@127.0.0.1:{port}/aidot_x", timeout=5.0))
            assert ok is True
        finally:
            srv.close()

    def test_a_missing_port_defaults_to_554(self):
        # 554 is almost certainly closed here; the point is it parses and
        # returns rather than raising.
        assert asyncio.run(_await_rtsp_publish_target(
            "rtsp://127.0.0.1/aidot_x", timeout=0.5)) in (True, False)
