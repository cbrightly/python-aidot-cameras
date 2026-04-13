package io.netty.handler.ssl;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public abstract class ApplicationProtocolNegotiationHandler extends ChannelInboundHandlerAdapter {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) ApplicationProtocolNegotiationHandler.class);
    private final String fallbackProtocol;

    /* access modifiers changed from: protected */
    public abstract void configurePipeline(ChannelHandlerContext channelHandlerContext, String str);

    protected ApplicationProtocolNegotiationHandler(String fallbackProtocol2) {
        this.fallbackProtocol = (String) ObjectUtil.checkNotNull(fallbackProtocol2, "fallbackProtocol");
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof SslHandshakeCompletionEvent) {
            ctx.pipeline().remove((ChannelHandler) this);
            SslHandshakeCompletionEvent handshakeEvent = (SslHandshakeCompletionEvent) evt;
            if (handshakeEvent.isSuccess()) {
                SslHandler sslHandler = (SslHandler) ctx.pipeline().get(SslHandler.class);
                if (sslHandler != null) {
                    String protocol = sslHandler.applicationProtocol();
                    configurePipeline(ctx, protocol != null ? protocol : this.fallbackProtocol);
                } else {
                    throw new IllegalStateException("cannot find a SslHandler in the pipeline (required for application-level protocol negotiation)");
                }
            } else {
                handshakeFailure(ctx, handshakeEvent.cause());
            }
        }
        ctx.fireUserEventTriggered(evt);
    }

    /* access modifiers changed from: protected */
    public void handshakeFailure(ChannelHandlerContext ctx, Throwable cause) {
        logger.warn("{} TLS handshake failed:", ctx.channel(), cause);
        ctx.close();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.warn("{} Failed to select the application-level protocol:", ctx.channel(), cause);
        ctx.close();
    }
}
