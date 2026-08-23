"""Ask the camera to resend the video packets the air dropped.

Measured 2026-08-23 on an A001064 over SDES: ~0.7% of its video RTP packets
never arrive, 37 of 56 losses landing INSIDE a frame (identical RTP timestamp
either side of the sequence gap), so ffmpeg reassembles a truncated H.264
slice and forwards it. A browser's WebRTC decoder conceals that; Media Source
Extensions treats a damaged keyframe as fatal and kills the pipeline. The
camera's own answer negotiates the recovery mechanism already:

    a=rtcp-fb:8  nack / goog-remb        (audio, PCMA)
    a=rtcp-fb:96 nack / goog-remb        (video)

Negotiating it changes nothing until a NACK is actually sent. This is that
packet, plus the bookkeeping that decides which sequence numbers to ask for.

Two things are worth testing rather than eyeballing. The wire format packs up
to 17 sequence numbers into one (PID, BLP) pair, and an off-by-one in the
bitmask asks for the wrong packets - which looks like NACK "not working"
rather than like a bug. And the tracker has to tell a real gap apart from
reordering, a 16-bit wrap, and a stream restart; getting that wrong either
floods a congested link or silently asks for nothing.
"""
import struct

import pytest

from aidot_cameras.camera.protocol import (
    NackTracker,
    build_nack,
    decode_nack_seqs,
)


# --------------------------------------------------------------------------
# build_nack: the wire format
# --------------------------------------------------------------------------

def test_it_is_a_transport_layer_feedback_packet():
    pkt = build_nack(sender_ssrc=0xAB12CD34, media_ssrc=0x11223344,
                     lost_seqs=[100])
    b0, pt, length = struct.unpack("!BBH", pkt[:4])
    assert b0 >> 6 == 2, "version must be 2"
    assert b0 & 0x1F == 1, "FMT 1 identifies Generic NACK"
    assert pt == 205, "PT 205 is transport-layer feedback"
    assert len(pkt) == (length + 1) * 4, "length field must match the packet"


def test_it_names_both_ssrcs():
    pkt = build_nack(sender_ssrc=0xAB12CD34, media_ssrc=0x11223344,
                     lost_seqs=[100])
    sender, media = struct.unpack("!II", pkt[4:12])
    assert sender == 0xAB12CD34
    assert media == 0x11223344


def test_a_single_loss_is_one_fci_with_an_empty_bitmask():
    pkt = build_nack(0xAB12CD34, 0x11223344, [100])
    pid, blp = struct.unpack("!HH", pkt[12:16])
    assert pid == 100
    assert blp == 0, "nothing else is lost, so no bits are set"


def test_the_bitmask_counts_from_the_packet_after_the_pid():
    # BLP bit 0 means PID+1, not PID. An off-by-one here asks the camera to
    # resend packets that arrived and skips the ones that did not.
    pkt = build_nack(0xAB12CD34, 0x11223344, [100, 101])
    pid, blp = struct.unpack("!HH", pkt[12:16])
    assert pid == 100
    assert blp == 0b1, "101 is PID+1, so bit 0"

    pkt = build_nack(0xAB12CD34, 0x11223344, [100, 116])
    pid, blp = struct.unpack("!HH", pkt[12:16])
    assert blp == 1 << 15, "116 is PID+16, the highest bit BLP can carry"


def test_seventeen_losses_fit_in_one_fci_and_the_eighteenth_starts_another():
    seqs = list(range(100, 117))          # 100..116 inclusive: 17 numbers
    assert len(build_nack(0xAB12CD34, 1, seqs)) == 12 + 4

    seqs = list(range(100, 118))          # 18 numbers
    assert len(build_nack(0xAB12CD34, 1, seqs)) == 12 + 8


def test_it_round_trips_through_the_decoder():
    seqs = [100, 101, 105, 116, 200, 65535]
    pkt = build_nack(0xAB12CD34, 0x11223344, seqs)
    assert decode_nack_seqs(pkt) == sorted(seqs)


