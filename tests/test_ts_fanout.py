"""TsRouter + TsChannel: one muxed MPEG-TS per camera, any number of consumers.

Home Assistant's stream worker reads a camera's HLS source from here instead of
from go2rtc's RTSP, because go2rtc re-bases each track for each consumer and a
mid-stream join comes out with audio 0.1-0.75 s late. A library-muxed TS keeps
one clock for both tracks, so whoever joins gets them in step - provided the
server hands every consumer a decodable start (tables, then a keyframe) and no
consumer can hold up the muxer or another consumer.
"""

import socket
import time

import pytest

from aidot_cameras.camera.ts_fanout import TsRouter

VIDEO, AUDIO, PMT = 0x0100, 0x0101, 0x1000


def _ts(pid, *, pusi=0, tag=b"\x00"):
    b = bytearray(188)
    b[0] = 0x47
    b[1] = ((pusi and 0x40) or 0) | ((pid >> 8) & 0x1F)
    b[2] = pid & 0xFF
    b[3] = 0x10
    b[4 : 4 + len(tag)] = tag
    return bytes(b)


def _pat(pmt_pid=PMT):
    sec = bytearray(13)
    sec[0], sec[1], sec[2] = 0x00, 0xB0, 0x0D
    sec[9] = 0x01
    sec[10] = 0xE0 | ((pmt_pid >> 8) & 0x1F)
    sec[11] = pmt_pid & 0xFF
    return _ts(0, pusi=1, tag=b"\x00" + bytes(sec))


PATP, PMTP = _pat(), _ts(PMT, pusi=1, tag=b"PMT")


def _connect(srv, path="/cam.ts", auth=None):
    """A consumer's request for ``path``, with the listener's secret unless given."""
    target = "%s?auth=%s" % (path, srv.token if auth is None else auth)
    s = socket.create_connection(("127.0.0.1", srv.port), timeout=5)
    s.sendall(b"GET %s HTTP/1.1\r\nHost: x\r\n\r\n" % target.encode())
    return s


def _wait(cond, timeout=3.0):
    end = time.time() + timeout
    while time.time() < end:
        if cond():
            return True
        time.sleep(0.01)
    return False


def _read_body(s, want, timeout=3.0):
    """Read until `want` media bytes (after the HTTP header) or timeout."""
    s.settimeout(0.2)
    buf = b""
    end = time.time() + timeout
    while time.time() < end:
        try:
            chunk = s.recv(65536)
        except TimeoutError:
            chunk = b""
        if chunk:
            buf += chunk
        body = buf.split(b"\r\n\r\n", 1)[1] if b"\r\n\r\n" in buf else b""
        if len(body) >= want:
            return body
    return buf.split(b"\r\n\r\n", 1)[1] if b"\r\n\r\n" in buf else b""


class _Srv:
    """A router with one camera channel, driven the way a muxer drives it."""

    def __init__(self):
        self.router = TsRouter(0)
        self.router.start()
        self.ch = self.router.channel("/cam.ts")
        self.port = self.router.port
        self.token = self.router.token

    def __getattr__(self, name):
        return getattr(self.ch, name)

    def close(self):
        self.router.close()


def _server():
    srv = _Srv()
    srv.write(PATP + PMTP)  # tables learned before anyone joins
    return srv


def _gop(n):
    """A keyframe write followed by n delta writes, as the mux would issue them."""
    return [_ts(VIDEO, pusi=1, tag=b"KEY")] + [
        _ts(VIDEO, pusi=1, tag=b"D%02d" % i) for i in range(n)
    ]


def _send_gop(srv, n=3):
    pkts = _gop(n)
    srv.mark_keyframe()
    srv.write(pkts[0])
    for p in pkts[1:]:
        srv.write(p)
    return pkts


