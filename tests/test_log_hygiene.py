"""Four lines that filled a day's log without saying anything was wrong.

Counted on the box over 24 h (2026-10-08): the direct publisher's normal
end-of-session report logged as a WARNING headed "ffmpeg SDES stderr" (20);
one light retried a dead LAN address at WARNING every five minutes (230); a
gappy camera's "without a frame to publish" (100); and three cloud ERRORs on
every restart while the network came up. Each is demoted to where it belongs,
and the real failures keep their level.
"""

import asyncio
import logging
import os
import sys
import types

import pytest

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot_cameras.camera.client as cc
from aidot_cameras.camera import rtsp_publish as rp
from aidot_cameras.camera.sdes import SdesSession
from aidot_cameras.client import CameraClient
from aidot_cameras.lan_retry import _LOGIN_RETRY_LIMIT

REPORT = (
    b"publishing audio PCMA, video H264, audio MPEG4-GENERIC to rtsp://127.0.0.1:8554/x\n"
    b"publish ended: 19705 packets, 0 timestamp repair(s), 204 late, 600 lost"
)


def _stderr_fn(proc, last_media=1234.5):
    obj = types.SimpleNamespace(
        last_media_monotonic=last_media, _device_id="dev-abc", _proc=proc
    )
    return SdesSession._log_ffmpeg_stderr.__get__(obj)


# 1. the direct publisher's report ---------------------------------------- #


@pytest.mark.parametrize(
    ("returncode", "level"),
    [(rp.EXIT_TERMINATED, logging.INFO), (rp.EXIT_KILLED, logging.INFO)],
)
def test_the_publishers_report_after_a_requested_stop_is_information(
    caplog, returncode, level
):
    proc = types.SimpleNamespace(is_direct_publisher=True, returncode=returncode)
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras.camera.sdes"):
        _stderr_fn(proc)(REPORT)
    (rec,) = [r for r in caplog.records if "publish ended" in r.getMessage()]
    assert rec.levelno == level
    assert "direct publish report" in rec.getMessage()
    assert "ffmpeg" not in rec.getMessage()


def test_the_publishers_report_after_a_failure_still_warns(caplog):
    proc = types.SimpleNamespace(is_direct_publisher=True, returncode=rp.EXIT_FAILED)
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras.camera.sdes"):
        _stderr_fn(proc)(REPORT)
    (rec,) = [r for r in caplog.records if "publish ended" in r.getMessage()]
    assert rec.levelno == logging.WARNING


def test_a_real_ffmpegs_stderr_is_still_a_warning(caplog):
    proc = types.SimpleNamespace(returncode=rp.EXIT_TERMINATED)  # ffmpeg, not ours
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras.camera.sdes"):
        _stderr_fn(proc)(b"Error writing trailer")
    (rec,) = [r for r in caplog.records if "ffmpeg SDES stderr" in r.getMessage()]
    assert rec.levelno == logging.WARNING


def test_the_publisher_declares_itself():
    assert rp.LoopbackRtpPublisher.is_direct_publisher is True


# 2. a light's dead LAN address ------------------------------------------- #

LIGHT = {
    "id": "54ecd6e9126b",
    "name": "lamp",
    "modelId": "lk.WIFI-CCTLight-D0001",
    "aesKey": ["k" * 16],
    "password": "pw",
    "product": {
        "serviceModules": [
            {
                "identity": "control.light.cct",
                "properties": [{"minValue": "2700", "maxValue": "6500"}],
            }
        ]
    },
}


def _light(monkeypatch, outcome):
    """A light whose upstream connect() records the attempt and ends in
    ``outcome`` (True = logged in). Patched on the upstream class, so the
    retry policy's own connect() wrapper - the thing under test - runs."""
    from aidot.device_client import DeviceClient as _Upstream

    dc = CameraClient(None, country_code="US").get_device_client(LIGHT)
    kicks = []

    async def _connect(self, ip_address):
        kicks.append(ip_address)
        self._connect_and_login = outcome[0]

    monkeypatch.setattr(_Upstream, "connect", _connect)
    return dc, kicks, outcome


