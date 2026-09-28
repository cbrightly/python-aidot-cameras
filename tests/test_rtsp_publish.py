"""Direct RTSP publish (docs/DESIGN-direct-publish.md): the pieces, and the
Popen-compatible SDES publisher end to end against a go2rtc-shaped server.

The fake server below mirrors what go2rtc 1.9.14's RTSP server enforces for a
publisher (pkg/rtsp/server.go, internal/rtsp/rtsp.go): ANNOUNCE needs
``Content-Type: application/sdp``; SETUP accepts only TCP-interleaved
transport (461 otherwise); a publish to a stream that does not exist is
answered 200 all the way through RECORD and then closed.
"""

from __future__ import annotations

import math
import logging
import random
import re
import socket
import statistics
import struct
import subprocess
import threading
import time

import pytest

from aidot_cameras.camera import rtsp_publish as rp
from aidot_cameras.g711 import linear2alaw

# --------------------------------------------------------------------------- #
# fake go2rtc                                                                  #
# --------------------------------------------------------------------------- #


class FakeGo2rtc:
    def __init__(self, streams=("aidot_cam",)):
        self.streams = set(streams)
        self.requests: list[str] = []
        self.announced: list[str] = []
        self.transports: list[str] = []
        self.frames: list[tuple[int, bytes]] = []
        self.options_after_record = 0
        self.recording = threading.Event()
        self.closed = threading.Event()
        self._srv = socket.socket()
        self._srv.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        self._srv.bind(("127.0.0.1", 0))
        self._srv.listen(4)
        self.port = self._srv.getsockname()[1]
        self._conns: list[socket.socket] = []
        threading.Thread(target=self._accept, daemon=True).start()

    def url(self, name="aidot_cam"):
        return f"rtsp://127.0.0.1:{self.port}/{name}"

    def stop(self):
        for c in self._conns:
            try:
                c.close()
            except OSError:
                pass
        self._srv.close()

    def drop_publisher(self):
        for c in self._conns:
            try:
                c.shutdown(socket.SHUT_RDWR)
            except OSError:
                pass

    def _accept(self):
        while True:
            try:
                c, _ = self._srv.accept()
            except OSError:
                return
            self._conns.append(c)
            threading.Thread(target=self._serve, args=(c,), daemon=True).start()

    def _serve(self, c):
        buf = b""
        stream_ok = True
        recording = False
        try:
            while True:
                while buf.startswith(b"$") and len(buf) >= 4:
                    n = struct.unpack_from("!H", buf, 2)[0]
                    if len(buf) < 4 + n:
                        break
                    self.frames.append((buf[1], buf[4 : 4 + n]))
                    buf = buf[4 + n :]
                if not buf.startswith(b"$") and b"\r\n\r\n" in buf:
                    head, _, rest = buf.partition(b"\r\n\r\n")
                    lines = head.decode().split("\r\n")
                    hdr = {}
                    for ln in lines[1:]:
                        k, _, v = ln.partition(":")
                        hdr[k.strip().lower()] = v.strip()
                    clen = int(hdr.get("content-length", 0))
                    if len(rest) < clen:
                        chunk = c.recv(65536)
                        if not chunk:
                            return
                        buf += chunk
                        continue
                    body, buf = rest[:clen], rest[clen:]
                    method, uri, _ = lines[0].split(" ", 2)
                    self.requests.append(method)
                    cseq = hdr.get("cseq", "0")
                    status, extra = 200, ""
                    if method == "ANNOUNCE":
                        if hdr.get("content-type") != "application/sdp":
                            status = 400
                        self.announced.append(body.decode())
                        stream_ok = uri.rsplit("/", 1)[-1] in self.streams
                    elif method == "SETUP":
                        tr = hdr.get("transport", "")
                        self.transports.append(tr)
                        if "RTP/AVP/TCP" not in tr:
                            status = 461
                        else:
                            extra = f"Transport: {tr}\r\nSession: 1234;timeout=60\r\n"
                    elif method == "OPTIONS" and recording:
                        self.options_after_record += 1
                    elif method == "TEARDOWN":
                        return
                    c.sendall(
                        f"RTSP/1.0 {status} X\r\nCSeq: {cseq}\r\n{extra}\r\n".encode()
                    )
                    if method == "RECORD":
                        recording = True
                        self.recording.set()
                        if not stream_ok:
                            return  # go2rtc: 200 to RECORD, then close
                    continue
                chunk = c.recv(65536)
                if not chunk:
                    return
                buf += chunk
        except OSError:
            return
        finally:
            self.closed.set()
            try:
                c.close()
            except OSError:
                pass


@pytest.fixture
def go2rtc():
    srv = FakeGo2rtc()
    yield srv
    srv.stop()


def _wait(pred, timeout=5.0):
    end = time.monotonic() + timeout
    while time.monotonic() < end:
        if pred():
            return True
        time.sleep(0.02)
    return pred()


# --------------------------------------------------------------------------- #
# SDP                                                                          #
# --------------------------------------------------------------------------- #

# What the SDES open writes for a plain-RTP camera, after narrowing.
SERVE_SDP = (
    "v=0\r\n"
    "o=- 1 1 IN IP4 0.0.0.0\r\n"
    "s=aidot-tutk-rx\r\n"
    "t=0 0\r\n"
    "m=audio 40002 RTP/AVP 8\r\n"
    "c=IN IP4 127.0.0.1\r\n"
    "a=rtpmap:8 PCMA/8000\r\n"
    "a=rtcp-mux\r\n"
    "m=video 40004 RTP/AVP 96\r\n"
    "c=IN IP4 127.0.0.1\r\n"
    "a=rtpmap:96 H264/90000\r\n"
    "a=fmtp:96 packetization-mode=1;profile-level-id=42e01f;"
    "sprop-parameter-sets=Z0IAH5WoFAFuQA==,aM48gA==\r\n"
    "a=rtcp-mux\r\n"
)


def test_publish_sdp_keeps_codecs_and_drops_transport_details():
    sdp, tracks, ports = rp.publish_sdp_from_serve_sdp(SERVE_SDP)
    assert ports == [40002, 40004]
    assert [(t.kind, t.pt, t.codec, t.clock_rate, t.channel) for t in tracks] == [
        ("audio", 8, "PCMA", 8000, 0),
        ("video", 96, "H264", 90000, 2),
    ]
    assert "m=audio 0 RTP/AVP 8" in sdp and "m=video 0 RTP/AVP 96" in sdp
    assert "sprop-parameter-sets=Z0IAH5WoFAFuQA==,aM48gA==" in sdp
    assert "a=control:trackID=0" in sdp and "a=control:trackID=1" in sdp
    for gone in ("rtcp-mux", "crypto", "sendonly", "40002", "40004"):
        assert gone not in sdp
    assert sdp.endswith("\r\n")


def test_publish_sdp_uses_only_the_first_payload_type_and_static_rtpmap():
    sdp, tracks, _ = rp.publish_sdp_from_serve_sdp(
        "v=0\r\nm=audio 5 RTP/AVP 8 0\r\nm=video 6 RTP/AVP 97 96\r\n"
        "a=rtpmap:96 H264/90000\r\na=rtpmap:97 H265/90000\r\n"
    )
    assert [(t.pt, t.codec) for t in tracks] == [(8, "PCMA"), (97, "H265")]
    assert "a=rtpmap:8 PCMA/8000" in sdp
    assert "H264" not in sdp


def test_publish_sdp_can_leave_audio_out():
    sdp, tracks, ports = rp.publish_sdp_from_serve_sdp(SERVE_SDP, ("video",))
    assert [t.kind for t in tracks] == ["video"] and tracks[0].channel == 0
    assert ports == [40004]
    assert "m=audio" not in sdp


def test_publish_sdp_rejects_dynamic_pt_without_rtpmap():
    with pytest.raises(ValueError):
        rp.publish_sdp_from_serve_sdp("v=0\r\nm=video 6 RTP/AVP 96\r\n")


def test_free_dynamic_pt_skips_used_payload_types():
    t = [
        rp.PublishTrack("video", 97, 90000, "H264", 0),
        rp.PublishTrack("audio", 8, 8000, "PCMA", 1),
    ]
    assert rp._free_dynamic_pt(t) == 98


# --------------------------------------------------------------------------- #
# RTP                                                                          #
# --------------------------------------------------------------------------- #


def test_rtp_round_trip_and_header_stripping():
    pkt = rp.build_rtp(96, True, 65535, 0xFFFFFFFF, 0x1234, b"abc")
    assert rp.parse_rtp(pkt) == (96, True, 65535, 0xFFFFFFFF, b"abc")
    # 1 CSRC + a one-word extension + 2 bytes of padding
    hdr = struct.pack("!BBHII", 0x80 | 0x20 | 0x10 | 1, 8, 7, 9, 1) + b"\0\0\0\1"
    ext = struct.pack("!HH", 0xBEDE, 1) + b"\1\2\3\4"
    parsed = rp.parse_rtp(hdr + ext + b"xy" + b"\0\2")
    assert parsed == (8, False, 7, 9, b"xy")


def test_rtcp_and_garbage_are_not_rtp():
    assert rp.parse_rtp(struct.pack("!BBH", 0x80, 200, 1) + b"\0" * 8) is None
    assert rp.parse_rtp(b"\x00\x01") is None
    assert rp.parse_rtp(b"\x40" + b"\0" * 20) is None  # version 1


# --------------------------------------------------------------------------- #
# timeline                                                                     #
# --------------------------------------------------------------------------- #


def _tl(policy="hybrid"):
    return rp.RtpTimeline(90000, policy=policy)


def test_timeline_passes_sane_camera_deltas_and_shares_frame_timestamps():
    tl = _tl()
    s0, t0 = tl.stamp(1000, 0.0)
    s1, t1 = tl.stamp(1000, 0.01)  # same frame
    s2, t2 = tl.stamp(7000, 0.5)  # next frame, arrival says otherwise
    assert t1 == t0 and s1 == (s0 + 1) & 0xFFFF
    assert (t2 - t0) & 0xFFFFFFFF == 6000
    assert s2 == (s0 + 2) & 0xFFFF and tl.repairs == 0


def test_timeline_repairs_a_backward_step_by_arrival():
    """The A001513 case: in sequence, but ~1.7 s in the past."""
    tl = _tl()
    _, t0 = tl.stamp(500_000, 10.0)
    _, t1 = tl.stamp(500_000 - 153_000, 10.066)
    assert (t1 - t0) & 0xFFFFFFFF == round(0.066 * 90000)
    _, t2 = tl.stamp(500_000 - 153_000 + 6000, 10.133)
    assert (t2 - t1) & 0xFFFFFFFF == 6000
    assert tl.repairs == 1


def test_timeline_repairs_a_forward_jump_and_handles_wrap():
    tl = _tl()
    _, t0 = tl.stamp(0xFFFFF000, 1.0)
    _, t1 = tl.stamp(0x00000800, 1.03)  # wraps: +0x1800
    assert (t1 - t0) & 0xFFFFFFFF == 0x1800 and tl.repairs == 0
    _, t2 = tl.stamp(0x00000800 + 90000 * 60, 1.063)  # +60 s: bogus
    assert (t2 - t1) & 0xFFFFFFFF == round(0.033 * 90000) and tl.repairs == 1


def test_timeline_never_stalls_on_zero_arrival_delta():
    tl = _tl("arrival")
    _, t0 = tl.stamp(1, 5.0)
    _, t1 = tl.stamp(2, 5.0)
    assert (t1 - t0) & 0xFFFFFFFF == 1


def test_timeline_camera_policy_trusts_the_camera():
    tl = _tl("camera")
    _, t0 = tl.stamp(100_000, 0.0)
    _, t1 = tl.stamp(100_000 + 90000 * 10, 0.1)
    assert (t1 - t0) & 0xFFFFFFFF == 900_000 and tl.repairs == 0


def test_timestamp_policy_env(monkeypatch):
    monkeypatch.setenv(rp.ENV_PUBLISH_TIMESTAMPS, "ARRIVAL")
    assert rp.timestamp_policy() == "arrival"
    monkeypatch.setenv(rp.ENV_PUBLISH_TIMESTAMPS, "nonsense")
    assert rp.timestamp_policy() == "hybrid"


# --------------------------------------------------------------------------- #
# steered timeline: the camera's spacing, locked to real time                  #
# --------------------------------------------------------------------------- #


def _steer_frames(tl, frames):
    """Stamp ``(in_ts, arrival)`` frames, two packets each (the second shares
    the first's timestamp), and return ``(arrival, output seconds)`` for every
    NEW output timestamp, both relative to the first frame."""
    rows = []
    for in_ts, arrival in frames:
        _, out = tl.stamp(in_ts, arrival)
        _, again = tl.stamp(in_ts, arrival + 0.0005)
        assert again == out  # one frame, one timestamp
        rows.append((arrival, out))
    a0, o0 = rows[0]
    return [(a - a0, ((o - o0) & 0xFFFFFFFF) / tl.clock_rate) for a, o in rows]


