package com.telink.bluetooth.event;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.bluetooth.light.DeviceInfo;

/* compiled from: LeScanEvent */
public class d extends a<DeviceInfo> {
    public static ChangeQuickRedirect changeQuickRedirect;

    public d(Object sender, String type, DeviceInfo args) {
        super(sender, type, args);
    }

    public static d d(Object sender, String type, DeviceInfo args) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{sender, type, args}, (Object) null, changeQuickRedirect, true, 13499, new Class[]{Object.class, String.class, DeviceInfo.class}, d.class);
        return proxy.isSupported ? (d) proxy.result : new d(sender, type, args);
    }
}
