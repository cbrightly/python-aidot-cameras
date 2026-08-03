"""A restart must not resurrect a credential the broker has already retired.

The worst-lasting outage in this repo's history was a restart-survival bug,
and restart survival is the one thing the suite never modelled: the MQTT
password was persisted into the config entry, so every start reloaded a stale
one, the broker refused it forever (rc=134), and camera signaling stayed dead
while snapshots kept working - so it read as a streaming bug. An in-memory
clear was shipped first and was simply undone by the next restart.

These tests do the full cycle - build state, persist it the way a consumer
does, drop the process, reload from disk only - and assert what has to be
true on the far side.
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
)


def _live_client() -> AidotClient:
    """A client whose login_info looks like a running session's."""
    c = AidotClient.__new__(AidotClient)
    c.login_info = {
        "id": "u1",
        "accessToken": "access-tok",
        "refreshToken": "refresh-tok",
        "region": "us",
        "mqttClientId": "app-u1",
        # Cached from this session's login - rotated by the broker on the next
        # one, so it is valid now and worthless after a restart.
        "mqttPassword": "rotates-every-login",
        # Live runtime objects stashed on the shared dict.
        LOGIN_INFO_PERSISTENT_MQTT_LOCK_KEY: asyncio.Lock(),
        LOGIN_INFO_PERSISTENT_MQTT_KEY: object(),
    }
    return c


def _persist_and_reload(client, tmp_path) -> dict:
    """Round-trip through disk exactly as a consumer's storage would."""
    path = tmp_path / "config_entry.json"
    path.write_text(json.dumps(client.serializable_login_info()))
    return json.loads(path.read_text())


def test_persisted_state_carries_no_mqtt_password(tmp_path):
    reloaded = _persist_and_reload(_live_client(), tmp_path)
    for key in LOGIN_INFO_MQTT_PASSWORD_KEYS:
        assert key not in reloaded, (
            f"{key} survived a restart cycle - the broker rotates it on every "
            "login, so a reloaded copy is refused forever (rc=134) and camera "
            "signaling never comes back"
        )


def test_persisted_state_carries_no_live_runtime_objects(tmp_path):
    reloaded = _persist_and_reload(_live_client(), tmp_path)
    assert LOGIN_INFO_PERSISTENT_MQTT_KEY not in reloaded
    assert LOGIN_INFO_PERSISTENT_MQTT_LOCK_KEY not in reloaded


def test_restart_keeps_the_state_that_must_survive(tmp_path):
    """Stripping secrets must not throw away the tokens a restart needs."""
    reloaded = _persist_and_reload(_live_client(), tmp_path)
    for key in ("id", "accessToken", "refreshToken", "region", "mqttClientId"):
        assert reloaded.get(key), f"{key} must survive a restart"


def test_a_second_restart_is_still_clean(tmp_path):
    """The clear must be structural, not a one-shot.

    The first attempt at this bug cleared the password in memory; the next
    restart reloaded it from disk and the outage came straight back.
    """
    first = _persist_and_reload(_live_client(), tmp_path)

    # Rehydrate a client from disk, as a restart does, and persist again.
    second_boot = AidotClient.__new__(AidotClient)
    second_boot.login_info = dict(first)
    # A fresh login during this run caches a new password on the shared dict.
    second_boot.login_info["mqttPassword"] = "newly-issued-this-boot"

    second = _persist_and_reload(second_boot, tmp_path)
    for key in LOGIN_INFO_MQTT_PASSWORD_KEYS:
        assert key not in second, (
            f"{key} leaked back into storage on the second cycle"
        )


def test_persisted_state_is_json_round_trippable(tmp_path):
    """Whatever we hand a consumer must survive its storage layer verbatim."""
    client = _live_client()
    reloaded = _persist_and_reload(client, tmp_path)
    assert reloaded == json.loads(json.dumps(reloaded))
