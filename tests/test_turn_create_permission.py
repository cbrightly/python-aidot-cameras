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

import ast
import os
import sys
import textwrap

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot_cameras.camera.sdes_open as so
from tests.ast_guards import name_of


_STUN_MAGIC = b"\x21\x12\xa4\x42"


def _extract_helper(src, name):
    """Pull a nested def out of the shipped source, dedented and runnable.

    Extracts by indentation so the real body - including its inner defs - is
    exercised verbatim; nothing here re-implements the logic under test.
    """
    lines = src.splitlines()
    start = next(
        i for i, ln in enumerate(lines) if ln.strip().startswith(f"def {name}(")
    )
    indent = len(lines[start]) - len(lines[start].lstrip())
    out = [lines[start]]
    for ln in lines[start + 1 :]:
        if ln.strip() and (len(ln) - len(ln.lstrip())) <= indent:
            break
        out.append(ln)
    return textwrap.dedent("\n".join(out))


def _nomination_order(block):
    """Line numbers of the permission installs and the nominations in ``block``."""
    perms = [
        c.lineno
        for c in ast.walk(block)
        if isinstance(c, ast.Call) and "install_permissions" in name_of(c)
    ]
    noms = [
        c.lineno
        for c in ast.walk(block)
        if isinstance(c, ast.Call) and name_of(c).endswith("send_use_candidate")
    ]
    return perms, noms


def test_permission_is_installed_before_every_nomination():
    """Every path that nominates opens the relay's door first: the answer
    path (driven above), the setup-time block and the bridge's periodic tick.
    The last two live in code that cannot be driven, so per block: the block
    that nominates installs the permission first."""
    from tests.ast_guards import names_in, sdes_bridge, sdes_open_family

    setup = [
        n
        for fn in sdes_open_family()
        for n in ast.walk(fn)
        if isinstance(n, ast.If)
        and {"_cam_ice_ufrag", "_cam_ice_pwd", "_cam_ice_cands"} <= names_in(n.test)
        and _nomination_order(n)[1]
    ]
    tick = [
        n
        for n in ast.walk(sdes_bridge())
        if isinstance(n, (ast.If, ast.For, ast.While, ast.Try))
        and _nomination_order(n)[0]
        and _nomination_order(n)[1]
    ]
    for name, blocks in (("setup", setup), ("bridge", tick)):
        assert blocks, f"{name}: expected a nomination path"
        innermost = min(blocks, key=lambda b: b.end_lineno - b.lineno)
        perms, noms = _nomination_order(innermost)
        assert perms and min(perms) < min(noms), (
            f"{name}: the relay permission must be installed BEFORE the "
            "connectivity checks"
        )


def test_relay_only_battery_path_installs_permissions_from_the_bridge():
    """A relay-only battery camera answers AFTER the STUN window: its
    candidates never reach the setup-time install, so the bridge's periodic
    nomination installs the permission for the very candidates it probes."""
    from tests.ast_guards import calls_to, sdes_bridge

    bridge = sdes_bridge()
    installs = calls_to(bridge, "_turn_install_permissions")
    assert installs, "the bridge never installs a relay permission"
    nominated = {ast.unparse(c.args[0]) for c in installs if c.args}
    assert any("_uc_cands" in a for a in nominated), nominated


def test_permission_helper_is_reachable_from_the_bridge_thread():
    """Function scope, not branch scope: defined inside `if _cam_ice_ufrag
    and ...` the helper was out of reach of exactly the relay-only cameras it
    was written for, which never enter that branch."""
    from tests.ast_guards import enclosing, parents, sdes_open_family

    binds = []
    for fn in sdes_open_family():
        pm = parents(fn)
        for n in ast.walk(fn):
            if (
                isinstance(n, ast.Assign)
                and any(
                    ast.unparse(t).endswith("_turn_create_permission")
                    for t in n.targets
                )
                and "partial" in ast.unparse(n.value)
            ):
                binds.append((n, enclosing(n, pm, (ast.If, ast.For, ast.While))))
    assert binds, "the permission helper is no longer bound in the open"
    for node, scopes in binds:
        assert not scopes, f"line {node.lineno}: bound inside a branch"


def test_the_open_binds_the_permission_helper_to_both_sockets():
    """Audio and video have separate relay allocations; the helper the open
    hands to every path is bound to both sockets."""
    from tests.ast_guards import sdes_open_family

    binds = [
        n.value
        for fn in sdes_open_family()
        for n in ast.walk(fn)
        if isinstance(n, ast.Assign)
        and any(ast.unparse(t).endswith("_turn_install_permissions") for t in n.targets)
        and "partial" in ast.unparse(n.value)
    ]
    assert binds
    for b in binds:
        tuples = [a for a in b.args if isinstance(a, ast.Tuple)]
        assert any(
            {name_of(e) for e in t.elts} == {"_audio_sock", "_video_sock"}
            for t in tuples
        ), ast.unparse(b)


