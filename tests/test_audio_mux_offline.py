"""Asserting offline tests of the DTLS A/V mux AUDIO path (no camera/network).

The asserting twin of ``scripts/audio_mux_probe.py``: feeds synthetic PCMA
through the REAL ``_dtls_av_mux_run()``, decodes the muxed MPEG-TS AAC back to
PCM, and asserts the choppiness signatures the probe only printed:

  * output duration tracks input (sample-accounting / desync drift)
  * no widespread silence dropouts in steady input
  * packet loss must not stall or kill the mux

Complements tests/test_dtls_mux_terminal_write.py, which covers the dead-pipe
and interleave-stall regressions.  Thresholds are deliberately generous and
directional (regression tripwires, not quality scores).
"""
import io
import queue
import threading
import time

import pytest

pytestmark = pytest.mark.mux

av = pytest.importorskip("av")
np = pytest.importorskip("numpy")

from aidot_cameras.camera.protocol import _dtls_av_mux_run

SR = 8000
PKT_SAMPLES = 320  # 40 ms PCMA packets (matches measured wire framing)
SECONDS = 6


def _make_pcma_packets(level_dbfs=-12.0, n_seconds=SECONDS, loss=0.0, seed=1):
    """White noise -> s16 -> PCMA bytes in 40 ms wire packets, optional drops."""
    rng = np.random.RandomState(seed)
    amp = (10 ** (level_dbfs / 20.0)) * 32767.0
    n = SR * n_seconds
    pcm = (rng.randn(n) * amp).clip(-32768, 32767).astype(np.int16)
    enc = av.CodecContext.create("pcm_alaw", "w")
    enc.sample_rate = SR
    enc.layout = "mono"
    enc.format = "s16"
    pkts = []
    for i in range(0, n - PKT_SAMPLES, PKT_SAMPLES):
        chunk = pcm[i:i + PKT_SAMPLES].reshape(1, -1)
        fr = av.AudioFrame.from_ndarray(chunk, format="s16", layout="mono")
        fr.sample_rate = SR
        for p in enc.encode(fr):
            pkts.append(bytes(p))
    raw = b"".join(pkts)
    # (packet_bytes, rtp_ts): ts reflects the TRUE wire position so a dropped
    # packet leaves a real timestamp gap (PCMA: 1 byte == 1 sample @ 8 kHz).
    wire = [(raw[i:i + PKT_SAMPLES], i) for i in range(0, len(raw) - PKT_SAMPLES, PKT_SAMPLES)]
    if loss > 0:
        wire = [(w, t) for (w, t) in wire if rng.rand() > loss]
    return wire, n


def _run_mux(wire_pkts):
    """Drive the real mux with one video keyframe + the audio packets."""
    vq, aq = queue.Queue(maxsize=4000), queue.Queue(maxsize=4000)
    out = io.BytesIO()
    progress = [time.monotonic()]
    stop = threading.Event()
    # minimal fake H.264 IDR so the video stream starts (NAL type 5)
    idr = b"\x00\x00\x00\x01\x67\x42\x00\x0a" + b"\x00\x00\x00\x01\x65" + b"\x88" * 40
    for w, ts in wire_pkts:
        aq.put((w, ts))
    vq.put((idr, 0, True))
    t = threading.Thread(
        target=_dtls_av_mux_run, args=(vq, aq, out, progress, stop), daemon=True
    )
    t.start()
    deadline = time.time() + 30
    while (not aq.empty()) and time.time() < deadline:
        time.sleep(0.05)
    time.sleep(0.5)
    stop.set()
    t.join(timeout=10)
    assert not t.is_alive(), "mux thread must terminate once stopped"
    return out.getvalue()


def _decode_aac(mpegts_bytes):
    """Decode the muxed TS back to a mono float array @ 48 kHz."""
    cont = av.open(io.BytesIO(mpegts_bytes), "r", format="mpegts")
    astream = next((s for s in cont.streams if s.type == "audio"), None)
    assert astream is not None, "muxed TS must contain an audio stream"
    rs = av.AudioResampler(format="flt", layout="mono", rate=48000)
    chunks = []
    for frame in cont.decode(astream):
        for rf in rs.resample(frame):
            chunks.append(rf.to_ndarray().reshape(-1))
    assert chunks, "muxed TS audio must decode to at least one frame"
    return np.concatenate(chunks), 48000


def _envelope_db(y, sr):
    w = int(0.02 * sr)  # 20 ms windows
    nfr = len(y) // w
    env = np.array(
        [np.sqrt(np.mean(y[i * w:(i + 1) * w] ** 2)) + 1e-9 for i in range(nfr)]
    )
    return 20 * np.log10(env)


def test_steady_noise_duration_and_no_dropouts():
    wire, n_in = _make_pcma_packets(level_dbfs=-12.0)
    ts = _run_mux(wire)
    y, sr = _decode_aac(ts)

    dur, exp = len(y) / sr, n_in / SR
    assert abs(dur - exp) < 1.0, (
        f"output duration {dur:.2f}s drifted from input {exp:.2f}s - "
        "sample-accounting / desync regression"
    )

    env_db = _envelope_db(y, sr)
    med = np.median(env_db)
    dropouts = int(np.sum(env_db < med - 25))
    assert dropouts < 0.10 * len(env_db), (
        f"{dropouts}/{len(env_db)} 20ms windows are >25dB below median on "
        "STEADY input - silence-dropout regression"
    )


def test_packet_loss_does_not_stall_or_kill_audio():
    wire, n_in = _make_pcma_packets(level_dbfs=-12.0, loss=0.03)
    ts = _run_mux(wire)  # asserts thread termination internally
    y, sr = _decode_aac(ts)
    dur, exp = len(y) / sr, n_in / SR
    # 3% loss shortens output slightly; a stall/desync shortens it massively.
    assert dur > 0.7 * exp, (
        f"only {dur:.2f}s of {exp:.2f}s decoded under 3% packet loss - "
        "the mux stalled or dropped far more than the lost packets"
    )


def test_quiet_input_still_produces_audio_stream():
    # Near the AGC gate: the stream must still exist and decode (the gate may
    # legitimately attenuate content, but must not kill the stream and with it
    # the mpegts PAT/PMT that video depends on).
    wire, _n_in = _make_pcma_packets(level_dbfs=-40.0)
    ts = _run_mux(wire)
    y, _sr = _decode_aac(ts)
    assert len(y) > 0
