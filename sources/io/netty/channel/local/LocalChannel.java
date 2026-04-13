package io.netty.channel.local;

import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.EventLoop;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.ConnectException;
import java.net.SocketAddress;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.NotYetConnectedException;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class LocalChannel extends AbstractChannel {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final ClosedChannelException DO_CLOSE_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), LocalChannel.class, "doClose()"));
    private static final ClosedChannelException DO_WRITE_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), LocalChannel.class, "doWrite(...)"));
    private static final AtomicReferenceFieldUpdater<LocalChannel, Future> FINISH_READ_FUTURE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(LocalChannel.class, Future.class, "finishReadFuture");
    private static final int MAX_READER_STACK_DEPTH = 8;
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) LocalChannel.class);
    private final ChannelConfig config = new DefaultChannelConfig(this);
    /* access modifiers changed from: private */
    public volatile ChannelPromise connectPromise;
    private volatile Future<?> finishReadFuture;
    final Queue<Object> inboundBuffer = PlatformDependent.newSpscQueue();
    private volatile LocalAddress localAddress;
    /* access modifiers changed from: private */
    public volatile LocalChannel peer;
    private volatile boolean readInProgress;
    private final Runnable readTask = new Runnable() {
        public void run() {
            ChannelPipeline pipeline = LocalChannel.this.pipeline();
            while (true) {
                Object m = LocalChannel.this.inboundBuffer.poll();
                if (m == null) {
                    pipeline.fireChannelReadComplete();
                    return;
                }
                pipeline.fireChannelRead(m);
            }
        }
    };
    /* access modifiers changed from: private */
    public volatile boolean registerInProgress;
    private volatile LocalAddress remoteAddress;
    private final Runnable shutdownHook = new Runnable() {
        public void run() {
            LocalChannel.this.unsafe().close(LocalChannel.this.unsafe().voidPromise());
        }
    };
    /* access modifiers changed from: private */
    public volatile int state;
    private volatile boolean writeInProgress;

    public enum State {
        OPEN,
        BOUND,
        CONNECTED,
        CLOSED
    }

    static {
        Class<LocalChannel> cls = LocalChannel.class;
    }

    public LocalChannel() {
        super((Channel) null);
    }

    protected LocalChannel(LocalServerChannel parent, LocalChannel peer2) {
        super(parent);
        this.peer = peer2;
        this.localAddress = parent.localAddress();
        this.remoteAddress = peer2.localAddress();
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    public ChannelConfig config() {
        return this.config;
    }

    public LocalServerChannel parent() {
        return (LocalServerChannel) super.parent();
    }

    public LocalAddress localAddress() {
        return (LocalAddress) super.localAddress();
    }

    public LocalAddress remoteAddress() {
        return (LocalAddress) super.remoteAddress();
    }

    public boolean isOpen() {
        return this.state < 3;
    }

    public boolean isActive() {
        return this.state == 2;
    }

    /* access modifiers changed from: protected */
    public AbstractChannel.AbstractUnsafe newUnsafe() {
        return new LocalUnsafe();
    }

    /* access modifiers changed from: protected */
    public boolean isCompatible(EventLoop loop) {
        return loop instanceof SingleThreadEventLoop;
    }

    /* access modifiers changed from: protected */
    public SocketAddress localAddress0() {
        return this.localAddress;
    }

    /* access modifiers changed from: protected */
    public SocketAddress remoteAddress0() {
        return this.remoteAddress;
    }

    /* access modifiers changed from: protected */
    public void doRegister() {
        if (!(this.peer == null || parent() == null)) {
            final LocalChannel peer2 = this.peer;
            this.registerInProgress = true;
            this.state = 2;
            peer2.remoteAddress = parent().localAddress();
            peer2.state = 2;
            peer2.eventLoop().execute(new Runnable() {
                public void run() {
                    boolean unused = LocalChannel.this.registerInProgress = false;
                    ChannelPromise promise = peer2.connectPromise;
                    if (promise != null && promise.trySuccess()) {
                        peer2.pipeline().fireChannelActive();
                    }
                }
            });
        }
        ((SingleThreadEventExecutor) eventLoop()).addShutdownHook(this.shutdownHook);
    }

    /* access modifiers changed from: protected */
    public void doBind(SocketAddress localAddress2) {
        this.localAddress = LocalChannelRegistry.register(this, this.localAddress, localAddress2);
        this.state = 1;
    }

    /* access modifiers changed from: protected */
    public void doDisconnect() {
        doClose();
    }

    /* access modifiers changed from: protected */
    public void doClose() {
        EventLoop peerEventLoop;
        final LocalChannel peer2 = this.peer;
        int oldState = this.state;
        if (oldState <= 2) {
            try {
                if (this.localAddress != null) {
                    if (parent() == null) {
                        LocalChannelRegistry.unregister(this.localAddress);
                    }
                    this.localAddress = null;
                }
                this.state = 3;
                finishPeerRead(this);
                ChannelPromise promise = this.connectPromise;
                if (promise != null) {
                    promise.tryFailure(DO_CLOSE_CLOSED_CHANNEL_EXCEPTION);
                    this.connectPromise = null;
                }
            } catch (Throwable th) {
                if (!(peer2 == null || oldState == 3)) {
                    releaseInboundBuffers();
                }
                throw th;
            }
        }
        if (peer2 != null) {
            this.peer = null;
            peerEventLoop = peer2.eventLoop();
            final boolean peerIsActive = peer2.isActive();
            if (!peerEventLoop.inEventLoop() || this.registerInProgress) {
                peerEventLoop.execute(new Runnable() {
                    public void run() {
                        peer2.tryClose(peerIsActive);
                    }
                });
            } else {
                peer2.tryClose(peerIsActive);
            }
        }
        if (peer2 != null && oldState != 3) {
            releaseInboundBuffers();
        }
    }

    /* access modifiers changed from: private */
    public void tryClose(boolean isActive) {
        if (isActive) {
            unsafe().close(unsafe().voidPromise());
        } else {
            releaseInboundBuffers();
        }
    }

    /* access modifiers changed from: protected */
    public void doDeregister() {
        ((SingleThreadEventExecutor) eventLoop()).removeShutdownHook(this.shutdownHook);
    }

    /* access modifiers changed from: protected */
    public void doBeginRead() {
        if (!this.readInProgress) {
            ChannelPipeline pipeline = pipeline();
            Queue<Object> inboundBuffer2 = this.inboundBuffer;
            if (inboundBuffer2.isEmpty()) {
                this.readInProgress = true;
                return;
            }
            InternalThreadLocalMap threadLocals = InternalThreadLocalMap.get();
            Integer stackDepth = Integer.valueOf(threadLocals.localChannelReaderStackDepth());
            if (stackDepth.intValue() < 8) {
                threadLocals.setLocalChannelReaderStackDepth(stackDepth.intValue() + 1);
                while (true) {
                    try {
                        Object received = inboundBuffer2.poll();
                        if (received == null) {
                            pipeline.fireChannelReadComplete();
                            return;
                        }
                        pipeline.fireChannelRead(received);
                    } finally {
                        threadLocals.setLocalChannelReaderStackDepth(stackDepth.intValue());
                    }
                }
            } else {
                try {
                    eventLoop().execute(this.readTask);
                } catch (Throwable cause) {
                    logger.warn("Closing Local channels {}-{} because exception occurred!", this, this.peer, cause);
                    close();
                    this.peer.close();
                    PlatformDependent.throwException(cause);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doWrite(ChannelOutboundBuffer in) {
        if (this.state < 2) {
            throw new NotYetConnectedException();
        } else if (this.state <= 2) {
            LocalChannel peer2 = this.peer;
            this.writeInProgress = true;
            while (true) {
                try {
                    Object msg = in.current();
                    if (msg == null) {
                        this.writeInProgress = false;
                        finishPeerRead(peer2);
                        return;
                    } else if (peer2.state == 2) {
                        peer2.inboundBuffer.add(ReferenceCountUtil.retain(msg));
                        in.remove();
                    } else {
                        in.remove(DO_WRITE_CLOSED_CHANNEL_EXCEPTION);
                    }
                } catch (Throwable th) {
                    this.writeInProgress = false;
                    throw th;
                }
            }
        } else {
            throw DO_WRITE_CLOSED_CHANNEL_EXCEPTION;
        }
    }

    private void finishPeerRead(LocalChannel peer2) {
        if (peer2.eventLoop() != eventLoop() || peer2.writeInProgress) {
            runFinishPeerReadTask(peer2);
        } else {
            finishPeerRead0(peer2);
        }
    }

    private void runFinishPeerReadTask(final LocalChannel peer2) {
        Runnable finishPeerReadTask = new Runnable() {
            public void run() {
                LocalChannel.this.finishPeerRead0(peer2);
            }
        };
        try {
            if (peer2.writeInProgress) {
                peer2.finishReadFuture = peer2.eventLoop().submit(finishPeerReadTask);
            } else {
                peer2.eventLoop().execute(finishPeerReadTask);
            }
        } catch (Throwable cause) {
            logger.warn("Closing Local channels {}-{} because exception occurred!", this, peer2, cause);
            close();
            peer2.close();
            PlatformDependent.throwException(cause);
        }
    }

    private void releaseInboundBuffers() {
        if (eventLoop() == null || eventLoop().inEventLoop()) {
            this.readInProgress = false;
            Queue<Object> inboundBuffer2 = this.inboundBuffer;
            while (true) {
                Object poll = inboundBuffer2.poll();
                Object msg = poll;
                if (poll != null) {
                    ReferenceCountUtil.release(msg);
                } else {
                    return;
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    /* access modifiers changed from: private */
    public void finishPeerRead0(LocalChannel peer2) {
        Future<?> peerFinishReadFuture = peer2.finishReadFuture;
        if (peerFinishReadFuture != null) {
            if (!peerFinishReadFuture.isDone()) {
                runFinishPeerReadTask(peer2);
                return;
            }
            FINISH_READ_FUTURE_UPDATER.compareAndSet(peer2, peerFinishReadFuture, (Object) null);
        }
        ChannelPipeline peerPipeline = peer2.pipeline();
        if (peer2.readInProgress) {
            peer2.readInProgress = false;
            while (true) {
                Object received = peer2.inboundBuffer.poll();
                if (received == null) {
                    peerPipeline.fireChannelReadComplete();
                    return;
                }
                peerPipeline.fireChannelRead(received);
            }
        }
    }

    public class LocalUnsafe extends AbstractChannel.AbstractUnsafe {
        private LocalUnsafe() {
            super();
        }

        public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
            if (promise.setUncancellable() && ensureOpen(promise)) {
                if (LocalChannel.this.state == 2) {
                    Exception cause = new AlreadyConnectedException();
                    safeSetFailure(promise, cause);
                    LocalChannel.this.pipeline().fireExceptionCaught(cause);
                } else if (LocalChannel.this.connectPromise == null) {
                    ChannelPromise unused = LocalChannel.this.connectPromise = promise;
                    if (LocalChannel.this.state != 1 && localAddress == null) {
                        localAddress = new LocalAddress((Channel) LocalChannel.this);
                    }
                    if (localAddress != null) {
                        try {
                            LocalChannel.this.doBind(localAddress);
                        } catch (Throwable t) {
                            safeSetFailure(promise, t);
                            close(voidPromise());
                            return;
                        }
                    }
                    Channel boundChannel = LocalChannelRegistry.get(remoteAddress);
                    if (!(boundChannel instanceof LocalServerChannel)) {
                        safeSetFailure(promise, new ConnectException("connection refused: " + remoteAddress));
                        close(voidPromise());
                        return;
                    }
                    LocalChannel localChannel = LocalChannel.this;
                    LocalChannel unused2 = localChannel.peer = ((LocalServerChannel) boundChannel).serve(localChannel);
                } else {
                    throw new ConnectionPendingException();
                }
            }
        }
    }
}
