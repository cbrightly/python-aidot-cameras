package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/* compiled from: BaseMpscLinkedAtomicArrayQueue */
public abstract class BaseMpscLinkedAtomicArrayQueueProducerFields<E> extends BaseMpscLinkedAtomicArrayQueuePad1<E> {
    private static final AtomicLongFieldUpdater<BaseMpscLinkedAtomicArrayQueueProducerFields> P_INDEX_UPDATER = AtomicLongFieldUpdater.newUpdater(BaseMpscLinkedAtomicArrayQueueProducerFields.class, "producerIndex");
    protected volatile long producerIndex;

    BaseMpscLinkedAtomicArrayQueueProducerFields() {
    }

    public final long lvProducerIndex() {
        return this.producerIndex;
    }

    /* access modifiers changed from: package-private */
    public final void soProducerIndex(long newValue) {
        P_INDEX_UPDATER.lazySet(this, newValue);
    }

    /* access modifiers changed from: package-private */
    public final boolean casProducerIndex(long expect, long newValue) {
        return P_INDEX_UPDATER.compareAndSet(this, expect, newValue);
    }
}
