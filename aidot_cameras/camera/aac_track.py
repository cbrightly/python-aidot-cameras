"""An AAC-LC track for the direct RTSP publish.

Home Assistant's stream component (its HLS player and ``camera.record``) keeps
AAC and MP3 audio and drops G.711, and the cameras send G.711 A-law. The pull
serves already encode AAC; the direct publish sent A-law only, so HLS played
it silent. Asking go2rtc to transcode is not an option: the go2rtc build these
streams are served by stamps a transcoded AAC track on a 90 kHz clock and every
HLS viewer played about eleven times too slow.

So the publish carries its own AAC track, after the A-law one, with timestamps
we own. Everything that reads the first audio track (WebRTC) is unchanged.
"""

from __future__ import annotations

import logging
import os
import struct

_LOGGER = logging.getLogger(__name__)

ENV_PUBLISH_AAC = "AIDOT_PUBLISH_AAC"
AAC_CLOCK_RATE = 48000
AAC_SAMPLES_PER_FRAME = 1024
AAC_BITRATE = 64000
PCMA_RATE = 8000
#: How long the camera may send no audio before silence is generated in its place.
AAC_IDLE_FILL_S = 0.5
#: The largest silence one step may insert; a larger jump re-anchors instead.
AAC_MAX_FILL_S = 5.0

_SR_INDEX = {
    96000: 0,
    88200: 1,
    64000: 2,
    48000: 3,
    44100: 4,
    32000: 5,
    24000: 6,
    22050: 7,
    16000: 8,
    12000: 9,
    11025: 10,
    8000: 11,
    7350: 12,
}
_OFF = ("0", "false", "no", "off")


def publish_aac_enabled() -> bool:
    """Whether the direct publish adds the AAC track (default on)."""
    return os.environ.get(ENV_PUBLISH_AAC, "1").strip().lower() not in _OFF


def audio_specific_config(
    sample_rate: int = AAC_CLOCK_RATE, channels: int = 1
) -> bytes:
    """ISO 14496-3 AudioSpecificConfig for AAC-LC (object type 2)."""
    return struct.pack(
        "!H", (2 << 11) | (_SR_INDEX[sample_rate] << 7) | (channels << 3)
    )


def aac_fmtp(sample_rate: int = AAC_CLOCK_RATE, channels: int = 1) -> str:
    """The ``a=fmtp`` body for an RFC 3640 AAC-hbr track."""
    return (
        "streamtype=5;profile-level-id=1;mode=AAC-hbr;"
        "sizelength=13;indexlength=3;indexdeltalength=3;"
        "config=" + audio_specific_config(sample_rate, channels).hex()
    )


def packetize_aac(frame: bytes) -> bytes:
    """One access unit as an RFC 3640 payload: AU-headers-length (bits), one header."""
    return struct.pack("!HH", 16, len(frame) << 3) + frame
