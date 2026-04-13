package io.netty.handler.traffic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.TimeUnit;

public abstract class AbstractTrafficShapingHandler extends ChannelDuplexHandler {
    static final int CHANNEL_DEFAULT_USER_DEFINED_WRITABILITY_INDEX = 1;
    public static final long DEFAULT_CHECK_INTERVAL = 1000;
    static final long DEFAULT_MAX_SIZE = 4194304;
    public static final long DEFAULT_MAX_TIME = 15000;
    static final int GLOBALCHANNEL_DEFAULT_USER_DEFINED_WRITABILITY_INDEX = 3;
    static final int GLOBAL_DEFAULT_USER_DEFINED_WRITABILITY_INDEX = 2;
    static final long MINIMAL_WAIT = 10;
    static final AttributeKey<Boolean> READ_SUSPENDED;
    static final AttributeKey<Runnable> REOPEN_TASK;
    /* access modifiers changed from: private */
    public static final InternalLogger logger;
    protected volatile long checkInterval;
    protected volatile long maxTime;
    volatile long maxWriteDelay;
    volatile long maxWriteSize;
    private volatile long readLimit;
    protected TrafficCounter trafficCounter;
    final int userDefinedWritabilityIndex;
    private volatile long writeLimit;

    /* access modifiers changed from: package-private */
    public abstract void submitWrite(ChannelHandlerContext channelHandlerContext, Object obj, long j, long j2, long j3, ChannelPromise channelPromise);

    static {
        Class<AbstractTrafficShapingHandler> cls = AbstractTrafficShapingHandler.class;
        logger = InternalLoggerFactory.getInstance((Class<?>) cls);
        READ_SUSPENDED = AttributeKey.valueOf(cls.getName() + ".READ_SUSPENDED");
        REOPEN_TASK = AttributeKey.valueOf(cls.getName() + ".REOPEN_TASK");
    }

    /* access modifiers changed from: package-private */
    public void setTrafficCounter(TrafficCounter newTrafficCounter) {
        this.trafficCounter = newTrafficCounter;
    }

    /* access modifiers changed from: protected */
    public int userDefinedWritabilityIndex() {
        return 1;
    }

    protected AbstractTrafficShapingHandler(long writeLimit2, long readLimit2, long checkInterval2, long maxTime2) {
        this.maxTime = 15000;
        this.checkInterval = 1000;
        this.maxWriteDelay = 4000;
        this.maxWriteSize = DEFAULT_MAX_SIZE;
        if (maxTime2 > 0) {
            this.userDefinedWritabilityIndex = userDefinedWritabilityIndex();
            this.writeLimit = writeLimit2;
            this.readLimit = readLimit2;
            this.checkInterval = checkInterval2;
            this.maxTime = maxTime2;
            return;
        }
        throw new IllegalArgumentException("maxTime must be positive");
    }

    protected AbstractTrafficShapingHandler(long writeLimit2, long readLimit2, long checkInterval2) {
        this(writeLimit2, readLimit2, checkInterval2, 15000);
    }

    protected AbstractTrafficShapingHandler(long writeLimit2, long readLimit2) {
        this(writeLimit2, readLimit2, 1000, 15000);
    }

    protected AbstractTrafficShapingHandler() {
        this(0, 0, 1000, 15000);
    }

    protected AbstractTrafficShapingHandler(long checkInterval2) {
        this(0, 0, checkInterval2, 15000);
    }

    public void configure(long newWriteLimit, long newReadLimit, long newCheckInterval) {
        configure(newWriteLimit, newReadLimit);
        configure(newCheckInterval);
    }

    public void configure(long newWriteLimit, long newReadLimit) {
        this.writeLimit = newWriteLimit;
        this.readLimit = newReadLimit;
        TrafficCounter trafficCounter2 = this.trafficCounter;
        if (trafficCounter2 != null) {
            trafficCounter2.resetAccounting(TrafficCounter.milliSecondFromNano());
        }
    }

    public void configure(long newCheckInterval) {
        this.checkInterval = newCheckInterval;
        TrafficCounter trafficCounter2 = this.trafficCounter;
        if (trafficCounter2 != null) {
            trafficCounter2.configure(this.checkInterval);
        }
    }

    public long getWriteLimit() {
        return this.writeLimit;
    }

