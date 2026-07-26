"""The other half of the never-persist contract: it must be re-FETCHED on demand.

Not persisting the MQTT password is only safe if something fetches a fresh one
when it is missing.  Nothing did: ``_async_fetch_user_config`` had a single
caller, ``async_post_login``, and a restart from a stored token never calls that
(the proactive refresh takes the ``refresh_token()`` path and returns early).  So
after the password stopped being written to disk, a restart left login_info with
none at all.

That failed silently, which is why it needs a test rather than a log check:
``_async_get_smarthome_auth`` strategy 4 hands back the ACCESS TOKEN as an MQTT
password, so the Python side looks fine and the only symptom is the broker
refusing every connect (rc=134) - no WebRTC signaling, therefore no live video,
while snapshots keep working.
"""
import asyncio

import pytest

from aidot_cameras.device_client import CameraDeviceClient


def _device_client(login_info: dict) -> CameraDeviceClient:
    """A camera device client with just enough state for the credential path."""
    c = CameraDeviceClient.__new__(CameraDeviceClient)
    c._user_info = login_info          # THE account-shared dict, by identity
    c._smarthome_auth = None
    c._mqtt_credential_refresh_cb = None
    c._mqtt_url = "wss://broker.example/mqtt"   # keeps strategy 3 off the network
    c.user_id = "u1"
    return c


def test_stored_token_start_fetches_before_falling_back_to_the_access_token():
    # The regression: login_info as reconstructed from storage has no password.
    login_info = {"id": "u1", "accessToken": "AT", "refreshToken": "RT"}
    client = _device_client(login_info)

    calls = []

    async def _fetch():
        calls.append(1)
        login_info["mqttPassword"] = "FRESH"

    client.set_mqtt_credential_refresh_cb(_fetch)
    auth = asyncio.run(client._async_get_smarthome_auth())

    assert calls == [1], "no fetch was attempted"
    assert auth is not None
    assert auth["mqttPassword"] == "FRESH"
    # The point of the fetch: never silently ship the access token as the
    # broker password (strategy 4), which the broker refuses.
    assert auth["mqttPassword"] != "AT"


def test_no_fetch_when_the_password_is_already_present():
    login_info = {"id": "u1", "accessToken": "AT", "mqttPassword": "CURRENT"}
    client = _device_client(login_info)

    calls = []

    async def _fetch():
        calls.append(1)

    client.set_mqtt_credential_refresh_cb(_fetch)
    auth = asyncio.run(client._async_get_smarthome_auth())

    assert calls == []                              # no needless rotation
    assert auth["mqttPassword"] == "CURRENT"


def test_a_failing_fetch_degrades_instead_of_raising():
    # Every caller has fallbacks; a failed fetch must not break a stream open.
    login_info = {"id": "u1", "accessToken": "AT"}
    client = _device_client(login_info)

    async def _fetch():
        raise RuntimeError("userConfig unreachable")

    client.set_mqtt_credential_refresh_cb(_fetch)
    auth = asyncio.run(client._async_get_smarthome_auth())
    assert auth is not None and auth["mqttPassword"] == "AT"   # strategy 4


def test_a_siblings_stale_cache_loses_to_the_shared_login_info():
    # The per-client cache is per device but the credential is per account: when
    # a refusal on camera A replaced the account password, camera B still holds
    # the dead one.  Trusting B's cache would refuse again on every camera in
    # turn, so the shared dict wins any disagreement.
    login_info = {"id": "u1", "accessToken": "AT", "mqttPassword": "FRESH"}
    client = _device_client(login_info)
    client._smarthome_auth = {
        "mqttUser": "u1", "mqttPassword": "DEAD", "userId": "u1", "raw": {},
    }

    async def _fetch():   # must not be needed - login_info already has one
        raise AssertionError("fetched despite a usable shared password")

    client.set_mqtt_credential_refresh_cb(_fetch)
    auth = asyncio.run(client._async_get_smarthome_auth())
    assert auth["mqttPassword"] == "FRESH"


def test_a_cache_with_no_shared_counterpart_is_still_honoured():
    # Strategies 2 and 4 are per client and write nothing to login_info; their
    # results must not be thrown away on every call.
    login_info = {"id": "u1", "accessToken": "AT"}
    client = _device_client(login_info)
    client._smarthome_auth = {
        "mqttUser": "u1", "mqttPassword": "FROM-GETUSER", "userId": "u1", "raw": {},
    }

    async def _fetch():
        raise AssertionError("re-fetched a per-client credential")

    client.set_mqtt_credential_refresh_cb(_fetch)
    auth = asyncio.run(client._async_get_smarthome_auth())
    assert auth["mqttPassword"] == "FROM-GETUSER"


