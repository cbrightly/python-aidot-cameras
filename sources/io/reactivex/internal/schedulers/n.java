package io.reactivex.internal.schedulers;

import io.reactivex.disposables.b;
import io.reactivex.internal.disposables.d;
import io.reactivex.r;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: SingleScheduler */
public final class n extends r {
    static final i b = new i("RxSingleScheduler", Math.max(1, Math.min(10, Integer.getInteger("rx2.single-priority", 5).intValue())), true);
    static final ScheduledExecutorService c;
    final ThreadFactory d;
    final AtomicReference<ScheduledExecutorService> e;

    static {
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(0);
        c = newScheduledThreadPool;
        newScheduledThreadPool.shutdown();
    }

    public n() {
        this(b);
    }

    public n(ThreadFactory threadFactory) {
        AtomicReference<ScheduledExecutorService> atomicReference = new AtomicReference<>();
        this.e = atomicReference;
        this.d = threadFactory;
        atomicReference.lazySet(e(threadFactory));
    }

    static ScheduledExecutorService e(ThreadFactory threadFactory) {
        return m.a(threadFactory);
    }

    public r.c a() {
        return new a(this.e.get());
    }

    public b c(Runnable run, long delay, TimeUnit unit) {
        Future<?> f;
        k task = new k(io.reactivex.plugins.a.t(run));
        if (delay <= 0) {
            try {
                f = this.e.get().submit(task);
            } catch (RejectedExecutionException ex) {
                io.reactivex.plugins.a.q(ex);
                return d.INSTANCE;
            }
        } else {
            f = this.e.get().schedule(task, delay, unit);
        }
        task.setFuture(f);
        return task;
    }

    public b d(Runnable run, long initialDelay, long period, TimeUnit unit) {
        Future<?> f;
        Runnable decoratedRun = io.reactivex.plugins.a.t(run);
        if (period <= 0) {
            ScheduledExecutorService exec = this.e.get();
            e periodicWrapper = new e(decoratedRun, exec);
            if (initialDelay <= 0) {
                try {
                    f = exec.submit(periodicWrapper);
                } catch (RejectedExecutionException ex) {
                    io.reactivex.plugins.a.q(ex);
                    return d.INSTANCE;
                }
            } else {
                f = exec.schedule(periodicWrapper, initialDelay, unit);
            }
            periodicWrapper.b(f);
            return periodicWrapper;
        }
        j task = new j(decoratedRun);
        try {
            task.setFuture(this.e.get().scheduleAtFixedRate(task, initialDelay, period, unit));
            return task;
        } catch (RejectedExecutionException ex2) {
            io.reactivex.plugins.a.q(ex2);
            return d.INSTANCE;
        }
    }

    /* compiled from: SingleScheduler */
    public static final class a extends r.c {
        final ScheduledExecutorService c;
        final io.reactivex.disposables.a d = new io.reactivex.disposables.a();
        volatile boolean f;

        a(ScheduledExecutorService executor) {
            this.c = executor;
        }

        public b c(Runnable run, long delay, TimeUnit unit) {
            Future<?> f2;
            if (this.f) {
                return d.INSTANCE;
            }
            l sr = new l(io.reactivex.plugins.a.t(run), this.d);
            this.d.b(sr);
            if (delay <= 0) {
                try {
                    f2 = this.c.submit(sr);
                } catch (RejectedExecutionException ex) {
                    dispose();
                    io.reactivex.plugins.a.q(ex);
                    return d.INSTANCE;
                }
            } else {
                f2 = this.c.schedule(sr, delay, unit);
            }
            sr.setFuture(f2);
            return sr;
        }

        public void dispose() {
            if (!this.f) {
                this.f = true;
                this.d.dispose();
            }
        }

        public boolean isDisposed() {
            return this.f;
        }
    }
}
