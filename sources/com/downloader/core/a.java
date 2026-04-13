package com.downloader.core;

/* compiled from: Core */
public class a {
    private static a a = null;
    private final e b = new b();

    private a() {
    }

    public static a b() {
        if (a == null) {
            synchronized (a.class) {
                if (a == null) {
                    a = new a();
                }
            }
        }
        return a;
    }

    public e a() {
        return this.b;
    }
}
