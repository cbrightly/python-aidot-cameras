package com.leedarson.serviceimpl.http;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: Utils */
public class b {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String a(String str) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, (Object) null, changeQuickRedirect, true, 7958, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            int chr1 = str.charAt(i);
            if (chr1 < 19968 || chr1 > 171941) {
                result = result + str.charAt(i);
            } else {
                result = result + "\\u" + Integer.toHexString(chr1);
            }
        }
        return result;
    }
}
