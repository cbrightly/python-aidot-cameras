"""Tests for the SDES serve ffmpeg stderr drain.

The serve is spawned with ``stderr=PIPE`` but the bridge loop never reads it, so an
un-drained pipe can fill and stall ffmpeg and a non-zero exit loses its reason.
``_start_serve_stderr_drain`` reads the pipe on a daemon thread and keeps a bounded
tail on ``proc._aidot_stderr_tail`` for the exit logger.
"""

import asyncio
import io
import logging
import time

from aidot_cameras.camera.sdes import SdesSession
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


class _TornDownProc:
    """A serve proc whose stderr pipe the drainer already emptied.

    ``stop()`` reads ``stderr`` after the process is gone; with the drain thread
    running that read returns nothing, so the teardown diagnostic has to come
    from the tail the drainer kept.
    """

    def __init__(self, tail: list) -> None:
        self._alive = True
        self.returncode = 0
        self.stderr = self
        self._aidot_stderr_tail = tail

    def poll(self):
        return None if self._alive else self.returncode

    def terminate(self):
        self._alive = False

    def wait(self, timeout=None):
        self._alive = False
        return self.returncode

    def kill(self):
        self._alive = False

    def read(self):  # stderr.read() - the drainer got there first
        return b""


class _FakeSock:
    def close(self) -> None:
        pass


class _FakeQ:
    def put_nowait(self, _x) -> None:
        pass


def _stopped_session(tail: list) -> None:
    """Run the real ``SdesSession.stop()`` against a drained serve proc."""
    session = SdesSession(
        proc=_TornDownProc(tail),
        sdp_path="/tmp/aidot_test_does_not_exist.sdp",
        outgoing_q=_FakeQ(),
        mqtt_fut=None,
        audio_sock=_FakeSock(),
        video_sock=_FakeSock(),
        cmd_chan=[None],
        talk_state=None,
    )
    asyncio.run(session.stop())


def test_teardown_logs_the_drained_tail_when_the_pipe_is_empty(caplog) -> None:
    # Without the fallback this diagnostic is lost entirely: the drain thread
    # consumed the pipe, so stop()'s stderr.read() has nothing left to log.
    caplog.set_level(logging.DEBUG, logger="aidot_cameras.camera.sdes")
    _stopped_session(["frame= 120 fps=20", "Error writing trailer: Broken pipe"])
    warnings = [
        r for r in caplog.records
        if r.levelno == logging.WARNING and "ffmpeg SDES stderr" in r.getMessage()
    ]
    assert warnings, "a mid-stream ffmpeg error must still reach the log"
    assert "Error writing trailer: Broken pipe" in warnings[0].getMessage()


def test_teardown_tail_still_demotes_the_expected_no_media_shape(caplog) -> None:
    # The tail feeds the SAME classifier, so a camera that never sent media does
    # not start warning once per retry just because the stderr route changed.
    caplog.set_level(logging.DEBUG, logger="aidot_cameras.camera.sdes")
    _stopped_session(["Output file is empty, nothing was encoded"])
    assert not [
        r for r in caplog.records
        if r.levelno == logging.WARNING and "ffmpeg SDES stderr" in r.getMessage()
    ]
    assert [
        r for r in caplog.records
        if r.levelno == logging.DEBUG and "ffmpeg SDES stderr" in r.getMessage()
    ]


def test_teardown_without_a_tail_logs_nothing(caplog) -> None:
    caplog.set_level(logging.DEBUG, logger="aidot_cameras.camera.sdes")
    _stopped_session([])
    assert not [
        r for r in caplog.records if "ffmpeg SDES stderr" in r.getMessage()
    ]


def test_undecodable_bytes_do_not_crash_the_drain() -> None:
    proc = _FakeProc(b"good\n\xff\xfe bad bytes\ndone\n")
    _start_serve_stderr_drain(proc)
    _wait_until(lambda: _tail(proc)[-1:] == ["done"])
    tail = _tail(proc)
    assert tail[0] == "good"
    assert tail[-1] == "done"
    assert len(tail) == 3