def _camera_frames(
    rng, fps, seconds, *, start_ts=1000, t0=100.0, step=6000, captures=None
):
    """Frames captured every ``1 / fps`` wall seconds from ``t0`` and delivered
    with +-15 ms of jitter. Each capture instant is appended to ``captures``
    when given."""
    frames, ts = [], start_ts
    for i in range(int(seconds * fps)):
        frames.append((ts & 0xFFFFFFFF, t0 + i / fps + rng.uniform(-0.015, 0.015)))
        if captures is not None:
            captures.append(t0 + i / fps)
        ts += step
    return frames


#: Seeds every fast-clock steered test must pass on, not just one.
STEER_SEEDS = range(1, 31)


def _vs_capture(rows, captures):
    """``(wall, output s)`` rows with wall = each frame's capture instant, both
    relative to the first frame. Capture, not arrival: delivery jitter is not
    the output's wander."""
    c0 = captures[0]
    return [(c - c0, o) for c, (_, o) in zip(captures, rows)]


def _locked(rows, since, *, offset_since=None):
    """What the steered clock guarantees on a fast camera clock, over the
    ``(wall, output s)`` rows from wall ``since``: ``(rate, wander, offset)``
    with rate = output / wall advance, wander = max - min of output - wall,
    and offset = the largest ``|output - wall|`` from ``offset_since`` (default
    ``since``). A fast clock locks at a constant offset, not onto wall time."""
    tail = [(w, o) for w, o in rows if w >= since]
    (w1, o1), (w2, o2) = tail[0], tail[-1]
    diff = [o - w for w, o in tail]
    start = since if offset_since is None else offset_since
    offset = max(abs(o - w) for w, o in rows if w >= start)
    return (o2 - o1) / (w2 - w1), max(diff) - min(diff), offset


def _assert_locked(seed, rate, wander, offset, *, offset_max=0.2):
    assert 0.999 <= rate <= 1.001, f"seed {seed}: rate {rate:.5f}"
    assert wander < 0.05, f"seed {seed}: wander {wander:.4f}"
    assert offset < offset_max, f"seed {seed}: offset {offset:.4f}"


def test_steered_locks_a_fast_camera_clock_to_real_time():
    """The SDES camera case: a 15 fps clock (+6000 ticks) on ~16.1 fps of
    delivery. hybrid runs ~7% slow; steered must run at real time and stay
    smoother than arrival stamping."""
    for seed in STEER_SEEDS:
        captures = []
        frames = _camera_frames(random.Random(seed), 16.1, 120, captures=captures)
        rows = _steer_frames(rp.RtpTimeline(90000, policy="steered"), frames)
        wall, out = rows[-1]
        assert abs(out / wall - 1.0) <= 0.005, f"seed {seed}"
        _assert_locked(seed, *_locked(_vs_capture(rows, captures), 20.0))
        steps = [b[1] - a[1] for a, b in zip(rows, rows[1:])]
        # steered measures ~1.5 ms of step-to-step jitter here, nearly all of
        # it easing onto the rate first learned at 6 s (~0.01 ms after 20 s);
        # arrival stamping on the same stream measures ~11.9 ms.
        assert statistics.pstdev(steps) < 0.003, f"seed {seed}"


def test_steered_keeps_an_accurate_camera_as_is():
    rng = random.Random(1)
    rows = _steer_frames(
        rp.RtpTimeline(90000, policy="steered"), _camera_frames(rng, 15.0, 60)
    )
    steps = [b[1] - a[1] for a, b in zip(rows, rows[1:])]
    assert abs(statistics.mean(steps) - 1 / 15) <= 0.01 / 15
    wall, out = rows[-1]
    assert abs(out / wall - 1.0) <= 0.005


def test_steered_rides_through_a_stall():
    """20 s of an accurate camera, 4 s of nothing, then the camera resumes
    with a timestamp delta that covers the 4 s."""
    rng = random.Random(1)
    before = _camera_frames(rng, 15.0, 20)
    last_ts = before[-1][0]
    resume = 100.0 + (len(before) - 1) / 15 + 4.0 + 1 / 15
    after = _camera_frames(
        rng, 15.0, 10, start_ts=last_ts + 6000 + 4 * 90000, t0=resume
    )
    rows = _steer_frames(rp.RtpTimeline(90000, policy="steered"), before + after)
    outs = [o for _, o in rows]
    assert all(b > a for a, b in zip(outs, outs[1:]))
    settled = resume - before[0][1] + 5.0
    tail = [abs(o - w) for w, o in rows if w >= settled]
    assert tail and max(tail) < 0.10


def test_steered_never_steps_backward_on_a_camera_backward_jump():
    """The A001513 case: ~1.7 s back in the camera's own timestamps."""
    rng = random.Random(1)
    frames = _camera_frames(rng, 15.0, 60, start_ts=500_000)
    back = int(1.7 * 90000)
    frames = frames[:450] + [((ts - back) & 0xFFFFFFFF, a) for ts, a in frames[450:]]
    tl = rp.RtpTimeline(90000, policy="steered")
    rows = _steer_frames(tl, frames)
    outs = [o for _, o in rows]
    assert all(b > a for a, b in zip(outs, outs[1:]))
    assert tl.repairs >= 1


def _delivery_stall(seed, after_s=50):
    """30 s of an accurate 15 fps camera, a 1.7 s DELIVERY stall whose backlog
    arrives in a ~0.1 s burst, then ``after_s`` more. Delivery resumes from the
    burst's end, so the arrival floor stays ~1/15 s later than before the
    stall. Returns the frames, each frame's capture time and the index of the
    first frame after the burst."""
    rng = random.Random(seed)
    fps = 15.0
    step = 6000
    t0 = 100.0
    n1 = int(30 * fps)
    n_stall = round(1.7 * fps)
    n3 = int(after_s * fps)

    gen_times = []
    frames = []  # (in_ts, arrival)
    ts = 1000

    for i in range(n1):
        gen = t0 + i / fps
        gen_times.append(gen)
        frames.append((ts & 0xFFFFFFFF, gen + rng.uniform(-0.015, 0.015)))
        ts += step

    stall_end_wall = frames[-1][1] + 1.7
    for j in range(n_stall):
        gen_times.append(t0 + (n1 + j) / fps)
        frames.append((ts & 0xFFFFFFFF, stall_end_wall + (j / n_stall) * 0.1))
        ts += step

    wall = frames[-1][1]
    for k in range(n3):
        gen_times.append(t0 + (n1 + n_stall + k) / fps)
        wall += 1 / fps
        frames.append((ts & 0xFFFFFFFF, wall + rng.uniform(-0.015, 0.015)))
        ts += step
    return frames, gen_times, n1 + n_stall


def test_steered_rides_through_a_delivery_stall_without_snapping():
    """A 1.7 s DELIVERY stall, not a capture gap: the camera's own clock
    stays continuous through it (every frame still steps by one normal
    +6000 tick), but the backlog generated during the stall arrives in a
    ~0.1 s burst once the connection catches up. The steered output must
    keep riding the camera's spacing through this, not snap onto the late
    burst arrival. Delivery then stays ~1/15 s later than before, which the
    output cannot tell from a phase step: it follows it to a new constant
    offset, so rate and wander are measured 20 s after the burst."""
    clock_rate = 90000
    for seed in STEER_SEEDS:
        frames, gen_times, after = _delivery_stall(seed)
        tl = rp.RtpTimeline(clock_rate, policy="steered")
        outs = [tl.stamp(in_ts, arrival)[1] for in_ts, arrival in frames]
        outs_s = [((o - outs[0]) & 0xFFFFFFFF) / clock_rate for o in outs]

        assert tl.repairs == 0, f"seed {seed}"
        assert all(b > a for a, b in zip(outs_s, outs_s[1:])), f"seed {seed}"
        steps = [b - a for a, b in zip(outs_s, outs_s[1:])]
        assert max(steps) < 0.2, f"seed {seed}"

        gen0 = gen_times[0]
        rows = [(g - gen0, o) for g, o in zip(gen_times, outs_s)]
        since = gen_times[after] - gen0 + 20.0
        _assert_locked(seed, *_locked(rows, since, offset_since=20.0), offset_max=0.1)


def _gap_frames(
    fps, before_s, gap_s, after_s, *, step=6000, jump=None, seed=1, captures=None
):
    """``before_s`` of frames, ``gap_s`` of silence, then ``after_s`` more.
    The camera stamp advances one normal step across the gap (an uncovered
    gap) unless ``jump`` gives the stamp's own jump in ticks. ``seed`` seeds
    the jitter; ``captures`` is as for ``_camera_frames``. Returns the frames
    and the index of the first frame after the gap."""
    rng = random.Random(seed)
    before = _camera_frames(rng, fps, before_s, step=step, captures=captures)
    last_ts, last_arrival = before[-1]
    after = _camera_frames(
        rng,
        fps,
        after_s,
        start_ts=(last_ts + (step if jump is None else jump)) & 0xFFFFFFFF,
        t0=last_arrival + gap_s,
        step=step,
        captures=captures,
    )
    return before + after, len(before)


def test_steered_snaps_across_an_uncovered_gap():
    """An uncovered gap - the camera stamp advances only one normal step
    across a 4 s silence - is a real phase error (capture stopped, not a
    delivery artifact) and must snap onto wall time once the latency has
    stayed high for the snap hold."""
    frames, first = _gap_frames(15.0, 20, 4.0, 3)
    tl = rp.RtpTimeline(90000, policy="steered")
    rows = _steer_frames(tl, frames)
    held = rows[first][0] + rp.STEER_SNAP_HOLD_S
    tail = [abs(o - w) for w, o in rows if w >= held]
    assert tl.repairs == 1
    assert len(tail) >= 15 and max(tail) < 0.1


def test_steered_relearns_rate_cleanly_after_an_uncovered_gap():
    frames, first = _gap_frames(16.1, 20, 4.0, 30)
    tl = rp.RtpTimeline(90000, policy="steered")
    rows = _steer_frames(tl, frames)
    tail = [abs(o - w) for w, o in rows if w >= rows[first][0] + 1.0]
    assert tl.repairs == 1
    assert max(tail) < 0.10


def test_steered_snaps_a_12s_uncovered_gap():
    """12 s of silence is longer than the floor window: the floor must come
    from the frame before the gap, not the late frame itself."""
    frames, first = _gap_frames(15.0, 20, 12.0, 10)
    tl = rp.RtpTimeline(90000, policy="steered")
    rows = _steer_frames(tl, frames)
    tail = [abs(o - w) for w, o in rows if w >= rows[first][0] + 1.5]
    assert tl.repairs == 1
    assert max(tail) < 0.1


def test_steered_snaps_a_second_gap_inside_the_hold():
    """Two 4 s uncovered gaps with three frames between them: the second gap
    starts while the first snap is still held, and its excess must be
    snapped as well."""
    rng = random.Random(1)
    frames = _camera_frames(rng, 15.0, 20)
    for run_s in (0.25, 20.0):
        last_ts, last_arrival = frames[-1]
        second = len(frames)
        frames += _camera_frames(
            rng,
            15.0,
            run_s,
            start_ts=(last_ts + 6000) & 0xFFFFFFFF,
            t0=last_arrival + 4.0,
        )
    tl = rp.RtpTimeline(90000, policy="steered")
    rows = _steer_frames(tl, frames)
    held = rows[second][0] + rp.STEER_SNAP_HOLD_S
    tail = [abs(o - w) for w, o in rows if w >= held]
    assert tl.repairs >= 1
    assert max(tail) < 0.1


def test_steered_snaps_two_short_gaps_that_add_up_in_the_floor_window():
    """Two 1.5 s uncovered gaps 4 s apart: neither alone is over STEER_SNAP_S,
    but the second leaves latency 3 s above the floor window's minimum, so the
    output snaps once and lands back on wall time."""
    rng = random.Random(1)
    frames = _camera_frames(rng, 15.0, 20)
    for run_s in (4.0, 20.0):
        last_ts, last_arrival = frames[-1]
        second = len(frames)
        frames += _camera_frames(
            rng,
            15.0,
            run_s,
            start_ts=(last_ts + 6000) & 0xFFFFFFFF,
            t0=last_arrival + 1.5,
        )
    tl = rp.RtpTimeline(90000, policy="steered")
    rows = _steer_frames(tl, frames)
    held = rows[second][0] + rp.STEER_SNAP_HOLD_S + 0.5
    tail = [abs(o - w) for w, o in rows if w >= held]
    assert tl.repairs == 1
    assert max(tail) < 0.1


def test_steered_snap_before_learning_keeps_phase():
    """A fast camera clock with an uncovered gap before any rate is learned:
    the snap must not move the base the first learned rate rebases from. The
    snap restarts learning, so the lock is measured 20 s after the gap."""
    biases = []
    for seed in STEER_SEEDS:
        captures = []
        frames, first = _gap_frames(16.1, 3, 4.0, 60, seed=seed, captures=captures)
        rows = _vs_capture(
            _steer_frames(rp.RtpTimeline(90000, policy="steered"), frames), captures
        )
        since = rows[first][0] + 20.0
        _assert_locked(seed, *_locked(rows, since))
        biases.append(statistics.mean(o - w for w, o in rows if w >= since))
    # The snap jumps by the excess measured where the gap began, so its
    # constant offset averages ~0 across seeds; measured at the snap instead
    # (after the gap and hold ran at the provisional rate) it sat ~78 ms behind.
    assert abs(statistics.mean(biases)) < 0.03


