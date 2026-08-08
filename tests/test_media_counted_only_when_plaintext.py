"""Only count media the consumer can actually use.

``_media_progress`` and ``_media_counts`` are the ONLY in-process evidence that
media flowed on the SDES path: the library decodes nothing itself (ffmpeg owns
the media), so ``on_frame`` never fires.  Everything downstream reads them --
``SdesSession.last_media_monotonic`` feeds ``SdesSession.is_stalled``, which the
keepalive loop uses to tear a dead session down; ``_healthy`` in the keepalive
loop is literally ``last_media_monotonic > 0.0``, and it resets
``_no_media_streak``, which is what ``_should_abandon_keepalive`` counts.

The bridge's SRTP forward site incremented all three unconditionally, right
after ``sendto``.  ``_fwd_pkt`` starts as the raw inbound packet and is rebound
only when ``unprotect`` succeeds, so on a plain-RTP camera it stays CIPHERTEXT
whenever there is no SRTP receive session (pylibsrtp is in the optional
``webrtc`` extra) or whenever ``unprotect`` raised -- that handler logs at most
eight lines and falls through.  ffmpeg is configured RTP/AVP for those cameras
and discards every such packet, so the viewer sees black while the counters
climb: ``is_stalled`` never trips, ``_healthy`` stays True, the streak keeps
resetting, and the abandon ceiling can never fire.  A session reports healthy
forever.

The condition is NOT "did we decrypt it".  It is "is ``_fwd_pkt`` plaintext by
the time ffmpeg reads it", and who owes the decryption depends on the camera:

* ``_use_plain_rtp`` models (the ``_PLAIN_RTP_MODELS`` substring allowlist) get
  an ffmpeg SDP of ``RTP/AVP`` with no ``a=crypto``, so the BRIDGE owes the
  decryption and ciphertext is a non-delivery.
* Every other SDES camera -- ``is_sdes_camera`` is a cloud property,
  ``enableSdes == '1'``, a different set from the model allowlist -- gets
  ``RTP/SAVP`` plus ``a=crypto`` and FFMPEG owes the decryption.  There the
  bridge forwards ciphertext by design, ``_decrypted`` is always False, and
  that forward is real delivered media.

Gating on ``_decrypted`` alone would therefore zero the counters for the whole
RTP/SAVP path: ``last_media_monotonic`` would stay 0.0, ``is_stalled`` would
trip at the grace deadline on a stream that is working, and
``_should_abandon_keepalive`` would eventually stop the keepalive for a healthy
camera.  Hence the two-term predicate.
"""
import inspect
import re

import aidot_cameras.camera.sdes_open as sdes_open
from aidot_cameras.camera.sdes_open import _should_count_media

_SRC = inspect.getsource(sdes_open)


# --------------------------------------------------------------------- #
# Pure helper truth table
# --------------------------------------------------------------------- #

def test_decrypted_plain_rtp_packet_counts():
    """The bridge unprotected it; ffmpeg gets plaintext on an RTP/AVP SDP."""
    assert _should_count_media(True, True) is True


def test_undecrypted_plain_rtp_packet_does_not_count():
    """The bug: ciphertext to an RTP/AVP ffmpeg, discarded, counted anyway."""
    assert _should_count_media(False, True) is False


def test_savp_packet_counts_even_though_the_bridge_did_not_decrypt_it():
    """ffmpeg owns the SRTP on this path -- forwarding ciphertext IS delivery.

    This is the case a bare ``if _decrypted:`` would have broken.
    """
    assert _should_count_media(False, False) is True


def test_savp_packet_counts_when_something_decrypted_it_anyway():
    assert _should_count_media(True, False) is True


def test_returns_a_bool_not_a_truthy_value():
    for d in (True, False):
        for p in (True, False):
            assert isinstance(_should_count_media(d, p), bool)


# --------------------------------------------------------------------- #
# Source anchor: the production forward site must actually call it.
#
# The bridge is one long closure inside _open_sdes_stream_impl and cannot be
# invoked standalone, so the mirror below is only a mirror.  These checks are
# what bind the helper to the real code -- without them the tests above would
# still pass with the production gate deleted.
# --------------------------------------------------------------------- #

_FWD_COUNTER = re.compile(
    r"if _should_count_media\((?P<args>[^)]*)\):\s*\n"
    r"\s*_media_progress\[0\] = _time_br\.monotonic\(\)\s*\n"
    r"\s*_media_counts\[0\] \+= 1\s*\n"
    r"\s*_media_counts\[1\] \+= len\(_fwd_pkt\)"
)


def test_the_srtp_forward_site_is_gated_on_the_helper():
    m = _FWD_COUNTER.search(_SRC)
    assert m, (
        "the bridge's SRTP forward site does not gate its media counters on "
        "_should_count_media - every packet it forwards counts as delivered "
        "media, including ciphertext ffmpeg discards, so is_stalled never "
        "trips and the session reports healthy while the viewer sees black"
    )
    args = m.group("args")
    assert "_decrypted" in args and "_use_plain_rtp" in args, (
        "the gate must take BOTH terms: _decrypted alone zeroes the counters "
        f"for every RTP/SAVP camera, where ffmpeg owns the SRTP. Got: {args!r}"
    )


