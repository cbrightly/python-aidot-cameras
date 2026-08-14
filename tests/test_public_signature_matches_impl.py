"""The public wrapper's signature must not drift from the impl it forwards to.

`async_open_webrtc_stream` used to be `(*args, **kwargs)`. This package ships
py.typed, so an untyped wrapper meant help(), IDE completion and downstream type
checkers all saw a function that takes anything, while the real parameter list
lived one call away in the impl.

Spelling it out fixes that and creates a new way to be wrong: the two lists can
drift. This asserts they cannot. Underscore-prefixed impl parameters are
internals and are deliberately NOT mirrored - they stay reachable through
**kwargs without being advertised.
"""

import inspect
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.webrtc_open import _WebRTCOpenMixin
from aidot_cameras.device_client import CameraDeviceClient


def _params(fn):
    return {
        name: p
        for name, p in inspect.signature(fn).parameters.items()
        if name not in ("self", "kwargs", "args")
    }


def test_every_public_impl_parameter_is_offered_by_the_wrapper():
    impl = _params(_WebRTCOpenMixin._async_open_webrtc_stream_impl)
    public = {n for n in impl if not n.startswith("_")}
    wrapper = set(_params(CameraDeviceClient.async_open_webrtc_stream))
    missing = public - wrapper
    assert not missing, f"the wrapper does not offer {sorted(missing)}"


def test_the_wrapper_invents_nothing_the_impl_cannot_take():
    impl = set(_params(_WebRTCOpenMixin._async_open_webrtc_stream_impl))
    wrapper = set(_params(CameraDeviceClient.async_open_webrtc_stream))
    extra = wrapper - impl
    assert not extra, f"the wrapper offers {sorted(extra)}, which the impl rejects"


def test_defaults_agree():
    impl = _params(_WebRTCOpenMixin._async_open_webrtc_stream_impl)
    wrapper = _params(CameraDeviceClient.async_open_webrtc_stream)
    for name, p in wrapper.items():
        assert p.default == impl[name].default, (
            f"{name} defaults to {p.default!r} on the wrapper but "
            f"{impl[name].default!r} on the impl"
        )


def test_internals_stay_unadvertised():
    """The underscore kwargs must not appear in the public signature."""
    wrapper = set(_params(CameraDeviceClient.async_open_webrtc_stream))
    assert not {n for n in wrapper if n.startswith("_")}


def test_the_deprecated_alias_warns_and_is_not_a_bare_alias():
    """A plain `x = y` alias cannot warn, so it must be its own function."""
    assert (
        CameraDeviceClient.async_open_kvs_stream
        is not CameraDeviceClient.async_open_webrtc_stream
    )
    src = inspect.getsource(CameraDeviceClient.async_open_kvs_stream)
    assert "DeprecationWarning" in src
