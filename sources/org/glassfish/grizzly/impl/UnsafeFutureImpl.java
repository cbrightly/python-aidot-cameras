package org.glassfish.grizzly.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.ThreadCache;

public final class UnsafeFutureImpl<R> implements FutureImpl<R> {
    private static final ThreadCache.CachedTypeIndex<UnsafeFutureImpl> CACHE_IDX = ThreadCache.obtainIndex(UnsafeFutureImpl.class, 4);
    protected Set<CompletionHandler<R>> completionHandlers;
    protected Throwable failure;
    protected boolean isCancelled;
    protected boolean isDone;
    protected int recycleMark;
    protected R result;

    public static <R> UnsafeFutureImpl<R> create() {
        UnsafeFutureImpl<R> future = (UnsafeFutureImpl) ThreadCache.takeFromCache(CACHE_IDX);
        if (future != null) {
            return future;
        }
        return new UnsafeFutureImpl<>();
    }

    private UnsafeFutureImpl() {
    }

    public void addCompletionHandler(CompletionHandler<R> completionHandler) {
        if (this.isDone) {
            notifyCompletionHandler(completionHandler);
            return;
        }
        if (this.completionHandlers == null) {
            this.completionHandlers = new HashSet(2);
        }
        this.completionHandlers.add(completionHandler);
    }

    public R getResult() {
        return this.result;
    }

    public void result(R result2) {
        this.result = result2;
        notifyHaveResult();
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        this.isCancelled = true;
        notifyHaveResult();
        return true;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public R get() {
        if (this.isDone) {
            if (this.isCancelled) {
                throw new CancellationException();
            } else if (this.failure == null) {
                R r = this.result;
                if (r != null) {
                    return r;
                }
            } else {
                throw new ExecutionException(this.failure);
            }
        }
        throw new ExecutionException(new IllegalStateException("Result is not ready"));
    }

    public R get(long timeout, TimeUnit unit) {
        return get();
    }

    public void failure(Throwable failure2) {
        this.failure = failure2;
        notifyHaveResult();
    }

    /* access modifiers changed from: protected */
    public void notifyHaveResult() {
        int i = this.recycleMark;
        boolean z = true;
        if (i == 0) {
            this.isDone = true;
            notifyCompletionHandlers();
            return;
        }
        if (i != 2) {
            z = false;
        }
        recycle(z);
    }

    private void notifyCompletionHandlers() {
        Set<CompletionHandler<R>> set = this.completionHandlers;
        if (set != null) {
            for (CompletionHandler<R> completionHandler : set) {
                notifyCompletionHandler(completionHandler);
            }
            this.completionHandlers = null;
        }
    }

    private void notifyCompletionHandler(CompletionHandler<R> completionHandler) {
        try {
            if (this.isCancelled) {
                completionHandler.cancelled();
                return;
            }
            Throwable th = this.failure;
            if (th != null) {
                completionHandler.failed(th);
                return;
            }
            R r = this.result;
            if (r != null) {
                completionHandler.completed(r);
            }
        } catch (Exception e) {
        }
    }

    public void markForRecycle(boolean recycleResult) {
        if (this.isDone) {
            recycle(recycleResult);
        } else {
            this.recycleMark = (recycleResult) + true;
        }
    }

    /* access modifiers changed from: protected */
    public void reset() {
        this.completionHandlers = null;
        this.result = null;
        this.failure = null;
        this.isCancelled = false;
        this.isDone = false;
        this.recycleMark = 0;
    }

    public void recycle(boolean recycleResult) {
        R r;
        if (recycleResult && (r = this.result) != null && (r instanceof Cacheable)) {
            ((Cacheable) r).recycle();
        }
        reset();
        ThreadCache.putToCache(CACHE_IDX, this);
    }

    public void recycle() {
        recycle(false);
    }
}
