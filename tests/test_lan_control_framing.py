"""Unit tests for the pure pieces of aidot_cameras.camera.lan_control.

The socket handshake belongs to the live path; what is pinned here is the frame
codec (header pack/unpack and the oversized-body guard that stops a hostile LAN
peer from driving an allocation) and the battery gate that decides eligibility.
"""

import asyncio
import struct

import pytest

from aidot_cameras.camera import lan_control
from aidot_cameras.camera.lan_control import CameraLanClient


def test_pack_lays_out_the_8_byte_header():
    frame = lan_control._pack(0x12, b"hello")
    magic, mtype, size = struct.unpack(">HHI", frame[:8])
    assert magic == lan_control._MAGIC
    assert mtype == 0x12
    assert size == len(b"hello")
    assert frame[8:] == b"hello"


def _reader(data: bytes) -> asyncio.StreamReader:
    r = asyncio.StreamReader()
    r.feed_data(data)
    r.feed_eof()
    return r


async def test_read_frame_returns_the_body():
    body = b'{"k":"v"}'
    header = struct.pack(">HHI", lan_control._MAGIC, 7, len(body))
    got = await lan_control._read_frame(_reader(header + body), timeout=1.0)
    assert got == body


async def test_read_frame_rejects_an_oversized_body():
    # A LAN peer claiming a 2 MiB body (over the 1 MiB cap) must be refused
    # before any allocation, not read.
    header = struct.pack(">HHI", lan_control._MAGIC, 7, 2 * 1024 * 1024)
    with pytest.raises(ValueError, match="too large"):
        await lan_control._read_frame(_reader(header), timeout=1.0)


async def test_read_frame_at_the_cap_is_allowed():
    size = lan_control._MAX_FRAME_BODY
    body = b"\x00" * size
    header = struct.pack(">HHI", lan_control._MAGIC, 7, size)
    got = await lan_control._read_frame(_reader(header + body), timeout=2.0)
    assert len(got) == size


async def test_read_frame_times_out_on_a_short_header():
    # Fewer than 8 header bytes and no EOF: readexactly must hit the timeout.
    r = asyncio.StreamReader()
    r.feed_data(b"\x1e")  # one byte, stream left open
    with pytest.raises((asyncio.TimeoutError, TimeoutError)):
        await lan_control._read_frame(r, timeout=0.05)


def test_mains_powered_when_no_battery_level_is_reported():
    assert CameraLanClient.is_mains_powered({"Battery_remaining": None}) is True
    assert CameraLanClient.is_mains_powered({}) is True


def test_battery_camera_is_not_mains_powered():
    assert CameraLanClient.is_mains_powered({"Battery_remaining": 64}) is False
    assert CameraLanClient.is_mains_powered({"Battery_remaining": 0}) is False
