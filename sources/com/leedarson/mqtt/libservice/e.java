package com.leedarson.mqtt.libservice;

import android.os.Binder;
import com.meituan.robust.ChangeQuickRedirect;

/* compiled from: MqttServiceBinder */
public class e extends Binder {
    public static ChangeQuickRedirect changeQuickRedirect;
    private MqttService a;
    private String b;

    e(MqttService mqttService) {
        this.a = mqttService;
    }

    public MqttService a() {
        return this.a;
    }

    /* access modifiers changed from: package-private */
    public void b(String activityToken) {
        this.b = activityToken;
    }
}
