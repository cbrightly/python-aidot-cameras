package com.downloader.request;

import com.downloader.f;
import com.downloader.i;
import com.downloader.l;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;

/* compiled from: DownloadRequest */
public class a {
    private i a;
    private Object b;
    private String c;
    private String d;
    private String e;
    private int f;
    private Future g;
    private long h;
    private long i;
    private int j;
    private int k;
    private String l;
    private com.downloader.e m;
    /* access modifiers changed from: private */
    public com.downloader.c n;
    /* access modifiers changed from: private */
    public f o;
    /* access modifiers changed from: private */
    public com.downloader.d p;
    /* access modifiers changed from: private */
    public com.downloader.b q;
    private int r;
    private HashMap<String, List<String>> s;
    private l t;

    a(b builder) {
        this.c = builder.a;
        this.d = builder.b;
        this.e = builder.c;
        this.s = builder.i;
        this.a = builder.d;
        this.b = builder.e;
        int i2 = builder.f;
        this.j = i2 == 0 ? x() : i2;
        int i3 = builder.g;
        this.k = i3 == 0 ? o() : i3;
        this.l = builder.h;
    }

    public i v() {
        return this.a;
    }

    public String B() {
        return this.c;
    }

    public void K(String url) {
        this.c = url;
    }

    public String p() {
        return this.d;
    }

    public String s() {
        return this.e;
    }

    public int y() {
        return this.f;
    }

    public void H(int sequenceNumber) {
        this.f = sequenceNumber;
    }

    public HashMap<String, List<String>> t() {
        return this.s;
    }

    public void E(Future future) {
        this.g = future;
    }

    public long r() {
        return this.h;
    }

    public void D(long downloadedBytes) {
        this.h = downloadedBytes;
    }

    public long A() {
        return this.i;
    }

    public void J(long totalBytes) {
        this.i = totalBytes;
    }

    public int w() {
        return this.j;
    }

    public int n() {
        return this.k;
    }

    public String C() {
        if (this.l == null) {
            this.l = com.downloader.internal.a.d().f();
        }
        return this.l;
    }

    public int q() {
        return this.r;
    }

    public l z() {
        return this.t;
    }

    public void I(l status) {
        this.t = status;
    }

    public com.downloader.e u() {
        return this.m;
    }

    public a G(f onStartOrResumeListener) {
        this.o = onStartOrResumeListener;
        return this;
    }

    public a F(com.downloader.e onProgressListener) {
        this.m = onProgressListener;
        return this;
    }

    public int L(com.downloader.c onDownloadListener) {
        this.n = onDownloadListener;
        this.r = com.downloader.utils.a.f(this.c, this.d, this.e);
        com.downloader.internal.b.e().a(this);
        return this.r;
    }

    public void h(com.downloader.a error) {
        if (this.t != l.CANCELLED) {
            I(l.FAILED);
            com.downloader.core.a.b().a().b().execute(new C0065a(error));
        }
    }

    /* renamed from: com.downloader.request.a$a  reason: collision with other inner class name */
    /* compiled from: DownloadRequest */
    public class C0065a implements Runnable {
        final /* synthetic */ com.downloader.a c;

        C0065a(com.downloader.a aVar) {
            this.c = aVar;
        }

        public void run() {
            if (a.this.n != null) {
                a.this.n.b(this.c);
            }
            a.this.m();
        }
    }

    public void k() {
        if (this.t != l.CANCELLED) {
            I(l.COMPLETED);
            com.downloader.core.a.b().a().b().execute(new b());
        }
    }

    /* compiled from: DownloadRequest */
    public class b implements Runnable {
        b() {
        }

        public void run() {
            if (a.this.n != null) {
                a.this.n.a();
            }
            a.this.m();
        }
    }

    public void j() {
        if (this.t != l.CANCELLED) {
            com.downloader.core.a.b().a().b().execute(new c());
        }
    }

    /* compiled from: DownloadRequest */
    public class c implements Runnable {
        c() {
        }

        public void run() {
            if (a.this.o != null) {
                a.this.o.a();
            }
        }
    }

    public void i() {
        if (this.t != l.CANCELLED) {
            com.downloader.core.a.b().a().b().execute(new d());
        }
    }

    /* compiled from: DownloadRequest */
    public class d implements Runnable {
        d() {
        }

        public void run() {
            if (a.this.p != null) {
                a.this.p.onPause();
            }
        }
    }

    /* compiled from: DownloadRequest */
    public class e implements Runnable {
        e() {
        }

        public void run() {
            if (a.this.q != null) {
                a.this.q.onCancel();
            }
        }
    }

    private void g() {
        com.downloader.core.a.b().a().b().execute(new e());
    }

    public void f() {
        this.t = l.CANCELLED;
        Future future = this.g;
        if (future != null) {
            future.cancel(true);
        }
        g();
        com.downloader.utils.a.a(com.downloader.utils.a.e(this.d, this.e), this.r);
    }

    /* access modifiers changed from: private */
    public void m() {
        l();
        com.downloader.internal.b.e().d(this);
    }

    private void l() {
        this.m = null;
        this.n = null;
        this.o = null;
        this.p = null;
        this.q = null;
    }

    private int x() {
        return com.downloader.internal.a.d().e();
    }

    private int o() {
        return com.downloader.internal.a.d().a();
    }
}
