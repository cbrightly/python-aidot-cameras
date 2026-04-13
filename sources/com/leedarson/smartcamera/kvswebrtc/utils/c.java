package com.leedarson.smartcamera.kvswebrtc.utils;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.webrtc.EglBase;
import org.webrtc.r0;

/* compiled from: EglUtils */
public class c {
    private static EglBase a;
    public static ChangeQuickRedirect changeQuickRedirect;

    public static synchronized EglBase a() {
        synchronized (c.class) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 10104, new Class[0], EglBase.class);
            if (proxy.isSupported) {
                EglBase eglBase = (EglBase) proxy.result;
                return eglBase;
            }
            if (a == null) {
                a = r0.b();
            }
            EglBase eglBase2 = a;
            return eglBase2;
        }
    }

    public static EglBase.Context b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 10105, new Class[0], EglBase.Context.class);
        if (proxy.isSupported) {
            return (EglBase.Context) proxy.result;
        }
        EglBase eglBase = a();
        if (eglBase == null) {
            return null;
        }
        return eglBase.getEglBaseContext();
    }
}
