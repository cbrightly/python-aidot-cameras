"""One template for the SDP ffmpeg reads from the SDES bridge.

The first launch, the key-restart rebuild and both branches of the bridge's
rewrite each spelled the template out. The four previous literals are copied
below verbatim and the shared builder must reproduce each byte for byte,
including the one real difference between them: the first launch binds
0.0.0.0 while the later rewrites bind 127.0.0.1.
"""

from aidot_cameras.camera.sdes_open import _build_restart_sdp, _ffmpeg_rx_sdp

TS, A, V = 1760000000, 40001, 40003
KA, KV = "a" * 40, "b" * 40


def _previous_first_launch(ts, audio_port, video_port, srtp_key_audio, srtp_key_video):
    return (
        "v=0\r\n"
        f"o=- {ts} {ts} IN IP4 0.0.0.0\r\n"
        "s=aidot-sdes-rx\r\n"
        "t=0 0\r\n"
        f"m=audio {audio_port} RTP/SAVP 0 8\r\n"
        "c=IN IP4 0.0.0.0\r\n"
        f"a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:{srtp_key_audio}\r\n"
        "a=rtpmap:0 PCMU/8000\r\n"
        "a=rtpmap:8 PCMA/8000\r\n"
        "a=rtcp-mux\r\n"
        f"m=video {video_port} RTP/SAVP 96 97\r\n"
        "c=IN IP4 0.0.0.0\r\n"
        f"a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:{srtp_key_video}\r\n"
        "a=rtpmap:96 H264/90000\r\n"
        "a=fmtp:96 level-asymmetry-allowed=1;packetization-mode=1;profile-level-id=42e01f\r\n"
        "a=rtpmap:97 H265/90000\r\n"
        "a=fmtp:97 level-id=93\r\n"
        "a=rtcp-mux\r\n"
    )


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


def _previous_bridge_sdes(
    _ts_br, _lo_audio_port, _lo_video_port, srtp_key_audio, srtp_key_video
):
    return (
        "v=0\r\n"
        f"o=- {_ts_br} {_ts_br} IN IP4 0.0.0.0\r\n"
        "s=aidot-sdes-rx\r\n"
        "t=0 0\r\n"
        f"m=audio {_lo_audio_port} RTP/SAVP 0 8\r\n"
        "c=IN IP4 127.0.0.1\r\n"
        f"a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:{srtp_key_audio}\r\n"
        "a=rtpmap:0 PCMU/8000\r\n"
        "a=rtpmap:8 PCMA/8000\r\n"
        "a=rtcp-mux\r\n"
        f"m=video {_lo_video_port} RTP/SAVP 96 97\r\n"
        "c=IN IP4 127.0.0.1\r\n"
        f"a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:{srtp_key_video}\r\n"
        "a=rtpmap:96 H264/90000\r\n"
        "a=fmtp:96 level-asymmetry-allowed=1;packetization-mode=1;"
        "profile-level-id=42e01f\r\n"
        "a=rtpmap:97 H265/90000\r\n"
        "a=fmtp:97 level-id=93\r\n"
        "a=rtcp-mux\r\n"
    )


def _previous_restart(
    ts, lo_audio_port, lo_video_port, use_plain_rtp, srtp_key_audio, srtp_key_video
):
    _proto = "RTP/AVP" if use_plain_rtp else "RTP/SAVP"

    def _crypto(key):
        if use_plain_rtp:
            return ""
        return f"a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:{key}\r\n"

    return (
        "v=0\r\n"
        f"o=- {ts} {ts} IN IP4 0.0.0.0\r\n"
        "s=aidot-sdes-rx\r\n"
        "t=0 0\r\n"
        f"m=audio {lo_audio_port} {_proto} 0 8\r\n"
        "c=IN IP4 127.0.0.1\r\n"
        f"{_crypto(srtp_key_audio)}"
        "a=rtpmap:0 PCMU/8000\r\n"
        "a=rtpmap:8 PCMA/8000\r\n"
        "a=rtcp-mux\r\n"
        f"m=video {lo_video_port} {_proto} 96 97\r\n"
        "c=IN IP4 127.0.0.1\r\n"
        f"{_crypto(srtp_key_video)}"
        "a=rtpmap:96 H264/90000\r\n"
        "a=fmtp:96 level-asymmetry-allowed=1;packetization-mode=1;"
        "profile-level-id=42e01f\r\n"
        "a=rtpmap:97 H265/90000\r\n"
        "a=fmtp:97 level-id=93\r\n"
        "a=rtcp-mux\r\n"
    )


def _build(plain, conn_ip, name):
    return _ffmpeg_rx_sdp(
        ts=TS,
        audio_port=A,
        video_port=V,
        plain_rtp=plain,
        srtp_key_audio=KA,
        srtp_key_video=KV,
        conn_ip=conn_ip,
        session_name=name,
    )


def test_first_launch_is_byte_identical():
    assert _build(False, "0.0.0.0", "aidot-sdes-rx") == _previous_first_launch(
        TS, A, V, KA, KV
    )


def test_bridge_rewrite_plain_is_byte_identical():
    assert _build(True, "127.0.0.1", "aidot-tutk-rx") == _previous_bridge_plain(
        TS, A, V
    )


def test_bridge_rewrite_sdes_is_byte_identical():
    assert _build(False, "127.0.0.1", "aidot-sdes-rx") == _previous_bridge_sdes(
        TS, A, V, KA, KV
    )


def test_restart_sdp_is_byte_identical_for_both_transports():
    for plain in (True, False):
        got = _build_restart_sdp(
            ts=TS,
            lo_audio_port=A,
            lo_video_port=V,
            use_plain_rtp=plain,
            srtp_key_audio=KA,
            srtp_key_video=KV,
        )
        # The restart narrows payload types after the template; with nothing
        # known it keeps both, so the narrowing is the identity here.
        assert got == _previous_restart(TS, A, V, plain, KA, KV)


def test_no_template_is_spelled_out_twice():
    import pathlib

    src = (
        pathlib.Path(__file__).resolve().parent.parent
        / "aidot_cameras"
        / "camera"
        / "sdes_open.py"
    ).read_text()
    # The camera-facing offer and answer keep their own templates (ICE, mids,
    # candidates); only the ffmpeg-facing one is checked here.
    assert src.count('"s=aidot-sdes-rx\\r\\n"') == 0
    assert src.count('"s=aidot-tutk-rx\\r\\n"') == 0