def test_steered_repair_gap_on_a_fast_clock():
    """A 20 s gap across which the camera stamp jumps more than max_step: the
    repair must advance the target by exactly the wall gap, not the wall gap
    times the learned rate."""
    for seed in STEER_SEEDS:
        captures = []
        frames, _ = _gap_frames(
            16.1, 30, 20.0, 20, jump=25 * 90000, seed=seed, captures=captures
        )
        tl = rp.RtpTimeline(90000, policy="steered")
        rows = _vs_capture(_steer_frames(tl, frames), captures)
        assert tl.repairs >= 1, f"seed {seed}"
        _assert_locked(seed, *_locked(rows, 20.0))


def test_steered_low_fps_fast_clock():
    """A 5 fps camera clock (+18000 ticks) delivered at ~5.37 fps."""
    for seed in STEER_SEEDS:
        captures = []
        frames = _camera_frames(
            random.Random(seed), 5.367, 60, step=18000, captures=captures
        )
        rows = _steer_frames(rp.RtpTimeline(90000, policy="steered"), frames)
        _assert_locked(seed, *_locked(_vs_capture(rows, captures), 20.0))


def _covered_stall(stall_s):
    """An accurate 15 fps camera whose DELIVERY stalls for ``stall_s`` at
    20 s; the camera stamps stay continuous and the held frames arrive as one
    burst. Returns the frames and each frame's capture time."""
    rng = random.Random(1)
    fps, t0, start = 15.0, 100.0, 20.0
    captures = [i / fps for i in range(int(60 * fps))]
    arrivals = []
    for c in captures:
        if start <= c < start + stall_s:
            arrivals.append(t0 + start + stall_s + 0.05)  # held, released together
        else:
            arrivals.append(t0 + c + 0.05 + rng.uniform(-0.01, 0.01))
    frames = [
        ((1000 + 6000 * i) & 0xFFFFFFFF, a) for i, a in enumerate(_monotonic(arrivals))
    ]
    return frames, captures


def test_steered_rides_a_covered_4s_stall_without_offset():
    frames, captures = _covered_stall(4.0)
    tl = rp.RtpTimeline(90000, policy="steered")
    rows = _steer_frames(tl, frames)
    tail = [abs(o - c) for (_, o), c in zip(rows, captures) if c >= 20.0]
    assert tl.repairs == 0
    assert max(tail) < 0.05


def test_steered_rides_a_covered_12s_stall_without_offset():
    frames, captures = _covered_stall(12.0)
    tl = rp.RtpTimeline(90000, policy="steered")
    rows = _steer_frames(tl, frames)
    tail = [abs(o - c) for (_, o), c in zip(rows, captures) if c >= 20.0]
    assert tl.repairs == 0
    assert max(tail) < 0.05


def _draining_stall(stall_s, speed):
    """An accurate 15 fps camera whose DELIVERY stalls for ``stall_s`` at
    20 s; the held frames then drain at ``speed`` times real time, not at
    once, until delivery has caught up. Returns the frames, each frame's
    capture time and the capture time at which the backlog has drained."""
    rng = random.Random(1)
    fps, t0, start = 15.0, 100.0, 20.0
    captures = [i / fps for i in range(int(60 * fps))]
    arrivals, drained = [], None
    for c in captures:
        on_time = t0 + c + 0.05 + rng.uniform(-0.01, 0.01)
        if c < start:
            arrivals.append(on_time)
        elif drained is None:
            queued = (
                arrivals[-1] + 1 / (fps * speed)
                if c > start
                else t0 + start + stall_s + 0.05
            )
            if on_time >= queued:
                drained = c
            arrivals.append(max(on_time, queued))
        else:
            arrivals.append(on_time)
    frames = [
        ((1000 + 6000 * i) & 0xFFFFFFFF, a) for i, a in enumerate(_monotonic(arrivals))
    ]
    return frames, captures, drained


def test_steered_rides_a_covered_4s_stall_draining_at_2x_and_5x():
    """A covered 4 s stall whose backlog drains at 2x or 5x real time: the
    excess stays over STEER_SNAP_S for longer than the snap hold, but it is
    falling, so it is a draining backlog and must not snap."""
    for speed in (2.0, 5.0):
        frames, captures, drained = _draining_stall(4.0, speed)
        tl = rp.RtpTimeline(90000, policy="steered")
        rows = _steer_frames(tl, frames)
        tail = [abs(o - c) for (_, o), c in zip(rows, captures) if c >= drained + 2]
        assert tl.repairs == 0, f"{speed}x"
        assert max(tail) < 0.1, f"{speed}x"


def test_steered_rides_a_covered_12s_stall_draining_at_2x_and_5x():
    for speed in (2.0, 5.0):
        frames, captures, drained = _draining_stall(12.0, speed)
        tl = rp.RtpTimeline(90000, policy="steered")
        rows = _steer_frames(tl, frames)
        tail = [abs(o - c) for (_, o), c in zip(rows, captures) if c >= drained + 2]
        assert tl.repairs == 0, f"{speed}x"
        assert max(tail) < 0.1, f"{speed}x"


def _monotonic(arrivals, gap=0.001):
    """Arrival times as a real clock reports them: never before the last."""
    out = []
    for a in arrivals:
        out.append(max(a, out[-1] + gap) if out else a)
    return out


def test_steered_keeps_a_cold_start_backlog_at_camera_spacing():
    """The cold-start backlog: an SDES camera sometimes delivers ~1.9 s of
    camera time in its first ~0.1 s. Those frames were captured earlier than
    they arrive, so their camera spacing is the truth; the steered output
    must not compress them toward the burst's arrival times."""
    rng = random.Random(1)
    fps, t0 = 15.0, 100.0
    captures = [i / fps for i in range(int(90 * fps))]
    backlog = [c for c in captures if c < 1.9]
    arrivals = [t0 + 1.9 + 0.1 * j / len(backlog) for j in range(len(backlog))]
    arrivals += [
        t0 + c + 0.05 + rng.uniform(-0.01, 0.01) for c in captures[len(backlog) :]
    ]
    frames = [
        ((1000 + 6000 * i) & 0xFFFFFFFF, a) for i, a in enumerate(_monotonic(arrivals))
    ]
    tl = rp.RtpTimeline(90000, policy="steered")
    rows = _steer_frames(tl, frames)
    err = max(abs(o - c) for (_, o), c in zip(rows, captures))
    assert tl.repairs == 0
    assert err < 0.05


def test_steered_learns_rate_from_the_least_late_frames():
    """An accurate camera whose delivery is held for 0.8 s every 5th second
    and then released at once. The held frames are late, not fast: the rate
    comes from the least-late frames, so output runs at the camera's rate
    and on its capture times."""
    rng = random.Random(1)
    fps, t0 = 15.0, 100.0
    captures = [i / fps for i in range(int(60 * fps))]
    arrivals = []
    for c in captures:
        sec = int(c + 1e-9)
        if sec and sec % 5 == 0 and c - sec < 0.8 - 1e-9:
            arrivals.append(t0 + sec + 0.8)  # held, released together
        else:
            arrivals.append(t0 + c + 0.05 + rng.uniform(-0.01, 0.01))
    frames = [
        ((1000 + 6000 * i) & 0xFFFFFFFF, a) for i, a in enumerate(_monotonic(arrivals))
    ]
    rows = _steer_frames(rp.RtpTimeline(90000, policy="steered"), frames)
    out = [o for _, o in rows]
    assert 0.999 <= out[-1] / captures[-1] <= 1.001
    tail = [abs(o - c) for o, c in zip(out, captures) if c >= 10.0]
    assert max(tail) < 0.05


def test_video_timestamp_policy(monkeypatch):
    monkeypatch.delenv(rp.ENV_PUBLISH_TIMESTAMPS, raising=False)
    assert rp.video_timestamp_policy() == "steered"
    monkeypatch.setenv(rp.ENV_PUBLISH_TIMESTAMPS, "hybrid")
    assert rp.video_timestamp_policy() == "hybrid"
    monkeypatch.setenv(rp.ENV_PUBLISH_TIMESTAMPS, "arrival")
    assert rp.video_timestamp_policy() == "arrival"
    monkeypatch.setenv(rp.ENV_PUBLISH_TIMESTAMPS, "garbage")
    assert rp.video_timestamp_policy() == "steered"
    assert rp.timestamp_policy() == "hybrid"
    monkeypatch.setenv(rp.ENV_PUBLISH_TIMESTAMPS, "steered")
    assert rp.timestamp_policy() == "steered"


def test_direct_publish_flag_defaults_off(monkeypatch):
    monkeypatch.delenv(rp.ENV_DIRECT_PUBLISH, raising=False)
    assert rp.direct_publish_enabled() is False
    monkeypatch.setenv(rp.ENV_DIRECT_PUBLISH, "on")
    assert rp.direct_publish_enabled() is True
    assert rp.is_publishable_url("rtsp://127.0.0.1:8554/x")
    assert not rp.is_publishable_url("http://127.0.0.1:1/x.ts")
    assert not rp.is_publishable_url("-")
    assert not rp.is_publishable_url(None)


# --------------------------------------------------------------------------- #
# H.264 packetizer                                                             #
# --------------------------------------------------------------------------- #


def _depacketize(payloads):
    """Reassemble RFC 6184 single-NAL/FU-A payloads into NAL units."""
    nals, cur = [], None
    for p in payloads:
        typ = p[0] & 0x1F
        if typ == 28:
            if p[1] & 0x80:
                cur = bytearray([(p[0] & 0xE0) | (p[1] & 0x1F)])
            cur += p[2:]
            if p[1] & 0x40:
                nals.append(bytes(cur))
                cur = None
        else:
            nals.append(bytes(p))
    return nals


def test_split_annexb_handles_both_start_code_lengths():
    au = b"\0\0\0\1\x67AB\0\0\1\x68C\0\0\0\1\x65" + b"D" * 5
    assert rp.split_annexb(au) == [b"\x67AB", b"\x68C", b"\x65DDDDD"]
    assert rp.split_annexb(b"") == []


def test_packetize_h264_fragments_and_reassembles_with_marker_last():
    sps, pps = b"\x67" + b"s" * 10, b"\x68" + b"p" * 4
    idr = b"\x65" + bytes(range(256)) * 20  # 5121 bytes -> FU-A
    au = b"\0\0\0\1" + sps + b"\0\0\0\1" + pps + b"\0\0\0\1" + idr
    out = rp.packetize_h264(au, mtu=1200)
    assert [m for _, m in out] == [False] * (len(out) - 1) + [True]
    assert all(len(p) <= 1200 for p, _ in out)
    assert _depacketize([p for p, _ in out]) == [sps, pps, idr]
    fus = [p for p, _ in out if p[0] & 0x1F == 28]
    assert fus[0][1] & 0x80 and fus[-1][1] & 0x40 and fus[0][0] & 0x60 == idr[0] & 0x60


# --------------------------------------------------------------------------- #
# A-law gain                                                                   #
# --------------------------------------------------------------------------- #


def test_alaw_decode_is_the_inverse_of_the_encoder():
    for a in range(256):
        assert linear2alaw(rp._alaw_to_linear(a)) == a


def test_alaw_gain_table():
    assert rp.alaw_gain_table(0) is None
    loud = rp.alaw_gain_table(6.0)
    quiet = rp.alaw_gain_table(-8.0)
    a = linear2alaw(4000)
    assert abs(rp._alaw_to_linear(loud[a])) > abs(rp._alaw_to_linear(a))
    assert abs(rp._alaw_to_linear(quiet[a])) < abs(rp._alaw_to_linear(a))
    # clips rather than wrapping
    top = linear2alaw(32000)
    assert rp._alaw_to_linear(loud[top]) > 30000


# --------------------------------------------------------------------------- #
# RtspPublisher against the go2rtc-shaped server                               #
# --------------------------------------------------------------------------- #


def test_publisher_handshake_matches_go2rtc(go2rtc):
    sdp, tracks, _ = rp.publish_sdp_from_serve_sdp(SERVE_SDP)
    pub = rp.RtspPublisher(go2rtc.url(), sdp, tracks)
    pub.connect()
    try:
        assert go2rtc.requests == ["OPTIONS", "ANNOUNCE", "SETUP", "SETUP", "RECORD"]
        assert go2rtc.announced == [sdp]
        assert go2rtc.transports == [
            "RTP/AVP/TCP;unicast;interleaved=0-1;mode=record",
            "RTP/AVP/TCP;unicast;interleaved=2-3;mode=record",
        ]
        pub.send_rtp(tracks[1], rp.build_rtp(96, True, 1, 2, 3, b"v"))
        pub.send_rtp(tracks[0], rp.build_rtp(8, False, 1, 2, 3, b"a"))
        assert _wait(lambda: len(go2rtc.frames) == 2)
        assert [ch for ch, _ in go2rtc.frames] == [2, 0]
        assert pub.packets_sent == 2 and pub.alive
    finally:
        pub.close()
    assert _wait(lambda: "TEARDOWN" in go2rtc.requests)


