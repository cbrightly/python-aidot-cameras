"""A playback open holds the serve's packets until the serve is listening.

The camera starts the clip the moment PLAY_START lands and the serve is
launched on the first video packet, so the clip's opening packets - the H265
parameter sets and first keyframe among them - went to a loopback port nothing
had bound. Nothing decoded until the camera repeated them: the first served
frame was ~4 s into the clip (A001064, 2026-10-27).
"""

import ast

from aidot_cameras.camera.sdes_open import (
    _MEDIA_HOLD_MAX,
    _MediaHold,
    _new_media_hold,
)
from tests.ast_guards import enclosing, function, module_tree, parents


class _Sock:
    def __init__(self, log):
        self.log = log

    def sendto(self, pkt, addr):
        self.log.append((pkt, addr[1]))


def test_live_opens_do_not_hold():
    assert _new_media_hold(None) is None
    assert isinstance(_new_media_hold(object()), _MediaHold)


def test_held_until_released_then_in_order_before_anything_new():
    sent = []
    sock = _Sock(sent)
    hold = _MediaHold()
    for i in range(5):
        hold.send(sock, b"p%d" % i, 5000 + i % 2)
    assert sent == []
    hold.release()
    hold.send(sock, b"new", 5000)
    assert [p for p, _ in sent] == [b"p0", b"p1", b"p2", b"p3", b"p4", b"new"]
    assert [port for _, port in sent][:2] == [5000, 5001]
    hold.send(sock, b"after", 5001)
    assert sent[-1] == (b"after", 5001)


def test_a_release_with_nothing_new_is_flushed_on_the_tick():
    sent = []
    hold = _MediaHold()
    hold.send(_Sock(sent), b"a", 1)
    assert not hold.pending_flush()
    hold.release()
    assert hold.pending_flush()
    assert hold.flush() == 1
    assert sent == [(b"a", 1)] and not hold.pending_flush()


def test_the_flush_is_paced_and_reported():
    sent, pauses, lines = [], [], []
    hold = _MediaHold()
    for i in range(40):
        hold.send(_Sock(sent), b"%d" % i, 1)
    hold.release()
    hold.flush(pace=pauses.append, log=lines.append)
    assert len(sent) == 40
    assert len(pauses) == 2  # after packets 16 and 32
    assert lines == ["bridge: released 40 held packet(s) to the serve"]


def test_the_hold_is_bounded(caplog):
    sent = []
    hold = _MediaHold(limit=3)
    for i in range(5):
        hold.send(_Sock(sent), b"%d" % i, 1)
    assert hold.dropped == 2
    hold.release()
    assert hold.flush() == 3
    assert _MEDIA_HOLD_MAX >= 1000
    # The hole it leaves is not a debug line only.
    assert any(
        r.levelname == "WARNING" and "2 packet(s)" in r.getMessage()
        for r in caplog.records
    )


def test_every_loopback_send_in_the_bridge_goes_through_the_hold():
    bridge = function(module_tree("aidot_cameras.camera.sdes_open"), "make_sdes_bridge")
    up = parents(bridge)
    sends = [
        n
        for n in ast.walk(bridge)
        if isinstance(n, ast.Call)
        and getattr(n.func, "attr", None) == "sendto"
        and "127.0.0.1" in ast.unparse(n)
    ]
    assert sends
    for n in sends:
        fns = enclosing(n, up, (ast.FunctionDef,))
        assert fns and fns[0].name == "_br_lo_send", n.lineno


def test_the_open_releases_after_the_serve_is_bound():
    src = ast.unparse(
        function(
            module_tree("aidot_cameras.camera.sdes_open"), "_open_sdes_stream_impl"
        )
    )
    bound = src.index("_udp_port_bound(_lo_audio_port)")
    released = src.index("_media_hold.release()")
    created = src.index("_media_hold = _new_media_hold(ps.sd_playback)")
    bridge = src.index("make_sdes_bridge(")
    assert created < bridge < bound < released


def test_a_playback_serve_keeps_what_ffmpeg_reads_while_probing():
    # -fflags nobuffer dropped the clip's first ~3 s (A001064, 2026-10-27).
    from aidot_cameras.camera.client import _build_sdes_serve_cmd

    def fflags(**kw):
        cmd = _build_sdes_serve_cmd(sdp_path="/x.sdp", output_path="/o.ts", **kw)
        return cmd[cmd.index("-fflags") + 1]

    assert "nobuffer" in fflags()
    assert "nobuffer" not in fflags(keep_probe_packets=True)
    assert "discardcorrupt" in fflags(keep_probe_packets=True)


def test_the_open_asks_for_it_only_when_playing_back():
    calls = [
        c
        for c in calls_to_serve_cmd()
        if any(k.arg == "keep_probe_packets" for k in c.keywords)
    ]
    assert len(calls) == 1
    (kw,) = [k for k in calls[0].keywords if k.arg == "keep_probe_packets"]
    assert ast.unparse(kw.value) == "ps.sd_playback is not None"


def calls_to_serve_cmd():
    from tests.ast_guards import calls_to

    return calls_to(
        module_tree("aidot_cameras.camera.sdes_open"), "_build_sdes_serve_cmd"
    )


def _rtp(seq, ssrc=0x1257, pt=8):
    return (
        bytes([0x80, pt])
        + seq.to_bytes(2, "big")
        + bytes(4)
        + ssrc.to_bytes(4, "big")
        + b"x"
    )


def test_a_packet_that_arrives_twice_is_forwarded_once():
    # The reference box, 2026-10-28: every audio packet came twice (two
    # routes to the camera), and the copied G.711 ran twice as long as the
    # picture.
    sent, lines = [], []
    hold = _MediaHold()
    hold.release()
    for seq in (1, 1, 2, 3, 2, 3, 4):
        hold.send(_Sock(sent), _rtp(seq), 5000, log=lines.append)
    assert [int.from_bytes(p[2:4], "big") for p, _ in sent] == [1, 2, 3, 4]
    assert hold.duplicates == 3
    assert lines.count("bridge: dropping RTP packets that arrive twice") == 1


def test_only_the_same_stream_and_port_count_as_a_repeat():
    sent = []
    hold = _MediaHold()
    hold.release()
    hold.send(_Sock(sent), _rtp(7), 5000)
    hold.send(_Sock(sent), _rtp(7, ssrc=0x1259, pt=97), 5000)  # other SSRC
    hold.send(_Sock(sent), _rtp(7), 5002)  # other port
    rtcp = bytes([0x80, 200]) + bytes(10)
    hold.send(_Sock(sent), rtcp, 5000)
    hold.send(_Sock(sent), rtcp, 5000)  # RTCP is never deduplicated
    assert len(sent) == 5 and hold.duplicates == 0


def test_a_repeat_while_held_is_dropped_too():
    sent = []
    hold = _MediaHold()
    for seq in (10, 10, 11):
        hold.send(_Sock(sent), _rtp(seq), 5000)
    hold.release()
    hold.flush()
    assert len(sent) == 2 and hold.duplicates == 1


def test_the_window_forgets_old_numbers_so_a_wrapped_sequence_plays():
    from aidot_cameras.camera.sdes_open import _DUPLICATE_WINDOW

    sent = []
    hold = _MediaHold()
    hold.release()
    n = 65536 + 10  # past a full wrap of the 16-bit sequence number
    for i in range(n):
        hold.send(_Sock(sent), _rtp(i & 0xFFFF), 5000)
    assert len(sent) == n and hold.duplicates == 0
    assert _DUPLICATE_WINDOW < 65536 // 4
