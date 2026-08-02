"""Unit tests for the BLE-mesh hub relay (no hub or mesh device required)."""

import asyncio
import json
import os
import struct
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import pytest

from aidot_cameras.ble_gateway import (
    BleMeshError,
    BleMeshGatewayClient,
    BleMeshHubSession,
    _pack_rgbw,
    _unpack_rgbw,
    close_all_hub_sessions,
    find_ble_mesh_hubs,
    get_hub_session,
    hub_id_of,
    is_ble_mesh_child,
)
from aidot_cameras.crypto import aes_decrypt, aes_encrypt

HUB = {
    "id": "hub1",
    "type": "BleMesh_Hub",
    "modelId": "LK.Mini_hub.A000026",
    "aesKey": ["h" * 16],
    "password": "hubpw",
    "properties": {"ipAddress": "192.0.2.20"},
}

CHILD = {
    "id": "bulb1",
    "type": "light",
    "modelId": "LK.light.A001497",
    "mac": "aa:bb:cc:dd:ee:ff",
    "name": "Mesh Bulb",
    "online": True,
    "directGateway": "hub1",
    "bleMeshDeviceKey": "deadbeef",
    "properties": {"OnOff": "1", "Dimming": "40", "CCT": "3000", "RGBW": "0"},
    "product": {
        "serviceModules": [
            {"identity": "control.onoff"},
            {"identity": "control.light.dimming"},
            {"identity": "control.light.rgbw"},
            {
                "identity": "control.light.cct",
                "properties": [
                    {"identity": "CCT", "minValue": 2000, "maxValue": 6500}
                ],
            },
        ]
    },
}

KEY = bytes(("h" * 16).encode())


# --------------------------------------------------------------------------- #
# Cloud-record predicates
# --------------------------------------------------------------------------- #


def test_find_ble_mesh_hubs_picks_only_ble_hubs():
    zigbee = {"id": "zb", "type": "Mini_hub"}
    hubs = find_ble_mesh_hubs([HUB, CHILD, zigbee])
    assert set(hubs) == {"hub1"}


def test_hub_id_of_reads_either_field_and_ignores_self_reference():
    assert hub_id_of(CHILD) == "hub1"
    assert hub_id_of({"id": "x", "parentId": "hub1"}) == "hub1"
    # A direct device points directId/parentId at itself or leaves them empty.
    assert hub_id_of({"id": "x", "directGateway": "", "parentId": ""}) == ""
    assert hub_id_of({"id": "x", "parentId": "x"}) == ""


def test_is_ble_mesh_child_requires_hub_and_mesh_addressing():
    hubs = find_ble_mesh_hubs([HUB])
    assert is_ble_mesh_child(CHILD, hubs) is True
    # Pointing at a hub is not enough on its own: Zigbee children and mere
    # house-mates do that too, and a mesh client for one controls nothing.
    no_mesh = dict(CHILD)
    no_mesh.pop("bleMeshDeviceKey")
    assert is_ble_mesh_child(no_mesh, hubs) is False
    # bleMeshAddr is accepted as the alternative corroboration.
    alt = {**no_mesh, "bleMeshAddr": "32699"}
    assert is_ble_mesh_child(alt, hubs) is True
    # Unknown hub id.
    assert is_ble_mesh_child({**CHILD, "directGateway": "nope"}, hubs) is False


def test_direct_wifi_light_is_not_a_mesh_child():
    hubs = find_ble_mesh_hubs([HUB])
    direct = {"id": "w1", "type": "light", "directGateway": "", "directId": "w1"}
    assert is_ble_mesh_child(direct, hubs) is False


# --------------------------------------------------------------------------- #
# RGBW packing
# --------------------------------------------------------------------------- #


def test_rgbw_round_trips_through_signed_packing():
    for rgbw in [(0, 0, 0, 0), (255, 255, 255, 255), (255, 128, 0, 64), (12, 34, 56, 78)]:
        assert _unpack_rgbw(_pack_rgbw(rgbw)) == rgbw


def test_rgbw_unpacks_negative_cloud_values():
    # Any colour with red > 0x7F arrives as a negative signed int.
    packed = _pack_rgbw((255, 0, 0, 0))
    assert packed < 0
    assert _unpack_rgbw(packed) == (255, 0, 0, 0)


# --------------------------------------------------------------------------- #
# Client construction
# --------------------------------------------------------------------------- #