def test_publisher_sends_credentials_but_not_in_the_request_uri():
    srv = FakeGo2rtc()
    seen = []
    orig = srv._serve

    def spy(c):
        data = c.recv(4096, socket.MSG_PEEK)
        seen.append(data.decode(errors="replace"))
        orig(c)

    srv._serve = spy
    try:
        sdp, tracks, _ = rp.publish_sdp_from_serve_sdp(SERVE_SDP, ("video",))
        url = f"rtsp://user:p%40ss@127.0.0.1:{srv.port}/aidot_cam"
        pub = rp.RtspPublisher(url, sdp, tracks)
        pub.connect()
        pub.close()
        first = seen[0]
        assert "user:" not in first.split("\r\n")[0]
        assert re.search(r"Authorization: Basic dXNlcjpwQHNz", first)
        assert rp.redact_url(url) == f"rtsp://user:***@127.0.0.1:{srv.port}/aidot_cam"
    finally:
        srv.stop()


def test_publish_to_a_missing_stream_is_reported():
    srv = FakeGo2rtc(streams=())
    try:
        sdp, tracks, _ = rp.publish_sdp_from_serve_sdp(SERVE_SDP, ("video",))
        pub = rp.RtspPublisher(srv.url(), sdp, tracks)
        pub.connect()  # go2rtc answers 200 all the way...
        assert _wait(lambda: not pub.alive)  # ...then closes
        assert "does the stream exist" in pub.error
        with pytest.raises(rp.RtspPublishError):
            pub.send_rtp(tracks[0], b"x" * 12)
    finally:
        srv.stop()


def test_publisher_refused_connection_raises():
    s = socket.socket()
    s.bind(("127.0.0.1", 0))
    port = s.getsockname()[1]
    s.close()
    sdp, tracks, _ = rp.publish_sdp_from_serve_sdp(SERVE_SDP)
    with pytest.raises(OSError):
        rp.RtspPublisher(f"rtsp://127.0.0.1:{port}/x", sdp, tracks).connect()


def test_publisher_keepalive_is_options(go2rtc):
    sdp, tracks, _ = rp.publish_sdp_from_serve_sdp(SERVE_SDP)
    pub = rp.RtspPublisher(go2rtc.url(), sdp, tracks, keepalive_s=0.0)
    pub.connect()
    try:
        assert pub.keepalive_due()
        pub.send_keepalive()
        assert _wait(lambda: go2rtc.options_after_record == 1)
        time.sleep(0.2)
        assert pub.alive  # the reply was consumed by the reader, not fatal
    finally:
        pub.close()


# --------------------------------------------------------------------------- #
# LoopbackRtpPublisher: the Popen stand-in                                     #
# --------------------------------------------------------------------------- #


def _free_udp_ports(n):
    socks = [socket.socket(socket.AF_INET, socket.SOCK_DGRAM) for _ in range(n)]
    for s in socks:
        s.bind(("127.0.0.1", 0))
    ports = [s.getsockname()[1] for s in socks]
    for s in socks:
        s.close()
    return ports


def _serve_sdp(a_port, v_port):
    return SERVE_SDP.replace("40002", str(a_port)).replace("40004", str(v_port))


def _udp_port_bound(port):
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    try:
        s.bind(("127.0.0.1", port))
        return False
    except OSError:
        return True
    finally:
        s.close()


def test_loopback_publisher_forwards_and_rewrites(go2rtc, monkeypatch):
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "0")
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(
        _serve_sdp(a_port, v_port), go2rtc.url(), device_id="cam", audio_gain_db=0
    )
    try:
        # Bound on return: the SDES open's port-bind wait passes at once.
        assert _udp_port_bound(a_port) and _udp_port_bound(v_port)
        assert proc.poll() is None
        tx = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        # Sent before RECORD may have landed: must be held, not lost.
        tx.sendto(
            rp.build_rtp(96, False, 10, 5000, 0xAAAA, b"\x65key"), ("127.0.0.1", v_port)
        )
        tx.sendto(
            rp.build_rtp(96, True, 11, 5000, 0xAAAA, b"frag"), ("127.0.0.1", v_port)
        )
        tx.sendto(
            rp.build_rtp(8, False, 1, 160, 0xBBBB, b"\xd5" * 160), ("127.0.0.1", a_port)
        )
        tx.sendto(
            rp.build_rtp(0, False, 2, 320, 0xBBBB, b"\xff" * 160), ("127.0.0.1", a_port)
        )
        tx.sendto(b"\x80\xc8" + b"\0" * 26, ("127.0.0.1", v_port))  # RTCP SR
        assert _wait(lambda: len(go2rtc.frames) == 3)
        by_ch = {}
        for ch, pkt in go2rtc.frames:
            by_ch.setdefault(ch, []).append(rp.parse_rtp(pkt))
        v = by_ch[2]
        assert [p[4] for p in v] == [b"\x65key", b"frag"]
        assert v[0][3] == v[1][3]  # one frame, one timestamp
        assert v[1][1] is True and v[1][2] == (v[0][2] + 1) & 0xFFFF
        assert [p[0] for p in by_ch[0]] == [8]  # PCMU on a PCMA track dropped
        assert proc.publish_stats()["dropped_pt"] == 1
        ssrc = struct.unpack_from("!I", go2rtc.frames[0][1], 8)[0]
        assert ssrc != 0xAAAA  # our own SSRC, not the camera's
        tx.close()
    finally:
        proc.terminate()
        assert proc.wait(3) == rp.EXIT_TERMINATED
    assert proc.poll() == rp.EXIT_TERMINATED
    assert not _udp_port_bound(a_port) and not _udp_port_bound(v_port)
    assert _wait(lambda: "TEARDOWN" in go2rtc.requests)
    assert proc.stderr.read().decode().count("publish ended") == 1
    stats = proc.publish_stats()
    for key in ("aac_silence_samples", "aac_trimmed_samples", "aac_reanchors"):
        assert stats[key] == 0
    assert "AAC" not in proc.stderr.read().decode()


def test_loopback_publisher_applies_audio_gain(go2rtc, monkeypatch):
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "0")
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(
        _serve_sdp(a_port, v_port), go2rtc.url(), audio_gain_db=-8.0
    )
    try:
        assert _wait(lambda: "RECORD" in go2rtc.requests)
        loud = linear2alaw(8000)
        tx = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        tx.sendto(
            rp.build_rtp(8, False, 1, 160, 1, bytes([loud]) * 160),
            ("127.0.0.1", a_port),
        )
        tx.close()
        assert _wait(lambda: len(go2rtc.frames) == 1)
        out = rp.parse_rtp(go2rtc.frames[0][1])[4]
        assert abs(rp._alaw_to_linear(out[0])) < 8000 * 0.5
    finally:
        proc.kill()
    assert proc.poll() == rp.EXIT_KILLED


def test_loopback_publish_adds_aac_after_pcma(go2rtc, monkeypatch):
    monkeypatch.delenv("AIDOT_PUBLISH_AAC", raising=False)
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(
        _serve_sdp(a_port, v_port), go2rtc.url(), device_id="cam", audio_gain_db=0
    )
    try:
        tx = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        for i in range(50):
            tx.sendto(
                rp.build_rtp(8, False, 1 + i, 160 * (i + 1), 0xBBBB, b"\xd5" * 160),
                ("127.0.0.1", a_port),
            )
            if i < 10:  # video flows too, so tick() actually runs on this test
                tx.sendto(
                    rp.build_rtp(96, True, 1 + i, 1800 * i, 0xAAAA, b"\x41pic"),
                    ("127.0.0.1", v_port),
                )
            time.sleep(0.02)
        sdp_ok = _wait(
            lambda: go2rtc.announced and "MPEG4-GENERIC/48000" in go2rtc.announced[0]
        )
        assert sdp_ok
        sdp = go2rtc.announced[0]
        assert sdp.index("PCMA/8000") < sdp.index("MPEG4-GENERIC")
        assert _wait(lambda: sum(1 for ch, _ in go2rtc.frames if ch == 4) >= 20)
        # The camera's timestamps are contiguous (160 samples/packet, no gaps),
        # so a working feed() never asks the pacer to fill silence. Video also
        # flows here, so tick() runs on every pass (see `_video_forwarded`):
        # if the AAC packets above came from tick()'s idle-fill instead of
        # feed() actually consuming the A-law, this would be in the thousands
        # by now.
        assert proc._aac.pacer.silence_samples == 0
        tx.close()
    finally:
        proc.terminate()
        proc.wait(3)
    stats = proc.publish_stats()
    assert stats["aac_frames"] >= 20
    assert stats["aac_seconds"] > 0
    assert "audio:MPEG4-GENERIC/" in str(stats["tracks"])
    # Audio only, contiguous stamps: nothing filled, trimmed or re-anchored.
    assert stats["aac_silence_samples"] == 0
    assert stats["aac_trimmed_samples"] == 0
    assert stats["aac_reanchors"] == 0
    ended = [ln for ln in proc.stderr.tail() if "publish ended" in ln]
    assert len(ended) == 1
    assert f"AAC {stats['aac_frames']} frames" in ended[0]
    assert "0 silence-filled / 0 trimmed / 0 re-anchors" in ended[0]
    # No video was sent in this test, so the re-sent-frame filter never ran.
    assert "0 re-sent frames dropped, 0 filter resets" in ended[0]


def test_loopback_publish_builds_the_aac_track_off_the_constructing_thread(
    go2rtc, monkeypatch
):
    # The constructor runs on Home Assistant's event loop, and the first AAC
    # track imports numpy and av and opens a codec: that belongs on the worker.
    monkeypatch.delenv("AIDOT_PUBLISH_AAC", raising=False)
    calls = []
    real_make = rp.make_aac_track

    def _make(device_id="?"):
        calls.append(threading.current_thread())
        return real_make(device_id)

    monkeypatch.setattr(rp, "make_aac_track", _make)
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(
        _serve_sdp(a_port, v_port), go2rtc.url(), device_id="cam"
    )
    try:
        assert _wait(lambda: bool(go2rtc.announced))
        assert calls, "make_aac_track was never called"
        assert threading.current_thread() not in calls
        sdp = go2rtc.announced[0]
        assert sdp.index("PCMA/8000") < sdp.index("a=rtpmap:97 MPEG4-GENERIC/48000")
        assert "audio:MPEG4-GENERIC/97" in proc.publish_stats()["tracks"]
    finally:
        proc.terminate()
        proc.wait(3)


def test_loopback_publish_fills_aac_silence_only_while_video_flows(go2rtc, monkeypatch):
    monkeypatch.delenv("AIDOT_PUBLISH_AAC", raising=False)
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(
        _serve_sdp(a_port, v_port), go2rtc.url(), device_id="cam", audio_gain_db=0
    )
    try:
        assert _wait(lambda: "RECORD" in go2rtc.requests)
        tx = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        for i in range(10):
            tx.sendto(
                rp.build_rtp(8, False, 1 + i, 160 * (i + 1), 0xBBBB, b"\x01" * 160),
                ("127.0.0.1", a_port),
            )
            time.sleep(0.02)
        assert _wait(lambda: proc._aac.frames > 0)
        # The camera goes quiet - no audio, no video. Silence is generated to
        # keep the track alive BESIDE video, so none is generated now.
        time.sleep(1.2)
        assert proc._aac.pacer.silence_samples == 0
        for i in range(40):  # video resumes, audio does not: fill beside it
            tx.sendto(
                rp.build_rtp(96, True, 1 + i, 1800 * i, 0xAAAA, b"\x41pic"),
                ("127.0.0.1", v_port),
            )
            time.sleep(0.02)
        assert _wait(lambda: proc._aac.pacer.silence_samples > 0)
        tx.close()
    finally:
        proc.terminate()
        proc.wait(3)


def test_loopback_publish_without_audio_has_no_aac(go2rtc, monkeypatch):
    monkeypatch.delenv("AIDOT_PUBLISH_AAC", raising=False)
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(
        _serve_sdp(a_port, v_port), go2rtc.url(), device_id="cam", include_audio=False
    )
    try:
        assert _wait(lambda: bool(go2rtc.announced))
        assert "MPEG4-GENERIC" not in go2rtc.announced[0]
    finally:
        proc.terminate()
        proc.wait(3)


def test_loopback_publish_pcmu_camera_gets_no_aac(go2rtc, monkeypatch):
    monkeypatch.delenv("AIDOT_PUBLISH_AAC", raising=False)
    a_port, v_port = _free_udp_ports(2)
    pcmu_sdp = _serve_sdp(a_port, v_port).replace("RTP/AVP 8", "RTP/AVP 0")
    pcmu_sdp = pcmu_sdp.replace("a=rtpmap:8 PCMA/8000", "a=rtpmap:0 PCMU/8000")
    proc = rp.LoopbackRtpPublisher(pcmu_sdp, go2rtc.url(), device_id="cam")
    try:
        assert _wait(lambda: bool(go2rtc.announced))
        assert "MPEG4-GENERIC" not in go2rtc.announced[0]
    finally:
        proc.terminate()
        proc.wait(3)