@pytest.mark.asyncio
async def test_concurrent_callers_coalesce_into_one_fetch():
    # Only the _get_persistent_mqtt path holds a lock; the per-op publish and
    # stream-open paths do not.  Concurrent fetches would each rotate the
    # credential out from under the others.
    from aidot_cameras.client import CameraClient

    account = CameraClient.__new__(CameraClient)
    account._user_config_inflight = None
    fetches = []

    async def _fetch_user_config():
        fetches.append(1)
        await asyncio.sleep(0)      # yield, so the racers all pile up

    account._async_fetch_user_config = _fetch_user_config

    await asyncio.gather(*(account.async_ensure_mqtt_credential() for _ in range(5)))
    assert fetches == [1]
    # The slot is released, so a later genuine need still fetches.
    await account.async_ensure_mqtt_credential()
    assert fetches == [1, 1]


@pytest.mark.asyncio
async def test_a_failed_shared_fetch_propagates_to_every_joiner():
    # A joiner must not conclude "a password is now present" when the flight it
    # waited on failed.
    from aidot_cameras.client import CameraClient

    account = CameraClient.__new__(CameraClient)
    account._user_config_inflight = None

    async def _fetch_user_config():
        await asyncio.sleep(0)
        raise RuntimeError("userConfig 500")

    account._async_fetch_user_config = _fetch_user_config

    results = await asyncio.gather(
        *(account.async_ensure_mqtt_credential() for _ in range(3)),
        return_exceptions=True,
    )
    assert all(isinstance(r, RuntimeError) for r in results), results
    assert account._user_config_inflight is None


def test_a_sibling_cache_that_came_from_login_info_is_treated_as_stale():
    # The shape that made the self-heal N-times-broken: a refusal on camera A
    # pops the shared password but can only null A's cache, so camera B holds the
    # dead one WITH an empty login_info.  If that counts as "nothing to disagree
    # with", B rebuilds its client with the dead password and is refused too -
    # once per camera, and an unexercised camera keeps the poison indefinitely.
    login_info = {"id": "u1", "accessToken": "AT"}      # password just popped
    client = _device_client(login_info)
    client._smarthome_auth = {
        "mqttUser": "u1", "mqttPassword": "DEAD", "userId": "u1",
        "raw": {"source": "login_info.mqttPassword"},   # the discriminator
    }

    async def _fetch():
        login_info["mqttPassword"] = "FRESH"

    client.set_mqtt_credential_refresh_cb(_fetch)
    auth = asyncio.run(client._async_get_smarthome_auth())
    assert auth["mqttPassword"] == "FRESH"


def test_the_refetch_floor_stops_a_hammering_loop():
    # A second refusal in quick succession means refetching is not helping.
    login_info = {"id": "u1", "accessToken": "AT"}
    client = _device_client(login_info)
    calls = []

    async def _fetch():
        calls.append(1)

    client.set_mqtt_credential_refresh_cb(_fetch)

    async def _drive():
        loop = asyncio.get_running_loop()
        client._mqtt_refused_at = loop.time()     # refused just now
        await client._async_refresh_mqtt_credential()
        assert calls == []                        # floored
        client._mqtt_refused_at = loop.time() - 3600   # long past
        await client._async_refresh_mqtt_credential()
        assert calls == [1]                       # allowed again

    asyncio.run(_drive())


def test_the_user_config_blob_kept_for_client_id_carries_no_password():
    # login_info["_userConfigRaw"] is NOT a runtime-only key, so anything left in
    # it reaches the config entry - one level below the key that was stripped.
    from aidot_cameras.client import _without_mqtt_password

    out = _without_mqtt_password({
        "mqttPassword": "P", "mqqtPwd": "P", "mqttPwd": "P",
        "mqttClientId": "app-u1",
        "mqtt": {"password": "P", "clientId": "app-u1"},
    })
    assert "mqttPassword" not in out and "mqqtPwd" not in out
    assert "mqttPwd" not in out
    assert out["mqttClientId"] == "app-u1"          # the only value read back
    assert "password" not in out["mqtt"]
    assert out["mqtt"]["clientId"] == "app-u1"
    assert "P" not in repr(out)


def test_a_refused_persistent_client_stops_reconnecting_and_releases_waiters():
    # paho was told reconnect_delay_set(max_delay=30), so a refused client keeps
    # re-offering the dead password for the life of the process - silently, since
    # the report latches.  And a caller waiting on it must not burn its full
    # timeout for a connection that can never happen.
    from aidot_cameras.camera.protocol import _PersistentMqtt

    pm = _PersistentMqtt("wss://b.example:8443/mqtt", "u1", "DEAD", "app-u1")
    assert not pm._auth_refused.is_set()

    fired = []
    pm._on_auth_failure = fired.append
    pm._on_connect(None, None, None, 134)     # MQTT5 "not authorized"

    assert fired == [134]
    assert pm._auth_refused.is_set()
    assert not pm._connected.is_set()         # nothing may publish into it

    pm.retire()
    assert pm._retired
    assert pm._on_auth_failure is None        # cannot re-fire from a retired one
    # A waiter now returns immediately instead of blocking for the timeout, and
    # crucially without rebuilding: retire() nulls _client, which on its own
    # looks exactly like "never started" and would reconnect with the dead
    # password.
    import time
    started = time.monotonic()
    assert pm._ensure_started_sync(timeout=5.0) is False
    assert time.monotonic() - started < 1.0
    assert pm._client is None
