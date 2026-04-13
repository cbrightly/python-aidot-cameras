package io.reactivex.internal.schedulers;

import io.reactivex.disposables.b;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* compiled from: DisposeOnCancel */
public final class c implements Future<Object> {
    final b c;

    c(b d) {
        this.c = d;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        this.c.dispose();
        return false;
    }

    public boolean isCancelled() {
        return false;
    }

    public boolean isDone() {
        return false;
    }

    public Object get() {
        return null;
    }

    public Object get(long timeout, TimeUnit unit) {
        return null;
    }
}
