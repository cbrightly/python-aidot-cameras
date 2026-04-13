package com.leedarson.mqtt.listerners;

import com.meituan.robust.ChangeQuickRedirect;
import org.eclipse.paho.client.mqttv3.d;
import org.eclipse.paho.client.mqttv3.h;
import org.eclipse.paho.client.mqttv3.l;

/* compiled from: MqttCallbackExtendedWrap */
public class b implements h {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String a;

    public b(String clientId) {
        this.a = clientId;
    }

    public void connectComplete(boolean reconnect, String serverURI) {
    }

    public void connectionLost(Throwable cause) {
    }

    public void messageArrived(String topic, l message) {
    }

    public void deliveryComplete(d token) {
    }
}
