"""The AAC track the direct publish adds so Home Assistant's HLS has audio."""

import logging
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
        (None, False),
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
    p.feed(b"\x01" * 320, 1000, 0.0)  # pos -> 1320
    # Overlaps by 400 samples: beyond the jitter tolerance, so still trimmed.
    out = p.feed(b"\x02" * 640, 920, 0.02)
    assert _flat(out) == b"\x02" * 240
    assert p.trimmed_samples == 400
    assert p.feed(b"\x03" * 160, 1000, 0.03) == []  # entirely old: dropped
    assert p.trimmed_samples == 400 + 160


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


def test_a_long_real_gap_is_filled_not_reanchored():
    """Audio and video both stop for 8 s; the stamps and the wall clock agree."""
    p = at.AacPacer()
    p.feed(b"\x01" * 160, 1000, 0.0)  # position 1160, anchored at t=0.0
    gap = 8 * 8000 - 160  # the stamp advance the hybrid timeline gives an 8 s gap
    out = _flat(p.feed(b"\x02" * 160, 1160 + gap, 8.0))
    assert out == S * gap + b"\x02" * 160
    assert p.reanchors == 0
    assert p.silence_samples == gap
    # and the next packet continues without a trim or another fill
    assert _flat(p.feed(b"\x03" * 160, 1160 + gap + 160, 8.02)) == b"\x03" * 160


def test_a_stamp_jump_the_wall_clock_does_not_explain_still_reanchors():
    p = at.AacPacer()
    p.feed(b"\x01" * 160, 1000, 0.0)
    out = _flat(p.feed(b"\x02" * 160, 1160 + 8000 * 60, 0.02))  # 60 s jump, 20 ms later
    assert out == b"\x02" * 160
    assert p.reanchors == 1 and p.silence_samples == 0


def test_idle_fill_keeps_the_track_alive_when_the_camera_sends_no_audio():
    p = at.AacPacer()
    assert p.tick(10.0) == []  # first tick only starts the clock
    assert _flat(p.tick(10.3)) == S * 2400  # no audio yet: fill up to now
    assert _flat(p.tick(10.6)) == S * 2400
    assert _flat(p.tick(10.7)) == S * 800


def test_before_any_audio_the_fill_follows_every_tick():
    p = at.AacPacer()
    step = 0.04
    t0 = 5.0
    assert p.tick(t0) == []
    for k in range(1, 50):
        now = t0 + k * step
        assert p.tick(now), f"tick {k} filled nothing"
        # The silence reaches `now`: the delivery lag is under one tick.
        lag = now - (t0 + p.silence_samples / at.PCMA_RATE)
        assert abs(lag) < step
    assert p.silence_samples == round(49 * step * at.PCMA_RATE)


def test_after_audio_stops_the_fill_starts_at_the_threshold_then_tracks_now():
    p = at.AacPacer()
    for i in range(50):  # audio until 0.98
        p.feed(b"\x01" * 160, 1000 + 160 * i, i * 0.02)
    last_audio = 49 * 0.02
    filled_at = []
    for k in range(1, 40):
        now = last_audio + k * 0.04
        out = _flat(p.tick(now))
        if now - last_audio < at.AAC_IDLE_FILL_S - 1e-9:
            assert out == b"", f"filled {len(out)} before the threshold"
            continue
        assert out, f"idle at {now:.2f} and nothing filled"
        filled_at.append(now)
        # Every tick once idle brings the silence up to `now`.
        covered = last_audio + 0.02 + p.silence_samples / at.PCMA_RATE
        assert abs(covered - (now + 0.02)) < 1.0 / at.PCMA_RATE
    assert filled_at and filled_at[0] - last_audio < at.AAC_IDLE_FILL_S + 0.04


def test_resume_after_a_continuous_fill_never_steps_backward():
    p = at.AacPacer()
    p.feed(b"\x01" * 160, 1000, 0.0)
    for k in range(1, 26):  # 1 s of ticks, the fill tracking every one
        p.tick(k * 0.04)
    pos = 1000 + 160 + p.silence_samples
    # The camera resumes 400 samples behind the filled position (beyond the
    # jitter tolerance): only its tail is new, and the position keeps moving
    # forward from there.
    assert _flat(p.feed(b"\x02" * 800, pos - 400, 1.02)) == b"\x02" * 400
    assert _flat(p.feed(b"\x02" * 160, pos + 400, 1.04)) == b"\x02" * 160
    assert p.tick(1.05) == []  # audio is flowing again: no fill


