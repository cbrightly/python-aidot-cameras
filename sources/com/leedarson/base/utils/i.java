package com.leedarson.base.utils;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: DateUtils */
public class i {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String a(long time, String format) {
        Object[] objArr = {new Long(time), format};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 467, new Class[]{Long.TYPE, String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return new SimpleDateFormat(format).format(new Date(time));
    }
}
