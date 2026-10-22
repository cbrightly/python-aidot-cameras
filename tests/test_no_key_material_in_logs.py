"""Key material never reaches the log.

Home Assistant users turn this integration's logger to DEBUG when something
is wrong and paste the result into public issues. Four sites used to print
secrets on that path: the SDES media PSK, the camera's data-channel SRTP key
inside its echoed ``m=application`` section, raw MQTT payloads (which carry
our own PSK and the TURN credentials), and a camera answer SDP with its ICE
password. The sites are guarded by one structural invariant over every log call on
the camera path, so a reintroduction - at those sites or a new one - fails
loudly rather than leaking quietly.
"""

import ast
import re

from aidot_cameras.camera.protocol import mask_sdp_secrets
from tests.ast_guards import calls_to, module_tree, name_of, names_in, string_parts

_SDP = (
    "v=0\r\n"
    "m=audio 9 RTP/SAVPF 8\r\n"
    "a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:Zm9vYmFyYmF6cXV1eHF1dXhxdXV4cXV1eHF1dQ==\r\n"
    "a=ice-ufrag:abcd\r\n"
    "a=ice-pwd:verysecretpassword1234\r\n"
    "a=psk:1234567890abcdef\r\n"
    "m=application 9 SCTP webrtc-datachannel\r\n"
    "a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:c2Vjb25ka2V5c2Vjb25ka2V5c2Vjb25kMTI=\r\n"
)


def test_mask_sdp_secrets_masks_every_secret_attribute():
    out = mask_sdp_secrets(_SDP)
    for secret in (
        "Zm9vYmFyYmF6",
        "c2Vjb25ka2V5",
        "verysecretpassword1234",
        "1234567890abcdef",
    ):
        assert secret not in out
    # The attribute names and everything else survive, so the line still
    # reads as what it was.
    assert "a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:<masked>" in out
    assert "a=ice-pwd:<masked>" in out
    assert "a=psk:<masked>" in out
    assert "a=ice-ufrag:abcd" in out
    assert out.count("\r\n") == _SDP.count("\r\n")


def test_mask_sdp_secrets_leaves_an_innocent_sdp_alone():
    sdp = "v=0\nm=video 9 RTP/SAVPF 96\na=rtpmap:96 H264/90000\n"
    assert mask_sdp_secrets(sdp) == sdp


# Names that hold key material, or text that carries it: SDES PSKs and SRTP
# keys, SDPs (a=crypto inline keys, ICE passwords), raw MQTT payloads (our
# own PSK and the TURN credentials) and passwords or tokens.
_SECRET = re.compile(
    r"psk|srtp_key|key_audio|key_b64|^payload_str$|sdp$|sdp_text|ans_lines"
    r"|password|token|secret",
    re.I,
)
# What turns one into something loggable: a fingerprint, a masked SDP, a
# redacted URL, a length, or a one-word summary.
_SANITIZERS = {
    "_key_fingerprint",
    "mask_sdp_secrets",
    "redact_url",
    "len",
    "_sdp_transport",
    "_srtp_tx_key_note",
}
_LOG_METHODS = {"debug", "info", "warning", "error", "exception", "critical"}
_MODULES = (
    "aidot_cameras.camera.client",
    "aidot_cameras.camera.sdes_open",
    "aidot_cameras.camera.webrtc_open",
)


def _is_log_call(call: ast.Call) -> bool:
    f = call.func
    if isinstance(f, ast.Attribute) and isinstance(f.value, ast.Name):
        if f.value.id == "_LOGGER" and f.attr in _LOG_METHODS:
            return True
    return name_of(call) in ("_status", "_trace", "status", "trace")


def _unsanitized(node: ast.AST):
    """Secret-named values reaching the log line from ``node`` unwrapped."""
    if isinstance(node, ast.Call):
        if name_of(node) in _SANITIZERS:
            return
        for arg in [*node.args, *(k.value for k in node.keywords)]:
            yield from _unsanitized(arg)
        if isinstance(node.func, ast.Attribute):
            yield from _unsanitized(node.func.value)
        return
    if isinstance(node, ast.IfExp):  # the test picks a branch; it is not logged
        yield from _unsanitized(node.body)
        yield from _unsanitized(node.orelse)
        return
    if isinstance(node, (ast.Name, ast.Attribute)):
        name = node.id if isinstance(node, ast.Name) else node.attr
        if _SECRET.search(name) and not name.endswith("exc"):
            yield name
    for child in ast.iter_child_nodes(node):
        yield from _unsanitized(child)


def _log_calls():
    for modname in _MODULES:
        for call in ast.walk(module_tree(modname)):
            if isinstance(call, ast.Call) and _is_log_call(call):
                yield modname, call


