"""Putting a TMMBR on the camera's RTCP path, off by default.

The bound itself is built and tested in test_tmmbr.py; this is the bridge side:
that it goes out the relay-aware sender, carries the SSRC the SRTP TX policy is
keyed on, and stays off until someone asks for it.

The relay point is not theoretical. The NACK path shipped broken for exactly
this reason -- written straight to the address media arrived from, which via
TURN is the relay, where a raw write is parsed as a malformed STUN message and
dropped, while the send still reported success. REMB still has that bug today
(it calls ``_cam_srtp_sock.sendto`` directly); it is latent only because
``REMB_TARGET_BPS`` defaults to 0. A new feedback message must not repeat it.
"""
import logging

import pytest

from aidot_cameras.camera.protocol import decode_tmmbr_bitrate
from aidot_cameras.camera.sdes_open import (
    _CAM_RTCP_SENDER_SSRC,
    _sdes_tmmbr_bps,
    _send_video_tmmbr,
)


class _Sess:
    """Stands in for the SRTCP session; marks what it protected."""

    def __init__(self):
        self.protected = []

    def protect_rtcp(self, raw):
        self.protected.append(raw)
        return b"SRTCP" + raw


def test_it_is_off_unless_asked(monkeypatch):
    monkeypatch.delenv("AIDOT_SDES_TMMBR_BPS", raising=False)
    assert _sdes_tmmbr_bps() is None
    monkeypatch.setenv("AIDOT_SDES_TMMBR_BPS", "0")
    assert _sdes_tmmbr_bps() is None
    monkeypatch.setenv("AIDOT_SDES_TMMBR_BPS", "800000")
    assert _sdes_tmmbr_bps() == 800_000


def test_an_unparseable_value_is_off_not_a_crash(monkeypatch):
    # Read while a session is running; a typo must not take a camera off air.
    monkeypatch.setenv("AIDOT_SDES_TMMBR_BPS", "800kbps")
    assert _sdes_tmmbr_bps() is None


def test_the_bound_reaches_the_sender_srtcp_protected():
    sent = []
    sess = _Sess()
    assert _send_video_tmmbr(sent.append, sess, 0xAB12CD34, 0x2222, 800_000)
    assert len(sent) == 1
    assert sent[0].startswith(b"SRTCP"), "an unprotected RTCP is dropped by the camera"
    assert decode_tmmbr_bitrate(sess.protected[0]) == 800_000


def test_it_still_sends_before_the_srtcp_session_exists():
    # Same rule the PLI and NACK follow: the session is built lazily on the
    # first PLI tick, and a plain-text packet in that window is dropped by the
    # camera rather than crashing us.
    sent = []
    assert _send_video_tmmbr(sent.append, None, 0xAB12CD34, 0x2222, 800_000)
    assert decode_tmmbr_bitrate(sent[0]) == 800_000


@pytest.mark.parametrize("bps", [None, 0, -1])
def test_no_bound_means_no_packet(bps):
    sent = []
    assert _send_video_tmmbr(sent.append, _Sess(), 1, 2, bps) is False
    assert sent == []


def test_a_failing_send_is_swallowed(caplog):
    # Runs inside the bridge's packet loop: taking the stream down to avoid a
    # dropped feedback packet is a bad trade.
    def _boom(_):
        raise OSError("network went away")

    with caplog.at_level(logging.DEBUG):
        assert _send_video_tmmbr(_boom, None, 1, 2, 800_000) is False


def test_the_call_site_uses_the_relay_aware_sender_and_the_keyed_ssrc():
    """Source guard: the unit tests above pass with the bridge untouched."""
    import pathlib

    src = (pathlib.Path(__file__).resolve().parents[1] / "aidot_cameras"
           / "camera" / "sdes_open.py").read_text()
    # Every occurrence that is not the definition is a call site.
    calls = [i for i in range(len(src))
             if src.startswith("_send_video_tmmbr(", i)
             and not src[:i].endswith("def ")]
    assert calls, "the bridge must actually send the TMMBR it can build"
    # Widen behind the call too: the sender is resolved into a local a few
    # lines above (_tmmbr_send = getattr(_bridge_fn, '_send_to_cam', None)).
    args = src[max(0, calls[0] - 900):calls[0] + 400]
    assert "_cam_srtp_sock.sendto" not in args, (
        "a raw socket write is silently inert on a TURN-relayed session -- "
        "use the bridge's relay-aware sender, as the NACK path does")
    assert "_send_to_cam" in args
    assert "_CAM_RTCP_SENDER_SSRC" in args, (
        "the SRTP TX policy is keyed on this SSRC; a TMMBR that disagrees "
        "with the PLI/RR/NACK is dropped by the camera")


