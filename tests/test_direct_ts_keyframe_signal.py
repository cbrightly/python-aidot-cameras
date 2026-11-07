"""A late joiner is spliced where the mux says a keyframe starts.

The DTLS pull serve (``ts_fanout.TsServer``, a ``TsChannel`` on the advertised
port) holds a new consumer until it can hand over a decodable start. The first
server decided that from the container: any TS packet with the adaptation
field's random_access_indicator set. That flag is per-PID, and AAC audio sets it
roughly 50x/s against a video keyframe every ~2 s, so a late joiner was spliced
onto audio and received video mid-GOP against an SPS/PPS it never got -
"non-existing PPS 0 referenced", a video track that never produces a frame.
Only a consumer present from byte 0 escaped, and go2rtc pulls lazily when a
viewer connects, so it is always late.

Gating on the flag being set on the VIDEO pid instead was tried on hardware
(2026-08-29) and served ZERO bytes: PyAV's mpegts muxer does not set it on video
in this stream, so the condition never became true. That attempt was reverted.

The mux already knows. It pulls ``(data, ts, kf)`` off the queue, where ``kf``
came from ``_h264_has_keyframe`` at the tap, and announces it
(``mark_keyframe``). The channel starts a consumer on that signal only; the
first server's fallback to the container flag, for a source that never
signals, went with it: the mux is the only source and always signals (pinned
at the bottom of this file).
"""

import os
import socket
import sys
import time

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.ts_fanout import TsChannel

VIDEO_PID = 0x0100
AUDIO_PID = 0x0101


def _ts(pid: int, *, rai: bool = False) -> bytes:
    """One 188-byte TS packet, optionally flagging a random-access point."""
    hdr = bytearray(4)
    hdr[0] = 0x47
    hdr[1] = (pid >> 8) & 0x1F
    hdr[2] = pid & 0xFF
    hdr[3] = 0x30 if rai else 0x10  # adaptation+payload, or payload only
    body = bytes([1, 0x40]) if rai else b""  # adaptation_field_length=1, RAI
    pkt = bytes(hdr) + body + b"\xde\xad"
    return pkt + b"\xff" * (188 - len(pkt))


def _pat_pmt():
    """A PAT naming PMT pid 0x1000, then that PMT (contents irrelevant)."""
    pat = bytearray(_ts(0))
    pat[1] |= 0x40
    pat[4:17] = bytes([0, 0x00, 0xB0, 0x0D, 0, 1, 0xC1, 0, 0, 0, 1, 0xF0, 0x00])
    return bytes(pat), _ts(0x1000)


def _channel():
    ch = TsChannel("/cam")
    a, b = socket.socketpair()
    ch.add_consumer(a)
    pat, pmt = _pat_pmt()
    ch.write(pat + pmt)
    return ch, b, pat, pmt


def _read(sock, timeout=0.5):
    sock.settimeout(timeout)
    buf = b""
    end = time.time() + timeout
    while time.time() < end:
        try:
            c = sock.recv(65536)
        except OSError:
            break
        if not c:
            break
        buf += c
    return buf


def test_audio_random_access_does_not_start_a_consumer():
    """The defect: audio RAI used to start the consumer mid-video-GOP."""
    ch, peer, _pat, _pmt = _channel()
    try:
        ch.write(_ts(AUDIO_PID, rai=True) * 20)
        assert _read(peer) == b""
        assert ch.started_count() == 0
    finally:
        ch.close()


def test_keyframe_signal_starts_with_tables_first():
    ch, peer, pat, pmt = _channel()
    try:
        ch.write(_ts(AUDIO_PID, rai=True) * 3)
        ch.mark_keyframe()
        ch.write(_ts(VIDEO_PID))
        out = _read(peer)
        assert out[:188] == pat, "PAT first"
        assert out[188:376] == pmt, "then PMT"
        assert out[376:] == _ts(VIDEO_PID), "then the keyframe's packet"
    finally:
        ch.close()


def test_the_signal_is_consumed_so_a_later_consumer_waits_again():
    ch, peer, _pat, _pmt = _channel()
    try:
        ch.mark_keyframe()
        ch.write(_ts(VIDEO_PID))
        a, b = socket.socketpair()
        ch.add_consumer(a)
        ch.write(_ts(VIDEO_PID) * 3)
        assert _read(b) == b"", "no second start without a second keyframe"
        assert len(_read(peer)) == 6 * 188, "the first keeps flowing"
    finally:
        ch.close()


def test_a_signal_with_no_whole_packet_waits_for_one():
    """A keyframe whose first bytes are a partial packet keeps its signal for
    the write that completes it."""
    ch, peer, pat, pmt = _channel()
    try:
        pkt = _ts(VIDEO_PID)
        ch.mark_keyframe()
        ch.write(pkt[:100])
        ch.write(pkt[100:])
        assert _read(peer) == pat + pmt + pkt
    finally:
        ch.close()


def _mux_into(sink):
    """Run the real DTLS mux over one H.264 keyframe into ``sink``."""
    import queue
    import threading
    import time

    from aidot_cameras.camera.protocol import _dtls_av_mux_run

    vq, aq = queue.Queue(), queue.Queue()
    idr = b"\x00\x00\x00\x01\x67\x42\x00\x0a" + b"\x00\x00\x00\x01\x65" + b"\x88" * 40
    for i in range(3):
        vq.put((idr, i * 3000, True))
    stop = threading.Event()
    t = threading.Thread(
        target=_dtls_av_mux_run,
        args=(vq, aq, sink, [time.monotonic()], stop),
        daemon=True,
    )
    t.start()
    deadline = time.monotonic() + 10
    while not vq.empty() and time.monotonic() < deadline:
        time.sleep(0.02)
    time.sleep(0.3)
    stop.set()
    t.join(timeout=10)
    assert not t.is_alive(), "the mux thread must end once stopped"


def test_mux_forwards_the_signal_only_to_sinks_that_understand_it():
    """The mux announces each keyframe to a sink that can take it (the pull
    serve), and the fail-flag wrapper must not raise on one that cannot
    (stdout and a recording file have no hook)."""
    import io

    class _Signalling(io.BytesIO):
        marks = 0

        def mark_keyframe(self):
            self.marks += 1

    signalling = _Signalling()
    _mux_into(signalling)
    assert signalling.marks >= 1, "the mux must announce keyframes"
    assert signalling.getvalue(), "and still write the stream"

    plain = io.BytesIO()
    _mux_into(plain)  # no mark_keyframe: must neither raise nor stop the mux
    assert plain.getvalue()
