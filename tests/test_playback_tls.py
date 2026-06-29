"""CLI-REC-001: playback TLS verification is opt-in via AIDOT_PLAYBACK_TLS_VERIFY."""
import logging
import ssl

from aidot.camera.playback import _playback_ssl_context


def test_default_is_permissive_and_warns(caplog, monkeypatch):
    monkeypatch.delenv("AIDOT_PLAYBACK_TLS_VERIFY", raising=False)
    with caplog.at_level(logging.WARNING, logger="aidot.camera.playback"):
        ctx = _playback_ssl_context()
    assert ctx.verify_mode == ssl.CERT_NONE
    assert ctx.check_hostname is False
    assert any("DISABLED" in r.getMessage() for r in caplog.records)


def test_optin_enables_verification(caplog, monkeypatch):
    monkeypatch.setenv("AIDOT_PLAYBACK_TLS_VERIFY", "1")
    with caplog.at_level(logging.WARNING, logger="aidot.camera.playback"):
        ctx = _playback_ssl_context()
    assert ctx.verify_mode == ssl.CERT_REQUIRED
    assert ctx.check_hostname is True
    assert not caplog.records
