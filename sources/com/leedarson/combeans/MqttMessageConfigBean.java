package com.leedarson.combeans;

public class MqttMessageConfigBean {
    public boolean isSupportSimpleVersion = false;
    public boolean onlyPubAck = false;
    public int qos = 1;
    public String seq = "";
    public long timeOutLimitOfMs = 10000;
}
