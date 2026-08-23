"""A serve we stopped on purpose must not be reported as a crash.

This is the "publisher dies every ~3 minutes" that three separate
investigations chased on the reference A001513.

`_classify_ffmpeg_exit` quiets a locally-initiated teardown only when the exit
code is NEGATIVE, on the stated premise that "a normal locally-initiated stop
routinely ends in a signal death (rc < 0, e.g. -9 SIGKILL / -15 SIGTERM)".

That premise does not hold for SIGTERM. ffmpeg installs its own SIGTERM
handler and does not die by the signal: it unwinds and calls `exit_program()`,
which returns **255** when a signal was received. Python's `Popen.poll()`
reports a negative value only when the child is killed BY a signal it did not
handle -- so our SIGTERM-first teardown produces `rc = 255`, positive, and the
classifier fell through to WARNING every single time.

Everything that made this look like a crash follows from that:

* exit code exactly 255, every time
* no stderr whatsoever -- ffmpeg's "Exiting normally, received signal 15." is
  logged at INFO, and the serve runs with `-loglevel warning`
* a ~3 minute cadence, which is the idle-release window, not a fault
* an offline reproduction of the camera's timestamp behaviour that never died,
  because nothing was signalling it

So: 255 WITH a teardown in flight is the expected end of a stop. 255 with no
teardown in flight is still worth shouting about -- something outside us
signalled the process -- and that distinction is the whole point of the
function, so it has to be kept.
"""
import logging

from aidot_cameras.camera.sdes_open import _classify_ffmpeg_exit


def test_a_sigterm_handled_exit_during_teardown_is_not_a_warning():
    # The reference case: SdesSession.stop() sends SIGTERM, ffmpeg handles it
    # and exits 255.
    assert _classify_ffmpeg_exit(255, True) == logging.DEBUG


def test_the_same_code_with_no_teardown_in_flight_still_warns():
    # Nobody asked for this. Something else signalled the process, and that is
    # exactly what this function exists to surface.
    assert _classify_ffmpeg_exit(255, False) == logging.WARNING


def test_an_uncaught_signal_death_during_teardown_stays_quiet():
    # The SIGKILL fallback, unchanged.
    assert _classify_ffmpeg_exit(-9, True) == logging.DEBUG
    assert _classify_ffmpeg_exit(-15, True) == logging.DEBUG


def test_a_real_ffmpeg_error_during_teardown_still_warns():
    # A positive code that is NOT the signal exit means ffmpeg failed on its
    # own terms; a teardown racing it must not hide that.
    assert _classify_ffmpeg_exit(1, True) == logging.WARNING
    assert _classify_ffmpeg_exit(69, True) == logging.WARNING


def test_a_clean_exit_and_the_broken_pipe_are_unchanged():
    assert _classify_ffmpeg_exit(224, False) == logging.DEBUG   # EPIPE
    assert _classify_ffmpeg_exit(-9, False) == logging.WARNING  # killed by nobody
