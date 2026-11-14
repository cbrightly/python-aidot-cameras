"""The read-only firmware version query.

The vendor app asks whether a newer firmware exists when its live page opens
(``NewLivePresenter``): ``getVerListReq`` on the ``ota`` service, published to
``iot/v1/s/{userId}/ota/getVerListReq`` with a payload of ``devId``,
``productId``, ``homeId`` and ``devType: "IPC"``. Its MQTT layer stamps
``srcAddr``, ``seq`` and ``tst`` and hands back the message whose ``seq``
matches; the live page then reads ``payload.verList[0]`` for ``newVersion``
and ``oldVersion`` and offers an update when the two differ.

These tests pin that shape, and the rules around it: nothing is installed,
unknown is None rather than "up to date", one camera never reads another's
answer, and the request is sent whatever the camera is doing.
"""

import asyncio
import json

import pytest

from aidot_cameras.camera.models import FirmwareInfo, parse_firmware_version_list

_RESP_TOPIC = "iot/v1/c/user-1/ota/getVerListResp"


class _Pm:
    """A persistent MQTT connection that answers with scripted replies."""

    def __init__(self, replies=()):
        self.replies = list(replies)
        self.requests = []
        self.subscriptions = []
        self.status = {}
        self.timeouts = []

    async def request(self, *, publish_items, subscribe_topics, timeout, until=None):
        """As the real one: collect in order, stop at the first message
        ``until`` accepts."""
        self.requests.append(publish_items)
        self.subscriptions.append(subscribe_topics)
        self.timeouts.append(timeout)
        seq = json.loads(publish_items[0][1])["seq"]
        msgs = []
        for topic, body in self.replies:
            body = body(seq) if callable(body) else body
            msgs.append((topic, body))
            if until is not None and until(topic, body):
                break
        return msgs, self.status


def _reply(new="2.0.1", old="2.0.0", seq="ours", **entry_extra):
    def build(our_seq):
        entry = {"newVersion": new, "oldVersion": old, **entry_extra}
        return json.dumps(
            {
                "method": "getVerListResp",
                "service": "ota",
                "seq": our_seq if seq == "ours" else seq,
                "payload": {"verList": [entry]},
            }
        )

    return (_RESP_TOPIC, build)


def _camera(make_camera_device_client, profile="A001064", replies=(), **overrides):
    cam = make_camera_device_client(profile, **overrides)
    pm = _Pm(replies)

    async def _get_pm():
        return pm

    cam._get_persistent_mqtt = _get_pm
    return cam, pm


def _sent(pm):
    topic, body = pm.requests[0][0]
    return topic, json.loads(body)


class TestTheRequestIsTheApps:
    def test_topic_and_fields(self, make_camera_device_client):
        cam, pm = _camera(
            make_camera_device_client, productId="prod-9", houseId="house-7"
        )
        asyncio.run(cam.async_get_firmware_info())
        assert len(pm.requests) == 1 and len(pm.requests[0]) == 1
        topic, msg = _sent(pm)
        assert topic == f"iot/v1/s/{cam.user_id}/ota/getVerListReq"
        assert msg["method"] == "getVerListReq"
        assert msg["service"] == "ota"
        assert msg["devId"] == cam.device_id
        assert msg["payload"] == {
            "devId": cam.device_id,
            "productId": "prod-9",
            "homeId": "house-7",
            "devType": "IPC",
        }

    def test_the_envelope_carries_what_the_apps_mqtt_layer_stamps(
        self, make_camera_device_client
    ):
        cam, pm = _camera(make_camera_device_client)
        asyncio.run(cam.async_get_firmware_info())
        _topic, msg = _sent(pm)
        assert msg["srcAddr"] == f"0.{cam.user_id}"
        assert isinstance(msg["seq"], str) and msg["seq"]
        assert isinstance(msg["tst"], int)

    def test_a_record_without_a_house_sends_empty_strings_as_the_app_does(
        self, make_camera_device_client
    ):
        cam, pm = _camera(make_camera_device_client)
        cam._raw_device.pop("houseId", None)
        cam._raw_device.pop("productId", None)
        asyncio.run(cam.async_get_firmware_info())
        _topic, msg = _sent(pm)
        assert msg["payload"]["homeId"] == "" and msg["payload"]["productId"] == ""

    def test_it_asks_nothing_else(self, make_camera_device_client):
        """One publish on the ota service: no wake, no action, no install."""
        cam, pm = _camera(make_camera_device_client, replies=[_reply()])
        asyncio.run(cam.async_get_firmware_info())
        sent = json.dumps(pm.requests)
        for word in ("devActionReq", "lowPowerActiveStateReq", "setDevAttrReq"):
            assert word not in sent

    def test_it_listens_on_the_account_topics(self, make_camera_device_client):
        cam, pm = _camera(make_camera_device_client)
        asyncio.run(cam.async_get_firmware_info())
        assert f"iot/v1/c/{cam.user_id}/#" in pm.subscriptions[0]


