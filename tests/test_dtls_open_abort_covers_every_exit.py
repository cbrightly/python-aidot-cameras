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

import pytest


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
    # The open and the phase coroutines it was split into, as one module: the
    # abort is defined in one phase and called from the later ones.
    from tests.ast_guards import dtls_open_family

    return ast.Module(body=dtls_open_family(), type_ignores=[])


def test_the_open_registers_its_abort_and_clears_it_on_hand_off():
    from tests.ast_guards import calls_to, name_of, names_in

    fn = _impl_function()
    appends = [
        c
        for c in calls_to(fn, "append")
        if name_of(c.func.value) == "_abort_holder"
        and [name_of(a) for a in c.args] == ["_abort_open"]
    ]
    assert appends, "the open does not register its abort"
    assert [
        c for c in calls_to(fn, "clear") if name_of(c.func.value) == "_abort_holder"
    ]
    abort = next(
        n
        for n in ast.walk(fn)
        if isinstance(n, ast.AsyncFunctionDef) and n.name == "_abort_open"
    )
    # Idempotent: the first statement after the docstring returns if it ran.
    first = abort.body[1]
    assert isinstance(first, ast.If) and "_aborted" in names_in(first.test)


def test_the_media_declined_exit_goes_through_the_abort():
    from tests.ast_guards import calls_to, name_of

    fn = _impl_function()
    branch = next(
        n
        for n in ast.walk(fn)
        if isinstance(n, ast.If)
        and isinstance(n.test, ast.Subscript)
        and name_of(n.test.value) == "_media_declined"
    )
    body = ast.Module(body=branch.body, type_ignores=[])
    assert [
        a
        for a in ast.walk(body)
        if isinstance(a, ast.Await) and name_of(a.value) == "_abort_open"
    ], "the DC-only exit must stop the recorder like every other exit"
    assert not [c for c in calls_to(body, "close") if name_of(c.func.value) == "pc"], (
        "no second hand-rolled teardown"
    )


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
