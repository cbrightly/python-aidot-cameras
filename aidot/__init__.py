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
