"""Wire/protocol constants for the AiDot camera client.

Extracted verbatim from client.py (behavior-preserving). client.py
re-imports these names, so the public/runtime surface is unchanged.
"""

import re
from typing import Optional

from .. import settings
from ..exceptions import AidotUnknownRegion

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


# --------------------------------------------------------------------------- #
# Regions.
#
# The account's region is formatted into every cloud hostname
# (``prod-{region}-api``, ``{region}-smarthome``, ``{region}-mqtt``), and it
# comes from stored login data rather than from this library, so it is checked
# before it reaches a URL: a value that is not a plain hostname label, or not a
# region the AiDot cloud is known to serve, is refused rather than sent.
#
# Where the list comes from (every source agrees; none names a fourth region):
#   * upstream python-aidot's country table (``aidot.const``), whose ``region``
#     column holds only US, EU and JP, lowercased by upstream at login;
#   * the vendor Android app's bundled region table, whose region codes are
#     only US, EU and JP;
#   * cloud hosts recorded in captures (``prod-us-api``, ``us-smarthome``,
#     ``eu-smarthome``).
# A region the cloud adds later can be accepted without a release through
# AIDOT_EXTRA_REGIONS.
KNOWN_REGIONS = frozenset({"us", "eu", "jp"})

# One hostname label: lowercase letters, digits and inner hyphens, at most 32.
_REGION_SHAPE = re.compile(r"[a-z0-9](?:[a-z0-9-]{0,30}[a-z0-9])?")


def _extra_regions() -> "frozenset[str]":
    """AIDOT_EXTRA_REGIONS, lowercased; entries of the wrong shape dropped."""
    return frozenset(
        r.lower()
        for r in settings.read("AIDOT_EXTRA_REGIONS")
        if _REGION_SHAPE.fullmatch(r.lower())
    )


def validate_region(region: object) -> str:
    """``region`` normalised to lowercase, or :class:`AidotUnknownRegion`.

    Hostnames are case-insensitive, so ``"US"`` from an older stored login is
    the same region as ``"us"``; anything else must be a known region (or one
    named in AIDOT_EXTRA_REGIONS) and shaped like a hostname label.
    """
    if not isinstance(region, str):
        raise AidotUnknownRegion(region)
    norm = region.lower()
    if not _REGION_SHAPE.fullmatch(norm):
        raise AidotUnknownRegion(region)
    if norm not in KNOWN_REGIONS and norm not in _extra_regions():
        raise AidotUnknownRegion(region)
    return norm


def smarthome_base(region: str) -> str:
    """Smarthome API base for ``region`` (AIDOT_SMARTHOME_URL_TEMPLATE override)."""
    tmpl = settings.read("AIDOT_SMARTHOME_URL_TEMPLATE") or _SMARTHOME_URL_TEMPLATE
    return tmpl.format(region=validate_region(region))


def aidot_api_base(region: str) -> str:
    """Platform API base for ``region`` (AIDOT_API_BASE_TEMPLATE override)."""
    tmpl = settings.read("AIDOT_API_BASE_TEMPLATE") or _AIDOT_API_BASE_TEMPLATE
    return tmpl.format(region=validate_region(region))


def mqtt_fallback_url(region: str) -> str:
    """The regional broker URL used when the cloud names none."""
    return f"wss://{validate_region(region)}-mqtt.arnoo.com:8443/mqtt"


# --------------------------------------------------------------------------- #
# MQTT broker URL schemes.
#
# The broker connection carries the account's MQTT password, and the URL comes
# from the cloud (getServerUrlConfig). paho connects over websockets and turns
# TLS on only for a TLS scheme, so a ``ws://`` URL would send the password in
# the clear. These are refused unless AIDOT_ALLOW_PLAINTEXT_MQTT opts in.
PLAINTEXT_MQTT_SCHEMES = frozenset({"ws", "tcp", "mqtt", "http"})
_URL_SCHEME = re.compile(r"([A-Za-z][A-Za-z0-9+.-]*)://")


def plaintext_mqtt_scheme(url: str) -> "Optional[str]":
    """The URL's scheme, lowercased, if it is a known non-TLS one; else None.

    A bare host (no scheme) is not plaintext: the caller prefixes ``wss://``.
    """
    m = _URL_SCHEME.match(url or "")
    if m and m.group(1).lower() in PLAINTEXT_MQTT_SCHEMES:
        return m.group(1).lower()
    return None


def _ice_uris_from_env(var: str, default: list) -> list:
    """Comma-separated ICE URI list from ``var``; unset -> default, "" -> [].

    ``default`` is the registry's and is kept in the call for the reader."""
    return list(settings.read(var))


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


