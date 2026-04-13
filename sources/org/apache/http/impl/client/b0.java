package org.apache.http.impl.client;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.h;
import org.apache.http.HttpException;
import org.apache.http.auth.e;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.f;
import org.apache.http.client.g;
import org.apache.http.client.methods.c;
import org.apache.http.client.methods.d;
import org.apache.http.client.methods.n;
import org.apache.http.conn.l;
import org.apache.http.conn.q;
import org.apache.http.conn.scheme.j;
import org.apache.http.cookie.k;
import org.apache.http.impl.execchain.b;
import org.apache.http.o;
import org.apache.http.params.HttpParams;

/* compiled from: InternalHttpClient */
public class b0 extends i implements d {
    private final org.apache.http.client.config.a a1;
    private final org.apache.commons.logging.a c = h.n(getClass());
    private final b d;
    /* access modifiers changed from: private */
    public final l f;
    private final g p0;
    private final List<Closeable> p1;
    private final org.apache.http.conn.routing.d q;
    private final org.apache.http.config.b<k> x;
    private final org.apache.http.config.b<e> y;
    private final f z;

    public b0(b execChain, l connManager, org.apache.http.conn.routing.d routePlanner, org.apache.http.config.b<k> cookieSpecRegistry, org.apache.http.config.b<e> authSchemeRegistry, f cookieStore, g credentialsProvider, org.apache.http.client.config.a defaultConfig, List<Closeable> closeables) {
        org.apache.http.util.a.i(execChain, "HTTP client exec chain");
        org.apache.http.util.a.i(connManager, "HTTP connection manager");
        org.apache.http.util.a.i(routePlanner, "HTTP route planner");
        this.d = execChain;
        this.f = connManager;
        this.q = routePlanner;
        this.x = cookieSpecRegistry;
        this.y = authSchemeRegistry;
        this.z = cookieStore;
        this.p0 = credentialsProvider;
        this.a1 = defaultConfig;
        this.p1 = closeables;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: org.apache.http.l} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.apache.http.conn.routing.b c(org.apache.http.l r4, org.apache.http.o r5, org.apache.http.protocol.f r6) {
        /*
            r3 = this;
            r0 = r4
            if (r0 != 0) goto L_0x0010
            org.apache.http.params.HttpParams r1 = r5.getParams()
            java.lang.String r2 = "http.default-host"
            java.lang.Object r1 = r1.getParameter(r2)
            r0 = r1
            org.apache.http.l r0 = (org.apache.http.l) r0
        L_0x0010:
            org.apache.http.conn.routing.d r1 = r3.q
            org.apache.http.conn.routing.b r1 = r1.a(r0, r5, r6)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.client.b0.c(org.apache.http.l, org.apache.http.o, org.apache.http.protocol.f):org.apache.http.conn.routing.b");
    }

    private void g(org.apache.http.client.protocol.a context) {
        if (context.getAttribute("http.auth.target-scope") == null) {
            context.setAttribute("http.auth.target-scope", new org.apache.http.auth.h());
        }
        if (context.getAttribute("http.auth.proxy-scope") == null) {
            context.setAttribute("http.auth.proxy-scope", new org.apache.http.auth.h());
        }
        if (context.getAttribute("http.authscheme-registry") == null) {
            context.setAttribute("http.authscheme-registry", this.y);
        }
        if (context.getAttribute("http.cookiespec-registry") == null) {
            context.setAttribute("http.cookiespec-registry", this.x);
        }
        if (context.getAttribute("http.cookie-store") == null) {
            context.setAttribute("http.cookie-store", this.z);
        }
        if (context.getAttribute("http.auth.credentials-provider") == null) {
            context.setAttribute("http.auth.credentials-provider", this.p0);
        }
        if (context.getAttribute("http.request-config") == null) {
            context.setAttribute("http.request-config", this.a1);
        }
    }

    /* access modifiers changed from: protected */
    public c doExecute(org.apache.http.l target, o request, org.apache.http.protocol.f context) {
        org.apache.http.util.a.i(request, "HTTP request");
        org.apache.http.client.methods.g execAware = null;
        if (request instanceof org.apache.http.client.methods.g) {
            execAware = (org.apache.http.client.methods.g) request;
        }
        try {
            n wrapper = n.w(request, target);
            org.apache.http.client.protocol.a localcontext = org.apache.http.client.protocol.a.g(context != null ? context : new org.apache.http.protocol.a());
            org.apache.http.client.config.a config = null;
            if (request instanceof d) {
                config = ((d) request).getConfig();
            }
            if (config == null) {
                HttpParams params = request.getParams();
                if (!(params instanceof org.apache.http.params.b)) {
                    config = org.apache.http.client.params.a.b(params, this.a1);
                } else if (!((org.apache.http.params.b) params).getNames().isEmpty()) {
                    config = org.apache.http.client.params.a.b(params, this.a1);
                }
            }
            if (config != null) {
                localcontext.x(config);
            }
            g(localcontext);
            return this.d.a(c(target, wrapper, localcontext), wrapper, localcontext, execAware);
        } catch (HttpException httpException) {
            throw new ClientProtocolException((Throwable) httpException);
        }
    }

    public org.apache.http.client.config.a getConfig() {
        return this.a1;
    }

    public void close() {
        List<Closeable> list = this.p1;
        if (list != null) {
            for (Closeable closeable : list) {
                try {
                    closeable.close();
                } catch (IOException ex) {
                    this.c.error(ex.getMessage(), ex);
                }
            }
        }
    }

    public HttpParams getParams() {
        throw new UnsupportedOperationException();
    }

    /* compiled from: InternalHttpClient */
    public class a implements org.apache.http.conn.b {
        a() {
        }

        public void shutdown() {
            b0.this.f.shutdown();
        }

        public org.apache.http.conn.e c(org.apache.http.conn.routing.b route, Object state) {
            throw new UnsupportedOperationException();
        }

        public void d(q conn, long validDuration, TimeUnit timeUnit) {
            throw new UnsupportedOperationException();
        }

        public j e() {
            throw new UnsupportedOperationException();
        }

        public void a(long idletime, TimeUnit tunit) {
            b0.this.f.a(idletime, tunit);
        }
    }

    public org.apache.http.conn.b getConnectionManager() {
        return new a();
    }
}
