package com.leedarson.utils;

import android.content.Context;
import android.util.TypedValue;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: ScreenUtils */
public class n {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static int b(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 11532, new Class[]{Context.class}, Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int a(Context context, float dp) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, new Float(dp)}, (Object) null, changeQuickRedirect, true, 11534, new Class[]{Context.class, Float.TYPE}, Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : (int) (TypedValue.applyDimension(1, dp, context.getResources().getDisplayMetrics()) + 0.5f);
    }

    public static boolean c(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 11535, new Class[]{Context.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            if (context.getResources().getConfiguration().orientation == 2) {
                return true;
            }
            return context.getResources().getConfiguration().orientation == 1 ? false : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
