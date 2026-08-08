"""Instrumentation for a disagreement nobody has been able to see.

Three RTCP senders run on the same we-to-camera direction on the SDES bridge --
PLI, REMB and RR -- but only two of them choose a key: REMB reuses the PLI's
cached SRTP session.  The two choices do not agree.  The PLI takes our own offer
key; the RR prefers the camera's answer key and only falls back to ours.  SRTP
keys are per-direction, so at most one of those can be the key the camera is
actually authenticating our RTCP against -- but there is no reported symptom
either way, and no log line today records which key any sender used, so a live
capture cannot currently tell you which one is right.

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


def test_it_leaks_no_fragment_of_any_key():
    """The note reaches home-assistant.log, and users paste that into issues.

    An SDES inline key is base64 of a 30-byte master key + salt. A leading
    fragment of it is not a nickname, it is key material with the search space
    reduced by however many characters were printed - so the note must carry no
    run of any key long enough to be worth having.
    """
    note = _srtp_tx_key_note("RR", ANSWER, OFFER, ANSWER)

    for name, key in (("offer", OFFER), ("answer", ANSWER)):
        for start in range(len(key) - 5):
            run = key[start:start + 6]
            assert run not in note, f"{name} key fragment {run!r} leaked: {note}"


def test_it_does_not_print_the_key_prefix():
    """The specific regression: an 8-char prefix is ~48 bits of key material."""
    note = _srtp_tx_key_note("PLI", OFFER, OFFER, ANSWER)

    assert OFFER[:8] not in note
    assert ANSWER[:8] not in note


def test_it_still_tells_two_different_keys_apart():
    """Distinguishing keys across log lines is the whole job of the field."""
    note = _srtp_tx_key_note("RR", ANSWER, OFFER, ANSWER)

    offer_field = note.split("offer=")[1].split(" ")[0]
    answer_field = note.split("answer=")[1].split(" ")[0]
    assert offer_field != answer_field


def test_the_same_key_gets_the_same_fingerprint_every_time():
    """Two log lines from one session have to be correlatable."""
    first = _srtp_tx_key_note("PLI", OFFER, OFFER, ANSWER)
    second = _srtp_tx_key_note("RR", OFFER, OFFER, ANSWER)

    assert first.split("used=offer(")[1].split(")")[0] == (
        second.split("used=offer(")[1].split(")")[0])
