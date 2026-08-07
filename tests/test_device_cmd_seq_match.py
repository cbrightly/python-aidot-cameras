"""A device-command ack has to be OUR ack.

`async_set_device_attribute` generates a `seq` and sends it, and the camera
answers `setDevAttrResp` matched by that seq - the docstring has said so since
the format was taken from the app. Coming back, nothing checked it: the ack loop
accepted the first message on a topic containing "setDevAttr" that carried code
200, whichever command it belonged to.

That matters because more than one message is in flight. Battery cameras get a
`lowPowerActiveStateReq` wake published in the same breath, HA writes attributes
in bursts when a user moves several controls, and every one of those subscribes
to the same four wildcard topics. Accepting a stranger's 200 means reporting
that a control landed on the evidence of a different control landing.

**What this does not change:** a command that draws no matching ack still
succeeds. The official app is fire-and-forget - the comment on that fallback is
explicit that no-ack is the normal case - so tightening it into a failure would
turn every working LED, motion and volume write into a reported error. A non-200
ack for our own seq is logged rather than acted on, because whether this
firmware ever sends one is not yet known, and a return value should not be
changed on a guess.
"""
import json

import pytest

from aidot_cameras.camera.client import _ack_matches_seq

SEQ = "ap1234567"


def _resp(seq=SEQ, code=200, method="setDevAttrResp"):
    return json.dumps({
        "id": "cam1", "method": method, "seq": seq,
        "ack": {"code": code, "desc": "ok" if code == 200 else "nope"},
    })


def test_our_own_ack_is_accepted():
    assert _ack_matches_seq(json.loads(_resp()), SEQ) is True


def test_another_commands_ack_is_not_ours():
    """The case this exists for: a concurrent write's 200, on the same topic."""
    assert _ack_matches_seq(json.loads(_resp(seq="ap7654321")), SEQ) is False


def test_the_battery_wake_ack_is_not_ours():
    """Battery cameras get lowPowerActiveStateReq published alongside the write.

    Its ack arrives first and lands on the same wildcard subscription.
    """
    wake = json.dumps({"method": "lowPowerActiveStateResp",
                       "ack": {"code": 200}})
    assert _ack_matches_seq(json.loads(wake), SEQ) is False


def test_a_response_without_a_seq_is_not_claimed():
    """Unattributable is not the same as ours - fall through to the fallback."""
    assert _ack_matches_seq(json.loads(_resp(seq=None)), SEQ) is False


def test_a_non_200_for_our_seq_is_still_recognised_as_ours():
    """Recognising it is the point: that is what makes it loggable.

    Acting on it is deliberately left alone until there is evidence this
    firmware sends one at all.
    """
    assert _ack_matches_seq(json.loads(_resp(code=500)), SEQ) == 500


def test_a_seq_that_is_an_integer_still_matches():
    """We send a string; nothing guarantees the camera echoes the same type."""
    msg = {"seq": 1234567, "ack": {"code": 200}}
    assert _ack_matches_seq(msg, "1234567") is True


@pytest.mark.parametrize("junk", [None, "", [], "not json shaped", 42])
def test_junk_is_not_our_ack(junk):
    assert _ack_matches_seq(junk, SEQ) is False


def test_no_seq_to_match_against_claims_nothing():
    """With no seq of our own there is nothing to correlate - say so."""
    assert _ack_matches_seq(json.loads(_resp()), None) is False
