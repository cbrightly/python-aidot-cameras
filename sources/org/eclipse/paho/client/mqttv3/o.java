package org.eclipse.paho.client.mqttv3;

import org.eclipse.paho.client.mqttv3.internal.w;
import org.eclipse.paho.client.mqttv3.internal.wire.u;

/* compiled from: MqttToken */
public class o implements f {
    public w a = null;

    public o() {
    }

    public o(String logContext) {
        this.a = new w(logContext);
    }

    public MqttException g() {
        return this.a.c();
    }

    public boolean h() {
        return this.a.k();
    }

    public void i(b listener) {
        this.a.q(listener);
    }

    public b d() {
        return this.a.a();
    }

    public c f() {
        return this.a.b();
    }

    public Object e() {
        return this.a.i();
    }

    public void j(Object userContext) {
        this.a.y(userContext);
    }

    public int c() {
        return this.a.f();
    }

    public u b() {
        return this.a.g();
    }
}
