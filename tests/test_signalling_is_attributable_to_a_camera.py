"""Signalling log lines and wake evidence must name the camera they came from.

The MQTT connection carries the whole account, and every concurrent open's
dispatcher sees every message.  ``webrtc: camera replied  method=...
endpoint=...`` dropped the device entirely, so on a fleet with two battery
cameras the same message appeared five or six times and belonged to none of
them in particular - several hours of a 2026-09-03 investigation were spent
disentangling that by hand.

``srcAddr`` is what settles it.  The account uses a prefix convention, verified
against live captures: ``2.<device_id>`` is the device, ``0.<user_id>`` the
app, ``9.<user_id>`` the server.  So a message from the camera can always be
named, and one from the cloud is visibly not from a camera.

The same distinction is what the stale-offer detector needs: it must arm on the
camera's own traffic and not on the cloud's ack for it, which is why
``_is_camera_present_signal`` grew ``accept_server_ack``.  The gate itself is
unchanged - it still accepts the ack, for every camera.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.webrtc_open import (
    _is_camera_present_signal,
    _signal_device_id,
    _signal_origin,
)

DEV = "a9b8c7d6e5f40312213243546576879a"
OTHER = "00112233445566778899aabbccddeeff"
UID = "5354ad296b414fe9be581c7116f246e0"

SERVER_ACK_TOPIC = f"iot/v1/c/{UID}/IPCAM/lowPowerActiveStateResp"
SERVER_ACK = {
    "ack": {"code": 200, "desc": "Success."},
    "clientId": "server-6a9f72d1901fc6fe2698a1ad84a0e715",
    "method": "lowPowerActiveStateResp",
    "service": "IPCAM",
    "srcAddr": f"9.{UID}",
}

WAKEUP_TOPIC = f"iot/v1/cb/{DEV}/device/wakeupStatus"
WAKEUP = {
    "service": "device",
    "seq": "240775779d",
    "srcAddr": f"2.{DEV}",
    "method": "wakeupStatus",
    "tst": 1638258124214,
    "payload": {"extends": None},
}

KEEPALIVE_TOPIC = f"iot/v1/cb/{DEV}/device/keepAliveState"
KEEPALIVE = {"service": "device", "method": "keepAliveState",
             "payload": {"devId": DEV}}


# --------------------------------------------------------------------------- #
# Naming the sender
# --------------------------------------------------------------------------- #

def test_a_camera_message_is_named_by_its_srcaddr():
    assert _signal_device_id(WAKEUP_TOPIC, WAKEUP) == DEV


def test_a_camera_message_is_named_by_its_devid_when_srcaddr_is_absent():
    assert _signal_device_id(KEEPALIVE_TOPIC, KEEPALIVE) == DEV


def test_a_top_level_devid_names_it_too():
    assert _signal_device_id("iot/v1/s/x/IPC/webrtcResp", {"devId": DEV}) == DEV


def test_a_server_message_is_not_attributed_to_any_camera():
    """The ack's srcAddr is 9.<user_id>. Naming the user id as a device would be
    worse than saying nothing - it reads as a camera that does not exist."""
    assert _signal_device_id(SERVER_ACK_TOPIC, SERVER_ACK) is None


def test_an_app_message_is_not_attributed_to_any_camera():
    assert _signal_device_id("iot/v1/s/x/IPC/livePlayReq",
                             {"srcAddr": f"0.{UID}"}) is None


def test_an_unidentifiable_message_is_not_guessed_at():
    assert _signal_device_id("iot/v1/s/x/IPC/livePlayResp", {}) is None
    assert _signal_device_id("", {"srcAddr": "2."}) is None
    assert _signal_device_id("", {"srcAddr": 2}) is None


def test_a_malformed_message_does_not_raise_into_the_dispatcher():
    """This runs on the MQTT thread for every inbound message; a payload that is
    not a dict must not take the signalling thread down."""
    assert _signal_device_id("t", None) is None
    assert _signal_device_id(None, {"srcAddr": f"2.{DEV}"}) == DEV
    assert _signal_device_id("t", {"payload": "not-a-dict"}) is None


# --------------------------------------------------------------------------- #
# Camera evidence vs the cloud's ack for it
# --------------------------------------------------------------------------- #

def test_the_ack_is_not_the_camera():
    assert _is_camera_present_signal(
        SERVER_ACK_TOPIC, SERVER_ACK, DEV, accept_server_ack=False) is False


def test_the_gate_itself_is_unchanged_and_still_accepts_the_ack():
    """No behaviour change on the open path: the pre-offer gate keeps releasing
    on the ack for every camera, exactly as before."""
    assert _is_camera_present_signal(SERVER_ACK_TOPIC, SERVER_ACK, DEV) is True


def test_the_cameras_own_traffic_is_evidence_without_the_ack_clause():
    assert _is_camera_present_signal(
        WAKEUP_TOPIC, WAKEUP, DEV, accept_server_ack=False) is True
    assert _is_camera_present_signal(
        KEEPALIVE_TOPIC, KEEPALIVE, DEV, accept_server_ack=False) is True


def test_another_cameras_traffic_is_not_evidence_for_this_one():
    """The property the stale-offer detector rests on: two battery cameras share
    this connection, and one waking must not arm the other's detector."""
    assert _is_camera_present_signal(
        f"iot/v1/cb/{OTHER}/device/wakeupStatus",
        {**WAKEUP, "srcAddr": f"2.{OTHER}"}, DEV,
        accept_server_ack=False) is False


# --------------------------------------------------------------------------- #
# How the sender is written into the line
# --------------------------------------------------------------------------- #

def test_this_cameras_message_is_tagged_with_its_id():
    assert _signal_origin(WAKEUP_TOPIC, WAKEUP, DEV) == f"dev={DEV[:12]}"


def test_another_cameras_message_says_so():
    other = {**WAKEUP, "srcAddr": f"2.{OTHER}"}
    assert _signal_origin(f"iot/v1/cb/{OTHER}/device/wakeupStatus", other,
                          DEV) == f"dev={OTHER[:12]} (not this camera)"


def test_a_message_from_no_camera_is_not_called_another_camera():
    """The cloud ack and our own echoes carry a user id, not a device id.
    Tagging them "not this camera" reads as a camera that exists somewhere."""
    assert _signal_origin(SERVER_ACK_TOPIC, SERVER_ACK, DEV) == "dev=-"
    assert _signal_origin("iot/v1/s/x/IPC/livePlayReq",
                          {"srcAddr": f"0.{UID}"}, DEV) == "dev=-"
