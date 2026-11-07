"""One template for the SDP ffmpeg reads from the SDES bridge.

The first launch and the bridge's rewrite each spelled the template out; the
literal the bridge used is copied below verbatim and the shared builder must
reproduce it byte for byte, including the one real difference between the two
sites: the first launch binds 0.0.0.0 while the rewrite binds 127.0.0.1.

The bridge is the only decryptor, so the template is plain RTP/AVP with no
a=crypto. An RTP/SAVP variant keyed from the offer served SDES models outside
_PLAIN_RTP_MODELS; no supported camera used it, and the open now refuses such a
model instead (see test_sdes_open_refuses_an_undecryptable_model).
"""

import re

import aidot_cameras.camera.sdes_open as so
from aidot_cameras.camera.sdes_open import _ffmpeg_rx_sdp

TS, A, V = 1760000000, 40001, 40003


def _previous_bridge_plain(_ts_br, _lo_audio_port, _lo_video_port):
    return (
        "v=0\r\n"
        f"o=- {_ts_br} {_ts_br} IN IP4 0.0.0.0\r\n"
        "s=aidot-tutk-rx\r\n"
        "t=0 0\r\n"
        f"m=audio {_lo_audio_port} RTP/AVP 0 8\r\n"
        "c=IN IP4 127.0.0.1\r\n"
        "a=rtpmap:0 PCMU/8000\r\n"
        "a=rtpmap:8 PCMA/8000\r\n"
        "a=rtcp-mux\r\n"
        f"m=video {_lo_video_port} RTP/AVP 96 97\r\n"
        "c=IN IP4 127.0.0.1\r\n"
        "a=rtpmap:96 H264/90000\r\n"
        "a=fmtp:96 level-asymmetry-allowed=1;packetization-mode=1;"
        "profile-level-id=42e01f\r\n"
        "a=rtpmap:97 H265/90000\r\n"
        "a=fmtp:97 level-id=93\r\n"
        "a=rtcp-mux\r\n"
    )


def _build(conn_ip, name):
    return _ffmpeg_rx_sdp(
        ts=TS, audio_port=A, video_port=V, conn_ip=conn_ip, session_name=name
    )


def test_bridge_rewrite_is_byte_identical():
    assert _build("127.0.0.1", "aidot-tutk-rx") == _previous_bridge_plain(TS, A, V)


def test_first_launch_differs_only_in_the_bind_address_and_name():
    first = _build("0.0.0.0", "aidot-sdes-rx")
    assert first == _previous_bridge_plain(TS, A, V).replace(
        "c=IN IP4 127.0.0.1", "c=IN IP4 0.0.0.0"
    ).replace("s=aidot-tutk-rx", "s=aidot-sdes-rx")


def test_no_crypto_and_no_savp_anywhere():
    for sdp in (_build("0.0.0.0", "x"), _build("127.0.0.1", "y")):
        assert "a=crypto" not in sdp
        assert "SAVP" not in sdp
        assert len(re.findall(r"^m=\w+ \d+ RTP/AVP ", sdp, re.M)) == 2


def test_no_template_is_spelled_out_twice():
    """The ffmpeg-facing receive SDP has one builder (_ffmpeg_rx_sdp); no
    other literal in the open spells its session line out again. (The
    camera-facing offer keeps its own template.)"""
    import ast

    from tests.ast_guards import module_tree

    spelled = [
        n.lineno
        for n in ast.walk(module_tree("aidot_cameras.camera.sdes_open"))
        if isinstance(n, ast.Constant)
        and isinstance(n.value, str)
        and ("s=aidot-sdes-rx" in n.value or "s=aidot-tutk-rx" in n.value)
    ]
    assert not spelled, f"a receive-SDP template is spelled out at {spelled}"


def test_first_media_wait_covers_the_documented_cold_start():
    # The repo documents a 25-70s cold start for battery cameras in several
    # places. A 45s deadline sat inside that range, so a camera at the slow end
    # launched with no payload types known - the exact failure the wait exists to
    # prevent.
    assert so._FIRST_MEDIA_WAIT_S >= 70.0


def test_narrowing_still_produces_a_single_payload_type_per_line():
    out = so.narrow_sdp_payload_types(
        _build("127.0.0.1", "aidot-tutk-rx"), keep_video=97, keep_audio=8
    )
    assert re.search(rf"m=audio {A} RTP/AVP 8\r\n", out)
    assert re.search(rf"m=video {V} RTP/AVP 97\r\n", out)
