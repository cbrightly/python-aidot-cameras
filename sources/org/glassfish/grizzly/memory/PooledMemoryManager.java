package org.glassfish.grizzly.memory;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.LockSupport;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringUtils;

public class PooledMemoryManager implements MemoryManager<Buffer>, WrapperAware {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    /* access modifiers changed from: private */
    public static final long BACK_OFF_DELAY;
    public static final int DEFAULT_BASE_BUFFER_SIZE = 4096;
    public static final int DEFAULT_GROWTH_FACTOR = 2;
    public static final float DEFAULT_HEAP_USAGE_PERCENTAGE = 0.03f;
    public static final int DEFAULT_NUMBER_OF_POOLS = 3;
    public static final float DEFAULT_PREALLOCATED_BUFFERS_PERCENTAGE = 1.0f;
    /* access modifiers changed from: private */
    public static final boolean FORCE_BYTE_BUFFER_BASED_BUFFERS;
    private final int maxPooledBufferSize;
    protected final DefaultMonitoringConfig<MemoryProbe> monitoringConfig;
    private final Pool[] pools;

    public interface PoolBuffer extends Buffer {
        PoolBuffer free(boolean z);

        boolean free();

        PoolSlice owner();

        PoolBuffer prepare();
    }

    static {
        Class<PooledMemoryManager> cls = PooledMemoryManager.class;
        FORCE_BYTE_BUFFER_BASED_BUFFERS = Boolean.getBoolean(cls + ".force-byte-buffer-based-buffers");
        BACK_OFF_DELAY = Long.getLong(cls + ".back-off-delay", 0).longValue();
    }

    public PooledMemoryManager() {
        this(4096, 3, 2, Runtime.getRuntime().availableProcessors(), 0.03f, 1.0f, false);
    }

    public PooledMemoryManager(boolean isDirect) {
        this(4096, 3, 2, Runtime.getRuntime().availableProcessors(), 0.03f, 1.0f, isDirect);
    }

    public PooledMemoryManager(int baseBufferSize, int numberOfPools, int growthFactor, int numberOfPoolSlices, float percentOfHeap, float percentPreallocated, boolean isDirect) {
        int i = numberOfPools;
        this.monitoringConfig = new DefaultMonitoringConfig<MemoryProbe>(MemoryProbe.class) {
            public Object createManagementObject() {
                return PooledMemoryManager.this.createJmxManagementObject();
            }
        };
        if (baseBufferSize <= 0) {
            throw new IllegalArgumentException("baseBufferSize must be greater than zero");
        } else if (i <= 0) {
            throw new IllegalArgumentException("numberOfPools must be greater than zero");
        } else if (growthFactor == 0 && i > 1) {
            throw new IllegalArgumentException("if numberOfPools is greater than 0 - growthFactor must be greater than zero");
        } else if (growthFactor < 0) {
            throw new IllegalArgumentException("growthFactor must be greater or equal to zero");
        } else if (numberOfPoolSlices <= 0) {
            throw new IllegalArgumentException("numberOfPoolSlices must be greater than zero");
        } else if (!isPowerOfTwo(baseBufferSize) || !isPowerOfTwo(growthFactor)) {
            throw new IllegalArgumentException("minBufferSize and growthFactor must be a power of two");
        } else if (percentOfHeap <= 0.0f || percentOfHeap >= 1.0f) {
            throw new IllegalArgumentException("percentOfHeap must be greater than zero and less than 1");
        } else if (percentPreallocated < 0.0f || percentPreallocated > 1.0f) {
            throw new IllegalArgumentException("percentPreallocated must be greater or equal to zero and less or equal to 1");
        } else {
            long memoryPerSubPool = (long) ((((float) Runtime.getRuntime().maxMemory()) * percentOfHeap) / ((float) i));
            this.pools = new Pool[i];
            int i2 = 0;
            int bufferSize = baseBufferSize;
            while (i2 < i) {
                this.pools[i2] = new Pool(bufferSize, memoryPerSubPool, numberOfPoolSlices, percentPreallocated, isDirect, this.monitoringConfig);
                i2++;
                bufferSize <<= growthFactor;
            }
            this.maxPooledBufferSize = this.pools[i - 1].bufferSize;
        }
    }

    public Buffer allocate(int size) {
        if (size >= 0) {
            return allocateAtLeast(size).limit(size);
        }
        throw new IllegalArgumentException("Requested allocation size must be greater than or equal to zero.");
    }

