"""What the camera says its card holds, and what it must never claim.

The distinction this file exists to protect: "I could not ask" and "the card is
empty" are different answers, and a browser that collapses them shows a user an
empty folder for two opposite reasons.
"""

import struct
from datetime import UTC, datetime, timedelta

import pytest

from aidot_cameras.camera.sd_events import (
    HASLISTEVENT_RESP_CMD,
    LISTEVENT_RESP_CMD,
    _stimeday,
)
from aidot_cameras.camera import sd_listing
from aidot_cameras.camera.sd_listing import _CameraSdMixin

#: The records below are dated 2026-08-11, and a listing drops a record from
#: outside the window it asked for, so every listing here runs on that day.
_NOW = datetime(2026, 8, 12, tzinfo=UTC).timestamp()


@pytest.fixture(autouse=True)
def _listing_clock(monkeypatch):
    monkeypatch.setattr(sd_listing.time, "time", lambda: _NOW)


class _Reply:
    def __init__(self, command, payload):
        self.command = command
        self.payload = payload


def _record(
    year=2026, mon=8, day=11, hour=20, mi=41, sec=42, channel=0, event=1, status=0
):
    return struct.pack(
        "<HBBBBBBBBBB", year, mon, day, 2, hour, mi, sec, channel, event, status, 0
    )


def _page(records, *, end_flag=1, total=1, index=0):
    body = b"".join(records)
    return (
        struct.pack("<II", 0, total) + bytes((index, end_flag, len(records), 0)) + body
    )


def _map(hours: bytes, *, total=1):
    return struct.pack("<II", 0, total) + bytes((0, 1, len(hours) & 0xFF, 0)) + hours


class _Session:
    """A session that answers whatever it was told to answer."""

    def __init__(self, answers, alive=True):
        self._answers = answers
        self.is_alive = alive
        self.asked = []

    async def async_avio_request(self, cmd, payload, *, response_cmd, timeout=8.0):
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
    session = _Session(
        {
            LISTEVENT_RESP_CMD: _Reply(
                LISTEVENT_RESP_CMD, _page([_record(), _record(mi=55)])
            ),
        }
    )
    # One window: this fake answers every window with the same page.
    out = await _Camera(session).async_get_sd_recordings(days=1)
    assert [r.isoformat() for r in out.records] == [
        "2026-08-11T20:41:42Z",
        "2026-08-11T20:55:42Z",
    ]
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
    answered = await _Camera(
        _Session(
            {
                LISTEVENT_RESP_CMD: _Reply(LISTEVENT_RESP_CMD, _page([])),
            }
        )
    ).async_get_sd_recordings()

    assert silent.records == answered.records == []
    assert silent.answered is False
    assert answered.answered is True


@pytest.mark.asyncio
async def test_the_map_alone_is_enough_to_prove_the_camera_is_listening():
    # Only one of the two requests has to come back. A firmware that answers
    # HASLISTEVENT and ignores LISTEVENT is still a camera that is talking.
    session = _Session(
        {
            HASLISTEVENT_RESP_CMD: _Reply(HASLISTEVENT_RESP_CMD, _map(bytes(24))),
        }
    )
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
    session = _Session(
        {
            LISTEVENT_RESP_CMD: _Reply(
                LISTEVENT_RESP_CMD, _page([_record()], end_flag=0)
            ),
        }
    )
    out = await _Camera(session).async_get_sd_recordings()
    assert out.records and out.complete is False


@pytest.mark.asyncio
async def test_the_occupancy_map_comes_back_as_hours_not_as_records():
    hours = bytes(168)
    session = _Session(
        {
            HASLISTEVENT_RESP_CMD: _Reply(HASLISTEVENT_RESP_CMD, _map(hours)),
        }
    )
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
    session = _Session(
        {
            LISTEVENT_RESP_CMD: _Reply(LISTEVENT_RESP_CMD, b"\x01\x02\x03"),
        }
    )
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