def test_loopback_publisher_steers_video_and_not_audio(go2rtc, monkeypatch):
    """SDES video is stamped on a camera clock that runs fast, so it is
    steered to real time; the camera's audio clock is exact and stays hybrid.
    An explicit policy still applies to every track."""
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "0")
    monkeypatch.delenv(rp.ENV_PUBLISH_TIMESTAMPS, raising=False)
    for policy, want_video, want_audio in (
        (None, "steered", "hybrid"),
        ("arrival", "arrival", "arrival"),
    ):
        a_port, v_port = _free_udp_ports(2)
        proc = rp.LoopbackRtpPublisher(
            _serve_sdp(a_port, v_port), go2rtc.url(), device_id="cam", policy=policy
        )
        try:
            by_kind = {
                t.kind: tl.policy for t, tl in zip(proc._tracks, proc._timelines)
            }
            assert by_kind == {"video": want_video, "audio": want_audio}
        finally:
            proc.terminate()
            proc.wait(3)


def test_loopback_publisher_env_arrival_applies_to_both_tracks(go2rtc, monkeypatch):
    """AIDOT_PUBLISH_TIMESTAMPS=arrival with no explicit policy= applies to
    every track, video and audio alike, the same as an explicit policy=
    does."""
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "0")
    monkeypatch.setenv(rp.ENV_PUBLISH_TIMESTAMPS, "arrival")
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(
        _serve_sdp(a_port, v_port), go2rtc.url(), device_id="cam"
    )
    try:
        by_kind = {t.kind: tl.policy for t, tl in zip(proc._tracks, proc._timelines)}
        assert by_kind == {"video": "arrival", "audio": "arrival"}
    finally:
        proc.terminate()
        proc.wait(3)


def test_loopback_publisher_exits_1_when_the_stream_is_missing():
    srv = FakeGo2rtc(streams=())
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(_serve_sdp(a_port, v_port), srv.url())
    try:
        assert proc.wait(5) == rp.EXIT_FAILED
        assert any("does the stream exist" in ln for ln in proc._aidot_stderr_notable)
    finally:
        srv.stop()


def test_loopback_publisher_exits_1_when_go2rtc_is_down():
    a_port, v_port, dead = _free_udp_ports(3)
    proc = rp.LoopbackRtpPublisher(
        _serve_sdp(a_port, v_port), f"rtsp://127.0.0.1:{dead}/x"
    )
    assert proc.wait(7) == rp.EXIT_FAILED
    assert any("failed" in ln for ln in proc._aidot_stderr_notable)


def test_loopback_publisher_exits_1_when_go2rtc_drops_it(go2rtc):
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(_serve_sdp(a_port, v_port), go2rtc.url())
    assert _wait(lambda: "RECORD" in go2rtc.requests)
    time.sleep(0.1)
    go2rtc.drop_publisher()
    assert proc.wait(5) == rp.EXIT_FAILED


def test_loopback_publisher_input_timeout(go2rtc):
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(
        _serve_sdp(a_port, v_port), go2rtc.url(), input_timeout_s=0.6
    )
    assert proc.wait(5) == rp.EXIT_FAILED
    assert any("no media" in ln for ln in proc._aidot_stderr_notable)


def test_loopback_publisher_popen_surface(go2rtc):
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(_serve_sdp(a_port, v_port), go2rtc.url())
    try:
        with pytest.raises(subprocess.TimeoutExpired):
            proc.wait(0.05)
        assert isinstance(proc.pid, int) and proc.stdout is None
        # The serve's stderr drain must see EOF at once and not block.
        assert proc.stderr.readline() == b""
    finally:
        proc.terminate()
        proc.wait(3)
    proc.stderr.close()
    assert proc.stderr.read() == b""


def test_loopback_publisher_port_in_use_raises_and_leaks_nothing():
    a_port, v_port = _free_udp_ports(2)
    hog = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    hog.bind(("127.0.0.1", v_port))
    try:
        with pytest.raises(OSError):
            rp.LoopbackRtpPublisher(
                _serve_sdp(a_port, v_port), "rtsp://127.0.0.1:1/x", device_id="hog"
            )
        assert not any(
            t.name == "aidot-direct-publish-hog" for t in threading.enumerate()
        )
        assert not _udp_port_bound(a_port)
    finally:
        hog.close()


def test_sdes_bridge_classifies_publisher_teardown_as_expected():
    """The bridge's exit logger must treat our stop like ffmpeg's."""
    import logging

    from aidot_cameras.camera.sdes_open import _classify_ffmpeg_exit

    assert _classify_ffmpeg_exit(rp.EXIT_TERMINATED, True) == logging.DEBUG
    assert _classify_ffmpeg_exit(rp.EXIT_KILLED, True) == logging.DEBUG
    assert _classify_ffmpeg_exit(rp.EXIT_FAILED, False) >= logging.WARNING


# --------------------------------------------------------------------------- #
# DTLS runner                                                                  #
# --------------------------------------------------------------------------- #


def test_dtls_runner_starts_on_a_keyframe_and_publishes_both_tracks(
    go2rtc, monkeypatch
):
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "0")
    import queue

    vq, aq = queue.Queue(), queue.Queue()
    sps_pps = b"\0\0\0\1\x67" + b"s" * 8 + b"\0\0\0\1\x68" + b"p" * 3
    vq.put((b"\0\0\0\1\x41" + b"x" * 50, 0, False))  # before a keyframe: skipped
    aq.put((b"\xd5" * 160, 0))  # before a keyframe: skipped
    vq.put((sps_pps + b"\0\0\0\1\x65" + b"k" * 3000, 3000, True))
    vq.put((b"\0\0\0\1\x41" + b"d" * 40, 6000, False))
    vq.put((b"\0\0\0\1\x41" + b"r" * 40, 6000, False))  # re-sent: dropped
    progress, stop, res = [0.0], threading.Event(), {}
    t = threading.Thread(
        target=rp.dtls_rtp_publish_run,
        args=(vq, aq, go2rtc.url(), progress, stop),
        kwargs={"result": res},
        daemon=True,
    )
    t.start()
    assert _wait(lambda: progress[0] > 0)
    aq.put((b"\xd5" * 160, 160))
    assert _wait(lambda: any(ch == 2 for ch, _ in go2rtc.frames))
    stop.set()
    t.join(3)
    assert not t.is_alive() and "error" not in res
    assert "m=video 0 RTP/AVP 96" in go2rtc.announced[0]
    assert "m=audio 0 RTP/AVP 8" in go2rtc.announced[0]
    video = [rp.parse_rtp(p) for ch, p in go2rtc.frames if ch == 0]
    nals = _depacketize([v[4] for v in video])
    assert [n[0] & 0x1F for n in nals] == [7, 8, 5, 1]
    assert nals[-1] == b"\x41" + b"d" * 40
    assert sum(1 for v in video if v[1]) == 2  # one marker per access unit
    assert _wait(lambda: "TEARDOWN" in go2rtc.requests)


def _run_dtls(go2rtc, feed, *, secs=1.2):
    import queue

    vq, aq = queue.Queue(), queue.Queue()
    sps_pps = b"\0\0\0\1\x67" + b"s" * 8 + b"\0\0\0\1\x68" + b"p" * 3
    vq.put((sps_pps + b"\0\0\0\1\x65" + b"k" * 3000, 3000, True))
    progress, stop, res = [0.0], threading.Event(), {}
    t = threading.Thread(
        target=rp.dtls_rtp_publish_run,
        args=(vq, aq, go2rtc.url(), progress, stop),
        kwargs={"result": res},
        daemon=True,
    )
    t.start()
    assert _wait(lambda: progress[0] > 0)
    feed(vq, aq)
    time.sleep(secs)
    stop.set()
    t.join(3)
    return res


def test_dtls_publish_announces_and_sends_an_aac_track(go2rtc, monkeypatch, caplog):
    monkeypatch.delenv("AIDOT_PUBLISH_AAC", raising=False)
    caplog.set_level(logging.INFO, logger="aidot_cameras.camera.rtsp_publish")

    def feed(vq, aq):
        for i in range(50):  # 1 s of A-law
            aq.put((b"\xd5" * 160, 160 + 160 * i))
            vq.put((b"\0\0\0\1\x41" + b"d" * 40, 6000 + 1800 * i, False))

    res = _run_dtls(go2rtc, feed)
    sdp = go2rtc.announced[0]
    assert "m=audio 0 RTP/AVP 8" in sdp
    assert sdp.index("RTP/AVP 8") < sdp.index("RTP/AVP 97")  # PCMA stays first
    assert "a=rtpmap:97 MPEG4-GENERIC/48000" in sdp
    assert "config=1188" in sdp
    aac = [rp.parse_rtp(p) for ch, p in go2rtc.frames if ch == 4]
    assert len(aac) >= 20
    ts = [a[3] for a in aac]
    assert all(((b - a) & 0xFFFFFFFF) == 1024 for a, b in zip(ts, ts[1:]))
    assert res["aac_frames"] == len(aac)
    assert res["aac_seconds"] > 0
    # The audio is contiguous: nothing is filled, trimmed, or re-anchored.
    assert res["aac_silence_samples"] == 0
    assert res["aac_trimmed_samples"] == 0
    assert res["aac_reanchors"] == 0
    ended = [
        r.getMessage()
        for r in caplog.records
        if "DTLS direct publish: AAC" in r.getMessage()
    ]
    assert len(ended) == 1
    assert f"AAC {res['aac_frames']} frames" in ended[0]
    assert f"{res['aac_silence_samples']} samples silence-filled" in ended[0]


def test_dtls_publish_feeds_queued_audio_before_the_idle_fill(go2rtc, monkeypatch):
    # After a loop stall, audio and video are queued together. The audio is
    # real and continuous; the idle fill must not run ahead of it and have it
    # trimmed away as already covered.
    monkeypatch.delenv("AIDOT_PUBLISH_AAC", raising=False)
    made = []

    def _make(device_id="?"):
        made.append(real_make(device_id))
        return made[-1]

    real_make = rp.make_aac_track
    monkeypatch.setattr(rp, "make_aac_track", _make)

    def feed(vq, aq):
        for i in range(10):
            aq.put((b"\x01" * 160, 160 + 160 * i))
            vq.put((b"\0\0\0\1\x41" + b"d" * 40, 6000 + 1800 * i, False))
        time.sleep(0.8)  # the stall: nothing is drained
        for i in range(10, 20):  # audio queued first, as it arrived first
            aq.put((b"\x01" * 160, 160 + 160 * i))
        for i in range(10, 20):
            vq.put((b"\0\0\0\1\x41" + b"d" * 40, 6000 + 1800 * i, False))

    _run_dtls(go2rtc, feed, secs=0.5)
    assert made and made[0] is not None
    assert made[0].pacer.trimmed_samples == 0


def test_dtls_publish_kill_switch_keeps_todays_sdp(go2rtc, monkeypatch, caplog):
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "0")
    caplog.set_level(logging.INFO, logger="aidot_cameras.camera.rtsp_publish")
    res = _run_dtls(go2rtc, lambda vq, aq: None, secs=0.2)
    assert "RTP/AVP 97" not in go2rtc.announced[0]
    assert not any(ch == 4 for ch, _ in go2rtc.frames)
    assert res["aac_frames"] == 0
    assert res["aac_seconds"] == 0.0
    for key in ("aac_silence_samples", "aac_trimmed_samples", "aac_reanchors"):
        assert res[key] == 0
    assert not any("AAC" in r.getMessage() for r in caplog.records)


def test_dtls_runner_reports_a_failed_publish():
    import queue

    s = socket.socket()
    s.bind(("127.0.0.1", 0))
    port = s.getsockname()[1]
    s.close()
    res = {}
    rp.dtls_rtp_publish_run(
        queue.Queue(),
        queue.Queue(),
        f"rtsp://127.0.0.1:{port}/x",
        [0.0],
        threading.Event(),
        result=res,
    )
    assert "failed" in res["error"]


# --------------------------------------------------------------------------- #
# SDES open wiring                                                             #
# --------------------------------------------------------------------------- #


def test_sdes_direct_publish_decision(monkeypatch):
    from aidot_cameras.camera.sdes_open import _should_direct_publish

    url = "rtsp://127.0.0.1:8554/aidot_x"
    monkeypatch.delenv(rp.ENV_DIRECT_PUBLISH, raising=False)
    assert not _should_direct_publish(url, None, None, True)  # default off
    monkeypatch.setenv(rp.ENV_DIRECT_PUBLISH, "1")
    assert _should_direct_publish(url, None, None, True)
    assert not _should_direct_publish("http://127.0.0.1:18600/x.ts", None, None, True)
    assert not _should_direct_publish(None, None, None, True)  # decode drain
    assert not _should_direct_publish(url, "/tmp/clip.ts", None, True)  # recording
    assert not _should_direct_publish(url, None, 10, True)  # snapshot
    # SRTP reaches the serve still encrypted (ffmpeg decrypts it from the
    # SDP's a=crypto) for any model the bridge does not decrypt itself.
    assert not _should_direct_publish(url, None, None, False)


