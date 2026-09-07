"""A connection failure has to leave its evidence at INFO.

Two rules, and both were got wrong once by demoting the whole transport state
machine to DEBUG:

1. The walk through connecting/connected/closed is commentary and belongs at
   DEBUG. ENTERING "failed" is the event, and belongs at INFO.
2. The per-transceiver DTLS/ICE dump runs only after a failure. It is the
   evidence for WHICH transport died, so it is INFO, and it must never raise -
   a diagnostic that takes down the failure path it is diagnosing is worse
   than no diagnostic at all.

Rule 2 is why these are unit tests on a seam rather than assertions about the
`@pc.on("connectionstatechange")` closure: that handler only ever runs against
a real camera that has actually failed, which is not reachable from a test and
did not occur once in 11.7 hours of production logs.
"""

import ast
import pathlib

import pytest

from aidot_cameras.camera.protocol import (
    _report_failed_transports,
    _transport_state_channel,
)


# --------------------------------------------------------------------------
# Rule 1: which channel a transport state goes out on
# --------------------------------------------------------------------------

def test_failed_state_goes_to_the_status_channel():
    assert _transport_state_channel("failed", "STATUS", "TRACE") == "STATUS"


@pytest.mark.parametrize(
    "state",
    ["new", "connecting", "connected", "completed", "checking",
     "disconnected", "closed"],
)
def test_every_other_state_goes_to_the_trace_channel(state):
    assert _transport_state_channel(state, "STATUS", "TRACE") == "TRACE"


# --------------------------------------------------------------------------
# Rule 2: the post-failure transceiver dump
# --------------------------------------------------------------------------

class _Ice:
    def __init__(self, state="failed", role="controlling"):
        self.state = state
        self._connection = type("C", (), {"role": role})()


class _Dtls:
    def __init__(self, state="failed", ice=None):
        self.state = state
        self.transport = ice if ice is not None else _Ice()


class _Receiver:
    def __init__(self, kind="video", dtls=None):
        self.track = type("T", (), {"kind": kind})() if kind else None
        self.transport = dtls if dtls is not None else _Dtls()


class _Transceiver:
    def __init__(self, kind="video", dtls=None):
        self.receiver = _Receiver(kind, dtls)


def test_reports_one_line_per_transceiver():
    said = []
    _report_failed_transports(
        lambda: [_Transceiver("video"), _Transceiver("audio")], said.append)

    assert len(said) == 2
    assert "transceiver[0]" in said[0] and "kind=video" in said[0]
    assert "transceiver[1]" in said[1] and "kind=audio" in said[1]


def test_the_line_names_the_states_that_identify_the_dead_transport():
    said = []
    ice = _Ice(state="failed", role="controlled")
    _report_failed_transports(
        lambda: [_Transceiver("video", _Dtls(state="connected", ice=ice))],
        said.append)

    line = said[0]
    assert "dtls.state=connected" in line
    assert "ice.state=failed" in line
    assert "ice.role=controlled" in line


def test_a_transceiver_that_cannot_be_read_does_not_hide_the_others():
    """The point of the dump is to find the broken one; losing the rest to it
    is the one failure mode that defeats the whole diagnostic."""
    class _Exploding:
        @property
        def receiver(self):
            raise RuntimeError("transport gone")

    said = []
    _report_failed_transports(
        lambda: [_Exploding(), _Transceiver("audio")], said.append)

    assert len(said) == 2
    assert "transceiver[0]" in said[0] and "transport gone" in said[0]
    assert "kind=audio" in said[1]


def test_a_failing_getter_is_reported_not_raised():
    def _boom():
        raise RuntimeError("peer connection closed")

    said = []
    _report_failed_transports(_boom, said.append)

    assert len(said) == 1
    assert "peer connection closed" in said[0]


def test_a_transceiver_with_no_track_still_reports():
    said = []
    _report_failed_transports(lambda: [_Transceiver(kind=None)], said.append)

    assert len(said) == 1
    assert "kind=?" in said[0]


def test_a_report_channel_that_raises_cannot_break_the_failure_path():
    """The channel is `_status`, which fans out to a caller-supplied callback.

    If that callback raises, the exception would otherwise escape into aiortc's
    event dispatch from inside the connection-failed handler - the diagnostic
    taking down the failure path it exists to diagnose.
    """
    def _boom(_msg):
        raise RuntimeError("logging is broken")

    _report_failed_transports(lambda: [_Transceiver("video")], _boom)


def test_a_raising_channel_does_not_stop_the_remaining_transceivers():
    calls = []

    def _first_one_explodes(msg):
        calls.append(msg)
        if len(calls) == 1:
            raise RuntimeError("logging is broken")

    _report_failed_transports(
        lambda: [_Transceiver("video"), _Transceiver("audio")],
        _first_one_explodes)

    assert len(calls) == 2
    assert "kind=audio" in calls[1]


# --------------------------------------------------------------------------
# The handlers must actually USE the seam above
# --------------------------------------------------------------------------
# The `@pc.on("connectionstatechange")` closure cannot be reached from a test:
# it is registered inside a 3.5k-line open against a live camera. So the rules
# are tested on the seam, and the seam is tested to be wired in. This is a
# STRUCTURAL assertion on purpose - it does not claim the handler behaves
# correctly, only that it delegates to the functions that were proven to. The
# regression it exists to catch is real and already happened once: both
# handlers were changed to log the whole state machine on one channel, which
# silently took the post-failure dump down to DEBUG with it.


def _handler_source(name):
    path = (pathlib.Path(__file__).resolve().parent.parent
            / "aidot_cameras" / "camera" / "webrtc_open.py")
    tree = ast.parse(path.read_text())
    for node in ast.walk(tree):
        if isinstance(node, (ast.FunctionDef, ast.AsyncFunctionDef)) and node.name == name:
            return node
    raise AssertionError(f"{name} not found - this test would pass on nothing")


def _called_names(node):
    return {n.func.id for n in ast.walk(node)
            if isinstance(n, ast.Call) and isinstance(n.func, ast.Name)}


@pytest.mark.parametrize(
    "handler", ["_on_conn_state", "_on_ice_state"])
def test_state_handlers_route_through_the_channel_rule(handler):
    called = _called_names(_handler_source(handler))
    assert "_transport_state_channel" in called, (
        f"{handler} picks a log channel without the rule that was tested; "
        "a bare _status( or _trace( here loses the failed/not-failed split"
    )


def test_the_failure_dump_is_delegated_not_inlined():
    node = _handler_source("_on_conn_state")
    called = _called_names(node)
    assert "_report_failed_transports" in called, (
        "the post-failure transceiver dump must go through the tested helper"
    )
