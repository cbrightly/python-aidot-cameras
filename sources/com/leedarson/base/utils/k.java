package com.leedarson.base.utils;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: FastclickUtils */
public class k {
    private static long a;
    public static ChangeQuickRedirect changeQuickRedirect;

    public static boolean a(int i) {
        Object[] objArr = {new Integer(i)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 473, new Class[]{Integer.TYPE}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        boolean flag = true;
        long curClickTime = System.currentTimeMillis();
        if (curClickTime - a > 1000) {
            flag = false;
        }
        a = curClickTime;
        return flag;
    }
}