def test_only_the_two_known_counter_sites_exist():
    """Every place that stamps media-liveness must be one we have reasoned about.

    ``_media_progress[0] = _time_br.monotonic()`` is the stamp ``is_stalled``
    and ``_healthy`` read, so a third one appearing anywhere in the bridge is a
    third claim that media flowed - and this whole defect was one such claim
    being made for packets ffmpeg discards.  There are exactly two: the TUTK
    audio forward (plaintext it decrypted itself, ungated by design) and the
    SRTP forward the test above pins to the gate.  A new one fails here rather
    than silently re-opening the hole.
    """
    stamps = re.findall(r"_media_progress\[0\] = _time_br\.monotonic\(\)", _SRC)
    assert len(stamps) == 2, (
        f"expected exactly 2 media-liveness stamps in the bridge, found "
        f"{len(stamps)} - a new one must be shown to fire only for media the "
        "consumer can actually use, then this count updated"
    )
    fwd_sites = re.findall(r"_media_counts\[1\] \+= len\(_fwd_pkt\)", _SRC)
    assert len(fwd_sites) == 1, (
        f"expected exactly one _fwd_pkt counter site, found {len(fwd_sites)} - "
        "re-point the gate test above and check each one is gated"
    )


def test_the_tutk_counter_site_is_deliberately_left_ungated():
    """Scope pin: the TUTK audio forward sends bytes it already decrypted.

    That site hands ffmpeg ``_rtp_hdr + _pd_plain`` -- plaintext PCMA it
    produced itself, on a path that reaches ``continue`` long before
    ``_decrypted`` exists.  Widening the gate to cover it would under-report a
    camera whose audio arrives over TUTK, which is a bug this repo has already
    fixed once.
    """
    assert "_media_counts[1] += len(_rtp_hdr) + len(_pd_plain)" in _SRC, (
        "the TUTK audio counter site moved or was gated - it forwards "
        "plaintext and must keep counting"
    )


# --------------------------------------------------------------------- #
# Behavioural mirror: the consequence, driven through the real helper.
# --------------------------------------------------------------------- #

class _FakeSession:
    """The slice of SdesSession the watchdog reads, over shared bridge state."""

    def __init__(self, media_progress, media_counts):
        self._media_progress = media_progress
        self._media_counts = media_counts

    @property
    def last_media_monotonic(self):
        return self._media_progress[0]

    def media_stats(self):
        return {
            "packets": self._media_counts[0],
            "bytes": self._media_counts[1],
            "last_media_monotonic": self._media_progress[0],
        }


def _forward(packets, plain_rtp, now=100.0):
    """Mirror of the bridge's forward step, using the real predicate.

    ``packets`` is a list of ``(payload, decrypted)``.  Everything is forwarded
    -- the fix does not change loop control flow -- only the counting is gated.
    """
    media_progress = [0.0]
    media_counts = [0, 0]
    forwarded = []
    for payload, decrypted in packets:
        forwarded.append(payload)  # sendto()
        if _should_count_media(decrypted, plain_rtp):
            media_progress[0] = now
            media_counts[0] += 1
            media_counts[1] += len(payload)
    return forwarded, _FakeSession(media_progress, media_counts)


def test_a_stream_that_never_decrypts_is_reported_stalled():
    """The whole point: black picture must become a torn-down session.

    Grace is 60 s and nothing decrypted, so ``last_media`` stays 0.0 and the
    keepalive's own health test (``last_media_monotonic > 0.0``) is False.
    """
    _, session = _forward([(b"x" * 200, False)] * 500, plain_rtp=True)
    assert session.last_media_monotonic == 0.0
    assert session.media_stats()["packets"] == 0
    assert sdes_open.SdesSession.is_stalled(
        session.last_media_monotonic, started_at=0.0, now=61.0) is True
    assert (session.last_media_monotonic > 0.0) is False  # _healthy


def test_undecryptable_packets_are_still_forwarded():
    """Prefer the flag over a ``continue``.  Forwarding ciphertext ffmpeg
    discards is inert; changing loop control flow on the whole SDES fleet is
    not."""
    forwarded, _ = _forward([(b"x" * 200, False)] * 3, plain_rtp=True)
    assert len(forwarded) == 3


def test_a_decrypting_stream_stays_healthy():
    _, session = _forward([(b"x" * 200, True)] * 10, plain_rtp=True)
    assert session.media_stats() == {
        "packets": 10, "bytes": 2000, "last_media_monotonic": 100.0}
    assert sdes_open.SdesSession.is_stalled(
        session.last_media_monotonic, started_at=0.0, now=101.0) is False


def test_a_savp_stream_stays_healthy_without_the_bridge_decrypting():
    """The regression the second term prevents: a working RTP/SAVP camera."""
    _, session = _forward([(b"x" * 200, False)] * 10, plain_rtp=False)
    assert session.media_stats()["packets"] == 10
    assert session.last_media_monotonic == 100.0
    assert sdes_open.SdesSession.is_stalled(
        session.last_media_monotonic, started_at=0.0, now=101.0) is False


def test_a_partly_decrypting_stream_counts_only_what_decrypted():
    """Intermittent decrypt failure: the counters track usable media only."""
    packets = [(b"x" * 100, i % 2 == 0) for i in range(10)]
    _, session = _forward(packets, plain_rtp=True)
    assert session.media_stats()["packets"] == 5
    assert session.media_stats()["bytes"] == 500