def test_idle_fill_after_audio_stops_then_resumes_without_a_backward_step():
    p = at.AacPacer()
    p.feed(b"\x01" * 160, 1000, 0.00)
    filled = _flat(p.tick(1.00))  # 1 s of silence since the last packet
    assert filled == S * 8000
    # The camera resumes; its first packet lies wholly inside the filled silence.
    out = _flat(p.feed(b"\x02" * 160, 1000 + 160 + 7800, 1.00))
    assert out == b""
    # The next packet overlaps the fill by 400 samples (beyond the jitter
    # tolerance): only its tail is kept.
    out = _flat(p.feed(b"\x02" * 800, 1000 + 160 + 8000 - 400, 1.02))
    assert out == b"\x02" * 400
    # From here on the camera's stamps are exactly continuous.
    out = _flat(p.feed(b"\x02" * 160, 1000 + 160 + 8000 + 400, 1.04))
    assert out == b"\x02" * 160


def test_audio_resumes_after_a_gap_the_camera_stamps_do_not_cover():
    # The camera's stamps advance less than wall time across an arrival gap:
    # tick() fills ahead of them, so the first packets after the gap lie
    # wholly behind the position and are trimmed. Those trimmed packets are
    # still the camera sending audio - the pacer must not treat it as idle
    # and keep filling, or no real sample would ever be emitted again.
    p = at.AacPacer()
    real = b"\x01"
    ts, t = 1000, 0.0
    for _ in range(50):  # 1 s of continuous audio
        p.feed(real * 160, ts, t)
        p.tick(t + 0.01)
        ts, t = ts + 160, t + 0.02
    gap_end = t + 0.6
    while t < gap_end:  # 0.6 s arrival gap; video keeps ticking
        p.tick(t)
        t += 0.02
    resumed = t
    first_real = None
    silence_at_catch_up = None
    while t < resumed + 4.0:  # the camera resumes where its stamps stopped
        out = _flat(p.feed(real * 160, ts, t))
        p.tick(t + 0.01)
        if first_real is None and real in out:
            first_real = t
            silence_at_catch_up = p.silence_samples
        ts, t = ts + 160, t + 0.02
    assert first_real is not None, "AAC stayed silent after the camera resumed"
    assert first_real - resumed <= 1.0
    assert p.silence_samples == silence_at_catch_up  # no fill once caught up


def test_idle_catch_up_is_capped_per_block():
    p = at.AacPacer()
    p.feed(b"\x01" * 160, 1000, 0.0)
    blocks = []
    for now in (12.02, 12.04, 12.06):  # 12 s with no tick at all
        blocks += p.tick(now)
    assert len(blocks) == 3
    assert all(len(b) <= at.AAC_MAX_FILL_S * at.PCMA_RATE for b in blocks)
    # Caught up: the filled silence covers the idle time up to the last tick.
    assert sum(len(b) for b in blocks) == round(12.06 * at.PCMA_RATE)


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

    trk = at.AacTrack(encoder=Broken(), device_id="cam7")
    assert trk.feed(b"\x01" * 160, 0, 0.0) == []
    assert trk.failed
    assert trk.feed(b"\x01" * 160, 160, 0.02) == []
    assert sum("AAC track stopped" in r.message for r in caplog.records) == 1
    assert (
        sum("camera cam7: AAC track stopped" in r.message for r in caplog.records) == 1
    )


def test_a_pacer_failure_also_stops_only_the_aac_track(caplog):
    class BrokenPacer:
        def feed(self, alaw, pcma_ts, now):
            raise RuntimeError("pacer boom")

        def tick(self, now):
            raise RuntimeError("pacer boom")

    class Encoder:
        def encode(self, alaw):
            return []

    trk = at.AacTrack(encoder=Encoder(), pacer=BrokenPacer(), device_id="cam8")
    assert trk.feed(b"\x01" * 160, 0, 0.0) == []
    assert trk.failed
    assert trk.tick(1.0) == []
    assert sum("AAC track stopped" in r.message for r in caplog.records) == 1
    assert (
        sum("camera cam8: AAC track stopped" in r.message for r in caplog.records) == 1
    )


def test_a_reanchor_names_the_camera(caplog):
    p = at.AacPacer(device_id="cam9")
    with caplog.at_level("INFO"):
        p.feed(b"\x01" * 160, 1000, 0.0)
        p.feed(b"\x02" * 160, 1160 + 8000 * 60, 0.02)  # 60 s jump: re-anchors
    assert p.reanchors == 1
    assert any(
        "camera cam9: AAC track: camera audio jumped" in r.message
        for r in caplog.records
    )


