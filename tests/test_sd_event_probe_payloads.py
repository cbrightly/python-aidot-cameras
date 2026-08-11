"""The SD event requests have to be the bytes the vendor's own client builds.

Everything about SD retrieval is known except the response layout, and the only
way to learn that is to send a request the camera recognises. If the request is
malformed the camera's silence says nothing at all - it would be
indistinguishable from a firmware that implements the command without a reply,
which is one of the outcomes this probe has to be able to report honestly.

So the request side is pinned against the decompiled client rather than trusted:

  * `SMsgAVIoctrlHasListEventReq.parseConent` allocates `const/16 v1, 0x16` -
    22 bytes - and copies the channel at offset 0 and the first STimeDay at
    offset 4 with length 8;
  * `STimeDay` is 8 bytes, an unsigned short year followed by six single bytes.
"""
import os
import struct
import sys
import time

sys.path.insert(0, os.path.join(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "scripts"))

from aidot_cameras.camera.sd_events import (
    HASLISTEVENT_REQ_CMD,
    HASLISTEVENT_RESP_CMD,
    LISTEVENT_REQ_CMD,
    LISTEVENT_RESP_CMD,
    SD_EVENT_ANY,
    SD_EVENT_APP,
    haslistevent_payload,
    listevent_payload,
    stimeday,
)
from sd_event_probe import (
    HASLISTEVENT_REQ,
    HASLISTEVENT_RESP,
    LISTEVENT_REQ,
    LISTEVENT_RESP,
)

# 2026-08-11 12:34:56 UTC, a Tuesday.
_WHEN = time.mktime((2026, 8, 11, 12, 34, 56, 1, 223, 0)) - time.timezone


def test_the_command_ids_are_the_vendors():
    assert (HASLISTEVENT_REQ, HASLISTEVENT_RESP) == (0x4B5, 0x4B6)
    assert (LISTEVENT_REQ, LISTEVENT_RESP) == (0x318, 0x319)


def test_stimeday_is_eight_bytes_with_a_little_endian_year():
    b = stimeday(_WHEN)
    assert len(b) == 8
    assert struct.unpack_from("<H", b, 0)[0] == 2026
    assert b[1] == 0x07, "2026 little-endian is 0xEA 0x07 - a big-endian year " \
                         "would put 0x07 first and the camera would read year 1770"


def test_stimeday_carries_the_wall_clock_fields_in_order():
    b = stimeday(_WHEN)
    assert (b[2], b[3]) == (8, 11), "month then day"
    assert (b[5], b[6], b[7]) == (12, 34, 56), "hour, minute, second"


def test_the_haslistevent_request_is_twenty_two_bytes():
    # Not a round number and not a guess: parseConent allocates 0x16.
    assert len(haslistevent_payload(_WHEN - 3600, _WHEN)) == 22


def test_the_listevent_request_is_twenty_four_bytes():
    # The overload the WebRTC path uses allocates 0x18, not 0x16. Sending 22
    # here is what the first probe did, and three cameras answered nothing.
    assert len(listevent_payload(_WHEN - 3600, _WHEN)) == 24


def test_the_default_event_selector_is_the_one_that_answers():
    # 0, not the app's 0x12. Measured 2026-08-11 on an A000088 with a card:
    # the same session and window answered an EMPTY page for 0x12 and four
    # real records for 0. The default carries the measurement so a caller
    # that says nothing gets the selector that works.
    p = listevent_payload(_WHEN - 3600, _WHEN)
    assert p[20] == 0 == SD_EVENT_ANY
    assert SD_EVENT_APP == 0x12
    assert listevent_payload(_WHEN - 3600, _WHEN, event=SD_EVENT_APP)[20] == 0x12


def test_the_probe_and_the_package_agree_on_the_command_ids():
    # One definition, two names: the probe re-exports what the package ships,
    # so a divergence here means the probe stopped testing the shipped code.
    assert (HASLISTEVENT_REQ, LISTEVENT_REQ) == (
        HASLISTEVENT_REQ_CMD, LISTEVENT_REQ_CMD)
    assert (HASLISTEVENT_RESP, LISTEVENT_RESP) == (
        HASLISTEVENT_RESP_CMD, LISTEVENT_RESP_CMD)


def test_the_channel_leads_and_the_first_time_starts_at_offset_four():
    p = haslistevent_payload(_WHEN - 3600, _WHEN, channel=0)
    assert struct.unpack_from("<I", p, 0)[0] == 0
    assert p[4:12] == stimeday(_WHEN - 3600)
    assert p[12:20] == stimeday(_WHEN)


def test_the_listevent_request_carries_event_and_status_after_the_times():
    p = listevent_payload(_WHEN - 3600, _WHEN, event=3, status=1)
    assert len(p) == 24
    assert p[20] == 3 and p[21] == 1


def test_the_two_requests_share_a_head():
    # They differ only in the trailing two bytes, which is what the two structs
    # say: one names them event and status, the other does not name them.
    a = haslistevent_payload(_WHEN - 3600, _WHEN)
    b = listevent_payload(_WHEN - 3600, _WHEN)
    assert a[:20] == b[:20]


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
