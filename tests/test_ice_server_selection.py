"""Unit 1: host-only ICE in fast_connect — _select_ice_servers."""
from aiortc import RTCIceServer

from aidot.device_client import _select_ice_servers


def _servers():
    return [
        RTCIceServer(urls=["stun:stun.l.google.com:19302"]),
        RTCIceServer(urls=["turn:3.230.182.123:5349"], username="u", credential="c"),
    ]


def test_fast_connect_is_host_only():
    # LAN-direct: empty server list -> aiortc gathers host candidates only,
    # no STUN/TURN gather stall.
    assert _select_ice_servers(_servers(), fast_connect=True) == []


def test_non_fast_connect_unchanged():
    srv = _servers()
    assert _select_ice_servers(srv, fast_connect=False) is srv
