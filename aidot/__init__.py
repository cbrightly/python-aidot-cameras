"""AiDot camera and device library."""

import logging as _logging

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

# Cap the per-packet loggers that flood the log at DEBUG.  When a user enables
# DEBUG on the parent ``aidot`` logger to diagnose the integration, these emit
# thousands of lines per second (in one capture, 99% of all lines) - and on a
# microSD Home Assistant host that log I/O can starve the recorder.  Cap them at
# INFO so enabling ``aidot`` DEBUG stays useful; the diagnostically valuable
# DEBUG (DTLS, SCTP/DCEP, ICE connection state) still flows.  This covers both the
# vendored aiortc RTP receiver/sender AND the external ``aioice`` package's
# per-STUN/TURN-packet loggers (aioice is a real dependency, not vendored, so it
# is not under ``aidot._vendor``).  Respect an explicit level if the user set one.
for _chatty in (
    "aidot._vendor.aiortc.rtcrtpreceiver",
    "aidot._vendor.aiortc.rtcrtpsender",
    "aioice.ice",
    "aioice.turn",
):
    _lg = _logging.getLogger(_chatty)
    if _lg.level == _logging.NOTSET:
        _lg.setLevel(_logging.INFO)

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
