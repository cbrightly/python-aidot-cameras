"""Camera-aware extension of upstream's per-device LAN client.

Upstream (`aidot.device_client`) owns the light/TCP protocol; nothing in it is
edited.  This module layers the camera surface on top:

* `DeviceStatusData` / `DeviceInformation` - thin subclasses of upstream's, kept
  here because `aidot_cameras.camera.models` builds `CameraStatusData` /
  `CameraDeviceInformation` on them and because the pre-inversion module exposed
  them under these names.  `DeviceInformation` additionally accepts the RAW cloud
  device dict, which upstream's typed constructor does not.
* `CameraDeviceClient` - `CameraMixin` + upstream `DeviceClient`.  Only the five
  seams the camera layer actually needs are overridden; no upstream method body
  is copied.

Import order matters: the two data classes must be defined BEFORE
`.camera.client` is imported, because `aidot_cameras.camera.models` imports them
back from this module at import time.
"""

import logging
from typing import Any, Optional

from aidot.device_client import DeviceClient as _UpstreamDeviceClient
from aidot.device_client import DeviceInformation as _UpstreamDeviceInformation
from aidot.device_client import DeviceStatusData as _UpstreamDeviceStatusData
from aidot.models.auth_model import UserInformation
from aidot.models.device_model import DeviceModel

from .const import (
    CONF_ATTR,
    CONF_HARDWARE_VERSION,
    CONF_ID,
    CONF_IDENTITY,
    CONF_MAC,
    CONF_MAXVALUE,
    CONF_MINVALUE,
    CONF_MODEL_ID,
    CONF_NAME,
    CONF_PASSWORD,
    CONF_PAYLOAD,
    CONF_PRODUCT,
    CONF_PROPERTIES,
    CONF_SERVICE_MODULES,
    Identity,
)

_LOGGER = logging.getLogger(__name__)

# Upstream's base client, re-exported under its plain name.  `get_device_client`
# hands one of these back for every non-camera device, so consumers (the Home
# Assistant integration) need the type to annotate against - and should get it
# from here rather than importing `aidot` directly, which for them is an
# undeclared transitive dependency.  This is upstream's class, not a subclass.
DeviceClient = _UpstreamDeviceClient


class DeviceStatusData(_UpstreamDeviceStatusData):
    """Upstream status plus the carried `active_color_mode` tracking."""

    # CARRIED: drop when python-aidot#6 merges.
    active_color_mode: "str | None" = None

    def update(self, attr) -> None:
        """Apply a typed `DeviceAttr` and track which color mode it implies."""
        if attr is None:
            return
        super().update(attr)

        # CARRIED: drop when python-aidot#6 merges.
        # Bulbs report state as deltas: a CCT-mode push carries only CCT, an
        # RGB-mode push carries only RGBW.  The getDevAttr login-sync instead
        # returns BOTH the retained RGBW register and CCT together, which is
        # ambiguous about which mode is active; that case (and RGBW == 0, which
        # is just the register's power-on default rather than a real color pick)
        # must update the values without changing active_color_mode.
        rgbw = getattr(attr, "RGBW", None)
        cct = getattr(attr, "CCT", None)
        if rgbw is not None and cct is None and rgbw != 0:
            self.active_color_mode = "rgbw"
        elif cct is not None and rgbw is None:
            self.active_color_mode = "cct"


class DeviceInformation(_UpstreamDeviceInformation):
    """Upstream device info that also accepts the raw cloud device dict.

    Upstream's constructor reads a typed `DeviceModel` by attribute.  The camera
    layer works from the raw device record (its camera fields have no place in
    `DeviceModel`), and `CameraDeviceInformation.__init__` forwards that dict
    straight to `super().__init__`, so the dict form must be understood here.
    """

    def __init__(self, device: Any) -> None:
        """Initialize from either a typed `DeviceModel` or a raw device dict."""
        if not isinstance(device, dict):
            super().__init__(device)
            return

        self.dev_id = device.get(CONF_ID)
        self.mac = device.get(CONF_MAC) if device.get(CONF_MAC) is not None else ""
        self.model_id = device.get(CONF_MODEL_ID)
        self.name = device.get(CONF_NAME)
        self.hw_version = device.get(CONF_HARDWARE_VERSION)
        self.password = device.get(CONF_PASSWORD) or ""
        self.simpleVersion = device.get("simpleVersion")
        if CONF_PRODUCT in device and CONF_SERVICE_MODULES in (device[CONF_PRODUCT] or {}):
            for service in device[CONF_PRODUCT][CONF_SERVICE_MODULES]:
                if service[CONF_IDENTITY] == Identity.RGBW:
                    self.enable_rgbw = True
                    self.enable_cct = True
                elif service[CONF_IDENTITY] == Identity.CCT:
                    self.cct_min = int(service[CONF_PROPERTIES][0][CONF_MINVALUE])
                    self.cct_max = int(service[CONF_PROPERTIES][0][CONF_MAXVALUE])
                    self.enable_cct = True


