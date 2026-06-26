"""Unit tests for the LAN device-control client (no camera required)."""

import json
import os
import struct
import sys

sys.path.insert(0, os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "src"))

import pytest

from aidot_cameras.aes_utils import aes_decrypt, aes_encrypt
from aidot_cameras.camera.lan_control import (
    ATTR_KEYS,
    CameraLanClient,
    CameraLanError,
    _local_ipv4,
    _pack,
    discover_subnet,
)

DEVICE = {"id": "dev1", "modelId": "LK.IPC.A000088", "aesKey": ["k" * 16], "password": "pw"}
USER = {"id": "user1"}


def test_pack_header_is_8_bytes_and_well_formed():
    body = b'{"x":1}'
    frame = _pack(1, body)
    assert len(frame) == 8 + len(body)
    magic, mtype, blen = struct.unpack(">HHI", frame[:8])
    assert magic == 0x1EED and mtype == 1 and blen == len(body)
    assert frame[8:] == body


def test_construction_derives_16byte_key():
    c = CameraLanClient(DEVICE, USER, ip="192.0.2.10")
    assert c.device_id == "dev1"
    assert len(c._key) == 16 and c._key.startswith(b"k" * 16)
    assert c.ip == "192.0.2.10"
    assert c.eligible is None  # not resolved yet


def test_construction_requires_aeskey():
    with pytest.raises(CameraLanError):
        CameraLanClient({"id": "d", "password": "p"}, USER)


def test_friendly_attr_map_round_trips_through_aes():
    # the keys we expose must encrypt/decrypt cleanly in a setDevAttr body
    c = CameraLanClient(DEVICE, USER, ip="192.0.2.10")
    body = {ATTR_KEYS["status_led"]: 1}
    blob = aes_encrypt(json.dumps(body).encode(), c._key)
    assert json.loads(aes_decrypt(blob, c._key)) == {"LedOnOff": 1}


def test_async_set_rejects_unknown_attr():
    c = CameraLanClient(DEVICE, USER, ip="192.0.2.10")
    import asyncio
    with pytest.raises(CameraLanError):
        asyncio.run(c.async_set("not_a_real_control", 1))


def test_battery_gating():
    assert CameraLanClient.is_mains_powered({"Battery_remaining": None}) is True
    assert CameraLanClient.is_mains_powered({"LedOnOff": 0}) is True  # key absent -> mains
    assert CameraLanClient.is_mains_powered({"Battery_remaining": 87}) is False


@pytest.mark.parametrize("friendly,wire", list(ATTR_KEYS.items()))
def test_attr_keys_are_strings(friendly, wire):
    assert isinstance(friendly, str) and isinstance(wire, str)


def test_network_helpers_are_callable():
    # Real behaviour is covered by the live smoke test; here just assert the
    # discovery helpers are importable and have the expected shape (the test
    # sandbox blocks sockets, so we don't invoke them).
    import inspect
    assert callable(_local_ipv4)
    assert inspect.iscoroutinefunction(discover_subnet)
    sig = inspect.signature(discover_subnet)
    assert list(sig.parameters) == ["cidr24", "timeout", "concurrency"]