def test_it_round_trips_across_the_sequence_wrap():
    # A gap that straddles 65535 -> 0 must still be expressible: the PID is
    # 65534 and the bitmask runs past the wrap.
    seqs = [65534, 65535, 0, 1]
    pkt = build_nack(0xAB12CD34, 0x11223344, seqs)
    assert decode_nack_seqs(pkt) == seqs


def test_it_refuses_to_ask_for_nothing():
    with pytest.raises(ValueError):
        build_nack(0xAB12CD34, 0x11223344, [])


# --------------------------------------------------------------------------
# NackTracker: which sequence numbers to ask for
# --------------------------------------------------------------------------

def test_the_first_packet_is_never_a_loss():
    assert NackTracker().observe(1000, now=0.0) == []


def test_packets_in_order_ask_for_nothing():
    t = NackTracker()
    t.observe(1000, now=0.0)
    assert t.observe(1001, now=0.01) == []
    assert t.observe(1002, now=0.02) == []


def test_a_gap_asks_for_exactly_the_missing_numbers():
    t = NackTracker()
    t.observe(1000, now=0.0)
    assert t.observe(1004, now=0.01) == [1001, 1002, 1003]


def test_it_does_not_re_ask_on_the_very_next_packet():
    # One loss must not turn into a NACK per packet for the rest of the frame.
    t = NackTracker()
    t.observe(1000, now=0.0)
    t.observe(1004, now=0.01)
    assert t.observe(1005, now=0.02) == []


def test_a_packet_that_turns_up_late_is_not_asked_for_again():
    t = NackTracker()
    t.observe(1000, now=0.0)
    t.observe(1004, now=0.01)           # asks for 1001..1003
    t.observe(1002, now=0.02)           # 1002 arrives after all
    assert t.observe(1005, now=1.0) == [1001, 1003], "1002 is no longer missing"


def test_it_re_asks_once_the_retry_delay_has_passed():
    t = NackTracker(retry_after=0.2)
    t.observe(1000, now=0.0)
    assert t.observe(1004, now=0.0) == [1001, 1002, 1003]
    assert t.observe(1005, now=0.1) == [], "too soon"
    assert t.observe(1006, now=0.25) == [1001, 1002, 1003]


def test_it_gives_up_after_a_few_attempts():
    t = NackTracker(retry_after=0.1, max_requests=2)
    t.observe(1000, now=0.0)
    assert t.observe(1004, now=0.0) == [1001, 1002, 1003]
    assert t.observe(1005, now=0.2) == [1001, 1002, 1003]
    assert t.observe(1006, now=0.4) == [], "two attempts is the limit"


def test_it_forgets_a_loss_that_is_too_old_to_be_useful():
    # A retransmission that arrives after the decoder has moved on is worse
    # than useless on a congested link: it costs bandwidth and fixes nothing.
    t = NackTracker(max_behind=50, retry_after=0.0)
    t.observe(1000, now=0.0)
    t.observe(1002, now=0.0)            # 1001 is missing
    assert t.observe(1060, now=0.1) != [], "the 1060 gap itself is fresh"
    assert 1001 not in t.observe(1061, now=0.2), "1001 is now 60 behind"


def test_a_sequence_wrap_is_not_a_gap():
    t = NackTracker()
    t.observe(65534, now=0.0)
    t.observe(65535, now=0.01)
    assert t.observe(0, now=0.02) == []
    assert t.observe(1, now=0.03) == []


def test_a_gap_across_the_wrap_asks_for_the_wrapped_numbers():
    t = NackTracker()
    t.observe(65534, now=0.0)
    assert t.observe(1, now=0.01) == [65535, 0]


def test_a_duplicate_packet_is_not_a_gap():
    t = NackTracker()
    t.observe(1000, now=0.0)
    t.observe(1001, now=0.01)
    assert t.observe(1001, now=0.02) == []


