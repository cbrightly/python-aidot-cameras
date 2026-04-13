package com.leedarson.serviceimpl.udp.manager;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: Utils */
public class c {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String a(byte[] bytes) {
        int a2;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bytes}, (Object) null, changeQuickRedirect, true, 9152, new Class[]{byte[].class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        char[] buf = new char[(bytes.length * 2)];
        int index = 0;
        for (byte b : bytes) {
            if (b < 0) {
                a2 = b + 256;
            } else {
                a2 = b;
            }
            int index2 = index + 1;
            char[] cArr = a;
            buf[index] = cArr[a2 / 16];
            index = index2 + 1;
            buf[index2] = cArr[a2 % 16];
        }
        return new String(buf);
    }

    public static byte[] b(String str) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, (Object) null, changeQuickRedirect, true, 9153, new Class[]{String.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }
        byte[] bytes = new byte[(str.length() / 2)];
        for (int i = 0; i < str.length() / 2; i++) {
            bytes[i] = (byte) Integer.parseInt(str.substring(i * 2, (i * 2) + 2), 16);
        }
        return bytes;
    }
}
