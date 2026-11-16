"""The per-packet helpers must give exactly what their first versions gave.

Each reference below is the original byte-at-a-time implementation, kept here
verbatim so the faster one in the package is compared against it on buffers
built to hit the awkward spots: 3- and 4-byte start codes back to back, runs
of zeros before a code and at the end, a code as the last bytes, no payload.
"""

import random
import struct
from types import SimpleNamespace

import pytest

from aidot_cameras.camera import rtsp_publish as rp
from aidot_cameras.camera.agc import _ALAW_SQUARES, _alaw_mean_square
from aidot_cameras.camera.protocol import _h264_has_keyframe


def _ref_has_keyframe(data):
    i, n = 0, len(data)
    while i < n - 4:
        if data[i] == 0 and data[i + 1] == 0:
            if data[i + 2] == 1:
                if (data[i + 3] & 0x1F) in (5, 7):
                    return True
                i += 4
                continue
            if data[i + 2] == 0 and i + 4 < n and data[i + 3] == 1:
                if (data[i + 4] & 0x1F) in (5, 7):
                    return True
                i += 5
                continue
        i += 1
    return False


def _ref_split_annexb(data):
    nals = []
    i, n = 0, len(data)
    starts = []
    while i + 3 <= n:
        if data[i] == 0 and data[i + 1] == 0:
            if data[i + 2] == 1:
                starts.append((i, i + 3))
                i += 3
                continue
            if i + 4 <= n and data[i + 2] == 0 and data[i + 3] == 1:
                starts.append((i, i + 4))
                i += 4
                continue
        i += 1
    if not starts:
        return [data] if data else []
    for k, (_s, body) in enumerate(starts):
        end = starts[k + 1][0] if k + 1 < len(starts) else n
        nal = data[body:end]
        while nal.endswith(b"\x00") and len(nal) > 1:
            nal = nal[:-1]
        if nal:
            nals.append(nal)
    return nals


def _ref_alaw_mean_square(payload):
    sq = _ALAW_SQUARES
    return sum(sq[b] for b in payload) / len(payload)


def _ref_build_rtp(pt, marker, seq, ts, ssrc, payload):
    return struct.pack(
        "!BBHII",
        0x80,
        (0x80 if marker else 0) | (pt & 0x7F),
        seq & 0xFFFF,
        ts & 0xFFFFFFFF,
        ssrc & 0xFFFFFFFF,
    ) + bytes(payload)


def _ref_interleaved(channel, pkt):
    return b"$" + bytes((channel,)) + struct.pack("!H", len(pkt)) + pkt


_SC3 = b"\x00\x00\x01"
_SC4 = b"\x00\x00\x00\x01"
_HEADERS = bytes((0x65, 0x67, 0x68, 0x41, 0x61, 0x06, 0x09, 0x25, 0x27, 0x00, 0x01))


def _pieces(rng):
    """One random Annex-B-ish buffer, biased toward the edge cases."""
    out = bytearray()
    for _ in range(rng.randint(0, 6)):
        kind = rng.randint(0, 9)
        if kind <= 2:
            out += _SC3
        elif kind <= 5:
            out += _SC4
        elif kind == 6:
            out += b"\x00" * rng.randint(1, 5)
        elif kind == 7:
            out += bytes((rng.choice(_HEADERS),))
        elif kind == 8:
            # Dense low alphabet: plenty of near-miss start codes.
            out += bytes(
                rng.choice((0, 0, 0, 1, 2, 0x65)) for _ in range(rng.randint(0, 12))
            )
        else:
            out += bytes(rng.randrange(256) for _ in range(rng.randint(0, 40)))
        if rng.random() < 0.5:
            out += bytes((rng.choice(_HEADERS),))
    tail = rng.randint(0, 9)
    if tail == 0:
        out += _SC3
    elif tail == 1:
        out += _SC4
    elif tail == 2:
        out += b"\x00" * rng.randint(1, 4)
    elif tail == 3:
        out += _SC3 + bytes((rng.choice(_HEADERS),))
    elif tail == 4:
        out += _SC4 + bytes((rng.choice(_HEADERS),))
    return bytes(out)


_FIXED = [
    b"",
    b"\x00",
    b"\x00\x00",
    _SC3,
    _SC4,
    _SC3 + b"\x65",
    _SC4 + b"\x65",
    _SC3 + b"\x65\x00",
    _SC4 + b"\x67\x00",
    b"\x00" + _SC4 + b"\x65",
    b"\xff" + _SC3 + b"\x65",
    b"\xff" + _SC4 + b"\x65",
    _SC3 + _SC3 + b"\x65",
    _SC3 + b"\x41" + _SC4 + b"\x65",
    _SC3 + b"\x41" + _SC4 + b"\x65\x00",
    _SC3 + b"\x41\x00" + _SC3 + b"\x65\x00",
    _SC3 + b"\x00\x00\x00",
    _SC3 + b"\x00" + _SC3 + b"\x67AB",
    _SC4 + b"\x67AB" + _SC4 + b"\x68C" + _SC3 + b"\x65DDDDD",
    b"\x67AB\x00\x00",
    b"no start code at all",
    b"\x00\x00\x00\x00\x00\x00",
    b"\x00\x00\x00\x00\x00\x01",
    b"\x00\x00\x00\x00\x00\x01\x65",
    b"\x00\x00\x00\x00\x00\x01\x65\x00",
]


