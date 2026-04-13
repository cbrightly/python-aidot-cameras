package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;
import java.nio.ByteBuffer;

public class UnpooledUnsafeNoCleanerDirectByteBuf extends UnpooledUnsafeDirectByteBuf {
    UnpooledUnsafeNoCleanerDirectByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
        super(alloc, initialCapacity, maxCapacity);
    }

    /* access modifiers changed from: protected */
    public ByteBuffer allocateDirect(int initialCapacity) {
        return PlatformDependent.allocateDirectNoCleaner(initialCapacity);
    }

    /* access modifiers changed from: package-private */
    public ByteBuffer reallocateDirect(ByteBuffer oldBuffer, int initialCapacity) {
        return PlatformDependent.reallocateDirectNoCleaner(oldBuffer, initialCapacity);
    }

    /* access modifiers changed from: protected */
    public void freeDirect(ByteBuffer buffer) {
        PlatformDependent.freeDirectNoCleaner(buffer);
    }

    public ByteBuf capacity(int newCapacity) {
        checkNewCapacity(newCapacity);
        int oldCapacity = capacity();
        if (newCapacity == oldCapacity) {
            return this;
        }
        ByteBuffer newBuffer = reallocateDirect(this.buffer, newCapacity);
        if (newCapacity < oldCapacity) {
            if (readerIndex() >= newCapacity) {
                setIndex(newCapacity, newCapacity);
            } else if (writerIndex() > newCapacity) {
                writerIndex(newCapacity);
            }
        }
        setByteBuffer(newBuffer, false);
        return this;
    }
}
