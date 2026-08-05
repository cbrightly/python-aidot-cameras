"""The push-mode serve, judged from the RTSP server it publishes into.

Push mode is the half of the serve that CI could not see. The library builds an
ffmpeg argv that publishes to ``rtsp://127.0.0.1:8554/<stream>``; go2rtc has a
producer for that stream only once the publish lands, and only keeps one while
frames keep arriving. Everything the unit tier can reach - the argv, the SDP
narrowing, the go2rtc registration - can be perfectly correct while no producer
ever exists, and then every viewer gets ``DESCRIBE ... 404``.

That is exactly what shipped: the serve was spawned with ``stderr=PIPE`` and
nothing read it, so once ffmpeg had written ~64KB of warnings it blocked on its
next stderr write and the publisher died mid-stream. The stream still existed in
go2rtc, still had its placeholder source, and answered every viewer with a 404.
Nothing in the suite asserted "a producer landed and is still being fed", so
nothing failed.

These tests assert it against a real RTSP endpoint, driven by the real argv
builder and the real drain helper:

- the publisher reaches ANNOUNCE/RECORD and media flows      (it landed)
- it is still flowing after ffmpeg has logged continuously   (it stays)
- the un-drained variant stalls                              (the control)

The noise is produced the way production produced it: RTP arrives with packets
missing, so ffmpeg logs at its normal ``-loglevel warning``. The relay drops
every second packet to reach that volume in seconds rather than minutes.

Scope: these spawn the serve the way the library does and install the drain
explicitly, so they pin the MECHANISM and the publish contract. That the
library's own spawn sites still install the drain is pinned separately, by
``test_every_piped_spawn_installs_the_drain`` in the unit tier - a source
guard, because a deleted drain call is invisible until ~64KB of ffmpeg output
has accumulated. The two together are what make this outage catchable.
"""
import asyncio
import os
import shutil
import socket
import subprocess
import tempfile
import threading

import pytest

from aidot_cameras.camera.client import _build_sdes_serve_cmd
from aidot_cameras.camera.sdes_open import _start_serve_stderr_drain
from tests.e2e.fakes.rtsp_sink import FakeRtspSink

pytestmark = [pytest.mark.e2e, pytest.mark.timeout(120)]

pytest.importorskip("aiohttp")

if shutil.which("ffmpeg") is None:                       # pragma: no cover
    pytest.skip("the serve is an ffmpeg subprocess", allow_module_level=True)


def _free_udp_port() -> int:
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    sock.bind(("127.0.0.1", 0))
    port = sock.getsockname()[1]
    sock.close()
    return port


class _LossyRtpFeed:
    """A camera stand-in: RTP video that arrives with packets missing.

    ffmpeg (as the feeder) sends RTP to a relay; the relay forwards every other
    packet to the port the serve reads. The gaps make the serve's ffmpeg log
    continuously at its normal level, which is the condition the drain exists
    for. Also writes the SDP the serve is given, pointed at the relay's output.
    """

    def __init__(self) -> None:
        self._stop = threading.Event()
        self._feeder: subprocess.Popen | None = None
        self._thread: threading.Thread | None = None
        self._tmp = tempfile.mkdtemp(prefix="aidot-e2e-serve-")
        self.sdp_path = os.path.join(self._tmp, "camera.sdp")

    async def start(self) -> "_LossyRtpFeed":
        src_port, dst_port = _free_udp_port(), _free_udp_port()
        self._thread = threading.Thread(
            target=self._relay, args=(src_port, dst_port), daemon=True
        )
        self._thread.start()
        self._feeder = subprocess.Popen(
            [
                "ffmpeg", "-hide_banner", "-loglevel", "error", "-re",
                "-f", "lavfi", "-i", "testsrc=size=640x480:rate=50",
                # mpeg4 is built into every ffmpeg; libx264 is not, and a skipped
                # test here would be a silent hole in exactly this coverage.
                "-c:v", "mpeg4", "-b:v", "6000k",
                "-f", "rtp", "-sdp_file", self.sdp_path,
                f"rtp://127.0.0.1:{src_port}",
            ],
            stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL,
        )
        for _ in range(150):
            if os.path.exists(self.sdp_path) and os.path.getsize(self.sdp_path):
                break
            await asyncio.sleep(0.1)
        else:                                            # pragma: no cover
            raise AssertionError("the RTP feeder never wrote its SDP")
        # Read fully before opening for write: the SDP ffmpeg wrote points at the
        # feeder's own port, and the serve has to read the relay's output port.
        sdp = open(self.sdp_path).read()
        with open(self.sdp_path, "w") as handle:
            handle.write(sdp.replace(f"m=video {src_port}", f"m=video {dst_port}"))
        return self

    def _relay(self, src_port: int, dst_port: int) -> None:
        rx = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        rx.bind(("127.0.0.1", src_port))
        rx.settimeout(0.5)
        tx = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        seen = 0
        while not self._stop.is_set():
            try:
                data, _addr = rx.recvfrom(65535)
            except TimeoutError:
                continue
            except OSError:                          # pragma: no cover
                break
            seen += 1
            if seen % 2:
                tx.sendto(data, ("127.0.0.1", dst_port))
        rx.close()
        tx.close()

    def stop(self) -> None:
        self._stop.set()
        if self._feeder is not None:
            self._feeder.kill()
            self._feeder.wait(timeout=5)
        shutil.rmtree(self._tmp, ignore_errors=True)


