"""Per-client stream settings (``StreamSettings``) and their one precedence rule.

A value set on the client wins, then the environment variable if it is set,
then the built-in default. A client given nothing reads exactly as before, and
two clients in one process do not see each other's settings: the reason these
are arguments and not the process environment.
"""

import asyncio

import pytest

import aidot_cameras
import aidot_cameras.client as client_mod
from aidot_cameras import settings
from aidot_cameras.camera import aac_track, hls_ts, rtsp_publish, sdes_open
from aidot_cameras.client import CameraClient
from aidot_cameras.settings import StreamSettings

KNOBS = (
    "AIDOT_DIRECT_PUBLISH",
    "AIDOT_PUBLISH_AAC",
    "AIDOT_HLS_DIRECT_TS",
    "AIDOT_SDES_VIDEO_PT",
)
URL = "rtsp://127.0.0.1:8554/aidot_x"


@pytest.fixture(autouse=True)
def _clean_env(monkeypatch):
    for name in KNOBS:
        monkeypatch.delenv(name, raising=False)


def test_exported_from_the_package_root():
    assert aidot_cameras.StreamSettings is StreamSettings
    assert "StreamSettings" in aidot_cameras.__all__


def test_every_field_is_a_registered_knob():
    assert set(StreamSettings().as_env()) == set()
    full = StreamSettings(True, True, True, 96).as_env()
    assert set(full) == set(KNOBS) and all(k in settings.KNOBS for k in full)


@pytest.mark.parametrize(
    "given, stored",
    [(96, "96"), ("97", "97"), ("none", "none"), ("OFF", "none"), ("0", "none")],
)
def test_video_pt_is_normalised(given, stored):
    assert StreamSettings(sdes_video_pt=given).sdes_video_pt == stored


@pytest.mark.parametrize(
    "kwargs, exc",
    [
        ({"sdes_video_pt": 95}, ValueError),
        ({"sdes_video_pt": "h264"}, ValueError),
        ({"sdes_video_pt": True}, ValueError),
        ({"direct_publish": "0"}, TypeError),
        ({"publish_aac": 1}, TypeError),
    ],
)
def test_invalid_values_are_refused(kwargs, exc):
    with pytest.raises(exc):
        StreamSettings(**kwargs)


def test_read_refuses_anything_but_stream_settings():
    with pytest.raises(TypeError):
        settings.read("AIDOT_DIRECT_PUBLISH", overrides={"AIDOT_DIRECT_PUBLISH": 0})


@pytest.mark.parametrize("name", KNOBS)
def test_nothing_passed_reads_as_before(name):
    for env in ({}, {name: "0"}, {name: "1"}, {name: "none"}):
        before = settings.read(name, env)
        assert settings.read(name, env, overrides=None) == before
        assert settings.read(name, env, overrides=StreamSettings()) == before


def test_explicit_beats_env_beats_default():
    on = StreamSettings(direct_publish=True)
    off = StreamSettings(direct_publish=False)
    name = "AIDOT_DIRECT_PUBLISH"
    assert settings.read(name, {}) is True  # the default
    assert settings.read(name, {name: "0"}) is False  # env beats default
    assert settings.read(name, {name: "0"}, overrides=on) is True  # explicit wins
    assert settings.read(name, {name: "1"}, overrides=off) is False
    assert settings.read(name, {name: "0"}, overrides=StreamSettings()) is False


def test_each_read_site_follows_the_rule(monkeypatch):
    off = StreamSettings(
        direct_publish=False, publish_aac=False, hls_direct_ts=False
    )
    on = StreamSettings(direct_publish=True, publish_aac=True, hls_direct_ts=True)
    # Default: everything on, the pin at 96.
    assert rtsp_publish.direct_publish_enabled() is True
    assert aac_track.publish_aac_enabled() is True
    assert hls_ts.enabled() is True
    assert sdes_open._resolve_sdes_video_pt() == 96
    # Explicit beats the default.
    assert rtsp_publish.direct_publish_enabled(off) is False
    assert aac_track.publish_aac_enabled(off) is False
    assert aac_track.make_aac_track("cam", off) is None
    assert hls_ts.enabled(off) is False
    assert not sdes_open._should_direct_publish(
        URL, None, None, stream_settings=off
    )
    pin_off = StreamSettings(sdes_video_pt="none")
    assert sdes_open._resolve_sdes_video_pt(pin_off) is None
    assert sdes_open._pinned_video_pt(None, pin_off) is None
    # The environment turns them off; explicit settings turn them back on.
    for name in KNOBS:
        monkeypatch.setenv(name, "0")
    assert rtsp_publish.direct_publish_enabled() is False
    assert hls_ts.enabled() is False
    assert sdes_open._resolve_sdes_video_pt() is None
    assert rtsp_publish.direct_publish_enabled(on) is True
    assert aac_track.publish_aac_enabled(on) is True
    assert hls_ts.enabled(on) is True
    assert sdes_open._should_direct_publish(URL, None, None, stream_settings=on)
    assert sdes_open._resolve_sdes_video_pt(StreamSettings(sdes_video_pt=96)) == 96
    # A partial setting leaves the rest to the environment.
    assert hls_ts.enabled(StreamSettings(hls_direct_ts=True)) is False


def test_stream_settings_of_ignores_anything_else():
    class _Obj:
        pass

    o = _Obj()
    assert settings.stream_settings_of(o) is None
    o.stream_settings = object()
    assert settings.stream_settings_of(o) is None
    s = StreamSettings(direct_publish=False)
    o.stream_settings = s
    assert settings.stream_settings_of(o) is s


def test_the_client_refuses_a_wrong_type():
    with pytest.raises(TypeError):
        CameraClient(None, country_code="US", stream_settings={"x": 1})


def _clients(monkeypatch, raw_device):
    async def _no_prefetch(_dc):
        return None

    monkeypatch.setattr(client_mod, "_prefetch_ice_config", _no_prefetch)

    async def run():
        a = CameraClient(
            None,
            country_code="US",
            stream_settings=StreamSettings(
                direct_publish=False, sdes_video_pt="none"
            ),
        )
        b = CameraClient(
            None,
            country_code="US",
            stream_settings=StreamSettings(direct_publish=True, sdes_video_pt=96),
        )
        plain = CameraClient(None, country_code="US")
        for c in (a, b, plain):
            c.login_info = {"id": "u1"}
        dev = raw_device("A000088")
        return [c.get_device_client(dict(dev)) for c in (a, b, plain)]

    return asyncio.run(run())


def test_two_clients_do_not_affect_each_other(monkeypatch, raw_device):
    dc_a, dc_b, dc_plain = _clients(monkeypatch, raw_device)
    assert dc_a.stream_settings.direct_publish is False
    assert dc_b.stream_settings.direct_publish is True
    assert dc_plain.stream_settings is None
    of = settings.stream_settings_of
    assert rtsp_publish.direct_publish_enabled(of(dc_a)) is False
    assert rtsp_publish.direct_publish_enabled(of(dc_b)) is True
    assert rtsp_publish.direct_publish_enabled(of(dc_plain)) is True
    assert sdes_open._resolve_sdes_video_pt(of(dc_a)) is None
    assert sdes_open._resolve_sdes_video_pt(of(dc_b)) == 96
    # The camera-side reads use the camera's own settings.
    assert dc_a._dtls_publish_kwargs({})["stream_settings"] is dc_a.stream_settings
    assert dc_a._hls_ts_expected() is False  # direct publish off for this one
    assert dc_b._hls_ts_expected() is True
    # And the process environment is untouched by either.
    import os

    assert not any(name in os.environ for name in KNOBS)
