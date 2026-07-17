"""Gate the SDES bridge observe-loop break on the teardown flag.

During a key-restart (sdes_open.py's SRTP-key-mismatch branch, ~3680-3739) the
OLD ffmpeg proc is terminated and the shared teardown flag is set BEFORE the
NEW proc is spawned and _proc_holder[0] is repointed to it.  If the bridge
thread's observe-loop tick lands in that window, it used to see the OLD
proc's non-None exit code and break unconditionally - tearing down the bridge
loop, closing the loopback sockets, and starving the freshly restarted
ffmpeg (NO_MEDIA until the liveness watchdog forces a 40-60s reconnect).

The break decision is extracted into a pure helper, _bridge_should_break(rc,
teardown_requested), so the policy is unit-testable in isolation; the loop
itself is embedded in a large closure inside _open_sdes_stream_impl that
cannot be invoked standalone, so the composed loop-level tests below drive a
small mirror of the loop's shape (select-gated wait / proc-holder poll / the
pre-existing except-break on socket close) using the real, imported
_bridge_should_break to make the decision - the same function the production
loop calls - plus a source-inspection check that the production loop really
does call it, so the mirror cannot silently drift from the real code.
"""
import inspect
import select
import socket

import aidot.camera.sdes_open as sdes_open
from aidot.camera.sdes_open import _bridge_should_break


# --------------------------------------------------------------------- #
# Pure helper truth table
# --------------------------------------------------------------------- #

def test_break_on_clean_exit_without_teardown():
    assert _bridge_should_break(0, False) is True


def test_break_on_signal_death_without_teardown():
    assert _bridge_should_break(-9, False) is True


def test_no_break_on_signal_death_during_teardown():
    assert _bridge_should_break(-9, True) is False


def test_no_break_on_clean_exit_during_teardown():
    assert _bridge_should_break(0, True) is False


def test_no_break_while_still_running():
    assert _bridge_should_break(None, False) is False
    assert _bridge_should_break(None, True) is False


# --------------------------------------------------------------------- #
# Source-structure guard: the real loop must call the pure helper, not
# reimplement (or bypass) the decision inline.
# --------------------------------------------------------------------- #

def test_production_loop_calls_the_shared_helper():
    src = inspect.getsource(sdes_open)
    start = src.index("def _bridge_fn():")
    end = src.index("def _bridge_fn():", start + 1) if src.count(
        "def _bridge_fn():"
    ) > 1 else len(src)
    block = src[start:end]
    assert "_bridge_should_break(" in block, (
        "the bridge observe loop must delegate the break decision to "
        "_bridge_should_break so the policy stays unit-testable and in sync"
    )


# --------------------------------------------------------------------- #
# Loop-level behavior with fakes
# --------------------------------------------------------------------- #

class _FakeProc:
    """Minimal Popen stand-in: poll() returns the fixed rc once "exited"."""

    def __init__(self, rc):
        self._rc = rc

    def poll(self):
        return self._rc


def _run_observe_loop(sock_a, sock_b, proc_holder, teardown_holder,
                       max_ticks=100):
    """Mirror of the sdes_open.py bridge observe loop's shape.

    Same structure as the production loop: a select()-gated wait whose
    except clause breaks (the socket-close escape hatch), then a proc poll
    that only breaks when the real _bridge_should_break helper says so, with
    an exit-code log fired at most once per skip window.  Returns
    (outcome, ticks, log_count) so tests can assert on all three without any
    real sleeping or threading - select's timeout is kept tiny since the
    real 0.5s constant is not itself under test here.
    """
    exit_logged = False
    log_count = 0
    for tick in range(1, max_ticks + 1):
        try:
            select.select([sock_a, sock_b], [], [], 0.01)
        except Exception:
            return "closed", tick, log_count
        proc = proc_holder[0]
        if proc is not None:
            rc = proc.poll()
            if rc is not None:
                if rc != 0 and not exit_logged:
                    exit_logged = True
                    log_count += 1
                if _bridge_should_break(rc, bool(teardown_holder[0])):
                    return "broke", tick, log_count
                continue
            exit_logged = False
    return "exhausted", max_ticks, log_count


def _udp_pair():
    a = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    b = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    return a, b


def test_teardown_window_exit_does_not_break_the_loop():
    # Old proc reports a signal death while teardown is flagged (the
    # key-restart window) - the loop must NOT break on that tick.
    a, b = _udp_pair()
    try:
        proc_holder = [_FakeProc(-9)]
        teardown_holder = [True]
        outcome, ticks, log_count = _run_observe_loop(
            a, b, proc_holder, teardown_holder, max_ticks=3
        )
        assert outcome == "exhausted"
        assert log_count == 1  # logged once, not suppressed entirely
    finally:
        a.close()
        b.close()


def test_repoint_to_live_proc_resumes_normal_operation():
    # Same window, but the key-restart repoints _proc_holder[0] to the new,
    # live proc after the first tick - the loop must keep running (not
    # break) and must not re-log once the new proc is polling clean.
    a, b = _udp_pair()
    try:
        proc_holder = [_FakeProc(-9)]
        teardown_holder = [True]

        exit_logged = False
        log_count = 0
        outcomes = []
        for i in range(4):
            if i == 1:
                proc_holder[0] = _FakeProc(None)  # key-restart repoint
            try:
                select.select([a, b], [], [], 0.01)
            except Exception:
                outcomes.append("closed")
                break
            proc = proc_holder[0]
            rc = proc.poll()
            if rc is not None:
                if rc != 0 and not exit_logged:
                    exit_logged = True
                    log_count += 1
                if _bridge_should_break(rc, bool(teardown_holder[0])):
                    outcomes.append("broke")
                    break
            else:
                exit_logged = False
            outcomes.append("continued")
        assert outcomes == ["continued", "continued", "continued", "continued"]
        assert log_count == 1
    finally:
        a.close()
        b.close()


def test_genuine_teardown_closes_sockets_and_exits_via_except_break():
    # No key-restart repoint arrives; instead a real teardown (stop(), the
    # DTLS-fallback abort, or _reap()) closes the loopback sockets - select()
    # must raise and the loop must exit via the pre-existing except-break.
    a, b = _udp_pair()
    proc_holder = [_FakeProc(-9)]
    teardown_holder = [True]

    a.close()
    b.close()

    outcome, ticks, log_count = _run_observe_loop(
        a, b, proc_holder, teardown_holder, max_ticks=10
    )
    assert outcome == "closed"
    assert ticks == 1


def test_genuine_crash_without_teardown_still_breaks():
    # No teardown in flight: an unexpected ffmpeg crash must still break the
    # loop immediately, exactly as before this fix.
    a, b = _udp_pair()
    try:
        proc_holder = [_FakeProc(-9)]
        teardown_holder = [False]
        outcome, ticks, log_count = _run_observe_loop(
            a, b, proc_holder, teardown_holder, max_ticks=10
        )
        assert outcome == "broke"
        assert ticks == 1
        assert log_count == 1
    finally:
        a.close()
        b.close()


def test_log_fires_at_most_once_across_a_multi_tick_skip_window():
    a, b = _udp_pair()
    try:
        proc_holder = [_FakeProc(-9)]
        teardown_holder = [True]
        outcome, ticks, log_count = _run_observe_loop(
            a, b, proc_holder, teardown_holder, max_ticks=20
        )
        assert outcome == "exhausted"
        assert log_count == 1
    finally:
        a.close()
        b.close()
