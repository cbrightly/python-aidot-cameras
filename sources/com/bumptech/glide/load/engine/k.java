package com.bumptech.glide.load.engine;

import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.core.util.Pools;
import com.bumptech.glide.load.engine.g;
import com.bumptech.glide.load.engine.o;
import com.bumptech.glide.load.f;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.pool.FactoryPools;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: EngineJob */
public class k<R> implements g.b<R>, FactoryPools.e {
    private static final c c = new c();
    private boolean A4;
    private boolean B4;
    private t<?> C4;
    com.bumptech.glide.load.a D4;
    private boolean E4;
    GlideException F4;
    private boolean G4;
    o<?> H4;
    private g<R> I4;
    private volatile boolean J4;
    private boolean K4;
    private final com.bumptech.glide.load.engine.executor.a a1;
    private final com.bumptech.glide.load.engine.executor.a a2;
    final e d;
    private final com.bumptech.glide.util.pool.b f;
    private final com.bumptech.glide.load.engine.executor.a p0;
    private final com.bumptech.glide.load.engine.executor.a p1;
    private final AtomicInteger p2;
    private f p3;
    private boolean p4;
    private final o.a q;
    private final Pools.Pool<k<?>> x;
    private final c y;
    private final l z;
    private boolean z4;

    k(com.bumptech.glide.load.engine.executor.a diskCacheExecutor, com.bumptech.glide.load.engine.executor.a sourceExecutor, com.bumptech.glide.load.engine.executor.a sourceUnlimitedExecutor, com.bumptech.glide.load.engine.executor.a animationExecutor, l engineJobListener, o.a resourceListener, Pools.Pool<k<?>> pool) {
        this(diskCacheExecutor, sourceExecutor, sourceUnlimitedExecutor, animationExecutor, engineJobListener, resourceListener, pool, c);
    }

