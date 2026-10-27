"""Ask a camera to play a recording off its own SD card.

The control surface only - the request bytes and the reply decode. Opening a
session the camera will play into is ``async_open_sd_playback`` on the camera
client; this half is pure so it can be tested with no camera in the way.

The request is the vendor app's own encoder,
``AVIOCTRLDEFs$SMsgAVIoctrlPlayRecord.parseContent(IIIIJII)``, as called from
``KVSWebRTCChannel.sdRecordPlay``: 0x18 bytes, little-endian.

    off 0   u32   0 (always 0 in the app's call)
    off 4   u32   command (PLAY_START, PLAY_STOP, ...)
    off 8   u32   param: 0, except PLAY_SEEKTIME, where the app sends a seek
                  counter (1..255, one more per seek)
    off 12  8B    STimeDay, UTC: u16 year, month, day, wday, hour, minute,
                  second; wday is Calendar.DAY_OF_WEEK, Sunday = 1. The record's
                  start, except PLAY_SEEKTIME, where it is the seek target
                  (the record's start plus the offset; KVSWebRTCChannel W2)
    off 20  u8    playback channel, allocated 1..26 by the app
    off 21  u8    the record's event type, as LISTEVENT reported it
    off 22  2B    0

The reply (0x31b) is read the way the app's handler reads it:

    off 0   u32   the command, echoed
    off 4   u32   result channel (the app logs it beside the channel it asked
                  for and acts on neither)
    off 8   u32   the clip length in seconds (the app's GetSDTotalTime)
    off 12  8B    the record's STimeDay, echoed
"""

import dataclasses
import datetime
import struct
from typing import Any, NamedTuple, Optional

RECORD_PLAYCONTROL_REQ_CMD = 0x31A
RECORD_PLAYCONTROL_RESP_CMD = 0x31B

# Values from the vendor enum. START and STOP are the ones exercised on
# hardware (A001064, 2026-10-27); the others are listed so a caller does not
# invent them, not because they are proven.
PLAY_PAUSE = 0x00
PLAY_STOP = 0x01
PLAY_STEPFORWARD = 0x02
PLAY_STEPBACKWARD = 0x03
PLAY_FORWARD = 0x04
PLAY_BACKWARD = 0x05
PLAY_SEEKTIME = 0x06
PLAY_END = 0x07
PLAY_START = 0x10
PLAY_RESUME = 0x11

_PAYLOAD_LEN = 0x18
_REPLY_LEN = 20

_MIN_SD_CHANNEL = 1
_MAX_SD_CHANNEL = 26

#: ``E_CMD_AVIO_CTRL_SESSION_MODE_REQ`` modes (KVSWebRTCChannel's q enum).
SESSION_MODE_LIVING = 1
SESSION_MODE_SD = 2


@dataclasses.dataclass(frozen=True)
class SdPlaybackStart:
    """What an open needs to play a recording instead of streaming live.

    ``request`` is the PLAYCONTROL payload the open sends itself once the camera
    has acknowledged ``session_mode`` on the data channel, before it waits for
    media - because in SD mode the camera sends nothing until it is asked to
    play.
    """

    request: bytes
    session_mode: int = SESSION_MODE_SD
    #: The record being played and its playback channel, for the session's
    #: pause, resume and seek (they name the same record and channel).
    record: Any = None
    sd_channel: int = 1


class PlaycontrolReply(NamedTuple):
    """What the camera said back to one playback command."""

    command: int
    result_channel: int
    clip_seconds: int
    year: int
    month: int
    day: int
    hour: int
    minute: int
    second: int


def _java_day_of_week(year: int, month: int, day: int) -> int:
    """``Calendar.DAY_OF_WEEK`` for a date: Sunday is 1, Saturday is 7."""
    try:
        return datetime.date(year, month, day).isoweekday() % 7 + 1
    except ValueError:
        return 0


def playcontrol_payload(
    command: int,
    record: Any,
    *,
    sd_channel: int = 1,
    param: int = 0,
    offset_s: float = 0,
) -> bytes:
    """The 24 bytes for one playback command against one record.

    ``record`` is an ``SdEvent`` (anything with the date fields and ``event``).
    ``offset_s`` moves the time field that many seconds past the record's
    start - the seek target, for PLAY_SEEKTIME. The field holds whole seconds.
    ``sd_channel`` must be 1..26, the range the app allocates; a value outside it
    is a caller bug and raises rather than being clamped, because a silently
    changed channel is a request the camera answers while sending nothing.
    """
    if not _MIN_SD_CHANNEL <= sd_channel <= _MAX_SD_CHANNEL:
        raise ValueError(
            f"sd_channel must be {_MIN_SD_CHANNEL}..{_MAX_SD_CHANNEL}, got {sd_channel}"
        )
    if offset_s < 0:
        raise ValueError(f"offset_s must not be negative, got {offset_s}")
    head = struct.pack("<III", 0, command, param)
    y, mo, d, h, mi, s = (
        record.year,
        record.month,
        record.day,
        record.hour,
        record.minute,
        record.second,
    )
    if offset_s:
        t = datetime.datetime(y, mo, d, h, mi, s) + datetime.timedelta(
            seconds=int(offset_s)
        )
        y, mo, d, h, mi, s = t.year, t.month, t.day, t.hour, t.minute, t.second
    when = struct.pack("<HBBBBBB", y, mo, d, _java_day_of_week(y, mo, d), h, mi, s)
    tail = struct.pack("<BB", sd_channel, int(getattr(record, "event", 0)) & 0xFF)
    return (
        head
        + when
        + tail
        + b"\x00" * (_PAYLOAD_LEN - len(head) - len(when) - len(tail))
    )


