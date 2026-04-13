package okhttp3;

import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.net.Proxy;
import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.TypeCastException;
import kotlin.collections.v;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okhttp3.e;
import okhttp3.internal.connection.i;
import okhttp3.internal.platform.h;
import okhttp3.internal.tls.c;
import okhttp3.internal.ws.d;
import okhttp3.r;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: OkHttpClient.kt */
public class z implements Cloneable, e.a {
    /* access modifiers changed from: private */
    @NotNull
    public static final List<a0> c = okhttp3.internal.b.t(a0.HTTP_2, a0.HTTP_1_1);
    /* access modifiers changed from: private */
    @NotNull
    public static final List<l> d = okhttp3.internal.b.t(l.d, l.f);
    public static final b f = new b((DefaultConstructorMarker) null);
    @Nullable
    private final Proxy A4;
    @NotNull
    private final ProxySelector B4;
    @NotNull
    private final b C4;
    @NotNull
    private final SocketFactory D4;
    /* access modifiers changed from: private */
    public final SSLSocketFactory E4;
    @Nullable
    private final X509TrustManager F4;
    @NotNull
    private final List<l> G4;
    @NotNull
    private final List<a0> H4;
    @NotNull
    private final HostnameVerifier I4;
    @NotNull
    private final g J4;
    @Nullable
    private final c K4;
    private final int L4;
    private final int M4;
    private final int N4;
    private final int O4;
    private final int P4;
    private final long Q4;
    @NotNull
    private final i R4;
    private final boolean a1;
    private final boolean a2;
    @NotNull
    private final r.c p0;
    @NotNull
    private final b p1;
    private final boolean p2;
    @NotNull
    private final n p3;
    @Nullable
    private final c p4;
    @NotNull
    private final p q;
    @NotNull
    private final k x;
    @NotNull
    private final List<w> y;
    @NotNull
    private final List<w> z;
    @NotNull
    private final q z4;

