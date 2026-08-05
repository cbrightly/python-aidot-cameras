"""Stub of an RTSP server that accepts a publisher, the way go2rtc does.

The serve's push mode runs ``ffmpeg ... -f rtsp -rtsp_transport tcp
rtsp://127.0.0.1:8554/<stream>``, which makes ffmpeg ANNOUNCE its SDP, SETUP
each stream interleaved over the same TCP connection, RECORD, and then write
``$``-framed RTP.  go2rtc only has a producer for that stream once RECORD lands
and only keeps one while the frames keep coming.

Nothing in the test suite spoke that half of the protocol, so "the publisher
never landed" and "the publisher landed and then stopped feeding" - the two
shapes of the ``DESCRIBE ... 404`` outage - were both invisible to CI.  This
records them: which requests arrived, and how many media bytes have been
received, so a test can assert flow is still moving rather than merely that it
once started.

Deliberately minimal: it answers the request sequence ffmpeg's rtsp muxer
issues and counts what follows.  It does not parse RTP, and it is not a media
server.
"""
import asyncio
import time


class FakeRtspSink:
    """An RTSP endpoint that accepts one publisher at a time."""

    def __init__(self) -> None:
        self.requests: list[str] = []
        #: SDP bodies received via ANNOUNCE, in order
        self.announced_sdp: list[str] = []
        #: media bytes received after RECORD (interleaved RTP, framing included)
        self.bytes_recv = 0
        #: monotonic timestamp of the most recent media byte
        self.last_media = 0.0
        #: set once RECORD is answered - go2rtc's "there is a producer" moment
        self.recording = False
        #: set when the publisher's connection ends, however it ends
        self.publisher_gone = False
        self._server: asyncio.AbstractServer | None = None
        self.port: int | None = None

    async def start(self) -> "FakeRtspSink":
        self._server = await asyncio.start_server(
            self._handle, "127.0.0.1", 0
        )
        self.port = self._server.sockets[0].getsockname()[1]
        return self

    async def stop(self) -> None:
        if self._server is not None:
            self._server.close()
            try:
                await self._server.wait_closed()
            except Exception:
                pass
            self._server = None

    @property
    def url_for(self):
        def _url(stream: str) -> str:
            return f"rtsp://127.0.0.1:{self.port}/{stream}"
        return _url

    async def __aenter__(self) -> "FakeRtspSink":
        return await self.start()

    async def __aexit__(self, *_exc) -> None:
        await self.stop()

    # -- assertion helpers -------------------------------------------------- #

    async def wait_for_recording(self, timeout: float = 30.0) -> bool:
        """True once the publisher has ANNOUNCEd and RECORDed."""
        deadline = time.monotonic() + timeout
        while time.monotonic() < deadline:
            if self.recording:
                return True
            await asyncio.sleep(0.05)
        return False

    async def wait_for_bytes(self, count: int, timeout: float = 30.0) -> bool:
        deadline = time.monotonic() + timeout
        while time.monotonic() < deadline:
            if self.bytes_recv >= count:
                return True
            await asyncio.sleep(0.05)
        return False

    async def media_flowed_during(self, window_s: float) -> int:
        """Bytes received over ``window_s`` - 0 means the publisher went quiet."""
        before = self.bytes_recv
        await asyncio.sleep(window_s)
        return self.bytes_recv - before

    async def still_flowing(self, patience_s: float = 6.0) -> bool:
        """True as soon as any new media arrives within ``patience_s``.

        Patience rather than a fixed window on purpose: a loaded CI runner can
        starve ffmpeg for a second or two, and "is this publisher alive" must
        not turn into "was the runner busy". A stalled publisher never sends
        again, so waiting longer only costs time on a genuine failure.
        """
        before = self.bytes_recv
        deadline = time.monotonic() + patience_s
        while time.monotonic() < deadline:
            await asyncio.sleep(0.25)
            if self.bytes_recv > before:
                return True
        return False

    # -- protocol ----------------------------------------------------------- #

    async def _handle(self, reader, writer) -> None:
        session = "1234abcd"
        try:
            while True:
                # One byte decides what this is.  Media is binary and contains
                # newlines, so a readline-first loop mis-frames it and starts
                # answering RTP as though it were requests (which makes ffmpeg
                # abort on a CSeq mismatch) - read the discriminator first.
                lead = await reader.read(1)
                if not lead:
                    break
                if lead == b"$":
                    # Interleaved frame: '$' <channel> <2-byte length> <data>.
                    hdr = await reader.readexactly(3)
                    size = int.from_bytes(hdr[1:3], "big")
                    if size:
                        await reader.readexactly(size)
                    self.bytes_recv += 4 + size
                    self.last_media = time.monotonic()
                    continue
                first = lead + await reader.readline()
                head = first.decode("utf-8", "replace").strip()
                headers = {}
                while True:
                    line = await reader.readline()
                    if not line or line in (b"\r\n", b"\n"):
                        break
                    name, _, value = line.decode(
                        "utf-8", "replace").partition(":")
                    headers[name.strip().lower()] = value.strip()
                body = b""
                length = int(headers.get("content-length") or 0)
                if length:
                    body = await reader.readexactly(length)

                verb = head.split(" ", 1)[0].upper()
                self.requests.append(verb)
                if verb == "ANNOUNCE":
                    self.announced_sdp.append(body.decode("utf-8", "replace"))
                cseq = headers.get("cseq", "0")
                writer.write(self._response(verb, cseq, session, headers))
                await writer.drain()
                if verb == "RECORD":
                    self.recording = True
                elif verb == "TEARDOWN":
                    break
        except (asyncio.IncompleteReadError, ConnectionResetError):
            pass
        except Exception:
            pass
        finally:
            self.publisher_gone = True
            try:
                writer.close()
            except Exception:
                pass

    def _response(self, verb: str, cseq: str, session: str, headers: dict) -> bytes:
        lines = [
            "RTSP/1.0 200 OK",
            f"CSeq: {cseq}",
            "Server: fake-rtsp-sink",
        ]
        if verb == "OPTIONS":
            lines.append(
                "Public: OPTIONS, DESCRIBE, ANNOUNCE, SETUP, RECORD, TEARDOWN"
            )
        if verb in ("SETUP", "RECORD", "TEARDOWN", "PLAY"):
            lines.append(f"Session: {session};timeout=60")
        if verb == "SETUP":
            # Echo the transport back: ffmpeg needs its interleaved channel
            # assignment confirmed or it aborts the publish.
            transport = headers.get("transport", "")
            lines.append(f"Transport: {transport}" if transport else
                         "Transport: RTP/AVP/TCP;unicast;interleaved=0-1;mode=record")
        return ("\r\n".join(lines) + "\r\n\r\n").encode()
