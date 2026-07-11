"""Serve/keepalive open-failure logging level.

A stream-open failure logs at WARNING normally, but at DEBUG when the cloud has
explicitly reported the device offline - a dead/unpowered camera is throttled to
probe cadence by the offline pause and should not drip WARNINGs every probe.
"""
import types

from aidot.camera import client as camclient


def _dc(online, explicit):
    o = types.SimpleNamespace()
    o.status = types.SimpleNamespace(online=online)
    o._cloud_online_explicit = explicit
    return camclient.CameraMixin._open_fail_logger.__get__(o)


def test_debug_when_cloud_reports_offline():
    assert _dc(online=False, explicit=True)() == camclient._LOGGER.debug


def test_warning_when_online():
    assert _dc(online=True, explicit=True)() == camclient._LOGGER.warning


def test_warning_when_online_state_unknown():
    # Never explicitly reported offline -> treat a failure as genuine (WARNING).
    assert _dc(online=False, explicit=False)() == camclient._LOGGER.warning
