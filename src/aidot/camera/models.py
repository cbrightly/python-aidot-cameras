"""Camera data models, split out of client.py (behavior-preserving).

CameraStatusData / CameraDeviceInformation extend the core DeviceStatusData /
DeviceInformation; VideoFrame is the decoded-frame container. Re-exported from
client.py so existing imports keep working.
"""

import json
import logging
from dataclasses import dataclass
from typing import Any, Optional

from ..const import (
    CONF_CCT,
    CONF_DIMMING,
    CONF_ON_OFF,
    CONF_PRODUCT,
    CONF_PROPERTIES,
    CONF_RGBW,
    CONF_SERVICE_MODULES,
)
from ..device_client import DeviceInformation, DeviceStatusData
from .constants import (
    _FRAME_TYPE_AUDIO,
    _FRAME_TYPE_B_FRAME,
    _FRAME_TYPE_I_FRAME,
    _FRAME_TYPE_P_FRAME,
)

_LOGGER = logging.getLogger(__name__)


def _as_int(v: Any) -> Optional[int]:
    """Coerce a cloud-supplied attr value to int, or None if not numeric.

    Cloud/device attributes are untrusted: a non-numeric value must be skipped,
    not raise ValueError/TypeError out of the status-refresh path.
    """
    try:
        return int(v)
    except (ValueError, TypeError):
        return None


def _as_bool(v: Any) -> Optional[bool]:
    """Coerce a 0/1 (int or numeric string) attr to bool, or None if not numeric."""
    i = _as_int(v)
    return None if i is None else bool(i)


class CameraStatusData(DeviceStatusData):
    """Core status plus camera fields; accepts both typed-model and dict updates."""

    # Restore None-as-unknown semantics: the core defaults (red/2700K/100)
    # would otherwise be pushed to consumers as real state before the first
    # getDevAttrReq reply arrives.
    rgdb: "int | None" = None
    rgbw: "tuple[int, int, int, int] | None" = None
    cct: "int | None" = None
    dimming: "int | None" = None

    # Camera-specific fields (None = unknown/not yet queried)
    motion_detection: Optional[bool] = None
    motion_sensitivity: Optional[int] = None  # MotionDetection_Sen 1-5
    status_led: Optional[bool] = None
    microphone: Optional[bool] = None
    night_vision_mode: Optional[str] = None   # "auto" | "on" | "off"
    ir_light: Optional[bool] = None           # nightVisionIRLight 0/1
    floodlight: Optional[bool] = None
    ptz_tracking: Optional[bool] = None
    siren: bool = False
    speaker_volume: Optional[int] = None  # SoundLevel 0-100
    # Diagnostic / read-only camera fields
    battery_remaining: Optional[int] = None  # Battery_remaining 0-100 (%)
    occupancy: Optional[bool] = None          # Occupancy live presence
    sd_card_status: Optional[str] = None      # SDcardStatus
    wifi_rssi: Optional[int] = None           # networkRssi (dBm), cloud property

    def update(self, attr) -> None:
        if attr is None:
            return
        if not isinstance(attr, dict):
            # Typed DeviceAttr model from the core local-control receive path.
            # Guard: a malformed payload.attr (non-dict, non-model) must not
            # kill the caller's receive loop.
            if not hasattr(attr, "OnOff"):
                return
            super().update(attr)
            return
        # Dict path: camera attributes ONLY.  Light keys (OnOff/Dimming/RGBW/
        # CCT) are handled by the typed-model path in the core; the dict
        # feeders either strip them (update_from_camera_attributes) or have
        # already applied them via the model (receive_data raw-dict pass).
        # Camera attributes
        # Every conversion goes through _as_int/_as_bool so a malformed value
        # from the cloud (e.g. "on" where 0/1 is expected) is skipped rather than
        # raising out of update() and aborting the whole status refresh.
        if (b := _as_bool(attr.get("MotionDetection_Enable"))) is not None:
            self.motion_detection = b
        if (i := _as_int(attr.get("MotionDetection_Sen"))) is not None:
            self.motion_sensitivity = i
        if (b := _as_bool(attr.get("LedOnOff"))) is not None:
            self.status_led = b
        if (b := _as_bool(attr.get("micEnable"))) is not None:
            self.microphone = b
        if (v := attr.get("nightVisionMode")) is not None:
            nv = _as_int(v)
            if nv is not None:
                self.night_vision_mode = {0: "auto", 1: "on", 2: "off"}.get(nv, str(nv))
            else:
                # Camera may send string "on"/"off"/"auto" instead of 0/1/2
                self.night_vision_mode = str(v)
        if (b := _as_bool(attr.get("nightVisionIRLight"))) is not None:
            self.ir_light = b
        if (b := _as_bool(attr.get("LightOnOff"))) is not None:
            self.floodlight = b
        if (b := _as_bool(attr.get("trackingMode"))) is not None:
            self.ptz_tracking = b
        if (i := _as_int(attr.get("SoundLevel"))) is not None:
            self.speaker_volume = i
        # Diagnostic / read-only
        if (i := _as_int(attr.get("Battery_remaining"))) is not None:
            self.battery_remaining = i
        if (b := _as_bool(attr.get("Occupancy"))) is not None:
            self.occupancy = b
        if (v := attr.get("SDcardStatus")) is not None:
            self.sd_card_status = str(v)
        if (i := _as_int(attr.get("networkRssi"))) is not None:
            self.wifi_rssi = i

    # Cloud "properties" keys that belong to lights, not cameras.  A camera's
    # image "Dimming" must not be read as a light brightness (and could TypeError
    # if non-numeric), so exclude the light-only keys when populating a camera.
    _LIGHT_ONLY_ATTR_KEYS = (CONF_ON_OFF, CONF_DIMMING, CONF_RGBW, CONF_CCT)

    def update_from_camera_attributes(self, attrs: dict) -> None:
        """Populate camera fields from a camera attribute / cloud-properties dict.

        Accepts either a setDevAttrNotif ``attr`` dict or a cloud device
        ``properties`` dict - both share the same camera attribute keys
        (Battery_remaining, Occupancy, SDcardStatus, MotionDetection_*, …).
        """
        self.update({
            k: v for k, v in attrs.items()
            if k not in self._LIGHT_ONLY_ATTR_KEYS
        })


