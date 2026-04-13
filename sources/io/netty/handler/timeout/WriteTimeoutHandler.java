package io.netty.handler.timeout;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class WriteTimeoutHandler extends ChannelOutboundHandlerAdapter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1);
    private boolean closed;
    private WriteTimeoutTask lastTask;
    private final long timeoutNanos;

    public WriteTimeoutHandler(int timeoutSeconds) {
        this((long) timeoutSeconds, TimeUnit.SECONDS);
    }

    public WriteTimeoutHandler(long timeout, TimeUnit unit) {
        if (unit == null) {
            throw new NullPointerException("unit");
        } else if (timeout <= 0) {
            this.timeoutNanos = 0;
        } else {
            this.timeoutNanos = Math.max(unit.toNanos(timeout), MIN_TIMEOUT_NANOS);
        }
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        scheduleTimeout(ctx, promise);
        ctx.write(msg, promise);
    }

    public void handlerRemoved(ChannelHandlerContext ctx) {
        WriteTimeoutTask task = this.lastTask;
        this.lastTask = null;
        while (task != null) {
            task.scheduledFuture.cancel(false);
            WriteTimeoutTask prev = task.prev;
            task.prev = null;
            task.next = null;
            task = prev;
        }
    }

    private void scheduleTimeout(ChannelHandlerContext ctx, ChannelPromise promise) {
        WriteTimeoutTask task = new WriteTimeoutTask(ctx, promise);
        ScheduledFuture<?> schedule = ctx.executor().schedule((Runnable) task, this.timeoutNanos, TimeUnit.NANOSECONDS);
        task.scheduledFuture = schedule;
        if (!schedule.isDone()) {
            addWriteTimeoutTask(task);
            promise.addListener(task);
        }
    }

    private void addWriteTimeoutTask(WriteTimeoutTask task) {
        WriteTimeoutTask writeTimeoutTask = this.lastTask;
        if (writeTimeoutTask == null) {
            this.lastTask = task;
            return;
        }
        writeTimeoutTask.next = task;
        task.prev = writeTimeoutTask;
        this.lastTask = task;
    }

    /* access modifiers changed from: private */
    public void removeWriteTimeoutTask(WriteTimeoutTask task) {
        WriteTimeoutTask writeTimeoutTask = this.lastTask;
        if (task != writeTimeoutTask) {
            WriteTimeoutTask writeTimeoutTask2 = task.prev;
            if (writeTimeoutTask2 != null || task.next != null) {
                if (writeTimeoutTask2 == null) {
                    task.next.prev = null;
                } else {
                    writeTimeoutTask2.next = task.next;
                    task.next.prev = writeTimeoutTask2;
                }
            } else {
                return;
            }
        } else if (task.next == null) {
            WriteTimeoutTask writeTimeoutTask3 = writeTimeoutTask.prev;
            this.lastTask = writeTimeoutTask3;
            if (writeTimeoutTask3 != null) {
                writeTimeoutTask3.next = null;
            }
        } else {
            throw new AssertionError();
        }
        task.prev = null;
        task.next = null;
    }

    /* access modifiers changed from: protected */
    public void writeTimedOut(ChannelHandlerContext ctx) {
        if (!this.closed) {
            ctx.fireExceptionCaught(WriteTimeoutException.INSTANCE);
            ctx.close();
            this.closed = true;
        }
    }

    public final class WriteTimeoutTask implements Runnable, ChannelFutureListener {
        private final ChannelHandlerContext ctx;
        WriteTimeoutTask next;
        WriteTimeoutTask prev;
        private final ChannelPromise promise;
        java.util.concurrent.ScheduledFuture<?> scheduledFuture;

        WriteTimeoutTask(ChannelHandlerContext ctx2, ChannelPromise promise2) {
            this.ctx = ctx2;
            this.promise = promise2;
        }

        public void run() {
            if (!this.promise.isDone()) {
                try {
                    WriteTimeoutHandler.this.writeTimedOut(this.ctx);
                } catch (Throwable t) {
                    this.ctx.fireExceptionCaught(t);
                }
            }
            WriteTimeoutHandler.this.removeWriteTimeoutTask(this);
        }

        public void operationComplete(ChannelFuture future) {
            this.scheduledFuture.cancel(false);
            WriteTimeoutHandler.this.removeWriteTimeoutTask(this);
        }
    }
}
