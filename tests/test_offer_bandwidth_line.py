"""The offer can carry a receive-bandwidth ceiling, and by default does not.

Every other way of asking this camera to slow down has been eliminated on
evidence (see AIDOT-FINDINGS-ptz-mse-packet-loss-2026-08-23.md, "Bitrate
control: what it is NOT"):

* `transport-cc` is advertised in the camera's `rtcp-fb` and is unusable -- it
  never stamps an RTP header extension, so 31669 video packets carry nothing a
  transport-cc report could reference;
* `SETSTREAMCTRL` is acked and inert, byte-for-byte identical to the vendor
  app's, at the same point mid-session, with the framing gap closed;
* the vendor app sends no `b=` line of its own, so this is not app parity.

`b=AS` (RFC 4566 s5.8, kilobits per second) is the remaining standards-defined
way for a RECEIVER to cap a sender, and the only one of the five entirely ours
to emit.

**It was measured, and it does nothing.** Ten sessions on the A001064,
2026-08-23. Kept at default OFF rather than deleted, following REMB, which also
ships off for having been measured to do nothing.

The measurement is worth keeping because it nearly produced a false positive.
Run ABAB it looked like a decisive 2.1x win:

    b=AS:800              1635, 1628   mean 1631
    control (no b= line)   766,  769   mean  768

Re-run AABB it fell apart, and sorting all ten sessions by the payload type
they negotiated explained every number without reference to the knob:

    H264 (pt=96)   1597, 1604, 1623, 1627, 1635, 1643, 1685 kbps
    H265 (pt=97)    766,  769,  774 kbps

Perfect separation. The camera answered H.265 on three of ten opens on its own,
and both H.265 sessions in the ABAB run happened to fall in the control arm.
The b=AS arm itself contains a 1604 and a 774.

Two lessons for anyone re-running arms on this camera. **Never alternate
strictly** -- ABAB makes "arm" indistinguishable from "session index". And
**record the negotiated payload type per session and check it before comparing
rates**, because on this camera the codec is worth 2:1 and it is not always the
one we asked for.

Default OFF also because an untested ceiling applied fleet-wide would risk the
four cameras that stream fine today -- the A000088s run at 0.43 Mbps and have
never lost a packet to load.
"""
import pytest

from aidot_cameras.camera.sdes_open import _offer_bandwidth_line


def test_there_is_no_bandwidth_line_by_default():
    # The shipped offer must be byte-identical to the one measured against the
    # whole fleet. Off means absent, not "b=AS:0" (which RFC 4566 reads as a
    # request for zero bandwidth, i.e. the opposite of unlimited).
    assert _offer_bandwidth_line(None) == ""
    assert _offer_bandwidth_line(0) == ""


def test_a_ceiling_becomes_a_b_as_line_in_kbps():
    assert _offer_bandwidth_line(800) == "b=AS:800\r\n"
    assert _offer_bandwidth_line(1500) == "b=AS:1500\r\n"


def test_the_value_is_an_integer_count_of_kilobits():
    # RFC 4566: <bandwidth> is a positive integer. A float would emit
    # "b=AS:1200.0", which a linear-parsing firmware may reject outright --
    # and this camera has already been observed discarding an offer over a
    # single misplaced attribute (the a=crypto ordering note in sdes_open).
    assert _offer_bandwidth_line(1200.0) == "b=AS:1200\r\n"
    assert _offer_bandwidth_line("900") == "b=AS:900\r\n"


@pytest.mark.parametrize("bad", [-1, -1000, "abc", "", object()])
def test_nonsense_is_dropped_rather_than_emitted(bad):
    # A bad env var must not be able to corrupt the offer. The whole session
    # dies if the camera rejects the SDP, and a typo in an experiment knob is
    # not worth a fleet-wide outage.
    assert _offer_bandwidth_line(bad) == ""


def test_the_knob_is_read_from_the_environment(monkeypatch):
    from aidot_cameras.camera.sdes_open import _sdes_offer_bandwidth_kbps

    monkeypatch.delenv("AIDOT_SDES_OFFER_BANDWIDTH_KBPS", raising=False)
    assert _sdes_offer_bandwidth_kbps() is None

    monkeypatch.setenv("AIDOT_SDES_OFFER_BANDWIDTH_KBPS", "800")
    assert _sdes_offer_bandwidth_kbps() == 800

    # An unparseable value is off, not a crash at session open.
    monkeypatch.setenv("AIDOT_SDES_OFFER_BANDWIDTH_KBPS", "not-a-number")
    assert _sdes_offer_bandwidth_kbps() is None


def test_the_line_sits_in_the_video_section_after_the_connection_line():
    """Binds the helper to the real offer, which is built in a long f-string.

    Without this the unit tests above pass with the production offer
    untouched -- the same failure mode the AVIO dSeq test has a source guard
    for. RFC 4566 fixes the order within a media section as m=, i=, c=, b=,
    so a b= line emitted anywhere else is not a valid offer.
    """
    import pathlib
    import re

    src = (pathlib.Path(__file__).resolve().parents[1] / "aidot_cameras"
           / "camera" / "sdes_open.py").read_text()
    m = re.search(
        r'\+ f"m=video \{_offer_video_port\} RTP/SAVPF \{_video_pt_list\}\\r\\n"\s*\n'
        r'\s*f"c=IN IP4 \{_offer_video_ip\}\\r\\n"\s*\n'
        r'\s*\+ _offer_bandwidth_line\(',
        src)
    assert m, (
        "the camera-facing offer must emit the bandwidth line immediately "
        "after the video c= line; RFC 4566 orders a media section m=, i=, "
        "c=, b=, and this camera parses linearly")


def test_applying_a_ceiling_leaves_a_receipt_in_the_log():
    """A knob with no receipt cannot tell a result from a coincidence.

    The offer is never logged -- the SDP that reaches the log is the camera's
    answer -- so a run has no way to show the ceiling was applied. This is the
    same trap the codec-order pin fell into, where two sessions read as a
    confirmed effect before the missing receipt showed the pin had never
    reached the SDP at all.
    """
    import pathlib
    import re

    src = (pathlib.Path(__file__).resolve().parents[1] / "aidot_cameras"
           / "camera" / "sdes_open.py").read_text()
    m = re.search(r"if _bw_kbps and _status:\s*\n"
                  r'\s*_status\(f"[^"]*b=AS:\{_bw_kbps\}"\)', src)
    assert m, ("applying a receive-bandwidth ceiling must emit a status "
               "receipt naming the value that reached the offer")
    assert src.count("_sdes_offer_bandwidth_kbps()") == 2, (
        "the knob must be read ONCE per offer (its definition plus one call "
        "site); a second read lets the receipt disagree with the SDP actually "
        "sent, which is the false-receipt trap the receipt exists to close")
