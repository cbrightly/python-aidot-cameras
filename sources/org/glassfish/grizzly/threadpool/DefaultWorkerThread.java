package org.glassfish.grizzly.threadpool;

import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.attributes.AttributeHolder;
import org.glassfish.grizzly.memory.ThreadLocalPool;

public class DefaultWorkerThread extends Thread implements WorkerThread {
    private final AttributeHolder attributes;
    private final ThreadLocalPool memoryPool;
    private final ThreadCache.ObjectCache objectCache = new ThreadCache.ObjectCache();
    private long transactionTimeoutMillis = -1;

    public DefaultWorkerThread(AttributeBuilder attrBuilder, String name, ThreadLocalPool pool, Runnable runTask) {
        super(runTask, name);
        this.attributes = attrBuilder.createUnsafeAttributeHolder();
        this.memoryPool = pool;
    }

    public Thread getThread() {
        return this;
    }

    public AttributeHolder getAttributes() {
        return this.attributes;
    }

    public ThreadLocalPool getMemoryPool() {
        return this.memoryPool;
    }

    public final <E> E getFromCache(ThreadCache.CachedTypeIndex<E> index) {
        return this.objectCache.get(index);
    }

    public final <E> E takeFromCache(ThreadCache.CachedTypeIndex<E> index) {
        return this.objectCache.take(index);
    }

    public final <E> boolean putToCache(ThreadCache.CachedTypeIndex<E> index, E o) {
        return this.objectCache.put(index, o);
    }

    public long getTransactionTimeout(TimeUnit timeunit) {
        return timeunit.convert(this.transactionTimeoutMillis, TimeUnit.MILLISECONDS);
    }

    public void setTransactionTimeout(long timeout, TimeUnit timeunit) {
        this.transactionTimeoutMillis = TimeUnit.MILLISECONDS.convert(timeout, timeunit);
    }

    public void destroy() {
    }
}
