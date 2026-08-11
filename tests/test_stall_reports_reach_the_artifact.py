"""The stall diagnosis has to survive the log, because the log does not.

The first-media stall report is a WARNING the library emits, and until now the
only place it existed was the CI log. Three consecutive fleet runs on
2026-08-10/11 dropped the entire `Validate every camera` step from their logs -
375 lines against ~2200 on every earlier run that day - while a required camera
returned no media six attempts in a row. The diagnostic was working and
unreadable, which is the same as not having it.

The report artifact is written by the harness itself and survives whatever the
runner's log capture does, so the reports belong in it. This collects them per
attempt rather than per run: which attempt stalled is part of the finding, and a
run-level list would lose it on a camera that fails once and passes on retry.

Matching is on the report's own opening words rather than the logger name or the
level, so an unrelated WARNING from the same module cannot be mistaken for a
stall - and a rename of the report text fails these tests loudly instead of
silently collecting nothing.
"""
import logging
import os
import sys

sys.path.insert(0, os.path.join(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "scripts"))

from live_validate import _StallCollector


def _record(msg: str, level: int = logging.WARNING) -> logging.LogRecord:
    return logging.LogRecord(
        name="aidot_cameras.camera.sdes_open", level=level,
        pathname=__file__, lineno=1, msg="%s", args=(msg,), exc_info=None)


_STALL = ("camera abc123: SDES first media never arrived (75s)."
          " nominated=none; use-candidate=not-sent; binding-success=0;"
          " trigger=not-sent; probes=none.")


def test_a_stall_report_is_collected():
    c = _StallCollector()
    c.emit(_record(_STALL))
    assert c.drain() == [_STALL]


def test_draining_empties_it_so_attempts_do_not_inherit_each_other():
    # Attempt 2 reporting attempt 1's stall would be worse than reporting
    # nothing: it would name a failure on a session that succeeded.
    c = _StallCollector()
    c.emit(_record(_STALL))
    assert c.drain() == [_STALL]
    assert c.drain() == []


def test_unrelated_warnings_from_the_same_module_are_not_collected():
    c = _StallCollector()
    c.emit(_record("camera abc123: ffmpeg exited with code 255"))
    assert c.drain() == []


def test_several_stalls_in_one_attempt_are_all_kept():
    # A snapshot opens its own session, so one attempt can produce more than
    # one - and the second is often the one that explains the first.
    c = _StallCollector()
    c.emit(_record(_STALL))
    c.emit(_record(_STALL.replace("abc123", "def456")))
    assert len(c.drain()) == 2


def test_a_record_that_cannot_be_formatted_is_dropped_quietly():
    # A logging handler that raises takes down the call that logged, which here
    # is inside the library's own stall path. Diagnosis must never do that.
    bad = logging.LogRecord(
        name="aidot_cameras.camera.sdes_open", level=logging.WARNING,
        pathname=__file__, lineno=1, msg="%s %s", args=("only-one",),
        exc_info=None)
    c = _StallCollector()
    c.emit(bad)
    assert c.drain() == []


if __name__ == "__main__":
    import traceback
    _fns = [v for k, v in sorted(globals().items()) if k.startswith("test_")]
    _fail = 0
    for _fn in _fns:
        try:
            _fn()
            print(f"PASS {_fn.__name__}")
        except Exception:
            _fail += 1
            print(f"FAIL {_fn.__name__}")
            traceback.print_exc()
    raise SystemExit(1 if _fail else 0)
