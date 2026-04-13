package com.telink.bluetooth.light;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: AdvertiseFilterChain */
public final class c {
    private static final c a;
    public static ChangeQuickRedirect changeQuickRedirect;
    private String b;
    private List<b> c = new ArrayList();

    static {
        c cVar = new c("Telink default filter chain");
        a = cVar;
        cVar.a(d.b());
    }

    private c(String name) {
        this.b = name;
    }

    public static c b() {
        return a;
    }

    public c a(b filter) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filter}, this, changeQuickRedirect, false, 13510, new Class[]{b.class}, c.class);
        if (proxy.isSupported) {
            return (c) proxy.result;
        }
        synchronized (this) {
            this.c.add(filter);
        }
        return this;
    }

    public Iterator<b> c() {
        Iterator<b> it;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13513, new Class[0], Iterator.class);
        if (proxy.isSupported) {
            return (Iterator) proxy.result;
        }
        synchronized (this) {
            it = this.c.iterator();
        }
        return it;
    }
}
