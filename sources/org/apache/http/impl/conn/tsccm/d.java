package org.apache.http.impl.conn.tsccm;

import com.meituan.robust.Constants;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import org.apache.commons.logging.h;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.routing.b;
import org.apache.http.conn.s;
import org.apache.http.params.HttpParams;

@Deprecated
/* compiled from: ConnPoolByRoute */
public class d extends a {
    private final org.apache.commons.logging.a e;
    /* access modifiers changed from: private */
    public final Lock f;
    protected final org.apache.http.conn.d g;
    protected final org.apache.http.conn.params.a h;
    protected final Set<b> i;
    protected final Queue<b> j;
    protected final Queue<h> k;
    protected final Map<b, f> l;
    private final long m;
    private final TimeUnit n;
    protected volatile boolean o;
    protected volatile int p;
    protected volatile int q;

    public d(org.apache.http.conn.d operator, org.apache.http.conn.params.a connPerRoute, int maxTotalConnections) {
        this(operator, connPerRoute, maxTotalConnections, -1, TimeUnit.MILLISECONDS);
    }

    public d(org.apache.http.conn.d operator, org.apache.http.conn.params.a connPerRoute, int maxTotalConnections, long connTTL, TimeUnit connTTLTimeUnit) {
        this.e = h.n(getClass());
        org.apache.http.util.a.i(operator, "Connection operator");
        org.apache.http.util.a.i(connPerRoute, "Connections per route");
        this.f = this.b;
        this.i = this.c;
        this.g = operator;
        this.h = connPerRoute;
        this.p = maxTotalConnections;
        this.j = e();
        this.k = g();
        this.l = f();
        this.m = connTTL;
        this.n = connTTLTimeUnit;
    }

    @Deprecated
    public d(org.apache.http.conn.d operator, HttpParams params) {
        this(operator, ConnManagerParams.getMaxConnectionsPerRoute(params), ConnManagerParams.getMaxTotalConnections(params));
    }

    /* access modifiers changed from: protected */
    public Queue<b> e() {
        return new LinkedList();
    }

    /* access modifiers changed from: protected */
    public Queue<h> g() {
        return new LinkedList();
    }

    /* access modifiers changed from: protected */
    public Map<b, f> f() {
        return new HashMap();
    }

    /* access modifiers changed from: protected */
    public f n(b route) {
        return new f(route, this.h);
    }

    /* access modifiers changed from: protected */
    public h o(Condition cond, f rospl) {
        return new h(cond, rospl);
    }

    private void b(b entry) {
        s conn = entry.h();
        if (conn != null) {
            try {
                conn.close();
            } catch (IOException ex) {
                this.e.debug("I/O error closing connection", ex);
            }
        }
    }

    /* access modifiers changed from: protected */
    public f m(b route, boolean create) {
        this.f.lock();
        try {
            f rospl = this.l.get(route);
            if (rospl == null && create) {
                rospl = n(route);
                this.l.put(route, rospl);
            }
            return rospl;
        } finally {
            this.f.unlock();
        }
    }

    public e q(b route, Object state) {
        return new a(new i(), route, state);
    }

    /* compiled from: ConnPoolByRoute */
    public class a implements e {
        final /* synthetic */ i a;
        final /* synthetic */ b b;
        final /* synthetic */ Object c;

        a(i iVar, b bVar, Object obj) {
            this.a = iVar;
            this.b = bVar;
            this.c = obj;
        }

        public void a() {
            d.this.f.lock();
            try {
                this.a.a();
            } finally {
                d.this.f.unlock();
            }
        }

        public b b(long timeout, TimeUnit tunit) {
            return d.this.k(this.b, this.c, timeout, tunit, this.a);
        }
    }

