"""Models for AiDot."""

from .device_client_model import (
    DeviceAck,
    DeviceAttr,
    DeviceAttrPayload,
    DeviceResponse,
    DeviceActionPayload,
    DeviceActionRequest,
    LoginPayload,
    LoginRequest,
    PingRequest,
    PingResponse,
)
from .device_model import (
    DeviceFading,
    DeviceProperties,
    DeviceProduct,
    DeviceInformation,
)

__all__ = [
    "DeviceAck",
    "DeviceActionPayload",
    "DeviceActionRequest",
    "DeviceAttr",
    "DeviceAttrPayload",
    "DeviceFading",
    "DeviceInformation",
    "DeviceProduct",
    "DeviceProperties",
    "DeviceResponse",
    "LoginPayload",
    "LoginRequest",
    "PingRequest",
    "PingResponse",
]
