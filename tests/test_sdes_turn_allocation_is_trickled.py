"""One TURN allocation, made after the offer and trickled.

The SDES open used to make two blocking Allocates (audio + video, ~2-3 s)
before it built the offer. Now the offer carries host/srflx only, and one
Allocate on the audio socket (the BUNDLE transport) runs after webrtcReq and
is trickled as a relay candidate. Relay mode still allocates before the
offer, because it puts the relay in c=/m=. The allocate reads the media
socket, so a STUN packet it reads that is not its own response is kept for
the STUN window rather than dropped: the camera may already be probing.
"""

import ast
import socket
import struct
import threading

from aidot_cameras.camera.sdes_open import (
    _TURN_STRAY_CAP,
    STUN_MAGIC_COOKIE,
    turn_allocate_udp,
)
from tests.ast_guards import calls_to, enclosing, function, module_tree, names_in
from tests.ast_guards import name_of, parents


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


def test_before_the_offer_only_relay_mode_allocates():
    tree = _tree()
    pm = parents(tree)
    pre = function(tree, "_sdes_open_turn_pre_allocation")
    (site,) = _allocate_sites(pre)
    tests = [names_in(i.test) for i in enclosing(site, pm, ast.If)]
    assert any("_conn_mode" in t and "_skip_turn_prealloc" in t for t in tests)


def test_after_the_offer_one_allocate_on_the_audio_socket_feeds_the_window():
    tree = _tree()
    post = function(tree, "_sdes_open_turn_allocation")
    (site,) = _allocate_sites(post)
    assert not any(
        isinstance(n, (ast.For, ast.While))
        for n in ast.walk(post)
        if site in list(ast.walk(n))
    ), "one allocate, not one per socket"
    assert name_of(site.args[1]) == "_audio_sock"
    assert name_of(site.args[-1]) == "_turn_strays"
    window = function(tree, "_sdes_open_stun_responder_window")
    assert "_turn_strays" in names_in(window)


def test_host_and_srflx_are_trickled_before_the_allocate():
    post = function(_tree(), "_sdes_open_turn_allocation")
    (site,) = _allocate_sites(post)
    sends = calls_to(post, "_send_sdes_ice_cand")
    host_srflx = [
        c
        for c in sends
        if any(
            isinstance(v, ast.Constant) and "typ host" in str(v.value)
            for v in ast.walk(c)
        )
    ]
    relay = [
        c
        for c in sends
        if any(
            isinstance(v, ast.Constant) and "typ relay" in str(v.value)
            for v in ast.walk(c)
        )
    ]
    assert host_srflx and relay
    assert all(c.lineno < site.lineno for c in host_srflx)
    assert all(c.lineno > site.lineno for c in relay)


def test_the_hole_punch_goes_out_before_the_allocate():
    """Both sockets get their outbound NAT mapping right after webrtcReq; the
    allocate (up to 4 s on a silent TURN server) must not hold it back."""
    impl = function(_tree(), "_open_sdes_stream_impl")
    (punch,) = calls_to(impl, "_sdes_open_nat_hole_punch")
    (alloc,) = calls_to(impl, "_sdes_open_turn_allocation")
    (window,) = calls_to(impl, "_sdes_open_stun_responder_window")
    assert punch.lineno < alloc.lineno < window.lineno
