package org.apache.http.impl.conn.tsccm;

import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.h;
import org.apache.http.conn.b;
import org.apache.http.conn.d;
import org.apache.http.conn.e;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.q;
import org.apache.http.conn.scheme.j;
import org.apache.http.impl.conn.i;
import org.apache.http.params.HttpParams;

@Deprecated
/* compiled from: ThreadSafeClientConnManager */
public class g implements b {
    /* access modifiers changed from: private */
    public final org.apache.commons.logging.a a = h.n(getClass());
    protected final j b;
    protected final a c;
    protected final d d;
    protected final d e;
    protected final ConnPerRouteBean f;

    @Deprecated
    public g(HttpParams params, j schreg) {
        org.apache.http.util.a.i(schreg, "Scheme registry");
        this.b = schreg;
        this.f = new ConnPerRouteBean();
        this.e = f(schreg);
        d dVar = (d) g(params);
        this.d = dVar;
        this.c = dVar;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        try {
            shutdown();
        } finally {
            super.finalize();
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public a g(HttpParams params) {
        return new d(this.e, params);
    }

    /* access modifiers changed from: protected */
    public d f(j schreg) {
        return new i(schreg);
    }

    public j e() {
        return this.b;
    }

    public e c(org.apache.http.conn.routing.b route, Object state) {
        return new a(this.d.q(route, state), route);
    }

    /* compiled from: ThreadSafeClientConnManager */
    public class a implements e {
        final /* synthetic */ e a;
        final /* synthetic */ org.apache.http.conn.routing.b b;

        a(e eVar, org.apache.http.conn.routing.b bVar) {
            this.a = eVar;
            this.b = bVar;
        }

        public void a() {
            this.a.a();
        }

        public q b(long timeout, TimeUnit tunit) {
            org.apache.http.util.a.i(this.b, "Route");
            if (g.this.a.isDebugEnabled()) {
                org.apache.commons.logging.a b2 = g.this.a;
                b2.debug("Get connection: " + this.b + ", timeout = " + timeout);
            }
            return new c(g.this, this.a.b(timeout, tunit));
        }
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:34:0x0076=Splitter:B:34:0x0076, B:19:0x0038=Splitter:B:19:0x0038} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void d(org.apache.http.conn.q r11, long r12, java.util.concurrent.TimeUnit r14) {
        /*
            r10 = this;
            boolean r0 = r11 instanceof org.apache.http.impl.conn.tsccm.c
            java.lang.String r1 = "Connection class mismatch, connection not obtained from this manager"
            org.apache.http.util.a.a(r0, r1)
            r0 = r11
            org.apache.http.impl.conn.tsccm.c r0 = (org.apache.http.impl.conn.tsccm.c) r0
            org.apache.http.impl.conn.b r1 = r0.r()
            if (r1 == 0) goto L_0x001e
            org.apache.http.conn.b r1 = r0.j()
            if (r1 != r10) goto L_0x0018
            r1 = 1
            goto L_0x0019
        L_0x0018:
            r1 = 0
        L_0x0019:
            java.lang.String r2 = "Connection not obtained from this manager"
            org.apache.http.util.b.a(r1, r2)
        L_0x001e:
            monitor-enter(r0)
            org.apache.http.impl.conn.b r1 = r0.r()     // Catch:{ all -> 0x00c8 }
            org.apache.http.impl.conn.tsccm.b r1 = (org.apache.http.impl.conn.tsccm.b) r1     // Catch:{ all -> 0x00c8 }
            if (r1 != 0) goto L_0x0029
            monitor-exit(r0)     // Catch:{ all -> 0x00c8 }
            return
        L_0x0029:
            boolean r2 = r0.isOpen()     // Catch:{ IOException -> 0x0066 }
            if (r2 == 0) goto L_0x0038
            boolean r2 = r0.m()     // Catch:{ IOException -> 0x0066 }
            if (r2 != 0) goto L_0x0038
            r0.shutdown()     // Catch:{ IOException -> 0x0066 }
        L_0x0038:
            boolean r2 = r0.m()     // Catch:{ all -> 0x00c8 }
            r8 = r2
            org.apache.commons.logging.a r2 = r10.a     // Catch:{ all -> 0x00c8 }
            boolean r2 = r2.isDebugEnabled()     // Catch:{ all -> 0x00c8 }
            if (r2 == 0) goto L_0x0056
            if (r8 == 0) goto L_0x004f
            org.apache.commons.logging.a r2 = r10.a     // Catch:{ all -> 0x00c8 }
            java.lang.String r3 = "Released connection is reusable."
            r2.debug(r3)     // Catch:{ all -> 0x00c8 }
            goto L_0x0056
        L_0x004f:
            org.apache.commons.logging.a r2 = r10.a     // Catch:{ all -> 0x00c8 }
            java.lang.String r3 = "Released connection is not reusable."
            r2.debug(r3)     // Catch:{ all -> 0x00c8 }
        L_0x0056:
            r0.i()     // Catch:{ all -> 0x00c8 }
            org.apache.http.impl.conn.tsccm.d r2 = r10.d     // Catch:{ all -> 0x00c8 }
        L_0x005b:
            r3 = r1
            r4 = r8
            r5 = r12
            r7 = r14
            r2.j(r3, r4, r5, r7)     // Catch:{ all -> 0x00c8 }
            goto L_0x009a
        L_0x0063:
            r2 = move-exception
            r8 = r2
            goto L_0x009c
        L_0x0066:
            r2 = move-exception
            org.apache.commons.logging.a r3 = r10.a     // Catch:{ all -> 0x0063 }
            boolean r3 = r3.isDebugEnabled()     // Catch:{ all -> 0x0063 }
            if (r3 == 0) goto L_0x0076
            org.apache.commons.logging.a r3 = r10.a     // Catch:{ all -> 0x0063 }
            java.lang.String r4 = "Exception shutting down released connection."
            r3.debug(r4, r2)     // Catch:{ all -> 0x0063 }
        L_0x0076:
            boolean r2 = r0.m()     // Catch:{ all -> 0x00c8 }
            r8 = r2
            org.apache.commons.logging.a r2 = r10.a     // Catch:{ all -> 0x00c8 }
            boolean r2 = r2.isDebugEnabled()     // Catch:{ all -> 0x00c8 }
            if (r2 == 0) goto L_0x0094
            if (r8 == 0) goto L_0x008d
            org.apache.commons.logging.a r2 = r10.a     // Catch:{ all -> 0x00c8 }
            java.lang.String r3 = "Released connection is reusable."
            r2.debug(r3)     // Catch:{ all -> 0x00c8 }
            goto L_0x0094
        L_0x008d:
            org.apache.commons.logging.a r2 = r10.a     // Catch:{ all -> 0x00c8 }
            java.lang.String r3 = "Released connection is not reusable."
            r2.debug(r3)     // Catch:{ all -> 0x00c8 }
        L_0x0094:
            r0.i()     // Catch:{ all -> 0x00c8 }
            org.apache.http.impl.conn.tsccm.d r2 = r10.d     // Catch:{ all -> 0x00c8 }
            goto L_0x005b
        L_0x009a:
            monitor-exit(r0)     // Catch:{ all -> 0x00c8 }
            return
        L_0x009c:
            boolean r2 = r0.m()     // Catch:{ all -> 0x00c8 }
            r9 = r2
            org.apache.commons.logging.a r2 = r10.a     // Catch:{ all -> 0x00c8 }
            boolean r2 = r2.isDebugEnabled()     // Catch:{ all -> 0x00c8 }
            if (r2 == 0) goto L_0x00ba
            if (r9 == 0) goto L_0x00b3
            org.apache.commons.logging.a r2 = r10.a     // Catch:{ all -> 0x00c8 }
            java.lang.String r3 = "Released connection is reusable."
            r2.debug(r3)     // Catch:{ all -> 0x00c8 }
            goto L_0x00ba
        L_0x00b3:
            org.apache.commons.logging.a r2 = r10.a     // Catch:{ all -> 0x00c8 }
            java.lang.String r3 = "Released connection is not reusable."
            r2.debug(r3)     // Catch:{ all -> 0x00c8 }
        L_0x00ba:
            r0.i()     // Catch:{ all -> 0x00c8 }
            org.apache.http.impl.conn.tsccm.d r2 = r10.d     // Catch:{ all -> 0x00c8 }
            r3 = r1
            r4 = r9
            r5 = r12
            r7 = r14
            r2.j(r3, r4, r5, r7)     // Catch:{ all -> 0x00c8 }
            throw r8     // Catch:{ all -> 0x00c8 }
        L_0x00c8:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00c8 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.conn.tsccm.g.d(org.apache.http.conn.q, long, java.util.concurrent.TimeUnit):void");
    }

    public void shutdown() {
        this.a.debug("Shutting down");
        this.d.r();
    }

    public void a(long idleTimeout, TimeUnit tunit) {
        if (this.a.isDebugEnabled()) {
            org.apache.commons.logging.a aVar = this.a;
            aVar.debug("Closing connections idle longer than " + idleTimeout + " " + tunit);
        }
        this.d.c(idleTimeout, tunit);
    }
}
