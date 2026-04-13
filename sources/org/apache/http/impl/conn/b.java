package org.apache.http.impl.conn;

import java.io.InterruptedIOException;
import java.net.Socket;
import org.apache.http.conn.d;
import org.apache.http.conn.routing.f;
import org.apache.http.conn.s;
import org.apache.http.l;
import org.apache.http.params.HttpParams;
import org.apache.http.util.a;

@Deprecated
/* compiled from: AbstractPoolEntry */
public abstract class b {
    protected final d a;
    protected final s b;
    protected volatile org.apache.http.conn.routing.b c;
    protected volatile Object d;
    protected volatile f e = null;

    protected b(d connOperator, org.apache.http.conn.routing.b route) {
        a.i(connOperator, "Connection operator");
        this.a = connOperator;
        this.b = connOperator.createConnection();
        this.c = route;
    }

    public Object a() {
        return this.d;
    }

    public void d(Object state) {
        this.d = state;
    }

    public void c(org.apache.http.conn.routing.b route, org.apache.http.protocol.f context, HttpParams params) {
        a.i(route, "Route");
        a.i(params, "HTTP parameters");
        if (this.e != null) {
            org.apache.http.util.b.a(!this.e.j(), "Connection already open");
        }
        this.e = new f(route);
        l proxy = route.c();
        this.a.a(this.b, proxy != null ? proxy : route.e(), route.getLocalAddress(), context, params);
        f localTracker = this.e;
        if (localTracker == null) {
            throw new InterruptedIOException("Request aborted");
        } else if (proxy == null) {
            localTracker.i(this.b.isSecure());
        } else {
            localTracker.h(proxy, this.b.isSecure());
        }
    }

    public void g(boolean secure, HttpParams params) {
        a.i(params, "HTTP parameters");
        org.apache.http.util.b.c(this.e, "Route tracker");
        org.apache.http.util.b.a(this.e.j(), "Connection not open");
        org.apache.http.util.b.a(!this.e.b(), "Connection is already tunnelled");
        this.b.e0((Socket) null, this.e.e(), secure, params);
        this.e.o(secure);
    }

    public void f(l next, boolean secure, HttpParams params) {
        a.i(next, "Next proxy");
        a.i(params, "Parameters");
        org.apache.http.util.b.c(this.e, "Route tracker");
        org.apache.http.util.b.a(this.e.j(), "Connection not open");
        this.b.e0((Socket) null, next, secure, params);
        this.e.n(next, secure);
    }

    public void b(org.apache.http.protocol.f context, HttpParams params) {
        a.i(params, "HTTP parameters");
        org.apache.http.util.b.c(this.e, "Route tracker");
        org.apache.http.util.b.a(this.e.j(), "Connection not open");
        org.apache.http.util.b.a(this.e.b(), "Protocol layering without a tunnel not supported");
        org.apache.http.util.b.a(!this.e.f(), "Multiple protocol layering not supported");
        this.a.b(this.b, this.e.e(), context, params);
        this.e.k(this.b.isSecure());
    }

    /* access modifiers changed from: protected */
    public void e() {
        this.e = null;
        this.d = null;
    }
}
