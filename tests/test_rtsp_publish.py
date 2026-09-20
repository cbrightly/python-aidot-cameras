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
import re
import socket
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


def test_loopback_publisher_forwards_and_rewrites(go2rtc):
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


def test_loopback_publisher_applies_audio_gain(go2rtc):
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


def test_dtls_runner_starts_on_a_keyframe_and_publishes_both_tracks(go2rtc):
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


def test_loopback_publisher_reorders_before_publishing(go2rtc):
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
            return (float(m.group(1)), float(m.group(2)),
                    int(m.group(3)), int(m.group(4)))
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
