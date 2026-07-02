"""Regression tests for persistent-MQTT robustness fixes (pre-0.8.0 review).

Covers three confirmed defects in the default-on persistent-MQTT path:
  * _request_sync must not raise when a concurrent close() nulls self._client,
    nor when publish() raises - it returns an error tuple so callers fall back.
  * _get_persistent_mqtt must create exactly ONE connection per account even
    under concurrent first-callers (double-checked-locking race).
  * _reap_stream_drain must release the executor thread blocked on outgoing_q
    (cancellation alone cannot) and run the drain's finally (handler removal).
"""
import asyncio
import os
import queue
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot.camera.protocol as proto
import aidot.camera.client as cc
from aidot.camera.protocol import _PersistentMqtt

_CAM = next(v for v in vars(cc).values()
            if isinstance(v, type) and "_get_persistent_mqtt" in v.__dict__)


def _cam():
    return _CAM.__new__(_CAM)


# --- finding 3: _request_sync hardened against a concurrent close()/publish --- #

def test_request_sync_returns_error_when_client_nulled(monkeypatch):
    pm = _PersistentMqtt("wss://h:8443/mqtt", "u", "p", "cid")
    monkeypatch.setattr(pm, "_ensure_started_sync", lambda timeout=15.0: True)
    pm._client = None   # simulate close() nulling the client after the started-check
    messages, st = pm._request_sync([("t", "pp")], ["a/#"], None, 0.05)
    assert messages == []
    assert st.get("error")            # truthy error tuple, NOT an AttributeError
    assert pm._collectors == []       # transient collector still cleaned up


def test_request_sync_returns_error_when_publish_raises(monkeypatch):
    pm = _PersistentMqtt("wss://h:8443/mqtt", "u", "p", "cid")
    monkeypatch.setattr(pm, "_ensure_started_sync", lambda timeout=15.0: True)

    class _BadClient:
        def publish(self, *a):
            raise RuntimeError("boom")

    pm._client = _BadClient()
    messages, st = pm._request_sync([("t", "pp")], [], None, 0.05)
    assert messages == []
    assert "boom" in (st.get("error") or "")
    assert pm._collectors == []


# --- finding 4: get-or-create is single-flight under concurrency ------------- #

def test_get_persistent_mqtt_one_instance_under_concurrency(monkeypatch):
    cam = _cam()
    cam._user_info = {"mqttClientId": "cid"}
    created = []

    async def _auth():
        return {"mqttUser": "u", "mqttPassword": "p"}

    async def _url():
        await asyncio.sleep(0)   # yield so both callers interleave past the first check
        return "wss://h:8443/mqtt"

    cam._async_get_smarthome_auth = _auth
    cam._async_get_mqtt_url = _url

    orig = proto._PersistentMqtt

    def _counting(*a, **k):
        obj = orig(*a, **k)
        created.append(obj)
        return obj

    monkeypatch.setattr(proto, "_PersistentMqtt", _counting)

    async def _run():
        return await asyncio.gather(cam._get_persistent_mqtt(),
                                    cam._get_persistent_mqtt())

    a, b = asyncio.run(_run())
    assert a is b is not None
    assert len(created) == 1           # the race previously built two


# --- finding 1: reaping releases the executor-blocked drain ------------------ #

def test_reap_stream_drain_releases_blocked_drain():
    cam = _cam()
    outq = queue.Queue()
    saw = []

    async def _drain():
        loop = asyncio.get_running_loop()
        try:
            while True:
                out = await loop.run_in_executor(None, outq.get)
                if out is None:        # sentinel from _reap_stream_drain
                    return
        finally:
            saw.append("finally")      # in prod this removes the handler

    async def _run():
        fut = asyncio.ensure_future(_drain())
        await asyncio.sleep(0.05)      # let it block on outgoing_q.get
        cam._stream_mqtt_drain = fut
        cam._stream_mqtt_outq = outq
        await cam._reap_stream_drain()
        return fut

    fut = asyncio.run(_run())
    assert fut.done()
    assert "finally" in saw            # drain's cleanup ran (not leaked)
    assert cam._stream_mqtt_drain is None and cam._stream_mqtt_outq is None


def test_reap_stream_drain_noop_when_nothing_tracked():
    cam = _cam()
    # No drain tracked: must be a clean no-op, not raise.
    asyncio.run(cam._reap_stream_drain())
    assert getattr(cam, "_stream_mqtt_drain", None) is None
