"""Our offer must not claim payload type 0 for audio.

Every camera on the reference fleet sends audio as PCMA (payload type 8),
measured on the wire 2026-08-17 - 80 kbps in 390-byte packets on both A000088
units. Offering PCMU alongside it buys nothing and risks a real collision: one
of those cameras announces a video section on payload type 0 and transmits its
video there, and a payload type cannot belong to two receivers at once.

Keeping PCMU is still one env var away, because "no camera we own does this"
is not the same as "no camera does this".
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.webrtc_open import _audio_codec_preferences


def _mimes(env=None):
    return [str(c.mimeType).lower() for c in _audio_codec_preferences(env)]


def test_pcma_is_offered():
    assert "audio/pcma" in _mimes({})


def test_pcmu_is_not_offered():
    assert "audio/pcmu" not in _mimes({})


def test_env_override_restores_pcmu():
    assert "audio/pcmu" in _mimes({"AIDOT_OFFER_PCMU": "1"})


def test_the_filter_removes_exactly_one_codec():
    """A filter that quietly emptied the list would break every open."""
    kept = _mimes({})
    full = _mimes({"AIDOT_OFFER_PCMU": "1"})
    assert len(kept) == len(full) - 1
    assert kept, "audio codec preferences must not be empty"
