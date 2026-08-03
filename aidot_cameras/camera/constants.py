"""Wire/protocol constants for the AiDot camera client.

Extracted verbatim from client.py (behavior-preserving). client.py
re-imports these names, so the public/runtime surface is unchanged.
"""

import os
import struct

# AppKey from LDSAppOpenSDK CocoaPods docs (kLDSAppOpenSDKKey = "appa070")
_LEEDARSON_APP_KEY = "appa070"

# APP_ID sent in smarthome HTTP headers - value from MainActivity.java SharedPrefs
# (SharePreferenceUtils.setPrefString(this, "APP_ID", "1392315867093508098")).
_LEEDARSON_APP_ID = "1392315867093508098"

# Camera-specific backend; region prefix mirrors AidotClient._base_url pattern.
# e.g. "us" -> "https://us-smarthome.arnoo.com:443"
_SMARTHOME_URL_TEMPLATE = "https://{region}-smarthome.arnoo.com:443"

# AiDot platform API base (the prod-{region}-api.arnoo.com family).
_AIDOT_API_BASE_TEMPLATE = "https://prod-{region}-api.arnoo.com"

# --------------------------------------------------------------------------- #
# Test-environment seams.
#
# Every cloud endpoint the camera layer contacts routes through one of these
# helpers, so an end-to-end test can point the WHOLE client at a local fake
# environment via env vars (the same opt-in pattern as
# AIDOT_SDES_HOLEPUNCH_HOST).  Read at call time, never cached at import, and
# defaulting to today's production URLs - unset means byte-identical behavior.
#
# These exist so the e2e tier CANNOT reach the real cloud: without them the
# fake-lab tests fall through to prod-{region}-api.arnoo.com and the live MQTT
# broker, which is real egress with test credentials from CI.


def smarthome_base(region: str) -> str:
    """Smarthome API base for ``region`` (AIDOT_SMARTHOME_URL_TEMPLATE override)."""
    tmpl = os.environ.get("AIDOT_SMARTHOME_URL_TEMPLATE") or _SMARTHOME_URL_TEMPLATE
    return tmpl.format(region=region)


def aidot_api_base(region: str) -> str:
    """Platform API base for ``region`` (AIDOT_API_BASE_TEMPLATE override)."""
    tmpl = os.environ.get("AIDOT_API_BASE_TEMPLATE") or _AIDOT_API_BASE_TEMPLATE
    return tmpl.format(region=region)


def _ice_uris_from_env(var: str, default: list) -> list:
    """Comma-separated ICE URI list from ``var``; unset -> default, "" -> []."""
    env = os.environ.get(var)
    if env is None:
        return list(default)
    return [u.strip() for u in env.split(",") if u.strip()]


def stun_server_uris() -> list:
    """Default STUN server URIs (AIDOT_STUN_SERVERS override; "" disables)."""
    return _ice_uris_from_env("AIDOT_STUN_SERVERS", ["stun:stun.l.google.com:19302"])


def fallback_turn_uris() -> list:
    """TURN relay URIs appended when the cloud ICE config carries none
    (AIDOT_TURN_SERVERS override; "" disables the hardcoded Arnoo fallback)."""
    return _ice_uris_from_env(
        "AIDOT_TURN_SERVERS",
        ["stun:3.230.182.123:3478", "turn:3.230.182.123:5349"],
    )


# --------------------------------------------------------------------------- #
# Playback TCP binary framing constants
#
# Wire layout (all big-endian) from RecordVideoEncoder.java, verified against
# INettyClientInitializer.java Netty params:
#   lengthFieldOffset=14, lengthFieldLength=4, lengthAdjustment=19
#
# version(H2) seq(i4) cmd(H2) subcmd(H2) cmdParam(i4)  <- 14 bytes
# payloadLen(i4)                                         <- offset 14
# timestamp(q8) context(i4) encodeType(b1) result(h2) reserve(i4)  <- 19 bytes
# <payload bytes>
# Total header = 37 bytes
# --------------------------------------------------------------------------- #

_HDR_FMT         = ">HiHHiiqibhi"
_HDR_SIZE        = struct.calcsize(_HDR_FMT)           # 37
_HDR_PREFIX_FMT  = ">HiHHii"
_HDR_PREFIX_SIZE = struct.calcsize(_HDR_PREFIX_FMT)    # 18
_HDR_SUFFIX_FMT  = ">qibhi"
_HDR_SUFFIX_SIZE = struct.calcsize(_HDR_SUFFIX_FMT)    # 19

assert _HDR_SIZE        == 37
assert _HDR_PREFIX_SIZE == 18
assert _HDR_SUFFIX_SIZE == 19

# Fixed values for all outbound request frames
_HDR_VERSION  = 256   # 0x0100
_HDR_CONTEXT  = 1005
_HDR_ENC_TYPE = 1
_HDR_RESULT   = 4
_HDR_RESERVE  = 2

# TCP command codes from AppCmd.java
_CMD_LOGIN_REQ  = 0x0101
_CMD_LOGIN_RES  = 0x0102
_CMD_HB_REQ     = 0x0105
_CMD_HB_RES     = 0x0106
_CMD_STREAM_REQ = 0x0107
_CMD_STREAM_RES = 0x0108
_CMD_SUBCMD     = 0x0001
_CMD_PARAM      = 0x00000002

# Video sub-frame header size from LDSPlayer.decodeStream():
# padding(2) frameType(1) audioCodec(1) timestamp(8) encType(1) payloadLen(4)
_SF_HDR_SIZE = 17

# Frame type values
_FRAME_TYPE_P_FRAME = 2
_FRAME_TYPE_B_FRAME = 3
_FRAME_TYPE_I_FRAME = 4   # keyframe
_FRAME_TYPE_AUDIO   = 5

_AUDIO_CODEC_G711A = 1

_PTZ_DIR_CODES: dict = {
    "stop": 0, "up": 1, "down": 2, "left": 3,
    "left_up": 4, "left_down": 5, "right": 6,
    "right_up": 7, "right_down": 8, "auto": 9,
    "set_point": 10, "clear_point": 11, "goto": 12,
    "zoom_in": 23, "zoom_out": 24,
}

# Live-stream resolution (AVIOCTRL_QUALITY_*).  The official app's HD/SD toggle
# switches between MAX(1) and MIDDLE(5) (f0.java g3() / LiveFragment d6); we
# mirror those two.  Sent as SETSTREAMCTRL (cmd 800) over the active session,
# payload <IB3x> = channel(0) + quality byte (SMsgAVIoctrlSetStreamCtrlReq).
SETSTREAMCTRL_CMD = 800
GETSTREAMCTRL_CMD = 802
_STREAM_QUALITY: dict = {"hd": 1, "sd": 5}  # AVIOCTRL_QUALITY_MAX / _MIDDLE


# livePlayResp code for "not ready": the camera accepted the request but cannot
# stream yet (a battery camera still waking).  Explicitly NOT a refusal - mains
# cameras emit it too and recover via ICE - so no path aborts an open on it.  It
# is only read after the fact, to tell a waking camera apart from a generically
# failed session when choosing a retry delay (see _live_play_not_ready).
_LIVE_PLAY_NOT_READY = -50019


# Two-way-audio (talk) / SDES timing constants (moved from client.py)
TALK_PCM_RATE = 8000
TALK_PCM_FRAME_BYTES = 320  # 160 samples (20 ms @ 8 kHz) x 2 bytes (s16)
SDES_SPEAKERSTART_DELAY = 0.6     # seconds after command channel up
SDES_TALK_PUMP_IDLE_TICK = 0.1   # pump idle sleep when not actively speaking