def test_a_dead_address_is_retried_only_up_to_the_ceiling(caplog, monkeypatch):
    # The integration pushes the discovered address on every poll (every 5
    # min), and upstream's update_ip_address starts a login each time: 230
    # "connect device error" warnings a day for one light on another subnet.
    async def _run():
        dc, kicks, _ = _light(monkeypatch, [False])
        with caplog.at_level(logging.DEBUG, logger="aidot_cameras.lan_retry"):
            for _ in range(_LOGIN_RETRY_LIMIT + 10):
                dc.update_ip_address("192.0.2.254")
                await asyncio.sleep(0)  # let the spawned login run
        return kicks

    kicks = asyncio.run(_run())
    assert len(kicks) == _LOGIN_RETRY_LIMIT
    given_up = [r for r in caplog.records if "not trying" in r.getMessage()]
    assert given_up and given_up[0].levelno == logging.WARNING
    assert len([r for r in given_up if r.levelno == logging.WARNING]) == 1  # once


def test_a_new_address_or_a_success_starts_the_count_again(monkeypatch):
    async def _run():
        dc, kicks, outcome = _light(monkeypatch, [False])
        for _ in range(_LOGIN_RETRY_LIMIT + 3):
            dc.update_ip_address("192.0.2.254")
            await asyncio.sleep(0)
        before = len(kicks)
        dc.update_ip_address("192.0.2.77")  # the sweep found it elsewhere
        await asyncio.sleep(0)
        moved = len(kicks) - before
        outcome[0] = True  # and this time it answers
        await dc.async_login()
        outcome[0] = False  # it dropped again later
        dc._connect_and_login = False
        n0 = len(kicks)
        for _ in range(3):
            dc.update_ip_address("192.0.2.77")
            await asyncio.sleep(0)
        return moved, len(kicks) - n0

    moved, after_success = asyncio.run(_run())
    assert moved == 1
    assert after_success == 3


# 3. the publisher's gap warning ------------------------------------------ #


def test_only_the_first_gap_of_a_session_is_a_warning():
    assert rp._gap_log_level(0) == logging.WARNING
    assert rp._gap_log_level(1) == logging.DEBUG
    assert rp._gap_log_level(50) == logging.DEBUG


# 4. a cloud call that failed while the network came up -------------------- #


class _Session:
    def __init__(self, outcome):
        self.outcome = outcome

    async def __aenter__(self):
        return self

    async def __aexit__(self, *a):
        return False

    def post(self, *a, **k):
        outcome = self.outcome

        class _Resp:
            status = 200

            async def __aenter__(self_):
                if isinstance(outcome, BaseException):
                    raise outcome
                return self_

            async def __aexit__(self_, *a):
                return False

            async def json(self_, content_type=None):
                return outcome

        return _Resp()


def _camera(monkeypatch, outcome):
    import aiohttp

    c = cc.CameraMixin.__new__(cc.CameraMixin)
    c._cached_device_user_info = None
    c._device_user_info_expiry = 0.0
    c.device_id = "TESTDEV"
    c._region = "us"  # _aidot_v21_base derives from it
    c._user_info = {}
    monkeypatch.setattr(aiohttp, "ClientSession", lambda *a, **k: _Session(outcome))
    return c


def _records(caplog):
    return [r for r in caplog.records if "device user info" in r.getMessage().lower()]


def test_an_unreachable_cloud_is_one_warning_then_quiet(caplog, monkeypatch):
    c = _camera(monkeypatch, TimeoutError())
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras.camera.client"):
        for _ in range(3):
            assert asyncio.run(c.async_get_device_user_info()) is None
    levels = [r.levelno for r in _records(caplog)]
    assert levels == [logging.WARNING, logging.DEBUG, logging.DEBUG]
    assert "retried" in _records(caplog)[0].getMessage()


def test_a_success_rearms_the_warning(caplog, monkeypatch):
    c = _camera(monkeypatch, TimeoutError())
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras.camera.client"):
        asyncio.run(c.async_get_device_user_info())
        _camera_ok = _camera(monkeypatch, [{"deviceId": "TESTDEV", "userId": 1}])
        c._store_device_user_info = _camera_ok._store_device_user_info
        assert asyncio.run(c.async_get_device_user_info()) is not None
        c._cached_device_user_info = None
        c._device_user_info_expiry = 0.0
        import aiohttp

        monkeypatch.setattr(
            aiohttp, "ClientSession", lambda *a, **k: _Session(OSError("down"))
        )
        asyncio.run(c.async_get_device_user_info())
    levels = [r.levelno for r in _records(caplog)]
    assert levels == [logging.WARNING, logging.WARNING]


def test_an_unexpected_failure_is_still_an_error(caplog, monkeypatch):
    c = _camera(monkeypatch, ValueError("not json"))
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras.camera.client"):
        assert asyncio.run(c.async_get_device_user_info()) is None
    assert [r.levelno for r in _records(caplog)] == [logging.ERROR]
