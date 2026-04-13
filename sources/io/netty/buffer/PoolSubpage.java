package io.netty.buffer;

public final class PoolSubpage<T> implements PoolSubpageMetric {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final long[] bitmap;
    private int bitmapLength;
    final PoolChunk<T> chunk;
    boolean doNotDestroy;
    int elemSize;
    private int maxNumElems;
    private final int memoryMapIdx;
    PoolSubpage<T> next;
    private int nextAvail;
    private int numAvail;
    private final int pageSize;
    PoolSubpage<T> prev;
    private final int runOffset;

    PoolSubpage(int pageSize2) {
        this.chunk = null;
        this.memoryMapIdx = -1;
        this.runOffset = -1;
        this.elemSize = -1;
        this.pageSize = pageSize2;
        this.bitmap = null;
    }

    PoolSubpage(PoolSubpage<T> head, PoolChunk<T> chunk2, int memoryMapIdx2, int runOffset2, int pageSize2, int elemSize2) {
        this.chunk = chunk2;
        this.memoryMapIdx = memoryMapIdx2;
        this.runOffset = runOffset2;
        this.pageSize = pageSize2;
        this.bitmap = new long[(pageSize2 >>> 10)];
        init(head, elemSize2);
    }

    /* access modifiers changed from: package-private */
    public void init(PoolSubpage<T> head, int elemSize2) {
        this.doNotDestroy = true;
        this.elemSize = elemSize2;
        if (elemSize2 != 0) {
            int i = this.pageSize / elemSize2;
            this.numAvail = i;
            this.maxNumElems = i;
            this.nextAvail = 0;
            int i2 = i >>> 6;
            this.bitmapLength = i2;
            if ((i & 63) != 0) {
                this.bitmapLength = i2 + 1;
            }
            for (int i3 = 0; i3 < this.bitmapLength; i3++) {
                this.bitmap[i3] = 0;
            }
        }
        addToPool(head);
    }

    /* access modifiers changed from: package-private */
    public long allocate() {
        if (this.elemSize == 0) {
            return toHandle(0);
        }
        if (this.numAvail == 0 || !this.doNotDestroy) {
            return -1;
        }
        int bitmapIdx = getNextAvail();
        int q = bitmapIdx >>> 6;
        int r = bitmapIdx & 63;
        long[] jArr = this.bitmap;
        if (((jArr[q] >>> r) & 1) == 0) {
            jArr[q] = jArr[q] | (1 << r);
            int i = this.numAvail - 1;
            this.numAvail = i;
            if (i == 0) {
                removeFromPool();
            }
            return toHandle(bitmapIdx);
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    public boolean free(PoolSubpage<T> head, int bitmapIdx) {
        if (this.elemSize == 0) {
            return true;
        }
        int q = bitmapIdx >>> 6;
        int r = bitmapIdx & 63;
        long[] jArr = this.bitmap;
        if (((jArr[q] >>> r) & 1) != 0) {
            jArr[q] = jArr[q] ^ (1 << r);
            setNextAvail(bitmapIdx);
            int i = this.numAvail;
            int i2 = i + 1;
            this.numAvail = i2;
            if (i == 0) {
                addToPool(head);
                return true;
            } else if (i2 != this.maxNumElems || this.prev == this.next) {
                return true;
            } else {
                this.doNotDestroy = false;
                removeFromPool();
                return false;
            }
        } else {
            throw new AssertionError();
        }
    }

    private void addToPool(PoolSubpage<T> head) {
        if (this.prev == null && this.next == null) {
            this.prev = head;
            PoolSubpage<T> poolSubpage = head.next;
            this.next = poolSubpage;
            poolSubpage.prev = this;
            head.next = this;
            return;
        }
        throw new AssertionError();
    }

    private void removeFromPool() {
        PoolSubpage<T> poolSubpage;
        PoolSubpage<T> poolSubpage2 = this.prev;
        if (poolSubpage2 == null || (poolSubpage = this.next) == null) {
            throw new AssertionError();
        }
        poolSubpage2.next = poolSubpage;
        this.next.prev = poolSubpage2;
        this.next = null;
        this.prev = null;
    }

    private void setNextAvail(int bitmapIdx) {
        this.nextAvail = bitmapIdx;
    }

    private int getNextAvail() {
        int nextAvail2 = this.nextAvail;
        if (nextAvail2 < 0) {
            return findNextAvail();
        }
        this.nextAvail = -1;
        return nextAvail2;
    }

    private int findNextAvail() {
        long[] bitmap2 = this.bitmap;
        int bitmapLength2 = this.bitmapLength;
        for (int i = 0; i < bitmapLength2; i++) {
            long bits = bitmap2[i];
            if ((~bits) != 0) {
                return findNextAvail0(i, bits);
            }
        }
        return -1;
    }

    private int findNextAvail0(int i, long bits) {
        int maxNumElems2 = this.maxNumElems;
        int baseVal = i << 6;
        for (int j = 0; j < 64; j++) {
            if ((1 & bits) == 0) {
                int val = baseVal | j;
                if (val < maxNumElems2) {
                    return val;
                }
                return -1;
            }
            bits >>>= 1;
        }
        return -1;
    }

    private long toHandle(int bitmapIdx) {
        return (((long) bitmapIdx) << 32) | 4611686018427387904L | ((long) this.memoryMapIdx);
    }

    public String toString() {
        int numAvail2;
        int elemSize2;
        int maxNumElems2;
        boolean doNotDestroy2;
        synchronized (this.chunk.arena) {
            if (!this.doNotDestroy) {
                doNotDestroy2 = false;
                maxNumElems2 = -1;
                elemSize2 = -1;
                numAvail2 = -1;
            } else {
                doNotDestroy2 = true;
                maxNumElems2 = this.maxNumElems;
                numAvail2 = this.numAvail;
                elemSize2 = this.elemSize;
            }
        }
        if (!doNotDestroy2) {
            return "(" + this.memoryMapIdx + ": not in use)";
        }
        return "(" + this.memoryMapIdx + ": " + (maxNumElems2 - numAvail2) + '/' + maxNumElems2 + ", offset: " + this.runOffset + ", length: " + this.pageSize + ", elemSize: " + elemSize2 + ')';
    }

    public int maxNumElements() {
        int i;
        synchronized (this.chunk.arena) {
            i = this.maxNumElems;
        }
        return i;
    }

    public int numAvailable() {
        int i;
        synchronized (this.chunk.arena) {
            i = this.numAvail;
        }
        return i;
    }

    public int elementSize() {
        int i;
        synchronized (this.chunk.arena) {
            i = this.elemSize;
        }
        return i;
    }

    public int pageSize() {
        return this.pageSize;
    }

    /* access modifiers changed from: package-private */
    public void destroy() {
        PoolChunk<T> poolChunk = this.chunk;
        if (poolChunk != null) {
            poolChunk.destroy();
        }
    }
}
