"""Matching the camera's answer sections to the offer's m-sections.

The rebuild that feeds aiortc used to match purely by ``a=mid``. That holds
right up until the camera adds a section the offer never had, which this
firmware family does: an A000088 was measured on 2026-08-13 answering an
SD-card offer with

    m=video ... 0        a=mid:0   a=rtpmap:0 H265/90000
    m=video ... 101 102  a=mid:1   (the H264 section we negotiated)
    m=application ...    a=mid:2

Every mid is shifted by one. Matching by mid then hands our video slot the
H265 section and drops the H264 one, and aiortc rejects the answer outright
("Failed to set remote video description send parameters") because H265 is not
in its codec registry. On a live offer the same shift makes every mid mismatch
on kind, all sections get stubbed, and the open fails as a cold camera would.

Mid order is still the right first guess - it is what a compliant answerer
sends, and the A001064 quirks the mid-walk was written for depend on it. So the
mid match stays, and the fallback only runs when it produces something
unusable.
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.protocol import select_answer_section

_OFFER_VIDEO_PTS = {"97", "98", "99", "100", "101", "102"}


def _sec(kind: str, *pts: str) -> tuple:
    """One answer section: (kind, lines), as the rebuild stores them."""
    line = f"m={kind} 9 UDP/TLS/RTP/SAVPF " + " ".join(pts)
    return (kind, [line, "a=rtcp-mux", "a=sendonly"])


# The shapes below are transcribed from measured answers, not invented.
_SD_SHIFTED = {
    "0": _sec("video", "0"),                # H265 stub the camera added
    "1": _sec("video", "101", "102"),       # the H264 answer we want
    "2": _sec("application"),
}

_LIVE_SHIFTED = {
    "0": _sec("video", "0"),
    "1": _sec("audio", "8"),
    "2": _sec("video", "101", "102"),
    "3": _sec("application"),
}

_LIVE_CLEAN = {
    "0": _sec("audio", "8"),
    "1": _sec("video", "101", "102"),
    "2": _sec("application"),
}


def test_clean_answer_is_matched_by_mid_unchanged():
    """The common case must not move: mid 1 is the video, and stays it."""
    for mid, kind in (("0", "audio"), ("1", "video"), ("2", "application")):
        got = select_answer_section(mid, kind, _LIVE_CLEAN, _OFFER_VIDEO_PTS, set())
        assert got == (mid, _LIVE_CLEAN[mid][1])


def test_sd_offer_skips_the_h265_stub_sharing_its_mid():
    """Our only video mid is 0, and the camera's mid 0 is its H265 section."""
    got = select_answer_section("0", "video", _SD_SHIFTED, _OFFER_VIDEO_PTS, set())
    assert got is not None
    assert got[0] == "1", "should take the H264 section, not the H265 one"
    assert "101 102" in got[1][0]


def test_sd_datachannel_finds_the_application_section_a_mid_away():
    got = select_answer_section("1", "application", _SD_SHIFTED,
                                _OFFER_VIDEO_PTS, {"0"})
    assert got is not None and got[0] == "2"


def test_live_offer_survives_a_one_mid_shift():
    """audio/video/application must each find their section despite the shift."""
    claimed: set = set()
    picks = {}
    for mid, kind in (("0", "audio"), ("1", "video"), ("2", "application")):
        got = select_answer_section(mid, kind, _LIVE_SHIFTED,
                                    _OFFER_VIDEO_PTS, claimed)
        assert got is not None, f"offer mid {mid} ({kind}) found nothing"
        claimed.add(got[0])
        picks[kind] = got[0]
    assert picks == {"audio": "1", "video": "2", "application": "3"}


def test_a_section_is_never_handed_to_two_offer_mids():
    claimed = {"1"}
    got = select_answer_section("0", "video", _SD_SHIFTED,
                                _OFFER_VIDEO_PTS, claimed)
    assert got is None or got[0] != "1"


def test_missing_kind_returns_none_so_the_caller_stubs():
    """A001064 drops sections outright; the caller must still get to stub."""
    answer = {"0": _sec("video", "101", "102")}
    assert select_answer_section("1", "audio", answer,
                                 _OFFER_VIDEO_PTS, set()) is None


def test_video_with_no_overlap_is_still_taken_when_it_is_all_there_is():
    """Better a section aiortc may reject than a stub that guarantees failure.

    Losing the real section would turn a recoverable answer into a declined one,
    so a no-overlap video section is preferred over nothing at all.
    """
    answer = {"0": _sec("video", "0")}
    got = select_answer_section("0", "video", answer, _OFFER_VIDEO_PTS, set())
    assert got is not None and got[0] == "0"


def test_no_offer_pts_falls_back_to_plain_mid_matching():
    """With nothing to score against, the mid is the only signal left."""
    got = select_answer_section("0", "video", _SD_SHIFTED, set(), set())
    assert got is not None and got[0] == "0"


# --- how long to wait for ICE, given what the answer looked like -------------

from aidot_cameras.camera.protocol import ice_wait_timeout


def test_a_clean_answer_gets_the_full_timeout():
    assert ice_wait_timeout(shifted_sections=0, default_timeout=45.0) == 45.0


def test_a_shifted_answer_is_capped_but_not_refused():
    """One shifted answer in seven DID open - at 8.8 s. The cap must clear it.

    Measured over 63 opens: every successful open finished ICE between 8.7 s and
    10.6 s and every failure ran the full 45 s. There is no overlap, so a cap
    above the success band and far below the failure band loses nothing and
    saves 30 s on the 86% that were never going to connect.
    """
    capped = ice_wait_timeout(shifted_sections=3, default_timeout=45.0)
    assert capped < 45.0
    assert capped > 10.6, "must still clear the slowest observed success"


def test_the_cap_never_extends_a_shorter_caller_timeout():
    """A caller asking for 5 s means 5 s, shifted answer or not."""
    assert ice_wait_timeout(shifted_sections=3, default_timeout=5.0) == 5.0


# --- telling an ADDED section from a dropped or reordered one ----------------

from aidot_cameras.camera.protocol import answer_inserted_a_section


def test_an_added_section_leaves_one_unclaimed():
    """The measured shape: the camera adds H265 and every later mid moves on.

    The SD offer has two mids (video 0, application 1); the camera answers with
    three, and the walk claims its video and application sections, leaving the
    H265 one spare. That spare is what says a section was INSERTED.
    """
    assert answer_inserted_a_section(shifted_sections=2, unclaimed_answer_mids=1)


def test_a_dropped_section_is_not_an_insert():
    """A001064 drops rejected sections and renumbers - quirk 1 of the rebuild.

    Its mids shift too, but nothing is left over. Treating that as the H265
    shape would cut the ICE deadline of a camera whose cold open is 25-70 s,
    on the SDES->DTLS fallback path, using a cap measured on a mains A000088
    that opens in 9 s.
    """
    assert not answer_inserted_a_section(shifted_sections=2,
                                         unclaimed_answer_mids=0)


def test_a_clean_answer_is_not_an_insert():
    assert not answer_inserted_a_section(shifted_sections=0,
                                         unclaimed_answer_mids=0)


def test_a_spare_section_without_a_shift_is_not_an_insert():
    """An extra section nobody wanted, with our mids still lining up, is the
    camera padding the answer - not the shift the cap was measured against."""
    assert not answer_inserted_a_section(shifted_sections=0,
                                         unclaimed_answer_mids=1)
