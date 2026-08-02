"""Control for BLE-mesh devices that sit behind an AiDot mesh hub.

A BLE-mesh bulb has no IP of its own: it is reached by talking to its **hub**
(``type == "BleMesh_Hub"``) over the hub's local TCP:10000 control channel and
naming the child in the payload.  The wire format is the one
:mod:`aidot_cameras.camera.lan_control` already speaks - 8-byte header
(``magic 0x1EED`` + ``msgtype:int16`` + ``bodysize:int32``) over an AES-ECB JSON
body - with three differences:

* the socket, ``aesKey`` and ``password`` are the **hub's**, not the child's;
* ``payload.parentId`` is the hub id while ``payload.devId`` / ``deviceId`` name
  the child;
* ``payload.channel`` is ``"ble"`` rather than ``"tcp"``, which is what tells the
  hub to relay the command onto the mesh.

**Session policy.**  The camera client opens a short-lived session per command
because that channel evicts an existing login on the next ``loginReq``.  A hub
multiplexes every bulb behind it, so paying connect+login per command shows up
directly as slider lag.  This module keeps one lock-serialised connection per hub
and closes it after :data:`_IDLE_CLOSE_S` of silence - bursts (a brightness drag)
reuse one login, and an idle hub is left free for the app rather than being held
indefinitely.

Nothing here is Home Assistant specific: it is the hub-relay transport plus the
cloud-record predicates needed to recognise which devices use it.
"""

from __future__ import annotations

import asyncio
import ctypes
import json
import logging
import time
from dataclasses import dataclass, field
from datetime import datetime
from typing import Any, Optional

# Single source of truth for the local control wire format - the hub relay is the
# same framing as the direct-to-device channel, so it reuses those helpers rather
# than restating the header layout (and its frame-size cap) a second time.
from .camera.lan_control import _pack, _read_frame
from .crypto import aes_decrypt, aes_encrypt

_LOGGER = logging.getLogger(__name__)

_CONTROL_PORT = 10000
_CONNECT_TIMEOUT = 8.0
_READ_TIMEOUT = 8.0
_MAX_RETRIES = 3
_BACKOFF_S = 0.5
# How long a hub connection is kept warm after the last command.  Long enough to
# coalesce a slider drag, short enough that an idle hub is not held against the
# phone app.
_IDLE_CLOSE_S = 5.0

BLE_MESH_HUB_TYPE = "BleMesh_Hub"


class BleMeshError(Exception):
    """A hub-relayed control operation failed (connect, login, or protocol)."""


# --------------------------------------------------------------------------- #
# Cloud-record predicates
# --------------------------------------------------------------------------- #


def find_ble_mesh_hubs(devices: list[dict[str, Any]]) -> dict[str, dict[str, Any]]:
    """Return ``{hub_id: device}`` for every BLE-mesh hub in a cloud device list."""
    return {
        d["id"]: d
        for d in devices
        if d.get("type") == BLE_MESH_HUB_TYPE and d.get("id")
    }


def hub_id_of(device: dict[str, Any]) -> str:
    """Return the id of the hub a device sits behind, or ``""`` if it is direct.

    The cloud record is not consistent about which field carries this:
    ``directGateway`` is the documented one, but ``parentId`` is populated on some
    firmware.  A device that is not proxied reports both as ``""`` and sets
    ``directId`` to its own id, so an empty result here means "talks for itself".
    """
    for key in ("directGateway", "parentId"):
        value = device.get(key)
        if value and value != device.get("id"):
            return str(value)
    return ""


def is_ble_mesh_child(
    device: dict[str, Any], hubs: dict[str, dict[str, Any]]
) -> bool:
    """True when ``device`` must be driven through one of ``hubs``.

    Requires both that the record points at a known hub **and** that it carries
    mesh addressing (``bleMeshDeviceKey`` / ``bleMeshAddr``).  The corroboration
    matters: a hub id alone also appears on Zigbee children and on records that
    merely share a house, and building a mesh client for one of those produces a
    device that accepts commands and silently controls nothing.
    """
    if hub_id_of(device) not in hubs:
        return False
    return bool(device.get("bleMeshDeviceKey")) or bool(device.get("bleMeshAddr"))


