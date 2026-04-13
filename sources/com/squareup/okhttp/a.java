package com.squareup.okhttp;

import com.squareup.okhttp.internal.j;
import com.squareup.okhttp.q;
import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.l;

/* compiled from: Address */
public final class a {
    final q a;
    final n b;
    final SocketFactory c;
    final b d;
    final List<u> e;
    final List<k> f;
    final ProxySelector g;
    final Proxy h;
    final SSLSocketFactory i;
    final HostnameVerifier j;
    final f k;

    public a(String uriHost, int uriPort, n dns, SocketFactory socketFactory, SSLSocketFactory sslSocketFactory, HostnameVerifier hostnameVerifier, f certificatePinner, b authenticator, Proxy proxy, List<u> protocols, List<k> connectionSpecs, ProxySelector proxySelector) {
        this.a = new q.b().u(sslSocketFactory != null ? "https" : l.DEFAULT_SCHEME_NAME).i(uriHost).p(uriPort).a();
        if (dns != null) {
            this.b = dns;
            if (socketFactory != null) {
                this.c = socketFactory;
                if (authenticator != null) {
                    this.d = authenticator;
                    if (protocols != null) {
                        this.e = j.j(protocols);
                        if (connectionSpecs != null) {
                            this.f = j.j(connectionSpecs);
                            if (proxySelector != null) {
                                this.g = proxySelector;
                                this.h = proxy;
                                this.i = sslSocketFactory;
                                this.j = hostnameVerifier;
                                this.k = certificatePinner;
                                return;
                            }
                            throw new IllegalArgumentException("proxySelector == null");
                        }
                        throw new IllegalArgumentException("connectionSpecs == null");
                    }
                    throw new IllegalArgumentException("protocols == null");
                }
                throw new IllegalArgumentException("authenticator == null");
            }
            throw new IllegalArgumentException("socketFactory == null");
        }
        throw new IllegalArgumentException("dns == null");
    }

    public q m() {
        return this.a;
    }

    @Deprecated
    public String k() {
        return this.a.q();
    }

    @Deprecated
    public int l() {
        return this.a.A();
    }

    public n d() {
        return this.b;
    }

    public SocketFactory i() {
        return this.c;
    }

    public b a() {
        return this.d;
    }

    public List<u> f() {
        return this.e;
    }

    public List<k> c() {
        return this.f;
    }

    public ProxySelector h() {
        return this.g;
    }

    public Proxy g() {
        return this.h;
    }

    public SSLSocketFactory j() {
        return this.i;
    }

    public HostnameVerifier e() {
        return this.j;
    }

    public f b() {
        return this.k;
    }

    public boolean equals(Object other) {
        if (!(other instanceof a)) {
            return false;
        }
        a that = (a) other;
        if (!this.a.equals(that.a) || !this.b.equals(that.b) || !this.d.equals(that.d) || !this.e.equals(that.e) || !this.f.equals(that.f) || !this.g.equals(that.g) || !j.h(this.h, that.h) || !j.h(this.i, that.i) || !j.h(this.j, that.j) || !j.h(this.k, that.k)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result = ((((((((((((17 * 31) + this.a.hashCode()) * 31) + this.b.hashCode()) * 31) + this.d.hashCode()) * 31) + this.e.hashCode()) * 31) + this.f.hashCode()) * 31) + this.g.hashCode()) * 31;
        Proxy proxy = this.h;
        int i2 = 0;
        int result2 = (result + (proxy != null ? proxy.hashCode() : 0)) * 31;
        SSLSocketFactory sSLSocketFactory = this.i;
        int result3 = (result2 + (sSLSocketFactory != null ? sSLSocketFactory.hashCode() : 0)) * 31;
        HostnameVerifier hostnameVerifier = this.j;
        int result4 = (result3 + (hostnameVerifier != null ? hostnameVerifier.hashCode() : 0)) * 31;
        f fVar = this.k;
        if (fVar != null) {
            i2 = fVar.hashCode();
        }
        return result4 + i2;
    }
}
