package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;

/* compiled from: BaseMpscLinkedAtomicArrayQueue */
public abstract class BaseMpscLinkedAtomicArrayQueueColdProducerFields<E> extends BaseMpscLinkedAtomicArrayQueuePad3<E> {
    private static final AtomicLongFieldUpdater<BaseMpscLinkedAtomicArrayQueueColdProducerFields> P_LIMIT_UPDATER = AtomicLongFieldUpdater.newUpdater(BaseMpscLinkedAtomicArrayQueueColdProducerFields.class, "producerLimit");
    protected AtomicReferenceArray<E> producerBuffer;
    protected volatile long producerLimit;
    protected long producerMask;

    BaseMpscLinkedAtomicArrayQueueColdProducerFields() {
    }

    /* access modifiers changed from: package-private */
    public final long lvProducerLimit() {
        return this.producerLimit;
    }

    /* access modifiers changed from: package-private */
    public final boolean casProducerLimit(long expect, long newValue) {
        return P_LIMIT_UPDATER.compareAndSet(this, expect, newValue);
    }

    /* access modifiers changed from: package-private */
    public final void soProducerLimit(long newValue) {
        P_LIMIT_UPDATER.lazySet(this, newValue);
    }
}
