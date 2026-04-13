package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;

public class FixedLengthFrameDecoder extends ByteToMessageDecoder {
    private final int frameLength;

    public FixedLengthFrameDecoder(int frameLength2) {
        if (frameLength2 > 0) {
            this.frameLength = frameLength2;
            return;
        }
        throw new IllegalArgumentException("frameLength must be a positive integer: " + frameLength2);
    }

    /* access modifiers changed from: protected */
    public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        Object decoded = decode(ctx, in);
        if (decoded != null) {
            out.add(decoded);
        }
    }

    /* access modifiers changed from: protected */
    public Object decode(ChannelHandlerContext ctx, ByteBuf in) {
        int readableBytes = in.readableBytes();
        int i = this.frameLength;
        if (readableBytes < i) {
            return null;
        }
        return in.readSlice(i).retain();
    }
}
