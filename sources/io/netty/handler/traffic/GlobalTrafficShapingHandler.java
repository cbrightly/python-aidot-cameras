package io.netty.handler.traffic;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.PlatformDependent;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;

@ChannelHandler.Sharable
public class GlobalTrafficShapingHandler extends AbstractTrafficShapingHandler {
    private final ConcurrentMap<Integer, PerChannel> channelQueues = PlatformDependent.newConcurrentHashMap();
    long maxGlobalWriteSize = 419430400;
    private final AtomicLong queuesSize = new AtomicLong();

    public static final class PerChannel {
        long lastReadTimestamp;
        long lastWriteTimestamp;
        ArrayDeque<ToSend> messagesQueue;
        long queueSize;

        private PerChannel() {
        }
    }

    /* access modifiers changed from: package-private */
    public void createGlobalTrafficCounter(ScheduledExecutorService executor) {
        if (executor != null) {
            TrafficCounter tc = new TrafficCounter(this, executor, "GlobalTC", this.checkInterval);
            setTrafficCounter(tc);
            tc.start();
            return;
        }
        throw new NullPointerException("executor");
    }

    /* access modifiers changed from: protected */
    public int userDefinedWritabilityIndex() {
        return 2;
    }

    public GlobalTrafficShapingHandler(ScheduledExecutorService executor, long writeLimit, long readLimit, long checkInterval, long maxTime) {
        super(writeLimit, readLimit, checkInterval, maxTime);
        createGlobalTrafficCounter(executor);
    }

    public GlobalTrafficShapingHandler(ScheduledExecutorService executor, long writeLimit, long readLimit, long checkInterval) {
        super(writeLimit, readLimit, checkInterval);
        createGlobalTrafficCounter(executor);
    }

    public GlobalTrafficShapingHandler(ScheduledExecutorService executor, long writeLimit, long readLimit) {
        super(writeLimit, readLimit);
        createGlobalTrafficCounter(executor);
    }

    public GlobalTrafficShapingHandler(ScheduledExecutorService executor, long checkInterval) {
        super(checkInterval);
        createGlobalTrafficCounter(executor);
    }

    public GlobalTrafficShapingHandler(EventExecutor executor) {
        createGlobalTrafficCounter(executor);
    }

    public long getMaxGlobalWriteSize() {
        return this.maxGlobalWriteSize;
    }

    public void setMaxGlobalWriteSize(long maxGlobalWriteSize2) {
        this.maxGlobalWriteSize = maxGlobalWriteSize2;
    }

    public long queuesSize() {
        return this.queuesSize.get();
    }

    public final void release() {
        this.trafficCounter.stop();
    }

    private PerChannel getOrSetPerChannel(ChannelHandlerContext ctx) {
        Integer key = Integer.valueOf(ctx.channel().hashCode());
        PerChannel perChannel = (PerChannel) this.channelQueues.get(key);
        if (perChannel != null) {
            return perChannel;
        }
        PerChannel perChannel2 = new PerChannel();
        perChannel2.messagesQueue = new ArrayDeque<>();
        perChannel2.queueSize = 0;
        long milliSecondFromNano = TrafficCounter.milliSecondFromNano();
        perChannel2.lastReadTimestamp = milliSecondFromNano;
        perChannel2.lastWriteTimestamp = milliSecondFromNano;
        this.channelQueues.put(key, perChannel2);
        return perChannel2;
    }

