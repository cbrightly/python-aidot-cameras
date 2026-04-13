package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;

public class MqttMessageArrivedEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String message;
    private String topic;

    public MqttMessageArrivedEvent(String topic2, String message2) {
        this.topic = topic2;
        this.message = message2;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic2) {
        this.topic = topic2;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message2) {
        this.message = message2;
    }
}
