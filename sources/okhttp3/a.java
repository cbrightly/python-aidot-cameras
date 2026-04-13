package okhttp3;

import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import java.util.Objects;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import kotlin.jvm.internal.k;
import okhttp3.internal.b;
import okhttp3.v;
import org.apache.http.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Address.kt */
public final class a {
    @NotNull
    private final v a;
    @NotNull
    private final List<a0> b;
    @NotNull
    private final List<l> c;
    @NotNull
    private final q d;
    @NotNull
    private final SocketFactory e;
    @Nullable
    private final SSLSocketFactory f;
    @Nullable
    private final HostnameVerifier g;
    @Nullable
    private final g h;
    @NotNull
    private final b i;
    @Nullable
    private final Proxy j;
    @NotNull
    private final ProxySelector k;

    public a(@NotNull String uriHost, int uriPort, @NotNull q dns, @NotNull SocketFactory socketFactory, @Nullable SSLSocketFactory sslSocketFactory, @Nullable HostnameVerifier hostnameVerifier, @Nullable g certificatePinner, @NotNull b proxyAuthenticator, @Nullable Proxy proxy, @NotNull List<? extends a0> protocols, @NotNull List<l> connectionSpecs, @NotNull ProxySelector proxySelector) {
        k.f(uriHost, "uriHost");
        k.f(dns, "dns");
        k.f(socketFactory, "socketFactory");
        k.f(proxyAuthenticator, "proxyAuthenticator");
        k.f(protocols, "protocols");
        k.f(connectionSpecs, "connectionSpecs");
        k.f(proxySelector, "proxySelector");
        this.d = dns;
        this.e = socketFactory;
        this.f = sslSocketFactory;
        this.g = hostnameVerifier;
        this.h = certificatePinner;
        this.i = proxyAuthenticator;
        this.j = proxy;
        this.k = proxySelector;
        this.a = new v.a().q(sslSocketFactory != null ? "https" : l.DEFAULT_SCHEME_NAME).g(uriHost).m(uriPort).c();
        this.b = b.R(protocols);
        this.c = b.R(connectionSpecs);
    }

    @NotNull
    public final q c() {
        return this.d;
    }

    @NotNull
    public final SocketFactory j() {
        return this.e;
    }

    @Nullable
    public final SSLSocketFactory k() {
        return this.f;
    }

    @Nullable
    public final HostnameVerifier e() {
        return this.g;
    }

    @Nullable
    public final g a() {
        return this.h;
    }

    @NotNull
    public final b h() {
        return this.i;
    }

    @Nullable
    public final Proxy g() {
        return this.j;
    }

    @NotNull
    public final ProxySelector i() {
        return this.k;
    }

    @NotNull
    public final v l() {
        return this.a;
    }

    @NotNull
    public final List<a0> f() {
        return this.b;
    }

    @NotNull
    public final List<l> b() {
        return this.c;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof a) && k.a(this.a, ((a) other).a) && d((a) other);
    }

    public int hashCode() {
        return (((((((((((((((((((17 * 31) + this.a.hashCode()) * 31) + this.d.hashCode()) * 31) + this.i.hashCode()) * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31) + this.k.hashCode()) * 31) + Objects.hashCode(this.j)) * 31) + Objects.hashCode(this.f)) * 31) + Objects.hashCode(this.g)) * 31) + Objects.hashCode(this.h);
    }

    public final boolean d(@NotNull a that) {
        k.f(that, "that");
        return k.a(this.d, that.d) && k.a(this.i, that.i) && k.a(this.b, that.b) && k.a(this.c, that.c) && k.a(this.k, that.k) && k.a(this.j, that.j) && k.a(this.f, that.f) && k.a(this.g, that.g) && k.a(this.h, that.h) && this.a.p() == that.a.p();
    }

    @NotNull
    public String toString() {
        Object obj;
        StringBuilder sb;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Address{");
        sb2.append(this.a.j());
        sb2.append(':');
        sb2.append(this.a.p());
        sb2.append(", ");
        if (this.j != null) {
            sb = new StringBuilder();
            sb.append("proxy=");
            obj = this.j;
        } else {
            sb = new StringBuilder();
            sb.append("proxySelector=");
            obj = this.k;
        }
        sb.append(obj);
        sb2.append(sb.toString());
        sb2.append("}");
        return sb2.toString();
    }
}
