package com.leedarson.serviceimpl.map;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import timber.log.a;

/* compiled from: RadarMapLog */
public class c {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void a(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, (Object) null, changeQuickRedirect, true, 8295, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.g("RadarMapLog").m(msg, new Object[0]);
        }
    }
}
