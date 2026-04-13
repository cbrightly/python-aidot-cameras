package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* compiled from: BaseLinkedAtomicQueue */
public abstract class BaseLinkedAtomicQueueProducerNodeRef<E> extends BaseLinkedAtomicQueuePad0<E> {
    private static final AtomicReferenceFieldUpdater<BaseLinkedAtomicQueueProducerNodeRef, LinkedQueueAtomicNode> P_NODE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(BaseLinkedAtomicQueueProducerNodeRef.class, LinkedQueueAtomicNode.class, "producerNode");
    protected volatile LinkedQueueAtomicNode<E> producerNode;

    BaseLinkedAtomicQueueProducerNodeRef() {
    }

    /* access modifiers changed from: protected */
    public final void spProducerNode(LinkedQueueAtomicNode<E> newValue) {
        P_NODE_UPDATER.lazySet(this, newValue);
    }

    /* access modifiers changed from: protected */
    public final LinkedQueueAtomicNode<E> lvProducerNode() {
        return this.producerNode;
    }

    /* access modifiers changed from: protected */
    public final boolean casProducerNode(LinkedQueueAtomicNode<E> expect, LinkedQueueAtomicNode<E> newValue) {
        return P_NODE_UPDATER.compareAndSet(this, expect, newValue);
    }

    /* access modifiers changed from: protected */
    public final LinkedQueueAtomicNode<E> lpProducerNode() {
        return this.producerNode;
    }

    /* access modifiers changed from: protected */
    public final LinkedQueueAtomicNode<E> xchgProducerNode(LinkedQueueAtomicNode<E> newValue) {
        return P_NODE_UPDATER.getAndSet(this, newValue);
    }
}
