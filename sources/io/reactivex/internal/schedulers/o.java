package io.reactivex.internal.schedulers;

import io.reactivex.internal.disposables.d;
import io.reactivex.r;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: TrampolineScheduler */
public final class o extends r {
    private static final o b = new o();

    public static o e() {
        return b;
    }

    public r.c a() {
        return new c();
    }

    o() {
    }

    public io.reactivex.disposables.b b(Runnable run) {
        io.reactivex.plugins.a.t(run).run();
        return d.INSTANCE;
    }

    public io.reactivex.disposables.b c(Runnable run, long delay, TimeUnit unit) {
        try {
            unit.sleep(delay);
            io.reactivex.plugins.a.t(run).run();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            io.reactivex.plugins.a.q(ex);
        }
        return d.INSTANCE;
    }

    /* compiled from: TrampolineScheduler */
    public static final class c extends r.c implements io.reactivex.disposables.b {
        final PriorityBlockingQueue<b> c = new PriorityBlockingQueue<>();
        private final AtomicInteger d = new AtomicInteger();
        final AtomicInteger f = new AtomicInteger();
        volatile boolean q;

        c() {
        }

        public io.reactivex.disposables.b b(Runnable action) {
            return e(action, a(TimeUnit.MILLISECONDS));
        }

        public io.reactivex.disposables.b c(Runnable action, long delayTime, TimeUnit unit) {
            long execTime = a(TimeUnit.MILLISECONDS) + unit.toMillis(delayTime);
            return e(new a(action, this, execTime), execTime);
        }

        /* access modifiers changed from: package-private */
        public io.reactivex.disposables.b e(Runnable action, long execTime) {
            if (this.q) {
                return d.INSTANCE;
            }
            b timedRunnable = new b(action, Long.valueOf(execTime), this.f.incrementAndGet());
            this.c.add(timedRunnable);
            if (this.d.getAndIncrement() != 0) {
                return io.reactivex.disposables.c.b(new a(timedRunnable));
            }
            int missed = 1;
            while (!this.q) {
                b polled = this.c.poll();
                if (polled == null) {
                    missed = this.d.addAndGet(-missed);
                    if (missed == 0) {
                        return d.INSTANCE;
                    }
                } else if (!polled.q) {
                    polled.c.run();
                }
            }
            this.c.clear();
            return d.INSTANCE;
        }

        public void dispose() {
            this.q = true;
        }

        public boolean isDisposed() {
            return this.q;
        }

        /* compiled from: TrampolineScheduler */
        public final class a implements Runnable {
            final b c;

            a(b timedRunnable) {
                this.c = timedRunnable;
            }

            public void run() {
                this.c.q = true;
                c.this.c.remove(this.c);
            }
        }
    }

    /* compiled from: TrampolineScheduler */
    public static final class b implements Comparable<b> {
        final Runnable c;
        final long d;
        final int f;
        volatile boolean q;

        b(Runnable run, Long execTime, int count) {
            this.c = run;
            this.d = execTime.longValue();
            this.f = count;
        }

        /* renamed from: a */
        public int compareTo(b that) {
            int result = io.reactivex.internal.functions.b.b(this.d, that.d);
            if (result == 0) {
                return io.reactivex.internal.functions.b.a(this.f, that.f);
            }
            return result;
        }
    }

    /* compiled from: TrampolineScheduler */
    public static final class a implements Runnable {
        private final Runnable c;
        private final c d;
        private final long f;

        a(Runnable run, c worker, long execTime) {
            this.c = run;
            this.d = worker;
            this.f = execTime;
        }

        public void run() {
            if (!this.d.q) {
                long t = this.d.a(TimeUnit.MILLISECONDS);
                long j = this.f;
                if (j > t) {
                    try {
                        Thread.sleep(j - t);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        io.reactivex.plugins.a.q(e);
                        return;
                    }
                }
                if (!this.d.q) {
                    this.c.run();
                }
            }
        }
    }
}
