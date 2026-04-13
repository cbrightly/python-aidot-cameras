package com.didichuxing.doraemonkit.okgo.utils;

import android.util.Log;

public class OkLogger {
    private static boolean isLogEnable = true;
    private static String tag = "OkGo";

    public static void debug(boolean isEnable) {
        debug(tag, isEnable);
    }

    public static void debug(String logTag, boolean isEnable) {
        tag = logTag;
        isLogEnable = isEnable;
    }

    public static void v(String msg) {
        v(tag, msg);
    }

    public static void v(String tag2, String msg) {
        if (isLogEnable) {
            Log.v(tag2, msg);
        }
    }

    public static void d(String msg) {
        d(tag, msg);
    }

    public static void d(String tag2, String msg) {
        if (isLogEnable) {
            Log.d(tag2, msg);
        }
    }

    public static void i(String msg) {
        i(tag, msg);
    }

    public static void i(String tag2, String msg) {
        if (isLogEnable) {
            Log.i(tag2, msg);
        }
    }

    public static void w(String msg) {
        w(tag, msg);
    }

    public static void w(String tag2, String msg) {
        if (isLogEnable) {
            Log.w(tag2, msg);
        }
    }

    public static void e(String msg) {
        e(tag, msg);
    }

    public static void e(String tag2, String msg) {
        if (isLogEnable) {
            Log.e(tag2, msg);
        }
    }

    public static void printStackTrace(Throwable t) {
        if (isLogEnable && t != null) {
            t.printStackTrace();
        }
    }
}
