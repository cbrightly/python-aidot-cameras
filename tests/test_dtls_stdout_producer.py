"""The DTLS serve can write MPEG-TS to stdout for a go2rtc ``exec:`` source.

SDES cameras get a lazily-managed producer for free: go2rtc spawns the process
per ``{output}``, the library RTSP-pushes into it, and go2rtc kills it when the
last viewer leaves. DTLS had no equivalent - it could only serve on an
``-listen`` socket, which has to be bound before anyone asks for it and stays
bound afterwards.

``-`` closes that gap: the mux writes to this process's own stdout, so whoever
spawned us is the consumer and owns the lifecycle. It used to go through an
ffmpeg ``-c copy`` hop; the mux's MPEG-TS now goes to stdout as written. What
these tests pin is that each serve URL reaches its own destination - a
regression here would either break every existing HTTP-listen serve or
silently send media to /dev/null.
"""

import asyncio
import os
import types

import pytest

import aidot_cameras.camera.client as client_mod
from aidot_cameras.camera.client import CameraMixin, _dtls_serve_mode


@pytest.mark.parametrize(
    "url, mode",
    [
        ("-", "stdout"),
        ("http://127.0.0.1:18981/cam.ts", "pull"),
        ("HTTP://127.0.0.1:18981/cam.ts", "pull"),
        ("rtsp://127.0.0.1:8554/driveway", "publish"),
        ("https://127.0.0.1:18981/cam.ts", "none"),
        ("rtsps://127.0.0.1:8554/driveway", "none"),
        ("", "none"),
        (None, "none"),
    ],
)
def test_each_serve_url_has_one_destination(url, mode):
    assert _dtls_serve_mode(url) == mode


def _stdout_run(monkeypatch, make_camera_device_client):
    """One serve run with "-", fd 1 swapped for a pipe we read back."""
    from tests.keepalive_driver import DtlsServeRun, FakeDtlsSession

    cam = make_camera_device_client("A000088")
    run = DtlsServeRun(monkeypatch, cam, [FakeDtlsSession()], url="-", timeout=0.5)

    def _mux(vq, aq, wfile, progress, stop, **kw):
        run.mux_sinks.append(wfile)
        wfile.write(b"\x47" + b"\xff" * 187)
        while not stop.is_set():
            stop.wait(0.002)

    monkeypatch.setattr(client_mod, "_dtls_av_mux_run", _mux)
    r, w = os.pipe()
    saved = os.dup(1)
    os.dup2(w, 1)
    try:
        run.run()
    finally:
        os.dup2(saved, 1)
        os.close(saved)
        os.close(w)
    try:
        os.set_blocking(r, False)
        got = os.read(r, 65536)
    finally:
        os.close(r)
    return run, got


def test_stdout_mode_writes_the_mux_to_this_process_stdout(
    monkeypatch, make_camera_device_client
):
    run, got = _stdout_run(monkeypatch, make_camera_device_client)
    # fd 1, not /dev/null: the media has to reach the parent that spawned us.
    assert got.startswith(b"\x47"), (
        "stdout mode must write to this process's real stdout - anything else "
        "sends the camera to nowhere while looking healthy"
    )
    assert not run.direct, "stdout mode binds no listen port"
    # Each cycle writes to a duplicate of fd 1 and closes only that.
    assert run.mux_sinks and all(f.closed for f in run.mux_sinks)
    os.fstat(1)  # still open


def test_a_failed_stdout_write_releases_the_serve(
    monkeypatch, make_camera_device_client
):
    """go2rtc gone and stdout broken: the mux ends with a failed write, and the
    serve goes dormant instead of restarting a mux into a dead pipe."""
    from tests.keepalive_driver import DtlsServeRun, FakeDtlsSession

    cam = make_camera_device_client("A000088")
    run = DtlsServeRun(monkeypatch, cam, [FakeDtlsSession()], url="-", timeout=2.0)
    started = []

    def _mux(vq, aq, wfile, progress, stop, *, state=None, **kw):
        started.append(wfile)
        state["failed"] = True

    monkeypatch.setattr(client_mod, "_dtls_av_mux_run", _mux)
    run.run()
    assert len(started) == 1, "no second mux into a dead stdout"
    assert [d["why"] for d in run.dormant] == ["idle release"]


