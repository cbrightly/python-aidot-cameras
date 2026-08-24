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

VALIDATED 2026-08-21 and now ON BY DEFAULT. Four cameras, ~20 minutes each,
about 64,500 packets: **0 negative DTS**, and **0 Home Assistant "Timestamp
discontinuity" errors** in the window, against a baseline of ~0.91/min on
a member of the reference fleet alone. Audio intact at aac/48000 - no G.711 downgrade. Frame rate
nominal (median step 6030 ticks = 14.9 fps).

Sized to 20 minutes on purpose: the defect is bursty and absent from roughly
half of two-minute windows, which produced several false "clean" readings
earlier in this investigation.

Three live failures before this worked were all defects in the wiring, not in
this server or in go2rtc: a `proc.returncode` dereference when there is no
ffmpeg process, binding an ephemeral port instead of the advertised one when no
relay holds it, and the missing join-awareness below. The server itself
describes cleanly against real go2rtc (`rc: 0, h264,video`).
"""
import os
import socket
import sys
import threading
import time

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.protocol import _DirectTsServer, _direct_serve_enabled


def _ts(pid, *, pusi=0, rai=0, payload=b"\x00"):
    """Build one 188-byte TS packet for the tests below."""
    b = bytearray(188)
    b[0] = 0x47
    b[1] = ((pusi and 0x40) or 0) | ((pid >> 8) & 0x1F)
    b[2] = pid & 0xFF
    if rai:
        b[3] = 0x30                      # adaptation field + payload
        b[4] = 1                         # AF length
        b[5] = 0x40                      # random_access_indicator
        b[6:6+len(payload)] = payload
    else:
        b[3] = 0x10                      # payload only
        b[4:4+len(payload)] = payload
    return bytes(b)


def _pat(pmt_pid=0x1000):
    """Minimal PAT naming one program whose PMT lives on pmt_pid."""
    sec = bytearray(13)
    sec[0] = 0x00                                    # table_id
    sec[1] = 0xB0; sec[2] = 0x0D                     # section length
    sec[8] = 0x00; sec[9] = 0x01                     # program_number 1
    sec[10] = 0xE0 | ((pmt_pid >> 8) & 0x1F); sec[11] = pmt_pid & 0xFF
    return _ts(0, pusi=1, payload=b"\x00" + bytes(sec))


def _wait_consumer(srv, timeout=3.0):
    """Block until the accept thread has registered the client.

    `_get` returns as soon as the HTTP response arrives, but the server sends
    that response BEFORE registering the socket - deliberately, so a media write
    can never interleave into the header. Writes in that window are dropped by
    design, which is correct in production (the consumer simply waits for the
    next keyframe) but makes a test that writes immediately after `_get` racy.
    glibc happened to win the race; musl did not.
    """
    end = time.time() + timeout
    while time.time() < end:
        if srv.has_consumer():
            return True
        time.sleep(0.01)
    return False


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
    """Media only flows once a random-access point arrives - see the
    join-awareness tests below for why. The keyframe here is what releases it."""
    srv = _DirectTsServer(0)
    try:
        srv.start()
        done = threading.Event()

        def feed():
            for _ in range(40):
                if srv.has_consumer():
                    break
                time.sleep(0.05)
            srv.write(_ts(0x100, pusi=1, rai=1))   # keyframe: releases the join
            for _ in range(10):
                srv.write(_ts(0x100, pusi=1))
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


def test_it_is_on_by_default(monkeypatch):
    monkeypatch.delenv("AIDOT_DTLS_DIRECT_SERVE", raising=False)
    assert _direct_serve_enabled() is True


def test_it_can_be_turned_off(monkeypatch):
    """An operator escape hatch, and the fallback the serve loop takes anyway
    when the port cannot be bound."""
    monkeypatch.setenv("AIDOT_DTLS_DIRECT_SERVE", "0")
    assert _direct_serve_enabled() is False


# --- join-awareness: what the ffmpeg hop was actually contributing ---------- #

def _drain(sock, n=8, timeout=3.0):
    sock.settimeout(timeout)
    buf = b""
    end = time.time() + timeout
    while time.time() < end and len(buf) < n * 188:
        try:
            c = sock.recv(65536)
        except OSError:
            break
        if not c:
            break
        buf += c
    return buf


def _packets(body):
    return [body[i:i+188] for i in range(0, len(body) - 187, 188)]


def test_a_late_consumer_is_not_handed_mid_gop_bytes():
    """The live failure. go2rtc joined mid-stream, never saw SPS/PPS, and
    answered DESCRIBE with 404. Reproduced offline: a consumer joining
    mid-stream got 'non-existing PPS 0 referenced' and no track, while the same
    server with the consumer present from byte 0 described cleanly."""
    srv = _DirectTsServer(0)
    try:
        srv.start()
        for _ in range(20):                       # stream already running
            srv.write(_ts(0x100, pusi=1))
        sock, head = _get(srv.port, read_bytes=1)
        assert _wait_consumer(srv)
        body = head.partition(b"\r\n\r\n")[2]
        for _ in range(20):                       # more non-keyframe media
            srv.write(_ts(0x100, pusi=1))
        body += _drain(sock, n=2, timeout=0.6)
        sock.close()
        assert body == b"", "media sent before a random-access point"
    finally:
        srv.close()


def test_a_late_consumer_gets_tables_then_a_keyframe():
    srv = _DirectTsServer(0)
    try:
        srv.start()
        srv.write(_pat())
        srv.write(_ts(0x1000, pusi=1))            # PMT
        for _ in range(10):
            srv.write(_ts(0x100, pusi=1))         # mid-GOP media
        sock, head = _get(srv.port, read_bytes=1)
        assert _wait_consumer(srv)
        body = head.partition(b"\r\n\r\n")[2]
        srv.write(_ts(0x100, pusi=1, rai=1))      # the next keyframe
        srv.write(_ts(0x100, pusi=1))
        body += _drain(sock, n=4)
        sock.close()
        pkts = _packets(body)
        assert len(pkts) >= 3, f"only {len(pkts)} packets"
        pids = [((p[1] & 0x1F) << 8) | p[2] for p in pkts]
        assert pids[0] == 0, f"first packet must be the PAT, got pid {pids[0]}"
        assert pids[1] == 0x1000, f"second must be the PMT, got pid {pids[1]}"
        assert (pkts[2][3] & 0x20) and (pkts[2][5] & 0x40), "media must start at a keyframe"
    finally:
        srv.close()


def test_once_synced_it_keeps_forwarding():
    srv = _DirectTsServer(0)
    try:
        srv.start()
        srv.write(_pat()); srv.write(_ts(0x1000, pusi=1))
        sock, head = _get(srv.port, read_bytes=1)
        assert _wait_consumer(srv)
        srv.write(_ts(0x100, pusi=1, rai=1))
        for _ in range(6):
            srv.write(_ts(0x100, pusi=1))
        body = head.partition(b"\r\n\r\n")[2] + _drain(sock, n=9)
        sock.close()
        assert len(_packets(body)) >= 8
    finally:
        srv.close()


def test_a_reconnecting_consumer_resyncs():
    """go2rtc reconnects. The second consumer must also start at a keyframe,
    not inherit the first one's position."""
    srv = _DirectTsServer(0)
    try:
        srv.start()
        srv.write(_pat()); srv.write(_ts(0x1000, pusi=1))
        s1, _ = _get(srv.port, read_bytes=1)
        assert _wait_consumer(srv)
        srv.write(_ts(0x100, pusi=1, rai=1))
        s1.close()
        time.sleep(0.3)
        for _ in range(5):
            srv.write(_ts(0x100, pusi=1))
        s2, head = _get(srv.port, read_bytes=1)
        assert _wait_consumer(srv)
        body = head.partition(b"\r\n\r\n")[2]
        body += _drain(s2, n=2, timeout=0.6)
        s2.close()
        assert body == b"", "second consumer was handed mid-GOP bytes"
    finally:
        srv.close()


