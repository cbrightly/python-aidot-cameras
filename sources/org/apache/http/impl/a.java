package org.apache.http.impl;

import java.io.IOException;
import java.net.SocketTimeoutException;
import org.apache.http.h;
import org.apache.http.impl.entity.b;
import org.apache.http.impl.entity.d;
import org.apache.http.impl.io.n;
import org.apache.http.io.c;
import org.apache.http.io.e;
import org.apache.http.io.g;
import org.apache.http.io.i;
import org.apache.http.k;
import org.apache.http.message.t;
import org.apache.http.o;
import org.apache.http.params.HttpParams;
import org.apache.http.q;
import org.apache.http.r;

@Deprecated
/* compiled from: AbstractHttpClientConnection */
public abstract class a implements h {
    private final b c = i();
    private final org.apache.http.impl.entity.a d = g();
    private org.apache.http.io.h f = null;
    private g p0 = null;
    private i q = null;
    private org.apache.http.io.b x = null;
    private c<q> y = null;
    private e<o> z = null;

    /* access modifiers changed from: protected */
    public abstract void a();

    /* access modifiers changed from: protected */
    public abstract c<q> m(org.apache.http.io.h hVar, r rVar, HttpParams httpParams);

    /* access modifiers changed from: protected */
    public org.apache.http.impl.entity.a g() {
        return new org.apache.http.impl.entity.a(new org.apache.http.impl.entity.c());
    }

    /* access modifiers changed from: protected */
    public b i() {
        return new b(new d());
    }

    /* access modifiers changed from: protected */
    public r j() {
        return e.a;
    }

    /* access modifiers changed from: protected */
    public e<o> l(i buffer, HttpParams params) {
        return new n(buffer, (t) null, params);
    }

    /* access modifiers changed from: protected */
    public g c(g inTransportMetric, g outTransportMetric) {
        return new g(inTransportMetric, outTransportMetric);
    }

    /* access modifiers changed from: protected */
    public void o(org.apache.http.io.h inbuffer, i outbuffer, HttpParams params) {
        this.f = (org.apache.http.io.h) org.apache.http.util.a.i(inbuffer, "Input session buffer");
        this.q = (i) org.apache.http.util.a.i(outbuffer, "Output session buffer");
        if (inbuffer instanceof org.apache.http.io.b) {
            this.x = (org.apache.http.io.b) inbuffer;
        }
        this.y = m(inbuffer, j(), params);
        this.z = l(outbuffer, params);
        this.p0 = c(inbuffer.getMetrics(), outbuffer.getMetrics());
    }

    public boolean a0(int timeout) {
        a();
        try {
            return this.f.b(timeout);
        } catch (SocketTimeoutException e) {
            return false;
        }
    }

    public void E0(o request) {
        org.apache.http.util.a.i(request, "HTTP request");
        a();
        this.z.a(request);
        this.p0.a();
    }

    public void H(k request) {
        org.apache.http.util.a.i(request, "HTTP request");
        a();
        if (request.a() != null) {
            this.c.b(this.q, request, request.a());
        }
    }

    /* access modifiers changed from: protected */
    public void n() {
        this.q.flush();
    }

    public void flush() {
        a();
        n();
    }

    public q K0() {
        a();
        q response = this.y.a();
        if (response.j().getStatusCode() >= 200) {
            this.p0.b();
        }
        return response;
    }

    public void G0(q response) {
        org.apache.http.util.a.i(response, "HTTP response");
        a();
        response.l(this.d.a(this.f, response));
    }

    /* access modifiers changed from: protected */
    public boolean r() {
        org.apache.http.io.b bVar = this.x;
        return bVar != null && bVar.c();
    }

    public boolean l0() {
        if (!isOpen() || r()) {
            return true;
        }
        try {
            this.f.b(1);
            return r();
        } catch (SocketTimeoutException e) {
            return false;
        } catch (IOException e2) {
            return true;
        }
    }
}
