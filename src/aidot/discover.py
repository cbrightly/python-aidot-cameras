import re
import socket
import json
import time
import logging
import asyncio
import subprocess
import sys
from typing import Any, List, Tuple

from .aes_utils import aes_encrypt, aes_decrypt
from .const import CONF_ID, CONF_IPADDRESS
from .exceptions import AidotOSError
from .models.discover_model import DiscoverResponse, DiscoverRequest

_LOGGER = logging.getLogger(__name__)
_DISCOVER_TIME = 5      # legacy repeat_broadcast() loop interval (seconds)
_DISCOVER_FAST = 6      # fast discovery cadence right after startup
_DISCOVER_SLOW = 120    # slow maintenance cadence once stable

def _get_broadcast_candidates() -> List[Tuple[str, str]]:
    """Return (bind_ip, broadcast_ip) pairs for every active IPv4 interface.

    Sends a separate broadcast per interface so cameras are reachable
    regardless of which interface the OS default route prefers.
    Falls back to a single ("0.0.0.0", "255.255.255.255") entry if
    interface enumeration is unavailable.
    """
    results: List[Tuple[str, str]] = []
    try:
        if sys.platform == "darwin":
            # macOS ifconfig: "inet 192.168.1.175 netmask 0xffffff00 broadcast 192.168.1.255"
            out = subprocess.check_output(
                ["/sbin/ifconfig"], text=True, stderr=subprocess.DEVNULL
            )
            for m in re.finditer(
                r"\binet\s+"
                r"((?!127\.|169\.254\.)\d+\.\d+\.\d+\.\d+)"
                r"\s+netmask\s+\S+"
                r"\s+broadcast\s+"
                r"(\d+\.\d+\.\d+\.\d+)",
                out,
            ):
                results.append((m.group(1), m.group(2)))
        else:
            # Linux: "inet 192.168.1.x/24 brd 192.168.1.255"
            out = subprocess.check_output(
                ["ip", "addr", "show"], text=True, stderr=subprocess.DEVNULL
            )
            for m in re.finditer(
                r"\binet\s+"
                r"((?!127\.|169\.254\.)\d+\.\d+\.\d+\.\d+)/\d+"
                r"\s+brd\s+"
                r"(\d+\.\d+\.\d+\.\d+)",
                out,
            ):
                results.append((m.group(1), m.group(2)))
    except Exception as exc:
        _LOGGER.debug("_get_broadcast_candidates: interface enumeration failed: %s", exc)

    if not results:
        # Fallback: let the OS pick the outgoing interface
        results = [("0.0.0.0", "255.255.255.255")]

    _LOGGER.debug("_get_broadcast_candidates: %s", results)
    return results


class BroadcastProtocol:
    _is_closed = False

    def __init__(self, callback, user_id, broadcast_addr: str = "255.255.255.255") -> None:
        self.aes_key = bytearray(b"T54uednca587".ljust(32, b'\x00'))
        self._discover_cb = callback
        self.user_id = user_id
        self._broadcast_addr = broadcast_addr

    def connection_made(self, transport) -> None:
        self.transport = transport
        sock = transport.get_extra_info("socket")
        sock.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)

    def send_broadcast(self) -> None:
        if self._is_closed:
            _LOGGER.error("%s: connection is closed", self.user_id)
            return
        try:
            request = DiscoverRequest.from_params(userId=self.user_id)
            message = request.to_dict()
            _LOGGER.debug("send_broadcast %s", message)
            send_data = aes_encrypt(json.dumps(message).encode(), self.aes_key)
            sock = self.transport.get_extra_info("socket")
            local_addr = sock.getsockname() if sock else ("?", 0)
            self.transport.sendto(send_data, (self._broadcast_addr, 6666))
            _LOGGER.debug(
                "discovery broadcast sent: %s:%s -> %s:6666",
                local_addr[0], local_addr[1], self._broadcast_addr,
            )
        except Exception as error:
            _LOGGER.error("%s: send failed: %s", self.user_id, error)

    def datagram_received(self, data, addr) -> None:
        try:
            data_str = aes_decrypt(data, self.aes_key)
            data_json = json.loads(data_str)
        except Exception as exc:
            _LOGGER.debug("discovery: ignored undecodable packet from %s: %s", addr, exc)
            return
        try:
            response = DiscoverResponse.from_json(data=data_json)
            _LOGGER.debug("datagram_received %s", data_json)
            if response.payload and response.payload.devId and self._discover_cb:
                self._discover_cb(response.payload.devId, {CONF_IPADDRESS: addr[0]})
        except Exception as error:
            _LOGGER.error(f"datagram_received error: {error}")

    def error_received(self, exc) -> None:
        _LOGGER.error("%s: error occurred: %s", self.user_id, exc)

    def close(self) -> None:
        try:
            self.transport.close()
        except Exception as error:
            _LOGGER.error("close error: %s", error)

    def connection_lost(self, exc) -> None:
        self._is_closed = True
        if exc:
            _LOGGER.error("%s: connection lost: %s", self.user_id, exc)
        else:
            _LOGGER.info("%s: connection closed", self.user_id)


