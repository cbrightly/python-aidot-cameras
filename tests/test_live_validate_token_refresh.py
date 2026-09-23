"""The live gate must exercise a REAL token refresh, and fail when it breaks.

python-aidot 0.3.57 moved the cloud API from v17 to v35.  Live validation then
proved a password login on v35, but nothing exercised ``users/refreshToken``:
the harness logs in once and never refreshes.  A broken refresh would only show
up in the field, hours later, when a token expired.

The check calls ``_upstream.api_refresh_token`` directly.  It deliberately does
NOT go through ``CameraClient.async_ensure_token``, which falls back to a full
password re-login when the refresh fails - a check built on it would pass with
refresh completely broken.
"""

import argparse
import asyncio
import importlib.util
import json
import os
import sys

import pytest

from aidot_cameras.const import CONF_ACCESS_TOKEN, CONF_REFRESH_TOKEN
from aidot_cameras.exceptions import AidotAuthFailed

SCRIPT = os.path.join(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))),
    "scripts",
    "live_validate.py",
)

OLD_ACCESS, NEW_ACCESS = "old-access-SECRET", "new-access-SECRET"
OLD_REFRESH, NEW_REFRESH = "old-refresh-SECRET", "new-refresh-SECRET"


@pytest.fixture(scope="module")
def lv():
    spec = importlib.util.spec_from_file_location("live_validate", SCRIPT)
    mod = importlib.util.module_from_spec(spec)
    sys.modules["live_validate"] = mod
    spec.loader.exec_module(mod)
    return mod


class _FakeClient:
    """Just the surface the check touches, behaving like upstream's dict shape.

    Upstream stores exactly what the refresh response returns
    (``login_info[accessToken] = response[accessToken]``), so the fake does too
    rather than rotating ``login_info`` behind the response's back.  The one
    deliberate exception is ``lost``, which models that store going missing.
    """

    def __init__(self, *, refresh="rotate", houses_ok=True):
        self.login_info = {
            CONF_ACCESS_TOKEN: OLD_ACCESS,
            CONF_REFRESH_TOKEN: OLD_REFRESH,
        }
        self._refresh = refresh
        self._houses_ok = houses_ok
        self.logins = 0

    async def async_refresh_token(self):
        mode = self._refresh
        if mode == "auth_failed":
            raise AidotAuthFailed()
        if mode == "none":
            return None
        body = {
            "rotate": {CONF_ACCESS_TOKEN: NEW_ACCESS, CONF_REFRESH_TOKEN: NEW_REFRESH},
            # What the v35 cloud really did on 2026-09-23: it accepted the
            # refresh token and handed back the still-valid tokens unchanged.
            "reissue": {CONF_ACCESS_TOKEN: OLD_ACCESS, CONF_REFRESH_TOKEN: OLD_REFRESH},
            "null_token": {CONF_ACCESS_TOKEN: None, CONF_REFRESH_TOKEN: None},
            "lost": {CONF_ACCESS_TOKEN: NEW_ACCESS, CONF_REFRESH_TOKEN: NEW_REFRESH},
        }[mode]
        body["expiresIn"] = 3600
        if mode != "lost":
            self.login_info[CONF_ACCESS_TOKEN] = body[CONF_ACCESS_TOKEN]
            if body[CONF_REFRESH_TOKEN] is not None:
                self.login_info[CONF_REFRESH_TOKEN] = body[CONF_REFRESH_TOKEN]
        return body

    async def async_get_houses(self):
        if not self._houses_ok:
            raise AidotAuthFailed()
        return [{"id": "h1"}]

    # The fallback the check must never take.
    async def async_post_login(self):
        self.logins += 1
        return self.login_info


@pytest.fixture(autouse=True)
def _dict_shape(monkeypatch):
    from aidot_cameras import _upstream

    monkeypatch.setattr(_upstream, "HAS_TYPED_ACCOUNT", False)


def _check(lv, client):
    return asyncio.run(lv._check_token_refresh(client))


