package io.netty.handler.codec.serialization;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class CompatibleObjectEncoder extends MessageToByteEncoder<Serializable> {
    private final int resetInterval;
    private int writtenObjects;

    public CompatibleObjectEncoder() {
        this(16);
    }

    public CompatibleObjectEncoder(int resetInterval2) {
        if (resetInterval2 >= 0) {
            this.resetInterval = resetInterval2;
            return;
        }
        throw new IllegalArgumentException("resetInterval: " + resetInterval2);
    }

    /* access modifiers changed from: protected */
    public ObjectOutputStream newObjectOutputStream(OutputStream out) {
        return new ObjectOutputStream(out);
    }

    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext ctx, Serializable msg, ByteBuf out) {
        ObjectOutputStream oos = newObjectOutputStream(new ByteBufOutputStream(out));
        try {
            int i = this.resetInterval;
            if (i != 0) {
                int i2 = this.writtenObjects + 1;
                this.writtenObjects = i2;
                if (i2 % i == 0) {
                    oos.reset();
                }
            }
            oos.writeObject(msg);
            oos.flush();
        } finally {
            oos.close();
        }
    }
}
