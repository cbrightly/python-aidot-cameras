"""TsTee: the publishers' video and AAC, muxed to MPEG-TS on one clock.

The point of the tee is that a consumer joining at ANY moment gets sound and
picture in step - which go2rtc's outputs do not give (0.1-0.75 s audio lag per
join, measured 2026-10-03). These feed real H.264 (libx264) and real AAC with a
flash and a click at the same instants, in real time, and join over HTTP with
libav (Home Assistant's demuxer).
"""

import fractions
import threading
import time

import pytest

av = pytest.importorskip("av")
np = pytest.importorskip("numpy")

from aidot_cameras.camera.ts_fanout import TsRouter  # noqa: E402
from aidot_cameras.camera.ts_tee import TsTee  # noqa: E402

FPS, GOP = 15, 30


def _media(seconds, flash_every=2):
    """(video [(au, keyframe)], aac [au]) with a flash and a click every flash_every s."""
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
    for i in range(FPS * seconds):
        lum = 235 if i % (flash_every * FPS) == 0 else 40 + (i % 50)
        img = np.vstack(
            [np.full((240, 320), lum, np.uint8), np.full((120, 320), 128, np.uint8)]
        )
        f = av.VideoFrame.from_ndarray(img, format="yuv420p")
        f.pts = i
        for p in enc.encode(f):
            video.append((bytes(p), bool(p.is_keyframe)))
    for p in enc.encode(None):
        video.append((bytes(p), bool(p.is_keyframe)))
    aenc = av.CodecContext.create("aac", "w")
    aenc.sample_rate, aenc.layout, aenc.format = 48000, "mono", "fltp"
    pcm = np.zeros(48000 * seconds, np.float32)
    for s in range(0, seconds, flash_every):
        pcm[s * 48000 : s * 48000 + 960] = 0.8
    aac = []
    for k in range(0, len(pcm), 1024):
        fr = av.AudioFrame.from_ndarray(
            pcm[None, k : k + 1024], format="fltp", layout="mono"
        )
        fr.sample_rate, fr.pts = 48000, k
        aac += [bytes(p) for p in aenc.encode(fr)]
    aac += [bytes(p) for p in aenc.encode(None)]
    return video, aac