def test_make_aac_track_honours_the_kill_switch(monkeypatch):
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "0")
    assert at.make_aac_track() is None
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "1")
    assert isinstance(at.make_aac_track(), at.AacTrack)


def test_make_aac_track_returns_none_when_the_encoder_cannot_open(monkeypatch, caplog):
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "1")

    def _fail(*a, **k):
        raise ImportError("no av")

    monkeypatch.setattr(at, "AacEncoder", _fail)
    monkeypatch.setattr(at, "_ENCODER_WARNED", False)
    assert at.make_aac_track("cam1") is None
    assert "publishing without it" in caplog.text


def test_encoder_open_failure_warns_once_then_only_debug(monkeypatch, caplog):
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "1")

    def _fail(*a, **k):
        raise ImportError("no aac encoder")

    monkeypatch.setattr(at, "AacEncoder", _fail)
    monkeypatch.setattr(at, "_ENCODER_WARNED", False)
    with caplog.at_level("DEBUG"):
        assert at.make_aac_track("cam1") is None
        assert at.make_aac_track("cam2") is None
    warnings = [r for r in caplog.records if r.levelname == "WARNING"]
    debugs = [r for r in caplog.records if r.levelname == "DEBUG"]
    assert len(warnings) == 1 and "cam1" in warnings[0].message
    assert len(debugs) == 1 and "cam2" in debugs[0].message


def _distinct(i, n):
    """`n` bytes of content that vary by packet index and are never 0xD5."""
    return bytes([(i % 200) + 1]) * n


@pytest.mark.parametrize("first_step,second_step", [(256, 384), (384, 256)])
def test_timestamp_jitter_inside_the_dead_band_is_absorbed(first_step, second_step):
    # The M3 Pro: 320-sample (40 ms) PCMA packets whose stamps step alternately
    # +256 and +384 (mean 320). Every step disagrees with the position by only
    # 64 samples, well inside AAC_JITTER_TOL_S, so nothing is filled or trimmed.
    # Pinned for both orderings of the alternation: starting +256 only ever
    # puts the stamp behind or level with the position (d in {0, -64}), so it
    # alone would not cover the forward half of the tolerance; starting +384
    # does (d in {0, +64}).
    p = at.AacPacer()
    n = 250
    content_len = 320
    packets = [_distinct(i, content_len) for i in range(n)]
    ts = 1000
    out = []
    for i, content in enumerate(packets):
        out += p.feed(content, ts, i * 0.04)
        ts += first_step if i % 2 == 0 else second_step
    assert _flat(out) == _flat(packets)
    assert p.silence_samples == 0
    assert p.trimmed_samples == 0


def test_dead_band_edges():
    # Each case starts fresh after a 160-sample packet stamped 1000 (position
    # -> 1160), then feeds one more packet right at (absorbed) or one sample
    # past (filled/trimmed) the AAC_JITTER_TOL_S edge.

    # 320 samples ahead is exactly the tolerance: absorbed, no silence.
    p = at.AacPacer()
    p.feed(b"\x01" * 160, 1000, 0.0)
    payload = _distinct(0, 160)
    out = _flat(p.feed(payload, 1160 + 320, 0.02))
    assert out == payload
    assert p.silence_samples == 0 and p.trimmed_samples == 0

    # 321 samples ahead is one past the tolerance: filled.
    p = at.AacPacer()
    p.feed(b"\x01" * 160, 1000, 0.0)
    payload = _distinct(0, 160)
    out = _flat(p.feed(payload, 1160 + 321, 0.02))
    assert out == S * 321 + payload
    assert p.silence_samples == 321

    # 320 samples behind is exactly the tolerance: all of it absorbed.
    p = at.AacPacer()
    p.feed(b"\x01" * 160, 1000, 0.0)
    payload = _distinct(0, 640)
    out = _flat(p.feed(payload, 1160 - 320, 0.02))
    assert out == payload
    assert p.trimmed_samples == 0

    # 321 samples behind is one past the tolerance: the overlap is trimmed.
    p = at.AacPacer()
    p.feed(b"\x01" * 160, 1000, 0.0)
    payload = _distinct(0, 640)
    out = _flat(p.feed(payload, 1160 - 321, 0.02))
    assert out == payload[321:]
    assert p.trimmed_samples == 321


def test_gap_tolerance_boundary_is_a_real_gap_not_a_reanchor():
    # AAC_GAP_TOLERANCE_S is an inclusive bound on the disagreement between
    # the stamp jump and the wall clock: exactly on the bound must still be
    # judged a real gap and filled, not treated as unexplained and
    # re-anchored. A `<=` -> `<` mutant of that check fails this.
    p = at.AacPacer()
    p.feed(b"\x01" * 160, 0, 10.0)  # anchored at wall time 10.0
    jump = 6 * at.PCMA_RATE  # a 6 s stamp jump; the wall clock only shows 5 s
    out = _flat(p.feed(b"\x02" * 160, 160 + jump, 15.0))
    assert out == S * jump + b"\x02" * 160
    assert p.silence_samples == jump
    assert p.reanchors == 0


