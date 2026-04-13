package com.telink.ble.mesh.core.message;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class Priority {
    public static ChangeQuickRedirect changeQuickRedirect;
    public int a;
    public int b;

    public Priority(int type, int order) {
        this.a = type;
        this.b = order;
    }

    public static Priority a(int type, int order) {
        Object[] objArr = {new Integer(type), new Integer(order)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12441, new Class[]{cls, cls}, Priority.class);
        return proxy.isSupported ? (Priority) proxy.result : new Priority(type, order);
    }
}
