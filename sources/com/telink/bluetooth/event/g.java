package com.telink.bluetooth.event;

import android.os.IBinder;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: ServiceEvent */
public class g extends a<IBinder> {
    public static ChangeQuickRedirect changeQuickRedirect;

    public g(Object sender, String type, IBinder args) {
        super(sender, type, args);
    }

    public static g d(Object sender, String type, IBinder args) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{sender, type, args}, (Object) null, changeQuickRedirect, true, 13507, new Class[]{Object.class, String.class, IBinder.class}, g.class);
        return proxy.isSupported ? (g) proxy.result : new g(sender, type, args);
    }
}
