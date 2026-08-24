"""The pre-offer wake gate must recognise the camera's own device-channel traffic.

`camera_ready_ev` releases the wait before the SDP offer is published.  Two
kinds of real camera evidence could never satisfy it:

*   The client subscribes to `iot/v1/cb/{device_id}/#` but the gate only ever
    checked `iot/v1/c/{device_id}/` - "c", no "b" - so no device-channel topic
    matched.
*   `wakeupStatus`, the camera announcing it is awake, carries no `devId` at
    top level or in `payload`.  It identifies itself only through
    `srcAddr: "2.{device_id}"`, which nothing read.

Captured live 2026-08-07:

    topic: iot/v1/cb/001122334455.../device/wakeupStatus
    {"service":"device","seq":"240775779d",
     "srcAddr":"2.00112233445566778899aabbccddeeff",
     "method":"wakeupStatus","tst":1638258124214,"payload":{"extends":null}}

`lowPowerActiveStateResp` remains sufficient on its own, and that is
deliberate rather than an oversight.  It is a cloud ack - published by the
server (`clientId "server-..."`), on the user channel, with no `devId` - and it
arrives in ~60 ms regardless of the camera.  Gating battery cameras on real
device evidence instead was tried and reverted: the official app does not do
it.  `DeviceWakeUpRepos.wakeUpOrSleep` fires the MQTT wake fire-and-forget and
completes the wake step from the HTTP `lowPowerActiveState` response
(`DeviceWakeUpRepos$1.onSuccess` -> `onNext` + `onComplete` on both branches),
and "wakeupStatus" appears nowhere in the app.  So these tests pin the additive
recognition only; the ack's sufficiency is app parity and must not be narrowed
without evidence that the app diverges.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.webrtc_open import _is_camera_present_signal

DEV = "a9b8c7d6e5f40312213243546576879a"
OTHER = "00112233445566778899aabbccddeeff"
UID = "5354ad296b414fe9be581c7116f246e0"

# All three captured verbatim from the live HA host, 2026-08-07.
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

EVENT_TOPIC = f"iot/v1/cb/{DEV}/device/devEventNotif"
EVENT = {
    "service": "device",
    "method": "devEventNotif",
    "seq": "123456",
    "srcAddr": f"2.{DEV}",
    "payload": {"devId": DEV, "event": "sleep_status_changed",
                "arguments": ["wakeup"]},
}


def test_wakeup_status_is_evidence_even_though_it_carries_no_devid():
    # The gap: the camera's own wake announcement matched no clause, because
    # its topic is /cb/ and its only identity is srcAddr.
    assert _is_camera_present_signal(WAKEUP_TOPIC, WAKEUP, DEV) is True


def test_device_channel_topic_alone_is_evidence_without_srcaddr():
    # Pins the iot/v1/cb/{device_id}/ clause on its own: the camera's other
    # device-channel messages are not guaranteed to carry srcAddr.
    no_src = {k: v for k, v in WAKEUP.items() if k != "srcAddr"}
    assert _is_camera_present_signal(WAKEUP_TOPIC, no_src, DEV) is True


def test_srcaddr_alone_is_evidence_on_the_user_channel():
    # Pins the srcAddr clause on its own.  This is the A001064 PTZ shape - it
    # answers on the user channel, where no device-scoped topic prefix matches.
    on_user_channel = f"iot/v1/c/{UID}/IPC/someResp"
    msg = {"method": "someResp", "srcAddr": f"2.{DEV}", "payload": {}}
    assert _is_camera_present_signal(on_user_channel, msg, DEV) is True


def test_device_channel_message_with_devid_is_evidence():
    assert _is_camera_present_signal(EVENT_TOPIC, EVENT, DEV) is True


def test_another_cameras_wake_announcement_is_not_evidence_for_this_one():
    # One shared account MQTT connection carries every camera's traffic, so the
    # additive clauses must not leak across devices.
    assert _is_camera_present_signal(WAKEUP_TOPIC, WAKEUP, OTHER) is False


def test_another_cameras_device_event_is_not_evidence_for_this_one():
    assert _is_camera_present_signal(EVENT_TOPIC, EVENT, OTHER) is False


def test_server_ack_releases_the_gate_for_any_camera():
    # App parity, not an oversight - see the module docstring.  Narrowing this
    # (e.g. to mains only) is the change that was tried and reverted.
    assert _is_camera_present_signal(SERVER_ACK_TOPIC, SERVER_ACK, DEV) is True
    assert _is_camera_present_signal(SERVER_ACK_TOPIC, SERVER_ACK, OTHER) is True


def test_unrelated_user_channel_traffic_is_not_evidence():
    # Guards the ack clause against widening to any user-channel response.
    other_resp = dict(SERVER_ACK, method="getIceConfigResp")
    assert _is_camera_present_signal(
        f"iot/v1/c/{UID}/IPC/getIceConfigResp", other_resp, DEV) is False
