package io.netty.handler.codec.serialization;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@ChannelHandler.Sharable
public class ObjectEncoder extends MessageToByteEncoder<Serializable> {
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];

    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext ctx, Serializable msg, ByteBuf out) {
        int startIdx = out.writerIndex();
        ByteBufOutputStream bout = new ByteBufOutputStream(out);
        ObjectOutputStream oout = null;
        try {
            bout.write(LENGTH_PLACEHOLDER);
            oout = new CompactObjectOutputStream(bout);
            oout.writeObject(msg);
            oout.flush();
            oout.close();
            out.setInt(startIdx, (out.writerIndex() - startIdx) - 4);
        } catch (Throwable th) {
            if (oout != null) {
                oout.close();
            } else {
                bout.close();
            }
            throw th;
        }
    }
}
