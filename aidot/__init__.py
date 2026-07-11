"""AiDot camera and device library."""

import logging as _logging

# The vendored aiortc RTP receiver/sender log EVERY media packet at DEBUG.  When a
# user enables DEBUG on the parent ``aidot`` logger to diagnose the integration,
# that becomes thousands of lines per second and floods the logs (in one capture,
# 99% of all lines).  Cap just those two per-packet loggers at INFO so enabling
# ``aidot`` DEBUG stays useful - the diagnostically valuable aiortc DEBUG (DTLS,
# ICE, SCTP/DCEP) still flows.  Respect an explicit level if the user set one.
for _chatty in ("aidot._vendor.aiortc.rtcrtpreceiver", "aidot._vendor.aiortc.rtcrtpsender"):
    _lg = _logging.getLogger(_chatty)
    if _lg.level == _logging.NOTSET:
        _lg.setLevel(_logging.INFO)

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
