package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* compiled from: BaseLinkedAtomicQueue */
public abstract class BaseLinkedAtomicQueueConsumerNodeRef<E> extends BaseLinkedAtomicQueuePad1<E> {
    private static final AtomicReferenceFieldUpdater<BaseLinkedAtomicQueueConsumerNodeRef, LinkedQueueAtomicNode> C_NODE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(BaseLinkedAtomicQueueConsumerNodeRef.class, LinkedQueueAtomicNode.class, "consumerNode");
    protected volatile LinkedQueueAtomicNode<E> consumerNode;

    BaseLinkedAtomicQueueConsumerNodeRef() {
    }

    /* access modifiers changed from: protected */
    public final void spConsumerNode(LinkedQueueAtomicNode<E> newValue) {
        C_NODE_UPDATER.lazySet(this, newValue);
    }

    /* access modifiers changed from: protected */
    public final LinkedQueueAtomicNode<E> lvConsumerNode() {
        return this.consumerNode;
    }

    /* access modifiers changed from: protected */
    public final LinkedQueueAtomicNode<E> lpConsumerNode() {
        return this.consumerNode;
    }
}
