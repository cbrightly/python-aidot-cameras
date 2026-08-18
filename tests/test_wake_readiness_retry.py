"""A waking battery camera gets a fast retry, not an escalating backoff.

A battery camera can answer ``livePlayResp`` with ``-50019`` ("not ready") and
then send no media: it was not refusing, it had not finished waking. The SDES
keepalive loop could not tell that apart from a degraded camera, so the pacer
escalated (10 s -> 300 s) on a camera that would have been ready seconds later.
Under a consumer that re-opens per view - Home Assistant idle-releases after 120 s
and re-opens on the next view - that is the difference between a slow first frame
and a live view that never fills in. Tracked as the open follow-up in #149: "a
rapid third consecutive session can still hit -50019 (battery wake-readiness)".

Two things are deliberately NOT done and are locked in below:

- ``-50019`` never aborts an open. It is benign on its own (mains cameras emit it
  and recover via ICE), which is what 0.12.15 established; only the retry *delay*
  keys on it, and only after a session has already failed to deliver media.
- The burst is bounded to the peerid reuse window, so the whole burst stays on ONE
  peerid. A fresh peerid registers another camera-side session, which is what
  wedged the L2 into a wake-then-sleep loop before 0.12.16.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot_cameras.camera.client as cc
from aidot_cameras.camera.constants import _LIVE_PLAY_NOT_READY

_CAM = next(v for v in vars(cc).values()
            if isinstance(v, type) and "_live_play_not_ready" in v.__dict__)


class _Info:
    def __init__(self, model_id):
        self.model_id = model_id


def _cam(model_id="LK.IPC.A001513", code=None):
    c = _CAM.__new__(_CAM)
    c.info = _Info(model_id)
    c.device_id = "DEV1234"
    c._raw_device = {}
    c._last_live_play_code = code
    return c


# --- recording the code ------------------------------------------------------ #

def test_note_records_the_code():
    c = _cam()
    c._note_live_play_resp({"code": _LIVE_PLAY_NOT_READY, "peerid": "p"})
    assert c._last_live_play_code == _LIVE_PLAY_NOT_READY
    assert c._live_play_not_ready() is True


def test_note_coerces_a_string_code():
    # Cloud/camera JSON is not consistently typed.
    c = _cam()
    c._note_live_play_resp({"code": "-50019"})
    assert c._live_play_not_ready() is True


def test_note_survives_a_malformed_payload():
    # Runs from the signaling dispatcher - must never raise into it.
    c = _cam(code=_LIVE_PLAY_NOT_READY)
    for _bad in (None, [], "nope", 7):
        c._note_live_play_resp(_bad)
    c._note_live_play_resp({})           # no "code" key
    assert c._live_play_not_ready() is False


def test_an_ok_code_is_not_not_ready():
    for _ok in (200, 0, None, -50002):
        assert _cam(code=_ok)._live_play_not_ready() is False


# --- classifying a finished session ------------------------------------------ #

def test_burst_counts_up_while_the_camera_keeps_waking():
    c = _cam(code=_LIVE_PLAY_NOT_READY)
    _b = 0
    for _expected in (1, 2, 3, 4):
        _b = c._next_not_ready_burst(healthy=False, burst=_b)
        assert _b == _expected


def test_a_healthy_session_resets_the_burst():
    c = _cam(code=_LIVE_PLAY_NOT_READY)
    assert c._next_not_ready_burst(healthy=True, burst=3) == 0


def test_a_failure_without_the_code_resets_the_burst():
    # A generic no-media session is the pacer's business, not a wake retry.
    c = _cam(code=200)
    assert c._next_not_ready_burst(healthy=False, burst=3) == 0


def test_mains_cameras_are_not_given_wake_retries():
    # -50019 on a mains camera is transient noise; if it then delivered no media,
    # something IS degraded and the pacer's escalation is the right answer.
    c = _cam("LK.IPC.A001064", code=_LIVE_PLAY_NOT_READY)
    assert c._next_not_ready_burst(healthy=False, burst=0) == 0


def test_evidence_detected_battery_gets_wake_retries():
    c = _cam("LK.IPC.A009999", code=_LIVE_PLAY_NOT_READY)
    c._raw_device = {"properties": {"Battery_remaining": 55}}
    assert c._next_not_ready_burst(healthy=False, burst=0) == 1


# --- the delay --------------------------------------------------------------- #

def test_no_burst_hands_back_to_the_pacer():
    assert _CAM._not_ready_retry_delay(0) == (0.0, False)


def test_burst_is_a_short_fast_retry():
    for _n in (1, 2, 3):
        _delay, _fast = _CAM._not_ready_retry_delay(_n, burst_max=3)
        assert _fast is True
        assert 0 < _delay <= 5.0, _delay


def test_spent_burst_hands_back_to_the_pacer():
    # Bounded: a camera that is persistently cold must not be hammered.
    _delay, _fast = _CAM._not_ready_retry_delay(4, burst_max=3)
    assert _fast is False


def test_burst_stays_inside_one_peerid_window():
    # The loop passes burst_max=_PEERID_MAX_REUSE so the whole burst re-offers on
    # the SAME peerid; a fresh one registers another camera-side session, which
    # the camera frees only slowly (0.12.16).
    import inspect
    _body = inspect.getsource(cc.CameraMixin._sdes_keepalive_loop_inner)
    # One module-level bound, shared with the DTLS serve loop so the two
    # peer-id reuse policies cannot drift apart.
    assert cc._PEERID_MAX_REUSE == 3
    assert "burst_max=_PEERID_MAX_REUSE" in _body


def test_fast_retry_is_much_shorter_than_the_pacer_floor():
    # The whole point: the pacer's floor is 10 s and escalates to 300 s; a waking
    # camera should be re-tried well inside that.
    import inspect
    _body = inspect.getsource(cc.CameraMixin._sdes_keepalive_loop_inner)
    assert "_MIN_DELAY = 10.0" in _body
    _delay, _fast = _CAM._not_ready_retry_delay(1, burst_max=3)
    assert _fast is True and _delay < 10.0


# --- what must NOT change ---------------------------------------------------- #

def test_not_ready_never_aborts_an_open():
    # 0.12.15: -50019 is benign and the camera recovers via ICE. Only an explicit
    # livePlay=0 is a refusal. Guard the SDES open against a regression that turns
    # the code back into a fast-fail.
    import inspect
    import aidot_cameras.camera.sdes_open as so
    _body = inspect.getsource(so._SdesOpenMixin)
    _idx = _body.find("_LIVE_PLAY_NOT_READY")
    assert _idx != -1, "the SDES open no longer references the not-ready code"
    # The only refusal branch keys on livePlay == 0, not on the code.
    assert "if _lp_on_sdes == 0:" in _body
    assert "raise RuntimeError" in _body


def test_constant_matches_the_documented_wire_code():
    assert _LIVE_PLAY_NOT_READY == -50019
