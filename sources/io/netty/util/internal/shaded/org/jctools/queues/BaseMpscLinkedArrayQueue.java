package io.netty.util.internal.shaded.org.jctools.queues;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import io.netty.util.internal.shaded.org.jctools.util.PortableJvmInfo;
import io.netty.util.internal.shaded.org.jctools.util.Pow2;
import io.netty.util.internal.shaded.org.jctools.util.RangeUtil;
import io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess;
import java.util.Iterator;

public abstract class BaseMpscLinkedArrayQueue<E> extends BaseMpscLinkedArrayQueueColdProducerFields<E> implements MessagePassingQueue<E>, QueueProgressIndicators {
    private static final int CONTINUE_TO_P_INDEX_CAS = 0;
    private static final Object JUMP = new Object();
    private static final int QUEUE_FULL = 2;
    private static final int QUEUE_RESIZE = 3;
    private static final int RETRY = 1;

    /* access modifiers changed from: protected */
    public abstract long availableInQueue(long j, long j2);

    public abstract int capacity();

    /* access modifiers changed from: protected */
    public abstract long getCurrentBufferCapacity(long j);

    /* access modifiers changed from: protected */
    public abstract int getNextBufferSize(E[] eArr);

    public BaseMpscLinkedArrayQueue(int initialCapacity) {
        RangeUtil.checkGreaterThanOrEqual(initialCapacity, 2, "initialCapacity");
        int p2capacity = Pow2.roundToPowerOfTwo(initialCapacity);
        long mask = (long) ((p2capacity - 1) << 1);
        E[] buffer = CircularArrayOffsetCalculator.allocate(p2capacity + 1);
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
                    E[] buffer = this.producerBuffer;
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
                        UnsafeRefArrayAccess.soElement(buffer, LinkedArrayQueueUtil.modifiedCalcElementOffset(pIndex, mask), e2);
                        return true;
                    }
                }
            }
        } else {
            throw new NullPointerException();
        }
    }

    public E poll() {
        E[] buffer = this.consumerBuffer;
        long index = this.consumerIndex;
        long mask = this.consumerMask;
        long offset = LinkedArrayQueueUtil.modifiedCalcElementOffset(index, mask);
        Object e = UnsafeRefArrayAccess.lvElement(buffer, offset);
        if (e == null) {
            if (index == lvProducerIndex()) {
                return null;
            }
            do {
                e = UnsafeRefArrayAccess.lvElement(buffer, offset);
            } while (e == null);
        }
        if (e == JUMP) {
            return newBufferPoll(getNextBuffer(buffer, mask), index);
        }
        UnsafeRefArrayAccess.soElement(buffer, offset, null);
        soConsumerIndex(2 + index);
        return e;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0022  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public E peek() {
        /*
            r10 = this;
            E[] r0 = r10.consumerBuffer
            long r1 = r10.consumerIndex
            long r3 = r10.consumerMask
            long r5 = io.netty.util.internal.shaded.org.jctools.queues.LinkedArrayQueueUtil.modifiedCalcElementOffset(r1, r3)
            java.lang.Object r7 = io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess.lvElement(r0, r5)
            if (r7 != 0) goto L_0x001e
            long r8 = r10.lvProducerIndex()
            int r8 = (r1 > r8 ? 1 : (r1 == r8 ? 0 : -1))
            if (r8 == 0) goto L_0x001e
        L_0x0018:
            java.lang.Object r7 = io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess.lvElement(r0, r5)
            if (r7 == 0) goto L_0x0018
        L_0x001e:
            java.lang.Object r8 = JUMP
            if (r7 != r8) goto L_0x002b
            java.lang.Object[] r8 = r10.getNextBuffer(r0, r3)
            java.lang.Object r8 = r10.newBufferPeek(r8, r1)
            return r8
        L_0x002b:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.shaded.org.jctools.queues.BaseMpscLinkedArrayQueue.peek():java.lang.Object");
    }

    private int offerSlowPath(long mask, long pIndex, long producerLimit) {
        long j = pIndex;
        long cIndex = lvConsumerIndex();
        long bufferCapacity = getCurrentBufferCapacity(mask);
        if (cIndex + bufferCapacity > j) {
            if (!casProducerLimit(producerLimit, cIndex + bufferCapacity)) {
                return 1;
            }
            return 0;
        }
        long j2 = producerLimit;
        if (availableInQueue(j, cIndex) <= 0) {
            return 2;
        }
        if (casProducerIndex(j, 1 + j)) {
            return 3;
        }
        return 1;
    }

    private E[] getNextBuffer(E[] buffer, long mask) {
        long offset = nextArrayOffset(mask);
        E[] nextBuffer = (Object[]) UnsafeRefArrayAccess.lvElement(buffer, offset);
        UnsafeRefArrayAccess.soElement(buffer, offset, null);
        return nextBuffer;
    }

    private long nextArrayOffset(long mask) {
        return LinkedArrayQueueUtil.modifiedCalcElementOffset(2 + mask, DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
    }

    private E newBufferPoll(E[] nextBuffer, long index) {
        long offset = newBufferAndOffset(nextBuffer, index);
        E n = UnsafeRefArrayAccess.lvElement(nextBuffer, offset);
        if (n != null) {
            UnsafeRefArrayAccess.soElement(nextBuffer, offset, null);
            soConsumerIndex(2 + index);
            return n;
        }
        throw new IllegalStateException("new buffer must have at least one element");
    }

    private E newBufferPeek(E[] nextBuffer, long index) {
        E n = UnsafeRefArrayAccess.lvElement(nextBuffer, newBufferAndOffset(nextBuffer, index));
        if (n != null) {
            return n;
        }
        throw new IllegalStateException("new buffer must have at least one element");
    }

    private long newBufferAndOffset(E[] nextBuffer, long index) {
        this.consumerBuffer = nextBuffer;
        long length = (long) ((LinkedArrayQueueUtil.length(nextBuffer) - 2) << 1);
        this.consumerMask = length;
        return LinkedArrayQueueUtil.modifiedCalcElementOffset(index, length);
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
        E[] buffer = this.consumerBuffer;
        long index = this.consumerIndex;
        long mask = this.consumerMask;
        long offset = LinkedArrayQueueUtil.modifiedCalcElementOffset(index, mask);
        Object e = UnsafeRefArrayAccess.lvElement(buffer, offset);
        if (e == null) {
            return null;
        }
        if (e == JUMP) {
            return newBufferPoll(getNextBuffer(buffer, mask), index);
        }
        UnsafeRefArrayAccess.soElement(buffer, offset, null);
        soConsumerIndex(2 + index);
        return e;
    }

    public E relaxedPeek() {
        E[] buffer = this.consumerBuffer;
        long index = this.consumerIndex;
        long mask = this.consumerMask;
        Object e = UnsafeRefArrayAccess.lvElement(buffer, LinkedArrayQueueUtil.modifiedCalcElementOffset(index, mask));
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
                E[] buffer = this.producerBuffer;
                long batchIndex2 = Math.min(producerLimit, ((long) (batchSize * 2)) + pIndex);
                if (pIndex >= producerLimit || producerLimit < batchIndex2) {
                    batchIndex = batchIndex2;
                    switch (offerSlowPath(mask, pIndex, producerLimit)) {
                        case 0:
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
                        UnsafeRefArrayAccess.soElement(buffer, LinkedArrayQueueUtil.modifiedCalcElementOffset(((long) (i * 2)) + pIndex, mask), s.get());
                    }
                    return claimedSlots;
                }
            }
        }
    }

    public void fill(MessagePassingQueue.Supplier<E> s, MessagePassingQueue.WaitStrategy w, MessagePassingQueue.ExitCondition exit) {
        while (exit.keepRunning()) {
            if (fill(s, PortableJvmInfo.RECOMENDED_OFFER_BATCH) == 0) {
                int idleCounter = 0;
                while (exit.keepRunning() && fill(s, PortableJvmInfo.RECOMENDED_OFFER_BATCH) == 0) {
                    idleCounter = w.idle(idleCounter);
                }
            }
        }
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

    private void resize(long oldMask, E[] oldBuffer, long pIndex, E e) {
        E[] eArr = oldBuffer;
        long j = pIndex;
        int newBufferLength = getNextBufferSize(eArr);
        E[] newBuffer = CircularArrayOffsetCalculator.allocate(newBufferLength);
        this.producerBuffer = newBuffer;
        int newMask = (newBufferLength - 2) << 1;
        this.producerMask = (long) newMask;
        long offsetInOld = LinkedArrayQueueUtil.modifiedCalcElementOffset(j, oldMask);
        UnsafeRefArrayAccess.soElement(newBuffer, LinkedArrayQueueUtil.modifiedCalcElementOffset(j, (long) newMask), e);
        UnsafeRefArrayAccess.soElement(eArr, nextArrayOffset(oldMask), newBuffer);
        int i = newBufferLength;
        E[] eArr2 = newBuffer;
        long availableInQueue = availableInQueue(j, lvConsumerIndex());
        RangeUtil.checkPositive(availableInQueue, "availableInQueue");
        soProducerLimit(Math.min((long) newMask, availableInQueue) + j);
        soProducerIndex(2 + j);
        UnsafeRefArrayAccess.soElement(eArr, offsetInOld, JUMP);
    }
}