def test_stale_nonce_is_refreshed_and_retried():
    """438 Stale Nonce is routine and recoverable: the bridge replaces the
    allocation's nonce with the server's fresh one and resets the retry gate
    so it is used at once."""
    from tests.ast_guards import sdes_bridge

    handlers = [
        n
        for n in ast.walk(sdes_bridge())
        if isinstance(n, ast.If)
        and "438" in ast.unparse(n.test)
        and "_cp_err" in ast.unparse(n.test)
    ]
    assert handlers, "must recognise 438 Stale Nonce"
    body = ast.Module(body=handlers[0].body, type_ignores=[])
    stores = [
        ast.unparse(t)
        for s in ast.walk(body)
        if isinstance(s, ast.Assign)
        for t in s.targets
    ]
    assert any(t.startswith("_relay_addrs[") for t in stores), (
        "the stale nonce is not replaced"
    )
    assert "_br_last_perm" in stores, "the retry gate is not reset"


def test_permission_helper_covers_every_candidate_on_both_sockets():
    """Audio and video have separate relay allocations; every camera candidate
    needs a permission on each."""
    asked = []

    def _create(sock, ip, port):
        asked.append((sock, ip, port))
        return True

    cands = [("10.0.0.5", 4000), ("203.0.113.7", 4001)]
    n = so.turn_install_permissions(
        _create, ("audio", "video"), [object()], lambda m: None, cands, "test"
    )
    assert n == 4
    assert sorted(asked) == sorted(
        (s, ip, port) for ip, port in cands for s in ("audio", "video")
    )


def test_the_answer_path_installs_the_permission_before_it_nominates():
    """The answer path's half of the invariant below, driven: the relay's door
    is opened for every candidate before the first USE-CANDIDATE goes out."""
    events = []
    sdp = "\r\n".join(
        [
            "v=0",
            "a=ice-ufrag:camU",
            "a=ice-pwd:camPwdcamPwdcamPwd",
            "a=candidate:1 1 udp 2130706431 10.0.0.5 4000 typ host",
            "a=candidate:2 1 udp 1694498815 203.0.113.7 4001 typ srflx",
        ]
    )
    seen = []
    n = so.nominate_from_answer_sdp(
        lambda cands, why: events.append(("perm", tuple(cands))),
        lambda sock, *a: events.append(("nominate", sock, a[-1])),
        ("a-sock", "v-sock"),
        (("ua", "pa"), ("uv", "pv")),
        seen,
        sdp,
    )
    assert n == 2
    assert events[0] == ("perm", (("10.0.0.5", 4000), ("203.0.113.7", 4001)))
    assert [e[0] for e in events[1:]] == ["nominate"] * 4
    assert seen == [("10.0.0.5", 4000), ("203.0.113.7", 4001)], (
        "what it nominated is recorded for the stall report"
    )


def test_create_permission_message_is_a_valid_stun_request():
    """Byte-level check of the SHIPPED builder, exercised via a fake socket."""
    sent = {}

    class _Sock:
        def sendto(self, msg, addr):
            sent["msg"], sent["addr"] = msg, addr

    sock = _Sock()
    key = b"k" * 16
    relay_addrs = {
        sock: ("1.2.3.4", 5000, b"realm", b"nonce", "turn.host", 3478, key, b"user")
    }
    assert so.turn_create_permission(relay_addrs, sock, "192.0.2.55", 41234) is True

    msg = sent["msg"]
    assert sent["addr"] == ("turn.host", 3478)
    assert msg[:2] == b"\x00\x08", "must be a CreatePermission request (0x0008)"
    assert msg[4:8] == _STUN_MAGIC, "magic cookie"
    assert int.from_bytes(msg[2:4], "big") == len(msg) - 20, "length covers body"

    # walk attributes: XOR-PEER-ADDRESS, USERNAME, REALM, NONCE, MESSAGE-INTEGRITY
    seen, i = [], 20
    while i + 4 <= len(msg):
        t = int.from_bytes(msg[i : i + 2], "big")
        ln = int.from_bytes(msg[i + 2 : i + 4], "big")
        seen.append((t, msg[i + 4 : i + 4 + ln]))
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
    assert so.turn_create_permission({}, object(), "10.0.0.1", 1234) is False, (
        "a socket with no relay allocation must be a no-op, not an exception"
    )


def test_every_relay_allocation_records_the_username():
    """CreatePermission re-authenticates; a short tuple silently disables it.

    The helper bails on `len(_cp) < 8`, so an allocation path that forgets the
    username is not a loud failure - it is a permission that is never sent.
    """
    import ast

    from tests.ast_guards import sdes_open_family

    stores = [
        node
        for fn in sdes_open_family()
        for node in ast.walk(fn)
        if isinstance(node, ast.Assign)
        and isinstance(node.targets[0], ast.Subscript)
        and name_of(node.targets[0].value) == "_relay_addrs"
    ]
    assert stores, "expected at least one _relay_addrs assignment"
    for node in stores:
        if (
            isinstance(node.value, ast.Call)
            and getattr(node.value.func, "id", None) == "_turn_relay_record"
        ):
            # The shared builder takes the username as a required positional
            # and stores all 8 fields; test_turn_target_shared pins its shape.
            continue
        assert isinstance(node.value, ast.Tuple), "_relay_addrs stores a tuple"
        if any(isinstance(e, ast.Starred) for e in node.value.elts):
            # A splat rewrites one field of an existing entry (the stale-nonce
            # refresh) and preserves its shape by construction.
            continue
        assert len(node.value.elts) == 8, (
            f"_relay_addrs assignment at line {node.lineno} stores "
            f"{len(node.value.elts)} fields; all 8 are required "
            "(relay_ip, relay_port, realm, nonce, t_host, t_port, key, user)"
        )


