"""The setDevAttrReq shape the app's H5 DeviceControl builder sends.

``{devId, parentId: directId || parentId, userId, password, attr}`` on
``iot/v1/c/{directId || id}/device/setDevAttrReq`` (assets/build.zip, main
chunk). We used to omit parentId and always publish on the device id. A record
with neither field must keep exactly the old shape: that is the one proven on
every model.
"""

import asyncio
import json
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from upstream_shapes import account_record, device_record

from aidot_cameras.device_client import CameraDeviceClient


def _send(extra):
    dev = {"id": "cam1", "modelId": "LK.IPC.A001513", "aesKey": [None], **extra}
    user = {"id": "u1", "accessToken": "t"}
    dc = CameraDeviceClient(
        device_record(dev),
        account_record(user),
        raw_device=dev,
        login_info=dict(user),
    )
    sent = []

    async def _cmd(topic, payload, **kw):
        sent.append((topic, json.loads(payload)))
        return True

    dc._mqtt_device_cmd = _cmd
    assert asyncio.run(dc.async_set_device_attribute("LedSwitch", 1)) is True
    assert len(sent) == 1
    return sent[0]


def test_a_record_without_direct_or_parent_id_keeps_the_old_shape():
    topic, msg = _send({})
    assert topic == "iot/v1/c/cam1/device/setDevAttrReq"
    assert "parentId" not in msg["payload"]
    assert msg["payload"]["devId"] == "cam1"
    assert msg["payload"]["attr"] == {"LedSwitch": 1}


def test_an_empty_direct_id_does_not_move_the_topic():
    topic, msg = _send({"directId": "", "parentId": ""})
    assert topic == "iot/v1/c/cam1/device/setDevAttrReq"
    assert "parentId" not in msg["payload"]


def test_parent_id_alone_is_sent_but_the_topic_stays_on_the_device():
    topic, msg = _send({"parentId": "hub9"})
    assert topic == "iot/v1/c/cam1/device/setDevAttrReq"
    assert msg["payload"]["parentId"] == "hub9"
    assert msg["payload"]["devId"] == "cam1"


def test_direct_id_moves_the_topic_and_wins_as_parent_id():
    topic, msg = _send({"directId": "gw7", "parentId": "hub9"})
    assert topic == "iot/v1/c/gw7/device/setDevAttrReq"
    assert msg["payload"]["parentId"] == "gw7"
    assert msg["payload"]["devId"] == "cam1"
    assert msg["id"] == "cam1"
