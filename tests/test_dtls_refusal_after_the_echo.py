"""A camera that refuses after the broker's echo ends the DTLS open there.

The 2026-10-15 review's item 1b.6. rc53 put ``terminal_error_fut`` (the
camera's -50002 / -50015 refusal) in the first signalling wait, but the
broker's echo of our own webrtcReq usually lands first. The two echo-reversal
waits that follow polled only ``{answer_fut, camera_offer_fut}``, so a refusal
that arrived during them was not seen: the open waited out 20 s, re-sent, waited
another 20 s, then fell to role reversal - which reads no refusal at all - and
failed later as an ICE timeout that the serve loop retries.

Source guards, because the waits live inside the one long open coroutine.
"""

import ast
import pathlib

_SRC = (
    pathlib.Path(__file__).resolve().parents[1]
    / "aidot_cameras"
    / "camera"
    / "webrtc_open.py"
)


def _impl():
    tree = ast.parse(_SRC.read_text())
    return next(
        n
        for n in ast.walk(tree)
        if isinstance(n, ast.AsyncFunctionDef)
        and n.name == "_async_open_webrtc_stream_impl"
    )


def _echo_branch(fn):
    return next(
        n
        for n in ast.walk(fn)
        if isinstance(n, ast.If)
        and "webrtc_req_echo_fut in _rr_done" in ast.unparse(n.test)
    )


def test_the_echo_reversal_waits_keep_the_refusal_in_their_wait_set():
    branch = _echo_branch(_impl())
    seeds = [
        n
        for n in ast.walk(branch)
        if isinstance(n, (ast.Assign, ast.AnnAssign))
        and "_rr_pending2" in ast.unparse(n).split("=")[0]
        and "asyncio.wait" not in ast.unparse(n)
    ]
    assert seeds, "expected the echo branch to seed _rr_pending2"
    assert "terminal_error_fut" in ast.unparse(seeds[0].value)


def test_each_echo_reversal_wait_is_followed_by_the_refusal_check():
    branch = _echo_branch(_impl())
    loops = [
        n
        for n in ast.walk(branch)
        if isinstance(n, ast.While) and "asyncio.wait" in ast.unparse(n)
    ]
    assert len(loops) == 2, "expected the 20 s wait and the extended wait"
    parents = {}
    for node in ast.walk(branch):
        for field in ("body", "orelse"):
            stmts = getattr(node, field, None)
            if isinstance(stmts, list):
                for i, st in enumerate(stmts):
                    parents[st] = (stmts, i)
    for loop in loops:
        stmts, i = parents[loop]
        after = ast.unparse(stmts[i + 1])
        assert after.startswith("await _raise_if_refused("), (
            f"the wait loop at line {loop.lineno} must be followed by the refusal check"
        )


def test_the_refusal_future_is_never_cancelled_before_it_is_read_again():
    # The final `if not _rr_done` check calls terminal_error_fut.result(); a
    # cancelled future would raise CancelledError there instead of refusing.
    branch = _echo_branch(_impl())
    tail = ast.unparse(branch.body[-1])
    assert tail == "_rr_pending.discard(terminal_error_fut)"


def test_the_no_answer_exit_uses_the_same_refusal_check():
    fn = _impl()
    branch = next(
        n
        for n in ast.walk(fn)
        if isinstance(n, ast.If) and ast.unparse(n.test) == "not _rr_done"
    )
    assert ast.unparse(branch.body[0]) == "await _raise_if_refused(set())"
