"""Recording which video profile a session actually negotiated.

An A001064 was measured serving two different profiles for identical requests -
H264 1280x720 at 2.5-4.0 Mbps, and H265 2560x1440 at ~1.1 Mbps - varying per
session with nothing on our side asking for either. What selects it is still
unknown, and the reason it is still unknown is that nothing recorded it: every
bitrate measurement taken before 2026-08-07 is unstratified, because the codec
that produced it was never written down.

This is instrumentation, not a fix. It claims no cause. It exists so the
question can be answered from a record instead of from another theory.

Scope is deliberately the payload type and its codec name, which is what the
bridge knows when the first video packet lands. Frame dimensions would require
parsing the H264/H265 parameter sets out of the stream, which is a much larger
change than the question warrants - and on the camera measured, codec and
resolution moved together in 11 of 11 sessions, so the codec is a sufficient
stratifier today.
"""
import os
import sys

import pytest

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.sdes_open import (  # noqa: E402
    _SDP_VIDEO_PT_BY_CODEC,
    describe_video_profile,
)


def test_names_the_h264_payload_type():
    assert describe_video_profile(96) == "pt=96 codec=H264"


def test_names_the_h265_payload_type():
    assert describe_video_profile(97) == "pt=97 codec=H265"


def test_stays_consistent_with_the_offer_template():
    """The mapping this reads must be the same one the offer is built from, or
    the record would name a codec the camera was never offered."""
    for codec, pt in _SDP_VIDEO_PT_BY_CODEC.items():
        assert describe_video_profile(pt) == f"pt={pt} codec={codec}"


@pytest.mark.parametrize("pt", [0, 8, 98, 99, 127])
def test_an_unmapped_payload_type_is_reported_as_unknown(pt):
    """Firmware that negotiates a payload type we do not map must still produce
    a usable record - 'unknown' is a finding, a crash or a silent skip is not."""
    assert describe_video_profile(pt) == f"pt={pt} codec=unknown"


@pytest.mark.parametrize("pt", [None, "97", -1])
def test_junk_does_not_raise(pt):
    """This runs on the media path. Nothing here may be able to break a stream
    that is otherwise healthy."""
    out = describe_video_profile(pt)
    assert isinstance(out, str)
    assert out
