"""RTP (RFC 6184) H.264 back into Annex B access units.

The SDES direct publisher forwards the camera's RTP packets as they arrive; the
in-sync HLS TS (``ts_tee``) needs whole access units. This rebuilds them from
single NAL unit packets, STAP-A and FU-A - the three packetizations the
cameras use - ending an access unit at its marker bit, or when the timestamp
moves on if the marker was lost.

A damaged frame must never reach the TS: a decoder fed one shows corruption
until the next keyframe, and Home Assistant would record it. So after any
loss - reported by the caller, which sees the sequence numbers, or found here
(a fragment without its start, a malformed aggregate, a packetization the
cameras never use) - nothing comes out until the next keyframe.
"""

from __future__ import annotations

import struct
from typing import List, Optional, Tuple

_SC = b"\x00\x00\x00\x01"


class H264Depacketizer:
    """Feed RTP payloads in sequence order; get ``(annexb, media, keyframe)``."""

    def __init__(self) -> None:
        self._ts: Optional[int] = None
        self._media = 0
        self._nals: List[bytes] = []
        self._fu: Optional[bytearray] = None
        self._bad = False
        self._need_keyframe = True
        self._skip_ts: Optional[int] = None

    def loss(self) -> None:
        """Packets went missing: drop the frame in progress, wait for a keyframe."""
        self._skip_ts = self._ts  # the rest of a damaged frame is not a frame
        self._reset()
        self._need_keyframe = True

    def push(
        self, payload: bytes, ts: int, marker: bool, media: int
    ) -> List[Tuple[bytes, int, bool]]:
        out: List[Tuple[bytes, int, bool]] = []
        if self._skip_ts is not None:
            if ts == self._skip_ts:
                return out
            self._skip_ts = None
        if self._ts is not None and ts != self._ts:
            out += self._finish()
        if self._ts is None:
            self._ts, self._media = ts, media
        self._add(payload)
        if marker:
            out += self._finish()
        return out

    # -- internals ----------------------------------------------------------- #

    def _add(self, payload: bytes) -> None:
        if not payload:
            return
        t = payload[0] & 0x1F
        if 1 <= t <= 23:
            if self._fu is not None:
                self._bad = True  # a fragmented NAL unit never finished
                self._fu = None
            self._nals.append(bytes(payload))
        elif t == 24:  # STAP-A
            i = 1
            while i < len(payload):
                if i + 2 > len(payload):
                    self._bad = True
                    return
                (size,) = struct.unpack(">H", payload[i : i + 2])
                nal = payload[i + 2 : i + 2 + size]
                if size == 0 or len(nal) != size:
                    self._bad = True
                    return
                self._nals.append(bytes(nal))
                i += 2 + size
        elif t == 28 and len(payload) >= 2:  # FU-A
            start, end = payload[1] & 0x80, payload[1] & 0x40
            if start:
                if self._fu is not None:
                    self._bad = True
                self._fu = bytearray([(payload[0] & 0xE0) | (payload[1] & 0x1F)])
            elif self._fu is None:
                self._bad = True  # its start is gone
                return
            self._fu += payload[2:]
            if end:
                self._nals.append(bytes(self._fu))
                self._fu = None
        else:
            # STAP-B, MTAP, FU-B or reserved: never sent by these cameras.
            self._bad = True

    def _finish(self) -> List[Tuple[bytes, int, bool]]:
        bad = self._bad or self._fu is not None
        nals, media = self._nals, self._media
        self._reset()
        if bad:
            self._need_keyframe = True
            return []
        if not nals:
            return []
        types = {n[0] & 0x1F for n in nals}
        keyframe = bool(types & {5, 7})
        if self._need_keyframe and not {5, 7} <= types:
            # Restart only on a whole keyframe: its parameter sets and an IDR
            # slice. A loss inside a frame skips the rest of that frame (loss()),
            # so one whose top went missing at a frame boundary lacks the SPS.
            return []
        self._need_keyframe = False
        return [(b"".join(_SC + n for n in nals), media, keyframe)]

    def _reset(self) -> None:
        self._ts = None
        self._nals = []
        self._fu = None
        self._bad = False
