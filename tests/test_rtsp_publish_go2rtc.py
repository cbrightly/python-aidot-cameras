"""Direct publish against a REAL go2rtc binary (the version HA pins: 1.9.14).

Skipped unless a go2rtc binary is available: ``AIDOT_GO2RTC_BIN`` or
``go2rtc`` on PATH. The stream is defined the way the HA integration defines
it - with a source that fails fast - because go2rtc refuses a publish into a
stream that does not exist and cannot create an empty one.

Each test publishes real H.264 (encoded here with PyAV) plus PCMA, then reads
it back from go2rtc over RTSP and decodes it - so codecs, channel mapping,
packetization and timestamps are all checked by go2rtc and FFmpeg, not by our
own parser.
"""

from __future__ import annotations

import os
import queue
import shutil
import socket
import subprocess
import threading
import time
import urllib.request

import pytest

av = pytest.importorskip("av")

from aidot_cameras.camera import rtsp_publish as rp  # noqa: E402

GO2RTC = os.environ.get("AIDOT_GO2RTC_BIN") or shutil.which("go2rtc")
pytestmark = pytest.mark.skipif(not GO2RTC, reason="no go2rtc binary")


def _free_port():
    s = socket.socket()
    s.bind(("127.0.0.1", 0))
    p = s.getsockname()[1]
    s.close()
    return p


def _cfg(api, rtsp, extra_src=None):
    src = f"  aidot_cam:\n    - rtsp://127.0.0.1:{_free_port()}/placeholder\n"
    if extra_src:
        src += f'    - "{extra_src}"\n'
    return (
        f'api:\n  listen: "127.0.0.1:{api}"\n'
        f'rtsp:\n  listen: "127.0.0.1:{rtsp}"\n'
        'webrtc:\n  listen: ""\n'
        "exec:\n  allow_paths:\n    - ffmpeg\n"
        "log:\n  level: debug\n"
        "streams:\n" + src
    )


@pytest.fixture
def go2rtc(tmp_path):
    api, rtsp = _free_port(), _free_port()
    cfg = tmp_path / "go2rtc.yaml"
    cfg.write_text(
        f'api:\n  listen: "127.0.0.1:{api}"\n'
        f'rtsp:\n  listen: "127.0.0.1:{rtsp}"\n'
        'webrtc:\n  listen: ""\n'
        "log:\n  level: debug\n"
        "streams:\n"
        f"  aidot_cam: rtsp://127.0.0.1:{_free_port()}/placeholder\n"
    )
    log = open(tmp_path / "go2rtc.log", "wb")
    proc = subprocess.Popen([GO2RTC, "-config", str(cfg)], stdout=log, stderr=log)
    deadline = time.monotonic() + 10
    while time.monotonic() < deadline:
        try:
            urllib.request.urlopen(f"http://127.0.0.1:{api}/api", timeout=1)
            break
        except Exception:
            time.sleep(0.1)
    else:
        proc.kill()
        pytest.fail("go2rtc did not start")
    yield {"api": api, "rtsp": rtsp, "log": tmp_path / "go2rtc.log"}
    proc.terminate()
    proc.wait(5)
    log.close()


def _encode_h264_aus(n=60, fps=15):
    """``n`` Annex-B access units of a moving test pattern, GOP 15."""
    ctx = av.CodecContext.create("libx264", "w")
    ctx.width, ctx.height = 320, 240
    ctx.pix_fmt = "yuv420p"
    from fractions import Fraction

    ctx.time_base = Fraction(1, fps)
    ctx.framerate = Fraction(fps, 1)
    ctx.options = {"tune": "zerolatency", "profile": "baseline", "g": "15", "bf": "0"}
    import numpy as np

    aus = []
    for i in range(n):
        img = np.zeros((240, 320, 3), dtype=np.uint8)
        img[:, (i * 5) % 320 :] = (i * 4) % 255
        frame = av.VideoFrame.from_ndarray(img, format="rgb24").reformat(
            format="yuv420p"
        )
        frame.pts = i
        for pkt in ctx.encode(frame):
            aus.append((bytes(pkt), pkt.is_keyframe))
    for pkt in ctx.encode(None):
        aus.append((bytes(pkt), pkt.is_keyframe))
    return aus


