"""One INFO line per live session per period, saying what the link did.

The loops that hold a session open (the SDES keepalive and the DTLS serve loop)
already poll every few seconds. They hand each poll's counters to a
:class:`HealthReporter`, which says nothing until ``period_s`` has elapsed and
then logs one line with the **deltas** for that period:

    camera <id>: health 60s: packets=1200 (20.0/s) bytes=60.0KB lost=2 (0.2%)
        stall=0.5s path=direct viewers=present

Zero loss and a sub-second stall is the normal reading between reconnects. A
steady non-zero loss is packet loss on the path from the camera. A growing
stall with a present viewer is the picture freezing. These were all available
before, as totals in end-of-session reports or at DEBUG; reporting the rate
once a minute, by default, is what lets a pasted log say which it was.

Everything here is pure: the reporter never reads a session itself. The loops
build the snapshot from what they already have (``SdesSession.media_stats()``,
``WebRTCSession.get_stats()``, the serve canary, their viewer-presence answer),
so the reporter can be tested with dicts and a fake clock.
"""

from __future__ import annotations

import logging
import math
from typing import Any, Mapping, Optional

import os

_LOGGER = logging.getLogger(__name__)

_PERIOD_ENV = "AIDOT_HEALTH_LINE_S"
_PERIOD_DEFAULT_S = 60.0


def health_line_period_s(env: Optional[Mapping[str, str]] = None) -> float:
    """Seconds between health lines; ``0`` disables them (negative reads as 0).

    A malformed or non-finite value is the default: this is read when a session
    opens, and a typo in a unit file must not be able to stop a camera."""
    raw = (os.environ if env is None else env).get(_PERIOD_ENV)
    if raw is None:
        return _PERIOD_DEFAULT_S
    try:
        v = float(raw)
    except (TypeError, ValueError):
        v = None
    if v is None or not math.isfinite(v):
        _LOGGER.debug(
            "%s=%r is not a number; using %.0f", _PERIOD_ENV, raw, _PERIOD_DEFAULT_S
        )
        return _PERIOD_DEFAULT_S
    return max(0.0, v)


def _fmt_bytes(n: float) -> str:
    n = float(max(0.0, n))
    if n < 1000:
        return f"{int(n)}B"
    for unit, div in (("KB", 1000.0), ("MB", 1000.0**2), ("GB", 1000.0**3)):
        if n < div * 1000 or unit == "GB":
            return f"{n / div:.1f}{unit}"
    return f"{n:.0f}B"  # unreachable; keeps the type checker honest


def _reset(prev: Optional[Mapping[str, Any]], cur: Mapping[str, Any]) -> bool:
    """Whether the counters were reset between the two snapshots.

    Judged on ``packets`` alone: it only goes backwards when a session was
    rebuilt under the same reporter. The other counters are not trusted for
    this, because aiortc's ``packetsLost`` is expected-minus-received and drops
    by a few when late packets arrive, which is not a reset."""
    if prev is None:
        return True
    return int(cur.get("packets") or 0) < int(prev.get("packets") or 0)


def _delta(
    prev: Optional[Mapping[str, Any]], cur: Mapping[str, Any], key: str, reset: bool
) -> int:
    """``cur[key] - prev[key]``, or ``cur[key]`` after a reset (there is no
    previous reading, or the session was rebuilt). A counter that went
    backwards without a reset reads as 0, never as a negative or as its
    absolute value."""
    now = int(cur.get(key) or 0)
    if reset:
        return now
    return max(0, now - int(prev.get(key) or 0))


def _pct(part: int, whole: int) -> float:
    if whole <= 0:
        return 100.0 if part > 0 else 0.0
    return 100.0 * part / whole


