package org.apache.http.pool;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.http.pool.c;

/* compiled from: AbstractConnPool */
public abstract class a<T, C, E extends c<T, C>> {
    /* access modifiers changed from: private */
    public final Lock a;
    /* access modifiers changed from: private */
    public final Condition b;
    private final b<T, C> c;
    private final Map<T, f<T, C, E>> d = new HashMap();
    private final Set<E> e = new HashSet();
    private final LinkedList<E> f = new LinkedList<>();
    private final LinkedList<Future<E>> g = new LinkedList<>();
    private final Map<T, Integer> h = new HashMap();
    private volatile boolean i;
    private volatile int j;
    private volatile int k;
    /* access modifiers changed from: private */
    public volatile int l;

    /* access modifiers changed from: protected */
    public abstract E g(T t, C c2);

    /* access modifiers changed from: protected */
    public abstract boolean x(E e2);

    public a(b<T, C> connFactory, int defaultMaxPerRoute, int maxTotal) {
        this.c = (b) org.apache.http.util.a.i(connFactory, "Connection factory");
        this.j = org.apache.http.util.a.j(defaultMaxPerRoute, "Max per route value");
        this.k = org.apache.http.util.a.j(maxTotal, "Max total value");
        ReentrantLock reentrantLock = new ReentrantLock();
        this.a = reentrantLock;
        this.b = reentrantLock.newCondition();
    }

    /* access modifiers changed from: protected */
    public void o(E e2) {
    }

    /* access modifiers changed from: protected */
    public void p(E e2) {
    }

    /* access modifiers changed from: protected */
    public void q(E e2) {
    }

    public void w() {
        if (!this.i) {
            this.i = true;
            this.a.lock();
            try {
                Iterator i$ = this.f.iterator();
                while (i$.hasNext()) {
                    ((c) i$.next()).a();
                }
                for (E entry : this.e) {
                    entry.a();
                }
                for (f<T, C, E> pool : this.d.values()) {
                    pool.m();
                }
                this.d.clear();
                this.e.clear();
                this.f.clear();
            } finally {
                this.a.unlock();
            }
        }
    }

    private f<T, C, E> j(T route) {
        RouteSpecificPool<T, C, E> pool = (f) this.d.get(route);
        if (pool != null) {
            return pool;
        }
        RouteSpecificPool<T, C, E> pool2 = new C0145a(route, route);
        this.d.put(route, pool2);
        return pool2;
    }

    /* renamed from: org.apache.http.pool.a$a  reason: collision with other inner class name */
    /* compiled from: AbstractConnPool */
    public class C0145a extends f<T, C, E> {
        final /* synthetic */ Object e;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0145a(Object x0, Object obj) {
            super(x0);
            this.e = obj;
        }

        /* access modifiers changed from: protected */
        public E b(C conn) {
            return a.this.g(this.e, conn);
        }
    }

    public Future<E> n(T route, Object state, org.apache.http.concurrent.b<E> callback) {
        org.apache.http.util.a.i(route, "Route");
        org.apache.http.util.b.a(!this.i, "Connection pool shut down");
        return new b(callback, route, state);
    }

    /* compiled from: AbstractConnPool */
    public class b implements Future<E> {
        private volatile boolean c;
        private volatile boolean d;
        private volatile E f;
        final /* synthetic */ org.apache.http.concurrent.b q;
        final /* synthetic */ Object x;
        final /* synthetic */ Object y;

        b(org.apache.http.concurrent.b bVar, Object obj, Object obj2) {
            this.q = bVar;
            this.x = obj;
            this.y = obj2;
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            boolean result;
            this.c = true;
            a.this.a.lock();
            try {
                a.this.b.signalAll();
                synchronized (this) {
                    result = !this.d;
                    this.d = true;
                    org.apache.http.concurrent.b bVar = this.q;
                    if (bVar != null) {
                        bVar.cancelled();
                    }
                }
                return result;
            } finally {
                a.this.a.unlock();
            }
        }

