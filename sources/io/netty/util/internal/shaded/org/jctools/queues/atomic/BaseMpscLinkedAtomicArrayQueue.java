package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import io.netty.util.internal.shaded.org.jctools.queues.QueueProgressIndicators;
import io.netty.util.internal.shaded.org.jctools.util.PortableJvmInfo;
import io.netty.util.internal.shaded.org.jctools.util.Pow2;
import io.netty.util.internal.shaded.org.jctools.util.RangeUtil;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReferenceArray;

public abstract class BaseMpscLinkedAtomicArrayQueue<E> extends BaseMpscLinkedAtomicArrayQueueColdProducerFields<E> implements MessagePassingQueue<E>, QueueProgressIndicators {
    private static final Object JUMP = new Object();

    /* access modifiers changed from: protected */
    public abstract long availableInQueue(long j, long j2);

    public abstract int capacity();

    /* access modifiers changed from: protected */
    public abstract long getCurrentBufferCapacity(long j);

    /* access modifiers changed from: protected */
    public abstract int getNextBufferSize(AtomicReferenceArray<E> atomicReferenceArray);

    public BaseMpscLinkedAtomicArrayQueue(int initialCapacity) {
        RangeUtil.checkGreaterThanOrEqual(initialCapacity, 2, "initialCapacity");
        int p2capacity = Pow2.roundToPowerOfTwo(initialCapacity);
        long mask = (long) ((p2capacity - 1) << 1);
        AtomicReferenceArray<E> buffer = LinkedAtomicArrayQueueUtil.allocate(p2capacity + 1);
        this.producerBuffer = buffer;
        this.producerMask = mask;
        this.consumerBuffer = buffer;
        this.consumerMask = mask;
        soProducerLimit(mask);
    }

    public final Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    public final int size() {
        long before;
        long currentProducerIndex;
        long after = lvConsumerIndex();
        do {
            before = after;
            currentProducerIndex = lvProducerIndex();
            after = lvConsumerIndex();
        } while (before != after);
        long size = (currentProducerIndex - after) >> 1;
        if (size > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) size;
    }

    public final boolean isEmpty() {
        return lvConsumerIndex() == lvProducerIndex();
    }

    public String toString() {
        return getClass().getName();
    }

    public boolean offer(E e) {
        E e2 = e;
        if (e2 != null) {
            while (true) {
                long producerLimit = lvProducerLimit();
                long pIndex = lvProducerIndex();
                if ((pIndex & 1) != 1) {
                    long mask = this.producerMask;
                    AtomicReferenceArray<E> buffer = this.producerBuffer;
                    if (producerLimit <= pIndex) {
                        switch (offerSlowPath(mask, pIndex, producerLimit)) {
                            case 1:
                                break;
                            case 2:
                                return false;
                            case 3:
                                resize(mask, buffer, pIndex, e);
                                return true;
                        }
                    }
                    if (casProducerIndex(pIndex, 2 + pIndex)) {
                        LinkedAtomicArrayQueueUtil.soElement(buffer, LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(pIndex, mask), e2);
                        return true;
                    }
                }
            }
        } else {
            throw new NullPointerException();
        }
    }

