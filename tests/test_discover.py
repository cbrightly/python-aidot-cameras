"""Unit tests for aidot_cameras.discover.

The networking (datagram endpoints, broadcast timers) is left to the live path;
what is exercised here is the logic that decides WHERE to broadcast and what to
do with a received packet: per-interface enumeration and its fallbacks, the
split-log packet handler, and the per-account discovered-address map.
"""

import json
import subprocess
from unittest import mock


from aidot_cameras import discover
from aidot_cameras.crypto import aes_encrypt

_SWEEP_KEY = bytearray(b"T54uednca587".ljust(32, b"\x00"))


# -- _resolve_tool -----------------------------------------------------------


def test_resolve_tool_prefers_path(monkeypatch):
    monkeypatch.setattr(discover.shutil, "which", lambda n: "/usr/bin/ip")
    assert discover._resolve_tool("ip", "/sbin/ip") == "/usr/bin/ip"


def test_resolve_tool_falls_back_to_absolute_path(monkeypatch):
    monkeypatch.setattr(discover.shutil, "which", lambda n: None)
    monkeypatch.setattr(discover.os.path, "exists", lambda p: p == "/sbin/ip")
    assert discover._resolve_tool("ip", "/sbin/ip") == "/sbin/ip"


def test_resolve_tool_returns_none_when_missing(monkeypatch):
    monkeypatch.setattr(discover.shutil, "which", lambda n: None)
    monkeypatch.setattr(discover.os.path, "exists", lambda p: False)
    assert discover._resolve_tool("ip", "/sbin/ip") is None


# -- _get_broadcast_candidates -----------------------------------------------

_LINUX_IP = """\
1: lo: <LOOPBACK,UP> mtu 65536
    inet 127.0.0.1/8 scope host lo
2: eth0: <BROADCAST,UP> mtu 1500
    inet 192.0.2.50/24 brd 192.0.2.255 scope global eth0
3: docker0: <BROADCAST,UP> mtu 1500
    inet 172.17.0.1/16 brd 172.17.255.255 scope global docker0
4: veth: <BROADCAST,UP> mtu 1500
    inet 169.254.1.1/16 brd 169.254.255.255 scope global veth
"""

_MACOS_IFCONFIG = """\
lo0: flags=8049<UP,LOOPBACK> mtu 16384
    inet 127.0.0.1 netmask 0xff000000
en0: flags=8863<UP,BROADCAST> mtu 1500
    inet 192.0.2.175 netmask 0xffffff00 broadcast 192.0.2.255
"""


def test_linux_enumeration_keeps_real_interfaces_only(monkeypatch):
    monkeypatch.setattr(discover.sys, "platform", "linux")
    monkeypatch.setattr(discover.shutil, "which", lambda n: "/usr/bin/ip")
    monkeypatch.setattr(discover.subprocess, "check_output", lambda *a, **k: _LINUX_IP)
    got = discover._get_broadcast_candidates()
    # loopback (127.) and link-local (169.254.) excluded; eth0 + docker0 kept.
    assert got == [("192.0.2.50", "192.0.2.255"), ("172.17.0.1", "172.17.255.255")]


def test_macos_enumeration_parses_ifconfig(monkeypatch):
    monkeypatch.setattr(discover.sys, "platform", "darwin")
    monkeypatch.setattr(discover.shutil, "which", lambda n: "/sbin/ifconfig")
    monkeypatch.setattr(
        discover.subprocess, "check_output", lambda *a, **k: _MACOS_IFCONFIG
    )
    assert discover._get_broadcast_candidates() == [("192.0.2.175", "192.0.2.255")]


def test_missing_tool_falls_back_to_the_single_global_socket(monkeypatch):
    monkeypatch.setattr(discover.sys, "platform", "linux")
    monkeypatch.setattr(discover.shutil, "which", lambda n: None)
    monkeypatch.setattr(discover.os.path, "exists", lambda p: False)
    assert discover._get_broadcast_candidates() == [("0.0.0.0", "255.255.255.255")]


def test_subprocess_failure_falls_back(monkeypatch):
    monkeypatch.setattr(discover.sys, "platform", "linux")
    monkeypatch.setattr(discover.shutil, "which", lambda n: "/usr/bin/ip")

    def boom(*a, **k):
        raise subprocess.SubprocessError("ip blew up")

    monkeypatch.setattr(discover.subprocess, "check_output", boom)
    assert discover._get_broadcast_candidates() == [("0.0.0.0", "255.255.255.255")]