def test_a_gap_beyond_the_dead_band_is_still_filled():
    p = at.AacPacer()
    p.feed(b"\x01" * 160, 1000, 0.0)  # pos -> 1160
    out = _flat(p.feed(b"\x02" * 160, 1000 + 160 + 800, 0.1))  # 800 samples late
    assert out == S * 800 + b"\x02" * 160
    assert p.silence_samples == 800


def test_an_overlap_beyond_the_dead_band_is_still_trimmed():
    p = at.AacPacer()
    p.feed(b"\x01" * 160, 1000, 0.0)  # pos -> 1160
    out = _flat(p.feed(b"\x02" * 640, 1160 - 480, 0.02))  # 480 samples early
    assert out == b"\x02" * 160
    assert p.trimmed_samples == 480


def test_a_duplicate_packet_is_still_dropped():
    p = at.AacPacer()
    p.feed(b"\x01" * 320, 1000, 0.0)  # pos -> 1320
    out = p.feed(b"\x01" * 320, 1000, 0.02)  # the exact same packet again
    assert out == []
    assert p.trimmed_samples == 320


def test_slow_clock_drift_is_resynced_when_it_passes_the_tolerance():
    # 320-sample packets stamped +330 each: 3% fast, 10 samples of drift a
    # packet. Nothing is filled until the accumulated drift passes the 320
    # sample tolerance; once it does, the position resyncs in one fill.
    p = at.AacPacer()
    n = 100
    step_ts = 330
    content_len = 320
    ts = 1000
    appended = 0
    fills = []  # (packet index, silence samples inserted by that feed)
    last_ts = None
    for i in range(n):
        content = _distinct(i, content_len)
        before_silence = p.silence_samples
        blocks = p.feed(content, ts, i * (step_ts / at.PCMA_RATE))
        out_len = sum(len(b) for b in blocks)
        added_silence = p.silence_samples - before_silence
        appended += out_len - added_silence
        if added_silence:
            fills.append((i, added_silence))
        last_ts = ts
        ts += step_ts
    assert [i for i, _ in fills] == [33, 66, 99]
    assert all(silence == 330 for _, silence in fills)
    assert p.silence_samples == (last_ts + content_len - 1000) - appended


def _cold_start(
    v_backlog,
    a_backlog,
    a_after,
    *,
    secs=12.0,
    fps=15.0,
    latency=0.05,
    audio=True,
    jitter=None,
    tick_first=True,
):
    """Drive a pacer through a synthetic cold start on one capture clock.

    The viewer starts the camera at capture time v_backlog; every packet has
    the same network latency. Video frames are captured every 1/fps from
    capture time 0: the backlog (captured before the start) arrives within
    0.1 s after start + latency, later frames at capture + latency. Audio
    packets (320 samples, 40 ms) are captured from v_backlog - a_backlog on:
    the audio backlog arrives together a_after seconds after the video's first
    frame, later packets at capture + latency but never before the backlog. Returns (pacer, rows) with one row per audio packet:
    (capture time, AAC output time of the packet's first sample).
    """
    p = at.AacPacer()
    events = []
    for j in range(int(secs * fps)):
        cap = j / fps
        start = v_backlog + latency
        arr = (
            start + cap / max(v_backlog, 1e-9) * 0.1
            if cap < v_backlog
            else cap + latency
        )
        events.append((arr, 0 if tick_first else 1, "v", j / fps, None))
    if audio:
        a_start = v_backlog - a_backlog
        k = 0
        while a_start + k * 0.04 < secs:
            cap = a_start + k * 0.04
            first = v_backlog + latency + a_after
            arr = first if cap < v_backlog else max(cap + latency, first)
            ts = 1000 + 320 * k + (jitter[k % len(jitter)] if jitter else 0)
            events.append((arr, 1 if tick_first else 0, "a", cap, ts))
            k += 1
    events.sort(key=lambda e: (e[0], e[1]))
    emitted = 0
    rows = []
    t0 = events[0][0]
    for arr, _, kind, value, ts in events:
        now = 100.0 + arr - t0
        if kind == "v":
            for blk in p.tick(now, video_media_s=value):
                emitted += len(blk)
        else:
            out = p.feed(b"\x11" * 320, ts, now)
            before = emitted
            for blk in out:
                emitted += len(blk)
            if out and out[-1] and out[-1][0:1] == b"\x11":
                rows.append((value, (before + sum(len(b) for b in out[:-1])) / 8000))
    return p, rows


