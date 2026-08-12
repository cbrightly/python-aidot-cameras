"""What the camera says its card holds, and what it must never claim.

The distinction this file exists to protect: "I could not ask" and "the card is
empty" are different answers, and a browser that collapses them shows a user an
empty folder for two opposite reasons.
"""
import struct

import pytest

from aidot_cameras.camera.sd_events import (
    HASLISTEVENT_RESP_CMD,
    LISTEVENT_RESP_CMD,
)
from aidot_cameras.camera.sd_listing import _CameraSdMixin


class _Reply:
    def __init__(self, command, payload):
        self.command = command
        self.payload = payload


def _record(year=2026, mon=8, day=11, hour=20, mi=41, sec=42,
            channel=0, event=1, status=0):
    return struct.pack("<HBBBBBBBBBB", year, mon, day, 2, hour, mi, sec,
                       channel, event, status, 0)


def _page(records, *, end_flag=1, total=1, index=0):
    body = b"".join(records)
    return (struct.pack("<II", 0, total)
            + bytes((index, end_flag, len(records), 0)) + body)


def _map(hours: bytes, *, total=1):
    return (struct.pack("<II", 0, total)
            + bytes((0, 1, len(hours) & 0xFF, 0)) + hours)


class _Session:
    """A session that answers whatever it was told to answer."""

    def __init__(self, answers, alive=True):
        self._answers = answers
        self.is_alive = alive
        self.asked = []

    async def async_avio_request(self, cmd, payload, *, response_cmd,
                                 timeout=8.0):
        self.asked.append((cmd, payload, response_cmd))
        return self._answers.get(response_cmd)


class _Camera(_CameraSdMixin):
    def __init__(self, session=None):
        self._stream_session = session
        self.device_id = "dev-1"


def test_no_session_is_not_a_live_session():
    # The point of the predicate: a caller can wait for a session to exist
    # without sending anything to find out whether it does.
    assert _Camera().has_live_session is False


def test_a_session_that_exists_is_a_live_session():
    assert _Camera(_Session({})).has_live_session is True


def test_a_torn_down_session_is_not_a_live_session():
    # Only SDES sessions say so, which is why a caller that acts on this still
    # has to handle a listing that fails anyway.
    assert _Camera(_Session({}, alive=False)).has_live_session is False


def test_the_predicate_reaches_the_client_a_caller_actually_holds():
    # Every test above builds the mixin directly, which proves the rule and not
    # the wiring. A caller reads this off the client it was handed, and reads it
    # through hasattr - so a name shadowed anywhere in that MRO would not raise,
    # it would quietly answer "cannot ask" and put the caller straight back to
    # the behaviour this predicate exists to end.
    from aidot_cameras.device_client import CameraDeviceClient, LightDeviceClient

    client = object.__new__(CameraDeviceClient)
    assert client.has_live_session is False
    client._stream_session = _Session({})
    assert client.has_live_session is True
    # And it stays off the client a light gets, like the rest of the camera
    # surface: that separation is enforced by construction, not by convention.
    assert not hasattr(LightDeviceClient, "has_live_session")


@pytest.mark.asyncio
async def test_no_session_is_not_an_empty_card():
    # None, not []. A caller cannot tell "could not ask" from "nothing there"
    # if both answer with an empty list, and those lead to opposite UI.
    assert await _Camera().async_get_sd_recordings() is None


@pytest.mark.asyncio
async def test_a_dead_session_is_also_not_an_empty_card():
    assert await _Camera(_Session({}, alive=False)).async_get_sd_recordings() is None


@pytest.mark.asyncio
async def test_the_records_come_back_decoded():
    session = _Session({
        LISTEVENT_RESP_CMD: _Reply(LISTEVENT_RESP_CMD,
                                   _page([_record(), _record(mi=55)])),
    })
    out = await _Camera(session).async_get_sd_recordings()
    assert [r.isoformat() for r in out.records] == [
        "2026-08-11T20:41:42Z", "2026-08-11T20:55:42Z"]
    assert out.complete is True


