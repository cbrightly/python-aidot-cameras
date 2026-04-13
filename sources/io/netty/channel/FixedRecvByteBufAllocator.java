package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.RecvByteBufAllocator;

public class FixedRecvByteBufAllocator implements RecvByteBufAllocator {
    private final RecvByteBufAllocator.Handle handle;

    public static final class HandleImpl implements RecvByteBufAllocator.Handle {
        private final int bufferSize;

        HandleImpl(int bufferSize2) {
            this.bufferSize = bufferSize2;
        }

        public ByteBuf allocate(ByteBufAllocator alloc) {
            return alloc.ioBuffer(this.bufferSize);
        }

        public int guess() {
            return this.bufferSize;
        }

        public void record(int actualReadBytes) {
        }
    }

    public FixedRecvByteBufAllocator(int bufferSize) {
        if (bufferSize > 0) {
            this.handle = new HandleImpl(bufferSize);
            return;
        }
        throw new IllegalArgumentException("bufferSize must greater than 0: " + bufferSize);
    }

    public RecvByteBufAllocator.Handle newHandle() {
        return this.handle;
    }
}
