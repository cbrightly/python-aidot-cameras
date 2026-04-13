package com.leedarson.serviceimpl.map;

import android.text.TextUtils;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: Util */
public class e {
    private static long a;
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String a(double longitude) {
        Object[] objArr = {new Double(longitude)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 8409, new Class[]{Double.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        long gmt = Math.round(longitude / 15.0d);
        if (gmt >= 0) {
            return "GMT+" + gmt;
        }
        return "GMT" + gmt;
    }

    public static LinkedTreeMap b(String jsonData) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{jsonData}, (Object) null, changeQuickRedirect, true, 8410, new Class[]{String.class}, LinkedTreeMap.class);
        if (proxy.isSupported) {
            return (LinkedTreeMap) proxy.result;
        }
        if (TextUtils.isEmpty(jsonData)) {
            return null;
        }
        return (LinkedTreeMap) new GsonBuilder().disableHtmlEscaping().create().fromJson(jsonData, LinkedTreeMap.class);
    }

    public static boolean c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 8411, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        long time = System.currentTimeMillis();
        if (time - a < 800) {
            a = time;
            return true;
        }
        a = time;
        return false;
    }
}
