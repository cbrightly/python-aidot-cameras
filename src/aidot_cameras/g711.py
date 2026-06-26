"""Pure-Python G.711 A-law encoder for two-way-audio (talk).

No stdlib ``audioop`` dependency - that module was removed in Python 3.13 and
Home Assistant runs on 3.12/3.13.  A-law encode follows the ITU-T G.711
reference (Sun ``g711.c`` ``linear2alaw``); verified byte-identical to
``audioop.lin2alaw`` over all 65536 samples on Python <=3.12.

The SDES talk pump (device_client) feeds 20 ms (320-byte) frames of s16le PCM
@ 8 kHz mono through :func:`pcm_to_alaw` to produce 160-byte PCMA (PT=8) RTP
payloads.
"""

from __future__ import annotations

import struct

_SEG_AEND = (0x1F, 0x3F, 0x7F, 0xFF, 0x1FF, 0x3FF, 0x7FF, 0xFFF)


def _search(value: int) -> int:
    for i, bound in enumerate(_SEG_AEND):
        if value <= bound:
            return i
    return 8


def linear2alaw(pcm_val: int) -> int:
    """Encode one signed 16-bit PCM sample to an 8-bit A-law byte."""
    pcm_val = pcm_val >> 3  # 16-bit -> 13-bit
    if pcm_val >= 0:
        mask = 0xD5
    else:
        mask = 0x55
        pcm_val = -pcm_val - 1
        if pcm_val < 0:
            pcm_val = 0
    seg = _search(pcm_val)
    if seg >= 8:
        return 0x7F ^ mask
    aval = seg << 4
    if seg < 2:
        aval |= (pcm_val >> 1) & 0x0F
    else:
        aval |= (pcm_val >> seg) & 0x0F
    return aval ^ mask


def pcm_to_alaw(pcm: bytes) -> bytes:
    """Encode little-endian signed 16-bit PCM bytes to A-law bytes."""
    n = len(pcm) // 2
    if n == 0:
        return b""
    samples = struct.unpack(f"<{n}h", pcm[: n * 2])
    return bytes(linear2alaw(s) for s in samples)
