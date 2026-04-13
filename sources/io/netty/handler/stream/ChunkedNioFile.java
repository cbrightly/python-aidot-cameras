package io.netty.handler.stream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.io.File;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.ScatteringByteChannel;

public class ChunkedNioFile implements ChunkedInput<ByteBuf> {
    private final int chunkSize;
    private final long endOffset;
    private final FileChannel in;
    private long offset;
    private final long startOffset;

    public ChunkedNioFile(File in2) {
        this(new FileInputStream(in2).getChannel());
    }

    public ChunkedNioFile(File in2, int chunkSize2) {
        this(new FileInputStream(in2).getChannel(), chunkSize2);
    }

    public ChunkedNioFile(FileChannel in2) {
        this(in2, 8192);
    }

    public ChunkedNioFile(FileChannel in2, int chunkSize2) {
        this(in2, 0, in2.size(), chunkSize2);
    }

    public ChunkedNioFile(FileChannel in2, long offset2, long length, int chunkSize2) {
        if (in2 == null) {
            throw new NullPointerException("in");
        } else if (offset2 < 0) {
            throw new IllegalArgumentException("offset: " + offset2 + " (expected: 0 or greater)");
        } else if (length < 0) {
            throw new IllegalArgumentException("length: " + length + " (expected: 0 or greater)");
        } else if (chunkSize2 > 0) {
            if (offset2 != 0) {
                in2.position(offset2);
            }
            this.in = in2;
            this.chunkSize = chunkSize2;
            this.startOffset = offset2;
            this.offset = offset2;
            this.endOffset = offset2 + length;
        } else {
            throw new IllegalArgumentException("chunkSize: " + chunkSize2 + " (expected: a positive integer)");
        }
    }

    public long startOffset() {
        return this.startOffset;
    }

    public long endOffset() {
        return this.endOffset;
    }

    public long currentOffset() {
        return this.offset;
    }

    public boolean isEndOfInput() {
        return this.offset >= this.endOffset || !this.in.isOpen();
    }

    public void close() {
        this.in.close();
    }

    public ByteBuf readChunk(ChannelHandlerContext ctx) {
        long offset2 = this.offset;
        long j = this.endOffset;
        if (offset2 >= j) {
            return null;
        }
        int chunkSize2 = (int) Math.min((long) this.chunkSize, j - offset2);
        ByteBuf buffer = ctx.alloc().buffer(chunkSize2);
        boolean release = true;
        int readBytes = 0;
        while (true) {
            try {
                int localReadBytes = buffer.writeBytes((ScatteringByteChannel) this.in, chunkSize2 - readBytes);
                if (localReadBytes >= 0) {
                    readBytes += localReadBytes;
                    if (readBytes == chunkSize2) {
                        break;
                    }
                } else {
                    break;
                }
            } finally {
                if (release) {
                    buffer.release();
                }
            }
        }
        this.offset += (long) readBytes;
        release = false;
        return buffer;
    }
}
