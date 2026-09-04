"""The quality campaign has to measure the session it is in, and say when it did not.

The 2026-08-07 sweep sent SETSTREAMCTRL and judged it by the profile the NEXT
session came back with. The 2026-08-11 vendor-app capture shows the app's own SD
tap taking effect inside the session it was sent in - no renegotiation, no cloud
call, the rate changing within seconds. So the sweep may have been sending a
command that worked and scoring it against the wrong observable, and the
replacement measures before-and-after in ONE session.

Four ways that measurement can quietly produce the answer it is looking for:

  * the session ends inside the second window (ffmpeg's -t expires, or an
    A001064 recycles itself at 60-85 s) and the frozen byte counter reads as a
    100% bitrate reduction;
  * the second window gets no media at all, and 0 bytes reads as 0 kbps;
  * the control arm does not exist or is not really a control, so a stream that
    settles downward on its own is credited to the command;
  * the command never goes out. `async_set_resolution` returns True when there
    is no session - it remembers the value and reports success - which is the
    PTZ probe's old bug in a worse form, because that one at least returned
    False.

These lock all four.
"""
import asyncio
import logging
import os
import sys
import types

sys.path.insert(0, os.path.join(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "scripts"))

import live_validate as lv


class _World:
    """A fake clock the media counter and asyncio.sleep both move with.

    Real sleeps would make these tests take a minute each, and a zero-length
    sleep gives windows of a few microseconds whose ratio is noise. So time is
    explicit: sleeping advances the clock and delivers bytes at the current
    rate, exactly as a camera would.
    """

    def __init__(self, rate_a: int, rate_b: int, alive: bool = True):
        self.now = 100.0
        self.rate = rate_a
        self.rate_b = rate_b
        self.bytes = 0
        self.packets = 0
        self.alive = alive
        self.arm_sent = None
        self.session_at_send = "never called"

    def monotonic(self) -> float:
        return self.now

    async def sleep(self, seconds: float) -> None:
        self.now += seconds
        self.bytes += int(self.rate * seconds)
        # Packets track bytes: a camera that stopped sending stops both, and a
        # fake where the packet counter kept moving on its own would hide
        # exactly the case these tests exist to catch.
        self.packets += int(50 * seconds) if self.rate else 0


class _Session:
    def __init__(self, world: _World):
        self._w = world
        self._cmd_chan = [lambda *a: True]

    def media_stats(self) -> dict:
        return {"bytes": self._w.bytes, "packets": self._w.packets}

    @property
    def is_alive(self) -> bool:
        return self._w.alive


class _Dc:
    def __init__(self, world: _World, die_on_send: bool = False):
        self._w = world
        self._stream_session = None
        self._desired_quality = None
        self._die_on_send = die_on_send

    async def async_set_resolution(self, quality: str) -> bool:
        # What the setter can see at the moment of the call IS the test: with
        # _stream_session None the real one remembers and sends nothing.
        self._w.session_at_send = self._stream_session
        self._w.arm_sent = quality
        self._w.rate = self._w.rate_b
        if self._die_on_send:
            self._w.alive = False
            self._w.rate = 0
        return True


def _probe(world: _World, arm: str, die_on_send: bool = False) -> dict:
    """Run _quality_probe against the fake world, with the fake clock installed."""
    real_time, real_asyncio = lv.time, lv.asyncio
    lv.time = types.SimpleNamespace(monotonic=world.monotonic)
    lv.asyncio = types.SimpleNamespace(sleep=world.sleep)
    try:
        dc = _Dc(world, die_on_send=die_on_send)
        session = _Session(world)
        return asyncio.run(lv._quality_probe(dc, session, arm, {"n": 0}))
    finally:
        lv.time, lv.asyncio = real_time, real_asyncio


def test_the_ratio_is_window_b_over_window_a_of_the_same_session():
    # 200 kB/s before, 100 kB/s after. Between-session variance (839-3698 Kbps
    # on this camera) cannot touch a ratio taken inside one session, which is
    # the whole reason the measurement is shaped this way.
    out = _probe(_World(200_000, 100_000), "sd")
    assert out["verdict"] == "OK"
    assert out["ratio_b_over_a"] == 0.5
    assert out["kbps_a"] == 1600.0 and out["kbps_b"] == 800.0


def test_a_session_that_ended_inside_the_measurement_is_void():
    # The failure that looks exactly like success: ffmpeg's -t expires, or the
    # camera recycles, and the frozen counter reads as a 100% reduction.
    out = _probe(_World(200_000, 0), "sd", die_on_send=True)
    assert out["verdict"] == "VOID"
    assert "ratio_b_over_a" not in out
    assert "ended" in out["void_reason"]


