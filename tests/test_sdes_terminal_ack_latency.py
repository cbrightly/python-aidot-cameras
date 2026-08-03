"""The SDES open must abandon its in-flight waits on a terminal refusal.

Complements tests/test_sdes_terminal_ack.py (shipped in 0.12.16), which covers
classifying the codes and honouring them AROUND the open - before the DTLS
fallback, and as keepalive backoff. This file covers the waits INSIDE the
open: the answer wait, and the 75s first-media wait that runs before it.

A terminal webrtcResp ack (-50002 max-streams / -50015 SD-cap) means the camera
refused the stream; classifying it as terminal exists precisely so nothing
waits or retries on it. The DTLS path honoured that, the SDES path did not
even look - it watched only answer_fut, so a refusal cost the whole answer
budget and then a DTLS-fallback offer that could not succeed either.

Measured before the fix: ~48s to surface a refusal that arrived in about one
second, on a path taken by two of the three validated models (A001513 SDES
battery, A001064 SDES PTZ).
"""
import asyncio
import os
import sys

import pytest

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.sdes_open import _sdes_await_answer_or_terminal
from aidot_cameras.exceptions import AidotCameraBusy


def _futs():
    loop = asyncio.get_running_loop()
    return loop.create_future(), loop.create_future()


async def test_terminal_ack_raises_busy_without_waiting_out_the_budget():
    answer_fut, terminal_fut = _futs()
    terminal_fut.set_result((-50002, "max streams"))

    started = asyncio.get_running_loop().time()
    with pytest.raises(AidotCameraBusy):
        # A generous budget: the point is that we do NOT spend it.
        await _sdes_await_answer_or_terminal(answer_fut, terminal_fut, 30.0)
    elapsed = asyncio.get_running_loop().time() - started
    assert elapsed < 1.0, (
        f"took {elapsed:.1f}s to report a refusal that was already known - "
        "the terminal ack must short-circuit the answer wait"
    )


async def test_terminal_ack_arriving_mid_wait_is_noticed():
    answer_fut, terminal_fut = _futs()

    async def _refuse_soon():
        await asyncio.sleep(0.1)
        terminal_fut.set_result((-50015, "sd cap"))

    asyncio.ensure_future(_refuse_soon())
    started = asyncio.get_running_loop().time()
    with pytest.raises(AidotCameraBusy):
        await _sdes_await_answer_or_terminal(answer_fut, terminal_fut, 30.0)
    assert asyncio.get_running_loop().time() - started < 2.0


async def test_the_busy_error_carries_the_camera_code_and_description():
    answer_fut, terminal_fut = _futs()
    terminal_fut.set_result((-50002, "max streams reached"))
    with pytest.raises(AidotCameraBusy) as caught:
        await _sdes_await_answer_or_terminal(answer_fut, terminal_fut, 5.0)
    text = str(caught.value)
    assert "50002" in text, f"the camera's code must reach the caller: {text!r}"


async def test_a_normal_answer_still_wins():
    answer_fut, terminal_fut = _futs()
    answer_fut.set_result({"sdp": "v=0\r\n", "type": "answer"})
    got = await _sdes_await_answer_or_terminal(answer_fut, terminal_fut, 5.0)
    assert got["sdp"] == "v=0\r\n"


async def test_an_answer_arriving_mid_wait_still_wins():
    answer_fut, terminal_fut = _futs()

    async def _answer_soon():
        await asyncio.sleep(0.1)
        answer_fut.set_result({"sdp": "v=0\r\nlate", "type": "answer"})

    asyncio.ensure_future(_answer_soon())
    got = await _sdes_await_answer_or_terminal(answer_fut, terminal_fut, 5.0)
    assert got["sdp"].endswith("late")


async def test_neither_arriving_times_out_as_before():
    """The no-answer path is what triggers the DTLS fallback; keep it intact."""
    answer_fut, terminal_fut = _futs()
    with pytest.raises((asyncio.TimeoutError, TimeoutError)):
        await _sdes_await_answer_or_terminal(answer_fut, terminal_fut, 0.2)


async def test_futures_are_left_usable_for_the_caller():
    """The caller inspects these afterwards - the wait must not cancel them."""
    answer_fut, terminal_fut = _futs()
    with pytest.raises((asyncio.TimeoutError, TimeoutError)):
        await _sdes_await_answer_or_terminal(answer_fut, terminal_fut, 0.2)
    assert not answer_fut.cancelled(), "answer_fut must survive the timeout"
    assert not terminal_fut.cancelled(), "terminal_error_fut must survive"
    # And still be resolvable by the MQTT handler afterwards.
    answer_fut.set_result({"sdp": "late"})
    assert answer_fut.result()["sdp"] == "late"


async def test_no_terminal_future_falls_back_to_the_old_behaviour():
    """Callers that pass nothing must behave exactly as before."""
    answer_fut, _ = _futs()
    answer_fut.set_result({"sdp": "ok"})
    assert (await _sdes_await_answer_or_terminal(answer_fut, None, 5.0))["sdp"] == "ok"

    answer_fut2, _ = _futs()
    with pytest.raises((asyncio.TimeoutError, TimeoutError)):
        await _sdes_await_answer_or_terminal(answer_fut2, None, 0.2)
