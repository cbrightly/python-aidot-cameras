package com.google.firebase.concurrent;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class PausableScheduledExecutorServiceImpl extends DelegatingScheduledExecutorService implements PausableScheduledExecutorService {
    private final PausableExecutorService delegate;

    PausableScheduledExecutorServiceImpl(PausableExecutorService delegate2, ScheduledExecutorService scheduler) {
        super(delegate2, scheduler);
        this.delegate = delegate2;
    }

    public void pause() {
        this.delegate.pause();
    }

    public void resume() {
        this.delegate.resume();
    }

    public boolean isPaused() {
        return this.delegate.isPaused();
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }
}
