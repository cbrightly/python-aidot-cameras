"""Wire the NACK tracker into the SDES bridge.

`test_nack.py` covers the packet format and the decision of which sequence
numbers are worth asking for. This covers the two seams the bridge actually
touches: deciding per forwarded video packet, and putting the request on the
same SRTCP socket the PLI already uses.

Both are kept out of the bridge closure so they can be tested without a
camera - the closure is ~2000 lines and the only other way to exercise this
would be a live A001064 on a lossy link.
"""
import os
import struct
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.protocol import decode_nack_seqs
from aidot_cameras.camera.sdes_open import (
    _sdes_nack_enabled,
    _send_video_nack,
    _video_nack_seqs,
)


class _Holder:
    """Stand-in for the bridge function the tracker is cached on."""


class _Send:
    """Stand-in for the bridge's relay-aware sender (`_br_send_to_cam`).

    The NACK must leave the same way every other reply to the camera does.
    When the camera reached us through our TURN relay, the address a packet
    arrived FROM is the relay, not the camera, and a raw write there is parsed
    as a malformed STUN message and dropped -- so a socket-and-address helper
    is silently inert on a relayed session while still reporting a send.
    """

    def __init__(self, fail=False):
        self.sent = []
        self.fail = fail

    def __call__(self, data):
        if self.fail:
            raise OSError("no route")
        self.sent.append(data)


class _Sess:
    """Stand-in for a pylibsrtp session."""

    def protect_rtcp(self, raw):
        return b"SRTCP" + raw


# --- the switch ------------------------------------------------------------ #

def test_nack_is_on_by_default(monkeypatch):
    monkeypatch.delenv("AIDOT_SDES_NACK", raising=False)
    assert _sdes_nack_enabled() is True


def test_nack_can_be_turned_off(monkeypatch):
    for val in ("0", "false", "no", "off", ""):
        monkeypatch.setenv("AIDOT_SDES_NACK", val)
        assert _sdes_nack_enabled() is False, val


# --- deciding what to ask for ---------------------------------------------- #

def test_the_first_video_packet_asks_for_nothing():
    assert _video_nack_seqs(_Holder(), 1000, now=0.0) == []


def test_a_gap_in_forwarded_video_asks_for_the_missing_numbers():
    h = _Holder()
    _video_nack_seqs(h, 1000, now=0.0)
    assert _video_nack_seqs(h, 1003, now=0.01) == [1001, 1002]


def test_the_tracker_survives_between_packets():
    # A tracker rebuilt per packet would never see a gap at all, which is the
    # quiet way this feature could ship doing nothing.
    h = _Holder()
    for seq in range(1000, 1010):
        _video_nack_seqs(h, seq, now=0.0)
    assert _video_nack_seqs(h, 1012, now=0.1) == [1010, 1011]


def test_nothing_is_asked_for_while_the_switch_is_off():
    h = _Holder()
    _video_nack_seqs(h, 1000, now=0.0, enabled=False)
    assert _video_nack_seqs(h, 1003, now=0.01, enabled=False) == []


# --- sending it ------------------------------------------------------------ #

def test_it_sends_a_generic_nack_naming_the_media_ssrc():
    send = _Send()
    assert _send_video_nack(send, None,
                            0xAB12CD34, 0x11223344, [1001, 1002]) is True
    data, = send.sent
    b0, pt = struct.unpack("!BB", data[:2])
    assert (b0 & 0x1F, pt) == (1, 205)
    assert struct.unpack("!I", data[8:12])[0] == 0x11223344
    assert decode_nack_seqs(data) == [1001, 1002]


def test_it_encrypts_when_there_is_an_srtcp_session():
    send = _Send()
    _send_video_nack(send, _Sess(), 0xAB12CD34, 0x11223344, [1001])
    data, = send.sent
    assert data.startswith(b"SRTCP"), "must go out through the SRTCP session"
    assert decode_nack_seqs(data[5:]) == [1001]


def test_a_send_failure_is_reported_not_raised():
    # This runs inside the bridge's packet loop. An exception here would take
    # the whole stream down to avoid one dropped video packet.
    assert _send_video_nack(_Send(fail=True), None,
                            0xAB12CD34, 0x11223344, [1001]) is False


def test_it_sends_nothing_when_there_is_nothing_to_ask_for():
    send = _Send()
    assert _send_video_nack(send, None, 0xAB12CD34, 0x11223344, []) is False
    assert send.sent == []
