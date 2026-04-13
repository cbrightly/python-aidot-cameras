package com.telink.bluetooth.event;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: MeshEvent */
public class e extends a<Integer> {
    public static ChangeQuickRedirect changeQuickRedirect;

    public e(Object sender, String type, Integer args) {
        super(sender, type, args);
    }

    public static e d(Object sender, String type, Integer args) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{sender, type, args}, (Object) null, changeQuickRedirect, true, 13500, new Class[]{Object.class, String.class, Integer.class}, e.class);
        return proxy.isSupported ? (e) proxy.result : new e(sender, type, args);
    }
}
