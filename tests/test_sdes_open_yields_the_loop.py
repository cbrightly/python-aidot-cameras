"""The SDES open must not block the event loop while it waits on its sockets.

The STUN-responder window and its retry polled the media sockets with a
blocking `select.select(..., 0.1)` inside the open coroutine for up to 20 s and
8 s, and the three TURN allocates ran two blocking 2 s request/response
exchanges per socket the same way. Every other camera's signalling, and Home
Assistant's own loop, stood still for that long on every SDES open (review
2026-10-15, 1b item 9).
"""

import ast
import asyncio
import inspect
import socket
import time

import pytest

from aidot_cameras.camera import sdes_open
from aidot_cameras.camera.sdes_open import _run_off_loop, _select_readable


def _udp_pair():
    a = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    b = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    a.bind(("127.0.0.1", 0))
    b.bind(("127.0.0.1", 0))
    a.setblocking(False)
    b.setblocking(False)
    return a, b


async def _max_gap_while(coro, tick=0.01):
    """Run coro alongside a ticker; return (result, the longest loop stall)."""
    gaps = []
    stop = False

    async def _ticker():
        last = time.monotonic()
        while not stop:
            await asyncio.sleep(tick)
            now = time.monotonic()
            gaps.append(now - last)
            last = now

    t = asyncio.create_task(_ticker())
    try:
        result = await coro
    finally:
        stop = True
        await t
    return result, max(gaps or [0.0])


async def test_waiting_on_silent_sockets_does_not_stall_the_loop():
    a, b = _udp_pair()
    try:
        rlist, gap = await _max_gap_while(_select_readable([a, b], 0.3))
        assert rlist == []
        assert gap < 0.1, f"the loop stood still for {gap:.3f} s"
    finally:
        a.close()
        b.close()


async def test_a_readable_socket_is_returned_without_waiting_the_timeout():
    a, b = _udp_pair()
    try:
        b.sendto(b"x", a.getsockname())
        t0 = time.monotonic()
        rlist = await _select_readable([a, b], 5.0)
        assert rlist == [a]
        assert time.monotonic() - t0 < 0.5
    finally:
        a.close()
        b.close()


async def test_data_arriving_mid_wait_ends_the_wait():
    a, b = _udp_pair()
    try:
        asyncio.get_running_loop().call_later(0.05, b.sendto, b"x", a.getsockname())
        t0 = time.monotonic()
        rlist = await _select_readable([a, b], 5.0)
        assert rlist == [a]
        assert time.monotonic() - t0 < 1.0
    finally:
        a.close()
        b.close()


async def test_a_closed_socket_raises_like_select_did():
    """The windows break on an exception from the wait; keep that contract."""
    a, b = _udp_pair()
    b.close()
    try:
        with pytest.raises((OSError, ValueError)):
            await _select_readable([a, b], 0.1)
    finally:
        a.close()


def _open_impl_node():
    tree = ast.parse(inspect.getsource(sdes_open))
    for node in ast.walk(tree):
        if (
            isinstance(node, ast.AsyncFunctionDef)
            and node.name == "_open_sdes_stream_impl"
        ):
            return node
    raise AssertionError("_open_sdes_stream_impl not found")


def _calls_on_the_coroutine_body(fn_node):
    """Calls whose nearest enclosing function is the coroutine itself.

    Calls inside nested sync defs (the bridge thread, the allocate helper)
    run off the loop and are not this test's concern.
    """
    found = []

    def visit(node, owner):
        for child in ast.iter_child_nodes(node):
            if isinstance(child, (ast.FunctionDef, ast.AsyncFunctionDef, ast.Lambda)):
                visit(child, child)
                continue
            if isinstance(child, ast.Call) and owner is fn_node:
                found.append(child)
            visit(child, owner)

    visit(fn_node, fn_node)
    return found


def _name(call):
    f = call.func
    if isinstance(f, ast.Attribute):
        return f.attr
    if isinstance(f, ast.Name):
        return f.id
    return ""


def test_the_open_coroutine_makes_no_blocking_socket_waits():
    calls = _calls_on_the_coroutine_body(_open_impl_node())
    blocking = [
        (c.lineno, _name(c))
        for c in calls
        if _name(c) in {"select", "_turn_allocate_udp"}
    ]
    assert not blocking, (
        "blocking socket wait on the event loop inside the SDES open: "
        f"{blocking}; use _select_readable or run it in an executor"
    )


def test_the_allocates_still_happen_off_the_loop():
    """Guard the guard: the allocate must still be reached, via the executor."""
    calls = _calls_on_the_coroutine_body(_open_impl_node())
    via_executor = [
        c
        for c in calls
        if _name(c) == "_run_off_loop"
        and c.args
        and isinstance(c.args[0], ast.Name)
        and c.args[0].id == "_turn_allocate_udp"
    ]
    assert len(via_executor) == 3


async def test_a_cancelled_caller_waits_out_the_blocking_call():
    """The allocate uses the session's sockets; cleanup must not close them
    under a worker thread that is still running."""
    import threading

    finished = threading.Event()

    def _slow():
        time.sleep(0.2)
        finished.set()
        return "done"

    task = asyncio.create_task(_run_off_loop(_slow))
    await asyncio.sleep(0.02)
    task.cancel()
    with pytest.raises(asyncio.CancelledError):
        await task
    assert finished.is_set(), "cancellation returned before the worker finished"


async def test_the_result_comes_back():
    assert await _run_off_loop(lambda a, b: a + b, 2, 3) == 5
