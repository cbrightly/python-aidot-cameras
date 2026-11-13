"""Card recording thumbnails (0x4b9/0x4ba) and deletion (0x4b7/0x4b8).

Layouts as the official app builds and reads them: the thumbnail request is a
request channel then each recording's start as epoch seconds; each reply is that
channel, the start, and the picture. The delete request is a fixed 14-byte
head then each recording's STimeDay and index.
"""

from __future__ import annotations

import asyncio
import struct
from types import SimpleNamespace

from aidot_cameras.camera.constants import (
    IOTYPE_USER_IPCAM_DELLISTEVENT_REQ,
    IOTYPE_USER_IPCAM_DELLISTEVENT_RESP,
    IOTYPE_USER_IPCAM_SD_THUMBNAIL_REQ,
    IOTYPE_USER_IPCAM_SD_THUMBNAIL_RESP,
)
from aidot_cameras.camera.sd_events import SdEvent
from aidot_cameras.camera.sd_listing import (
    _record_epoch_s,
    parse_sd_thumbnail,
    sd_delete_request,
    sd_thumbnail_request,
)

REC = SdEvent(2026, 11, 9, 14, 5, 7, channel=0, event=2, status=0)
REC2 = SdEvent(2026, 11, 9, 20, 5, 0, channel=0, event=1, status=0)


def test_the_record_start_is_epoch_seconds_in_utc():
    assert _record_epoch_s(REC) == 1794233107  # 2026-11-09T14:05:07Z


def test_the_thumbnail_request_is_channel_then_starts():
    req = sd_thumbnail_request([REC, REC2], 7)
    assert len(req) == 4 + 8 * 2
    assert struct.unpack_from("<I", req, 0)[0] == 7
    assert struct.unpack_from("<q", req, 4)[0] == _record_epoch_s(REC)
    assert struct.unpack_from("<q", req, 12)[0] == _record_epoch_s(REC2)


def test_a_thumbnail_reply_is_channel_time_and_picture():
    payload = struct.pack("<Iq", 7, _record_epoch_s(REC)) + b"\xff\xd8JPEG"
    assert parse_sd_thumbnail(payload) == (7, _record_epoch_s(REC), b"\xff\xd8JPEG")
    assert parse_sd_thumbnail(b"\x00" * 11) is None


def test_the_delete_request_layout():
    req = sd_delete_request([REC, REC2], event_type=2)
    assert len(req) == 14 + 10 * 2
    assert struct.unpack_from("<IIBBBBh", req, 0) == (0, 2, 0, 1, 2, 0, 2)
    # Each record: STimeDay (year, month, day, Java weekday, h, m, s) + index.
    y, mo, d, wd, h, mi, s = struct.unpack_from("<HBBBBBB", req, 14)
    assert (y, mo, d, h, mi, s) == (2026, 11, 9, 14, 5, 7)
    assert wd == 2  # a Monday: Calendar.DAY_OF_WEEK, Sunday = 1
    assert struct.unpack_from("<h", req, 22)[0] == 0
    assert struct.unpack_from("<h", req, 32)[0] == 1


class _Session:
    def __init__(self, thumbs=None, delete_reply=None):
        self.sent = []
        self._thumbs = thumbs or {}
        self._delete_reply = delete_reply

    async def async_avio_request_all(self, cmd, payload, *, response_cmd, **kw):
        self.sent.append((cmd, payload))
        assert response_cmd == IOTYPE_USER_IPCAM_SD_THUMBNAIL_RESP
        ch = struct.unpack_from("<I", payload, 0)[0]
        n = (len(payload) - 4) // 8
        out = []
        for i in range(n):
            when = struct.unpack_from("<q", payload, 4 + 8 * i)[0]
            if when in self._thumbs:
                out.append(
                    SimpleNamespace(
                        payload=struct.pack("<Iq", ch, when) + self._thumbs[when]
                    )
                )
        return out

    async def async_avio_request(self, cmd, payload, *, response_cmd, timeout):
        self.sent.append((cmd, payload))
        assert response_cmd == IOTYPE_USER_IPCAM_DELLISTEVENT_RESP
        return (
            None
            if self._delete_reply is None
            else SimpleNamespace(payload=self._delete_reply)
        )


def test_thumbnails_come_back_keyed_by_record(make_camera_device_client):
    cam = make_camera_device_client("A000088")
    sess = _Session(thumbs={_record_epoch_s(REC): b"\xff\xd8one"})
    out = asyncio.run(cam.async_get_sd_thumbnails([REC, REC2], session=sess))
    assert out == {REC: b"\xff\xd8one"}  # REC2 had no picture
    assert sess.sent[0][0] == IOTYPE_USER_IPCAM_SD_THUMBNAIL_REQ


def test_thumbnails_need_a_live_session(make_camera_device_client):
    cam = make_camera_device_client("A000088")
    cam._stream_session = None
    assert asyncio.run(cam.async_get_sd_thumbnails([REC])) == {}


def test_delete_reports_done_refused_or_silence(make_camera_device_client):
    cam = make_camera_device_client("A000088")
    assert (
        asyncio.run(
            cam.async_delete_sd_recordings(
                [REC], session=_Session(delete_reply=b"\x00")
            )
        )
        is True
    )
    assert (
        asyncio.run(
            cam.async_delete_sd_recordings(
                [REC], session=_Session(delete_reply=b"\x01")
            )
        )
        is False
    )
    assert (
        asyncio.run(cam.async_delete_sd_recordings([REC], session=_Session())) is None
    )
    cam._stream_session = None
    assert asyncio.run(cam.async_delete_sd_recordings([REC])) is None


def test_a_single_delete_carries_the_records_event_type(make_camera_device_client):
    cam = make_camera_device_client("A000088")
    sess = _Session(delete_reply=b"\x00")
    asyncio.run(cam.async_delete_sd_recordings([REC], session=sess))
    cmd, payload = sess.sent[0]
    assert cmd == IOTYPE_USER_IPCAM_DELLISTEVENT_REQ
    assert struct.unpack_from("<IIBBBBh", payload, 0)[6] == REC.event
