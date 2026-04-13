package com.leedarson.serviceimpl.ble.utils;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: MacUtils */
public class b {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String a(String s) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{s}, (Object) null, changeQuickRedirect, true, 6325, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        char[] result = new char[17];
        char[] chars = s.toCharArray();
        if (chars.length < 12) {
            return "";
        }
        result[0] = chars[10];
        result[1] = chars[11];
        result[2] = ':';
        result[3] = chars[8];
        result[4] = chars[9];
        result[5] = ':';
        result[6] = chars[6];
        result[7] = chars[7];
        result[8] = ':';
        result[9] = chars[4];
        result[10] = chars[5];
        result[11] = ':';
        result[12] = chars[2];
        result[13] = chars[3];
        result[14] = ':';
        result[15] = chars[0];
        result[16] = chars[1];
        return String.valueOf(result);
    }

    public static String b(String s) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{s}, (Object) null, changeQuickRedirect, true, 6326, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        char[] chars = s.toCharArray();
        return String.valueOf(new char[]{chars[15], chars[16], chars[12], chars[13], chars[9], chars[10], chars[6], chars[7], chars[3], chars[4], chars[0], chars[1]});
    }
}
