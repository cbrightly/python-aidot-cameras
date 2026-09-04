"""The STUN responder window must not sit on an answer it already has.

The window was written when the camera's answer arrived AFTER it closed - the
comment above the answer harvest still says "the STUN window above runs on a
fixed schedule that closes ~2.4 s before the camera answers".  Cutting the
livePlayReq echo wait from 5.0 s to 0.25 s inverted that: measured on a cold
A001064 open, the answer landed at +0.72 s and the window ran on until +5.05 s
before anything looked at it, then nominated at +5.06 s and got first media at
+6.66 s.  4.3 s of a 6.7 s connect was spent holding credentials in hand.

Nothing is gained by staying: the bridge thread takes over STUN responding the
moment the window closes, and it is the bridge - not the window - that learns
peer-reflexive candidates and re-nominates every 2.5 s.
"""

import inspect

import pytest

from aidot_cameras.camera.webrtc_open import (
    _answer_is_from_the_camera,
    _deliver_webrtc_answer,
)
from aidot_cameras.camera.sdes_open import (
    _answer_ready_for_this_open,
    _answer_sdp_can_nominate,
    _parse_answer_ice,
    _stun_window_answer_exit_due,
)


# An abridged but real-shaped camera answer: the credentials and the one udp
# candidate that USE-CANDIDATE is actually addressed to.
_ANSWER = "\r\n".join([
    "v=0",
    "o=- 0 0 IN IP4 192.168.0.124",
    "s=-",
    "t=0 0",
    "m=audio 35488 RTP/SAVP 8",
    "a=ice-ufrag:CAMufrag",
    "a=ice-pwd:CAMpwdCAMpwdCAMpwd",
    "a=candidate:1 1 udp 2130706431 192.168.0.124 35488 typ host",
    "a=candidate:2 1 udp 1694498815 81.2.3.4 51820 typ srflx",
    "m=video 35490 RTP/SAVP 96",
])


# --------------------------------------------------------------------------- #
# What nomination needs out of the answer
# --------------------------------------------------------------------------- #

def test_it_reads_the_credentials_and_candidates_the_nomination_addresses():
    ufrag, pwd, cands, host = _parse_answer_ice(_ANSWER)
    assert ufrag == "CAMufrag"
    assert pwd == "CAMpwdCAMpwdCAMpwd"
    assert cands == [("192.168.0.124", 35488), ("81.2.3.4", 51820)]
    assert host == ("192.168.0.124", 35488)


def test_the_host_candidate_is_the_first_typ_host_not_merely_the_first():
    """SCTP is addressed to the host candidate.  An answer that lists srflx
    first must not hand back the srflx address as the host one."""
    srflx_first = _ANSWER.replace(
        "a=candidate:1 1 udp 2130706431 192.168.0.124 35488 typ host\r\n"
        "a=candidate:2 1 udp 1694498815 81.2.3.4 51820 typ srflx",
        "a=candidate:2 1 udp 1694498815 81.2.3.4 51820 typ srflx\r\n"
        "a=candidate:1 1 udp 2130706431 192.168.0.124 35488 typ host",
    )
    _u, _p, cands, host = _parse_answer_ice(srflx_first)
    assert cands[0] == ("81.2.3.4", 51820)
    assert host == ("192.168.0.124", 35488)


def test_an_empty_or_absent_sdp_yields_nothing():
    assert _parse_answer_ice("") == ("", "", [], ())
    assert _parse_answer_ice(None) == ("", "", [], ())


@pytest.mark.parametrize("missing", ["a=ice-ufrag:", "a=ice-pwd:", "a=candidate:"])
def test_an_answer_missing_any_one_of_the_three_cannot_nominate(missing):
    """The exit is gated on the exact precondition the nomination is gated on -
    ufrag AND pwd AND at least one candidate.  Leaving the window on an answer
    that satisfies only part of it would trade 4.3 s of responding for a
    session that never nominates at all."""
    stripped = "\r\n".join(
        ln for ln in _ANSWER.split("\r\n") if not ln.startswith(missing)
    )
    assert _answer_sdp_can_nominate(stripped) is False


def test_a_complete_answer_can_nominate():
    assert _answer_sdp_can_nominate(_ANSWER) is True


def test_no_answer_at_all_cannot_nominate():
    assert _answer_sdp_can_nominate("") is False
    assert _answer_sdp_can_nominate(None) is False


