"""Sound detection, WiFi and SD-card properties read over devActionReq.

The camera answers these `get` actions with real data. The behaviour worth
pinning is what happens when it does NOT: a camera that cannot answer must read
as unknown, never as a set of switched-off features.
"""

import asyncio
import json
import logging

from aidot_cameras.camera.controls import _CameraControlsMixin


class _Cam:
    def __init__(self, out=None, set_ok=True):
        self.device_id = "dev1"
        self._out = out
        self._set_ok = set_ok
        self.queries = []
        self.actions = []

    async def async_query_device_action(self, action, params=None, *, timeout=8.0):
        self.queries.append(action)
        return self._out

    async def async_trigger_device_action(
        self, action, params, *, timeout=4.0, expect_ack=True
    ):
        self.actions.append((action, params))
        return self._set_ok

    async_get_sound_detection = _CameraControlsMixin.async_get_sound_detection
    async_set_sound_detection = _CameraControlsMixin.async_set_sound_detection
    async_get_wifi_info = _CameraControlsMixin.async_get_wifi_info
    async_get_sd_card_info = _CameraControlsMixin.async_get_sd_card_info


_SOUND = [{"sound_enable": 0}, {"glass_Break": 1}, {"smoke_T3": 0}]


class TestSoundDetection:
    def test_it_flattens_the_camera_s_list_of_single_key_dicts(self):
        cam = _Cam(_SOUND)
        assert asyncio.run(cam.async_get_sound_detection()) == {
            "sound_enable": False,
            "glass_Break": True,
            "smoke_T3": False,
        }

    def test_no_reply_is_unknown_not_all_off(self):
        """A null `out` must not be reported as a set of disabled detectors."""
        assert asyncio.run(_Cam(None).async_get_sound_detection()) is None

    def test_a_write_echoes_the_camera_s_own_structure(self):
        cam = _Cam(_SOUND)
        assert asyncio.run(cam.async_set_sound_detection("smoke_T3", True)) is True
        action, payload = cam.actions[0]
        assert action == "soundAlgorithmSet"
        assert payload == [{"sound_enable": 0}, {"glass_Break": 1}, {"smoke_T3": 1}]

    def test_it_leaves_the_other_flags_untouched(self):
        cam = _Cam(_SOUND)
        asyncio.run(cam.async_set_sound_detection("sound_enable", True))
        assert cam.actions[0][1] == [
            {"sound_enable": 1},
            {"glass_Break": 1},
            {"smoke_T3": 0},
        ]

    def test_it_refuses_to_write_a_key_the_camera_does_not_report(self):
        cam = _Cam(_SOUND)
        assert asyncio.run(cam.async_set_sound_detection("nope", True)) is False
        assert cam.actions == []

    def test_it_refuses_to_write_when_the_camera_did_not_answer(self):
        """Without a current list there is nothing to echo - guessing a payload
        here is how an unrelated detector gets switched off."""
        cam = _Cam(None)
        assert asyncio.run(cam.async_set_sound_detection("smoke_T3", True)) is False
        assert cam.actions == []


class TestWifiInfo:
    def test_it_names_only_the_confirmed_fields(self):
        cam = _Cam(["Brightly", 63, 1984564238])
        assert asyncio.run(cam.async_get_wifi_info()) == {
            "ssid": "Brightly",
            "rssi": 63,
        }

    def test_no_reply_is_none(self):
        assert asyncio.run(_Cam(None).async_get_wifi_info()) is None


class TestSdCardInfo:
    def test_absent_card(self):
        cam = _Cam([False, 0, 0, 0, 0])
        got = asyncio.run(cam.async_get_sd_card_info())
        assert got["present"] is False and got["total"] == 0 and got["used"] == 0

    def test_present_card_passes_numbers_through_unconverted(self):
        cam = _Cam([True, 29838, 28848, 3, 0])
        got = asyncio.run(cam.async_get_sd_card_info())
        assert got["present"] is True
        assert got["total"] == 29838 and got["used"] == 28848

    def test_the_fourth_field_is_not_reported_as_free_space(self):
        """Measured on a real camera: total 29838, used 28848, fourth field 3.
        That is not a remainder (990), so naming it "free" would report 3 units
        left on a nearly-full 30 GB card. It stays raw until confirmed."""
        cam = _Cam([True, 29838, 28848, 3, 0])
        got = asyncio.run(cam.async_get_sd_card_info())
        assert "free" not in got
        assert got["raw"] == [True, 29838, 28848, 3, 0]

    def test_no_reply_is_none(self):
        assert asyncio.run(_Cam(None).async_get_sd_card_info()) is None


