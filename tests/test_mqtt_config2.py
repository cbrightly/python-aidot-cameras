"""AIDOT_MQTT_CONFIG2: MQTT credentials from /v25/commons/mqttConfig2, as the app.

The request shape is pinned to what the Android app sends
(``FetchMqttConfigRepos.actualGetMqttConfigFromRemote``): a GET whose two
parameters travel as a query string (``UserApiStores.getHttp`` takes them as a
``@QueryMap``), with no body.  Everything runs against fake HTTP; no cloud.
"""

import asyncio
import logging
import os
import stat

import aiohttp
import pytest

from aidot_cameras import mqtt_config2
from aidot_cameras.client import AidotClient
from aidot_cameras.const import LOGIN_INFO_MQTT_CREDENTIALS_KEY as _REC
from aidot_cameras.device_client import CameraDeviceClient

_TOKEN = "TOKEN-SECRET-123"
_CONFIG2_URL = "https://prod-us-api.arnoo.com/v25/commons/mqttConfig2"
_USERCONFIG_URL = "https://prod-us-api.arnoo.com/commons/userConfig"

_GOOD_REPLY = {
    "code": 200,
    "data": {
        "host": "us-mqtt.example.com:8443",
        "clientId": "cfg2-client-0001-u1",
        "password": "CFG2-PASSWORD",
        "userId": "someone-else",
        "heartbeat": 60,
    },
}
_USERCONFIG_REPLY = {
    "code": 200,
    "data": {"mqttPassword": "UC-PASSWORD", "mqttClientId": "uc-client-u1"},
}


class _Resp:
    def __init__(self, body):
        self._body = body

    async def json(self, content_type=None):
        if isinstance(self._body, Exception):
            raise self._body
        return self._body

    async def __aenter__(self):
        return self

    async def __aexit__(self, *exc):
        return False


class _Session:
    """Records every GET and answers from a url -> reply map."""

    def __init__(self, replies):
        self.replies = replies
        self.calls = []

    def get(self, url, **kwargs):
        self.calls.append((url, kwargs))
        reply = self.replies[url]
        if isinstance(reply, Exception) and not isinstance(reply, ValueError):
            raise reply
        return _Resp(reply)

    def post(self, *a, **kw):  # pragma: no cover - must never be used
        raise AssertionError("mqttConfig2 is a GET")


def _client(session, login_info=None) -> AidotClient:
    c = AidotClient.__new__(AidotClient)
    c.login_info = login_info or {"id": "u1", "accessToken": _TOKEN}
    c.session = session
    c._region = "us"
    c._mqtt_config2_failure = None
    c._mqtt_credential_source = None
    c._mqtt_session_id = None
    return c


@pytest.fixture(autouse=True)
def _clean_env(monkeypatch):
    for name in ("AIDOT_MQTT_CONFIG2", "AIDOT_TOKEN_FILE", "AIDOT_API_BASE_TEMPLATE"):
        monkeypatch.delenv(name, raising=False)


@pytest.fixture
def on(monkeypatch):
    monkeypatch.setenv("AIDOT_MQTT_CONFIG2", "1")


# --------------------------------------------------------------------------- #
# The request, as the app sends it
# --------------------------------------------------------------------------- #


def test_request_shape_matches_the_app(on):
    s = _Session({_CONFIG2_URL: _GOOD_REPLY})
    c = _client(s)
    asyncio.run(c._async_fetch_user_config())

    assert len(s.calls) == 1  # userConfig not needed
    url, kw = s.calls[0]
    assert url == _CONFIG2_URL
    assert set(kw["params"]) == {"sessionType", "sessionId"}
    assert kw["params"]["sessionType"] == "App"
    assert mqtt_config2.is_valid_session_id(kw["params"]["sessionId"])
    # A query string, never a body.
    assert "json" not in kw and "data" not in kw
    hdrs = {k.lower(): v for k, v in kw["headers"].items()}
    assert hdrs["token"] == _TOKEN
    assert hdrs["terminal"] == "app"
    assert hdrs["appid"]


