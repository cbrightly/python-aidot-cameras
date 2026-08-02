"""``_viewer_present`` in SDES push mode, and the ``go2rtc_register`` seam.

Why this file exists, in one paragraph:

In push mode the "serve port" is go2rtc's SHARED RTSP port (8554), where every
camera's own publisher is connected - so a socket check there reports a viewer
for every camera forever. ``_viewer_present`` therefore refuses to use it and
answers "unknown", and ``_idle_release_due`` correctly never releases on
unknown. That is all deliberate and right. The trap is what it composes into: a
consumer that withholds ``go2rtc_url`` (to avoid a duplicate registration that
re-points the stream mid-flight) removes the ONLY viewer signal push mode has,
so every push-mode camera is pinned to "unknown" and a battery camera's
keepalive renews forever against a camera nobody is watching. Observed live: a
sleeping battery L2 still being renewed every ~100 s.

``go2rtc_register=False`` is the way out - keep the query, drop the
registration. These tests pin the decision table so neither half can regress
silently, since both failure modes are quiet ones.
"""

import asyncio
import types

import pytest

from aidot_cameras.camera.protocol import _idle_release_due

PUSH_URL = "rtsp://127.0.0.1:8554/aidot_cam1"     # go2rtc's shared RTSP port
HTTP_SERVE = "http://127.0.0.1:18000/aidot_cam1"  # our own -listen socket


def _client(go2rtc_url=None, keepalive_url=PUSH_URL, viewers=None, raises=False):
    """A stand-in exposing exactly what ``_viewer_present`` touches.

    Built by binding the real method onto a namespace rather than constructing a
    CameraDeviceClient, which would need a device record, an account and a live
    loop - none of which this decision depends on.
    """
    from aidot_cameras.camera.client import CameraMixin

    obj = types.SimpleNamespace(
        # Sentinel, not None: if the throttle ever short-circuits, the test
        # returns "STALE" and fails loudly instead of passing vacuously
        # because the cached value happened to match the expected answer.
        _viewer_cache=(0.0, "STALE"),
        _go2rtc_url=go2rtc_url,
        _keepalive_rtsp_url=keepalive_url,
        _go2rtc_stream_name=lambda: "aidot_cam1",
        _sdes_serve_consumer_present=lambda port: True,  # the WRONG answer in push mode
    )

    async def _fake_count(self, name):
        if raises:
            raise RuntimeError("go2rtc unreachable")
        return viewers

    obj._viewer_present = CameraMixin._viewer_present.__get__(obj)
    obj._fake_count = _fake_count
    return obj


def _ask(obj, monkeypatch, port=8554):
    """Call _viewer_present with the go2rtc client stubbed out."""
    from aidot_cameras.camera import client as client_mod

    class _Stub:
        def __init__(self, session, base):
            pass

        async def viewer_count(self, name):
            return await obj._fake_count(obj, name)

    class _Sess:
        async def __aenter__(self):
            return self

        async def __aexit__(self, *a):
            return False

    import aidot_cameras.camera.go2rtc as g2
    monkeypatch.setattr(g2, "Go2rtcClient", _Stub)
    monkeypatch.setattr(client_mod, "_VIEWER_CHECK_INTERVAL_S", 0, raising=False)

    import aiohttp
    monkeypatch.setattr(aiohttp, "ClientSession", lambda *a, **k: _Sess())
    return asyncio.run(obj._viewer_present(port))


# --------------------------------------------------------------------------- #
# push mode: the socket fallback must never be consulted
# --------------------------------------------------------------------------- #

def test_push_mode_without_go2rtc_is_unknown_not_a_confident_lie(monkeypatch):
    """No go2rtc + push mode -> unknown.

    The stub's socket check returns True; if it were ever consulted the answer
    would be True, every camera would look watched, and nothing would release.
    """
    obj = _client(go2rtc_url=None)
    assert _ask(obj, monkeypatch) is None


def test_push_mode_falls_back_to_unknown_when_go2rtc_query_fails(monkeypatch):
    """go2rtc configured but unreachable must NOT drop to the socket check.

    This is the case the restructure fixed: previously the shared-port guard sat
    behind `if not base`, so configuring go2rtc and having the query fail fell
    straight through to the wrong check.
    """
    obj = _client(go2rtc_url="http://go2rtc:1984", raises=True)
    assert _ask(obj, monkeypatch) is None


def test_push_mode_falls_back_to_unknown_when_go2rtc_returns_none(monkeypatch):
    obj = _client(go2rtc_url="http://go2rtc:1984", viewers=None)
    assert _ask(obj, monkeypatch) is None


# --------------------------------------------------------------------------- #
# push mode WITH go2rtc: real answers, which is the whole point
# --------------------------------------------------------------------------- #

def test_push_mode_with_go2rtc_reports_a_real_viewer(monkeypatch):
    obj = _client(go2rtc_url="http://go2rtc:1984", viewers=2)
    assert _ask(obj, monkeypatch) is True


def test_push_mode_with_go2rtc_reports_nobody_watching(monkeypatch):
    """The answer that actually lets a battery camera release.

    Without go2rtc this is unreachable in push mode - it answers unknown, and
    unknown never releases.
    """
    obj = _client(go2rtc_url="http://go2rtc:1984", viewers=0)
    assert _ask(obj, monkeypatch) is False


# --------------------------------------------------------------------------- #
# non-push (our own HTTP -listen serve): the socket check is legitimate there
# --------------------------------------------------------------------------- #

def test_http_serve_still_uses_the_socket_check(monkeypatch):
    """On our own serve port the socket check is meaningful and must be kept."""
    obj = _client(go2rtc_url=None, keepalive_url=HTTP_SERVE)
    assert _ask(obj, monkeypatch) is True


# --------------------------------------------------------------------------- #
# the composition that caused the live bug
# --------------------------------------------------------------------------- #

@pytest.mark.parametrize("answer,expected_release", [
    (True, False),    # someone watching -> hold
    (None, False),    # unknown          -> hold (fail-safe)
    (False, True),    # nobody watching  -> release
])
def test_only_a_definite_no_releases(answer, expected_release):
    """`unknown` must never release - and therefore must never be the only answer.

    Pinned here next to the push-mode tests because the live bug was not in
    either half: `_viewer_present` answering unknown is right, and
    `_idle_release_due` holding on unknown is right. The defect was that push
    mode without go2rtc made unknown the *only reachable* answer, so "never
    release" became unconditional.
    """
    assert _idle_release_due(answer, last_consumer=0.0, now=10_000.0,
                             idle_secs=120.0) is expected_release


# --------------------------------------------------------------------------- #
# the registration seam
# --------------------------------------------------------------------------- #

def test_start_keepalive_exposes_go2rtc_register():
    import inspect

    from aidot_cameras.camera.client import CameraMixin

    params = inspect.signature(CameraMixin.start_keepalive).parameters
    assert "go2rtc_register" in params
    assert params["go2rtc_register"].default is True, (
        "default must stay True so existing callers are unaffected"
    )
