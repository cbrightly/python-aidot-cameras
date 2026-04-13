package com.leedarson.serviceimpl.database.manager;

import com.meituan.robust.ChangeQuickRedirect;

/* compiled from: Note */
public class e {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Long a;
    private String b;
    private String c;

    public e(Long _id, String source, String url) {
        this.a = _id;
        this.b = source;
        this.c = url;
    }

    public e() {
    }

    public Long c() {
        return this.a;
    }

    public void f(Long _id) {
        this.a = _id;
    }

    public String a() {
        return this.b;
    }

    public void d(String source) {
        this.b = source;
    }

    public String b() {
        return this.c;
    }

    public void e(String url) {
        this.c = url;
    }
}
