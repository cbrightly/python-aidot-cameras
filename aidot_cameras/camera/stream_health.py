"""What a camera's background stream is doing, as the serve loops know it.

A consumer that shows a live view cannot tell, from the stream alone, why
nothing is on screen: the camera may still be connecting, a battery camera may
be waking, the loop may be waiting out a backoff after a failure, it may have
given up after a run of futile sessions, or the camera may be switched off.
The serve loops (``start_keepalive``) know which, and record it here.

The public surface is small:

* ``StreamHealth`` - an immutable snapshot, read from
  ``CameraDeviceClient.stream_health``.
* ``CameraDeviceClient.add_stream_health_listener(callback)`` - call
  ``callback()`` on the event loop whenever the snapshot changes.
* ``STREAM_STATES`` and ``STREAM_END_REASONS`` - every value ``state`` and
  ``last_end_reason`` can take.

Everything else in this module is private to the library.

Only what the loops can really distinguish is reported. In particular:

* ``waking`` means "connecting to a battery camera". The library cannot see
  whether the camera was asleep, only that an open on a battery camera pays
  for a wake when it was.
* ``off`` is the camera's own power switch (``status.powered`` False). A
  camera the cloud reports offline is not a separate state: its opens fail
  and the state reads ``retrying``; ``cloud_reports_offline`` answers that
  question.
* On a DTLS camera "media" is video a viewer can use (a keyframe). A session
  that carries audio and no video is not streaming: it stays ``connecting``
  until the loop ends it as ``no_video``, and it counts as a failure.
* While the cloud reports the camera offline (or it is switched off), the
  SDES and JPEG loops hold their reopen for up to ``AIDOT_OFFLINE_PROBE_S``
  instead of the backoff, and ``retry_delay_s`` is that hold.
* ``last_connect_s`` runs from the start of the open attempt that succeeded
  to the first media the loop saw. On an SDES camera the open itself returns
  on first media, so the figure is exact. On a DTLS camera the loop polls
  (every 0.2 s while a serve starts, every 0.5 s after), so the figure can
  read up to half a second long.
"""

from __future__ import annotations

import dataclasses
import logging
from typing import Any, Callable, Optional

_LOGGER = logging.getLogger(__name__)

#: No background stream: never started, released when the last viewer left,
#: or stopped by the caller.
STATE_IDLE = "idle"
#: An open is in flight, or a session is up and has not delivered media yet.
STATE_CONNECTING = "connecting"
#: The same on a battery camera, whose open includes its wake.
STATE_WAKING = "waking"
#: A session is up and media has been seen on it.
STATE_STREAMING = "streaming"
#: An open failed or a session ended badly; the loop is waiting to reopen.
STATE_RETRYING = "retrying"
#: The loop gave up after a run of futile sessions. Nothing reopens until the
#: next ``start_keepalive`` (a live view).
STATE_DORMANT = "dormant"
#: The camera's own power switch is off and nothing is streaming.
STATE_OFF = "off"

#: Every value ``StreamHealth.state`` can take.
STREAM_STATES: "tuple[str, ...]" = (
    STATE_IDLE,
    STATE_CONNECTING,
    STATE_WAKING,
    STATE_STREAMING,
    STATE_RETRYING,
    STATE_DORMANT,
    STATE_OFF,
)

#: The serve was released because nobody was watching.
END_IDLE_RELEASE = "idle_release"
#: The caller stopped the stream (``async_stop_streaming``).
END_STOPPED = "stopped"
#: The peer connection dropped (DTLS cameras).
END_CONNECTION_LOST = "connection_lost"
#: The camera closed a session that had been delivering video (DTLS cameras).
END_CAMERA_CLOSED = "camera_closed"
#: The local serve or publish ended (the consumer of the stream went away).
END_SERVE_ENDED = "serve_ended"
#: The session connected and never delivered video (DTLS cameras).
END_NO_VIDEO = "no_video"
#: Media stopped arriving on a session that was up.
END_MEDIA_STALLED = "media_stalled"
#: Media and the camera's ICE answers both stopped (SDES cameras).
END_ICE_LOST = "ice_lost"
#: The camera answered the open and then sent no media.
END_NO_MEDIA = "no_media"
#: The camera sent the other video codec than the one asked for; reopened.
END_WRONG_CODEC = "wrong_codec"
#: The camera refused the stream: it has no free session.
END_BUSY = "busy"
#: The camera answered that it is not ready yet (waking, encoder cold).
END_NOT_READY = "not_ready"
#: The open failed for any other reason (no answer, ICE never established).
END_OPEN_FAILED = "open_failed"
#: The serve loop itself ended on an unexpected error; the camera was released.
END_ERROR = "error"

