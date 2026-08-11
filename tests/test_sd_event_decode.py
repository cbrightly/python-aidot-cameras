"""The recording-list decoder, written before any camera here has answered.

The vendor client's own 0x319 handler supplies the parts that matter and this
pins them: twelve-byte records, an STimeDay per record, a paging end-flag, and a
per-packet count. What the handler does NOT supply is where its header fields
sit - it addresses them through registers reassigned dozens of times across a
2000-line switch - so the header offsets come from the published TUTK layout
instead.

That difference is the whole design of these tests. A decoder that quietly
forces a payload into the layout it expects would turn the first real reply into
a plausible-looking list of recordings that might be nonsense. So the decoder
reports whether the payload's own length agrees with the count the header
declared, and whether anything was left over, and these assert on those signals
as hard as on the happy path.
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


def _page(records, *, channel=0, total=5, index=0, end_flag=1, count=None):
    header = (_stimeday(2026, 8, 1, 0, 0, 0, 0)
              + _stimeday(2026, 8, 11, 2, 23, 59, 59)
              + bytes((channel, total, index, end_flag,
                       len(records) if count is None else count))
              + b"\x00\x00\x00")
    return header + b"".join(records)


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
    # The header claims five records, the payload carries one. That is the
    # signal that the layout read here is wrong, and it must reach the caller.
    page = decode_list_event_response(_page([_record()], count=5))
    assert page.record_count == 5
    assert len(page.events) == 1, "decode what is there, never invent records"
    assert page.consistent is False


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
    assert page.record_count == 1


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
