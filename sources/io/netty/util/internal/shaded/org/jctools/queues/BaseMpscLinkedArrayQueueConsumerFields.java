package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

/* compiled from: BaseMpscLinkedArrayQueue */
public abstract class BaseMpscLinkedArrayQueueConsumerFields<E> extends BaseMpscLinkedArrayQueuePad2<E> {
    private static final long C_INDEX_OFFSET;
    protected E[] consumerBuffer;
    protected long consumerIndex;
    protected long consumerMask;

    BaseMpscLinkedArrayQueueConsumerFields() {
    }

    static {
        try {
            C_INDEX_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(BaseMpscLinkedArrayQueueConsumerFields.class.getDeclaredField("consumerIndex"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public final long lvConsumerIndex() {
        return UnsafeAccess.UNSAFE.getLongVolatile(this, C_INDEX_OFFSET);
    }

    /* access modifiers changed from: package-private */
    public final void soConsumerIndex(long newValue) {
        UnsafeAccess.UNSAFE.putOrderedLong(this, C_INDEX_OFFSET, newValue);
    }
}
