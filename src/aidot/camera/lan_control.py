"""Offline LAN device-control for AiDot cameras.

This is the **control plane only** - settings, toggles, PTZ-adjacent attributes and
status - over the camera's local TCP:10000 channel.  It does NOT do video; video is
WebRTC (``liveType=2``) and stays cloud-MQTT-signaled.

Design constraints (validated against LK.IPC.A000088 firmware):

* The control channel speaks the lights' local protocol: an 8-byte header
  (``magic 0x1EED`` + ``msgtype:int16`` + ``bodysize:int32``) followed by an
  AES-ECB-encrypted JSON body, keyed by the device's 16-char ``aesKey``.
* It is **single-session**: a second ``loginReq`` evicts the first.  So this client
  never holds a socket open - every operation is a short-lived
  *connect → login → command(s) → close*, serialized by a per-camera lock.
* The camera acks changes with ``setDevAttrResp`` but emits no ``setDevAttrNotif``,
  so there is no push; status is obtained by polling :meth:`async_get_attributes`.
* Only cameras that advertise ``localCtrFlag == 1`` on unicast discovery AND are
  mains-powered are eligible.  Battery models sleep and do not answer unicast.

Credentials (``aesKey`` + ``password``) come from the cloud device dict once and are
stable per device; cache them and this client runs with no cloud contact.
"""

from __future__ import annotations

import asyncio
import json
import logging
import socket
import struct
import time
from datetime import datetime
from typing import Any, Optional

from ..aes_utils import aes_encrypt, aes_decrypt

_LOGGER = logging.getLogger(__name__)

_CONTROL_PORT = 10000
_DISCOVER_PORT = 6666
_MAGIC = 0x1EED
# Well-known AES key for the unprovisioned discovery exchange (lights + cameras),
# zero-padded to 32 bytes.  Matches aidot.discover.BroadcastProtocol.
_DISCOVER_KEY = bytearray(b"T54uednca587".ljust(32, b"\x00"))

# Camera attribute keys that are safe, mains-relevant device-control toggles.
# (Maps a friendly name -> on-the-wire attr key.)
ATTR_KEYS = {
    "status_led": "LedOnOff",
    "motion_detection": "MotionDetection_Enable",
    "motion_sensitivity": "MotionDetection_Sen",
    "night_vision_mode": "nightVisionMode",
    "ir_light": "nightVisionIRLight",
    "microphone": "micEnable",
    "speaker_volume": "SoundLevel",
    "ptz_tracking": "trackingMode",
    "floodlight": "LightOnOff",
    "siren": "sirenRing",
}


# Control-channel bodies are small JSON; reject anything wildly larger than that
# so a hostile/broken LAN peer can't drive memory exhaustion via the size field.
_MAX_FRAME_BODY = 1024 * 1024  # 1 MiB


def _pack(msgtype: int, body: bytes) -> bytes:
    return struct.pack(">Hhi", _MAGIC, msgtype, len(body)) + body


async def _read_frame(reader: asyncio.StreamReader, timeout: float) -> bytes:
    """Read one 8-byte-header frame; return the raw (still-encrypted) body bytes.

    The caller decrypts and parses the JSON (the AES key lives on the client)."""
    header = await asyncio.wait_for(reader.readexactly(8), timeout)
    _magic, _mtype, bodysize = struct.unpack(">HHI", header)
    # Cap the server-supplied body size: `bodysize` is an unsigned 32-bit field
    # (up to ~4 GiB) from a LAN host, and control-channel bodies are small JSON.
    # Reject an implausibly large frame instead of allocating on a malicious or
    # malfunctioning peer (mirrors the 4 MiB cap in playback._read_frame).
    if bodysize > _MAX_FRAME_BODY:
        raise ValueError(f"LAN control frame body too large: {bodysize} bytes")
    body = await asyncio.wait_for(reader.readexactly(bodysize), timeout)
    return body  # caller decrypts (key is on the client)


async def discover_unicast(ip: str, timeout: float = 2.0) -> Optional[dict]:
    """Send a unicast ``devDiscoveryReq`` to ``ip`` and return the reply payload.

    Cameras ignore the broadcast sweep but answer a unicast probe.  Returns the
    ``payload`` dict (``devId``, ``mac``, ``productModel``, ``lanMode``,
    ``localCtrFlag`` …) or ``None`` if nothing answered.
    """
    msg = {
        "protocolVer": "2.0.0",
        "service": "device",
        "method": "devDiscoveryReq",
        "seq": str(int(time.time() * 1000))[-9:],
        "srcAddr": "0.lan",
        "tst": int(time.time() * 1000),
        "payload": {"extends": {}, "localCtrFlag": 1,
                    "timestamp": str(int(time.time() * 1000))},
    }
    enc = aes_encrypt(json.dumps(msg).encode(), _DISCOVER_KEY)

    loop = asyncio.get_running_loop()
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    sock.setblocking(False)
    try:
        await loop.sock_sendto(sock, enc, (ip, _DISCOVER_PORT))
        try:
            data = await asyncio.wait_for(loop.sock_recv(sock, 8192), timeout)
        except TimeoutError:
            return None
        try:
            return json.loads(aes_decrypt(data, _DISCOVER_KEY)).get("payload")
        except Exception:
            return None
    finally:
        sock.close()


