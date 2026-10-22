"""Demote expected ffmpeg signal-death warnings on locally-initiated teardown.

Teardown is SIGTERM-first with a SIGKILL fallback (SdesSession.stop(),
sdes.py); a battery camera's ffmpeg cannot exit promptly on a dead UDP input,
so the 5s wait routinely times out and the library SIGKILLs it. The bridge
observe loop (sdes_open.py) used to warn on ANY non-zero exit code, so a
completely normal teardown logged a WARNING ("ffmpeg exited with code -9").

A shared "teardown requested" flag is set by every locally-initiated
ffmpeg-kill path (SdesSession.stop(), the cold-open _reap(), the key-restart
proc replace, and the DTLS-fallback abort) and consulted by
_classify_ffmpeg_exit() to decide the log level: a signal death (negative rc)
during a requested teardown is expected -> DEBUG. Anything else (no teardown
in flight, or a positive ffmpeg error code even during teardown) is still
unexpected -> WARNING.
"""

import asyncio
import logging

from aidot_cameras.camera.sdes_open import _classify_ffmpeg_exit
from aidot_cameras.camera.sdes import SdesSession


def test_signal_death_during_teardown_is_quiet():
    assert _classify_ffmpeg_exit(-9, True) <= logging.INFO


def test_signal_death_without_teardown_still_warns():
    assert _classify_ffmpeg_exit(-9, False) == logging.WARNING


def test_error_code_during_teardown_still_warns():
    # Positive rc is a genuine ffmpeg error, not a signal death - even if a
    # teardown happened to be in flight, this is unexpected and stays loud.
    assert _classify_ffmpeg_exit(1, True) == logging.WARNING


def test_sigterm_death_during_teardown_is_quiet():
    assert _classify_ffmpeg_exit(-15, True) <= logging.INFO


def test_reap_sets_teardown_flag_before_kill():
    """_reap (the cold-open kill site) is a closure deep in the open, so it is
    checked structurally: like the other three locally-initiated kill sites it
    sets the teardown flag before it kills, so the bridge thread can never see
    the exit code with the flag still False."""
    import ast

    from tests.ast_guards import calls_to, function, module_tree

    reap = function(module_tree("aidot_cameras.camera.sdes_open"), "_reap")
    flags = [
        n.lineno
        for n in ast.walk(reap)
        if isinstance(n, ast.Assign)
        and any(ast.unparse(t) == "_teardown_holder[0]" for t in n.targets)
        and ast.unparse(n.value) == "True"
    ]
    kills = [c.lineno for c in calls_to(reap, "kill")]
    assert flags and kills
    assert min(flags) < min(kills)


def test_the_bridge_exit_log_names_the_camera():
    """Several SDES cameras stream at once; an exit line without a device id
    cannot be attributed. The bridge's exit lines and the serve's stderr line
    carry one, and the session is handed the id its own stderr log needs."""
    import ast

    from tests.ast_guards import calls_to, module_tree, sdes_bridge, string_parts

    tree = module_tree("aidot_cameras.camera.sdes_open")
    bridge = sdes_bridge()
    # What each name in the bridge was assigned: the exit line's format is
    # chosen into a local, and the device id is read into one.
    assigned = {
        t.id: n.value
        for n in ast.walk(bridge)
        if isinstance(n, ast.Assign)
        for t in n.targets
        if isinstance(t, ast.Name)
    }

    def text(node):
        node = assigned.get(getattr(node, "id", None), node)
        if isinstance(node, ast.IfExp):
            return text(node.body) + text(node.orelse)
        return string_parts(node)

    exits = []
    for call in ast.walk(bridge):
        if not (isinstance(call, ast.Call) and isinstance(call.func, ast.Attribute)):
            continue
        if call.func.attr not in ("debug", "info", "warning", "error", "log"):
            continue
        args = call.args[1:] if call.func.attr == "log" else call.args
        if args and (
            "ffmpeg exited" in text(args[0]) or "ffmpeg stderr" in text(args[0])
        ):
            exits.append((call, args))
    assert len(exits) >= 2, "the bridge exit log lines moved"
    for call, args in exits:
        assert text(args[0]).startswith("camera %s"), call.lineno
        dev = assigned.get(getattr(args[1], "id", None), args[1])
        assert "device_id" in ast.unparse(dev), call.lineno
    sessions = calls_to(tree, "SdesSession")
    assert sessions and all(
        any(k.arg == "device_id" for k in s.keywords) for s in sessions
    ), "SdesSession is constructed without a device_id"


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
    def close(self):
        pass


class _FakeQ:
    def put_nowait(self, _x):
        pass


def test_session_stop_sets_shared_teardown_flag():
    holder = [False]
    s = SdesSession(
        proc=_FakeProc(),
        sdp_path="/tmp/aidot_test_does_not_exist.sdp",
        outgoing_q=_FakeQ(),
        mqtt_fut=None,
        audio_sock=_FakeSock(),
        video_sock=_FakeSock(),
        cmd_chan=[None],
        talk_state=None,
        teardown_requested=holder,
    )
    assert holder[0] is False
    asyncio.run(s.stop())
    assert holder[0] is True


def test_session_stop_without_shared_holder_does_not_crash():
    # No teardown_requested passed - stop() must still work (defaults to a
    # private holder) rather than raising AttributeError.
    s = SdesSession(
        proc=_FakeProc(),
        sdp_path="/tmp/aidot_test_does_not_exist.sdp",
        outgoing_q=_FakeQ(),
        mqtt_fut=None,
        audio_sock=_FakeSock(),
        video_sock=_FakeSock(),
        cmd_chan=[None],
        talk_state=None,
    )
    asyncio.run(s.stop())
