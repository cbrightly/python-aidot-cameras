"""CLI-EGRESS-001: warn when decrypted media is served on a non-loopback bind."""
import logging

from aidot.camera.protocol import (
    _serve_host,
    _is_loopback_serve_host,
    _warn_lan_serve,
)


def test_serve_host_extraction():
    assert _serve_host("http://0.0.0.0:8888/cam.ts") == "0.0.0.0"
    assert _serve_host("http://127.0.0.1:1/x") == "127.0.0.1"
    assert _serve_host("http://192.168.1.5:8554/x.ts") == "192.168.1.5"
    assert _serve_host("rtsp://camhost:554/stream") == "camhost"
    assert _serve_host("http://[::1]:8888/x") == "::1"
    assert _serve_host(None) is None
    assert _serve_host("") is None


def test_is_loopback_serve_host():
    assert _is_loopback_serve_host("127.0.0.1")
    assert _is_loopback_serve_host("127.5.5.5")
    assert _is_loopback_serve_host("::1")
    assert _is_loopback_serve_host("localhost")
    assert _is_loopback_serve_host(None)            # unset -> not exposed
    assert not _is_loopback_serve_host("0.0.0.0")   # binds all interfaces
    assert not _is_loopback_serve_host("192.168.1.5")


def test_warn_fires_on_non_loopback(caplog, monkeypatch):
    monkeypatch.delenv("AIDOT_ALLOW_LAN_SERVE", raising=False)
    with caplog.at_level(logging.WARNING, logger="aidot.camera.protocol"):
        _warn_lan_serve("0.0.0.0", context="t")
    assert any("DECRYPTED" in r.getMessage() for r in caplog.records)


def test_no_warn_on_loopback(caplog, monkeypatch):
    monkeypatch.delenv("AIDOT_ALLOW_LAN_SERVE", raising=False)
    with caplog.at_level(logging.WARNING, logger="aidot.camera.protocol"):
        _warn_lan_serve("127.0.0.1", context="t")
        _warn_lan_serve(None, context="t")
    assert not caplog.records


def test_optout_env_silences(caplog, monkeypatch):
    monkeypatch.setenv("AIDOT_ALLOW_LAN_SERVE", "1")
    with caplog.at_level(logging.WARNING, logger="aidot.camera.protocol"):
        _warn_lan_serve("192.168.1.5", context="t")
    assert not caplog.records
