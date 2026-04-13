package io.netty.handler.stream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class ChunkedStream implements ChunkedInput<ByteBuf> {
    static final int DEFAULT_CHUNK_SIZE = 8192;
    private final int chunkSize;
    private boolean closed;
    private final PushbackInputStream in;
    private long offset;

    public ChunkedStream(InputStream in2) {
        this(in2, 8192);
    }

    public ChunkedStream(InputStream in2, int chunkSize2) {
        if (in2 == null) {
            throw new NullPointerException("in");
        } else if (chunkSize2 > 0) {
            if (in2 instanceof PushbackInputStream) {
                this.in = (PushbackInputStream) in2;
            } else {
                this.in = new PushbackInputStream(in2);
            }
            this.chunkSize = chunkSize2;
        } else {
            throw new IllegalArgumentException("chunkSize: " + chunkSize2 + " (expected: a positive integer)");
        }
    }

    public long transferredBytes() {
        return this.offset;
    }

    public boolean isEndOfInput() {
        int b;
        if (this.closed || (b = this.in.read()) < 0) {
            return true;
        }
        this.in.unread(b);
        return false;
    }

    public void close() {
        this.closed = true;
        this.in.close();
    }

    public ByteBuf readChunk(ChannelHandlerContext ctx) {
        int chunkSize2;
        if (isEndOfInput()) {
            return null;
        }
        if (this.in.available() <= 0) {
            chunkSize2 = this.chunkSize;
        } else {
            chunkSize2 = Math.min(this.chunkSize, this.in.available());
        }
        boolean release = true;
        ByteBuf buffer = ctx.alloc().buffer(chunkSize2);
        try {
            this.offset += (long) buffer.writeBytes((InputStream) this.in, chunkSize2);
            release = false;
            return buffer;
        } finally {
            if (release) {
                buffer.release();
            }
        }
    }
}
