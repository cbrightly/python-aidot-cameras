package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.socket.ChannelOutputShutdownEvent;
import io.netty.channel.socket.ChannelOutputShutdownException;
import io.netty.util.DefaultAttributeMap;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.NotYetConnectedException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

public abstract class AbstractChannel extends DefaultAttributeMap implements Channel {
    /* access modifiers changed from: private */
    public static final ClosedChannelException CLOSE_CLOSED_CHANNEL_EXCEPTION;
    /* access modifiers changed from: private */
    public static final ClosedChannelException ENSURE_OPEN_CLOSED_CHANNEL_EXCEPTION;
    /* access modifiers changed from: private */
    public static final ClosedChannelException FLUSH0_CLOSED_CHANNEL_EXCEPTION;
    /* access modifiers changed from: private */
    public static final NotYetConnectedException FLUSH0_NOT_YET_CONNECTED_EXCEPTION;
    /* access modifiers changed from: private */
    public static final ClosedChannelException WRITE_CLOSED_CHANNEL_EXCEPTION;
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) AbstractChannel.class);
    /* access modifiers changed from: private */
    public final CloseFuture closeFuture = new CloseFuture(this);
    /* access modifiers changed from: private */
    public boolean closeInitiated;
    /* access modifiers changed from: private */
    public volatile EventLoop eventLoop;
    private final long hashCode = PlatformDependent.threadLocalRandom().nextLong();
    private volatile SocketAddress localAddress;
    private final Channel parent;
    /* access modifiers changed from: private */
    public final DefaultChannelPipeline pipeline;
    /* access modifiers changed from: private */
    public volatile boolean registered;
    private volatile SocketAddress remoteAddress;
    private String strVal;
    private boolean strValActive;
    private final ChannelFuture succeededFuture = new SucceededChannelFuture(this, (EventExecutor) null);
    private final Channel.Unsafe unsafe;
    /* access modifiers changed from: private */
    public final VoidChannelPromise unsafeVoidPromise = new VoidChannelPromise(this, false);
    private final VoidChannelPromise voidPromise = new VoidChannelPromise(this, true);

    /* access modifiers changed from: protected */
    public abstract void doBeginRead();

    /* access modifiers changed from: protected */
    public abstract void doBind(SocketAddress socketAddress);

    /* access modifiers changed from: protected */
    public abstract void doClose();

    /* access modifiers changed from: protected */
    public abstract void doDisconnect();

    /* access modifiers changed from: protected */
    public abstract void doWrite(ChannelOutboundBuffer channelOutboundBuffer);

    /* access modifiers changed from: protected */
    public abstract boolean isCompatible(EventLoop eventLoop2);

    /* access modifiers changed from: protected */
    public abstract SocketAddress localAddress0();

    /* access modifiers changed from: protected */
    public abstract AbstractUnsafe newUnsafe();

    /* access modifiers changed from: protected */
    public abstract SocketAddress remoteAddress0();

    static {
        Class cls = AbstractUnsafe.class;
        FLUSH0_CLOSED_CHANNEL_EXCEPTION = (ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), cls, "flush0()");
        ENSURE_OPEN_CLOSED_CHANNEL_EXCEPTION = (ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), cls, "ensureOpen(...)");
        CLOSE_CLOSED_CHANNEL_EXCEPTION = (ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), cls, "close(...)");
        WRITE_CLOSED_CHANNEL_EXCEPTION = (ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), cls, "write(...)");
        FLUSH0_NOT_YET_CONNECTED_EXCEPTION = (NotYetConnectedException) ThrowableUtil.unknownStackTrace(new NotYetConnectedException(), cls, "flush0()");
    }

    protected AbstractChannel(Channel parent2) {
        this.parent = parent2;
        this.unsafe = newUnsafe();
        this.pipeline = newChannelPipeline();
    }

    /* access modifiers changed from: protected */
    public DefaultChannelPipeline newChannelPipeline() {
        return new DefaultChannelPipeline(this);
    }

    public boolean isWritable() {
        ChannelOutboundBuffer buf = this.unsafe.outboundBuffer();
        return buf != null && buf.isWritable();
    }

    public Channel parent() {
        return this.parent;
    }

    public ChannelPipeline pipeline() {
        return this.pipeline;
    }

    public ByteBufAllocator alloc() {
        return config().getAllocator();
    }

    public EventLoop eventLoop() {
        EventLoop eventLoop2 = this.eventLoop;
        if (eventLoop2 != null) {
            return eventLoop2;
        }
        throw new IllegalStateException("channel not registered to an event loop");
    }

    public SocketAddress localAddress() {
        SocketAddress localAddress2 = this.localAddress;
        if (localAddress2 != null) {
            return localAddress2;
        }
        try {
            SocketAddress localAddress3 = unsafe().localAddress();
            SocketAddress localAddress4 = localAddress3;
            this.localAddress = localAddress3;
            return localAddress4;
        } catch (Throwable th) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void invalidateLocalAddress() {
        this.localAddress = null;
    }

    public SocketAddress remoteAddress() {
        SocketAddress remoteAddress2 = this.remoteAddress;
        if (remoteAddress2 != null) {
            return remoteAddress2;
        }
        try {
            SocketAddress remoteAddress3 = unsafe().remoteAddress();
            SocketAddress remoteAddress4 = remoteAddress3;
            this.remoteAddress = remoteAddress3;
            return remoteAddress4;
        } catch (Throwable th) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void invalidateRemoteAddress() {
        this.remoteAddress = null;
    }

    public boolean isRegistered() {
        return this.registered;
    }

    public ChannelFuture bind(SocketAddress localAddress2) {
        return this.pipeline.bind(localAddress2);
    }

    public ChannelFuture connect(SocketAddress remoteAddress2) {
        return this.pipeline.connect(remoteAddress2);
    }

    public ChannelFuture connect(SocketAddress remoteAddress2, SocketAddress localAddress2) {
        return this.pipeline.connect(remoteAddress2, localAddress2);
    }

    public ChannelFuture disconnect() {
        return this.pipeline.disconnect();
    }

    public ChannelFuture close() {
        return this.pipeline.close();
    }

    public ChannelFuture deregister() {
        return this.pipeline.deregister();
    }

    public Channel flush() {
        this.pipeline.flush();
        return this;
    }

    public ChannelFuture bind(SocketAddress localAddress2, ChannelPromise promise) {
        return this.pipeline.bind(localAddress2, promise);
    }

    public ChannelFuture connect(SocketAddress remoteAddress2, ChannelPromise promise) {
        return this.pipeline.connect(remoteAddress2, promise);
    }

    public ChannelFuture connect(SocketAddress remoteAddress2, SocketAddress localAddress2, ChannelPromise promise) {
        return this.pipeline.connect(remoteAddress2, localAddress2, promise);
    }

    public ChannelFuture disconnect(ChannelPromise promise) {
        return this.pipeline.disconnect(promise);
    }

    public ChannelFuture close(ChannelPromise promise) {
        return this.pipeline.close(promise);
    }

    public ChannelFuture deregister(ChannelPromise promise) {
        return this.pipeline.deregister(promise);
    }

    public Channel read() {
        this.pipeline.read();
        return this;
    }

    public ChannelFuture write(Object msg) {
        return this.pipeline.write(msg);
    }

    public ChannelFuture write(Object msg, ChannelPromise promise) {
        return this.pipeline.write(msg, promise);
    }

    public ChannelFuture writeAndFlush(Object msg) {
        return this.pipeline.writeAndFlush(msg);
    }

    public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
        return this.pipeline.writeAndFlush(msg, promise);
    }

    public ChannelPromise newPromise() {
        return new DefaultChannelPromise(this);
    }

    public ChannelProgressivePromise newProgressivePromise() {
        return new DefaultChannelProgressivePromise(this);
    }

    public ChannelFuture newSucceededFuture() {
        return this.succeededFuture;
    }

    public ChannelFuture newFailedFuture(Throwable cause) {
        return new FailedChannelFuture(this, (EventExecutor) null, cause);
    }

    public ChannelFuture closeFuture() {
        return this.closeFuture;
    }

    public Channel.Unsafe unsafe() {
        return this.unsafe;
    }

    public final int hashCode() {
        return (int) this.hashCode;
    }

    public final boolean equals(Object o) {
        return this == o;
    }

    public final int compareTo(Channel o) {
        if (this == o) {
            return 0;
        }
        long ret = this.hashCode - ((long) o.hashCode());
        if (ret > 0) {
            return 1;
        }
        if (ret < 0) {
            return -1;
        }
        long ret2 = (long) (System.identityHashCode(this) - System.identityHashCode(o));
        if (ret2 != 0) {
            return (int) ret2;
        }
        throw new Error();
    }

    public String toString() {
        String str;
        boolean active = isActive();
        if (this.strValActive == active && (str = this.strVal) != null) {
            return str;
        }
        SocketAddress remoteAddr = remoteAddress();
        SocketAddress localAddr = localAddress();
        if (remoteAddr != null) {
            this.strVal = String.format("[id: 0x%08x, L:%s %s R:%s]", new Object[]{Integer.valueOf((int) this.hashCode), localAddr, active ? "-" : "!", remoteAddr});
        } else if (localAddr != null) {
            this.strVal = String.format("[id: 0x%08x, L:%s]", new Object[]{Integer.valueOf((int) this.hashCode), localAddr});
        } else {
            this.strVal = String.format("[id: 0x%08x]", new Object[]{Integer.valueOf((int) this.hashCode)});
        }
        this.strValActive = active;
        return this.strVal;
    }

    public final ChannelPromise voidPromise() {
        return this.voidPromise;
    }

    public abstract class AbstractUnsafe implements Channel.Unsafe {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private boolean inFlush0;
        private boolean neverRegistered = true;
        private volatile ChannelOutboundBuffer outboundBuffer;

        static {
            Class<AbstractChannel> cls = AbstractChannel.class;
        }

        protected AbstractUnsafe() {
            this.outboundBuffer = new ChannelOutboundBuffer(AbstractChannel.this);
        }

        private void assertEventLoop() {
            if (AbstractChannel.this.registered && !AbstractChannel.this.eventLoop.inEventLoop()) {
                throw new AssertionError();
            }
        }

        public final ChannelOutboundBuffer outboundBuffer() {
            return this.outboundBuffer;
        }

        public final SocketAddress localAddress() {
            return AbstractChannel.this.localAddress0();
        }

        public final SocketAddress remoteAddress() {
            return AbstractChannel.this.remoteAddress0();
        }

        public final void register(EventLoop eventLoop, final ChannelPromise promise) {
            if (eventLoop == null) {
                throw new NullPointerException("eventLoop");
            } else if (AbstractChannel.this.isRegistered()) {
                promise.setFailure(new IllegalStateException("registered to an event loop already"));
            } else if (!AbstractChannel.this.isCompatible(eventLoop)) {
                promise.setFailure(new IllegalStateException("incompatible event loop type: " + eventLoop.getClass().getName()));
            } else {
                EventLoop unused = AbstractChannel.this.eventLoop = eventLoop;
                if (eventLoop.inEventLoop()) {
                    register0(promise);
                    return;
                }
                try {
                    eventLoop.execute(new Runnable() {
                        public void run() {
                            AbstractUnsafe.this.register0(promise);
                        }
                    });
                } catch (Throwable t) {
                    AbstractChannel.logger.warn("Force-closing a channel whose registration task was not accepted by an event loop: {}", AbstractChannel.this, t);
                    closeForcibly();
                    AbstractChannel.this.closeFuture.setClosed();
                    safeSetFailure(promise, t);
                }
            }
        }

        /* access modifiers changed from: private */
        public void register0(ChannelPromise promise) {
            try {
                if (!promise.setUncancellable()) {
                    return;
                }
                if (ensureOpen(promise)) {
                    boolean firstRegistration = this.neverRegistered;
                    AbstractChannel.this.doRegister();
                    this.neverRegistered = false;
                    boolean unused = AbstractChannel.this.registered = true;
                    AbstractChannel.this.pipeline.invokeHandlerAddedIfNeeded();
                    safeSetSuccess(promise);
                    AbstractChannel.this.pipeline.fireChannelRegistered();
                    if (!AbstractChannel.this.isActive()) {
                        return;
                    }
                    if (firstRegistration) {
                        AbstractChannel.this.pipeline.fireChannelActive();
                    } else if (AbstractChannel.this.config().isAutoRead()) {
                        beginRead();
                    }
                }
            } catch (Throwable t) {
                closeForcibly();
                AbstractChannel.this.closeFuture.setClosed();
                safeSetFailure(promise, t);
            }
        }

        public final void bind(SocketAddress localAddress, ChannelPromise promise) {
            assertEventLoop();
            if (promise.setUncancellable() && ensureOpen(promise)) {
                if (Boolean.TRUE.equals(AbstractChannel.this.config().getOption(ChannelOption.SO_BROADCAST)) && (localAddress instanceof InetSocketAddress) && !((InetSocketAddress) localAddress).getAddress().isAnyLocalAddress() && !PlatformDependent.isWindows() && !PlatformDependent.maybeSuperUser()) {
                    InternalLogger access$300 = AbstractChannel.logger;
                    access$300.warn("A non-root user can't receive a broadcast packet if the socket is not bound to a wildcard address; binding to a non-wildcard address (" + localAddress + ") anyway as requested.");
                }
                boolean wasActive = AbstractChannel.this.isActive();
                try {
                    AbstractChannel.this.doBind(localAddress);
                    if (!wasActive && AbstractChannel.this.isActive()) {
                        invokeLater(new Runnable() {
                            public void run() {
                                AbstractChannel.this.pipeline.fireChannelActive();
                            }
                        });
                    }
                    safeSetSuccess(promise);
                } catch (Throwable t) {
                    safeSetFailure(promise, t);
                    closeIfClosed();
                }
            }
        }

        public final void disconnect(ChannelPromise promise) {
            assertEventLoop();
            if (promise.setUncancellable()) {
                boolean wasActive = AbstractChannel.this.isActive();
                try {
                    AbstractChannel.this.doDisconnect();
                    if (wasActive && !AbstractChannel.this.isActive()) {
                        invokeLater(new Runnable() {
                            public void run() {
                                AbstractChannel.this.pipeline.fireChannelInactive();
                            }
                        });
                    }
                    safeSetSuccess(promise);
                    closeIfClosed();
                } catch (Throwable t) {
                    safeSetFailure(promise, t);
                    closeIfClosed();
                }
            }
        }

        public final void close(ChannelPromise promise) {
            assertEventLoop();
            close(promise, AbstractChannel.CLOSE_CLOSED_CHANNEL_EXCEPTION, AbstractChannel.CLOSE_CLOSED_CHANNEL_EXCEPTION, false);
        }

        public final void shutdownOutput(ChannelPromise promise) {
            assertEventLoop();
            shutdownOutput(promise, (Throwable) null);
        }

        private void shutdownOutput(final ChannelPromise promise, Throwable cause) {
            Throwable channelOutputShutdownException;
            if (promise.setUncancellable()) {
                final ChannelOutboundBuffer outboundBuffer2 = this.outboundBuffer;
                if (outboundBuffer2 == null) {
                    promise.setFailure(AbstractChannel.CLOSE_CLOSED_CHANNEL_EXCEPTION);
                    return;
                }
                this.outboundBuffer = null;
                if (cause != null) {
                    channelOutputShutdownException = new ChannelOutputShutdownException("Channel output shutdown", cause);
                }
                final Throwable shutdownCause = channelOutputShutdownException;
                Executor closeExecutor = prepareToClose();
                if (closeExecutor != null) {
                    closeExecutor.execute(new Runnable() {
                        public void run() {
                            AnonymousClass1 r1;
                            EventLoop eventLoop;
                            try {
                                AbstractChannel.this.doShutdownOutput();
                                promise.setSuccess();
                                eventLoop = AbstractChannel.this.eventLoop();
                                r1 = new Runnable() {
                                    public void run() {
                                        AbstractUnsafe abstractUnsafe = AbstractUnsafe.this;
                                        DefaultChannelPipeline access$500 = AbstractChannel.this.pipeline;
                                        AnonymousClass4 r2 = AnonymousClass4.this;
                                        abstractUnsafe.closeOutboundBufferForShutdown(access$500, outboundBuffer2, shutdownCause);
                                    }
                                };
                            } catch (Throwable th) {
                                AbstractChannel.this.eventLoop().execute(new Runnable() {
                                    public void run() {
                                        AbstractUnsafe abstractUnsafe = AbstractUnsafe.this;
                                        DefaultChannelPipeline access$500 = AbstractChannel.this.pipeline;
                                        AnonymousClass4 r2 = AnonymousClass4.this;
                                        abstractUnsafe.closeOutboundBufferForShutdown(access$500, outboundBuffer2, shutdownCause);
                                    }
                                });
                                throw th;
                            }
                            eventLoop.execute(r1);
                        }
                    });
                    return;
                }
                try {
                    AbstractChannel.this.doShutdownOutput();
                    promise.setSuccess();
                } catch (Throwable th) {
                    closeOutboundBufferForShutdown(AbstractChannel.this.pipeline, outboundBuffer2, shutdownCause);
                    throw th;
                }
                closeOutboundBufferForShutdown(AbstractChannel.this.pipeline, outboundBuffer2, shutdownCause);
            }
        }

        /* access modifiers changed from: private */
        public void closeOutboundBufferForShutdown(ChannelPipeline pipeline, ChannelOutboundBuffer buffer, Throwable cause) {
            buffer.failFlushed(cause, false);
            buffer.close(cause, true);
            pipeline.fireUserEventTriggered(ChannelOutputShutdownEvent.INSTANCE);
        }

        private void close(ChannelPromise promise, Throwable cause, ClosedChannelException closeCause, boolean notify) {
            final ChannelPromise channelPromise = promise;
            Throwable th = cause;
            ClosedChannelException closedChannelException = closeCause;
            boolean z = notify;
            if (promise.setUncancellable()) {
                if (!AbstractChannel.this.closeInitiated) {
                    boolean unused = AbstractChannel.this.closeInitiated = true;
                    final boolean wasActive = AbstractChannel.this.isActive();
                    ChannelOutboundBuffer outboundBuffer2 = this.outboundBuffer;
                    this.outboundBuffer = null;
                    Executor closeExecutor = prepareToClose();
                    if (closeExecutor != null) {
                        final ChannelPromise channelPromise2 = promise;
                        final ChannelOutboundBuffer channelOutboundBuffer = outboundBuffer2;
                        final Throwable th2 = cause;
                        final boolean z2 = notify;
                        final ClosedChannelException closedChannelException2 = closeCause;
                        Executor closeExecutor2 = closeExecutor;
                        final boolean z3 = wasActive;
                        closeExecutor2.execute(new Runnable() {
                            public void run() {
                                try {
                                    AbstractUnsafe.this.doClose0(channelPromise2);
                                } finally {
                                    AbstractUnsafe.this.invokeLater(new Runnable() {
                                        public void run() {
                                            AnonymousClass6 r0 = AnonymousClass6.this;
                                            ChannelOutboundBuffer channelOutboundBuffer = channelOutboundBuffer;
                                            if (channelOutboundBuffer != null) {
                                                channelOutboundBuffer.failFlushed(th2, z2);
                                                AnonymousClass6 r02 = AnonymousClass6.this;
                                                channelOutboundBuffer.close(closedChannelException2);
                                            }
                                            AnonymousClass6 r03 = AnonymousClass6.this;
                                            AbstractUnsafe.this.fireChannelInactiveAndDeregister(z3);
                                        }
                                    });
                                }
                            }
                        });
                        return;
                    }
                    try {
                        doClose0(promise);
                        if (outboundBuffer2 != null) {
                            outboundBuffer2.failFlushed(th, z);
                            outboundBuffer2.close(closedChannelException);
                        }
                        if (this.inFlush0) {
                            invokeLater(new Runnable() {
                                public void run() {
                                    AbstractUnsafe.this.fireChannelInactiveAndDeregister(wasActive);
                                }
                            });
                        } else {
                            fireChannelInactiveAndDeregister(wasActive);
                        }
                    } catch (Throwable th3) {
                        Throwable th4 = th3;
                        if (outboundBuffer2 != null) {
                            outboundBuffer2.failFlushed(th, z);
                            outboundBuffer2.close(closedChannelException);
                        }
                        throw th4;
                    }
                } else if (AbstractChannel.this.closeFuture.isDone()) {
                    safeSetSuccess(promise);
                } else if (!(channelPromise instanceof VoidChannelPromise)) {
                    AbstractChannel.this.closeFuture.addListener((GenericFutureListener) new ChannelFutureListener() {
                        public void operationComplete(ChannelFuture future) {
                            channelPromise.setSuccess();
                        }
                    });
                }
            }
        }

        /* access modifiers changed from: private */
        public void doClose0(ChannelPromise promise) {
            try {
                AbstractChannel.this.doClose();
                AbstractChannel.this.closeFuture.setClosed();
                safeSetSuccess(promise);
            } catch (Throwable t) {
                AbstractChannel.this.closeFuture.setClosed();
                safeSetFailure(promise, t);
            }
        }

        /* access modifiers changed from: private */
        public void fireChannelInactiveAndDeregister(boolean wasActive) {
            deregister(voidPromise(), wasActive && !AbstractChannel.this.isActive());
        }

        public final void closeForcibly() {
            assertEventLoop();
            try {
                AbstractChannel.this.doClose();
            } catch (Exception e) {
                AbstractChannel.logger.warn("Failed to close a channel.", (Throwable) e);
            }
        }

        public final void deregister(ChannelPromise promise) {
            assertEventLoop();
            deregister(promise, false);
        }

        private void deregister(final ChannelPromise promise, final boolean fireChannelInactive) {
            if (promise.setUncancellable()) {
                if (!AbstractChannel.this.registered) {
                    safeSetSuccess(promise);
                } else {
                    invokeLater(new Runnable() {
                        /* JADX WARNING: Code restructure failed: missing block: B:17:0x005c, code lost:
                            if (io.netty.channel.AbstractChannel.access$000(r4.this$1.this$0) == false) goto L_0x0033;
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:18:0x005f, code lost:
                            return;
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:7:0x001f, code lost:
                            if (io.netty.channel.AbstractChannel.access$000(r4.this$1.this$0) != false) goto L_0x0021;
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0021, code lost:
                            io.netty.channel.AbstractChannel.access$002(r4.this$1.this$0, false);
                            io.netty.channel.AbstractChannel.access$500(r4.this$1.this$0).fireChannelUnregistered();
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0033, code lost:
                            r4.this$1.safeSetSuccess(r2);
                         */
                        /* Code decompiled incorrectly, please refer to instructions dump. */
                        public void run() {
                            /*
                                r4 = this;
                                r0 = 0
                                io.netty.channel.AbstractChannel$AbstractUnsafe r1 = io.netty.channel.AbstractChannel.AbstractUnsafe.this     // Catch:{ all -> 0x003b }
                                io.netty.channel.AbstractChannel r1 = io.netty.channel.AbstractChannel.this     // Catch:{ all -> 0x003b }
                                r1.doDeregister()     // Catch:{ all -> 0x003b }
                                boolean r1 = r3
                                if (r1 == 0) goto L_0x0017
                                io.netty.channel.AbstractChannel$AbstractUnsafe r1 = io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.AbstractChannel r1 = io.netty.channel.AbstractChannel.this
                                io.netty.channel.DefaultChannelPipeline r1 = r1.pipeline
                                r1.fireChannelInactive()
                            L_0x0017:
                                io.netty.channel.AbstractChannel$AbstractUnsafe r1 = io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.AbstractChannel r1 = io.netty.channel.AbstractChannel.this
                                boolean r1 = r1.registered
                                if (r1 == 0) goto L_0x0033
                            L_0x0021:
                                io.netty.channel.AbstractChannel$AbstractUnsafe r1 = io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.AbstractChannel r1 = io.netty.channel.AbstractChannel.this
                                boolean unused = r1.registered = r0
                                io.netty.channel.AbstractChannel$AbstractUnsafe r0 = io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.AbstractChannel r0 = io.netty.channel.AbstractChannel.this
                                io.netty.channel.DefaultChannelPipeline r0 = r0.pipeline
                                r0.fireChannelUnregistered()
                            L_0x0033:
                                io.netty.channel.AbstractChannel$AbstractUnsafe r0 = io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.ChannelPromise r1 = r2
                                r0.safeSetSuccess(r1)
                                goto L_0x005f
                            L_0x003b:
                                r1 = move-exception
                                io.netty.util.internal.logging.InternalLogger r2 = io.netty.channel.AbstractChannel.logger     // Catch:{ all -> 0x0060 }
                                java.lang.String r3 = "Unexpected exception occurred while deregistering a channel."
                                r2.warn((java.lang.String) r3, (java.lang.Throwable) r1)     // Catch:{ all -> 0x0060 }
                                boolean r1 = r3
                                if (r1 == 0) goto L_0x0054
                                io.netty.channel.AbstractChannel$AbstractUnsafe r1 = io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.AbstractChannel r1 = io.netty.channel.AbstractChannel.this
                                io.netty.channel.DefaultChannelPipeline r1 = r1.pipeline
                                r1.fireChannelInactive()
                            L_0x0054:
                                io.netty.channel.AbstractChannel$AbstractUnsafe r1 = io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.AbstractChannel r1 = io.netty.channel.AbstractChannel.this
                                boolean r1 = r1.registered
                                if (r1 == 0) goto L_0x0033
                                goto L_0x0021
                            L_0x005f:
                                return
                            L_0x0060:
                                r1 = move-exception
                                boolean r2 = r3
                                if (r2 == 0) goto L_0x0070
                                io.netty.channel.AbstractChannel$AbstractUnsafe r2 = io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.AbstractChannel r2 = io.netty.channel.AbstractChannel.this
                                io.netty.channel.DefaultChannelPipeline r2 = r2.pipeline
                                r2.fireChannelInactive()
                            L_0x0070:
                                io.netty.channel.AbstractChannel$AbstractUnsafe r2 = io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.AbstractChannel r2 = io.netty.channel.AbstractChannel.this
                                boolean r2 = r2.registered
                                if (r2 == 0) goto L_0x008c
                                io.netty.channel.AbstractChannel$AbstractUnsafe r2 = io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.AbstractChannel r2 = io.netty.channel.AbstractChannel.this
                                boolean unused = r2.registered = r0
                                io.netty.channel.AbstractChannel$AbstractUnsafe r0 = io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.AbstractChannel r0 = io.netty.channel.AbstractChannel.this
                                io.netty.channel.DefaultChannelPipeline r0 = r0.pipeline
                                r0.fireChannelUnregistered()
                            L_0x008c:
                                io.netty.channel.AbstractChannel$AbstractUnsafe r0 = io.netty.channel.AbstractChannel.AbstractUnsafe.this
                                io.netty.channel.ChannelPromise r2 = r2
                                r0.safeSetSuccess(r2)
                                throw r1
                            */
                            throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.AbstractChannel.AbstractUnsafe.AnonymousClass8.run():void");
                        }
                    });
                }
            }
        }

        public final void beginRead() {
            assertEventLoop();
            if (AbstractChannel.this.isActive()) {
                try {
                    AbstractChannel.this.doBeginRead();
                } catch (Exception e) {
                    invokeLater(new Runnable() {
                        public void run() {
                            AbstractChannel.this.pipeline.fireExceptionCaught(e);
                        }
                    });
                    close(voidPromise());
                }
            }
        }

        public final void write(Object msg, ChannelPromise promise) {
            assertEventLoop();
            ChannelOutboundBuffer outboundBuffer2 = this.outboundBuffer;
            if (outboundBuffer2 == null) {
                safeSetFailure(promise, AbstractChannel.WRITE_CLOSED_CHANNEL_EXCEPTION);
                ReferenceCountUtil.release(msg);
                return;
            }
            try {
                Object msg2 = AbstractChannel.this.filterOutboundMessage(msg);
                int size = AbstractChannel.this.pipeline.estimatorHandle().size(msg2);
                if (size < 0) {
                    size = 0;
                }
                outboundBuffer2.addMessage(msg2, size, promise);
            } catch (Throwable t) {
                safeSetFailure(promise, t);
                ReferenceCountUtil.release(msg);
            }
        }

        public final void flush() {
            assertEventLoop();
            ChannelOutboundBuffer outboundBuffer2 = this.outboundBuffer;
            if (outboundBuffer2 != null) {
                outboundBuffer2.addFlush();
                flush0();
            }
        }

        /* access modifiers changed from: protected */
        public void flush0() {
            ChannelOutboundBuffer outboundBuffer2;
            if (!this.inFlush0 && (outboundBuffer2 = this.outboundBuffer) != null && !outboundBuffer2.isEmpty()) {
                this.inFlush0 = true;
                if (!AbstractChannel.this.isActive()) {
                    try {
                        if (AbstractChannel.this.isOpen()) {
                            outboundBuffer2.failFlushed(AbstractChannel.FLUSH0_NOT_YET_CONNECTED_EXCEPTION, true);
                        } else {
                            outboundBuffer2.failFlushed(AbstractChannel.FLUSH0_CLOSED_CHANNEL_EXCEPTION, false);
                        }
                    } finally {
                        this.inFlush0 = false;
                    }
                } else {
                    try {
                        AbstractChannel.this.doWrite(outboundBuffer2);
                    } catch (Throwable t2) {
                        close(voidPromise(), t2, AbstractChannel.FLUSH0_CLOSED_CHANNEL_EXCEPTION, false);
                    }
                    this.inFlush0 = false;
                }
            }
        }

        public final ChannelPromise voidPromise() {
            assertEventLoop();
            return AbstractChannel.this.unsafeVoidPromise;
        }

        /* access modifiers changed from: protected */
        public final boolean ensureOpen(ChannelPromise promise) {
            if (AbstractChannel.this.isOpen()) {
                return true;
            }
            safeSetFailure(promise, AbstractChannel.ENSURE_OPEN_CLOSED_CHANNEL_EXCEPTION);
            return false;
        }

        /* access modifiers changed from: protected */
        public final void safeSetSuccess(ChannelPromise promise) {
            if (!(promise instanceof VoidChannelPromise) && !promise.trySuccess()) {
                AbstractChannel.logger.warn("Failed to mark a promise as success because it is done already: {}", (Object) promise);
            }
        }

        /* access modifiers changed from: protected */
        public final void safeSetFailure(ChannelPromise promise, Throwable cause) {
            if (!(promise instanceof VoidChannelPromise) && !promise.tryFailure(cause)) {
                AbstractChannel.logger.warn("Failed to mark a promise as failure because it's done already: {}", promise, cause);
            }
        }

        /* access modifiers changed from: protected */
        public final void closeIfClosed() {
            if (!AbstractChannel.this.isOpen()) {
                close(voidPromise());
            }
        }

        /* access modifiers changed from: private */
        public void invokeLater(Runnable task) {
            try {
                AbstractChannel.this.eventLoop().execute(task);
            } catch (RejectedExecutionException e) {
                AbstractChannel.logger.warn("Can't invoke task later as EventLoop rejected it", (Throwable) e);
            }
        }

        /* access modifiers changed from: protected */
        public final Throwable annotateConnectException(Throwable cause, SocketAddress remoteAddress) {
            if (cause instanceof ConnectException) {
                return new AnnotatedConnectException((ConnectException) cause, remoteAddress);
            }
            if (cause instanceof NoRouteToHostException) {
                return new AnnotatedNoRouteToHostException((NoRouteToHostException) cause, remoteAddress);
            }
            if (cause instanceof SocketException) {
                return new AnnotatedSocketException((SocketException) cause, remoteAddress);
            }
            return cause;
        }

        /* access modifiers changed from: protected */
        public Executor prepareToClose() {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void doRegister() {
    }

    /* access modifiers changed from: protected */
    public void doShutdownOutput() {
        doClose();
    }

    /* access modifiers changed from: protected */
    public void doDeregister() {
    }

    /* access modifiers changed from: protected */
    public Object filterOutboundMessage(Object msg) {
        return msg;
    }

    public static final class CloseFuture extends DefaultChannelPromise {
        CloseFuture(AbstractChannel ch) {
            super(ch);
        }

        public ChannelPromise setSuccess() {
            throw new IllegalStateException();
        }

        public ChannelPromise setFailure(Throwable cause) {
            throw new IllegalStateException();
        }

        public boolean trySuccess() {
            throw new IllegalStateException();
        }

        public boolean tryFailure(Throwable cause) {
            throw new IllegalStateException();
        }

        /* access modifiers changed from: package-private */
        public boolean setClosed() {
            return super.trySuccess();
        }
    }

    public static final class AnnotatedConnectException extends ConnectException {
        private static final long serialVersionUID = 3901958112696433556L;

        AnnotatedConnectException(ConnectException exception, SocketAddress remoteAddress) {
            super(exception.getMessage() + ": " + remoteAddress);
            initCause(exception);
            setStackTrace(exception.getStackTrace());
        }

        public Throwable fillInStackTrace() {
            return this;
        }
    }

    public static final class AnnotatedNoRouteToHostException extends NoRouteToHostException {
        private static final long serialVersionUID = -6801433937592080623L;

        AnnotatedNoRouteToHostException(NoRouteToHostException exception, SocketAddress remoteAddress) {
            super(exception.getMessage() + ": " + remoteAddress);
            initCause(exception);
            setStackTrace(exception.getStackTrace());
        }

        public Throwable fillInStackTrace() {
            return this;
        }
    }

    public static final class AnnotatedSocketException extends SocketException {
        private static final long serialVersionUID = 3896743275010454039L;

        AnnotatedSocketException(SocketException exception, SocketAddress remoteAddress) {
            super(exception.getMessage() + ": " + remoteAddress);
            initCause(exception);
            setStackTrace(exception.getStackTrace());
        }

        public Throwable fillInStackTrace() {
            return this;
        }
    }
}