    public E poll() {
        AtomicReferenceArray<E> buffer = this.consumerBuffer;
        long index = this.consumerIndex;
        long mask = this.consumerMask;
        int offset = LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(index, mask);
        Object e = LinkedAtomicArrayQueueUtil.lvElement(buffer, offset);
        if (e == null) {
            if (index == lvProducerIndex()) {
                return null;
            }
            do {
                e = LinkedAtomicArrayQueueUtil.lvElement(buffer, offset);
            } while (e == null);
        }
        if (e == JUMP) {
            return newBufferPoll(getNextBuffer(buffer, mask), index);
        }
        LinkedAtomicArrayQueueUtil.soElement(buffer, offset, (Object) null);
        soConsumerIndex(2 + index);
        return e;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public E peek() {
        /*
            r9 = this;
            java.util.concurrent.atomic.AtomicReferenceArray<E> r0 = r9.consumerBuffer
            long r1 = r9.consumerIndex
            long r3 = r9.consumerMask
            int r5 = io.netty.util.internal.shaded.org.jctools.queues.atomic.LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(r1, r3)
            java.lang.Object r6 = io.netty.util.internal.shaded.org.jctools.queues.atomic.LinkedAtomicArrayQueueUtil.lvElement(r0, r5)
            if (r6 != 0) goto L_0x0020
            long r7 = r9.lvProducerIndex()
            int r7 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1))
            if (r7 == 0) goto L_0x0020
        L_0x0018:
            java.lang.Object r7 = io.netty.util.internal.shaded.org.jctools.queues.atomic.LinkedAtomicArrayQueueUtil.lvElement(r0, r5)
            r6 = r7
            if (r7 != 0) goto L_0x0020
            goto L_0x0018
        L_0x0020:
            java.lang.Object r7 = JUMP
            if (r6 != r7) goto L_0x002d
            java.util.concurrent.atomic.AtomicReferenceArray r7 = r9.getNextBuffer(r0, r3)
            java.lang.Object r7 = r9.newBufferPeek(r7, r1)
            return r7
        L_0x002d:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.shaded.org.jctools.queues.atomic.BaseMpscLinkedAtomicArrayQueue.peek():java.lang.Object");
    }

    private int offerSlowPath(long mask, long pIndex, long producerLimit) {
        long cIndex = lvConsumerIndex();
        long bufferCapacity = getCurrentBufferCapacity(mask);
        if (cIndex + bufferCapacity > pIndex) {
            if (!casProducerLimit(producerLimit, cIndex + bufferCapacity)) {
                return 1;
            }
            return 0;
        } else if (availableInQueue(pIndex, cIndex) <= 0) {
            return 2;
        } else {
            if (casProducerIndex(pIndex, 1 + pIndex)) {
                return 3;
            }
            return 1;
        }
    }

    private AtomicReferenceArray<E> getNextBuffer(AtomicReferenceArray<E> buffer, long mask) {
        int offset = nextArrayOffset(mask);
        AtomicReferenceArray<E> nextBuffer = (AtomicReferenceArray) LinkedAtomicArrayQueueUtil.lvElement(buffer, offset);
        LinkedAtomicArrayQueueUtil.soElement(buffer, offset, (Object) null);
        return nextBuffer;
    }

    private int nextArrayOffset(long mask) {
        return LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(2 + mask, DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
    }

    private E newBufferPoll(AtomicReferenceArray<E> nextBuffer, long index) {
        int offset = newBufferAndOffset(nextBuffer, index);
        E n = LinkedAtomicArrayQueueUtil.lvElement(nextBuffer, offset);
        if (n != null) {
            LinkedAtomicArrayQueueUtil.soElement(nextBuffer, offset, (Object) null);
            soConsumerIndex(2 + index);
            return n;
        }
        throw new IllegalStateException("new buffer must have at least one element");
    }

    private E newBufferPeek(AtomicReferenceArray<E> nextBuffer, long index) {
        E n = LinkedAtomicArrayQueueUtil.lvElement(nextBuffer, newBufferAndOffset(nextBuffer, index));
        if (n != null) {
            return n;
        }
        throw new IllegalStateException("new buffer must have at least one element");
    }

    private int newBufferAndOffset(AtomicReferenceArray<E> nextBuffer, long index) {
        this.consumerBuffer = nextBuffer;
        long length = (long) ((LinkedAtomicArrayQueueUtil.length(nextBuffer) - 2) << 1);
        this.consumerMask = length;
        return LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(index, length);
    }

    public long currentProducerIndex() {
        return lvProducerIndex() / 2;
    }

    public long currentConsumerIndex() {
        return lvConsumerIndex() / 2;
    }

    public boolean relaxedOffer(E e) {
        return offer(e);
    }

    public E relaxedPoll() {
        AtomicReferenceArray<E> buffer = this.consumerBuffer;
        long index = this.consumerIndex;
        long mask = this.consumerMask;
        int offset = LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(index, mask);
        Object e = LinkedAtomicArrayQueueUtil.lvElement(buffer, offset);
        if (e == null) {
            return null;
        }
        if (e == JUMP) {
            return newBufferPoll(getNextBuffer(buffer, mask), index);
        }
        LinkedAtomicArrayQueueUtil.soElement(buffer, offset, (Object) null);
        soConsumerIndex(2 + index);
        return e;
    }

    public E relaxedPeek() {
        AtomicReferenceArray<E> buffer = this.consumerBuffer;
        long index = this.consumerIndex;
        long mask = this.consumerMask;
        Object e = LinkedAtomicArrayQueueUtil.lvElement(buffer, LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(index, mask));
        if (e == JUMP) {
            return newBufferPeek(getNextBuffer(buffer, mask), index);
        }
        return e;
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

    public int fill(MessagePassingQueue.Supplier<E> s, int batchSize) {
        long batchIndex;
        while (true) {
            long producerLimit = lvProducerLimit();
            long pIndex = lvProducerIndex();
            if ((pIndex & 1) != 1) {
                long mask = this.producerMask;
                AtomicReferenceArray<E> buffer = this.producerBuffer;
                long batchIndex2 = Math.min(producerLimit, ((long) (batchSize * 2)) + pIndex);
                if (pIndex == producerLimit || producerLimit < batchIndex2) {
                    batchIndex = batchIndex2;
                    switch (offerSlowPath(mask, pIndex, producerLimit)) {
                        case 1:
                            break;
                        case 2:
                            return 0;
                        case 3:
                            resize(mask, buffer, pIndex, s.get());
                            return 1;
                    }
                } else {
                    batchIndex = batchIndex2;
                }
                long batchIndex3 = batchIndex;
                if (casProducerIndex(pIndex, batchIndex3)) {
                    int claimedSlots = (int) ((batchIndex3 - pIndex) / 2);
                    for (int i = 0; i < claimedSlots; i++) {
                        LinkedAtomicArrayQueueUtil.soElement(buffer, LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(((long) (i * 2)) + pIndex, mask), s.get());
                    }
                    return claimedSlots;
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:2:0x0006 A[LOOP:1: B:2:0x0006->B:5:0x0012, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void fill(io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Supplier<E> r3, io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.WaitStrategy r4, io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.ExitCondition r5) {
        /*
            r2 = this;
        L_0x0000:
            boolean r0 = r5.keepRunning()
            if (r0 == 0) goto L_0x002a
        L_0x0006:
            int r0 = io.netty.util.internal.shaded.org.jctools.util.PortableJvmInfo.RECOMENDED_OFFER_BATCH
            int r0 = r2.fill(r3, r0)
            if (r0 == 0) goto L_0x0015
            boolean r0 = r5.keepRunning()
            if (r0 == 0) goto L_0x0015
            goto L_0x0006
        L_0x0015:
            r0 = 0
        L_0x0016:
            boolean r1 = r5.keepRunning()
            if (r1 == 0) goto L_0x0029
            int r1 = io.netty.util.internal.shaded.org.jctools.util.PortableJvmInfo.RECOMENDED_OFFER_BATCH
            int r1 = r2.fill(r3, r1)
            if (r1 != 0) goto L_0x0029
            int r0 = r4.idle(r0)
            goto L_0x0016
        L_0x0029:
            goto L_0x0000
        L_0x002a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.shaded.org.jctools.queues.atomic.BaseMpscLinkedAtomicArrayQueue.fill(io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue$Supplier, io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue$WaitStrategy, io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue$ExitCondition):void");
    }

    public int drain(MessagePassingQueue.Consumer<E> c) {
        return drain(c, capacity());
    }

    public int drain(MessagePassingQueue.Consumer<E> c, int limit) {
        int i = 0;
        while (i < limit) {
            E relaxedPoll = relaxedPoll();
            E m = relaxedPoll;
            if (relaxedPoll == null) {
                break;
            }
            c.accept(m);
            i++;
        }
        return i;
    }

    public void drain(MessagePassingQueue.Consumer<E> c, MessagePassingQueue.WaitStrategy w, MessagePassingQueue.ExitCondition exit) {
        int idleCounter = 0;
        while (exit.keepRunning()) {
            E e = relaxedPoll();
            if (e == null) {
                idleCounter = w.idle(idleCounter);
            } else {
                idleCounter = 0;
                c.accept(e);
            }
        }
    }

    private void resize(long oldMask, AtomicReferenceArray<E> oldBuffer, long pIndex, E e) {
        AtomicReferenceArray<E> atomicReferenceArray = oldBuffer;
        long j = pIndex;
        int newBufferLength = getNextBufferSize(atomicReferenceArray);
        AtomicReferenceArray<E> newBuffer = LinkedAtomicArrayQueueUtil.allocate(newBufferLength);
        this.producerBuffer = newBuffer;
        int newMask = (newBufferLength - 2) << 1;
        this.producerMask = (long) newMask;
        int offsetInOld = LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(j, oldMask);
        LinkedAtomicArrayQueueUtil.soElement(newBuffer, LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(j, (long) newMask), e);
        LinkedAtomicArrayQueueUtil.soElement(atomicReferenceArray, nextArrayOffset(oldMask), newBuffer);
        long availableInQueue = availableInQueue(j, lvConsumerIndex());
        int i = newBufferLength;
        RangeUtil.checkPositive(availableInQueue, "availableInQueue");
        AtomicReferenceArray<E> atomicReferenceArray2 = newBuffer;
        soProducerLimit(Math.min((long) newMask, availableInQueue) + j);
        soProducerIndex(2 + j);
        LinkedAtomicArrayQueueUtil.soElement(atomicReferenceArray, offsetInOld, JUMP);
    }
}
