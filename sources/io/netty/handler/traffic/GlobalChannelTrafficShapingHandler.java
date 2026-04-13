package io.netty.handler.traffic;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.traffic.AbstractTrafficShapingHandler;
import io.netty.util.Attribute;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.AbstractCollection;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@ChannelHandler.Sharable
public class GlobalChannelTrafficShapingHandler extends AbstractTrafficShapingHandler {
    private static final float DEFAULT_ACCELERATION = -0.1f;
    private static final float DEFAULT_DEVIATION = 0.1f;
    private static final float DEFAULT_SLOWDOWN = 0.4f;
    private static final float MAX_DEVIATION = 0.4f;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) GlobalChannelTrafficShapingHandler.class);
    private volatile float accelerationFactor;
    final ConcurrentMap<Integer, PerChannel> channelQueues = PlatformDependent.newConcurrentHashMap();
    private final AtomicLong cumulativeReadBytes = new AtomicLong();
    private final AtomicLong cumulativeWrittenBytes = new AtomicLong();
    private volatile float maxDeviation;
    volatile long maxGlobalWriteSize = 419430400;
    private final AtomicLong queuesSize = new AtomicLong();
    private volatile long readChannelLimit;
    private volatile boolean readDeviationActive;
    private volatile float slowDownFactor;
    private volatile long writeChannelLimit;
    private volatile boolean writeDeviationActive;

    public static final class PerChannel {
        TrafficCounter channelTrafficCounter;
        long lastReadTimestamp;
        long lastWriteTimestamp;
        ArrayDeque<ToSend> messagesQueue;
        long queueSize;

        PerChannel() {
        }
    }

    /* access modifiers changed from: package-private */
    public void createGlobalTrafficCounter(ScheduledExecutorService executor) {
        setMaxDeviation(0.1f, 0.4f, DEFAULT_ACCELERATION);
        if (executor != null) {
            GlobalChannelTrafficCounter globalChannelTrafficCounter = new GlobalChannelTrafficCounter(this, executor, "GlobalChannelTC", this.checkInterval);
            setTrafficCounter(globalChannelTrafficCounter);
            globalChannelTrafficCounter.start();
            return;
        }
        throw new IllegalArgumentException("Executor must not be null");
    }

    /* access modifiers changed from: protected */
    public int userDefinedWritabilityIndex() {
        return 3;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor, long writeGlobalLimit, long readGlobalLimit, long writeChannelLimit2, long readChannelLimit2, long checkInterval, long maxTime) {
        super(writeGlobalLimit, readGlobalLimit, checkInterval, maxTime);
        createGlobalTrafficCounter(executor);
        this.writeChannelLimit = writeChannelLimit2;
        this.readChannelLimit = readChannelLimit2;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor, long writeGlobalLimit, long readGlobalLimit, long writeChannelLimit2, long readChannelLimit2, long checkInterval) {
        super(writeGlobalLimit, readGlobalLimit, checkInterval);
        this.writeChannelLimit = writeChannelLimit2;
        this.readChannelLimit = readChannelLimit2;
        createGlobalTrafficCounter(executor);
    }

    public GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor, long writeGlobalLimit, long readGlobalLimit, long writeChannelLimit2, long readChannelLimit2) {
        super(writeGlobalLimit, readGlobalLimit);
        this.writeChannelLimit = writeChannelLimit2;
        this.readChannelLimit = readChannelLimit2;
        createGlobalTrafficCounter(executor);
    }

    public GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor, long checkInterval) {
        super(checkInterval);
        createGlobalTrafficCounter(executor);
    }

    public GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor) {
        createGlobalTrafficCounter(executor);
    }

    public float maxDeviation() {
        return this.maxDeviation;
    }

    public float accelerationFactor() {
        return this.accelerationFactor;
    }

    public float slowDownFactor() {
        return this.slowDownFactor;
    }

    public void setMaxDeviation(float maxDeviation2, float slowDownFactor2, float accelerationFactor2) {
        if (maxDeviation2 > 0.4f) {
            throw new IllegalArgumentException("maxDeviation must be <= 0.4");
        } else if (slowDownFactor2 < 0.0f) {
            throw new IllegalArgumentException("slowDownFactor must be >= 0");
        } else if (accelerationFactor2 <= 0.0f) {
            this.maxDeviation = maxDeviation2;
            this.accelerationFactor = accelerationFactor2 + 1.0f;
            this.slowDownFactor = 1.0f + slowDownFactor2;
        } else {
            throw new IllegalArgumentException("accelerationFactor must be <= 0");
        }
    }

    private void computeDeviationCumulativeBytes() {
        long maxWrittenBytes = 0;
        long maxReadBytes = 0;
        long minWrittenBytes = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
        long minReadBytes = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
        for (PerChannel perChannel : this.channelQueues.values()) {
            long value = perChannel.channelTrafficCounter.cumulativeWrittenBytes();
            if (maxWrittenBytes < value) {
                maxWrittenBytes = value;
            }
            if (minWrittenBytes > value) {
                minWrittenBytes = value;
            }
            long value2 = perChannel.channelTrafficCounter.cumulativeReadBytes();
            if (maxReadBytes < value2) {
                maxReadBytes = value2;
            }
            if (minReadBytes > value2) {
                minReadBytes = value2;
            }
        }
        boolean z = false;
        boolean multiple = this.channelQueues.size() > 1;
        this.readDeviationActive = multiple && minReadBytes < maxReadBytes / 2;
        if (multiple && minWrittenBytes < maxWrittenBytes / 2) {
            z = true;
        }
        this.writeDeviationActive = z;
        this.cumulativeWrittenBytes.set(maxWrittenBytes);
        this.cumulativeReadBytes.set(maxReadBytes);
    }

    /* access modifiers changed from: protected */
    public void doAccounting(TrafficCounter counter) {
        computeDeviationCumulativeBytes();
        super.doAccounting(counter);
    }

    private long computeBalancedWait(float maxLocal, float maxGlobal, long wait) {
        float ratio;
        if (maxGlobal == 0.0f) {
            return wait;
        }
        float ratio2 = maxLocal / maxGlobal;
        if (ratio2 <= this.maxDeviation) {
            ratio = this.accelerationFactor;
        } else if (ratio2 < 1.0f - this.maxDeviation) {
            return wait;
        } else {
            ratio = this.slowDownFactor;
            if (wait < 10) {
                wait = 10;
            }
        }
        return (long) (((float) wait) * ratio);
    }

    public long getMaxGlobalWriteSize() {
        return this.maxGlobalWriteSize;
    }

    public void setMaxGlobalWriteSize(long maxGlobalWriteSize2) {
        if (maxGlobalWriteSize2 > 0) {
            this.maxGlobalWriteSize = maxGlobalWriteSize2;
            return;
        }
        throw new IllegalArgumentException("maxGlobalWriteSize must be positive");
    }

    public long queuesSize() {
        return this.queuesSize.get();
    }

    public void configureChannel(long newWriteLimit, long newReadLimit) {
        this.writeChannelLimit = newWriteLimit;
        this.readChannelLimit = newReadLimit;
        long now = TrafficCounter.milliSecondFromNano();
        for (PerChannel perChannel : this.channelQueues.values()) {
            perChannel.channelTrafficCounter.resetAccounting(now);
        }
    }

    public long getWriteChannelLimit() {
        return this.writeChannelLimit;
    }

    public void setWriteChannelLimit(long writeLimit) {
        this.writeChannelLimit = writeLimit;
        long now = TrafficCounter.milliSecondFromNano();
        for (PerChannel perChannel : this.channelQueues.values()) {
            perChannel.channelTrafficCounter.resetAccounting(now);
        }
    }

    public long getReadChannelLimit() {
        return this.readChannelLimit;
    }

    public void setReadChannelLimit(long readLimit) {
        this.readChannelLimit = readLimit;
        long now = TrafficCounter.milliSecondFromNano();
        for (PerChannel perChannel : this.channelQueues.values()) {
            perChannel.channelTrafficCounter.resetAccounting(now);
        }
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
        perChannel2.channelTrafficCounter = new TrafficCounter(this, (ScheduledExecutorService) null, "ChannelTC" + ctx.channel().hashCode(), this.checkInterval);
        perChannel2.queueSize = 0;
        long milliSecondFromNano = TrafficCounter.milliSecondFromNano();
        perChannel2.lastReadTimestamp = milliSecondFromNano;
        perChannel2.lastWriteTimestamp = milliSecondFromNano;
        this.channelQueues.put(key, perChannel2);
        return perChannel2;
    }

    public void handlerAdded(ChannelHandlerContext ctx) {
        getOrSetPerChannel(ctx);
        this.trafficCounter.resetCumulativeTime();
        super.handlerAdded(ctx);
    }

    public void handlerRemoved(ChannelHandlerContext ctx) {
        this.trafficCounter.resetCumulativeTime();
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
                        perChannel.channelTrafficCounter.bytesRealWriteFlowControl(size);
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

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        long now;
        ChannelHandlerContext channelHandlerContext = ctx;
        long size = calculateSize(msg);
        long now2 = TrafficCounter.milliSecondFromNano();
        if (size > 0) {
            long waitGlobal = this.trafficCounter.readTimeToWait(size, getReadLimit(), this.maxTime, now2);
            Integer key = Integer.valueOf(ctx.channel().hashCode());
            PerChannel perChannel = (PerChannel) this.channelQueues.get(key);
            long wait = 0;
            if (perChannel != null) {
                long wait2 = perChannel.channelTrafficCounter.readTimeToWait(size, this.readChannelLimit, this.maxTime, now2);
                if (this.readDeviationActive) {
                    long maxLocalRead = perChannel.channelTrafficCounter.cumulativeReadBytes();
                    long maxGlobalRead = this.cumulativeReadBytes.get();
                    if (maxLocalRead <= 0) {
                        maxLocalRead = 0;
                    }
                    if (maxGlobalRead < maxLocalRead) {
                        maxGlobalRead = maxLocalRead;
                    }
                    wait = computeBalancedWait((float) maxLocalRead, (float) maxGlobalRead, wait2);
                } else {
                    wait = wait2;
                }
            }
            if (wait < waitGlobal) {
                wait = waitGlobal;
            }
            Integer num = key;
            PerChannel perChannel2 = perChannel;
            now = now2;
            long wait3 = checkWaitReadTime(ctx, wait, now2);
            if (wait3 >= 10) {
                ChannelConfig config = ctx.channel().config();
                InternalLogger internalLogger = logger;
                if (internalLogger.isDebugEnabled()) {
                    internalLogger.debug("Read Suspend: " + wait3 + ':' + config.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(ctx));
                }
                if (config.isAutoRead() && AbstractTrafficShapingHandler.isHandlerActive(ctx)) {
                    config.setAutoRead(false);
                    channelHandlerContext.attr(AbstractTrafficShapingHandler.READ_SUSPENDED).set(true);
                    Attribute<Runnable> attr = channelHandlerContext.attr(AbstractTrafficShapingHandler.REOPEN_TASK);
                    Runnable reopenTask = attr.get();
                    if (reopenTask == null) {
                        reopenTask = new AbstractTrafficShapingHandler.ReopenReadTimerTask(channelHandlerContext);
                        attr.set(reopenTask);
                    }
                    ctx.executor().schedule(reopenTask, wait3, TimeUnit.MILLISECONDS);
                    if (internalLogger.isDebugEnabled()) {
                        internalLogger.debug("Suspend final status => " + config.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(ctx) + " will reopened at: " + wait3);
                    }
                }
            }
        } else {
            now = now2;
        }
        informReadOperation(channelHandlerContext, now);
        ctx.fireChannelRead(msg);
    }

    /* access modifiers changed from: protected */
    public long checkWaitReadTime(ChannelHandlerContext ctx, long wait, long now) {
        PerChannel perChannel = (PerChannel) this.channelQueues.get(Integer.valueOf(ctx.channel().hashCode()));
        if (perChannel == null || wait <= this.maxTime || (now + wait) - perChannel.lastReadTimestamp <= this.maxTime) {
            return wait;
        }
        return this.maxTime;
    }

    /* access modifiers changed from: protected */
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

    /* access modifiers changed from: protected */
    public long maximumCumulativeWrittenBytes() {
        return this.cumulativeWrittenBytes.get();
    }

    /* access modifiers changed from: protected */
    public long maximumCumulativeReadBytes() {
        return this.cumulativeReadBytes.get();
    }

    public Collection<TrafficCounter> channelTrafficCounters() {
        return new AbstractCollection<TrafficCounter>() {
            public Iterator<TrafficCounter> iterator() {
                return new Iterator<TrafficCounter>() {
                    final Iterator<PerChannel> iter;

                    {
                        this.iter = GlobalChannelTrafficShapingHandler.this.channelQueues.values().iterator();
                    }

                    public boolean hasNext() {
                        return this.iter.hasNext();
                    }

                    public TrafficCounter next() {
                        return this.iter.next().channelTrafficCounter;
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }

            public int size() {
                return GlobalChannelTrafficShapingHandler.this.channelQueues.size();
            }
        };
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        long wait;
        long size = calculateSize(msg);
        long now = TrafficCounter.milliSecondFromNano();
        if (size > 0) {
            long waitGlobal = this.trafficCounter.writeTimeToWait(size, getWriteLimit(), this.maxTime, now);
            Integer key = Integer.valueOf(ctx.channel().hashCode());
            PerChannel perChannel = (PerChannel) this.channelQueues.get(key);
            long wait2 = 0;
            if (perChannel != null) {
                PerChannel perChannel2 = perChannel;
                long wait3 = perChannel.channelTrafficCounter.writeTimeToWait(size, this.writeChannelLimit, this.maxTime, now);
                if (this.writeDeviationActive) {
                    long maxLocalWrite = perChannel2.channelTrafficCounter.cumulativeWrittenBytes();
                    long maxGlobalWrite = this.cumulativeWrittenBytes.get();
                    if (maxLocalWrite <= 0) {
                        maxLocalWrite = 0;
                    }
                    if (maxGlobalWrite < maxLocalWrite) {
                        maxGlobalWrite = maxLocalWrite;
                    }
                    wait2 = computeBalancedWait((float) maxLocalWrite, (float) maxGlobalWrite, wait3);
                } else {
                    wait2 = wait3;
                }
            }
            if (wait2 < waitGlobal) {
                wait = waitGlobal;
            } else {
                wait = wait2;
            }
            if (wait >= 10) {
                InternalLogger internalLogger = logger;
                if (internalLogger.isDebugEnabled()) {
                    internalLogger.debug("Write suspend: " + wait + ':' + ctx.channel().config().isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(ctx));
                }
                long j = wait;
                Integer num = key;
                submitWrite(ctx, msg, size, wait, now, promise);
                return;
            }
            Integer num2 = key;
        }
        submitWrite(ctx, msg, size, 0, now, promise);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00bb A[Catch:{ all -> 0x00de }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00c0  */
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
            java.util.concurrent.ConcurrentMap<java.lang.Integer, io.netty.handler.traffic.GlobalChannelTrafficShapingHandler$PerChannel> r0 = r7.channelQueues
            java.lang.Object r0 = r0.get(r2)
            io.netty.handler.traffic.GlobalChannelTrafficShapingHandler$PerChannel r0 = (io.netty.handler.traffic.GlobalChannelTrafficShapingHandler.PerChannel) r0
            if (r0 != 0) goto L_0x0024
            io.netty.handler.traffic.GlobalChannelTrafficShapingHandler$PerChannel r0 = r23.getOrSetPerChannel(r24)
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
            if (r0 != 0) goto L_0x0058
            java.util.ArrayDeque<io.netty.handler.traffic.GlobalChannelTrafficShapingHandler$ToSend> r0 = r1.messagesQueue     // Catch:{ all -> 0x004f }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x004f }
            if (r0 == 0) goto L_0x0058
            io.netty.handler.traffic.TrafficCounter r0 = r7.trafficCounter     // Catch:{ all -> 0x004f }
            r0.bytesRealWriteFlowControl(r5)     // Catch:{ all -> 0x004f }
            io.netty.handler.traffic.TrafficCounter r0 = r1.channelTrafficCounter     // Catch:{ all -> 0x004f }
            r0.bytesRealWriteFlowControl(r5)     // Catch:{ all -> 0x004f }
            r15 = r25
            r13 = r32
            r8.write(r15, r13)     // Catch:{ all -> 0x004d }
            r1.lastWriteTimestamp = r3     // Catch:{ all -> 0x004d }
            monitor-exit(r1)     // Catch:{ all -> 0x004d }
            return
        L_0x004d:
            r0 = move-exception
            goto L_0x0054
        L_0x004f:
            r0 = move-exception
            r15 = r25
            r13 = r32
        L_0x0054:
            r11 = r1
            r12 = r2
            goto L_0x00f5
        L_0x0058:
            r15 = r25
            r13 = r32
            long r11 = r7.maxTime     // Catch:{ all -> 0x00f0 }
            int r0 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r0 <= 0) goto L_0x007a
            long r11 = r3 + r9
            r19 = r9
            long r9 = r1.lastWriteTimestamp     // Catch:{ all -> 0x0073 }
            long r11 = r11 - r9
            long r9 = r7.maxTime     // Catch:{ all -> 0x0073 }
            int r0 = (r11 > r9 ? 1 : (r11 == r9 ? 0 : -1))
            if (r0 <= 0) goto L_0x007c
            long r9 = r7.maxTime     // Catch:{ all -> 0x0073 }
            r10 = r9
            goto L_0x007e
        L_0x0073:
            r0 = move-exception
            r11 = r1
            r12 = r2
            r9 = r19
            goto L_0x00f5
        L_0x007a:
            r19 = r9
        L_0x007c:
            r10 = r19
        L_0x007e:
            io.netty.handler.traffic.GlobalChannelTrafficShapingHandler$ToSend r0 = new io.netty.handler.traffic.GlobalChannelTrafficShapingHandler$ToSend     // Catch:{ all -> 0x00ea }
            long r19 = r10 + r3
            r16 = 0
            r9 = r0
            r21 = r10
            r10 = r19
            r12 = r25
            r13 = r26
            r15 = r32
            r9.<init>(r10, r12, r13, r15)     // Catch:{ all -> 0x00e3 }
            java.util.ArrayDeque<io.netty.handler.traffic.GlobalChannelTrafficShapingHandler$ToSend> r9 = r1.messagesQueue     // Catch:{ all -> 0x00e3 }
            r9.addLast(r0)     // Catch:{ all -> 0x00e3 }
            long r9 = r1.queueSize     // Catch:{ all -> 0x00e3 }
            long r9 = r9 + r5
            r1.queueSize = r9     // Catch:{ all -> 0x00e3 }
            java.util.concurrent.atomic.AtomicLong r9 = r7.queuesSize     // Catch:{ all -> 0x00e3 }
            r9.addAndGet(r5)     // Catch:{ all -> 0x00e3 }
            long r9 = r1.queueSize     // Catch:{ all -> 0x00e3 }
            r11 = r1
            r1 = r23
            r12 = r2
            r2 = r24
            r3 = r21
            r5 = r9
            r1.checkWriteSuspend(r2, r3, r5)     // Catch:{ all -> 0x00de }
            java.util.concurrent.atomic.AtomicLong r1 = r7.queuesSize     // Catch:{ all -> 0x00de }
            long r1 = r1.get()     // Catch:{ all -> 0x00de }
            long r3 = r7.maxGlobalWriteSize     // Catch:{ all -> 0x00de }
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 <= 0) goto L_0x00bd
            r18 = 1
        L_0x00bd:
            monitor-exit(r11)     // Catch:{ all -> 0x00de }
            if (r18 == 0) goto L_0x00c4
            r1 = 0
            r7.setUserDefinedWritability(r8, r1)
        L_0x00c4:
            long r9 = r0.relativeTimeAction
            r4 = r11
            io.netty.util.concurrent.EventExecutor r13 = r24.executor()
            io.netty.handler.traffic.GlobalChannelTrafficShapingHandler$2 r14 = new io.netty.handler.traffic.GlobalChannelTrafficShapingHandler$2
            r1 = r14
            r2 = r23
            r3 = r24
            r5 = r9
            r1.<init>(r3, r4, r5)
            java.util.concurrent.TimeUnit r1 = java.util.concurrent.TimeUnit.MILLISECONDS
            r2 = r21
            r13.schedule((java.lang.Runnable) r14, (long) r2, (java.util.concurrent.TimeUnit) r1)
            return
        L_0x00de:
            r0 = move-exception
            r2 = r21
            r9 = r2
            goto L_0x00f5
        L_0x00e3:
            r0 = move-exception
            r11 = r1
            r12 = r2
            r2 = r21
            r9 = r2
            goto L_0x00f5
        L_0x00ea:
            r0 = move-exception
            r12 = r2
            r2 = r10
            r11 = r1
            r9 = r2
            goto L_0x00f5
        L_0x00f0:
            r0 = move-exception
            r11 = r1
            r12 = r2
            r19 = r9
        L_0x00f5:
            monitor-exit(r11)     // Catch:{ all -> 0x00f7 }
            throw r0
        L_0x00f7:
            r0 = move-exception
            goto L_0x00f5
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.traffic.GlobalChannelTrafficShapingHandler.submitWrite(io.netty.channel.ChannelHandlerContext, java.lang.Object, long, long, long, io.netty.channel.ChannelPromise):void");
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
                    perChannel.channelTrafficCounter.bytesRealWriteFlowControl(size);
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

    public String toString() {
        StringBuilder sb = new StringBuilder(340);
        sb.append(super.toString());
        sb.append(" Write Channel Limit: ");
        sb.append(this.writeChannelLimit);
        sb.append(" Read Channel Limit: ");
        sb.append(this.readChannelLimit);
        return sb.toString();
    }
}
