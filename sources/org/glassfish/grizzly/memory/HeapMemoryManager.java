package org.glassfish.grizzly.memory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.memory.AbstractMemoryManager;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringUtils;

public class HeapMemoryManager extends AbstractMemoryManager<HeapBuffer> implements WrapperAware {
    /* access modifiers changed from: private */
    public static final ThreadCache.CachedTypeIndex<RecyclableByteBufferWrapper> BBW_CACHE_IDX;
    /* access modifiers changed from: private */
    public static final ThreadCache.CachedTypeIndex<TrimmableHeapBuffer> CACHE_IDX;

    static {
        Class<HeapMemoryManager> cls = HeapMemoryManager.class;
        CACHE_IDX = ThreadCache.obtainIndex(TrimmableHeapBuffer.class, Integer.getInteger(cls.getName() + ".thb-cache-size", 8).intValue());
        BBW_CACHE_IDX = ThreadCache.obtainIndex(RecyclableByteBufferWrapper.class, Integer.getInteger(cls.getName() + ".rbbw-cache-size", 2).intValue());
    }

    public HeapMemoryManager() {
    }

    public HeapMemoryManager(int maxBufferSize) {
        super(maxBufferSize);
    }

    public HeapBuffer allocate(int size) {
        return allocateHeapBuffer(size);
    }

    public HeapBuffer allocateAtLeast(int size) {
        return allocateHeapBufferAtLeast(size);
    }

    public HeapBuffer reallocate(HeapBuffer oldBuffer, int newSize) {
        return reallocateHeapBuffer(oldBuffer, newSize);
    }

    public void release(HeapBuffer buffer) {
        releaseHeapBuffer(buffer);
    }

    public boolean willAllocateDirect(int size) {
        return false;
    }

    public MonitoringConfig<MemoryProbe> getMonitoringConfig() {
        return this.monitoringConfig;
    }

    public ThreadLocalPool createThreadLocalPool() {
        return new HeapBufferThreadLocalPool(this);
    }

    /* access modifiers changed from: protected */
    public Object createJmxManagementObject() {
        return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.memory.jmx.HeapMemoryManager", this, HeapMemoryManager.class);
    }

    public HeapBuffer wrap(byte[] data) {
        return createTrimAwareBuffer(data, 0, data.length);
    }

    public HeapBuffer wrap(byte[] data, int offset, int length) {
        return createTrimAwareBuffer(data, offset, length);
    }

    public HeapBuffer wrap(String s) {
        return wrap(s, Charset.defaultCharset());
    }

    public HeapBuffer wrap(String s, Charset charset) {
        return wrap(s.getBytes(charset));
    }

    public Buffer wrap(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            return wrap(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
        }
        return createByteBufferWrapper(byteBuffer);
    }

    /* access modifiers changed from: protected */
    public HeapBuffer allocateHeapBuffer(int size) {
        if (size > this.maxBufferSize) {
            return createTrimAwareBuffer(size);
        }
        ThreadLocalPool<HeapBuffer> threadLocalCache = getHeapBufferThreadLocalPool();
        if (threadLocalCache == null) {
            return createTrimAwareBuffer(size);
        }
        int remaining = threadLocalCache.remaining();
        if (remaining == 0 || remaining < size) {
            reallocatePoolBuffer();
        }
        return (HeapBuffer) allocateFromPool(threadLocalCache, size);
    }

    /* access modifiers changed from: protected */
    public HeapBuffer allocateHeapBufferAtLeast(int size) {
        if (size > this.maxBufferSize) {
            return createTrimAwareBuffer(size);
        }
        ThreadLocalPool<HeapBuffer> threadLocalCache = getHeapBufferThreadLocalPool();
        if (threadLocalCache == null) {
            return createTrimAwareBuffer(size);
        }
        int remaining = threadLocalCache.remaining();
        if (remaining == 0 || remaining < size) {
            reallocatePoolBuffer();
            remaining = threadLocalCache.remaining();
        }
        return (HeapBuffer) allocateFromPool(threadLocalCache, remaining);
    }

    /* access modifiers changed from: protected */
    public HeapBuffer reallocateHeapBuffer(HeapBuffer oldHeapBuffer, int newSize) {
        HeapBuffer newBuffer;
        if (oldHeapBuffer.capacity() >= newSize) {
            return oldHeapBuffer;
        }
        ThreadLocalPool<HeapBuffer> memoryPool = getHeapBufferThreadLocalPool();
        if (memoryPool == null || (newBuffer = memoryPool.reallocate(oldHeapBuffer, newSize)) == null) {
            HeapBuffer newHeapBuffer = allocateHeapBuffer(newSize);
            oldHeapBuffer.flip();
            return newHeapBuffer.put((Buffer) oldHeapBuffer);
        }
        ProbeNotifier.notifyBufferAllocatedFromPool(this.monitoringConfig, newSize - oldHeapBuffer.capacity());
        return newBuffer;
    }

