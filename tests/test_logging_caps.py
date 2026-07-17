"""Importing the library caps the chatty per-packet/per-message loggers.

aiortc's RTP receiver/sender log every media packet at DEBUG, so they are capped
at INFO: enabling DEBUG on the parent ``aidot`` logger (the normal way to
diagnose the integration) would otherwise unleash thousands of lines per
second, which on a microSD host can starve the recorder.

The external ``aioice`` package's flood is different: it logs ICE
connectivity-check state transitions at INFO, not DEBUG, so an INFO cap does
nothing to silence it. aioice emits nothing at WARNING or above, so
``aioice.ice`` and ``aioice.turn`` are capped at WARNING instead.

Both caps only apply when the logger's level is still NOTSET, so a level set
explicitly by the user (e.g. to re-enable aioice INFO for debugging) is
respected.
"""
import logging

import aidot  # noqa: F401 - import triggers the logger caps
from aidot import _cap_external_loggers

INFO_CAPPED = (
    "aidot._vendor.aiortc.rtcrtpreceiver",
    "aidot._vendor.aiortc.rtcrtpsender",
)

WARNING_CAPPED = (
    "aioice.ice",
    "aioice.turn",
)


def test_aiortc_packet_loggers_capped_to_info():
    for name in INFO_CAPPED:
        lg = logging.getLogger(name)
        assert lg.level == logging.INFO, f"{name} not capped (level={lg.level})"


def test_aioice_loggers_capped_to_warning():
    for name in WARNING_CAPPED:
        lg = logging.getLogger(name)
        assert lg.level == logging.WARNING, f"{name} not capped (level={lg.level})"


def test_aidot_debug_does_not_unleash_the_aiortc_packet_firehose():
    # Even with the parent 'aidot' logger at DEBUG, the per-packet DEBUG is off.
    logging.getLogger("aidot").setLevel(logging.DEBUG)
    for name in INFO_CAPPED:
        assert not logging.getLogger(name).isEnabledFor(logging.DEBUG), f"{name} still emits DEBUG"


def test_aidot_debug_does_not_unleash_the_aioice_info_flood():
    # Even with the parent 'aidot' logger at DEBUG, aioice's INFO flood is off.
    logging.getLogger("aidot").setLevel(logging.DEBUG)
    for name in WARNING_CAPPED:
        assert not logging.getLogger(name).isEnabledFor(logging.INFO), f"{name} still emits INFO"


def test_cap_respects_an_explicit_user_level():
    # A user who explicitly sets a level (e.g. to re-enable aioice INFO for
    # debugging) must not have it silently overridden by the cap.
    for name in INFO_CAPPED + WARNING_CAPPED:
        lg = logging.getLogger(name)
        original_level = lg.level
        try:
            lg.setLevel(logging.INFO)
            _cap_external_loggers()
            assert lg.level == logging.INFO, f"{name} explicit level was overridden"
        finally:
            lg.setLevel(original_level)


def test_cap_is_idempotent_and_still_applies_to_fresh_notset_loggers():
    # A logger that has never had its level set (NOTSET) still gets capped
    # when the (idempotent) cap function runs again.
    for name in INFO_CAPPED:
        lg = logging.getLogger(name)
        original_level = lg.level
        try:
            lg.setLevel(logging.NOTSET)
            _cap_external_loggers()
            assert lg.level == logging.INFO
        finally:
            lg.setLevel(original_level)
    for name in WARNING_CAPPED:
        lg = logging.getLogger(name)
        original_level = lg.level
        try:
            lg.setLevel(logging.NOTSET)
            _cap_external_loggers()
            assert lg.level == logging.WARNING
        finally:
            lg.setLevel(original_level)
