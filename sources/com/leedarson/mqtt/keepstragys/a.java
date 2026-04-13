package com.leedarson.mqtt.keepstragys;

import com.leedarson.mqtt.logs.b;
import com.leedarson.mqtt.n;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: MqttKeepConnectKeepHealthyStragy */
public abstract class a implements n.a {
    public static ChangeQuickRedirect changeQuickRedirect;
    private long a = 10000;
    private int b = 0;

    public abstract void b();

    public void a(long j) {
        if (!PatchProxy.proxy(new Object[]{new Long(j)}, this, changeQuickRedirect, false, 1518, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            int i = this.b + 1;
            this.b = i;
            if (i > 10) {
                try {
                    b();
                    this.b = 0;
                } catch (Exception e) {
                    e.printStackTrace();
                    b.a("MqttKeepConnectKeepHealthyStragy onActualCheck Exception=" + e.toString());
                }
            }
        }
    }
}
