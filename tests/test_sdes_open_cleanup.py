"""Resource-cleanup plumbing for the SDES cold-open wrapper.

_open_sdes_stream reserves UDP sockets, a bridge thread, ffmpeg and a temp SDP
before handing them to the returned SdesSession.  If the cold open is cancelled
mid-handshake (or raises) before that hand-off, the wrapper must release them via
its ExitStack; on a successful return it must NOT (the session now owns them).
These tests exercise that wrapper mechanism directly with a stub impl.
"""
import asyncio
import types

import pytest

from aidot.camera.client import CameraMixin


def _wrapper_for(impl):
    obj = types.SimpleNamespace()
    obj._open_sdes_stream_impl = impl
    return CameraMixin._open_sdes_stream.__get__(obj)


def test_open_sdes_wrapper_cleans_up_on_cancel():
    ran = []

    async def impl(_cleanup=None, **kwargs):
        _cleanup.callback(lambda: ran.append("cleaned"))
        raise asyncio.CancelledError()

    with pytest.raises(asyncio.CancelledError):
        asyncio.run(_wrapper_for(impl)(peer_id="x"))
    assert ran == ["cleaned"]


def test_open_sdes_wrapper_cleans_up_on_error():
    ran = []

    async def impl(_cleanup=None, **kwargs):
        _cleanup.callback(lambda: ran.append("cleaned"))
        raise RuntimeError("boom")

    with pytest.raises(RuntimeError):
        asyncio.run(_wrapper_for(impl)(peer_id="x"))
    assert ran == ["cleaned"]


def test_open_sdes_wrapper_skips_cleanup_on_success():
    ran = []
    sentinel = object()

    async def impl(_cleanup=None, **kwargs):
        _cleanup.callback(lambda: ran.append("cleaned"))
        return sentinel

    result = asyncio.run(_wrapper_for(impl)(peer_id="x"))
    assert result is sentinel
    assert ran == []  # ownership transferred to the session; cleanup must NOT run


def test_open_sdes_wrapper_runs_cleanups_lifo():
    # Steps run last-registered-first, so ffmpeg is killed before its sockets
    # close (mirrors the impl's registration order: sockets, then sdp, then proc).
    order = []

    async def impl(_cleanup=None, **kwargs):
        _cleanup.callback(lambda: order.append("sock"))
        _cleanup.callback(lambda: order.append("proc"))
        raise asyncio.CancelledError()

    with pytest.raises(asyncio.CancelledError):
        asyncio.run(_wrapper_for(impl)(peer_id="x"))
    assert order == ["proc", "sock"]  # LIFO
