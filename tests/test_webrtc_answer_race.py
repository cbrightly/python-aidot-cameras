"""Future resolution from the MQTT thread must test-and-set in one loop hop.

The MQTT message handler runs on the paho network thread, not the event loop.
It used to test ``fut.done()`` on that thread and then hand the mutation to
``loop.call_soon_threadsafe(fut.set_result, ...)``, so the test and the set were
two separate hops.  The event loop stalls for seconds at a time on this path
(the synchronous STUN ``select()``), and MQTT keeps draining messages while it
does - so two webrtcResp messages can both observe ``answer_fut.done()`` False
and both schedule ``answer_fut.set_result``.

Consequences: the second answer is dropped with no log at all (the else branch
is never entered), so it never reaches the code that reads ``second_answer_fut``
- the SDES late-ICE-credential recovery and the DTLS role-reversal candidate
loop; and the duplicate ``set_result`` raises ``InvalidStateError`` inside an
asyncio callback, surfacing as an unattributed "Exception in callback" with no
camera or peerid context.  The first answer still resolves, so this is not a
loss of video.

These tests drive that interleaving deterministically: both deliveries happen
before the loop runs a single callback, which is exactly what a stalled loop
produces.
"""
import asyncio
import contextlib
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera import webrtc_open as wo

_FIRST = {"sdp": "v=0\r\nfirst"}
_SECOND = {"sdp": "v=0\r\nsecond"}


@contextlib.contextmanager
def _stalled_loop():
    """A loop that is not running, plus the callback exceptions it reports.

    Callbacks handed to it before ``run_until_complete`` sit in the ready queue
    exactly as they do while the loop is blocked in a synchronous call.  The
    exception handler is captured because a failure inside a scheduled callback
    is otherwise swallowed and the test would pass on a broken code path.
    """
    loop = asyncio.new_event_loop()
    errors: list = []
    loop.set_exception_handler(lambda _loop, ctx: errors.append(ctx))
    try:
        yield loop, errors
    finally:
        loop.close()


def _reported(errors):
    return [str(ctx.get("exception")) for ctx in errors]


def test_second_answer_is_captured_when_both_arrive_in_one_loop_stall():
    with _stalled_loop() as (loop, _errors):
        answer_fut = loop.create_future()
        second_answer_fut = loop.create_future()
        wo._deliver_webrtc_answer(loop, answer_fut, second_answer_fut, _FIRST)
        wo._deliver_webrtc_answer(loop, answer_fut, second_answer_fut, _SECOND)
        loop.run_until_complete(asyncio.sleep(0.01))

        assert answer_fut.result() == _FIRST
        assert second_answer_fut.done(), (
            "the second webrtcResp was dropped: both deliveries saw answer_fut "
            "unresolved because the done() test ran off-loop"
        )
        assert second_answer_fut.result() == _SECOND


def test_two_answers_in_one_loop_stall_raise_no_callback_exception():
    with _stalled_loop() as (loop, errors):
        answer_fut = loop.create_future()
        second_answer_fut = loop.create_future()
        wo._deliver_webrtc_answer(loop, answer_fut, second_answer_fut, _FIRST)
        wo._deliver_webrtc_answer(loop, answer_fut, second_answer_fut, _SECOND)
        loop.run_until_complete(asyncio.sleep(0.01))

        assert not errors, (
            "a duplicate set_result surfaced as an unattributed asyncio "
            f"callback exception: {_reported(errors)}"
        )


def test_duplicate_resolution_in_one_loop_stall_keeps_the_first_value():
    # Shared by the livePlayResp and terminal-ack sites, which have no paired
    # second future: the only observable harm there is the callback exception.
    with _stalled_loop() as (loop, errors):
        fut = loop.create_future()
        wo._resolve_future_threadsafe(loop, fut, "first")
        wo._resolve_future_threadsafe(loop, fut, "second")
        loop.run_until_complete(asyncio.sleep(0.01))

        assert fut.result() == "first"
        assert not errors, (
            "a duplicate set_result surfaced as an unattributed asyncio "
            f"callback exception: {_reported(errors)}"
        )
