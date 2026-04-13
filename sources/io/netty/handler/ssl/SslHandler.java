package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelPromiseNotifier;
import io.netty.channel.PendingWriteQueue;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ImmediateExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import meshsdk.ctrl.GroupCtrlAdapter;

public class SslHandler extends ByteToMessageDecoder implements ChannelOutboundHandler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final ClosedChannelException CHANNEL_CLOSED;
    /* access modifiers changed from: private */
    public static final SSLException HANDSHAKE_TIMED_OUT;
    private static final Pattern IGNORABLE_CLASS_IN_STACK = Pattern.compile("^.*(?:Socket|Datagram|Sctp|Udt)Channel.*$");
    private static final Pattern IGNORABLE_ERROR_MESSAGE = Pattern.compile("^.*(?:connection.*(?:reset|closed|abort|broken)|broken.*pipe).*$", 2);
    private static final SSLException SSLENGINE_CLOSED;
    /* access modifiers changed from: private */
    public static final InternalLogger logger;
    private volatile long closeNotifyFlushTimeoutMillis;
    /* access modifiers changed from: private */
    public volatile long closeNotifyReadTimeoutMillis;
    /* access modifiers changed from: private */
    public volatile ChannelHandlerContext ctx;
    private final Executor delegatedTaskExecutor;
    /* access modifiers changed from: private */
    public final SSLEngine engine;
    private final SslEngineType engineType;
    private boolean firedChannelRead;
    private boolean flushedBeforeHandshake;
    private Promise<Channel> handshakePromise;
    private boolean handshakeStarted;
    private volatile long handshakeTimeoutMillis;
    private final boolean jdkCompatibilityMode;
    private boolean needsFlush;
    /* access modifiers changed from: private */
    public boolean outboundClosed;
    private int packetLength;
    private PendingWriteQueue pendingUnencryptedWrites;
    private boolean readDuringHandshake;
    private boolean sentFirstMessage;
    /* access modifiers changed from: private */
    public final ByteBuffer[] singleBuffer;
    /* access modifiers changed from: private */
    public final LazyChannelPromise sslClosePromise;
    private final boolean startTls;

    static {
        Class cls = SslHandler.class;
        logger = InternalLoggerFactory.getInstance((Class<?>) cls);
        SSLENGINE_CLOSED = (SSLException) ThrowableUtil.unknownStackTrace(new SSLException("SSLEngine closed already"), cls, "wrap(...)");
        HANDSHAKE_TIMED_OUT = (SSLException) ThrowableUtil.unknownStackTrace(new SSLException("handshake timed out"), cls, "handshake(...)");
        CHANNEL_CLOSED = (ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), cls, "channelInactive(...)");
    }

    public enum SslEngineType {
        TCNATIVE(true, r1) {
            /* access modifiers changed from: package-private */
            public SSLEngineResult unwrap(SslHandler handler, ByteBuf in, int readerIndex, int len, ByteBuf out) {
                SSLEngineResult result;
                int nioBufferCount = in.nioBufferCount();
                int writerIndex = out.writerIndex();
                if (nioBufferCount > 1) {
                    ReferenceCountedOpenSslEngine opensslEngine = (ReferenceCountedOpenSslEngine) handler.engine;
                    try {
                        handler.singleBuffer[0] = SslHandler.toByteBuffer(out, writerIndex, out.writableBytes());
                        result = opensslEngine.unwrap(in.nioBuffers(readerIndex, len), handler.singleBuffer);
                    } finally {
                        handler.singleBuffer[0] = null;
                    }
                } else {
                    result = handler.engine.unwrap(SslHandler.toByteBuffer(in, readerIndex, len), SslHandler.toByteBuffer(out, writerIndex, out.writableBytes()));
                }
                out.writerIndex(result.bytesProduced() + writerIndex);
                return result;
            }

            /* access modifiers changed from: package-private */
            public int getPacketBufferSize(SslHandler handler) {
                return ((ReferenceCountedOpenSslEngine) handler.engine).maxEncryptedPacketLength0();
            }

            /* access modifiers changed from: package-private */
            public int calculateWrapBufferCapacity(SslHandler handler, int pendingBytes, int numComponents) {
                return ((ReferenceCountedOpenSslEngine) handler.engine).calculateMaxLengthForWrap(pendingBytes, numComponents);
            }

            /* access modifiers changed from: package-private */
            public int calculatePendingData(SslHandler handler, int guess) {
                int sslPending = ((ReferenceCountedOpenSslEngine) handler.engine).sslPending();
                return sslPending > 0 ? sslPending : guess;
            }

            /* access modifiers changed from: package-private */
            public boolean jdkCompatibilityMode(SSLEngine engine) {
                return ((ReferenceCountedOpenSslEngine) engine).jdkCompatibilityMode;
            }
        },
        CONSCRYPT(true, r1) {
            /* access modifiers changed from: package-private */
            public SSLEngineResult unwrap(SslHandler handler, ByteBuf in, int readerIndex, int len, ByteBuf out) {
                SSLEngineResult result;
                int nioBufferCount = in.nioBufferCount();
                int writerIndex = out.writerIndex();
                if (nioBufferCount > 1) {
                    try {
                        handler.singleBuffer[0] = SslHandler.toByteBuffer(out, writerIndex, out.writableBytes());
                        result = ((ConscryptAlpnSslEngine) handler.engine).unwrap(in.nioBuffers(readerIndex, len), handler.singleBuffer);
                    } finally {
                        handler.singleBuffer[0] = null;
                    }
                } else {
                    result = handler.engine.unwrap(SslHandler.toByteBuffer(in, readerIndex, len), SslHandler.toByteBuffer(out, writerIndex, out.writableBytes()));
                }
                out.writerIndex(result.bytesProduced() + writerIndex);
                return result;
            }

            /* access modifiers changed from: package-private */
            public int calculateWrapBufferCapacity(SslHandler handler, int pendingBytes, int numComponents) {
                return ((ConscryptAlpnSslEngine) handler.engine).calculateOutNetBufSize(pendingBytes, numComponents);
            }

            /* access modifiers changed from: package-private */
            public int calculatePendingData(SslHandler handler, int guess) {
                return guess;
            }

            /* access modifiers changed from: package-private */
            public boolean jdkCompatibilityMode(SSLEngine engine) {
                return true;
            }
        },
        JDK(false, ByteToMessageDecoder.MERGE_CUMULATOR) {
            /* access modifiers changed from: package-private */
            public SSLEngineResult unwrap(SslHandler handler, ByteBuf in, int readerIndex, int len, ByteBuf out) {
                int writerIndex = out.writerIndex();
                SSLEngineResult result = handler.engine.unwrap(SslHandler.toByteBuffer(in, readerIndex, len), SslHandler.toByteBuffer(out, writerIndex, out.writableBytes()));
                out.writerIndex(result.bytesProduced() + writerIndex);
                return result;
            }

            /* access modifiers changed from: package-private */
            public int calculateWrapBufferCapacity(SslHandler handler, int pendingBytes, int numComponents) {
                return handler.engine.getSession().getPacketBufferSize();
            }

            /* access modifiers changed from: package-private */
            public int calculatePendingData(SslHandler handler, int guess) {
                return guess;
            }

            /* access modifiers changed from: package-private */
            public boolean jdkCompatibilityMode(SSLEngine engine) {
                return true;
            }
        };
        
        final ByteToMessageDecoder.Cumulator cumulator;
        final boolean wantsDirectBuffer;

        /* access modifiers changed from: package-private */
        public abstract int calculatePendingData(SslHandler sslHandler, int i);

        /* access modifiers changed from: package-private */
        public abstract int calculateWrapBufferCapacity(SslHandler sslHandler, int i, int i2);

        /* access modifiers changed from: package-private */
        public abstract boolean jdkCompatibilityMode(SSLEngine sSLEngine);

        /* access modifiers changed from: package-private */
        public abstract SSLEngineResult unwrap(SslHandler sslHandler, ByteBuf byteBuf, int i, int i2, ByteBuf byteBuf2);

        static SslEngineType forEngine(SSLEngine engine) {
            if (engine instanceof ReferenceCountedOpenSslEngine) {
                return TCNATIVE;
            }
            return engine instanceof ConscryptAlpnSslEngine ? CONSCRYPT : JDK;
        }

        private SslEngineType(boolean wantsDirectBuffer2, ByteToMessageDecoder.Cumulator cumulator2) {
            this.wantsDirectBuffer = wantsDirectBuffer2;
            this.cumulator = cumulator2;
        }

        /* access modifiers changed from: package-private */
        public int getPacketBufferSize(SslHandler handler) {
            return handler.engine.getSession().getPacketBufferSize();
        }
    }

    public SslHandler(SSLEngine engine2) {
        this(engine2, false);
    }

    public SslHandler(SSLEngine engine2, boolean startTls2) {
        this(engine2, startTls2, ImmediateExecutor.INSTANCE);
    }

    @Deprecated
    public SslHandler(SSLEngine engine2, Executor delegatedTaskExecutor2) {
        this(engine2, false, delegatedTaskExecutor2);
    }

    @Deprecated
    public SslHandler(SSLEngine engine2, boolean startTls2, Executor delegatedTaskExecutor2) {
        this.singleBuffer = new ByteBuffer[1];
        this.handshakePromise = new LazyChannelPromise();
        this.sslClosePromise = new LazyChannelPromise();
        this.handshakeTimeoutMillis = 10000;
        this.closeNotifyFlushTimeoutMillis = GroupCtrlAdapter.RETRY_TIMEOUT;
        if (engine2 == null) {
            throw new NullPointerException("engine");
        } else if (delegatedTaskExecutor2 != null) {
            this.engine = engine2;
            SslEngineType forEngine = SslEngineType.forEngine(engine2);
            this.engineType = forEngine;
            this.delegatedTaskExecutor = delegatedTaskExecutor2;
            this.startTls = startTls2;
            this.jdkCompatibilityMode = forEngine.jdkCompatibilityMode(engine2);
            setCumulator(forEngine.cumulator);
        } else {
            throw new NullPointerException("delegatedTaskExecutor");
        }
    }

    public long getHandshakeTimeoutMillis() {
        return this.handshakeTimeoutMillis;
    }

    public void setHandshakeTimeout(long handshakeTimeout, TimeUnit unit) {
        if (unit != null) {
            setHandshakeTimeoutMillis(unit.toMillis(handshakeTimeout));
            return;
        }
        throw new NullPointerException("unit");
    }

    public void setHandshakeTimeoutMillis(long handshakeTimeoutMillis2) {
        if (handshakeTimeoutMillis2 >= 0) {
            this.handshakeTimeoutMillis = handshakeTimeoutMillis2;
            return;
        }
        throw new IllegalArgumentException("handshakeTimeoutMillis: " + handshakeTimeoutMillis2 + " (expected: >= 0)");
    }

    @Deprecated
    public long getCloseNotifyTimeoutMillis() {
        return getCloseNotifyFlushTimeoutMillis();
    }

    @Deprecated
    public void setCloseNotifyTimeout(long closeNotifyTimeout, TimeUnit unit) {
        setCloseNotifyFlushTimeout(closeNotifyTimeout, unit);
    }

    @Deprecated
    public void setCloseNotifyTimeoutMillis(long closeNotifyFlushTimeoutMillis2) {
        setCloseNotifyFlushTimeoutMillis(closeNotifyFlushTimeoutMillis2);
    }

    public final long getCloseNotifyFlushTimeoutMillis() {
        return this.closeNotifyFlushTimeoutMillis;
    }

    public final void setCloseNotifyFlushTimeout(long closeNotifyFlushTimeout, TimeUnit unit) {
        setCloseNotifyFlushTimeoutMillis(unit.toMillis(closeNotifyFlushTimeout));
    }

    public final void setCloseNotifyFlushTimeoutMillis(long closeNotifyFlushTimeoutMillis2) {
        if (closeNotifyFlushTimeoutMillis2 >= 0) {
            this.closeNotifyFlushTimeoutMillis = closeNotifyFlushTimeoutMillis2;
            return;
        }
        throw new IllegalArgumentException("closeNotifyFlushTimeoutMillis: " + closeNotifyFlushTimeoutMillis2 + " (expected: >= 0)");
    }

    public final long getCloseNotifyReadTimeoutMillis() {
        return this.closeNotifyReadTimeoutMillis;
    }

    public final void setCloseNotifyReadTimeout(long closeNotifyReadTimeout, TimeUnit unit) {
        setCloseNotifyReadTimeoutMillis(unit.toMillis(closeNotifyReadTimeout));
    }

    public final void setCloseNotifyReadTimeoutMillis(long closeNotifyReadTimeoutMillis2) {
        if (closeNotifyReadTimeoutMillis2 >= 0) {
            this.closeNotifyReadTimeoutMillis = closeNotifyReadTimeoutMillis2;
            return;
        }
        throw new IllegalArgumentException("closeNotifyReadTimeoutMillis: " + closeNotifyReadTimeoutMillis2 + " (expected: >= 0)");
    }

    public SSLEngine engine() {
        return this.engine;
    }

    public String applicationProtocol() {
        SSLEngine engine2 = engine();
        if (!(engine2 instanceof ApplicationProtocolAccessor)) {
            return null;
        }
        return ((ApplicationProtocolAccessor) engine2).getNegotiatedApplicationProtocol();
    }

    public Future<Channel> handshakeFuture() {
        return this.handshakePromise;
    }

    @Deprecated
    public ChannelFuture close() {
        return close(this.ctx.newPromise());
    }

    @Deprecated
    public ChannelFuture close(final ChannelPromise promise) {
        final ChannelHandlerContext ctx2 = this.ctx;
        ctx2.executor().execute(new Runnable() {
            public void run() {
                boolean unused = SslHandler.this.outboundClosed = true;
                SslHandler.this.engine.closeOutbound();
                try {
                    SslHandler.this.flush(ctx2, promise);
                } catch (Exception e) {
                    if (!promise.tryFailure(e)) {
                        SslHandler.logger.warn("{} flush() raised a masked exception.", ctx2.channel(), e);
                    }
                }
            }
        });
        return promise;
    }

    public Future<Channel> sslCloseFuture() {
        return this.sslClosePromise;
    }

    public void handlerRemoved0(ChannelHandlerContext ctx2) {
        if (!this.pendingUnencryptedWrites.isEmpty()) {
            this.pendingUnencryptedWrites.removeAndFailAll(new ChannelException("Pending write on removal of SslHandler"));
        }
        this.pendingUnencryptedWrites = null;
        SSLEngine sSLEngine = this.engine;
        if (sSLEngine instanceof ReferenceCounted) {
            ((ReferenceCounted) sSLEngine).release();
        }
    }

    public void bind(ChannelHandlerContext ctx2, SocketAddress localAddress, ChannelPromise promise) {
        ctx2.bind(localAddress, promise);
    }

    public void connect(ChannelHandlerContext ctx2, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
        ctx2.connect(remoteAddress, localAddress, promise);
    }

    public void deregister(ChannelHandlerContext ctx2, ChannelPromise promise) {
        ctx2.deregister(promise);
    }

    public void disconnect(ChannelHandlerContext ctx2, ChannelPromise promise) {
        closeOutboundAndChannel(ctx2, promise, true);
    }

    public void close(ChannelHandlerContext ctx2, ChannelPromise promise) {
        closeOutboundAndChannel(ctx2, promise, false);
    }

    public void read(ChannelHandlerContext ctx2) {
        if (!this.handshakePromise.isDone()) {
            this.readDuringHandshake = true;
        }
        ctx2.read();
    }

    private static IllegalStateException newPendingWritesNullException() {
        return new IllegalStateException("pendingUnencryptedWrites is null, handlerRemoved0 called?");
    }

    public void write(ChannelHandlerContext ctx2, Object msg, ChannelPromise promise) {
        if (!(msg instanceof ByteBuf)) {
            UnsupportedMessageTypeException exception = new UnsupportedMessageTypeException(msg, (Class<?>[]) new Class[]{ByteBuf.class});
            ReferenceCountUtil.safeRelease(msg);
            promise.setFailure(exception);
            return;
        }
        PendingWriteQueue pendingWriteQueue = this.pendingUnencryptedWrites;
        if (pendingWriteQueue == null) {
            ReferenceCountUtil.safeRelease(msg);
            promise.setFailure(newPendingWritesNullException());
            return;
        }
        pendingWriteQueue.add(msg, promise);
    }

    public void flush(ChannelHandlerContext ctx2) {
        if (!this.startTls || this.sentFirstMessage) {
            try {
                wrapAndFlush(ctx2);
            } catch (Throwable cause) {
                setHandshakeFailure(ctx2, cause);
                PlatformDependent.throwException(cause);
            }
        } else {
            this.sentFirstMessage = true;
            this.pendingUnencryptedWrites.removeAndWriteAll();
            forceFlush(ctx2);
        }
    }

    private void wrapAndFlush(ChannelHandlerContext ctx2) {
        if (this.pendingUnencryptedWrites.isEmpty()) {
            this.pendingUnencryptedWrites.add(Unpooled.EMPTY_BUFFER, ctx2.newPromise());
        }
        if (!this.handshakePromise.isDone()) {
            this.flushedBeforeHandshake = true;
        }
        try {
            wrap(ctx2, false);
        } finally {
            forceFlush(ctx2);
        }
    }

    private void wrap(ChannelHandlerContext ctx2, boolean inUnwrap) {
        ByteBufAllocator alloc = ctx2.alloc();
        ByteBuf out = null;
        ChannelPromise promise = null;
        while (true) {
            try {
                if (!ctx2.isRemoved()) {
                    Object msg = this.pendingUnencryptedWrites.current();
                    if (msg == null) {
                        ChannelHandlerContext channelHandlerContext = ctx2;
                    } else {
                        ByteBuf buf = (ByteBuf) msg;
                        if (out == null) {
                            try {
                                out = allocateOutNetBuf(ctx2, buf.readableBytes(), buf.nioBufferCount());
                            } catch (Throwable th) {
                                th = th;
                                finishWrap(ctx2, out, promise, inUnwrap, false);
                                throw th;
                            }
                        } else {
                            ChannelHandlerContext channelHandlerContext2 = ctx2;
                        }
                        SSLEngineResult result = wrap(alloc, this.engine, buf, out);
                        if (result.getStatus() == SSLEngineResult.Status.CLOSED) {
                            this.pendingUnencryptedWrites.removeAndFailAll(SSLENGINE_CLOSED);
                            finishWrap(ctx2, out, promise, inUnwrap, false);
                            return;
                        }
                        if (!buf.isReadable()) {
                            promise = this.pendingUnencryptedWrites.remove();
                        } else {
                            promise = null;
                        }
                        switch (AnonymousClass9.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[result.getHandshakeStatus().ordinal()]) {
                            case 1:
                                runDelegatedTasks();
                                continue;
                            case 2:
                                setHandshakeSuccess();
                                break;
                            case 3:
                                break;
                            case 4:
                                break;
                            case 5:
                                finishWrap(ctx2, out, promise, inUnwrap, true);
                                return;
                            default:
                                throw new IllegalStateException("Unknown handshake status: " + result.getHandshakeStatus());
                        }
                        setHandshakeSuccessIfStillHandshaking();
                        finishWrap(ctx2, out, promise, inUnwrap, false);
                        promise = null;
                        out = null;
                        continue;
                    }
                } else {
                    ChannelHandlerContext channelHandlerContext3 = ctx2;
                }
            } catch (Throwable th2) {
                th = th2;
                ChannelHandlerContext channelHandlerContext4 = ctx2;
                finishWrap(ctx2, out, promise, inUnwrap, false);
                throw th;
            }
        }
        finishWrap(ctx2, out, promise, inUnwrap, false);
    }

    private void finishWrap(ChannelHandlerContext ctx2, ByteBuf out, ChannelPromise promise, boolean inUnwrap, boolean needUnwrap) {
        if (out == null) {
            out = Unpooled.EMPTY_BUFFER;
        } else if (!out.isReadable()) {
            out.release();
            out = Unpooled.EMPTY_BUFFER;
        }
        if (promise != null) {
            ctx2.write(out, promise);
        } else {
            ctx2.write(out);
        }
        if (inUnwrap) {
            this.needsFlush = true;
        }
        if (needUnwrap) {
            readIfNeeded(ctx2);
        }
    }

    private boolean wrapNonAppData(ChannelHandlerContext ctx2, boolean inUnwrap) {
        ByteBuf out = null;
        ByteBufAllocator alloc = ctx2.alloc();
        while (true) {
            try {
                if (!ctx2.isRemoved()) {
                    if (out == null) {
                        out = allocateOutNetBuf(ctx2, 2048, 1);
                    }
                    SSLEngineResult result = wrap(alloc, this.engine, Unpooled.EMPTY_BUFFER, out);
                    if (result.bytesProduced() > 0) {
                        ctx2.write(out);
                        if (inUnwrap) {
                            this.needsFlush = true;
                        }
                        out = null;
                    }
                    switch (AnonymousClass9.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[result.getHandshakeStatus().ordinal()]) {
                        case 1:
                            runDelegatedTasks();
                            break;
                        case 2:
                            setHandshakeSuccess();
                            if (out != null) {
                                out.release();
                            }
                            return false;
                        case 3:
                            setHandshakeSuccessIfStillHandshaking();
                            if (!inUnwrap) {
                                unwrapNonAppData(ctx2);
                            }
                            if (out != null) {
                                out.release();
                            }
                            return true;
                        case 4:
                            break;
                        case 5:
                            if (!inUnwrap) {
                                unwrapNonAppData(ctx2);
                                break;
                            } else {
                                if (out != null) {
                                    out.release();
                                }
                                return false;
                            }
                        default:
                            throw new IllegalStateException("Unknown handshake status: " + result.getHandshakeStatus());
                    }
                    if (result.bytesProduced() != 0) {
                        if (result.bytesConsumed() == 0 && result.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
                        }
                    }
                }
            } finally {
                if (out != null) {
                    out.release();
                }
            }
        }
        return false;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0082 A[SYNTHETIC, Splitter:B:19:0x0082] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0097 A[FINALLY_INSNS] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0077 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private javax.net.ssl.SSLEngineResult wrap(io.netty.buffer.ByteBufAllocator r11, javax.net.ssl.SSLEngine r12, io.netty.buffer.ByteBuf r13, io.netty.buffer.ByteBuf r14) {
        /*
            r10 = this;
            r0 = 0
            r1 = 0
            r2 = 0
            int r3 = r13.readerIndex()     // Catch:{ all -> 0x0090 }
            int r4 = r13.readableBytes()     // Catch:{ all -> 0x0090 }
            boolean r5 = r13.isDirect()     // Catch:{ all -> 0x0090 }
            if (r5 != 0) goto L_0x002d
            io.netty.handler.ssl.SslHandler$SslEngineType r5 = r10.engineType     // Catch:{ all -> 0x0090 }
            boolean r5 = r5.wantsDirectBuffer     // Catch:{ all -> 0x0090 }
            if (r5 != 0) goto L_0x0018
            goto L_0x002d
        L_0x0018:
            io.netty.buffer.ByteBuf r5 = r11.directBuffer(r4)     // Catch:{ all -> 0x0090 }
            r0 = r5
            r0.writeBytes((io.netty.buffer.ByteBuf) r13, (int) r3, (int) r4)     // Catch:{ all -> 0x0090 }
            java.nio.ByteBuffer[] r5 = r10.singleBuffer     // Catch:{ all -> 0x0090 }
            int r6 = r0.readerIndex()     // Catch:{ all -> 0x0090 }
            java.nio.ByteBuffer r6 = r0.internalNioBuffer(r6, r4)     // Catch:{ all -> 0x0090 }
            r5[r2] = r6     // Catch:{ all -> 0x0090 }
            goto L_0x0045
        L_0x002d:
            boolean r5 = r13 instanceof io.netty.buffer.CompositeByteBuf     // Catch:{ all -> 0x0090 }
            if (r5 != 0) goto L_0x0041
            int r5 = r13.nioBufferCount()     // Catch:{ all -> 0x0090 }
            r6 = 1
            if (r5 != r6) goto L_0x0041
            java.nio.ByteBuffer[] r5 = r10.singleBuffer     // Catch:{ all -> 0x0090 }
            java.nio.ByteBuffer r6 = r13.internalNioBuffer(r3, r4)     // Catch:{ all -> 0x0090 }
            r5[r2] = r6     // Catch:{ all -> 0x0090 }
            goto L_0x0045
        L_0x0041:
            java.nio.ByteBuffer[] r5 = r13.nioBuffers()     // Catch:{ all -> 0x0090 }
        L_0x0045:
            int r6 = r14.writerIndex()     // Catch:{ all -> 0x0090 }
            int r7 = r14.writableBytes()     // Catch:{ all -> 0x0090 }
            java.nio.ByteBuffer r6 = r14.nioBuffer(r6, r7)     // Catch:{ all -> 0x0090 }
            javax.net.ssl.SSLEngineResult r7 = r12.wrap(r5, r6)     // Catch:{ all -> 0x0090 }
            int r8 = r7.bytesConsumed()     // Catch:{ all -> 0x0090 }
            r13.skipBytes(r8)     // Catch:{ all -> 0x0090 }
            int r8 = r14.writerIndex()     // Catch:{ all -> 0x0090 }
            int r9 = r7.bytesProduced()     // Catch:{ all -> 0x0090 }
            int r8 = r8 + r9
            r14.writerIndex(r8)     // Catch:{ all -> 0x0090 }
            int[] r8 = io.netty.handler.ssl.SslHandler.AnonymousClass9.$SwitchMap$javax$net$ssl$SSLEngineResult$Status     // Catch:{ all -> 0x0090 }
            javax.net.ssl.SSLEngineResult$Status r9 = r7.getStatus()     // Catch:{ all -> 0x0090 }
            int r9 = r9.ordinal()     // Catch:{ all -> 0x0090 }
            r8 = r8[r9]     // Catch:{ all -> 0x0090 }
            switch(r8) {
                case 1: goto L_0x0082;
                default: goto L_0x0077;
            }
        L_0x0077:
            java.nio.ByteBuffer[] r8 = r10.singleBuffer
            r8[r2] = r1
            if (r0 == 0) goto L_0x008f
            r0.release()
            goto L_0x008f
        L_0x0082:
            javax.net.ssl.SSLSession r8 = r12.getSession()     // Catch:{ all -> 0x0090 }
            int r8 = r8.getPacketBufferSize()     // Catch:{ all -> 0x0090 }
            r14.ensureWritable(r8)     // Catch:{ all -> 0x0090 }
            goto L_0x0045
        L_0x008f:
            return r7
        L_0x0090:
            r3 = move-exception
            java.nio.ByteBuffer[] r4 = r10.singleBuffer
            r4[r2] = r1
            if (r0 == 0) goto L_0x009a
            r0.release()
        L_0x009a:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.SslHandler.wrap(io.netty.buffer.ByteBufAllocator, javax.net.ssl.SSLEngine, io.netty.buffer.ByteBuf, io.netty.buffer.ByteBuf):javax.net.ssl.SSLEngineResult");
    }

    /* renamed from: io.netty.handler.ssl.SslHandler$9  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass9 {
        static final /* synthetic */ int[] $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus;
        static final /* synthetic */ int[] $SwitchMap$javax$net$ssl$SSLEngineResult$Status;

        static {
            int[] iArr = new int[SSLEngineResult.Status.values().length];
            $SwitchMap$javax$net$ssl$SSLEngineResult$Status = iArr;
            try {
                iArr[SSLEngineResult.Status.BUFFER_OVERFLOW.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$Status[SSLEngineResult.Status.CLOSED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            int[] iArr2 = new int[SSLEngineResult.HandshakeStatus.values().length];
            $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus = iArr2;
            try {
                iArr2[SSLEngineResult.HandshakeStatus.NEED_TASK.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.FINISHED.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NEED_WRAP.ordinal()] = 4;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NEED_UNWRAP.ordinal()] = 5;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public void channelInactive(ChannelHandlerContext ctx2) {
        ClosedChannelException closedChannelException = CHANNEL_CLOSED;
        setHandshakeFailure(ctx2, closedChannelException, !this.outboundClosed, this.handshakeStarted);
        notifyClosePromise(closedChannelException);
        super.channelInactive(ctx2);
    }

    public void exceptionCaught(ChannelHandlerContext ctx2, Throwable cause) {
        if (ignoreException(cause)) {
            InternalLogger internalLogger = logger;
            if (internalLogger.isDebugEnabled()) {
                internalLogger.debug("{} Swallowing a harmless 'connection reset by peer / broken pipe' error that occurred while writing close_notify in response to the peer's close_notify", ctx2.channel(), cause);
            }
            if (ctx2.channel().isActive()) {
                ctx2.close();
                return;
            }
            return;
        }
        ctx2.fireExceptionCaught(cause);
    }

    private boolean ignoreException(Throwable t) {
        if (!(t instanceof SSLException) && (t instanceof IOException) && this.sslClosePromise.isDone()) {
            String message = t.getMessage();
            if (message != null && IGNORABLE_ERROR_MESSAGE.matcher(message).matches()) {
                return true;
            }
            for (StackTraceElement element : t.getStackTrace()) {
                String classname = element.getClassName();
                String methodname = element.getMethodName();
                if (!classname.startsWith("io.netty.") && "read".equals(methodname)) {
                    if (IGNORABLE_CLASS_IN_STACK.matcher(classname).matches()) {
                        return true;
                    }
                    try {
                        Class<?> clazz = PlatformDependent.getClassLoader(getClass()).loadClass(classname);
                        if (!SocketChannel.class.isAssignableFrom(clazz)) {
                            if (!DatagramChannel.class.isAssignableFrom(clazz)) {
                                if (PlatformDependent.javaVersion() >= 7 && "com.sun.nio.sctp.SctpChannel".equals(clazz.getSuperclass().getName())) {
                                    return true;
                                }
                            }
                        }
                        return true;
                    } catch (Throwable cause) {
                        logger.debug("Unexpected exception while loading class {} classname {}", getClass(), classname, cause);
                    }
                }
            }
        }
        return false;
    }

    public static boolean isEncrypted(ByteBuf buffer) {
        if (buffer.readableBytes() >= 5) {
            return SslUtils.getEncryptedPacketLength(buffer, buffer.readerIndex()) != -2;
        }
        throw new IllegalArgumentException("buffer must have at least 5 readable bytes");
    }

    private void decodeJdkCompatible(ChannelHandlerContext ctx2, ByteBuf in) {
        int packetLength2 = this.packetLength;
        if (packetLength2 <= 0) {
            int readableBytes = in.readableBytes();
            if (readableBytes >= 5) {
                packetLength2 = SslUtils.getEncryptedPacketLength(in, in.readerIndex());
                if (packetLength2 == -2) {
                    NotSslRecordException e = new NotSslRecordException("not an SSL/TLS record: " + ByteBufUtil.hexDump(in));
                    in.skipBytes(in.readableBytes());
                    setHandshakeFailure(ctx2, e);
                    throw e;
                } else if (packetLength2 <= 0) {
                    throw new AssertionError();
                } else if (packetLength2 > readableBytes) {
                    this.packetLength = packetLength2;
                    return;
                }
            } else {
                return;
            }
        } else if (in.readableBytes() < packetLength2) {
            return;
        }
        this.packetLength = 0;
        try {
            int bytesConsumed = unwrap(ctx2, in, in.readerIndex(), packetLength2);
            if (bytesConsumed != packetLength2) {
                if (!this.engine.isInboundDone()) {
                    throw new AssertionError("we feed the SSLEngine a packets worth of data: " + packetLength2 + " but it only consumed: " + bytesConsumed);
                }
            }
            in.skipBytes(bytesConsumed);
        } catch (Throwable cause) {
            handleUnwrapThrowable(ctx2, cause);
        }
    }

    private void decodeNonJdkCompatible(ChannelHandlerContext ctx2, ByteBuf in) {
        try {
            in.skipBytes(unwrap(ctx2, in, in.readerIndex(), in.readableBytes()));
        } catch (Throwable cause) {
            handleUnwrapThrowable(ctx2, cause);
        }
    }

    private void handleUnwrapThrowable(ChannelHandlerContext ctx2, Throwable cause) {
        try {
            if (this.handshakePromise.tryFailure(cause)) {
                ctx2.fireUserEventTriggered(new SslHandshakeCompletionEvent(cause));
            }
            wrapAndFlush(ctx2);
        } catch (SSLException ex) {
            logger.debug("SSLException during trying to call SSLEngine.wrap(...) because of an previous SSLException, ignoring...", (Throwable) ex);
        } catch (Throwable th) {
            setHandshakeFailure(ctx2, cause, true, false);
            throw th;
        }
        setHandshakeFailure(ctx2, cause, true, false);
        PlatformDependent.throwException(cause);
    }

    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext ctx2, ByteBuf in, List<Object> list) {
        if (this.jdkCompatibilityMode) {
            decodeJdkCompatible(ctx2, in);
        } else {
            decodeNonJdkCompatible(ctx2, in);
        }
    }

    public void channelReadComplete(ChannelHandlerContext ctx2) {
        discardSomeReadBytes();
        flushIfNeeded(ctx2);
        readIfNeeded(ctx2);
        this.firedChannelRead = false;
        ctx2.fireChannelReadComplete();
    }

    private void readIfNeeded(ChannelHandlerContext ctx2) {
        if (ctx2.channel().config().isAutoRead()) {
            return;
        }
        if (!this.firedChannelRead || !this.handshakePromise.isDone()) {
            ctx2.read();
        }
    }

    private void flushIfNeeded(ChannelHandlerContext ctx2) {
        if (this.needsFlush) {
            forceFlush(ctx2);
        }
    }

    private void unwrapNonAppData(ChannelHandlerContext ctx2) {
        unwrap(ctx2, Unpooled.EMPTY_BUFFER, 0, 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0126, code lost:
        r12 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x012a, code lost:
        if (r1 == javax.net.ssl.SSLEngineResult.Status.BUFFER_UNDERFLOW) goto L_0x0135;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x012c, code lost:
        if (r4 != 0) goto L_0x0131;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x012e, code lost:
        if (r3 != 0) goto L_0x0131;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0131, code lost:
        r10 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0137, code lost:
        if (r2 != javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_UNWRAP) goto L_0x013c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0139, code lost:
        readIfNeeded(r20);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x013c, code lost:
        r10 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x013f, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0140, code lost:
        r10 = r16;
     */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0194  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int unwrap(io.netty.channel.ChannelHandlerContext r20, io.netty.buffer.ByteBuf r21, int r22, int r23) {
        /*
            r19 = this;
            r7 = r19
            r8 = r20
            r9 = r23
            r0 = 0
            r1 = 0
            r2 = -1
            r3 = r23
            io.netty.buffer.ByteBuf r4 = r7.allocate(r8, r3)
            r10 = r22
            r12 = r0
            r13 = r1
            r14 = r2
            r11 = r3
            r15 = r4
        L_0x0016:
            r6 = 1
            boolean r0 = r20.isRemoved()     // Catch:{ all -> 0x018f }
            if (r0 != 0) goto L_0x0169
            io.netty.handler.ssl.SslHandler$SslEngineType r1 = r7.engineType     // Catch:{ all -> 0x018f }
            r2 = r19
            r3 = r21
            r4 = r10
            r5 = r11
            r22 = r12
            r12 = r6
            r6 = r15
            javax.net.ssl.SSLEngineResult r0 = r1.unwrap(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0165 }
            javax.net.ssl.SSLEngineResult$Status r1 = r0.getStatus()     // Catch:{ all -> 0x0165 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r2 = r0.getHandshakeStatus()     // Catch:{ all -> 0x0165 }
            int r3 = r0.bytesProduced()     // Catch:{ all -> 0x0165 }
            int r4 = r0.bytesConsumed()     // Catch:{ all -> 0x0165 }
            int r10 = r10 + r4
            int r11 = r11 - r4
            int[] r5 = io.netty.handler.ssl.SslHandler.AnonymousClass9.$SwitchMap$javax$net$ssl$SSLEngineResult$Status     // Catch:{ all -> 0x015f }
            int r6 = r1.ordinal()     // Catch:{ all -> 0x015f }
            r5 = r5[r6]     // Catch:{ all -> 0x015f }
            switch(r5) {
                case 1: goto L_0x005c;
                case 2: goto L_0x0052;
                default: goto L_0x004a;
            }     // Catch:{ all -> 0x015f }
        L_0x004a:
            r17 = r0
            r16 = r10
            r0 = -1
            r14 = r0
            goto L_0x00e3
        L_0x0052:
            r5 = 1
            r6 = -1
            r17 = r0
            r13 = r5
            r14 = r6
            r16 = r10
            goto L_0x00e3
        L_0x005c:
            int r5 = r15.readableBytes()     // Catch:{ all -> 0x015f }
            r6 = r14
            r14 = r5
            javax.net.ssl.SSLEngine r12 = r7.engine     // Catch:{ all -> 0x015f }
            javax.net.ssl.SSLSession r12 = r12.getSession()     // Catch:{ all -> 0x015f }
            int r12 = r12.getApplicationBufferSize()     // Catch:{ all -> 0x015f }
            int r12 = r12 - r5
            if (r5 <= 0) goto L_0x0086
            r16 = r10
            r10 = 1
            r7.firedChannelRead = r10     // Catch:{ all -> 0x0159 }
            r8.fireChannelRead(r15)     // Catch:{ all -> 0x0159 }
            r15 = 0
            if (r12 > 0) goto L_0x008d
            javax.net.ssl.SSLEngine r10 = r7.engine     // Catch:{ all -> 0x0159 }
            javax.net.ssl.SSLSession r10 = r10.getSession()     // Catch:{ all -> 0x0159 }
            int r10 = r10.getApplicationBufferSize()     // Catch:{ all -> 0x0159 }
            r12 = r10
            goto L_0x008d
        L_0x0086:
            r16 = r10
            r15.release()     // Catch:{ all -> 0x0159 }
            r10 = 0
            r15 = r10
        L_0x008d:
            if (r5 != 0) goto L_0x00ce
            if (r6 == 0) goto L_0x0096
            r17 = r0
            r18 = r5
            goto L_0x00d2
        L_0x0096:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0159 }
            r17 = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0159 }
            r0.<init>()     // Catch:{ all -> 0x0159 }
            r18 = r5
            java.lang.String r5 = "Two consecutive overflows but no content was consumed. "
            r0.append(r5)     // Catch:{ all -> 0x0159 }
            java.lang.Class<javax.net.ssl.SSLSession> r5 = javax.net.ssl.SSLSession.class
            java.lang.String r5 = r5.getSimpleName()     // Catch:{ all -> 0x0159 }
            r0.append(r5)     // Catch:{ all -> 0x0159 }
            java.lang.String r5 = " getApplicationBufferSize: "
            r0.append(r5)     // Catch:{ all -> 0x0159 }
            javax.net.ssl.SSLEngine r5 = r7.engine     // Catch:{ all -> 0x0159 }
            javax.net.ssl.SSLSession r5 = r5.getSession()     // Catch:{ all -> 0x0159 }
            int r5 = r5.getApplicationBufferSize()     // Catch:{ all -> 0x0159 }
            r0.append(r5)     // Catch:{ all -> 0x0159 }
            java.lang.String r5 = " maybe too small."
            r0.append(r5)     // Catch:{ all -> 0x0159 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0159 }
            r10.<init>(r0)     // Catch:{ all -> 0x0159 }
            throw r10     // Catch:{ all -> 0x0159 }
        L_0x00ce:
            r17 = r0
            r18 = r5
        L_0x00d2:
            io.netty.handler.ssl.SslHandler$SslEngineType r0 = r7.engineType     // Catch:{ all -> 0x0159 }
            int r0 = r0.calculatePendingData(r7, r12)     // Catch:{ all -> 0x0159 }
            io.netty.buffer.ByteBuf r0 = r7.allocate(r8, r0)     // Catch:{ all -> 0x0159 }
            r15 = r0
            r12 = r22
            r10 = r16
            goto L_0x0016
        L_0x00e3:
            int[] r0 = io.netty.handler.ssl.SslHandler.AnonymousClass9.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus     // Catch:{ all -> 0x0159 }
            int r5 = r2.ordinal()     // Catch:{ all -> 0x0159 }
            r0 = r0[r5]     // Catch:{ all -> 0x0159 }
            switch(r0) {
                case 1: goto L_0x0122;
                case 2: goto L_0x011c;
                case 3: goto L_0x0101;
                case 4: goto L_0x00f2;
                case 5: goto L_0x00f1;
                default: goto L_0x00ee;
            }     // Catch:{ all -> 0x0159 }
        L_0x00ee:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0159 }
            goto L_0x0143
        L_0x00f1:
            goto L_0x0126
        L_0x00f2:
            r5 = 1
            boolean r0 = r7.wrapNonAppData(r8, r5)     // Catch:{ all -> 0x0159 }
            if (r0 == 0) goto L_0x0126
            if (r11 != 0) goto L_0x0126
            r12 = r22
            r10 = r16
            goto L_0x016b
        L_0x0101:
            boolean r0 = r19.setHandshakeSuccessIfStillHandshaking()     // Catch:{ all -> 0x0159 }
            if (r0 == 0) goto L_0x010c
            r12 = 1
            r10 = r16
            goto L_0x0016
        L_0x010c:
            boolean r0 = r7.flushedBeforeHandshake     // Catch:{ all -> 0x0159 }
            if (r0 == 0) goto L_0x0115
            r0 = 0
            r7.flushedBeforeHandshake = r0     // Catch:{ all -> 0x0159 }
            r12 = 1
            goto L_0x0117
        L_0x0115:
            r12 = r22
        L_0x0117:
            if (r11 != 0) goto L_0x0128
            r10 = r16
            goto L_0x016b
        L_0x011c:
            r19.setHandshakeSuccess()     // Catch:{ all -> 0x0159 }
            r0 = 1
            r12 = r0
            goto L_0x0128
        L_0x0122:
            r19.runDelegatedTasks()     // Catch:{ all -> 0x0159 }
        L_0x0126:
            r12 = r22
        L_0x0128:
            javax.net.ssl.SSLEngineResult$Status r0 = javax.net.ssl.SSLEngineResult.Status.BUFFER_UNDERFLOW     // Catch:{ all -> 0x013f }
            if (r1 == r0) goto L_0x0135
            if (r4 != 0) goto L_0x0131
            if (r3 != 0) goto L_0x0131
            goto L_0x0135
        L_0x0131:
            r10 = r16
            goto L_0x0016
        L_0x0135:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r0 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_UNWRAP     // Catch:{ all -> 0x013f }
            if (r2 != r0) goto L_0x013c
            r19.readIfNeeded(r20)     // Catch:{ all -> 0x013f }
        L_0x013c:
            r10 = r16
            goto L_0x016b
        L_0x013f:
            r0 = move-exception
            r10 = r16
            goto L_0x0192
        L_0x0143:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0159 }
            r5.<init>()     // Catch:{ all -> 0x0159 }
            java.lang.String r6 = "unknown handshake status: "
            r5.append(r6)     // Catch:{ all -> 0x0159 }
            r5.append(r2)     // Catch:{ all -> 0x0159 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0159 }
            r0.<init>(r5)     // Catch:{ all -> 0x0159 }
            throw r0     // Catch:{ all -> 0x0159 }
        L_0x0159:
            r0 = move-exception
            r12 = r22
            r10 = r16
            goto L_0x0192
        L_0x015f:
            r0 = move-exception
            r16 = r10
            r12 = r22
            goto L_0x0192
        L_0x0165:
            r0 = move-exception
            r12 = r22
            goto L_0x0192
        L_0x0169:
            r22 = r12
        L_0x016b:
            if (r12 == 0) goto L_0x0174
            r1 = 1
            r7.wrap(r8, r1)     // Catch:{ all -> 0x0172 }
            goto L_0x0174
        L_0x0172:
            r0 = move-exception
            goto L_0x0192
        L_0x0174:
            if (r13 == 0) goto L_0x017a
            r0 = 0
            r7.notifyClosePromise(r0)     // Catch:{ all -> 0x0172 }
        L_0x017a:
            if (r15 == 0) goto L_0x018c
            boolean r0 = r15.isReadable()
            if (r0 == 0) goto L_0x0189
            r1 = 1
            r7.firedChannelRead = r1
            r8.fireChannelRead(r15)
            goto L_0x018c
        L_0x0189:
            r15.release()
        L_0x018c:
            int r0 = r9 - r11
            return r0
        L_0x018f:
            r0 = move-exception
            r22 = r12
        L_0x0192:
            if (r15 == 0) goto L_0x01a4
            boolean r1 = r15.isReadable()
            if (r1 == 0) goto L_0x01a1
            r1 = 1
            r7.firedChannelRead = r1
            r8.fireChannelRead(r15)
            goto L_0x01a4
        L_0x01a1:
            r15.release()
        L_0x01a4:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.SslHandler.unwrap(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, int, int):int");
    }

    /* access modifiers changed from: private */
    public static ByteBuffer toByteBuffer(ByteBuf out, int index, int len) {
        if (out.nioBufferCount() == 1) {
            return out.internalNioBuffer(index, len);
        }
        return out.nioBuffer(index, len);
    }

    private void runDelegatedTasks() {
        if (this.delegatedTaskExecutor == ImmediateExecutor.INSTANCE) {
            while (true) {
                Runnable task = this.engine.getDelegatedTask();
                if (task != null) {
                    task.run();
                } else {
                    return;
                }
            }
        } else {
            final List<Runnable> tasks = new ArrayList<>(2);
            while (true) {
                Runnable task2 = this.engine.getDelegatedTask();
                if (task2 == null) {
                    break;
                }
                tasks.add(task2);
            }
            if (!tasks.isEmpty()) {
                final CountDownLatch latch = new CountDownLatch(1);
                this.delegatedTaskExecutor.execute(new Runnable() {
                    public void run() {
                        try {
                            for (Runnable task : tasks) {
                                task.run();
                            }
                        } catch (Exception e) {
                            SslHandler.this.ctx.fireExceptionCaught(e);
                        } catch (Throwable th) {
                            latch.countDown();
                            throw th;
                        }
                        latch.countDown();
                    }
                });
                boolean interrupted = false;
                while (latch.getCount() != 0) {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        interrupted = true;
                    }
                }
                if (interrupted) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private boolean setHandshakeSuccessIfStillHandshaking() {
        if (this.handshakePromise.isDone()) {
            return false;
        }
        setHandshakeSuccess();
        return true;
    }

    private void setHandshakeSuccess() {
        this.handshakePromise.trySuccess(this.ctx.channel());
        InternalLogger internalLogger = logger;
        if (internalLogger.isDebugEnabled()) {
            internalLogger.debug("{} HANDSHAKEN: {}", this.ctx.channel(), this.engine.getSession().getCipherSuite());
        }
        this.ctx.fireUserEventTriggered(SslHandshakeCompletionEvent.SUCCESS);
        if (this.readDuringHandshake && !this.ctx.channel().config().isAutoRead()) {
            this.readDuringHandshake = false;
            this.ctx.read();
        }
    }

    private void setHandshakeFailure(ChannelHandlerContext ctx2, Throwable cause) {
        setHandshakeFailure(ctx2, cause, true, true);
    }

    private void setHandshakeFailure(ChannelHandlerContext ctx2, Throwable cause, boolean closeInbound, boolean notify) {
        String msg;
        try {
            this.engine.closeOutbound();
            if (closeInbound) {
                this.engine.closeInbound();
            }
        } catch (SSLException e) {
            InternalLogger internalLogger = logger;
            if (internalLogger.isDebugEnabled() && ((msg = e.getMessage()) == null || !msg.contains("possible truncation attack"))) {
                internalLogger.debug("{} SSLEngine.closeInbound() raised an exception.", ctx2.channel(), e);
            }
        } catch (Throwable th) {
            PendingWriteQueue pendingWriteQueue = this.pendingUnencryptedWrites;
            if (pendingWriteQueue != null) {
                pendingWriteQueue.removeAndFailAll(cause);
            }
            throw th;
        }
        notifyHandshakeFailure(cause, notify);
        PendingWriteQueue pendingWriteQueue2 = this.pendingUnencryptedWrites;
        if (pendingWriteQueue2 != null) {
            pendingWriteQueue2.removeAndFailAll(cause);
        }
    }

    /* access modifiers changed from: private */
    public void notifyHandshakeFailure(Throwable cause, boolean notify) {
        if (this.handshakePromise.tryFailure(cause)) {
            SslUtils.notifyHandshakeFailure(this.ctx, cause, notify);
        }
    }

    private void notifyClosePromise(Throwable cause) {
        if (cause == null) {
            if (this.sslClosePromise.trySuccess(this.ctx.channel())) {
                this.ctx.fireUserEventTriggered(SslCloseCompletionEvent.SUCCESS);
            }
        } else if (this.sslClosePromise.tryFailure(cause)) {
            this.ctx.fireUserEventTriggered(new SslCloseCompletionEvent(cause));
        }
    }

    private void closeOutboundAndChannel(ChannelHandlerContext ctx2, ChannelPromise promise, boolean disconnect) {
        if (ctx2.channel().isActive()) {
            this.outboundClosed = true;
            this.engine.closeOutbound();
            ChannelPromise closeNotifyPromise = ctx2.newPromise();
            try {
                flush(ctx2, closeNotifyPromise);
            } finally {
                safeClose(ctx2, closeNotifyPromise, ctx2.newPromise().addListener(new ChannelPromiseNotifier(false, promise)));
            }
        } else if (disconnect) {
            ctx2.disconnect(promise);
        } else {
            ctx2.close(promise);
        }
    }

    /* access modifiers changed from: private */
    public void flush(ChannelHandlerContext ctx2, ChannelPromise promise) {
        PendingWriteQueue pendingWriteQueue = this.pendingUnencryptedWrites;
        if (pendingWriteQueue != null) {
            pendingWriteQueue.add(Unpooled.EMPTY_BUFFER, promise);
        } else {
            promise.setFailure(newPendingWritesNullException());
        }
        flush(ctx2);
    }

    public void handlerAdded(ChannelHandlerContext ctx2) {
        this.ctx = ctx2;
        this.pendingUnencryptedWrites = new PendingWriteQueue(ctx2);
        if (ctx2.channel().isActive()) {
            startHandshakeProcessing();
        }
    }

    private void startHandshakeProcessing() {
        this.handshakeStarted = true;
        if (this.engine.getUseClientMode()) {
            handshake((Promise<Channel>) null);
        } else {
            applyHandshakeTimeout((Promise<Channel>) null);
        }
    }

    public Future<Channel> renegotiate() {
        ChannelHandlerContext ctx2 = this.ctx;
        if (ctx2 != null) {
            return renegotiate(ctx2.executor().newPromise());
        }
        throw new IllegalStateException();
    }

    public Future<Channel> renegotiate(final Promise<Channel> promise) {
        if (promise != null) {
            ChannelHandlerContext ctx2 = this.ctx;
            if (ctx2 != null) {
                EventExecutor executor = ctx2.executor();
                if (!executor.inEventLoop()) {
                    executor.execute(new Runnable() {
                        public void run() {
                            SslHandler.this.handshake(promise);
                        }
                    });
                    return promise;
                }
                handshake(promise);
                return promise;
            }
            throw new IllegalStateException();
        }
        throw new NullPointerException("promise");
    }

    /* access modifiers changed from: private */
    public void handshake(final Promise<Channel> newHandshakePromise) {
        Promise<Channel> p;
        if (newHandshakePromise != null) {
            Promise<Channel> oldHandshakePromise = this.handshakePromise;
            if (!oldHandshakePromise.isDone()) {
                oldHandshakePromise.addListener(new FutureListener<Channel>() {
                    public void operationComplete(Future<Channel> future) {
                        if (future.isSuccess()) {
                            newHandshakePromise.setSuccess(future.getNow());
                        } else {
                            newHandshakePromise.setFailure(future.cause());
                        }
                    }
                });
                return;
            } else {
                p = newHandshakePromise;
                this.handshakePromise = newHandshakePromise;
            }
        } else if (this.engine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
            p = this.handshakePromise;
            if (p.isDone()) {
                throw new AssertionError();
            }
        } else {
            return;
        }
        ChannelHandlerContext ctx2 = this.ctx;
        try {
            this.engine.beginHandshake();
            wrapNonAppData(ctx2, false);
        } catch (Throwable th) {
            forceFlush(ctx2);
            throw th;
        }
        forceFlush(ctx2);
        applyHandshakeTimeout(p);
    }

    private void applyHandshakeTimeout(Promise<Channel> p) {
        final Promise<Channel> promise = p == null ? this.handshakePromise : p;
        long handshakeTimeoutMillis2 = this.handshakeTimeoutMillis;
        if (handshakeTimeoutMillis2 > 0 && !promise.isDone()) {
            final ScheduledFuture<?> timeoutFuture = this.ctx.executor().schedule((Runnable) new Runnable() {
                public void run() {
                    if (!promise.isDone()) {
                        SslHandler.this.notifyHandshakeFailure(SslHandler.HANDSHAKE_TIMED_OUT, true);
                    }
                }
            }, handshakeTimeoutMillis2, TimeUnit.MILLISECONDS);
            promise.addListener(new FutureListener<Channel>() {
                public void operationComplete(Future<Channel> future) {
                    timeoutFuture.cancel(false);
                }
            });
        }
    }

    private void forceFlush(ChannelHandlerContext ctx2) {
        this.needsFlush = false;
        ctx2.flush();
    }

    public void channelActive(ChannelHandlerContext ctx2) {
        if (!this.startTls) {
            startHandshakeProcessing();
        }
        ctx2.fireChannelActive();
    }

    private void safeClose(final ChannelHandlerContext ctx2, final ChannelFuture flushFuture, final ChannelPromise promise) {
        final ScheduledFuture<?> timeoutFuture;
        if (!ctx2.channel().isActive()) {
            ctx2.close(promise);
            return;
        }
        if (!flushFuture.isDone()) {
            long closeNotifyTimeout = this.closeNotifyFlushTimeoutMillis;
            if (closeNotifyTimeout > 0) {
                timeoutFuture = ctx2.executor().schedule((Runnable) new Runnable() {
                    public void run() {
                        if (!flushFuture.isDone()) {
                            SslHandler.logger.warn("{} Last write attempt timed out; force-closing the connection.", (Object) ctx2.channel());
                            ChannelHandlerContext channelHandlerContext = ctx2;
                            SslHandler.addCloseListener(channelHandlerContext.close(channelHandlerContext.newPromise()), promise);
                        }
                    }
                }, closeNotifyTimeout, TimeUnit.MILLISECONDS);
            } else {
                timeoutFuture = null;
            }
        } else {
            timeoutFuture = null;
        }
        flushFuture.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture f) {
                final ScheduledFuture<?> closeNotifyReadTimeoutFuture;
                ScheduledFuture scheduledFuture = timeoutFuture;
                if (scheduledFuture != null) {
                    scheduledFuture.cancel(false);
                }
                final long closeNotifyReadTimeout = SslHandler.this.closeNotifyReadTimeoutMillis;
                if (closeNotifyReadTimeout <= 0) {
                    ChannelHandlerContext channelHandlerContext = ctx2;
                    SslHandler.addCloseListener(channelHandlerContext.close(channelHandlerContext.newPromise()), promise);
                    return;
                }
                if (!SslHandler.this.sslClosePromise.isDone()) {
                    closeNotifyReadTimeoutFuture = ctx2.executor().schedule((Runnable) new Runnable() {
                        public void run() {
                            if (!SslHandler.this.sslClosePromise.isDone()) {
                                SslHandler.logger.debug("{} did not receive close_notify in {}ms; force-closing the connection.", ctx2.channel(), Long.valueOf(closeNotifyReadTimeout));
                                ChannelHandlerContext channelHandlerContext = ctx2;
                                SslHandler.addCloseListener(channelHandlerContext.close(channelHandlerContext.newPromise()), promise);
                            }
                        }
                    }, closeNotifyReadTimeout, TimeUnit.MILLISECONDS);
                } else {
                    closeNotifyReadTimeoutFuture = null;
                }
                SslHandler.this.sslClosePromise.addListener((GenericFutureListener) new FutureListener<Channel>() {
                    public void operationComplete(Future<Channel> future) {
                        ScheduledFuture scheduledFuture = closeNotifyReadTimeoutFuture;
                        if (scheduledFuture != null) {
                            scheduledFuture.cancel(false);
                        }
                        ChannelHandlerContext channelHandlerContext = ctx2;
                        SslHandler.addCloseListener(channelHandlerContext.close(channelHandlerContext.newPromise()), promise);
                    }
                });
            }
        });
    }

    /* access modifiers changed from: private */
    public static void addCloseListener(ChannelFuture future, ChannelPromise promise) {
        future.addListener(new ChannelPromiseNotifier(false, promise));
    }

    private ByteBuf allocate(ChannelHandlerContext ctx2, int capacity) {
        ByteBufAllocator alloc = ctx2.alloc();
        if (this.engineType.wantsDirectBuffer) {
            return alloc.directBuffer(capacity);
        }
        return alloc.buffer(capacity);
    }

    private ByteBuf allocateOutNetBuf(ChannelHandlerContext ctx2, int pendingBytes, int numComponents) {
        return allocate(ctx2, this.engineType.calculateWrapBufferCapacity(this, pendingBytes, numComponents));
    }

    public final class LazyChannelPromise extends DefaultPromise<Channel> {
        private LazyChannelPromise() {
        }

        /* access modifiers changed from: protected */
        public EventExecutor executor() {
            if (SslHandler.this.ctx != null) {
                return SslHandler.this.ctx.executor();
            }
            throw new IllegalStateException();
        }

        /* access modifiers changed from: protected */
        public void checkDeadLock() {
            if (SslHandler.this.ctx != null) {
                super.checkDeadLock();
            }
        }
    }
}
