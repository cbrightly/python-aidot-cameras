"""Unit tests for the go2rtc REST client + prefer/fallback helper.

No network: a hand-written fake aiohttp session models the go2rtc API
(GET /api, GET/PUT/DELETE /api/streams).
"""

import asyncio
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.go2rtc import Go2rtcClient, prefer_go2rtc


class _Resp:
    def __init__(self, status, json_data=None, text=""):
        self.status = status
        self._json = json_data if json_data is not None else {}
        self._text = text

    async def __aenter__(self):
        return self

    async def __aexit__(self, *a):
        return False

    async def json(self):
        return self._json

    async def text(self):
        return self._text


class _FakeSession:
    def __init__(
        self,
        *,
        api_status=200,
        streams=None,
        put_status=200,
        put_text="",
        delete_status=200,
        raise_on=(),
        write_delay=0.0,
    ):
        self.api_status = api_status
        self.streams = streams if streams is not None else {}
        self.put_status = put_status
        self.put_text = put_text
        self.write_delay = write_delay
        self.writes_in_flight = 0
        self.max_writes_in_flight = 0
        self.delete_status = delete_status
        self.raise_on = set(raise_on)
        self.calls = []

    def get(self, url, **kw):
        self.calls.append(("GET", url, kw.get("params")))
        if "get" in self.raise_on:
            raise OSError("connection refused")
        if url.endswith("/api"):
            return _Resp(self.api_status)
        if url.endswith("/api/streams"):
            return _Resp(200, self.streams)
        return _Resp(404)

    def put(self, url, **kw):
        self.calls.append(("PUT", url, kw.get("params")))
        if "put" in self.raise_on:
            raise OSError("connection refused")
        return self._write(_Resp(self.put_status, text=self.put_text))

    def delete(self, url, **kw):
        self.calls.append(("DELETE", url, kw.get("params")))
        return self._write(_Resp(self.delete_status))

    def _write(self, resp):
        """A write that takes ``write_delay`` and counts how many overlap."""
        session = self

        class _Timed:
            async def __aenter__(self_):
                session.writes_in_flight += 1
                session.max_writes_in_flight = max(
                    session.max_writes_in_flight, session.writes_in_flight
                )
                await asyncio.sleep(session.write_delay)
                return resp

            async def __aexit__(self_, *a):
                session.writes_in_flight -= 1
                return False

        return _Timed()


def test_available():
    assert asyncio.run(Go2rtcClient(_FakeSession(api_status=200)).available()) is True
    assert asyncio.run(Go2rtcClient(_FakeSession(api_status=500)).available()) is False
    assert (
        asyncio.run(Go2rtcClient(_FakeSession(raise_on={"get"})).available()) is False
    )


def test_list_and_has_stream():
    s = _FakeSession(streams={"cam1": {}, "cam2": {}})
    c = Go2rtcClient(s)
    assert set(asyncio.run(c.list_streams())) == {"cam1", "cam2"}
    assert asyncio.run(c.has_stream("cam1")) is True
    assert asyncio.run(c.has_stream("nope")) is False


def test_ensure_stream():
    s = _FakeSession(put_status=200)
    assert asyncio.run(Go2rtcClient(s).ensure_stream("cam", "rtsp://x/y")) is True
    # the PUT carries name + src params
    put = [c for c in s.calls if c[0] == "PUT"][0]
    # An ORDERED list, not a dict: go2rtc serves a consumer from the first
    # source whose codecs match, so src order is part of the contract.
    assert put[2] == [("name", "cam"), ("src", "rtsp://x/y")]
    assert (
        asyncio.run(Go2rtcClient(_FakeSession(put_status=500)).ensure_stream("c", "s"))
        is False
    )
    assert (
        asyncio.run(
            Go2rtcClient(_FakeSession(raise_on={"put"})).ensure_stream("c", "s")
        )
        is False
    )


def test_ensure_stream_extra_sources_follow_the_primary():
    """A transcoding source is registered AFTER the live one, so it is used
    only by a consumer the live source cannot satisfy (HA's AAC-only HLS
    player), and never in place of the passthrough."""
    s = _FakeSession(put_status=200)
    assert (
        asyncio.run(
            Go2rtcClient(s).ensure_stream(
                "cam", "rtsp://x/y", extra_sources=("ffmpeg:cam#audio=aac",)
            )
        )
        is True
    )
    put = [c for c in s.calls if c[0] == "PUT"][0]
    assert put[2] == [
        ("name", "cam"),
        ("src", "rtsp://x/y"),
        ("src", "ffmpeg:cam#audio=aac"),
    ]


def test_remove_stream():
    assert (
        asyncio.run(Go2rtcClient(_FakeSession(delete_status=200)).remove_stream("cam"))
        is True
    )
    assert (
        asyncio.run(Go2rtcClient(_FakeSession(delete_status=404)).remove_stream("cam"))
        is False
    )


