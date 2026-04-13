package org.apache.http.impl.conn;

import org.apache.commons.logging.h;
import org.apache.http.NoHttpResponseException;
import org.apache.http.ProtocolException;
import org.apache.http.config.c;
import org.apache.http.impl.e;
import org.apache.http.impl.io.a;
import org.apache.http.message.u;
import org.apache.http.message.v;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.f;
import org.apache.http.q;
import org.apache.http.r;
import org.apache.http.util.d;

/* compiled from: DefaultHttpResponseParser */
public class k extends a<q> {
    private final org.apache.commons.logging.a g = h.n(getClass());
    private final r h;
    private final d i;

    @Deprecated
    public k(org.apache.http.io.h buffer, u parser, r responseFactory, HttpParams params) {
        super(buffer, parser, params);
        org.apache.http.util.a.i(responseFactory, "Response factory");
        this.h = responseFactory;
        this.i = new d(128);
    }

    public k(org.apache.http.io.h buffer, u lineParser, r responseFactory, c constraints) {
        super(buffer, lineParser, constraints);
        this.h = responseFactory != null ? responseFactory : e.a;
        this.i = new d(128);
    }

    /* access modifiers changed from: protected */
    /* renamed from: e */
    public q b(org.apache.http.io.h sessionBuffer) {
        int count = 0;
        while (true) {
            this.i.clear();
            int i2 = sessionBuffer.a(this.i);
            if (i2 == -1 && count == 0) {
                throw new NoHttpResponseException("The target server failed to respond");
            }
            v cursor = new v(0, this.i.length());
            if (this.d.a(this.i, cursor)) {
                return this.h.a(this.d.c(this.i, cursor), (f) null);
            } else if (i2 != -1 && !f(this.i, count)) {
                if (this.g.isDebugEnabled()) {
                    org.apache.commons.logging.a aVar = this.g;
                    aVar.debug("Garbage in response: " + this.i.toString());
                }
                count++;
            }
        }
        throw new ProtocolException("The server failed to respond with a valid HTTP response");
    }

    /* access modifiers changed from: protected */
    public boolean f(d line, int count) {
        return false;
    }
}