def test_a_second_window_with_no_media_is_void_rather_than_zero_kbps():
    world = _World(200_000, 0)
    out = _probe(world, "sd")
    assert world.alive is True, "this case is a live session that stopped sending"
    assert out["verdict"] == "VOID"
    assert out["void_reason"] == "no media in window B"
    assert "ratio_b_over_a" not in out


def test_the_control_arm_sends_nothing_and_is_still_measured():
    # A control that skipped the wait, or that sent "hd", would not be a
    # control: the question it answers is what the rate does across the same
    # timeline with no command in it.
    world = _World(200_000, 200_000)
    out = _probe(world, "")
    assert world.arm_sent is None, "the control arm must not send SETSTREAMCTRL"
    assert out["arm"] == "control"
    assert out["verdict"] == "OK"
    assert out["ratio_b_over_a"] == 1.0


def test_the_command_is_sent_on_a_session_the_setter_can_actually_see():
    # With _stream_session left None the real setter REMEMBERS the quality,
    # sends nothing, and returns True. A campaign built on that would report a
    # null result for a command that was never issued.
    world = _World(200_000, 100_000)
    out = _probe(world, "sd")
    assert world.session_at_send is not None
    assert world.arm_sent == "sd"
    assert out["cmd_channel_ready"] is True
    assert out["set_resolution_returned"] is True


def test_the_arms_are_balanced_rather_than_blocked():
    """Each arm appears exactly `repeats` times. A blocked campaign - three sd
    then three controls - would measure the time of day, because this camera's
    own rate varies 839-3698 Kbps between sessions."""
    from collections import Counter

    out = lv._interleave_arms(["sd", ""], 3, seed=1)
    assert Counter(out) == Counter({"sd": 3, "": 3})
    assert len(out) == 6


def test_the_order_is_randomised_rather_than_a_fixed_cycle():
    """It used to return a strict sd/control/sd/control cycle. A fixed period is
    the failure this exists to avoid: the reference camera varies things of its
    own between sessions - measured 2026-09-04, 4 of 44 cold opens negotiated
    H.265 rather than H.264 - and anything of the camera's own that happens to
    share the cycle's period lands preferentially on one arm and is read as that
    arm's effect. The b=AS knob nearly produced a false positive from exactly
    this. Balanced blocks, shuffled within each block, keep the balance without
    the period."""
    cycle = ["sd", "", "sd", "", "sd", "", "sd", "", "sd", "", "sd", ""]
    orders = {tuple(lv._interleave_arms(["sd", ""], 6, seed=s)) for s in range(25)}
    assert len(orders) > 1, "assignment is deterministic - the period is still there"
    assert any(list(o) != cycle for o in orders)


def test_every_block_still_contains_every_arm():
    """Shuffling must not drift the balance: an arm that goes missing from a
    block makes the campaign lopsided at whatever n it is stopped at."""
    from collections import Counter

    for seed in range(10):
        out = lv._interleave_arms(["sd", "hd", ""], 4, seed=seed)
        for i in range(0, len(out), 3):
            assert Counter(out[i:i + 3]) == Counter({"sd": 1, "hd": 1, "": 1})


def test_a_seed_makes_a_campaign_reproducible():
    """A run that finds something has to be re-runnable in the same order."""
    assert (lv._interleave_arms(["sd", "hd", ""], 5, seed=7)
            == lv._interleave_arms(["sd", "hd", ""], 5, seed=7))


def test_the_control_arm_survives_the_shuffle():
    """The empty string is the control and is meaningful; a falsy-value filter
    creeping in would silently drop it and leave nothing to compare against."""
    out = lv._interleave_arms(["sd", ""], 4, seed=3)
    assert out.count("") == 4


def test_no_arms_means_no_campaign():
    assert lv._interleave_arms([], 3, seed=1) == []


def test_a_void_is_named_so_it_can_be_re_run_instead_of_averaged_in():
    assert lv._void_reason({"verdict": "PASS", "quality": {"verdict": "OK"}}) is None
    assert lv._void_reason(
        {"verdict": "PASS",
         "quality": {"verdict": "VOID", "void_reason": "no media in window B"}}
    ) == "no media in window B"
    # A session that never streamed is void for the campaign too, whatever the
    # quality probe managed to say about it.
    assert lv._void_reason({"verdict": "NO_MEDIA"}) == "NO_MEDIA"


