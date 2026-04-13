package com.leedarson.serviceimpl.camera;

import android.content.res.Resources;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: DensityUtil */
public class g {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static int a(float dpValue) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Float(dpValue)}, (Object) null, changeQuickRedirect, true, 7371, new Class[]{Float.TYPE}, Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : (int) ((Resources.getSystem().getDisplayMetrics().density * dpValue) + 0.5f);
    }
}
