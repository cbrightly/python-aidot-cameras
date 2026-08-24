"""A TMMBR is a receiver telling a sender a bitrate it must not exceed.

RFC 5104 s4.2.1. RTPFB (PT=205) with FMT=3, one 8-byte FCI entry per limited
sender:

    0                   1                   2                   3
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                              SSRC                             |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    | MxTBR Exp |  MxTBR Mantissa                 |Measured Overhead|
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

Why this one, after the others died: REMB is an *estimate* of available
bandwidth and this camera was measured to ignore it; TMMBR is a *bound*, and
the two are different messages with different semantics. And the camera
demonstrably acts on RTCP feedback it never negotiated -- our SDES offer
carries no `a=rtcp-fb` line of any kind (see the offer in sdes_open, which
emits rtpmap/fmtp/rtcp-mux/ICE and nothing else), yet the Generic NACKs we send
recover 98.4% of lost packets. So "the answer does not advertise ccm tmmbr" is
not a reason to skip the measurement here.

The encoding is the part worth testing: the bitrate is not a plain integer but
a 17-bit mantissa scaled by a 6-bit exponent, and getting that wrong yields a
well-formed packet asking for the wrong rate -- which would read as "the camera
ignored it".
"""
import struct

import pytest

from aidot_cameras.camera.protocol import build_tmmbr, decode_tmmbr_bitrate


def _parse(pkt):
    assert len(pkt) % 4 == 0, "RTCP packets are a whole number of 32-bit words"
    v_p_fmt, pt, length = struct.unpack("!BBH", pkt[:4])
    return {
        "version": v_p_fmt >> 6,
        "fmt": v_p_fmt & 0x1F,
        "pt": pt,
        "length": length,
        "sender_ssrc": struct.unpack("!I", pkt[4:8])[0],
        "media_ssrc": struct.unpack("!I", pkt[8:12])[0],
        "fci": pkt[12:],
    }


def test_it_is_an_rtpfb_tmmbr():
    p = _parse(build_tmmbr(0x1111, 0x2222, 800_000))
    assert p["version"] == 2
    assert p["pt"] == 205, "TMMBR is RTPFB, the same payload type as a NACK"
    assert p["fmt"] == 3, "FMT=3 is TMMBR; FMT=1 would be a Generic NACK"


def test_the_media_source_field_is_zero():
    # RFC 5104 s4.2.1.2: the header's media-source SSRC is not used for TMMBR
    # and SHALL be zero -- the SSRC being limited lives in the FCI instead.
    # A camera that validates this would drop a packet carrying it twice.
    p = _parse(build_tmmbr(0x1111, 0x2222, 800_000))
    assert p["media_ssrc"] == 0
    assert struct.unpack("!I", p["fci"][:4])[0] == 0x2222


def test_the_length_field_counts_words_minus_one():
    pkt = build_tmmbr(1, 2, 800_000)
    assert len(pkt) == 20, "12-byte RTPFB header + one 8-byte FCI entry"
    assert _parse(pkt)["length"] == len(pkt) // 4 - 1 == 4


@pytest.mark.parametrize("bps", [64_000, 250_000, 800_000, 1_500_000,
                                 2_000_000, 4_000_000])
def test_the_requested_bitrate_survives_the_mantissa_encoding(bps):
    # The bound may be rounded DOWN by the encoding but must never come out
    # higher than asked: a TMMBR that permits more than intended is worse than
    # none at all on a link that is already saturating.
    got = decode_tmmbr_bitrate(build_tmmbr(1, 2, bps))
    assert got <= bps
    assert got >= bps * 0.999, f"asked {bps}, encoded {got}: lost too much"


def test_the_mantissa_and_exponent_are_in_range():
    pkt = build_tmmbr(1, 2, 4_000_000)
    word = struct.unpack("!I", pkt[16:20])[0]
    exp = word >> 26
    mantissa = (word >> 9) & 0x1FFFF
    assert 0 <= exp < 64
    assert 0 <= mantissa < (1 << 17)
    assert mantissa << exp == decode_tmmbr_bitrate(pkt)


def test_the_overhead_field_is_carried():
    # Measured Overhead is per-packet transport overhead in bytes, 9 bits.
    pkt = build_tmmbr(1, 2, 800_000, overhead=60)
    assert struct.unpack("!I", pkt[16:20])[0] & 0x1FF == 60


def test_an_absurd_overhead_cannot_corrupt_the_neighbouring_field():
    # 9 bits. A caller passing 1000 must not have its high bits land in the
    # mantissa and silently multiply the bound.
    pkt = build_tmmbr(1, 2, 800_000, overhead=1000)
    assert struct.unpack("!I", pkt[16:20])[0] & 0x1FF <= 0x1FF
    assert decode_tmmbr_bitrate(pkt) <= 800_000


@pytest.mark.parametrize("bad", [0, -1, -800_000])
def test_a_non_positive_bound_is_refused(bad):
    # b=AS:0 means "no bandwidth"; the same mistake in a TMMBR would ask the
    # camera to stop sending. That must be a programming error, not a stream
    # that goes quiet in the field.
    with pytest.raises(ValueError):
        build_tmmbr(1, 2, bad)


def test_ssrcs_are_masked_to_32_bits():
    p = _parse(build_tmmbr(0x1_0000_1111, 0x2222, 800_000))
    assert p["sender_ssrc"] == 0x0000_1111
