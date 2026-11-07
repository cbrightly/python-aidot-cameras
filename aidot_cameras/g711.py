"""Pure-Python G.711 helpers: the A-law encoder for two-way-audio (talk), and
the A-law <-> mu-law byte tables the SDES direct publisher converts with.

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


_SEG_UEND = (0x3F, 0x7F, 0xFF, 0x1FF, 0x3FF, 0x7FF, 0xFFF, 0x1FFF)


def linear2ulaw(pcm_val: int) -> int:
    """Encode one signed 16-bit PCM sample to an 8-bit mu-law byte (Sun g711.c)."""
    pcm_val = pcm_val >> 2  # 16-bit -> 14-bit
    if pcm_val < 0:
        pcm_val = -pcm_val
        mask = 0x7F
    else:
        mask = 0xFF
    pcm_val = min(pcm_val, 8159) + (0x84 >> 2)
    seg = next((i for i, b in enumerate(_SEG_UEND) if pcm_val <= b), 8)
    if seg >= 8:
        return 0x7F ^ mask
    return ((seg << 4) | ((pcm_val >> (seg + 1)) & 0x0F)) ^ mask


def ulaw2linear(u_val: int) -> int:
    """Decode one mu-law byte to a signed 16-bit PCM sample (Sun g711.c)."""
    u_val = ~u_val & 0xFF
    t = (((u_val & 0x0F) << 3) + 0x84) << ((u_val & 0x70) >> 4)
    return (0x84 - t) if u_val & 0x80 else (t - 0x84)


def alaw2linear(a_val: int) -> int:
    """Decode one A-law byte to a signed 16-bit PCM sample (Sun g711.c)."""
    a_val ^= 0x55
    t = (a_val & 0x0F) << 4
    seg = (a_val & 0x70) >> 4
    if seg == 0:
        t += 8
    else:
        t = (t + 0x108) << (seg - 1)
    return t if a_val & 0x80 else -t


#: ``bytes.translate`` tables between the two G.711 laws, through linear.
ULAW_TO_ALAW = bytes(linear2alaw(ulaw2linear(i)) for i in range(256))
ALAW_TO_ULAW = bytes(linear2ulaw(alaw2linear(i)) for i in range(256))
