package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import io.netty.util.internal.shaded.org.jctools.queues.IndexedQueueSizeUtil;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import io.netty.util.internal.shaded.org.jctools.queues.QueueProgressIndicators;
import io.netty.util.internal.shaded.org.jctools.util.Pow2;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReferenceArray;

public abstract class AtomicReferenceArrayQueue<E> extends AbstractQueue<E> implements IndexedQueueSizeUtil.IndexedQueue, QueueProgressIndicators, MessagePassingQueue<E> {
    protected final AtomicReferenceArray<E> buffer;
    protected final int mask;

    public AtomicReferenceArrayQueue(int capacity) {
        int actualCapacity = Pow2.roundToPowerOfTwo(capacity);
        this.mask = actualCapacity - 1;
        this.buffer = new AtomicReferenceArray<>(actualCapacity);
    }

    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return getClass().getName();
    }

    public void clear() {
        do {
        } while (poll() != null);
    }

    /* access modifiers changed from: protected */
    public final int calcElementOffset(long index, int mask2) {
        return ((int) index) & mask2;
    }

    /* access modifiers changed from: protected */
    public final int calcElementOffset(long index) {
        return ((int) index) & this.mask;
    }

    public static <E> E lvElement(AtomicReferenceArray<E> buffer2, int offset) {
        return buffer2.get(offset);
    }

    public static <E> E lpElement(AtomicReferenceArray<E> buffer2, int offset) {
        return buffer2.get(offset);
    }

    /* access modifiers changed from: protected */
    public final E lpElement(int offset) {
        return this.buffer.get(offset);
    }

    public static <E> void spElement(AtomicReferenceArray<E> buffer2, int offset, E value) {
        buffer2.lazySet(offset, value);
    }

    /* access modifiers changed from: protected */
    public final void spElement(int offset, E value) {
        this.buffer.lazySet(offset, value);
    }

    public static <E> void soElement(AtomicReferenceArray<E> buffer2, int offset, E value) {
        buffer2.lazySet(offset, value);
    }

    /* access modifiers changed from: protected */
    public final void soElement(int offset, E value) {
        this.buffer.lazySet(offset, value);
    }

    public static <E> void svElement(AtomicReferenceArray<E> buffer2, int offset, E value) {
        buffer2.set(offset, value);
    }

    /* access modifiers changed from: protected */
    public final E lvElement(int offset) {
        return lvElement(this.buffer, offset);
    }

    public final int capacity() {
        return this.mask + 1;
    }

    public final int size() {
        return IndexedQueueSizeUtil.size(this);
    }

    public final boolean isEmpty() {
        return IndexedQueueSizeUtil.isEmpty(this);
    }

    public final long currentProducerIndex() {
        return lvProducerIndex();
    }

    public final long currentConsumerIndex() {
        return lvConsumerIndex();
    }
}
