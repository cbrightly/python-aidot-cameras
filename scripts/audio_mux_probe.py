"""Offline probe of the DTLS A/V mux audio path (no camera, no network).

Feeds synthetic PCMA audio through the REAL _dtls_av_mux_run() and decodes the
muxed MPEG-TS AAC back to PCM, then measures it for choppiness signatures:
  * silence dropouts (runs of near-zero samples)
  * level pumping (variance of the 20 ms RMS envelope)
  * output duration vs input (sample-accounting / desync drift)
Tests steady white noise, white noise with packet loss, and AGC on/off.
"""
import io
import os
import queue
import sys
import threading
import time

import av
import numpy as np

sys.path.insert(0, os.path.join(os.path.dirname(__file__), ".."))
from aidot.camera.protocol import _dtls_av_mux_run

SR = 8000
PKT_SAMPLES = 320          # 40 ms PCMA packets (matches measured wire framing)
SECONDS = 20


def make_pcma_packets(level_dbfs=-18.0, n_seconds=SECONDS, loss=0.0, seed=1):
    """White noise -> s16 -> PCMA bytes, in 40 ms packets. Optionally drop some."""
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
    # encoded PCMA packets are 1 byte/sample; regroup to 320-byte wire packets
    raw = b"".join(pkts)
    # (packet_bytes, rtp_ts) with rtp_ts reflecting the TRUE wire position so a
    # dropped packet leaves a real timestamp gap (PCMA: 1 byte == 1 sample @8k).
    wire = [(raw[i:i + PKT_SAMPLES], i) for i in range(0, len(raw) - PKT_SAMPLES, PKT_SAMPLES)]
    if loss > 0:
        wire = [(w, t) for (w, t) in wire if rng.rand() > loss]
    return wire, n


def run_mux(wire_pkts, realtime=False):
    """Drive the real mux with a video keyframe + the audio packets."""
    vq, aq = queue.Queue(maxsize=4000), queue.Queue(maxsize=4000)
    out = io.BytesIO()
    progress = [0.0]
    stop = threading.Event()
    # minimal fake H.264 IDR so the video stream starts (NAL type 5)
    idr = b"\x00\x00\x00\x01\x67\x42\x00\x0a" + b"\x00\x00\x00\x01\x65" + b"\x88" * 40
    for w, ts in wire_pkts:
        aq.put((w, ts))
    vq.put((idr, 0, True))
    t = threading.Thread(target=_dtls_av_mux_run, args=(vq, aq, out, progress, stop), daemon=True)
    t.start()
    # let the mux drain the queue
    deadline = time.time() + 15
    while (not aq.empty()) and time.time() < deadline:
        time.sleep(0.05)
    time.sleep(0.5)
    stop.set()
    t.join(timeout=5)
    return out.getvalue()


def decode_aac(mpegts_bytes):
    """Decode the muxed TS back to a mono float array @48k."""
    cont = av.open(io.BytesIO(mpegts_bytes), "r", format="mpegts")
    astream = next((s for s in cont.streams if s.type == "audio"), None)
    if astream is None:
        return None, None
    rs = av.AudioResampler(format="flt", layout="mono", rate=48000)
    chunks = []
    for frame in cont.decode(astream):
        for rf in rs.resample(frame):
            chunks.append(rf.to_ndarray().reshape(-1))
    if not chunks:
        return None, 48000
    return np.concatenate(chunks), 48000


def analyze(y, sr, expected_in_samples):
    if y is None or len(y) == 0:
        print("    NO AUDIO DECODED")
        return
    dur = len(y) / sr
    exp_dur = expected_in_samples / SR
    # 20 ms RMS envelope
    w = int(0.02 * sr)
    nfr = len(y) // w
    env = np.array([np.sqrt(np.mean(y[i * w:(i + 1) * w] ** 2)) + 1e-9 for i in range(nfr)])
    env_db = 20 * np.log10(env)
    # dropouts: 20ms windows >25 dB below median level
    med = np.median(env_db)
    drop = int(np.sum(env_db < med - 25))
    # pumping: stddev of envelope in dB over windows that are NOT dropouts
    live = env_db[env_db > med - 25]
    pump = float(np.std(live)) if len(live) else 0.0
    # click/discontinuity proxy: count large sample-to-sample jumps
    d = np.abs(np.diff(y))
    jumps = int(np.sum(d > 0.5))  # |delta|>0.5 of full-scale in one sample @48k
    print(f"    out {dur:6.2f}s (in {exp_dur:5.2f}s, drift {dur - exp_dur:+.2f}s) | "
          f"env median {med:6.1f} dBFS | dropouts(20ms) {drop:3d} | "
          f"pump sigma {pump:4.1f} dB | big-jumps {jumps:4d}")


def case(name, **kw):
    print(name)
    loss = kw.pop("loss", 0.0)
    level = kw.pop("level", -18.0)
    env = kw.pop("env", {})
    old = {}
    for k, v in env.items():
        old[k] = os.environ.get(k)
        os.environ[k] = v
    try:
        wire, n_in = make_pcma_packets(level_dbfs=level, loss=loss)
        ts = run_mux(wire)
        y, sr = decode_aac(ts)
        analyze(y, sr, n_in)
    finally:
        for k, v in old.items():
            if v is None:
                os.environ.pop(k, None)
            else:
                os.environ[k] = v


if __name__ == "__main__":
    print(f"=== DTLS audio-mux probe: {SECONDS}s white noise, 40ms PCMA packets ===")
    case("[1] loud white noise, AGC default (-15 target, -45 gate)", level=-12.0)
    case("[2] loud white noise, AGC OFF (gate=-120, min=max=0dB)", level=-12.0,
         env={"AIDOT_AUDIO_GATE_DBFS": "-120", "AIDOT_AUDIO_MINGAIN_DB": "0",
              "AIDOT_AUDIO_MAXGAIN_DB": "0"})
    case("[3] loud white noise + 3% packet loss, AGC default", level=-12.0, loss=0.03)
    case("[4] quiet white noise (-40 dBFS), AGC default (near gate)", level=-40.0)