    public void setWriteLimit(long writeLimit2) {
        this.writeLimit = writeLimit2;
        TrafficCounter trafficCounter2 = this.trafficCounter;
        if (trafficCounter2 != null) {
            trafficCounter2.resetAccounting(TrafficCounter.milliSecondFromNano());
        }
    }

    public long getReadLimit() {
        return this.readLimit;
    }

    public void setReadLimit(long readLimit2) {
        this.readLimit = readLimit2;
        TrafficCounter trafficCounter2 = this.trafficCounter;
        if (trafficCounter2 != null) {
            trafficCounter2.resetAccounting(TrafficCounter.milliSecondFromNano());
        }
    }

    public long getCheckInterval() {
        return this.checkInterval;
    }

    public void setCheckInterval(long checkInterval2) {
        this.checkInterval = checkInterval2;
        TrafficCounter trafficCounter2 = this.trafficCounter;
        if (trafficCounter2 != null) {
            trafficCounter2.configure(checkInterval2);
        }
    }

    public void setMaxTimeWait(long maxTime2) {
        if (maxTime2 > 0) {
            this.maxTime = maxTime2;
            return;
        }
        throw new IllegalArgumentException("maxTime must be positive");
    }

    public long getMaxTimeWait() {
        return this.maxTime;
    }

    public long getMaxWriteDelay() {
        return this.maxWriteDelay;
    }

    public void setMaxWriteDelay(long maxWriteDelay2) {
        if (maxWriteDelay2 > 0) {
            this.maxWriteDelay = maxWriteDelay2;
            return;
        }
        throw new IllegalArgumentException("maxWriteDelay must be positive");
    }

    public long getMaxWriteSize() {
        return this.maxWriteSize;
    }

    public void setMaxWriteSize(long maxWriteSize2) {
        this.maxWriteSize = maxWriteSize2;
    }

    /* access modifiers changed from: protected */
    public void doAccounting(TrafficCounter counter) {
    }

    public static final class ReopenReadTimerTask implements Runnable {
        final ChannelHandlerContext ctx;

        ReopenReadTimerTask(ChannelHandlerContext ctx2) {
            this.ctx = ctx2;
        }

