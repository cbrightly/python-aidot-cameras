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

import ast

import aidot_cameras.camera.sdes_open as sdes_open
from aidot_cameras.camera.sdes_open import _should_count_media
from tests.ast_guards import calls_to, enclosing, names_in, parents, sdes_bridge


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


def _media_stamps():
    """Assignments of the media-liveness stamp in the bridge."""
    return [
        n
        for n in ast.walk(sdes_bridge())
        if isinstance(n, ast.Assign)
        and any(ast.unparse(t) == "_media_progress[0]" for t in n.targets)
    ]


def _gates(node):
    """The `if` tests above ``node`` in the bridge."""
    return [p.test for p in enclosing(node, parents(sdes_bridge()), ast.If)]


def test_the_srtp_forward_site_is_gated_on_the_helper():
    """The SRTP forward's counters run only when _should_count_media says the
    consumer can use what was forwarded - with BOTH terms: _decrypted alone
    zeroes the counters for every RTP/SAVP camera, where ffmpeg owns SRTP."""
    fwd = [
        n
        for n in ast.walk(sdes_bridge())
        if isinstance(n, ast.AugAssign)
        and ast.unparse(n.target) == "_media_counts[1]"
        and "_fwd_pkt" in names_in(n.value)
    ]
    assert len(fwd) == 1, "expected one _fwd_pkt counter site"
    calls = [c for t in _gates(fwd[0]) for c in calls_to(t, "_should_count_media")]
    assert calls, (
        "the bridge's SRTP forward counts every packet it forwards, including "
        "ciphertext ffmpeg discards - is_stalled never trips"
    )
    assert {"_decrypted", "_use_plain_rtp"} <= names_in(calls[0])


def test_only_the_two_known_counter_sites_exist():
    """Every media-liveness stamp is a claim that media flowed. There are
    exactly two: the TUTK audio forward (plaintext it decrypted itself) and
    the gated SRTP forward. A third fails here rather than reopening the hole."""
    assert len(_media_stamps()) == 2


def test_the_tutk_counter_site_is_deliberately_left_ungated():
    """The TUTK audio forward hands ffmpeg plaintext it produced itself, on a
    path that never computes _decrypted; gating it would under-report a camera
    whose audio arrives over TUTK."""
    tutk = [
        n
        for n in ast.walk(sdes_bridge())
        if isinstance(n, ast.AugAssign)
        and ast.unparse(n.target) == "_media_counts[1]"
        and "_pd_plain" in names_in(n.value)
    ]
    assert len(tutk) == 1, "the TUTK audio counter site moved"
    assert not [
        c for t in _gates(tutk[0]) for c in calls_to(t, "_should_count_media")
    ], "the TUTK audio counter must keep counting"


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
    assert (
        sdes_open.SdesSession.is_stalled(
            session.last_media_monotonic, started_at=0.0, now=61.0
        )
        is True
    )
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
        "packets": 10,
        "bytes": 2000,
        "last_media_monotonic": 100.0,
    }
    assert (
        sdes_open.SdesSession.is_stalled(
            session.last_media_monotonic, started_at=0.0, now=101.0
        )
        is False
    )


def test_a_savp_stream_stays_healthy_without_the_bridge_decrypting():
    """The regression the second term prevents: a working RTP/SAVP camera."""
    _, session = _forward([(b"x" * 200, False)] * 10, plain_rtp=False)
    assert session.media_stats()["packets"] == 10
    assert session.last_media_monotonic == 100.0
    assert (
        sdes_open.SdesSession.is_stalled(
            session.last_media_monotonic, started_at=0.0, now=101.0
        )
        is False
    )


def test_a_partly_decrypting_stream_counts_only_what_decrypted():
    """Intermittent decrypt failure: the counters track usable media only."""
    packets = [(b"x" * 100, i % 2 == 0) for i in range(10)]
    _, session = _forward(packets, plain_rtp=True)
    assert session.media_stats()["packets"] == 5
    assert session.media_stats()["bytes"] == 500