def _which_loop(monkeypatch, url, *, sdes=False):
    """Which keepalive loop does start_keepalive pick for ``url``?"""
    picked = []

    async def _noop():
        return None

    obj = types.SimpleNamespace(
        is_sdes_camera=sdes,
        _stream_task=None,
        _serve_relay_opt=None,
        _start_keepalive_renew=lambda: None,
        _sdes_keepalive_loop=lambda: (picked.append("sdes"), _noop())[1],
        _dtls_serve_loop=lambda: (picked.append("serve"), _noop())[1],
        _streaming_loop=lambda: (picked.append("jpeg"), _noop())[1],
        _maybe_register_go2rtc=lambda *a, **k: _noop(),
    )
    fn = CameraMixin.start_keepalive.__get__(obj)

    async def _run():
        await fn(rtsp_push_url=url)
        task = obj._stream_task
        if task is not None:
            await asyncio.gather(task, return_exceptions=True)

    asyncio.run(_run())
    return picked[0] if picked else None


def test_stdout_url_routes_to_the_serve_loop():
    """``-`` has to reach _dtls_serve_loop or the producer never runs.

    It used to fall through the ``startswith("http")`` gate into the on_frame
    JPEG loop, which opens a perfectly healthy WebRTC session and writes
    nothing to stdout - a go2rtc exec: source that hangs forever with no error
    anywhere.  Measured on an A000088: session up, data channel live, zero
    bytes out.  Unit tests of the ffmpeg spawn helper passed throughout,
    because they called it directly and never asked who calls it.
    """
    assert _which_loop(None, "-") == "serve"


def test_http_and_bare_keepalive_routing_are_unchanged():
    assert _which_loop(None, "http://127.0.0.1:18981/cam.ts") == "serve"
    assert _which_loop(None, None) == "jpeg"
    assert _which_loop(None, "http://127.0.0.1:18981/cam.ts", sdes=True) == "sdes"


def test_rtsp_push_publishes_whatever_the_direct_publish_switch_says(
    monkeypatch, make_camera_device_client
):
    """A DTLS rtsp:// push used to go through ffmpeg with the direct publish
    off, transcoding the mux's AAC back to A-law. The publish already sends the
    camera's A-law (and AAC), so the push publishes either way."""
    from tests.keepalive_driver import DtlsServeRun, FakeDtlsSession

    for flag in ("0", "1"):
        monkeypatch.setenv("AIDOT_DIRECT_PUBLISH", flag)
        cam = make_camera_device_client("A000088")
        cam._stream_idle_opt = 0.0
        run = DtlsServeRun(
            monkeypatch,
            cam,
            [FakeDtlsSession()],
            url="rtsp://127.0.0.1:8554/driveway",
            timeout=0.5,
        ).run()
        assert run.mux_targets[:1] == ["publish"], flag
        assert not run.direct


def test_no_serve_url_releases_the_camera_with_a_reason(
    monkeypatch, make_camera_device_client
):
    from tests.keepalive_driver import DtlsServeRun, FakeDtlsSession

    cam = make_camera_device_client("A000088")
    session = FakeDtlsSession()
    run = DtlsServeRun(
        monkeypatch, cam, [session], url="rtsps://127.0.0.1:8554/x", timeout=0.5
    )
    with pytest.raises(client_mod._ServeUnavailable):
        asyncio.run(cam._dtls_serve_loop_inner())
    assert not run.mux_targets
    assert session.stopped == 1


def test_rtsp_url_routes_to_the_serve_loop():
    assert _which_loop(None, "rtsp://127.0.0.1:8554/driveway") == "serve"
