"""Unit tests for the LAN device-control client (no camera required)."""

import json
import os
import struct
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import pytest

from aidot_cameras.crypto import aes_decrypt, aes_encrypt
from aidot_cameras.camera.lan_control import (
    ATTR_KEYS,
    CameraLanClient,
    CameraLanError,
    CameraLanLoginRejected,
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


def test_get_attributes_empty_replies_raises_lanerror():
    # If the camera accepts login but never answers getDevAttrReq (read timeout),
    # _session returns [] - async_get_attributes must raise CameraLanError (the
    # contract callers rely on for cloud fallback), not an unguarded IndexError.
    import asyncio

    c = CameraLanClient(DEVICE, USER, ip="192.0.2.10")

    async def _empty(_build):
        return []

    c._session = _empty

    async def _go():
        with pytest.raises(CameraLanError):
            await c.async_get_attributes()

    asyncio.run(_go())


# --------------------------------------------------------------------------- #
# A device that ADVERTISES local control and then refuses the login
# --------------------------------------------------------------------------- #
# Measured on the reference LAN 2026-08-08: every device answers unicast
# discovery with lanMode=1/localCtrFlag=1 - so async_resolve() says yes and the
# login is attempted - and then rejects loginReq. Cameras answer ack 4352,
# lights 400 or 4354. The consumer caught one flat CameraLanError for every
# failure mode and logged it at DEBUG, so a feature that has never once worked
# looked exactly like a device that simply does not offer local control.
#
# The distinction the caller needs is "this device declined to offer local
# control" (ordinary, quiet) versus "this device offered it and then refused
# us" (a defect, and it should be able to say so).

def test_a_refused_login_is_distinguishable_from_an_unavailable_one():
    """The caller cannot log the interesting case differently without this."""
    assert issubclass(CameraLanLoginRejected, CameraLanError)


def test_a_refused_login_carries_the_ack_the_device_sent():
    """4352, 400 and 4354 are different refusals; a bare exception loses that."""
    exc = CameraLanLoginRejected("dev1: login rejected ack=4352", ack=4352)
    assert exc.ack == 4352


def test_the_login_raises_the_specific_error_on_a_refusal():
    """The real login path must produce the new type, not a bare CameraLanError."""
    import asyncio

    c = CameraLanClient(DEVICE, USER, ip="192.0.2.10")
    reply = {"service": "device", "method": "loginResp", "deviceId": "dev1",
             "payload": {"ascNumber": 1}, "ack": {"code": 4352, "desc": "fail"}}
    body = aes_encrypt(json.dumps(reply).encode(), c._key)

    class _Reader:
        def __init__(self):
            self._buf = _pack(1, body)

        async def readexactly(self, n):
            out, self._buf = self._buf[:n], self._buf[n:]
            return out

    class _Writer:
        def write(self, _data):
            return None

        async def drain(self):
            return None

    async def _go():
        with pytest.raises(CameraLanLoginRejected) as caught:
            await c._login(_Reader(), _Writer())
        return caught.value

    exc = asyncio.run(_go())
    assert exc.ack == 4352
    # The client still de-eligibles itself, so cloud fallback is unchanged.
    assert c._eligible is False