def _local_ipv4() -> Optional[str]:
    """Best-effort primary IPv4 of this host (no traffic sent)."""
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    try:
        s.connect(("192.0.2.1", 9))  # TEST-NET-1; just selects an interface
        return s.getsockname()[0]
    except OSError:
        return None
    finally:
        s.close()


async def discover_subnet(cidr24: Optional[str] = None, timeout: float = 3.0,
                          concurrency: int = 64) -> dict[str, str]:
    """Unicast-sweep a /24 and return ``{devId: ip}`` for every camera that answers.

    Cameras ignore the broadcast discovery sweep but answer a unicast probe, so the
    only way to map a camera's devId to its LAN IP locally is to probe each host.
    ``cidr24`` is the first three octets (e.g. ``"192.168.1"``); if omitted it is
    derived from this host's primary IPv4.  Cheap (254 UDP probes, ~timeout wall).
    """
    if cidr24 is None:
        ip = _local_ipv4()
        if not ip:
            return {}
        cidr24 = ip.rsplit(".", 1)[0]

    sem = asyncio.Semaphore(concurrency)
    found: dict[str, str] = {}

    async def probe(host: str) -> None:
        async with sem:
            payload = await discover_unicast(host, timeout=timeout)
        if payload and payload.get("devId"):
            found[payload["devId"]] = host

    await asyncio.gather(*(probe(f"{cidr24}.{n}") for n in range(1, 255)))
    return found


class CameraLanError(Exception):
    """A local control operation failed (connect, login, or protocol error)."""


