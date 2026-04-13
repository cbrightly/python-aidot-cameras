package com.google.firebase.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public final class PausableExecutorServiceImpl implements PausableExecutorService {
    private final ExecutorService delegateService;
    private final PausableExecutor pausableDelegate;

    PausableExecutorServiceImpl(boolean paused, ExecutorService delegate) {
        this.delegateService = delegate;
        this.pausableDelegate = new PausableExecutorImpl(paused, delegate);
    }

    public void execute(Runnable command) {
        this.pausableDelegate.execute(command);
    }

    public void pause() {
        this.pausableDelegate.pause();
    }

    public void resume() {
        this.pausableDelegate.resume();
    }

    public boolean isPaused() {
        return this.pausableDelegate.isPaused();
    }

    public void shutdown() {
        throw new UnsupportedOperationException("Shutting down is not allowed.");
    }

    public List<Runnable> shutdownNow() {
        throw new UnsupportedOperationException("Shutting down is not allowed.");
    }

    public boolean isShutdown() {
        return this.delegateService.isShutdown();
    }

    public boolean isTerminated() {
        return this.delegateService.isTerminated();
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) {
        return this.delegateService.awaitTermination(timeout, unit);
    }

    public <T> Future<T> submit(Callable<T> task) {
        FutureTask<T> ft = new FutureTask<>(task);
        execute(ft);
        return ft;
    }

    public <T> Future<T> submit(Runnable task, T result) {
        return submit(new y(task, result));
    }

    public Future<?> submit(Runnable task) {
        return submit(new z(task));
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) {
        return this.delegateService.invokeAll(tasks);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) {
        return this.delegateService.invokeAll(tasks, timeout, unit);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) {
        return this.delegateService.invokeAny(tasks);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) {
        return this.delegateService.invokeAny(tasks, timeout, unit);
    }
}
