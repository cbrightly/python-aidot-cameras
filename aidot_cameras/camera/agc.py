"""Audio conditioning on A-law bytes, shared by every path that serves audio.

One level normalizer: the DTLS and SDES direct publishers run it on the
camera's A-law payload before the bytes leave the process, and the PyAV
mux behind the ffmpeg serve runs it on the same bytes before it decodes
them to AAC. It used to exist twice - here as a translate table, and in
the mux as a numpy loop over decoded samples - with the same four
environment variables and the gate and the clamp in opposite orders, so
the two paths disagreed by up to 12 dB on a silent room. The class is the
version every default path has run since 1.0.0rc52 and was checked live.
"""

from __future__ import annotations

import math
from typing import Optional

from .. import settings

# --------------------------------------------------------------------------- #
# Audio gain on A-law bytes                                                    #
# --------------------------------------------------------------------------- #


#: A-law byte -> linear sample, and the squares of those, precomputed. The
#: publish loop runs per 20 ms audio frame on every camera, so the decode and
#: the level measurement must not be per-sample Python.
_ALAW_TO_LINEAR: tuple = ()
_ALAW_SQUARES: tuple = ()


def _alaw_mean_square(payload: bytes) -> float:
    """Mean square of an A-law frame, by table lookup."""
    sq = _ALAW_SQUARES
    return sum(sq[b] for b in payload) / len(payload)


def _alaw_encode(sample: int) -> int:
    from ..g711 import linear2alaw

    return linear2alaw(sample)


def _db2amp(db: float) -> float:
    """Decibels (full scale) to a linear amplitude factor."""
    return 10.0 ** (db / 20.0)


def _alaw_to_linear(a: int) -> int:
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


def alaw_gain_table(gain_db: float) -> Optional[bytes]:
    """A 256-byte translate table applying ``gain_db`` to A-law, or None at 0 dB."""
    if not gain_db:
        return None
    from ..g711 import linear2alaw

    k = 10 ** (gain_db / 20.0)
    out = bytearray(256)
    for a in range(256):
        v = round(_alaw_to_linear(a) * k)
        out[a] = linear2alaw(max(-32768, min(32767, v)))
    return bytes(out)


_ALAW_TO_LINEAR = tuple(_alaw_to_linear(b) for b in range(256))
_ALAW_SQUARES = tuple(float(v) * v for v in _ALAW_TO_LINEAR)


class AlawAgc:
    """The audio conditioning every served path applies to A-law payloads.

    The PyAV mux the DTLS publisher replaces did not send the camera's audio
    as it arrived: it decoded, ran a level tracker with a gain clamp, a noise
    gate and a tanh soft-limiter toward a target level, and re-encoded.
    Publishing raw A-law would quietly drop all of that, so the same
    conditioning runs here, reading the same environment variables.

    The SDES publisher runs it too (since the release after 1.0.0rc51). It
    had only a fixed trim, sized in 0.7.34 for a hot microphone, and the
    measured truth on 2026-10-14 was the opposite: an A001064 and an A001513
    send their rooms at -68 and -70 dBFS RMS, so after the trim every sample
    sat under the first A-law step and the published audio was digital zero,
    while the same room through an A000088 came out at a listening level.
    The two transports now get the same conditioning, and the trim is an
    offset applied after it. The PyAV mux behind the ffmpeg serve runs this
    class too (since the release after 1.0.0rc54; it used to carry its own
    numpy copy of the same algorithm). Variables:

    ``AIDOT_AUDIO_TARGET_DBFS`` (-15), ``AIDOT_AUDIO_MAXGAIN_DB`` (30),
    ``AIDOT_AUDIO_MINGAIN_DB`` (-12), ``AIDOT_AUDIO_GATE_DBFS`` (-45).

    The gate matters: below it the gain is scaled down quadratically rather
    than cranked toward maximum, which is what stops a quiet camera's A-law
    quantization floor being amplified into audible clicking.
    """

    def __init__(self, env=None):
        def _f(name, default):
            # The registry owns the default (``default`` is kept in the call
            # for the reader) and the parse: unset, unparseable or non-finite
            # reads as the default. "inf"/"nan" must never get through - a
            # non-finite gain reaches math.log() when the translate table is
            # keyed and raises *inside the publish loop*, taking the stream
            # down. The per-sample path this replaced merely saturated.
            return float(settings.read(name, env))

        self.enabled = True
        self.target = _db2amp(_f("AIDOT_AUDIO_TARGET_DBFS", -15)) * 32767.0
        self.maxg = _db2amp(_f("AIDOT_AUDIO_MAXGAIN_DB", 30))
        self.ming = _db2amp(_f("AIDOT_AUDIO_MINGAIN_DB", -12))
        self.gate = _db2amp(_f("AIDOT_AUDIO_GATE_DBFS", -45)) * 32767.0
        self._ms = None  # smoothed mean square
        self._tables: dict = {}  # quantized gain -> 256-entry translate table

    #: Gain is quantized to this many dB before a table is built for it, so a
    #: slowly-moving level tracker reuses one table instead of rebuilding it
    #: per frame. 0.5 dB is well under audible.
    _GAIN_STEP_DB = 0.5

    def process(self, payload: bytes) -> bytes:
        """One A-law frame in, one conditioned A-law frame out.

        The conditioning is deterministic given the gain, so the gain, the
        limiter and the A-law round trip are baked into a 256-entry translate
        table and applied by ``bytes.translate`` at C speed. Measured on ARM
        before the table: 0.858 ms per 20 ms frame, 4.3% of a core per stream,
        all of it inside the publish loop.

        The level measurement below is still per-sample Python, and is now
        ~90% of what this costs; the conditioning itself is ~0.0007 ms. That
        is where to look if more headroom is ever wanted.
        """
        if not self.enabled or not payload:
            return payload
        ms = _alaw_mean_square(payload)
        self._ms = ms if self._ms is None else self._ms * 0.95 + ms * 0.05
        rms = (self._ms**0.5) + 1.0
        gain = self.target / rms
        if rms < self.gate:
            # Below the gate: fade the gain down with the square of how far
            # under it we are, instead of cranking toward maximum.
            gain *= (rms / self.gate) ** 2
        gain = max(self.ming, min(self.maxg, gain))
        return payload.translate(self._table(gain))

    def _table(self, gain: float) -> bytes:
        step = 10 ** (self._GAIN_STEP_DB / 20.0)
        key = round(math.log(max(gain, 1e-9), step))
        table = self._tables.get(key)
        if table is None:
            g = step**key
            table = bytes(
                _alaw_encode(
                    int(max(-32768, min(32767, math.tanh(x * (g / 32767.0)) * 32767.0)))
                )
                for x in _ALAW_TO_LINEAR
            )
            self._tables[key] = table
        return table