def _wait_for_publisher(api_port, name="aidot_cam", timeout=10.0):
    """Wait until go2rtc lists a pushed producer (one with no dial ``url``) -
    the same check the HA integration's ``_await_publisher_attached`` makes
    before handing a viewer the stream."""
    import json

    deadline = time.monotonic() + timeout
    while time.monotonic() < deadline:
        try:
            with urllib.request.urlopen(
                f"http://127.0.0.1:{api_port}/api/streams?src={name}", timeout=1
            ) as r:
                info = json.load(r)
            if any(not p.get("url") for p in info.get("producers") or []):
                return True
        except Exception:
            pass
        time.sleep(0.1)
    return False


def _read_back(url, want_frames=20, timeout=20.0):
    """Decode video from go2rtc; return (codec names, decoded frame count)."""
    result = {}

    def run():
        try:
            c = av.open(url, options={"rtsp_transport": "tcp", "timeout": "10000000"})
            result["codecs"] = sorted(s.codec_context.name for s in c.streams)
            n = 0
            for f in c.decode(video=0):
                n += 1
                if n >= want_frames:
                    break
            result["frames"] = n
            c.close()
        except Exception as exc:  # surfaced below
            result["error"] = repr(exc)

    t = threading.Thread(target=run, daemon=True)
    t.start()
    t.join(timeout)
    return result


def test_dtls_runner_through_real_go2rtc(go2rtc):
    aus = _encode_h264_aus()
    vq, aq = queue.Queue(), queue.Queue()
    progress, stop, res = [0.0], threading.Event(), {}
    url = f"rtsp://127.0.0.1:{go2rtc['rtsp']}/aidot_cam"
    t = threading.Thread(
        target=rp.dtls_rtp_publish_run,
        args=(vq, aq, url, progress, stop),
        kwargs={"result": res, "device_id": "it"},
        daemon=True,
    )
    t.start()

    def feed():
        k = 0
        while not stop.is_set():
            data, kf = aus[k % len(aus)]
            ts = k * 6000
            vq.put((data, ts, kf))
            aq.put((b"\xd5" * 160, k * 533))
            k += 1
            time.sleep(1 / 15)

    threading.Thread(target=feed, daemon=True).start()
    try:
        assert _wait_for_publisher(go2rtc["api"])
        out = _read_back(url)
    finally:
        stop.set()
        t.join(5)
    assert "error" not in out, (out, go2rtc["log"].read_text()[-3000:])
    assert out["codecs"] == ["h264", "pcm_alaw"]
    assert out["frames"] >= 20
    assert "error" not in res


def test_loopback_publisher_through_real_go2rtc(go2rtc):
    """The SDES shape: RTP over loopback UDP -> Popen-compatible publisher."""
    aus = _encode_h264_aus()
    a_port, v_port = _free_port(), _free_port()
    serve_sdp = (
        "v=0\r\no=- 1 1 IN IP4 0.0.0.0\r\ns=aidot-tutk-rx\r\nt=0 0\r\n"
        f"m=audio {a_port} RTP/AVP 8\r\nc=IN IP4 127.0.0.1\r\n"
        "a=rtpmap:8 PCMA/8000\r\na=rtcp-mux\r\n"
        f"m=video {v_port} RTP/AVP 96\r\nc=IN IP4 127.0.0.1\r\n"
        "a=rtpmap:96 H264/90000\r\n"
        "a=fmtp:96 level-asymmetry-allowed=1;packetization-mode=1;"
        "profile-level-id=42e01f\r\na=rtcp-mux\r\n"
    )
    url = f"rtsp://127.0.0.1:{go2rtc['rtsp']}/aidot_cam"
    proc = rp.LoopbackRtpPublisher(serve_sdp, url, device_id="it", input_timeout_s=10)
    stop = threading.Event()

    def feed():
        tx = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        seq = 0
        k = 0
        # A camera-style clock, including an A001513-style ~1.7 s backward
        # step every 8 frames (the camera does it every ~30 s; compressed
        # here), which the timeline must absorb.
        cam_ts = 1_000_000
        while not stop.is_set():
            data, _kf = aus[k % len(aus)]
            cam_ts += 6000
            if k and k % 8 == 0:
                cam_ts -= 153_000
            for payload, marker in rp.packetize_h264(data):
                seq += 1
                tx.sendto(
                    rp.build_rtp(96, marker, seq, cam_ts, 0x1111, payload),
                    ("127.0.0.1", v_port),
                )
            tx.sendto(
                rp.build_rtp(8, False, k, k * 533, 0x2222, b"\xd5" * 160),
                ("127.0.0.1", a_port),
            )
            k += 1
            time.sleep(1 / 15)
        tx.close()

    threading.Thread(target=feed, daemon=True).start()
    try:
        assert _wait_for_publisher(go2rtc["api"])
        out = _read_back(url)
    finally:
        stop.set()
        proc.terminate()
        rc = proc.wait(5)
    assert "error" not in out, (out, go2rtc["log"].read_text()[-3000:])
    assert out["codecs"] == ["h264", "pcm_alaw"]
    assert out["frames"] >= 20
    assert rc == rp.EXIT_TERMINATED
    assert proc.publish_stats()["timestamp_repairs"] >= 1


