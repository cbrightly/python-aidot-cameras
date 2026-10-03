"""End to end: the real DTLS publisher -> TS tee -> router -> a mid-stream join.

Real H.264 (libx264) and real A-law, with a flash and a click at the same
instants, go through ``dtls_rtp_publish_run`` - its A-law conditioning, AAC
pacer and cold-start alignment included - into the camera's TS. A consumer then
joins mid-stream over HTTP with libav (Home Assistant's demuxer) and the click is
measured against the flash. Through go2rtc this same join came out 0.1-0.75 s
late; here it must be within a video frame.
"""

import fractions
import queue
import threading
import time

import pytest

av = pytest.importorskip("av")
np = pytest.importorskip("numpy")

import aidot_cameras.camera.rtsp_publish as rp  # noqa: E402
from aidot_cameras.camera.ts_fanout import TsRouter  # noqa: E402
from aidot_cameras.camera.ts_tee import TsTee  # noqa: E402

from test_rtsp_publish import go2rtc  # noqa: E402,F401  (fixture)
from test_ts_tee import _join, _offsets  # noqa: E402

FPS, GOP, SECS, EVERY = 15, 30, 20, 3


def _camera_media():
    enc = av.CodecContext.create("libx264", "w")
    enc.width, enc.height, enc.pix_fmt = 320, 240, "yuv420p"
    enc.time_base = fractions.Fraction(1, FPS)
    enc.options = {
        "preset": "ultrafast",
        "tune": "zerolatency",
        "g": str(GOP),
        "keyint_min": str(GOP),
        "sc_threshold": "0",
    }
    video = []
    for i in range(FPS * SECS):
        lum = 235 if i % (EVERY * FPS) == 0 else 40 + (i % 50)
        img = np.vstack(
            [np.full((240, 320), lum, np.uint8), np.full((120, 320), 128, np.uint8)]
        )
        f = av.VideoFrame.from_ndarray(img, format="yuv420p")
        f.pts = i
        video += [(bytes(p), bool(p.is_keyframe)) for p in enc.encode(f)]
    video += [(bytes(p), bool(p.is_keyframe)) for p in enc.encode(None)]
    aenc = av.CodecContext.create("pcm_alaw", "w")
    aenc.sample_rate, aenc.layout, aenc.format = 8000, "mono", "s16"
    pcm = (np.random.default_rng(1).normal(0, 300, 8000 * SECS)).astype(
        np.int16
    )  # room tone
    for s in range(0, SECS, EVERY):
        pcm[s * 8000 : s * 8000 + 160] = 24000
    alaw = []
    for k in range(0, len(pcm), 320):  # 40 ms packets, as the M3 Pro sends
        fr = av.AudioFrame.from_ndarray(
            pcm[None, k : k + 320], format="s16", layout="mono"
        )
        fr.sample_rate, fr.pts = 8000, k
        alaw.append((b"".join(bytes(p) for p in aenc.encode(fr)), k))
    return video, alaw


def test_a_mid_stream_join_through_the_real_publisher_is_in_step(
    go2rtc, monkeypatch, tmp_path
):  # noqa: F811
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "1")
    router = TsRouter(0)
    router.start()
    tee = TsTee(router.channel("/cam.ts"))
    tee.start()
    vq, aq = queue.Queue(), queue.Queue()
    progress, stop, res = [0.0], threading.Event(), {}
    pub = threading.Thread(
        target=rp.dtls_rtp_publish_run,
        args=(vq, aq, go2rtc.url(), progress, stop),
        kwargs={"result": res, "ts_session": tee.session()},
        daemon=True,
    )
    pub.start()
    video, alaw = _camera_media()

    def camera():
        t0, ai = time.monotonic(), 0
        for i, (au, kf) in enumerate(video):
            while ai < len(alaw) and alaw[ai][1] <= i * 8000 // FPS:
                aq.put((alaw[ai][0], 1000 + alaw[ai][1]))
                ai += 1
            vq.put((au, 5000 + i * (90000 // FPS), kf))
            time.sleep(max(0.0, t0 + (i + 1) / FPS - time.monotonic()))

    cam = threading.Thread(target=camera, daemon=True)
    cam.start()
    try:
        found = []
        for k, wait in enumerate((5.4, 1.3)):  # mid-stream, mid-GOP
            time.sleep(wait)
            out = str(tmp_path / ("j%d.mp4" % k))
            _join(router.port, 6.5, out)
            offs = _offsets(out)
            assert offs, "join %d: no flash/click pair" % k
            found += offs
        # The sync gate is +/-0.1 s: a flash is seen only to within a video frame
        # (67 ms) and the AAC pacer has a 40 ms jitter dead band by design. The
        # mean must sit near zero - a systematic offset is the defect.
        assert all(abs(o) <= 0.1 for o in found), found
        assert abs(sum(found) / len(found)) <= 0.05, found
    finally:
        cam.join(30)
        stop.set()
        pub.join(5)
        tee.close()
        router.close()
