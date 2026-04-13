package io.reactivex.internal.schedulers;

import io.reactivex.plugins.a;

/* compiled from: ScheduledDirectPeriodicTask */
public final class j extends a implements Runnable {
    private static final long serialVersionUID = 1811839108042568751L;

    public /* bridge */ /* synthetic */ Runnable getWrappedRunnable() {
        return super.getWrappedRunnable();
    }

    public j(Runnable runnable) {
        super(runnable);
    }

    public void run() {
        this.runner = Thread.currentThread();
        try {
            this.runnable.run();
            this.runner = null;
        } catch (Throwable ex) {
            this.runner = null;
            lazySet(a.FINISHED);
            a.q(ex);
        }
    }
}
