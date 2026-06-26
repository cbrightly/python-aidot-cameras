"""Unit 4: active serve-ready bind probe — _await_listen_bound.

Repo convention: no pytest-asyncio; drive coroutines with asyncio.run().
"""
import asyncio
import socket

from aidot.device_client import _await_listen_bound


def test_returns_true_when_socket_is_listening():
    srv = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    srv.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    srv.bind(("127.0.0.1", 0))
    srv.listen(1)
    port = srv.getsockname()[1]
    try:
        assert asyncio.run(_await_listen_bound("127.0.0.1", port, timeout=2.0)) is True
    finally:
        srv.close()


def test_returns_false_when_nothing_listens():
    # Grab a free port then close it so nothing is listening there.
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind(("127.0.0.1", 0))
    port = s.getsockname()[1]
    s.close()
    assert asyncio.run(_await_listen_bound("127.0.0.1", port, timeout=0.5)) is False
