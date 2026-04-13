package io.netty.util.internal.shaded.org.jctools.queues;

public interface QueueProgressIndicators {
    long currentConsumerIndex();

    long currentProducerIndex();
}
