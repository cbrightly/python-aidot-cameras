package com.leedarson.base.utils;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.security.SecureRandom;

/* compiled from: Arrays */
public final class e {
    public static final char[] a = "0123456789ABCDEF".toCharArray();
    public static ChangeQuickRedirect changeQuickRedirect;

    public static byte[] h(byte[] a2) {
        if (a2 == null) {
            return null;
        }
        int p1 = 0;
        int p2 = a2.length;
        byte[] result = new byte[p2];
        while (true) {
            p2--;
            if (p2 < 0) {
                return result;
            }
            result[p2] = a2[p1];
            p1++;
        }
    }

    public static boolean d(byte[] array1, byte[] array2) {
        if (array1 == array2) {
            return true;
        }
        if (array1 == null || array2 == null || array1.length != array2.length) {
            return false;
        }
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }

    public static String a(byte[] array) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{array}, (Object) null, changeQuickRedirect, true, 452, new Class[]{byte[].class}, String.class);
        return proxy.isSupported ? (String) proxy.result : b(array, "");
    }

    public static String b(byte[] array, String separator) {
        boolean z = false;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{array, separator}, (Object) null, changeQuickRedirect, true, 453, new Class[]{byte[].class, String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (array == null || array.length == 0) {
            return "";
        }
        if (separator == null || separator.length() == 0) {
            z = true;
        }
        boolean sepNul = z;
        StringBuilder hexResult = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            int ai = array[i] & 255;
            if (i != 0 && !sepNul) {
                hexResult.append(separator);
            }
            char[] cArr = a;
            hexResult.append(cArr[ai >>> 4]);
            hexResult.append(cArr[ai & 15]);
        }
        return hexResult.toString();
    }

    public static byte[] g(String hexStr) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{hexStr}, (Object) null, changeQuickRedirect, true, 454, new Class[]{String.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (hexStr == null) {
            return null;
        }
        if (hexStr.length() == 1) {
            hexStr = "0" + hexStr;
        }
        int length = hexStr.length() / 2;
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = (byte) Integer.parseInt(hexStr.substring(i * 2, (i * 2) + 2), 16);
        }
        return result;
    }

    public static byte[] f(String hexString) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{hexString}, (Object) null, changeQuickRedirect, true, 455, new Class[]{String.class}, byte[].class);
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
            d[i] = (byte) ((c(hexChars[pos]) << 4) | c(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte c(char c) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Character(c)}, (Object) null, changeQuickRedirect, true, 456, new Class[]{Character.TYPE}, Byte.TYPE);
        return proxy.isSupported ? ((Byte) proxy.result).byteValue() : (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static byte[] e(int length) {
        Object[] objArr = {new Integer(length)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 457, new Class[]{Integer.TYPE}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] data = new byte[length];
        new SecureRandom().nextBytes(data);
        return data;
    }
}