    /* access modifiers changed from: protected */
    public b k(b route, Object state, long timeout, TimeUnit tunit, i aborter) {
        Date deadline;
        b bVar = route;
        Object obj = state;
        long j2 = timeout;
        if (j2 > 0) {
            deadline = new Date(System.currentTimeMillis() + tunit.toMillis(j2));
        } else {
            TimeUnit timeUnit = tunit;
            deadline = null;
        }
        b entry = null;
        this.f.lock();
        boolean z = true;
        try {
            f rospl = m(bVar, true);
            h waitingThread = null;
            while (true) {
                if (entry != null) {
                    i iVar = aborter;
                    break;
                }
                boolean z2 = false;
                org.apache.http.util.b.a(!this.o ? z : false, "Connection pool shut down");
                if (this.e.isDebugEnabled()) {
                    this.e.debug(Constants.ARRAY_TYPE + bVar + "] total kept alive: " + this.j.size() + ", total issued: " + this.i.size() + ", total allocated: " + this.q + " out of " + this.p);
                }
                entry = l(rospl, obj);
                if (entry != null) {
                    i iVar2 = aborter;
                    break;
                }
                if (rospl.f() > 0) {
                    z2 = z;
                }
                boolean hasCapacity = z2;
                if (this.e.isDebugEnabled()) {
                    this.e.debug("Available capacity: " + rospl.f() + " out of " + rospl.g() + " [" + bVar + "][" + obj + "]");
                }
                if (hasCapacity && this.q < this.p) {
                    i iVar3 = aborter;
                    entry = d(rospl, this.g);
                    z = true;
                } else if (!hasCapacity || this.j.isEmpty()) {
                    z = true;
                    if (this.e.isDebugEnabled()) {
                        this.e.debug("Need to wait for connection [" + bVar + "][" + obj + "]");
                    }
                    if (waitingThread == null) {
                        waitingThread = o(this.f.newCondition(), rospl);
                        try {
                            aborter.b(waitingThread);
                        } catch (Throwable th) {
                            th = th;
                        }
                    } else {
                        i iVar4 = aborter;
                    }
                    rospl.l(waitingThread);
                    this.k.add(waitingThread);
                    boolean success = waitingThread.a(deadline);
                    rospl.m(waitingThread);
                    this.k.remove(waitingThread);
                    if (!success && deadline != null) {
                        if (deadline.getTime() <= System.currentTimeMillis()) {
                            throw new ConnectionPoolTimeoutException("Timeout waiting for connection from pool");
                        }
                    }
                } else {
                    i();
                    z = true;
                    f rospl2 = m(bVar, true);
                    rospl = rospl2;
                    entry = d(rospl2, this.g);
                    i iVar5 = aborter;
                }
                long j3 = timeout;
            }
            this.f.unlock();
            return entry;
        } catch (Throwable th2) {
            th = th2;
            i iVar6 = aborter;
            this.f.unlock();
            throw th;
        }
    }

    public void j(b entry, boolean reusable, long validDuration, TimeUnit timeUnit) {
        String s;
        b route = entry.i();
        if (this.e.isDebugEnabled()) {
            this.e.debug("Releasing connection [" + route + "][" + entry.a() + "]");
        }
        this.f.lock();
        try {
            if (this.o) {
                b(entry);
                return;
            }
            this.i.remove(entry);
            f rospl = m(route, true);
            if (!reusable || rospl.f() < 0) {
                b(entry);
                rospl.d();
                this.q--;
            } else {
                if (this.e.isDebugEnabled()) {
                    if (validDuration > 0) {
                        s = "for " + validDuration + " " + timeUnit;
                    } else {
                        s = "indefinitely";
                    }
                    this.e.debug("Pooling connection [" + route + "][" + entry.a() + "]; keep alive " + s);
                }
                rospl.e(entry);
                entry.l(validDuration, timeUnit);
                this.j.add(entry);
            }
            p(rospl);
            this.f.unlock();
        } finally {
            this.f.unlock();
        }
    }

