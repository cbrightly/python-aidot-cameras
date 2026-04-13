package com.squareup.okhttp;

import com.squareup.okhttp.internal.d;
import com.squareup.okhttp.internal.e;
import com.squareup.okhttp.internal.http.q;
import com.squareup.okhttp.internal.i;
import com.squareup.okhttp.internal.io.b;
import com.squareup.okhttp.internal.j;
import com.squareup.okhttp.p;
import java.net.CookieHandler;
import java.net.Proxy;
import java.net.ProxySelector;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/* compiled from: OkHttpClient */
public class t implements Cloneable {
    private static final List<u> c = j.k(u.HTTP_2, u.SPDY_3, u.HTTP_1_1);
    private static final List<k> d = j.k(k.b, k.c, k.d);
    private static SSLSocketFactory f;
    private SSLSocketFactory A4;
    private HostnameVerifier B4;
    private f C4;
    private b D4;
    private j E4;
    private n F4;
    private boolean G4;
    private boolean H4;
    private boolean I4;
    private int J4;
    private int K4;
    private int L4;
    private final List<r> a1;
    private ProxySelector a2;
    private List<k> p0;
    private final List<r> p1;
    private CookieHandler p2;
    private e p3;
    private c p4;
    private final i q;
    private m x;
    private Proxy y;
    private List<u> z;
    private SocketFactory z4;

    static {
        d.b = new a();
    }

    /* compiled from: OkHttpClient */
    public static final class a extends d {
        a() {
        }

        public void a(p.b builder, String line) {
            builder.c(line);
        }

        public e e(t client) {
            return client.A();
        }

        public boolean c(j pool, b connection) {
            return pool.b(connection);
        }

        public b d(j pool, a address, q streamAllocation) {
            return pool.c(address, streamAllocation);
        }

        public void f(j pool, b connection) {
            pool.f(connection);
        }

        public i g(j connectionPool) {
            return connectionPool.g;
        }

        public void b(k tlsConfiguration, SSLSocket sslSocket, boolean isFallback) {
            tlsConfiguration.e(sslSocket, isFallback);
        }
    }

    public t() {
        this.a1 = new ArrayList();
        this.p1 = new ArrayList();
        this.G4 = true;
        this.H4 = true;
        this.I4 = true;
        this.J4 = 10000;
        this.K4 = 10000;
        this.L4 = 10000;
        this.q = new i();
        this.x = new m();
    }

    private t(t okHttpClient) {
        ArrayList arrayList = new ArrayList();
        this.a1 = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.p1 = arrayList2;
        this.G4 = true;
        this.H4 = true;
        this.I4 = true;
        this.J4 = 10000;
        this.K4 = 10000;
        this.L4 = 10000;
        this.q = okHttpClient.q;
        this.x = okHttpClient.x;
        this.y = okHttpClient.y;
        this.z = okHttpClient.z;
        this.p0 = okHttpClient.p0;
        arrayList.addAll(okHttpClient.a1);
        arrayList2.addAll(okHttpClient.p1);
        this.a2 = okHttpClient.a2;
        this.p2 = okHttpClient.p2;
        c cVar = okHttpClient.p4;
        this.p4 = cVar;
        this.p3 = cVar != null ? cVar.a : okHttpClient.p3;
        this.z4 = okHttpClient.z4;
        this.A4 = okHttpClient.A4;
        this.B4 = okHttpClient.B4;
        this.C4 = okHttpClient.C4;
        this.D4 = okHttpClient.D4;
        this.E4 = okHttpClient.E4;
        this.F4 = okHttpClient.F4;
        this.G4 = okHttpClient.G4;
        this.H4 = okHttpClient.H4;
        this.I4 = okHttpClient.I4;
        this.J4 = okHttpClient.J4;
        this.K4 = okHttpClient.K4;
        this.L4 = okHttpClient.L4;
    }

