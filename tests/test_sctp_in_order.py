"""The camera's SCTP DATA is delivered in TSN order, not arrival order.

A ~2.8 KB SD listing page reaches us as three DATA fragments. Over a TURN relay
one can be lost and resent. Handed to reassembly as they arrived, the first and
last fragments were joined around the hole into a page that did not decode, the
resent middle and last then arrived with no beginning and were dropped, and the
listing reported that the camera had not answered - a Home Assistant install
reaching a PTZ only through the relay showed its card list cut off part way
(2026-11-02) where the same camera listed in full over the LAN.
"""

import struct

from aidot_cameras.camera.sdes_open import (
    _SCTP_GAP,
    _SCTP_GAP_HOLD_CAP,
    _SCTP_GAP_WAIT_S,
    _SCTP_MAX_AHEAD,
    _sctp_data_chunks,
    _sctp_reassemble,
    _sctp_take_in_order,
)

B, E = 0x02, 0x01


def _deliver(state, arrivals, now=0.0):
    """Feed (tsn, flags, payload) as ONE packet, as the bridge does: take every
    chunk in order first, then reassemble what that released."""
    ready = []
    for tsn, flags, payload in arrivals:
        ready += _sctp_take_in_order(state, tsn, (flags, 0, payload, 51), now)
    out = []
    for item in ready:
        if item is _SCTP_GAP:
            state.setdefault("rx_frag", {}).clear()
            continue
        f, sid, frag, _ppid = item
        msg = _sctp_reassemble(f, sid, frag, state.setdefault("rx_frag", {}))
        if msg is not None:
            out.append(msg)
    return out


def _packets(state, packets, now=0.0):
    """Several packets, each a list of (tsn, flags, payload)."""
    out = []
    for pkt in packets:
        out += _deliver(state, pkt, now)
    return out


def _started(tsn):
    """Receive state for an association whose last delivered TSN is ``tsn``."""
    return {"peer_cum_tsn": tsn}


class TestOrder:
    def test_in_order_chunks_pass_straight_through(self):
        state = _started(99)
        got = _packets(state, [[(100, B, b"ab")], [(101, 0, b"cd")], [(102, E, b"ef")]])
        assert got == [b"abcdef"]
        assert state["peer_cum_tsn"] == 102

    def test_a_lost_middle_fragment_is_waited_for_not_joined_around(self):
        state = _started(99)
        # 101 is lost; the camera resends it (and 102, which we could not ack).
        got = _packets(
            state,
            [
                [(100, B, b"ab")],
                [(102, E, b"ef")],
                [(101, 0, b"cd")],
                [(102, E, b"ef")],
            ],
        )
        assert got == [b"abcdef"]
        assert state["peer_cum_tsn"] == 102

    def test_a_chunk_past_a_gap_is_not_acknowledged_until_the_gap_fills(self):
        state = _started(99)
        assert _sctp_take_in_order(state, 101, "x", 0.0) == []
        assert state["peer_cum_tsn"] == 99
        assert _sctp_take_in_order(state, 100, "w", 0.1) == ["w", "x"]
        assert state["peer_cum_tsn"] == 101

    def test_every_packet_arriving_twice_gives_each_message_once(self):
        """Measured 2026-11-02 on a host reaching the camera by two routes:
        every control packet arrived twice, 1-3 ms apart. In arrival order a
        repeated middle fragment was appended into the page."""
        state = _started(99)
        frags = [(100, B, b"ab"), (101, 0, b"cd"), (102, E, b"ef"), (103, B | E, b"z")]
        got = _packets(state, [[f] for f in frags for _ in (0, 1)])
        assert got == [b"abcdef", b"z"]

    def test_a_resend_of_a_delivered_chunk_is_dropped(self):
        state = _started(99)
        assert _sctp_take_in_order(state, 100, "w", 0.0) == ["w"]
        assert _sctp_take_in_order(state, 100, "w", 0.1) == []
        assert _sctp_take_in_order(state, 90, "old", 0.1) == []

    def test_the_first_chunk_defines_the_base(self):
        state = {}
        assert _sctp_take_in_order(state, 5000, "a", 0.0) == ["a"]
        assert state["peer_cum_tsn"] == 5000

    def test_tsns_wrap(self):
        state = _started(0xFFFFFFFE)
        assert _sctp_take_in_order(state, 0, "b", 0.0) == []
        assert _sctp_take_in_order(state, 0xFFFFFFFF, "a", 0.0) == ["a", "b"]
        assert state["peer_cum_tsn"] == 0