class _PagedSession(_Session):
    """A session that answers the list with every page, like a real camera."""

    def __init__(self, pages, answers=None):
        super().__init__(answers or {})
        self._pages = pages

    async def async_avio_request_all(
        self, cmd, payload, *, response_cmd, max_replies, done, **_timeouts
    ):
        self.asked.append((cmd, payload, response_cmd))
        got = []
        for body in self._pages[:max_replies]:
            got.append(_Reply(response_cmd, body))
            if done(got):
                break
        return got


@pytest.mark.asyncio
async def test_every_page_of_a_long_listing_is_read():
    """Measured 2026-11-01: four pages for one PTZ week, and only the first was read.

    The first page holds the OLDEST records, so stopping there hid every
    recording newer than the first 255.
    """
    session = _PagedSession(
        [
            _page([_record(day=9)], total=3, index=0, end_flag=0),
            _page([_record(day=10)], total=3, index=1, end_flag=0),
            _page([_record(day=11)], total=3, index=2, end_flag=1),
        ]
    )
    out = await _Camera(session).async_get_sd_recordings(days=3)
    assert [r.day for r in out.records] == [9, 10, 11]
    assert out.complete is True


@pytest.mark.asyncio
async def test_pages_that_arrive_out_of_order_are_put_back_in_order():
    session = _PagedSession(
        [
            _page([_record(day=10)], total=2, index=1, end_flag=1),
            _page([_record(day=9)], total=2, index=0, end_flag=0),
        ]
    )
    out = await _Camera(session).async_get_sd_recordings(days=3)
    assert [r.day for r in out.records] == [9, 10]
    assert out.complete is True


@pytest.mark.asyncio
async def test_a_page_sent_twice_is_counted_once():
    session = _PagedSession(
        [
            _page([_record(day=9)], total=2, index=0, end_flag=0),
            _page([_record(day=9)], total=2, index=0, end_flag=0),
            _page([_record(day=10)], total=2, index=1, end_flag=1),
        ]
    )
    out = await _Camera(session).async_get_sd_recordings(days=3)
    assert [r.day for r in out.records] == [9, 10]
    assert out.complete is True


@pytest.mark.asyncio
async def test_a_missing_page_before_the_end_is_reported_incomplete():
    session = _PagedSession(
        [
            _page([_record(day=9)], total=3, index=0, end_flag=0),
            _page([_record(day=11)], total=3, index=2, end_flag=1),
        ]
    )
    out = await _Camera(session).async_get_sd_recordings(days=3)
    assert [r.day for r in out.records] == [9, 11]
    assert out.complete is False


@pytest.mark.asyncio
async def test_pages_that_stop_before_the_end_are_reported_incomplete():
    session = _PagedSession(
        [
            _page([_record(day=9)], total=3, index=0, end_flag=0),
            _page([_record(day=10)], total=3, index=1, end_flag=0),
        ]
    )
    out = await _Camera(session).async_get_sd_recordings(days=3)
    assert [r.day for r in out.records] == [9, 10]
    assert out.complete is False
    assert out.answered is True


@pytest.mark.asyncio
async def test_a_late_page_of_another_listing_is_not_mixed_in():
    """Every page of one reply declares the same page count; a stray does not."""
    session = _PagedSession(
        [
            _page([_record(day=9)], total=2, index=0, end_flag=0),
            _page([_record(day=1)], total=4, index=1, end_flag=0),
            _page([_record(day=10)], total=2, index=1, end_flag=1),
        ]
    )
    out = await _Camera(session).async_get_sd_recordings(days=3)
    assert [r.day for r in out.records] == [9, 10]
    assert out.complete is True


@pytest.mark.asyncio
async def test_an_end_page_first_with_no_page_count_does_not_end_the_listing():
    """With no declared count, the pages before the end-flag page are still owed."""
    session = _PagedSession(
        [
            _page([_record(day=10)], total=0, index=1, end_flag=1),
            _page([_record(day=9)], total=0, index=0, end_flag=0),
        ]
    )
    out = await _Camera(session).async_get_sd_recordings(days=3)
    assert [r.day for r in out.records] == [9, 10]
    assert out.complete is True


