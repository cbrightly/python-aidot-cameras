package org.glassfish.grizzly.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public interface TransferQueue<E> extends BlockingQueue<E> {
    int getWaitingConsumerCount();

    boolean hasWaitingConsumer();

    void transfer(E e);

    boolean tryTransfer(E e);

    boolean tryTransfer(E e, long j, TimeUnit timeUnit);
}
