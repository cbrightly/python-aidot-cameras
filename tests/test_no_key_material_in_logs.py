"""Key material never reaches the log.

Home Assistant users turn this integration's logger to DEBUG when something
is wrong and paste the result into public issues. Four sites used to print
secrets on that path: the SDES media PSK, the camera's data-channel SRTP key
inside its echoed ``m=application`` section, raw MQTT payloads (which carry
our own PSK and the TURN credentials), and a camera answer SDP with its ICE
password. These tests pin the fix at each site by reading the source, the
way ``test_upstream_secrets_redacted.py`` pins upstream's lines, so a
reintroduction fails loudly rather than leaking quietly.
"""

import inspect

from aidot_cameras.camera import client as camera_client
from aidot_cameras.camera import sdes_open, webrtc_open
from aidot_cameras.camera.protocol import mask_sdp_secrets

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


def test_sdes_open_logs_the_psk_as_a_fingerprint():
    src = inspect.getsource(sdes_open)
    i = src.index('"sdes: offer key=%s psk=%s"')
    call = src[i : i + 200]
    assert "_key_fingerprint(_psk_value_req)" in call
    assert "\n            _psk_value_req,\n" not in call


def test_sdes_open_masks_the_echoed_data_channel_section():
    src = inspect.getsource(sdes_open)
    i = src.index("DC probe ACCEPTED: camera answered m=application")
    assert "mask_sdp_secrets(" in src[i : i + 300]


def test_sdes_open_redacts_the_ffmpeg_argv():
    src = inspect.getsource(sdes_open)
    i = src.index('"SDES ffmpeg cmd: %s"')
    assert "redact_url(t)" in src[i : i + 200]


def test_webrtc_open_never_logs_a_payload_body():
    src = inspect.getsource(webrtc_open)
    assert "%.400s" not in src
    i = src.index('"webrtc rx  topic=%s')
    assert "len(payload_str)" in src[i : i + 120]


def test_webrtc_open_masks_the_answer_sdp_it_logs_on_failure():
    src = inspect.getsource(webrtc_open)
    i = src.index("--- camera answer SDP ---")
    assert "mask_sdp_secrets(_ans_sdp)" in src[i : i + 300]


def test_device_detail_bodies_are_logged_as_keys():
    src = inspect.getsource(camera_client)
    assert "returned no UID for %s. body=%s" not in src
