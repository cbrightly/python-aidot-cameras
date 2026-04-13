package io.netty.handler.codec.marshalling;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteInput;

public class ChannelBufferByteInput implements ByteInput {
    private final ByteBuf buffer;

    ChannelBufferByteInput(ByteBuf buffer2) {
        this.buffer = buffer2;
    }

    public void close() {
    }

    public int available() {
        return this.buffer.readableBytes();
    }

    public int read() {
        if (this.buffer.isReadable()) {
            return this.buffer.readByte() & 255;
        }
        return -1;
    }

    public int read(byte[] array) {
        return read(array, 0, array.length);
    }

    public int read(byte[] dst, int dstIndex, int length) {
        int available = available();
        if (available == 0) {
            return -1;
        }
        int length2 = Math.min(available, length);
        this.buffer.readBytes(dst, dstIndex, length2);
        return length2;
    }

    public long skip(long bytes) {
        int readable = this.buffer.readableBytes();
        if (((long) readable) < bytes) {
            bytes = (long) readable;
        }
        ByteBuf byteBuf = this.buffer;
        byteBuf.readerIndex((int) (((long) byteBuf.readerIndex()) + bytes));
        return bytes;
    }
}