def _feed(sess, video, aac, realtime=True):
    """Feed one session in real time: media times from 0, as the publisher does."""
    t0, ai = time.monotonic(), 0
    for i, (au, kf) in enumerate(video):
        while ai < len(aac) and ai * 1024 <= i * 48000 // FPS:
            sess.aac(aac[ai], ai * 1024)
            ai += 1
        sess.video(au, i * (90000 // FPS), kf)
        if realtime:
            time.sleep(max(0.0, t0 + (i + 1) / FPS - time.monotonic()))


def _join(port, seconds, path):
    # rw_timeout: once the feed ends the server sends nothing more; without it
    # the read would wait forever and the recording would never be closed.
    src = av.open(
        "http://127.0.0.1:%d/cam.ts" % port, options={"rw_timeout": "5000000"}
    )
    dst = av.open(path, "w")
    vin, ain = src.streams.video[0], src.streams.audio[0]
    vo, ao = dst.add_stream_from_template(vin), dst.add_stream_from_template(ain)
    t0 = time.monotonic()
    try:
        for pkt in src.demux(vin, ain):
            if pkt.dts is None:
                continue
            pkt.stream = vo if pkt.stream is vin else ao
            dst.mux(pkt)
            if time.monotonic() - t0 > seconds:
                break
    except (av.error.ExitError, av.error.EOFError, OSError):
        pass  # the stream went quiet: keep what was recorded
    src.close()
    dst.close()


def _offsets(path):
    c = av.open(path)
    flashes = []
    for f in c.decode(video=0):
        if f.to_ndarray(format="gray").mean() > 200:
            t = float(f.pts * f.time_base)
            if not flashes or t - flashes[-1] > 1.0:
                flashes.append(t)
    c.close()
    c = av.open(path)
    clicks = []
    for f in c.decode(audio=0):
        x = np.abs(f.to_ndarray()).max(axis=0)
        t0 = float(f.pts * f.time_base)
        for k in np.nonzero(x > 0.3)[0]:
            t = t0 + k / f.sample_rate
            if not clicks or t - clicks[-1] > 1.0:
                clicks.append(t)
    c.close()
    return [
        b - min(flashes, key=lambda x: abs(x - b))
        for b in clicks
        if flashes and min(abs(x - b) for x in flashes) < 1.0
    ]


def _tee():
    srv = TsRouter(0)
    srv.start()
    tee = TsTee(srv.channel("/cam.ts"))
    tee.start()
    return srv, tee


def test_any_join_gets_sound_and_picture_in_step(tmp_path):
    srv, tee = _tee()
    try:
        video, aac = _media(24, flash_every=3)  # not 2 s: the reorder slack is 2 s
        feeder = threading.Thread(
            target=_feed, args=(tee.session(), video, aac), daemon=True
        )
        feeder.start()
        found = []
        # At the start, then twice mid-GOP; every join ends well before the feed.
        for k, wait in enumerate((0.2, 1.1, 1.7)):
            time.sleep(wait)
            out = str(tmp_path / ("j%d.mp4" % k))
            _join(srv.port, 4.5, out)
            offs = _offsets(out)
            assert offs, "join %d saw no flash/click pair" % k
            found += offs
        feeder.join()
        assert all(abs(o) <= 0.07 for o in found), found  # within one video frame
    finally:
        tee.close()
        srv.close()


def test_a_consumer_stays_through_a_camera_reconnect_without_time_going_back(tmp_path):
    # Each session's media times restart at 0; the tee continues its timeline.
    srv, tee = _tee()
    try:
        video, aac = _media(7, flash_every=3)
        out = str(tmp_path / "r.mp4")
        joiner = threading.Thread(target=_join, args=(srv.port, 13.0, out), daemon=True)
        joiner.start()
        time.sleep(0.3)
        _feed(tee.session(), video, aac)
        _feed(tee.session(), video, aac)  # the camera reconnected
        joiner.join(20)
        c = av.open(out)
        vts = [
            float(p.pts * p.time_base) for p in c.demux(video=0) if p.pts is not None
        ]
        c.close()
        assert all(b > a for a, b in zip(vts, vts[1:])), "video time went backwards"
        assert 12.0 < max(vts) - min(vts) < 15.0, (min(vts), max(vts))
        offs = _offsets(out)
        assert len(offs) >= 3 and all(abs(o) <= 0.07 for o in offs), offs
    finally:
        tee.close()
        srv.close()


def test_a_full_queue_drops_to_the_next_keyframe_never_single_audio_frames():
    srv = TsRouter(0)
    srv.start()
    tee = TsTee(srv.channel("/cam.ts"), queue_max=4)  # not started: nothing drains
    try:
        sess = tee.session()
        t0 = time.monotonic()
        sess.video(b"K", 0, True)
        for i in range(3):
            sess.aac(b"a", i * 1024)  # queue now full (4)
        sess.aac(b"a", 3 * 1024)  # overflow: resync
        sess.video(b"P", 6000, False)  # dropped: not a keyframe
        sess.aac(b"a", 4 * 1024)  # dropped
        assert time.monotonic() - t0 < 1.0
        st = tee.stats()
        assert st["overflows"] == 1 and st["dropped"] == 3
        # Space again, but only a keyframe may restart the flow.
        while not tee._q.empty():
            tee._q.get_nowait()
        sess.aac(b"a", 5 * 1024)
        assert tee._q.empty()
        sess.video(b"K", 12000, True)
        assert tee._q.qsize() == 1
        sess.aac(b"a", 6 * 1024)
        assert tee._q.qsize() == 2
    finally:
        tee.close()
        srv.close()


def test_close_stops_the_mux_thread():
    srv, tee = _tee()
    tee.close()
    tee.close()
    assert not tee.is_running()
    srv.close()


def test_an_old_session_still_writing_cannot_drag_the_timeline(tmp_path):
    # During a reconnect the old publisher can still be writing when the new one
    # starts. Each switch between them used to re-base the timeline, and 1.3 s
    # of interleaved media came out spanning 27.5 s.
    srv, tee = _tee()
    try:
        video, aac = _media(6, flash_every=3)
        out = str(tmp_path / "o.mp4")
        joiner = threading.Thread(target=_join, args=(srv.port, 8.0, out), daemon=True)
        joiner.start()
        time.sleep(0.3)
        old, new = tee.session(), tee.session()
        half = 2 * GOP  # the new session starts on a real keyframe, as publishers do
        t0, ai = time.monotonic(), 0
        for i, (au, kf) in enumerate(video):
            while ai < len(aac) and ai * 1024 <= i * 48000 // FPS:
                old.aac(aac[ai], ai * 1024)
                if i >= half:
                    new.aac(aac[ai], ai * 1024 - half * 3200)
                ai += 1
            old.video(au, i * 6000, kf)  # the old session never stops
            if i >= half:
                new.video(au, (i - half) * 6000, kf)
            time.sleep(max(0.0, t0 + (i + 1) / FPS - time.monotonic()))
        joiner.join(15)
        c = av.open(out)
        vts = [
            float(p.pts * p.time_base) for p in c.demux(video=0) if p.pts is not None
        ]
        c.close()
        assert all(b > a for a, b in zip(vts, vts[1:]))
        assert max(vts) - min(vts) < 8.0, (min(vts), max(vts))
        # The newer session owns the timeline: nothing it sends is lost to the
        # old one (measured: 89 of 90 frames; switching back and forth kept 61).
        assert len(vts) >= len(video) - 5, len(vts)
    finally:
        tee.close()
        srv.close()


class _Capture:
    """A channel that keeps the TS bytes and reports a chosen consumer count."""

    def __init__(self, consumers, started=None):
        self.buf, self.consumers = bytearray(), consumers
        self.started = consumers if started is None else started

    def write(self, b):
        self.buf += bytes(b)
        return len(b)

    def mark_keyframe(self):
        pass

    def consumer_count(self):
        return self.consumers

    def started_count(self):
        return self.started


def _session_starts(consumers, started=None):
    import io

    def drained():
        end = time.monotonic() + 10
        while not tee._q.empty():
            assert tee.is_running() and time.monotonic() < end, tee.stats()
            time.sleep(0.02)
        time.sleep(0.2)  # the last item is muxed

    ch = _Capture(consumers, started)
    tee = TsTee(ch)
    tee.start()
    video, aac = _media(2)
    _feed(tee.session(), video, aac, realtime=False)
    drained()
    _feed(tee.session(), video, aac, realtime=False)
    drained()
    tee.close()
    c = av.open(io.BytesIO(bytes(ch.buf)), format="mpegts")
    vts = [p.pts for p in c.demux(video=0) if p.pts is not None]
    c.close()
    assert len(vts) == 2 * len(video)
    return vts[0], vts[len(video)]  # each session's first frame


@pytest.mark.parametrize(
    ("consumers", "started", "restarts"),
    [(0, 0, True), (1, 1, False), (1, 0, True)],
)
def test_with_nobody_reading_a_new_session_restarts_the_timeline(
    consumers, started, restarts
):
    # The timeline only ever grew: across a day of sessions it would reach
    # MPEG-TS's 33-bit PTS wrap (26.5 h). With nobody reading no one can see
    # time go back, so a new session starts it over. A consumer still waiting
    # for its first keyframe has seen nothing: Home Assistant connects before
    # the camera session it asked for exists (measured live 2026-10-03, the
    # timeline kept growing through every cold start).
    first, second = _session_starts(consumers, started)
    assert first == 90000
    assert (second == 90000) is restarts, (first, second)
