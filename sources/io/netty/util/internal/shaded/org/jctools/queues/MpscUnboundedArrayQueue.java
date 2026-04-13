package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import io.netty.util.internal.shaded.org.jctools.util.PortableJvmInfo;

public class MpscUnboundedArrayQueue<E> extends BaseMpscLinkedArrayQueue<E> {
    long p0;
    long p1;
    long p10;
    long p11;
    long p12;
    long p13;
    long p14;
    long p15;
    long p16;
    long p17;
    long p2;
    long p3;
    long p4;
    long p5;
    long p6;
    long p7;

    public MpscUnboundedArrayQueue(int chunkSize) {
        super(chunkSize);
    }

    /* access modifiers changed from: protected */
    public long availableInQueue(long pIndex, long cIndex) {
        return 2147483647L;
    }

    public int capacity() {
        return -1;
    }

    public int drain(MessagePassingQueue.Consumer<E> c) {
        return drain(c, 4096);
    }

    public int fill(MessagePassingQueue.Supplier<E> s) {
        long result = 0;
        do {
            int filled = fill(s, PortableJvmInfo.RECOMENDED_OFFER_BATCH);
            if (filled == 0) {
                return (int) result;
            }
            result += (long) filled;
        } while (result <= 4096);
        return (int) result;
    }

    /* access modifiers changed from: protected */
    public int getNextBufferSize(E[] buffer) {
        return LinkedArrayQueueUtil.length(buffer);
    }

    /* access modifiers changed from: protected */
    public long getCurrentBufferCapacity(long mask) {
        return mask;
    }
}
