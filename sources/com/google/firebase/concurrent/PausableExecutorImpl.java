package com.google.firebase.concurrent;

import androidx.annotation.VisibleForTesting;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

public final class PausableExecutorImpl implements PausableExecutor {
    private final Executor delegate;
    private volatile boolean paused;
    @VisibleForTesting
    final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    PausableExecutorImpl(boolean paused2, Executor delegate2) {
        this.paused = paused2;
        this.delegate = delegate2;
    }

    public void execute(Runnable command) {
        this.queue.offer(command);
        maybeEnqueueNext();
    }

    private void maybeEnqueueNext() {
        if (!this.paused) {
            Runnable next = this.queue.poll();
            while (next != null) {
                this.delegate.execute(next);
                if (!this.paused) {
                    next = this.queue.poll();
                } else {
                    next = null;
                }
            }
        }
    }

    public void pause() {
        this.paused = true;
    }

    public void resume() {
        this.paused = false;
        maybeEnqueueNext();
    }

    public boolean isPaused() {
        return this.paused;
    }
}