# --------------------------------------------------------------------------- #
# When the window is allowed to leave early
# --------------------------------------------------------------------------- #

def test_it_leaves_once_the_answer_is_in_hand_and_the_camera_is_doing_ice():
    assert _stun_window_answer_exit_due(
        stun_seen=True, answer_ready=True) is True


def test_it_stays_while_the_answer_has_not_arrived():
    """This is the case the window was built for and it is unchanged."""
    assert _stun_window_answer_exit_due(
        stun_seen=True, answer_ready=False) is False


def test_it_stays_until_the_camera_has_actually_started_its_checks():
    """Before the first binding request there is no evidence the camera is
    running ICE at all.  A relay-only camera that answers early and probes late
    keeps today's behaviour rather than being nominated into silence."""
    assert _stun_window_answer_exit_due(
        stun_seen=False, answer_ready=True) is False


def test_it_is_not_scoped_to_one_branch_of_the_window():
    """The first cut gated this on _sdes_webrtcresp_sent, on the reading that
    only the 20 s echo-reversal window carried the delay.  A cold A001064 open
    on 2026-09-04 took the no-echo branch instead: answer at +0.22 s, window ran
    its full 2.5 s cap, nomination at +4.60 s, first media at +6.21 s - the same
    defect, and the gate silently switched the fix off for it.  The predicate
    takes no branch flag now, and this test exists to stop one coming back."""
    import inspect

    sig = inspect.signature(_stun_window_answer_exit_due)
    assert set(sig.parameters) == {"stun_seen", "answer_ready"}


# --------------------------------------------------------------------------- #
# The helpers have to be the ones the code actually runs
# --------------------------------------------------------------------------- #

def _open_source() -> str:
    from aidot_cameras.camera.client import CameraMixin

    return inspect.getsource(CameraMixin._open_sdes_stream_impl)


def test_the_window_loop_consults_the_exit_helper():
    """A pure predicate is easy to write, test green, and never wire up.  The
    window is a 150-line blob in the middle of a 7000-line method; assert the
    loop names the helper."""
    assert "_stun_window_answer_exit_due" in _open_source()


def test_the_nomination_reads_its_candidates_through_the_same_parser():
    """The exit condition means "the nomination will succeed".  It only means
    that while both sides read the answer the same way, so the open path must
    parse through _parse_answer_ice rather than keeping a second copy inline."""
    src = _open_source()
    assert "_parse_answer_ice" in src
    assert 'startswith("a=ice-ufrag:")' not in src, (
        "inline ICE parsing is back - the exit predicate and the nomination can"
        " now disagree about the same SDP"
    )


def test_the_early_exit_says_so_in_the_log():
    """The window's exit reason was invisible - it just broke.  Without a line
    naming it, a measurement cannot tell "the new code ran and did not help"
    from "the new code never ran"."""
    src = _open_source()
    assert "STUN window: leaving early" in src


def test_the_readiness_flag_is_stamped_off_the_loop_thread():
    """The window blocks the event loop, so call_soon_threadsafe(set_result) is
    only QUEUED while it runs and answer_fut.done() stays False throughout -
    which is why the window could not see the answer in the first place.  The
    marker therefore has to be written by the MQTT thread directly."""
    from aidot_cameras.camera.webrtc_open import _WebRTCOpenMixin

    src = inspect.getsource(_WebRTCOpenMixin)
    assert "_camera_answer_ice_ready_ts" in src
    assert "_answer_sdp_can_nominate" in src


def test_the_flag_is_cleared_for_each_open():
    """Left set, the second open leaves its window instantly on the first
    open's answer."""
    from aidot_cameras.camera.webrtc_open import _WebRTCOpenMixin

    src = inspect.getsource(_WebRTCOpenMixin)
    assert "self._camera_answer_ice_ready_ts = None" in src


# --------------------------------------------------------------------------- #
# Our own answer, echoed back at us
# --------------------------------------------------------------------------- #
#
# On the echo-reversal branch WE send a webrtcResp, and the broker echoes it
# back on the account topic. It carries our own peerid, so the accept filter
# admits it, and its SDP has our ice-ufrag, ice-pwd and candidates - so it
# satisfies _answer_sdp_can_nominate exactly as the camera's answer does.
# Without a sender check the window would leave on our own SDP and the setup
# nomination would address our own host and srflx addresses.