class CameraDeviceInformation(DeviceInformation):
    """Core device info plus camera fields parsed from the device dict."""

    # Per-device AES-128 key from the API (16-char ASCII).  Hypothesis: used
    # for TUTK IOCtrl encryption in LDS/SDES mode.  Confirmed structure-fit
    # (16B = AES-128).  Needs key from PTZ/L2 cameras to test against pcap.
    aes_key: str
    # Local device access password (TUTK viewPwd candidate)
    device_password: str
    # TUTK IOCtrl direction codes the camera advertises (e.g. [3,6] = left+right
    # for a pan-only camera, [1,2,3,6] = full PTZ).  Empty means unknown - callers
    # should treat unknown as "show all" for backward compatibility.
    ptz_directions: list

    def __init__(self, device: dict[str, Any]) -> None:
        super().__init__(device)
        # aesKey is a list in the API response; take first entry
        _aes = device.get("aesKey") or []
        self.aes_key = _aes[0] if isinstance(_aes, list) and _aes else (
            str(_aes) if _aes else "")
        self.device_password = device.get("password") or ""
        self.ptz_directions = []
        if CONF_PRODUCT in device and CONF_SERVICE_MODULES in device[CONF_PRODUCT]:
            for service in device[CONF_PRODUCT][CONF_SERVICE_MODULES]:
                for prop in service.get(CONF_PROPERTIES) or []:
                    if prop.get("code") == "ptzDirection":
                        raw = prop.get("allowedValues")
                        try:
                            codes = json.loads(raw) if isinstance(raw, str) else raw
                            if isinstance(codes, list):
                                self.ptz_directions = [int(c) for c in codes]
                        except Exception:
                            _LOGGER.debug("swallowed exception in %s", '__init__', exc_info=True)


@dataclass
class VideoFrame:
    # frame_type: 2=P-frame  3=B-frame  4=I-frame/keyframe  5=audio
    # audio_codec: 0=N/A  1=G.711A  (meaningful only when frame_type==5)
    # timestamp: server-side PTS in milliseconds
    # is_encrypted: True when sub-frame encryption byte was non-zero
    # data: raw H.264 NAL bytes (video) or G.711A bytes (audio)
    frame_type:   int
    audio_codec:  int
    timestamp:    int
    is_encrypted: bool
    data:         bytes

    @property
    def is_video(self) -> bool:
        return self.frame_type in (_FRAME_TYPE_P_FRAME,
                                   _FRAME_TYPE_B_FRAME,
                                   _FRAME_TYPE_I_FRAME)

    @property
    def is_keyframe(self) -> bool:
        return self.frame_type == _FRAME_TYPE_I_FRAME

    @property
    def is_audio(self) -> bool:
        return self.frame_type == _FRAME_TYPE_AUDIO
