package org.glassfish.grizzly.memory;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.memory.AbstractMemoryManager;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringUtils;

public class ByteBufferManager extends AbstractMemoryManager<ByteBufferWrapper> implements WrapperAware, ByteBufferAware {
    /* access modifiers changed from: private */
    public static final ThreadCache.CachedTypeIndex<TrimAwareWrapper> CACHE_IDX = ThreadCache.obtainIndex(TrimAwareWrapper.class, Integer.getInteger(ByteBufferManager.class.getName() + ".taw-cache-size", 2).intValue());
    public static final int DEFAULT_SMALL_BUFFER_SIZE = 32;
    /* access modifiers changed from: private */
    public final ThreadCache.CachedTypeIndex<SmallByteBufferWrapper> SMALL_BUFFER_CACHE_IDX;
    protected boolean isDirect;
    protected final int maxSmallBufferSize;

    public ByteBufferManager() {
        this(false, 65536, 32);
    }

    public ByteBufferManager(boolean isDirect2) {
        this(isDirect2, 65536, 32);
    }

    public ByteBufferManager(boolean isDirect2, int maxBufferSize, int maxSmallBufferSize2) {
        super(maxBufferSize);
        this.SMALL_BUFFER_CACHE_IDX = ThreadCache.obtainIndex(SmallByteBufferWrapper.class.getName() + '.' + System.identityHashCode(this), SmallByteBufferWrapper.class, Integer.getInteger(ByteBufferManager.class.getName() + ".sbbw-cache-size", 16).intValue());
        this.maxSmallBufferSize = maxSmallBufferSize2;
        this.isDirect = isDirect2;
    }

    public int getMaxSmallBufferSize() {
        return this.maxSmallBufferSize;
    }

    public ByteBufferWrapper allocate(int size) {
        if (size > this.maxSmallBufferSize) {
            return wrap(allocateByteBuffer(size));
        }
        SmallByteBufferWrapper buffer = createSmallBuffer();
        buffer.limit(size);
        return buffer;
    }

    public ByteBufferWrapper allocateAtLeast(int size) {
        if (size > this.maxSmallBufferSize) {
            return wrap(allocateByteBufferAtLeast(size));
        }
        SmallByteBufferWrapper buffer = createSmallBuffer();
        buffer.limit(size);
        return buffer;
    }

    public ByteBufferWrapper reallocate(ByteBufferWrapper oldBuffer, int newSize) {
        return wrap(reallocateByteBuffer(oldBuffer.underlying(), newSize));
    }

    public void release(ByteBufferWrapper buffer) {
        releaseByteBuffer(buffer.underlying());
    }

    public boolean isDirect() {
        return this.isDirect;
    }

    public void setDirect(boolean isDirect2) {
        this.isDirect = isDirect2;
    }

    public boolean willAllocateDirect(int size) {
        return this.isDirect;
    }

    public ByteBufferWrapper wrap(byte[] data) {
        return wrap(data, 0, data.length);
    }

    public ByteBufferWrapper wrap(byte[] data, int offset, int length) {
        return wrap(ByteBuffer.wrap(data, offset, length));
    }

    public ByteBufferWrapper wrap(String s) {
        return wrap(s, Charset.defaultCharset());
    }

