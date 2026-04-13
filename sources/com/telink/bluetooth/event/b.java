package com.telink.bluetooth.event;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.bluetooth.light.DeviceInfo;

/* compiled from: DeviceEvent */
public class b extends a<DeviceInfo> {
    public static ChangeQuickRedirect changeQuickRedirect;

    public b(Object sender, String type, DeviceInfo args) {
        super(sender, type, args);
    }

    public static b d(Object sender, String type, DeviceInfo args) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{sender, type, args}, (Object) null, changeQuickRedirect, true, 13497, new Class[]{Object.class, String.class, DeviceInfo.class}, b.class);
        return proxy.isSupported ? (b) proxy.result : new b(sender, type, args);
    }
}
