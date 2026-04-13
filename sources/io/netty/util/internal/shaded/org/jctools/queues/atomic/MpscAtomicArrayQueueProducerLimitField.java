package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/* compiled from: MpscAtomicArrayQueue */
public abstract class MpscAtomicArrayQueueProducerLimitField<E> extends MpscAtomicArrayQueueMidPad<E> {
    private static final AtomicLongFieldUpdater<MpscAtomicArrayQueueProducerLimitField> P_LIMIT_UPDATER = AtomicLongFieldUpdater.newUpdater(MpscAtomicArrayQueueProducerLimitField.class, "producerLimit");
    private volatile long producerLimit;

    public MpscAtomicArrayQueueProducerLimitField(int capacity) {
        super(capacity);
        this.producerLimit = (long) capacity;
    }

    /* access modifiers changed from: protected */
    public final long lvProducerLimit() {
        return this.producerLimit;
    }

    /* access modifiers changed from: protected */
    public final void soProducerLimit(long newValue) {
        P_LIMIT_UPDATER.lazySet(this, newValue);
    }
}
