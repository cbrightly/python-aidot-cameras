"""A `max_seconds` bound must actually bound the serve.

`-use_wallclock_as_timestamps 1` is an INPUT option that rewrites PTS to
wall-clock epoch values, and `-t` then measures against a timeline that no
longer starts near zero. The two do not compose. Measured on the box's own
ffmpeg 8.1.2, `-c copy`, realtime input, a 3 s bound on an 8 s source that
should yield ~30 packets:

    control: -t on the output                 32 packets, duration 3.2000   correct
    wallclock + -t on the output              unreadable output, 262 bytes
    wallclock + -t on the INPUT (before -i)   unreadable output, 262 bytes
    -t on the input, no wallclock             32 packets, duration 3.2000   correct

So moving the bound to the input side does NOT rescue it - both placements fail
once the input is wallclock-stamped. The review that found this measured a
different shape on ffmpeg 9.0.1 (an overrun to end-of-input rather than an empty
file), which is the other reason not to pin a placement: the failure is
build-dependent, and only "do not combine them" holds across builds.

Dropping the flag when a bound is asked for costs nothing real. A bounded run is
a snapshot or the `-f null` drain, never the live stream - the live serve passes
no `max_seconds` - and arrival stamping exists to stop a long stream drifting on
the camera's backward clock, which a ten-second grab does not care about.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.client import _build_sdes_serve_cmd as build

WALLCLOCK = "-use_wallclock_as_timestamps"


def _cmd(**kw):
    return build(sdp_path="/tmp/x.sdp", **kw)


def test_an_unbounded_serve_still_stamps_by_arrival():
    """The live stream is the reason the flag exists; it must keep it."""
    assert WALLCLOCK in _cmd()
    assert "-t" not in _cmd()


def test_a_bounded_serve_drops_the_flag_that_breaks_the_bound():
    cmd = _cmd(max_seconds=10, output_path="/tmp/snap.mp4")
    assert "-t" in cmd, "the bound must still be requested"
    assert WALLCLOCK not in cmd, (
        "a wallclock-stamped input makes -t produce an unusable file; "
        "the two must never appear together"
    )


def test_the_bound_reaches_the_recording_destination():
    cmd = _cmd(max_seconds=10, output_path="/tmp/snap.mp4")
    assert cmd[cmd.index("-t") + 1] == "10"


def test_the_bound_reaches_the_drain_destination():
    """The `-f null` drain passes a bound too and breaks the same way."""
    cmd = _cmd(max_seconds=5)
    assert "-t" in cmd
    assert WALLCLOCK not in cmd


# --- a sub-second bound must not truncate to nothing ------------------------

def test_a_sub_second_bound_is_not_truncated_to_zero():
    """`int(0.5)` is 0, and `-t 0` asks ffmpeg for no output at all.

    Separate defect from the wallclock interaction above: this one mistypes the
    argument, that one made it ineffective. A caller asking for half a second
    should get half a second, not an empty file.
    """
    cmd = _cmd(max_seconds=0.5, output_path="/tmp/snap.mp4")
    bound = cmd[cmd.index("-t") + 1]
    assert float(bound) > 0, "a positive bound became %r" % bound
    assert abs(float(bound) - 0.5) < 1e-6


def test_a_whole_second_bound_stays_readable():
    """Do not regress the common case into 10.0 noise where 10 will do."""
    cmd = _cmd(max_seconds=10, output_path="/tmp/snap.mp4")
    assert cmd[cmd.index("-t") + 1] == "10"
