package com.yanzhenjie.andserver.framework.config;

import com.yanzhenjie.andserver.framework.config.c;
import java.util.ArrayList;
import java.util.List;

/* compiled from: Delegate */
public class a implements c.a {
    private b a;
    private List<com.yanzhenjie.andserver.framework.website.c> b = new ArrayList();

    public static a e() {
        return new a();
    }

    private a() {
    }

    public b c() {
        return this.a;
    }

    public void a(b multipart) {
        this.a = multipart;
    }

    public List<com.yanzhenjie.andserver.framework.website.c> d() {
        return this.b;
    }

    public void b(com.yanzhenjie.andserver.framework.website.c website) {
        this.b.add(website);
    }
}