        public boolean isCancelled() {
            return this.c;
        }

        public boolean isDone() {
            return this.d;
        }

        /* renamed from: a */
        public E get() {
            try {
                return get(0, TimeUnit.MILLISECONDS);
            } catch (TimeoutException ex) {
                throw new ExecutionException(ex);
            }
        }

        /* renamed from: b */
        public E get(long timeout, TimeUnit tunit) {
            E leasedEntry;
            E e;
            if (this.f != null) {
                return this.f;
            }
            synchronized (this) {
                while (true) {
                    try {
                        leasedEntry = a.this.k(this.x, this.y, timeout, tunit, this);
                        if (a.this.l <= 0 || leasedEntry.g() + ((long) a.this.l) > System.currentTimeMillis() || a.this.x(leasedEntry)) {
                            this.f = leasedEntry;
                            this.d = true;
                            a.this.o(this.f);
                            org.apache.http.concurrent.b bVar = this.q;
                        } else {
                            leasedEntry.a();
                            a.this.s(leasedEntry, false);
                        }
                    } catch (IOException ex) {
                        this.d = true;
                        org.apache.http.concurrent.b bVar2 = this.q;
                        if (bVar2 != null) {
                            bVar2.a(ex);
                        }
                        throw new ExecutionException(ex);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                this.f = leasedEntry;
                this.d = true;
                a.this.o(this.f);
                org.apache.http.concurrent.b bVar3 = this.q;
                if (bVar3 != null) {
                    bVar3.completed(this.f);
                }
                e = this.f;
            }
            return e;
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00be, code lost:
        if (r1.f.size() <= (r9 - 1)) goto L_0x00de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00c6, code lost:
        if (r1.f.isEmpty() != false) goto L_0x00de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00c8, code lost:
        r14 = (org.apache.http.pool.c) r1.f.removeLast();
        r14.a();
        j(r14.e()).l(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
        r11 = r6.a(r1.c.create(r19));
        r16 = r0;
        r1.e.add(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00f3, code lost:
        r1.a.unlock();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00f9, code lost:
        return r11;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public E k(T r19, java.lang.Object r20, long r21, java.util.concurrent.TimeUnit r23, java.util.concurrent.Future<E> r24) {
        /*
            r18 = this;
            r1 = r18
            r2 = r21
            r4 = r24
            r0 = 0
            r5 = 0
            int r5 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r5 <= 0) goto L_0x001f
            java.util.Date r5 = new java.util.Date
            long r6 = java.lang.System.currentTimeMillis()
            r8 = r23
            long r9 = r8.toMillis(r2)
            long r6 = r6 + r9
            r5.<init>(r6)
            r0 = r5
            goto L_0x0022
        L_0x001f:
            r8 = r23
            r5 = r0
        L_0x0022:
            java.util.concurrent.locks.Lock r0 = r1.a
            r0.lock()
            org.apache.http.pool.f r0 = r18.j(r19)     // Catch:{ all -> 0x016a }
            r6 = r0
        L_0x002c:
            boolean r0 = r1.i     // Catch:{ all -> 0x016a }
            r7 = 1
            r9 = 0
            if (r0 != 0) goto L_0x0034
            r0 = r7
            goto L_0x0035
        L_0x0034:
            r0 = r9
        L_0x0035:
            java.lang.String r10 = "Connection pool shut down"
            org.apache.http.util.b.a(r0, r10)     // Catch:{ all -> 0x016a }
        L_0x003a:
            r10 = r20
            org.apache.http.pool.c r0 = r6.f(r10)     // Catch:{ all -> 0x0166 }
            r11 = r0
            if (r11 != 0) goto L_0x0044
            goto L_0x0060
        L_0x0044:
            long r12 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0166 }
            boolean r0 = r11.i(r12)     // Catch:{ all -> 0x0166 }
            if (r0 == 0) goto L_0x0051
            r11.a()     // Catch:{ all -> 0x0166 }
        L_0x0051:
            boolean r0 = r11.h()     // Catch:{ all -> 0x0166 }
            if (r0 == 0) goto L_0x0060
            java.util.LinkedList<E> r0 = r1.f     // Catch:{ all -> 0x0166 }
            r0.remove(r11)     // Catch:{ all -> 0x0166 }
            r6.c(r11, r9)     // Catch:{ all -> 0x0166 }
            goto L_0x003a
        L_0x0060:
            if (r11 == 0) goto L_0x0076
            java.util.LinkedList<E> r0 = r1.f     // Catch:{ all -> 0x0166 }
            r0.remove(r11)     // Catch:{ all -> 0x0166 }
            java.util.Set<E> r0 = r1.e     // Catch:{ all -> 0x0166 }
            r0.add(r11)     // Catch:{ all -> 0x0166 }
            r1.q(r11)     // Catch:{ all -> 0x0166 }
            java.util.concurrent.locks.Lock r0 = r1.a
            r0.unlock()
            return r11
        L_0x0076:
            int r0 = r18.i(r19)     // Catch:{ all -> 0x0166 }
            r12 = r0
            int r0 = r6.d()     // Catch:{ all -> 0x0166 }
            int r0 = r0 + r7
            int r0 = r0 - r12
            int r0 = java.lang.Math.max(r9, r0)     // Catch:{ all -> 0x0166 }
            r7 = r0
            if (r7 <= 0) goto L_0x00a1
            r0 = 0
        L_0x0089:
            if (r0 >= r7) goto L_0x00a1
            org.apache.http.pool.c r13 = r6.g()     // Catch:{ all -> 0x0166 }
            if (r13 != 0) goto L_0x0092
            goto L_0x00a1
        L_0x0092:
            r13.a()     // Catch:{ all -> 0x0166 }
            java.util.LinkedList<E> r14 = r1.f     // Catch:{ all -> 0x0166 }
            r14.remove(r13)     // Catch:{ all -> 0x0166 }
            r6.l(r13)     // Catch:{ all -> 0x0166 }
            int r0 = r0 + 1
            goto L_0x0089
        L_0x00a1:
            int r0 = r6.d()     // Catch:{ all -> 0x0166 }
            if (r0 >= r12) goto L_0x00ff
            java.util.Set<E> r0 = r1.e     // Catch:{ all -> 0x0166 }
            int r0 = r0.size()     // Catch:{ all -> 0x0166 }
            int r13 = r1.k     // Catch:{ all -> 0x0166 }
            int r13 = r13 - r0
            int r9 = java.lang.Math.max(r13, r9)     // Catch:{ all -> 0x0166 }
            if (r9 <= 0) goto L_0x00fa
            java.util.LinkedList<E> r13 = r1.f     // Catch:{ all -> 0x0166 }
            int r13 = r13.size()     // Catch:{ all -> 0x0166 }
            int r14 = r9 + -1
            if (r13 <= r14) goto L_0x00de
            java.util.LinkedList<E> r14 = r1.f     // Catch:{ all -> 0x0166 }
            boolean r14 = r14.isEmpty()     // Catch:{ all -> 0x0166 }
            if (r14 != 0) goto L_0x00de
            java.util.LinkedList<E> r14 = r1.f     // Catch:{ all -> 0x0166 }
            java.lang.Object r14 = r14.removeLast()     // Catch:{ all -> 0x0166 }
            org.apache.http.pool.c r14 = (org.apache.http.pool.c) r14     // Catch:{ all -> 0x0166 }
            r14.a()     // Catch:{ all -> 0x0166 }
            java.lang.Object r15 = r14.e()     // Catch:{ all -> 0x0166 }
            org.apache.http.pool.f r15 = r1.j(r15)     // Catch:{ all -> 0x0166 }
            r15.l(r14)     // Catch:{ all -> 0x0166 }
        L_0x00de:
            org.apache.http.pool.b<T, C> r14 = r1.c     // Catch:{ all -> 0x0166 }
            r15 = r19
            java.lang.Object r14 = r14.create(r15)     // Catch:{ all -> 0x0164 }
            org.apache.http.pool.c r16 = r6.a(r14)     // Catch:{ all -> 0x0164 }
            r11 = r16
            r16 = r0
            java.util.Set<E> r0 = r1.e     // Catch:{ all -> 0x0164 }
            r0.add(r11)     // Catch:{ all -> 0x0164 }
            java.util.concurrent.locks.Lock r0 = r1.a
            r0.unlock()
            return r11
        L_0x00fa:
            r15 = r19
            r16 = r0
            goto L_0x0101
        L_0x00ff:
            r15 = r19
        L_0x0101:
            r9 = 0
            boolean r0 = r24.isCancelled()     // Catch:{ all -> 0x015a }
            java.lang.String r13 = "Operation interrupted"
            if (r0 != 0) goto L_0x0154
            r6.k(r4)     // Catch:{ all -> 0x015a }
            java.util.LinkedList<java.util.concurrent.Future<E>> r0 = r1.g     // Catch:{ all -> 0x015a }
            r0.add(r4)     // Catch:{ all -> 0x015a }
            if (r5 == 0) goto L_0x011c
            java.util.concurrent.locks.Condition r0 = r1.b     // Catch:{ all -> 0x015a }
            boolean r0 = r0.awaitUntil(r5)     // Catch:{ all -> 0x015a }
            r9 = r0
            goto L_0x0123
        L_0x011c:
            java.util.concurrent.locks.Condition r0 = r1.b     // Catch:{ all -> 0x015a }
            r0.await()     // Catch:{ all -> 0x015a }
            r0 = 1
            r9 = r0
        L_0x0123:
            boolean r0 = r24.isCancelled()     // Catch:{ all -> 0x015a }
            if (r0 != 0) goto L_0x014e
            r6.n(r4)     // Catch:{ all -> 0x0164 }
            java.util.LinkedList<java.util.concurrent.Future<E>> r0 = r1.g     // Catch:{ all -> 0x0164 }
            r0.remove(r4)     // Catch:{ all -> 0x0164 }
            if (r9 != 0) goto L_0x014c
            if (r5 == 0) goto L_0x014c
            long r13 = r5.getTime()     // Catch:{ all -> 0x0164 }
            long r16 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0164 }
            int r0 = (r13 > r16 ? 1 : (r13 == r16 ? 0 : -1))
            if (r0 <= 0) goto L_0x0143
            goto L_0x014c
        L_0x0143:
            java.util.concurrent.TimeoutException r0 = new java.util.concurrent.TimeoutException     // Catch:{ all -> 0x0164 }
            java.lang.String r7 = "Timeout waiting for connection"
            r0.<init>(r7)     // Catch:{ all -> 0x0164 }
            throw r0     // Catch:{ all -> 0x0164 }
        L_0x014c:
            goto L_0x002c
        L_0x014e:
            java.lang.InterruptedException r0 = new java.lang.InterruptedException     // Catch:{ all -> 0x015a }
            r0.<init>(r13)     // Catch:{ all -> 0x015a }
            throw r0     // Catch:{ all -> 0x015a }
        L_0x0154:
            java.lang.InterruptedException r0 = new java.lang.InterruptedException     // Catch:{ all -> 0x015a }
            r0.<init>(r13)     // Catch:{ all -> 0x015a }
            throw r0     // Catch:{ all -> 0x015a }
        L_0x015a:
            r0 = move-exception
            r6.n(r4)     // Catch:{ all -> 0x0164 }
            java.util.LinkedList<java.util.concurrent.Future<E>> r13 = r1.g     // Catch:{ all -> 0x0164 }
            r13.remove(r4)     // Catch:{ all -> 0x0164 }
            throw r0     // Catch:{ all -> 0x0164 }
        L_0x0164:
            r0 = move-exception
            goto L_0x016f
        L_0x0166:
            r0 = move-exception
            r15 = r19
            goto L_0x016f
        L_0x016a:
            r0 = move-exception
            r15 = r19
            r10 = r20
        L_0x016f:
            java.util.concurrent.locks.Lock r6 = r1.a
            r6.unlock()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.pool.a.k(java.lang.Object, java.lang.Object, long, java.util.concurrent.TimeUnit, java.util.concurrent.Future):org.apache.http.pool.c");
    }

    public void s(E entry, boolean reusable) {
        this.a.lock();
        try {
            if (this.e.remove(entry)) {
                RouteSpecificPool<T, C, E> pool = j(entry.e());
                pool.c(entry, reusable);
                if (!reusable || this.i) {
                    entry.a();
                } else {
                    this.f.addFirst(entry);
                }
                p(entry);
                Future<E> future = pool.j();
                if (future != null) {
                    this.g.remove(future);
                } else {
                    future = this.g.poll();
                }
                if (future != null) {
                    this.b.signalAll();
                }
            }
        } finally {
            this.a.unlock();
        }
    }

    private int i(T route) {
        Integer v = this.h.get(route);
        if (v != null) {
            return v.intValue();
        }
        return this.j;
    }

    public void u(int max) {
        org.apache.http.util.a.j(max, "Max value");
        this.a.lock();
        try {
            this.k = max;
        } finally {
            this.a.unlock();
        }
    }

    public void t(int max) {
        org.apache.http.util.a.j(max, "Max per route value");
        this.a.lock();
        try {
            this.j = max;
        } finally {
            this.a.unlock();
        }
    }

    public e m() {
        this.a.lock();
        try {
            return new e(this.e.size(), this.g.size(), this.f.size(), this.k);
        } finally {
            this.a.unlock();
        }
    }

    public e l(T route) {
        org.apache.http.util.a.i(route, "Route");
        this.a.lock();
        try {
            RouteSpecificPool<T, C, E> pool = j(route);
            return new e(pool.h(), pool.i(), pool.e(), i(route));
        } finally {
            this.a.unlock();
        }
    }

    /* access modifiers changed from: protected */
    public void h(d<T, C> callback) {
        this.a.lock();
        try {
            Iterator<E> it = this.f.iterator();
            while (it.hasNext()) {
                E entry = (c) it.next();
                callback.a(entry);
                if (entry.h()) {
                    j(entry.e()).l(entry);
                    it.remove();
                }
            }
            r();
        } finally {
            this.a.unlock();
        }
    }

    private void r() {
        Iterator<Map.Entry<T, f<T, C, E>>> it = this.d.entrySet().iterator();
        while (it.hasNext()) {
            RouteSpecificPool<T, C, E> pool = (f) it.next().getValue();
            if (pool.i() + pool.d() == 0) {
                it.remove();
            }
        }
    }

    public void f(long idletime, TimeUnit tunit) {
        org.apache.http.util.a.i(tunit, "Time unit");
        long time = tunit.toMillis(idletime);
        if (time < 0) {
            time = 0;
        }
        h(new c(System.currentTimeMillis() - time));
    }

    /* compiled from: AbstractConnPool */
    public class c implements d<T, C> {
        final /* synthetic */ long a;

        c(long j) {
            this.a = j;
        }

        public void a(c<T, C> entry) {
            if (entry.g() <= this.a) {
                entry.a();
            }
        }
    }

    /* compiled from: AbstractConnPool */
    public class d implements d<T, C> {
        final /* synthetic */ long a;

        d(long j) {
            this.a = j;
        }

        public void a(c<T, C> entry) {
            if (entry.i(this.a)) {
                entry.a();
            }
        }
    }

    public void e() {
        h(new d(System.currentTimeMillis()));
    }

    public void v(int ms) {
        this.l = ms;
    }

    public String toString() {
        return "[leased: " + this.e + "][available: " + this.f + "][pending: " + this.g + "]";
    }
}
