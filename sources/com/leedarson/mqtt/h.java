package com.leedarson.mqtt;

import com.leedarson.mqtt.beans.ApiMqttConfigBean;
import com.leedarson.mqtt.logs.MqttLogStepBean;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class h implements e {
    public final /* synthetic */ l c;
    public final /* synthetic */ MqttLogStepBean d;
    public final /* synthetic */ String f;

    public /* synthetic */ h(l lVar, MqttLogStepBean mqttLogStepBean, String str) {
        this.c = lVar;
        this.d = mqttLogStepBean;
        this.f = str;
    }

    public final void accept(Object obj) {
        this.c.K(this.d, this.f, (ApiMqttConfigBean) obj);
    }
}
