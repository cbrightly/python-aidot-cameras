package com.downloader.internal;

import android.content.Context;
import com.downloader.database.c;
import com.downloader.database.e;
import com.downloader.g;
import com.downloader.h;
import com.downloader.httpclient.b;
import meshsdk.ctrl.GroupCtrlAdapter;

/* compiled from: ComponentHolder */
public class a {
    private static final a a = new a();
    private int b;
    private int c;
    private String d;
    private b e;
    private c f;

    public static a d() {
        return a;
    }

    public void g(Context context, h config) {
        this.b = config.c();
        this.c = config.a();
        this.d = config.d();
        this.e = config.b();
        this.f = config.e() ? new com.downloader.database.a(context) : new e();
        if (config.e()) {
            g.b(30);
        }
    }

    public int e() {
        if (this.b == 0) {
            synchronized (a.class) {
                if (this.b == 0) {
                    this.b = GroupCtrlAdapter.TIMEOUT_WAIT_MESH_ONLINE;
                }
            }
        }
        return this.b;
    }

    public int a() {
        if (this.c == 0) {
            synchronized (a.class) {
                if (this.c == 0) {
                    this.c = GroupCtrlAdapter.TIMEOUT_WAIT_MESH_ONLINE;
                }
            }
        }
        return this.c;
    }

    public String f() {
        if (this.d == null) {
            synchronized (a.class) {
                if (this.d == null) {
                    this.d = "PRDownloader";
                }
            }
        }
        return this.d;
    }

    public c b() {
        if (this.f == null) {
            synchronized (a.class) {
                if (this.f == null) {
                    this.f = new e();
                }
            }
        }
        return this.f;
    }

    public b c() {
        if (this.e == null) {
            synchronized (a.class) {
                if (this.e == null) {
                    this.e = new com.downloader.httpclient.a();
                }
            }
        }
        return this.e.clone();
    }
}
