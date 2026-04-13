package com.squareup.okhttp;

import com.squareup.okhttp.internal.http.k;
import com.squareup.okhttp.p;
import java.util.Collections;
import java.util.List;
import org.glassfish.tyrus.spi.UpgradeResponse;

/* compiled from: Response */
public final class x {
    /* access modifiers changed from: private */
    public final v a;
    /* access modifiers changed from: private */
    public final u b;
    /* access modifiers changed from: private */
    public final int c;
    /* access modifiers changed from: private */
    public final String d;
    /* access modifiers changed from: private */
    public final o e;
    /* access modifiers changed from: private */
    public final p f;
    /* access modifiers changed from: private */
    public final y g;
    /* access modifiers changed from: private */
    public x h;
    /* access modifiers changed from: private */
    public x i;
    /* access modifiers changed from: private */
    public final x j;
    private volatile d k;

    private x(b builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
        this.e = builder.e;
        this.f = builder.f.e();
        this.g = builder.g;
        this.h = builder.h;
        this.i = builder.i;
        this.j = builder.j;
    }

    public v x() {
        return this.a;
    }

    public u w() {
        return this.b;
    }

    public int o() {
        return this.c;
    }

    public String t() {
        return this.d;
    }

    public o p() {
        return this.e;
    }

    public String q(String name) {
        return r(name, (String) null);
    }

    public String r(String name, String defaultValue) {
        String result = this.f.a(name);
        return result != null ? result : defaultValue;
    }

    public p s() {
        return this.f;
    }

    public y k() {
        return this.g;
    }

    public b v() {
        return new b();
    }

    public x u() {
        return this.h;
    }

    public x m() {
        return this.i;
    }

    public List<g> n() {
        String responseField;
        int i2 = this.c;
        if (i2 == 401) {
            responseField = UpgradeResponse.WWW_AUTHENTICATE;
        } else if (i2 != 407) {
            return Collections.emptyList();
        } else {
            responseField = "Proxy-Authenticate";
        }
        return k.i(s(), responseField);
    }

    public d l() {
        d result = this.k;
        if (result != null) {
            return result;
        }
        d k2 = d.k(this.f);
        this.k = k2;
        return k2;
    }

    public String toString() {
        return "Response{protocol=" + this.b + ", code=" + this.c + ", message=" + this.d + ", url=" + this.a.p() + '}';
    }

    /* compiled from: Response */
    public static class b {
        /* access modifiers changed from: private */
        public v a;
        /* access modifiers changed from: private */
        public u b;
        /* access modifiers changed from: private */
        public int c;
        /* access modifiers changed from: private */
        public String d;
        /* access modifiers changed from: private */
        public o e;
        /* access modifiers changed from: private */
        public p.b f;
        /* access modifiers changed from: private */
        public y g;
        /* access modifiers changed from: private */
        public x h;
        /* access modifiers changed from: private */
        public x i;
        /* access modifiers changed from: private */
        public x j;

        public b() {
            this.c = -1;
            this.f = new p.b();
        }

        private b(x response) {
            this.c = -1;
            this.a = response.a;
            this.b = response.b;
            this.c = response.c;
            this.d = response.d;
            this.e = response.e;
            this.f = response.f.e();
            this.g = response.g;
            this.h = response.h;
            this.i = response.i;
            this.j = response.j;
        }

        public b y(v request) {
            this.a = request;
            return this;
        }

        public b x(u protocol) {
            this.b = protocol;
            return this;
        }

        public b q(int code) {
            this.c = code;
            return this;
        }

        public b u(String message) {
            this.d = message;
            return this;
        }

        public b r(o handshake) {
            this.e = handshake;
            return this;
        }

        public b s(String name, String value) {
            this.f.h(name, value);
            return this;
        }

        public b k(String name, String value) {
            this.f.b(name, value);
            return this;
        }

        public b t(p headers) {
            this.f = headers.e();
            return this;
        }

        public b l(y body) {
            this.g = body;
            return this;
        }

        public b v(x networkResponse) {
            if (networkResponse != null) {
                p("networkResponse", networkResponse);
            }
            this.h = networkResponse;
            return this;
        }

        public b n(x cacheResponse) {
            if (cacheResponse != null) {
                p("cacheResponse", cacheResponse);
            }
            this.i = cacheResponse;
            return this;
        }

        private void p(String name, x response) {
            if (response.g != null) {
                throw new IllegalArgumentException(name + ".body != null");
            } else if (response.h != null) {
                throw new IllegalArgumentException(name + ".networkResponse != null");
            } else if (response.i != null) {
                throw new IllegalArgumentException(name + ".cacheResponse != null");
            } else if (response.j != null) {
                throw new IllegalArgumentException(name + ".priorResponse != null");
            }
        }

        public b w(x priorResponse) {
            if (priorResponse != null) {
                o(priorResponse);
            }
            this.j = priorResponse;
            return this;
        }

        private void o(x response) {
            if (response.g != null) {
                throw new IllegalArgumentException("priorResponse.body != null");
            }
        }

        public x m() {
            if (this.a == null) {
                throw new IllegalStateException("request == null");
            } else if (this.b == null) {
                throw new IllegalStateException("protocol == null");
            } else if (this.c >= 0) {
                return new x(this);
            } else {
                throw new IllegalStateException("code < 0: " + this.c);
            }
        }
    }
}
