"""Every SESSION_MODE request takes its seq from the client's counter.

The firmware of every owned model (A001064 FUN_0028fd30, A000088 V1 and V2
FUN_7024ce60, A001513 webrtc_handle_liveplay; read 2026-10-27) ignores a
SESSION_MODE whose seq is not above the last one it accepted - and still
answers it, so the sender cannot tell. A random seq per request lost about
half of them; an SD playback open whose SD request is the dropped one plays
nothing. The app counts up from 100 (sendCtrl), as ``_next_dseq`` does.
"""

import ast

from tests.ast_guards import calls_to, module_tree, name_of, parents


_MODULES = ("aidot_cameras.camera.sdes_open", "aidot_cameras.camera.webrtc_open")


def test_no_session_mode_frame_is_built_outside_the_builder():
    # Any other avio_header(..., 5376, ...) would choose its own seq.
    for mod in _MODULES:
        tree = module_tree(mod)
        up = parents(tree)
        for call in calls_to(tree, "avio_header"):
            cmd = call.args[1] if len(call.args) > 1 else None
            if getattr(cmd, "value", None) != 5376:
                continue
            fn = call
            while not isinstance(fn, (ast.FunctionDef, ast.AsyncFunctionDef)):
                fn = up[fn]
            assert fn.name == "_session_mode_req", f"{mod}:{call.lineno}"


def test_each_session_mode_seq_is_the_next_dseq():
    seen = 0
    for mod in _MODULES:
        tree = module_tree(mod)
        up = parents(tree)
        for call in calls_to(tree, "_session_mode_req"):
            seen += 1
            seq = call.args[0]
            assert isinstance(seq, ast.Name), f"{mod}:{call.lineno}"
            fn = call
            while not isinstance(fn, (ast.FunctionDef, ast.AsyncFunctionDef)):
                fn = up[fn]
            sources = [
                n.value
                for n in ast.walk(fn)
                if isinstance(n, ast.Assign)
                and any(getattr(t, "id", None) == seq.id for t in n.targets)
            ]
            # The nearest assignment above the call: the bridge reuses some of
            # these names further down for unrelated values.
            before = [s for s in sources if s.lineno <= call.lineno]
            assert before, f"{mod}:{call.lineno}: {seq.id} never assigned"
            src = max(before, key=lambda s: s.lineno)
            assert isinstance(src, ast.Call) and name_of(src) == "_next_dseq", (
                f"{mod}:{src.lineno}: {seq.id} = {ast.unparse(src)}"
            )
    assert seen, "no SESSION_MODE sender found"


def test_the_counter_is_one_locked_step():
    # The bridge thread and the event loop both take from it.
    import threading

    from aidot_cameras.camera.client import CameraMixin

    cam = CameraMixin.__new__(CameraMixin)
    cam._live_dseq = 100
    got = []

    def _take():
        for _ in range(2000):
            got.append(cam._next_dseq())

    threads = [threading.Thread(target=_take) for _ in range(4)]
    for t in threads:
        t.start()
    for t in threads:
        t.join()
    assert sorted(got) == list(range(100, 8100))
