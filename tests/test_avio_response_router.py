"""Match an AVIO response to the command that asked for it.

Parsing a reply (``parse_avio_response``) is only half of it. The camera's
control channel carries traffic nobody asked for - session-mode notifications,
track switches, acks for commands issued by the keepalive - so "the next frame
that arrives" is not the answer to the question we just asked. Without
correlation, ``async_set_resolution`` would happily report a track-switch notify
as the camera's verdict on resolution.

Two constraints shape the design and both come from the transports:

*   **A wait must be bounded.** These calls sit behind Home Assistant service
    calls; a control command that hangs because this firmware never implements
    ``SetStreamCtrlResp`` would block a view. Timing out and saying "no reply"
    is a real answer (see the design doc: no-reply points at the channel, not
    the firmware).
*   **Responses arrive off the event loop.** On the SDES path, inbound AVIO
    frames are decrypted and dispatched on the bridge thread - the same loop
    that forwards media - while the caller awaiting the reply is on the event
    loop. Dispatch therefore has to be callable from any thread, and must never
    block the bridge.
"""
import asyncio
import struct
import threading

import pytest

from aidot_cameras.camera.protocol import AvioResponseRouter

_HDR = "<IIqII4x"

SETSTREAMCTRL_RESP = 801
TRACK_SWITCH_NOTIFY = 804


def _frame(cmd: int, payload: bytes = b"", *, seq: int = 7) -> bytes:
    return struct.pack(_HDR, seq, cmd, 0, len(payload), 0) + payload


async def test_a_matching_response_resolves_the_wait():
    """The happy path: we asked, the camera answered, we read its answer."""
    router = AvioResponseRouter()
    waiter = router.expect(SETSTREAMCTRL_RESP)
    router.dispatch(_frame(SETSTREAMCTRL_RESP, b"\x00\x05"))

    got = await waiter.result(timeout=1.0)
    assert got is not None
    assert got.command == SETSTREAMCTRL_RESP
    assert got.payload == b"\x00\x05"


async def test_an_unrelated_response_does_not_answer_our_question():
    """The whole point of correlating.

    804 (track-switch notify) is sent unsolicited by the camera. Treating it as
    the reply to 800 would turn "the camera said nothing" into a confident wrong
    answer about the resolution.
    """
    router = AvioResponseRouter()
    waiter = router.expect(SETSTREAMCTRL_RESP)
    router.dispatch(_frame(TRACK_SWITCH_NOTIFY, b"\x01"))

    assert await waiter.result(timeout=0.05) is None


async def test_no_reply_at_all_times_out_rather_than_hanging():
    """A firmware with no SetStreamCtrlResp must not wedge a control call."""
    router = AvioResponseRouter()
    waiter = router.expect(SETSTREAMCTRL_RESP)

    started = asyncio.get_running_loop().time()
    assert await waiter.result(timeout=0.05) is None
    assert asyncio.get_running_loop().time() - started < 1.0


async def test_a_timed_out_wait_stops_listening():
    """Registrations must not accumulate.

    ``_avio_cmd`` runs on the keepalive path; one leaked entry per command would
    grow without bound over a session measured in hours, and a late reply would
    resolve a waiter nobody is holding.
    """
    router = AvioResponseRouter()
    waiter = router.expect(SETSTREAMCTRL_RESP)
    assert await waiter.result(timeout=0.05) is None

    # The late reply now belongs to nobody - dispatch says so.
    assert router.dispatch(_frame(SETSTREAMCTRL_RESP, b"\x00\x05")) is False


async def test_frames_arriving_with_nobody_waiting_are_harmless():
    """The camera talks unprompted all session long; that is not an error."""
    router = AvioResponseRouter()
    assert router.dispatch(_frame(TRACK_SWITCH_NOTIFY)) is False


@pytest.mark.parametrize("blob", [b"", b"\x00" * 4, b"not an avio frame"])
async def test_junk_neither_resolves_a_wait_nor_raises(blob):
    """Dispatch sits on the bridge's hot path - it may never throw at it."""
    router = AvioResponseRouter()
    waiter = router.expect(SETSTREAMCTRL_RESP)

    assert router.dispatch(blob) is False
    assert await waiter.result(timeout=0.05) is None


