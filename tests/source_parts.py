"""The source of a split method, with the module functions it was split into.

The two stream opens and the two serve loops are orchestrators over phase and
region coroutines (``_sdes_open_*``, ``_dtls_open_*``, ``_sdes_keepalive_*``,
``_dtls_serve_loop_*``). A test that reads one of them as source to pin an
ordering or a guard needs the whole thing; this joins the method and its
parts in definition order, which is the order the orchestrator runs them, so
an index comparison means what it meant in the single-function form. Each
piece is dedented, so the text can also be parsed as a module.
"""

import importlib
import inspect
import textwrap

PARTS = {
    "_open_sdes_stream_impl": ("aidot_cameras.camera.sdes_open", "_sdes_open_"),
    "_async_open_webrtc_stream_impl": (
        "aidot_cameras.camera.webrtc_open",
        "_dtls_open_",
    ),
    "_sdes_keepalive_loop_inner": ("aidot_cameras.camera.client", "_sdes_keepalive_"),
    "_dtls_serve_loop_inner": ("aidot_cameras.camera.client", "_dtls_serve_loop_"),
}


def full_source(name: str) -> str:
    """``name``'s source on CameraMixin, then its phase/region coroutines in order."""
    from aidot_cameras.camera.client import CameraMixin

    parts = [textwrap.dedent(inspect.getsource(getattr(CameraMixin, name)))]
    if name in PARTS:
        modname, prefix = PARTS[name]
        mod = importlib.import_module(modname)
        fns = [
            getattr(mod, n)
            for n in dir(mod)
            if n.startswith(prefix) and inspect.isfunction(getattr(mod, n))
        ]
        fns.sort(key=lambda fn: fn.__code__.co_firstlineno)
        parts += [textwrap.dedent(inspect.getsource(fn)) for fn in fns]
    return "\n".join(parts)