        public void run() {
            ChannelConfig config = this.ctx.channel().config();
            if (config.isAutoRead() || !AbstractTrafficShapingHandler.isHandlerActive(this.ctx)) {
                if (AbstractTrafficShapingHandler.logger.isDebugEnabled()) {
                    if (!config.isAutoRead() || AbstractTrafficShapingHandler.isHandlerActive(this.ctx)) {
                        InternalLogger access$000 = AbstractTrafficShapingHandler.logger;
                        access$000.debug("Normal unsuspend: " + config.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
                    } else {
                        InternalLogger access$0002 = AbstractTrafficShapingHandler.logger;
                        access$0002.debug("Unsuspend: " + config.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
                    }
                }
                this.ctx.attr(AbstractTrafficShapingHandler.READ_SUSPENDED).set(false);
                config.setAutoRead(true);
                this.ctx.channel().read();
            } else {
                if (AbstractTrafficShapingHandler.logger.isDebugEnabled()) {
                    InternalLogger access$0003 = AbstractTrafficShapingHandler.logger;
                    access$0003.debug("Not unsuspend: " + config.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
                }
                this.ctx.attr(AbstractTrafficShapingHandler.READ_SUSPENDED).set(false);
            }
            if (AbstractTrafficShapingHandler.logger.isDebugEnabled()) {
                InternalLogger access$0004 = AbstractTrafficShapingHandler.logger;
                access$0004.debug("Unsuspend final status => " + config.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void releaseReadSuspended(ChannelHandlerContext ctx) {
        ctx.attr(READ_SUSPENDED).set(false);
        ctx.channel().config().setAutoRead(true);
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        long size = calculateSize(msg);
        long now = TrafficCounter.milliSecondFromNano();
        if (size > 0) {
            long wait = checkWaitReadTime(ctx, this.trafficCounter.readTimeToWait(size, this.readLimit, this.maxTime, now), now);
            if (wait >= 10) {
                ChannelConfig config = ctx.channel().config();
                InternalLogger internalLogger = logger;
                if (internalLogger.isDebugEnabled()) {
                    internalLogger.debug("Read suspend: " + wait + ':' + config.isAutoRead() + ':' + isHandlerActive(ctx));
                }
                if (config.isAutoRead() && isHandlerActive(ctx)) {
                    config.setAutoRead(false);
                    ctx.attr(READ_SUSPENDED).set(true);
                    Attribute<Runnable> attr = ctx.attr(REOPEN_TASK);
                    Runnable reopenTask = attr.get();
                    if (reopenTask == null) {
                        reopenTask = new ReopenReadTimerTask(ctx);
                        attr.set(reopenTask);
                    }
                    ctx.executor().schedule(reopenTask, wait, TimeUnit.MILLISECONDS);
                    if (internalLogger.isDebugEnabled()) {
                        internalLogger.debug("Suspend final status => " + config.isAutoRead() + ':' + isHandlerActive(ctx) + " will reopened at: " + wait);
                    }
                }
            }
        }
        informReadOperation(ctx, now);
        ctx.fireChannelRead(msg);
    }

    /* access modifiers changed from: package-private */
    public long checkWaitReadTime(ChannelHandlerContext ctx, long wait, long now) {
        return wait;
    }

    /* access modifiers changed from: package-private */
    public void informReadOperation(ChannelHandlerContext ctx, long now) {
    }

    protected static boolean isHandlerActive(ChannelHandlerContext ctx) {
        Boolean suspended = ctx.attr(READ_SUSPENDED).get();
        return suspended == null || Boolean.FALSE.equals(suspended);
    }

    public void read(ChannelHandlerContext ctx) {
        if (isHandlerActive(ctx)) {
            ctx.read();
        }
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        long size = calculateSize(msg);
        long now = TrafficCounter.milliSecondFromNano();
        if (size > 0) {
            long wait = this.trafficCounter.writeTimeToWait(size, this.writeLimit, this.maxTime, now);
            if (wait >= 10) {
                InternalLogger internalLogger = logger;
                if (internalLogger.isDebugEnabled()) {
                    internalLogger.debug("Write suspend: " + wait + ':' + ctx.channel().config().isAutoRead() + ':' + isHandlerActive(ctx));
                }
                long j = wait;
                submitWrite(ctx, msg, size, wait, now, promise);
                return;
            }
        }
        submitWrite(ctx, msg, size, 0, now, promise);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void submitWrite(ChannelHandlerContext ctx, Object msg, long delay, ChannelPromise promise) {
        submitWrite(ctx, msg, calculateSize(msg), delay, TrafficCounter.milliSecondFromNano(), promise);
    }

    public void channelRegistered(ChannelHandlerContext ctx) {
        setUserDefinedWritability(ctx, true);
        super.channelRegistered(ctx);
    }

    /* access modifiers changed from: package-private */
    public void setUserDefinedWritability(ChannelHandlerContext ctx, boolean writable) {
        ChannelOutboundBuffer cob = ctx.channel().unsafe().outboundBuffer();
        if (cob != null) {
            cob.setUserDefinedWritability(this.userDefinedWritabilityIndex, writable);
        }
    }

    /* access modifiers changed from: package-private */
    public void checkWriteSuspend(ChannelHandlerContext ctx, long delay, long queueSize) {
        if (queueSize > this.maxWriteSize || delay > this.maxWriteDelay) {
            setUserDefinedWritability(ctx, false);
        }
    }

    /* access modifiers changed from: package-private */
    public void releaseWriteSuspended(ChannelHandlerContext ctx) {
        setUserDefinedWritability(ctx, true);
    }

    public TrafficCounter trafficCounter() {
        return this.trafficCounter;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(290);
        sb.append("TrafficShaping with Write Limit: ");
        sb.append(this.writeLimit);
        sb.append(" Read Limit: ");
        sb.append(this.readLimit);
        sb.append(" CheckInterval: ");
        sb.append(this.checkInterval);
        sb.append(" maxDelay: ");
        sb.append(this.maxWriteDelay);
        sb.append(" maxSize: ");
        sb.append(this.maxWriteSize);
        StringBuilder builder = sb.append(" and Counter: ");
        TrafficCounter trafficCounter2 = this.trafficCounter;
        if (trafficCounter2 != null) {
            builder.append(trafficCounter2);
        } else {
            builder.append("none");
        }
        return builder.toString();
    }

    /* access modifiers changed from: protected */
    public long calculateSize(Object msg) {
        if (msg instanceof ByteBuf) {
            return (long) ((ByteBuf) msg).readableBytes();
        }
        if (msg instanceof ByteBufHolder) {
            return (long) ((ByteBufHolder) msg).content().readableBytes();
        }
        return -1;
    }
}
