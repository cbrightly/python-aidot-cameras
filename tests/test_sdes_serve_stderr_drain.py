"""Tests for the SDES serve ffmpeg stderr drain.

The serve is spawned with ``stderr=PIPE`` but the bridge loop never reads it, so an
un-drained pipe can fill and stall ffmpeg and a non-zero exit loses its reason.
``_start_serve_stderr_drain`` reads the pipe on a daemon thread and keeps a bounded
tail on ``proc._aidot_stderr_tail`` for the exit logger.
"""

import io
import time

from aidot_cameras.camera.sdes_open import _start_serve_stderr_drain


class _FakeProc:
    """Minimal stand-in exposing a readable ``stderr``, like ``subprocess.Popen``."""

    def __init__(self, data: bytes) -> None:
        self.stderr = io.BytesIO(data)


def _tail(proc) -> list:
    """The captured stderr tail (attribute is set dynamically by the drainer)."""
    return list(getattr(proc, "_aidot_stderr_tail"))


def _wait_until(pred, timeout: float = 2.0) -> None:
    deadline = time.monotonic() + timeout
    while not pred():
        if time.monotonic() > deadline:
            break
        time.sleep(0.01)


def test_stderr_drain_captures_lines() -> None:
    proc = _FakeProc(b"line one\nline two\nline three\n")
    _start_serve_stderr_drain(proc)
    _wait_until(lambda: _tail(proc)[-1:] == ["line three"])
    assert _tail(proc) == ["line one", "line two", "line three"]


def test_stderr_drain_is_bounded_to_the_tail() -> None:
    data = b"".join(f"l{i}\n".encode() for i in range(100))
    proc = _FakeProc(data)
    _start_serve_stderr_drain(proc, maxlines=10)
    _wait_until(lambda: _tail(proc)[-1:] == ["l99"])
    assert _tail(proc) == [f"l{i}" for i in range(90, 100)]


def test_stderr_none_is_safe() -> None:
    class _NoStderr:
        stderr = None

    proc = _NoStderr()
    _start_serve_stderr_drain(proc)  # must not raise
    assert _tail(proc) == []


def test_undecodable_bytes_do_not_crash_the_drain() -> None:
    proc = _FakeProc(b"good\n\xff\xfe bad bytes\ndone\n")
    _start_serve_stderr_drain(proc)
    _wait_until(lambda: _tail(proc)[-1:] == ["done"])
    tail = _tail(proc)
    assert tail[0] == "good"
    assert tail[-1] == "done"
    assert len(tail) == 3
