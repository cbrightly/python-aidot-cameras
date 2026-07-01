"""Regression tests for the 0.10.x security-review hardening fixes."""

import asyncio
import os
import struct

import pytest

from aidot.camera.lan_control import _MAGIC, _MAX_FRAME_BODY, _read_frame
from aidot.camera.models import _as_bool, _as_int
from aidot.camera.protocol import _SPROP_DIR, _sprop_cache_path


# ── models: malformed cloud attrs are skipped, not fatal ──────────────────────

def test_as_int_coerces_or_none():
    assert _as_int("5") == 5
    assert _as_int(5) == 5
    assert _as_int("on") is None       # non-numeric string must not raise
    assert _as_int(None) is None
    assert _as_int({}) is None


def test_as_bool_coerces_or_none():
    assert _as_bool("0") is False
    assert _as_bool("1") is True
    assert _as_bool(1) is True
    assert _as_bool("yes") is None     # non-numeric must not raise
    assert _as_bool(None) is None


# ── sprop cache: devid can't escape the cache dir ─────────────────────────────

@pytest.mark.parametrize("devid", ["../../etc/passwd", "/etc/passwd", "a/b/c", "..", "x\\y"])
def test_sprop_path_stays_in_cache_dir(devid):
    p = os.path.abspath(_sprop_cache_path(devid))
    assert os.path.dirname(p) == os.path.abspath(_SPROP_DIR)
    assert p.endswith(".sprop")


def test_sprop_path_normal_devid_preserved():
    p = _sprop_cache_path("LK-IPC-A000088-abc123")
    assert os.path.basename(p) == "LK-IPC-A000088-abc123.sprop"


# ── LAN control: oversized frame body is rejected before allocation ───────────

def test_read_frame_rejects_oversized_body():
    async def _run():
        reader = asyncio.StreamReader()
        reader.feed_data(struct.pack(">HHI", _MAGIC, 1, _MAX_FRAME_BODY + 1))
        reader.feed_eof()
        with pytest.raises(ValueError, match="too large"):
            await _read_frame(reader, timeout=1.0)

    asyncio.run(_run())


def test_read_frame_accepts_small_body():
    async def _run():
        body = b'{"ok":1}'
        reader = asyncio.StreamReader()
        reader.feed_data(struct.pack(">HHI", _MAGIC, 1, len(body)) + body)
        reader.feed_eof()
        assert await _read_frame(reader, timeout=1.0) == body

    asyncio.run(_run())
