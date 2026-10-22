"""A non-TLS MQTT broker URL from the cloud is refused unless opted in.

The broker connection carries the account's MQTT password, and the broker URL
comes from the cloud's getServerUrlConfig answer. paho connects over websockets
and enables TLS only for a TLS scheme, so a ``ws://`` URL used to be accepted
as-is and would have sent the password in the clear.
"""

import asyncio
import types

import pytest

from aidot_cameras import (
    AidotError,
    AidotMqttUnavailable,
    AidotPlaintextMqttRefused,
    AidotStreamOpenError,
)
from aidot_cameras.camera.constants import plaintext_mqtt_scheme


class _Resp:
    def __init__(self, body):
        self._body = body

    async def __aenter__(self):
        return self

    async def __aexit__(self, *exc):
        return False

    async def json(self, content_type=None):
        return self._body


class _Session:
    body: dict = {}

    async def __aenter__(self):
        return self

    async def __aexit__(self, *exc):
        return False

    def get(self, *_a, **_k):
        return _Resp(self.body)


def _client(monkeypatch, make_camera_device_client, mqtt_server_url):
    import aiohttp

    dc = make_camera_device_client("A001513")
    dc._mqtt_url = None
    dc._smarthome_auth = None
    monkeypatch.delenv("AIDOT_MQTT_URL", raising=False)
    monkeypatch.delenv("AIDOT_SMARTHOME_URL_TEMPLATE", raising=False)
    monkeypatch.setattr(dc, "_leedarson_headers", lambda: {})
    body = {"data": {"mqttServerUrl": mqtt_server_url, "mqttPassword": "pw"}}
    monkeypatch.setattr(
        aiohttp, "ClientSession", type("S", (_Session,), {"body": body})
    )
    return dc


@pytest.mark.parametrize(
    "url, scheme",
    [
        ("ws://broker.example.invalid:8080/mqtt", "ws"),
        ("WS://broker.example.invalid/mqtt", "ws"),
        ("tcp://broker.example.invalid:1883", "tcp"),
        ("mqtt://broker.example.invalid:1883", "mqtt"),
        ("http://broker.example.invalid/mqtt", "http"),
        ("wss://broker.example.invalid:8443/mqtt", None),
        ("broker.example.invalid:8443/mqtt", None),
        ("", None),
    ],
)
def test_scheme_classification(url, scheme):
    assert plaintext_mqtt_scheme(url) == scheme


@pytest.mark.parametrize(
    "url", ["ws://broker.example.invalid/mqtt", "tcp://broker.example.invalid:1883"]
)
def test_a_plaintext_cloud_url_is_refused_by_default(
    monkeypatch, make_camera_device_client, url
):
    monkeypatch.delenv("AIDOT_ALLOW_PLAINTEXT_MQTT", raising=False)
    dc = _client(monkeypatch, make_camera_device_client, url)
    assert asyncio.run(dc._async_get_mqtt_url()) is None
    # Nothing cached: neither the URL nor the password it would have carried.
    assert dc._mqtt_url is None
    assert dc._smarthome_auth is None
    assert dc._mqtt_url_refused_scheme == url.split(":", 1)[0]


def test_the_opt_in_keeps_the_old_behaviour(monkeypatch, make_camera_device_client):
    monkeypatch.setenv("AIDOT_ALLOW_PLAINTEXT_MQTT", "1")
    dc = _client(
        monkeypatch, make_camera_device_client, "ws://broker.example.invalid/m"
    )
    assert asyncio.run(dc._async_get_mqtt_url()) == "ws://broker.example.invalid/m"
    assert dc._mqtt_url_refused_scheme is None


@pytest.mark.parametrize(
    "url, expect",
    [
        (
            "wss://broker.example.invalid:8443/mqtt",
            "wss://broker.example.invalid:8443/mqtt",
        ),
        ("broker.example.invalid:8443/mqtt", "wss://broker.example.invalid:8443/mqtt"),
        ("", "wss://us-mqtt.arnoo.com:8443/mqtt"),
    ],
)
def test_tls_and_bare_urls_are_unchanged(
    monkeypatch, make_camera_device_client, url, expect
):
    monkeypatch.delenv("AIDOT_ALLOW_PLAINTEXT_MQTT", raising=False)
    dc = _client(monkeypatch, make_camera_device_client, url)
    assert asyncio.run(dc._async_get_mqtt_url()) == expect


def test_the_operator_seam_is_not_refused(monkeypatch, make_camera_device_client):
    """AIDOT_MQTT_URL is set by the operator (the e2e tier points it at a local
    ws:// broker), so the refusal of a cloud-supplied URL does not apply."""
    monkeypatch.delenv("AIDOT_ALLOW_PLAINTEXT_MQTT", raising=False)
    dc = make_camera_device_client("A001513")
    dc._mqtt_url = None
    monkeypatch.setenv("AIDOT_MQTT_URL", "ws://localhost:11883/mqtt")
    assert asyncio.run(dc._async_get_mqtt_url()) == "ws://localhost:11883/mqtt"


def _open_self(refused):
    async def _creds():
        return ("user", "pw", "app-1")

    async def _url():
        return None

    return types.SimpleNamespace(
        _mqtt_credentials=_creds,
        _async_get_mqtt_url=_url,
        user_id=1,
        _user_info={},
        _mqtt_url_refused_scheme=refused,
    )


def _empty_open_state():
    # The phase reads and writes the open's state object; every field
    # starts unset, as the locals of the open did.
    from aidot_cameras.camera.webrtc_open import _DtlsOpenState

    return _DtlsOpenState.__new__(_DtlsOpenState)


def test_the_open_raises_the_refusal():
    from aidot_cameras.camera.webrtc_open import _dtls_open_credentials_and_mqtt_setup

    with pytest.raises(AidotPlaintextMqttRefused) as ei:
        asyncio.run(
            _dtls_open_credentials_and_mqtt_setup(_open_self("ws"), _empty_open_state())
        )
    err = ei.value
    assert err.scheme == "ws"
    assert "AIDOT_ALLOW_PLAINTEXT_MQTT" in str(err)
    # Still everything an older caller caught.
    assert isinstance(err, (AidotMqttUnavailable, AidotStreamOpenError))
    assert isinstance(err, AidotError) and isinstance(err, RuntimeError)


def test_the_open_without_a_refusal_says_no_url():
    from aidot_cameras.camera.webrtc_open import _dtls_open_credentials_and_mqtt_setup

    with pytest.raises(AidotMqttUnavailable) as ei:
        asyncio.run(
            _dtls_open_credentials_and_mqtt_setup(_open_self(None), _empty_open_state())
        )
    assert not isinstance(ei.value, AidotPlaintextMqttRefused)


def test_a_repeated_refusal_is_logged_loudly_once(
    monkeypatch, make_camera_device_client, caplog
):
    """Nothing is cached, so every poll refetches; only the first refusal of a
    scheme is an ERROR, the rest are DEBUG."""
    import logging

    monkeypatch.delenv("AIDOT_ALLOW_PLAINTEXT_MQTT", raising=False)
    dc = _client(
        monkeypatch, make_camera_device_client, "ws://broker.example.invalid/m"
    )
    with caplog.at_level(logging.DEBUG):
        for _ in range(3):
            assert asyncio.run(dc._async_get_mqtt_url()) is None
    refusals = [r for r in caplog.records if "non-TLS MQTT broker" in r.getMessage()]
    assert [r.levelno for r in refusals] == [
        logging.ERROR,
        logging.DEBUG,
        logging.DEBUG,
    ]