def test_no_log_call_hands_over_key_material():
    """Every log line on the camera path that mentions a key, an SDP, a raw
    MQTT payload or a credential passes it through a fingerprint, the SDP
    mask, the URL redaction or len() first.

    This is the one invariant the site pins below used to check one line at
    a time: the SDES PSK, the echoed data-channel section, the MQTT payload
    body and the camera answer SDP.
    """
    leaks = [
        f"{modname}:{call.lineno}: {sorted(set(names))}"
        for modname, call in _log_calls()
        if (
            names := [
                n
                for a in [*call.args, *(k.value for k in call.keywords)]
                for n in _unsanitized(a)
            ]
        )
    ]
    assert not leaks, "\n".join(leaks)


def test_the_invariant_sees_the_sites_it_replaced():
    """Guard the guard: the sites the old pins named are log calls this
    check walks, each still reaching a secret-named value through a
    sanitizer - so the invariant above is not passing on nothing."""
    by_text = {}
    for modname, call in _log_calls():
        text = string_parts(call.args[0]) if call.args else ""
        by_text.setdefault(text, []).append(call)
    expected = {
        "sdes: offer key=%s psk=%s": "_key_fingerprint",
        "webrtc: SDP offer (first 500 chars):\n%s": "mask_sdp_secrets",
        "webrtc rx  topic=%s  bytes=%d": "len",
    }
    for text, sanitizer in expected.items():
        assert text in by_text, f"the {text!r} log call moved"
        assert any(calls_to(c, sanitizer) for c in by_text[text]), text


def test_sdes_open_redacts_the_ffmpeg_argv():
    """The serve command line carries the push URL; every log line that
    prints a command list redacts its URLs."""
    sites = [
        call
        for _m, call in _log_calls()
        if any("cmd" in names_in(a) for a in call.args[1:])
    ]
    assert sites, "the ffmpeg command is no longer logged - re-point this test"
    for call in sites:
        assert calls_to(call, "redact_url"), f"line {call.lineno}: argv not redacted"


# --------------------------------------------------------------------------- #
# A filter, not call-site masking: found live on 2026-10-18
# --------------------------------------------------------------------------- #
#
# Masking at each call site shipped in 1.0.0rc53 and missed three places that
# log a whole SDP - our own "SDP offer (first 500 chars)" line and, inside the
# vendored aiortc, setLocalDescription and setRemoteDescription. A real Home
# Assistant instance with the library at DEBUG printed 18 ICE passwords. The
# filter below catches every site, including ones added later.


def test_sdp_redaction_masks_all_three_credential_attributes():
    from aidot_cameras._log_redaction import redact_sdp_secrets

    sdp = (
        "v=0\r\n"
        "a=ice-ufrag:xu0G\r\n"
        "a=ice-pwd:tl5mID0sjY2OjnmVLs50er\r\n"
        "a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:Zm9vYmFyYmF6cXV1eA==\r\n"
        "a=psk:deadbeef1234567890\r\n"
        "a=mid:0\r\n"
    )
    out = redact_sdp_secrets(sdp)
    for secret in (
        "tl5mID0sjY2OjnmVLs50er",
        "Zm9vYmFyYmF6cXV1eA==",
        "deadbeef1234567890",
    ):
        assert secret not in out
    # Everything that is not key material survives, so the log stays useful.
    assert "a=ice-ufrag:xu0G" in out
    assert "a=mid:0" in out
    assert "a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:<masked>" in out


def test_the_vendored_aiortc_logger_is_covered():
    """aiortc logs the full offer and answer at DEBUG; it is vendored, so its
    logger is ours to filter."""
    import logging

    from aidot_cameras._log_redaction import (
        SDP_LOGGERS,
        RedactSdpFilter,
        install_upstream_redaction,
    )

    assert "aidot_cameras._vendor.aiortc.rtcpeerconnection" in SDP_LOGGERS
    install_upstream_redaction()
    lg = logging.getLogger("aidot_cameras._vendor.aiortc.rtcpeerconnection")
    assert any(isinstance(f, RedactSdpFilter) for f in lg.filters)

    sdp = "v=0\r\na=ice-pwd:supersecreticepassword\r\n"
    rec = lg.makeRecord(
        lg.name, logging.DEBUG, "f", 1, "setLocalDescription(offer)\n%s", (sdp,), None
    )
    for f in lg.filters:
        f.filter(rec)
    assert "supersecreticepassword" not in rec.getMessage()


def test_installing_the_filters_is_idempotent():
    import logging

    from aidot_cameras._log_redaction import (
        SDP_LOGGERS,
        RedactSdpFilter,
        install_upstream_redaction,
    )

    for _ in range(3):
        install_upstream_redaction()
    for name in SDP_LOGGERS:
        lg = logging.getLogger(name)
        assert sum(isinstance(f, RedactSdpFilter) for f in lg.filters) == 1, name
