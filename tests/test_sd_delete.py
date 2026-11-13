"""Deletion of card recordings (0x4b7/0x4b8).

The delete request is the fixed 14-byte head the official app's deleteRecord
builds, then each recording's STimeDay and index; the reply is one byte, 0 for
done. (Card thumbnails are not implemented - the camera streams the picture
over a separate native TUTK AV channel this library's WebRTC/SDES bridge cannot
read; see docs/PROTOCOL-INVENTORY.md.)
"""

from __future__ import annotations

import asyncio
import struct
from types import SimpleNamespace

from aidot_cameras.camera.constants import (
    IOTYPE_USER_IPCAM_DELLISTEVENT_REQ,
    IOTYPE_USER_IPCAM_DELLISTEVENT_RESP,
)
from aidot_cameras.camera.sd_events import SdEvent
from aidot_cameras.camera.sd_listing import sd_delete_request

REC = SdEvent(2026, 11, 9, 14, 5, 7, channel=0, event=2, status=0)
REC2 = SdEvent(2026, 11, 9, 20, 5, 0, channel=0, event=1, status=0)


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
    def __init__(self, delete_reply=None):
        self.sent = []
        self._delete_reply = delete_reply

    async def async_avio_request(self, cmd, payload, *, response_cmd, timeout):
        self.sent.append((cmd, payload))
        assert response_cmd == IOTYPE_USER_IPCAM_DELLISTEVENT_RESP
        return (
            None
            if self._delete_reply is None
            else SimpleNamespace(payload=self._delete_reply)
        )


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
