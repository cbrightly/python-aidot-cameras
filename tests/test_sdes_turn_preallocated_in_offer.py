"""Auto mode allocates both TURN relays before the offer, and offers them.

rc71 made two Allocates (audio and video) before it built the offer and put
both relays in it. A later change made one Allocate on the audio socket after
webrtcReq and trickled it instead; on the A001064 PTZ that put first media
about 2.3 s later on every cold open, so it was undone. These tests pin the
restored shape: in auto (and relay) mode both sockets are allocated before
the offer, the offer's candidate blocks carry the relays, and nothing
allocates after the offer except the retry when the pre-offer allocation got
nothing. That retry reads the media sockets after webrtcReq, so a STUN packet
it reads that is not its own response is kept for the STUN window.
"""

import ast
import asyncio
import logging
import socket
import struct
import threading
import types

from aidot_cameras.camera import sdes_open
from aidot_cameras.camera.sdes_open import (
    _TURN_STRAY_CAP,
    STUN_MAGIC_COOKIE,
    _sdes_offer_candidate_lines,
    turn_allocate_udp,
)
from tests.ast_guards import calls_to, function, module_tree, name_of, names_in
from tests.ast_guards import string_parts


def _attr(t, v):
    return struct.pack("!HH", t, len(v)) + v + b"\0" * (-len(v) % 4)


def _fake_turn(server, relay_ip="198.51.100.7", relay_port=50000):
    """Answer one unauthenticated and one authenticated Allocate."""

    def run():
        for step in (1, 2):
            req, addr = server.recvfrom(2048)
            tid = req[8:20]
            if step == 1:
                body = _attr(0x0014, b"realm") + _attr(0x0015, b"nonce")
                hdr = b"\x01\x13"
            else:
                ip = bytes(int(o) for o in relay_ip.split("."))
                xip = bytes(a ^ b for a, b in zip(ip, STUN_MAGIC_COOKIE))
                xpa = b"\x00\x01" + struct.pack("!H", relay_port ^ 0x2112) + xip
                body = _attr(0x0016, xpa)
                hdr = b"\x01\x03"
            server.sendto(
                hdr + struct.pack("!H", len(body)) + STUN_MAGIC_COOKIE + tid + body,
                addr,
            )

    t = threading.Thread(target=run, daemon=True)
    t.start()
    return t


def _udp():
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    s.bind(("127.0.0.1", 0))
    return s


def test_packets_that_are_not_the_allocate_s_are_kept():
    media, server, camera = _udp(), _udp(), _udp()
    try:
        probe = b"\x00\x01\x00\x00" + STUN_MAGIC_COOKIE + b"P" * 12
        camera.sendto(probe, media.getsockname())
        t = _fake_turn(server)
        stray = []
        res = turn_allocate_udp(
            media, "127.0.0.1", server.getsockname()[1], b"u", b"p", stray
        )
        t.join(2)
        assert res is not None and res[:2] == ("198.51.100.7", 50000)
        assert stray == [(probe, camera.getsockname())]
    finally:
        for s in (media, server, camera):
            s.close()


def test_only_stun_packets_are_kept_and_only_so_many():
    """Media or junk read during the allocate is dropped, and a flood of probes
    does not grow the replay without bound."""
    media, server, camera = _udp(), _udp(), _udp()
    try:
        camera.sendto(b"\x80\x08" + b"\x00" * 30, media.getsockname())  # RTP
        probes = [
            b"\x00\x01\x00\x00" + STUN_MAGIC_COOKIE + b"%012d" % i
            for i in range(_TURN_STRAY_CAP + 8)
        ]
        for probe in probes:
            camera.sendto(probe, media.getsockname())
        t = _fake_turn(server)
        stray = []
        res = turn_allocate_udp(
            media, "127.0.0.1", server.getsockname()[1], b"u", b"p", stray
        )
        t.join(2)
        assert res is not None
        assert [p for p, _ in stray] == probes[:_TURN_STRAY_CAP]
    finally:
        for s in (media, server, camera):
            s.close()


def test_without_a_sink_it_still_allocates():
    media, server = _udp(), _udp()
    try:
        t = _fake_turn(server)
        res = turn_allocate_udp(media, "127.0.0.1", server.getsockname()[1], b"u", b"p")
        t.join(2)
        assert res is not None
    finally:
        media.close()
        server.close()


def _tree():
    return module_tree("aidot_cameras.camera.sdes_open")


def _allocate_sites(fn):
    return [
        c
        for c in calls_to(fn, "_run_off_loop")
        if c.args and name_of(c.args[0]) == "_turn_allocate_udp"
    ]


_ENTRIES = [{"Username": "u1", "Password": "pw", "Uris": ["turn:turn.example:3478"]}]


