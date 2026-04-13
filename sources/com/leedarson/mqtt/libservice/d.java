package com.leedarson.mqtt.libservice;

import com.meituan.robust.ChangeQuickRedirect;
import org.eclipse.paho.client.mqttv3.b;
import org.eclipse.paho.client.mqttv3.l;

/* compiled from: MqttDeliveryTokenAndroid */
public class d extends f implements org.eclipse.paho.client.mqttv3.d {
    public static ChangeQuickRedirect changeQuickRedirect;
    private l j;

    d(MqttAndroidClient client, Object userContext, b listener, l message) {
        super(client, userContext, listener);
        this.j = message;
    }

    public l a() {
        return this.j;
    }
}
