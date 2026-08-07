"""Build the REMB packet that tells the camera what bitrate we want.

Stage 1 got the camera to negotiate `goog-remb` - confirmed in its own answer
over SDES:

    a=rtcp-fb:8  nack / goog-remb        (audio, PCMA)
    a=rtcp-fb:96 nack / goog-remb        (video)

Negotiating it only means the camera will ACCEPT a REMB. Nothing changes until
one is sent, and we send only RTCP Receiver Reports today. That is stage 2, and
this is the packet.

Format is the Google draft (draft-alvestrand-rmcat-remb), carried as an RTCP
Payload-Specific Feedback message:

    V=2 P=0 FMT=15, PT=206
    sender SSRC, media SSRC (0 by convention for REMB)
    'R' 'E' 'M' 'B'
    num-SSRC (1 byte) | BR exponent (6 bits) | BR mantissa (18 bits)
    one SSRC per stream the estimate covers

The bitrate is exponent-and-mantissa, not a plain integer, which is the part
worth testing: a mis-packed exponent asks the camera for a wildly wrong rate,
and the failure mode is a stream that looks fine until you measure it.
"""
import struct

import pytest

from aidot_cameras.camera.protocol import build_remb, decode_remb_bitrate


def test_it_is_a_payload_specific_feedback_packet():
    pkt = build_remb(sender_ssrc=0xAB12CD34, media_ssrcs=[0x11223344],
                     bitrate_bps=400_000)
    b0, pt, length = struct.unpack("!BBH", pkt[:4])
    assert b0 >> 6 == 2, "version must be 2"
    assert b0 & 0x1F == 15, "FMT 15 identifies REMB"
    assert pt == 206, "PT 206 is payload-specific feedback"
    assert len(pkt) == (length + 1) * 4, "length field must match the packet"


def test_it_carries_the_remb_identifier():
    pkt = build_remb(sender_ssrc=1, media_ssrcs=[2], bitrate_bps=300_000)
    assert pkt[12:16] == b"REMB"


def test_the_media_ssrc_field_is_zero():
    """REMB puts the streams in its own list, not in the header's media SSRC.

    Offsets matter here and are easy to get wrong: sender SSRC is at 4, media
    SSRC at 8, the identifier at 12. Writing the bitrate at the wrong offset
    asks the camera for a wildly different rate and still produces a packet that
    looks structurally valid.
    """
    pkt = build_remb(sender_ssrc=0xAB12CD34, media_ssrcs=[0x99], bitrate_bps=1)
    assert struct.unpack("!I", pkt[4:8])[0] == 0xAB12CD34
    assert struct.unpack("!I", pkt[8:12])[0] == 0


def test_every_ssrc_is_listed():
    pkt = build_remb(sender_ssrc=1, media_ssrcs=[0xAAA, 0xBBB], bitrate_bps=250_000)
    assert pkt[16] == 2
    assert struct.unpack("!I", pkt[20:24])[0] == 0xAAA
    assert struct.unpack("!I", pkt[24:28])[0] == 0xBBB


@pytest.mark.parametrize("bps", [50_000, 225_000, 400_000, 500_000,
                                 1_000_000, 3_500_000])
def test_the_bitrate_round_trips_within_the_formats_precision(bps):
    """The mantissa is 18 bits, so large values lose a little precision.

    What must not happen is an error of the kind a mis-packed exponent gives -
    asking for megabits when you meant hundreds of kilobits. Within 1% is far
    tighter than that failure mode.
    """
    got = decode_remb_bitrate(build_remb(1, [2], bps))
    assert abs(got - bps) / bps < 0.01, f"asked {bps}, encoded {got}"


def test_a_small_bitrate_needs_no_exponent():
    """Values inside 18 bits are exact, and that is the common case."""
    pkt = build_remb(1, [2], 200_000)
    assert decode_remb_bitrate(pkt) == 200_000


def test_it_refuses_a_nonsense_bitrate():
    """Zero or negative would tell the camera to send nothing at all."""
    for bad in (0, -1):
        with pytest.raises(ValueError):
            build_remb(1, [2], bad)


def test_it_needs_at_least_one_stream():
    with pytest.raises(ValueError):
        build_remb(1, [], 300_000)