def test_the_sender_ssrc_is_the_one_every_other_rtcp_uses():
    assert _CAM_RTCP_SENDER_SSRC == 0xAB12CD34


# -- Holding the bound back until mid-session ---------------------------------
#
# Sending from session start forces a BETWEEN-session comparison, and that
# design has now produced two wrong answers on this camera in one day: it read
# a codec split as a 2.1x bandwidth-cap win, and before that it read the
# encoder's own session-to-session drift as a working SD control.
#
# The only design that has ever produced a trustworthy number here is
# within-session -- window B against window A of the SAME session, which cannot
# be confounded by the codec or by the scene, because both windows share them.
# `--quality-arms` already measures that way; holding the TMMBR back until
# after window A lets it be measured on the same rig.

def test_the_delay_is_off_by_default(monkeypatch):
    from aidot_cameras.camera.sdes_open import _sdes_tmmbr_after_s

    monkeypatch.delenv("AIDOT_SDES_TMMBR_AFTER_S", raising=False)
    assert _sdes_tmmbr_after_s() == 0.0
    monkeypatch.setenv("AIDOT_SDES_TMMBR_AFTER_S", "18")
    assert _sdes_tmmbr_after_s() == 18.0
    monkeypatch.setenv("AIDOT_SDES_TMMBR_AFTER_S", "banana")
    assert _sdes_tmmbr_after_s() == 0.0


def test_nothing_is_due_before_the_first_video_packet():
    from aidot_cameras.camera.sdes_open import _tmmbr_ready

    # Elapsed media time, not wall time since open: a camera that takes 12 s to
    # wake would otherwise spend its whole window A already capped.
    assert _tmmbr_ready(None, now=1000.0, after_s=0.0) is False
    assert _tmmbr_ready(None, now=1000.0, after_s=18.0) is False


def test_with_no_delay_it_is_due_as_soon_as_media_starts():
    from aidot_cameras.camera.sdes_open import _tmmbr_ready

    assert _tmmbr_ready(100.0, now=100.0, after_s=0.0) is True


def test_the_delay_is_measured_from_the_first_video_packet():
    from aidot_cameras.camera.sdes_open import _tmmbr_ready

    assert _tmmbr_ready(100.0, now=117.9, after_s=18.0) is False
    assert _tmmbr_ready(100.0, now=118.0, after_s=18.0) is True
    assert _tmmbr_ready(100.0, now=200.0, after_s=18.0) is True


def test_the_bridge_gates_the_send_on_it():
    """Source guard: the helper is inert unless the send site consults it."""
    import pathlib

    src = (pathlib.Path(__file__).resolve().parents[1] / "aidot_cameras"
           / "camera" / "sdes_open.py").read_text()
    i = src.index("_tmmbr_send = getattr(_bridge_fn, '_send_to_cam', None)")
    window = src[i:i + 900]
    assert "_tmmbr_ready(" in window, (
        "the TMMBR cadence must consult _tmmbr_ready, or AIDOT_SDES_TMMBR_AFTER_S "
        "does nothing and the measurement is between-session again")
    assert "_first_video_ts" in window


def test_the_switches_are_resolved_once_per_session_not_per_packet():
    """`os.environ` must not be read from the bridge's packet loop.

    The RTCP cadence lives inside the bridge's `while True:` select loop, which
    turns over at packet rate -- roughly 300x/s per camera. `_nack_on` is
    already resolved once per session for exactly this reason, with the comment
    "the per-packet path runs ~300x/s per camera and os.environ.get costs
    ~250ns of it". Two env reads per iteration for TMMBR would undo that on the
    same loop.

    Resolving at session start also gives the knobs the same semantics as the
    NACK switch: a flip takes effect on the next stream open, not mid-session.
    """
    import pathlib

    src = (pathlib.Path(__file__).resolve().parents[1] / "aidot_cameras"
           / "camera" / "sdes_open.py").read_text()

    # Resolved where _nack_on is, at the first video packet.
    setup = src[src.index("_bridge_fn._nack_on = _sdes_nack_enabled()"):][:600]
    assert "_sdes_tmmbr_bps()" in setup, (
        "resolve the TMMBR target once per session, beside _nack_on")
    assert "_sdes_tmmbr_after_s()" in setup

    # And the cadence reads the resolved value rather than the environment.
    i = src.index("_tmmbr_send = getattr(_bridge_fn, '_send_to_cam', None)")
    cadence = src[i - 600:i + 900]
    assert "_sdes_tmmbr_bps()" not in cadence, (
        "the cadence must not call os.environ; it runs on the packet loop")
    assert "_sdes_tmmbr_after_s()" not in cadence
