package com.leedarson.serviceimpl.ble.manager;

import android.text.TextUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: HexUtils */
public class d {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static ChangeQuickRedirect changeQuickRedirect;

    public static int[] b(byte s) {
        return new int[]{s & 15, s >> 4};
    }

    public static byte c(int all, int index) {
        return (byte) ((all << 4) | index);
    }

    public static byte f(String s) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{s}, (Object) null, changeQuickRedirect, true, 6275, new Class[]{String.class}, Byte.TYPE);
        if (proxy.isSupported) {
            return ((Byte) proxy.result).byteValue();
        }
        return (byte) Integer.parseInt(s, 16);
    }

    public static byte[] e(String s) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{s}, (Object) null, changeQuickRedirect, true, 6276, new Class[]{String.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (TextUtils.isEmpty(s) || s.length() < 2) {
            return new byte[0];
        }
        int size = s.length() / 2;
        byte[] writeByte = new byte[size];
        for (int i = 0; i < size; i++) {
            writeByte[i] = (byte) Integer.parseInt(s.substring(i * 2, (i * 2) + 2), 16);
        }
        return writeByte;
    }

    public static String a(byte b2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Byte(b2)}, (Object) null, changeQuickRedirect, true, 6277, new Class[]{Byte.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String s = Integer.toHexString(b2);
        if (s.length() == 1) {
            s = "0" + s;
        }
        if (s.length() > 2) {
            return s.substring(s.length() - 2);
        }
        return s;
    }

    public static String d(byte[] bytes) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bytes}, (Object) null, changeQuickRedirect, true, 6278, new Class[]{byte[].class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (bytes.length < 8) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            if (i > 6) {
                builder.append(a(bytes[i]));
            }
        }
        return builder.toString();
    }
}