class TestAGapThatNeverFills:
    """A camera that does not resend must not stall its control channel."""

    def test_it_is_given_up_after_the_wait(self):
        state = _started(99)
        assert _sctp_take_in_order(state, 101, "x", 10.0) == []
        assert _sctp_take_in_order(state, 102, "y", 10.0 + _SCTP_GAP_WAIT_S / 2) == []
        got = _sctp_take_in_order(state, 103, "z", 10.0 + _SCTP_GAP_WAIT_S)
        assert got == [_SCTP_GAP, "x", "y", "z"]
        assert state["peer_cum_tsn"] == 103
        assert not state["rx_ooo"]

    def test_it_is_given_up_when_too_much_is_waiting_behind_it(self):
        state = _started(99)
        got = []
        for i in range(_SCTP_GAP_HOLD_CAP):
            got += _sctp_take_in_order(state, 101 + i, i, 0.0)
        assert got == [_SCTP_GAP] + list(range(_SCTP_GAP_HOLD_CAP))

    def test_a_half_built_message_is_not_joined_across_it(self):
        state = _started(99)
        # B arrives, its middle is lost for good, then a whole new message.
        got = _deliver(state, [(100, B, b"ab")])
        got += _deliver(state, [(102, B | E, b"whole")], now=0.0)
        got += _deliver(state, [(103, E, b"tail")], now=_SCTP_GAP_WAIT_S)
        # "ab" + "tail" must not come out as a message.
        assert got == [b"whole"]

    def test_the_wait_runs_from_the_oldest_chunk_held(self):
        """A partial fill must not keep pushing the give-up back."""
        state = _started(100)
        assert _sctp_take_in_order(state, 102, "y", 0.0) == []
        assert _sctp_take_in_order(state, 105, "z", 0.2) == []
        # 101 fills the first gap; 102 comes out and 105 is still waiting.
        assert _sctp_take_in_order(state, 101, "x", 0.9) == ["x", "y"]
        # 105 has waited since 0.2, so the next arrival gives it up.
        got = _sctp_take_in_order(state, 107, "q", 0.2 + _SCTP_GAP_WAIT_S)
        assert got == [_SCTP_GAP, "z"]


class TestTheFirstChunk:
    def test_the_first_chunk_seen_starts_the_count_even_past_the_initial_tsn(self):
        """DATA the camera sent while the channel was opening was not taken, so
        the first chunk seen once it is up is delivered, not held behind it."""
        state = {"peer_tsn": 500}
        assert _sctp_take_in_order(state, 502, "c", 0.0) == ["c"]
        assert state["peer_cum_tsn"] == 502


class TestOnePacketAcrossAGap:
    def test_a_fragment_before_the_gap_is_not_joined_to_one_after_it(self):
        """In one packet: 100 (B) arrives in order, 104 gives up the gap at 101."""
        state = _started(99)
        _deliver(state, [(102, E, b"ef")], now=0.0)
        got = _deliver(
            state, [(100, B, b"ab"), (104, B | E, b"whole")], now=_SCTP_GAP_WAIT_S
        )
        assert got == []
        assert b"abef" not in got

    def test_a_stray_tsn_far_ahead_is_dropped_not_held(self):
        state = _started(99)
        assert _sctp_take_in_order(state, 99 + _SCTP_MAX_AHEAD + 1, "x", 0.0) == []
        assert not state["rx_ooo"]


def _data(tsn, payload, flags=B | E, sid=0, ppid=51):
    body = struct.pack("!IHHI", tsn, sid, 0, ppid) + payload
    chunk = struct.pack("!BBH", 0, flags, 4 + len(body)) + body
    return chunk + b"\x00" * (-len(chunk) % 4)


def _sack(cum):
    body = struct.pack("!IIHH", cum, 65536, 0, 0)
    return struct.pack("!BBH", 3, 0, 4 + len(body)) + body


class TestEveryDataChunkInAPacket:
    HEADER = b"\x00" * 12

    def test_a_single_chunk(self):
        got = list(_sctp_data_chunks(self.HEADER + _data(7, b"abc")))
        assert got == [(7, B | E, 0, 51, b"abc")]

    def test_two_bundled_chunks_are_both_read(self):
        pkt = self.HEADER + _data(7, b"abc") + _data(8, b"defgh", flags=B)
        assert [c[0] for c in _sctp_data_chunks(pkt)] == [7, 8]
        assert [c[4] for c in _sctp_data_chunks(pkt)] == [b"abc", b"defgh"]

    def test_data_behind_a_sack_is_read(self):
        pkt = self.HEADER + _sack(5) + _data(9, b"x")
        assert [c[0] for c in _sctp_data_chunks(pkt)] == [9]

    def test_a_packet_with_no_data_has_none(self):
        assert list(_sctp_data_chunks(self.HEADER + _sack(5))) == []

    def test_a_truncated_chunk_is_not_read_past_the_packet(self):
        assert list(_sctp_data_chunks(self.HEADER + _data(7, b"abcdef")[:10])) == []

    def test_a_chunk_cut_short_is_not_delivered_short(self):
        """Acked as if whole, it would never be resent."""
        assert list(_sctp_data_chunks(self.HEADER + _data(7, b"abcdefgh")[:-3])) == []
