"""Importing the library caps the chatty per-packet loggers.

aiortc's RTP receiver/sender log every media packet at DEBUG, and the external
aioice package logs every STUN/TURN packet. Enabling DEBUG on the parent ``aidot``
logger (the normal way to diagnose the integration) would otherwise unleash
thousands of lines per second - which on a microSD host can starve the recorder.
The package init caps those loggers at INFO so DEBUG on ``aidot`` stays useful.
"""
import logging

import aidot  # noqa: F401 - import triggers the logger caps

CAPPED = (
    "aidot._vendor.aiortc.rtcrtpreceiver",
    "aidot._vendor.aiortc.rtcrtpsender",
    "aioice.ice",
    "aioice.turn",
)


def test_packet_loggers_capped_to_info():
    for name in CAPPED:
        lg = logging.getLogger(name)
        assert lg.level == logging.INFO, f"{name} not capped (level={lg.level})"


def test_aidot_debug_does_not_unleash_the_packet_firehose():
    # Even with the parent 'aidot' logger at DEBUG, the per-packet DEBUG is off.
    logging.getLogger("aidot").setLevel(logging.DEBUG)
    for name in CAPPED:
        assert not logging.getLogger(name).isEnabledFor(logging.DEBUG), f"{name} still emits DEBUG"
