package io.netty.handler.ssl.ocsp;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.ssl.ReferenceCountedOpenSslEngine;
import io.netty.handler.ssl.SslHandshakeCompletionEvent;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.ThrowableUtil;
import javax.net.ssl.SSLHandshakeException;

public abstract class OcspClientHandler extends ChannelInboundHandlerAdapter {
    private static final SSLHandshakeException OCSP_VERIFICATION_EXCEPTION = ((SSLHandshakeException) ThrowableUtil.unknownStackTrace(new SSLHandshakeException("Bad OCSP response"), OcspClientHandler.class, "verify(...)"));
    private final ReferenceCountedOpenSslEngine engine;

    /* access modifiers changed from: protected */
    public abstract boolean verify(ChannelHandlerContext channelHandlerContext, ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine);

    protected OcspClientHandler(ReferenceCountedOpenSslEngine engine2) {
        this.engine = (ReferenceCountedOpenSslEngine) ObjectUtil.checkNotNull(engine2, "engine");
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof SslHandshakeCompletionEvent) {
            ctx.pipeline().remove((ChannelHandler) this);
            if (((SslHandshakeCompletionEvent) evt).isSuccess() && !verify(ctx, this.engine)) {
                throw OCSP_VERIFICATION_EXCEPTION;
            }
        }
        ctx.fireUserEventTriggered(evt);
    }
}