def test_session_id_shape():
    for _ in range(50):
        sid = mqtt_config2.new_session_id()
        assert len(sid) == 21
        assert set(sid) <= set(
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        )
    assert not mqtt_config2.is_valid_session_id("short")
    assert not mqtt_config2.is_valid_session_id("x" * 20 + "!")
    # The H5 bundle's own ids ([0-9a-z]) are a subset and stay valid.
    assert mqtt_config2.is_valid_session_id("0123456789abcdefghijk")


# --------------------------------------------------------------------------- #
# Field mapping
# --------------------------------------------------------------------------- #


def test_reply_fields_land_in_login_info(on):
    s = _Session({_CONFIG2_URL: _GOOD_REPLY})
    c = _client(s)
    asyncio.run(c._async_fetch_user_config())

    li = c.login_info
    assert li["mqttPassword"] == "CFG2-PASSWORD"
    assert li[_REC] == {
        "source": "mqttConfig2",
        "password": "CFG2-PASSWORD",
        "clientId": "cfg2-client-0001-u1",
        "host": "us-mqtt.example.com:8443",
    }
    # The app ignores the reply's userId (it overwrites it with its own); so do
    # we - the username stays the account id.
    assert li["id"] == "u1"
    # The record is runtime-only with its password, so nothing mqttConfig2
    # issued outlives it; the persisted mqttClientId is userConfig's alone.
    out = c.serializable_login_info()
    assert "mqttPassword" not in out and _REC not in out
    assert "mqttClientId" not in out


@pytest.mark.parametrize(
    "body",
    [
        {"code": 200, "data": {"host": "h", "clientId": "c", "password": "p"}},
        {"host": "h", "clientId": "c", "password": "p"},  # unwrapped
    ],
)
def test_parse_reply_accepts_wrapped_and_unwrapped(body):
    out = mqtt_config2.parse_reply(body)
    assert out["host"] == "h" and out["clientId"] == "c" and out["password"] == "p"


@pytest.mark.parametrize(
    "body",
    [
        None,
        "nope",
        [],
        {"code": 21026, "desc": "login again"},
        {"data": {"host": "", "clientId": "c", "password": "p"}},
        {"data": {"host": "h", "clientId": "", "password": "p"}},
        {"data": {"host": "h", "clientId": "c", "password": None}},
        {"data": {"host": 5, "clientId": "c", "password": "p"}},
    ],
)
def test_parse_reply_rejects_incomplete(body):
    assert mqtt_config2.parse_reply(body) is None


def test_broker_url():
    assert mqtt_config2.broker_url("h.example:8443") == "wss://h.example:8443/mqtt"
    assert mqtt_config2.broker_url("h.example:8443/") == "wss://h.example:8443/mqtt"
    assert mqtt_config2.broker_url("h.example/m") == "wss://h.example/m"
    assert mqtt_config2.broker_url("wss://h/mqtt") == "wss://h/mqtt"


# --------------------------------------------------------------------------- #
# Fallback
# --------------------------------------------------------------------------- #


@pytest.mark.parametrize(
    "bad",
    [
        aiohttp.ClientError("boom"),
        ValueError("not json"),  # raised from resp.json()
        {"code": 500, "desc": "server error"},
        {"code": 200, "data": {"host": "h", "password": "p"}},  # no clientId
    ],
)
def test_failure_falls_back_to_user_config(on, bad, caplog):
    s = _Session({_CONFIG2_URL: bad, _USERCONFIG_URL: _USERCONFIG_REPLY})
    c = _client(s)
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras"):
        asyncio.run(c._async_fetch_user_config())

    assert [u for u, _ in s.calls] == [_CONFIG2_URL, _USERCONFIG_URL]
    li = c.login_info
    assert li["mqttPassword"] == "UC-PASSWORD"
    assert li["mqttClientId"] == "uc-client-u1"
    assert li[_REC]["source"] == "userConfig" and li[_REC]["host"] is None
    info = [r.getMessage() for r in caplog.records if r.levelno == logging.INFO]
    assert any("MQTT credentials from userConfig" in m for m in info)


