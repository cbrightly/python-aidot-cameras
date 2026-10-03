"""Mux a direct publisher's video and AAC into one camera's MPEG-TS, on one clock.

Home Assistant's HLS stream worker keeps whatever relation between audio and
video its input demuxer gives it. Through go2rtc's RTSP that relation is
re-based per consumer, so a recording or HLS view that joins a running stream
gets its sound 0.1-0.75 s late, differently each time (measured 2026-10-03,
synthetic and with claps). Here the publisher hands over each video access unit
and each AAC frame with its media time - video ticks and AAC samples since the
session's first frame, the origin the publisher already aligns (the AAC pacer
follows video media time until audio arrives, rc33) - and both are muxed on a
single clock, so every consumer of the camera's ``TsChannel`` gets them in step
whenever it joins.

One ``TsTee`` lives as long as the camera's channel, across sessions: each new
session's origin is placed just after the last timestamp written, so a consumer
that stays connected through a camera reconnect never sees time go backwards
(Home Assistant's worker drops backward timestamps for up to 30 s). Nothing is
re-encoded. AAC's 1024-sample encoder priming is taken off its timestamps.
A session that starts with nobody connected starts the timeline over instead.

Known limit: MPEG-TS timestamps are 33 bits (26.5 h at 90 kHz). Only a consumer
that stays connected that long through back-to-back sessions reaches the wrap;
what Home Assistant's worker does then is untested.

Input never blocks: when the mux thread falls behind, everything is dropped
until the next video keyframe - never single AAC frames, which would put audio
out of step for the rest of the session.
"""

from __future__ import annotations

import itertools
import logging
import queue
import threading
from fractions import Fraction
from typing import Optional

_LOGGER = logging.getLogger(__name__)

_TB90 = Fraction(1, 90000)
#: AAC-LC's encoder delay: its first 1024 decoded samples are priming.
_AAC_PRIMING_90K = 1024 * 90000 // 48000
#: Where the first session starts (keeps the primed AAC timestamps positive).
_FIRST_BASE_90K = 90000
#: Gap left between one session's last timestamp and the next one's first.
_SESSION_GAP_90K = 3000


def adts_header(
    payload_len: int, sample_rate_index: int = 3, channels: int = 1
) -> bytes:
    """7-byte ADTS header for one AAC-LC frame (MPEG-TS carries AAC as ADTS).

    ``sample_rate_index`` 3 is 48 kHz.
    """
    n = payload_len + 7
    return bytes(
        (
            0xFF,
            0xF1,  # MPEG-4, layer 0, no CRC
            (1 << 6) | (sample_rate_index << 2) | ((channels >> 2) & 1),  # AAC LC
            ((channels & 3) << 6) | ((n >> 11) & 3),
            (n >> 3) & 0xFF,
            ((n & 7) << 5) | 0x1F,
            0xFC,
        )
    )


class _Sink:
    """The container's file object: forwards to the channel, remembers failure."""

    def __init__(self, channel) -> None:
        self._channel = channel
        self.failed = False

    def write(self, b) -> int:
        try:
            return self._channel.write(b)
        except Exception:
            self.failed = True
            raise

    def flush(self) -> None:
        return None

    def mark_keyframe(self) -> None:
        self._channel.mark_keyframe()


class TsSession:
    """One camera session's input to the tee. Media times start at 0."""

    def __init__(self, tee: "TsTee", sid: int) -> None:
        self._tee, self._sid = tee, sid

    def video(self, au: bytes, media90: int, keyframe: bool) -> None:
        """One H.264 access unit; ``media90``: 90 kHz ticks since the first frame."""
        self._tee._put((self._sid, "v", bytes(au), int(media90), bool(keyframe)))

    def aac(self, au: bytes, media48: int) -> None:
        """One raw AAC frame; ``media48``: samples since the track's first frame."""
        self._tee._put((self._sid, "a", bytes(au), int(media48), False))


