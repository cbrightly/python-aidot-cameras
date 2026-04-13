package com.didichuxing.doraemonkit.util;

import com.blankj.utilcode.util.q;
import com.meituan.robust.Constants;

public class LogHelper {
    private static boolean IS_DEBUG = true;
    private static final String TAG = "DoraemonKit";

    public static void d(String subTag, String msg) {
        if (IS_DEBUG) {
            q.F(Constants.ARRAY_TYPE + subTag + "]: " + msg);
        }
    }

    public static void i(String subTag, String msg) {
        if (IS_DEBUG) {
            q.F(Constants.ARRAY_TYPE + subTag + "]: " + msg);
        }
    }

    public static void e(String subTag, String msg) {
        if (IS_DEBUG) {
            q.F(Constants.ARRAY_TYPE + subTag + "]: " + msg);
        }
    }

    public static void setDebug(boolean debug) {
        IS_DEBUG = debug;
    }
}
