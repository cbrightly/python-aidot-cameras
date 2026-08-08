"""An INIT-ACK describes the camera's send sequence, not ours.

RFC 4960 s3.3.3: the INIT-ACK carries the peer's Initiate Tag and the peer's
Initial TSN.  Ours is the one we chose for our own INIT and keep incrementing
in the DATA path, and nothing the camera answers with may move it -- overwrite
it and our very next DATA chunk is numbered from the camera's sequence instead
of the sequence we already told the camera we would use, which is an SCTP
protocol violation the camera is entitled to drop or ABORT on.

`_sctp_parse_init_ack` wrote the peer's Initial TSN into `local_tsn`.  The path
is close to unreachable in practice -- the SDES cameras we have go through the
role-reversed INIT/INIT-ACK branch, where `_sctp_parse_init` (which has always
had the mapping right) is the one that runs -- so there is no live symptom to
point at and there never was.  That is exactly why the mapping needs pinning
here: a test is the only thing that can tell anyone this is right.
"""
import struct

from aidot_cameras.camera.sdes_open import _sctp_parse_init_ack

OUR_TSN = 0x11111111
CAMERA_TSN = 0x22222222
CAMERA_TAG = 0x33333333
COOKIE = b"state-cookie-bytes"


def _init_ack(*, peer_tag=CAMERA_TAG, peer_tsn=CAMERA_TSN, cookie=COOKIE):
    """A minimal but wire-accurate SCTP INIT-ACK carrying a State Cookie."""
    body = struct.pack(
        "!IIHHI",
        peer_tag,     # Initiate Tag        - the camera's
        131072,       # a_rwnd
        1024,         # Number of Outbound Streams
        2048,         # Number of Inbound Streams
        peer_tsn,     # Initial TSN         - the camera's
    )
    body += struct.pack("!HH", 7, 4 + len(cookie)) + cookie  # State Cookie param
    chunk = struct.pack("!BBH", 0x02, 0, 4 + len(body)) + body
    header = struct.pack("!HHII", 5000, 5000, 0, 0)  # sport, dport, vtag, checksum
    return header + chunk


def _state():
    return {
        'state': 'INIT_SENT', 'local_tag': 0xAAAAAAAA, 'peer_tag': 0,
        'local_tsn': OUR_TSN, 'stream_seq': 0,
    }


def test_the_cameras_initial_tsn_is_recorded_as_the_peers():
    state = _state()

    _sctp_parse_init_ack(_init_ack(), state)

    assert state['peer_tsn'] == CAMERA_TSN


def test_our_own_tsn_survives_the_init_ack():
    """The counter our DATA chunks are numbered from is ours to keep."""
    state = _state()

    _sctp_parse_init_ack(_init_ack(), state)

    assert state['local_tsn'] == OUR_TSN


def test_the_cameras_verification_tag_is_recorded():
    state = _state()

    _sctp_parse_init_ack(_init_ack(), state)

    assert state['peer_tag'] == CAMERA_TAG


def test_the_state_cookie_is_returned_for_the_cookie_echo():
    assert _sctp_parse_init_ack(_init_ack(), _state()) == COOKIE


def test_an_init_ack_without_a_cookie_yields_nothing():
    """No cookie means no COOKIE-ECHO to send, so the caller keeps waiting."""
    body = struct.pack("!IIHHI", CAMERA_TAG, 131072, 1024, 2048, CAMERA_TSN)
    chunk = struct.pack("!BBH", 0x02, 0, 4 + len(body)) + body
    pkt = struct.pack("!HHII", 5000, 5000, 0, 0) + chunk

    assert _sctp_parse_init_ack(pkt, _state()) is None


def test_a_packet_that_is_not_an_init_ack_leaves_the_state_alone():
    """A COOKIE-ACK (type 11) shares the loop; it must not be read as an answer."""
    state = _state()
    pkt = struct.pack("!HHII", 5000, 5000, 0, 0) + struct.pack("!BBH", 0x0B, 0, 4)

    assert _sctp_parse_init_ack(pkt, state) is None
    assert state == _state()
