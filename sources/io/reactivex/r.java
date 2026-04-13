package io.reactivex;

import io.reactivex.internal.disposables.d;
import io.reactivex.internal.disposables.g;
import io.reactivex.internal.schedulers.h;
import io.reactivex.internal.util.f;
import java.util.concurrent.TimeUnit;

/* compiled from: Scheduler */
public abstract class r {
    static final long a = TimeUnit.MINUTES.toNanos(Long.getLong("rx2.scheduler.drift-tolerance", 15).longValue());

    public abstract c a();

    public io.reactivex.disposables.b b(Runnable run) {
        return c(run, 0, TimeUnit.NANOSECONDS);
    }

    public io.reactivex.disposables.b c(Runnable run, long delay, TimeUnit unit) {
        c w = a();
        a task = new a(io.reactivex.plugins.a.t(run), w);
        w.c(task, delay, unit);
        return task;
    }

    public io.reactivex.disposables.b d(Runnable run, long initialDelay, long period, TimeUnit unit) {
        c w = a();
        b periodicTask = new b(io.reactivex.plugins.a.t(run), w);
        io.reactivex.disposables.b d = w.d(periodicTask, initialDelay, period, unit);
        if (d == d.INSTANCE) {
            return d;
        }
        return periodicTask;
    }

    /* compiled from: Scheduler */
    public static abstract class c implements io.reactivex.disposables.b {
        public abstract io.reactivex.disposables.b c(Runnable runnable, long j, TimeUnit timeUnit);

        public io.reactivex.disposables.b b(Runnable run) {
            return c(run, 0, TimeUnit.NANOSECONDS);
        }

        public io.reactivex.disposables.b d(Runnable run, long initialDelay, long period, TimeUnit unit) {
            long j = initialDelay;
            TimeUnit timeUnit = unit;
            g first = new g();
            g sd = new g(first);
            Runnable decoratedRun = io.reactivex.plugins.a.t(run);
            long periodInNanoseconds = timeUnit.toNanos(period);
            long firstNowNanoseconds = a(TimeUnit.NANOSECONDS);
            g first2 = first;
            a aVar = r0;
            a aVar2 = new a(firstNowNanoseconds + timeUnit.toNanos(j), decoratedRun, firstNowNanoseconds, sd, periodInNanoseconds);
            io.reactivex.disposables.b d = c(aVar, j, timeUnit);
            if (d == d.INSTANCE) {
                return d;
            }
            first2.replace(d);
            return sd;
        }

        public long a(TimeUnit unit) {
            return unit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        /* compiled from: Scheduler */
        public final class a implements Runnable {
            final Runnable c;
            final g d;
            final long f;
            long q;
            long x;
            long y;

            a(long firstStartInNanoseconds, Runnable decoratedRun, long firstNowNanoseconds, g sd, long periodInNanoseconds) {
                this.c = decoratedRun;
                this.d = sd;
                this.f = periodInNanoseconds;
                this.x = firstNowNanoseconds;
                this.y = firstStartInNanoseconds;
            }

            public void run() {
                long nextTick;
                this.c.run();
                if (!this.d.isDisposed()) {
                    c cVar = c.this;
                    TimeUnit timeUnit = TimeUnit.NANOSECONDS;
                    long nowNanoseconds = cVar.a(timeUnit);
                    long j = r.a;
                    long j2 = this.x;
                    if (nowNanoseconds + j >= j2) {
                        long j3 = this.f;
                        if (nowNanoseconds < j2 + j3 + j) {
                            long j4 = this.y;
                            long j5 = this.q + 1;
                            this.q = j5;
                            nextTick = j4 + (j5 * j3);
                            this.x = nowNanoseconds;
                            this.d.replace(c.this.c(this, nextTick - nowNanoseconds, timeUnit));
                        }
                    }
                    long nextTick2 = this.f;
                    long nextTick3 = nowNanoseconds + nextTick2;
                    long j6 = this.q + 1;
                    this.q = j6;
                    this.y = nextTick3 - (nextTick2 * j6);
                    nextTick = nextTick3;
                    this.x = nowNanoseconds;
                    this.d.replace(c.this.c(this, nextTick - nowNanoseconds, timeUnit));
                }
            }
        }
    }

    /* compiled from: Scheduler */
    public static final class b implements io.reactivex.disposables.b, Runnable {
        final Runnable c;
        final c d;
        volatile boolean f;

        b(Runnable run, c worker) {
            this.c = run;
            this.d = worker;
        }

        public void run() {
            if (!this.f) {
                try {
                    this.c.run();
                } catch (Throwable ex) {
                    io.reactivex.exceptions.a.b(ex);
                    this.d.dispose();
                    throw f.d(ex);
                }
            }
        }

        public void dispose() {
            this.f = true;
            this.d.dispose();
        }

        public boolean isDisposed() {
            return this.f;
        }
    }

    /* compiled from: Scheduler */
    public static final class a implements io.reactivex.disposables.b, Runnable {
        final Runnable c;
        final c d;
        Thread f;

        a(Runnable decoratedRun, c w) {
            this.c = decoratedRun;
            this.d = w;
        }

        public void run() {
            this.f = Thread.currentThread();
            try {
                this.c.run();
            } finally {
                dispose();
                this.f = null;
            }
        }

        public void dispose() {
            if (this.f == Thread.currentThread()) {
                c cVar = this.d;
                if (cVar instanceof h) {
                    ((h) cVar).h();
                    return;
                }
            }
            this.d.dispose();
        }

        public boolean isDisposed() {
            return this.d.isDisposed();
        }
    }
}