def test_fallback_drops_the_config2_client_id_and_host(on):
    # The config2 clientId and host must not be paired with a userConfig
    # password when userConfig names no clientId of its own.
    s = _Session({_CONFIG2_URL: _GOOD_REPLY})
    c = _client(s, {"id": "u1", "accessToken": _TOKEN, "mqttClientId": "uc-old"})
    asyncio.run(c._async_fetch_user_config())
    assert c.login_info[_REC]["clientId"] == "cfg2-client-0001-u1"

    s.replies[_CONFIG2_URL] = aiohttp.ClientError("down")
    s.replies[_USERCONFIG_URL] = {"code": 200, "data": {"mqttPassword": "UC"}}
    c.login_info.pop("mqttPassword")
    asyncio.run(c._async_fetch_user_config())
    assert c.login_info["mqttPassword"] == "UC"
    assert c.login_info[_REC] == {
        "source": "userConfig",
        "password": "UC",
        "clientId": "",
        "host": None,
    }
    assert c.login_info["mqttClientId"] == "uc-old"


# --------------------------------------------------------------------------- #
# The sessionId: generated once, reused
# --------------------------------------------------------------------------- #


def test_session_id_is_reused_for_the_client_lifetime(on):
    s = _Session({_CONFIG2_URL: _GOOD_REPLY})
    c = _client(s)
    asyncio.run(c._async_fetch_user_config())
    asyncio.run(c._async_fetch_user_config())
    sids = [kw["params"]["sessionId"] for _, kw in s.calls]
    assert len(sids) == 2 and sids[0] == sids[1]


def test_session_id_persists_next_to_the_token_file(on, tmp_path, monkeypatch):
    token = tmp_path / "token.json"
    monkeypatch.setenv("AIDOT_TOKEN_FILE", str(token))
    s1 = _Session({_CONFIG2_URL: _GOOD_REPLY})
    asyncio.run(_client(s1)._async_fetch_user_config())
    sid_file = tmp_path / "token.json.mqtt-session-id"
    assert sid_file.read_text() == s1.calls[0][1]["params"]["sessionId"]
    assert stat.S_IMODE(os.stat(sid_file).st_mode) == 0o600

    # A new client - a restart - reads the same id back.
    s2 = _Session({_CONFIG2_URL: _GOOD_REPLY})
    asyncio.run(_client(s2)._async_fetch_user_config())
    assert s2.calls[0][1]["params"]["sessionId"] == sid_file.read_text()


def test_a_malformed_session_id_file_is_replaced(tmp_path):
    path = tmp_path / "t.json.mqtt-session-id"
    path.write_text("not-a-valid-id")
    sid = mqtt_config2.load_or_create_session_id(str(path))
    assert mqtt_config2.is_valid_session_id(sid)
    assert path.read_text() == sid


def test_an_unwritable_session_id_dir_still_yields_an_id(tmp_path):
    path = tmp_path / "missing-dir" / "t.json.mqtt-session-id"
    sid = mqtt_config2.load_or_create_session_id(str(path))
    assert mqtt_config2.is_valid_session_id(sid)


def test_two_clients_without_a_token_file_get_different_ids(on):
    s = _Session({_CONFIG2_URL: _GOOD_REPLY})
    asyncio.run(_client(s)._async_fetch_user_config())
    asyncio.run(_client(s)._async_fetch_user_config())
    a, b = (kw["params"]["sessionId"] for _, kw in s.calls)
    assert a != b


# --------------------------------------------------------------------------- #
# Logging: once, at INFO, nothing secret
# --------------------------------------------------------------------------- #


def test_source_logged_once_and_redacted(on, caplog):
    s = _Session({_CONFIG2_URL: _GOOD_REPLY})
    c = _client(s)
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras"):
        asyncio.run(c._async_fetch_user_config())
        asyncio.run(c._async_fetch_user_config())
    info = [r.getMessage() for r in caplog.records if r.levelno == logging.INFO]
    src = [m for m in info if m.startswith("MQTT credentials from")]
    assert len(src) == 1 and "mqttConfig2" in src[0]
    sid = s.calls[0][1]["params"]["sessionId"]
    text = "\n".join(r.getMessage() for r in caplog.records)
    assert "CFG2-PASSWORD" not in text
    assert _TOKEN not in text
    assert "cfg2-client-0001-u1" not in text
    assert sid not in text


# --------------------------------------------------------------------------- #
# Off: nothing changes
# --------------------------------------------------------------------------- #


