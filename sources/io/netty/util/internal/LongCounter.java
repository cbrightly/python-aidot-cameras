package io.netty.util.internal;

public interface LongCounter {
    void add(long j);

    void decrement();

    void increment();

    long value();
}