# --------------------------------------------------------------------------- #
# Device state
# --------------------------------------------------------------------------- #


@dataclass
class BleMeshDeviceInfo:
    """Static description of a mesh device, derived from its cloud record."""

    dev_id: str
    hub_id: str
    model_id: str
    mac: str
    name: str
    hw_version: Optional[str] = None
    enable_rgbw: bool = False
    enable_cct: bool = False
    enable_dimming: bool = False
    cct_min: int = 2700
    cct_max: int = 6500


@dataclass
class BleMeshDeviceStatus:
    """Last-known state of a mesh device.

    Mesh children do not push status - the hub acks a command and says nothing
    afterwards - so these values are seeded from the cloud record and then
    advanced optimistically by each accepted command.
    """

    online: bool = True
    on: bool = False
    dimming: int = 255
    cct: int = 2700
    rgbw: tuple[int, int, int, int] = field(default_factory=lambda: (255, 255, 255, 0))


def _unpack_rgbw(raw: Any) -> tuple[int, int, int, int]:
    """Split a packed RGBW int into components.

    The cloud sends this as a *signed* 32-bit value, so any colour with the red
    channel above 0x7F arrives negative; masking through ``c_uint32`` first is
    what keeps those from shifting into nonsense.
    """
    packed = ctypes.c_uint32(int(raw or 0)).value
    return (
        (packed >> 24) & 0xFF,
        (packed >> 16) & 0xFF,
        (packed >> 8) & 0xFF,
        packed & 0xFF,
    )


def _pack_rgbw(rgbw: tuple[int, int, int, int]) -> int:
    """Pack RGBW components into the signed 32-bit int the wire format expects."""
    packed = (
        ((rgbw[0] & 0xFF) << 24)
        | ((rgbw[1] & 0xFF) << 16)
        | ((rgbw[2] & 0xFF) << 8)
        | (rgbw[3] & 0xFF)
    )
    return ctypes.c_int32(packed).value


def _service_modules(device: dict[str, Any]) -> list[dict[str, Any]]:
    return (device.get("product") or {}).get("serviceModules") or []


def _cct_range(device: dict[str, Any]) -> tuple[int, int]:
    """Read the device's CCT limits, falling back to the common 2700-6500 K."""
    cct_min, cct_max = 2700, 6500
    for module in _service_modules(device):
        if (module.get("identity") or "").lower() != "control.light.cct":
            continue
        for prop in module.get("properties") or []:
            if (prop.get("identity") or "").upper() != "CCT":
                continue
            try:
                cct_min = int(prop.get("minValue", cct_min))
                cct_max = int(prop.get("maxValue", cct_max))
            except (TypeError, ValueError):
                pass
    return cct_min, cct_max


# --------------------------------------------------------------------------- #
# Hub session
# --------------------------------------------------------------------------- #


