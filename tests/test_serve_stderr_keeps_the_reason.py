"""The exit reason must survive ffmpeg's own noise.

Measured 2026-08-23 on an A001513 that dies every ~3 minutes: the 40-line tail
captured at exit was 40 consecutive `Non-monotonic DTS` warnings and nothing
else, so three separate investigations of that camera have never seen why
ffmpeg actually stopped.

The noise is not incidental, it is structural. Both camera families step their
RTP timestamp backward every exactly 30.0 s -- the A001064 by 0.05-0.35 s, the
A001513 by ~2.195 s -- and `-c copy` emits one `Non-monotonic DTS` line per
frame until the input catches up. At 15 fps a 2.2 s step is ~33 lines, several
times a minute, plus a `RTP: missed N packets` / `max delay reached` pair for
every lost packet on a lossy link. A fixed-size tail of the last N lines is
guaranteed to hold only that.

So the drain keeps a second, separate tail of lines that are NOT the known
repetitive noise. The raw tail still shows what the stream was doing; the
notable tail shows the sentence that explains the exit.
"""
import io

from aidot_cameras.camera.sdes_open import _start_serve_stderr_drain


class _Proc:
    """Minimal stand-in for a Popen with a readable stderr pipe."""

    def __init__(self, lines):
        self.stderr = io.BytesIO(b"".join(l.encode() + b"\n" for l in lines))


def _drained(lines, **kw):
    proc = _Proc(lines)
    _start_serve_stderr_drain(proc, **kw)
    # the drain thread reads a BytesIO to EOF immediately
    for _ in range(200):
        if proc.stderr.tell() >= len(proc.stderr.getvalue()):
            break
    import time
    time.sleep(0.05)
    return proc


NOISE = "[vost#0:0/copy @ 0x7f] Non-monotonic DTS; previous: 1, current: 0; changing to 2."
LOSS = "[in#0/sdp @ 0x7f] RTP: missed 2 packets"
DELAY = "[in#0/sdp @ 0x7f] max delay reached. need to consume packet"


def test_the_raw_tail_still_shows_what_the_stream_was_doing():
    proc = _drained([NOISE] * 60)
    assert len(proc._aidot_stderr_tail) == 40
    assert all("Non-monotonic" in line for line in proc._aidot_stderr_tail)


def test_the_reason_survives_being_buried_by_noise():
    # The line that explains the exit, then enough noise to flush a 40-line tail.
    reason = "[out#0/rtsp @ 0x7f] Error writing trailer: Broken pipe"
    proc = _drained([reason] + [NOISE] * 200)
    assert reason not in proc._aidot_stderr_tail, "precondition: buried in the raw tail"
    assert reason in proc._aidot_stderr_notable


def test_the_two_other_repetitive_lines_are_noise_too():
    # A lossy link emits these constantly; they would flush the reason just as
    # effectively as the DTS warnings.
    reason = "Connection to tcp://127.0.0.1:8554 failed: Connection refused"
    proc = _drained([reason] + [LOSS, DELAY] * 100)
    assert reason in proc._aidot_stderr_notable


def test_the_notable_tail_is_bounded():
    proc = _drained([f"distinct error {i}" for i in range(100)])
    assert len(proc._aidot_stderr_notable) <= 20
    assert "distinct error 99" in proc._aidot_stderr_notable


def test_a_clean_exit_leaves_both_tails_empty():
    proc = _drained([])
    assert list(proc._aidot_stderr_tail) == []
    assert list(proc._aidot_stderr_notable) == []
