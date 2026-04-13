package io.netty.buffer;

import io.netty.buffer.PoolArena;
import io.netty.util.Recycler;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.util.Queue;

public final class PoolThreadCache {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) PoolThreadCache.class);
    private int allocations;
    final PoolArena<ByteBuffer> directArena;
    private final int freeSweepAllocationThreshold;
    final PoolArena<byte[]> heapArena;
    private final MemoryRegionCache<ByteBuffer>[] normalDirectCaches;
    private final MemoryRegionCache<byte[]>[] normalHeapCaches;
    private final int numShiftsNormalDirect;
    private final int numShiftsNormalHeap;
    private final MemoryRegionCache<ByteBuffer>[] smallSubPageDirectCaches;
    private final MemoryRegionCache<byte[]>[] smallSubPageHeapCaches;
    private final MemoryRegionCache<ByteBuffer>[] tinySubPageDirectCaches;
    private final MemoryRegionCache<byte[]>[] tinySubPageHeapCaches;

    PoolThreadCache(PoolArena<byte[]> heapArena2, PoolArena<ByteBuffer> directArena2, int tinyCacheSize, int smallCacheSize, int normalCacheSize, int maxCachedBufferCapacity, int freeSweepAllocationThreshold2) {
        if (maxCachedBufferCapacity >= 0) {
            this.freeSweepAllocationThreshold = freeSweepAllocationThreshold2;
            this.heapArena = heapArena2;
            this.directArena = directArena2;
            if (directArena2 != null) {
                this.tinySubPageDirectCaches = createSubPageCaches(tinyCacheSize, 32, PoolArena.SizeClass.Tiny);
                this.smallSubPageDirectCaches = createSubPageCaches(smallCacheSize, directArena2.numSmallSubpagePools, PoolArena.SizeClass.Small);
                this.numShiftsNormalDirect = log2(directArena2.pageSize);
                this.normalDirectCaches = createNormalCaches(normalCacheSize, maxCachedBufferCapacity, directArena2);
                directArena2.numThreadCaches.getAndIncrement();
            } else {
                this.tinySubPageDirectCaches = null;
                this.smallSubPageDirectCaches = null;
                this.normalDirectCaches = null;
                this.numShiftsNormalDirect = -1;
            }
            if (heapArena2 != null) {
                this.tinySubPageHeapCaches = createSubPageCaches(tinyCacheSize, 32, PoolArena.SizeClass.Tiny);
                this.smallSubPageHeapCaches = createSubPageCaches(smallCacheSize, heapArena2.numSmallSubpagePools, PoolArena.SizeClass.Small);
                this.numShiftsNormalHeap = log2(heapArena2.pageSize);
                this.normalHeapCaches = createNormalCaches(normalCacheSize, maxCachedBufferCapacity, heapArena2);
                heapArena2.numThreadCaches.getAndIncrement();
            } else {
                this.tinySubPageHeapCaches = null;
                this.smallSubPageHeapCaches = null;
                this.normalHeapCaches = null;
                this.numShiftsNormalHeap = -1;
            }
            if (!(this.tinySubPageDirectCaches == null && this.smallSubPageDirectCaches == null && this.normalDirectCaches == null && this.tinySubPageHeapCaches == null && this.smallSubPageHeapCaches == null && this.normalHeapCaches == null) && freeSweepAllocationThreshold2 < 1) {
                throw new IllegalArgumentException("freeSweepAllocationThreshold: " + freeSweepAllocationThreshold2 + " (expected: > 0)");
            }
            return;
        }
        throw new IllegalArgumentException("maxCachedBufferCapacity: " + maxCachedBufferCapacity + " (expected: >= 0)");
    }

    private static <T> MemoryRegionCache<T>[] createSubPageCaches(int cacheSize, int numCaches, PoolArena.SizeClass sizeClass) {
        if (cacheSize <= 0 || numCaches <= 0) {
            return null;
        }
        MemoryRegionCache<T>[] cache = new MemoryRegionCache[numCaches];
        for (int i = 0; i < cache.length; i++) {
            cache[i] = new SubPageMemoryRegionCache(cacheSize, sizeClass);
        }
        return cache;
    }

    private static <T> MemoryRegionCache<T>[] createNormalCaches(int cacheSize, int maxCachedBufferCapacity, PoolArena<T> area) {
        if (cacheSize <= 0 || maxCachedBufferCapacity <= 0) {
            return null;
        }
        MemoryRegionCache<T>[] cache = new MemoryRegionCache[Math.max(1, log2(Math.min(area.chunkSize, maxCachedBufferCapacity) / area.pageSize) + 1)];
        for (int i = 0; i < cache.length; i++) {
            cache[i] = new NormalMemoryRegionCache(cacheSize);
        }
        return cache;
    }

    private static int log2(int val) {
        int res = 0;
        while (val > 1) {
            val >>= 1;
            res++;
        }
        return res;
    }

    /* access modifiers changed from: package-private */
    public boolean allocateTiny(PoolArena<?> area, PooledByteBuf<?> buf, int reqCapacity, int normCapacity) {
        return allocate(cacheForTiny(area, normCapacity), buf, reqCapacity);
    }

    /* access modifiers changed from: package-private */
    public boolean allocateSmall(PoolArena<?> area, PooledByteBuf<?> buf, int reqCapacity, int normCapacity) {
        return allocate(cacheForSmall(area, normCapacity), buf, reqCapacity);
    }

    /* access modifiers changed from: package-private */
    public boolean allocateNormal(PoolArena<?> area, PooledByteBuf<?> buf, int reqCapacity, int normCapacity) {
        return allocate(cacheForNormal(area, normCapacity), buf, reqCapacity);
    }

    private boolean allocate(MemoryRegionCache<?> cache, PooledByteBuf buf, int reqCapacity) {
        if (cache == null) {
            return false;
        }
        boolean allocated = cache.allocate(buf, reqCapacity);
        int i = this.allocations + 1;
        this.allocations = i;
        if (i >= this.freeSweepAllocationThreshold) {
            this.allocations = 0;
            trim();
        }
        return allocated;
    }

    /* access modifiers changed from: package-private */
    public boolean add(PoolArena<?> area, PoolChunk chunk, long handle, int normCapacity, PoolArena.SizeClass sizeClass) {
        MemoryRegionCache<?> cache = cache(area, normCapacity, sizeClass);
        if (cache == null) {
            return false;
        }
        return cache.add(chunk, handle);
    }

    /* renamed from: io.netty.buffer.PoolThreadCache$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$buffer$PoolArena$SizeClass;

        static {
            int[] iArr = new int[PoolArena.SizeClass.values().length];
            $SwitchMap$io$netty$buffer$PoolArena$SizeClass = iArr;
            try {
                iArr[PoolArena.SizeClass.Normal.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$netty$buffer$PoolArena$SizeClass[PoolArena.SizeClass.Small.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$io$netty$buffer$PoolArena$SizeClass[PoolArena.SizeClass.Tiny.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private MemoryRegionCache<?> cache(PoolArena<?> area, int normCapacity, PoolArena.SizeClass sizeClass) {
        switch (AnonymousClass1.$SwitchMap$io$netty$buffer$PoolArena$SizeClass[sizeClass.ordinal()]) {
            case 1:
                return cacheForNormal(area, normCapacity);
            case 2:
                return cacheForSmall(area, normCapacity);
            case 3:
                return cacheForTiny(area, normCapacity);
            default:
                throw new Error();
        }
    }

    /* access modifiers changed from: package-private */
    public void free() {
        int numFreed = free((MemoryRegionCache<?>[]) this.tinySubPageDirectCaches) + free((MemoryRegionCache<?>[]) this.smallSubPageDirectCaches) + free((MemoryRegionCache<?>[]) this.normalDirectCaches) + free((MemoryRegionCache<?>[]) this.tinySubPageHeapCaches) + free((MemoryRegionCache<?>[]) this.smallSubPageHeapCaches) + free((MemoryRegionCache<?>[]) this.normalHeapCaches);
        if (numFreed > 0) {
            InternalLogger internalLogger = logger;
            if (internalLogger.isDebugEnabled()) {
                internalLogger.debug("Freed {} thread-local buffer(s) from thread: {}", Integer.valueOf(numFreed), Thread.currentThread().getName());
            }
        }
        PoolArena<ByteBuffer> poolArena = this.directArena;
        if (poolArena != null) {
            poolArena.numThreadCaches.getAndDecrement();
        }
        PoolArena<byte[]> poolArena2 = this.heapArena;
        if (poolArena2 != null) {
            poolArena2.numThreadCaches.getAndDecrement();
        }
    }

    private static int free(MemoryRegionCache<?>[] caches) {
        if (caches == null) {
            return 0;
        }
        int numFreed = 0;
        for (MemoryRegionCache<?> c : caches) {
            numFreed += free(c);
        }
        return numFreed;
    }

    private static int free(MemoryRegionCache<?> cache) {
        if (cache == null) {
            return 0;
        }
        return cache.free();
    }

    /* access modifiers changed from: package-private */
    public void trim() {
        trim((MemoryRegionCache<?>[]) this.tinySubPageDirectCaches);
        trim((MemoryRegionCache<?>[]) this.smallSubPageDirectCaches);
        trim((MemoryRegionCache<?>[]) this.normalDirectCaches);
        trim((MemoryRegionCache<?>[]) this.tinySubPageHeapCaches);
        trim((MemoryRegionCache<?>[]) this.smallSubPageHeapCaches);
        trim((MemoryRegionCache<?>[]) this.normalHeapCaches);
    }

    private static void trim(MemoryRegionCache<?>[] caches) {
        if (caches != null) {
            for (MemoryRegionCache<?> c : caches) {
                trim(c);
            }
        }
    }

    private static void trim(MemoryRegionCache<?> cache) {
        if (cache != null) {
            cache.trim();
        }
    }

    private MemoryRegionCache<?> cacheForTiny(PoolArena<?> area, int normCapacity) {
        int idx = PoolArena.tinyIdx(normCapacity);
        if (area.isDirect()) {
            return cache(this.tinySubPageDirectCaches, idx);
        }
        return cache(this.tinySubPageHeapCaches, idx);
    }

    private MemoryRegionCache<?> cacheForSmall(PoolArena<?> area, int normCapacity) {
        int idx = PoolArena.smallIdx(normCapacity);
        if (area.isDirect()) {
            return cache(this.smallSubPageDirectCaches, idx);
        }
        return cache(this.smallSubPageHeapCaches, idx);
    }

    private MemoryRegionCache<?> cacheForNormal(PoolArena<?> area, int normCapacity) {
        if (area.isDirect()) {
            return cache(this.normalDirectCaches, log2(normCapacity >> this.numShiftsNormalDirect));
        }
        return cache(this.normalHeapCaches, log2(normCapacity >> this.numShiftsNormalHeap));
    }

    private static <T> MemoryRegionCache<T> cache(MemoryRegionCache<T>[] cache, int idx) {
        if (cache == null || idx > cache.length - 1) {
            return null;
        }
        return cache[idx];
    }

    public static final class SubPageMemoryRegionCache<T> extends MemoryRegionCache<T> {
        SubPageMemoryRegionCache(int size, PoolArena.SizeClass sizeClass) {
            super(size, sizeClass);
        }

        /* access modifiers changed from: protected */
        public void initBuf(PoolChunk<T> chunk, long handle, PooledByteBuf<T> buf, int reqCapacity) {
            chunk.initBufWithSubpage(buf, handle, reqCapacity);
        }
    }

    public static final class NormalMemoryRegionCache<T> extends MemoryRegionCache<T> {
        NormalMemoryRegionCache(int size) {
            super(size, PoolArena.SizeClass.Normal);
        }

        /* access modifiers changed from: protected */
        public void initBuf(PoolChunk<T> chunk, long handle, PooledByteBuf<T> buf, int reqCapacity) {
            chunk.initBuf(buf, handle, reqCapacity);
        }
    }

    public static abstract class MemoryRegionCache<T> {
        /* access modifiers changed from: private */
        public static final Recycler<Entry> RECYCLER = new Recycler<Entry>() {
            /* access modifiers changed from: protected */
            public Entry newObject(Recycler.Handle handle) {
                return new Entry(handle);
            }
        };
        private int allocations;
        private final Queue<Entry<T>> queue;
        private final int size;
        private final PoolArena.SizeClass sizeClass;

        /* access modifiers changed from: protected */
        public abstract void initBuf(PoolChunk<T> poolChunk, long j, PooledByteBuf<T> pooledByteBuf, int i);

        MemoryRegionCache(int size2, PoolArena.SizeClass sizeClass2) {
            int safeFindNextPositivePowerOfTwo = MathUtil.safeFindNextPositivePowerOfTwo(size2);
            this.size = safeFindNextPositivePowerOfTwo;
            this.queue = PlatformDependent.newFixedMpscQueue(safeFindNextPositivePowerOfTwo);
            this.sizeClass = sizeClass2;
        }

        public final boolean add(PoolChunk<T> chunk, long handle) {
            Entry<T> entry = newEntry(chunk, handle);
            boolean queued = this.queue.offer(entry);
            if (!queued) {
                entry.recycle();
            }
            return queued;
        }

        public final boolean allocate(PooledByteBuf<T> buf, int reqCapacity) {
            Entry<T> entry = this.queue.poll();
            if (entry == null) {
                return false;
            }
            initBuf(entry.chunk, entry.handle, buf, reqCapacity);
            entry.recycle();
            this.allocations++;
            return true;
        }

        public final int free() {
            return free(Integer.MAX_VALUE);
        }

        private int free(int max) {
            int numFreed = 0;
            while (numFreed < max) {
                Entry<T> entry = this.queue.poll();
                if (entry == null) {
                    return numFreed;
                }
                freeEntry(entry);
                numFreed++;
            }
            return numFreed;
        }

        public final void trim() {
            int free = this.size - this.allocations;
            this.allocations = 0;
            if (free > 0) {
                free(free);
            }
        }

        private void freeEntry(Entry entry) {
            PoolChunk chunk = entry.chunk;
            long handle = entry.handle;
            entry.recycle();
            chunk.arena.freeChunk(chunk, handle, this.sizeClass);
        }

        public static final class Entry<T> {
            PoolChunk<T> chunk;
            long handle = -1;
            final Recycler.Handle recyclerHandle;

            Entry(Recycler.Handle recyclerHandle2) {
                this.recyclerHandle = recyclerHandle2;
            }

            /* access modifiers changed from: package-private */
            public void recycle() {
                this.chunk = null;
                this.handle = -1;
                MemoryRegionCache.RECYCLER.recycle(this, this.recyclerHandle);
            }
        }

        private static Entry newEntry(PoolChunk<?> chunk, long handle) {
            Entry entry = RECYCLER.get();
            entry.chunk = chunk;
            entry.handle = handle;
            return entry;
        }
    }
}
