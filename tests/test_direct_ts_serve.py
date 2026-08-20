"""Serve the muxed MPEG-TS to go2rtc directly, without the -c copy ffmpeg hop.

WHY THIS EXISTS. Home Assistant logs "Timestamp discontinuity" and restarts the
stream. Root-caused 2026-08-20 by capturing both sides of the hop:

    this library's mux : 64,981 video PES, PTS_DTS_flags histogram {3: 64981}
                         - every one carries both PTS and DTS, none missing
    the serve ffmpeg's : 8 PES with PTS_DTS_flags == 0 in 12,729, on the wire
      own output         at 127.0.0.1:18931

go2rtc turns a timestampless PES into RTP timestamp 0, and HA's rtpdec then
computes pts = 0 - base_timestamp, i.e. the constant negative DTS. The
timestamps are correct when we hand the TS to ffmpeg and missing when ffmpeg
emits it, so the only component in the chain that loses them is the hop.

The hop also earns nothing: it is `-c copy`, re-packetizing MPEG-TS our own mux
already wrote in the form go2rtc wants. Serving those bytes straight to the
consumer removes the defect by removing the component.

Rewriting timestamps was tried twice and is NOT the fix - `+genpts` made it
about 10x worse and `-avoid_negative_ts make_zero` about 9x worse plus an
unstable stream. The bytes are right; the hop drops them.

Opt-in via AIDOT_DTLS_DIRECT_SERVE while it earns trust on real hardware; the
default path is unchanged.

STATUS 2026-08-20: the server below is correct in isolation - it binds, speaks
HTTP, streams, and survives a consumer disconnect, all asserted here - but
enabling it on the live fleet FAILED. go2rtc registered a producer and reported
no error, yet `DESCRIBE rtsp://.../aidot_<id>` answered **404 Not Found**: go2rtc
never derived a playable track from the stream. Reverted within minutes; the
default ffmpeg path was verified healthy again immediately after.

So removing the hop is not a drop-in. What ffmpeg contributes beyond `-c copy`
is not yet understood - a plausible candidate is that go2rtc probes the incoming
MPEG-TS for codec information and something in the header cadence our mux emits
(PAT/PMT interval, or the absence of ffmpeg's own stream signalling) leaves it
unable to describe a track. That is the next thing to establish, and it is a
go2rtc-side question rather than a timestamp one.

The tests below are kept because the component is sound and the diagnosis that
motivated it is measured; what is missing is the go2rtc handshake, not the
server.
"""
import os
import socket
import sys
import threading
import time

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.protocol import _DirectTsServer, _direct_serve_enabled


def _get(port, path="/cam.ts", read_bytes=None):
    s = socket.create_connection(("127.0.0.1", port), timeout=5)
    s.sendall(f"GET {path} HTTP/1.1\r\nHost: x\r\n\r\n".encode())
    buf = b""
    deadline = time.time() + 5
    while time.time() < deadline:
        chunk = s.recv(65536)
        if not chunk:
            break
        buf += chunk
        if read_bytes is not None and len(buf) >= read_bytes:
            break
    return s, buf


def test_it_binds_and_reports_its_port():
    srv = _DirectTsServer(0)
    try:
        srv.start()
        assert srv.port > 0
    finally:
        srv.close()


def test_a_consumer_gets_an_http_response_then_the_media():
    srv = _DirectTsServer(0)
    try:
        srv.start()
        payload = b"\x47" + b"\x11" * 187      # one TS packet, correct sync byte
        done = threading.Event()

        def feed():
            for _ in range(40):
                if srv.has_consumer():
                    break
                time.sleep(0.05)
            for _ in range(10):
                srv.write(payload)
            done.set()

        threading.Thread(target=feed, daemon=True).start()
        sock, buf = _get(srv.port, read_bytes=200)
        done.wait(3)
        sock.close()
        head, _, body = buf.partition(b"\r\n\r\n")
        assert head.startswith(b"HTTP/1.0 200") or head.startswith(b"HTTP/1.1 200"), head[:60]
        assert b"video/mp2t" in head.lower()
        assert body.startswith(b"\x47"), body[:8]
    finally:
        srv.close()


def test_writes_before_a_consumer_arrives_are_dropped_not_buffered():
    """A live stream must not hand a late consumer stale media.

    The mux runs whether or not anyone is pulling. Buffering those bytes would
    deliver a backlog at connect - which is exactly the pre-roll that makes HA's
    base timestamp land seconds into the stream.
    """
    srv = _DirectTsServer(0)
    try:
        srv.start()
        for _ in range(100):
            srv.write(b"\x47" + b"\x00" * 187)
        assert srv.pending_bytes() == 0
    finally:
        srv.close()


def test_a_disconnect_does_not_kill_the_writer():
    """go2rtc reconnects. A dead consumer must not take the mux thread with it."""
    srv = _DirectTsServer(0)
    try:
        srv.start()
        sock, _ = _get(srv.port, read_bytes=1)
        sock.close()
        time.sleep(0.3)
        for _ in range(50):
            srv.write(b"\x47" + b"\x22" * 187)   # must not raise
        assert srv.port > 0
    finally:
        srv.close()


def test_close_is_idempotent():
    srv = _DirectTsServer(0)
    srv.start()
    srv.close()
    srv.close()


def test_the_flag_is_off_by_default(monkeypatch):
    monkeypatch.delenv("AIDOT_DTLS_DIRECT_SERVE", raising=False)
    assert _direct_serve_enabled() is False


def test_the_flag_opts_in(monkeypatch):
    monkeypatch.setenv("AIDOT_DTLS_DIRECT_SERVE", "1")
    assert _direct_serve_enabled() is True
