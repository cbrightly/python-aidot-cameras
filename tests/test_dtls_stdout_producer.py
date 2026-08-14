"""The DTLS serve can write MPEG-TS to stdout for a go2rtc ``exec:`` source.

SDES cameras get a lazily-managed producer for free: go2rtc spawns the process
per ``{output}``, the library RTSP-pushes into it, and go2rtc kills it when the
last viewer leaves. DTLS had no equivalent - it could only serve on an
``-listen`` socket, which has to be bound before anyone asks for it and stays
bound afterwards.

``-`` closes that gap: ffmpeg writes to this process's own stdout, so whoever
spawned us is the consumer and owns the lifecycle. Everything else about the
serve is untouched, which is what these tests pin - a regression here would
either break every existing HTTP-listen serve or silently send media to
/dev/null.
"""
import asyncio
import types

import aidot_cameras.camera.client as client_mod
from aidot_cameras.camera.client import CameraMixin


def _spawn(monkeypatch, serve_url):
    """Run the real _spawn_dtls_serve_ffmpeg, capturing what it would exec."""
    seen = {}

    async def _fake_exec(*cmd, stdin=None, stdout=None, stderr=None):
        seen["cmd"] = list(cmd)
        seen["stdout"] = stdout
        seen["stdin"] = stdin
        return object()

    monkeypatch.setattr(client_mod, "_ffmpeg_path", lambda: "/usr/bin/ffmpeg")
    monkeypatch.setattr(asyncio, "create_subprocess_exec", _fake_exec)
    obj = types.SimpleNamespace()
    fn = CameraMixin._spawn_dtls_serve_ffmpeg.__get__(obj)
    proc = asyncio.run(fn(serve_url, 7))
    return proc, seen


def test_stdout_mode_writes_mpegts_to_this_process_stdout(monkeypatch):
    proc, seen = _spawn(monkeypatch, "-")
    assert proc is not None
    assert seen["cmd"][-3:] == ["-f", "mpegts", "pipe:1"], seen["cmd"]
    # fd 1, not DEVNULL: the media has to reach the parent that spawned us.
    assert seen["stdout"] == 1, (
        "stdout mode must hand ffmpeg this process's real stdout - anything "
        "else sends the camera to nowhere while looking healthy"
    )
    assert "-listen" not in seen["cmd"]


def test_http_listen_mode_is_unchanged(monkeypatch):
    url = "http://127.0.0.1:18981/cam.ts"
    proc, seen = _spawn(monkeypatch, url)
    assert proc is not None
    assert seen["cmd"][-4:] == ["-f", "mpegts", "-listen", "1", url][-4:]
    assert seen["cmd"][-1] == url
    assert "-listen" in seen["cmd"]
    assert seen["stdout"] == asyncio.subprocess.DEVNULL, (
        "the listen serve must keep stdout discarded; writing media to our own "
        "stdout there would corrupt whatever the parent reads"
    )


def test_both_modes_still_copy_from_the_mux_pipe(monkeypatch):
    for url in ("-", "http://127.0.0.1:1/x.ts"):
        _proc, seen = _spawn(monkeypatch, url)
        cmd = seen["cmd"]
        assert cmd[:2] == ["ffmpeg", "-y"]
        assert "-i" in cmd and cmd[cmd.index("-i") + 1] == "pipe:0"
        assert "-c" in cmd and cmd[cmd.index("-c") + 1] == "copy"
        assert seen["stdin"] == 7


def test_no_serve_url_still_returns_none(monkeypatch):
    proc, seen = _spawn(monkeypatch, "")
    assert proc is None and "cmd" not in seen


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
    bytes out.  The unit tests above passed throughout, because they call
    _spawn_dtls_serve_ffmpeg directly and never ask who calls it.
    """
    assert _which_loop(None, "-") == "serve"


def test_http_and_bare_keepalive_routing_are_unchanged():
    assert _which_loop(None, "http://127.0.0.1:18981/cam.ts") == "serve"
    assert _which_loop(None, None) == "jpeg"
    assert _which_loop(None, "http://127.0.0.1:18981/cam.ts", sdes=True) == "sdes"


def test_rtsp_push_mode_publishes_over_tcp(monkeypatch):
    """DTLS gained the RTSP push destination SDES always had.

    Nothing about the media differs by transport at this point - the mux has
    already produced MPEG-TS - so the only reason DTLS lacked a push was that
    no destination was wired.  TCP interleave because a UDP publish fragments
    a 720p keyframe and the first loss takes the GOP with it.
    """
    url = "rtsp://127.0.0.1:8554/driveway"
    proc, seen = _spawn(monkeypatch, url)
    assert proc is not None
    cmd = seen["cmd"]
    assert cmd[-4:] == ["-f", "rtsp", "-rtsp_transport", "tcp", url][-4:]
    assert cmd[-1] == url
    assert "-listen" not in cmd
    # Media must not reach our own stdout here: the push owns the socket, and a
    # duplicate copy on fd 1 would corrupt whatever a parent reads.
    assert seen["stdout"] == asyncio.subprocess.DEVNULL
    # Video copies; audio MUST NOT.  The mux writes AAC into MPEG-TS as ADTS,
    # and ffmpeg's RTSP muxer rejects AAC with no global headers at header
    # write - which kills the whole publish, video included.  Reproduced with
    # the exact argv: "AAC with no global headers is currently not supported".
    assert cmd[cmd.index("-c:v") + 1] == "copy"
    assert cmd[cmd.index("-c:a") + 1] == "pcm_alaw"
    assert "-c" not in cmd, "a blanket -c copy here re-introduces the AAC refusal"


def test_the_other_two_destinations_still_copy_everything(monkeypatch):
    for url in ("-", "http://127.0.0.1:1/x.ts"):
        _proc, seen = _spawn(monkeypatch, url)
        cmd = seen["cmd"]
        assert "-c" in cmd and cmd[cmd.index("-c") + 1] == "copy"
        assert "-c:a" not in cmd, (
            "only the RTSP push transcodes audio; MPEG-TS carries the mux's "
            "AAC as-is and re-encoding it here would burn CPU for nothing"
        )


def test_rtsp_url_routes_to_the_serve_loop():
    assert _which_loop(None, "rtsp://127.0.0.1:8554/driveway") == "serve"