class Discover:
    _login_info: dict[str, Any] = None
    discovered_device: dict[str, str]
    _timer_handle: asyncio.TimerHandle | None = None

    def __init__(self, login_info, callback):
        self.discovered_device = {}
        self._login_info = login_info
        self._callback = callback
        self._protocols: List[BroadcastProtocol] = []

    async def _ensure_sockets(self) -> None:
        """Create one datagram endpoint per active interface (idempotent)."""
        if self._protocols:
            return

        candidates = _get_broadcast_candidates()
        user_id = self._login_info[CONF_ID]

        for bind_ip, broadcast_ip in candidates:
            protocol = BroadcastProtocol(
                self._discover_callback, user_id, broadcast_addr=broadcast_ip
            )
            try:
                await asyncio.get_running_loop().create_datagram_endpoint(
                    lambda p=protocol: p,
                    local_addr=(bind_ip, 0),
                )
                self._protocols.append(protocol)
                _LOGGER.debug(
                    "discovery socket: bind=%s  broadcast=%s", bind_ip, broadcast_ip
                )
            except OSError as exc:
                _LOGGER.debug(
                    "discovery socket bind %s failed: %s", bind_ip, exc
                )

        if not self._protocols:
            # Last-resort fallback
            protocol = BroadcastProtocol(self._discover_callback, user_id)
            try:
                await asyncio.get_running_loop().create_datagram_endpoint(
                    lambda: protocol,
                    local_addr=("0.0.0.0", 0),
                )
                self._protocols.append(protocol)
            except OSError:
                raise AidotOSError

    # ---------------------------------------------------------------------- #
    # Public API (kept compatible with existing callers)
    # ---------------------------------------------------------------------- #

    async def try_create_broadcast(self) -> None:
        await self._ensure_sockets()

    async def send_broadcast(self) -> None:
        await self._ensure_sockets()
        for proto in self._protocols:
            proto.send_broadcast()

    async def repeat_broadcast(self) -> None:
        self._is_close = False
        while True:
            await self.send_broadcast()
            for _ in range(_DISCOVER_TIME):
                await asyncio.sleep(1)
                if self._is_close:
                    return

    def start_repeat_broadcast(self) -> None:
        """Timer-driven discovery: a few fast rounds at startup, then slow."""
        self._is_close = False
        self._fast_discover_count = 5
        self._schedule_broadcast()

    def _schedule_broadcast(self) -> None:
        if self._fast_discover_count > 0:
            interval = _DISCOVER_FAST
            self._fast_discover_count -= 1
        else:
            interval = _DISCOVER_SLOW

        loop = asyncio.get_running_loop()
        asyncio.create_task(self._do_broadcast())
        self._timer_handle = loop.call_later(interval, self._schedule_broadcast)

    async def _do_broadcast(self) -> None:
        try:
            await self.send_broadcast()
        except Exception as e:
            _LOGGER.error(f"Broadcast failed: {e}")

    async def fetch_devices_info(self) -> dict[str, str]:
        await self.send_broadcast()
        await asyncio.sleep(2)
        return self.discovered_device

    def _discover_callback(self, dev_id, event: dict[str, str]) -> None:
        self.discovered_device[dev_id] = event[CONF_IPADDRESS]
        if self._callback:
            self._callback(dev_id, event)

    def close(self) -> None:
        self._is_close = True
        if self._timer_handle is not None:
            self._timer_handle.cancel()
            self._timer_handle = None
        for proto in self._protocols:
            proto.close()
        self._protocols.clear()
