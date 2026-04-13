package com.leedarson.mqtt.libservice;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.b;
import org.eclipse.paho.client.mqttv3.c;
import org.eclipse.paho.client.mqttv3.internal.wire.u;

/* compiled from: MqttTokenAndroid */
public class f implements org.eclipse.paho.client.mqttv3.f {
    public static ChangeQuickRedirect changeQuickRedirect;
    private b a;
    private volatile boolean b;
    private volatile MqttException c;
    private Object d;
    private MqttAndroidClient e;
    private Object f;
    private String[] g;
    private org.eclipse.paho.client.mqttv3.f h;
    private MqttException i;

    f(MqttAndroidClient client, Object userContext, b listener) {
        this(client, userContext, listener, (String[]) null);
    }

    f(MqttAndroidClient client, Object userContext, b listener, String[] topics) {
        this.d = new Object();
        this.e = client;
        this.f = userContext;
        this.a = listener;
        this.g = topics;
    }

    /* access modifiers changed from: package-private */
    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1686, new Class[0], Void.TYPE).isSupported) {
            synchronized (this.d) {
                this.b = true;
                this.d.notifyAll();
                b bVar = this.a;
                if (bVar != null) {
                    bVar.onSuccess(this);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void h(Throwable exception) {
        if (!PatchProxy.proxy(new Object[]{exception}, this, changeQuickRedirect, false, 1687, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            synchronized (this.d) {
                this.b = true;
                if (exception instanceof MqttException) {
                    this.i = (MqttException) exception;
                } else {
                    this.i = new MqttException(exception);
                }
                this.d.notifyAll();
                if (exception instanceof MqttException) {
                    this.c = (MqttException) exception;
                }
                b bVar = this.a;
                if (bVar != null) {
                    bVar.onFailure(this, exception);
                }
            }
        }
    }

    public c f() {
        return this.e;
    }

    public b d() {
        return this.a;
    }

    public Object e() {
        return this.f;
    }

    /* access modifiers changed from: package-private */
    public void i(org.eclipse.paho.client.mqttv3.f delegate) {
        this.h = delegate;
    }

    public int c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1688, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        org.eclipse.paho.client.mqttv3.f fVar = this.h;
        if (fVar != null) {
            return fVar.c();
        }
        return 0;
    }

    public u b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1689, new Class[0], u.class);
        return proxy.isSupported ? (u) proxy.result : this.h.b();
    }
}