def test_output_without_a_usable_interface_falls_back(monkeypatch):
    monkeypatch.setattr(discover.sys, "platform", "linux")
    monkeypatch.setattr(discover.shutil, "which", lambda n: "/usr/bin/ip")
    monkeypatch.setattr(
        discover.subprocess, "check_output", lambda *a, **k: "no inet lines here"
    )
    assert discover._get_broadcast_candidates() == [("0.0.0.0", "255.255.255.255")]


# -- InterfaceBroadcastProtocol.datagram_received ----------------------------


def _proto(cb):
    return discover.InterfaceBroadcastProtocol(
        cb, "user-1", broadcast_addr="192.0.2.255"
    )


def test_a_valid_packet_records_the_device_address():
    seen = []
    proto = _proto(lambda dev, ev: seen.append((dev, ev)))
    packet = aes_encrypt(
        json.dumps({"payload": {"devId": "DEV1"}}).encode(), _SWEEP_KEY
    )
    proto.datagram_received(packet, ("192.0.2.7", 6666))
    assert seen == [("DEV1", {"ipAddress": "192.0.2.7"})]


def test_an_undecodable_packet_is_ignored():
    seen = []
    proto = _proto(lambda dev, ev: seen.append(dev))
    proto.datagram_received(b"not-encrypted-with-our-key", ("192.0.2.9", 6666))
    assert seen == []  # other vendors' chatter on 6666 is dropped quietly


def test_a_packet_without_a_device_id_fires_no_callback():
    seen = []
    proto = _proto(lambda dev, ev: seen.append(dev))
    packet = aes_encrypt(json.dumps({"payload": {}}).encode(), _SWEEP_KEY)
    proto.datagram_received(packet, ("192.0.2.9", 6666))
    assert seen == []


# -- InterfaceBroadcastProtocol.send_broadcast -------------------------------


class _FakeTransport:
    def __init__(self):
        self.sent = []

    def get_extra_info(self, _name):
        return None

    def sendto(self, data, addr):
        self.sent.append((data, addr))


def test_send_broadcast_targets_this_interfaces_address():
    proto = _proto(lambda *a: None)
    proto._is_closed = False
    proto.transport = _FakeTransport()
    proto.send_broadcast()
    assert len(proto.transport.sent) == 1
    data, addr = proto.transport.sent[0]
    assert addr == ("192.0.2.255", discover._DISCOVER_PORT)
    assert isinstance(data, (bytes, bytearray)) and data  # encrypted request


def test_send_broadcast_is_a_no_op_once_closed():
    proto = _proto(lambda *a: None)
    proto._is_closed = True
    proto.transport = _FakeTransport()
    proto.send_broadcast()
    assert proto.transport.sent == []


# -- CameraDiscover ----------------------------------------------------------


def _discover():
    return discover.CameraDiscover({"id": "acct-1"}, callback=None)


def test_discovered_callback_records_and_forwards():
    forwarded = []
    d = discover.CameraDiscover(
        {"id": "acct-1"}, callback=lambda *a: forwarded.append(a)
    )
    d._discover_callback("DEVX", {"ipAddress": "192.0.2.3"})
    assert d.discovered_device == {"DEVX": "192.0.2.3"}
    assert forwarded == [("DEVX", {"ipAddress": "192.0.2.3"})]


def test_two_accounts_keep_separate_maps():
    a = _discover()
    b = _discover()
    a._discover_callback("DEV", {"ipAddress": "10.0.0.1"})
    assert a.discovered_device == {"DEV": "10.0.0.1"}
    assert b.discovered_device == {}


def test_close_cancels_timers_tasks_and_sockets():
    d = _discover()
    proto = mock.Mock()
    task = mock.Mock()
    task.done.return_value = False
    timer = mock.Mock()
    d._protocols = [proto]
    d._broadcast_task = task
    d._timer_handle = timer
    d.close()
    task.cancel.assert_called_once()
    timer.cancel.assert_called_once()
    proto.close.assert_called_once()
    assert d._protocols == [] and d._is_close is True


def test_close_is_idempotent():
    d = _discover()
    d.close()
    d.close()  # must not raise with nothing to cancel


async def test_send_broadcast_hits_every_interface():
    d = _discover()
    p1, p2 = mock.Mock(), mock.Mock()
    d._protocols = [p1, p2]
    # Sockets already exist, so _ensure_sockets returns immediately.
    await d.send_broadcast()
    p1.send_broadcast.assert_called_once()
    p2.send_broadcast.assert_called_once()