def _misalignment(rows, after=5.0):
    """Output time minus capture time for packets captured after `after` s -
    constant when aligned; aligned with video means it equals 0 (the video's
    first frame is capture 0 and output 0)."""
    return [out - cap for cap, out in rows if cap >= after]


@pytest.mark.parametrize(
    "v_backlog,a_backlog,a_after",
    [(1.35, 0.0, 0.06), (2.4, 0.15, 0.18), (0.77, 0.18, 0.05)],
    ids=["m3", "l2", "ptz"],
)
def test_cold_start_backlog_ends_aligned_with_video(v_backlog, a_backlog, a_after):
    p, rows = _cold_start(v_backlog, a_backlog, a_after)
    mis = _misalignment(rows)
    assert mis and max(abs(m) for m in mis) <= 0.04
    # One correction, after the backlogs have settled - not a string of them.
    assert p.align_corrections == 1


def test_warm_start_gets_no_correction():
    p, rows = _cold_start(0.0001, 0.0, 0.0)
    assert p.align_samples == 0 and p.align_corrections == 0
    assert max(abs(m) for m in _misalignment(rows)) <= 0.04


def test_jitter_inside_the_tolerance_is_not_realigned():
    p, _ = _cold_start(0.0001, 0.0, 0.0, jitter=[-64, 64])
    assert p.align_samples == 0


def test_audio_before_video_still_ends_aligned():
    # The audio backlog arrives before the first video frame does.
    p, rows = _cold_start(1.35, 0.1, -0.05)
    assert max(abs(m) for m in _misalignment(rows)) <= 0.04


def test_tick_after_feed_order_ends_aligned():
    p, rows = _cold_start(1.35, 0.0, 0.06, tick_first=False)
    assert max(abs(m) for m in _misalignment(rows)) <= 0.04


def test_no_audio_silence_follows_the_video_media_clock():
    p = at.AacPacer()
    emitted = 0
    for j in range(150):  # 10 s at 15 fps, first 1.35 s delivered in a burst
        cap = j / 15
        arr = 1.40 + cap / 1.35 * 0.1 if cap < 1.35 else cap + 0.05
        for blk in p.tick(100.0 + arr, video_media_s=cap):
            emitted += len(blk)
        assert abs(emitted / 8000 - cap) <= 1 / 15
    assert p.align_samples == 0


def test_a_correction_beyond_the_maximum_is_skipped(caplog):
    caplog.set_level(logging.INFO)
    # Silence already follows the video before audio, so only audio arriving
    # BEFORE a huge video backlog can need a correction beyond the maximum.
    p, _ = _cold_start(at.AAC_ALIGN_MAX_S + 1.0, 0.1, -0.08, secs=14.0)
    assert p.align_samples == 0
    assert (
        sum(
            "start correction" in r.getMessage() and "skipped" in r.getMessage()
            for r in caplog.records
        )
        == 1
    )


def test_nothing_realigns_after_the_window():
    p = at.AacPacer()
    p.tick(100.0, video_media_s=0.0)
    p.feed(b"\x11" * 320, 1000, 100.05)
    before = p.align_samples
    # 4 s later video's media clock claims a 1 s lead it never had at the start.
    p.tick(104.1, video_media_s=5.1)
    p.feed(b"\x11" * 320, 1000 + 320 * 100, 104.1)
    assert p.align_samples == before


def test_non_monotonic_video_media_is_ignored():
    p = at.AacPacer()
    assert p.tick(100.0, video_media_s=0.0) == []
    first = b"".join(p.tick(100.5, video_media_s=0.5))
    assert len(first) == 4000
    # a smaller value is ignored (wall-clock fallback), never an exception
    back = b"".join(p.tick(100.6, video_media_s=0.1))
    assert len(back) >= 0


def test_callers_without_video_media_behave_as_before():
    old, new = at.AacPacer(), at.AacPacer()
    seq = [("t", 0.0), ("t", 0.3), ("t", 0.8), ("f", 0.85), ("t", 1.0), ("t", 2.0)]
    for i, (kind, t) in enumerate(seq):
        if kind == "t":
            assert new.tick(100.0 + t) == old.tick(100.0 + t)
        else:
            assert new.feed(b"\x11" * 320, 1000, 100.0 + t) == old.feed(
                b"\x11" * 320, 1000, 100.0 + t
            )
    assert new.align_samples == 0
