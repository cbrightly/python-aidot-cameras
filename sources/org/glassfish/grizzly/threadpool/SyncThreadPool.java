package org.glassfish.grizzly.threadpool;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.threadpool.AbstractThreadPool;

public class SyncThreadPool extends AbstractThreadPool {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private int activeThreadsCount;
    /* access modifiers changed from: private */
    public int currentPoolSize;
    protected int maxQueuedTasks = -1;
    /* access modifiers changed from: private */
    public final Queue<Runnable> workQueue;

    static {
        Class<SyncThreadPool> cls = SyncThreadPool.class;
    }

    static /* synthetic */ int access$008(SyncThreadPool x0) {
        int i = x0.activeThreadsCount;
        x0.activeThreadsCount = i + 1;
        return i;
    }

    static /* synthetic */ int access$010(SyncThreadPool x0) {
        int i = x0.activeThreadsCount;
        x0.activeThreadsCount = i - 1;
        return i;
    }

    public SyncThreadPool(ThreadPoolConfig config) {
        super(config);
        if (config.getKeepAliveTime(TimeUnit.MILLISECONDS) >= 0) {
            this.workQueue = config.getQueue() != null ? config.getQueue() : config.setQueue(new LinkedList()).getQueue();
            this.maxQueuedTasks = config.getQueueLimit();
            int corePoolSize = config.getCorePoolSize();
            while (this.currentPoolSize < corePoolSize) {
                startWorker(new SyncThreadWorker(true));
            }
            ProbeNotifier.notifyThreadPoolStarted(this);
            return;
        }
        throw new IllegalArgumentException("keepAliveTime < 0");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void execute(java.lang.Runnable r7) {
        /*
            r6 = this;
            if (r7 == 0) goto L_0x0070
            java.lang.Object r0 = r6.stateLock
            monitor-enter(r0)
            boolean r1 = r6.running     // Catch:{ all -> 0x006d }
            if (r1 == 0) goto L_0x0065
            java.util.Queue<java.lang.Runnable> r1 = r6.workQueue     // Catch:{ all -> 0x006d }
            int r1 = r1.size()     // Catch:{ all -> 0x006d }
            r2 = 1
            int r1 = r1 + r2
            int r3 = r6.maxQueuedTasks     // Catch:{ all -> 0x006d }
            if (r3 < 0) goto L_0x0017
            if (r1 > r3) goto L_0x005c
        L_0x0017:
            java.util.Queue<java.lang.Runnable> r3 = r6.workQueue     // Catch:{ all -> 0x006d }
            boolean r3 = r3.offer(r7)     // Catch:{ all -> 0x006d }
            if (r3 == 0) goto L_0x005c
            r6.onTaskQueued(r7)     // Catch:{ all -> 0x006d }
            int r3 = r6.currentPoolSize     // Catch:{ all -> 0x006d }
            int r4 = r6.activeThreadsCount     // Catch:{ all -> 0x006d }
            int r4 = r3 - r4
            if (r4 < r1) goto L_0x0031
            java.lang.Object r2 = r6.stateLock     // Catch:{ all -> 0x006d }
            r2.notify()     // Catch:{ all -> 0x006d }
            monitor-exit(r0)     // Catch:{ all -> 0x006d }
            return
        L_0x0031:
            org.glassfish.grizzly.threadpool.ThreadPoolConfig r5 = r6.config     // Catch:{ all -> 0x006d }
            int r5 = r5.getMaxPoolSize()     // Catch:{ all -> 0x006d }
            if (r3 >= r5) goto L_0x005a
            int r3 = r6.currentPoolSize     // Catch:{ all -> 0x006d }
            org.glassfish.grizzly.threadpool.ThreadPoolConfig r5 = r6.config     // Catch:{ all -> 0x006d }
            int r5 = r5.getCorePoolSize()     // Catch:{ all -> 0x006d }
            if (r3 >= r5) goto L_0x0044
            goto L_0x0045
        L_0x0044:
            r2 = 0
        L_0x0045:
            org.glassfish.grizzly.threadpool.SyncThreadPool$SyncThreadWorker r3 = new org.glassfish.grizzly.threadpool.SyncThreadPool$SyncThreadWorker     // Catch:{ all -> 0x006d }
            r3.<init>(r2)     // Catch:{ all -> 0x006d }
            r6.startWorker(r3)     // Catch:{ all -> 0x006d }
            int r3 = r6.currentPoolSize     // Catch:{ all -> 0x006d }
            org.glassfish.grizzly.threadpool.ThreadPoolConfig r5 = r6.config     // Catch:{ all -> 0x006d }
            int r5 = r5.getMaxPoolSize()     // Catch:{ all -> 0x006d }
            if (r3 != r5) goto L_0x005a
            r6.onMaxNumberOfThreadsReached()     // Catch:{ all -> 0x006d }
        L_0x005a:
            monitor-exit(r0)     // Catch:{ all -> 0x006d }
            return
        L_0x005c:
            r6.onTaskQueueOverflow()     // Catch:{ all -> 0x006d }
            java.lang.AssertionError r2 = new java.lang.AssertionError     // Catch:{ all -> 0x006d }
            r2.<init>()     // Catch:{ all -> 0x006d }
            throw r2     // Catch:{ all -> 0x006d }
        L_0x0065:
            java.util.concurrent.RejectedExecutionException r1 = new java.util.concurrent.RejectedExecutionException     // Catch:{ all -> 0x006d }
            java.lang.String r2 = "ThreadPool is not running"
            r1.<init>(r2)     // Catch:{ all -> 0x006d }
            throw r1     // Catch:{ all -> 0x006d }
        L_0x006d:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x006d }
            throw r1
        L_0x0070:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Runnable task is null"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.threadpool.SyncThreadPool.execute(java.lang.Runnable):void");
    }

    /* access modifiers changed from: protected */
    public void startWorker(AbstractThreadPool.Worker worker) {
        synchronized (this.stateLock) {
            super.startWorker(worker);
            this.activeThreadsCount++;
            this.currentPoolSize++;
        }
    }

    /* access modifiers changed from: protected */
    public void onWorkerExit(AbstractThreadPool.Worker worker) {
        super.onWorkerExit(worker);
        synchronized (this.stateLock) {
            this.currentPoolSize--;
            this.activeThreadsCount--;
        }
    }

    /* access modifiers changed from: protected */
    public void poisonAll() {
        int size = this.currentPoolSize;
        Queue<Runnable> q = getQueue();
        while (true) {
            int size2 = size - 1;
            if (size > 0) {
                q.offer(AbstractThreadPool.poison);
                size = size2;
            } else {
                return;
            }
        }
    }

    public String toString() {
        String str;
        synchronized (this.stateLock) {
            str = super.toString() + ", max-queue-size=" + this.maxQueuedTasks;
        }
        return str;
    }

    public class SyncThreadWorker extends AbstractThreadPool.Worker {
        private final boolean core;

        public SyncThreadWorker(boolean core2) {
            super();
            this.core = core2;
        }

        /* access modifiers changed from: protected */
        public Runnable getTask() {
            synchronized (SyncThreadPool.this.stateLock) {
                SyncThreadPool.access$010(SyncThreadPool.this);
                try {
                    if (SyncThreadPool.this.running) {
                        if (this.core || SyncThreadPool.this.currentPoolSize <= SyncThreadPool.this.config.getMaxPoolSize()) {
                            Runnable r = (Runnable) SyncThreadPool.this.workQueue.poll();
                            if (r != null) {
                                SyncThreadPool.access$008(SyncThreadPool.this);
                                return r;
                            }
                            long keepAliveMillis = SyncThreadPool.this.config.getKeepAliveTime(TimeUnit.MILLISECONDS);
                            boolean hasKeepAlive = !this.core && keepAliveMillis >= 0;
                            long endTime = -1;
                            if (hasKeepAlive) {
                                endTime = System.currentTimeMillis() + keepAliveMillis;
                            }
                            while (true) {
                                if (!hasKeepAlive) {
                                    SyncThreadPool.this.stateLock.wait();
                                } else {
                                    SyncThreadPool.this.stateLock.wait(keepAliveMillis);
                                }
                                Runnable r2 = (Runnable) SyncThreadPool.this.workQueue.poll();
                                if (r2 != null) {
                                    SyncThreadPool.access$008(SyncThreadPool.this);
                                    return r2;
                                } else if (!SyncThreadPool.this.running) {
                                    SyncThreadPool.access$008(SyncThreadPool.this);
                                    return null;
                                } else if (hasKeepAlive) {
                                    keepAliveMillis = endTime - System.currentTimeMillis();
                                    if (keepAliveMillis < 20) {
                                        SyncThreadPool.access$008(SyncThreadPool.this);
                                        return null;
                                    }
                                }
                            }
                        }
                    }
                    return null;
                } finally {
                    SyncThreadPool.access$008(SyncThreadPool.this);
                }
            }
        }
    }
}
