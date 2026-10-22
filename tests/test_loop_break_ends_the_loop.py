"""A region that breaks out of a serve loop ends the loop.

The serve loops dispatch on what a region hands back: ``_LoopContinue`` is
the loop's ``continue`` and ``_LoopBreak`` its ``break``. ``_LoopBreak`` was
a subclass of ``_LoopContinue`` and both loops tested for a continue first,
so a region that broke out of the loop would have continued it instead. No
region breaks today, so this pins the dispatch before one does.
"""

from types import SimpleNamespace

import pytest

from aidot_cameras.camera import client
from aidot_cameras.camera.client import CameraMixin, _LoopBreak, _LoopContinue


def test_break_and_continue_are_not_each_other():
    assert not issubclass(_LoopBreak, _LoopContinue)
    assert not issubclass(_LoopContinue, _LoopBreak)


_LOOPS = [
    ("_sdes_keepalive_loop_inner", "_sdes_keepalive_open_attempt", "_sdes_keepalive_"),
    ("_dtls_serve_loop_inner", "_dtls_serve_loop_open_attempt", "_dtls_serve_loop_"),
]


@pytest.mark.parametrize("loop,first,prefix", _LOOPS, ids=[x[0] for x in _LOOPS])
@pytest.mark.parametrize("signal", ["break", "continue"])
async def test_the_loop_follows_the_signal(monkeypatch, loop, first, prefix, signal):
    cam = SimpleNamespace(
        _streaming_active=True,
        device_id="cam-test",
        _keepalive_rtsp_url="rtsp://127.0.0.1:1/test",
        generate_webrtc_peer_id=lambda **kw: "peer-test",
    )
    calls = []

    async def _first(self, state):
        calls.append(first)
        if len(calls) > 1:
            # A second pass means the loop went round again: stop it there.
            cam._streaming_active = False
            return client._PhaseReturn("again")
        flow = _LoopBreak if signal == "break" else _LoopContinue
        return flow()

    async def _later(self, state):
        calls.append("later region")
        return client._PhaseReturn("fell through")

    monkeypatch.setattr(client, first, _first)
    for name in dir(client):
        if (
            name.startswith(prefix)
            and name != first
            and callable(getattr(client, name))
        ):
            monkeypatch.setattr(client, name, _later)

    result = await getattr(CameraMixin, loop)(cam)

    if signal == "break":
        assert calls == [first], f"a break did not end {loop}: {calls}"
        assert result is None
    else:
        assert calls == [first, first], f"a continue did not go round {loop}: {calls}"
        assert result == "again"
