package com.leedarson.newui.view.radar;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import timber.log.a;

/* compiled from: RadarLog */
public class g {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void a(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, (Object) null, changeQuickRedirect, true, 5431, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.g("RadarLog").m(msg, new Object[0]);
        }
    }

    public static void c(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, (Object) null, changeQuickRedirect, true, 5432, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.g("RadarLog").c(msg, new Object[0]);
        }
    }

    public static void d(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, (Object) null, changeQuickRedirect, true, 5433, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.g("RadarLog").m(msg, new Object[0]);
            com.leedarson.log.elk.a.y(g.class).p(msg).b(8).o("info").e("LiveRadarPointEvent").a().b();
        }
    }

    public static void e(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, (Object) null, changeQuickRedirect, true, 5434, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g = a.g("RadarLog");
            g.m("sdcard radar:" + msg, new Object[0]);
        }
    }

    public static void b(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, (Object) null, changeQuickRedirect, true, 5435, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g = a.g("RadarLog");
            g.m("cloudplayback radar:" + msg, new Object[0]);
        }
    }
}