    public Buffer allocateAtLeast(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Requested allocation size must be greater than or equal to zero.");
        } else if (size == 0) {
            return Buffers.EMPTY_BUFFER;
        } else {
            return size <= this.maxPooledBufferSize ? getPoolFor(size).allocate() : allocateToCompositeBuffer(newCompositeBuffer(), size);
        }
    }

    public Buffer reallocate(Buffer oldBuffer, int newSize) {
        if (newSize == 0) {
            oldBuffer.tryDispose();
            return Buffers.EMPTY_BUFFER;
        }
        int curBufSize = oldBuffer.capacity();
        if (oldBuffer instanceof PoolBuffer) {
            if (curBufSize >= newSize) {
                PoolBuffer oldPoolBuffer = (PoolBuffer) oldBuffer;
                Pool newPool = getPoolFor(newSize);
                if (newPool == oldPoolBuffer.owner().owner) {
                    return oldPoolBuffer.limit(newSize);
                }
                int pos = Math.min(oldPoolBuffer.position(), newSize);
                Buffer newPoolBuffer = newPool.allocate();
                Buffers.setPositionLimit((Buffer) oldPoolBuffer, 0, newSize);
                newPoolBuffer.put((Buffer) oldPoolBuffer);
                Buffers.setPositionLimit(newPoolBuffer, pos, newSize);
                oldPoolBuffer.tryDispose();
                return newPoolBuffer;
            }
            int pos2 = oldBuffer.position();
            Buffers.setPositionLimit(oldBuffer, 0, curBufSize);
            if (newSize <= this.maxPooledBufferSize) {
                Buffer newPoolBuffer2 = getPoolFor(newSize).allocate();
                newPoolBuffer2.put(oldBuffer);
                Buffers.setPositionLimit(newPoolBuffer2, pos2, newSize);
                oldBuffer.tryDispose();
                return newPoolBuffer2;
            }
            CompositeBuffer cb = newCompositeBuffer();
            cb.append(oldBuffer);
            allocateToCompositeBuffer(cb, newSize - curBufSize);
            Buffers.setPositionLimit((Buffer) cb, pos2, newSize);
            return cb;
        } else if (oldBuffer.isComposite()) {
            CompositeBuffer oldCompositeBuffer = (CompositeBuffer) oldBuffer;
            if (curBufSize <= newSize) {
                return allocateToCompositeBuffer(oldCompositeBuffer, newSize - curBufSize);
            }
            int oldPos = oldCompositeBuffer.position();
            Buffers.setPositionLimit(oldBuffer, newSize, newSize);
            oldCompositeBuffer.trim();
            oldCompositeBuffer.position(Math.min(oldPos, newSize));
            return oldCompositeBuffer;
        } else {
            throw new AssertionError();
        }
    }

    public void release(Buffer buffer) {
        buffer.tryDispose();
    }

    public boolean willAllocateDirect(int size) {
        return false;
    }

    public MonitoringConfig<MemoryProbe> getMonitoringConfig() {
        return this.monitoringConfig;
    }

    public Buffer wrap(byte[] data) {
        return wrap(ByteBuffer.wrap(data));
    }

    public Buffer wrap(byte[] data, int offset, int length) {
        return wrap(ByteBuffer.wrap(data, offset, length));
    }

    public Buffer wrap(String s) {
        return wrap(s.getBytes(Charset.defaultCharset()));
    }

    public Buffer wrap(String s, Charset charset) {
        return wrap(s.getBytes(charset));
    }

    public Buffer wrap(ByteBuffer byteBuffer) {
        return new ByteBufferWrapper(byteBuffer);
    }

    /* access modifiers changed from: protected */
    public Object createJmxManagementObject() {
        return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.memory.jmx.PooledMemoryManager", this, PooledMemoryManager.class);
    }

    /* access modifiers changed from: package-private */
    public Pool[] getPools() {
        Pool[] poolArr = this.pools;
        return (Pool[]) Arrays.copyOf(poolArr, poolArr.length);
    }

    private Pool getPoolFor(int size) {
        int i = 0;
        while (true) {
            Pool[] poolArr = this.pools;
            if (i < poolArr.length) {
                Pool pool = poolArr[i];
                if (pool.bufferSize >= size) {
                    return pool;
                }
                i++;
            } else {
                throw new IllegalStateException("There is no pool big enough to allocate " + size + " bytes");
            }
        }
    }

    private CompositeBuffer allocateToCompositeBuffer(CompositeBuffer cb, int size) {
        int i;
        if (size >= 0) {
            if (size >= this.maxPooledBufferSize) {
                Pool[] poolArr = this.pools;
                Pool maxBufferSizePool = poolArr[poolArr.length - 1];
                do {
                    cb.append(maxBufferSizePool.allocate());
                    i = this.maxPooledBufferSize;
                    size -= i;
                } while (size >= i);
            }
            int i2 = 0;
            while (true) {
                Pool[] poolArr2 = this.pools;
                if (i2 >= poolArr2.length) {
                    break;
                }
                Pool pool = poolArr2[i2];
                if (pool.bufferSize >= size) {
                    cb.append(pool.allocate().limit(size));
                    break;
                }
                i2++;
            }
            return cb;
        }
        throw new AssertionError();
    }

    private CompositeBuffer newCompositeBuffer() {
        CompositeBuffer cb = CompositeBuffer.newBuffer(this);
        cb.allowInternalBuffersDispose(true);
        cb.allowBufferDispose(true);
        return cb;
    }

    private static boolean isPowerOfTwo(int valueToCheck) {
        return ((valueToCheck + -1) & valueToCheck) == 0;
    }

    /* access modifiers changed from: private */
    public static int fillHighestOneBitRight(int value) {
        int value2 = value | (value >> 1);
        int value3 = value2 | (value2 >> 2);
        int value4 = value3 | (value3 >> 4);
        int value5 = value4 | (value4 >> 8);
        return value5 | (value5 >> 16);
    }

    public static final class Pool {
        /* access modifiers changed from: private */
        public final int bufferSize;
        private final PoolSlice[] slices;

        public Pool(int bufferSize2, long memoryPerSubPool, int numberOfPoolSlices, float percentPreallocated, boolean isDirect, DefaultMonitoringConfig<MemoryProbe> monitoringConfig) {
            int i = numberOfPoolSlices;
            this.bufferSize = bufferSize2;
            this.slices = new PoolSlice[i];
            long memoryPerSlice = memoryPerSubPool / ((long) i);
            for (int i2 = 0; i2 < i; i2++) {
                this.slices[i2] = new PoolSlice(this, memoryPerSlice, bufferSize2, percentPreallocated, isDirect, monitoringConfig);
            }
        }

        public int elementsCount() {
            int sum = 0;
            int i = 0;
            while (true) {
                PoolSlice[] poolSliceArr = this.slices;
                if (i >= poolSliceArr.length) {
                    return sum;
                }
                sum += poolSliceArr[i].elementsCount();
                i++;
            }
        }

        public long size() {
            return ((long) elementsCount()) * ((long) this.bufferSize);
        }

        public int getBufferSize() {
            return this.bufferSize;
        }

        public PoolSlice[] getSlices() {
            PoolSlice[] poolSliceArr = this.slices;
            return (PoolSlice[]) Arrays.copyOf(poolSliceArr, poolSliceArr.length);
        }

        public Buffer allocate() {
            PoolSlice slice = getSlice();
            PoolBuffer b = slice.poll();
            if (b == null) {
                b = slice.allocate();
            }
            return b.prepare();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("Pool[" + Integer.toHexString(hashCode()) + "] {buffer size=" + this.bufferSize + ", slices count=" + this.slices.length);
            for (int i = 0; i < this.slices.length; i++) {
                if (i == 0) {
                    sb.append("\n");
                }
                sb.append("\t[");
                sb.append(i);
                sb.append("] ");
                sb.append(this.slices[i].toString());
                sb.append(10);
            }
            sb.append('}');
            return sb.toString();
        }

        private PoolSlice getSlice() {
            return this.slices[ThreadLocalRandom.current().nextInt(this.slices.length)];
        }
    }

    public static final class PoolSlice {
        private static final int LOG2_STRIDE = 4;
        private static final int MASK = 1073741823;
        private static final int STRIDE = 16;
        private static final int WRAP_BIT_MASK = 1073741824;
        private final int bufferSize;
        private final boolean isDirect;
        private final int maxPoolSize;
        private final DefaultMonitoringConfig<MemoryProbe> monitoringConfig;
        private final PaddedAtomicInteger offerIdx;
        /* access modifiers changed from: private */
        public final Pool owner;
        private final PaddedAtomicInteger pollIdx;
        private final PaddedAtomicReferenceArray<PoolBuffer> pool1;
        private final PaddedAtomicReferenceArray<PoolBuffer> pool2;
        private final int stridesInPool;

        PoolSlice(Pool owner2, long totalPoolSize, int bufferSize2, float percentPreallocated, boolean isDirect2, DefaultMonitoringConfig<MemoryProbe> monitoringConfig2) {
            this.owner = owner2;
            this.bufferSize = bufferSize2;
            this.isDirect = isDirect2;
            this.monitoringConfig = monitoringConfig2;
            int i = ((((int) (totalPoolSize / ((long) bufferSize2))) + 16) - 1) & -16;
            this.maxPoolSize = i;
            this.stridesInPool = i >> 4;
            if (i < 1073741824) {
                this.pool1 = new PaddedAtomicReferenceArray<>(i);
                int preallocatedBufs = Math.min(i, (int) (((float) i) * percentPreallocated));
                int idx = 0;
                int i2 = 0;
                while (i2 < preallocatedBufs) {
                    this.pool1.lazySet(idx, allocate().free(true));
                    i2++;
                    idx = nextIndex(idx);
                }
                this.pool2 = new PaddedAtomicReferenceArray<>(this.maxPoolSize);
                this.pollIdx = new PaddedAtomicInteger(0);
                this.offerIdx = new PaddedAtomicInteger(idx);
                return;
            }
            throw new IllegalStateException("Cannot manage a pool larger than 2^30-1");
        }

        public PoolBuffer poll() {
            while (true) {
                int pollIdx2 = this.pollIdx.get();
                if (isEmpty(pollIdx2, this.offerIdx.get())) {
                    return null;
                }
                if (this.pollIdx.compareAndSet(pollIdx2, nextIndex(pollIdx2))) {
                    int unmaskedPollIdx = unmask(pollIdx2);
                    AtomicReferenceArray<PoolBuffer> pool = pool(pollIdx2);
                    while (true) {
                        PoolBuffer pb = pool.getAndSet(unmaskedPollIdx, (Object) null);
                        if (pb != null) {
                            ProbeNotifier.notifyBufferAllocatedFromPool(this.monitoringConfig, this.bufferSize);
                            return pb;
                        }
                        Thread.yield();
                    }
                } else {
                    LockSupport.parkNanos(PooledMemoryManager.BACK_OFF_DELAY);
                }
            }
        }

        public boolean offer(PoolBuffer b) {
            while (true) {
                int offerIdx2 = this.offerIdx.get();
                if (isFull(this.pollIdx.get(), offerIdx2)) {
                    return false;
                }
                if (this.offerIdx.compareAndSet(offerIdx2, nextIndex(offerIdx2))) {
                    int unmaskedOfferIdx = unmask(offerIdx2);
                    AtomicReferenceArray<PoolBuffer> pool = pool(offerIdx2);
                    while (!pool.compareAndSet(unmaskedOfferIdx, (Object) null, b)) {
                        Thread.yield();
                    }
                    ProbeNotifier.notifyBufferReleasedToPool(this.monitoringConfig, this.bufferSize);
                    return true;
                }
                LockSupport.parkNanos(PooledMemoryManager.BACK_OFF_DELAY);
            }
        }

        public int elementsCount() {
            return elementsCount(this.pollIdx.get(), this.offerIdx.get());
        }

        private int elementsCount(int ridx, int widx) {
            return (unstride(unmask(widx)) - unstride(unmask(ridx))) + (this.maxPoolSize & PooledMemoryManager.fillHighestOneBitRight((ridx ^ widx) & 1073741824));
        }

        public int getMaxElementsCount() {
            return this.maxPoolSize;
        }

        public long size() {
            return ((long) elementsCount()) * ((long) this.bufferSize);
        }

        public void clear() {
            do {
            } while (poll() != null);
        }

        public PoolBuffer allocate() {
            PoolBuffer buffer;
            if (this.isDirect || PooledMemoryManager.FORCE_BYTE_BUFFER_BASED_BUFFERS) {
                buffer = new PoolByteBufferWrapper(this.isDirect ? ByteBuffer.allocateDirect(this.bufferSize) : ByteBuffer.allocate(this.bufferSize), this);
            } else {
                buffer = new PoolHeapBuffer(new byte[this.bufferSize], this);
            }
            ProbeNotifier.notifyBufferAllocated(this.monitoringConfig, this.bufferSize);
            return buffer;
        }

        private static boolean isFull(int pollIdx2, int offerIdx2) {
            return (pollIdx2 ^ offerIdx2) == 1073741824;
        }

        private static boolean isEmpty(int pollIdx2, int offerIdx2) {
            return pollIdx2 == offerIdx2;
        }

        private AtomicReferenceArray<PoolBuffer> pool(int idx) {
            return (1073741824 & idx) == 0 ? this.pool1 : this.pool2;
        }

        private int nextIndex(int currentIdx) {
            int arrayIndex = unmask(currentIdx);
            int i = arrayIndex + 16;
            int i2 = this.maxPoolSize;
            if (i < i2) {
                return currentIdx + 16;
            }
            int offset = (arrayIndex - i2) + 16 + 1;
            return offset == 16 ? (currentIdx & 1073741824) ^ 1073741824 : (currentIdx & 1073741824) | offset;
        }

        private static int unmask(int val) {
            return MASK & val;
        }

        private static int getWrappingBit(int val) {
            return 1073741824 & val;
        }

        private int unstride(int idx) {
            return (idx >> 4) + ((idx & 15) * this.stridesInPool);
        }

        public String toString() {
            return toString(this.pollIdx.get(), this.offerIdx.get());
        }

        private String toString(int ridx, int widx) {
            return "BufferSlice[" + Integer.toHexString(hashCode()) + "] {buffer size=" + this.bufferSize + ", elements in pool=" + elementsCount(ridx, widx) + ", poll index=" + unmask(ridx) + ", poll wrap bit=" + (PooledMemoryManager.fillHighestOneBitRight(getWrappingBit(ridx)) & 1) + ", offer index=" + unmask(widx) + ", offer wrap bit=" + (PooledMemoryManager.fillHighestOneBitRight(getWrappingBit(widx)) & 1) + ", maxPoolSize=" + this.maxPoolSize + '}';
        }

        public static final class PaddedAtomicInteger extends AtomicInteger {
            private long p0;
            private long p1;
            private long p2;
            private long p3;
            private long p4;
            private long p5;
            private long p6;
            private long p7 = 7;

            PaddedAtomicInteger(int initialValue) {
                super(initialValue);
            }
        }

        public static final class PaddedAtomicReferenceArray<E> extends AtomicReferenceArray<E> {
            private long p0;
            private long p1;
            private long p2;
            private long p3;
            private long p4;
            private long p5;
            private long p6;
            private long p7 = 7;

            PaddedAtomicReferenceArray(int length) {
                super(length);
            }
        }
    }

    public static final class PoolHeapBuffer extends HeapBuffer implements PoolBuffer {
        boolean free;
        private final PoolSlice owner;
        protected final AtomicInteger shareCount;
        protected final PoolHeapBuffer source;

        private PoolHeapBuffer(byte[] heap, PoolSlice owner2) {
            this(heap, 0, heap.length, owner2, (PoolHeapBuffer) null, new AtomicInteger());
        }

        private PoolHeapBuffer(byte[] heap, int offs, int cap, PoolSlice owner2, PoolHeapBuffer source2, AtomicInteger shareCount2) {
            super(heap, offs, cap);
            if (heap == null) {
                throw new IllegalArgumentException("heap cannot be null.");
            } else if (shareCount2 != null) {
                this.owner = owner2;
                this.shareCount = shareCount2;
                this.source = source2 != null ? source2 : this;
            } else {
                throw new IllegalArgumentException("shareCount cannot be null");
            }
        }

        public PoolBuffer prepare() {
            this.allowBufferDispose = true;
            this.free = false;
            return this;
        }

        public PoolSlice owner() {
            return this.owner;
        }

        public boolean free() {
            return this.free;
        }

        public PoolBuffer free(boolean free2) {
            this.free = free2;
            return this;
        }

        public HeapBuffer asReadOnlyBuffer() {
            HeapBuffer b = asReadOnlyBuffer(this.offset, this.cap);
            b.pos = this.pos;
            b.lim = this.lim;
            return b;
        }

        /* access modifiers changed from: private */
        public HeapBuffer asReadOnlyBuffer(int offset, int cap) {
            checkDispose();
            onShareHeap();
            HeapBuffer b = new ReadOnlyHeapBuffer(this.heap, offset, cap) {
                public void dispose() {
                    super.dispose();
                    PoolHeapBuffer.this.dispose0();
                }

                /* access modifiers changed from: protected */
                public void onShareHeap() {
                    PoolHeapBuffer.this.onShareHeap();
                }

                /* access modifiers changed from: protected */
                public HeapBuffer createHeapBuffer(int offset, int capacity) {
                    return PoolHeapBuffer.this.asReadOnlyBuffer(offset, capacity);
                }
            };
            b.allowBufferDispose(true);
            return b;
        }

        public void dispose() {
            if (!this.free) {
                this.free = true;
                dispose0();
            }
        }

        /* access modifiers changed from: private */
        public void dispose0() {
            boolean z = true;
            boolean isNotShared = this.shareCount.get() == 0;
            if (!isNotShared) {
                if (this.shareCount.getAndDecrement() != 0) {
                    z = false;
                }
                isNotShared = z;
                if (isNotShared) {
                    this.shareCount.set(0);
                }
            }
            if (isNotShared) {
                this.source.returnToPool();
            }
        }

        private void returnToPool() {
            this.cap = this.heap.length;
            clear();
            this.owner.offer(this);
        }

        /* access modifiers changed from: protected */
        public void checkDispose() {
            if (this.free) {
                throw new IllegalStateException("PoolBuffer has already been disposed", this.disposeStackTrace);
            }
        }

        /* access modifiers changed from: protected */
        public HeapBuffer createHeapBuffer(int offs, int capacity) {
            onShareHeap();
            PoolHeapBuffer b = new PoolHeapBuffer(this.heap, offs + this.offset, capacity, (PoolSlice) null, this.source, this.shareCount);
            b.allowBufferDispose(true);
            return b;
        }

        /* access modifiers changed from: protected */
        public void onShareHeap() {
            super.onShareHeap();
            this.shareCount.incrementAndGet();
        }
    }

    public static final class PoolByteBufferWrapper extends ByteBufferWrapper implements PoolBuffer {
        boolean free;
        private final ByteBuffer origVisible;
        private final PoolSlice owner;
        protected final AtomicInteger shareCount;
        protected final PoolByteBufferWrapper source;

        private PoolByteBufferWrapper(ByteBuffer underlyingByteBuffer, PoolSlice owner2) {
            this(underlyingByteBuffer, owner2, (PoolByteBufferWrapper) null, new AtomicInteger());
        }

        private PoolByteBufferWrapper(ByteBuffer underlyingByteBuffer, PoolSlice owner2, PoolByteBufferWrapper source2, AtomicInteger shareCount2) {
            super(underlyingByteBuffer);
            if (underlyingByteBuffer == null) {
                throw new IllegalArgumentException("underlyingByteBuffer cannot be null.");
            } else if (shareCount2 != null) {
                this.owner = owner2;
                this.shareCount = shareCount2;
                PoolByteBufferWrapper poolByteBufferWrapper = source2 != null ? source2 : this;
                this.source = poolByteBufferWrapper;
                this.origVisible = poolByteBufferWrapper.visible;
            } else {
                throw new IllegalArgumentException("shareCount cannot be null");
            }
        }

        public PoolBuffer prepare() {
            this.allowBufferDispose = true;
            this.free = false;
            return this;
        }

        public PoolSlice owner() {
            return this.owner;
        }

        public boolean free() {
            return this.free;
        }

        public PoolBuffer free(boolean free2) {
            this.free = free2;
            return this;
        }

        public void dispose() {
            if (!this.free) {
                this.free = true;
                dispose0();
            }
        }

        private void dispose0() {
            boolean z = true;
            boolean isNotShared = this.shareCount.get() == 0;
            if (!isNotShared) {
                if (this.shareCount.getAndDecrement() != 0) {
                    z = false;
                }
                isNotShared = z;
                if (isNotShared) {
                    this.shareCount.set(0);
                }
            }
            if (isNotShared) {
                this.source.returnToPool();
            }
        }

        /* access modifiers changed from: protected */
        public ByteBufferWrapper wrapByteBuffer(ByteBuffer buffer) {
            PoolByteBufferWrapper b = new PoolByteBufferWrapper(buffer, (PoolSlice) null, this.source, this.shareCount);
            b.allowBufferDispose(true);
            this.shareCount.incrementAndGet();
            return b;
        }

        /* access modifiers changed from: protected */
        public void checkDispose() {
            if (this.free) {
                throw new IllegalStateException("PoolBuffer has already been disposed", this.disposeStackTrace);
            }
        }

        private void returnToPool() {
            ByteBuffer byteBuffer = this.origVisible;
            this.visible = byteBuffer;
            byteBuffer.clear();
            this.owner.offer(this);
        }
    }
}
