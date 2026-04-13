package io.netty.buffer;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.netty.buffer.PoolArena;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PooledByteBufAllocator extends AbstractByteBufAllocator implements ByteBufAllocatorMetricProvider {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final PooledByteBufAllocator DEFAULT = new PooledByteBufAllocator(PlatformDependent.directBufferPreferred());
    /* access modifiers changed from: private */
    public static final int DEFAULT_CACHE_TRIM_INTERVAL;
    private static final int DEFAULT_DIRECT_MEMORY_CACHE_ALIGNMENT = SystemPropertyUtil.getInt("io.netty.allocator.directMemoryCacheAlignment", 0);
    /* access modifiers changed from: private */
    public static final int DEFAULT_MAX_CACHED_BUFFER_CAPACITY;
    private static final int DEFAULT_MAX_ORDER;
    private static final int DEFAULT_NORMAL_CACHE_SIZE;
    private static final int DEFAULT_NUM_DIRECT_ARENA;
    private static final int DEFAULT_NUM_HEAP_ARENA;
    private static final int DEFAULT_PAGE_SIZE;
    private static final int DEFAULT_SMALL_CACHE_SIZE;
    private static final int DEFAULT_TINY_CACHE_SIZE;
    private static final boolean DEFAULT_USE_CACHE_FOR_ALL_THREADS;
    private static final int MAX_CHUNK_SIZE = 1073741824;
    private static final int MIN_PAGE_SIZE = 4096;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) PooledByteBufAllocator.class);
    private final int chunkSize;
    private final List<PoolArenaMetric> directArenaMetrics;
    /* access modifiers changed from: private */
    public final PoolArena<ByteBuffer>[] directArenas;
    private final List<PoolArenaMetric> heapArenaMetrics;
    /* access modifiers changed from: private */
    public final PoolArena<byte[]>[] heapArenas;
    private final PooledByteBufAllocatorMetric metric;
    /* access modifiers changed from: private */
    public final int normalCacheSize;
    /* access modifiers changed from: private */
    public final int smallCacheSize;
    private final PoolThreadLocalCache threadCache;
    /* access modifiers changed from: private */
    public final int tinyCacheSize;

    static {
        int defaultPageSize = SystemPropertyUtil.getInt("io.netty.allocator.pageSize", 8192);
        Throwable pageSizeFallbackCause = null;
        try {
            validateAndCalculatePageShifts(defaultPageSize);
        } catch (Throwable th) {
            pageSizeFallbackCause = th;
            defaultPageSize = 8192;
        }
        DEFAULT_PAGE_SIZE = defaultPageSize;
        int defaultMaxOrder = SystemPropertyUtil.getInt("io.netty.allocator.maxOrder", 11);
        Throwable maxOrderFallbackCause = null;
        try {
            validateAndCalculateChunkSize(defaultPageSize, defaultMaxOrder);
        } catch (Throwable th2) {
            maxOrderFallbackCause = th2;
            defaultMaxOrder = 11;
        }
        DEFAULT_MAX_ORDER = defaultMaxOrder;
        Runtime runtime = Runtime.getRuntime();
        int defaultMinNumArena = NettyRuntime.availableProcessors() * 2;
        int i = DEFAULT_PAGE_SIZE;
        int defaultChunkSize = i << defaultMaxOrder;
        int max = Math.max(0, SystemPropertyUtil.getInt("io.netty.allocator.numHeapArenas", (int) Math.min((long) defaultMinNumArena, ((runtime.maxMemory() / ((long) defaultChunkSize)) / 2) / 3)));
        DEFAULT_NUM_HEAP_ARENA = max;
        int i2 = defaultPageSize;
        int max2 = Math.max(0, SystemPropertyUtil.getInt("io.netty.allocator.numDirectArenas", (int) Math.min((long) defaultMinNumArena, ((PlatformDependent.maxDirectMemory() / ((long) defaultChunkSize)) / 2) / 3)));
        DEFAULT_NUM_DIRECT_ARENA = max2;
        int i3 = SystemPropertyUtil.getInt("io.netty.allocator.tinyCacheSize", 512);
        DEFAULT_TINY_CACHE_SIZE = i3;
        int i4 = SystemPropertyUtil.getInt("io.netty.allocator.smallCacheSize", 256);
        DEFAULT_SMALL_CACHE_SIZE = i4;
        int i5 = SystemPropertyUtil.getInt("io.netty.allocator.normalCacheSize", 64);
        DEFAULT_NORMAL_CACHE_SIZE = i5;
        int i6 = SystemPropertyUtil.getInt("io.netty.allocator.maxCachedBufferCapacity", 32768);
        DEFAULT_MAX_CACHED_BUFFER_CAPACITY = i6;
        int i7 = SystemPropertyUtil.getInt("io.netty.allocator.cacheTrimInterval", 8192);
        DEFAULT_CACHE_TRIM_INTERVAL = i7;
        boolean z = SystemPropertyUtil.getBoolean("io.netty.allocator.useCacheForAllThreads", true);
        DEFAULT_USE_CACHE_FOR_ALL_THREADS = z;
        Runtime runtime2 = runtime;
        InternalLogger internalLogger = logger;
        if (internalLogger.isDebugEnabled()) {
            internalLogger.debug("-Dio.netty.allocator.numHeapArenas: {}", (Object) Integer.valueOf(max));
            internalLogger.debug("-Dio.netty.allocator.numDirectArenas: {}", (Object) Integer.valueOf(max2));
            if (pageSizeFallbackCause == null) {
                internalLogger.debug("-Dio.netty.allocator.pageSize: {}", (Object) Integer.valueOf(i));
            } else {
                internalLogger.debug("-Dio.netty.allocator.pageSize: {}", Integer.valueOf(i), pageSizeFallbackCause);
            }
            if (maxOrderFallbackCause == null) {
                internalLogger.debug("-Dio.netty.allocator.maxOrder: {}", (Object) Integer.valueOf(defaultMaxOrder));
            } else {
                internalLogger.debug("-Dio.netty.allocator.maxOrder: {}", Integer.valueOf(defaultMaxOrder), maxOrderFallbackCause);
            }
            internalLogger.debug("-Dio.netty.allocator.chunkSize: {}", (Object) Integer.valueOf(i << defaultMaxOrder));
            internalLogger.debug("-Dio.netty.allocator.tinyCacheSize: {}", (Object) Integer.valueOf(i3));
            internalLogger.debug("-Dio.netty.allocator.smallCacheSize: {}", (Object) Integer.valueOf(i4));
            internalLogger.debug("-Dio.netty.allocator.normalCacheSize: {}", (Object) Integer.valueOf(i5));
            internalLogger.debug("-Dio.netty.allocator.maxCachedBufferCapacity: {}", (Object) Integer.valueOf(i6));
            internalLogger.debug("-Dio.netty.allocator.cacheTrimInterval: {}", (Object) Integer.valueOf(i7));
            internalLogger.debug("-Dio.netty.allocator.useCacheForAllThreads: {}", (Object) Boolean.valueOf(z));
        }
    }

    public PooledByteBufAllocator() {
        this(false);
    }

    public PooledByteBufAllocator(boolean preferDirect) {
        this(preferDirect, DEFAULT_NUM_HEAP_ARENA, DEFAULT_NUM_DIRECT_ARENA, DEFAULT_PAGE_SIZE, DEFAULT_MAX_ORDER);
    }

    public PooledByteBufAllocator(int nHeapArena, int nDirectArena, int pageSize, int maxOrder) {
        this(false, nHeapArena, nDirectArena, pageSize, maxOrder);
    }

    @Deprecated
    public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder) {
        this(preferDirect, nHeapArena, nDirectArena, pageSize, maxOrder, DEFAULT_TINY_CACHE_SIZE, DEFAULT_SMALL_CACHE_SIZE, DEFAULT_NORMAL_CACHE_SIZE);
    }

    @Deprecated
    public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder, int tinyCacheSize2, int smallCacheSize2, int normalCacheSize2) {
        this(preferDirect, nHeapArena, nDirectArena, pageSize, maxOrder, tinyCacheSize2, smallCacheSize2, normalCacheSize2, DEFAULT_USE_CACHE_FOR_ALL_THREADS, DEFAULT_DIRECT_MEMORY_CACHE_ALIGNMENT);
    }

    public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder, int tinyCacheSize2, int smallCacheSize2, int normalCacheSize2, boolean useCacheForAllThreads) {
        this(preferDirect, nHeapArena, nDirectArena, pageSize, maxOrder, tinyCacheSize2, smallCacheSize2, normalCacheSize2, useCacheForAllThreads, DEFAULT_DIRECT_MEMORY_CACHE_ALIGNMENT);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder, int tinyCacheSize2, int smallCacheSize2, int normalCacheSize2, boolean useCacheForAllThreads, int directMemoryCacheAlignment) {
        super(preferDirect);
        PoolArena<ByteBuffer>[] poolArenaArr;
        int i = nHeapArena;
        int i2 = nDirectArena;
        int i3 = directMemoryCacheAlignment;
        this.threadCache = new PoolThreadLocalCache(useCacheForAllThreads);
        this.tinyCacheSize = tinyCacheSize2;
        this.smallCacheSize = smallCacheSize2;
        this.normalCacheSize = normalCacheSize2;
        this.chunkSize = validateAndCalculateChunkSize(pageSize, maxOrder);
        if (i < 0) {
            throw new IllegalArgumentException("nHeapArena: " + i + " (expected: >= 0)");
        } else if (i2 < 0) {
            throw new IllegalArgumentException("nDirectArea: " + i2 + " (expected: >= 0)");
        } else if (i3 < 0) {
            throw new IllegalArgumentException("directMemoryCacheAlignment: " + i3 + " (expected: >= 0)");
        } else if (i3 > 0 && !isDirectMemoryCacheAlignmentSupported()) {
            throw new IllegalArgumentException("directMemoryCacheAlignment is not supported");
        } else if (((-i3) & i3) == i3) {
            int pageShifts = validateAndCalculatePageShifts(pageSize);
            PoolArena<ByteBuffer>[] poolArenaArr2 = null;
            if (i > 0) {
                PoolArena<byte[]>[] newArenaArray = newArenaArray(nHeapArena);
                this.heapArenas = newArenaArray;
                List<PoolArenaMetric> metrics = new ArrayList<>(newArenaArray.length);
                int i4 = 0;
                while (i4 < this.heapArenas.length) {
                    int i5 = i4;
                    List<PoolArenaMetric> metrics2 = metrics;
                    PoolArena.HeapArena arena = new PoolArena.HeapArena(this, pageSize, maxOrder, pageShifts, this.chunkSize, directMemoryCacheAlignment);
                    this.heapArenas[i5] = arena;
                    metrics2.add(arena);
                    i4 = i5 + 1;
                    metrics = metrics2;
                    poolArenaArr2 = poolArenaArr2;
                    int i6 = tinyCacheSize2;
                    boolean z = useCacheForAllThreads;
                }
                int i7 = i4;
                poolArenaArr = poolArenaArr2;
                this.heapArenaMetrics = Collections.unmodifiableList(metrics);
            } else {
                poolArenaArr = null;
                this.heapArenas = null;
                this.heapArenaMetrics = Collections.emptyList();
            }
            if (i2 > 0) {
                PoolArena<ByteBuffer>[] newArenaArray2 = newArenaArray(nDirectArena);
                this.directArenas = newArenaArray2;
                List<PoolArenaMetric> metrics3 = new ArrayList<>(newArenaArray2.length);
                for (int i8 = 0; i8 < this.directArenas.length; i8++) {
                    PoolArena.DirectArena arena2 = new PoolArena.DirectArena(this, pageSize, maxOrder, pageShifts, this.chunkSize, directMemoryCacheAlignment);
                    this.directArenas[i8] = arena2;
                    metrics3.add(arena2);
                }
                this.directArenaMetrics = Collections.unmodifiableList(metrics3);
            } else {
                this.directArenas = poolArenaArr;
                this.directArenaMetrics = Collections.emptyList();
            }
            this.metric = new PooledByteBufAllocatorMetric(this);
        } else {
            throw new IllegalArgumentException("directMemoryCacheAlignment: " + i3 + " (expected: power of two)");
        }
    }

    @Deprecated
    public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder, int tinyCacheSize2, int smallCacheSize2, int normalCacheSize2, long cacheThreadAliveCheckInterval) {
        this(preferDirect, nHeapArena, nDirectArena, pageSize, maxOrder, tinyCacheSize2, smallCacheSize2, normalCacheSize2);
    }

    private static <T> PoolArena<T>[] newArenaArray(int size) {
        return new PoolArena[size];
    }

    private static int validateAndCalculatePageShifts(int pageSize) {
        if (pageSize < 4096) {
            throw new IllegalArgumentException("pageSize: " + pageSize + " (expected: " + 4096 + ")");
        } else if (((pageSize - 1) & pageSize) == 0) {
            return 31 - Integer.numberOfLeadingZeros(pageSize);
        } else {
            throw new IllegalArgumentException("pageSize: " + pageSize + " (expected: power of 2)");
        }
    }

    private static int validateAndCalculateChunkSize(int pageSize, int maxOrder) {
        if (maxOrder <= 14) {
            int chunkSize2 = pageSize;
            int i = maxOrder;
            while (i > 0) {
                if (chunkSize2 <= 536870912) {
                    chunkSize2 <<= 1;
                    i--;
                } else {
                    throw new IllegalArgumentException(String.format("pageSize (%d) << maxOrder (%d) must not exceed %d", new Object[]{Integer.valueOf(pageSize), Integer.valueOf(maxOrder), 1073741824}));
                }
            }
            return chunkSize2;
        }
        throw new IllegalArgumentException("maxOrder: " + maxOrder + " (expected: 0-14)");
    }

    /* access modifiers changed from: protected */
    public ByteBuf newHeapBuffer(int initialCapacity, int maxCapacity) {
        ByteBuf buf;
        PoolThreadCache cache = (PoolThreadCache) this.threadCache.get();
        PoolArena poolArena = cache.heapArena;
        if (poolArena != null) {
            buf = poolArena.allocate(cache, initialCapacity, maxCapacity);
        } else {
            buf = PlatformDependent.hasUnsafe() ? new UnpooledUnsafeHeapByteBuf(this, initialCapacity, maxCapacity) : new UnpooledHeapByteBuf((ByteBufAllocator) this, initialCapacity, maxCapacity);
        }
        return AbstractByteBufAllocator.toLeakAwareBuffer(buf);
    }

    /* access modifiers changed from: protected */
    public ByteBuf newDirectBuffer(int initialCapacity, int maxCapacity) {
        ByteBuf buf;
        PoolThreadCache cache = (PoolThreadCache) this.threadCache.get();
        PoolArena poolArena = cache.directArena;
        if (poolArena != null) {
            buf = poolArena.allocate(cache, initialCapacity, maxCapacity);
        } else {
            buf = PlatformDependent.hasUnsafe() ? UnsafeByteBufUtil.newUnsafeDirectByteBuf(this, initialCapacity, maxCapacity) : new UnpooledDirectByteBuf((ByteBufAllocator) this, initialCapacity, maxCapacity);
        }
        return AbstractByteBufAllocator.toLeakAwareBuffer(buf);
    }

    public static int defaultNumHeapArena() {
        return DEFAULT_NUM_HEAP_ARENA;
    }

    public static int defaultNumDirectArena() {
        return DEFAULT_NUM_DIRECT_ARENA;
    }

    public static int defaultPageSize() {
        return DEFAULT_PAGE_SIZE;
    }

    public static int defaultMaxOrder() {
        return DEFAULT_MAX_ORDER;
    }

    public static boolean defaultUseCacheForAllThreads() {
        return DEFAULT_USE_CACHE_FOR_ALL_THREADS;
    }

    public static boolean defaultPreferDirect() {
        return PlatformDependent.directBufferPreferred();
    }

    public static int defaultTinyCacheSize() {
        return DEFAULT_TINY_CACHE_SIZE;
    }

    public static int defaultSmallCacheSize() {
        return DEFAULT_SMALL_CACHE_SIZE;
    }

    public static int defaultNormalCacheSize() {
        return DEFAULT_NORMAL_CACHE_SIZE;
    }

    public static boolean isDirectMemoryCacheAlignmentSupported() {
        return PlatformDependent.hasUnsafe();
    }

    public boolean isDirectBufferPooled() {
        return this.directArenas != null;
    }

    @Deprecated
    public boolean hasThreadLocalCache() {
        return this.threadCache.isSet();
    }

    @Deprecated
    public void freeThreadLocalCache() {
        this.threadCache.remove();
    }

    public final class PoolThreadLocalCache extends FastThreadLocal<PoolThreadCache> {
        private final boolean useCacheForAllThreads;

        PoolThreadLocalCache(boolean useCacheForAllThreads2) {
            this.useCacheForAllThreads = useCacheForAllThreads2;
        }

        /* access modifiers changed from: protected */
        public synchronized PoolThreadCache initialValue() {
            PoolArena<byte[]> heapArena = leastUsedArena(PooledByteBufAllocator.this.heapArenas);
            PoolArena<ByteBuffer> directArena = leastUsedArena(PooledByteBufAllocator.this.directArenas);
            Thread current = Thread.currentThread();
            if (!this.useCacheForAllThreads) {
                if (!(current instanceof FastThreadLocalThread)) {
                    return new PoolThreadCache(heapArena, directArena, 0, 0, 0, 0, 0);
                }
            }
            return new PoolThreadCache(heapArena, directArena, PooledByteBufAllocator.this.tinyCacheSize, PooledByteBufAllocator.this.smallCacheSize, PooledByteBufAllocator.this.normalCacheSize, PooledByteBufAllocator.DEFAULT_MAX_CACHED_BUFFER_CAPACITY, PooledByteBufAllocator.DEFAULT_CACHE_TRIM_INTERVAL);
        }

        /* access modifiers changed from: protected */
        public void onRemoval(PoolThreadCache threadCache) {
            threadCache.free();
        }

        private <T> PoolArena<T> leastUsedArena(PoolArena<T>[] arenas) {
            if (arenas == null || arenas.length == 0) {
                return null;
            }
            PoolArena<T> minArena = arenas[0];
            for (int i = 1; i < arenas.length; i++) {
                PoolArena<T> arena = arenas[i];
                if (arena.numThreadCaches.get() < minArena.numThreadCaches.get()) {
                    minArena = arena;
                }
            }
            return minArena;
        }
    }

    public PooledByteBufAllocatorMetric metric() {
        return this.metric;
    }

    @Deprecated
    public int numHeapArenas() {
        return this.heapArenaMetrics.size();
    }

    @Deprecated
    public int numDirectArenas() {
        return this.directArenaMetrics.size();
    }

    @Deprecated
    public List<PoolArenaMetric> heapArenas() {
        return this.heapArenaMetrics;
    }

    @Deprecated
    public List<PoolArenaMetric> directArenas() {
        return this.directArenaMetrics;
    }

    @Deprecated
    public int numThreadLocalCaches() {
        PoolArena[] poolArenaArr = this.heapArenas;
        if (poolArenaArr == null) {
            poolArenaArr = this.directArenas;
        }
        if (poolArenaArr == null) {
            return 0;
        }
        int total = 0;
        for (PoolArena poolArena : poolArenaArr) {
            total += poolArena.numThreadCaches.get();
        }
        return total;
    }

    @Deprecated
    public int tinyCacheSize() {
        return this.tinyCacheSize;
    }

    @Deprecated
    public int smallCacheSize() {
        return this.smallCacheSize;
    }

    @Deprecated
    public int normalCacheSize() {
        return this.normalCacheSize;
    }

    @Deprecated
    public final int chunkSize() {
        return this.chunkSize;
    }

    /* access modifiers changed from: package-private */
    public final long usedHeapMemory() {
        return usedMemory(this.heapArenas);
    }

    /* access modifiers changed from: package-private */
    public final long usedDirectMemory() {
        return usedMemory(this.directArenas);
    }

    private static long usedMemory(PoolArena<?>... arenas) {
        if (arenas == null) {
            return -1;
        }
        long used = 0;
        for (PoolArena<?> arena : arenas) {
            used += arena.numActiveBytes();
            if (used < 0) {
                return DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
            }
        }
        return used;
    }

    /* access modifiers changed from: package-private */
    public final PoolThreadCache threadCache() {
        PoolThreadCache cache = (PoolThreadCache) this.threadCache.get();
        if (cache != null) {
            return cache;
        }
        throw new AssertionError();
    }

    public String dumpStats() {
        PoolArena<byte[]>[] poolArenaArr = this.heapArenas;
        int heapArenasLen = poolArenaArr == null ? 0 : poolArenaArr.length;
        StringBuilder sb = new StringBuilder(512);
        sb.append(heapArenasLen);
        sb.append(" heap arena(s):");
        StringBuilder buf = sb.append(StringUtil.NEWLINE);
        if (heapArenasLen > 0) {
            for (PoolArena<byte[]> a : this.heapArenas) {
                buf.append(a);
            }
        }
        PoolArena<ByteBuffer>[] poolArenaArr2 = this.directArenas;
        int directArenasLen = poolArenaArr2 == null ? 0 : poolArenaArr2.length;
        buf.append(directArenasLen);
        buf.append(" direct arena(s):");
        buf.append(StringUtil.NEWLINE);
        if (directArenasLen > 0) {
            for (PoolArena<ByteBuffer> a2 : this.directArenas) {
                buf.append(a2);
            }
        }
        return buf.toString();
    }
}
