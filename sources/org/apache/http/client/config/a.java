package org.apache.http.client.config;

import com.meituan.robust.Constants;
import java.net.InetAddress;
import java.util.Collection;
import org.apache.http.l;

/* compiled from: RequestConfig */
public class a implements Cloneable {
    public static final a c = new C0091a().a();
    private final int A4;
    private final boolean B4;
    private final boolean a1;
    private final boolean a2;
    private final boolean d;
    private final l f;
    private final boolean p0;
    private final int p1;
    private final Collection<String> p2;
    private final Collection<String> p3;
    private final int p4;
    private final InetAddress q;
    private final boolean x;
    private final String y;
    private final boolean z;
    private final int z4;

    protected a() {
        this(false, (l) null, (InetAddress) null, false, (String) null, false, false, false, 0, false, (Collection<String>) null, (Collection<String>) null, 0, 0, 0, true);
    }

    a(boolean expectContinueEnabled, l proxy, InetAddress localAddress, boolean staleConnectionCheckEnabled, String cookieSpec, boolean redirectsEnabled, boolean relativeRedirectsAllowed, boolean circularRedirectsAllowed, int maxRedirects, boolean authenticationEnabled, Collection<String> targetPreferredAuthSchemes, Collection<String> proxyPreferredAuthSchemes, int connectionRequestTimeout, int connectTimeout, int socketTimeout, boolean contentCompressionEnabled) {
        this.d = expectContinueEnabled;
        this.f = proxy;
        this.q = localAddress;
        this.x = staleConnectionCheckEnabled;
        this.y = cookieSpec;
        this.z = redirectsEnabled;
        this.p0 = relativeRedirectsAllowed;
        this.a1 = circularRedirectsAllowed;
        this.p1 = maxRedirects;
        this.a2 = authenticationEnabled;
        this.p2 = targetPreferredAuthSchemes;
        this.p3 = proxyPreferredAuthSchemes;
        this.p4 = connectionRequestTimeout;
        this.z4 = connectTimeout;
        this.A4 = socketTimeout;
        this.B4 = contentCompressionEnabled;
    }

    public boolean r() {
        return this.d;
    }

    public l j() {
        return this.f;
    }

    public InetAddress h() {
        return this.q;
    }

    @Deprecated
    public boolean u() {
        return this.x;
    }

    public String f() {
        return this.y;
    }

    public boolean s() {
        return this.z;
    }

    public boolean t() {
        return this.p0;
    }

    public boolean o() {
        return this.a1;
    }

    public int i() {
        return this.p1;
    }

    public boolean n() {
        return this.a2;
    }

    public Collection<String> m() {
        return this.p2;
    }

    public Collection<String> k() {
        return this.p3;
    }

    public int e() {
        return this.p4;
    }

    public int d() {
        return this.z4;
    }

    public int l() {
        return this.A4;
    }

    @Deprecated
    public boolean q() {
        return this.B4;
    }

    public boolean p() {
        return this.B4;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public a clone() {
        return (a) super.clone();
    }

    public String toString() {
        return Constants.ARRAY_TYPE + "expectContinueEnabled=" + this.d + ", proxy=" + this.f + ", localAddress=" + this.q + ", cookieSpec=" + this.y + ", redirectsEnabled=" + this.z + ", relativeRedirectsAllowed=" + this.p0 + ", maxRedirects=" + this.p1 + ", circularRedirectsAllowed=" + this.a1 + ", authenticationEnabled=" + this.a2 + ", targetPreferredAuthSchemes=" + this.p2 + ", proxyPreferredAuthSchemes=" + this.p3 + ", connectionRequestTimeout=" + this.p4 + ", connectTimeout=" + this.z4 + ", socketTimeout=" + this.A4 + ", contentCompressionEnabled=" + this.B4 + "]";
    }

    public static C0091a c() {
        return new C0091a();
    }

    public static C0091a b(a config) {
        return new C0091a().i(config.r()).l(config.j()).j(config.h()).q(config.u()).g(config.f()).n(config.s()).o(config.t()).c(config.o()).k(config.i()).b(config.n()).r(config.m()).m(config.k()).e(config.e()).d(config.d()).p(config.l()).h(config.q()).f(config.p());
    }

    /* renamed from: org.apache.http.client.config.a$a  reason: collision with other inner class name */
    /* compiled from: RequestConfig */
    public static class C0091a {
        private boolean a;
        private l b;
        private InetAddress c;
        private boolean d = false;
        private String e;
        private boolean f = true;
        private boolean g = true;
        private boolean h;
        private int i = 50;
        private boolean j = true;
        private Collection<String> k;
        private Collection<String> l;
        private int m = -1;
        private int n = -1;
        private int o = -1;
        private boolean p = true;

        C0091a() {
        }

        public C0091a i(boolean expectContinueEnabled) {
            this.a = expectContinueEnabled;
            return this;
        }

        public C0091a l(l proxy) {
            this.b = proxy;
            return this;
        }

        public C0091a j(InetAddress localAddress) {
            this.c = localAddress;
            return this;
        }

        @Deprecated
        public C0091a q(boolean staleConnectionCheckEnabled) {
            this.d = staleConnectionCheckEnabled;
            return this;
        }

        public C0091a g(String cookieSpec) {
            this.e = cookieSpec;
            return this;
        }

        public C0091a n(boolean redirectsEnabled) {
            this.f = redirectsEnabled;
            return this;
        }

        public C0091a o(boolean relativeRedirectsAllowed) {
            this.g = relativeRedirectsAllowed;
            return this;
        }

        public C0091a c(boolean circularRedirectsAllowed) {
            this.h = circularRedirectsAllowed;
            return this;
        }

        public C0091a k(int maxRedirects) {
            this.i = maxRedirects;
            return this;
        }

        public C0091a b(boolean authenticationEnabled) {
            this.j = authenticationEnabled;
            return this;
        }

        public C0091a r(Collection<String> targetPreferredAuthSchemes) {
            this.k = targetPreferredAuthSchemes;
            return this;
        }

        public C0091a m(Collection<String> proxyPreferredAuthSchemes) {
            this.l = proxyPreferredAuthSchemes;
            return this;
        }

        public C0091a e(int connectionRequestTimeout) {
            this.m = connectionRequestTimeout;
            return this;
        }

        public C0091a d(int connectTimeout) {
            this.n = connectTimeout;
            return this;
        }

        public C0091a p(int socketTimeout) {
            this.o = socketTimeout;
            return this;
        }

        @Deprecated
        public C0091a h(boolean decompressionEnabled) {
            this.p = decompressionEnabled;
            return this;
        }

        public C0091a f(boolean contentCompressionEnabled) {
            this.p = contentCompressionEnabled;
            return this;
        }

        public a a() {
            return new a(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o, this.p);
        }
    }
}