@pytest.fixture
async def rtsp_sink():
    sink = FakeRtspSink()
    await sink.start()
    try:
        yield sink
    finally:
        await sink.stop()


@pytest.fixture
async def lossy_feed():
    feed = _LossyRtpFeed()
    await feed.start()
    try:
        yield feed
    finally:
        feed.stop()


def _spawn_serve(feed: _LossyRtpFeed, sink: FakeRtspSink, *, drain: bool):
    """Spawn the serve exactly as the library does, optionally without the drain.

    The argv comes from the shipped builder, so a change to the push
    destination, the transport, or the codec mapping is exercised here rather
    than re-stated.
    """
    cmd = _build_sdes_serve_cmd(
        sdp_path=feed.sdp_path,
        rtsp_push_url=sink.url_for("cam-1"),
        push_video_only=True,
    )
    proc = subprocess.Popen(
        cmd, stdout=subprocess.DEVNULL, stderr=subprocess.PIPE
    )
    if drain:
        _start_serve_stderr_drain(proc)
    return proc


async def _kill(proc) -> None:
    proc.kill()
    await asyncio.get_running_loop().run_in_executor(None, proc.wait)


async def test_the_publisher_lands_in_the_rtsp_server(rtsp_sink, lossy_feed):
    """No producer means every viewer gets a 404, however healthy the camera is."""
    proc = _spawn_serve(lossy_feed, rtsp_sink, drain=True)
    try:
        assert await rtsp_sink.wait_for_recording(30), (
            "the serve never got to RECORD - go2rtc would hold only the "
            f"placeholder source and 404 every viewer. Requests seen: "
            f"{rtsp_sink.requests}"
        )
        assert "ANNOUNCE" in rtsp_sink.requests
        assert rtsp_sink.announced_sdp, "the publisher announced no SDP"
        assert await rtsp_sink.wait_for_bytes(20_000, 30), (
            "RECORD landed but no media followed - a producer that feeds "
            f"nothing (got {rtsp_sink.bytes_recv} bytes)"
        )
    finally:
        await _kill(proc)


async def test_the_publisher_keeps_feeding_while_ffmpeg_logs(rtsp_sink, lossy_feed):
    """The regression itself: continuous logging must not stop the publish.

    Without the drain ffmpeg blocks on its stderr write once the pipe fills, and
    the RTSP publish dies with it. With the drain the same stream keeps flowing.
    """
    proc = _spawn_serve(lossy_feed, rtsp_sink, drain=True)
    try:
        assert await rtsp_sink.wait_for_recording(30)
        assert await rtsp_sink.wait_for_bytes(20_000, 30)
        # Sit past the point where the un-drained control below has already
        # gone silent - ffmpeg writes the ~64KB that fills the pipe within a
        # few seconds at this noise rate - then check twice that media is still
        # moving.
        for checkpoint in (8.0, 4.0):
            await asyncio.sleep(checkpoint)
            assert await rtsp_sink.still_flowing(), (
                "the publisher went silent during a run WITH the stderr drain "
                f"installed - media stopped reaching the RTSP server after "
                f"{rtsp_sink.bytes_recv} bytes"
            )
        tail = list(getattr(proc, "_aidot_stderr_tail", []))
        assert tail, (
            "the serve logged nothing, so this run never exercised the "
            "condition the drain exists for - the test has stopped proving "
            "anything and its noise source needs to be made noisier"
        )
        assert proc.poll() is None, (
            f"the serve exited (rc={proc.poll()}) mid-run; last stderr: {tail[-3:]}"
        )
    finally:
        await _kill(proc)


async def test_an_undrained_serve_stalls_the_publisher(rtsp_sink, lossy_feed):
    """Control: the failure the drain prevents must still be reachable here.

    If this ever stops stalling - a larger pipe, a quieter ffmpeg - then the
    test above is passing for free and no longer covers the outage. Failing
    loudly at that point is the intent, not noise.
    """
    proc = _spawn_serve(lossy_feed, rtsp_sink, drain=False)
    try:
        assert await rtsp_sink.wait_for_recording(30)
        assert await rtsp_sink.wait_for_bytes(20_000, 30)
        stalled = False
        for _ in range(12):
            # Two silent windows back to back: one alone could be a starved
            # runner rather than a dead publisher.
            if await rtsp_sink.media_flowed_during(1.5) == 0 and \
                    await rtsp_sink.media_flowed_during(1.5) == 0:
                stalled = True
                break
        assert stalled, (
            "an un-drained serve kept publishing for 18s. Either ffmpeg has "
            "stopped logging enough to fill the pipe, or this platform buffers "
            "far more than the 64KB this outage depended on - either way the "
            "drained test above is no longer proving the fix"
        )
    finally:
        await _kill(proc)