    @VisibleForTesting
    k(com.bumptech.glide.load.engine.executor.a diskCacheExecutor, com.bumptech.glide.load.engine.executor.a sourceExecutor, com.bumptech.glide.load.engine.executor.a sourceUnlimitedExecutor, com.bumptech.glide.load.engine.executor.a animationExecutor, l engineJobListener, o.a resourceListener, Pools.Pool<k<?>> pool, c engineResourceFactory) {
        this.d = new e();
        this.f = com.bumptech.glide.util.pool.b.a();
        this.p2 = new AtomicInteger();
        this.p0 = diskCacheExecutor;
        this.a1 = sourceExecutor;
        this.p1 = sourceUnlimitedExecutor;
        this.a2 = animationExecutor;
        this.z = engineJobListener;
        this.q = resourceListener;
        this.x = pool;
        this.y = engineResourceFactory;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public synchronized k<R> l(f key, boolean isCacheable, boolean useUnlimitedSourceGeneratorPool, boolean useAnimationPool, boolean onlyRetrieveFromCache) {
        this.p3 = key;
        this.p4 = isCacheable;
        this.z4 = useUnlimitedSourceGeneratorPool;
        this.A4 = useAnimationPool;
        this.B4 = onlyRetrieveFromCache;
        return this;
    }

    public synchronized void s(g<R> decodeJob) {
        this.I4 = decodeJob;
        (decodeJob.P() ? this.p0 : j()).execute(decodeJob);
    }

    /* access modifiers changed from: package-private */
    public synchronized void a(com.bumptech.glide.request.g cb, Executor callbackExecutor) {
        this.f.c();
        this.d.a(cb, callbackExecutor);
        boolean z2 = true;
        if (this.E4) {
            k(1);
            callbackExecutor.execute(new b(cb));
        } else if (this.G4) {
            k(1);
            callbackExecutor.execute(new a(cb));
        } else {
            if (this.J4) {
                z2 = false;
            }
            i.a(z2, "Cannot add callbacks to a cancelled EngineJob");
        }
    }

    /* access modifiers changed from: package-private */
    @GuardedBy("this")
    public void g(com.bumptech.glide.request.g cb) {
        try {
            cb.b(this.H4, this.D4, this.K4);
        } catch (Throwable t) {
            throw new CallbackException(t);
        }
    }

    /* access modifiers changed from: package-private */
    @GuardedBy("this")
    public void f(com.bumptech.glide.request.g cb) {
        try {
            cb.c(this.F4);
        } catch (Throwable t) {
            throw new CallbackException(t);
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void r(com.bumptech.glide.request.g cb) {
        boolean isFinishedRunning;
        this.f.c();
        this.d.f(cb);
        if (this.d.isEmpty()) {
            h();
            if (!this.E4) {
                if (!this.G4) {
                    isFinishedRunning = false;
                    if (isFinishedRunning && this.p2.get() == 0) {
                        q();
                    }
                }
            }
            isFinishedRunning = true;
            q();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean p() {
        return this.B4;
    }

    private com.bumptech.glide.load.engine.executor.a j() {
        if (this.z4) {
            return this.p1;
        }
        return this.A4 ? this.a2 : this.a1;
    }

    /* access modifiers changed from: package-private */
    public void h() {
        if (!m()) {
            this.J4 = true;
            this.I4.b();
            this.z.c(this, this.p3);
        }
    }

    private boolean m() {
        return this.G4 || this.E4 || this.J4;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0046, code lost:
        r8.z.b(r8, r0, r2);
        r3 = r1.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0053, code lost:
        if (r3.hasNext() == false) goto L_0x0068;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0055, code lost:
        r4 = r3.next();
        r4.b.execute(new com.bumptech.glide.load.engine.k.b(r8, r4.a));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0068, code lost:
        i();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x006b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void o() {
        /*
            r8 = this;
            monitor-enter(r8)
            com.bumptech.glide.util.pool.b r0 = r8.f     // Catch:{ all -> 0x007c }
            r0.c()     // Catch:{ all -> 0x007c }
            boolean r0 = r8.J4     // Catch:{ all -> 0x007c }
            if (r0 == 0) goto L_0x0014
            com.bumptech.glide.load.engine.t<?> r0 = r8.C4     // Catch:{ all -> 0x007c }
            r0.recycle()     // Catch:{ all -> 0x007c }
            r8.q()     // Catch:{ all -> 0x007c }
            monitor-exit(r8)     // Catch:{ all -> 0x007c }
            return
        L_0x0014:
            com.bumptech.glide.load.engine.k$e r0 = r8.d     // Catch:{ all -> 0x007c }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x007c }
            if (r0 != 0) goto L_0x0074
            boolean r0 = r8.E4     // Catch:{ all -> 0x007c }
            if (r0 != 0) goto L_0x006c
            com.bumptech.glide.load.engine.k$c r0 = r8.y     // Catch:{ all -> 0x007c }
            com.bumptech.glide.load.engine.t<?> r1 = r8.C4     // Catch:{ all -> 0x007c }
            boolean r2 = r8.p4     // Catch:{ all -> 0x007c }
            com.bumptech.glide.load.f r3 = r8.p3     // Catch:{ all -> 0x007c }
            com.bumptech.glide.load.engine.o$a r4 = r8.q     // Catch:{ all -> 0x007c }
            com.bumptech.glide.load.engine.o r0 = r0.a(r1, r2, r3, r4)     // Catch:{ all -> 0x007c }
            r8.H4 = r0     // Catch:{ all -> 0x007c }
            r0 = 1
            r8.E4 = r0     // Catch:{ all -> 0x007c }
            com.bumptech.glide.load.engine.k$e r1 = r8.d     // Catch:{ all -> 0x007c }
            com.bumptech.glide.load.engine.k$e r1 = r1.d()     // Catch:{ all -> 0x007c }
            int r2 = r1.size()     // Catch:{ all -> 0x007c }
            int r2 = r2 + r0
            r8.k(r2)     // Catch:{ all -> 0x007c }
            com.bumptech.glide.load.f r0 = r8.p3     // Catch:{ all -> 0x007c }
            com.bumptech.glide.load.engine.o<?> r2 = r8.H4     // Catch:{ all -> 0x007c }
            monitor-exit(r8)     // Catch:{ all -> 0x007c }
            com.bumptech.glide.load.engine.l r3 = r8.z
            r3.b(r8, r0, r2)
            java.util.Iterator r3 = r1.iterator()
        L_0x004f:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0068
            java.lang.Object r4 = r3.next()
            com.bumptech.glide.load.engine.k$d r4 = (com.bumptech.glide.load.engine.k.d) r4
            java.util.concurrent.Executor r5 = r4.b
            com.bumptech.glide.load.engine.k$b r6 = new com.bumptech.glide.load.engine.k$b
            com.bumptech.glide.request.g r7 = r4.a
            r6.<init>(r7)
            r5.execute(r6)
            goto L_0x004f
        L_0x0068:
            r8.i()
            return
        L_0x006c:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x007c }
            java.lang.String r1 = "Already have resource"
            r0.<init>(r1)     // Catch:{ all -> 0x007c }
            throw r0     // Catch:{ all -> 0x007c }
        L_0x0074:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x007c }
            java.lang.String r1 = "Received a resource without any callbacks to notify"
            r0.<init>(r1)     // Catch:{ all -> 0x007c }
            throw r0     // Catch:{ all -> 0x007c }
        L_0x007c:
            r0 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x007c }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.engine.k.o():void");
    }

    /* access modifiers changed from: package-private */
    public synchronized void k(int count) {
        o<?> oVar;
        i.a(m(), "Not yet complete!");
        if (this.p2.getAndAdd(count) == 0 && (oVar = this.H4) != null) {
            oVar.b();
        }
    }

    /* access modifiers changed from: package-private */
    public void i() {
        EngineResource<?> toRelease = null;
        synchronized (this) {
            this.f.c();
            i.a(m(), "Not yet complete!");
            int decremented = this.p2.decrementAndGet();
            i.a(decremented >= 0, "Can't decrement below 0");
            if (decremented == 0) {
                toRelease = this.H4;
                q();
            }
        }
        if (toRelease != null) {
            toRelease.e();
        }
    }

    private synchronized void q() {
        if (this.p3 != null) {
            this.d.clear();
            this.p3 = null;
            this.H4 = null;
            this.C4 = null;
            this.G4 = false;
            this.J4 = false;
            this.E4 = false;
            this.K4 = false;
            this.I4.I(false);
            this.I4 = null;
            this.F4 = null;
            this.D4 = null;
            this.x.release(this);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void b(t<R> resource, com.bumptech.glide.load.a dataSource, boolean isLoadedFromAlternateCacheKey) {
        synchronized (this) {
            this.C4 = resource;
            this.D4 = dataSource;
            this.K4 = isLoadedFromAlternateCacheKey;
        }
        o();
    }

    public void c(GlideException e2) {
        synchronized (this) {
            this.F4 = e2;
        }
        n();
    }

    public void e(g<?> job) {
        j().execute(job);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002f, code lost:
        r7.z.b(r7, r1, (com.bumptech.glide.load.engine.o<?>) null);
        r0 = r2.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003d, code lost:
        if (r0.hasNext() == false) goto L_0x0052;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003f, code lost:
        r3 = r0.next();
        r3.b.execute(new com.bumptech.glide.load.engine.k.a(r7, r3.a));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0052, code lost:
        i();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0055, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void n() {
        /*
            r7 = this;
            monitor-enter(r7)
            com.bumptech.glide.util.pool.b r0 = r7.f     // Catch:{ all -> 0x0066 }
            r0.c()     // Catch:{ all -> 0x0066 }
            boolean r0 = r7.J4     // Catch:{ all -> 0x0066 }
            if (r0 == 0) goto L_0x000f
            r7.q()     // Catch:{ all -> 0x0066 }
            monitor-exit(r7)     // Catch:{ all -> 0x0066 }
            return
        L_0x000f:
            com.bumptech.glide.load.engine.k$e r0 = r7.d     // Catch:{ all -> 0x0066 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0066 }
            if (r0 != 0) goto L_0x005e
            boolean r0 = r7.G4     // Catch:{ all -> 0x0066 }
            if (r0 != 0) goto L_0x0056
            r0 = 1
            r7.G4 = r0     // Catch:{ all -> 0x0066 }
            com.bumptech.glide.load.f r1 = r7.p3     // Catch:{ all -> 0x0066 }
            com.bumptech.glide.load.engine.k$e r2 = r7.d     // Catch:{ all -> 0x0066 }
            com.bumptech.glide.load.engine.k$e r2 = r2.d()     // Catch:{ all -> 0x0066 }
            int r3 = r2.size()     // Catch:{ all -> 0x0066 }
            int r3 = r3 + r0
            r7.k(r3)     // Catch:{ all -> 0x0066 }
            monitor-exit(r7)     // Catch:{ all -> 0x0066 }
            com.bumptech.glide.load.engine.l r0 = r7.z
            r3 = 0
            r0.b(r7, r1, r3)
            java.util.Iterator r0 = r2.iterator()
        L_0x0039:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L_0x0052
            java.lang.Object r3 = r0.next()
            com.bumptech.glide.load.engine.k$d r3 = (com.bumptech.glide.load.engine.k.d) r3
            java.util.concurrent.Executor r4 = r3.b
            com.bumptech.glide.load.engine.k$a r5 = new com.bumptech.glide.load.engine.k$a
            com.bumptech.glide.request.g r6 = r3.a
            r5.<init>(r6)
            r4.execute(r5)
            goto L_0x0039
        L_0x0052:
            r7.i()
            return
        L_0x0056:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0066 }
            java.lang.String r1 = "Already failed once"
            r0.<init>(r1)     // Catch:{ all -> 0x0066 }
            throw r0     // Catch:{ all -> 0x0066 }
        L_0x005e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0066 }
            java.lang.String r1 = "Received an exception without any callbacks to notify"
            r0.<init>(r1)     // Catch:{ all -> 0x0066 }
            throw r0     // Catch:{ all -> 0x0066 }
        L_0x0066:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0066 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.engine.k.n():void");
    }

    @NonNull
    public com.bumptech.glide.util.pool.b d() {
        return this.f;
    }

    /* compiled from: EngineJob */
    public class a implements Runnable {
        private final com.bumptech.glide.request.g c;

        a(com.bumptech.glide.request.g cb) {
            this.c = cb;
        }

        public void run() {
            synchronized (this.c.f()) {
                synchronized (k.this) {
                    if (k.this.d.b(this.c)) {
                        k.this.f(this.c);
                    }
                    k.this.i();
                }
            }
        }
    }

    /* compiled from: EngineJob */
    public class b implements Runnable {
        private final com.bumptech.glide.request.g c;

        b(com.bumptech.glide.request.g cb) {
            this.c = cb;
        }

        public void run() {
            synchronized (this.c.f()) {
                synchronized (k.this) {
                    if (k.this.d.b(this.c)) {
                        k.this.H4.b();
                        k.this.g(this.c);
                        k.this.r(this.c);
                    }
                    k.this.i();
                }
            }
        }
    }

    /* compiled from: EngineJob */
    public static final class e implements Iterable<d> {
        private final List<d> c;

        e() {
            this(new ArrayList(2));
        }

        e(List<d> callbacksAndExecutors) {
            this.c = callbacksAndExecutors;
        }

        /* access modifiers changed from: package-private */
        public void a(com.bumptech.glide.request.g cb, Executor executor) {
            this.c.add(new d(cb, executor));
        }

        /* access modifiers changed from: package-private */
        public void f(com.bumptech.glide.request.g cb) {
            this.c.remove(e(cb));
        }

        /* access modifiers changed from: package-private */
        public boolean b(com.bumptech.glide.request.g cb) {
            return this.c.contains(e(cb));
        }

        /* access modifiers changed from: package-private */
        public boolean isEmpty() {
            return this.c.isEmpty();
        }

        /* access modifiers changed from: package-private */
        public int size() {
            return this.c.size();
        }

        /* access modifiers changed from: package-private */
        public void clear() {
            this.c.clear();
        }

        /* access modifiers changed from: package-private */
        public e d() {
            return new e(new ArrayList(this.c));
        }

        private static d e(com.bumptech.glide.request.g cb) {
            return new d(cb, com.bumptech.glide.util.d.a());
        }

        @NonNull
        public Iterator<d> iterator() {
            return this.c.iterator();
        }
    }

    /* compiled from: EngineJob */
    public static final class d {
        final com.bumptech.glide.request.g a;
        final Executor b;

        d(com.bumptech.glide.request.g cb, Executor executor) {
            this.a = cb;
            this.b = executor;
        }

        public boolean equals(Object o) {
            if (o instanceof d) {
                return this.a.equals(((d) o).a);
            }
            return false;
        }

        public int hashCode() {
            return this.a.hashCode();
        }
    }

    @VisibleForTesting
    /* compiled from: EngineJob */
    public static class c {
        c() {
        }

        public <R> o<R> a(t<R> resource, boolean isMemoryCacheable, f key, o.a listener) {
            return new o(resource, isMemoryCacheable, true, key, listener);
        }
    }
}
