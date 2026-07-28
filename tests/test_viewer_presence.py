"""Idle-release must key on real viewers, not on go2rtc being attached.

go2rtc attaches to the camera serve as its PRODUCER and stays attached for as long
as the stream is registered - viewer or no viewer. So the serve socket always has
an ESTABLISHED peer, the idle window never elapses, and every camera keeps
streaming forever after a single view. Confirmed live: five cameras, nobody
watching, still producing after 7 minutes against a 5 minute idle window.
"""
import asyncio

from aidot_cameras.camera.go2rtc import Go2rtcClient


class _Resp:
    def __init__(self, payload, status=200):
        self._p, self.status = payload, status

    async def __aenter__(self):
        return self

    async def __aexit__(self, *a):
        return False

    async def json(self):
        return self._p


class _Session:
    def __init__(self, payload, status=200):
        self._p, self._status = payload, status

    def get(self, *a, **kw):
        return _Resp(self._p, self._status)


def _count(payload, name="aidot_abc", status=200):
    client = Go2rtcClient(_Session(payload, status), "http://go2rtc:1984")
    return asyncio.run(client.viewer_count(name))


def test_no_viewers_when_consumers_is_empty():
    # A producer attached with no consumers is exactly the idle case that was
    # being misread as "someone is watching".
    assert _count({"aidot_abc": {"producers": [{"url": "x"}], "consumers": []}}) == 0


def test_no_viewers_when_go2rtc_reports_null():
    # go2rtc returns null rather than [] for some streams; that is still nobody.
    assert _count({"aidot_abc": {"producers": [{"url": "x"}], "consumers": None}}) == 0


def test_counts_real_viewers():
    assert _count({"aidot_abc": {"consumers": [{"id": 1}, {"id": 2}]}}) == 2


def test_unknown_when_the_stream_is_absent():
    # Unknown must stay unknown so the caller fails safe and does NOT release.
    assert _count({"something_else": {"consumers": []}}) is None


def test_unknown_when_go2rtc_is_unreachable():
    assert _count({}, status=500) is None


def test_release_policy_treats_unknown_as_do_not_release():
    from aidot_cameras.camera.protocol import _idle_release_due
    now = 1000.0
    assert _idle_release_due(None, now - 9999, now, 300) is False   # unknown
    assert _idle_release_due(True, now - 9999, now, 300) is False   # watching
    assert _idle_release_due(False, now - 9999, now, 300) is True   # idle, elapsed
    assert _idle_release_due(False, now - 10, now, 300) is False    # idle, too soon
