package io.reactivex.internal.schedulers;

import java.util.concurrent.Callable;

/* compiled from: ScheduledDirectTask */
public final class k extends a implements Callable<Void> {
    private static final long serialVersionUID = 1811839108042568751L;

    public /* bridge */ /* synthetic */ Runnable getWrappedRunnable() {
        return super.getWrappedRunnable();
    }

    public k(Runnable runnable) {
        super(runnable);
    }

    public Void call() {
        this.runner = Thread.currentThread();
        try {
            this.runnable.run();
            return null;
        } finally {
            lazySet(a.FINISHED);
            this.runner = null;
        }
    }
}