@pytest.mark.asyncio
async def test_the_request_asks_with_the_selector_that_answers():
    session = _Session({})
    await _Camera(session).async_get_sd_recordings()
    listevent = [a for a in session.asked if a[2] == LISTEVENT_RESP_CMD][0]
    # Byte 20 is the event selector. 0x12 is the app's and returns nothing.
    assert listevent[1][20] == 0
    assert len(listevent[1]) == 24


@pytest.mark.asyncio
async def test_silence_is_an_empty_list_and_not_an_exception():
    # A camera that never answers is the common case on a model without a
    # card. It must reach the browser as "nothing", never as a traceback.
    out = await _Camera(_Session({})).async_get_sd_recordings()
    assert out is not None
    assert out.records == [] and out.hours is None


@pytest.mark.asyncio
async def test_silence_and_an_empty_card_are_not_the_same_answer():
    # THE distinction this module exists for, and the one that is easiest to
    # lose: both of these carry an empty record list, and a browser that reads
    # only `records` tells a silent camera's owner that their card is empty.
    silent = await _Camera(_Session({})).async_get_sd_recordings()
    answered = await _Camera(_Session({
        LISTEVENT_RESP_CMD: _Reply(LISTEVENT_RESP_CMD, _page([])),
    })).async_get_sd_recordings()

    assert silent.records == answered.records == []
    assert silent.answered is False
    assert answered.answered is True


@pytest.mark.asyncio
async def test_the_map_alone_is_enough_to_prove_the_camera_is_listening():
    # Only one of the two requests has to come back. A firmware that answers
    # HASLISTEVENT and ignores LISTEVENT is still a camera that is talking.
    session = _Session({
        HASLISTEVENT_RESP_CMD: _Reply(HASLISTEVENT_RESP_CMD, _map(bytes(24))),
    })
    out = await _Camera(session).async_get_sd_recordings()
    assert out.answered is True and out.records == []


@pytest.mark.asyncio
async def test_a_dead_channel_does_not_come_back_as_an_empty_card():
    # A session that raises on every request is torn down, not empty. Only
    # SDES sessions publish is_alive, so this is the guard that has to hold on
    # every transport.
    class _Angry(_Session):
        async def async_avio_request(self, *a, **k):
            raise RuntimeError("channel gone")

    out = await _Camera(_Angry({})).async_get_sd_recordings()
    assert out.answered is False


@pytest.mark.asyncio
async def test_a_page_that_never_ends_is_reported_incomplete():
    session = _Session({
        LISTEVENT_RESP_CMD: _Reply(LISTEVENT_RESP_CMD,
                                   _page([_record()], end_flag=0)),
    })
    out = await _Camera(session).async_get_sd_recordings()
    assert out.records and out.complete is False


@pytest.mark.asyncio
async def test_the_occupancy_map_comes_back_as_hours_not_as_records():
    hours = bytes(168)
    session = _Session({
        HASLISTEVENT_RESP_CMD: _Reply(HASLISTEVENT_RESP_CMD, _map(hours)),
    })
    out = await _Camera(session).async_get_sd_recordings()
    assert out.hours == hours
    assert out.records == [], "fourteen 12-byte 'records' fit in 168 bytes"


@pytest.mark.asyncio
async def test_the_window_is_carried_so_an_hour_byte_can_be_placed():
    session = _Session({})
    out = await _Camera(session).async_get_sd_recordings(days=2)
    assert round(out.end_ts - out.start_ts) == 2 * 86400


@pytest.mark.asyncio
async def test_an_undecodable_reply_is_an_empty_list_with_the_session_intact():
    session = _Session({
        LISTEVENT_RESP_CMD: _Reply(LISTEVENT_RESP_CMD, b"\x01\x02\x03"),
    })
    out = await _Camera(session).async_get_sd_recordings()
    assert out is not None and out.records == []
    # Something came back and could not be read, so the camera IS listening
    # and the empty list is NOT the whole story. Reporting this as a complete
    # empty list would hide every recording behind an unreadable reply.
    assert out.answered is True
    assert out.complete is False


@pytest.mark.asyncio
async def test_a_session_that_raises_does_not_reach_the_caller():
    class _Angry(_Session):
        async def async_avio_request(self, *a, **k):
            raise RuntimeError("channel gone")

    out = await _Camera(_Angry({})).async_get_sd_recordings()
    assert out is not None and out.records == []
