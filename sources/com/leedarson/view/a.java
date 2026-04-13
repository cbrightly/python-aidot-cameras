package com.leedarson.view;

import android.content.Context;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: DensityUtil */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static int a(Context context, float dpValue) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, new Float(dpValue)}, (Object) null, changeQuickRedirect, true, 11619, new Class[]{Context.class, Float.TYPE}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int b(Context context, float pxValue) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, new Float(pxValue)}, (Object) null, changeQuickRedirect, true, 11620, new Class[]{Context.class, Float.TYPE}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