_OUR_ANSWER = "\r\n".join([
    "v=0",
    "m=audio 39641 RTP/SAVPF 8",
    "a=ice-ufrag:OURufrag",
    "a=ice-pwd:OURpwdOURpwdOURpwd",
    "a=candidate:1 1 udp 2130706431 192.168.0.114 39641 typ host",
])

_USER = "76adc6bd26f516d0fb06e804ee3cf85e"


def test_our_own_answer_would_otherwise_pass_the_nomination_check():
    """The premise. If this ever goes False the sender check below is dead code
    and the test that matters is silently passing for the wrong reason."""
    assert _answer_sdp_can_nominate(_OUR_ANSWER) is True


def test_our_own_echoed_answer_is_not_treated_as_the_cameras():
    assert _answer_is_from_the_camera(
        src_addr=f"0.{_USER}", user_id=_USER) is False


def test_the_cameras_answer_is():
    assert _answer_is_from_the_camera(
        src_addr="2.338603b50fce46ef8d2545fc7362c967", user_id=_USER) is True


def test_a_server_message_is_not_mistaken_for_ours():
    """`9.` is the server prefix. It is not us, so it is not filtered here -
    the SDP check is what decides whether it can nominate."""
    assert _answer_is_from_the_camera(
        src_addr=f"9.{_USER}", user_id=_USER) is True


def test_an_answer_with_no_srcaddr_keeps_the_prior_behaviour():
    """Device-channel messages omit srcAddr. Absent evidence, do not start
    rejecting answers that worked before this gate existed."""
    assert _answer_is_from_the_camera(src_addr="", user_id=_USER) is True
    assert _answer_is_from_the_camera(src_addr=None, user_id=_USER) is True


def test_the_stamp_consults_the_sender_check():
    from aidot_cameras.camera.webrtc_open import _WebRTCOpenMixin

    src = inspect.getsource(_WebRTCOpenMixin)
    assert "_answer_is_from_the_camera" in src


def test_the_retry_window_leaves_early_too():
    """A camera that quickConns after signalling re-runs ICE in a second, 8 s
    window. Wiring the exit into only the first one leaves the same delay in the
    window the fix had not reached."""
    src = _open_source()
    assert src.count("_stun_window_answer_exit_due") >= 2


def test_a_corrupt_candidate_line_is_still_rejected():
    """Widening the type token to accept a bare `typ` must not also accept
    `typo host` or `typhoon`: every parse this collapsed rejected those, and a
    corrupt line reaching the nomination gets a TURN permission and a probe."""
    for bad in ("a=candidate:1 1 udp 1 1.2.3.4 5 typo host",
                "a=candidate:1 1 udp 1 1.2.3.4 5 typhoon"):
        assert _parse_answer_ice(bad)[2] == [], bad


def test_the_trickle_path_reads_candidates_through_the_same_parser():
    """It kept its own copy of the pattern, so the two could disagree about the
    same line - which is the gap the collapse exists to close."""
    src = _open_source()
    assert "_parse_candidate_line" in src
    assert "_re_ice" not in src


def test_the_candidate_parse_accepts_a_line_that_ends_at_typ():
    """Two of the four parses this collapsed ended at a bare `typ` and captured
    no type token. The shared regex must not reject a line either of them would
    have taken."""
    sdp = "a=candidate:1 1 udp 2130706431 192.168.0.124 35488 typ"
    _u, _p, cands, host = _parse_answer_ice(sdp)
    assert cands == [("192.168.0.124", 35488)]
    assert host == ()


# --------------------------------------------------------------------------- #
# Our own echo must not reach the future the nomination reads
# --------------------------------------------------------------------------- #

def test_our_own_echo_is_dropped_rather_than_routed():
    """Gating only the readiness marker left the failure it was written to
    prevent fully reachable: `answer_fut` is where the nomination reads its SDP,
    so an echo that resolves it makes USE-CANDIDATE address our own host and
    srflx candidates with our own credentials. Routing it to second_answer_fut
    instead just feeds the same values to the late-credential recovery."""
    import asyncio

    async def _run():
        loop = asyncio.get_running_loop()
        first, second = loop.create_future(), loop.create_future()
        _deliver_webrtc_answer(loop, first, second,
                               {"sdp": _OUR_ANSWER}, from_camera=False)
        await asyncio.sleep(0)
        assert not first.done(), "our own SDP must not resolve answer_fut"
        assert not second.done(), "nor the future the late recovery reads"

        _deliver_webrtc_answer(loop, first, second,
                               {"sdp": _ANSWER}, from_camera=True)
        await asyncio.sleep(0)
        assert first.done() and first.result()["sdp"] == _ANSWER

        _deliver_webrtc_answer(loop, first, second,
                               {"sdp": _ANSWER}, from_camera=True)
        await asyncio.sleep(0)
        assert second.done(), "a later camera answer still reaches the recovery"

    asyncio.run(_run())