    public void handlerAdded(ChannelHandlerContext ctx) {
        getOrSetPerChannel(ctx);
        super.handlerAdded(ctx);
    }

    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        PerChannel perChannel = (PerChannel) this.channelQueues.remove(Integer.valueOf(channel.hashCode()));
        if (perChannel != null) {
            synchronized (perChannel) {
                if (channel.isActive()) {
                    Iterator<ToSend> it = perChannel.messagesQueue.iterator();
                    while (it.hasNext()) {
                        ToSend toSend = it.next();
                        long size = calculateSize(toSend.toSend);
                        this.trafficCounter.bytesRealWriteFlowControl(size);
                        perChannel.queueSize -= size;
                        this.queuesSize.addAndGet(-size);
                        ctx.write(toSend.toSend, toSend.promise);
                    }
                } else {
                    this.queuesSize.addAndGet(-perChannel.queueSize);
                    Iterator<ToSend> it2 = perChannel.messagesQueue.iterator();
                    while (it2.hasNext()) {
                        Object obj = it2.next().toSend;
                        if (obj instanceof ByteBuf) {
                            ((ByteBuf) obj).release();
                        }
                    }
                }
                perChannel.messagesQueue.clear();
            }
        }
        releaseWriteSuspended(ctx);
        releaseReadSuspended(ctx);
        super.handlerRemoved(ctx);
    }

    /* access modifiers changed from: package-private */
    public long checkWaitReadTime(ChannelHandlerContext ctx, long wait, long now) {
        PerChannel perChannel = (PerChannel) this.channelQueues.get(Integer.valueOf(ctx.channel().hashCode()));
        if (perChannel == null || wait <= this.maxTime || (now + wait) - perChannel.lastReadTimestamp <= this.maxTime) {
            return wait;
        }
        return this.maxTime;
    }

    /* access modifiers changed from: package-private */
    public void informReadOperation(ChannelHandlerContext ctx, long now) {
        PerChannel perChannel = (PerChannel) this.channelQueues.get(Integer.valueOf(ctx.channel().hashCode()));
        if (perChannel != null) {
            perChannel.lastReadTimestamp = now;
        }
    }

    public static final class ToSend {
        final ChannelPromise promise;
        final long relativeTimeAction;
        final long size;
        final Object toSend;

        private ToSend(long delay, Object toSend2, long size2, ChannelPromise promise2) {
            this.relativeTimeAction = delay;
            this.toSend = toSend2;
            this.size = size2;
            this.promise = promise2;
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00b6 A[Catch:{ all -> 0x00d9 }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00bb  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void submitWrite(io.netty.channel.ChannelHandlerContext r24, java.lang.Object r25, long r26, long r28, long r30, io.netty.channel.ChannelPromise r32) {
        /*
            r23 = this;
            r7 = r23
            r8 = r24
            r5 = r26
            r3 = r30
            io.netty.channel.Channel r17 = r24.channel()
            int r0 = r17.hashCode()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r0)
            java.util.concurrent.ConcurrentMap<java.lang.Integer, io.netty.handler.traffic.GlobalTrafficShapingHandler$PerChannel> r0 = r7.channelQueues
            java.lang.Object r0 = r0.get(r2)
            io.netty.handler.traffic.GlobalTrafficShapingHandler$PerChannel r0 = (io.netty.handler.traffic.GlobalTrafficShapingHandler.PerChannel) r0
            if (r0 != 0) goto L_0x0024
            io.netty.handler.traffic.GlobalTrafficShapingHandler$PerChannel r0 = r23.getOrSetPerChannel(r24)
            r1 = r0
            goto L_0x0025
        L_0x0024:
            r1 = r0
        L_0x0025:
            r9 = r28
            r18 = 0
            monitor-enter(r1)
            r11 = 0
            int r0 = (r28 > r11 ? 1 : (r28 == r11 ? 0 : -1))
            if (r0 != 0) goto L_0x0053
            java.util.ArrayDeque<io.netty.handler.traffic.GlobalTrafficShapingHandler$ToSend> r0 = r1.messagesQueue     // Catch:{ all -> 0x004a }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x004a }
            if (r0 == 0) goto L_0x0053
            io.netty.handler.traffic.TrafficCounter r0 = r7.trafficCounter     // Catch:{ all -> 0x004a }
            r0.bytesRealWriteFlowControl(r5)     // Catch:{ all -> 0x004a }
            r15 = r25
            r13 = r32
            r8.write(r15, r13)     // Catch:{ all -> 0x0048 }
            r1.lastWriteTimestamp = r3     // Catch:{ all -> 0x0048 }
            monitor-exit(r1)     // Catch:{ all -> 0x0048 }
            return
        L_0x0048:
            r0 = move-exception
            goto L_0x004f
        L_0x004a:
            r0 = move-exception
            r15 = r25
            r13 = r32
        L_0x004f:
            r11 = r1
            r12 = r2
            goto L_0x00f0
        L_0x0053:
            r15 = r25
            r13 = r32
            long r11 = r7.maxTime     // Catch:{ all -> 0x00eb }
            int r0 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r0 <= 0) goto L_0x0075
            long r11 = r3 + r9
            r19 = r9
            long r9 = r1.lastWriteTimestamp     // Catch:{ all -> 0x006e }
            long r11 = r11 - r9
            long r9 = r7.maxTime     // Catch:{ all -> 0x006e }
            int r0 = (r11 > r9 ? 1 : (r11 == r9 ? 0 : -1))
            if (r0 <= 0) goto L_0x0077
            long r9 = r7.maxTime     // Catch:{ all -> 0x006e }
            r10 = r9
            goto L_0x0079
        L_0x006e:
            r0 = move-exception
            r11 = r1
            r12 = r2
            r9 = r19
            goto L_0x00f0
        L_0x0075:
            r19 = r9
        L_0x0077:
            r10 = r19
        L_0x0079:
            io.netty.handler.traffic.GlobalTrafficShapingHandler$ToSend r0 = new io.netty.handler.traffic.GlobalTrafficShapingHandler$ToSend     // Catch:{ all -> 0x00e5 }
            long r19 = r10 + r3
            r16 = 0
            r9 = r0
            r21 = r10
            r10 = r19
            r12 = r25
            r13 = r26
            r15 = r32
            r9.<init>(r10, r12, r13, r15)     // Catch:{ all -> 0x00de }
            java.util.ArrayDeque<io.netty.handler.traffic.GlobalTrafficShapingHandler$ToSend> r9 = r1.messagesQueue     // Catch:{ all -> 0x00de }
            r9.addLast(r0)     // Catch:{ all -> 0x00de }
            long r9 = r1.queueSize     // Catch:{ all -> 0x00de }
            long r9 = r9 + r5
            r1.queueSize = r9     // Catch:{ all -> 0x00de }
            java.util.concurrent.atomic.AtomicLong r9 = r7.queuesSize     // Catch:{ all -> 0x00de }
            r9.addAndGet(r5)     // Catch:{ all -> 0x00de }
            long r9 = r1.queueSize     // Catch:{ all -> 0x00de }
            r11 = r1
            r1 = r23
            r12 = r2
            r2 = r24
            r3 = r21
            r5 = r9
            r1.checkWriteSuspend(r2, r3, r5)     // Catch:{ all -> 0x00d9 }
            java.util.concurrent.atomic.AtomicLong r1 = r7.queuesSize     // Catch:{ all -> 0x00d9 }
            long r1 = r1.get()     // Catch:{ all -> 0x00d9 }
            long r3 = r7.maxGlobalWriteSize     // Catch:{ all -> 0x00d9 }
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 <= 0) goto L_0x00b8
            r18 = 1
        L_0x00b8:
            monitor-exit(r11)     // Catch:{ all -> 0x00d9 }
            if (r18 == 0) goto L_0x00bf
            r1 = 0
            r7.setUserDefinedWritability(r8, r1)
        L_0x00bf:
            long r9 = r0.relativeTimeAction
            r4 = r11
            io.netty.util.concurrent.EventExecutor r13 = r24.executor()
            io.netty.handler.traffic.GlobalTrafficShapingHandler$1 r14 = new io.netty.handler.traffic.GlobalTrafficShapingHandler$1
            r1 = r14
            r2 = r23
            r3 = r24
            r5 = r9
            r1.<init>(r3, r4, r5)
            java.util.concurrent.TimeUnit r1 = java.util.concurrent.TimeUnit.MILLISECONDS
            r2 = r21
            r13.schedule((java.lang.Runnable) r14, (long) r2, (java.util.concurrent.TimeUnit) r1)
            return
        L_0x00d9:
            r0 = move-exception
            r2 = r21
            r9 = r2
            goto L_0x00f0
        L_0x00de:
            r0 = move-exception
            r11 = r1
            r12 = r2
            r2 = r21
            r9 = r2
            goto L_0x00f0
        L_0x00e5:
            r0 = move-exception
            r12 = r2
            r2 = r10
            r11 = r1
            r9 = r2
            goto L_0x00f0
        L_0x00eb:
            r0 = move-exception
            r11 = r1
            r12 = r2
            r19 = r9
        L_0x00f0:
            monitor-exit(r11)     // Catch:{ all -> 0x00f2 }
            throw r0
        L_0x00f2:
            r0 = move-exception
            goto L_0x00f0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.traffic.GlobalTrafficShapingHandler.submitWrite(io.netty.channel.ChannelHandlerContext, java.lang.Object, long, long, long, io.netty.channel.ChannelPromise):void");
    }

    /* access modifiers changed from: private */
    public void sendAllValid(ChannelHandlerContext ctx, PerChannel perChannel, long now) {
        synchronized (perChannel) {
            ToSend newToSend = perChannel.messagesQueue.pollFirst();
            while (true) {
                if (newToSend != null) {
                    if (newToSend.relativeTimeAction > now) {
                        perChannel.messagesQueue.addFirst(newToSend);
                        break;
                    }
                    long size = newToSend.size;
                    this.trafficCounter.bytesRealWriteFlowControl(size);
                    perChannel.queueSize -= size;
                    this.queuesSize.addAndGet(-size);
                    ctx.write(newToSend.toSend, newToSend.promise);
                    perChannel.lastWriteTimestamp = now;
                    newToSend = perChannel.messagesQueue.pollFirst();
                } else {
                    break;
                }
            }
            if (perChannel.messagesQueue.isEmpty()) {
                releaseWriteSuspended(ctx);
            }
        }
        ctx.flush();
    }
}
