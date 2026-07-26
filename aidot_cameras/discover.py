"""LAN discovery with per-interface broadcast fan-out.

Extends upstream's ``aidot.discover``.  Upstream sends one datagram to
``255.255.255.255`` from a single socket bound to ``0.0.0.0``, which the OS
routes out of whichever interface the default route prefers.  On a host with
more than one active interface (the common Home Assistant / NAS case: LAN +
docker0 + VPN) that silently misses every device on the other interfaces, and
there is no way to stop the sweep once started.

This module keeps upstream's wire format and packet handling and changes only
the fan-out and lifecycle:

* one datagram endpoint per active IPv4 interface, each broadcasting to that
  interface's own broadcast address;
* an instance-based lifecycle (``start_repeat_broadcast()`` / ``close()``) so an
  integration can stop discovery on unload;
* a fast-then-slow cadence, so a restart re-finds devices quickly without
  broadcasting every few seconds forever.

Discovered addresses are written to ``aidot.discover.Discover.DISCOVERED_DEVICE``
- the very dict upstream's ``AidotClient.get_device_client`` reads - so a plain
upstream ``DeviceClient`` still gets its LAN IP from this sweep.

Note: the unicast camera probe is NOT here; cameras ignore the broadcast sweep
entirely and are probed from ``aidot_cameras.camera.lan_control``.
"""

import asyncio
import json
import logging
import os
import re
import shutil
import subprocess
import sys
from typing import Any, List, Optional, Tuple

from aidot.discover import BroadcastProtocol as _UpstreamBroadcastProtocol
from aidot.discover import Discover as _UpstreamDiscover
from aidot.models.discover_model import DiscoverRequest, DiscoverResponse

from .const import CONF_ID, CONF_IPADDRESS
from .crypto import aes_decrypt, aes_encrypt
from .exceptions import AidotOSError

_LOGGER = logging.getLogger(__name__)

_DISCOVER_FAST = 6      # fast discovery cadence right after startup
_DISCOVER_SLOW = 120    # slow maintenance cadence once stable
_DISCOVER_FAST_ROUNDS = 5
_DISCOVER_PORT = 6666


def _resolve_tool(name: str, absolute_fallback: str) -> Optional[str]:
    """Resolve a system tool via PATH, falling back to an absolute path.

    Returns None if neither the PATH lookup nor the fallback path exists, so
    callers can degrade gracefully instead of shelling out to a missing tool.
    """
    resolved = shutil.which(name)
    if resolved:
        return resolved
    if os.path.exists(absolute_fallback):
        return absolute_fallback
    return None


