package io.reactivex.internal.schedulers;

import io.reactivex.disposables.b;
import io.reactivex.internal.functions.a;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: InstantPeriodicTask */
public final class e implements Callable<Void>, b {
    static final FutureTask<Void> c = new FutureTask<>(a.b, (Object) null);
    final Runnable d;
    final AtomicReference<Future<?>> f = new AtomicReference<>();
    final AtomicReference<Future<?>> q = new AtomicReference<>();
    final ExecutorService x;
    Thread y;

    e(Runnable task, ExecutorService executor) {
        this.d = task;
        this.x = executor;
    }

    /* renamed from: a */
    public Void call() {
        this.y = Thread.currentThread();
        try {
            this.d.run();
            c(this.x.submit(this));
            this.y = null;
        } catch (Throwable ex) {
            this.y = null;
            io.reactivex.plugins.a.q(ex);
        }
        return null;
    }

    public void dispose() {
        AtomicReference<Future<?>> atomicReference = this.q;
        Future<?> future = c;
        Future<?> current = (Future) atomicReference.getAndSet(future);
        boolean z = true;
        if (!(current == null || current == future)) {
            current.cancel(this.y != Thread.currentThread());
        }
        Future<?> current2 = this.f.getAndSet(future);
        if (current2 != null && current2 != future) {
            if (this.y == Thread.currentThread()) {
                z = false;
            }
            current2.cancel(z);
        }
    }

    public boolean isDisposed() {
        return this.q.get() == c;
    }

    /* access modifiers changed from: package-private */
    public void b(Future<?> f2) {
        Future<?> current;
        do {
            current = this.q.get();
            if (current == c) {
                f2.cancel(this.y != Thread.currentThread());
                return;
            }
        } while (!this.q.compareAndSet(current, f2));
    }

    /* access modifiers changed from: package-private */
    public void c(Future<?> f2) {
        Future<?> current;
        do {
            current = this.f.get();
            if (current == c) {
                f2.cancel(this.y != Thread.currentThread());
                return;
            }
        } while (!this.f.compareAndSet(current, f2));
    }
}
