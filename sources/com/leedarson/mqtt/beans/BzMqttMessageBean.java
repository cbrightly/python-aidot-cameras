package com.leedarson.mqtt.beans;

import org.json.JSONObject;

public class BzMqttMessageBean {
    public String method = "";
    public JSONObject payload;
    public int seq = 0;
    public String service = "";
    public String srcAddr = "";
    public String tid = "";
    public long tst = 0;
}
