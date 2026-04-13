package com.leedarson.serviceimpl.reporters;

import com.leedarson.log.elk.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: MatterDeviceControllerReporter */
public class j extends h {
    public static ChangeQuickRedirect changeQuickRedirect;

    public j(String traceId, String deviceInfo) {
        super(traceId, deviceInfo);
    }

    public a a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8555, new Class[0], a.class);
        return proxy.isSupported ? (a) proxy.result : a.y(this).t("LdsMatter").e("DeviceController").b(8).o("info");
    }
}