async def test_two_outstanding_questions_each_get_their_own_answer():
    """Resolution and a stream-profile read can be in flight at once."""
    router = AvioResponseRouter()
    w_a = router.expect(SETSTREAMCTRL_RESP)
    w_b = router.expect(803)

    router.dispatch(_frame(803, b"\xbb"))
    router.dispatch(_frame(SETSTREAMCTRL_RESP, b"\xaa"))

    assert (await w_a.result(timeout=1.0)).payload == b"\xaa"
    assert (await w_b.result(timeout=1.0)).payload == b"\xbb"


async def test_a_duplicate_reply_does_not_disturb_the_answered_waiter():
    """Cameras retransmit. The second copy must be a no-op, not a crash."""
    router = AvioResponseRouter()
    waiter = router.expect(SETSTREAMCTRL_RESP)

    assert router.dispatch(_frame(SETSTREAMCTRL_RESP, b"\xaa")) is True
    assert router.dispatch(_frame(SETSTREAMCTRL_RESP, b"\xbb")) is False

    assert (await waiter.result(timeout=1.0)).payload == b"\xaa"


async def test_the_result_can_be_read_twice():
    """Retrieving the answer must not consume it - callers log it and return it."""
    router = AvioResponseRouter()
    waiter = router.expect(SETSTREAMCTRL_RESP)
    router.dispatch(_frame(SETSTREAMCTRL_RESP, b"\xaa"))

    first = await waiter.result(timeout=1.0)
    second = await waiter.result(timeout=1.0)
    assert first is not None
    assert first == second


async def test_cancelling_the_caller_is_not_swallowed():
    """Cancellation is not the same as "the camera did not answer".

    Home Assistant cancels these tasks on entity teardown and config-entry
    unload. Turning that into a quiet None would let the caller carry on inside
    a task the loop believes it has stopped - and the waiting-for-a-camera case
    is exactly where a slow teardown gets cancelled.
    """
    router = AvioResponseRouter()
    waiter = router.expect(SETSTREAMCTRL_RESP)

    task = asyncio.create_task(waiter.result(timeout=5.0))
    await asyncio.sleep(0.01)  # let it reach the wait
    task.cancel()
    with pytest.raises(asyncio.CancelledError):
        await task

    # ...and it still stopped listening on the way out.
    assert router.dispatch(_frame(SETSTREAMCTRL_RESP, b"\xaa")) is False


async def test_an_unanswered_wait_can_be_asked_again():
    """Reading the result twice must be safe whether or not a reply arrived.

    The success path already allows it; the timeout path must not raise the
    cancellation used internally to end the wait.
    """
    router = AvioResponseRouter()
    waiter = router.expect(SETSTREAMCTRL_RESP)

    assert await waiter.result(timeout=0.05) is None
    assert await waiter.result(timeout=0.05) is None


async def test_a_reply_dispatched_from_the_bridge_thread_reaches_the_waiter():
    """The real shape of the SDES path: decrypt on the bridge, await on the loop.

    A correlator built on a bare ``asyncio.Future`` would set its result from a
    foreign thread and the awaiting coroutine would never be woken - the bug
    would look exactly like a camera that does not answer.
    """
    router = AvioResponseRouter()
    waiter = router.expect(SETSTREAMCTRL_RESP)

    threading.Thread(
        target=router.dispatch,
        args=(_frame(SETSTREAMCTRL_RESP, b"\x00\x05"),),
        daemon=True,
    ).start()

    got = await waiter.result(timeout=2.0)
    assert got is not None
    assert got.payload == b"\x00\x05"


async def test_dispatch_does_not_block_the_bridge_thread():
    """Dispatch is called inline on the media path; it may not wait on anything."""
    router = AvioResponseRouter()
    router.expect(SETSTREAMCTRL_RESP)

    loop = asyncio.get_running_loop()
    started = loop.time()
    await asyncio.to_thread(router.dispatch, _frame(SETSTREAMCTRL_RESP, b"\xaa"))
    assert loop.time() - started < 0.5
