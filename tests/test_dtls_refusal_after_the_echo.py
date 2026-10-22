"""A camera that refuses after the broker's echo ends the DTLS open there.

The 2026-10-15 review's item 1b.6. rc53 put ``terminal_error_fut`` (the
camera's -50002 / -50015 refusal) in the first signalling wait, but the
broker's echo of our own webrtcReq usually lands first. The two echo-reversal
waits that follow polled only ``{answer_fut, camera_offer_fut}``, so a refusal
that arrived during them was not seen: the open waited out 20 s, re-sent, waited
another 20 s, then fell to role reversal - which reads no refusal at all - and
failed later as an ICE timeout that the serve loop retries.

Structural guards on the AST, because the waits live inside the one long open
coroutine.
"""

import ast

from tests.ast_guards import name_of, names_in


def _awaits(stmt, name):
    """``stmt`` is ``await name(...)``."""
    return (
        isinstance(stmt, ast.Expr)
        and isinstance(stmt.value, ast.Await)
        and name_of(stmt.value.value) == name
    )


def _impl():
    # The open and the phase coroutines it was split into, as one module.
    from tests.ast_guards import dtls_open_family

    return ast.Module(body=dtls_open_family(), type_ignores=[])


def _echo_branch(fn):
    return next(
        n
        for n in ast.walk(fn)
        if isinstance(n, ast.If)
        and any(
            isinstance(c, ast.Compare)
            and isinstance(c.ops[0], ast.In)
            and name_of(c.left) == "webrtc_req_echo_fut"
            and name_of(c.comparators[0]) == "_rr_done"
            for c in ast.walk(n.test)
        )
    )


def test_the_echo_reversal_waits_keep_the_refusal_in_their_wait_set():
    branch = _echo_branch(_impl())
    seeds = [
        n
        for n in ast.walk(branch)
        if isinstance(n, (ast.Assign, ast.AnnAssign))
        and "_rr_pending2"
        in names_in(n.targets[0] if isinstance(n, ast.Assign) else n.target)
        and not isinstance(n.value, ast.Await)  # the seed, not a wait result
    ]
    assert seeds, "expected the echo branch to seed _rr_pending2"
    assert "terminal_error_fut" in names_in(seeds[0].value)


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
        assert _awaits(stmts[i + 1], "_raise_if_refused"), (
            f"the wait loop at line {loop.lineno} must be followed by the refusal check"
        )


def test_the_refusal_future_is_never_cancelled_before_it_is_read_again():
    # The final `if not _rr_done` check calls terminal_error_fut.result(); a
    # cancelled future would raise CancelledError there instead of refusing.
    branch = _echo_branch(_impl())
    tail = branch.body[-1]
    assert isinstance(tail, ast.Expr) and isinstance(tail.value, ast.Call)
    call = tail.value
    assert name_of(call) == "discard" and name_of(call.func.value) == "_rr_pending"
    assert [name_of(a) for a in call.args] == ["terminal_error_fut"]


def test_the_no_answer_exit_uses_the_same_refusal_check():
    fn = _impl()
    branch = next(
        n
        for n in ast.walk(fn)
        if isinstance(n, ast.If)
        and isinstance(n.test, ast.UnaryOp)
        and isinstance(n.test.op, ast.Not)
        and name_of(n.test.operand) == "_rr_done"
    )
    first = branch.body[0]
    assert _awaits(first, "_raise_if_refused")
    assert ast.unparse(first.value.value.args[0]) == "set()"


def test_the_first_signalling_wait_watches_the_refusal_future():
    """The first wait - for an answer, an offer or an echo - keeps the refusal
    in its wait set, and is followed by the same refusal check, so a camera
    that says -50002 is not waited out as a timeout."""
    fn = _impl()
    seeds = [
        n
        for n in ast.walk(fn)
        if isinstance(n, (ast.Assign, ast.AnnAssign))
        and "_init_pending"
        in names_in(n.targets[0] if isinstance(n, ast.Assign) else n.target)
        and isinstance(n.value, ast.Set)
    ]
    assert seeds, "the first signalling wait set moved"
    assert any("terminal_error_fut" in names_in(s.value) for s in seeds)
    checks = [
        n
        for n in ast.walk(fn)
        if _awaits(n, "_raise_if_refused")
        and [name_of(a) for a in n.value.value.args] == ["_rr_pending"]
    ]
    assert checks, "the first wait is not followed by the refusal check"
