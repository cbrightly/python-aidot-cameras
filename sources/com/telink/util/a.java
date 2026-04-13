package com.telink.util;

import com.google.maps.android.BuildConfig;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.Formatter;

/* compiled from: Arrays */
public final class a {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static byte[] d(byte[] a) {
        if (a == null) {
            return null;
        }
        int p1 = 0;
        int p2 = a.length;
        byte[] result = new byte[p2];
        while (true) {
            p2--;
            if (p2 < 0) {
                return result;
            }
            result[p2] = a[p1];
            p1++;
        }
    }

    public static byte[] e(byte[] arr, int begin, int end) {
        while (begin < end) {
            byte temp = arr[end];
            arr[end] = arr[begin];
            arr[begin] = temp;
            begin++;
            end--;
        }
        return arr;
    }

    public static boolean c(byte[] array1, byte[] array2) {
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

    public static String b(byte[] array) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{array}, (Object) null, changeQuickRedirect, true, 13861, new Class[]{byte[].class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (array == null) {
            return BuildConfig.TRAVIS;
        }
        if (array.length == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder(array.length * 6);
        sb.append('[');
        sb.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            sb.append(", ");
            sb.append(array[i]);
        }
        sb.append(']');
        return sb.toString();
    }

    public static String a(byte[] array, String separator) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{array, separator}, (Object) null, changeQuickRedirect, true, 13863, new Class[]{byte[].class, String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (array == null || array.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        formatter.format("%02X", new Object[]{Byte.valueOf(array[0])});
        for (int i = 1; i < array.length; i++) {
            if (!f.b(separator)) {
                sb.append(separator);
            }
            formatter.format("%02X", new Object[]{Byte.valueOf(array[i])});
        }
        formatter.flush();
        formatter.close();
        return sb.toString();
    }
}