def _get_broadcast_candidates() -> List[Tuple[str, str]]:
    """Return (bind_ip, broadcast_ip) pairs for every active IPv4 interface.

    Sends a separate broadcast per interface so devices are reachable
    regardless of which interface the OS default route prefers.
    Falls back to a single ("0.0.0.0", "255.255.255.255") entry if
    interface enumeration is unavailable (missing tool or subprocess error) -
    i.e. exactly upstream's single-socket behavior.
    """
    results: List[Tuple[str, str]] = []
    try:
        if sys.platform == "darwin":
            # macOS ifconfig: "inet 192.168.1.175 netmask 0xffffff00 broadcast 192.168.1.255"
            tool = _resolve_tool("ifconfig", "/sbin/ifconfig")
            if tool is None:
                _LOGGER.debug("_get_broadcast_candidates: ifconfig not found on PATH")
            else:
                out = subprocess.check_output(
                    [tool], text=True, stderr=subprocess.DEVNULL
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
            tool = _resolve_tool("ip", "/sbin/ip")
            if tool is None:
                _LOGGER.debug("_get_broadcast_candidates: ip not found on PATH")
            else:
                out = subprocess.check_output(
                    [tool, "addr", "show"], text=True, stderr=subprocess.DEVNULL
                )
                for m in re.finditer(
                    r"\binet\s+"
                    r"((?!127\.|169\.254\.)\d+\.\d+\.\d+\.\d+)/\d+"
                    r"\s+brd\s+"
                    r"(\d+\.\d+\.\d+\.\d+)",
                    out,
                ):
                    results.append((m.group(1), m.group(2)))
    except (OSError, subprocess.SubprocessError) as exc:
        _LOGGER.debug("_get_broadcast_candidates: interface enumeration failed: %s", exc)
    except Exception as exc:  # never let enumeration crash discovery
        _LOGGER.debug("_get_broadcast_candidates: unexpected error: %s", exc)

    if not results:
        # Fallback: let the OS pick the outgoing interface
        results = [("0.0.0.0", "255.255.255.255")]

    _LOGGER.debug("_get_broadcast_candidates: %s", results)
    return results


class InterfaceBroadcastProtocol(_UpstreamBroadcastProtocol):
    """One upstream broadcast protocol pinned to a single interface.

    Everything about the wire format and the receive path is upstream's:
    ``connection_made`` (SO_BROADCAST), ``error_received``, ``close`` and
    ``connection_lost`` are inherited unchanged.  Only the two methods that
    hardcode a destination or a log level are overridden.
    """

    def __init__(
        self,
        callback,
        user_id: str,
        broadcast_addr: str = "255.255.255.255",
    ) -> None:
        super().__init__(callback)
        self.user_id = user_id
        self._broadcast_addr = broadcast_addr

    def send_broadcast(self, message: "Optional[dict]" = None) -> None:
        """Broadcast a discovery request to THIS interface's broadcast address.

        Overridden rather than reused: upstream's ``send_broadcast`` hardcodes
        the ``("255.255.255.255", 6666)`` destination inline, so there is no seam
        for a per-interface address.  ``message`` stays accepted (and optional)
        so an upstream caller passing one keeps working.
        """
        if self._is_closed:
            _LOGGER.error("%s: connection is closed", self.user_id)
            return
        try:
            if message is None:
                message = DiscoverRequest.from_params(userId=self.user_id).to_dict()
            _LOGGER.debug("send_broadcast %s", message)
            send_data = aes_encrypt(json.dumps(message).encode(), self.aes_key)
            sock = self.transport.get_extra_info("socket")
            local_addr = sock.getsockname() if sock else ("?", 0)
            self.transport.sendto(send_data, (self._broadcast_addr, _DISCOVER_PORT))
            _LOGGER.debug(
                "discovery broadcast sent: %s:%s -> %s:%s",
                local_addr[0], local_addr[1], self._broadcast_addr, _DISCOVER_PORT,
            )
        except Exception as error:
            _LOGGER.error("%s: send failed: %s", self.user_id, error)

    def datagram_received(self, data, addr) -> None:
        """Same handling as upstream, split into two log levels.

        Port 6666 also carries other vendors' (and other accounts') discovery
        chatter, none of which decrypts with the well-known sweep key.  Upstream
        logs every such packet at ERROR, which floods the log on a busy LAN, so
        undecodable input is demoted to DEBUG while a genuine failure to handle
        a packet we DID decode still logs at ERROR.
        """
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
            _LOGGER.error("datagram_received error: %s", error)


class CameraDiscover(_UpstreamDiscover):
    """Instance-scoped, multi-interface discovery sweep.

    Subclasses upstream's ``Discover`` for one reason that matters: the
    inherited ``DISCOVERED_DEVICE`` class attribute.  ``_discover_callback``
    writes into that dict by item assignment, so it mutates the *base class's*
    object rather than shadowing it - which is what lets a pure-upstream
    ``DeviceClient`` (created by ``AidotClient.get_device_client``, which reads
    ``Discover.DISCOVERED_DEVICE``) still receive a LAN IP from this sweep.

    The rest of upstream's ``Discover`` is a static class whose broadcast state
    (``_BROADCAST_PROTOCOL``, ``BROADCAST_TIMER``) is single-valued and whose
    timer is started as a side effect of ``set_user_info``, with no way to stop
    it.  Per-interface fan-out plus a stoppable lifecycle cannot be expressed by
    overriding any single one of those classmethods, so the instance API below
    is provided in full.  Upstream's classmethod API is left intact and unused
    by us: ``CameraClient.setup_discover`` never calls ``set_user_info``, so
    upstream's own timer is never started and the two do not double-broadcast.
    """

    _timer_handle: "Optional[asyncio.TimerHandle]" = None

    def __init__(self, login_info: dict[str, Any], callback) -> None:
        """Create a sweep for one account.

        Deliberately does NOT call ``super().__init__``: upstream's raises
        ``TypeError("Discover is a static class and cannot be instantiated")``.
        Re-enabling instance construction is the intended contract change - the
        sweep needs per-instance sockets and a close hook.
        """
        self._login_info = login_info
        self._callback = callback
        self._protocols: List[InterfaceBroadcastProtocol] = []
        self._is_close = False
        self._broadcast_task: "Optional[asyncio.Task]" = None
        self._fast_discover_count = 0

    @property
    def discovered_device(self) -> dict[str, str]:
        """The device-id -> LAN IP map.

        Intentionally the same object as
        ``aidot.discover.Discover.DISCOVERED_DEVICE`` (see the class docstring):
        one map, read by both our dispatch and upstream's.
        """
        return _UpstreamDiscover.DISCOVERED_DEVICE

    async def _ensure_sockets(self) -> None:
        """Create one datagram endpoint per active interface (idempotent)."""
        if self._is_close or self._protocols:
            return

        # subprocess-based interface enumeration must not block the loop
        candidates = await asyncio.to_thread(_get_broadcast_candidates)
        user_id = self._login_info[CONF_ID]

        for bind_ip, broadcast_ip in candidates:
            protocol = InterfaceBroadcastProtocol(
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
                _LOGGER.debug("discovery socket bind %s failed: %s", bind_ip, exc)

        if not self._protocols:
            # Last-resort fallback: upstream's single 0.0.0.0 socket.
            protocol = InterfaceBroadcastProtocol(self._discover_callback, user_id)
            try:
                await asyncio.get_running_loop().create_datagram_endpoint(
                    lambda: protocol,
                    local_addr=("0.0.0.0", 0),
                )
                self._protocols.append(protocol)
            except OSError:
                raise AidotOSError

    async def try_create_broadcast(self) -> None:
        """Ensure discovery sockets are open without sending a broadcast yet."""
        await self._ensure_sockets()

    async def send_broadcast(self) -> None:
        """Send a discovery broadcast on every active interface."""
        await self._ensure_sockets()
        for proto in self._protocols:
            proto.send_broadcast()

    def start_repeat_broadcast(self) -> None:
        """Timer-driven discovery: a few fast rounds at startup, then slow."""
        self._is_close = False
        self._fast_discover_count = _DISCOVER_FAST_ROUNDS
        self._schedule_broadcast()

    def _schedule_broadcast(self) -> None:
        if self._is_close:
            return
        if self._fast_discover_count > 0:
            interval = _DISCOVER_FAST
            self._fast_discover_count -= 1
        else:
            interval = _DISCOVER_SLOW

        loop = asyncio.get_running_loop()
        self._broadcast_task = asyncio.create_task(self._do_broadcast())
        self._timer_handle = loop.call_later(interval, self._schedule_broadcast)

    async def _do_broadcast(self) -> None:
        if self._is_close:
            return
        try:
            await self.send_broadcast()
        except Exception as exc:
            _LOGGER.error("Broadcast failed: %s", exc)

    def _discover_callback(self, dev_id, event: dict[str, str]) -> None:
        """Record a discovered address and forward it to the account client.

        Overrides upstream's classmethod with an instance method so the sweep's
        own callback is used; the write still lands in the shared
        ``DISCOVERED_DEVICE`` map (see ``discovered_device``).
        """
        self.discovered_device[dev_id] = event[CONF_IPADDRESS]
        if self._callback:
            self._callback(dev_id, event)

    def close(self) -> None:
        """Stop discovery, cancel timers/tasks, and close all sockets.

        Upstream has no equivalent - its timer runs for the process lifetime.
        """
        self._is_close = True
        if self._broadcast_task is not None and not self._broadcast_task.done():
            self._broadcast_task.cancel()
        self._broadcast_task = None
        if self._timer_handle is not None:
            self._timer_handle.cancel()
            self._timer_handle = None
        for proto in self._protocols:
            proto.close()
        self._protocols.clear()


# Back-compat aliases: these are the names the fork exported before the port.
Discover = CameraDiscover
BroadcastProtocol = InterfaceBroadcastProtocol