class _WindowSession(_Session):
    """A session that answers each listing window from a function of it."""

    def __init__(self, answer_for):
        super().__init__({})
        self._answer_for = answer_for
        self.windows = []

    async def async_avio_request(self, cmd, payload, *, response_cmd, timeout=8.0):
        if response_cmd != LISTEVENT_RESP_CMD:
            return None
        start = datetime(*_stimeday(payload, 4), tzinfo=UTC)
        end = datetime(*_stimeday(payload, 12), tzinfo=UTC)
        self.windows.append((start, end))
        body = self._answer_for(start, end)
        return None if body is None else _Reply(LISTEVENT_RESP_CMD, body)


@pytest.mark.asyncio
async def test_a_week_is_asked_in_windows_the_camera_answers_correctly():
    """An A000088 fills in nothing for a window of five days or more (2026-11-02)."""
    session = _WindowSession(lambda start, end: _page([]))
    await _Camera(session).async_get_sd_recordings(days=7)
    assert len(session.windows) == 3
    assert all(end - start <= timedelta(days=3) for start, end in session.windows)
    # Newest first, contiguous, and the whole week between them.
    assert session.windows[0][1] > session.windows[-1][1]
    for (start, _), (_, end) in zip(session.windows, session.windows[1:]):
        assert end == start
    assert session.windows[0][1] - session.windows[-1][0] >= timedelta(
        days=7
    ) - timedelta(seconds=2)


@pytest.mark.asyncio
async def test_records_from_every_window_come_back_oldest_first_and_once():
    def answer(start, end):
        # Each window holds one record of its own, plus one on its lower edge
        # that the window below also reports.
        mid = start + (end - start) / 2
        return _page(
            [
                _record(
                    year=start.year,
                    mon=start.month,
                    day=start.day,
                    hour=start.hour,
                    mi=start.minute,
                    sec=start.second,
                ),
                _record(
                    year=mid.year,
                    mon=mid.month,
                    day=mid.day,
                    hour=mid.hour,
                    mi=mid.minute,
                    sec=mid.second,
                ),
            ]
            + (
                [
                    _record(
                        year=end.year,
                        mon=end.month,
                        day=end.day,
                        hour=end.hour,
                        mi=end.minute,
                        sec=end.second,
                    )
                ]
                if end < session.windows[0][1]
                else []
            )
        )

    session = _WindowSession(answer)
    out = await _Camera(session).async_get_sd_recordings(days=7)
    stamps = [r.isoformat() for r in out.records]
    assert stamps == sorted(stamps)
    assert len(stamps) == len(set(stamps))
    assert len(stamps) == 3 * 2
    assert out.complete is True


@pytest.mark.asyncio
async def test_records_the_camera_never_filled_in_are_dropped():
    blank = b"\x00" * 12
    session = _WindowSession(lambda start, end: _page([blank, _record(), blank]))
    out = await _Camera(session).async_get_sd_recordings(days=1)
    assert [r.isoformat() for r in out.records] == ["2026-08-11T20:41:42Z"]
    # There were more slots than records: this is not the whole card.
    assert out.complete is False


@pytest.mark.asyncio
async def test_a_window_that_goes_unanswered_part_way_keeps_the_newer_records():
    calls = []

    def answer(start, end):
        calls.append(start)
        return _page([_record()]) if len(calls) == 1 else None

    session = _WindowSession(answer)
    out = await _Camera(session).async_get_sd_recordings(days=7)
    # It stops there: a reply still on its way for the window given up on
    # would otherwise be read as the next window's.
    assert len(session.windows) == 2
    assert [r.isoformat() for r in out.records] == ["2026-08-11T20:41:42Z"]
    assert out.answered is True
    assert out.complete is False


@pytest.mark.asyncio
async def test_a_camera_that_answers_no_window_is_asked_once():
    session = _WindowSession(lambda start, end: None)
    out = await _Camera(session).async_get_sd_recordings(days=7)
    assert len(session.windows) == 1
    assert out.answered is False