def test_off_by_default_makes_no_config2_call(caplog):
    s = _Session({_USERCONFIG_URL: _USERCONFIG_REPLY})
    c = _client(s)
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras"):
        asyncio.run(c._async_fetch_user_config())
    assert [u for u, _ in s.calls] == [_USERCONFIG_URL]
    assert "params" not in s.calls[0][1]
    assert c.login_info["mqttPassword"] == "UC-PASSWORD"
    assert c.login_info["mqttClientId"] == "uc-client-u1"
    assert c._mqtt_session_id is None
    assert not any(
        r.getMessage().startswith("MQTT credentials from") for r in caplog.records
    )


@pytest.mark.parametrize("value", ["0", "false", "", "off"])
def test_falsy_values_are_off(monkeypatch, value):
    monkeypatch.setenv("AIDOT_MQTT_CONFIG2", value)
    s = _Session({_USERCONFIG_URL: _USERCONFIG_REPLY})
    asyncio.run(_client(s)._async_fetch_user_config())
    assert [u for u, _ in s.calls] == [_USERCONFIG_URL]


def test_off_keeps_a_user_config_client_id_without_a_marker():
    # Off, a userConfig reply with no clientId leaves the stored one alone,
    # exactly as before.
    s = _Session({_USERCONFIG_URL: {"code": 200, "data": {"mqttPassword": "P"}}})
    c = _client(s, {"id": "u1", "accessToken": "AT", "mqttClientId": "kept"})
    asyncio.run(c._async_fetch_user_config())
    assert c.login_info["mqttClientId"] == "kept"


# --------------------------------------------------------------------------- #
# The camera side: the broker URL, and the srflx "ip" kept
# --------------------------------------------------------------------------- #


class _CamSession:
    def __init__(self, body):
        self.body = body
        self.urls = []

    def get(self, url, **kw):
        self.urls.append(url)
        return _Resp(self.body)

    async def __aenter__(self):
        return self

    async def __aexit__(self, *exc):
        return False


def _cfg2_record(password, host, client_id="cfg2-cid"):
    return {
        "source": "mqttConfig2",
        "password": password,
        "clientId": client_id,
        "host": host,
    }


def _camera(login_info):
    c = CameraDeviceClient.__new__(CameraDeviceClient)
    c._user_info = login_info
    c._smarthome_auth = None
    c._mqtt_credential_refresh_cb = None
    c._mqtt_url = None
    c._mqtt_url_refused_scheme = None
    c._mqtt_refused_at = 0.0
    c._region = "us"
    c.user_id = "u1"
    return c


def _patch_session(monkeypatch, body):
    sess = _CamSession(body)
    monkeypatch.setattr(aiohttp, "ClientSession", lambda *a, **k: sess)
    return sess


_SRV = {
    "code": 200,
    "data": {"mqttServerUrl": "wss://srv.example:8443/mqtt", "ip": "203.0.113.7"},
}


def test_camera_uses_the_config2_host_and_keeps_the_ip(on, monkeypatch):
    sess = _patch_session(monkeypatch, _SRV)
    li = {
        "id": "u1",
        "accessToken": "AT",
        "mqttPassword": "CFG2-PASSWORD",
        "mqttClientId": "uc-cid",
        _REC: _cfg2_record("CFG2-PASSWORD", "cfg2.example:8443"),
    }
    cam = _camera(li)
    url = asyncio.run(cam._async_get_mqtt_url())
    assert url == "wss://cfg2.example:8443/mqtt"
    assert cam._mqtt_client_id() == "cfg2-cid"
    assert any("getServerUrlConfig" in u for u in sess.urls)
    # The srflx source the SDES path reads.
    assert cam._smarthome_auth["raw"]["ip"] == "203.0.113.7"


def test_camera_ignores_a_host_without_its_password(on, monkeypatch):
    _patch_session(monkeypatch, _SRV)
    # A refusal popped the password; and a record whose password is not the
    # account's current one is just as stale.
    for li in (
        {"id": "u1", "accessToken": "AT", _REC: _cfg2_record("OLD", "stale")},
        {
            "id": "u1",
            "accessToken": "AT",
            "mqttPassword": "NEW",
            "mqttClientId": "uc-cid",
            _REC: _cfg2_record("OLD", "stale"),
        },
    ):
        cam = _camera(li)
        url = asyncio.run(cam._async_get_mqtt_url())
        assert url == "wss://srv.example:8443/mqtt"
        assert cam._mqtt_client_id() == li.get("mqttClientId")


