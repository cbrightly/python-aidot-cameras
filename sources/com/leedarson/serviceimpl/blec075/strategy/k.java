package com.leedarson.serviceimpl.blec075.strategy;

import com.leedarson.serviceimpl.ble.manager.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashMap;

/* compiled from: RetryMaker */
public class k {
    private static k a;
    public static ChangeQuickRedirect changeQuickRedirect;
    private HashMap<String, e.f> b = new HashMap<>();

    public static k b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 6585, new Class[0], k.class);
        if (proxy.isSupported) {
            return (k) proxy.result;
        }
        if (a == null) {
            a = new k();
        }
        return a;
    }

    public void c(String devId, e.f msgWrap) {
        Class[] clsArr = {String.class, e.f.class};
        if (!PatchProxy.proxy(new Object[]{devId, msgWrap}, this, changeQuickRedirect, false, 6586, clsArr, Void.TYPE).isSupported) {
            this.b.put(devId, msgWrap);
        }
    }

    public boolean a(String devId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{devId}, this, changeQuickRedirect, false, 6587, new Class[]{String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : this.b.containsKey(devId);
    }

    public void e(String devId, e.f fVar) {
        Class[] clsArr = {String.class, e.f.class};
        if (!PatchProxy.proxy(new Object[]{devId, fVar}, this, changeQuickRedirect, false, 6588, clsArr, Void.TYPE).isSupported) {
            this.b.remove(devId);
        }
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6589, new Class[0], Void.TYPE).isSupported) {
            this.b.clear();
        }
    }
}
