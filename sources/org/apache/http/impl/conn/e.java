package org.apache.http.impl.conn;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.logging.h;
import org.apache.http.conn.r;
import org.apache.http.conn.routing.b;
import org.apache.http.pool.a;
import org.apache.http.pool.d;

/* compiled from: CPool */
public class e extends a<b, r, f> {
    private static final AtomicLong m = new AtomicLong();
    private final org.apache.commons.logging.a n = h.n(e.class);
    private final long o;
    private final TimeUnit p;

    public e(org.apache.http.pool.b<b, r> connFactory, int defaultMaxPerRoute, int maxTotal, long timeToLive, TimeUnit tunit) {
        super(connFactory, defaultMaxPerRoute, maxTotal);
        this.o = timeToLive;
        this.p = tunit;
    }

    /* access modifiers changed from: protected */
    /* renamed from: y */
    public f g(b route, r conn) {
        return new f(this.n, Long.toString(m.getAndIncrement()), route, conn, this.o, this.p);
    }

    /* access modifiers changed from: protected */
    /* renamed from: z */
    public boolean x(f entry) {
        return !((r) entry.b()).l0();
    }

    /* access modifiers changed from: protected */
    public void h(d<b, r> callback) {
        super.h(callback);
    }
}