def test_the_open_passes_the_plain_rtp_decision_through():
    import inspect

    from aidot_cameras.camera import sdes_open

    src = inspect.getsource(sdes_open)
    assert (
        "_direct_publish = _should_direct_publish(\n"
        "            rtsp_push_url, output_path, max_seconds, _use_plain_rtp, _keep_v\n"
    ) in src


def test_both_sdes_serve_launches_go_through_the_spawn_helper():
    """Source guard: the first launch AND the SRTP key-change relaunch must
    both use ``_spawn_serve`` - a raw ``subprocess.Popen`` left at either site
    would silently bring ffmpeg back with direct publish on."""
    import inspect

    from aidot_cameras.camera import sdes_open

    src = inspect.getsource(sdes_open)
    assert src.count("proc = await _spawn_serve()") == 2
    # The SDP read goes through the executor: Home Assistant flags a blocking
    # open() in the event loop, and this one runs on the open's hot path.
    assert 'with open(sdp_path, encoding="utf-8") as _f_dp' not in src
    assert "_dp_sdp = await asyncio.get_running_loop().run_in_executor(" in src
    body = src[src.index("def _spawn_serve():") :]
    body = body[: body.index("# Do not launch the publisher")]
    assert "LoopbackRtpPublisher(" in body and "subprocess.Popen(" in body
    # The serve Popen exists only inside the helper now.
    assert src.count("subprocess.Popen(") == 1


def test_dtls_serve_loop_checks_publish_before_the_direct_ts_serve():
    """Source guard: with an rtsp:// destination the direct TS serve would
    try to bind go2rtc's own RTSP port, so the publish branch must win."""
    import inspect

    from aidot_cameras.camera import client

    src = inspect.getsource(client)
    i_pub = src.index("_publishing = direct_publish_enabled() and is_publishable_url(")
    i_ts = src.index("elif _direct_serve_enabled()")
    assert i_pub < i_ts
    assert "target=dtls_rtp_publish_run" in src


# --------------------------------------------------------------------------- #
# review fixes: reorder, unannounced ports, close during connect               #
# --------------------------------------------------------------------------- #


def test_reorder_passes_in_order_packets_straight_through():
    b = rp.RtpReorderBuffer()
    assert b.push(10, "a", 0.0) == ["a"]
    assert b.push(11, "b", 0.0) == ["b"]
    assert b.push(0xFFFF, "late", 0.0) == [] and b.late == 1


def test_reorder_fills_a_gap_in_sequence_order():
    b = rp.RtpReorderBuffer()
    assert b.push(1, "1", 0.0) == ["1"]
    assert b.push(3, "3", 0.01) == []
    assert b.push(4, "4", 0.02) == []
    assert b.push(2, "2", 0.05) == ["2", "3", "4"]  # e.g. a NACK retransmit
    assert b.push(2, "dup", 0.06) == [] and b.late == 1


def test_reorder_skips_a_gap_that_never_fills():
    b = rp.RtpReorderBuffer(max_delay_s=0.5)
    b.push(1, "1", 0.0)
    b.push(3, "3", 1.0)
    assert b.expire(1.4) == []
    assert b.expire(1.5) == ["3"] and b.skipped == 1
    assert b.push(4, "4", 1.6) == ["4"]


def test_reorder_wraps_and_resyncs_on_a_new_sender():
    b = rp.RtpReorderBuffer()
    b.push(0xFFFE, "a", 0.0)
    assert b.push(0x0000, "c", 0.0) == []
    assert b.push(0xFFFF, "b", 0.0) == ["b", "c"]
    assert b.push(30000, "new", 0.1) == ["new"]  # far jump: resync, not a gap
    assert b.push(30001, "next", 0.1) == ["next"]


def test_reorder_bounds_what_it_holds():
    b = rp.RtpReorderBuffer(max_packets=3)
    b.push(1, 1, 0.0)
    for sq in (3, 4, 5):
        assert b.push(sq, sq, 0.0) == []
    assert b.push(6, 6, 0.0) == [3, 4, 5, 6]


def test_loopback_publisher_reorders_before_publishing(go2rtc, monkeypatch):
    # Video-only assertions on go2rtc.frames, not filtered by channel: an AAC
    # idle-fill packet landing on channel 4 before the check would break the
    # exact count, same reason as the two tests above.
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "0")
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(_serve_sdp(a_port, v_port), go2rtc.url())
    try:
        assert _wait(lambda: "RECORD" in go2rtc.requests)
        time.sleep(0.1)
        tx = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        for sq, body, mk in ((100, b"A", False), (102, b"C", True), (101, b"B", False)):
            tx.sendto(rp.build_rtp(96, mk, sq, 9000, 1, body), ("127.0.0.1", v_port))
            time.sleep(0.02)
        tx.close()
        assert _wait(lambda: len(go2rtc.frames) == 3)
        got = [rp.parse_rtp(p) for _, p in go2rtc.frames]
        assert [g[4] for g in got] == [b"A", b"B", b"C"]
        assert len({g[3] for g in got}) == 1  # one frame, one timestamp
    finally:
        proc.terminate()
        proc.wait(3)


def test_loopback_publish_drops_resent_video_frames(go2rtc, monkeypatch):
    """The A001064 family re-sends runs of video frames it has already sent,
    after a periodic backward jump of its own timestamps. Forwarding a resend
    duplicates picture content and, worse, RtpTimeline gives the repeat a
    fresh forward output timestamp - so its time is counted twice."""
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "0")
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(_serve_sdp(a_port, v_port), go2rtc.url())
    try:
        assert _wait(lambda: "RECORD" in go2rtc.requests)
        time.sleep(0.1)
        tx = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        seq = 100
        for ts in (0, 6000, 12000, 18000, 6000, 12000, 24000):
            tx.sendto(
                rp.build_rtp(96, True, seq, ts, 0xAAAA, b"pic"), ("127.0.0.1", v_port)
            )
            seq += 1
            time.sleep(0.02)
        tx.close()
        assert _wait(lambda: len(go2rtc.frames) == 5)
        time.sleep(0.1)  # nothing more should arrive
        assert len(go2rtc.frames) == 5
        video = [rp.parse_rtp(p) for ch, p in go2rtc.frames if ch == 2]
        assert len(video) == 5
        ts_out = [v[3] for v in video]
        assert ts_out == sorted(set(ts_out)) and len(set(ts_out)) == 5
        assert proc.publish_stats()["dropped_resent"] == 2
    finally:
        proc.terminate()
        proc.wait(3)


def test_loopback_publish_never_splits_an_accepted_frame(go2rtc, monkeypatch):
    """A frame that is accepted must be forwarded whole, even when the resend
    filter is judging it: the decision is made once, on the frame's first
    packet, and every later packet of that same frame follows it."""
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "0")
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(_serve_sdp(a_port, v_port), go2rtc.url())
    try:
        assert _wait(lambda: "RECORD" in go2rtc.requests)
        time.sleep(0.1)
        tx = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        packets = [
            (100, 0, True, b"a"),
            (101, 6000, True, b"b"),
            (102, 12000, False, b"c1"),
            (103, 12000, False, b"c2"),
            (104, 12000, True, b"c3"),
        ]
        for seq, ts, mk, body in packets:
            tx.sendto(
                rp.build_rtp(96, mk, seq, ts, 0xAAAA, body), ("127.0.0.1", v_port)
            )
            time.sleep(0.02)
        tx.close()
        assert _wait(lambda: len(go2rtc.frames) == 5)
        video = [rp.parse_rtp(p) for ch, p in go2rtc.frames if ch == 2]
        assert [v[4] for v in video] == [b"a", b"b", b"c1", b"c2", b"c3"]
        assert len({v[3] for v in video[2:]}) == 1  # the 3 pkts share one ts
        assert proc.publish_stats()["dropped_resent"] == 0
    finally:
        proc.terminate()
        proc.wait(3)


def test_loopback_publish_does_not_filter_audio(go2rtc, monkeypatch):
    """The resend filter is video-only: an audio track whose timestamp goes
    backward is still forwarded (the DTLS path never applies it to audio
    either)."""
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "0")
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(_serve_sdp(a_port, v_port), go2rtc.url())
    try:
        assert _wait(lambda: "RECORD" in go2rtc.requests)
        time.sleep(0.1)
        tx = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        tx.sendto(
            rp.build_rtp(8, False, 1, 1000, 0xBBBB, b"\xd5" * 160),
            ("127.0.0.1", a_port),
        )
        tx.sendto(
            rp.build_rtp(8, False, 2, 500, 0xBBBB, b"\xd5" * 160),  # backward
            ("127.0.0.1", a_port),
        )
        tx.close()
        assert _wait(lambda: len(go2rtc.frames) == 2)
        time.sleep(0.1)
        assert len(go2rtc.frames) == 2
        audio = [rp.parse_rtp(p) for ch, p in go2rtc.frames if ch == 0]
        assert len(audio) == 2
        assert proc.publish_stats()["dropped_resent"] == 0
    finally:
        proc.terminate()
        proc.wait(3)


def test_loopback_publish_resets_the_resent_filter_on_a_new_timestamp_base(
    go2rtc, monkeypatch
):
    """A backward step bigger than any genuine re-send (the camera's own
    re-sends go back about 1.7 s) is a new timestamp base, not a re-send -
    dropping video until the old high-water mark is caught back up to would
    black it out for hours instead of the fraction of a second this camera
    family's real re-sends cost."""
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "0")
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(_serve_sdp(a_port, v_port), go2rtc.url())
    try:
        assert _wait(lambda: "RECORD" in go2rtc.requests)
        time.sleep(0.1)
        tx = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        seq = 100
        # ~10 s jump back (912000 -> 1000 at 90 kHz), far past RESENT_MAX_BACK_S.
        for ts in (900000, 906000, 912000, 1000, 7000, 13000):
            tx.sendto(
                rp.build_rtp(96, True, seq, ts, 0xAAAA, b"pic"), ("127.0.0.1", v_port)
            )
            seq += 1
            time.sleep(0.02)
        tx.close()
        assert _wait(lambda: len(go2rtc.frames) == 6)
        time.sleep(0.1)
        assert len(go2rtc.frames) == 6
        video = [rp.parse_rtp(p) for ch, p in go2rtc.frames if ch == 2]
        assert len(video) == 6
        ts_out = [v[3] for v in video]
        assert ts_out == sorted(ts_out)
        assert len(set(ts_out)) == 6  # strictly increasing
        stats = proc.publish_stats()
        assert stats["dropped_resent"] == 0
        assert stats["resent_filter_resets"] == 1
    finally:
        proc.terminate()
        proc.wait(3)


def test_loopback_publish_resets_the_resent_filter_on_a_new_ssrc(go2rtc, monkeypatch):
    """The bridge re-syncs its reorder buffer's own sequence numbering when a
    TUTK-framed camera switches from TUTK SFrames to real SRTP mid-session -
    on the SAME loopback port, with a new SSRC (RtpReorderBuffer.push already
    takes an ssrc and resyncs on it; this is the same signal). The old
    unwrapped position and high-water mark do not apply to the new sender, so
    its frames must not be judged against them."""
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "0")
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(_serve_sdp(a_port, v_port), go2rtc.url())
    try:
        assert _wait(lambda: "RECORD" in go2rtc.requests)
        time.sleep(0.1)
        tx = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        for seq, ts in ((100, 0), (101, 6000), (102, 12000)):
            tx.sendto(
                rp.build_rtp(96, True, seq, ts, 0xAAAA, b"pic"), ("127.0.0.1", v_port)
            )
            time.sleep(0.02)
        # A new sender on the same port: different SSRC, its own timestamps.
        for seq, ts in ((10, 3000), (11, 9000)):
            tx.sendto(
                rp.build_rtp(96, True, seq, ts, 0xCCCC, b"pic"), ("127.0.0.1", v_port)
            )
            time.sleep(0.02)
        tx.close()
        assert _wait(lambda: len(go2rtc.frames) == 5)
        time.sleep(0.1)
        assert len(go2rtc.frames) == 5
        video = [rp.parse_rtp(p) for ch, p in go2rtc.frames if ch == 2]
        assert len(video) == 5
        stats = proc.publish_stats()
        assert stats["dropped_resent"] == 0
        assert stats["resent_filter_resets"] == 1
    finally:
        proc.terminate()
        proc.wait(3)


def test_loopback_publish_drops_a_real_resend_within_the_boundary(go2rtc, monkeypatch):
    """A re-send run landing within RESENT_MAX_BACK_S of the high-water mark
    (the camera's genuine re-sends go back about 1.7 s = 153000 ticks at
    90 kHz) must still be DROPPED, not treated as a new timestamp base. This
    pins the boundary from the drop side: a too-small limit (e.g. 0.5 s, or
    computing it with 8000 instead of the track's clock rate) would instead
    reset the filter here."""
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "0")
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(_serve_sdp(a_port, v_port), go2rtc.url())
    try:
        assert _wait(lambda: "RECORD" in go2rtc.requests)
        time.sleep(0.1)
        tx = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        seq = 100
        # 200000 -> 353000 (+153000, hw climbs to 153000) -> 200000 again (a
        # real re-send run landing exactly 153000 below the high-water mark).
        for ts in (200000, 353000, 200000, 359000):
            tx.sendto(
                rp.build_rtp(96, True, seq, ts, 0xAAAA, b"pic"), ("127.0.0.1", v_port)
            )
            seq += 1
            time.sleep(0.02)
        tx.close()
        assert _wait(lambda: len(go2rtc.frames) == 3)
        time.sleep(0.1)
        assert len(go2rtc.frames) == 3  # the re-sent 200000 frame is dropped
        stats = proc.publish_stats()
        assert stats["dropped_resent"] == 1
        assert stats["resent_filter_resets"] == 0
    finally:
        proc.terminate()
        proc.wait(3)