class BleMeshHubSession:
    """One lock-serialised, idle-closed connection to a mesh hub.

    Every child behind a hub shares this object: the hub resets the socket when a
    second login lands, so commands must be serialised rather than merely
    connection-pooled.  A dropped connection is retried, which also covers the
    case where the hub closed an idle socket before our own timer fired.
    """

    def __init__(
        self,
        hub_id: str,
        hub_ip: str,
        aes_key: bytes,
        user_id: str,
        hub_password: str,
        idle_close_s: float = _IDLE_CLOSE_S,
    ) -> None:
        self.hub_id = hub_id
        self.hub_ip = hub_ip
        self._key = aes_key
        self._user_id = user_id
        self._password = hub_password
        self._idle_close_s = idle_close_s
        self._lock = asyncio.Lock()
        self._reader: Optional[asyncio.StreamReader] = None
        self._writer: Optional[asyncio.StreamWriter] = None
        self._asc = 1
        self._idle_task: Optional[asyncio.Task] = None

    async def async_send_attributes(self, dev_id: str, attr: dict[str, Any]) -> bool:
        """Relay one ``setDevAttrReq`` to ``dev_id``; True on a ``setDevAttrResp``."""
        async with self._lock:
            self._cancel_idle_timer()
            try:
                for attempt in range(_MAX_RETRIES):
                    try:
                        await self._ensure_connected()
                        return await self._set_attr(dev_id, attr)
                    except (
                        OSError,
                        TimeoutError,
                        asyncio.IncompleteReadError,
                    ) as exc:
                        await self._close()
                        if attempt == _MAX_RETRIES - 1:
                            raise BleMeshError(
                                f"{self.hub_id}: relay to {dev_id} failed: {exc}"
                            ) from exc
                        await asyncio.sleep(_BACKOFF_S * (attempt + 1))
                raise BleMeshError(f"{self.hub_id}: relay to {dev_id} exhausted retries")
            finally:
                self._start_idle_timer()

    # -- connection lifecycle ---------------------------------------------- #

    async def _ensure_connected(self) -> None:
        if self._writer is None or self._writer.is_closing():
            await self._connect_and_login()

    async def _connect_and_login(self) -> None:
        self._reader, self._writer = await asyncio.wait_for(
            asyncio.open_connection(self.hub_ip, _CONTROL_PORT),
            timeout=_CONNECT_TIMEOUT,
        )
        msg = {
            "service": "device",
            "method": "loginReq",
            "seq": str(int(time.time() * 1000))[-9:],
            "srcAddr": self._user_id,
            "deviceId": self.hub_id,
            "payload": {
                "userId": self._user_id,
                "password": self._password,
                "timestamp": datetime.now().strftime("%Y-%m-%d %H:%M:%S.000"),
                "ascNumber": 1,
            },
        }
        self._writer.write(_pack(1, aes_encrypt(json.dumps(msg).encode(), self._key)))
        await self._writer.drain()
        try:
            resp = json.loads(
                aes_decrypt(await _read_frame(self._reader, _READ_TIMEOUT), self._key)
            )
        except Exception as exc:
            await self._close()
            # A host that cannot answer with something our hub key decrypts is not
            # the hub. Surfacing that distinctly matters because the hub's IP comes
            # from an unauthenticated cloud "properties" field that can go stale
            # onto whatever else now holds the address.
            raise BleMeshError(
                f"{self.hub_id}: login response undecryptable (wrong host?)"
            ) from exc
        ack = (resp.get("ack") or {}).get("code")
        if ack not in (None, 200):
            await self._close()
            raise BleMeshError(f"{self.hub_id}: login rejected ack={ack}")
        self._asc = (resp.get("payload") or {}).get("ascNumber", 1) + 1

    async def _set_attr(self, dev_id: str, attr: dict[str, Any]) -> bool:
        assert self._writer is not None and self._reader is not None
        msg = {
            "method": "setDevAttrReq",
            "service": "device",
            "clientId": "ha-" + self._user_id,
            "srcAddr": "0." + self._user_id,
            "seq": "b" + str(int(time.time() * 1000))[-9:],
            "payload": {
                "devId": dev_id,
                "parentId": self.hub_id,
                "userId": self._user_id,
                "password": self._password,
                "attr": attr,
                "channel": "ble",
                "ascNumber": self._asc,
            },
            "tst": int(time.time() * 1000),
            "deviceId": dev_id,
        }
        self._asc += 1
        self._writer.write(_pack(1, aes_encrypt(json.dumps(msg).encode(), self._key)))
        await self._writer.drain()
        # The hub may emit an unrelated frame (a status echo for another child on
        # the mesh) before our ack, so read until the response method arrives.
        for _ in range(4):
            frame = json.loads(
                aes_decrypt(await _read_frame(self._reader, _READ_TIMEOUT), self._key)
            )
            method = str(frame.get("method") or "")
            if method == "setDevAttrResp":
                return True
            if method.endswith("Resp"):
                return False
        return False

    def _start_idle_timer(self) -> None:
        if self._writer is None or self._idle_close_s <= 0:
            return
        loop = asyncio.get_running_loop()
        self._idle_task = loop.create_task(self._idle_close())

    def _cancel_idle_timer(self) -> None:
        task = self._idle_task
        self._idle_task = None
        if task is not None and not task.done():
            task.cancel()

    async def _idle_close(self) -> None:
        try:
            await asyncio.sleep(self._idle_close_s)
        except asyncio.CancelledError:
            return
        # Take the lock so this can never close a socket mid-command.
        async with self._lock:
            if self._idle_task is not None:
                await self._close()

    async def async_close(self) -> None:
        """Close the hub connection and stop the idle timer."""
        self._cancel_idle_timer()
        async with self._lock:
            await self._close()

    async def _close(self) -> None:
        writer, self._writer, self._reader = self._writer, None, None
        if writer is None:
            return
        try:
            writer.close()
            await writer.wait_closed()
        except Exception:  # teardown is best-effort
            pass


