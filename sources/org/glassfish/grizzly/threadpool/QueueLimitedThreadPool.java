package org.glassfish.grizzly.threadpool;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import org.glassfish.grizzly.threadpool.AbstractThreadPool;

public final class QueueLimitedThreadPool extends FixedThreadPool {
    private final Semaphore queuePermits;

    QueueLimitedThreadPool(ThreadPoolConfig config) {
        super(config);
        if (config.getQueueLimit() >= 0) {
            this.queuePermits = new Semaphore(config.getQueueLimit());
            return;
        }
        throw new IllegalArgumentException("maxQueuedTasks < 0");
    }

    public void execute(Runnable command) {
        if (command == null) {
            throw new IllegalArgumentException("Runnable task is null");
        } else if (this.running) {
            if (!this.queuePermits.tryAcquire()) {
                onTaskQueueOverflow();
            }
            if (!this.workQueue.offer(command)) {
                this.queuePermits.release();
                onTaskQueueOverflow();
            }
            onTaskQueued(command);
        } else {
            throw new RejectedExecutionException("ThreadPool is not running");
        }
    }

    /* access modifiers changed from: protected */
    public void beforeExecute(AbstractThreadPool.Worker worker, Thread t, Runnable r) {
        super.beforeExecute(worker, t, r);
        this.queuePermits.release();
    }
}
