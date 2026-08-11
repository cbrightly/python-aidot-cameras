"""The recording-list decoder, half from the vendor client and half from the wire.

The client's own 0x319 handler supplies the record shape - twelve bytes, an
STimeDay each, a paging end flag, a per-packet count. It does NOT supply where
the header fields sit, so the first version of this used the published TUTK
layout, 24 bytes, and refused to decode the real replies because they were
shorter than that.

Refusing was the right behaviour and it is why the true layout is now known: a
decoder that had forced those payloads into 24 bytes would have produced a
plausible-looking list of recordings that was nonsense, and nobody would have
looked again. The header is 12 bytes, measured from two live replies in run
31497241870, and both are pinned here as literal bytes - those two tests are the
only ones that can be wrong about the wire format, which is exactly why they
exist.
"""
import os
import struct
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.sd_events import (
    EVENT_RECORD_LEN,
    decode_event_record,
    decode_list_event_response,
)


def _stimeday(y, mo, d, wd, h, mi, s):
    return struct.pack("<HBBBBBB", y, mo, d, wd, h, mi, s)


def _record(y=2026, mo=8, d=11, h=9, mi=30, s=0, channel=0, event=0x12,
            status=1):
    return _stimeday(y, mo, d, 2, h, mi, s) + bytes((channel, event, status, 0))


def _page(records, *, channel=0, total=1, index=0, end_flag=1, count=None):
    body = b"".join(records)
    return (struct.pack("<II", channel, total)
            + bytes((index, end_flag,
                     len(body) if count is None else count, 0))
            + body)


#: The two replies actually received, from run 31497241870. Tests that use
#: these are the only ones that can be wrong about the wire format.
REAL_HASLISTEVENT = bytes.fromhex("00000000010000000001a800") + b"\x00" * 168
REAL_LISTEVENT = bytes.fromhex("000000000100000000010000")


def test_a_record_is_twelve_bytes():
    # Confirmed from the vendor's own arraycopy, so it is asserted rather than
    # left implicit in a struct string.
    assert EVENT_RECORD_LEN == 12
    assert len(_record()) == 12


def test_a_record_decodes_to_a_time_and_the_selectors():
    e = decode_event_record(_record(h=14, mi=5, s=9, event=0x12, status=1))
    assert e.isoformat() == "2026-08-11T14:05:09Z"
    assert (e.event, e.status) == (0x12, 1)


def test_a_page_yields_its_records():
    page = decode_list_event_response(_page([_record(mi=0), _record(mi=30)]))
    assert [e.isoformat() for e in page.events] == [
        "2026-08-11T09:00:00Z", "2026-08-11T09:30:00Z"]
    assert page.consistent and page.trailing == 0


def test_the_end_flag_is_surfaced_because_the_reply_is_paged():
    # A caller that reads one packet and stops silently truncates the list.
    assert decode_list_event_response(_page([_record()], end_flag=0)).end_flag == 0
    assert decode_list_event_response(_page([_record()], end_flag=1)).end_flag == 1


def test_a_count_that_disagrees_with_the_payload_is_reported_not_hidden():
    # The header claims a body far larger than the one that arrived. That is
    # the signal the layout is wrong for this firmware, and it must reach the
    # caller rather than being smoothed over.
    page = decode_list_event_response(_page([_record()], count=99))
    assert page.record_count == 99
    assert len(page.events) == 1, "decode what is there, never invent records"
    assert page.consistent is False


def test_the_real_listevent_reply_decodes_as_an_empty_page():
    # Captured from an A001064. The camera holds no recordings in the range, so
    # zero records is the right answer - and it must not read as a failure.
    page = decode_list_event_response(REAL_LISTEVENT)
    assert page is not None, "the measured 12-byte header must decode"
    assert page.events == []
    assert (page.end_flag, page.record_count) == (1, 0)
    assert page.consistent


def test_the_real_haslistevent_reply_is_one_byte_per_hour():
    # 168 bytes for a 7-day request: 7 x 24. The count field equals the body
    # length, which is what pins the header at 12 bytes rather than 24.
    page = decode_list_event_response(REAL_HASLISTEVENT)
    assert page is not None
    assert page.record_count == 168 == len(REAL_HASLISTEVENT) - 12
    assert page.consistent


def test_bytes_left_over_after_the_records_are_reported():
    payload = _page([_record()]) + b"\xde\xad\xbe"
    page = decode_list_event_response(payload)
    assert page.trailing == 3, (
        "a non-zero remainder means the record size or header length is wrong "
        "for this firmware - the decoder has to say so")


def test_a_payload_too_short_to_be_a_reply_decodes_to_nothing():
    # None, not an empty page: "this does not fit" and "there are no
    # recordings" are answers a caller must be able to tell apart.
    assert decode_list_event_response(b"\x00" * 8) is None
    assert decode_list_event_response(b"") is None
    assert decode_list_event_response(None) is None


def test_an_empty_but_well_formed_page_is_an_empty_page_not_a_failure():
    page = decode_list_event_response(_page([]))
    assert page is not None
    assert page.events == [] and page.consistent


def test_the_page_fields_do_not_shadow_tuple_methods():
    # `count` and `index` on a NamedTuple would shadow tuple.count/tuple.index,
    # so `page.count` would stop being callable. The fields are named for what
    # they are instead.
    page = decode_list_event_response(_page([_record()]))
    assert callable(page.count) and callable(page.index)
    # A byte count, not a record count: HASLISTEVENT uses the same field for a
    # per-hour map, and both live replies have it equal to the body length.
    assert page.record_count == EVENT_RECORD_LEN


if __name__ == "__main__":
    import traceback
    _fns = [v for k, v in sorted(globals().items()) if k.startswith("test_")]
    _fail = 0
    for _fn in _fns:
        try:
            _fn()
            print(f"PASS {_fn.__name__}")
        except Exception:
            _fail += 1
            print(f"FAIL {_fn.__name__}")
            traceback.print_exc()
    raise SystemExit(1 if _fail else 0)
