package com.clj.fastble.utils;

/* compiled from: BleLog */
public final class a {
    public static boolean a = true;

    public static void b(String msg) {
        if (a && msg != null) {
            timber.log.a.g("FastBle").h(msg, new Object[0]);
        }
    }

    public static void c(String msg) {
        if (a && msg != null) {
            timber.log.a.g("FastBle").n(msg, new Object[0]);
        }
    }

    public static void a(String msg) {
        if (a && msg != null) {
            timber.log.a.g("FastBle").c(msg, new Object[0]);
        }
    }
}
