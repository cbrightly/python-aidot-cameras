package org.apache.http.impl.conn;

import org.apache.http.conn.b;
import org.apache.http.conn.s;
import org.apache.http.l;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.f;

@Deprecated
/* compiled from: AbstractPooledConnAdapter */
public abstract class c extends a {
    protected volatile b y;

    protected c(b manager, b entry) {
        super(manager, entry.b);
        this.y = entry;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public b r() {
        return this.y;
    }

    /* access modifiers changed from: protected */
    public void o(b entry) {
        if (n() || entry == null) {
            throw new ConnectionShutdownException();
        }
    }

    /* access modifiers changed from: protected */
    public synchronized void i() {
        this.y = null;
        super.i();
    }

    public org.apache.http.conn.routing.b e() {
        b entry = r();
        o(entry);
        if (entry.e == null) {
            return null;
        }
        return entry.e.m();
    }

    public void R(org.apache.http.conn.routing.b route, f context, HttpParams params) {
        b entry = r();
        o(entry);
        entry.c(route, context, params);
    }

    public void A0(boolean secure, HttpParams params) {
        b entry = r();
        o(entry);
        entry.g(secure, params);
    }

    public void J0(l next, boolean secure, HttpParams params) {
        b entry = r();
        o(entry);
        entry.f(next, secure, params);
    }

    public void z0(f context, HttpParams params) {
        b entry = r();
        o(entry);
        entry.b(context, params);
    }

    public void close() {
        b entry = r();
        if (entry != null) {
            entry.e();
        }
        s conn = l();
        if (conn != null) {
            conn.close();
        }
    }

    public void shutdown() {
        b entry = r();
        if (entry != null) {
            entry.e();
        }
        s conn = l();
        if (conn != null) {
            conn.shutdown();
        }
    }

    public void y0(Object state) {
        b entry = r();
        o(entry);
        entry.d(state);
    }
}
