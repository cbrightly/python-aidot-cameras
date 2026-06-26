"""Unit tests for the go2rtc REST client + prefer/fallback helper.

No network: a hand-written fake aiohttp session models the go2rtc API
(GET /api, GET/PUT/DELETE /api/streams).
"""
import asyncio
import os
import sys

sys.path.insert(0, os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "src"))

from aidot_cameras.camera.go2rtc import Go2rtcClient, prefer_go2rtc


class _Resp:
    def __init__(self, status, json_data=None):
        self.status = status
        self._json = json_data if json_data is not None else {}

    async def __aenter__(self):
        return self

    async def __aexit__(self, *a):
        return False

    async def json(self):
        return self._json


class _FakeSession:
    def __init__(self, *, api_status=200, streams=None, put_status=200,
                 delete_status=200, raise_on=()):
        self.api_status = api_status
        self.streams = streams if streams is not None else {}
        self.put_status = put_status
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
        return _Resp(self.put_status)

    def delete(self, url, **kw):
        self.calls.append(("DELETE", url, kw.get("params")))
        return _Resp(self.delete_status)


def test_available():
    assert asyncio.run(Go2rtcClient(_FakeSession(api_status=200)).available()) is True
    assert asyncio.run(Go2rtcClient(_FakeSession(api_status=500)).available()) is False
    assert asyncio.run(Go2rtcClient(_FakeSession(raise_on={"get"})).available()) is False


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
    assert put[2] == {"name": "cam", "src": "rtsp://x/y"}
    assert asyncio.run(Go2rtcClient(_FakeSession(put_status=500)).ensure_stream("c", "s")) is False
    assert asyncio.run(Go2rtcClient(_FakeSession(raise_on={"put"})).ensure_stream("c", "s")) is False


def test_remove_stream():
    assert asyncio.run(Go2rtcClient(_FakeSession(delete_status=200)).remove_stream("cam")) is True
    assert asyncio.run(Go2rtcClient(_FakeSession(delete_status=404)).remove_stream("cam")) is False


def test_rtsp_url():
    c = Go2rtcClient(_FakeSession(), base_url="http://homeassistant.local:1984")
    assert c.rtsp_url("rear") == "rtsp://homeassistant.local:8554/rear"
    assert c.rtsp_url("rear", rtsp_port=18554) == "rtsp://homeassistant.local:18554/rear"


def test_prefer_go2rtc_registers_and_returns_url():
    s = _FakeSession(api_status=200, put_status=200)
    url = asyncio.run(prefer_go2rtc(s, "rear", "rtsp://cam/src",
                                    base_url="http://homeassistant.local:1984"))
    assert url == "rtsp://homeassistant.local:8554/rear"


def test_prefer_go2rtc_falls_back_when_unavailable():
    # go2rtc down -> None (caller serves directly / HLS)
    assert asyncio.run(prefer_go2rtc(_FakeSession(api_status=502), "rear", "s")) is None
    assert asyncio.run(prefer_go2rtc(_FakeSession(raise_on={"get"}), "rear", "s")) is None


def test_prefer_go2rtc_none_when_register_fails():
    assert asyncio.run(prefer_go2rtc(_FakeSession(api_status=200, put_status=500), "rear", "s")) is None


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
