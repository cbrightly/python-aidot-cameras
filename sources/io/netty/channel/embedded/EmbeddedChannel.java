package io.netty.channel.embedded;

import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.DefaultChannelPipeline;
import io.netty.channel.EventLoop;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.RecyclableArrayList;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;

public class EmbeddedChannel extends AbstractChannel {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final ChannelMetadata METADATA_DISCONNECT = new ChannelMetadata(true);
    private static final ChannelMetadata METADATA_NO_DISCONNECT = new ChannelMetadata(false);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) EmbeddedChannel.class);
    private final ChannelConfig config;
    private Queue<Object> inboundMessages;
    private Throwable lastException;
    private final SocketAddress localAddress;
    private final EmbeddedEventLoop loop;
    private final ChannelMetadata metadata;
    private Queue<Object> outboundMessages;
    private final ChannelFutureListener recordExceptionListener;
    private final SocketAddress remoteAddress;
    private int state;

    static {
        Class<EmbeddedChannel> cls = EmbeddedChannel.class;
    }

    public EmbeddedChannel(ChannelHandler... handlers) {
        this(false, handlers);
    }

    public EmbeddedChannel(boolean hasDisconnect, ChannelHandler... handlers) {
        this(true, hasDisconnect, handlers);
    }

    public EmbeddedChannel(boolean register, boolean hasDisconnect, ChannelHandler... handlers) {
        super((Channel) null);
        this.loop = new EmbeddedEventLoop();
        this.recordExceptionListener = new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) {
                EmbeddedChannel.this.recordException(future);
            }
        };
        this.localAddress = new EmbeddedSocketAddress();
        this.remoteAddress = new EmbeddedSocketAddress();
        this.metadata = metadata(hasDisconnect);
        this.config = new DefaultChannelConfig(this);
        setup(register, handlers);
    }

    public EmbeddedChannel(boolean hasDisconnect, ChannelConfig config2, ChannelHandler... handlers) {
        super((Channel) null);
        this.loop = new EmbeddedEventLoop();
        this.recordExceptionListener = new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) {
                EmbeddedChannel.this.recordException(future);
            }
        };
        this.localAddress = new EmbeddedSocketAddress();
        this.remoteAddress = new EmbeddedSocketAddress();
        this.metadata = metadata(hasDisconnect);
        this.config = (ChannelConfig) ObjectUtil.checkNotNull(config2, "config");
        setup(true, handlers);
    }

    private static ChannelMetadata metadata(boolean hasDisconnect) {
        return hasDisconnect ? METADATA_DISCONNECT : METADATA_NO_DISCONNECT;
    }

    private void setup(boolean register, final ChannelHandler... handlers) {
        ObjectUtil.checkNotNull(handlers, "handlers");
        pipeline().addLast(new ChannelInitializer<Channel>() {
            /* access modifiers changed from: protected */
            public void initChannel(Channel ch) {
                ChannelPipeline pipeline = ch.pipeline();
                ChannelHandler[] channelHandlerArr = handlers;
                int length = channelHandlerArr.length;
                int i = 0;
                while (i < length) {
                    ChannelHandler h = channelHandlerArr[i];
                    if (h != null) {
                        pipeline.addLast(h);
                        i++;
                    } else {
                        return;
                    }
                }
            }
        });
        if (register && !this.loop.register(this).isDone()) {
            throw new AssertionError();
        }
    }

    public void register() {
        ChannelFuture future = this.loop.register(this);
        if (future.isDone()) {
            Throwable cause = future.cause();
            if (cause != null) {
                PlatformDependent.throwException(cause);
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: protected */
    public final DefaultChannelPipeline newChannelPipeline() {
        return new EmbeddedChannelPipeline(this);
    }

    public ChannelMetadata metadata() {
        return this.metadata;
    }

    public ChannelConfig config() {
        return this.config;
    }

    public boolean isOpen() {
        return this.state < 2;
    }

    public boolean isActive() {
        return this.state == 1;
    }

    public Queue<Object> inboundMessages() {
        if (this.inboundMessages == null) {
            this.inboundMessages = new ArrayDeque();
        }
        return this.inboundMessages;
    }

    @Deprecated
    public Queue<Object> lastInboundBuffer() {
        return inboundMessages();
    }

    public Queue<Object> outboundMessages() {
        if (this.outboundMessages == null) {
            this.outboundMessages = new ArrayDeque();
        }
        return this.outboundMessages;
    }

    @Deprecated
    public Queue<Object> lastOutboundBuffer() {
        return outboundMessages();
    }

    public Object readInbound() {
        return poll(this.inboundMessages);
    }

    public Object readOutbound() {
        return poll(this.outboundMessages);
    }

    public boolean writeInbound(Object... msgs) {
        ensureOpen();
        if (msgs.length == 0) {
            return isNotEmpty(this.inboundMessages);
        }
        ChannelPipeline p = pipeline();
        for (Object m : msgs) {
            p.fireChannelRead(m);
        }
        p.fireChannelReadComplete();
        runPendingTasks();
        checkException();
        return isNotEmpty(this.inboundMessages);
    }

    public boolean writeOutbound(Object... msgs) {
        ensureOpen();
        if (msgs.length == 0) {
            return isNotEmpty(this.outboundMessages);
        }
        RecyclableArrayList futures = RecyclableArrayList.newInstance(msgs.length);
        try {
            int length = msgs.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Object m = msgs[i];
                if (m == null) {
                    break;
                }
                futures.add(write(m));
                i++;
            }
            runPendingTasks();
            flush();
            int size = futures.size();
            for (int i2 = 0; i2 < size; i2++) {
                ChannelFuture future = (ChannelFuture) futures.get(i2);
                if (future.isDone()) {
                    recordException(future);
                } else {
                    future.addListener(this.recordExceptionListener);
                }
            }
            checkException();
            return isNotEmpty(this.outboundMessages);
        } finally {
            futures.recycle();
        }
    }

    public boolean finish() {
        return finish(false);
    }

    public boolean finishAndReleaseAll() {
        return finish(true);
    }

    private boolean finish(boolean releaseAll) {
        close();
        try {
            checkException();
            return isNotEmpty(this.inboundMessages) || isNotEmpty(this.outboundMessages);
        } finally {
            if (releaseAll) {
                releaseAll(this.inboundMessages);
                releaseAll(this.outboundMessages);
            }
        }
    }

    public boolean releaseInbound() {
        return releaseAll(this.inboundMessages);
    }

    public boolean releaseOutbound() {
        return releaseAll(this.outboundMessages);
    }

    private static boolean releaseAll(Queue<Object> queue) {
        if (!isNotEmpty(queue)) {
            return false;
        }
        while (true) {
            Object msg = queue.poll();
            if (msg == null) {
                return true;
            }
            ReferenceCountUtil.release(msg);
        }
    }

    private void finishPendingTasks(boolean cancel) {
        runPendingTasks();
        if (cancel) {
            this.loop.cancelScheduledTasks();
        }
    }

    public final ChannelFuture close() {
        return close(newPromise());
    }

    public final ChannelFuture disconnect() {
        return disconnect(newPromise());
    }

    public final ChannelFuture close(ChannelPromise promise) {
        runPendingTasks();
        ChannelFuture future = super.close(promise);
        finishPendingTasks(true);
        return future;
    }

    public final ChannelFuture disconnect(ChannelPromise promise) {
        ChannelFuture future = super.disconnect(promise);
        finishPendingTasks(!this.metadata.hasDisconnect());
        return future;
    }

    private static boolean isNotEmpty(Queue<Object> queue) {
        return queue != null && !queue.isEmpty();
    }

    private static Object poll(Queue<Object> queue) {
        if (queue != null) {
            return queue.poll();
        }
        return null;
    }

    public void runPendingTasks() {
        try {
            this.loop.runTasks();
        } catch (Exception e) {
            recordException((Throwable) e);
        }
        try {
            this.loop.runScheduledTasks();
        } catch (Exception e2) {
            recordException((Throwable) e2);
        }
    }

    public long runScheduledPendingTasks() {
        try {
            return this.loop.runScheduledTasks();
        } catch (Exception e) {
            recordException((Throwable) e);
            return this.loop.nextScheduledTask();
        }
    }

    /* access modifiers changed from: private */
    public void recordException(ChannelFuture future) {
        if (!future.isSuccess()) {
            recordException(future.cause());
        }
    }

    /* access modifiers changed from: private */
    public void recordException(Throwable cause) {
        if (this.lastException == null) {
            this.lastException = cause;
        } else {
            logger.warn("More than one exception was raised. Will report only the first one and log others.", cause);
        }
    }

    public void checkException() {
        Throwable t = this.lastException;
        if (t != null) {
            this.lastException = null;
            PlatformDependent.throwException(t);
        }
    }

    /* access modifiers changed from: protected */
    public final void ensureOpen() {
        if (!isOpen()) {
            recordException((Throwable) new ClosedChannelException());
            checkException();
        }
    }

    /* access modifiers changed from: protected */
    public boolean isCompatible(EventLoop loop2) {
        return loop2 instanceof EmbeddedEventLoop;
    }

    /* access modifiers changed from: protected */
    public SocketAddress localAddress0() {
        if (isActive()) {
            return this.localAddress;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public SocketAddress remoteAddress0() {
        if (isActive()) {
            return this.remoteAddress;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void doRegister() {
        this.state = 1;
    }

    /* access modifiers changed from: protected */
    public void doBind(SocketAddress localAddress2) {
    }

    /* access modifiers changed from: protected */
    public void doDisconnect() {
        if (!this.metadata.hasDisconnect()) {
            doClose();
        }
    }

    /* access modifiers changed from: protected */
    public void doClose() {
        this.state = 2;
    }

    /* access modifiers changed from: protected */
    public void doBeginRead() {
    }

    /* access modifiers changed from: protected */
    public AbstractChannel.AbstractUnsafe newUnsafe() {
        return new EmbeddedUnsafe();
    }

    public Channel.Unsafe unsafe() {
        return ((EmbeddedUnsafe) super.unsafe()).wrapped;
    }

    /* access modifiers changed from: protected */
    public void doWrite(ChannelOutboundBuffer in) {
        while (true) {
            Object msg = in.current();
            if (msg != null) {
                ReferenceCountUtil.retain(msg);
                outboundMessages().add(msg);
                in.remove();
            } else {
                return;
            }
        }
    }

    public final class EmbeddedUnsafe extends AbstractChannel.AbstractUnsafe {
        final Channel.Unsafe wrapped;

        private EmbeddedUnsafe() {
            super();
            this.wrapped = new Channel.Unsafe() {
                public SocketAddress localAddress() {
                    return EmbeddedUnsafe.this.localAddress();
                }

                public SocketAddress remoteAddress() {
                    return EmbeddedUnsafe.this.remoteAddress();
                }

                public void register(EventLoop eventLoop, ChannelPromise promise) {
                    EmbeddedUnsafe.this.register(eventLoop, promise);
                    EmbeddedChannel.this.runPendingTasks();
                }

                public void bind(SocketAddress localAddress, ChannelPromise promise) {
                    EmbeddedUnsafe.this.bind(localAddress, promise);
                    EmbeddedChannel.this.runPendingTasks();
                }

                public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
                    EmbeddedUnsafe.this.connect(remoteAddress, localAddress, promise);
                    EmbeddedChannel.this.runPendingTasks();
                }

                public void disconnect(ChannelPromise promise) {
                    EmbeddedUnsafe.this.disconnect(promise);
                    EmbeddedChannel.this.runPendingTasks();
                }

                public void close(ChannelPromise promise) {
                    EmbeddedUnsafe.this.close(promise);
                    EmbeddedChannel.this.runPendingTasks();
                }

                public void closeForcibly() {
                    EmbeddedUnsafe.this.closeForcibly();
                    EmbeddedChannel.this.runPendingTasks();
                }

                public void deregister(ChannelPromise promise) {
                    EmbeddedUnsafe.this.deregister(promise);
                    EmbeddedChannel.this.runPendingTasks();
                }

                public void beginRead() {
                    EmbeddedUnsafe.this.beginRead();
                    EmbeddedChannel.this.runPendingTasks();
                }

                public void write(Object msg, ChannelPromise promise) {
                    EmbeddedUnsafe.this.write(msg, promise);
                    EmbeddedChannel.this.runPendingTasks();
                }

                public void flush() {
                    EmbeddedUnsafe.this.flush();
                    EmbeddedChannel.this.runPendingTasks();
                }

                public ChannelPromise voidPromise() {
                    return EmbeddedUnsafe.this.voidPromise();
                }

                public ChannelOutboundBuffer outboundBuffer() {
                    return EmbeddedUnsafe.this.outboundBuffer();
                }
            };
        }

        public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
            safeSetSuccess(promise);
        }
    }

    public final class EmbeddedChannelPipeline extends DefaultChannelPipeline {
        EmbeddedChannelPipeline(EmbeddedChannel channel) {
            super(channel);
        }

        /* access modifiers changed from: protected */
        public void onUnhandledInboundException(Throwable cause) {
            EmbeddedChannel.this.recordException(cause);
        }

        /* access modifiers changed from: protected */
        public void onUnhandledInboundMessage(Object msg) {
            EmbeddedChannel.this.inboundMessages().add(msg);
        }
    }
}