# AVIO control command ids, one table for both transports.  Named after the
# app's com.tutk.IOTC.AVIOCTRLDEFs, values copied from it (read 2026-11-07).
# Every frame these ride in is the 28-byte header protocol.avio_header packs.
IOTYPE_USER_IPCAM_START = 0x1FF  # 511
IOTYPE_USER_IPCAM_AUDIOSTART = 0x300  # 768
IOTYPE_USER_IPCAM_AUDIOSTOP = 0x301  # 769
IOTYPE_USER_IPCAM_LISTEVENT_REQ = 0x318
IOTYPE_USER_IPCAM_LISTEVENT_RESP = 0x319
IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL = 0x31A
IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL_RESP = 0x31B
IOTYPE_USER_IPCAM_SETSTREAMCTRL_REQ = 0x320  # 800
IOTYPE_USER_IPCAM_SETSTREAMCTRL_RESP = 0x321  # 801
IOTYPE_USER_IPCAM_GETSTREAMCTRL_REQ = 0x322  # 802
#: The app calls 0x324 both SETMOTIONDETECT_REQ and NOTIFY_TRACK_REQ; what the
#: camera sends unprompted under it is the track-switch notification
#: (processRespData case 0x324).
IIOTYPE_USER_TYPE_NOTIFY_TRACK_REQ = 0x324  # 804
IOTYPE_USER_IPCAM_SPEAKERSTART = 0x350  # 848
IOTYPE_USER_IPCAM_SPEAKERSTOP = 0x351  # 849
#: The SPEAKERSTART ack on the WebRTC transports; 0x352 is the TUTK one.
IOTYPE_USER_IPCAM_SPEAKERSTART_WEB_RTC_RESP = 0x353  # 851
#: Delete card recordings (the app's deleteRecord): 0x4b7 asks, 0x4b8 answers
#: with one byte, 0 for done.
IOTYPE_USER_IPCAM_DELLISTEVENT_REQ = 0x4B7
IOTYPE_USER_IPCAM_DELLISTEVENT_RESP = 0x4B8
# Card thumbnails (the app's getThumbnais, 0x4b9/0x4ba "custom command") are
# NOT implemented: the camera streams the picture back over a separate native
# TUTK AV channel (avClientStartEx -> avRecvFrameData2), which this library's
# WebRTC/SDES bridge cannot open or read. See docs/PROTOCOL-INVENTORY.md.
IOTYPE_USER_IPCAM_HASLISTEVENT_REQ = 0x4B5
IOTYPE_USER_IPCAM_HASLISTEVENT_RESP = 0x4B6
IOTYPE_USER_IPCAM_PTZ_COMMAND = 0x1001  # 4097
CMD_AVIO_CTRL_HEARTHEAT_REQ = 0x1424  # 5156, the app's spelling
CMD_AVIO_CTRL_HEARTHEAT_RESP = 0x1425  # 5157
E_CMD_AVIO_CTRL_SESSION_MODE_REQ = 0x1500  # 5376, "LIVING"
E_CMD_AVIO_CTRL_SESSION_MODE_RESP = 0x1501  # 5377

# Frame type values
_FRAME_TYPE_P_FRAME = 2
_FRAME_TYPE_B_FRAME = 3
_FRAME_TYPE_I_FRAME = 4  # keyframe
_FRAME_TYPE_AUDIO = 5

_PTZ_DIR_CODES: dict = {
    "stop": 0,
    "up": 1,
    "down": 2,
    "left": 3,
    "left_up": 4,
    "left_down": 5,
    "right": 6,
    "right_up": 7,
    "right_down": 8,
    "auto": 9,
    "set_point": 10,
    "clear_point": 11,
    "goto": 12,
    "zoom_in": 23,
    "zoom_out": 24,
}

# Live-stream resolution (AVIOCTRL_QUALITY_*).  The official app's picker has
# THREE arms, not two: MAX(1), MIDDLE(5), and 0x10 = 16, whose label is
# _auto_resulution (HorLiveController.smali:629-631).  All three are offered:
# measured 2026-11-09 on an A000088, the camera records the value it was sent
# as its StreamType property (hd -> "0", sd -> "1", auto -> "2"), keeps it
# across sessions, and the app reads that property back for its label - so a
# setting made here is what the app shows, and one made in the app is what the
# property reports.  Whether the encode follows is the camera's affair.
# Sent as IOTYPE_USER_IPCAM_SETSTREAMCTRL_REQ (800) over the active session,
# payload <IB3x> = channel(0) + quality byte (SMsgAVIoctrlSetStreamCtrlReq).
_STREAM_QUALITY: dict = {"hd": 1, "sd": 5, "auto": 16}  # MAX / MIDDLE / the app's Auto


# livePlayResp code for "not ready": the camera accepted the request but cannot
# stream yet (a battery camera still waking).  Explicitly NOT a refusal - mains
# cameras emit it too and recover via ICE - so no path aborts an open on it.  It
# is only read after the fact, to tell a waking camera apart from a generically
# failed session when choosing a retry delay (see _live_play_not_ready).
_LIVE_PLAY_NOT_READY = -50019


# Two-way-audio (talk) / SDES timing constants (moved from client.py)
TALK_PCM_RATE = 8000
TALK_PCM_FRAME_BYTES = 320  # 160 samples (20 ms @ 8 kHz) x 2 bytes (s16)
SDES_SPEAKERSTART_DELAY = 0.6  # seconds after command channel up
SDES_TALK_PUMP_IDLE_TICK = 0.1  # pump idle sleep when not actively speaking
