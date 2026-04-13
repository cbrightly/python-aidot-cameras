package com.telink.bluetooth.event;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.bluetooth.light.ErrorReportInfo;

/* compiled from: ErrorReportEvent */
public class c extends a<ErrorReportInfo> {
    public static ChangeQuickRedirect changeQuickRedirect;

    public c(Object sender, String type, ErrorReportInfo args) {
        super(sender, type, args);
    }

    public static c d(Object sender, String type, ErrorReportInfo args) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{sender, type, args}, (Object) null, changeQuickRedirect, true, 13498, new Class[]{Object.class, String.class, ErrorReportInfo.class}, c.class);
        return proxy.isSupported ? (c) proxy.result : new c(sender, type, args);
    }
}
