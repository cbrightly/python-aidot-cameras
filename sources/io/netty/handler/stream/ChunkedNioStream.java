package io.netty.handler.stream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

public class ChunkedNioStream implements ChunkedInput<ByteBuf> {
    private final ByteBuffer byteBuffer;
    private final int chunkSize;
    private final ReadableByteChannel in;
    private long offset;

    public ChunkedNioStream(ReadableByteChannel in2) {
        this(in2, 8192);
    }

    public ChunkedNioStream(ReadableByteChannel in2, int chunkSize2) {
        if (in2 == null) {
            throw new NullPointerException("in");
        } else if (chunkSize2 > 0) {
            this.in = in2;
            this.offset = 0;
            this.chunkSize = chunkSize2;
            this.byteBuffer = ByteBuffer.allocate(chunkSize2);
        } else {
            throw new IllegalArgumentException("chunkSize: " + chunkSize2 + " (expected: a positive integer)");
        }
    }

    public long transferredBytes() {
        return this.offset;
    }

    public boolean isEndOfInput() {
        int b;
        if (this.byteBuffer.position() > 0) {
            return false;
        }
        if (!this.in.isOpen() || (b = this.in.read(this.byteBuffer)) < 0) {
            return true;
        }
        this.offset += (long) b;
        return false;
    }

    public void close() {
        this.in.close();
    }

    public ByteBuf readChunk(ChannelHandlerContext ctx) {
        if (isEndOfInput()) {
            return null;
        }
        int readBytes = this.byteBuffer.position();
        do {
            int localReadBytes = this.in.read(this.byteBuffer);
            if (localReadBytes < 0) {
                break;
            }
            readBytes += localReadBytes;
            this.offset += (long) localReadBytes;
        } while (readBytes != this.chunkSize);
        this.byteBuffer.flip();
        boolean release = true;
        ByteBuf buffer = ctx.alloc().buffer(this.byteBuffer.remaining());
        try {
            buffer.writeBytes(this.byteBuffer);
            this.byteBuffer.clear();
            release = false;
            return buffer;
        } finally {
            if (release) {
                buffer.release();
            }
        }
    }
}
