package io.reactivex.internal.schedulers;

import io.reactivex.internal.disposables.d;
import io.reactivex.internal.disposables.e;
import io.reactivex.r;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ComputationScheduler */
public final class b extends r {
    static final C0307b b;
    static final i c;
    static final int d = e(Runtime.getRuntime().availableProcessors(), Integer.getInteger("rx2.computation-threads", 0).intValue());
    static final c e;
    final ThreadFactory f;
    final AtomicReference<C0307b> g;

    static {
        c cVar = new c(new i("RxComputationShutdown"));
        e = cVar;
        cVar.dispose();
        i iVar = new i("RxComputationThreadPool", Math.max(1, Math.min(10, Integer.getInteger("rx2.computation-priority", 5).intValue())), true);
        c = iVar;
        C0307b bVar = new C0307b(0, iVar);
        b = bVar;
        bVar.b();
    }

    static int e(int cpuCount, int paramThreads) {
        return (paramThreads <= 0 || paramThreads > cpuCount) ? cpuCount : paramThreads;
    }

    /* renamed from: io.reactivex.internal.schedulers.b$b  reason: collision with other inner class name */
    /* compiled from: ComputationScheduler */
    public static final class C0307b {
        final int a;
        final c[] b;
        long c;

        C0307b(int maxThreads, ThreadFactory threadFactory) {
            this.a = maxThreads;
            this.b = new c[maxThreads];
            for (int i = 0; i < maxThreads; i++) {
                this.b[i] = new c(threadFactory);
            }
        }

        public c a() {
            int c2 = this.a;
            if (c2 == 0) {
                return b.e;
            }
            c[] cVarArr = this.b;
            long j = this.c;
            this.c = 1 + j;
            return cVarArr[(int) (j % ((long) c2))];
        }

        public void b() {
            for (c w : this.b) {
                w.dispose();
            }
        }
    }

    public b() {
        this(c);
    }

    public b(ThreadFactory threadFactory) {
        this.f = threadFactory;
        this.g = new AtomicReference<>(b);
        f();
    }

    public r.c a() {
        return new a(this.g.get().a());
    }

    public io.reactivex.disposables.b c(Runnable run, long delay, TimeUnit unit) {
        return this.g.get().a().f(run, delay, unit);
    }

    public io.reactivex.disposables.b d(Runnable run, long initialDelay, long period, TimeUnit unit) {
        return this.g.get().a().g(run, initialDelay, period, unit);
    }

    public void f() {
        C0307b update = new C0307b(d, this.f);
        if (!this.g.compareAndSet(b, update)) {
            update.b();
        }
    }

    /* compiled from: ComputationScheduler */
    public static final class a extends r.c {
        private final e c;
        private final io.reactivex.disposables.a d;
        private final e f;
        private final c q;
        volatile boolean x;

        a(c poolWorker) {
            this.q = poolWorker;
            e eVar = new e();
            this.c = eVar;
            io.reactivex.disposables.a aVar = new io.reactivex.disposables.a();
            this.d = aVar;
            e eVar2 = new e();
            this.f = eVar2;
            eVar2.b(eVar);
            eVar2.b(aVar);
        }

        public void dispose() {
            if (!this.x) {
                this.x = true;
                this.f.dispose();
            }
        }

        public boolean isDisposed() {
            return this.x;
        }

        public io.reactivex.disposables.b b(Runnable action) {
            if (this.x) {
                return d.INSTANCE;
            }
            return this.q.e(action, 0, TimeUnit.MILLISECONDS, this.c);
        }

        public io.reactivex.disposables.b c(Runnable action, long delayTime, TimeUnit unit) {
            if (this.x) {
                return d.INSTANCE;
            }
            return this.q.e(action, delayTime, unit, this.d);
        }
    }

    /* compiled from: ComputationScheduler */
    public static final class c extends h {
        c(ThreadFactory threadFactory) {
            super(threadFactory);
        }
    }
}
