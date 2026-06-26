"""Unit test: WebRTCSession.get_stats() shape + ICE-path extraction.

get_stats reaches into private aiortc/aioice attributes (no public selected-pair
API exists). These tests lock the parsing against fakes shaped like those
internals, so a refactor that breaks the extraction fails loudly here instead of
silently returning empty diagnostics on a live box. No camera needed.
"""
import asyncio
import os
import sys
from types import SimpleNamespace

sys.path.insert(0, os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "src"))

from aidot_cameras.camera.webrtc import WebRTCSession


def _cand(typ, host, port, transport="udp"):
    return SimpleNamespace(type=typ, host=host, port=port, transport=transport)


def _fake_pc(nominated, inbound):
    pair = SimpleNamespace(
        local_candidate=_cand(*nominated[0]),
        remote_candidate=_cand(*nominated[1]),
    )
    conn = SimpleNamespace(_nominated={1: pair})
    transport = SimpleNamespace(_connection=conn)

    class _Report(dict):
        pass

    report = _Report()
    for i, s in enumerate(inbound):
        report[f"in{i}"] = SimpleNamespace(type="inbound-rtp", **s)
    report["transport"] = SimpleNamespace(type="transport")  # ignored

    async def _get_stats():
        return report

    pc = SimpleNamespace(getStats=_get_stats)
    setattr(pc, "_RTCPeerConnection__iceTransports", [transport])
    return pc


def _session(pc):
    return WebRTCSession(
        pc=pc, outgoing_q=None, mqtt_fut=None, recorder=None, track_tasks=[],
    )


def test_get_stats_reports_ice_path_and_loss():
    pc = _fake_pc(
        nominated=(("host", "192.168.1.206", 40410), ("prflx", "192.168.1.245", 58305)),
        inbound=[
            {"kind": "video", "packetsReceived": 900, "packetsLost": 100, "jitter": 30},
            {"kind": "audio", "packetsReceived": 500, "packetsLost": 0, "jitter": 12},
        ],
    )
    out = asyncio.run(_session(pc).get_stats())
    assert out["ice"] == [{
        "component": 1,
        "local_type": "host", "local": "192.168.1.206:40410",
        "remote_type": "prflx", "remote": "192.168.1.245:58305",
        "transport": "udp",
    }]
    video = next(s for s in out["inbound"] if s["kind"] == "video")
    assert video["packets_received"] == 900 and video["packets_lost"] == 100
    assert video["loss_pct"] == 10.0          # 100 / (900+100)
    assert "error" not in out


def test_get_stats_never_raises_on_broken_internals():
    # pc with no ICE transports and a getStats that raises: must degrade, not throw.
    async def _boom():
        raise RuntimeError("aiortc said no")
    pc = SimpleNamespace(getStats=_boom)
    out = asyncio.run(_session(pc).get_stats())
    assert out["ice"] is None
    assert out["inbound"] == []
    assert "error" in out
