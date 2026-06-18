#!/usr/bin/env python3
"""Faux cloud-side MQTT responder.

Connects to HA's Mosquitto and plays the role AiDot's cloud used to: answer the
`getIceConfigReq` requests (from both the bridge and the cameras) with OUR coturn,
and ACK the other cloud-mediated bits. Camera<->bridge signaling (webrtcReq/Resp,
iceCandidateReq) is peer-to-peer through the broker and needs no responder.

SCAFFOLD. Topic shapes from the blackhole-feasibility / sdes-signaling-protocol
notes:
  request : iot/v1/s/{userId}/IPC/getIceConfigReq
  response: iot/v1/c/{userId}/IPC/getIceConfigResp
Envelope: {method, service, devId, srcAddr:"0.{userId}", seq, tst, payload}

Run:  FAUX_MQTT_HOST=192.168.1.162 python mqtt_responder.py
"""
import json
import os
import re
import time

import paho.mqtt.client as mqtt

HOST = os.environ.get("FAUX_MQTT_HOST", "192.168.1.162")
PORT = int(os.environ.get("FAUX_MQTT_PORT", "1883"))
USER = os.environ.get("FAUX_MQTT_USER", "aidot")
PASS = os.environ.get("FAUX_MQTT_PASS", "aidot")
TURN_HOST = os.environ.get("FAUX_TURN_HOST", HOST)
TURN_USER = os.environ.get("FAUX_TURN_USER", "aidot")
TURN_PASS = os.environ.get("FAUX_TURN_PASS", "aidot")

REQ_RE = re.compile(r"iot/v1/s/(?P<uid>[^/]+)/IPC/getIceConfigReq")


def _ice_payload():
    # mirror the cloud's shape: list of [stun, turn] pairs (the bridge accepts
    # both the W3C iceServers form and this paired form — see client.py parser).
    turn = f"turn:{TURN_HOST}:3478?transport=udp"
    stun = f"stun:{TURN_HOST}:3478"
    return {"iceServers": [{"urls": [stun, turn], "username": TURN_USER,
                            "credential": TURN_PASS}]}


def on_connect(client, userdata, flags, rc, *a):
    print(f"connected rc={rc}; subscribing to getIceConfigReq")
    client.subscribe("iot/v1/s/+/IPC/getIceConfigReq")
    client.subscribe("iot/v1/s/+/IPCAM/lowPowerActiveStateReq")


def on_message(client, userdata, msg):
    m = REQ_RE.match(msg.topic)
    try:
        req = json.loads(msg.payload.decode())
    except Exception:
        req = {}
    if m:
        uid = m.group("uid")
        resp_topic = f"iot/v1/c/{uid}/IPC/getIceConfigResp"
        resp = {"method": "getIceConfigResp", "service": "IPC",
                "devId": req.get("devId", ""), "srcAddr": f"0.{uid}",
                "seq": req.get("seq", ""), "tst": int(time.time() * 1000),
                "payload": _ice_payload()}
        client.publish(resp_topic, json.dumps(resp))
        print(f"answered getIceConfigReq for {uid} -> coturn {TURN_HOST}")
    elif "lowPowerActiveStateReq" in msg.topic:
        uid = msg.topic.split("/")[3]
        client.publish(f"iot/v1/c/{uid}/IPCAM/lowPowerActiveStateResp",
                       json.dumps({"code": 0, "tst": int(time.time() * 1000)}))
        print(f"ACKed lowPowerActiveState for {uid}")


def main():
    c = mqtt.Client(client_id="faux-cloud-responder", protocol=mqtt.MQTTv311)
    if USER:
        c.username_pw_set(USER, PASS)
    c.on_connect = on_connect
    c.on_message = on_message
    c.connect(HOST, PORT, keepalive=30)
    print(f"faux responder on {HOST}:{PORT}; coturn={TURN_HOST}")
    c.loop_forever()


if __name__ == "__main__":
    main()
