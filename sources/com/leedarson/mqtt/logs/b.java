package com.leedarson.mqtt.logs;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import timber.log.a;

/* compiled from: MqttLog */
public class b {
    private static String a = "MqttLog";
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void a(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, (Object) null, changeQuickRedirect, true, 1705, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.g(a).c(msg, new Object[0]);
        }
    }

    public static void b(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, (Object) null, changeQuickRedirect, true, 1706, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g = a.g(a);
            g.h(msg + " " + a, new Object[0]);
        }
    }
}
