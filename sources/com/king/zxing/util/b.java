package com.king.zxing.util;

import android.util.Log;
import java.util.Locale;
import timber.log.a;

/* compiled from: LogUtils */
public class b {
    private static boolean a = true;
    private static int b = 1;

    public static boolean g() {
        return a;
    }

    private static String b(StackTraceElement caller) {
        String callerClazzName = caller.getClassName();
        String callerClazzName2 = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        String tag = String.format(Locale.US, "%s.%s(L:%d)", new Object[]{callerClazzName2, caller.getMethodName(), Integer.valueOf(caller.getLineNumber())});
        return "ZXingLite" + "|" + tag;
    }

    public static StackTraceElement d(int n) {
        return Thread.currentThread().getStackTrace()[n];
    }

    private static String c() {
        return b(d(5));
    }

    private static String e(Throwable t) {
        return Log.getStackTraceString(t);
    }

    public static void a(String msg) {
        if (a && b <= 3) {
            a.a(c(), String.valueOf(msg));
        }
    }

    public static void f(String msg) {
        if (a && b <= 4) {
            a.e(c(), String.valueOf(msg));
        }
    }

    public static void h(String msg) {
    }

    public static void i(String msg) {
        if (a && b <= 5) {
            a.j(c(), String.valueOf(msg));
        }
    }

    public static void k(Throwable t) {
        if (a && b <= 5) {
            a.j(c(), e(t));
        }
    }

    public static void j(String msg, Throwable t) {
        if (a && b <= 5) {
            a.j(c(), String.valueOf(msg), t);
        }
    }
}
