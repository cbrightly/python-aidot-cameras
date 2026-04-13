package io.reactivex.internal.schedulers;

import io.reactivex.disposables.b;
import io.reactivex.internal.disposables.d;
import io.reactivex.plugins.a;
import io.reactivex.r;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/* compiled from: NewThreadWorker */
public class h extends r.c implements b {
    private final ScheduledExecutorService c;
    volatile boolean d;

    public h(ThreadFactory threadFactory) {
        this.c = m.a(threadFactory);
    }

    public b b(Runnable run) {
        return c(run, 0, (TimeUnit) null);
    }

    public b c(Runnable action, long delayTime, TimeUnit unit) {
        if (this.d) {
            return d.INSTANCE;
        }
        return e(action, delayTime, unit, (io.reactivex.internal.disposables.b) null);
    }

    public b f(Runnable run, long delayTime, TimeUnit unit) {
        Future<?> f;
        k task = new k(a.t(run));
        if (delayTime <= 0) {
            try {
                f = this.c.submit(task);
            } catch (RejectedExecutionException ex) {
                a.q(ex);
                return d.INSTANCE;
            }
        } else {
            f = this.c.schedule(task, delayTime, unit);
        }
        task.setFuture(f);
        return task;
    }

    public b g(Runnable run, long initialDelay, long period, TimeUnit unit) {
        Future<?> f;
        Runnable decoratedRun = a.t(run);
        if (period <= 0) {
            e periodicWrapper = new e(decoratedRun, this.c);
            if (initialDelay <= 0) {
                try {
                    f = this.c.submit(periodicWrapper);
                } catch (RejectedExecutionException ex) {
                    a.q(ex);
                    return d.INSTANCE;
                }
            } else {
                f = this.c.schedule(periodicWrapper, initialDelay, unit);
            }
            periodicWrapper.b(f);
            return periodicWrapper;
        }
        j task = new j(decoratedRun);
        try {
            task.setFuture(this.c.scheduleAtFixedRate(task, initialDelay, period, unit));
            return task;
        } catch (RejectedExecutionException ex2) {
            a.q(ex2);
            return d.INSTANCE;
        }
    }

    public l e(Runnable run, long delayTime, TimeUnit unit, io.reactivex.internal.disposables.b parent) {
        Future<?> f;
        l sr = new l(a.t(run), parent);
        if (parent != null && !parent.b(sr)) {
            return sr;
        }
        if (delayTime <= 0) {
            try {
                f = this.c.submit(sr);
            } catch (RejectedExecutionException ex) {
                if (parent != null) {
                    parent.a(sr);
                }
                a.q(ex);
            }
        } else {
            f = this.c.schedule(sr, delayTime, unit);
        }
        sr.setFuture(f);
        return sr;
    }

    public void dispose() {
        if (!this.d) {
            this.d = true;
            this.c.shutdownNow();
        }
    }

    public void h() {
        if (!this.d) {
            this.d = true;
            this.c.shutdown();
        }
    }

    public boolean isDisposed() {
        return this.d;
    }
}
