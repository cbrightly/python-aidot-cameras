"""Video has not "arrived" until a keyframe has.

The serve mux begins on a keyframe and discards everything before it, so a
session that delivers P-frames and never an IDR produces an EMPTY stream no
matter how many frames come in. Measured 2026-08-17 on an A000088: one session
carried 600 video frames and zero keyframes through roughly fifteen PLIs. The
viewer's playlist held three segments with no frames in them, and the presence
watchdog - written for exactly this outcome - stayed quiet the whole time,
because it read the field that was climbing.

The canary has counted ``keyframes`` separately since then; the serve loop
just never consulted it when deciding whether video had started. That is the
one line this fixes. The verdict function itself is unchanged: a session that
has seen a keyframe is "ok", one that has not is still "waiting" and then
"give-up" - exactly as for a session with no video at all, which is what a
P-frame-only session is to a consumer.
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.client import _video_has_started


def test_a_keyframe_means_video_has_started():
    assert _video_has_started({"frames": 12, "keyframes": 1}) is True


def test_p_frames_alone_do_not():
    """600 frames, 0 keyframes: the measured session. An empty stream."""
    assert _video_has_started({"frames": 600, "keyframes": 0}) is False


def test_no_frames_at_all_has_not_started():
    assert _video_has_started({"frames": 0, "keyframes": 0}) is False


def test_a_missing_canary_has_not_started():
    assert _video_has_started(None) is False


def test_a_canary_without_a_keyframe_counter_falls_back_to_frames():
    """An older canary shape that never learned to count keyframes must not
    read as permanently video-less; only a canary that CAN tell is trusted to."""
    assert _video_has_started({"frames": 12}) is True
    assert _video_has_started({"frames": 0}) is False
