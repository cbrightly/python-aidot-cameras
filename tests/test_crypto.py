"""Unit tests for the string-keyed AES-256/ECB pair in aidot_cameras.crypto.

This is the Leedarson-SDK convention (zero-pad a string key to 32 bytes) that
upstream does not provide, so it is exercised here: the encrypt/decrypt
round-trip, PKCS7 padding across block boundaries, and the exact key
normalization (short keys zero-padded, long keys truncated to 32).
"""

import pytest

from aidot_cameras import crypto


def test_key_is_zero_padded_to_32_bytes():
    assert crypto._str_key_32("abc") == b"abc" + b"\x00" * 29
    assert len(crypto._str_key_32("abc")) == 32


def test_a_key_longer_than_32_bytes_is_truncated():
    long = "x" * 40
    assert crypto._str_key_32(long) == b"x" * 32


def test_an_exactly_32_byte_key_is_unchanged():
    k = "k" * 32
    assert crypto._str_key_32(k) == k.encode()


@pytest.mark.parametrize(
    "plaintext",
    [
        b"",  # empty: one full pad block
        b"short",
        b"exactly-16-bytes",  # one block, PKCS7 adds a second
        b"a much longer message that spans several AES blocks for good measure",
    ],
)
def test_encrypt_then_decrypt_round_trips(plaintext):
    key = "camera-session-key"
    ct = crypto.aes_ecb_encrypt_str_key(plaintext, key)
    assert ct != plaintext
    assert len(ct) % 16 == 0  # block-aligned ciphertext
    assert crypto.aes_ecb_decrypt_str_key(ct, key) == plaintext


def test_encryption_is_deterministic_for_ecb():
    # ECB has no IV, so the same key and plaintext always give the same bytes
    # (this is exactly why the SDK uses it for its fixed-key fields).
    a = crypto.aes_ecb_encrypt_str_key(b"same", "key")
    b = crypto.aes_ecb_encrypt_str_key(b"same", "key")
    assert a == b


def test_short_keys_normalize_to_the_same_padded_key():
    # "abc" and its explicit zero-padded form must encrypt identically.
    ct1 = crypto.aes_ecb_encrypt_str_key(b"payload", "abc")
    ct2 = crypto.aes_ecb_encrypt_str_key(b"payload", "abc" + "\x00" * 29)
    assert ct1 == ct2


def test_the_wrong_key_does_not_recover_the_plaintext():
    ct = crypto.aes_ecb_encrypt_str_key(b"secret-payload-16", "right-key")
    # A wrong key either fails PKCS7 unpadding or yields different bytes; never
    # the original plaintext.
    try:
        out = crypto.aes_ecb_decrypt_str_key(ct, "wrong-key")
    except ValueError:
        return
    assert out != b"secret-payload-16"