class TestTheReply:
    def test_new_and_old_version_are_read_from_the_first_entry(
        self, make_camera_device_client
    ):
        cam, _pm = _camera(make_camera_device_client, replies=[_reply()])
        info = asyncio.run(cam.async_get_firmware_info())
        assert info == FirmwareInfo(installed_version="2.0.0", latest_version="2.0.1")
        assert info.update_available is True
        assert info.release_notes is None

    def test_equal_versions_mean_up_to_date(self, make_camera_device_client):
        cam, _pm = _camera(
            make_camera_device_client, replies=[_reply(new="2.0.0", old="2.0.0")]
        )
        info = asyncio.run(cam.async_get_firmware_info())
        assert info.latest_version == "2.0.0" and info.update_available is False

    def test_fields_the_app_does_not_read_are_ignored(self, make_camera_device_client):
        cam, _pm = _camera(
            make_camera_device_client, replies=[_reply(somethingElse="x")]
        )
        assert asyncio.run(cam.async_get_firmware_info()).latest_version == "2.0.1"

    def test_no_reply_is_unknown(self, make_camera_device_client):
        cam, _pm = _camera(make_camera_device_client)
        assert asyncio.run(cam.async_get_firmware_info()) is None

    def test_a_reply_for_another_request_is_not_ours(self, make_camera_device_client):
        """The connection carries the whole account, and several cameras ask in
        the same window; the app finds its reply by seq, and so does this."""
        cam, _pm = _camera(
            make_camera_device_client, replies=[_reply(new="9.9.9", seq="ap0000001")]
        )
        assert asyncio.run(cam.async_get_firmware_info()) is None

    def test_our_reply_is_found_among_others(self, make_camera_device_client):
        cam, _pm = _camera(
            make_camera_device_client,
            replies=[
                _reply(new="9.9.9", seq="ap0000001"),
                ("iot/v1/c/user-1/device/setDevAttrNotif", "not json"),
                _reply(new="2.0.1"),
            ],
        )
        assert asyncio.run(cam.async_get_firmware_info()).latest_version == "2.0.1"

    def test_our_own_request_echoed_back_is_not_a_reply(
        self, make_camera_device_client
    ):
        def echo(our_seq):
            return json.dumps(
                {
                    "method": "getVerListReq",
                    "seq": our_seq,
                    "payload": {"verList": [{"newVersion": "9", "oldVersion": "1"}]},
                }
            )

        cam, _pm = _camera(make_camera_device_client, replies=[("t", echo)])
        assert asyncio.run(cam.async_get_firmware_info()) is None

    def test_it_stops_waiting_when_its_reply_is_in(self, make_camera_device_client):
        """The collection ends at the reply with our seq, not at the timeout:
        what follows it on the connection is never collected. Another
        request's reply, text that is not JSON and our own request echoed
        back do not end it."""

        def echo(our_seq):
            return json.dumps({"method": "getVerListReq", "seq": our_seq})

        cam, pm = _camera(
            make_camera_device_client,
            replies=[
                _reply(new="9.9.9", seq="ap0000001"),
                ("iot/v1/c/user-1/device/setDevAttrNotif", "not json"),
                ("t", echo),
                _reply(new="2.0.1"),
                _reply(new="3.0.0"),
            ],
        )
        seen = []
        real = pm.request

        async def _request(**kwargs):
            msgs, status = await real(**kwargs)
            seen.extend(msgs)
            return msgs, status

        pm.request = _request
        info = asyncio.run(cam.async_get_firmware_info(timeout=4.0))
        assert info.latest_version == "2.0.1"
        assert len(seen) == 4
        assert pm.timeouts == [4.0]

    def test_a_failed_connection_is_unknown_not_an_error(
        self, make_camera_device_client
    ):
        cam, pm = _camera(make_camera_device_client)
        pm.status = {"error": "persistent mqtt connect timeout"}
        assert asyncio.run(cam.async_get_firmware_info()) is None

    def test_no_connection_at_all_is_unknown(self, make_camera_device_client):
        cam, _pm = _camera(make_camera_device_client)

        async def _none():
            return None

        cam._get_persistent_mqtt = _none
        assert asyncio.run(cam.async_get_firmware_info()) is None


@pytest.mark.parametrize(
    "message",
    [
        None,
        "text",
        {},
        {"payload": None},
        {"payload": {}},
        {"payload": {"verList": None}},
        {"payload": {"verList": []}},
        {"payload": {"verList": ["2.0.1"]}},
        {"payload": {"verList": [{}]}},
        {"payload": {"verList": [{"oldVersion": "2.0.0"}]}},
        {"payload": {"verList": [{"newVersion": 7, "oldVersion": "2.0.0"}]}},
        {"payload": {"verList": [{"newVersion": "  ", "oldVersion": "2.0.0"}]}},
        {"ack": {"code": 500}},
    ],
)
def test_a_reply_without_a_usable_entry_is_unknown(message):
    assert parse_firmware_version_list(message) is None


def test_a_missing_old_version_keeps_the_new_one():
    info = parse_firmware_version_list(
        {"payload": {"verList": [{"newVersion": "2.0.1"}]}}
    )
    assert info == FirmwareInfo(installed_version=None, latest_version="2.0.1")
    # Not known to differ: the app's rule needs both.
    assert info.update_available is False


class TestTheCloudAnswersWhateverTheCameraIsDoing:
    """The ota service answers by itself (measured 2026-11-14), so the request
    goes out for a sleeping battery camera and an offline one alike."""

    def test_a_sleeping_battery_camera_is_asked(self, make_camera_device_client):
        cam, pm = _camera(make_camera_device_client, "A001513", replies=[_reply()])
        assert cam.is_battery_camera and not cam.has_live_session
        assert asyncio.run(cam.async_get_firmware_info()).latest_version == "2.0.1"
        assert len(pm.requests) == 1

    def test_a_camera_the_cloud_reports_offline_is_asked(
        self, make_camera_device_client
    ):
        cam, pm = _camera(make_camera_device_client, replies=[_reply()])
        cam.status.online = False
        cam._cloud_online_explicit = True
        assert asyncio.run(cam.async_get_firmware_info()).latest_version == "2.0.1"
        assert len(pm.requests) == 1