def test_rtsp_url():
    c = Go2rtcClient(_FakeSession(), base_url="http://homeassistant.local:1984")
    assert c.rtsp_url("rear") == "rtsp://homeassistant.local:8554/rear"
    assert (
        c.rtsp_url("rear", rtsp_port=18554) == "rtsp://homeassistant.local:18554/rear"
    )


def test_prefer_go2rtc_registers_and_returns_url():
    s = _FakeSession(api_status=200, put_status=200)
    url = asyncio.run(
        prefer_go2rtc(
            s, "rear", "rtsp://cam/src", base_url="http://homeassistant.local:1984"
        )
    )
    assert url == "rtsp://homeassistant.local:8554/rear"


def test_prefer_go2rtc_falls_back_when_unavailable():
    # go2rtc down -> None (caller serves directly / HLS)
    assert asyncio.run(prefer_go2rtc(_FakeSession(api_status=502), "rear", "s")) is None
    assert (
        asyncio.run(prefer_go2rtc(_FakeSession(raise_on={"get"}), "rear", "s")) is None
    )


def test_prefer_go2rtc_none_when_register_fails():
    assert (
        asyncio.run(
            prefer_go2rtc(_FakeSession(api_status=200, put_status=500), "rear", "s")
        )
        is None
    )


def test_prefer_go2rtc_uses_a_stream_go2rtc_kept_despite_a_400():
    # go2rtc can answer the register call with 400 - a duplicate key in its own
    # config makes it reject every PUT - while still holding the stream. Falling
    # back on that 400 downgrades a camera go2rtc can actually serve to HLS.
    s = _FakeSession(api_status=200, put_status=400, streams={"rear": {}})
    url = asyncio.run(
        prefer_go2rtc(
            s, "rear", "rtsp://cam/src", base_url="http://homeassistant.local:1984"
        )
    )
    assert url == "rtsp://homeassistant.local:8554/rear"


if __name__ == "__main__":
    _fail = 0
    for _k, _v in sorted(globals().items()):
        if _k.startswith("test_"):
            try:
                _v()
                print(f"PASS {_k}")
            except Exception as _e:
                _fail += 1
                print(f"FAIL {_k}: {_e}")
    raise SystemExit(1 if _fail else 0)


def test_a_rejected_register_call_logs_what_go2rtc_said(caplog):
    # go2rtc 1.9.9 answers every PUT with 400 once its own config file holds a
    # duplicate key, and the body names the key: the one line that explains a
    # wall of "failed http=400" warnings. Logged on one line, cut short.
    s = _FakeSession(
        put_status=400,
        put_text='yaml: unmarshal errors:\n  line 4: mapping key "aidot_x" already defined at line 2\n'
        + "x" * 300,
    )
    with caplog.at_level("WARNING"):
        assert asyncio.run(Go2rtcClient(s).ensure_stream("cam", "rtsp://x/y")) is False
    (rec,) = [r for r in caplog.records if "failed http=400" in r.getMessage()]
    msg = rec.getMessage()
    assert 'mapping key "aidot_x" already defined' in msg
    assert "\n" not in msg and len(msg) < 260


def test_writes_to_one_go2rtc_never_overlap_even_from_two_clients():
    # go2rtc 1.9.9 saves every PUT and DELETE by reading its config file,
    # patching it and writing it back with nothing guarding the read-modify-
    # write. Two overlapping writes - for any two streams - can tear the file:
    # keys lost, or one duplicated, after which go2rtc rejects every later
    # write with 400 until the file is fixed by hand (reproduced 2026-10-08).
    # So the client queues every write to one server, across client objects.
    s = _FakeSession(write_delay=0.02)

    async def run():
        a, b = (
            Go2rtcClient(s, "http://127.0.0.1:1984"),
            Go2rtcClient(s, "http://127.0.0.1:1984/"),
        )
        await asyncio.gather(
            a.ensure_stream("cam_a", "rtsp://x/a"),
            b.ensure_stream("cam_b", "rtsp://x/b"),
            a.remove_stream("cam_c"),
            b.ensure_stream("cam_d", "rtsp://x/d"),
        )

    asyncio.run(run())
    assert len(s.calls) == 4
    assert s.max_writes_in_flight == 1


def test_writes_to_different_go2rtc_servers_do_not_queue_on_each_other():
    s = _FakeSession(write_delay=0.02)

    async def run():
        a, b = (
            Go2rtcClient(s, "http://127.0.0.1:1984"),
            Go2rtcClient(s, "http://127.0.0.1:1985"),
        )
        await asyncio.gather(
            a.ensure_stream("cam_a", "rtsp://x/a"),
            b.ensure_stream("cam_b", "rtsp://x/b"),
        )

    asyncio.run(run())
    assert s.max_writes_in_flight == 2
