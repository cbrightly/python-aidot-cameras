package io.netty.buffer;

import io.netty.util.internal.LongCounter;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class PoolArena<T> implements PoolArenaMetric {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final boolean HAS_UNSAFE = PlatformDependent.hasUnsafe();
    static final int numTinySubpagePools = 32;
    private final LongCounter activeBytesHuge = PlatformDependent.newLongCounter();
    private final LongCounter allocationsHuge = PlatformDependent.newLongCounter();
    private long allocationsNormal;
    private final LongCounter allocationsSmall = PlatformDependent.newLongCounter();
    private final LongCounter allocationsTiny = PlatformDependent.newLongCounter();
    private final List<PoolChunkListMetric> chunkListMetrics;
    final int chunkSize;
    private final LongCounter deallocationsHuge = PlatformDependent.newLongCounter();
    private long deallocationsNormal;
    private long deallocationsSmall;
    private long deallocationsTiny;
    final int directMemoryCacheAlignment;
    final int directMemoryCacheAlignmentMask;
    private final int maxOrder;
    final int numSmallSubpagePools;
    final AtomicInteger numThreadCaches = new AtomicInteger();
    final int pageShifts;
    final int pageSize;
    final PooledByteBufAllocator parent;
    private final PoolChunkList<T> q000;
    private final PoolChunkList<T> q025;
    private final PoolChunkList<T> q050;
    private final PoolChunkList<T> q075;
    private final PoolChunkList<T> q100;
    private final PoolChunkList<T> qInit;
    private final PoolSubpage<T>[] smallSubpagePools;
    final int subpageOverflowMask;
    private final PoolSubpage<T>[] tinySubpagePools;

    public enum SizeClass {
        Tiny,
        Small,
        Normal
    }

    /* access modifiers changed from: protected */
    public abstract void destroyChunk(PoolChunk<T> poolChunk);

    /* access modifiers changed from: package-private */
    public abstract boolean isDirect();

    /* access modifiers changed from: protected */
    public abstract void memoryCopy(T t, int i, T t2, int i2, int i3);

    /* access modifiers changed from: protected */
    public abstract PooledByteBuf<T> newByteBuf(int i);

    /* access modifiers changed from: protected */
    public abstract PoolChunk<T> newChunk(int i, int i2, int i3, int i4);

    /* access modifiers changed from: protected */
    public abstract PoolChunk<T> newUnpooledChunk(int i);

    protected PoolArena(PooledByteBufAllocator parent2, int pageSize2, int maxOrder2, int pageShifts2, int chunkSize2, int cacheAlignment) {
        int i = pageSize2;
        int i2 = pageShifts2;
        int i3 = cacheAlignment;
        this.parent = parent2;
        this.pageSize = i;
        this.maxOrder = maxOrder2;
        this.pageShifts = i2;
        this.chunkSize = chunkSize2;
        this.directMemoryCacheAlignment = i3;
        this.directMemoryCacheAlignmentMask = i3 - 1;
        this.subpageOverflowMask = ~(i - 1);
        this.tinySubpagePools = newSubpagePoolArray(32);
        int i4 = 0;
        while (true) {
            PoolSubpage<T>[] poolSubpageArr = this.tinySubpagePools;
            if (i4 >= poolSubpageArr.length) {
                break;
            }
            poolSubpageArr[i4] = newSubpagePoolHead(i);
            i4++;
        }
        int i5 = i2 - 9;
        this.numSmallSubpagePools = i5;
        this.smallSubpagePools = newSubpagePoolArray(i5);
        int i6 = 0;
        while (true) {
            PoolSubpage<T>[] poolSubpageArr2 = this.smallSubpagePools;
            if (i6 < poolSubpageArr2.length) {
                poolSubpageArr2[i6] = newSubpagePoolHead(i);
                i6++;
            } else {
                int i7 = chunkSize2;
                PoolChunkList poolChunkList = new PoolChunkList(this, (PoolChunkList) null, 100, Integer.MAX_VALUE, i7);
                this.q100 = poolChunkList;
                PoolChunkList poolChunkList2 = new PoolChunkList(this, poolChunkList, 75, 100, i7);
                this.q075 = poolChunkList2;
                PoolChunkList poolChunkList3 = new PoolChunkList(this, poolChunkList2, 50, 100, i7);
                this.q050 = poolChunkList3;
                PoolChunkList<T> poolChunkList4 = r0;
                PoolChunkList<T> poolChunkList5 = new PoolChunkList<>(this, poolChunkList3, 25, 75, chunkSize2);
                this.q025 = poolChunkList4;
                PoolChunkList<T> poolChunkList6 = r0;
                PoolChunkList<T> poolChunkList7 = new PoolChunkList<>(this, poolChunkList4, 1, 50, chunkSize2);
                this.q000 = poolChunkList6;
                PoolChunkList<T> poolChunkList8 = r0;
                PoolChunkList<T> poolChunkList9 = new PoolChunkList<>(this, poolChunkList6, Integer.MIN_VALUE, 25, chunkSize2);
                this.qInit = poolChunkList8;
                poolChunkList.prevList(poolChunkList2);
                poolChunkList2.prevList(poolChunkList3);
                poolChunkList3.prevList(poolChunkList4);
                poolChunkList4.prevList(poolChunkList6);
                poolChunkList6.prevList((PoolChunkList<T>) null);
                poolChunkList8.prevList(poolChunkList8);
                List<PoolChunkListMetric> metrics = new ArrayList<>(6);
                metrics.add(poolChunkList8);
                metrics.add(poolChunkList6);
                metrics.add(poolChunkList4);
                metrics.add(poolChunkList3);
                metrics.add(poolChunkList2);
                metrics.add(poolChunkList);
                this.chunkListMetrics = Collections.unmodifiableList(metrics);
                return;
            }
        }
    }

    private PoolSubpage<T> newSubpagePoolHead(int pageSize2) {
        PoolSubpage<T> head = new PoolSubpage<>(pageSize2);
        head.prev = head;
        head.next = head;
        return head;
    }

    private PoolSubpage<T>[] newSubpagePoolArray(int size) {
        return new PoolSubpage[size];
    }

    /* access modifiers changed from: package-private */
    public PooledByteBuf<T> allocate(PoolThreadCache cache, int reqCapacity, int maxCapacity) {
        PooledByteBuf<T> buf = newByteBuf(maxCapacity);
        allocate(cache, buf, reqCapacity);
        return buf;
    }

    static int tinyIdx(int normCapacity) {
        return normCapacity >>> 4;
    }

    static int smallIdx(int normCapacity) {
        int tableIdx = 0;
        int i = normCapacity >>> 10;
        while (i != 0) {
            i >>>= 1;
            tableIdx++;
        }
        return tableIdx;
    }

    /* access modifiers changed from: package-private */
    public boolean isTinyOrSmall(int normCapacity) {
        return (this.subpageOverflowMask & normCapacity) == 0;
    }

    static boolean isTiny(int normCapacity) {
        return (normCapacity & -512) == 0;
    }

    private void allocate(PoolThreadCache cache, PooledByteBuf<T> buf, int reqCapacity) {
        PoolSubpage<T>[] table;
        int tableIdx;
        int normCapacity = normalizeCapacity(reqCapacity);
        if (isTinyOrSmall(normCapacity)) {
            boolean tiny = isTiny(normCapacity);
            if (tiny) {
                if (!cache.allocateTiny(this, buf, reqCapacity, normCapacity)) {
                    tableIdx = tinyIdx(normCapacity);
                    table = this.tinySubpagePools;
                } else {
                    return;
                }
            } else if (cache.allocateSmall(this, buf, reqCapacity, normCapacity) == 0) {
                tableIdx = smallIdx(normCapacity);
                table = this.smallSubpagePools;
            } else {
                return;
            }
            PoolSubpage<T> head = table[tableIdx];
            synchronized (head) {
                PoolSubpage<T> s = head.next;
                if (s == head) {
                    synchronized (this) {
                        allocateNormal(buf, reqCapacity, normCapacity);
                    }
                    incTinySmallAllocation(tiny);
                } else if (!s.doNotDestroy || s.elemSize != normCapacity) {
                    throw new AssertionError();
                } else {
                    long handle = s.allocate();
                    if (handle >= 0) {
                        s.chunk.initBufWithSubpage(buf, handle, reqCapacity);
                        incTinySmallAllocation(tiny);
                        return;
                    }
                    throw new AssertionError();
                }
            }
        } else if (normCapacity > this.chunkSize) {
            allocateHuge(buf, reqCapacity);
        } else if (!cache.allocateNormal(this, buf, reqCapacity, normCapacity)) {
            synchronized (this) {
                allocateNormal(buf, reqCapacity, normCapacity);
                this.allocationsNormal++;
            }
        }
    }

    private void allocateNormal(PooledByteBuf<T> buf, int reqCapacity, int normCapacity) {
        if (!this.q050.allocate(buf, reqCapacity, normCapacity) && !this.q025.allocate(buf, reqCapacity, normCapacity) && !this.q000.allocate(buf, reqCapacity, normCapacity) && !this.qInit.allocate(buf, reqCapacity, normCapacity) && !this.q075.allocate(buf, reqCapacity, normCapacity)) {
            PoolChunk<T> c = newChunk(this.pageSize, this.maxOrder, this.pageShifts, this.chunkSize);
            long handle = c.allocate(normCapacity);
            if (handle > 0) {
                c.initBuf(buf, handle, reqCapacity);
                this.qInit.add(c);
                return;
            }
            throw new AssertionError();
        }
    }

    private void incTinySmallAllocation(boolean tiny) {
        if (tiny) {
            this.allocationsTiny.increment();
        } else {
            this.allocationsSmall.increment();
        }
    }

    private void allocateHuge(PooledByteBuf<T> buf, int reqCapacity) {
        PoolChunk<T> chunk = newUnpooledChunk(reqCapacity);
        this.activeBytesHuge.add((long) chunk.chunkSize());
        buf.initUnpooled(chunk, reqCapacity);
        this.allocationsHuge.increment();
    }

    /* access modifiers changed from: package-private */
    public void free(PoolChunk<T> chunk, long handle, int normCapacity, PoolThreadCache cache) {
        if (chunk.unpooled) {
            int size = chunk.chunkSize();
            destroyChunk(chunk);
            this.activeBytesHuge.add((long) (-size));
            this.deallocationsHuge.increment();
            return;
        }
        SizeClass sizeClass = sizeClass(normCapacity);
        if (cache == null || !cache.add(this, chunk, handle, normCapacity, sizeClass)) {
            freeChunk(chunk, handle, sizeClass);
        }
    }

    private SizeClass sizeClass(int normCapacity) {
        if (!isTinyOrSmall(normCapacity)) {
            return SizeClass.Normal;
        }
        return isTiny(normCapacity) ? SizeClass.Tiny : SizeClass.Small;
    }

    /* renamed from: io.netty.buffer.PoolArena$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$buffer$PoolArena$SizeClass;

        static {
            int[] iArr = new int[SizeClass.values().length];
            $SwitchMap$io$netty$buffer$PoolArena$SizeClass = iArr;
            try {
                iArr[SizeClass.Normal.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$netty$buffer$PoolArena$SizeClass[SizeClass.Small.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$io$netty$buffer$PoolArena$SizeClass[SizeClass.Tiny.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void freeChunk(PoolChunk<T> chunk, long handle, SizeClass sizeClass) {
        boolean destroyChunk;
        synchronized (this) {
            switch (AnonymousClass1.$SwitchMap$io$netty$buffer$PoolArena$SizeClass[sizeClass.ordinal()]) {
                case 1:
                    this.deallocationsNormal++;
                    break;
                case 2:
                    this.deallocationsSmall++;
                    break;
                case 3:
                    this.deallocationsTiny++;
                    break;
                default:
                    throw new Error();
            }
            destroyChunk = !chunk.parent.free(chunk, handle);
        }
        if (destroyChunk) {
            destroyChunk(chunk);
        }
    }

    /* access modifiers changed from: package-private */
    public PoolSubpage<T> findSubpagePoolHead(int elemSize) {
        PoolSubpage<T>[] table;
        int tableIdx;
        if (isTiny(elemSize)) {
            tableIdx = elemSize >>> 4;
            table = this.tinySubpagePools;
        } else {
            tableIdx = 0;
            int elemSize2 = elemSize >>> 10;
            while (elemSize2 != 0) {
                elemSize2 >>>= 1;
                tableIdx++;
            }
            table = this.smallSubpagePools;
        }
        return table[tableIdx];
    }

    /* access modifiers changed from: package-private */
    public int normalizeCapacity(int reqCapacity) {
        if (reqCapacity < 0) {
            throw new IllegalArgumentException("capacity: " + reqCapacity + " (expected: 0+)");
        } else if (reqCapacity >= this.chunkSize) {
            return this.directMemoryCacheAlignment == 0 ? reqCapacity : alignCapacity(reqCapacity);
        } else {
            if (!isTiny(reqCapacity)) {
                int normalizedCapacity = reqCapacity - 1;
                int normalizedCapacity2 = normalizedCapacity | (normalizedCapacity >>> 1);
                int normalizedCapacity3 = normalizedCapacity2 | (normalizedCapacity2 >>> 2);
                int normalizedCapacity4 = normalizedCapacity3 | (normalizedCapacity3 >>> 4);
                int normalizedCapacity5 = normalizedCapacity4 | (normalizedCapacity4 >>> 8);
                int normalizedCapacity6 = (normalizedCapacity5 | (normalizedCapacity5 >>> 16)) + 1;
                if (normalizedCapacity6 < 0) {
                    normalizedCapacity6 >>>= 1;
                }
                if (this.directMemoryCacheAlignment == 0 || (this.directMemoryCacheAlignmentMask & normalizedCapacity6) == 0) {
                    return normalizedCapacity6;
                }
                throw new AssertionError();
            } else if (this.directMemoryCacheAlignment > 0) {
                return alignCapacity(reqCapacity);
            } else {
                if ((reqCapacity & 15) == 0) {
                    return reqCapacity;
                }
                return (reqCapacity & -16) + 16;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int alignCapacity(int reqCapacity) {
        int delta = this.directMemoryCacheAlignmentMask & reqCapacity;
        return delta == 0 ? reqCapacity : (this.directMemoryCacheAlignment + reqCapacity) - delta;
    }

    /* access modifiers changed from: package-private */
    public void reallocate(PooledByteBuf<T> buf, int newCapacity, boolean freeOldMemory) {
        int oldMaxLength;
        int oldMaxLength2;
        int writerIndex;
        PooledByteBuf<T> pooledByteBuf = buf;
        int i = newCapacity;
        if (i < 0 || i > buf.maxCapacity()) {
            throw new IllegalArgumentException("newCapacity: " + i);
        }
        int oldCapacity = pooledByteBuf.length;
        if (oldCapacity != i) {
            PoolChunk<T> oldChunk = pooledByteBuf.chunk;
            long oldHandle = pooledByteBuf.handle;
            T oldMemory = pooledByteBuf.memory;
            int oldOffset = pooledByteBuf.offset;
            int oldMaxLength3 = pooledByteBuf.maxLength;
            int readerIndex = buf.readerIndex();
            int writerIndex2 = buf.writerIndex();
            allocate(this.parent.threadCache(), pooledByteBuf, i);
            if (i > oldCapacity) {
                T t = pooledByteBuf.memory;
                writerIndex = writerIndex2;
                int writerIndex3 = pooledByteBuf.offset;
                oldMaxLength = oldMaxLength3;
                oldMaxLength2 = readerIndex;
                memoryCopy(oldMemory, oldOffset, t, writerIndex3, oldCapacity);
            } else {
                writerIndex = writerIndex2;
                oldMaxLength = oldMaxLength3;
                oldMaxLength2 = readerIndex;
                if (i < oldCapacity) {
                    if (oldMaxLength2 < i) {
                        if (writerIndex > i) {
                            writerIndex = newCapacity;
                        }
                        memoryCopy(oldMemory, oldOffset + oldMaxLength2, pooledByteBuf.memory, pooledByteBuf.offset + oldMaxLength2, writerIndex - oldMaxLength2);
                    } else {
                        writerIndex = newCapacity;
                        oldMaxLength2 = newCapacity;
                    }
                }
            }
            pooledByteBuf.setIndex(oldMaxLength2, writerIndex);
            if (freeOldMemory) {
                free(oldChunk, oldHandle, oldMaxLength, pooledByteBuf.cache);
            }
        }
    }

    public int numThreadCaches() {
        return this.numThreadCaches.get();
    }

    public int numTinySubpages() {
        return this.tinySubpagePools.length;
    }

    public int numSmallSubpages() {
        return this.smallSubpagePools.length;
    }

    public int numChunkLists() {
        return this.chunkListMetrics.size();
    }

    public List<PoolSubpageMetric> tinySubpages() {
        return subPageMetricList(this.tinySubpagePools);
    }

    public List<PoolSubpageMetric> smallSubpages() {
        return subPageMetricList(this.smallSubpagePools);
    }

    public List<PoolChunkListMetric> chunkLists() {
        return this.chunkListMetrics;
    }

    private static List<PoolSubpageMetric> subPageMetricList(PoolSubpage<?>[] pages) {
        List<PoolSubpageMetric> metrics = new ArrayList<>();
        for (PoolSubpage<?> head : pages) {
            if (head.next != head) {
                PoolSubpage<T> poolSubpage = head.next;
                do {
                    metrics.add(poolSubpage);
                    poolSubpage = poolSubpage.next;
                } while (poolSubpage != head);
            }
        }
        return metrics;
    }

    public long numAllocations() {
        long allocsNormal;
        synchronized (this) {
            allocsNormal = this.allocationsNormal;
        }
        return this.allocationsTiny.value() + this.allocationsSmall.value() + allocsNormal + this.allocationsHuge.value();
    }

    public long numTinyAllocations() {
        return this.allocationsTiny.value();
    }

    public long numSmallAllocations() {
        return this.allocationsSmall.value();
    }

    public synchronized long numNormalAllocations() {
        return this.allocationsNormal;
    }

    public long numDeallocations() {
        long deallocs;
        synchronized (this) {
            deallocs = this.deallocationsTiny + this.deallocationsSmall + this.deallocationsNormal;
        }
        return this.deallocationsHuge.value() + deallocs;
    }

    public synchronized long numTinyDeallocations() {
        return this.deallocationsTiny;
    }

    public synchronized long numSmallDeallocations() {
        return this.deallocationsSmall;
    }

    public synchronized long numNormalDeallocations() {
        return this.deallocationsNormal;
    }

    public long numHugeAllocations() {
        return this.allocationsHuge.value();
    }

    public long numHugeDeallocations() {
        return this.deallocationsHuge.value();
    }

    public long numActiveAllocations() {
        long val;
        long val2 = ((this.allocationsTiny.value() + this.allocationsSmall.value()) + this.allocationsHuge.value()) - this.deallocationsHuge.value();
        synchronized (this) {
            val = val2 + (this.allocationsNormal - ((this.deallocationsTiny + this.deallocationsSmall) + this.deallocationsNormal));
        }
        return Math.max(val, 0);
    }

    public long numActiveTinyAllocations() {
        return Math.max(numTinyAllocations() - numTinyDeallocations(), 0);
    }

    public long numActiveSmallAllocations() {
        return Math.max(numSmallAllocations() - numSmallDeallocations(), 0);
    }

    public long numActiveNormalAllocations() {
        long val;
        synchronized (this) {
            val = this.allocationsNormal - this.deallocationsNormal;
        }
        return Math.max(val, 0);
    }

    public long numActiveHugeAllocations() {
        return Math.max(numHugeAllocations() - numHugeDeallocations(), 0);
    }

    public long numActiveBytes() {
        long val = this.activeBytesHuge.value();
        synchronized (this) {
            for (int i = 0; i < this.chunkListMetrics.size(); i++) {
                for (PoolChunkMetric m : this.chunkListMetrics.get(i)) {
                    val += (long) m.chunkSize();
                }
            }
        }
        return Math.max(0, val);
    }

    public synchronized String toString() {
        StringBuilder buf;
        buf = new StringBuilder();
        buf.append("Chunk(s) at 0~25%:");
        String str = StringUtil.NEWLINE;
        buf.append(str);
        buf.append(this.qInit);
        buf.append(str);
        buf.append("Chunk(s) at 0~50%:");
        buf.append(str);
        buf.append(this.q000);
        buf.append(str);
        buf.append("Chunk(s) at 25~75%:");
        buf.append(str);
        buf.append(this.q025);
        buf.append(str);
        buf.append("Chunk(s) at 50~100%:");
        buf.append(str);
        buf.append(this.q050);
        buf.append(str);
        buf.append("Chunk(s) at 75~100%:");
        buf.append(str);
        buf.append(this.q075);
        buf.append(str);
        buf.append("Chunk(s) at 100%:");
        buf.append(str);
        buf.append(this.q100);
        buf.append(str);
        buf.append("tiny subpages:");
        appendPoolSubPages(buf, this.tinySubpagePools);
        buf.append(str);
        buf.append("small subpages:");
        appendPoolSubPages(buf, this.smallSubpagePools);
        buf.append(str);
        return buf.toString();
    }

    private static void appendPoolSubPages(StringBuilder buf, PoolSubpage<?>[] subpages) {
        for (int i = 0; i < subpages.length; i++) {
            PoolSubpage<?> head = subpages[i];
            if (head.next != head) {
                buf.append(StringUtil.NEWLINE);
                buf.append(i);
                buf.append(": ");
                PoolSubpage<T> poolSubpage = head.next;
                do {
                    buf.append(poolSubpage);
                    poolSubpage = poolSubpage.next;
                } while (poolSubpage != head);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void finalize() {
        try {
            super.finalize();
        } finally {
            destroyPoolSubPages(this.smallSubpagePools);
            destroyPoolSubPages(this.tinySubpagePools);
            destroyPoolChunkLists(this.qInit, this.q000, this.q025, this.q050, this.q075, this.q100);
        }
    }

    private static void destroyPoolSubPages(PoolSubpage<?>[] pages) {
        for (PoolSubpage<?> page : pages) {
            page.destroy();
        }
    }

    private void destroyPoolChunkLists(PoolChunkList<T>... chunkLists) {
        for (PoolChunkList<T> chunkList : chunkLists) {
            chunkList.destroy(this);
        }
    }

    public static final class HeapArena extends PoolArena<byte[]> {
        HeapArena(PooledByteBufAllocator parent, int pageSize, int maxOrder, int pageShifts, int chunkSize, int directMemoryCacheAlignment) {
            super(parent, pageSize, maxOrder, pageShifts, chunkSize, directMemoryCacheAlignment);
        }

        private static byte[] newByteArray(int size) {
            return PlatformDependent.allocateUninitializedArray(size);
        }

        /* access modifiers changed from: package-private */
        public boolean isDirect() {
            return false;
        }

        /* access modifiers changed from: protected */
        public PoolChunk<byte[]> newChunk(int pageSize, int maxOrder, int pageShifts, int chunkSize) {
            return new PoolChunk(this, newByteArray(chunkSize), pageSize, maxOrder, pageShifts, chunkSize, 0);
        }

        /* access modifiers changed from: protected */
        public PoolChunk<byte[]> newUnpooledChunk(int capacity) {
            return new PoolChunk<>(this, newByteArray(capacity), capacity, 0);
        }

        /* access modifiers changed from: protected */
        public void destroyChunk(PoolChunk<byte[]> poolChunk) {
        }

        /* access modifiers changed from: protected */
        public PooledByteBuf<byte[]> newByteBuf(int maxCapacity) {
            if (PoolArena.HAS_UNSAFE) {
                return PooledUnsafeHeapByteBuf.newUnsafeInstance(maxCapacity);
            }
            return PooledHeapByteBuf.newInstance(maxCapacity);
        }

        /* access modifiers changed from: protected */
        public void memoryCopy(byte[] src, int srcOffset, byte[] dst, int dstOffset, int length) {
            if (length != 0) {
                System.arraycopy(src, srcOffset, dst, dstOffset, length);
            }
        }
    }

    public static final class DirectArena extends PoolArena<ByteBuffer> {
        DirectArena(PooledByteBufAllocator parent, int pageSize, int maxOrder, int pageShifts, int chunkSize, int directMemoryCacheAlignment) {
            super(parent, pageSize, maxOrder, pageShifts, chunkSize, directMemoryCacheAlignment);
        }

        /* access modifiers changed from: package-private */
        public boolean isDirect() {
            return true;
        }

        private int offsetCacheLine(ByteBuffer memory) {
            if (PoolArena.HAS_UNSAFE) {
                return (int) (PlatformDependent.directBufferAddress(memory) & ((long) this.directMemoryCacheAlignmentMask));
            }
            return 0;
        }

        /* access modifiers changed from: protected */
        public PoolChunk<ByteBuffer> newChunk(int pageSize, int maxOrder, int pageShifts, int chunkSize) {
            int i = this.directMemoryCacheAlignment;
            if (i == 0) {
                return new PoolChunk(this, allocateDirect(chunkSize), pageSize, maxOrder, pageShifts, chunkSize, 0);
            }
            ByteBuffer memory = allocateDirect(i + chunkSize);
            return new PoolChunk(this, memory, pageSize, maxOrder, pageShifts, chunkSize, offsetCacheLine(memory));
        }

        /* access modifiers changed from: protected */
        public PoolChunk<ByteBuffer> newUnpooledChunk(int capacity) {
            int i = this.directMemoryCacheAlignment;
            if (i == 0) {
                return new PoolChunk<>(this, allocateDirect(capacity), capacity, 0);
            }
            ByteBuffer memory = allocateDirect(i + capacity);
            return new PoolChunk<>(this, memory, capacity, offsetCacheLine(memory));
        }

        private static ByteBuffer allocateDirect(int capacity) {
            return PlatformDependent.useDirectBufferNoCleaner() ? PlatformDependent.allocateDirectNoCleaner(capacity) : ByteBuffer.allocateDirect(capacity);
        }

        /* access modifiers changed from: protected */
        public void destroyChunk(PoolChunk<ByteBuffer> chunk) {
            if (PlatformDependent.useDirectBufferNoCleaner()) {
                PlatformDependent.freeDirectNoCleaner((ByteBuffer) chunk.memory);
            } else {
                PlatformDependent.freeDirectBuffer((ByteBuffer) chunk.memory);
            }
        }

        /* access modifiers changed from: protected */
        public PooledByteBuf<ByteBuffer> newByteBuf(int maxCapacity) {
            if (PoolArena.HAS_UNSAFE) {
                return PooledUnsafeDirectByteBuf.newInstance(maxCapacity);
            }
            return PooledDirectByteBuf.newInstance(maxCapacity);
        }

        /* access modifiers changed from: protected */
        public void memoryCopy(ByteBuffer src, int srcOffset, ByteBuffer dst, int dstOffset, int length) {
            if (length != 0) {
                if (PoolArena.HAS_UNSAFE) {
                    PlatformDependent.copyMemory(PlatformDependent.directBufferAddress(src) + ((long) srcOffset), PlatformDependent.directBufferAddress(dst) + ((long) dstOffset), (long) length);
                    return;
                }
                ByteBuffer src2 = src.duplicate();
                ByteBuffer dst2 = dst.duplicate();
                src2.position(srcOffset).limit(srcOffset + length);
                dst2.position(dstOffset);
                dst2.put(src2);
            }
        }
    }
}
