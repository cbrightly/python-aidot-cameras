"""`nominated=none` has two causes and they want different investigations.

The A001064 has been in a persistent no-media state since 2026-08-10, and its
reports all read `nominated=none; use-candidate=not-sent; probes=none`. Nothing
was nominated - but the report does not say WHY there was nothing to nominate,
and there are two answers:

  * the camera never answered at all, which is a signaling problem; or
  * it answered with no ICE candidates, which is the camera's own gathering.

One is investigated at MQTT and the cloud, the other at the camera. Reading the
wrong one first is how a fortnight goes missing, and the existing corpus already
contains a `(no ICE creds in answer)` row that nothing since has been able to
tell apart from a lost answer.

So the report carries what the answer was: absent, or present with a candidate
count. The count matters on its own - an answer with credentials and zero
candidates is a different camera state from one with three that are all
unreachable, and only the second is an ICE problem.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.sdes_open import _first_media_stall_report


def _report(**over):
    kwargs = dict(
        device_id="cam1", waited_s=75.0, nominated=[],
        use_candidate_sent=False, binding_success=0, trigger_sent=False,
        probes=[],
    )
    kwargs.update(over)
    return _first_media_stall_report(**kwargs)


def test_an_answer_that_never_arrived_is_named_as_absent():
    line = _report(answer_cands=None)
    assert "answer=none" in line
    assert "never answered" in line, (
        "with no answer at all the line should say so - that is a signaling "
        "failure and does not belong to ICE")


def test_an_answer_with_no_candidates_is_distinguished_from_no_answer():
    line = _report(answer_cands=0)
    assert "answer=0-candidates" in line
    assert "answer=none" not in line
    assert "never answered" not in line, (
        "the camera did answer - saying otherwise sends the reader to the "
        "wrong subsystem")


def test_an_answer_with_candidates_reports_how_many():
    line = _report(answer_cands=3, nominated=[("10.0.0.1", 5000)])
    assert "answer=3-candidates" in line


def test_the_absent_answer_note_is_not_emitted_when_media_simply_stalled():
    # A session that answered, nominated and still failed must not be labelled
    # a signaling failure.
    line = _report(answer_cands=3, nominated=[("10.0.0.1", 5000)],
                   use_candidate_sent=True, binding_success=4,
                   trigger_sent=True)
    assert "never answered" not in line


def test_the_field_is_omitted_rather_than_guessed_when_unknown():
    # Callers that cannot determine it must not have a zero invented for them:
    # "we did not look" and "there were none" are the confusion this whole
    # module keeps having to undo.
    line = _report()
    assert "answer=" not in line


def test_the_line_is_still_one_line():
    assert "\n" not in _report(answer_cands=0)


# --------------------------------------------------------------------------- #
# The derivation, not just the rendering. The field's whole purpose is to keep
# "never answered" and "answered with nothing" apart, so the code that decides
# which one to report is where the mistake would actually be made.
# --------------------------------------------------------------------------- #
class _Fut:
    """The answer future, in the states the stall path can find it in."""

    def __init__(self, *, done=True, cancelled=False, exc=None, result=None):
        self._done, self._cancelled = done, cancelled
        self._exc, self._result = exc, result

    def done(self):
        return self._done

    def cancelled(self):
        return self._cancelled

    def exception(self):
        return self._exc

    def result(self):
        return self._result


def _derive(pre_launch, fut):
    from aidot_cameras.camera.sdes_open import _stall_answer_candidates
    return _stall_answer_candidates(pre_launch, fut)


def test_an_answer_that_arrived_with_an_empty_sdp_is_not_called_absent():
    # The trap: an SDP string that is present but empty is falsy, and treating
    # falsy as "no answer" claims the camera never replied when it did. That is
    # the exact error this field exists to prevent, made by the field itself.
    assert _derive("", _Fut(result={"sdp": ""})) == 0


def test_an_answer_with_no_candidate_lines_counts_zero():
    assert _derive("", _Fut(result={"sdp": "v=0\r\na=ice-ufrag:x\r\n"})) == 0


def test_candidates_are_counted_from_the_late_answer_too():
    sdp = "a=candidate:1 1 udp 1 10.0.0.1 1 typ host\r\na=candidate:2 1 udp 1 10.0.0.2 2 typ srflx\r\n"
    assert _derive("", _Fut(result={"sdp": sdp})) == 2


def test_the_pre_launch_snapshot_is_preferred_when_present():
    assert _derive("a=candidate:1 1 udp 1 10.0.0.1 1 typ host\r\n", None) == 1


def test_a_cancelled_answer_wait_means_the_camera_never_answered():
    assert _derive("", _Fut(done=False, cancelled=True)) is None


def test_an_answer_future_that_failed_is_reported_as_unknown():
    # An exception is not evidence the camera stayed silent - it could be ours.
    assert _derive("", _Fut(exc=RuntimeError("boom"))) == -1


def test_a_future_that_has_not_resolved_is_unknown_not_absent():
    assert _derive("", _Fut(done=False)) == -1


def test_no_future_at_all_is_unknown():
    assert _derive("", None) == -1


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
