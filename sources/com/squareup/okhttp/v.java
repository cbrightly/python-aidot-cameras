package com.squareup.okhttp;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.squareup.okhttp.internal.http.i;
import com.squareup.okhttp.p;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: Request */
public final class v {
    /* access modifiers changed from: private */
    public final q a;
    /* access modifiers changed from: private */
    public final String b;
    /* access modifiers changed from: private */
    public final p c;
    /* access modifiers changed from: private */
    public final w d;
    /* access modifiers changed from: private */
    public final Object e;
    private volatile URI f;
    private volatile d g;

    private v(b builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c.e();
        w unused = builder.d;
        this.e = builder.e != null ? builder.e : this;
    }

    public q k() {
        return this.a;
    }

    public URI o() {
        try {
            URI result = this.f;
            if (result != null) {
                return result;
            }
            URI F = this.a.F();
            this.f = F;
            return F;
        } catch (IllegalStateException e2) {
            throw new IOException(e2.getMessage());
        }
    }

    public String p() {
        return this.a.toString();
    }

    public String m() {
        return this.b;
    }

    public p i() {
        return this.c;
    }

    public String h(String name) {
        return this.c.a(name);
    }

    public List<String> j(String name) {
        return this.c.h(name);
    }

    public w f() {
        return this.d;
    }

    public b n() {
        return new b();
    }

    public d g() {
        d result = this.g;
        if (result != null) {
            return result;
        }
        d k = d.k(this.c);
        this.g = k;
        return k;
    }

    public boolean l() {
        return this.a.r();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Request{method=");
        sb.append(this.b);
        sb.append(", url=");
        sb.append(this.a);
        sb.append(", tag=");
        Object obj = this.e;
        if (obj == this) {
            obj = null;
        }
        sb.append(obj);
        sb.append('}');
        return sb.toString();
    }

    /* compiled from: Request */
    public static class b {
        /* access modifiers changed from: private */
        public q a;
        /* access modifiers changed from: private */
        public String b;
        /* access modifiers changed from: private */
        public p.b c;
        /* access modifiers changed from: private */
        public w d;
        /* access modifiers changed from: private */
        public Object e;

        public b() {
            this.b = Constants.GET;
            this.c = new p.b();
        }

        private b(v request) {
            this.a = request.a;
            this.b = request.b;
            w unused = request.d;
            this.e = request.e;
            this.c = request.c.e();
        }

        public b m(q url) {
            if (url != null) {
                this.a = url;
                return this;
            }
            throw new IllegalArgumentException("url == null");
        }

        public b n(String url) {
            if (url != null) {
                if (url.regionMatches(true, 0, "ws:", 0, 3)) {
                    url = "http:" + url.substring(3);
                } else if (url.regionMatches(true, 0, "wss:", 0, 4)) {
                    url = "https:" + url.substring(4);
                }
                q parsed = q.u(url);
                if (parsed != null) {
                    return m(parsed);
                }
                throw new IllegalArgumentException("unexpected url: " + url);
            }
            throw new IllegalArgumentException("url == null");
        }

        public b i(String name, String value) {
            this.c.h(name, value);
            return this;
        }

        public b f(String name, String value) {
            this.c.b(name, value);
            return this;
        }

        public b l(String name) {
            this.c.g(name);
            return this;
        }

        public b j(p headers) {
            this.c = headers.e();
            return this;
        }

        public b h(d cacheControl) {
            String value = cacheControl.toString();
            if (value.isEmpty()) {
                return l(HttpHeaders.HEAD_KEY_CACHE_CONTROL);
            }
            return i(HttpHeaders.HEAD_KEY_CACHE_CONTROL, value);
        }

        public b k(String method, w body) {
            if (method == null || method.length() == 0) {
                throw new IllegalArgumentException("method == null || method.length() == 0");
            } else if (body != null && !i.b(method)) {
                throw new IllegalArgumentException("method " + method + " must not have a request body.");
            } else if (body != null || !i.d(method)) {
                this.b = method;
                return this;
            } else {
                throw new IllegalArgumentException("method " + method + " must have a request body.");
            }
        }

        public v g() {
            if (this.a != null) {
                return new v(this);
            }
            throw new IllegalStateException("url == null");
        }
    }
}