    /* access modifiers changed from: protected */
    public final void releaseHeapBuffer(HeapBuffer heapBuffer) {
        ThreadLocalPool<HeapBuffer> memoryPool = getHeapBufferThreadLocalPool();
        if (memoryPool != null && memoryPool.release(heapBuffer.clear())) {
            ProbeNotifier.notifyBufferReleasedToPool(this.monitoringConfig, heapBuffer.capacity());
        }
    }

    private void reallocatePoolBuffer() {
        int i = this.maxBufferSize;
        byte[] heap = new byte[i];
        ProbeNotifier.notifyBufferAllocated(this.monitoringConfig, i);
        HeapBufferThreadLocalPool threadLocalCache = getHeapBufferThreadLocalPool();
        if (threadLocalCache != null) {
            threadLocalCache.reset(heap, 0, this.maxBufferSize);
        }
    }

    /* access modifiers changed from: package-private */
    public TrimmableHeapBuffer createTrimAwareBuffer(int length) {
        ProbeNotifier.notifyBufferAllocated(this.monitoringConfig, length);
        return createTrimAwareBuffer(new byte[length], 0, length);
    }

    /* access modifiers changed from: package-private */
    public TrimmableHeapBuffer createTrimAwareBuffer(byte[] heap, int offset, int length) {
        TrimmableHeapBuffer buffer = (TrimmableHeapBuffer) ThreadCache.takeFromCache(CACHE_IDX);
        if (buffer == null) {
            return new TrimmableHeapBuffer(heap, offset, length);
        }
        buffer.initialize(this, heap, offset, length);
        return buffer;
    }

    private ByteBufferWrapper createByteBufferWrapper(ByteBuffer underlyingByteBuffer) {
        RecyclableByteBufferWrapper buffer = (RecyclableByteBufferWrapper) ThreadCache.takeFromCache(BBW_CACHE_IDX);
        if (buffer == null) {
            return new RecyclableByteBufferWrapper(underlyingByteBuffer);
        }
        buffer.initialize(underlyingByteBuffer);
        return buffer;
    }

    /* access modifiers changed from: private */
    public static HeapBufferThreadLocalPool getHeapBufferThreadLocalPool() {
        ThreadLocalPool pool = AbstractMemoryManager.getThreadLocalPool();
        if (pool instanceof HeapBufferThreadLocalPool) {
            return (HeapBufferThreadLocalPool) pool;
        }
        return null;
    }

    public static final class HeapBufferThreadLocalPool implements ThreadLocalPool<HeapBuffer> {
        private final ByteBuffer[] byteBufferCache;
        private int byteBufferCacheSize;
        private int end;
        private int leftPos;
        private final HeapMemoryManager mm;
        private byte[] pool;
        private int rightPos;
        private int start;

        public HeapBufferThreadLocalPool(HeapMemoryManager mm2) {
            this(mm2, 8);
        }

        public HeapBufferThreadLocalPool(HeapMemoryManager mm2, int maxByteBufferCacheSize) {
            this.byteBufferCacheSize = 0;
            this.byteBufferCache = new ByteBuffer[maxByteBufferCacheSize];
            this.mm = mm2;
        }

        public HeapBuffer allocate(int size) {
            HeapBuffer allocated = this.mm.createTrimAwareBuffer(this.pool, this.rightPos, size);
            int i = this.byteBufferCacheSize;
            if (i > 0) {
                ByteBuffer[] byteBufferArr = this.byteBufferCache;
                int i2 = i - 1;
                this.byteBufferCacheSize = i2;
                allocated.byteBuffer = byteBufferArr[i2];
                byteBufferArr[i2] = null;
            }
            this.rightPos += size;
            return allocated;
        }

        public HeapBuffer reallocate(HeapBuffer heapBuffer, int newSize) {
            if (!isLastAllocated(heapBuffer)) {
                return null;
            }
            int remaining = remaining();
            int i = newSize - heapBuffer.cap;
            int diff = i;
            if (remaining < i) {
                return null;
            }
            this.rightPos += diff;
            heapBuffer.cap = newSize;
            heapBuffer.lim = newSize;
            return heapBuffer;
        }

        public boolean release(HeapBuffer heapBuffer) {
            boolean result;
            boolean z = false;
            boolean canCacheByteBuffer = heapBuffer.byteBuffer != null && this.byteBufferCacheSize < this.byteBufferCache.length;
            if (isLastAllocated(heapBuffer)) {
                int i = this.rightPos - heapBuffer.cap;
                this.rightPos = i;
                if (this.leftPos == i) {
                    int i2 = this.start;
                    this.rightPos = i2;
                    this.leftPos = i2;
                }
                result = true;
            } else if (isReleasableLeft(heapBuffer)) {
                int i3 = this.leftPos + heapBuffer.cap;
                this.leftPos = i3;
                if (i3 == this.rightPos) {
                    int i4 = this.start;
                    this.rightPos = i4;
                    this.leftPos = i4;
                }
                result = true;
            } else if (wantReset(heapBuffer.cap)) {
                reset(heapBuffer);
                result = true;
            } else {
                if (canCacheByteBuffer && this.pool == heapBuffer.heap) {
                    z = true;
                }
                canCacheByteBuffer = z;
                result = false;
            }
            if (canCacheByteBuffer) {
                ByteBuffer[] byteBufferArr = this.byteBufferCache;
                int i5 = this.byteBufferCacheSize;
                this.byteBufferCacheSize = i5 + 1;
                byteBufferArr[i5] = heapBuffer.byteBuffer;
            }
            return result;
        }