def test_client_derives_hub_credentials_and_capabilities():
    c = BleMeshGatewayClient(CHILD, HUB, "user1")
    assert c.device_id == "bulb1"
    assert c._hub_ip == "192.0.2.20"
    assert len(c._key) == 16 and c._key.startswith(b"h" * 16)
    assert c.info.enable_rgbw and c.info.enable_cct and c.info.enable_dimming
    assert (c.info.cct_min, c.info.cct_max) == (2000, 6500)


def test_client_seeds_status_from_cloud_properties():
    c = BleMeshGatewayClient(CHILD, HUB, "user1")
    assert c.status.on is True
    assert c.status.dimming == round(40 * 255 / 100)
    assert c.status.cct == 3000


@pytest.mark.parametrize(
    "hub_patch",
    [
        {"aesKey": []},
        {"properties": {}},
        {"id": ""},
    ],
)
def test_client_refuses_unusable_hub_record(hub_patch):
    with pytest.raises(BleMeshError):
        BleMeshGatewayClient(CHILD, {**HUB, **hub_patch}, "user1")


# --------------------------------------------------------------------------- #
# Wire format (fake hub over an in-memory stream)
# --------------------------------------------------------------------------- #


class _FakeHub:
    """Records frames written to it and replies with canned responses."""

    def __init__(self, responses):
        self._responses = list(responses)
        self.sent = []
        self._pending = b""

    # -- StreamWriter surface -- #
    def write(self, data):
        magic, _mtype, size = struct.unpack(">HHI", data[:8])
        assert magic == 0x1EED
        body = data[8 : 8 + size]
        self.sent.append(json.loads(aes_decrypt(body, KEY)))
        if self._responses:
            reply = json.dumps(self._responses.pop(0)).encode()
            enc = aes_encrypt(reply, KEY)
            self._pending += struct.pack(">Hhi", 0x1EED, 1, len(enc)) + enc

    async def drain(self):
        return None

    def is_closing(self):
        return False

    def close(self):
        return None

    async def wait_closed(self):
        return None

    # -- StreamReader surface -- #
    async def readexactly(self, n):
        if len(self._pending) < n:
            raise asyncio.IncompleteReadError(self._pending, n)
        chunk, self._pending = self._pending[:n], self._pending[n:]
        return chunk


def _login_ok():
    return {"method": "loginResp", "ack": {"code": 200}, "payload": {"ascNumber": 7}}


def _set_ok():
    return {"method": "setDevAttrResp", "ack": {"code": 200}}


async def _session_with(monkeypatch, responses, idle_close_s=0.0):
    hub = _FakeHub(responses)

    async def fake_open_connection(host, port):
        assert port == 10000
        return hub, hub

    monkeypatch.setattr(
        "aidot_cameras.ble_gateway.asyncio.open_connection", fake_open_connection
    )
    session = BleMeshHubSession(
        "hub1", "192.0.2.20", KEY, "user1", "hubpw", idle_close_s=idle_close_s
    )
    return session, hub


def test_relay_command_is_addressed_to_child_via_hub(monkeypatch):
    async def run():
        session, hub = await _session_with(monkeypatch, [_login_ok(), _set_ok()])
        assert await session.async_send_attributes("bulb1", {"OnOff": 1}) is True
        return hub.sent

    sent = asyncio.run(run())
    login, cmd = sent
    assert login["method"] == "loginReq"
    assert login["deviceId"] == "hub1"  # login is to the hub itself

    assert cmd["method"] == "setDevAttrReq"
    assert cmd["deviceId"] == "bulb1"
    payload = cmd["payload"]
    assert payload["devId"] == "bulb1"
    assert payload["parentId"] == "hub1"  # ...but the command names the child
    # "ble" is what makes the hub relay onto the mesh; "tcp" would target the hub.
    assert payload["channel"] == "ble"
    assert payload["attr"] == {"OnOff": 1}
    # ascNumber continues from the login response rather than restarting at 1.
    assert payload["ascNumber"] == 8


def test_second_command_reuses_one_login(monkeypatch):
    async def run():
        session, hub = await _session_with(
            monkeypatch, [_login_ok(), _set_ok(), _set_ok()]
        )
        await session.async_send_attributes("bulb1", {"OnOff": 1})
        await session.async_send_attributes("bulb1", {"Dimming": 50})
        return hub.sent

    sent = asyncio.run(run())
    assert [m["method"] for m in sent] == [
        "loginReq",
        "setDevAttrReq",
        "setDevAttrReq",
    ]
    # ascNumber advances per command on the shared session.
    assert sent[1]["payload"]["ascNumber"] == 8
    assert sent[2]["payload"]["ascNumber"] == 9


