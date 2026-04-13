package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

public class OptionalSslHandler extends ByteToMessageDecoder {
    private final SslContext sslContext;

    public OptionalSslHandler(SslContext sslContext2) {
        this.sslContext = (SslContext) ObjectUtil.checkNotNull(sslContext2, "sslContext");
    }

    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext context, ByteBuf in, List<Object> list) {
        if (in.readableBytes() >= 5) {
            if (SslHandler.isEncrypted(in)) {
                handleSsl(context);
            } else {
                handleNonSsl(context);
            }
        }
    }

    private void handleSsl(ChannelHandlerContext context) {
        SslHandler sslHandler = null;
        try {
            sslHandler = newSslHandler(context, this.sslContext);
            context.pipeline().replace((ChannelHandler) this, newSslHandlerName(), (ChannelHandler) sslHandler);
            sslHandler = null;
        } finally {
            if (sslHandler != null) {
                ReferenceCountUtil.safeRelease(sslHandler.engine());
            }
        }
    }

    private void handleNonSsl(ChannelHandlerContext context) {
        ChannelHandler handler = newNonSslHandler(context);
        if (handler != null) {
            context.pipeline().replace((ChannelHandler) this, newNonSslHandlerName(), handler);
        } else {
            context.pipeline().remove((ChannelHandler) this);
        }
    }

    /* access modifiers changed from: protected */
    public String newSslHandlerName() {
        return null;
    }

    /* access modifiers changed from: protected */
    public SslHandler newSslHandler(ChannelHandlerContext context, SslContext sslContext2) {
        return sslContext2.newHandler(context.alloc());
    }

    /* access modifiers changed from: protected */
    public String newNonSslHandlerName() {
        return null;
    }

    /* access modifiers changed from: protected */
    public ChannelHandler newNonSslHandler(ChannelHandlerContext context) {
        return null;
    }
}
