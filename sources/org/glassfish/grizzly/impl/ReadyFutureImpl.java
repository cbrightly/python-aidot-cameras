package org.glassfish.grizzly.impl;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.ThreadCache;

public final class ReadyFutureImpl<R> implements FutureImpl<R> {
    private static final ThreadCache.CachedTypeIndex<ReadyFutureImpl> CACHE_IDX = ThreadCache.obtainIndex(ReadyFutureImpl.class, 4);
    private Throwable failure;
    private boolean isCancelled;
    protected R result;

    public static <R> ReadyFutureImpl<R> create() {
        ReadyFutureImpl<R> future = takeFromCache();
        if (future == null) {
            return new ReadyFutureImpl<>();
        }
        future.isCancelled = true;
        return future;
    }

    public static <R> ReadyFutureImpl<R> create(R result2) {
        ReadyFutureImpl<R> future = takeFromCache();
        if (future == null) {
            return new ReadyFutureImpl<>(result2);
        }
        future.result = result2;
        return future;
    }

    public static <R> ReadyFutureImpl<R> create(Throwable failure2) {
        ReadyFutureImpl<R> future = takeFromCache();
        if (future == null) {
            return new ReadyFutureImpl<>(failure2);
        }
        future.failure = failure2;
        return future;
    }

    private static <R> ReadyFutureImpl<R> takeFromCache() {
        return (ReadyFutureImpl) ThreadCache.takeFromCache(CACHE_IDX);
    }

    private ReadyFutureImpl() {
        this((Object) null, (Throwable) null, true);
    }

    private ReadyFutureImpl(R result2) {
        this(result2, (Throwable) null, false);
    }

    private ReadyFutureImpl(Throwable failure2) {
        this((Object) null, failure2, false);
    }

    private ReadyFutureImpl(R result2, Throwable failure2, boolean isCancelled2) {
        this.result = result2;
        this.failure = failure2;
        this.isCancelled = isCancelled2;
    }

    public void addCompletionHandler(CompletionHandler<R> completionHandler) {
        if (this.isCancelled) {
            completionHandler.cancelled();
            return;
        }
        Throwable th = this.failure;
        if (th != null) {
            completionHandler.failed(th);
        } else {
            completionHandler.completed(this.result);
        }
    }

    public R getResult() {
        return this.result;
    }

    public void setResult(R r) {
        throw new IllegalStateException("Can not be reset on ReadyFutureImpl");
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return this.isCancelled;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public boolean isDone() {
        return true;
    }

    public R get() {
        if (this.isCancelled) {
            throw new CancellationException();
        } else if (this.failure == null) {
            return this.result;
        } else {
            throw new ExecutionException(this.failure);
        }
    }

    public R get(long timeout, TimeUnit unit) {
        if (this.isCancelled) {
            throw new CancellationException();
        } else if (this.failure == null) {
            R r = this.result;
            if (r != null) {
                return r;
            }
            throw new TimeoutException();
        } else {
            throw new ExecutionException(this.failure);
        }
    }

    public void failure(Throwable failure2) {
        throw new IllegalStateException("Can not be reset on ReadyFutureImpl");
    }

    public void result(R r) {
        throw new IllegalStateException("Can not be reset on ReadyFutureImpl");
    }

    private void reset() {
        this.result = null;
        this.failure = null;
        this.isCancelled = false;
    }

    public void markForRecycle(boolean recycleResult) {
        recycle(recycleResult);
    }

    public void recycle() {
        recycle(false);
    }

    public void recycle(boolean recycleResult) {
        R r;
        if (recycleResult && (r = this.result) != null && (r instanceof Cacheable)) {
            ((Cacheable) r).recycle();
        }
        reset();
        ThreadCache.putToCache(CACHE_IDX, this);
    }
}
