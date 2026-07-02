"""Unit tests for _ServeRelay - the cold-start port holder.

The relay keeps a public serve port connectable while ffmpeg (the real
-listen backend) is not up yet, so an eager go2rtc pull CONNECTS and waits
instead of getting ECONNREFUSED and giving up.  These tests use real localhost
sockets with ephemeral ports - no camera, no ffmpeg, fully deterministic.
"""
import os
import socket
import sys
import threading
import time

import pytest

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot.camera.protocol import _ServeRelay, _grab_free_port, _rewrite_serve_port


@pytest.fixture(autouse=True)
def _real_sockets():
    """Bind real localhost TCP ports in this module regardless of the env.

    In CI only bare pytest is installed, so nothing interferes. Locally,
    pytest-socket arrives transitively (via pytest-homeassistant-custom-component,
    whose pytest_runtest_setup hook disables sockets before every test) and would
    fail every test here. Re-enable sockets for this module; the plugin re-disables
    before the next test, so other tests keep their default deny-by-default posture.
    No-op when pytest-socket is absent (e.g. CI).
    """
    try:
        import pytest_socket
    except ImportError:
        yield
        return
    pytest_socket.enable_socket()
    yield


def test_rewrite_serve_port_swaps_only_the_port():
    assert (_rewrite_serve_port("http://127.0.0.1:18989/abc.ts", 40001)
            == "http://127.0.0.1:40001/abc.ts")
    # Path and scheme preserved; host preserved.
    assert (_rewrite_serve_port("http://127.0.0.1:8554/aidot_x", 9)
            == "http://127.0.0.1:9/aidot_x")


def test_rewrite_serve_port_handles_missing_or_malformed():
    assert _rewrite_serve_port(None, 1) is None
    assert _rewrite_serve_port("not a url", 1) == "not a url"


def test_grab_free_port_returns_a_bindable_port():
    p = _grab_free_port()
    assert isinstance(p, int) and 1024 < p < 65536


def _free_port():
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind(("127.0.0.1", 0))
    p = s.getsockname()[1]
    s.close()
    return p


class _EchoBackend:
    """A tiny one-shot -listen-style backend: accepts one client and echoes,
    optionally prefixing a tag so tests can tell backends apart."""

    def __init__(self, tag=b""):
        self._tag = tag
        self._srv = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self._srv.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        self._srv.bind(("127.0.0.1", 0))
        self._srv.listen(1)
        self.port = self._srv.getsockname()[1]
        self._t = threading.Thread(target=self._run, daemon=True)
        self._t.start()

    def _run(self):
        try:
            cli, _ = self._srv.accept()
        except OSError:
            return
        try:
            cli.sendall(self._tag)
            while True:
                d = cli.recv(4096)
                if not d:
                    break
                cli.sendall(d)
        except OSError:
            pass
        finally:
            cli.close()

    def close(self):
        try:
            self._srv.close()
        except OSError:
            pass


def test_connect_before_backend_does_not_refuse_then_flows():
    # The core guarantee: a pull that arrives BEFORE the backend exists connects
    # (no ECONNREFUSED) and gets data once the backend appears.
    relay = _ServeRelay(_free_port(), dial_timeout=10.0)
    relay.start()
    try:
        cli = socket.create_connection(("127.0.0.1", relay.port), timeout=2.0)
        # Backend comes up ~now, after the client already connected.
        be = _EchoBackend()
        relay.set_backend(be.port)
        cli.sendall(b"GET /x.ts HTTP/1.0\r\n\r\n")
        cli.settimeout(5.0)
        got = cli.recv(4096)
        assert b"GET /x.ts" in got
        cli.close()
        be.close()
    finally:
        relay.close()


def test_without_relay_connection_is_refused():
    # Control: this is the bug the relay fixes - an unbound port refuses fast.
    port = _free_port()
    try:
        socket.create_connection(("127.0.0.1", port), timeout=1.0)
        assert False, "expected ConnectionRefusedError on an unbound port"
    except ConnectionRefusedError:
        pass


def test_backend_swap_between_connections():
    # After ffmpeg restarts (go2rtc reconnect) the relay redials the new backend.
    relay = _ServeRelay(_free_port(), dial_timeout=10.0)
    relay.start()
    try:
        be1 = _EchoBackend(tag=b"ONE:")
        relay.set_backend(be1.port)
        c1 = socket.create_connection(("127.0.0.1", relay.port), timeout=2.0)
        c1.settimeout(5.0)
        assert c1.recv(16).startswith(b"ONE:")
        c1.close()
        be1.close()

        be2 = _EchoBackend(tag=b"TWO:")
        relay.set_backend(be2.port)
        c2 = socket.create_connection(("127.0.0.1", relay.port), timeout=2.0)
        c2.settimeout(5.0)
        assert c2.recv(16).startswith(b"TWO:")
        c2.close()
        be2.close()
    finally:
        relay.close()


def test_close_frees_the_public_port():
    port = _free_port()
    relay = _ServeRelay(port)
    relay.start()
    relay.close()
    # SO_REUSEADDR + a real close means we can rebind immediately (this is what
    # lets ffmpeg/other consumers take the port back after teardown).
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    s.bind(("127.0.0.1", port))
    s.close()


def test_dial_timeout_closes_idle_client():
    # A client that connects but whose backend never appears is dropped after
    # the dial timeout (so it can retry) rather than hanging forever.
    relay = _ServeRelay(_free_port(), dial_timeout=0.5, dial_interval=0.05)
    relay.start()
    try:
        cli = socket.create_connection(("127.0.0.1", relay.port), timeout=2.0)
        cli.settimeout(3.0)
        t0 = time.monotonic()
        got = cli.recv(16)  # blocks until relay closes the conn (EOF -> b"")
        assert got == b""
        assert time.monotonic() - t0 >= 0.4
        cli.close()
    finally:
        relay.close()


if __name__ == "__main__":
    import traceback
    _fail = 0
    for _k, _v in sorted(globals().items()):
        if _k.startswith("test_"):
            try:
                _v()
                print(f"PASS {_k}")
            except Exception:
                _fail += 1
                print(f"FAIL {_k}")
                traceback.print_exc()
    raise SystemExit(1 if _fail else 0)
