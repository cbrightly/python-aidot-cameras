"""A failed container write must END the DTLS mux thread, not be swallowed.

Regression for the abandoned-mux-thread leak: teardown's join(2.0) gives up on
a thread blocked in a pipe write; once that pipe's ffmpeg dies the write raises
EPIPE forever.  The old per-packet swallow kept the abandoned thread looping,
stealing frames from the SHARED vq/aq that the next cycle's thread needed - the
new ffmpeg starved at its input probe and never bound its -listen port (the
field "dead DTLS video" of 2026-07).  A dead write now exits the thread and
leaves the queues alone.
"""

import os
import queue
import threading
import time

import pytest

av = pytest.importorskip("av")

from aidot_cameras.camera.protocol import _dtls_av_mux_run


def _h264_payloads(n=60):
    """Real encoded H.264 access units (the mux needs valid-ish annex-b).

    Noise frames compress terribly on purpose: the total must exceed PyAV's
    ~32KB avio buffer so the container performs REAL pipe writes (a smaller
    payload sits in the buffer forever and never observes the dead pipe).
    """
    import numpy as np

    rng = np.random.default_rng(7)
    enc = av.CodecContext.create("libx264", "w")
    enc.width, enc.height, enc.pix_fmt = 320, 240, "yuv420p"
    from fractions import Fraction

    enc.time_base = Fraction(1, 15)
    enc.options = {"tune": "zerolatency"}
    out = []
    for i in range(n):
        img = rng.integers(0, 255, (240, 320, 3), dtype=np.uint8)
        fr = av.VideoFrame.from_ndarray(img, format="rgb24").reformat(format="yuv420p")
        fr.pts = i
        for p in enc.encode(fr):
            out.append((bytes(p), bool(p.is_keyframe)))
    for p in enc.encode(None):
        out.append((bytes(p), bool(p.is_keyframe)))
    assert sum(len(d) for d, _ in out) > 100_000
    return out


def test_dead_pipe_ends_mux_thread_and_spares_queues():
    payloads = _h264_payloads()
    assert payloads and payloads[0][1], "first packet should be a keyframe"

    vq: queue.Queue = queue.Queue()
    aq: queue.Queue = queue.Queue()
    ts = 0
    for data, kf in payloads:
        vq.put((data, ts, kf))
        ts += 6000

    r, w = os.pipe()
    os.close(r)  # reader already gone -> every flushed write is EPIPE
    wfile = os.fdopen(w, "wb", buffering=0)
    progress = [time.monotonic()]
    stop_flag = threading.Event()

    t = threading.Thread(
        target=_dtls_av_mux_run, args=(vq, aq, wfile, progress, stop_flag), daemon=True
    )
    t.start()
    t.join(timeout=10.0)

    # The thread must have exited on its own (stop_flag was never set).
    assert not t.is_alive(), "mux thread must end on a dead pipe, not loop forever"
    assert not stop_flag.is_set()
    # And it must not have drained the whole queue into the dead pipe: once the
    # avio buffer first flushes and EPIPEs, remaining frames stay for the next
    # cycle's consumer.  (The avio buffer absorbs the first ~32KB, so a few
    # packets are legitimately consumed before the first real write.)
    assert not vq.empty(), "a dead mux must leave queued frames for the next cycle"


def test_healthy_pipe_still_muxes():
    payloads = _h264_payloads()
    vq: queue.Queue = queue.Queue()
    aq: queue.Queue = queue.Queue()
    ts = 0
    for data, kf in payloads:
        vq.put((data, ts, kf))
        ts += 6000

    r, w = os.pipe()
    got = [0]

    def drain():
        while True:
            try:
                b = os.read(r, 65536)
            except OSError:
                return
            if not b:
                return
            got[0] += len(b)

    threading.Thread(target=drain, daemon=True).start()
    wfile = os.fdopen(w, "wb", buffering=0)
    progress = [time.monotonic()]
    stop_flag = threading.Event()
    t = threading.Thread(
        target=_dtls_av_mux_run, args=(vq, aq, wfile, progress, stop_flag), daemon=True
    )
    t.start()
    time.sleep(1.5)
    # REGRESSION (interleave stall): with an AAC stream declared but zero audio
    # packets, libavformat used to buffer ALL video in its interleave queue -
    # bytes only appeared at close().  They must flow DURING muxing.
    assert got[0] > 10_000, (
        "video must reach the pipe while muxing, without any audio packets"
    )
    stop_flag.set()
    t.join(timeout=10.0)
    assert not t.is_alive()
    assert vq.empty(), "healthy mux consumes the queue"
