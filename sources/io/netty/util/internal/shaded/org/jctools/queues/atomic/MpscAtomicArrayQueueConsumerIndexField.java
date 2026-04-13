package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/* compiled from: MpscAtomicArrayQueue */
public abstract class MpscAtomicArrayQueueConsumerIndexField<E> extends MpscAtomicArrayQueueL2Pad<E> {
    private static final AtomicLongFieldUpdater<MpscAtomicArrayQueueConsumerIndexField> C_INDEX_UPDATER = AtomicLongFieldUpdater.newUpdater(MpscAtomicArrayQueueConsumerIndexField.class, "consumerIndex");
    protected volatile long consumerIndex;

    public MpscAtomicArrayQueueConsumerIndexField(int capacity) {
        super(capacity);
    }

    /* access modifiers changed from: protected */
    public final long lpConsumerIndex() {
        return this.consumerIndex;
    }

    public final long lvConsumerIndex() {
        return this.consumerIndex;
    }

    /* access modifiers changed from: protected */
    public void soConsumerIndex(long newValue) {
        C_INDEX_UPDATER.lazySet(this, newValue);
    }
}