class TsTee:
    """One camera's video and AAC in, MPEG-TS out to its ``TsChannel``."""

    def __init__(self, channel, device_id: str = "?", queue_max: int = 600) -> None:
        self._channel = channel
        self._device_id = device_id
        self._q: "queue.Queue" = queue.Queue(maxsize=queue_max)
        self._stop = threading.Event()
        self._thread: Optional[threading.Thread] = None
        self._sids = itertools.count(1)
        self._resync = False
        self._stats = {
            "video_in": 0,
            "aac_in": 0,
            "dropped": 0,
            "overflows": 0,
            "sessions": 0,
            "failed": False,
        }

    def session(self) -> TsSession:
        """Start a new session's input (a camera (re)connect)."""
        self._stats["sessions"] += 1
        return TsSession(self, next(self._sids))

    def _put(self, item) -> None:
        if self._stop.is_set():
            return
        _sid, kind, _d, _t, kf = item
        self._stats["video_in" if kind == "v" else "aac_in"] += 1
        if self._resync and not (kind == "v" and kf):
            self._stats["dropped"] += 1
            return
        try:
            self._q.put_nowait(item)
            self._resync = False
        except queue.Full:
            # Never drop one AAC frame and keep going: start over at a keyframe.
            if not self._resync:
                self._stats["overflows"] += 1
            self._resync = True
            self._stats["dropped"] += 1

    # -- lifecycle ---------------------------------------------------------- #

    def start(self) -> None:
        if self._thread is None:
            self._thread = threading.Thread(
                target=self._run, name="aidot-ts-tee", daemon=True
            )
            self._thread.start()

    def is_running(self) -> bool:
        return self._thread is not None and self._thread.is_alive()

    def stats(self) -> dict:
        out = dict(self._stats)
        count = getattr(self._channel, "consumer_count", None)
        out["consumers"] = count() if callable(count) else 0
        return out

    def close(self) -> None:
        self._stop.set()
        try:
            self._q.put_nowait(None)
        except queue.Full:
            pass
        if self._thread is not None:
            self._thread.join(timeout=3.0)

    # -- mux thread --------------------------------------------------------- #

    def _run(self) -> None:
        try:
            import av
        except Exception as exc:  # pragma: no cover - [webrtc] extra missing
            _LOGGER.warning(
                "camera %s: HLS TS: PyAV unavailable: %s", self._device_id, exc
            )
            self._stats["failed"] = True
            return
        sink = _Sink(self._channel)

        def open_mux():
            out = av.open(
                sink,
                "w",
                format="mpegts",
                # 100 ms: never hold video back waiting for audio (2026-07 field
                # failure); flush per packet so a keyframe signal lines up with
                # the bytes it describes.
                options={"max_interleave_delta": "100000", "flush_packets": "1"},
            )
            vs = out.add_stream("h264")
            vs.time_base = _TB90
            as_ = out.add_stream("aac", rate=48000)
            as_.layout = "mono"
            return out, vs, as_

        try:
            out, vs, as_ = open_mux()
        except Exception as exc:
            _LOGGER.warning(
                "camera %s: HLS TS: mux open failed: %r", self._device_id, exc
            )
            self._stats["failed"] = True
            return
        sid = None
        base = _FIRST_BASE_90K
        started = False
        last_v = last_a = None  # last PTS written per track (90 kHz)
        try:
            while not self._stop.is_set():
                try:
                    item = self._q.get(timeout=0.5)
                except queue.Empty:
                    continue
                if item is None:
                    break
                isid, kind, data, t, kf = item
                if sid is not None and isid < sid:
                    # An older session still writing during a reconnect: the
                    # newer one owns the timeline now. Switching back and forth
                    # re-based it each time and ran it away.
                    continue
                if isid != sid:
                    if sid is not None and self.stats()["consumers"] == 0:
                        # Nobody is reading, so nobody can see time go back:
                        # start over in a new mux (the muxer itself refuses
                        # to go back), which keeps the timeline far from the
                        # 33-bit PTS wrap unless one consumer stays a day.
                        try:
                            out.close()
                        except Exception:
                            _LOGGER.debug("TsTee: closing the old mux", exc_info=True)
                        out, vs, as_ = open_mux()
                        base, last_v, last_a = _FIRST_BASE_90K, None, None
                    elif sid is not None and (last_v is not None or last_a is not None):
                        # A consumer is connected: continue after the last write.
                        last = max(x for x in (last_v, last_a) if x is not None)
                        base = last + _SESSION_GAP_90K + _AAC_PRIMING_90K
                    sid = isid
                    started = False
                if kind == "v":
                    if not started:
                        if not kf:
                            continue  # a consumer must be able to decode from here
                        started = True
                    pts = base + t
                    if last_v is not None and pts <= last_v:
                        pts = last_v + 1  # never backwards on one track
                    last_v = pts
                    pkt = av.Packet(data)
                    pkt.stream = vs
                    pkt.pts = pkt.dts = pts
                    pkt.time_base = _TB90
                    if kf:
                        pkt.is_keyframe = True
                        sink.mark_keyframe()
                    out.mux(pkt)
                else:
                    if not started:
                        continue  # no picture yet for this sound
                    pts = base + t * 90000 // 48000 - _AAC_PRIMING_90K
                    if last_a is not None and pts <= last_a:
                        continue
                    last_a = pts
                    pkt = av.Packet(adts_header(len(data)) + data)
                    pkt.stream = as_
                    pkt.pts = pkt.dts = pts
                    pkt.time_base = _TB90
                    out.mux(pkt)
                if sink.failed:
                    raise OSError("TS sink write failed")
        except Exception as exc:
            self._stats["failed"] = True
            _LOGGER.warning("camera %s: HLS TS mux stopped (%r)", self._device_id, exc)
        finally:
            try:
                out.close()
            except Exception:
                _LOGGER.debug("swallowed exception in %s", "TsTee._run", exc_info=True)