#: Every value ``StreamHealth.last_end_reason`` can take besides ``None``.
#: A later minor release may add to it; treat an unknown value as "ended".
STREAM_END_REASONS: "tuple[str, ...]" = (
    END_IDLE_RELEASE,
    END_STOPPED,
    END_CONNECTION_LOST,
    END_CAMERA_CLOSED,
    END_SERVE_ENDED,
    END_NO_VIDEO,
    END_MEDIA_STALLED,
    END_ICE_LOST,
    END_NO_MEDIA,
    END_WRONG_CODEC,
    END_BUSY,
    END_NOT_READY,
    END_OPEN_FAILED,
    END_ERROR,
)


@dataclasses.dataclass(frozen=True, slots=True)
class StreamHealth:
    """A snapshot of one camera's background stream.

    ``state`` is one of ``STREAM_STATES``.

    ``last_connect_s`` is how long the last successful connect took, in
    seconds, from the start of the open attempt that succeeded to first
    media; ``None`` until a session has delivered media. Failed attempts and
    the waits between them are not in it: ``failures`` counts those.

    ``last_end_reason`` is why the last session or open attempt ended, one of
    ``STREAM_END_REASONS``, or ``None`` when nothing has ended yet. It is kept
    while the next session streams, so "streaming" with a reason reads as
    "streaming again after that".

    ``retry_delay_s`` is the longest the loop will wait before it reopens,
    in seconds, as of entering ``retrying`` (the loop reopens sooner when a
    camera reported offline comes back); ``None`` in any other state.

    ``failures`` counts open attempts and sessions that ended without
    delivering media since the last one that did. It is not reset by a
    restart of the stream, only by media.
    """

    state: str = STATE_IDLE
    last_connect_s: Optional[float] = None
    last_end_reason: Optional[str] = None
    retry_delay_s: Optional[float] = None
    failures: int = 0


