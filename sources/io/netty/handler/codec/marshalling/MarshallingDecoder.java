package io.netty.handler.codec.marshalling;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.jboss.marshalling.Unmarshaller;

public class MarshallingDecoder extends LengthFieldBasedFrameDecoder {
    private final UnmarshallerProvider provider;

    public MarshallingDecoder(UnmarshallerProvider provider2) {
        this(provider2, 1048576);
    }

    public MarshallingDecoder(UnmarshallerProvider provider2, int maxObjectSize) {
        super(maxObjectSize, 0, 4, 0, 4);
        this.provider = provider2;
    }

    /* access modifiers changed from: protected */
    public Object decode(ChannelHandlerContext ctx, ByteBuf in) {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        Unmarshaller unmarshaller = this.provider.getUnmarshaller(ctx);
        try {
            unmarshaller.start(new ChannelBufferByteInput(frame));
            Object obj = unmarshaller.readObject();
            unmarshaller.finish();
            return obj;
        } finally {
            unmarshaller.close();
        }
    }

    /* access modifiers changed from: protected */
    public ByteBuf extractFrame(ChannelHandlerContext ctx, ByteBuf buffer, int index, int length) {
        return buffer.slice(index, length);
    }
}
