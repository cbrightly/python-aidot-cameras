"""Instrumentation for a disagreement nobody has been able to see.

Two RTCP senders run on the same we-to-camera direction on the SDES bridge, and
they do not encrypt with the same key.  The PLI takes our own offer key; the RR
prefers the camera's answer key and only falls back to ours.  SRTP keys are
per-direction, so at most one of those can be the key the camera is actually
authenticating our RTCP against -- but there is no reported symptom either way,
and no log line today records which key either sender used, so a live capture
cannot currently tell you which one is right.

This note is what makes that answerable.  It reports, per sender, the key that
went out and where it came from, plus whether the two candidates differ at all
in this session -- when the camera echoes our own key back in its answer the two
senders are identical and the question is moot, so `differ=yes` is the only
case worth correlating.  With the note in the log, a `differ=yes` run whose
PLIs produce keyframes says the offer key is the one the camera authenticates
on this direction -- the RR has no comparably sharp outcome, so its line
records which key it used rather than proving that key right.

Deliberately no behavior change here -- see the module docstring of the fix.
"""
from aidot_cameras.camera.sdes_open import _srtp_tx_key_note

OFFER = "b3VyLW93bi1vZmZlci1rZXktMDAwMDAwMDA="
ANSWER = "Y2FtZXJhLWFuc3dlci1rZXktMDAwMDAwMDA="


def test_it_names_the_sender():
    """Two senders, one grep -- the line is useless if it does not say which."""
    note = _srtp_tx_key_note("PLI", OFFER, OFFER, ANSWER)

    assert "sender=PLI" in note


def test_it_says_the_key_came_from_our_offer():
    assert "used=offer" in _srtp_tx_key_note("PLI", OFFER, OFFER, ANSWER)


def test_it_says_the_key_came_from_the_cameras_answer():
    assert "used=answer" in _srtp_tx_key_note("RR", ANSWER, OFFER, ANSWER)


def test_it_flags_that_the_two_candidates_differ():
    """`differ=yes` is the case where the two senders cannot both be right."""
    assert "differ=yes" in _srtp_tx_key_note("RR", ANSWER, OFFER, ANSWER)


def test_it_flags_that_the_candidates_are_the_same_key():
    """A camera that echoes our key makes the disagreement unobservable."""
    assert "differ=no" in _srtp_tx_key_note("RR", OFFER, OFFER, OFFER)


def test_an_absent_answer_key_is_not_a_disagreement():
    """No answer key at all means every sender used ours; nothing to compare."""
    note = _srtp_tx_key_note("RR", OFFER, OFFER, "")

    assert "differ=no" in note
    assert "answer=none" in note


def test_it_records_key_prefixes_not_whole_keys():
    """Enough to tell two keys apart in a log, not enough to be one."""
    note = _srtp_tx_key_note("PLI", OFFER, OFFER, ANSWER)

    assert OFFER not in note
    assert ANSWER not in note
    assert OFFER[:8] in note
    assert ANSWER[:8] in note
