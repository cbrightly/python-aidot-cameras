"""Regression test for a live bug: AidotClient.login_info doubles as the
account-shared cache for the persistent-MQTT connection and its guarding
asyncio.Lock (camera/client.py's _get_persistent_mqtt), so anything that
json.dumps login_info directly - once a persistent MQTT connection has been
created (the default since 2026-06-17) - hits
"TypeError: Object of type Lock is not JSON serializable".

Confirmed live (2026-07-07): a standalone aidot-go2rtc run's token-refresh
callback threw exactly this while persisting client.login_info to disk.
serializable_login_info() is the fix: a JSON-safe view that excludes the
known runtime-only keys.
"""
import asyncio
import json
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.client import AidotClient
from aidot_cameras.const import (
    LOGIN_INFO_MQTT_PASSWORD_KEYS,
    LOGIN_INFO_PERSISTENT_MQTT_KEY,
    LOGIN_INFO_PERSISTENT_MQTT_LOCK_KEY,
    RUNTIME_ONLY_LOGIN_INFO_KEYS,
)


def _client_with_login_info(extra: dict) -> AidotClient:
    c = AidotClient.__new__(AidotClient)
    c.login_info = {
        "id": "u1",
        "accessToken": "tok",
        "refreshToken": "rtok",
        "mqttPassword": "mp",
        **extra,
    }
    return c


def test_serializable_login_info_strips_runtime_keys():
    c = _client_with_login_info({
        LOGIN_INFO_PERSISTENT_MQTT_LOCK_KEY: asyncio.Lock(),
        LOGIN_INFO_PERSISTENT_MQTT_KEY: object(),  # stand-in for a live _PersistentMqtt
    })
    result = c.serializable_login_info()
    assert LOGIN_INFO_PERSISTENT_MQTT_KEY not in result
    assert LOGIN_INFO_PERSISTENT_MQTT_LOCK_KEY not in result
    # The MQTT password is a rotating cache, not state, so it is stripped too -
    # persisting it made a stale credential survive restarts.  Real account
    # state survives unchanged.
    assert result == {
        "id": "u1", "accessToken": "tok", "refreshToken": "rtok",
    }


def test_serializable_login_info_is_actually_json_dumpable():
    # The exact failure mode this fixes: json.dumps on the raw dict must
    # raise, but on the filtered view it must not.
    c = _client_with_login_info({
        LOGIN_INFO_PERSISTENT_MQTT_LOCK_KEY: asyncio.Lock(),
    })
    try:
        json.dumps(c.login_info)
        raise AssertionError("expected TypeError serializing the raw login_info")
    except TypeError:
        pass
    json.dumps(c.serializable_login_info())  # must not raise


def test_serializable_login_info_keeps_real_state_when_no_runtime_keys_present():
    # Common case (no persistent-MQTT connection created yet): only the cached
    # MQTT password is dropped; every piece of real account state survives.
    c = _client_with_login_info({})
    result = c.serializable_login_info()
    assert result == {"id": "u1", "accessToken": "tok", "refreshToken": "rtok"}
    for key in LOGIN_INFO_MQTT_PASSWORD_KEYS:
        assert key not in result


def test_runtime_only_keys_constant_matches_the_key_names_written():
    # Locks the literal key names camera/client.py actually writes - the
    # persistent-MQTT client and lock (_get_persistent_mqtt) plus the cached
    # MQTT password - against the shared frozenset, so a rename on one side
    # without the other cannot silently reopen either bug.
    assert RUNTIME_ONLY_LOGIN_INFO_KEYS == {
        LOGIN_INFO_PERSISTENT_MQTT_KEY, LOGIN_INFO_PERSISTENT_MQTT_LOCK_KEY,
        *LOGIN_INFO_MQTT_PASSWORD_KEYS,
    }