def test_loopback_publish_resets_on_a_backward_step_past_the_boundary(
    go2rtc, monkeypatch
):
    """A backward step bigger than RESENT_MAX_BACK_S (~6 s = 540000 ticks at
    90 kHz, comfortably past the boundary) must RESET the filter and be
    forwarded - the companion of the drop-side test above, pinning the
    boundary from the other side."""
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "0")
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(_serve_sdp(a_port, v_port), go2rtc.url())
    try:
        assert _wait(lambda: "RECORD" in go2rtc.requests)
        time.sleep(0.1)
        tx = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        seq = 100
        # 600000 -> 606000 -> 612000 (hw climbs to 12000 unwrapped), then
        # 72000 (612000 - 540000: a ~6 s step back, past the boundary).
        for ts in (600000, 606000, 612000, 72000):
            tx.sendto(
                rp.build_rtp(96, True, seq, ts, 0xAAAA, b"pic"), ("127.0.0.1", v_port)
            )
            seq += 1
            time.sleep(0.02)
        tx.close()
        assert _wait(lambda: len(go2rtc.frames) == 4)
        time.sleep(0.1)
        assert len(go2rtc.frames) == 4  # all forwarded, including the reset frame
        stats = proc.publish_stats()
        assert stats["dropped_resent"] == 0
        assert stats["resent_filter_resets"] == 1
    finally:
        proc.terminate()
        proc.wait(3)


def test_loopback_publish_never_splits_a_frame_a_stray_packet_interrupts(
    go2rtc, monkeypatch
):
    """Reproduces the reviewer's finding: the reorder buffer's resync (a run
    of late packets) can deliver A1, B1, A_late, B2 in that order - a stray
    packet carrying A's already-served timestamp landing BETWEEN B's two
    packets. The filter must judge a new frame only against the last
    ACCEPTED timestamp, not the last packet seen, or the stray flips the
    verdict and splits B."""
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "0")
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(_serve_sdp(a_port, v_port), go2rtc.url())
    try:
        assert _wait(lambda: "RECORD" in go2rtc.requests)
        time.sleep(0.1)
        video_idx = 1  # SERVE_SDP: audio then video
        pub = proc._publisher
        ssrc = 0xAAAA
        now = time.monotonic()
        proc._send(pub, video_idx, (True, 1000, b"A1", now, ssrc))
        proc._send(pub, video_idx, (False, 7000, b"B1", now, ssrc))
        proc._video_forwarded = False
        proc._send(pub, video_idx, (False, 1000, b"Alate", now, ssrc))
        assert proc._video_forwarded is False
        proc._send(pub, video_idx, (True, 7000, b"B2", now, ssrc))
        assert _wait(lambda: len(go2rtc.frames) == 3)
        time.sleep(0.1)
        assert len(go2rtc.frames) == 3
        video = [rp.parse_rtp(p) for ch, p in go2rtc.frames if ch == 2]
        assert [v[4] for v in video] == [b"A1", b"B1", b"B2"]
        assert video[1][3] == video[2][3]  # B1 and B2 share one output ts
        stats = proc.publish_stats()
        assert stats["dropped_resent"] == 1  # A_late, once - not per packet
        assert stats["resent_filter_resets"] == 0
    finally:
        proc.terminate()
        proc.wait(3)


def test_loopback_publish_does_not_leak_a_resend_run_that_ends_on_the_last_frame(
    go2rtc, monkeypatch
):
    """Regression: a re-send run that replays the last N accepted frames ends
    ON the last accepted frame's timestamp by construction (it is replaying
    everything since the high-water mark, and the mark IS that timestamp).
    The `ts == last_accepted_ts` continuation shortcut used to forward that
    final replayed frame unconditionally, because nothing had ever closed the
    window after the frame's own marker packet: frames 0, 6000, 12000, 18000,
    then a re-send run 6000, 12000, 18000 (new sequence numbers), then 24000
    must forward only F0..F4 and drop all three re-sent frames."""
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "0")
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(_serve_sdp(a_port, v_port), go2rtc.url())
    try:
        assert _wait(lambda: "RECORD" in go2rtc.requests)
        time.sleep(0.1)
        tx = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        seq = 100
        payloads = [b"F0", b"F1", b"F2", b"F3", b"R1", b"R2", b"R3", b"F4"]
        for ts, body in zip(
            (0, 6000, 12000, 18000, 6000, 12000, 18000, 24000), payloads
        ):
            tx.sendto(
                rp.build_rtp(96, True, seq, ts, 0xAAAA, body), ("127.0.0.1", v_port)
            )
            seq += 1
            time.sleep(0.02)
        tx.close()
        assert _wait(lambda: len(go2rtc.frames) == 5)
        time.sleep(0.1)
        assert len(go2rtc.frames) == 5  # the three re-sent frames are dropped
        video = [rp.parse_rtp(p) for ch, p in go2rtc.frames if ch == 2]
        assert [v[4] for v in video] == [b"F0", b"F1", b"F2", b"F3", b"F4"]
        stats = proc.publish_stats()
        assert stats["dropped_resent"] == 3
        assert stats["resent_filter_resets"] == 0
    finally:
        proc.terminate()
        proc.wait(3)


def test_loopback_publish_drops_a_single_frame_resend_run_immediately_after(
    go2rtc, monkeypatch
):
    """The degenerate N=1 case of the regression above: the re-send run is a
    single frame, and it arrives with NO other distinct timestamp judged in
    between - so `ts == last_raw` exactly, right after the frame's own
    acceptance closed its window. This must still be re-judged and dropped,
    not forwarded as a cached-verdict continuation."""
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "0")
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(_serve_sdp(a_port, v_port), go2rtc.url())
    try:
        assert _wait(lambda: "RECORD" in go2rtc.requests)
        time.sleep(0.1)
        tx = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        seq = 100
        payloads = [b"F0", b"F1", b"F2", b"F3", b"R3", b"F4"]
        for ts, body in zip((0, 6000, 12000, 18000, 18000, 24000), payloads):
            tx.sendto(
                rp.build_rtp(96, True, seq, ts, 0xAAAA, body), ("127.0.0.1", v_port)
            )
            seq += 1
            time.sleep(0.02)
        tx.close()
        assert _wait(lambda: len(go2rtc.frames) == 5)
        time.sleep(0.1)
        assert len(go2rtc.frames) == 5
        video = [rp.parse_rtp(p) for ch, p in go2rtc.frames if ch == 2]
        assert [v[4] for v in video] == [b"F0", b"F1", b"F2", b"F3", b"F4"]
        stats = proc.publish_stats()
        assert stats["dropped_resent"] == 1
        assert stats["resent_filter_resets"] == 0
    finally:
        proc.terminate()
        proc.wait(3)


def test_loopback_publish_does_not_leak_a_resend_run_that_ends_on_a_multi_packet_frame(
    go2rtc, monkeypatch
):
    """The companion of the two regressions above, for a MULTI-packet
    accepted frame: F2 (F2a/F2b) closes on its own marker packet, and a
    re-send run that later replays it (R1/R2a/R2b) must not leak R2a/R2b a
    second time."""
    monkeypatch.setenv("AIDOT_PUBLISH_AAC", "0")
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(_serve_sdp(a_port, v_port), go2rtc.url())
    try:
        assert _wait(lambda: "RECORD" in go2rtc.requests)
        time.sleep(0.1)
        tx = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        packets = [
            (100, 0, True, b"F0"),
            (101, 6000, True, b"F1"),
            (102, 12000, False, b"F2a"),
            (103, 12000, True, b"F2b"),
            (104, 6000, True, b"R1"),
            (105, 12000, False, b"R2a"),
            (106, 12000, True, b"R2b"),
            (107, 18000, True, b"F3"),
        ]
        for seq, ts, mk, body in packets:
            tx.sendto(
                rp.build_rtp(96, mk, seq, ts, 0xAAAA, body), ("127.0.0.1", v_port)
            )
            time.sleep(0.02)
        tx.close()

        def _f3_arrived():
            video = [rp.parse_rtp(p) for ch, p in go2rtc.frames if ch == 2]
            return any(v[4] == b"F3" for v in video)

        assert _wait(_f3_arrived)
        time.sleep(0.1)
        video = [rp.parse_rtp(p) for ch, p in go2rtc.frames if ch == 2]
        assert [v[4] for v in video] == [b"F0", b"F1", b"F2a", b"F2b", b"F3"]
        assert video[2][3] == video[3][3]  # F2a and F2b share one output ts
        stats = proc.publish_stats()
        assert stats["dropped_resent"] == 2
        assert stats["resent_filter_resets"] == 0
    finally:
        proc.terminate()
        proc.wait(3)


def test_video_only_publish_still_binds_the_audio_port(go2rtc):
    """The SDES open waits for BOTH loopback ports before signalling; binding
    only the announced one cost every video-only open the 3 s wait plus 1.5 s."""
    a_port, v_port = _free_udp_ports(2)
    proc = rp.LoopbackRtpPublisher(
        _serve_sdp(a_port, v_port), go2rtc.url(), include_audio=False
    )
    try:
        assert _udp_port_bound(a_port) and _udp_port_bound(v_port)
        assert _wait(lambda: "RECORD" in go2rtc.requests)
        assert "m=audio" not in go2rtc.announced[0]
        tx = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        tx.sendto(
            rp.build_rtp(8, False, 1, 160, 1, b"\xd5" * 160), ("127.0.0.1", a_port)
        )
        tx.sendto(rp.build_rtp(96, True, 1, 90, 1, b"\x65v"), ("127.0.0.1", v_port))
        tx.close()
        assert _wait(lambda: len(go2rtc.frames) == 1)
        time.sleep(0.2)
        assert [ch for ch, _ in go2rtc.frames] == [0]  # audio read and discarded
    finally:
        proc.terminate()
        proc.wait(3)
    assert not _udp_port_bound(a_port)


def test_a_close_during_connect_leaves_no_publish_behind():
    """close() before the handshake finishes must win: the handshake tears
    itself down rather than leaving an orphaned producer in go2rtc."""
    srv = FakeGo2rtc()
    gate = threading.Event()
    orig = srv._serve

    def slow(c):
        gate.wait(5)  # hold the handshake open
        orig(c)

    srv._serve = slow
    try:
        sdp, tracks, _ = rp.publish_sdp_from_serve_sdp(SERVE_SDP, ("video",))
        pub = rp.RtspPublisher(srv.url(), sdp, tracks)
        err = []

        def run():
            try:
                pub.connect()
            except Exception as exc:
                err.append(exc)

        t = threading.Thread(target=run)
        t.start()
        time.sleep(0.2)
        pub.close()
        gate.set()
        t.join(6)
        assert err and not pub.alive
        assert "RECORD" not in srv.requests or _wait(srv.closed.is_set)
    finally:
        srv.stop()


def test_close_before_connect_is_sticky(go2rtc):
    sdp, tracks, _ = rp.publish_sdp_from_serve_sdp(SERVE_SDP)
    pub = rp.RtspPublisher(go2rtc.url(), sdp, tracks)
    pub.close()
    with pytest.raises(rp.RtspPublishError):
        pub.connect()
    assert "ANNOUNCE" not in go2rtc.requests


def test_reorder_resyncs_on_a_new_ssrc_just_behind_the_old_numbering():
    """TUTK SFrames (bridge counter 1..N) then the camera's SRTP (random
    sequence numbers) on one port: a new sender landing just behind N must not
    be dropped as late until it wraps past N."""
    b = rp.RtpReorderBuffer()
    for sq in range(1, 1001):
        b.push(sq, sq, 0.0, ssrc=0xAAAA)
    assert b.push(400, "srtp", 0.1, ssrc=0xBBBB) == ["srtp"]
    assert b.push(401, "srtp2", 0.1, ssrc=0xBBBB) == ["srtp2"]
    assert b.resyncs == 1 and b.late == 0


def test_reorder_resyncs_after_a_run_of_late_packets():
    b = rp.RtpReorderBuffer(late_run_resync=5)
    for sq in range(1000, 1010):
        b.push(sq, sq, 0.0)
    out = []
    for sq in range(900, 905):
        out += b.push(sq, sq, 0.0)
    assert out == [904] and b.resyncs == 1
    assert b.push(905, 905, 0.0) == [905]


def test_close_never_raises_and_is_idempotent():
    sdp, tracks, _ = rp.publish_sdp_from_serve_sdp(SERVE_SDP)
    pub = rp.RtspPublisher("rtsp://127.0.0.1:1/x", sdp, tracks)
    pub.close()
    pub.close()
    assert not pub.alive


