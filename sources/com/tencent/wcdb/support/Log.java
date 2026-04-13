package com.tencent.wcdb.support;

public class Log {
    private static LogCallback a = null;

    public interface LogCallback {
        void println(int i, String str, String str2);
    }

    private static native void nativePrintLn(int i, String str, String str2);

    private static native void nativeSetLogger(int i, LogCallback logCallback);

    private Log() {
    }

    public static void e(int priority, String tag, String msg) {
        LogCallback logCallback = a;
        if (logCallback != null) {
            logCallback.println(priority, tag, msg);
        } else {
            nativePrintLn(priority, tag, msg);
        }
    }

    public static void a(String tag, String msg) {
        e(6, tag, msg);
    }

    public static void f(String tag, String msg) {
        e(5, tag, msg);
    }

    public static void c(String tag, String msg) {
        e(4, tag, msg);
    }

    public static void b(String tag, String format, Object... args) {
        e(6, tag, String.format(format, args));
    }

    public static void g(String tag, String format, Object... args) {
        e(5, tag, String.format(format, args));
    }

    public static void d(String tag, String format, Object... args) {
        e(4, tag, String.format(format, args));
    }
}