def test_delivery_defaults_to_treating_an_answer_as_the_cameras():
    """Every other caller predates the flag; none of them must start dropping
    answers because a keyword was added."""
    import inspect

    sig = inspect.signature(_deliver_webrtc_answer)
    assert sig.parameters["from_camera"].default is True


# --------------------------------------------------------------------------- #
# The marker belongs to one open
# --------------------------------------------------------------------------- #

def test_a_marker_from_this_open_arms_the_exit():
    assert _answer_ready_for_this_open(100.0, 99.0) is True
    assert _answer_ready_for_this_open(99.0, 99.0) is True


def test_a_marker_left_by_an_earlier_open_is_inert():
    """The marker is per-camera state driving a per-open decision. The per-open
    reset covers the sequential case; two opens overlapping on one camera object
    would otherwise let one leave its window on the other's answer."""
    assert _answer_ready_for_this_open(98.9, 99.0) is False


def test_no_marker_at_all_is_not_ready():
    assert _answer_ready_for_this_open(None, 99.0) is False


def test_the_window_checks_the_marker_belongs_to_this_open():
    src = _open_source()
    assert "_answer_ready_for_this_open" in src
    assert src.count("_answer_ready_for_this_open") >= 2, (
        "both STUN windows must use it, not just the first")


# --------------------------------------------------------------------------- #
# One credential pair, measured rather than assumed
# --------------------------------------------------------------------------- #
#
# The nomination sends USE-CANDIDATE on BOTH sockets with the single ufrag/pwd
# this parser returns, computing the video socket's MESSAGE-INTEGRITY with what
# the parser found first. That is only correct if the camera answers one pair
# for the whole session, which nothing here proved.
#
# Measured 2026-09-04 by logging every a=ice-ufrag / a=ice-pwd line of the
# answer, per m-section, across four cold opens on two models (A001064 mains,
# A001513 battery): every answer carried ONE pair, repeated identically in the
# audio, video and application sections. Values below are synthesised - the real
# ones are per-session credentials and do not belong in a repo - but the shape
# is the shape that was captured.

_ONE_PAIR_ANSWER = "\r\n".join([
    "v=0",
    "m=video 9 RTP/SAVP 96",
    "a=ice-ufrag:9z7F",
    "a=ice-pwd:mDe5uxBe1msnVNMlu8BwzQPy",
    "a=candidate:1 1 udp 2130706431 192.168.0.124 33912 typ host",
    "m=audio 9 RTP/SAVP 8",
    "a=ice-ufrag:9z7F",
    "a=ice-pwd:mDe5uxBe1msnVNMlu8BwzQPy",
    "m=application 9 SCTP webrtc-datachannel",
    "a=ice-ufrag:9z7F",
    "a=ice-pwd:mDe5uxBe1msnVNMlu8BwzQPy",
])


def test_the_first_credential_pair_is_the_only_pair():
    """So nominating both sockets with it is right, not merely lucky."""
    ufrag, pwd, cands, _host = _parse_answer_ice(_ONE_PAIR_ANSWER)
    assert ufrag == "9z7F"
    assert pwd == "mDe5uxBe1msnVNMlu8BwzQPy"
    assert cands == [("192.168.0.124", 33912)]

    pairs = {
        (ln[len("a=ice-ufrag:"):].strip() if ln.startswith("a=ice-ufrag:")
         else ln[len("a=ice-pwd:"):].strip())
        for ln in _ONE_PAIR_ANSWER.splitlines()
        if ln.startswith(("a=ice-ufrag:", "a=ice-pwd:"))
    }
    assert pairs == {ufrag, pwd}, (
        "this answer carries more than one credential pair, so the single pair"
        " the nomination uses on both sockets is no longer the whole story")
