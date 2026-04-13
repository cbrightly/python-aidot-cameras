package com.leedarson.mqtt.listerners;

import com.meituan.robust.ChangeQuickRedirect;
import org.eclipse.paho.client.mqttv3.b;
import org.eclipse.paho.client.mqttv3.f;

/* compiled from: MqttActionListernerWrap */
public class a implements b {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int a = 0;
    private String b = "";

    public a(int taskId, String clientId) {
        this.a = taskId;
        this.b = clientId;
    }

    public a(int taskId) {
        this.a = taskId;
    }

    public void onSuccess(f asyncActionToken) {
    }

    public void onFailure(f asyncActionToken, Throwable exception) {
    }

    public int a() {
        return this.a;
    }
}
