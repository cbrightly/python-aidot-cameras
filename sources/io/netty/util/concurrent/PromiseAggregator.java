package io.netty.util.concurrent;

import io.netty.util.concurrent.Future;
import java.util.LinkedHashSet;
import java.util.Set;

@Deprecated
public class PromiseAggregator<V, F extends Future<V>> implements GenericFutureListener<F> {
    private final Promise<?> aggregatePromise;
    private final boolean failPending;
    private Set<Promise<V>> pendingPromises;

    public PromiseAggregator(Promise<Void> aggregatePromise2, boolean failPending2) {
        if (aggregatePromise2 != null) {
            this.aggregatePromise = aggregatePromise2;
            this.failPending = failPending2;
            return;
        }
        throw new NullPointerException("aggregatePromise");
    }

    public PromiseAggregator(Promise<Void> aggregatePromise2) {
        this(aggregatePromise2, true);
    }

    @SafeVarargs
    public final PromiseAggregator<V, F> add(Promise<V>... promises) {
        int size;
        if (promises == null) {
            throw new NullPointerException("promises");
        } else if (promises.length == 0) {
            return this;
        } else {
            synchronized (this) {
                if (this.pendingPromises == null) {
                    if (promises.length > 1) {
                        size = promises.length;
                    } else {
                        size = 2;
                    }
                    this.pendingPromises = new LinkedHashSet(size);
                }
                for (Promise<V> p : promises) {
                    if (p != null) {
                        this.pendingPromises.add(p);
                        p.addListener(this);
                    }
                }
            }
            return this;
        }
    }

    public synchronized void operationComplete(F future) {
        Set<Promise<V>> set = this.pendingPromises;
        if (set == null) {
            this.aggregatePromise.setSuccess(null);
        } else {
            set.remove(future);
            if (!future.isSuccess()) {
                Throwable cause = future.cause();
                this.aggregatePromise.setFailure(cause);
                if (this.failPending) {
                    for (Promise<V> pendingFuture : this.pendingPromises) {
                        pendingFuture.setFailure(cause);
                    }
                }
            } else if (this.pendingPromises.isEmpty()) {
                this.aggregatePromise.setSuccess(null);
            }
        }
    }
}
