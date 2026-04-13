package io.netty.channel;

import androidx.core.app.NotificationCompat;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.AbstractChannel;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.util.DefaultAttributeMap;
import io.netty.util.Recycler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.OrderedEventExecutor;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PromiseNotificationUtil;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import java.net.SocketAddress;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public abstract class AbstractChannelHandlerContext extends DefaultAttributeMap implements ChannelHandlerContext {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ADD_COMPLETE = 2;
    private static final int ADD_PENDING = 1;
    private static final AtomicIntegerFieldUpdater<AbstractChannelHandlerContext> HANDLER_STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(AbstractChannelHandlerContext.class, "handlerState");
    private static final int INIT = 0;
    private static final int REMOVE_COMPLETE = 3;
    final EventExecutor executor;
    private volatile int handlerState = 0;
    private final boolean inbound;
    private Runnable invokeChannelReadCompleteTask;
    private Runnable invokeChannelWritableStateChangedTask;
    private Runnable invokeFlushTask;
    private Runnable invokeReadTask;
    private final String name;
    volatile AbstractChannelHandlerContext next;
    private final boolean ordered;
    private final boolean outbound;
    /* access modifiers changed from: private */
    public final DefaultChannelPipeline pipeline;
    volatile AbstractChannelHandlerContext prev;
    private ChannelFuture succeededFuture;

    AbstractChannelHandlerContext(DefaultChannelPipeline pipeline2, EventExecutor executor2, String name2, boolean inbound2, boolean outbound2) {
        boolean z = false;
        this.name = (String) ObjectUtil.checkNotNull(name2, "name");
        this.pipeline = pipeline2;
        this.executor = executor2;
        this.inbound = inbound2;
        this.outbound = outbound2;
        this.ordered = (executor2 == null || (executor2 instanceof OrderedEventExecutor)) ? true : z;
    }

    public Channel channel() {
        return this.pipeline.channel();
    }

    public ChannelPipeline pipeline() {
        return this.pipeline;
    }

    public ByteBufAllocator alloc() {
        return channel().config().getAllocator();
    }

    public EventExecutor executor() {
        EventExecutor eventExecutor = this.executor;
        if (eventExecutor == null) {
            return channel().eventLoop();
        }
        return eventExecutor;
    }

    public String name() {
        return this.name;
    }

    public ChannelHandlerContext fireChannelRegistered() {
        invokeChannelRegistered(findContextInbound());
        return this;
    }

    static void invokeChannelRegistered(AbstractChannelHandlerContext next2) {
        EventExecutor executor2 = next2.executor();
        if (executor2.inEventLoop()) {
            next2.invokeChannelRegistered();
        } else {
            executor2.execute(new Runnable(next2) {
                final /* synthetic */ AbstractChannelHandlerContext val$next;

                {
                    this.val$next = r1;
                }

                public void run() {
                    this.val$next.invokeChannelRegistered();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void invokeChannelRegistered() {
        if (invokeHandler()) {
            try {
                ((ChannelInboundHandler) handler()).channelRegistered(this);
            } catch (Throwable t) {
                notifyHandlerException(t);
            }
        } else {
            fireChannelRegistered();
        }
    }

    public ChannelHandlerContext fireChannelUnregistered() {
        invokeChannelUnregistered(findContextInbound());
        return this;
    }

    static void invokeChannelUnregistered(AbstractChannelHandlerContext next2) {
        EventExecutor executor2 = next2.executor();
        if (executor2.inEventLoop()) {
            next2.invokeChannelUnregistered();
        } else {
            executor2.execute(new Runnable(next2) {
                final /* synthetic */ AbstractChannelHandlerContext val$next;

                {
                    this.val$next = r1;
                }

                public void run() {
                    this.val$next.invokeChannelUnregistered();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void invokeChannelUnregistered() {
        if (invokeHandler()) {
            try {
                ((ChannelInboundHandler) handler()).channelUnregistered(this);
            } catch (Throwable t) {
                notifyHandlerException(t);
            }
        } else {
            fireChannelUnregistered();
        }
    }

    public ChannelHandlerContext fireChannelActive() {
        invokeChannelActive(findContextInbound());
        return this;
    }

    static void invokeChannelActive(AbstractChannelHandlerContext next2) {
        EventExecutor executor2 = next2.executor();
        if (executor2.inEventLoop()) {
            next2.invokeChannelActive();
        } else {
            executor2.execute(new Runnable(next2) {
                final /* synthetic */ AbstractChannelHandlerContext val$next;

                {
                    this.val$next = r1;
                }

                public void run() {
                    this.val$next.invokeChannelActive();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void invokeChannelActive() {
        if (invokeHandler()) {
            try {
                ((ChannelInboundHandler) handler()).channelActive(this);
            } catch (Throwable t) {
                notifyHandlerException(t);
            }
        } else {
            fireChannelActive();
        }
    }

    public ChannelHandlerContext fireChannelInactive() {
        invokeChannelInactive(findContextInbound());
        return this;
    }

    static void invokeChannelInactive(AbstractChannelHandlerContext next2) {
        EventExecutor executor2 = next2.executor();
        if (executor2.inEventLoop()) {
            next2.invokeChannelInactive();
        } else {
            executor2.execute(new Runnable(next2) {
                final /* synthetic */ AbstractChannelHandlerContext val$next;

                {
                    this.val$next = r1;
                }

                public void run() {
                    this.val$next.invokeChannelInactive();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void invokeChannelInactive() {
        if (invokeHandler()) {
            try {
                ((ChannelInboundHandler) handler()).channelInactive(this);
            } catch (Throwable t) {
                notifyHandlerException(t);
            }
        } else {
            fireChannelInactive();
        }
    }

    public ChannelHandlerContext fireExceptionCaught(Throwable cause) {
        invokeExceptionCaught(this.next, cause);
        return this;
    }

    static void invokeExceptionCaught(AbstractChannelHandlerContext next2, final Throwable cause) {
        ObjectUtil.checkNotNull(cause, "cause");
        EventExecutor executor2 = next2.executor();
        if (executor2.inEventLoop()) {
            next2.invokeExceptionCaught(cause);
            return;
        }
        try {
            executor2.execute(new Runnable(next2) {
                final /* synthetic */ AbstractChannelHandlerContext val$next;

                {
                    this.val$next = r1;
                }

                public void run() {
                    this.val$next.invokeExceptionCaught(cause);
                }
            });
        } catch (Throwable t) {
            InternalLogger internalLogger = DefaultChannelPipeline.logger;
            if (internalLogger.isWarnEnabled()) {
                internalLogger.warn("Failed to submit an exceptionCaught() event.", t);
                internalLogger.warn("The exceptionCaught() event that was failed to submit was:", cause);
            }
        }
    }

    /* access modifiers changed from: private */
    public void invokeExceptionCaught(Throwable cause) {
        if (invokeHandler()) {
            try {
                handler().exceptionCaught(this, cause);
            } catch (Throwable error) {
                InternalLogger internalLogger = DefaultChannelPipeline.logger;
                if (internalLogger.isDebugEnabled()) {
                    internalLogger.debug("An exception {}was thrown by a user handler's exceptionCaught() method while handling the following exception:", ThrowableUtil.stackTraceToString(error), cause);
                } else if (internalLogger.isWarnEnabled()) {
                    internalLogger.warn("An exception '{}' [enable DEBUG level for full stacktrace] was thrown by a user handler's exceptionCaught() method while handling the following exception:", error, cause);
                }
            }
        } else {
            fireExceptionCaught(cause);
        }
    }

    public ChannelHandlerContext fireUserEventTriggered(Object event) {
        invokeUserEventTriggered(findContextInbound(), event);
        return this;
    }

    static void invokeUserEventTriggered(AbstractChannelHandlerContext next2, final Object event) {
        ObjectUtil.checkNotNull(event, NotificationCompat.CATEGORY_EVENT);
        EventExecutor executor2 = next2.executor();
        if (executor2.inEventLoop()) {
            next2.invokeUserEventTriggered(event);
        } else {
            executor2.execute(new Runnable(next2) {
                final /* synthetic */ AbstractChannelHandlerContext val$next;

                {
                    this.val$next = r1;
                }

                public void run() {
                    this.val$next.invokeUserEventTriggered(event);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void invokeUserEventTriggered(Object event) {
        if (invokeHandler()) {
            try {
                ((ChannelInboundHandler) handler()).userEventTriggered(this, event);
            } catch (Throwable t) {
                notifyHandlerException(t);
            }
        } else {
            fireUserEventTriggered(event);
        }
    }

    public ChannelHandlerContext fireChannelRead(Object msg) {
        invokeChannelRead(findContextInbound(), msg);
        return this;
    }

    static void invokeChannelRead(AbstractChannelHandlerContext next2, final Object msg) {
        ObjectUtil.checkNotNull(msg, NotificationCompat.CATEGORY_MESSAGE);
        EventExecutor executor2 = next2.executor();
        if (executor2.inEventLoop()) {
            next2.invokeChannelRead(msg);
        } else {
            executor2.execute(new Runnable(next2) {
                final /* synthetic */ AbstractChannelHandlerContext val$next;

                {
                    this.val$next = r1;
                }

                public void run() {
                    this.val$next.invokeChannelRead(msg);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void invokeChannelRead(Object msg) {
        if (invokeHandler()) {
            try {
                ((ChannelInboundHandler) handler()).channelRead(this, msg);
            } catch (Throwable t) {
                notifyHandlerException(t);
            }
        } else {
            fireChannelRead(msg);
        }
    }

    public ChannelHandlerContext fireChannelReadComplete() {
        invokeChannelReadComplete(findContextInbound());
        return this;
    }

    static void invokeChannelReadComplete(AbstractChannelHandlerContext next2) {
        EventExecutor executor2 = next2.executor();
        if (executor2.inEventLoop()) {
            next2.invokeChannelReadComplete();
            return;
        }
        Runnable task = next2.invokeChannelReadCompleteTask;
        if (task == null) {
            AnonymousClass8 r2 = new Runnable(next2) {
                final /* synthetic */ AbstractChannelHandlerContext val$next;

                {
                    this.val$next = r1;
                }

                public void run() {
                    this.val$next.invokeChannelReadComplete();
                }
            };
            task = r2;
            next2.invokeChannelReadCompleteTask = r2;
        }
        executor2.execute(task);
    }

    /* access modifiers changed from: private */
    public void invokeChannelReadComplete() {
        if (invokeHandler()) {
            try {
                ((ChannelInboundHandler) handler()).channelReadComplete(this);
            } catch (Throwable t) {
                notifyHandlerException(t);
            }
        } else {
            fireChannelReadComplete();
        }
    }

    public ChannelHandlerContext fireChannelWritabilityChanged() {
        invokeChannelWritabilityChanged(findContextInbound());
        return this;
    }

    static void invokeChannelWritabilityChanged(AbstractChannelHandlerContext next2) {
        EventExecutor executor2 = next2.executor();
        if (executor2.inEventLoop()) {
            next2.invokeChannelWritabilityChanged();
            return;
        }
        Runnable task = next2.invokeChannelWritableStateChangedTask;
        if (task == null) {
            AnonymousClass9 r2 = new Runnable(next2) {
                final /* synthetic */ AbstractChannelHandlerContext val$next;

                {
                    this.val$next = r1;
                }

                public void run() {
                    this.val$next.invokeChannelWritabilityChanged();
                }
            };
            task = r2;
            next2.invokeChannelWritableStateChangedTask = r2;
        }
        executor2.execute(task);
    }

    /* access modifiers changed from: private */
    public void invokeChannelWritabilityChanged() {
        if (invokeHandler()) {
            try {
                ((ChannelInboundHandler) handler()).channelWritabilityChanged(this);
            } catch (Throwable t) {
                notifyHandlerException(t);
            }
        } else {
            fireChannelWritabilityChanged();
        }
    }

    public ChannelFuture bind(SocketAddress localAddress) {
        return bind(localAddress, newPromise());
    }

    public ChannelFuture connect(SocketAddress remoteAddress) {
        return connect(remoteAddress, newPromise());
    }

    public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
        return connect(remoteAddress, localAddress, newPromise());
    }

    public ChannelFuture disconnect() {
        return disconnect(newPromise());
    }

    public ChannelFuture close() {
        return close(newPromise());
    }

    public ChannelFuture deregister() {
        return deregister(newPromise());
    }

    public ChannelFuture bind(final SocketAddress localAddress, final ChannelPromise promise) {
        if (localAddress == null) {
            throw new NullPointerException("localAddress");
        } else if (isNotValidPromise(promise, false)) {
            return promise;
        } else {
            final AbstractChannelHandlerContext next2 = findContextOutbound();
            EventExecutor executor2 = next2.executor();
            if (executor2.inEventLoop()) {
                next2.invokeBind(localAddress, promise);
            } else {
                safeExecute(executor2, new Runnable() {
                    public void run() {
                        next2.invokeBind(localAddress, promise);
                    }
                }, promise, (Object) null);
            }
            return promise;
        }
    }

    /* access modifiers changed from: private */
    public void invokeBind(SocketAddress localAddress, ChannelPromise promise) {
        if (invokeHandler()) {
            try {
                ((ChannelOutboundHandler) handler()).bind(this, localAddress, promise);
            } catch (Throwable t) {
                notifyOutboundHandlerException(t, promise);
            }
        } else {
            bind(localAddress, promise);
        }
    }

    public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
        return connect(remoteAddress, (SocketAddress) null, promise);
    }

    public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
        if (remoteAddress == null) {
            throw new NullPointerException("remoteAddress");
        } else if (isNotValidPromise(promise, false)) {
            return promise;
        } else {
            AbstractChannelHandlerContext next2 = findContextOutbound();
            EventExecutor executor2 = next2.executor();
            if (executor2.inEventLoop()) {
                next2.invokeConnect(remoteAddress, localAddress, promise);
            } else {
                final AbstractChannelHandlerContext abstractChannelHandlerContext = next2;
                final SocketAddress socketAddress = remoteAddress;
                final SocketAddress socketAddress2 = localAddress;
                final ChannelPromise channelPromise = promise;
                safeExecute(executor2, new Runnable() {
                    public void run() {
                        abstractChannelHandlerContext.invokeConnect(socketAddress, socketAddress2, channelPromise);
                    }
                }, promise, (Object) null);
            }
            return promise;
        }
    }

    /* access modifiers changed from: private */
    public void invokeConnect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
        if (invokeHandler()) {
            try {
                ((ChannelOutboundHandler) handler()).connect(this, remoteAddress, localAddress, promise);
            } catch (Throwable t) {
                notifyOutboundHandlerException(t, promise);
            }
        } else {
            connect(remoteAddress, localAddress, promise);
        }
    }

    public ChannelFuture disconnect(final ChannelPromise promise) {
        if (isNotValidPromise(promise, false)) {
            return promise;
        }
        final AbstractChannelHandlerContext next2 = findContextOutbound();
        EventExecutor executor2 = next2.executor();
        if (!executor2.inEventLoop()) {
            safeExecute(executor2, new Runnable() {
                public void run() {
                    if (!AbstractChannelHandlerContext.this.channel().metadata().hasDisconnect()) {
                        next2.invokeClose(promise);
                    } else {
                        next2.invokeDisconnect(promise);
                    }
                }
            }, promise, (Object) null);
        } else if (!channel().metadata().hasDisconnect()) {
            next2.invokeClose(promise);
        } else {
            next2.invokeDisconnect(promise);
        }
        return promise;
    }

    /* access modifiers changed from: private */
    public void invokeDisconnect(ChannelPromise promise) {
        if (invokeHandler()) {
            try {
                ((ChannelOutboundHandler) handler()).disconnect(this, promise);
            } catch (Throwable t) {
                notifyOutboundHandlerException(t, promise);
            }
        } else {
            disconnect(promise);
        }
    }

    public ChannelFuture close(final ChannelPromise promise) {
        if (isNotValidPromise(promise, false)) {
            return promise;
        }
        final AbstractChannelHandlerContext next2 = findContextOutbound();
        EventExecutor executor2 = next2.executor();
        if (executor2.inEventLoop()) {
            next2.invokeClose(promise);
        } else {
            safeExecute(executor2, new Runnable() {
                public void run() {
                    next2.invokeClose(promise);
                }
            }, promise, (Object) null);
        }
        return promise;
    }

    /* access modifiers changed from: private */
    public void invokeClose(ChannelPromise promise) {
        if (invokeHandler()) {
            try {
                ((ChannelOutboundHandler) handler()).close(this, promise);
            } catch (Throwable t) {
                notifyOutboundHandlerException(t, promise);
            }
        } else {
            close(promise);
        }
    }

    public ChannelFuture deregister(final ChannelPromise promise) {
        if (isNotValidPromise(promise, false)) {
            return promise;
        }
        final AbstractChannelHandlerContext next2 = findContextOutbound();
        EventExecutor executor2 = next2.executor();
        if (executor2.inEventLoop()) {
            next2.invokeDeregister(promise);
        } else {
            safeExecute(executor2, new Runnable() {
                public void run() {
                    next2.invokeDeregister(promise);
                }
            }, promise, (Object) null);
        }
        return promise;
    }

    /* access modifiers changed from: private */
    public void invokeDeregister(ChannelPromise promise) {
        if (invokeHandler()) {
            try {
                ((ChannelOutboundHandler) handler()).deregister(this, promise);
            } catch (Throwable t) {
                notifyOutboundHandlerException(t, promise);
            }
        } else {
            deregister(promise);
        }
    }

    public ChannelHandlerContext read() {
        final AbstractChannelHandlerContext next2 = findContextOutbound();
        EventExecutor executor2 = next2.executor();
        if (executor2.inEventLoop()) {
            next2.invokeRead();
        } else {
            Runnable task = next2.invokeReadTask;
            if (task == null) {
                AnonymousClass15 r3 = new Runnable() {
                    public void run() {
                        next2.invokeRead();
                    }
                };
                task = r3;
                next2.invokeReadTask = r3;
            }
            executor2.execute(task);
        }
        return this;
    }

    /* access modifiers changed from: private */
    public void invokeRead() {
        if (invokeHandler()) {
            try {
                ((ChannelOutboundHandler) handler()).read(this);
            } catch (Throwable t) {
                notifyHandlerException(t);
            }
        } else {
            read();
        }
    }

    public ChannelFuture write(Object msg) {
        return write(msg, newPromise());
    }

    public ChannelFuture write(Object msg, ChannelPromise promise) {
        if (msg != null) {
            try {
                if (isNotValidPromise(promise, true)) {
                    ReferenceCountUtil.release(msg);
                    return promise;
                }
                write(msg, false, promise);
                return promise;
            } catch (RuntimeException e) {
                ReferenceCountUtil.release(msg);
                throw e;
            }
        } else {
            throw new NullPointerException(NotificationCompat.CATEGORY_MESSAGE);
        }
    }

    /* access modifiers changed from: private */
    public void invokeWrite(Object msg, ChannelPromise promise) {
        if (invokeHandler()) {
            invokeWrite0(msg, promise);
        } else {
            write(msg, promise);
        }
    }

    private void invokeWrite0(Object msg, ChannelPromise promise) {
        try {
            ((ChannelOutboundHandler) handler()).write(this, msg, promise);
        } catch (Throwable t) {
            notifyOutboundHandlerException(t, promise);
        }
    }

    public ChannelHandlerContext flush() {
        final AbstractChannelHandlerContext next2 = findContextOutbound();
        EventExecutor executor2 = next2.executor();
        if (executor2.inEventLoop()) {
            next2.invokeFlush();
        } else {
            Runnable task = next2.invokeFlushTask;
            if (task == null) {
                AnonymousClass16 r3 = new Runnable() {
                    public void run() {
                        next2.invokeFlush();
                    }
                };
                task = r3;
                next2.invokeFlushTask = r3;
            }
            safeExecute(executor2, task, channel().voidPromise(), (Object) null);
        }
        return this;
    }

    /* access modifiers changed from: private */
    public void invokeFlush() {
        if (invokeHandler()) {
            invokeFlush0();
        } else {
            flush();
        }
    }

    private void invokeFlush0() {
        try {
            ((ChannelOutboundHandler) handler()).flush(this);
        } catch (Throwable t) {
            notifyHandlerException(t);
        }
    }

    public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
        if (msg == null) {
            throw new NullPointerException(NotificationCompat.CATEGORY_MESSAGE);
        } else if (isNotValidPromise(promise, true)) {
            ReferenceCountUtil.release(msg);
            return promise;
        } else {
            write(msg, true, promise);
            return promise;
        }
    }

    private void invokeWriteAndFlush(Object msg, ChannelPromise promise) {
        if (invokeHandler()) {
            invokeWrite0(msg, promise);
            invokeFlush0();
            return;
        }
        writeAndFlush(msg, promise);
    }

    private void write(Object msg, boolean flush, ChannelPromise promise) {
        AbstractWriteTask task;
        AbstractChannelHandlerContext next2 = findContextOutbound();
        EventExecutor executor2 = next2.executor();
        if (!executor2.inEventLoop()) {
            if (flush) {
                task = WriteAndFlushTask.newInstance(next2, msg, promise);
            } else {
                task = WriteTask.newInstance(next2, msg, promise);
            }
            safeExecute(executor2, task, promise, msg);
        } else if (flush) {
            next2.invokeWriteAndFlush(msg, promise);
        } else {
            next2.invokeWrite(msg, promise);
        }
    }

    public ChannelFuture writeAndFlush(Object msg) {
        return writeAndFlush(msg, newPromise());
    }

    private static void notifyOutboundHandlerException(Throwable cause, ChannelPromise promise) {
        PromiseNotificationUtil.tryFailure(promise, cause, promise instanceof VoidChannelPromise ? null : DefaultChannelPipeline.logger);
    }

    private void notifyHandlerException(Throwable cause) {
        if (inExceptionCaught(cause)) {
            InternalLogger internalLogger = DefaultChannelPipeline.logger;
            if (internalLogger.isWarnEnabled()) {
                internalLogger.warn("An exception was thrown by a user handler while handling an exceptionCaught event", cause);
                return;
            }
            return;
        }
        invokeExceptionCaught(cause);
    }

    private static boolean inExceptionCaught(Throwable cause) {
        do {
            StackTraceElement[] trace = cause.getStackTrace();
            if (trace != null) {
                int length = trace.length;
                int i = 0;
                while (i < length) {
                    StackTraceElement t = trace[i];
                    if (t == null) {
                        break;
                    } else if ("exceptionCaught".equals(t.getMethodName())) {
                        return true;
                    } else {
                        i++;
                    }
                }
            }
            cause = cause.getCause();
        } while (cause != null);
        return false;
    }

    public ChannelPromise newPromise() {
        return new DefaultChannelPromise(channel(), executor());
    }

    public ChannelProgressivePromise newProgressivePromise() {
        return new DefaultChannelProgressivePromise(channel(), executor());
    }

    public ChannelFuture newSucceededFuture() {
        ChannelFuture succeededFuture2 = this.succeededFuture;
        if (succeededFuture2 != null) {
            return succeededFuture2;
        }
        ChannelFuture succeededChannelFuture = new SucceededChannelFuture(channel(), executor());
        ChannelFuture succeededFuture3 = succeededChannelFuture;
        this.succeededFuture = succeededChannelFuture;
        return succeededFuture3;
    }

    public ChannelFuture newFailedFuture(Throwable cause) {
        return new FailedChannelFuture(channel(), executor(), cause);
    }

    private boolean isNotValidPromise(ChannelPromise promise, boolean allowVoidPromise) {
        if (promise == null) {
            throw new NullPointerException("promise");
        } else if (promise.isDone()) {
            if (promise.isCancelled()) {
                return true;
            }
            throw new IllegalArgumentException("promise already done: " + promise);
        } else if (promise.channel() != channel()) {
            throw new IllegalArgumentException(String.format("promise.channel does not match: %s (expected: %s)", new Object[]{promise.channel(), channel()}));
        } else if (promise.getClass() == DefaultChannelPromise.class) {
            return false;
        } else {
            if (!allowVoidPromise && (promise instanceof VoidChannelPromise)) {
                throw new IllegalArgumentException(StringUtil.simpleClassName((Class<?>) VoidChannelPromise.class) + " not allowed for this operation");
            } else if (!(promise instanceof AbstractChannel.CloseFuture)) {
                return false;
            } else {
                throw new IllegalArgumentException(StringUtil.simpleClassName((Class<?>) AbstractChannel.CloseFuture.class) + " not allowed in a pipeline");
            }
        }
    }

    private AbstractChannelHandlerContext findContextInbound() {
        AbstractChannelHandlerContext ctx = this;
        do {
            ctx = ctx.next;
        } while (!ctx.inbound);
        return ctx;
    }

    private AbstractChannelHandlerContext findContextOutbound() {
        AbstractChannelHandlerContext ctx = this;
        do {
            ctx = ctx.prev;
        } while (!ctx.outbound);
        return ctx;
    }

    public ChannelPromise voidPromise() {
        return channel().voidPromise();
    }

    /* access modifiers changed from: package-private */
    public final void setRemoved() {
        this.handlerState = 3;
    }

    /*  JADX ERROR: StackOverflow in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    final void setAddComplete() {
        /*
            r3 = this;
        L_0x0000:
            int r0 = r3.handlerState
            r1 = 3
            if (r0 == r1) goto L_0x0010
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater<io.netty.channel.AbstractChannelHandlerContext> r1 = HANDLER_STATE_UPDATER
            r2 = 2
            boolean r1 = r1.compareAndSet(r3, r0, r2)
            if (r1 == 0) goto L_0x000f
            goto L_0x0010
        L_0x000f:
            goto L_0x0000
        L_0x0010:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.AbstractChannelHandlerContext.setAddComplete():void");
    }

    /* access modifiers changed from: package-private */
    public final void setAddPending() {
        if (!HANDLER_STATE_UPDATER.compareAndSet(this, 0, 1)) {
            throw new AssertionError();
        }
    }

    private boolean invokeHandler() {
        int handlerState2 = this.handlerState;
        if (handlerState2 != 2) {
            return !this.ordered && handlerState2 == 1;
        }
        return true;
    }

    public boolean isRemoved() {
        return this.handlerState == 3;
    }

    private static void safeExecute(EventExecutor executor2, Runnable runnable, ChannelPromise promise, Object msg) {
        try {
            executor2.execute(runnable);
        } catch (Throwable th) {
            if (msg != null) {
                ReferenceCountUtil.release(msg);
            }
            throw th;
        }
    }

    public static abstract class AbstractWriteTask implements Runnable {
        private static final boolean ESTIMATE_TASK_SIZE_ON_SUBMIT = SystemPropertyUtil.getBoolean("io.netty.transport.estimateSizeOnSubmit", true);
        private static final int WRITE_TASK_OVERHEAD = SystemPropertyUtil.getInt("io.netty.transport.writeTaskSizeOverhead", 48);
        private AbstractChannelHandlerContext ctx;
        private final Recycler.Handle handle;
        private Object msg;
        private ChannelPromise promise;
        private int size;

        /* access modifiers changed from: protected */
        public abstract void recycle(Recycler.Handle handle2);

        private AbstractWriteTask(Recycler.Handle handle2) {
            this.handle = handle2;
        }

        protected static void init(AbstractWriteTask task, AbstractChannelHandlerContext ctx2, Object msg2, ChannelPromise promise2) {
            task.ctx = ctx2;
            task.msg = msg2;
            task.promise = promise2;
            if (ESTIMATE_TASK_SIZE_ON_SUBMIT) {
                ChannelOutboundBuffer buffer = ctx2.channel().unsafe().outboundBuffer();
                if (buffer != null) {
                    int size2 = ctx2.pipeline.estimatorHandle().size(msg2) + WRITE_TASK_OVERHEAD;
                    task.size = size2;
                    buffer.incrementPendingOutboundBytes((long) size2);
                    return;
                }
                task.size = 0;
                return;
            }
            task.size = 0;
        }

        public final void run() {
            try {
                ChannelOutboundBuffer buffer = this.ctx.channel().unsafe().outboundBuffer();
                if (ESTIMATE_TASK_SIZE_ON_SUBMIT && buffer != null) {
                    buffer.decrementPendingOutboundBytes((long) this.size);
                }
                write(this.ctx, this.msg, this.promise);
            } finally {
                this.ctx = null;
                this.msg = null;
                this.promise = null;
                recycle(this.handle);
            }
        }

        /* access modifiers changed from: protected */
        public void write(AbstractChannelHandlerContext ctx2, Object msg2, ChannelPromise promise2) {
            ctx2.invokeWrite(msg2, promise2);
        }
    }

    public static final class WriteTask extends AbstractWriteTask implements SingleThreadEventLoop.NonWakeupRunnable {
        private static final Recycler<WriteTask> RECYCLER = new Recycler<WriteTask>() {
            /* access modifiers changed from: protected */
            public WriteTask newObject(Recycler.Handle handle) {
                return new WriteTask(handle);
            }
        };

        /* access modifiers changed from: private */
        public static WriteTask newInstance(AbstractChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
            WriteTask task = RECYCLER.get();
            AbstractWriteTask.init(task, ctx, msg, promise);
            return task;
        }

        private WriteTask(Recycler.Handle handle) {
            super(handle);
        }

        /* access modifiers changed from: protected */
        public void recycle(Recycler.Handle handle) {
            RECYCLER.recycle(this, handle);
        }
    }

    public static final class WriteAndFlushTask extends AbstractWriteTask {
        private static final Recycler<WriteAndFlushTask> RECYCLER = new Recycler<WriteAndFlushTask>() {
            /* access modifiers changed from: protected */
            public WriteAndFlushTask newObject(Recycler.Handle handle) {
                return new WriteAndFlushTask(handle);
            }
        };

        /* access modifiers changed from: private */
        public static WriteAndFlushTask newInstance(AbstractChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
            WriteAndFlushTask task = RECYCLER.get();
            AbstractWriteTask.init(task, ctx, msg, promise);
            return task;
        }

        private WriteAndFlushTask(Recycler.Handle handle) {
            super(handle);
        }

        public void write(AbstractChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
            super.write(ctx, msg, promise);
            ctx.invokeFlush();
        }

        /* access modifiers changed from: protected */
        public void recycle(Recycler.Handle handle) {
            RECYCLER.recycle(this, handle);
        }
    }
}
