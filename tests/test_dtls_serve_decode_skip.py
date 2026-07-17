"""Serve-path decode skip + corruption canary (CameraMixin._install_encoded_tap).

For a DTLS camera being SERVED, the served H.264 bytes are teed pre-decode into
the mux queue and muxed as copy; the vendored decoder's output is discarded
(no-op on_frame).  So in serve mode the tap must NOT feed the vendored decoder
queue for video DATA frames (wasted CPU + the sole source of the decode-failure
warning flood), while STILL forwarding the None terminator so the decoder thread
exits cleanly.  Because decode was the only signal the served H.264 was corrupt,
a cheap keyframe-based canary (no decoding) is kept in its place.

Non-serve (live-view) taps and audio taps must keep feeding the decoder as
before.

Repo convention: no pytest-asyncio; these are plain synchronous unit tests.
"""
import logging

from aidot.camera.client import CameraMixin


# Annex-B H.264 fragments (start code + NAL header byte).  NAL type is low 5
# bits of the byte after 00 00 01: 5 = IDR (keyframe), 7 = SPS (keyframe), 1 =
# non-IDR slice (delta).  Pad so _h264_has_keyframe's `i < n - 4` loop runs.
_KEYFRAME = b"\x00\x00\x01\x65\x88\x84\x00\x10"   # 0x65 & 0x1F == 5 (IDR)
_DELTA = b"\x00\x00\x01\x41\x9a\x00\x00\x10"       # 0x41 & 0x1F == 1 (non-IDR)


class _Enc:
    """Stand-in for aiortc's JitterFrame (task[1]): has .data and .timestamp."""

    def __init__(self, data, timestamp):
        self.data = data
        self.timestamp = timestamp


class _FakeQueue:
    """Stand-in for the receiver's __decoder_queue: records original put()s."""

    def __init__(self):
        self.puts = []

    def put(self, task, *a, **k):
        self.puts.append(task)


class _FakeReceiver:
    def __init__(self, qd):
        # Matches aiortc's name-mangled attribute the tap reads.
        self._RTCRtpReceiver__decoder_queue = qd


class _OutQ:
    """Stand-in for the mux queue (queue.Queue): records teed items."""

    def __init__(self):
        self.items = []

    def put_nowait(self, item):
        self.items.append(item)


def _task(data, ts=1000):
    return (0, _Enc(data, ts))


def test_serve_video_skips_decoder_put_for_data_frames():
    qd = _FakeQueue()
    out_q = _OutQ()
    rcv = _FakeReceiver(qd)

    assert CameraMixin._install_encoded_tap(rcv, out_q, True, serve=True)

    # Two video data frames: a keyframe then a delta.
    qd.put(_task(_KEYFRAME))
    qd.put(_task(_DELTA))

    # Both are teed to the mux queue as (bytes, ts, is_keyframe).
    assert len(out_q.items) == 2
    assert out_q.items[0] == (_KEYFRAME, 1000, True)
    assert out_q.items[1] == (_DELTA, 1000, False)

    # But the vendored decoder queue is NOT fed for serve-mode video data frames.
    assert qd.puts == []


def test_serve_video_still_forwards_none_terminator():
    qd = _FakeQueue()
    out_q = _OutQ()
    rcv = _FakeReceiver(qd)

    assert CameraMixin._install_encoded_tap(rcv, out_q, True, serve=True)

    qd.put(_task(_KEYFRAME))     # data frame: skipped
    qd.put(None)                 # terminator: MUST be forwarded

    assert qd.puts == [None]


def test_serve_video_canary_counts_frames_and_keyframe_gaps():
    qd = _FakeQueue()
    out_q = _OutQ()
    rcv = _FakeReceiver(qd)

    assert CameraMixin._install_encoded_tap(rcv, out_q, True, serve=True)

    canary = qd._aidot_serve_canary
    assert canary["frames"] == 0 and canary["keyframes"] == 0

    qd.put(_task(_KEYFRAME))
    qd.put(_task(_DELTA))
    qd.put(_task(_DELTA))
    qd.put(_task(_KEYFRAME))

    assert canary["frames"] == 4
    assert canary["keyframes"] == 2
    # Longest run with no keyframe was the two deltas between the keyframes.
    assert canary["max_gap"] == 2
    # Current gap reset to 0 by the trailing keyframe.
    assert canary["gap"] == 0


def test_serve_video_canary_emits_periodic_debug_summary(caplog):
    qd = _FakeQueue()
    out_q = _OutQ()
    rcv = _FakeReceiver(qd)

    assert CameraMixin._install_encoded_tap(rcv, out_q, True, serve=True)

    with caplog.at_level(logging.DEBUG, logger="aidot.camera.client"):
        # 299 frames: no summary yet (cadence is every 300).
        for _ in range(299):
            qd.put(_task(_DELTA))
        assert "serve h264 canary" not in caplog.text
        # 300th frame trips the periodic DEBUG summary exactly once.
        qd.put(_task(_DELTA))

    lines = [r for r in caplog.records if "serve h264 canary" in r.getMessage()]
    assert len(lines) == 1
    assert "frames=300" in lines[0].getMessage()
    # Summaries are DEBUG so a corrupt camera can't recreate the warning flood.
    assert lines[0].levelno == logging.DEBUG


def test_non_serve_video_still_feeds_decoder():
    qd = _FakeQueue()
    out_q = _OutQ()
    rcv = _FakeReceiver(qd)

    # serve defaults to False (live-view semantics).
    assert CameraMixin._install_encoded_tap(rcv, out_q, True)

    qd.put(_task(_KEYFRAME))
    qd.put(None)

    # Live-view path is unchanged: every task reaches the decoder queue, and no
    # canary is installed.
    assert len(qd.puts) == 2
    assert qd.puts[0][1].data == _KEYFRAME   # data frame forwarded to decoder
    assert qd.puts[1] is None
    assert not hasattr(qd, "_aidot_serve_canary")
    # Still teed to the mux queue.
    assert out_q.items == [(_KEYFRAME, 1000, True)]


def test_serve_audio_still_feeds_decoder():
    qd = _FakeQueue()
    out_q = _OutQ()
    rcv = _FakeReceiver(qd)

    # Audio tap (is_video=False) even in serve mode keeps feeding the decoder:
    # the skip is scoped to H.264 video, and audio is drained separately.
    assert CameraMixin._install_encoded_tap(rcv, out_q, False, serve=True)

    qd.put((0, _Enc(b"\x00\x01\x02\x03", 2000)))
    qd.put(None)

    assert len(qd.puts) == 2
    assert qd.puts[1] is None
    assert not hasattr(qd, "_aidot_serve_canary")
    # Audio is teed as (bytes, ts) with no keyframe field.
    assert out_q.items == [(b"\x00\x01\x02\x03", 2000)]
