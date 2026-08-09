"""A speaker that never opened must not be reported as a speaker that did.

Measured on the fleet 2026-08-09 (run 31332008184): all three SDES cameras
returned True from ``async_start_talk`` and then never pulled a single PCM frame.
The run log settles why - ``SDES talk: sent SPEAKERSTART(848)`` appears zero
times in it, while ``camera media addr captured`` appears once per camera. The
command was never dispatched, so the bridge never set ``speaker_on``, so the
pump - which requires it - correctly stayed silent.

The reason the caller was told otherwise is ``_speaker_ack_accepted``: it treats
"no ack arrived" as acceptance. That fail-open is right for what it was written
for, which is INTERPRETING an ack whose payloads we have not catalogued. It is
wrong for the case where nothing was ever SENT - there is no camera behaviour to
be generous about, only our own bridge that is gone. Those are separable
propositions and only the second one is decided here, so the DTLS path (which
shares ``_speaker_ack_accepted``) is untouched.

Reachable in production, not only in the harness: ``async_speak`` reuses
``self._stream_session`` whenever ``talk_supported`` is true, and that property
answered from the talk-state dict alone, which outlives ffmpeg. A session whose
ffmpeg had exited - a stall, a camera drop, the abandon ceiling - therefore
accepted talk, dispatched nothing, and returned True to the ``aidot.talk``
service. The user got silence and a success.
"""
import asyncio
import os
import sys
import threading
import time

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.device_client import SdesSession


class _FakeProc:
    """Stands in for the ffmpeg subprocess; ``poll()`` is what liveness reads."""

    def __init__(self, alive: bool = True):
        self._alive = alive
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

    def read(self):
        return b""


class _FakeSock:
    def close(self):
        pass


class _FakeQ:
    def put_nowait(self, _x):
        pass


def _fresh_talk_state():
    return {
        "provider": None, "src": None, "sock": None,
        "ssrc": 0x0000ABCD, "key": "x" * 40,
        "want_speaker": False, "speaker_on": False,
        "spk_eligible_ts": None, "stop": False,
    }


def _make_sdes(talk_state, alive: bool = True):
    return SdesSession(
        proc=_FakeProc(alive),
        sdp_path="/tmp/aidot_test_does_not_exist.sdp",
        outgoing_q=_FakeQ(),
        mqtt_fut=None,
        audio_sock=_FakeSock(),
        video_sock=_FakeSock(),
        cmd_chan=[None],
        talk_state=talk_state,
    )


def _stop_pump(s):
    """Join the daemon pump so pytest-homeassistant's thread check stays quiet."""
    if s._talk_state is not None:
        s._talk_state["stop"] = True
    t = getattr(s, "_talk_thread", None)
    if t is not None:
        t.join(timeout=2.0)


def _fake_bridge(ts, delay: float = 0.05):
    """The bridge thread's half of the handshake: open the speaker on request.

    Production does this in ``_bridge_fn`` - it sees ``want_speaker``, waits
    SDES_SPEAKERSTART_DELAY, sends 848 and sets ``speaker_on``. Only the
    observable effect matters here.
    """
    def _run():
        for _ in range(400):
            if ts.get("want_speaker"):
                time.sleep(delay)
                ts["speaker_on"] = True
                return
            time.sleep(0.005)
    threading.Thread(target=_run, daemon=True).start()


def test_start_talk_is_false_when_the_bridge_never_sent_speakerstart():
    # No bridge: nothing sets speaker_on, nothing acks. This is exactly the
    # fleet's SDES sessions, whose ffmpeg (and with it the bridge thread) had
    # already exited by the time talk was asked for.
    ts = _fresh_talk_state()
    s = _make_sdes(ts)
    try:
        assert asyncio.run(s.async_start_talk(lambda: None)) is False
        # And it must not leave the state armed - a stale want_speaker would make
        # a later bridge open a speaker nobody is feeding.
        assert ts["want_speaker"] is False
        assert ts["provider"] is None
    finally:
        _stop_pump(s)


def test_start_talk_is_true_once_the_bridge_opens_the_speaker():
    # The other side of the same assertion: when the speaker really opens, the
    # answer is still True. Without this the fix above could be satisfied by
    # returning False unconditionally.
    ts = _fresh_talk_state()
    s = _make_sdes(ts)
    _fake_bridge(ts)
    try:
        assert asyncio.run(s.async_start_talk(lambda: None)) is True
        assert ts["want_speaker"] is True
    finally:
        _stop_pump(s)


def test_talk_is_not_supported_on_a_session_whose_ffmpeg_has_exited():
    # talk_supported gates async_speak's reuse of the warm session. Answering
    # from the talk-state dict alone made a dead session look talk-capable, so
    # the reuse branch was taken and no fresh session was ever opened.
    ts = _fresh_talk_state()
    assert _make_sdes(ts, alive=True).talk_supported is True
    assert _make_sdes(ts, alive=False).talk_supported is False


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
