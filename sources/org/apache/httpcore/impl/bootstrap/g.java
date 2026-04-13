package org.apache.httpcore.impl.bootstrap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: WorkerPoolExecutor */
public class g extends ThreadPoolExecutor {
    private final Map<f, Boolean> c = new ConcurrentHashMap();

    public g(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    /* access modifiers changed from: protected */
    public void beforeExecute(Thread t, Runnable r) {
        if (r instanceof f) {
            this.c.put((f) r, Boolean.TRUE);
        }
    }

    /* access modifiers changed from: protected */
    public void afterExecute(Runnable r, Throwable t) {
        if (r instanceof f) {
            this.c.remove(r);
        }
    }

    public Set<f> a() {
        return new HashSet(this.c.keySet());
    }
}
