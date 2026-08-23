"""The NACK must not pile load onto a link that is losing packets from load.

Measured on the reference A001064, same camera, same position, one day, wire
captures:

    2.21 Mbps -> 0.37% loss
    2.59 Mbps -> 0.70%
    3.07 Mbps -> 0.94%
    3.90 Mbps -> 7.25%

Loss rises monotonically with offered load. That rules out fixed signal
strength as the explanation -- position and antenna did not change -- and makes
this link bandwidth-limited, somewhere above ~2 Mbps.

Which makes a retransmission request a dangerous thing to send carelessly: it
asks the camera to put MORE bytes on the link that is already dropping them.
The failed 100 ms x 4 retry experiment demonstrated the feedback loop directly
-- recovery fell from 99.2% to 73.6% because the extra requests cost more than
they recovered.

The remaining hole is burst behaviour. A single wide gap has every pending
sequence number eligible at once with `last is None`, so the tracker emits a
full report on each of the next several received packets: measured, a 250-packet
gap produced 12 NACK sends across 31 consecutive packets. That is a burst of
requests fired at exactly the moment the link is worst.

A minimum interval between SENDS closes it, and costs nothing in the steady
state: at the measured 0.7% loss there are about two losses a second, so
reports are already far apart. During a burst the interval coalesces sequence
numbers into fewer, fuller reports -- the same cadence gating the PLI and REMB
paths already use.
"""
from aidot_cameras.camera.protocol import NackTracker


def _burst(t, width, now=0.0):
    """One wide gap: observe 1000, then jump `width` ahead."""
    t.observe(1000, now=now)
    t.observe(1000 + width, now=now)


def test_a_wide_gap_does_not_fire_a_report_on_every_following_packet():
    t = NackTracker(max_gap=400, min_report_interval=0.02)
    _burst(t, 250)
    sends = 0
    seq = 1251
    now = 0.0
    for _ in range(30):                 # 30 consecutive in-order packets
        if t.observe(seq, now=now):
            sends += 1
        seq += 1
        now = round(now + 0.003, 4)     # ~330 packets/s, as measured
    assert sends <= 5, (
        f"{sends} NACK sends across 30 packets: that is a request burst aimed "
        f"at the moment the link is least able to carry it")


def test_the_interval_coalesces_rather_than_discards():
    # Suppressed numbers must still go out in a later report -- the point is to
    # send fewer, fuller reports, not to ask for less.
    t = NackTracker(max_gap=400, min_report_interval=0.02)
    _burst(t, 60)
    asked = set()
    seq, now = 1061, 0.0
    for _ in range(40):
        asked.update(t.observe(seq, now=now))
        seq += 1
        now = round(now + 0.01, 4)
    assert len(asked) >= 55, f"only {len(asked)} of the 59 lost numbers were asked for"


def test_the_steady_state_is_untouched():
    # ~2 losses a second is what 0.7% at 300 pkt/s looks like. Reports that far
    # apart must never be delayed: the measured-good 150ms x 3 schedule depends
    # on the first request going out immediately.
    t = NackTracker(min_report_interval=0.02)
    t.observe(1000, now=0.0)
    assert t.observe(1002, now=0.0) == [1001], "the first request must not be delayed"

    # A later, isolated loss on a tracker with nothing outstanding must also go
    # out at once. (Deliberately a fresh tracker: with 1001 still pending it
    # would be re-requested first and the throttle would correctly fire -- that
    # is the retry schedule doing its job, not the steady state.)
    u = NackTracker(min_report_interval=0.02)
    u.observe(2000, now=10.0)
    assert u.observe(2002, now=10.0) == [2001]


def test_the_default_is_short_enough_to_be_invisible_to_recovery():
    # Recovery is 45 ms at the median against a 500 ms budget; a coalescing
    # delay has to be small next to that or it eats the thing it protects.
    assert 0 < NackTracker().min_report_interval <= 0.03