def answers(command: int):
    """A reply match for one playback command: the 0x31b reply echoes it in its
    first word, and every playback command shares that one reply id."""

    def _match(resp: Any) -> bool:
        data = bytes(getattr(resp, "payload", b"") or b"")
        return len(data) >= 4 and struct.unpack_from("<I", data)[0] == command

    return _match


def decode_playcontrol_reply(data: Optional[bytes]) -> Optional[PlaycontrolReply]:
    """Read a 0x31b reply, or None if it cannot be read.

    None rather than an exception, for the same reason the listing decode does
    it: a reply with an unexpected shape is ordinary and must not surface as a
    traceback in a media browser.
    """
    if not data or len(data) < _REPLY_LEN:
        return None
    command, result_channel, clip_seconds = struct.unpack("<III", data[:12])
    year, month, day, _wday, hour, minute, second = struct.unpack(
        "<HBBBBBB", data[12:20]
    )
    return PlaycontrolReply(
        command, result_channel, clip_seconds, year, month, day, hour, minute, second
    )


# Peer id fields a playback open announces (generate_webrtc_peer_id).
#: Field 2's first character: the Android app. The firmware of every owned
#: model gives H265, and the second video transceiver recorded H265 is sent
#: to, only to client class 0 or 1; the library's live class (2) gets audio
#: alone from an H265-recording camera.
PLAYBACK_CLIENT_CLASS = 0
#: Field 3, the app's playCmd: LIVE=0, SDCARD=1, PRE_LINK=2.
PLAY_CMD_SDCARD = 1
#: Field 5, the StreamID the firmware logs: 0 is the main stream.
PLAYBACK_STREAM = 0

#: Shared with the DTLS open's own refusal, so the two cannot drift apart.
DTLS_PLAYBACK_UNSUPPORTED = "SD playback is not supported on the DTLS transport yet"


class _CameraSdPlaybackMixin:
    """Playing a card recording, mixed into CameraMixin."""

    async def async_open_sd_playback(
        self,
        record: Any,
        *,
        on_frame: Any = None,
        output_path: Optional[str] = None,
        max_seconds: Optional[float] = None,
        rtsp_push_url: Optional[str] = None,
        status_callback: Any = None,
        timeout: float = 45.0,
        sd_channel: int = 1,
    ) -> Any:
        """Open a session that plays ``record`` instead of the live view.

        ``record`` is an ``SdEvent`` from ``async_get_sd_recordings``. Returns
        an ``SdesSession`` carrying the recording, served the way a live
        session is (``output_path``, ``rtsp_push_url``, ``on_frame``), with the
        camera's answer to PLAY_START from ``await session.playback_reply()``.
        ``async_sd_pause()``, ``async_sd_resume()`` and ``async_sd_seek(s)`` on
        the session control it; ``session.stop()`` ends it.

        Raises ``AidotSdPlaybackUnavailable`` rather than returning the live
        view on a camera this cannot play on: a DTLS camera, or an answer with
        no data channel. Proven on the A001064 (2026-10-27); the A000088 and
        A001513 firmware follow the same rule but neither has been played live.

        The session is not kept alive or reopened: a recording that ends or
        drops is reopened by calling this again. It needs the camera's one
        media slot, so it raises the same error while this client is streaming
        live; ``async_stop_streaming()`` frees it.
        """
        from ..exceptions import AidotSdPlaybackUnavailable

        if not self.is_sdes_camera:
            raise AidotSdPlaybackUnavailable(DTLS_PLAYBACK_UNSUPPORTED)
        stream_task = getattr(self, "_stream_task", None)
        if (stream_task is not None and not stream_task.done()) or getattr(
            self, "has_live_session", False
        ):
            # Two sessions on one camera fight for its single media slot: the
            # live view drops, its keepalive reopens it and pushes the
            # playback out, or the playback comes up empty.
            raise AidotSdPlaybackUnavailable(
                "a live stream holds this camera's media slot;"
                " stop it with async_stop_streaming() first"
            )
        start = SdPlaybackStart(
            request=playcontrol_payload(PLAY_START, record, sd_channel=sd_channel),
            record=record,
            sd_channel=sd_channel,
        )
        peer_id = self.generate_webrtc_peer_id(
            live_type=PLAY_CMD_SDCARD,
            # Field 4, a literal 0 in the app - not the StreamID, which is
            # field 5 (``stream``).
            stream_id=0,
            sdes=True,
            device_id=self.device_id,
            client_class=PLAYBACK_CLIENT_CLASS,
            stream=PLAYBACK_STREAM,
        )
        return await self.async_open_webrtc_stream(
            on_frame,
            timeout=timeout,
            output_path=output_path,
            max_seconds=max_seconds,
            status_callback=status_callback,
            rtsp_push_url=rtsp_push_url,
            reuse_peer_id=peer_id,
            _sd_playback=start,
        )
