package com.leedarson.smartcamera.utils;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/* compiled from: StreamCacheUtils */
public class g {
    private static g a;
    private static BlockingDeque<h> b = new LinkedBlockingDeque(200);
    private static BlockingDeque<byte[]> c = new LinkedBlockingDeque(1000);
    public static ChangeQuickRedirect changeQuickRedirect;

    private g() {
    }

    public static g d() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 10509, new Class[0], g.class);
        if (proxy.isSupported) {
            return (g) proxy.result;
        }
        if (a == null) {
            a = new g();
        }
        return a;
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10510, new Class[0], Void.TYPE).isSupported) {
            c.clear();
        }
    }

    public void h(byte[] data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 10511, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            c.put(data);
        }
    }

    public byte[] e() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10512, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        return c.take();
    }

    public int f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10513, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return c.size();
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10514, new Class[0], Void.TYPE).isSupported) {
            b.clear();
        }
    }

    public void g(h data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 10515, new Class[]{h.class}, Void.TYPE).isSupported) {
            b.put(data);
        }
    }

    public h c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10516, new Class[0], h.class);
        if (proxy.isSupported) {
            return (h) proxy.result;
        }
        return b.take();
    }
}
