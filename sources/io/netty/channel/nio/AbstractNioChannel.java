package io.netty.channel.nio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ConnectTimeoutException;
import io.netty.channel.EventLoop;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class AbstractNioChannel extends AbstractChannel {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final ClosedChannelException DO_CLOSE_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractNioChannel.class, "doClose()"));
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) AbstractNioChannel.class);
    private final SelectableChannel ch;
    /* access modifiers changed from: private */
    public ChannelPromise connectPromise;
    /* access modifiers changed from: private */
    public ScheduledFuture<?> connectTimeoutFuture;
    private volatile boolean inputShutdown;
    protected final int readInterestOp;
    private volatile boolean readPending;
    /* access modifiers changed from: private */
    public SocketAddress requestedRemoteAddress;
    volatile SelectionKey selectionKey;

    public interface NioUnsafe extends Channel.Unsafe {
        SelectableChannel ch();

        void finishConnect();

        void forceFlush();

        void read();
    }

    /* access modifiers changed from: protected */
    public abstract boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2);

    /* access modifiers changed from: protected */
    public abstract void doFinishConnect();

    static {
        Class<AbstractNioChannel> cls = AbstractNioChannel.class;
    }

    protected AbstractNioChannel(Channel parent, SelectableChannel ch2, int readInterestOp2) {
        super(parent);
        this.ch = ch2;
        this.readInterestOp = readInterestOp2;
        try {
            ch2.configureBlocking(false);
        } catch (IOException e) {
            try {
                ch2.close();
            } catch (IOException e2) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Failed to close a partially initialized socket.", (Throwable) e2);
                }
            }
            throw new ChannelException("Failed to enter non-blocking mode.", e);
        }
    }

    public boolean isOpen() {
        return this.ch.isOpen();
    }

    public NioUnsafe unsafe() {
        return (NioUnsafe) super.unsafe();
    }

    /* access modifiers changed from: protected */
    public SelectableChannel javaChannel() {
        return this.ch;
    }

    public NioEventLoop eventLoop() {
        return (NioEventLoop) super.eventLoop();
    }

    /* access modifiers changed from: protected */
    public SelectionKey selectionKey() {
        if (this.selectionKey != null) {
            return this.selectionKey;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: protected */
    public boolean isReadPending() {
        return this.readPending;
    }

    /* access modifiers changed from: protected */
    public void setReadPending(boolean readPending2) {
        this.readPending = readPending2;
    }

    /* access modifiers changed from: protected */
    public boolean isInputShutdown() {
        return this.inputShutdown;
    }

    /* access modifiers changed from: package-private */
    public void setInputShutdown() {
        this.inputShutdown = true;
    }

    public abstract class AbstractNioUnsafe extends AbstractChannel.AbstractUnsafe implements NioUnsafe {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        static {
            Class<AbstractNioChannel> cls = AbstractNioChannel.class;
        }

        protected AbstractNioUnsafe() {
            super();
        }

        /* access modifiers changed from: protected */
        public final void removeReadOp() {
            SelectionKey key = AbstractNioChannel.this.selectionKey();
            if (key.isValid()) {
                int interestOps = key.interestOps();
                int i = AbstractNioChannel.this.readInterestOp;
                if ((interestOps & i) != 0) {
                    key.interestOps((~i) & interestOps);
                }
            }
        }

        public final SelectableChannel ch() {
            return AbstractNioChannel.this.javaChannel();
        }

        public final void connect(final SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
            if (promise.setUncancellable() && ensureOpen(promise)) {
                try {
                    if (AbstractNioChannel.this.connectPromise == null) {
                        boolean wasActive = AbstractNioChannel.this.isActive();
                        if (AbstractNioChannel.this.doConnect(remoteAddress, localAddress)) {
                            fulfillConnectPromise(promise, wasActive);
                        } else {
                            ChannelPromise unused = AbstractNioChannel.this.connectPromise = promise;
                            SocketAddress unused2 = AbstractNioChannel.this.requestedRemoteAddress = remoteAddress;
                            int connectTimeoutMillis = AbstractNioChannel.this.config().getConnectTimeoutMillis();
                            if (connectTimeoutMillis > 0) {
                                AbstractNioChannel abstractNioChannel = AbstractNioChannel.this;
                                ScheduledFuture unused3 = abstractNioChannel.connectTimeoutFuture = abstractNioChannel.eventLoop().schedule((Runnable) new Runnable() {
                                    public void run() {
                                        ChannelPromise connectPromise = AbstractNioChannel.this.connectPromise;
                                        ConnectTimeoutException cause = new ConnectTimeoutException("connection timed out: " + remoteAddress);
                                        if (connectPromise != null && connectPromise.tryFailure(cause)) {
                                            AbstractNioUnsafe abstractNioUnsafe = AbstractNioUnsafe.this;
                                            abstractNioUnsafe.close(abstractNioUnsafe.voidPromise());
                                        }
                                    }
                                }, (long) connectTimeoutMillis, TimeUnit.MILLISECONDS);
                            }
                            promise.addListener(new ChannelFutureListener() {
                                public void operationComplete(ChannelFuture future) {
                                    if (future.isCancelled()) {
                                        if (AbstractNioChannel.this.connectTimeoutFuture != null) {
                                            AbstractNioChannel.this.connectTimeoutFuture.cancel(false);
                                        }
                                        ChannelPromise unused = AbstractNioChannel.this.connectPromise = null;
                                        AbstractNioUnsafe abstractNioUnsafe = AbstractNioUnsafe.this;
                                        abstractNioUnsafe.close(abstractNioUnsafe.voidPromise());
                                    }
                                }
                            });
                        }
                        return;
                    }
                    throw new ConnectionPendingException();
                } catch (Throwable t) {
                    promise.tryFailure(annotateConnectException(t, remoteAddress));
                    closeIfClosed();
                }
            }
        }

        private void fulfillConnectPromise(ChannelPromise promise, boolean wasActive) {
            if (promise != null) {
                boolean active = AbstractNioChannel.this.isActive();
                boolean promiseSet = promise.trySuccess();
                if (!wasActive && active) {
                    AbstractNioChannel.this.pipeline().fireChannelActive();
                }
                if (!promiseSet) {
                    close(voidPromise());
                }
            }
        }

        private void fulfillConnectPromise(ChannelPromise promise, Throwable cause) {
            if (promise != null) {
                promise.tryFailure(cause);
                closeIfClosed();
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0053, code lost:
            if (io.netty.channel.nio.AbstractNioChannel.access$200(r5.this$0) == null) goto L_0x0033;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0056, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:6:0x0028, code lost:
            if (io.netty.channel.nio.AbstractNioChannel.access$200(r5.this$0) != null) goto L_0x002a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:7:0x002a, code lost:
            io.netty.channel.nio.AbstractNioChannel.access$200(r5.this$0).cancel(false);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0033, code lost:
            io.netty.channel.nio.AbstractNioChannel.access$002(r5.this$0, (io.netty.channel.ChannelPromise) null);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void finishConnect() {
            /*
                r5 = this;
                io.netty.channel.nio.AbstractNioChannel r0 = io.netty.channel.nio.AbstractNioChannel.this
                io.netty.channel.nio.NioEventLoop r0 = r0.eventLoop()
                boolean r0 = r0.inEventLoop()
                if (r0 == 0) goto L_0x006f
                r0 = 0
                r1 = 0
                io.netty.channel.nio.AbstractNioChannel r2 = io.netty.channel.nio.AbstractNioChannel.this     // Catch:{ all -> 0x0039 }
                boolean r2 = r2.isActive()     // Catch:{ all -> 0x0039 }
                io.netty.channel.nio.AbstractNioChannel r3 = io.netty.channel.nio.AbstractNioChannel.this     // Catch:{ all -> 0x0039 }
                r3.doFinishConnect()     // Catch:{ all -> 0x0039 }
                io.netty.channel.nio.AbstractNioChannel r3 = io.netty.channel.nio.AbstractNioChannel.this     // Catch:{ all -> 0x0039 }
                io.netty.channel.ChannelPromise r3 = r3.connectPromise     // Catch:{ all -> 0x0039 }
                r5.fulfillConnectPromise((io.netty.channel.ChannelPromise) r3, (boolean) r2)     // Catch:{ all -> 0x0039 }
                io.netty.channel.nio.AbstractNioChannel r2 = io.netty.channel.nio.AbstractNioChannel.this
                java.util.concurrent.ScheduledFuture r2 = r2.connectTimeoutFuture
                if (r2 == 0) goto L_0x0033
            L_0x002a:
                io.netty.channel.nio.AbstractNioChannel r2 = io.netty.channel.nio.AbstractNioChannel.this
                java.util.concurrent.ScheduledFuture r2 = r2.connectTimeoutFuture
                r2.cancel(r0)
            L_0x0033:
                io.netty.channel.nio.AbstractNioChannel r0 = io.netty.channel.nio.AbstractNioChannel.this
                io.netty.channel.ChannelPromise unused = r0.connectPromise = r1
                goto L_0x0056
            L_0x0039:
                r2 = move-exception
                io.netty.channel.nio.AbstractNioChannel r3 = io.netty.channel.nio.AbstractNioChannel.this     // Catch:{ all -> 0x0057 }
                io.netty.channel.ChannelPromise r3 = r3.connectPromise     // Catch:{ all -> 0x0057 }
                io.netty.channel.nio.AbstractNioChannel r4 = io.netty.channel.nio.AbstractNioChannel.this     // Catch:{ all -> 0x0057 }
                java.net.SocketAddress r4 = r4.requestedRemoteAddress     // Catch:{ all -> 0x0057 }
                java.lang.Throwable r4 = r5.annotateConnectException(r2, r4)     // Catch:{ all -> 0x0057 }
                r5.fulfillConnectPromise((io.netty.channel.ChannelPromise) r3, (java.lang.Throwable) r4)     // Catch:{ all -> 0x0057 }
                io.netty.channel.nio.AbstractNioChannel r2 = io.netty.channel.nio.AbstractNioChannel.this
                java.util.concurrent.ScheduledFuture r2 = r2.connectTimeoutFuture
                if (r2 == 0) goto L_0x0033
                goto L_0x002a
            L_0x0056:
                return
            L_0x0057:
                r2 = move-exception
                io.netty.channel.nio.AbstractNioChannel r3 = io.netty.channel.nio.AbstractNioChannel.this
                java.util.concurrent.ScheduledFuture r3 = r3.connectTimeoutFuture
                if (r3 == 0) goto L_0x0069
                io.netty.channel.nio.AbstractNioChannel r3 = io.netty.channel.nio.AbstractNioChannel.this
                java.util.concurrent.ScheduledFuture r3 = r3.connectTimeoutFuture
                r3.cancel(r0)
            L_0x0069:
                io.netty.channel.nio.AbstractNioChannel r0 = io.netty.channel.nio.AbstractNioChannel.this
                io.netty.channel.ChannelPromise unused = r0.connectPromise = r1
                throw r2
            L_0x006f:
                java.lang.AssertionError r0 = new java.lang.AssertionError
                r0.<init>()
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.nio.AbstractNioChannel.AbstractNioUnsafe.finishConnect():void");
        }

        /* access modifiers changed from: protected */
        public final void flush0() {
            if (!isFlushPending()) {
                super.flush0();
            }
        }

        public final void forceFlush() {
            super.flush0();
        }

        private boolean isFlushPending() {
            SelectionKey selectionKey = AbstractNioChannel.this.selectionKey();
            return selectionKey.isValid() && (selectionKey.interestOps() & 4) != 0;
        }
    }

    /* access modifiers changed from: protected */
    public boolean isCompatible(EventLoop loop) {
        return loop instanceof NioEventLoop;
    }

    /* access modifiers changed from: protected */
    public void doRegister() {
        boolean selected = false;
        while (true) {
            try {
                this.selectionKey = javaChannel().register(eventLoop().unwrappedSelector(), 0, this);
                return;
            } catch (CancelledKeyException e) {
                if (!selected) {
                    eventLoop().selectNow();
                    selected = true;
                } else {
                    throw e;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doDeregister() {
        eventLoop().cancel(selectionKey());
    }

    /* access modifiers changed from: protected */
    public void doBeginRead() {
        if (!this.inputShutdown) {
            SelectionKey selectionKey2 = this.selectionKey;
            if (selectionKey2.isValid()) {
                this.readPending = true;
                int interestOps = selectionKey2.interestOps();
                int i = this.readInterestOp;
                if ((interestOps & i) == 0) {
                    selectionKey2.interestOps(i | interestOps);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final ByteBuf newDirectBuffer(ByteBuf buf) {
        int readableBytes = buf.readableBytes();
        if (readableBytes == 0) {
            ReferenceCountUtil.safeRelease(buf);
            return Unpooled.EMPTY_BUFFER;
        }
        ByteBufAllocator alloc = alloc();
        if (alloc.isDirectBufferPooled()) {
            ByteBuf directBuf = alloc.directBuffer(readableBytes);
            directBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
            ReferenceCountUtil.safeRelease(buf);
            return directBuf;
        }
        ByteBuf directBuf2 = ByteBufUtil.threadLocalDirectBuffer();
        if (directBuf2 == null) {
            return buf;
        }
        directBuf2.writeBytes(buf, buf.readerIndex(), readableBytes);
        ReferenceCountUtil.safeRelease(buf);
        return directBuf2;
    }

    /* access modifiers changed from: protected */
    public final ByteBuf newDirectBuffer(ReferenceCounted holder, ByteBuf buf) {
        int readableBytes = buf.readableBytes();
        if (readableBytes == 0) {
            ReferenceCountUtil.safeRelease(holder);
            return Unpooled.EMPTY_BUFFER;
        }
        ByteBufAllocator alloc = alloc();
        if (alloc.isDirectBufferPooled()) {
            ByteBuf directBuf = alloc.directBuffer(readableBytes);
            directBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
            ReferenceCountUtil.safeRelease(holder);
            return directBuf;
        }
        ByteBuf directBuf2 = ByteBufUtil.threadLocalDirectBuffer();
        if (directBuf2 != null) {
            directBuf2.writeBytes(buf, buf.readerIndex(), readableBytes);
            ReferenceCountUtil.safeRelease(holder);
            return directBuf2;
        }
        if (holder != buf) {
            buf.retain();
            ReferenceCountUtil.safeRelease(holder);
        }
        return buf;
    }

    /* access modifiers changed from: protected */
    public void doClose() {
        ChannelPromise promise = this.connectPromise;
        if (promise != null) {
            promise.tryFailure(DO_CLOSE_CLOSED_CHANNEL_EXCEPTION);
            this.connectPromise = null;
        }
        ScheduledFuture<?> future = this.connectTimeoutFuture;
        if (future != null) {
            future.cancel(false);
            this.connectTimeoutFuture = null;
        }
    }
}
