package com.leedarson.mqtt.beans;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.eclipse.paho.client.mqttv3.l;

public class MqttMessageWrapBean extends MqttServiceRequestTaskBean {
    public static ChangeQuickRedirect changeQuickRedirect;

    public void setMqttMessage(l mqttMessage) {
        if (!PatchProxy.proxy(new Object[]{mqttMessage}, this, changeQuickRedirect, false, 1509, new Class[]{l.class}, Void.TYPE).isSupported) {
            this.mqttMessage = mqttMessage;
            this.messageId = mqttMessage.b();
            this.createTime = System.currentTimeMillis();
        }
    }
}
