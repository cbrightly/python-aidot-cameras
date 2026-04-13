package com.leedarson.smartcamera.utils;

import android.util.Log;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.Locale;

/* compiled from: Logger */
public class e {
    private static String a;
    private static String b;
    private static int c;
    public static ChangeQuickRedirect changeQuickRedirect;
    private static boolean d = false;
    private static boolean e = true;

    private static String a(String log) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{log}, (Object) null, changeQuickRedirect, true, 10486, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(b);
        buffer.append("(");
        buffer.append(a);
        buffer.append(":");
        buffer.append(c);
        buffer.append("):");
        buffer.append(log);
        return buffer.toString();
    }

    private static void d(StackTraceElement[] sElements) {
        if (!PatchProxy.proxy(new Object[]{sElements}, (Object) null, changeQuickRedirect, true, 10487, new Class[]{StackTraceElement[].class}, Void.TYPE).isSupported) {
            a = sElements[1].getFileName();
            b = sElements[1].getMethodName();
            c = sElements[1].getLineNumber();
        }
    }

    public static void c(String tag, String message) {
        Class<String> cls = String.class;
        if (PatchProxy.proxy(new Object[]{tag, message}, (Object) null, changeQuickRedirect, true, 10488, new Class[]{cls, cls}, Void.TYPE).isSupported || !d) {
            return;
        }
        if (e) {
            d(new Throwable().getStackTrace());
            Log.e(String.format(Locale.US, "%s:%s", new Object[]{a, tag}), a(message));
            return;
        }
        Log.e(tag, message);
    }

    public static void e(String tag, String message) {
        Class<String> cls = String.class;
        if (PatchProxy.proxy(new Object[]{tag, message}, (Object) null, changeQuickRedirect, true, 10489, new Class[]{cls, cls}, Void.TYPE).isSupported || !d) {
            return;
        }
        if (e) {
            d(new Throwable().getStackTrace());
            Log.i(String.format(Locale.US, "%s:%s", new Object[]{a, tag}), a(message));
            return;
        }
        Log.i(tag, message);
    }

    public static void b(String tag, String message) {
        Class<String> cls = String.class;
        if (PatchProxy.proxy(new Object[]{tag, message}, (Object) null, changeQuickRedirect, true, 10490, new Class[]{cls, cls}, Void.TYPE).isSupported || !d) {
            return;
        }
        if (e) {
            d(new Throwable().getStackTrace());
            Log.d(String.format(Locale.US, "%s:%s", new Object[]{a, tag}), a(message));
            return;
        }
        Log.d(tag, message);
    }
}
