package org.apache.http.impl.io;

import org.apache.http.NoHttpResponseException;
import org.apache.http.config.c;
import org.apache.http.impl.e;
import org.apache.http.io.h;
import org.apache.http.message.u;
import org.apache.http.message.v;
import org.apache.http.protocol.f;
import org.apache.http.q;
import org.apache.http.r;
import org.apache.http.util.d;

/* compiled from: DefaultHttpResponseParser */
public class k extends a<q> {
    private final r g;
    private final d h;

    public k(h buffer, u lineParser, r responseFactory, c constraints) {
        super(buffer, lineParser, constraints);
        this.g = responseFactory != null ? responseFactory : e.a;
        this.h = new d(128);
    }

    /* access modifiers changed from: protected */
    /* renamed from: e */
    public q b(h sessionBuffer) {
        this.h.clear();
        if (sessionBuffer.a(this.h) != -1) {
            return this.g.a(this.d.c(this.h, new v(0, this.h.length())), (f) null);
        }
        throw new NoHttpResponseException("The target server failed to respond");
    }
}
