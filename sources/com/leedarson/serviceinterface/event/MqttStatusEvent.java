package com.leedarson.serviceinterface.event;

public class MqttStatusEvent {
    public boolean connected;

    public MqttStatusEvent(boolean connected2) {
        this.connected = connected2;
    }
}
