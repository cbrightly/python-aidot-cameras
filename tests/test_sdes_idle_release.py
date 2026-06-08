"""Unit tests for the SDES no-viewer idle-release policy helpers.

An SDES keepalive otherwise reconnects forever even with zero HA consumers
(a battery-draining orphan).  The loop releases when no TCP client is connected
to the -listen serve port for the idle window.  These pure helpers decide that
policy; no camera/network/host needed.

  - _sdes_serve_port: pull the TCP port out of the serve URL.
  - _tcp_table_has_established_on_port: is a consumer ESTABLISHED on that port?
    (A LISTEN socket - ffmpeg waiting - must NOT count as a consumer.)
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot.device_client import (
    _sdes_serve_port,
    _tcp_table_has_established_on_port,
)

_PORT = 18981          # 0x4A25
_HEX = f"{_PORT:04X}"   # "4A25"

_HEADER = (
    "  sl  local_address rem_address   st tx_queue rx_queue tr tm->when "
    "retrnsmt   uid  timeout inode"
)


def _row(local_port_hex, st, remote="0100007F:9C40"):
    return (
        f"   0: 0100007F:{local_port_hex} {remote} {st} "
        "00000000:00000000 00:00000000 00000000     0        0 99999 1"
    )


# ---- _sdes_serve_port ------------------------------------------------------

def test_serve_port_parsed():
    assert _sdes_serve_port("http://127.0.0.1:18981/abc.ts") == 18981


def test_serve_port_none_url():
    assert _sdes_serve_port(None) is None


def test_serve_port_malformed():
    assert _sdes_serve_port("not-a-url") is None
    assert _sdes_serve_port("http://127.0.0.1:notaport/x.ts") is None


# ---- _tcp_table_has_established_on_port ------------------------------------

def test_established_consumer_present():
    table = _HEADER + "\n" + _row(_HEX, "01")  # 01 == ESTABLISHED
    assert _tcp_table_has_established_on_port(table, _PORT) is True


def test_listen_only_is_not_a_consumer():
    # ffmpeg -listen 1 waiting (0A == LISTEN) is not a viewer pulling.
    table = _HEADER + "\n" + _row(_HEX, "0A")
    assert _tcp_table_has_established_on_port(table, _PORT) is False


def test_established_on_other_port_ignored():
    table = _HEADER + "\n" + _row("0050", "01")  # ESTABLISHED on :80, not ours
    assert _tcp_table_has_established_on_port(table, _PORT) is False


def test_empty_or_header_only_table():
    assert _tcp_table_has_established_on_port("", _PORT) is False
    assert _tcp_table_has_established_on_port(_HEADER, _PORT) is False


def test_tcp6_long_local_address_parses():
    # tcp6 local_address is a 32-hex-char IPv6 + :PORT; port parse must still work.
    table = (
        _HEADER + "\n"
        f"   0: 00000000000000000000000001000000:{_HEX} "
        "00000000000000000000000000000000:0000 01 "
        "00000000:00000000 00:00000000 00000000     0        0 12345 1"
    )
    assert _tcp_table_has_established_on_port(table, _PORT) is True


def test_mixed_rows_finds_the_established_one():
    table = "\n".join([
        _HEADER,
        _row(_HEX, "0A"),          # our port, LISTEN (not a consumer)
        _row("0050", "01"),        # other port, ESTABLISHED
        _row(_HEX, "01"),          # our port, ESTABLISHED  <-- the viewer
    ])
    assert _tcp_table_has_established_on_port(table, _PORT) is True


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