def test_an_old_reordered_packet_is_not_a_gap():
    t = NackTracker()
    t.observe(1000, now=0.0)
    t.observe(1005, now=0.01)
    assert t.observe(999, now=0.02) == []


def test_a_stream_restart_resets_instead_of_asking_for_thousands():
    # The camera republishes with a fresh random sequence base. Treating that
    # as a 40000-packet loss would emit a NACK storm at the worst moment.
    t = NackTracker(max_gap=100)
    t.observe(1000, now=0.0)
    assert t.observe(41000, now=0.01) == []
    assert t.observe(41001, now=0.02) == []
    assert t.observe(41005, now=0.03) == [41002, 41003, 41004], (
        "and it carries on from the new base")


def test_it_does_not_re_ask_before_a_repeat_could_have_arrived():
    """The retry interval is a measured value, not a guess. Do not lower it.

    Live A/B on the reference A001064, same camera, comparable link (2.15% vs
    2.27% loss):

        retry_after=0.15, 3 attempts -> 99.2% of losses recovered
        retry_after=0.10, 4 attempts -> 73.6% of losses recovered

    Recovery takes 45 ms at the median but 162 ms at p90, so a 100 ms retry
    re-asks for a large share of packets whose repeat is still in flight. The
    camera then sends it twice, on an uplink that is already saturated enough
    to be dropping 2% -- and recovery collapses. More attempts, sooner, made
    it worse. The interval has to clear the p90, not the median.

    The last attempt still has to leave room for the repeat to beat ffmpeg's
    `-max_delay 500000`, which 0.15 x 2 = 300 ms does.
    """
    t = NackTracker()
    t.observe(1000, now=0.0)
    attempts = []
    now, nxt = 0.0, 1002
    while now <= 0.6:
        if 1001 in t.observe(nxt, now=now):
            attempts.append(round(now, 3))
        nxt += 1
        now = round(now + 0.01, 3)
    gaps = [round(b - a, 3) for a, b in zip(attempts, attempts[1:])]
    assert all(g >= 0.15 for g in gaps), (
        f"retries {gaps}s apart: anything under the 162 ms p90 recovery "
        f"re-asks for packets already on their way back")
    assert attempts[-1] <= 0.35, (
        f"last attempt at {attempts[-1]}s leaves too little room for the "
        f"repeat to beat -max_delay 500000")


# --------------------------------------------------------------------------
# Interop: an independent RTCP implementation has to agree
# --------------------------------------------------------------------------

def test_an_independent_rtcp_parser_reads_it_as_a_generic_nack():
    # decode_nack_seqs is our own decoder, so a round trip through it would
    # still pass with a consistently wrong bitmask. aiortc is a separate
    # implementation of the same RFC; if it agrees, the camera will too.
    from aidot_cameras._vendor.aiortc.rtp import RtcpPacket

    seqs = [100, 101, 105, 116, 200]
    parsed, = RtcpPacket.parse(build_nack(0xAB12CD34, 0x11223344, seqs))
    assert parsed.fmt == 1
    assert parsed.ssrc == 0xAB12CD34
    assert parsed.media_ssrc == 0x11223344
    assert parsed.lost == seqs


def test_we_read_back_what_an_independent_implementation_builds():
    from aidot_cameras._vendor.aiortc.rtp import RtcpRtpfbPacket

    seqs = [100, 101, 105, 116, 200]
    theirs = RtcpRtpfbPacket(fmt=1, ssrc=0xAB12CD34, media_ssrc=0x11223344)
    theirs.lost = list(seqs)
    assert decode_nack_seqs(bytes(theirs)) == seqs


def test_one_report_is_capped_so_a_burst_cannot_flood_the_link():
    t = NackTracker(max_gap=1000, max_report=8)
    t.observe(1000, now=0.0)
    asked = t.observe(1100, now=0.01)
    assert len(asked) == 8
    assert asked == [1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008], (
        "the oldest missing packets are the ones the decoder needs first")