    public void F(long timeout, TimeUnit unit) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout < 0");
        } else if (unit != null) {
            long millis = unit.toMillis(timeout);
            if (millis > 2147483647L) {
                throw new IllegalArgumentException("Timeout too large.");
            } else if (millis != 0 || timeout <= 0) {
                this.J4 = (int) millis;
            } else {
                throw new IllegalArgumentException("Timeout too small.");
            }
        } else {
            throw new IllegalArgumentException("unit == null");
        }
    }

    public int f() {
        return this.J4;
    }

    public void G(long timeout, TimeUnit unit) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout < 0");
        } else if (unit != null) {
            long millis = unit.toMillis(timeout);
            if (millis > 2147483647L) {
                throw new IllegalArgumentException("Timeout too large.");
            } else if (millis != 0 || timeout <= 0) {
                this.K4 = (int) millis;
            } else {
                throw new IllegalArgumentException("Timeout too small.");
            }
        } else {
            throw new IllegalArgumentException("unit == null");
        }
    }

    public int t() {
        return this.K4;
    }

    public void H(long timeout, TimeUnit unit) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout < 0");
        } else if (unit != null) {
            long millis = unit.toMillis(timeout);
            if (millis > 2147483647L) {
                throw new IllegalArgumentException("Timeout too large.");
            } else if (millis != 0 || timeout <= 0) {
                this.L4 = (int) millis;
            } else {
                throw new IllegalArgumentException("Timeout too small.");
            }
        } else {
            throw new IllegalArgumentException("unit == null");
        }
    }

    public int y() {
        return this.L4;
    }

    public Proxy r() {
        return this.y;
    }

    public ProxySelector s() {
        return this.a2;
    }

    public CookieHandler j() {
        return this.p2;
    }

    /* access modifiers changed from: package-private */
    public e A() {
        return this.p3;
    }

    public t D(c cache) {
        this.p4 = cache;
        this.p3 = null;
        return this;
    }

    public c d() {
        return this.p4;
    }

    public n m() {
        return this.F4;
    }

    public SocketFactory v() {
        return this.z4;
    }

    public SSLSocketFactory w() {
        return this.A4;
    }

    public HostnameVerifier p() {
        return this.B4;
    }

    public f e() {
        return this.C4;
    }

    public b c() {
        return this.D4;
    }

    public j h() {
        return this.E4;
    }

    public boolean o() {
        return this.G4;
    }

    public boolean n() {
        return this.H4;
    }

    public boolean u() {
        return this.I4;
    }

    public m l() {
        return this.x;
    }

    public List<u> q() {
        return this.z;
    }

    public List<k> i() {
        return this.p0;
    }

    public List<r> z() {
        return this.a1;
    }

    public List<r> B() {
        return this.p1;
    }

    public e C(v request) {
        return new e(this, request);
    }

    /* access modifiers changed from: package-private */
    public t b() {
        t result = new t(this);
        if (result.a2 == null) {
            result.a2 = ProxySelector.getDefault();
        }
        if (result.p2 == null) {
            result.p2 = CookieHandler.getDefault();
        }
        if (result.z4 == null) {
            result.z4 = SocketFactory.getDefault();
        }
        if (result.A4 == null) {
            result.A4 = k();
        }
        if (result.B4 == null) {
            result.B4 = com.squareup.okhttp.internal.tls.d.a;
        }
        if (result.C4 == null) {
            result.C4 = f.a;
        }
        if (result.D4 == null) {
            result.D4 = com.squareup.okhttp.internal.http.a.a;
        }
        if (result.E4 == null) {
            result.E4 = j.d();
        }
        if (result.z == null) {
            result.z = c;
        }
        if (result.p0 == null) {
            result.p0 = d;
        }
        if (result.F4 == null) {
            result.F4 = n.a;
        }
        return result;
    }

    private synchronized SSLSocketFactory k() {
        if (f == null) {
            try {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init((KeyManager[]) null, (TrustManager[]) null, (SecureRandom) null);
                f = sslContext.getSocketFactory();
            } catch (GeneralSecurityException e) {
                throw new AssertionError();
            }
        }
        return f;
    }

    /* renamed from: a */
    public t clone() {
        return new t(this);
    }
}
