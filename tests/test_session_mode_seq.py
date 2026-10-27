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


def test_each_session_mode_seq_is_the_next_dseq():
    tree = module_tree("aidot_cameras.camera.sdes_open")
    up = parents(tree)
    calls = [c for c in calls_to(tree, "_session_mode_req") if c.args]
    assert len(calls) == 3, [c.lineno for c in calls]
    for call in calls:
        seq = call.args[0]
        assert isinstance(seq, ast.Name), ast.unparse(call)
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
        assert before, f"{seq.id} is never assigned before line {call.lineno}"
        src = max(before, key=lambda s: s.lineno)
        assert isinstance(src, ast.Call) and name_of(src) == "_next_dseq", (
            f"line {src.lineno}: {seq.id} = {ast.unparse(src)}"
        )
