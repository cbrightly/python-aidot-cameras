"""The AAC track the direct publish adds so Home Assistant's HLS has audio."""

import struct

import pytest

from aidot_cameras.camera import aac_track as at


def test_audio_specific_config_is_aac_lc_48k_mono():
    # 5 bits object type 2 (AAC-LC), 4 bits index 3 (48000), 4 bits 1 channel
    assert at.audio_specific_config() == bytes.fromhex("1188")
    assert at.audio_specific_config(8000, 1) == bytes.fromhex("1588")


def test_fmtp_names_the_hbr_mode_and_the_config():
    fmtp = at.aac_fmtp()
    assert "mode=AAC-hbr" in fmtp
    assert "sizelength=13;indexlength=3;indexdeltalength=3" in fmtp
    assert fmtp.endswith("config=1188")


def test_packetize_writes_one_au_header():
    frame = b"\x21" * 300
    payload = at.packetize_aac(frame)
    headers_len_bits, au_header = struct.unpack("!HH", payload[:4])
    assert headers_len_bits == 16
    assert au_header >> 3 == 300 and au_header & 0x7 == 0
    assert payload[4:] == frame


@pytest.mark.parametrize(
    "val,on",
    [
        (None, True),
        ("1", True),
        ("yes", True),
        ("0", False),
        ("false", False),
        ("off", False),
        ("no", False),
        ("garbage", True),
    ],
)
def test_kill_switch(monkeypatch, val, on):
    if val is None:
        monkeypatch.delenv("AIDOT_PUBLISH_AAC", raising=False)
    else:
        monkeypatch.setenv("AIDOT_PUBLISH_AAC", val)
    assert at.publish_aac_enabled() is on
