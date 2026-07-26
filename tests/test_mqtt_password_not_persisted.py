"""The MQTT password must never reach persistent storage.

It is a cache: the broker issues a new one on every account login and allows a
single connection, so a stored copy is stale as soon as anything else logs in.
Persisting it made a credential failure survive restarts, because the credential
fetch prefers the cached value.
"""
from aidot_cameras.const import (
    LOGIN_INFO_MQTT_PASSWORD_KEYS,
    RUNTIME_ONLY_LOGIN_INFO_KEYS,
)


def test_mqtt_password_keys_are_runtime_only():
    for key in LOGIN_INFO_MQTT_PASSWORD_KEYS:
        assert key in RUNTIME_ONLY_LOGIN_INFO_KEYS, key


def test_serializable_login_info_omits_the_mqtt_password():
    from aidot_cameras.client import CameraClient

    client = CameraClient.__new__(CameraClient)   # no network/session needed
    client.login_info = {
        "id": "u1",
        "accessToken": "AT",
        "refreshToken": "RT",
        "mqttPassword": "SHOULD-NOT-PERSIST",
        "mqttPwd": "ALSO-NOT",
        "_persistent_mqtt": object(),
        "_persistent_mqtt_lock": object(),
    }
    out = CameraClient.serializable_login_info(client)
    assert "mqttPassword" not in out
    assert "mqttPwd" not in out
    assert "_persistent_mqtt" not in out
    assert out["accessToken"] == "AT"   # real state still persists