    public z(@NotNull a builder) {
        ProxySelector proxySelector;
        k.f(builder, "builder");
        this.q = builder.r();
        this.x = builder.o();
        this.y = okhttp3.internal.b.R(builder.x());
        this.z = okhttp3.internal.b.R(builder.z());
        this.p0 = builder.t();
        this.a1 = builder.G();
        this.p1 = builder.i();
        this.a2 = builder.u();
        this.p2 = builder.v();
        this.p3 = builder.q();
        this.p4 = builder.j();
        this.z4 = builder.s();
        this.A4 = builder.C();
        if (builder.C() != null) {
            proxySelector = okhttp3.internal.proxy.a.a;
        } else {
            proxySelector = builder.E();
            proxySelector = proxySelector == null ? ProxySelector.getDefault() : proxySelector;
            if (proxySelector == null) {
                proxySelector = okhttp3.internal.proxy.a.a;
            }
        }
        this.B4 = proxySelector;
        this.C4 = builder.D();
        this.D4 = builder.I();
        List<l> p = builder.p();
        this.G4 = p;
        this.H4 = builder.B();
        this.I4 = builder.w();
        this.L4 = builder.k();
        this.M4 = builder.n();
        this.N4 = builder.F();
        this.O4 = builder.K();
        this.P4 = builder.A();
        this.Q4 = builder.y();
        i H = builder.H();
        this.R4 = H == null ? new i() : H;
        boolean z2 = true;
        if (!(p instanceof Collection) || !p.isEmpty()) {
            Iterator<T> it = p.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (((l) it.next()).f()) {
                        z2 = false;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        if (z2) {
            this.E4 = null;
            this.K4 = null;
            this.F4 = null;
            this.J4 = g.a;
        } else if (builder.J() != null) {
            this.E4 = builder.J();
            c l = builder.l();
            if (l == null) {
                k.n();
            }
            this.K4 = l;
            X509TrustManager L = builder.L();
            if (L == null) {
                k.n();
            }
            this.F4 = L;
            g m = builder.m();
            if (l == null) {
                k.n();
            }
            this.J4 = m.e(l);
        } else {
            h.a aVar = h.c;
            X509TrustManager p5 = aVar.g().p();
            this.F4 = p5;
            h g = aVar.g();
            if (p5 == null) {
                k.n();
            }
            this.E4 = g.o(p5);
            c.a aVar2 = c.a;
            if (p5 == null) {
                k.n();
            }
            c a3 = aVar2.a(p5);
            this.K4 = a3;
            g m2 = builder.m();
            if (a3 == null) {
                k.n();
            }
            this.J4 = m2.e(a3);
        }
        M();
    }

    @NotNull
    public Object clone() {
        return super.clone();
    }

    @NotNull
    public final p o() {
        return this.q;
    }

    @NotNull
    public final k l() {
        return this.x;
    }

    @NotNull
    public final List<w> v() {
        return this.y;
    }

    @NotNull
    public final List<w> y() {
        return this.z;
    }

    @NotNull
    public final r.c q() {
        return this.p0;
    }

    public final boolean I() {
        return this.a1;
    }

    @NotNull
    public final b e() {
        return this.p1;
    }

    public final boolean r() {
        return this.a2;
    }

    public final boolean s() {
        return this.p2;
    }

    @NotNull
    public final n n() {
        return this.p3;
    }

    @Nullable
    public final c f() {
        return this.p4;
    }

    @NotNull
    public final q p() {
        return this.z4;
    }

    @Nullable
    public final Proxy D() {
        return this.A4;
    }

    @NotNull
    public final ProxySelector G() {
        return this.B4;
    }

    @NotNull
    public final b F() {
        return this.C4;
    }

    @NotNull
    public final SocketFactory K() {
        return this.D4;
    }

    @NotNull
    public final SSLSocketFactory L() {
        SSLSocketFactory sSLSocketFactory = this.E4;
        if (sSLSocketFactory != null) {
            return sSLSocketFactory;
        }
        throw new IllegalStateException("CLEARTEXT-only client");
    }

    @Nullable
    public final X509TrustManager O() {
        return this.F4;
    }

    @NotNull
    public final List<l> m() {
        return this.G4;
    }

    @NotNull
    public final List<a0> C() {
        return this.H4;
    }

    @NotNull
    public final HostnameVerifier u() {
        return this.I4;
    }

    @NotNull
    public final g j() {
        return this.J4;
    }

    @Nullable
    public final c i() {
        return this.K4;
    }

    public final int h() {
        return this.L4;
    }

    public final int k() {
        return this.M4;
    }

    public final int H() {
        return this.N4;
    }

    public final int N() {
        return this.O4;
    }

    public final int B() {
        return this.P4;
    }

    public final long w() {
        return this.Q4;
    }

    @NotNull
    public final i t() {
        return this.R4;
    }

    public z() {
        this(new a());
    }

    private final void M() {
        Iterable $this$none$iv;
        List<w> list = this.y;
        if (list != null) {
            boolean z2 = true;
            if (!list.contains((Object) null)) {
                List<w> list2 = this.z;
                if (list2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<okhttp3.Interceptor?>");
                } else if (!list2.contains((Object) null)) {
                    List<l> list3 = this.G4;
                    if (!(list3 instanceof Collection) || !list3.isEmpty()) {
                        Iterator<T> it = list3.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                if (((l) it.next()).f()) {
                                    $this$none$iv = null;
                                    break;
                                }
                            } else {
                                $this$none$iv = 1;
                                break;
                            }
                        }
                    } else {
                        $this$none$iv = 1;
                    }
                    if ($this$none$iv != null) {
                        if (this.E4 == null) {
                            if (this.K4 == null) {
                                if (this.F4 != null) {
                                    z2 = false;
                                }
                                if (!z2) {
                                    throw new IllegalStateException("Check failed.".toString());
                                } else if (!k.a(this.J4, g.a)) {
                                    throw new IllegalStateException("Check failed.".toString());
                                }
                            } else {
                                throw new IllegalStateException("Check failed.".toString());
                            }
                        } else {
                            throw new IllegalStateException("Check failed.".toString());
                        }
                    } else if (this.E4 == null) {
                        throw new IllegalStateException("sslSocketFactory == null".toString());
                    } else if (this.K4 == null) {
                        throw new IllegalStateException("certificateChainCleaner == null".toString());
                    } else if (this.F4 == null) {
                        throw new IllegalStateException("x509TrustManager == null".toString());
                    }
                } else {
                    throw new IllegalStateException(("Null network interceptor: " + this.z).toString());
                }
            } else {
                throw new IllegalStateException(("Null interceptor: " + this.y).toString());
            }
        } else {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<okhttp3.Interceptor?>");
        }
    }

