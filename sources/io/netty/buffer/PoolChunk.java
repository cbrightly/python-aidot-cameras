package io.netty.buffer;

public final class PoolChunk<T> implements PoolChunkMetric {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int INTEGER_SIZE_MINUS_ONE = 31;
    final PoolArena<T> arena;
    private final int chunkSize;
    private final byte[] depthMap;
    private int freeBytes;
    private final int log2ChunkSize;
    private final int maxOrder;
    private final int maxSubpageAllocs;
    final T memory;
    private final byte[] memoryMap;
    PoolChunk<T> next;
    final int offset;
    private final int pageShifts;
    private final int pageSize;
    PoolChunkList<T> parent;
    PoolChunk<T> prev;
    private final int subpageOverflowMask;
    private final PoolSubpage<T>[] subpages;
    final boolean unpooled = true;
    private final byte unusable;

    PoolChunk(PoolArena<T> arena2, T memory2, int pageSize2, int maxOrder2, int pageShifts2, int chunkSize2, int offset2) {
        this.arena = arena2;
        this.memory = memory2;
        this.pageSize = pageSize2;
        this.pageShifts = pageShifts2;
        this.maxOrder = maxOrder2;
        this.chunkSize = chunkSize2;
        this.offset = offset2;
        this.unusable = (byte) (maxOrder2 + 1);
        this.log2ChunkSize = log2(chunkSize2);
        this.subpageOverflowMask = ~(pageSize2 - 1);
        this.freeBytes = chunkSize2;
        if (maxOrder2 < 30) {
            int i = 1 << maxOrder2;
            this.maxSubpageAllocs = i;
            byte[] bArr = new byte[(i << 1)];
            this.memoryMap = bArr;
            this.depthMap = new byte[bArr.length];
            int memoryMapIndex = 1;
            for (int d = 0; d <= maxOrder2; d++) {
                int depth = 1 << d;
                for (int p = 0; p < depth; p++) {
                    this.memoryMap[memoryMapIndex] = (byte) d;
                    this.depthMap[memoryMapIndex] = (byte) d;
                    memoryMapIndex++;
                }
            }
            this.subpages = newSubpageArray(this.maxSubpageAllocs);
            return;
        }
        throw new AssertionError("maxOrder should be < 30, but is: " + maxOrder2);
    }

    PoolChunk(PoolArena<T> arena2, T memory2, int size, int offset2) {
        this.arena = arena2;
        this.memory = memory2;
        this.offset = offset2;
        this.memoryMap = null;
        this.depthMap = null;
        this.subpages = null;
        this.subpageOverflowMask = 0;
        this.pageSize = 0;
        this.pageShifts = 0;
        this.maxOrder = 0;
        this.unusable = (byte) (1 + 0);
        this.chunkSize = size;
        this.log2ChunkSize = log2(size);
        this.maxSubpageAllocs = 0;
    }

    private PoolSubpage<T>[] newSubpageArray(int size) {
        return new PoolSubpage[size];
    }

    public int usage() {
        int freeBytes2;
        synchronized (this.arena) {
            freeBytes2 = this.freeBytes;
        }
        return usage(freeBytes2);
    }

    private int usage(int freeBytes2) {
        if (freeBytes2 == 0) {
            return 100;
        }
        int freePercentage = (int) ((((long) freeBytes2) * 100) / ((long) this.chunkSize));
        if (freePercentage == 0) {
            return 99;
        }
        return 100 - freePercentage;
    }

    /* access modifiers changed from: package-private */
    public long allocate(int normCapacity) {
        if ((this.subpageOverflowMask & normCapacity) != 0) {
            return allocateRun(normCapacity);
        }
        return allocateSubpage(normCapacity);
    }

    private void updateParentsAlloc(int id) {
        while (id > 1) {
            int parentId = id >>> 1;
            byte val1 = value(id);
            byte val2 = value(id ^ 1);
            setValue(parentId, val1 < val2 ? val1 : val2);
            id = parentId;
        }
    }

    private void updateParentsFree(int id) {
        int logChild = depth(id) + 1;
        while (id > 1) {
            int parentId = id >>> 1;
            byte val1 = value(id);
            byte val2 = value(id ^ 1);
            logChild--;
            if (val1 == logChild && val2 == logChild) {
                setValue(parentId, (byte) (logChild - 1));
            } else {
                setValue(parentId, val1 < val2 ? val1 : val2);
            }
            id = parentId;
        }
    }

    private int allocateNode(int d) {
        int id = 1;
        int initial = -(1 << d);
        byte val = value(1);
        if (val > d) {
            return -1;
        }
        while (true) {
            if (val >= d && (id & initial) != 0) {
                break;
            }
            id <<= 1;
            val = value(id);
            if (val > d) {
                id ^= 1;
                val = value(id);
            }
        }
        byte value = value(id);
        if (value == d && (id & initial) == (1 << d)) {
            setValue(id, this.unusable);
            updateParentsAlloc(id);
            return id;
        }
        throw new AssertionError(String.format("val = %d, id & initial = %d, d = %d", new Object[]{Byte.valueOf(value), Integer.valueOf(id & initial), Integer.valueOf(d)}));
    }

