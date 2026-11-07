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


# --- agc decodes with g711.alaw2linear: bit-identical to the copy it had --- #


def _old_agc_alaw_to_linear(a: int) -> int:
    """The decoder agc.py carried before it used g711.alaw2linear, verbatim."""
    a ^= 0x55
    t = (a & 0x0F) << 4
    seg = (a & 0x70) >> 4
    if seg == 0:
        t += 8
    elif seg == 1:
        t += 0x108
    else:
        t = (t + 0x108) << (seg - 1)
    return t if (a & 0x80) else -t


def _old_gain_table(gain_db):
    from aidot_cameras.g711 import linear2alaw

    k = 10 ** (gain_db / 20.0)
    return bytes(
        linear2alaw(max(-32768, min(32767, round(_old_agc_alaw_to_linear(a) * k))))
        for a in range(256)
    )


def test_agc_decode_is_bit_identical_to_its_old_copy():
    from aidot_cameras.camera import agc
    from aidot_cameras.g711 import alaw2linear

    old = tuple(_old_agc_alaw_to_linear(a) for a in range(256))
    assert tuple(alaw2linear(a) for a in range(256)) == old
    assert agc._ALAW_TO_LINEAR == old
    assert agc._ALAW_SQUARES == tuple(float(v) * v for v in old)
    assert agc._alaw_to_linear is alaw2linear


def test_agc_gain_tables_are_bit_identical():
    from aidot_cameras.camera import agc

    assert agc.alaw_gain_table(0) is None
    for gain_db in (-12.0, -8.0, -0.5, 0.5, 6.0, 30.0):
        assert agc.alaw_gain_table(gain_db) == _old_gain_table(gain_db), gain_db
