package io.reactivex.internal.schedulers;

import io.reactivex.internal.disposables.d;
import io.reactivex.r;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: IoScheduler */
public final class f extends r {
    static final i b;
    static final i c;
    private static final long d = Long.getLong("rx2.io-keep-alive-time", 60).longValue();
    private static final TimeUnit e = TimeUnit.SECONDS;
    static final c f;
    static final a g;
    final ThreadFactory h;
    final AtomicReference<a> i;

    static {
        c cVar = new c(new i("RxCachedThreadSchedulerShutdown"));
        f = cVar;
        cVar.dispose();
        int priority = Math.max(1, Math.min(10, Integer.getInteger("rx2.io-priority", 5).intValue()));
        i iVar = new i("RxCachedThreadScheduler", priority);
        b = iVar;
        c = new i("RxCachedWorkerPoolEvictor", priority);
        a aVar = new a(0, (TimeUnit) null, iVar);
        g = aVar;
        aVar.e();
    }

    /* compiled from: IoScheduler */
    public static final class a implements Runnable {
        private final long c;
        private final ConcurrentLinkedQueue<c> d;
        final io.reactivex.disposables.a f;
        private final ScheduledExecutorService q;
        private final Future<?> x;
        private final ThreadFactory y;

        a(long keepAliveTime, TimeUnit unit, ThreadFactory threadFactory) {
            long nanos = unit != null ? unit.toNanos(keepAliveTime) : 0;
            this.c = nanos;
            this.d = new ConcurrentLinkedQueue<>();
            this.f = new io.reactivex.disposables.a();
            this.y = threadFactory;
            ScheduledExecutorService evictor = null;
            Future<?> task = null;
            if (unit != null) {
                evictor = Executors.newScheduledThreadPool(1, f.c);
                task = evictor.scheduleWithFixedDelay(this, nanos, nanos, TimeUnit.NANOSECONDS);
            }
            this.q = evictor;
            this.x = task;
        }

        public void run() {
            a();
        }

        /* access modifiers changed from: package-private */
        public c b() {
            if (this.f.isDisposed()) {
                return f.f;
            }
            while (!this.d.isEmpty()) {
                c threadWorker = this.d.poll();
                if (threadWorker != null) {
                    return threadWorker;
                }
            }
            c w = new c(this.y);
            this.f.b(w);
            return w;
        }

        /* access modifiers changed from: package-private */
        public void d(c threadWorker) {
            threadWorker.j(c() + this.c);
            this.d.offer(threadWorker);
        }

        /* access modifiers changed from: package-private */
        public void a() {
            if (!this.d.isEmpty()) {
                long currentTimestamp = c();
                Iterator<c> it = this.d.iterator();
                while (it.hasNext()) {
                    c threadWorker = it.next();
                    if (threadWorker.i() > currentTimestamp) {
                        return;
                    }
                    if (this.d.remove(threadWorker)) {
                        this.f.a(threadWorker);
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public long c() {
            return System.nanoTime();
        }

        /* access modifiers changed from: package-private */
        public void e() {
            this.f.dispose();
            Future<?> future = this.x;
            if (future != null) {
                future.cancel(true);
            }
            ScheduledExecutorService scheduledExecutorService = this.q;
            if (scheduledExecutorService != null) {
                scheduledExecutorService.shutdownNow();
            }
        }
    }

    public f() {
        this(b);
    }

    public f(ThreadFactory threadFactory) {
        this.h = threadFactory;
        this.i = new AtomicReference<>(g);
        e();
    }

    public void e() {
        a update = new a(d, e, this.h);
        if (!this.i.compareAndSet(g, update)) {
            update.e();
        }
    }

    public r.c a() {
        return new b(this.i.get());
    }

    /* compiled from: IoScheduler */
    public static final class b extends r.c {
        private final io.reactivex.disposables.a c;
        private final a d;
        private final c f;
        final AtomicBoolean q = new AtomicBoolean();

        b(a pool) {
            this.d = pool;
            this.c = new io.reactivex.disposables.a();
            this.f = pool.b();
        }

        public void dispose() {
            if (this.q.compareAndSet(false, true)) {
                this.c.dispose();
                this.d.d(this.f);
            }
        }

        public boolean isDisposed() {
            return this.q.get();
        }

        public io.reactivex.disposables.b c(Runnable action, long delayTime, TimeUnit unit) {
            if (this.c.isDisposed()) {
                return d.INSTANCE;
            }
            return this.f.e(action, delayTime, unit, this.c);
        }
    }

    /* compiled from: IoScheduler */
    public static final class c extends h {
        private long f = 0;

        c(ThreadFactory threadFactory) {
            super(threadFactory);
        }

        public long i() {
            return this.f;
        }

        public void j(long expirationTime) {
            this.f = expirationTime;
        }
    }
}
