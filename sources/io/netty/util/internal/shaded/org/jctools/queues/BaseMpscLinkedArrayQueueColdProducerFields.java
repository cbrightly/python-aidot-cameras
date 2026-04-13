package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

/* compiled from: BaseMpscLinkedArrayQueue */
public abstract class BaseMpscLinkedArrayQueueColdProducerFields<E> extends BaseMpscLinkedArrayQueuePad3<E> {
    private static final long P_LIMIT_OFFSET;
    protected E[] producerBuffer;
    private volatile long producerLimit;
    protected long producerMask;

    BaseMpscLinkedArrayQueueColdProducerFields() {
    }

    static {
        try {
            P_LIMIT_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(BaseMpscLinkedArrayQueueColdProducerFields.class.getDeclaredField("producerLimit"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: package-private */
    public final long lvProducerLimit() {
        return this.producerLimit;
    }

    /* access modifiers changed from: package-private */
    public final boolean casProducerLimit(long expect, long newValue) {
        return UnsafeAccess.UNSAFE.compareAndSwapLong(this, P_LIMIT_OFFSET, expect, newValue);
    }

    /* access modifiers changed from: package-private */
    public final void soProducerLimit(long newValue) {
        UnsafeAccess.UNSAFE.putOrderedLong(this, P_LIMIT_OFFSET, newValue);
    }
}
