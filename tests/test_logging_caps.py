"""Importing the library caps the vendored aiortc per-packet loggers.

aiortc's RTP receiver/sender log every media packet at DEBUG. Enabling DEBUG on
the parent ``aidot`` logger (the normal way to diagnose the integration) would
otherwise unleash thousands of lines per second. The package init caps just those
two loggers at INFO so DEBUG on ``aidot`` stays useful without the firehose.
"""
import logging

import aidot  # noqa: F401 - import triggers the logger caps


def test_vendored_aiortc_packet_loggers_capped_to_info():
    for name in (
        "aidot._vendor.aiortc.rtcrtpreceiver",
        "aidot._vendor.aiortc.rtcrtpsender",
    ):
        lg = logging.getLogger(name)
        assert lg.level == logging.INFO, f"{name} not capped (level={lg.level})"
        # Even with the parent 'aidot' logger at DEBUG, the per-packet DEBUG is off.
        logging.getLogger("aidot").setLevel(logging.DEBUG)
        assert not lg.isEnabledFor(logging.DEBUG), f"{name} still emits DEBUG"
