"""SPS/PPS must only be cached from a packet that actually decrypted.

The bridge forwards `_fwd_pkt`, which starts as the raw inbound packet and is
rebound only when `unprotect` succeeds. Two reachable paths leave it as
ciphertext: no SRTP receive session (pylibsrtp lives in the optional `webrtc`
extra, so a base install has none), and an `unprotect` that raised - the handler
logs at most 8 lines and falls through without a flag.

The capture that follows was gated only on "this is video" and "not done yet".
SRTP leaves the RTP header in clear, so the payload offset comes out right and
random ciphertext is handed to the NAL demux; `payload[0] & 0x1F` draws 7 and 8
at roughly 1/32 each, so on a 30 fps stream both turn up within seconds.

That matters more than a bad frame because the result is PERSISTED:
`_save_sprop` writes `~/.config/aidot/sprop/<devid>.sprop`, every later session
injects it as `sprop-parameter-sets=`, installing pylibsrtp afterwards does not
clear it, and the first correct capture then differs from the cache and trips
the `.unstable` marker - which disables injection for that camera for good.

So the gate has to include "did this packet decrypt", and the answer must be
false unless something actually decrypted it.
"""
import os
import sys

import pytest

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.sdes_open import _should_capture_sprop  # noqa: E402


def test_captures_from_a_decrypted_video_packet():
    assert _should_capture_sprop("video", decrypted=True, sprop_done=False) is True


def test_does_not_capture_from_an_undecrypted_packet():
    """The bug: this returned True and wrote ciphertext into the sprop cache."""
    assert _should_capture_sprop("video", decrypted=False, sprop_done=False) is False


def test_does_not_capture_once_done():
    assert _should_capture_sprop("video", decrypted=True, sprop_done=True) is False


@pytest.mark.parametrize("kind", ["audio", "", "application", None])
def test_only_video_carries_parameter_sets(kind):
    assert _should_capture_sprop(kind, decrypted=True, sprop_done=False) is False


def test_undecrypted_beats_every_other_condition():
    """Whatever else is true, ciphertext must never reach the cache - this is
    the property that keeps a poisoned file off disk."""
    for kind in ("video", "audio"):
        for done in (True, False):
            assert _should_capture_sprop(kind, decrypted=False, sprop_done=done) is False


def test_returns_a_bool_not_a_truthy_value():
    """It guards a write to a file that outlives the process; a caller reading
    this as a tri-state would be a bad surprise."""
    for d in (True, False):
        assert isinstance(_should_capture_sprop("video", decrypted=d, sprop_done=False), bool)
