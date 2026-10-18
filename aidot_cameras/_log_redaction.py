"""Keep credentials that upstream python-aidot logs out of the log.

Upstream writes secrets into two of its log lines (checked on 0.3.56 and
0.3.58):

* ``aidot.device_client`` - ``DeviceClient.login`` logs the device's login
  reply at WARNING, and the device echoes its LAN password back in it.  WARNING
  is Home Assistant's default level, so any install doing LAN login writes the
  password into its log, and that password lets anyone on the LAN control the
  device.
* ``aidot.client`` - ``AidotClient.async_refresh_token`` logs the refresh
  response at DEBUG, which carries the account's access and refresh token.

Both messages are dict reprs, sometimes via ``%s`` arguments, so the filter
works on the fully formatted message and masks the VALUE of a known secret key
wherever it appears, leaving every other field - which is what makes the line
useful for debugging - untouched.

The filter is attached to upstream's loggers by name, because a filter on a
parent logger does not see records created on its children.
``tests/test_upstream_secrets_redacted.py`` pins the names and drives upstream's
real methods, so a rename or a new secret-bearing line fails a test rather than
leaking quietly.
"""

import logging
import re

#: Upstream loggers known to emit secrets.  Module loggers, so these are the
#: exact names records are created on.
UPSTREAM_LOGGERS = ("aidot.device_client", "aidot.client")

#: Loggers that can emit a whole SDP, which carries three kinds of credential:
#: ``a=crypto ... inline:`` (the SRTP master key and salt), ``a=ice-pwd`` and
#: ``a=psk``.  Masking at each call site was tried first and missed three of
#: them, two inside the vendored aiortc, which logs the full offer and answer
#: at DEBUG from ``setLocalDescription`` / ``setRemoteDescription``.  A filter
#: on the loggers themselves catches every site, including ones added later.
#: Named individually because a filter on a parent logger does not see records
#: created on its children.
SDP_LOGGERS = (
    "aidot_cameras._vendor.aiortc.rtcpeerconnection",
    "aidot_cameras.camera.webrtc_open",
    "aidot_cameras.camera.sdes_open",
    "aidot_cameras.camera.client",
)

#: Keys whose values are credentials.  Matched as whole quoted keys, so
#: ``tokenType`` or ``passwordless`` are left alone.
SECRET_KEYS = ("password", "accessToken", "refreshToken", "token", "aesKey")

REDACTED = "'<redacted>'"

_QUOTED = r"'(?:[^'\\]|\\.)*'|\"(?:[^\"\\]|\\.)*\""
_SECRET_FIELD = re.compile(
    r"(?P<key>(?P<q>['\"])(?:"
    + "|".join(re.escape(k) for k in SECRET_KEYS)
    + r")(?P=q)\s*:\s*)"
    + rf"(?:{_QUOTED}|\[[^\]]*\])"
)


#: ``a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:<key>`` - the value after
#: ``inline:`` is the base64 SRTP master key + salt.
_SDP_INLINE = re.compile(r"(inline:)[A-Za-z0-9+/=]+")
#: ``a=ice-pwd:<pw>`` and ``a=psk:<psk>`` run to the end of their line.
_SDP_ATTR = re.compile(r"^(\s*a=(?:ice-pwd|psk):)[^\r\n]+", re.MULTILINE)


def redact_secrets(text: str) -> str:
    """``text`` with the value of every known secret key replaced."""
    return _SECRET_FIELD.sub(lambda m: m.group("key") + REDACTED, text)


def redact_sdp_secrets(text: str) -> str:
    """``text`` with every credential-bearing SDP attribute value masked.

    Leaves the attribute name and the rest of the document intact, so a
    logged SDP is still readable for debugging - only the key material goes.
    """
    text = _SDP_INLINE.sub(r"\1<masked>", text)
    return _SDP_ATTR.sub(r"\1<masked>", text)


class RedactSecretsFilter(logging.Filter):
    """Masks secret values in a record's message; never drops the record."""

    def filter(self, record: logging.LogRecord) -> bool:
        try:
            message = record.getMessage()
        except Exception:  # a malformed record is not ours to fix
            return True
        redacted = redact_secrets(message)
        if redacted != message:
            record.msg = redacted
            record.args = ()
        return True


class RedactSdpFilter(logging.Filter):
    """Masks SDP key material in a record's message; never drops the record."""

    def filter(self, record: logging.LogRecord) -> bool:
        try:
            message = record.getMessage()
        except Exception:  # a malformed record is not ours to fix
            return True
        if "a=" not in message and "inline:" not in message:
            return True  # the overwhelmingly common case, and the cheap one
        redacted = redact_sdp_secrets(message)
        if redacted != message:
            record.msg = redacted
            record.args = ()
        return True


def install_upstream_redaction() -> None:
    """Idempotently attach both filters to the loggers that need them."""
    for name in UPSTREAM_LOGGERS:
        logger = logging.getLogger(name)
        if not any(isinstance(f, RedactSecretsFilter) for f in logger.filters):
            logger.addFilter(RedactSecretsFilter())
    for name in SDP_LOGGERS:
        logger = logging.getLogger(name)
        if not any(isinstance(f, RedactSdpFilter) for f in logger.filters):
            logger.addFilter(RedactSdpFilter())
