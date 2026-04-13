package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.logging.h;
import org.apache.http.conn.b;
import org.apache.http.conn.e;
import org.apache.http.conn.q;
import org.apache.http.conn.scheme.j;

@Deprecated
/* compiled from: BasicClientConnectionManager */
public class d implements b {
    private static final AtomicLong a = new AtomicLong();
    private final org.apache.commons.logging.a b = h.n(getClass());
    private final j c;
    private final org.apache.http.conn.d d;
    private r e;
    private y f;
    private volatile boolean g;

    public d(j schreg) {
        org.apache.http.util.a.i(schreg, "Scheme registry");
        this.c = schreg;
        this.d = f(schreg);
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        try {
            shutdown();
        } finally {
            super.finalize();
        }
    }

    public j e() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public org.apache.http.conn.d f(j schreg) {
        return new i(schreg);
    }

    /* compiled from: BasicClientConnectionManager */
    public class a implements e {
        final /* synthetic */ org.apache.http.conn.routing.b a;
        final /* synthetic */ Object b;

        a(org.apache.http.conn.routing.b bVar, Object obj) {
            this.a = bVar;
            this.b = obj;
        }

        public void a() {
        }

        public q b(long timeout, TimeUnit tunit) {
            return d.this.g(this.a, this.b);
        }
    }

    public final e c(org.apache.http.conn.routing.b route, Object state) {
        return new a(route, state);
    }

    private void b() {
        org.apache.http.util.b.a(!this.g, "Connection manager has been shut down");
    }

    /* access modifiers changed from: package-private */
    public q g(org.apache.http.conn.routing.b route, Object state) {
        y yVar;
        org.apache.http.util.a.i(route, "Route");
        synchronized (this) {
            b();
            if (this.b.isDebugEnabled()) {
                org.apache.commons.logging.a aVar = this.b;
                aVar.debug("Get connection for route " + route);
            }
            org.apache.http.util.b.a(this.f == null, "Invalid use of BasicClientConnManager: connection still allocated.\nMake sure to release the connection before allocating another one.");
            r rVar = this.e;
            if (rVar != null && !rVar.m().equals(route)) {
                this.e.a();
                this.e = null;
            }
            if (this.e == null) {
                this.e = new r(this.b, Long.toString(a.getAndIncrement()), route, this.d.createConnection(), 0, TimeUnit.MILLISECONDS);
            }
            if (this.e.i(System.currentTimeMillis())) {
                this.e.a();
                this.e.n().l();
            }
            yVar = new y(this, this.d, this.e);
            this.f = yVar;
        }
        return yVar;
    }

