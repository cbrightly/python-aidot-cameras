"""DTLS ``output_path`` recordings to MPEG-TS copy the camera's own streams.

The recording used to go through aiortc's MediaRecorder, which decodes and
re-encodes. A code review found it lost about half the frames (it read the same
track queue as the ``on_frame`` consumer and the audio drain), could write a
640x480 crop when audio reached it before video, carried aiortc's false 2**32
"wrap" into the file as a ~13-hour jump, and held video back for 10 s when no
audio arrived. It now uses the copy mux Home Assistant's DTLS path already runs:
encoded frames are teed before decode, so nothing competes for them.

These feed REAL H.264 (libx264) and REAL A-law packets through the REAL tap
(``CameraMixin._install_encoded_tap``) into the REAL recorder, then decode the
file it wrote - checking the properties a viewer would notice.
"""

import asyncio
import fractions

import pytest

av = pytest.importorskip("av")
np = pytest.importorskip("numpy")

from aidot_cameras.camera.client import CameraMixin  # noqa: E402
from aidot_cameras.camera.recording import TsCopyRecorder  # noqa: E402

W, H, FPS = 1280, 720, 15
FRAMES = 45  # 3 s


class _Enc:
    def __init__(self, data, timestamp):
        self.data, self.timestamp = data, timestamp


class _DecoderQueue:
    """Stand-in for aiortc's receiver decoder queue; records what decode gets."""

    def __init__(self):
        self.puts = []

    def put(self, task, *a, **k):
        self.puts.append(task)


class _Receiver:
    def __init__(self):
        self._RTCRtpReceiver__decoder_queue = _DecoderQueue()


