package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

/* compiled from: MpscArrayQueue */
public abstract class MpscArrayQueueProducerLimitField<E> extends MpscArrayQueueMidPad<E> {
    private static final long P_LIMIT_OFFSET;
    private volatile long producerLimit;

    static {
        try {
            P_LIMIT_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(MpscArrayQueueProducerLimitField.class.getDeclaredField("producerLimit"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public MpscArrayQueueProducerLimitField(int capacity) {
        super(capacity);
        this.producerLimit = (long) capacity;
    }

    /* access modifiers changed from: protected */
    public final long lvProducerLimit() {
        return this.producerLimit;
    }

    /* access modifiers changed from: protected */
    public final void soProducerLimit(long newValue) {
        UnsafeAccess.UNSAFE.putOrderedLong(this, P_LIMIT_OFFSET, newValue);
    }
}