class _StreamHealthTracker:
    """The serve loops' record of what they are doing. Private.

    Every method runs on the event loop. The tracker knows nothing about the
    camera; ``_stream_health_of`` adds what the camera knows (its power switch,
    whether it runs on a battery) when it builds the public snapshot.
    """

    __slots__ = (
        "_announced",
        "_listeners",
        "_stopped",
        "failures",
        "last_connect_s",
        "last_end_reason",
        "open_started",
        "phase",
        "retry_delay_s",
    )

    def __init__(self) -> None:
        self.phase: str = STATE_IDLE
        # Monotonic start of the open attempt in flight; None once its first
        # media has been timed (or when no attempt is in flight).
        self.open_started: Optional[float] = None
        self.last_connect_s: Optional[float] = None
        self.last_end_reason: Optional[str] = None
        self.retry_delay_s: Optional[float] = None
        self.failures: int = 0
        self._listeners: "list[Callable[[], None]]" = []
        # What the listeners were last told (see _set).
        self._announced = self._record()
        # Set by stopped(), cleared by starting(): the caller stopped the
        # stream, and whatever its loop still says while it winds down is
        # about a stream that no longer exists.
        self._stopped: bool = False

    # -- listeners ---------------------------------------------------------- #

    def add_listener(self, callback: Callable[[], None]) -> Callable[[], None]:
        self._listeners.append(callback)

        def _remove() -> None:
            try:
                self._listeners.remove(callback)
            except ValueError:
                pass

        return _remove

    def notify(self) -> None:
        for callback in list(self._listeners):
            try:
                callback()
            except Exception:
                _LOGGER.debug("stream health listener failed", exc_info=True)

    def _record(self) -> tuple:
        """Everything the public snapshot is built from."""
        return (
            self.phase,
            self.last_connect_s,
            self.last_end_reason,
            self.retry_delay_s,
            self.failures,
        )

    def _set(self, phase: str, *, retry_delay_s: Optional[float] = None) -> None:
        """Move to ``phase`` and tell the listeners, unless nothing they can
        read has changed since they were last told (a poll that repeats
        itself, a second open of the same connect)."""
        self.phase = phase
        self.retry_delay_s = retry_delay_s
        record = self._record()
        if record == self._announced:
            return
        self._announced = record
        self.notify()

    # -- what the loops report ---------------------------------------------- #

    def starting(self) -> None:
        """A background stream was asked for; its loop has not opened yet."""
        self._stopped = False
        self.open_started = None
        self._set(STATE_CONNECTING)

    def opening(self, now: float) -> None:
        """An open attempt begins at ``now`` (the caller's monotonic clock;
        ``media_started`` must be given the same one)."""
        if self._stopped:
            return
        self.open_started = now
        self._set(STATE_CONNECTING)

    def media_started(self, now: float) -> None:
        """Media has been seen on the session. Safe to call on every poll: the
        connect is timed once per open, and nothing is announced twice.

        Ignored after ``stopped()``: a loop that is still winding down, or a
        frame callback that fires late, must not bring a stopped camera back
        to streaming."""
        if self._stopped:
            return
        if self.open_started is not None:
            self.last_connect_s = round(max(0.0, now - self.open_started), 2)
            self.open_started = None
        self.failures = 0
        self._set(STATE_STREAMING)

    def ending(self, reason: str, *, failure: bool = True) -> None:
        """Record why an open attempt or a session ended, announcing nothing:
        for a caller about to say what happens next (``gave_up``).

        ``failure=False`` is for an end that is not the camera failing to
        deliver (it sent the other codec and is asked again at once)."""
        if self._stopped:
            return
        if failure and self.phase != STATE_STREAMING:
            self.failures += 1
        self.open_started = None
        self.last_end_reason = reason

    def ended(
        self,
        reason: str,
        delay: Optional[float] = None,
        *,
        still_connecting: bool = False,
        failure: bool = True,
    ) -> None:
        """An open attempt or a session ended for ``reason`` and the loop will
        reopen after ``delay`` seconds.

        A session that had delivered media is not counted as a failure. With
        no delay, or with ``still_connecting`` (a quick retry that is part of
        one connect, as while a battery camera wakes), the state goes back to
        connecting instead of retrying. ``failure`` is ``ending``'s.
        """
        if self._stopped:
            return
        self.ending(reason, failure=failure)
        if still_connecting or not delay or delay <= 0:
            self._set(STATE_CONNECTING)
        else:
            self._set(STATE_RETRYING, retry_delay_s=round(float(delay), 1))

    def released(self, reason: str) -> None:
        """The stream was let go (idle release, loop error): idle."""
        if self._stopped:
            return
        self.open_started = None
        self.last_end_reason = reason
        self._set(STATE_IDLE)

    def stopped(self) -> None:
        """The caller stopped the stream. A loop that had already let go, or
        given up, keeps its own account of why.

        From here until the next ``starting()`` nothing a loop reports is
        taken: the stop marks the stream idle before its loop has been
        cancelled, and the loop can still be between two awaits."""
        if self.phase not in (STATE_IDLE, STATE_DORMANT):
            self.released(END_STOPPED)
        self._stopped = True

    def gave_up(self) -> None:
        """The loop stopped reopening after a run of futile sessions. The
        reason of the last of them stands."""
        if self._stopped:
            return
        self.open_started = None
        self._set(STATE_DORMANT)


def _tracker_of(dev: Any) -> _StreamHealthTracker:
    """``dev``'s tracker, made on first use: some callers build a camera
    client without running its initialiser."""
    tracker = getattr(dev, "_stream_health_tracker", None)
    if tracker is None:
        tracker = _StreamHealthTracker()
        try:
            dev._stream_health_tracker = tracker
        except Exception:  # a client that cannot hold one still streams
            _LOGGER.debug("no stream health tracker on %r", dev, exc_info=True)
    return tracker


def _stream_health_of(dev: Any, *, powered_off: bool, battery: bool) -> StreamHealth:
    """The public snapshot for ``dev``: the tracker's record, with the two
    things only the camera knows laid over it."""
    tracker = _tracker_of(dev)
    state = tracker.phase
    if powered_off and state != STATE_STREAMING:
        state = STATE_OFF
    elif state == STATE_CONNECTING and battery:
        state = STATE_WAKING
    return StreamHealth(
        state=state,
        last_connect_s=tracker.last_connect_s,
        last_end_reason=tracker.last_end_reason,
        retry_delay_s=tracker.retry_delay_s if state == STATE_RETRYING else None,
        failures=tracker.failures,
    )
