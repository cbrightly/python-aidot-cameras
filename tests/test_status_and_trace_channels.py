"""The status channel splits in two, and only one of them is INFO.

``_status`` was designed as a status *channel* for the CLI: when a
``status_callback`` is routing the messages somewhere, logging is DEBUG; when
nothing is, the log IS the channel, so it logs at INFO.  Under Home Assistant
there is never a callback, so every message that ever went through ``_status``
landed at INFO -- per-packet bridge diagnostics alongside once-per-session
lifecycle lines.  HA's own limiter fired against it twice in ten minutes on an
idle box.

``_trace`` is the same channel for the steady-state sites: it still feeds the
callback so the CLI keeps its full stream, but it logs at DEBUG whether or not
a callback exists.

These assert on captured log RECORDS, never on source substrings -- a
source-substring test certifies the letter while the substance inverts.
"""

import logging

import pytest

from aidot_cameras.camera.protocol import _make_status_pair


LOGGER_NAME = "test_status_and_trace_channels.fake"


def _records(caplog, level):
    return [r for r in caplog.records
            if r.name == LOGGER_NAME and r.levelno == level]


@pytest.fixture
def logger(caplog):
    log = logging.getLogger(LOGGER_NAME)
    log.setLevel(logging.DEBUG)
    log.propagate = True
    caplog.set_level(logging.DEBUG, logger=LOGGER_NAME)
    return log


def test_status_without_a_callback_is_info(logger, caplog):
    """No callback means the log IS the status channel, so INFO."""
    status, _trace = _make_status_pair(None, logger)

    status("lifecycle line")

    info = _records(caplog, logging.INFO)
    assert len(info) == 1
    assert "lifecycle line" in info[0].getMessage()


def test_trace_without_a_callback_is_debug_and_never_info(logger, caplog):
    """The whole point: steady-state sites stay out of a user's INFO log."""
    _status, trace = _make_status_pair(None, logger)

    trace("per-packet line")

    assert _records(caplog, logging.INFO) == []
    debug = _records(caplog, logging.DEBUG)
    assert len(debug) == 1
    assert "per-packet line" in debug[0].getMessage()


def test_both_channels_still_feed_the_callback(logger, caplog):
    """The CLI's stream must not lose the steady-state detail."""
    seen = []
    status, trace = _make_status_pair(seen.append, logger)

    status("lifecycle line")
    trace("per-packet line")

    assert seen == ["lifecycle line", "per-packet line"]
    # With a callback routing the messages, neither channel is INFO.
    assert _records(caplog, logging.INFO) == []
    assert len(_records(caplog, logging.DEBUG)) == 2
