"""Mux a direct publisher's video and AAC into MPEG-TS on one clock.

Home Assistant's HLS stream worker keeps whatever relation between audio and
video its input demuxer gives it. Through go2rtc's RTSP that relation is
re-based per consumer, so a recording or HLS view that joins a running stream
gets its sound 0.1-0.75 s late, differently each time (measured 2026-10-03,
synthetic and with claps). Here the publisher hands over each video access unit
and each AAC frame with the timestamps it already publishes, and they are muxed
on a single clock, so every consumer of the ``TsFanoutServer`` gets them in step
whenever it joins.

Both tracks start from their first frame, the origin the publishers already
align for Home Assistant's first-packet semantics (the AAC pacer follows video
media time until audio arrives, and lines its start up with the video backlog -
rc33). Nothing is re-encoded. The reorder slack ``video_pts_dts`` adds to video
presentation is added to audio too - forgetting that put audio 2 s ahead of the
picture in rc35's recordings until it was caught.

Input never blocks: ``video()`` and ``aac()`` queue and return, dropping (and
counting) when the mux thread falls behind.
"""

from __future__ import annotations

import logging
import queue
import threading
from fractions import Fraction
from typing import Optional

from .protocol import _reorder_slack, video_pts_dts

_LOGGER = logging.getLogger(__name__)

_MASK32 = 0xFFFFFFFF
_TB90 = Fraction(1, 90000)


def _signed32(d: int) -> int:
    d &= _MASK32
    return d - (1 << 32) if d >= 1 << 31 else d


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


class _Unwrap:
    """32-bit RTP timestamps -> a monotonic count from the first one."""

    def __init__(self) -> None:
        self._first: Optional[int] = None
        self._last = 0
        self._acc = 0

    def __call__(self, ts: int) -> int:
        ts &= _MASK32
        if self._first is None:
            self._first = self._last = ts
            return 0
        self._acc += _signed32(ts - self._last)
        self._last = ts
        return self._acc


class _Sink:
    """The container's file object: forwards to the server, remembers failure."""

    def __init__(self, server) -> None:
        self._server = server
        self.failed = False

    def write(self, b) -> int:
        try:
            return self._server.write(b)
        except Exception:
            self.failed = True
            raise

    def flush(self) -> None:
        return None

    def mark_keyframe(self) -> None:
        self._server.mark_keyframe()


class TsTee:
    """Video access units and AAC frames in, MPEG-TS out to ``server``."""

    def __init__(self, server, device_id: str = "?", queue_max: int = 600) -> None:
        self._server = server
        self._device_id = device_id
        self._q: "queue.Queue" = queue.Queue(maxsize=queue_max)
        self._stop = threading.Event()
        self._thread: Optional[threading.Thread] = None
        self._stats = {"video_in": 0, "aac_in": 0, "dropped": 0, "failed": False}

    # -- input (publisher threads) ---------------------------------------- #

    def video(self, au: bytes, pts90: int, keyframe: bool) -> None:
        self._put(("v", bytes(au), int(pts90), bool(keyframe)), "video_in")

    def aac(self, au: bytes, pts48: int) -> None:
        self._put(("a", bytes(au), int(pts48), False), "aac_in")

    def _put(self, item, counter: str) -> None:
        if self._stop.is_set():
            return
        self._stats[counter] += 1
        try:
            self._q.put_nowait(item)
        except queue.Full:
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
        count = getattr(self._server, "consumer_count", None)
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
        sink = _Sink(self._server)
        try:
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
        except Exception as exc:
            _LOGGER.warning(
                "camera %s: HLS TS: mux open failed: %r", self._device_id, exc
            )
            self._stats["failed"] = True
            return
        slack = _reorder_slack()
        vts, ats = _Unwrap(), _Unwrap()
        vstate: dict = {}
        started = False
        last_apts = None
        try:
            while not self._stop.is_set():
                try:
                    item = self._q.get(timeout=0.5)
                except queue.Empty:
                    continue
                if item is None:
                    break
                kind, data, ts, kf = item
                if kind == "v":
                    rel = vts(ts)  # origin: the first frame, keyframe or not
                    if not started:
                        if not kf:
                            continue  # a consumer must be able to decode from here
                        started = True
                    pkt = av.Packet(data)
                    pkt.stream = vs
                    pkt.pts, pkt.dts = video_pts_dts(vstate, rel, slack)
                    pkt.time_base = _TB90
                    if kf:
                        sink.mark_keyframe()
                    out.mux(pkt)
                else:
                    rel = ats(ts)
                    if not started:
                        continue  # no picture yet for this sound
                    pts = rel * 90000 // 48000 + slack
                    if last_apts is not None and pts <= last_apts:
                        continue
                    last_apts = pts
                    pkt = av.Packet(adts_header(len(data)) + data)
                    pkt.stream = as_
                    pkt.pts = pkt.dts = pts
                    pkt.time_base = _TB90
                    out.mux(pkt)
                if sink.failed:
                    raise OSError("TS sink write failed")
        except Exception as exc:
            self._stats["failed"] = True
            _LOGGER.warning(
                "camera %s: HLS TS mux stopped (%r) - HLS falls back to go2rtc",
                self._device_id,
                exc,
            )
        finally:
            try:
                out.close()
            except Exception:
                _LOGGER.debug("swallowed exception in %s", "TsTee._run", exc_info=True)
