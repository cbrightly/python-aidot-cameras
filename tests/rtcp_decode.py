"""RTCP decoders the tests read our builders back with.

The library only builds these packets; it never needs to read one, so the
decoders live here rather than in ``camera/protocol.py``.
"""

import struct


def decode_remb_bitrate(pkt: bytes) -> int:
    """The bitrate a REMB packet asks for."""
    # Layout: 0-3 header, 4-7 sender SSRC, 8-11 media SSRC, 12-15 "REMB",
    # 16 num-SSRC, 17-19 exponent+mantissa, 20+ the SSRCs.
    b = pkt[17]
    exp = b >> 2
    mantissa = ((b & 0x03) << 16) | (pkt[18] << 8) | pkt[19]
    return mantissa << exp


def decode_nack_seqs(pkt: bytes) -> "list":
    """The sequence numbers a Generic NACK asks for."""
    out = []
    for off in range(12, len(pkt), 4):
        pid, blp = struct.unpack_from("!HH", pkt, off)
        out.append(pid)
        for bit in range(16):
            if blp & (1 << bit):
                out.append((pid + bit + 1) & 0xFFFF)
    return out
