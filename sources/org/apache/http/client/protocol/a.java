package org.apache.http.client.protocol;

import java.net.URI;
import java.util.List;
import org.apache.http.auth.h;
import org.apache.http.conn.routing.b;
import org.apache.http.conn.routing.e;
import org.apache.http.cookie.i;
import org.apache.http.cookie.k;
import org.apache.http.protocol.f;
import org.apache.http.protocol.g;

/* compiled from: HttpClientContext */
public class a extends g {
    public static a g(f context) {
        if (context instanceof a) {
            return (a) context;
        }
        return new a(context);
    }

    public a(f context) {
        super(context);
    }

    public a() {
    }

    public e o() {
        return (e) b("http.route", b.class);
    }

    public List<URI> r() {
        return (List) b("http.protocol.redirect-locations", List.class);
    }

    public org.apache.http.client.f m() {
        return (org.apache.http.client.f) b("http.cookie-store", org.apache.http.client.f.class);
    }

    public i k() {
        return (i) b("http.cookie-spec", i.class);
    }

    public org.apache.http.cookie.f j() {
        return (org.apache.http.cookie.f) b("http.cookie-origin", org.apache.http.cookie.f.class);
    }

    private <T> org.apache.http.config.b<T> p(String name, Class<T> cls) {
        return (org.apache.http.config.b) b(name, org.apache.http.config.b.class);
    }

    public org.apache.http.config.b<k> l() {
        return p("http.cookiespec-registry", k.class);
    }

    public org.apache.http.config.b<org.apache.http.auth.e> i() {
        return p("http.authscheme-registry", org.apache.http.auth.e.class);
    }

    public org.apache.http.client.g n() {
        return (org.apache.http.client.g) b("http.auth.credentials-provider", org.apache.http.client.g.class);
    }

    public void w(org.apache.http.client.g credentialsProvider) {
        setAttribute("http.auth.credentials-provider", credentialsProvider);
    }

    public org.apache.http.client.a h() {
        return (org.apache.http.client.a) b("http.auth.auth-cache", org.apache.http.client.a.class);
    }

    public void v(org.apache.http.client.a authCache) {
        setAttribute("http.auth.auth-cache", authCache);
    }

    public h t() {
        return (h) b("http.auth.target-scope", h.class);
    }

    public h q() {
        return (h) b("http.auth.proxy-scope", h.class);
    }

    public Object u() {
        return getAttribute("http.user-token");
    }

    public org.apache.http.client.config.a s() {
        org.apache.http.client.config.a config = (org.apache.http.client.config.a) b("http.request-config", org.apache.http.client.config.a.class);
        return config != null ? config : org.apache.http.client.config.a.c;
    }

    public void x(org.apache.http.client.config.a config) {
        setAttribute("http.request-config", config);
    }
}
