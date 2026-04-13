package io.netty.handler.codec.base64;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

@ChannelHandler.Sharable
public class Base64Decoder extends MessageToMessageDecoder<ByteBuf> {
    private final Base64Dialect dialect;

    public Base64Decoder() {
        this(Base64Dialect.STANDARD);
    }

    public Base64Decoder(Base64Dialect dialect2) {
        if (dialect2 != null) {
            this.dialect = dialect2;
            return;
        }
        throw new NullPointerException("dialect");
    }

    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        out.add(Base64.decode(msg, msg.readerIndex(), msg.readableBytes(), this.dialect));
    }
}
