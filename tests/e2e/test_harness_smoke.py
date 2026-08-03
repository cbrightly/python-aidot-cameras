"""The fake environment itself must work before anything is asserted on it.

If these fail, every other e2e result is meaningless - so they run first and
say plainly which leg of the harness broke.
"""
import asyncio

import pytest

from tests.e2e.fakes.signaling import FakeCameraSignaling

pytestmark = [pytest.mark.e2e, pytest.mark.timeout(60)]


async def test_broker_accepts_the_library_paho_config(fake_broker):
    """paho, configured exactly as protocol.py does, must connect and pub/sub."""
    import json
    import threading
    from urllib.parse import urlparse

    import paho.mqtt.client as paho

    parsed = urlparse(fake_broker.url)
    got: list = []
    done = threading.Event()

    client = paho.Client(
        callback_api_version=paho.CallbackAPIVersion.VERSION2,
        client_id="harness-smoke",
        transport="websockets",
    )
    client.ws_set_options(path=parsed.path)
    client.username_pw_set("any-user", "any-pass")

    rcs: list = []

    def on_connect(c, _u, _f, rc, _p=None):
        # paho >=2 hands back a ReasonCode, <2 an int - same normalization the
        # library does in protocol.py.  Never raise in a paho callback: it kills
        # the network thread and the failure surfaces as an unrelated timeout.
        try:
            rcs.append(int(rc))
        except (TypeError, ValueError):
            rcs.append(getattr(rc, "value", -1))
        c.subscribe("iot/v1/s/user-1/IPC/#")
        c.publish("iot/v1/s/user-1/IPC/livePlayReq", json.dumps({"method": "livePlayReq"}))

    def on_message(_c, _u, msg):
        got.append((msg.topic, msg.payload))
        done.set()

    client.on_connect, client.on_message = on_connect, on_message
    # connect_async, not connect(): the fake broker shares this test's event
    # loop, so a blocking handshake here would deadlock against it.
    client.connect_async(parsed.hostname, parsed.port, keepalive=30)
    client.loop_start()
    try:
        for _ in range(300):          # yield to the loop so the broker can run
            if done.is_set():
                break
            await asyncio.sleep(0.05)
    finally:
        client.loop_stop()
        client.disconnect()

    assert rcs == [0], f"fake broker refused the library's paho config: rc={rcs}"
    assert got, "no message round-tripped through the fake broker"
    assert got[0][0] == "iot/v1/s/user-1/IPC/livePlayReq"


async def test_cloud_serves_the_endpoints_the_client_calls(fake_cloud):
    import aiohttp

    async with aiohttp.ClientSession() as s:
        async with s.get(
            f"{fake_cloud.base_url}/commonController/getServerUrlConfig"
        ) as r:
            body = await r.json()
    assert body["data"]["mqttServerUrl"].startswith("ws://127.0.0.1:")
    assert body["data"]["ip"]
    assert fake_cloud.count("getServerUrlConfig") == 1


async def test_seams_redirect_the_real_client(
    e2e_device_client, fake_cloud, fake_api, fake_broker
):
    """The env seams must make a REAL device client address the fakes.

    smarthome and platform-API are separate hosts in production and separate
    fakes here, so this also pins that the client keeps them apart.
    """
    dc = e2e_device_client("A001513")
    assert dc._smarthome_base == fake_cloud.base_url
    assert dc._aidot_v21_base == f"{fake_api.base_url}/v21"
    assert dc._aidot_v32_base == f"{fake_api.base_url}/v32/api/ipc"
    assert await dc._async_get_mqtt_url() == fake_broker.url


async def test_fake_camera_answers_liveplay_on_peerid(fake_broker):
    """livePlayResp must echo the peerid - the client matches on it, not devId."""
    import json
    import threading
    from urllib.parse import urlparse

    import paho.mqtt.client as paho

    cam = FakeCameraSignaling(fake_broker.url, device_id="dev-1", user_id="user-1")
    await cam.start()
    try:
        parsed = urlparse(fake_broker.url)
        replies: list = []
        got = threading.Event()

        app = paho.Client(
            callback_api_version=paho.CallbackAPIVersion.VERSION2,
            client_id="harness-app", transport="websockets",
        )
        app.ws_set_options(path=parsed.path)

        def on_connect(c, _u, _f, _rc, _p=None):
            c.subscribe("iot/v1/c/user-1/#")

        def on_message(_c, _u, msg):
            replies.append(json.loads(msg.payload.decode()))
            got.set()

        app.on_connect, app.on_message = on_connect, on_message
        app.connect_async(parsed.hostname, parsed.port, keepalive=30)
        app.loop_start()
        try:
            await asyncio.sleep(0.5)  # let the subscription land
            app.publish("iot/v1/s/user-1/IPC/livePlayReq", json.dumps({
                "method": "livePlayReq", "devId": "dev-1",
                "payload": {"peerid": "abc_123_2_0_1", "devId": "dev-1"},
            }))
            for _ in range(300):
                if got.is_set():
                    break
                await asyncio.sleep(0.05)
        finally:
            app.loop_stop()
            app.disconnect()

        assert replies, "fake camera did not answer livePlayReq"
        resp = replies[0]
        assert resp["method"] == "livePlayResp"
        assert resp["payload"]["peerid"] == "abc_123_2_0_1", (
            "livePlayResp must echo OUR peerid - the client keys on it"
        )
    finally:
        cam.stop()
