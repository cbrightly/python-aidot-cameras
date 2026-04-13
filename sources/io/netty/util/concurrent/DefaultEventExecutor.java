package io.netty.util.concurrent;

import java.util.concurrent.ThreadFactory;

public final class DefaultEventExecutor extends SingleThreadEventExecutor {
    DefaultEventExecutor(DefaultEventExecutorGroup parent, ThreadFactory threadFactory) {
        super(parent, threadFactory, true);
    }

    DefaultEventExecutor(DefaultEventExecutorGroup parent, ThreadFactory threadFactory, int maxPendingTasks, RejectedExecutionHandler rejectedExecutionHandler) {
        super(parent, threadFactory, true, maxPendingTasks, rejectedExecutionHandler);
    }

    /* access modifiers changed from: protected */
    public void run() {
        do {
            Runnable task = takeTask();
            if (task != null) {
                task.run();
                updateLastExecutionTime();
            }
        } while (!confirmShutdown());
    }
}
