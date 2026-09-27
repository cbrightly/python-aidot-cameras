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


S = at.ALAW_SILENCE


def _flat(blocks):
    return b"".join(blocks)


def test_continuous_audio_passes_straight_through():
    p = at.AacPacer()
    a = p.feed(b"\x01" * 160, 1000, 0.00)
    b = p.feed(b"\x02" * 160, 1160, 0.02)
    assert _flat(a + b) == b"\x01" * 160 + b"\x02" * 160
    assert p.silence_samples == 0 and p.trimmed_samples == 0


def test_a_gap_in_the_camera_stamps_is_filled_with_silence():
    p = at.AacPacer()
    p.feed(b"\x01" * 160, 1000, 0.0)
    out = p.feed(b"\x02" * 160, 1000 + 160 + 800, 0.12)  # 100 ms missing
    assert _flat(out) == S * 800 + b"\x02" * 160
    assert p.silence_samples == 800


def test_an_overlapping_packet_is_trimmed_never_stepped_back():
    p = at.AacPacer()
    p.feed(b"\x01" * 160, 1000, 0.0)
    out = p.feed(b"\x02" * 160, 1080, 0.02)  # overlaps 80 samples
    assert _flat(out) == b"\x02" * 80
    assert p.trimmed_samples == 80
    assert p.feed(b"\x03" * 160, 1000, 0.03) == []  # entirely old: dropped
    assert p.trimmed_samples == 80 + 160


def test_the_32_bit_timestamp_wraps():
    p = at.AacPacer()
    p.feed(b"\x01" * 160, 0xFFFFFF60, 0.0)  # ends exactly at the wrap
    out = p.feed(b"\x02" * 160, 0, 0.02)
    assert _flat(out) == b"\x02" * 160 and p.silence_samples == 0


def test_a_huge_forward_jump_reanchors_instead_of_emitting_minutes_of_silence():
    p = at.AacPacer()
    p.feed(b"\x01" * 160, 1000, 0.0)
    out = p.feed(b"\x02" * 160, 1000 + 160 + 8000 * 60, 0.02)
    assert _flat(out) == b"\x02" * 160
    assert p.reanchors == 1 and p.silence_samples == 0


def test_idle_fill_keeps_the_track_alive_when_the_camera_sends_no_audio():
    p = at.AacPacer()
    assert p.tick(10.0) == []  # first tick only starts the clock
    assert p.tick(10.3) == []  # under AAC_IDLE_FILL_S
    out = p.tick(10.6)
    assert _flat(out) == S * 4800  # 0.6 s at 8 kHz, measured from 10.0
    assert p.tick(10.7) == []  # just filled - not idle again yet


def test_idle_fill_after_audio_stops_then_resumes_without_a_backward_step():
    p = at.AacPacer()
    p.feed(b"\x01" * 160, 1000, 0.00)
    filled = _flat(p.tick(1.00))  # 1 s of silence since the last packet
    assert filled == S * 8000
    # The camera resumes; its first packet lies wholly inside the filled silence.
    out = _flat(p.feed(b"\x02" * 160, 1000 + 160 + 7800, 1.00))
    assert out == b""
    # The next packet overlaps the fill by 40 samples: only its tail is kept.
    out = _flat(p.feed(b"\x02" * 160, 1000 + 160 + 7960, 1.02))
    assert out == b"\x02" * 120
    # From here on the camera's stamps are exactly continuous.
    out = _flat(p.feed(b"\x02" * 160, 1000 + 160 + 8120, 1.04))
    assert out == b"\x02" * 160


def test_ticks_during_continuous_audio_add_nothing():
    p = at.AacPacer()
    for i in range(50):
        p.feed(b"\x01" * 160, 1000 + 160 * i, i * 0.02)
        assert p.tick(i * 0.02 + 0.01) == []


def _sine_alaw(seconds, freq=440):
    import numpy as np

    from aidot_cameras.camera.rtsp_publish import _alaw_encode

    n = int(seconds * 8000)
    pcm = (np.sin(2 * np.pi * freq * np.arange(n) / 8000) * 8000).astype(int)
    return bytes(_alaw_encode(int(v)) for v in pcm)


def test_track_emits_1024_steps_and_decodes_back_to_the_same_duration():
    import av

    trk = at.AacTrack()
    alaw = _sine_alaw(2.0)
    pkts = []
    for i in range(0, len(alaw), 160):
        pkts += trk.feed(alaw[i : i + 160], 5000 + i, i / 8000)
    ts = [t for _, t, _ in pkts]
    assert all(((b - a) & 0xFFFFFFFF) == 1024 for a, b in zip(ts, ts[1:]))
    seqs = [s for s, _, _ in pkts]
    assert all(((b - a) & 0xFFFF) == 1 for a, b in zip(seqs, seqs[1:]))
    # 2 s at 48 kHz = 93.75 AUs; the encoder holds back its priming delay.
    assert 88 <= len(pkts) <= 94
    dec = av.CodecContext.create("aac", "r")
    dec.extradata = at.audio_specific_config()
    samples = 0
    for _, _, payload in pkts:
        for fr in dec.decode(av.Packet(payload[4:])):
            samples += fr.samples
    assert abs(samples / 48000 - len(pkts) * 1024 / 48000) < 0.05


def test_idle_track_still_produces_packets():
    trk = at.AacTrack()
    trk.tick(0.0)
    pkts = trk.tick(1.0)
    assert len(pkts) >= 40  # ~46 AUs per second of silence, minus priming


def test_an_encoder_failure_stops_only_the_aac_track(caplog):
    class Broken:
        def encode(self, alaw):
            raise RuntimeError("boom")

    trk = at.AacTrack(encoder=Broken())
    assert trk.feed(b"\x01" * 160, 0, 0.0) == []
    assert trk.failed
    assert trk.feed(b"\x01" * 160, 160, 0.02) == []
    assert sum("AAC track stopped" in r.message for r in caplog.records) == 1


def test_a_pacer_failure_also_stops_only_the_aac_track(caplog):
    class BrokenPacer:
        def feed(self, alaw, pcma_ts, now):
            raise RuntimeError("pacer boom")

        def tick(self, now):
            raise RuntimeError("pacer boom")

    class Encoder:
        def encode(self, alaw):
            return []

    trk = at.AacTrack(encoder=Encoder(), pacer=BrokenPacer())
    assert trk.feed(b"\x01" * 160, 0, 0.0) == []
    assert trk.failed
    assert trk.tick(1.0) == []
    assert sum("AAC track stopped" in r.message for r in caplog.records) == 1


def test_make_aac_track_honours_the_kill_switch(monkeypatch):
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "0")
    assert at.make_aac_track() is None
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "1")
    assert isinstance(at.make_aac_track(), at.AacTrack)


def test_make_aac_track_returns_none_when_the_encoder_cannot_open(monkeypatch, caplog):
    def _fail(*a, **k):
        raise ImportError("no av")

    monkeypatch.setattr(at, "AacEncoder", _fail)
    assert at.make_aac_track("cam1") is None
    assert "publishing without it" in caplog.text
