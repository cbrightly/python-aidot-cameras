package com.leedarson.mqtt.beans;

import org.json.JSONObject;

public class MqttWebPubInvokeParamsBean {
    public String key = "";
    public JSONObject message;
    public int timeout = 10000;
    public String topic = "";
}
