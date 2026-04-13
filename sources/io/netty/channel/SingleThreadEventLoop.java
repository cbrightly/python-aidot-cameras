package io.netty.channel;

import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.concurrent.RejectedExecutionHandlers;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import io.netty.util.internal.SystemPropertyUtil;
import java.util.concurrent.ThreadFactory;

public abstract class SingleThreadEventLoop extends SingleThreadEventExecutor implements EventLoop {
    protected static final int DEFAULT_MAX_PENDING_TASKS = Math.max(16, SystemPropertyUtil.getInt("io.netty.eventLoop.maxPendingTasks", Integer.MAX_VALUE));

    public interface NonWakeupRunnable extends Runnable {
    }

    protected SingleThreadEventLoop(EventLoopGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp) {
        this(parent, threadFactory, addTaskWakesUp, DEFAULT_MAX_PENDING_TASKS, RejectedExecutionHandlers.reject());
    }

    protected SingleThreadEventLoop(EventLoopGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp, int maxPendingTasks, RejectedExecutionHandler rejectedExecutionHandler) {
        super(parent, threadFactory, addTaskWakesUp, maxPendingTasks, rejectedExecutionHandler);
    }

    public EventLoopGroup parent() {
        return (EventLoopGroup) super.parent();
    }

    public EventLoop next() {
        return (EventLoop) super.next();
    }

    public ChannelFuture register(Channel channel) {
        return register(channel, new DefaultChannelPromise(channel, this));
    }

    public ChannelFuture register(Channel channel, ChannelPromise promise) {
        if (channel == null) {
            throw new NullPointerException("channel");
        } else if (promise != null) {
            channel.unsafe().register(this, promise);
            return promise;
        } else {
            throw new NullPointerException("promise");
        }
    }

    /* access modifiers changed from: protected */
    public boolean wakesUpForTask(Runnable task) {
        return !(task instanceof NonWakeupRunnable);
    }
}
