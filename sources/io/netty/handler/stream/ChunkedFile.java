package io.netty.handler.stream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.io.File;
import java.io.RandomAccessFile;

public class ChunkedFile implements ChunkedInput<ByteBuf> {
    private final int chunkSize;
    private final long endOffset;
    private final RandomAccessFile file;
    private long offset;
    private final long startOffset;

    public ChunkedFile(File file2) {
        this(file2, 8192);
    }

    public ChunkedFile(File file2, int chunkSize2) {
        this(new RandomAccessFile(file2, "r"), chunkSize2);
    }

    public ChunkedFile(RandomAccessFile file2) {
        this(file2, 8192);
    }

    public ChunkedFile(RandomAccessFile file2, int chunkSize2) {
        this(file2, 0, file2.length(), chunkSize2);
    }

    public ChunkedFile(RandomAccessFile file2, long offset2, long length, int chunkSize2) {
        if (file2 == null) {
            throw new NullPointerException("file");
        } else if (offset2 < 0) {
            throw new IllegalArgumentException("offset: " + offset2 + " (expected: 0 or greater)");
        } else if (length < 0) {
            throw new IllegalArgumentException("length: " + length + " (expected: 0 or greater)");
        } else if (chunkSize2 > 0) {
            this.file = file2;
            this.startOffset = offset2;
            this.offset = offset2;
            this.endOffset = offset2 + length;
            this.chunkSize = chunkSize2;
            file2.seek(offset2);
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
        return this.offset >= this.endOffset || !this.file.getChannel().isOpen();
    }

    public void close() {
        this.file.close();
    }

    public ByteBuf readChunk(ChannelHandlerContext ctx) {
        long offset2 = this.offset;
        long j = this.endOffset;
        if (offset2 >= j) {
            return null;
        }
        int chunkSize2 = (int) Math.min((long) this.chunkSize, j - offset2);
        ByteBuf buf = ctx.alloc().heapBuffer(chunkSize2);
        boolean release = true;
        try {
            this.file.readFully(buf.array(), buf.arrayOffset(), chunkSize2);
            buf.writerIndex(chunkSize2);
            this.offset = ((long) chunkSize2) + offset2;
            release = false;
            return buf;
        } finally {
            if (release) {
                buf.release();
            }
        }
    }
}
