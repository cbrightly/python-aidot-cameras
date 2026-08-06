"""Camera media sockets must ask for a receive buffer big enough for a keyframe.

These sockets ran on the OS default - 208 KB on Home Assistant OS. An A001064
keyframe is 146-190 KB delivered as one burst of ~130 packets, so a single
keyframe nearly fills that buffer. Any delay in the reader (the GIL while
another camera's bridge runs, an ffmpeg write blocking) and the kernel drops the
tail of the burst, which ffmpeg reports as ``RTP: missed N packets`` while its
input queue backs up. The box carried 44.3 million ``RcvbufErrors`` against
161 million datagrams received over four days, and every UDP receive error on it
was a buffer overflow rather than a checksum or port error.
"""
import socket

from aidot_cameras.camera.sdes_open import (
    _MEDIA_RCVBUF_BYTES,
    _widen_media_rcvbuf,
)


def test_the_request_clears_a_keyframe_burst():
    # 190 KB is the largest keyframe measured on the A001064; the request has to
    # leave room for that plus scheduling jitter, not merely match it.
    assert _MEDIA_RCVBUF_BYTES >= 8 * 190 * 1024


def test_a_real_socket_gets_more_than_the_os_default():
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    try:
        default = sock.getsockopt(socket.SOL_SOCKET, socket.SO_RCVBUF)
        got = _widen_media_rcvbuf(sock, "video", "dev-abc")
        assert got > 0, "the option was not set at all"
        # Kernels clamp to rmem_max and report double what they reserve, so the
        # exact number is not assertable - only that it grew, and that it now
        # clears one keyframe.
        assert got >= default
        assert got >= 190 * 1024, f"granted {got} bytes, under one keyframe"
    finally:
        sock.close()


def test_failure_is_survivable():
    """A platform that refuses the option must not stop a camera streaming."""

    class _Refuses:
        def setsockopt(self, *_a):
            raise OSError("nope")

        def getsockopt(self, *_a):
            raise OSError("nope")

    assert _widen_media_rcvbuf(_Refuses(), "audio", "dev-abc") == 0


def test_both_media_sockets_are_widened_before_bind():
    """Pin the call sites: a socket widened after bind may already have dropped.

    The open path is one long method that cannot be called in isolation, so
    assert on its source - both the audio and video sockets must be widened, and
    each before its own bind.
    """
    import inspect

    from aidot_cameras.camera import sdes_open

    src = inspect.getsource(sdes_open)
    for kind in ("_audio_sock", "_video_sock"):
        widen = src.index(f"_widen_media_rcvbuf({kind}")
        bind = src.index(f"{kind}.bind(")
        assert widen < bind, f"{kind} is bound before its receive buffer is set"
