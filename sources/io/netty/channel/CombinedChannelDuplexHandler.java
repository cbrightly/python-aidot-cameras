package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;

public class CombinedChannelDuplexHandler<I extends ChannelInboundHandler, O extends ChannelOutboundHandler> extends ChannelDuplexHandler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) CombinedChannelDuplexHandler.class);
    private volatile boolean handlerAdded;
    private DelegatingChannelHandlerContext inboundCtx;
    private I inboundHandler;
    /* access modifiers changed from: private */
    public DelegatingChannelHandlerContext outboundCtx;
    /* access modifiers changed from: private */
    public O outboundHandler;

    protected CombinedChannelDuplexHandler() {
        ensureNotSharable();
    }

    public CombinedChannelDuplexHandler(I inboundHandler2, O outboundHandler2) {
        ensureNotSharable();
        init(inboundHandler2, outboundHandler2);
    }

    /* access modifiers changed from: protected */
    public final void init(I inboundHandler2, O outboundHandler2) {
        validate(inboundHandler2, outboundHandler2);
        this.inboundHandler = inboundHandler2;
        this.outboundHandler = outboundHandler2;
    }

    private void validate(I inboundHandler2, O outboundHandler2) {
        if (this.inboundHandler != null) {
            throw new IllegalStateException("init() can not be invoked if " + CombinedChannelDuplexHandler.class.getSimpleName() + " was constructed with non-default constructor.");
        } else if (inboundHandler2 == null) {
            throw new NullPointerException("inboundHandler");
        } else if (outboundHandler2 == null) {
            throw new NullPointerException("outboundHandler");
        } else if (inboundHandler2 instanceof ChannelOutboundHandler) {
            throw new IllegalArgumentException("inboundHandler must not implement " + ChannelOutboundHandler.class.getSimpleName() + " to get combined.");
        } else if (outboundHandler2 instanceof ChannelInboundHandler) {
            throw new IllegalArgumentException("outboundHandler must not implement " + ChannelInboundHandler.class.getSimpleName() + " to get combined.");
        }
    }

    /* access modifiers changed from: protected */
    public final I inboundHandler() {
        return this.inboundHandler;
    }

    /* access modifiers changed from: protected */
    public final O outboundHandler() {
        return this.outboundHandler;
    }

    private void checkAdded() {
        if (!this.handlerAdded) {
            throw new IllegalStateException("handler not added to pipeline yet");
        }
    }

    public final void removeInboundHandler() {
        checkAdded();
        this.inboundCtx.remove();
    }

    public final void removeOutboundHandler() {
        checkAdded();
        this.outboundCtx.remove();
    }

    public void handlerAdded(ChannelHandlerContext ctx) {
        if (this.inboundHandler != null) {
            this.outboundCtx = new DelegatingChannelHandlerContext(ctx, this.outboundHandler);
            this.inboundCtx = new DelegatingChannelHandlerContext(ctx, this.inboundHandler) {
                public ChannelHandlerContext fireExceptionCaught(Throwable cause) {
                    if (!CombinedChannelDuplexHandler.this.outboundCtx.removed) {
                        try {
                            CombinedChannelDuplexHandler.this.outboundHandler.exceptionCaught(CombinedChannelDuplexHandler.this.outboundCtx, cause);
                        } catch (Throwable error) {
                            if (CombinedChannelDuplexHandler.logger.isDebugEnabled()) {
                                CombinedChannelDuplexHandler.logger.debug("An exception {}was thrown by a user handler's exceptionCaught() method while handling the following exception:", ThrowableUtil.stackTraceToString(error), cause);
                            } else if (CombinedChannelDuplexHandler.logger.isWarnEnabled()) {
                                CombinedChannelDuplexHandler.logger.warn("An exception '{}' [enable DEBUG level for full stacktrace] was thrown by a user handler's exceptionCaught() method while handling the following exception:", error, cause);
                            }
                        }
                    } else {
                        super.fireExceptionCaught(cause);
                    }
                    return this;
                }
            };
            this.handlerAdded = true;
            try {
                this.inboundHandler.handlerAdded(this.inboundCtx);
            } finally {
                this.outboundHandler.handlerAdded(this.outboundCtx);
            }
        } else {
            throw new IllegalStateException("init() must be invoked before being added to a " + ChannelPipeline.class.getSimpleName() + " if " + CombinedChannelDuplexHandler.class.getSimpleName() + " was constructed with the default constructor.");
        }
    }

    public void handlerRemoved(ChannelHandlerContext ctx) {
        try {
            this.inboundCtx.remove();
        } finally {
            this.outboundCtx.remove();
        }
    }

    public void channelRegistered(ChannelHandlerContext ctx) {
        if (ctx == this.inboundCtx.ctx) {
            DelegatingChannelHandlerContext delegatingChannelHandlerContext = this.inboundCtx;
            if (!delegatingChannelHandlerContext.removed) {
                this.inboundHandler.channelRegistered(delegatingChannelHandlerContext);
            } else {
                delegatingChannelHandlerContext.fireChannelRegistered();
            }
        } else {
            throw new AssertionError();
        }
    }

    public void channelUnregistered(ChannelHandlerContext ctx) {
        if (ctx == this.inboundCtx.ctx) {
            DelegatingChannelHandlerContext delegatingChannelHandlerContext = this.inboundCtx;
            if (!delegatingChannelHandlerContext.removed) {
                this.inboundHandler.channelUnregistered(delegatingChannelHandlerContext);
            } else {
                delegatingChannelHandlerContext.fireChannelUnregistered();
            }
        } else {
            throw new AssertionError();
        }
    }

    public void channelActive(ChannelHandlerContext ctx) {
        if (ctx == this.inboundCtx.ctx) {
            DelegatingChannelHandlerContext delegatingChannelHandlerContext = this.inboundCtx;
            if (!delegatingChannelHandlerContext.removed) {
                this.inboundHandler.channelActive(delegatingChannelHandlerContext);
            } else {
                delegatingChannelHandlerContext.fireChannelActive();
            }
        } else {
            throw new AssertionError();
        }
    }

    public void channelInactive(ChannelHandlerContext ctx) {
        if (ctx == this.inboundCtx.ctx) {
            DelegatingChannelHandlerContext delegatingChannelHandlerContext = this.inboundCtx;
            if (!delegatingChannelHandlerContext.removed) {
                this.inboundHandler.channelInactive(delegatingChannelHandlerContext);
            } else {
                delegatingChannelHandlerContext.fireChannelInactive();
            }
        } else {
            throw new AssertionError();
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (ctx == this.inboundCtx.ctx) {
            DelegatingChannelHandlerContext delegatingChannelHandlerContext = this.inboundCtx;
            if (!delegatingChannelHandlerContext.removed) {
                this.inboundHandler.exceptionCaught(delegatingChannelHandlerContext, cause);
            } else {
                delegatingChannelHandlerContext.fireExceptionCaught(cause);
            }
        } else {
            throw new AssertionError();
        }
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (ctx == this.inboundCtx.ctx) {
            DelegatingChannelHandlerContext delegatingChannelHandlerContext = this.inboundCtx;
            if (!delegatingChannelHandlerContext.removed) {
                this.inboundHandler.userEventTriggered(delegatingChannelHandlerContext, evt);
            } else {
                delegatingChannelHandlerContext.fireUserEventTriggered(evt);
            }
        } else {
            throw new AssertionError();
        }
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (ctx == this.inboundCtx.ctx) {
            DelegatingChannelHandlerContext delegatingChannelHandlerContext = this.inboundCtx;
            if (!delegatingChannelHandlerContext.removed) {
                this.inboundHandler.channelRead(delegatingChannelHandlerContext, msg);
            } else {
                delegatingChannelHandlerContext.fireChannelRead(msg);
            }
        } else {
            throw new AssertionError();
        }
    }

    public void channelReadComplete(ChannelHandlerContext ctx) {
        if (ctx == this.inboundCtx.ctx) {
            DelegatingChannelHandlerContext delegatingChannelHandlerContext = this.inboundCtx;
            if (!delegatingChannelHandlerContext.removed) {
                this.inboundHandler.channelReadComplete(delegatingChannelHandlerContext);
            } else {
                delegatingChannelHandlerContext.fireChannelReadComplete();
            }
        } else {
            throw new AssertionError();
        }
    }

    public void channelWritabilityChanged(ChannelHandlerContext ctx) {
        if (ctx == this.inboundCtx.ctx) {
            DelegatingChannelHandlerContext delegatingChannelHandlerContext = this.inboundCtx;
            if (!delegatingChannelHandlerContext.removed) {
                this.inboundHandler.channelWritabilityChanged(delegatingChannelHandlerContext);
            } else {
                delegatingChannelHandlerContext.fireChannelWritabilityChanged();
            }
        } else {
            throw new AssertionError();
        }
    }

    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) {
        if (ctx == this.outboundCtx.ctx) {
            DelegatingChannelHandlerContext delegatingChannelHandlerContext = this.outboundCtx;
            if (!delegatingChannelHandlerContext.removed) {
                this.outboundHandler.bind(delegatingChannelHandlerContext, localAddress, promise);
            } else {
                delegatingChannelHandlerContext.bind(localAddress, promise);
            }
        } else {
            throw new AssertionError();
        }
    }

    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
        if (ctx == this.outboundCtx.ctx) {
            DelegatingChannelHandlerContext delegatingChannelHandlerContext = this.outboundCtx;
            if (!delegatingChannelHandlerContext.removed) {
                this.outboundHandler.connect(delegatingChannelHandlerContext, remoteAddress, localAddress, promise);
            } else {
                delegatingChannelHandlerContext.connect(localAddress, promise);
            }
        } else {
            throw new AssertionError();
        }
    }

    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) {
        if (ctx == this.outboundCtx.ctx) {
            DelegatingChannelHandlerContext delegatingChannelHandlerContext = this.outboundCtx;
            if (!delegatingChannelHandlerContext.removed) {
                this.outboundHandler.disconnect(delegatingChannelHandlerContext, promise);
            } else {
                delegatingChannelHandlerContext.disconnect(promise);
            }
        } else {
            throw new AssertionError();
        }
    }

    public void close(ChannelHandlerContext ctx, ChannelPromise promise) {
        if (ctx == this.outboundCtx.ctx) {
            DelegatingChannelHandlerContext delegatingChannelHandlerContext = this.outboundCtx;
            if (!delegatingChannelHandlerContext.removed) {
                this.outboundHandler.close(delegatingChannelHandlerContext, promise);
            } else {
                delegatingChannelHandlerContext.close(promise);
            }
        } else {
            throw new AssertionError();
        }
    }

    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) {
        if (ctx == this.outboundCtx.ctx) {
            DelegatingChannelHandlerContext delegatingChannelHandlerContext = this.outboundCtx;
            if (!delegatingChannelHandlerContext.removed) {
                this.outboundHandler.deregister(delegatingChannelHandlerContext, promise);
            } else {
                delegatingChannelHandlerContext.deregister(promise);
            }
        } else {
            throw new AssertionError();
        }
    }

    public void read(ChannelHandlerContext ctx) {
        if (ctx == this.outboundCtx.ctx) {
            DelegatingChannelHandlerContext delegatingChannelHandlerContext = this.outboundCtx;
            if (!delegatingChannelHandlerContext.removed) {
                this.outboundHandler.read(delegatingChannelHandlerContext);
            } else {
                delegatingChannelHandlerContext.read();
            }
        } else {
            throw new AssertionError();
        }
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        if (ctx == this.outboundCtx.ctx) {
            DelegatingChannelHandlerContext delegatingChannelHandlerContext = this.outboundCtx;
            if (!delegatingChannelHandlerContext.removed) {
                this.outboundHandler.write(delegatingChannelHandlerContext, msg, promise);
            } else {
                delegatingChannelHandlerContext.write(msg, promise);
            }
        } else {
            throw new AssertionError();
        }
    }

    public void flush(ChannelHandlerContext ctx) {
        if (ctx == this.outboundCtx.ctx) {
            DelegatingChannelHandlerContext delegatingChannelHandlerContext = this.outboundCtx;
            if (!delegatingChannelHandlerContext.removed) {
                this.outboundHandler.flush(delegatingChannelHandlerContext);
            } else {
                delegatingChannelHandlerContext.flush();
            }
        } else {
            throw new AssertionError();
        }
    }

    public static class DelegatingChannelHandlerContext implements ChannelHandlerContext {
        /* access modifiers changed from: private */
        public final ChannelHandlerContext ctx;
        private final ChannelHandler handler;
        boolean removed;

        DelegatingChannelHandlerContext(ChannelHandlerContext ctx2, ChannelHandler handler2) {
            this.ctx = ctx2;
            this.handler = handler2;
        }

        public Channel channel() {
            return this.ctx.channel();
        }

        public EventExecutor executor() {
            return this.ctx.executor();
        }

        public String name() {
            return this.ctx.name();
        }

        public ChannelHandler handler() {
            return this.ctx.handler();
        }

        public boolean isRemoved() {
            return this.removed || this.ctx.isRemoved();
        }

        public ChannelHandlerContext fireChannelRegistered() {
            this.ctx.fireChannelRegistered();
            return this;
        }

        public ChannelHandlerContext fireChannelUnregistered() {
            this.ctx.fireChannelUnregistered();
            return this;
        }

        public ChannelHandlerContext fireChannelActive() {
            this.ctx.fireChannelActive();
            return this;
        }

        public ChannelHandlerContext fireChannelInactive() {
            this.ctx.fireChannelInactive();
            return this;
        }

        public ChannelHandlerContext fireExceptionCaught(Throwable cause) {
            this.ctx.fireExceptionCaught(cause);
            return this;
        }

        public ChannelHandlerContext fireUserEventTriggered(Object event) {
            this.ctx.fireUserEventTriggered(event);
            return this;
        }

        public ChannelHandlerContext fireChannelRead(Object msg) {
            this.ctx.fireChannelRead(msg);
            return this;
        }

        public ChannelHandlerContext fireChannelReadComplete() {
            this.ctx.fireChannelReadComplete();
            return this;
        }

        public ChannelHandlerContext fireChannelWritabilityChanged() {
            this.ctx.fireChannelWritabilityChanged();
            return this;
        }

        public ChannelFuture bind(SocketAddress localAddress) {
            return this.ctx.bind(localAddress);
        }

        public ChannelFuture connect(SocketAddress remoteAddress) {
            return this.ctx.connect(remoteAddress);
        }

        public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
            return this.ctx.connect(remoteAddress, localAddress);
        }

        public ChannelFuture disconnect() {
            return this.ctx.disconnect();
        }

        public ChannelFuture close() {
            return this.ctx.close();
        }

        public ChannelFuture deregister() {
            return this.ctx.deregister();
        }

        public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
            return this.ctx.bind(localAddress, promise);
        }

        public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
            return this.ctx.connect(remoteAddress, promise);
        }

        public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
            return this.ctx.connect(remoteAddress, localAddress, promise);
        }

        public ChannelFuture disconnect(ChannelPromise promise) {
            return this.ctx.disconnect(promise);
        }

        public ChannelFuture close(ChannelPromise promise) {
            return this.ctx.close(promise);
        }

        public ChannelFuture deregister(ChannelPromise promise) {
            return this.ctx.deregister(promise);
        }

        public ChannelHandlerContext read() {
            this.ctx.read();
            return this;
        }

        public ChannelFuture write(Object msg) {
            return this.ctx.write(msg);
        }

        public ChannelFuture write(Object msg, ChannelPromise promise) {
            return this.ctx.write(msg, promise);
        }

        public ChannelHandlerContext flush() {
            this.ctx.flush();
            return this;
        }

        public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
            return this.ctx.writeAndFlush(msg, promise);
        }

        public ChannelFuture writeAndFlush(Object msg) {
            return this.ctx.writeAndFlush(msg);
        }

        public ChannelPipeline pipeline() {
            return this.ctx.pipeline();
        }

        public ByteBufAllocator alloc() {
            return this.ctx.alloc();
        }

        public ChannelPromise newPromise() {
            return this.ctx.newPromise();
        }

        public ChannelProgressivePromise newProgressivePromise() {
            return this.ctx.newProgressivePromise();
        }

        public ChannelFuture newSucceededFuture() {
            return this.ctx.newSucceededFuture();
        }

        public ChannelFuture newFailedFuture(Throwable cause) {
            return this.ctx.newFailedFuture(cause);
        }

        public ChannelPromise voidPromise() {
            return this.ctx.voidPromise();
        }

        public <T> Attribute<T> attr(AttributeKey<T> key) {
            return this.ctx.attr(key);
        }

        /* access modifiers changed from: package-private */
        public final void remove() {
            EventExecutor executor = executor();
            if (executor.inEventLoop()) {
                remove0();
            } else {
                executor.execute(new Runnable() {
                    public void run() {
                        DelegatingChannelHandlerContext.this.remove0();
                    }
                });
            }
        }

        /* access modifiers changed from: private */
        public void remove0() {
            if (!this.removed) {
                this.removed = true;
                try {
                    this.handler.handlerRemoved(this);
                } catch (Throwable cause) {
                    fireExceptionCaught(new ChannelPipelineException(this.handler.getClass().getName() + ".handlerRemoved() has thrown an exception.", cause));
                }
            }
        }
    }
}
