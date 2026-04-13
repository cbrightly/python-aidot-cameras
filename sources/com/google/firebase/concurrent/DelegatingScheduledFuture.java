package com.google.firebase.concurrent;

import android.annotation.SuppressLint;
import androidx.concurrent.futures.AbstractResolvableFuture;
import java.util.concurrent.Delayed;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@SuppressLint({"RestrictedApi"})
public class DelegatingScheduledFuture<V> extends AbstractResolvableFuture<V> implements ScheduledFuture<V> {
    private final ScheduledFuture<?> upstreamFuture;

    public interface Completer<T> {
        void set(T t);

        void setException(Throwable th);
    }

    public interface Resolver<T> {
        ScheduledFuture<?> addCompleter(Completer<T> completer);
    }

    DelegatingScheduledFuture(Resolver<V> resolver) {
        this.upstreamFuture = resolver.addCompleter(new Completer<V>() {
            public void set(V value) {
                boolean unused = DelegatingScheduledFuture.this.set(value);
            }

            public void setException(Throwable ex) {
                boolean unused = DelegatingScheduledFuture.this.setException(ex);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void afterDone() {
        this.upstreamFuture.cancel(wasInterrupted());
    }

    public long getDelay(TimeUnit unit) {
        return this.upstreamFuture.getDelay(unit);
    }

    public int compareTo(Delayed o) {
        return this.upstreamFuture.compareTo(o);
    }
}
