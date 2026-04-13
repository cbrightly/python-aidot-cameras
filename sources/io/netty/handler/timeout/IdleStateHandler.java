package io.netty.handler.timeout;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class IdleStateHandler extends ChannelDuplexHandler {
    private static final long MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1);
    /* access modifiers changed from: private */
    public final long allIdleTimeNanos;
    /* access modifiers changed from: private */
    public ScheduledFuture<?> allIdleTimeout;
    /* access modifiers changed from: private */
    public boolean firstAllIdleEvent;
    /* access modifiers changed from: private */
    public boolean firstReaderIdleEvent;
    /* access modifiers changed from: private */
    public boolean firstWriterIdleEvent;
    private long lastChangeCheckTimeStamp;
    private int lastMessageHashCode;
    private long lastPendingWriteBytes;
    /* access modifiers changed from: private */
    public long lastReadTime;
    /* access modifiers changed from: private */
    public long lastWriteTime;
    private final boolean observeOutput;
    /* access modifiers changed from: private */
    public final long readerIdleTimeNanos;
    /* access modifiers changed from: private */
    public ScheduledFuture<?> readerIdleTimeout;
    /* access modifiers changed from: private */
    public boolean reading;
    private byte state;
    private final ChannelFutureListener writeListener;
    /* access modifiers changed from: private */
    public final long writerIdleTimeNanos;
    /* access modifiers changed from: private */
    public ScheduledFuture<?> writerIdleTimeout;

    public IdleStateHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
        this((long) readerIdleTimeSeconds, (long) writerIdleTimeSeconds, (long) allIdleTimeSeconds, TimeUnit.SECONDS);
    }

    public IdleStateHandler(long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit) {
        this(false, readerIdleTime, writerIdleTime, allIdleTime, unit);
    }

    public IdleStateHandler(boolean observeOutput2, long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit) {
        this.writeListener = new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) {
                IdleStateHandler idleStateHandler = IdleStateHandler.this;
                long unused = idleStateHandler.lastWriteTime = idleStateHandler.ticksInNanos();
                IdleStateHandler idleStateHandler2 = IdleStateHandler.this;
                boolean unused2 = idleStateHandler2.firstWriterIdleEvent = idleStateHandler2.firstAllIdleEvent = true;
            }
        };
        this.firstReaderIdleEvent = true;
        this.firstWriterIdleEvent = true;
        this.firstAllIdleEvent = true;
        if (unit != null) {
            this.observeOutput = observeOutput2;
            if (readerIdleTime <= 0) {
                this.readerIdleTimeNanos = 0;
            } else {
                this.readerIdleTimeNanos = Math.max(unit.toNanos(readerIdleTime), MIN_TIMEOUT_NANOS);
            }
            if (writerIdleTime <= 0) {
                this.writerIdleTimeNanos = 0;
            } else {
                this.writerIdleTimeNanos = Math.max(unit.toNanos(writerIdleTime), MIN_TIMEOUT_NANOS);
            }
            if (allIdleTime <= 0) {
                this.allIdleTimeNanos = 0;
            } else {
                this.allIdleTimeNanos = Math.max(unit.toNanos(allIdleTime), MIN_TIMEOUT_NANOS);
            }
        } else {
            throw new NullPointerException("unit");
        }
    }

    public long getReaderIdleTimeInMillis() {
        return TimeUnit.NANOSECONDS.toMillis(this.readerIdleTimeNanos);
    }

    public long getWriterIdleTimeInMillis() {
        return TimeUnit.NANOSECONDS.toMillis(this.writerIdleTimeNanos);
    }

    public long getAllIdleTimeInMillis() {
        return TimeUnit.NANOSECONDS.toMillis(this.allIdleTimeNanos);
    }

    public void handlerAdded(ChannelHandlerContext ctx) {
        if (ctx.channel().isActive() && ctx.channel().isRegistered()) {
            initialize(ctx);
        }
    }

    public void handlerRemoved(ChannelHandlerContext ctx) {
        destroy();
    }

    public void channelRegistered(ChannelHandlerContext ctx) {
        if (ctx.channel().isActive()) {
            initialize(ctx);
        }
        super.channelRegistered(ctx);
    }

    public void channelActive(ChannelHandlerContext ctx) {
        initialize(ctx);
        super.channelActive(ctx);
    }

    public void channelInactive(ChannelHandlerContext ctx) {
        destroy();
        super.channelInactive(ctx);
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (this.readerIdleTimeNanos > 0 || this.allIdleTimeNanos > 0) {
            this.reading = true;
            this.firstAllIdleEvent = true;
            this.firstReaderIdleEvent = true;
        }
        ctx.fireChannelRead(msg);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) {
        if ((this.readerIdleTimeNanos > 0 || this.allIdleTimeNanos > 0) && this.reading) {
            this.lastReadTime = ticksInNanos();
            this.reading = false;
        }
        ctx.fireChannelReadComplete();
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        if (this.writerIdleTimeNanos > 0 || this.allIdleTimeNanos > 0) {
            ctx.write(msg, promise).addListener(this.writeListener);
        } else {
            ctx.write(msg, promise);
        }
    }

    private void initialize(ChannelHandlerContext ctx) {
        switch (this.state) {
            case 1:
            case 2:
                return;
            default:
                this.state = 1;
                initOutputChanged(ctx);
                long ticksInNanos = ticksInNanos();
                this.lastWriteTime = ticksInNanos;
                this.lastReadTime = ticksInNanos;
                if (this.readerIdleTimeNanos > 0) {
                    this.readerIdleTimeout = schedule(ctx, new ReaderIdleTimeoutTask(ctx), this.readerIdleTimeNanos, TimeUnit.NANOSECONDS);
                }
                if (this.writerIdleTimeNanos > 0) {
                    this.writerIdleTimeout = schedule(ctx, new WriterIdleTimeoutTask(ctx), this.writerIdleTimeNanos, TimeUnit.NANOSECONDS);
                }
                if (this.allIdleTimeNanos > 0) {
                    this.allIdleTimeout = schedule(ctx, new AllIdleTimeoutTask(ctx), this.allIdleTimeNanos, TimeUnit.NANOSECONDS);
                    return;
                }
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public long ticksInNanos() {
        return System.nanoTime();
    }

    /* access modifiers changed from: package-private */
    public ScheduledFuture<?> schedule(ChannelHandlerContext ctx, Runnable task, long delay, TimeUnit unit) {
        return ctx.executor().schedule(task, delay, unit);
    }

    private void destroy() {
        this.state = 2;
        ScheduledFuture<?> scheduledFuture = this.readerIdleTimeout;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
            this.readerIdleTimeout = null;
        }
        ScheduledFuture<?> scheduledFuture2 = this.writerIdleTimeout;
        if (scheduledFuture2 != null) {
            scheduledFuture2.cancel(false);
            this.writerIdleTimeout = null;
        }
        ScheduledFuture<?> scheduledFuture3 = this.allIdleTimeout;
        if (scheduledFuture3 != null) {
            scheduledFuture3.cancel(false);
            this.allIdleTimeout = null;
        }
    }

    /* access modifiers changed from: protected */
    public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        ctx.fireUserEventTriggered(evt);
    }

    /* renamed from: io.netty.handler.timeout.IdleStateHandler$2  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$timeout$IdleState;

        static {
            int[] iArr = new int[IdleState.values().length];
            $SwitchMap$io$netty$handler$timeout$IdleState = iArr;
            try {
                iArr[IdleState.ALL_IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$netty$handler$timeout$IdleState[IdleState.READER_IDLE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$io$netty$handler$timeout$IdleState[IdleState.WRITER_IDLE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public IdleStateEvent newIdleStateEvent(IdleState state2, boolean first) {
        switch (AnonymousClass2.$SwitchMap$io$netty$handler$timeout$IdleState[state2.ordinal()]) {
            case 1:
                return first ? IdleStateEvent.FIRST_ALL_IDLE_STATE_EVENT : IdleStateEvent.ALL_IDLE_STATE_EVENT;
            case 2:
                return first ? IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT : IdleStateEvent.READER_IDLE_STATE_EVENT;
            case 3:
                return first ? IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT : IdleStateEvent.WRITER_IDLE_STATE_EVENT;
            default:
                throw new IllegalArgumentException("Unhandled: state=" + state2 + ", first=" + first);
        }
    }

    private void initOutputChanged(ChannelHandlerContext ctx) {
        ChannelOutboundBuffer buf;
        if (this.observeOutput && (buf = ctx.channel().unsafe().outboundBuffer()) != null) {
            this.lastMessageHashCode = System.identityHashCode(buf.current());
            this.lastPendingWriteBytes = buf.totalPendingWriteBytes();
        }
    }

    /* access modifiers changed from: private */
    public boolean hasOutputChanged(ChannelHandlerContext ctx, boolean first) {
        if (!this.observeOutput) {
            return false;
        }
        long j = this.lastChangeCheckTimeStamp;
        long j2 = this.lastWriteTime;
        if (j != j2) {
            this.lastChangeCheckTimeStamp = j2;
            if (!first) {
                return true;
            }
        }
        ChannelOutboundBuffer buf = ctx.channel().unsafe().outboundBuffer();
        if (buf == null) {
            return false;
        }
        int messageHashCode = System.identityHashCode(buf.current());
        long pendingWriteBytes = buf.totalPendingWriteBytes();
        if (messageHashCode == this.lastMessageHashCode && pendingWriteBytes == this.lastPendingWriteBytes) {
            return false;
        }
        this.lastMessageHashCode = messageHashCode;
        this.lastPendingWriteBytes = pendingWriteBytes;
        if (!first) {
            return true;
        }
        return false;
    }

    public static abstract class AbstractIdleTask implements Runnable {
        private final ChannelHandlerContext ctx;

        /* access modifiers changed from: protected */
        public abstract void run(ChannelHandlerContext channelHandlerContext);

        AbstractIdleTask(ChannelHandlerContext ctx2) {
            this.ctx = ctx2;
        }

        public void run() {
            if (this.ctx.channel().isOpen()) {
                run(this.ctx);
            }
        }
    }

    public final class ReaderIdleTimeoutTask extends AbstractIdleTask {
        ReaderIdleTimeoutTask(ChannelHandlerContext ctx) {
            super(ctx);
        }

        /* access modifiers changed from: protected */
        public void run(ChannelHandlerContext ctx) {
            long nextDelay = IdleStateHandler.this.readerIdleTimeNanos;
            if (!IdleStateHandler.this.reading) {
                nextDelay -= IdleStateHandler.this.ticksInNanos() - IdleStateHandler.this.lastReadTime;
            }
            if (nextDelay <= 0) {
                IdleStateHandler idleStateHandler = IdleStateHandler.this;
                ScheduledFuture unused = idleStateHandler.readerIdleTimeout = idleStateHandler.schedule(ctx, this, idleStateHandler.readerIdleTimeNanos, TimeUnit.NANOSECONDS);
                boolean first = IdleStateHandler.this.firstReaderIdleEvent;
                boolean unused2 = IdleStateHandler.this.firstReaderIdleEvent = false;
                try {
                    IdleStateHandler.this.channelIdle(ctx, IdleStateHandler.this.newIdleStateEvent(IdleState.READER_IDLE, first));
                } catch (Throwable t) {
                    ctx.fireExceptionCaught(t);
                }
            } else {
                IdleStateHandler idleStateHandler2 = IdleStateHandler.this;
                ScheduledFuture unused3 = idleStateHandler2.readerIdleTimeout = idleStateHandler2.schedule(ctx, this, nextDelay, TimeUnit.NANOSECONDS);
            }
        }
    }

    public final class WriterIdleTimeoutTask extends AbstractIdleTask {
        WriterIdleTimeoutTask(ChannelHandlerContext ctx) {
            super(ctx);
        }

        /* access modifiers changed from: protected */
        public void run(ChannelHandlerContext ctx) {
            long nextDelay = IdleStateHandler.this.writerIdleTimeNanos - (IdleStateHandler.this.ticksInNanos() - IdleStateHandler.this.lastWriteTime);
            if (nextDelay <= 0) {
                IdleStateHandler idleStateHandler = IdleStateHandler.this;
                ScheduledFuture unused = idleStateHandler.writerIdleTimeout = idleStateHandler.schedule(ctx, this, idleStateHandler.writerIdleTimeNanos, TimeUnit.NANOSECONDS);
                boolean first = IdleStateHandler.this.firstWriterIdleEvent;
                boolean unused2 = IdleStateHandler.this.firstWriterIdleEvent = false;
                try {
                    if (!IdleStateHandler.this.hasOutputChanged(ctx, first)) {
                        IdleStateHandler.this.channelIdle(ctx, IdleStateHandler.this.newIdleStateEvent(IdleState.WRITER_IDLE, first));
                    }
                } catch (Throwable t) {
                    ctx.fireExceptionCaught(t);
                }
            } else {
                IdleStateHandler idleStateHandler2 = IdleStateHandler.this;
                ScheduledFuture unused3 = idleStateHandler2.writerIdleTimeout = idleStateHandler2.schedule(ctx, this, nextDelay, TimeUnit.NANOSECONDS);
            }
        }
    }

    public final class AllIdleTimeoutTask extends AbstractIdleTask {
        AllIdleTimeoutTask(ChannelHandlerContext ctx) {
            super(ctx);
        }

        /* access modifiers changed from: protected */
        public void run(ChannelHandlerContext ctx) {
            long nextDelay = IdleStateHandler.this.allIdleTimeNanos;
            if (!IdleStateHandler.this.reading) {
                nextDelay -= IdleStateHandler.this.ticksInNanos() - Math.max(IdleStateHandler.this.lastReadTime, IdleStateHandler.this.lastWriteTime);
            }
            if (nextDelay <= 0) {
                IdleStateHandler idleStateHandler = IdleStateHandler.this;
                ScheduledFuture unused = idleStateHandler.allIdleTimeout = idleStateHandler.schedule(ctx, this, idleStateHandler.allIdleTimeNanos, TimeUnit.NANOSECONDS);
                boolean first = IdleStateHandler.this.firstAllIdleEvent;
                boolean unused2 = IdleStateHandler.this.firstAllIdleEvent = false;
                try {
                    if (!IdleStateHandler.this.hasOutputChanged(ctx, first)) {
                        IdleStateHandler.this.channelIdle(ctx, IdleStateHandler.this.newIdleStateEvent(IdleState.ALL_IDLE, first));
                    }
                } catch (Throwable t) {
                    ctx.fireExceptionCaught(t);
                }
            } else {
                IdleStateHandler idleStateHandler2 = IdleStateHandler.this;
                ScheduledFuture unused3 = idleStateHandler2.allIdleTimeout = idleStateHandler2.schedule(ctx, this, nextDelay, TimeUnit.NANOSECONDS);
            }
        }
    }
}