class CameraLanClient:
    """Short-lived local device-control client for one camera.

    Build from the cloud device dict + login info; optionally pass a known LAN ``ip``
    (otherwise call :meth:`async_resolve` to unicast-discover it).  All control calls
    are serialized and each opens/closes its own socket.
    """

    def __init__(self, device: dict[str, Any], user_info: dict[str, Any],
                 ip: Optional[str] = None) -> None:
        self.device_id: str = device.get("id") or device.get("devId")
        _aes = device.get("aesKey") or []
        key_str = _aes[0] if isinstance(_aes, list) and _aes else (_aes or "")
        if not key_str:
            raise CameraLanError(f"{self.device_id}: no aesKey in device dict")
        self._key = bytearray(16)
        kb = key_str.encode()
        self._key[: len(kb)] = kb
        self._password = device.get("password") or ""
        self._user_id = user_info.get("id")
        self._model_id = device.get("modelId") or ""
        self._ip = ip
        self._lock = asyncio.Lock()           # single in-flight session per camera
        self._eligible: Optional[bool] = None  # set by async_resolve

    # -- discovery / eligibility ------------------------------------------- #

    @property
    def ip(self) -> Optional[str]:
        return self._ip

    @property
    def eligible(self) -> Optional[bool]:
        """True/False once resolved, else None.  Eligible = local control advertised
        and the device is mains-powered (battery models excluded)."""
        return self._eligible

    async def async_resolve(self, ip: Optional[str] = None) -> bool:
        """Unicast-discover the camera (at ``ip`` or the configured ip) and gate
        eligibility on ``localCtrFlag``.  Returns True if locally controllable."""
        ip = ip or self._ip
        if ip is None:
            self._eligible = False
            return False
        payload = await discover_unicast(ip)
        if not payload or payload.get("devId") != self.device_id:
            self._eligible = False
            return False
        self._ip = ip
        # localCtrFlag==1 / lanMode==1 advertise local control.  Battery gating is
        # confirmed separately on the first getDevAttr (battery_remaining is None).
        self._eligible = bool(payload.get("localCtrFlag")) or bool(payload.get("lanMode"))
        return self._eligible

    # -- low-level session ------------------------------------------------- #

    async def _session(self, messages):
        """Open one short-lived session, send each built message, collect replies.

        ``messages`` is a callable ``(user_id, ascNumber) -> list[(msgtype, dict)]``
        built after login so it can use the camera's ascNumber.  Returns the list of
        decoded reply dicts (excluding the login response).
        """
        if self._ip is None:
            raise CameraLanError(f"{self.device_id}: no LAN ip (call async_resolve)")
        async with self._lock:
            try:
                reader, writer = await asyncio.wait_for(
                    asyncio.open_connection(self._ip, _CONTROL_PORT), timeout=5.0
                )
            except (TimeoutError, OSError) as exc:
                raise CameraLanError(f"{self.device_id}: connect failed: {exc}") from exc
            try:
                asc = await self._login(reader, writer)
                replies = []
                for msgtype, body_obj in messages(self._user_id, asc):
                    writer.write(_pack(msgtype, aes_encrypt(
                        json.dumps(body_obj).encode(), self._key)))
                    await writer.drain()
                    # The camera may emit an intermediate frame before the
                    # ``*Resp`` (e.g. a status echo before ``setDevAttrResp``), so
                    # drain until a response method arrives or the read times out.
                    for _ in range(4):
                        try:
                            raw = await _read_frame(reader, timeout=4.0)
                        except (TimeoutError, asyncio.IncompleteReadError):
                            break
                        frame = json.loads(aes_decrypt(raw, self._key))
                        replies.append(frame)
                        if str(frame.get("method", "")).endswith("Resp"):
                            break
                return replies
            finally:
                writer.close()
                try:
                    await writer.wait_closed()
                except Exception:
                    pass

    async def _login(self, reader, writer) -> int:
        seq = str(int(time.time() * 1000))[-9:]
        msg = {
            "service": "device", "method": "loginReq", "seq": seq,
            "srcAddr": self._user_id, "deviceId": self.device_id,
            "payload": {"userId": self._user_id, "password": self._password,
                        "timestamp": datetime.now().strftime("%Y-%m-%d %H:%M:%S.000"),
                        "ascNumber": 1},
        }
        writer.write(_pack(1, aes_encrypt(json.dumps(msg).encode(), self._key)))
        await writer.drain()
        try:
            resp = json.loads(
                aes_decrypt(await _read_frame(reader, timeout=8.0), self._key)
            )
            ack = (resp.get("ack") or {}).get("code")
        except Exception as exc:
            # A host that can't produce a response we can decrypt with the
            # device's real AES key is not the camera (e.g. a LAN peer that
            # spoofed this devId in discovery - discovery is unauthenticated).
            # Mark ineligible so control falls back to the cloud instead of
            # repeatedly targeting a bogus/broken host.
            self._eligible = False
            raise CameraLanError(
                f"{self.device_id}: login response undecryptable (wrong host?)"
            ) from exc
        if ack != 200:
            # The real device rejected our key-authenticated login; stop using
            # the LAN path and revert to cloud.
            self._eligible = False
            raise CameraLanError(f"{self.device_id}: login rejected ack={ack}")
        return (resp.get("payload") or {}).get("ascNumber", 1)

    # -- public control ---------------------------------------------------- #

    async def async_get_attributes(self) -> dict:
        """Poll the full device attribute set over a short-lived session."""
        def build(uid, asc):
            return [(1, {"method": "getDevAttrReq", "service": "device",
                         "clientId": "ha-" + uid, "srcAddr": "0." + uid,
                         "seq": "g" + str(int(time.time() * 1000))[-9:],
                         "payload": {"devId": self.device_id, "parentId": self.device_id,
                                     "userId": uid, "password": self._password,
                                     "attr": [], "channel": "tcp", "ascNumber": asc},
                         "tst": int(time.time() * 1000), "deviceId": self.device_id})]
        replies = await self._session(build)
        return (replies[0].get("payload") or {}).get("attr") or {}

    async def async_set_attributes(self, attr: dict) -> bool:
        """Apply ``attr`` (on-the-wire keys, e.g. ``{"LedOnOff": 1}``) locally.

        Returns True on a ``setDevAttrResp`` ack.  Raises CameraLanError on failure.
        """
        def build(uid, asc):
            return [(1, {"method": "setDevAttrReq", "service": "device",
                         "clientId": "ha-" + uid, "srcAddr": "0." + uid,
                         "seq": "s" + str(int(time.time() * 1000))[-9:],
                         "payload": {"devId": self.device_id, "parentId": self.device_id,
                                     "userId": uid, "password": self._password,
                                     "attr": attr, "channel": "tcp", "ascNumber": asc},
                         "tst": int(time.time() * 1000), "deviceId": self.device_id})]
        replies = await self._session(build)
        return any(r.get("method") == "setDevAttrResp" for r in replies)

    async def async_set(self, friendly: str, value: Any) -> bool:
        """Set a single attribute by friendly name (see ``ATTR_KEYS``)."""
        if friendly not in ATTR_KEYS:
            raise CameraLanError(f"unknown control attribute: {friendly}")
        return await self.async_set_attributes({ATTR_KEYS[friendly]: value})

    @staticmethod
    def is_mains_powered(attrs: dict) -> bool:
        """Battery gating: cameras that report no battery level are mains-powered
        (the eligible class).  Call with the dict from :meth:`async_get_attributes`."""
        return attrs.get("Battery_remaining") is None