    /* access modifiers changed from: protected */
    public b l(f rospl, Object state) {
        b entry = null;
        this.f.lock();
        boolean done = false;
        while (!done) {
            try {
                entry = rospl.a(state);
                if (entry != null) {
                    if (this.e.isDebugEnabled()) {
                        this.e.debug("Getting free connection [" + rospl.h() + "][" + state + "]");
                    }
                    this.j.remove(entry);
                    if (entry.k(System.currentTimeMillis())) {
                        if (this.e.isDebugEnabled()) {
                            this.e.debug("Closing expired free connection [" + rospl.h() + "][" + state + "]");
                        }
                        b(entry);
                        rospl.d();
                        this.q--;
                    } else {
                        this.i.add(entry);
                        done = true;
                    }
                } else {
                    done = true;
                    if (this.e.isDebugEnabled()) {
                        this.e.debug("No free connections [" + rospl.h() + "][" + state + "]");
                    }
                }
            } catch (Throwable th) {
                this.f.unlock();
                throw th;
            }
        }
        this.f.unlock();
        return entry;
    }

    /* access modifiers changed from: protected */
    public b d(f rospl, org.apache.http.conn.d op) {
        if (this.e.isDebugEnabled()) {
            this.e.debug("Creating new connection [" + rospl.h() + "]");
        }
        b bVar = new b(op, rospl.h(), this.m, this.n);
        this.f.lock();
        try {
            rospl.b(bVar);
            this.q++;
            this.i.add(bVar);
            return bVar;
        } finally {
            this.f.unlock();
        }
    }

    /* access modifiers changed from: protected */
    public void h(b entry) {
        b route = entry.i();
        if (this.e.isDebugEnabled()) {
            this.e.debug("Deleting connection [" + route + "][" + entry.a() + "]");
        }
        this.f.lock();
        try {
            b(entry);
            f rospl = m(route, true);
            rospl.c(entry);
            this.q--;
            if (rospl.j()) {
                this.l.remove(route);
            }
        } finally {
            this.f.unlock();
        }
    }