def test_camera_off_ignores_the_host(monkeypatch):
    _patch_session(monkeypatch, _SRV)
    li = {
        "id": "u1",
        "accessToken": "AT",
        "mqttPassword": "P",
        _REC: _cfg2_record("P", "cfg2.example:8443"),
    }
    url = asyncio.run(_camera(li)._async_get_mqtt_url())
    assert url == "wss://srv.example:8443/mqtt"


# --------------------------------------------------------------------------- #
# The sessionId in login_info (no token file: Home Assistant)
# --------------------------------------------------------------------------- #


def test_session_id_round_trips_through_serializable_login_info(on):
    s = _Session({_CONFIG2_URL: _GOOD_REPLY})
    c1 = _client(s)
    asyncio.run(c1._async_fetch_user_config())
    sid = s.calls[0][1]["params"]["sessionId"]
    stored = c1.serializable_login_info()
    assert stored["mqttSessionId"] == sid

    # A restart: a new client built from what the integration stored.
    c2 = _client(s, dict(stored))
    asyncio.run(c2._async_fetch_user_config())
    assert s.calls[1][1]["params"]["sessionId"] == sid


def test_session_id_survives_a_relogin(on):
    s = _Session({_CONFIG2_URL: _GOOD_REPLY})
    c = _client(s)
    asyncio.run(c._async_fetch_user_config())
    sid = c.login_info["mqttSessionId"]
    # async_post_login assigns the login response, which has no sessionId.
    c.login_info = {"id": "u1", "accessToken": "NEW"}
    assert c.login_info["mqttSessionId"] == sid


def test_a_malformed_stored_session_id_is_replaced(on):
    s = _Session({_CONFIG2_URL: _GOOD_REPLY})
    c = _client(s, {"id": "u1", "accessToken": _TOKEN, "mqttSessionId": "bad!"})
    asyncio.run(c._async_fetch_user_config())
    sid = s.calls[0][1]["params"]["sessionId"]
    assert mqtt_config2.is_valid_session_id(sid)
    assert c.login_info["mqttSessionId"] == sid


def test_the_token_file_takes_precedence(on, tmp_path, monkeypatch):
    token = tmp_path / "token.json"
    monkeypatch.setenv("AIDOT_TOKEN_FILE", str(token))
    file_sid = "F" * 21
    (tmp_path / "token.json.mqtt-session-id").write_text(file_sid)
    s = _Session({_CONFIG2_URL: _GOOD_REPLY})
    li = {"id": "u1", "accessToken": _TOKEN, "mqttSessionId": "L" * 21}
    asyncio.run(_client(s, li)._async_fetch_user_config())
    assert s.calls[0][1]["params"]["sessionId"] == file_sid


def test_stored_session_id_is_never_logged(on, caplog):
    sid = "S3ss10n1dV4lue0000abc"
    s = _Session({_CONFIG2_URL: _GOOD_REPLY})
    c = _client(s, {"id": "u1", "accessToken": _TOKEN, "mqttSessionId": sid})
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras"):
        asyncio.run(c._async_fetch_user_config())
    assert s.calls[0][1]["params"]["sessionId"] == sid
    assert sid not in "\n".join(r.getMessage() for r in caplog.records)


def test_user_config_client_id_log_is_masked(caplog):
    s = _Session({_USERCONFIG_URL: _USERCONFIG_REPLY})
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras"):
        asyncio.run(_client(s)._async_fetch_user_config())
    text = "\n".join(r.getMessage() for r in caplog.records)
    assert "mqttClientId stored" in text
    assert "uc-client-u1" not in text


# --------------------------------------------------------------------------- #
# The fallback URL takes the same plaintext gate as the main path
# --------------------------------------------------------------------------- #


class _FailingCamSession(_CamSession):
    def get(self, url, **kw):
        self.urls.append(url)
        raise aiohttp.ClientError("getServerUrlConfig down")


