package org.glassfish.grizzly.memory;

import java.nio.ByteBuffer;

public class MemoryUtils {
    public static ByteBuffer allocateByteBuffer(MemoryManager memoryManager, int size) {
        if (memoryManager instanceof ByteBufferAware) {
            return ((ByteBufferAware) memoryManager).allocateByteBuffer(size);
        }
        return ByteBuffer.allocate(size);
    }

    public static ByteBuffer reallocateByteBuffer(MemoryManager memoryManager, ByteBuffer oldByteBuffer, int size) {
        if (memoryManager instanceof ByteBufferAware) {
            return ((ByteBufferAware) memoryManager).reallocateByteBuffer(oldByteBuffer, size);
        }
        return ByteBuffer.allocate(size);
    }

    public static void releaseByteBuffer(MemoryManager memoryManager, ByteBuffer byteBuffer) {
        if (memoryManager instanceof ByteBufferAware) {
            ((ByteBufferAware) memoryManager).releaseByteBuffer(byteBuffer);
        }
    }
}
