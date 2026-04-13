package com.google.firebase.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public final class LimitedConcurrencyExecutorService extends LimitedConcurrencyExecutor implements ExecutorService {
    private final ExecutorService delegate;

    LimitedConcurrencyExecutorService(ExecutorService delegate2, int concurrency) {
        super(delegate2, concurrency);
        this.delegate = delegate2;
    }

    public void shutdown() {
        throw new UnsupportedOperationException("Shutting down is not allowed.");
    }

    public List<Runnable> shutdownNow() {
        throw new UnsupportedOperationException("Shutting down is not allowed.");
    }

    public boolean isShutdown() {
        return this.delegate.isShutdown();
    }

    public boolean isTerminated() {
        return this.delegate.isTerminated();
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) {
        return this.delegate.awaitTermination(timeout, unit);
    }

    public <T> Future<T> submit(Callable<T> task) {
        FutureTask<T> ft = new FutureTask<>(task);
        execute(ft);
        return ft;
    }

    public <T> Future<T> submit(Runnable task, T result) {
        return submit(new x(task, result));
    }

    public Future<?> submit(Runnable task) {
        return submit(new w(task));
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) {
        return this.delegate.invokeAll(tasks);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) {
        return this.delegate.invokeAll(tasks, timeout, unit);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) {
        return this.delegate.invokeAny(tasks);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) {
        return this.delegate.invokeAny(tasks, timeout, unit);
    }
}
