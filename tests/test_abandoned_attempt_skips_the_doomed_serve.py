"""An abandoned attempt should go to the retry, not serve nothing first.

When the stale-offer backstop ends the media wait and no media has arrived even
after the grace, the code used to build a serve SDP from nothing observed,
launch ffmpeg, and wait for that serve to fail before retrying. Measured
2026-09-03 across two slow-path trials, that costs twice:

    21:33:54  backstop ends the wait
    21:34:03  grace expires: "no video observed ... no audio observed"
    21:34:03  serve launched anyway, video only          (+37.4 s)
    21:34:13  session ends: "camera not ready ... fast retry in 3s"
    21:34:17  retry
    21:34:21  retry's first media                         (+4.7 s on its own clock)
              card served at +57.0 s, audio from the RETRY, not from the serve

The doomed serve delivered nothing, delayed the retry by ~14 s, and its
video-only SDP is what dropped the audio in the first place. Skipping it goes
straight to the attempt that works.

**The delay it retries on is the whole risk.** An exception out of the open
lands in the generic handler, which uses the pacer's `fail_delay()` - the
ESCALATING open-failure backoff. Taking that path would be worse than the
behaviour being replaced, because the session-ended path this replaces gets the
fast not-ready retry instead. So the abandoned attempt raises a distinct
exception and is paced like the session-ended case it stands in for: the fast
retry when the camera said -50019, and the ordinary unhealthy-session delay
otherwise. Never the open-failure escalation.
"""

import ast
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import pytest

from aidot_cameras.exceptions import AidotCameraBusy, AidotCameraNoMedia, AidotError
from aidot_cameras.camera.sdes_open import _should_skip_doomed_serve


def _s(**kw):
    args = dict(abandoned=True, have_video=False)
    args.update(kw)
    return _should_skip_doomed_serve(**args)


# --------------------------------------------------------------------------- #
# When to skip
# --------------------------------------------------------------------------- #


def test_an_abandoned_attempt_with_no_media_is_skipped():
    assert _s() is True


def test_an_attempt_that_got_media_is_served():
    """The grace caught it - this is the good outcome and must be untouched."""
    assert _s(have_video=True) is False


def test_the_plain_timeout_path_is_now_skipped_too():
    """SUPERSEDED 2026-09-05, and kept rather than deleted because the reason
    matters.

    This used to assert that a camera which never spoke keeps the old
    behaviour - serve launched, HA's stream worker retries into it - on the
    grounds that it was a transient worth tolerating. A camera that dropped off
    the WiFi showed it is not: 11 attempts, 6 stalls, every one launching an
    ffmpeg that died on "dimensions not set" because no media ever arrived.
    A permanent failure was being looped on, not a transient tolerated.

    A serve with observed video is still served, whichever wait ended."""
    assert _s(abandoned=False) is True
    assert _s(abandoned=False, have_video=True) is False


# --------------------------------------------------------------------------- #
# The exception it raises
# --------------------------------------------------------------------------- #


def test_the_exception_is_distinct_from_a_terminal_refusal():
    """AidotCameraBusy means 'stop retrying'. This one means the opposite -
    retry now - so the loop must be able to tell them apart."""
    assert not issubclass(AidotCameraNoMedia, AidotCameraBusy)
    assert issubclass(AidotCameraNoMedia, AidotError)


def test_the_exception_carries_how_long_was_waited():
    e = AidotCameraNoMedia(waited_s=37.4)
    assert e.waited_s == 37.4
    assert "37" in str(e)


def test_it_is_catchable_as_a_plain_exception_too():
    """It must not escape any existing `except Exception` that guards a caller
    which has not been taught about it."""
    with pytest.raises(Exception):
        raise AidotCameraNoMedia(waited_s=1.0)


# --------------------------------------------------------------------------- #
# How the loop paces the retry - the part that could make this worse
# --------------------------------------------------------------------------- #


def test_a_battery_camera_that_never_delivers_is_abandoned_by_the_futile_guard(
    monkeypatch, make_camera_device_client
):
    """The no-media exception feeds the futile-keepalive guard.

    The guard is what stops a battery camera being woken forever ("a unit has
    already been drained to 5% that way"). If the generic open-failure handler
    caught the exception instead, nothing would count the streak and the loop
    would retry for ever.
    """
    import aidot_cameras.camera.client as cc
    from tests.keepalive_driver import SdesKeepaliveRun, no_media

    cam = make_camera_device_client("A001513")
    assert cam.is_battery_camera
    limit = cc._FUTILE_KEEPALIVE_LIMIT
    run = SdesKeepaliveRun(monkeypatch, cam, no_media(limit + 3)).run()

    assert len(run.opens) == limit, "it retried, then stopped at the limit"
    assert run.dormant == [{"release_url": False, "why": "abandon"}]
    assert run.deregistered == 1
    assert cam._streaming_active is False


def test_a_generic_open_failure_is_not_counted_as_no_media(
    monkeypatch, make_camera_device_client
):
    """The other side of the ordering: only the no-media exception is a
    session that ended without media; an open that failed outright is paced by
    the open-failure backoff and never trips the abandon."""
    import aidot_cameras.camera.client as cc
    from tests.keepalive_driver import SdesKeepaliveRun

    cam = make_camera_device_client("A001513")
    limit = cc._FUTILE_KEEPALIVE_LIMIT
    run = SdesKeepaliveRun(
        monkeypatch, cam, [RuntimeError("signalling failed")] * (limit + 3)
    ).run()

    assert len(run.opens) == limit + 4  # every scripted failure, then the stop
    assert run.dormant == []