def _cfg2_camera(host):
    li = {
        "id": "u1",
        "accessToken": "AT",
        "mqttPassword": "P",
        _REC: _cfg2_record("P", host),
    }
    cam = _camera(li)
    cam._leedarson_headers = lambda: {}
    return cam


def test_fallback_uses_the_config2_url_when_server_url_config_fails(on, monkeypatch):
    monkeypatch.setattr(
        aiohttp, "ClientSession", lambda *a, **k: _FailingCamSession(None)
    )
    cam = _cfg2_camera("cfg2.example:8443")
    assert asyncio.run(cam._async_get_mqtt_url()) == "wss://cfg2.example:8443/mqtt"
    assert cam._mqtt_url == "wss://cfg2.example:8443/mqtt"


def test_a_plaintext_config2_fallback_is_refused_and_logged(on, monkeypatch, caplog):
    monkeypatch.delenv("AIDOT_ALLOW_PLAINTEXT_MQTT", raising=False)
    monkeypatch.setattr(
        aiohttp, "ClientSession", lambda *a, **k: _FailingCamSession(None)
    )
    cam = _cfg2_camera("ws://cfg2.example/mqtt")
    with caplog.at_level(logging.ERROR):
        assert asyncio.run(cam._async_get_mqtt_url()) is None
    assert cam._mqtt_url is None and cam._mqtt_url_refused_scheme == "ws"
    assert any(
        "mqttConfig2 named a non-TLS MQTT broker" in r.getMessage()
        for r in caplog.records
    )


def test_a_plaintext_config2_fallback_takes_the_opt_in(on, monkeypatch):
    monkeypatch.setenv("AIDOT_ALLOW_PLAINTEXT_MQTT", "1")
    monkeypatch.setattr(
        aiohttp, "ClientSession", lambda *a, **k: _FailingCamSession(None)
    )
    cam = _cfg2_camera("ws://cfg2.example/mqtt")
    assert asyncio.run(cam._async_get_mqtt_url()) == "ws://cfg2.example/mqtt"
    assert cam._mqtt_url_refused_scheme is None


def test_the_two_fetches_run_concurrently(on, monkeypatch):
    # The credential fetch mqttConfig2 needs and getServerUrlConfig are
    # independent, so neither waits for the other.
    order = []
    sess = _patch_session(monkeypatch, _SRV)
    real_get = sess.get

    def get(url, **kw):
        order.append("server")
        return real_get(url, **kw)

    sess.get = get
    li = {"id": "u1", "accessToken": "AT"}
    cam = _camera(li)

    async def refresh():
        order.append("cred-start")
        await asyncio.sleep(0.01)
        li["mqttPassword"] = "P"
        li[_REC] = _cfg2_record("P", "cfg2.example:8443")
        order.append("cred-end")

    cam._mqtt_credential_refresh_cb = refresh
    url = asyncio.run(cam._async_get_mqtt_url())
    assert url == "wss://cfg2.example:8443/mqtt"
    assert order == ["cred-start", "server", "cred-end"]


def test_mask_identifier_lives_with_the_redaction():
    from aidot_cameras._log_redaction import mask_identifier

    assert mask_identifier(None) == "<none>"
    assert mask_identifier("abcdefgh") == "<8 chars>"
    assert mask_identifier("cfg2-client-0001-u1") == "...1-u1 (19 chars)"
    assert not hasattr(mqtt_config2, "mask")


def test_the_private_write_leaves_the_old_file_on_failure(tmp_path):
    from aidot_cameras._private_file import write_private_file

    path = tmp_path / "f"
    write_private_file(
        str(path), lambda fh: fh.write("one"), prefix=".t-", encoding="ascii"
    )
    assert path.read_text() == "one"
    assert stat.S_IMODE(os.stat(path).st_mode) == 0o600

    def boom(fh):
        fh.write("partial")
        raise TypeError("not serializable")

    with pytest.raises(TypeError):
        write_private_file(str(path), boom, prefix=".t-", encoding="ascii")
    assert path.read_text() == "one"
    assert sorted(p.name for p in tmp_path.iterdir()) == ["f"]
