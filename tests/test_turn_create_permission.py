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


def test_relay_only_battery_path_installs_permissions_from_the_bridge():
    """A relay-only battery cam answers AFTER the STUN window.

    Its candidates never reach the setup-time install, so the bridge thread's
    periodic nomination tick is the only place its permission can be created.
    Nominating without one probes a black hole.
    """
    src = _impl_source()
    tick = src[src.index("Periodic ICE controlling check"):]
    perm_at = tick.index("_turn_install_permissions(_uc_cands")
    probe_at = tick.index("_send_use_candidate(")
    assert perm_at < probe_at, (
        "the bridge must install the relay permission BEFORE it nominates, "
        "otherwise the relay drops the camera's reply on the late-creds path")


def test_permission_helper_is_reachable_from_the_bridge_thread():
    """Function scope, not branch scope.

    Defining the helper inside `if _cam_ice_ufrag and ...` put it out of reach
    of exactly the cameras it was written for: the relay-only ones, which never
    enter that branch.
    """
    src = _impl_source()
    def_at = src.index("def _turn_create_permission")
    branch_at = src.index("if _cam_ice_ufrag and _cam_ice_pwd and _cam_ice_cands:")
    assert def_at < branch_at, (
        "_turn_create_permission must be defined at function scope so the "
        "bridge thread can call it on the late-creds path")


def test_every_relay_allocation_records_the_username():
    """CreatePermission re-authenticates; a short tuple silently disables it.

    The helper bails on `len(_cp) < 8`, so an allocation path that forgets the
    username is not a loud failure - it is a permission that is never sent.
    """
    import ast

    tree = ast.parse(textwrap.dedent(_impl_source()))
    stores = [
        node for node in ast.walk(tree)
        if isinstance(node, ast.Assign)
        and isinstance(node.targets[0], ast.Subscript)
        and getattr(node.targets[0].value, "id", None) == "_relay_addrs"
    ]
    assert stores, "expected at least one _relay_addrs assignment"
    for node in stores:
        assert isinstance(node.value, ast.Tuple), "_relay_addrs stores a tuple"
        if any(isinstance(e, ast.Starred) for e in node.value.elts):
            # A splat rewrites one field of an existing entry (the stale-nonce
            # refresh) and preserves its shape by construction.
            continue
        assert len(node.value.elts) == 8, (
            f"_relay_addrs assignment at line {node.lineno} stores "
            f"{len(node.value.elts)} fields; all 8 are required "
            "(relay_ip, relay_port, realm, nonce, t_host, t_port, key, user)")


def test_stale_nonce_is_refreshed_and_retried():
    """438 Stale Nonce is routine and recoverable, not a dead end."""
    src = _impl_source()
    assert "_cp_err == 438" in src, "must recognise 438 Stale Nonce"
    handler = src[src.index("_cp_err == 438"):]
    handler = handler[:handler.index("else:")]
    assert "_relay_addrs[_bs] = (" in handler, (
        "a stale nonce must be replaced with the server's fresh one")
    assert "_br_last_perm = 0.0" in handler, (
        "the retry gate must be reset so the refreshed nonce is used at once")


def test_trickled_candidates_are_permissioned_not_just_the_answer_sdp():
    """The answer SDP only carries the camera's private host candidate.

    A TURN permission is matched against the peer's source address as the
    relay sees it, so a permission for an RFC1918 host candidate authorises an
    address that can never arrive. Measured on a live A001513: CreatePermission
    for the host candidate returned success and still yielded zero
    relay-carried inbound packets. The reachable addresses (srflx, relay) only
    ever arrive by iceCandidateReq trickle.
    """
    src = _impl_source()
    assert "ice_cands_seen" in src, (
        "the SDES path must observe the camera's trickled ICE candidates")
    trickle = src[src.index("async def _consume_camera_trickle"):]
    trickle = trickle[:trickle.index("\n        if ice_cands_seen is not None")]
    assert "_turn_install_permissions(" in trickle, (
        "every trickled candidate needs a relay permission")

    # No candidate-type filter between parsing the type and installing: srflx
    # and relay are precisely the addresses the TURN server can match, so a
    # guard that skipped them would reinstate the original black hole.
    body = trickle[trickle.index("_tk_typ = "):
                   trickle.index("_turn_install_permissions(")]
    for guard in ("_tk_typ ==", "_tk_typ !=", "_tk_typ in", "_tk_typ not in"):
        assert guard not in body, (
            f"candidate type is filtered by `{guard}` before the permission is "
            "installed; srflx and relay must not be skipped")


def test_trickle_regex_parses_real_camera_candidates():
    """Byte-for-byte against candidate lines a live A001513 actually sent."""
    import re

    src = _impl_source()
    trickle = src[src.index("async def _consume_camera_trickle"):]
    pat = trickle[trickle.index('r"(?:a=)?candidate:'):]
    pat = pat[len('r"'):pat.index('",\n')]

    lines = {
        "host":  "candidate:0 1 udp 2130706431 203.0.113.9 48776 typ host raddr 0.0.0.0 rport 0 generation 0 network-cost 999",
        "srflx": "candidate:1 1 udp 1694498815 198.51.100.7 34686 typ srflx raddr 0.0.0.0 rport 0 generation 0 network-cost 999",
        "relay": "candidate:2 1 udp 16777215 3.230.182.123 21449 typ relay raddr 0.0.0.0 rport 0 generation 0 network-cost 999",
    }
    expected = {
        "host":  ("203.0.113.9", 48776),
        "srflx": ("198.51.100.7", 34686),
        "relay": ("3.230.182.123", 21449),
    }
    for kind, line in lines.items():
        m = re.match(pat, line)
        assert m, f"{kind} candidate must parse: {line}"
        assert (m.group(1), int(m.group(2))) == expected[kind]
        assert m.group(3) == kind


def test_nomination_set_is_rebound_not_mutated():
    """The bridge thread iterates this list; mutating it under them raises."""
    src = _impl_source()
    trickle = src[src.index("async def _consume_camera_trickle"):]
    assert '_bridge_uc_info["cands"] = [' in trickle, (
        "rebind the candidate list to a new object so the bridge thread never "
        "sees it mutate mid-iteration")
    assert '_bridge_uc_info["cands"].append' not in trickle, (
        "appending in place races the bridge thread's iteration")


def test_trickle_observer_does_not_consume_the_dtls_queue():
    """SDES must not steal candidates the DTLS fallback still needs."""
    wo = inspect.getsource(sys.modules["aidot_cameras.camera.webrtc_open"])
    hook = wo[wo.index('cand = inner.get("candidate")'):][:400]
    assert "ice_cands_seen.append(cand)" in hook, "observer must record it"
    assert "ice_q.put_nowait" in hook, (
        "the DTLS queue must still receive every candidate")