def test_login_rejection_raises_rather_than_reporting_success(monkeypatch):
    async def run():
        session, _ = await _session_with(
            monkeypatch,
            [{"method": "loginResp", "ack": {"code": 401}}],
        )
        await session.async_send_attributes("bulb1", {"OnOff": 1})

    with pytest.raises(BleMeshError):
        asyncio.run(run())


def test_dropped_connection_is_retried(monkeypatch):
    monkeypatch.setattr("aidot_cameras.ble_gateway._BACKOFF_S", 0)
    attempts = []

    async def run():
        # First hub answers the login then dies before the ack; second works.
        dead = _FakeHub([_login_ok()])
        live = _FakeHub([_login_ok(), _set_ok()])
        hubs = [dead, live]

        async def fake_open_connection(host, port):
            hub = hubs[min(len(attempts), len(hubs) - 1)]
            attempts.append(hub)
            return hub, hub

        monkeypatch.setattr(
            "aidot_cameras.ble_gateway.asyncio.open_connection", fake_open_connection
        )
        session = BleMeshHubSession(
            "hub1", "192.0.2.20", KEY, "user1", "hubpw", idle_close_s=0.0
        )
        return await session.async_send_attributes("bulb1", {"OnOff": 1})

    assert asyncio.run(run()) is True
    assert len(attempts) == 2


# --------------------------------------------------------------------------- #
# Optimistic status
# --------------------------------------------------------------------------- #


def test_status_advances_only_on_ack(monkeypatch):
    async def run():
        client = BleMeshGatewayClient(CHILD, HUB, "user1")
        client.status.on = False
        seen = []
        client.on_status_update = seen.append

        hub = _FakeHub([_login_ok(), {"method": "setDevAttrResp"}])

        async def fake_open_connection(host, port):
            return hub, hub

        monkeypatch.setattr(
            "aidot_cameras.ble_gateway.asyncio.open_connection", fake_open_connection
        )
        await close_all_hub_sessions()
        ok = await client.async_turn_on()
        await close_all_hub_sessions()
        return ok, client.status.on, seen

    ok, is_on, seen = asyncio.run(run())
    assert ok is True and is_on is True and len(seen) == 1


def test_status_unchanged_when_hub_does_not_ack(monkeypatch):
    async def run():
        client = BleMeshGatewayClient(CHILD, HUB, "user1")
        client.status.on = False
        # An error response is a reply, but not an ack of the set.
        hub = _FakeHub([_login_ok(), {"method": "errorResp"}])

        async def fake_open_connection(host, port):
            return hub, hub

        monkeypatch.setattr(
            "aidot_cameras.ble_gateway.asyncio.open_connection", fake_open_connection
        )
        await close_all_hub_sessions()
        ok = await client.async_turn_on()
        await close_all_hub_sessions()
        return ok, client.status.on

    ok, is_on = asyncio.run(run())
    assert ok is False and is_on is False


def test_brightness_never_rounds_down_to_off():
    c = BleMeshGatewayClient(CHILD, HUB, "user1")
    from aidot_cameras.ble_gateway import _255_to_pct

    # 1/255 is ~0.4%, which would round to 0 == off.
    assert _255_to_pct(1) == 1
    assert _255_to_pct(255) == 100
    assert c.info.enable_dimming


def test_update_status_from_cloud_record():
    c = BleMeshGatewayClient(CHILD, HUB, "user1")
    c.update_status_from_device(
        {"online": False, "properties": {"OnOff": "0", "Dimming": "100"}}
    )
    assert c.status.online is False
    assert c.status.on is False
    assert c.status.dimming == 255


# --------------------------------------------------------------------------- #
# Session registry
# --------------------------------------------------------------------------- #


def test_hub_session_is_shared_and_replaced_on_ip_change():
    async def run():
        await close_all_hub_sessions()
        a = get_hub_session("hub1", "192.0.2.20", KEY, "user1", "pw")
        b = get_hub_session("hub1", "192.0.2.20", KEY, "user1", "pw")
        assert a is b  # every child behind the hub shares one serialised session
        c = get_hub_session("hub1", "192.0.2.99", KEY, "user1", "pw")
        assert c is not a  # a DHCP move must not reuse the old address
        await close_all_hub_sessions()
        d = get_hub_session("hub1", "192.0.2.20", KEY, "user1", "pw")
        assert d is not a

    asyncio.run(run())