def test_two_consumers_each_start_with_tables_then_a_keyframe():
    srv = _server()
    try:
        a, b = _connect(srv), _connect(srv)
        assert _wait(lambda: srv.consumer_count() == 2)
        srv.write(_ts(VIDEO, pusi=1, tag=b"MID"))  # mid-GOP: nobody may start here
        pkts = _send_gop(srv, 3)
        want = 2 * 188 + len(pkts) * 188
        for s in (a, b):
            body = _read_body(s, want)
            assert body[:188] == PATP and body[188:376] == PMTP
            assert body[376:564] == pkts[0]  # the keyframe, not the mid-GOP packet
            assert b"MID" not in body
        a.close(), b.close()
    finally:
        srv.close()


def test_a_late_joiner_waits_for_the_next_keyframe():
    srv = _server()
    try:
        a = _connect(srv)
        assert _wait(lambda: srv.consumer_count() == 1)
        _send_gop(srv, 2)
        b = _connect(srv)
        assert _wait(lambda: srv.consumer_count() == 2)
        srv.write(_ts(VIDEO, pusi=1, tag=b"TAIL"))  # still the first GOP
        second = _send_gop(srv, 1)
        body = _read_body(b, 4 * 188)
        assert body[376:564] == second[0]
        assert b"TAIL" not in body
        a.close(), b.close()
    finally:
        srv.close()


def test_a_consumer_that_stops_reading_holds_up_nobody():
    srv = _server()
    try:
        stuck = _connect(srv)  # connects, then never reads
        ok = _connect(srv)
        assert _wait(lambda: srv.consumer_count() == 2)
        t0 = time.time()
        for _ in range(400):  # ~20 MB through the server
            _send_gop(srv, 99)
        assert time.time() - t0 < 10.0  # write() never blocked on the stuck one
        # The healthy consumer kept receiving throughout.
        assert len(_read_body(ok, 2_000_000, timeout=5)) >= 2_000_000
        stuck.close(), ok.close()
    finally:
        srv.close()


def test_a_disconnect_is_noticed_and_counted():
    srv = _server()
    try:
        a = _connect(srv)
        assert _wait(lambda: srv.consumer_count() == 1)
        _send_gop(srv, 1)
        a.close()
        for _ in range(50):  # writes after the peer left surface the error
            _send_gop(srv, 1)
            if srv.consumer_count() == 0:
                break
            time.sleep(0.02)
        assert srv.consumer_count() == 0
    finally:
        srv.close()


def test_writes_with_no_consumer_are_cheap_and_dropped():
    srv = _server()
    try:
        for _ in range(100):
            _send_gop(srv, 10)
        assert srv.consumer_count() == 0
        assert srv.pending_bytes() == 0
    finally:
        srv.close()


def test_close_is_idempotent_and_disconnects_everyone():
    srv = _server()
    a = _connect(srv)
    assert _wait(lambda: srv.consumer_count() == 1)
    srv.close()
    srv.close()
    assert srv.consumer_count() == 0
    # The consumer sees the connection end: after the HTTP header, EOF.
    a.settimeout(2)
    got_eof = False
    end = time.time() + 3
    while time.time() < end:
        try:
            chunk = a.recv(65536)
        except (TimeoutError, ConnectionResetError):
            break
        if chunk == b"":
            got_eof = True
            break
    assert got_eof


def test_the_keyframe_signal_survives_a_write_with_no_whole_packet():
    srv = _server()
    try:
        a = _connect(srv)
        assert _wait(lambda: srv.consumer_count() == 1)
        key = _ts(VIDEO, pusi=1, tag=b"KEY")
        srv.mark_keyframe()
        srv.write(key[:100])  # the muxer's buffer can split a packet
        srv.write(key[100:])
        body = _read_body(a, 3 * 188)
        assert body[376:564] == key
        a.close()
    finally:
        srv.close()


