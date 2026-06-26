"""Unit tests for the SDES two-way-audio (talk) state machine - no camera needed.

Locks down the SdesSession talk lifecycle and the two fixes from the max-effort
review:
  * spk_eligible_ts is re-armed (set None) whenever talk stops, so a SECOND clip
    on a reused session waits the full SPEAKERSTART delay again instead of firing
    immediately (which the camera would ignore -> silent re-talk).
  * stop() gives the bridge thread a window to emit SPEAKERSTOP(849) on the
    still-live SCTP channel before tearing ffmpeg down (else the camera mic stays
    occupied and the next session gets 851 "mic occupied").

Runs under pytest, or standalone:  python tests/test_sdes_talk.py
"""
import asyncio
import os
import sys
import threading
import time

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.device_client import SdesSession


class _FakeProc:
    def __init__(self):
        self._alive = True
        self.returncode = 0
        self.stderr = self

    def poll(self):
        return None if self._alive else self.returncode

    def terminate(self):
        self._alive = False

    def wait(self, timeout=None):
        self._alive = False
        return self.returncode

    def kill(self):
        self._alive = False

    def read(self):  # stderr.read()
        return b""


class _FakeSock:
    def __init__(self):
        self.closed = False

    def close(self):
        self.closed = True


class _FakeQ:
    def put_nowait(self, _x):
        pass


def _fresh_talk_state():
    # Mirrors the dict _open_sdes_stream builds for a talk-capable open.
    return {
        "provider": None, "src": None, "sock": None,
        "ssrc": 0x0000ABCD, "key": "x" * 40,   # 40 b64 chars -> 30-byte SRTP key
        "want_speaker": False, "speaker_on": False,
        "spk_eligible_ts": None, "stop": False,
    }


def _stop_pump(s):
    """Signal the talk pump to exit and JOIN it before the test returns.

    async_start_talk spawns a daemon ``_run_sdes_talk_pump`` thread.  Production
    stop() only flips the ``stop`` flag (the daemon winds itself down and never
    blocks process exit), but pytest-homeassistant's teardown fails the test on
    ANY leftover non-dummy thread - so tests that start the pump must join it.
    """
    if s._talk_state is not None:
        s._talk_state["stop"] = True
    t = getattr(s, "_talk_thread", None)
    if t is not None:
        t.join(timeout=2.0)


def _make_sdes(talk_state):
    return SdesSession(
        proc=_FakeProc(),
        sdp_path="/tmp/aidot_test_does_not_exist.sdp",
        outgoing_q=_FakeQ(),
        mqtt_fut=None,            # stop() awaits this in try/except -> harmless
        audio_sock=_FakeSock(),
        video_sock=_FakeSock(),
        cmd_chan=[None],
        talk_state=talk_state,
    )


def test_talk_supported_reflects_talk_state():
    assert _make_sdes(None).talk_supported is False
    assert _make_sdes(_fresh_talk_state()).talk_supported is True


def test_start_talk_sets_want_speaker_and_provider():
    ts = _fresh_talk_state()
    s = _make_sdes(ts)
    prov = lambda: None
    assert asyncio.run(s.async_start_talk(prov)) is True
    assert ts["want_speaker"] is True
    assert ts["provider"] is prov
    assert ts["stop"] is False
    _stop_pump(s)  # join the idle daemon pump (src is None -> it never sent)


def test_start_talk_returns_false_without_talk_state():
    s = _make_sdes(None)              # opened non-talk-capable (offer stayed recvonly)
    assert asyncio.run(s.async_start_talk(lambda: None)) is False


def test_async_stop_talk_rearms_spk_eligible_ts():
    # FIX #1: a stale spk_eligible_ts (armed by the bridge during clip 1) must be
    # cleared on stop so clip 2's SPEAKERSTART is delayed again.
    ts = _fresh_talk_state()
    ts["want_speaker"] = True
    ts["provider"] = lambda: b"\x00" * 320
    ts["spk_eligible_ts"] = 123.0
    s = _make_sdes(ts)
    assert asyncio.run(s.async_stop_talk()) is True
    assert ts["want_speaker"] is False
    assert ts["provider"] is None
    assert ts["spk_eligible_ts"] is None


def test_retalk_on_same_session_rearms_delay():
    # FIX #1 end-to-end: start -> (bridge arms) -> stop -> start again leaves
    # spk_eligible_ts None, so the bridge re-arms a fresh delay for clip 2.
    ts = _fresh_talk_state()
    s = _make_sdes(ts)

    async def _seq():
        await s.async_start_talk(lambda: None)
        ts["spk_eligible_ts"] = 100.0          # simulate the bridge arming clip 1
        await s.async_stop_talk()
        assert ts["spk_eligible_ts"] is None    # re-armed
        await s.async_start_talk(lambda: None)  # clip 2
        assert ts["spk_eligible_ts"] is None    # still None -> fresh delay, not stale

    asyncio.run(_seq())
    _stop_pump(s)  # join the pump from clip 2 so no daemon thread leaks


def test_stop_waits_for_bridge_speakerstop_then_tears_down():
    # FIX #2: speaker is open; stop() must NOT terminate ffmpeg until the bridge
    # has cleared speaker_on (i.e. emitted SPEAKERSTOP on the live channel).
    ts = _fresh_talk_state()
    ts["speaker_on"] = True
    ts["want_speaker"] = True
    s = _make_sdes(ts)

    def _fake_bridge():
        # Mimic the bridge: once stop() flips want_speaker off, send SPEAKERSTOP.
        for _ in range(100):
            if ts.get("want_speaker") is False:
                time.sleep(0.04)
                ts["speaker_on"] = False
                return
            time.sleep(0.005)

    threading.Thread(target=_fake_bridge, daemon=True).start()
    t0 = time.monotonic()
    asyncio.run(s.stop())
    elapsed = time.monotonic() - t0

    assert ts["speaker_on"] is False            # bridge got its window to send 849
    assert ts["want_speaker"] is False
    assert ts["spk_eligible_ts"] is None
    assert ts["stop"] is True
    assert s._proc.poll() is not None           # ffmpeg terminated (after the wait)
    assert 0.02 < elapsed < 0.8                 # waited, but bounded (didn't hang)


def test_stop_no_wait_when_speaker_already_off():
    # Normal flow: the clip completed (pump already cleared speaker via the bridge),
    # so stop() must not block on the SPEAKERSTOP window.
    ts = _fresh_talk_state()                    # speaker_on=False
    s = _make_sdes(ts)
    t0 = time.monotonic()
    asyncio.run(s.stop())
    assert time.monotonic() - t0 < 0.3
    assert ts["stop"] is True
    assert s._proc.poll() is not None


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
