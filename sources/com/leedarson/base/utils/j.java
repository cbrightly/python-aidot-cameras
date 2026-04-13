package com.leedarson.base.utils;

import android.content.Context;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: DensityUtil */
public class j {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static int a(Context context, float dpValue) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, new Float(dpValue)}, (Object) null, changeQuickRedirect, true, 468, new Class[]{Context.class, Float.TYPE}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
