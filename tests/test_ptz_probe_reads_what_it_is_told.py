"""A PTZ command that was refused must not be scored as a PTZ command that ran.

Fleet run 31335318890 scored ptz=PASS on the A001064 while the same run's log
carries four `async_ptz_move: no active stream session` warnings for it - one per
call the probe made. `_probe_ptz` only caught exceptions, and `async_ptz_move`
does not raise when there is no session; it logs, returns False, and sends
nothing. So the probe reported a pass for four commands that never left the host,
which is the failure this module's own docstring says it exists to prevent, now
found for the fifth time.

The reason there was no session is separate and lives in live_validate:
`_stream_session` is set by the keepalive, streaming and serve loops - the paths
Home Assistant actually goes through - and not by a bare
`async_open_webrtc_stream`, which is what the harness calls. So PTZ had never
been exercised on real hardware at all; both of the runs that reported PASS for
it were reporting the probe's own blindness.
"""
import asyncio
import os
import sys

sys.path.insert(0, os.path.join(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "scripts"))

from feature_probe import FAIL, PASS, _probe_ptz


class _Cam:
    """Stands in for the device client's PTZ surface.

    ``async_ptz_move`` returning False is the real refusal shape: no session, or
    an unknown direction. It is not an exception.
    """

    def __init__(self, moves=True, stops=True):
        self._moves = moves
        self._stops = stops
        self.calls = []

    async def async_ptz_move(self, direction, speed=4):
        self.calls.append(direction)
        return self._moves

    async def async_ptz_stop(self):
        self.calls.append("stop")
        return self._stops


def _verdict_of(cam):
    attempted, ok, err = asyncio.run(_probe_ptz(cam, 5.0))
    return (PASS if ok else FAIL), err


def test_a_refused_move_is_a_failure():
    verdict, err = _verdict_of(_Cam(moves=False))
    assert verdict == FAIL
    assert err and "refus" in err.lower()


def test_a_refused_stop_is_a_failure():
    # A stop that did not leave the host is worse than a move that did not: the
    # head keeps travelling.
    verdict, err = _verdict_of(_Cam(stops=False))
    assert verdict == FAIL


def test_a_camera_that_accepts_every_command_passes():
    cam = _Cam()
    verdict, err = _verdict_of(cam)
    assert verdict == PASS
    assert err is None
    # And it really did nudge both ways and stop after each.
    assert cam.calls == ["right", "stop", "left", "stop"]


if __name__ == "__main__":
    import traceback
    _fns = [v for k, v in sorted(globals().items()) if k.startswith("test_")]
    _fail = 0
    for _fn in _fns:
        try:
            _fn()
            print(f"PASS {_fn.__name__}")
        except Exception:
            _fail += 1
            print(f"FAIL {_fn.__name__}")
            traceback.print_exc()
    raise SystemExit(1 if _fail else 0)
