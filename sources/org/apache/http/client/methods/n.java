package org.apache.http.client.methods;

import com.amazonaws.http.HttpHeader;
import java.net.URI;
import org.apache.http.d;
import org.apache.http.j;
import org.apache.http.k;
import org.apache.http.l;
import org.apache.http.o;
import org.apache.http.params.HttpParams;
import org.apache.http.v;
import org.apache.http.x;

/* compiled from: HttpRequestWrapper */
public class n extends org.apache.http.message.a implements p {
    private final o f;
    private URI p0;
    private final l q;
    private final String x;
    private x y;
    private v z;

    private n(o request, l target) {
        o oVar = (o) org.apache.http.util.a.i(request, "HTTP request");
        this.f = oVar;
        this.q = target;
        this.z = oVar.r().getProtocolVersion();
        this.x = oVar.r().getMethod();
        if (request instanceof p) {
            this.p0 = ((p) request).t();
        } else {
            this.p0 = null;
        }
        u0(request.v());
    }

    public v getProtocolVersion() {
        v vVar = this.z;
        return vVar != null ? vVar : this.f.getProtocolVersion();
    }

    public URI t() {
        return this.p0;
    }

    public void k(URI uri) {
        this.p0 = uri;
        this.y = null;
    }

    public String getMethod() {
        return this.x;
    }

    public boolean n() {
        return false;
    }

    public x r() {
        String requestUri;
        if (this.y == null) {
            URI uri = this.p0;
            if (uri != null) {
                requestUri = uri.toASCIIString();
            } else {
                requestUri = this.f.r().getUri();
            }
            if (requestUri == null || requestUri.isEmpty()) {
                requestUri = "/";
            }
            this.y = new org.apache.http.message.n(this.x, requestUri, getProtocolVersion());
        }
        return this.y;
    }

    public o f() {
        return this.f;
    }

    public l h() {
        return this.q;
    }

    public String toString() {
        return r() + " " + this.c;
    }

    /* compiled from: HttpRequestWrapper */
    public static class b extends n implements k {
        private j a1;

        b(k request, l target) {
            super(request, target);
            this.a1 = request.a();
        }

        public j a() {
            return this.a1;
        }

        public void l(j entity) {
            this.a1 = entity;
        }

        public boolean m() {
            d expect = u(HttpHeader.EXPECT);
            return expect != null && "100-continue".equalsIgnoreCase(expect.getValue());
        }
    }

    public static n q(o request) {
        return w(request, (l) null);
    }

    public static n w(o request, l target) {
        org.apache.http.util.a.i(request, "HTTP request");
        if (request instanceof k) {
            return new b((k) request, target);
        }
        return new n(request, target);
    }

    @Deprecated
    public HttpParams getParams() {
        if (this.d == null) {
            this.d = this.f.getParams().copy();
        }
        return this.d;
    }
}
