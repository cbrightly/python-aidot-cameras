"""The feature probe must not report a closed session as a broken camera.

Fleet run 31332008184 scored talk=FAIL for all three SDES cameras. The cameras
were fine; the session had been closed by the time the probe asked. `max_seconds`
was `hold - 2`, so ffmpeg - and on the SDES path the bridge thread that
dispatches SPEAKERSTART, which lives and dies with it - was gone before
`asyncio.sleep(hold)` even returned, let alone before the 25 s snapshot that ran
ahead of talk.

feature_probe's own docstring says unsupported, not-run and failed are three
different results and that collapsing any two of them is the defect this project
keeps re-fixing. Reporting a corpse as a FAIL collapsed the second into the
third. These tests hold the line at the probe, independently of live_validate
sizing the session correctly - a harness that depends on two things being right
at once should still tell the truth when one of them is wrong.
"""
import asyncio
import os
import sys

sys.path.insert(0, os.path.join(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "scripts"))

from feature_probe import NOT_RUN, PASS, probe_features, session_alive


class _Session:
    """Minimum surface probe_features reads off a live stream session."""

    def __init__(self, alive: bool):
        self.is_alive = alive
        self.talk_supported = True
        self.start_calls = 0

    async def async_start_talk(self, provider):
        self.start_calls += 1
        # A live session's bridge opens the speaker and the pump polls us.
        for _ in range(3):
            provider()
        return True

    async def async_stop_talk(self):
        return True


class _DeviceClient:
    """No snapshot/thumbnail/recording methods - those probe UNSUPPORTED here."""

    device_id = "cam"


def _run(session, device=None):
    return asyncio.run(probe_features(_DeviceClient(), device or {}, session))


def test_talk_is_not_run_when_the_session_is_already_closed():
    session = _Session(alive=False)
    out = _run(session)
    assert out["talk"] == NOT_RUN
    assert "closed" in out["talk_error"]
    # And it must not have gone ahead anyway: asking a dead session produces a
    # verdict about nothing.
    assert session.start_calls == 0


def test_talk_still_runs_on_a_live_session():
    # The guard must not be satisfiable by refusing to probe at all.
    session = _Session(alive=True)
    out = _run(session)
    assert out["talk"] == PASS
    assert session.start_calls == 1


def test_ptz_is_not_run_when_the_session_is_already_closed():
    # PTZ rides the same session. Its send can succeed into a socket nobody
    # reads, so a closed session yields PASS on no evidence.
    out = _run(_Session(alive=False), {"modelId": "LK.IPC.A001064"})
    assert out["ptz"] == NOT_RUN
    assert "closed" in out["ptz_error"]


def test_the_snapshot_reports_how_long_it_took():
    # The SDES snapshot budget has been wrong twice and both times the only
    # evidence was a verdict, so the replacement budget was a guess. Reporting
    # the elapsed time makes the next runs answer it.
    class _Snapper(_DeviceClient):
        async def async_snapshot(self, path, timeout=10.0):
            return False        # verdict is irrelevant here; the timing is not

    out = asyncio.run(probe_features(_Snapper(), {}, _Session(alive=True)))
    assert "snapshot_s" in out
    assert isinstance(out["snapshot_s"], float)


def test_the_snapshot_budget_clears_the_slowest_measured_snapshot():
    # Measured across the fleet: SDES 17.2-23.6 s, DTLS 2.8-3.0 s. Two earlier
    # budgets were set just above the then-known maximum and both timed out a
    # camera afterwards, so this asserts headroom rather than a value.
    from feature_probe import _snapshot_budget

    slowest_sdes_seen = 23.6
    sdes = _snapshot_budget({"modelId": "LK.IPC.A001064"}, 10.0)
    assert sdes >= slowest_sdes_seen * 1.5, (
        "an SDES budget without real headroom is how this failed twice")
    # DTLS is a different path and an order of magnitude faster; widening it
    # would only slow down reporting a camera that is genuinely broken.
    assert _snapshot_budget({"modelId": "LK.IPC.A000088"}, 10.0) == 10.0


def test_a_session_that_does_not_publish_liveness_counts_as_live():
    # Only SdesSession has is_alive - it tracks the ffmpeg the bridge dies with.
    # The DTLS session keeps no ffmpeg and publishes nothing, and absence of the
    # attribute must not be read as death.
    class _NoLiveness:
        pass

    assert session_alive(_NoLiveness()) is True


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
