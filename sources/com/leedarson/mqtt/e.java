package com.leedarson.mqtt;

import com.leedarson.mqtt.logs.MqttLogStepBean;
import com.leedarson.mqtt.logs.a;

/* compiled from: lambda */
public final /* synthetic */ class e implements io.reactivex.functions.e {
    public final /* synthetic */ MqttLogStepBean c;
    public final /* synthetic */ a d;

    public /* synthetic */ e(MqttLogStepBean mqttLogStepBean, a aVar) {
        this.c = mqttLogStepBean;
        this.d = aVar;
    }

    public final void accept(Object obj) {
        l.L(this.c, this.d, (Throwable) obj);
    }
}