def test_trickled_candidates_are_permissioned_not_just_the_answer_sdp(monkeypatch):
    """The answer SDP only carries the camera's private host candidate.

    A TURN permission is matched against the peer's source address as the
    relay sees it, so a permission for an RFC1918 host candidate authorises an
    address that can never arrive. The reachable addresses (srflx, relay) only
    ever arrive by trickle, and none of them may be filtered out by type.
    """
    from tests.open_helpers import drive_trickle

    lines = [
        "candidate:1 1 udp 2130706431 192.168.1.20 40000 typ host",
        "candidate:2 1 udp 1694498815 203.0.113.7 40001 typ srflx",
        "candidate:3 1 udp 16777215 198.51.100.9 40002 typ relay",
        "candidate:3 1 udp 16777215 198.51.100.9 40002 typ relay",  # repeat
    ]
    installed, uc, _orig = drive_trickle(monkeypatch, lines)
    assert [(ip, port) for ip, port, _w in installed] == [
        ("192.168.1.20", 40000),
        ("203.0.113.7", 40001),
        ("198.51.100.9", 40002),
    ]
    assert uc["cands"] == [(ip, port) for ip, port, _w in installed], (
        "and each one joins the nomination set"
    )


def test_trickle_regex_parses_real_camera_candidates():
    """Byte-for-byte against candidate lines a live A001513 actually sent.

    Through the shared parser now: the trickle path used to carry its own copy
    of the pattern, which could diverge from the one the answer path nominates
    with. This test reached into the source for that copy; it exercises the
    shared parser directly instead, and the "same parser" guard in
    test_nominates_as_soon_as_the_answer_lands.py keeps the two from splitting
    again.
    """
    from aidot_cameras.camera.sdes_open import _parse_candidate_line

    lines = {
        "host": "candidate:0 1 udp 2130706431 203.0.113.9 48776 typ host raddr 0.0.0.0 rport 0 generation 0 network-cost 999",
        "srflx": "candidate:1 1 udp 1694498815 198.51.100.7 34686 typ srflx raddr 0.0.0.0 rport 0 generation 0 network-cost 999",
        "relay": "candidate:2 1 udp 16777215 198.51.100.20 21449 typ relay raddr 0.0.0.0 rport 0 generation 0 network-cost 999",
    }
    expected = {
        "host": ("203.0.113.9", 48776),
        "srflx": ("198.51.100.7", 34686),
        "relay": ("198.51.100.20", 21449),
    }
    for kind, line in lines.items():
        parsed = _parse_candidate_line(line)
        assert parsed, f"{kind} candidate must parse: {line}"
        assert (parsed[0], parsed[1]) == expected[kind]
        assert parsed[2] == kind
        # The same line as it appears in an answer SDP, which is where the
        # other four callers read it from.
        assert _parse_candidate_line("a=" + line) == parsed


def test_nomination_set_is_rebound_not_mutated(monkeypatch):
    """The bridge thread iterates this list; mutating it under them raises."""
    from tests.open_helpers import drive_trickle

    host = ("192.168.1.20", 40000)
    _inst, uc, original = drive_trickle(
        monkeypatch,
        ["candidate:2 1 udp 1694498815 203.0.113.7 40001 typ srflx"],
        uc_cands=[host],
    )
    assert original == [host], "the list the bridge held was never mutated"
    assert uc["cands"] is not original
    assert uc["cands"] == [host, ("203.0.113.7", 40001)]


def test_trickle_observer_does_not_consume_the_dtls_queue():
    """SDES must not steal candidates the DTLS fallback still needs: the
    handler records each trickled candidate AND still queues it for DTLS."""
    import json

    from tests.open_helpers import mqtt_handler

    handler, _owner, loop, seen = mqtt_handler(peer_id="pid-1")
    cand = {"candidate": "candidate:2 1 udp 1 203.0.113.7 40001 typ srflx"}
    handler(
        "iot/v1/c/user-1/IPC/iceCandidateReq",
        json.dumps(
            {
                "method": "iceCandidateReq",
                "payload": {"peerid": "pid-1", "candidate": cand},
            }
        ),
    )
    assert seen == [cand], "the SDES observer records it"
    assert any(args == (cand,) for _fn, args in loop.calls), (
        "and the DTLS queue still receives it"
    )
