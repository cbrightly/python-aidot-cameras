package com.leedarson.mqtt;

import com.leedarson.mqtt.beans.MqttServiceRequestTaskBean;
import com.leedarson.mqtt.l;
import kotlin.n;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ MqttServiceRequestTaskBean c;
    public final /* synthetic */ n d;
    public final /* synthetic */ String f;

    public /* synthetic */ a(MqttServiceRequestTaskBean mqttServiceRequestTaskBean, n nVar, String str) {
        this.c = mqttServiceRequestTaskBean;
        this.d = nVar;
        this.f = str;
    }

    public final void run() {
        l.f.c(this.c, this.d, this.f);
    }
}
