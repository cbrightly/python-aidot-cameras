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

The bridge is the only decryptor: every SDES model the open accepts is in
``_PLAIN_RTP_MODELS``, and the ffmpeg SDP is ``RTP/AVP`` with no ``a=crypto``.
So the gate is ``_decrypted`` alone.  (An ``RTP/SAVP`` branch, where ffmpeg
owed the decryption and the bridge forwarded ciphertext as real media, needed a
second term; that branch is gone and the open refuses any other SDES model.)
"""

import ast

import aidot_cameras.camera.sdes_open as sdes_open
from tests.ast_guards import enclosing, names_in, parents, sdes_bridge


# --------------------------------------------------------------------- #
# Source anchor: the production forward site must actually be gated.
#
# The bridge is one long closure inside _open_sdes_stream_impl and cannot be
# invoked standalone, so the mirror below is only a mirror.  These checks are
# what bind it to the real code -- without them the mirror would still pass
# with the production gate deleted.
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


def test_the_srtp_forward_site_is_gated_on_decryption():
    """The SRTP forward's counters run only when the bridge decrypted what it
    forwarded."""
    fwd = [
        n
        for n in ast.walk(sdes_bridge())
        if isinstance(n, ast.AugAssign)
        and ast.unparse(n.target) == "_media_counts[1]"
        and "_fwd_pkt" in names_in(n.value)
    ]
    assert len(fwd) == 1, "expected one _fwd_pkt counter site"
    assert any("_decrypted" in names_in(t) for t in _gates(fwd[0])), (
        "the bridge's SRTP forward counts every packet it forwards, including "
        "ciphertext ffmpeg discards - is_stalled never trips"
    )


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
    assert not any("_decrypted" in names_in(t) for t in _gates(tutk[0])), (
        "the TUTK audio counter must keep counting"
    )


# --------------------------------------------------------------------- #
# Behavioural mirror: the consequence of the gate.
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


def _forward(packets, now=100.0):
    """Mirror of the bridge's forward step.

    ``packets`` is a list of ``(payload, decrypted)``.  Everything is forwarded
    -- the fix does not change loop control flow -- only the counting is gated.
    """
    media_progress = [0.0]
    media_counts = [0, 0]
    forwarded = []
    for payload, decrypted in packets:
        forwarded.append(payload)  # sendto()
        if decrypted:
            media_progress[0] = now
            media_counts[0] += 1
            media_counts[1] += len(payload)
    return forwarded, _FakeSession(media_progress, media_counts)


def test_a_stream_that_never_decrypts_is_reported_stalled():
    """The whole point: black picture must become a torn-down session.

    Grace is 60 s and nothing decrypted, so ``last_media`` stays 0.0 and the
    keepalive's own health test (``last_media_monotonic > 0.0``) is False.
    """
    _, session = _forward([(b"x" * 200, False)] * 500)
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
    forwarded, _ = _forward([(b"x" * 200, False)] * 3)
    assert len(forwarded) == 3


def test_a_decrypting_stream_stays_healthy():
    _, session = _forward([(b"x" * 200, True)] * 10)
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


def test_a_partly_decrypting_stream_counts_only_what_decrypted():
    """Intermittent decrypt failure: the counters track usable media only."""
    packets = [(b"x" * 100, i % 2 == 0) for i in range(10)]
    _, session = _forward(packets)
    assert session.media_stats()["packets"] == 5
    assert session.media_stats()["bytes"] == 500
