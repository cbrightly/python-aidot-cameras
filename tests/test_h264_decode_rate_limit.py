"""Tests for rate-limiting the vendored H.264 decode-failure WARNING.

``aidot._vendor.aiortc.codecs.h264`` logs a WARNING per corrupt/undecodable
frame (see h264.py:118). On a degrading link that has been observed to log
172 identical lines in 11 minutes. The library attaches a rate-limiting
``logging.Filter`` to that logger at import (aidot/__init__.py): the first
WARNING passes immediately, subsequent WARNINGs within a window are
suppressed and counted, and the first WARNING after the window elapses
passes through carrying the suppressed count, so the corruption canary
stays visible without flooding.
"""
import logging

import aidot  # noqa: F401 - import triggers filter installation
from aidot import (
    _H264_DECODE_LOGGER_NAME,
    _RateLimitingWarningFilter,
    _install_h264_decode_rate_limit_filter,
)

DECODE_MESSAGE = "H264Decoder() failed to decode, skipping package: some ffmpeg error"


class _FakeClock:
    """Deterministic stand-in for time.monotonic; advanced manually by tests."""

    def __init__(self, start: float = 0.0) -> None:
        self.now = start

    def __call__(self) -> float:
        return self.now

    def advance(self, seconds: float) -> None:
        self.now += seconds


def _make_record(level=logging.WARNING, msg=DECODE_MESSAGE, name=_H264_DECODE_LOGGER_NAME):
    return logging.LogRecord(
        name=name,
        level=level,
        pathname=__file__,
        lineno=1,
        msg=msg,
        args=(),
        exc_info=None,
    )


def test_first_record_passes_immediately():
    clock = _FakeClock()
    rate_filter = _RateLimitingWarningFilter(window_seconds=30.0, time_func=clock)
    assert rate_filter.filter(_make_record()) is True


def test_records_within_window_are_suppressed():
    clock = _FakeClock()
    rate_filter = _RateLimitingWarningFilter(window_seconds=30.0, time_func=clock)
    assert rate_filter.filter(_make_record()) is True  # first, opens the window
    for _ in range(5):
        clock.advance(1.0)
        assert rate_filter.filter(_make_record()) is False


def test_summary_passes_at_window_boundary_with_suppressed_count():
    clock = _FakeClock()
    rate_filter = _RateLimitingWarningFilter(window_seconds=30.0, time_func=clock)
    assert rate_filter.filter(_make_record()) is True  # first
    for _ in range(3):
        clock.advance(1.0)
        assert rate_filter.filter(_make_record()) is False
    clock.advance(30.0)  # window elapsed
    boundary = _make_record()
    assert rate_filter.filter(boundary) is True
    assert "suppressed 3" in boundary.getMessage()


def test_new_window_opens_from_the_boundary_record():
    clock = _FakeClock()
    rate_filter = _RateLimitingWarningFilter(window_seconds=30.0, time_func=clock)
    rate_filter.filter(_make_record())
    clock.advance(30.0)
    rate_filter.filter(_make_record())  # boundary, starts a new window
    clock.advance(1.0)
    assert rate_filter.filter(_make_record()) is False  # inside the new window


def test_non_warning_records_pass_through_untouched():
    clock = _FakeClock()
    rate_filter = _RateLimitingWarningFilter(window_seconds=30.0, time_func=clock)
    assert rate_filter.filter(_make_record()) is True
    info_record = _make_record(level=logging.INFO, msg="unrelated info message")
    assert rate_filter.filter(info_record) is True
    assert info_record.getMessage() == "unrelated info message"


def test_unrelated_logger_is_not_touched_by_the_installed_filter():
    other = logging.getLogger("aidot._vendor.aiortc.codecs.not_h264")
    assert not any(isinstance(f, _RateLimitingWarningFilter) for f in other.filters)


def test_filter_is_installed_on_the_h264_logger_at_import():
    lg = logging.getLogger(_H264_DECODE_LOGGER_NAME)
    assert any(isinstance(f, _RateLimitingWarningFilter) for f in lg.filters)


def test_install_is_idempotent():
    lg = logging.getLogger(_H264_DECODE_LOGGER_NAME)
    before = sum(1 for f in lg.filters if isinstance(f, _RateLimitingWarningFilter))
    _install_h264_decode_rate_limit_filter()
    _install_h264_decode_rate_limit_filter()
    after = sum(1 for f in lg.filters if isinstance(f, _RateLimitingWarningFilter))
    assert before == after == 1


def test_end_to_end_via_real_logger_suppresses_and_summarizes(caplog):
    lg = logging.getLogger(_H264_DECODE_LOGGER_NAME)
    clock = _FakeClock()
    # Swap out the module-installed filter (wired to the real time.monotonic,
    # which would also rate-limit these records) for a dedicated instance
    # driven by the fake clock, then restore it afterwards.
    installed_filters = [f for f in lg.filters if isinstance(f, _RateLimitingWarningFilter)]
    for f in installed_filters:
        lg.removeFilter(f)
    test_filter = _RateLimitingWarningFilter(window_seconds=10.0, time_func=clock)
    lg.addFilter(test_filter)
    try:
        with caplog.at_level(logging.WARNING, logger=_H264_DECODE_LOGGER_NAME):
            lg.warning(DECODE_MESSAGE)
            for _ in range(4):
                clock.advance(1.0)
                lg.warning(DECODE_MESSAGE)
            clock.advance(10.0)
            lg.warning(DECODE_MESSAGE)
        messages = [r.getMessage() for r in caplog.records if r.name == _H264_DECODE_LOGGER_NAME]
        assert len(messages) == 2
        assert messages[0] == DECODE_MESSAGE
        assert "suppressed 4" in messages[1]
    finally:
        lg.removeFilter(test_filter)
        for f in installed_filters:
            lg.addFilter(f)
