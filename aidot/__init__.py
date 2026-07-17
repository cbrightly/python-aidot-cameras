"""AiDot camera and device library."""

import logging as _logging
import time as _time

from .client import AidotClient
from .device_client import DeviceClient
from .discover import Discover
from .exceptions import (
    AidotAuthFailed,
    AidotAuthTokenExpired,
    AidotCameraBusy,
    AidotError,
    AidotNotLogin,
    AidotOSError,
    AidotUserOrPassIncorrect,
    HTTPError,
    InvalidHost,
    InvalidURL,
)

# Cap the noisy external/vendored loggers so enabling DEBUG on the parent
# ``aidot`` logger to diagnose the integration does not unleash a flood that,
# on a microSD Home Assistant host, can starve the recorder with log I/O.
#
# The vendored aiortc RTP receiver/sender log every media packet at DEBUG, so
# capping them at INFO removes that flood while leaving the diagnostically
# valuable DEBUG (DTLS, SCTP/DCEP, ICE connection state) flowing.
#
# The external ``aioice`` package's flood is different: it logs ICE
# connectivity-check state transitions at INFO, not DEBUG (aioice is a real
# dependency, not vendored, so it is not under ``aidot._vendor``). An INFO cap
# cannot suppress an INFO-level flood, so ``aioice.ice`` and ``aioice.turn``
# are capped at WARNING instead; aioice emits nothing at WARNING or above, so
# no real warning is lost. A user who wants the aioice ICE INFO logging back
# (e.g. to debug connectivity issues) can set that logger's level explicitly -
# the NOTSET guard below respects it.
_EXTERNAL_LOGGER_CAPS = {
    "aidot._vendor.aiortc.rtcrtpreceiver": _logging.INFO,
    "aidot._vendor.aiortc.rtcrtpsender": _logging.INFO,
    "aioice.ice": _logging.WARNING,
    "aioice.turn": _logging.WARNING,
}


def _cap_external_loggers() -> None:
    """Idempotently apply the caps in ``_EXTERNAL_LOGGER_CAPS``.

    Only touches a logger whose level is still NOTSET, so an explicit level
    set by the user (before or after this runs) is never overridden.
    """
    for _name, _level in _EXTERNAL_LOGGER_CAPS.items():
        _lg = _logging.getLogger(_name)
        if _lg.level == _logging.NOTSET:
            _lg.setLevel(_level)


_cap_external_loggers()

# The vendored H.264 decoder logs a WARNING per corrupt/undecodable frame
# (aidot/_vendor/aiortc/codecs/h264.py:118). A degrading link can therefore
# log hundreds of identical lines in a few minutes; a live evaluation saw 172
# in 11 minutes. A level cap cannot help here since the message is already at
# WARNING, and the message is a corruption canary for a degrading link, so it
# must not be silenced outright. Instead a rate-limiting filter lets the
# first WARNING through immediately, suppresses subsequent same-logger
# WARNINGs within a window, and lets the first WARNING after the window
# elapses through carrying the suppressed count, so the canary stays visible
# at a low rate instead of flooding.
_H264_DECODE_LOGGER_NAME = "aidot._vendor.aiortc.codecs.h264"
_H264_DECODE_RATE_LIMIT_WINDOW_SECONDS = 30.0


class _RateLimitingWarningFilter(_logging.Filter):
    """Rate-limit WARNING records on the logger it is attached to.

    The first WARNING is let through immediately. Subsequent WARNINGs within
    ``window_seconds`` of it are suppressed (dropped) but counted. The next
    WARNING to arrive once the window has elapsed is let through, with its
    message annotated with how many were suppressed during the window, and a
    new window begins from that record's time. Records at other levels, and
    records on other loggers, are never touched.

    ``time_func`` defaults to ``time.monotonic`` but is injectable so tests
    can drive the window deterministically without real sleeps.
    """

    def __init__(self, window_seconds: float = _H264_DECODE_RATE_LIMIT_WINDOW_SECONDS, time_func=_time.monotonic) -> None:
        super().__init__()
        self._window_seconds = window_seconds
        self._time_func = time_func
        self._window_start = None
        self._suppressed = 0

    def filter(self, record: _logging.LogRecord) -> bool:
        if record.levelno != _logging.WARNING:
            return True
        _now = self._time_func()
        if self._window_start is None:
            self._window_start = _now
            return True
        if _now - self._window_start < self._window_seconds:
            self._suppressed += 1
            return False
        _suppressed = self._suppressed
        self._window_start = _now
        self._suppressed = 0
        if _suppressed:
            record.msg = f"{record.getMessage()} (suppressed {_suppressed} similar warning(s) in the last {self._window_seconds:.0f}s)"
            record.args = ()
        return True


def _install_h264_decode_rate_limit_filter() -> None:
    """Idempotently attach the rate-limiting filter to the h264 decode logger."""
    _lg = _logging.getLogger(_H264_DECODE_LOGGER_NAME)
    if not any(isinstance(_f, _RateLimitingWarningFilter) for _f in _lg.filters):
        _lg.addFilter(_RateLimitingWarningFilter())


_install_h264_decode_rate_limit_filter()

__all__ = [
    "AidotAuthFailed",
    "AidotAuthTokenExpired",
    "AidotCameraBusy",
    "AidotClient",
    "AidotError",
    "AidotNotLogin",
    "AidotOSError",
    "AidotUserOrPassIncorrect",
    "DeviceClient",
    "Discover",
    "HTTPError",
    "InvalidHost",
    "InvalidURL",
]
