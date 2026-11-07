"""The A-law <-> mu-law tables the SDES direct publisher converts with.

The publisher announces the audio type the camera's answer named when no
packet has been seen, and the bridge stamps TUTK-framed audio as PCMA (8)
whatever the answer said; a packet of the other law is converted rather than
dropped (see LoopbackRtpPublisher._forward).
"""

from aidot_cameras.g711 import (
    ALAW_TO_ULAW,
    ULAW_TO_ALAW,
    alaw2linear,
    linear2alaw,
    linear2ulaw,
    ulaw2linear,
)


def test_decoders_match_the_g711_extremes():
    assert ulaw2linear(0x00) == -32124 and ulaw2linear(0x80) == 32124
    assert alaw2linear(0x2A) == -32256 and alaw2linear(0xAA) == 32256


def test_encoders_round_trip_every_code():
    # mu-law 0x7F is negative zero; it encodes back as positive zero (0xFF).
    assert [u for u in range(256) if linear2ulaw(ulaw2linear(u)) != u] == [0x7F]
    assert [a for a in range(256) if linear2alaw(alaw2linear(a)) != a] == []


def test_silence_maps_to_silence():
    assert ULAW_TO_ALAW[0xFF] == 0xD5
    assert abs(ulaw2linear(ALAW_TO_ULAW[0xD5])) <= 8


def test_conversion_keeps_the_level_within_one_step():
    for u in range(256):
        lin = ulaw2linear(u)
        assert abs(alaw2linear(ULAW_TO_ALAW[u]) - lin) <= abs(lin) / 8 + 8
    for a in range(256):
        lin = alaw2linear(a)
        assert abs(ulaw2linear(ALAW_TO_ULAW[a]) - lin) <= abs(lin) / 8 + 8