        public void reset(HeapBuffer heapBuffer) {
            reset(heapBuffer.heap, heapBuffer.offset, heapBuffer.cap);
        }

        public void reset(byte[] heap, int offset, int capacity) {
            if (this.pool != heap) {
                clearByteBufferCache();
                this.pool = heap;
            }
            this.start = offset;
            this.rightPos = offset;
            this.leftPos = offset;
            this.end = offset + capacity;
        }

        public boolean wantReset(int size) {
            return size - remaining() > 1024;
        }

        public boolean isLastAllocated(HeapBuffer oldHeapBuffer) {
            return oldHeapBuffer.heap == this.pool && oldHeapBuffer.offset + oldHeapBuffer.cap == this.rightPos;
        }

        private boolean isReleasableLeft(HeapBuffer oldHeapBuffer) {
            return oldHeapBuffer.heap == this.pool && oldHeapBuffer.offset == this.leftPos;
        }

        public HeapBuffer reduceLastAllocated(HeapBuffer heapBuffer) {
            int newPos = heapBuffer.offset + heapBuffer.cap;
            ProbeNotifier.notifyBufferReleasedToPool(this.mm.monitoringConfig, this.rightPos - newPos);
            this.rightPos = newPos;
            return null;
        }

        public int remaining() {
            return this.end - this.rightPos;
        }

        public boolean hasRemaining() {
            return this.rightPos < this.end;
        }

        public String toString() {
            return "(pool=" + this.pool.length + " pos=" + this.rightPos + " cap=" + this.end + ')';
        }

        private void clearByteBufferCache() {
            Arrays.fill(this.byteBufferCache, 0, this.byteBufferCacheSize, (Object) null);
            this.byteBufferCacheSize = 0;
        }
    }

    public static final class TrimmableHeapBuffer extends HeapBuffer implements AbstractMemoryManager.TrimAware {
        private HeapMemoryManager mm;

        private TrimmableHeapBuffer(HeapMemoryManager mm2, byte[] heap, int offset, int capacity) {
            super(heap, offset, capacity);
            this.mm = mm2;
        }

        public void trim() {
            HeapBufferThreadLocalPool threadLocalCache;
            checkDispose();
            int sizeToReturn = this.cap - this.pos;
            if (sizeToReturn > 0 && (threadLocalCache = HeapMemoryManager.getHeapBufferThreadLocalPool()) != null) {
                if (threadLocalCache.isLastAllocated((HeapBuffer) this)) {
                    flip();
                    this.cap = this.lim;
                    threadLocalCache.reduceLastAllocated((HeapBuffer) this);
                    return;
                } else if (threadLocalCache.wantReset(sizeToReturn)) {
                    flip();
                    int i = this.lim;
                    this.cap = i;
                    threadLocalCache.reset(this.heap, this.offset + i, sizeToReturn);
                    return;
                }
            }
            super.trim();
        }

        public void recycle() {
            this.allowBufferDispose = false;
            ThreadCache.putToCache(HeapMemoryManager.CACHE_IDX, this);
        }

        public void dispose() {
            prepareDispose();
            this.mm.release((HeapBuffer) this);
            this.mm = null;
            this.byteBuffer = null;
            this.heap = null;
            this.pos = 0;
            this.offset = 0;
            this.lim = 0;
            this.cap = 0;
            this.order = ByteOrder.BIG_ENDIAN;
            this.bigEndian = true;
            recycle();
        }

        /* access modifiers changed from: protected */
        public HeapBuffer createHeapBuffer(int offs, int capacity) {
            return this.mm.createTrimAwareBuffer(this.heap, this.offset + offs, capacity);
        }

        /* access modifiers changed from: package-private */
        public void initialize(HeapMemoryManager mm2, byte[] heap, int offset, int length) {
            this.mm = mm2;
            this.heap = heap;
            this.offset = offset;
            this.pos = 0;
            this.cap = length;
            this.lim = length;
            this.disposeStackTrace = null;
        }
    }

    public static final class RecyclableByteBufferWrapper extends ByteBufferWrapper implements Cacheable {
        private RecyclableByteBufferWrapper(ByteBuffer underlyingByteBuffer) {
            super(underlyingByteBuffer);
        }

        public void recycle() {
            this.allowBufferDispose = false;
            ThreadCache.putToCache(HeapMemoryManager.BBW_CACHE_IDX, this);
        }

        public void dispose() {
            super.dispose();
            recycle();
        }

        /* access modifiers changed from: private */
        public void initialize(ByteBuffer underlyingByteBuffer) {
            this.visible = underlyingByteBuffer;
            this.disposeStackTrace = null;
        }
    }
}
