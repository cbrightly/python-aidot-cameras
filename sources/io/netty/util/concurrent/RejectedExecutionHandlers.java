package io.netty.util.concurrent;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public final class RejectedExecutionHandlers {
    private static final RejectedExecutionHandler REJECT = new RejectedExecutionHandler() {
        public void rejected(Runnable task, SingleThreadEventExecutor executor) {
            throw new RejectedExecutionException();
        }
    };

    private RejectedExecutionHandlers() {
    }

    public static RejectedExecutionHandler reject() {
        return REJECT;
    }

    public static RejectedExecutionHandler backoff(final int retries, long backoffAmount, TimeUnit unit) {
        if (retries > 0) {
            final long backOffNanos = unit.toNanos(backoffAmount);
            return new RejectedExecutionHandler() {
                public void rejected(Runnable task, SingleThreadEventExecutor executor) {
                    if (!executor.inEventLoop()) {
                        int i = 0;
                        while (i < retries) {
                            executor.wakeup(false);
                            LockSupport.parkNanos(backOffNanos);
                            if (!executor.offerTask(task)) {
                                i++;
                            } else {
                                return;
                            }
                        }
                    }
                    throw new RejectedExecutionException();
                }
            };
        }
        throw new IllegalArgumentException(retries + ": " + retries + " (expected: > 0)");
    }
}
