"""The SDES live-view session is opened talk-capable, so talk reuses it.

`async_speak` reuses `_stream_session` when it is talk-capable and otherwise
opens a session of its own. None of the loops that set `_stream_session` opened
with `talk=True`, and on SDES a session that did not ask for talk never gets a
return audio track - so the reuse branch was unreachable and every press-to-talk
opened a SECOND camera session alongside the live view. The camera holds a viewer
slot for about 120 s after a session ends, so that is not free.

The offer change this rests on is not new code: the release harness has opened
every camera talk-capable across three fleet runs and seven cameras with no
streaming regression. What is new is the keepalive loop asking for it.

Two things are locked here, because either alone would rot:
  - the SDES keepalive loop asks for talk;
  - `async_speak` really does reuse a talk-capable live session instead of
    opening another one.
"""
import asyncio
import inspect
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot_cameras.camera.client as cc


def _fn_source(qualname: str) -> str:
    for cls in vars(cc).values():
        if isinstance(cls, type) and qualname in cls.__dict__:
            return inspect.getsource(cls.__dict__[qualname])
    raise AssertionError(f"{qualname} not found")


def test_the_sdes_keepalive_loop_opens_talk_capable():
    src = _fn_source("_sdes_keepalive_loop_inner")
    # The loop has exactly one async_open_webrtc_stream call; it must ask for
    # talk, or the session it publishes as _stream_session cannot carry any.
    assert src.count("async_open_webrtc_stream") == 1, (
        "this test assumes a single open call in the loop - re-read it if that "
        "changed")
    assert "talk=True" in src, (
        "the live-view session must be talk-capable or async_speak can never "
        "reuse it")


class _Session:
    """A live, talk-capable session that records what was asked of it."""

    talk_supported = True

    def __init__(self):
        self.started = 0
        self.stopped = 0

    async def async_start_talk(self, provider):
        self.started += 1
        while provider() is not None:
            pass                       # drain the clip as the real pump would
        return True

    async def async_stop_talk(self):
        return True

    async def stop(self):
        self.stopped += 1


class _Client:
    """Just enough of the device client for async_speak's reuse branch."""

    device_id = "cam"

    def __init__(self, session):
        self._stream_session = session
        self.opens = 0

    async def async_open_webrtc_stream(self, *a, **kw):
        self.opens += 1
        raise AssertionError("async_speak must not open a second session")

    async_speak = cc.CameraMixin.async_speak


def test_async_speak_reuses_the_live_session_instead_of_opening_another():
    session = _Session()
    client = _Client(session)
    frames = iter([b"\x00" * 320] * 3)

    ok = asyncio.run(client.async_speak(lambda: next(frames, None),
                                        max_seconds=5))

    assert ok is True
    assert client.opens == 0            # no second camera session
    assert session.started == 1
    # And it must not close the session it borrowed - the live view is using it.
    assert session.stopped == 0


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
