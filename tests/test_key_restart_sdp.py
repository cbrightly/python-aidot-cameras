"""The SRTP key-restart must rewrite the SDP the same way the primary path does.

It rebuilds the ffmpeg SDP from scratch and relaunches. Two things it got wrong,
each undoing a shipped fix:

- Transport. Every SDES model this library supports is in `_use_plain_rtp`: the
  bridge decrypts and forwards PLAIN RTP, so the primary SDP is RTP/AVP with no
  a=crypto. Writing RTP/SAVP makes ffmpeg authenticate already-decrypted packets,
  every HMAC check fails, and a working stream drops to zero bytes mid-session.
- Payload types. Hard-coding "0 8" and "96 97" discards the narrowing, and ffmpeg
  binds the FIRST type per line - so an H.265 camera loses all video, and with it
  the PAT/PMT and therefore the entire output.
"""
import re

import aidot_cameras.camera.sdes_open as so


def _restart_block() -> str:
    src = so.__loader__.get_source("aidot_cameras.camera.sdes_open")
    i = src.index("_new_sdp = (")
    return src[i:i + 3000]


def test_restart_respects_plain_rtp_transport():
    block = _restart_block()
    assert "_proto" in block, "transport must follow _use_plain_rtp, not be fixed"
    assert 'f"m=audio {_lo_audio_port} {_proto}' in block
    assert 'f"m=video {_lo_video_port} {_proto}' in block


def test_restart_omits_crypto_for_plain_rtp():
    block = _restart_block()
    assert "_crypto(" in block, "a=crypto must be conditional on the transport"
    assert "a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:{srtp_key_audio}" not in block


def test_restart_narrows_the_payload_types():
    block = _restart_block()
    assert "narrow_sdp_payload_types(" in block
    assert "keep_video=" in block and "keep_audio=" in block


def test_first_media_wait_covers_the_documented_cold_start():
    # The repo documents a 25-70s cold start for battery cameras in several
    # places. A 45s deadline sat inside that range, so a camera at the slow end
    # launched with no payload types known - the exact failure the wait exists to
    # prevent.
    assert so._FIRST_MEDIA_WAIT_S >= 70.0


def test_narrowing_still_produces_a_single_payload_type_per_line():
    sdp = (
        "m=audio 1000 RTP/AVP 0 8\r\n"
        "a=rtpmap:0 PCMU/8000\r\na=rtpmap:8 PCMA/8000\r\n"
        "m=video 1002 RTP/AVP 96 97\r\n"
        "a=rtpmap:96 H264/90000\r\na=rtpmap:97 H265/90000\r\n"
    )
    out = so.narrow_sdp_payload_types(sdp, keep_video=97, keep_audio=8)
    assert re.search(r"m=audio 1000 RTP/AVP 8\r\n", out)
    assert re.search(r"m=video 1002 RTP/AVP 97\r\n", out)
