"""Every way out of a failed DTLS open releases what the open holds.

rc53 gave the DTLS open one abort path (``_abort_open``: the MQTT sentinel,
the track tasks, the recorder, the peer connection), but the 2026-10-15
review's item 1b.7 was only half closed by it:

* the "camera declined media" exit still did its own inline teardown and never
  stopped the recorder, so the file opened in ``TsCopyRecorder.__init__``
  stayed open on every DC-only answer;
* nothing guarded the signalling and ICE waits between the offer and the
  return, so an open cancelled there (a Home Assistant stop or reload) left
  the peer connection, the track tasks and the recorder's file behind.

The open now registers ``_abort_open`` with the gated entry point, which calls
it when the open ends by any exception, and ``_abort_open`` runs only once so
the exits that already call it are unaffected.
"""

import ast
import asyncio
import pathlib

import pytest

_SRC = pathlib.Path(__file__).resolve().parents[1] / "aidot_cameras" / "camera"


def _mixin():
    import aidot_cameras.camera.client as cc

    cls = next(
        v
        for v in vars(cc).values()
        if isinstance(v, type) and "async_open_webrtc_stream" in v.__dict__
    )
    return cls.__new__(cls)


def _run_wrapper(cam, impl):
    cam._async_open_webrtc_stream_impl = impl
    cam.device_id = "dev-test"
    return asyncio.run(cam.async_open_webrtc_stream())


@pytest.mark.parametrize(
    "exc", [asyncio.CancelledError(), RuntimeError("ICE"), TimeoutError()]
)
def test_an_open_that_ends_by_any_exception_is_aborted(exc):
    aborted = []

    async def _impl(*args, _abort_holder=None, **kwargs):
        async def _abort():
            aborted.append(True)

        _abort_holder.append(_abort)
        raise exc

    with pytest.raises(type(exc)):
        _run_wrapper(_mixin(), _impl)
    assert aborted == [True]


def test_a_successful_open_is_not_aborted():
    aborted = []
    session = object()

    async def _impl(*args, _abort_holder=None, **kwargs):
        async def _abort():
            aborted.append(True)

        _abort_holder.append(_abort)
        _abort_holder.clear()  # what the open does as it hands off the session
        return session

    assert _run_wrapper(_mixin(), _impl) is session
    assert aborted == []


def test_an_open_that_fails_before_holding_anything_is_left_alone():
    # The SDES branch returns or raises before the DTLS peer connection
    # exists; there is nothing to abort and the error must pass through as is.
    async def _impl(*args, _abort_holder=None, **kwargs):
        raise ValueError("no answer")

    with pytest.raises(ValueError):
        _run_wrapper(_mixin(), _impl)


def test_a_failing_abort_does_not_replace_the_open_error():
    async def _impl(*args, _abort_holder=None, **kwargs):
        async def _abort():
            raise OSError("close failed")

        _abort_holder.append(_abort)
        raise RuntimeError("the real failure")

    with pytest.raises(RuntimeError, match="the real failure"):
        _run_wrapper(_mixin(), _impl)


def _impl_function():
    tree = ast.parse((_SRC / "webrtc_open.py").read_text())
    return next(
        n
        for n in ast.walk(tree)
        if isinstance(n, ast.AsyncFunctionDef)
        and n.name == "_async_open_webrtc_stream_impl"
    )


def test_the_open_registers_its_abort_and_clears_it_on_hand_off():
    fn = _impl_function()
    src = ast.unparse(fn)
    assert "_abort_holder.append(_abort_open)" in src
    assert "_abort_holder.clear()" in src
    abort = next(
        n
        for n in ast.walk(fn)
        if isinstance(n, ast.AsyncFunctionDef) and n.name == "_abort_open"
    )
    # Idempotent: the first statement after the docstring returns if it ran.
    first = abort.body[1]
    assert isinstance(first, ast.If) and "_aborted" in ast.unparse(first.test)


def test_the_media_declined_exit_goes_through_the_abort():
    fn = _impl_function()
    branch = next(
        n
        for n in ast.walk(fn)
        if isinstance(n, ast.If) and ast.unparse(n.test) == "_media_declined[0]"
    )
    body = "\n".join(ast.unparse(s) for s in branch.body)
    assert "await _abort_open()" in body, (
        "the DC-only exit must stop the recorder like every other exit"
    )
    assert "pc.close()" not in body, "no second hand-rolled teardown"


def test_the_sdes_to_dtls_fallback_carries_the_holder():
    fn = _impl_function()
    calls = [
        n
        for n in ast.walk(fn)
        if isinstance(n, ast.Call)
        and isinstance(n.func, ast.Attribute)
        and n.func.attr == "_async_open_webrtc_stream_impl"
    ]
    assert calls, "expected the SDES->DTLS fallback to re-enter the impl"
    for call in calls:
        assert any(k.arg == "_abort_holder" for k in call.keywords)