def test_a_rotating_refresh_passes(lv):
    result = _check(lv, _FakeClient())
    assert result["ok"] is True, result
    assert result["access_rotated"] is True
    assert result["refresh_rotated"] is True


def test_a_refresh_that_reissues_the_current_token_passes(lv):
    """The server may hand back the token it already issued while it is valid.

    That is what the v35 cloud did on the first live run of this check
    (access_is_previous and refresh_is_previous both true, and login_info
    holding exactly what the response returned).  The first version of this
    check demanded rotation and failed that run; rotation is now recorded, not
    required.
    """
    result = _check(lv, _FakeClient(refresh="reissue"))
    assert result["ok"] is True, result
    assert result["access_rotated"] is False
    assert result["response"]["access_is_previous"] is True


@pytest.mark.parametrize(
    "client_kwargs, why",
    [
        ({"refresh": "none"}, "no response"),
        ({"refresh": "auth_failed"}, "rejected"),
        ({"refresh": "null_token"}, "no access token"),
        ({"refresh": "lost"}, "did not reach login_info"),
        ({"houses_ok": False}, "refused"),
    ],
    ids=["no-response", "rejected", "null-token", "not-stored", "token-unusable"],
)
def test_a_broken_refresh_fails(lv, client_kwargs, why):
    result = _check(lv, _FakeClient(**client_kwargs))
    assert result["ok"] is False, result
    assert why in result["detail"], result["detail"]


def test_a_failed_refresh_never_falls_back_to_a_password_login(lv):
    """The reason the check bypasses async_ensure_token."""
    client = _FakeClient(refresh="auth_failed")
    _check(lv, client)
    assert client.logins == 0


def test_the_result_never_carries_a_token(lv):
    for kwargs in (
        {},
        {"refresh": "reissue"},
        {"refresh": "auth_failed"},
        {"refresh": "null_token"},
        {"refresh": "lost"},
    ):
        text = json.dumps(_check(lv, _FakeClient(**kwargs)))
        for secret in (OLD_ACCESS, NEW_ACCESS, OLD_REFRESH, NEW_REFRESH):
            assert secret not in text, text


# --------------------------------------------------------------------------- #
# It gates
# --------------------------------------------------------------------------- #


def _passing_report(lv):
    cams = [
        {
            "name": f"cam-{m}",
            "model": f"LK.IPC.{m}",
            "verdict": "PASS",
            "tier": "required",
            "transport": "DTLS",
            "attempts_used": 1,
        }
        for m in lv.REQUIRED_MODELS
    ]
    return {"cameras": cams}


def test_a_failed_refresh_fails_the_run(lv, tmp_path):
    report = _passing_report(lv)
    report["token_refresh"] = {"ok": False, "detail": "rejected"}
    rc = lv._summarize(report, argparse.Namespace(json_out=str(tmp_path / "r.json")))
    assert rc == 1
    assert report["verdict"] == "FAIL"


def test_a_passing_refresh_does_not_block_the_run(lv, tmp_path):
    report = _passing_report(lv)
    report["token_refresh"] = {"ok": True, "detail": "ok"}
    rc = lv._summarize(report, argparse.Namespace(json_out=str(tmp_path / "r.json")))
    assert rc == 0
    assert report["verdict"] == "PASS"


def test_the_result_says_where_a_refresh_went_wrong(lv):
    """Enough to tell 're-issued the same token' from 'a new token never
    reached login_info' - as booleans and key names, never values."""
    diag = _check(lv, _FakeClient(refresh="lost"))["response"]
    assert diag["keys"] == ["accessToken", "expiresIn", "refreshToken"]
    assert diag["has_access"] is True
    assert diag["access_is_previous"] is False
    assert diag["stored_access_is_response"] is False

    diag = _check(lv, _FakeClient(refresh="reissue"))["response"]
    assert diag["access_is_previous"] is True
    assert diag["stored_access_is_response"] is True
    assert all(isinstance(v, bool) for k, v in diag.items() if k != "keys"), diag