    /* access modifiers changed from: protected */
    public void i() {
        this.f.lock();
        try {
            b entry = this.j.remove();
            if (entry != null) {
                h(entry);
            } else if (this.e.isDebugEnabled()) {
                this.e.debug("No free connection to delete");
            }
        } finally {
            this.f.unlock();
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x006d A[Catch:{ all -> 0x0077 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void p(org.apache.http.impl.conn.tsccm.f r5) {
        /*
            r4 = this;
            r0 = 0
            java.util.concurrent.locks.Lock r1 = r4.f
            r1.lock()
            if (r5 == 0) goto L_0x003b
            boolean r1 = r5.i()     // Catch:{ all -> 0x0077 }
            if (r1 == 0) goto L_0x003b
            org.apache.commons.logging.a r1 = r4.e     // Catch:{ all -> 0x0077 }
            boolean r1 = r1.isDebugEnabled()     // Catch:{ all -> 0x0077 }
            if (r1 == 0) goto L_0x0035
            org.apache.commons.logging.a r1 = r4.e     // Catch:{ all -> 0x0077 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0077 }
            r2.<init>()     // Catch:{ all -> 0x0077 }
            java.lang.String r3 = "Notifying thread waiting on pool ["
            r2.append(r3)     // Catch:{ all -> 0x0077 }
            org.apache.http.conn.routing.b r3 = r5.h()     // Catch:{ all -> 0x0077 }
            r2.append(r3)     // Catch:{ all -> 0x0077 }
            java.lang.String r3 = "]"
            r2.append(r3)     // Catch:{ all -> 0x0077 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0077 }
            r1.debug(r2)     // Catch:{ all -> 0x0077 }
        L_0x0035:
            org.apache.http.impl.conn.tsccm.h r1 = r5.k()     // Catch:{ all -> 0x0077 }
            r0 = r1
            goto L_0x006b
        L_0x003b:
            java.util.Queue<org.apache.http.impl.conn.tsccm.h> r1 = r4.k     // Catch:{ all -> 0x0077 }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0077 }
            if (r1 != 0) goto L_0x005c
            org.apache.commons.logging.a r1 = r4.e     // Catch:{ all -> 0x0077 }
            boolean r1 = r1.isDebugEnabled()     // Catch:{ all -> 0x0077 }
            if (r1 == 0) goto L_0x0052
            org.apache.commons.logging.a r1 = r4.e     // Catch:{ all -> 0x0077 }
            java.lang.String r2 = "Notifying thread waiting on any pool"
            r1.debug(r2)     // Catch:{ all -> 0x0077 }
        L_0x0052:
            java.util.Queue<org.apache.http.impl.conn.tsccm.h> r1 = r4.k     // Catch:{ all -> 0x0077 }
            java.lang.Object r1 = r1.remove()     // Catch:{ all -> 0x0077 }
            org.apache.http.impl.conn.tsccm.h r1 = (org.apache.http.impl.conn.tsccm.h) r1     // Catch:{ all -> 0x0077 }
            r0 = r1
            goto L_0x006b
        L_0x005c:
            org.apache.commons.logging.a r1 = r4.e     // Catch:{ all -> 0x0077 }
            boolean r1 = r1.isDebugEnabled()     // Catch:{ all -> 0x0077 }
            if (r1 == 0) goto L_0x006b
            org.apache.commons.logging.a r1 = r4.e     // Catch:{ all -> 0x0077 }
            java.lang.String r2 = "Notifying no-one, there are no waiting threads"
            r1.debug(r2)     // Catch:{ all -> 0x0077 }
        L_0x006b:
            if (r0 == 0) goto L_0x0070
            r0.c()     // Catch:{ all -> 0x0077 }
        L_0x0070:
            java.util.concurrent.locks.Lock r1 = r4.f
            r1.unlock()
            return
        L_0x0077:
            r1 = move-exception
            java.util.concurrent.locks.Lock r2 = r4.f
            r2.unlock()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.conn.tsccm.d.p(org.apache.http.impl.conn.tsccm.f):void");
    }

    public void c(long idletime, TimeUnit tunit) {
        org.apache.http.util.a.i(tunit, "Time unit");
        long t = 0;
        if (idletime > 0) {
            t = idletime;
        }
        if (this.e.isDebugEnabled()) {
            org.apache.commons.logging.a aVar = this.e;
            aVar.debug("Closing connections idle longer than " + t + " " + tunit);
        }
        long deadline = System.currentTimeMillis() - tunit.toMillis(t);
        this.f.lock();
        try {
            Iterator<BasicPoolEntry> iter = this.j.iterator();
            while (iter.hasNext()) {
                b entry = (b) iter.next();
                if (entry.j() <= deadline) {
                    if (this.e.isDebugEnabled()) {
                        org.apache.commons.logging.a aVar2 = this.e;
                        aVar2.debug("Closing connection last used @ " + new Date(entry.j()));
                    }
                    iter.remove();
                    h(entry);
                }
            }
        } finally {
            this.f.unlock();
        }
    }

    public void r() {
        this.f.lock();
        try {
            if (!this.o) {
                this.o = true;
                Iterator<b> it = this.i.iterator();
                while (it.hasNext()) {
                    it.remove();
                    b(it.next());
                }
                Iterator<BasicPoolEntry> iter2 = this.j.iterator();
                while (iter2.hasNext()) {
                    b entry = (b) iter2.next();
                    iter2.remove();
                    if (this.e.isDebugEnabled()) {
                        org.apache.commons.logging.a aVar = this.e;
                        aVar.debug("Closing connection [" + entry.i() + "][" + entry.a() + "]");
                    }
                    b(entry);
                }
                Iterator<WaitingThread> iwth = this.k.iterator();
                while (iwth.hasNext()) {
                    iwth.remove();
                    ((h) iwth.next()).c();
                }
                this.l.clear();
                this.f.unlock();
            }
        } finally {
            this.f.unlock();
        }
    }
}
