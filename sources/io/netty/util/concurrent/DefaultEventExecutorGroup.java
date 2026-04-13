package io.netty.util.concurrent;

import java.util.concurrent.ThreadFactory;

public class DefaultEventExecutorGroup extends MultithreadEventExecutorGroup {
    public DefaultEventExecutorGroup(int nThreads) {
        this(nThreads, (ThreadFactory) null);
    }

    public DefaultEventExecutorGroup(int nThreads, ThreadFactory threadFactory) {
        this(nThreads, threadFactory, SingleThreadEventExecutor.DEFAULT_MAX_PENDING_EXECUTOR_TASKS, RejectedExecutionHandlers.reject());
    }

    public DefaultEventExecutorGroup(int nThreads, ThreadFactory threadFactory, int maxPendingTasks, RejectedExecutionHandler rejectedHandler) {
        super(nThreads, threadFactory, Integer.valueOf(maxPendingTasks), rejectedHandler);
    }

    /* access modifiers changed from: protected */
    public EventExecutor newChild(ThreadFactory threadFactory, Object... args) {
        return new DefaultEventExecutor(this, threadFactory, args[0].intValue(), args[1]);
    }
}