def _h264_frames():
    """FRAMES Annex-B access units of a moving 1280x720 picture, GOP 15."""
    enc = av.CodecContext.create("libx264", "w")
    enc.width, enc.height, enc.pix_fmt = W, H, "yuv420p"
    enc.time_base = fractions.Fraction(1, FPS)
    enc.options = {"preset": "ultrafast", "tune": "zerolatency", "g": "15"}
    out = []
    base = np.add.outer(np.arange(H), np.arange(W)).astype(np.uint8)
    for i in range(FRAMES):
        y = np.roll(base, 8 * i, axis=1)
        img = np.vstack([y, np.full((H // 2, W), 128, np.uint8)])
        f = av.VideoFrame.from_ndarray(img, format="yuv420p")
        f.pts = i
        out += [bytes(p) for p in enc.encode(f)]
    out += [bytes(p) for p in enc.encode(None)]
    assert len(out) == FRAMES
    return out


def _alaw_packets(seconds):
    """20 ms A-law packets of a 440 Hz tone, as (bytes, 8 kHz timestamp)."""
    enc = av.CodecContext.create("pcm_alaw", "w")
    enc.sample_rate, enc.layout, enc.format = 8000, "mono", "s16"
    t = np.arange(int(8000 * seconds)) / 8000
    pcm = (np.sin(2 * np.pi * 440 * t) * 8000).astype(np.int16)
    out = []
    for k in range(0, len(pcm), 160):
        fr = av.AudioFrame.from_ndarray(
            pcm[None, k : k + 160], format="s16", layout="mono"
        )
        fr.sample_rate, fr.pts = 8000, k
        out.append((b"".join(bytes(p) for p in enc.encode(fr)), 5000 + k))
    return out


async def _record(path, *, decode_video, audio_first=0, audio=True, backward_at=None):
    """Drive the recorder the way a DTLS session does and return the decoder log."""
    rec = TsCopyRecorder(str(path))
    vr, ar = _Receiver(), _Receiver()
    assert CameraMixin._install_encoded_tap(vr, rec.vq, True, serve=not decode_video)
    assert CameraMixin._install_encoded_tap(ar, rec.aq, False)
    vput = vr._RTCRtpReceiver__decoder_queue
    aput = ar._RTCRtpReceiver__decoder_queue
    tapped_v, tapped_a = vput.put, aput.put  # the tap replaced put()
    await rec.start()
    frames, pkts = _h264_frames(), (_alaw_packets(FRAMES / FPS + 0.5) if audio else [])
    for d, ts in pkts[:audio_first]:
        tapped_a((0, _Enc(d, ts)))
        await asyncio.sleep(0.02)
    ai = audio_first
    for i, d in enumerate(frames):
        ts = 1000 + i * (90000 // FPS)
        if backward_at is not None and i >= backward_at:
            # aiortc's mapper: one frame 6000 ticks late reads as a 2**32 wrap,
            # and every frame after it stays 2**32 high.
            ts += 2**32 - (6000 if i == backward_at else 0)
        tapped_v((0, _Enc(d, ts)))
        for _ in range(3):  # ~3 audio packets per video frame at 15 fps
            if ai < len(pkts):
                tapped_a((0, _Enc(*pkts[ai])))
                ai += 1
        await asyncio.sleep(0.005)
    await rec.stop()
    return vput.puts, ai * 0.020  # what decode saw; seconds of audio sent


def _probe(path):
    c = av.open(str(path))
    v = c.streams.video[0]
    vframes = sum(1 for _ in c.decode(video=0))
    c.close()
    c = av.open(str(path))
    a_s = 0.0
    if c.streams.audio:
        for fr in c.decode(audio=0):
            a_s += fr.samples / fr.sample_rate
    c.close()
    c = av.open(str(path))
    vts = [float(p.pts * p.time_base) for p in c.demux(video=0) if p.pts is not None]
    c.close()
    return {
        "w": v.codec_context.width,
        "h": v.codec_context.height,
        "frames": vframes,
        "span": max(vts) - min(vts),
        "audio_s": a_s,
    }


async def test_every_frame_reaches_the_file_while_the_decoder_is_also_fed(tmp_path):
    out = tmp_path / "r.ts"
    decoder_got, _ = await _record(out, decode_video=True)
    p = _probe(out)
    assert p["frames"] == FRAMES, p  # was about half with MediaRecorder
    assert (
        len([t for t in decoder_got if t is not None]) == FRAMES
    )  # on_frame unaffected


async def test_audio_arriving_first_does_not_crop_the_picture(tmp_path):
    out = tmp_path / "r.ts"
    await _record(out, decode_video=False, audio_first=10)
    p = _probe(out)
    assert (p["w"], p["h"]) == (W, H), p  # MediaRecorder could open at 640x480


async def test_a_false_wrap_does_not_stretch_the_recording(tmp_path):
    out = tmp_path / "r.ts"
    await _record(out, decode_video=False, backward_at=20)
    p = _probe(out)
    assert p["span"] < FRAMES / FPS + 1.0, p  # was ~47,722 s


async def test_video_is_recorded_when_the_camera_sends_no_audio(tmp_path):
    out = tmp_path / "r.ts"
    await _record(out, decode_video=False, audio=False)
    assert _probe(out)["frames"] == FRAMES


async def test_audio_is_complete_and_the_decoder_is_skipped_without_on_frame(tmp_path):
    out = tmp_path / "r.ts"
    decoder_got, sent_s = await _record(out, decode_video=False)
    p = _probe(out)
    # All the audio sent is in the file (AAC adds at most one padded frame).
    # MediaRecorder kept about half: it shared the queue with the audio drain.
    assert sent_s - 0.03 <= p["audio_s"] <= sent_s + 0.05, (p, sent_s)
    # No on_frame: decoding video would only fill a queue nobody drains.
    assert [t for t in decoder_got if t is not None] == []


async def test_stop_twice_and_stop_without_start_are_harmless(tmp_path):
    rec = TsCopyRecorder(str(tmp_path / "never.ts"))
    await rec.stop()  # never started
    rec2 = TsCopyRecorder(str(tmp_path / "r.ts"))
    await rec2.start()
    await rec2.stop()
    await rec2.stop()
