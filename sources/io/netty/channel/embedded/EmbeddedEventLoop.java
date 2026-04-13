package io.netty.channel.embedded;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.AbstractScheduledEventExecutor;
import io.netty.util.concurrent.Future;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public final class EmbeddedEventLoop extends AbstractScheduledEventExecutor implements EventLoop {
    private final Queue<Runnable> tasks = new ArrayDeque(2);

    EmbeddedEventLoop() {
    }

    public void execute(Runnable command) {
        if (command != null) {
            this.tasks.add(command);
            return;
        }
        throw new NullPointerException("command");
    }

    /* access modifiers changed from: package-private */
    public void runTasks() {
        while (true) {
            Runnable task = this.tasks.poll();
            if (task != null) {
                task.run();
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public long runScheduledTasks() {
        long time = AbstractScheduledEventExecutor.nanoTime();
        while (true) {
            Runnable task = pollScheduledTask(time);
            if (task == null) {
                return nextScheduledTaskNano();
            }
            task.run();
        }
    }

    /* access modifiers changed from: package-private */
    public long nextScheduledTask() {
        return nextScheduledTaskNano();
    }

    /* access modifiers changed from: protected */
    public void cancelScheduledTasks() {
        super.cancelScheduledTasks();
    }

    public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    public Future<?> terminationFuture() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void shutdown() {
        throw new UnsupportedOperationException();
    }

    public boolean isShuttingDown() {
        return false;
    }

    public boolean isShutdown() {
        return false;
    }

    public boolean isTerminated() {
        return false;
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) {
        Thread.sleep(unit.toMillis(timeout));
        return false;
    }

    public ChannelFuture register(Channel channel) {
        return register(channel, new DefaultChannelPromise(channel, this));
    }

    public ChannelFuture register(Channel channel, ChannelPromise promise) {
        channel.unsafe().register(this, promise);
        return promise;
    }

    public boolean inEventLoop() {
        return true;
    }

    public boolean inEventLoop(Thread thread) {
        return true;
    }

    public EventLoop next() {
        return this;
    }

    public EventLoopGroup parent() {
        return this;
    }
}