def _buffers():
    rng = random.Random(0x5EED)
    yield from _FIXED
    for _ in range(20000):
        yield _pieces(rng)
    # Exhaustive over a tiny alphabet: every buffer up to 7 bytes long.
    alphabet = (0x00, 0x01, 0x65)
    for length in range(1, 8):
        for code in range(len(alphabet) ** length):
            buf = bytearray()
            for _ in range(length):
                code, digit = divmod(code, len(alphabet))
                buf.append(alphabet[digit])
            yield bytes(buf)


def test_has_keyframe_matches_reference():
    seen = {True: 0, False: 0}
    for buf in _buffers():
        want = _ref_has_keyframe(buf)
        assert _h264_has_keyframe(buf) is want, buf
        seen[want] += 1
    assert seen[True] > 1000 and seen[False] > 1000


def test_has_keyframe_accepts_a_bytearray():
    for buf in _FIXED:
        assert _h264_has_keyframe(bytearray(buf)) is _ref_has_keyframe(buf)


def test_split_annexb_matches_reference():
    multi = 0
    for buf in _buffers():
        want = _ref_split_annexb(buf)
        got = rp.split_annexb(buf)
        assert got == want, buf
        assert all(type(n) is bytes for n in got)
        multi += len(want) > 1
    assert multi > 1000


def test_split_annexb_large_access_unit():
    rng = random.Random(7)
    body = bytes(rng.randrange(256) for _ in range(60000))
    au = (
        _SC4
        + b"\x67"
        + body[:30]
        + _SC4
        + b"\x68\x01"
        + _SC3
        + b"\x65"
        + body
        + b"\x00\x00"
    )
    assert rp.split_annexb(au) == _ref_split_annexb(au)
    assert [len(n) for n in rp.split_annexb(au)] == [31, 2, 60001]


def test_alaw_mean_square_matches_reference():
    rng = random.Random(11)
    payloads = [bytes((v,)) for v in range(256)]
    payloads += [bytes(range(256)), b"\xd5" * 160, b"\x00" * 320, b"\xff" * 8]
    for _ in range(2000):
        payloads.append(bytes(rng.randrange(256) for _ in range(rng.randint(1, 400))))
    for payload in payloads:
        got = _alaw_mean_square(payload)
        assert type(got) is float
        assert got == _ref_alaw_mean_square(payload), payload


def test_alaw_mean_square_empty_still_raises():
    with pytest.raises(ZeroDivisionError):
        _ref_alaw_mean_square(b"")
    with pytest.raises(ZeroDivisionError):
        _alaw_mean_square(b"")


def test_build_rtp_matches_reference():
    rng = random.Random(13)
    cases = [
        (96, True, 0, 0, 0, b""),
        (96, False, 0xFFFF, 0xFFFFFFFF, 0xFFFFFFFF, b"\x65"),
        (8, False, 0x10000, 0x100000000, 0x1FFFFFFFF, b"\xd5" * 160),
        (255, True, -1, -1, -1, bytearray(b"abc")),
        (97, 1, 70000, 2**40 + 5, 2**33 + 9, memoryview(b"xyz")),
    ]
    for _ in range(3000):
        cases.append(
            (
                rng.randrange(0, 300),
                rng.random() < 0.5,
                rng.randrange(-5, 1 << 20),
                rng.randrange(-5, 1 << 36),
                rng.randrange(-5, 1 << 36),
                bytes(rng.randrange(256) for _ in range(rng.randint(0, 64))),
            )
        )
    for case in cases:
        got = rp.build_rtp(*case)
        assert type(got) is bytes
        assert got == _ref_build_rtp(*case), case


def test_send_rtp_frames_match_reference():
    sent = []
    pub = rp.RtspPublisher.__new__(rp.RtspPublisher)
    pub._send_raw = sent.append
    pub.packets_sent = 0
    pub.bytes_sent = 0
    rng = random.Random(17)
    total = 0
    count = 0
    sizes = [0, 1, 12, 1400, 65535] + [rng.randint(0, 2000) for _ in range(500)]
    for size in sizes:
        channel = rng.choice((0, 1, 2, 3, 36, 127, 128, 255))
        pkt = bytes(rng.randrange(256) for _ in range(min(size, 64))) + b"\x00" * max(
            0, size - 64
        )
        track = SimpleNamespace(channel=channel)
        pub.send_rtp(track, pkt)
        total += len(pkt)
        count += 1
        assert type(sent[-1]) is bytes
        assert sent[-1] == _ref_interleaved(channel, pkt)
        assert len(sent) == count
        assert pub.packets_sent == count
        assert pub.bytes_sent == total


def test_send_rtp_oversize_packet_is_still_refused():
    sent = []
    pub = rp.RtspPublisher.__new__(rp.RtspPublisher)
    pub._send_raw = sent.append
    pub.packets_sent = 0
    pub.bytes_sent = 0
    track = SimpleNamespace(channel=0)
    with pytest.raises(struct.error):
        _ref_interleaved(0, b"\x00" * 65536)
    with pytest.raises(struct.error):
        pub.send_rtp(track, b"\x00" * 65536)
    assert sent == [] and pub.packets_sent == 0 and pub.bytes_sent == 0
