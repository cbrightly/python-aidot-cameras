package io.netty.handler.codec.marshalling;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteOutput;

public class ChannelBufferByteOutput implements ByteOutput {
    private final ByteBuf buffer;

    ChannelBufferByteOutput(ByteBuf buffer2) {
        this.buffer = buffer2;
    }

    public void close() {
    }

    public void flush() {
    }

    public void write(int b) {
        this.buffer.writeByte(b);
    }

    public void write(byte[] bytes) {
        this.buffer.writeBytes(bytes);
    }

    public void write(byte[] bytes, int srcIndex, int length) {
        this.buffer.writeBytes(bytes, srcIndex, length);
    }

    /* access modifiers changed from: package-private */
    public ByteBuf getBuffer() {
        return this.buffer;
    }
}
