"""The DTLS recorder must write what it receives while the recording runs.

``output_path`` on a DTLS camera records through the vendored aiortc
MediaRecorder, which re-encodes the decoded video with libx264. With x264's
default lookahead the encoder holds dozens of frames before it emits a packet,
and a small host encodes so slowly (measured on a Pi Zero 2 W: 32 frames of
1080p in 20 s) that a whole recording can pass with nothing written.
"""

import asyncio
import fractions
import os

import pytest

av = pytest.importorskip("av")
np = pytest.importorskip("numpy")

from aidot_cameras._vendor.aiortc.contrib.media import MediaRecorder  # noqa: E402
from aidot_cameras._vendor.aiortc.mediastreams import MediaStreamTrack  # noqa: E402


class _FiveFrames(MediaStreamTrack):
    kind = "video"

    def __init__(self):
        super().__init__()
        self.sent = 0
        rng = np.random.default_rng(7)
        self._img = rng.integers(0, 255, (720 * 3 // 2, 1280), dtype=np.uint8)

    async def recv(self):
        if self.sent >= 5:
            await asyncio.sleep(3600)  # a live track that has nothing more yet
        frame = av.VideoFrame.from_ndarray(self._img, format="yuv420p")
        frame.pts, frame.time_base = self.sent, fractions.Fraction(1, 15)
        self.sent += 1
        return frame


def test_a_few_frames_reach_the_file_before_the_recording_stops(tmp_path):
    out = str(tmp_path / "rec.ts")

    async def run():
        track = _FiveFrames()
        rec = MediaRecorder(out)
        rec.addTrack(track)
        await rec.start()
        for _ in range(100):
            await asyncio.sleep(0.05)
            if track.sent >= 5 and os.path.exists(out) and os.path.getsize(out) > 0:
                break
        size = os.path.getsize(out) if os.path.exists(out) else 0
        await rec.stop()
        return track.sent, size

    sent, size = asyncio.run(run())
    assert sent == 5
    assert size > 0, "five frames in, nothing written while recording"
