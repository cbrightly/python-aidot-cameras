"""The serve SDP must never narrow to a codec the pin excluded.

Measured 2026-08-23 on the reference A001064, with `sdes_pin_h264` ON (so the
offer advertises H.264 only) and the camera sending pt=96 H264 in every
observed session:

    camera 0a1b2c3d4e5f: no video observed before the serve launched;
      narrowing the SDP to payload type 97 from the camera's negotiated answer
    SDES: narrowed ffmpeg SDP to video pt=97 (H265)

and the serve then died at startup, every time:

    Could not find codec parameters for stream 1 (Video: hevc, none)
    [rtsp] dimensions not set
    [out#0/rtsp] Could not write header (incorrect codec parameters ?)

This camera ANSWERS H.265 and SENDS H.264. When video has not been observed
before the serve launches, the fallback trusted the answer -- so it built an
hevc-only SDP, no hevc ever arrived, ffmpeg could not determine dimensions, and
the RTSP header could not be written. That failure was invisible until the exit
reason stopped being flushed out by Non-monotonic DTS noise.

The precedence has to be: what we OBSERVED beats what we PINNED beats what the
camera CLAIMED. An observed payload type is fact. A pin is our own constraint
on the offer, so narrowing past it can only produce a stream the camera was
never asked for. The answer is the weakest of the three -- this camera
demonstrably does not honour its own.
"""
import pytest

from aidot_cameras.camera.sdes_open import _serve_video_pt


def test_an_observed_payload_type_wins_over_everything():
    # Fact beats claim and beats constraint: if H.265 is actually arriving,
    # serving H.264 would serve nothing.
    assert _serve_video_pt(observed=97, answer=96, pinned=96) == 97


def test_the_pin_beats_the_answer_when_nothing_was_observed():
    # The exact reference case: pinned to 96, answer says 97, no video yet.
    assert _serve_video_pt(observed=None, answer=97, pinned=96) == 96


def test_the_answer_is_used_when_there_is_no_pin():
    assert _serve_video_pt(observed=None, answer=97, pinned=None) == 97


def test_nothing_known_stays_unknown():
    # The caller keeps its own "could not narrow" path; do not invent a type.
    assert _serve_video_pt(observed=None, answer=None, pinned=None) is None


def test_a_pin_still_answers_when_the_camera_never_answered():
    assert _serve_video_pt(observed=None, answer=None, pinned=96) == 96


@pytest.mark.parametrize("bogus", [0, 8, 42, 999, -1])
def test_an_observed_type_the_template_does_not_carry_is_not_used(bogus):
    # _SDP_VIDEO_PTS is what our SDP template can express; narrowing to
    # anything else would produce an SDP the camera was never offered.
    assert _serve_video_pt(observed=bogus, answer=97, pinned=96) == 96
