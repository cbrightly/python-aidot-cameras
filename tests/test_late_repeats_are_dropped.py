"""A retransmission that missed the decoder's window must be dropped, not forwarded.

Seen in production once NACK was live (2026-08-23):

    [in#0/sdp] RTP: dropping old packet received too late

and on the wire, repeats arriving with their sequence number 4 to 115 behind
the newest, which ffmpeg then reports as `Non-monotonic DTS` at the output.

A repeat that beats ffmpeg's `-max_delay` is reordered back into place and
repairs the frame -- that is the whole feature. One that arrives after it
cannot: ffmpeg has already emitted the damaged frame, so forwarding the repeat
only inserts an out-of-order packet, and the muxer clamps the output DTS. The
packet is pure cost by the time it lands.

The tracker already knows when each loss was first noticed, so it can say how
old an arriving repeat is; the bridge drops the ones past the budget.
"""
from aidot_cameras.camera.protocol import NackTracker


def test_a_repeat_that_arrives_promptly_is_not_late():
    t = NackTracker()
    t.observe(1000, now=0.0)
    t.observe(1003, now=0.0)          # 1001, 1002 now missing
    assert t.repeat_age(1001, now=0.05) == 0.05


def test_a_repeat_that_missed_the_window_reports_its_full_age():
    t = NackTracker()
    t.observe(1000, now=0.0)
    t.observe(1003, now=0.0)
    assert t.repeat_age(1002, now=0.9) == 0.9


def test_a_packet_that_was_never_missing_is_not_a_repeat():
    t = NackTracker()
    t.observe(1000, now=0.0)
    t.observe(1001, now=0.01)
    assert t.repeat_age(1001, now=0.02) is None


def test_an_unknown_sequence_number_is_not_a_repeat():
    t = NackTracker()
    t.observe(1000, now=0.0)
    assert t.repeat_age(4242, now=0.5) is None


def test_the_age_is_measured_from_when_the_loss_was_FIRST_noticed():
    # Not from the last NACK attempt: what matters is how long the decoder has
    # been waiting, which is what ffmpeg's max_delay is counting too.
    t = NackTracker(retry_after=0.1)
    t.observe(1000, now=0.0)
    t.observe(1003, now=0.0)
    t.observe(1004, now=0.2)          # a retry goes out here
    assert t.repeat_age(1001, now=0.3) == 0.3


# --- the bridge seam ------------------------------------------------------- #

from aidot_cameras.camera.sdes_open import (  # noqa: E402
    _SERVE_REORDER_BUDGET_S,
    _video_nack_seqs,
    _video_repeat_too_late,
)


class _Holder:
    """Stand-in for the bridge function the tracker is cached on."""


def _seed(h, first, then, now=0.0):
    """Make the numbers between `first` and `then` outstanding on h's tracker.

    Must go through _video_nack_seqs: that is what OBSERVES packets and records
    the gap. _video_repeat_too_late only reads. Seeding through the reader left
    the tracker empty and made two of the assertions below pass vacuously.
    """
    _video_nack_seqs(h, first, now, enabled=True)
    _video_nack_seqs(h, then, now, enabled=True)


def test_a_repeat_inside_the_window_is_still_forwarded():
    h = _Holder()
    _seed(h, 1000, 1003)
    # guard: the seeding really did leave 1001 outstanding, so a False here
    # means "inside the window" and not "the tracker never saw it".
    assert h._nack_tracker.repeat_age(1001, 0.2) == 0.2
    assert _video_repeat_too_late(h, 1001, now=0.2) is False


def test_a_repeat_past_the_window_is_dropped():
    # ffmpeg has already emitted the damaged frame; this packet can only
    # arrive out of order and make the muxer clamp its output DTS.
    h = _Holder()
    _seed(h, 1000, 1003)
    assert _video_repeat_too_late(h, 1002, now=1.2) is True


def test_an_ordinary_in_order_packet_is_never_dropped():
    h = _Holder()
    _video_nack_seqs(h, 1000, 0.0, enabled=True)
    for seq in range(1001, 1010):
        _video_nack_seqs(h, seq, 1.0, enabled=True)
        assert _video_repeat_too_late(h, seq, now=1.0) is False


def test_the_budget_tracks_ffmpegs_reorder_window():
    # The serve runs -max_delay 500000. A packet later than that is discarded
    # by ffmpeg anyway, so the budget must not exceed it.
    assert 0 < _SERVE_REORDER_BUDGET_S <= 0.5


def test_the_tracker_is_shared_with_the_nack_decision():
    # It has to be the SAME tracker: a second one would have no idea which
    # packets were ever missing, so nothing would ever look late.
    h = _Holder()
    _video_nack_seqs(h, 1000, 0.0, enabled=True)
    seen_by_nack = h._nack_tracker
    _video_repeat_too_late(h, 1001, now=0.0)
    assert h._nack_tracker is seen_by_nack
