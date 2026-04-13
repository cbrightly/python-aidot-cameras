package org.glassfish.grizzly.memory;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.threadpool.DefaultWorkerThread;

public abstract class AbstractMemoryManager<E extends Buffer> implements MemoryManager<E>, ThreadLocalPoolProvider {
    public static final int DEFAULT_MAX_BUFFER_SIZE = 65536;
    protected final int maxBufferSize;
    protected final DefaultMonitoringConfig<MemoryProbe> monitoringConfig;

    public interface TrimAware extends Cacheable {
    }

    /* access modifiers changed from: protected */
    public abstract Object createJmxManagementObject();

    public AbstractMemoryManager() {
        this(65536);
    }

    public AbstractMemoryManager(int maxBufferSize2) {
        this.monitoringConfig = new DefaultMonitoringConfig<MemoryProbe>(MemoryProbe.class) {
            public Object createManagementObject() {
                return AbstractMemoryManager.this.createJmxManagementObject();
            }
        };
        this.maxBufferSize = maxBufferSize2;
    }

    public int getReadyThreadBufferSize() {
        ThreadLocalPool threadLocalPool = getThreadLocalPool();
        if (threadLocalPool != null) {
            return threadLocalPool.remaining();
        }
        return 0;
    }

    public int getMaxBufferSize() {
        return this.maxBufferSize;
    }

    /* access modifiers changed from: protected */
    public Object allocateFromPool(ThreadLocalPool threadLocalCache, int size) {
        if (threadLocalCache.remaining() < size) {
            return null;
        }
        ProbeNotifier.notifyBufferAllocatedFromPool(this.monitoringConfig, size);
        return threadLocalCache.allocate(size);
    }

    protected static ThreadLocalPool getThreadLocalPool() {
        Thread t = Thread.currentThread();
        if (t instanceof DefaultWorkerThread) {
            return ((DefaultWorkerThread) t).getMemoryPool();
        }
        return null;
    }
}
