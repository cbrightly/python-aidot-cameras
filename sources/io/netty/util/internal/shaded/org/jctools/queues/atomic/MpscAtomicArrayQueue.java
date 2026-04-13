package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import io.netty.util.internal.shaded.org.jctools.util.PortableJvmInfo;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class MpscAtomicArrayQueue<E> extends MpscAtomicArrayQueueL3Pad<E> {
    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    public /* bridge */ /* synthetic */ Iterator iterator() {
        return super.iterator();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public MpscAtomicArrayQueue(int capacity) {
        super(capacity);
    }

    public boolean offerIfBelowThreshold(E e, int threshold) {
        long pIndex;
        E e2 = e;
        int i = threshold;
        if (e2 != null) {
            int mask = this.mask;
            long capacity = (long) (mask + 1);
            long producerLimit = lvProducerLimit();
            do {
                pIndex = lvProducerIndex();
                if (capacity - (producerLimit - pIndex) >= ((long) i)) {
                    long cIndex = lvConsumerIndex();
                    long j = producerLimit;
                    if (pIndex - cIndex >= ((long) i)) {
                        return false;
                    }
                    producerLimit = cIndex + capacity;
                    soProducerLimit(producerLimit);
                } else {
                    long j2 = producerLimit;
                }
            } while (!casProducerIndex(pIndex, 1 + pIndex));
            AtomicReferenceArrayQueue.soElement(this.buffer, calcElementOffset(pIndex, mask), e2);
            return true;
        }
        throw new NullPointerException();
    }

    public boolean offer(E e) {
        long pIndex;
        if (e != null) {
            int mask = this.mask;
            long producerLimit = lvProducerLimit();
            do {
                pIndex = lvProducerIndex();
                if (pIndex >= producerLimit) {
                    long producerLimit2 = ((long) mask) + lvConsumerIndex() + 1;
                    if (pIndex >= producerLimit2) {
                        return false;
                    }
                    soProducerLimit(producerLimit2);
                    producerLimit = producerLimit2;
                }
            } while (!casProducerIndex(pIndex, 1 + pIndex));
            AtomicReferenceArrayQueue.soElement(this.buffer, calcElementOffset(pIndex, mask), e);
            return true;
        }
        throw new NullPointerException();
    }

    public final int failFastOffer(E e) {
        if (e != null) {
            int mask = this.mask;
            long capacity = (long) (mask + 1);
            long pIndex = lvProducerIndex();
            if (pIndex >= lvProducerLimit()) {
                long producerLimit = lvConsumerIndex() + capacity;
                if (pIndex >= producerLimit) {
                    return 1;
                }
                soProducerLimit(producerLimit);
            }
            if (!casProducerIndex(pIndex, 1 + pIndex)) {
                return -1;
            }
            AtomicReferenceArrayQueue.soElement(this.buffer, calcElementOffset(pIndex, mask), e);
            return 0;
        }
        throw new NullPointerException();
    }

    public E poll() {
        long cIndex = lpConsumerIndex();
        int offset = calcElementOffset(cIndex);
        AtomicReferenceArray<E> buffer = this.buffer;
        E e = AtomicReferenceArrayQueue.lvElement(buffer, offset);
        if (e != null) {
            AtomicReferenceArrayQueue.spElement(buffer, offset, null);
            soConsumerIndex(1 + cIndex);
        } else if (cIndex == lvProducerIndex()) {
            return null;
        } else {
            do {
                e = AtomicReferenceArrayQueue.lvElement(buffer, offset);
            } while (e == null);
        }
        AtomicReferenceArrayQueue.spElement(buffer, offset, null);
        soConsumerIndex(1 + cIndex);
        return e;
    }

    public E peek() {
        AtomicReferenceArray<E> buffer = this.buffer;
        long cIndex = lpConsumerIndex();
        int offset = calcElementOffset(cIndex);
        E e = AtomicReferenceArrayQueue.lvElement(buffer, offset);
        if (e != null) {
            return e;
        }
        if (cIndex == lvProducerIndex()) {
            return null;
        }
        do {
            e = AtomicReferenceArrayQueue.lvElement(buffer, offset);
        } while (e == null);
        return e;
    }

    public boolean relaxedOffer(E e) {
        return offer(e);
    }

    public E relaxedPoll() {
        AtomicReferenceArray<E> buffer = this.buffer;
        long cIndex = lpConsumerIndex();
        int offset = calcElementOffset(cIndex);
        E e = AtomicReferenceArrayQueue.lvElement(buffer, offset);
        if (e == null) {
            return null;
        }
        AtomicReferenceArrayQueue.spElement(buffer, offset, null);
        soConsumerIndex(1 + cIndex);
        return e;
    }

    public E relaxedPeek() {
        return AtomicReferenceArrayQueue.lvElement(this.buffer, calcElementOffset(lpConsumerIndex(), this.mask));
    }

    public int drain(MessagePassingQueue.Consumer<E> c) {
        return drain(c, capacity());
    }

    public int fill(MessagePassingQueue.Supplier<E> s) {
        long result = 0;
        int capacity = capacity();
        do {
            int filled = fill(s, PortableJvmInfo.RECOMENDED_OFFER_BATCH);
            if (filled == 0) {
                return (int) result;
            }
            result += (long) filled;
        } while (result <= ((long) capacity));
        return (int) result;
    }

    public int drain(MessagePassingQueue.Consumer<E> c, int limit) {
        AtomicReferenceArray<E> buffer = this.buffer;
        int mask = this.mask;
        long cIndex = lpConsumerIndex();
        for (int i = 0; i < limit; i++) {
            long index = ((long) i) + cIndex;
            int offset = calcElementOffset(index, mask);
            E e = AtomicReferenceArrayQueue.lvElement(buffer, offset);
            if (e == null) {
                return i;
            }
            AtomicReferenceArrayQueue.spElement(buffer, offset, null);
            soConsumerIndex(1 + index);
            c.accept(e);
        }
        return limit;
    }

    public int fill(MessagePassingQueue.Supplier<E> s, int limit) {
        long pIndex;
        int actualLimit;
        int mask = this.mask;
        long capacity = (long) (mask + 1);
        long producerLimit = lvProducerLimit();
        do {
            pIndex = lvProducerIndex();
            long available = producerLimit - pIndex;
            if (available <= 0) {
                producerLimit = lvConsumerIndex() + capacity;
                available = producerLimit - pIndex;
                if (available <= 0) {
                    return 0;
                }
                soProducerLimit(producerLimit);
            }
            actualLimit = Math.min((int) available, limit);
        } while (!casProducerIndex(pIndex, ((long) actualLimit) + pIndex));
        AtomicReferenceArray<E> buffer = this.buffer;
        for (int i = 0; i < actualLimit; i++) {
            AtomicReferenceArrayQueue.soElement(buffer, calcElementOffset(((long) i) + pIndex, mask), s.get());
        }
        return actualLimit;
    }

    public void drain(MessagePassingQueue.Consumer<E> c, MessagePassingQueue.WaitStrategy w, MessagePassingQueue.ExitCondition exit) {
        AtomicReferenceArray<E> buffer = this.buffer;
        int mask = this.mask;
        long cIndex = lpConsumerIndex();
        int counter = 0;
        while (exit.keepRunning()) {
            for (int i = 0; i < 4096; i++) {
                int offset = calcElementOffset(cIndex, mask);
                E e = AtomicReferenceArrayQueue.lvElement(buffer, offset);
                if (e == null) {
                    counter = w.idle(counter);
                } else {
                    cIndex++;
                    counter = 0;
                    AtomicReferenceArrayQueue.spElement(buffer, offset, null);
                    soConsumerIndex(cIndex);
                    c.accept(e);
                }
            }
        }
    }

    public void fill(MessagePassingQueue.Supplier<E> s, MessagePassingQueue.WaitStrategy w, MessagePassingQueue.ExitCondition exit) {
        int idleCounter = 0;
        while (exit.keepRunning()) {
            if (fill(s, PortableJvmInfo.RECOMENDED_OFFER_BATCH) == 0) {
                idleCounter = w.idle(idleCounter);
            } else {
                idleCounter = 0;
            }
        }
    }

    @Deprecated
    public int weakOffer(E e) {
        return failFastOffer(e);
    }
}
