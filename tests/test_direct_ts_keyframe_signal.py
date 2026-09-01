"""A late joiner is spliced where the mux says a keyframe starts.

`_DirectTsServer` holds a new consumer until it can hand over a decodable start.
It used to decide that from the container: any TS packet with the adaptation
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
came from ``_h264_has_keyframe`` at the tap, and then threw it away. It now
announces it, and the legacy container test survives only as a bounded fallback
for a source that never signals - so a stream that does not use the signal can
still be served rather than hanging forever.
"""
import os
import sys
import threading

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.protocol import _DirectTsServer

VIDEO_PID = 0x0100
AUDIO_PID = 0x0101


def _ts(pid: int, *, rai: bool = False) -> bytes:
    """One 188-byte TS packet, optionally flagging a random-access point."""
    hdr = bytearray(4)
    hdr[0] = 0x47
    hdr[1] = (pid >> 8) & 0x1F
    hdr[2] = pid & 0xFF
    hdr[3] = 0x30 if rai else 0x10       # adaptation+payload, or payload only
    body = bytes([1, 0x40]) if rai else b""   # adaptation_field_length=1, RAI
    pkt = bytes(hdr) + body + b"\xde\xad"
    return pkt + b"\xff" * (188 - len(pkt))


def _server():
    srv = _DirectTsServer.__new__(_DirectTsServer)
    srv._tail = b""
    srv._pat = b"P" * 188
    srv._pmt = b"M" * 188
    srv._pmt_pid = 0x1000
    srv._synced = False
    srv._kf_pending = False
    srv._kf_ever = False
    srv._waiting_since = None
    srv._lock = threading.Lock()
    sent = bytearray()

    class _Sock:
        def sendall(self, b):
            sent.extend(b)

    srv._client = _Sock()
    return srv, sent


def test_audio_random_access_does_not_splice_a_signalling_source():
    """The defect: audio RAI used to start the consumer mid-video-GOP."""
    srv, sent = _server()
    srv.mark_keyframe()                    # source signals, so it is trusted
    srv._kf_pending = False                # ...but no keyframe pending yet
    srv.write(_ts(AUDIO_PID, rai=True) * 20)
    assert not srv._synced
    assert bytes(sent) == b""


def test_keyframe_signal_splices_with_tables_first():
    srv, sent = _server()
    srv.write(_ts(AUDIO_PID, rai=True) * 3)
    assert not srv._synced
    srv.mark_keyframe()
    srv.write(_ts(VIDEO_PID))
    assert srv._synced
    out = bytes(sent)
    assert out[:188] == b"P" * 188, "PAT first"
    assert out[188:376] == b"M" * 188, "then PMT"
    assert len(out) == 3 * 188, "then the keyframe's packet"


def test_pending_flag_is_consumed_so_a_later_consumer_waits_again():
    srv, _ = _server()
    srv.mark_keyframe()
    srv.write(_ts(VIDEO_PID))
    assert srv._synced and not srv._kf_pending


def test_everything_flows_once_synced():
    srv, sent = _server()
    srv.mark_keyframe()
    srv.write(_ts(VIDEO_PID))
    before = len(bytes(sent))
    srv.write(_ts(AUDIO_PID) + _ts(VIDEO_PID))
    assert len(bytes(sent)) == before + 2 * 188


def test_non_signalling_source_still_starts_after_the_grace_period():
    """A writer that never calls mark_keyframe must not hang forever.

    This is the regression that gating solely on a video-pid flag caused:
    zero bytes, which is worse than the bug it was fixing.
    """
    srv, sent = _server()
    srv.write(_ts(AUDIO_PID, rai=True))
    assert not srv._synced, "must wait out the grace period first"
    srv._waiting_since -= _DirectTsServer._LEGACY_SYNC_AFTER_S + 1
    srv.write(_ts(AUDIO_PID, rai=True))
    assert srv._synced, "a non-signalling source must still be servable"
    assert bytes(sent), "and must actually receive bytes"


def test_a_signalling_source_never_falls_back():
    """Once a source has signalled, the container test must never win."""
    srv, _ = _server()
    srv.mark_keyframe()
    srv._kf_pending = False
    srv._waiting_since = 0.0               # grace long expired
    assert srv._legacy_sync_ok(_ts(AUDIO_PID, rai=True)) is False


def test_mux_forwards_the_signal_only_to_sinks_that_understand_it():
    """The fail-flag wrapper must not raise on an os pipe, which has no hook."""
    import inspect

    from aidot_cameras.camera import protocol

    src = inspect.getsource(protocol._dtls_av_mux_run)
    assert "wsink.mark_keyframe()" in src, "the mux must announce keyframes"
    assert 'getattr(self._f, "mark_keyframe", None)' in src, (
        "forwarding must be optional - the ffmpeg path's sink is a pipe"
    )
