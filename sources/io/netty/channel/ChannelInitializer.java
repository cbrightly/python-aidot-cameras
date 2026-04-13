package io.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.ConcurrentMap;

@ChannelHandler.Sharable
public abstract class ChannelInitializer<C extends Channel> extends ChannelInboundHandlerAdapter {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) ChannelInitializer.class);
    private final ConcurrentMap<ChannelHandlerContext, Boolean> initMap = PlatformDependent.newConcurrentHashMap();

    /* access modifiers changed from: protected */
    public abstract void initChannel(C c);

    public final void channelRegistered(ChannelHandlerContext ctx) {
        if (initChannel(ctx)) {
            ctx.pipeline().fireChannelRegistered();
        } else {
            ctx.fireChannelRegistered();
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        InternalLogger internalLogger = logger;
        internalLogger.warn("Failed to initialize a channel. Closing: " + ctx.channel(), cause);
        ctx.close();
    }

    public void handlerAdded(ChannelHandlerContext ctx) {
        if (ctx.channel().isRegistered()) {
            initChannel(ctx);
        }
    }

    private boolean initChannel(ChannelHandlerContext ctx) {
        if (this.initMap.putIfAbsent(ctx, Boolean.TRUE) != null) {
            return false;
        }
        try {
            initChannel(ctx.channel());
        } catch (Throwable th) {
            remove(ctx);
            throw th;
        }
        remove(ctx);
        return true;
    }

    private void remove(ChannelHandlerContext ctx) {
        try {
            ChannelPipeline pipeline = ctx.pipeline();
            if (pipeline.context((ChannelHandler) this) != null) {
                pipeline.remove((ChannelHandler) this);
            }
        } finally {
            this.initMap.remove(ctx);
        }
    }
}
