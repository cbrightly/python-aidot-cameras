"""Media/serve deadlines must stay outside the documented cold-start window.

Two shipped regressions were a timing constant set too low: a serve launch
gated on 15 s when a cold session's media does not start until ~21 s (so the
serve launched with both payload types unknown), and a 45 s first-media wait
sitting INSIDE the documented 25-70 s cold-start window (so a cold camera was
declared dead while it was still coming up).

docs/CAMERAS.md documents the observed cold-start envelope; these constants
must clear its upper end.  If real-world numbers change, update the window
here deliberately - do not quietly lower a constant to make something pass.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

# Upper end of the documented cold-start window (docs/CAMERAS.md).
COLD_START_UPPER_S = 70.0


def test_first_media_wait_clears_the_cold_start_window():
    from aidot_cameras.camera.sdes_open import _FIRST_MEDIA_WAIT_S

    assert _FIRST_MEDIA_WAIT_S > COLD_START_UPPER_S, (
        f"_FIRST_MEDIA_WAIT_S={_FIRST_MEDIA_WAIT_S}s is inside the documented "
        f"{COLD_START_UPPER_S}s cold-start window - a cold camera would be "
        "declared dead while it is still coming up"
    )


def test_audio_pt_grace_is_short_enough_to_not_delay_the_serve():
    from aidot_cameras.camera.sdes_open import _AUDIO_PT_GRACE_S

    # The audio grace is additive to the video wait; keep it a brief top-up.
    assert 0 < _AUDIO_PT_GRACE_S <= 5.0, (
        f"_AUDIO_PT_GRACE_S={_AUDIO_PT_GRACE_S}s delays every serve launch"
    )
