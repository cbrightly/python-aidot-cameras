"""Crypto helpers for the camera layer.

The shared AES/RSA primitives come from upstream (``aidot.utils.crypto``) and
are re-exported so camera modules have one import site.  Only the string-keyed
AES-256/ECB pair is defined here: it is a Leedarson camera-SDK convention
(zero-pad a string key to 32 bytes) that upstream has no use for.
"""

from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import padding
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes

# Shared primitives live upstream; re-exported for camera modules.
from aidot.utils.crypto import (  # noqa: F401 - deliberate re-export
    aes_decrypt,
    aes_decrypt_to_json,
    aes_encrypt,
    rsa_encrypt,
)


def _str_key_32(key_str: str) -> bytes:
    """Zero-pad a string key to 32 bytes (AESUtils.get32Key from the Leedarson SDK)."""
    raw = key_str.encode("utf-8")
    return raw[:32].ljust(32, b"\x00")


def aes_ecb_encrypt_str_key(plaintext: bytes, key_str: str) -> bytes:
    """AES-256/ECB/PKCS7 encrypt; key is a string zero-padded to 32 bytes."""
    padder = padding.PKCS7(128).padder()
    padded = padder.update(plaintext) + padder.finalize()
    cipher = Cipher(algorithms.AES(_str_key_32(key_str)), modes.ECB(), backend=default_backend())
    enc = cipher.encryptor()
    return enc.update(padded) + enc.finalize()


def aes_ecb_decrypt_str_key(ciphertext: bytes, key_str: str) -> bytes:
    """AES-256/ECB/PKCS7 decrypt; key is a string zero-padded to 32 bytes."""
    cipher = Cipher(algorithms.AES(_str_key_32(key_str)), modes.ECB(), backend=default_backend())
    dec = cipher.decryptor()
    padded = dec.update(ciphertext) + dec.finalize()
    unpadder = padding.PKCS7(128).unpadder()
    return unpadder.update(padded) + unpadder.finalize()
