package com.google.firebase.concurrent;

import com.google.firebase.components.Preconditions;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class LimitedConcurrencyExecutor implements Executor {
    private final Executor delegate;
    private final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private final Semaphore semaphore;

    LimitedConcurrencyExecutor(Executor delegate2, int concurrency) {
        Preconditions.checkArgument(concurrency > 0, "concurrency must be positive.");
        this.delegate = delegate2;
        this.semaphore = new Semaphore(concurrency, true);
    }

    public void execute(Runnable command) {
        this.queue.offer(command);
        maybeEnqueueNext();
    }

    private void maybeEnqueueNext() {
        while (this.semaphore.tryAcquire()) {
            Runnable next = this.queue.poll();
            if (next != null) {
                this.delegate.execute(decorate(next));
            } else {
                this.semaphore.release();
                return;
            }
        }
    }

    private Runnable decorate(Runnable command) {
        return new v(this, command);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$decorate$0 */
    public /* synthetic */ void a(Runnable command) {
        try {
            command.run();
        } finally {
            this.semaphore.release();
            maybeEnqueueNext();
        }
    }
}
