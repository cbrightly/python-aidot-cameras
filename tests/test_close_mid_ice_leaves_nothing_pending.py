"""Closing a peer connection mid-ICE leaves no STUN retries or orphan tasks.

The soak's failed DTLS opens (ICE never completes, the open times out and
``_abort_open`` closes the peer connection) logged two tracebacks per failure:

* ``Exception in callback Transaction.__retry()`` - an aioice connectivity
  check's retry timer fired after ``close()`` had closed its UDP transport,
  and ``sendto`` on the closed transport raised;
* ``Task exception was never retrieved`` from ``RTCPeerConnection.__connect``
  - the fire-and-forget connect task reached ``RTCIceTransport.start()`` after
  the transport had been stopped.

Behind the noise was a leak: the check tasks whose retry chain died never
finished, so aioice's ``connect()`` (which never got an end-of-candidates)
kept polling every 20 ms for the life of the process, once per failed open.

This drives the real vendored aiortc and aioice against a remote candidate
that never answers - the shape of a camera that does not complete ICE - and
closes while the checks are in flight.
"""

import asyncio
import gc
import socket

import aioice.stun
import pytest

from aidot_cameras._vendor.aiortc import (
    RTCConfiguration,
    RTCPeerConnection,
    RTCSessionDescription,
)
from aidot_cameras._vendor.aiortc.sdp import candidate_from_sdp


def _no_candidates(sdp: str) -> str:
    """The answer with its candidates and end-of-candidates removed.

    Without end-of-candidates aioice keeps its check loop alive waiting for
    more, which is how the cameras' answers arrive (candidates trickle in).
    """
    keep = [
        line
        for line in sdp.splitlines()
        if not line.startswith(("a=candidate", "a=end-of-candidates"))
    ]
    return "\r\n".join(keep) + "\r\n"


def _in_flight_checks(pc: RTCPeerConnection) -> list:
    tasks = []
    for transceiver in pc.getTransceivers():
        connection = transceiver.receiver.transport.transport._connection
        tasks.extend(
            p.task for p in connection._check_list if p.task and not p.task.done()
        )
    return tasks


def _leftover_tasks() -> list[str]:
    names = []
    for task in asyncio.all_tasks():
        if task is asyncio.current_task() or task.done():
            continue
        qualname = getattr(task.get_coro(), "__qualname__", "")
        if "check_start" in qualname or "__connect" in qualname:
            names.append(qualname)
    return names


@pytest.mark.timeout(30)
async def test_close_mid_ice_leaves_no_retry_callbacks_or_task_errors(monkeypatch):
    # Fast retransmissions so the retry timers fire within the test.
    monkeypatch.setattr(aioice.stun, "RETRY_RTO", 0.05)

    loop = asyncio.get_running_loop()
    errors: list[dict] = []
    previous = loop.get_exception_handler()
    loop.set_exception_handler(lambda _loop, ctx: errors.append(ctx))

    # A remote candidate that never answers: bound, so nothing is refused,
    # and never read.
    silent = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    silent.bind(("127.0.0.1", 0))
    port = silent.getsockname()[1]

    config = RTCConfiguration(iceServers=[])  # no STUN/TURN egress
    pc = RTCPeerConnection(config)
    other = RTCPeerConnection(config)
    try:
        pc.addTransceiver("video", direction="recvonly")
        pc.addTransceiver("audio", direction="recvonly")
        await pc.setLocalDescription(await pc.createOffer())

        await other.setRemoteDescription(pc.localDescription)
        await other.setLocalDescription(await other.createAnswer())
        answer = _no_candidates(other.localDescription.sdp)
        await other.close()

        await pc.setRemoteDescription(RTCSessionDescription(sdp=answer, type="answer"))
        for transceiver in pc.getTransceivers():
            candidate = candidate_from_sdp(
                f"1 1 udp 2130706431 127.0.0.1 {port} typ host"
            )
            candidate.sdpMid = transceiver.mid
            await pc.addIceCandidate(candidate)

        for _ in range(50):
            if _in_flight_checks(pc):
                break
            await asyncio.sleep(0.02)
        # Not vacuous: the close below lands while checks are retrying.
        assert _in_flight_checks(pc), "no ICE check in flight before close"
        assert pc.iceConnectionState == "checking"

        await pc.close()

        # Several RTOs: any timer that survived the close fires in here.
        await asyncio.sleep(1.0)
        gc.collect()
        await asyncio.sleep(0.05)

        messages = [f"{ctx.get('message')}: {ctx.get('exception')!r}" for ctx in errors]
        assert messages == []
        assert _leftover_tasks() == []
    finally:
        loop.set_exception_handler(previous)
        silent.close()
        await pc.close()
        await other.close()
