package io.netty.handler.traffic;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.EventExecutor;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class ChannelTrafficShapingHandler extends AbstractTrafficShapingHandler {
    private final ArrayDeque<ToSend> messagesQueue = new ArrayDeque<>();
    private long queueSize;

    public ChannelTrafficShapingHandler(long writeLimit, long readLimit, long checkInterval, long maxTime) {
        super(writeLimit, readLimit, checkInterval, maxTime);
    }

    public ChannelTrafficShapingHandler(long writeLimit, long readLimit, long checkInterval) {
        super(writeLimit, readLimit, checkInterval);
    }

    public ChannelTrafficShapingHandler(long writeLimit, long readLimit) {
        super(writeLimit, readLimit);
    }

    public ChannelTrafficShapingHandler(long checkInterval) {
        super(checkInterval);
    }

    public void handlerAdded(ChannelHandlerContext ctx) {
        EventExecutor executor = ctx.executor();
        TrafficCounter trafficCounter = new TrafficCounter(this, executor, "ChannelTC" + ctx.channel().hashCode(), this.checkInterval);
        setTrafficCounter(trafficCounter);
        trafficCounter.start();
        super.handlerAdded(ctx);
    }

    public void handlerRemoved(ChannelHandlerContext ctx) {
        this.trafficCounter.stop();
        synchronized (this) {
            if (ctx.channel().isActive()) {
                Iterator<ToSend> it = this.messagesQueue.iterator();
                while (it.hasNext()) {
                    ToSend toSend = it.next();
                    long size = calculateSize(toSend.toSend);
                    this.trafficCounter.bytesRealWriteFlowControl(size);
                    this.queueSize -= size;
                    ctx.write(toSend.toSend, toSend.promise);
                }
            } else {
                Iterator<ToSend> it2 = this.messagesQueue.iterator();
                while (it2.hasNext()) {
                    Object obj = it2.next().toSend;
                    if (obj instanceof ByteBuf) {
                        ((ByteBuf) obj).release();
                    }
                }
            }
            this.messagesQueue.clear();
        }
        releaseWriteSuspended(ctx);
        releaseReadSuspended(ctx);
        super.handlerRemoved(ctx);
    }

    public static final class ToSend {
        final ChannelPromise promise;
        final long relativeTimeAction;
        final Object toSend;

        private ToSend(long delay, Object toSend2, ChannelPromise promise2) {
            this.relativeTimeAction = delay;
            this.toSend = toSend2;
            this.promise = promise2;
        }
    }

    /* access modifiers changed from: package-private */
    public void submitWrite(ChannelHandlerContext ctx, Object msg, long size, long delay, long now, ChannelPromise promise) {
        final ChannelHandlerContext channelHandlerContext = ctx;
        long j = size;
        long j2 = delay;
        synchronized (this) {
            if (j2 == 0) {
                try {
                    if (this.messagesQueue.isEmpty()) {
                        this.trafficCounter.bytesRealWriteFlowControl(j);
                        channelHandlerContext.write(msg, promise);
                        return;
                    }
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            }
            Object obj = msg;
            ChannelPromise channelPromise = promise;
            ToSend toSend = new ToSend(j2 + now, msg, promise);
            this.messagesQueue.addLast(toSend);
            long j3 = this.queueSize + j;
            this.queueSize = j3;
            checkWriteSuspend(ctx, delay, j3);
            final long futureNow = toSend.relativeTimeAction;
            ctx.executor().schedule((Runnable) new Runnable() {
                public void run() {
                    ChannelTrafficShapingHandler.this.sendAllValid(channelHandlerContext, futureNow);
                }
            }, j2, TimeUnit.MILLISECONDS);
        }
    }

    /* access modifiers changed from: private */
    public void sendAllValid(ChannelHandlerContext ctx, long now) {
        synchronized (this) {
            ToSend newToSend = this.messagesQueue.pollFirst();
            while (true) {
                if (newToSend != null) {
                    if (newToSend.relativeTimeAction > now) {
                        this.messagesQueue.addFirst(newToSend);
                        break;
                    }
                    long size = calculateSize(newToSend.toSend);
                    this.trafficCounter.bytesRealWriteFlowControl(size);
                    this.queueSize -= size;
                    ctx.write(newToSend.toSend, newToSend.promise);
                    newToSend = this.messagesQueue.pollFirst();
                } else {
                    break;
                }
            }
            if (this.messagesQueue.isEmpty()) {
                releaseWriteSuspended(ctx);
            }
        }
        ctx.flush();
    }

    public long queueSize() {
        return this.queueSize;
    }
}
