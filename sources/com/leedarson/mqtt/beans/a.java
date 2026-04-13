package com.leedarson.mqtt.beans;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/* compiled from: DateUtil */
public class a {
    private static ThreadLocal<DateFormat> a = new ThreadLocal<>();
    public static ChangeQuickRedirect changeQuickRedirect;

    public static DateFormat a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 1508, new Class[0], DateFormat.class);
        if (proxy.isSupported) {
            return (DateFormat) proxy.result;
        }
        DateFormat df = a.get();
        if (df != null) {
            return df;
        }
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        a.set(df2);
        return df2;
    }
}
