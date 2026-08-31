"""We must acknowledge the camera's SCTP DATA, or it tears the channel down.

SCTP puts acknowledgement on the receiver (RFC 4960 s6.2). We never sent a
SACK. Measured across 47 stalled sessions on the A001064 PTZ, times from first
media:

    last camera DATA on the control channel   58.35 s  sd 0.10
    last SACK of one of our heartbeats        60.08 s  sd 0.05
    first SCTP ABORT from the camera          61.42 s  sd 0.10
    session torn down                         80.2  s

The camera pushed a DATA chunk every ~3 s, we acknowledged none, its
retransmission timer ran out and it ABORTed. After the abort our AVIO heartbeat
(5156) could no longer reach the camera, so `ctx->last_keepalive` stopped being
refreshed, and 20 s later `rtc_session_check_keepalive` disconnected the session
with -50020. That is the 80.2 s cliff, and the same abort is why PTZ, talkback
and SD listing stop answering about a minute into every session - they all ride
this channel.
"""
import re

from aidot_cameras.camera.sdes_open import (
    _sctp_advance_cum_tsn,
    _sctp_sack_chunk,
)


class TestSackWireFormat:
    def test_it_is_a_16_byte_type_3_chunk(self):
        b = _sctp_sack_chunk(0x11223344)
        assert len(b) == 16
        assert b[0] == 3            # SACK
        assert b[1] == 0            # no flags
        assert int.from_bytes(b[2:4], "big") == 16

    def test_the_fields_are_where_rfc_4960_puts_them(self):
        b = _sctp_sack_chunk(0x11223344, a_rwnd=65536)
        assert int.from_bytes(b[4:8], "big") == 0x11223344   # cumulative TSN ack
        assert int.from_bytes(b[8:12], "big") == 65536       # a_rwnd
        assert int.from_bytes(b[12:14], "big") == 0          # gap ack blocks
        assert int.from_bytes(b[14:16], "big") == 0          # duplicate TSNs

    def test_a_tsn_past_the_32_bit_range_is_masked_not_raised(self):
        """TSNs wrap; struct.pack would raise on an out-of-range int."""
        b = _sctp_sack_chunk(0x1_0000_0005)
        assert int.from_bytes(b[4:8], "big") == 5


class TestCumulativeTsn:
    def test_the_first_chunk_defines_the_base(self):
        """A camera that starts numbering somewhere unexpected must not wedge
        us at an ack it will never reach."""
        assert _sctp_advance_cum_tsn(None, 5000) == 5000

    def test_the_next_tsn_in_sequence_advances_the_ack(self):
        assert _sctp_advance_cum_tsn(5000, 5001) == 5001

    def test_a_gap_does_not_advance_the_ack(self):
        """We report no gap blocks, so the ack must stay put and let the camera
        retransmit what we are missing."""
        assert _sctp_advance_cum_tsn(5000, 5002) == 5000

    def test_a_duplicate_does_not_move_the_ack_backwards(self):
        assert _sctp_advance_cum_tsn(5000, 5000) == 5000
        assert _sctp_advance_cum_tsn(5000, 4999) == 5000

    def test_it_wraps_at_2_to_the_32(self):
        assert _sctp_advance_cum_tsn(0xFFFFFFFF, 0) == 0
        assert _sctp_advance_cum_tsn(0xFFFFFFFE, 0xFFFFFFFF) == 0xFFFFFFFF

    def test_a_long_in_order_run_tracks_every_chunk(self):
        cum = None
        for tsn in range(1000, 1100):
            cum = _sctp_advance_cum_tsn(cum, tsn)
        assert cum == 1099


class TestItIsActuallyWiredIn:
    """The builders are worthless if the receive path never calls them - which
    is exactly the state this file was written to end."""

    def test_the_inbound_data_branch_sacks_before_it_dispatches(self):
        import inspect
        from aidot_cameras.camera import sdes_open
        src = inspect.getsource(sdes_open)
        m = re.search(r"elif _pd_ct8 == 0x00 and _sct == 'DONE':(.*?)_sc_pay =",
                      src, re.S)
        assert m, "the inbound SCTP DATA branch has moved or gone"
        branch = m.group(1)
        assert "_sctp_sack_chunk" in branch, (
            "inbound DATA is not acknowledged - the camera will ABORT the "
            "association and the 80.2 s cliff comes back")
        assert "_sctp_advance_cum_tsn" in branch
        assert "_br_send_to_cam" in branch, "the SACK is built but never sent"
