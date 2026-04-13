package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

/* compiled from: MpscArrayQueue */
public abstract class MpscArrayQueueConsumerIndexField<E> extends MpscArrayQueueL2Pad<E> {
    private static final long C_INDEX_OFFSET;
    protected long consumerIndex;

    static {
        try {
            C_INDEX_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(MpscArrayQueueConsumerIndexField.class.getDeclaredField("consumerIndex"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public MpscArrayQueueConsumerIndexField(int capacity) {
        super(capacity);
    }

    /* access modifiers changed from: protected */
    public final long lpConsumerIndex() {
        return this.consumerIndex;
    }

    public final long lvConsumerIndex() {
        return UnsafeAccess.UNSAFE.getLongVolatile(this, C_INDEX_OFFSET);
    }

    /* access modifiers changed from: protected */
    public void soConsumerIndex(long newValue) {
        UnsafeAccess.UNSAFE.putOrderedLong(this, C_INDEX_OFFSET, newValue);
    }
}