    private long allocateRun(int normCapacity) {
        int id = allocateNode(this.maxOrder - (log2(normCapacity) - this.pageShifts));
        if (id < 0) {
            return (long) id;
        }
        this.freeBytes -= runLength(id);
        return (long) id;
    }

    private long allocateSubpage(int normCapacity) {
        int i = normCapacity;
        PoolSubpage<T> head = this.arena.findSubpagePoolHead(i);
        synchronized (head) {
            int id = allocateNode(this.maxOrder);
            if (id < 0) {
                long j = (long) id;
                return j;
            }
            PoolSubpage<T>[] subpages2 = this.subpages;
            int pageSize2 = this.pageSize;
            this.freeBytes -= pageSize2;
            int subpageIdx = subpageIdx(id);
            PoolSubpage<T> subpage = subpages2[subpageIdx];
            if (subpage == null) {
                subpage = new PoolSubpage<>(head, this, id, runOffset(id), pageSize2, normCapacity);
                subpages2[subpageIdx] = subpage;
            } else {
                subpage.init(head, i);
            }
            long allocate = subpage.allocate();
            return allocate;
        }
    }

    /* access modifiers changed from: package-private */
    public void free(long handle) {
        int memoryMapIdx = memoryMapIdx(handle);
        int bitmapIdx = bitmapIdx(handle);
        if (bitmapIdx != 0) {
            PoolSubpage<T> subpage = this.subpages[subpageIdx(memoryMapIdx)];
            if (subpage == null || !subpage.doNotDestroy) {
                throw new AssertionError();
            }
            PoolSubpage<T> head = this.arena.findSubpagePoolHead(subpage.elemSize);
            synchronized (head) {
                if (subpage.free(head, 1073741823 & bitmapIdx)) {
                    return;
                }
            }
        }
        this.freeBytes += runLength(memoryMapIdx);
        setValue(memoryMapIdx, depth(memoryMapIdx));
        updateParentsFree(memoryMapIdx);
    }

    /* access modifiers changed from: package-private */
    public void initBuf(PooledByteBuf<T> buf, long handle, int reqCapacity) {
        int memoryMapIdx = memoryMapIdx(handle);
        int bitmapIdx = bitmapIdx(handle);
        if (bitmapIdx == 0) {
            byte val = value(memoryMapIdx);
            if (val == this.unusable) {
                buf.init(this, handle, runOffset(memoryMapIdx) + this.offset, reqCapacity, runLength(memoryMapIdx), this.arena.parent.threadCache());
                return;
            }
            throw new AssertionError(String.valueOf(val));
        }
        initBufWithSubpage(buf, handle, bitmapIdx, reqCapacity);
    }

    /* access modifiers changed from: package-private */
    public void initBufWithSubpage(PooledByteBuf<T> buf, long handle, int reqCapacity) {
        initBufWithSubpage(buf, handle, bitmapIdx(handle), reqCapacity);
    }

    private void initBufWithSubpage(PooledByteBuf<T> buf, long handle, int bitmapIdx, int reqCapacity) {
        if (bitmapIdx != 0) {
            int memoryMapIdx = memoryMapIdx(handle);
            PoolSubpage<T> subpage = this.subpages[subpageIdx(memoryMapIdx)];
            if (!subpage.doNotDestroy) {
                int i = reqCapacity;
                throw new AssertionError();
            } else if (reqCapacity <= subpage.elemSize) {
                int runOffset = runOffset(memoryMapIdx);
                int i2 = subpage.elemSize;
                buf.init(this, handle, runOffset + ((bitmapIdx & 1073741823) * i2) + this.offset, reqCapacity, i2, this.arena.parent.threadCache());
            } else {
                throw new AssertionError();
            }
        } else {
            int i3 = reqCapacity;
            throw new AssertionError();
        }
    }

    private byte value(int id) {
        return this.memoryMap[id];
    }

    private void setValue(int id, byte val) {
        this.memoryMap[id] = val;
    }

    private byte depth(int id) {
        return this.depthMap[id];
    }

    private static int log2(int val) {
        return 31 - Integer.numberOfLeadingZeros(val);
    }

    private int runLength(int id) {
        return 1 << (this.log2ChunkSize - depth(id));
    }

    private int runOffset(int id) {
        return runLength(id) * ((1 << depth(id)) ^ id);
    }

    private int subpageIdx(int memoryMapIdx) {
        return this.maxSubpageAllocs ^ memoryMapIdx;
    }

    private static int memoryMapIdx(long handle) {
        return (int) handle;
    }

    private static int bitmapIdx(long handle) {
        return (int) (handle >>> 32);
    }

    public int chunkSize() {
        return this.chunkSize;
    }

    public int freeBytes() {
        int i;
        synchronized (this.arena) {
            i = this.freeBytes;
        }
        return i;
    }

    public String toString() {
        int freeBytes2;
        synchronized (this.arena) {
            freeBytes2 = this.freeBytes;
        }
        return "Chunk(" + Integer.toHexString(System.identityHashCode(this)) + ": " + usage(freeBytes2) + "%, " + (this.chunkSize - freeBytes2) + '/' + this.chunkSize + ')';
    }

    /* access modifiers changed from: package-private */
    public void destroy() {
        this.arena.destroyChunk(this);
    }
}