def test_cameras_share_one_listener_and_each_gets_only_its_own_media():
    router = TsRouter(0)
    router.start()
    try:
        a_ch, b_ch = router.channel("/a.ts"), router.channel("/b.ts")
        srv = router
        a, b = _connect(srv, "/a.ts"), _connect(srv, "/b.ts")
        assert _wait(lambda: a_ch.consumer_count() == 1 and b_ch.consumer_count() == 1)
        for ch, tag in ((a_ch, b"AAA"), (b_ch, b"BBB")):
            ch.write(PATP + PMTP)
            ch.mark_keyframe()
            ch.write(_ts(VIDEO, pusi=1, tag=tag))
        body_a, body_b = _read_body(a, 3 * 188), _read_body(b, 3 * 188)
        assert b"AAA" in body_a and b"BBB" not in body_a
        assert b"BBB" in body_b and b"AAA" not in body_b
        assert router.url("/a.ts") == "http://127.0.0.1:%d/a.ts?auth=%s" % (
            router.port,
            router.token,
        )
        a.close(), b.close()
    finally:
        router.close()


def test_an_unknown_path_gets_404_and_never_another_cameras_media():
    router = TsRouter(0)
    router.start()
    try:
        router.channel("/a.ts")
        srv = router
        s = _connect(srv, "/nope.ts")
        s.settimeout(3)
        assert s.recv(200).startswith(b"HTTP/1.0 404")
        s.close()
    finally:
        router.close()


@pytest.mark.parametrize(
    "auth",
    ["", "wrong", "first-char"],
)
def test_a_request_without_the_listeners_secret_gets_404(auth):
    # The listener is loopback-only, but anything else on the host (an add-on
    # sharing the host network) could otherwise read a camera's video from its
    # port and its well-known stream name.
    router = TsRouter(0)
    router.start()
    try:
        ch = router.channel("/a.ts")
        if auth == "first-char":  # all but one character right
            auth = ("A" if router.token[0] != "A" else "B") + router.token[1:]
        s = _connect(router, "/a.ts", auth=auth)
        s.settimeout(3)
        assert s.recv(200).startswith(b"HTTP/1.0 404")
        s.close()
        bare = socket.create_connection(("127.0.0.1", router.port), timeout=5)
        bare.sendall(b"GET /a.ts HTTP/1.1\r\n\r\n")  # no query at all
        bare.settimeout(3)
        assert bare.recv(200).startswith(b"HTTP/1.0 404")
        bare.close()
        assert ch.consumer_count() == 0
    finally:
        router.close()


def test_a_consumer_that_leaves_while_nothing_is_written_is_noticed():
    # A dead consumer used to be found only by a failed send: with no writes it
    # stayed counted - a "viewer" that pinned the camera awake - with its thread.
    srv = _server()
    try:
        a, b = _connect(srv), _connect(srv)
        assert _wait(lambda: srv.consumer_count() == 2)
        a.close()
        b.close()
        assert _wait(lambda: srv.consumer_count() == 0, timeout=3.0)
    finally:
        srv.close()


def test_the_listener_survives_an_accept_error():
    # EMFILE or ECONNABORTED used to end the accept loop for good: no camera
    # could be joined again until a restart.
    router = TsRouter(0)
    router.start()
    try:
        router.channel("/cam.ts")
        real = router._listen
        failed = []

        class _Flaky:
            def accept(self):
                if not failed:
                    failed.append(1)
                    raise OSError(24, "Too many open files")
                return real.accept()

            def close(self):
                real.close()

        router._listen = _Flaky()
        time.sleep(0.7)  # let the loop hit the error
        srv = router
        s = _connect(srv, "/cam.ts")
        s.settimeout(3)
        assert s.recv(64).startswith(b"HTTP/1.0 200")
        s.close()
    finally:
        router.close()


def test_only_consumers_that_were_sent_media_count_as_started():
    # A consumer still waiting for its first keyframe has seen no timestamps;
    # the tee may start its timeline over under it, but not under one that has.
    srv = _server()
    try:
        a = _connect(srv)
        assert _wait(lambda: srv.consumer_count() == 1)
        srv.write(_ts(VIDEO, pusi=1, tag=b"MID"))
        assert srv.started_count() == 0
        _send_gop(srv, 1)
        assert srv.started_count() == 1
        a.close()
        assert _wait(lambda: srv.started_count() == 0)
    finally:
        srv.close()
