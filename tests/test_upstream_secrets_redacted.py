"""Upstream python-aidot must not write credentials into the log.

Two upstream log calls carry secrets (checked on 0.3.56 and 0.3.58):

* ``aidot.device_client`` - ``DeviceClient.login`` logs the device's login
  reply at WARNING, and the device echoes its LAN password back in it.  WARNING
  is Home Assistant's default level, so this reaches every install that does a
  LAN login, and the password lets anyone on the LAN control the device.
* ``aidot.client`` - ``AidotClient.async_refresh_token`` logs the refresh
  response at DEBUG, which carries the account's access AND refresh token - an
  account takeover for whoever reads a debug log someone shared to get help.

This package installs a redacting filter on those two loggers.  These tests
drive upstream's REAL methods rather than logging a hand-made imitation of the
line, so a change to what upstream logs, or where, is what breaks them.
"""

import asyncio
import json
import logging
import struct

import pytest

import aidot_cameras  # noqa: F401  (installs the filter on import)
from aidot_cameras import _upstream

LAN_PASSWORD = "hunter2-LAN-SECRET"
ACCESS_TOKEN = "NEW-ACCESS-SECRET"
REFRESH_TOKEN = "NEW-REFRESH-SECRET"


class _Capture(logging.Handler):
    """Records as a handler sees them - i.e. after the logger's own filters."""

    def __init__(self):
        super().__init__(level=logging.DEBUG)
        self.messages = []

    def emit(self, record):
        self.messages.append(record.getMessage())


@pytest.fixture
def capture():
    """Attach a capturing handler to both upstream loggers, at DEBUG."""
    handler = _Capture()
    touched = []
    for name in ("aidot.device_client", "aidot.client"):
        lg = logging.getLogger(name)
        touched.append((lg, lg.level))
        lg.setLevel(logging.DEBUG)
        lg.addHandler(handler)
    yield handler
    for lg, level in touched:
        lg.removeHandler(handler)
        lg.setLevel(level)


# --------------------------------------------------------------------------- #
# The real upstream emitters
# --------------------------------------------------------------------------- #


class _FakeWriter:
    def write(self, data):
        pass

    async def drain(self):
        pass

    def close(self):
        pass

    async def wait_closed(self):
        pass

    def is_closing(self):
        return True


class _FakeReader:
    """Serves one framed, AES-encrypted reply, the way the device does."""

    def __init__(self, frame):
        self._frame = frame

    async def readexactly(self, n):
        chunk, self._frame = self._frame[:n], self._frame[n:]
        return chunk


def test_device_login_reply_does_not_log_the_lan_password(capture):
    from upstream_shapes import make_upstream_device_client

    raw_device = {
        "id": "d1",
        "name": "d1",
        "modelId": "lk.WIFI-RGBWLight-D0006",
        "aesKey": ["k" * 16],
        "password": LAN_PASSWORD,
    }
    dc = make_upstream_device_client(raw_device, {"id": "u1", "region": "us"})

    # The device's login reply: it echoes the password in its payload.  A
    # non-200 ack takes upstream's early error path right after the WARNING,
    # so nothing else (receive loop, pings) is started.
    reply = {
        "service": "device",
        "method": "loginResp",
        "seq": "1",
        "srcAddr": "d1",
        "deviceId": "d1",
        "ack": {"code": 500, "desc": "test"},
        "payload": {
            "userId": "u1",
            "password": LAN_PASSWORD,
            "ascNumber": 7,
            "timestamp": 1,
        },
    }
    body = _upstream.aes_encrypt(json.dumps(reply).encode(), dc.aes_key)
    dc.reader = _FakeReader(struct.pack(">HHI", 0x1EED, 1, len(body)) + body)
    dc.writer = _FakeWriter()

    asyncio.run(dc.login())

    login_lines = [m for m in capture.messages if "login result" in m]
    assert login_lines, capture.messages  # upstream still logs it - premise
    for line in login_lines:
        assert LAN_PASSWORD not in line, line
        # Everything that is not a secret stays, so the line is still useful.
        assert "userId" in line and "ascNumber" in line, line


def test_token_refresh_does_not_log_the_tokens(capture):
    from aidot.client import AidotClient

    class _Response:
        async def json(self):
            return {
                "accessToken": ACCESS_TOKEN,
                "refreshToken": REFRESH_TOKEN,
                "expiresIn": 3600,
            }

        def raise_for_status(self):
            pass

    class _Session:
        async def post(self, url, headers=None, json=None):
            return _Response()

    client = AidotClient(None, country_code="US")
    client.session = _Session()
    client.login_info = {"accessToken": "old", "refreshToken": "old"}

    asyncio.run(client.async_refresh_token())

    refresh_lines = [m for m in capture.messages if "refresh token" in m]
    assert refresh_lines, capture.messages  # upstream still logs it - premise
    for line in refresh_lines:
        assert ACCESS_TOKEN not in line, line
        assert REFRESH_TOKEN not in line, line
        assert "expiresIn" in line, line
    # Redaction is of the LOG only - the client must still hold the new tokens.
    assert client.login_info["accessToken"] == ACCESS_TOKEN
    assert client.login_info["refreshToken"] == REFRESH_TOKEN


# --------------------------------------------------------------------------- #
# The redaction itself
# --------------------------------------------------------------------------- #


@pytest.mark.parametrize(
    "text, secret",
    [
        ("{'password': 'p@ss'}", "p@ss"),
        ('{"password": "p@ss"}', "p@ss"),
        ("{'password': \"it's\"}", "it's"),
        ("{'accessToken': 'aaa', 'x': 1}", "aaa"),
        ("{'refreshToken': 'rrr'}", "rrr"),
        ("{'token': 'ttt'}", "ttt"),
        ("{'aesKey': ['kkkkkkkkkkkkkkkk']}", "kkkkkkkkkkkkkkkk"),
    ],
)
def test_redaction_removes_the_value_in_either_quote_style(text, secret):
    from aidot_cameras._log_redaction import redact_secrets

    out = redact_secrets(text)
    assert secret not in out, out


def test_redaction_leaves_ordinary_text_alone():
    from aidot_cameras._log_redaction import redact_secrets

    text = "{'userId': 'u1', 'ascNumber': 7, 'tokenType': 'x', 'passwordless': True}"
    assert redact_secrets(text) == text


def test_the_filter_is_on_both_upstream_loggers():
    from aidot_cameras._log_redaction import RedactSecretsFilter

    for name in ("aidot.device_client", "aidot.client"):
        filters = logging.getLogger(name).filters
        assert any(isinstance(f, RedactSecretsFilter) for f in filters), name


def test_upstream_still_logs_from_the_loggers_we_filter():
    """The filter is attached by logger NAME; a rename would silently bypass it."""
    import aidot.client
    import aidot.device_client

    assert aidot.device_client._LOGGER.name == "aidot.device_client"
    assert aidot.client._LOGGER.name == "aidot.client"
