"""The SDES path must open the TURN relay's door before probing the camera.

A TURN server drops everything from a peer until a permission exists for that
peer's address (RFC 5766 s9). We advertise the relay allocation as an ICE
candidate, but the only thing that ever created a permission was a Send
indication emitted while HANDLING data that had already arrived through the
relay - which cannot happen until the permission exists. That deadlock made the
advertised relay candidate a black hole.

Measured on a live A001513 whose host could not be reached directly: zero
packets ever arrived from the relay and the camera sent no media at all, while
the same camera streamed to a host it could reach directly. The DTLS path never
hit this because aiortc implements TURN properly.

These tests assert against the SHIPPED code path, not a local re-implementation.
"""
import inspect
import os
import sys
import textwrap

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot_cameras.camera.sdes_open as so

_STUN_MAGIC = b"\x21\x12\xa4\x42"


def _extract_helper(src, name):
    """Pull a nested def out of the shipped source, dedented and runnable.

    Extracts by indentation so the real body - including its inner defs - is
    exercised verbatim; nothing here re-implements the logic under test.
    """
    lines = src.splitlines()
    start = next(i for i, ln in enumerate(lines)
                 if ln.strip().startswith(f"def {name}("))
    indent = len(lines[start]) - len(lines[start].lstrip())
    out = [lines[start]]
    for ln in lines[start + 1:]:
        if ln.strip() and (len(ln) - len(ln.lstrip())) <= indent:
            break
        out.append(ln)
    return textwrap.dedent("\n".join(out))


def _impl_source():
    for cls in vars(so).values():
        if isinstance(cls, type):
            fn = cls.__dict__.get("_open_sdes_stream_impl")
            if fn is not None:
                return inspect.getsource(fn)
    raise AssertionError("_open_sdes_stream_impl not found")


def test_create_permission_is_sent_before_connectivity_checks():
    src = _impl_source()
    assert "_turn_create_permission" in src, (
        "the SDES path must install a TURN permission for the camera")
    perm_at = src.index("_turn_create_permission(_p_sock")
    probe_at = src.index("_send_use_candidate(\n")
    assert perm_at < probe_at, (
        "the relay permission must be installed BEFORE the connectivity checks, "
        "otherwise the relay drops the camera's reply")


def test_permission_is_built_for_every_camera_candidate_on_both_sockets():
    src = _impl_source()
    block = src[src.index("_perm_ok = 0"):src.index("_send_use_candidate(\n")]
    assert "for _c_ip, _c_port in _cam_ice_cands" in block, "must cover every candidate"
    assert "_audio_sock" in block and "_video_sock" in block, (
        "audio and video have separate relay allocations; both need permissions")


def test_create_permission_message_is_a_valid_stun_request():
    """Byte-level check of the SHIPPED builder, exercised via a fake socket."""
    body = _extract_helper(_impl_source(), "_turn_create_permission")

    sent = {}

    class _Sock:
        def sendto(self, msg, addr):
            sent["msg"], sent["addr"] = msg, addr

    sock = _Sock()
    key = b"k" * 16
    ns = {
        "_relay_addrs": {sock: ("1.2.3.4", 5000, b"realm", b"nonce",
                                "turn.host", 3478, key, b"user")},
        "_STUN_MAGIC": _STUN_MAGIC,
        "os": os,
        "_LOGGER": so._LOGGER,
    }
    exec(body, ns)                      # noqa: S102 - exercising the shipped source
    assert ns["_turn_create_permission"](sock, "192.0.2.55", 41234) is True

    msg = sent["msg"]
    assert sent["addr"] == ("turn.host", 3478)
    assert msg[:2] == b"\x00\x08", "must be a CreatePermission request (0x0008)"
    assert msg[4:8] == _STUN_MAGIC, "magic cookie"
    assert int.from_bytes(msg[2:4], "big") == len(msg) - 20, "length covers body"

    # walk attributes: XOR-PEER-ADDRESS, USERNAME, REALM, NONCE, MESSAGE-INTEGRITY
    seen, i = [], 20
    while i + 4 <= len(msg):
        t = int.from_bytes(msg[i:i + 2], "big")
        ln = int.from_bytes(msg[i + 2:i + 4], "big")
        seen.append((t, msg[i + 4:i + 4 + ln]))
        i += 4 + ln + ((-ln) % 4)
    types = [t for t, _ in seen]
    for required in (0x0012, 0x0006, 0x0014, 0x0015, 0x0008):
        assert required in types, f"missing STUN attribute 0x{required:04x}"

    xpa = dict(seen)[0x0012]
    assert xpa[:2] == b"\x00\x01", "IPv4 family"
    port = int.from_bytes(xpa[2:4], "big") ^ 0x2112
    assert port == 41234, "port must be XOR-mapped"
    ip = bytes(a ^ b for a, b in zip(xpa[4:8], _STUN_MAGIC, strict=True))
    assert ".".join(str(b) for b in ip) == "192.0.2.55", "addr must be XOR-mapped"


def test_permission_helper_is_defensive_without_an_allocation():
    body = _extract_helper(_impl_source(), "_turn_create_permission")
    ns = {"_relay_addrs": {}, "_STUN_MAGIC": _STUN_MAGIC, "os": os,
          "_LOGGER": so._LOGGER}
    exec(body, ns)                      # noqa: S102
    assert ns["_turn_create_permission"](object(), "10.0.0.1", 1234) is False, (
        "a socket with no relay allocation must be a no-op, not an exception")
