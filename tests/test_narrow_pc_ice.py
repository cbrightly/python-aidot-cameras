"""Unit 1: host-only local-pc ICE narrowing — _narrow_pc_ice.

A live A/B (2026-06-26) showed fast_connect's ~5s gather stall is a fixed aioice
srflx timeout (host-only ~16ms vs ~5020ms for STUN, regardless of server count).
host-only is opt-in/default-off because it drops srflx fallback (on-subnet only).

The load-bearing invariant — and the regression that bit twice (v0.5.15 and again
this session) — is that narrowing the LOCAL pc must NOT touch the camera-facing
webrtcReq IceServerList source list.  These tests pin that: host_only returns []
while the input STUN list is left intact and STUN-bearing.
"""
from aidot.camera.webrtc_open import _narrow_pc_ice


class _Srv:
    """Minimal RTCIceServer stand-in (urls only)."""
    def __init__(self, urls):
        self.urls = urls


def test_host_only_returns_empty_local_list():
    ice = [_Srv(["stun:stun.l.google.com:19302"]), _Srv(["stun:1.2.3.4:3478"])]
    assert _narrow_pc_ice(ice, host_only=True) == []


def test_host_only_leaves_camera_ice_list_untouched():
    # _ice_servers feeds the camera webrtcReq IceServerList; it must survive.
    ice = [_Srv(["stun:stun.l.google.com:19302"]), _Srv(["stun:1.2.3.4:3478"])]
    before = list(ice)
    _narrow_pc_ice(ice, host_only=True)
    assert ice == before  # not mutated
    assert len(ice) == 2
    assert any("stun:" in u for s in ice for u in s.urls)  # still STUN-bearing


def test_default_keeps_stun_for_local_pc():
    ice = [_Srv(["stun:stun.l.google.com:19302"]), _Srv(["stun:1.2.3.4:3478"])]
    out = _narrow_pc_ice(ice, host_only=False)
    assert [s.urls for s in out] == [s.urls for s in ice]


def test_default_returns_copy_not_alias():
    # Caller may reassign _ice_servers afterwards; the local list must not alias.
    ice = [_Srv(["stun:stun.l.google.com:19302"])]
    out = _narrow_pc_ice(ice, host_only=False)
    assert out is not ice
    out.append(_Srv(["stun:5.6.7.8:3478"]))
    assert len(ice) == 1  # mutating the returned list did not touch the input
