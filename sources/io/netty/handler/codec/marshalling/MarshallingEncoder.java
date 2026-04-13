package io.netty.handler.codec.marshalling;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.jboss.marshalling.Marshaller;

@ChannelHandler.Sharable
public class MarshallingEncoder extends MessageToByteEncoder<Object> {
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    private final MarshallerProvider provider;

    public MarshallingEncoder(MarshallerProvider provider2) {
        this.provider = provider2;
    }

    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) {
        Marshaller marshaller = this.provider.getMarshaller(ctx);
        int lengthPos = out.writerIndex();
        out.writeBytes(LENGTH_PLACEHOLDER);
        marshaller.start(new ChannelBufferByteOutput(out));
        marshaller.writeObject(msg);
        marshaller.finish();
        marshaller.close();
        out.setInt(lengthPos, (out.writerIndex() - lengthPos) - 4);
    }
}
