package io.netty.buffer;

public interface ByteBufAllocatorMetric {
    long usedDirectMemory();

    long usedHeapMemory();
}
