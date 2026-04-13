package com.google.android.datatransport.runtime.logging;

import android.os.Build;
import android.util.Log;

public final class Logging {
    private static final String LOG_PREFIX = "TRuntime.";
    private static final int MAX_LOG_TAG_SIZE_IN_SDK_N = 23;

    private Logging() {
    }

    private static String getTag(String tag) {
        if (Build.VERSION.SDK_INT < 26) {
            return concatTag(LOG_PREFIX, tag);
        }
        return LOG_PREFIX + tag;
    }

    private static String concatTag(String prefix, String tag) {
        String concatText = prefix + tag;
        if (concatText.length() > 23) {
            return concatText.substring(0, 23);
        }
        return concatText;
    }

    public static void d(String tag, String message) {
        String tag2 = getTag(tag);
        if (Log.isLoggable(tag2, 3)) {
            Log.d(tag2, message);
        }
    }

    public static void d(String tag, String message, Object arg1) {
        String tag2 = getTag(tag);
        if (Log.isLoggable(tag2, 3)) {
            Log.d(tag2, String.format(message, new Object[]{arg1}));
        }
    }

    public static void d(String tag, String message, Object arg1, Object arg2) {
        String tag2 = getTag(tag);
        if (Log.isLoggable(tag2, 3)) {
            Log.d(tag2, String.format(message, new Object[]{arg1, arg2}));
        }
    }

    public static void d(String tag, String message, Object... args) {
        String tag2 = getTag(tag);
        if (Log.isLoggable(tag2, 3)) {
            Log.d(tag2, String.format(message, args));
        }
    }

    public static void i(String tag, String message, Object arg1) {
        String tag2 = getTag(tag);
        if (Log.isLoggable(tag2, 4)) {
            Log.i(tag2, String.format(message, new Object[]{arg1}));
        }
    }

    public static void e(String tag, String message, Throwable e) {
        String tag2 = getTag(tag);
        if (Log.isLoggable(tag2, 6)) {
            Log.e(tag2, message, e);
        }
    }

    public static void w(String tag, String message, Object arg1) {
        String tag2 = getTag(tag);
        if (Log.isLoggable(tag2, 5)) {
            Log.w(tag2, String.format(message, new Object[]{arg1}));
        }
    }
}