    private void h(org.apache.http.h conn) {
        try {
            conn.shutdown();
        } catch (IOException iox) {
            if (this.b.isDebugEnabled()) {
                this.b.debug("I/O exception shutting down connection", iox);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00bc, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void d(org.apache.http.conn.q r8, long r9, java.util.concurrent.TimeUnit r11) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof org.apache.http.impl.conn.y
            java.lang.String r1 = "Connection class mismatch, connection not obtained from this manager"
            org.apache.http.util.a.a(r0, r1)
            r0 = r8
            org.apache.http.impl.conn.y r0 = (org.apache.http.impl.conn.y) r0
            monitor-enter(r0)
            org.apache.commons.logging.a r1 = r7.b     // Catch:{ all -> 0x00d2 }
            boolean r1 = r1.isDebugEnabled()     // Catch:{ all -> 0x00d2 }
            if (r1 == 0) goto L_0x0029
            org.apache.commons.logging.a r1 = r7.b     // Catch:{ all -> 0x00d2 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d2 }
            r2.<init>()     // Catch:{ all -> 0x00d2 }
            java.lang.String r3 = "Releasing connection "
            r2.append(r3)     // Catch:{ all -> 0x00d2 }
            r2.append(r8)     // Catch:{ all -> 0x00d2 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00d2 }
            r1.debug(r2)     // Catch:{ all -> 0x00d2 }
        L_0x0029:
            org.apache.http.impl.conn.r r1 = r0.n()     // Catch:{ all -> 0x00d2 }
            if (r1 != 0) goto L_0x0031
            monitor-exit(r0)     // Catch:{ all -> 0x00d2 }
            return
        L_0x0031:
            org.apache.http.conn.b r1 = r0.m()     // Catch:{ all -> 0x00d2 }
            if (r1 != r7) goto L_0x0039
            r2 = 1
            goto L_0x003a
        L_0x0039:
            r2 = 0
        L_0x003a:
            java.lang.String r3 = "Connection not obtained from this manager"
            org.apache.http.util.b.a(r2, r3)     // Catch:{ all -> 0x00d2 }
            monitor-enter(r7)     // Catch:{ all -> 0x00d2 }
            boolean r2 = r7.g     // Catch:{ all -> 0x00cf }
            if (r2 == 0) goto L_0x004a
            r7.h(r0)     // Catch:{ all -> 0x00cf }
            monitor-exit(r7)     // Catch:{ all -> 0x00cf }
            monitor-exit(r0)     // Catch:{ all -> 0x00d2 }
            return
        L_0x004a:
            r2 = 0
            boolean r3 = r0.isOpen()     // Catch:{ all -> 0x00bd }
            if (r3 == 0) goto L_0x005a
            boolean r3 = r0.o()     // Catch:{ all -> 0x00bd }
            if (r3 != 0) goto L_0x005a
            r7.h(r0)     // Catch:{ all -> 0x00bd }
        L_0x005a:
            boolean r3 = r0.o()     // Catch:{ all -> 0x00bd }
            if (r3 == 0) goto L_0x00ab
            org.apache.http.impl.conn.r r3 = r7.e     // Catch:{ all -> 0x00bd }
            if (r11 == 0) goto L_0x0066
            r4 = r11
            goto L_0x0068
        L_0x0066:
            java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ all -> 0x00bd }
        L_0x0068:
            r3.k(r9, r4)     // Catch:{ all -> 0x00bd }
            org.apache.commons.logging.a r3 = r7.b     // Catch:{ all -> 0x00bd }
            boolean r3 = r3.isDebugEnabled()     // Catch:{ all -> 0x00bd }
            if (r3 == 0) goto L_0x00ab
            r3 = 0
            int r3 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r3 <= 0) goto L_0x0093
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00bd }
            r3.<init>()     // Catch:{ all -> 0x00bd }
            java.lang.String r4 = "for "
            r3.append(r4)     // Catch:{ all -> 0x00bd }
            r3.append(r9)     // Catch:{ all -> 0x00bd }
            java.lang.String r4 = " "
            r3.append(r4)     // Catch:{ all -> 0x00bd }
            r3.append(r11)     // Catch:{ all -> 0x00bd }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00bd }
            goto L_0x0095
        L_0x0093:
            java.lang.String r3 = "indefinitely"
        L_0x0095:
            org.apache.commons.logging.a r4 = r7.b     // Catch:{ all -> 0x00bd }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00bd }
            r5.<init>()     // Catch:{ all -> 0x00bd }
            java.lang.String r6 = "Connection can be kept alive "
            r5.append(r6)     // Catch:{ all -> 0x00bd }
            r5.append(r3)     // Catch:{ all -> 0x00bd }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00bd }
            r4.debug(r5)     // Catch:{ all -> 0x00bd }
        L_0x00ab:
            r0.a()     // Catch:{ all -> 0x00cf }
            r7.f = r2     // Catch:{ all -> 0x00cf }
            org.apache.http.impl.conn.r r3 = r7.e     // Catch:{ all -> 0x00cf }
            boolean r3 = r3.h()     // Catch:{ all -> 0x00cf }
            if (r3 == 0) goto L_0x00ba
            r7.e = r2     // Catch:{ all -> 0x00cf }
        L_0x00ba:
            monitor-exit(r7)     // Catch:{ all -> 0x00cf }
            monitor-exit(r0)     // Catch:{ all -> 0x00d2 }
            return
        L_0x00bd:
            r3 = move-exception
            r0.a()     // Catch:{ all -> 0x00cf }
            r7.f = r2     // Catch:{ all -> 0x00cf }
            org.apache.http.impl.conn.r r4 = r7.e     // Catch:{ all -> 0x00cf }
            boolean r4 = r4.h()     // Catch:{ all -> 0x00cf }
            if (r4 == 0) goto L_0x00cd
            r7.e = r2     // Catch:{ all -> 0x00cf }
        L_0x00cd:
            throw r3     // Catch:{ all -> 0x00cf }
        L_0x00cf:
            r2 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x00cf }
            throw r2     // Catch:{ all -> 0x00d2 }
        L_0x00d2:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00d2 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.conn.d.d(org.apache.http.conn.q, long, java.util.concurrent.TimeUnit):void");
    }

    public void a(long idletime, TimeUnit tunit) {
        org.apache.http.util.a.i(tunit, "Time unit");
        synchronized (this) {
            b();
            long time = tunit.toMillis(idletime);
            if (time < 0) {
                time = 0;
            }
            long deadline = System.currentTimeMillis() - time;
            r rVar = this.e;
            if (rVar != null && rVar.g() <= deadline) {
                this.e.a();
                this.e.n().l();
            }
        }
    }

    public void shutdown() {
        synchronized (this) {
            this.g = true;
            try {
                r rVar = this.e;
                if (rVar != null) {
                    rVar.a();
                }
            } finally {
                this.e = null;
                this.f = null;
            }
        }
    }
}
