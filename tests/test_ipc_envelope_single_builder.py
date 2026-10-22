"""Every IPC request rides in one envelope, built in one place.

Eleven call sites across the DTLS and SDES opens spelled the same eight-key
dict inline. The literal they used is kept here verbatim and the builder has
to produce the same JSON text, key order included, with and without the
numeric user id.
"""

import json
import time

import pytest

from aidot_cameras.camera.protocol import ipc_envelope


def _previous(method, device_id, user_id, _numeric_uid_raw, seq, payload):
    return json.dumps(
        {
            "method": method,
            "service": "IPC",
            "devId": device_id,
            "srcAddr": f"0.{user_id}",
            "seq": seq,
            "tst": int(time.time() * 1000),
            **({"userId": _numeric_uid_raw} if _numeric_uid_raw is not None else {}),
            "payload": payload,
        }
    )


@pytest.mark.parametrize("numeric_uid", [None, 4242, "4242"])
@pytest.mark.parametrize(
    "method,payload",
    [
        ("getIceConfigReq", {"devId": "d1", "userId": "u1"}),
        ("webrtcReq", {"dstAddr": "d1", "wPayload": {"peerid": "p", "sdp": "v=0"}}),
        ("iceCandidateReq", {"candidate": {"candidate": "candidate:1 1 udp"}}),
    ],
)
def test_the_builder_matches_the_previous_literal(
    monkeypatch, numeric_uid, method, payload
):
    monkeypatch.setattr(time, "time", lambda: 1760000000.123)
    got = ipc_envelope(
        method, "d1", "u1", numeric_uid, seq="ap1234567", payload=payload
    )
    assert got == _previous(method, "d1", "u1", numeric_uid, "ap1234567", payload)
    assert list(json.loads(got)) == (
        ["method", "service", "devId", "srcAddr", "seq", "tst", "payload"]
        if numeric_uid is None
        else [
            "method",
            "service",
            "devId",
            "srcAddr",
            "seq",
            "tst",
            "userId",
            "payload",
        ]
    )


def test_no_envelope_is_spelled_out_inline():
    """The open paths build every IPC envelope through ipc_envelope: no dict
    literal there carries the envelope's millisecond `tst` field by hand. (The
    MQTT connect and the wake request have their own, smaller shapes.)"""
    import ast

    from tests.ast_guards import module_tree

    inline = [
        f"{mod}:{n.lineno}"
        for mod in (
            "aidot_cameras.camera.webrtc_open",
            "aidot_cameras.camera.sdes_open",
        )
        for n in ast.walk(module_tree(mod))
        if isinstance(n, ast.Dict)
        and any(isinstance(k, ast.Constant) and k.value == "tst" for k in n.keys)
    ]
    assert not inline, inline
