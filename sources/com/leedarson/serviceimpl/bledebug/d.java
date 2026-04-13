package com.leedarson.serviceimpl.bledebug;

import com.meituan.robust.ChangeQuickRedirect;

/* compiled from: TestResult */
public class d {
    private static int a;
    private static int b;
    private static int c;
    public static ChangeQuickRedirect changeQuickRedirect;
    private static int d;

    public static void a() {
        a = 0;
        b = 0;
        d = 0;
        c = 0;
    }

    public static int e(int i) {
        int i2 = a + i;
        a = i2;
        return i2;
    }

    public static int d() {
        int i = b + 1;
        b = i;
        return i;
    }

    public static int c() {
        return a;
    }

    public static int b() {
        return b;
    }
}