_HUB_SESSIONS: dict[str, BleMeshHubSession] = {}


def get_hub_session(
    hub_id: str, hub_ip: str, aes_key: bytes, user_id: str, hub_password: str
) -> BleMeshHubSession:
    """Return the process-wide session for a hub, creating it on first use.

    Keyed by hub id; a changed IP (DHCP move) replaces the session rather than
    reusing one pointed at the old address.
    """
    session = _HUB_SESSIONS.get(hub_id)
    if session is None or session.hub_ip != hub_ip:
        session = BleMeshHubSession(hub_id, hub_ip, aes_key, user_id, hub_password)
        _HUB_SESSIONS[hub_id] = session
    return session


async def close_all_hub_sessions() -> None:
    """Close every open hub connection and clear the registry (call on unload)."""
    sessions = list(_HUB_SESSIONS.values())
    _HUB_SESSIONS.clear()
    for session in sessions:
        await session.async_close()


# --------------------------------------------------------------------------- #
# Per-device client
# --------------------------------------------------------------------------- #


class BleMeshGatewayClient:
    """Drives one BLE-mesh light by relaying commands through its hub.

    Shaped to match the attribute surface the device clients expose (``info``,
    ``status``, ``on_status_update``) so a consumer can treat it as one more
    client flavour rather than a special case.
    """

    def __init__(
        self,
        device: dict[str, Any],
        hub_device: dict[str, Any],
        user_id: str,
    ) -> None:
        self.device_id: str = device.get("id") or ""
        if not self.device_id:
            raise BleMeshError("mesh device record has no id")

        self._hub_id: str = hub_device.get("id") or ""
        self._hub_ip: str = (hub_device.get("properties") or {}).get("ipAddress") or ""
        if not self._hub_id:
            raise BleMeshError(f"{self.device_id}: hub record has no id")
        if not self._hub_ip:
            raise BleMeshError(f"{self.device_id}: hub {self._hub_id} has no ipAddress")

        aes = hub_device.get("aesKey") or []
        key_str = aes[0] if isinstance(aes, list) and aes else (aes or "")
        if not key_str:
            raise BleMeshError(f"{self.device_id}: hub {self._hub_id} has no aesKey")
        key = bytearray(16)
        raw = str(key_str).encode()
        key[: len(raw)] = raw[:16]
        self._key = bytes(key)
        self._hub_password: str = hub_device.get("password") or ""
        self._user_id = user_id

        modules = {
            (m.get("identity") or "").lower() for m in _service_modules(device)
        }
        cct_min, cct_max = _cct_range(device)
        self.info = BleMeshDeviceInfo(
            dev_id=self.device_id,
            hub_id=self._hub_id,
            model_id=device.get("modelId") or "unknown",
            mac=device.get("mac") or "",
            name=device.get("name") or "",
            hw_version=device.get("hardwareVersion"),
            enable_rgbw="control.light.rgbw" in modules,
            enable_cct="control.light.cct" in modules,
            enable_dimming="control.light.dimming" in modules,
            cct_min=cct_min,
            cct_max=cct_max,
        )

        props = device.get("properties") or {}
        self.status = BleMeshDeviceStatus(
            online=bool(device.get("online", True)),
            on=str(props.get("OnOff", "0")) == "1",
            # The cloud reports Dimming as 0-100; the client surface is 0-255.
            dimming=_pct_to_255(props.get("Dimming", 100)),
            cct=_as_int(props.get("CCT"), cct_min),
            rgbw=_unpack_rgbw(props.get("RGBW", 0)),
        )
        self.on_status_update: Any = None

    # -- commands ----------------------------------------------------------- #

    async def async_set_attributes(self, attr: dict[str, Any]) -> bool:
        """Relay ``attr`` (on-the-wire keys) and advance the optimistic status."""
        session = get_hub_session(
            self._hub_id, self._hub_ip, self._key, self._user_id, self._hub_password
        )
        acked = await session.async_send_attributes(self.device_id, attr)
        if not acked:
            _LOGGER.debug(
                "mesh device %s: hub %s did not ack %s",
                self.device_id,
                self._hub_id,
                sorted(attr),
            )
            return False
        self._apply_optimistic(attr)
        return True

    def _apply_optimistic(self, attr: dict[str, Any]) -> None:
        """Fold an acked command into ``status``.

        Mesh children never report back, so this is the only thing that moves
        state between cloud refreshes.  It runs *only* after an ack, so a command
        the hub dropped does not leave a state the device never reached.
        """
        if "OnOff" in attr:
            self.status.on = bool(attr["OnOff"])
        if "Dimming" in attr:
            self.status.dimming = _pct_to_255(attr["Dimming"])
        if "CCT" in attr:
            self.status.cct = _as_int(attr["CCT"], self.status.cct)
        if "RGBW" in attr:
            self.status.rgbw = _unpack_rgbw(attr["RGBW"])
        if self.on_status_update:
            self.on_status_update(self.status)

    async def async_turn_on(self) -> bool:
        return await self.async_set_attributes({"OnOff": 1})

    async def async_turn_off(self) -> bool:
        return await self.async_set_attributes({"OnOff": 0})

    async def async_set_brightness(self, brightness: int) -> bool:
        """Set brightness from a 0-255 value (the wire field is 0-100)."""
        return await self.async_set_attributes(
            {"OnOff": 1, "Dimming": _255_to_pct(brightness)}
        )

    async def async_set_cct(self, cct: int) -> bool:
        clamped = max(self.info.cct_min, min(self.info.cct_max, int(cct)))
        return await self.async_set_attributes({"OnOff": 1, "CCT": clamped})

    async def async_set_rgbw(self, rgbw: tuple[int, int, int, int]) -> bool:
        return await self.async_set_attributes(
            {"OnOff": 1, "RGBW": _pack_rgbw(rgbw)}
        )

    def update_status_from_device(self, device: dict[str, Any]) -> None:
        """Re-seed status from a fresh cloud record (the only inbound signal)."""
        props = device.get("properties") or {}
        self.status.online = bool(device.get("online", self.status.online))
        if "OnOff" in props:
            self.status.on = str(props["OnOff"]) == "1"
        if "Dimming" in props:
            self.status.dimming = _pct_to_255(props["Dimming"])
        if "CCT" in props:
            self.status.cct = _as_int(props["CCT"], self.status.cct)
        if "RGBW" in props:
            self.status.rgbw = _unpack_rgbw(props["RGBW"])
        if self.on_status_update:
            self.on_status_update(self.status)


def _as_int(value: Any, default: int) -> int:
    try:
        return int(value)
    except (TypeError, ValueError):
        return default


def _pct_to_255(value: Any) -> int:
    """0-100 wire percentage -> 0-255, clamped."""
    pct = max(0, min(100, _as_int(value, 100)))
    return round(pct * 255 / 100)


def _255_to_pct(value: Any) -> int:
    """0-255 -> 0-100 wire percentage, clamped to a minimum of 1.

    Rounding 1-2/255 down to 0% would send "off" on a brightness command, which
    reads as the light ignoring the slider.
    """
    raw = max(0, min(255, _as_int(value, 255)))
    return max(1, round(raw * 100 / 255))
