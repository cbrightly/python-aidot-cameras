package com.telink.util;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.nio.charset.Charset;

/* compiled from: Strings */
public final class f {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static byte[] c(String str, int length) {
        Object[] objArr = {str, new Integer(length)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 13887, new Class[]{String.class, Integer.TYPE}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (length <= 0) {
            return str.getBytes(Charset.defaultCharset());
        }
        byte[] result = new byte[length];
        byte[] srcBytes = str.getBytes(Charset.defaultCharset());
        if (srcBytes.length <= length) {
            System.arraycopy(srcBytes, 0, result, 0, srcBytes.length);
        } else {
            System.arraycopy(srcBytes, 0, result, 0, length);
        }
        return result;
    }

    public static String a(byte[] data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, (Object) null, changeQuickRedirect, true, 13889, new Class[]{byte[].class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (data == null || data.length <= 0) {
            return null;
        }
        return new String(data, Charset.defaultCharset()).trim();
    }

    public static boolean b(String str) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, (Object) null, changeQuickRedirect, true, 13890, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return str == null || str.trim().isEmpty();
    }
}