def test_close_racing_an_aborting_handshake_does_not_raise(go2rtc):
    """close() used to read self._sock twice; the aborting handshake clears it
    in between, and the AttributeError escaped the publisher's cleanup."""
    sdp, tracks, _ = rp.publish_sdp_from_serve_sdp(SERVE_SDP)
    pub = rp.RtspPublisher(go2rtc.url(), sdp, tracks)
    pub.connect()

    class Vanishing:
        """A socket reference that is cleared the moment close() touches it."""

        def __init__(self, real):
            self.real = real

        def settimeout(self, t):
            pub._sock = None  # the other thread's _close_sock()
            return self.real.settimeout(t)

        def __getattr__(self, name):
            return getattr(self.real, name)

    pub._sock = Vanishing(pub._sock)
    pub.close()  # must not raise
    assert not pub.alive


def test_direct_publish_is_gated_to_the_validated_codec(monkeypatch):
    """H.265 has only ever been published synthetically: the A001064 picks its
    own codec and answered H.264 every time it was asked. Such a session keeps
    the ffmpeg serve, which has carried H.265 in the field all along."""
    from aidot_cameras.camera.sdes_open import _should_direct_publish

    url = "rtsp://127.0.0.1:8554/aidot_x"
    monkeypatch.setenv(rp.ENV_DIRECT_PUBLISH, "1")
    monkeypatch.delenv("AIDOT_DIRECT_PUBLISH_H265", raising=False)
    assert _should_direct_publish(url, None, None, True, 96)  # H.264
    assert not _should_direct_publish(url, None, None, True, 97)  # H.265
    # Codec not known yet (no narrowing): the caller decides later.
    assert _should_direct_publish(url, None, None, True, None)
    # An escape hatch for whoever validates H.265 on real hardware.
    monkeypatch.setenv("AIDOT_DIRECT_PUBLISH_H265", "1")
    assert _should_direct_publish(url, None, None, True, 97)


def test_the_open_passes_the_narrowed_codec_to_the_decision():
    import inspect

    from aidot_cameras.camera import sdes_open

    src = inspect.getsource(sdes_open)
    assert (
        "_direct_publish = _should_direct_publish(\n"
        "            rtsp_push_url, output_path, max_seconds, _use_plain_rtp, _keep_v\n"
    ) in src


# --------------------------------------------------------------------------- #
# DTLS audio conditioning                                                      #
# --------------------------------------------------------------------------- #


def _alaw_tone(level: int, n: int = 160) -> bytes:
    """``n`` A-law samples of a square wave at +/- ``level``."""
    from aidot_cameras.g711 import linear2alaw

    return bytes(linear2alaw(level if i % 2 else -level) for i in range(n))


def _alaw_rms(payload: bytes) -> float:
    vals = [rp._alaw_to_linear(b) for b in payload]
    return (sum(float(v) * v for v in vals) / len(vals)) ** 0.5


def test_agc_lifts_a_quiet_camera_toward_the_target():
    """The mux ran a level tracker toward -15 dBFS; publishing raw A-law would
    have dropped it, leaving quiet cameras quiet."""
    agc = rp.AlawAgc(env={})
    quiet = _alaw_tone(600)  # about -35 dBFS
    out = quiet
    for _ in range(40):  # the tracker smooths, so let it settle
        out = agc.process(quiet)
    assert _alaw_rms(out) > _alaw_rms(quiet) * 4


def test_agc_limits_a_loud_camera_instead_of_clipping():
    agc = rp.AlawAgc(env={})
    loud = _alaw_tone(30000)
    out = loud
    for _ in range(40):
        out = agc.process(loud)
    assert _alaw_rms(out) <= 32767
    # Pulled down toward the target rather than left at full scale.
    assert _alaw_rms(out) < _alaw_rms(loud)


def test_agc_gate_does_not_amplify_near_silence():
    """Below the gate the gain is faded out quadratically - without that, the
    A-law quantization floor of a silent camera becomes audible clicking."""
    agc = rp.AlawAgc(env={})
    silence = _alaw_tone(4)
    out = silence
    for _ in range(40):
        out = agc.process(silence)
    assert _alaw_rms(out) < 200


def test_agc_can_be_turned_off_and_passes_bytes_through():
    agc = rp.AlawAgc(env={"AIDOT_AUDIO_AGC": "0"})
    payload = _alaw_tone(600)
    assert agc.process(payload) is payload


def test_agc_reads_the_same_knobs_as_the_mux():
    agc = rp.AlawAgc(
        env={
            "AIDOT_AUDIO_TARGET_DBFS": "-20",
            "AIDOT_AUDIO_MAXGAIN_DB": "6",
            "AIDOT_AUDIO_MINGAIN_DB": "-3",
            "AIDOT_AUDIO_GATE_DBFS": "-50",
        }
    )
    assert round(agc.target) == round(rp._db2amp(-20) * 32767)
    assert round(agc.maxg, 6) == round(rp._db2amp(6), 6)
    assert round(agc.ming, 6) == round(rp._db2amp(-3), 6)
    assert round(agc.gate) == round(rp._db2amp(-50) * 32767)
    # A malformed value falls back to the default rather than raising.
    assert (
        rp.AlawAgc(env={"AIDOT_AUDIO_TARGET_DBFS": "loud"}).target
        == rp.AlawAgc(env={}).target
    )


def test_the_dtls_runner_conditions_its_audio():
    import inspect

    src = inspect.getsource(rp.dtls_rtp_publish_run)
    assert "agc = AlawAgc()" in src and "agc.process(adata)" in src


def test_gap_warn_threshold_env():
    import os

    from unittest.mock import patch

    with patch.dict(os.environ, {}, clear=False):
        os.environ.pop("AIDOT_PUBLISH_GAP_WARN_S", None)
        assert rp._publish_gap_warn_s() == 1.0
        os.environ["AIDOT_PUBLISH_GAP_WARN_S"] = "2.5"
        assert rp._publish_gap_warn_s() == 2.5
        os.environ["AIDOT_PUBLISH_GAP_WARN_S"] = "0"
        assert rp._publish_gap_warn_s() == 0.0  # disabled
        os.environ["AIDOT_PUBLISH_GAP_WARN_S"] = "nonsense"
        assert rp._publish_gap_warn_s() == 1.0


def test_dtls_runner_reports_its_worst_frame_gap(go2rtc):
    """A viewer sees a stall as a gap between frames; the session reports its
    worst one so a repeat of the unexplained 1.63 s gap can be attributed."""
    import queue

    vq, aq = queue.Queue(), queue.Queue()
    stop, res = threading.Event(), {}
    t = threading.Thread(
        target=rp.dtls_rtp_publish_run,
        args=(vq, aq, go2rtc.url(), [0.0], stop),
        kwargs={"result": res},
        daemon=True,
    )
    t.start()
    assert _wait(lambda: "RECORD" in go2rtc.requests)
    vq.put((b"\x00\x00\x00\x01\x65" + b"k" * 200, 0, True))
    assert _wait(lambda: any(ch == 0 for ch, _ in go2rtc.frames))
    time.sleep(0.3)
    vq.put((b"\x00\x00\x00\x01\x41" + b"d" * 50, 6000, False))
    assert _wait(lambda: len([1 for ch, _ in go2rtc.frames if ch == 0]) >= 2)
    stop.set()
    t.join(3)
    assert res["max_frame_gap_s"] >= 0.25


def test_dtls_runner_attributes_a_gap_to_drops_or_starvation(go2rtc):
    """A gap means one of three things - nothing arrived, what arrived was
    dropped, or the publish blocked - and the warning could not tell them
    apart. The session result carries the drop counts behind it."""
    import queue

    vq, aq = queue.Queue(), queue.Queue()
    stop, res = threading.Event(), {}
    t = threading.Thread(
        target=rp.dtls_rtp_publish_run,
        args=(vq, aq, go2rtc.url(), [0.0], stop),
        kwargs={"result": res},
        daemon=True,
    )
    t.start()
    assert _wait(lambda: "RECORD" in go2rtc.requests)
    # Two non-keyframes before the first keyframe: dropped, not published.
    vq.put((b"\x00\x00\x00\x01\x41" + b"a" * 30, 0, False))
    vq.put((b"\x00\x00\x00\x01\x41" + b"b" * 30, 3000, False))
    vq.put((b"\x00\x00\x00\x01\x65" + b"k" * 60, 6000, True))
    assert _wait(lambda: any(ch == 0 for ch, _ in go2rtc.frames))
    # A presentation time already served: dropped as re-sent.
    vq.put((b"\x00\x00\x00\x01\x41" + b"c" * 30, 6000, False))
    time.sleep(0.4)
    stop.set()
    t.join(3)
    assert res["skipped_pre_keyframe"] == 2
    assert res["dropped_resent"] == 1


def _gap_fields(caplog):
    """(idle, blocked, skipped, dropped) from the gap warning, or None."""
    rx = re.compile(
        r"([0-9.]+) s idle waiting for one to arrive, ([0-9.]+) s inside the"
        r" publish, (\d+) skipped waiting for a keyframe, (\d+) dropped"
    )
    for rec in caplog.records:
        m = rx.search(rec.getMessage())
        if m:
            return (
                float(m.group(1)),
                float(m.group(2)),
                int(m.group(3)),
                int(m.group(4)),
            )
    return None


def _run_gap(go2rtc, feed):
    import queue

    vq, aq = queue.Queue(), queue.Queue()
    stop, res = threading.Event(), {}
    t = threading.Thread(
        target=rp.dtls_rtp_publish_run,
        args=(vq, aq, go2rtc.url(), [0.0], stop),
        kwargs={"result": res},
        daemon=True,
    )
    t.start()
    assert _wait(lambda: "RECORD" in go2rtc.requests)
    vq.put((b"\x00\x00\x00\x01\x65" + b"k" * 60, 0, True))
    assert _wait(lambda: any(ch == 0 for ch, _ in go2rtc.frames))
    feed(vq)
    vq.put((b"\x00\x00\x00\x01\x41" + b"d" * 30, 30000, False))
    time.sleep(0.4)
    stop.set()
    t.join(3)
    return res


def test_gap_warning_separates_starvation_from_dropped_frames(go2rtc, caplog):
    """The gap line has to say WHICH cause, not just that there was a gap."""
    with caplog.at_level(logging.WARNING, logger="aidot_cameras.camera.rtsp_publish"):
        _run_gap(go2rtc, lambda vq: time.sleep(1.3))
    got = _gap_fields(caplog)
    assert got is not None, "expected a gap warning"
    idle, _blocked, _skipped, dropped = got
    assert idle >= 1.0, f"starvation reported idle={idle}"
    assert dropped == 0

    caplog.clear()

    def keep_arriving(vq):
        end = time.monotonic() + 1.3
        while time.monotonic() < end:
            # Arrives, but its presentation time was already served.
            vq.put((b"\x00\x00\x00\x01\x41" + b"c" * 30, 0, False))
            time.sleep(0.05)

    with caplog.at_level(logging.WARNING, logger="aidot_cameras.camera.rtsp_publish"):
        _run_gap(go2rtc, keep_arriving)
    got = _gap_fields(caplog)
    assert got is not None
    idle, _blocked, _skipped, dropped = got
    assert idle < 0.5, f"drops reported idle={idle}"
    assert dropped >= 10


def test_a_silence_ending_in_a_resend_burst_still_reads_as_silence(go2rtc, caplog):
    """The realistic shape, and the one that broke two earlier attempts.

    This camera family re-sends runs of already-served timestamps - 40.75% of
    frames, bursts of up to 41, about twice a second - so a silence normally
    ENDS in a resend burst. Timing the gap to the LAST arrival before the
    publish reports ~0 s here and blames the drop path, when the camera was in
    fact silent for the whole gap.
    """

    def silence_then_burst(vq):
        time.sleep(1.3)
        # The burst that ends the silence starts with already-served frames.
        for _ in range(5):
            vq.put((b"\x00\x00\x00\x01\x41" + b"r" * 30, 0, False))

    with caplog.at_level(logging.WARNING, logger="aidot_cameras.camera.rtsp_publish"):
        _run_gap(go2rtc, silence_then_burst)
    got = _gap_fields(caplog)
    assert got is not None, "expected a gap warning"
    idle, _blocked, _skipped, dropped = got
    assert idle >= 1.0, f"silence ending in a resend burst reported idle={idle}"
    assert dropped >= 1, "the burst should still be counted as drops"


def test_agc_ignores_a_non_finite_gain_setting():
    """`inf`/`nan` parse as floats and would reach math.log() when the
    translate table is keyed - raising inside the publish loop, which takes
    the stream down. They fall back to the default instead."""
    for bad in ("inf", "-inf", "nan"):
        agc = rp.AlawAgc(env={"AIDOT_AUDIO_MINGAIN_DB": bad})
        assert math.isfinite(agc.ming), bad
        # The real hazard: conditioning a frame must not raise.
        assert len(agc.process(bytes(range(256)))) == 256
    for bad in ("nan", "inf"):
        agc = rp.AlawAgc(env={"AIDOT_AUDIO_TARGET_DBFS": bad})
        assert math.isfinite(agc.target), bad
        assert len(agc.process(b"\xd5" * 160)) == 160
