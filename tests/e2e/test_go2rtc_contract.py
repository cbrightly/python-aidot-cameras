"""go2rtc integration, against a real HTTP go2rtc stub.

Five distinct go2rtc regressions shipped inside one week, and all of them are
invisible from inside the client - they are only observable from what go2rtc
receives:

- a viewer check that ran inside the 0.5s watchdog and opened a fresh
  aiohttp session per tick: ~2 req/s per camera, go2rtc stopped answering and
  EVERY camera went black while the integration reported streaming
- a stream registered as its OWN source (push mode), so go2rtc became its own
  producer: HTTP 200 with a zero-byte frame
- idle-release keyed on "is a TCP client on the serve port?" while go2rtc
  attaches as the stream's PRODUCER and never leaves, so no camera ever went
  dormant and batteries drained

The stub records every request, which is what makes these assertable.
"""
import asyncio

import pytest

from aidot_cameras.camera.go2rtc import Go2rtcClient, prefer_go2rtc

pytestmark = [pytest.mark.e2e, pytest.mark.timeout(60)]


async def test_viewer_count_reads_consumers_not_producers(fake_go2rtc):
    """go2rtc's producer is go2rtc itself; only consumers are viewers.

    This is the whole idle-release bug in one assertion: a stream go2rtc is
    pulling (producer attached) with nobody watching (no consumers) must
    report ZERO viewers, or the camera never goes dormant.
    """
    import aiohttp

    fake_go2rtc.streams["cam-1"] = ["http://serve/x.ts"]
    fake_go2rtc.producers["cam-1"] = [{"type": "http", "remote": "go2rtc-itself"}]
    fake_go2rtc.consumers["cam-1"] = []

    async with aiohttp.ClientSession() as s:
        count = await Go2rtcClient(s, fake_go2rtc.base_url).viewer_count("cam-1")
    assert count == 0, (
        f"a producer-only stream must report 0 viewers, got {count} - this is "
        "what kept every camera streaming forever after a single view"
    )


async def test_viewer_count_sees_real_consumers(fake_go2rtc):
    import aiohttp

    fake_go2rtc.streams["cam-1"] = ["http://serve/x.ts"]
    fake_go2rtc.producers["cam-1"] = [{"type": "http"}]
    fake_go2rtc.consumers["cam-1"] = [{"type": "webrtc"}, {"type": "webrtc"}]

    async with aiohttp.ClientSession() as s:
        assert await Go2rtcClient(s, fake_go2rtc.base_url).viewer_count("cam-1") == 2


async def test_viewer_count_is_unknown_for_an_absent_stream(fake_go2rtc):
    """Unknown must be None, never 0: callers must not release on ignorance."""
    import aiohttp

    async with aiohttp.ClientSession() as s:
        assert await Go2rtcClient(s, fake_go2rtc.base_url).viewer_count("nope") is None


async def test_null_consumers_reads_as_zero_not_unknown(fake_go2rtc):
    """go2rtc reports null rather than [] when a stream has no consumers."""
    import aiohttp

    fake_go2rtc.streams["cam-1"] = ["src"]
    fake_go2rtc.consumers["cam-1"] = None
    async with aiohttp.ClientSession() as s:
        assert await Go2rtcClient(s, fake_go2rtc.base_url).viewer_count("cam-1") == 0


async def test_registration_records_the_serve_url_as_the_source(fake_go2rtc):
    import aiohttp

    async with aiohttp.ClientSession() as s:
        url = await prefer_go2rtc(
            s, "cam-1", "http://127.0.0.1:9999/cam.ts",
            base_url=fake_go2rtc.base_url,
        )
    assert url and url.startswith("rtsp://"), f"expected an RTSP pull URL, got {url!r}"
    assert fake_go2rtc.sources_for("cam-1") == ["http://127.0.0.1:9999/cam.ts"]


async def test_a_stream_is_never_registered_as_its_own_source(fake_go2rtc):
    """Push mode: the keepalive URL IS go2rtc's address for this stream.

    Registering it makes go2rtc its own producer - a loop that answers HTTP 200
    with a zero-byte frame. The guard is _is_self_referential_source; this pins
    that it recognizes the shapes go2rtc actually hands out.
    """
    from aidot_cameras.camera.client import _is_self_referential_source

    name = "cam-1"
    # The push URL is always RTSP (that is what an RTSP publish target is), so
    # the guard is deliberately scoped to rtsp:// / rtsps://. These are the
    # shapes the keepalive can actually produce.
    for own_url in (
        f"rtsp://127.0.0.1:8554/{name}",
        f"rtsp://localhost:8554/{name}",
        f"rtsps://127.0.0.1:8554/{name}",
        f"rtsp://127.0.0.1:8554/{name}/",       # trailing slash
    ):
        assert _is_self_referential_source(own_url, name), (
            f"{own_url!r} is go2rtc's own address for {name!r} and must be "
            "refused as a source"
        )

    # A real serve URL must still be accepted, or nothing ever registers.
    assert not _is_self_referential_source("http://127.0.0.1:9999/cam.ts", name)
    assert not _is_self_referential_source(f"rtsp://127.0.0.1:8554/other-{name}x", name)
    assert not _is_self_referential_source("", name)
    assert not _is_self_referential_source(f"rtsp://127.0.0.1:8554/{name}", "")


async def test_viewer_checks_do_not_storm_go2rtc(fake_go2rtc, e2e_device_client):
    """Repeated viewer checks must be throttled, not one request per tick.

    The storm version issued ~2 req/s per camera; with a handful of cameras
    go2rtc stopped answering and every picture went black while the
    integration still reported streaming.
    """
    dc = e2e_device_client("A001513")
    dc._go2rtc_url = fake_go2rtc.base_url
    dc._keepalive_rtsp_url = "http://127.0.0.1:9999/cam.ts"
    name = dc._go2rtc_stream_name()
    fake_go2rtc.streams[name] = ["http://127.0.0.1:9999/cam.ts"]
    fake_go2rtc.consumers[name] = []

    # Drive the check at the watchdog's own cadence for a few seconds.
    deadline = asyncio.get_running_loop().time() + 3.0
    calls = 0
    while asyncio.get_running_loop().time() < deadline:
        await dc._viewer_present(0)
        calls += 1
        await asyncio.sleep(0.05)

    requests = fake_go2rtc.request_count("/api/streams")
    assert calls > 20, f"the probe itself did not run often enough ({calls})"
    assert requests < calls / 2, (
        f"{calls} viewer checks produced {requests} go2rtc requests - the "
        "throttle is not holding, which is the request storm that took go2rtc "
        "down"
    )
    assert fake_go2rtc.peak_rate_per_s(1.0) <= 4, (
        f"peak {fake_go2rtc.peak_rate_per_s(1.0):.1f} req/s against go2rtc for "
        "ONE camera - multiply that by the fleet"
    )


async def test_deregistration_removes_the_stream(fake_go2rtc, e2e_device_client):
    """A dormant camera must not leave a producerless stream behind."""
    dc = e2e_device_client("A001513")
    dc._go2rtc_url = fake_go2rtc.base_url
    name = dc._go2rtc_stream_name()
    fake_go2rtc.streams[name] = ["http://127.0.0.1:9999/cam.ts"]

    await dc._deregister_go2rtc()
    assert name not in fake_go2rtc.streams, (
        "the stream survived deregistration - it sits in go2rtc with a "
        "producer nothing is feeding"
    )
