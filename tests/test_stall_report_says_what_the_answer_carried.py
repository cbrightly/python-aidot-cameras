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
