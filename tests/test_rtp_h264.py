"""RTP (RFC 6184) H.264 back into access units, for the SDES in-sync TS.

The SDES publisher forwards the camera's RTP as it comes, so the in-sync TS
needs whole access units. A lost packet must never produce a damaged frame in
the TS: everything is dropped until the next keyframe, as a decoder would need.
"""

import struct

from aidot_cameras.camera.rtp_h264 import H264Depacketizer

SPS = bytes.fromhex("674d001fe900a00b742000007d20000daf8080")
PPS = b"\x68\xee\x3c\xb0"
IDR = b"\x65" + bytes(range(1, 200))
P1 = b"\x41" + bytes(range(50, 120))
SC = b"\x00\x00\x00\x01"


def stap(*nals):
    return b"\x18" + b"".join(struct.pack(">H", len(n)) + n for n in nals)


def fu(nal, size=60):
    """FU-A fragments of one NAL unit."""
    ind, body = (nal[0] & 0xE0) | 28, nal[1:]
    parts = [body[i : i + size] for i in range(0, len(body), size)]
    out = []
    for k, p in enumerate(parts):
        hdr = (
            (nal[0] & 0x1F)
            | (0x80 if k == 0 else 0)
            | (0x40 if k == len(parts) - 1 else 0)
        )
        out.append(bytes([ind, hdr]) + p)
    return out


def feed(d, packets):
    """packets: (payload, ts, marker, media); returns every emitted AU."""
    out = []
    for payload, ts, marker, media in packets:
        out += d.push(payload, ts, marker, media)
    return out


def keyframe(ts, media):
    pkts = [(stap(SPS, PPS), ts, False, media)]
    frags = fu(IDR)
    pkts += [(f, ts, k == len(frags) - 1, media) for k, f in enumerate(frags)]
    return pkts


def test_a_keyframe_from_stap_a_and_fu_a_and_a_p_frame():
    d = H264Depacketizer()
    aus = feed(d, keyframe(1000, 0) + [(P1, 4000, True, 3000)])
    assert aus == [(SC + SPS + SC + PPS + SC + IDR, 0, True), (SC + P1, 3000, False)]


def test_an_au_without_a_marker_ends_when_the_timestamp_changes():
    d = H264Depacketizer()
    pkts = keyframe(1000, 0)
    pkts[-1] = (pkts[-1][0], 1000, False, 0)  # the marker went missing
    aus = feed(d, [*pkts, (P1, 4000, True, 3000)])
    assert [a[1:] for a in aus] == [(0, True), (3000, False)]


def test_nothing_comes_out_before_the_first_keyframe():
    d = H264Depacketizer()
    aus = feed(d, [(P1, 500, True, 0), *keyframe(1000, 500)])
    assert [a[1:] for a in aus] == [(500, True)]


def test_after_a_loss_nothing_until_the_next_keyframe():
    d = H264Depacketizer()
    first = feed(d, keyframe(1000, 0))
    frags = fu(IDR)
    feed(d, [(stap(SPS, PPS), 4000, False, 3000), (frags[0], 4000, False, 3000)])
    d.loss()  # a packet of this keyframe never arrived
    rest = feed(
        d,
        [(f, 4000, k == len(frags) - 1, 3000) for k, f in enumerate(frags[2:], 2)]
        + [(P1, 7000, True, 6000)]
        + keyframe(10000, 9000)
        + [(P1, 13000, True, 12000)],
    )
    assert [a[1:] for a in first] == [(0, True)]
    assert [a[1:] for a in rest] == [(9000, True), (12000, False)]


def test_a_fragment_without_its_start_or_an_unknown_type_is_a_loss():
    d = H264Depacketizer()
    feed(d, keyframe(1000, 0))
    frags = fu(P1, 20)
    assert (
        feed(
            d,
            [(f, 4000, k == len(frags) - 1, 3000) for k, f in enumerate(frags[1:], 1)],
        )
        == []
    )
    assert feed(d, [(P1, 7000, True, 6000)]) == []  # still waiting for a keyframe
    d2 = H264Depacketizer()
    feed(d2, keyframe(1000, 0))
    assert feed(d2, [(b"\x19" + P1, 4000, True, 3000), (P1, 7000, True, 6000)]) == []


def test_a_malformed_stap_a_is_a_loss():
    d = H264Depacketizer()
    feed(d, keyframe(1000, 0))
    bad = b"\x18" + struct.pack(">H", 500) + P1
    assert feed(d, [(bad, 4000, True, 3000), (P1, 7000, True, 6000)]) == []


def test_the_rest_of_a_damaged_keyframe_is_not_taken_for_one():
    # A two-slice keyframe that loses its parameter sets and first slice: the
    # second slice is an IDR slice, but a frame missing its top is not a frame.
    d = H264Depacketizer()
    feed(d, keyframe(1000, 0))
    feed(d, [(stap(SPS, PPS), 4000, False, 3000)])
    d.loss()
    assert feed(d, [(IDR, 4000, True, 3000), (P1, 7000, True, 6000)]) == []
    # and a frame that starts mid-way after a loss at a frame boundary
    d.loss()
    assert feed(d, [(IDR, 10000, True, 9000)]) == []
    assert [a[1:] for a in feed(d, keyframe(13000, 12000))] == [(12000, True)]