def _pre_allocate(monkeypatch, mode, battery=False):
    """Run the pre-offer phase with a fake allocate; return (ps, calls)."""
    calls = []

    def fake_allocate(sock, host, port, user, password, stray=None):
        calls.append((sock, host, port, stray))
        return ("203.0.113.%d" % (len(calls) + 1), 40000 + len(calls), b"r", b"n")

    monkeypatch.setattr(sdes_open, "turn_allocate_udp", fake_allocate)
    owner = types.SimpleNamespace(
        device_id="cam1",
        is_battery_camera=battery,
        _resolve_sdes_connection_mode=lambda: mode,
        _resolve_sdes_skip_turn=lambda: mode == "lan" and not battery,
    )
    status = []
    ps = types.SimpleNamespace(
        _sdes_turn_entries=_ENTRIES,
        _audio_sock="AUDIO",
        _video_sock="VIDEO",
        user_id="u1",
        _status=status.append,
    )
    asyncio.run(sdes_open._sdes_open_turn_pre_allocation(owner, ps))
    ps.status_lines = status
    return ps, calls


def test_auto_mode_preallocates_both_sockets_before_the_offer(monkeypatch, caplog):
    with caplog.at_level(logging.INFO, logger=sdes_open._LOGGER.name):
        ps, calls = _pre_allocate(monkeypatch, "auto")
    assert [c[0] for c in calls] == ["AUDIO", "VIDEO"]
    assert all(c[1:3] == ("turn.example", 3478) for c in calls)
    assert set(ps._relay_addrs) == {"AUDIO", "VIDEO"}
    assert any(
        l.startswith("TURN relay pre-allocated (offer): audio -> ")
        for l in ps.status_lines
    )
    assert any(
        l.startswith("TURN relay pre-allocated (offer): video -> ")
        for l in ps.status_lines
    )
    assert any(
        "sdes-turn-prealloc" in r.getMessage() and "allocated=2" in r.getMessage()
        for r in caplog.records
    )


def test_relay_mode_preallocates_both_sockets_too(monkeypatch):
    ps, calls = _pre_allocate(monkeypatch, "relay")
    assert [c[0] for c in calls] == ["AUDIO", "VIDEO"]


def test_lan_mode_skips_it_but_never_for_a_battery_camera(monkeypatch):
    ps, calls = _pre_allocate(monkeypatch, "lan")
    assert calls == [] and ps._relay_addrs == {}
    ps, calls = _pre_allocate(monkeypatch, "lan", battery=True)
    assert [c[0] for c in calls] == ["AUDIO", "VIDEO"]


def test_the_auto_offer_carries_each_socket_s_relay(monkeypatch):
    ps, _ = _pre_allocate(monkeypatch, "auto")
    for sock, port in (("AUDIO", 41000), ("VIDEO", 41002)):
        relay = ps._relay_addrs[sock]
        lines = _sdes_offer_candidate_lines(
            "auto", "192.168.7.2", port, "198.51.100.7", relay
        )
        assert f"{relay[0]} {relay[1]} typ relay" in lines


def test_the_offer_is_built_from_the_preallocated_relays():
    """Each m-section's candidate block gets that socket's relay, and the
    allocation runs before the offer is built and sent."""
    tree = _tree()
    offer = function(tree, "_sdes_open_build_offer")
    relay_args = [
        name_of(c.args[4].args[0])
        for c in sorted(
            calls_to(offer, "_sdes_offer_candidate_lines"), key=lambda c: c.lineno
        )
        if isinstance(c.args[4], ast.Call)
        and "_relay_addrs" in names_in(c.args[4].func)
    ]
    assert relay_args == ["_audio_sock", "_video_sock"]
    impl = function(tree, "_open_sdes_stream_impl")
    (pre,) = calls_to(impl, "_sdes_open_turn_pre_allocation")
    (build,) = calls_to(impl, "_sdes_open_build_offer")
    (send,) = calls_to(impl, "_sdes_open_write_ffmpeg_sdp_and_send_webrtcreq")
    assert pre.lineno < build.lineno < send.lineno


def test_no_single_allocation_after_the_offer():
    """The only post-offer allocate is the retry, on both sockets, and only
    when the pre-offer allocation left nothing; its strays reach the window
    tagged with the socket that read them."""
    tree = _tree()
    assert not any(
        isinstance(n, ast.FunctionDef | ast.AsyncFunctionDef)
        and n.name == "_sdes_open_turn_allocation"
        for n in ast.walk(tree)
    )
    assert not any("sdes-turn-alloc " in string_parts(n) for n in ast.walk(tree))
    retry = function(tree, "_sdes_open_turn_allocation_fallback")
    (site,) = _allocate_sites(retry)
    loops = [
        n
        for n in ast.walk(retry)
        if isinstance(n, ast.For) and site in list(ast.walk(n))
    ]
    assert loops and {"_audio_sock", "_video_sock"} <= names_in(loops[0].iter)
    assert "_turn_strays" in names_in(retry)
    window = function(tree, "_sdes_open_stun_responder_window")
    assert "_turn_strays" in names_in(window)


def test_rc71_phase_order_after_the_offer():
    """Candidates (and any retry) are trickled, then the hole-punch, then
    the STUN window, as in rc71."""
    impl = function(_tree(), "_open_sdes_stream_impl")
    (announce,) = calls_to(impl, "_sdes_open_announce_candidates")
    (retry,) = calls_to(impl, "_sdes_open_turn_allocation_fallback")
    (punch,) = calls_to(impl, "_sdes_open_nat_hole_punch")
    (window,) = calls_to(impl, "_sdes_open_stun_responder_window")
    assert announce.lineno < retry.lineno < punch.lineno < window.lineno
