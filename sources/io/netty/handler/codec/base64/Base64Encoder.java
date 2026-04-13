package io.netty.handler.codec.base64;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

@ChannelHandler.Sharable
public class Base64Encoder extends MessageToMessageEncoder<ByteBuf> {
    private final boolean breakLines;
    private final Base64Dialect dialect;

    public Base64Encoder() {
        this(true);
    }

    public Base64Encoder(boolean breakLines2) {
        this(breakLines2, Base64Dialect.STANDARD);
    }

    public Base64Encoder(boolean breakLines2, Base64Dialect dialect2) {
        if (dialect2 != null) {
            this.breakLines = breakLines2;
            this.dialect = dialect2;
            return;
        }
        throw new NullPointerException("dialect");
    }

    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        out.add(Base64.encode(msg, msg.readerIndex(), msg.readableBytes(), this.breakLines, this.dialect));
    }
}
