package org.apache.http.impl.conn;

import com.didichuxing.doraemonkit.kit.weaknetwork.WeakNetworkManager;
import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.logging.h;
import org.apache.http.config.d;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.i;
import org.apache.http.conn.l;
import org.apache.http.conn.m;
import org.apache.http.conn.n;
import org.apache.http.conn.r;
import org.apache.http.conn.t;
import org.apache.http.pool.e;
import org.apache.http.protocol.f;

/* compiled from: PoolingHttpClientConnectionManager */
public class a0 implements l, Closeable {
    private final org.apache.commons.logging.a c;
    private final b d;
    private final e f;
    private final m q;
    private final AtomicBoolean x;

    public a0(d<org.apache.http.conn.socket.a> socketFactoryRegistry, n<org.apache.http.conn.routing.b, r> connFactory, t schemePortResolver, i dnsResolver, long timeToLive, TimeUnit tunit) {
        this(new j(socketFactoryRegistry, schemePortResolver, dnsResolver), connFactory, timeToLive, tunit);
    }

    public a0(m httpClientConnectionOperator, n<org.apache.http.conn.routing.b, r> connFactory, long timeToLive, TimeUnit tunit) {
        this.c = h.n(getClass());
        b bVar = new b();
        this.d = bVar;
        e eVar = new e(new c(bVar, connFactory), 2, 20, timeToLive, tunit);
        this.f = eVar;
        eVar.v(WeakNetworkManager.DEFAULT_TIMEOUT_MILLIS);
        this.q = (m) org.apache.http.util.a.i(httpClientConnectionOperator, "HttpClientConnectionOperator");
        this.x = new AtomicBoolean(false);
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        try {
            shutdown();
        } finally {
            super.finalize();
        }
    }

    public void close() {
        shutdown();
    }

    private String n(org.apache.http.conn.routing.b route, Object state) {
        StringBuilder buf = new StringBuilder();
        buf.append("[route: ");
        buf.append(route);
        buf.append("]");
        if (state != null) {
            buf.append("[state: ");
            buf.append(state);
            buf.append("]");
        }
        return buf.toString();
    }

    private String r(org.apache.http.conn.routing.b route) {
        StringBuilder buf = new StringBuilder();
        e totals = this.f.m();
        e stats = this.f.l(route);
        buf.append("[total kept alive: ");
        buf.append(totals.getAvailable());
        buf.append("; ");
        buf.append("route allocated: ");
        buf.append(stats.getLeased() + stats.getAvailable());
        buf.append(" of ");
        buf.append(stats.getMax());
        buf.append("; ");
        buf.append("total allocated: ");
        buf.append(totals.getLeased() + totals.getAvailable());
        buf.append(" of ");
        buf.append(totals.getMax());
        buf.append("]");
        return buf.toString();
    }

    private String o(f entry) {
        StringBuilder buf = new StringBuilder();
        buf.append("[id: ");
        buf.append(entry.d());
        buf.append("]");
        buf.append("[route: ");
        buf.append(entry.e());
        buf.append("]");
        Object state = entry.f();
        if (state != null) {
            buf.append("[state: ");
            buf.append(state);
            buf.append("]");
        }
        return buf.toString();
    }

    public org.apache.http.conn.h c(org.apache.http.conn.routing.b route, Object state) {
        org.apache.http.util.a.i(route, "HTTP route");
        if (this.c.isDebugEnabled()) {
            org.apache.commons.logging.a aVar = this.c;
            aVar.debug("Connection request: " + n(route, state) + r(route));
        }
        return new a(this.f.n(route, state, (org.apache.http.concurrent.b) null));
    }

    /* compiled from: PoolingHttpClientConnectionManager */
    public class a implements org.apache.http.conn.h {
        final /* synthetic */ Future c;

        a(Future future) {
            this.c = future;
        }

        public boolean cancel() {
            return this.c.cancel(true);
        }

        public org.apache.http.h get(long timeout, TimeUnit tunit) {
            return a0.this.s(this.c, timeout, tunit);
        }
    }