def test_publish_into_a_missing_stream_fails_fast(go2rtc):
    a_port, v_port = _free_port(), _free_port()
    serve_sdp = (
        f"v=0\r\nm=video {v_port} RTP/AVP 96\r\na=rtpmap:96 H264/90000\r\n"
        f"m=audio {a_port} RTP/AVP 8\r\n"
    )
    url = f"rtsp://127.0.0.1:{go2rtc['rtsp']}/no_such_stream"
    proc = rp.LoopbackRtpPublisher(serve_sdp, url, device_id="it")
    assert proc.wait(8) == rp.EXIT_FAILED


@pytest.mark.skipif(shutil.which("ffmpeg") is None, reason="no ffmpeg binary")
def test_an_aac_only_consumer_is_served_from_a_pcma_publish(tmp_path):
    """Home Assistant's HLS player takes AAC only, and a direct publish sends
    PCMA. A transcoding source listed after the live one covers exactly that
    consumer: go2rtc starts it on demand and the publisher stays untouched."""
    api, rtsp = _free_port(), _free_port()
    cfg = tmp_path / "go2rtc.yaml"
    cfg.write_text(_cfg(api, rtsp, "ffmpeg:aidot_cam#audio=aac"))
    log = open(tmp_path / "go2rtc.log", "wb")
    proc = subprocess.Popen([GO2RTC, "-config", str(cfg)], stdout=log, stderr=log)
    deadline = time.monotonic() + 10
    while time.monotonic() < deadline:
        try:
            urllib.request.urlopen(f"http://127.0.0.1:{api}/api", timeout=1)
            break
        except Exception:
            time.sleep(0.1)
    aus = _encode_h264_aus()
    vq, aq = queue.Queue(), queue.Queue()
    stop = threading.Event()
    url = f"rtsp://127.0.0.1:{rtsp}/aidot_cam"
    t = threading.Thread(
        target=rp.dtls_rtp_publish_run,
        args=(vq, aq, url, [0.0], stop),
        kwargs={"device_id": "it"},
        daemon=True,
    )
    t.start()

    def feed():
        k = 0
        while not stop.is_set():
            data, kf = aus[k % len(aus)]
            vq.put((data, k * 6000, kf))
            aq.put((b"\xd5" * 160, k * 533))
            k += 1
            time.sleep(1 / 15)

    threading.Thread(target=feed, daemon=True).start()
    try:
        assert _wait_for_publisher(api)
        aac = _read_back(url + "?video&audio=aac", want_frames=5)
        pcma = _read_back(url + "?video&audio=pcma", want_frames=5)
    finally:
        stop.set()
        t.join(5)
        proc.terminate()
        proc.wait(5)
        log.close()
    tail = (tmp_path / "go2rtc.log").read_text()[-3000:]
    assert aac.get("codecs") == ["aac", "h264"], (aac, tail)
    assert aac["frames"] >= 5, (aac, tail)
    # The publisher's own audio still reaches a consumer that takes it.
    assert pcma.get("codecs") == ["h264", "pcm_alaw"], (pcma, tail)


