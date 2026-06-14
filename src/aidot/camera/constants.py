"""Wire/protocol constants for the AiDot camera client.

Extracted verbatim from client.py (behavior-preserving). client.py
re-imports these names, so the public/runtime surface is unchanged.
"""

import struct

# AppKey from LDSAppOpenSDK CocoaPods docs (kLDSAppOpenSDKKey = "appa070")
_LEEDARSON_APP_KEY = "appa070"

# APP_ID sent in smarthome HTTP headers - value from MainActivity.java SharedPrefs
# (SharePreferenceUtils.setPrefString(this, "APP_ID", "1392315867093508098")).
_LEEDARSON_APP_ID = "1392315867093508098"

# Camera-specific backend; region prefix mirrors AidotClient._base_url pattern.
# e.g. "us" -> "https://us-smarthome.arnoo.com:443"
_SMARTHOME_URL_TEMPLATE = "https://{region}-smarthome.arnoo.com:443"

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
