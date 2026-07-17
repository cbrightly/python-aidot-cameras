"""Demote expected ffmpeg signal-death warnings on locally-initiated teardown.

Teardown is SIGTERM-first with a SIGKILL fallback (SdesSession.stop(),
sdes.py); a battery camera's ffmpeg cannot exit promptly on a dead UDP input,
so the 5s wait routinely times out and the library SIGKILLs it. The bridge
observe loop (sdes_open.py) used to warn on ANY non-zero exit code, so a
completely normal teardown logged a WARNING ("ffmpeg exited with code -9").

A shared "teardown requested" flag is set by every locally-initiated
ffmpeg-kill path (SdesSession.stop(), the cold-open _reap(), the key-restart
proc replace, and the DTLS-fallback abort) and consulted by
_classify_ffmpeg_exit() to decide the log level: a signal death (negative rc)
during a requested teardown is expected -> DEBUG. Anything else (no teardown
in flight, or a positive ffmpeg error code even during teardown) is still
unexpected -> WARNING.
"""
import asyncio
import logging

from aidot.camera.sdes_open import _classify_ffmpeg_exit
from aidot.camera.sdes import SdesSession


def test_signal_death_during_teardown_is_quiet():
    assert _classify_ffmpeg_exit(-9, True) <= logging.INFO


def test_signal_death_without_teardown_still_warns():
    assert _classify_ffmpeg_exit(-9, False) == logging.WARNING


def test_error_code_during_teardown_still_warns():
    # Positive rc is a genuine ffmpeg error, not a signal death - even if a
    # teardown happened to be in flight, this is unexpected and stays loud.
    assert _classify_ffmpeg_exit(1, True) == logging.WARNING


def test_sigterm_death_during_teardown_is_quiet():
    assert _classify_ffmpeg_exit(-15, True) <= logging.INFO


class _FakeProc:
    def __init__(self):
        self._alive = True
        self.returncode = 0
        self.stderr = self

    def poll(self):
        return None if self._alive else self.returncode

    def terminate(self):
        self._alive = False

    def wait(self, timeout=None):
        self._alive = False
        return self.returncode

    def kill(self):
        self._alive = False

    def read(self):  # stderr.read()
        return b""


class _FakeSock:
    def close(self):
        pass


class _FakeQ:
    def put_nowait(self, _x):
        pass


def test_session_stop_sets_shared_teardown_flag():
    holder = [False]
    s = SdesSession(
        proc=_FakeProc(),
        sdp_path="/tmp/aidot_test_does_not_exist.sdp",
        outgoing_q=_FakeQ(),
        mqtt_fut=None,
        audio_sock=_FakeSock(),
        video_sock=_FakeSock(),
        cmd_chan=[None],
        talk_state=None,
        teardown_requested=holder,
    )
    assert holder[0] is False
    asyncio.run(s.stop())
    assert holder[0] is True


def test_session_stop_without_shared_holder_does_not_crash():
    # No teardown_requested passed - stop() must still work (defaults to a
    # private holder) rather than raising AttributeError.
    s = SdesSession(
        proc=_FakeProc(),
        sdp_path="/tmp/aidot_test_does_not_exist.sdp",
        outgoing_q=_FakeQ(),
        mqtt_fut=None,
        audio_sock=_FakeSock(),
        video_sock=_FakeSock(),
        cmd_chan=[None],
        talk_state=None,
    )
    asyncio.run(s.stop())