def test_the_no_media_retry_takes_the_fast_wake_burst_then_the_pacer(
    monkeypatch, make_camera_device_client
):
    """Paced as a session that ended without media - which is what it is.

    A camera that said -50019 (still waking) gets the bounded fast burst; once
    the burst is spent the retry falls back to session_end_delay(healthy=False),
    which escalates from the 10 s floor. Never the open-failure escalation
    straight away, which would be worse than the doomed serve this replaced.
    """
    from aidot_cameras.camera.client import _PEERID_MAX_REUSE
    from tests.keepalive_driver import SdesKeepaliveRun, no_media

    monkeypatch.setenv("AIDOT_FUTILE_KEEPALIVE_LIMIT", "0")
    cam = make_camera_device_client("A001513")
    cam._live_play_not_ready = lambda: True
    run = SdesKeepaliveRun(monkeypatch, cam, no_media(_PEERID_MAX_REUSE + 2)).run()

    burst = run.sleeps[:_PEERID_MAX_REUSE]
    assert all(d < 10.0 for d in burst), run.sleeps
    assert all(d >= 10.0 for d in run.sleeps[_PEERID_MAX_REUSE:]), run.sleeps


def test_a_mains_camera_keeps_retrying_an_open_that_delivers_nothing(
    monkeypatch, make_camera_device_client
):
    """The no-media branch loops round to another attempt; only the battery
    guard ever stops it, and a mains camera has no charge to protect."""
    from tests.keepalive_driver import SdesKeepaliveRun, no_media

    cam = make_camera_device_client("A001064")
    assert not cam.is_battery_camera
    run = SdesKeepaliveRun(monkeypatch, cam, no_media(12)).run()

    assert len(run.opens) == 13
    assert run.dormant == []
    assert all(d >= 10.0 for d in run.sleeps), "no fast burst on a mains camera"


def test_the_guard_actually_trips_for_a_run_of_no_media_attempts():
    """Behaviour, not just wiring: the helpers this branch calls must reach the
    abandon decision for a battery camera that never delivers."""
    from aidot_cameras.camera.client import (
        _FUTILE_KEEPALIVE_LIMIT,
        _next_no_media_streak,
        _should_abandon_keepalive,
    )

    streak = 0
    for _ in range(_FUTILE_KEEPALIVE_LIMIT):
        streak = _next_no_media_streak(streak, False)
    assert _should_abandon_keepalive(streak, is_battery=True) is True
    # A delivered session clears it, so a camera that recovers is not punished.
    assert (
        _should_abandon_keepalive(_next_no_media_streak(streak, True), is_battery=True)
        is False
    )


# --------------------------------------------------------------------------- #
# The plain timeout path, which the first version deliberately left alone
# --------------------------------------------------------------------------- #
#
# It was left alone with a stated reason: "HA's stream worker tolerates the
# transient failure and retries into the serve, and taking that away would
# change a case this has no evidence about."
#
# There is evidence now, from a camera that dropped off the WiFi on 2026-09-05.
# Every attempt ran the full first-media wait, timed out with NOTHING observed,
# launched the serve anyway, and ffmpeg died on the spot:
#
#   Could not find codec parameters for stream 1 (Video: h264, none):
#                                                       unspecified size
#   [rtsp] dimensions not set
#   [out#0/rtsp] Could not write header (incorrect codec parameters ?)
#
# 11 attempts and 6 stalls in 25 minutes, each spawning an ffmpeg that could not
# start. The stream worker was not tolerating a transient failure; it was
# looping on a permanent one. So the skip now applies to the timeout path too:
# what makes a serve doomed is that no media was observed, not which wait ended.


def test_a_timed_out_attempt_with_nothing_observed_also_skips():
    """The 2026-09-05 case: not abandoned by the backstop, just silent.

    Env cleared, because this pins the SHIPPED default and the knob is read
    from the ambient environment - an operator who set the documented escape
    hatch would otherwise get a red suite that has nothing to do with them."""
    assert _should_skip_doomed_serve(abandoned=False, have_video=False) is True


def test_a_talk_or_snapshot_open_is_never_abandoned():
    """The open path is shared. async_speak (siren, announce) and
    async_snapshot run the same first-media wait with no serve to build, and
    outbound talk does not need inbound media at all - the SRTP session and the
    ICE nomination are already up. Aborting those because no video arrived would
    break the siren on exactly the cameras this was written to help."""
    assert (
        _should_skip_doomed_serve(abandoned=False, have_video=False, serving=False)
        is False
    )
    assert (
        _should_skip_doomed_serve(abandoned=True, have_video=False, serving=False)
        is False
    )


def test_the_gate_only_fires_when_a_serve_is_actually_being_built():
    """The skip gate must be told whether this open builds a serve at all,
    or a talk/snapshot open would be abandoned for want of video.

    The open cannot be driven in a unit test; this reads its AST: every call
    of _should_skip_doomed_serve passes ``serving=`` derived from the two
    serve destinations.
    """
    from tests.ast_guards import calls_to, module_tree, names_in

    calls = calls_to(
        module_tree("aidot_cameras.camera.sdes_open"), "_should_skip_doomed_serve"
    )
    assert calls, "the open no longer consults the skip gate"
    for call in calls:
        serving = {k.arg: k.value for k in call.keywords}.get("serving")
        assert serving is not None, f"line {call.lineno}: no serving= argument"
        assert {"rtsp_push_url", "output_path"} <= names_in(serving), ast.unparse(
            serving
        )


def test_the_backstop_case_still_skips():
    assert _should_skip_doomed_serve(abandoned=True, have_video=False) is True


def test_an_attempt_that_saw_video_still_serves():
    """Observed video is the whole point - that serve has something to carry,
    whichever way the wait ended."""
    assert _should_skip_doomed_serve(abandoned=True, have_video=True) is False
    assert _should_skip_doomed_serve(abandoned=False, have_video=True) is False