def test_the_ack_collector_takes_the_setters_debug_line():
    # handle(), not emit(): the level check is the part that has gone wrong on
    # this harness before, and these lines are DEBUG - one level below the INFO
    # receipt lines that already shipped collecting nothing.
    c = lv._AckCollector()
    c.handle(logging.LogRecord(
        name="aidot_cameras.camera.controls", level=logging.DEBUG,
        pathname=__file__, lineno=1,
        msg="set resolution sd (quality=2): camera acked 801 payload=",
        args=(), exc_info=None))
    assert len(c.drain()) == 1


def test_the_ack_collector_ignores_unrelated_lines():
    c = lv._AckCollector()
    c.handle(logging.LogRecord(
        name="aidot_cameras.camera.controls", level=logging.DEBUG,
        pathname=__file__, lineno=1, msg="PTZ up (code=1) -> sent",
        args=(), exc_info=None))
    assert c.drain() == []


def test_the_video_series_reports_its_own_failure_rather_than_a_flat_rate():
    out = asyncio.run(lv._video_bitrate_series("/nonexistent/recording.ts"))
    assert "series_error" in out and "video_kbps_by_second" not in out


def test_the_summary_keeps_every_session_not_just_a_mean():
    # Three control ratios scattering 0.6-1.4 is a finding - the windows are
    # too short - and a mean alone would hide it.
    attempts = [
        {"attempt": 1, "quality": {"arm": "sd", "verdict": "OK", "kbps_a": 1800,
                                   "kbps_b": 900, "ratio_b_over_a": 0.5}},
        {"attempt": 2, "quality": {"arm": "control", "verdict": "OK",
                                   "kbps_a": 1700, "kbps_b": 1690,
                                   "ratio_b_over_a": 0.994}},
        {"attempt": 3, "quality": {"arm": "sd", "verdict": "VOID",
                                   "void_reason": "no media in window B"}},
    ]
    summary = lv._quality_summary(attempts)
    assert summary["sd"]["n"] == 1 and summary["sd"]["void"] == 1
    assert summary["sd"]["sessions"][0]["kbps_a"] == 1800
    assert summary["control"]["ratio_mean"] == 0.994


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


# --------------------------------------------------------------------------- #
# A null result has to be distinguishable from a command that never arrived
# --------------------------------------------------------------------------- #
#
# The probe already records the camera's own ack per session, precisely so "the
# lever does nothing" cannot be confused with "the command never got there".
# The summary kept that per session and then dropped it on the way to the arm,
# so an arm could report n=35 and a mean of 0.99 while only a handful of those
# sessions had the command acked - which is the null this whole campaign exists
# to avoid drawing.

def _att(n, arm, ratio, acked):
    return {"attempt": n,
            "quality": {"arm": arm, "verdict": "OK", "kbps_a": 1000,
                        "kbps_b": int(1000 * ratio),
                        "ratio_b_over_a": ratio,
                        "ack_log": ["801"] if acked else []}}


def test_the_arm_reports_how_many_of_its_sessions_were_acked():
    out = lv._quality_summary([
        _att(1, "sd", 1.0, True),
        _att(2, "sd", 1.0, False),
        _att(3, "sd", 1.0, False),
    ])
    assert out["sd"]["n"] == 3
    assert out["sd"]["acked_n"] == 1, (
        "without this an arm of unacked sessions reads as a measured null")


def test_an_arm_whose_commands_all_landed_says_so():
    out = lv._quality_summary([_att(1, "hd", 0.5, True),
                               _att(2, "hd", 0.5, True)])
    assert out["hd"]["acked_n"] == out["hd"]["n"] == 2


def test_the_control_arm_is_not_penalised_for_sending_nothing():
    """The control deliberately sends no command, so it has no ack and must not
    look like a delivery failure."""
    out = lv._quality_summary([_att(1, "control", 1.0, False),
                               _att(2, "control", 1.0, False)])
    assert out["control"]["n"] == 2
    assert out["control"]["acked_n"] == 0


def test_voids_still_do_not_enter_the_ack_count():
    void = {"attempt": 9, "quality": {"arm": "sd", "verdict": "VOID",
                                      "void_reason": "no media in window B"}}
    out = lv._quality_summary([_att(1, "sd", 1.0, True), void])
    assert out["sd"]["n"] == 1
    assert out["sd"]["void"] == 1
    assert out["sd"]["acked_n"] == 1