    /* access modifiers changed from: protected */
    public org.apache.http.h s(Future<f> future, long timeout, TimeUnit tunit) {
        try {
            f entry = future.get(timeout, tunit);
            if (entry != null) {
                try {
                    if (!future.isCancelled()) {
                        org.apache.http.util.b.a(entry.b() != null, "Pool entry with no connection");
                        if (this.c.isDebugEnabled()) {
                            org.apache.commons.logging.a aVar = this.c;
                            aVar.debug("Connection leased: " + o(entry) + r((org.apache.http.conn.routing.b) entry.e()));
                        }
                        return g.n(entry);
                    }
                } catch (TimeoutException e) {
                    throw new ConnectionPoolTimeoutException("Timeout waiting for connection from pool");
                }
            }
            throw new InterruptedException();
        } catch (TimeoutException e2) {
            throw new ConnectionPoolTimeoutException("Timeout waiting for connection from pool");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00bf, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void l(org.apache.http.h r11, java.lang.Object r12, long r13, java.util.concurrent.TimeUnit r15) {
        /*
            r10 = this;
            java.lang.String r0 = "Managed connection"
            org.apache.http.util.a.i(r11, r0)
            monitor-enter(r11)
            org.apache.http.impl.conn.f r0 = org.apache.http.impl.conn.g.c(r11)     // Catch:{ all -> 0x0105 }
            if (r0 != 0) goto L_0x000e
            monitor-exit(r11)     // Catch:{ all -> 0x0105 }
            return
        L_0x000e:
            java.lang.Object r1 = r0.b()     // Catch:{ all -> 0x0105 }
            org.apache.http.conn.r r1 = (org.apache.http.conn.r) r1     // Catch:{ all -> 0x0105 }
            r2 = 1
            r3 = 0
            boolean r4 = r1.isOpen()     // Catch:{ all -> 0x00c0 }
            if (r4 == 0) goto L_0x007c
            if (r15 == 0) goto L_0x0020
            r4 = r15
            goto L_0x0022
        L_0x0020:
            java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ all -> 0x00c0 }
        L_0x0022:
            r0.j(r12)     // Catch:{ all -> 0x00c0 }
            r0.k(r13, r4)     // Catch:{ all -> 0x00c0 }
            org.apache.commons.logging.a r5 = r10.c     // Catch:{ all -> 0x00c0 }
            boolean r5 = r5.isDebugEnabled()     // Catch:{ all -> 0x00c0 }
            if (r5 == 0) goto L_0x007c
            r5 = 0
            int r5 = (r13 > r5 ? 1 : (r13 == r5 ? 0 : -1))
            if (r5 <= 0) goto L_0x0058
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c0 }
            r5.<init>()     // Catch:{ all -> 0x00c0 }
            java.lang.String r6 = "for "
            r5.append(r6)     // Catch:{ all -> 0x00c0 }
            long r6 = r4.toMillis(r13)     // Catch:{ all -> 0x00c0 }
            double r6 = (double) r6     // Catch:{ all -> 0x00c0 }
            r8 = 4652007308841189376(0x408f400000000000, double:1000.0)
            double r6 = r6 / r8
            r5.append(r6)     // Catch:{ all -> 0x00c0 }
            java.lang.String r6 = " seconds"
            r5.append(r6)     // Catch:{ all -> 0x00c0 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00c0 }
            goto L_0x005a
        L_0x0058:
            java.lang.String r5 = "indefinitely"
        L_0x005a:
            org.apache.commons.logging.a r6 = r10.c     // Catch:{ all -> 0x00c0 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c0 }
            r7.<init>()     // Catch:{ all -> 0x00c0 }
            java.lang.String r8 = "Connection "
            r7.append(r8)     // Catch:{ all -> 0x00c0 }
            java.lang.String r8 = r10.o(r0)     // Catch:{ all -> 0x00c0 }
            r7.append(r8)     // Catch:{ all -> 0x00c0 }
            java.lang.String r8 = " can be kept alive "
            r7.append(r8)     // Catch:{ all -> 0x00c0 }
            r7.append(r5)     // Catch:{ all -> 0x00c0 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x00c0 }
            r6.debug(r7)     // Catch:{ all -> 0x00c0 }
        L_0x007c:
            org.apache.http.impl.conn.e r4 = r10.f     // Catch:{ all -> 0x0105 }
            boolean r5 = r1.isOpen()     // Catch:{ all -> 0x0105 }
            if (r5 == 0) goto L_0x008b
            boolean r5 = r0.m()     // Catch:{ all -> 0x0105 }
            if (r5 == 0) goto L_0x008b
            goto L_0x008c
        L_0x008b:
            r2 = r3
        L_0x008c:
            r4.s(r0, r2)     // Catch:{ all -> 0x0105 }
            org.apache.commons.logging.a r2 = r10.c     // Catch:{ all -> 0x0105 }
            boolean r2 = r2.isDebugEnabled()     // Catch:{ all -> 0x0105 }
            if (r2 == 0) goto L_0x00be
            org.apache.commons.logging.a r2 = r10.c     // Catch:{ all -> 0x0105 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0105 }
            r3.<init>()     // Catch:{ all -> 0x0105 }
            java.lang.String r4 = "Connection released: "
            r3.append(r4)     // Catch:{ all -> 0x0105 }
            java.lang.String r4 = r10.o(r0)     // Catch:{ all -> 0x0105 }
            r3.append(r4)     // Catch:{ all -> 0x0105 }
            java.lang.Object r4 = r0.e()     // Catch:{ all -> 0x0105 }
            org.apache.http.conn.routing.b r4 = (org.apache.http.conn.routing.b) r4     // Catch:{ all -> 0x0105 }
            java.lang.String r4 = r10.r(r4)     // Catch:{ all -> 0x0105 }
            r3.append(r4)     // Catch:{ all -> 0x0105 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0105 }
            r2.debug(r3)     // Catch:{ all -> 0x0105 }
        L_0x00be:
            monitor-exit(r11)     // Catch:{ all -> 0x0105 }
            return
        L_0x00c0:
            r4 = move-exception
            org.apache.http.impl.conn.e r5 = r10.f     // Catch:{ all -> 0x0105 }
            boolean r6 = r1.isOpen()     // Catch:{ all -> 0x0105 }
            if (r6 == 0) goto L_0x00d0
            boolean r6 = r0.m()     // Catch:{ all -> 0x0105 }
            if (r6 == 0) goto L_0x00d0
            goto L_0x00d1
        L_0x00d0:
            r2 = r3
        L_0x00d1:
            r5.s(r0, r2)     // Catch:{ all -> 0x0105 }
            org.apache.commons.logging.a r2 = r10.c     // Catch:{ all -> 0x0105 }
            boolean r2 = r2.isDebugEnabled()     // Catch:{ all -> 0x0105 }
            if (r2 == 0) goto L_0x0103
            org.apache.commons.logging.a r2 = r10.c     // Catch:{ all -> 0x0105 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0105 }
            r3.<init>()     // Catch:{ all -> 0x0105 }
            java.lang.String r5 = "Connection released: "
            r3.append(r5)     // Catch:{ all -> 0x0105 }
            java.lang.String r5 = r10.o(r0)     // Catch:{ all -> 0x0105 }
            r3.append(r5)     // Catch:{ all -> 0x0105 }
            java.lang.Object r5 = r0.e()     // Catch:{ all -> 0x0105 }
            org.apache.http.conn.routing.b r5 = (org.apache.http.conn.routing.b) r5     // Catch:{ all -> 0x0105 }
            java.lang.String r5 = r10.r(r5)     // Catch:{ all -> 0x0105 }
            r3.append(r5)     // Catch:{ all -> 0x0105 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0105 }
            r2.debug(r3)     // Catch:{ all -> 0x0105 }
        L_0x0103:
            throw r4     // Catch:{ all -> 0x0105 }
        L_0x0105:
            r0 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x0105 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.conn.a0.l(org.apache.http.h, java.lang.Object, long, java.util.concurrent.TimeUnit):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0023, code lost:
        r0 = r12.e();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0027, code lost:
        r1 = r12.i();
        r2 = r10.d.d(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0031, code lost:
        if (r2 != null) goto L_0x0039;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0033, code lost:
        r2 = r10.d.c();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0039, code lost:
        if (r2 != null) goto L_0x003d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003b, code lost:
        r2 = org.apache.http.config.f.c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003d, code lost:
        r10.q.b(r4, r0, r1, r13, r2, r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0047, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001c, code lost:
        if (r12.c() == null) goto L_0x0023;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001e, code lost:
        r0 = r12.c();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void m(org.apache.http.h r11, org.apache.http.conn.routing.b r12, int r13, org.apache.http.protocol.f r14) {
        /*
            r10 = this;
            java.lang.String r0 = "Managed Connection"
            org.apache.http.util.a.i(r11, r0)
            java.lang.String r0 = "HTTP route"
            org.apache.http.util.a.i(r12, r0)
            monitor-enter(r11)
            r0 = 0
            org.apache.http.impl.conn.f r1 = org.apache.http.impl.conn.g.j(r11)     // Catch:{ all -> 0x0048 }
            java.lang.Object r2 = r1.b()     // Catch:{ all -> 0x0048 }
            r4 = r2
            org.apache.http.conn.r r4 = (org.apache.http.conn.r) r4     // Catch:{ all -> 0x0048 }
            monitor-exit(r11)     // Catch:{ all -> 0x004d }
            org.apache.http.l r0 = r12.c()
            if (r0 == 0) goto L_0x0023
            org.apache.http.l r0 = r12.c()
            goto L_0x0027
        L_0x0023:
            org.apache.http.l r0 = r12.e()
        L_0x0027:
            java.net.InetSocketAddress r1 = r12.i()
            org.apache.http.impl.conn.a0$b r2 = r10.d
            org.apache.http.config.f r2 = r2.d(r0)
            if (r2 != 0) goto L_0x0039
            org.apache.http.impl.conn.a0$b r3 = r10.d
            org.apache.http.config.f r2 = r3.c()
        L_0x0039:
            if (r2 != 0) goto L_0x003d
            org.apache.http.config.f r2 = org.apache.http.config.f.c
        L_0x003d:
            org.apache.http.conn.m r3 = r10.q
            r5 = r0
            r6 = r1
            r7 = r13
            r8 = r2
            r9 = r14
            r3.b(r4, r5, r6, r7, r8, r9)
            return
        L_0x0048:
            r1 = move-exception
            r4 = r0
            r0 = r1
        L_0x004b:
            monitor-exit(r11)     // Catch:{ all -> 0x004d }
            throw r0
        L_0x004d:
            r0 = move-exception
            goto L_0x004b
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.conn.a0.m(org.apache.http.h, org.apache.http.conn.routing.b, int, org.apache.http.protocol.f):void");
    }

    public void i(org.apache.http.h managedConn, org.apache.http.conn.routing.b route, f context) {
        org.apache.http.util.a.i(managedConn, "Managed Connection");
        org.apache.http.util.a.i(route, "HTTP route");
        synchronized (managedConn) {
            try {
                r conn = (r) g.j(managedConn).b();
                try {
                    this.q.a(conn, route.e(), context);
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    public void g(org.apache.http.h managedConn, org.apache.http.conn.routing.b route, f context) {
        org.apache.http.util.a.i(managedConn, "Managed Connection");
        org.apache.http.util.a.i(route, "HTTP route");
        synchronized (managedConn) {
            g.j(managedConn).n();
        }
    }

    public void shutdown() {
        if (this.x.compareAndSet(false, true)) {
            this.c.debug("Connection manager is shutting down");
            try {
                this.f.w();
            } catch (IOException ex) {
                this.c.debug("I/O exception shutting down connection manager", ex);
            }
            this.c.debug("Connection manager shut down");
        }
    }

    public void a(long idleTimeout, TimeUnit tunit) {
        if (this.c.isDebugEnabled()) {
            org.apache.commons.logging.a aVar = this.c;
            aVar.debug("Closing connections idle longer than " + idleTimeout + " " + tunit);
        }
        this.f.f(idleTimeout, tunit);
    }

    public void j() {
        this.c.debug("Closing expired connections");
        this.f.e();
    }

    public void x(int max) {
        this.f.u(max);
    }

    public void u(int max) {
        this.f.t(max);
    }

    public void v(org.apache.http.config.f defaultSocketConfig) {
        this.d.f(defaultSocketConfig);
    }

    public void t(org.apache.http.config.a defaultConnectionConfig) {
        this.d.e(defaultConnectionConfig);
    }

    /* compiled from: PoolingHttpClientConnectionManager */
    public static class b {
        private final Map<org.apache.http.l, org.apache.http.config.f> a = new ConcurrentHashMap();
        private final Map<org.apache.http.l, org.apache.http.config.a> b = new ConcurrentHashMap();
        private volatile org.apache.http.config.f c;
        private volatile org.apache.http.config.a d;

        b() {
        }

        public org.apache.http.config.f c() {
            return this.c;
        }

        public void f(org.apache.http.config.f defaultSocketConfig) {
            this.c = defaultSocketConfig;
        }

        public org.apache.http.config.a b() {
            return this.d;
        }

        public void e(org.apache.http.config.a defaultConnectionConfig) {
            this.d = defaultConnectionConfig;
        }

        public org.apache.http.config.f d(org.apache.http.l host) {
            return this.a.get(host);
        }

        public org.apache.http.config.a a(org.apache.http.l host) {
            return this.b.get(host);
        }
    }

    /* compiled from: PoolingHttpClientConnectionManager */
    public static class c implements org.apache.http.pool.b<org.apache.http.conn.routing.b, r> {
        private final b a;
        private final n<org.apache.http.conn.routing.b, r> b;

        c(b configData, n<org.apache.http.conn.routing.b, r> connFactory) {
            this.a = configData != null ? configData : new b();
            this.b = connFactory != null ? connFactory : z.b;
        }

        /* renamed from: a */
        public r create(org.apache.http.conn.routing.b route) {
            org.apache.http.config.a config = null;
            if (route.c() != null) {
                config = this.a.a(route.c());
            }
            if (config == null) {
                config = this.a.a(route.e());
            }
            if (config == null) {
                config = this.a.b();
            }
            if (config == null) {
                config = org.apache.http.config.a.c;
            }
            return this.b.a(route, config);
        }
    }
}