# --- binding the port the consumer was actually told about ----------------- #

def test_the_serve_port_is_read_from_the_url():
    from aidot_cameras.camera.protocol import _serve_port
    assert _serve_port("http://127.0.0.1:18931/abc.ts") == 18931
    assert _serve_port("http://0.0.0.0:8099/x.ts") == 8099
    assert _serve_port("http://[::1]:1234/x.ts") == 1234


def test_a_url_without_a_port_yields_none():
    from aidot_cameras.camera.protocol import _serve_port
    assert _serve_port("http://127.0.0.1/x.ts") is None
    assert _serve_port(None) is None
    assert _serve_port("not a url") is None


def test_the_serve_loop_binds_the_advertised_port_when_there_is_no_relay():
    """The bug this fixes.

    `_DirectTsServer(_ff_port or 0)` bound port 0 - a random ephemeral port -
    whenever no relay was running, because `_ff_port` is only set on the relay
    branch. go2rtc was pointed at the advertised URL, found nothing listening,
    and answered DESCRIBE with 404. The component was fine the whole time: it
    describes cleanly (`rc: 0, h264,video`) when go2rtc is pointed at the port
    it actually bound.
    """
    import inspect

    import aidot_cameras.camera.client as cc
    src = inspect.getsource(cc.CameraMixin._dtls_serve_loop_inner)
    assert "_DirectTsServer(_ff_port or 0)" not in src, (
        "binds a random port when no relay holds the public one")
    assert "_serve_port(serve_url)" in src, (
        "the advertised port must come from the serve URL")
