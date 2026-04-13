package io.netty.handler.flush;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import java.util.concurrent.Future;

public class FlushConsolidationHandler extends ChannelDuplexHandler {
    private final boolean consolidateWhenNoReadInProgress;
    /* access modifiers changed from: private */
    public ChannelHandlerContext ctx;
    private final int explicitFlushAfterFlushes;
    /* access modifiers changed from: private */
    public int flushPendingCount;
    private final Runnable flushTask;
    /* access modifiers changed from: private */
    public Future<?> nextScheduledFlush;
    /* access modifiers changed from: private */
    public boolean readInProgress;

    public FlushConsolidationHandler() {
        this(256, false);
    }

    public FlushConsolidationHandler(int explicitFlushAfterFlushes2) {
        this(explicitFlushAfterFlushes2, false);
    }

    public FlushConsolidationHandler(int explicitFlushAfterFlushes2, boolean consolidateWhenNoReadInProgress2) {
        if (explicitFlushAfterFlushes2 > 0) {
            this.explicitFlushAfterFlushes = explicitFlushAfterFlushes2;
            this.consolidateWhenNoReadInProgress = consolidateWhenNoReadInProgress2;
            this.flushTask = consolidateWhenNoReadInProgress2 ? new Runnable() {
                public void run() {
                    if (FlushConsolidationHandler.this.flushPendingCount > 0 && !FlushConsolidationHandler.this.readInProgress) {
                        int unused = FlushConsolidationHandler.this.flushPendingCount = 0;
                        FlushConsolidationHandler.this.ctx.flush();
                        Future unused2 = FlushConsolidationHandler.this.nextScheduledFlush = null;
                    }
                }
            } : null;
            return;
        }
        throw new IllegalArgumentException("explicitFlushAfterFlushes: " + explicitFlushAfterFlushes2 + " (expected: > 0)");
    }

    public void handlerAdded(ChannelHandlerContext ctx2) {
        this.ctx = ctx2;
    }

    public void flush(ChannelHandlerContext ctx2) {
        if (this.readInProgress) {
            int i = this.flushPendingCount + 1;
            this.flushPendingCount = i;
            if (i == this.explicitFlushAfterFlushes) {
                flushNow(ctx2);
            }
        } else if (this.consolidateWhenNoReadInProgress) {
            int i2 = this.flushPendingCount + 1;
            this.flushPendingCount = i2;
            if (i2 == this.explicitFlushAfterFlushes) {
                flushNow(ctx2);
            } else {
                scheduleFlush(ctx2);
            }
        } else {
            flushNow(ctx2);
        }
    }

    public void channelReadComplete(ChannelHandlerContext ctx2) {
        resetReadAndFlushIfNeeded(ctx2);
        ctx2.fireChannelReadComplete();
    }

    public void channelRead(ChannelHandlerContext ctx2, Object msg) {
        this.readInProgress = true;
        ctx2.fireChannelRead(msg);
    }

    public void exceptionCaught(ChannelHandlerContext ctx2, Throwable cause) {
        resetReadAndFlushIfNeeded(ctx2);
        ctx2.fireExceptionCaught(cause);
    }

    public void disconnect(ChannelHandlerContext ctx2, ChannelPromise promise) {
        resetReadAndFlushIfNeeded(ctx2);
        ctx2.disconnect(promise);
    }

    public void close(ChannelHandlerContext ctx2, ChannelPromise promise) {
        resetReadAndFlushIfNeeded(ctx2);
        ctx2.close(promise);
    }

    public void channelWritabilityChanged(ChannelHandlerContext ctx2) {
        if (!ctx2.channel().isWritable()) {
            flushIfNeeded(ctx2);
        }
        ctx2.fireChannelWritabilityChanged();
    }

    public void handlerRemoved(ChannelHandlerContext ctx2) {
        flushIfNeeded(ctx2);
    }

    private void resetReadAndFlushIfNeeded(ChannelHandlerContext ctx2) {
        this.readInProgress = false;
        flushIfNeeded(ctx2);
    }

    private void flushIfNeeded(ChannelHandlerContext ctx2) {
        if (this.flushPendingCount > 0) {
            flushNow(ctx2);
        }
    }

    private void flushNow(ChannelHandlerContext ctx2) {
        cancelScheduledFlush();
        this.flushPendingCount = 0;
        ctx2.flush();
    }

    private void scheduleFlush(ChannelHandlerContext ctx2) {
        if (this.nextScheduledFlush == null) {
            this.nextScheduledFlush = ctx2.channel().eventLoop().submit(this.flushTask);
        }
    }

    private void cancelScheduledFlush() {
        Future<?> future = this.nextScheduledFlush;
        if (future != null) {
            future.cancel(false);
            this.nextScheduledFlush = null;
        }
    }
}
