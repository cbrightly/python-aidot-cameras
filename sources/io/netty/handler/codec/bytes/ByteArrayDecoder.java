package io.netty.handler.codec.bytes;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

public class ByteArrayDecoder extends MessageToMessageDecoder<ByteBuf> {
    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        byte[] array = new byte[msg.readableBytes()];
        msg.getBytes(0, array);
        out.add(array);
    }
}
