package io.netty.handler.codec.string;

import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;

@ChannelHandler.Sharable
public class StringEncoder extends MessageToMessageEncoder<CharSequence> {
    private final Charset charset;

    public StringEncoder() {
        this(Charset.defaultCharset());
    }

    public StringEncoder(Charset charset2) {
        if (charset2 != null) {
            this.charset = charset2;
            return;
        }
        throw new NullPointerException("charset");
    }

    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext ctx, CharSequence msg, List<Object> out) {
        if (msg.length() != 0) {
            out.add(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(msg), this.charset));
        }
    }
}
