package com.leedarson.serviceimpl.ble.manager;

import android.text.TextUtils;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: Utils */
public class g {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static LinkedTreeMap b(String jsonData) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{jsonData}, (Object) null, changeQuickRedirect, true, 6313, new Class[]{String.class}, LinkedTreeMap.class);
        if (proxy.isSupported) {
            return (LinkedTreeMap) proxy.result;
        }
        if (TextUtils.isEmpty(jsonData)) {
            return null;
        }
        return (LinkedTreeMap) new GsonBuilder().disableHtmlEscaping().create().fromJson(jsonData, LinkedTreeMap.class);
    }

    public static byte[] c(String hexString) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{hexString}, (Object) null, changeQuickRedirect, true, 6317, new Class[]{String.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        String hexString2 = hexString.toUpperCase();
        int length = hexString2.length() / 2;
        char[] hexChars = hexString2.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) ((a(hexChars[pos]) << 4) | a(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte a(char c) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Character(c)}, (Object) null, changeQuickRedirect, true, 6318, new Class[]{Character.TYPE}, Byte.TYPE);
        return proxy.isSupported ? ((Byte) proxy.result).byteValue() : (byte) "0123456789ABCDEF".indexOf(c);
    }
}
