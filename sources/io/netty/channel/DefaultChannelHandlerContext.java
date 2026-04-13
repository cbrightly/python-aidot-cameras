package io.netty.channel;

import io.netty.util.concurrent.EventExecutor;

public final class DefaultChannelHandlerContext extends AbstractChannelHandlerContext {
    private final ChannelHandler handler;

    DefaultChannelHandlerContext(DefaultChannelPipeline pipeline, EventExecutor executor, String name, ChannelHandler handler2) {
        super(pipeline, executor, name, isInbound(handler2), isOutbound(handler2));
        if (handler2 != null) {
            this.handler = handler2;
            return;
        }
        throw new NullPointerException("handler");
    }

    public ChannelHandler handler() {
        return this.handler;
    }

    private static boolean isInbound(ChannelHandler handler2) {
        return handler2 instanceof ChannelInboundHandler;
    }

    private static boolean isOutbound(ChannelHandler handler2) {
        return handler2 instanceof ChannelOutboundHandler;
    }
}