    @NotNull
    public e a(@NotNull b0 request) {
        k.f(request, Progress.REQUEST);
        return new okhttp3.internal.connection.e(this, request, false);
    }

    @NotNull
    public h0 A(@NotNull b0 request, @NotNull i0 listener) {
        k.f(request, Progress.REQUEST);
        k.f(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        d dVar = new d(okhttp3.internal.concurrent.e.a, request, listener, new Random(), (long) this.P4, (okhttp3.internal.ws.e) null, this.Q4);
        dVar.o(this);
        return dVar;
    }

    @NotNull
    public a z() {
        return new a(this);
    }

    /* compiled from: OkHttpClient.kt */
    public static final class a {
        private int A;
        private int B;
        private long C;
        @Nullable
        private i D;
        @NotNull
        private p a;
        @NotNull
        private k b;
        @NotNull
        private final List<w> c;
        @NotNull
        private final List<w> d;
        @NotNull
        private r.c e;
        private boolean f;
        @NotNull
        private b g;
        private boolean h;
        private boolean i;
        @NotNull
        private n j;
        @Nullable
        private c k;
        @NotNull
        private q l;
        @Nullable
        private Proxy m;
        @Nullable
        private ProxySelector n;
        @NotNull
        private b o;
        @NotNull
        private SocketFactory p;
        @Nullable
        private SSLSocketFactory q;
        @Nullable
        private X509TrustManager r;
        @NotNull
        private List<l> s;
        @NotNull
        private List<? extends a0> t;
        @NotNull
        private HostnameVerifier u;
        @NotNull
        private g v;
        @Nullable
        private c w;
        private int x;
        private int y;
        private int z;

        public a() {
            this.a = new p();
            this.b = new k();
            this.c = new ArrayList();
            this.d = new ArrayList();
            this.e = okhttp3.internal.b.e(r.a);
            this.f = true;
            b bVar = b.a;
            this.g = bVar;
            this.h = true;
            this.i = true;
            this.j = n.a;
            this.l = q.a;
            this.o = bVar;
            SocketFactory socketFactory = SocketFactory.getDefault();
            k.b(socketFactory, "SocketFactory.getDefault()");
            this.p = socketFactory;
            b bVar2 = z.f;
            this.s = bVar2.a();
            this.t = bVar2.b();
            this.u = okhttp3.internal.tls.d.a;
            this.v = g.a;
            this.y = 10000;
            this.z = 10000;
            this.A = 10000;
            this.C = 1024;
        }

        @NotNull
        public final p r() {
            return this.a;
        }

        @NotNull
        public final k o() {
            return this.b;
        }

        @NotNull
        public final List<w> x() {
            return this.c;
        }

        @NotNull
        public final List<w> z() {
            return this.d;
        }

        @NotNull
        public final r.c t() {
            return this.e;
        }

        public final boolean G() {
            return this.f;
        }

        @NotNull
        public final b i() {
            return this.g;
        }

        public final boolean u() {
            return this.h;
        }

        public final boolean v() {
            return this.i;
        }

        @NotNull
        public final n q() {
            return this.j;
        }

        @Nullable
        public final c j() {
            return this.k;
        }

        @NotNull
        public final q s() {
            return this.l;
        }

        @Nullable
        public final Proxy C() {
            return this.m;
        }

        @Nullable
        public final ProxySelector E() {
            return this.n;
        }

        @NotNull
        public final b D() {
            return this.o;
        }

        @NotNull
        public final SocketFactory I() {
            return this.p;
        }

        @Nullable
        public final SSLSocketFactory J() {
            return this.q;
        }

        @Nullable
        public final X509TrustManager L() {
            return this.r;
        }

        @NotNull
        public final List<l> p() {
            return this.s;
        }

        @NotNull
        public final List<a0> B() {
            return this.t;
        }

        @NotNull
        public final HostnameVerifier w() {
            return this.u;
        }

        @NotNull
        public final g m() {
            return this.v;
        }

        @Nullable
        public final c l() {
            return this.w;
        }

        public final int k() {
            return this.x;
        }

        public final int n() {
            return this.y;
        }

        public final int F() {
            return this.z;
        }

        public final int K() {
            return this.A;
        }

        public final int A() {
            return this.B;
        }

        public final long y() {
            return this.C;
        }

        @Nullable
        public final i H() {
            return this.D;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public a(@NotNull z okHttpClient) {
            this();
            k.f(okHttpClient, "okHttpClient");
            this.a = okHttpClient.o();
            this.b = okHttpClient.l();
            v.x(this.c, okHttpClient.v());
            v.x(this.d, okHttpClient.y());
            this.e = okHttpClient.q();
            this.f = okHttpClient.I();
            this.g = okHttpClient.e();
            this.h = okHttpClient.r();
            this.i = okHttpClient.s();
            this.j = okHttpClient.n();
            this.k = okHttpClient.f();
            this.l = okHttpClient.p();
            this.m = okHttpClient.D();
            this.n = okHttpClient.G();
            this.o = okHttpClient.F();
            this.p = okHttpClient.K();
            this.q = okHttpClient.E4;
            this.r = okHttpClient.O();
            this.s = okHttpClient.m();
            this.t = okHttpClient.C();
            this.u = okHttpClient.u();
            this.v = okHttpClient.j();
            this.w = okHttpClient.i();
            this.x = okHttpClient.h();
            this.y = okHttpClient.k();
            this.z = okHttpClient.H();
            this.A = okHttpClient.N();
            this.B = okHttpClient.B();
            this.C = okHttpClient.w();
            this.D = okHttpClient.t();
        }

        @NotNull
        public final a f(@NotNull p dispatcher) {
            k.f(dispatcher, "dispatcher");
            this.a = dispatcher;
            return this;
        }

        @NotNull
        public final List<w> N() {
            return this.c;
        }

        @NotNull
        public final a a(@NotNull w interceptor) {
            k.f(interceptor, "interceptor");
            this.c.add(interceptor);
            return this;
        }

        @NotNull
        public final List<w> O() {
            return this.d;
        }

        @NotNull
        public final a b(@NotNull w interceptor) {
            k.f(interceptor, "interceptor");
            this.d.add(interceptor);
            return this;
        }

        @NotNull
        public final a g(@NotNull r eventListener) {
            k.f(eventListener, "eventListener");
            this.e = okhttp3.internal.b.e(eventListener);
            return this;
        }

        @NotNull
        public final a S(boolean retryOnConnectionFailure) {
            this.f = retryOnConnectionFailure;
            return this;
        }

        @NotNull
        public final a h(boolean followRedirects) {
            this.h = followRedirects;
            return this;
        }

        @NotNull
        public final a d(@Nullable c cache) {
            this.k = cache;
            return this;
        }

        @NotNull
        public final a Q(@Nullable Proxy proxy) {
            if (!k.a(proxy, this.m)) {
                this.D = null;
            }
            this.m = proxy;
            return this;
        }

        @NotNull
        public final a T(@NotNull SSLSocketFactory sslSocketFactory) {
            k.f(sslSocketFactory, "sslSocketFactory");
            if (!k.a(sslSocketFactory, this.q)) {
                this.D = null;
            }
            this.q = sslSocketFactory;
            h.a aVar = h.c;
            X509TrustManager q2 = aVar.g().q(sslSocketFactory);
            if (q2 != null) {
                this.r = q2;
                h g2 = aVar.g();
                X509TrustManager x509TrustManager = this.r;
                if (x509TrustManager == null) {
                    k.n();
                }
                this.w = g2.c(x509TrustManager);
                return this;
            }
            throw new IllegalStateException("Unable to extract the trust manager on " + aVar.g() + ", " + "sslSocketFactory is " + sslSocketFactory.getClass());
        }

        @NotNull
        public final a U(@NotNull SSLSocketFactory sslSocketFactory, @NotNull X509TrustManager trustManager) {
            k.f(sslSocketFactory, "sslSocketFactory");
            k.f(trustManager, "trustManager");
            if ((!k.a(sslSocketFactory, this.q)) || (!k.a(trustManager, this.r))) {
                this.D = null;
            }
            this.q = sslSocketFactory;
            this.w = c.a.a(trustManager);
            this.r = trustManager;
            return this;
        }

        @NotNull
        public final a P(@NotNull List<? extends a0> protocols) {
            k.f(protocols, "protocols");
            List protocolsCopy = y.F0(protocols);
            a0 a0Var = a0.H2_PRIOR_KNOWLEDGE;
            boolean z2 = false;
            if (protocolsCopy.contains(a0Var) || protocolsCopy.contains(a0.HTTP_1_1)) {
                if (!protocolsCopy.contains(a0Var) || protocolsCopy.size() <= 1) {
                    z2 = true;
                }
                if (!z2) {
                    throw new IllegalArgumentException(("protocols containing h2_prior_knowledge cannot use other protocols: " + protocolsCopy).toString());
                } else if (!(!protocolsCopy.contains(a0.HTTP_1_0))) {
                    throw new IllegalArgumentException(("protocols must not contain http/1.0: " + protocolsCopy).toString());
                } else if (!protocolsCopy.contains((Object) null)) {
                    protocolsCopy.remove(a0.SPDY_3);
                    if (!k.a(protocolsCopy, this.t)) {
                        this.D = null;
                    }
                    List<? extends a0> unmodifiableList = Collections.unmodifiableList(protocolsCopy);
                    k.b(unmodifiableList, "Collections.unmodifiableList(protocolsCopy)");
                    this.t = unmodifiableList;
                    return this;
                } else {
                    throw new IllegalArgumentException("protocols must not contain null".toString());
                }
            } else {
                throw new IllegalArgumentException(("protocols must contain h2_prior_knowledge or http/1.1: " + protocolsCopy).toString());
            }
        }

        @NotNull
        public final a M(@NotNull HostnameVerifier hostnameVerifier) {
            k.f(hostnameVerifier, "hostnameVerifier");
            if (!k.a(hostnameVerifier, this.u)) {
                this.D = null;
            }
            this.u = hostnameVerifier;
            return this;
        }

        @NotNull
        public final a e(long timeout, @NotNull TimeUnit unit) {
            k.f(unit, "unit");
            this.y = okhttp3.internal.b.h("timeout", timeout, unit);
            return this;
        }

        @NotNull
        public final a R(long timeout, @NotNull TimeUnit unit) {
            k.f(unit, "unit");
            this.z = okhttp3.internal.b.h("timeout", timeout, unit);
            return this;
        }

        @NotNull
        public final a V(long timeout, @NotNull TimeUnit unit) {
            k.f(unit, "unit");
            this.A = okhttp3.internal.b.h("timeout", timeout, unit);
            return this;
        }

        @NotNull
        public final z c() {
            return new z(this);
        }
    }

    /* compiled from: OkHttpClient.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final List<a0> b() {
            return z.c;
        }

        @NotNull
        public final List<l> a() {
            return z.d;
        }
    }
}