class _PagedWindowSession(_Session):
    """Every page of each window's reply through the multi-reply request."""

    def __init__(self, pages_for):
        super().__init__({})
        self._pages_for = pages_for
        self.windows = []
        self.timeouts = []

    async def async_avio_request_all(
        self, cmd, payload, *, response_cmd, timeout, max_replies, done, **_rest
    ):
        start = datetime(*_stimeday(payload, 4), tzinfo=UTC)
        end = datetime(*_stimeday(payload, 12), tzinfo=UTC)
        self.windows.append((start, end))
        self.timeouts.append(timeout)
        got = []
        for body in self._pages_for(start, end)[:max_replies]:
            got.append(_Reply(response_cmd, body))
            if done(got):
                break
        return got

    async def async_avio_request(self, cmd, payload, *, response_cmd, timeout=8.0):
        # The occupancy map; this camera does not draw one.
        self.timeouts.append(("map", timeout))


def _at(when):
    return _record(
        year=when.year,
        mon=when.month,
        day=when.day,
        hour=when.hour,
        mi=when.minute,
        sec=when.second,
    )


@pytest.mark.asyncio
async def test_each_window_reads_all_of_its_pages():
    def pages_for(start, end):
        mid = start + (end - start) / 2
        return [
            _page([_at(start + timedelta(hours=1))], total=2, index=0, end_flag=0),
            _page([_at(mid)], total=2, index=1, end_flag=1),
        ]

    session = _PagedWindowSession(pages_for)
    out = await _Camera(session).async_get_sd_recordings(days=7)
    assert len(session.windows) == 3
    assert len(out.records) == 6
    stamps = [r.isoformat() for r in out.records]
    assert stamps == sorted(stamps)
    assert out.complete is True


@pytest.mark.asyncio
async def test_a_window_cut_short_ends_the_listing():
    """Its missing pages may still arrive, and must not answer the next window."""

    def pages_for(start, end):
        return [_page([_at(start + timedelta(hours=1))], total=2, index=0, end_flag=0)]

    session = _PagedWindowSession(pages_for)
    out = await _Camera(session).async_get_sd_recordings(days=7)
    assert len(session.windows) == 1
    assert len(out.records) == 1
    assert out.complete is False


@pytest.mark.asyncio
async def test_a_record_of_stray_bytes_is_dropped():
    """Measured on an A000088: slots that decode to month 160 or year 1680."""
    junk = struct.pack("<HBBBBBBBBBB", 1680, 160, 6, 2, 6, 160, 6, 0, 0, 0, 0)
    session = _WindowSession(lambda start, end: _page([junk, _record()]))
    out = await _Camera(session).async_get_sd_recordings(days=1)
    assert [r.isoformat() for r in out.records] == ["2026-08-11T20:41:42Z"]
    assert out.complete is False


@pytest.mark.asyncio
async def test_a_record_from_outside_the_window_is_dropped():
    session = _WindowSession(lambda start, end: _page([_record(mon=1), _record()]))
    out = await _Camera(session).async_get_sd_recordings(days=1)
    assert [r.isoformat() for r in out.records] == ["2026-08-11T20:41:42Z"]
    assert out.complete is False


@pytest.mark.asyncio
async def test_two_identical_records_in_one_window_are_both_kept():
    session = _WindowSession(lambda start, end: _page([_record(), _record()]))
    out = await _Camera(session).async_get_sd_recordings(days=1)
    assert len(out.records) == 2


@pytest.mark.asyncio
async def test_without_a_timeout_a_list_page_gets_the_longer_wait():
    session = _PagedWindowSession(lambda start, end: [_page([])])
    await _Camera(session).async_get_sd_recordings(days=1)
    assert session.timeouts == [
        sd_listing._SD_LIST_FIRST_PAGE_TIMEOUT_S,
        ("map", sd_listing._SD_LIST_TIMEOUT_S),
    ]


@pytest.mark.asyncio
async def test_a_callers_timeout_is_used_as_given():
    session = _PagedWindowSession(lambda start, end: [_page([])])
    await _Camera(session).async_get_sd_recordings(days=1, timeout=3.0)
    assert session.timeouts == [3.0, ("map", 3.0)]


@pytest.mark.asyncio
async def test_an_empty_window_is_still_asked():
    """Never 'complete' for a listing that was not sent."""
    session = _PagedWindowSession(lambda start, end: [_page([])])
    out = await _Camera(session).async_get_sd_recordings(days=0)
    assert len(session.windows) == 1
    assert out.answered is True
