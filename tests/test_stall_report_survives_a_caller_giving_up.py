"""A caller that gives up early must still get the diagnosis.

The first-media stall report is emitted when the 75 s wait expires. Snapshots
give up sooner - the probe's outer bound is the snapshot budget plus 10, which
is 50 s - so a snapshot session that stalls is cancelled 25 s before the only
line that could explain it would have been written. Measured in run
31399498436: L2_F8A3 reported `snapshot_s=50.0` with its snapshot session
logging no first media at all, and the run contains no stall report for it.

That is the worst shape for a diagnostic: it is present exactly when it is not
needed and absent exactly when it is. Raising the snapshot budget earlier today
made the gap wider, not smaller.

So the report is emitted on cancellation too, with the time actually waited
rather than the constant, and saying which of the two happened - a caller giving
up at 50 s and a wait expiring at 75 s are different facts about the session.

Cancellation must still propagate. This package has already shipped one bug
where a handler caught CancelledError and returned normally, so the re-raise is
the property that matters most here and is asserted directly.
"""
import ast
import inspect
import os
import sys
import textwrap

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot_cameras.camera.sdes_open as so
from aidot_cameras.camera.sdes_open import _first_media_stall_report


def _report(**kw):
    args = dict(
        device_id="cam", waited_s=75.0, nominated=[("10.0.0.1", 5000)],
        use_candidate_sent=True, binding_success=0, trigger_sent=False,
        probes=[],
    )
    args.update(kw)
    return _first_media_stall_report(**args)


def test_the_report_states_the_time_actually_waited():
    # Not the constant. A cancelled wait that says 75 s is describing a deadline
    # the session never reached.
    assert "(50s)" in _report(waited_s=49.9)
    assert "(75s)" in _report(waited_s=75.0)


def test_a_cancelled_wait_says_so():
    # Otherwise a snapshot giving up at 50 s is indistinguishable in the log
    # from a wait that ran its full course, and the two mean different things
    # about the camera.
    cancelled = _report(waited_s=49.9, cancelled=True)
    expired = _report(waited_s=75.0)
    assert "cancel" in cancelled.lower()
    assert "cancel" not in expired.lower()


def _first_media_wait_handler():
    """The CancelledError handler guarding the first-media wait, as an AST."""
    # The public name is a thin allocate-and-hand-off wrapper; the wait lives in
    # the impl.
    src = inspect.getsource(so._SdesOpenMixin._open_sdes_stream_impl)
    tree = ast.parse(textwrap.dedent(src))
    for node in ast.walk(tree):
        if not isinstance(node, ast.Try):
            continue
        for handler in node.handlers:
            names = [n.id for n in ast.walk(handler) if isinstance(n, ast.Name)]
            # The handler calls the local emitter; the emitter is what wraps
            # _first_media_stall_report. Looking for the builder here would
            # miss the refactor that gave both exit paths one implementation.
            if "_report_first_media_stall" in names:
                return handler
    raise AssertionError(
        "no CancelledError handler around the first-media wait reports the stall")


def test_the_cancelled_path_reports_before_it_re_raises():
    handler = _first_media_wait_handler()
    # Ordering, not mere presence: a handler that re-raises first would never
    # reach the report, and a test that only checked both existed would pass.
    report_line = min(
        n.lineno for n in ast.walk(handler)
        if isinstance(n, ast.Name) and n.id == "_report_first_media_stall")
    raises = [n.lineno for n in ast.walk(handler) if isinstance(n, ast.Raise)]
    assert raises, "cancellation must propagate - see async_snapshot's own history"
    assert max(raises) > report_line, (
        "the re-raise must come after the report, or the report never runs")


def test_the_cancelled_path_re_raises_bare():
    # `raise` with no argument preserves the CancelledError. Raising something
    # else would turn a cancellation into a failure the caller cannot recognise.
    handler = _first_media_wait_handler()
    bare = [n for n in ast.walk(handler)
            if isinstance(n, ast.Raise) and n.exc is None]
    assert bare, "re-raise the CancelledError itself, unwrapped"


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
