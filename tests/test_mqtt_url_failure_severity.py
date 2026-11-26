"""A box-side DNS blip at stream open must not page; a lasting one must.

Measured 2026-11-26: four cameras opened inside 8 s while the box briefly
could not resolve the vendor cloud host. ``_async_get_mqtt_url`` logged each
``ClientConnectorDNSError`` at ERROR, and the soak watcher paged on four
unclassified library errors. The name resolved again within minutes and the
serve loop retries a failed open on its own pacing, so one failed attempt is
a WARNING. ``_is_transient_cloud_error`` already called this error transient
for the motion poll (test_cloud_error_severity); this path ignored it.

ERROR is kept for what it means: a failure not known to be transient, or a
transient one that has gone on for ``_MQTT_URL_TRANSIENT_GRACE_S``.
"""

import asyncio
import logging

import aiohttp
import pytest

import aidot_cameras.camera.client as cc
from aidot_cameras.device_client import CameraDeviceClient

_LOG = "aidot_cameras.camera.client"
_PREFIX = "_async_get_mqtt_url failed"
_SRV = {
    "code": 200,
    "data": {"mqttServerUrl": "wss://srv.example:8443/mqtt", "ip": "203.0.113.7"},
}


class _Resp:
    def __init__(self, body):
        self._body = body

    async def json(self, content_type=None):
        return self._body

    async def __aenter__(self):
        return self

    async def __aexit__(self, *exc):
        return False


class _Session:
    """Raises ``exc`` on every GET while it is set, else answers ``_SRV``."""

    def __init__(self):
        self.exc = None

    def get(self, url, **kw):
        if self.exc is not None:
            raise self.exc
        return _Resp(_SRV)

    async def __aenter__(self):
        return self

    async def __aexit__(self, *exc):
        return False


def _camera():
    c = CameraDeviceClient.__new__(CameraDeviceClient)
    c._user_info = {"id": "u1", "accessToken": "AT", "mqttPassword": "P"}
    c._smarthome_auth = None
    c._mqtt_credential_refresh_cb = None
    c._mqtt_url = None
    c._mqtt_url_refused_scheme = None
    c._mqtt_refused_at = 0.0
    c._region = "us"
    c.user_id = "u1"
    return c


def _dns_error():
    key = type("K", (), {"host": "cloud.invalid", "port": 443, "ssl": True})()
    return aiohttp.ClientConnectorDNSError(key, OSError(-3, "Try again"))


@pytest.fixture(autouse=True)
def _env(monkeypatch):
    for name in ("AIDOT_MQTT_CONFIG2", "AIDOT_MQTT_URL"):
        monkeypatch.delenv(name, raising=False)


@pytest.fixture
def session(monkeypatch):
    sess = _Session()
    monkeypatch.setattr(aiohttp, "ClientSession", lambda *a, **k: sess)
    return sess


@pytest.fixture
def clock(monkeypatch):
    now = [1000.0]
    monkeypatch.setattr(cc.time, "monotonic", lambda: now[0])
    return now


def _lines(caplog):
    return [r for r in caplog.records if r.getMessage().startswith(_PREFIX)]


def test_dns_blip_is_a_warning_that_names_the_exception(session, clock, caplog):
    session.exc = _dns_error()
    cam = _camera()
    with caplog.at_level(logging.DEBUG, logger=_LOG):
        assert asyncio.run(cam._async_get_mqtt_url()) is None
    (rec,) = _lines(caplog)
    assert rec.levelno == logging.WARNING
    assert "ClientConnectorDNSError" in rec.getMessage()


def test_a_run_past_the_grace_escalates(session, clock, caplog):
    session.exc = _dns_error()
    cam = _camera()
    with caplog.at_level(logging.DEBUG, logger=_LOG):
        asyncio.run(cam._async_get_mqtt_url())
        clock[0] += cc._MQTT_URL_TRANSIENT_GRACE_S - 1
        asyncio.run(cam._async_get_mqtt_url())
        clock[0] += 1
        asyncio.run(cam._async_get_mqtt_url())
    levels = [r.levelno for r in _lines(caplog)]
    assert levels == [logging.WARNING, logging.WARNING, logging.ERROR]


def test_success_resets_the_run(session, clock, caplog):
    cam = _camera()
    with caplog.at_level(logging.DEBUG, logger=_LOG):
        session.exc = _dns_error()
        asyncio.run(cam._async_get_mqtt_url())
        session.exc = None
        clock[0] += 10
        assert asyncio.run(cam._async_get_mqtt_url()) == "wss://srv.example:8443/mqtt"
        # The URL is cached; a later failure only happens once it is dropped.
        cam._mqtt_url = None
        session.exc = _dns_error()
        clock[0] += cc._MQTT_URL_TRANSIENT_GRACE_S * 10
        asyncio.run(cam._async_get_mqtt_url())
    levels = [r.levelno for r in _lines(caplog)]
    assert levels == [logging.WARNING, logging.WARNING]


def test_an_unknown_failure_is_an_error_at_once(session, clock, caplog):
    session.exc = RuntimeError("surprise")
    cam = _camera()
    with caplog.at_level(logging.DEBUG, logger=_LOG):
        asyncio.run(cam._async_get_mqtt_url())
    (rec,) = _lines(caplog)
    assert rec.levelno == logging.ERROR
    assert "RuntimeError" in rec.getMessage()
