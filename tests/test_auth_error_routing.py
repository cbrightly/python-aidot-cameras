"""21027/21041 mean the session is void - go straight to a full login.

The three auth codes do not mean the same thing. 21026 is an expired access
token, which the refresh token can fix. 21027 and 21041 mean the session itself
is finished, and the refresh token with it - so spending a round trip on
``/users/refreshToken`` before logging in is at best waste.

It is not only waste. ``_do_ensure_token`` reaches the full re-login ONLY when
the refresh call fails. If the vendor's endpoint answers a 21027 with a fresh
access token that the server then refuses, every call site retries once with
that token, fails, and stops - no re-login, so the entry stays broken until
something else happens to trigger one.

These tests pin the routing, not the transport: which recovery a code asks for,
and that asking for a full login actually skips the refresh call.
"""
import asyncio
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from upstream_shapes import (
    account_record,
    device_record,
    patch_refresh_token_call,
    set_access_token,
    set_refresh_token,
)
from aidot_cameras.client import CameraClient
from aidot_cameras.device_client import CameraDeviceClient


def _make_dc():
    dev = {"id": "devX", "modelId": "LK.IPC.A001513", "aesKey": [None]}
    user = {"id": "u1", "accessToken": "stale"}
    return CameraDeviceClient(
        device_record(dev),
        account_record(user),
        raw_device=dev,
        login_info=dict(user),
    )


# --- which recovery does each code ask for? ---------------------------------

def test_21026_is_recoverable_by_the_refresh_token():
    assert CameraDeviceClient._auth_error_needs_full_login({"code": 21026}) is False
    assert CameraDeviceClient._auth_error_needs_full_login({"code": "21026"}) is False


def test_21027_and_21041_need_a_full_login():
    for code in (21027, 21041, "21027", "21041"):
        assert CameraDeviceClient._auth_error_needs_full_login({"code": code}) is True, code


def test_a_non_auth_body_needs_nothing():
    assert CameraDeviceClient._auth_error_needs_full_login({"code": 200}) is False
    assert CameraDeviceClient._auth_error_needs_full_login(None) is False
    assert CameraDeviceClient._auth_error_needs_full_login([]) is False


def test_a_bare_login_again_is_not_assumed_to_be_fatal():
    """The desc-only match has no code, so it cannot claim the session is void."""
    assert CameraDeviceClient._auth_error_needs_full_login(
        {"desc": "Please login again."}) is False


# --- does the classification reach the callback? ----------------------------

def test_a_21027_body_asks_the_callback_for_a_full_login():
    dc = _make_dc()
    asked = []

    async def _cb(force_login=False):
        asked.append(force_login)
        return True

    dc.set_token_refresh_cb(_cb)
    assert asyncio.run(dc._async_refresh_auth_token({"code": 21027})) is True
    assert asked == [True]


def test_a_21026_body_does_not_force_a_login():
    dc = _make_dc()
    asked = []

    async def _cb(force_login=False):
        asked.append(force_login)
        return True

    dc.set_token_refresh_cb(_cb)
    assert asyncio.run(dc._async_refresh_auth_token({"code": 21026})) is True
    assert asked == [False]


def test_a_callback_that_takes_no_argument_still_works():
    """External consumers set their own callback; do not break their signature."""
    dc = _make_dc()
    calls = []

    async def _cb():
        calls.append(1)
        return True

    dc.set_token_refresh_cb(_cb)
    assert asyncio.run(dc._async_refresh_auth_token({"code": 21027})) is True
    assert calls == [1]


# --- does a forced login actually skip the refresh call? --------------------

def test_force_login_skips_the_refresh_token_endpoint():
    client = CameraClient(None, country_code="US")
    set_access_token(client, "stale")
    set_refresh_token(client, "rt")

    refreshed, logged_in = [], []

    async def _fake_refresh():
        refreshed.append(1)
        return {"accessToken": "fresh"}

    async def _fake_login():
        logged_in.append(1)
        return client.login_info

    patch_refresh_token_call(client, _fake_refresh)
    client.async_post_login = _fake_login

    async def _run():
        ok = await client.async_ensure_token(force_login=True)
        if client._refresh_task is not None:
            client._refresh_task.cancel()
        return ok

    assert asyncio.run(_run()) is True
    assert refreshed == [], "a void session must not spend a call on refreshToken"
    assert logged_in == [1]


def test_without_force_login_the_refresh_token_is_still_tried_first():
    client = CameraClient(None, country_code="US")
    set_access_token(client, "stale")
    set_refresh_token(client, "rt")

    refreshed, logged_in = [], []

    async def _fake_refresh():
        refreshed.append(1)
        return {"accessToken": "fresh"}

    async def _fake_login():
        logged_in.append(1)
        return client.login_info

    patch_refresh_token_call(client, _fake_refresh)
    client.async_post_login = _fake_login

    async def _run():
        ok = await client.async_ensure_token()
        if client._refresh_task is not None:
            client._refresh_task.cancel()
        return ok

    assert asyncio.run(_run()) is True
    assert refreshed == [1]
    assert logged_in == [], "a refreshable token must not cost a full login"


# --- is the routing actually wired at the call sites? -----------------------

def test_every_auth_retry_passes_the_body_it_classified():
    """A classifier nothing calls with a body is dead code.

    Each retry site reads `if self._is_auth_error(X) and await
    self._async_refresh_auth_token(...)`. If the second call is bare, the code
    that decides between a refresh and a full login never sees the response and
    every code falls back to refresh-first - the exact behaviour this change
    exists to end.
    """
    import re

    src_path = os.path.join(
        os.path.dirname(os.path.dirname(os.path.abspath(__file__))),
        "aidot_cameras", "camera", "client.py",
    )
    src = open(src_path, encoding="utf-8").read()

    pattern = re.compile(
        r"_is_auth_error\((?P<body>\w+)\)\s+and\s+await\s+"
        r"self\._async_refresh_auth_token\((?P<arg>[^)]*)\)"
    )
    sites = list(pattern.finditer(src))
    assert sites, "no auth-retry call sites found - did the shape change?"

    bare = [m.group("body") for m in sites if not m.group("arg").strip()]
    assert not bare, (
        "%d of %d auth-retry sites still call _async_refresh_auth_token() with "
        "no body, so 21027/21041 cannot route to a full login: %s"
        % (len(bare), len(sites), bare)
    )

    mismatched = [
        (m.group("body"), m.group("arg"))
        for m in sites
        if m.group("arg").strip() != m.group("body")
    ]
    assert not mismatched, (
        "a retry site classified one body and refreshed on another: %s" % mismatched
    )
