"""DTLS fingerprint-pinning verification (AIDOT_DTLS_PINNED_FP).

These guard the fail-closed contract: with a pin configured, a missing cert or a
non-matching fingerprint must fail the handshake, never silently accept. The
absence of exactly this test is how the earlier fail-open bugs survived.
"""

import pytest

from aidot.camera.webrtc_open import (
    _dtls_pin_matches,
    _normalize_fingerprint,
    _verified_dtls_fingerprint,
)

# aiortc formats sha-256 fingerprints as uppercase colon-separated hex.
REAL = ":".join(["AB", "CD", "EF", "01"] * 8)  # 32 bytes


def _fp_fn(cert, alg):
    # In these tests the "cert" object IS its fingerprint string.
    assert alg == "sha-256"
    return cert


def test_normalize_strips_colons_case_and_space():
    assert _normalize_fingerprint("AB:CD:ef 12") == "abcdef12"


def test_pin_matches_is_format_insensitive():
    assert _dtls_pin_matches(REAL, REAL.lower().replace(":", ""))
    assert _dtls_pin_matches(REAL, f"  {REAL}  ")
    assert not _dtls_pin_matches(REAL, "00:11")


def test_matching_pin_returns_fingerprint():
    assert _verified_dtls_fingerprint(REAL, REAL, _fp_fn) == REAL


def test_pin_format_differences_still_match():
    # Operator supplies the pin without colons / lowercased.
    assert _verified_dtls_fingerprint(REAL, REAL.lower().replace(":", ""), _fp_fn) == REAL


def test_mismatching_pin_raises():
    with pytest.raises(ValueError, match="mismatch"):
        _verified_dtls_fingerprint(REAL, "00:11:22", _fp_fn)


def test_missing_cert_with_pin_fails_closed():
    # No peer cert while pinning MUST fail, not accept-any.
    with pytest.raises(ValueError, match="no certificate"):
        _verified_dtls_fingerprint(None, REAL, _fp_fn)


def test_missing_cert_without_pin_accepts():
    assert _verified_dtls_fingerprint(None, "", _fp_fn) is None


def test_present_cert_without_pin_returns_fingerprint():
    # Unpinned but a cert is present: return the real fp so the caller can
    # rewrite remote_params.fingerprints (the empty-fingerprint workaround).
    assert _verified_dtls_fingerprint(REAL, "", _fp_fn) == REAL
