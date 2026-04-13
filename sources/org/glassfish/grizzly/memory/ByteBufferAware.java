package org.glassfish.grizzly.memory;

import java.nio.ByteBuffer;

public interface ByteBufferAware {
    ByteBuffer allocateByteBuffer(int i);

    ByteBuffer allocateByteBufferAtLeast(int i);

    ByteBuffer reallocateByteBuffer(ByteBuffer byteBuffer, int i);

    void releaseByteBuffer(ByteBuffer byteBuffer);
}