# --------------------------------------------------------------------------- #
# Camera surface (additive layer)
# --------------------------------------------------------------------------- #
# All camera/streaming code lives in aidot_cameras.camera.client and attaches via
# CameraMixin.  This import must come AFTER DeviceStatusData / DeviceInformation
# (camera.models subclasses them at import time) and BEFORE CameraDeviceClient.
# The names below are re-exported for back-compat: the public API and the test
# suite import them from this module.
from .camera.client import (
    CameraMixin,
    WebRTCSession,
    SdesSession,
    TALK_PCM_FRAME_BYTES,
    TALK_PCM_RATE,
    _CAMERA_ALARM_TYPES,
    _WEBRTC_TERMINAL_ACK_CODES,
    _build_sprop,
    _extract_param_sets_from_rtp,
    _highport_nomination_decision,
    _idle_release_due,
    _install_highport_nomination_patch,
    _load_sprop,
    _make_talk_audio_track,
    _parse_alarm_event,
    _save_sprop,
    _sdes_serve_port,
    _tcp_table_has_established_on_port,
    _terminal_webrtc_ack,
)


class CameraDeviceClient(CameraMixin, _UpstreamDeviceClient):
    """Upstream device client with the camera surface mixed in.

    `CameraMixin` comes first in the MRO so camera behavior wins, but the mixin
    defines none of the upstream connection methods, so every `super()` call
    below lands on `aidot.device_client.DeviceClient`.
    """

    # Raw JSON of the frame most recently read off the wire, consumed once by
    # _notify_status_update().  Class-level so the attribute exists before
    # __init__ completes and after every consume.
    _last_raw_payload: Optional[dict] = None

    def __init__(
        self,
        device: DeviceModel,
        user_info: UserInformation,
        raw_device: Optional[dict] = None,
        login_info: Optional[dict] = None,
    ) -> None:
        """Build the upstream client, then initialize camera state.

        `raw_device` / `login_info` are the unparsed cloud records.  Prefer them:
        `DeviceModel` and `UserInformation` are closed dataclasses, so round-
        tripping through `to_dict()` silently drops every camera field (the whole
        of `properties`: enableSdes, isDTLS, Battery_remaining, Occupancy,
        SDcardStatus, MotionDetection_*, ...) and detaches `login_info` from the
        account-shared dict the camera layer mutates in place (access-token
        refresh, persistent-MQTT cache).  The `to_dict()` fallback keeps the
        two-argument upstream signature usable for light-only devices.
        """
        # Real cameras report `aesKey: [None]` - a truthy list holding None.
        # Upstream guards only the list ("if self._device.aesKey:") and then
        # calls .encode() on the element, so that shape raises AttributeError
        # for every camera.  Normalize to None so upstream skips the block; the
        # pre-inversion client guarded the same case ("if key_string is not
        # None").  Harmless for devices that carry a real key.
        _aes_key = getattr(device, "aesKey", None)
        if _aes_key and _aes_key[0] is None:
            device.aesKey = None

        super().__init__(device, user_info)

        # Upstream's typed models stay reachable; _init_camera_state overwrites
        # self._user_info with the raw dict the camera layer expects.
        self._device_model = device
        self._user_info_model = user_info

        raw_dev = raw_device if isinstance(raw_device, dict) else device.to_dict()
        raw_user = login_info if isinstance(login_info, dict) else user_info.to_dict()

        # Pre-inversion attribute names the camera layer reads throughout, and
        # which _init_camera_state itself depends on (self.device_id).
        self.device_id = self.info.dev_id or raw_dev.get(CONF_ID)
        self.user_id = getattr(user_info, "id", None) or raw_user.get(CONF_ID)

        self._init_camera_state(raw_dev, raw_user)

    async def async_login(self) -> None:
        """Log in over TCP:10000, except for cameras, which do not serve it.

        The base control channel is the LIGHT protocol.  Cameras' local control
        is the separate CameraLanClient (camera/lan_control.py) and their LAN IP
        comes from WebRTC signaling.  A camera reaching here means a discovered
        IP slipped the camera gate; connecting would hammer a closed port and
        never succeed.  This is the single chokepoint for both the discovery and
        the reconnect-chain login paths.
        """
        model = getattr(getattr(self, "info", None), "model_id", "") or ""
        if "IPC" in model:
            return
        await super().async_login()

    async def close(self) -> None:
        """Stop any streaming, then close the connection permanently."""
        try:
            await self.async_stop_streaming()
        except Exception:
            _LOGGER.debug(
                "%s: stop streaming during close failed", self._TAG, exc_info=True
            )
        finally:
            await super().close()

    async def read_data(self) -> dict[str, Any]:
        """Read one frame, keeping the raw JSON for the camera attribute pass."""
        data = await super().read_data()
        self._last_raw_payload = data if isinstance(data, dict) else None
        return data

    def _notify_status_update(self) -> None:
        """Apply camera-only attribute keys, then notify as upstream does.

        Upstream feeds status from the typed `DeviceAttr` model, which has no
        field for a camera/floodlight key and therefore drops it.  Re-applying
        the raw `payload.attr` dict here recovers those keys without copying
        upstream's receive loop.  The stash is consumed (not just read) so a
        notify that is not driven by a frame - reset(), login - cannot re-apply
        a stale attribute set.
        """
        raw = self._last_raw_payload
        self._last_raw_payload = None
        if isinstance(raw, dict):
            payload = raw.get(CONF_PAYLOAD)
            raw_attr = payload.get(CONF_ATTR) if isinstance(payload, dict) else None
            if isinstance(raw_attr, dict):
                self.status.update(raw_attr)
        super()._notify_status_update()
