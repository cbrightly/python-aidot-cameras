"""AiDot camera and device library."""

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
