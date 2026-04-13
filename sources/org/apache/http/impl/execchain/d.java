package org.apache.http.impl.execchain;

import org.apache.http.client.methods.c;
import org.apache.http.g;
import org.apache.http.j;
import org.apache.http.params.HttpParams;
import org.apache.http.q;
import org.apache.http.v;
import org.apache.http.y;

/* compiled from: HttpResponseProxy */
public class d implements c {
    private final q c;
    private final c d;

    public d(q original, c connHolder) {
        this.c = original;
        this.d = connHolder;
        i.h(original, connHolder);
    }

    public void close() {
        c cVar = this.d;
        if (cVar != null) {
            cVar.close();
        }
    }

    public y j() {
        return this.c.j();
    }

    public j a() {
        return this.c.a();
    }

    public void l(j entity) {
        this.c.l(entity);
    }

    public v getProtocolVersion() {
        return this.c.getProtocolVersion();
    }

    public org.apache.http.d[] c(String name) {
        return this.c.c(name);
    }

    public org.apache.http.d u(String name) {
        return this.c.u(name);
    }

    public org.apache.http.d[] v() {
        return this.c.v();
    }

    public void u0(org.apache.http.d[] headers) {
        this.c.u0(headers);
    }

    public void s(String name) {
        this.c.s(name);
    }

    public g i() {
        return this.c.i();
    }

    public g o(String name) {
        return this.c.o(name);
    }

    @Deprecated
    public HttpParams getParams() {
        return this.c.getParams();
    }

    @Deprecated
    public void z(HttpParams params) {
        this.c.z(params);
    }

    public String toString() {
        return "HttpResponseProxy{" + this.c + '}';
    }
}