    public ByteBufferWrapper wrap(String s, Charset charset) {
        try {
            return wrap(ByteBuffer.wrap(s.getBytes(charset.name())));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public ThreadLocalPool createThreadLocalPool() {
        return new ByteBufferThreadLocalPool();
    }

    public ByteBufferWrapper wrap(ByteBuffer byteBuffer) {
        return createTrimAwareBuffer(byteBuffer);
    }

    public ByteBuffer allocateByteBuffer(int size) {
        if (size > this.maxBufferSize) {
            return allocateByteBuffer0(size);
        }
        ThreadLocalPool<ByteBuffer> threadLocalCache = getByteBufferThreadLocalPool();
        if (threadLocalCache == null) {
            return allocateByteBuffer0(size);
        }
        int remaining = threadLocalCache.remaining();
        if (remaining == 0 || remaining < size) {
            reallocatePoolBuffer();
        }
        return (ByteBuffer) allocateFromPool(threadLocalCache, size);
    }

    public ByteBuffer allocateByteBufferAtLeast(int size) {
        if (size > this.maxBufferSize) {
            return allocateByteBuffer0(size);
        }
        ThreadLocalPool<ByteBuffer> threadLocalCache = getByteBufferThreadLocalPool();
        if (threadLocalCache == null) {
            return allocateByteBuffer0(size);
        }
        int remaining = threadLocalCache.remaining();
        if (remaining == 0 || remaining < size) {
            reallocatePoolBuffer();
            remaining = threadLocalCache.remaining();
        }
        return (ByteBuffer) allocateFromPool(threadLocalCache, remaining);
    }

    public ByteBuffer reallocateByteBuffer(ByteBuffer oldByteBuffer, int newSize) {
        ByteBuffer newBuffer;
        if (oldByteBuffer.capacity() >= newSize) {
            return oldByteBuffer;
        }
        ThreadLocalPool<ByteBuffer> memoryPool = getByteBufferThreadLocalPool();
        if (memoryPool == null || (newBuffer = memoryPool.reallocate(oldByteBuffer, newSize)) == null) {
            ByteBuffer newByteBuffer = allocateByteBuffer(newSize);
            oldByteBuffer.flip();
            return newByteBuffer.put(oldByteBuffer);
        }
        ProbeNotifier.notifyBufferAllocatedFromPool(this.monitoringConfig, newSize - oldByteBuffer.capacity());
        return newBuffer;
    }

    public void releaseByteBuffer(ByteBuffer byteBuffer) {
        ThreadLocalPool<ByteBuffer> memoryPool = getByteBufferThreadLocalPool();
        if (memoryPool != null && memoryPool.release((ByteBuffer) byteBuffer.clear())) {
            ProbeNotifier.notifyBufferReleasedToPool(this.monitoringConfig, byteBuffer.capacity());
        }
    }

    /* access modifiers changed from: protected */
    public SmallByteBufferWrapper createSmallBuffer() {
        SmallByteBufferWrapper buffer = (SmallByteBufferWrapper) ThreadCache.takeFromCache(this.SMALL_BUFFER_CACHE_IDX);
        if (buffer == null) {
            return new SmallByteBufferWrapper(allocateByteBuffer0(this.maxSmallBufferSize));
        }
        ProbeNotifier.notifyBufferAllocatedFromPool(this.monitoringConfig, this.maxSmallBufferSize);
        return buffer;
    }

    public MonitoringConfig<MemoryProbe> getMonitoringConfig() {
        return this.monitoringConfig;
    }

    /* access modifiers changed from: protected */
    public Object createJmxManagementObject() {
        return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.memory.jmx.ByteBufferManager", this, ByteBufferManager.class);
    }

    /* access modifiers changed from: protected */
    public final ByteBuffer allocateByteBuffer0(int size) {
        ProbeNotifier.notifyBufferAllocated(this.monitoringConfig, size);
        if (this.isDirect) {
            return ByteBuffer.allocateDirect(size);
        }
        return ByteBuffer.allocate(size);
    }

    private TrimAwareWrapper createTrimAwareBuffer(ByteBuffer underlyingByteBuffer) {
        TrimAwareWrapper buffer = (TrimAwareWrapper) ThreadCache.takeFromCache(CACHE_IDX);
        if (buffer == null) {
            return new TrimAwareWrapper(underlyingByteBuffer);
        }
        buffer.visible = underlyingByteBuffer;
        return buffer;
    }

    private void reallocatePoolBuffer() {
        ByteBuffer byteBuffer = allocateByteBuffer0(this.maxBufferSize);
        ThreadLocalPool<ByteBuffer> threadLocalCache = getByteBufferThreadLocalPool();
        if (threadLocalCache != null) {
            threadLocalCache.reset(byteBuffer);
        }
    }

    /* access modifiers changed from: private */
    public static ByteBufferThreadLocalPool getByteBufferThreadLocalPool() {
        ThreadLocalPool pool = AbstractMemoryManager.getThreadLocalPool();
        if (pool instanceof ByteBufferThreadLocalPool) {
            return (ByteBufferThreadLocalPool) pool;
        }
        return null;
    }

    public static final class ByteBufferThreadLocalPool implements ThreadLocalPool<ByteBuffer> {
        private Object[] allocationHistory = new Object[8];
        private int lastAllocatedIndex;
        private ByteBuffer pool;

        public void reset(ByteBuffer pool2) {
            Arrays.fill(this.allocationHistory, 0, this.lastAllocatedIndex, (Object) null);
            this.lastAllocatedIndex = 0;
            this.pool = pool2;
        }

        public ByteBuffer allocate(int size) {
            return addHistory(Buffers.slice(this.pool, size));
        }

        public ByteBuffer reallocate(ByteBuffer oldByteBuffer, int newSize) {
            if (!isLastAllocated(oldByteBuffer) || remaining() + oldByteBuffer.capacity() < newSize) {
                return null;
            }
            this.lastAllocatedIndex--;
            ByteBuffer byteBuffer = this.pool;
            byteBuffer.position(byteBuffer.position() - oldByteBuffer.capacity());
            ByteBuffer newByteBuffer = Buffers.slice(this.pool, newSize);
            newByteBuffer.position(oldByteBuffer.position());
            return addHistory(newByteBuffer);
        }

        public boolean release(ByteBuffer underlyingBuffer) {
            if (isLastAllocated(underlyingBuffer)) {
                ByteBuffer byteBuffer = this.pool;
                byteBuffer.position(byteBuffer.position() - underlyingBuffer.capacity());
                Object[] objArr = this.allocationHistory;
                int i = this.lastAllocatedIndex - 1;
                this.lastAllocatedIndex = i;
                objArr[i] = null;
                return true;
            } else if (!wantReset(underlyingBuffer.capacity())) {
                return false;
            } else {
                reset(underlyingBuffer);
                return true;
            }
        }

        public boolean wantReset(int size) {
            return !hasRemaining() || (this.lastAllocatedIndex == 0 && this.pool.remaining() < size);
        }

        public boolean isLastAllocated(ByteBuffer oldByteBuffer) {
            int i = this.lastAllocatedIndex;
            return i > 0 && this.allocationHistory[i - 1] == oldByteBuffer;
        }

        public ByteBuffer reduceLastAllocated(ByteBuffer byteBuffer) {
            ByteBuffer oldLastAllocated = (ByteBuffer) this.allocationHistory[this.lastAllocatedIndex - 1];
            ByteBuffer byteBuffer2 = this.pool;
            byteBuffer2.position(byteBuffer2.position() - (oldLastAllocated.capacity() - byteBuffer.capacity()));
            this.allocationHistory[this.lastAllocatedIndex - 1] = byteBuffer;
            return oldLastAllocated;
        }

        public int remaining() {
            ByteBuffer byteBuffer = this.pool;
            if (byteBuffer != null) {
                return byteBuffer.remaining();
            }
            return 0;
        }

        public boolean hasRemaining() {
            return remaining() > 0;
        }

        private ByteBuffer addHistory(ByteBuffer allocated) {
            int i = this.lastAllocatedIndex;
            Object[] objArr = this.allocationHistory;
            if (i >= objArr.length) {
                this.allocationHistory = Arrays.copyOf(objArr, ((objArr.length * 3) / 2) + 1);
            }
            Object[] objArr2 = this.allocationHistory;
            int i2 = this.lastAllocatedIndex;
            this.lastAllocatedIndex = i2 + 1;
            objArr2[i2] = allocated;
            return allocated;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("(pool=");
            sb.append(this.pool);
            sb.append(" last-allocated-index=");
            sb.append(this.lastAllocatedIndex - 1);
            sb.append(" allocation-history=");
            sb.append(Arrays.toString(this.allocationHistory));
            sb.append(')');
            return sb.toString();
        }
    }

    public final class TrimAwareWrapper extends ByteBufferWrapper implements AbstractMemoryManager.TrimAware {
        private TrimAwareWrapper(ByteBuffer underlyingByteBuffer) {
            super(underlyingByteBuffer);
        }

        public void trim() {
            ThreadLocalPool<ByteBuffer> threadLocalCache;
            int sizeToReturn = this.visible.capacity() - this.visible.position();
            if (sizeToReturn > 0 && (threadLocalCache = ByteBufferManager.getByteBufferThreadLocalPool()) != null) {
                if (threadLocalCache.isLastAllocated(this.visible)) {
                    this.visible.flip();
                    ByteBuffer slice = this.visible.slice();
                    this.visible = slice;
                    threadLocalCache.reduceLastAllocated(slice);
                    return;
                } else if (threadLocalCache.wantReset(sizeToReturn)) {
                    this.visible.flip();
                    ByteBuffer originalByteBuffer = this.visible;
                    this.visible = this.visible.slice();
                    originalByteBuffer.position(originalByteBuffer.limit());
                    originalByteBuffer.limit(originalByteBuffer.capacity());
                    threadLocalCache.reset(originalByteBuffer);
                    return;
                }
            }
            super.trim();
        }

        public void recycle() {
            this.allowBufferDispose = false;
            ThreadCache.putToCache(ByteBufferManager.CACHE_IDX, this);
        }

        public void dispose() {
            prepareDispose();
            ByteBufferManager.this.release((ByteBufferWrapper) this);
            this.visible = null;
            recycle();
        }

        /* access modifiers changed from: protected */
        public ByteBufferWrapper wrapByteBuffer(ByteBuffer byteBuffer) {
            return ByteBufferManager.this.wrap(byteBuffer);
        }
    }

    public final class SmallByteBufferWrapper extends ByteBufferWrapper implements Cacheable {
        private SmallByteBufferWrapper(ByteBuffer underlyingByteBuffer) {
            super(underlyingByteBuffer);
        }

        public void dispose() {
            super.prepareDispose();
            this.visible.clear();
            recycle();
        }

        public void recycle() {
            int remaining = this.visible.remaining();
            ByteBufferManager byteBufferManager = ByteBufferManager.this;
            if (remaining == byteBufferManager.maxSmallBufferSize) {
                this.allowBufferDispose = false;
                this.disposeStackTrace = null;
                if (ThreadCache.putToCache(byteBufferManager.SMALL_BUFFER_CACHE_IDX, this)) {
                    ByteBufferManager byteBufferManager2 = ByteBufferManager.this;
                    ProbeNotifier.notifyBufferReleasedToPool(byteBufferManager2.monitoringConfig, byteBufferManager2.maxSmallBufferSize);
                }
            }
        }

        /* access modifiers changed from: protected */
        public ByteBufferWrapper wrapByteBuffer(ByteBuffer byteBuffer) {
            return ByteBufferManager.this.wrap(byteBuffer);
        }
    }
}