class _Pm:
    """A persistent MQTT connection that answers with scripted replies."""

    def __init__(self, replies):
        self.replies = replies
        self.requests = []

    async def request(self, *, publish_items, subscribe_topics, timeout):
        self.requests.append(publish_items)
        seq = json.loads(publish_items[0][1])["seq"]
        msgs = [
            (topic, json.dumps(body(seq) if callable(body) else body))
            for topic, body in self.replies
        ]
        return msgs, {}


def _querying_camera(monkeypatch, make_camera_device_client, replies):
    """A real client whose query rides a fake persistent connection; a fresh
    MQTT session would fail the test."""
    from aidot_cameras.camera import client as client_mod

    cam = make_camera_device_client("A001064")
    pm = _Pm(replies)

    async def _creds():
        return "u", "p", "app-user-1"

    async def _url():
        return "mqtts://broker.invalid:8883"

    async def _get_pm():
        return pm

    async def _fresh_session(*a, **k):
        raise AssertionError("opened a second session on the account's client id")

    cam._mqtt_credentials = _creds
    cam._async_get_mqtt_url = _url
    cam._resolve_persistent_mqtt = lambda: True
    cam._get_persistent_mqtt = _get_pm
    monkeypatch.setattr(client_mod, "_mqtt_session_with_status", _fresh_session)
    return cam, pm


def _reply(dev, out, seq=None, action="getWifiInfo"):
    def build(our_seq):
        msg = {"payload": {"devId": dev, "action": action, "out": out}}
        if seq == "ours":
            msg["seq"] = our_seq
        elif seq is not None:
            msg["seq"] = seq
        return msg

    return ("iot/v1/c/user-1/device/devActionResp", build)


class TestItDoesNotFightTheIntegrationsConnection:
    """The query must reuse the persistent MQTT connection when there is one.

    Opening a fresh session uses the same `mqttClientId` the long-lived
    connection already holds, and a broker evicts the older session on a
    duplicate client id. Inside Home Assistant that means the query and the
    integration knock each other off and the reply never arrives -- which looks
    exactly like "this camera does not support the action".
    """

    def test_the_query_prefers_the_persistent_connection(
        self, monkeypatch, make_camera_device_client
    ):
        cam, pm = _querying_camera(monkeypatch, make_camera_device_client, [])
        pm.replies = [_reply(cam.device_id, {"ssid": "x"}, seq="ours")]
        got = asyncio.run(cam.async_query_device_action("getWifiInfo"))
        assert got == {"ssid": "x"}
        assert len(pm.requests) == 1

    def test_a_missing_reply_is_logged_not_silent(
        self, monkeypatch, make_camera_device_client, caplog
    ):
        """INFO, not debug: a silent None is what hid this failure."""
        cam, _pm = _querying_camera(monkeypatch, make_camera_device_client, [])
        with caplog.at_level(logging.DEBUG, logger="aidot_cameras.camera.client"):
            assert asyncio.run(cam.async_query_device_action("getWifiInfo")) is None
        hits = [r for r in caplog.records if "no reply from" in r.getMessage()]
        assert [r.levelno for r in hits] == [logging.INFO]


class TestRepliesAreMatchedToTheRightCamera:
    """The persistent connection carries the whole account's traffic.

    Observed live: all four cameras reported identical SD figures, because the
    matcher keyed on the action alone and each camera accepted whichever reply
    arrived first.
    """

    def test_it_filters_on_the_device_id(self, monkeypatch, make_camera_device_client):
        cam, pm = _querying_camera(monkeypatch, make_camera_device_client, [])
        pm.replies = [
            _reply("another-camera", {"sd": "theirs"}, seq="ours"),
            _reply(cam.device_id, {"sd": "ours"}),
        ]
        assert asyncio.run(cam.async_query_device_action("getWifiInfo")) == {
            "sd": "ours"
        }
        pm.replies = [_reply("another-camera", {"sd": "theirs"}, seq="ours")]
        assert asyncio.run(cam.async_query_device_action("getWifiInfo")) is None

    def test_seq_is_a_preference_not_a_filter(
        self, monkeypatch, make_camera_device_client
    ):
        """Requiring the seq discards every reply from a camera that does not
        echo it -- seen live as "no reply (4 messages seen)" on a camera that
        had in fact answered four times."""
        cam, pm = _querying_camera(monkeypatch, make_camera_device_client, [])
        dev = cam.device_id
        pm.replies = [_reply(dev, {"n": 1}, seq="someone-else")]
        assert asyncio.run(cam.async_query_device_action("getWifiInfo")) == {"n": 1}
        pm.replies = [
            _reply(dev, {"n": 1}, seq="someone-else"),
            _reply(dev, {"n": 2}, seq="ours"),
        ]
        assert asyncio.run(cam.async_query_device_action("getWifiInfo")) == {"n": 2}