def _encode_h265_aus(n=40, fps=15):
    """``n`` Annex-B H.265 access units (VPS/SPS/PPS in-band on keyframes)."""
    from fractions import Fraction

    import numpy as np

    ctx = av.CodecContext.create("libx265", "w")
    ctx.width, ctx.height = 320, 240
    ctx.pix_fmt = "yuv420p"
    ctx.time_base = Fraction(1, fps)
    ctx.framerate = Fraction(fps, 1)
    ctx.options = {"x265-params": "keyint=15:min-keyint=15:bframes=0:repeat-headers=1"}
    aus = []
    for i in range(n):
        img = np.zeros((240, 320, 3), dtype=np.uint8)
        img[:, (i * 5) % 320 :] = (i * 4) % 255
        frame = av.VideoFrame.from_ndarray(img, format="rgb24").reformat(
            format="yuv420p"
        )
        frame.pts = i
        for pkt in ctx.encode(frame):
            aus.append(bytes(pkt))
    for pkt in ctx.encode(None):
        aus.append(bytes(pkt))
    return aus


def _packetize_h265(au, mtu=1200):
    """RFC 7798 payloads for one access unit: single NAL, or FU when too big."""
    out = []
    for nal in rp.split_annexb(au):
        if len(nal) <= mtu:
            out.append((nal, False))
            continue
        typ = (nal[0] >> 1) & 0x3F
        layer_tid = ((nal[0] & 0x01) << 8) | nal[1]
        payload_hdr = bytes(((49 << 1) | (layer_tid >> 8), layer_tid & 0xFF))
        body = nal[2:]
        step = mtu - 3
        for off in range(0, len(body), step):
            chunk = body[off : off + step]
            start, end = off == 0, off + step >= len(body)
            fu = (0x80 if start else 0) | (0x40 if end else 0) | typ
            out.append((payload_hdr + bytes((fu,)) + chunk, False))
    if out:
        out[-1] = (out[-1][0], True)
    return out


def test_h265_publish_through_real_go2rtc(go2rtc):
    """The A001064 answers H.265 in some sessions and this package's SDP names
    no sprop-vps/sps/pps for it, so go2rtc drops that fmtp - the parameter sets
    have to arrive in-band. Publish real H.265 the way the SDES path would and
    read it back decoded."""
    aus = _encode_h265_aus()
    a_port, v_port = _free_port(), _free_port()
    # What the SDES open narrows to when the camera answers H.265 (pt 97).
    serve_sdp = (
        "v=0\r\no=- 1 1 IN IP4 0.0.0.0\r\ns=aidot-tutk-rx\r\nt=0 0\r\n"
        f"m=audio {a_port} RTP/AVP 8\r\nc=IN IP4 127.0.0.1\r\n"
        "a=rtpmap:8 PCMA/8000\r\na=rtcp-mux\r\n"
        f"m=video {v_port} RTP/AVP 97\r\nc=IN IP4 127.0.0.1\r\n"
        "a=rtpmap:97 H265/90000\r\na=fmtp:97 level-id=93\r\na=rtcp-mux\r\n"
    )
    url = f"rtsp://127.0.0.1:{go2rtc['rtsp']}/aidot_cam"
    proc = rp.LoopbackRtpPublisher(serve_sdp, url, device_id="it", input_timeout_s=20)
    stop = threading.Event()

    def feed():
        tx = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        seq = ts = k = 0
        while not stop.is_set():
            for payload, marker in _packetize_h265(aus[k % len(aus)]):
                seq += 1
                tx.sendto(
                    rp.build_rtp(97, marker, seq, ts, 0x3333, payload),
                    ("127.0.0.1", v_port),
                )
            ts += 6000
            k += 1
            time.sleep(1 / 15)
        tx.close()

    threading.Thread(target=feed, daemon=True).start()
    try:
        assert _wait_for_publisher(go2rtc["api"])
        out = _read_back(url, want_frames=15)
    finally:
        stop.set()
        proc.terminate()
        proc.wait(5)
    assert "error" not in out, (out, go2rtc["log"].read_text()[-2500:])
    # The audio track is announced from the same serve SDP, so it is listed
    # even though this test publishes no audio.
    assert out["codecs"] == ["hevc", "pcm_alaw"], (
        out,
        go2rtc["log"].read_text()[-2500:],
    )
    assert out["frames"] >= 15, out