def format_health_line(
    device_id: str,
    period_s: float,
    prev: Optional[Mapping[str, Any]],
    cur: Mapping[str, Any],
    now: float,
) -> str:
    """The line for one period. ``prev`` is the snapshot at the previous line
    (or ``None`` for the first); ``cur`` the snapshot now; ``now`` the
    monotonic clock the snapshot's ``last_media_monotonic`` is on."""
    secs = max(period_s, 1e-9)
    reset = _reset(prev, cur)
    packets = _delta(prev, cur, "packets", reset)
    parts = [
        f"camera {device_id}: health {period_s:.0f}s:",
        f"packets={packets} ({packets / secs:.1f}/s)",
    ]
    if "bytes" in cur:
        parts.append(f"bytes={_fmt_bytes(_delta(prev, cur, 'bytes', reset))}")
    if "lost" in cur:
        # Only a transport that counts loss says so: aiortc's inbound-rtp
        # report on DTLS. The SDES bridge recovers loss with NACK and has no
        # receiver-side count, so its line carries no misleading zero.
        lost = _delta(prev, cur, "lost", reset)
        parts.append(f"lost={lost} ({_pct(lost, packets + lost):.1f}%)")
    last_media = float(cur.get("last_media_monotonic") or 0.0)
    if last_media > 0.0 and math.isfinite(last_media):
        parts.append(f"stall={max(0.0, now - last_media):.1f}s")
    else:
        parts.append("no media yet")
    parts.append(f"path={cur.get('media_path') or '?'}")
    viewer = cur.get("viewer")
    parts.append(
        "viewers="
        + ("present" if viewer is True else "absent" if viewer is False else "unknown")
    )
    if "frames" in cur:
        frames = _delta(prev, cur, "frames", reset)
        parts.append(f"frames={frames} ({frames / secs:.1f}/s)")
    if "keyframes" in cur:
        parts.append(f"keyframes={_delta(prev, cur, 'keyframes', reset)}")
    if cur.get("max_keyframe_gap") is not None:
        # The serve canary's largest run of frames between two keyframes - a
        # frame COUNT, not a time: 44 on a 15 fps camera is a three-second GOP.
        # Printed as seconds in 1.0.0rc49, which read as a 44 s freeze.
        parts.append(f"max_keyframe_gap={int(cur['max_keyframe_gap'])}")
    return " ".join(parts)


def dtls_snapshot(
    stats: Mapping[str, Any],
    canary: Optional[Mapping[str, Any]],
    last_media_monotonic: float,
    viewer: Optional[bool],
) -> dict:
    """A reporter snapshot from what the DTLS serve loop already has.

    ``stats`` is ``WebRTCSession.get_stats()``: inbound RTP packets received
    and lost, summed over audio and video, and the nominated ICE pairs, from
    which the path is ``relay`` when any remote candidate is a relay, else
    ``direct``, else unknown. ``canary`` is the serve's video canary (frames,
    keyframes, largest frame count between keyframes). There is no byte count
    on this transport, so the
    snapshot carries none and the line says nothing about bytes.
    """
    packets = 0
    lost = 0
    for row in stats.get("inbound") or ():
        packets += int(row.get("packets_received") or 0)
        lost += max(0, int(row.get("packets_lost") or 0))
    pairs = stats.get("ice") or ()
    path: Optional[str] = None
    if pairs:
        path = (
            "relay" if any(p.get("remote_type") == "relay" for p in pairs) else "direct"
        )
    out: dict = {
        "packets": packets,
        "lost": lost,
        "last_media_monotonic": last_media_monotonic,
        "media_path": path,
        "viewer": viewer,
    }
    if canary:
        out["frames"] = int(canary.get("frames") or 0)
        out["keyframes"] = int(canary.get("keyframes") or 0)
        out["max_keyframe_gap"] = int(canary.get("max_gap") or 0)
    return out


class HealthReporter:
    """Turns a stream of counter snapshots into one line per period.

    ``tick(now, snapshot)`` is called on the loop's own cadence with the
    monotonic clock and the current counters. It returns the line it logged,
    or ``None`` when the period has not elapsed (or the reporter is disabled),
    so a caller never has to track the schedule itself.
    """

    def __init__(
        self,
        device_id: str,
        period_s: Optional[float] = None,
        logger: Optional[logging.Logger] = None,
    ) -> None:
        self.device_id = device_id
        self.period_s = health_line_period_s() if period_s is None else float(period_s)
        self._log = logger or _LOGGER
        self._last_at: Optional[float] = None
        self._last_snapshot: Optional[dict] = None

    @property
    def enabled(self) -> bool:
        return self.period_s > 0.0

    def due(self, now: float) -> bool:
        """Whether a ``tick`` at ``now`` would do anything (anchor or report).

        For a caller whose snapshot costs something to build (an awaited
        ``getStats()``), so it can skip the work on the ticks in between."""
        if not self.enabled:
            return False
        return self._last_at is None or now - self._last_at >= self.period_s

    def skip(self, now: float) -> None:
        """Count this period as reported without a line, for a caller whose
        snapshot failed: the next attempt is a period away, not the next poll."""
        self._last_at = now

    def tick(self, now: float, snapshot: Mapping[str, Any]) -> Optional[str]:
        if not self.enabled:
            return None
        if self._last_at is None:
            # The first tick only anchors the period; there is no delta to report.
            self._last_at = now
            self._last_snapshot = dict(snapshot)
            return None
        elapsed = now - self._last_at
        if elapsed < self.period_s:
            return None
        line = format_health_line(
            self.device_id, elapsed, self._last_snapshot, snapshot, now
        )
        self._log.info("%s", line)
        self._last_at = now
        self._last_snapshot = dict(snapshot)
        return line
